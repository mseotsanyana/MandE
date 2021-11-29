package com.me.mseotsanyana.mande.PL.ui.fragments.logframe;

import android.Manifest;
import android.app.Activity;
import android.app.SearchManager;

import android.content.Context;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatEditText;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.text.Layout;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.util.Pair;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.me.mseotsanyana.mande.BLL.executor.Impl.cThreadExecutorImpl;
import com.me.mseotsanyana.mande.BLL.model.logframe.cLogFrameModel;
import com.me.mseotsanyana.mande.DAL.ìmpl.firestore.logframe.cLogFrameFirestoreRepositoryImpl;
import com.me.mseotsanyana.mande.DAL.ìmpl.firestore.session.cSharedPreferenceFirestoreRepositoryImpl;
import com.me.mseotsanyana.mande.PL.presenters.cPresenterUtils;
import com.me.mseotsanyana.mande.PL.presenters.logframe.Impl.cLogFramePresenterImpl;
import com.me.mseotsanyana.mande.PL.presenters.logframe.iLogFramePresenter;
import com.me.mseotsanyana.mande.PL.ui.adapters.logframe.cLogFrameAdapter;
import com.me.mseotsanyana.mande.UTIL.TextDrawable;
import com.me.mseotsanyana.mande.UTIL.cConstant;

import com.me.mseotsanyana.mande.R;
import com.me.mseotsanyana.mande.UTIL.cFontManager;
import com.me.mseotsanyana.mande.cMainThreadImpl;
import com.me.mseotsanyana.multiselectspinnerlibrary.cKeyPairBoolData;
import com.me.mseotsanyana.multiselectspinnerlibrary.cSingleSpinnerSearch;
import com.me.mseotsanyana.quickactionlibrary.cCustomQuickAction;
import com.me.mseotsanyana.treeadapterlibrary.cTreeModel;

import java.io.File;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;

import static com.me.mseotsanyana.mande.R.string.logframe_list_title;

/**
 * Created by mseotsanyana on 2016/11/02.
 */
public class cLogFrameFragment extends Fragment implements iLogFramePresenter.View {
    private static final String TAG = cLogFrameFragment.class.getSimpleName();
    private static final SimpleDateFormat tsdf = cConstant.TIMESTAMP_FORMAT_DATE;
    private static final SimpleDateFormat ssdf = cConstant.SHORT_FORMAT_DATE;

    //private static final int STORAGE_PERMISSION_CODE = 101;


    /* logframe views */
    private LinearLayout includeProgressBar;

    /* logframe interface */
    private iLogFramePresenter logFramePresenter;

    /* logframe adapter */
    private cLogFrameAdapter logFrameRecyclerViewAdapter;

    private ActivityResultLauncher<Intent> launchUploadActivity;

    private AppCompatActivity activity;

    public cLogFrameFragment() {
    }

    public static cLogFrameFragment newInstance() {
        return new cLogFrameFragment();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        // Create launcher variable inside onAttach or onCreate or global
//        launchUploadActivity = registerForActivityResult(
//                new ActivityResultContracts.StartActivityForResult(), result -> {
//                    if (result.getResultCode() == Activity.RESULT_OK) {
//
//
//
//                        Intent resultDate = result.getData();
//                        Uri fileUri = Objects.requireNonNull(resultDate).getData();
//                        String filePath = cPresenterUtils.getPath(getContext(), fileUri);
//
//                        Log.d(TAG, "======================= " + filePath);
//                        logFramePresenter.uploadLogFrameFromExcel(filePath);
//                    }
//                });
    }

    @Override
    public void onResume() {
        super.onResume();
        /* get all logframes from the database*/
        logFramePresenter.resume();
        //logFramePresenter.readSharedOrganizations();
    }

