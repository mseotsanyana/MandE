package com.me.mseotsanyana.mande.BRBAC.PL;

import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.google.gson.Gson;
import com.me.mseotsanyana.expandablelayoutlibrary.cExpandableLayout;
import com.me.mseotsanyana.mande.BRBAC.BLL.cEntityDomain;
import com.me.mseotsanyana.mande.BRBAC.BLL.cOperationDomain;
import com.me.mseotsanyana.mande.BRBAC.BLL.cPermissionDomain;
import com.me.mseotsanyana.mande.BRBAC.BLL.cPermissionHandler;
import com.me.mseotsanyana.mande.BRBAC.BLL.cPrivilegeDomain;
import com.me.mseotsanyana.mande.BRBAC.BLL.cSessionManager;
import com.me.mseotsanyana.mande.BRBAC.BLL.cStatusDomain;
import com.me.mseotsanyana.mande.Interface.iEntityTVHInterface;
import com.me.mseotsanyana.mande.Interface.iTreeAdapterCallback;
import com.me.mseotsanyana.mande.R;
import com.me.mseotsanyana.mande.Util.cFontManager;
import com.me.mseotsanyana.mande.Util.cPermParam;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import static com.me.mseotsanyana.mande.Util.cConstant.COLLAPSE;
import static com.me.mseotsanyana.mande.Util.cConstant.EXPAND;
import static com.me.mseotsanyana.mande.Util.cConstant.NUM_OPS;
import static com.me.mseotsanyana.mande.Util.cConstant.NUM_STS;
import static com.me.mseotsanyana.mande.Util.cConstant.OWNER;
import static com.me.mseotsanyana.mande.Util.cConstant.GROUP;
import static com.me.mseotsanyana.mande.Util.cConstant.OTHER;


public class cOperationsFragment extends Fragment implements iTreeAdapterCallback {
    private static final String TAG = cOperationsFragment.class.getSimpleName();
    private static final SimpleDateFormat formatter = new SimpleDateFormat(
            "dd MMMM, yyyy hh:mm:ss a", Locale.US);

    private cSessionManager session;

    private cExpandableLayout expandableLayoutOwner;
    private cExpandableLayout expandableLayoutGroup;
    private cExpandableLayout expandableLayoutOther;

    private cStatusTreeAdapter statusOwnerTreeAdapter,
            statusGroupTreeAdapter, statusOtherTreeAdapter;

    private cEntityDomain entityDomain;

    private cOperationDomain[] operationDomains;
    private cStatusDomain[][] statusDomains;

    private cOperationDomain ownerOperationDomain;
    private cOperationDomain groupOperationDomain;
    private cOperationDomain otherOperationDomain;
    private cOperationDomain[] ops = new cOperationDomain[NUM_OPS];
    private cStatusDomain[][] sts = new cStatusDomain[NUM_OPS][NUM_STS];

    private ArrayList<cStatusDomain> statusOwnerDomains;
    private ArrayList<cStatusDomain> statusGroupDomains;
    private ArrayList<cStatusDomain> statusOtherDomains;

    private ArrayList<cPermissionDomain> permissionDomains;

    private cPermissionHandler permissionHandler;

    private Set<cPermissionDomain> create_perms;
    private Set<cPermissionDomain> update_perms;
    private Set<cPermissionDomain> delete_perms;

    /**
     * store original data from database
     **/
    private int privilegeID;
    private int operationMask;
    private int statusMask;

    private TextView textViewStatusIconOwner;
    private TextView textViewStatusIconGroup;
    private TextView textViewStatusIconOther;

    private AppCompatCheckBox appCompatCheckBoxAllOperations;

    private AppCompatCheckBox appCompatCheckBoxAllOwnerStatuses;
    private AppCompatCheckBox appCompatCheckBoxAllGroupStatuses;
    private AppCompatCheckBox appCompatCheckBoxAllOtherStatuses;

    private AppCompatCheckBox appCompatCheckBoxOperationOwner;
    private AppCompatCheckBox appCompatCheckBoxOperationGroup;
    private AppCompatCheckBox appCompatCheckBoxOperationOther;

    private TextView textViewNameOwner;
    private TextView textViewNameGroup;
    private TextView textViewNameOther;

    private TextView textViewDescriptionOwner;
    private TextView textViewDescriptionGroup;
    private TextView textViewDescriptionOther;

    private RecyclerView recyclerViewStatusOwner;
    private RecyclerView recyclerViewStatusGroup;
    private RecyclerView recyclerViewStatusOther;

    private iEntityTVHInterface entityTreeVHInterface;

    Gson gson = new Gson();

