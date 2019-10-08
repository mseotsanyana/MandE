package com.me.mseotsanyana.mande.BRBAC.DAL;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.me.mseotsanyana.mande.PPMER.DAL.cSQLDBHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by mseotsanyana on 2017/06/27.
 */

public class cUserRoleDBA {
    // an object of the database helper
    private cSQLDBHelper dbHelper;

    private cUserDBA userDBA;
    private cRoleDBA roleDBA;
    private cOrganizationDBA organizationDBA;

    private static final SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
    private static final String TAG = "dbHelper";

    public cUserRoleDBA(Context context) {
        dbHelper = new cSQLDBHelper(context);
        userDBA  = new cUserDBA(context);
        roleDBA  = new cRoleDBA(context);
        organizationDBA = new cOrganizationDBA(context);
    }

    public boolean addUserRoleFromExcel(cUserRoleModel userRoleModel){
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long result = -1;

        // get an user by id
        cUserModel userModel = userDBA.getUserByID(userRoleModel.getUserID());
        // get a group by id
        cRoleModel roleModel = roleDBA.getRoleByID(userRoleModel.getRoleID());
        // get a group by id
        cOrganizationModel organizationModel = organizationDBA.getOrganizationByID(userRoleModel.getOrganizationID());

        if ((userModel != null) && (roleModel != null) && (organizationModel != null)){

            ContentValues cv = new ContentValues();

            cv.put(cSQLDBHelper.KEY_USER_FK_ID, userModel.getUserID());
            cv.put(cSQLDBHelper.KEY_ROLE_FK_ID, roleModel.getRoleID());
            cv.put(cSQLDBHelper.KEY_ORGANIZATION_FK_ID, organizationModel.getOrganizationID());

            // insert user group record
            result = db.insert(cSQLDBHelper.TABLE_tblUSER_ROLE, null, cv);
        }

        // close the database connection
        db.close();

        return result > -1;
    }

    public boolean addUserRole(cUserRoleModel userRoleModel){
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long result = -1;

        // get an user by id
        cUserModel userModel = userDBA.getUserByID(userRoleModel.getUserID());
        // get a group by id
        cRoleModel groupModel = roleDBA.getRoleByID(userRoleModel.getRoleID());

        if ((userModel != null) && (groupModel != null)){

            ContentValues cv = new ContentValues();

            cv.put(cSQLDBHelper.KEY_USER_FK_ID, userModel.getUserID());
            cv.put(cSQLDBHelper.KEY_ROLE_FK_ID, groupModel.getRoleID());
            cv.put(cSQLDBHelper.KEY_OWNER_ID, userRoleModel.getOwnerID());
            cv.put(cSQLDBHelper.KEY_GROUP_BITS, userRoleModel.getGroupBITS());
            cv.put(cSQLDBHelper.KEY_PERMS_BITS, userRoleModel.getPermsBITS());
            cv.put(cSQLDBHelper.KEY_STATUS_BITS, userRoleModel.getStatusBITS());
            //cv.put(cSQLDBHelper.KEY_USER_ROLE_DATE, formatter.format(userRoleModel.getCreateDate()));

            // insert user group record
            result = db.insert(cSQLDBHelper.TABLE_tblUSER_ROLE, null, cv);
        }

        // close the database connection
        db.close();

        return result > -1;
    }



