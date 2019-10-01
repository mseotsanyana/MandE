package com.me.mseotsanyana.mande.PPMER.DAL;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.me.mseotsanyana.treeadapterlibrary.cTreeModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class cLogFrameDBA {

    private static final SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy",Locale.US);

    // an object of the database helper
    private cSQLDBHelper dbHelper;
    private static final String TAG = "dbHelper";

    public cLogFrameDBA(Context context) {
        dbHelper = new cSQLDBHelper(context);
    }

    /**
     * this function adds the logframe (i.e., project) details
     */
    public boolean addLogFrameFromExcel(cLogFrameModel logFrameModel) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_ID, logFrameModel.getID());
        cv.put(cSQLDBHelper.KEY_SERVER_ID, logFrameModel.getServerID());
        cv.put(cSQLDBHelper.KEY_OWNER_ID, logFrameModel.getOwnerID());
        cv.put(cSQLDBHelper.KEY_ORG_ID, logFrameModel.getOrgID());
        cv.put(cSQLDBHelper.KEY_GROUP_BITS, logFrameModel.getGroupBITS());
        cv.put(cSQLDBHelper.KEY_PERMS_BITS, logFrameModel.getPermsBITS());
        cv.put(cSQLDBHelper.KEY_STATUS_BITS, logFrameModel.getStatusBITS());
        cv.put(cSQLDBHelper.KEY_NAME, logFrameModel.getName());
        cv.put(cSQLDBHelper.KEY_DESCRIPTION, logFrameModel.getDescription());
        cv.put(cSQLDBHelper.KEY_START_DATE, formatter.format(logFrameModel.getStartDate()));
        cv.put(cSQLDBHelper.KEY_END_DATE, formatter.format(logFrameModel.getEndDate()));
        cv.put(cSQLDBHelper.KEY_CREATED_DATE, formatter.format(logFrameModel.getCreatedDate()));
        cv.put(cSQLDBHelper.KEY_MODIFIED_DATE, formatter.format(logFrameModel.getModifiedDate()));
        cv.put(cSQLDBHelper.KEY_SYNCED_DATE, formatter.format(logFrameModel.getSyncedDate()));

        // insert project details
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblLOGFRAME, null, cv) < 0) {
                return false;
            }
        } catch (Exception e) {
            Log.d("Exception in importing ", e.getMessage().toString());
        }

        // close the database connection
        db.close();

        return true;
    }

    /*
     * the function delate a specific logframe
     */
    public boolean deleteProject(cLogFrameModel logFrameModel) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete a record of a specific ID
        try {
            if(db.delete(cSQLDBHelper.TABLE_tblLOGFRAME, cSQLDBHelper.KEY_ID + " = ?",
                    new String[]{String.valueOf(logFrameModel.getID())}) < 0){
                return false;
            }
        }catch (Exception e){
            Log.d("Exception in deleting ", e.getMessage().toString());
        }

        // close the database connection
        db.close();

        return true;
    }

    /*
     * the function delete all logFrames
     */
    public boolean deleteAllLogFrames() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        try {
            if(db.delete(cSQLDBHelper.TABLE_tblLOGFRAME, null, null) < 0){
                return false;
            }
        }catch (Exception e){
            Log.d(TAG, "Exception in deleting all logframes "+e.getMessage().toString());
        }

        // close the database connection
        db.close();

        return true;
    }


    /*
     * the function fetches all logframes
    */
    public ArrayList<cLogFrameModel> getLogFrameModels() {

        ArrayList<cLogFrameModel> logFrameModels = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+ cSQLDBHelper.TABLE_tblLOGFRAME, null);

        try {
            if (cursor.moveToFirst()) {
                do {
                    cLogFrameModel logFrameModel = new cLogFrameModel();

                    logFrameModel.setID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ID)));
                    logFrameModel.setServerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_SERVER_ID)));
                    logFrameModel.setOwnerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OWNER_ID)));
                    logFrameModel.setOrgID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ORG_ID)));
                    logFrameModel.setGroupBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_GROUP_BITS)));
                    logFrameModel.setPermsBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PERMS_BITS)));
                    logFrameModel.setStatusBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_STATUS_BITS)));
                    logFrameModel.setName(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_NAME)));
                    logFrameModel.setDescription(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_DESCRIPTION)));
                    logFrameModel.setStartDate(formatter.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_START_DATE))));
                    logFrameModel.setEndDate(formatter.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_END_DATE))));
                    logFrameModel.setCreatedDate(formatter.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_CREATED_DATE))));
                    logFrameModel.setModifiedDate(formatter.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_MODIFIED_DATE))));
                    logFrameModel.setSyncedDate(formatter.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_SYNCED_DATE))));

                    logFrameModels.add(logFrameModel);

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

        return logFrameModels;
    }

    // get child logFrames
    public ArrayList<cLogFrameModel> getChildLogFramesByID(int parentID) {
        // list of logFrames
        ArrayList<cLogFrameModel> logFrameModels = new ArrayList<>();

        // open the connection to the database
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // construct a selection query
        String selectQuery = "SELECT * FROM " +
                cSQLDBHelper.TABLE_tblLOGFRAME + " parent, " +
                cSQLDBHelper.TABLE_tblLOGFRAMETREE + " child " +
                "WHERE parent." + cSQLDBHelper.KEY_ID + " = child." + cSQLDBHelper.KEY_PARENT_FK_ID +
                " AND parent." + cSQLDBHelper.KEY_ID + " = ?";

        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(parentID)});

        try {
            if (cursor.moveToFirst()) {
                do {
                    cLogFrameModel logFrameModel = new cLogFrameModel();

                    logFrameModel.setID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ID)));
                    logFrameModel.setServerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_SERVER_ID)));
                    logFrameModel.setOwnerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OWNER_ID)));
                    logFrameModel.setOrgID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ORG_ID)));
                    logFrameModel.setGroupBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_GROUP_BITS)));
                    logFrameModel.setPermsBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PERMS_BITS)));
                    logFrameModel.setStatusBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_STATUS_BITS)));
                    logFrameModel.setName(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_NAME)));
                    logFrameModel.setDescription(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_DESCRIPTION)));
                    logFrameModel.setStartDate(formatter.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_START_DATE))));
                    logFrameModel.setEndDate(formatter.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_END_DATE))));
                    logFrameModel.setCreatedDate(formatter.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_CREATED_DATE))));
                    logFrameModel.setModifiedDate(formatter.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_MODIFIED_DATE))));
                    logFrameModel.setSyncedDate(formatter.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_SYNCED_DATE))));

                    logFrameModels.add(logFrameModel);

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

        return logFrameModels;
    }

    // get impacts
    public ArrayList<cImpactModel> getImpactsByID(int logFrameID) {
        // list of logFrames
        ArrayList<cImpactModel> impactModels = new ArrayList<>();

        // open the connection to the database
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // construct a selection query
        String selectQuery = "SELECT * FROM "+
                cSQLDBHelper.TABLE_tblLOGFRAME + " logframe, " +
                cSQLDBHelper.TABLE_tblIMPACT + " impact, " +
                cSQLDBHelper.TABLE_USER_ROLE +" logframe_impact " +
                " WHERE logframe."+cSQLDBHelper.KEY_ID+" = logframe_impact."+cSQLDBHelper.KEY_LOGFRAME_FK_ID+
                " AND impact."+cSQLDBHelper.KEY_ID+" = logframe_impact."+cSQLDBHelper.KEY_IMPACT_FK_ID+
                " AND logframe."+cSQLDBHelper.KEY_ID +" = ?";

        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(logFrameID)});

        try {
            if (cursor.moveToFirst()) {
                do {
                    cImpactModel impactModel = new cImpactModel();

                    impactModel.setID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ID)));
                    impactModel.setServerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_SERVER_ID)));
                    impactModel.setOwnerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OWNER_ID)));
                    impactModel.setOrgID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ORG_ID)));
                    impactModel.setGroupBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_GROUP_BITS)));
                    impactModel.setPermsBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PERMS_BITS)));
                    impactModel.setStatusBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_STATUS_BITS)));
                    impactModel.setName(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_NAME)));
                    impactModel.setDescription(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_DESCRIPTION)));
                    impactModel.setStartDate(formatter.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_START_DATE))));
                    impactModel.setEndDate(formatter.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_END_DATE))));
                    impactModel.setCreatedDate(formatter.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_CREATED_DATE))));
                    impactModel.setModifiedDate(formatter.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_MODIFIED_DATE))));
                    impactModel.setSyncedDate(formatter.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_SYNCED_DATE))));

                    impactModels.add(impactModel);

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

        return impactModels;
    }
}