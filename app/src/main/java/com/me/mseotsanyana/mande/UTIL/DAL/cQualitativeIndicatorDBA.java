package com.me.mseotsanyana.mande.UTIL.DAL;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.me.mseotsanyana.mande.DAL.storage.database.cSQLDBHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class cQualitativeIndicatorDBA {
    // an object of the database helper
    private cSQLDBHelper dbHelper;

    private static final SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
    private static final String TAG = "dbHelper";

    public cQualitativeIndicatorDBA(Context context) {
        dbHelper = new cSQLDBHelper(context);
    }


    public boolean deleteAllQualitativeIndicators() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_QUALITATIVEINDICATOR, null, null);

        // close the database connection
        db.close();

        return result > -1;
    }

    public boolean addQualitativeIndicator(cQualitativeIndicatorModel indicatorModel) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_INDICATOR_FK_ID, indicatorModel.getIndicatorID());
        cv.put(cSQLDBHelper.KEY_QUANTITATIVE_OWNER_ID, indicatorModel.getOwnerID());
        cv.put(cSQLDBHelper.KEY_QUANTITATIVE_BASELINE, indicatorModel.getQualitativeBaseline());
        cv.put(cSQLDBHelper.KEY_QUANTITATIVE_BASELINE_DATE, formatter.format(indicatorModel.getCreateDate()));

        // insert outcome record
        try {
            if (db.insert(cSQLDBHelper.TABLE_QUALITATIVEINDICATOR, null, cv) < 0) {
                return false;
            }
        } catch (Exception ex) {
            Log.d("Exception in importing ", ex.getMessage().toString());
        }

        // close the database connection
        db.close();

        return true;
    }

    public cQualitativeIndicatorModel getQualitativeIndicatorByID(int indicatorID) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        // construct a selection query
        String selectQuery = "SELECT * FROM " +
                cSQLDBHelper.TABLE_QUALITATIVEINDICATOR + " WHERE " +
                cSQLDBHelper.KEY_INDICATOR_FK_ID + "= ?";

        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(indicatorID)});

        cQualitativeIndicatorModel indicator = new cQualitativeIndicatorModel();

        try {
            if (cursor.moveToFirst()) {
                do {
                    indicator.setIndicatorID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_INDICATOR_FK_ID)));
                    indicator.setOwnerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_QUALITATIVE_OWNER_ID)));
                    indicator.setQualitativeBaseline(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_QUALITATIVE_BASELINE)));
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


    public List<cQualitativeIndicatorModel> getQualitativeIndicatorList() {

        List<cQualitativeIndicatorModel> indicatorModelList = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+ cSQLDBHelper.TABLE_QUALITATIVEINDICATOR, null);

        try {
            if (cursor.moveToFirst()) {
                do {
                    cQualitativeIndicatorModel indicator = new cQualitativeIndicatorModel();

                    indicator.setIndicatorID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_INDICATOR_ID)));
                    indicator.setOwnerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_QUALITATIVE_OWNER_ID)));
                    indicator.setQualitativeBaseline(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_QUALITATIVE_BASELINE)));
                    indicator.setCreateDate(formatter.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_INDICATOR_DATE))));

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
