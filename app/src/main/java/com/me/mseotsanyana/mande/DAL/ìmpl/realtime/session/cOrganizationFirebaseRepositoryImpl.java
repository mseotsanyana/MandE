package com.me.mseotsanyana.mande.DAL.ìmpl.realtime.session;

import androidx.annotation.NonNull;

import android.content.Context;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.me.mseotsanyana.mande.BLL.model.session.cMenuModel;
import com.me.mseotsanyana.mande.BLL.model.session.cOrganizationModel;
import com.me.mseotsanyana.mande.BLL.model.session.cPlanModel;
import com.me.mseotsanyana.mande.BLL.model.session.cRoleModel;
import com.me.mseotsanyana.mande.BLL.model.session.cTeamModel;
import com.me.mseotsanyana.mande.BLL.model.session.cUserAccountModel;
import com.me.mseotsanyana.mande.BLL.model.utils.cCommonPropertiesModel;
import com.me.mseotsanyana.mande.BLL.repository.session.iOrganizationRepository;
import com.me.mseotsanyana.mande.DAL.storage.database.cRealtimeHelper;
import com.me.mseotsanyana.mande.DAL.ìmpl.cDatabaseUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by mseotsanyana on 2016/10/23.
 */
public class cOrganizationFirebaseRepositoryImpl implements iOrganizationRepository {
    private static final String TAG = cOrganizationFirebaseRepositoryImpl.class.getSimpleName();

    // an object of the database helper
    private final FirebaseDatabase database;
    private final Context context;

    public cOrganizationFirebaseRepositoryImpl(Context context) {
        this.database = FirebaseDatabase.getInstance();
        this.context = context;
    }

    /* ############################################# CREATE ACTIONS ############################################# */

    @Override
    public void createOrganization(cOrganizationModel organizationModel,
                                   iCreateOrganizationCallback callback) {

        // get current logged in user
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        assert currentUser != null;
        String userID = currentUser.getUid();
        // get new organization id
        DatabaseReference dbOrganizationRef = database.getReference(cRealtimeHelper.KEY_ORGANIZATIONS);
        String organizationServerID = dbOrganizationRef.push().getKey();
        assert organizationServerID != null;
        // get common columns' default values
        cCommonPropertiesModel commonPropertiesModel = cDatabaseUtils.getCommonModel(context);
        // update organization model with auto generated id
        organizationModel.setOrganizationServerID(organizationServerID);
        // update organization model with default values
        organizationModel.setUserOwnerID(userID);
        organizationModel.setOrganizationOwnerID(organizationServerID);
        organizationModel.setTeamOwnerBIT(commonPropertiesModel.getCteamOwnerBIT());
        organizationModel.setUnixpermBITS(commonPropertiesModel.getCunixpermBITS());
        organizationModel.setStatusBIT(commonPropertiesModel.getCstatusBIT());

        dbOrganizationRef.child(organizationServerID).setValue(organizationModel, (dbError, dbReference) -> {
            if (dbError == null) {
                DatabaseReference dbTypeOfOrganizationRef;
                switch (organizationModel.getOrganizationType()) {
                    case 0:
                        dbTypeOfOrganizationRef = database.getReference(cRealtimeHelper.KEY_BENEFACTORS);
                        dbTypeOfOrganizationRef.child(organizationServerID).setValue(true);
                        dbTypeOfOrganizationRef = database.getReference(cRealtimeHelper.KEY_NATIONAL_PARTNERS);
                        dbTypeOfOrganizationRef.child(organizationServerID).setValue(true);
                        break;
                    case 1:
                        dbTypeOfOrganizationRef = database.getReference(cRealtimeHelper.KEY_BENEFACTORS);
                        dbTypeOfOrganizationRef.child(organizationServerID).setValue(true);
                        dbTypeOfOrganizationRef = database.getReference(cRealtimeHelper.KEY_DONORS);
                        dbTypeOfOrganizationRef.child(organizationServerID).setValue(true);
                        break;
                    case 2:
                        dbTypeOfOrganizationRef = database.getReference(cRealtimeHelper.KEY_BENEFICIARIES);
                        dbTypeOfOrganizationRef.child(organizationServerID).setValue(true);
                        break;
                    case 3:
                        dbTypeOfOrganizationRef = database.getReference(cRealtimeHelper.KEY_IMPLEMENTING_AGENCIES);
                        dbTypeOfOrganizationRef.child(organizationServerID).setValue(true);
                        break;
                    default:
                        Log.d(TAG, "Error in creating an organization");
                }

                /* default settings */
                cPlanModel defaultPlanModel = cDatabaseUtils.getDefaultPlanModel(context);
                cTeamModel teamModel = cDatabaseUtils.getAdminTeamModel(context, organizationServerID, commonPropertiesModel);
                cRoleModel roleModel = cDatabaseUtils.getAdminRoleModel(context, commonPropertiesModel);
//                cPrivilegeModel privilegeModel = cDatabaseUtils.getAdminPrivilegeModel(context, commonPropertiesModel);
//                Pair<cPermissionModel, List<cUnixPermissionModel>> permissions = cDatabaseUtils.
//                        getAdminPermissions(context);

                /* create links */
                createOrganizationLinks(organizationServerID, userID);
                createUserAccounts(organizationServerID, userID, teamModel, defaultPlanModel);
                createTeamLinks(organizationServerID, userID, teamModel, commonPropertiesModel);
                createRoleLinks(teamModel, roleModel, commonPropertiesModel);
                createMenuItemLinks(roleModel);
                //createPrivilegeLinks(roleModel, privilegeModel, permissions);

                callback.onCreateOrganizationSucceeded("Organization saved successfully.");
            } else {
                callback.onCreateOrganizationFailed("Organization could not be saved " +
                        dbError.getMessage());
            }
        });
    }

