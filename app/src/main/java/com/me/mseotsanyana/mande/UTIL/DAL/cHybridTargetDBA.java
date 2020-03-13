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

public class cHybridTargetDBA {
    // an object of the database helper
    private cSQLDBHelper dbHelper;

    private static final SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
    private static final String TAG = "dbHelper";

    public cHybridTargetDBA(Context context) {
        dbHelper = new cSQLDBHelper(context);
    }


    public boolean deleteAllHybridTargets() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_HYBRIDTARGET, null, null);

        // close the database connection
        db.close();

        return result > -1;
    }

    public boolean addHybridTarget(cHybridTargetModel indicatorModel) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_HYBRID_ID, indicatorModel.getHybridID());
        cv.put(cSQLDBHelper.KEY_INDICATOR_FK_ID, indicatorModel.getIndicatorID());
        cv.put(cSQLDBHelper.KEY_HYBRID_OWNER_ID, indicatorModel.getOwnerID());
        cv.put(cSQLDBHelper.KEY_CATEGORY_FK_ID, indicatorModel.getCategoryID());
        cv.put(cSQLDBHelper.KEY_HYBRID_TARGET, indicatorModel.getHybridTarget());
        cv.put(cSQLDBHelper.KEY_HYBRID_TARGET_DATE, formatter.format(indicatorModel.getCreateDate()));

        // insert outcome record
        try {
            if (db.insert(cSQLDBHelper.TABLE_HYBRIDTARGET, null, cv) < 0) {
                return false;
            }
        } catch (Exception ex) {
            Log.d("Exception in importing ", ex.getMessage().toString());
        }

        // close the database connection
        db.close();

        return true;
    }

    public cHybridTargetModel getHybridTargetByID(int indicatorID) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        // construct a selection query
        String selectQuery = "SELECT * FROM " +
                cSQLDBHelper.TABLE_HYBRIDTARGET + " WHERE " +
                cSQLDBHelper.KEY_HYBRID_ID + "= ?";

        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(indicatorID)});

        cHybridTargetModel indicator = new cHybridTargetModel();

        try {
            if (cursor.moveToFirst()) {
                do {
                    indicator.setHybridID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_HYBRID_ID)));
                    indicator.setIndicatorID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_INDICATOR_FK_ID)));
                    indicator.setOwnerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_HYBRID_OWNER_ID)));
                    indicator.setCategoryID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_CATEGORY_FK_ID)));
                    indicator.setHybridTarget(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_HYBRID_TARGET)));
                    indicator.setCreateDate(formatter.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_HYBRID_TARGET_DATE))));

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to get hybrid targets from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        // close the database connection
        db.close();

        return indicator;
    }


    public List<cHybridTargetModel> getHybridTargetList() {

        List<cHybridTargetModel> indicatorModelList = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+ cSQLDBHelper.TABLE_INDICATOR, null);

        try {
            if (cursor.moveToFirst()) {
                do {
                    cHybridTargetModel indicator = new cHybridTargetModel();

                    indicator.setHybridID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_HYBRID_ID)));
                    indicator.setIndicatorID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_INDICATOR_FK_ID)));
                    indicator.setOwnerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_HYBRID_OWNER_ID)));
                    indicator.setCategoryID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_CATEGORY_FK_ID)));
                    indicator.setHybridTarget(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_HYBRID_TARGET)));
                    indicator.setCreateDate(formatter.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_HYBRID_TARGET_DATE))));

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
