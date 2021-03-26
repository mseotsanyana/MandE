package com.me.mseotsanyana.mande.DAL.ìmpl.firebase.session;

import androidx.annotation.NonNull;

import android.content.Context;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.me.mseotsanyana.mande.BLL.model.session.cFeatureModel;
import com.me.mseotsanyana.mande.BLL.model.session.cMenuModel;
import com.me.mseotsanyana.mande.BLL.model.session.cOrganizationModel;
import com.me.mseotsanyana.mande.BLL.model.session.cPermissionModel;
import com.me.mseotsanyana.mande.BLL.model.session.cPlanModel;
import com.me.mseotsanyana.mande.BLL.model.session.cPrivilegeModel;
import com.me.mseotsanyana.mande.BLL.model.session.cRoleModel;
import com.me.mseotsanyana.mande.BLL.model.session.cTeamModel;
import com.me.mseotsanyana.mande.BLL.model.utils.cColumnModel;
import com.me.mseotsanyana.mande.BLL.repository.session.iOrganizationRepository;
import com.me.mseotsanyana.mande.DAL.storage.database.cRealtimeHelper;
import com.me.mseotsanyana.mande.DAL.ìmpl.cDatabaseUtils;
import com.me.mseotsanyana.mande.UTIL.cConstant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by mseotsanyana on 2016/10/23.
 */
public class cOrganizationFirebaseRepositoryImpl implements iOrganizationRepository {
    private static SimpleDateFormat sdf = cConstant.FORMAT_DATE;
    private static String TAG = cOrganizationFirebaseRepositoryImpl.class.getSimpleName();

    // an object of the database helper
    private final FirebaseDatabase database;
    private DatabaseReference dbReference;
    private final Context context;

    public cOrganizationFirebaseRepositoryImpl(Context context) {
        //FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        this.context = context;
    }

    /* ############################################# CREATE ACTIONS ############################################# */

    @Override
    public void createOrganization(cOrganizationModel organizationModel,
                                   iOrganizationRepositoryCallback callback) {

        // get current logged in user
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        assert currentUser != null;
        String userID = currentUser.getUid();
        // get new organization id
        dbReference = database.getReference(cRealtimeHelper.KEY_ORGANIZATIONS);
        String organizationID = dbReference.push().getKey();
        assert organizationID != null;
        // get common columns' default values
        cColumnModel columnModel = getColumnModel(organizationID, userID);
        // update organization model with auto generated id
        organizationModel.setOrgServerID(organizationID);
        // update organization model with default values
        organizationModel.setOwnerID(userID);
        organizationModel.setOrgOwnerID(columnModel.getCorgOwnerID());
        organizationModel.setTeamOwnerBIT(columnModel.getCteamOwnerBIT());
        organizationModel.setUnixpermsBITS(columnModel.getCunixpermsBITS());
        organizationModel.setStatusesBITS(columnModel.getCstatusesBITS());

        dbReference.child(organizationID).setValue(organizationModel, (dbError, dbReference) -> {
            if (dbError == null) {
                DatabaseReference dbOrgRef;
                switch (organizationModel.getOrgType()) {
                    case 0:
                        dbOrgRef = database.getReference(cRealtimeHelper.KEY_BENEFACTORS);
                        dbOrgRef.child(organizationID).setValue(true);
                        dbOrgRef = database.getReference(cRealtimeHelper.KEY_NATIONAL_PARTNERS);
                        dbOrgRef.child(organizationID).setValue(true);
                        break;
                    case 1:
                        dbOrgRef = database.getReference(cRealtimeHelper.KEY_BENEFACTORS);
                        dbOrgRef.child(organizationID).setValue(true);
                        dbOrgRef = database.getReference(cRealtimeHelper.KEY_DONORS);
                        dbOrgRef.child(organizationID).setValue(true);
                        break;
                    case 2:
                        dbOrgRef = database.getReference(cRealtimeHelper.KEY_BENEFICIARIES);
                        dbOrgRef.child(organizationID).setValue(true);
                        break;
                    case 3:
                        dbOrgRef = database.getReference(cRealtimeHelper.KEY_IMPLEMENTING_AGENCIES);
                        dbOrgRef.child(organizationID).setValue(true);
                        break;
                    default:
                        Log.d(TAG, "Error in creating an organization");
                }

                cTeamModel teamModel = getDefaultTeamModel(organizationID);
                cPlanModel planModel = getPremiumPlanModel();
                cRoleModel roleModel = getAdminRoleModel(organizationID);
                createOrganizationLinks(organizationID, userID, columnModel,
                        teamModel, planModel, roleModel);

                cPrivilegeModel privilegeModel = getAdminPrivilegeModel(columnModel);
                List<cPermissionModel> permissions = getAdminPermissions(columnModel);

                createAdminPermissions(organizationID, roleModel.getRoleServerID(),
                        privilegeModel, permissions);

                callback.onCreateOrganizationSucceeded("Organization saved successfully.");
            } else {
                callback.onCreateOrganizationFailed("Organization could not be saved " +
                        dbError.getMessage());
            }
        });
    }