    @Override
    public void readOrganizations(String userServerID, String orgServerID, int primaryTeamBIT, List<Integer> secondaryTeams, List<Integer> statuses, iReadOrganizationsCallback callback) {

    }

    @Override
    public void readOrganizationMembers(String userServerID, String orgServerID, int primaryTeamBIT, List<Integer> secondaryTeams, List<Integer> statuses, iReadOrganizationMembersCallback callback) {

    }

    public void createOrganizationLinks(String organizationID, String userID) {
        DatabaseReference dbOrgMembersRef, dbMemberOrgsRef;
        // add the current user to the organization just created
        dbOrgMembersRef = database.getReference(cRealtimeHelper.KEY_ORGANIZATION_MEMBERS);
        dbMemberOrgsRef = database.getReference(cRealtimeHelper.KEY_MEMBER_ORGANIZATIONS);
        dbOrgMembersRef.child(organizationID).child(userID).setValue(true);
        dbMemberOrgsRef.child(userID).child(organizationID).setValue(true);
    }

    public void createUserAccounts(String organizationID, String userID,
                                   cTeamModel teamModel, cPlanModel planModel) {
        DatabaseReference dbUserAccountsRef;
        // create a user account for the user in an organization just created
        dbUserAccountsRef = database.getReference(cRealtimeHelper.KEY_USERACCOUNTS);

        cUserAccountModel userAccountModel = new cUserAccountModel();
        userAccountModel.setUserAccountServerID(organizationID + "_" + userID);
        userAccountModel.setOrganizationServerID(organizationID);
        userAccountModel.setTeamServerID(teamModel.getTeamServerID());
        userAccountModel.setUserServerID(userID);
        userAccountModel.setPlanServerID(planModel.getPlanServerID());
        userAccountModel.setCurrentOrganization(false);
        dbUserAccountsRef.child(organizationID + "_" + userID).setValue(userAccountModel);

        // set the account just created as the current active user account
        Query userAccountQuery = dbUserAccountsRef.orderByChild("userServerID").equalTo(userID);
        userAccountQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot useraccount : snapshot.getChildren()) {
                    cUserAccountModel account = useraccount.getValue(cUserAccountModel.class);

                    assert account != null;
                    String orgID = account.getOrganizationServerID();

                    assert orgID != null;
                    if(orgID.equals(organizationID)) {
                        useraccount.getRef().child("currentOrganization").setValue(true);
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(TAG, "Failed to update user accounts: ", error.toException());
            }
        });
    }

    public void createTeamLinks(String organizationID, String userServerID, cTeamModel teamModel,
                                cCommonPropertiesModel columnModel) {
        // create a team in an organization just created
        DatabaseReference dbTeamsRef = database.getReference(cRealtimeHelper.KEY_TEAMS);
        String compositeServerID = teamModel.getCompositeServerID();

        // update team's common columns
        teamModel.setUserOwnerID(columnModel.getCownerID());
        teamModel.setOrganizationOwnerID(columnModel.getCorgOwnerID());
        teamModel.setTeamOwnerBIT(columnModel.getCteamOwnerBIT());
        teamModel.setUnixpermBITS(columnModel.getCunixpermBITS());
        teamModel.setStatusBIT(columnModel.getCstatusBIT());

        dbTeamsRef.child(compositeServerID).setValue(teamModel);

        // add the current user to the team of the organization
        DatabaseReference dbTeamMembersRef, dbMemberTeamsRef;
        dbTeamMembersRef = database.getReference(cRealtimeHelper.KEY_TEAM_MEMBERS);
        dbMemberTeamsRef = database.getReference(cRealtimeHelper.KEY_MEMBER_TEAMS);

        dbTeamMembersRef.child(compositeServerID).child(organizationID + "_" + userServerID).
                child("organizationServerID").setValue(organizationID);
        dbTeamMembersRef.child(compositeServerID).child(organizationID + "_" + userServerID).
                child("userServerID").setValue(userServerID);

        dbMemberTeamsRef.child(organizationID + "_" + userServerID).child(compositeServerID).
                child("organizationServerID").setValue(organizationID);
        String teamServerID = teamModel.getTeamServerID();
        dbMemberTeamsRef.child(organizationID + "_" + userServerID).child(compositeServerID).
                child("teamServerID").setValue(teamServerID);
    }

    public void createRoleLinks(cTeamModel teamModel, cRoleModel roleModel, cCommonPropertiesModel columnModel) {
        // create s role of the organization just created
        DatabaseReference dbRoleRef = database.getReference(cRealtimeHelper.KEY_ROLES);
        String roleID = dbRoleRef.push().getKey();
        String compositeTeamID = teamModel.getCompositeServerID();

        assert roleID != null;
        roleModel.setRoleServerID(roleID);

        // update role's common columns
        roleModel.setUserOwnerID(columnModel.getCownerID());
        roleModel.setOrganizationOwnerID(columnModel.getCorgOwnerID());
        roleModel.setTeamOwnerBIT(columnModel.getCteamOwnerBIT());
        roleModel.setUnixpermBITS(columnModel.getCunixpermBITS());
        roleModel.setStatusBIT(columnModel.getCstatusBIT());

        dbRoleRef.child(roleID).setValue(roleModel);

        // add admin role to the user of the organization
        DatabaseReference dbTeamRolesRef, dbRoleTeamsRef;
        dbTeamRolesRef = database.getReference(cRealtimeHelper.KEY_TEAM_ROLES);
        dbRoleTeamsRef = database.getReference(cRealtimeHelper.KEY_ROLE_TEAMS);
        dbTeamRolesRef.child(compositeTeamID).child(roleID).setValue(true);
        dbRoleTeamsRef.child(roleID).child(compositeTeamID).setValue(true);
    }

    public void createMenuItemLinks(cRoleModel roleModel) {
        // create menu items of the related roles
        DatabaseReference dbOrgRoleMenuRef;
        // add menu items related to admin role of the organization
        dbOrgRoleMenuRef = database.getReference(cRealtimeHelper.KEY_ROLE_PERMISSIONS);

        Map<cMenuModel, Map<String, cMenuModel>> modelMapHashMap = cDatabaseUtils.
                getAdminMenuModelSet(context);
        for (Map.Entry<cMenuModel, Map<String, cMenuModel>> entry : modelMapHashMap.entrySet()) {
            cMenuModel menuModel = entry.getKey();
            Map<String, cMenuModel> subMenuModelMap = entry.getValue();

            // create sub menu items
            if (subMenuModelMap.size() > 0) {

                ArrayList<cMenuModel> subMenuModels = new ArrayList<>();
                for (Map.Entry<String, cMenuModel> subEntry : subMenuModelMap.entrySet()) {
                    subMenuModels.add(subEntry.getValue());
                }
                menuModel.setSubmenu(subMenuModels);
            }

            // add menu items related to admin role of the organization
            dbOrgRoleMenuRef.child(roleModel.getRoleServerID()).child(
                    String.valueOf(menuModel.getMenuServerID())).setValue(menuModel);
        }
    }

