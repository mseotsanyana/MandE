package com.me.mseotsanyana.mande.PL.ui.fragments.session;

import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.me.mseotsanyana.mande.BLL.executor.Impl.cThreadExecutorImpl;
import com.me.mseotsanyana.mande.BLL.model.session.cMenuModel;
import com.me.mseotsanyana.mande.BLL.model.session.cUserProfileModel;
import com.me.mseotsanyana.mande.DAL.ìmpl.firestore.session.cHomePageFirestoreRepositoryImpl;
import com.me.mseotsanyana.mande.DAL.ìmpl.firestore.session.cSharedPreferenceFirestoreRepositoryImpl;
import com.me.mseotsanyana.mande.DAL.ìmpl.firestore.session.cUserProfileFirestoreRepositoryImpl;
import com.me.mseotsanyana.mande.PL.presenters.session.Impl.cHomePagePresenterImpl;
import com.me.mseotsanyana.mande.PL.presenters.session.Impl.cUserSignOutPresenterImpl;
import com.me.mseotsanyana.mande.PL.presenters.session.iHomePagePresenter;
import com.me.mseotsanyana.mande.PL.presenters.session.iUserSignOutPresenter;
import com.me.mseotsanyana.mande.PL.ui.adapters.session.cExpandableListAdapter;
import com.me.mseotsanyana.mande.R;
import com.me.mseotsanyana.mande.UTIL.cConstant;
import com.me.mseotsanyana.mande.cMainThreadImpl;
import com.me.mseotsanyana.mande.cSettingsActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

