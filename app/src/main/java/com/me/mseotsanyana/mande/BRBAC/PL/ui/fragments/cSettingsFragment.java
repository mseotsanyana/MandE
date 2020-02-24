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
import com.me.mseotsanyana.mande.PPMER.DAL.impl.cUploadAWPBRepositoryImpl;
import com.me.mseotsanyana.mande.PPMER.DAL.impl.cUploadEvaluationRepositoryImpl;
import com.me.mseotsanyana.mande.PPMER.DAL.impl.cUploadGlobalRepositoryImpl;
import com.me.mseotsanyana.mande.PPMER.DAL.impl.cUploadLogFrameRepositoryImpl;
import com.me.mseotsanyana.mande.PPMER.DAL.impl.cUploadMonitoringRepositoryImpl;
import com.me.mseotsanyana.mande.PPMER.DAL.impl.cUploadRAIDRepositoryImpl;
import com.me.mseotsanyana.mande.PPMER.PL.presenters.Impl.cUploadAWPBPresenterImpl;
import com.me.mseotsanyana.mande.PPMER.PL.presenters.Impl.cUploadEvaluationPresenterImpl;
import com.me.mseotsanyana.mande.PPMER.PL.presenters.Impl.cUploadGlobalPresenterImpl;
import com.me.mseotsanyana.mande.PPMER.PL.presenters.Impl.cUploadLogFramePresenterImpl;
import com.me.mseotsanyana.mande.PPMER.PL.presenters.Impl.cUploadMonitoringPresenterImpl;
import com.me.mseotsanyana.mande.PPMER.PL.presenters.Impl.cUploadRAIDPresenterImpl;
import com.me.mseotsanyana.mande.PPMER.PL.presenters.iUploadAWPBPresenter;
import com.me.mseotsanyana.mande.PPMER.PL.presenters.iUploadEvaluationPresenter;
import com.me.mseotsanyana.mande.PPMER.PL.presenters.iUploadGlobalPresenter;
import com.me.mseotsanyana.mande.PPMER.PL.presenters.iUploadLogFramePresenter;
import com.me.mseotsanyana.mande.PPMER.PL.presenters.iUploadMonitoringPresenter;
import com.me.mseotsanyana.mande.PPMER.PL.presenters.iUploadRAIDPresenter;
import com.me.mseotsanyana.mande.UTILITY.cUploadSessionData;
import com.me.mseotsanyana.mande.UTILITY.cUtil;
import com.me.mseotsanyana.mande.R;
import com.me.mseotsanyana.mande.cMainThreadImpl;

public class cSettingsFragment extends Fragment implements
        iUploadGlobalPresenter.View, iUploadLogFramePresenter.View,
        iUploadEvaluationPresenter.View, iUploadMonitoringPresenter.View,
        iUploadRAIDPresenter.View, iUploadAWPBPresenter.View{

    private static String TAG = cSettingsFragment.class.getSimpleName();

    private cSessionManager session;

    private AppCompatButton appCompatButtonGlobal;
    private AppCompatButton appCompatButtonBRBAC;
    private AppCompatButton appCompatButtonLogFrame;
    private AppCompatButton appCompatButtonEvaluation;
    private AppCompatButton appCompatButtonMonitoring;
    private AppCompatButton appCompatButtonRAID;
    private AppCompatButton appCompatButtonAWPB;

    private BottomNavigationView bottomNavigationView;
    private ProgressBar progressBar;

    private iUploadGlobalPresenter presenterGlobal;
    private iUploadLogFramePresenter presenterLogFrame;
    private iUploadEvaluationPresenter presenterEvaluation;
    private iUploadMonitoringPresenter presenterMonitoring;
    private iUploadRAIDPresenter presenterRAID;
    private iUploadAWPBPresenter presenterAWPB;

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

        presenterGlobal = new cUploadGlobalPresenterImpl(
                cThreadExecutorImpl.getInstance(),
                cMainThreadImpl.getInstance(),
                this,
                new cUploadGlobalRepositoryImpl(getContext()));

        presenterLogFrame = new cUploadLogFramePresenterImpl(
                cThreadExecutorImpl.getInstance(),
                cMainThreadImpl.getInstance(),
                this,
                new cUploadLogFrameRepositoryImpl(getContext()));

        presenterEvaluation = new cUploadEvaluationPresenterImpl(
                cThreadExecutorImpl.getInstance(),
                cMainThreadImpl.getInstance(),
                this,
                new cUploadEvaluationRepositoryImpl(getContext()));

        presenterMonitoring = new cUploadMonitoringPresenterImpl(
                cThreadExecutorImpl.getInstance(),
                cMainThreadImpl.getInstance(),
                this,
                new cUploadMonitoringRepositoryImpl(getContext()));

        presenterRAID = new cUploadRAIDPresenterImpl(
                cThreadExecutorImpl.getInstance(),
                cMainThreadImpl.getInstance(),
                this,
                new cUploadRAIDRepositoryImpl(getContext()));

        presenterAWPB = new cUploadAWPBPresenterImpl(
                cThreadExecutorImpl.getInstance(),
                cMainThreadImpl.getInstance(),
                this,
                new cUploadAWPBRepositoryImpl(getContext()));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_settings_fragment, container, false);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        /* a button to upload Global data set */
        appCompatButtonGlobal = (AppCompatButton) view.findViewById(R.id.appCompatButtonUploadGlobal);
        appCompatButtonGlobal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenterGlobal.uploadGlobalFromExcel();            }
        });

        /* a button to upload BRBAC data set */
        appCompatButtonBRBAC = (AppCompatButton) view.findViewById(R.id.appCompatButtonUploadBRBAC);
        appCompatButtonBRBAC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new cUploadSessionData(getContext(), session).execute();
            }
        });

        /* a button to upload LogFrame data set */
        appCompatButtonLogFrame = (AppCompatButton) view.findViewById(R.id.appCompatButtonUploadLogFrame);
        appCompatButtonLogFrame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //showProgress();
                presenterLogFrame.uploadLogFrameFromExcel();
            }
        });

        /* a button to upload Evaluation data set */
        appCompatButtonEvaluation = (AppCompatButton) view.findViewById(R.id.appCompatButtonUploadEvaluation);
        appCompatButtonEvaluation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //showProgress();
                presenterEvaluation.uploadEvaluationFromExcel();
            }
        });

        /* a button to upload Monitoring data set */
        appCompatButtonMonitoring = (AppCompatButton) view.findViewById(R.id.appCompatButtonUploadMonitoring);
        appCompatButtonMonitoring.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //showProgress();
                presenterMonitoring.uploadMonitoringFromExcel();
            }
        });

        /* a button to upload RAID data set */
        appCompatButtonRAID = (AppCompatButton) view.findViewById(R.id.appCompatButtonUploadRAID);
        appCompatButtonRAID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //showProgress();
                presenterRAID.uploadRAIDFromExcel();
            }
        });

        /* a button to upload AWPB data set */
        appCompatButtonAWPB = (AppCompatButton) view.findViewById(R.id.appCompatButtonUploadAWPB);
        appCompatButtonAWPB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //showProgress();
                presenterAWPB.uploadAWPBFromExcel();
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
        Log.d(TAG, "Modules Added Successfully!");
    }
}
