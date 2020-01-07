package com.me.mseotsanyana.mande.BRBAC.DAL;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.google.gson.Gson;
import com.me.mseotsanyana.mande.BRBAC.BLL.cMenuDomain;
import com.me.mseotsanyana.mande.STORAGE.database.cSQLDBHelper;
import com.me.mseotsanyana.mande.UTILITY.cConstant;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by mseotsanyana on 2017/08/24.
 */

public class cRoleDBA {
    private static SimpleDateFormat sdf = cConstant.FORMAT_DATE;
    private static String TAG = cRoleDBA.class.getSimpleName();

    // an object of the database helper
    private cSQLDBHelper dbHelper;

    private cOrganizationDBA organizationDBA;
    private cMenuDBA menuDBA;

    public cRoleDBA(Context context) {
        dbHelper        = new cSQLDBHelper(context);
        organizationDBA = new cOrganizationDBA(context);
        menuDBA         = new cMenuDBA(context);
    }

    /* ############################################# CREATE ACTIONS ############################################# */

    /**
     * Add role from an excel file
     * @param roleModel
     * @return Boolean
     */
    public boolean addRoleFromExcel(cRoleModel roleModel,  ArrayList<Integer> users,
                                    ArrayList<Integer> menus) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_ID, roleModel.getRoleID());
        cv.put(cSQLDBHelper.KEY_ORGANIZATION_FK_ID, roleModel.getOrganizationID());
        cv.put(cSQLDBHelper.KEY_PRIVILEGE_FK_ID, roleModel.getPrivilegeID());
        cv.put(cSQLDBHelper.KEY_NAME, roleModel.getName());
        cv.put(cSQLDBHelper.KEY_DESCRIPTION, roleModel.getDescription());

        // insert outcome record
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblROLE, null, cv) < 0) {
                return false;
            }

            // add user roles
            for(int user: users) {
                if (addUserRole(user, roleModel.getOrganizationID(), roleModel.getRoleID()))
                    continue;
                else
                    return false;
            }

            // add menu roles
            for(int menu: menus) {
                if (addMenuRole(menu, roleModel.getRoleID(), roleModel.getOrganizationID()))
                    continue;
                else
                    return false;
            }

        } catch (Exception e) {
            Log.d(TAG,"Exception in adding: "+e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }

    public boolean addRole(cRoleModel groupModel, int organizationID) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_ID, groupModel.getRoleID());
        cv.put(cSQLDBHelper.KEY_ORGANIZATION_FK_ID, organizationID);
        cv.put(cSQLDBHelper.KEY_OWNER_ID, groupModel.getOwnerID());
        cv.put(cSQLDBHelper.KEY_GROUP_BITS, groupModel.getGroupBITS());
        cv.put(cSQLDBHelper.KEY_PERMS_BITS, groupModel.getPermsBITS());
        cv.put(cSQLDBHelper.KEY_STATUS_BITS, groupModel.getStatusBITS());
        cv.put(cSQLDBHelper.KEY_NAME, groupModel.getName());
        cv.put(cSQLDBHelper.KEY_DESCRIPTION, groupModel.getDescription());

        // insert outcome record
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblROLE, null, cv) < 0) {
                return false;
            }
        } catch (Exception ex) {
            Log.d("Exception in importing ", ex.getMessage().toString());
        }

        // close the database connection
        db.close();

        return true;
    }

    public boolean addUserRole(int userID, int organizationID, int roleID) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(cSQLDBHelper.KEY_USER_FK_ID, userID);
        cv.put(cSQLDBHelper.KEY_ORGANIZATION_FK_ID, organizationID);
        cv.put(cSQLDBHelper.KEY_ROLE_FK_ID, roleID);

        if (db.insert(cSQLDBHelper.TABLE_tblUSER_ROLE, null, cv) < 0) {
            return false;
        }

        return true;
    }

    public boolean addMenuRole(int menuID, int roleID, int organizationID) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(cSQLDBHelper.KEY_MENU_FK_ID, menuID);
        cv.put(cSQLDBHelper.KEY_ROLE_FK_ID, roleID);
        cv.put(cSQLDBHelper.KEY_ORGANIZATION_FK_ID, organizationID);

        if (db.insert(cSQLDBHelper.TABLE_tblMENU_ROLE, null, cv) < 0) {
            return false;
        }

        return true;
    }

    /* ############################################# READ ACTIONS ############################################# */

    /**
     * Read and filter roles
     * @param userID
     * @param orgID
     * @param primaryRole
     * @param secondaryRoles
     * @param operationBITS
     * @param statusBITS
     * @return List
     */
    public Set<cRoleModel> getRoleModelSet(
            int userID, int orgID, int primaryRole,
            int secondaryRoles, int operationBITS, int statusBITS) {

        Set<cRoleModel> roleModelSet = new HashSet<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String selectQuery = "SELECT * FROM "+ cSQLDBHelper.TABLE_tblROLE +
                " WHERE ((("+cSQLDBHelper.KEY_GROUP_BITS +" & ?) != 0) " +
                " OR (("+cSQLDBHelper.KEY_OWNER_ID+" = ?) " +
                " AND (("+cSQLDBHelper.KEY_PERMS_BITS+" & ?) != 0)) " +
                " OR ((("+cSQLDBHelper.KEY_GROUP_BITS +" & ?) != 0) " +
                " AND (("+cSQLDBHelper.KEY_PERMS_BITS+" & ?) != 0)))";

        Cursor cursor = db.rawQuery(selectQuery, new String[]{
                String.valueOf(primaryRole),
                String.valueOf(userID),String.valueOf(operationBITS),
                String.valueOf(secondaryRoles),String.valueOf(operationBITS)});

        try {
            if (cursor.moveToFirst()) {
                do {
                    cRoleModel role = new cRoleModel();

                    role.setRoleID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ID)));
                    role.setOrganizationID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ORGANIZATION_FK_ID)));
                    role.setServerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_SERVER_ID)));
                    role.setOwnerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OWNER_ID)));
                    role.setOrgID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ORG_ID)));
                    role.setGroupBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_GROUP_BITS)));
                    role.setPermsBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PERMS_BITS)));
                    role.setStatusBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_STATUS_BITS)));
                    role.setName(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_NAME)));
                    role.setDescription(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_DESCRIPTION)));
                    role.setCreatedDate(
                            Timestamp.valueOf(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_CREATED_DATE))));
                    role.setModifiedDate(
                            Timestamp.valueOf(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_MODIFIED_DATE))));
                    role.setSyncedDate(
                            Timestamp.valueOf(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_SYNCED_DATE))));


                    // construct an organization
                    role.setOrganizationModel(new cOrganizationModel(
                            organizationDBA.getOrganizationByID(role.getOrganizationID())));

                    // populate users for a specific role
                    role.setUserModelSet(getUsersByRoleID(role.getRoleID()));

                    // populate menu items for a specific role
                    Gson gson = new Gson();
                    //Log.d(TAG, role.getRoleID()+"-"+gson.toJson(getMenusByRoleID(role.getRoleID(), role.getOrganizationID())));
                    role.setMenuModelSet(getMenusByRoleID(role.getRoleID(), role.getOrganizationID()));

                    // populate permissions for a specific role
                    //role.setPrivilegeModelSet(getPrivilegesByRoleID(role.getRoleID()));

                    roleModelSet.add(role);

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "ERROR READING ROLE SET:- "+e.getMessage());
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        // close the database connection
        db.close();

        return roleModelSet;
    }

    /**
     * Read and filter roles
     * @param userID
     * @param orgID
     * @param primaryRole
     * @param secondaryRoles
     * @param operationBITS
     * @param statusBITS
     * @return List
     */
    public List<cRoleModel> getRoleList(
            int userID, int orgID, int primaryRole,
            int secondaryRoles, int operationBITS, int statusBITS) {

        List<cRoleModel> roleModelList = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String selectQuery = "SELECT * FROM "+ cSQLDBHelper.TABLE_tblROLE +
                " WHERE ((("+cSQLDBHelper.KEY_GROUP_BITS +" & ?) != 0) " +
                " OR (("+cSQLDBHelper.KEY_OWNER_ID+" = ?) " +
                " AND (("+cSQLDBHelper.KEY_PERMS_BITS+" & ?) != 0)) " +
                " OR ((("+cSQLDBHelper.KEY_GROUP_BITS +" & ?) != 0) " +
                " AND (("+cSQLDBHelper.KEY_PERMS_BITS+" & ?) != 0)))";

        Cursor cursor = db.rawQuery(selectQuery, new String[]{
                String.valueOf(primaryRole),
                String.valueOf(userID),String.valueOf(operationBITS),
                String.valueOf(secondaryRoles),String.valueOf(operationBITS)});

        try {
            if (cursor.moveToFirst()) {
                do {
                    cRoleModel role = new cRoleModel();

                    role.setRoleID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ID)));
                    role.setOrganizationID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ORGANIZATION_FK_ID)));
                    role.setServerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_SERVER_ID)));
                    role.setOwnerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OWNER_ID)));
                    role.setOrgID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ORG_ID)));
                    role.setGroupBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_GROUP_BITS)));
                    role.setPermsBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PERMS_BITS)));
                    role.setStatusBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_STATUS_BITS)));
                    role.setName(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_NAME)));
                    role.setDescription(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_DESCRIPTION)));
                    role.setCreatedDate(
                            Timestamp.valueOf(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_CREATED_DATE))));
                    role.setModifiedDate(
                            Timestamp.valueOf(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_MODIFIED_DATE))));
                    role.setSyncedDate(
                            Timestamp.valueOf(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_SYNCED_DATE))));


                    // construct an organization
                    role.setOrganizationModel(new cOrganizationModel(
                            organizationDBA.getOrganizationByID(role.getOrganizationID())));

                    // populate users for a specific role
                    role.setUserModelSet(getUsersByRoleID(role.getRoleID()));

                    // populate menu items for a specific role
                    role.setMenuModelSet(getMenusByRoleID(role.getRoleID(), role.getOrganizationID()));

                    // populate permissions for a specific role
                    //role.setPrivilegeModelSet(getPrivilegesByRoleID(role.getRoleID()));

                    roleModelList.add(role);

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to get projects from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        // close the database connection
        db.close();

        return roleModelList;
    }

    /**
     * Read users by role ID
     * @param roleID
     * @return Set
     */
    public Set<cUserModel> getUsersByRoleID(int roleID) {

        Set<cUserModel> userModelSet = new HashSet<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT user."+cSQLDBHelper.KEY_ID +", "+
                "user."+cSQLDBHelper.KEY_ORGANIZATION_FK_ID +", user."+cSQLDBHelper.KEY_SERVER_ID +", "+
                "user." +cSQLDBHelper.KEY_OWNER_ID + ", user." +cSQLDBHelper.KEY_ORG_ID +", "+
                "user."+cSQLDBHelper.KEY_GROUP_BITS + ", user." +cSQLDBHelper.KEY_PERMS_BITS +", "+
                "user."+cSQLDBHelper.KEY_STATUS_BITS + ", user." +cSQLDBHelper.KEY_NAME +", "+
                "user."+cSQLDBHelper.KEY_SURNAME + ", user." +cSQLDBHelper.KEY_DESCRIPTION +", "+
                "user."+cSQLDBHelper.KEY_GENDER + ", user." +cSQLDBHelper.KEY_EMAIL +", "+
                "user."+cSQLDBHelper.KEY_WEBSITE + ", user." +cSQLDBHelper.KEY_PHONE +", "+
                "user."+cSQLDBHelper.KEY_PASSWORD + ", user." +cSQLDBHelper.KEY_SALT +", "+
                "user."+cSQLDBHelper.KEY_CREATED_DATE +", "+
                "user."+cSQLDBHelper.KEY_MODIFIED_DATE +", "+
                "user."+cSQLDBHelper.KEY_SYNCED_DATE +
                " FROM "+
                cSQLDBHelper.TABLE_tblUSER + " user, " +
                cSQLDBHelper.TABLE_tblROLE + " role, " +
                cSQLDBHelper.TABLE_tblUSER_ROLE +" user_role " +
                " WHERE user."+cSQLDBHelper.KEY_ID+" = user_role."+cSQLDBHelper.KEY_USER_FK_ID+
                " AND role."+cSQLDBHelper.KEY_ID+" = user_role."+cSQLDBHelper.KEY_ROLE_FK_ID+
                " AND "+ roleID +" = role."+cSQLDBHelper.KEY_ID, null);

        try {
            if (cursor.moveToFirst()) {
                do {
                    cUserModel user = new cUserModel();

                    user.setUserID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ID)));
                    user.setOrganizationID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ORGANIZATION_FK_ID)));
                    user.setServerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_SERVER_ID)));
                    user.setOwnerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OWNER_ID)));
                    user.setOwnerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ORG_ID)));
                    user.setGroupBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_GROUP_BITS)));
                    user.setPermsBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PERMS_BITS)));
                    user.setStatusBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_STATUS_BITS)));
                    //user.setPhoto(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_PHOTO)));
                    user.setName(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_NAME)));
                    user.setSurname(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_SURNAME)));
                    user.setDescription(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_DESCRIPTION)));
                    user.setGender(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_GENDER)));
                    user.setEmail(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_EMAIL)));
                    user.setWebsite(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_WEBSITE)));
                    user.setPhone(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_PHONE)));
                    user.setPassword(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_PASSWORD)));
                    user.setSalt(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_SALT)));
                    user.setCreatedDate(
                            Timestamp.valueOf(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_CREATED_DATE))));
                    user.setModifiedDate(
                            Timestamp.valueOf(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_MODIFIED_DATE))));
                    user.setSyncedDate(
                            Timestamp.valueOf(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_SYNCED_DATE))));

                    userModelSet.add(user);

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to get user groups from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        // close the database connection
        db.close();

        return userModelSet;
    }

    /**
     * \Read menus by role ID
     * @param roleID
     * @return Set
     */
    public Set<cMenuModel> getMenusByRoleID(int roleID, int organizationID) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Set<cMenuModel> menuModelSet = new HashSet();

        // construct a selection query
        String selectQuery = "SELECT " + "menu."+cSQLDBHelper.KEY_ID +", "+
                "menu."+cSQLDBHelper.KEY_PARENT_FK_ID + ", menu."+cSQLDBHelper.KEY_SERVER_ID +", "+
                "menu." +cSQLDBHelper.KEY_OWNER_ID + ", menu." +cSQLDBHelper.KEY_ORG_ID +", "+
                "menu."+cSQLDBHelper.KEY_GROUP_BITS + ", menu." +cSQLDBHelper.KEY_PERMS_BITS +", "+
                "menu."+cSQLDBHelper.KEY_STATUS_BITS + ", menu." +cSQLDBHelper.KEY_NAME+", "+
                "menu."+cSQLDBHelper.KEY_DESCRIPTION +", "+
                "menu."+cSQLDBHelper.KEY_CREATED_DATE +", "+
                "menu."+cSQLDBHelper.KEY_MODIFIED_DATE +", "+
                "menu."+cSQLDBHelper.KEY_SYNCED_DATE +
                " FROM " +
                cSQLDBHelper.TABLE_tblROLE + " role, " +
                cSQLDBHelper.TABLE_tblMENU + " menu, " +
                cSQLDBHelper.TABLE_tblMENU_ROLE + " menu_role " +
                " WHERE role."+cSQLDBHelper.KEY_ID + " = menu_role."+cSQLDBHelper.KEY_ROLE_FK_ID +
                " AND role."+cSQLDBHelper.KEY_ORGANIZATION_FK_ID + " = menu_role."+cSQLDBHelper.KEY_ORGANIZATION_FK_ID +
                " AND menu."+cSQLDBHelper.KEY_ID + " = menu_role."+cSQLDBHelper.KEY_MENU_FK_ID +
                " AND role."+cSQLDBHelper.KEY_ID +" = ? AND role."+cSQLDBHelper.KEY_ORGANIZATION_FK_ID +" = ?";

        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(roleID), String.valueOf(organizationID)});

        try {
            if (cursor.moveToFirst()) {
                do {

                    cMenuModel menu = new cMenuModel();

                    menu.setMenuID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ID)));
                    menu.setParentID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PARENT_FK_ID)));
                    menu.setServerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_SERVER_ID)));
                    menu.setOwnerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OWNER_ID)));
                    menu.setOrgID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ORG_ID)));
                    menu.setGroupBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_GROUP_BITS)));
                    menu.setPermsBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PERMS_BITS)));
                    menu.setStatusBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_STATUS_BITS)));
                    menu.setName(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_NAME)));
                    menu.setDescription(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_DESCRIPTION)));
                    menu.setCreatedDate(
                            Timestamp.valueOf(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_CREATED_DATE))));
                    menu.setModifiedDate(
                            Timestamp.valueOf(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_MODIFIED_DATE))));
                    menu.setSyncedDate(
                            Timestamp.valueOf(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_SYNCED_DATE))));

                    // populate submenu for a specific menu item
                    menu.setMenuModelSet(menuDBA.getSubsetMenuByID(menu.getMenuID()));

                    menuModelSet.add(menu);

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while reading: "+e.getMessage());
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        // close the database connection
        db.close();

        return menuModelSet;
    }

    public Set<cMenuDomain> getMenus1ByRoleID(int roleID) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Set<cMenuDomain> menuModelSet = new HashSet();

        // construct a selection query
        String selectQuery = "SELECT " + "role."+cSQLDBHelper.KEY_ID +", "+
                "role."+cSQLDBHelper.KEY_PARENT_FK_ID + ", role."+cSQLDBHelper.KEY_SERVER_ID +", "+
                "role." +cSQLDBHelper.KEY_OWNER_ID + ", role." +cSQLDBHelper.KEY_ORG_ID +", "+
                "role."+cSQLDBHelper.KEY_GROUP_BITS + ", role." +cSQLDBHelper.KEY_PERMS_BITS +", "+
                "role."+cSQLDBHelper.KEY_STATUS_BITS + ", role." +cSQLDBHelper.KEY_NAME+", "+
                "role."+cSQLDBHelper.KEY_DESCRIPTION +", menu."+cSQLDBHelper.KEY_ID +", "+
                "role."+cSQLDBHelper.KEY_CREATED_DATE +", "+
                "role."+cSQLDBHelper.KEY_MODIFIED_DATE +", "+
                "role."+cSQLDBHelper.KEY_SYNCED_DATE +
                " FROM " +
                cSQLDBHelper.TABLE_tblROLE+ " role, " +
                cSQLDBHelper.TABLE_tblMENU+ " menu, " +
                cSQLDBHelper.TABLE_tblMENU_ROLE + " menu_role " +
                " WHERE role."+cSQLDBHelper.KEY_ID + " = menu_role."+cSQLDBHelper.KEY_ROLE_FK_ID +
                " AND menu."+cSQLDBHelper.KEY_ID + " = menu_role."+cSQLDBHelper.KEY_MENU_FK_ID +
                " AND role."+cSQLDBHelper.KEY_ID +" = ?";

        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(roleID)});

        try {
            if (cursor.moveToFirst()) {
                do {

                    cMenuDomain menu = new cMenuDomain();

                    menu.setMenuID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ID)));
                    menu.setParentID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PARENT_FK_ID)));
                    menu.setServerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_SERVER_ID)));
                    menu.setOwnerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OWNER_ID)));
                    menu.setOrgID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ORG_ID)));
                    menu.setGroupBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_GROUP_BITS)));
                    menu.setPermsBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PERMS_BITS)));
                    menu.setStatusBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_STATUS_BITS)));
                    menu.setName(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_NAME)));
                    menu.setDescription(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_DESCRIPTION)));
                    menu.setCreatedDate(
                            Timestamp.valueOf(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_CREATED_DATE))));
                    menu.setModifiedDate(
                            Timestamp.valueOf(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_MODIFIED_DATE))));
                    menu.setSyncedDate(
                            Timestamp.valueOf(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_SYNCED_DATE))));

                    // populate submenu for a specific menu item
                    //menu.setMenuModelSet(menuDBA.getSubsetMenuByID(menu.getMenuID()));

                    menuModelSet.add(menu);

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while reading: "+e.getMessage());
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        // close the database connection
        db.close();

        return menuModelSet;
    }

    /**
     * Read privileges by role ID
     * @param roleID
     * @return Set
     */
    public Set<cPrivilegeModel> getPrivilegesByRoleID(int roleID) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Set<cPrivilegeModel> privilegeModelSet = new HashSet();

        // construct a selection query
        String selectQuery = "SELECT " + "perm."+cSQLDBHelper.KEY_ID +", "+
                "perm."+cSQLDBHelper.KEY_SERVER_ID + ", perm." +cSQLDBHelper.KEY_OWNER_ID +", "+
                "perm." +cSQLDBHelper.KEY_ORG_ID + ", perm."+cSQLDBHelper.KEY_GROUP_BITS +", "+
                "perm." +cSQLDBHelper.KEY_PERMS_BITS + ", perm."+cSQLDBHelper.KEY_STATUS_BITS +", "+
                "perm." +cSQLDBHelper.KEY_NAME + ", perm."+cSQLDBHelper.KEY_DESCRIPTION +", "+
                "perm."+cSQLDBHelper.KEY_CREATED_DATE +", "+
                "perm."+cSQLDBHelper.KEY_MODIFIED_DATE +", "+
                "perm."+cSQLDBHelper.KEY_SYNCED_DATE +
                " FROM " +
                cSQLDBHelper.TABLE_tblROLE+ " role, " +
                cSQLDBHelper.TABLE_tblMENU+ " perm, " +
                cSQLDBHelper.TABLE_tblMENU_ROLE + " role_perm " +
                " WHERE role."+cSQLDBHelper.KEY_ID + " = role_perm."+cSQLDBHelper.KEY_ROLE_FK_ID +
                " AND menu."+cSQLDBHelper.KEY_ID + " = role_perm."+cSQLDBHelper.KEY_MENU_FK_ID +
                " AND role."+cSQLDBHelper.KEY_ID +" = ?";

        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(roleID)});

        try {
            if (cursor.moveToFirst()) {
                do {

                    cPrivilegeModel privilege = new cPrivilegeModel();

                    privilege.setPrivilegeID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ID)));
                    privilege.setServerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_SERVER_ID)));
                    privilege.setOwnerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OWNER_ID)));
                    privilege.setOrgID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ORG_ID)));
                    privilege.setGroupBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_GROUP_BITS)));
                    privilege.setPermsBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PERMS_BITS)));
                    privilege.setStatusBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_STATUS_BITS)));
                    privilege.setName(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_NAME)));
                    privilege.setDescription(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_DESCRIPTION)));
                    privilege.setCreatedDate(
                            Timestamp.valueOf(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_CREATED_DATE))));
                    privilege.setModifiedDate(
                            Timestamp.valueOf(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_MODIFIED_DATE))));
                    privilege.setSyncedDate(
                            Timestamp.valueOf(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_SYNCED_DATE))));

                    privilegeModelSet.add(privilege);

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while reading: "+e.getMessage());
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        // close the database connection
        db.close();

        return privilegeModelSet;
    }

    /**
     * Read role by ID
     * @param roleID
     * @return
     */
    public cRoleModel getRoleByID(int roleID) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        // construct a selection query
        String selectQuery = "SELECT * FROM " +
                cSQLDBHelper.TABLE_tblROLE + " WHERE " +
                cSQLDBHelper.KEY_ID + "= ?";

        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(roleID)});

        cRoleModel role = new cRoleModel();

        try {
            if (cursor.moveToFirst()) {
                do {
                    role.setRoleID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ID)));
                    role.setOrganizationID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ORGANIZATION_FK_ID)));
                    role.setServerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_SERVER_ID)));
                    role.setOwnerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OWNER_ID)));
                    role.setOrgID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ORG_ID)));
                    role.setGroupBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_GROUP_BITS)));
                    role.setPermsBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PERMS_BITS)));
                    role.setStatusBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_STATUS_BITS)));
                    role.setName(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_NAME)));
                    role.setDescription(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_DESCRIPTION)));
                    role.setCreatedDate(
                            sdf.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_CREATED_DATE))));
                    role.setModifiedDate(
                            sdf.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_MODIFIED_DATE))));
                    role.setSyncedDate(
                            sdf.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_SYNCED_DATE))));

                    role.setOrganizationModel(new cOrganizationModel(organizationDBA.getOrganizationByID(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ORGANIZATION_FK_ID)))));

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to get projects from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        // close the database connection
        db.close();

        return role;
    }

    /* ############################################# UPDATE ACTIONS ############################################# */

    public boolean updateRole(cRoleModel roleModel, int organizationID){
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        Date date= new Date();
        Timestamp ts = new Timestamp(date.getTime());

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_ID, roleModel.getRoleID());
        cv.put(cSQLDBHelper.KEY_ORGANIZATION_FK_ID, organizationID);
        cv.put(cSQLDBHelper.KEY_OWNER_ID, roleModel.getOwnerID());
        cv.put(cSQLDBHelper.KEY_GROUP_BITS, roleModel.getGroupBITS());
        cv.put(cSQLDBHelper.KEY_PERMS_BITS, roleModel.getPermsBITS());
        cv.put(cSQLDBHelper.KEY_STATUS_BITS, roleModel.getStatusBITS());
        cv.put(cSQLDBHelper.KEY_NAME, roleModel.getName());
        cv.put(cSQLDBHelper.KEY_DESCRIPTION, roleModel.getDescription());
        cv.put(cSQLDBHelper.KEY_MODIFIED_DATE, sdf.format(ts));

        // update a specific record
        long result = db.update(cSQLDBHelper.TABLE_tblROLE, cv,
                cSQLDBHelper.KEY_ID + "= ? AND "+
                        cSQLDBHelper.KEY_ORGANIZATION_FK_ID +" = ?",
                new String[]{String.valueOf(roleModel.getRoleID()),
                        String.valueOf(organizationID)});

        // close the database connection
        db.close();

        return result > -1;
    }

    /* ############################################# DELETE ACTIONS ############################################# */

    /**
     * Delete all roles
     * @return
     */
    public boolean deleteRoles() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblROLE, null, null);

        // close the database connection
        db.close();

        return result > -1;
    }

    /* ############################################# SYNC ACTIONS ############################################# */
}
