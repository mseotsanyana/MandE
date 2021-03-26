package com.me.mseotsanyana.mande.PL.ui.fragments.session;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.me.mseotsanyana.mande.BLL.executor.Impl.cThreadExecutorImpl;
import com.me.mseotsanyana.mande.DAL.ìmpl.firebase.session.cUserFirebaseRepositoryImpl;
import com.me.mseotsanyana.mande.PL.presenters.session.Impl.cUserSignUpPresenterImpl;
import com.me.mseotsanyana.mande.PL.presenters.session.iUserSignUpPresenter;
import com.me.mseotsanyana.mande.R;
import com.me.mseotsanyana.mande.cMainThreadImpl;

import java.util.Objects;

public class cSignUpFragment extends Fragment implements iUserSignUpPresenter.View {
    private static String TAG = cSignUpFragment.class.getSimpleName();

    private EditText firstNameEditText, surnameEditText, emailEditText,
            passwordEditText, confirmPasswordEditText;
    private View progressBar;

    private iUserSignUpPresenter userSignUpPresenter;

    public cSignUpFragment() {
    }

    public static cSignUpFragment newInstance() {
        return new cSignUpFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userSignUpPresenter = new cUserSignUpPresenterImpl(
                cThreadExecutorImpl.getInstance(),
                cMainThreadImpl.getInstance(),
                this,
                new cUserFirebaseRepositoryImpl(getContext()));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.session_signup_fragment, container,
                false);

        initViews(view);

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void initViews(View view) {
        firstNameEditText = view.findViewById(R.id.firstNameEditText);
        surnameEditText = view.findViewById(R.id.surnameEditText);
        emailEditText = view.findViewById(R.id.emailEditText);
        passwordEditText = view.findViewById(R.id.passwordEditText);
        confirmPasswordEditText = view.findViewById(R.id.confirmPasswordEditText);
        progressBar = view.findViewById(R.id.progressBar);

        TextView loginTextView = view.findViewById(R.id.signTextView);
        TextView signUpTextView = view.findViewById(R.id.signUpTextView);

        signUpTextView.setText(R.string.user_sign_up);
        loginTextView.setText(R.string.user_sign_in);

        /* initial hide progress bar */
        hideProgress();

        /* login listener */
        loginTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavDirections action = cSignUpFragmentDirections.actionCSignUpFragmentToCLoginFragment();
                Navigation.findNavController(requireView()).navigate(action);
            }
        });

        /* sign up listener */
        signUpTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String firstName = Objects.requireNonNull(firstNameEditText.getText()).toString();
                String surname = Objects.requireNonNull(surnameEditText.getText()).toString();
                String email = Objects.requireNonNull(emailEditText.getText()).toString();
                String password = Objects.requireNonNull(passwordEditText.getText()).toString();
                String confirmPassword = Objects.requireNonNull(confirmPasswordEditText.getText()).toString();

                if (!email.isEmpty() && !password.isEmpty() && !confirmPassword.isEmpty()) {
                    userSignUpPresenter.createUserWithEmailAndPassword(email, password, firstName, surname);
                } else {
                    Snackbar.make(requireView(), "Fields are empty !", Snackbar.LENGTH_LONG).show();
                }
            }
        });

        /* login listener */
        loginTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NavDirections action = cSignUpFragmentDirections.actionCSignUpFragmentToCLoginFragment();
                Navigation.findNavController(requireView()).navigate(action);
            }
        });
    }

    @Override
    public void onUserSignUpSucceeded(String msg) {
        String email = Objects.requireNonNull(emailEditText.getText()).toString();
        NavDirections action = cSignUpFragmentDirections.actionCSignUpFragmentToCHomeFragment(email);
        Navigation.findNavController(requireView()).navigate(action);
    }

    @Override
    public void onUserSignUpFailed(String msg) {

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
    public EditText getEmailEditText() {
        return emailEditText;
    }

    @Override
    public EditText getPasswordEditText() {
        return passwordEditText;
    }

    @Override
    public EditText getConfirmPasswordEditText() {
        return confirmPasswordEditText;
    }

    @Override
    public String getResourceString(int resourceID) {
        return getString(resourceID);
    }
}