   /* public void createAdminMenuItems() {
        dbReference = database.getReference(cRealtimeHelper.KEY_MENU_ITEMS);
        List<cMenuModel> menuModels = getAdminMenuModelSet();

        for (cMenuModel menuModel: menuModels){
            dbReference.child(menuModel.getServerID()).setValue(menuModel);
        }
    }*/

    public void createOrganizationLinks(String organizationID, String userID, cColumnModel columnModel,
                                        cTeamModel teamModel, cPlanModel planModel, cRoleModel roleModel) {
        DatabaseReference dbPeopleRef, dbTeamRef, dbPlanRef, dbRoleRef, dbUserOrgRef,
                dbOrgMemberRef, dbTeamMemberRef, dbMenuItemRef, dbOrgRoleMenuRef;

        dbPeopleRef = database.getReference(cRealtimeHelper.KEY_PEOPLE);
        String peopleID = dbPeopleRef.push().getKey();
        assert peopleID != null;
        dbPeopleRef.child(peopleID).child("orgServerID").setValue(organizationID);
        dbPeopleRef.child(peopleID).child("userServerID").setValue(userID);

        // add the current user to the organization
        dbOrgMemberRef = database.getReference(cRealtimeHelper.KEY_ORGANIZATION_MEMBERS);
        dbUserOrgRef = database.getReference(cRealtimeHelper.KEY_USER_ORGANIZATIONS);

        dbOrgMemberRef.child(organizationID).child(userID).setValue(true);
        dbUserOrgRef.child(userID).child(organizationID).setValue(true);

        //dbOrgMemberRef.child(orgMemberID).child(organizationID).setValue(true);
        //dbOrgMemberRef.child(orgMemberID).child(userID).setValue(true);

        //== create default team of the organization
        dbTeamRef = database.getReference(cRealtimeHelper.KEY_TEAMS);
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
        dbTeamMemberRef.child(organizationID + "_" + teamID).child(peopleID).setValue(true);

        //== create premium plan for the admin role
        dbPlanRef = database.getReference(cRealtimeHelper.KEY_PLANS);
        dbPlanRef.child(String.valueOf(planModel.getPlanServerID())).setValue(planModel);

        //== create role of the organization
        dbRoleRef = database.getReference(cRealtimeHelper.KEY_ROLES);
        String roleID = dbRoleRef.push().getKey();
        assert roleID != null;
        roleModel.setRoleServerID(roleID);
        roleModel.setOrgServerID(organizationID);
        roleModel.setPlanServerID(planModel.getPlanServerID());
        // update role's common columns
        roleModel.setOwnerID(columnModel.getCownerID());
        roleModel.setOrgOwnerID(columnModel.getCorgOwnerID());
        roleModel.setTeamOwnerBIT(columnModel.getCteamOwnerBIT());
        roleModel.setUnixpermsBITS(columnModel.getCunixpermsBITS());
        roleModel.setStatusesBITS(columnModel.getCstatusesBITS());

        dbRoleRef.child(roleID).setValue(roleModel);

        // create menu items
        dbMenuItemRef = database.getReference(cRealtimeHelper.KEY_MENU_ITEMS);

        // add menu items related to admin role of the organization
        dbOrgRoleMenuRef = database.getReference(cRealtimeHelper.KEY_ROLE_MENU_ITEMS);

        HashMap<cMenuModel, Map<String, cMenuModel>> modelMapHashMap = getAdminMenuModelSet(columnModel);

        for (Map.Entry<cMenuModel, Map<String, cMenuModel>> entry : modelMapHashMap.entrySet()) {
            cMenuModel menuModel = entry.getKey();
            Map<String, cMenuModel> subMenuModelMap = entry.getValue();
            // create main menu
            dbMenuItemRef.child(String.valueOf(menuModel.getMenuServerID())).setValue(menuModel);
            // create sub menu items
            if (subMenuModelMap.size() > 0) {
                dbMenuItemRef.child(String.valueOf(menuModel.getMenuServerID())).
                        child(cRealtimeHelper.KEY_SUB_MENU_ITEMS).setValue(subMenuModelMap);
            }

            // add menu items related to admin role of the organization
            dbOrgRoleMenuRef.child(roleModel.getRoleServerID()).child(
                    String.valueOf(menuModel.getMenuServerID())).setValue(true);

        }

        /*for(cMenuModel menuModel: menuModels){
            String roleID = roleModel.getServerID();
            String menuID = menuModel.getServerID();
            dbReference.child(organizationID+"_"+roleID+"_"+menuID).setValue(true);
        }*/
    }

