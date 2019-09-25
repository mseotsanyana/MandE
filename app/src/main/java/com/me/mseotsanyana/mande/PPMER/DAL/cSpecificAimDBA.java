package com.me.mseotsanyana.mande.PPMER.DAL;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class cSpecificAimDBA {

    private static final SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

    // an object of the database helper
    private cSQLDBHelper dbHelper;
    private static final String TAG = "dbHelper";

    public cSpecificAimDBA(Context context) {
        dbHelper = new cSQLDBHelper(context);
    }

    public boolean addSpecificAim(cSpecificAimModel specificAimModel) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_PROJECT_ID, specificAimModel.getProjectID());
        cv.put(cSQLDBHelper.KEY_OVERALLAIM_FK_ID, specificAimModel.getOverallAimID());
        cv.put(cSQLDBHelper.KEY_SPECIFICAIM_OWNER_ID, specificAimModel.getOwnerID());
        cv.put(cSQLDBHelper.KEY_SPECIFICAIM_NAME, specificAimModel.getSpecificAimName());
        cv.put(cSQLDBHelper.KEY_SPECIFICAIM_DESCRIPTION, specificAimModel.getSpecificAimDescription());
        cv.put(cSQLDBHelper.KEY_SPECIFICAIM_DATE, formatter.format(specificAimModel.getCreateDate()));

        try {
            if (db.insert(cSQLDBHelper.TABLE_SPECIFICAIM, null, cv) < 0) {
                return false;
            }
        } catch (Exception ex) {
            Log.d("Exception in importing ", ex.getMessage().toString());
        }

        // close the database connection
        db.close();

        return true;
    }

    public boolean addSpecificAimFromExcel(cSpecificAimModel specificAimModel) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_PROJECT_ID, specificAimModel.getProjectID());
        cv.put(cSQLDBHelper.KEY_OVERALLAIM_FK_ID, specificAimModel.getOverallAimID());
        cv.put(cSQLDBHelper.KEY_SPECIFICAIM_OWNER_ID, specificAimModel.getOwnerID());
        cv.put(cSQLDBHelper.KEY_SPECIFICAIM_NAME, specificAimModel.getSpecificAimName());
        cv.put(cSQLDBHelper.KEY_SPECIFICAIM_DESCRIPTION, specificAimModel.getSpecificAimDescription());
        cv.put(cSQLDBHelper.KEY_SPECIFICAIM_DATE, formatter.format(specificAimModel.getCreateDate()));

        try {
            if (db.insert(cSQLDBHelper.TABLE_SPECIFICAIM, null, cv) < 0) {
                return false;
            }
        } catch (Exception ex) {
            Log.d("Exception in importing ", ex.getMessage().toString());
        }

        // close the database connection
        db.close();

        return true;
    }

    public boolean deleteAllSpecificAims() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_SPECIFICAIM, null, null);

        // close the database connection
        db.close();

        return result > -1;
    }

    public List<cSpecificAimModel> getSpecificAimList() {
        List<cSpecificAimModel> specificAimModels = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+ cSQLDBHelper.TABLE_SPECIFICAIM, null);

        try {
            if (cursor.moveToFirst()) {
                do {
                    cSpecificAimModel specificAimModel = new cSpecificAimModel();
                    // populate specific aim model object
                    specificAimModel.setProjectID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_SPECIFICAIM_ID)));
                    specificAimModel.setOverallAimID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OVERALLAIM_FK_ID)));
                    specificAimModel.setOwnerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_SPECIFICAIM_OWNER_ID)));
                    specificAimModel.setSpecificAimName(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_SPECIFICAIM_NAME)));
                    specificAimModel.setSpecificAimDescription(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_SPECIFICAIM_DESCRIPTION)));
                    specificAimModel.setCreateDate(formatter.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_SPECIFICAIM_DATE))));
                    // add model specific aim into the action_list
                    specificAimModels.add(specificAimModel);

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to get values from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        // close the database connection
        db.close();

        return specificAimModels;
    }
}