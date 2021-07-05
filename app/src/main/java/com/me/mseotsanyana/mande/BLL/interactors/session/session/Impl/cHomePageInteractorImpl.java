package com.me.mseotsanyana.mande.BLL.interactors.session.session.Impl;

import android.util.Log;

import com.google.gson.Gson;
import com.me.mseotsanyana.mande.BLL.executor.iExecutor;
import com.me.mseotsanyana.mande.BLL.executor.iMainThread;
import com.me.mseotsanyana.mande.BLL.interactors.base.cAbstractInteractor;
import com.me.mseotsanyana.mande.BLL.interactors.session.session.iHomePageInteractor;
import com.me.mseotsanyana.mande.BLL.model.session.cMenuModel;
import com.me.mseotsanyana.mande.BLL.model.session.cUserProfileModel;
import com.me.mseotsanyana.mande.BLL.repository.session.iHomePageRepository;
import com.me.mseotsanyana.mande.BLL.repository.session.iSharedPreferenceRepository;
import com.me.mseotsanyana.mande.DAL.storage.preference.cSharedPreference;

import java.util.List;

public class cHomePageInteractorImpl extends cAbstractInteractor
        implements iHomePageInteractor {
    private static final String TAG = cHomePageInteractorImpl.class.getSimpleName();

    private final Callback callback;
    private final iSharedPreferenceRepository sharedPreferenceRepository;
    private final iHomePageRepository homePageRepository;

    private final String userServerID;
    private final String organizationServerID;
    private final int primaryTeamBIT;
    private final List<Integer> secondaryTeams;

    private final int teamEntitypermBITS;
    private final List<Integer> teamStatuses;
    private final int teamUnixpermBITS;

    private final int roleEntitypermBITS;
    private final List<Integer> roleStatuses;
    private final int roleUnixpermBITS;

    private final int privilegeEntitypermBITS;
    private final List<Integer> privilegeStatuses;
    private final int privilegeUnixpermBITS;

    Gson gson = new Gson();

    public cHomePageInteractorImpl(iExecutor threadExecutor, iMainThread mainThread, Callback callback,
                                   iSharedPreferenceRepository sharedPreferenceRepository,
                                   iHomePageRepository homePageRepository) {
        super(threadExecutor, mainThread);

        if (sharedPreferenceRepository == null || homePageRepository == null || callback == null) {
            throw new IllegalArgumentException("Arguments can not be null!");
        }

        this.sharedPreferenceRepository = sharedPreferenceRepository;
        this.homePageRepository = homePageRepository;
        this.callback = callback;

        // load shared preferences

        this.userServerID = sharedPreferenceRepository.loadUserID();
        this.organizationServerID = sharedPreferenceRepository.loadOrganizationID();
        this.primaryTeamBIT = sharedPreferenceRepository.loadPrimaryTeamBIT();
        this.secondaryTeams = sharedPreferenceRepository.loadSecondaryTeams();

        // team permissions
        this.teamEntitypermBITS = sharedPreferenceRepository.loadEntityPermissionBITS(
                cSharedPreference.SESSION_MODULE, cSharedPreference.TEAM);
        this.teamStatuses = sharedPreferenceRepository.loadOperationStatuses(
                cSharedPreference.SESSION_MODULE, cSharedPreference.TEAM,
                cSharedPreference.READ);
        this.teamUnixpermBITS = sharedPreferenceRepository.loadUnixPermissionBITS(
                cSharedPreference.SESSION_MODULE, cSharedPreference.TEAM);

        // role permissions
        this.roleEntitypermBITS = sharedPreferenceRepository.loadEntityPermissionBITS(
                cSharedPreference.SESSION_MODULE, cSharedPreference.ROLE);
        this.roleStatuses = sharedPreferenceRepository.loadOperationStatuses(
                cSharedPreference.SESSION_MODULE, cSharedPreference.ROLE,
                cSharedPreference.READ);
        this.roleUnixpermBITS = sharedPreferenceRepository.loadUnixPermissionBITS(
                cSharedPreference.SESSION_MODULE, cSharedPreference.ROLE);

        // privilege permissions
        this.privilegeEntitypermBITS = sharedPreferenceRepository.loadEntityPermissionBITS(
                cSharedPreference.SESSION_MODULE, cSharedPreference.PRIVILEGE);
        this.privilegeStatuses = sharedPreferenceRepository.loadOperationStatuses(
                cSharedPreference.SESSION_MODULE, cSharedPreference.PRIVILEGE,
                cSharedPreference.READ);
        this.privilegeUnixpermBITS = sharedPreferenceRepository.loadUnixPermissionBITS(
                cSharedPreference.SESSION_MODULE, cSharedPreference.PRIVILEGE);

        Log.d(TAG, " \n USER ID = " + this.userServerID +
                " \n ORGANIZATION ID = " + this.organizationServerID +
                " \n PRIMARY TEAM BIT = " + this.primaryTeamBIT +
                " \n SECONDARY TEAM BITS = "+this.secondaryTeams +
                " \n ENTITY PERMISSION BITS = "+this.teamEntitypermBITS +
                " \n OPERATION STATUSES = " + this.teamStatuses +
                " \n UNIX PERMISSIONS = "+ this.teamUnixpermBITS);
    }

    /* call back on update home page failed */
    private void notifyError(String msg) {
        mainThread.post(() -> callback.onReadHomePageFailed(msg));
    }

    /* call back on user profile */
    private void userProfileMessage(cUserProfileModel userProfileModel) {
        mainThread.post(() -> callback.onReadUserProfileSucceeded(userProfileModel));
    }

    /* call back on role menu items */
    private void menuItemsMessage(List<cMenuModel> menuModels) {
        mainThread.post(() -> callback.onReadMenuItemsSucceeded(menuModels));
    }

    /* call back on user profile and default menu items */
    private void defaultProfileMessage(cUserProfileModel userProfileModel,
                                       List<cMenuModel> menuModels) {
        mainThread.post(() -> callback.onDefaultHomePageSucceeded(userProfileModel, menuModels));
    }

    @Override
    public void run() {

        /* load user profile and menu items */
        this.homePageRepository.updateHomePageModels(userServerID, organizationServerID,
                primaryTeamBIT, secondaryTeams, teamEntitypermBITS, teamStatuses, teamUnixpermBITS,
                new iHomePageRepository.iHomePageCallback() {
                    @Override
                    public void onReadUserProfileSucceeded(cUserProfileModel userProfileModel) {
                        userProfileMessage(userProfileModel);
                        Log.d(TAG, " userProfileModel => " + gson.toJson(userProfileModel));
                    }

                    @Override
                    public void onReadMenuItemsSucceeded() {
                        List<cMenuModel> menuModels;
                        menuModels = sharedPreferenceRepository.loadMenuItems();
                        menuItemsMessage(menuModels);
                        Log.d(TAG, " roleMenuItemsMap => " + gson.toJson(menuModels));
                    }

                    @Override
                    public void onDefaultHomePageSucceeded(cUserProfileModel userProfileModel,
                                                           List<cMenuModel> menuModels) {
                        defaultProfileMessage(userProfileModel, menuModels);
                    }

                    @Override
                    public void onReadHomePageFailed(String msg) {
                        notifyError(msg);
                    }
                });
    }
}

