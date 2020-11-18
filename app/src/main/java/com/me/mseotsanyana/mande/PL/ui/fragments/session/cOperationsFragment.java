package com.me.mseotsanyana.mande.PL.ui.fragments.session;

import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.google.gson.Gson;
import com.me.mseotsanyana.expandablelayoutlibrary.cExpandableLayout;

import com.me.mseotsanyana.mande.BLL.model.session.cEntityModel;
import com.me.mseotsanyana.mande.BLL.model.session.cOperationModel;
import com.me.mseotsanyana.mande.BLL.model.session.cPermissionModel;
import com.me.mseotsanyana.mande.BLL.model.session.cStatusModel;
import com.me.mseotsanyana.mande.PL.ui.adapters.session.cStatusTreeAdapter;
import com.me.mseotsanyana.mande.UTIL.INTERFACE.iEntityTVHInterface;
import com.me.mseotsanyana.mande.UTIL.INTERFACE.iTreeAdapterCallback;
import com.me.mseotsanyana.mande.R;
import com.me.mseotsanyana.mande.UTIL.cFontManager;
import com.me.mseotsanyana.mande.UTIL.cPermParam;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import static com.me.mseotsanyana.mande.UTIL.cConstant.COLLAPSE;
import static com.me.mseotsanyana.mande.UTIL.cConstant.EXPAND;
import static com.me.mseotsanyana.mande.UTIL.cConstant.NUM_OPS;
import static com.me.mseotsanyana.mande.UTIL.cConstant.NUM_STS;
import static com.me.mseotsanyana.mande.UTIL.cConstant.OWNER;
import static com.me.mseotsanyana.mande.UTIL.cConstant.GROUP;
import static com.me.mseotsanyana.mande.UTIL.cConstant.OTHER;


public class cOperationsFragment extends Fragment /*implements iTreeAdapterCallback*/ {
    private static final String TAG = cOperationsFragment.class.getSimpleName();
    private static final SimpleDateFormat formatter = new SimpleDateFormat(
            "dd MMMM, yyyy hh:mm:ss a", Locale.US);

    //private cSessionManager session;

    private cExpandableLayout expandableLayoutOwner;
    private cExpandableLayout expandableLayoutGroup;
    private cExpandableLayout expandableLayoutOther;

    private cStatusTreeAdapter statusOwnerTreeAdapter,
            statusGroupTreeAdapter, statusOtherTreeAdapter;

    private cEntityModel entityModel;

    private cOperationModel[] operationModels;
    private cStatusModel[][] statusModels;

    private cOperationModel ownerOperationModel;
    private cOperationModel groupOperationModel;
    private cOperationModel otherOperationModel;
    private cOperationModel[] ops = new cOperationModel[NUM_OPS];
    private cStatusModel[][] sts = new cStatusModel[NUM_OPS][NUM_STS];

    private ArrayList<cStatusModel> statusOwnerModels;
    private ArrayList<cStatusModel> statusGroupModels;
    private ArrayList<cStatusModel> statusOtherModels;

    private ArrayList<cPermissionModel> permissionModels;

    //private cPermissionHandler permissionHandler;

    private Set<cPermissionModel> create_perms;
    private Set<cPermissionModel> update_perms;
    private Set<cPermissionModel> delete_perms;

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

