package com.me.mseotsanyana.mande.DAL.ìmpl;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.QuerySnapshot;
import com.me.mseotsanyana.mande.BLL.model.session.cEntityModel;
import com.me.mseotsanyana.mande.BLL.model.session.cMenuModel;
import com.me.mseotsanyana.mande.BLL.model.session.cPermissionModel;
import com.me.mseotsanyana.mande.BLL.model.session.cPlanModel;
import com.me.mseotsanyana.mande.BLL.model.session.cRoleModel;
import com.me.mseotsanyana.mande.BLL.model.session.cTeamModel;
import com.me.mseotsanyana.mande.BLL.model.utils.cCommonPropertiesModel;
import com.me.mseotsanyana.mande.DAL.storage.preference.cSharedPreference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class cDatabaseUtils {

    // Private constructor to prevent instantiation
    private cDatabaseUtils() {
        throw new UnsupportedOperationException();
    }

    //public static methods here
    public static @NonNull
    String loadJSONFromAsset(String jsonMenu, AssetManager assetManager) {
        String json;
        try {
            //AssetManager assetManager = getContext().getAssets();
            InputStream is = assetManager.open(jsonMenu);

            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");

        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }

        //Log.e(TAG, "Json response " + json);
        return json;
    }

    public static cPlanModel getDefaultPlanModel(Context context) {
        cPlanModel planModel = new cPlanModel();
        // read json file
        String plans = "jsons/sys_plans.json";
        String planJSONString = cDatabaseUtils.loadJSONFromAsset(plans, context.getAssets());

        //Log.d(TAG, roleJSONString);
        try {
            //assert planJSONString != null;
            JSONObject jsonObjectPlan = new JSONObject(planJSONString);
            JSONArray jsonPlans = jsonObjectPlan.getJSONArray("plans");

            for (int i = 0; i < jsonPlans.length(); i++) {
                JSONObject jsonPlan = jsonPlans.getJSONObject(i);
                String default_plan = jsonPlan.getString("id");

                if (default_plan.equals("FREE_SUB")) {
                    planModel.setPlanServerID(jsonPlan.getString("id"));
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



    public static cTeamModel getAdminTeamModel(Context context, String organizationID,
                                               cCommonPropertiesModel columnModel) {
        cTeamModel teamModel = new cTeamModel();

        // read json file
        String team = "jsons/admin_team.json";
        String teamJSONString = cDatabaseUtils.loadJSONFromAsset(team, context.getAssets());

        try {
            teamModel.setOrganizationServerID(organizationID);

            JSONObject jsonObjectTeam = new JSONObject(teamJSONString);
            JSONObject jsonTeam = jsonObjectTeam.getJSONObject("team");

            teamModel.setCompositeServerID(organizationID + "_" + jsonTeam.getString("id"));
            teamModel.setOrganizationServerID(organizationID);
            teamModel.setTeamServerID(jsonTeam.getString("id"));

            teamModel.setName(jsonTeam.getString("name"));
            teamModel.setDescription(jsonTeam.getString("description"));

            // update team's common columns
            teamModel.setUserOwnerID(columnModel.getCownerID());
            teamModel.setOrganizationOwnerID(columnModel.getCorgOwnerID());
            teamModel.setTeamOwnerBIT(columnModel.getCteamOwnerBIT());
            teamModel.setUnixpermBITS(columnModel.getCunixpermBITS());
            teamModel.setStatusBIT(columnModel.getCstatusBIT());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return teamModel;
    }


    public static cRoleModel getAdminRoleModel(Context context, cCommonPropertiesModel columnModel) {
        cRoleModel roleModel = new cRoleModel();

        // read json file
        String role = "jsons/admin_role.json";
        String roleJSONString = cDatabaseUtils.loadJSONFromAsset(role, context.getAssets());

        //Log.d(TAG, roleJSONString);
        try {
            JSONObject jsonObjectRole = new JSONObject(roleJSONString);
            JSONObject jsonRole = jsonObjectRole.getJSONObject("role");

            roleModel.setName(jsonRole.getString("name"));
            roleModel.setDescription(jsonRole.getString("description"));

            // update privilege's common columns
            roleModel.setUserOwnerID(columnModel.getCownerID());
            roleModel.setOrganizationOwnerID(columnModel.getCorgOwnerID());
            roleModel.setTeamOwnerBIT(columnModel.getCteamOwnerBIT());
            roleModel.setUnixpermBITS(columnModel.getCunixpermBITS());
            roleModel.setStatusBIT(columnModel.getCstatusBIT());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return roleModel;
    }

    public static List<cMenuModel> getDefaultMenuModelSet(Context context) {
        List<cMenuModel> menuModels = new ArrayList<>();

        String menu = "jsons/sys_default_menu_items.json";
        String menuJSONString = cDatabaseUtils.loadJSONFromAsset(menu, context.getAssets());

        try {
            JSONObject jsonObjectMenu = new JSONObject(menuJSONString);
            JSONArray jsonArrayMenu = jsonObjectMenu.getJSONArray("menu");
            for (int i = 0; i < jsonArrayMenu.length(); i++) {
                ArrayList<cMenuModel> subMenuModels = new ArrayList<>();
                JSONObject jsonObject = jsonArrayMenu.getJSONObject(i);
                JSONArray jsonArraySubMenu = jsonObject.getJSONArray("sub_menu");

                for (int j = 0; j < jsonArraySubMenu.length(); j++) {
                    cMenuModel subMenuModel = new cMenuModel();
                    JSONObject jsonObjectSubItem = jsonArraySubMenu.getJSONObject(j);
                    subMenuModel.setMenuServerID(jsonObjectSubItem.getInt("sub_id"));
                    subMenuModel.setName(jsonObjectSubItem.getString("sub_item"));
                    subMenuModels.add(subMenuModel);
                }

                cMenuModel menuModel = new cMenuModel();
                // set the main menu item
                menuModel.setMenuServerID(jsonObject.getInt("id"));
                menuModel.setName(jsonObject.getString("item"));

                // sub menu items to the main menu item
                menuModel.setSubmenu(subMenuModels);

                menuModels.add(menuModel);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return menuModels;
    }


    public static List<cMenuModel> getMenuModelSet(Context context,
                                                   Map<String, List<Integer>> menu_map) {
        List<cMenuModel> menuModels = new ArrayList<>();

        String menu = "jsons/admin_menu_items.json";
        String menuJSONString = cDatabaseUtils.loadJSONFromAsset(menu, context.getAssets());

        try {

            JSONObject jsonObjectMenu = new JSONObject(menuJSONString);
            JSONArray jsonArrayMenu = jsonObjectMenu.getJSONArray("menu");

            Set<String> menu_set = menu_map.keySet();
            Set<Map.Entry<String, List<Integer>>> sub_menu_set =  menu_map.entrySet();

            Log.d("cDatabaseUtils"," =========>>>>>> "+ menu_map);
            //Log.d("cDatabaseUtils"," =========>>>>>> "+ sub_menu_set.);

            for (int i = 0; i < jsonArrayMenu.length(); i++) {

                ArrayList<cMenuModel> subMenuModels = new ArrayList<>();
                JSONObject jsonObject = jsonArrayMenu.getJSONObject(i);

                if (menu_set.contains(jsonObject.getString("id"))) {

                    cMenuModel menuModel = new cMenuModel();
                    // set the main menu item
                    menuModel.setMenuServerID(jsonObject.getInt("id"));
                    menuModel.setName(jsonObject.getString("item"));

                    JSONArray jsonArraySubMenu = jsonObject.getJSONArray("sub_menu");
                    for (int j = 0; j < jsonArraySubMenu.length(); j++) {

                        //if() {

                            cMenuModel subMenuModel = new cMenuModel();
                            JSONObject jsonObjectSubItem = jsonArraySubMenu.getJSONObject(j);
                            subMenuModel.setParentServerID(menuModel.getMenuServerID());
                            subMenuModel.setMenuServerID(jsonObjectSubItem.getInt("sub_id"));
                            subMenuModel.setName(jsonObjectSubItem.getString("sub_item"));
                            subMenuModels.add(subMenuModel);
                        //}
                    }

                    // sub menu items to the main menu item
                    menuModel.setSubmenu(subMenuModels);
                    // add menu items in the list
                    menuModels.add(menuModel);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return menuModels;
    }


    public static cPermissionModel getAdminPermissions(Context context) {

        cPermissionModel permissionModel = null;

        String perms = "jsons/admin_perms.json";
        String permsJSONString = cDatabaseUtils.loadJSONFromAsset(perms, context.getAssets());

        try {
            JSONObject jsonObjectPerms = new JSONObject(permsJSONString);

            /* processing entity and unix permissions*/

            JSONArray jsonArrayModule = jsonObjectPerms.getJSONArray("entitymodules");
            Map<String, List<cEntityModel>> entitymodules = new HashMap<>();

            for (int i = 0; i < jsonArrayModule.length(); i++) {
                JSONObject jsonObjectModules = jsonArrayModule.getJSONObject(i);
                String moduleID = jsonObjectModules.getString("module_id");
                JSONArray jsonArrayEntity = jsonObjectModules.getJSONArray("entities");

                List<cEntityModel> entityModels = new ArrayList<>();

                for (int j = 0; j < jsonArrayEntity.length(); j++) {
                    JSONObject jsonObjectEntity = jsonArrayEntity.getJSONObject(j);
                    String entityID = jsonObjectEntity.getString("entity_id");

                    // operation with list of statuses
                    JSONArray jsonArrayOpStatus = jsonObjectEntity.getJSONArray("operations");
                    Map<String, List<Integer>> entityperms = new HashMap<>();
                    for (int k = 0; k < jsonArrayOpStatus.length(); k++) {
                        JSONObject jsonObjectOps = jsonArrayOpStatus.getJSONObject(k);
                        String operationID = jsonObjectOps.getString("op_id");

                        JSONArray jsonArrayStatus = jsonObjectOps.getJSONArray("status_ids");
                        List<Integer> statuses = new ArrayList<>();
                        for (int l = 0; l < jsonArrayStatus.length(); l++) {
                            int jsonObjectStatus = jsonArrayStatus.getInt(l);
                            statuses.add(jsonObjectStatus);
                        }

                        entityperms.put(operationID, statuses);
                    }

                    // unix operations
                    JSONArray jsonArrayUnixPerm = jsonObjectEntity.getJSONArray("unixoperations");
                    List<Integer> unixperms = new ArrayList<>();
                    for (int k = 0; k < jsonArrayUnixPerm.length(); k++) {
                        JSONObject jsonObjectUnixOps = jsonArrayUnixPerm.getJSONObject(k);
                        String unixPermID = jsonObjectUnixOps.getString("unix_op_id");
                        unixperms.add(Integer.parseInt(unixPermID));
                    }

                    // create entity model
                    cEntityModel entityModel = new cEntityModel(entityID, entityperms, unixperms);
                    entityModels.add(entityModel);
                }

                entitymodules.put(moduleID, entityModels);
            }

            /* processing menu items */

            JSONArray jsonArrayMenuItem = jsonObjectPerms.getJSONArray("menuitems");
            Map<String, List<Integer>>  menuitems = new HashMap<>();
            for (int i = 0; i < jsonArrayMenuItem.length(); i++) {
                JSONObject jsonObject = jsonArrayMenuItem.getJSONObject(i);
                JSONArray jsonArraySubMenu = jsonObject.getJSONArray("sub_menu");

                List<Integer> submenuitems = new ArrayList<>();
                for (int j = 0; j < jsonArraySubMenu.length(); j++) {
                    JSONObject jsonObjectSubItem = jsonArraySubMenu.getJSONObject(j);
                    submenuitems.add(jsonObjectSubItem.getInt("sub_id"));
                }

                // set the main menu item
                menuitems.put(jsonObject.getString("id"), submenuitems);
            }

            permissionModel = new cPermissionModel(entitymodules, menuitems);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return permissionModel;
    }

    public static cCommonPropertiesModel getCommonModel(Context context) {
        cCommonPropertiesModel commonPropertiesModel = new cCommonPropertiesModel();

        // read json file
        String cproperties = "jsons/admin_common_properties.json";
        String ccolumnsJSONString = cDatabaseUtils.loadJSONFromAsset(cproperties,
                context.getAssets());

        try {
            //assert ccolumnsJSONString != null;
            JSONObject jsonObjectColumn = new JSONObject(ccolumnsJSONString);
            JSONObject jsonColumn = jsonObjectColumn.getJSONObject("cproperties");

            commonPropertiesModel.setCteamOwnerBIT(jsonColumn.getInt("cteam"));
            commonPropertiesModel.setCstatusBIT(jsonColumn.getInt("cstatus"));
            JSONArray jsonArrayPerm = jsonColumn.getJSONArray("cunixperms");

            List<Integer> unixperm = new ArrayList<>();
            for (int j = 0; j < jsonArrayPerm.length(); j++) {
                JSONObject jsonObjectPerm = jsonArrayPerm.getJSONObject(j);
                unixperm.add(jsonObjectPerm.getInt("unix_op_id"));
            }
            commonPropertiesModel.setCunixpermBITS(unixperm);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return commonPropertiesModel;
    }

    public static boolean isPermitted(cUnixPerm perm, String userServerID, int primaryTeamBIT,
                                      List<Integer> secondaryTeamBITS, List<Integer> statusBITS) {

        return statusBITS.contains(perm.getStatusBIT()) &&
                /* 1. members of 'Administrator' team are always allowed to do everything */
                ((perm.getTeamOwnerBIT() == 1) ||
                        /* 2. the member is the owner and the owner permission bit is on */
                        (perm.getUserOwnerID().equals(userServerID) &&
                                perm.getUnixpermBITS().contains(cSharedPreference.OWNER_READ)) ||
                        /* 3. the member is in the primary team and the primary team permission bit is on */
                        (perm.getTeamOwnerBIT() == primaryTeamBIT &&
                                perm.getUnixpermBITS().contains(cSharedPreference.PRIMARY_READ)) ||
                        /* 4. the member is in the secondary team and the second team permission bit is on */
                        (secondaryTeamBITS.contains(perm.getTeamOwnerBIT()) &&
                                perm.getUnixpermBITS().contains(cSharedPreference.SECONDARY_READ)) ||
                        /* 5. members is in the organization and the workplace permission bit is on */
                        (perm.getUnixpermBITS().contains(cSharedPreference.WORKPLACE_READ)));
    }

    public static boolean isPermitted(cUnixPerm perm, String userServerID, int primaryTeamBIT,
                                      List<Integer> secondaryTeamBITS) {

        return  /* 1. members of 'Administrator' team are always allowed to do everything */
                ((perm.getTeamOwnerBIT() == 1) ||
                        /* 2. the member is the owner and the owner permission bit is on */
                        (perm.getUserOwnerID().equals(userServerID) &&
                                perm.getUnixpermBITS().contains(cSharedPreference.OWNER_READ)) ||
                        /* 3. the member is in the primary team and the primary team permission bit is on */
                        (perm.getTeamOwnerBIT() == primaryTeamBIT &&
                                perm.getUnixpermBITS().contains(cSharedPreference.PRIMARY_READ)) ||
                        /* 4. the member is in the secondary team and the second team permission bit is on */
                        (secondaryTeamBITS.contains(perm.getTeamOwnerBIT()) &&
                                perm.getUnixpermBITS().contains(cSharedPreference.SECONDARY_READ)) ||
                        /* 5. members is in the organization and the workplace permission bit is on */
                        (perm.getUnixpermBITS().contains(cSharedPreference.WORKPLACE_READ)));
    }

    public static class cUnixPerm {
        private String userOwnerID;
        private int teamOwnerBIT;
        private List<Integer> unixpermBITS;
        private int statusBIT;

        public String getUserOwnerID() {
            return userOwnerID;
        }

        public void setUserOwnerID(String userOwnerID) {
            this.userOwnerID = userOwnerID;
        }

        public int getTeamOwnerBIT() {
            return teamOwnerBIT;
        }

        public void setTeamOwnerBIT(int teamOwnerBIT) {
            this.teamOwnerBIT = teamOwnerBIT;
        }

        public List<Integer> getUnixpermBITS() {
            return unixpermBITS;
        }

        public void setUnixpermBITS(List<Integer> unixpermBITS) {
            this.unixpermBITS = unixpermBITS;
        }

        public int getStatusBIT() {
            return statusBIT;
        }

        public void setStatusBIT(int statusBIT) {
            this.statusBIT = statusBIT;
        }
    }

    //==

    public static Map<cMenuModel, Map<String, cMenuModel>> getAdminMenuModelSet(
            Context context) {
        Map<cMenuModel, Map<String, cMenuModel>> menuModels = new HashMap<>();

        String menu = "jsons/admin_menu_items.json";
        String menuJSONString = cDatabaseUtils.loadJSONFromAsset(menu, context.getAssets());

        try {
            JSONObject jsonObjectMenu = new JSONObject(menuJSONString);
            JSONArray jsonArrayMenu = jsonObjectMenu.getJSONArray("menu");
            for (int i = 0; i < jsonArrayMenu.length(); i++) {
                JSONObject jsonObject = jsonArrayMenu.getJSONObject(i);
                JSONArray jsonArraySubMenu = jsonObject.getJSONArray("sub_menu");

                Map<String, cMenuModel> subMenuMap = new HashMap<>();
                for (int j = 0; j < jsonArraySubMenu.length(); j++) {
                    cMenuModel subMenuModel = new cMenuModel();

                    JSONObject jsonObjectSubItem = jsonArraySubMenu.getJSONObject(j);
                    subMenuModel.setMenuServerID(jsonObjectSubItem.getInt("sub_id"));
                    subMenuModel.setName(jsonObjectSubItem.getString("sub_item"));

                    subMenuMap.put(jsonObjectSubItem.getString("sub_id"), subMenuModel);
                }

                cMenuModel menuModel = new cMenuModel();

                // set the main menu item
                menuModel.setMenuServerID(jsonObject.getInt("id"));
                menuModel.setName(jsonObject.getString("item"));

                // sub menu items to the main menu item
                menuModels.put(menuModel, subMenuMap);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return menuModels;
    }



    public static Task<List<QuerySnapshot>> applyReadPermissions(CollectionReference coRef,
                                                                 String userServerID,
                                                                 String organizationServerID,
                                                                 int primaryTeamBIT,
                                                                 List<Integer> secondaryTeamBITS) {

        Task ownerTask, primaryTask, secondaryTask, organizationTask;

        /* 2. the owner permission bit is on, and the member is the owner */
        ownerTask = coRef
                .whereEqualTo("organizationOwnerID", organizationServerID)
                .whereEqualTo("userOwnerID", userServerID)
                .whereArrayContains("unixpermBITS", cSharedPreference.OWNER_READ)
                .get();

        /* 3. the primary team permission bit is on, and the member is in the team */
        primaryTask = coRef
                .whereEqualTo("organizationOwnerID", organizationServerID)
                .whereEqualTo("teamOwnerBIT", primaryTeamBIT)
                .whereArrayContains("unixpermBITS", cSharedPreference.PRIMARY_READ)
                .get();

        /* 4. the second team permission bit is on, and the member is in the team */
        secondaryTask = coRef
                .whereEqualTo("organizationOwnerID", organizationServerID)
                .whereIn("teamOwnerBIT", secondaryTeamBITS)
                .whereArrayContains("unixpermBITS", cSharedPreference.SECONDARY_READ)
                .get();

        /* 5. the other permission bit is on -
              all members of an organization have access */
        organizationTask = coRef
                .whereEqualTo("organizationOwnerID", organizationServerID)
                .whereArrayContains("unixpermBITS", cSharedPreference.WORKPLACE_READ)
                .get();

        return Tasks.whenAllSuccess(ownerTask, primaryTask, secondaryTask, organizationTask);
    }

//    public static cPrivilegeModel getAdminPrivilegeModel(Context context,
//                                                         cCommonPropertiesModel columnModel) {
//        cPrivilegeModel privilegeModel = new cPrivilegeModel();
//
//        // read json file
//        String privilege = "jsons_old_files/admin_privilege.json";
//        String privilegeJSONString = cDatabaseUtils.loadJSONFromAsset(privilege,
//                context.getAssets());
//
//        try {
//            JSONObject jsonObjectPrivilege = new JSONObject(privilegeJSONString);
//            JSONObject jsonPrivilege = jsonObjectPrivilege.getJSONObject("privilege");
//            privilegeModel.setName(jsonPrivilege.getString("name"));
//            privilegeModel.setDescription(jsonPrivilege.getString("description"));
//
//            // update privilege's common columns
//            privilegeModel.setOwnerID(columnModel.getCownerID());
//            privilegeModel.setOrgOwnerID(columnModel.getCorgOwnerID());
//            privilegeModel.setTeamOwnerBIT(columnModel.getCteamOwnerBIT());
//            privilegeModel.setUnixpermsBITS(columnModel.getCunixpermsBITS());
//            privilegeModel.setStatusesBITS(columnModel.getCstatusesBITS());
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        return privilegeModel;
//    }


}

//    public static cPermissionModel getAdminPermissions(Context context) {
//
//        cPermissionModel permissionModel = null;
//
//        Gson gson = new Gson();
//        String perms = "jsons/admin_perms.json";
//        String permsJSONString = cDatabaseUtils.loadJSONFromAsset(perms, context.getAssets());
//
//        try {
//            assert permsJSONString != null;
//            JSONObject jsonObjectPerms = new JSONObject(permsJSONString);
//            JSONArray jsonArrayModule = jsonObjectPerms.getJSONArray("modules");
//
//            Map<String, Map<String, Map<String, List<Integer>>>> opStatusModuleMap = new HashMap<>();
//            Map<String, Map<String, List<Integer>>> unixPermModuleMap = new HashMap<>();
//
//            for (int i = 0; i < jsonArrayModule.length(); i++) {
//                JSONObject jsonObjectModules = jsonArrayModule.getJSONObject(i);
//                String moduleID = jsonObjectModules.getString("module_id");
//                JSONArray jsonArrayEntity = jsonObjectModules.getJSONArray("entities");
//                Map<String, Map<String, List<Integer>>> entityOpStatusMap = new HashMap<>();
//                Map<String, List<Integer>> entityUnixPermMap = new HashMap<>();
//                for (int j = 0; j < jsonArrayEntity.length(); j++) {
//                    JSONObject jsonObjectEntities = jsonArrayEntity.getJSONObject(j);
//                    String entityID = jsonObjectEntities.getString("entity_id");
//
//                    // operation with list of statuses
//                    JSONArray jsonArrayOpStatus = jsonObjectEntities.getJSONArray("operations");
//                    Map<String, List<Integer>> operationStatusMap = new HashMap<>();
//                    for (int k = 0; k < jsonArrayOpStatus.length(); k++) {
//                        JSONObject jsonObjectOps = jsonArrayOpStatus.getJSONObject(k);
//                        String operationID = jsonObjectOps.getString("op_id");
//
//                        JSONArray jsonArrayStatus = jsonObjectOps.getJSONArray("status_ids");
//                        List<Integer> statuses = new ArrayList<>();
//                        for (int l = 0; l < jsonArrayStatus.length(); l++) {
//                            int jsonObjectStatus = jsonArrayStatus.getInt(l);
//                            statuses.add(jsonObjectStatus);
//                        }
//
//                        operationStatusMap.put(operationID, statuses);
//                    }
//
//                    // unix operations
//                    JSONArray jsonArrayUnixPerm = jsonObjectEntities.getJSONArray("unixoperations");
//                    List<Integer> unixPermList = new ArrayList<>();
//                    for (int k = 0; k < jsonArrayUnixPerm.length(); k++) {
//                        JSONObject jsonObjectUnixOps = jsonArrayUnixPerm.getJSONObject(k);
//                        String unixPermID = jsonObjectUnixOps.getString("unix_op_id");
//                        unixPermList.add(Integer.parseInt(unixPermID));
//                    }
//
//                    entityOpStatusMap.put(entityID, operationStatusMap);
//                    entityUnixPermMap.put(entityID, unixPermList);
//                }
//
//                opStatusModuleMap.put(moduleID, entityOpStatusMap);
//                unixPermModuleMap.put(moduleID, entityUnixPermMap);
//            }
//
//            permissionModel = new cPermissionModel(opStatusModuleMap, unixPermModuleMap);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        return permissionModel;
//    }
//
//    public static Pair<cPermissionModel, List<cUnixPermissionModel>> getAdminPermissions(Context context) {
//        //List<cPermissionModel> permissionModels = new ArrayList<>();
//        //cPermissionModel permissionModels = new ArrayList<>();
//        List<cUnixPermissionModel> unixPermissionModels = new ArrayList<>();
//        Pair<cPermissionModel, List<cUnixPermissionModel>> entityPair = null;
//
//        String perms = "jsons/admin_perms.json";
//        String permsJSONString = cDatabaseUtils.loadJSONFromAsset(perms, context.getAssets());
//
//        try {
//            assert permsJSONString != null;
//            JSONObject jsonObjectPerm = new JSONObject(permsJSONString);
//            JSONArray jsonArrayPerm = jsonObjectPerm.getJSONArray("permissions");
//
//            Map<String, Map<String, Map<String, Integer>>> privileges = new HashMap<>();
//
//            for (int i = 0; i < jsonArrayPerm.length(); i++) {
//
//                JSONObject jsonObject = jsonArrayPerm.getJSONObject(i);
//                String moduleID = jsonObject.getString("module_id");
//                String entityID = jsonObject.getString("entity_id");
//
//                cPermissionModel permission = new cPermissionModel(moduleID, entityID);
//                cUnixPermissionModel unixPermission = new cUnixPermissionModel(moduleID, entityID);
//
//                // operations that control access at table/entity level
//                JSONArray jsonArrayOps = jsonObject.getJSONArray("operations");
//                Map<String, Integer> operationStatusMap = new HashMap<>();
//                for (int j = 0; j < jsonArrayOps.length(); j++) {
//                    JSONObject jsonObjectOps = jsonArrayOps.getJSONObject(j);
//
//                    String operationID = jsonObjectOps.getString("op_id");
//                    operationStatusMap.put(operationID, 0);
//                }
//
//                privileges
//
//                permission.setOperationStatusMap(operationStatusMap);
//                permissionModels.add(permission);
//
//                // unix operations that control access at row level
//                JSONArray jsonArrayUnixOps = jsonObject.getJSONArray("unixoperations");
//                List<String> unixOperations = new ArrayList<>();
//                for (int k = 0; k < jsonArrayUnixOps.length(); k++) {
//                    JSONObject jsonObjectUnixOp = jsonArrayUnixOps.getJSONObject(k);
//
//                    String unixOperationID = jsonObjectUnixOp.getString("unix_op_id");
//                    unixOperations.add(unixOperationID);
//                }
//                unixPermission.setUnixOperations(unixOperations);
//                unixPermissionModels.add(unixPermission);
//            }
//
//            entityPair = new Pair<>(permissionModels, unixPermissionModels);
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        return entityPair;
//    }
//
//
//    public static HashMap<cMenuModel, Map<String, cMenuModel>> getDefaultMenuModelMap(Context context) {
//        HashMap<cMenuModel, Map<String, cMenuModel>> menuModels = new HashMap<>();
//
//        String menu = "jsons_old_files/sys_default_menu_items.json";
//        String menuJSONString = cDatabaseUtils.loadJSONFromAsset(menu, context.getAssets());
//
//        try {
//            JSONObject jsonObjectMenu = new JSONObject(menuJSONString);
//            JSONArray jsonArrayMenu = jsonObjectMenu.getJSONArray("menu");
//            for (int i = 0; i < jsonArrayMenu.length(); i++) {
//                JSONObject jsonObject = jsonArrayMenu.getJSONObject(i);
//                JSONArray jsonArraySubMenu = jsonObject.getJSONArray("sub_menu");
//
//                Map<String, cMenuModel> subMenuMap = new HashMap<>();
//                for (int j = 0; j < jsonArraySubMenu.length(); j++) {
//                    cMenuModel subMenuModel = new cMenuModel();
//
//                    JSONObject jsonObjectSubItem = jsonArraySubMenu.getJSONObject(j);
//                    subMenuModel.setMenuServerID(jsonObjectSubItem.getInt("sub_id"));
//                    subMenuModel.setName(jsonObjectSubItem.getString("sub_item"));
//
//                    subMenuMap.put(jsonObjectSubItem.getString("sub_id"), subMenuModel);
//                }
//
//                cMenuModel menuModel = new cMenuModel();
//
//                // set the main menu item
//                menuModel.setMenuServerID(jsonObject.getInt("id"));
//                menuModel.setName(jsonObject.getString("item"));
//
//                // sub menu items to the main menu item
//                menuModels.put(menuModel, subMenuMap);
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        return menuModels;
//    }
//
//    public static List<cPlanModel> getPlanModels(Context context) {
//        List<cPlanModel> planModels = new ArrayList<>();
//        // read json file
//        String plans = "jsons/sys_plans.json";
//        String planJSONString = cDatabaseUtils.loadJSONFromAsset(plans, context.getAssets());
//
//        try {
//            JSONObject jsonObjectPlan = new JSONObject(planJSONString);
//            JSONArray jsonPlans = jsonObjectPlan.getJSONArray("plans");
//
//            for (int i = 0; i < jsonPlans.length(); i++) {
//                cPlanModel planModel = new cPlanModel();
//
//                JSONObject jsonPlan = jsonPlans.getJSONObject(i);
//
//                planModel.setPlanServerID(jsonPlan.getString("id"));
//                planModel.setName(jsonPlan.getString("name"));
//                planModel.setDescription(jsonPlan.getString("description"));
//
//                planModel.setOrgLimit(jsonPlan.getInt("max_org_limit"));
//                planModel.setTeamLimit(jsonPlan.getInt("max_team_limit"));
//                planModel.setOrgUserLimit(jsonPlan.getInt("max_org_user_limit"));
//                planModel.setTeamUserLimit(jsonPlan.getInt("max_team_user_limit"));
//
//                planModels.add(planModel);
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        return planModels;
//    }
//
//    public static List<cFeatureModel> getFeatureModels(Context context) {
//        List<cFeatureModel> featureModels = new ArrayList<>();
//        // read json file
//        String features = "jsons/sys_features.json";
//        String featureJSONString = cDatabaseUtils.loadJSONFromAsset(features, context.getAssets());
//
//        try {
//            JSONObject jsonObjectFeature = new JSONObject(featureJSONString);
//            JSONArray jsonFeatures = jsonObjectFeature.getJSONArray("features");
//
//            for (int i = 0; i < jsonFeatures.length(); i++) {
//                cFeatureModel featureModel = new cFeatureModel();
//
//                JSONObject jsonFeature = jsonFeatures.getJSONObject(i);
//
//                featureModel.setFeatureServerID(jsonFeature.getInt("id"));
//                featureModel.setName(jsonFeature.getString("name"));
//                featureModel.setDescription(jsonFeature.getString("description"));
//
//                featureModels.add(featureModel);
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        return featureModels;
//    }
//
//    public static HashMap<String, List<cFeatureModel>> getPlanFeatureModels(Context context) {
//        HashMap<String, List<cFeatureModel>> planFeatures = new HashMap<>();
//
//        String plan = "jsons/sys_features.json";
//        String planJSONString = cDatabaseUtils.loadJSONFromAsset(plan, context.getAssets());
//
//        try {
//            assert planJSONString != null;
//            JSONObject jsonObjectPlan = new JSONObject(planJSONString);
//            JSONArray jsonArrayPlan = jsonObjectPlan.getJSONArray("plans");
//            for (int i = 0; i < jsonArrayPlan.length(); i++) {
//                JSONObject jsonObject = jsonArrayPlan.getJSONObject(i);
//                String planID = jsonObject.getString("id");
//
//                JSONArray jsonArrayFeature = jsonObject.getJSONArray("features");
//                List<cFeatureModel> featureModels = new ArrayList<>();
//                for (int j = 0; j < jsonArrayFeature.length(); j++) {
//                    JSONObject jsonObjectFeature = jsonArrayFeature.getJSONObject(j);
//
//                    cFeatureModel featureModel = new cFeatureModel(
//                            jsonObjectFeature.getInt("id"),
//                            jsonObjectFeature.getString("name"),
//                            jsonObjectFeature.getString("description")
//                    );
//
//                    featureModels.add(featureModel);
//                }
//
//                planFeatures.put(planID, featureModels);
//
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        return planFeatures;
//    }
//
//
//    public static List<Integer> convertBinary(int number) {
//        //int binary[] = new int[40];
//        List<Integer> binary_array = new ArrayList<>();
//        List<Integer> binary = new ArrayList<>();
//        //int index = 0;
//        while (number > 0) {
//            //binary[index++] = num%2;
//            binary_array.add(number % 2);
//            number = number / 2;
//        }
//
//        for (int i = binary_array.size() - 1; i >= 0; i--) {
//            //System.out.print(binary[i]);
//            binary_array.add(i);
//        }
//
//        return binary;
//    }
//
//    public static List<cUnixPermissionModel> getAdminUnixOperation(Context context) {
//        List<cUnixPermissionModel> unixOperationModels = new ArrayList<>();
//
//        String perms = "jsons/sys_unixoperations.json";
//        String unixoperationJSONString = cDatabaseUtils.loadJSONFromAsset(perms, context.getAssets());
//
//        try {
//            assert unixoperationJSONString != null;
//            JSONObject jsonObjectUnixOperation = new JSONObject(unixoperationJSONString);
//            JSONArray jsonArrayUnixOperation = jsonObjectUnixOperation.getJSONArray("unixoperations");
//            for (int i = 0; i < jsonArrayUnixOperation.length(); i++) {
//                cUnixPermissionModel unixperm = new cUnixPermissionModel();
//
//                JSONObject jsonObject = jsonArrayUnixOperation.getJSONObject(i);
//
//                unixperm.setUnixOperationServerID(jsonObject.getString("id"));
//                unixperm.setName(jsonObject.getString("name"));
//                unixperm.setDescription(jsonObject.getString("description"));
//
//                unixOperationModels.add(unixperm);
////
////                JSONArray jsonArrayOps = jsonObject.getJSONArray("unixoperations");
////
////
////                for (int j = 0; j < jsonArrayOps.length(); j++) {
////                    JSONObject jsonObjectEntity = jsonArrayOps.getJSONObject(j);
////
////                    cUnixPermModel unixperm = new cUnixPermModel();
////                    // entity and its type identification bits
////                    unixperm.setEntityServerID(jsonObject.getString("entity_id"));
////                    unixperm.setEntityTypeServerID(jsonObject.getString("entity_type_id"));
////                    // unix operation identification bit
////                    unixperm.setUnixoperationServerID(jsonObjectEntity.getString("ops_id"));
////
////                    unixPermModels.add(unixperm);
////                }
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        return unixOperationModels;
//    }

