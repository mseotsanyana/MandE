package com.me.mseotsanyana.mande.DAL.ìmpl.firestore.session;

import android.content.Context;
import android.util.Log;
import android.util.Pair;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.me.mseotsanyana.mande.BLL.model.session.cMenuModel;
import com.me.mseotsanyana.mande.BLL.model.session.cOrganizationModel;
import com.me.mseotsanyana.mande.BLL.model.session.cPermissionModel;
import com.me.mseotsanyana.mande.BLL.model.session.cPlanModel;
import com.me.mseotsanyana.mande.BLL.model.session.cPrivilegeModel;
import com.me.mseotsanyana.mande.BLL.model.session.cRoleModel;
import com.me.mseotsanyana.mande.BLL.model.session.cTeamModel;
import com.me.mseotsanyana.mande.BLL.model.session.cUserAccountModel;
import com.me.mseotsanyana.mande.BLL.model.utils.cCommonPropertiesModel;
import com.me.mseotsanyana.mande.BLL.repository.session.iOrganizationRepository;
import com.me.mseotsanyana.mande.DAL.storage.database.cRealtimeHelper;
import com.me.mseotsanyana.mande.DAL.ìmpl.cDatabaseUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mseotsanyana on 2016/10/23.
 */
public class cOrganizationFirestoreRepositoryImpl implements iOrganizationRepository {
    private static final String TAG = cOrganizationFirestoreRepositoryImpl.class.getSimpleName();

    // an object of the database helper
    private final FirebaseFirestore db;
    private final Context context;

    public cOrganizationFirestoreRepositoryImpl(Context context) {
        this.db = FirebaseFirestore.getInstance();
        this.context = context;
    }

    /* ############################################# CREATE ACTIONS ############################################# */

    /**
     * create organization
     *
     * @param organizationModel organization model
     * @param callback          call back
     */
    @Override
    public void createOrganization(cOrganizationModel organizationModel,
                                   iOrganizationRepositoryCallback callback) {

        // get current logged in user
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        assert currentUser != null;
        String userServerID = currentUser.getUid();

        // create new organization
        CollectionReference collectionOrgRef = db.collection(cRealtimeHelper.KEY_ORGANIZATIONS);
        String organizationServerID = collectionOrgRef.document().getId();

        // update organization default dates
        Date currentDate = new Date();
        organizationModel.setCreatedDate(currentDate);
        organizationModel.setModifiedDate(currentDate);

        // update organization model with default values
        cCommonPropertiesModel commonPropertiesModel = cDatabaseUtils.getColumnModel(context);
        organizationModel.setOwnerID(userServerID);
        organizationModel.setOrgOwnerID(organizationServerID);
        organizationModel.setTeamOwnerBIT(commonPropertiesModel.getCteamOwnerBIT());
        organizationModel.setUnixpermsBITS(commonPropertiesModel.getCunixpermsBITS());
        organizationModel.setStatusesBITS(commonPropertiesModel.getCstatusesBITS());

        collectionOrgRef.document(organizationServerID).set(organizationModel).
                addOnSuccessListener(documentReference -> {

                    Map<String, Object> subOrganization = new HashMap<>();
                    subOrganization.put("organizationID", organizationServerID);

                    CollectionReference coOrganizationTypeRef;
                    switch (organizationModel.getOrganizationType()) {
                        case 0:
                            coOrganizationTypeRef = db.collection(cRealtimeHelper.KEY_BENEFACTORS);
                            coOrganizationTypeRef.document(organizationServerID).set(subOrganization);
                            coOrganizationTypeRef = db.collection(cRealtimeHelper.KEY_NATIONAL_PARTNERS);
                            coOrganizationTypeRef.document(organizationServerID).set(subOrganization);
                            break;
                        case 1:
                            coOrganizationTypeRef = db.collection(cRealtimeHelper.KEY_BENEFACTORS);
                            coOrganizationTypeRef.document(organizationServerID).set(subOrganization);
                            coOrganizationTypeRef = db.collection(cRealtimeHelper.KEY_DONORS);
                            coOrganizationTypeRef.document(organizationServerID).set(subOrganization);
                            break;
                        case 2:
                            coOrganizationTypeRef = db.collection(cRealtimeHelper.KEY_BENEFICIARIES);
                            coOrganizationTypeRef.document(organizationServerID).set(subOrganization);
                            break;
                        case 3:
                            coOrganizationTypeRef = db.collection(cRealtimeHelper.KEY_IMPLEMENTING_AGENCIES);
                            coOrganizationTypeRef.document(organizationServerID).set(subOrganization);
                            break;
                        default:
                            Log.d(TAG, "Error in creating an organization");
                    }

                    /* default settings */
                    cPlanModel defaultPlanModel = cDatabaseUtils.getDefaultPlanModel(context);
                    cTeamModel teamModel = cDatabaseUtils.getAdminTeamModel(context, organizationServerID,
                            commonPropertiesModel);
                    cRoleModel roleModel = cDatabaseUtils.getAdminRoleModel(context, commonPropertiesModel);
                    cPrivilegeModel privilegeModel = cDatabaseUtils.getAdminPrivilegeModel(context,
                            commonPropertiesModel);
                    cPermissionModel permissionModel = cDatabaseUtils.getAdminPermissions(context);

                    /* create links */
                    createUserAccounts(organizationServerID, userServerID, teamModel, defaultPlanModel);
                    createTeamLinks(organizationServerID, userServerID, teamModel, commonPropertiesModel);
                    createRoleLinks(organizationServerID, userServerID, teamModel, roleModel,
                            commonPropertiesModel);
                    createMenuItemLinks(roleModel);
                    createPrivilegeLinks(organizationServerID, userServerID, roleModel, privilegeModel,
                            permissionModel, commonPropertiesModel);

                    callback.onCreateOrganizationSucceeded("Organization saved successfully.");

                })
                .addOnFailureListener(e -> {
                    callback.onCreateOrganizationFailed("Organization could not be saved " +
                            e.getMessage());
                });
    }

