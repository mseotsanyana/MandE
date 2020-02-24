package com.me.mseotsanyana.mande.UTILITY.DAL;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.me.mseotsanyana.mande.STORAGE.database.cSQLDBHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class cHybridIndicatorDBA {
    // an object of the database helper
    private cSQLDBHelper dbHelper;

    private static final SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
    private static final String TAG = "dbHelper";

    public cHybridIndicatorDBA(Context context) {
        dbHelper = new cSQLDBHelper(context);
    }


    public boolean deleteAllHybridIndicators() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_HYBRIDINDICATOR, null, null);

        // close the database connection
        db.close();

        return result > -1;
    }

    public boolean addHybridIndicator(cHybridIndicatorModel indicatorModel) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_INDICATOR_ID, indicatorModel.getIndicatorID());
        cv.put(cSQLDBHelper.KEY_HYBRID_OWNER_ID, indicatorModel.getOwnerID());
        cv.put(cSQLDBHelper.KEY_CATEGORY_FK_ID, indicatorModel.getCategoryID());
        cv.put(cSQLDBHelper.KEY_HYBRID_BASELINE, indicatorModel.getHybridBaseline());
        cv.put(cSQLDBHelper.KEY_INDICATOR_DATE, formatter.format(indicatorModel.getCreateDate()));

        // insert outcome record
        try {
            if (db.insert(cSQLDBHelper.TABLE_INDICATOR, null, cv) < 0) {
                return false;
            }
        } catch (Exception ex) {
            Log.d("Exception in importing ", ex.getMessage().toString());
        }

        // close the database connection
        db.close();

        return true;
    }

    public cHybridIndicatorModel getHybridIndicatorByID(int indicatorID) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        // construct a selection query
        String selectQuery = "SELECT * FROM " +
                cSQLDBHelper.TABLE_HYBRIDINDICATOR + " WHERE " +
                cSQLDBHelper.KEY_INDICATOR_FK_ID + "= ?";

        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(indicatorID)});

        cHybridIndicatorModel indicator = new cHybridIndicatorModel();

        try {
            if (cursor.moveToFirst()) {
                do {
                    indicator.setIndicatorID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_INDICATOR_ID)));
                    indicator.setOwnerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OUTCOME_OWNER_ID)));
                    indicator.setCategoryID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_CATEGORY_FK_ID)));
                    indicator.setHybridBaseline(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_HYBRID_BASELINE)));
                    indicator.setCreateDate(formatter.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_HYBRID_BASELINE_DATE))));

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


    public List<cHybridIndicatorModel> getHybridIndicatorList() {

        List<cHybridIndicatorModel> indicatorModelList = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+ cSQLDBHelper.TABLE_INDICATOR, null);

        try {
            if (cursor.moveToFirst()) {
                do {
                    cHybridIndicatorModel indicator = new cHybridIndicatorModel();

                    indicator.setIndicatorID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_INDICATOR_FK_ID)));
                    indicator.setOwnerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_INDICATOR_OWNER_ID)));
                    indicator.setCategoryID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_CATEGORY_FK_ID)));
                    indicator.setHybridBaseline(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_HYBRID_BASELINE)));
                    indicator.setCreateDate(formatter.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_HYBRID_BASELINE_DATE))));

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
