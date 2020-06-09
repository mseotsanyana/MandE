package com.me.mseotsanyana.mande.DAL.ìmpl.logframe;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.google.gson.Gson;
import com.me.mseotsanyana.mande.DAL.model.logframe.cActivityModel;
import com.me.mseotsanyana.mande.DAL.model.logframe.cImpactModel;
import com.me.mseotsanyana.mande.DAL.model.monitor.cIndicatorModel;
import com.me.mseotsanyana.mande.DAL.model.logframe.cInputModel;
import com.me.mseotsanyana.mande.DAL.model.logframe.cLogFrameModel;
import com.me.mseotsanyana.mande.DAL.model.logframe.cOutcomeModel;
import com.me.mseotsanyana.mande.DAL.model.logframe.cOutputModel;
import com.me.mseotsanyana.mande.DAL.model.logframe.cQuestionModel;
import com.me.mseotsanyana.mande.DAL.model.logframe.cRaidModel;
import com.me.mseotsanyana.mande.DAL.storage.database.cSQLDBHelper;
import com.me.mseotsanyana.mande.BLL.repository.logframe.iLogFrameRepository;
import com.me.mseotsanyana.mande.DAL.storage.excel.cExcelHelper;
import com.me.mseotsanyana.mande.DAL.storage.preference.cBitwise;
import com.me.mseotsanyana.mande.DAL.ìmpl.session.cOrganizationRepositoryImpl;
import com.me.mseotsanyana.mande.UTIL.cConstant;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class cLogFrameRepositoryImpl implements iLogFrameRepository {
    private static SimpleDateFormat sdf = cConstant.TIMESTAMP_FORMAT_DATE;
    private static String TAG = cLogFrameRepositoryImpl.class.getSimpleName();

    /* an object of the database helper */
    private cSQLDBHelper dbHelper;

    /* an object of the excel helper */
    private cExcelHelper excelHelper;

    private cOrganizationRepositoryImpl organizationRepository;

    public cLogFrameRepositoryImpl(Context context) {
        dbHelper = new cSQLDBHelper(context);
        excelHelper = new cExcelHelper(context);
        organizationRepository = new cOrganizationRepositoryImpl(context);
    }

    /* ######################################## CREATE ACTIONS ########################################*/

    public boolean addLogFrameFromExcel() {

        Workbook workbook = excelHelper.getWorkbookLOGFRAME();
        Sheet logFrameSheet = workbook.getSheet(cExcelHelper.SHEET_tblLOGFRAME);
        if (logFrameSheet == null) {
            return false;
        }

        for (Iterator<Row> ritLogFrame = logFrameSheet.iterator(); ritLogFrame.hasNext(); ) {
            Row cRow = ritLogFrame.next();

            //just skip the row if row number is 0
            if (cRow.getRowNum() == 0) {
                continue;
            }

            cLogFrameModel logFrameModel = new cLogFrameModel();

            logFrameModel.setLogFrameID((int) cRow.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            logFrameModel.setName(cRow.getCell(1, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
            logFrameModel.setDescription(cRow.getCell(2, Row.CREATE_NULL_AS_BLANK).getStringCellValue());

            Set<Integer> subLogFrameDomainSet = new HashSet<>();
            int parentID, childID;

            Sheet logFrameTreeSheet = workbook.getSheet(cExcelHelper.SHEET_tblLOGFRAMETREE);
            for (Iterator<Row> ritLogframeTree = logFrameTreeSheet.iterator(); ritLogframeTree.hasNext(); ) {
                Row rowLogFrameTree = ritLogframeTree.next();

                //just skip the row if row number is 0
                if (rowLogFrameTree.getRowNum() == 0) {
                    continue;
                }

                parentID = (int) rowLogFrameTree.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                if (logFrameModel.getLogFrameID() == parentID) {
                    childID = (int) rowLogFrameTree.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                    subLogFrameDomainSet.add(childID);
                }
            }

            //Gson gson = new Gson();
            //Log.d(TAG, gson.toJson(logFrameModel)+" -> "+gson.toJson(subLogFrameDomainSet));
            if (!addLogFrameFromExcel(logFrameModel, subLogFrameDomainSet)) {
                return false;
            }
        }

        return true;
    }

    /**
     * this function adds the logframe (i.e., project) details
     */
    public boolean addLogFrameFromExcel(cLogFrameModel logFrameModel, Set<Integer> subLogFrameSet) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_ID, logFrameModel.getLogFrameID());
        cv.put(cSQLDBHelper.KEY_NAME, logFrameModel.getName());
        cv.put(cSQLDBHelper.KEY_DESCRIPTION, logFrameModel.getDescription());

        // insert LogFrame record
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblLOGFRAME, null, cv) < 0) {
                return false;
            }

            // add subLogFrame
            for (int childID : subLogFrameSet) {
                if (addLogFrameTree(logFrameModel.getLogFrameID(), childID))
                    continue;
                else
                    return false;
            }
        } catch (Exception e) {
            Log.d(TAG, "Exception in importing LOGFRAME from Excel: " + e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }

    public boolean addLogFrameTree(long parentID, long childID) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(cSQLDBHelper.KEY_PARENT_FK_ID, parentID);
        cv.put(cSQLDBHelper.KEY_CHILD_FK_ID, childID);

        Gson gson = new Gson();
        Log.d(TAG, gson.toJson(parentID) + " -> " + gson.toJson(childID));
        if (db.insert(cSQLDBHelper.TABLE_tblLOGFRAMETREE, null, cv) < 0) {
            return false;
        }

        return true;
    }

    /**
     * this function adds the logframe (i.e., project) details
     */
    public boolean createLogFrameModel(cLogFrameModel logFrameModel) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_ORGANIZATION_FK_ID, logFrameModel.getOrganizationID());
        cv.put(cSQLDBHelper.KEY_NAME, logFrameModel.getName());
        cv.put(cSQLDBHelper.KEY_DESCRIPTION, logFrameModel.getDescription());
        cv.put(cSQLDBHelper.KEY_START_DATE, sdf.format(logFrameModel.getStartDate()));
        cv.put(cSQLDBHelper.KEY_END_DATE, sdf.format(logFrameModel.getEndDate()));

        // insert logframe details
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblLOGFRAME, null, cv) < 0) {
                return false;
            }
        } catch (Exception e) {
            Log.d(TAG, "Exception in importing " + e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }

    @Override
    public boolean createSubLogFrameModel(long logParentFrameID, cLogFrameModel logSubFrameModel) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_ORGANIZATION_FK_ID, logSubFrameModel.getOrganizationID());
        cv.put(cSQLDBHelper.KEY_NAME, logSubFrameModel.getName());
        cv.put(cSQLDBHelper.KEY_DESCRIPTION, logSubFrameModel.getDescription());
        cv.put(cSQLDBHelper.KEY_START_DATE, sdf.format(logSubFrameModel.getStartDate()));
        cv.put(cSQLDBHelper.KEY_END_DATE, sdf.format(logSubFrameModel.getEndDate()));

        // insert logframe details
        try {
            long childLOgFrameID = db.insert(cSQLDBHelper.TABLE_tblLOGFRAME, null, cv);
            if (childLOgFrameID < 0) {
                return false;
            }

            // add subLogFrame
            if (!addLogFrameTree(logParentFrameID, childLOgFrameID)){
                return false;
            }
        } catch (Exception e) {
            Log.d(TAG, "Exception in adding sub logframe " + e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }


    /*####################################### READ ACTIONS #######################################*/

    public Set<cLogFrameModel> getLogFrameModelSet(int userID, int primaryRoleBITS,
                                                   int secondaryRoleBITS, int statusBITS) {

        Set<cLogFrameModel> logFrameModelSet = new HashSet<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String selectQuery = " SELECT " +
                "logframe." + cSQLDBHelper.KEY_ID + ", " +
                "logframe_tree." + cSQLDBHelper.KEY_PARENT_FK_ID + ", " +
                "logframe_tree." + cSQLDBHelper.KEY_CHILD_FK_ID + ", " +
                "logframe." + cSQLDBHelper.KEY_ORGANIZATION_FK_ID + ", " +
                "logframe." + cSQLDBHelper.KEY_SERVER_ID + ", " +
                "logframe." + cSQLDBHelper.KEY_OWNER_ID + ", " +
                "logframe." + cSQLDBHelper.KEY_ORG_ID + ", " +
                "logframe." + cSQLDBHelper.KEY_GROUP_BITS + ", " +
                "logframe." + cSQLDBHelper.KEY_PERMS_BITS + ", " +
                "logframe." + cSQLDBHelper.KEY_STATUS_BITS + ", " +
                "logframe." + cSQLDBHelper.KEY_NAME + ", " +
                "logframe." + cSQLDBHelper.KEY_DESCRIPTION + ", " +
                "logframe." + cSQLDBHelper.KEY_START_DATE + ", " +
                "logframe." + cSQLDBHelper.KEY_END_DATE + ", " +
                "logframe." + cSQLDBHelper.KEY_CREATED_DATE + ", " +
                "logframe." + cSQLDBHelper.KEY_MODIFIED_DATE + ", " +
                "logframe." + cSQLDBHelper.KEY_SYNCED_DATE +
                " FROM " + cSQLDBHelper.TABLE_tblLOGFRAME + " logframe " +
                " LEFT JOIN " + cSQLDBHelper.TABLE_tblLOGFRAMETREE + " logframe_tree " +
                " ON logframe."+cSQLDBHelper.KEY_ID + " = logframe_tree."+cSQLDBHelper.KEY_CHILD_FK_ID +
                " WHERE ("+
                /* read access permissions */
                /* organization creator is always allowed to do everything (i.e., where: userID = orgID) */
                " ((logframe." + cSQLDBHelper.KEY_ORG_ID + " = ?) " +
                /* owner permission */
                " OR ((((logframe." + cSQLDBHelper.KEY_OWNER_ID + " = ?) " +
                " AND ((logframe." + cSQLDBHelper.KEY_PERMS_BITS + " & " + cBitwise.OWNER_READ + ") != 0))" +
                /* group (owner/primary organization) permission */
                " OR (((logframe." + cSQLDBHelper.KEY_GROUP_BITS + " & ?) != 0) " +
                " AND ((logframe." + cSQLDBHelper.KEY_PERMS_BITS + " & " + cBitwise.GROUP_READ + ") != 0))" +
                /* other (secondary organizations) permission */
                " OR (((logframe." + cSQLDBHelper.KEY_GROUP_BITS + " & ?) != 0) " +
                " AND ((logframe." + cSQLDBHelper.KEY_PERMS_BITS + " & " + cBitwise.OTHER_READ + ") != 0)))" +
                /* owner, group and other permissions allowed when the statuses hold */
                " AND ((logframe." + cSQLDBHelper.KEY_STATUS_BITS + " = 0) " +
                " OR ((logframe." + cSQLDBHelper.KEY_STATUS_BITS + " & ?) != 0)))))";

        Cursor cursor = db.rawQuery(selectQuery, new String[]{
                String.valueOf(userID),           /* access due to organization creator */
                String.valueOf(userID),           /* access due to record owner */
                String.valueOf(primaryRoleBITS),  /* access due to membership in primary role */
                String.valueOf(secondaryRoleBITS),/* access due to membership in secondary role */
                String.valueOf(statusBITS)});     /* access due to assigned statuses */

        try {
            if (cursor.moveToFirst()) {
                do {
                    cLogFrameModel logFrameModel = new cLogFrameModel();

                    logFrameModel.setLogFrameID(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ID)));
                    logFrameModel.setLogFrameParentID(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PARENT_FK_ID)));
                    logFrameModel.setOrganizationID(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ORGANIZATION_FK_ID)));
                    logFrameModel.setServerID(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_SERVER_ID)));
                    logFrameModel.setOwnerID(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OWNER_ID)));
                    logFrameModel.setOrgID(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ORG_ID)));
                    logFrameModel.setGroupBITS(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_GROUP_BITS)));
                    logFrameModel.setPermsBITS(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PERMS_BITS)));
                    logFrameModel.setStatusBITS(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_STATUS_BITS)));
                    logFrameModel.setName(
                            cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_NAME)));
                    logFrameModel.setDescription(
                            cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_DESCRIPTION)));
                    logFrameModel.setStartDate(Timestamp.valueOf(
                            cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_START_DATE))));
                    logFrameModel.setEndDate(Timestamp.valueOf(
                           cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_END_DATE))));
                    logFrameModel.setCreatedDate(Timestamp.valueOf(
                            cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_CREATED_DATE))));
                    logFrameModel.setModifiedDate(Timestamp.valueOf(
                            cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_MODIFIED_DATE))));
                    logFrameModel.setSyncedDate(Timestamp.valueOf(
                            cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_SYNCED_DATE))));

                    int logFrameID = logFrameModel.getLogFrameID();
                    int organizationID = logFrameModel.getOrganizationID();

                    logFrameModel.setOrganizationModel(organizationRepository.getOrganizationByID(
                            organizationID));

                    /* populate child log-frames */
                    logFrameModel.setLogFrameModelSet(getLogFrameModelSetByID(logFrameID, userID,
                            primaryRoleBITS, secondaryRoleBITS, statusBITS));

                    /* populate impact component */
                    logFrameModel.setImpactModelSet(getImpactModelSetByID(logFrameID, userID,
                            primaryRoleBITS, secondaryRoleBITS, statusBITS));

                    /* populate outcome component */
                    logFrameModel.setOutcomeModelSet(getOutcomeModelSetByID(logFrameID, userID,
                            primaryRoleBITS, secondaryRoleBITS, statusBITS));

                    /* populate output component */
                    logFrameModel.setOutputModelSet(getOutputModelSetByID(logFrameID, userID,
                            primaryRoleBITS, secondaryRoleBITS, statusBITS));

                    /* populate activity component */
                    //logFrameModel.setActivityModelSet(getActivityModelSetByID(logFrameID, userID,
                    //        primaryRoleBITS, secondaryRoleBITS, statusBITS));

                    /* populate input component */
                    logFrameModel.setInputModelSet(getInputModelSetByID(logFrameID, userID,
                            primaryRoleBITS, secondaryRoleBITS, statusBITS));

                    /** auxiliary components **/

                    /* populate question component */
                    logFrameModel.setQuestionModelSet(null);

                    /* populate indicator component */
                    logFrameModel.setIndicatorModelSet(null);

                    /* populate RAID component */
                    logFrameModel.setRaidModelSet(null);

                    /* logframe information details */
                    logFrameModelSet.add(logFrameModel);

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error in reading : " + e.getMessage());
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        // close the database connection
        db.close();

        return logFrameModelSet;
    }

    /**
     * This returns the children logFrames of a given parent identification.
     *
     * @param logFrameID
     * @param userID
     * @param primaryRole
     * @param secondaryRoles
     * @param statusBITS
     * @return
     */
    public Set<cLogFrameModel> getLogFrameModelSetByID(int logFrameID, int userID, int primaryRole,
                                                       int secondaryRoles, int statusBITS) {

        Set<cLogFrameModel> logFrameModelSet = new HashSet<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String selectQuery =
                " SELECT " +
                        "logframe." + cSQLDBHelper.KEY_ID + ", " +
                        "logframe." + cSQLDBHelper.KEY_ORGANIZATION_FK_ID + ", " +
                        "logframe_tree." + cSQLDBHelper.KEY_PARENT_FK_ID + ", " +
                        "logframe_tree." + cSQLDBHelper.KEY_CHILD_FK_ID + ", " +
                        "logframe." + cSQLDBHelper.KEY_SERVER_ID + ", " +
                        "logframe." + cSQLDBHelper.KEY_OWNER_ID + ", " +
                        "logframe." + cSQLDBHelper.KEY_ORG_ID + ", " +
                        "logframe." + cSQLDBHelper.KEY_GROUP_BITS + ", " +
                        "logframe." + cSQLDBHelper.KEY_PERMS_BITS + ", " +
                        "logframe." + cSQLDBHelper.KEY_STATUS_BITS + ", " +
                        "logframe." + cSQLDBHelper.KEY_NAME + ", " +
                        "logframe." + cSQLDBHelper.KEY_DESCRIPTION + ", " +
                        "logframe." + cSQLDBHelper.KEY_START_DATE + ", " +
                        "logframe." + cSQLDBHelper.KEY_END_DATE + ", " +
                        "logframe." + cSQLDBHelper.KEY_CREATED_DATE + ", " +
                        "logframe." + cSQLDBHelper.KEY_MODIFIED_DATE + ", " +
                        "logframe." + cSQLDBHelper.KEY_SYNCED_DATE +
                        " FROM " + cSQLDBHelper.TABLE_tblLOGFRAME + " logframe, " +
                        cSQLDBHelper.TABLE_tblLOGFRAMETREE + " logframe_tree " +
                        " WHERE ((logframe." + cSQLDBHelper.KEY_ID + " = logframe_tree." +
                        cSQLDBHelper.KEY_CHILD_FK_ID +
                        " AND logframe_tree." + cSQLDBHelper.KEY_PARENT_FK_ID + " = ?) AND " +
                        /* read access permissions */
                        /* organization creator is always allowed to do everything (i.e., where:
                           userID = orgID)*/
                        " ((logframe." +cSQLDBHelper.KEY_ORG_ID + " = ?) " +
                        /* owner permission */
                        " OR ((((logframe." +cSQLDBHelper.KEY_OWNER_ID + " = ?) " +
                        " AND ((logframe." + cSQLDBHelper.KEY_PERMS_BITS + " & " + cBitwise.OWNER_READ + ") != 0))" +
                        /* group (owner/primary organization) permission */
                        " OR (((logframe." + cSQLDBHelper.KEY_GROUP_BITS + " & ?) != 0) " +
                        " AND ((logframe." + cSQLDBHelper.KEY_PERMS_BITS + " & " + cBitwise.GROUP_READ + ") != 0))" +
                        /* other (secondary organizations) permission */
                        " OR (((logframe." + cSQLDBHelper.KEY_GROUP_BITS + " & ?) != 0) " +
                        " AND ((logframe." + cSQLDBHelper.KEY_PERMS_BITS + " & " + cBitwise.OTHER_READ + ") != 0)))" +
                        /* owner, group and other permissions allowed when the statuses hold */
                        " AND ((logframe." + cSQLDBHelper.KEY_STATUS_BITS + " = 0) " +
                        " OR ((logframe." + cSQLDBHelper.KEY_STATUS_BITS + " & ?) != 0)))))";

        Cursor cursor = db.rawQuery(selectQuery, new String[]{
                String.valueOf(logFrameID),
                String.valueOf(userID),
                String.valueOf(userID),
                String.valueOf(primaryRole),
                String.valueOf(secondaryRoles),
                String.valueOf(statusBITS)});

        try {
            if (cursor.moveToFirst()) {
                do {
                    cLogFrameModel logFrameModel = new cLogFrameModel();

                    logFrameModel.setLogFrameID(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ID)));
                    logFrameModel.setOrganizationID(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ORGANIZATION_FK_ID)));
                    logFrameModel.setServerID(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_SERVER_ID)));
                    logFrameModel.setOwnerID(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OWNER_ID)));
                    logFrameModel.setOrgID(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ORG_ID)));
                    logFrameModel.setGroupBITS(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_GROUP_BITS)));
                    logFrameModel.setPermsBITS(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PERMS_BITS)));
                    logFrameModel.setStatusBITS(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_STATUS_BITS)));
                    logFrameModel.setName(
                            cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_NAME)));
                    logFrameModel.setDescription(
                            cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_DESCRIPTION)));
                    logFrameModel.setStartDate(Timestamp.valueOf(
                            cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_START_DATE))));
                    logFrameModel.setEndDate(Timestamp.valueOf(
                            cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_END_DATE))));
                    logFrameModel.setCreatedDate(Timestamp.valueOf(
                            cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_CREATED_DATE))));
                    logFrameModel.setModifiedDate(Timestamp.valueOf(
                            cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_MODIFIED_DATE))));
                    logFrameModel.setSyncedDate(Timestamp.valueOf(
                            cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_SYNCED_DATE))));

                    logFrameModelSet.add(logFrameModel);

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to read LogFrame and LogFrameTree entities: " + e.getMessage());
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        // close the database connection
        db.close();

        return logFrameModelSet;
    }

    /**
     * This returns a set of impact entities.
     *
     * @param logFrameID
     * @param userID
     * @param primaryRole
     * @param secondaryRoles
     * @param statusBITS
     * @return
     */
    public Set<cImpactModel> getImpactModelSetByID(int logFrameID, int userID, int primaryRole,
                                                   int secondaryRoles, int statusBITS) {

        Set<cImpactModel> impactModelSet = new HashSet<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String selectQuery =
                " SELECT * " +
                        " FROM " + cSQLDBHelper.TABLE_tblIMPACT +
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
                String.valueOf(logFrameID),
                String.valueOf(userID),
                String.valueOf(userID),
                String.valueOf(primaryRole),
                String.valueOf(secondaryRoles),
                String.valueOf(statusBITS)});

        try {
            if (cursor.moveToFirst()) {
                do {
                    cImpactModel impactModel = new cImpactModel();

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

                    impactModelSet.add(impactModel);

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to read an Impact entity: " + e.getMessage());
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
     * This returns a set of outcome entities.
     *
     * @param logFrameID
     * @param userID
     * @param primaryRole
     * @param secondaryRoles
     * @param statusBITS
     * @return
     */
    public Set<cOutcomeModel> getOutcomeModelSetByID(int logFrameID, int userID, int primaryRole,
                                                     int secondaryRoles, int statusBITS) {

        Set<cOutcomeModel> outcomeModelSet = new HashSet<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String selectQuery =
                " SELECT * " +
                        " FROM " + cSQLDBHelper.TABLE_tblOUTCOME +
                        " WHERE ((" + cSQLDBHelper.KEY_LOGFRAME_FK_ID + " = ?) AND " +
                        /* read access permissions */
                        /* organization creator is always allowed to do everything (i.e., where:
                           userID = orgID)*/
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
                String.valueOf(logFrameID),
                String.valueOf(userID),
                String.valueOf(userID),
                String.valueOf(primaryRole),
                String.valueOf(secondaryRoles),
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

                    outcomeModelSet.add(outcomeModel);

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to read an Outcome entity: " + e.getMessage());
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
     * This returns a set of Output entities.
     *
     * @param logFrameID
     * @param userID
     * @param primaryRole
     * @param secondaryRoles
     * @param statusBITS
     * @return
     */
    public Set<cOutputModel> getOutputModelSetByID(int logFrameID, int userID, int primaryRole,
                                                   int secondaryRoles, int statusBITS) {

        Set<cOutputModel> outputModelSet = new HashSet<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String selectQuery =
                " SELECT * " +
                        " FROM " + cSQLDBHelper.TABLE_tblOUTPUT +
                        " WHERE ((" + cSQLDBHelper.KEY_LOGFRAME_FK_ID + " = ?) AND  " +
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
                String.valueOf(logFrameID),
                String.valueOf(userID),
                String.valueOf(userID),
                String.valueOf(primaryRole),
                String.valueOf(secondaryRoles),
                String.valueOf(statusBITS)});

        try {
            if (cursor.moveToFirst()) {
                do {
                    cOutputModel outputModel = new cOutputModel();

                    outputModel.setOutputID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ID)));
                    outputModel.setParentID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PARENT_FK_ID)));
                    outputModel.setLogFrameID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_LOGFRAME_FK_ID)));
                    outputModel.setOutcomeID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OUTCOME_FK_ID)));
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
            Log.d(TAG, "Error while trying to read an Output entity: " + e.getMessage());
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
     * This returns a set of Input entities.
     *
     * @param logFrameID
     * @param userID
     * @param primaryRole
     * @param secondaryRoles
     * @param statusBITS
     * @return
     */
    public Set<cActivityModel> getActivityModelSetByID(int logFrameID, int userID, int primaryRole,
                                                       int secondaryRoles, int statusBITS) {

        Set<cActivityModel> activityModelSet = new HashSet<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String selectQuery =
                " SELECT * " +
                        " FROM " + cSQLDBHelper.TABLE_tblACTIVITY +
                        " WHERE (" +//+ cSQLDBHelper.KEY_LOGFRAME_FK_ID + " = ?) AND " +
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
                //String.valueOf(logFrameID),
                String.valueOf(userID),
                String.valueOf(userID),
                String.valueOf(primaryRole),
                String.valueOf(secondaryRoles),
                String.valueOf(statusBITS)});

        try {
            if (cursor.moveToFirst()) {
                do {
                    cActivityModel activityModel = new cActivityModel();

                    activityModel.setActivityID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ID)));
                    activityModel.setParentID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PARENT_FK_ID)));
                    //activityModel.setLogFrameID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_LOGFRAME_FK_ID)));
                    activityModel.setOutputID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OUTPUT_FK_ID)));
                    activityModel.setServerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_SERVER_ID)));
                    activityModel.setOwnerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OWNER_ID)));
                    activityModel.setOrgID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ORG_ID)));
                    activityModel.setGroupBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_GROUP_BITS)));
                    activityModel.setPermsBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PERMS_BITS)));
                    activityModel.setStatusBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_STATUS_BITS)));
                    activityModel.setName(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_NAME)));
                    activityModel.setDescription(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_DESCRIPTION)));
                    activityModel.setStartDate(
                            Timestamp.valueOf(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_START_DATE))));
                    activityModel.setEndDate(
                            Timestamp.valueOf(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_END_DATE))));
                    activityModel.setCreatedDate(
                            Timestamp.valueOf(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_CREATED_DATE))));
                    activityModel.setModifiedDate(
                            Timestamp.valueOf(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_MODIFIED_DATE))));
                    activityModel.setSyncedDate(
                            Timestamp.valueOf(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_SYNCED_DATE))));

                    activityModelSet.add(activityModel);

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to read an Activity entity: " + e.getMessage());
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        // close the database connection
        db.close();

        return activityModelSet;
    }


    /**
     * This returns a set of Input entities.
     *
     * @param logFrameID
     * @param userID
     * @param primaryRole
     * @param secondaryRoles
     * @param statusBITS
     * @return
     */
    public Set<cInputModel> getInputModelSetByID(int logFrameID, int userID, int primaryRole,
                                                 int secondaryRoles, int statusBITS) {

        Set<cInputModel> inputModelSet = new HashSet<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String selectQuery =
                " SELECT * " +
                        " FROM " + cSQLDBHelper.TABLE_tblINPUT +
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
                String.valueOf(logFrameID),
                String.valueOf(userID),
                String.valueOf(userID),
                String.valueOf(primaryRole),
                String.valueOf(secondaryRoles),
                String.valueOf(statusBITS)});

        try {
            if (cursor.moveToFirst()) {
                do {
                    cInputModel inputModel = new cInputModel();

                    inputModel.setInputID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ID)));
                    inputModel.setActivityPlanningID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ACTIVITYPLANNING_FK_ID)));
                    inputModel.setLogFrameID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_LOGFRAME_FK_ID)));
                    inputModel.setServerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_SERVER_ID)));
                    inputModel.setOwnerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OWNER_ID)));
                    inputModel.setOrgID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ORG_ID)));
                    inputModel.setGroupBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_GROUP_BITS)));
                    inputModel.setPermsBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PERMS_BITS)));
                    inputModel.setStatusBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_STATUS_BITS)));
                    //inputModel.setName(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_NAME)));
                    //inputModel.setDescription(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_DESCRIPTION)));
                    inputModel.setStartDate(
                            Timestamp.valueOf(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_START_DATE))));
                    inputModel.setEndDate(
                            Timestamp.valueOf(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_END_DATE))));
                    inputModel.setCreatedDate(
                            Timestamp.valueOf(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_CREATED_DATE))));
                    inputModel.setModifiedDate(
                            Timestamp.valueOf(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_MODIFIED_DATE))));
                    inputModel.setSyncedDate(
                            Timestamp.valueOf(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_SYNCED_DATE))));

                    inputModelSet.add(inputModel);

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to read an Output entity: " + e.getMessage());
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        // close the database connection
        db.close();

        return inputModelSet;
    }


    /*
     * the function fetches all logFrames
     */
    public ArrayList<cLogFrameModel> getLogFrameModels() {
        // list of logFrames
        ArrayList<cLogFrameModel> logFrameModels = new ArrayList<>();

        // open the connection to the database
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + cSQLDBHelper.TABLE_tblLOGFRAME, null);

        try {
            if (cursor.moveToFirst()) {
                do {
                    cLogFrameModel logFrameModel = new cLogFrameModel();

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
        String selectQuery = "SELECT * FROM " +
                cSQLDBHelper.TABLE_tblIMPACT + " impact " +
                " WHERE impact." + cSQLDBHelper.KEY_ID + " = ?";

        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(logFrameID)});

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
        String selectQuery = "SELECT * FROM " +
                cSQLDBHelper.TABLE_tblOUTCOME + " outcome " +
                " WHERE outcome." + cSQLDBHelper.KEY_ID + " = ?";

        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(logFrameID)});

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

                    outcomeModel.setStartDate(
                            Timestamp.valueOf(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_START_DATE))));
                    outcomeModel.setEndDate(
                            Timestamp.valueOf(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_END_DATE))));
                    outcomeModel.setCreatedDate(
                            Timestamp.valueOf(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_CREATED_DATE))));
                    outcomeModel.setModifiedDate(
                            Timestamp.valueOf(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_MODIFIED_DATE))));

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
        String selectQuery = "SELECT * FROM " +
                cSQLDBHelper.TABLE_tblOUTPUT + " output " +
                " WHERE output." + cSQLDBHelper.KEY_ID + " = ?";

        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(logFrameID)});

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
                    outputModel.setStartDate(
                            Timestamp.valueOf(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_START_DATE))));
                    outputModel.setEndDate(
                            Timestamp.valueOf(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_END_DATE))));
                    outputModel.setCreatedDate(
                            Timestamp.valueOf(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_CREATED_DATE))));
                    outputModel.setModifiedDate(
                            Timestamp.valueOf(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_MODIFIED_DATE))));

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
        String selectQuery = "SELECT * FROM " +
                cSQLDBHelper.TABLE_tblACTIVITY + " activity " +
                " WHERE activity." + cSQLDBHelper.KEY_ID + " = ?";

        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(logFrameID)});

        try {
            if (cursor.moveToFirst()) {
                do {
                    cActivityModel activityModel = new cActivityModel();

                    activityModel.setActivityID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ID)));
                    activityModel.setServerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_SERVER_ID)));
                    activityModel.setOwnerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OWNER_ID)));
                    activityModel.setOrgID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ORG_ID)));
                    activityModel.setGroupBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_GROUP_BITS)));
                    activityModel.setPermsBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PERMS_BITS)));
                    activityModel.setStatusBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_STATUS_BITS)));
                    activityModel.setName(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_NAME)));
                    activityModel.setDescription(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_DESCRIPTION)));
                    activityModel.setStartDate(
                            Timestamp.valueOf(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_START_DATE))));
                    activityModel.setEndDate(
                            Timestamp.valueOf(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_END_DATE))));
                    activityModel.setCreatedDate(
                            Timestamp.valueOf(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_CREATED_DATE))));
                    activityModel.setModifiedDate(
                            Timestamp.valueOf(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_MODIFIED_DATE))));
                    activityModel.setSyncedDate(
                            Timestamp.valueOf(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_SYNCED_DATE))));

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
        String selectQuery = "SELECT * FROM " +
                cSQLDBHelper.TABLE_tblINPUT + " input " +
                " WHERE input." + cSQLDBHelper.KEY_ID + " = ?";

        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(logFrameID)});

        try {
            if (cursor.moveToFirst()) {
                do {
                    cInputModel inputModel = new cInputModel();

                    inputModel.setInputID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ID)));
                    inputModel.setServerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_SERVER_ID)));
                    inputModel.setOwnerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OWNER_ID)));
                    inputModel.setOrgID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ORG_ID)));
                    inputModel.setGroupBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_GROUP_BITS)));
                    inputModel.setPermsBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PERMS_BITS)));
                    inputModel.setStatusBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_STATUS_BITS)));
                    //inputModel.setName(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_NAME)));
                    //inputModel.setDescription(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_DESCRIPTION)));
                    inputModel.setStartDate(
                            Timestamp.valueOf(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_START_DATE))));
                    inputModel.setEndDate(
                            Timestamp.valueOf(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_END_DATE))));
                    inputModel.setCreatedDate(
                            Timestamp.valueOf(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_CREATED_DATE))));
                    inputModel.setModifiedDate(
                            Timestamp.valueOf(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_MODIFIED_DATE))));
                    inputModel.setSyncedDate(
                            Timestamp.valueOf(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_SYNCED_DATE))));

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
        String selectQuery = "SELECT * FROM " +
                cSQLDBHelper.TABLE_tblQUESTION + " question " +
                " WHERE question." + cSQLDBHelper.KEY_ID + " = ?";

        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(logFrameID)});

        try {
            if (cursor.moveToFirst()) {
                do {
                    cQuestionModel questionModel = new cQuestionModel();

                    questionModel.setQuestionID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ID)));
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
        String selectQuery = "SELECT * FROM " +
                cSQLDBHelper.TABLE_tblINPUT + " indicator " +
                " WHERE indicator." + cSQLDBHelper.KEY_ID + " = ?";

        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(logFrameID)});

        try {
            if (cursor.moveToFirst()) {
                do {
                    cIndicatorModel indicatorModel = new cIndicatorModel();

                    indicatorModel.setIndicatorID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ID)));
                    indicatorModel.setServerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_SERVER_ID)));
                    indicatorModel.setOwnerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OWNER_ID)));
                    indicatorModel.setOrgID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ORG_ID)));
                    indicatorModel.setGroupBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_GROUP_BITS)));
                    indicatorModel.setPermsBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PERMS_BITS)));
                    indicatorModel.setStatusBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_STATUS_BITS)));
                    indicatorModel.setName(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_NAME)));
                    indicatorModel.setDescription(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_DESCRIPTION)));
                    indicatorModel.setStartDate(
                            Timestamp.valueOf(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_START_DATE))));
                    indicatorModel.setEndDate(
                            Timestamp.valueOf(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_END_DATE))));
                    indicatorModel.setCreatedDate(
                            Timestamp.valueOf(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_CREATED_DATE))));
                    indicatorModel.setModifiedDate(
                            Timestamp.valueOf(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_MODIFIED_DATE))));
                    indicatorModel.setSyncedDate(
                            Timestamp.valueOf(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_SYNCED_DATE))));

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
        String selectQuery = "SELECT * FROM " +
                cSQLDBHelper.TABLE_tblRAID + " raid " +
                " WHERE raid." + cSQLDBHelper.KEY_ID + " = ?";

        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(logFrameID)});

        try {
            if (cursor.moveToFirst()) {
                do {
                    cRaidModel raidModel = new cRaidModel();

                    raidModel.setRaidID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ID)));
                    raidModel.setServerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_SERVER_ID)));
                    raidModel.setOwnerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OWNER_ID)));
                    raidModel.setOrgID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ORG_ID)));
                    raidModel.setGroupBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_GROUP_BITS)));
                    raidModel.setPermsBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PERMS_BITS)));
                    raidModel.setStatusBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_STATUS_BITS)));
                    raidModel.setName(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_NAME)));
                    raidModel.setDescription(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_DESCRIPTION)));
                    raidModel.setStartDate(
                            Timestamp.valueOf(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_START_DATE))));
                    raidModel.setEndDate(
                            Timestamp.valueOf(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_END_DATE))));
                    raidModel.setCreatedDate(
                            Timestamp.valueOf(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_CREATED_DATE))));
                    raidModel.setModifiedDate(
                            Timestamp.valueOf(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_MODIFIED_DATE))));
                    raidModel.setSyncedDate(
                            Timestamp.valueOf(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_SYNCED_DATE))));

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

    /* ######################################## UPDATE ACTIONS ########################################*/

    public boolean updateLogFrameModel(cLogFrameModel logFrameModel){
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        Date date= new Date();
        Timestamp timestamp = new Timestamp(date.getTime());

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_ID, logFrameModel.getLogFrameID());
        cv.put(cSQLDBHelper.KEY_ORGANIZATION_FK_ID, logFrameModel.getOrganizationID());
        cv.put(cSQLDBHelper.KEY_NAME, logFrameModel.getName());
        cv.put(cSQLDBHelper.KEY_DESCRIPTION, logFrameModel.getDescription());
        cv.put(cSQLDBHelper.KEY_START_DATE, sdf.format(logFrameModel.getStartDate()));
        cv.put(cSQLDBHelper.KEY_END_DATE, sdf.format(logFrameModel.getEndDate()));

        // update a specific record
        long result = db.update(cSQLDBHelper.TABLE_tblLOGFRAME, cv,
                        cSQLDBHelper.KEY_ID + "= ?",
                new String[]{String.valueOf(logFrameModel.getLogFrameID())});

        // close the database connection
        db.close();

        return result > -1;
    }

    /* ######################################## DELETE ACTIONS ########################################*/

    /*
     * the function delate a specific logframe
     */
    public boolean deleteLogFrame(long logFrameID) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete a record of a specific ID
        try {
            if (db.delete(cSQLDBHelper.TABLE_tblLOGFRAME, cSQLDBHelper.KEY_ID + " = ?",
                    new String[]{String.valueOf(logFrameID)}) < 0) {
                return false;
            }
        } catch (Exception e) {
            Log.d(TAG, "Exception in deleting " + e.getMessage().toString());
        }

        // close the database connection
        db.close();

        return true;
    }


    @Override
    public boolean deleteSubLogFrame(long logSubFrameID) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete a record of a specific ID
        try {
            if (db.delete(cSQLDBHelper.TABLE_tblLOGFRAME, cSQLDBHelper.KEY_ID + " = ?",
                    new String[]{String.valueOf(logSubFrameID)}) < 0) {
                return false;
            }
        } catch (Exception e) {
            Log.d(TAG, "Exception in deleting " + e.getMessage().toString());
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
            if (db.delete(cSQLDBHelper.TABLE_tblLOGFRAME, null, null) < 0) {
                return false;
            }
        } catch (Exception e) {
            Log.d(TAG, "Exception in deleting all logframes " + e.getMessage().toString());
        }

        // close the database connection
        db.close();

        return true;
    }

    /* ######################################## SYNC ACTIONS ########################################*/

}

/*
    String selectQuery = "SELECT * FROM "+
            cSQLDBHelper.TABLE_tblIMPACT + " impact " +
            " WHERE logframe."+cSQLDBHelper.KEY_ID + " = logframe_impact."+cSQLDBHelper.KEY_LOGFRAME_FK_ID +
            " AND impact."+cSQLDBHelper.KEY_ID + " = logframe_impact."+cSQLDBHelper.KEY_IMPACT_FK_ID +
            " AND logframe."+cSQLDBHelper.KEY_ID + " = ?";
*/