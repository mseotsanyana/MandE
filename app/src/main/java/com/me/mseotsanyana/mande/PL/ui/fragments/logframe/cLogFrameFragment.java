package com.me.mseotsanyana.mande.PL.ui.fragments.logframe;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatEditText;

import android.graphics.Typeface;
import android.os.Build;
import android.text.Layout;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;

import android.os.Bundle;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.util.Pair;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.picker.MaterialDatePicker;
import com.google.android.material.picker.MaterialPickerOnPositiveButtonClickListener;
import com.google.gson.Gson;
import com.me.mseotsanyana.mande.BLL.executor.Impl.cThreadExecutorImpl;
import com.me.mseotsanyana.mande.DAL.model.logframe.cLogFrameModel;
import com.me.mseotsanyana.mande.DAL.model.session.cUserModel;
import com.me.mseotsanyana.mande.DAL.ìmpl.logframe.cLogFrameRepositoryImpl;
import com.me.mseotsanyana.mande.DAL.ìmpl.session.cMenuRepositoryImpl;
import com.me.mseotsanyana.mande.DAL.ìmpl.session.cSessionManagerImpl;
import com.me.mseotsanyana.mande.PL.presenters.logframe.Impl.cLogFramePresenterImpl;
import com.me.mseotsanyana.mande.PL.presenters.logframe.iLogFramePresenter;
import com.me.mseotsanyana.mande.PL.ui.adapters.logframe.cLogFrameAdapter;
import com.me.mseotsanyana.mande.PL.ui.fragments.session.cPermissionFragment;
import com.me.mseotsanyana.mande.PL.ui.fragments.session.cRoleFragment;
import com.me.mseotsanyana.mande.PL.ui.fragments.session.cUserFragment;
import com.me.mseotsanyana.mande.UTIL.TextDrawable;
import com.me.mseotsanyana.mande.UTIL.cConstant;

import com.me.mseotsanyana.mande.UTIL.cExpandableListAdapter;
import com.me.mseotsanyana.mande.R;
import com.me.mseotsanyana.mande.UTIL.cFontManager;
import com.me.mseotsanyana.mande.cMainThreadImpl;
import com.me.mseotsanyana.mande.cSettingsActivity;
import com.me.mseotsanyana.treeadapterlibrary.cTreeModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by mseotsanyana on 2016/11/02.
 */
public class cLogFrameFragment extends Fragment implements iLogFramePresenter.View {
    private static String TAG = cLogFrameFragment.class.getSimpleName();
    private static SimpleDateFormat sdf = cConstant.SHORT_FORMAT_DATE;

    private Date startDate, endDate;

    // navigation drawer declarations
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;

    /* logframe adapters */
    private cExpandableListAdapter menuExpandableListAdapter;
    private cLogFrameAdapter logFrameRecyclerViewAdapter;

    /* logframe views */
    private ExpandableListView menuExpandableListView;
    private RecyclerView logFrameRecyclerView;

    /* logframe interface */
    private iLogFramePresenter logFramePresenter;

    /* menu data structures */
    private List<String> menuItemTitles;
    private HashMap<String, List<String>> expandableMenuItems;

    /* logframe data structures */
    private List<cTreeModel> logFrameTreeModels;

    //private Set<cMenuModel> menuModelSet;
    //private Set<cLogFrameModel> logFrameModelSet;

    AppCompatActivity activity;

    Gson gson = new Gson();

    public cLogFrameFragment() {
    }

    public static cLogFrameFragment newInstance(cUserModel userModel) {
        Bundle bundle = new Bundle();

        bundle.putParcelable("USERMODEL", userModel);

        cLogFrameFragment fragment = new cLogFrameFragment();
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        init();
    }

    @Override
    public void onResume() {
        super.onResume();
        /* get all logframes from the database */
        //readDesktopPresenter.resume();
    }

