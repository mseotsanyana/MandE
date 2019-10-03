package com.me.mseotsanyana.mande.PPMER.DAL;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.me.mseotsanyana.treeadapterlibrary.cTreeModel;

import java.lang.String;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class cImpactDBA
{
	// an object of the database helper
	private cSQLDBHelper dbHelper;

    private static final SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
    private static final String TAG = "dbHelper";

    public cImpactDBA(Context context)
    {
        dbHelper = new cSQLDBHelper(context);
    }

    public boolean addImpact(cImpactModel impactModel) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_ID, impactModel.getID());
        cv.put(cSQLDBHelper.KEY_SERVER_ID, impactModel.getServerID());
        cv.put(cSQLDBHelper.KEY_OWNER_ID, impactModel.getOwnerID());
        cv.put(cSQLDBHelper.KEY_ORG_ID, impactModel.getOrgID());
        cv.put(cSQLDBHelper.KEY_GROUP_BITS, impactModel.getGroupBITS());
        cv.put(cSQLDBHelper.KEY_PERMS_BITS, impactModel.getPermsBITS());
        cv.put(cSQLDBHelper.KEY_STATUS_BITS, impactModel.getStatusBITS());
        cv.put(cSQLDBHelper.KEY_NAME, impactModel.getName());
        cv.put(cSQLDBHelper.KEY_DESCRIPTION, impactModel.getDescription());
        cv.put(cSQLDBHelper.KEY_START_DATE, formatter.format(impactModel.getStartDate()));
        cv.put(cSQLDBHelper.KEY_END_DATE, formatter.format(impactModel.getEndDate()));
        cv.put(cSQLDBHelper.KEY_CREATED_DATE, formatter.format(impactModel.getCreatedDate()));
        cv.put(cSQLDBHelper.KEY_MODIFIED_DATE, formatter.format(impactModel.getModifiedDate()));
        cv.put(cSQLDBHelper.KEY_SYNCED_DATE, formatter.format(impactModel.getSyncedDate()));

        // insert project details
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblIMPACT, null, cv) < 0) {
                return false;
            }
        } catch (Exception e) {
            Log.d(TAG, "Exception in importing "+e.getMessage().toString());
        }

        // close the database connection
        db.close();

        return true;
    }

    /*
     * the function delate a specific logframe
     */
    public boolean deleteImpact(cImpactModel impactModel) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete a record of a specific ID
        try {
            if(db.delete(cSQLDBHelper.TABLE_tblIMPACT, cSQLDBHelper.KEY_ID + " = ?",
                    new String[]{String.valueOf(impactModel.getID())}) < 0){
                return false;
            }
        }catch (Exception e){
            Log.d(TAG, "Exception in deleting "+e.getMessage().toString());
        }

        // close the database connection
        db.close();

        return true;
    }

    /*
     * the function delete all impacts
     */
    public boolean deleteImpacts() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        try {
            if(db.delete(cSQLDBHelper.TABLE_tblIMPACT, null, null) < 0){
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
     * the function fetches all impacts
     */
    public ArrayList<cImpactModel> getImpactModels() {
        // list of impacts
        ArrayList<cImpactModel> impactModels = new ArrayList<>();

        // open the connection to the database
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM "+ cSQLDBHelper.TABLE_tblIMPACT, null);

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

    // get impacts for a given impact ID
    public ArrayList<cImpactModel> getImpactsByID(int impactID) {
        // list of impacts
        ArrayList<cImpactModel> impactModels = new ArrayList<>();

        // open the connection to the database
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // construct a selection query
        String selectQuery = "SELECT * FROM "+
                cSQLDBHelper.TABLE_tblIMPACT + " impact "+
                " WHERE impact."+cSQLDBHelper.KEY_ID + " = ?";

        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(impactID)});

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
