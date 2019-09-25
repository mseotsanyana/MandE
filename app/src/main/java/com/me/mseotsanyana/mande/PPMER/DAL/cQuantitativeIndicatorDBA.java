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

public class cQuantitativeIndicatorDBA {
    // an object of the database helper
    private cSQLDBHelper dbHelper;

    private static final SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
    private static final String TAG = "dbHelper";

    public cQuantitativeIndicatorDBA(Context context) {
        dbHelper = new cSQLDBHelper(context);
    }


    public boolean deleteAllQuantitativeIndicators() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_QUANTITATIVEINDICATOR, null, null);

        // close the database connection
        db.close();

        return result > -1;
    }

    public boolean addQuantitativeIndicator(cQuantitativeIndicatorModel indicatorModel) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_INDICATOR_FK_ID, indicatorModel.getIndicatorID());
        cv.put(cSQLDBHelper.KEY_INDICATOR_OWNER_ID, indicatorModel.getOwnerID());
        cv.put(cSQLDBHelper.KEY_UNIT_FK_ID, indicatorModel.getUnitID());
        cv.put(cSQLDBHelper.KEY_QUANTITATIVE_BASELINE, indicatorModel.getQuantitativeBaseline());
        cv.put(cSQLDBHelper.KEY_QUANTITATIVE_BASELINE_DATE, formatter.format(indicatorModel.getCreateDate()));

        // insert outcome record
        try {
            if (db.insert(cSQLDBHelper.TABLE_QUANTITATIVEINDICATOR, null, cv) < 0) {
                return false;
            }
        } catch (Exception ex) {
            Log.d("Exception in importing ", ex.getMessage().toString());
        }

        // close the database connection
        db.close();

        return true;
    }

    public cQuantitativeIndicatorModel getQuantitativeIndicatorByID(int indicatorID) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        // construct a selection query
        String selectQuery = "SELECT * FROM " +
                cSQLDBHelper.TABLE_QUANTITATIVEINDICATOR + " WHERE " +
                cSQLDBHelper.KEY_INDICATOR_FK_ID + "= ?";

        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(indicatorID)});

        cQuantitativeIndicatorModel indicator = new cQuantitativeIndicatorModel();

        try {
            if (cursor.moveToFirst()) {
                do {
                    indicator.setIndicatorID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_INDICATOR_FK_ID)));
                    indicator.setOwnerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OUTCOME_OWNER_ID)));
                    indicator.setUnitID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_UNIT_FK_ID)));
                    indicator.setQuantitativeBaseline(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_QUANTITATIVE_BASELINE)));
                    indicator.setCreateDate(formatter.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_QUANTITATIVE_BASELINE_DATE))));

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


    public List<cQuantitativeIndicatorModel> getQuantitativeIndicatorList() {

        List<cQuantitativeIndicatorModel> indicatorModelList = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+ cSQLDBHelper.TABLE_QUANTITATIVEINDICATOR, null);

        try {
            if (cursor.moveToFirst()) {
                do {
                    cQuantitativeIndicatorModel indicator = new cQuantitativeIndicatorModel();

                    indicator.setIndicatorID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_INDICATOR_ID)));
                    indicator.setOwnerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OUTCOME_OWNER_ID)));
                    indicator.setUnitID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_UNIT_FK_ID)));
                    indicator.setQuantitativeBaseline(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_QUANTITATIVE_BASELINE)));
                    indicator.setCreateDate(formatter.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_QUANTITATIVE_BASELINE_DATE))));

                    indicatorModelList.add(indicator);

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

        return indicatorModelList;
    }
}
