package com.me.mseotsanyana.mande.DAL.ìmpl.firestore.session;

import android.content.Context;
import android.util.Log;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.gson.Gson;
import com.me.mseotsanyana.mande.BLL.model.session.cEntityModel;
import com.me.mseotsanyana.mande.BLL.model.session.cMenuModel;
import com.me.mseotsanyana.mande.BLL.model.session.cOperationModel;
import com.me.mseotsanyana.mande.BLL.model.session.cOperationStatusCollection;
import com.me.mseotsanyana.mande.BLL.model.session.cPermissionModel;
import com.me.mseotsanyana.mande.BLL.model.session.cRoleModel;
import com.me.mseotsanyana.mande.BLL.model.session.cSectionModel;
import com.me.mseotsanyana.mande.BLL.model.session.cStatusModel;
import com.me.mseotsanyana.mande.BLL.model.session.cUnixOperationCollection;
import com.me.mseotsanyana.mande.BLL.model.session.cUnixOperationModel;
import com.me.mseotsanyana.mande.BLL.repository.session.iModuleRepository;
import com.me.mseotsanyana.mande.DAL.storage.database.cRealtimeHelper;
import com.me.mseotsanyana.mande.DAL.ìmpl.cDatabaseUtils;
import com.me.mseotsanyana.treeadapterlibrary.cNode;
import com.me.mseotsanyana.treeadapterlibrary.cTreeModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class cModuleFirestoreRepositoryImpl implements iModuleRepository {
    //private static final String TAG = cModuleFirestoreRepositoryImpl.class.getSimpleName();

    private static final int MENUITEM_SECTION     = 1;
    private static final int ENTITYMODULE_SECTION = 4;
    private static final int ENTITY_OPS_SECTION   = 6;
    private static final int UNIX_OPS_SECTION     = 9;

    private final Context context;
    private final FirebaseFirestore db;

    public cModuleFirestoreRepositoryImpl(Context context) {
        this.context = context;
        this.db = FirebaseFirestore.getInstance();
    }

    /* ###################################### READ ACTIONS ###################################### */

    @Override
    public void readModulePermissions(String organizationServerID, String userServerID,
                                      int primaryTeamBIT, List<Integer> secondaryTeamBITS,
                                      List<Integer> statusBITS,
                                      iReadModulePermissionsCallback callback) {

        CollectionReference coRoleRef = db.collection(cRealtimeHelper.KEY_ROLES);

        Query roleQuery = coRoleRef
                .whereEqualTo("organizationOwnerID", organizationServerID)
                .whereIn("statusBIT", statusBITS);

        roleQuery.get()
                .addOnCompleteListener(task -> {
                    //List<String> role_ids = new ArrayList<>();
                    Map<String, cRoleModel> roleModelMap = new HashMap<>();
                    cRoleModel roleModel;
                    for (DocumentSnapshot role_doc : Objects.requireNonNull(task.getResult())) {
                        roleModel = role_doc.toObject(cRoleModel.class);

                        if (roleModel != null) {
                            roleModel.setRoleServerID(role_doc.getId());

                            cDatabaseUtils.cUnixPerm perm = new cDatabaseUtils.cUnixPerm();
                            perm.setUserOwnerID(roleModel.getUserOwnerID());
                            perm.setTeamOwnerBIT(roleModel.getTeamOwnerBIT());
                            perm.setUnixpermBITS(roleModel.getUnixpermBITS());

                            if (cDatabaseUtils.isPermitted(perm, userServerID, primaryTeamBIT,
                                    secondaryTeamBITS)) {
                                //role_ids.add(roleModel.getRoleServerID());
                                roleModelMap.put(roleModel.getRoleServerID(), roleModel);
                            }
                        }
                    }

                    /* filtering the permissions by roles */
                    filterModulePermissions(roleModelMap, callback);
                })
                .addOnFailureListener(e ->
                        callback.onReadModulePermissionsFailed("Failed to read roles"));

    }

    private void filterModulePermissions(Map<String, cRoleModel> roleModelMap,
                                         iReadModulePermissionsCallback callback) {
        CollectionReference coPermissionRef = db.collection(cRealtimeHelper.KEY_ROLE_PERMISSIONS);
        List<String> role_ids = new ArrayList<>(roleModelMap.keySet());
        Query permissionQuery = coPermissionRef.whereIn(FieldPath.documentId(), role_ids);

        permissionQuery.get()
                .addOnCompleteListener(task -> {
                    cRoleModel roleModel;
                    cPermissionModel permissionModel;
                    List<cTreeModel> treeModels = new ArrayList<>();
                    for (DocumentSnapshot perm_doc : Objects.requireNonNull(task.getResult())) {

                        permissionModel = perm_doc.toObject(cPermissionModel.class);

                        if (permissionModel != null) {
                            permissionModel.setRoleServerID(perm_doc.getId());
                            for (Map.Entry<String, cRoleModel> entry : roleModelMap.entrySet()) {
                                //Log.d(TAG, " Role Model = " + entry.getKey() + " = " +
                                // permissionModel.getRoleServerID());
                                if (entry.getKey().equals(permissionModel.getRoleServerID())) {
                                    // rolePermissionModels.put(entry.getValue(), permissionModel);
                                    // FIXME: what about if the user has list of multiple roles
                                    //  and permissions
                                    roleModel = entry.getValue();
                                    roleModel.setRoleServerID(entry.getKey());
                                    permissionModel.setRoleServerID(perm_doc.getId());
                                    permissionModel.setName(roleModel.getName());

                                    //Log.d(TAG, "RESULT = " + gson.toJson(permissionModel));

                                    // add maps of menu items and entities not in the db
                                    // return all menus
                                    treeModels = cDatabaseUtils.buildModulePermissions(context,
                                            permissionModel);

                                    break;
                                }
                            }
                        }
                    }

                    /* call back on list of user permissions by roles */
                    callback.onReadModulePermissionsSucceeded(treeModels);
                })
                .addOnFailureListener(e ->
                        callback.onReadModulePermissionsFailed("Failed to read permissions"));
    }

    /* ##################################### UPDATE ACTIONS ##################################### */

    /**
     * update the role permission tree
     *
     * @param organizationServerID organization identification
     * @param userServerID user identification
     * @param primaryTeamBIT primary team bit
     * @param secondaryTeamBITS secondary team bits
     * @param statusBITS status bits
     * @param nodes permission tree nodes
     * @param callback call back
     */
    @Override
    public void updatePermissions(String organizationServerID, String userServerID,
                                  int primaryTeamBIT, List<Integer> secondaryTeamBITS,
                                  List<Integer> statusBITS, List<cNode> nodes,
                                  iUpdatePermissionsCallback callback) {

        /* find the root of the permission tree */
        cNode root = null;
        for (cNode node : nodes) {
            if (node.isRoot()) {
                root = node;
                break;
            }
        }

        /* use the root to build the tree of the selected permissions */
        cPermissionModel permissionModel = null;
        if (root != null) {
            permissionModel = updatePermissionTree(root);
        }

        /* update permissions in the cloud */
        if (permissionModel != null) {
            CollectionReference coRolePermsRef = db.collection(
                    cRealtimeHelper.KEY_ROLE_PERMISSIONS);
            coRolePermsRef.document(permissionModel.getRoleServerID()).set(permissionModel)
                    .addOnSuccessListener(unused -> callback.onUpdatePermissionsSucceeded(
                            "Permissions successfully updated"))
                    .addOnFailureListener(e -> callback.onReadModulePermissionsFailed(
                            "Error! Failed to update permissions "));

        }
    }

    /**
     * update the role permission which contains menu items and entity modules
     *
     * @param node root of the permission tree
     * @return root node
     */
    private cPermissionModel updatePermissionTree(cNode node) {
        cPermissionModel permissionModel = new cPermissionModel();
        cTreeModel permTreeModel = (cTreeModel) node.getObj();
        cPermissionModel perm = (cPermissionModel) permTreeModel.getModelObject();

        /* update the permission model */
        permissionModel.setRoleServerID(perm.getRoleServerID());
        permissionModel.setName(perm.getName());
        permissionModel.setDescription(perm.getDescription());

        Map<String, List<Integer>> menuitems = new HashMap<>();
        Map<String, List<cEntityModel>> entitymodules = new HashMap<>();
        for (cNode module : node.getChildren()) {
            cTreeModel permSectionTreeModel = (cTreeModel) module.getObj();
            cSectionModel sectionModel = (cSectionModel) permSectionTreeModel.getModelObject();

            /* MENU ITEM SECTION */

            if(permSectionTreeModel.getType() == MENUITEM_SECTION){

                for(cNode mainmenu: module.getChildren()) {

                    cTreeModel mainmenuTreeModel = (cTreeModel) mainmenu.getObj();
                    cMenuModel menuModel = (cMenuModel) mainmenuTreeModel.getModelObject();

                    List<Integer> submenu_ids = new ArrayList<>();
                    if (menuModel.isChecked()){
                        for (cNode submenu: mainmenu.getChildren()){
                            cTreeModel submenuTreeModel = (cTreeModel) submenu.getObj();
                            cMenuModel submenuModel = (cMenuModel)
                                    submenuTreeModel.getModelObject();
                            if(submenuModel.isChecked()) {
                                submenu_ids.add(submenuModel.getMenuServerID());
                            }
                        }
                        menuitems.put(String.valueOf(menuModel.getMenuServerID()), submenu_ids);
                    }
                }
            }

            /* ENTITY MODULE SECTION */

            if (permSectionTreeModel.getType() == ENTITYMODULE_SECTION ) {

                List<cEntityModel> entityModels = new ArrayList<>();
                for (cNode entity : module.getChildren()) {
                    cTreeModel entityTreeModel = (cTreeModel) entity.getObj();
                    cEntityModel entityModel = (cEntityModel) entityTreeModel.getModelObject();

                    /* is the entity selected? */
                    if (entityModel.isChecked()) {
                        Map<String, List<Integer>> entityperms = new HashMap<>();
                        List<Integer> unixperms = new ArrayList<>();
                        for (cNode section : entity.getChildren()) {
                            cTreeModel sectionTreeModel = (cTreeModel) section.getObj();

                            /* ENTITY OPERATIONS SECTION */

                            if (sectionTreeModel.getType() == ENTITY_OPS_SECTION) {

                                for (cNode operation : section.getChildren()) {
                                    cTreeModel operationTreeModel = (cTreeModel) operation.getObj();
                                    cOperationModel operationModel = (cOperationModel)
                                            operationTreeModel.getModelObject();

                                    /* is the entity operation selected? */
                                    if (operationModel.isChecked()) {

                                        for (cNode entity_op : operation.getChildren()) {
                                            cTreeModel entityOpTreeModel;
                                            entityOpTreeModel = (cTreeModel) entity_op.getObj();
                                            cOperationStatusCollection collection;
                                            collection = (cOperationStatusCollection)
                                                    entityOpTreeModel.getModelObject();

                                            List<Integer> statuses = new ArrayList<>();
                                            for (cStatusModel statusModel : collection.
                                                    getStatusCollection()) {
                                                /* are the operation status selected? */
                                                if (statusModel.isChecked()) {
                                                    statuses.add(Integer.parseInt(
                                                            statusModel.getStatusServerID()));
                                                }
                                            }

                                            entityperms.put(operationModel.getOperationServerID(),
                                                    statuses);
                                        }
                                    }
                                }
                            }

                            /* UNIX OPERATIONS SECTION */

                            if (sectionTreeModel.getType() == UNIX_OPS_SECTION) {
                                for (cNode unix_ops : section.getChildren()) {
                                    cTreeModel unixTreeModel = (cTreeModel) unix_ops.getObj();
                                    cUnixOperationCollection collection = (cUnixOperationCollection)
                                            unixTreeModel.getModelObject();

                                    for (cUnixOperationModel unix : collection.getUnixOperationModels()) {
                                        /* are unix permissions selected? */
                                        if (unix.isChecked()) {
                                            unixperms.add(Integer.parseInt(unix.getOperationServerID()));
                                        }
                                    }
                                }
                            }

                        }
                        // create entity model
                        entityModels.add(new cEntityModel(entityModel.getEntityServerID(),
                                entityperms, unixperms));
                    }
                }

                entitymodules.put(sectionModel.getModuleServerID(), entityModels);
            }
        }

        // update the menu items
        permissionModel.setMenuitems(menuitems);
        // update the entity modules
        permissionModel.setEntitymodules(entitymodules);

        return permissionModel;
    }
}