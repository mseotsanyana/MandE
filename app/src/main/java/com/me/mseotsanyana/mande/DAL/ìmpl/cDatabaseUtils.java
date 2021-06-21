package com.me.mseotsanyana.mande.DAL.ìmpl;

import android.content.Context;
import android.content.res.AssetManager;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.me.mseotsanyana.mande.BLL.model.session.cFeatureModel;
import com.me.mseotsanyana.mande.BLL.model.session.cMenuModel;
import com.me.mseotsanyana.mande.BLL.model.session.cPermissionModel;
import com.me.mseotsanyana.mande.BLL.model.session.cPlanModel;
import com.me.mseotsanyana.mande.BLL.model.session.cPrivilegeModel;
import com.me.mseotsanyana.mande.BLL.model.session.cRoleModel;
import com.me.mseotsanyana.mande.BLL.model.session.cTeamModel;
import com.me.mseotsanyana.mande.BLL.model.utils.cCommonPropertiesModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
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
        String plans = "jsons/app_plans.json";
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

    public static List<cMenuModel> getDefaultMenuModelSet(Context context) {
        List<cMenuModel> menuModels = new ArrayList<>();

        String menu = "jsons/default_menu.json";
        String menuJSONString = cDatabaseUtils.loadJSONFromAsset(menu, context.getAssets());

        try {
            assert menuJSONString != null;
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
                    subMenuModel.setDescription(jsonObjectSubItem.getString("sub_description"));
                    subMenuModels.add(subMenuModel);
                }

                cMenuModel menuModel = new cMenuModel();
                // set the main menu item
                menuModel.setMenuServerID(jsonObject.getInt("id"));
                menuModel.setName(jsonObject.getString("item"));
                menuModel.setDescription(jsonObject.getString("description"));

                // sub menu items to the main menu item
                menuModel.setSubMenuModels(subMenuModels);

                menuModels.add(menuModel);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return menuModels;
    }


    public static List<cMenuModel> getMenuModelSet(Context context, Set<Integer> menuSet) {
        List<cMenuModel> menuModels = new ArrayList<>();

        String menu = "jsons/admin_menu.json";
        String menuJSONString = cDatabaseUtils.loadJSONFromAsset(menu, context.getAssets());

        try {

            JSONObject jsonObjectMenu = new JSONObject(menuJSONString);
            JSONArray jsonArrayMenu = jsonObjectMenu.getJSONArray("menu");
            for (int i = 0; i < jsonArrayMenu.length(); i++) {
                ArrayList<cMenuModel> subMenuModels = new ArrayList<>();
                JSONObject jsonObject = jsonArrayMenu.getJSONObject(i);

                if (menuSet.contains(jsonObject.getInt("id"))) {
                    JSONArray jsonArraySubMenu = jsonObject.getJSONArray("sub_menu");
                    for (int j = 0; j < jsonArraySubMenu.length(); j++) {
                        cMenuModel subMenuModel = new cMenuModel();
                        JSONObject jsonObjectSubItem = jsonArraySubMenu.getJSONObject(j);
                        subMenuModel.setMenuServerID(jsonObjectSubItem.getInt("sub_id"));
                        subMenuModel.setName(jsonObjectSubItem.getString("sub_item"));
                        subMenuModel.setDescription(jsonObjectSubItem.getString("sub_description"));
                        subMenuModels.add(subMenuModel);
                    }

                    cMenuModel menuModel = new cMenuModel();
                    // set the main menu item
                    menuModel.setMenuServerID(jsonObject.getInt("id"));
                    menuModel.setName(jsonObject.getString("item"));
                    menuModel.setDescription(jsonObject.getString("description"));
                    // sub menu items to the main menu item
                    menuModel.setSubMenuModels(subMenuModels);
                    // add menu items in the list
                    menuModels.add(menuModel);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return menuModels;
    }

    public static Map<cMenuModel, Map<String, cMenuModel>> getAdminMenuModelSet(
            Context context) {
        Map<cMenuModel, Map<String, cMenuModel>> menuModels = new HashMap<>();

        String menu = "jsons/admin_menu.json";
        String menuJSONString = cDatabaseUtils.loadJSONFromAsset(menu, context.getAssets());

        try {
            assert menuJSONString != null;
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
                    subMenuModel.setDescription(jsonObjectSubItem.getString("sub_description"));

                    subMenuMap.put(jsonObjectSubItem.getString("sub_id"), subMenuModel);
                }

                cMenuModel menuModel = new cMenuModel();

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

    public static cCommonPropertiesModel getColumnModel(Context context) {
        cCommonPropertiesModel commonPropertiesModel = new cCommonPropertiesModel();

        // read json file
        String cproperties = "jsons/common_properties.json";
        String ccolumnsJSONString = cDatabaseUtils.loadJSONFromAsset(cproperties,
                context.getAssets());

        try {
            //assert ccolumnsJSONString != null;
            JSONObject jsonObjectColumn = new JSONObject(ccolumnsJSONString);
            JSONObject jsonColumn = jsonObjectColumn.getJSONObject("cproperties");

            commonPropertiesModel.setCteamOwnerBIT(jsonColumn.getInt("cteam"));
            commonPropertiesModel.setCstatusesBITS(jsonColumn.getInt("cstatuses"));
            JSONArray jsonArrayPerm = jsonColumn.getJSONArray("cunixperms");

            List<Integer> unixperm = new ArrayList<>();
            for (int j = 0; j < jsonArrayPerm.length(); j++) {
                JSONObject jsonObjectPerm = jsonArrayPerm.getJSONObject(j);
                unixperm.add(jsonObjectPerm.getInt("unix_op_id"));
            }
            commonPropertiesModel.setCunixpermsBITS(unixperm);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return commonPropertiesModel;
    }

    public static cTeamModel getAdminTeamModel(Context context, String organizationID,
                                               cCommonPropertiesModel columnModel) {
        cTeamModel teamModel = new cTeamModel();

        // read json file
        String team = "jsons/admin_team.json";
        String teamJSONString = cDatabaseUtils.loadJSONFromAsset(team, context.getAssets());

        try {
            teamModel.setOrganizationServerID(organizationID);

            assert teamJSONString != null;
            JSONObject jsonObjectTeam = new JSONObject(teamJSONString);
            JSONObject jsonTeam = jsonObjectTeam.getJSONObject("team");

            teamModel.setCompositeServerID(organizationID + "_" + jsonTeam.getString("id"));
            teamModel.setOrganizationServerID(organizationID);
            teamModel.setTeamServerID(jsonTeam.getString("id"));

            teamModel.setName(jsonTeam.getString("name"));
            teamModel.setDescription(jsonTeam.getString("description"));

            // update team's common columns
            teamModel.setOwnerID(columnModel.getCownerID());
            teamModel.setOrgOwnerID(columnModel.getCorgOwnerID());
            teamModel.setTeamOwnerBIT(columnModel.getCteamOwnerBIT());
            teamModel.setUnixpermsBITS(columnModel.getCunixpermsBITS());
            teamModel.setStatusesBITS(columnModel.getCstatusesBITS());

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
            assert roleJSONString != null;
            JSONObject jsonObjectRole = new JSONObject(roleJSONString);
            JSONObject jsonRole = jsonObjectRole.getJSONObject("role");

            roleModel.setName(jsonRole.getString("name"));
            roleModel.setDescription(jsonRole.getString("description"));

            // update privilege's common columns
            roleModel.setOwnerID(columnModel.getCownerID());
            roleModel.setOrgOwnerID(columnModel.getCorgOwnerID());
            roleModel.setTeamOwnerBIT(columnModel.getCteamOwnerBIT());
            roleModel.setUnixpermsBITS(columnModel.getCunixpermsBITS());
            roleModel.setStatusesBITS(columnModel.getCstatusesBITS());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return roleModel;
    }


    public static cPrivilegeModel getAdminPrivilegeModel(Context context, cCommonPropertiesModel columnModel) {
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

    public static cPermissionModel getAdminPermissions(Context context) {

        cPermissionModel permissionModel = null;

        Gson gson = new Gson();
        String perms = "jsons/admin_perms.json";
        String permsJSONString = cDatabaseUtils.loadJSONFromAsset(perms, context.getAssets());

        try {
            assert permsJSONString != null;
            JSONObject jsonObjectPerms = new JSONObject(permsJSONString);
            JSONArray jsonArrayModule = jsonObjectPerms.getJSONArray("modules");

            Map<String, Map<String, Map<String, List<Integer>>>> opStatusModuleMap = new HashMap<>();
            Map<String, Map<String, List<Integer>>> unixPermModuleMap = new HashMap<>();

            for (int i = 0; i < jsonArrayModule.length(); i++) {
                JSONObject jsonObjectModules = jsonArrayModule.getJSONObject(i);
                String moduleID = jsonObjectModules.getString("module_id");
                JSONArray jsonArrayEntity = jsonObjectModules.getJSONArray("entities");
                Map<String, Map<String, List<Integer>>> entityOpStatusMap = new HashMap<>();
                Map<String, List<Integer>> entityUnixPermMap = new HashMap<>();
                for (int j = 0; j < jsonArrayEntity.length(); j++) {
                    JSONObject jsonObjectEntities = jsonArrayEntity.getJSONObject(j);
                    String entityID = jsonObjectEntities.getString("entity_id");

                    // operation with list of statuses
                    JSONArray jsonArrayOpStatus = jsonObjectEntities.getJSONArray("operations");
                    Map<String, List<Integer>> operationStatusMap = new HashMap<>();
                    for (int k = 0; k < jsonArrayOpStatus.length(); k++) {
                        JSONObject jsonObjectOps = jsonArrayOpStatus.getJSONObject(k);
                        String operationID = jsonObjectOps.getString("op_id");

                        JSONArray jsonArrayStatus = jsonObjectOps.getJSONArray("status_ids");
                        List<Integer> statuses = new ArrayList<>();
                        for (int l = 0; l < jsonArrayStatus.length(); l++) {
                            int jsonObjectStatus = jsonArrayStatus.getInt(l);
                            statuses.add(jsonObjectStatus);
                        }

                        operationStatusMap.put(operationID, statuses);
                    }

                    // unix operations
                    JSONArray jsonArrayUnixPerm = jsonObjectEntities.getJSONArray("unixoperations");
                    List<Integer> unixPermList = new ArrayList<>();
                    for (int k = 0; k < jsonArrayUnixPerm.length(); k++) {
                        JSONObject jsonObjectUnixOps = jsonArrayUnixPerm.getJSONObject(k);
                        String unixPermID = jsonObjectUnixOps.getString("unix_op_id");
                        unixPermList.add(Integer.parseInt(unixPermID));
                    }

                    entityOpStatusMap.put(entityID, operationStatusMap);
                    entityUnixPermMap.put(entityID, unixPermList);
                }

                opStatusModuleMap.put(moduleID, entityOpStatusMap);
                unixPermModuleMap.put(moduleID, entityUnixPermMap);
            }

            permissionModel = new cPermissionModel(opStatusModuleMap, unixPermModuleMap);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return permissionModel;
    }


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


    public static HashMap<cMenuModel, Map<String, cMenuModel>> getDefaultMenuModelMap(Context context) {
        HashMap<cMenuModel, Map<String, cMenuModel>> menuModels = new HashMap<>();

        String menu = "jsons/default_menu.json";
        String menuJSONString = cDatabaseUtils.loadJSONFromAsset(menu, context.getAssets());

        try {
            assert menuJSONString != null;
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
                    subMenuModel.setDescription(jsonObjectSubItem.getString("sub_description"));

                    subMenuMap.put(jsonObjectSubItem.getString("sub_id"), subMenuModel);
                }

                cMenuModel menuModel = new cMenuModel();

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

    public static List<cPlanModel> getPlanModels(Context context) {
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

    public static List<cFeatureModel> getFeatureModels(Context context) {
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

    public static HashMap<String, List<cFeatureModel>> getPlanFeatureModels(Context context) {
        HashMap<String, List<cFeatureModel>> planFeatures = new HashMap<>();

        String plan = "jsons/plan_features.json";
        String planJSONString = cDatabaseUtils.loadJSONFromAsset(plan, context.getAssets());

        try {
            assert planJSONString != null;
            JSONObject jsonObjectPlan = new JSONObject(planJSONString);
            JSONArray jsonArrayPlan = jsonObjectPlan.getJSONArray("plans");
            for (int i = 0; i < jsonArrayPlan.length(); i++) {
                JSONObject jsonObject = jsonArrayPlan.getJSONObject(i);
                String planID = jsonObject.getString("id");

                JSONArray jsonArrayFeature = jsonObject.getJSONArray("features");
                List<cFeatureModel> featureModels = new ArrayList<>();
                for (int j = 0; j < jsonArrayFeature.length(); j++) {
                    JSONObject jsonObjectFeature = jsonArrayFeature.getJSONObject(j);

                    cFeatureModel featureModel = new cFeatureModel(
                            jsonObjectFeature.getInt("id"),
                            jsonObjectFeature.getString("name"),
                            jsonObjectFeature.getString("description")
                    );

                    featureModels.add(featureModel);
                }

                planFeatures.put(planID, featureModels);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return planFeatures;
    }


    public static List<Integer> convertBinary(int number) {
        //int binary[] = new int[40];
        List<Integer> binary_array = new ArrayList<>();
        List<Integer> binary = new ArrayList<>();
        //int index = 0;
        while (number > 0) {
            //binary[index++] = num%2;
            binary_array.add(number % 2);
            number = number / 2;
        }

        for (int i = binary_array.size() - 1; i >= 0; i--) {
            //System.out.print(binary[i]);
            binary_array.add(i);
        }

        return binary;
    }


//    public static List<cUnixPermissionModel> getAdminUnixOperation(Context context) {
//        List<cUnixPermissionModel> unixOperationModels = new ArrayList<>();
//
//        String perms = "jsons/unixoperations.json";
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
}
