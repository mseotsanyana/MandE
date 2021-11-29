package com.me.mseotsanyana.mande.PL.ui.fragments.session;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.me.mseotsanyana.mande.BLL.executor.Impl.cThreadExecutorImpl;
import com.me.mseotsanyana.mande.DAL.ìmpl.firestore.session.cPermissionFirestoreRepositoryImpl;
import com.me.mseotsanyana.mande.DAL.ìmpl.firestore.session.cSharedPreferenceFirestoreRepositoryImpl;
import com.me.mseotsanyana.mande.DAL.ìmpl.firestore.session.cUserProfileFirestoreRepositoryImpl;
import com.me.mseotsanyana.mande.PL.presenters.session.Impl.cUserLoginPresenterImpl;
import com.me.mseotsanyana.mande.PL.presenters.session.iUserLoginPresenter;
import com.me.mseotsanyana.mande.R;
import com.me.mseotsanyana.mande.cMainThreadImpl;

import java.util.Objects;

public class cLoginFragment extends Fragment implements iUserLoginPresenter.View {
    private static String TAG = cLoginFragment.class.getSimpleName();

    private EditText emailEditText, passwordEditText;
    private TextView loginTextView, forgotPasswordTextView, signUpTextView;
    private View progressBar;

    private iUserLoginPresenter userLoginPresenter;

    public cLoginFragment() {
    }

    public static cLoginFragment newInstance() {
        return new cLoginFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userLoginPresenter = new cUserLoginPresenterImpl(
                cThreadExecutorImpl.getInstance(),
                cMainThreadImpl.getInstance(), this,
                new cSharedPreferenceFirestoreRepositoryImpl(requireContext()),
                new cPermissionFirestoreRepositoryImpl(requireContext()),
                new cUserProfileFirestoreRepositoryImpl(requireContext()));
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.session_login_fragment, container, false);

        initViews(view);

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void initViews(View view) {
        emailEditText = view.findViewById(R.id.emailEditText);
        passwordEditText = view.findViewById(R.id.passwordEditText);

        forgotPasswordTextView = view.findViewById(R.id.forgotPasswordTextView);
        loginTextView = view.findViewById(R.id.loginTextView);
        signUpTextView = view.findViewById(R.id.signTextView);
        progressBar = view.findViewById(R.id.progressBar);

        /* initial hide progress bar */
        hideProgress();

        /* forget password listener */
        forgotPasswordTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        /* login listener */
        loginTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = Objects.requireNonNull(emailEditText.getText()).toString().trim();
                String password = Objects.requireNonNull(passwordEditText.getText()).toString().trim();

                if (!email.isEmpty() && !password.isEmpty()) {
                    userLoginPresenter.signInWithEmailAndPassword(email, password);
                } else {
                    Snackbar.make(requireView(), "Fields are empty !", Snackbar.LENGTH_LONG).show();
                }
            }
        });

        /* sign up listener */
        signUpTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavDirections action = cLoginFragmentDirections.
                        actionCLoginFragmentToCSignUpFragment();
                Navigation.findNavController(requireView()).navigate(action);
            }
        });
    }

    @Override
    public void onUserLoginSucceeded(String msg) {
        NavDirections action = cLoginFragmentDirections.
                actionCLoginFragmentToCHomeFragment();
        Navigation.findNavController(requireView()).navigate(action);
    }

    @Override
    public void onUserLoginFailed(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
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
    public void showError(String msg) {
        Log.d(TAG, msg);
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
    public TextView getForgotPasswordTextView() {
        return forgotPasswordTextView;
    }

    @Override
    public String getResourceString(int resourceID) {
        return getString(resourceID);
    }
}