    public cOperationsFragment newInstance(int privilegeID, cEntityModel entityModel,
                                           cOperationModel[] operationModels, int operationMask,
                                           cStatusModel[][] statusModels, int statusMask,
                                           ArrayList<cPermissionModel> permissionModels,
                                           iEntityTVHInterface entityTreeVHInterface) {
        Bundle bundle = new Bundle();

        bundle.putInt("PRIVILEDGEID", privilegeID);
        //bundle.putParcelable("ENTITY", entityModel);
        bundle.putInt("OPERATIONSMASK", operationMask);
        bundle.putString("OPERATIONS", gson.toJson(operationModels));
        bundle.putInt("STATUSESMASK", statusMask);
        bundle.putString("STATUSES", gson.toJson(statusModels));
        //bundle.putParcelableArrayList("PERMISSIONS", permissionModels);
        bundle.putSerializable("IPERMISSION", entityTreeVHInterface);

        cOperationsFragment fragment = new cOperationsFragment();
        fragment.setArguments(bundle);

        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //this.session = new cSessionManager(getContext());

        //this.permissionHandler = new cPermissionHandler(getContext());

        this.privilegeID = getArguments().getInt("PRIVILEDGEID");
        this.entityModel = getArguments().getParcelable("ENTITY");
        String operations = getArguments().getString("OPERATIONS");
        this.operationModels = gson.fromJson(operations, cOperationModel[].class);
        this.operationMask = getArguments().getInt("OPERATIONSMASK");
        String statuses = getArguments().getString("STATUSES");
        this.statusModels = gson.fromJson(statuses, cStatusModel[][].class);
        this.statusMask = getArguments().getInt("STATUSESMASK");

        this.permissionModels = null;//getArguments().getParcelableArrayList("PERMISSIONS");
        this.entityTreeVHInterface = (iEntityTVHInterface) getArguments().getSerializable("IPERMISSION");

        //Log.d(TAG, "statusMask = "+statusMask);

        /** create a deep copy of operations **/
        for (int i = 0; i < operationModels.length; i++) {
            /* create operation */
            if (i == OWNER) {
                ownerOperationModel = new cOperationModel(operationModels[i]);
            }
            if (i == GROUP) {
                groupOperationModel = new cOperationModel(operationModels[i]);
            }
            if (i == OTHER) {
                otherOperationModel = new cOperationModel(operationModels[i]);
            }
        }

        /** create a deep copy of statuses **/
        statusOwnerModels = new ArrayList<>();
        statusGroupModels = new ArrayList<>();
        statusOtherModels = new ArrayList<>();

        for (int i = 0; i < statusModels[OWNER].length; i++) {
            statusOwnerModels.add(i, new cStatusModel(statusModels[OWNER][i]));
            statusGroupModels.add(i, new cStatusModel(statusModels[GROUP][i]));
            statusOtherModels.add(i, new cStatusModel(statusModels[OTHER][i]));
        }
        //Log.d(TAG, gson.toJson(statusOwnerModels));
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
        textViewNameOwner.setText(ownerOperationModel.getName());
        textViewNameGroup.setText(groupOperationModel.getName());
        textViewNameOther.setText(otherOperationModel.getName());

        textViewDescriptionOwner.setText(ownerOperationModel.getDescription());
        textViewDescriptionGroup.setText(groupOperationModel.getDescription());
        textViewDescriptionOther.setText(otherOperationModel.getDescription());

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
        //appCompatCheckBoxOperationOwner.setChecked(ownerOperationModel.isState());
        //appCompatCheckBoxOperationGroup.setChecked(groupOperationModel.isState());
        //appCompatCheckBoxOperationOther.setChecked(otherOperationModel.isState());

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
//                    /** check whether owner operation is either modified or not **/
//                    if (!operationModels[OWNER].isState())
//                        ownerOperationModel.setDirty(true);
//                    else
//                        ownerOperationModel.setDirty(false);
//                    ownerOperationModel.setState(true);
//
//                    /** check whether group operation is either modified or not **/
//                    if (!operationModels[GROUP].isState())
//                        groupOperationModel.setDirty(true);
//                    else
//                        groupOperationModel.setDirty(false);
//                    groupOperationModel.setState(true);
//
//                    /** check whether other operation is either modified or not **/
//                    if (!operationModels[OTHER].isState())
//                        otherOperationModel.setDirty(true);
//                    else
//                        otherOperationModel.setDirty(false);
//                    otherOperationModel.setState(true);

                    /** update all the status checkboxes **/
                    for (int i = 0; i < statusModels[0].length; i++) {
                        /** owner statuses **/
                        //-statusOwnerModels.get(i).setState(statusModels[OWNER][i].isState());
                        //-statusOwnerModels.get(i).setDirty(statusModels[OWNER][i].isDirty());

                        /** group statuses **/
                        //-statusGroupModels.get(i).setState(statusModels[GROUP][i].isState());
                        //-statusGroupModels.get(i).setDirty(statusModels[GROUP][i].isDirty());

                        /** other statuses **/
                        //-statusOtherModels.get(i).setState(statusModels[OTHER][i].isState());
                        //-statusOtherModels.get(i).setDirty(statusModels[OTHER][i].isDirty());
                    }

                } else {
                    /** check whether owner operation is either modified or not **/
//                    if (operationModels[OWNER].isState())
//                        ownerOperationModel.setDirty(true);
//                    else
//                        ownerOperationModel.setDirty(false);
//                    ownerOperationModel.setState(false);
//
//                    /** check whether group operation is either modified or not **/
//                    if (operationModels[GROUP].isState())
//                        groupOperationModel.setDirty(true);
//                    else
//                        groupOperationModel.setDirty(false);
//                    groupOperationModel.setState(false);
//
//                    /** check whether other operation is either modified or not **/
//                    if (operationModels[OTHER].isState())
//                        otherOperationModel.setDirty(true);
//                    else
//                        otherOperationModel.setDirty(false);
//                    otherOperationModel.setState(false);

                    /** unchecking all operations implies removing all statuses **/
                    for (int i = 0; i < statusModels[0].length; i++) {
                        /** owner statuses **/
                        /*if (statusModels[OWNER][i].isState())
                            statusOwnerModels.get(i).setDirty(true);
                        else
                            statusOwnerModels.get(i).setDirty(false);
                        statusOwnerModels.get(i).setState(false);*/

                        /** group statuses **/
                        /*if (statusModels[GROUP][i].isState())
                            statusGroupModels.get(i).setDirty(true);
                        else
                            statusGroupModels.get(i).setDirty(false);
                        statusGroupModels.get(i).setState(false);*/

                        /** other statuses **/
                        /*if (statusModels[OTHER][i].isState())
                            statusOtherModels.get(i).setDirty(true);
                        else
                            statusOtherModels.get(i).setDirty(false);
                        statusOtherModels.get(i).setState(false);*/
                    }
                }

