package com.me.mseotsanyana.mande.BRBAC.PL.ui.fragments;

//import android.app.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.me.mseotsanyana.mande.BRBAC.BLL.cSessionManager;
import com.me.mseotsanyana.mande.BRBAC.PL.cJoinFragment;
import com.me.mseotsanyana.mande.BRBAC.PL.cLoginFragment;
import com.me.mseotsanyana.mande.BRBAC.PL.cRegisterFragment;
import com.me.mseotsanyana.mande.PPMER.BLL.executor.Impl.cThreadExecutorImpl;
import com.me.mseotsanyana.mande.PPMER.DAL.impl.cUploadPMRepositoryImpl;
import com.me.mseotsanyana.mande.PPMER.PL.presenters.Impl.cUploadPMPresenterImpl;
import com.me.mseotsanyana.mande.PPMER.PL.presenters.iUploadPMPresenter;
import com.me.mseotsanyana.mande.UTILITY.cUploadSessionData;
import com.me.mseotsanyana.mande.UTILITY.cUtil;
import com.me.mseotsanyana.mande.R;
import com.me.mseotsanyana.mande.cMainThreadImpl;

public class cSettingsFragment extends Fragment implements iUploadPMPresenter.View {
    private static String TAG = cSettingsFragment.class.getSimpleName();

    private cSessionManager session;

    private AppCompatButton appCompatButtonBRBAC;
    private AppCompatButton appCompatButtonPM;
    private BottomNavigationView bottomNavigationView;
    private ProgressBar progressBar;

    private iUploadPMPresenter presenter;

    public cSettingsFragment() {
    }

    public static cSettingsFragment newInstance() {

        return new cSettingsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        session = new cSessionManager(getContext());

        presenter = new cUploadPMPresenterImpl(
                cThreadExecutorImpl.getInstance(),
                cMainThreadImpl.getInstance(),
                this,
                new cUploadPMRepositoryImpl(getContext()));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_settings_fragment, container, false);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        /* a button to upload BRBAC data set */
        appCompatButtonBRBAC = (AppCompatButton) view.findViewById(R.id.appCompatButtonUploadBRBAC);
        appCompatButtonBRBAC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new cUploadSessionData(getContext(), session).execute();
            }
        });

        /* a button to upload PM data set */
        appCompatButtonPM = (AppCompatButton) view.findViewById(R.id.appCompatButtonUploadPM);
        appCompatButtonPM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //showProgress();
                presenter.uploadPMFromExcel();
            }
        });

        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        /* the view responsible for bottom navigation menu */
        bottomNavigationView = (BottomNavigationView) view.findViewById(R.id.bottom_navigation);
        bottomNavigationView.getMenu().getItem(3).setChecked(true);

        cUtil.setIcon(getContext(), bottomNavigationView, 3);
        cUtil.disableShiftMode(bottomNavigationView);
        setupBottomNavigation();
    }

    private void setupBottomNavigation() {

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.action_login:
                        pushFragment(cLoginFragment.newInstance(session));
                        return true;
                    case R.id.action_create:
                        pushFragment(cRegisterFragment.newInstance());
                        return true;
                    case R.id.action_join:
                        pushFragment(cJoinFragment.newInstance());
                        return true;
                }
                return false;
            }
        });
    }

    /**
     * Preferences
     * Method to push any fragment into given id.
     *
     * @param fragment An instance of FPreferencesragment to show into the given id.
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

    @Override
    public void onUploadCompleted() {
        Log.d(TAG, "Planning Module Added Successfully!");
    }
}
