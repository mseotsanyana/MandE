package com.me.mseotsanyana.mande.PL.ui.fragments.session;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Parcelable;
import android.util.Log;
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
import com.me.mseotsanyana.mande.DAL.ìmpl.session.cOrganizationRepositoryImpl;
import com.me.mseotsanyana.mande.DAL.ìmpl.session.cRoleRepositoryImpl;
import com.me.mseotsanyana.mande.DAL.ìmpl.session.cSessionManagerImpl;
import com.me.mseotsanyana.mande.DAL.ìmpl.session.cStatusRepositoryImpl;
import com.me.mseotsanyana.mande.DAL.ìmpl.session.cUserRepositoryImpl;
import com.me.mseotsanyana.mande.PL.presenters.session.Impl.cUserLoginPresenterImpl;
import com.me.mseotsanyana.mande.PL.presenters.session.iUserLoginPresenter;
import com.me.mseotsanyana.mande.PL.ui.fragments.logframe.cLogFrameFragment;
import com.me.mseotsanyana.mande.UTIL.cUtil;
import com.me.mseotsanyana.mande.R;
import com.me.mseotsanyana.mande.cMainThreadImpl;

import java.util.Objects;

public class cLoginFragment extends Fragment implements iUserLoginPresenter.View{
    private static String TAG = cLoginFragment.class.getSimpleName();


    //    private OnFragmentInteractionListener mListener;
    private AppCompatButton loginButton;
    private TextInputLayout emailTextInputLayout, passwordTextInputLayout;
    private TextInputEditText emailTextInputEditText, passwordTextInputEditText;
    private TextView forgotPasswordTextView;
    private ProgressBar progressBar;

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

        public static cLoginFragment newInstance(View view) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("SESSION", (Parcelable) view);
        cLoginFragment fragment = new cLoginFragment();
        fragment.setArguments(bundle);
        return fragment;
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
                new cUserRepositoryImpl(getContext()),
                new cOrganizationRepositoryImpl(getContext()),
                new cRoleRepositoryImpl(getContext()),
                new cStatusRepositoryImpl(getContext()),
                new cSessionManagerImpl(getContext()));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.home_login_fragment,container,false);

        //userHandler = new cUserHandler(getActivity(), session);

        initViews(view);
        //setupBottomNavigation();

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void initViews(View view){
        loginButton               = view.findViewById(R.id.loginButton);
        forgotPasswordTextView    = view.findViewById(R.id.forgotPasswordTextView);
        emailTextInputLayout      = view.findViewById(R.id.emailTextInputLayout);
        emailTextInputEditText    = view.findViewById(R.id.emailTextInputEditText);
        passwordTextInputLayout   = view.findViewById(R.id.passwordTextInputLayout);
        passwordTextInputEditText = view.findViewById(R.id.passwordTextInputEditText);
        progressBar               = view.findViewById(R.id.progressBar);

        //BottomNavigationView bottomNavigationView = (BottomNavigationView) view.findViewById(
        //        R.id.bottom_navigation);
        //bottomNavigationView.setVisibility(View.VISIBLE);

        //bottomNavigationView.getMenu().getItem(0).setChecked(true);

        //cUtil.setIcon(getContext(), bottomNavigationView, 0);
        //cUtil.disableShiftMode(bottomNavigationView);

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
/*
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
*/
    /**
     * Method to push any fragment into given id.
     *
     * @param fragment An instance of Fragment to show into the given id.
     */
    protected void pushFragment(Fragment fragment, String fragmentTag) {
        if (fragment == null)
            return;

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.add(R.id.fragment_frame, fragment, fragmentTag);
        ft.commit();
    }

    private void showFragment(String selectedFrag){
        if (Objects.requireNonNull(getFragmentManager()).findFragmentByTag(selectedFrag) != null) {
            //if the fragment exists, show it.
            getFragmentManager().beginTransaction().show(
                    Objects.requireNonNull(getFragmentManager().findFragmentByTag(selectedFrag))).
                    commit();
        } else {
            //if the fragment does not exist, add it to fragment manager.
            getFragmentManager().beginTransaction().add(
                    R.id.fragment_frame, new cLogFrameFragment(), selectedFrag).commit();
        }
        if (getFragmentManager().findFragmentByTag(TAG) != null) {
            //if the other fragment is visible, hide it.
            getFragmentManager().beginTransaction().hide(
                    Objects.requireNonNull(getFragmentManager().findFragmentByTag(TAG))).commit();
        }
    }

    @Override
    public void onUserLoginSucceeded(cUserModel userModel) {
        // get the session data
        //FIXME: load the MainFragment (MainMenuFragment) with set of menu items
        /* this populates the navigation menu and list of logframes with Boom menu */

        showFragment(cLogFrameFragment.class.getSimpleName());

        //pushFragment(new cLogFrameFragment(), cLogFrameFragment.class.getSimpleName());
        (getActivity().findViewById(R.id.bottom_navigation)).setVisibility(View.GONE);
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