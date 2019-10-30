package com.me.mseotsanyana.mande.BRBAC.DAL;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.me.mseotsanyana.mande.PPMER.DAL.cSQLDBHelper;
import com.me.mseotsanyana.mande.Util.cConstant;

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
public class cPrivilegeDBA {
    private static SimpleDateFormat sdf = cConstant.FORMAT_DATE;
    private static String TAG = cPrivilegeDBA.class.getSimpleName();

    // an object of the database helper
    private cSQLDBHelper dbHelper;

    public cPrivilegeDBA(Context context) {
        dbHelper = new cSQLDBHelper(context);
    }

    /* ############################################# CREATE ACTIONS ############################################# */

    /**
     * Add privilege from an excel file
     *
     * @param privilegeModel
     * @return Boolean
     */
    public boolean addPrivilegeFromExcel(cPrivilegeModel privilegeModel) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_ID, privilegeModel.getPrivilegeID());
        cv.put(cSQLDBHelper.KEY_ROLE_FK_ID, privilegeModel.getRoleID());
        cv.put(cSQLDBHelper.KEY_ORGANIZATION_FK_ID, privilegeModel.getOrganizationID());
        cv.put(cSQLDBHelper.KEY_NAME, privilegeModel.getName());
        cv.put(cSQLDBHelper.KEY_DESCRIPTION, privilegeModel.getDescription());

        // insert record
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblPRIVILEGE, null, cv) < 0) {
                return false;
            }
        } catch (Exception e) {
            Log.d(TAG, "Exception in adding: " + e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }

    public boolean addPrivilege(cPrivilegeModel groupModel, int roleID, int organizationID) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_ID, groupModel.getPrivilegeID());
        cv.put(cSQLDBHelper.KEY_ROLE_FK_ID, roleID);
        cv.put(cSQLDBHelper.KEY_ORGANIZATION_FK_ID, organizationID);
        cv.put(cSQLDBHelper.KEY_OWNER_ID, groupModel.getOwnerID());
        cv.put(cSQLDBHelper.KEY_GROUP_BITS, groupModel.getGroupBITS());
        cv.put(cSQLDBHelper.KEY_PERMS_BITS, groupModel.getPermsBITS());
        cv.put(cSQLDBHelper.KEY_STATUS_BITS, groupModel.getStatusBITS());
        cv.put(cSQLDBHelper.KEY_NAME, groupModel.getName());
        cv.put(cSQLDBHelper.KEY_DESCRIPTION, groupModel.getDescription());

        // insert outcome record
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblPRIVILEGE, null, cv) < 0) {
                return false;
            }
        } catch (Exception ex) {
            Log.d("Exception in importing ", ex.getMessage().toString());
        }

        // close the database connection
        db.close();

        return true;
    }

    /* ############################################# READ ACTIONS ############################################# */

    /**
     * Read and filter privileges
     *
     * @param userID
     * @param orgID
     * @param primaryPrivilege
     * @param secondaryPrivileges
     * @param operationBITS
     * @param statusBITS
     * @return List
     */
    public List<cPrivilegeModel> getPrivilegeList(
            int userID, int orgID, int primaryPrivilege,
            int secondaryPrivileges, int operationBITS, int statusBITS) {

        List<cPrivilegeModel> privilegeModelList = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + cSQLDBHelper.TABLE_tblPRIVILEGE +
                " WHERE (((" + cSQLDBHelper.KEY_GROUP_BITS + " & ?) != 0) " +
                " OR ((" + cSQLDBHelper.KEY_OWNER_ID + " = ?) " +
                " AND ((" + cSQLDBHelper.KEY_PERMS_BITS + " & ?) != 0)) " +
                " OR (((" + cSQLDBHelper.KEY_GROUP_BITS + " & ?) != 0) " +
                " AND ((" + cSQLDBHelper.KEY_PERMS_BITS + " & ?) != 0)))";

        Cursor cursor = db.rawQuery(selectQuery, new String[]{
                String.valueOf(primaryPrivilege),
                String.valueOf(userID), String.valueOf(operationBITS),
                String.valueOf(secondaryPrivileges), String.valueOf(operationBITS)});
        try {
            if (cursor.moveToFirst()) {
                do {
                    cPrivilegeModel privilege = new cPrivilegeModel();

                    privilege.setPrivilegeID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ID)));
                    privilege.setRoleID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ROLE_FK_ID)));
                    privilege.setOrganizationID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ORGANIZATION_FK_ID)));
                    privilege.setServerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_SERVER_ID)));
                    privilege.setOwnerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OWNER_ID)));
                    privilege.setOrgID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ORG_ID)));
                    privilege.setGroupBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_GROUP_BITS)));
                    privilege.setPermsBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PERMS_BITS)));
                    privilege.setStatusBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_STATUS_BITS)));
                    privilege.setName(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_NAME)));
                    privilege.setDescription(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_DESCRIPTION)));
                    privilege.setCreatedDate(
                            sdf.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_CREATED_DATE))));
                    privilege.setModifiedDate(
                            sdf.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_MODIFIED_DATE))));
                    privilege.setSyncedDate(
                            sdf.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_SYNCED_DATE))));

                    // construct a permission
                    privilege.setPermissionModelSet(getPermissionsByPrivilegeID(privilege.getPrivilegeID()));

                    privilegeModelList.add(privilege);

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

        return privilegeModelList;
    }

    /**
     * Read privileges by privilege ID
     *
     * @param privilegeID
     * @return Set
     */
    public Set<cPermissionModel> getPermissionsByPrivilegeID(int privilegeID) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Set<cPermissionModel> permissionModelSet = new HashSet();

        // construct a selection query
        String selectQuery = "SELECT " +
                "perm." + cSQLDBHelper.KEY_PRIVILEGE_FK_ID + ", perm." + cSQLDBHelper.KEY_ENTITY_FK_ID + ", " +
                "perm." + cSQLDBHelper.KEY_ENTITY_TYPE_FK_ID + ", perm." + cSQLDBHelper.KEY_OPERATION_FK_ID + ", " +
                "perm." + cSQLDBHelper.KEY_SERVER_ID + ", perm." + cSQLDBHelper.KEY_OWNER_ID + ", " +
                "perm." + cSQLDBHelper.KEY_ORG_ID + ", perm." + cSQLDBHelper.KEY_GROUP_BITS + ", " +
                "perm." + cSQLDBHelper.KEY_PERMS_BITS + ", perm." + cSQLDBHelper.KEY_STATUS_BITS + ", " +
                "perm." + cSQLDBHelper.KEY_CREATED_DATE + ", " +
                "perm." + cSQLDBHelper.KEY_MODIFIED_DATE + ", " +
                "perm." + cSQLDBHelper.KEY_SYNCED_DATE +
                " FROM " + cSQLDBHelper.TABLE_tblPERMISSION + " perm " +
                " WHERE perm." + cSQLDBHelper.KEY_PRIVILEGE_FK_ID + " = ?";

        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(privilegeID)});

        try {
            if (cursor.moveToFirst()) {
                do {

                    cPermissionModel permission = new cPermissionModel();

                    permission.setPrivilegeID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PRIVILEGE_FK_ID)));
                    permission.setEntityID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ENTITY_FK_ID)));
                    permission.setEntityTypeID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ENTITY_TYPE_FK_ID)));
                    permission.setOperationID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OPERATION_FK_ID)));
                    permission.setServerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_SERVER_ID)));
                    permission.setOwnerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OWNER_ID)));
                    permission.setOrgID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ORG_ID)));
                    permission.setGroupBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_GROUP_BITS)));
                    permission.setPermsBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PERMS_BITS)));
                    permission.setStatusBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_STATUS_BITS)));
                    permission.setCreatedDate(
                            Timestamp.valueOf(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_CREATED_DATE))));
                    permission.setModifiedDate(
                            Timestamp.valueOf(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_MODIFIED_DATE))));
                    permission.setSyncedDate(
                            Timestamp.valueOf(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_SYNCED_DATE))));

                    permissionModelSet.add(permission);

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while reading: " + e.getMessage());
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        // close the database connection
        db.close();

        return permissionModelSet;
    }

    /**
     * Read privilege by ID
     *
     * @param privilegeID
     * @return
     */
    public cPrivilegeModel getPrivilegeByID(int privilegeID) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        // construct a selection query
        String selectQuery = "SELECT * FROM " +
                cSQLDBHelper.TABLE_tblPRIVILEGE + " WHERE " +
                cSQLDBHelper.KEY_ID + "= ?";

        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(privilegeID)});

        cPrivilegeModel privilege = new cPrivilegeModel();

        try {
            if (cursor.moveToFirst()) {
                privilege.setPrivilegeID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ID)));
                privilege.setRoleID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ROLE_FK_ID)));
                privilege.setOrganizationID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ORGANIZATION_FK_ID)));
                privilege.setServerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_SERVER_ID)));
                privilege.setOwnerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OWNER_ID)));
                privilege.setOrgID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ORG_ID)));
                privilege.setGroupBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_GROUP_BITS)));
                privilege.setPermsBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PERMS_BITS)));
                privilege.setStatusBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_STATUS_BITS)));
                privilege.setName(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_NAME)));
                privilege.setDescription(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_DESCRIPTION)));
                privilege.setCreatedDate(
                        sdf.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_CREATED_DATE))));
                privilege.setModifiedDate(
                        sdf.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_MODIFIED_DATE))));
                privilege.setSyncedDate(
                        sdf.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_SYNCED_DATE))));

                privilege.setPermissionModelSet(getPermissionsByPrivilegeID(privilege.getPrivilegeID()));
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

        return privilege;
    }

    /* ############################################# UPDATE ACTIONS ############################################# */

    public boolean updatePrivilege(cPrivilegeModel privilegeModel, int organizationID) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        Date date = new Date();
        Timestamp ts = new Timestamp(date.getTime());

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_ID, privilegeModel.getPrivilegeID());
        cv.put(cSQLDBHelper.KEY_ROLE_FK_ID, privilegeModel.getRoleID());
        cv.put(cSQLDBHelper.KEY_ORGANIZATION_FK_ID, organizationID);
        cv.put(cSQLDBHelper.KEY_OWNER_ID, privilegeModel.getOwnerID());
        cv.put(cSQLDBHelper.KEY_GROUP_BITS, privilegeModel.getGroupBITS());
        cv.put(cSQLDBHelper.KEY_PERMS_BITS, privilegeModel.getPermsBITS());
        cv.put(cSQLDBHelper.KEY_STATUS_BITS, privilegeModel.getStatusBITS());
        cv.put(cSQLDBHelper.KEY_NAME, privilegeModel.getName());
        cv.put(cSQLDBHelper.KEY_DESCRIPTION, privilegeModel.getDescription());
        cv.put(cSQLDBHelper.KEY_MODIFIED_DATE, sdf.format(ts));

        // update a specific record
        long result = db.update(cSQLDBHelper.TABLE_tblPRIVILEGE, cv,
                cSQLDBHelper.KEY_ID + "= ? AND " +
                        cSQLDBHelper.KEY_ORGANIZATION_FK_ID + " = ?",
                new String[]{String.valueOf(privilegeModel.getPrivilegeID()),
                        String.valueOf(organizationID)});

        // close the database connection
        db.close();

        return result > -1;
    }

    /* ############################################# DELETE ACTIONS ############################################# */

    /**
     * Delete all privileges
     *
     * @return
     */
    public boolean deletePrivileges() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblPRIVILEGE, null, null);

        // close the database connection
        db.close();

        return result > -1;
    }

    /* ############################################# SYNC ACTIONS ############################################# */
}