//    public void createPrivilegeLinks(cRoleModel roleModel, cPrivilegeModel privilegeModel,
//                                     Pair<List<cPermissionModel>, List<cPermissionModel>> permissions) {
//        DatabaseReference dbPrivilegeRef, dbRolePrivRef, dbPrivPermsRef, dbPrivUnixPermsRef;
//
//        dbPrivilegeRef = database.getReference(cRealtimeHelper.KEY_PRIVILEGES);
//        dbRolePrivRef = database.getReference(cRealtimeHelper.KEY_ROLE_PRIVILEGES);
//        dbPrivPermsRef = database.getReference(cRealtimeHelper.KEY_PRIVILEGE_PERMISSIONS);
//        dbPrivUnixPermsRef = database.getReference(cRealtimeHelper.KEY_PRIVILEGE_UNIXPERMS);
//
//        // creating admin privilege
//        String privilegeID = dbPrivilegeRef.push().getKey();
//        assert privilegeID != null;
//        privilegeModel.setPrivilegeServerID(privilegeID);
//        dbPrivilegeRef.child(privilegeID).setValue(privilegeModel);
//
//        // creating admin role privileges
//        dbRolePrivRef.child(roleModel.getRoleServerID()).child(privilegeID).setValue(true);
//
//        List<cPermissionModel> permissionModels = permissions.first;
//        List<cPermissionModel> unixPermissionModels = permissions.second;
//
//        /* permissions which are used to control access at table level */
//        for (int i = 0; i < permissionModels.size(); i++) {
//            // creating permissions
////            String entityID = permissionModels.get(i).getEntityServerID();
////            String entityTypeID = permissionModels.get(i).getModuleServerID();
//            String operationID = "";//permissionModels.get(i).getOperationServerID();
//
////            dbPrivPermsRef.child(privilegeID).child(entityTypeID).
////                    child(entityID).child(operationID).setValue(0);
//        }
//
//        // create unix permissions for each entity - they put access control on rows
//        for (int j = 0; j < unixPermissionModels.size(); j++) {
////            String entityID = unixPermissionModels.get(j).getEntityServerID();
////            String entityTypeID = unixPermissionModels.get(j).getModuleServerID();
////            String unixOperationID = "";//unixPermissionModels.get(j).getUnixOperationServerID();
////
////            dbPrivUnixPermsRef.child(privilegeID).child(entityTypeID).
////                    child(entityID).child(unixOperationID).setValue(true);
//        }
//    }

    /* ############################################# READ ACTIONS ############################################# */

