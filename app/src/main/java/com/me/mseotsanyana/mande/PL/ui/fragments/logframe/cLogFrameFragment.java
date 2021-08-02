package com.me.mseotsanyana.mande.PL.ui.fragments.logframe;

import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatEditText;

import android.graphics.Color;
import android.graphics.Typeface;
import android.text.Layout;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import android.os.Bundle;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.util.Pair;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.me.mseotsanyana.mande.BLL.executor.Impl.cThreadExecutorImpl;
import com.me.mseotsanyana.mande.BLL.model.logframe.cLogFrameModel;
import com.me.mseotsanyana.mande.BLL.model.session.cOrganizationModel;
import com.me.mseotsanyana.mande.DAL.ìmpl.sqlite.logframe.cLogFrameRepositoryImpl;
import com.me.mseotsanyana.mande.DAL.ìmpl.sqlite.session.cMenuRepositoryImpl;
import com.me.mseotsanyana.mande.PL.presenters.logframe.Impl.cLogFramePresenterImpl;
import com.me.mseotsanyana.mande.PL.presenters.logframe.iLogFramePresenter;
import com.me.mseotsanyana.mande.PL.ui.adapters.logframe.cLogFrameAdapter;
import com.me.mseotsanyana.mande.PL.ui.fragments.session.cRoleFragment;
import com.me.mseotsanyana.mande.PL.ui.fragments.session.cUserFragment;
import com.me.mseotsanyana.mande.UTIL.TextDrawable;
import com.me.mseotsanyana.mande.UTIL.cConstant;

import com.me.mseotsanyana.mande.UTIL.cExpandableListAdapter;
import com.me.mseotsanyana.mande.R;
import com.me.mseotsanyana.mande.UTIL.cFontManager;
import com.me.mseotsanyana.mande.cMainThreadImpl;
import com.me.mseotsanyana.mande.cSettingsActivity;
import com.me.mseotsanyana.multiselectspinnerlibrary.cKeyPairBoolData;
import com.me.mseotsanyana.multiselectspinnerlibrary.cSingleSpinnerListener;
import com.me.mseotsanyana.multiselectspinnerlibrary.cSingleSpinnerSearch;
import com.me.mseotsanyana.treeadapterlibrary.cTreeModel;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;

/**
 * Created by mseotsanyana on 2016/11/02.
 */
public class cLogFrameFragment extends Fragment implements iLogFramePresenter.View {
    private static String TAG = cLogFrameFragment.class.getSimpleName();
    private static SimpleDateFormat tsdf = cConstant.TIMESTAMP_FORMAT_DATE;
    private static SimpleDateFormat ssdf = cConstant.SHORT_FORMAT_DATE;

    /* logframe views */
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private ExpandableListView menuExpandableListView;
    private LinearLayout includeProgressBar;

    /* logframe interface */
    private iLogFramePresenter logFramePresenter;

    /* logframe adapter */
    private cLogFrameAdapter logFrameRecyclerViewAdapter;

    /* menu data structures */
    private List<String> menuItemTitles;
    private HashMap<String, List<String>> expandableMenuItems;
    private ArrayList<cOrganizationModel> sharedOrganizations;

    private AppCompatActivity activity;

    public cLogFrameFragment() {
    }

    public static cLogFrameFragment newInstance() {
        return new cLogFrameFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        /* get all logframes from the database */
        logFramePresenter.readAllLogFrames();
        logFramePresenter.readSharedOrganizations();
    }

    /**
     * the onCreateView method is called when fragment should create its View object
     * hierarchy either dynamically or via XML Layout inflation.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.logframe_list_fragment, container, false);
    }

    /**
     * this event is triggered soon after on CreateView(). onViewCreated is called if the
     * view is returned from onCreateView() is non-null. Any view setup should occur here. e.g.
     * view lookups and attaching view listeners.
     */
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {

        /* create data structures */
        initDataStructures();

        /* create navigation drawer menu */
        initNavigationDrawer(view);

        /* create logframe menu */
        initLogframeView(view);

        /* draggable floating button */
        initDraggableFAB(view);
    }

