package com.me.mseotsanyana.mande.PPMER.PL;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.GridView;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.me.mseotsanyana.mande.BRBAC.BLL.cSessionManager;
import com.me.mseotsanyana.mande.Util.cDashboardFilter;
import com.me.mseotsanyana.mande.PPMER.BLL.cActivityDomain;
import com.me.mseotsanyana.mande.PPMER.BLL.cActivityHandler;
import com.me.mseotsanyana.mande.PPMER.BLL.cGoalDomain;
import com.me.mseotsanyana.mande.PPMER.BLL.cGoalHandler;
import com.me.mseotsanyana.mande.BRBAC.BLL.cMenuDomain;
import com.me.mseotsanyana.mande.BRBAC.BLL.cMenuHandler;
import com.me.mseotsanyana.mande.BRBAC.BLL.cMenuRoleHandler;
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
import com.me.mseotsanyana.mande.BRBAC.BLL.cRoleDomain;
import com.me.mseotsanyana.mande.PPMER.BLL.cSpecificAimDomain;
import com.me.mseotsanyana.mande.PPMER.BLL.cSpecificAimHandler;
import com.me.mseotsanyana.mande.BRBAC.BLL.cUserRoleHandler;
import com.me.mseotsanyana.mande.Util.cExpandableListAdapter;
import com.me.mseotsanyana.mande.Util.cGridAdapter;
import com.me.mseotsanyana.mande.R;
import com.me.mseotsanyana.mande.cSettingsActivity;
import com.me.mseotsanyana.multiselectspinnerlibrary.cKeyPairBoolData;
import com.me.mseotsanyana.multiselectspinnerlibrary.cMultiSpinnerSearch;
import com.me.mseotsanyana.multiselectspinnerlibrary.cSpinnerListener;
import com.me.mseotsanyana.treeadapterlibrary.cTreeModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;


/**
 * Created by mseotsanyana on 2016/11/02.
 */
public class cMainFragment extends Fragment {
    private static final String TAG = "DashboardFragment";
    private static final String STATE_SELECTED_POSITION = "selected_navigation_drawer_position";

    // navigation drawer declarations
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private String[] items;

    private ExpandableListView expandableListView;
    private cExpandableListAdapter expandableListAdapter;
    private List<String> expandableListTitle;
    //private NavigationManager mNavigationManager;

    private HashMap<String, List<String>> expandableListDetail = new LinkedHashMap<String, List<String>>();

    //private NavigationView navigationView;
    //private DrawerLayout drawer;
    //private View navHeader;
    //private ImageView imgNavHeaderBg, imgProfile;
    //private TextView txtName, txtWebsite;

    //private FloatingActionButton fab;

    //private static final String FILE_NAME = "/tmp/MyFirstExcel.xlsx";

    // urls to load navigation header background image
    // and profile image
    //private static final String urlNavHeaderBg = "http://api.androidhive.info/images/nav-menu-header-bg.jpg";
    //private static final String urlProfileImg = "https://lh3.googleusercontent.com/eCtE_G34M9ygdkmOpYvCag1vBARCmZwnVS6rS5t4JLzJ6QgQSBquM0nuTsCpLhYbKljoyS-txg";

    // contains all selected organisations
    private ArrayList<cOrganizationDomain>  selectedOrganizations = new ArrayList<cOrganizationDomain>();

    private ArrayList<cTreeModel> selectedModel = new ArrayList<>();

    /** start declaration of filtering objects **/
    private View filterLayout;

    private cUserRoleHandler userRoleHandler;
    private cMenuRoleHandler menuRoleHandler;
    private cMenuHandler menuHandler;

    private cOrganizationHandler organizationHandler;
    private cGoalHandler goalHandler;
    private cProjectHandler projectHandler;
    private cOutcomeHandler outcomeHandler;
    private cOutputHandler outputHandler;
    private cActivityHandler activityHandler;
    private cSpecificAimHandler specificAimHandler;
    private cObjectiveHandler objectiveHandler;
    private cProjectOutcomeHandler projectOutcomeHandler;
    private cOutcomeOutputHandler outcomeOutputHandler;
    private cOutputActivityHandler outputActivityHandler;

    private cMultiSpinnerSearch organizationTreeSpinner;
    private cMultiSpinnerSearch goalTreeSpinner;
    private cMultiSpinnerSearch projectTreeSpinner;
    private cMultiSpinnerSearch outcomeTreeSpinner;
    private cMultiSpinnerSearch outputTreeSpinner;
    private cMultiSpinnerSearch activityTreeSpinner;

    // create a action_list of organization ids and names
    private List<cKeyPairBoolData> keyPairBoolOrganizationTree;
    // create a action_list of goal ids and names
    private List<cKeyPairBoolData> keyPairBoolGoalTree;
    // create a action_list of project ids and names
    private List<cKeyPairBoolData> keyPairBoolProjectTree;
    // create a action_list of outcome ids and names
    private List<cKeyPairBoolData> keyPairBoolOutcomeTree;
    // create a action_list of output ids and names
    private List<cKeyPairBoolData> keyPairBoolOutputTree;
    // create a action_list of activity ids and names
    private List<cKeyPairBoolData> keyPairBoolActivityTree;

    private ArrayList<cOrganizationDomain> organizationTree;
    private ArrayList<cGoalDomain> goalTree;
    private ArrayList<cProjectDomain> projectTree;
    private ArrayList<cOutcomeDomain> outcomeTree;
    private ArrayList<cOutputDomain> outputTree;
    private ArrayList<cActivityDomain> activityTree;

    private ArrayList<cProjectOutcomeDomain> projectOutcomeTree;
    private ArrayList<cOutcomeOutputDomain> outcomeOutputTree;
    private ArrayList<cOutputActivityDomain> outputActivityTree;

    /** end declaration of filtering objects **/

    // index to identify current nav menu item
    //private int navItemIndex = 0;

    // toolbar titles respected to selected nav menu item
    //private String[] fragmentTitles;

    // flag to load home fragment when user presses back key
    //private boolean shouldLoadHomeFragOnBackPress = true;

    GridView gridView;
    AppCompatActivity activity;

    private cSessionManager session;

    public cMainFragment() {
    }

    public static cMainFragment newInstance() {
        cMainFragment fragment = new cMainFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        session  = new cSessionManager(getActivity());

        userRoleHandler = new cUserRoleHandler(getActivity(), session);
        menuRoleHandler = new cMenuRoleHandler(getActivity(), session);
        menuHandler     = new cMenuHandler(getActivity());

        activity = ((AppCompatActivity) getActivity());

    }