                /** update the owner, group and other operation checkboxes **/
                //appCompatCheckBoxOperationOwner.setChecked(ownerOperationModel.isState());
                //appCompatCheckBoxOperationGroup.setChecked(groupOperationModel.isState());
                //appCompatCheckBoxOperationOther.setChecked(otherOperationModel.isState());

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
//                if (((CheckBox) view).isChecked()) {
//                    if (!operationModels[OWNER].isState())
//                        ownerOperationModel.setDirty(true);
//                    else
//                        ownerOperationModel.setDirty(false);
//                    ownerOperationModel.setState(true);
//
//                    /** set the owner statuses to the database default values **/
//                    for (int i = 0; i < statusModels[OWNER].length; i++) {
//                        //statusOwnerModels.get(i).setState(statusModels[OWNER][i].isState());
//                        //statusOwnerModels.get(i).setDirty(statusModels[OWNER][i].isDirty());
//                    }
//
//                } else {
//                    if (operationModels[OWNER].isState())
//                        ownerOperationModel.setDirty(true);
//                    else
//                        ownerOperationModel.setDirty(false);
//                    ownerOperationModel.setState(false);
//
//                    /** disable the owner statuses due to owner operation being disabled **/
//                    appCompatCheckBoxAllOwnerStatuses.setChecked(false);
//
//                    /*for (int i = 0; i < statusModels[OWNER].length; i++) {
//                        if (statusModels[OWNER][i].isState())
//                            statusOwnerModels.get(i).setDirty(true);
//                        else
//                            statusOwnerModels.get(i).setDirty(false);
//                        statusOwnerModels.get(i).setState(false);
//                    }*/
//                }
//
//                /** refresh a owner status adapter **/
//                statusOwnerTreeAdapter.notifyDataSetChanged();
//
//                /** check the all operation checkbox if all operations are checked **/
//                if (appCompatCheckBoxAllOperations != null) {
//                    if (isAllOperationsChecked()) {
//                        appCompatCheckBoxAllOperations.setChecked(true);
//                    } else {
//                        appCompatCheckBoxAllOperations.setChecked(false);
//                    }
//                }
//
//                /** refresh permissions after the update **/
//                //onRefreshPermissions();
            }
        });

        /** event listener for group operation **/
        appCompatCheckBoxOperationGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (((CheckBox) view).isChecked()) {
//                    if (!operationModels[GROUP].isState()) {
//                        groupOperationModel.setDirty(true);
//                    }
//                    groupOperationModel.setState(true);
//
//                    /** set the owner statuses to the database default values **/
//                    for (int i = 0; i < statusModels[GROUP].length; i++) {
//                        //statusGroupModels.get(i).setState(statusModels[GROUP][i].isState());
//                        //statusGroupModels.get(i).setDirty(statusModels[GROUP][i].isDirty());
//                    }
//                } else {
//                    if (operationModels[GROUP].isState()) {
//                        groupOperationModel.setDirty(true);
//                    }
//                    groupOperationModel.setState(false);
//
//                    /** disable the owner statuses due to owner operation being disabled **/
//                    appCompatCheckBoxAllGroupStatuses.setChecked(false);
//
//                    for (int i = 0; i < statusModels[GROUP].length; i++) {
//                        /*if (statusModels[GROUP][i].isState())
//                            statusGroupModels.get(i).setDirty(true);
//                        else
//                            statusGroupModels.get(i).setDirty(false);
//                        statusGroupModels.get(i).setState(false);*/
//                    }
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
//                if (((CheckBox) view).isChecked()) {
//                    if (!operationModels[OTHER].isState()) {
//                        otherOperationModel.setDirty(true);
//                    }
//                    otherOperationModel.setState(true);
//
//                    /** set the owner statuses to the database default values **/
//                    for (int i = 0; i < statusModels[OTHER].length; i++) {
//                        //statusOtherModels.get(i).setState(statusModels[OTHER][i].isState());
//                        //statusOtherModels.get(i).setDirty(statusModels[OTHER][i].isDirty());
//                    }
//                } else {
//                    if (operationModels[OTHER].isState()) {
//                        otherOperationModel.setDirty(true);
//                    }
//                    otherOperationModel.setState(false);
//
//                    /** disable the owner statuses due to owner operation being disabled **/
//                    appCompatCheckBoxAllOtherStatuses.setChecked(false);
//
//                    for (int i = 0; i < statusModels[OTHER].length; i++) {
//                        /*if (statusModels[OTHER][i].isState())
//                            statusOtherModels.get(i).setDirty(true);
//                        else
//                            statusOtherModels.get(i).setDirty(false);
//                        statusOtherModels.get(i).setState(false);*/
//                    }
//                }

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
//                    for (int i = 0; i < statusModels[OWNER].length; i++) {
//                        /*if (!statusModels[OWNER][i].isState())
//                            statusOwnerModels.get(i).setDirty(true);
//                        else
//                            statusOwnerModels.get(i).setDirty(false);
//                        statusOwnerModels.get(i).setState(true);*/
//                    }
//
//                    if (!operationModels[OWNER].isState())
//                        ownerOperationModel.setDirty(true);
//                    else
//                        ownerOperationModel.setDirty(false);
//                    ownerOperationModel.setState(true);
//                } else {
//                    for (int i = 0; i < statusModels[OWNER].length; i++) {
//                        /*if (statusModels[OWNER][i].isState())
//                            statusOwnerModels.get(i).setDirty(true);
//                        else
//                            statusOwnerModels.get(i).setDirty(false);
//                        statusOwnerModels.get(i).setState(statusModels[OWNER][i].isState());*/
//                    }
//
//                    if (operationModels[OWNER].isState())
//                        ownerOperationModel.setDirty(true);
//                    else
//                        ownerOperationModel.setDirty(false);
//                    ownerOperationModel.setState(operationModels[OWNER].isState());
                }

                /** check owner operation **/
                //appCompatCheckBoxOperationOwner.setChecked(ownerOperationModel.isState());

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
                    for (int i = 0; i < statusModels[GROUP].length; i++) {
                        /*if (!statusModels[GROUP][i].isState())
                            statusGroupModels.get(i).setDirty(true);
                        else
                            statusGroupModels.get(i).setDirty(false);
                        statusGroupModels.get(i).setState(true);*/
                    }

