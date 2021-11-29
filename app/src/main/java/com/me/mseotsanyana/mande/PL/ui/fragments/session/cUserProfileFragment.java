package com.me.mseotsanyana.mande.PL.ui.fragments.session;

import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.firebase.firestore.ListenerRegistration;
import com.me.mseotsanyana.mande.BLL.executor.Impl.cThreadExecutorImpl;
import com.me.mseotsanyana.mande.BLL.model.session.cUserProfileModel;
import com.me.mseotsanyana.mande.BLL.repository.session.iUserProfileRepository;
import com.me.mseotsanyana.mande.DAL.storage.base.cFirebaseChildCallBack;
import com.me.mseotsanyana.mande.DAL.ìmpl.firestore.session.cSharedPreferenceFirestoreRepositoryImpl;
import com.me.mseotsanyana.mande.DAL.ìmpl.firestore.session.cUserProfileFirestoreRepositoryImpl;
import com.me.mseotsanyana.mande.PL.presenters.session.Impl.cUserProfilesPresenterImpl;
import com.me.mseotsanyana.mande.PL.presenters.session.iUserProfilePresenter;
import com.me.mseotsanyana.mande.PL.ui.adapters.session.cUserProfileAdapter;
import com.me.mseotsanyana.mande.R;
import com.me.mseotsanyana.mande.cMainThreadImpl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class cUserProfileFragment extends Fragment implements iUserProfilePresenter.View,
        Filterable {
    //private static final String TAG = cUserProfileFragment.class.getSimpleName();
    //private static final SimpleDateFormat ssdf = cConstant.SHORT_FORMAT_DATE;

    private iUserProfilePresenter userProfilesPresenter;

    private Toolbar toolbar;
    private LinearLayout includeProgressBar;
    private cUserProfileAdapter userProfilesAdapter;

    private ActivityResultLauncher<String> launchUploadImage, launchUploadExcelFile;

    private AppCompatActivity activity;

    private cUserProfileModel userProfileModel;

    private iUserProfileRepository userProfileRepository;
    private ListenerRegistration listenerRegistration;

    public cUserProfileFragment() {
    }

    public static cUserProfileFragment newInstance() {
        return new cUserProfileFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        // upload user profile image contract
        launchUploadImage = registerForActivityResult(new ActivityResultContracts.GetContent(),
                this::onSelectedImageResult);

        // upload excel file contract
        launchUploadExcelFile = registerForActivityResult(new ActivityResultContracts.GetContent(),
                this::onSelectedExcelFileResult);

        // update user profiles listener
        //userProfilesPresenter.createUserProfilesListener();

    }

    @Override
    public void onResume() {
        super.onResume();
        /* get all teams from the database */
        userProfilesPresenter.resume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //userProfilesPresenter.destroyUserProfilesListener();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.session_userprofiles_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        /* create data structures */
        initDataStructures();

        /* create RecyclerView */
        initUserProfilesViews(view);

        /* initialize progress bar */
        initProgressBarView(view);

        /* initialise draggable FAB */
        initDraggableFAB(view);

        /* show the back arrow button */
        toolbar = view.findViewById(R.id.toolbar);
        CollapsingToolbarLayout collapsingToolbarLayout;
        collapsingToolbarLayout = view.findViewById(R.id.collapsingToolbarLayout);
        collapsingToolbarLayout.setTitle("User Profiles");
        activity.setSupportActionBar(toolbar);
        Objects.requireNonNull(activity.getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        Objects.requireNonNull(activity.getSupportActionBar()).setDisplayShowHomeEnabled(true);
    }

    private void initDataStructures() {
        List<cUserProfileModel> userProfileModels = new ArrayList<>();

        userProfilesAdapter = new cUserProfileAdapter(getActivity(), userProfileModels, this);

        assert getArguments() != null;
        userProfileRepository = new cUserProfileFirestoreRepositoryImpl(requireContext());
        userProfilesPresenter = new cUserProfilesPresenterImpl(
                cThreadExecutorImpl.getInstance(),
                cMainThreadImpl.getInstance(),
                this,
                new cSharedPreferenceFirestoreRepositoryImpl(requireContext()),
                userProfileRepository);

        activity = ((AppCompatActivity) getActivity());
    }

    private void initUserProfilesViews(View view) {
        RecyclerView userRecyclerView = view.findViewById(R.id.userRecyclerView);
        userRecyclerView.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        userRecyclerView.setAdapter(userProfilesAdapter);
        userRecyclerView.setLayoutManager(llm);
    }

    private void initProgressBarView(View view) {
        includeProgressBar = view.findViewById(R.id.includeProgressBar);
    }

    private void initDraggableFAB(View view) {
        //view.findViewById(R.id.userprofileFAB).setOnClickListener(v -> onClickCreateTeam());
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        /* get inflated option menu */
        Menu toolBarMenu = setToolBar();

        toolbar.setOnMenuItemClickListener(
                item -> onOptionsItemSelected(item));

        SearchManager searchManager = (SearchManager) requireActivity().
                getSystemService(Context.SEARCH_SERVICE);

        SearchView searchView = (SearchView) toolBarMenu.findItem(R.id.searchItem).getActionView();
        searchView.setSearchableInfo(Objects.requireNonNull(searchManager).
                getSearchableInfo(requireActivity().getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        search(searchView);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.uploadItem:
                launchUploadExcelFile.launch(
                        "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
                return true;
            case R.id.downloadItem:
                // do something
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    private void search(SearchView searchView) {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                userProfilesAdapter.getFilter().filter(query);
                return false;
            }
        });
    }

    private Menu setToolBar() {
        toolbar.inflateMenu(R.menu.me_toolbar_menu);
        return toolbar.getMenu();
    }

    public cUserProfileModel getUserProfileModel() {
        return userProfileModel;
    }

    public void setUserProfileModel(cUserProfileModel userProfileModel) {
        this.userProfileModel = userProfileModel;
    }

    // CRUD

    // READ
    @Override
    public void onReadUserProfilesFailed(String msg) {

    }

    @Override
    public void onReadUserProfilesSucceeded(List<cUserProfileModel> userProfileModels) {
        userProfilesAdapter.setUserProfileModels(userProfileModels);
        userProfilesAdapter.notifyDataSetChanged();
    }

    // UPLOAD
    @Override
    public void onUploadUserProfilesFailed(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUploadUserProfilesSucceeded(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpdateUserProfileImageFailed(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpdateUserProfileImageSucceeded(String msg) {
        userProfilesAdapter.updateUserProfileImage(getUserProfileModel());
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    // ON CLICK FUNCTIONS

    @Override
    public void onClickUserProfileImage(cUserProfileModel userProfileModel) {
        setUserProfileModel(userProfileModel);
        launchUploadImage.launch("image/*");
    }

    // PRESENTER FUNCTIONS
    @Override
    public void showProgress() {
        includeProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        includeProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showError(String message) {

    }

    @Override
    public Filter getFilter() {
        return null;
    }

    private void onSelectedImageResult(Uri uri) {
        // update uri of the model
        try {
            Bitmap bitmap;
            bitmap = MediaStore.Images.Media.getBitmap(requireActivity().getContentResolver(), uri);
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            byte[] userProfileImageData = bytes.toByteArray();
            //getUserProfileModel().setImageData(userProfileImageData);

            userProfilesPresenter.updateUserProfileImage(getUserProfileModel().getUserServerID(),
                    userProfileImageData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void onSelectedExcelFileResult(Uri uri) {
        // update uri of the model
        //Log.d(TAG, "uri path: "+uri.getPath());
        userProfilesPresenter.uploadUserProfilesFromExcel(uri.getPath());
    }

    private void getAllUserProfiles() {
        listenerRegistration = userProfileRepository.readAllUserProfilesByChildEvent(
                new cFirebaseChildCallBack() {
                    @Override
                    public void onChildAdded(Object object) {
                        if (object != null) {
                            cUserProfileModel userProfileModel = (cUserProfileModel) object;
                            if (userProfilesAdapter != null) {
                                userProfilesAdapter.getUserProfileModels().add(userProfileModel);
                                userProfilesAdapter.reloadList(
                                        userProfilesAdapter.getUserProfileModels().size() - 1,
                                        "ADD");
                            }
                        }
                    }

                    @Override
                    public void onChildChanged(Object object) {
//                        if (object != null) {
//                            cUserProfileModel userProfileModel = (cUserProfileModel) object;
//                            userProfilesAdapter.getUserProfileModels().update(
//                                    userProfileModel.getUserServerID(), userProfileModel);
//                            userProfilesAdapter.reloadList(
//                                    userProfilesAdapter.getUserProfileModels().getIndexByKey(
//                                            userProfileModel.getUserServerID()),
//                                    "UPDATE");
//                        }
                    }

                    @Override
                    public void onChildRemoved(Object object) {
//                        if (object != null) {
//                            cUserProfileModel userProfileModel = (cUserProfileModel) object;
//                            userProfilesAdapter.getUserProfileModels().update(
//                                    userProfileModel.getUserServerID(), userProfileModel);
//                            userProfilesAdapter.reloadList(
//                                    userProfilesAdapter.getUserProfileModels().getIndexByKey(
//                                            userProfileModel.getUserServerID()),
//                                    "DELETE");
//                        }
                    }

                    @Override
                    public void onCancelled(Object object) {

                    }
                });
    }
}