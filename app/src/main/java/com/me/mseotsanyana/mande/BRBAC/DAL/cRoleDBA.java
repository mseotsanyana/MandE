package com.me.mseotsanyana.mande.BRBAC.DAL;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.me.mseotsanyana.mande.PPMER.DAL.cSQLDBHelper;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by mseotsanyana on 2017/08/24.
 */

public class cRoleDBA {
    private static final String TAG = "cRoleDBA";
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);

    // an object of the database helper
    private cSQLDBHelper dbHelper;

    public cRoleDBA(Context context) {
        dbHelper = new cSQLDBHelper(context);
    }

    public boolean deleteAllRoles() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_ROLE, null, null);

        // close the database connection
        db.close();

        return result > -1;
    }

    public boolean addRoleFromExcel(cRoleModel roleModel) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_ROLE_ID, roleModel.getRoleID());
        cv.put(cSQLDBHelper.KEY_ORGANIZATION_FK_ID, roleModel.getOrganizationID());
        cv.put(cSQLDBHelper.KEY_ROLE_NAME, roleModel.getName());
        cv.put(cSQLDBHelper.KEY_ROLE_DESCRIPTION, roleModel.getDescription());
        //cv.put(cSQLDBHelper.KEY_ROLE_DATE, formatter.format(roleModel.getCreateDate()));

        // insert outcome record
        try {
            if (db.insert(cSQLDBHelper.TABLE_ROLE, null, cv) < 0) {
                return false;
            }
        } catch (Exception ex) {
            Log.d("Exception in importing ", ex.getMessage().toString());
        }

        // close the database connection
        db.close();

        return true;
    }

    public boolean addRole(cRoleModel groupModel) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_ROLE_ID, groupModel.getRoleID());
        cv.put(cSQLDBHelper.KEY_ORGANIZATION_FK_ID, groupModel.getOrganizationID());
        cv.put(cSQLDBHelper.KEY_ROLE_OWNER_ID, groupModel.getOwnerID());
        cv.put(cSQLDBHelper.KEY_ROLE_GROUP_BITS, groupModel.getGroupBITS());
        cv.put(cSQLDBHelper.KEY_ROLE_PERMS_BITS, groupModel.getPermsBITS());
        cv.put(cSQLDBHelper.KEY_ROLE_STATUS_BITS, groupModel.getStatusBITS());
        cv.put(cSQLDBHelper.KEY_ROLE_NAME, groupModel.getName());
        cv.put(cSQLDBHelper.KEY_ROLE_DESCRIPTION, groupModel.getDescription());
        //cv.put(cSQLDBHelper.KEY_ROLE_DATE, formatter.format(groupModel.getCreateDate()));

        // insert outcome record
        try {
            if (db.insert(cSQLDBHelper.TABLE_ROLE, null, cv) < 0) {
                return false;
            }
        } catch (Exception ex) {
            Log.d("Exception in importing ", ex.getMessage().toString());
        }

        // close the database connection
        db.close();

        return true;
    }

    public cRoleModel getRoleByID(int roleID) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        // construct a selection query
        String selectQuery = "SELECT * FROM " +
                cSQLDBHelper.TABLE_ROLE + " WHERE " +
                cSQLDBHelper.KEY_ROLE_ID + "= ?";

        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(roleID)});

        cRoleModel role = new cRoleModel();

        try {
            if (cursor.moveToFirst()) {
                do {
                    role.setRoleID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ROLE_ID)));
                    role.setOrganizationID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ORGANIZATION_FK_ID)));
                    role.setOwnerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ROLE_OWNER_ID)));
                    role.setGroupBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ROLE_GROUP_BITS)));
                    role.setPermsBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ROLE_PERMS_BITS)));
                    role.setStatusBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ROLE_STATUS_BITS)));
                    role.setName(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_ROLE_NAME)));
                    role.setDescription(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_ROLE_DESCRIPTION)));
                    role.setCreatedDate(sdf.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_ROLE_CREATED_DATE))));
                    role.setModifiedDate(sdf.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_ROLE_MODIFIED_DATE))));
                    role.setSyncedDate(sdf.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_ROLE_SYNCED_DATE))));

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


    public List<cRoleModel> getRoleList(
            int userID, int orgID, int primaryRole,
            int secondaryRoles, int operationBITS, int statusBITS) {

        List<cRoleModel> groupModelList = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String selectQuery = "SELECT * FROM "+ cSQLDBHelper.TABLE_ROLE +
                " WHERE ((("+cSQLDBHelper.KEY_ROLE_GROUP_BITS +" & ?) != 0) " +
                " OR (("+cSQLDBHelper.KEY_ROLE_OWNER_ID+" = ?) " +
                " AND (("+cSQLDBHelper.KEY_ROLE_PERMS_BITS+" & ?) != 0)) " +
                " OR ((("+cSQLDBHelper.KEY_ROLE_GROUP_BITS +" & ?) != 0) " +
                " AND (("+cSQLDBHelper.KEY_ROLE_PERMS_BITS+" & ?) != 0)))";

        Cursor cursor = db.rawQuery(selectQuery, new String[]{
                String.valueOf(primaryRole),
                String.valueOf(userID),String.valueOf(operationBITS),
                String.valueOf(secondaryRoles),String.valueOf(operationBITS)});

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
                    role.setCreatedDate(sdf.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_ROLE_CREATED_DATE))));
                    role.setModifiedDate(sdf.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_ROLE_MODIFIED_DATE))));
                    role.setSyncedDate(sdf.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_ROLE_SYNCED_DATE))));

                    groupModelList.add(role);

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

        return groupModelList;
    }


    public boolean updateRole(cRoleModel roleModel){
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        Date date= new Date();
        Timestamp ts = new Timestamp(date.getTime());

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_ROLE_ID, roleModel.getRoleID());
        cv.put(cSQLDBHelper.KEY_ORGANIZATION_FK_ID, roleModel.getOrganizationID());
        cv.put(cSQLDBHelper.KEY_ROLE_OWNER_ID, roleModel.getOwnerID());
        cv.put(cSQLDBHelper.KEY_ROLE_GROUP_BITS, roleModel.getGroupBITS());
        cv.put(cSQLDBHelper.KEY_ROLE_PERMS_BITS, roleModel.getPermsBITS());
        cv.put(cSQLDBHelper.KEY_ROLE_STATUS_BITS, roleModel.getStatusBITS());
        cv.put(cSQLDBHelper.KEY_ROLE_NAME, roleModel.getName());
        cv.put(cSQLDBHelper.KEY_ROLE_DESCRIPTION, roleModel.getDescription());
        cv.put(cSQLDBHelper.KEY_ROLE_MODIFIED_DATE, sdf.format(ts));

        // update a specific record
        long result = db.update(cSQLDBHelper.TABLE_ROLE, cv,
                cSQLDBHelper.KEY_ROLE_ID + "= ? AND "+
                        cSQLDBHelper.KEY_ORGANIZATION_FK_ID +" = ?",
                new String[]{String.valueOf(roleModel.getRoleID()),
                        String.valueOf(roleModel.getOrganizationID())});

        // close the database connection
        db.close();

        return result > -1;
    }
}
