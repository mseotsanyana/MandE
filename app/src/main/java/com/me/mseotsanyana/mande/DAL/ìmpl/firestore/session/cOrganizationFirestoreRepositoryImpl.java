package com.me.mseotsanyana.mande.DAL.ìmpl.firestore.session;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.me.mseotsanyana.mande.BLL.model.session.cOrganizationModel;
import com.me.mseotsanyana.mande.BLL.model.session.cPermissionModel;
import com.me.mseotsanyana.mande.BLL.model.session.cPlanModel;
import com.me.mseotsanyana.mande.BLL.model.session.cRoleModel;
import com.me.mseotsanyana.mande.BLL.model.session.cTeamModel;
import com.me.mseotsanyana.mande.BLL.model.session.cUserAccountModel;
import com.me.mseotsanyana.mande.BLL.model.session.cUserProfileModel;
import com.me.mseotsanyana.mande.BLL.model.utils.cCommonPropertiesModel;
import com.me.mseotsanyana.mande.BLL.repository.session.iOrganizationRepository;
import com.me.mseotsanyana.mande.DAL.storage.database.cRealtimeHelper;
import com.me.mseotsanyana.mande.DAL.ìmpl.cDatabaseUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;


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

    /* ##################################### CREATE ACTIONS ##################################### */

    /**
     * create organization
     *
     * @param organizationModel organization model
     * @param callback          call back
     */
    @Override
    public void createOrganization(cOrganizationModel organizationModel,
                                   iCreateOrganizationCallback callback) {

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
        cCommonPropertiesModel commonPropertiesModel = cDatabaseUtils.getCommonModel(context);
        organizationModel.setUserOwnerID(userServerID);
        organizationModel.setOrganizationOwnerID(organizationServerID);
        organizationModel.setTeamOwnerBIT(commonPropertiesModel.getCteamOwnerBIT());
        organizationModel.setUnixpermBITS(commonPropertiesModel.getCunixpermBITS());
        organizationModel.setStatusBIT(commonPropertiesModel.getCstatusBIT());

        collectionOrgRef.document(organizationServerID).set(organizationModel).
                addOnSuccessListener(documentReference -> {

                    Map<String, Object> subOrganization = new HashMap<>();
                    subOrganization.put("organizationServerID", organizationServerID);

                    CollectionReference orgTypeRef;
                    switch (organizationModel.getOrganizationType()) {
                        case 0:
                            orgTypeRef = db.collection(cRealtimeHelper.KEY_BENEFACTORS);
                            orgTypeRef.document(organizationServerID).set(subOrganization);
                            orgTypeRef = db.collection(cRealtimeHelper.KEY_NATIONAL_PARTNERS);
                            orgTypeRef.document(organizationServerID).set(subOrganization);
                            break;
                        case 1:
                            orgTypeRef = db.collection(cRealtimeHelper.KEY_BENEFACTORS);
                            orgTypeRef.document(organizationServerID).set(subOrganization);
                            orgTypeRef = db.collection(cRealtimeHelper.KEY_DONORS);
                            orgTypeRef.document(organizationServerID).set(subOrganization);
                            break;
                        case 2:
                            orgTypeRef = db.collection(cRealtimeHelper.KEY_BENEFICIARIES);
                            orgTypeRef.document(organizationServerID).set(subOrganization);
                            break;
                        case 3:
                            orgTypeRef = db.collection(cRealtimeHelper.KEY_IMPLEMENTING_AGENCIES);
                            orgTypeRef.document(organizationServerID).set(subOrganization);
                            break;
                        default:
                            Log.d(TAG, "Error in creating an organization");
                    }

                    /* create a default administrator team during the creation of an organization */
                    cTeamModel teamModel = cDatabaseUtils.getAdminTeamModel(context,
                            organizationServerID, commonPropertiesModel);
                    /* create a default administrator role during the creation of an organization */
                    cRoleModel roleModel = cDatabaseUtils.getAdminRoleModel(context,
                            commonPropertiesModel);
                    /* read a default permissions associated with the administrator role during
                    the creation of an organization */
                    cPermissionModel permissionModel = cDatabaseUtils.getAdminPermissions(context);
                    /* read a default freemium plan from json associated with the administrator
                    of the organization just created */
                    cPlanModel freemiumPlanModel = cDatabaseUtils.getDefaultPlanModel(context);
                    /* create a user account of the administrator with the organization just
                    created */
                    createUserAccount(organizationServerID, userServerID, teamModel,
                            freemiumPlanModel);
                    /* create an administrator team of the organization just created */
                    createTeamLinks(organizationServerID, userServerID, teamModel,
                            commonPropertiesModel);
                    /* create administrator role with the entity and unix permissions of the team
                    just created */
                    createRoleLinks(organizationServerID, userServerID, teamModel,
                            roleModel, permissionModel, commonPropertiesModel);
                    /* call back on successful message */
                    callback.onCreateOrganizationSucceeded("Organization saved successfully.");
                })
                .addOnFailureListener(e -> callback.onCreateOrganizationFailed(
                        "Organization could not be saved " + e.getMessage()));
    }

    /**
     * create user account
     *
     * @param organizationServerID organization identification
     * @param userServerID         user identification
     * @param teamModel            team model
     * @param planModel            plan model
     */
    public void createUserAccount(String organizationServerID, String userServerID,
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
        cCommonPropertiesModel commonPropertiesModel = cDatabaseUtils.getCommonModel(context);
        userAccountModel.setUserOwnerID(userServerID);
        userAccountModel.setOrganizationOwnerID(organizationServerID);
        userAccountModel.setTeamOwnerBIT(commonPropertiesModel.getCteamOwnerBIT());
        userAccountModel.setUnixpermBITS(commonPropertiesModel.getCunixpermBITS());
        userAccountModel.setStatusBIT(commonPropertiesModel.getCstatusBIT());

        Date currentDate = new Date();
        userAccountModel.setCreatedDate(currentDate);
        userAccountModel.setModifiedDate(currentDate);

        coUserAccountsRef.document(organizationServerID + "_" + userServerID).
                set(userAccountModel);

        // make sure the just created organization is activated
        Query query = coUserAccountsRef.whereEqualTo("userServerID", userServerID);
        query.get().addOnCompleteListener(task -> {
            for (QueryDocumentSnapshot doc : Objects.requireNonNull(task.getResult())) {
                cUserAccountModel useraccount = doc.toObject(cUserAccountModel.class);
                if (!(useraccount.getOrganizationServerID().equals(organizationServerID))) {
                    useraccount.setCurrentOrganization(false);
                    coUserAccountsRef.document(
                            useraccount.getOrganizationServerID() + "_" +
                                    userServerID).set(useraccount);
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
        teamModel.setUserOwnerID(userServerID);
        teamModel.setOrganizationOwnerID(organizationServerID);
        teamModel.setTeamOwnerBIT(commonPropertiesModel.getCteamOwnerBIT());
        teamModel.setUnixpermBITS(commonPropertiesModel.getCunixpermBITS());
        teamModel.setStatusBIT(commonPropertiesModel.getCstatusBIT());

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
     * create a role with its permissions
     *
     * @param organizationServerID  organization identification
     * @param userServerID          user identification
     * @param teamModel             team model
     * @param roleModel             role model
     * @param permissionModel       permission model
     * @param commonPropertiesModel common properties model
     */
    public void createRoleLinks(String organizationServerID, String userServerID,
                                cTeamModel teamModel, cRoleModel roleModel,
                                cPermissionModel permissionModel,
                                cCommonPropertiesModel commonPropertiesModel) {
        // create s role of the organization just created
        CollectionReference coRoleRef, coRolePermsRef;
        coRoleRef = db.collection(cRealtimeHelper.KEY_ROLES);
        coRolePermsRef = db.collection(cRealtimeHelper.KEY_ROLE_PERMISSIONS);

        String roleID = coRoleRef.document().getId();
        String compositeTeamID = teamModel.getCompositeServerID();

        roleModel.setRoleServerID(roleID);
        // update role's common columns
        roleModel.setUserOwnerID(userServerID);
        roleModel.setOrganizationOwnerID(organizationServerID);
        roleModel.setTeamOwnerBIT(commonPropertiesModel.getCteamOwnerBIT());
        roleModel.setUnixpermBITS(commonPropertiesModel.getCunixpermBITS());
        roleModel.setStatusBIT(commonPropertiesModel.getCstatusBIT());

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

        // add permissions to the role
        permissionModel.setDescription("Administrator permissions for both entity and property level " +
                "access control");
        coRolePermsRef.document(roleID).set(permissionModel);
    }

    /* ###################################### READ ACTIONS ###################################### */

    /**
     * read organizations
     *
     * @param organizationServerID organization identifications that are aligned to the user
     *                             account of the loggedin user - FIXME
     * @param userServerID         user identification
     * @param primaryTeamBIT       primary team bit
     * @param secondaryTeamBITS    secondary team bits
     * @param statusBITS           status bits
     * @param callback             call back
     */
    @Override
    public void readOrganizations(String organizationServerID, String userServerID,
                                  int primaryTeamBIT, List<Integer> secondaryTeamBITS,
                                  List<Integer> statusBITS, iReadOrganizationsCallback callback) {


        CollectionReference coOrganizationRef = db.collection(cRealtimeHelper.KEY_ORGANIZATIONS);

        Task<List<QuerySnapshot>> org_perm = cDatabaseUtils.applyReadPermissions(coOrganizationRef,
                organizationServerID, userServerID, primaryTeamBIT, secondaryTeamBITS);

        org_perm
                .addOnCompleteListener(task -> {
                    Set<cOrganizationModel> organizationSet = new HashSet<>();
                    for (QuerySnapshot result : Objects.requireNonNull(task.getResult())) {
                        for (QueryDocumentSnapshot ds : result) {
                            cOrganizationModel organizationModel = ds.toObject(
                                    cOrganizationModel.class);

                            if (statusBITS.contains(organizationModel.getStatusBIT())) {
                                organizationSet.add(organizationModel);

                                /*Log.d(TAG, "Organization name: " +
                                        organizationModel.getName() + ", email " +
                                        organizationModel.getEmail() + ", website " +
                                        organizationModel.getEmail());*/
                            }
                        }
                    }

                    ArrayList<cOrganizationModel> organizationModels;
                    organizationModels = new ArrayList<>(organizationSet);
                    callback.onReadOrganizationsSucceeded(organizationModels);
                })
                .addOnFailureListener(e -> {
                    callback.onReadOrganizationsFailed("Failed to read Organization.");
                    Log.w(TAG, "Failed to read value.", e);
                });
    }

    /**
     * read organization members
     *
     * @param organizationServerID organization identification
     * @param userServerID         user identification
     * @param primaryTeamBIT       primary team bit
     * @param secondaryTeamBITS    secondary team bits
     * @param statusBITS           status bits
     * @param callback             call back
     */
    @Override
    public void readOrganizationMembers(String organizationServerID, String userServerID,
                                        int primaryTeamBIT, List<Integer> secondaryTeamBITS,
                                        List<Integer> statusBITS,
                                        iReadOrganizationMembersCallback callback) {

        CollectionReference coUserAccountsRef = db.collection(cRealtimeHelper.KEY_USERACCOUNTS);

        Query userAccountQuery = coUserAccountsRef
                .whereEqualTo("organizationOwnerID", organizationServerID)
                .whereIn("statusBIT", statusBITS);

        userAccountQuery.get()
                .addOnCompleteListener(task -> {
                    Set<String> user_ids_set = new HashSet<>();
                    for (DocumentSnapshot useraccount : Objects.requireNonNull(task.getResult())) {
                        cUserAccountModel accountModel;
                        accountModel = useraccount.toObject(cUserAccountModel.class);

                        if (accountModel != null) {
                            cDatabaseUtils.cUnixPerm perm = new cDatabaseUtils.cUnixPerm();
                            perm.setUserOwnerID(accountModel.getUserOwnerID());
                            perm.setTeamOwnerBIT(accountModel.getTeamOwnerBIT());
                            perm.setUnixpermBITS(accountModel.getUnixpermBITS());

                            if (cDatabaseUtils.isPermitted(perm, userServerID, primaryTeamBIT,
                                    secondaryTeamBITS)) {
                                String userID = accountModel.getUserServerID();
                                user_ids_set.add(userID);
                            }
                        } else {
                            callback.onReadOrganizationMembersFailed(
                                    " due to user account entity!");
                        }
                    }

                    /* read organization members */
                    List<String> user_ids = new ArrayList<>(user_ids_set);
                    filterUserProfiles(user_ids, callback);

                })
                .addOnFailureListener(e -> callback.onReadOrganizationMembersFailed(
                        "Failed to read organization members"));
    }

    /**
     * filter user profiles
     *
     * @param user_ids list of user identifications
     * @param callback call back
     */
    private void filterUserProfiles(List<String> user_ids,
                                    iReadOrganizationMembersCallback callback) {

        CollectionReference coUserProfilesRef = db.collection(cRealtimeHelper.KEY_USERPROFILES);

        Query userProfileQuery = coUserProfilesRef
                .whereIn(FieldPath.documentId(), user_ids);

        userProfileQuery.get()
                .addOnCompleteListener(task -> {
                    List<cUserProfileModel> userProfileModels = new ArrayList<>();
                    for (QueryDocumentSnapshot doc : Objects.requireNonNull(task.getResult())) {
                        cUserProfileModel user = doc.toObject(cUserProfileModel.class);
                        userProfileModels.add(user);
                    }

                    // call back on organization members
                    callback.onReadOrganizationMembersSucceeded(userProfileModels);
                })
                .addOnFailureListener(e -> callback.onReadOrganizationMembersFailed(
                        "Failed to read organization members"));
    }

    /* ##################################### UPDATE ACTIONS ##################################### */


    /* ##################################### DELETE ACTIONS ##################################### */

}

//            Task ownerTask, primaryTask, secondaryTask, organizationTask;
//            /* 2. the member is the owner and the owner permission bit is on */
//            ownerTask = coOrganizationRef
//                    .whereEqualTo("organizationOwnerID", organizationServerID)
//                    .whereEqualTo("userOwnerID", userServerID)
//                    .whereArrayContains("unixpermBITS", cSharedPreference.OWNER_READ)
//                    .get();
//
//            /* 3. the member is in the primary team and the primary team permission bit is on */
//            primaryTask = coOrganizationRef
//                    .whereEqualTo("organizationOwnerID", organizationServerID)
//                    .whereEqualTo("teamOwnerBIT", primaryTeamBIT)
//                    .whereArrayContains("unixpermBITS", cSharedPreference.PRIMARY_READ)
//                    .get();
//
//            /* 4. the member is in the secondary team and the second team permission bit is on */
//            secondaryTask = coOrganizationRef
//                    .whereEqualTo("organizationOwnerID", organizationServerID)
//                    .whereIn("teamOwnerBIT", secondaryTeams)
//                    .whereArrayContains("unixpermBITS", cSharedPreference.SECONDARY_READ)
//                    .get();
//
//            /* 5. members is in the organization and the workplace permission bit is on */
//            organizationTask = coOrganizationRef
//                    .whereEqualTo("organizationOwnerID", organizationServerID)
//                    .whereArrayContains("unixpermBITS", cSharedPreference.WORKPLACE_READ)
//                    .get();
//
//            Task<List<QuerySnapshot>> allPerms = Tasks.whenAllSuccess(ownerTask, primaryTask,
//                    secondaryTask, organizationTask);

//
//
//            Task ownerTask, primaryTask, secondaryTask, organizationTask;
//
//            /* 2. the owner permission bit is on, and the member is the owner */
//            ownerTask = coUserAccountsRef
//                    .whereEqualTo("organizationOwnerID", organizationServerID)
//                    .whereEqualTo("userOwnerID", userServerID)
//                    .whereArrayContains("unixpermBITS", cSharedPreference.OWNER_READ)
//                    .whereIn("statusBIT", statusBITS)
//                    .get();
//
//            /* 3. the primary team permission bit is on, and the member is in the team */
//            primaryTask = coUserAccountsRef
//                    .whereEqualTo("organizationOwnerID", organizationServerID)
//                    .whereEqualTo("teamOwnerBIT", primaryTeamBIT)
//                    .whereArrayContains("unixpermBITS", cSharedPreference.PRIMARY_READ)
//                    .whereIn("statusBIT", statusBITS)
//                    .get();
//
//            /* 4. the second team permission bit is on, and the member is in the team */
//            secondaryTask = coUserAccountsRef
//                    .whereEqualTo("organizationOwnerID", organizationServerID)
//                    .whereIn("teamOwnerBIT", secondaryTeamBITS)
//                    .whereArrayContains("unixpermBITS", cSharedPreference.SECONDARY_READ)
//                    .get();
//
//        /* 5. the other permission bit is on -
//              all members of an organization have access */
//            organizationTask = coUserAccountsRef
//                    .whereEqualTo("organizationOwnerID", organizationServerID)
//                    .whereArrayContains("unixpermBITS", cSharedPreference.WORKPLACE_READ)
//                    .whereIn("statusBIT", statusBITS)
//                    .get();
//
//            Task<List<QuerySnapshot>> allPerms = Tasks.whenAllSuccess(ownerTask, primaryTask,
//                    secondaryTask, organizationTask);


//    /**
//     * create object and property operations
//     *
//     * @param organizationServerID  organization identification
//     * @param userServerID          user identification
//     * @param roleModel             role model
//     * @param privilegeModel        privilege model
//     * @param permissionModel       permission model
//     * @param commonPropertiesModel common property models
//     */
//    public void createPrivilegeLinks(String organizationServerID, String userServerID,
//                                     cRoleModel roleModel, cPrivilegeModel privilegeModel,
//                                     permissionModel,
//                                     cCommonPropertiesModel commonPropertiesModel) {
//
//        CollectionReference coPrivilegeRef, coRolePrivRef, coPrivPermsRef;
//
//        coPrivilegeRef = db.collection(cRealtimeHelper.KEY_PRIVILEGES);
//        coRolePrivRef = db.collection(cRealtimeHelper.KEY_ROLE_PRIVILEGES);
//        coPrivPermsRef = db.collection(cRealtimeHelper.KEY_PRIVILEGE_PERMISSIONS);
//        //coPrivUnixPermsRef = db.collection(cRealtimeHelper.KEY_PRIVILEGE_UNIXPERMS);
//
//        // creating admin privilege
//        String privilegeID = coPrivilegeRef.document().getId();
//        privilegeModel.setPrivilegeServerID(privilegeID);
//
//        // update role's common columns
//        privilegeModel.setOwnerID(userServerID);
//        privilegeModel.setOrgOwnerID(organizationServerID);
//        privilegeModel.setTeamOwnerBIT(commonPropertiesModel.getCteamOwnerBIT());
//        privilegeModel.setUnixpermsBITS(commonPropertiesModel.getCunixpermsBITS());
//        privilegeModel.setStatusesBITS(commonPropertiesModel.getCstatusesBITS());
//
//        // update default dates
//        Date currentDate = new Date();
//        privilegeModel.setCreatedDate(currentDate);
//        privilegeModel.setModifiedDate(currentDate);
//
//        coPrivilegeRef.document(privilegeID).set(privilegeModel);
//
//        // creating admin role privileges
//        Map<String, Object> role_privileges = new HashMap<>();
//        String roleID = roleModel.getRoleServerID();
//        role_privileges.put("roleServerID", roleID);
//        role_privileges.put("privilegeServerID", privilegeID);
//        coRolePrivRef.document(roleID + "_" + privilegeID).set(role_privileges);
//
//        // create operation statuses and unix permissions
//        coPrivPermsRef.document(privilegeID).set(permissionModel);
//    }


//        if (primaryTeamBIT == 1) {
//
//            /* 1. members of the 'admin' team are always allowed to do everything */
//            Query adminQuery = coOrganizationRef
//                    .whereEqualTo("organizationOwnerID", organizationServerID)
//                    .whereEqualTo("teamOwnerBIT", 1)
//                    .whereIn("statusBIT", statusBITS);
//
//            adminQuery.get()
//                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                        @Override
//                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                            List<cOrganizationModel> organizationModels = new ArrayList<>();
//                            for (QueryDocumentSnapshot org_doc : Objects.requireNonNull(
//                                    task.getResult())) {
//
//                                cOrganizationModel organizationModel = org_doc.toObject(
//                                        cOrganizationModel.class);
//
//                                organizationModels.add(organizationModel);
//                                /*Log.d(TAG, "Organization name: " +
//                                        organizationModel.getName() + ", email " +
//                                        organizationModel.getEmail() + ", website " +
//                                        organizationModel.getEmail());*/
//
//                            }
//                            callback.onReadOrganizationsSucceeded(organizationModels);
//                        }
//                    })
//                    .addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            callback.onReadOrganizationsFailed("Failed to read Organization." + e);
//                            Log.w(TAG, "Failed to read value.", e);
//                        }
//                    });
//        } else {
//
//            Task<List<QuerySnapshot>> allPerms = cDatabaseUtils.applyReadPermissions(
//                    coOrganizationRef, userServerID, organizationServerID, primaryTeamBIT,
//                    secondaryTeamBITS);
//
//            allPerms
//                    .addOnCompleteListener(new OnCompleteListener<List<QuerySnapshot>>() {
//                        @Override
//                        public void onComplete(@NonNull Task<List<QuerySnapshot>> task) {
//                            Set<cOrganizationModel> organizationSet = new HashSet<>();
//                            for (QuerySnapshot result : Objects.requireNonNull(task.getResult())) {
//                                for (QueryDocumentSnapshot ds : result) {
//                                    cOrganizationModel organizationModel = ds.toObject(
//                                            cOrganizationModel.class);
//
//                                    if (statusBITS.contains(organizationModel.getStatusBIT())) {
//                                        organizationSet.add(organizationModel);
//
//                                    /*Log.d(TAG, "Organization name: " +
//                                            organizationModel.getName() + ", email " +
//                                            organizationModel.getEmail() + ", website " +
//                                            organizationModel.getEmail());*/
//                                    }
//                                }
//                            }
//
//                            ArrayList<cOrganizationModel> organizationModels;
//                            organizationModels = new ArrayList<>(organizationSet);
//                            callback.onReadOrganizationsSucceeded(organizationModels);
//                        }
//                    })
//                    .addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            callback.onReadOrganizationsFailed("Failed to read Organization." + e);
//                            Log.w(TAG, "Failed to read value.", e);
//                        }
//                    });
//        }
//        if (primaryTeamBIT == 1) {
//
//            /* 1. members of the 'Administrator' team are always allowed to do everything */
//            Query userAccountQuery = coUserAccountsRef
//                    .whereEqualTo("organizationOwnerID", organizationServerID)
//                    .whereIn("statusBIT", statusBITS);
//
//            userAccountQuery.get()
//                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                        @Override
//                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                            List<String> user_ids = new ArrayList<>();
//                            for (DocumentSnapshot useraccount : Objects.requireNonNull(task.getResult())) {
//                                cUserAccountModel user;
//                                user = useraccount.toObject(cUserAccountModel.class);
//
//                                if (user != null) {
//                                    String userID = user.getUserServerID();
//                                    user_ids.add(userID);
//                                } else {
//                                    callback.onReadOrganizationMembersFailed(" due to user account entity!");
//                                }
//                            }
//
//                            /* read organization members */
//                            readUserProfiles(user_ids, callback);
//                        }
//                    })
//                    .addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            callback.onReadOrganizationMembersFailed(
//                                    "Failed to read organization members");
//                        }
//                    });
//        } else {
//
//            Task<List<QuerySnapshot>> allPerms = cDatabaseUtils.applyReadPermissions(
//                    coUserAccountsRef, userServerID, organizationServerID, primaryTeamBIT,
//                    secondaryTeamBITS);
//
//            allPerms
//                    .addOnCompleteListener(new OnCompleteListener<List<QuerySnapshot>>() {
//                        @Override
//                        public void onComplete(@NonNull Task<List<QuerySnapshot>> task) {
//
//                            Set<String> user_ids_set = new HashSet<>();
//                            for (QuerySnapshot result : Objects.requireNonNull(task.getResult())) {
//
//                                for (DocumentSnapshot useraccount : Objects.requireNonNull(result)) {
//                                    cUserAccountModel user;
//                                    user = useraccount.toObject(cUserAccountModel.class);
//
//                                    if (user != null) {
//                                        if (statusBITS.contains(user.getStatusBIT())) {
//                                            String userID = user.getUserServerID();
//                                            user_ids_set.add(userID);
//                                        } else {
//                                            callback.onReadOrganizationMembersFailed(
//                                                    " due to user account entity!");
//                                        }
//                                    }
//                                }
//                            }
//
//                            /* read organization members */
//                            List<String> user_ids = new ArrayList<>(user_ids_set);
//                            readUserProfiles(user_ids, callback);
//                        }
//                    })
//                    .addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            callback.onReadOrganizationMembersFailed("Failed to read Organization." + e);
//                            Log.w(TAG, "Failed to read value.", e);
//                        }
//                    });
//        }
//}
//    /**
//     * role menu items
//     *
//     * @param roleModel role model
//     */
//    public void createMenuItemLinks(cRoleModel roleModel) {
//        // create menu items of the related roles
//        CollectionReference coOrgRoleMenuRef;
//        // add menu items related to admin role of the organization
//        coOrgRoleMenuRef = db.collection(cRealtimeHelper.KEY_ROLE_MENU_ITEMS);
//
//        Map<cMenuModel, Map<String, cMenuModel>> modelMapHashMap = cDatabaseUtils.
//                getAdminMenuModelSet(context);
//
//        Map<String, Object> role_menus = new HashMap<>();
//        for (Map.Entry<cMenuModel, Map<String, cMenuModel>> entry : modelMapHashMap.entrySet()) {
//            cMenuModel menuModel = entry.getKey();
//            String menuServerID = String.valueOf(menuModel.getMenuServerID());
//
//            // create sub menu items
//            Map<String, cMenuModel> subMenuModelMap = entry.getValue();
//            List<Integer> sub_menu = new ArrayList<>();
//            if (subMenuModelMap.size() > 0) {
//                for (Map.Entry<String, cMenuModel> subEntry : subMenuModelMap.entrySet()) {
//                    sub_menu.add(subEntry.getValue().getMenuServerID());
//                }
//            }
//
//            // add menu items to the role
//            role_menus.put(menuServerID, sub_menu);
//        }
//
//        String roleServerID = roleModel.getRoleServerID();
//        coOrgRoleMenuRef.document(roleServerID).set(role_menus);
//    }