    private void initNavigationDrawer(View view) {
        /* populate navigation view with relevant to the logged in user from database */
        populateNavigationDrawer(view);
        /* initialise the toolbar and the drawer layout for animating the menu */
        setupDrawerToggle(view);
        /* setup drawer navigation group and children listeners */
        setupDrawerNavigationListener();

        // put an arrow button
        //activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //activity.getSupportActionBar().setHomeButtonEnabled(true);

    }

    private void initDataStructures() {
        /* contains main menu and its corresponding submenu items */
        menuItemTitles = new ArrayList<String>();
        expandableMenuItems = new LinkedHashMap<String, List<String>>();
        /* contains a tree of logframes */
        /* logframe data structures */
        List<cTreeModel> logFrameTreeModels = new ArrayList<>();
        /* shared preference organizations */
        sharedOrganizations = new ArrayList<>();

        logFramePresenter = new cLogFramePresenterImpl(
                cThreadExecutorImpl.getInstance(),
                cMainThreadImpl.getInstance(),
                this,null
                /*new cSessionManagerImpl(getContext())*/,
                new cMenuRepositoryImpl(getContext()),
                new cLogFrameRepositoryImpl(getContext()));

        // setup recycler view adapter
        logFrameRecyclerViewAdapter = new cLogFrameAdapter(getActivity(), this,
                logFrameTreeModels);

        activity = ((AppCompatActivity) getActivity());
    }

    private void initLogframeView(View view) {
        includeProgressBar = view.findViewById(R.id.includeProgressBar);

        RecyclerView logFrameRecyclerView = view.findViewById(R.id.logframeRecyclerView);
        logFrameRecyclerView.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);

