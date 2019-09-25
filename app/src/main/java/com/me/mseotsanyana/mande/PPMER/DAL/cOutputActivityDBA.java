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

/**
 * Created by mseotsanyana on 2017/06/27.
 */

public class cOutputActivityDBA {
    // an object of the database helper
    private cSQLDBHelper dbHelper;

    private cOutputDBA outputDBA;
    private cActivityDBA activityDBA;

    private static final SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
    private static final String TAG = "dbHelper";

    public cOutputActivityDBA(Context context) {
        dbHelper = new cSQLDBHelper(context);
        outputDBA = new cOutputDBA(context);
        activityDBA = new cActivityDBA(context);
    }

    public boolean addOuputActivity(cOutputActivityModel outputActivityModel){
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long result = -1;

        // get a output by id
        cOutputModel outputModel = outputDBA.getOutputByID(outputActivityModel.getOutputID());
        // get an activity by id
        cActivityModel activityModel = activityDBA.getActivityByID(outputActivityModel.getActivityID());

        if ((outputModel != null) && (activityModel != null)){

            ContentValues cv = new ContentValues();

            cv.put(cSQLDBHelper.KEY_OUTPUT_FK_ID, outputModel.getOutputID());
            cv.put(cSQLDBHelper.KEY_ACTIVITY_FK_ID, activityModel.getActivityID());
            cv.put(cSQLDBHelper.KEY_OUTPUT_ACTIVITY_OWNER_ID, outputActivityModel.getOwnerID());
            cv.put(cSQLDBHelper.KEY_OUTPUT_ACTIVITY_DATE, formatter.format(outputActivityModel.getCreateDate()));

            // insert output activity record
            result = db.insert(cSQLDBHelper.TABLE_OUTPUT_ACTIVITY, null, cv);
        }

        // close the database connection
        db.close();

        return result > -1;
    }

    public List<cOutputActivityModel> getOutputActivityList() {

        List<cOutputActivityModel> outputActivityModelList = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+ cSQLDBHelper.TABLE_OUTPUT_ACTIVITY, null);

        try {
            if (cursor.moveToFirst()) {
                do {
                    cOutputActivityModel output = new cOutputActivityModel();

                    output.setOutputID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OUTPUT_FK_ID)));
                    output.setActivityID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ACTIVITY_FK_ID)));
                    output.setOwnerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OUTPUT_ACTIVITY_OWNER_ID)));
                    output.setCreateDate(formatter.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_OUTPUT_ACTIVITY_DATE))));

                    outputActivityModelList.add(output);

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

        return outputActivityModelList;
    }
}