    /**
     * the onCreateView method is called when fragment should create its View object
     * hierarchy either dynamically or via XML Layout inflation.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.logframe_list_fragment, container, false);
    }

    /**
     * this event is triggered soon after on CreateView(). onViewCreated is called if the
     * view is returned from onCreateView() is non-null. Any view setup should occur here. e.g.
     * view lookups and attaching view listeners.
     */
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {

        /* create data structures */
        initDataStructures();

        /* create logframe menu */
        initLogframeView(view);

        /* draggable floating button */
        initDraggableFAB(view);

        /* show the back arrow button */
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        activity.setSupportActionBar(toolbar);
        Objects.requireNonNull(activity.getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        Objects.requireNonNull(activity.getSupportActionBar()).setDisplayShowHomeEnabled(true);
    }

    private void initDataStructures() {

        /* logframe data structures */
        List<cTreeModel> logFrameTreeModels = new ArrayList<>();
        /* shared preference organizations */
        //sharedOrganizations = new ArrayList<>();

        logFramePresenter = new cLogFramePresenterImpl(
                cThreadExecutorImpl.getInstance(),
                cMainThreadImpl.getInstance(),
                this,
                new cSharedPreferenceFirestoreRepositoryImpl(requireContext()),
                new cLogFrameFirestoreRepositoryImpl(getContext()));

        // setup recycler view adapter
        logFrameRecyclerViewAdapter = new cLogFrameAdapter(getActivity(), this,
                logFrameTreeModels);

        activity = ((AppCompatActivity) getActivity());
    }

    private void initLogframeView(View view) {
        includeProgressBar = view.findViewById(R.id.includeProgressBar);

        RecyclerView logFrameRecyclerView = view.findViewById(R.id.logFrameRecyclerView);
        logFrameRecyclerView.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);

        logFrameRecyclerView.setAdapter(logFrameRecyclerViewAdapter);
        logFrameRecyclerView.setLayoutManager(llm);

        // set the title
        CollapsingToolbarLayout collapsingToolbarLayout;
        collapsingToolbarLayout = view.findViewById(R.id.collapsingToolbarLayout);
        collapsingToolbarLayout.setTitle(
                requireContext().getResources().getText(logframe_list_title));
    }

