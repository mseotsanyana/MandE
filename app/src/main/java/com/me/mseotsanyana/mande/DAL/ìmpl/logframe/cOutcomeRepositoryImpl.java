package com.me.mseotsanyana.mande.DAL.ìmpl.logframe;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.util.Pair;

import com.me.mseotsanyana.mande.BLL.repository.logframe.iOutcomeRepository;
import com.me.mseotsanyana.mande.DAL.model.logframe.cQuestionModel;
import com.me.mseotsanyana.mande.DAL.model.logframe.cImpactModel;
import com.me.mseotsanyana.mande.DAL.model.logframe.cLogFrameModel;
import com.me.mseotsanyana.mande.DAL.model.logframe.cOutcomeModel;
import com.me.mseotsanyana.mande.DAL.model.logframe.cOutputModel;
import com.me.mseotsanyana.mande.DAL.model.logframe.cRaidModel;
import com.me.mseotsanyana.mande.DAL.storage.database.cSQLDBHelper;
import com.me.mseotsanyana.mande.DAL.storage.preference.cBitwise;
import com.me.mseotsanyana.mande.UTIL.cConstant;

import java.lang.String;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class cOutcomeRepositoryImpl implements iOutcomeRepository {
    private static SimpleDateFormat sdf = cConstant.FORMAT_DATE;
    private static String TAG = cOutcomeRepositoryImpl.class.getSimpleName();

    // an object of the database helper
    private cSQLDBHelper dbHelper;

    public cOutcomeRepositoryImpl(Context context) {
        dbHelper = new cSQLDBHelper(context);
    }

    /* ######################################## CREATE ACTIONS ########################################*/

    /**
     * The function adding a specific outcome
     *
     * @param outcomeModel
     * @return
     */
    public boolean addOutcome(cOutcomeModel outcomeModel) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_ID, outcomeModel.getOutcomeID());
        cv.put(cSQLDBHelper.KEY_SERVER_ID, outcomeModel.getServerID());
        cv.put(cSQLDBHelper.KEY_OWNER_ID, outcomeModel.getOwnerID());
        cv.put(cSQLDBHelper.KEY_ORG_ID, outcomeModel.getOrgID());
        cv.put(cSQLDBHelper.KEY_GROUP_BITS, outcomeModel.getGroupBITS());
        cv.put(cSQLDBHelper.KEY_PERMS_BITS, outcomeModel.getPermsBITS());
        cv.put(cSQLDBHelper.KEY_STATUS_BITS, outcomeModel.getStatusBITS());
        cv.put(cSQLDBHelper.KEY_NAME, outcomeModel.getName());
        cv.put(cSQLDBHelper.KEY_DESCRIPTION, outcomeModel.getDescription());
        cv.put(cSQLDBHelper.KEY_START_DATE, sdf.format(outcomeModel.getStartDate()));
        cv.put(cSQLDBHelper.KEY_END_DATE, sdf.format(outcomeModel.getEndDate()));
        cv.put(cSQLDBHelper.KEY_CREATED_DATE, sdf.format(outcomeModel.getCreatedDate()));
        cv.put(cSQLDBHelper.KEY_MODIFIED_DATE, sdf.format(outcomeModel.getModifiedDate()));
        cv.put(cSQLDBHelper.KEY_SYNCED_DATE, sdf.format(outcomeModel.getSyncedDate()));

        // insert project details
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblOUTCOME, null, cv) < 0) {
                return false;
            }
        } catch (Exception e) {
            Log.d(TAG, "Exception in adding OUTCOME " + e.getMessage().toString());
        }

        // close the database connection
        db.close();

        return true;
    }

    /* ######################################## READ ACTIONS ########################################*/

    /*
     * the function fetches all outcomes
     */
    public Set<cOutcomeModel> getOutcomeModelSet(long logFrameID, int userID, int primaryRoleBITS,
                                                 int secondaryRoleBITS, int statusBITS) {
        // list of outcomes
        Set<cOutcomeModel> outcomeModelSet = new HashSet<>();

        // open the connection to the database
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + cSQLDBHelper.TABLE_tblOUTCOME +
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
                    cOutcomeModel outcome = new cOutcomeModel();

                    outcome.setOutcomeID(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ID)));
                    outcome.setParentID(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PARENT_FK_ID)));
                    outcome.setLogFrameID(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_LOGFRAME_FK_ID)));
                    outcome.setImpactID(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_IMPACT_FK_ID)));
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

                    /* objects */

                    /* populate a logframe object */
                    outcome.setLogFrameModel(getLogFrameModelByID(outcome.getLogFrameID(), userID,
                            primaryRoleBITS, secondaryRoleBITS, statusBITS));
                    /* populate an impact object */
                    outcome.setImpactModel(getImpactModelByID(outcome.getImpactID(), userID,
                            primaryRoleBITS, secondaryRoleBITS, statusBITS));

                    /* sets */

                    /* populate child outcome components */
                    outcome.setChildOutcomeModelSet(getOutcomeModelSetByID(outcome.getOutcomeID(),
                            userID, primaryRoleBITS, secondaryRoleBITS, statusBITS));
                    /* populate output components */
                    outcome.setOutputModelSet(getOutputModelSetByID(outcome.getOutcomeID(), userID,
                            primaryRoleBITS, secondaryRoleBITS, statusBITS));
                    /* populate question components */
                    outcome.setQuestionModelSet(getQuestionModelSetByID(outcome.getOutcomeID(),
                            userID, primaryRoleBITS, secondaryRoleBITS, statusBITS));
                    /* populate raid components */
                    outcome.setRaidModelSet(getRaidModelSetByID(outcome.getOutcomeID(), userID,
                            primaryRoleBITS, secondaryRoleBITS, statusBITS));

                    /* maps */

                    /* populate child impacts for the outcome */
                    outcome.setChildImpactModelMap(getChildImpactMapByID(outcome.getOutcomeID(),
                            userID, primaryRoleBITS, secondaryRoleBITS, statusBITS));

                    /* add outcome entity */
                    outcomeModelSet.add(outcome);

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

        return outcomeModelSet;
    }

    /**
     * Return a logFrame entity related to the outcome entity
     *
     * @param logFrameID        logframe identification
     * @param userID            user identification
     * @param primaryRoleBITS   primary role bits
     * @param secondaryRoleBITS secondary role bits
     * @param statusBITS        status bits
     * @return logFrame
     */
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

    /**
     * Return an Impact entity related to the outcome entity
     *
     * @param impactID         outcome identification
     * @param userID            user identification
     * @param primaryRoleBITS   primary role bits
     * @param secondaryRoleBITS secondary role bits
     * @param statusBITS        status bits
     * @return impact
     */
    private cImpactModel getImpactModelByID(int impactID, int userID, int primaryRoleBITS,
                                            int secondaryRoleBITS, int statusBITS) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        // construct a selection query
        String selectQuery = "SELECT * FROM " + cSQLDBHelper.TABLE_tblIMPACT +
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
                String.valueOf(impactID),
                String.valueOf(userID),           /* access due to organization creator */
                String.valueOf(userID),           /* access due to record owner */
                String.valueOf(primaryRoleBITS),  /* access due to membership in primary role */
                String.valueOf(secondaryRoleBITS),/* access due to membership in secondary role */
                String.valueOf(statusBITS)});     /* access due to assigned statuses */

        cImpactModel impact = new cImpactModel();

        try {
            if (cursor.moveToFirst()) {
                impact.setImpactID(
                        cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ID)));
                impact.setParentID(
                        cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PARENT_FK_ID)));
                impact.setLogFrameID(
                        cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_LOGFRAME_FK_ID)));
                impact.setServerID(
                        cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_SERVER_ID)));
                impact.setOwnerID(
                        cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OWNER_ID)));
                impact.setOrgID(
                        cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ORG_ID)));
                impact.setGroupBITS(
                        cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_GROUP_BITS)));
                impact.setPermsBITS(
                        cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PERMS_BITS)));
                impact.setStatusBITS(
                        cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_STATUS_BITS)));
                impact.setName(
                        cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_NAME)));
                impact.setDescription(
                        cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_DESCRIPTION)));
                impact.setStartDate(Timestamp.valueOf(
                        cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_START_DATE))));
                impact.setEndDate(Timestamp.valueOf(
                        cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_END_DATE))));
                impact.setCreatedDate(Timestamp.valueOf(
                        cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_CREATED_DATE))));
                impact.setModifiedDate(Timestamp.valueOf(
                        cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_MODIFIED_DATE))));
                impact.setSyncedDate(Timestamp.valueOf(
                        cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_SYNCED_DATE))));
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to read an IMPACT entity: " + e.getMessage());
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        // close the database connection
        db.close();

        return impact;
    }

    /**
     * outcome children for an outcome
     *
     * @param outcomeID         outcome identification
     * @param userID            user identification
     * @param primaryRoleBITS   primary role bits
     * @param secondaryRoleBITS secondary role bits
     * @param statusBITS        status bits
     * @return child outcomes
     */
    private Set<cOutcomeModel> getOutcomeModelSetByID(int outcomeID, int userID, int primaryRoleBITS,
                                                      int secondaryRoleBITS, int statusBITS) {

        Set<cOutcomeModel> outcomeModelSet = new HashSet<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + cSQLDBHelper.TABLE_tblOUTCOME +
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
                String.valueOf(outcomeID),         /* access due to being child impact */
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
                    outcome.setLogFrameID(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_LOGFRAME_FK_ID)));
                    outcome.setImpactID(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_IMPACT_FK_ID)));
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

                    outcomeModelSet.add(outcome);

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while reading a SUB OUTCOME entity: " + e.getMessage());
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        // close the database connection
        db.close();

        return outcomeModelSet;
    }

    /**
     * outputs of an outcome
     *
     * @param outcomeID         outcome identification
     * @param userID            user identification
     * @param primaryRoleBITS   primary role bits
     * @param secondaryRoleBITS secondary role bits
     * @param statusBITS        status bits
     * @return outputs
     */
    private Set<cOutputModel> getOutputModelSetByID(long outcomeID, long userID, int primaryRoleBITS,
                                                    int secondaryRoleBITS, int statusBITS) {

        Set<cOutputModel> outputModelSet = new HashSet<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + cSQLDBHelper.TABLE_tblOUTPUT +
                " WHERE ((" + cSQLDBHelper.KEY_OUTCOME_FK_ID + " = ?) AND " +
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
            Log.d(TAG, "Error while trying to read an OUTPUT entity: " + e.getMessage());
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        // close the database connection
        db.close();

        return outputModelSet;
    }

    /**
     * questions of an outcome
     *
     * @param outcomeID         outcome identification
     * @param userID            user identification
     * @param primaryRoleBITS   primary role bits
     * @param secondaryRoleBITS secondary role bits
     * @param statusBITS        status bits
     * @return questions
     */
    private Set<cQuestionModel> getQuestionModelSetByID(int outcomeID, int userID, int primaryRoleBITS,
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
                cSQLDBHelper.TABLE_tblOUTCOME + " O, " +
                cSQLDBHelper.TABLE_tblQUESTION + " Q, " +
                cSQLDBHelper.TABLE_tblOUTCOME_QUESTION + " O_Q " +
                " WHERE ((O." + cSQLDBHelper.KEY_ID + " = O_Q." + cSQLDBHelper.KEY_OUTCOME_FK_ID +
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
                String.valueOf(outcomeID),
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

    /**
     * RAIDs of an outcome
     *
     * @param outcomeID         outcome identification
     * @param userID            user identification
     * @param primaryRoleBITS   primary role bits
     * @param secondaryRoleBITS secondary role bits
     * @param statusBITS        status bits
     * @return raids
     */
    private Set<cRaidModel> getRaidModelSetByID(int outcomeID, int userID, int primaryRoleBITS,
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
                cSQLDBHelper.TABLE_tblOUTCOME + " O, " +
                cSQLDBHelper.TABLE_tblRAID + " R, " +
                cSQLDBHelper.TABLE_tblOUTCOME_RAID + " O_R " +
                " WHERE ((O." + cSQLDBHelper.KEY_ID + " = O_R." + cSQLDBHelper.KEY_OUTCOME_FK_ID +
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
                String.valueOf(outcomeID),
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


    private Map<Pair<Long, Long>, Set<cImpactModel>> getChildImpactMapByID(
            int outcomeID, int userID, int primaryRoleBITS, int secondaryRoleBITS, int statusBITS) {

        Map<Pair<Long, Long>, Set<cImpactModel>> impactMap = new HashMap<>();

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
                cSQLDBHelper.TABLE_tblOUTCOME + " O, " +
                cSQLDBHelper.TABLE_tblIMPACT + " I, " +
                cSQLDBHelper.TABLE_tblOUTCOME_IMPACT + " OI, " +
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
                String.valueOf(outcomeID),
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

                    Set<cImpactModel> impactModelSet = getImpactSetByLogFrameID(
                            childLogFrameID, userID, primaryRoleBITS, secondaryRoleBITS, statusBITS);

                    impactMap.put(new Pair<>(parentLogFrameID, childLogFrameID), impactModelSet);

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

        return impactMap;
    }

    private Set<cImpactModel> getImpactSetByLogFrameID(long childLogFrameID, int userID,
                                                       int primaryRoleBITS, int secondaryRoleBITS,
                                                       int statusBITS) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Set<cImpactModel> impactModelSet = new HashSet<>();

        // construct a selection query
        String selectQuery = "SELECT * FROM " + cSQLDBHelper.TABLE_tblIMPACT +
                " WHERE ((" + cSQLDBHelper.KEY_LOGFRAME_FK_ID + "= ? ) AND " +
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
                String.valueOf(childLogFrameID),
                String.valueOf(userID),           /* access due to organization creator */
                String.valueOf(userID),           /* access due to record owner */
                String.valueOf(primaryRoleBITS),  /* access due to membership in primary role */
                String.valueOf(secondaryRoleBITS),/* access due to membership in secondary role */
                String.valueOf(statusBITS)});     /* access due to assigned statuses */

        try {
            if (cursor.moveToFirst()) {
                do {
                    cImpactModel impact = new cImpactModel();

                    impact.setImpactID(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ID)));
                    impact.setParentID(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PARENT_FK_ID)));
                    impact.setLogFrameID(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_LOGFRAME_FK_ID)));
                    impact.setServerID(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_SERVER_ID)));
                    impact.setOwnerID(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OWNER_ID)));
                    impact.setOrgID(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ORG_ID)));
                    impact.setGroupBITS(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_GROUP_BITS)));
                    impact.setPermsBITS(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PERMS_BITS)));
                    impact.setStatusBITS(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_STATUS_BITS)));
                    impact.setName(
                            cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_NAME)));
                    impact.setDescription(
                            cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_DESCRIPTION)));
                    impact.setStartDate(Timestamp.valueOf(
                            cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_START_DATE))));
                    impact.setEndDate(Timestamp.valueOf(
                            cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_END_DATE))));
                    impact.setCreatedDate(Timestamp.valueOf(
                            cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_CREATED_DATE))));
                    impact.setModifiedDate(Timestamp.valueOf(
                            cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_MODIFIED_DATE))));
                    impact.setSyncedDate(Timestamp.valueOf(
                            cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_SYNCED_DATE))));

                    impact.setLogFrameModel(getLogFrameModelByID(impact.getLogFrameID(), userID,
                            primaryRoleBITS, secondaryRoleBITS, statusBITS));

                    impactModelSet.add(impact);

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to read a IMPACT entities: " + e.getMessage());
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        // close the database connection
        db.close();

        return impactModelSet;
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
                " WHERE outcome." + cSQLDBHelper.KEY_ID + " = outcome_impact." + cSQLDBHelper.KEY_OUTCOME_FK_ID +
                " AND outcome." + cSQLDBHelper.KEY_LOGFRAME_FK_ID + " = outcome_impact." + cSQLDBHelper.KEY_PARENT_FK_ID +
                " AND impact." + cSQLDBHelper.KEY_ID + " = outcome_impact." + cSQLDBHelper.KEY_IMPACT_FK_ID +
                " AND impact." + cSQLDBHelper.KEY_LOGFRAME_FK_ID + " = outcome_impact." + cSQLDBHelper.KEY_CHILD_FK_ID +
                " AND outcome." + cSQLDBHelper.KEY_ID + " = ?";

        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(outcomeID)});

        try {
            if (cursor.moveToFirst()) {
                do {

                    cImpactModel impactModel = new cImpactModel();

                    impactModel.setImpactID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ID)));
                    impactModel.setServerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_SERVER_ID)));
                    impactModel.setOwnerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OWNER_ID)));
                    impactModel.setOrgID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ORG_ID)));
                    impactModel.setGroupBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_GROUP_BITS)));
                    impactModel.setPermsBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PERMS_BITS)));
                    impactModel.setStatusBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_STATUS_BITS)));
                    impactModel.setName(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_NAME)));
                    impactModel.setDescription(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_DESCRIPTION)));
                    impactModel.setStartDate(
                            Timestamp.valueOf(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_START_DATE))));
                    impactModel.setEndDate(
                            Timestamp.valueOf(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_END_DATE))));
                    impactModel.setCreatedDate(
                            Timestamp.valueOf(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_CREATED_DATE))));
                    impactModel.setModifiedDate(
                            Timestamp.valueOf(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_MODIFIED_DATE))));
                    impactModel.setSyncedDate(
                            Timestamp.valueOf(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_SYNCED_DATE))));
                    impactModels.add(impactModel);

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Exception in reading all OUTCOME_IMPACTs " + e.getMessage().toString());
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
        String selectQuery = "SELECT * FROM " +
                cSQLDBHelper.TABLE_tblOUTCOME + " outcome " +
                " WHERE outcome." + cSQLDBHelper.KEY_ID + " = ?";

        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(outcomeID)});

        try {
            if (cursor.moveToFirst()) {
                do {

                    outcomeModel.setOutcomeID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ID)));
                    outcomeModel.setServerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_SERVER_ID)));
                    outcomeModel.setOwnerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OWNER_ID)));
                    outcomeModel.setOrgID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ORG_ID)));
                    outcomeModel.setGroupBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_GROUP_BITS)));
                    outcomeModel.setPermsBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PERMS_BITS)));
                    outcomeModel.setStatusBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_STATUS_BITS)));
                    outcomeModel.setName(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_NAME)));
                    outcomeModel.setDescription(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_DESCRIPTION)));
                    outcomeModel.setStartDate(
                            Timestamp.valueOf(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_START_DATE))));
                    outcomeModel.setEndDate(
                            Timestamp.valueOf(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_END_DATE))));
                    outcomeModel.setCreatedDate(
                            Timestamp.valueOf(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_CREATED_DATE))));
                    outcomeModel.setModifiedDate(
                            Timestamp.valueOf(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_MODIFIED_DATE))));
                    outcomeModel.setSyncedDate(
                            Timestamp.valueOf(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_SYNCED_DATE))));
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

    /* ######################################## UPDATE ACTIONS ########################################*/

    /* ######################################## DELETE ACTIONS ########################################*/

    /*
     * the function delete all outcomes
     */
    public boolean deleteOutcomes() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        try {
            if (db.delete(cSQLDBHelper.TABLE_tblOUTCOME, null, null) < 0) {
                return false;
            }
        } catch (Exception e) {
            Log.d(TAG, "Exception in deleting all OUTCOMEs " + e.getMessage());
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
            if (db.delete(cSQLDBHelper.TABLE_tblOUTCOME_IMPACT, null, null) < 0) {
                return false;
            }
        } catch (Exception e) {
            Log.d(TAG, "Exception in deleting all OUTCOME_IMPACTs " + e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }

    /*
     * the function delete a specific outcome
     */
    public boolean deleteOutcome(cOutcomeModel outcomeModel) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete a record of a specific ID
        try {
            if (db.delete(cSQLDBHelper.TABLE_tblIMPACT, cSQLDBHelper.KEY_ID + " = ?",
                    new String[]{String.valueOf(outcomeModel.getImpactID())}) < 0) {
                return false;
            }
        } catch (Exception e) {
            Log.d(TAG, "Exception in deleting OUTCOME " + e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }

    /* ######################################## SYNC ACTIONS ########################################*/


}
