package com.me.mseotsanyana.mande.DAL.ìmpl.logframe;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.me.mseotsanyana.mande.BLL.domain.logframe.cOutcomeDomain;
import com.me.mseotsanyana.mande.DAL.model.logframe.cQuestionModel;
import com.me.mseotsanyana.mande.DAL.storage.mapper.cMapper;
import com.me.mseotsanyana.mande.DAL.model.logframe.cImpactModel;
import com.me.mseotsanyana.mande.DAL.model.logframe.cLogFrameModel;
import com.me.mseotsanyana.mande.DAL.model.logframe.cOutcomeModel;
import com.me.mseotsanyana.mande.DAL.model.logframe.cOutputModel;
import com.me.mseotsanyana.mande.DAL.storage.database.cSQLDBHelper;
import com.me.mseotsanyana.mande.DAL.storage.preference.cBitwise;
import com.me.mseotsanyana.mande.UTIL.cConstant;

import java.lang.String;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class cOutcomeImpl extends cMapper<cImpactModel, cOutcomeDomain> /*implements iOutcomeRepository*/ {
    private static SimpleDateFormat sdf = cConstant.FORMAT_DATE;
    private static String TAG = cOutcomeImpl.class.getSimpleName();

	// an object of the database helper
	private cSQLDBHelper dbHelper;

	public cOutcomeImpl(Context context) {
		dbHelper = new cSQLDBHelper(context);
	}

    /* ######################################## MAPPER ACTIONS ########################################*/

    @Override
    protected cImpactModel DomainToModel(cOutcomeDomain cOutcomeDomain) {
        return null;
    }

    @Override
    protected cOutcomeDomain ModelToDomain(cImpactModel cImpactModel) {
        return null;
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
            Log.d(TAG, "Exception in adding OUTCOME "+e.getMessage().toString());
        }

        // close the database connection
        db.close();

        return true;
    }

    /* ######################################## READ ACTIONS ########################################*/

    /*
     * the function fetches all outcomes
     */
    public Set<cOutcomeModel> getOutcomeModelSet(int userID, int primaryRole, int secondaryRoles,
                                                 int operationBITS, int statusBITS) {
        // list of outcomes
        Set<cOutcomeModel> outcomeModelSet = new HashSet<>();

        // open the connection to the database
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + cSQLDBHelper.TABLE_tblOUTCOME +
                " WHERE (" +
                /* read access permissions */
                /* organization creator is always allowed to do everything (i.e., where: userID = orgID)*/
                " ((" + cSQLDBHelper.KEY_ORG_ID + " = ?) " +
                /* owner permission */
                " OR ((((" + cSQLDBHelper.KEY_OWNER_ID + " = ?) " +
                " AND ((" + cSQLDBHelper.KEY_PERMS_BITS + " & " + cBitwise.OWNER_READ + " & ? ) != 0)) " +
                /* group (owner/primary organization) permission */
                " OR (((" + cSQLDBHelper.KEY_GROUP_BITS + " & ?) != 0) " +
                " AND ((" + cSQLDBHelper.KEY_PERMS_BITS + " & " + cBitwise.GROUP_READ + " & ? ) != 0)) " +
                /* other (secondary organizations) permission */
                " OR (((" + cSQLDBHelper.KEY_GROUP_BITS + " & ?) != 0) " +
                " AND ((" + cSQLDBHelper.KEY_PERMS_BITS + " & " + cBitwise.OTHER_READ + " & ? ) != 0)))" +
                /* owner, group and other permissions allowed when the statuses hold */
                " AND ((" + cSQLDBHelper.KEY_STATUS_BITS + " & ?) != 0))))";

        Cursor cursor = db.rawQuery(selectQuery, new String[]{
                String.valueOf(userID),
                String.valueOf(userID), String.valueOf(operationBITS),
                String.valueOf(primaryRole), String.valueOf(operationBITS),
                String.valueOf(secondaryRoles), String.valueOf(operationBITS),
                String.valueOf(statusBITS)});

        try {
            if (cursor.moveToFirst()) {
                do {
                    cOutcomeModel outcomeModel = new cOutcomeModel();

                    outcomeModel.setOutcomeID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ID)));
                    outcomeModel.setParentID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PARENT_FK_ID)));
                    outcomeModel.setLogFrameID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_LOGFRAME_FK_ID)));
                    outcomeModel.setImpactID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_IMPACT_FK_ID)));
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

                    /*** incoming mappings
                    private cLogFrameModel logFrameModel;
                    private cImpactModel impactModel;
                    private cOutcomeModel outcomeModel;
                    private Set<cOutcomeModel> outcomeModelSet;

                    /*** outgoing mappings
                    private Set<cOutputModel> outputModelSet;
                    private Set<cQuestionModel> questionModelSet;
                    private Set<cRaidModel> raidModelSet;
                    /* set of impact in a sub-logframe for the parent outcome
                    private Map<Pair<cLogFrameModel, cLogFrameModel>, Set<cImpactModel>> outcomeModelSetMap;
                    /* a parent output of the outcome in a sub-logframe
                    private Map<Pair<cLogFrameModel, cLogFrameModel>, cOutputModel> outcomeModelMap;
                    */

                    /*** objects ***/
                    /* populate a logframe object */
                    outcomeModel.setLogFrameModel(getLogFrameModelByID(outcomeModel.getLogFrameID(), userID,
                            primaryRole, secondaryRoles, operationBITS, statusBITS));
                    /* populate an impact object */
                    outcomeModel.setImpactModel(getImpactModelByID(outcomeModel.getImpactID(), userID,
                            primaryRole, secondaryRoles, operationBITS, statusBITS));
                    /* populate an outcome object */
                    outcomeModel.setOutcomeModel(getOutcomeModelByID(outcomeModel.getParentID(), userID,
                            primaryRole, secondaryRoles, operationBITS, statusBITS));

                    /*** sets ***/
                    /* populate output components */
                    outcomeModel.setOutputModelSet(getOutputModelSetByID(outcomeModel.getOutcomeID(), userID,
                            primaryRole, secondaryRoles, operationBITS, statusBITS));
                    /* populate question components */
                    outcomeModel.setQuestionModelSet(getQuestionModelSetByID(outcomeModel.getOutcomeID(), userID,
                            primaryRole, secondaryRoles, operationBITS, statusBITS));
                    /* populate raid components
                    outcomeModel.setRaidModelSet(getRaidModelSetByID(outcomeModel.getOutcomeID(), userID,
                            primaryRole, secondaryRoles, operationBITS, statusBITS));*/

                    /*** maps ***/
                    /* populate outcome component maps
                    outcomeModel.setOutcomeModelSetMap(getOutcomeModelSetMapByID(outcomeModel.getOutcomeID(), userID,
                            primaryRole, secondaryRoles, operationBITS, statusBITS));*/
                    /* populate outcome component maps
                    outcomeModel.setOutcomeModelMap(getOutcomeModelMapByID(outcomeModel.getOutcomeID(), userID,
                            primaryRole, secondaryRoles, operationBITS, statusBITS));*/

                    /*** add outcome entity ***/
                    outcomeModelSet.add(outcomeModel);

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error in reading all OUTCOME entities: "+e.getMessage());
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
     * @param logFrameID
     * @param userID
     * @param primaryRole
     * @param secondaryRoles
     * @param operationBITS
     * @param statusBITS
     * @return
     */
    public cLogFrameModel getLogFrameModelByID(int logFrameID, int userID, int primaryRole, int secondaryRoles,
                                               int operationBITS, int statusBITS) {
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
                " AND ((" + cSQLDBHelper.KEY_PERMS_BITS + " & " + cBitwise.OWNER_READ + " & ? ) != 0)) " +
                /* group (owner/primary organization) permission */
                " OR (((" + cSQLDBHelper.KEY_GROUP_BITS + " & ?) != 0) " +
                " AND ((" + cSQLDBHelper.KEY_PERMS_BITS + " & " + cBitwise.GROUP_READ + " & ? ) != 0)) " +
                /* other (secondary organizations) permission */
                " OR (((" + cSQLDBHelper.KEY_GROUP_BITS + " & ?) != 0) " +
                " AND ((" + cSQLDBHelper.KEY_PERMS_BITS + " & " + cBitwise.OTHER_READ + " & ? ) != 0)))" +
                /* owner, group and other permissions allowed when the statuses hold */
                " AND ((" + cSQLDBHelper.KEY_STATUS_BITS + " & ?) != 0))))";

        Cursor cursor = db.rawQuery(selectQuery, new String[]{
                String.valueOf(logFrameID),
                String.valueOf(userID),
                String.valueOf(userID), String.valueOf(operationBITS),
                String.valueOf(primaryRole), String.valueOf(operationBITS),
                String.valueOf(secondaryRoles), String.valueOf(operationBITS),
                String.valueOf(statusBITS)});

        cLogFrameModel logFrameModel = new cLogFrameModel();

        try {
            if (cursor.moveToFirst()) {
                logFrameModel.setLogFrameID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ID)));
                logFrameModel.setServerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_SERVER_ID)));
                logFrameModel.setOwnerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OWNER_ID)));
                logFrameModel.setOrgID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ORG_ID)));
                logFrameModel.setGroupBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_GROUP_BITS)));
                logFrameModel.setPermsBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PERMS_BITS)));
                logFrameModel.setStatusBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_STATUS_BITS)));
                logFrameModel.setName(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_NAME)));
                logFrameModel.setDescription(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_DESCRIPTION)));
                logFrameModel.setStartDate(
                        Timestamp.valueOf(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_START_DATE))));
                logFrameModel.setEndDate(
                        Timestamp.valueOf(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_END_DATE))));
                logFrameModel.setCreatedDate(
                        Timestamp.valueOf(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_CREATED_DATE))));
                logFrameModel.setModifiedDate(
                        Timestamp.valueOf(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_MODIFIED_DATE))));
                logFrameModel.setSyncedDate(
                        Timestamp.valueOf(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_SYNCED_DATE))));
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to read logFrame entity: "+e.getMessage());
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        // close the database connection
        db.close();

        return logFrameModel;
    }

    /**
     * Return an Impact entity related to the outcome entity
     *
     * @param impactID
     * @param userID
     * @param primaryRole
     * @param secondaryRoles
     * @param operationBITS
     * @param statusBITS
     * @return
     */
    public cImpactModel getImpactModelByID(int impactID, int userID, int primaryRole, int secondaryRoles,
                                              int operationBITS, int statusBITS) {
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
                " AND ((" + cSQLDBHelper.KEY_PERMS_BITS + " & " + cBitwise.OWNER_READ + " & ? ) != 0)) " +
                /* group (owner/primary organization) permission */
                " OR (((" + cSQLDBHelper.KEY_GROUP_BITS + " & ?) != 0) " +
                " AND ((" + cSQLDBHelper.KEY_PERMS_BITS + " & " + cBitwise.GROUP_READ + " & ? ) != 0)) " +
                /* other (secondary organizations) permission */
                " OR (((" + cSQLDBHelper.KEY_GROUP_BITS + " & ?) != 0) " +
                " AND ((" + cSQLDBHelper.KEY_PERMS_BITS + " & " + cBitwise.OTHER_READ + " & ? ) != 0)))" +
                /* owner, group and other permissions allowed when the statuses hold */
                " AND ((" + cSQLDBHelper.KEY_STATUS_BITS + " & ?) != 0))))";

        Cursor cursor = db.rawQuery(selectQuery, new String[]{
                String.valueOf(impactID),
                String.valueOf(userID),
                String.valueOf(userID), String.valueOf(operationBITS),
                String.valueOf(primaryRole), String.valueOf(operationBITS),
                String.valueOf(secondaryRoles), String.valueOf(operationBITS),
                String.valueOf(statusBITS)});

        cImpactModel impactModel = new cImpactModel();

        try {
            if (cursor.moveToFirst()) {
                impactModel.setImpactID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ID)));
                impactModel.setParentID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PARENT_FK_ID)));
                impactModel.setLogFrameID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_LOGFRAME_FK_ID)));
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
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to read logFrame entity: "+e.getMessage());
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        // close the database connection
        db.close();

        return impactModel;
    }

    /**
     * Return an Outcome entity related to the outcome entity
     *
     * @param parentID
     * @param userID
     * @param primaryRole
     * @param secondaryRoles
     * @param operationBITS
     * @param statusBITS
     * @return
     */
    public cOutcomeModel getOutcomeModelByID(int parentID, int userID, int primaryRole, int secondaryRoles,
                                               int operationBITS, int statusBITS) {
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
                " AND ((" + cSQLDBHelper.KEY_PERMS_BITS + " & " + cBitwise.OWNER_READ + " & ? ) != 0)) " +
                /* group (owner/primary organization) permission */
                " OR (((" + cSQLDBHelper.KEY_GROUP_BITS + " & ?) != 0) " +
                " AND ((" + cSQLDBHelper.KEY_PERMS_BITS + " & " + cBitwise.GROUP_READ + " & ? ) != 0)) " +
                /* other (secondary organizations) permission */
                " OR (((" + cSQLDBHelper.KEY_GROUP_BITS + " & ?) != 0) " +
                " AND ((" + cSQLDBHelper.KEY_PERMS_BITS + " & " + cBitwise.OTHER_READ + " & ? ) != 0)))" +
                /* owner, group and other permissions allowed when the statuses hold */
                " AND ((" + cSQLDBHelper.KEY_STATUS_BITS + " & ?) != 0))))";

        Cursor cursor = db.rawQuery(selectQuery, new String[]{
                String.valueOf(parentID),
                String.valueOf(userID),
                String.valueOf(userID), String.valueOf(operationBITS),
                String.valueOf(primaryRole), String.valueOf(operationBITS),
                String.valueOf(secondaryRoles), String.valueOf(operationBITS),
                String.valueOf(statusBITS)});

        cOutcomeModel outcomeModel = new cOutcomeModel();

        try {
            if (cursor.moveToFirst()) {
                outcomeModel.setOutcomeID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ID)));
                outcomeModel.setParentID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PARENT_FK_ID)));
                outcomeModel.setLogFrameID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_LOGFRAME_FK_ID)));
                outcomeModel.setImpactID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_IMPACT_FK_ID)));
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
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to read logFrame entity: "+e.getMessage());
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        // close the database connection
        db.close();

        return outcomeModel;
    }

    public Set<cOutputModel> getOutputModelSetByID(int impactID, int userID, int primaryRole,
                                                   int secondaryRoles, int operationBITS, int statusBITS) {

        Set<cOutputModel> outputModelSet = new HashSet<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + cSQLDBHelper.TABLE_tblOUTPUT +
                " WHERE ((" + cSQLDBHelper.KEY_OUTCOME_FK_ID + " = ?) AND " +
                /* read access permissions */
                /* organization creator is always allowed to do everything (i.e., where: userID = orgID)*/
                " ((" + cSQLDBHelper.KEY_ORG_ID + " = ?) " +
                /* owner permission */
                " OR ((((" + cSQLDBHelper.KEY_OWNER_ID + " = ?) " +
                " AND ((" + cSQLDBHelper.KEY_PERMS_BITS + " & " + cBitwise.OWNER_READ + " & ? ) != 0)) " +
                /* group (owner/primary organization) permission */
                " OR (((" + cSQLDBHelper.KEY_GROUP_BITS + " & ?) != 0) " +
                " AND ((" + cSQLDBHelper.KEY_PERMS_BITS + " & " + cBitwise.GROUP_READ + " & ? ) != 0)) " +
                /* other (secondary organizations) permission */
                " OR (((" + cSQLDBHelper.KEY_GROUP_BITS + " & ?) != 0) " +
                " AND ((" + cSQLDBHelper.KEY_PERMS_BITS + " & " + cBitwise.OTHER_READ + " & ? ) != 0)))" +
                /* owner, group and other permissions allowed when the statuses hold */
                " AND ((" + cSQLDBHelper.KEY_STATUS_BITS + " & ?) != 0))))";

        Cursor cursor = db.rawQuery(selectQuery, new String[]{
                String.valueOf(impactID),
                String.valueOf(userID),
                String.valueOf(userID), String.valueOf(operationBITS),
                String.valueOf(primaryRole), String.valueOf(operationBITS),
                String.valueOf(secondaryRoles), String.valueOf(operationBITS),
                String.valueOf(statusBITS)});

        try {
            if (cursor.moveToFirst()) {
                do {
                    cOutputModel outputModel = new cOutputModel();

                    outputModel.setOutputID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ID)));
                    outputModel.setParentID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PARENT_FK_ID)));
                    outputModel.setServerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_SERVER_ID)));
                    outputModel.setOwnerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OWNER_ID)));
                    outputModel.setOrgID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ORG_ID)));
                    outputModel.setGroupBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_GROUP_BITS)));
                    outputModel.setPermsBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PERMS_BITS)));
                    outputModel.setStatusBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_STATUS_BITS)));
                    outputModel.setName(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_NAME)));
                    outputModel.setDescription(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_DESCRIPTION)));
                    outputModel.setStartDate(
                            Timestamp.valueOf(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_START_DATE))));
                    outputModel.setEndDate(
                            Timestamp.valueOf(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_END_DATE))));
                    outputModel.setCreatedDate(
                            Timestamp.valueOf(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_CREATED_DATE))));
                    outputModel.setModifiedDate(
                            Timestamp.valueOf(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_MODIFIED_DATE))));
                    outputModel.setSyncedDate(
                            Timestamp.valueOf(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_SYNCED_DATE))));

                    outputModelSet.add(outputModel);

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to read OUTPUT entities: " + e.getMessage());
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        // close the database connection
        db.close();

        return outputModelSet;
    }

    public Set<cQuestionModel> getQuestionModelSetByID(int impactID, int userID, int primaryRole,
                                                       int secondaryRoles, int operationBITS, int statusBITS) {

        Set<cQuestionModel> questionModelSet = new HashSet<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();

/*
        String selectQuery = "SELECT status." + cSQLDBHelper.KEY_ID + ", " +
                "status." + cSQLDBHelper.KEY_SERVER_ID + ", " +
                "status." + cSQLDBHelper.KEY_OWNER_ID + ", status." + cSQLDBHelper.KEY_ORG_ID + ", " +
                "status." + cSQLDBHelper.KEY_GROUP_BITS + ", status." + cSQLDBHelper.KEY_PERMS_BITS + ", " +
                "status." + cSQLDBHelper.KEY_STATUS_BITS + ", status." + cSQLDBHelper.KEY_NAME + ", " +
                "status." + cSQLDBHelper.KEY_DESCRIPTION + ", " +
                "status." + cSQLDBHelper.KEY_CREATED_DATE + ", " +
                "status." + cSQLDBHelper.KEY_MODIFIED_DATE + ", " +
                "status." + cSQLDBHelper.KEY_SYNCED_DATE +
                " FROM " +
                cSQLDBHelper.TABLE_tblPRIVILEGE + " privilege, " +
                cSQLDBHelper.TABLE_tblSTATUS + " status, " +
                cSQLDBHelper.TABLE_tblPRIV_STATUS + " priv_status " +
                " WHERE privilege." + cSQLDBHelper.KEY_ID + " = priv_status." + cSQLDBHelper.KEY_PRIVILEGE_FK_ID +
                " AND status." + cSQLDBHelper.KEY_ID + " = priv_status." + cSQLDBHelper.KEY_STATUS_FK_ID +
                " AND privilege." + cSQLDBHelper.KEY_ID + " = ?";
*/

        String selectQuery = "SELECT " +
                "outcome." + cSQLDBHelper.KEY_ID + ", " +
                "question." + cSQLDBHelper.KEY_ID + ", " +
                "question." + cSQLDBHelper.KEY_SERVER_ID + ", " +
                "question." + cSQLDBHelper.KEY_OWNER_ID + ", " +
                "question." + cSQLDBHelper.KEY_ORG_ID + ", " +
                "question." + cSQLDBHelper.KEY_GROUP_BITS + ", " +
                "question." + cSQLDBHelper.KEY_PERMS_BITS + ", " +
                "question." + cSQLDBHelper.KEY_STATUS_BITS + ", " +
                "question." + cSQLDBHelper.KEY_NAME + ", " +
                "question." + cSQLDBHelper.KEY_DESCRIPTION + ", " +
                "question." + cSQLDBHelper.KEY_CREATED_DATE + ", " +
                "question." + cSQLDBHelper.KEY_MODIFIED_DATE + ", " +
                "question." + cSQLDBHelper.KEY_SYNCED_DATE +
                " FROM " +
                cSQLDBHelper.TABLE_tblOUTCOME + " outcome, " +
                cSQLDBHelper.TABLE_tblQUESTION + " question, " +
                cSQLDBHelper.TABLE_tblOUTCOME_QUESTION + " outcome_question " +
                " WHERE ((outcome." + cSQLDBHelper.KEY_ID + " = outcome_question." + cSQLDBHelper.KEY_OUTCOME_FK_ID +
                " AND question." + cSQLDBHelper.KEY_ID + " = outcome_question." + cSQLDBHelper.KEY_QUESTION_FK_ID +
                " AND question." + cSQLDBHelper.KEY_ID + " = ?) AND " +
                /* read access permissions */
                /* organization creator is always allowed to do everything (i.e., where: userID = orgID)*/
                " ((" + cSQLDBHelper.KEY_ORG_ID + " = ?) " +
                /* owner permission */
                " OR ((((" + cSQLDBHelper.KEY_OWNER_ID + " = ?) " +
                " AND ((" + cSQLDBHelper.KEY_PERMS_BITS + " & " + cBitwise.OWNER_READ + " & ? ) != 0)) " +
                /* group (owner/primary organization) permission */
                " OR (((" + cSQLDBHelper.KEY_GROUP_BITS + " & ?) != 0) " +
                " AND ((" + cSQLDBHelper.KEY_PERMS_BITS + " & " + cBitwise.GROUP_READ + " & ? ) != 0)) " +
                /* other (secondary organizations) permission */
                " OR (((" + cSQLDBHelper.KEY_GROUP_BITS + " & ?) != 0) " +
                " AND ((" + cSQLDBHelper.KEY_PERMS_BITS + " & " + cBitwise.OTHER_READ + " & ? ) != 0)))" +
                /* owner, group and other permissions allowed when the statuses hold */
                " AND ((" + cSQLDBHelper.KEY_STATUS_BITS + " & ?) != 0))))";

        Cursor cursor = db.rawQuery(selectQuery, new String[]{
                String.valueOf(impactID),
                String.valueOf(userID),
                String.valueOf(userID), String.valueOf(operationBITS),
                String.valueOf(primaryRole), String.valueOf(operationBITS),
                String.valueOf(secondaryRoles), String.valueOf(operationBITS),
                String.valueOf(statusBITS)});

        try {
            if (cursor.moveToFirst()) {
                do {
                    cQuestionModel questionModel = new cQuestionModel();

                    //questionModel.setOutputID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ID)));
                    //questionModel.setParentID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PARENT_FK_ID)));
                    questionModel.setServerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_SERVER_ID)));
                    questionModel.setOwnerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OWNER_ID)));
                    questionModel.setOrgID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ORG_ID)));
                    questionModel.setGroupBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_GROUP_BITS)));
                    questionModel.setPermsBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PERMS_BITS)));
                    questionModel.setStatusBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_STATUS_BITS)));
                    questionModel.setName(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_NAME)));
                    questionModel.setDescription(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_DESCRIPTION)));
                    questionModel.setStartDate(
                            Timestamp.valueOf(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_START_DATE))));
                    questionModel.setEndDate(
                            Timestamp.valueOf(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_END_DATE))));
                    questionModel.setCreatedDate(
                            Timestamp.valueOf(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_CREATED_DATE))));
                    questionModel.setModifiedDate(
                            Timestamp.valueOf(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_MODIFIED_DATE))));
                    questionModel.setSyncedDate(
                            Timestamp.valueOf(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_SYNCED_DATE))));

                    questionModelSet.add(questionModel);

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to read QUESTION entities: " + e.getMessage());
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        // close the database connection
        db.close();

        return questionModelSet;
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
     * the function delete a specific outcome
     */
    public boolean deleteOutcome(cOutcomeModel outcomeModel) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete a record of a specific ID
        try {
            if(db.delete(cSQLDBHelper.TABLE_tblIMPACT, cSQLDBHelper.KEY_ID + " = ?",
                    new String[]{String.valueOf(outcomeModel.getImpactID())}) < 0){
                return false;
            }
        }catch (Exception e){
            Log.d(TAG, "Exception in deleting OUTCOME "+e.getMessage().toString());
        }

        // close the database connection
        db.close();

        return true;
    }

    /* ######################################## SYNC ACTIONS ########################################*/



}
