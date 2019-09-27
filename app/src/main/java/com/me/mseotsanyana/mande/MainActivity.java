package com.me.mseotsanyana.mande;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.me.mseotsanyana.mande.BRBAC.PL.cLoginFragment;
import com.me.mseotsanyana.mande.BRBAC.PL.cPermissionFragment;
import com.me.mseotsanyana.mande.BRBAC.PL.cRoleFragment;
import com.me.mseotsanyana.mande.BRBAC.BLL.cSessionManager;
import com.me.mseotsanyana.mande.BRBAC.PL.cUserFragment;
import com.me.mseotsanyana.mande.Util.cUploadBRBACData;
import com.me.mseotsanyana.mande.PPMER.BLL.cActivityDomain;
import com.me.mseotsanyana.mande.PPMER.BLL.cActivityHandler;
import com.me.mseotsanyana.mande.PPMER.BLL.cGoalDomain;
import com.me.mseotsanyana.mande.PPMER.BLL.cGoalHandler;
import com.me.mseotsanyana.mande.PPMER.BLL.cObjectiveDomain;
import com.me.mseotsanyana.mande.PPMER.BLL.cObjectiveHandler;
import com.me.mseotsanyana.mande.BRBAC.BLL.cOrganizationDomain;
import com.me.mseotsanyana.mande.BRBAC.BLL.cOrganizationHandler;
import com.me.mseotsanyana.mande.PPMER.BLL.cOutcomeDomain;
import com.me.mseotsanyana.mande.PPMER.BLL.cOutcomeHandler;
import com.me.mseotsanyana.mande.PPMER.BLL.cOutcomeOutputDomain;
import com.me.mseotsanyana.mande.PPMER.BLL.cOutcomeOutputHandler;
import com.me.mseotsanyana.mande.PPMER.BLL.cOutputActivityDomain;
import com.me.mseotsanyana.mande.PPMER.BLL.cOutputActivityHandler;
import com.me.mseotsanyana.mande.PPMER.BLL.cOutputDomain;
import com.me.mseotsanyana.mande.PPMER.BLL.cOutputHandler;
import com.me.mseotsanyana.mande.PPMER.BLL.cProjectDomain;
import com.me.mseotsanyana.mande.PPMER.BLL.cProjectHandler;
import com.me.mseotsanyana.mande.PPMER.BLL.cProjectOutcomeDomain;
import com.me.mseotsanyana.mande.PPMER.BLL.cProjectOutcomeHandler;
import com.me.mseotsanyana.mande.PPMER.BLL.cSpecificAimDomain;
import com.me.mseotsanyana.mande.PPMER.BLL.cSpecificAimHandler;
import com.me.mseotsanyana.mande.BRBAC.BLL.cValueDomain;
import com.me.mseotsanyana.mande.BRBAC.BLL.cValueHandler;
import com.me.mseotsanyana.mande.PPMER.PL.cActivityFragment;
import com.me.mseotsanyana.mande.PPMER.PL.cGoalFragment;
import com.me.mseotsanyana.mande.PPMER.PL.cOrganizationFragment;
import com.me.mseotsanyana.mande.PPMER.PL.cOutcomeFragment;
import com.me.mseotsanyana.mande.PPMER.PL.cOutputFragment;
import com.me.mseotsanyana.mande.PPMER.PL.cProjectFragment;
import com.me.mseotsanyana.mande.PPMER.PL.cTriangleFragment;
import com.me.mseotsanyana.quickactionlibrary.cActionItem;
import com.me.mseotsanyana.quickactionlibrary.cQuickAction;

