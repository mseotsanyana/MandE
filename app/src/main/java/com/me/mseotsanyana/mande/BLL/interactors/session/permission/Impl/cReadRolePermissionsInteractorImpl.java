package com.me.mseotsanyana.mande.BLL.interactors.session.permission.Impl;

import android.util.Log;
import android.util.Pair;

import com.me.mseotsanyana.mande.BLL.executor.iExecutor;
import com.me.mseotsanyana.mande.BLL.executor.iMainThread;
import com.me.mseotsanyana.mande.BLL.interactors.base.cAbstractInteractor;
import com.me.mseotsanyana.mande.BLL.interactors.session.permission.iReadRolePermissionsInteractor;
import com.me.mseotsanyana.mande.BLL.model.session.cEntityModel;
import com.me.mseotsanyana.mande.BLL.model.session.cMenuModel;
import com.me.mseotsanyana.mande.BLL.model.session.cModuleModel;
import com.me.mseotsanyana.mande.BLL.model.session.cOperationModel;
import com.me.mseotsanyana.mande.BLL.model.session.cPermissionModel;
import com.me.mseotsanyana.mande.BLL.model.session.cRoleModel;
import com.me.mseotsanyana.mande.BLL.model.session.cStatusModel;
import com.me.mseotsanyana.mande.BLL.repository.session.iPermissionRepository;
import com.me.mseotsanyana.mande.BLL.repository.session.iSharedPreferenceRepository;
import com.me.mseotsanyana.mande.DAL.storage.preference.cSharedPreference;
import com.me.mseotsanyana.treeadapterlibrary.cTreeModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class cReadRolePermissionsInteractorImpl extends cAbstractInteractor
        implements iReadRolePermissionsInteractor {
    private static final String TAG = cReadRolePermissionsInteractorImpl.class.getSimpleName();

    private final Callback callback;
    private final iPermissionRepository permissionRepository;

    // permission data
    private final String userServerID;
    private final String organizationServerID;
    private final int primaryTeamBIT;
    private final List<Integer> secondaryTeamBITS;
    private final List<Integer> statusBITS;

    private final int entityBITS;
    private final int entitypermBITS;

    public cReadRolePermissionsInteractorImpl(iExecutor threadExecutor, iMainThread mainThread,
                                              iSharedPreferenceRepository sharedPreferenceRepository,
                                              iPermissionRepository permissionRepository,
                                              Callback callback) {
        super(threadExecutor, mainThread);

        if (sharedPreferenceRepository == null || permissionRepository == null ||
                callback == null) {
            throw new IllegalArgumentException("Arguments can not be null!");
        }

        this.callback = callback;
        this.permissionRepository = permissionRepository;

        // load user shared preferences
        this.userServerID = sharedPreferenceRepository.loadUserID();
        this.organizationServerID = sharedPreferenceRepository.loadOrganizationID();
        this.primaryTeamBIT = sharedPreferenceRepository.loadPrimaryTeamBIT();
        this.secondaryTeamBITS = sharedPreferenceRepository.loadSecondaryTeams();

        // load entity shared preferences
        this.entityBITS = sharedPreferenceRepository.loadEntityBITS(
                cSharedPreference.SESSION_MODULE);
        this.entitypermBITS = sharedPreferenceRepository.loadEntityPermissionBITS(
                cSharedPreference.SESSION_MODULE, cSharedPreference.USERACCOUNT);
        this.statusBITS = sharedPreferenceRepository.loadOperationStatuses(
                cSharedPreference.SESSION_MODULE, cSharedPreference.USERACCOUNT,
                cSharedPreference.READ);

        Log.d(TAG, " \n USER ID = " + this.userServerID +
                " \n ORGANIZATION ID = " + this.organizationServerID +
                " \n PRIMARY TEAM BIT = " + this.primaryTeamBIT +
                " \n SECONDARY TEAM BITS = " + this.secondaryTeamBITS +
                " \n ENTITY PERMISSION BITS = " + this.entitypermBITS +
                " \n OPERATION STATUSES = " + this.statusBITS);
    }

    /* */
    private void notifyError(String msg) {
        mainThread.post(() -> callback.onReadRolePermissionsFailed(msg));
    }

    /* */
    private void postMessage(List<cTreeModel> treeModels) {
        mainThread.post(() -> callback.onReadRolePermissionsRetrieved(treeModels));
    }

    private void buildAndReadTree(Map<cRoleModel, cPermissionModel> rolePermissionModels){

        List<cTreeModel> treeModels = new ArrayList<>();
        int parentIndex = 0, childIndex;
        for(Map.Entry<cRoleModel, cPermissionModel> entry: rolePermissionModels.entrySet()){
            cRoleModel roleModel = entry.getKey();
            cPermissionModel permissionModel = entry.getValue();

            /* LEVEL 0: add root role node */
            treeModels.add(new cTreeModel(new cTreeModel(parentIndex, -1, 0,
                    roleModel)));
            /* LEVEL 1: add child permission node to the parent role node */
            childIndex = parentIndex + 1;
            treeModels.add(new cTreeModel(new cTreeModel(childIndex, parentIndex, 1,
                    permissionModel)));
            parentIndex = childIndex;

            // entity permissions
            for(Map.Entry<String, List<cEntityModel>> moduleEntry: permissionModel.
                    getEntitymodules().entrySet()){
                // build module node
                String moduleKey =  moduleEntry.getKey();
                cModuleModel moduleModel = new cModuleModel();
                String moduleName = null;//getModuleFromJson(moduleKey);
                moduleModel.setModuleServerID(moduleKey);
                moduleModel.setName(moduleName);

                /* LEVEL 2: add child module node to the parent permission node */
                childIndex = childIndex + 1;
                treeModels.add(new cTreeModel(childIndex, parentIndex, 2, moduleModel));
                parentIndex = childIndex;

                // entity modules
                List<cEntityModel> entityModels = moduleEntry.getValue();
                for(cEntityModel entityModel: entityModels){
                    // get pair of name and description through entity identification
                    Pair<String, String> pair = null;//getNameAndDescription(entityModel.);
                    entityModel.setName(pair.first);
                    entityModel.setDescription(pair.second);

                    /* LEVEL 3: add child entity node to the parent module node */
                    childIndex = childIndex + 1;
                    treeModels.add(new cTreeModel(childIndex, parentIndex, 3, entityModel));
                    parentIndex = childIndex;

                    // entity operations
                    for(Map.Entry<String, List<Integer>> entityEntry : entityModel.
                            getEntityperms().entrySet()){
                        String operationKey =  entityEntry.getKey();
                        Pair<String, String> ops_pair = null;//getNameAndDescription(operationKey);

                        cOperationModel operationModel = new cOperationModel();
                        operationModel.setName(ops_pair.first);
                        operationModel.setDescription(ops_pair.second);

                        /* LEVEL 4: add child operation node to the parent entity node */
                        childIndex = childIndex + 1;
                        treeModels.add(new cTreeModel(childIndex, parentIndex, 4, moduleModel));
                        parentIndex = childIndex;

                        // operation status
                        for(Integer status_id: entityEntry.getValue()){
                            Pair<String, String> status_pair = null;//getNameAndDescription(operationKey);

                            cStatusModel statusModel = new cStatusModel();
                            statusModel.setName(status_pair.first);
                            statusModel.setDescription(status_pair.second);

                            /* LEVEL 5: add child status node to the parent operation node */
                            childIndex = childIndex + 1;
                            treeModels.add(new cTreeModel(childIndex, parentIndex, 5, moduleModel));
                            parentIndex = childIndex;
                        }
                    }

                    // unix permissions
                    List<Integer> unixperms = entityModel.getUnixperms();


                }
            }

            // menu permissions
            for(Map.Entry<String, List<Integer>> moduleEntry: permissionModel.
                    getMenuitems().entrySet()){

                treeModels.add(new cTreeModel(parentIndex, -1, 0, permissionModel));

                // build menu nodes and add them to permission node
                List<Integer> menu_ids = moduleEntry.getValue();
                childIndex = parentIndex;
                for(Integer menu_id: menu_ids){
                    cMenuModel menuModel = new cMenuModel();
                    // get pair of name and description through module id
                    Pair<String, String> pair = null;//getNameAndDescription(menu_id);
                    menuModel.setName(pair.first);
                    menuModel.setDescription(pair.second);

                    childIndex = childIndex + 1;
                    treeModels.add(new cTreeModel(childIndex, parentIndex, 2, menuModel));
                }
            }
        }

        postMessage(treeModels);
    }

    @Override
    public void run() {
        if ((this.entityBITS & cSharedPreference.PERMISSION) != 0) {
            if ((this.entitypermBITS & cSharedPreference.READ) != 0) {
                this.permissionRepository.readUserPermissions(organizationServerID,
                        userServerID, primaryTeamBIT, secondaryTeamBITS, statusBITS,
                        new iPermissionRepository.iReadUserPermissionsCallback() {
                            @Override
                            public void onReadUserPermissionsSucceeded(Map<cRoleModel,
                                    cPermissionModel> rolePermissionModels) {

                                buildAndReadTree(rolePermissionModels);

                                //postMessage(rolePermissionModels);
                            }

                            @Override
                            public void onReadUserPermissionsFailed(String msg) {
                                notifyError(msg);
                            }
                        });
            } else {
                notifyError("Permission denied! Please contact your administrator");
            }
        } else {
            notifyError("No access to the entity! Please contact your administrator");
        }
    }
}