    /**
     * create user account
     *
     * @param organizationServerID organization identification
     * @param userServerID         user identification
     * @param teamModel            team model
     * @param planModel            plan model
     */
    public void createUserAccounts(String organizationServerID, String userServerID,
                                   cTeamModel teamModel, cPlanModel planModel) {
        CollectionReference coUserAccountsRef;
        // create a user account for the user in an organization just created
        coUserAccountsRef = db.collection(cRealtimeHelper.KEY_USERACCOUNTS);

        cUserAccountModel userAccountModel = new cUserAccountModel();

        userAccountModel.setUserAccountServerID(organizationServerID + "_" + userServerID);
        userAccountModel.setOrganizationServerID(organizationServerID);
        userAccountModel.setTeamServerID(teamModel.getTeamServerID());
        userAccountModel.setUserServerID(userServerID);
        userAccountModel.setPlanServerID(planModel.getPlanServerID());
        userAccountModel.setCurrentOrganization(true);

        // update user account model with default values
        cCommonPropertiesModel commonPropertiesModel = cDatabaseUtils.getColumnModel(context);
        userAccountModel.setOwnerID(userServerID);
        userAccountModel.setOrgOwnerID(organizationServerID);
        userAccountModel.setTeamOwnerBIT(commonPropertiesModel.getCteamOwnerBIT());
        userAccountModel.setUnixpermsBITS(commonPropertiesModel.getCunixpermsBITS());
        userAccountModel.setStatusesBITS(commonPropertiesModel.getCstatusesBITS());

        Date currentDate = new Date();
        userAccountModel.setCreatedDate(currentDate);
        userAccountModel.setModifiedDate(currentDate);

        coUserAccountsRef.document(organizationServerID + "_" + userServerID).
                set(userAccountModel);

        // make sure the just created organization is activated
        Query query = coUserAccountsRef.whereEqualTo("userServerID", userServerID);
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for (QueryDocumentSnapshot doc : task.getResult()) {
                    cUserAccountModel useraccount = doc.toObject(cUserAccountModel.class);
                    if (!(useraccount.getOrganizationServerID().equals(organizationServerID))) {
                        useraccount.setCurrentOrganization(false);
                        coUserAccountsRef.document(useraccount.getOrganizationServerID() + "_" +
                                userServerID).set(useraccount);
                    }
                }
            }
        });
    }

    /**
     * create a team
     *
     * @param organizationServerID  organization identification
     * @param userServerID          user identification
     * @param teamModel             team model
     * @param commonPropertiesModel common properties model
     */
    public void createTeamLinks(String organizationServerID, String userServerID,
                                cTeamModel teamModel, cCommonPropertiesModel commonPropertiesModel) {
        // create a team in an organization just created
        CollectionReference coTeamsRef = db.collection(cRealtimeHelper.KEY_TEAMS);
        String compositeServerID = teamModel.getCompositeServerID();

        // update team's common columns
        teamModel.setOwnerID(userServerID);
        teamModel.setOrgOwnerID(organizationServerID);
        teamModel.setTeamOwnerBIT(commonPropertiesModel.getCteamOwnerBIT());
        teamModel.setUnixpermsBITS(commonPropertiesModel.getCunixpermsBITS());
        teamModel.setStatusesBITS(commonPropertiesModel.getCstatusesBITS());

        // update default dates
        Date currentDate = new Date();
        teamModel.setCreatedDate(currentDate);
        teamModel.setModifiedDate(currentDate);

        // create a team in the database
        coTeamsRef.document(compositeServerID).set(teamModel);

        // add the current user to the team of the organization just created
        CollectionReference coTeamMembersRef;
        coTeamMembersRef = db.collection(cRealtimeHelper.KEY_TEAM_MEMBERS);
        Map<String, Object> team_members = new HashMap<>();
        team_members.put("userAccountServerID", organizationServerID + "_" + userServerID);
        team_members.put("teamMemberServerID", compositeServerID);
        coTeamMembersRef.document(organizationServerID + "_" + userServerID + "_" +
                compositeServerID).set(team_members);
    }

    /**
     * create a role
     *
     * @param teamModel             team model
     * @param roleModel             role model
     * @param commonPropertiesModel common property model
     */
    public void createRoleLinks(String organizationServerID, String userServerID,
                                cTeamModel teamModel, cRoleModel roleModel,
                                cCommonPropertiesModel commonPropertiesModel) {
        // create s role of the organization just created
        CollectionReference coRoleRef = db.collection(cRealtimeHelper.KEY_ROLES);
        String roleID = coRoleRef.document().getId();

        String compositeTeamID = teamModel.getCompositeServerID();

        roleModel.setRoleServerID(roleID);
        // update role's common columns
        roleModel.setOwnerID(userServerID);
        roleModel.setOrgOwnerID(organizationServerID);
        roleModel.setTeamOwnerBIT(commonPropertiesModel.getCteamOwnerBIT());
        roleModel.setUnixpermsBITS(commonPropertiesModel.getCunixpermsBITS());
        roleModel.setStatusesBITS(commonPropertiesModel.getCstatusesBITS());

        // update default dates
        Date currentDate = new Date();
        roleModel.setCreatedDate(currentDate);
        roleModel.setModifiedDate(currentDate);

        coRoleRef.document(roleID).set(roleModel);

        // add admin role to the user of the organization
        CollectionReference coTeamRolesRef;
        coTeamRolesRef = db.collection(cRealtimeHelper.KEY_TEAM_ROLES);
        Map<String, Object> team_roles = new HashMap<>();
        team_roles.put("teamServerID", compositeTeamID);
        team_roles.put("roleServerID", roleID);
        coTeamRolesRef.document(compositeTeamID + "_" + roleID).set(team_roles);
    }

    /**
     * role menu items
     *
     * @param roleModel role model
     */
    public void createMenuItemLinks(cRoleModel roleModel) {
        // create menu items of the related roles
        CollectionReference coOrgRoleMenuRef;
        // add menu items related to admin role of the organization
        coOrgRoleMenuRef = db.collection(cRealtimeHelper.KEY_ROLE_MENU_ITEMS);

        Map<cMenuModel, Map<String, cMenuModel>> modelMapHashMap = cDatabaseUtils.
                getAdminMenuModelSet(context);

        Map<String, Object> role_menus = new HashMap<>();
        for (Map.Entry<cMenuModel, Map<String, cMenuModel>> entry : modelMapHashMap.entrySet()) {
            cMenuModel menuModel = entry.getKey();
            String menuServerID = String.valueOf(menuModel.getMenuServerID());

            // create sub menu items
            Map<String, cMenuModel> subMenuModelMap = entry.getValue();
            List<Integer> sub_menu = new ArrayList<>();
            if (subMenuModelMap.size() > 0) {
                for (Map.Entry<String, cMenuModel> subEntry : subMenuModelMap.entrySet()) {
                    sub_menu.add(subEntry.getValue().getMenuServerID());
                }
            }

            // add menu items to the role
            role_menus.put(menuServerID, sub_menu);
        }

        String roleServerID = roleModel.getRoleServerID();
        coOrgRoleMenuRef.document(roleServerID).set(role_menus);
    }

    /**
     * create object and property operations
     *
     * @param organizationServerID  organization identification
     * @param userServerID          user identification
     * @param roleModel             role model
     * @param privilegeModel        privilege model
     * @param permissionModel       permission model
     * @param commonPropertiesModel common property models
     */
    public void createPrivilegeLinks(String organizationServerID, String userServerID,
                                     cRoleModel roleModel, cPrivilegeModel privilegeModel,
                                     cPermissionModel permissionModel,
                                     cCommonPropertiesModel commonPropertiesModel) {

        CollectionReference coPrivilegeRef, coRolePrivRef, coPrivPermsRef;

        coPrivilegeRef = db.collection(cRealtimeHelper.KEY_PRIVILEGES);
        coRolePrivRef = db.collection(cRealtimeHelper.KEY_ROLE_PRIVILEGES);
        coPrivPermsRef = db.collection(cRealtimeHelper.KEY_PRIVILEGE_PERMISSIONS);
        //coPrivUnixPermsRef = db.collection(cRealtimeHelper.KEY_PRIVILEGE_UNIXPERMS);

        // creating admin privilege
        String privilegeID = coPrivilegeRef.document().getId();
        privilegeModel.setPrivilegeServerID(privilegeID);

        // update role's common columns
        privilegeModel.setOwnerID(userServerID);
        privilegeModel.setOrgOwnerID(organizationServerID);
        privilegeModel.setTeamOwnerBIT(commonPropertiesModel.getCteamOwnerBIT());
        privilegeModel.setUnixpermsBITS(commonPropertiesModel.getCunixpermsBITS());
        privilegeModel.setStatusesBITS(commonPropertiesModel.getCstatusesBITS());

        // update default dates
        Date currentDate = new Date();
        privilegeModel.setCreatedDate(currentDate);
        privilegeModel.setModifiedDate(currentDate);

        coPrivilegeRef.document(privilegeID).set(privilegeModel);

        // creating admin role privileges
        Map<String, Object> role_privileges = new HashMap<>();
        String roleID = roleModel.getRoleServerID();
        role_privileges.put("roleServerID", roleID);
        role_privileges.put("privilegeServerID", privilegeID);
        coRolePrivRef.document(roleID + "_" + privilegeID).set(role_privileges);

        // create operation statuses and unix permissions
        coPrivPermsRef.document(privilegeID).set(permissionModel);
    }

    /* ############################################# READ ACTIONS ############################################# */

    @Override
    public void readOrganizations(String userServerID, String orgServerID, int primaryTeamBIT,
                                  int secondaryTeamBITS, int entitypermBITS, List<Integer> statuses,
                                  int unixpermBITS, iReadOrganizationsModelSetCallback callback) {

        CollectionReference coOrganizationRef = db.collection(cRealtimeHelper.KEY_ORGANIZATIONS);

        Task adminTask, ownerTask, primaryTask, secondaryTask, otherTask;

        /* 1. members of the 'admin' team are always allowed to do everything */
        adminTask = coOrganizationRef
                .whereEqualTo("orgOwnerID", orgServerID)
                .whereEqualTo("teamOwnerBIT", 1)
                .get();

//        /* 2. the owner permission bit is on, and the member is the owner */
//        ownerTask = coOrganizationRef
//                .whereEqualTo("orgOwnerID", orgServerID)
//                .whereEqualTo("ownerID", userServerID)
//                .whereArrayContains("unixpermsBITS", unixpermBIT)
//                .get();
//
//        /* 3. the primary team permission bit is on, and the member is in the team */
//        primaryTask = coOrganizationRef
//                .whereEqualTo("orgOwnerID", orgServerID)
//                .whereEqualTo("teamServerID", primaryTeamBIT)
//                .whereArrayContains("unixpermsBITS", unixpermBIT)
//                .get();
//
//        /* 4. the second team permission bit is on, and the member is in the team */
//        secondaryTask = coOrganizationRef
//                .whereEqualTo("orgOwnerID", orgServerID)
//                .whereIn("teamServerID", secondaryTeamBITS)
//                .whereArrayContains("unixpermsBITS", unixpermBIT)
//                .get();
//
//        /* 5. the other permission bit is on -
//              all members of an organization have access */
//        otherTask = coOrganizationRef
//                .whereEqualTo("orgOwnerID", orgServerID)
//                .whereArrayContains("unixpermsBITS", unixpermBIT)
//                .get();

        Task<List<QuerySnapshot>> allPerms = Tasks.whenAllSuccess(adminTask);

        allPerms
                .addOnCompleteListener(new OnCompleteListener<List<QuerySnapshot>>() {
                    @Override
                    public void onComplete(@NonNull Task<List<QuerySnapshot>> task) {
                        //Log.d(TAG, "I AM HERE ========================== "+task.getResult());
                        if (task.isSuccessful()) {

                            ArrayList<cOrganizationModel> organizationModels = new ArrayList<>();
                            for (QuerySnapshot queryDocumentSnapshots : task.getResult()) {
                                //Log.d(TAG, "I AM HERE ========================== "+queryDocumentSnapshots.getDocuments());

                                for (QueryDocumentSnapshot ds : queryDocumentSnapshots) {
                                    cOrganizationModel organizationModel = ds.toObject(cOrganizationModel.class);
                                    organizationModels.add(organizationModel);
                                    Log.d(TAG, "Organization name: " + organizationModel.getName() + ", email " +
                                            organizationModel.getEmail() + ", website " + organizationModel.getEmail());
                                }
                            }

                            callback.onReadOrganizationsSucceeded(organizationModels);
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        callback.onReadOrganizationsFailed("Failed to read Organization." + e);
                        Log.w(TAG, "Failed to read value.", e);
                    }
                });


//        coOrganizationRef.get().
//                addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        ArrayList<cOrganizationModel> organizationModels = new ArrayList<>();
//                        if (task.isSuccessful()) {
//                            for (QueryDocumentSnapshot ds : task.getResult()) {
//                                cOrganizationModel organizationModel = ds.toObject(cOrganizationModel.class);
//                                organizationModels.add(organizationModel);
//                                Log.d(TAG, "Organization name: " + organizationModel.getName() + ", email " +
//                                        organizationModel.getEmail() + ", website " + organizationModel.getEmail());
//                            }
//
//                            callback.onReadOrganizationsSucceeded(organizationModels);
//                        }
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        callback.onReadOrganizationsFailed("Failed to read Organization." + e);
//                        Log.w(TAG, "Failed to read value.", e);
//                    }
//                });
    }

    /* ############################################# UPDATE ACTIONS ############################################# */


    /* ############################################# DELETE ACTIONS ############################################# */

}
