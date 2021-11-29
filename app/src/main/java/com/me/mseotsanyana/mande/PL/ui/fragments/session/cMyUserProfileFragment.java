package com.me.mseotsanyana.mande.PL.ui.fragments.session;

import android.graphics.Color;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.me.mseotsanyana.mande.BLL.executor.Impl.cThreadExecutorImpl;
import com.me.mseotsanyana.mande.BLL.model.session.cUserProfileModel;
import com.me.mseotsanyana.mande.DAL.ìmpl.firestore.session.cSharedPreferenceFirestoreRepositoryImpl;
import com.me.mseotsanyana.mande.DAL.ìmpl.firestore.session.cUserProfileFirestoreRepositoryImpl;
import com.me.mseotsanyana.mande.PL.presenters.session.Impl.cMyUserProfilePresenterImpl;
import com.me.mseotsanyana.mande.PL.presenters.session.iMyUserProfilePresenter;
import com.me.mseotsanyana.mande.R;
import com.me.mseotsanyana.mande.UTIL.cConstant;
import com.me.mseotsanyana.mande.cMainThreadImpl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;


public class cMyUserProfileFragment extends Fragment implements iMyUserProfilePresenter.View{
    private static final String TAG = cMyUserProfileFragment.class.getSimpleName();
    private static final SimpleDateFormat ssdf = cConstant.SHORT_FORMAT_DATE;

    private Toolbar toolbar;

    private iMyUserProfilePresenter userProfilePresenter;
    private cUserProfileModel userProfileModel;

    private TextView nameTextView, surnameTextView, designationTextView, phoneTextView,
            emailTextView, websiteTextView, locationTextView, createdonTextView, modifiedonTextView;
    private EditText nameEditText, surnameEditText, designationEditText, phoneEditText,
            websiteEditText, locationEditText;

    private Button nameButtonEdit, surnameButtonEdit, designationButtonEdit,
            phoneButtonEdit, websiteButtonEdit, locationButtonEdit;

    private AppCompatActivity activity;

    public cMyUserProfileFragment() {
    }