    private void initDraggableFAB(View view) {
        view.findViewById(R.id.logFrameFAB).setOnClickListener(v -> {
            cCustomQuickAction quickAction = cCustomQuickAction.Builder(v);
            // set the adapter
            quickAction.setAdapter(new cLogFrameAdapter.cQAAdapter(requireContext()));

            // set the number of columns ( setting -1 for auto )
            quickAction.setNumColumns(-1);
            quickAction.setOnClickListener((dialog, position) -> {
                switch (position) {
                    case 0:
                        cLogFrameModel logFrameModel = new cLogFrameModel();
                        onClickCreateLogFrame(logFrameModel);
                        break;

                    case 1:
                        logFramePresenter.uploadLogFrameFromExcel(null);

//                        if (ContextCompat.checkSelfPermission(requireActivity(),
//                                Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
//                            //Toast.makeText(requireActivity(), "You have already granted this permission!",Toast.LENGTH_SHORT).show();
//
//                            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
//                            intent.setType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
//                            //intent.setType("*/*");
//                            intent.addCategory(Intent.CATEGORY_OPENABLE);
//                            launchUploadActivity.launch(intent);
//
//                        } else {
//                            requestStoragePermission();
//                        }

                        break;

                    default:
                        break;
                }
                dialog.dismiss();
            });

            // finally show the view
            quickAction.show();
        });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getActivity().getMenuInflater().inflate(R.menu.drawer_menu_main, menu);
        inflater.inflate(R.menu.home_toolbar_menu, menu);

        //getting the search view from the menu
        MenuItem toolBarMenu = menu.findItem(R.id.searchItem);

        /* getting search manager from system service */
        SearchManager searchManager = (SearchManager) requireActivity().
                getSystemService(Context.SEARCH_SERVICE);
        /* getting the search view */
        SearchView searchView = (SearchView) toolBarMenu.getActionView();
        /* you can put a hint for the search input field */
        searchView.setQueryHint("Search LogFrames...");
        searchView.setSearchableInfo(Objects.requireNonNull(searchManager).
                getSearchableInfo(requireActivity().getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);
        /* by setting it true we are making it iconified
           so the search input will show up after taping the search iconified
           if you want to make it visible all the time make it false
         */
        //searchView.setIconifiedByDefault(true);
        search(searchView);

        /* end */

        super.onCreateOptionsMenu(menu, inflater);
    }

    private void search(SearchView searchView) {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                logFrameRecyclerViewAdapter.getFilter().filter(query);
                return false;
            }
        });
    }

    //=========== these functions are called in the adapter when clicking on the buttons ===========


    @Override
    public void onClickUploadLogFrame(File excelFile) {

    }

    @Override
    public void onClickCreateLogFrame(cLogFrameModel logFrameModel) {
        createAlertDialog(null, logFrameModel, true);
    }

    @Override
    public void onClickCreateSubLogFrame(String logFrameID, cLogFrameModel logFrameModel) {
        createAlertDialog(logFrameID, logFrameModel, false);
    }

    @Override
    public void onClickUpdateLogFrame(int position, cLogFrameModel logFrameModel) {
        updateAlertDialog(position, logFrameModel);
    }

    @Override
    public void onClickDeleteLogFrame(int position, String logframeID) {
        int resID = R.string.fa_delete;
        String title = "Delete Logframe.";
        String message = "Are you sure you want to delete this logframe ?";

        deleteAlertDialog(resID, title, message, position, logframeID);
    }

    @Override
    public void onClickDeleteSubLogFrame(int position, String logframeID) {

    }

    @Override
    public void onClickBMBLogFrame(int menuIndex, cLogFrameModel logFrameModel) {
        NavDirections action;
        switch (menuIndex) {
            case 0: // Impact Fragment
                /* navigate from logframe to impact */
                action = cLogFrameFragmentDirections.
                        actionCLogFrameFragmentToCImpactFragment(logFrameModel);
                Navigation.findNavController(requireView()).navigate(action);
                break;
            case 1: // Outcome Fragment
                /* navigate from logframe to outcome */
                action = cLogFrameFragmentDirections.
                        actionCLogFrameFragmentToCOutcomeFragment(logFrameModel);
                Navigation.findNavController(requireView()).navigate(action);
                break;
            case 2: // Output Fragment
                /* navigate from logframe to output */
                action = cLogFrameFragmentDirections.
                        actionCLogFrameFragmentToCOutputFragment(logFrameModel);
                Navigation.findNavController(requireView()).navigate(action);
                break;
            case 3: // Activity Fragment
                /* navigate from logframe to activity */
                action = cLogFrameFragmentDirections.
                        actionCLogFrameFragmentToCActivityFragment(logFrameModel);
                Navigation.findNavController(requireView()).navigate(action);
                break;
            case 4: // Input Fragment
                /* navigate from logframe to input */
                action = cLogFrameFragmentDirections.
                        actionCLogFrameFragmentToCInputFragment(logFrameModel);
                Navigation.findNavController(requireView()).navigate(action);
                break;
//            case 5: // Key Performance Question (KPQ) Fragment
//                /* navigate from logframe to question */
//                action = cLogFrameFragmentDirections.
//                        actionCLogFrameFragmentToCQuestionFragment(logFrameID);
//                Navigation.findNavController(requireView()).navigate(action);
//                break;
//            case 6: // Key Performance Indicator (KPI) Fragment
//                /* navigate from logframe to indicator */
//                action = cLogFrameFragmentDirections.
//                        actionCLogFrameFragmentToCIndicatorFragment(logFrameID);
//                Navigation.findNavController(requireView()).navigate(action);
//                break;
//            case 7: // RAID Log Fragment
//                /* navigate from logframe to RAID */
//                action = cLogFrameFragmentDirections.
//                        actionCLogFrameFragmentToCRaidLogFragment(logFrameID);
//                Navigation.findNavController(requireView()).navigate(action);
//                break;
//            case 8: // AWPB Fragment
//                /* navigate from logframe to AWPB */
//                action = cLogFrameFragmentDirections.
//                        actionCLogFrameFragmentToCAWPBFragment(logFrameID);
//                Navigation.findNavController(requireView()).navigate(action);
//                break;
//            case 9: // Book Keeping Fragment
//                /* navigate from logframe to book keeping */
//                action = cLogFrameFragmentDirections.
//                        actionCLogFrameFragmentToCBookKeepingFragment(logFrameID);
//                Navigation.findNavController(requireView()).navigate(action);
//                break;
//            case 10: // Monitoring Fragment
//                /* navigate from logframe to monitoring */
//                action = cLogFrameFragmentDirections.
//                        actionCLogFrameFragmentToCMonitoringFragment(logFrameID);
//                Navigation.findNavController(requireView()).navigate(action);
//                break;
//            case 11: // Evaluation Fragment
//                /* navigate from logframe to evaluation */
//                action = cLogFrameFragmentDirections.
//                        actionCLogFrameFragmentToCEvaluationFragment(logFrameID);
//                Navigation.findNavController(requireView()).navigate(action);
//                break;
            default:
                break;
        }
    }

    //========================== these functions give feedback to the user =========================

    @Override
    public void onRetrieveLogFramesCompleted(List<cTreeModel> treeModels) {
        /* update logframe adapters */
        try {
            logFrameRecyclerViewAdapter.setTreeModel(treeModels);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onRetrieveLogFramesFailed(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLogFrameCreated(cLogFrameModel frameModel, String msg) {
        try {
            int parentIndex = logFrameRecyclerViewAdapter.getMaxParentIndex();
            cTreeModel treeModel = new cTreeModel(parentIndex, -1, 0, frameModel);
            logFrameRecyclerViewAdapter.addData(treeModel);
            Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSubLogFrameCreated(cLogFrameModel logFrameModel, String msg) {

    }

    @Override
    public void onLogFrameCreateFailed(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSubLogFrameCreateFailed(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLogFrameUpdated(cLogFrameModel frameModel, int position, String msg) {
        cTreeModel treeModel = new cTreeModel(frameModel);
        logFrameRecyclerViewAdapter.notifyTreeModelUpdated(treeModel, position);
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLogFrameUpdateFailed(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLogFrameDeleted(int position, String msg) {
        try {
            logFrameRecyclerViewAdapter.notifyTreeModelDeleted(position);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSubLogFrameDeleted(int position, String msg) {
        try {
            logFrameRecyclerViewAdapter.notifyTreeModelDeleted(position);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLogFrameDeleteFailed(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSubLogFrameDeleteFailed(String msg) {

    }

    @Override
    public void onUploadLogFrameCompleted(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();

        // reload logframes
        logFramePresenter.readLogFrames();
    }

    @Override
    public void onUploadLogFrameFailed(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    //========================= these functions show and hide progress bar =========================

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

    //======================= these function show the forms to capture data ========================

    /**
     * create/add a logframe
     *
     * @param logFrameID    logframe identification
     * @param logFrameModel logframe model
     * @param isLogFrame    is this a parent or child logframe
     */
    private void createAlertDialog(String logFrameID, cLogFrameModel logFrameModel, boolean isLogFrame) {
        /* inflate the logframe model */
        LayoutInflater inflater = this.getLayoutInflater();
        View createView = inflater.inflate(R.layout.logframe_update, null);
        /* instantiates create views */
        TextView textViewTitle = createView.findViewById(R.id.textViewTitle);
        TextView textViewOrganization = createView.findViewById(R.id.textViewOrganization);
        cSingleSpinnerSearch singleSpinnerSearchOrg = createView.findViewById(R.id.singleSpinnerSearchOrg);
        AppCompatEditText editTextName = createView.findViewById(R.id.editTextName);
        AppCompatEditText editTextDescription = createView.findViewById(R.id.editTextDescription);
        AppCompatEditText startDateEditText = createView.findViewById(R.id.editTextStartDate);
        AppCompatEditText endDateEditText = createView.findViewById(R.id.editTextEndDate);
        TextView datePickerIcon = createView.findViewById(R.id.textViewDatePicker);

        /* set a title of the create view */
        textViewTitle.setText(requireContext().getResources().getText(
                R.string.logframe_create_title));

        /* populate the logical model with the create views */
        /* 1. create selection dialog box for organizations */
        List<cKeyPairBoolData> keyPairBoolOrgs = new ArrayList<>();
//        for (int i = 0; i < sharedOrganizations.size(); i++) {
//            cKeyPairBoolData idNameBool = new cKeyPairBoolData();
//            //--idNameBool.setId(sharedOrganizations.get(i).getOrganizationID());
//            idNameBool.setName(sharedOrganizations.get(i).getName());
//            idNameBool.setObject(sharedOrganizations.get(i));
//            idNameBool.setSelected(false);
//
//            keyPairBoolOrgs.add(idNameBool);
//        }

        // called when click spinner
        singleSpinnerSearchOrg.setItem(keyPairBoolOrgs, -1, item -> {
            /* assign selected organization name to the view */
            textViewOrganization.setText(item.getName());
            /* modify the organization ID for the logframe model */

            //logFrameModel.setOrganizationID((int) item.getId());
            //logFrameModel.getOrganizationModel().setName(item.getName());
        });

        /* 4. set start and end date */
        datePickerIcon.setTypeface(null, Typeface.NORMAL);
        datePickerIcon.setTypeface(cFontManager.getTypeface(requireActivity(),
                cFontManager.FONTAWESOME));
        datePickerIcon.setTextColor(requireActivity().getColor(R.color.colorPrimaryDark));
        datePickerIcon.setText(requireActivity().getResources().getString(R.string.fa_calendar));
        datePickerIcon.setOnClickListener(view -> {
            // date range picker
            MaterialDatePicker.Builder<Pair<Long, Long>> builder = MaterialDatePicker.Builder.
                    dateRangePicker();
            MaterialDatePicker<Pair<Long, Long>> picker = builder.build();

            picker.addOnCancelListener(dialogInterface ->
                    Log.d(TAG, "DatePicker Activity Dialog was cancelled"));

            picker.addOnPositiveButtonClickListener(
                    selection -> {
                        /* get start and end date from picker */
                        assert selection.first != null;
                        assert selection.second != null;
                        Date startDate = new Date(selection.first);
                        Date endDate = new Date(selection.second);
                        /* populate start and end dates on the view */
                        startDateEditText.setText(ssdf.format(startDate));
                        endDateEditText.setText(ssdf.format(endDate));

                        /* modify start and end dates of logframe model */
                        logFrameModel.setStartDate(Timestamp.valueOf(tsdf.format(startDate)));
                        logFrameModel.setEndDate(Timestamp.valueOf(tsdf.format(endDate)));

                        Log.d(TAG, "DatePicker Activity Date String =" +
                                picker.getHeaderText() + " Date epoch values :: " +
                                selection.first + " :: to :: " + selection.second);
                    });

            picker.addOnNegativeButtonClickListener(view1 ->
                    Log.d(TAG, "DatePicker Activity Dialog Negative Button was clicked"));

            picker.show(getParentFragmentManager(), picker.toString());
        });

        /* create or cancel action */
        MaterialAlertDialogBuilder alertDialogBuilder =
                new MaterialAlertDialogBuilder(requireActivity(), R.style.AlertDialogTheme);
        alertDialogBuilder.setPositiveButton(requireContext().getResources().getText(
                R.string.Save), (dialogInterface, i) -> {
            /* 2. set name of the logframe */
            logFrameModel.setName(Objects.requireNonNull(editTextName.getText()).toString());
            /* 3. set description of the logframe */
            logFrameModel.setDescription(Objects.requireNonNull(editTextDescription.getText()).
                    toString());

            if (isLogFrame) {
                logFramePresenter.createLogFrameModel(logFrameModel);
            } else {
                logFramePresenter.createSubLogFrameModel(logFrameID, logFrameModel);
            }
        });

        alertDialogBuilder.setNegativeButton(requireContext().getResources().getText(
                R.string.Cancel), (dialogInterface, i) -> {

        })
                .setView(createView)
                .show();
    }

    /**
     * update a logframe
     *
     * @param position      position
     * @param logFrameModel logframe model
     */
    private void updateAlertDialog(int position, cLogFrameModel logFrameModel) {
        /* inflates */
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.logframe_update, null);

        /* instantiate update views */
        TextView textViewTitle = dialogView.findViewById(R.id.textViewTitle);
        TextView textViewOrganization = dialogView.findViewById(R.id.textViewOrganization);
        cSingleSpinnerSearch singleSpinnerSearchOrg = dialogView.findViewById(
                R.id.singleSpinnerSearchOrg);
        AppCompatEditText editTextName = dialogView.findViewById(R.id.editTextName);
        AppCompatEditText editTextDescription = dialogView.findViewById(R.id.editTextDescription);
        AppCompatEditText startDateEditText = dialogView.findViewById(R.id.editTextStartDate);
        AppCompatEditText endDateEditText = dialogView.findViewById(R.id.editTextEndDate);
        TextView datePickerIcon = dialogView.findViewById(R.id.textViewDatePicker);

        /* populate the fields to be updated */
        textViewTitle.setText(requireContext().getResources().getText(
                R.string.logframe_update_title));
        //textViewOrganization.setText(logFrameModel.getOrganizationModel().getName());
        editTextName.setText(logFrameModel.getName());
        editTextDescription.setText(logFrameModel.getDescription());
        startDateEditText.setText(ssdf.format(logFrameModel.getStartDate()));
        endDateEditText.setText(ssdf.format(logFrameModel.getEndDate()));

        /* 1. set own organization */
        List<cKeyPairBoolData> keyPairBoolOrgs = new ArrayList<>();
//        for (int i = 0; i < sharedOrganizations.size(); i++) {
//            cKeyPairBoolData idNameBool = new cKeyPairBoolData();
//            //--idNameBool.setId(sharedOrganizations.get(i).getOrganizationID());
//            idNameBool.setName(sharedOrganizations.get(i).getName());
//            idNameBool.setObject(sharedOrganizations.get(i));
//
//            /* get the current organization ID */
//            long organizationID = 0;//logFrameModel.getOrganizationModel().getOrganizationID();
//
//            if (true/*(sharedOrganizations.get(i).getOrganizationID() == organizationID)*/) {
//                idNameBool.setSelected(true);
//                /* initialize organization ID for the logframe model */
//                logFrameModel.setOrganizationID(organizationID);
//            } else {
//                idNameBool.setSelected(false);
//            }
//
//            keyPairBoolOrgs.add(idNameBool);
//        }

        // called when click spinner
        singleSpinnerSearchOrg.setItem(keyPairBoolOrgs, -1, item -> {
            /* assign selected organization name to the view */
            textViewOrganization.setText(item.getName());
            /* modify the organization ID for the logframe model */
            //logFrameModel.setOrganizationID((int) item.getId());
            //logFrameModel.getOrganizationModel().setName(item.getName());
        });

        /* 3. set start and end dates */
        datePickerIcon.setTypeface(null, Typeface.NORMAL);
        datePickerIcon.setTypeface(cFontManager.getTypeface(requireActivity(),
                cFontManager.FONTAWESOME));
        datePickerIcon.setTextColor(requireActivity().getColor(R.color.colorPrimaryDark));
        datePickerIcon.setText(requireActivity().getResources().getString(R.string.fa_calendar));
        datePickerIcon.setOnClickListener(view -> {
            // date range picker
            MaterialDatePicker.Builder<Pair<Long, Long>> builder = MaterialDatePicker.Builder.
                    dateRangePicker();
            MaterialDatePicker<Pair<Long, Long>> picker = builder.build();

            picker.addOnCancelListener(dialogInterface ->
                    Log.d(TAG, "DatePicker Activity Dialog was cancelled"));

            picker.addOnPositiveButtonClickListener(
                    selection -> {
                        /* get start and end date from picker */
                        assert selection.first != null;
                        assert selection.second != null;
                        Date startDate = new Date(selection.first);
                        Date endDate = new Date(selection.second);

                        /* populate start and end dates on the view */
                        startDateEditText.setText(ssdf.format(startDate));
                        endDateEditText.setText(ssdf.format(endDate));

                        /* modify start and end dates of logframe model */
                        logFrameModel.setStartDate(Timestamp.valueOf(tsdf.format(startDate)));
                        logFrameModel.setEndDate(Timestamp.valueOf(tsdf.format(endDate)));

                        Log.d(TAG, "DatePicker Activity Date String =" +
                                picker.getHeaderText() + " Date epoch values :: " +
                                selection.first + " :: to :: " + selection.second);
                    });

            picker.addOnNegativeButtonClickListener(view1 ->
                    Log.d(TAG, "DatePicker Activity Dialog Negative Button was clicked"));

            picker.show(getParentFragmentManager(), picker.toString());
        });

        /* save or cancel actions */
        MaterialAlertDialogBuilder alertDialogBuilder =
                new MaterialAlertDialogBuilder(requireActivity(), R.style.AlertDialogTheme);
        alertDialogBuilder.setPositiveButton(requireContext().getResources().getText(
                R.string.Save), (dialogInterface, i) -> {
            /* 2. update the remaining view attributes to the logical model */
            logFrameModel.setName(Objects.requireNonNull(editTextName.getText()).toString());
            logFrameModel.setDescription(Objects.requireNonNull(editTextDescription.getText()).
                    toString());

            logFramePresenter.updateLogFrame(logFrameModel, position);

        });

        alertDialogBuilder.setNegativeButton(requireContext().getResources().getText(
                R.string.Cancel), (dialogInterface, i) -> {

        })
                .setView(dialogView)
                .show();
    }

    /**
     * delete a logframe
     *
     * @param resID      resource identification
     * @param title      title
     * @param message    message
     * @param position   position
     * @param logFrameID logframe identification
     */
    private void deleteAlertDialog(int resID, String title, String message, int position,
                                   String logFrameID) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                requireContext());

        // setting icon to dialog
        TextDrawable faIcon = new TextDrawable(requireContext());
        faIcon.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
        faIcon.setTextAlign(Layout.Alignment.ALIGN_CENTER);
        faIcon.setTypeface(cFontManager.getTypeface(requireContext(), cFontManager.FONTAWESOME));
        faIcon.setText(requireContext().getResources().getText(resID));
        faIcon.setTextColor(requireContext().getColor(R.color.colorAccent));
        alertDialogBuilder.setIcon(faIcon);

        // set title
        alertDialogBuilder.setTitle(title);

        // set dialog message
        alertDialogBuilder
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton(requireContext().getResources().getText(
                        R.string.Yes), (dialog, id) -> {
                    logFramePresenter.deleteLogFrameModel(logFrameID, position);
                    dialog.dismiss();
                })
                .setNegativeButton(requireContext().getResources().getText(
                        R.string.No), (dialog, id) -> {
                    // if this button is clicked, just close
                    dialog.cancel();
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }

//    private void requestStoragePermission() {
//        if (ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(),
//                Manifest.permission.READ_EXTERNAL_STORAGE)) {
//            new android.app.AlertDialog.Builder(getContext())
//                    .setTitle("Permission needed")
//                    .setMessage("This permission is needed because of this and that")
//                    .setPositiveButton("ok", (dialog, which) ->
//                            ActivityCompat.requestPermissions(requireActivity(),
//                                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
//                                    STORAGE_PERMISSION_CODE))
//                    .setNegativeButton("cancel", (dialog, which) -> dialog.dismiss())
//                    .create().show();
//        } else {
//            ActivityCompat.requestPermissions(requireActivity(),
//                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
//                    STORAGE_PERMISSION_CODE);
//        }
//    }
//
//    @Override
//    public void onRequestPermissionsResult(Context context, int requestCode,
//                                           @NonNull String[] permissions,
//                                           @NonNull int[] grantResults) {
//        if (requestCode == STORAGE_PERMISSION_CODE) {
//            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                Toast.makeText(context, "Permission GRANTED", Toast.LENGTH_SHORT).show();
//            } else {
//                Toast.makeText(context, "Permission DENIED", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }
}