public class cHomeFragment extends Fragment implements iHomePagePresenter.View,
        iUserSignOutPresenter.View {
    private static final String TAG = cHomeFragment.class.getSimpleName();
    private static final SimpleDateFormat tsdf = cConstant.TIMESTAMP_FORMAT_DATE;
    //private static SimpleDateFormat ssdf = cConstant.SHORT_FORMAT_DATE;

    private Toolbar toolbar;
    private View progressbar;

    private ImageView userIcon;
    private TextView currentDate, displayName, displayEmail;


    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private View headerView;
    private ExpandableListView expandableListView;
    private cExpandableListAdapter expandableListAdapter;

    /* menu data structures */
    private final List<cMenuModel> menuModels = new ArrayList<>();

    private iUserSignOutPresenter userSignOutPresenter;
    private iHomePagePresenter homePagePresenter;

    private AppCompatActivity activity;

    // Required empty public constructor
    public cHomeFragment() {
    }

    public cHomeFragment newInstance() {
        return new cHomeFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //this.IsPermissionLoaded = cHomeFragmentArgs.fromBundle(requireArguments()).getPerm();
    }

    @Override
    public void onStart() {
        super.onStart();
        //FirebaseAuth.getInstance().signOut();
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        if (firebaseUser == null /*|| !firebaseUser.isEmailVerified()*/) {
            NavDirections action = cHomeFragmentDirections.actionCHomeFragmentToCLoginFragment();
            Navigation.findNavController(requireView()).navigate(action);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        /* read menu items from the asset */
        homePagePresenter.resume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.session_home_fragment, container, false);
    }

    /**
     * this event is triggered soon after on CreateView(). onViewCreated is called if the
     * view is returned from onCreateView() is non-null. Any view setup should occur here. e.g.
     * view lookups and attaching view listeners.
     */
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        /* create toolbar and progressbar */
        initViews(view);

        /* create navigation drawer menu */
        initNavigationDrawerViews(view);

        /* create bottom navigation menu */
        initBottomNavigationViews(view);

        /* populate header items */
        populateHeaderViews();

        // initialise navigationView listeners
        drawerNavigationListener();
    }

    private void initViews(View view) {
        activity = ((AppCompatActivity) getActivity());

        userSignOutPresenter = new cUserSignOutPresenterImpl(
                cThreadExecutorImpl.getInstance(),
                cMainThreadImpl.getInstance(),
                this,
                new cSharedPreferenceFirestoreRepositoryImpl(requireContext()),
                new cUserProfileFirestoreRepositoryImpl(requireContext()));

        homePagePresenter = new cHomePagePresenterImpl(
                cThreadExecutorImpl.getInstance(),
                cMainThreadImpl.getInstance(),
                this,
                new cSharedPreferenceFirestoreRepositoryImpl(requireContext()),
                new cHomePageFirestoreRepositoryImpl(getContext()));

        /* initialize the toolbar */
        toolbar = view.findViewById(R.id.toolbar);
        activity.setSupportActionBar(toolbar);

        /* initialize the progress bar*/
        progressbar = view.findViewById(R.id.progressbar);

        // instantiating the expandable action_list view under the DrawerLayout
        expandableListView = view.findViewById(R.id.expandableListView);

        // instantiating the navigation view
        NavigationView navigationView = view.findViewById(R.id.navigationView);
        headerView = navigationView.getHeaderView(0);

        expandableListAdapter = new cExpandableListAdapter(getContext(), menuModels);
        expandableListView.setAdapter(expandableListAdapter);
    }

    private void initNavigationDrawerViews(View view) {
        drawerLayout = view.findViewById(R.id.drawer_layout);
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

        // Sync the toggle state after onRestoreInstanceState has occur
        // and show the display menu icon
        drawerToggle.syncState();
    }

    private void drawerNavigationListener() {
        // called when expanding...
        expandableListView.setOnGroupExpandListener(groupPosition -> {
            //Objects.requireNonNull(activity.getSupportActionBar()).setTitle(
            //        (CharSequence) menuModels.get(groupPosition));
            Log.d(TAG, "I AM EXPANDING!!!!!!!!!!!!!!!");
        });

        // called when collapsing...
        expandableListView.setOnGroupCollapseListener(groupPosition -> {
            //Objects.requireNonNull(activity.getSupportActionBar()).setTitle(R.string.app_name);
            Log.d(TAG, "I AM CLOSING!!!!!!!!!!!!!!!!");
        });

        /*expandableListView.setOnGroupCollapseListener(groupPosition ->
                        Objects.requireNonNull(activity.getSupportActionBar()).
                        setTitle(R.string.app_name));*/

        // called when clicking on parent menu item...
        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            /**
             * Callback method to be invoked when a group in this expandable action_list has
             * been clicked.
             *
             * @param parent        The ExpandableListConnector where the click happened
             * @param v             The view within the expandable action_list/ListView
             *                      that was clicked
             * @param groupPosition The group position that was clicked
             * @param id            The row id of the group that was clicked
             * @return True if the click was handled
             */
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition,
                                        long id) {
                boolean retVal = true; // used to enable expanding to child menu items
                cMenuModel menuModel = expandableListAdapter.getGroup(groupPosition);
                switch (menuModel.getMenuServerID()) {
                    case 0: // My Profile
                        Toast.makeText(getActivity(), "My Profile Fragment",
                                Toast.LENGTH_SHORT).show();
                        retVal = false;
                        break;
                    case 8: // User Management
                        Toast.makeText(getActivity(), "User Management Fragment",
                                Toast.LENGTH_SHORT).show();
                        retVal = false;
                        break;
                    case 256: // Notification Settings
                        Toast.makeText(getActivity(), "Notification Fragment",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case 512: // Settings
                        Intent intent = new Intent(activity, cSettingsActivity.class);
                        startActivity(intent);
                        break;
                    case 1024: // Logout
                        userSignOutPresenter.signOutWithEmailAndPassword();
                        NavDirections action;
                        action = cHomeFragmentDirections.actionCHomeFragmentToCLoginFragment();
                        Navigation.findNavController(requireView()).navigate(action);
                        break;
                    default:
                        break;
                }
                return retVal;
            }
        });

        // called when clicking on child menu item...
        expandableListView.setOnChildClickListener((parent, v, groupPosition, childPosition, id) -> {
            cMenuModel parentModel = expandableListAdapter.getGroup(groupPosition);
            cMenuModel childModel = expandableListAdapter.getChild(groupPosition, childPosition);

            NavDirections action;

            switch (parentModel.getMenuServerID()) {
                case 0: // Admin
                    switch (childModel.getMenuServerID()) {
                        case 1: // Profile
                            action = cHomeFragmentDirections.actionCHomeFragmentToCMyUserProfileFragment();
                            Navigation.findNavController(requireView()).navigate(action);
                            //Toast.makeText(getActivity(),"Profile Fragment", Toast.LENGTH_SHORT).show();
                            break;

                        case 2: // Account Settings
                            action = cHomeFragmentDirections.actionCHomeFragmentToCUserProfilesFragment();
                            Navigation.findNavController(requireView()).navigate(action);
                            //Toast.makeText(getActivity(), "Account Fragment", Toast.LENGTH_SHORT).show();
                            break;

                        case 4: // Join Org.
                            Toast.makeText(getActivity(),
                                    "Join Org. Fragment", Toast.LENGTH_SHORT).show();
                            break;

                        default:
                            break;
                    }
                    break;

                case 8:
                    switch (childModel.getMenuServerID()) {
                        case 16:
                            action = cHomeFragmentDirections.
                                    actionCHomeFragmentToCOrganizationMemberFragment();
                            Navigation.findNavController(requireView()).navigate(action);
                            break;

                        case 32:
                            action = cHomeFragmentDirections.
                                    actionCHomeFragmentToCTeamRoleFragment();
                            Navigation.findNavController(requireView()).navigate(action);
                            break;

                        case 64:
                            action = cHomeFragmentDirections.
                                    actionCHomeFragmentToCPermissionFragment();
                            Navigation.findNavController(requireView()).navigate(action);
                            break;

//                        case 128:
//                            action = cHomeFragmentDirections.actionCHomeFragmentToCModuleFragment();
//                            Navigation.findNavController(requireView()).navigate(action);
//                            break;
                        default:
                            break;
                    }
                    break;

                default:
                    break;
            }

            return true;
        });
    }

    /* bottom navigation views */
    private void initBottomNavigationViews(View view) {
        NavigationBarView navigationBarView = view.findViewById(R.id.bottomNavigationView);
        navigationBarView.setOnItemSelectedListener(onItemSelectedListener);
        openFragment(cDashboardFragment.newInstance());
    }

    @SuppressLint("NonConstantResourceId")
    NavigationBarView.OnItemSelectedListener onItemSelectedListener =
            item -> {
                switch (item.getItemId()) {
                    case R.id.cDashboardFragment:
                        openFragment(cDashboardFragment.newInstance());
                        return true;

                    case R.id.cMessagesFragment:
                        openFragment(cMessagesFragment.newInstance());
                        return true;

                    case R.id.cSettingsFragment:
                        openFragment(cSettingsFragment.newInstance());
                        return true;
                }
                return false;
            };

