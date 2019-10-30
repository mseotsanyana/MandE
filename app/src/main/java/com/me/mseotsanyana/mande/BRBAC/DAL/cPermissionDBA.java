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

public class cPermissionDBA {
    private static SimpleDateFormat sdf = cConstant.FORMAT_DATE;
    private static String TAG = cPermissionDBA.class.getSimpleName();

    // an object of the database helper
    private cSQLDBHelper dbHelper;
    private cEntityDBA entityDBA;
    private cOperationDBA operationDBA;

    public cPermissionDBA(Context context) {
        dbHelper = new cSQLDBHelper(context);

        entityDBA    = new cEntityDBA(context);
        operationDBA = new cOperationDBA(context);
    }

    /* ##################################### CREATE ACTIONS ##################################### */

    public boolean addPermissionFromExcel(cPermissionModel permissionModel,
                                          ArrayList<Integer> statuses) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_PRIVILEGE_FK_ID, permissionModel.getPrivilegeID());
        cv.put(cSQLDBHelper.KEY_ENTITY_FK_ID, permissionModel.getEntityID());
        cv.put(cSQLDBHelper.KEY_ENTITY_TYPE_FK_ID, permissionModel.getEntityTypeID());
        cv.put(cSQLDBHelper.KEY_OPERATION_FK_ID, permissionModel.getOperationID());

        // insert outcome record
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblPERMISSION, null, cv) < 0) {
                return false;
            }
        } catch (Exception e) {
            Log.d(TAG,"Exception in importing: "+e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }

    public boolean addPermission(cPermissionModel permissionModel) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_PRIVILEGE_FK_ID, permissionModel.getPrivilegeID());
        cv.put(cSQLDBHelper.KEY_ENTITY_FK_ID, permissionModel.getEntityID());
        cv.put(cSQLDBHelper.KEY_ENTITY_TYPE_FK_ID, permissionModel.getEntityTypeID());
        cv.put(cSQLDBHelper.KEY_OPERATION_FK_ID, permissionModel.getOperationID());
        cv.put(cSQLDBHelper.KEY_SERVER_ID, permissionModel.getServerID());
        cv.put(cSQLDBHelper.KEY_ORG_ID, permissionModel.getOrgID());
        cv.put(cSQLDBHelper.KEY_OWNER_ID, permissionModel.getOwnerID());
        cv.put(cSQLDBHelper.KEY_GROUP_BITS, permissionModel.getGroupBITS());
        cv.put(cSQLDBHelper.KEY_PERMS_BITS, permissionModel.getPermsBITS());
        cv.put(cSQLDBHelper.KEY_STATUS_BITS, permissionModel.getStatusBITS());

        // insert outcome record
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblPERMISSION, null, cv) < 0) {
                return false;
            }
        } catch (Exception ex) {
            Log.d("Exception in importing ", ex.getMessage().toString());
        }

        // close the database connection
        db.close();

        return true;
    }

    /* ###################################### READ ACTIONS ###################################### */

    /**
     * Read and filer permissions
     * @param userID
     * @param orgID
     * @param primaryRole
     * @param secondaryRoles
     * @param operationBITS
     * @param statusBITS
     * @return
     */
    public List<cPermissionModel> getPermissionList(
            int userID, int orgID, int primaryRole,
            int secondaryRoles, int operationBITS, int statusBITS) {

        List<cPermissionModel> permissionModels = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String selectQuery = "SELECT * FROM "+ cSQLDBHelper.TABLE_tblPERMISSION +
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
                            sdf.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_CREATED_DATE))));
                    permission.setModifiedDate(
                            sdf.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_MODIFIED_DATE))));
                    permission.setSyncedDate(
                            sdf.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_SYNCED_DATE))));

                    // construct an entity object
                    permission.setEntityModel(new cEntityModel(
                            entityDBA.getEntityByID(permission.getEntityID(), permission.getEntityTypeID())));

                    // construct an operation object
                    permission.setOperationModel(new cOperationModel(operationDBA.getOperationByID(permission.getOperationID())));

                    // populate permission statuses
                    permission.setStatusModelSet(getStatusesByPermissionID(
                            permission.getPrivilegeID(), permission.getEntityID(),
                            permission.getEntityTypeID(), permission.getOperationID()));

                    permissionModels.add(permission);

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

        return permissionModels;
    }

    /**
     * Read statuses by permission ID
     * @param privilegeID
     * @param entityID
     * @param entityTypeID
     * @param operationID
     * @return
     */
    public Set<cStatusModel> getStatusesByPermissionID(int privilegeID, int entityID, int entityTypeID, int operationID) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Set<cStatusModel> statusModelSet = new HashSet();

        // construct a selection query
        String selectQuery = "SELECT " +
                "status." + cSQLDBHelper.KEY_ID + ", status." + cSQLDBHelper.KEY_SERVER_ID + ", " +
                "status." + cSQLDBHelper.KEY_OWNER_ID + ", status." + cSQLDBHelper.KEY_ORG_ID + ", " +
                "status." + cSQLDBHelper.KEY_GROUP_BITS +", status." + cSQLDBHelper.KEY_PERMS_BITS + ", " +
                "status." + cSQLDBHelper.KEY_STATUS_BITS +", status." + cSQLDBHelper.KEY_NAME + ", " +
                "status." + cSQLDBHelper.KEY_DESCRIPTION +", status." + cSQLDBHelper.KEY_CREATED_DATE + ", " +
                "status." + cSQLDBHelper.KEY_MODIFIED_DATE +", status." + cSQLDBHelper.KEY_SYNCED_DATE +
                " FROM " +
                cSQLDBHelper.TABLE_tblPERMISSION + " perm, " +
                cSQLDBHelper.TABLE_tblSTATUS + " status, " +
                cSQLDBHelper.TABLE_tblPERM_STATUS + " perm_status " +
                " WHERE perm."+cSQLDBHelper.KEY_PRIVILEGE_FK_ID + " = perm_status."+cSQLDBHelper.KEY_PRIVILEGE_FK_ID +
                " AND perm."+cSQLDBHelper.KEY_ENTITY_FK_ID + " = perm_status."+cSQLDBHelper.KEY_ENTITY_FK_ID +
                " AND perm."+cSQLDBHelper.KEY_ENTITY_TYPE_FK_ID + " = perm_status."+cSQLDBHelper.KEY_ENTITY_TYPE_FK_ID +
                " AND perm."+cSQLDBHelper.KEY_OPERATION_FK_ID + " = perm_status."+cSQLDBHelper.KEY_OPERATION_FK_ID +
                " AND status."+cSQLDBHelper.KEY_ID + " = perm_status."+cSQLDBHelper.KEY_STATUS_FK_ID +
                " AND perm."+cSQLDBHelper.KEY_PRIVILEGE_FK_ID +" = ? AND perm."+cSQLDBHelper.KEY_ENTITY_FK_ID +" = ?" +
                " AND perm."+cSQLDBHelper.KEY_ENTITY_TYPE_FK_ID +" = ? AND perm."+cSQLDBHelper.KEY_OPERATION_FK_ID +" = ?";

        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(privilegeID), String.valueOf(entityID),
                String.valueOf(entityTypeID), String.valueOf(operationID)});

        try {
            if (cursor.moveToFirst()) {
                do {

                    cStatusModel status = new cStatusModel();

                    status.setStatusID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ID)));
                    status.setServerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_SERVER_ID)));
                    status.setOwnerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OWNER_ID)));
                    status.setOrgID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ORG_ID)));
                    status.setGroupBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_GROUP_BITS)));
                    status.setPermsBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PERMS_BITS)));
                    status.setStatusBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_STATUS_BITS)));
                    status.setName(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_NAME)));
                    status.setDescription(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_DESCRIPTION)));
                    status.setCreatedDate(
                            Timestamp.valueOf(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_CREATED_DATE))));
                    status.setModifiedDate(
                            Timestamp.valueOf(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_MODIFIED_DATE))));
                    status.setSyncedDate(
                            Timestamp.valueOf(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_SYNCED_DATE))));

                    statusModelSet.add(status);

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

        return statusModelSet;
    }

    /* ############################################# UPDATE ACTIONS ############################################# */

    public boolean updatePermission(cPermissionModel model){
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        Date date= new Date();
        Timestamp timestamp = new Timestamp(date.getTime());

        // assign values to the table fields
        //cv.put(cSQLDBHelper.KEY_ORGANIZATION_FK_ID, model.getOrganizationID());
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
        long result = db.update(cSQLDBHelper.TABLE_tblPERMISSION, cv,
                cSQLDBHelper.KEY_ORGANIZATION_FK_ID +" = ? AND "+
                        cSQLDBHelper.KEY_ROLE_FK_ID + "= ?"
                ,
                new String[]{String.valueOf(model.getPrivilegeID()),
                        String.valueOf(model.getEntityID())});

        // close the database connection
        db.close();

        return result > -1;
    }


    /* ############################################# DELETE ACTIONS ############################################# */

    /**
     * Delete all permissions
     * @return
     */
    public boolean deletePermissions() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblPERMISSION, null, null);

        // close the database connection
        db.close();

        return result > -1;
    }


    /**
     * Delete a specific permission
     * @param permissionModel
     * @return
     */
    public boolean deletePermission(cPermissionModel permissionModel) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long result = -1;

        if (permissionModel != null) {

            // delete a specific record
            result = db.delete(cSQLDBHelper.TABLE_tblPERMISSION,
                    cSQLDBHelper.KEY_ROLE_FK_ID + " = ?",
                    new String[]{String.valueOf(permissionModel.getPrivilegeID())});
        }

        // close the database connection
        db.close();

        return result > -1;
    }

    /* ############################################# SYNCED ACTIONS ############################################# */

}
