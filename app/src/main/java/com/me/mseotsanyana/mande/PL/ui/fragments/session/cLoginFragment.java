package com.me.mseotsanyana.mande.PL.ui.fragments.session;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.me.mseotsanyana.mande.BLL.executor.Impl.cThreadExecutorImpl;
import com.me.mseotsanyana.mande.DAL.model.session.cUserModel;
//import com.me.mseotsanyana.mande.DAL.storage.managers.cSessionManager;
import com.me.mseotsanyana.mande.DAL.ìmpl.session.cRoleRepositoryImpl;
import com.me.mseotsanyana.mande.DAL.ìmpl.session.cSessionManagerImpl;
import com.me.mseotsanyana.mande.DAL.ìmpl.session.cUserRepositoryImpl;
import com.me.mseotsanyana.mande.PL.presenters.session.Impl.cUserLoginPresenterImpl;
import com.me.mseotsanyana.mande.PL.presenters.session.iUserLoginPresenter;
import com.me.mseotsanyana.mande.PL.ui.fragments.logframe.cLogFrameFragment;
import com.me.mseotsanyana.mande.UTIL.cUtil;
import com.me.mseotsanyana.mande.R;
import com.me.mseotsanyana.mande.cMainThreadImpl;

public class cLoginFragment extends Fragment implements iUserLoginPresenter.View{
    //private cSessionManager session;

//    private OnFragmentInteractionListener mListener;
    private AppCompatButton loginButton;
    private TextInputLayout emailTextInputLayout, passwordTextInputLayout;
    private TextInputEditText emailTextInputEditText, passwordTextInputEditText;
    private TextView forgotPasswordTextView;
    private ProgressBar progressBar;
    private BottomNavigationView bottomNavigationView;

    private iUserLoginPresenter userLoginPresenter;


/*
    private TextInputLayout textInputLayoutEmail;
    private TextInputLayout textInputLayoutPassword;

    private TextInputEditText textInputEditTextEmail;
    private TextInputEditText textInputEditTextPassword;

    private AppCompatButton appCompatButtonLogin;
*/

//    private cUserHandler userHandler;



    // Required empty public constructor
    public cLoginFragment() {
        //inputValidation = new cInputValidation(getContext());
    }

    /*public static cLoginFragment newInstance(cSessionManager session) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("SESSION", session);
        cLoginFragment fragment = new cLoginFragment();
        fragment.setArguments(bundle);
        return fragment;
    }*/

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //if (getArguments() != null) {
            // keeps global user information
       //     session = (cSessionManager) getArguments().getParcelable("SESSION");
            //session = new cSessionManager(getActivity());
        //}

        userLoginPresenter = new cUserLoginPresenterImpl(
                cThreadExecutorImpl.getInstance(),
                cMainThreadImpl.getInstance(),
                this,
                new cSessionManagerImpl(getContext()),
                new cUserRepositoryImpl(getContext()),
                new cRoleRepositoryImpl(getContext()));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.home_login_fragment,container,false);

        //userHandler = new cUserHandler(getActivity(), session);

        initViews(view);
        setupBottomNavigation();

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void initViews(View view){
        loginButton               = (AppCompatButton)view.findViewById(R.id.loginButton);
        forgotPasswordTextView    = (TextView)view.findViewById(R.id.forgotPasswordTextView);
        emailTextInputLayout      = (TextInputLayout)view.findViewById(R.id.emailTextInputLayout);
        emailTextInputEditText    = (TextInputEditText)view.findViewById(R.id.emailTextInputEditText);
        passwordTextInputLayout   = (TextInputLayout)view.findViewById(R.id.passwordTextInputLayout);
        passwordTextInputEditText = (TextInputEditText)view.findViewById(R.id.passwordTextInputEditText);
        progressBar               = (ProgressBar)view.findViewById(R.id.progressBar);

        bottomNavigationView      = (BottomNavigationView) view.findViewById(R.id.bottom_navigation);
        bottomNavigationView.getMenu().getItem(0).setChecked(true);

        cUtil.setIcon(getContext(), bottomNavigationView, 0);
        cUtil.disableShiftMode(bottomNavigationView);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email    = emailTextInputEditText.getText().toString();
                String password = passwordTextInputEditText.getText().toString();

                if(!email.isEmpty() && !password.isEmpty()) {

                    userLoginPresenter.userLogin(email, password);
                } else {
                    Snackbar.make(getView(), "Fields are empty !", Snackbar.LENGTH_LONG).show();
                }
            }
        });

        forgotPasswordTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //FIXME
                //loadForgotPasswordFragment();
            }
        });
    }

    /**
     * This method is to empty all input edit text
     */
    private void emptyInputEditText() {
        emailTextInputEditText.setText(null);
        passwordTextInputEditText.setText(null);
    }

    private void setupBottomNavigation() {
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_create:
                        pushFragment(cRegisterFragment.newInstance());
                        return true;
                    case R.id.action_join:
                        pushFragment(cJoinFragment.newInstance());
                        return true;
                    case R.id.action_settings:
                        pushFragment(cSettingsFragment.newInstance());
                        return true;
                }
                return false;
            }
        });
    }

    /**
     * Method to push any fragment into given id.
     *
     * @param fragment An instance of Fragment to show into the given id.
     */
    protected void pushFragment(Fragment fragment) {
        if (fragment == null)
            return;

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        if (ft != null) {
            ft.replace(R.id.fragment_frame, fragment);
            ft.commit();
        }
    }

    @Override
    public void onUserLoginSucceeded(cUserModel userModel) {
        // get the session data
        //FIXME: load the MainFragment (MainMenuFragment) with set of menu items
        /* this populates the navigation menu and list of logframes with Boom menu */
        pushFragment(cLogFrameFragment.newInstance(userModel));
    }

    @Override
    public void onUserLoginFailed(String msg) {

    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showError(String message) {

    }

    /* getters and setters */

    @Override
    public TextInputLayout getEmailTextInputLayout() {
        return emailTextInputLayout;
    }

    @Override
    public TextInputLayout getPasswordTextInputLayout() {
        return passwordTextInputLayout;
    }

    @Override
    public TextInputEditText getEmailTextInputEditText() {
        return emailTextInputEditText;
    }

    @Override
    public TextInputEditText getPasswordTextInputEditText() {
        return passwordTextInputEditText;
    }

    @Override
    public TextView getForgotPasswordTextView() {
        return forgotPasswordTextView;
    }

    @Override
    public String getResourceString(int resourceID) {
        return getString(resourceID);
    }
}