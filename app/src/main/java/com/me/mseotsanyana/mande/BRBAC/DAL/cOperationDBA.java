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
 * Created by mseotsanyana on 2017/08/24.
 */

public class cOperationDBA {
    // an object of the database helper
    private cSQLDBHelper dbHelper;

    private static final SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
    private static final String TAG = "dbHelper";

    public cOperationDBA(Context context) {
        dbHelper = new cSQLDBHelper(context);
    }

    public boolean deleteAllOperations() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_OPERATION, null, null);

        // close the database connection
        db.close();

        return result > -1;
    }

    public boolean addOperationFromExcel(cOperationModel operationModel) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_OPERATION_ID, operationModel.getOperationID());
        cv.put(cSQLDBHelper.KEY_OPERATION_NAME, operationModel.getName());
        cv.put(cSQLDBHelper.KEY_OPERATION_DESCRIPTION, operationModel.getDescription());
        //cv.put(cSQLDBHelper.KEY_OPERATION_DATE, formatter.format(operationModel.getCreateDate()));

        // insert outcome record
        try {
            if (db.insert(cSQLDBHelper.TABLE_OPERATION, null, cv) < 0) {
                return false;
            }
        } catch (Exception ex) {
            Log.d("Exception in importing ", ex.getMessage().toString());
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
        cv.put(cSQLDBHelper.KEY_OPERATION_ID, actionModel.getOperationID());
        cv.put(cSQLDBHelper.KEY_OPERATION_OWNER_ID, actionModel.getOwnerID());
        cv.put(cSQLDBHelper.KEY_OPERATION_GROUP_BITS, actionModel.getGroupBITS());
        cv.put(cSQLDBHelper.KEY_OPERATION_PERMS_BITS, actionModel.getPermsBITS());
        cv.put(cSQLDBHelper.KEY_OPERATION_STATUS_BITS, actionModel.getStatusBITS());
        cv.put(cSQLDBHelper.KEY_OPERATION_NAME, actionModel.getName());
        cv.put(cSQLDBHelper.KEY_OPERATION_DESCRIPTION, actionModel.getDescription());
        //cv.put(cSQLDBHelper.KEY_OPERATION_DATE, formatter.format(actionModel.getCreateDate()));

        // insert outcome record
        try {
            if (db.insert(cSQLDBHelper.TABLE_OPERATION, null, cv) < 0) {
                return false;
            }
        } catch (Exception ex) {
            Log.d("Exception in importing ", ex.getMessage().toString());
        }

        // close the database connection
        db.close();

        return true;
    }

    public cOperationModel getOperationByID(int operationID) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        // construct a selection query
        String selectQuery = "SELECT * FROM " +
                cSQLDBHelper.TABLE_OPERATION + " WHERE " +
                cSQLDBHelper.KEY_OPERATION_ID + "= ?";

        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(operationID)});

        cOperationModel operation = new cOperationModel();

        try {
            if (cursor.moveToFirst()) {
                do {
                    operation.setOperationID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OPERATION_ID)));
                    operation.setOwnerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OPERATION_OWNER_ID)));
                    operation.setGroupBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OPERATION_GROUP_BITS)));
                    operation.setPermsBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OPERATION_PERMS_BITS)));
                    operation.setStatusBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OPERATION_STATUS_BITS)));
                    operation.setName(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_OPERATION_NAME)));
                    operation.setDescription(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_OPERATION_DESCRIPTION)));
                    //operation.setCreateDate(formatter.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_OPERATION_DATE))));

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

        return operation;
    }


    public List<cOperationModel> getOperationList() {

        List<cOperationModel> operationModelList = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+ cSQLDBHelper.TABLE_OPERATION, null);

        try {
            if (cursor.moveToFirst()) {
                do {
                    cOperationModel type = new cOperationModel();

                    type.setOperationID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OPERATION_ID)));
                    type.setOwnerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OPERATION_OWNER_ID)));
                    type.setGroupBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OPERATION_GROUP_BITS)));
                    type.setPermsBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OPERATION_PERMS_BITS)));
                    type.setStatusBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OPERATION_STATUS_BITS)));
                    type.setName(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_OPERATION_NAME)));
                    type.setDescription(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_OPERATION_DESCRIPTION)));
                    //type.setCreateDate(formatter.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_OPERATION_DATE))));

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
}