//@SuppressLint("NonConstantResourceId")
//    BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener=
//            item->{
//            switch(item.getItemId()){
//            case R.id.cDashboardFragment:
//            openFragment(cDashboardFragment.newInstance());
//            return true;
//
//            case R.id.cMessagesFragment:
//            openFragment(cMessagesFragment.newInstance());
//            return true;
//
//            case R.id.cSettingsFragment:
//            openFragment(cSettingsFragment.newInstance());
//            return true;
//            }
//            return false;
//            };

    @SuppressLint("SetTextI18n")
    private void populateHeaderViews() {
        // instantiate header view objects
        userIcon = headerView.findViewById(R.id.userIcon);
        currentDate = headerView.findViewById(R.id.currentDate);
        displayName = headerView.findViewById(R.id.displayName);
        displayEmail = headerView.findViewById(R.id.displayEmail);
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
                //organizationAdapter.getFilter().filter(query);
                return false;
            }
        });
    }

    public void openFragment(Fragment fragment) {
        FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
        transaction.replace(R.id.sessionFrameLayout, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void onUserSignOutFailed(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUserSignOutSucceeded(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onReadUserProfileSucceeded(cUserProfileModel userProfileModel) {
        /* update the user profile */
        currentDate.setText(tsdf.format(Calendar.getInstance().getTime()));
        displayName.setText(userProfileModel.getName() + " " + userProfileModel.getSurname());
        displayEmail.setText(userProfileModel.getEmail());
    }

    @Override
    public void onReadMenuItemsSucceeded(List<cMenuModel> menuModels) {
        // update the menu when there is a change
        this.expandableListAdapter.setMenuModels(menuModels);
        this.expandableListAdapter.notifyDataSetChanged();
        hideProgress();
    }

    @Override
    public void onDefaultHomePageSucceeded(List<cMenuModel> menuModels) {
        /* update the user profile
        currentDate.setText(tsdf.format(Calendar.getInstance().getTime()));
        displayName.setText(userProfileModel.getName() + " " + userProfileModel.getSurname());
        displayEmail.setText(userProfileModel.getEmail());*/

        // update the menu when there is a change
        this.expandableListAdapter.setMenuModels(menuModels);
        this.expandableListAdapter.notifyDataSetChanged();
        hideProgress();
    }

    @Override
    public void onReadHomePageFailed(String msg) {
        hideProgress();
    }

    @Override
    public void showProgress() {
        progressbar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressbar.setVisibility(View.GONE);
    }

    @Override
    public void showError(String message) {

    }
}