package com.me.mseotsanyana.mande.PL.ui.fragments.session;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.me.mseotsanyana.mande.BLL.executor.Impl.cThreadExecutorImpl;
import com.me.mseotsanyana.mande.BLL.model.session.cUserModel;
import com.me.mseotsanyana.mande.DAL.ìmpl.session.cOrganizationRepositoryImpl;
import com.me.mseotsanyana.mande.DAL.ìmpl.session.cRoleRepositoryImpl;
import com.me.mseotsanyana.mande.DAL.ìmpl.session.cSessionManagerImpl;
import com.me.mseotsanyana.mande.DAL.ìmpl.session.cStatusRepositoryImpl;
import com.me.mseotsanyana.mande.DAL.ìmpl.session.cUserRepositoryImpl;
import com.me.mseotsanyana.mande.PL.presenters.session.Impl.cUserLoginPresenterImpl;
import com.me.mseotsanyana.mande.PL.presenters.session.iUserLoginPresenter;
import com.me.mseotsanyana.mande.R;
import com.me.mseotsanyana.mande.cMainThreadImpl;

import java.util.Objects;

public class cLoginFragment extends Fragment implements iUserLoginPresenter.View {
    private static String TAG = cLoginFragment.class.getSimpleName();

    private TextInputLayout emailTextInputLayout, passwordTextInputLayout;
    private TextInputEditText emailTextInputEditText, passwordTextInputEditText;
    private TextView forgotPasswordTextView;
    private ProgressBar progressBar;

    private iUserLoginPresenter userLoginPresenter;

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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
        View view = inflater.inflate(R.layout.home_login_fragment, container, false);

        initViews(view);

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void initViews(View view) {
        AppCompatButton loginButton = view.findViewById(R.id.loginButton);
        forgotPasswordTextView = view.findViewById(R.id.forgotPasswordTextView);
        emailTextInputLayout = view.findViewById(R.id.emailTextInputLayout);
        emailTextInputEditText = view.findViewById(R.id.emailTextInputEditText);
        passwordTextInputLayout = view.findViewById(R.id.passwordTextInputLayout);
        passwordTextInputEditText = view.findViewById(R.id.passwordTextInputEditText);
        progressBar = view.findViewById(R.id.progressBar);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = Objects.requireNonNull(emailTextInputEditText.getText()).toString();
                String password = Objects.requireNonNull(passwordTextInputEditText.getText()).toString();

                if (!email.isEmpty() && !password.isEmpty()) {
                    userLoginPresenter.userLogin(email, password);
                } else {
                    Snackbar.make(requireView(), "Fields are empty !", Snackbar.LENGTH_LONG).show();
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

    @Override
    public void onUserLoginSucceeded(cUserModel userModel) {
        /* navigate from login to logframe */
        NavDirections action = cLoginFragmentDirections.actionCLoginFragmentToCLogFrameFragment();
        Navigation.findNavController(requireView()).navigate(action);
        /* remove the bottom navigation from view */
        (requireActivity().findViewById(R.id.bottom_navigation)).setVisibility(View.GONE);
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