import com.me.mseotsanyana.mande.COM.cEvent;
import com.me.mseotsanyana.mande.COM.cGlobalBus;
import com.me.mseotsanyana.mande.PPMER.PL.cMainFragment;
import com.me.mseotsanyana.treeadapterlibrary.cTreeModel;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements
        cMainFragment.OnGridViewItemSelectedListener {

    private cSessionManager session;

    private cQuickAction mQuickAction;

    private cOrganizationDomain organizationDomain;
    private cValueDomain valueDomain;
    private cGoalDomain goalDomain;
    private cSpecificAimDomain specificAimDomain;
    private cProjectDomain projectDomain;
    private cObjectiveDomain objectiveDomain;
    private cOutcomeDomain outcomeDomain;
    private cOutputDomain outputDomain;
    private cActivityDomain activityDomain;

    private cProjectOutcomeDomain projectOutcomeDomain;
    private cOutcomeOutputDomain outcomeOutputDomain;
    private cOutputActivityDomain outputActivityDomain;

    private cOrganizationHandler organizationHandler;
    private cValueHandler valueHandler;
    private cGoalHandler goalHandler;
    private cSpecificAimHandler specificAimHandler;
    private cProjectHandler projectHandler;
    private cObjectiveHandler objectiveHandler;
    private cOutcomeHandler outcomeHandler;
    private cOutputHandler outputHandler;
    private cActivityHandler activityHandler;

    private cProjectOutcomeHandler projectOutcomeHandler;
    private cOutcomeOutputHandler outcomeOutputHandler;
    private cOutputActivityHandler outputActivityHandler;

    //private cUploadMEData uploadExcelData;
    //private cUploadBRBACData uploadMEData;
    private cUploadBRBACData task;

    private View imageView;
    private int position;
    ProgressDialog pd;


    // index to identify current nav menu item
    public static int navItemIndex = 0;

    // tags used to attach the fragments
    private static final String TAG_HOME = "home";
    private static final String TAG_PHOTOS = "photos";
    private static final String TAG_MOVIES = "movies";
    private static final String TAG_NOTIFICATIONS = "notifications";
    private static final String TAG_SETTINGS = "settings";
    private static final String TAG_UPLOADINGS = "uploadings";

    public static String CURRENT_TAG = TAG_HOME;

    String[] listOfTables = {"ORGANIZATION", "VALUE", "OVERALL_AIM", "SPECIFIC_AIM", "PROJECT", "OBJECTIVE", "OUTCOME", "OUTPUT", "ACTIVITY", "PROJECT_OUTCOME", "OUTCOME_OUTPUT", "OUTPUT_ACTIVITY"};

    ArrayList<cOrganizationDomain> organizationDomainList = new ArrayList<cOrganizationDomain>();
    ArrayList<cTreeModel> selectedModel = new ArrayList<cTreeModel>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        /*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent modifySettings=new Intent(MainActivity.this,cSettingsActivity.class);
                startActivity(modifySettings);
            }
        });*/


        // keeps global user information
        session = new cSessionManager(getApplicationContext());

        //uploadDemoData = new cSetDomainsFromExcel();
        //uploadExcelData = new cUploadMEData(this);

        // initialise the dashboard fragment
        initFragment();

        // register broadcast receiver and services
        //scheduleJobServices = new cSyncManager(getApplicationContext());
/*        Map<String, String> map = new HashMap<>();
        map.put("userID","7");
        map.put("group","0");
        map.put("other","4");
        map.put("perms","4");
        map.put("status","0");

        String stringMap = new Gson().toJson(map);  // returns {"hei":"sann"}

        PersistableBundle params = new PersistableBundle();
        params.putString("jsonParam",stringMap);
        scheduleJobServices.startAddressJobService(0, params);
*/
        //url = volleyHandler.generateUrl(url, params);
        //scheduleJobServices.startServices();
        //scheduleJobServices.stopServices();

        // initialise quick actions
        //initQuickActions();

        // uploading
        //task = new cUploadBRBACData(this);

        //task.execute();

        //setupBottomNavigation();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Register this fragment to listen to event.
        cGlobalBus.getBus().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
//        scheduleJobServices.stopServices();
        cGlobalBus.getBus().unregister(this);
    }

    @Subscribe
    public void getMessage(cEvent.cProjectActivityMessage projectActivityMessage) {
        // receive and show a quick action view message from adapter
        mQuickAction.show(projectActivityMessage.getMessage());
    }

    // initialise the login and profile fragments
    private void initFragment() {
        /*session.logoutUser();
        session.deleteSettings();
        session.commitSettings();*/

        Fragment fragment;
        if (session.isLoggedIn()) {
            //Intent intent = new Intent(this, cDesktopActivity.class);
            //startActivity(intent);
            fragment = new cMainFragment().newInstance();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fragment_frame, fragment);
            ft.commit();
        } else {
            fragment = new cLoginFragment().newInstance();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fragment_frame, fragment);
            ft.commit();
        }
    }

    // initialise the services to automatically sync client with servers
    /*private void initUserSynchronization(){
        Intent alarm = new Intent(getApplicationContext(), cBroadcastReceiver.class);
        alarm.setAction("com.me.mseotsanyana.mande.cUserJobService");
        alarm.setAction("com.me.mseotsanyana.mande.cAddressJobService");

        boolean alarmRunning = (PendingIntent.getBroadcast(getApplicationContext(),
                0,
                alarm,
                PendingIntent.FLAG_NO_CREATE) != null);
        Toast.makeText(this, "Alarm has started 1 !!!", Toast.LENGTH_SHORT).show();

        if(alarmRunning == false) {
            Toast.makeText(this, "Alarm has started 2 !!!", Toast.LENGTH_SHORT).show();
            pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, alarm, 0);
            alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime(), 60000, pendingIntent);
        }else {
            if (pendingIntent != null) {
                alarmManager.cancel(pendingIntent);
            }
        }
    }*/

    // initialise the quick actions
    private void initQuickActions() {
        mQuickAction = new cQuickAction(this);

        // Delete action item
        cActionItem delAction = new cActionItem();
        delAction.setTitle("Delete");
        delAction.setIcon(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_delete2));

        // Edit action item
        cActionItem editAction = new cActionItem();
        editAction.setTitle("Edit");
        editAction.setIcon(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_writer2));

        // Synchronise action itemtextCapWords
        cActionItem syncAction = new cActionItem();
        syncAction.setTitle("Sync");
        syncAction.setIcon(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_sync2));

        // Detail action item
        cActionItem detailAction = new cActionItem();
        detailAction.setTitle("Details");
        detailAction.setIcon(ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_record2));

        mQuickAction.addActionItem(delAction);
        mQuickAction.addActionItem(editAction);
        mQuickAction.addActionItem(syncAction);
        mQuickAction.addActionItem(detailAction);

        // setup the action item click listener
        mQuickAction
                .setOnActionItemClickListener(new cQuickAction.OnActionItemClickListener() {
                    public void onItemClick(int position) {
                        try {
                            // send a position to fragment
                            cEvent.cActivityFragmentMessage activityFragmentMessageEvent =
                                    new cEvent.cActivityFragmentMessage(position);

                            cGlobalBus.getBus().post(activityFragmentMessageEvent);

                        } catch (ClassCastException cce) {

                        }
                    }
                });
    }

    public void getSelectedOrganizationList(ArrayList<cOrganizationDomain> selectedOrganizations) {
        organizationDomainList = selectedOrganizations;
    }

    public void getSelectedTreeModel(ArrayList<cTreeModel> selectedModel) {
        this.selectedModel = selectedModel;
    }

    // menu for the dashboard
    @Override
    public void getGridPosition(int position) {
        //Fragment dashboard_fragment = null;
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        switch (position) {
            case 0:
                cOrganizationFragment organizationFragment = cOrganizationFragment.newInstance(organizationDomainList);
                ft.replace(R.id.fragment_frame, organizationFragment);
                ft.commit();
                break;
            case 1:
                cTriangleFragment triangleFragment = cTriangleFragment.newInstance(this.selectedModel);
                ft.replace(R.id.fragment_frame, triangleFragment);
                ft.commit();
                break;
            case 2:
                cGoalFragment goalFragment = cGoalFragment.newInstance(this.selectedModel);
                ft.replace(R.id.fragment_frame, goalFragment);
                ft.commit();
                break;
            case 3:
                cProjectFragment projectFragment = cProjectFragment.newInstance(this.selectedModel);
                ft.replace(R.id.fragment_frame, projectFragment);
                ft.commit();
                break;
            case 4:
                cOutcomeFragment outcomeFragment = cOutcomeFragment.newInstance(this.selectedModel);
                ft.replace(R.id.fragment_frame, outcomeFragment);
                ft.commit();
                break;
            case 5:
                cOutputFragment outputFragment = cOutputFragment.newInstance(this.selectedModel);
                ft.replace(R.id.fragment_frame, outputFragment);
                ft.commit();
                break;
            case 6:
                cActivityFragment activityFragment = cActivityFragment.newInstance(this.selectedModel);
                ft.replace(R.id.fragment_frame, activityFragment);
                ft.commit();
                break;
            case 7:
                //dashboard_fragment = new cIndicatorFragment();
                break;
            case 8:
                //dashboard_fragment = new cMoVFragment();
                break;
            case 9:
                //dashboard_fragment = new cRiskFragment();
                break;
            case 10:
                //dashboard_fragment = new cWorkplanFragment();
                break;
            case 11:
                //dashboard_fragment = new cBudgetFragment();
                break;
            case 12:
                //dashboard_fragment = new cResourceFragment();
                break;
            case 13:
                //dashboard_fragment = new cEvaluationFragment();
                break;
            case 14:
                //dashboard_fragment = new cCriterionFragment();
                break;
            case 15:
                //dashboard_fragment = new cQuestionFragment();
                break;
            case 16:
                //dashboard_fragment = new cCategoryFragment();
                break;
            case 17:
                //dashboard_fragment = new cEvaluatingFragment();
                break;
            case 18:
                //dashboard_fragment = new cMonitoringFragment();
                break;
            case 19:
                //dashboard_fragment = new cReportFragment();
                break;
            case 20:
                //dashboard_fragment = new cAlertFragment();
                break;
            default:
                //Toast.makeText(appCompatActivitykm, "Failed to create fragment", Toast.LENGTH_SHORT).show();
                break;
        }
/*
        if (dashboard_fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.fragment_frame, dashboard_fragment);
            ft.commit();
        }else {
            //Snackbar.make(getCurrentFocus(), "FAB Clicked", Snackbar.LENGTH_SHORT).show();
            Toast.makeText(this, "Failed to create fragment", Toast.LENGTH_SHORT).show();
        }*/
    }


    // drawer navigation main menu
    @Override
    public void getGroupMenuPosition(int position) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        //Check to see which item was being clicked and perform appropriate action
        switch (position) {
            //Replacing the main content with ContentFragment Which is our Inbox View;
            case 0:
                navItemIndex = 0;
                CURRENT_TAG = TAG_HOME;
                break;
            case 1:
                navItemIndex = 1;
                CURRENT_TAG = TAG_PHOTOS;
                break;
            case 2:
                navItemIndex = 2;
                CURRENT_TAG = TAG_MOVIES;
                break;
            case 3:
                // launch settings activity
                //startActivity(new Intent(MainActivity.this,
                //       cSettingsPrefActivity.class));

                //getFragmentManager().beginTransaction().
                //        replace(R.id.fragment_frame, new cPreferenceFragment()).commit();

                //cPreferenceFragment preferenceFragment = cPreferenceFragment.newInstance();
                //ft.replace(R.id.fragment_frame, preferenceFragment);
                //ft.commit();
                break;
            case 4:
                navItemIndex = 4;
                CURRENT_TAG = TAG_SETTINGS;
                break;
            case 5:
                navItemIndex = 5;
                CURRENT_TAG = TAG_UPLOADINGS;

                task = new cUploadBRBACData(this, session);

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                //uncomment the below code to Set the message and title from the strings.xml file
                //builder.setMessage(R.string.dialog_message) .setTitle(R.string.dialog_title);

                //setting message manually and performing action on button click
                builder.setMessage("This operation will delete and replace all your existing data! Do you want to continue ?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // calling AsyncTask for populating database from excel
                                task.execute();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                //creating dialog box
                AlertDialog alert = builder.create();

                //Setting the title manually
                alert.setTitle("Uploading demo data");
                alert.show();

                break;
            case 6:
                session.logoutUser();
                finish();

                break;

            default:
                navItemIndex = 0;
                break;
        }
    }

    // drawer navigation sub menu
    @Override
    public void getChildMenuPosition(int childPosition) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();

        //Check to see which item was being clicked and perform appropriate action
        switch (childPosition) {
            //Replacing the main content with ContentFragment Which is our Inbox View;
            case 0:
                cUserFragment userFragment = cUserFragment.newInstance();
                ft.replace(R.id.fragment_frame, userFragment);
                ft.addToBackStack("USER");
                ft.commit();
                break;
            case 1:
                cRoleFragment roleFragment = cRoleFragment.newInstance();
                ft.replace(R.id.fragment_frame, roleFragment);
                ft.addToBackStack("ROLE");
                ft.commit();
                break;
            /*case 2:
                cPrivilegeFragment privilegeFragment = cPrivilegeFragment.newInstance();
                ft.replace(R.id.fragment_frame, privilegeFragment);
                ft.addToBackStack("PRIVILEGE");
                ft.commit();
                break;*/
            case 2:
                cPermissionFragment permissionFragment = cPermissionFragment.newInstance();
                ft.replace(R.id.fragment_frame, permissionFragment);
                ft.addToBackStack("PERMISSION");
                ft.commit();
                break;
            /*
            case 4:
                navItemIndex = 3;
                CURRENT_TAG = TAG_NOTIFICATIONS;
                break;
            case 5:@Override
      public boolean onBackPressed() {
                navItemIndex = 4;
                CURRENT_TAG = TAG_SETTINGS;
                break;
            case 6:
                navItemIndex = 5;
                CURRENT_TAG = TAG_UPLOADINGS;

                task = new cUploadBRBACData(this);

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                //uncomment the below code to Set the message and title from the strings.xml file
                //builder.setMessage(R.string.dialog_message) .setTitle(R.string.dialog_title);

                //setting message manually and performing action on button click
                builder.setMessage("This operation will delete and replace all your existing data! Do you want to continue ?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // calling AsyncTask for populating database from excel
                                task.execute();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                //creating dialog box
                AlertDialog alert = builder.create();

                //Setting the title manually
                alert.setTitle("Uploading demo data");
                alert.show();

                break;
            case 7:
                // launch new intent instead of loading fragment
                //startActivity(new Intent(MainActivity.this, AboutUsActivity.class));
                //drawer.closeDrawers();
                // refresh toolbar menu
                //this.invalidateOptionsMenu();
                //return true;
                break;
            */

            default:
                break;
        }
    }

    // called when a home button is clicked
    public void onClickHome(View v) {
        Fragment dashboard = new cMainFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_frame, dashboard);
        ft.commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}