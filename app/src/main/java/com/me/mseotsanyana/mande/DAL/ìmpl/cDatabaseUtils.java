package com.me.mseotsanyana.mande.DAL.ìmpl;

import android.content.Context;
import android.content.res.AssetManager;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.QuerySnapshot;
import com.me.mseotsanyana.mande.BLL.model.session.cEntityModel;
import com.me.mseotsanyana.mande.BLL.model.session.cMenuModel;
import com.me.mseotsanyana.mande.BLL.model.session.cOperationStatusCollection;
import com.me.mseotsanyana.mande.BLL.model.session.cSectionModel;
import com.me.mseotsanyana.mande.BLL.model.session.cOperationModel;
import com.me.mseotsanyana.mande.BLL.model.session.cPermissionModel;
import com.me.mseotsanyana.mande.BLL.model.session.cPlanModel;
import com.me.mseotsanyana.mande.BLL.model.session.cRoleModel;
import com.me.mseotsanyana.mande.BLL.model.session.cStatusModel;
import com.me.mseotsanyana.mande.BLL.model.session.cTeamModel;
import com.me.mseotsanyana.mande.BLL.model.session.cUnixOperationCollection;
import com.me.mseotsanyana.mande.BLL.model.session.cUnixOperationModel;
import com.me.mseotsanyana.mande.BLL.model.utils.cCommonPropertiesModel;
import com.me.mseotsanyana.mande.DAL.storage.preference.cSharedPreference;
import com.me.mseotsanyana.treeadapterlibrary.cTreeModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class cDatabaseUtils {

    // Private constructor to prevent instantiation
    private cDatabaseUtils() {
        throw new UnsupportedOperationException();
    }

    // public static methods here
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
            json = new String(buffer, StandardCharsets.UTF_8);

        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }

        //Log.e(TAG, "Json response " + json);
        return json;
    }

    /**
     * read a default freemium plan of the new user or demotion to a lower plan due to non payment
     *
     * @param context context
     * @return plan model
     */
    public static cPlanModel getDefaultPlanModel(Context context) {
        cPlanModel planModel = new cPlanModel();
        // read json file
        String plans = "jsons/sys_plans.json";
        String planJSONString = cDatabaseUtils.loadJSONFromAsset(plans, context.getAssets());

        try {
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

    /**
     * read administrator's default team from json - during the creation of an organization
     *
     * @param context               context
     * @param organizationID        organization identification
     * @param commonPropertiesModel common properties model
     * @return team model
     */
    public static cTeamModel getAdminTeamModel(Context context, String organizationID,
                                               cCommonPropertiesModel commonPropertiesModel) {
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
            teamModel.setUserOwnerID(commonPropertiesModel.getCownerID());
            teamModel.setOrganizationOwnerID(commonPropertiesModel.getCorgOwnerID());
            teamModel.setTeamOwnerBIT(commonPropertiesModel.getCteamOwnerBIT());
            teamModel.setUnixpermBITS(commonPropertiesModel.getCunixpermBITS());
            teamModel.setStatusBIT(commonPropertiesModel.getCstatusBIT());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return teamModel;
    }

    /**
     * read administrator's default role from json - during the creation of an organization
     *
     * @param context               context
     * @param commonPropertiesModel common properties model
     * @return role model
     */
    public static cRoleModel getAdminRoleModel(Context context,
                                               cCommonPropertiesModel commonPropertiesModel) {
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
            roleModel.setUserOwnerID(commonPropertiesModel.getCownerID());
            roleModel.setOrganizationOwnerID(commonPropertiesModel.getCorgOwnerID());
            roleModel.setTeamOwnerBIT(commonPropertiesModel.getCteamOwnerBIT());
            roleModel.setUnixpermBITS(commonPropertiesModel.getCunixpermBITS());
            roleModel.setStatusBIT(commonPropertiesModel.getCstatusBIT());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return roleModel;
    }

    /**
     * read default menu if the role based menu is not available.
     *
     * @param context context
     * @return list of default menu
     */
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

    /**
     * build the main menu with its corresponding sub menu model based on the permissions
     * allocated to the user through team membership and role assigned to the team
     *
     * @param context  context
     * @param menu_map menu map with main and sub menu items
     * @return list of menu models
     */
    public static List<cMenuModel> getMenuModelSet(Context context,
                                                   Map<String, List<Integer>> menu_map) {
        List<cMenuModel> menuModels = new ArrayList<>();

        String menu = "jsons/admin_menu_items.json";
        String menuJSONString = cDatabaseUtils.loadJSONFromAsset(menu, context.getAssets());

        try {

            JSONObject jsonObjectMenu = new JSONObject(menuJSONString);
            JSONArray jsonArrayMenu = jsonObjectMenu.getJSONArray("menu");

            Set<String> menu_set = menu_map.keySet();

            for (int i = 0; i < jsonArrayMenu.length(); i++) {

                ArrayList<cMenuModel> subMenuModels = new ArrayList<>();
                JSONObject jsonObject = jsonArrayMenu.getJSONObject(i);

                if (menu_set.contains(jsonObject.getString("id"))) {

                    cMenuModel menuModel = new cMenuModel();
                    // set the main menu item
                    menuModel.setMenuServerID(jsonObject.getInt("id"));
                    menuModel.setName(jsonObject.getString("item"));

                    JSONArray jsonArraySubMenu = jsonObject.getJSONArray("sub_menu");
                    List<Integer> sub_menu_items = menu_map.get(jsonObject.getString("id"));
                    for (int j = 0; j < jsonArraySubMenu.length(); j++) {

                        JSONObject jsonObjectSubItem = jsonArraySubMenu.getJSONObject(j);
                        assert sub_menu_items != null;
                        if (sub_menu_items.contains(jsonObjectSubItem.getInt("sub_id"))) {
                            cMenuModel subMenuModel = new cMenuModel();
                            subMenuModel.setParentServerID(menuModel.getMenuServerID());
                            subMenuModel.setMenuServerID(jsonObjectSubItem.getInt("sub_id"));
                            subMenuModel.setName(jsonObjectSubItem.getString("sub_item"));
                            subMenuModels.add(subMenuModel);
                        }
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

    /**
     * load a default administration menu from json file into the database - during the
     * creation of an organization
     *
     * @param context context
     * @return permission model
     */
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
            Map<String, List<Integer>> menuitems = new HashMap<>();
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

    /**
     * read default common properties of every entity
     *
     * @param context context
     * @return common properties model
     */
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

    /**
     * apply read permissions on organization entity - this needs a special application of
     * permissions since they have be applied from outside of the organization itself
     *
     * @param coRef                collection reference
     * @param organizationServerID organization identification
     * @param userServerID         user identification
     * @param primaryTeamBIT       primary team identification
     * @param secondaryTeamBITS    secondary teams identification
     * @return list query results
     */
    public static Task<List<QuerySnapshot>> applyReadPermissions(CollectionReference coRef,
                                                                 String organizationServerID,
                                                                 String userServerID,
                                                                 int primaryTeamBIT,
                                                                 List<Integer> secondaryTeamBITS) {

        Task ownerTask, primaryTask, secondaryTask, workplaceTask;

        /* 2. the owner permission bit is on, and the member is the owner */
        ownerTask = coRef
                .whereEqualTo("userOwnerID", userServerID)
                .whereArrayContains("unixpermBITS", cSharedPreference.OWNER_READ)
                .get();

        /* 3. the primary team permission bit is on, and the member is in the team */
        primaryTask = coRef
                .whereEqualTo("organizationServerID", organizationServerID)
                .whereEqualTo("teamOwnerBIT", primaryTeamBIT)
                .whereArrayContains("unixpermBITS", cSharedPreference.PRIMARY_READ)
                .get();

        /* 4. the second team permission bit is on, and the member is in the team */
        secondaryTask = coRef
                .whereEqualTo("organizationServerID", organizationServerID)
                .whereIn("teamOwnerBIT", secondaryTeamBITS)
                .whereArrayContains("unixpermBITS", cSharedPreference.SECONDARY_READ)
                .get();

        /* 5. the other permission bit is on -
              all members of an organization have access */
        workplaceTask = coRef
                .whereEqualTo("organizationServerID", organizationServerID)
                .whereArrayContains("unixpermBITS", cSharedPreference.WORKPLACE_READ)
                .get();

        return Tasks.whenAllSuccess(ownerTask, primaryTask, secondaryTask, workplaceTask);
    }

    /**
     * check whether the access is allowed or not based on the permission assigned to a role
     *
     * @param perm              common properties permissions for an entity
     * @param userServerID      user identification for testing the ownership permissions
     * @param primaryTeamBIT    primary team bit for testing primary team membership permissions
     * @param secondaryTeamBITS secondary team bits for testing secondary teams membership permissions
     * @param statusBITS        status bits for testing permission on an operation given the status bits
     * @return boolean
     */
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

    /**
     * check whether the access is allowed or not based on the permission assigned to a role
     * used only for organization entity
     *
     * @param perm              common properties permissions for an entity
     * @param userServerID      user identification for testing the ownership permissions
     * @param primaryTeamBIT    primary team bit for testing primary team membership permissions
     * @param secondaryTeamBITS secondary team bits for testing secondary teams membership permissions
     * @return boolean
     */
    public static boolean isPermitted(cUnixPerm perm, String userServerID, int primaryTeamBIT,
                                      List<Integer> secondaryTeamBITS) {

        return (
                /* 1. the member is the owner and the owner permission bit is on */
                (perm.getUserOwnerID().equals(userServerID) &&
                        perm.getUnixpermBITS().contains(cSharedPreference.OWNER_READ)) ||
                        /* 2. the member is in the primary team and the primary team permission bit is on */
                        (perm.getTeamOwnerBIT() == primaryTeamBIT &&
                                perm.getUnixpermBITS().contains(cSharedPreference.PRIMARY_READ)) ||
                        /* 3. the member is in the secondary team and the second team permission bit is on */
                        (secondaryTeamBITS.contains(perm.getTeamOwnerBIT()) &&
                                perm.getUnixpermBITS().contains(cSharedPreference.SECONDARY_READ)) ||
                        /* 4. members is in the organization and the workplace permission bit is on */
                        (perm.getUnixpermBITS().contains(cSharedPreference.WORKPLACE_READ)));
    }

    /**
     * static class that holds permissions of an entity
     */
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

    /**
     * build permission menu tree
     *
     * @param context         context
     * @param permissionModel permission models
     * @return menu tree
     */
    public static List<cTreeModel> buildMenuPermissions(Context context,
                                                        cPermissionModel permissionModel) {
        List<cTreeModel> menuTreeModels = new ArrayList<>();

        /* menu items */
        String menu_items = "jsons/sys_menu_items.json";
        String menuItemsJSONString = cDatabaseUtils.loadJSONFromAsset(menu_items,
                context.getAssets());

        try {
            /* LEVEL 0: add permission node as the root node */
            int parentIndex = 0, childIndex = 0;
            menuTreeModels.add(new cTreeModel(parentIndex, -1, 0,
                    new cPermissionModel(permissionModel)));
            int parentPermissionIndex = childIndex;

            /* menu items */
            JSONObject jsonObjectMenuItem = new JSONObject(menuItemsJSONString);
            JSONArray jsonArrayMenu = jsonObjectMenuItem.getJSONArray("menuitems");
            List<String> main_menu_ids = getMainMenuIDs(permissionModel);
            for (int m = 0; m < jsonArrayMenu.length(); m++) {
                JSONObject jsonObjectMenu = jsonArrayMenu.getJSONObject(m);

                cMenuModel menuModel = new cMenuModel();
                // set the main menu item
                menuModel.setMenuServerID(jsonObjectMenu.getInt("menu_id"));
                menuModel.setName(jsonObjectMenu.getString("menu_name"));
                menuModel.setDescription(jsonObjectMenu.getString("menu_description"));
                menuModel.setChecked(main_menu_ids.contains(jsonObjectMenu.getString(
                        "menu_id")));

                /* LEVEL 1: add child main menu node to the parent permission node */
                childIndex = childIndex + 1;
                menuTreeModels.add(new cTreeModel(childIndex, parentPermissionIndex, 1,
                        menuModel));
                int parentMenuIndex = childIndex;

                JSONArray jsonArraySubMenu = jsonObjectMenu.getJSONArray("sub_menu");
                List<String> sub_menu_items = getSubMenuIDs(permissionModel,
                        String.valueOf(menuModel.getMenuServerID()));
                for (int j = 0; j < jsonArraySubMenu.length(); j++) {

                    JSONObject jsonObjectSubItem = jsonArraySubMenu.getJSONObject(j);
                    //if (sub_menu_items.contains(jsonObjectSubItem.getString(
                    //        "sub_menu_id"))) {
                    cMenuModel subMenuModel = new cMenuModel();
                    subMenuModel.setParentServerID(menuModel.getMenuServerID());
                    subMenuModel.setMenuServerID(jsonObjectSubItem.getInt("sub_menu_id"));
                    subMenuModel.setName(jsonObjectSubItem.getString("sub_menu_name"));
                    subMenuModel.setDescription(jsonObjectSubItem.getString(
                            "sub_menu_description"));
                    subMenuModel.setChecked(sub_menu_items.contains(jsonObjectSubItem.getString(
                            "sub_menu_id")));

                    /* LEVEL 2: add child sub menu node to the parent main menu node */
                    childIndex = childIndex + 1;
                    menuTreeModels.add(new cTreeModel(childIndex, parentMenuIndex, 2,
                            subMenuModel));
                    // }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return menuTreeModels;
    }

    /**
     * build module permissions
     *
     * @param context context
     * @param permissionModel permission model
     * @return tree model
     */
    public static List<cTreeModel> buildModulePermissions(Context context,
                                                          cPermissionModel permissionModel) {
        List<cTreeModel> moduleTreeModels = new ArrayList<>();

        /* permission tree with modules with their entities and menu items */
        String perm_tree = "jsons/sys_permission_tree.json";
        String permTreeJSONString = cDatabaseUtils.loadJSONFromAsset(perm_tree,
                context.getAssets());

        /* entity operations */
        String entity_ops = "jsons/sys_entity_operations.json";
        String entityOpsJSONString = cDatabaseUtils.loadJSONFromAsset(entity_ops,
                context.getAssets());

        /* operation status */
        String op_status = "jsons/sys_op_statuses.json";
        String opStatusJSONString = cDatabaseUtils.loadJSONFromAsset(op_status,
                context.getAssets());

        /* unix operations */
        String unix_ops = "jsons/sys_unix_operations.json";
        String unixOpsJSONString = cDatabaseUtils.loadJSONFromAsset(unix_ops,
                context.getAssets());

        try {
            /* LEVEL 0: add permission node as the root node */
            int parentIndex = 0, childIndex = 0;
            moduleTreeModels.add(new cTreeModel(parentIndex, -1, 0,
                    new cPermissionModel(permissionModel)));
            int parentPermissionIndex = childIndex;

            JSONObject jsonObjectPermTree = new JSONObject(permTreeJSONString);

            /* MENU ITEM OPTIONS */

            JSONArray jsonArrayMenuItems = jsonObjectPermTree.getJSONArray("menuitems");
            for(int i = 0; i < jsonArrayMenuItems.length(); i++) {

                JSONObject objectMenuItem = jsonArrayMenuItems.getJSONObject(i);

                cSectionModel menuItemsSection = new cSectionModel();

                // set the main menu item
                menuItemsSection.setModuleServerID(objectMenuItem.getString("menuitems_id"));
                menuItemsSection.setName(objectMenuItem.getString("menuitems_name"));

                /* LEVEL 1: add child module node to the parent permission node */
                childIndex = childIndex + 1;
                moduleTreeModels.add(new cTreeModel(childIndex, parentPermissionIndex, 1,
                        menuItemsSection));
                int parentMenuItemIndex = childIndex;

                JSONArray jsonArrayItems = objectMenuItem.getJSONArray("items");
                List<String> main_menu_ids = getMainMenuIDs(permissionModel);
                for (int j = 0; j < jsonArrayItems.length(); j++) {
                    JSONObject jsonObjectItem = jsonArrayItems.getJSONObject(j);

                    cMenuModel menuModel = new cMenuModel();
                    // set the main menu item
                    menuModel.setMenuServerID(jsonObjectItem.getInt("item_id"));
                    menuModel.setName(jsonObjectItem.getString("item_name"));
                    menuModel.setDescription(jsonObjectItem.getString("item_description"));
                    menuModel.setChecked(main_menu_ids.contains(jsonObjectItem.getString(
                            "item_id")));

                    /* LEVEL 2: add child item to the parent menu items section node */
                    childIndex = childIndex + 1;
                    moduleTreeModels.add(new cTreeModel(childIndex, parentMenuItemIndex, 2,
                            menuModel));
                    int parentItemIndex = childIndex;

                    JSONArray jsonArraySubItem = jsonObjectItem.getJSONArray("sub_item");
                    List<String> sub_menu_items = getSubMenuIDs(permissionModel,
                            String.valueOf(menuModel.getMenuServerID()));
                    for (int k = 0; k < jsonArraySubItem.length(); k++) {

                        JSONObject jsonObjectSubItem = jsonArraySubItem.getJSONObject(k);

                        cMenuModel subMenuModel = new cMenuModel();
                        // set sub menu item
                        subMenuModel.setParentServerID(menuModel.getMenuServerID());
                        subMenuModel.setMenuServerID(jsonObjectSubItem.getInt("sub_item_id"));
                        subMenuModel.setName(jsonObjectSubItem.getString("sub_item_name"));
                        subMenuModel.setDescription(jsonObjectSubItem.getString(
                                "sub_item_description"));
                        subMenuModel.setChecked(sub_menu_items.contains(jsonObjectSubItem.getString(
                                "sub_item_id")));

                        /* LEVEL 3: add child sub menu node to the parent main menu node */
                        childIndex = childIndex + 1;
                        moduleTreeModels.add(new cTreeModel(childIndex, parentItemIndex, 3,
                                subMenuModel));
                    }
                }
            }

            /* ENTITY MODULES */

            List<String> module_ids = getModuleIDs(permissionModel);
            JSONArray jsonArrayModules = jsonObjectPermTree.getJSONArray("entitymodules");
            for (int i = 0; i < jsonArrayModules.length(); i++) {
                JSONObject objectModule = jsonArrayModules.getJSONObject(i);

                cSectionModel moduleSection = new cSectionModel();
                moduleSection.setModuleServerID(objectModule.getString("module_id"));
                moduleSection.setName(objectModule.getString("module_name"));
                moduleSection.setChecked(module_ids.contains(objectModule.getString(
                        "module_id")));

                /* LEVEL 4: add child module node to the parent permission node */
                childIndex = childIndex + 1;
                moduleTreeModels.add(new cTreeModel(childIndex, parentPermissionIndex, 4,
                        moduleSection));
                int parentModuleIndex = childIndex;

                /* entities */
                JSONArray jsonArrayEntities = objectModule.getJSONArray("entities");
                List<String> entity_ids = getEntityIDs(permissionModel,
                        moduleSection.getModuleServerID());
                for (int j = 0; j < jsonArrayEntities.length(); j++) {
                    JSONObject objectEntity = jsonArrayEntities.getJSONObject(j);

                    cEntityModel entityModel = new cEntityModel();
                    entityModel.setEntityServerID(objectEntity.getString("entity_id"));
                    entityModel.setName(objectEntity.getString("entity_name"));
                    entityModel.setDescription(objectEntity.getString("entity_description"));
                    entityModel.setChecked(entity_ids.contains(objectEntity.getString(
                            "entity_id")));

                    /* LEVEL 5: add child entity node to the parent module node */
                    childIndex = childIndex + 1;
                    moduleTreeModels.add(new cTreeModel(childIndex, parentModuleIndex, 5,
                            entityModel));
                    int parentEntityIndex = childIndex;


                    /* LEVEL 6: add child entity operation node to the parent entity node */
                    /* entity operation section model */
                    cSectionModel entityOperationSection = new cSectionModel();
                    entityOperationSection.setName(entityModel.getName()+" "+"OPERATIONS");

                    childIndex = childIndex + 1;
                    moduleTreeModels.add(new cTreeModel(childIndex, parentEntityIndex, 6,
                            entityOperationSection));
                    int parentEntityOperationSectionIndex = childIndex;

                    /* entity operations */
                    JSONObject jsonObjectEntityOps = new JSONObject(entityOpsJSONString);
                    JSONArray jsonArrayOps = jsonObjectEntityOps.getJSONArray("operations");
                    List<String> op_ids = getOperationIDs(permissionModel,
                            moduleSection.getModuleServerID(), entityModel.getEntityServerID());
                    for (int k = 0; k < jsonArrayOps.length(); k++) {
                        JSONObject objectOps = jsonArrayOps.getJSONObject(k);

                        cOperationModel operationModel = new cOperationModel();
                        operationModel.setOperationServerID(objectOps.getString(
                                "entity_op_id"));
                        operationModel.setName(objectOps.getString(
                                "entity_op_name"));
                        operationModel.setDescription(objectOps.getString(
                                "entity_op_description"));
                        operationModel.setChecked(op_ids.contains(objectOps.getString(
                                "entity_op_id")));

                        // LEVEL 7: add child entity operation node to the parent entity operation
                        // section node
                        childIndex = childIndex + 1;
                        moduleTreeModels.add(new cTreeModel(childIndex,
                                parentEntityOperationSectionIndex, 7, operationModel));
                        int parentEntityOperationIndex = childIndex;

                        /* operation status */
                        List<cStatusModel> collection = new ArrayList<>();
                        cOperationStatusCollection operationStatusCollection;
                        operationStatusCollection = new cOperationStatusCollection();

                        JSONObject jsonObjectOpStatus = new JSONObject(opStatusJSONString);
                        JSONArray jsonArrayStatus = jsonObjectOpStatus.getJSONArray(
                                "statuses");
                        List<String> status_ids = getStatusIDs(permissionModel,
                                moduleSection.getModuleServerID(), entityModel.getEntityServerID(),
                                operationModel.getOperationServerID());
                        for (int l = 0; l < jsonArrayStatus.length(); l++) {
                            JSONObject objectStatus = jsonArrayStatus.getJSONObject(l);

                            cStatusModel statusModel = new cStatusModel();
                            statusModel.setStatusServerID(objectStatus.getString(
                                    "status_id"));
                            statusModel.setName(objectStatus.getString("status_name"));
                            statusModel.setDescription(objectStatus.getString(
                                    "status_description"));
                            statusModel.setChecked(status_ids.contains(objectStatus.getString(
                                    "status_id")));

                            collection.add(statusModel);

                        }

                        // LEVEL 8: add child status node to the parent entity operation node
                        operationStatusCollection.setStatusCollection(collection);
                        childIndex = childIndex + 1;
                        moduleTreeModels.add(new cTreeModel(childIndex,
                                parentEntityOperationIndex, 8, operationStatusCollection));
                    }

                    /* LEVEL 9: add child unix operation node to the parent entity node */

                    /* unix operation section */
                    cSectionModel unixOperationSection = new cSectionModel();
                    unixOperationSection.setName(entityModel.getName()+" "+"PERMISSIONS");

                    childIndex = childIndex + 1;
                    moduleTreeModels.add(new cTreeModel(childIndex, parentEntityIndex, 9,
                            unixOperationSection));
                    int parentUnixOperationSectionIndex = childIndex;

                    /* unix operations */
                    List<cUnixOperationModel> collection = new ArrayList<>();
                    cUnixOperationCollection unixOperationCollection;
                    unixOperationCollection = new cUnixOperationCollection();

                    JSONObject jsonObjectUnixOps = new JSONObject(unixOpsJSONString);
                    JSONArray jsonArrayUnixOps = jsonObjectUnixOps.getJSONArray(
                            "unixoperations");

                    List<String> unix_op_ids = getUnixOperationIDs(permissionModel,
                            moduleSection.getModuleServerID(), entityModel.getEntityServerID());

                    for (int k = 0; k < jsonArrayUnixOps.length(); k++) {
                        JSONObject objectUnixOps = jsonArrayUnixOps.getJSONObject(k);

                        cUnixOperationModel unixOperationModel = new cUnixOperationModel();

                        unixOperationModel.setOperationServerID(objectUnixOps.getString(
                                "unix_op_id"));
                        unixOperationModel.setName(objectUnixOps.getString(
                                "unix_op_name"));
                        unixOperationModel.setDescription(objectUnixOps.getString(
                                "unix_op_description"));
                        unixOperationModel.setChecked(unix_op_ids.contains(
                                objectUnixOps.getString("unix_op_id")));

                        collection.add(unixOperationModel);
                    }

                    unixOperationCollection.setUnixOperationModels(collection);
                    // LEVEL 10: add child unix operation node to the parent entity node
                    childIndex = childIndex + 1;
                    moduleTreeModels.add(new cTreeModel(childIndex, parentUnixOperationSectionIndex,
                            10, unixOperationCollection));

                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return moduleTreeModels;
    }

//    public static List<cTreeModel> buildPermissionTree(Context context, cRoleModel roleModel,
//                                                       cPermissionModel permissionModel) {
//        List<cTreeModel> treeModels = new ArrayList<>();
//
//        Gson gson = new Gson();
//
//        /* permission tree */
//        String perm_tree = "jsons/sys_permission_tree.json";
//        String permTreeJSONString = cDatabaseUtils.loadJSONFromAsset(perm_tree,
//                context.getAssets());
//
//        /* entity operations */
//        String entity_ops = "jsons/sys_entity_operations.json";
//        String entityOpsJSONString = cDatabaseUtils.loadJSONFromAsset(entity_ops,
//                context.getAssets());
//        /* operation status */
//        String op_status = "jsons/sys_op_statuses.json";
//        String opStatusJSONString = cDatabaseUtils.loadJSONFromAsset(op_status,
//                context.getAssets());
//
//        /* unix operations */
//        String unix_ops = "jsons/sys_unix_operations.json";
//        String unixOpsJSONString = cDatabaseUtils.loadJSONFromAsset(unix_ops,
//                context.getAssets());
//
//        /* menu items */
//        String menu_items = "jsons/sys_menu_items.json";
//        String menuItemsJSONString = cDatabaseUtils.loadJSONFromAsset(menu_items,
//                context.getAssets());
//
//        try {
//            /* LEVEL 0: add root role node */
//            int parentIndex = 0, childIndex = 0;
//            //treeModels.add(new cTreeModel(childIndex, -1, 0,
//            //        new cRoleModel(roleModel)));
//
//            /* LEVEL 0: add child permission node to the parent role node */
//            //childIndex = parentIndex + 1;
//            treeModels.add(new cTreeModel(childIndex, -1, 0,
//                    new cPermissionModel(permissionModel)));
//            int parentPermissionIndex = childIndex;
//
//            /* modules */
//            List<String> module_ids = getModuleIDs(permissionModel);
//
//            JSONObject jsonObjectPermTree = new JSONObject(permTreeJSONString);
//            JSONArray jsonArrayModules = jsonObjectPermTree.getJSONArray("modules");
//            for (int i = 0; i < jsonArrayModules.length(); i++) {
//                JSONObject objectModule = jsonArrayModules.getJSONObject(i);
//
//                cSectionModel moduleModel = new cSectionModel();
//                moduleModel.setModuleServerID(objectModule.getString("module_id"));
//                moduleModel.setName(objectModule.getString("module_name"));
//                moduleModel.setChecked(module_ids.contains(objectModule.getString(
//                        "module_id")));
//
//                /* LEVEL 1: add child module node to the parent permission node */
//                childIndex = childIndex + 1;
//                treeModels.add(new cTreeModel(childIndex, parentPermissionIndex, 1,
//                        moduleModel));
//                int parentModuleIndex = childIndex;
//
//                /* entities */
//                JSONArray jsonArrayEntities = objectModule.getJSONArray("entities");
//                List<String> entity_ids = getEntityIDs(permissionModel,
//                        moduleModel.getModuleServerID());
//                for (int j = 0; j < jsonArrayEntities.length(); j++) {
//                    JSONObject objectEntity = jsonArrayEntities.getJSONObject(j);
//
//                    cEntityModel entityModel = new cEntityModel();
//                    entityModel.setEntityServerID(objectEntity.getString("entity_id"));
//                    entityModel.setName(objectEntity.getString("entity_name"));
//                    entityModel.setDescription(objectEntity.getString("entity_description"));
//                    entityModel.setChecked(entity_ids.contains(objectEntity.getString(
//                            "entity_id")));
//
//                    /* LEVEL 2: add child entity node to the parent module node */
//                    childIndex = childIndex + 1;
//                    treeModels.add(new cTreeModel(childIndex, parentModuleIndex, 2, entityModel));
//                    int parentEntityIndex = childIndex;
//
//                    /* entity operations */
//                    JSONObject jsonObjectEntityOps = new JSONObject(entityOpsJSONString);
//                    JSONArray jsonArrayOps = jsonObjectEntityOps.getJSONArray("operations");
//                    List<String> op_ids = getOperationIDs(permissionModel,
//                            moduleModel.getModuleServerID(), entityModel.getEntityServerID());
//                    for (int k = 0; k < jsonArrayOps.length(); k++) {
//                        JSONObject objectOps = jsonArrayOps.getJSONObject(k);
//
//                        cOperationModel operationModel = new cOperationModel();
//                        operationModel.setOperationServerID(objectOps.getString(
//                                "entity_op_id"));
//                        operationModel.setName(objectOps.getString(
//                                "entity_op_name"));
//                        operationModel.setDescription(objectOps.getString(
//                                "entity_op_description"));
//                        operationModel.setChecked(op_ids.contains(objectOps.getString(
//                                "entity_op_id")));
//
//                        /* LEVEL 3: add child entity operation node to the parent entity node */
//                        childIndex = childIndex + 1;
//                        treeModels.add(new cTreeModel(childIndex, parentEntityIndex, 3,
//                                operationModel));
//                        int parentEntityOperationIndex = childIndex;
//
//                        /* operation status */
//                        JSONObject jsonObjectOpStatus = new JSONObject(opStatusJSONString);
//                        JSONArray jsonArrayStatus = jsonObjectOpStatus.getJSONArray(
//                                "statuses");
//                        List<String> status_ids = getStatusIDs(permissionModel,
//                                moduleModel.getModuleServerID(), entityModel.getEntityServerID(),
//                                operationModel.getOperationServerID());
//                        for (int l = 0; l < jsonArrayStatus.length(); l++) {
//                            JSONObject objectStatus = jsonArrayStatus.getJSONObject(l);
//
//                            cStatusModel statusModel = new cStatusModel();
//                            statusModel.setStatusServerID(objectStatus.getString(
//                                    "status_id"));
//                            statusModel.setName(objectStatus.getString("status_name"));
//                            statusModel.setDescription(objectStatus.getString(
//                                    "status_description"));
//                            statusModel.setChecked(status_ids.contains(objectStatus.getString(
//                                    "status_id")));
//
//
//                            /* LEVEL 4: add child status node to the parent entity operation node */
//                            childIndex = childIndex + 1;
//                            treeModels.add(new cTreeModel(childIndex, parentEntityOperationIndex,
//                                    4, statusModel));
//                            //parentIndex = childIndex;
//                        }
//                    }
//
//                    /* unix operations */
//                    JSONObject jsonObjectUnixOps = new JSONObject(unixOpsJSONString);
//                    JSONArray jsonArrayUnixOps = jsonObjectUnixOps.getJSONArray(
//                            "unixoperations");
//                    List<String> unix_op_ids = getUnixOperationIDs(permissionModel,
//                            moduleModel.getModuleServerID(), entityModel.getEntityServerID());
//
//                    for (int k = 0; k < jsonArrayUnixOps.length(); k++) {
//                        JSONObject objectUnixOps = jsonArrayUnixOps.getJSONObject(k);
//
//                        cUnixOperationModel unixOperationModel = new cUnixOperationModel();
//                        unixOperationModel.setOperationServerID(objectUnixOps.getString(
//                                "unix_op_id"));
//                        unixOperationModel.setName(objectUnixOps.getString(
//                                "unix_op_name"));
//                        unixOperationModel.setDescription(objectUnixOps.getString(
//                                "unix_op_description"));
//                        unixOperationModel.setChecked(unix_op_ids.contains(objectUnixOps.getString(
//                                "unix_op_id")));
//
//                        /* LEVEL 5: add child unix operation node to the parent entity node */
//                        childIndex = childIndex + 1;
//                        treeModels.add(new cTreeModel(childIndex, parentEntityIndex, 5,
//                                unixOperationModel));
//                        //parentIndex = childIndex;
//                    }
//                }
//
//                /* menu items */
//                JSONObject jsonObjectMenuItem = new JSONObject(menuItemsJSONString);
//                JSONArray jsonArrayMenu = jsonObjectMenuItem.getJSONArray("menuitems");
//                List<String> main_menu_ids = getMainMenuIDs(permissionModel);
//                for (int m = 0; m < jsonArrayMenu.length(); m++) {
//                    JSONObject jsonObjectMenu = jsonArrayMenu.getJSONObject(m);
//
//                    cMenuModel menuModel = new cMenuModel();
//                    // set the main menu item
//                    menuModel.setMenuServerID(jsonObjectMenu.getInt("menu_id"));
//                    menuModel.setName(jsonObjectMenu.getString("menu_name"));
//                    menuModel.setDescription(jsonObjectMenu.getString("menu_description"));
//                    menuModel.setChecked(main_menu_ids.contains(jsonObjectMenu.getString(
//                            "menu_id")));
//
//                    /* LEVEL 6: add child main menu node to the parent permission node */
//                    childIndex = childIndex + 1;
//                    treeModels.add(new cTreeModel(childIndex, parentPermissionIndex, 6, menuModel));
//                    int parentMenuIndex = childIndex;
//
//                    JSONArray jsonArraySubMenu = jsonObjectMenu.getJSONArray("sub_menu");
//                    List<String> sub_menu_items = getSubMenuIDs(permissionModel,
//                            String.valueOf(menuModel.getMenuServerID()));
//                    for (int j = 0; j < jsonArraySubMenu.length(); j++) {
//
//                        JSONObject jsonObjectSubItem = jsonArraySubMenu.getJSONObject(j);
//                        if (sub_menu_items.contains(jsonObjectSubItem.getString(
//                                "sub_menu_id"))) {
//                            cMenuModel subMenuModel = new cMenuModel();
//                            subMenuModel.setParentServerID(menuModel.getMenuServerID());
//                            subMenuModel.setMenuServerID(jsonObjectSubItem.getInt("sub_menu_id"));
//                            subMenuModel.setName(jsonObjectSubItem.getString("sub_menu_name"));
//                            subMenuModel.setDescription(jsonObjectSubItem.getString(
//                                    "sub_menu_description"));
//
//                            /* LEVEL 7: add child sub menu node to the parent main menu node */
//                            childIndex = childIndex + 1;
//                            treeModels.add(new cTreeModel(childIndex, parentMenuIndex, 7,
//                                    subMenuModel));
//                            //parentIndex = childIndex;
//                            //Log.d("TREE MODEL ","RESULTS ==>> "+childIndex);
//                        }
//                    }
//                }
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        return treeModels;
//    }

    private static List<String> getModuleIDs(cPermissionModel permissionModel) {
        Map<String, List<cEntityModel>> perm_modules = permissionModel.getEntitymodules();
        return new ArrayList<>(perm_modules.keySet());
    }

    private static List<String> getEntityIDs(cPermissionModel permissionModel, String moduleID) {
        List<String> entity_ids = new ArrayList<>();
        for (Map.Entry<String, List<cEntityModel>> entry : permissionModel.getEntitymodules().
                entrySet()) {
            if (entry.getKey().equals(moduleID)) {
                for (cEntityModel entityModel : entry.getValue()) {
                    entity_ids.add(entityModel.getEntityServerID());
                }
                break;
            }
        }
        return entity_ids;
    }

    private static List<String> getOperationIDs(cPermissionModel perm, String moduleID,
                                                String entityID) {
        List<String> ops_ids = new ArrayList<>();
        for (Map.Entry<String, List<cEntityModel>> entryModule : perm.getEntitymodules().
                entrySet()) {
            if (entryModule.getKey().equals(moduleID)) {
                for (cEntityModel entityModel : entryModule.getValue()) {
                    if (entityModel.getEntityServerID().equals(entityID)) {
                        for (Map.Entry<String, List<Integer>> entry : entityModel.getEntityperms().
                                entrySet()) {
                            ops_ids.add(entry.getKey());
                        }
                        break;
                    }
                }
                break;
            }
        }
        return ops_ids;
    }

    private static List<String> getStatusIDs(cPermissionModel perm, String moduleID,
                                             String entityID, String operationID) {
        List<String> status_ids = new ArrayList<>();
        for (Map.Entry<String, List<cEntityModel>> entryModule : perm.getEntitymodules().
                entrySet()) {
            if (entryModule.getKey().equals(moduleID)) {
                for (cEntityModel entityModel : entryModule.getValue()) {
                    if (entityModel.getEntityServerID().equals(entityID)) {
                        for (Map.Entry<String, List<Integer>> entry : entityModel.getEntityperms().
                                entrySet()) {
                            if (String.valueOf(operationID).equals(entry.getKey())) {
                                for (Integer status : entry.getValue()) {
                                    status_ids.add(String.valueOf(status));
                                }
                                break;
                            }
                        }
                        break;
                    }
                }
                break;
            }
        }
        return status_ids;
    }

    private static List<String> getUnixOperationIDs(cPermissionModel perm, String moduleID,
                                                    String entityID) {
        List<String> unix_ops_ids = new ArrayList<>();

        for (Map.Entry<String, List<cEntityModel>> entryModule : perm.getEntitymodules().
                entrySet()) {
            if (entryModule.getKey().equals(moduleID)) {
                for (cEntityModel entityModel : entryModule.getValue()) {
                    if (entityModel.getEntityServerID().equals(entityID)) {
                        for (Integer unix_op : entityModel.getUnixperms()) {
                            unix_ops_ids.add(String.valueOf(unix_op));
                        }
                        break;
                    }
                }
                break;
            }
        }
        return unix_ops_ids;
    }


    private static List<String> getMainMenuIDs(cPermissionModel permissionModel) {
        List<String> menu_ids = new ArrayList<>();
        for (Map.Entry<String, List<Integer>> entry : permissionModel.getMenuitems().
                entrySet()) {
            menu_ids.add(entry.getKey());
        }
        return menu_ids;
    }

    private static List<String> getSubMenuIDs(cPermissionModel permissionModel, String menuID) {
        List<String> sub_menu_ids = new ArrayList<>();
        for (Map.Entry<String, List<Integer>> entry : permissionModel.getMenuitems().
                entrySet()) {
            if (entry.getKey().equals(menuID)) {
                for (Integer sub_menu : entry.getValue()) {
                    sub_menu_ids.add(String.valueOf(sub_menu));
                }
                break;
            }
        }
        return sub_menu_ids;
    }
}
