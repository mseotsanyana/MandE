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

public class cQuantitativeTargetDBA {
    // an object of the database helper
    private cSQLDBHelper dbHelper;

    private static final SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
    private static final String TAG = "dbHelper";

    public cQuantitativeTargetDBA(Context context) {
        dbHelper = new cSQLDBHelper(context);
    }


    public boolean deleteAllQuantitativeTargets() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_QUANTITATIVETARGET, null, null);

        // close the database connection
        db.close();

        return result > -1;
    }

    public boolean addQuantitativeTarget(cQuantitativeTargetModel indicatorModel) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_QUANTITATIVE_ID, indicatorModel.getQuantitativeID());
        cv.put(cSQLDBHelper.KEY_INDICATOR_FK_ID, indicatorModel.getIndicatorID());
        cv.put(cSQLDBHelper.KEY_QUANTITATIVE_OWNER_ID, indicatorModel.getOwnerID());
        cv.put(cSQLDBHelper.KEY_QUANTITATIVE_TARGET, indicatorModel.getQuantitativeTarget());
        cv.put(cSQLDBHelper.KEY_QUANTITATIVE_TARGET_DATE, formatter.format(indicatorModel.getCreateDate()));

        // insert outcome record
        try {
            if (db.insert(cSQLDBHelper.TABLE_QUANTITATIVETARGET, null, cv) < 0) {
                return false;
            }
        } catch (Exception ex) {
            Log.d("Exception in importing ", ex.getMessage().toString());
        }

        // close the database connection
        db.close();

        return true;
    }

    public cQuantitativeTargetModel getQuantitativeTargetByID(int indicatorID) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        // construct a selection query
        String selectQuery = "SELECT * FROM " +
                cSQLDBHelper.TABLE_QUANTITATIVETARGET + " WHERE " +
                cSQLDBHelper.KEY_QUANTITATIVE_ID + "= ?";

        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(indicatorID)});

        cQuantitativeTargetModel indicator = new cQuantitativeTargetModel();

        try {
            if (cursor.moveToFirst()) {
                do {
                    indicator.setQuantitativeID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_QUANTITATIVE_ID)));
                    indicator.setIndicatorID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_INDICATOR_FK_ID)));
                    indicator.setOwnerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_QUANTITATIVE_OWNER_ID)));
                    indicator.setQuantitativeTarget(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_QUANTITATIVE_TARGET)));
                    indicator.setCreateDate(formatter.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_QUANTITATIVE_TARGET_DATE))));
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

        return indicator;
    }


    public List<cQuantitativeTargetModel> getQuantitativeTargetList() {

        List<cQuantitativeTargetModel> indicatorModelList = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+ cSQLDBHelper.TABLE_QUANTITATIVETARGET, null);

        try {
            if (cursor.moveToFirst()) {
                do {
                    cQuantitativeTargetModel indicator = new cQuantitativeTargetModel();

                    indicator.setQuantitativeID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_QUANTITATIVE_ID)));
                    indicator.setIndicatorID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_INDICATOR_ID)));
                    indicator.setOwnerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_QUANTITATIVE_OWNER_ID)));
                    indicator.setQuantitativeTarget(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_QUANTITATIVE_TARGET)));
                    indicator.setCreateDate(formatter.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_QUANTITATIVE_TARGET_DATE))));

                    indicatorModelList.add(indicator);

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to get targets from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        // close the database connection
        db.close();

        return indicatorModelList;
    }
}
