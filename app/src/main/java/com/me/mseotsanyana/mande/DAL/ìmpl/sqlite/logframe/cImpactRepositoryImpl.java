package com.me.mseotsanyana.mande.DAL.ìmpl.sqlite.logframe;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.me.mseotsanyana.mande.BLL.repository.logframe.iImpactRepository;
import com.me.mseotsanyana.mande.BLL.model.logframe.cImpactModel;
import com.me.mseotsanyana.mande.DAL.storage.database.cSQLDBHelper;
import com.me.mseotsanyana.mande.DAL.storage.preference.cBitwise;
import com.me.mseotsanyana.mande.UTIL.cConstant;

import java.lang.String;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class cImpactRepositoryImpl implements iImpactRepository {
    private static SimpleDateFormat sdf = cConstant.TIMESTAMP_FORMAT_DATE;
    private static String TAG = cImpactRepositoryImpl.class.getSimpleName();

    /* an object of the database helper */
    private cSQLDBHelper dbHelper;
    private Context context;

    public cImpactRepositoryImpl(Context context) {
        this.dbHelper = new cSQLDBHelper(context);
        this.context = context;
    }

    /* ######################################## CREATE ACTIONS ########################################*/

    public boolean addImpact(cImpactModel impactModel) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

//        // assign values to the table fields
//        cv.put(cSQLDBHelper.KEY_ID, impactModel.getComponentID());
//        cv.put(cSQLDBHelper.KEY_SERVER_ID, impactModel.getServerID());
//        cv.put(cSQLDBHelper.KEY_OWNER_ID, impactModel.getOwnerID());
//        cv.put(cSQLDBHelper.KEY_ORG_ID, impactModel.getOrgID());
//        cv.put(cSQLDBHelper.KEY_GROUP_BITS, impactModel.getGroupBITS());
//        cv.put(cSQLDBHelper.KEY_PERMS_BITS, impactModel.getPermsBITS());
//        cv.put(cSQLDBHelper.KEY_STATUS_BITS, impactModel.getStatusBITS());
//        cv.put(cSQLDBHelper.KEY_NAME, impactModel.getName());
//        cv.put(cSQLDBHelper.KEY_DESCRIPTION, impactModel.getDescription());
//        cv.put(cSQLDBHelper.KEY_START_DATE, sdf.format(impactModel.getStartDate()));
//        cv.put(cSQLDBHelper.KEY_END_DATE, sdf.format(impactModel.getEndDate()));
//        cv.put(cSQLDBHelper.KEY_CREATED_DATE, sdf.format(impactModel.getCreatedDate()));
//        cv.put(cSQLDBHelper.KEY_MODIFIED_DATE, sdf.format(impactModel.getModifiedDate()));
//        cv.put(cSQLDBHelper.KEY_SYNCED_DATE, sdf.format(impactModel.getSyncedDate()));

        // insert project details
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblIMPACT, null, cv) < 0) {
                return false;
            }
        } catch (Exception e) {
            Log.d(TAG, "Exception in importing " + e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }

    /* ######################################## READ ACTIONS ########################################*/

    /**
     * the function fetches all impacts
     *
     *
     * @param logFrameID logframe identification
     * @param userID user identification
     * @param primaryRoleBITS primary roles
     * @param secondaryRoleBITS secondary roles
     * @param statusBITS statuses
     * @return set of impacts
     */
    public Set<cImpactModel> getImpactModelSet(long logFrameID, long userID, int primaryRoleBITS,
                                               int secondaryRoleBITS, int statusBITS) {

        Set<cImpactModel> impactModelSet = new HashSet<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        //String selectQuery = "SELECT * FROM " + cSQLDBHelper.TABLE_tblIMPACT +
        //        " WHERE ((" + cSQLDBHelper.KEY_LOGFRAME_FK_ID + " = ?) AND " +
        String selectQuery = "SELECT * FROM " + cSQLDBHelper.TABLE_tblCOMPONENT +" C INNER JOIN " +
                cSQLDBHelper.TABLE_tblIMPACT +" I ON C."+cSQLDBHelper.KEY_ID +" = I." +
                cSQLDBHelper.KEY_COMPONENT_FK_ID +
                " WHERE ((" + cSQLDBHelper.KEY_LOGFRAME_FK_ID + " = ?) AND " +
                /* read access permissions */
                /* organization creator is always allowed to do everything (i.e., where: userID = orgID)*/
                " ((" + cSQLDBHelper.KEY_ORG_ID + " = ?) " +
                /* owner permission */
                " OR ((((" + cSQLDBHelper.KEY_OWNER_ID + " = ?) " +
                " AND ((" + cSQLDBHelper.KEY_PERMS_BITS + " & " + cBitwise.OWNER_READ + ") != 0)) " +
                /* group (owner/primary organization) permission */
                " OR (((" + cSQLDBHelper.KEY_GROUP_BITS + " & ?) != 0) " +
                " AND ((" + cSQLDBHelper.KEY_PERMS_BITS + " & " + cBitwise.GROUP_READ + ") != 0)) " +
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
                    cImpactModel impact = new cImpactModel();

//                    impact.setComponentID(
//                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_COMPONENT_FK_ID)));
////                    impact.getLogFrameModel().setLogFrameID(
////                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_LOGFRAME_FK_ID)));
//                    impact.setServerID(
//                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_SERVER_ID)));
//                    impact.setOwnerID(
//                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OWNER_ID)));
//                    impact.setOrgID(
//                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ORG_ID)));
//                    impact.setGroupBITS(
//                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_GROUP_BITS)));
//                    impact.setPermsBITS(
//                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PERMS_BITS)));
//                    impact.setStatusBITS(
//                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_STATUS_BITS)));
//                    impact.setName(
//                            cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_NAME)));
//                    impact.setDescription(
//                            cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_DESCRIPTION)));
//                    impact.setStartDate(Timestamp.valueOf(
//                            cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_START_DATE))));
//                    impact.setEndDate(Timestamp.valueOf(
//                            cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_END_DATE))));
//                    impact.setCreatedDate(Timestamp.valueOf(
//                            cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_CREATED_DATE))));
//                    impact.setModifiedDate(Timestamp.valueOf(
//                            cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_MODIFIED_DATE))));
//                    impact.setSyncedDate(Timestamp.valueOf(
//                            cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_SYNCED_DATE))));
//
//                    /* set a logframe for the impact */
//                    cLogFrameRepositoryImpl logFrameRepository = new cLogFrameRepositoryImpl(context);
////                    impact.setLogFrameModel(logFrameRepository.getLogFrameModelByID(
////                            impact.getLogFrameModel().getLogFrameID(), userID, primaryRoleBITS,
////                            secondaryRoleBITS, statusBITS));
//
//                    /* populate impact children */
//                    impact.setChildImpactModelSet(getImpactModelSetByID(impact.getComponentID(),
//                            userID, primaryRoleBITS, secondaryRoleBITS, statusBITS));
//
//                    /* populate outcome components*/
//                    cOutcomeRepositoryImpl outcomeRepository = new cOutcomeRepositoryImpl(context);
//                    impact.setOutcomeModelSet(outcomeRepository.getOutcomeModelSetByImpactID(
//                            impact.getComponentID(), userID, primaryRoleBITS, secondaryRoleBITS,
//                            statusBITS));
//
//                    /* populate question components*/
//                    cQuestionRepositoryImpl questionRepository = new cQuestionRepositoryImpl(context);
//                    impact.setQuestionModelSet(questionRepository.getQuestionModelSetByComponentID(
//                            impact.getComponentID(), userID, primaryRoleBITS, secondaryRoleBITS,
//                            statusBITS));
//
//                    /* populate RAID components
//                    cRaidRepositoryImpl raidRepository = new cRaidRepositoryImpl(context);
//                    impact.setRaidModelSet(raidRepository.getRaidModelSetByComponentID(
//                            impact.getComponentID(), userID, primaryRoleBITS, secondaryRoleBITS,
//                            statusBITS));*/

                    /* add impact in the set */
                    impactModelSet.add(impact);

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while reading IMPACT table: "+e.getMessage());
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        // close the database connection
        db.close();

        return impactModelSet;
    }

    /**
     * This returns a set of impacts
     *
     * @param impactID impact identification
     * @param userID user identification
     * @param primaryRoleBITS primary roles
     * @param secondaryRoleBITS secondary roles
     * @param statusBITS statuses
     * @return set of child impacts
     */
    private Set<cImpactModel> getImpactModelSetByID(long impactID, long userID, int primaryRoleBITS,
                                                   int secondaryRoleBITS, int statusBITS) {

        Set<cImpactModel> impactModelSet = new HashSet<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + cSQLDBHelper.TABLE_tblCOMPONENT +" C INNER JOIN " +
                cSQLDBHelper.TABLE_tblIMPACT +" I ON C."+cSQLDBHelper.KEY_ID +" = I." +
                cSQLDBHelper.KEY_COMPONENT_FK_ID +
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
                String.valueOf(impactID),         /* access due to being child impact */
                String.valueOf(userID),           /* access due to organization creator */
                String.valueOf(userID),           /* access due to record owner */
                String.valueOf(primaryRoleBITS),  /* access due to membership in primary role */
                String.valueOf(secondaryRoleBITS),/* access due to membership in secondary role */
                String.valueOf(statusBITS)});     /* access due to assigned statuses */

        try {
            if (cursor.moveToFirst()) {
                do {
                    cImpactModel impact = new cImpactModel();

//                    impact.setComponentID(
//                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ID)));
////                    impact.getLogFrameModel().setLogFrameID(
////                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_LOGFRAME_FK_ID)));
//                    impact.setServerID(
//                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_SERVER_ID)));
//                    impact.setOwnerID(
//                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OWNER_ID)));
//                    impact.setOrgID(
//                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ORG_ID)));
//                    impact.setGroupBITS(
//                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_GROUP_BITS)));
//                    impact.setPermsBITS(
//                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PERMS_BITS)));
//                    impact.setStatusBITS(
//                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_STATUS_BITS)));
//                    impact.setName(
//                            cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_NAME)));
//                    impact.setDescription(
//                            cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_DESCRIPTION)));
//                    impact.setStartDate(Timestamp.valueOf(
//                            cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_START_DATE))));
//                    impact.setEndDate(Timestamp.valueOf(
//                            cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_END_DATE))));
//                    impact.setCreatedDate(Timestamp.valueOf(
//                            cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_CREATED_DATE))));
//                    impact.setModifiedDate(Timestamp.valueOf(
//                            cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_MODIFIED_DATE))));
//                    impact.setSyncedDate(Timestamp.valueOf(
//                            cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_SYNCED_DATE))));

                    impactModelSet.add(impact);

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while reading IMPACT table:" + e.getMessage());
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        // close the database connection
        db.close();

        return impactModelSet;
    }

    // get impacts for a given impact ID
    public ArrayList<cImpactModel> getImpactsByID(int impactID) {
        // list of impacts
        ArrayList<cImpactModel> impactModels = new ArrayList<>();

        // open the connection to the database
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // construct a selection query
        String selectQuery = "SELECT * FROM " +
                cSQLDBHelper.TABLE_tblIMPACT + " impact " +
                " WHERE impact." + cSQLDBHelper.KEY_ID + " = ?";

        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(impactID)});

        try {
            if (cursor.moveToFirst()) {
                do {
                    cImpactModel impactModel = new cImpactModel();

//                    impactModel.setComponentID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ID)));
//                    impactModel.setServerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_SERVER_ID)));
//                    impactModel.setOwnerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OWNER_ID)));
//                    impactModel.setOrgID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ORG_ID)));
//                    impactModel.setGroupBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_GROUP_BITS)));
//                    impactModel.setPermsBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PERMS_BITS)));
//                    impactModel.setStatusBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_STATUS_BITS)));
//                    impactModel.setName(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_NAME)));
//                    impactModel.setDescription(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_DESCRIPTION)));
//                    impactModel.setStartDate(
//                            Timestamp.valueOf(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_START_DATE))));
//                    impactModel.setEndDate(
//                            Timestamp.valueOf(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_END_DATE))));
//                    impactModel.setCreatedDate(
//                            Timestamp.valueOf(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_CREATED_DATE))));
//                    impactModel.setModifiedDate(
//                            Timestamp.valueOf(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_MODIFIED_DATE))));
//                    impactModel.setSyncedDate(
//                            Timestamp.valueOf(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_SYNCED_DATE))));
//                    impactModels.add(impactModel);

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

    /* ######################################## UPDATE ACTIONS ########################################*/


    /* ######################################## DELETE ACTIONS ########################################*/

    /*
     * the function delete a specific logframe
     */
    public boolean deleteImpact(cImpactModel impactModel) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete a record of a specific ID
        try {
//            if (db.delete(cSQLDBHelper.TABLE_tblIMPACT, cSQLDBHelper.KEY_ID + " = ?",
//                    new String[]{String.valueOf(impactModel.getComponentID())}) < 0) {
//                return false;
//            }
        } catch (Exception e) {
            Log.d(TAG, "Exception in deleting " + e.getMessage());
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
            if (db.delete(cSQLDBHelper.TABLE_tblIMPACT, null, null) < 0) {
                return false;
            }
        } catch (Exception e) {
            Log.d(TAG, "Exception in deleting all logframes " + e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }

    @Override
    public void readImpacts(String logframeID, String organizationServerID, String userServerID, int primaryTeamBIT, List<Integer> secondaryTeamBITS, List<Integer> statusBITS, iReadImpactsCallback callback) {

    }

    /* ######################################## SYNC ACTIONS ########################################*/


}
//    /**
//     *
//     * @param logFrameID logical frame identification
//     * @param userID user identification
//     * @param primaryRoleBITS primary roles
//     * @param secondaryRoleBITS secondary roles
//     * @param statusBITS statuses
//     * @return a parent logframe
//     */
//    private cLogFrameModel getLogFrameByID(long logFrameID, long userID, int primaryRoleBITS,
//                                           int secondaryRoleBITS, int statusBITS) {
//        // open connection to read only
//        SQLiteDatabase db = dbHelper.getReadableDatabase();
//        // construct a selection query
//        String selectQuery = "SELECT * FROM " + cSQLDBHelper.TABLE_tblLOGFRAME +
//                " WHERE ((" + cSQLDBHelper.KEY_ID + " = ?) AND " +
//                /* read access permissions */
//                /* organization creator is always allowed to do everything (i.e., where: userID = orgID)*/
//                " ((" + cSQLDBHelper.KEY_ORG_ID + " = ?) " +
//                /* owner permission */
//                " OR ((((" + cSQLDBHelper.KEY_OWNER_ID + " = ?) " +
//                " AND ((" + cSQLDBHelper.KEY_PERMS_BITS + " & " + cBitwise.OWNER_READ + ") != 0))" +
//                /* group (owner/primary organization) permission */
//                " OR (((" + cSQLDBHelper.KEY_GROUP_BITS + " & ?) != 0) " +
//                " AND ((" + cSQLDBHelper.KEY_PERMS_BITS + " & " + cBitwise.GROUP_READ + ") != 0))" +
//                /* other (secondary organizations) permission */
//                " OR (((" + cSQLDBHelper.KEY_GROUP_BITS + " & ?) != 0) " +
//                " AND ((" + cSQLDBHelper.KEY_PERMS_BITS + " & " + cBitwise.OTHER_READ + ") != 0)))" +
//                /* owner, group and other permissions allowed when the statuses hold */
//                " AND ((" + cSQLDBHelper.KEY_STATUS_BITS + " = 0) " +
//                " OR ((" + cSQLDBHelper.KEY_STATUS_BITS + " & ?) != 0)))))";
//
//
//        Cursor cursor = db.rawQuery(selectQuery, new String[]{
//                String.valueOf(logFrameID),       /* access due to being child impact */
//                String.valueOf(userID),           /* access due to organization creator */
//                String.valueOf(userID),           /* access due to record owner */
//                String.valueOf(primaryRoleBITS),  /* access due to membership in primary role */
//                String.valueOf(secondaryRoleBITS),/* access due to membership in secondary role */
//                String.valueOf(statusBITS)});     /* access due to assigned statuses */
//
//        cLogFrameModel logFrame = new cLogFrameModel();
//
//        try {
//            if (cursor.moveToFirst()) {
//
//                logFrame.setLogFrameID(
//                        cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ID)));
//                logFrame.setOrganizationID(
//                        cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ORGANIZATION_FK_ID)));
//                logFrame.setServerID(
//                        cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_SERVER_ID)));
//                logFrame.setOwnerID(
//                        cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OWNER_ID)));
//                logFrame.setOrgID(
//                        cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ORG_ID)));
//                logFrame.setGroupBITS(
//                        cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_GROUP_BITS)));
//                logFrame.setPermsBITS(
//                        cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PERMS_BITS)));
//                logFrame.setStatusBITS(
//                        cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_STATUS_BITS)));
//                logFrame.setName(
//                        cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_NAME)));
//                logFrame.setDescription(
//                        cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_DESCRIPTION)));
//                logFrame.setStartDate(Timestamp.valueOf(
//                        cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_START_DATE))));
//                logFrame.setEndDate(Timestamp.valueOf(
//                        cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_END_DATE))));
//                logFrame.setCreatedDate(Timestamp.valueOf(
//                        cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_CREATED_DATE))));
//                logFrame.setModifiedDate(Timestamp.valueOf(
//                        cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_MODIFIED_DATE))));
//                logFrame.setSyncedDate(Timestamp.valueOf(
//                        cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_SYNCED_DATE))));
//            }
//
//            return logFrame;
//
//        } catch (Exception e) {
//            Log.d(TAG, "Error while reading LOGFRAME table: " + e.getMessage());
//            return null;
//        } finally {
//            if (cursor != null && !cursor.isClosed()) {
//                cursor.close();
//            }
//        }
//    }