//                    if (!operationModels[GROUP].isState())
//                        groupOperationModel.setDirty(true);
//                    else
//                        groupOperationModel.setDirty(false);
//                    groupOperationModel.setState(true);
                } else {
                    for (int i = 0; i < statusModels[GROUP].length; i++) {
                        /*if (statusModels[GROUP][i].isState())
                            statusGroupModels.get(i).setDirty(true);
                        else
                            statusGroupModels.get(i).setDirty(false);
                        statusGroupModels.get(i).setState(statusModels[GROUP][i].isState());*/
                    }

//                    if (operationModels[GROUP].isState())
//                        groupOperationModel.setDirty(true);
//                    else
//                        groupOperationModel.setDirty(false);
//                    groupOperationModel.setState(operationModels[GROUP].isState());
                }

                /** check group operation **/
                //appCompatCheckBoxOperationGroup.setChecked(groupOperationModel.isState());

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
                    for (int i = 0; i < statusModels[OTHER].length; i++) {
                        /*if (!statusModels[OTHER][i].isState())
                            statusOtherModels.get(i).setDirty(true);
                        else
                            statusOtherModels.get(i).setDirty(false);
                        statusOtherModels.get(i).setState(true);*/
                    }

//                    if (!operationModels[OTHER].isState())
//                        otherOperationModel.setDirty(true);
//                    else
//                        otherOperationModel.setDirty(false);
//                    otherOperationModel.setState(true);
                } else {
                    for (int i = 0; i < statusModels[OTHER].length; i++) {
                        /*if (statusModels[OTHER][i].isState())
                            statusOtherModels.get(i).setDirty(true);
                        else
                            statusOtherModels.get(i).setDirty(false);
                        statusOtherModels.get(i).setState(statusModels[OTHER][i].isState());*/
                    }

//                    if (operationModels[OTHER].isState())
//                        otherOperationModel.setDirty(true);
//                    else
//                        otherOperationModel.setDirty(false);
//                    otherOperationModel.setState(operationModels[OTHER].isState());
                }

                /** check group operation **/
                //appCompatCheckBoxOperationOther.setChecked(otherOperationModel.isState());

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

        statusOwnerTreeAdapter = null;
