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
    public boolean addLogFrame(cLogFrameModel logFrameModel) {
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
            Log.d(TAG, "Exception in importing "+e.getMessage().toString());
        }

        // close the database connection
        db.close();

        return true;
    }

    /*
     * the function delate a specific logframe
     */
    public boolean deleteLogFrame(cLogFrameModel logFrameModel) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete a record of a specific ID
        try {
            if(db.delete(cSQLDBHelper.TABLE_tblLOGFRAME, cSQLDBHelper.KEY_ID + " = ?",
                    new String[]{String.valueOf(logFrameModel.getID())}) < 0){
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
     * the function delete all logFrames
     */
    public boolean deleteLogFrames() {
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
     * the function fetches all logFrames
    */
    public ArrayList<cLogFrameModel> getLogFrameModels() {
        // list of logFrames
        ArrayList<cLogFrameModel> logFrameModels = new ArrayList<>();

        // open the connection to the database
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
        // list of child logFrames
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

    // get impacts for a given logFrame
    public ArrayList<cImpactModel> getImpactsByID(int logFrameID) {
        // list of impacts
        ArrayList<cImpactModel> impactModels = new ArrayList<>();

        // open the connection to the database
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // construct a selection query
        String selectQuery = "SELECT * FROM "+
                cSQLDBHelper.TABLE_tblIMPACT + " impact "+
                " WHERE impact."+cSQLDBHelper.KEY_ID + " = ?";

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

    // get outcomes for a given logFrame
    public ArrayList<cOutcomeModel> getOutcomesByID(int logFrameID) {
        // list of outcomes
        ArrayList<cOutcomeModel> outcomeModels = new ArrayList<>();

        // open the connection to the database
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // construct a selection query
        String selectQuery = "SELECT * FROM "+
                cSQLDBHelper.TABLE_tblOUTCOME + " outcome "+
                " WHERE outcome."+cSQLDBHelper.KEY_ID + " = ?";

        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(logFrameID)});

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
            Log.d(TAG, "Error while trying to get outcomes from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        // close the database connection
        db.close();

        return outcomeModels;
    }

    // get outputs for a given logFrame
    public ArrayList<cOutputModel> getOutputsByID(int logFrameID) {
        // list of outputs
        ArrayList<cOutputModel> outputModels = new ArrayList<>();

        // open the connection to the database
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // construct a selection query
        String selectQuery = "SELECT * FROM "+
                cSQLDBHelper.TABLE_tblOUTPUT + " output "+
                " WHERE output."+cSQLDBHelper.KEY_ID + " = ?";

        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(logFrameID)});

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
            Log.d(TAG, "Error while trying to get outputs from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        // close the database connection
        db.close();

        return outputModels;
    }

    // get activities for a given logFrame
    public ArrayList<cActivityModel> getActivitiesByID(int logFrameID) {
        // list of activities
        ArrayList<cActivityModel> activityModels = new ArrayList<>();

        // open the connection to the database
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // construct a selection query
        String selectQuery = "SELECT * FROM "+
                cSQLDBHelper.TABLE_tblACTIVITY + " activity "+
                " WHERE activity."+cSQLDBHelper.KEY_ID + " = ?";

        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(logFrameID)});

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
            Log.d(TAG, "Error while trying to get activities from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        // close the database connection
        db.close();

        return activityModels;
    }

    // get inputs for a given logFrame
    public ArrayList<cInputModel> getInputsByID(int logFrameID) {
        // list of inputs
        ArrayList<cInputModel> inputModels = new ArrayList<>();

        // open the connection to the database
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // construct a selection query
        String selectQuery = "SELECT * FROM "+
                cSQLDBHelper.TABLE_tblINPUT + " input "+
                " WHERE input."+cSQLDBHelper.KEY_ID + " = ?";

        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(logFrameID)});

        try {
            if (cursor.moveToFirst()) {
                do {
                    cInputModel inputModel = new cInputModel();

                    inputModel.setID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ID)));
                    inputModel.setServerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_SERVER_ID)));
                    inputModel.setOwnerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OWNER_ID)));
                    inputModel.setOrgID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ORG_ID)));
                    inputModel.setGroupBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_GROUP_BITS)));
                    inputModel.setPermsBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PERMS_BITS)));
                    inputModel.setStatusBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_STATUS_BITS)));
                    inputModel.setName(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_NAME)));
                    inputModel.setDescription(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_DESCRIPTION)));
                    inputModel.setStartDate(formatter.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_START_DATE))));
                    inputModel.setEndDate(formatter.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_END_DATE))));
                    inputModel.setCreatedDate(formatter.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_CREATED_DATE))));
                    inputModel.setModifiedDate(formatter.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_MODIFIED_DATE))));
                    inputModel.setSyncedDate(formatter.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_SYNCED_DATE))));

                    inputModels.add(inputModel);

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to get inputs from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        // close the database connection
        db.close();

        return inputModels;
    }

    // get questions for a given logFrame
    public ArrayList<cQuestionModel> getQuestionsByID(int logFrameID) {
        // list of questions
        ArrayList<cQuestionModel> questionModels = new ArrayList<>();

        // open the connection to the database
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // construct a selection query
        String selectQuery = "SELECT * FROM "+
                cSQLDBHelper.TABLE_tblQUESTION + " question "+
                " WHERE question."+cSQLDBHelper.KEY_ID + " = ?";

        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(logFrameID)});

        try {
            if (cursor.moveToFirst()) {
                do {
                    cQuestionModel questionModel = new cQuestionModel();

                    questionModel.setID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ID)));
                    questionModel.setServerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_SERVER_ID)));
                    questionModel.setOwnerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OWNER_ID)));
                    questionModel.setOrgID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ORG_ID)));
                    questionModel.setGroupBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_GROUP_BITS)));
                    questionModel.setPermsBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PERMS_BITS)));
                    questionModel.setStatusBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_STATUS_BITS)));
                    questionModel.setName(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_NAME)));
                    questionModel.setDescription(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_DESCRIPTION)));
                    questionModel.setStartDate(formatter.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_START_DATE))));
                    questionModel.setEndDate(formatter.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_END_DATE))));
                    questionModel.setCreatedDate(formatter.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_CREATED_DATE))));
                    questionModel.setModifiedDate(formatter.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_MODIFIED_DATE))));
                    questionModel.setSyncedDate(formatter.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_SYNCED_DATE))));

                    questionModels.add(questionModel);

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to get questions from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        // close the database connection
        db.close();

        return questionModels;
    }

    // get indicators for a given logFrame
    public ArrayList<cIndicatorModel> getIndicatorsByID(int logFrameID) {
        // list of indicators
        ArrayList<cIndicatorModel> indicatorModels = new ArrayList<>();

        // open the connection to the database
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // construct a selection query
        String selectQuery = "SELECT * FROM "+
                cSQLDBHelper.TABLE_tblINPUT + " indicator "+
                " WHERE indicator."+cSQLDBHelper.KEY_ID + " = ?";

        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(logFrameID)});

        try {
            if (cursor.moveToFirst()) {
                do {
                    cIndicatorModel indicatorModel = new cIndicatorModel();

                    indicatorModel.setID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ID)));
                    indicatorModel.setServerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_SERVER_ID)));
                    indicatorModel.setOwnerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OWNER_ID)));
                    indicatorModel.setOrgID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ORG_ID)));
                    indicatorModel.setGroupBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_GROUP_BITS)));
                    indicatorModel.setPermsBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PERMS_BITS)));
                    indicatorModel.setStatusBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_STATUS_BITS)));
                    indicatorModel.setName(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_NAME)));
                    indicatorModel.setDescription(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_DESCRIPTION)));
                    indicatorModel.setStartDate(formatter.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_START_DATE))));
                    indicatorModel.setEndDate(formatter.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_END_DATE))));
                    indicatorModel.setCreatedDate(formatter.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_CREATED_DATE))));
                    indicatorModel.setModifiedDate(formatter.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_MODIFIED_DATE))));
                    indicatorModel.setSyncedDate(formatter.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_SYNCED_DATE))));

                    indicatorModels.add(indicatorModel);

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to get indicators from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        // close the database connection
        db.close();

        return indicatorModels;
    }

    // get raids for a given logFrame
    public ArrayList<cRaidModel> getRaidsByID(int logFrameID) {
        // list of raids
        ArrayList<cRaidModel> raidModels = new ArrayList<>();

        // open the connection to the database
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // construct a selection query
        String selectQuery = "SELECT * FROM "+
                cSQLDBHelper.TABLE_tblRAID + " raid "+
                " WHERE raid."+cSQLDBHelper.KEY_ID + " = ?";

        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(logFrameID)});

        try {
            if (cursor.moveToFirst()) {
                do {
                    cRaidModel raidModel = new cRaidModel();

                    raidModel.setID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ID)));
                    raidModel.setServerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_SERVER_ID)));
                    raidModel.setOwnerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OWNER_ID)));
                    raidModel.setOrgID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ORG_ID)));
                    raidModel.setGroupBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_GROUP_BITS)));
                    raidModel.setPermsBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PERMS_BITS)));
                    raidModel.setStatusBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_STATUS_BITS)));
                    raidModel.setName(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_NAME)));
                    raidModel.setDescription(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_DESCRIPTION)));
                    raidModel.setStartDate(formatter.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_START_DATE))));
                    raidModel.setEndDate(formatter.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_END_DATE))));
                    raidModel.setCreatedDate(formatter.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_CREATED_DATE))));
                    raidModel.setModifiedDate(formatter.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_MODIFIED_DATE))));
                    raidModel.setSyncedDate(formatter.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_SYNCED_DATE))));

                    raidModels.add(raidModel);

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to get raids from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        // close the database connection
        db.close();

        return raidModels;
    }
}

/*
    String selectQuery = "SELECT * FROM "+
            cSQLDBHelper.TABLE_tblIMPACT + " impact " +
            " WHERE logframe."+cSQLDBHelper.KEY_ID + " = logframe_impact."+cSQLDBHelper.KEY_LOGFRAME_FK_ID +
            " AND impact."+cSQLDBHelper.KEY_ID + " = logframe_impact."+cSQLDBHelper.KEY_IMPACT_FK_ID +
            " AND logframe."+cSQLDBHelper.KEY_ID + " = ?";
*/