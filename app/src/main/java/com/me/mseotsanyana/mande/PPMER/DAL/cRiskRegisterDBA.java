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

public class cRiskRegisterDBA {
    // an object of the database helper
    private cSQLDBHelper dbHelper;

    private static final SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
    private static final String TAG = "dbHelper";

    public cRiskRegisterDBA(Context context) {
        dbHelper = new cSQLDBHelper(context);
    }


    public boolean deleteAllRiskRegister() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_RISKREGISTER, null, null);

        // close the database connection
        db.close();

        return result > -1;
    }

    public boolean addRiskRegister(cOutputModel outputModel) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_OUTPUT_ID, outputModel.getOutputID());
        cv.put(cSQLDBHelper.KEY_OBJECTIVE_FK_ID, outputModel.getObjectiveID());
        cv.put(cSQLDBHelper.KEY_OUTPUT_OWNER_ID, outputModel.getOwnerID());
        cv.put(cSQLDBHelper.KEY_OUTPUT_NAME, outputModel.getOutputName());
        cv.put(cSQLDBHelper.KEY_OUTPUT_DESCRIPTION, outputModel.getOutputDescription());
        cv.put(cSQLDBHelper.KEY_OUTPUT_DATE, formatter.format(outputModel.getCreateDate()));

        // insert outcome record
        try {
            if (db.insert(cSQLDBHelper.TABLE_OUTPUT, null, cv) < 0) {
                return false;
            }
        } catch (Exception ex) {
            Log.d("Exception in importing ", ex.getMessage().toString());
        }

        // close the database connection
        db.close();

        return true;
    }

    public cOutputModel getOutputByID(int outputID) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        // construct a selection query
        String selectQuery = "SELECT * FROM " +
                cSQLDBHelper.TABLE_OUTPUT + " WHERE " +
                cSQLDBHelper.KEY_OUTPUT_ID + "= ?";

        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(outputID)});

        cOutputModel output = new cOutputModel();

        try {
            if (cursor.moveToFirst()) {
                do {
                    output.setOutputID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OUTPUT_ID)));
                    output.setObjectiveID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OBJECTIVE_FK_ID)));
                    output.setOwnerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OUTPUT_OWNER_ID)));
                    output.setOutputName(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_OUTPUT_NAME)));
                    output.setOutputDescription(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_OUTPUT_DESCRIPTION)));
                    output.setCreateDate(formatter.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_OUTPUT_DATE))));

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

        return output;
    }


    public List<cOutputModel> getOutputList() {

        List<cOutputModel> outputModelList = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+ cSQLDBHelper.TABLE_OUTPUT, null);

        try {
            if (cursor.moveToFirst()) {
                do {
                    cOutputModel output = new cOutputModel();

                    output.setOutputID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OUTPUT_ID)));
                    output.setObjectiveID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OBJECTIVE_FK_ID)));
                    output.setOwnerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OUTPUT_OWNER_ID)));
                    output.setOutputName(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_OUTPUT_NAME)));
                    output.setOutputDescription(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_OUTPUT_DESCRIPTION)));
                    output.setCreateDate(formatter.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_OUTPUT_DATE))));

                    outputModelList.add(output);

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

        return outputModelList;
    }
}