    public static cMyUserProfileFragment newInstance() {
        return new cMyUserProfileFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        /* get a user profile from the database */
        userProfilePresenter.readUserProfile();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.session_user_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        /* create data structures */
        initDataStructures();

        /* initialize appBar Layout */
        initAppBarLayout(view);

        /* create RecyclerView */
        initUserProfileViews(view);

        /* show the back arrow button */
        activity.setSupportActionBar(toolbar);
        Objects.requireNonNull(activity.getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        Objects.requireNonNull(activity.getSupportActionBar()).setDisplayShowHomeEnabled(true);
    }

    private void initDataStructures() {

        userProfilePresenter = new cMyUserProfilePresenterImpl(
                cThreadExecutorImpl.getInstance(),
                cMainThreadImpl.getInstance(),
                this,
                new cSharedPreferenceFirestoreRepositoryImpl(getContext()),
                new cUserProfileFirestoreRepositoryImpl(getContext()));

        activity = ((AppCompatActivity) getActivity());
    }

    private void initAppBarLayout(View view){
        toolbar = view.findViewById(R.id.toolbar);
        //TextView appName = view.findViewById(R.id.appName);
        //logFrameName = view.findViewById(R.id.subtitle);
//        appName.setText(R.string.app_name);
        CollapsingToolbarLayout collapsingToolbarLayout =
                view.findViewById(R.id.collapsingToolbarLayout);
        collapsingToolbarLayout.setContentScrimColor(Color.WHITE);
        //collapsingToolbarLayout.setTitle("Organizations");
    }

    private void initUserProfileViews(View view) {
        //LinearLayout includeProgressBar = view.findViewById(R.id.includeProgressBar);
        //ImageView userImageView = view.findViewById(R.id.userImageView);

        nameTextView = view.findViewById(R.id.nameTextView);
        surnameTextView = view.findViewById(R.id.surnameTextView);
        designationTextView = view.findViewById(R.id.designationTextView);
        phoneTextView = view.findViewById(R.id.phoneTextView);
        emailTextView = view.findViewById(R.id.emailTextView);
        websiteTextView = view.findViewById(R.id.websiteTextView);
        locationTextView = view.findViewById(R.id.locationTextView);
        createdonTextView = view.findViewById(R.id.createdonTextView);
        modifiedonTextView = view.findViewById(R.id.modifiedonTextView);

        nameEditText = view.findViewById(R.id.nameEditText);
        surnameEditText = view.findViewById(R.id.surnameEditText);
        designationEditText = view.findViewById(R.id.designationEditText);
        phoneEditText = view.findViewById(R.id.phoneEditText);
        websiteEditText = view.findViewById(R.id.websiteEditText);
        locationEditText = view.findViewById(R.id.locationEditText);

        /* name */
        nameButtonEdit = view.findViewById(R.id.nameButtonEdit);
        nameButtonEdit.setOnClickListener(nameView -> {
            if(nameTextView.getVisibility() == View.VISIBLE) {
                nameTextView.setVisibility(View.GONE);
                nameEditText.setVisibility(View.VISIBLE);

                nameEditText.setText(nameTextView.getText().toString().trim());

                nameButtonEdit.setCompoundDrawablesWithIntrinsicBounds(
                        R.drawable.baseline_save_black_24dp, 0, 0, 0);
            }else{
                nameTextView.setVisibility(View.VISIBLE);
                nameEditText.setVisibility(View.GONE);

                nameTextView.setText(nameEditText.getText().toString());

                userProfileModel.setName(nameTextView.getText().toString().trim());
                userProfileModel.setModifiedDate(new Date());
                userProfilePresenter.updateUserProfile(userProfileModel);

                nameButtonEdit.setCompoundDrawablesWithIntrinsicBounds(
                        R.drawable.baseline_create_black_24dp, 0, 0, 0);
            }
        });

        /* surname */
        surnameButtonEdit = view.findViewById(R.id.surnameButtonEdit);
        surnameButtonEdit.setOnClickListener(nameView -> {
            if(surnameTextView.getVisibility() == View.VISIBLE) {
                surnameTextView.setVisibility(View.GONE);
                surnameEditText.setVisibility(View.VISIBLE);

                surnameEditText.setText(surnameTextView.getText().toString().trim());

                surnameButtonEdit.setCompoundDrawablesWithIntrinsicBounds(
                        R.drawable.baseline_save_black_24dp, 0, 0, 0);
            }else{
                surnameTextView.setVisibility(View.VISIBLE);
                surnameEditText.setVisibility(View.GONE);

                surnameTextView.setText(surnameEditText.getText().toString());

                userProfileModel.setSurname(surnameTextView.getText().toString().trim());
                userProfileModel.setModifiedDate(new Date());
                userProfilePresenter.updateUserProfile(userProfileModel);

                surnameButtonEdit.setCompoundDrawablesWithIntrinsicBounds(
                        R.drawable.baseline_create_black_24dp, 0, 0, 0);
            }
        });

        /* designation */
        designationButtonEdit = view.findViewById(R.id.designationButtonEdit);
        designationButtonEdit.setOnClickListener(nameView -> {
            if(designationTextView.getVisibility() == View.VISIBLE) {
                designationTextView.setVisibility(View.GONE);
                designationEditText.setVisibility(View.VISIBLE);

                designationEditText.setText(designationTextView.getText().toString().trim());

                designationButtonEdit.setCompoundDrawablesWithIntrinsicBounds(
                        R.drawable.baseline_save_black_24dp, 0, 0, 0);
            }else{
                designationTextView.setVisibility(View.VISIBLE);
                designationEditText.setVisibility(View.GONE);

                designationTextView.setText(designationEditText.getText().toString());

                userProfileModel.setModifiedDate(new Date());
                userProfileModel.setDesignation(designationTextView.getText().toString().trim());
                userProfilePresenter.updateUserProfile(userProfileModel);

                designationButtonEdit.setCompoundDrawablesWithIntrinsicBounds(
                        R.drawable.baseline_create_black_24dp, 0, 0, 0);
            }
        });

        /* website */
        websiteButtonEdit = view.findViewById(R.id.websiteButtonEdit);
        websiteButtonEdit.setOnClickListener(nameView -> {
            if(websiteTextView.getVisibility() == View.VISIBLE) {
                websiteTextView.setVisibility(View.GONE);
                websiteEditText.setVisibility(View.VISIBLE);

                websiteEditText.setText(websiteTextView.getText().toString().trim());

                websiteButtonEdit.setCompoundDrawablesWithIntrinsicBounds(
                        R.drawable.baseline_save_black_24dp, 0, 0, 0);
            }else{
                websiteTextView.setVisibility(View.VISIBLE);
                websiteEditText.setVisibility(View.GONE);

                websiteTextView.setText(websiteEditText.getText().toString());

                userProfileModel.setModifiedDate(new Date());
                userProfileModel.setWebsite(websiteTextView.getText().toString().trim());
                userProfilePresenter.updateUserProfile(userProfileModel);

                websiteButtonEdit.setCompoundDrawablesWithIntrinsicBounds(
                        R.drawable.baseline_create_black_24dp, 0, 0, 0);
            }
        });

        /* location */
        locationButtonEdit = view.findViewById(R.id.locationButtonEdit);
        locationButtonEdit.setOnClickListener(nameView -> {
            if(locationTextView.getVisibility() == View.VISIBLE) {
                locationTextView.setVisibility(View.GONE);
                locationEditText.setVisibility(View.VISIBLE);

                locationEditText.setText(locationTextView.getText().toString().trim());

                locationButtonEdit.setCompoundDrawablesWithIntrinsicBounds(
                        R.drawable.baseline_save_black_24dp, 0, 0, 0);
            }else{
                locationTextView.setVisibility(View.VISIBLE);
                locationEditText.setVisibility(View.GONE);

                locationTextView.setText(locationEditText.getText().toString());

//                cUserProfileModel userProfileModel = new cUserProfileModel(
//                        FirebaseAuth.getInstance().getUid(),
//                        nameTextView.getText().toString().trim(),
//                        surnameTextView.getText().toString().trim(),
//                        designationTextView.getText().toString().trim(),
//                        phoneTextView.getText().toString().trim(),
//                        emailTextView.getText().toString().trim(),
//                        websiteTextView.getText().toString().trim(),
//                        locationTextView.getText().toString().trim());

                userProfileModel.setModifiedDate(new Date());
                userProfileModel.setLocation(locationTextView.getText().toString().trim());
                userProfilePresenter.updateUserProfile(userProfileModel);

                locationButtonEdit.setCompoundDrawablesWithIntrinsicBounds(
                        R.drawable.baseline_create_black_24dp, 0, 0, 0);
            }
        });

        /* phone */
        phoneButtonEdit = view.findViewById(R.id.phoneButtonEdit);
        phoneButtonEdit.setOnClickListener(nameView -> {
            if(phoneTextView.getVisibility() == View.VISIBLE) {
                phoneTextView.setVisibility(View.GONE);
                phoneEditText.setVisibility(View.VISIBLE);

                phoneEditText.setText(phoneTextView.getText().toString().trim());

                phoneButtonEdit.setCompoundDrawablesWithIntrinsicBounds(
                        R.drawable.baseline_save_black_24dp, 0, 0, 0);
            }else{
                phoneTextView.setVisibility(View.VISIBLE);
                phoneEditText.setVisibility(View.GONE);

                phoneTextView.setText(phoneEditText.getText().toString());

                userProfileModel.setModifiedDate(new Date());
                userProfileModel.setPhone(phoneTextView.getText().toString().trim());
                userProfilePresenter.updateUserProfile(userProfileModel);

                phoneButtonEdit.setCompoundDrawablesWithIntrinsicBounds(
                        R.drawable.baseline_dialpad_black_24dp, 0, 0, 0);
            }
        });

    }

    // READ

    @Override
    public void onReadUserProfileFailed(String msg) {

    }

    @Override
    public void onReadUserProfileSucceeded(cUserProfileModel userProfileModel) {
        if(userProfileModel != null) {
            this.userProfileModel = userProfileModel;
            this.nameTextView.setText(userProfileModel.getName());
            this.surnameTextView.setText(userProfileModel.getSurname());
            this.designationTextView.setText(userProfileModel.getDesignation());
            this.websiteTextView.setText(userProfileModel.getWebsite());
            this.locationTextView.setText(userProfileModel.getLocation());
            this.phoneTextView.setText(userProfileModel.getPhone());
            this.emailTextView.setText(userProfileModel.getEmail());
            this.createdonTextView.setText(ssdf.format(userProfileModel.getCreatedDate()));
            this.modifiedonTextView.setText(ssdf.format(userProfileModel.getModifiedDate()));
        }
    }

    // UPDATE

    @Override
    public void onUpdateUserProfileFailed(String msg) {

    }

    @Override
    public void onUpdateUserProfileSucceeded(String msg) {

    }

    // PRESENTER FUNCTIONS

    @Override
    public void showProgress() {
        //includeProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        //includeProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showError(String message) {

    }
}