    private void init() {
        /* contains main menu and its corresponding submenu items */
        menuItemTitles = new ArrayList<String>();
        expandableMenuItems = new LinkedHashMap<String, List<String>>();
        /* contains a tree of logframes */
        logFrameTreeModels = new ArrayList<cTreeModel>();

        /* get arguments for successful login */
        cUserModel userModel = getArguments().getParcelable("USERMODEL");

        logFramePresenter = new cLogFramePresenterImpl(
                cThreadExecutorImpl.getInstance(),
                cMainThreadImpl.getInstance(),
                this,
                new cSessionManagerImpl(getContext()),
                new cMenuRepositoryImpl(getContext()),
                new cLogFrameRepositoryImpl(getContext()),
                userModel.getUserID());

        activity = ((AppCompatActivity) getActivity());
    }

    /**
     * the onCreateView method is called when fragment should create its View object
     * hierarchy either dynamically or via XML Layout inflation.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.logframe_list_fragment, container, false);

        return view;
    }

    /**
     * this event is triggered soon after on CreateView(). onViewCreated is called if the
     * view is returned from onCreateView() is non-null. Any view setup should occur here. e.g.
     * view lookups and attaching view listeners.
     */
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // create navigation drawer menu
        navigationDrawer(view);

        // create logframe menu
        logframeView(view);

