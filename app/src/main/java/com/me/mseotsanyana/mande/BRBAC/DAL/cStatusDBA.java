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

public class cStatusDBA {
    // an object of the database helper
    private cSQLDBHelper dbHelper;

    private static final SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
    private static final String TAG = "dbHelper";

    public cStatusDBA(Context context) {
        dbHelper = new cSQLDBHelper(context);
    }

    public boolean deleteAllStatuses() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_STATUS, null, null);

        // close the database connection
        db.close();

        return result > -1;
    }

    public boolean addStatusFromExcel(cStatusModel statusModel) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_STATUS_ID, statusModel.getStatusID());
        cv.put(cSQLDBHelper.KEY_STATUS_NAME, statusModel.getName());
        cv.put(cSQLDBHelper.KEY_STATUS_DESCRIPTION, statusModel.getDescription());
        //cv.put(cSQLDBHelper.KEY_STATUS_DATE, formatter.format(statusModel.getCreateDate()));

        // insert outcome record
        try {
            if (db.insert(cSQLDBHelper.TABLE_STATUS, null, cv) < 0) {
                return false;
            }
        } catch (Exception ex) {
            Log.d("Exception in importing ", ex.getMessage().toString());
        }

        // close the database connection
        db.close();

        return true;
    }

    public boolean addStatus(cStatusModel statusModel) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_STATUS_ID, statusModel.getStatusID());
        cv.put(cSQLDBHelper.KEY_STATUS_OWNER_ID, statusModel.getOwnerID());
        cv.put(cSQLDBHelper.KEY_STATUS_GROUP_BITS, statusModel.getStatusBITS());
        cv.put(cSQLDBHelper.KEY_STATUS_PERMS_BITS, statusModel.getPermsBITS());
        cv.put(cSQLDBHelper.KEY_STATUS_STATUS_BITS, statusModel.getStatusBITS());
        cv.put(cSQLDBHelper.KEY_STATUS_NAME, statusModel.getName());
        cv.put(cSQLDBHelper.KEY_STATUS_DESCRIPTION, statusModel.getDescription());
        //cv.put(cSQLDBHelper.KEY_STATUS_DATE, formatter.format(statusModel.getCreateDate()));

        // insert outcome record
        try {
            if (db.insert(cSQLDBHelper.TABLE_STATUS, null, cv) < 0) {
                return false;
            }
        } catch (Exception ex) {
            Log.d("Exception in importing ", ex.getMessage().toString());
        }

        // close the database connection
        db.close();

        return true;
    }

    public cStatusModel getStatusByID(int statusID) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        // construct a selection query
        String selectQuery = "SELECT * FROM " +
                cSQLDBHelper.TABLE_STATUS+ " WHERE " +
                cSQLDBHelper.KEY_STATUS_ID + "= ?";

        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(statusID)});

        cStatusModel status = new cStatusModel();

        try {
            if (cursor.moveToFirst()) {
                do {
                    status.setStatusID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_STATUS_ID)));
                    status.setOwnerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_STATUS_OWNER_ID)));
                    status.setStatusBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_STATUS_GROUP_BITS)));
                    status.setPermsBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_STATUS_PERMS_BITS)));
                    status.setStatusBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_STATUS_STATUS_BITS)));
                    status.setName(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_STATUS_NAME)));
                    status.setDescription(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_STATUS_DESCRIPTION)));
                    //status.setCreateDate(formatter.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_STATUS_DATE))));

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

        return status;
    }


    public List<cStatusModel> getStatusList() {

        List<cStatusModel> statusModelList = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+ cSQLDBHelper.TABLE_STATUS, null);

        try {
            if (cursor.moveToFirst()) {
                do {
                    cStatusModel status = new cStatusModel();

                    status.setStatusID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_STATUS_ID)));
                    status.setOwnerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_STATUS_OWNER_ID)));
                    status.setGroupBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_STATUS_GROUP_BITS)));
                    status.setPermsBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_STATUS_PERMS_BITS)));
                    status.setStatusBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_STATUS_STATUS_BITS)));
                    status.setName(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_STATUS_NAME)));
                    status.setDescription(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_STATUS_DESCRIPTION)));
                    //status.setCreateDate(formatter.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_STATUS_DATE))));

                    statusModelList.add(status);

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

        return statusModelList;
    }
}