//    @Override
//    public void readOrganizations(iReadOrganizationCallback callback) {
//        /* read an organization of the current loggedIn user */
//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//        if (user != null) {
//            DatabaseReference dbUserRef;
//            dbUserRef = database.getReference(cRealtimeHelper.KEY_USERACCOUNTS);
//            Query query = dbUserRef.orderByChild("userServerID").equalTo(user.getUid());
//
//            query.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot snapshot) {
//                    List<cOrganizationModel> organizationModels = new ArrayList<>();
//                    for (DataSnapshot datas : snapshot.getChildren()) {
//                        String organizationKey = Objects.requireNonNull(
//                                datas.child("organizationServerID").getValue()).toString();
//
//                        DatabaseReference dbOrganizationsRef = database.getReference(
//                                cRealtimeHelper.KEY_ORGANIZATIONS);
//                        dbOrganizationsRef.child(organizationKey).addValueEventListener(new ValueEventListener() {
//                            @Override
//                            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                                cOrganizationModel organizationModel = snapshot.getValue(cOrganizationModel.class);
//                                organizationModels.add(organizationModel);
//                            }
//
//                            @Override
//                            public void onCancelled(@NonNull DatabaseError error) {
//                                callback.onReadOrganizationFailed("Failed to read Organization. " +
//                                        error.toException());
//                                Log.w(TAG, "Failed to read value.", error.toException());
//                            }
//                        });
//                    }
//                    callback.onReadOrganizationSucceeded(organizationModels);
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError error) {
//                    callback.onReadOrganizationFailed("Failed to read User Account." + error.toException());
//                    Log.w(TAG, "Failed to read value.", error.toException());
//                }
//            });
//        }
//    }
//
//    @Override
//    public Set<cOrganizationModel> getOrganizationSet() {
//        return null;
//    }

//    @Override
//    public void readOrganizations(String userServerID, String orgServerID, int primaryTeamBIT,
//                                  List<Integer> secondaryTeamBITS, int statusBITS, int unixpermBITS,
//                                  iReadOrganizationsModelSetCallback callback) {
//        DatabaseReference dbReference = database.getReference(cRealtimeHelper.KEY_ORGANIZATIONS);
//        dbReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                ArrayList<cOrganizationModel> organizationModels = new ArrayList<>();
//                for (DataSnapshot ds : dataSnapshot.getChildren()) {
//                    cOrganizationModel organizationModel = ds.getValue(cOrganizationModel.class);
//                    organizationModels.add(organizationModel);
//
//                    assert organizationModel != null;
//                    Log.d(TAG, "Organization name: " + organizationModel.getName() + ", email " +
//                            organizationModel.getEmail() + ", website " + organizationModel.getEmail());
//                }
//
//                callback.onReadOrganizationsSucceeded(organizationModels);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                callback.onReadOrganizationsFailed("Failed to read Organization." + error.toException());
//                Log.w(TAG, "Failed to read value.", error.toException());
//            }
//        });
//    }

    /* ############################################# UPDATE ACTIONS ############################################# */


    /* ############################################# DELETE ACTIONS ############################################# */

}

