package com.me.mseotsanyana.mande.PPMER.DAL;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.lang.String;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class cOutcomeDBA {
	// an object of the database helper
	private cSQLDBHelper dbHelper;

	private static final SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
	private static final String TAG = "dbHelper";

	public cOutcomeDBA(Context context) {
		dbHelper = new cSQLDBHelper(context);
	}

    /*
     * the function adding a specific outcome
     */
    public boolean addOutcome(cOutcomeModel outcomeModel) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_ID, outcomeModel.getID());
        cv.put(cSQLDBHelper.KEY_SERVER_ID, outcomeModel.getServerID());
        cv.put(cSQLDBHelper.KEY_OWNER_ID, outcomeModel.getOwnerID());
        cv.put(cSQLDBHelper.KEY_ORG_ID, outcomeModel.getOrgID());
        cv.put(cSQLDBHelper.KEY_GROUP_BITS, outcomeModel.getGroupBITS());
        cv.put(cSQLDBHelper.KEY_PERMS_BITS, outcomeModel.getPermsBITS());
        cv.put(cSQLDBHelper.KEY_STATUS_BITS, outcomeModel.getStatusBITS());
        cv.put(cSQLDBHelper.KEY_NAME, outcomeModel.getName());
        cv.put(cSQLDBHelper.KEY_DESCRIPTION, outcomeModel.getDescription());
        cv.put(cSQLDBHelper.KEY_START_DATE, formatter.format(outcomeModel.getStartDate()));
        cv.put(cSQLDBHelper.KEY_END_DATE, formatter.format(outcomeModel.getEndDate()));
        cv.put(cSQLDBHelper.KEY_CREATED_DATE, formatter.format(outcomeModel.getCreatedDate()));
        cv.put(cSQLDBHelper.KEY_MODIFIED_DATE, formatter.format(outcomeModel.getModifiedDate()));
        cv.put(cSQLDBHelper.KEY_SYNCED_DATE, formatter.format(outcomeModel.getSyncedDate()));

        // insert project details
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblOUTCOME, null, cv) < 0) {
                return false;
            }
        } catch (Exception e) {
            Log.d(TAG, "Exception in adding OUTCOME "+e.getMessage().toString());
        }

        // close the database connection
        db.close();

        return true;
    }

    /*
     * the function adding a specific outcome impact
     */
    public boolean addOutcomeImpact(cOutcomeModel.cOutcomeImpactModel outcomeImpactModel){
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_OUTCOME_FK_ID, outcomeImpactModel.getImpactID());
        cv.put(cSQLDBHelper.KEY_IMPACT_FK_ID, outcomeImpactModel.getImpactID());
        cv.put(cSQLDBHelper.KEY_PARENT_FK_ID, outcomeImpactModel.getParentID());
        cv.put(cSQLDBHelper.KEY_CHILD_FK_ID, outcomeImpactModel.getChildID());
        cv.put(cSQLDBHelper.KEY_SERVER_ID, outcomeImpactModel.getServerID());
        cv.put(cSQLDBHelper.KEY_OWNER_ID, outcomeImpactModel.getOwnerID());
        cv.put(cSQLDBHelper.KEY_ORG_ID, outcomeImpactModel.getOrgID());
        cv.put(cSQLDBHelper.KEY_GROUP_BITS, outcomeImpactModel.getGroupBITS());
        cv.put(cSQLDBHelper.KEY_PERMS_BITS, outcomeImpactModel.getPermsBITS());
        cv.put(cSQLDBHelper.KEY_STATUS_BITS, outcomeImpactModel.getStatusBITS());
        cv.put(cSQLDBHelper.KEY_CREATED_DATE, formatter.format(outcomeImpactModel.getCreatedDate()));
        cv.put(cSQLDBHelper.KEY_MODIFIED_DATE, formatter.format(outcomeImpactModel.getModifiedDate()));
        cv.put(cSQLDBHelper.KEY_SYNCED_DATE, formatter.format(outcomeImpactModel.getSyncedDate()));

        // insert project details
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblOUTCOME_IMPACT, null, cv) < 0) {
                return false;
            }
        } catch (Exception e) {
            Log.d(TAG, "Exception in adding OUTCOME_IMPACTs "+e.getMessage().toString());
        }

        // close the database connection
        db.close();

        return true;
    }

    /*
     * the function delete all outcomes
     */
    public boolean deleteOutcomes() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        try {
            if(db.delete(cSQLDBHelper.TABLE_tblOUTCOME, null, null) < 0){
                return false;
            }
        }catch (Exception e){
            Log.d(TAG, "Exception in deleting all OUTCOMEs "+e.getMessage().toString());
        }

        // close the database connection
        db.close();

        return true;
    }

    /*
     * the function delete all outcome impacts
     */
    public boolean deleteOutcomeImpacts() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        try {
            if(db.delete(cSQLDBHelper.TABLE_tblOUTCOME_IMPACT, null, null) < 0){
                return false;
            }
        }catch (Exception e){
            Log.d(TAG, "Exception in deleting all OUTCOME_IMPACTs "+e.getMessage().toString());
        }

        // close the database connection
        db.close();

        return true;
    }

    /*
     * the function fetches all outcomes
     */
    public ArrayList<cOutcomeModel> getOutcomeModels() {
        // list of outcomes
        ArrayList<cOutcomeModel> outcomeModels = new ArrayList<>();

        // open the connection to the database
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // construct a selection query
        String selectQuery = "SELECT * FROM "+ cSQLDBHelper.TABLE_tblOUTCOME;

        // construct an argument cursor
        Cursor cursor = db.rawQuery(selectQuery, null);

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
            Log.d(TAG, "Exception in reading all OUTCOMEs "+e.getMessage().toString());
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        // close the database connection
        db.close();

        return outcomeModels;
    }

    // get outcome impacts
    public ArrayList<cImpactModel> getOutcomeImpactsByID(int outcomeID) {
        // list of child impact
        ArrayList<cImpactModel> impactModels = new ArrayList<cImpactModel>();

        // open the connection to the database
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // construct a selection query
        String selectQuery = "SELECT * FROM " +
                cSQLDBHelper.TABLE_tblOUTCOME + " outcome, " +
                cSQLDBHelper.TABLE_tblIMPACT + " impact, " +
                cSQLDBHelper.TABLE_tblOUTCOME_IMPACT + " outcome_impact " +
                " WHERE outcome."+cSQLDBHelper.KEY_ID+" = outcome_impact."+cSQLDBHelper.KEY_OUTCOME_FK_ID +
                " AND outcome."+cSQLDBHelper.KEY_LOGFRAME_FK_ID + " = outcome_impact."+cSQLDBHelper.KEY_PARENT_FK_ID +
                " AND impact."+cSQLDBHelper.KEY_ID + " = outcome_impact."+cSQLDBHelper.KEY_IMPACT_FK_ID +
                " AND impact."+cSQLDBHelper.KEY_LOGFRAME_FK_ID +" = outcome_impact."+cSQLDBHelper.KEY_CHILD_FK_ID +
                " AND outcome."+cSQLDBHelper.KEY_ID +" = ?";

        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(outcomeID)});

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
            Log.d(TAG, "Exception in reading all OUTCOME_IMPACTs "+e.getMessage().toString());
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        // close the database connection
        db.close();

        return impactModels;
    }


    //=============================================================================================

    // get outcomes for a given outcome ID
    public cOutcomeModel getOutcomeByID(int outcomeID) {
        // list of outcomes
        cOutcomeModel outcomeModel = new cOutcomeModel();

        // open the connection to the database
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // construct a selection query
        String selectQuery = "SELECT * FROM "+
                cSQLDBHelper.TABLE_tblOUTCOME + " outcome "+
                " WHERE outcome."+cSQLDBHelper.KEY_ID + " = ?";

        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(outcomeID)});

        try {
            if (cursor.moveToFirst()) {
                do {

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

        return outcomeModel;
    }


    /*
     * the function fetches all outcomes
     */
    public ArrayList<cOutcomeModel.cOutcomeImpactModel> getOutcomeImpactModels() {
        // list of outcomes
        ArrayList<cOutcomeModel.cOutcomeImpactModel> outcomeImpactModels = new ArrayList<>();

        // open the connection to the database
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // construct a selection query
        String selectQuery = "SELECT * FROM "+ cSQLDBHelper.TABLE_tblOUTCOME_IMPACT;

        // construct an argument cursor
        Cursor cursor = db.rawQuery(selectQuery, null);

        try {
            if (cursor.moveToFirst()) {
                do {
                    cOutcomeModel.cOutcomeImpactModel outcomeImpactModel = new cOutcomeModel.cOutcomeImpactModel();

                    outcomeImpactModel.setOutcomeID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OUTCOME_FK_ID)));
                    outcomeImpactModel.setImpactID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_IMPACT_FK_ID)));
                    outcomeImpactModel.setParentID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PARENT_FK_ID)));
                    outcomeImpactModel.setChildID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_CHILD_FK_ID)));
                    outcomeImpactModel.setServerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_SERVER_ID)));
                    outcomeImpactModel.setOwnerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OWNER_ID)));
                    outcomeImpactModel.setOrgID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ORG_ID)));
                    outcomeImpactModel.setGroupBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_GROUP_BITS)));
                    outcomeImpactModel.setPermsBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PERMS_BITS)));
                    outcomeImpactModel.setStatusBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_STATUS_BITS)));
                    outcomeImpactModel.setCreatedDate(formatter.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_CREATED_DATE))));
                    outcomeImpactModel.setModifiedDate(formatter.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_MODIFIED_DATE))));
                    outcomeImpactModel.setSyncedDate(formatter.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_SYNCED_DATE))));

                    outcomeImpactModels.add(outcomeImpactModel);

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

        return outcomeImpactModels;
    }

    /*
     * the function delete a specific outcome
     */
    public boolean deleteOutcome(cOutcomeModel outcomeModel) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete a record of a specific ID
        try {
            if(db.delete(cSQLDBHelper.TABLE_tblIMPACT, cSQLDBHelper.KEY_ID + " = ?",
                    new String[]{String.valueOf(outcomeModel.getID())}) < 0){
                return false;
            }
        }catch (Exception e){
            Log.d(TAG, "Exception in deleting OUTCOME "+e.getMessage().toString());
        }

        // close the database connection
        db.close();

        return true;
    }

    /*
     * the function delete a specific outcome impact
     */
    public boolean deleteOutcomeImpact(cOutcomeModel.cOutcomeImpactModel outcomeImpactModel) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete a record of a specific ID
        try {
            if(db.delete(cSQLDBHelper.TABLE_tblOUTCOME_IMPACT, cSQLDBHelper.KEY_OUTCOME_FK_ID + " = ?" +
                            " AND "+cSQLDBHelper.KEY_IMPACT_FK_ID + " = ? AND "+cSQLDBHelper.KEY_PARENT_FK_ID + " = ?" +
                            " AND "+cSQLDBHelper.KEY_CHILD_FK_ID +" = ?",
                    new String[]{String.valueOf(outcomeImpactModel.getOutcomeID()),
                            String.valueOf(outcomeImpactModel.getImpactID()),
                            String.valueOf(outcomeImpactModel.getParentID()),
                            String.valueOf(outcomeImpactModel.getChildID())}) < 0){
                return false;
            }
        }catch (Exception e){
            Log.d(TAG, "Exception in deleting OUTCOME_IMPACT "+e.getMessage().toString());
        }

        // close the database connection
        db.close();

        return true;
    }
}
