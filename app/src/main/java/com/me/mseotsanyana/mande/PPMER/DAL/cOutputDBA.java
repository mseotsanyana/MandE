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

public class cOutputDBA {
    // an object of the database helper
    private cSQLDBHelper dbHelper;

    private static final SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
    private static final String TAG = "dbHelper";

    public cOutputDBA(Context context) {
        dbHelper = new cSQLDBHelper(context);
    }

    /*
     * the function adding a specific output
     */
    public boolean addOutput(cOutputModel outputModel) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_ID, outputModel.getID());
        cv.put(cSQLDBHelper.KEY_SERVER_ID, outputModel.getServerID());
        cv.put(cSQLDBHelper.KEY_OWNER_ID, outputModel.getOwnerID());
        cv.put(cSQLDBHelper.KEY_ORG_ID, outputModel.getOrgID());
        cv.put(cSQLDBHelper.KEY_GROUP_BITS, outputModel.getGroupBITS());
        cv.put(cSQLDBHelper.KEY_PERMS_BITS, outputModel.getPermsBITS());
        cv.put(cSQLDBHelper.KEY_STATUS_BITS, outputModel.getStatusBITS());
        cv.put(cSQLDBHelper.KEY_NAME, outputModel.getName());
        cv.put(cSQLDBHelper.KEY_DESCRIPTION, outputModel.getDescription());
        cv.put(cSQLDBHelper.KEY_START_DATE, formatter.format(outputModel.getStartDate()));
        cv.put(cSQLDBHelper.KEY_END_DATE, formatter.format(outputModel.getEndDate()));
        cv.put(cSQLDBHelper.KEY_CREATED_DATE, formatter.format(outputModel.getCreatedDate()));
        cv.put(cSQLDBHelper.KEY_MODIFIED_DATE, formatter.format(outputModel.getModifiedDate()));
        cv.put(cSQLDBHelper.KEY_SYNCED_DATE, formatter.format(outputModel.getSyncedDate()));

        // insert project details
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblOUTPUT, null, cv) < 0) {
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
     * the function adding a specific output outcome
     */
    public boolean addOutputOutcome(cOutputModel.cOutputOutcomeModel outputOutcomeModel){
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_OUTPUT_FK_ID, outputOutcomeModel.getOutputID());
        cv.put(cSQLDBHelper.KEY_OUTCOME_FK_ID, outputOutcomeModel.getOutcomeID());
        cv.put(cSQLDBHelper.KEY_PARENT_FK_ID, outputOutcomeModel.getParentID());
        cv.put(cSQLDBHelper.KEY_CHILD_FK_ID, outputOutcomeModel.getChildID());
        cv.put(cSQLDBHelper.KEY_SERVER_ID, outputOutcomeModel.getServerID());
        cv.put(cSQLDBHelper.KEY_OWNER_ID, outputOutcomeModel.getOwnerID());
        cv.put(cSQLDBHelper.KEY_ORG_ID, outputOutcomeModel.getOrgID());
        cv.put(cSQLDBHelper.KEY_GROUP_BITS, outputOutcomeModel.getGroupBITS());
        cv.put(cSQLDBHelper.KEY_PERMS_BITS, outputOutcomeModel.getPermsBITS());
        cv.put(cSQLDBHelper.KEY_STATUS_BITS, outputOutcomeModel.getStatusBITS());
        cv.put(cSQLDBHelper.KEY_CREATED_DATE, formatter.format(outputOutcomeModel.getCreatedDate()));
        cv.put(cSQLDBHelper.KEY_MODIFIED_DATE, formatter.format(outputOutcomeModel.getModifiedDate()));
        cv.put(cSQLDBHelper.KEY_SYNCED_DATE, formatter.format(outputOutcomeModel.getSyncedDate()));

        // insert project details
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblOUTPUT_OUTCOME, null, cv) < 0) {
                return false;
            }
        } catch (Exception e) {
            Log.d(TAG, "Exception in adding OUTPUT_OUTCOMEs "+e.getMessage().toString());
        }

        // close the database connection
        db.close();

        return true;
    }

    /*
     * the function delete all outputs
     */
    public boolean deleteOutputs() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        try {
            if(db.delete(cSQLDBHelper.TABLE_tblOUTPUT, null, null) < 0){
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
     * the function delete all output outcomes
     */
    public boolean deleteOutputOutcomes() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        try {
            if(db.delete(cSQLDBHelper.TABLE_tblOUTPUT_OUTCOME, null, null) < 0){
                return false;
            }
        }catch (Exception e){
            Log.d(TAG, "Exception in deleting all OUTPUT_OUTCOMEs "+e.getMessage().toString());
        }

        // close the database connection
        db.close();

        return true;
    }

    /*
     * the function fetches all outputs
     */
    public ArrayList<cOutputModel> getOutputModels() {
        // list of outputs
        ArrayList<cOutputModel> outputModels = new ArrayList<>();

        // open the connection to the database
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // construct a selection query
        String selectQuery = "SELECT * FROM "+ cSQLDBHelper.TABLE_tblOUTPUT;

        // construct an argument cursor
        Cursor cursor = db.rawQuery(selectQuery, null);

        try {
            if (cursor.moveToFirst()) {
                do {
                    cOutputModel outputModel = new cOutputModel();

                    outputModel.setID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ID)));
                    outputModel.setServerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_SERVER_ID)));
                    outputModel.setOwnerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OWNER_ID)));
                    outputModel.setOrgID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ORG_ID)));
                    outputModel.setGroupBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_GROUP_BITS)));
                    outputModel.setPermsBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PERMS_BITS)));
                    outputModel.setStatusBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_STATUS_BITS)));
                    outputModel.setName(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_NAME)));
                    outputModel.setDescription(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_DESCRIPTION)));
                    outputModel.setStartDate(formatter.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_START_DATE))));
                    outputModel.setEndDate(formatter.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_END_DATE))));
                    outputModel.setCreatedDate(formatter.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_CREATED_DATE))));
                    outputModel.setModifiedDate(formatter.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_MODIFIED_DATE))));
                    outputModel.setSyncedDate(formatter.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_SYNCED_DATE))));

                    outputModels.add(outputModel);

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

        return outputModels;
    }

    // get output outcomes
    public ArrayList<cOutcomeModel> getOutputOutcomesByID(int outputID) {
        // list of child outcome
        ArrayList<cOutcomeModel> outcomeModels = new ArrayList<cOutcomeModel>();

        // open the connection to the database
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // construct a selection query
        String selectQuery = "SELECT * FROM " +
                cSQLDBHelper.TABLE_tblOUTPUT + " output, " +
                cSQLDBHelper.TABLE_tblOUTCOME + " outcome, " +
                cSQLDBHelper.TABLE_tblOUTCOME_IMPACT + " output_outcome " +
                " WHERE output."+cSQLDBHelper.KEY_ID+" = output_outcome."+cSQLDBHelper.KEY_OUTCOME_FK_ID +
                " AND output."+cSQLDBHelper.KEY_LOGFRAME_FK_ID + " = output_outcome."+cSQLDBHelper.KEY_PARENT_FK_ID +
                " AND outcome."+cSQLDBHelper.KEY_ID + " = output_outcome."+cSQLDBHelper.KEY_IMPACT_FK_ID +
                " AND outcome."+cSQLDBHelper.KEY_LOGFRAME_FK_ID +" = output_outcome."+cSQLDBHelper.KEY_CHILD_FK_ID +
                " AND output."+cSQLDBHelper.KEY_ID +" = ?";

        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(outputID)});

        try {
            if (cursor.moveToFirst()) {
                do {

                    cOutcomeModel outcomeModel = new cOutcomeModel();

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
            Log.d(TAG, "Exception in reading all OUTCOME_IMPACTs "+e.getMessage().toString());
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