    public void createAdminPermissions(String organizationID, String roleID,
                                       cPrivilegeModel privilegeModel,


                                       List<cPermissionModel> permissions) {
        DatabaseReference dbPlansRef, dbFeaturesRef, dbPlanFeaturesRef, dbPrivilegeRef, dbPermsRef,
                dbRolePrivRef, dbPrivPermsRef;

        dbPlansRef = database.getReference(cRealtimeHelper.KEY_PLANS);
        dbFeaturesRef = database.getReference(cRealtimeHelper.KEY_FEATURES);
        dbPlanFeaturesRef = database.getReference(cRealtimeHelper.KEY_PLAN_FEATURES);
        dbPrivilegeRef = database.getReference(cRealtimeHelper.KEY_PRIVILEGES);
        dbPermsRef = database.getReference(cRealtimeHelper.KEY_PERMISSIONS);
        dbRolePrivRef = database.getReference(cRealtimeHelper.KEY_ROLE_PRIVILEGES);
        dbPrivPermsRef = database.getReference(cRealtimeHelper.KEY_PRIVILEGE_PERMS);

        // creating plans
        List<cPlanModel> planModels = getPlanModels();
        for(int i = 0; i < planModels.size(); i++){
            cPlanModel planModel = planModels.get(i);
            dbPlansRef.child(String.valueOf(planModel.getPlanServerID())).setValue(planModel);
        }

        // creating features
        List<cFeatureModel> featureModels = getFeatureModels();
        for(int i = 0; i < featureModels.size(); i++){
            cFeatureModel featureModel = featureModels.get(i);
            dbFeaturesRef.child(String.valueOf(featureModel.getFeatureServerID())).
                    setValue(featureModel);
        }

        // creating link between plans and features
        HashMap<Integer, Set<Integer>> planFeatures = getPlanFeatureModelSet();
        for (Map.Entry<Integer, Set<Integer>> entry : planFeatures.entrySet()){
            int planID = entry.getKey();
            for (Integer featureID: entry.getValue()){
                dbPlanFeaturesRef.child(String.valueOf(planID)).
                        child(String.valueOf(featureID)).setValue(true);
            }
        }

        // creating admin privilege
        String privilegeID = dbPrivilegeRef.push().getKey();
        assert privilegeID != null;
        privilegeModel.setPrivilegeServerID(privilegeID);
        dbPrivilegeRef.child(privilegeID).setValue(privilegeModel);

        // creating admin role privileges
        dbRolePrivRef.child(roleID).child(privilegeID).setValue(true);

        for (int i = 0; i < permissions.size(); i++) {
            // creating permissions
            String permissionID = dbPermsRef.push().getKey();
            assert permissionID != null;

            String entityID = permissions.get(i).getEntityServerID();
            String operationID = permissions.get(i).getOperationServerID();
            String statusID = permissions.get(i).getStatusServerID();
            //dbPermsRef.child(permissionID + "_" + entityID + "_" + operationID + "_" + statusID).setValue(true);

            dbPermsRef.child(permissionID).child("entityServerID").setValue(entityID);
            dbPermsRef.child(permissionID).child("operationServerID").setValue(operationID);
            dbPermsRef.child(permissionID).child("statusServerID").setValue(statusID);

            // creating admin privilege permissions
            dbPrivPermsRef.child(privilegeID).child(permissionID).setValue(true);
        }



    }

