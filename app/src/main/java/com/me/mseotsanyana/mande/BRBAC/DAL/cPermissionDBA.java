package com.me.mseotsanyana.mande.BRBAC.DAL;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.me.mseotsanyana.mande.COM.cEntityBITS;
import com.me.mseotsanyana.mande.COM.cOperationBITS;
import com.me.mseotsanyana.mande.COM.cStatusBITS;
import com.me.mseotsanyana.mande.PPMER.DAL.cSQLDBHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import static com.me.mseotsanyana.mande.Util.cConstant.FORMAT_DATE;

/**
 * Created by mseotsanyana on 2017/06/27.
 */

public class cPermissionDBA {
    private static final String TAG = "cPermissionDBA";
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);

    // an object of the database helper
    private cSQLDBHelper dbHelper;

    private cEntityDBA entityDBA;
    private cOperationDBA operationDBA;
    private cStatusDBA statusDBA;



    public cPermissionDBA(Context context) {
        dbHelper = new cSQLDBHelper(context);
//        entityDBA = new cEntityDBA(context);
        //       operationDBA = new cOperationDBA(context);
        //       statusDBA = new cStatusDBA(context);

    }

    public boolean deleteAllPermissions() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_PERMISSION, null, null);

        // close the database connection
        db.close();

        return result > -1;
    }

    public boolean addPermissionFromExcel(cPermissionModel permissionModel) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long result = -1;

        cPrivilegeModel privilegeModel = permissionModel.getPrivilegeModel();
        cEntityModel entityModel = permissionModel.getEntityModel();
        cOperationModel operationModel = permissionModel.getOperationModel();
        cStatusModel statusModel = permissionModel.getStatusModel();

        if ((privilegeModel != null) && (entityModel != null) && (operationModel != null) && (statusModel != null)) {

            ContentValues cv = new ContentValues();

            cv.put(cSQLDBHelper.KEY_ORGANIZATION_FK_ID, permissionModel.getOrganizationID());
            cv.put(cSQLDBHelper.KEY_PRIVILEGE_FK_ID, privilegeModel.getPrivilegeID());
            cv.put(cSQLDBHelper.KEY_ENTITY_FK_ID, entityModel.getEntityID());
            cv.put(cSQLDBHelper.KEY_ENTITY_TYPE_FK_ID, entityModel.getTypeID());
            cv.put(cSQLDBHelper.KEY_OPERATION_FK_ID, operationModel.getOperationID());
            cv.put(cSQLDBHelper.KEY_STATUS_FK_ID, statusModel.getStatusID());

            // insert permission group record
            result = db.insert(cSQLDBHelper.TABLE_PERMISSION, null, cv);
        }

        // close the database connection
        db.close();

        return result > -1;
    }

    public boolean updatePermissionFromExcel(cPermissionModel permissionModel) {

        return true;
    }

    public boolean addPermission(cPermissionModel permissionModel) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long result = -1;

        cPrivilegeModel privilegeModel = null;
        cEntityModel entityModel = null;
        cOperationModel operationModel = null;
        cStatusModel statusModel = null;

        if (permissionModel != null) {
            privilegeModel = permissionModel.getPrivilegeModel();
            entityModel = permissionModel.getEntityModel();
            operationModel = permissionModel.getOperationModel();
            statusModel = permissionModel.getStatusModel();
        }

        if (((privilegeModel != null) && (entityModel != null) &&
                (operationModel != null) && (statusModel != null))) {

            ContentValues cv = new ContentValues();

            cv.put(cSQLDBHelper.KEY_ORGANIZATION_FK_ID, permissionModel.getOrganizationID());
            cv.put(cSQLDBHelper.KEY_PRIVILEGE_FK_ID, privilegeModel.getPrivilegeID());
            cv.put(cSQLDBHelper.KEY_ENTITY_FK_ID, entityModel.getEntityID());
            cv.put(cSQLDBHelper.KEY_ENTITY_TYPE_FK_ID, entityModel.getTypeID());
            cv.put(cSQLDBHelper.KEY_OPERATION_FK_ID, operationModel.getOperationID());
            cv.put(cSQLDBHelper.KEY_STATUS_FK_ID, statusModel.getStatusID());
            cv.put(cSQLDBHelper.KEY_PERMISSION_OWNER_ID, permissionModel.getOwnerID());
            cv.put(cSQLDBHelper.KEY_PERMISSION_ORG_ID, permissionModel.getOrgID());
            cv.put(cSQLDBHelper.KEY_PERMISSION_GROUP_BITS, permissionModel.getGroupBITS());
            cv.put(cSQLDBHelper.KEY_PERMISSION_PERMS_BITS, permissionModel.getPermsBITS());
            cv.put(cSQLDBHelper.KEY_PERMISSION_STATUS_BITS, permissionModel.getStatusBITS());

            // insert permission group record
            result = db.insert(cSQLDBHelper.TABLE_PERMISSION, null, cv);
        }

        // close the database connection
        db.close();

        return result > -1;
    }

    public boolean updatePermission(cPermissionModel permissionModel) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long result = -1;

        cPrivilegeModel privilegeModel = null;
        cEntityModel entityModel = null;
        cOperationModel operationModel = null;
        cStatusModel statusModel = null;

        if (permissionModel != null) {
            privilegeModel = permissionModel.getPrivilegeModel();
            entityModel = permissionModel.getEntityModel();
            operationModel = permissionModel.getOperationModel();
            statusModel = permissionModel.getStatusModel();
        }

        if (((privilegeModel != null) && (entityModel != null) &&
                (operationModel != null) && (statusModel != null))) {

            ContentValues cv = new ContentValues();
            // assign values to the table fields
            cv.put(cSQLDBHelper.KEY_PRIVILEGE_FK_ID, privilegeModel.getPrivilegeID());
            cv.put(cSQLDBHelper.KEY_ENTITY_FK_ID, entityModel.getEntityID());
            cv.put(cSQLDBHelper.KEY_ENTITY_TYPE_FK_ID, entityModel.getTypeID());
            cv.put(cSQLDBHelper.KEY_OPERATION_FK_ID, operationModel.getOperationID());
            cv.put(cSQLDBHelper.KEY_STATUS_FK_ID, statusModel.getStatusID());
            cv.put(cSQLDBHelper.KEY_PERMISSION_OWNER_ID, permissionModel.getOwnerID());
            cv.put(cSQLDBHelper.KEY_PERMISSION_ORG_ID, permissionModel.getOrgID());
            cv.put(cSQLDBHelper.KEY_PERMISSION_GROUP_BITS, permissionModel.getGroupBITS());
            cv.put(cSQLDBHelper.KEY_PERMISSION_PERMS_BITS, permissionModel.getPermsBITS());
            cv.put(cSQLDBHelper.KEY_PERMISSION_STATUS_BITS, permissionModel.getStatusBITS());
            cv.put(cSQLDBHelper.KEY_PERMISSION_CREATED_DATE, sdf.format(permissionModel.getCreatedDate()));
            cv.put(cSQLDBHelper.KEY_PERMISSION_MODIFIED_DATE, sdf.format(permissionModel.getModifiedDate()));
            cv.put(cSQLDBHelper.KEY_PERMISSION_SYNCED_DATE, sdf.format(permissionModel.getSyncedDate()));

            // update a specific record
            result = db.update(cSQLDBHelper.TABLE_PERMISSION, cv,
                    cSQLDBHelper.KEY_PRIVILEGE_FK_ID + " = ? AND " +
                            cSQLDBHelper.KEY_ENTITY_FK_ID + " = ? AND " +
                            cSQLDBHelper.KEY_ENTITY_TYPE_FK_ID + "= ? AND " +
                            cSQLDBHelper.KEY_OPERATION_FK_ID + " = ? AND " +
                            cSQLDBHelper.KEY_STATUS_FK_ID + " = ?",
                    new String[]{String.valueOf(privilegeModel.getPrivilegeID()),
                            String.valueOf(entityModel.getEntityID()),
                            String.valueOf(entityModel.getTypeID()),
                            String.valueOf(operationModel.getOperationID()),
                            String.valueOf(statusModel.getStatusID())});

            // close the database connection
            db.close();

        }

        return result > -1;
    }

    public boolean deletePermission(cPermissionModel permissionModel) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long result = -1;

        cPrivilegeModel privilegeModel = null;
        cEntityModel entityModel = null;
        cOperationModel operationModel = null;
        cStatusModel statusModel = null;

        if (permissionModel != null) {
            privilegeModel = permissionModel.getPrivilegeModel();
            entityModel = permissionModel.getEntityModel();
            operationModel = permissionModel.getOperationModel();
            statusModel = permissionModel.getStatusModel();
        }

        if (((privilegeModel != null) && (entityModel != null) &&
                (operationModel != null) && (statusModel != null))) {

            // delete a specific record
            result = db.delete(cSQLDBHelper.TABLE_PERMISSION,
                    cSQLDBHelper.KEY_PRIVILEGE_FK_ID + " = ? AND " +
                            cSQLDBHelper.KEY_ENTITY_FK_ID + " = ? AND " +
                            cSQLDBHelper.KEY_ENTITY_TYPE_FK_ID + "= ? AND " +
                            cSQLDBHelper.KEY_OPERATION_FK_ID + " = ? AND " +
                            cSQLDBHelper.KEY_STATUS_FK_ID + " = ?",
                    new String[]{String.valueOf(privilegeModel.getPrivilegeID()),
                            String.valueOf(entityModel.getEntityID()),
                            String.valueOf(entityModel.getTypeID()),
                            String.valueOf(operationModel.getOperationID()),
                            String.valueOf(statusModel.getStatusID())});
        }

        // close the database connection
        db.close();

        return result > -1;
    }


    public boolean deletePermissionByIDs(int privilegeID, int entityID, int typeID, int operationID, int statusID) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete a specific record
        long result = db.delete(cSQLDBHelper.TABLE_PERMISSION,
                cSQLDBHelper.KEY_PRIVILEGE_FK_ID + " = ? AND " +
                        cSQLDBHelper.KEY_ENTITY_FK_ID + " = ? AND " +
                        cSQLDBHelper.KEY_ENTITY_TYPE_FK_ID + "= ? AND " +
                        cSQLDBHelper.KEY_OPERATION_FK_ID + " = ? AND " +
                        cSQLDBHelper.KEY_STATUS_FK_ID + " = ?",
                new String[]{String.valueOf(privilegeID), String.valueOf(entityID),
                        String.valueOf(typeID), String.valueOf(operationID),
                        String.valueOf(statusID)});

        // close the database connection
        db.close();

        return result > -1;
    }

    public boolean deletePermissionByEntityIDs(int organizationID, int privilegeID, int entityID, int typeID) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete a specific record
        long result = db.delete(cSQLDBHelper.TABLE_PERMISSION,
                cSQLDBHelper.KEY_ORGANIZATION_FK_ID + " = ? AND " +
                        cSQLDBHelper.KEY_PRIVILEGE_FK_ID + " = ? AND " +
                        cSQLDBHelper.KEY_ENTITY_FK_ID + "= ? AND " +
                        cSQLDBHelper.KEY_ENTITY_TYPE_FK_ID + " = ?",
                new String[]{String.valueOf(organizationID), String.valueOf(privilegeID),
                        String.valueOf(entityID), String.valueOf(typeID)});

        // close the database connection
        db.close();

        return result > -1;
    }

    public List<cPermissionModel> getPermissionList(
            int userID, int primaryRole, int secondaryRoles, int operationBITS, int statusBITS) {

        List<cPermissionModel> permissionModels = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // construct a selection query
        //String selectQuery = "SELECT * FROM " + cSQLDBHelper.TABLE_PERMISSION;

        String selectQuery = "SELECT * FROM " + cSQLDBHelper.TABLE_PERMISSION +
                " WHERE (((" + cSQLDBHelper.KEY_PERMISSION_GROUP_BITS + " & ?) = ?) " +
                " OR (((" + cSQLDBHelper.KEY_PERMISSION_OWNER_ID + " = ?) " +
                " AND ((" + cSQLDBHelper.KEY_PERMISSION_PERMS_BITS + " & ?) != 0)) " +
                " OR (((" + cSQLDBHelper.KEY_PERMISSION_GROUP_BITS + " & ?) != 0) " +
                " AND ((" + cSQLDBHelper.KEY_PERMISSION_PERMS_BITS + " & ?) != 0))" +
                " AND ((" + cSQLDBHelper.KEY_PERMISSION_STATUS_BITS + " & ?) != 0)))";

        //Cursor cursor = db.rawQuery(selectQuery, null);
        Cursor cursor = db.rawQuery(selectQuery, new String[]{
                String.valueOf(primaryRole), String.valueOf(primaryRole),
                String.valueOf(userID), String.valueOf(operationBITS),
                String.valueOf(secondaryRoles), String.valueOf(operationBITS),
                String.valueOf(statusBITS)});

        try {
            if (cursor.moveToFirst()) {
                do {
                    cPermissionModel permissionModel = new cPermissionModel();

                    cPrivilegeModel privilegeModel = new cPrivilegeModel();
                    cEntityModel entityModel = new cEntityModel();
                    cOperationModel operationModel = new cOperationModel();

                    privilegeModel.setPrivilegeID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PRIVILEGE_FK_ID)));
                    entityModel.setEntityID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ENTITY_FK_ID)));
                    entityModel.setTypeID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ENTITY_TYPE_FK_ID)));
                    operationModel.setOperationID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OPERATION_FK_ID)));

                    permissionModel.setPrivilegeModel(privilegeModel);
                    permissionModel.setEntityModel(entityModel);
                    permissionModel.setOperationModel(operationModel);
                    permissionModel.setOwnerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PERMISSION_OWNER_ID)));
                    permissionModel.setGroupBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PERMISSION_GROUP_BITS)));
                    permissionModel.setPermsBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PERMISSION_PERMS_BITS)));
                    permissionModel.setStatusBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PERMISSION_STATUS_BITS)));

                    permissionModels.add(permissionModel);

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to get permission groups from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        // close the database connection
        db.close();

        return permissionModels;
    }


    public List<cPermissionModel> getDistinctPermissionList() {

        List<cPermissionModel> permissionModels = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // construct a selection query
        String selectQuery = "SELECT " + cSQLDBHelper.KEY_PRIVILEGE_FK_ID + ", " +
                cSQLDBHelper.KEY_ENTITY_FK_ID + ", " + cSQLDBHelper.KEY_ENTITY_TYPE_FK_ID + ", " +
                "SUM(DISTINCT " + cSQLDBHelper.KEY_OPERATION_FK_ID + ") AS operations FROM " +
                cSQLDBHelper.TABLE_PERMISSION + " GROUP BY " + cSQLDBHelper.KEY_PRIVILEGE_FK_ID + ", " +
                cSQLDBHelper.KEY_ENTITY_FK_ID + ", " + cSQLDBHelper.KEY_ENTITY_TYPE_FK_ID;

        Cursor cursor = db.rawQuery(selectQuery, null);

        try {
            if (cursor.moveToFirst()) {
                do {
                    cPermissionModel permissionModel = new cPermissionModel();

                    //permissionModel.setPrivilegeID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PRIVILEGE_FK_ID)));
                    //permissionModel.setEntityID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ENTITY_FK_ID)));
                    //permissionModel.setTypeID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ENTITY_TYPE_FK_ID)));
                    //permissionModel.setOperationID(cursor.getInt(cursor.getColumnIndex("operations")));
                    //permissionModel.setStatuses(cursor.getInt(cursor.getColumnIndex("statuses")));
                    //permissionModel.setOwnerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PERMISSION_OWNER_ID)));
                    //permissionModel.setGroupBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PERMISSION_GROUP_BITS)));
                    //permissionModel.setPermsBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PERMISSION_PERMS_BITS)));
                    //permissionModel.setStatusBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PERMISSION_STATUS_BITS)));
                    //permissionModel.setCreateDate(formatter.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_PERMISSION_DATE))));

                    permissionModels.add(permissionModel);

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to get permission groups from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        // close the database connection
        db.close();

        return permissionModels;
    }

    public cPermissionModel getPermissionByIDs(int privilegeID, int entityID, int typeID, int operationID) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        // construct a selection query
        String selectQuery = "SELECT * FROM " +
                cSQLDBHelper.TABLE_PERMISSION + " WHERE " +
                cSQLDBHelper.KEY_PRIVILEGE_FK_ID + "= ? AND " +
                cSQLDBHelper.KEY_ENTITY_FK_ID + "= ? AND " +
                cSQLDBHelper.KEY_ENTITY_TYPE_FK_ID + "= ? AND " +
                cSQLDBHelper.KEY_OPERATION_FK_ID + " = ?";

        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(privilegeID),
                String.valueOf(entityID), String.valueOf(typeID), String.valueOf(operationID)});

        cPermissionModel permission = new cPermissionModel();
        cPrivilegeModel privilegeModel = new cPrivilegeModel();
        cEntityModel entityModel = new cEntityModel();
        cOperationModel operationModel = new cOperationModel();

        try {
            if (cursor.moveToFirst()) {
                do {
                    privilegeModel.setPrivilegeID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PRIVILEGE_FK_ID)));
                    entityModel.setEntityID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ENTITY_FK_ID)));
                    entityModel.setTypeID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ENTITY_TYPE_FK_ID)));
                    operationModel.setOperationID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OPERATION_FK_ID)));

                    permission.setPrivilegeModel(privilegeModel);
                    permission.setEntityModel(entityModel);
                    permission.setOperationModel(operationModel);
                    permission.setOwnerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PERMISSION_OWNER_ID)));
                    permission.setGroupBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PERMISSION_GROUP_BITS)));
                    permission.setPermsBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PERMISSION_PERMS_BITS)));
                    permission.setStatusBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PERMISSION_STATUS_BITS)));
                    //permission.setCreateDate(formatter.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_PERMISSION_DATE))));
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
    }                //cSQLDBHelper.KEY_ROLE_DATE


    public cPermissionModel getPermissionByIDs(int privilegeID, int entityID, int typeID) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        // construct a selection query
        String selectQuery = "SELECT * FROM " +
                cSQLDBHelper.TABLE_PERMISSION + " WHERE " +
                cSQLDBHelper.KEY_PRIVILEGE_FK_ID + "= ? AND " +
                cSQLDBHelper.KEY_ENTITY_FK_ID + "= ? AND " +
                cSQLDBHelper.KEY_ENTITY_TYPE_FK_ID + "= ?";

        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(privilegeID),
                String.valueOf(entityID), String.valueOf(typeID)});

        cPermissionModel permission = new cPermissionModel();
        cPrivilegeModel privilegeModel = new cPrivilegeModel();
        cEntityModel entityModel = new cEntityModel();
        cOperationModel operationModel = new cOperationModel();

        try {
            if (cursor.moveToFirst()) {
                do {
                    privilegeModel.setPrivilegeID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PRIVILEGE_FK_ID)));
                    entityModel.setEntityID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ENTITY_FK_ID)));
                    entityModel.setTypeID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ENTITY_TYPE_FK_ID)));
                    operationModel.setOperationID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OPERATION_FK_ID)));

                    permission.setPrivilegeModel(privilegeModel);
                    permission.setEntityModel(entityModel);
                    permission.setOperationModel(operationModel);

                    permission.setOwnerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PERMISSION_OWNER_ID)));
                    permission.setGroupBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PERMISSION_GROUP_BITS)));
                    permission.setPermsBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PERMISSION_PERMS_BITS)));
                    permission.setStatusBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PERMISSION_STATUS_BITS)));
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

    public ArrayList<cPermissionModel> getPermissionsByPrivilegeID(int privilegeID) {

        ArrayList<cPermissionModel> permissionModels = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " +
                cSQLDBHelper.TABLE_PRIVILEGE + " privilege," + cSQLDBHelper.TABLE_PERMISSION + " permission" +
                " WHERE privilege." + cSQLDBHelper.KEY_ROLE_FK_ID + " = permission." + cSQLDBHelper.KEY_PRIVILEGE_FK_ID +
                " AND " + privilegeID + " = privilege." + cSQLDBHelper.KEY_ROLE_FK_ID, null);
        /*
        Cursor cursor = db.rawQuery("SELECT permission." + cSQLDBHelper.KEY_ROLE_FK_ID + ", permission." +
                cSQLDBHelper.KEY_ENTITY_FK_ID + ", permission." + cSQLDBHelper.KEY_OPERATION_FK_ID + ", permission." +
                cSQLDBHelper.KEY_OPERATION_TYPE_FK_ID + ", permission." + cSQLDBHelper.KEY_STATUS_FK_ID +
                " FROM "+ cSQLDBHelper.TABLE_ROLE +" role," + cSQLDBHelper.TABLE_PERMISSION +" permission" +
                " WHERE role." + cSQLDBHelper.KEY_ROLE_ID +" = permission." + cSQLDBHelper.KEY_ROLE_FK_ID +
                " AND "+ roleID + "= role." + cSQLDBHelper.KEY_ROLE_ID, null);
        */
        try {
            if (cursor.moveToFirst()) {
                do {
                    cPermissionModel permission = new cPermissionModel();

                    cPrivilegeModel privilegeModel = new cPrivilegeModel();
                    cEntityModel entityModel = new cEntityModel();
                    cOperationModel operationModel = new cOperationModel();

                    privilegeModel.setPrivilegeID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PRIVILEGE_FK_ID)));
                    entityModel.setEntityID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ENTITY_FK_ID)));
                    entityModel.setTypeID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ENTITY_TYPE_FK_ID)));
                    operationModel.setOperationID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OPERATION_FK_ID)));

                    permission.setPrivilegeModel(privilegeModel);
                    permission.setEntityModel(entityModel);
                    permission.setOperationModel(operationModel);
                    permission.setOwnerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PERMISSION_OWNER_ID)));
                    permission.setGroupBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PERMISSION_GROUP_BITS)));
                    permission.setPermsBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PERMISSION_PERMS_BITS)));
                    permission.setStatusBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PERMISSION_STATUS_BITS)));
                    //permission.setCreateDate(formatter.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_PERMISSION_DATE))));

                    permissionModels.add(permission);

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to execute: 'getPermissionsByPrivilegeID()'");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        // close the database connection
        db.close();

        return permissionModels;
    }

    public ArrayList<cPermissionModel> getPermissionBITSByPrivilegeID(int privilegeID) {

        ArrayList<cPermissionModel> permissionModels = new ArrayList<>();

        String selectQuery = "SELECT privilege." + cSQLDBHelper.KEY_ROLE_FK_ID + " AS privilegeID, " +
                " permission." + cSQLDBHelper.KEY_ENTITY_FK_ID + " AS entities, " +
                " permission." + cSQLDBHelper.KEY_ENTITY_TYPE_FK_ID + " AS entity_type, " +
                " SUM(DISTINCT " + cSQLDBHelper.KEY_OPERATION_FK_ID + ") AS operations " +
                " FROM " + cSQLDBHelper.TABLE_PRIVILEGE + " privilege," + cSQLDBHelper.TABLE_PERMISSION + " permission" +
                " WHERE privilege." + cSQLDBHelper.KEY_ROLE_FK_ID + " = permission." + cSQLDBHelper.KEY_PRIVILEGE_FK_ID +
                " AND privilege." + cSQLDBHelper.KEY_ROLE_FK_ID + " = ? GROUP BY entities";

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(privilegeID)});

        try {
            if (cursor.moveToFirst()) {
                do {
                    cPermissionModel permission = new cPermissionModel();
                    cPrivilegeModel privilegeModel = new cPrivilegeModel();
                    cEntityModel entityModel = new cEntityModel();
                    cOperationModel operationModel = new cOperationModel();

                    privilegeModel.setPrivilegeID(cursor.getInt(cursor.getColumnIndex("privilegeID")));
                    entityModel.setEntityID(cursor.getInt(cursor.getColumnIndex("entities")));
                    entityModel.setTypeID(cursor.getInt(cursor.getColumnIndex("entity_type")));
                    operationModel.setOperationID(cursor.getInt(cursor.getColumnIndex("operations")));

                    permission.setPrivilegeModel(privilegeModel);
                    permission.setEntityModel(entityModel);
                    permission.setOperationModel(operationModel);

                    permissionModels.add(permission);

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to execute: 'getPermissionBITSByPrivilegeID()'");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        // close the database connection
        db.close();

        return permissionModels;
    }

    public List<cRoleModel> getRolesByPrivilegeID(int privilegeID) {

        List<cRoleModel> roleModels = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String selectQuery = "SELECT role." + cSQLDBHelper.KEY_ROLE_ID + ", role." +
                cSQLDBHelper.KEY_ROLE_NAME + ", role." + cSQLDBHelper.KEY_ROLE_DESCRIPTION + ", role." +
                cSQLDBHelper.KEY_ROLE_CREATED_DATE + " FROM " +
                cSQLDBHelper.TABLE_PRIVILEGE + " privilege, " +
                cSQLDBHelper.TABLE_ROLE + " role, " +
                cSQLDBHelper.TABLE_PERMISSION + " privilege_role " +
                "WHERE privilege." + cSQLDBHelper.KEY_ROLE_FK_ID + " = privilege_role." + cSQLDBHelper.KEY_PRIVILEGE_FK_ID +
                " AND role." + cSQLDBHelper.KEY_ROLE_ID + " = privilege_role." + cSQLDBHelper.KEY_ROLE_FK_ID +
                " AND privilege." + cSQLDBHelper.KEY_ROLE_FK_ID + " = ?";

        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(privilegeID)});

        try {
            if (cursor.moveToFirst()) {
                do {
                    cRoleModel role = new cRoleModel();

                    role.setRoleID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ROLE_ID)));
                    role.setOrganizationID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ORGANIZATION_FK_ID)));
                    role.setOwnerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ROLE_OWNER_ID)));
                    role.setGroupBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ROLE_GROUP_BITS)));
                    role.setPermsBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ROLE_PERMS_BITS)));
                    role.setStatusBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ROLE_STATUS_BITS)));
                    role.setName(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_ROLE_NAME)));
                    role.setDescription(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_ROLE_DESCRIPTION)));
                    //role.setCreateDate(formatter.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_ROLE_DATE))));

                    roleModels.add(role);

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to get permission groups from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        // close the database connection
        db.close();

        return roleModels;
    }

    public ArrayList<Integer> getRoleIDsByPermissionID(int permissionID) {

        ArrayList<Integer> roleIDs = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT role." + cSQLDBHelper.KEY_ROLE_ID + " FROM " +
                cSQLDBHelper.TABLE_PERMISSION + " permission, " +
                cSQLDBHelper.TABLE_ROLE + " role, " +
                cSQLDBHelper.TABLE_PERMISSION + " permission_role " +
                "WHERE permission." + cSQLDBHelper.KEY_ROLE_FK_ID + " = permission_role." + cSQLDBHelper.KEY_ROLE_FK_ID +
                " AND role." + cSQLDBHelper.KEY_ROLE_ID + " = permission_role." + cSQLDBHelper.KEY_ROLE_FK_ID +
                " AND " + permissionID + " = permission." + cSQLDBHelper.KEY_ROLE_FK_ID, null);

        try {
            if (cursor.moveToFirst()) {
                do {
                    roleIDs.add(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ROLE_ID)));
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to get permission groups from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        // close the database connection
        db.close();

        return roleIDs;
    }


    public ArrayList<cPermissionTreeModel> getPermissionTreeDetails(
            int userID, int primaryRole, int secondaryRoles, int operationBITS, int statusBITS) {

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        ArrayList<cPermissionTreeModel> permissionTreeModels = new ArrayList<>();

        String selectQueryPerms = "SELECT * " +
                " FROM " + cSQLDBHelper.TABLE_PERMISSION +
                " WHERE (((" + cSQLDBHelper.KEY_PERMISSION_GROUP_BITS + " & ?) = ?) " +
                " OR (((" + cSQLDBHelper.KEY_PERMISSION_OWNER_ID + " = ?) " +
                " AND ((" + cSQLDBHelper.KEY_PERMISSION_PERMS_BITS + " & ?) != 0)) " +
                " OR (((" + cSQLDBHelper.KEY_PERMISSION_GROUP_BITS + " & ?) != 0) " +
                " AND ((" + cSQLDBHelper.KEY_PERMISSION_PERMS_BITS + " & ?) != 0)) " +
                " AND ((" + cSQLDBHelper.KEY_PERMISSION_STATUS_BITS + " & ?) != 0))) " +
                " GROUP BY " + cSQLDBHelper.KEY_ORGANIZATION_FK_ID + ", " +
                cSQLDBHelper.KEY_PRIVILEGE_FK_ID + ", " +
                cSQLDBHelper.KEY_ENTITY_FK_ID + ", " +
                cSQLDBHelper.KEY_ENTITY_TYPE_FK_ID;

        Cursor cursorPerms = db.rawQuery(selectQueryPerms, new String[]{
                String.valueOf(primaryRole), String.valueOf(primaryRole),
                String.valueOf(userID), String.valueOf(operationBITS),
                String.valueOf(secondaryRoles), String.valueOf(operationBITS),
                String.valueOf(statusBITS)});

        String selectQueryOps, selectQueryOpsStatus;
        Cursor cursorOps = null, cursorOpsStatus = null;

        Gson gson = new Gson();

        try {
            if (cursorPerms.moveToFirst()) {
                do {
                    int organizationID = cursorPerms.getInt(cursorPerms.getColumnIndex(
                            cSQLDBHelper.KEY_ORGANIZATION_FK_ID));
                    int privilegeID = cursorPerms.getInt(cursorPerms.getColumnIndex(
                            cSQLDBHelper.KEY_PRIVILEGE_FK_ID));
                    int entityID = cursorPerms.getInt(cursorPerms.getColumnIndex(
                            cSQLDBHelper.KEY_ENTITY_FK_ID));
                    int typeID = cursorPerms.getInt(cursorPerms.getColumnIndex(
                            cSQLDBHelper.KEY_ENTITY_TYPE_FK_ID));

                    cEntityModel entityModel = new cEntityModel();
                    entityModel.setEntityID(entityID);
                    entityModel.setTypeID(typeID);

                    cPermissionTreeModel model = new cPermissionTreeModel(organizationID,
                            privilegeID, entityModel);

                    selectQueryOps = "SELECT " + cSQLDBHelper.KEY_OPERATION_FK_ID +
                            " FROM " + cSQLDBHelper.TABLE_PERMISSION +
                            " WHERE ((((" + cSQLDBHelper.KEY_PERMISSION_GROUP_BITS + " & ?) = ?) " +
                            " OR (((" + cSQLDBHelper.KEY_PERMISSION_OWNER_ID + " = ?) " +
                            " AND ((" + cSQLDBHelper.KEY_PERMISSION_PERMS_BITS + " & ?) != 0)) " +
                            " OR (((" + cSQLDBHelper.KEY_PERMISSION_GROUP_BITS + " & ?) != 0) " +
                            " AND ((" + cSQLDBHelper.KEY_PERMISSION_PERMS_BITS + " & ?) != 0)) " +
                            " AND ((" + cSQLDBHelper.KEY_PERMISSION_STATUS_BITS + " & ?) != 0))) " +
                            " AND ((" + cSQLDBHelper.KEY_ORGANIZATION_FK_ID + " = ? ) " +
                            " AND (" + cSQLDBHelper.KEY_PRIVILEGE_FK_ID + " = ? ) " +
                            " AND (" + cSQLDBHelper.KEY_ENTITY_FK_ID + " = ? ) " +
                            " AND (" + cSQLDBHelper.KEY_ENTITY_TYPE_FK_ID + " = ?)))";

                    // query operations of the privilege, entity and type
                    cursorOps = db.rawQuery(selectQueryOps, new String[]{
                            String.valueOf(primaryRole), String.valueOf(primaryRole),
                            String.valueOf(userID), String.valueOf(operationBITS),
                            String.valueOf(secondaryRoles), String.valueOf(operationBITS),
                            String.valueOf(statusBITS),
                            String.valueOf(model.getOrganizationID()),
                            String.valueOf(model.getPrivilegeID()),
                            String.valueOf(model.getEntityModel().getEntityID()),
                            String.valueOf(model.getEntityModel().getTypeID())});

                    Set<Integer> ops = new HashSet<>();
                    if (cursorOps.moveToFirst()) {
                        do {
                            ops.add(cursorOps.getInt(
                                    cursorOps.getColumnIndex(cSQLDBHelper.KEY_OPERATION_FK_ID)));
                        } while (cursorOps.moveToNext());
                    }

                    // permission details map
                    HashMap<cOperationModel, HashMap<cStatusModel, cPermissionModel>>
                            operationMap = new HashMap<>();

                    // prepare the operation models list
                    List<Integer> listOps = new ArrayList<Integer>(ops);
                    List<cOperationModel> operationModels = new ArrayList<>();
                    for (int i = 0; i < listOps.size(); i++) {
                        cOperationModel operationModel = new cOperationModel();
                        operationModel.setOperationID(listOps.get(i));
                        operationModels.add(i, operationModel);
                    }

                    //
                    if (!operationModels.isEmpty()) {

                        selectQueryOpsStatus = "SELECT * " +
                                " FROM " + cSQLDBHelper.TABLE_PERMISSION +
                                " WHERE ((((" + cSQLDBHelper.KEY_PERMISSION_GROUP_BITS + " & ?) = ?) " +
                                " OR (((" + cSQLDBHelper.KEY_PERMISSION_OWNER_ID + " = ?) " +
                                " AND ((" + cSQLDBHelper.KEY_PERMISSION_PERMS_BITS + " & ?) != 0)) " +
                                " OR (((" + cSQLDBHelper.KEY_PERMISSION_GROUP_BITS + " & ?) != 0) " +
                                " AND ((" + cSQLDBHelper.KEY_PERMISSION_PERMS_BITS + " & ?) != 0)) " +
                                " AND ((" + cSQLDBHelper.KEY_PERMISSION_STATUS_BITS + " & ?) != 0))) " +
                                " AND ((" + cSQLDBHelper.KEY_ORGANIZATION_FK_ID + " = ? )" +
                                " AND (" + cSQLDBHelper.KEY_PRIVILEGE_FK_ID + " = ? )" +
                                " AND (" + cSQLDBHelper.KEY_ENTITY_FK_ID + " = ? )" +
                                " AND (" + cSQLDBHelper.KEY_ENTITY_TYPE_FK_ID + " = ? )" +
                                " AND (" + cSQLDBHelper.KEY_OPERATION_FK_ID + " = ? )))";

                        for (int i = 0; i < operationModels.size(); i++) {
                            cursorOpsStatus = db.rawQuery(selectQueryOpsStatus, new String[]{
                                    String.valueOf(primaryRole), String.valueOf(primaryRole),
                                    String.valueOf(userID), String.valueOf(operationBITS),
                                    String.valueOf(secondaryRoles), String.valueOf(operationBITS),
                                    String.valueOf(statusBITS),
                                    String.valueOf(model.getOrganizationID()),
                                    String.valueOf(model.getPrivilegeID()),
                                    String.valueOf(model.getEntityModel().getEntityID()),
                                    String.valueOf(model.getEntityModel().getTypeID()),
                                    String.valueOf(operationModels.get(i).getOperationID())});

                            if (cursorOpsStatus.moveToFirst()) {
                                // add status and other details
                                HashMap<cStatusModel, cPermissionModel>
                                        statusMap = new HashMap<>();
                                do {
                                    // permission details
                                    cPermissionModel permissionModel = new cPermissionModel();

                                    cPrivilegeModel privilegeModel = new cPrivilegeModel();
                                    cOperationModel operationModel = new cOperationModel();
                                    cStatusModel statusModel = new cStatusModel();

                                    permissionModel.setOrganizationID(cursorOpsStatus.getInt(
                                            cursorOpsStatus.getColumnIndex(cSQLDBHelper.KEY_ORGANIZATION_FK_ID)));
                                    privilegeModel.setPrivilegeID(cursorOpsStatus.getInt(
                                            cursorOpsStatus.getColumnIndex(cSQLDBHelper.KEY_PRIVILEGE_FK_ID)));
                                    operationModel.setOperationID(cursorOpsStatus.getInt(
                                            cursorOpsStatus.getColumnIndex(cSQLDBHelper.KEY_OPERATION_FK_ID)));
                                    statusModel.setStatusID(cursorOpsStatus.getInt(
                                            cursorOpsStatus.getColumnIndex(cSQLDBHelper.KEY_STATUS_FK_ID)));

                                    //Log.d(TAG, "Status Model = "+gson.toJson(statusModel));

                                    // set a composite key
                                    permissionModel.setPrivilegeModel(privilegeModel);
                                    permissionModel.setEntityModel(entityModel);
                                    permissionModel.setOperationModel(operationModel);
                                    permissionModel.setStatusModel(statusModel);

                                    // set permission details other than a composite key
                                    permissionModel.setOwnerID(cursorOpsStatus.getInt(
                                            cursorOpsStatus.getColumnIndex(cSQLDBHelper.KEY_PERMISSION_OWNER_ID)));
                                    permissionModel.setOrgID(cursorOpsStatus.getInt(
                                            cursorOpsStatus.getColumnIndex(cSQLDBHelper.KEY_PERMISSION_ORG_ID)));

                                    permissionModel.setGroupBITS(cursorOpsStatus.getInt(
                                            cursorOpsStatus.getColumnIndex(cSQLDBHelper.KEY_PERMISSION_GROUP_BITS)));
                                    permissionModel.setPermsBITS(cursorOpsStatus.getInt(
                                            cursorOpsStatus.getColumnIndex(cSQLDBHelper.KEY_PERMISSION_PERMS_BITS)));
                                    permissionModel.setStatusBITS(cursorOpsStatus.getInt(
                                            cursorOpsStatus.getColumnIndex(cSQLDBHelper.KEY_PERMISSION_STATUS_BITS)));

                                    permissionModel.setCreatedDate(sdf.parse(cursorOpsStatus.getString(cursorOpsStatus.getColumnIndex(
                                            cSQLDBHelper.KEY_PERMISSION_CREATED_DATE))));
                                    permissionModel.setModifiedDate(sdf.parse(cursorOpsStatus.getString(cursorOpsStatus.getColumnIndex(
                                            cSQLDBHelper.KEY_PERMISSION_MODIFIED_DATE))));
                                    permissionModel.setSyncedDate(sdf.parse(cursorOpsStatus.getString(cursorOpsStatus.getColumnIndex(
                                            cSQLDBHelper.KEY_PERMISSION_SYNCED_DATE))));

                                    //Log.d(TAG, "Permission Model = "+gson.toJson(permissionModel));

                                    statusMap.put(statusModel, permissionModel);


                                } while (cursorOpsStatus.moveToNext());

                                // add all the details
                                operationMap.put(operationModels.get(i), statusMap);
                            }
                        }
                    }

                    // update operation details
                    model.setPermModelDetails(operationMap);

                    // add the tree model
                    permissionTreeModels.add(model);

                } while (cursorPerms.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to execute: 'getPermissionTreeDetails()'");
        } finally {
            if (cursorPerms != null && !cursorPerms.isClosed()) {
                cursorPerms.close();
                //cursorOps.close();
            }
        }

        // close the database connection
        db.close();

        return permissionTreeModels;
    }


    public ArrayList<cOperationModel> getEntityOperations(int privilegeID, int entityID, int typeID) {

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        ArrayList<cOperationModel> operationModels = new ArrayList<>();

        String selectQueryPerms = "SELECT " + cSQLDBHelper.KEY_PRIVILEGE_FK_ID + ", " +
                cSQLDBHelper.KEY_ENTITY_FK_ID + ", " + cSQLDBHelper.KEY_ENTITY_TYPE_FK_ID + ", " +
                cSQLDBHelper.KEY_OPERATION_FK_ID +
                " FROM " + cSQLDBHelper.TABLE_PERMISSION +
                " WHERE  " + cSQLDBHelper.KEY_PRIVILEGE_FK_ID + " = ? AND " +
                cSQLDBHelper.KEY_ENTITY_FK_ID + " = ? AND " +
                cSQLDBHelper.KEY_ENTITY_TYPE_FK_ID + " = ?";

        Cursor cursorPerms = db.rawQuery(selectQueryPerms, new String[]{
                String.valueOf(privilegeID),
                String.valueOf(entityID),
                String.valueOf(typeID)});
        try {
            if (cursorPerms.moveToFirst()) {
                do {
                    // create objects to hold data
                    /*
                    cPermissionModel permission = new cPermissionModel();
                    cPrivilegeModel privilegeModel = new cPrivilegeModel();
                    cEntityModel entityModel = new cEntityModel();
                    */
                    cOperationModel operationModel = new cOperationModel();

                    // get all privileges, entities and entity types and operations
                    /*
                    privilegeModel.setPrivilegeID(cursorPerms.getInt(cursorPerms.getColumnIndex(
                            cSQLDBHelper.KEY_PRIVILEGE_FK_ID)));
                    entityModel.setEntityID(cursorPerms.getInt(cursorPerms.getColumnIndex(
                            cSQLDBHelper.KEY_ENTITY_FK_ID)));
                    entityModel.setTypeID(cursorPerms.getInt(cursorPerms.getColumnIndex(
                            cSQLDBHelper.KEY_ENTITY_TYPE_FK_ID)));
                    */
                    operationModel.setOperationID(cursorPerms.getInt(cursorPerms.getColumnIndex(
                            cSQLDBHelper.KEY_OPERATION_FK_ID)));

                    // set the objects to the permission object
                    /*
                    permission.setPrivilegeModel(privilegeModel);
                    permission.setEntityModel(entityModel);
                    permission.setOperationModel(operationModel);
                    */

                    // add data to the list
                    operationModels.add(operationModel);

                } while (cursorPerms.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to execute: 'getEntityOperations()'");
        } finally {
            if (cursorPerms != null && !cursorPerms.isClosed()) {
                cursorPerms.close();
            }
        }
        return operationModels;
    }


    public List<cEntityBITS> getEntitiesBIT(ArrayList<cPrivilegeModel> privilegeModels) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        List<cEntityBITS> entityBITS = new ArrayList<>();

        String[] privileges = new String[privilegeModels.size()];
        for (int i = 0; i < privilegeModels.size(); i++) {
            privileges[i] = String.valueOf(privilegeModels.get(i).getPrivilegeID());
        }


        // construct a selection query
        String selectQuery = "SELECT " + cSQLDBHelper.KEY_ENTITY_TYPE_FK_ID + ", " +
                " SUM(DISTINCT " + cSQLDBHelper.KEY_ENTITY_FK_ID + ") AS entityBITS " +
                " FROM " + cSQLDBHelper.TABLE_PERMISSION +
                " WHERE " + cSQLDBHelper.KEY_PRIVILEGE_FK_ID +
                " IN (" + TextUtils.join(",",
                Collections.nCopies(privileges.length, "?")) + ")" +
                " GROUP BY " + cSQLDBHelper.KEY_ENTITY_TYPE_FK_ID;

        Cursor cursor = db.rawQuery(selectQuery, privileges);

        try {
            if (cursor.moveToFirst()) {
                do {
                    cEntityBITS entityBIT = new cEntityBITS();

                    entityBIT.setTypeID(cursor.getInt(
                            cursor.getColumnIndex(cSQLDBHelper.KEY_ENTITY_TYPE_FK_ID)));
                    entityBIT.setEntityBITS(cursor.getInt(cursor.getColumnIndex("entityBITS")));

                    entityBITS.add(entityBIT);

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to get permission groups from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        // close the database connection
        db.close();

        return entityBITS;
    }

    public List<cOperationBITS> getOperationsBIT(ArrayList<cPrivilegeModel> privilegeModels) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        List<cOperationBITS> operationBITS = new ArrayList<>();

        String[] privileges = new String[privilegeModels.size()];
        for (int i = 0; i < privilegeModels.size(); i++) {
            privileges[i] = String.valueOf(privilegeModels.get(i).getPrivilegeID());
        }

        // construct a selection query
        String selectQuery = "SELECT " + cSQLDBHelper.KEY_ENTITY_FK_ID + ", " +
                cSQLDBHelper.KEY_ENTITY_TYPE_FK_ID + ", " +
                " SUM(DISTINCT " + cSQLDBHelper.KEY_OPERATION_FK_ID + ") AS operationBITS " +
                " FROM " + cSQLDBHelper.TABLE_PERMISSION +
                " WHERE " + cSQLDBHelper.KEY_PRIVILEGE_FK_ID +
                " IN (" + TextUtils.join(",",
                Collections.nCopies(privileges.length, "?")) + ")" +
                " GROUP BY " + cSQLDBHelper.KEY_ENTITY_FK_ID + ", " + cSQLDBHelper.KEY_ENTITY_TYPE_FK_ID;

        Cursor cursor = db.rawQuery(selectQuery, privileges);

        try {
            if (cursor.moveToFirst()) {
                do {
                    cOperationBITS operationBIT = new cOperationBITS();

                    operationBIT.setEntityID(cursor.getInt(
                            cursor.getColumnIndex(cSQLDBHelper.KEY_ENTITY_FK_ID)));
                    operationBIT.setTypeID(cursor.getInt(
                            cursor.getColumnIndex(cSQLDBHelper.KEY_ENTITY_TYPE_FK_ID)));
                    operationBIT.setOperationBITS(cursor.getInt(
                            cursor.getColumnIndex("operationBITS")));

                    operationBITS.add(operationBIT);

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to get permission groups from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        // close the database connection
        db.close();

        return operationBITS;
    }

    public List<cStatusBITS> getStatusesBIT(ArrayList<cPrivilegeModel> privilegeModels) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        List<cStatusBITS> statusBITS = new ArrayList<>();

        String[] privileges = new String[privilegeModels.size()];
        for (int i = 0; i < privilegeModels.size(); i++) {
            privileges[i] = String.valueOf(privilegeModels.get(i).getPrivilegeID());
        }

        // construct a selection query
        String selectQuery = "SELECT " + cSQLDBHelper.KEY_ENTITY_FK_ID + ", " +
                cSQLDBHelper.KEY_ENTITY_TYPE_FK_ID + ", " + cSQLDBHelper.KEY_OPERATION_FK_ID + ", " +
                " SUM(DISTINCT " + cSQLDBHelper.KEY_STATUS_FK_ID + ") AS statusBITS " +
                " FROM " + cSQLDBHelper.TABLE_PERMISSION +
                " WHERE " + cSQLDBHelper.KEY_PRIVILEGE_FK_ID +
                " IN (" + TextUtils.join(",",
                Collections.nCopies(privileges.length, "?")) + ")" +
                " GROUP BY " + cSQLDBHelper.KEY_ENTITY_FK_ID + ", " +
                cSQLDBHelper.KEY_ENTITY_TYPE_FK_ID + ", " + cSQLDBHelper.KEY_OPERATION_FK_ID;

        Cursor cursor = db.rawQuery(selectQuery, privileges);

        try {
            if (cursor.moveToFirst()) {
                do {
                    cStatusBITS statusBIT = new cStatusBITS();

                    statusBIT.setEntityID(cursor.getInt(
                            cursor.getColumnIndex(cSQLDBHelper.KEY_ENTITY_FK_ID)));
                    statusBIT.setTypeID(cursor.getInt(
                            cursor.getColumnIndex(cSQLDBHelper.KEY_ENTITY_TYPE_FK_ID)));
                    statusBIT.setOperationID(cursor.getInt(
                            cursor.getColumnIndex(cSQLDBHelper.KEY_OPERATION_FK_ID)));
                    statusBIT.setStatusBITS(cursor.getInt(cursor.getColumnIndex("statusBITS")));

                    statusBITS.add(statusBIT);

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to get permission groups from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        // close the database connection
        db.close();

        return statusBITS;
    }
}