    public cOperationsFragment newInstance(int privilegeID, cEntityDomain entityDomain,
                                           cOperationDomain[] operationDomains, int operationMask,
                                           cStatusDomain[][] statusDomains, int statusMask,
                                           ArrayList<cPermissionDomain> permissionDomains,
                                           iEntityTVHInterface entityTreeVHInterface) {
        Bundle bundle = new Bundle();

        bundle.putInt("PRIVILEDGEID", privilegeID);
        bundle.putParcelable("ENTITY", entityDomain);
        bundle.putInt("OPERATIONSMASK", operationMask);
        bundle.putString("OPERATIONS", gson.toJson(operationDomains));
        bundle.putInt("STATUSESMASK", statusMask);
        bundle.putString("STATUSES", gson.toJson(statusDomains));
        bundle.putParcelableArrayList("PERMISSIONS", permissionDomains);
        bundle.putSerializable("IPERMISSION", entityTreeVHInterface);

        cOperationsFragment fragment = new cOperationsFragment();
        fragment.setArguments(bundle);

        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.session = new cSessionManager(getContext());

        this.permissionHandler = new cPermissionHandler(getContext(), session);

        this.privilegeID = getArguments().getInt("PRIVILEDGEID");
        this.entityDomain = getArguments().getParcelable("ENTITY");
        String operations = getArguments().getString("OPERATIONS");
        this.operationDomains = gson.fromJson(operations, cOperationDomain[].class);
        this.operationMask = getArguments().getInt("OPERATIONSMASK");
        String statuses = getArguments().getString("STATUSES");
        this.statusDomains = gson.fromJson(statuses, cStatusDomain[][].class);
        this.statusMask = getArguments().getInt("STATUSESMASK");

        this.permissionDomains = getArguments().getParcelableArrayList("PERMISSIONS");
        this.entityTreeVHInterface = (iEntityTVHInterface) getArguments().getSerializable("IPERMISSION");

        //Log.d(TAG, "statusMask = "+statusMask);

        /** create a deep copy of operations **/
        for (int i = 0; i < operationDomains.length; i++) {
            /* create operation */
            if (i == OWNER) {
                ownerOperationDomain = new cOperationDomain(operationDomains[i]);
            }
            if (i == GROUP) {
                groupOperationDomain = new cOperationDomain(operationDomains[i]);
            }
            if (i == OTHER) {
                otherOperationDomain = new cOperationDomain(operationDomains[i]);
            }
        }

        /** create a deep copy of statuses **/
        statusOwnerDomains = new ArrayList<>();
        statusGroupDomains = new ArrayList<>();
        statusOtherDomains = new ArrayList<>();

        for (int i = 0; i < statusDomains[OWNER].length; i++) {
            statusOwnerDomains.add(i, new cStatusDomain(statusDomains[OWNER][i]));
            statusGroupDomains.add(i, new cStatusDomain(statusDomains[GROUP][i]));
            statusOtherDomains.add(i, new cStatusDomain(statusDomains[OTHER][i]));
        }
        //Log.d(TAG, gson.toJson(statusOwnerDomains));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // inflate the layout for this fragment
        return inflater.inflate(R.layout.entity_operations, container,
                false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        /** initialization of expandable view objects for operations **/
        expandableLayoutOwner = (cExpandableLayout) view.findViewById(R.id.expandableLayoutOwner);
        expandableLayoutGroup = (cExpandableLayout) view.findViewById(R.id.expandableLayoutGroup);
        expandableLayoutOther = (cExpandableLayout) view.findViewById(R.id.expandableLayoutOther);

        /** initialization of text view objects for operations **/
        textViewNameOwner = (TextView) view.findViewById(R.id.textViewNameOwner);
        textViewNameGroup = (TextView) view.findViewById(R.id.textViewNameGroup);
        textViewNameOther = (TextView) view.findViewById(R.id.textViewNameOther);
        textViewDescriptionOwner = (TextView) view.findViewById(R.id.textViewDescriptionOwner);
        textViewDescriptionGroup = (TextView) view.findViewById(R.id.textViewDescriptionGroup);
        textViewDescriptionOther = (TextView) view.findViewById(R.id.textViewDescriptionOther);

        /** initialization of check box view objects for all operations **/
        appCompatCheckBoxAllOperations = (AppCompatCheckBox)
                view.findViewById(R.id.appCompatCheckBoxAllOperations);

        /** initialization of check box view objects for operations **/
        appCompatCheckBoxOperationOwner = (AppCompatCheckBox) view.findViewById(R.id.checkBoxOperationOwner);
        appCompatCheckBoxOperationGroup = (AppCompatCheckBox) view.findViewById(R.id.checkBoxOperationGroup);
        appCompatCheckBoxOperationOther = (AppCompatCheckBox) view.findViewById(R.id.checkBoxOperationOther);

        /** initialization of check box view objects for owner statuses **/
        appCompatCheckBoxAllOwnerStatuses = (AppCompatCheckBox)
                view.findViewById(R.id.appCompatCheckBoxAllOwnerStatuses);
        appCompatCheckBoxAllGroupStatuses = (AppCompatCheckBox)
                view.findViewById(R.id.appCompatCheckBoxAllGroupStatuses);
        appCompatCheckBoxAllOtherStatuses = (AppCompatCheckBox)
                view.findViewById(R.id.appCompatCheckBoxAllOtherStatuses);

        /** initialise text views for expandable and collapse icons of operation statuses **/
        textViewStatusIconOwner = (TextView) view.findViewById(R.id.textViewStatusIconOwner);
        textViewStatusIconGroup = (TextView) view.findViewById(R.id.textViewStatusIconGroup);
        textViewStatusIconOther = (TextView) view.findViewById(R.id.textViewStatusIconOther);

        /** initialization of recycler view objects for statuses **/
        recyclerViewStatusOwner = (RecyclerView) view.findViewById(R.id.recyclerViewStatusOwner);
        recyclerViewStatusGroup = (RecyclerView) view.findViewById(R.id.recyclerViewStatusGroup);
        recyclerViewStatusOther = (RecyclerView) view.findViewById(R.id.recyclerViewStatusOther);

        /** assign names and descriptions of operations **/
        textViewNameOwner.setText(ownerOperationDomain.getName());
        textViewNameGroup.setText(groupOperationDomain.getName());
        textViewNameOther.setText(otherOperationDomain.getName());

        textViewDescriptionOwner.setText(ownerOperationDomain.getDescription());
        textViewDescriptionGroup.setText(groupOperationDomain.getDescription());
        textViewDescriptionOther.setText(otherOperationDomain.getDescription());

        /** assign icons to expand and collapse statuses for operations **/
        textViewStatusIconOwner.setTypeface(null, Typeface.NORMAL);
        textViewStatusIconOwner.setTypeface(cFontManager.getTypeface(getContext(),
                cFontManager.FONTAWESOME));
        textViewStatusIconOwner.setText(getContext().getResources().getString(R.string.fa_plus));

        textViewStatusIconGroup.setTypeface(null, Typeface.NORMAL);
        textViewStatusIconGroup.setTypeface(cFontManager.getTypeface(getContext(),
                cFontManager.FONTAWESOME));
        textViewStatusIconGroup.setText(getContext().getResources().getString(R.string.fa_plus));

        textViewStatusIconOther.setTypeface(null, Typeface.NORMAL);
        textViewStatusIconOther.setTypeface(cFontManager.getTypeface(getContext(),
                cFontManager.FONTAWESOME));
        textViewStatusIconOther.setText(getContext().getResources().getString(R.string.fa_plus));


        /*********************************************************/
        /** initialise event listeners on views                 **/
        /*********************************************************/

        // set the operations' check button without clicking
        if (appCompatCheckBoxAllOperations != null) {
            if (isAllOperationsChecked()) {
                appCompatCheckBoxAllOperations.setChecked(true);
            } else {
                appCompatCheckBoxAllOperations.setChecked(false);
            }

            /** refresh permissions after the update **/
            //onRefreshPermissions();
        }

        //appCompatCheckBoxAllOperations.setChecked(isAllOperationsChecked());

        /** update the owner, group and other operation checkboxes **/
        appCompatCheckBoxOperationOwner.setChecked(ownerOperationDomain.isState());
        appCompatCheckBoxOperationGroup.setChecked(groupOperationDomain.isState());
        appCompatCheckBoxOperationOther.setChecked(otherOperationDomain.isState());

        /** check whether all statuses are checked **/
        appCompatCheckBoxAllOwnerStatuses.setChecked(isAllStatusOwnerChecked());
        appCompatCheckBoxAllGroupStatuses.setChecked(isAllStatusGroupChecked());
        appCompatCheckBoxAllOtherStatuses.setChecked(isAllStatusOtherChecked());

        /*********************************************************/
        /** event listeners on check boxes to update operations **/
        /*********************************************************/

        /** event listener for all operations **/
        appCompatCheckBoxAllOperations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (appCompatCheckBoxAllOperations.isChecked()) {
                    /** check whether owner operation is either modified or not **/
                    if (!operationDomains[OWNER].isState())
                        ownerOperationDomain.setDirty(true);
                    else
                        ownerOperationDomain.setDirty(false);
                    ownerOperationDomain.setState(true);

                    /** check whether group operation is either modified or not **/
                    if (!operationDomains[GROUP].isState())
                        groupOperationDomain.setDirty(true);
                    else
                        groupOperationDomain.setDirty(false);
                    groupOperationDomain.setState(true);

                    /** check whether other operation is either modified or not **/
                    if (!operationDomains[OTHER].isState())
                        otherOperationDomain.setDirty(true);
                    else
                        otherOperationDomain.setDirty(false);
                    otherOperationDomain.setState(true);

                    /** update all the status checkboxes **/
                    for (int i = 0; i < statusDomains[0].length; i++) {
                        /** owner statuses **/
                        statusOwnerDomains.get(i).setState(statusDomains[OWNER][i].isState());
                        statusOwnerDomains.get(i).setDirty(statusDomains[OWNER][i].isDirty());

                        /** group statuses **/
                        statusGroupDomains.get(i).setState(statusDomains[GROUP][i].isState());
                        statusGroupDomains.get(i).setDirty(statusDomains[GROUP][i].isDirty());

                        /** other statuses **/
                        statusOtherDomains.get(i).setState(statusDomains[OTHER][i].isState());
                        statusOtherDomains.get(i).setDirty(statusDomains[OTHER][i].isDirty());
                    }

                } else {
                    /** check whether owner operation is either modified or not **/
                    if (operationDomains[OWNER].isState())
                        ownerOperationDomain.setDirty(true);
                    else
                        ownerOperationDomain.setDirty(false);
                    ownerOperationDomain.setState(false);

                    /** check whether group operation is either modified or not **/
                    if (operationDomains[GROUP].isState())
                        groupOperationDomain.setDirty(true);
                    else
                        groupOperationDomain.setDirty(false);
                    groupOperationDomain.setState(false);

                    /** check whether other operation is either modified or not **/
                    if (operationDomains[OTHER].isState())
                        otherOperationDomain.setDirty(true);
                    else
                        otherOperationDomain.setDirty(false);
                    otherOperationDomain.setState(false);

                    /** unchecking all operations implies removing all statuses **/
                    for (int i = 0; i < statusDomains[0].length; i++) {
                        /** owner statuses **/
                        if (statusDomains[OWNER][i].isState())
                            statusOwnerDomains.get(i).setDirty(true);
                        else
                            statusOwnerDomains.get(i).setDirty(false);
                        statusOwnerDomains.get(i).setState(false);

                        /** group statuses **/
                        if (statusDomains[GROUP][i].isState())
                            statusGroupDomains.get(i).setDirty(true);
                        else
                            statusGroupDomains.get(i).setDirty(false);
                        statusGroupDomains.get(i).setState(false);

                        /** other statuses **/
                        if (statusDomains[OTHER][i].isState())
                            statusOtherDomains.get(i).setDirty(true);
                        else
                            statusOtherDomains.get(i).setDirty(false);
                        statusOtherDomains.get(i).setState(false);
                    }
                }

                /** update the owner, group and other operation checkboxes **/
                appCompatCheckBoxOperationOwner.setChecked(ownerOperationDomain.isState());
                appCompatCheckBoxOperationGroup.setChecked(groupOperationDomain.isState());
                appCompatCheckBoxOperationOther.setChecked(otherOperationDomain.isState());

                statusOwnerTreeAdapter.notifyDataSetChanged();
                statusGroupTreeAdapter.notifyDataSetChanged();
                statusOtherTreeAdapter.notifyDataSetChanged();

                /** refresh permissions after the update **/
                //onRefreshPermissions();
            }
        });

        /** event listener for owner operation **/
        appCompatCheckBoxOperationOwner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (((CheckBox) view).isChecked()) {
                    if (!operationDomains[OWNER].isState())
                        ownerOperationDomain.setDirty(true);
                    else
                        ownerOperationDomain.setDirty(false);
                    ownerOperationDomain.setState(true);

                    /** set the owner statuses to the database default values **/
                    for (int i = 0; i < statusDomains[OWNER].length; i++) {
                        statusOwnerDomains.get(i).setState(statusDomains[OWNER][i].isState());
                        statusOwnerDomains.get(i).setDirty(statusDomains[OWNER][i].isDirty());
                    }

                } else {
                    if (operationDomains[OWNER].isState())
                        ownerOperationDomain.setDirty(true);
                    else
                        ownerOperationDomain.setDirty(false);
                    ownerOperationDomain.setState(false);

                    /** disable the owner statuses due to owner operation being disabled **/
                    appCompatCheckBoxAllOwnerStatuses.setChecked(false);

                    for (int i = 0; i < statusDomains[OWNER].length; i++) {
                        if (statusDomains[OWNER][i].isState())
                            statusOwnerDomains.get(i).setDirty(true);
                        else
                            statusOwnerDomains.get(i).setDirty(false);
                        statusOwnerDomains.get(i).setState(false);
                    }
                }

                /** refresh a owner status adapter **/
                statusOwnerTreeAdapter.notifyDataSetChanged();

                /** check the all operation checkbox if all operations are checked **/
                if (appCompatCheckBoxAllOperations != null) {
                    if (isAllOperationsChecked()) {
                        appCompatCheckBoxAllOperations.setChecked(true);
                    } else {
                        appCompatCheckBoxAllOperations.setChecked(false);
                    }
                }

                /** refresh permissions after the update **/
                //onRefreshPermissions();
            }
        });

        /** event listener for group operation **/
        appCompatCheckBoxOperationGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (((CheckBox) view).isChecked()) {
                    if (!operationDomains[GROUP].isState()) {
                        groupOperationDomain.setDirty(true);
                    }
                    groupOperationDomain.setState(true);

                    /** set the owner statuses to the database default values **/
                    for (int i = 0; i < statusDomains[GROUP].length; i++) {
                        statusGroupDomains.get(i).setState(statusDomains[GROUP][i].isState());
                        statusGroupDomains.get(i).setDirty(statusDomains[GROUP][i].isDirty());
                    }
                } else {
                    if (operationDomains[GROUP].isState()) {
                        groupOperationDomain.setDirty(true);
                    }
                    groupOperationDomain.setState(false);

                    /** disable the owner statuses due to owner operation being disabled **/
                    appCompatCheckBoxAllGroupStatuses.setChecked(false);

                    for (int i = 0; i < statusDomains[GROUP].length; i++) {
                        if (statusDomains[GROUP][i].isState())
                            statusGroupDomains.get(i).setDirty(true);
                        else
                            statusGroupDomains.get(i).setDirty(false);
                        statusGroupDomains.get(i).setState(false);
                    }
                }

                /** refresh a group status adapter **/
                statusGroupTreeAdapter.notifyDataSetChanged();

                /** check the all operation checkbox if all operations are checked **/
                if (appCompatCheckBoxAllOperations != null) {
                    if (isAllOperationsChecked()) {
                        appCompatCheckBoxAllOperations.setChecked(true);
                    } else {
                        appCompatCheckBoxAllOperations.setChecked(false);
                    }
                }

                /** refresh permissions after the update **/
                //onRefreshPermissions();
            }
        });

        /** event listener for other operation **/
        appCompatCheckBoxOperationOther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (((CheckBox) view).isChecked()) {
                    if (!operationDomains[OTHER].isState()) {
                        otherOperationDomain.setDirty(true);
                    }
                    otherOperationDomain.setState(true);

                    /** set the owner statuses to the database default values **/
                    for (int i = 0; i < statusDomains[OTHER].length; i++) {
                        statusOtherDomains.get(i).setState(statusDomains[OTHER][i].isState());
                        statusOtherDomains.get(i).setDirty(statusDomains[OTHER][i].isDirty());
                    }
                } else {
                    if (operationDomains[OTHER].isState()) {
                        otherOperationDomain.setDirty(true);
                    }
                    otherOperationDomain.setState(false);

                    /** disable the owner statuses due to owner operation being disabled **/
                    appCompatCheckBoxAllOtherStatuses.setChecked(false);

                    for (int i = 0; i < statusDomains[OTHER].length; i++) {
                        if (statusDomains[OTHER][i].isState())
                            statusOtherDomains.get(i).setDirty(true);
                        else
                            statusOtherDomains.get(i).setDirty(false);
                        statusOtherDomains.get(i).setState(false);
                    }
                }

                /** refresh a other status adapter **/
                statusOtherTreeAdapter.notifyDataSetChanged();

                /** check the all operation checkbox if all operations are checked **/
                if (appCompatCheckBoxAllOperations != null) {
                    if (isAllOperationsChecked()) {
                        appCompatCheckBoxAllOperations.setChecked(true);
                    } else {
                        appCompatCheckBoxAllOperations.setChecked(false);
                    }
                }

                /** refresh permissions after the update **/
                //onRefreshPermissions();
            }
        });

        /** event listener all owner statuses **/
        appCompatCheckBoxAllOwnerStatuses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (appCompatCheckBoxAllOwnerStatuses.isChecked()) {
                    for (int i = 0; i < statusDomains[OWNER].length; i++) {
                        if (!statusDomains[OWNER][i].isState())
                            statusOwnerDomains.get(i).setDirty(true);
                        else
                            statusOwnerDomains.get(i).setDirty(false);
                        statusOwnerDomains.get(i).setState(true);
                    }

                    if (!operationDomains[OWNER].isState())
                        ownerOperationDomain.setDirty(true);
                    else
                        ownerOperationDomain.setDirty(false);
                    ownerOperationDomain.setState(true);
                } else {
                    for (int i = 0; i < statusDomains[OWNER].length; i++) {
                        if (statusDomains[OWNER][i].isState())
                            statusOwnerDomains.get(i).setDirty(true);
                        else
                            statusOwnerDomains.get(i).setDirty(false);
                        statusOwnerDomains.get(i).setState(statusDomains[OWNER][i].isState());
                    }

                    if (operationDomains[OWNER].isState())
                        ownerOperationDomain.setDirty(true);
                    else
                        ownerOperationDomain.setDirty(false);
                    ownerOperationDomain.setState(operationDomains[OWNER].isState());
                }

                /** check owner operation **/
                appCompatCheckBoxOperationOwner.setChecked(ownerOperationDomain.isState());

                /** check the all operation checkbox if all operations are checked **/
                if (appCompatCheckBoxAllOperations != null) {
                    if (isAllOperationsChecked()) {
                        appCompatCheckBoxAllOperations.setChecked(true);
                    } else {
                        appCompatCheckBoxAllOperations.setChecked(false);
                    }
                }

                /** check whether all statuses are checked **/
                appCompatCheckBoxAllOwnerStatuses.setChecked(isAllStatusOwnerChecked());

                /** refresh a other status adapter **/
                statusOwnerTreeAdapter.notifyDataSetChanged();

                /** refresh permissions after the update **/
                //onRefreshPermissions();
            }
        });

        /** event listener for group statuses **/
        appCompatCheckBoxAllGroupStatuses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (appCompatCheckBoxAllGroupStatuses.isChecked()) {
                    for (int i = 0; i < statusDomains[GROUP].length; i++) {
                        if (!statusDomains[GROUP][i].isState())
                            statusGroupDomains.get(i).setDirty(true);
                        else
                            statusGroupDomains.get(i).setDirty(false);
                        statusGroupDomains.get(i).setState(true);
                    }

                    if (!operationDomains[GROUP].isState())
                        groupOperationDomain.setDirty(true);
                    else
                        groupOperationDomain.setDirty(false);
                    groupOperationDomain.setState(true);
                } else {
                    for (int i = 0; i < statusDomains[GROUP].length; i++) {
                        if (statusDomains[GROUP][i].isState())
                            statusGroupDomains.get(i).setDirty(true);
                        else
                            statusGroupDomains.get(i).setDirty(false);
                        statusGroupDomains.get(i).setState(statusDomains[GROUP][i].isState());
                    }

                    if (operationDomains[GROUP].isState())
                        groupOperationDomain.setDirty(true);
                    else
                        groupOperationDomain.setDirty(false);
                    groupOperationDomain.setState(operationDomains[GROUP].isState());
                }

                /** check group operation **/
                appCompatCheckBoxOperationGroup.setChecked(groupOperationDomain.isState());

                /** check the all operation checkbox if all operations are checked **/
                if (appCompatCheckBoxAllOperations != null) {
                    if (isAllOperationsChecked()) {
                        appCompatCheckBoxAllOperations.setChecked(true);
                    } else {
                        appCompatCheckBoxAllOperations.setChecked(false);
                    }
                }

                /** check whether all statuses are checked **/
                appCompatCheckBoxAllGroupStatuses.setChecked(isAllStatusGroupChecked());

                /** refresh a other status adapter **/
                statusGroupTreeAdapter.notifyDataSetChanged();

                /** refresh permissions after the update **/
                //onRefreshPermissions();

            }
        });

        /** event listener for other statuses **/
        appCompatCheckBoxAllOtherStatuses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (appCompatCheckBoxAllOtherStatuses.isChecked()) {
                    for (int i = 0; i < statusDomains[OTHER].length; i++) {
                        if (!statusDomains[OTHER][i].isState())
                            statusOtherDomains.get(i).setDirty(true);
                        else
                            statusOtherDomains.get(i).setDirty(false);
                        statusOtherDomains.get(i).setState(true);
                    }

                    if (!operationDomains[OTHER].isState())
                        otherOperationDomain.setDirty(true);
                    else
                        otherOperationDomain.setDirty(false);
                    otherOperationDomain.setState(true);
                } else {
                    for (int i = 0; i < statusDomains[OTHER].length; i++) {
                        if (statusDomains[OTHER][i].isState())
                            statusOtherDomains.get(i).setDirty(true);
                        else
                            statusOtherDomains.get(i).setDirty(false);
                        statusOtherDomains.get(i).setState(statusDomains[OTHER][i].isState());
                    }

                    if (operationDomains[OTHER].isState())
                        otherOperationDomain.setDirty(true);
                    else
                        otherOperationDomain.setDirty(false);
                    otherOperationDomain.setState(operationDomains[OTHER].isState());
                }

                /** check group operation **/
                appCompatCheckBoxOperationOther.setChecked(otherOperationDomain.isState());

                /** check the all operation checkbox if all operations are checked **/
                if (appCompatCheckBoxAllOperations != null) {
                    if (isAllOperationsChecked()) {
                        appCompatCheckBoxAllOperations.setChecked(true);
                    } else {
                        appCompatCheckBoxAllOperations.setChecked(false);
                    }
                }

                /** check whether all statuses are checked **/
                appCompatCheckBoxAllOtherStatuses.setChecked(isAllStatusOtherChecked());

                /** refresh a other status adapter **/
                statusOtherTreeAdapter.notifyDataSetChanged();

                /** refresh permissions after the update **/
                //onRefreshPermissions();
            }
        });

        /** event listener for owner common attributes **/
        textViewStatusIconOwner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (expandableLayoutOwner.isExpanded()) {
                    textViewStatusIconOwner.setText(getContext().getResources().getString(R.string.fa_plus));
                    entityTreeVHInterface.onOperationUpdate(COLLAPSE);
                } else {
                    textViewStatusIconOwner.setText(getContext().getResources().getString(R.string.fa_minus));
                    entityTreeVHInterface.onOperationUpdate(EXPAND);
                }
                expandableLayoutOwner.toggle();
            }
        });

        /** event listener for group common attributes **/
        textViewStatusIconGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (expandableLayoutGroup.isExpanded()) {
                    textViewStatusIconGroup.setText(getContext().getResources().getString(R.string.fa_plus));
                    entityTreeVHInterface.onOperationUpdate(COLLAPSE);
                } else {
                    textViewStatusIconGroup.setText(getContext().getResources().getString(R.string.fa_minus));
                    entityTreeVHInterface.onOperationUpdate(EXPAND);
                }
                expandableLayoutGroup.toggle();
            }
        });

        /** event listener for other common attributes **/
        textViewStatusIconOther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (expandableLayoutOther.isExpanded()) {
                    textViewStatusIconOther.setText(getContext().getResources().getString(R.string.fa_plus));
                    entityTreeVHInterface.onOperationUpdate(COLLAPSE);
                } else {
                    textViewStatusIconOther.setText(getContext().getResources().getString(R.string.fa_minus));
                    entityTreeVHInterface.onOperationUpdate(EXPAND);
                }
                expandableLayoutOther.toggle();
            }
        });

        /*********************************************************/
        /** create adapters for owner, group and other statuses **/
        /*********************************************************/

        statusOwnerTreeAdapter = new cStatusTreeAdapter(getContext(), session,
                privilegeID, entityDomain, ownerOperationDomain, statusOwnerDomains,
                permissionDomains, operationDomains[OWNER], statusDomains[OWNER],
                appCompatCheckBoxOperationOwner, appCompatCheckBoxAllOwnerStatuses,
                cOperationsFragment.this);

        statusGroupTreeAdapter = new cStatusTreeAdapter(getContext(), session,
                privilegeID, entityDomain, groupOperationDomain, statusGroupDomains,
                permissionDomains, operationDomains[GROUP], statusDomains[GROUP],
                appCompatCheckBoxOperationGroup, appCompatCheckBoxAllGroupStatuses,
                cOperationsFragment.this);

        statusOtherTreeAdapter = new cStatusTreeAdapter(getContext(), session,
                privilegeID, entityDomain, otherOperationDomain, statusOtherDomains,
                permissionDomains, operationDomains[OTHER], statusDomains[OTHER],
                appCompatCheckBoxOperationOther, appCompatCheckBoxAllOtherStatuses,
                cOperationsFragment.this);

        /***************************************************************/
        /** attach the status list to owner, group and other adapters **/
        /***************************************************************/

        recyclerViewStatusOwner.setHasFixedSize(true);
        LinearLayoutManager llmOwner = new LinearLayoutManager(getContext());
        llmOwner.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewStatusOwner.setLayoutManager(llmOwner);
        recyclerViewStatusOwner.setAdapter(statusOwnerTreeAdapter);

        recyclerViewStatusGroup.setHasFixedSize(true);
        LinearLayoutManager llmGroup = new LinearLayoutManager(getContext());
        llmGroup.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewStatusGroup.setLayoutManager(llmGroup);
        recyclerViewStatusGroup.setAdapter(statusGroupTreeAdapter);

        recyclerViewStatusOther.setHasFixedSize(true);
        LinearLayoutManager llmOther = new LinearLayoutManager(getContext());
        llmOther.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewStatusOther.setLayoutManager(llmOther);
        recyclerViewStatusOther.setAdapter(statusOtherTreeAdapter);
    }

    /**
     * find if all values are checked
     **/
    public boolean isAllOperationsChecked() {
        if (ownerOperationDomain.isState() &&
                groupOperationDomain.isState() &&
                otherOperationDomain.isState()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * find if all own statuses are checked
     **/
    public boolean isAllStatusOwnerChecked() {
        for (int i = 0; i < statusOwnerDomains.size(); i++) {
            if (!statusOwnerDomains.get(i).isState()) {
                return false;
            }
        }
        return true;
    }

    /**
     * find if all group statuses are checked
     **/
    public boolean isAllStatusGroupChecked() {
        for (int i = 0; i < statusGroupDomains.size(); i++) {
            if (!statusGroupDomains.get(i).isState()) {
                return false;
            }
        }
        return true;
    }

    /**
     * find if all other statuses are checked
     **/
    public boolean isAllStatusOtherChecked() {
        for (int i = 0; i < statusOtherDomains.size(); i++) {
            if (!statusOtherDomains.get(i).isState()) {
                return false;
            }
        }
        return true;
    }

    /**
     * return a permission domain
     **/
    public cPermissionDomain getPermissionByIDs(int privilegeID, int entityID, int typeID,
                                                int operationID, int statusID) {
        cPermissionDomain permissionDomain = null;
        for (int i = 0; i < permissionDomains.size(); i++) {
            if ((permissionDomains.get(i).getPrivilegeDomain().getPrivilegeID() == privilegeID) &&
                    (permissionDomains.get(i).getEntityDomain().getEntityID() == entityID) &&
                    (permissionDomains.get(i).getEntityDomain().getTypeID() == typeID) &&
                    (permissionDomains.get(i).getOperationDomain().getOperationID() == operationID) &&
                    (permissionDomains.get(i).getStatusDomain().getStatusID() == statusID)) {

                permissionDomain = permissionDomains.get(i);

                return permissionDomain;
            }
        }
        return permissionDomain;
    }

    /**
     * is there a change in common permission attributes
     **/
    public boolean isPermissionDirty(cPermissionDomain originalDomain, cPermissionDomain modifiedDomain) {
        if (((originalDomain.getOwnerID() == modifiedDomain.getOwnerID()) &&
                (originalDomain.getOrgID() == modifiedDomain.getOrgID()) &&
                (originalDomain.getGroupBITS() == modifiedDomain.getGroupBITS()) &&
                (originalDomain.getPermsBITS() == modifiedDomain.getPermsBITS()) &&
                (originalDomain.getStatusBITS() == modifiedDomain.getStatusBITS()))) {

            return false;
        } else {
            return true;
        }
    }


    @Override
    public void onRefreshTreeAdapter() {

    }

    @Override
    public void onRefreshPermissions() {
        if (statusDomains != null) {
            /** consolidate the modified operations of the permission **/
            create_perms = new HashSet<>();
            delete_perms = new HashSet<>();

            ops[OWNER] = ownerOperationDomain;
            ops[GROUP] = groupOperationDomain;
            ops[OTHER] = otherOperationDomain;

            for (int i = 0; i < statusDomains[OWNER].length; i++) {
                sts[OWNER][i] = statusOwnerDomains.get(i);
                sts[GROUP][i] = statusGroupDomains.get(i);
                sts[OTHER][i] = statusOtherDomains.get(i);
            }

            /** deal with change of part of composite key operationID and statusID **/
            for (int i = 0; i < ops.length; i++) {
                // populate privilege details
                cPrivilegeDomain privilegeDomain = new cPrivilegeDomain();
                privilegeDomain.setPrivilegeID(privilegeID);

                // populate entity details
                cEntityDomain tmpEntityDomain = new cEntityDomain();
                tmpEntityDomain.setEntityID(entityDomain.getEntityID());
                tmpEntityDomain.setTypeID(entityDomain.getTypeID());

                // populate operation details
                cOperationDomain operationDomain = new cOperationDomain();
                operationDomain.setOperationID(ops[i].getOperationID());

                for (int j = 0; j < sts[i].length; j++) {
                    /** if the state of operation or/and status are true and they are not in the
                     permission's table ==> CREATE (or ADD) **/
                    if ((((operationMask & ops[i].getOperationID()) != ops[i].getOperationID()) &&
                            ops[i].isState() && ops[i].isDirty()) ||
                            (sts[i][j].isState() && sts[i][j].isDirty())) {

                        /** keep current permission domain **/
                        //private cPermissionDomain permissionDomain;

                        cPermissionDomain permDomain = new cPermissionDomain();

                        /** set the permission domain details **/
                        permDomain.setPrivilegeDomain(privilegeDomain);
                        permDomain.setEntityDomain(tmpEntityDomain);
                        permDomain.setOperationDomain(operationDomain);
                        permDomain.setStatusDomain(new cStatusDomain(sts[i][j]));
                        permDomain.setOwnerID(session.loadUserID());
                        permDomain.setOrgID(session.loadOrganizationID());

                    /*Log.d(TAG, "CREATE => privilegeID = " +
                            permDomain.getPrivilegeDomain().getPrivilegeID() + ", entityID = " +
                            permDomain.getEntityDomain().getEntityID() + ", typeID = " +
                            permDomain.getEntityDomain().getTypeID() + ", operationID = " +
                            permDomain.getOperationDomain().getOperationID() + ", statusID = " +
                            permDomain.getStatusDomain().getStatusID() + ", ownerID = " +
                            permDomain.getOwnerID() + ", organizationID = " +
                            permDomain.getOrganizationID());*/

                        /** add record in a create permission list to create **/
                        create_perms.add(permDomain);
                    }

                    // if the state is false and the operation is in the
                    // permission's table ==> DELETE => (((operationMask & opID) == opID) && !opState && isDirty)
                    if (((operationMask & ops[i].getOperationID()) == ops[i].getOperationID()) &&
                            !sts[i][j].isState() && sts[i][j].isDirty()) {
                    /*Log.d(TAG, "DELETE => privilegeID = " +
                            privilegeID + ", entityID = " +
                            entityDomain.getEntityID() + ", typeID = " +
                            entityDomain.getTypeID() + ", operationID = " +
                            ops[i].getOperationID() + ", statusID = " +
                            sts[i][j].getStatusID());*/

                        /** get the permission domain details to delete **/
                        cPermissionDomain permDomain = getPermissionByIDs(
                                privilegeID, entityDomain.getEntityID(),
                                entityDomain.getTypeID(), ops[i].getOperationID(),
                                sts[i][j].getStatusID());

                        /** delete record in a delete permission list **/
                        delete_perms.add(permDomain);
                    }
                }
            }

            //Log.d(TAG, "CREATE => "+ gson.toJson(create_perms));
            //Log.d(TAG, "DELETE => "+ gson.toJson(delete_perms));

            setCreate_perms(create_perms);
            setDelete_perms(delete_perms);
        }
    }


    @Override
    public void onCreatePermissions(cStatusDomain statusDomain) {

    }

    @Override
    public void onUpdatePermissions(cPermissionDomain originalDomain, cPermissionDomain modifiedDomain) {
        // check whether the permission details from statuses
        Log.d(TAG, "Owner = " + originalDomain.getOwnerID() +
                ", Org. Owner = " + (originalDomain.getGroupBITS() & originalDomain.getOrgID()) +
                ", Other Orgs. = " + (originalDomain.getGroupBITS() & ~originalDomain.getOrgID()) +
                ", Permissions = " + originalDomain.getPermsBITS() +
                ", Statuses = " + originalDomain.getStatusBITS() +
                ", Created Date = " + originalDomain.getCreatedDate() +
                ", Modified Date = " + originalDomain.getModifiedDate() +
                ", Synced Date = " + originalDomain.getSyncedDate());

        Log.d(TAG, "Owner = " + modifiedDomain.getOwnerID() +
                ", Org. Owner = " + (modifiedDomain.getGroupBITS() & modifiedDomain.getOrgID()) +
                ", Other Orgs. = " + (modifiedDomain.getGroupBITS() & ~modifiedDomain.getOrgID()) +
                ", Permissions = " + modifiedDomain.getPermsBITS() +
                ", Statuses = " + modifiedDomain.getStatusBITS() +
                ", Created Date = " + modifiedDomain.getCreatedDate() +
                ", Modified Date = " + modifiedDomain.getModifiedDate() +
                ", Synced Date = " + modifiedDomain.getSyncedDate());

        update_perms = new HashSet<>();

        if (isPermissionDirty(originalDomain, modifiedDomain)) {
            update_perms.add(modifiedDomain);
            Log.d(TAG, "UPDATE => "+ gson.toJson(update_perms));
        }
        setUpdate_perms(update_perms);
    }

    public Set<cPermissionDomain> getCreate_perms() {
        return create_perms;
    }

    public Set<cPermissionDomain> getUpdate_perms() {
        return update_perms;
    }

    public Set<cPermissionDomain> getDelete_perms() {
        return delete_perms;
    }

    public void setCreate_perms(Set<cPermissionDomain> create_perms) {
        this.create_perms = create_perms;
    }

    public void setUpdate_perms(Set<cPermissionDomain> update_perms) {
        this.update_perms = update_perms;
    }

    public void setDelete_perms(Set<cPermissionDomain> delete_perms) {
        this.delete_perms = delete_perms;
    }

    /**
     * asynchronously create permission domains
     **/
    public void createPermissions(ArrayList<cPermissionDomain> create_perms) {

        cPermParam param = new cPermParam(create_perms, null, null);

        new AsyncTask<cPermParam, Void, Void>() {
            @Override
            protected Void doInBackground(cPermParam... param) {
                for (int i = 0; i < param[0].getCreate_perms().size(); i++) {
                    if (param[0].getCreate_perms() != null) {
                        permissionHandler.addPermission(param[0].getCreate_perms().get(i));
                    }
                }

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
            }
        }.execute(param);
    }

    /**
     * asynchronously update permission domains
     **/
    public void updatePermissions(ArrayList<cPermissionDomain> update_perms) {

        cPermParam param = new cPermParam(null, update_perms, null);

        new AsyncTask<cPermParam, Void, Void>() {
            @Override
            protected Void doInBackground(cPermParam... param) {

                if (param[0].getUpdate_perms() != null) {
                    for (int i = 0; i < param[0].getUpdate_perms().size(); i++) {
                        permissionHandler.updatePermission(param[0].getUpdate_perms().get(i));
                    }
                }

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
            }
        }.execute(param);
    }

    /**
     * asynchronously delete permission domains
     **/
    public void deletePermissions(ArrayList<cPermissionDomain> delete_perms) {

        cPermParam param = new cPermParam(null, null, delete_perms);

        new AsyncTask<cPermParam, Void, Void>() {
            @Override
            protected Void doInBackground(cPermParam... param) {
                for (int i = 0; i < param[0].getDelete_perms().size(); i++) {
                    if (param[0].getDelete_perms().get(i) != null) {
                        permissionHandler.deletePermission(param[0].getDelete_perms().get(i));
                    }
                }

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
            }
        }.execute(param);
    }
}
