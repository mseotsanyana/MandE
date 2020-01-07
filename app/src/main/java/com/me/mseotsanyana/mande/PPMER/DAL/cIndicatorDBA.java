package com.me.mseotsanyana.mande.PPMER.DAL;

import android.content.Context;

import com.me.mseotsanyana.mande.STORAGE.database.cSQLDBHelper;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class cIndicatorDBA {
    // an object of the database helper
    private cSQLDBHelper dbHelper;

    private static final SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
    private static final String TAG = "dbHelper";

    public cIndicatorDBA(Context context) {
        dbHelper = new cSQLDBHelper(context);
    }

/*
    public boolean deleteAllIndicators() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_INDICATOR, null, null);

        // close the database connection
        db.close();

        return result > -1;
    }

    public boolean addIndicator(cIndicatorModel indicatorModel) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_INDICATOR_ID, indicatorModel.getIndicatorID());
        cv.put(cSQLDBHelper.KEY_INDICATOR_OWNER_ID, indicatorModel.getOwnerID());
        cv.put(cSQLDBHelper.KEY_STATUS_FK_ID, indicatorModel.getStatusID());
        cv.put(cSQLDBHelper.KEY_INDICATOR_NAME, indicatorModel.getIndicatorName());
        cv.put(cSQLDBHelper.KEY_INDICATOR_DESCRIPTION, indicatorModel.getIndicatorDescription());
        cv.put(cSQLDBHelper.KEY_INDICATOR_TYPE, indicatorModel.getIndicatorType());
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

    public cIndicatorModel getIndicatorByID(int indicatorID) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        // construct a selection query
        String selectQuery = "SELECT * FROM " +
                cSQLDBHelper.TABLE_INDICATOR + " WHERE " +
                cSQLDBHelper.KEY_INDICATOR_ID + "= ?";

        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(indicatorID)});

        cIndicatorModel indicator = new cIndicatorModel();

        try {
            if (cursor.moveToFirst()) {
                do {
                    indicator.setIndicatorID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_INDICATOR_ID)));
                    indicator.setOwnerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OUTCOME_OWNER_ID)));
                    indicator.setStatusID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_STATUS_FK_ID)));
                    indicator.setIndicatorName(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_INDICATOR_NAME)));
                    indicator.setIndicatorDescription(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_INDICATOR_DESCRIPTION)));
                    indicator.setIndicatorType(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_INDICATOR_TYPE)));
                    indicator.setCreateDate(formatter.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_INDICATOR_DATE))));

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


    public List<cIndicatorModel> getIndicatorList() {

        List<cIndicatorModel> indicatorModelList = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+ cSQLDBHelper.TABLE_INDICATOR, null);

        try {
            if (cursor.moveToFirst()) {
                do {
                    cIndicatorModel indicator = new cIndicatorModel();

                    indicator.setIndicatorID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_INDICATOR_ID)));
                    indicator.setOwnerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OUTCOME_OWNER_ID)));
                    indicator.setStatusID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_STATUS_FK_ID)));
                    indicator.setIndicatorName(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_INDICATOR_NAME)));
                    indicator.setIndicatorDescription(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_INDICATOR_DESCRIPTION)));
                    indicator.setIndicatorType(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_INDICATOR_TYPE)));
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

 */
}