    public cTeamModel getDefaultTeamModel(String organizationID) {
        cTeamModel teamModel = new cTeamModel();

        // read json file
        String team = "jsons/org_team.json";
        String teamJSONString = cDatabaseUtils.loadJSONFromAsset(team, context.getAssets());

        try {
            teamModel.setOrgServerID(organizationID);

            assert teamJSONString != null;
            JSONObject jsonObjectTeam = new JSONObject(teamJSONString);
            JSONObject jsonTeam = jsonObjectTeam.getJSONObject("team");

            teamModel.setOrgServerID(organizationID);
            teamModel.setTeamServerID(jsonTeam.getString("id"));
            teamModel.setName(jsonTeam.getString("name"));
            teamModel.setDescription(jsonTeam.getString("description"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return teamModel;
    }

    public cPlanModel getPremiumPlanModel() {
        cPlanModel planModel = new cPlanModel();
        // read json file
        String plans = "jsons/app_plans.json";
        String planJSONString = cDatabaseUtils.loadJSONFromAsset(plans, context.getAssets());

        //Log.d(TAG, roleJSONString);
        try {
            assert planJSONString != null;
            JSONObject jsonObjectPlan = new JSONObject(planJSONString);
            JSONArray jsonPlans = jsonObjectPlan.getJSONArray("plans");

            for (int i = 0; i < jsonPlans.length(); i++) {
                JSONObject jsonPlan = jsonPlans.getJSONObject(i);
                int premium_plan = jsonPlan.getInt("id");

                if (premium_plan == 4) {
                    planModel.setPlanServerID(jsonPlan.getInt("id"));
                    planModel.setName(jsonPlan.getString("name"));
                    planModel.setDescription(jsonPlan.getString("description"));

                    planModel.setOrgLimit(jsonPlan.getInt("max_org_limit"));
                    planModel.setTeamLimit(jsonPlan.getInt("max_team_limit"));
                    planModel.setOrgUserLimit(jsonPlan.getInt("max_org_user_limit"));
                    planModel.setTeamUserLimit(jsonPlan.getInt("max_team_user_limit"));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return planModel;
    }

//=== START
    public List<cPlanModel> getPlanModels() {
        List<cPlanModel> planModels = new ArrayList<>();
        // read json file
        String plans = "jsons/app_plans.json";
        String planJSONString = cDatabaseUtils.loadJSONFromAsset(plans, context.getAssets());

        try {
            assert planJSONString != null;
            JSONObject jsonObjectPlan = new JSONObject(planJSONString);
            JSONArray jsonPlans = jsonObjectPlan.getJSONArray("plans");

            for (int i = 0; i < jsonPlans.length(); i++) {
                cPlanModel planModel = new cPlanModel();

                JSONObject jsonPlan = jsonPlans.getJSONObject(i);

                planModel.setPlanServerID(jsonPlan.getInt("id"));
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
        String features = "jsons/app_features.json";
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

    public HashMap<Integer, Set<Integer>> getPlanFeatureModelSet() {
        HashMap<Integer, Set<Integer>> planFeatures = new HashMap<>();

        String plan = "jsons/plan_features.json";
        String planJSONString = cDatabaseUtils.loadJSONFromAsset(plan, context.getAssets());

        try {
            assert planJSONString != null;
            JSONObject jsonObjectPlan = new JSONObject(planJSONString);
            JSONArray jsonArrayPlan = jsonObjectPlan.getJSONArray("plans");
            for (int i = 0; i < jsonArrayPlan.length(); i++) {
                JSONObject jsonObject = jsonArrayPlan.getJSONObject(i);
                int planID = jsonObject.getInt("id");

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
    }
//=== END

    public cRoleModel getAdminRoleModel(String organizationID) {
        cRoleModel roleModel = new cRoleModel();

        // read json file
        String role = "jsons/admin_role.json";
        String roleJSONString = cDatabaseUtils.loadJSONFromAsset(role, context.getAssets());

        //Log.d(TAG, roleJSONString);
        try {
            roleModel.setOrgServerID(organizationID);

            assert roleJSONString != null;
            JSONObject jsonObjectRole = new JSONObject(roleJSONString);
            JSONObject jsonRole = jsonObjectRole.getJSONObject("role");

            roleModel.setOrgServerID(jsonRole.getString("role_id"));
            roleModel.setPlanServerID(jsonRole.getInt("plan_id"));
            roleModel.setName(jsonRole.getString("name"));
            roleModel.setDescription(jsonRole.getString("description"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return roleModel;
    }

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
    public HashMap<cMenuModel, Map<String, cMenuModel>> getAdminMenuModelSet(cColumnModel columnModel) {
        HashMap<cMenuModel, Map<String, cMenuModel>> menuModels = new HashMap<>();

        String menu = "jsons/admin_menu.json";
        String menuJSONString = cDatabaseUtils.loadJSONFromAsset(menu, context.getAssets());

        try {
            assert menuJSONString != null;
            JSONObject jsonObjectMenu = new JSONObject(menuJSONString);
            JSONArray jsonArrayMenu = jsonObjectMenu.getJSONArray("menu");
            for (int i = 0; i < jsonArrayMenu.length(); i++) {
                //ArrayList<cMenuModel> subMenuModels = new ArrayList<>();
                JSONObject jsonObject = jsonArrayMenu.getJSONObject(i);
                JSONArray jsonArraySubMenu = jsonObject.getJSONArray("sub_menu");

                Map<String, cMenuModel> subMenuMap = new HashMap<>();
                for (int j = 0; j < jsonArraySubMenu.length(); j++) {
                    cMenuModel subMenuModel = new cMenuModel();
                    // update submenu's common columns
                    subMenuModel.setOwnerID(columnModel.getCownerID());
                    subMenuModel.setOrgOwnerID(columnModel.getCorgOwnerID());
                    subMenuModel.setTeamOwnerBIT(columnModel.getCteamOwnerBIT());
                    subMenuModel.setUnixpermsBITS(columnModel.getCunixpermsBITS());
                    subMenuModel.setStatusesBITS(columnModel.getCstatusesBITS());

                    JSONObject jsonObjectSubItem = jsonArraySubMenu.getJSONObject(j);
                    subMenuModel.setMenuServerID(jsonObjectSubItem.getInt("sub_id"));
                    subMenuModel.setName(jsonObjectSubItem.getString("sub_item"));
                    subMenuModel.setDescription(jsonObjectSubItem.getString("sub_description"));

                    subMenuMap.put(jsonObjectSubItem.getString("sub_id"), subMenuModel);
                }

                cMenuModel menuModel = new cMenuModel();
                // update menu's common columns
                menuModel.setOwnerID(columnModel.getCownerID());
                menuModel.setOrgOwnerID(columnModel.getCorgOwnerID());
                menuModel.setTeamOwnerBIT(columnModel.getCteamOwnerBIT());
                menuModel.setUnixpermsBITS(columnModel.getCunixpermsBITS());
                menuModel.setStatusesBITS(columnModel.getCstatusesBITS());
                // set the main menu item
                menuModel.setMenuServerID(jsonObject.getInt("id"));
                menuModel.setName(jsonObject.getString("item"));
                menuModel.setDescription(jsonObject.getString("description"));

                // sub menu items to the main menu item
                menuModels.put(menuModel, subMenuMap);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return menuModels;
    }

    public cPrivilegeModel getAdminPrivilegeModel(cColumnModel columnModel) {
        cPrivilegeModel privilegeModel = new cPrivilegeModel();

        // read json file
        String privilege = "jsons/admin_privilege.json";
        String privilegeJSONString = cDatabaseUtils.loadJSONFromAsset(privilege,
                context.getAssets());

        try {
            assert privilegeJSONString != null;
            JSONObject jsonObjectPrivilege = new JSONObject(privilegeJSONString);
            JSONObject jsonPrivilege = jsonObjectPrivilege.getJSONObject("privilege");
            privilegeModel.setName(jsonPrivilege.getString("name"));
            privilegeModel.setDescription(jsonPrivilege.getString("description"));

            // update privilege's common columns
            privilegeModel.setOwnerID(columnModel.getCownerID());
            privilegeModel.setOrgOwnerID(columnModel.getCorgOwnerID());
            privilegeModel.setTeamOwnerBIT(columnModel.getCteamOwnerBIT());
            privilegeModel.setUnixpermsBITS(columnModel.getCunixpermsBITS());
            privilegeModel.setStatusesBITS(columnModel.getCstatusesBITS());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return privilegeModel;
    }

    public List<cPermissionModel> getAdminPermissions(cColumnModel columnModel) {
        List<cPermissionModel> permissionModels = new ArrayList<>();

        String perms = "jsons/admin_perms.json";
        String permsJSONString = cDatabaseUtils.loadJSONFromAsset(perms, context.getAssets());

        try {
            assert permsJSONString != null;
            JSONObject jsonObjectPerm = new JSONObject(permsJSONString);
            JSONArray jsonArrayPerm = jsonObjectPerm.getJSONArray("permissions");
            for (int i = 0; i < jsonArrayPerm.length(); i++) {

                JSONObject jsonObject = jsonArrayPerm.getJSONObject(i);
                JSONArray jsonArrayOps = jsonObject.getJSONArray("operations");


                for (int j = 0; j < jsonArrayOps.length(); j++) {
                    JSONObject jsonObjectEntity = jsonArrayOps.getJSONObject(j);

                    cPermissionModel permission = new cPermissionModel();
                    // entity identification bit
                    permission.setEntityServerID(jsonObject.getString("id"));
                    // operation identification bit
                    permission.setOperationServerID(jsonObjectEntity.getString("action"));
                    // status identification bit
                    permission.setStatusServerID(jsonObjectEntity.getString("status"));

                    permissionModels.add(permission);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return permissionModels;
    }

    public cColumnModel getColumnModel(String organizationID, String userID) {
        cColumnModel columnModel = new cColumnModel();

        // read json file
        String ccolumns = "jsons/default_columns.json";
        String ccolumnsJSONString = cDatabaseUtils.loadJSONFromAsset(ccolumns,
                context.getAssets());

        try {
            assert ccolumnsJSONString != null;
            JSONObject jsonObjectColumn = new JSONObject(ccolumnsJSONString);
            JSONObject jsonColumn = jsonObjectColumn.getJSONObject("ccolumns");

            columnModel.setCownerID(userID);
            columnModel.setCorgOwnerID(organizationID);
            columnModel.setCteamOwnerBIT(jsonColumn.getInt("cteam"));
            columnModel.setCunixpermsBITS(jsonColumn.getInt("cunixperms"));
            columnModel.setCstatusesBITS(jsonColumn.getInt("cstatuses"));

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return columnModel;
    }

    /* ############################################# READ ACTIONS ############################################# */

    @Override
    public void readOrganizations(long userID, int primaryRole, int secondaryRoles, int statusBITS,
                                  iReadOrganizationsModelSetCallback callback) {
        DatabaseReference dbReference = database.getReference(cRealtimeHelper.KEY_ORGANIZATIONS);
        dbReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                ArrayList<cOrganizationModel> organizationModels = new ArrayList<>();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    cOrganizationModel organizationModel = ds.getValue(cOrganizationModel.class);
                    organizationModels.add(organizationModel);

                    assert organizationModel != null;
                    Log.d(TAG, "Organization name: " + organizationModel.getName() + ", email " +
                            organizationModel.getEmail() + ", website " + organizationModel.getEmail());
                }

                callback.onReadOrganizationsSucceeded(organizationModels);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callback.onReadOrganizationsFailed("Failed to read Organization." + error.toException());
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    @Override
    public Set<cOrganizationModel> getOrganizationSet() {
        return null;
    }

    /* ############################################# UPDATE ACTIONS ############################################# */


    /* ############################################# DELETE ACTIONS ############################################# */

}