//=== START
    /*public List<cPlanModel> getPlanModels() {
        List<cPlanModel> planModels = new ArrayList<>();
        // read json file
        String plans = "jsons/sys_plans.json";
        String planJSONString = cDatabaseUtils.loadJSONFromAsset(plans, context.getAssets());

        try {
            assert planJSONString != null;
            JSONObject jsonObjectPlan = new JSONObject(planJSONString);
            JSONArray jsonPlans = jsonObjectPlan.getJSONArray("plans");

            for (int i = 0; i < jsonPlans.length(); i++) {
                cPlanModel planModel = new cPlanModel();

                JSONObject jsonPlan = jsonPlans.getJSONObject(i);

                planModel.setPlanServerID(jsonPlan.getString("id"));
                planModel.setName(jsonPlan.getString("name"));
                planModel.setDescription(jsonPlan.getString("description"));

                planModel.setOrgLimit(jsonPlan.getInt("max_org_limit"));
                planModel.setTeamLimit(jsonPlan.getInt("max_team_limit"));
                planModel.setOrgUserLimit(jsonPlan.getInt("max_org_user_limit"));
                planModel.setTeamUserLimit(jsonPlan.getInt("max_team_user_limit"));

                planModels.add(planModel);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return planModels;
    }

    public List<cFeatureModel> getFeatureModels() {
        List<cFeatureModel> featureModels = new ArrayList<>();
        // read json file
        String features = "jsons/sys_features.json";
        String featureJSONString = cDatabaseUtils.loadJSONFromAsset(features, context.getAssets());

        try {
            assert featureJSONString != null;
            JSONObject jsonObjectFeature = new JSONObject(featureJSONString);
            JSONArray jsonFeatures = jsonObjectFeature.getJSONArray("features");

            for (int i = 0; i < jsonFeatures.length(); i++) {
                cFeatureModel featureModel = new cFeatureModel();

                JSONObject jsonFeature = jsonFeatures.getJSONObject(i);

                featureModel.setFeatureServerID(jsonFeature.getInt("id"));
                featureModel.setName(jsonFeature.getString("name"));
                featureModel.setDescription(jsonFeature.getString("description"));

                featureModels.add(featureModel);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return featureModels;
    }

    public HashMap<String, Set<Integer>> getPlanFeatureModelSet() {
        HashMap<String, Set<Integer>> planFeatures = new HashMap<>();

        String plan = "jsons/sys_features.json";
        String planJSONString = cDatabaseUtils.loadJSONFromAsset(plan, context.getAssets());

        try {
            assert planJSONString != null;
            JSONObject jsonObjectPlan = new JSONObject(planJSONString);
            JSONArray jsonArrayPlan = jsonObjectPlan.getJSONArray("plans");
            for (int i = 0; i < jsonArrayPlan.length(); i++) {
                JSONObject jsonObject = jsonArrayPlan.getJSONObject(i);
                String planID = jsonObject.getString("id");

                JSONArray jsonArrayFeature = jsonObject.getJSONArray("features");
                Set<Integer> featureSet = new HashSet<>();
                for (int j = 0; j < jsonArrayFeature.length(); j++) {
                    JSONObject jsonObjectFeature = jsonArrayFeature.getJSONObject(j);
                    featureSet.add(jsonObjectFeature.getInt("id"));
                }

                planFeatures.put(planID, featureSet);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return planFeatures;
    }*/
