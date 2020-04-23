package com.me.mseotsanyana.mande.DAL.ìmpl.session;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.me.mseotsanyana.mande.DAL.model.session.cOperationModel;
import com.me.mseotsanyana.mande.DAL.storage.database.cSQLDBHelper;
import com.me.mseotsanyana.mande.UTIL.cConstant;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mseotsanyana on 2017/08/24.
 */

public class cOperationRepositoryImpl {
    private static SimpleDateFormat sdf = cConstant.FORMAT_DATE;
    private static String TAG = cOperationRepositoryImpl.class.getSimpleName();

    // an object of the database helper
    private cSQLDBHelper dbHelper;

    public cOperationRepositoryImpl(Context context) {
        dbHelper = new cSQLDBHelper(context);
    }

    /* ############################################# CREATE ACTIONS ############################################# */

    public boolean addOperationFromExcel(cOperationModel operationModel) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_ID, operationModel.getOperationID());
        cv.put(cSQLDBHelper.KEY_NAME, operationModel.getName());
        cv.put(cSQLDBHelper.KEY_DESCRIPTION, operationModel.getDescription());

        // insert outcome record
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblOPERATION, null, cv) < 0) {
                return false;
            }
        } catch (Exception e) {
            Log.d(TAG, "Exception in reading: " + e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }

    public boolean addOperation(cOperationModel actionModel) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_ID, actionModel.getOperationID());
        cv.put(cSQLDBHelper.KEY_OWNER_ID, actionModel.getOwnerID());
        cv.put(cSQLDBHelper.KEY_ORG_ID, actionModel.getOrgID());
        cv.put(cSQLDBHelper.KEY_GROUP_BITS, actionModel.getGroupBITS());
        cv.put(cSQLDBHelper.KEY_PERMS_BITS, actionModel.getPermsBITS());
        cv.put(cSQLDBHelper.KEY_STATUS_BITS, actionModel.getStatusBITS());
        cv.put(cSQLDBHelper.KEY_NAME, actionModel.getName());
        cv.put(cSQLDBHelper.KEY_DESCRIPTION, actionModel.getDescription());

        // insert outcome record
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblOPERATION, null, cv) < 0) {
                return false;
            }
        } catch (Exception e) {
            Log.d(TAG, "Exception in reading: " + e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }

    /* ############################################# READ ACTIONS ############################################# */

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
                operation.setCreatedDate(Timestamp.valueOf(
                        cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_CREATED_DATE))));
                operation.setModifiedDate(Timestamp.valueOf(
                        cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_MODIFIED_DATE))));
                operation.setSyncedDate(Timestamp.valueOf(
                        cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_SYNCED_DATE))));
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

    public List<cOperationModel> getOperationList() {

        List<cOperationModel> operationModelList = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + cSQLDBHelper.TABLE_tblOPERATION, null);

        try {
            if (cursor.moveToFirst()) {
                do {
                    cOperationModel type = new cOperationModel();

                    type.setOperationID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ID)));
                    type.setOwnerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OWNER_ID)));
                    type.setGroupBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_GROUP_BITS)));
                    type.setPermsBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PERMS_BITS)));
                    type.setStatusBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_STATUS_BITS)));
                    type.setName(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_NAME)));
                    type.setDescription(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_DESCRIPTION)));
                    //type.setCreateDate(formatter.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_DATE))));

                    operationModelList.add(type);

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

        return operationModelList;
    }

    /* ############################################# UPDATE ACTIONS ############################################# */


    /* ############################################# DELETE ACTIONS ############################################# */

    public boolean deleteOperations() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblOPERATION, null, null);

        // close the database connection
        db.close();

        return result > -1;
    }

    /* ############################################# SYNCED ACTIONS ############################################# */

}