//                new cStatusTreeAdapter(getContext(),
//                privilegeID, entityModel, ownerOperationModel, statusOwnerModels,
//                permissionModels, operationModels[OWNER], statusModels[OWNER],
//                appCompatCheckBoxOperationOwner, appCompatCheckBoxAllOwnerStatuses,
//                cOperationsFragment.this);

        statusGroupTreeAdapter = null;
//                new cStatusTreeAdapter(getContext(),
//                privilegeID, entityModel, groupOperationModel, statusGroupModels,
//                permissionModels, operationModels[GROUP], statusModels[GROUP],
//                appCompatCheckBoxOperationGroup, appCompatCheckBoxAllGroupStatuses,
//                cOperationsFragment.this);

        statusOtherTreeAdapter = null;
//                new cStatusTreeAdapter(getContext(),
//                privilegeID, entityModel, otherOperationModel, statusOtherModels,
//                permissionModels, operationModels[OTHER], statusModels[OTHER],
//                appCompatCheckBoxOperationOther, appCompatCheckBoxAllOtherStatuses,
//                cOperationsFragment.this);

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
//        if (ownerOperationModel.isState() &&
//                groupOperationModel.isState() &&
//                otherOperationModel.isState()) {
//            return true;
//        } else {
//            return false;
//        }
        return true;
    }

    /**
     * find if all own statuses are checked
     **/
    public boolean isAllStatusOwnerChecked() {
        for (int i = 0; i < statusOwnerModels.size(); i++) {
            /*if (!statusOwnerModels.get(i).isState()) {
                return false;
            }*/
        }
        return true;
    }

    /**
     * find if all group statuses are checked
     **/
    public boolean isAllStatusGroupChecked() {
        for (int i = 0; i < statusGroupModels.size(); i++) {
            /*if (!statusGroupModels.get(i).isState()) {
                return false;
            }*/
        }
        return true;
    }

    /**
     * find if all other statuses are checked
     **/
    public boolean isAllStatusOtherChecked() {
        for (int i = 0; i < statusOtherModels.size(); i++) {
            /*if (!statusOtherModels.get(i).isState()) {
                return false;
            }*/
        }
        return true;
    }

    /**
     * return a permission domain
     **/
    public cPermissionModel getPermissionByIDs(int privilegeID, int entityID, int typeID,
                                                int operationID, int statusID) {
        cPermissionModel permissionModel = null;
        for (int i = 0; i < permissionModels.size(); i++) {
            /*if ((permissionModels.get(i).getPrivilegeModel().getPrivilegeID() == privilegeID) &&
                    (permissionModels.get(i).getEntityModel().getEntityID() == entityID) &&
                    (permissionModels.get(i).getEntityModel().getTypeID() == typeID) &&
                    (permissionModels.get(i).getOperationModel().getOperationID() == operationID) &&
                    (permissionModels.get(i).getStatusModel().getStatusID() == statusID))*/ {

                permissionModel = permissionModels.get(i);

                return permissionModel;
            }
        }
        return permissionModel;
    }

    /**
     * is there a change in common permission attributes
     **/
    public boolean isPermissionDirty(cPermissionModel originalModel, cPermissionModel modifiedModel) {
        if (((originalModel.getOwnerID() == modifiedModel.getOwnerID()) &&
                (originalModel.getOrgID() == modifiedModel.getOrgID()) &&
                (originalModel.getGroupBITS() == modifiedModel.getGroupBITS()) &&
                (originalModel.getPermsBITS() == modifiedModel.getPermsBITS()) &&
                (originalModel.getStatusBITS() == modifiedModel.getStatusBITS()))) {

            return false;
        } else {
            return true;
        }
    }