//=== END
    /*
        public cPermissionModel getAdminPermissionModel() {
            cPermissionModel permissionModel = new cPermissionModel();

            // read json file
            String perm = "jsons/admin_perms.json";
            String permJSONString = cDatabaseUtils.loadJSONFromAsset(perm, context.getAssets());

            //Log.d(TAG, roleJSONString);
            try {
                assert permJSONString != null;
                JSONObject jsonObjectPerm = new JSONObject(permJSONString);
                JSONObject jsonPermission = jsonObjectPerm.getJSONObject("permission");
                permissionModel.setName(jsonPermission.getString("name"));
                permissionModel.setDescription(jsonPermission.getString("description"));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return permissionModel;
        }
    */

// public void createOrganizationLinks(String organizationID, String userID, cPlanModel planModel,
//                                      cTeamModel teamModel, cRoleModel roleModel, cColumnModel columnModel) {
//      DatabaseReference dbUserAccountRef, dbTeamRef, dbRoleRef, dbMemberOrgRef, dbOrgUserRef,
//              dbUserRoleRef, dbOrgMemberRef, dbTeamMemberRef, dbMenuItemRef, dbOrgRoleMenuRef;

// add the current user to the organization just created
        /*dbOrgMemberRef = database.getReference(cRealtimeHelper.KEY_ORGANIZATION_MEMBERS);
        dbMemberOrgRef = database.getReference(cRealtimeHelper.KEY_MEMBER_ORGANIZATIONS);
        dbOrgMemberRef.child(organizationID).child(userID).setValue(true);
        dbMemberOrgRef.child(userID).child(organizationID).setValue(true);*/

// create a user account for the user in an organization just created
        /*dbUserAccountRef = database.getReference(cRealtimeHelper.KEY_USER_ACCOUNTS);
        dbUserAccountRef.child(organizationID+"_"+userID).child("organization").setValue(organizationID);
        dbUserAccountRef.child(organizationID+"_"+userID).child("team").setValue(teamModel.getTeamServerID());
        dbUserAccountRef.child(organizationID+"_"+userID).child("user").setValue(userID);
        dbUserAccountRef.child(organizationID+"_"+userID).child("plan").setValue(planModel.getPlanServerID());*/

//dbOrgMemberRef.child(orgMemberID).child(organizationID).setValue(true);
//dbOrgMemberRef.child(orgMemberID).child(userID).setValue(true);