        logFramePresenter.readAllLogframes();
    }

    private void navigationDrawer(View view) {
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

    private void logframeView(View view) {
        logFrameRecyclerView = (RecyclerView) view.findViewById(R.id.logframeRecyclerView);
        logFrameRecyclerView.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);

        logFrameRecyclerView.setLayoutManager(llm);
    }

    private void populateNavigationDrawer(View view) {
        // instantiating the expandable action_list view under the DrawerLayout
        menuExpandableListView = (ExpandableListView) view.findViewById(R.id.navList);

        // adding the header to the expandable action_list view
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View headerView = inflater.inflate(R.layout.dashboard_drawer_nav_header,
                null, false);

        // instantiate header view objects
        ImageView userIcon = (ImageView) headerView.findViewById(R.id.userIcon);
        TextView currentDate = (TextView) headerView.findViewById(R.id.currentDate);
        TextView website = (TextView) headerView.findViewById(R.id.website);

        // set header view objects
        //userIcon.setImageResource(...);
        currentDate.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime()));
        //website.setText(sessionManager.getCurrentUser().getName()+" "+sessionManager.getCurrentUser().getSurname());

        menuExpandableListView.addHeaderView(headerView);
    }

    private void setupDrawerToggle(View view) {
        //for create home button
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        //Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        activity.setSupportActionBar(toolbar);

        drawerLayout = (DrawerLayout) view.findViewById(R.id.drawer_layout);

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
        menuExpandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                activity.getSupportActionBar().setTitle(menuItemTitles.get(groupPosition).toString());
            }
        });

        // called when collapsing...
        menuExpandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
                activity.getSupportActionBar().setTitle(R.string.app_name);
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

                String selectedItem = ((List) (expandableMenuItems.get(menuItemTitles.get(groupPosition))))
                        .get(childPosition).toString();
                activity.getSupportActionBar().setTitle(selectedItem);

                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();

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
                                cPermissionFragment permissionFragment = cPermissionFragment.newInstance();
                                ft.replace(R.id.fragment_frame, permissionFragment);
                                ft.addToBackStack("PERMISSION");
                                ft.commit();
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

    /*=============================== REQUEST VIEW IMPLEMENTATION ================================*/

    @Override
    public void onClickCreateLogframe(cLogFrameModel logFrameModel) {

    }

    @Override
    public void onClickUpdateLogframe(cLogFrameModel logFrameModel, int position) {
        final MaterialAlertDialogBuilder alertDialogBuilder =
                new MaterialAlertDialogBuilder(getActivity(), R.style.AlertDialogTheme);
        // set title
        //alertDialogBuilder.setTitle("Edit Logframe.");
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.logframe_update, null);

        final TextView title = (TextView) dialogView.findViewById(R.id.textViewTitle);
        final AppCompatEditText name = (AppCompatEditText) dialogView.findViewById(R.id.editTextName);
        final AppCompatEditText description = (AppCompatEditText) dialogView.findViewById(R.id.editTextDescription);
        final AppCompatEditText startDateEditText = (AppCompatEditText) dialogView.findViewById(R.id.editTextStartDate);
        final AppCompatEditText endDateEditText = (AppCompatEditText) dialogView.findViewById(R.id.editTextEndDate);

        /* assign the fields to be updated */
        title.setText("UPDATE LOGFRAME");
        name.setText(logFrameModel.getName());
        description.setText(logFrameModel.getDescription());
        startDateEditText.setText(sdf.format(logFrameModel.getStartDate()));
        endDateEditText.setText(sdf.format(logFrameModel.getEndDate()));

        final TextView datePickerIcon = (TextView) dialogView.findViewById(R.id.textViewDatePicker);
        datePickerIcon.setTypeface(null, Typeface.NORMAL);
        datePickerIcon.setTypeface(
                cFontManager.getTypeface(getActivity(), cFontManager.FONTAWESOME));
        datePickerIcon.setTextColor(getActivity().getColor(R.color.colorPrimaryDark));
        datePickerIcon.setText(getActivity().getResources().getString(R.string.fa_calendar));
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
                        Log.d(TAG,"DatePicker Activity Dialog was cancelled");
                    }
                });

                picker.addOnPositiveButtonClickListener(
                        new MaterialPickerOnPositiveButtonClickListener<Pair<Long, Long>>() {
                    @Override
                    public void onPositiveButtonClick(Pair<Long, Long> selection) {
                        startDate = new Date(selection.first);
                        endDate = new Date(selection.second);
                        String startDateText = sdf.format(startDate);
                        String endDateText = sdf.format(endDate);

                        startDateEditText.setText(startDateText);
                        endDateEditText.setText(endDateText);


                        Log.d(TAG,"DatePicker Activity Date String ="+ picker.getHeaderText() +
                                " Date epoch values :: "+ selection.first +" :: to :: "+ selection.second);
                    }
                });

                picker.addOnNegativeButtonClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.d(TAG,"DatePicker Activity Dialog Negative Button was clicked");
                    }
                });

                picker.show(getFragmentManager(), picker.toString());


            }
        });

        alertDialogBuilder.setPositiveButton("SAVE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                logFramePresenter.updateLogframe(logFrameModel.getLogFrameID(),
                        name.toString(), description.toString(), startDate, endDate);
            }
        });
        alertDialogBuilder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        })
        .setView(dialogView)
        .show();

    }

    @Override
    public void onClickDeleteLogframe(long logframeID) {
        int resID = R.string.fa_delete;
        String title = "Delete Logframe.";
        String message = "Are you sure you want to delete this logframe ?";

        displayAlertDialog(resID, title, message);
    }

    @Override
    public void onClickSyncLogframe(cLogFrameModel logFrameModel) {

    }

    @Override
    public void onClickBoomMenu(int menuIndex) {
        switch (menuIndex) {
            case 0: // Impact Fragment
                //pushFragment(new cTriangleFragment());
                Log.d(TAG, "Clicked " + menuIndex);
                break;
            case 1: // Outcome Fragment
                //pushFragment(new cTriangleFragment());
                Log.d(TAG, "Clicked " + menuIndex);
                break;
            case 2: // Output Fragment
                //pushFragment(new cTriangleFragment());
                Log.d(TAG, "Clicked " + menuIndex);
                break;
            case 3: // Activity Fragment
                //pushFragment(new cTriangleFragment());
                Log.d(TAG, "Clicked " + menuIndex);
                break;
            case 4: // Resources Fragment
                //pushFragment(new cTriangleFragment());
                Log.d(TAG, "Clicked " + menuIndex);
                break;
            case 5: // Impact Fragment
                //pushFragment(new cTriangleFragment());
                Log.d(TAG, "Clicked " + menuIndex);
                break;
            case 6: // Impact Fragment
                //pushFragment(new cTriangleFragment());
                Log.d(TAG, "Clicked " + menuIndex);
                break;
            case 7: // Impact Fragment
                ///pushFragment(new cTriangleFragment());
                Log.d(TAG, "Clicked " + menuIndex);
                break;
            case 8: // Impact Fragment
                //pushFragment(new cTriangleFragment());
                Log.d(TAG, "Clicked " + menuIndex);
                break;
            case 9: // Impact Fragment
                pushFragment(new cTriangleFragment());
                break;
            case 10: // Impact Fragment
                //pushFragment(new cTriangleFragment());
                Log.d(TAG, "Clicked " + menuIndex);
                break;
            case 11: // Impact Fragment
                //pushFragment(new cTriangleFragment());
                Log.d(TAG, "Clicked " + menuIndex);
                break;
            default:
                break;
        }
    }

    /*=============================== RESPONSE VIEW IMPLEMENTATION ===============================*/

    @Override
    public void onRetrieveLogFramesCompleted(LinkedHashMap<String, List<String>> expandableMenuItems,
                                             ArrayList<cTreeModel> logFrameTreeModels) {
        /* populate navigation menu */
        menuItemTitles = new ArrayList<String>(expandableMenuItems.keySet());
        menuExpandableListAdapter = new cExpandableListAdapter(getActivity(), menuItemTitles,
                expandableMenuItems);
        menuExpandableListView.setAdapter(menuExpandableListAdapter);

        /* populate logframe list */
        // setup recycler view adapter
        logFrameRecyclerViewAdapter = new cLogFrameAdapter(getActivity(), this,
                logFrameTreeModels, 0);

        // populate the logframe list with data from database
        logFrameRecyclerView.setAdapter(logFrameRecyclerViewAdapter);


        //Log.d(TAG,"MENU ITEMS -->> "+gson.toJson(getExpandableMenuItems()));
        //Log.d(TAG,"LOGFRAMES  -->> "+gson.toJson(getMenuItemTitles()));

        //logFrameExpandableListAdapter.notifyDataSetChanged();
        //drawerLayout.invalidate();
    }

    @Override
    public void onLogframeCreated(cLogFrameModel logFrameModel) {

    }

    @Override
    public void onLogframeUpdated(cLogFrameModel logFrameModel) {

    }

    @Override
    public void onLogframeDeleted(long logframeID) {
        // we deleted some data, REMOVE FROM LIST and REFRESH !
        //notifyItemChanged(position);

        //readDesktopPresenter.getAllCosts();
    }

    @Override
    public void onLogframeSynced(cLogFrameModel logFrameModel) {

    }

    /*================================= BASE VIEW IMPLEMENTATION =================================*/

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showError(String message) {

    }

    /*===================================== UTILITY FUNCTIONS ====================================*/

    private void displayAlertDialog(int resID, String title, String message) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());

        // setting icon to dialog
        TextDrawable faIcon = new TextDrawable(getContext());
        faIcon.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
        faIcon.setTextAlign(Layout.Alignment.ALIGN_CENTER);
        faIcon.setTypeface(cFontManager.getTypeface(getContext(), cFontManager.FONTAWESOME));
        faIcon.setText(getContext().getResources().getText(resID));
        faIcon.setTextColor(getContext().getColor(R.color.colorAccent));
        alertDialogBuilder.setIcon(faIcon);

        // set title
        alertDialogBuilder.setTitle(title);
        // set dialog message
        alertDialogBuilder
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //readDesktopPresenter.deleteLogframe(logframeID);
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
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

    protected void pushFragment(Fragment fragment) {
        if (fragment == null)
            return;

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        if (ft != null) {
            ft.replace(R.id.fragment_frame, fragment);
            ft.commit();
        }
    }
}