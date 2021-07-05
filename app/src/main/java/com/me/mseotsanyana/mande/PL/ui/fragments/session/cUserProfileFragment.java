package com.me.mseotsanyana.mande.PL.ui.fragments.session;

import android.graphics.Color;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.me.mseotsanyana.mande.BLL.executor.Impl.cThreadExecutorImpl;
import com.me.mseotsanyana.mande.BLL.model.session.cUserProfileModel;
import com.me.mseotsanyana.mande.DAL.ìmpl.realtime.session.cUserProfileFirebaseRepositoryImpl;
import com.me.mseotsanyana.mande.DAL.ìmpl.sqlite.session.cSessionManagerImpl;
import com.me.mseotsanyana.mande.PL.presenters.session.Impl.cUserProfilePresenterImpl;
import com.me.mseotsanyana.mande.PL.presenters.session.iUserProfilePresenter;
import com.me.mseotsanyana.mande.R;
import com.me.mseotsanyana.mande.cMainThreadImpl;

import java.util.Objects;


public class cUserProfileFragment extends Fragment implements iUserProfilePresenter.View{
    private static String TAG = cUserProfileFragment.class.getSimpleName();

    private Toolbar toolbar;

    private iUserProfilePresenter userProfilePresenter;

    private TextView nameTextView, surnameTextView, phoneTextView, locationTextView, emailTextView;
    private EditText nameEditText, surnameEditText, phoneEditText, locationEditText;

    private Button nameButtonEdit, surnameButtonEdit, phoneButtonEdit, locationButtonEdit;

    private AppCompatActivity activity;

    public cUserProfileFragment() {
    }

    public static cUserProfileFragment newInstance() {
        return new cUserProfileFragment();
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

        userProfilePresenter = new cUserProfilePresenterImpl(
                cThreadExecutorImpl.getInstance(),
                cMainThreadImpl.getInstance(),
                this,null
                /*new cSessionManagerImpl(getContext())*/,
                new cUserProfileFirebaseRepositoryImpl(getContext()));

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
        LinearLayout includeProgressBar = view.findViewById(R.id.includeProgressBar);
        ImageView userImageView = view.findViewById(R.id.userImageView);

        nameTextView = view.findViewById(R.id.nameTextView);
        surnameTextView = view.findViewById(R.id.surnameTextView);
        phoneTextView = view.findViewById(R.id.phoneTextView);
        locationTextView = view.findViewById(R.id.locationTextView);
        emailTextView = view.findViewById(R.id.emailTextView);

        nameEditText = view.findViewById(R.id.nameEditText);
        surnameEditText = view.findViewById(R.id.surnameEditText);
        locationEditText = view.findViewById(R.id.locationEditText);
        phoneEditText = view.findViewById(R.id.phoneEditText);

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

                cUserProfileModel userProfileModel = new cUserProfileModel(
                        FirebaseAuth.getInstance().getUid(),
                        nameTextView.getText().toString().trim(),
                        surnameTextView.getText().toString().trim(),
                        locationTextView.getText().toString().trim(),
                        phoneTextView.getText().toString().trim(),
                        emailTextView.getText().toString().trim()
                );

                userProfilePresenter.updateUserProfile(userProfileModel);

                nameButtonEdit.setCompoundDrawablesWithIntrinsicBounds(
                        R.drawable.baseline_create_black_24dp, 0, 0, 0);
            }
        });

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

                cUserProfileModel userProfileModel = new cUserProfileModel(
                        FirebaseAuth.getInstance().getUid(),
                        nameTextView.getText().toString().trim(),
                        surnameTextView.getText().toString().trim(),
                        locationTextView.getText().toString().trim(),
                        phoneTextView.getText().toString().trim(),
                        emailTextView.getText().toString().trim());

                userProfilePresenter.updateUserProfile(userProfileModel);

                surnameButtonEdit.setCompoundDrawablesWithIntrinsicBounds(
                        R.drawable.baseline_create_black_24dp, 0, 0, 0);
            }
        });

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

                cUserProfileModel userProfileModel = new cUserProfileModel(
                        FirebaseAuth.getInstance().getUid(),
                        nameTextView.getText().toString().trim(),
                        surnameTextView.getText().toString().trim(),
                        locationTextView.getText().toString().trim(),
                        phoneTextView.getText().toString().trim(),
                        emailTextView.getText().toString().trim());

                userProfilePresenter.updateUserProfile(userProfileModel);

                locationButtonEdit.setCompoundDrawablesWithIntrinsicBounds(
                        R.drawable.baseline_create_black_24dp, 0, 0, 0);
            }
        });

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

                cUserProfileModel userProfileModel = new cUserProfileModel(
                        FirebaseAuth.getInstance().getUid(),
                        nameTextView.getText().toString().trim(),
                        surnameTextView.getText().toString().trim(),
                        locationTextView.getText().toString().trim(),
                        phoneTextView.getText().toString().trim(),
                        emailTextView.getText().toString().trim());

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
            nameTextView.setText(userProfileModel.getName());
            surnameTextView.setText(userProfileModel.getSurname());
            locationTextView.setText(userProfileModel.getLocation());
            phoneTextView.setText(userProfileModel.getPhone());
            emailTextView.setText(userProfileModel.getEmail());
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