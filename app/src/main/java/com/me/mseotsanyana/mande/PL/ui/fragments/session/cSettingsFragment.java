package com.me.mseotsanyana.mande.PL.ui.fragments.session;

//import android.app.Fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.Layout;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.me.mseotsanyana.mande.BLL.executor.Impl.cThreadExecutorImpl;
import com.me.mseotsanyana.mande.DAL.ìmpl.wpb.cUploadAWPBRepositoryImpl;
import com.me.mseotsanyana.mande.DAL.ìmpl.evaluator.cUploadEvaluationRepositoryImpl;
import com.me.mseotsanyana.mande.DAL.ìmpl.common.cUploadGlobalRepositoryImpl;
import com.me.mseotsanyana.mande.DAL.ìmpl.logframe.cUploadLogFrameRepositoryImpl;
import com.me.mseotsanyana.mande.DAL.ìmpl.monitor.cUploadMonitoringRepositoryImpl;
import com.me.mseotsanyana.mande.DAL.ìmpl.raid.cUploadRAIDRepositoryImpl;
import com.me.mseotsanyana.mande.DAL.ìmpl.session.cUploadSessionRepositoryImpl;
import com.me.mseotsanyana.mande.PL.presenters.wpb.Impl.cUploadAWPBPresenterImpl;
import com.me.mseotsanyana.mande.PL.presenters.evaluator.Impl.cUploadEvaluationPresenterImpl;
import com.me.mseotsanyana.mande.PL.presenters.common.Impl.cUploadGlobalPresenterImpl;
import com.me.mseotsanyana.mande.PL.presenters.logframe.Impl.cUploadLogFramePresenterImpl;
import com.me.mseotsanyana.mande.PL.presenters.monitor.Impl.cUploadMonitoringPresenterImpl;
import com.me.mseotsanyana.mande.PL.presenters.raid.Impl.cUploadRAIDPresenterImpl;
import com.me.mseotsanyana.mande.PL.presenters.wpb.iUploadAWPBPresenter;
import com.me.mseotsanyana.mande.PL.presenters.evaluator.iUploadEvaluationPresenter;
import com.me.mseotsanyana.mande.PL.presenters.common.iUploadGlobalPresenter;
import com.me.mseotsanyana.mande.PL.presenters.logframe.iUploadLogFramePresenter;
import com.me.mseotsanyana.mande.PL.presenters.monitor.iUploadMonitoringPresenter;
import com.me.mseotsanyana.mande.PL.presenters.raid.iUploadRAIDPresenter;
import com.me.mseotsanyana.mande.PL.presenters.session.Impl.cUploadSessionPresenterImpl;
import com.me.mseotsanyana.mande.PL.presenters.session.iUploadSessionPresenter;
import com.me.mseotsanyana.mande.UTIL.TextDrawable;
import com.me.mseotsanyana.mande.UTIL.cFontManager;
import com.me.mseotsanyana.mande.R;
import com.me.mseotsanyana.mande.cMainThreadImpl;

import java.util.Objects;