//                    @Override
//                    public void onReadUserAccountSucceeded(cUserAccountModel userAccountModel) {
//
//                    }

//                    @Override
//                    public void onReadRolesSucceeded(List<cRoleModel> roleModels) {
//                        rolesMessage(roleModels);
//                        Log.d(TAG, " roleModels => " + gson.toJson(roleModels));
//                    }


//                    @Override
//                    public void onReadRolePrivilegesSucceeded(cRoleModel roleModel, List<cPrivilegeModel> privilegeModels) {
//                        HashMap<cRoleModel, List<cPrivilegeModel>> rolePrivilegesMap = new HashMap<>();
//                        rolePrivilegesMap.put(roleModel, privilegeModels);
//                        rolePrivilegesMessage(rolePrivilegesMap);
//                        Log.d(TAG, " rolePrivilegesMap => " + gson.toJson(rolePrivilegesMap));
//                    }
//
//                    @Override
//                    public void onReadPrivilegePermissionsSucceeded(cPrivilegeModel privilegeModel,
//                                                                    List<cPermissionModel> permissionModels) {
//                        HashMap<cPrivilegeModel, List<cPermissionModel>> privilegePermissionsMap = new HashMap<>();
//                        privilegePermissionsMap.put(privilegeModel, permissionModels);
//                        privilegePermissionsMessage(privilegePermissionsMap);
//                        Log.d(TAG, " privilegePermissionsMap => " + gson.toJson(privilegePermissionsMap));
//                    }


//        /* get the logged in user */
//        cUserModel userModel = userRepository.getUserByEmailPassword(email, password);
//
//        //userRepository.createUserByEmailAndPassword(email, password,null);
//        Gson gson = new Gson();
//        Log.d(TAG,"USER MODEL: "+gson.toJson(userModel));
//
//
//        if (userModel != null) {
//            if (!userModel.getRoleModelSet().isEmpty()) {
//                /* delete all shared preferences */
//                sessionManagerRepository.deleteSettings();
//
//                /* save user/owner ID */
//                sessionManagerRepository.saveUserID(userModel.getUserID());
//                /* save owner organization ID */
//                sessionManagerRepository.saveOrganizationID(userModel.getOrganizationID());
//                /* compute and save user primary role bits */
//                sessionManagerRepository.savePrimaryRoleBITS(userModel);
//                /* compute and save user secondary role bits */
//                sessionManagerRepository.saveSecondaryRoleBITS(userModel);
//                /* save default permission bits */
//                sessionManagerRepository.saveDefaultPermsBITS(cBitwise.OWNER);
//                /* set default status bits */
//                sessionManagerRepository.saveDefaultStatusBITS(cBitwise.ACTIVATED);
//
//                /* compute and save entity, operation and statuses bits for the user */
//                Set<cPermissionModel> permissionModelSet = new HashSet<>();
//                for (cRoleModel roleModel : userModel.getRoleModelSet()) {
//                    Set<cPermissionModel> permissionSet = null;//roleRepository.getPermissionSetByRoleID(roleModel.getRoleID(), 0/*roleModel.getOrganizationID()*/);
//                    permissionModelSet.addAll(permissionSet);
//                }
//
//                if (!permissionModelSet.isEmpty()) {
//                    sessionManagerRepository.savePermissionBITS(permissionModelSet);
//                    /* compute and save the status and role sets */
//                    sessionManagerRepository.saveStatusSet(statusRepository.getStatusSet());
//                    sessionManagerRepository.saveRoleSet(roleRepository.getRoleModelSet());
//                    /* save the individual and organization owners */
//                    sessionManagerRepository.saveIndividualOwners(userRepository.getOwnerSet());
//                    sessionManagerRepository.saveOrganizationOwners(
//                            organizationRepository.getOrganizationSet());
//
//                    /* save the shared preferences */
//                    sessionManagerRepository.commitSettings();
//                } else {
//                    notifyError("Login failed since there are no roles assigned !!");
//                }
//
//                postMessage(userModel);
//            } else {
//                notifyError("Login failed since there are no privileges assigned !!");
//            }
//        } else {
//            notifyError("Login failed due to invalid user login details !!");
//        }

