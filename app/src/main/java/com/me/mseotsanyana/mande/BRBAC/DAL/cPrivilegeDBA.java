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
import java.util.List;
import java.util.Locale;

/**
 * Created by mseotsanyana on 2017/08/24.
 */

public class cPrivilegeDBA {
    private static SimpleDateFormat sdf = cConstant.FORMAT_DATE;
    private static String TAG = cSettingDBA.class.getSimpleName();

    // an object of the database helper
    private cSQLDBHelper dbHelper;

    public cPrivilegeDBA(Context context) {
        dbHelper = new cSQLDBHelper(context);
    }

    /* ############################################# CREATE ACTIONS ############################################# */

    public boolean addPrivilegeFromExcel(cPrivilegeModel privilegeModel) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_ID, privilegeModel.getPrivilegeID());
        cv.put(cSQLDBHelper.KEY_ROLE_FK_ID, privilegeModel.getRoleID());
        cv.put(cSQLDBHelper.KEY_NAME, privilegeModel.getName());
        cv.put(cSQLDBHelper.KEY_DESCRIPTION, privilegeModel.getDescription());

        // insert outcome record
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblPRIVILEGE, null, cv) < 0) {
                return false;
            }
        } catch (Exception e) {
            Log.d(TAG,"Exception in importing: "+e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }

    public boolean addPrivilege(cPrivilegeModel privilegeModel) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_ID, privilegeModel.getPrivilegeID());
        cv.put(cSQLDBHelper.KEY_ROLE_FK_ID, privilegeModel.getRoleID());
        cv.put(cSQLDBHelper.KEY_SERVER_ID, privilegeModel.getServerID());
        cv.put(cSQLDBHelper.KEY_ORG_ID, privilegeModel.getOrgID());
        cv.put(cSQLDBHelper.KEY_OWNER_ID, privilegeModel.getOwnerID());
        cv.put(cSQLDBHelper.KEY_GROUP_BITS, privilegeModel.getGroupBITS());
        cv.put(cSQLDBHelper.KEY_PERMS_BITS, privilegeModel.getPermsBITS());
        cv.put(cSQLDBHelper.KEY_STATUS_BITS, privilegeModel.getStatusBITS());
        cv.put(cSQLDBHelper.KEY_NAME, privilegeModel.getName());
        cv.put(cSQLDBHelper.KEY_DESCRIPTION, privilegeModel.getDescription());

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

    public List<cPrivilegeModel> getPrivilegeList(
            int userID, int orgID, int primaryRole,
            int secondaryRoles, int operationBITS, int statusBITS) {

        List<cPrivilegeModel> privilegeModelList = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String selectQuery = "SELECT * FROM "+ cSQLDBHelper.TABLE_tblPRIVILEGE +
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
                    cPrivilegeModel privilege = new cPrivilegeModel();

                    privilege.setPrivilegeID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ID)));
                    privilege.setRoleID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ROLE_FK_ID)));
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

                    //

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

    public cPermissionModel getPermissionsByPrivilegeID(int privilegeID) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        // construct a selection query
        String selectQuery = "SELECT * FROM " +
                cSQLDBHelper.TABLE_tblPERMISSION + " WHERE " +
                cSQLDBHelper.KEY_PRIVILEGE_FK_ID + "= ?";

        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(privilegeID)});

        cPermissionModel permission = new cPermissionModel();

        try {
            if (cursor.moveToFirst()) {
                do {
                    permission.setPrivilegeID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PRIVILEGE_FK_ID)));
                    permission.setEntityID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ENTITY_FK_ID)));
                    permission.setEntityTypeID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ENTITY_TYPE_FK_ID)));
                    permission.setOperationID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OPERATION_FK_ID)));
                    permission.setStatusID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_STATUS_FK_ID)));
                    permission.setOwnerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OWNER_ID)));
                    permission.setGroupBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_GROUP_BITS)));
                    permission.setPermsBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PERMS_BITS)));
                    permission.setStatusBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_STATUS_BITS)));

                    permission.setCreatedDate(
                            sdf.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_CREATED_DATE))));
                    permission.setModifiedDate(
                            sdf.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_MODIFIED_DATE))));
                    permission.setSyncedDate(
                            sdf.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_SYNCED_DATE))));


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

        return permission;
    }


    public cPrivilegeModel getPrivilegeByID(int organizationID, int privilegeID) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        // construct a selection query
        String selectQuery = "SELECT * FROM " +
                cSQLDBHelper.TABLE_tblPRIVILEGE + " WHERE " +
                cSQLDBHelper.KEY_ORGANIZATION_FK_ID + "= ? AND " +
                cSQLDBHelper.KEY_ROLE_FK_ID + "= ?";

        Cursor cursor = db.rawQuery(selectQuery,
                new String[]{String.valueOf(organizationID), String.valueOf(privilegeID)});

        cPrivilegeModel privilege = new cPrivilegeModel();

        try {
            if (cursor.moveToFirst()) {
                do {
                    privilege.setOrganizationID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ORGANIZATION_FK_ID)));
                    privilege.setPrivilegeID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ROLE_FK_ID)));
                    privilege.setOwnerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OWNER_ID)));
                    privilege.setOrgID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ORG_ID)));
                    privilege.setGroupBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_GROUP_BITS)));
                    privilege.setPermsBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PERMS_BITS)));
                    privilege.setStatusBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_STATUS_BITS)));
                    privilege.setName(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_NAME)));
                    privilege.setDescription(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_DESCRIPTION)));
                    privilege.setCreatedDate(sdf.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_CREATED_DATE))));
                    privilege.setModifiedDate(sdf.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_MODIFIED_DATE))));
                    privilege.setSyncedDate(sdf.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_SYNCED_DATE))));
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to get privileges from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        // close the database connection
        db.close();

        return privilege;
    }

    public List<cPrivilegeModel> getPrivilegesByIDs(int organizationID, int roleID) {

        List<cPrivilegeModel> privilegeModels = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String selectQuery = "SELECT * FROM " +
                cSQLDBHelper.TABLE_tblPRIVILEGE + " WHERE " +
                cSQLDBHelper.KEY_ORGANIZATION_FK_ID + "= ? AND " +
                cSQLDBHelper.KEY_ROLE_FK_ID + "= ?";

        Cursor cursor = db.rawQuery(selectQuery,
                new String[]{String.valueOf(organizationID), String.valueOf(roleID)});

        try {
            if (cursor.moveToFirst()) {
                do {
                    cPrivilegeModel privilege = new cPrivilegeModel();

                    privilege.setOrganizationID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ORGANIZATION_FK_ID)));
                    privilege.setPrivilegeID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ROLE_FK_ID)));
                    privilege.setName(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_NAME)));
                    privilege.setDescription(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_DESCRIPTION)));
                    privilege.setOwnerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OWNER_ID)));
                    privilege.setGroupBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_GROUP_BITS)));
                    privilege.setPermsBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PERMS_BITS)));
                    privilege.setStatusBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_STATUS_BITS)));
                    privilege.setCreatedDate(sdf.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_CREATED_DATE))));
                    privilege.setModifiedDate(sdf.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_MODIFIED_DATE))));
                    privilege.setSyncedDate(sdf.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_SYNCED_DATE))));

                    privilegeModels.add(privilege);

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

        return privilegeModels;
    }

    /* ############################################# UPDATE ACTIONS ############################################# */

    public boolean updatePrivilege(cPrivilegeModel model){
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        Date date= new Date();
        Timestamp timestamp = new Timestamp(date.getTime());

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_ORGANIZATION_FK_ID, model.getOrganizationID());
        cv.put(cSQLDBHelper.KEY_ROLE_FK_ID, model.getPrivilegeID());
        cv.put(cSQLDBHelper.KEY_OWNER_ID, model.getOwnerID());
        cv.put(cSQLDBHelper.KEY_ORG_ID, model.getOrgID());
        cv.put(cSQLDBHelper.KEY_GROUP_BITS, model.getGroupBITS());
        cv.put(cSQLDBHelper.KEY_PERMS_BITS, model.getPermsBITS());
        cv.put(cSQLDBHelper.KEY_STATUS_BITS, model.getStatusBITS());
        cv.put(cSQLDBHelper.KEY_NAME, model.getName());
        cv.put(cSQLDBHelper.KEY_DESCRIPTION, model.getDescription());
        cv.put(cSQLDBHelper.KEY_MODIFIED_DATE, sdf.format(timestamp));

        // update a specific record
        long result = db.update(cSQLDBHelper.TABLE_tblPRIVILEGE, cv,
                cSQLDBHelper.KEY_ORGANIZATION_FK_ID +" = ? AND "+
                        cSQLDBHelper.KEY_ROLE_FK_ID + "= ?"
                ,
                new String[]{String.valueOf(model.getOrganizationID()),
                        String.valueOf(model.getPrivilegeID())});

        // close the database connection
        db.close();

        return result > -1;
    }


    /* ############################################# DELETE ACTIONS ############################################# */

    public boolean deleteAllPrivileges() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblPRIVILEGE, null, null);

        // close the database connection
        db.close();

        return result > -1;
    }


    public boolean deletePrivilege(cPrivilegeModel privilegeModel) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long result = -1;

        if (privilegeModel != null) {

            // delete a specific record
            result = db.delete(cSQLDBHelper.TABLE_tblPRIVILEGE,
                    cSQLDBHelper.KEY_ROLE_FK_ID + " = ?",
                    new String[]{String.valueOf(privilegeModel.getPrivilegeID())});
        }

        // close the database connection
        db.close();

        return result > -1;
    }

    /* ############################################# SYNCED ACTIONS ############################################# */

}