    public boolean deleteUserRoles() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblUSER_ROLE, null, null);

        // close the database connection
        db.close();

        return result > -1;
    }

    public List<cUserRoleModel> getUserRoleList() {

        List<cUserRoleModel> userRoleModels = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+ cSQLDBHelper.TABLE_tblUSER_ROLE, null);

        try {
            if (cursor.moveToFirst()) {
                do {
                    cUserRoleModel userRole = new cUserRoleModel();

                    userRole.setUserID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_USER_FK_ID)));
                    userRole.setRoleID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ROLE_FK_ID)));
                    userRole.setOwnerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OWNER_ID)));
                    userRole.setGroupBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_GROUP_BITS)));
                    userRole.setPermsBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PERMS_BITS)));
                    userRole.setStatusBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_STATUS_BITS)));
                    userRole.setCreatedDate(
                            formatter.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_CREATED_DATE))));
                    userRole.setModifiedDate(
                            formatter.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_MODIFIED_DATE))));
                    userRole.setSyncedDate(
                            formatter.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_SYNCED_DATE))));

                    userRoleModels.add(userRole);

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

        return userRoleModels;
    }


    public List<cUserModel> getUsersByRoleID(int roleID) {

        List<cUserModel> userModels = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM "+
                cSQLDBHelper.TABLE_tblUSER + " user, " +
                cSQLDBHelper.TABLE_tblROLE + " role, " +
                cSQLDBHelper.TABLE_tblUSER_ROLE +" user_role " +
                "WHERE user."+cSQLDBHelper.KEY_USER_ID+" = user_role."+cSQLDBHelper.KEY_USER_FK_ID+
                " AND role."+cSQLDBHelper.KEY_ID+" = user_role."+cSQLDBHelper.KEY_ROLE_FK_ID+
                " AND "+ roleID +" = role."+cSQLDBHelper.KEY_ID, null);

        try {
            if (cursor.moveToFirst()) {
                do {
                    cUserModel user = new cUserModel();

                    user.setUserID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_USER_ID)));
                    user.setOwnerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OWNER_ID)));
                    user.setGroupBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_GROUP_BITS)));
                    user.setPermsBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PERMS_BITS)));
                    user.setStatusBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_STATUS_BITS)));
                    user.setName(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_NAME)));
                    user.setSurname(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_SURNAME)));
                    user.setDescription(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_DESCRIPTION)));
                    user.setEmail(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_EMAIL)));
                    user.setPassword(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_PASSWORD)));
                    user.setSalt(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_SALT)));
                    //user.setCreateDate(formatter.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_USER_DATE))));

                    userModels.add(user);

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

        return userModels;
    }

    public List<cRoleModel> getRolesByUserID(int userID) {

        List<cRoleModel> roleModels = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT role." + cSQLDBHelper.KEY_ID + ", role." +
                cSQLDBHelper.KEY_ORGANIZATION_FK_ID + ", role." + cSQLDBHelper.KEY_NAME + ", role." +
                cSQLDBHelper.KEY_DESCRIPTION + ", "
                + "role." + cSQLDBHelper.KEY_CREATED_DATE + " FROM " +
                cSQLDBHelper.TABLE_tblUSER + " user, " +
                cSQLDBHelper.TABLE_tblROLE + " role, " +
                cSQLDBHelper.TABLE_tblUSER_ROLE +" user_role " +
                "WHERE user."+cSQLDBHelper.KEY_USER_ID+" = user_role."+cSQLDBHelper.KEY_USER_FK_ID+
                " AND role."+cSQLDBHelper.KEY_ID+" = user_role."+cSQLDBHelper.KEY_ROLE_FK_ID+
                " AND "+ userID +" = user."+cSQLDBHelper.KEY_USER_ID, null);

        try {
            if (cursor.moveToFirst()) {
                do {
                    cRoleModel role = new cRoleModel();

                    role.setRoleID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ID)));
                    role.setOrganizationID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ORGANIZATION_FK_ID)));
                    //role.setOwnerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ROLE_OWNER_ID)));
                    //role.setGroupBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ROLE_GROUP_BITS)));
                    //role.setPermsBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ROLE_PERMS_BITS)));
                    //role.setTypeBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ROLE_TYPE_BITS)));
                    //role.setStatusBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ROLE_STATUS_BITS)));
                    role.setName(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_NAME)));
                    role.setDescription(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_DESCRIPTION)));
                    //role.setCreateDate(formatter.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_ROLE_DATE))));

                    roleModels.add(role);

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

        return roleModels;
    }

    public ArrayList<Integer> getRoleIDsByUserID(int userID) {

        ArrayList<Integer> roleIDs = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT role." + cSQLDBHelper.KEY_ID + " FROM " +
                cSQLDBHelper.TABLE_tblUSER + " user, " +
                cSQLDBHelper.TABLE_tblROLE + " role, " +
                cSQLDBHelper.TABLE_tblUSER_ROLE +" user_role " +
                "WHERE user."+cSQLDBHelper.KEY_USER_ID+" = user_role."+cSQLDBHelper.KEY_USER_FK_ID+
                " AND role."+cSQLDBHelper.KEY_ID+" = user_role."+cSQLDBHelper.KEY_ROLE_FK_ID+
                " AND "+ userID +" = user."+cSQLDBHelper.KEY_USER_ID, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    roleIDs.add(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ID)));
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

        return roleIDs;
    }

    public ArrayList<Integer> getOrganizationIDsByUserID(int userID) {

        ArrayList<Integer> organizationIDs = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT DISTINCT(" + cSQLDBHelper.KEY_ORGANIZATION_FK_ID + ")" +
                " FROM " + cSQLDBHelper.TABLE_tblUSER_ROLE +
                " WHERE "+ userID + " = " + cSQLDBHelper.KEY_USER_FK_ID, null);

        try {
            if (cursor.moveToFirst()) {
                do {
                    organizationIDs.add(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ORGANIZATION_FK_ID)));
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to get organization IDs from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        // close the database connection
        db.close();

        return organizationIDs;
    }


    public ArrayList<Integer> getRoleIDsByOrganizationID(int organizationID) {

        ArrayList<Integer> roleIDs = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT DISTINCT(" + cSQLDBHelper.KEY_ROLE_FK_ID + ")" +
                " FROM " + cSQLDBHelper.TABLE_tblUSER_ROLE +
                " WHERE "+ organizationID + " = " + cSQLDBHelper.KEY_ORGANIZATION_FK_ID, null);

        try {
            if (cursor.moveToFirst()) {
                do {
                    roleIDs.add(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ROLE_FK_ID)));
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to get organization IDs from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        // close the database connection
        db.close();

        return roleIDs;
    }
}
