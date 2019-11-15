package com.me.mseotsanyana.mande.BRBAC.PL;

import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.me.mseotsanyana.mande.BRBAC.BLL.cOrganizationDomain;
import com.me.mseotsanyana.mande.BRBAC.BLL.cOrganizationHandler;
import com.me.mseotsanyana.mande.BRBAC.BLL.cRoleDomain;
import com.me.mseotsanyana.mande.BRBAC.BLL.cSessionManager;
import com.me.mseotsanyana.mande.BRBAC.BLL.cStatusDomain;
import com.me.mseotsanyana.mande.BRBAC.BLL.cStatusHandler;
import com.me.mseotsanyana.mande.INTERFACE.iPermissionInterface;
import com.me.mseotsanyana.mande.PPMER.PL.cMainFragment;
import com.me.mseotsanyana.mande.R;
import com.me.mseotsanyana.mande.UTILITY.TextDrawable;
import com.me.mseotsanyana.mande.UTILITY.cFontManager;
import com.me.mseotsanyana.mande.UTILITY.cParam;
import com.me.mseotsanyana.multiselectspinnerlibrary.cKeyPairBoolData;
import com.me.mseotsanyana.multiselectspinnerlibrary.cSingleSpinnerSearch;
import com.me.mseotsanyana.multiselectspinnerlibrary.cSpinnerListener;
import com.me.mseotsanyana.treeadapterlibrary.cNode;
import com.me.mseotsanyana.treeadapterlibrary.cTreeModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mseotsanyana on 2017/02/24.
 */

public class cRoleFragment extends Fragment implements iPermissionInterface {
    private static String TAG = cRoleFragment.class.getSimpleName();

    private cSessionManager session;

    private ArrayList<cTreeModel> roleUserTree;
    //private ArrayList<cRoleDomain> roleDomains;
    private ArrayList<cStatusDomain> statusDomains;

    private Toolbar toolBar;
    private Menu toolBarMenu;
    private MenuItem toolBarMenuItem;

    private cOrganizationHandler organizationHandler;
    //private cUserRoleHandler userRoleHandler;
    private cStatusHandler statusHandler;

    private cRoleDomain roleDomain;
    private RecyclerView recyclerView;
    private cRoleAdapter roleUserTreeAdapter;
    private int cardPosition = 0;
    private int level = 0;

    private Gson gson = new Gson();

    public cRoleFragment() {

    }

    public static cRoleFragment newInstance() {
        cRoleFragment fragment = new cRoleFragment();
        return fragment;
    }

