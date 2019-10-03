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

    /*
     * the function adding a specific activity
     */
    public boolean addActivity(cActivityModel activityModel) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_ID, activityModel.getID());
        cv.put(cSQLDBHelper.KEY_SERVER_ID, activityModel.getServerID());
        cv.put(cSQLDBHelper.KEY_OWNER_ID, activityModel.getOwnerID());
        cv.put(cSQLDBHelper.KEY_ORG_ID, activityModel.getOrgID());
        cv.put(cSQLDBHelper.KEY_GROUP_BITS, activityModel.getGroupBITS());
        cv.put(cSQLDBHelper.KEY_PERMS_BITS, activityModel.getPermsBITS());
        cv.put(cSQLDBHelper.KEY_STATUS_BITS, activityModel.getStatusBITS());
        cv.put(cSQLDBHelper.KEY_NAME, activityModel.getName());
        cv.put(cSQLDBHelper.KEY_DESCRIPTION, activityModel.getDescription());
        cv.put(cSQLDBHelper.KEY_START_DATE, formatter.format(activityModel.getStartDate()));
        cv.put(cSQLDBHelper.KEY_END_DATE, formatter.format(activityModel.getEndDate()));
        cv.put(cSQLDBHelper.KEY_CREATED_DATE, formatter.format(activityModel.getCreatedDate()));
        cv.put(cSQLDBHelper.KEY_MODIFIED_DATE, formatter.format(activityModel.getModifiedDate()));
        cv.put(cSQLDBHelper.KEY_SYNCED_DATE, formatter.format(activityModel.getSyncedDate()));

        // insert project details
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblACTIVITY, null, cv) < 0) {
                return false;
            }
        } catch (Exception e) {
            Log.d(TAG, "Exception in adding OUTPUT "+e.getMessage().toString());
        }

        // close the database connection
        db.close();

        return true;
    }

    /*
     * the function adding a specific activity outcome
     */
    public boolean addActivityOutput(cActivityModel.cActivityOutputModel activityOutputModel){
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_OUTPUT_FK_ID, activityOutputModel.getActivityID());
        cv.put(cSQLDBHelper.KEY_OUTPUT_FK_ID, activityOutputModel.getOutputID());
        cv.put(cSQLDBHelper.KEY_PARENT_FK_ID, activityOutputModel.getParentID());
        cv.put(cSQLDBHelper.KEY_CHILD_FK_ID, activityOutputModel.getChildID());
        cv.put(cSQLDBHelper.KEY_SERVER_ID, activityOutputModel.getServerID());
        cv.put(cSQLDBHelper.KEY_OWNER_ID, activityOutputModel.getOwnerID());
        cv.put(cSQLDBHelper.KEY_ORG_ID, activityOutputModel.getOrgID());
        cv.put(cSQLDBHelper.KEY_GROUP_BITS, activityOutputModel.getGroupBITS());
        cv.put(cSQLDBHelper.KEY_PERMS_BITS, activityOutputModel.getPermsBITS());
        cv.put(cSQLDBHelper.KEY_STATUS_BITS, activityOutputModel.getStatusBITS());
        cv.put(cSQLDBHelper.KEY_CREATED_DATE, formatter.format(activityOutputModel.getCreatedDate()));
        cv.put(cSQLDBHelper.KEY_MODIFIED_DATE, formatter.format(activityOutputModel.getModifiedDate()));
        cv.put(cSQLDBHelper.KEY_SYNCED_DATE, formatter.format(activityOutputModel.getSyncedDate()));

        // insert project details
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblACTIVITY_OUTPUT, null, cv) < 0) {
                return false;
            }
        } catch (Exception e) {
            Log.d(TAG, "Exception in adding OUTPUT_OUTPUTs "+e.getMessage().toString());
        }

        // close the database connection
        db.close();

        return true;
    }

    /*
     * the function delete all activities
     */
    public boolean deleteActivities() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        try {
            if(db.delete(cSQLDBHelper.TABLE_tblACTIVITY, null, null) < 0){
                return false;
            }
        }catch (Exception e){
            Log.d(TAG, "Exception in deleting all OUTPUTs "+e.getMessage().toString());
        }

        // close the database connection
        db.close();

        return true;
    }

    /*
     * the function delete all activity outcomes
     */
    public boolean deleteActivityOutputs() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        try {
            if(db.delete(cSQLDBHelper.TABLE_tblACTIVITY_OUTPUT, null, null) < 0){
                return false;
            }
        }catch (Exception e){
            Log.d(TAG, "Exception in deleting all ACTIVITY_OUTPUTs "+e.getMessage().toString());
        }

        // close the database connection
        db.close();

        return true;
    }

    /*
     * the function fetches all activitys
     */
    public ArrayList<cActivityModel> getActivityModels() {
        // list of activitys
        ArrayList<cActivityModel> activityModels = new ArrayList<>();

        // open the connection to the database
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // construct a selection query
        String selectQuery = "SELECT * FROM "+ cSQLDBHelper.TABLE_tblACTIVITY;

        // construct an argument cursor
        Cursor cursor = db.rawQuery(selectQuery, null);

        try {
            if (cursor.moveToFirst()) {
                do {
                    cActivityModel activityModel = new cActivityModel();

                    activityModel.setID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ID)));
                    activityModel.setServerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_SERVER_ID)));
                    activityModel.setOwnerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OWNER_ID)));
                    activityModel.setOrgID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ORG_ID)));
                    activityModel.setGroupBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_GROUP_BITS)));
                    activityModel.setPermsBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PERMS_BITS)));
                    activityModel.setStatusBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_STATUS_BITS)));
                    activityModel.setName(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_NAME)));
                    activityModel.setDescription(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_DESCRIPTION)));
                    activityModel.setStartDate(formatter.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_START_DATE))));
                    activityModel.setEndDate(formatter.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_END_DATE))));
                    activityModel.setCreatedDate(formatter.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_CREATED_DATE))));
                    activityModel.setModifiedDate(formatter.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_MODIFIED_DATE))));
                    activityModel.setSyncedDate(formatter.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_SYNCED_DATE))));

                    activityModels.add(activityModel);

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Exception in reading all OUTPUTs "+e.getMessage().toString());
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        // close the database connection
        db.close();

        return activityModels;
    }

    // get activity outcomes
    public ArrayList<cOutputModel> getActivityOutputsByID(int activityID) {
        // list of child outcome
        ArrayList<cOutputModel> outcomeModels = new ArrayList<cOutputModel>();

        // open the connection to the database
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // construct a selection query
        String selectQuery = "SELECT * FROM " +
                cSQLDBHelper.TABLE_tblACTIVITY + " activity, " +
                cSQLDBHelper.TABLE_tblOUTPUT + " output, " +
                cSQLDBHelper.TABLE_tblACTIVITY_OUTPUT + " activity_output " +
                " WHERE activity."+cSQLDBHelper.KEY_ID+" = activity_output."+cSQLDBHelper.KEY_ACTIVITY_FK_ID +
                " AND activity."+cSQLDBHelper.KEY_LOGFRAME_FK_ID + " = activity_output."+cSQLDBHelper.KEY_PARENT_FK_ID +
                " AND output."+cSQLDBHelper.KEY_ID + " = activity_output."+cSQLDBHelper.KEY_OUTPUT_FK_ID +
                " AND output."+cSQLDBHelper.KEY_LOGFRAME_FK_ID +" = activity_output."+cSQLDBHelper.KEY_CHILD_FK_ID +
                " AND activity."+cSQLDBHelper.KEY_ID +" = ?";

        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(activityID)});

        try {
            if (cursor.moveToFirst()) {
                do {

                    cOutputModel outcomeModel = new cOutputModel();

                    outcomeModel.setID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ID)));
                    outcomeModel.setServerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_SERVER_ID)));
                    outcomeModel.setOwnerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OWNER_ID)));
                    outcomeModel.setOrgID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ORG_ID)));
                    outcomeModel.setGroupBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_GROUP_BITS)));
                    outcomeModel.setPermsBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PERMS_BITS)));
                    outcomeModel.setStatusBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_STATUS_BITS)));
                    outcomeModel.setName(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_NAME)));
                    outcomeModel.setDescription(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_DESCRIPTION)));
                    outcomeModel.setStartDate(formatter.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_START_DATE))));
                    outcomeModel.setEndDate(formatter.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_END_DATE))));
                    outcomeModel.setCreatedDate(formatter.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_CREATED_DATE))));
                    outcomeModel.setModifiedDate(formatter.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_MODIFIED_DATE))));
                    outcomeModel.setSyncedDate(formatter.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_SYNCED_DATE))));

                    outcomeModels.add(outcomeModel);

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Exception in reading all ACTIVITY_OUTPUTs "+e.getMessage().toString());
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        // close the database connection
        db.close();

        return outcomeModels;
    }
}
