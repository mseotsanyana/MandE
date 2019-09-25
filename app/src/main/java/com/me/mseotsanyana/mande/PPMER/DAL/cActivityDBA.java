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

public class cActivityDBA {
    // an object of the database helper
    private cSQLDBHelper dbHelper;

    private static final SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
    private static final String TAG = "dbHelper";

    public cActivityDBA(Context context) {
        dbHelper = new cSQLDBHelper(context);
    }


    public boolean deleteAllActivities() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_ACTIVITY, null, null);

        // close the database connection
        db.close();

        return result > -1;
    }

    public boolean addActivity(cActivityModel activityModel) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_ACTIVITY_ID, activityModel.getActivityID());
        cv.put(cSQLDBHelper.KEY_ACTIVITY_OWNER_ID, activityModel.getOwnerID());
        cv.put(cSQLDBHelper.KEY_ACTIVITY_NAME, activityModel.getActivityName());
        cv.put(cSQLDBHelper.KEY_ACTIVITY_DESCRIPTION, activityModel.getActivityDescription());
        cv.put(cSQLDBHelper.KEY_ACTIVITY_DATE, formatter.format(activityModel.getCreateDate()));

        // insert outcome record
        try {
            if (db.insert(cSQLDBHelper.TABLE_ACTIVITY, null, cv) < 0) {
                return false;
            }
        } catch (Exception ex) {
            Log.d("Exception in importing ", ex.getMessage().toString());
        }

        // close the database connection
        db.close();

        return true;
    }

    public cActivityModel getActivityByID(int activityID) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        // construct a selection query
        String selectQuery = "SELECT * FROM " +
                cSQLDBHelper.TABLE_ACTIVITY + " WHERE " +
                cSQLDBHelper.KEY_ACTIVITY_ID + "= ?";

        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(activityID)});

        cActivityModel activity = new cActivityModel();

        try {
            if (cursor.moveToFirst()) {
                do {
                    activity.setActivityID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ACTIVITY_ID)));
                    activity.setOwnerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ACTIVITY_OWNER_ID)));
                    activity.setActivityName(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_ACTIVITY_NAME)));
                    activity.setActivityDescription(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_ACTIVITY_DESCRIPTION)));
                    activity.setCreateDate(formatter.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_ACTIVITY_DATE))));

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

        return activity;
    }


    public List<cActivityModel> getActivityList() {

        List<cActivityModel> activityModelList = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+ cSQLDBHelper.TABLE_ACTIVITY, null);

        try {
            if (cursor.moveToFirst()) {
                do {
                    cActivityModel activity = new cActivityModel();

                    activity.setActivityID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ACTIVITY_ID)));
                    activity.setOwnerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ACTIVITY_OWNER_ID)));
                    activity.setActivityName(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_ACTIVITY_NAME)));
                    activity.setActivityDescription(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_ACTIVITY_DESCRIPTION)));
                    activity.setCreateDate(formatter.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_ACTIVITY_DATE))));

                    activityModelList.add(activity);

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

        return activityModelList;
    }
}