    /**
     * the onCreateView method is called when fragment should create its View object
     * hierarchy either dynamically or via XML Layout inflation.
     */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_fragmant, container, false);

        // create navigation drawer menu
        navigationDrawer(view);

        // create dashboard menu
        dashboardView(view);


        return view;
    }

    /**
     * this event is triggered soon after on CreateView(). onViewCreated is called if the
     * view is returned from onCreateView() is non-null. Any view setup should occur here. e.g.
     * view lookups and attaching view listeners.
     */
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        // initialise a handler and get organization data from the database
        organizationHandler = new cOrganizationHandler(getActivity(), session);
        //  initialise a handler and get goal data from the database
        goalHandler = new cGoalHandler(getActivity());
        //  initialise a handler and get goal data from the database
        projectHandler = new cProjectHandler(getActivity());
        //  initialise a handler and get outcome data from the database
        outcomeHandler = new cOutcomeHandler(getActivity());
        //  initialise a handler and get output data from the database
        outputHandler = new cOutputHandler(getActivity());
        //  initialise a handler and get activity data from the database
        activityHandler = new cActivityHandler(getActivity());
        //  initialise a handler and get specific aim data from the database
        specificAimHandler = new cSpecificAimHandler(getActivity());
        //  initialise a handler and get objective data from the database
        objectiveHandler = new cObjectiveHandler(getActivity());

        //  initialise a handler and get project outcome data from the database
        projectOutcomeHandler = new cProjectOutcomeHandler(getActivity());
        //  initialise a handler and get outcome output data from the database
        outcomeOutputHandler = new cOutcomeOutputHandler(getActivity());
        //  initialise a handler and get outcome output data from the database
        outputActivityHandler = new cOutputActivityHandler(getActivity());
    }

    private void navigationDrawer(View view){
        // populate navigation view with relevant to the logged in user from database
        populateNavigationDrawer(view);
        // initialise the toolbar and the drawer layout for animating the menu
        setupDrawerToggle(view);
        // setup drawer navigation group and children listeners
        setupDrawerNavigationListener();

        // put an arrow button
        //activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //activity.getSupportActionBar().setHomeButtonEnabled(true);

    }

    private void populateNavigationDrawer(View view){
        // instantiating the expandable action_list view under the DrawerLayout
        expandableListView = (ExpandableListView) view.findViewById(R.id.navList);

        // adding the header to the expandable action_list view
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View headerView = inflater.inflate(R.layout.dashboard_drawer_nav_header, null, false);

        // instantiate header view objects
        ImageView userIcon   = (ImageView)headerView.findViewById(R.id.userIcon);
        TextView currentDate = (TextView)headerView.findViewById(R.id.currentDate);
        TextView website     = (TextView)headerView.findViewById(R.id.website);

        // set header view objects
        //userIcon.setImageResource(...);
        currentDate.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime()));
        //website.setText(sessionManager.getCurrentUser().getName()+" "+sessionManager.getCurrentUser().getSurname());


        expandableListView.addHeaderView(headerView);

        List<cRoleDomain> roleDomains = userRoleHandler.getRolesByUserID(session.loadUserID());
        //List<cRoleDomain> merged_list = new ArrayList<>();
        //Toast.makeText(getActivity(), "roleDomains = "+roleDomains.get(0).getName(), Toast.LENGTH_SHORT).show();
        List<cMenuDomain> menuDomains;
        for (int i = 0; i < roleDomains.size(); i++){
            //Toast.makeText(getActivity(), "roleDomains = "+roleDomains.get(i).getName(), Toast.LENGTH_SHORT).show();
            menuDomains = menuRoleHandler.getMenusByRoleID(roleDomains.get(i).getRoleID());
            for (int j = 0; j < menuDomains.size(); j++){
                List<String> subMenu = new ArrayList<String>();
                //Toast.makeText(getActivity(), "menuDomains = "+menuDomains.get(j).getName(), Toast.LENGTH_SHORT).show();

                List<cMenuDomain> subMenuDomains = menuHandler.getSubMenuByID(menuDomains.get(j).getMenuID());

                for (int k = 0; k < subMenuDomains.size(); k++){
                    //Toast.makeText(getActivity(), "subMenuDomains = "+subMenuDomains.get(k).getName(), Toast.LENGTH_SHORT).show();
                    subMenu.add(subMenuDomains.get(k).getName());
                }
                //Toast.makeText(getActivity(), "== END ==", Toast.LENGTH_SHORT).show();

                expandableListDetail.put(menuDomains.get(j).getName(), subMenu);
            }
        }

        expandableListTitle = new ArrayList<String>(expandableListDetail.keySet());
        expandableListAdapter = new cExpandableListAdapter(getActivity(), expandableListTitle, expandableListDetail);
        expandableListView.setAdapter(expandableListAdapter);
    }

    private void setupDrawerToggle(View view) {

        //for create home button
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        //Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        activity.setSupportActionBar(toolbar);

        drawerLayout = (DrawerLayout) view.findViewById(R.id.drawer_layout);
        //mActivityTitle = getTitle().toString();


        drawerToggle = new ActionBarDrawerToggle(
                getActivity(),        // host activity
                drawerLayout,         // drawer layout
                toolbar,              // custom toolbar
                R.string.drawer_open, // open drawer description
                R.string.drawer_close // close drawer description
        ) {

            /* Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getActivity().invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /* Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getActivity().invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
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
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                activity.getSupportActionBar().setTitle(expandableListTitle.get(groupPosition).toString());
            }
        });

        // called when collapsing...
        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
                activity.getSupportActionBar().setTitle(R.string.app_name);
            }
        });

        // called when clicking on parent menu item...
        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
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

                switch(groupPosition) {
                    case 0: // Admin
                        retVal = false;
                        break;
                    case 1: // Profile
                        Toast.makeText(getActivity(), "Profile Fragment" , Toast.LENGTH_SHORT).show();
                        break;
                    case 2: // Notification
                        Toast.makeText(getActivity(), "Notification Fragment" , Toast.LENGTH_SHORT).show();
                        break;
                    case 3: // Settings
                        Intent intent = new Intent(activity, cSettingsActivity.class);
                        startActivity(intent);
                        break;
                    case 4: // Uploading
                        retVal = false;
                        Toast.makeText(getActivity(), "Uploading Fragment" , Toast.LENGTH_SHORT).show();
                        break;
                    case 5: // Downloading
                        retVal = false;
                        Toast.makeText(getActivity(), "Downloading Fragment" , Toast.LENGTH_SHORT).show();
                        break;
                    case 6: // Logout
                        session.logoutUser();
                        session.deleteSettings();
                        session.commitSettings();
                        Toast.makeText(getActivity(), "Logout" , Toast.LENGTH_SHORT).show();
                        break;

                    default:
                        break;
                }

                ((OnGridViewItemSelectedListener) getActivity()).getGroupMenuPosition(groupPosition);

                return retVal;
            }
        });

        // called when clicking on child menu item...
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                String selectedItem = ((List) (expandableListDetail.get(expandableListTitle.get(groupPosition))))
                        .get(childPosition).toString();
                activity.getSupportActionBar().setTitle(selectedItem);

                switch (groupPosition){
                    case 0: // Admin
                        switch(childPosition) {
                            case 0: // Users
                                ((OnGridViewItemSelectedListener) getActivity()).getChildMenuPosition(childPosition);
                                break;
                            case 1: // User Groups
                                ((OnGridViewItemSelectedListener) getActivity()).getChildMenuPosition(childPosition);
                                break;
                            /*case 2: // Group Privileges
                                ((OnGridViewItemSelectedListener) getActivity()).getChildMenuPosition(childPosition);
                                break;*/
                            case 2: // Permissions Mgmt.
                                ((OnGridViewItemSelectedListener) getActivity()).getChildMenuPosition(childPosition);
                                break;

                            default:
                                break;
                        }
                        break;
                    case 4:
                        switch(childPosition) {
                            case 0:
                                Toast.makeText(getActivity(), "RBAC Upload Fragment" , Toast.LENGTH_SHORT).show();
                                break;
                            case 1:
                                Toast.makeText(getActivity(), "ME Upload Fragment" , Toast.LENGTH_SHORT).show();
                                break;
                            default:
                                break;
                        }
                        break;

                    case 5:
                        switch(childPosition) {
                            case 0:
                                Toast.makeText(getActivity(), "RBAC Download Fragment" , Toast.LENGTH_SHORT).show();
                                break;
                            case 1:
                                Toast.makeText(getActivity(), "ME Download Fragment" , Toast.LENGTH_SHORT).show();
                                break;
                            default:
                                break;
                        }
                        break;

                    default:
                        break;
                }

                //((OnGridViewItemSelectedListener) getActivity()).getChildMenuPosition(groupPosition, childPosition);

                return true;
            }
        });
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getActivity().getMenuInflater().inflate(R.menu.drawer_menu_main, menu);
        inflater.inflate(R.menu.drawer_menu_main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        //int id = item.getItemId();

        // Activate the navigation drawer toggle
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // dashboard view
    private void dashboardView(View view){
        final String[] tableDescription = {
                "Organization",
                "Triangle",
                "Goals",
                "Projects",
                "Outcomes",
                "Outputs",
                "Activities",
                "Indicators",
                "MoVs",
                "Risks",
                "Workplans",
                "Budgets",
                "Resources",
                "Evaluations",
                "Criteria",
                "Questions",
                "Categories",
                "Evaluating",
                "Monitoring",
                "Reports",
                "Alerts"
        };

        int[] imageId = {
                R.drawable.dashboard_organization,
                R.drawable.dashboard_triangle,
                R.drawable.dashboard_goal,
                R.drawable.dashboard_project,
                R.drawable.dashboard_outcome,
                R.drawable.dashboard_output,
                R.drawable.dashboard_activity,
                R.drawable.dashboard_indicator,
                R.drawable.dashboard_movs,
                R.drawable.dashboard_risk,
                R.drawable.dashboard_workplan,
                R.drawable.dashboard_budget,
                R.drawable.dashboard_resource,
                R.drawable.dashboard_evaluation,
                R.drawable.dashboard_criterion,
                R.drawable.dashboard_question,
                R.drawable.dashboard_category,
                R.drawable.dashboard_evaluating,
                R.drawable.dashboard_monitoring,
                R.drawable.dashboard_report,
                R.drawable.dashboard_notification
        };

        // create adapter for gridview
        cGridAdapter adapter = new cGridAdapter(getActivity(), tableDescription, imageId);

        gridView = (GridView)view.findViewById(R.id.grid);
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                LayoutInflater inflater = getActivity().getLayoutInflater();
                switch (position) {
                    case 0:
                        // get all organization from database
                        final ArrayList<cOrganizationDomain>  allOrganizations = organizationHandler.getOrganizationList();
                        // create a spinner for filtering purposes spinner
                        filterLayout = inflater.inflate(R.layout.organization_filter, null);

                        // create a action_list of organization ids and names
                        final List<cKeyPairBoolData> keyPairBoolDataList = new ArrayList<>();

                        for (int i = 0; i < allOrganizations.size(); i++) {
                            cKeyPairBoolData idNameBool = new cKeyPairBoolData();
                            idNameBool.setId(allOrganizations.get(i).getOrganizationID());
                            idNameBool.setName(allOrganizations.get(i).getOrganizationName());
                            idNameBool.setSelected(false);
                            keyPairBoolDataList.add(idNameBool);
                        }

                        /***
                         * -1 is no by default selection
                         * 0 to length will select corresponding values
                         */
                        cMultiSpinnerSearch searchMultiSpinnerUnlimited =
                                (cMultiSpinnerSearch) filterLayout.findViewById(R.id.spinnerOrganizationTree);
                        // called when click spinner
                        searchMultiSpinnerUnlimited.setItems(keyPairBoolDataList, -1, new cSpinnerListener() {
                            @Override
                            public void onItemsSelected(List<cKeyPairBoolData> items) {
                                for (int i = 0; i < items.size(); i++) {
                                    if (items.get(i).isSelected()){
                                        if (!selectedOrganizations.contains(allOrganizations.get(i))) {
                                            selectedOrganizations.add(allOrganizations.get(i));
                                        }
                                    }
                                    else if (!items.get(i).isSelected()){
                                        if (selectedOrganizations.contains(allOrganizations.get(i))) {
                                            selectedOrganizations.remove(i);
                                        }
                                    }
                                }
                            }
                        });

                        // update organization adapter before displaying spinner with organization
                        ((OnGridViewItemSelectedListener) getActivity()).getSelectedOrganizationList(selectedOrganizations);

                        // populate selected organizations
                        filterDialog(R.style.AnimateLeftRight,"Organizations Filter", filterLayout, position);
                        break;

                    case 1:
                        // get all CES from database
                        //final ArrayList<cGoalDomain> goalDomains = goalHandler.getGoalList();
                        final ArrayList<cSpecificAimDomain> specificAimDomains = specificAimHandler.getSpecificAimList();
                        final ArrayList<cObjectiveDomain> objectiveDomains = objectiveHandler.getObjectiveList();

                        // get all organization from database
                        final ArrayList<cOrganizationDomain>  organizationDomains = organizationHandler.getOrganizationList();

                        // create a spinner for filtering purposes spinner
                        filterLayout = inflater.inflate(R.layout.ces_filter, null);

                        // create a action_list of organization ids and names
                        final List<cKeyPairBoolData> keyPairBoolDatas = new ArrayList<>();

                        for (int i = 0; i < organizationDomains.size(); i++) {
                            cKeyPairBoolData idNameBool = new cKeyPairBoolData();
                            idNameBool.setId(organizationDomains.get(i).getOrganizationID());
                            idNameBool.setName(organizationDomains.get(i).getOrganizationName());
                            idNameBool.setSelected(false);
                            keyPairBoolDatas.add(idNameBool);
                        }

                        /***
                         * -1 is no by default selection
                         * 0 to length will select corresponding values
                         */
                        cMultiSpinnerSearch multiSpinnerSearch =
                                (cMultiSpinnerSearch) filterLayout.findViewById(R.id.spinnerCESTree);

                        // called when click spinner
                        multiSpinnerSearch.setItems(keyPairBoolDatas, -1, new cSpinnerListener() {
                            @Override
                            public void onItemsSelected(List<cKeyPairBoolData> items) {
                                //updateCESTree(items, goalDomains, specificAimDomains, objectiveDomains);
                            }
                        });

                        filterDialog(R.style.AnimateLeftRight,"CES Triagle Filter", filterLayout, position);

                        break;

                    case 2:
                        // 1. create goals filtering dialog box
                        filterLayout = inflater.inflate(R.layout.goal_filter, null);

                        // 2. initialise and attach the spinners to the filtering dialog box
                        organizationTreeSpinner =
                                (cMultiSpinnerSearch) filterLayout.findViewById(R.id.spinnerOrganizationTree);
                        goalTreeSpinner =
                                (cMultiSpinnerSearch) filterLayout.findViewById(R.id.spinnerGoatBottom);

                        // 3. populate the trees objects with the data from database
                        organizationTree = organizationHandler.getOrganizationList();
                        //goalTree = goalHandler.getGoalList();

                        // 4. populate (ids, names and bools) record with data for merging popurses
                        keyPairBoolOrganizationTree = cDashboardFilter.getKeyPairBoolOrganizationTree(organizationTree);
                        keyPairBoolGoalTree = cDashboardFilter.getKeyPairBoolGoalTree(goalTree);


                        /*
                        final cMultiSpinnerSearch organizationTreeSpinner =
                                (cMultiSpinnerSearch) filterLayout.findViewById(R.id.spinnerOrganizationTree);
                        final cMultiSpinnerSearch goalTreeSpinner =
                                (cMultiSpinnerSearch) filterLayout.findViewById(R.id.spinnerGoatTree);
                        */

                        // contains selected goals
                        //final ArrayList<cTreeModel>  selectedOrganizationTree = new ArrayList<>();
                        //final ArrayList<cTreeModel>  selectedGoalTree = new ArrayList<>();

                        // 5. called when click on organization spinner
                        organizationTreeSpinner.setItems(keyPairBoolOrganizationTree, -1, new cSpinnerListener() {
                            @Override
                            public void onItemsSelected(List<cKeyPairBoolData> items) {
                                for (int i = 0; i < items.size(); i++) {
                                    if (items.get(i).isSelected()) {
                                        for (int j = 0; j < keyPairBoolGoalTree.size(); j++) {
                                            if (items.get(i).getId() == keyPairBoolGoalTree.get(j).getRefId()) {
                                                // update the goals linked to the organization
                                                keyPairBoolGoalTree.get(j).setSelected(true);
                                                keyPairBoolOrganizationTree.get(i).setSelected(true);
                                                // update the spinner text with selected goals
                                                goalTreeSpinner.updateSpinnerText(keyPairBoolGoalTree);
                                                organizationTreeSpinner.updateSpinnerText(keyPairBoolOrganizationTree);
                                            }
                                        }
                                    }
                                    else {
                                        for (int j = 0; j < keyPairBoolGoalTree.size(); j++){
                                            if (items.get(i).getId() == keyPairBoolGoalTree.get(j).getRefId()) {
                                                // update the goals linked to the organization
                                                keyPairBoolGoalTree.get(j).setSelected(false);
                                                keyPairBoolOrganizationTree.get(i).setSelected(false);
                                                // update the spinner text with selected goals
                                                goalTreeSpinner.updateSpinnerText(keyPairBoolGoalTree);
                                                organizationTreeSpinner.updateSpinnerText(keyPairBoolOrganizationTree);
                                            }
                                        }
                                    }
                                }
                                // 6.
                                updateOrganizationGoalTree(keyPairBoolOrganizationTree, keyPairBoolGoalTree);
                            }
                        });

                        // 5. called when click on goal spinner
                        goalTreeSpinner.setItems(keyPairBoolGoalTree, -1, new cSpinnerListener() {
                            @Override
                            public void onItemsSelected(List<cKeyPairBoolData> items) {
                                for (int i = 0; i < items.size(); i++) {
                                    if (items.get(i).isSelected()){
                                        for (int j = 0; j < keyPairBoolOrganizationTree.size(); j++){
                                            if (items.get(i).getRefId() == keyPairBoolOrganizationTree.get(j).getId()) {
                                                // update the goals linked to the organization
                                                keyPairBoolOrganizationTree.get(j).setSelected(true);
                                                keyPairBoolGoalTree.get(i).setSelected(true);
                                                // update the spinner text with selected goals
                                                organizationTreeSpinner.updateSpinnerText(keyPairBoolOrganizationTree);
                                                goalTreeSpinner.updateSpinnerText(keyPairBoolGoalTree);
                                            }
                                        }
                                    }

                                    else {
                                        for (int j = 0; j < keyPairBoolOrganizationTree.size(); j++){
                                            if (items.get(i).getRefId() == keyPairBoolOrganizationTree.get(j).getId()) {
                                                // update the goals linked to the organization
                                                keyPairBoolOrganizationTree.get(j).setSelected(false);
                                                keyPairBoolGoalTree.get(i).setSelected(false);
                                                // update the spinner text with selected goals
                                                organizationTreeSpinner.updateSpinnerText(keyPairBoolOrganizationTree);
                                                goalTreeSpinner.updateSpinnerText(keyPairBoolGoalTree);
                                            }
                                        }
                                    }
                                }
                                // 6.
                                updateOrganizationGoalTree(keyPairBoolOrganizationTree, keyPairBoolGoalTree);
                            }
                        });

                        // show a filter dialog box
                        filterDialog(R.style.AnimateLeftRight,"Goals Filter", filterLayout, position);
                        break;

                    case 3:
                        // 1. create projects filtering dialog box
                        filterLayout = inflater.inflate(R.layout.project_filter, null);

                        // 2. initialise and attach the spinners to the filtering dialog box
                        goalTreeSpinner =
                                (cMultiSpinnerSearch) filterLayout.findViewById(R.id.spinnerGoalTop);
                        projectTreeSpinner =
                                (cMultiSpinnerSearch) filterLayout.findViewById(R.id.spinnerProjectTree);

                        // 3. populate the trees objects with the data from database
                        //goalTree = goalHandler.getGoalList();
                        //projectTree = projectHandler.getProjectList();

                        // 4. populate (ids, names and bools) record with data for merging popurses
                        keyPairBoolGoalTree = cDashboardFilter.getKeyPairBoolGoalTree(goalTree);
                        keyPairBoolProjectTree = cDashboardFilter.getKeyPairBoolProjectTree(projectTree);

                        // 5. called when click on goal spinner
                       goalTreeSpinner.setItems(keyPairBoolGoalTree, -1, new cSpinnerListener() {
                            @Override
                            public void onItemsSelected(List<cKeyPairBoolData> items) {
                                for (int i = 0; i < items.size(); i++) {
                                    if (items.get(i).isSelected()){
                                        for (int j = 0; j < keyPairBoolProjectTree.size(); j++){
                                            if (items.get(i).getId() == keyPairBoolProjectTree.get(j).getRefId()) {
                                                // update the goals linked to the organization
                                                keyPairBoolGoalTree.get(i).setSelected(true);
                                                keyPairBoolProjectTree.get(j).setSelected(true);
                                                // update the spinner text with selected goals
                                                goalTreeSpinner.updateSpinnerText(keyPairBoolGoalTree);
                                                projectTreeSpinner.updateSpinnerText(keyPairBoolProjectTree);

                                            }
                                        }
                                    }

                                    else {
                                        for (int j = 0; j < keyPairBoolProjectTree.size(); j++){
                                            if (items.get(i).getId() == keyPairBoolProjectTree.get(j).getRefId()) {
                                                // update the goals linked to the organization
                                                keyPairBoolGoalTree.get(i).setSelected(false);
                                                keyPairBoolProjectTree.get(j).setSelected(false);
                                                // update the spinner text with selected goals
                                                goalTreeSpinner.updateSpinnerText(keyPairBoolGoalTree);
                                                projectTreeSpinner.updateSpinnerText(keyPairBoolProjectTree);
                                            }
                                        }
                                    }
                                }
                                // 6.
                                updateGoalProjectTree(keyPairBoolGoalTree, keyPairBoolProjectTree);
                            }
                        });

                        // 5. called when click on project spinner
                        projectTreeSpinner.setItems(keyPairBoolProjectTree, -1, new cSpinnerListener() {
                            @Override
                            public void onItemsSelected(List<cKeyPairBoolData> items) {
                                for (int i = 0; i < items.size(); i++) {
                                    if (items.get(i).isSelected()) {
                                        for (int j = 0; j < keyPairBoolGoalTree.size(); j++) {
                                            if (items.get(i).getRefId() == keyPairBoolGoalTree.get(j).getId()) {
                                                // update the goals linked to the project
                                                keyPairBoolGoalTree.get(j).setSelected(true);
                                                keyPairBoolProjectTree.get(i).setSelected(true);
                                                // update the spinner text with selected goals
                                                goalTreeSpinner.updateSpinnerText(keyPairBoolGoalTree);
                                                projectTreeSpinner.updateSpinnerText(keyPairBoolProjectTree);
                                            }
                                        }
                                    }
                                    else {
                                        for (int j = 0; j < keyPairBoolGoalTree.size(); j++){
                                            if (items.get(i).getRefId() == keyPairBoolGoalTree.get(j).getId()) {
                                                // update the goals linked to the organization
                                                //keyPairBoolGoalTree.get(j).setSelected(false);
                                                keyPairBoolProjectTree.get(i).setSelected(false);
                                                // update the spinner text with selected goals
                                                goalTreeSpinner.updateSpinnerText(keyPairBoolGoalTree);
                                                projectTreeSpinner.updateSpinnerText(keyPairBoolProjectTree);
                                            }
                                        }
                                    }
                                }
                                // 6.
                                updateGoalProjectTree(keyPairBoolGoalTree, keyPairBoolProjectTree);
                            }
                        });

                        // 7.
                        filterDialog(R.style.AnimateLeftRight,"Projects Filter", filterLayout, position);
                        break;

                    case 4:
                        // 1. create outcomes filtering dialog box
                        filterLayout = inflater.inflate(R.layout.outcome_filter, null);

                        // 2. initialise and attach the spinners to the filtering dialog box
                        projectTreeSpinner =
                                (cMultiSpinnerSearch) filterLayout.findViewById(R.id.spinnerProjectTop);
                        outcomeTreeSpinner =
                                (cMultiSpinnerSearch) filterLayout.findViewById(R.id.spinnerOutcomeTree);

                        // 3. populate the trees objects with the data from database
                        //projectTree = projectHandler.getProjectList();
                        //outcomeTree = outcomeHandler.getOutcomeList();

                        //projectOutcomeTree = projectOutcomeHandler.getProjectOutcomeList();

                        // 4. populate (ids, names and bools) record with data for merging popurses
                        keyPairBoolProjectTree = cDashboardFilter.getKeyPairBoolProjectTree(projectTree);
                        keyPairBoolOutcomeTree = cDashboardFilter.getKeyPairBoolOutcomeTree(outcomeTree);

                        // 5. called when click on project spinner
                        projectTreeSpinner.setItems(keyPairBoolProjectTree, -1, new cSpinnerListener() {
                            @Override
                            public void onItemsSelected(List<cKeyPairBoolData> items) {
                                for (int i = 0; i < keyPairBoolOutcomeTree.size(); i++){
                                    keyPairBoolOutcomeTree.get(i).setSelected(false);
                                }

                                for (int i = 0; i < items.size(); i++) {
                                    if (items.get(i).isSelected()){
                                        // update the corresponding outcomes
                                        selectedKeyPairBoolProjectOutcome((int)items.get(i).getId());
                                        // update the selected project
                                        keyPairBoolProjectTree.get(i).setSelected(true);
                                    }

                                    else {
                                        // update the selected project
                                        keyPairBoolProjectTree.get(i).setSelected(false);
                                    }
                                }
                                // update the spinner items
                                projectTreeSpinner.updateSpinnerText(keyPairBoolProjectTree);
                                outcomeTreeSpinner.updateSpinnerText(keyPairBoolOutcomeTree);
                                // 6. update the tree model accordingly
                                updateProjectOutcomeTree(keyPairBoolProjectTree, keyPairBoolOutcomeTree);
                            }
                        });

                        // 5. called when click on outcome spinner
                        outcomeTreeSpinner.setItems(keyPairBoolOutcomeTree, -1, new cSpinnerListener() {
                            @Override
                            public void onItemsSelected(List<cKeyPairBoolData> items) {
                                for (int i = 0; i < keyPairBoolProjectTree.size(); i++){
                                    keyPairBoolProjectTree.get(i).setSelected(false);
                                }

                                for (int i = 0; i < items.size(); i++) {
                                    if (items.get(i).isSelected()) {
                                        // update the corresponding projects
                                        selectedKeyPairBoolOutcomeProject((int)items.get(i).getId());
                                        // update the selected outcome
                                        keyPairBoolOutcomeTree.get(i).setSelected(true);

                                    }
                                    else {
                                        // update the selected projects
                                        keyPairBoolOutcomeTree.get(i).setSelected(false);

                                    }
                                }
                                // update the spinner text with selected goals
                                projectTreeSpinner.updateSpinnerText(keyPairBoolProjectTree);
                                outcomeTreeSpinner.updateSpinnerText(keyPairBoolOutcomeTree);
                                // 6. update the tree model accordingly
                                updateProjectOutcomeTree(keyPairBoolProjectTree, keyPairBoolOutcomeTree);
                            }
                        });

                        // 7.
                        filterDialog(R.style.AnimateLeftRight,"Outcome Filter", filterLayout, position);
                        break;

                    case 5:
                        // 1. create output filtering dialog box
                        filterLayout = inflater.inflate(R.layout.output_filter, null);

                        // 2. initialise and attach the spinners to the filtering dialog box
                        outcomeTreeSpinner =
                                (cMultiSpinnerSearch) filterLayout.findViewById(R.id.spinnerOutcomeTop);
                        outputTreeSpinner =
                                (cMultiSpinnerSearch) filterLayout.findViewById(R.id.spinnerOutputTree);

                        // 3. populate the trees objects with the data from database
                        //outcomeTree = outcomeHandler.getOutcomeList();
                        //outputTree = outputHandler.getOutputList();

                        //outcomeOutputTree = outcomeOutputHandler.getOutcomeOutputList();

                        // 4. populate (ids, names and bools) record with data for merging popurses
                        keyPairBoolOutcomeTree = cDashboardFilter.getKeyPairBoolOutcomeTree(outcomeTree);
                        keyPairBoolOutputTree = cDashboardFilter.getKeyPairBoolOutputTree(outputTree);

                        // 5. called when click on outcome spinner
                        outcomeTreeSpinner.setItems(keyPairBoolOutcomeTree, -1, new cSpinnerListener() {
                            @Override
                            public void onItemsSelected(List<cKeyPairBoolData> items) {
                                for (int i = 0; i < keyPairBoolOutputTree.size(); i++){
                                    keyPairBoolOutputTree.get(i).setSelected(false);
                                }

                                for (int i = 0; i < items.size(); i++) {
                                    if (items.get(i).isSelected()){
                                        // update the corresponding outcomes
                                        selectedKeyPairBoolOutcomeOutput((int)items.get(i).getId());
                                        // update the selected outcome
                                        keyPairBoolOutcomeTree.get(i).setSelected(true);
                                    }

                                    else {
                                        // update the selected outcome
                                        keyPairBoolOutcomeTree.get(i).setSelected(false);
                                    }
                                }
                                // update the spinner items
                                outcomeTreeSpinner.updateSpinnerText(keyPairBoolOutcomeTree);
                                outputTreeSpinner.updateSpinnerText(keyPairBoolOutputTree);
                                // 6. update the tree model accordingly
                                updateOutcomeOutputTree(keyPairBoolOutcomeTree, keyPairBoolOutputTree);
                            }
                        });

                        // 5. called when click on output spinner
                        outputTreeSpinner.setItems(keyPairBoolOutputTree, -1, new cSpinnerListener() {
                            @Override
                            public void onItemsSelected(List<cKeyPairBoolData> items) {
                                for (int i = 0; i < keyPairBoolOutcomeTree.size(); i++){
                                    keyPairBoolOutcomeTree.get(i).setSelected(false);
                                }

                                for (int i = 0; i < items.size(); i++) {
                                    if (items.get(i).isSelected()) {
                                        // update the corresponding outcome
                                        selectedKeyPairBoolOutputOutcome((int)items.get(i).getId());
                                        // update the selected outcome
                                        keyPairBoolOutputTree.get(i).setSelected(true);

                                    }
                                    else {
                                        // update the selected outcomes
                                        keyPairBoolOutputTree.get(i).setSelected(false);

                                    }
                                }
                                // update the spinner text with selected goals
                                outcomeTreeSpinner.updateSpinnerText(keyPairBoolOutcomeTree);
                                outputTreeSpinner.updateSpinnerText(keyPairBoolOutputTree);
                                // 6. update the tree model accordingly
                                updateOutcomeOutputTree(keyPairBoolOutcomeTree, keyPairBoolOutputTree);
                            }
                        });

                        // 7.
                        filterDialog(R.style.AnimateLeftRight,"Output Filter", filterLayout, position);

                        break;
                    case 6:
                        // 1. create activity filtering dialog box
                        filterLayout = inflater.inflate(R.layout.activity_filter, null);

                        // 2. initialise and attach the spinners to the filtering dialog box
                        outputTreeSpinner =
                                (cMultiSpinnerSearch) filterLayout.findViewById(R.id.spinnerOutputTop);
                        activityTreeSpinner =
                                (cMultiSpinnerSearch) filterLayout.findViewById(R.id.spinnerActivityTree);

                        // 3. populate the trees objects with the data from database
                        //outputTree = outputHandler.getOutputList();
                        //activityTree = activityHandler.getActivityList();

                        //outputActivityTree = outputActivityHandler.getOutputActivityList();

                        // 4. populate (ids, names and bools) record with data for merging popurses
                        keyPairBoolOutputTree   = cDashboardFilter.getKeyPairBoolOutputTree(outputTree);
                        keyPairBoolActivityTree = cDashboardFilter.getKeyPairBoolActivityTree(activityTree);

                        // 5. called when click on output spinner
                        outputTreeSpinner.setItems(keyPairBoolOutputTree, -1, new cSpinnerListener() {
                            @Override
                            public void onItemsSelected(List<cKeyPairBoolData> items) {
                                for (int i = 0; i < keyPairBoolActivityTree.size(); i++){
                                    keyPairBoolActivityTree.get(i).setSelected(false);
                                }

                                for (int i = 0; i < items.size(); i++) {
                                    if (items.get(i).isSelected()){
                                        // update the corresponding outcomes
                                        selectedKeyPairBoolOutputActivity((int)items.get(i).getId());
                                        // update the selected outcome
                                        keyPairBoolOutputTree.get(i).setSelected(true);
                                    }

                                    else {
                                        // update the selected outcome
                                        keyPairBoolOutputTree.get(i).setSelected(false);
                                    }
                                }
                                // update the spinner items
                                outputTreeSpinner.updateSpinnerText(keyPairBoolOutputTree);
                                activityTreeSpinner.updateSpinnerText(keyPairBoolActivityTree);
                                // 6. update the tree model accordingly
                                updateOutputActivityTree(keyPairBoolOutputTree, keyPairBoolActivityTree);
                            }
                        });

                        // 5. called when click on activity spinner
                        activityTreeSpinner.setItems(keyPairBoolActivityTree, -1, new cSpinnerListener() {
                            @Override
                            public void onItemsSelected(List<cKeyPairBoolData> items) {
                                for (int i = 0; i < keyPairBoolOutputTree.size(); i++){
                                    keyPairBoolOutputTree.get(i).setSelected(false);
                                }

                                for (int i = 0; i < items.size(); i++) {
                                    if (items.get(i).isSelected()) {
                                        // update the corresponding outcome
                                        selectedKeyPairBoolActivityOutput((int)items.get(i).getId());
                                        // update the selected outcome
                                        keyPairBoolActivityTree.get(i).setSelected(true);

                                    }
                                    else {
                                        // update the selected outcomes
                                        keyPairBoolActivityTree.get(i).setSelected(false);

                                    }
                                }
                                // update the spinner text with selected goals
                                outputTreeSpinner.updateSpinnerText(keyPairBoolOutputTree);
                                activityTreeSpinner.updateSpinnerText(keyPairBoolActivityTree);
                                // 6. update the tree model accordingly
                                updateOutputActivityTree(keyPairBoolOutputTree, keyPairBoolActivityTree);
                            }
                        });

                        // 7.
                        filterDialog(R.style.AnimateLeftRight,"Activity Filter", filterLayout, position);

                        break;
                    case 7:
                        filterLayout = inflater.inflate(R.layout.organization_filter, null);
                        //filterDialog(R.style.AnimateLeftRight,"Indicators Filter", filterLayout, position);
                        break;
                    case 8:
                        filterLayout = inflater.inflate(R.layout.organization_filter, null);
                        //filterDialog(R.style.AnimateLeftRight,"MoVs Filter", filterLayout, position);
                        break;
                    case 9:
                        filterLayout = inflater.inflate(R.layout.organization_filter, null);
                        //filterDialog(R.style.AnimateLeftRight,"Risks Filter", filterLayout, position);
                        break;
                    case 10:
                        filterLayout = inflater.inflate(R.layout.organization_filter, null);
                        //filterDialog(R.style.AnimateLeftRight,"Workplans Filter", filterLayout, position);
                        break;
                    case 11:
                        filterLayout = inflater.inflate(R.layout.organization_filter, null);
                        //filterDialog(R.style.AnimateLeftRight,"Budgets Filter", filterLayout, position);
                        break;
                    case 12:
                        filterLayout = inflater.inflate(R.layout.organization_filter, null);
                        //filterDialog(R.style.AnimateLeftRight,"Resources Filter", filterLayout, position);
                        break;
                    case 13:
                        filterLayout = inflater.inflate(R.layout.organization_filter, null);
                        //filterDialog(R.style.AnimateLeftRight,"Evaluations Filter", filterLayout, position);
                        break;
                    case 14:
                        filterLayout = inflater.inflate(R.layout.organization_filter, null);
                        //filterDialog(R.style.AnimateLeftRight,"Criteria Filter", filterLayout, position);
                        break;
                    case 15:
                        filterLayout = inflater.inflate(R.layout.organization_filter, null);
                        //filterDialog(R.style.AnimateLeftRight,"Questions Filter", filterLayout, position);
                        break;
                    case 16:
                        filterLayout = inflater.inflate(R.layout.organization_filter, null);
                        //filterDialog(R.style.AnimateLeftRight,"Categories Filter", filterLayout, position);
                        break;
                    case 17:
                        filterLayout = inflater.inflate(R.layout.organization_filter, null);
                        //filterDialog(R.style.AnimateLeftRight,"Evaluating Filter", filterLayout, position);
                        break;
                    case 18:
                        filterLayout = inflater.inflate(R.layout.organization_filter, null);
                        //filterDialog(R.style.AnimateLeftRight,"Monitoring Filter", filterLayout, position);
                        break;
                    case 19:
                        filterLayout = inflater.inflate(R.layout.organization_filter, null);
                        //filterDialog(R.style.AnimateLeftRight,"Reports Filter", filterLayout, position);
                        break;
                    case 20:
                        filterLayout = inflater.inflate(R.layout.organization_filter, null);
                        //filterDialog(R.style.AnimateLeftRight,"Alerts Filter", filterLayout, position);
                        break;
                    default:
                        break;
                }
            }
        });

    }

    // create a tree model for organization -> goal
    void updateOrganizationGoalTree(List<cKeyPairBoolData> organizationTree, List<cKeyPairBoolData> goalTree){
        ArrayList<cTreeModel> treeModelList = new ArrayList<>();
        int parentID = 0;
        int childID  = 0;
        for (int i = 0; i < organizationTree.size(); i++) {
            if (organizationTree.get(i).isSelected()) {
                treeModelList.add(new cTreeModel(parentID, -1, 0, organizationTree.get(i).getObject()));

                int organizationID = ((cOrganizationDomain) organizationTree.get(i).getObject()).getOrganizationID();
                for (int j = 0; j < goalTree.size(); j++) {
                    int organization_FK_ID = ((cGoalDomain) goalTree.get(j).getObject()).getOrganizationID();
                    if (organizationID == organization_FK_ID) {

                        childID = childID + 1;
                        treeModelList.add(new cTreeModel(childID, parentID, 1, goalTree.get(j).getObject()));
                    }
                }
                parentID = childID + 1;
            }
        }
        // update the goal tree model (used for all tree updates)
        setSelectedModel(treeModelList);
    }

    // create a tree model for CES
    void updateCESTree(List<cKeyPairBoolData> selectedOrganizations,
                       ArrayList<cGoalDomain> goalDomains,
                       ArrayList<cSpecificAimDomain> specificAimDomains,
                       ArrayList<cObjectiveDomain> objectiveDomains){
        ArrayList<cTreeModel> treeModelList = new ArrayList<>();
        int overallAimID  = 0;
        int specificAimID = 0;

        int indexID = 0;

        for (int i = 0; i < selectedOrganizations.size(); i++) {
            if (selectedOrganizations.get(i).isSelected()) {
                for (int j = 0; j < goalDomains.size(); j++){
                    if (selectedOrganizations.get(i).getId() == goalDomains.get(j).getOrganizationID()) {
                        treeModelList.add(new cTreeModel(indexID, -1, 0, goalDomains.get(j)));
                        overallAimID = indexID;
                        indexID      = indexID + 1;
                        for (int k = 0; k < specificAimDomains.size(); k++) {
                            if (goalDomains.get(j).getGoalID() == specificAimDomains.get(k).getOverallAimID()) {
                                treeModelList.add(new cTreeModel(indexID, overallAimID, 1, specificAimDomains.get(k)));
                                specificAimID = indexID;
                                indexID       = indexID + 1;

                                for (int l = 0; l < objectiveDomains.size(); l++) {
                                    if (specificAimDomains.get(k).getProjectID() == objectiveDomains.get(l).getProjectID()) {
                                        treeModelList.add(new cTreeModel(indexID, specificAimID, 2, objectiveDomains.get(l)));
                                        indexID = indexID + 1;
                                    }
                                }
                            }
                        }
                    }

                }
            }
        }

        // update the goal tree model (used for all tree updates)
        setSelectedModel(treeModelList);
    }

    // create a tree model for goal -> project
    void updateGoalProjectTree(List<cKeyPairBoolData> goalTree, List<cKeyPairBoolData> projectTree){
        ArrayList<cTreeModel> treeModelList = new ArrayList<>();
        int parentID = 0;
        int childID  = 0;
        for (int i = 0; i < goalTree.size(); i++) {
            if (goalTree.get(i).isSelected()) {
                treeModelList.add(new cTreeModel(parentID, -1, 0, goalTree.get(i).getObject()));

                int goalID = ((cGoalDomain) goalTree.get(i).getObject()).getGoalID();
                for (int j = 0; j < projectTree.size(); j++) {
                    if (projectTree.get(j).isSelected()) {
                        int goal_FK_ID = ((cProjectDomain) projectTree.get(j).getObject()).getOverallAimID();
                        if (goalID == goal_FK_ID) {
                            childID = childID + 1;
                            treeModelList.add(new cTreeModel(childID, parentID, 1, projectTree.get(j).getObject()));
                        }
                    }
                }
                parentID = childID + 1;
            }
        }
        // update the goal tree model (used for all tree updates)
        setSelectedModel(treeModelList);
    }

    // create a tree model for project -> outcome
    void updateProjectOutcomeTree(List<cKeyPairBoolData> projectTree, List<cKeyPairBoolData> outcomeTree){
        ArrayList<cTreeModel> treeModelList = new ArrayList<>();
        int parentID = 0;
        int childID  = 0;
        for (int i = 0; i < projectTree.size(); i++) {
            if (projectTree.get(i).isSelected()) {
                treeModelList.add(new cTreeModel(parentID, -1, 0, projectTree.get(i).getObject()));
                for (int j = 0; j < projectOutcomeTree.size(); j++){
                    if (projectTree.get(i).getId() == projectOutcomeTree.get(j).getProjectID()){
                        for (int k = 0; k < outcomeTree.size(); k++){
                            if ((outcomeTree.get(k).getId() == projectOutcomeTree.get(j).getOutcomeID()) && (outcomeTree.get(k).isSelected())){
                                childID = childID + 1;
                                treeModelList.add(new cTreeModel(childID, parentID, 1, outcomeTree.get(k).getObject()));
                            }
                        }
                    }
                }
                parentID = childID + 1;
                childID  = parentID;
            }
        }
        // update the goal tree model (used for all tree updates)
        setSelectedModel(treeModelList);
    }

    // create a tree model for outcome -> output
    void updateOutcomeOutputTree(List<cKeyPairBoolData> outcomeTree, List<cKeyPairBoolData> outputTree){
        ArrayList<cTreeModel> treeModelList = new ArrayList<>();
        int parentID = 0;
        int childID  = 0;
        for (int i = 0; i < outcomeTree.size(); i++) {
            if (outcomeTree.get(i).isSelected()) {
                treeModelList.add(new cTreeModel(parentID, -1, 0, outcomeTree.get(i).getObject()));
                for (int j = 0; j < outcomeOutputTree.size(); j++){
                    if (outcomeTree.get(i).getId() == outcomeOutputTree.get(j).getOutcomeID()){
                        for (int k = 0; k < outputTree.size(); k++){
                            if ((outputTree.get(k).getId() == outcomeOutputTree.get(j).getOutputID()) && (outputTree.get(k).isSelected())){
                                childID = childID + 1;
                                treeModelList.add(new cTreeModel(childID, parentID, 1, outputTree.get(k).getObject()));
                            }
                        }
                    }
                }
                parentID = childID + 1;
                childID  = parentID;
            }
        }
        // update the goal tree model (used for all tree updates)
        setSelectedModel(treeModelList);
    }

    // create a tree model for outcome -> output
    void updateOutputActivityTree(List<cKeyPairBoolData> outputTree, List<cKeyPairBoolData> activityTree){
        ArrayList<cTreeModel> treeModelList = new ArrayList<>();
        int parentID = 0;
        int childID  = 0;
        for (int i = 0; i < outputTree.size(); i++) {
            if (outputTree.get(i).isSelected()) {
                treeModelList.add(new cTreeModel(parentID, -1, 0, outputTree.get(i).getObject()));
                for (int j = 0; j < outputActivityTree.size(); j++){
                    if (outputTree.get(i).getId() == outputActivityTree.get(j).getOutputID()){
                        for (int k = 0; k < activityTree.size(); k++){
                            if ((activityTree.get(k).getId() == outputActivityTree.get(j).getActivityID()) && (activityTree.get(k).isSelected())){
                                childID = childID + 1;
                                treeModelList.add(new cTreeModel(childID, parentID, 1, activityTree.get(k).getObject()));
                            }
                        }
                    }
                }
                parentID = childID + 1;
                childID  = parentID;
            }
        }
        // update the goal tree model (used for all tree updates)
        setSelectedModel(treeModelList);
    }

    // using the selected outcomes to select corresponding projects
    void selectedKeyPairBoolOutcomeProject(int outcomeID){
        for (int i = 0; i < projectOutcomeTree.size(); i++){
            if (projectOutcomeTree.get(i).getOutcomeID() == outcomeID){
                for (int j = 0; j < keyPairBoolProjectTree.size(); j++){
                    if (keyPairBoolProjectTree.get(j).getId() ==
                            projectOutcomeTree.get(i).getProjectID()){
                        keyPairBoolProjectTree.get(j).setSelected(true);
                    }
                }
            }
        }
    }

    // using the selected projects to select corresponding outcomes
    void selectedKeyPairBoolProjectOutcome(int projectID){
        for (int i = 0; i < projectOutcomeTree.size(); i++){
            if (projectOutcomeTree.get(i).getProjectID() == projectID){
                for (int j = 0; j < keyPairBoolOutcomeTree.size(); j++){
                    if (keyPairBoolOutcomeTree.get(j).getId() ==
                            projectOutcomeTree.get(i).getOutcomeID()){
                        keyPairBoolOutcomeTree.get(j).setSelected(true);
                    }
                }
            }
        }
    }

    // using the selected outcomes to select corresponding outputs
    void selectedKeyPairBoolOutcomeOutput(int outcomeID){
        for (int i = 0; i < outcomeOutputTree.size(); i++){
            if (outcomeOutputTree.get(i).getOutcomeID() == outcomeID){
                for (int j = 0; j < keyPairBoolOutputTree.size(); j++){
                    if (keyPairBoolOutputTree.get(j).getId() ==
                            outcomeOutputTree.get(i).getOutputID()){
                        keyPairBoolOutputTree.get(j).setSelected(true);
                    }
                }
            }
        }
    }

    // using the selected outputs to select corresponding outcomes
    void selectedKeyPairBoolOutputOutcome(int outputID){
        for (int i = 0; i < outcomeOutputTree.size(); i++){
            if (outcomeOutputTree.get(i).getOutputID() == outputID){
                for (int j = 0; j < keyPairBoolOutcomeTree.size(); j++){
                    if (keyPairBoolOutcomeTree.get(j).getId() ==
                            outcomeOutputTree.get(i).getOutcomeID()){
                        keyPairBoolOutcomeTree.get(j).setSelected(true);
                    }
                }
            }
        }
    }

    // using the selected outputs to select corresponding activity
    void selectedKeyPairBoolOutputActivity(int outputID){
        for (int i = 0; i < outputActivityTree.size(); i++){
            if (outputActivityTree.get(i).getOutputID() == outputID){
                for (int j = 0; j < keyPairBoolActivityTree.size(); j++){
                    if (keyPairBoolActivityTree.get(j).getId() ==
                            outputActivityTree.get(i).getActivityID()){
                        keyPairBoolActivityTree.get(j).setSelected(true);
                    }
                }
            }
        }
    }

    // using the selected activity to select corresponding output
    void selectedKeyPairBoolActivityOutput(int activityID){
        for (int i = 0; i < outputActivityTree.size(); i++){
            if (outputActivityTree.get(i).getActivityID() == activityID){
                for (int j = 0; j < keyPairBoolOutputTree.size(); j++){
                    if (keyPairBoolOutputTree.get(j).getId() ==
                            outputActivityTree.get(i).getOutputID()){
                        keyPairBoolOutputTree.get(j).setSelected(true);
                    }
                }
            }
        }
    }


    private void filterDialog(int animationSource, String title, View filterLayout, final int position){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title);
        builder.setView(filterLayout);
        builder.setPositiveButton(
                "Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        try{
                            // update a relavent adapter (getSelectedTreeModel used for all trees)
                            ((OnGridViewItemSelectedListener) getActivity()).getSelectedTreeModel(getSelectedModel());

                            // select a relavent fragment through a position
                            ((OnGridViewItemSelectedListener) getActivity()).getGridPosition(position);
                        }catch (ClassCastException cce){

                        }
                    }
                }
        );

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // dismiss
            }
        });
        AlertDialog dialog = builder.create();
        dialog.getWindow().getAttributes().windowAnimations = animationSource;
        builder.show();
    }

    public void setSelectedModel(ArrayList<cTreeModel> selectedModel) {
        this.selectedModel = selectedModel;
    }

    public ArrayList<cTreeModel> getSelectedModel() {
        return this.selectedModel;
    }

    public interface OnGridViewItemSelectedListener{
        void getGridPosition(int position);
        void getSelectedOrganizationList(ArrayList<cOrganizationDomain> selectedOrganizations);
        void getSelectedTreeModel(ArrayList<cTreeModel> selectedGoalTree);
        void getGroupMenuPosition(int parentPosition);
        void getChildMenuPosition(int childPosition);
    }
}