        logFrameRecyclerView.setAdapter(logFrameRecyclerViewAdapter);
        logFrameRecyclerView.setLayoutManager(llm);
    }

    private void initDraggableFAB(View view) {
        view.findViewById(R.id.logframeFAB).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cLogFrameModel logFrameModel = new cLogFrameModel();
                onClickCreateLogFrame(logFrameModel);
            }
        });
    }

    private void populateNavigationDrawer(View view) {
        // instantiating the expandable action_list view under the DrawerLayout
        menuExpandableListView = view.findViewById(R.id.navigationList);

        // adding the header to the expandable action_list view
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View headerView = inflater.inflate(R.layout.dashboard_drawer_nav_header,
                null, false);

        // instantiate header view objects
        ImageView userIcon = headerView.findViewById(R.id.userIcon);
        TextView currentDate = headerView.findViewById(R.id.currentDate);
        //TextView website = headerView.findViewById(R.id.website);

        // set header view objects
        //userIcon.setImageResource(...);
        currentDate.setText(ssdf.format(Calendar.getInstance().getTime()));
        //website.setText(sessionManager.getCurrentUser().getName()+" "+
        // sessionManager.getCurrentUser().getSurname());

        menuExpandableListView.addHeaderView(headerView);
    }

    private void setupDrawerToggle(View view) {
        /* initialize the toolbar */
        toolbar = view.findViewById(R.id.toolbar);
        activity.setSupportActionBar(toolbar);
        CollapsingToolbarLayout collapsingToolbarLayout =
                view.findViewById(R.id.collapsingToolbarLayout);
        collapsingToolbarLayout.setContentScrimColor(Color.WHITE);

        toolbar.setOnMenuItemClickListener(
                new Toolbar.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        return onOptionsItemSelected(item);
                    }
                });

        drawerLayout = view.findViewById(R.id.drawer_layout);

        drawerToggle = new ActionBarDrawerToggle(
                getActivity(),            // host activity
                drawerLayout,             // drawer layout
                toolbar,                  // custom toolbar
                R.string.drawer_open, // open drawer description
                R.string.drawer_close // close drawer description
        ) {

            /* Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                // creates call to onPrepareOptionsMenu()
                requireActivity().invalidateOptionsMenu();
            }

            /* Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                // creates call to onPrepareOptionsMenu()
                requireActivity().invalidateOptionsMenu();
            }
        };

        // show animations
        drawerLayout.addDrawerListener(drawerToggle);
        //
        drawerToggle.setDrawerIndicatorEnabled(true);
        // Sync the toggle state after onRestoreInstanceState has occured
        // and show the display menu icon
        drawerToggle.syncState();
    }

    private void setupDrawerNavigationListener() {
        // called when expanding...
        menuExpandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                Objects.requireNonNull(activity.getSupportActionBar()).setTitle(
                        menuItemTitles.get(groupPosition));
            }
        });

        // called when collapsing...
        menuExpandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
                Objects.requireNonNull(activity.getSupportActionBar()).setTitle(R.string.app_name);
            }
        });

        // called when clicking on parent menu item...
        menuExpandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            /**
             * Callback method to be invoked when a group in this expandable action_list has
             * been clicked.
             *
             * @param parent        The ExpandableListConnector where the click happened
             * @param v             The view within the expandable action_list/ListView that was clicked
             * @param groupPosition The group position that was clicked
             * @param id            The row id of the group that was clicked
             * @return True if the click was handled
             */
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                boolean retVal = true;
                int position = 0;

                switch (groupPosition) {
                    case 0: // Admin
                        retVal = false;
                        break;
                    case 1: // Profile
                        Toast.makeText(getActivity(), "Profile Fragment", Toast.LENGTH_SHORT).show();
                        break;
                    case 2: // Notification
                        Toast.makeText(getActivity(), "Notification Fragment", Toast.LENGTH_SHORT).show();
                        break;
                    case 3: // Settings
                        Intent intent = new Intent(activity, cSettingsActivity.class);
                        startActivity(intent);
                        break;
                    case 4: // Uploading
                        retVal = false;
                        Toast.makeText(getActivity(), "Uploading Fragment", Toast.LENGTH_SHORT).show();
                        break;
                    case 5: // Downloading
                        retVal = false;
                        Toast.makeText(getActivity(), "Downloading Fragment", Toast.LENGTH_SHORT).show();
                        break;
                    case 6: // Logout
                        //session.logoutUser();
                        //session.deleteSettings();
                        //session.commitSettings();
                        Toast.makeText(getActivity(), "Logout", Toast.LENGTH_SHORT).show();
                        break;

                    default:
                        break;
                }

                return retVal;
            }
        });

        // called when clicking on child menu item...
        menuExpandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {

                String selectedItem = ((List) (Objects.requireNonNull(
                        expandableMenuItems.get(menuItemTitles.get(groupPosition)))))
                        .get(childPosition).toString();
                Objects.requireNonNull(activity.getSupportActionBar()).setTitle(selectedItem);

                FragmentTransaction ft = requireActivity().
                        getSupportFragmentManager().beginTransaction();

                switch (groupPosition) {
                    case 0: // Admin
                        switch (childPosition) {
                            case 0: // Users
                                cUserFragment userFragment = cUserFragment.newInstance();
                                ft.replace(R.id.fragment_frame, userFragment);
                                ft.addToBackStack("USER");
                                ft.commit();
                                break;

                            case 1: // User Groups
                                cRoleFragment roleFragment = cRoleFragment.newInstance();
                                ft.replace(R.id.fragment_frame, roleFragment);
                                ft.addToBackStack("ROLE");
                                ft.commit();
                                break;

                            case 2: // Permissions Mgmt.
//                                cPermissionFragment_REMOVE permissionFragment = cPermissionFragment_REMOVE.newInstance();
//                                ft.replace(R.id.fragment_frame, permissionFragment);
//                                ft.addToBackStack("PERMISSION");
//                                ft.commit();
                                break;

                            default:
                                break;
                        }
                        break;
                    case 4:
                        switch (childPosition) {
                            case 0:
                                Toast.makeText(getActivity(), "RBAC Upload Fragment", Toast.LENGTH_SHORT).show();
                                break;
                            case 1:
                                Toast.makeText(getActivity(), "ME Upload Fragment", Toast.LENGTH_SHORT).show();
                                break;
                            default:
                                break;
                        }
                        break;

                    case 5:
                        switch (childPosition) {
                            case 0:
                                Toast.makeText(getActivity(), "RBAC Download Fragment", Toast.LENGTH_SHORT).show();
                                break;
                            case 1:
                                Toast.makeText(getActivity(), "ME Download Fragment", Toast.LENGTH_SHORT).show();
                                break;
                            default:
                                break;
                        }
                        break;

                    default:
                        break;
                }

                return true;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        //int id = item.getItemId();

        if (item.getItemId() == R.id.homeItem) {
            Log.d(TAG, "Stub for overflow menu");
        }

        // Activate the navigation drawer toggle
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getActivity().getMenuInflater().inflate(R.menu.drawer_menu_main, menu);
        inflater.inflate(R.menu.home_toolbar_menu, menu);

        //getting the search view from the menu
        MenuItem toolBarMenu = menu.findItem(R.id.searchItem);

        /* getting search manager from system service */
        SearchManager searchManager = (SearchManager) requireActivity().
                getSystemService(Context.SEARCH_SERVICE);
        /* getting the search view */
        SearchView searchView = (SearchView) toolBarMenu.getActionView();
        /* you can put a hint for the search input field */
        searchView.setQueryHint("Search LogFrames...");
        searchView.setSearchableInfo(Objects.requireNonNull(searchManager).
                getSearchableInfo(requireActivity().getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);
        /* by setting it true we are making it iconified
           so the search input will show up after taping the search iconified
           if you want to make it visible all the time make it false
         */
        //searchView.setIconifiedByDefault(true);
        search(searchView);

        /* end */

        super.onCreateOptionsMenu(menu, inflater);
    }

    private void search(SearchView searchView) {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                logFrameRecyclerViewAdapter.getFilter().filter(query);
                return false;
            }
        });
    }

    //=========== these functions are called in the adapter when clicking on the buttons ===========

    @Override
    public void onClickCreateLogFrame(cLogFrameModel logFrameModel) {
        createAlertDialog(-1, logFrameModel, true);
    }

    @Override
    public void onClickCreateSubLogFrame(long logFrameID, cLogFrameModel logFrameModel) {
        createAlertDialog(logFrameID, logFrameModel, false);
    }

    @Override
    public void onClickUpdateLogFrame(int position, cLogFrameModel logFrameModel) {
        updateAlertDialog(position, logFrameModel);
    }

    @Override
    public void onClickDeleteLogFrame(int position, long logframeID) {
        int resID = R.string.fa_delete;
        String title = "Delete Logframe.";
        String message = "Are you sure you want to delete this logframe ?";

        deleteAlertDialog(resID, title, message, position, logframeID);
    }

    @Override
    public void onClickDeleteSubLogFrame(int position, long logframeID) {

    }

    @Override
    public void onClickSyncLogFrame(cLogFrameModel logFrameModel) {
        /* change the colour of a sync button */
        //logFrameRecyclerViewAdapter.onClickLogframeSync(logFrameModel);
        logFramePresenter.syncLogFrameModel(logFrameModel);
    }

    @Override
    public void onClickBMBLogFrame(int menuIndex, long logFrameID) {
        NavDirections action;
        switch (menuIndex) {
            case 0: // Impact Fragment
                /* navigate from logframe to impact */
                action = cLogFrameFragmentDirections.
                        actionCLogFrameFragmentToCImpactFragment(logFrameID);
                Navigation.findNavController(requireView()).navigate(action);
                break;
            case 1: // Outcome Fragment
                /* navigate from logframe to outcome */
                action = cLogFrameFragmentDirections.
                        actionCLogFrameFragmentToCOutcomeFragment(logFrameID);
                Navigation.findNavController(requireView()).navigate(action);
                break;
            case 2: // Output Fragment
                /* navigate from logframe to output */
                action = cLogFrameFragmentDirections.
                        actionCLogFrameFragmentToCOutputFragment(logFrameID);
                Navigation.findNavController(requireView()).navigate(action);
                break;
            case 3: // Activity Fragment
                /* navigate from logframe to activity */
                action = cLogFrameFragmentDirections.
                        actionCLogFrameFragmentToCActivityFragment(logFrameID);
                Navigation.findNavController(requireView()).navigate(action);
                break;
            case 4: // Input Fragment
                /* navigate from logframe to input */
                action = cLogFrameFragmentDirections.
                        actionCLogFrameFragmentToCInputFragment(logFrameID);
                Navigation.findNavController(requireView()).navigate(action);
                break;
            case 5: // Key Performance Question (KPQ) Fragment
                /* navigate from logframe to question */
                action = cLogFrameFragmentDirections.
                        actionCLogFrameFragmentToCQuestionFragment(logFrameID);
                Navigation.findNavController(requireView()).navigate(action);
                break;
            case 6: // Key Performance Indicator (KPI) Fragment
                /* navigate from logframe to indicator */
                action = cLogFrameFragmentDirections.
                        actionCLogFrameFragmentToCIndicatorFragment(logFrameID);
                Navigation.findNavController(requireView()).navigate(action);
                break;
            case 7: // RAID Log Fragment
                /* navigate from logframe to RAID */
                action = cLogFrameFragmentDirections.
                        actionCLogFrameFragmentToCRaidLogFragment(logFrameID);
                Navigation.findNavController(requireView()).navigate(action);
                break;
            case 8: // AWPB Fragment
                /* navigate from logframe to AWPB */
                action = cLogFrameFragmentDirections.
                        actionCLogFrameFragmentToCAWPBFragment(logFrameID);
                Navigation.findNavController(requireView()).navigate(action);
                break;
            case 9: // Book Keeping Fragment
                /* navigate from logframe to book keeping */
                action = cLogFrameFragmentDirections.
                        actionCLogFrameFragmentToCBookKeepingFragment(logFrameID);
                Navigation.findNavController(requireView()).navigate(action);
                break;
            case 10: // Monitoring Fragment
                /* navigate from logframe to monitoring */
                action = cLogFrameFragmentDirections.
                        actionCLogFrameFragmentToCMonitoringFragment(logFrameID);
                Navigation.findNavController(requireView()).navigate(action);
                break;
            case 11: // Evaluation Fragment
                /* navigate from logframe to evaluation */
                action = cLogFrameFragmentDirections.
                        actionCLogFrameFragmentToCEvaluationFragment(logFrameID);
                Navigation.findNavController(requireView()).navigate(action);
                break;
            default:
                break;
        }
    }

    //========================== these functions give feedback to the user =========================

    @Override
    public void onRetrieveLogFramesCompleted(LinkedHashMap<String, List<String>> expandableMenuItems,
                                             ArrayList<cTreeModel> logFrameTreeModels) {
        /* populate navigation menu */
        menuItemTitles = new ArrayList<String>(expandableMenuItems.keySet());
        cExpandableListAdapter menuExpandableListAdapter = new cExpandableListAdapter(
                requireActivity(), menuItemTitles, expandableMenuItems);
        menuExpandableListView.setAdapter(menuExpandableListAdapter);

        /* logframe adapters */
        try {
            logFrameRecyclerViewAdapter.setTreeModel(logFrameTreeModels);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLogFrameCreated(cLogFrameModel frameModel, String msg) {
        try {
            int parentIndex = logFrameRecyclerViewAdapter.getMaxParentIndex();
            cTreeModel treeModel = new cTreeModel(parentIndex, -1, 0, frameModel);
            logFrameRecyclerViewAdapter.addData(treeModel);
            Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onSubLogFrameCreated(cLogFrameModel logFrameModel, String msg) {

    }

    @Override
    public void onLogFrameCreateFailed(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSubLogFrameCreateFailed(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLogFrameUpdated(cLogFrameModel frameModel, int position, String msg) {
        cTreeModel treeModel = new cTreeModel(frameModel);
        logFrameRecyclerViewAdapter.notifyTreeModelUpdated(treeModel, position);
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLogFrameUpdateFailed(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLogFrameDeleted(int position, String msg) {
        try {
            logFrameRecyclerViewAdapter.notifyTreeModelDeleted(position);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSubLogFrameDeleted(int position, String msg) {
        try {
            logFrameRecyclerViewAdapter.notifyTreeModelDeleted(position);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLogFrameDeleteFailed(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLogFrameSynced(cLogFrameModel logFrameModel) {

    }

    @Override
    public void onSubLogFrameDeleteFailed(String msg) {

    }

    @Override
    public void onRetrieveSharedOrgsCompleted(ArrayList<cOrganizationModel> organizationModels) {
        sharedOrganizations = organizationModels;
    }

    //========================= these functions show and hide progress bar =========================

    @Override
    public void showProgress() {
        includeProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        includeProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showError(String message) {

    }

    //======================= these function show the forms to capture data ========================

    /**
     *  create/add a logframe
     *
     * @param logFrameID logframe identification
     * @param logFrameModel logframe model
     * @param isLogFrame is this a parent or child logframe
     */
    private void createAlertDialog(long logFrameID, cLogFrameModel logFrameModel, boolean isLogFrame) {
        /* inflate the logframe model */
        LayoutInflater inflater = this.getLayoutInflater();
        View createView = inflater.inflate(R.layout.logframe_update, null);
        /* instantiates create views */
        TextView textViewTitle = createView.findViewById(R.id.textViewTitle);
        TextView textViewOrganization = createView.findViewById(R.id.textViewOrganization);
        cSingleSpinnerSearch singleSpinnerSearchOrg = createView.findViewById(R.id.singleSpinnerSearchOrg);
        AppCompatEditText editTextName = createView.findViewById(R.id.editTextName);
        AppCompatEditText editTextDescription = createView.findViewById(R.id.editTextDescription);
        AppCompatEditText startDateEditText = createView.findViewById(R.id.editTextStartDate);
        AppCompatEditText endDateEditText = createView.findViewById(R.id.editTextEndDate);
        TextView datePickerIcon = createView.findViewById(R.id.textViewDatePicker);

        /* set a title of the create view */
        textViewTitle.setText(getContext().getResources().getText(
                R.string.logframe_create_title));

        /* populate the logical model with the create views */
        /* 1. create selection dialog box for organizations */
        List<cKeyPairBoolData> keyPairBoolOrgs = new ArrayList<>();
        for (int i = 0; i < sharedOrganizations.size(); i++) {
            cKeyPairBoolData idNameBool = new cKeyPairBoolData();
            //--idNameBool.setId(sharedOrganizations.get(i).getOrganizationID());
            idNameBool.setName(sharedOrganizations.get(i).getName());
            idNameBool.setObject(sharedOrganizations.get(i));
            idNameBool.setSelected(false);

            keyPairBoolOrgs.add(idNameBool);
        }

        // called when click spinner
        singleSpinnerSearchOrg.setItem(keyPairBoolOrgs, -1, new cSingleSpinnerListener() {
            @Override
            public void onItemSelected(cKeyPairBoolData item) {
                /* assign selected organization name to the view */
                textViewOrganization.setText(item.getName());
                /* modify the organization ID for the logframe model */

                logFrameModel.setOrganizationID((int) item.getId());
                logFrameModel.getOrganizationModel().setName(item.getName());
            }
        });

        /* 4. set start and end date */
        datePickerIcon.setTypeface(null, Typeface.NORMAL);
        datePickerIcon.setTypeface(cFontManager.getTypeface(requireActivity(),
                        cFontManager.FONTAWESOME));
        datePickerIcon.setTextColor(requireActivity().getColor(R.color.colorPrimaryDark));
        datePickerIcon.setText(requireActivity().getResources().getString(R.string.fa_calendar));
        datePickerIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // date range picker
                MaterialDatePicker.Builder<Pair<Long, Long>> builder = MaterialDatePicker.Builder.
                        dateRangePicker();
                MaterialDatePicker<Pair<Long, Long>> picker = builder.build();

                picker.addOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        Log.d(TAG, "DatePicker Activity Dialog was cancelled");
                    }
                });

                picker.addOnPositiveButtonClickListener(
                        new MaterialPickerOnPositiveButtonClickListener<Pair<Long, Long>>() {
                            @Override
                            public void onPositiveButtonClick(Pair<Long, Long> selection) {
                                /* get start and end date from picker */
                                assert selection.first != null;
                                assert selection.second != null;
                                Date startDate = new Date(selection.first);
                                Date endDate = new Date(selection.second);
                                /* populate start and end dates on the view */
                                startDateEditText.setText(ssdf.format(startDate));
                                endDateEditText.setText(ssdf.format(endDate));

                                /* modify start and end dates of logframe model */
                                logFrameModel.setStartDate(Timestamp.valueOf(tsdf.format(startDate)));
                                logFrameModel.setEndDate(Timestamp.valueOf(tsdf.format(endDate)));

                                Log.d(TAG, "DatePicker Activity Date String =" +
                                        picker.getHeaderText() + " Date epoch values :: " +
                                        selection.first + " :: to :: " + selection.second);
                            }
                        });

                picker.addOnNegativeButtonClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.d(TAG, "DatePicker Activity Dialog Negative Button was clicked");
                    }
                });

                picker.show(requireFragmentManager(), picker.toString());
            }
        });

        /* create or cancel action */
        MaterialAlertDialogBuilder alertDialogBuilder =
                new MaterialAlertDialogBuilder(requireActivity(), R.style.AlertDialogTheme);
        alertDialogBuilder.setPositiveButton(getContext().getResources().getText(
                R.string.Save), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                /* 2. set name of the logframe */
                logFrameModel.setName(Objects.requireNonNull(editTextName.getText()).toString());
                /* 3. set description of the logframe */
                logFrameModel.setDescription(Objects.requireNonNull(editTextDescription.getText()).
                        toString());

                if(isLogFrame) {
                    logFramePresenter.createLogFrameModel(logFrameModel);
                }else {
                    logFramePresenter.createSubLogFrameModel(logFrameID, logFrameModel);
                }
            }
        });

        alertDialogBuilder.setNegativeButton(getContext().getResources().getText(
                R.string.Cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        })
                .setView(createView)
                .show();
    }

    /**
     * update a logframe
     *
     * @param position position
     * @param logFrameModel logframe model
     */
    private void updateAlertDialog(int position, cLogFrameModel logFrameModel) {
        /* inflates */
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.logframe_update, null);

        /* instantiate update views */
        TextView textViewTitle = dialogView.findViewById(R.id.textViewTitle);
        TextView textViewOrganization = dialogView.findViewById(R.id.textViewOrganization);
        cSingleSpinnerSearch singleSpinnerSearchOrg = dialogView.findViewById(
                R.id.singleSpinnerSearchOrg);
        AppCompatEditText editTextName = dialogView.findViewById(R.id.editTextName);
        AppCompatEditText editTextDescription = dialogView.findViewById(R.id.editTextDescription);
        AppCompatEditText startDateEditText = dialogView.findViewById(R.id.editTextStartDate);
        AppCompatEditText endDateEditText = dialogView.findViewById(R.id.editTextEndDate);
        TextView datePickerIcon = dialogView.findViewById(R.id.textViewDatePicker);

        /* populate the fields to be updated */
        textViewTitle.setText(getContext().getResources().getText(
                R.string.logframe_update_title));
        textViewOrganization.setText(logFrameModel.getOrganizationModel().getName());
        editTextName.setText(logFrameModel.getName());
        editTextDescription.setText(logFrameModel.getDescription());
        startDateEditText.setText(ssdf.format(logFrameModel.getStartDate()));
        endDateEditText.setText(ssdf.format(logFrameModel.getEndDate()));

        /* 1. set own organization */
        List<cKeyPairBoolData> keyPairBoolOrgs = new ArrayList<>();
        for (int i = 0; i < sharedOrganizations.size(); i++) {
            cKeyPairBoolData idNameBool = new cKeyPairBoolData();
            //--idNameBool.setId(sharedOrganizations.get(i).getOrganizationID());
            idNameBool.setName(sharedOrganizations.get(i).getName());
            idNameBool.setObject(sharedOrganizations.get(i));

            /* get the current organization ID */
            long organizationID = 0;//logFrameModel.getOrganizationModel().getOrganizationID();

            if (true/*(sharedOrganizations.get(i).getOrganizationID() == organizationID)*/) {
                idNameBool.setSelected(true);
                /* initialize organization ID for the logframe model */
                logFrameModel.setOrganizationID(organizationID);
            } else {
                idNameBool.setSelected(false);
            }

            keyPairBoolOrgs.add(idNameBool);
        }

        // called when click spinner
        singleSpinnerSearchOrg.setItem(keyPairBoolOrgs, -1, new cSingleSpinnerListener() {
            @Override
            public void onItemSelected(cKeyPairBoolData item) {
                /* assign selected organization name to the view */
                textViewOrganization.setText(item.getName());
                /* modify the organization ID for the logframe model */
                logFrameModel.setOrganizationID((int) item.getId());
                logFrameModel.getOrganizationModel().setName(item.getName());
            }
        });

        /* 3. set start and end dates */
        datePickerIcon.setTypeface(null, Typeface.NORMAL);
        datePickerIcon.setTypeface(cFontManager.getTypeface(requireActivity(),
                        cFontManager.FONTAWESOME));
        datePickerIcon.setTextColor(requireActivity().getColor(R.color.colorPrimaryDark));
        datePickerIcon.setText(requireActivity().getResources().getString(R.string.fa_calendar));
        datePickerIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // date range picker
                MaterialDatePicker.Builder<Pair<Long, Long>> builder = MaterialDatePicker.Builder.
                        dateRangePicker();
                MaterialDatePicker<Pair<Long, Long>> picker = builder.build();

                picker.addOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        Log.d(TAG, "DatePicker Activity Dialog was cancelled");
                    }
                });

                picker.addOnPositiveButtonClickListener(
                        new MaterialPickerOnPositiveButtonClickListener<Pair<Long, Long>>() {
                            @Override
                            public void onPositiveButtonClick(Pair<Long, Long> selection) {
                                /* get start and end date from picker */
                                assert selection.first != null;
                                assert selection.second != null;
                                Date startDate = new Date(selection.first);
                                Date endDate = new Date(selection.second);

                                /* populate start and end dates on the view */
                                startDateEditText.setText(ssdf.format(startDate));
                                endDateEditText.setText(ssdf.format(endDate));

                                /* modify start and end dates of logframe model */
                                logFrameModel.setStartDate(Timestamp.valueOf(tsdf.format(startDate)));
                                logFrameModel.setEndDate(Timestamp.valueOf(tsdf.format(endDate)));

                                Log.d(TAG, "DatePicker Activity Date String =" +
                                        picker.getHeaderText() + " Date epoch values :: " +
                                        selection.first + " :: to :: " + selection.second);
                            }
                        });

                picker.addOnNegativeButtonClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.d(TAG, "DatePicker Activity Dialog Negative Button was clicked");
                    }
                });

                picker.show(requireFragmentManager(), picker.toString());
            }
        });

        /* save or cancel actions */
        MaterialAlertDialogBuilder alertDialogBuilder =
                new MaterialAlertDialogBuilder(requireActivity(), R.style.AlertDialogTheme);
        alertDialogBuilder.setPositiveButton(getContext().getResources().getText(
                R.string.Save), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                /* 2. update the remaining view attributes to the logical model */
                logFrameModel.setName(Objects.requireNonNull(editTextName.getText()).toString());
                logFrameModel.setDescription(Objects.requireNonNull(editTextDescription.getText()).
                        toString());

                logFramePresenter.updateLogFrame(logFrameModel, position);

            }
        });

        alertDialogBuilder.setNegativeButton(getContext().getResources().getText(
                R.string.Cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        })
                .setView(dialogView)
                .show();
    }

    /**
     * delete a logframe
     *
     * @param resID resource identification
     * @param title title
     * @param message message
     * @param position position
     * @param logFrameID logframe identification
     */
    private void deleteAlertDialog(int resID, String title, String message, int position,
                                   long logFrameID) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                requireContext());

        // setting icon to dialog
        TextDrawable faIcon = new TextDrawable(requireContext());
        faIcon.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
        faIcon.setTextAlign(Layout.Alignment.ALIGN_CENTER);
        faIcon.setTypeface(cFontManager.getTypeface(requireContext(), cFontManager.FONTAWESOME));
        faIcon.setText(requireContext().getResources().getText(resID));
        faIcon.setTextColor(requireContext().getColor(R.color.colorAccent));
        alertDialogBuilder.setIcon(faIcon);

        // set title
        alertDialogBuilder.setTitle(title);

        // set dialog message
        alertDialogBuilder
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton(requireContext().getResources().getText(
                        R.string.Yes), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        logFramePresenter.deleteLogFrameModel(logFrameID, position);
                        dialog.dismiss();
                    }
                })
                .setNegativeButton(requireContext().getResources().getText(
                        R.string.No), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // if this button is clicked, just close
                        dialog.cancel();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }
}