    /*
     * this event fires 1st, before creation of fragment or any views
     * the onAttach method is called when the Fragment instance is
     * associated with an Activity and this does not mean the activity
     * is fully initialized.
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    /**
     * this method is fired 2nd, before views are created for the fragment,
     * the onCreate method is called when the fragment instance is being created,
     * or re-created use onCreate for any standard setup that does not require
     * the activity to be fully created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        session = new cSessionManager(getContext());

        roleUserTree  = new ArrayList<>();
        //roleDomains   = new ArrayList<>();
        statusDomains = new ArrayList<>();

        // getting a action_list with all projects in a database
        organizationHandler = new cOrganizationHandler(getActivity(), session);
        //userRoleHandler = new cUserRoleHandler(getActivity(), session);
        statusHandler   = new cStatusHandler(getActivity());

        roleUserTreeAdapter = new cRoleAdapter(getActivity(), session,
                roleUserTree, statusDomains, level, this);
    }

    /**
     * the onCreateView method is called when fragment should create its View object
     * hierarchy either dynamically or via XML Layout inflation.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Register the event to subscribe.
        //-cGlobalBus.getBus().register(this);
        return inflater.inflate(R.layout.role_list_fragment, parent, false);
    }

    /**
     * this event is triggered soon after on CreateView(). onViewCreated is called if the
     * view is returned from onCreateView() is non-null. Any view setup should occur here. e.g.
     * view lookups and attaching view listeners.
     */
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // instantiate and initialize the action_list
        recyclerView = (RecyclerView) view.findViewById(R.id.role_card_list);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);

        // populate the action_list with data from database
        recyclerView.setAdapter(roleUserTreeAdapter);

        recyclerView.setLayoutManager(llm);

        // populate role user tree from database
        getRoleUserTree(session.loadUserID(),          /* loggedin user id */
                session.loadOrganizationID(),          /* loggedin's organization ID */
                session.loadPrimaryRole(session.loadUserID(),
                        session.loadOrganizationID()), /* primary role bit */
                session.loadSecondaryRoles(session.loadUserID(),
                        session.loadOrganizationID())  /* secondary role bits */
        );

        // initialise the floating action button (FAB)
        initFab(view);

        // initialize the toolbar
        toolBar = (Toolbar) view.findViewById(R.id.me_toolbar);
        toolBar.setTitle(R.string.role_list_title);
        toolBar.setTitleTextColor(Color.WHITE);

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolBar);
    }

    // initialise the floating action button
    private void initFab(View view) {
        view.findViewById(R.id.role_fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View formElementsView = inflater.inflate(R.layout.role_add_edit_record, null, false);

                // get all organizations from database
                final ArrayList<cOrganizationDomain> orgs =
                        organizationHandler.getOrganizationList(
                                session.loadUserID(),                  /* loggedIn user id  */
                                session.loadOrganizationID(),          /* loggedIn own org. */
                                session.loadPrimaryRole(session.loadUserID(),
                                        session.loadOrganizationID()), /* primary group bit */
                                session.loadSecondaryRoles(session.loadUserID(),
                                        session.loadOrganizationID())  /* secondary group bits */
                        );

                // create a pair list of organization ids and names
                final List<cKeyPairBoolData> keyPairBoolOrgs = new ArrayList<>();
                for (int i = 0; i < orgs.size(); i++) {
                    cKeyPairBoolData idNameBool = new cKeyPairBoolData();
                    idNameBool.setId(orgs.get(i).getOrganizationID());
                    idNameBool.setName(orgs.get(i).getName());
                    /*if ((mPermissionDomain != null) &&
                            (mPermissionDomain.getOrganizationID() == orgs.get(i).getOrganizationID())) {
                        idNameBool.setSelected(true);
                    } else {
                        idNameBool.setSelected(false);
                    }*/
                    keyPairBoolOrgs.add(idNameBool);
                }

                // -1 is no by default selection, 0 to length will select corresponding values
                cSingleSpinnerSearch singleSpinnerSearchOrg =
                        (cSingleSpinnerSearch) formElementsView.findViewById(R.id.appCompatSpinnerOrg);
                // called when click organization single spinner search
                singleSpinnerSearchOrg.setItems(keyPairBoolOrgs, -1, new cSpinnerListener() {
                    @Override
                    public void onItemsSelected(List<cKeyPairBoolData> items) {
                        for (int i = 0; i < items.size(); i++) {
                            /*if (items.get(i).isSelected()) {
                                mPermissionDomain.setOrganizationID((int) items.get(i).getId());
                                break;
                            }*/
                        }
                        //Log.d(TAG, "ORGANIZATION OWNER : " + mPermissionDomain.getOrganizationID());
                    }
                });



                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());

                alertDialogBuilder.setView(formElementsView);

                /** setting icon to dialog **/
                TextDrawable faIcon = new TextDrawable(getActivity());
                faIcon.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                faIcon.setTextAlign(Layout.Alignment.ALIGN_CENTER);
                faIcon.setTypeface(cFontManager.getTypeface(getActivity(), cFontManager.FONTAWESOME));
                faIcon.setText(getActivity().getResources().getText(R.string.fa_create));
                faIcon.setTextColor(Color.BLUE);

                /** put an icon **/
                alertDialogBuilder.setIcon(faIcon);

                /** set title **/
                alertDialogBuilder.setTitle("Add Role.");

                /** set dialog message **/
                alertDialogBuilder
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // create the privilege in the database
                                //userRoleHandler
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
        });
    }

    /**
     * This method is to fetch all user records from SQLite
     */
    private void getRoleUserTree(int userID,        /* loggedin user id */
                                 int orgID,
                                 int primaryRole,   /* primary group bit */
                                 int secondaryRoles /* secondary group bits */) {

        final cParam param = new cParam(userID, orgID, primaryRole, secondaryRoles);

        // AsyncTask is used that SQLite operation not blocks the UI Thread.
        new AsyncTask<cParam, Void, Void>() {
            @Override
            protected Void doInBackground(cParam... param) {
                roleUserTree.clear();
                /*roleUserTree.addAll(userRoleHandler.getRoleUserTree(
                        param[0].getUserID(), param[0].getOrgID(), param[0].getPrimaryRole(),
                        param[0].getSecondaryRoles()));*/

                /*
                roleDomains.clear();
                for (int i = 0; i < roleUserTree.size(); i++) {
                    if (roleUserTree.get(i).getType() == 0) {
                        roleDomains.add((cRoleDomain) roleUserTree.get(i).getModelObject());
                    }
                }
                */

                statusDomains.clear();
                statusDomains.addAll(statusHandler.getStatusList());

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                try {
                    roleUserTreeAdapter.sort(level);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }.execute(param);
    }

    @Override
    public void onUpdatePrivilege(cTreeModel treeModel) {

    }
/*
    @Override
    public void onUpdateEntity(int position) {
        roleDomain = roleUserTreeAdapter.getItem(position);
        //pushFragment(cUserEditFragment.newInstance(roleDomain, this));
    }
*/

    @Override
    public void onDeletePrivilege(cTreeModel treeModel, int position) {

    }

    @Override
    public void onSyncPrivilege(cTreeModel treeModel, int position) {

    }

    @Override
    public void onAddPermissionEntities(cNode node) {

    }

    @Override
    public void onRemovePermissionEntities(cNode node) {

    }


    @Override
    public void onResponseMessage(final int titleID, final int messageID) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(getActivity());

                // setting dialog title
                alertDialog.setTitle(titleID);

                // setting dialog message
                alertDialog.setMessage(messageID);

                // setting icon to dialog
                TextDrawable faIcon = new TextDrawable(getContext());
                faIcon.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 10);
                faIcon.setTextAlign(Layout.Alignment.ALIGN_CENTER);
                faIcon.setTypeface(cFontManager.getTypeface(getContext(), cFontManager.FONTAWESOME));
                faIcon.setText(getContext().getResources().getText(R.string.fa_exclamation_triangle));
                faIcon.setTextColor(Color.BLUE);

                alertDialog.setIcon(faIcon);

                // setting OK button
                alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // go to main menu
                        pushFragment(cRoleFragment.this);
                        dialog.cancel();
                    }
                });

                // modal mode
                alertDialog.setCancelable(false);

                // showing alert message
                alertDialog.show();
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        toolBar.inflateMenu(R.menu.menu_main);
        toolBarMenu = toolBar.getMenu();


        toolBarMenuItem = toolBarMenu.findItem(R.id.homeItem);

        toolBar.setOnMenuItemClickListener(
                new Toolbar.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        return onOptionsItemSelected(item);
                    }
                });

        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);

        SearchView searchView = (SearchView) toolBarMenu.findItem(R.id.searchItem).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        search(searchView);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.homeItem:
                pushFragment(cMainFragment.newInstance(null));
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    private void search(SearchView searchView) {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //userAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                roleUserTreeAdapter.getFilter().filter(query);
                return false;
            }
        });
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
