package com.me.mseotsanyana.mande.BRBAC.PL;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.support.v4.app.Fragment;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.me.mseotsanyana.mande.BRBAC.BLL.cSessionManager;
import com.me.mseotsanyana.mande.Util.cInputValidation;
import com.me.mseotsanyana.mande.Util.cUtil;
import com.me.mseotsanyana.mande.PPMER.PL.cMainFragment;
import com.me.mseotsanyana.mande.BRBAC.BLL.cUserDomain;
import com.me.mseotsanyana.mande.BRBAC.BLL.cUserHandler;
import com.me.mseotsanyana.mande.R;

public class cLoginFragment extends Fragment {
    private cSessionManager session;

//    private OnFragmentInteractionListener mListener;
    private AppCompatButton loginButton;
    private TextInputLayout emailTextInputLayout, passwordTextInputLayout;
    private TextInputEditText emailTextInputEditText, passwordTextInputEditText;
    private TextView forgotPasswordTextView;
    private ProgressBar progressBar;
    private BottomNavigationView bottomNavigationView;
/*
    private TextInputLayout textInputLayoutEmail;
    private TextInputLayout textInputLayoutPassword;

    private TextInputEditText textInputEditTextEmail;
    private TextInputEditText textInputEditTextPassword;

    private AppCompatButton appCompatButtonLogin;
*/
    private cInputValidation inputValidation;

    private cUserHandler userHandler;

    // Required empty public constructor
    public cLoginFragment() {
        inputValidation = new cInputValidation(getContext());
    }

    public static cLoginFragment newInstance() {
        Bundle bundle = new Bundle();
        cLoginFragment fragment = new cLoginFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            // keeps global user information
            session  = new cSessionManager(getContext());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.home_login_fragment,container,false);

        userHandler = new cUserHandler(getActivity(), session);

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
                    progressBar.setVisibility(View.VISIBLE);
                    localLogin();

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
     * This method is to validate the input text fields and verify login credentials from SQLite
     */
    private void localLogin() {
        if (!inputValidation.isInputEditTextFilled(emailTextInputEditText, emailTextInputLayout, getString(R.string.error_message_email))) {
            return;
        }
        if (!inputValidation.isInputEditTextEmail(emailTextInputEditText, emailTextInputLayout, getString(R.string.error_message_email))) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(passwordTextInputEditText, passwordTextInputLayout, getString(R.string.error_message_email))) {
            return;
        }

        // check whether the user is in the database
        cUserDomain userDomain = userHandler.getUserByEmailPassword(emailTextInputEditText.getText().toString().trim(),
                passwordTextInputEditText.getText().toString().trim());

        //boolean isUser = userHandler.checkUser(emailTextInputEditText.getText().toString().trim());

        if (userDomain != null) {

            emptyInputEditText();

            session.setUserSession(getActivity(), userDomain);
            //Toast.makeText(getActivity(), "MEMBERSHIPS = "+sessionManager.getMemberships(), Toast.LENGTH_SHORT).show();
            //Toast.makeText(getActivity(), "ROLES = "+sessionManager.getLoggedInUserRoles().get(0).getName(), Toast.LENGTH_SHORT).show();

            pushFragment(cMainFragment.newInstance());

        } else {
            // Snack Bar to show success message that record is wrong
            Snackbar.make(getView(), getString(R.string.error_valid_email_password), Snackbar.LENGTH_LONG).show();
        }
    }


    /**
     * This method is to empty all input edit text
     */
    private void emptyInputEditText() {
        emailTextInputEditText.setText(null);
        passwordTextInputEditText.setText(null);
    }

    private void setupBottomNavigation() {
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
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
}