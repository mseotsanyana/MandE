package com.me.mseotsanyana.mande.BRBAC.DAL;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.util.Pair;

import com.me.mseotsanyana.mande.STORAGE.database.cSQLDBHelper;
import com.me.mseotsanyana.mande.UTILITY.cConstant;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
    public boolean addPrivilegeFromExcel(cPrivilegeModel privilegeModel, ArrayList<Integer> entities,
                                         ArrayList<Integer> entityTypes, ArrayList<Integer> operations,
                                         ArrayList<Integer> statuses) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_ID, privilegeModel.getPrivilegeID());
        cv.put(cSQLDBHelper.KEY_NAME, privilegeModel.getName());
        cv.put(cSQLDBHelper.KEY_DESCRIPTION, privilegeModel.getDescription());

        // insert record
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblPRIVILEGE, null, cv) < 0) {
                return false;
            }


            // add permission statuses
            for (int i = 0; i < entities.size(); i++) {
                if (addPrivilegePermission(privilegeModel.getPrivilegeID(),
                        entities.get(i), entityTypes.get(i), operations.get(i)))
                    continue;
                else
                    return false;
            }

            // add permission statuses
            for (int status : statuses) {
                if (addPrivilegeStatus(privilegeModel.getPrivilegeID(), status))
                    continue;
                else
                    return false;
            }


        } catch (Exception e) {
            Log.d(TAG, "Exception in adding: " + e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }

    public boolean addPrivilegePermission(int privilegeID, int entityID, int entityTypeID, int operationID) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(cSQLDBHelper.KEY_PRIVILEGE_FK_ID, privilegeID);
        cv.put(cSQLDBHelper.KEY_ENTITY_FK_ID, entityID);
        cv.put(cSQLDBHelper.KEY_ENTITY_TYPE_FK_ID, entityTypeID);
        cv.put(cSQLDBHelper.KEY_OPERATION_FK_ID, operationID);

        if (db.insert(cSQLDBHelper.TABLE_tblPERMISSION, null, cv) < 0) {
            return false;
        }

        return true;
    }

    public boolean addPrivilegeStatus(int privilegeID, int statusID) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(cSQLDBHelper.KEY_PRIVILEGE_FK_ID, privilegeID);
        cv.put(cSQLDBHelper.KEY_STATUS_FK_ID, statusID);

        if (db.insert(cSQLDBHelper.TABLE_tblPRIV_STATUS, null, cv) < 0) {
            return false;
        }

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

    public Set<cPrivilegeModel> getPrivilegeModelSet(
            int userID, int orgID, int primaryRole,
            int secondaryRoles, int operationBITS, int statusBITS) {

        Set<cPrivilegeModel> privilegeModelSet = new HashSet<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + cSQLDBHelper.TABLE_tblPRIVILEGE +
                " WHERE (((" + cSQLDBHelper.KEY_GROUP_BITS + " & ?) != 0) " +
                " OR ((" + cSQLDBHelper.KEY_OWNER_ID + " = ?) " +
                " AND ((" + cSQLDBHelper.KEY_PERMS_BITS + " & ?) != 0)) " +
                " OR (((" + cSQLDBHelper.KEY_GROUP_BITS + " & ?) != 0) " +
                " AND ((" + cSQLDBHelper.KEY_PERMS_BITS + " & ?) != 0)))";

        Cursor cursor = db.rawQuery(selectQuery, new String[]{
                String.valueOf(primaryRole),
                String.valueOf(userID), String.valueOf(operationBITS),
                String.valueOf(secondaryRoles), String.valueOf(operationBITS)});

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

                    // populate permissions for a specific role
                    privilege.setPermModelMap(getPermissionByPrivilegeID(privilege.getPrivilegeID()));

                    // populate permission statuses for a specific role
                    privilege.setStatusModelSet(getStatusByPrivilegeID(privilege.getPrivilegeID()));

                    privilegeModelSet.add(privilege);

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "ERROR READING ROLE SET:- " + e.getMessage());
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
     * Read privileges by privilege ID
     *
     * @param privilegeID
     * @return Set
     */
    public HashMap<cEntityModel, Set<cOperationModel>> getPermissionByPrivilegeID(int privilegeID) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        HashMap<cEntityModel, Set<cOperationModel>> permModelMap = new HashMap<>();

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

        Cursor cursorPerms = db.rawQuery(selectQuery, new String[]{String.valueOf(privilegeID)});

        Set<Pair<Integer, Integer>> entitySet = new HashSet<>();
        //HashMap<cEntityModel, Set<cOperationModel>> permModelMap = new HashMap<>();

        try {
            if (cursorPerms.moveToFirst()) {
                do {
                    int entityID = cursorPerms.getInt(cursorPerms.getColumnIndex(
                            cSQLDBHelper.KEY_ENTITY_FK_ID));
                    int entityTypeID = cursorPerms.getInt(cursorPerms.getColumnIndex(
                            cSQLDBHelper.KEY_ENTITY_TYPE_FK_ID));

                    entitySet.add(new Pair<Integer, Integer>(entityID, entityTypeID));

                } while (cursorPerms.moveToNext());

            }

            cOperationModel operationModel;

            for (Pair<Integer, Integer> entity : entitySet) {
                cEntityModel entityModel = getEntityByID(entity.first, entity.second);
                Set<cOperationModel> operationModelSet = new HashSet<>();
                if (cursorPerms.moveToFirst()) {
                    do {
                        int entityID = cursorPerms.getInt(cursorPerms.getColumnIndex(
                                cSQLDBHelper.KEY_ENTITY_FK_ID));
                        int entityTypeID = cursorPerms.getInt(cursorPerms.getColumnIndex(
                                cSQLDBHelper.KEY_ENTITY_TYPE_FK_ID));
                        int operationID = cursorPerms.getInt(cursorPerms.getColumnIndex(
                                cSQLDBHelper.KEY_OPERATION_FK_ID));
                        if (entity.first == entityID && entity.second == entityTypeID) {
                            operationModel = getOperationByID(operationID);
                            operationModelSet.add(operationModel);
                        }
                    } while (cursorPerms.moveToNext());
                }
                permModelMap.put(entityModel, operationModelSet);
            }

            //Gson gson = new Gson();
            //Log.d(TAG, "ENTITY = " + gson.toJson(entitySet));

        } catch (Exception e) {
            Log.d(TAG, "Error while reading: " + e.getMessage());
        } finally {
            if (cursorPerms != null && !cursorPerms.isClosed()) {
                cursorPerms.close();
            }
        }

        // close the database connection
        db.close();

        return permModelMap;
    }

    public cEntityModel getEntityByID(int entityID, int typeID) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        // construct a selection query
        String selectQuery = "SELECT * FROM " +
                cSQLDBHelper.TABLE_tblENTITY + " WHERE " +
                cSQLDBHelper.KEY_ID + " = ? AND " + cSQLDBHelper.KEY_ENTITY_TYPE_ID + " = ?";

        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(entityID), String.valueOf(typeID)});

        cEntityModel entity = new cEntityModel();

        try {
            if (cursor.moveToFirst()) {
                do {
                    entity.setEntityID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ID)));
                    entity.setEntityTypeID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ENTITY_TYPE_ID)));
                    entity.setServerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_SERVER_ID)));
                    entity.setOwnerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OWNER_ID)));
                    entity.setOrgID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ORG_ID)));
                    entity.setGroupBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_GROUP_BITS)));
                    entity.setPermsBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PERMS_BITS)));
                    entity.setStatusBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_STATUS_BITS)));
                    entity.setName(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_NAME)));
                    entity.setDescription(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_DESCRIPTION)));
                    entity.setCreatedDate(
                            Timestamp.valueOf(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_CREATED_DATE))));
                    entity.setModifiedDate(
                            Timestamp.valueOf(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_MODIFIED_DATE))));
                    entity.setSyncedDate(
                            Timestamp.valueOf(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_SYNCED_DATE))));
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

        return entity;
    }

    public cOperationModel getOperationByID(int operationID) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        // construct a selection query
        String selectQuery = "SELECT * FROM " +
                cSQLDBHelper.TABLE_tblOPERATION + " WHERE " +
                cSQLDBHelper.KEY_ID + "= ?";

        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(operationID)});

        cOperationModel operation = new cOperationModel();

        try {
            if (cursor.moveToFirst()) {
                operation.setOperationID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ID)));
                operation.setServerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_SERVER_ID)));
                operation.setOwnerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OWNER_ID)));
                operation.setOrgID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ORG_ID)));
                operation.setGroupBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_GROUP_BITS)));
                operation.setPermsBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PERMS_BITS)));
                operation.setStatusBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_STATUS_BITS)));
                operation.setName(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_NAME)));
                operation.setDescription(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_DESCRIPTION)));
                operation.setCreatedDate(
                        Timestamp.valueOf(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_CREATED_DATE))));
                operation.setModifiedDate(
                        Timestamp.valueOf(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_MODIFIED_DATE))));
                operation.setSyncedDate(
                        Timestamp.valueOf(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_SYNCED_DATE))));
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

        return operation;
    }

    /*
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
    */
    public Set<cStatusModel> getStatusByPrivilegeID(int privilegeID) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Set<cStatusModel> statusModelSet = new HashSet();

        // construct a selection query
        String selectQuery = "SELECT status." + cSQLDBHelper.KEY_ID + ", " +
                "status." + cSQLDBHelper.KEY_SERVER_ID + ", " +
                "status." + cSQLDBHelper.KEY_OWNER_ID + ", status." + cSQLDBHelper.KEY_ORG_ID + ", " +
                "status." + cSQLDBHelper.KEY_GROUP_BITS + ", status." + cSQLDBHelper.KEY_PERMS_BITS + ", " +
                "status." + cSQLDBHelper.KEY_STATUS_BITS + ", status." + cSQLDBHelper.KEY_NAME + ", " +
                "status." + cSQLDBHelper.KEY_DESCRIPTION + ", " +
                "status." + cSQLDBHelper.KEY_CREATED_DATE + ", " +
                "status." + cSQLDBHelper.KEY_MODIFIED_DATE + ", " +
                "status." + cSQLDBHelper.KEY_SYNCED_DATE +
                " FROM " +
                cSQLDBHelper.TABLE_tblPRIVILEGE + " privilege, " +
                cSQLDBHelper.TABLE_tblSTATUS + " status, " +
                cSQLDBHelper.TABLE_tblPRIV_STATUS + " priv_status " +
                " WHERE privilege." + cSQLDBHelper.KEY_ID + " = priv_status." + cSQLDBHelper.KEY_PRIVILEGE_FK_ID +
                " AND status." + cSQLDBHelper.KEY_ID + " = priv_status." + cSQLDBHelper.KEY_STATUS_FK_ID +
                " AND privilege." + cSQLDBHelper.KEY_ID + " = ?";

        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(privilegeID)});

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
            Log.d(TAG, "Error while reading: " + e.getMessage());
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        // close the database connection
        db.close();

        return statusModelSet;
    }


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
                    //privilege.setPermissionModelSet(getPermissionsByPrivilegeID(privilege.getPrivilegeID()));

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

                //privilege.setPermissionModelSet(getPermissionsByPrivilegeID(privilege.getPrivilegeID()));
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
