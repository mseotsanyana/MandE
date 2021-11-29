package com.me.mseotsanyana.mande.PL.ui.adapters.session;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.gson.Gson;
import com.me.mseotsanyana.mande.BLL.model.session.cEntityModel;
import com.me.mseotsanyana.mande.BLL.model.session.cMenuModel;
import com.me.mseotsanyana.mande.BLL.model.session.cOperationStatusCollection;
import com.me.mseotsanyana.mande.BLL.model.session.cSectionModel;
import com.me.mseotsanyana.mande.BLL.model.session.cOperationModel;
import com.me.mseotsanyana.mande.BLL.model.session.cPermissionModel;
import com.me.mseotsanyana.mande.BLL.model.session.cStatusModel;
import com.me.mseotsanyana.mande.BLL.model.session.cUnixOperationCollection;
import com.me.mseotsanyana.mande.BLL.model.session.cUnixOperationModel;
import com.me.mseotsanyana.mande.PL.presenters.session.iPermissionPresenter;
import com.me.mseotsanyana.mande.PL.ui.listeners.session.iViewPermissionListener;
import com.me.mseotsanyana.mande.R;
import com.me.mseotsanyana.mande.UTIL.cFontManager;
import com.me.mseotsanyana.treeadapterlibrary.cNode;
import com.me.mseotsanyana.treeadapterlibrary.cTreeAdapter;
import com.me.mseotsanyana.treeadapterlibrary.cTreeModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mseotsanyana on 2017/02/27.
 */

public class cPermissionAdapter extends cTreeAdapter implements iViewPermissionListener, Filterable {
    private static final String TAG = cPermissionAdapter.class.getSimpleName();
//    private static final SimpleDateFormat sdf = cConstant.SHORT_FORMAT_DATE;

    private static final int PERMISSION_LIST      = 0;
    private static final int MENUITEM_SECTION     = 1;
    private static final int MENUITEM_LIST        = 2;
    private static final int SUB_MENUITEM_LIST    = 3;
    private static final int ENTITYMODULE_SECTION = 4;
    private static final int ENTITY_LIST          = 5;
    private static final int ENTITY_OPS_SECTION   = 6;
    private static final int ENTITY_OPS_LIST      = 7;
    private static final int OPS_STATUS_LIST      = 8;
    private static final int UNIX_OPS_SECTION     = 9;
    private static final int UNIX_OPS_LIST        = 10;

    private static final String DELETED   = "1";
    private static final String BLOCKED   = "2";
    private static final String ACTIVATED = "4";
    private static final String CANCELLED = "8";
    private static final String PENDING   = "16";

    private static final String OWNER_READ          = "2048";
    private static final String OWNER_UPDATE        = "1024";
    private static final String OWNER_DELETE        = "512";
    private static final String PRIMARY_READ        = "256";
    private static final String PRIMARY_UPDATE      = "128";
    private static final String PRIMARY_DELETE      = "64";
    private static final String SECONDARY_READ      = "32";
    private static final String SECONDARY_UPDATE    = "16";
    private static final String SECONDARY_DELETE    = "8";
    private static final String ORGANIZATION_READ   = "4";
    private static final String ORGANIZATION_UPDATE = "2";
    private static final String ORGANIZATION_DELETE = "1";

    private final Context context;
    private List<cTreeModel> filteredModuleTree;

    private final iPermissionPresenter.View iPermissionPresenterView;


    public cPermissionAdapter(Context context,
                              iPermissionPresenter.View iPermissionPresenterView,
                              List<cTreeModel> moduleTree) {
        super(context, moduleTree);
        this.context = context;
        this.filteredModuleTree = moduleTree;

        this.iPermissionPresenterView = iPermissionPresenterView;
    }

    public void setModuleWithSubMenu(ArrayList<cTreeModel> moduleTree) {
        this.filteredModuleTree = moduleTree;
    }

    @Override
    public RecyclerView.ViewHolder OnCreateTreeViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view;