//== create default team of the organization
        /*dbTeamRef = database.getReference(cRealtimeHelper.KEY_TEAMS);
        String teamID = teamModel.getTeamServerID();
        // update team's common columns
        teamModel.setOwnerID(columnModel.getCownerID());
        teamModel.setOrgOwnerID(columnModel.getCorgOwnerID());
        teamModel.setTeamOwnerBIT(columnModel.getCteamOwnerBIT());
        teamModel.setUnixpermsBITS(columnModel.getCunixpermsBITS());
        teamModel.setStatusesBITS(columnModel.getCstatusesBITS());

        dbTeamRef.child(organizationID + "_" + teamID).setValue(teamModel);

        // add the current user to the team of the organization
        dbTeamMemberRef = database.getReference(cRealtimeHelper.KEY_TEAM_MEMBERS);
        dbTeamMemberRef.child(organizationID + "_" + teamID).child(userID).setValue(true);*/

//== create premium plan for the admin role
        /*dbPlanRef = database.getReference(cRealtimeHelper.KEY_PLANS);
        dbPlanRef.child(String.valueOf(planModel.getPlanServerID())).setValue(planModel);*/

//== create role of the organization
        /*dbRoleRef = database.getReference(cRealtimeHelper.KEY_ROLES);
        String roleID = dbRoleRef.push().getKey();
        assert roleID != null;
        roleModel.setRoleServerID(roleID);
        roleModel.setOrgServerID(organizationID);
        //roleModel.setPlanServerID(planModel.getPlanServerID());
        // update role's common columns
        roleModel.setOwnerID(columnModel.getCownerID());
        roleModel.setOrgOwnerID(columnModel.getCorgOwnerID());
        roleModel.setTeamOwnerBIT(columnModel.getCteamOwnerBIT());
        roleModel.setUnixpermsBITS(columnModel.getCunixpermsBITS());
        roleModel.setStatusesBITS(columnModel.getCstatusesBITS());

        dbRoleRef.child(roleID).setValue(roleModel);

        // add admin role to the user of the organization
        dbUserRoleRef = database.getReference(cRealtimeHelper.KEY_USER_ROLES);
        dbUserRoleRef.child(organizationID+"_"+userID).child(roleID).setValue(true);*/

// create menu items
        /*dbMenuItemRef = database.getReference(cRealtimeHelper.KEY_MENU_ITEMS);

        // add menu items related to admin role of the organization
        dbOrgRoleMenuRef = database.getReference(cRealtimeHelper.KEY_ROLE_MENU_ITEMS);

        //List<cMenuModel> menuModels = new ArrayList<>();
        HashMap<cMenuModel, Map<String, cMenuModel>> modelMapHashMap = cDatabaseUtils.getAdminMenuModelSet(context, columnModel);
        for (Map.Entry<cMenuModel, Map<String, cMenuModel>> entry : modelMapHashMap.entrySet()) {
            cMenuModel menuModel = entry.getKey();
            Map<String, cMenuModel> subMenuModelMap = entry.getValue();

            // create main menu
            dbMenuItemRef.child(String.valueOf(menuModel.getMenuServerID())).setValue(menuModel);

            // create sub menu items
            if (subMenuModelMap.size() > 0) {
                dbMenuItemRef.child(String.valueOf(menuModel.getMenuServerID())).
                        child(cRealtimeHelper.KEY_SUB_MENU_ITEMS).setValue(subMenuModelMap);

                ArrayList<cMenuModel> subMenuModels = new ArrayList<>();
                for (Map.Entry<String, cMenuModel> subEntry : subMenuModelMap.entrySet()){
                    subMenuModels.add(subEntry.getValue());
                }

                menuModel.setSubMenuModels(subMenuModels);
            }

            /* add parent menu in the list
            //menuModels.add(menuModel);

            // add menu items related to admin role of the organization
            dbOrgRoleMenuRef.child(roleModel.getRoleServerID()).child(
                    String.valueOf(menuModel.getMenuServerID())).setValue(menuModel);

        }

        for(cMenuModel menuModel: menuModels){
            String roleID = roleModel.getServerID();
            String menuID = menuModel.getServerID();
            dbReference.child(organizationID+"_"+roleID+"_"+menuID).setValue(true);
        }
    }*/