//    @Override
    public void onRefreshTreeAdapter() {

    }

//    @Override
//    public void onRefreshPermissions() {
//        if (statusModels != null) {
//            /** consolidate the modified operations of the permission **/
//            create_perms = new HashSet<>();
//            delete_perms = new HashSet<>();
//
//            ops[OWNER] = ownerOperationModel;
//            ops[GROUP] = groupOperationModel;
//            ops[OTHER] = otherOperationModel;
//
//            for (int i = 0; i < statusModels[OWNER].length; i++) {
//                sts[OWNER][i] = statusOwnerModels.get(i);
//                sts[GROUP][i] = statusGroupModels.get(i);
//                sts[OTHER][i] = statusOtherModels.get(i);
//            }
//
//            /** deal with change of part of composite key operationID and statusID **/
//            for (int i = 0; i < ops.length; i++) {
//                // populate privilege details
//                cPermissionModel privilegeModel = new cPermissionModel();
//                privilegeModel.setPrivilegeID(privilegeID);
//
//                // populate entity details
//                cEntityModel tmpEntityModel = new cEntityModel();
//                tmpEntityModel.setEntityID(entityModel.getEntityID());
//                tmpEntityModel.setTypeID(entityModel.getTypeID());
//
//                // populate operation details
//                cOperationModel operationModel = new cOperationModel();
//                operationModel.setOperationID(ops[i].getOperationID());
//
//                for (int j = 0; j < sts[i].length; j++) {
//                    /** if the state of operation or/and status are true and they are not in the
//                     permission's table ==> CREATE (or ADD) **/
//                    if ((((operationMask & ops[i].getOperationID()) != ops[i].getOperationID()) &&
//                            ops[i].isState() && ops[i].isDirty()) /*||
//                            (sts[i][j].isState() && sts[i][j].isDirty())*/) {
//
//                        /** keep current permission domain **/
//                        //private cPermissionModel permissionModel;
//
//                        cPermissionModel permModel = new cPermissionModel();
//
//                        /** set the permission domain details **/
//                        /*permModel.setPrivilegeModel(privilegeModel);
//                        permModel.setEntityModel(tmpEntityModel);
//                        permModel.setOperationModel(operationModel);
//                        permModel.setStatusModel(new cStatusModel(sts[i][j]));*/
//                        //permModel.setOwnerID(session.loadUserID());
//                        //permModel.setOrgID(session.loadOrgID());
//
//                    /*Log.d(TAG, "CREATE => privilegeID = " +
//                            permModel.getPrivilegeModel().getPrivilegeID() + ", entityID = " +
//                            permModel.getEntityModel().getEntityID() + ", typeID = " +
//                            permModel.getEntityModel().getTypeID() + ", operationID = " +
//                            permModel.getOperationModel().getOperationID() + ", statusID = " +
//                            permModel.getStatusModel().getStatusID() + ", ownerID = " +
//                            permModel.getOwnerID() + ", organizationID = " +
//                            permModel.getOrganizationID());*/
//
//                        /** add record in a create permission list to create **/
//                        create_perms.add(permModel);
//                    }
//
//                    // if the state is false and the operation is in the
//                    // permission's table ==> DELETE => (((operationMask & opID) == opID) && !opState && isDirty)
//                    if (((operationMask & ops[i].getOperationID()) == ops[i].getOperationID())/* &&
//                            !sts[i][j].isState() && sts[i][j].isDirty()*/) {
//                    /*Log.d(TAG, "DELETE => privilegeID = " +
//                            privilegeID + ", entityID = " +
//                            entityModel.getEntityID() + ", typeID = " +
//                            entityModel.getTypeID() + ", operationID = " +
//                            ops[i].getOperationID() + ", statusID = " +
//                            sts[i][j].getStatusID());*/
//
//                        /** get the permission domain details to delete **/
//                        cPermissionModel permModel = getPermissionByIDs(
//                                privilegeID, entityModel.getEntityID(),
//                                entityModel.getTypeID(), ops[i].getOperationID(),
//                                sts[i][j].getStatusID());
//
//                        /** delete record in a delete permission list **/
//                        delete_perms.add(permModel);
//                    }
//                }
//            }
//
//            //Log.d(TAG, "CREATE => "+ gson.toJson(create_perms));
//            //Log.d(TAG, "DELETE => "+ gson.toJson(delete_perms));
//
//            setCreate_perms(create_perms);
//            setDelete_perms(delete_perms);
//        }
//    }



   // @Override
    public void onUpdatePermissions(cPermissionModel originalModel, cPermissionModel modifiedModel) {
        // check whether the permission details from statuses
        Log.d(TAG, "Owner = " + originalModel.getOwnerID() +
                ", Org. Owner = " + (originalModel.getGroupBITS() & originalModel.getOrgID()) +
                ", Other Orgs. = " + (originalModel.getGroupBITS() & ~originalModel.getOrgID()) +
                ", Permissions = " + originalModel.getPermsBITS() +
                ", Statuses = " + originalModel.getStatusBITS() +
                ", Created Date = " + originalModel.getCreatedDate() +
                ", Modified Date = " + originalModel.getModifiedDate() +
                ", Synced Date = " + originalModel.getSyncedDate());

        Log.d(TAG, "Owner = " + modifiedModel.getOwnerID() +
                ", Org. Owner = " + (modifiedModel.getGroupBITS() & modifiedModel.getOrgID()) +
                ", Other Orgs. = " + (modifiedModel.getGroupBITS() & ~modifiedModel.getOrgID()) +
                ", Permissions = " + modifiedModel.getPermsBITS() +
                ", Statuses = " + modifiedModel.getStatusBITS() +
                ", Created Date = " + modifiedModel.getCreatedDate() +
                ", Modified Date = " + modifiedModel.getModifiedDate() +
                ", Synced Date = " + modifiedModel.getSyncedDate());

        update_perms = new HashSet<>();

        if (isPermissionDirty(originalModel, modifiedModel)) {
            update_perms.add(modifiedModel);
            Log.d(TAG, "UPDATE => "+ gson.toJson(update_perms));
        }
        setUpdate_perms(update_perms);
    }

    public Set<cPermissionModel> getCreate_perms() {
        return create_perms;
    }

    public Set<cPermissionModel> getUpdate_perms() {
        return update_perms;
    }

    public Set<cPermissionModel> getDelete_perms() {
        return delete_perms;
    }

    public void setCreate_perms(Set<cPermissionModel> create_perms) {
        this.create_perms = create_perms;
    }

    public void setUpdate_perms(Set<cPermissionModel> update_perms) {
        this.update_perms = update_perms;
    }

    public void setDelete_perms(Set<cPermissionModel> delete_perms) {
        this.delete_perms = delete_perms;
    }

    /**
     * asynchronously create permission domains
     **/
    public void createPermissions(ArrayList<cPermissionModel> create_perms) {

        cPermParam param = new cPermParam(null, null, null);

        new AsyncTask<cPermParam, Void, Void>() {
            @Override
            protected Void doInBackground(cPermParam... param) {
                for (int i = 0; i < param[0].getCreate_perms().size(); i++) {
                    if (param[0].getCreate_perms() != null) {
                        //--permissionHandler.addPermission(param[0].getCreate_perms().get(i));
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
    public void updatePermissions(ArrayList<cPermissionModel> update_perms) {

        cPermParam param = new cPermParam(null, null, null);

        new AsyncTask<cPermParam, Void, Void>() {
            @Override
            protected Void doInBackground(cPermParam... param) {

                if (param[0].getUpdate_perms() != null) {
                    for (int i = 0; i < param[0].getUpdate_perms().size(); i++) {
                        //--permissionHandler.updatePermission(param[0].getUpdate_perms().get(i));
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
    public void deletePermissions(ArrayList<cPermissionModel> delete_perms) {

        cPermParam param = new cPermParam(null, null, null);

        new AsyncTask<cPermParam, Void, Void>() {
            @Override
            protected Void doInBackground(cPermParam... param) {
                for (int i = 0; i < param[0].getDelete_perms().size(); i++) {
                    if (param[0].getDelete_perms().get(i) != null) {
                        //--permissionHandler.deletePermission(param[0].getDelete_perms().get(i));
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