        switch (viewType) {
            case PERMISSION_LIST:
                view = inflater.inflate(R.layout.session_permission_cardview, parent,
                        false);
                viewHolder = new cPermissionViewHolder(view, this);
                break;

            case MENUITEM_SECTION:
                view = inflater.inflate(R.layout.session_section_fragment, parent,
                        false);
                viewHolder = new cMenuItemSectionViewHolder(view);
                break;

            case MENUITEM_LIST:
                view = inflater.inflate(R.layout.session_menu_entity_cardview, parent, false);
                viewHolder = new cMenuItemViewHolder(view);
                break;

            case SUB_MENUITEM_LIST:
                view = inflater.inflate(R.layout.session_menu_entity_cardview, parent, false);
                viewHolder = new cSubMenuItemViewHolder(view);
                break;

            case ENTITYMODULE_SECTION:
                view = inflater.inflate(R.layout.session_section_fragment, parent,
                        false);
                viewHolder = new cEntityModuleSectionViewHolder(view);
                break;

            case ENTITY_LIST:
                view = inflater.inflate(R.layout.session_menu_entity_cardview, parent,
                        false);
                viewHolder = new cEntityViewHolder(view);
                break;

            case ENTITY_OPS_SECTION:
                view = inflater.inflate(R.layout.session_section_fragment, parent,
                        false);
                viewHolder = new cEntityOperationSectionViewHolder(view);
                break;

            case ENTITY_OPS_LIST:
                view = inflater.inflate(R.layout.session_entity_operation_cardview, parent,
                        false);
                viewHolder = new cEntityOperationViewHolder(view);
                break;

            case OPS_STATUS_LIST:
                view = inflater.inflate(R.layout.session_status_cardview, parent,
                        false);
                viewHolder = new cOperationStatusViewHolder(view);
                break;

            case UNIX_OPS_SECTION:
                view = inflater.inflate(R.layout.session_section_fragment, parent,
                        false);
                viewHolder = new cUnixOperationSectionViewHolder(view);
                break;

            case UNIX_OPS_LIST:
                view = inflater.inflate(R.layout.session_unix_permission, parent,
                        false);
                viewHolder = new cUnixOperationViewHolder(view);
                break;

            default:
                viewHolder = null;
                break;
        }
        return viewHolder;
    }

    @Override
    public void OnBindTreeViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        cNode node = visibleNodes.get(position);
        cTreeModel treeModel = (cTreeModel) node.getObj();

        if (treeModel != null) {
            switch (treeModel.getType()) {
                case PERMISSION_LIST:
                    cPermissionModel permissionModel = (cPermissionModel) treeModel.getModelObject();
                    cPermissionViewHolder PH = ((cPermissionViewHolder) viewHolder);

                    PH.setPaddingLeft(20 * node.getLevel());

                    /* permission menu */
                    PH.textViewName.setText(permissionModel.getName());
                    PH.textViewDescription.setText(permissionModel.getDescription());

                    /* the collapse and expansion of the permission */
                    if (node.isLeaf()) {
                        PH.textViewDetailIcon.setVisibility(View.GONE);
                    } else {
                        PH.textViewDetailIcon.setVisibility(View.VISIBLE);
                        if (node.isExpand()) {
                            PH.textViewDetailIcon.setTypeface(null, Typeface.NORMAL);
                            PH.textViewDetailIcon.setTypeface(
                                    cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
                            PH.textViewDetailIcon.setText(
                                    context.getResources().getString(R.string.fa_angle_down));
                        } else {
                            PH.textViewDetailIcon.setTypeface(null, Typeface.NORMAL);
                            PH.textViewDetailIcon.setTypeface(
                                    cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
                            PH.textViewDetailIcon.setText(
                                    context.getResources().getString(R.string.fa_angle_up));
                        }
                    }
                    PH.textViewDetailIcon.setOnClickListener(v -> expandOrCollapse(position));

                    /* icon for saving updated record */
                    PH.textViewUpdateIcon.setTypeface(null, Typeface.NORMAL);
                    PH.textViewUpdateIcon.setTypeface(
                            cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
                    PH.textViewUpdateIcon.setTextColor(context.getColor(R.color.colorPrimaryDark));
                    PH.textViewUpdateIcon.setText(context.getResources().getString(R.string.fa_update));
                    PH.textViewUpdateIcon.setOnClickListener(view ->
                            PH.viewPermissionListener.onClickUpdatePermission(allNodes));

                    /* icon for deleting a record */
                    PH.textViewDeleteIcon.setTypeface(null, Typeface.NORMAL);
                    PH.textViewDeleteIcon.setTypeface(
                            cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
                    PH.textViewDeleteIcon.setTextColor(context.getColor(R.color.colorPrimaryDark));
                    PH.textViewDeleteIcon.setText(context.getResources().getString(R.string.fa_delete));
                    PH.textViewDeleteIcon.setOnClickListener(view -> {
                        //FIXME:PVH.logFrameListener.onClickDeleteLogFrame(
                        // position,parentLogFrame.getLogFrameID());
                    });

                    break;

                case MENUITEM_SECTION:
                    cSectionModel menuitemModel = (cSectionModel) treeModel.getModelObject();
                    cMenuItemSectionViewHolder MIH = ((cMenuItemSectionViewHolder) viewHolder);

                    MIH.setPaddingLeft(20 * node.getLevel());

                    /* menu item header */
                    MIH.textViewHeading.setText(menuitemModel.getName());

                    /* the collapse and expansion of the menu items */
                    if (node.isLeaf()) {
                        MIH.toggleView.setVisibility(View.GONE);
                    } else {
                        MIH.toggleView.setVisibility(View.VISIBLE);
                        if (node.isExpand()) {
                            MIH.toggleIcon.setTypeface(null, Typeface.NORMAL);
                            MIH.toggleIcon.setTypeface(
                                    cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
                            MIH.toggleIcon.setText(
                                    context.getResources().getString(R.string.fa_angle_down));
                        } else {
                            MIH.toggleIcon.setTypeface(null, Typeface.NORMAL);
                            MIH.toggleIcon.setTypeface(
                                    cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
                            MIH.toggleIcon.setText(
                                    context.getResources().getString(R.string.fa_angle_up));
                        }
                    }
                    MIH.toggleView.setOnClickListener(v -> expandOrCollapse(position));

                    break;

                case MENUITEM_LIST:
                    cMenuModel menuModel = (cMenuModel) treeModel.getModelObject();
                    cMenuItemViewHolder MH = ((cMenuItemViewHolder) viewHolder);

                    MH.setPaddingLeft(20 * node.getLevel());

                    /* check box to select the menu items */
                    MH.checkBoxMenu.setChecked(menuModel.isChecked());
                    MH.checkBoxMenu.setOnClickListener(view -> {
                        boolean checked = ((CheckBox) view).isChecked();
                        menuModel.setChecked(checked);
                    });

                    /* menu items */
                    MH.textViewName.setText(menuModel.getName());
                    MH.textViewDescription.setText(menuModel.getDescription());

                    /* the collapse and expansion of the main menu */
                    if (node.isLeaf()) {
                        MH.textViewDetailIcon.setVisibility(View.GONE);
                    } else {

                        MH.textViewDetailIcon.setVisibility(View.VISIBLE);
                        if (node.isExpand()) {
                            MH.textViewDetailIcon.setTypeface(null, Typeface.NORMAL);
                            MH.textViewDetailIcon.setTypeface(
                                    cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
                            MH.textViewDetailIcon.setText(
                                    context.getResources().getString(R.string.fa_minus));
                        } else {
                            MH.textViewDetailIcon.setTypeface(null, Typeface.NORMAL);
                            MH.textViewDetailIcon.setTypeface(
                                    cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
                            MH.textViewDetailIcon.setText(
                                    context.getResources().getString(R.string.fa_plus));
                        }
                    }
                    MH.textViewDetailIcon.setOnClickListener(v -> expandOrCollapse(position));

                    break;

                case SUB_MENUITEM_LIST:
                    cMenuModel subMenuModel = (cMenuModel) treeModel.getModelObject();
                    cSubMenuItemViewHolder SH = ((cSubMenuItemViewHolder) viewHolder);

                    SH.setPaddingLeft(20 * node.getLevel());

                    SH.textViewDetailIcon.setVisibility(View.GONE);

                    /* check box to select the sub menu item */
                    SH.checkBoxMenu.setChecked(subMenuModel.isChecked());
                    SH.checkBoxMenu.setOnClickListener(view -> {
                        boolean checked = ((CheckBox) view).isChecked();
                        subMenuModel.setChecked(checked);
                    });

                    /* sub menu details */
                    SH.textViewName.setText(subMenuModel.getName());
                    SH.textViewDescription.setText(subMenuModel.getDescription());

                    break;

                case ENTITYMODULE_SECTION:
                    cSectionModel entitymoduleModel = (cSectionModel) treeModel.getModelObject();
                    cEntityModuleSectionViewHolder EMH = ((cEntityModuleSectionViewHolder) viewHolder);

                    EMH.setPaddingLeft(20 * node.getLevel());

                    /* entity module header */
                    EMH.textViewHeading.setText(entitymoduleModel.getName());

                    /* the collapse and expansion of the entities */
                    if (node.isLeaf()) {
                        EMH.toggleView.setVisibility(View.GONE);
                    } else {
                        EMH.toggleView.setVisibility(View.VISIBLE);
                        if (node.isExpand()) {
                            EMH.toggleIcon.setTypeface(null, Typeface.NORMAL);
                            EMH.toggleIcon.setTypeface(
                                    cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
                            EMH.toggleIcon.setText(
                                    context.getResources().getString(R.string.fa_angle_down));
                        } else {
                            EMH.toggleIcon.setTypeface(null, Typeface.NORMAL);
                            EMH.toggleIcon.setTypeface(
                                    cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
                            EMH.toggleIcon.setText(
                                    context.getResources().getString(R.string.fa_angle_up));
                        }
                    }
                    EMH.toggleView.setOnClickListener(v -> expandOrCollapse(position));

                    break;

                case ENTITY_LIST:
                    cEntityModel entityModel = (cEntityModel) treeModel.getModelObject();
                    cEntityViewHolder EH = ((cEntityViewHolder) viewHolder);

                    EH.setPaddingLeft(20 * node.getLevel());

                    /* check box to select the entities */
                    EH.checkBoxEntity.setChecked(entityModel.isChecked());
                    EH.checkBoxEntity.setOnClickListener(view -> {
                        boolean checked = ((CheckBox) view).isChecked();
                        entityModel.setChecked(checked);
                    });

                    /* entity details */
                    EH.textViewName.setText(entityModel.getName());
                    EH.textViewDescription.setText(entityModel.getDescription());

                    /* the collapse and expansion of the entities */
                    if (node.isLeaf()) {
                        EH.textViewDetailIcon.setVisibility(View.GONE);
                    } else {

                        EH.textViewDetailIcon.setVisibility(View.VISIBLE);
                        if (node.isExpand()) {
                            EH.textViewDetailIcon.setTypeface(null, Typeface.NORMAL);
                            EH.textViewDetailIcon.setTypeface(
                                    cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
                            EH.textViewDetailIcon.setText(
                                    context.getResources().getString(R.string.fa_minus));
                        } else {
                            EH.textViewDetailIcon.setTypeface(null, Typeface.NORMAL);
                            EH.textViewDetailIcon.setTypeface(
                                    cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
                            EH.textViewDetailIcon.setText(
                                    context.getResources().getString(R.string.fa_plus));
                        }
                    }
                    EH.textViewDetailIcon.setOnClickListener(v -> expandOrCollapse(position));

                    break;

                case ENTITY_OPS_SECTION:
                    cSectionModel entityOperationSection = (cSectionModel) treeModel.getModelObject();
                    cEntityOperationSectionViewHolder EOSH = ((cEntityOperationSectionViewHolder)
                            viewHolder);

                    EOSH.setPaddingLeft(20 * node.getLevel());

                    /* entity operation header */
                    EOSH.textViewHeading.setText(entityOperationSection.getName());

                    /* the collapse and expansion of the entity operations */
                    if (node.isLeaf()) {
                        EOSH.toggleView.setVisibility(View.GONE);
                    } else {
                        EOSH.toggleView.setVisibility(View.VISIBLE);
                        if (node.isExpand()) {
                            EOSH.toggleIcon.setTypeface(null, Typeface.NORMAL);
                            EOSH.toggleIcon.setTypeface(
                                    cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
                            EOSH.toggleIcon.setText(
                                    context.getResources().getString(R.string.fa_angle_down));
                        } else {
                            EOSH.toggleIcon.setTypeface(null, Typeface.NORMAL);
                            EOSH.toggleIcon.setTypeface(
                                    cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
                            EOSH.toggleIcon.setText(
                                    context.getResources().getString(R.string.fa_angle_up));
                        }
                    }
                    EOSH.toggleView.setOnClickListener(v -> expandOrCollapse(position));

                    break;

                case ENTITY_OPS_LIST:
                    cOperationModel operationModel = (cOperationModel) treeModel.getModelObject();
                    cEntityOperationViewHolder OH = ((cEntityOperationViewHolder) viewHolder);

                    OH.setPaddingLeft(20 * node.getLevel());

                    /* check box to select the entity operations */
                    OH.checkBoxEntityOperation.setChecked(operationModel.isChecked());
                    OH.checkBoxEntityOperation.setOnClickListener(view -> {
                        boolean checked = ((CheckBox) view).isChecked();
                        operationModel.setChecked(checked);
                    });

                    /* entity operation details */
                    OH.textViewName.setText(operationModel.getName());
                    OH.textViewDescription.setText(operationModel.getDescription());

                    /* the collapse and expansion of the entity operations */
                    if (node.isLeaf()) {
                        OH.textViewDetailIcon.setVisibility(View.GONE);
                    } else {

                        OH.textViewDetailIcon.setVisibility(View.VISIBLE);
                        if (node.isExpand()) {
                            OH.textViewDetailIcon.setTypeface(null, Typeface.NORMAL);
                            OH.textViewDetailIcon.setTypeface(
                                    cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
                            OH.textViewDetailIcon.setText(
                                    context.getResources().getString(R.string.fa_minus));
                        } else {
                            OH.textViewDetailIcon.setTypeface(null, Typeface.NORMAL);
                            OH.textViewDetailIcon.setTypeface(
                                    cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
                            OH.textViewDetailIcon.setText(
                                    context.getResources().getString(R.string.fa_plus));
                        }
                    }

                    OH.textViewDetailIcon.setOnClickListener(v -> expandOrCollapse(position));

                    break;

                case OPS_STATUS_LIST:
                    cOperationStatusCollection statusCollection = (cOperationStatusCollection)
                            treeModel.getModelObject();
                    cOperationStatusViewHolder OSH = ((cOperationStatusViewHolder) viewHolder);

                    OSH.setPaddingLeft(20 * node.getLevel());

                    List<cStatusModel> statusModels = statusCollection.getStatusCollection();
                    for (cStatusModel statusModel : statusModels) {
                        switch (statusModel.getStatusServerID()) {
                            case DELETED:
                                OSH.switchDeleted.setChecked(statusModel.isChecked());
                                OSH.switchDeleted.setTag(statusModel);
                                break;
                            case BLOCKED:
                                OSH.switchBlocked.setChecked(statusModel.isChecked());
                                OSH.switchBlocked.setTag(statusModel);
                                break;
                            case ACTIVATED:
                                OSH.switchActivated.setChecked(statusModel.isChecked());
                                OSH.switchActivated.setTag(statusModel);
                                break;
                            case CANCELLED:
                                OSH.switchCancelled.setChecked(statusModel.isChecked());
                                OSH.switchCancelled.setTag(statusModel);
                                break;
                            case PENDING:
                                OSH.switchPending.setChecked(statusModel.isChecked());
                                OSH.switchPending.setTag(statusModel);
                                break;
                        }
                    }

                    OSH.switchDeleted.setOnClickListener(view -> {
                        boolean checked = ((SwitchMaterial) view).isChecked();
                        ((cStatusModel) OSH.switchDeleted.getTag()).setChecked(checked);
                    });

                    OSH.switchBlocked.setOnClickListener(view -> {
                        boolean checked = ((SwitchMaterial) view).isChecked();
                        ((cStatusModel) OSH.switchBlocked.getTag()).setChecked(checked);
                    });

                    OSH.switchActivated.setOnClickListener(view -> {
                        boolean checked = ((SwitchMaterial) view).isChecked();
                        ((cStatusModel) OSH.switchActivated.getTag()).setChecked(checked);
                    });

                    OSH.switchCancelled.setOnClickListener(view -> {
                        boolean checked = ((SwitchMaterial) view).isChecked();
                        ((cStatusModel) OSH.switchCancelled.getTag()).setChecked(checked);
                    });

                    OSH.switchPending.setOnClickListener(view -> {
                        boolean checked = ((SwitchMaterial) view).isChecked();
                        ((cStatusModel) OSH.switchPending.getTag()).setChecked(checked);
                    });

                    break;

                case UNIX_OPS_SECTION:
                    cSectionModel unixOperationSection = (cSectionModel) treeModel.getModelObject();
                    cUnixOperationSectionViewHolder UOSH = ((cUnixOperationSectionViewHolder)
                            viewHolder);

                    UOSH.setPaddingLeft(20 * node.getLevel());

                    /* unix operation header */
                    UOSH.textViewHeading.setText(unixOperationSection.getName());

                    /* the collapse and expansion of the unix operations */
                    if (node.isLeaf()) {
                        UOSH.toggleView.setVisibility(View.GONE);
                    } else {
                        UOSH.toggleView.setVisibility(View.VISIBLE);
                        if (node.isExpand()) {
                            UOSH.toggleIcon.setTypeface(null, Typeface.NORMAL);
                            UOSH.toggleIcon.setTypeface(
                                    cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
                            UOSH.toggleIcon.setText(
                                    context.getResources().getString(R.string.fa_angle_down));
                        } else {
                            UOSH.toggleIcon.setTypeface(null, Typeface.NORMAL);
                            UOSH.toggleIcon.setTypeface(
                                    cFontManager.getTypeface(context, cFontManager.FONTAWESOME));
                            UOSH.toggleIcon.setText(
                                    context.getResources().getString(R.string.fa_angle_up));
                        }
                    }
                    UOSH.toggleView.setOnClickListener(v -> expandOrCollapse(position));

                    break;

                case UNIX_OPS_LIST:
                    cUnixOperationCollection unix_ops = (cUnixOperationCollection)
                            treeModel.getModelObject();
                    cUnixOperationViewHolder UOH = ((cUnixOperationViewHolder) viewHolder);

                    List<cUnixOperationModel> models = unix_ops.getUnixOperationModels();
                    for (cUnixOperationModel unixOperationModel : models) {
                        switch (unixOperationModel.getOperationServerID()) {
                            case OWNER_READ:
                                UOH.checkBoxOwnerRead.setChecked(
                                        unixOperationModel.isChecked());
                                UOH.checkBoxOwnerRead.setTag(unixOperationModel);
                                break;
                            case OWNER_UPDATE:
                                UOH.checkBoxOwnerUpdate.setChecked(
                                        unixOperationModel.isChecked());
                                UOH.checkBoxOwnerUpdate.setTag(unixOperationModel);
                                break;
                            case OWNER_DELETE:
                                UOH.checkBoxOwnerDelete.setChecked(
                                        unixOperationModel.isChecked());
                                UOH.checkBoxOwnerDelete.setTag(unixOperationModel);
                                break;
                            case PRIMARY_READ:
                                UOH.checkBoxPrimaryRead.setChecked(
                                        unixOperationModel.isChecked());
                                UOH.checkBoxPrimaryRead.setTag(unixOperationModel);
                                break;
                            case PRIMARY_UPDATE:
                                UOH.checkBoxPrimaryUpdate.setChecked(
                                        unixOperationModel.isChecked());
                                UOH.checkBoxPrimaryUpdate.setTag(unixOperationModel);
                                break;
                            case PRIMARY_DELETE:
                                UOH.checkBoxPrimaryDelete.setChecked(
                                        unixOperationModel.isChecked());
                                UOH.checkBoxPrimaryDelete.setTag(unixOperationModel);
                                break;
                            case SECONDARY_READ:
                                UOH.checkBoxSecondaryRead.setChecked(
                                        unixOperationModel.isChecked());
                                UOH.checkBoxSecondaryRead.setTag(unixOperationModel);
                                break;
                            case SECONDARY_UPDATE:
                                UOH.checkBoxSecondaryUpdate.setChecked(
                                        unixOperationModel.isChecked());
                                UOH.checkBoxSecondaryUpdate.setTag(unixOperationModel);
                                break;
                            case SECONDARY_DELETE:
                                UOH.checkBoxSecondaryDelete.setChecked(
                                        unixOperationModel.isChecked());
                                UOH.checkBoxSecondaryDelete.setTag(unixOperationModel);
                                break;
                            case ORGANIZATION_READ:
                                UOH.checkBoxOrganizationRead.setChecked(
                                        unixOperationModel.isChecked());
                                UOH.checkBoxOrganizationRead.setTag(unixOperationModel);
                                break;
                            case ORGANIZATION_UPDATE:
                                UOH.checkBoxOrganizationUpdate.setChecked(
                                        unixOperationModel.isChecked());
                                UOH.checkBoxOrganizationUpdate.setTag(unixOperationModel);
                                break;
                            case ORGANIZATION_DELETE:
                                UOH.checkBoxOrganizationDelete.setChecked(
                                        unixOperationModel.isChecked());
                                UOH.checkBoxOrganizationDelete.setTag(unixOperationModel);
                                break;
                        }

                        /* owner permissions */
                        UOH.checkBoxOwnerRead.setOnClickListener(view -> {
                            boolean checked = ((CheckBox) view).isChecked();
                            ((cUnixOperationModel) UOH.checkBoxOwnerRead.getTag()).
                                    setChecked(checked);
                        });
                        UOH.checkBoxOwnerUpdate.setOnClickListener(view -> {
                            boolean checked = ((CheckBox) view).isChecked();
                            ((cUnixOperationModel) UOH.checkBoxOwnerUpdate.getTag()).
                                    setChecked(checked);
                        });
                        UOH.checkBoxOwnerDelete.setOnClickListener(view -> {
                            boolean checked = ((CheckBox) view).isChecked();
                            ((cUnixOperationModel) UOH.checkBoxOwnerDelete.getTag()).
                                    setChecked(checked);
                        });

                        /* primary permissions */
                        UOH.checkBoxPrimaryRead.setOnClickListener(view -> {
                            boolean checked = ((CheckBox) view).isChecked();
                            ((cUnixOperationModel) UOH.checkBoxPrimaryRead.getTag()).
                                    setChecked(checked);
                        });
                        UOH.checkBoxPrimaryUpdate.setOnClickListener(view -> {
                            boolean checked = ((CheckBox) view).isChecked();
                            ((cUnixOperationModel) UOH.checkBoxPrimaryUpdate.getTag()).
                                    setChecked(checked);
                        });
                        UOH.checkBoxPrimaryDelete.setOnClickListener(view -> {
                            boolean checked = ((CheckBox) view).isChecked();
                            ((cUnixOperationModel) UOH.checkBoxPrimaryDelete.getTag()).
                                    setChecked(checked);
                        });

                        /* secondary permissions */
                        UOH.checkBoxSecondaryRead.setOnClickListener(view -> {
                            boolean checked = ((CheckBox) view).isChecked();
                            ((cUnixOperationModel) UOH.checkBoxSecondaryRead.getTag()).
                                    setChecked(checked);
                        });
                        UOH.checkBoxSecondaryUpdate.setOnClickListener(view -> {
                            boolean checked = ((CheckBox) view).isChecked();
                            ((cUnixOperationModel) UOH.checkBoxSecondaryUpdate.getTag()).
                                    setChecked(checked);
                        });
                        UOH.checkBoxSecondaryDelete.setOnClickListener(view -> {
                            boolean checked = ((CheckBox) view).isChecked();
                            ((cUnixOperationModel) UOH.checkBoxSecondaryDelete.getTag()).
                                    setChecked(checked);
                        });

                        /* organization permissions */
                        UOH.checkBoxOrganizationRead.setOnClickListener(view -> {
                            boolean checked = ((CheckBox) view).isChecked();
                            ((cUnixOperationModel) UOH.checkBoxOrganizationRead.getTag()).
                                    setChecked(checked);
                        });
                        UOH.checkBoxOrganizationUpdate.setOnClickListener(view -> {
                            boolean checked = ((CheckBox) view).isChecked();
                            ((cUnixOperationModel) UOH.checkBoxOrganizationUpdate.getTag()).
                                    setChecked(checked);
                        });
                        UOH.checkBoxOrganizationDelete.setOnClickListener(view -> {
                            boolean checked = ((CheckBox) view).isChecked();
                            ((cUnixOperationModel) UOH.checkBoxOrganizationDelete.getTag()).
                                    setChecked(checked);
                        });

                    }

                    UOH.setPaddingLeft(20 * node.getLevel());

                    break;
            }
        }
    }

    @Override
    public void onClickUpdatePermission(List<cNode> nodes) {
        this.iPermissionPresenterView.onClickUpdateRolePermission(nodes);
    }

    @Override
    public void onClickDeletePermission(String permissionServerID) {

    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {

                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    filteredModuleTree = getTreeModel();
                } else {

                    ArrayList<cTreeModel> filteredList = new ArrayList<>();
                    for (cTreeModel treeModel : getTreeModel()) {
                        if (((cPermissionModel) treeModel.getModelObject()).getName().toLowerCase().
                                contains(charString.toLowerCase())) {
                            filteredList.add(treeModel);
                        }
                    }

                    filteredModuleTree = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.count = filteredModuleTree.size();
                filterResults.values = filteredModuleTree;

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                filteredModuleTree = (ArrayList<cTreeModel>) filterResults.values;
                try {
                    Gson gson = new Gson();
                    Log.d(TAG, "===============>>>> " + gson.toJson(filteredModuleTree));
                    notifyTreeModelChanged(filteredModuleTree);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    public static class cPermissionViewHolder extends RecyclerView.ViewHolder {

        private final TextView textViewName;
        private final TextView textViewDescription;
        private final TextView textViewDetailIcon;
        private final TextView textViewUpdateIcon;
        private final TextView textViewDeleteIcon;

        private final View viewHolder;

        private final iViewPermissionListener viewPermissionListener;

        private cPermissionViewHolder(final View viewHolder, iViewPermissionListener listener) {
            super(viewHolder);
            this.viewHolder = viewHolder;
            this.viewPermissionListener = listener;

            this.textViewName = viewHolder.findViewById(R.id.textViewName);
            this.textViewDescription = viewHolder.findViewById(R.id.textViewDescription);
            this.textViewDetailIcon = viewHolder.findViewById(R.id.textViewDetailIcon);
            this.textViewUpdateIcon = viewHolder.findViewById(R.id.textViewUpdateIcon);
            this.textViewDeleteIcon = viewHolder.findViewById(R.id.textViewDeleteIcon);
        }

        public void setPaddingLeft(int paddingLeft) {
            viewHolder.setPadding(paddingLeft, 0,
                    0, 0);
        }
    }

    public static class cMenuItemViewHolder extends RecyclerView.ViewHolder {

        private final CheckBox checkBoxMenu;
        private final TextView textViewName;
        private final TextView textViewDescription;
        private final TextView textViewDetailIcon;

        private final View viewHolder;

        private cMenuItemViewHolder(final View viewHolder) {
            super(viewHolder);
            this.viewHolder = viewHolder;

            this.checkBoxMenu = viewHolder.findViewById(R.id.checkBoxMenu);
            this.textViewName = viewHolder.findViewById(R.id.textViewName);
            this.textViewDescription = viewHolder.findViewById(R.id.textViewDescription);
            this.textViewDetailIcon = viewHolder.findViewById(R.id.textViewDetailIcon);
        }

        public void setPaddingLeft(int paddingLeft) {
            viewHolder.setPadding(paddingLeft, 0,
                    0, 0);
        }
    }

    public static class cSubMenuItemViewHolder extends RecyclerView.ViewHolder {

        private final AppCompatCheckBox checkBoxMenu;
        private final TextView textViewName;
        private final TextView textViewDescription;
        private final TextView textViewDetailIcon;

        private final View viewHolder;

        private cSubMenuItemViewHolder(final View viewHolder) {
            super(viewHolder);
            this.viewHolder = viewHolder;

            this.checkBoxMenu = viewHolder.findViewById(R.id.checkBoxMenu);
            this.textViewName = viewHolder.findViewById(R.id.textViewName);
            this.textViewDescription = viewHolder.findViewById(R.id.textViewDescription);
            this.textViewDetailIcon = viewHolder.findViewById(R.id.textViewDetailIcon);
        }

        public void setPaddingLeft(int paddingLeft) {
            viewHolder.setPadding(paddingLeft, 0,
                    0, 0);
        }
    }

    public static class cMenuItemSectionViewHolder extends RecyclerView.ViewHolder {

        private final LinearLayout toggleView;
        private final TextView textViewHeading;
        private final TextView toggleIcon;

        private final View viewHolder;

        private cMenuItemSectionViewHolder(final View viewHolder) {
            super(viewHolder);
            this.viewHolder = viewHolder;

            this.toggleView = viewHolder.findViewById(R.id.toggleView);
            this.textViewHeading = viewHolder.findViewById(R.id.textViewHeading);
            this.toggleIcon = viewHolder.findViewById(R.id.toggleIcon);
        }

        public void setPaddingLeft(int paddingLeft) {
            viewHolder.setPadding(paddingLeft, 0,
                    0, 0);
        }
    }

    public static class cEntityModuleSectionViewHolder extends RecyclerView.ViewHolder {

        private final LinearLayout toggleView;
        private final TextView textViewHeading;
        private final TextView toggleIcon;

        private final View viewHolder;

        private cEntityModuleSectionViewHolder(final View viewHolder) {
            super(viewHolder);
            this.viewHolder = viewHolder;

            this.toggleView = viewHolder.findViewById(R.id.toggleView);
            this.textViewHeading = viewHolder.findViewById(R.id.textViewHeading);
            this.toggleIcon = viewHolder.findViewById(R.id.toggleIcon);
        }

        public void setPaddingLeft(int paddingLeft) {
            viewHolder.setPadding(paddingLeft, 0,
                    0, 0);
        }
    }


    public static class cEntityViewHolder extends RecyclerView.ViewHolder {

        private final AppCompatCheckBox checkBoxEntity;
        private final TextView textViewName;
        private final TextView textViewDescription;
        private final TextView textViewDetailIcon;

        private final View viewHolder;

        private cEntityViewHolder(final View viewHolder) {
            super(viewHolder);
            this.viewHolder = viewHolder;

            this.checkBoxEntity = viewHolder.findViewById(R.id.checkBoxMenu);
            this.textViewName = viewHolder.findViewById(R.id.textViewName);
            this.textViewDescription = viewHolder.findViewById(R.id.textViewDescription);
            this.textViewDetailIcon = viewHolder.findViewById(R.id.textViewDetailIcon);
        }

        public void setPaddingLeft(int paddingLeft) {
            viewHolder.setPadding(paddingLeft, 0,
                    0, 0);
        }
    }

    public static class cEntityOperationSectionViewHolder extends RecyclerView.ViewHolder {

        private final LinearLayout toggleView;
        private final TextView textViewHeading;
        private final TextView toggleIcon;

        private final View viewHolder;

        private cEntityOperationSectionViewHolder(final View viewHolder) {
            super(viewHolder);
            this.viewHolder = viewHolder;
            this.toggleView = viewHolder.findViewById(R.id.toggleView);
            this.textViewHeading = viewHolder.findViewById(R.id.textViewHeading);
            this.toggleIcon = viewHolder.findViewById(R.id.toggleIcon);
        }

        public void setPaddingLeft(int paddingLeft) {
            viewHolder.setPadding(paddingLeft, 0,
                    0, 0);
        }
    }

    public static class cEntityOperationViewHolder extends RecyclerView.ViewHolder {

        private final AppCompatCheckBox checkBoxEntityOperation;
        private final TextView textViewName;
        private final TextView textViewDescription;
        private final TextView textViewDetailIcon;

        private final View viewHolder;

        private cEntityOperationViewHolder(final View viewHolder) {
            super(viewHolder);
            this.viewHolder = viewHolder;

            this.checkBoxEntityOperation = viewHolder.findViewById(R.id.checkBoxMenu);
            this.textViewName = viewHolder.findViewById(R.id.textViewName);
            this.textViewDescription = viewHolder.findViewById(R.id.textViewDescription);
            this.textViewDetailIcon = viewHolder.findViewById(R.id.textViewDetailIcon);
        }

        public void setPaddingLeft(int paddingLeft) {
            viewHolder.setPadding(paddingLeft, 0,
                    0, 0);
        }
    }

    public static class cOperationStatusViewHolder extends RecyclerView.ViewHolder {

        private final SwitchMaterial switchDeleted;
        private final SwitchMaterial switchBlocked;
        private final SwitchMaterial switchActivated;
        private final SwitchMaterial switchCancelled;
        private final SwitchMaterial switchPending;

        private final View viewHolder;

        private cOperationStatusViewHolder(final View viewHolder) {
            super(viewHolder);
            this.viewHolder = viewHolder;

            this.switchDeleted = viewHolder.findViewById(R.id.switchDeleted);
            this.switchBlocked = viewHolder.findViewById(R.id.switchBlocked);
            this.switchActivated = viewHolder.findViewById(R.id.switchActivated);
            this.switchCancelled = viewHolder.findViewById(R.id.switchCancelled);
            this.switchPending = viewHolder.findViewById(R.id.switchPending);
        }

        public void setPaddingLeft(int paddingLeft) {
            viewHolder.setPadding(paddingLeft, 0,
                    0, 0);
        }
    }

    public static class cUnixOperationSectionViewHolder extends RecyclerView.ViewHolder {

        private final LinearLayout toggleView;
        private final TextView textViewHeading;
        private final TextView toggleIcon;

        private final View viewHolder;

        private cUnixOperationSectionViewHolder(final View viewHolder) {
            super(viewHolder);
            this.viewHolder = viewHolder;
            this.toggleView = viewHolder.findViewById(R.id.toggleView);
            this.textViewHeading = viewHolder.findViewById(R.id.textViewHeading);
            this.toggleIcon = viewHolder.findViewById(R.id.toggleIcon);
        }

        public void setPaddingLeft(int paddingLeft) {
            viewHolder.setPadding(paddingLeft, 0,
                    0, 0);
        }
    }

    public static class cUnixOperationViewHolder extends RecyclerView.ViewHolder {

        private final AppCompatCheckBox checkBoxOwnerRead;
        private final AppCompatCheckBox checkBoxOwnerUpdate;
        private final AppCompatCheckBox checkBoxOwnerDelete;

        private final AppCompatCheckBox checkBoxPrimaryRead;
        private final AppCompatCheckBox checkBoxPrimaryUpdate;
        private final AppCompatCheckBox checkBoxPrimaryDelete;

        private final AppCompatCheckBox checkBoxSecondaryRead;
        private final AppCompatCheckBox checkBoxSecondaryUpdate;
        private final AppCompatCheckBox checkBoxSecondaryDelete;

        private final AppCompatCheckBox checkBoxOrganizationRead;
        private final AppCompatCheckBox checkBoxOrganizationUpdate;
        private final AppCompatCheckBox checkBoxOrganizationDelete;

        private final View viewHolder;

        private cUnixOperationViewHolder(final View viewHolder) {
            super(viewHolder);
            this.viewHolder = viewHolder;

            this.checkBoxOwnerRead = viewHolder.findViewById(R.id.checkBoxOwnerRead);
            this.checkBoxOwnerUpdate = viewHolder.findViewById(R.id.checkBoxOwnerUpdate);
            this.checkBoxOwnerDelete = viewHolder.findViewById(R.id.checkBoxOwnerDelete);

            this.checkBoxPrimaryRead = viewHolder.findViewById(R.id.checkBoxPrimaryRead);
            this.checkBoxPrimaryUpdate = viewHolder.findViewById(R.id.checkBoxPrimaryUpdate);
            this.checkBoxPrimaryDelete = viewHolder.findViewById(R.id.checkBoxPrimaryDelete);

            this.checkBoxSecondaryRead = viewHolder.findViewById(R.id.checkBoxSecondaryRead);
            this.checkBoxSecondaryUpdate = viewHolder.findViewById(R.id.checkBoxSecondaryUpdate);
            this.checkBoxSecondaryDelete = viewHolder.findViewById(R.id.checkBoxSecondaryDelete);

            this.checkBoxOrganizationRead = viewHolder.findViewById(R.id.checkBoxOrganizationRead);
            this.checkBoxOrganizationUpdate = viewHolder.findViewById(R.id.checkBoxOrganizationUpdate);
            this.checkBoxOrganizationDelete = viewHolder.findViewById(R.id.checkBoxOrganizationDelete);
        }

        public void setPaddingLeft(int paddingLeft) {
            viewHolder.setPadding(paddingLeft,
                    0, 0, 0);
        }
    }
}