public class cSettingsFragment extends Fragment implements
        iUploadSessionPresenter.View, iUploadGlobalPresenter.View,
        iUploadLogFramePresenter.View, iUploadEvaluationPresenter.View,
        iUploadMonitoringPresenter.View, iUploadRAIDPresenter.View,
        iUploadAWPBPresenter.View{

    private static String TAG = cSettingsFragment.class.getSimpleName();

    //private cSessionManager session;

    //private BottomNavigationView bottomNavigationView;
    private ProgressBar progressBar;

    private iUploadSessionPresenter presenterSession;
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
        AppCompatButton appCompatButtonGlobal = (AppCompatButton) view.findViewById(R.id.appCompatButtonUploadGlobal);
        appCompatButtonGlobal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                presenterGlobal = new cUploadGlobalPresenterImpl(
                        cThreadExecutorImpl.getInstance(),
                        cMainThreadImpl.getInstance(),
                        cSettingsFragment.this,
                        new cUploadGlobalRepositoryImpl(getContext()));

                presenterGlobal.uploadGlobalFromExcel();            }
        });

        /* a button to upload Session data set */
        AppCompatButton appCompatButtonBRBAC = (AppCompatButton) view.findViewById(R.id.appCompatButtonUploadBRBAC);
        appCompatButtonBRBAC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenterSession = new cUploadSessionPresenterImpl(
                        cThreadExecutorImpl.getInstance(),
                        cMainThreadImpl.getInstance(),
                        cSettingsFragment.this,
                        new cUploadSessionRepositoryImpl(getContext()));

                presenterSession.uploadSessionFromExcel();
            }
        });

        /* a button to upload LogFrame data set */
        AppCompatButton appCompatButtonLogFrame = (AppCompatButton) view.findViewById(R.id.appCompatButtonUploadLogFrame);
        appCompatButtonLogFrame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenterLogFrame = new cUploadLogFramePresenterImpl(
                        cThreadExecutorImpl.getInstance(),
                        cMainThreadImpl.getInstance(),
                        cSettingsFragment.this,
                        new cUploadLogFrameRepositoryImpl(getContext()));

                presenterLogFrame.uploadLogFrameFromExcel();
            }
        });

        /* a button to upload Evaluation data set */
        AppCompatButton appCompatButtonEvaluation = (AppCompatButton) view.findViewById(R.id.appCompatButtonUploadEvaluation);
        appCompatButtonEvaluation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenterEvaluation = new cUploadEvaluationPresenterImpl(
                        cThreadExecutorImpl.getInstance(),
                        cMainThreadImpl.getInstance(),
                        cSettingsFragment.this,
                        new cUploadEvaluationRepositoryImpl(getContext()));

                presenterEvaluation.uploadEvaluationFromExcel();
            }
        });

        /* a button to upload Monitoring data set */
        AppCompatButton appCompatButtonMonitoring = (AppCompatButton) view.findViewById(R.id.appCompatButtonUploadMonitoring);
        appCompatButtonMonitoring.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenterMonitoring = new cUploadMonitoringPresenterImpl(
                        cThreadExecutorImpl.getInstance(),
                        cMainThreadImpl.getInstance(),
                        cSettingsFragment.this,
                        new cUploadMonitoringRepositoryImpl(getContext()));

                presenterMonitoring.uploadMonitoringFromExcel();
            }
        });

        /* a button to upload RAID data set */
        AppCompatButton appCompatButtonRAID = (AppCompatButton) view.findViewById(R.id.appCompatButtonUploadRAID);
        appCompatButtonRAID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenterRAID = new cUploadRAIDPresenterImpl(
                        cThreadExecutorImpl.getInstance(),
                        cMainThreadImpl.getInstance(),
                        cSettingsFragment.this,
                        new cUploadRAIDRepositoryImpl(getContext()));

                presenterRAID.uploadRAIDFromExcel();
            }
        });

        /* a button to upload AWPB data set */
        AppCompatButton appCompatButtonAWPB = (AppCompatButton) view.findViewById(R.id.appCompatButtonUploadAWPB);
        appCompatButtonAWPB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenterAWPB = new cUploadAWPBPresenterImpl(
                        cThreadExecutorImpl.getInstance(),
                        cMainThreadImpl.getInstance(),
                        cSettingsFragment.this,
                        new cUploadAWPBRepositoryImpl(getContext()));

                presenterAWPB.uploadAWPBFromExcel();
            }
        });

        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        /* the view responsible for bottom navigation menu */
        //bottomNavigationView = (BottomNavigationView) view.findViewById(R.id.bottom_navigation);
        //bottomNavigationView.getMenu().getItem(3).setChecked(true);

        //cUtil.setIcon(getContext(), bottomNavigationView, 3);
        //cUtil.disableShiftMode(bottomNavigationView);
        //setupBottomNavigation();
    }
/*
    private void setupBottomNavigation() {

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.action_login:
                        pushFragment(new cLoginFragment());//.newInstance(session));
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
*/
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
        ft.replace(R.id.fragment_frame, fragment);
        ft.commit();
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

    /* declared in all module.View */
    @Override
    public void onUploadCompleted(String title, String msg) {
        deleteAlertDialog(title, msg);
    }

    private void deleteAlertDialog(String title, String message) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                Objects.requireNonNull(getContext()));

        // setting icon to dialog
        TextDrawable faIcon = new TextDrawable(getContext());
        faIcon.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
        faIcon.setTextAlign(Layout.Alignment.ALIGN_CENTER);
        faIcon.setTypeface(cFontManager.getTypeface(getContext(), cFontManager.FONTAWESOME));
        faIcon.setText(getContext().getResources().getText(R.string.fa_delete));
        faIcon.setTextColor(getContext().getColor(R.color.colorPrimaryDark));
        alertDialogBuilder.setIcon(faIcon);

        // set title
        alertDialogBuilder.setTitle(title);
        // set dialog message
        alertDialogBuilder
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton(getContext().getResources().getText(
                        R.string.Ok), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }
}
