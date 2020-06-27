package com.me.mseotsanyana.mande.DAL.ìmpl.logframe;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.me.mseotsanyana.mande.BLL.repository.logframe.iOutputRepository;
import com.me.mseotsanyana.mande.DAL.model.logframe.cActivityModel;
import com.me.mseotsanyana.mande.DAL.model.logframe.cLogFrameModel;
import com.me.mseotsanyana.mande.DAL.model.logframe.cOutcomeModel;
import com.me.mseotsanyana.mande.DAL.model.logframe.cOutputModel;
import com.me.mseotsanyana.mande.DAL.model.logframe.cQuestionModel;
import com.me.mseotsanyana.mande.DAL.model.logframe.cRaidModel;
import com.me.mseotsanyana.mande.DAL.storage.database.cSQLDBHelper;
import com.me.mseotsanyana.mande.DAL.storage.preference.cBitwise;
import com.me.mseotsanyana.mande.UTIL.cConstant;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class cOutputRepositoryImpl implements iOutputRepository {
    private static SimpleDateFormat sdf = cConstant.FORMAT_DATE;
    private static String TAG = cOutputRepositoryImpl.class.getSimpleName();

    // an object of the database helper
    private cSQLDBHelper dbHelper;

    public cOutputRepositoryImpl(Context context) {
        dbHelper = new cSQLDBHelper(context);
    }

    /* ######################################## CREATE ACTIONS ########################################*/

    /*
     * the function adding a specific output
     */
    public boolean addOutput(cOutputModel outputModel) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_ID, outputModel.getOutputID());
        cv.put(cSQLDBHelper.KEY_SERVER_ID, outputModel.getServerID());
        cv.put(cSQLDBHelper.KEY_OWNER_ID, outputModel.getOwnerID());
        cv.put(cSQLDBHelper.KEY_ORG_ID, outputModel.getOrgID());
        cv.put(cSQLDBHelper.KEY_GROUP_BITS, outputModel.getGroupBITS());
        cv.put(cSQLDBHelper.KEY_PERMS_BITS, outputModel.getPermsBITS());
        cv.put(cSQLDBHelper.KEY_STATUS_BITS, outputModel.getStatusBITS());
        cv.put(cSQLDBHelper.KEY_NAME, outputModel.getName());
        cv.put(cSQLDBHelper.KEY_DESCRIPTION, outputModel.getDescription());
        cv.put(cSQLDBHelper.KEY_START_DATE, sdf.format(outputModel.getStartDate()));
        cv.put(cSQLDBHelper.KEY_END_DATE, sdf.format(outputModel.getEndDate()));
        cv.put(cSQLDBHelper.KEY_CREATED_DATE, sdf.format(outputModel.getCreatedDate()));
        cv.put(cSQLDBHelper.KEY_MODIFIED_DATE, sdf.format(outputModel.getModifiedDate()));
        cv.put(cSQLDBHelper.KEY_SYNCED_DATE, sdf.format(outputModel.getSyncedDate()));

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

    /* ######################################## READ ACTIONS ########################################*/

    /*
     * the function fetches all outputs
     */
    public Set<cOutputModel> getOutputModelSet(long logFrameID, int userID, int primaryRoleBITS,
                                                 int secondaryRoleBITS, int statusBITS) {
        // list of outputs
        Set<cOutputModel> outputModelSet = new HashSet<>();

        // open the connection to the database
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + cSQLDBHelper.TABLE_tblOUTPUT +
                " WHERE ((" + cSQLDBHelper.KEY_LOGFRAME_FK_ID + " = ?) AND " +
                /* read access permissions */
                /* organization creator is always allowed to do everything (i.e., where: userID = orgID)*/
                " ((" + cSQLDBHelper.KEY_ORG_ID + " = ?) " +
                /* owner permission */
                " OR ((((" + cSQLDBHelper.KEY_OWNER_ID + " = ?) " +
                " AND ((" + cSQLDBHelper.KEY_PERMS_BITS + " & " + cBitwise.OWNER_READ + ") != 0))" +
                /* group (owner/primary organization) permission */
                " OR (((" + cSQLDBHelper.KEY_GROUP_BITS + " & ?) != 0) " +
                " AND ((" + cSQLDBHelper.KEY_PERMS_BITS + " & " + cBitwise.GROUP_READ + ") != 0))" +
                /* other (secondary organizations) permission */
                " OR (((" + cSQLDBHelper.KEY_GROUP_BITS + " & ?) != 0) " +
                " AND ((" + cSQLDBHelper.KEY_PERMS_BITS + " & " + cBitwise.OTHER_READ + ") != 0)))" +
                /* owner, group and other permissions allowed when the statuses hold */
                " AND ((" + cSQLDBHelper.KEY_STATUS_BITS + " = 0) " +
                " OR ((" + cSQLDBHelper.KEY_STATUS_BITS + " & ?) != 0)))))";

        Cursor cursor = db.rawQuery(selectQuery, new String[]{
                String.valueOf(logFrameID),       /* access due to logframe foreign key */
                String.valueOf(userID),           /* access due to organization creator */
                String.valueOf(userID),           /* access due to record owner */
                String.valueOf(primaryRoleBITS),  /* access due to membership in primary role */
                String.valueOf(secondaryRoleBITS),/* access due to membership in secondary role */
                String.valueOf(statusBITS)});     /* access due to assigned statuses */
        try {
            if (cursor.moveToFirst()) {
                do {
                    cOutputModel output = new cOutputModel();

                    output.setOutputID(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ID)));
                    output.setParentID(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PARENT_FK_ID)));
                    output.setLogFrameID(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_LOGFRAME_FK_ID)));
                    output.setOutcomeID(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OUTCOME_FK_ID)));
                    output.setServerID(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_SERVER_ID)));
                    output.setOwnerID(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OWNER_ID)));
                    output.setOrgID(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ORG_ID)));
                    output.setGroupBITS(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_GROUP_BITS)));
                    output.setPermsBITS(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PERMS_BITS)));
                    output.setStatusBITS(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_STATUS_BITS)));
                    output.setName(
                            cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_NAME)));
                    output.setDescription(
                            cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_DESCRIPTION)));
                    output.setStartDate(Timestamp.valueOf(
                            cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_START_DATE))));
                    output.setEndDate(Timestamp.valueOf(
                            cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_END_DATE))));
                    output.setCreatedDate(Timestamp.valueOf(
                            cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_CREATED_DATE))));
                    output.setModifiedDate(Timestamp.valueOf(
                            cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_MODIFIED_DATE))));
                    output.setSyncedDate(Timestamp.valueOf(
                            cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_SYNCED_DATE))));

                    /* objects */

                    /* populate a logframe object */
                    output.setLogFrameModel(getLogFrameModelByID(output.getLogFrameID(), userID,
                            primaryRoleBITS, secondaryRoleBITS, statusBITS));
                    /* populate an outcome object */
                    output.setOutcomeModel(getOutcomeModelByID(output.getOutcomeID(), userID,
                            primaryRoleBITS, secondaryRoleBITS, statusBITS));

                    /* sets */

                    /* populate child output components */
                    output.setChildrenOutputModelSet(getOutputModelSetByID(output.getOutputID(),
                            userID, primaryRoleBITS, secondaryRoleBITS, statusBITS));
                    /* populate activity components */
                    output.setActivityModelSet(getActivityModelSetByID(output.getOutputID(), userID,
                            primaryRoleBITS, secondaryRoleBITS, statusBITS));
                    /* populate question components */
                    output.setQuestionModelSet(getQuestionModelSetByID(output.getOutputID(),
                            userID, primaryRoleBITS, secondaryRoleBITS, statusBITS));
                    /* populate raid components */
                    output.setRaidModelSet(getRaidModelSetByID(output.getOutputID(), userID,
                            primaryRoleBITS, secondaryRoleBITS, statusBITS));

                    /* maps */

                    /* populate child impacts for the outcome */
                    output.setChildOutcomeModelSet(getChildOutcomeSetByID(output.getOutcomeID(),
                            userID, primaryRoleBITS, secondaryRoleBITS, statusBITS));

                    /* add outcome entity */
                    outputModelSet.add(output);

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error in reading all OUTCOME entities: " + e.getMessage());
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        // close the database connection
        db.close();

        return outputModelSet;
    }

    private cLogFrameModel getLogFrameModelByID(int logFrameID, int userID, int primaryRoleBITS,
                                                int secondaryRoleBITS, int statusBITS) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // construct a selection query
        String selectQuery = "SELECT * FROM " + cSQLDBHelper.TABLE_tblLOGFRAME +
                " WHERE ((" + cSQLDBHelper.KEY_ID + "= ? ) AND " +
                /* read access permissions */
                /* organization creator is always allowed to do everything (i.e., where: userID = orgID)*/
                " ((" + cSQLDBHelper.KEY_ORG_ID + " = ?) " +
                /* owner permission */
                " OR ((((" + cSQLDBHelper.KEY_OWNER_ID + " = ?) " +
                " AND ((" + cSQLDBHelper.KEY_PERMS_BITS + " & " + cBitwise.OWNER_READ + ") != 0))" +
                /* group (owner/primary organization) permission */
                " OR (((" + cSQLDBHelper.KEY_GROUP_BITS + " & ?) != 0) " +
                " AND ((" + cSQLDBHelper.KEY_PERMS_BITS + " & " + cBitwise.GROUP_READ + ") != 0))" +
                /* other (secondary organizations) permission */
                " OR (((" + cSQLDBHelper.KEY_GROUP_BITS + " & ?) != 0) " +
                " AND ((" + cSQLDBHelper.KEY_PERMS_BITS + " & " + cBitwise.OTHER_READ + ") != 0)))" +
                /* owner, group and other permissions allowed when the statuses hold */
                " AND ((" + cSQLDBHelper.KEY_STATUS_BITS + " = 0) " +
                " OR ((" + cSQLDBHelper.KEY_STATUS_BITS + " & ?) != 0)))))";

        Cursor cursor = db.rawQuery(selectQuery, new String[]{
                String.valueOf(logFrameID),       /* access due to logframe foreign key */
                String.valueOf(userID),           /* access due to organization creator */
                String.valueOf(userID),           /* access due to record owner */
                String.valueOf(primaryRoleBITS),  /* access due to membership in primary role */
                String.valueOf(secondaryRoleBITS),/* access due to membership in secondary role */
                String.valueOf(statusBITS)});     /* access due to assigned statuses */

        cLogFrameModel logFrame = new cLogFrameModel();

        try {
            if (cursor.moveToFirst()) {
                logFrame.setLogFrameID(
                        cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ID)));
                logFrame.setServerID(
                        cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_SERVER_ID)));
                logFrame.setOwnerID(
                        cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OWNER_ID)));
                logFrame.setOrgID(
                        cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ORG_ID)));
                logFrame.setGroupBITS(
                        cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_GROUP_BITS)));
                logFrame.setPermsBITS(
                        cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PERMS_BITS)));
                logFrame.setStatusBITS(
                        cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_STATUS_BITS)));
                logFrame.setName(
                        cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_NAME)));
                logFrame.setDescription(
                        cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_DESCRIPTION)));
                logFrame.setStartDate(Timestamp.valueOf(
                        cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_START_DATE))));
                logFrame.setEndDate(Timestamp.valueOf(
                        cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_END_DATE))));
                logFrame.setCreatedDate(Timestamp.valueOf(
                        cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_CREATED_DATE))));
                logFrame.setModifiedDate(Timestamp.valueOf(
                        cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_MODIFIED_DATE))));
                logFrame.setSyncedDate(Timestamp.valueOf(
                        cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_SYNCED_DATE))));
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to read logFrame entity: " + e.getMessage());
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        // close the database connection
        db.close();

        return logFrame;
    }

    private cOutcomeModel getOutcomeModelByID(int outcomeID, int userID, int primaryRoleBITS,
                                            int secondaryRoleBITS, int statusBITS) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        // construct a selection query
        String selectQuery = "SELECT * FROM " + cSQLDBHelper.TABLE_tblOUTCOME +
                " WHERE ((" + cSQLDBHelper.KEY_ID + "= ? ) AND " +
                /* read access permissions */
                /* organization creator is always allowed to do everything (i.e., where: userID = orgID)*/
                " ((" + cSQLDBHelper.KEY_ORG_ID + " = ?) " +
                /* owner permission */
                " OR ((((" + cSQLDBHelper.KEY_OWNER_ID + " = ?) " +
                " AND ((" + cSQLDBHelper.KEY_PERMS_BITS + " & " + cBitwise.OWNER_READ + ") != 0))" +
                /* group (owner/primary organization) permission */
                " OR (((" + cSQLDBHelper.KEY_GROUP_BITS + " & ?) != 0) " +
                " AND ((" + cSQLDBHelper.KEY_PERMS_BITS + " & " + cBitwise.GROUP_READ + ") != 0))" +
                /* other (secondary organizations) permission */
                " OR (((" + cSQLDBHelper.KEY_GROUP_BITS + " & ?) != 0) " +
                " AND ((" + cSQLDBHelper.KEY_PERMS_BITS + " & " + cBitwise.OTHER_READ + ") != 0)))" +
                /* owner, group and other permissions allowed when the statuses hold */
                " AND ((" + cSQLDBHelper.KEY_STATUS_BITS + " = 0) " +
                " OR ((" + cSQLDBHelper.KEY_STATUS_BITS + " & ?) != 0)))))";

        Cursor cursor = db.rawQuery(selectQuery, new String[]{
                String.valueOf(outcomeID),
                String.valueOf(userID),           /* access due to organization creator */
                String.valueOf(userID),           /* access due to record owner */
                String.valueOf(primaryRoleBITS),  /* access due to membership in primary role */
                String.valueOf(secondaryRoleBITS),/* access due to membership in secondary role */
                String.valueOf(statusBITS)});     /* access due to assigned statuses */

        cOutcomeModel outcome = new cOutcomeModel();

        try {
            if (cursor.moveToFirst()) {
                outcome.setOutcomeID(
                        cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ID)));
                outcome.setParentID(
                        cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PARENT_FK_ID)));
                outcome.setLogFrameID(
                        cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_LOGFRAME_FK_ID)));
                outcome.setServerID(
                        cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_SERVER_ID)));
                outcome.setOwnerID(
                        cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OWNER_ID)));
                outcome.setOrgID(
                        cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ORG_ID)));
                outcome.setGroupBITS(
                        cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_GROUP_BITS)));
                outcome.setPermsBITS(
                        cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PERMS_BITS)));
                outcome.setStatusBITS(
                        cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_STATUS_BITS)));
                outcome.setName(
                        cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_NAME)));
                outcome.setDescription(
                        cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_DESCRIPTION)));
                outcome.setStartDate(Timestamp.valueOf(
                        cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_START_DATE))));
                outcome.setEndDate(Timestamp.valueOf(
                        cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_END_DATE))));
                outcome.setCreatedDate(Timestamp.valueOf(
                        cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_CREATED_DATE))));
                outcome.setModifiedDate(Timestamp.valueOf(
                        cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_MODIFIED_DATE))));
                outcome.setSyncedDate(Timestamp.valueOf(
                        cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_SYNCED_DATE))));
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to read an OUTCOME entity: " + e.getMessage());
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        // close the database connection
        db.close();

        return outcome;
    }

    private Set<cOutputModel> getOutputModelSetByID(int outputID, int userID, int primaryRoleBITS,
                                                      int secondaryRoleBITS, int statusBITS) {

        Set<cOutputModel> outputModelSet = new HashSet<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + cSQLDBHelper.TABLE_tblOUTPUT +
                " WHERE ((" + cSQLDBHelper.KEY_PARENT_FK_ID + " = ?) AND " +
                /* read access permissions */
                /* organization creator is always allowed to do everything (i.e., where: userID = orgID)*/
                " ((" + cSQLDBHelper.KEY_ORG_ID + " = ?) " +
                /* owner permission */
                " OR ((((" + cSQLDBHelper.KEY_OWNER_ID + " = ?) " +
                " AND ((" + cSQLDBHelper.KEY_PERMS_BITS + " & " + cBitwise.OWNER_READ + ") != 0))" +
                /* group (owner/primary organization) permission */
                " OR (((" + cSQLDBHelper.KEY_GROUP_BITS + " & ?) != 0) " +
                " AND ((" + cSQLDBHelper.KEY_PERMS_BITS + " & " + cBitwise.GROUP_READ + ") != 0))" +
                /* other (secondary organizations) permission */
                " OR (((" + cSQLDBHelper.KEY_GROUP_BITS + " & ?) != 0) " +
                " AND ((" + cSQLDBHelper.KEY_PERMS_BITS + " & " + cBitwise.OTHER_READ + ") != 0)))" +
                /* owner, group and other permissions allowed when the statuses hold */
                " AND ((" + cSQLDBHelper.KEY_STATUS_BITS + " = 0) " +
                " OR ((" + cSQLDBHelper.KEY_STATUS_BITS + " & ?) != 0)))))";

        Cursor cursor = db.rawQuery(selectQuery, new String[]{
                String.valueOf(outputID),         /* access due to being child output */
                String.valueOf(userID),           /* access due to organization creator */
                String.valueOf(userID),           /* access due to record owner */
                String.valueOf(primaryRoleBITS),  /* access due to membership in primary role */
                String.valueOf(secondaryRoleBITS),/* access due to membership in secondary role */
                String.valueOf(statusBITS)});     /* access due to assigned statuses */

        try {
            if (cursor.moveToFirst()) {
                do {
                    cOutputModel output = new cOutputModel();

                    output.setOutputID(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ID)));
                    output.setParentID(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PARENT_FK_ID)));
                    output.setLogFrameID(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_LOGFRAME_FK_ID)));
                    output.setOutcomeID(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OUTCOME_FK_ID)));
                    output.setServerID(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_SERVER_ID)));
                    output.setOwnerID(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OWNER_ID)));
                    output.setOrgID(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ORG_ID)));
                    output.setGroupBITS(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_GROUP_BITS)));
                    output.setPermsBITS(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PERMS_BITS)));
                    output.setStatusBITS(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_STATUS_BITS)));
                    output.setName(
                            cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_NAME)));
                    output.setDescription(
                            cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_DESCRIPTION)));
                    output.setStartDate(Timestamp.valueOf(
                            cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_START_DATE))));
                    output.setEndDate(Timestamp.valueOf(
                            cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_END_DATE))));
                    output.setCreatedDate(Timestamp.valueOf(
                            cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_CREATED_DATE))));
                    output.setModifiedDate(Timestamp.valueOf(
                            cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_MODIFIED_DATE))));
                    output.setSyncedDate(Timestamp.valueOf(
                            cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_SYNCED_DATE))));

                    outputModelSet.add(output);

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while reading a SUB OUTPUT entity: " + e.getMessage());
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        // close the database connection
        db.close();

        return outputModelSet;
    }


    private Set<cActivityModel> getActivityModelSetByID(long outputID, long userID, int primaryRoleBITS,
                                                      int secondaryRoleBITS, int statusBITS) {

        Set<cActivityModel> activityModelSet = new HashSet<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + cSQLDBHelper.TABLE_tblACTIVITY +
                " WHERE ((" + cSQLDBHelper.KEY_OUTPUT_FK_ID + " = ?) AND " +
                /* read access permissions */
                /* organization creator is always allowed to do everything (i.e., where: userID = orgID)*/
                " ((" + cSQLDBHelper.KEY_ORG_ID + " = ?) " +
                /* owner permission */
                " OR ((((" + cSQLDBHelper.KEY_OWNER_ID + " = ?) " +
                " AND ((" + cSQLDBHelper.KEY_PERMS_BITS + " & " + cBitwise.OWNER_READ + ") != 0))" +
                /* group (owner/primary organization) permission */
                " OR (((" + cSQLDBHelper.KEY_GROUP_BITS + " & ?) != 0) " +
                " AND ((" + cSQLDBHelper.KEY_PERMS_BITS + " & " + cBitwise.GROUP_READ + ") != 0))" +
                /* other (secondary organizations) permission */
                " OR (((" + cSQLDBHelper.KEY_GROUP_BITS + " & ?) != 0) " +
                " AND ((" + cSQLDBHelper.KEY_PERMS_BITS + " & " + cBitwise.OTHER_READ + ") != 0)))" +
                /* owner, group and other permissions allowed when the statuses hold */
                " AND ((" + cSQLDBHelper.KEY_STATUS_BITS + " = 0) " +
                " OR ((" + cSQLDBHelper.KEY_STATUS_BITS + " & ?) != 0)))))";

        Cursor cursor = db.rawQuery(selectQuery, new String[]{
                String.valueOf(outputID),
                String.valueOf(userID),           /* access due to organization creator */
                String.valueOf(userID),           /* access due to record owner */
                String.valueOf(primaryRoleBITS),  /* access due to membership in primary role */
                String.valueOf(secondaryRoleBITS),/* access due to membership in secondary role */
                String.valueOf(statusBITS)});     /* access due to assigned statuses */

        try {
            if (cursor.moveToFirst()) {
                do {
                    cActivityModel activity = new cActivityModel();

                    activity.setActivityPlanningID(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ID)));
                    activity.setParentID(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PARENT_FK_ID)));
                    activity.setLogFrameID(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_LOGFRAME_FK_ID)));
                    activity.setOutputID(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OUTPUT_FK_ID)));
                    activity.setServerID(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_SERVER_ID)));
                    activity.setOwnerID(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OWNER_ID)));
                    activity.setOrgID(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ORG_ID)));
                    activity.setGroupBITS(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_GROUP_BITS)));
                    activity.setPermsBITS(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PERMS_BITS)));
                    activity.setStatusBITS(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_STATUS_BITS)));
                    activity.setName(
                            cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_NAME)));
                    activity.setDescription(
                            cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_DESCRIPTION)));
                    activity.setStartDate(Timestamp.valueOf(
                            cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_START_DATE))));
                    activity.setEndDate(Timestamp.valueOf(
                            cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_END_DATE))));
                    activity.setCreatedDate(Timestamp.valueOf(
                            cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_CREATED_DATE))));
                    activity.setModifiedDate(Timestamp.valueOf(
                            cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_MODIFIED_DATE))));
                    activity.setSyncedDate(Timestamp.valueOf(
                            cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_SYNCED_DATE))));

                    activityModelSet.add(activity);

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to read an ACTIVITY entity: " + e.getMessage());
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        // close the database connection
        db.close();

        return activityModelSet;
    }

    private Set<cQuestionModel> getQuestionModelSetByID(int outputID, int userID, int primaryRoleBITS,
                                                        int secondaryRoleBITS, int statusBITS) {

        Set<cQuestionModel> questionModelSet = new HashSet<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String selectQuery = "SELECT " +
                "O." + cSQLDBHelper.KEY_ID + ", Q." + cSQLDBHelper.KEY_LOGFRAME_FK_ID + ", " +
                "Q." + cSQLDBHelper.KEY_QUESTION_TYPE_FK_ID + ", " +
                "Q." + cSQLDBHelper.KEY_QUESTION_GROUPING_FK_ID + ", " +
                "Q." + cSQLDBHelper.KEY_SERVER_ID + ", Q." + cSQLDBHelper.KEY_OWNER_ID + ", " +
                "Q." + cSQLDBHelper.KEY_ORG_ID + ", Q." + cSQLDBHelper.KEY_GROUP_BITS + ", " +
                "Q." + cSQLDBHelper.KEY_PERMS_BITS + ", Q." + cSQLDBHelper.KEY_STATUS_BITS + ", " +
                "Q." + cSQLDBHelper.KEY_NAME + ", Q." + cSQLDBHelper.KEY_DESCRIPTION + ", " +
                "Q." + cSQLDBHelper.KEY_START_DATE + ", Q." + cSQLDBHelper.KEY_END_DATE + ", " +
                "Q." + cSQLDBHelper.KEY_CREATED_DATE + ", Q." + cSQLDBHelper.KEY_MODIFIED_DATE + ", " +
                "Q." + cSQLDBHelper.KEY_SYNCED_DATE +
                " FROM " +
                cSQLDBHelper.TABLE_tblOUTPUT + " O, " +
                cSQLDBHelper.TABLE_tblQUESTION + " Q, " +
                cSQLDBHelper.TABLE_tblOUTPUT_QUESTION + " O_Q " +
                " WHERE ((O." + cSQLDBHelper.KEY_ID + " = O_Q." + cSQLDBHelper.KEY_OUTPUT_FK_ID +
                " AND Q." + cSQLDBHelper.KEY_ID + " = O_Q." + cSQLDBHelper.KEY_QUESTION_FK_ID +
                " AND O." + cSQLDBHelper.KEY_ID + " = ?) AND " +
                /* read access permissions */
                /* organization creator is always allowed to do everything (i.e., where: userID = orgID)*/
                " ((Q." + cSQLDBHelper.KEY_ORG_ID + " = ?) " +
                /* owner permission */
                " OR ((((Q." + cSQLDBHelper.KEY_OWNER_ID + " = ?) " +
                " AND ((Q." + cSQLDBHelper.KEY_PERMS_BITS + " & " + cBitwise.OWNER_READ + ") != 0))" +
                /* group (owner/primary organization) permission */
                " OR (((Q." + cSQLDBHelper.KEY_GROUP_BITS + " & ?) != 0) " +
                " AND ((Q." + cSQLDBHelper.KEY_PERMS_BITS + " & " + cBitwise.GROUP_READ + ") != 0))" +
                /* other (secondary organizations) permission */
                " OR (((Q." + cSQLDBHelper.KEY_GROUP_BITS + " & ?) != 0) " +
                " AND ((Q." + cSQLDBHelper.KEY_PERMS_BITS + " & " + cBitwise.OTHER_READ + ") != 0)))" +
                /* owner, group and other permissions allowed when the statuses hold */
                " AND ((Q." + cSQLDBHelper.KEY_STATUS_BITS + " = 0) " +
                " OR ((Q." + cSQLDBHelper.KEY_STATUS_BITS + " & ?) != 0)))))";

        Cursor cursor = db.rawQuery(selectQuery, new String[]{
                String.valueOf(outputID),
                String.valueOf(userID),           /* access due to organization creator */
                String.valueOf(userID),           /* access due to record owner */
                String.valueOf(primaryRoleBITS),  /* access due to membership in primary role */
                String.valueOf(secondaryRoleBITS),/* access due to membership in secondary role */
                String.valueOf(statusBITS)});     /* access due to assigned statuses */

        try {
            if (cursor.moveToFirst()) {
                do {
                    cQuestionModel question = new cQuestionModel();

                    question.setQuestionID(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ID)));
                    question.setLogFrameID(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_LOGFRAME_FK_ID)));
                    question.setQuestionTypeID(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_QUESTION_TYPE_FK_ID)));
                    question.setQuestionGroupID(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_QUESTION_GROUPING_FK_ID)));
                    question.setServerID(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_SERVER_ID)));
                    question.setOwnerID(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OWNER_ID)));
                    question.setOrgID(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ORG_ID)));
                    question.setGroupBITS(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_GROUP_BITS)));
                    question.setPermsBITS(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PERMS_BITS)));
                    question.setStatusBITS(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_STATUS_BITS)));
                    question.setName(
                            cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_NAME)));
                    question.setDescription(
                            cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_DESCRIPTION)));
                    question.setStartDate(Timestamp.valueOf(
                            cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_START_DATE))));
                    question.setEndDate(Timestamp.valueOf(
                            cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_END_DATE))));
                    question.setCreatedDate(Timestamp.valueOf(
                            cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_CREATED_DATE))));
                    question.setModifiedDate(Timestamp.valueOf(
                            cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_MODIFIED_DATE))));
                    question.setSyncedDate(Timestamp.valueOf(
                            cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_SYNCED_DATE))));

                    questionModelSet.add(question);

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to read a QUESTION entity: " + e.getMessage());
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        // close the database connection
        db.close();

        return questionModelSet;
    }

    private Set<cRaidModel> getRaidModelSetByID(int outputID, int userID, int primaryRoleBITS,
                                                int secondaryRoleBITS, int statusBITS) {

        Set<cRaidModel> raidModelSet = new HashSet<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String selectQuery = "SELECT " +
                "O." + cSQLDBHelper.KEY_ID + ", R." + cSQLDBHelper.KEY_LOGFRAME_FK_ID + ", " +
                "R." + cSQLDBHelper.KEY_SERVER_ID + ", R." + cSQLDBHelper.KEY_OWNER_ID + ", " +
                "R." + cSQLDBHelper.KEY_ORG_ID + ", R." + cSQLDBHelper.KEY_GROUP_BITS + ", " +
                "R." + cSQLDBHelper.KEY_PERMS_BITS + ", R." + cSQLDBHelper.KEY_STATUS_BITS + ", " +
                "R." + cSQLDBHelper.KEY_NAME + ", R." + cSQLDBHelper.KEY_DESCRIPTION + ", " +
                "R." + cSQLDBHelper.KEY_START_DATE + ", R." + cSQLDBHelper.KEY_END_DATE + ", " +
                "R." + cSQLDBHelper.KEY_CREATED_DATE + ", R." + cSQLDBHelper.KEY_MODIFIED_DATE + ", " +
                "R." + cSQLDBHelper.KEY_SYNCED_DATE +
                " FROM " +
                cSQLDBHelper.TABLE_tblOUTPUT + " O, " +
                cSQLDBHelper.TABLE_tblRAID + " R, " +
                cSQLDBHelper.TABLE_tblOUTPUT_RAID + " O_R " +
                " WHERE ((O." + cSQLDBHelper.KEY_ID + " = O_R." + cSQLDBHelper.KEY_OUTPUT_FK_ID +
                " AND R." + cSQLDBHelper.KEY_ID + " = O_R." + cSQLDBHelper.KEY_RAID_FK_ID +
                " AND O." + cSQLDBHelper.KEY_ID + " = ?) AND " +
                /* read access permissions */
                /* organization creator is always allowed to do everything (i.e., where: userID = orgID)*/
                " ((R." + cSQLDBHelper.KEY_ORG_ID + " = ?) " +
                /* owner permission */
                " OR ((((R." + cSQLDBHelper.KEY_OWNER_ID + " = ?) " +
                " AND ((R." + cSQLDBHelper.KEY_PERMS_BITS + " & " + cBitwise.OWNER_READ + ") != 0))" +
                /* group (owner/primary organization) permission */
                " OR (((R." + cSQLDBHelper.KEY_GROUP_BITS + " & ?) != 0) " +
                " AND ((R." + cSQLDBHelper.KEY_PERMS_BITS + " & " + cBitwise.GROUP_READ + ") != 0))" +
                /* other (secondary organizations) permission */
                " OR (((R." + cSQLDBHelper.KEY_GROUP_BITS + " & ?) != 0) " +
                " AND ((R." + cSQLDBHelper.KEY_PERMS_BITS + " & " + cBitwise.OTHER_READ + ") != 0)))" +
                /* owner, group and other permissions allowed when the statuses hold */
                " AND ((R." + cSQLDBHelper.KEY_STATUS_BITS + " = 0) " +
                " OR ((R." + cSQLDBHelper.KEY_STATUS_BITS + " & ?) != 0)))))";

        Cursor cursor = db.rawQuery(selectQuery, new String[]{
                String.valueOf(outputID),
                String.valueOf(userID),           /* access due to organization creator */
                String.valueOf(userID),           /* access due to record owner */
                String.valueOf(primaryRoleBITS),  /* access due to membership in primary role */
                String.valueOf(secondaryRoleBITS),/* access due to membership in secondary role */
                String.valueOf(statusBITS)});     /* access due to assigned statuses */

        try {
            if (cursor.moveToFirst()) {
                do {
                    cRaidModel raid = new cRaidModel();

                    raid.setRaidID(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ID)));
                    raid.setLogFrameID(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_LOGFRAME_FK_ID)));
                    raid.setServerID(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_SERVER_ID)));
                    raid.setOwnerID(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OWNER_ID)));
                    raid.setOrgID(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ORG_ID)));
                    raid.setGroupBITS(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_GROUP_BITS)));
                    raid.setPermsBITS(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PERMS_BITS)));
                    raid.setStatusBITS(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_STATUS_BITS)));
                    raid.setName(
                            cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_NAME)));
                    raid.setDescription(
                            cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_DESCRIPTION)));
                    raid.setStartDate(Timestamp.valueOf(
                            cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_START_DATE))));
                    raid.setEndDate(Timestamp.valueOf(
                            cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_END_DATE))));
                    raid.setCreatedDate(Timestamp.valueOf(
                            cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_CREATED_DATE))));
                    raid.setModifiedDate(Timestamp.valueOf(
                            cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_MODIFIED_DATE))));
                    raid.setSyncedDate(Timestamp.valueOf(
                            cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_SYNCED_DATE))));

                    raidModelSet.add(raid);

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to read a RAID entities: " + e.getMessage());
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        // close the database connection
        db.close();

        return raidModelSet;
    }

    private Set<cOutcomeModel> getChildOutcomeSetByID(
            int outputID, int userID, int primaryRoleBITS, int secondaryRoleBITS, int statusBITS) {

        Set<cOutcomeModel> outcomeModelSet = new HashSet<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String selectQuery = "SELECT DISTINCT " +
                "O." + cSQLDBHelper.KEY_ID + ", O." + cSQLDBHelper.KEY_PARENT_FK_ID + ", " +
                "O." + cSQLDBHelper.KEY_IMPACT_FK_ID + ", " +
                "O." + cSQLDBHelper.KEY_LOGFRAME_FK_ID + " AS PARENT_LOGFRAME_ID, " +
                "I." + cSQLDBHelper.KEY_LOGFRAME_FK_ID + " AS CHILD_LOGFRAME_ID, " +
                "O." + cSQLDBHelper.KEY_SERVER_ID + ", O." + cSQLDBHelper.KEY_OWNER_ID + ", " +
                "O." + cSQLDBHelper.KEY_ORG_ID + ", O." + cSQLDBHelper.KEY_GROUP_BITS + ", " +
                "O." + cSQLDBHelper.KEY_PERMS_BITS + ", O." + cSQLDBHelper.KEY_STATUS_BITS + ", " +
                "O." + cSQLDBHelper.KEY_NAME + ", O." + cSQLDBHelper.KEY_DESCRIPTION + ", " +
                "O." + cSQLDBHelper.KEY_START_DATE + ", O." + cSQLDBHelper.KEY_END_DATE + ", " +
                "O." + cSQLDBHelper.KEY_CREATED_DATE + ", O." + cSQLDBHelper.KEY_MODIFIED_DATE + ", " +
                "O." + cSQLDBHelper.KEY_SYNCED_DATE +
                " FROM " +
                cSQLDBHelper.TABLE_tblOUTPUT + " O, " +
                cSQLDBHelper.TABLE_tblOUTCOME + " I, " +
                cSQLDBHelper.TABLE_tblOUTPUT_OUTCOME + " OI, " +
                cSQLDBHelper.TABLE_tblLOGFRAMETREE + " LT " +
                " WHERE ((O." + cSQLDBHelper.KEY_ID + " = OI." + cSQLDBHelper.KEY_OUTCOME_FK_ID +
                " AND O." + cSQLDBHelper.KEY_LOGFRAME_FK_ID + " = OI." + cSQLDBHelper.KEY_PARENT_FK_ID +
                " AND I." + cSQLDBHelper.KEY_LOGFRAME_FK_ID + " = OI." + cSQLDBHelper.KEY_CHILD_FK_ID +
                " AND OI." + cSQLDBHelper.KEY_PARENT_FK_ID + " = LT." + cSQLDBHelper.KEY_PARENT_FK_ID +
                " AND OI." + cSQLDBHelper.KEY_CHILD_FK_ID + " = LT." + cSQLDBHelper.KEY_CHILD_FK_ID +
                " AND O." + cSQLDBHelper.KEY_ID + " = ?) AND " +
                /* read access permissions */
                /* organization creator is always allowed to do everything (i.e., where: userID = orgID)*/
                " ((O." + cSQLDBHelper.KEY_ORG_ID + " = ?) " +
                /* owner permission */
                " OR ((((O." + cSQLDBHelper.KEY_OWNER_ID + " = ?) " +
                " AND ((O." + cSQLDBHelper.KEY_PERMS_BITS + " & " + cBitwise.OWNER_READ + ") != 0))" +
                /* group (owner/primary organization) permission */
                " OR (((O." + cSQLDBHelper.KEY_GROUP_BITS + " & ?) != 0) " +
                " AND ((O." + cSQLDBHelper.KEY_PERMS_BITS + " & " + cBitwise.GROUP_READ + ") != 0))" +
                /* other (secondary organizations) permission */
                " OR (((O." + cSQLDBHelper.KEY_GROUP_BITS + " & ?) != 0) " +
                " AND ((O." + cSQLDBHelper.KEY_PERMS_BITS + " & " + cBitwise.OTHER_READ + ") != 0)))" +
                /* owner, group and other permissions allowed when the statuses hold */
                " AND ((O." + cSQLDBHelper.KEY_STATUS_BITS + " = 0) " +
                " OR ((O." + cSQLDBHelper.KEY_STATUS_BITS + " & ?) != 0)))))";

        Cursor cursor = db.rawQuery(selectQuery, new String[]{
                String.valueOf(outputID),
                String.valueOf(userID),           /* access due to organization creator */
                String.valueOf(userID),           /* access due to record owner */
                String.valueOf(primaryRoleBITS),  /* access due to membership in primary role */
                String.valueOf(secondaryRoleBITS),/* access due to membership in secondary role */
                String.valueOf(statusBITS)});     /* access due to assigned statuses */

        try {
            if (cursor.moveToFirst()) {
                do {
                    cOutcomeModel outcome = new cOutcomeModel();

                    outcome.setOutcomeID(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ID)));
                    outcome.setParentID(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PARENT_FK_ID)));
                    outcome.setImpactID(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_IMPACT_FK_ID)));
                    //outcome.setLogFrameID(
                    //        cursor.getInt(cursor.getColumnIndex("PARENT_LOGFRAME_ID")));
                    outcome.setServerID(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_SERVER_ID)));
                    outcome.setOwnerID(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OWNER_ID)));
                    outcome.setOrgID(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ORG_ID)));
                    outcome.setGroupBITS(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_GROUP_BITS)));
                    outcome.setPermsBITS(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PERMS_BITS)));
                    outcome.setStatusBITS(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_STATUS_BITS)));
                    outcome.setName(
                            cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_NAME)));
                    outcome.setDescription(
                            cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_DESCRIPTION)));
                    outcome.setStartDate(Timestamp.valueOf(
                            cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_START_DATE))));
                    outcome.setEndDate(Timestamp.valueOf(
                            cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_END_DATE))));
                    outcome.setCreatedDate(Timestamp.valueOf(
                            cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_CREATED_DATE))));
                    outcome.setModifiedDate(Timestamp.valueOf(
                            cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_MODIFIED_DATE))));
                    outcome.setSyncedDate(Timestamp.valueOf(
                            cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_SYNCED_DATE))));

                    /* compute set of impacts under sub-logframe */
                    long parentLogFrameID, childLogFrameID;
                    parentLogFrameID = cursor.getInt(cursor.getColumnIndex("PARENT_LOGFRAME_ID"));
                    childLogFrameID = cursor.getInt(cursor.getColumnIndex("CHILD_LOGFRAME_ID"));


                    outcomeModelSet.add(outcome);

                    /*Set<cImpactModel> impactModelSet = getOutcomeSetByLogFrameID(
                            childLogFrameID, userID, primaryRoleBITS, secondaryRoleBITS, statusBITS);

                    impactMap.put(new Pair<>(parentLogFrameID, childLogFrameID), impactModelSet);*/

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to read a OUTCOME entity: " + e.getMessage());
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        // close the database connection
        db.close();

        return outcomeModelSet;
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

                    outputModel.setOutputID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ID)));
                    outputModel.setServerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_SERVER_ID)));
                    outputModel.setOwnerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OWNER_ID)));
                    outputModel.setOrgID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ORG_ID)));
                    outputModel.setGroupBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_GROUP_BITS)));
                    outputModel.setPermsBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PERMS_BITS)));
                    outputModel.setStatusBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_STATUS_BITS)));
                    outputModel.setName(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_NAME)));
                    outputModel.setDescription(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_DESCRIPTION)));
                    outputModel.setStartDate(sdf.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_START_DATE))));
                    outputModel.setEndDate(sdf.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_END_DATE))));
                    outputModel.setCreatedDate(sdf.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_CREATED_DATE))));
                    outputModel.setModifiedDate(sdf.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_MODIFIED_DATE))));
                    outputModel.setSyncedDate(sdf.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_SYNCED_DATE))));

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

                    outcomeModel.setOutcomeID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ID)));
                    outcomeModel.setServerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_SERVER_ID)));
                    outcomeModel.setOwnerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OWNER_ID)));
                    outcomeModel.setOrgID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ORG_ID)));
                    outcomeModel.setGroupBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_GROUP_BITS)));
                    outcomeModel.setPermsBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PERMS_BITS)));
                    outcomeModel.setStatusBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_STATUS_BITS)));
                    outcomeModel.setName(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_NAME)));
                    outcomeModel.setDescription(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_DESCRIPTION)));
                    outcomeModel.setStartDate(sdf.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_START_DATE))));
                    outcomeModel.setEndDate(sdf.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_END_DATE))));
                    outcomeModel.setCreatedDate(sdf.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_CREATED_DATE))));
                    outcomeModel.setModifiedDate(sdf.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_MODIFIED_DATE))));
                    outcomeModel.setSyncedDate(sdf.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_SYNCED_DATE))));

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
