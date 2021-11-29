package com.me.mseotsanyana.mande.DAL.ìmpl.sqlite.logframe;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.me.mseotsanyana.mande.BLL.repository.logframe.iInputRepository;
import com.me.mseotsanyana.mande.BLL.model.logframe.cActivityModel;
import com.me.mseotsanyana.mande.BLL.model.wpb.cIncomeModel;
import com.me.mseotsanyana.mande.BLL.model.wpb.cHumanSetModel;
import com.me.mseotsanyana.mande.BLL.model.logframe.cLogFrameModel;
import com.me.mseotsanyana.mande.BLL.model.wpb.cMaterialModel;
import com.me.mseotsanyana.mande.BLL.model.logframe.cInputModel;
import com.me.mseotsanyana.mande.BLL.model.logframe.cQuestionModel;
import com.me.mseotsanyana.mande.BLL.model.logframe.cResourceTypeModel;
import com.me.mseotsanyana.mande.BLL.model.wpb.cJournalModel;
import com.me.mseotsanyana.mande.DAL.storage.database.cSQLDBHelper;
import com.me.mseotsanyana.mande.DAL.storage.preference.cBitwise;
import com.me.mseotsanyana.mande.DAL.ìmpl.sqlite.evaluator.cArrayTypeRepositoryImpl;
import com.me.mseotsanyana.mande.DAL.ìmpl.sqlite.evaluator.cMatrixTypeRepositoryImpl;
import com.me.mseotsanyana.mande.DAL.ìmpl.sqlite.evaluator.cPrimitiveTypeRepositoryImpl;
import com.me.mseotsanyana.mande.UTIL.cConstant;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Set;

public class cInputRepositoryImpl implements iInputRepository {
    private static SimpleDateFormat sdf = cConstant.FORMAT_DATE;
    private static String TAG = cInputRepositoryImpl.class.getSimpleName();

    // an object of the database helper
    private cSQLDBHelper dbHelper;

    private cLogFrameRepositoryImpl logFrameRepository;
    private cQuestionGroupingRepositoryImpl questionGroupingRepository;
    private cPrimitiveTypeRepositoryImpl primitiveTypeRepository;
    private cArrayTypeRepositoryImpl arrayTypeRepository;
    private cMatrixTypeRepositoryImpl matrixTypeRepository;

    public cInputRepositoryImpl(Context context) {
        dbHelper = new cSQLDBHelper(context);

        logFrameRepository = new cLogFrameRepositoryImpl(context);
        questionGroupingRepository = new cQuestionGroupingRepositoryImpl(context);
        primitiveTypeRepository = new cPrimitiveTypeRepositoryImpl(context);
        arrayTypeRepository = new cArrayTypeRepositoryImpl(context);
        matrixTypeRepository = new cMatrixTypeRepositoryImpl(context);
    }

    /* ######################################## CREATE ACTIONS ########################################*/

    /*
     * the function adding a specific input
     */
    public boolean addInput(cInputModel inputModel) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
//        cv.put(cSQLDBHelper.KEY_ID, inputModel.getComponentID());
//        cv.put(cSQLDBHelper.KEY_SERVER_ID, inputModel.getServerID());
//        cv.put(cSQLDBHelper.KEY_OWNER_ID, inputModel.getOwnerID());
//        cv.put(cSQLDBHelper.KEY_ORG_ID, inputModel.getOrgID());
//        cv.put(cSQLDBHelper.KEY_GROUP_BITS, inputModel.getGroupBITS());
//        cv.put(cSQLDBHelper.KEY_PERMS_BITS, inputModel.getPermsBITS());
//        cv.put(cSQLDBHelper.KEY_STATUS_BITS, inputModel.getStatusBITS());
//        //cv.put(cSQLDBHelper.KEY_NAME, inputModel.getName());
//        //cv.put(cSQLDBHelper.KEY_DESCRIPTION, inputModel.getDescription());
//        cv.put(cSQLDBHelper.KEY_START_DATE, sdf.format(inputModel.getStartDate()));
//        cv.put(cSQLDBHelper.KEY_END_DATE, sdf.format(inputModel.getEndDate()));
//        cv.put(cSQLDBHelper.KEY_CREATED_DATE, sdf.format(inputModel.getCreatedDate()));
//        cv.put(cSQLDBHelper.KEY_MODIFIED_DATE, sdf.format(inputModel.getModifiedDate()));
//        cv.put(cSQLDBHelper.KEY_SYNCED_DATE, sdf.format(inputModel.getSyncedDate()));

        // insert project details
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblINPUT, null, cv) < 0) {
                return false;
            }
        } catch (Exception e) {
            Log.d(TAG, "Exception in adding INPUT " + e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }

    /*
     * the function adding a specific input
     */
    public boolean addInputHumanSet(cHumanSetModel humanSetModel) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_INPUT_FK_ID, humanSetModel.getInputID());

        // insert project details
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblHUMANSET, null, cv) < 0) {
                return false;
            }
        } catch (Exception e) {
            Log.d(TAG, "Exception in adding HUMAN SET " + e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }


    /*
     * the function adding a specific input
     */
    public boolean addInputMaterial(cMaterialModel materialModel) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
//        cv.put(cSQLDBHelper.KEY_INPUT_FK_ID, materialModel.getComponentID());

        // insert project details
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblMATERIAL, null, cv) < 0) {
                return false;
            }
        } catch (Exception e) {
            Log.d(TAG, "Exception in adding MATERIAL " + e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }


    /*
     * the function adding a specific input
     */
    public boolean addInputBudget(cInputModel inputModel, cIncomeModel budgetModel) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
//        cv.put(cSQLDBHelper.KEY_ID, inputModel.getComponentID());
//        cv.put(cSQLDBHelper.KEY_SERVER_ID, inputModel.getServerID());
//        cv.put(cSQLDBHelper.KEY_OWNER_ID, inputModel.getOwnerID());
//        cv.put(cSQLDBHelper.KEY_ORG_ID, inputModel.getOrgID());
//        cv.put(cSQLDBHelper.KEY_GROUP_BITS, inputModel.getGroupBITS());
//        cv.put(cSQLDBHelper.KEY_PERMS_BITS, inputModel.getPermsBITS());
//        cv.put(cSQLDBHelper.KEY_STATUS_BITS, inputModel.getStatusBITS());
//        //cv.put(cSQLDBHelper.KEY_NAME, inputModel.getName());
//        //cv.put(cSQLDBHelper.KEY_DESCRIPTION, inputModel.getDescription());
//        cv.put(cSQLDBHelper.KEY_START_DATE, sdf.format(inputModel.getStartDate()));
//        cv.put(cSQLDBHelper.KEY_END_DATE, sdf.format(inputModel.getEndDate()));
//        cv.put(cSQLDBHelper.KEY_CREATED_DATE, sdf.format(inputModel.getCreatedDate()));
//        cv.put(cSQLDBHelper.KEY_MODIFIED_DATE, sdf.format(inputModel.getModifiedDate()));
//        cv.put(cSQLDBHelper.KEY_SYNCED_DATE, sdf.format(inputModel.getSyncedDate()));

        // insert project details
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblACTIVITY, null, cv) < 0) {
                return false;
            }
        } catch (Exception e) {
            Log.d(TAG, "Exception in adding INPUT " + e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }

    /*
     * the function adding a specific input outcome
     */
    public boolean addInputBudget(cMaterialModel materialModel) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
//        cv.put(cSQLDBHelper.KEY_INPUT_FK_ID, materialModel.getComponentID());
        /*
        cv.put(cSQLDBHelper.KEY_OUTPUT_FK_ID, materialModel.getMaterialID());
        cv.put(cSQLDBHelper.KEY_PARENT_FK_ID, materialModel.getParentID());
        cv.put(cSQLDBHelper.KEY_CHILD_FK_ID, materialModel.getChildID());
        cv.put(cSQLDBHelper.KEY_SERVER_ID, materialModel.getServerID());
        cv.put(cSQLDBHelper.KEY_OWNER_ID, materialModel.getOwnerID());
        cv.put(cSQLDBHelper.KEY_ORG_ID, materialModel.getOrgID());
        cv.put(cSQLDBHelper.KEY_GROUP_BITS, materialModel.getGroupBITS());
        cv.put(cSQLDBHelper.KEY_PERMS_BITS, materialModel.getPermsBITS());
        cv.put(cSQLDBHelper.KEY_STATUS_BITS, materialModel.getStatusBITS());
        cv.put(cSQLDBHelper.KEY_CREATED_DATE, formatter.format(materialModel.getCreatedDate()));
        cv.put(cSQLDBHelper.KEY_MODIFIED_DATE, formatter.format(materialModel.getModifiedDate()));
        cv.put(cSQLDBHelper.KEY_SYNCED_DATE, formatter.format(materialModel.getSyncedDate()));
         */

        // insert project details
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblACTIVITY_OUTPUT, null, cv) < 0) {
                return false;
            }
        } catch (Exception e) {
            Log.d(TAG, "Exception in adding OUTPUT_OUTPUTs " + e.getMessage().toString());
        }

        // close the database connection
        db.close();

        return true;
    }


    /* ######################################## READ ACTIONS ########################################*/


    @Override
    public Set<cInputModel> getInputModelSet(long logFrameID, long userID, int primaryRoleBITS,
                                             int secondaryRoleBITS, int statusBITS) {

        // list of inputs
        Set<cInputModel> inputModelSet = new HashSet<>();

        // open the connection to the database
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String selectQuery = "SELECT I." + cSQLDBHelper.KEY_ID + ", " +
                "I." + cSQLDBHelper.KEY_COMPONENT_FK_ID + ", I." + cSQLDBHelper.KEY_LOGFRAME_FK_ID + ", " +
                "I." + cSQLDBHelper.KEY_RESOURCE_FK_ID + ", R." + cSQLDBHelper.KEY_RESOURCE_TYPE_FK_ID + ", " +
                "I." + cSQLDBHelper.KEY_SERVER_ID + ", I." + cSQLDBHelper.KEY_OWNER_ID + ", " +
                "I." + cSQLDBHelper.KEY_ORG_ID + ", I." + cSQLDBHelper.KEY_GROUP_BITS + ", " +
                "I." + cSQLDBHelper.KEY_PERMS_BITS + ", I." + cSQLDBHelper.KEY_STATUS_BITS + ", " +
                "R." + cSQLDBHelper.KEY_NAME + ", R." + cSQLDBHelper.KEY_DESCRIPTION + ", " +
                "I." + cSQLDBHelper.KEY_START_DATE + ", I." + cSQLDBHelper.KEY_END_DATE + ", " +
                "I." + cSQLDBHelper.KEY_CREATED_DATE + ", I." + cSQLDBHelper.KEY_MODIFIED_DATE + ", " +
                "I." + cSQLDBHelper.KEY_SYNCED_DATE +
                " FROM " + cSQLDBHelper.TABLE_tblINPUT + " I INNER JOIN " +
                cSQLDBHelper.TABLE_tblRESOURCETYPE + " R ON R." + cSQLDBHelper.KEY_ID + " = I." +
                cSQLDBHelper.KEY_RESOURCE_FK_ID +
                " WHERE ((I." + cSQLDBHelper.KEY_LOGFRAME_FK_ID + " = ?) AND " +
                /* read access permissions */
                /* organization creator is always allowed to do everything (i.e., where: userID = orgID)*/
                " ((I." + cSQLDBHelper.KEY_ORG_ID + " = ?) " +
                /* owner permission */
                " OR ((((I." + cSQLDBHelper.KEY_OWNER_ID + " = ?) " +
                " AND ((I." + cSQLDBHelper.KEY_PERMS_BITS + " & " + cBitwise.OWNER_READ + ") != 0))" +
                /* group (owner/primary organization) permission */
                " OR (((I." + cSQLDBHelper.KEY_GROUP_BITS + " & ?) != 0) " +
                " AND ((I." + cSQLDBHelper.KEY_PERMS_BITS + " & " + cBitwise.GROUP_READ + ") != 0))" +
                /* other (secondary organizations) permission */
                " OR (((I." + cSQLDBHelper.KEY_GROUP_BITS + " & ?) != 0) " +
                " AND ((I." + cSQLDBHelper.KEY_PERMS_BITS + " & " + cBitwise.OTHER_READ + ") != 0)))" +
                /* owner, group and other permissions allowed when the statuses hold */
                " AND ((I." + cSQLDBHelper.KEY_STATUS_BITS + " = 0) " +
                " OR ((I." + cSQLDBHelper.KEY_STATUS_BITS + " & ?) != 0)))))" +
                " ORDER BY R." + cSQLDBHelper.KEY_RESOURCE_TYPE_FK_ID + " ASC ";

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
                    cInputModel input = new cInputModel();

//                    input.setComponentID(
//                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ID)));
//                    input.getActivityModel().setComponentID(cursor.getInt(
//                            cursor.getColumnIndex(cSQLDBHelper.KEY_COMPONENT_FK_ID)));
////                    input.getLogFrameModel().setLogFrameID(
////                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_LOGFRAME_FK_ID)));
//                    input.getResourceTypeModel().setResourceTypeID(
//                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_RESOURCE_TYPE_FK_ID)));
//                    input.setServerID(
//                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_SERVER_ID)));
//                    input.setOwnerID(
//                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OWNER_ID)));
//                    input.setOrgID(
//                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ORG_ID)));
//                    input.setGroupBITS(
//                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_GROUP_BITS)));
//                    input.setPermsBITS(
//                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PERMS_BITS)));
//                    input.setStatusBITS(
//                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_STATUS_BITS)));
//                    input.setStartDate(Timestamp.valueOf(
//                            cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_START_DATE))));
//                    input.setEndDate(Timestamp.valueOf(
//                            cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_END_DATE))));
//                    input.setCreatedDate(Timestamp.valueOf(
//                            cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_CREATED_DATE))));
//                    input.setModifiedDate(Timestamp.valueOf(
//                            cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_MODIFIED_DATE))));
//                    input.setSyncedDate(Timestamp.valueOf(
//                            cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_SYNCED_DATE))));

                    /* objects */

                    /* populate a input object */
//                    input.setLogFrameModel(getLogFrameModelByID(
//                            input.getLogFrameModel().getLogFrameID(), userID,
//                            primaryRoleBITS, secondaryRoleBITS, statusBITS));
//                    /* populate an resource object */
//                    input.setResourceTypeModel(getResourceTypeModelByID(
//                            input.getResourceTypeModel().getResourceTypeID(), userID,
//                            primaryRoleBITS, secondaryRoleBITS, statusBITS));
//                    /* populate an activity object */
//                    input.setActivityModel(getActivityModelByID(input.getComponentID(),
//                            userID, primaryRoleBITS, secondaryRoleBITS, statusBITS));
//
//                    /* sets */
//
//                    /* populate question components */
//                    input.setQuestionModelSet(getQuestionModelSetByID(
//                            input.getComponentID(), userID, primaryRoleBITS, secondaryRoleBITS,
//                            statusBITS));
//                    /* populate journal components */
//                    input.setJournalModelSet(getJournalModelSetByID(input.getComponentID(),
//                            userID, primaryRoleBITS, secondaryRoleBITS, statusBITS));
//
//                    /* maps */
//
//                    /* populate child activity for the input */
//                    input.setChildActivityModelSet(getChildActivitySetByID(input.getComponentID(),
//                            userID, primaryRoleBITS, secondaryRoleBITS, statusBITS));

                    /* add input entity */
                    inputModelSet.add(input);

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error in reading all INPUT entities: " + e.getMessage());
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        // close the database connection
        db.close();

        return inputModelSet;
    }

    /*############# utility functions used in the subclass repository implementations ############*/

    /**
     * returns a logframe model
     *
     * @param logFrameID        logframe identification
     * @param userID            user identification
     * @param primaryRoleBITS   primary role bits
     * @param secondaryRoleBITS secondary role bits
     * @param statusBITS        status bits
     * @return logframe model
     */
    protected cLogFrameModel getLogFrameModelByID(long logFrameID, long userID, int primaryRoleBITS,
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
//                logFrame.setLogFrameID(
//                        cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ID)));
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
//                logFrame.setSyncedDate(Timestamp.valueOf(
//                        cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_SYNCED_DATE))));
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

//    /**
//     * returns a resource model
//     *
//     * @param resourceID        resource identification
//     * @param userID            user identification
//     * @param primaryRoleBITS   primary role bits
//     * @param secondaryRoleBITS secondary role bits
//     * @param statusBITS        status bits
//     * @return resource model
//     */
//    protected cResourceModel getResourceModelByID(long resourceID, long userID, int primaryRoleBITS,
//                                                  int secondaryRoleBITS, int statusBITS) {
//
//        // open the connection to the database
//        SQLiteDatabase db = dbHelper.getReadableDatabase();
//
//        // construct a selection query
//        String selectQuery = "SELECT * FROM " + cSQLDBHelper.TABLE_tblRESOURCE +
//                " WHERE ((" + cSQLDBHelper.KEY_ID + "= ? ) AND " +
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
//        Cursor cursor = db.rawQuery(selectQuery, new String[]{
//                String.valueOf(resourceID),
//                String.valueOf(userID),           /* access due to organization creator */
//                String.valueOf(userID),           /* access due to record owner */
//                String.valueOf(primaryRoleBITS),  /* access due to membership in primary role */
//                String.valueOf(secondaryRoleBITS),/* access due to membership in secondary role */
//                String.valueOf(statusBITS)});     /* access due to assigned statuses */
//
//        cResourceModel resource = new cResourceModel();
//
//        try {
//            if (cursor.moveToFirst()) {
//                resource.setResourceID(
//                        cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ID)));
//                resource.setResourceTypeID(
//                        cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_RESOURCE_TYPE_FK_ID)));
//                resource.setServerID(
//                        cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_SERVER_ID)));
//                resource.setOwnerID(
//                        cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OWNER_ID)));
//                resource.setOrgID(
//                        cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ORG_ID)));
//                resource.setGroupBITS(
//                        cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_GROUP_BITS)));
//                resource.setPermsBITS(
//                        cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PERMS_BITS)));
//                resource.setStatusBITS(
//                        cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_STATUS_BITS)));
//                resource.setName(
//                        cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_NAME)));
//                resource.setDescription(
//                        cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_DESCRIPTION)));
//                resource.setCreatedDate(Timestamp.valueOf(
//                        cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_CREATED_DATE))));
//                resource.setModifiedDate(Timestamp.valueOf(
//                        cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_MODIFIED_DATE))));
//                resource.setSyncedDate(Timestamp.valueOf(
//                        cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_SYNCED_DATE))));
//
//                /* populate a resource type object */
//                resource.setResourceTypeModel(getResourceTypeModelByID(resource.getResourceTypeID(),
//                        userID, primaryRoleBITS, secondaryRoleBITS, statusBITS));
//            }
//        } catch (Exception e) {
//            Log.d(TAG, "Error while trying to read an OUTCOME entity: " + e.getMessage());
//        } finally {
//            if (cursor != null && !cursor.isClosed()) {
//                cursor.close();
//            }
//        }
//
//        // close the database connection
//        db.close();
//
//        return resource;
//    }

    /**
     * return a set of question models
     *
     * @param inputID input identification
     * @param userID user identification
     * @param primaryRoleBITS primary role bits
     * @param secondaryRoleBITS secondary role bits
     * @param statusBITS status bits
     * @return set of questions
     */
    protected Set<cQuestionModel> getQuestionModelSetByID(
            long inputID, long userID, int primaryRoleBITS, int secondaryRoleBITS, int statusBITS) {

        Set<cQuestionModel> questionModelSet = new HashSet<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String selectQuery = "SELECT " +
                "I." + cSQLDBHelper.KEY_ID + ", Q." + cSQLDBHelper.KEY_LOGFRAME_FK_ID + ", " +
                "Q." + cSQLDBHelper.KEY_QUESTION_TYPE_FK_ID + ", " +
                "Q." + cSQLDBHelper.KEY_QUESTION_GROUPING_FK_ID + ", " +
                "Q." + cSQLDBHelper.KEY_SERVER_ID + ", Q." + cSQLDBHelper.KEY_OWNER_ID + ", " +
                "Q." + cSQLDBHelper.KEY_ORG_ID + ", Q." + cSQLDBHelper.KEY_GROUP_BITS + ", " +
                "Q." + cSQLDBHelper.KEY_PERMS_BITS + ", Q." + cSQLDBHelper.KEY_STATUS_BITS + ", " +
                "Q." + cSQLDBHelper.KEY_LABEL + ", Q." + cSQLDBHelper.KEY_QUESTION + ", " +
                "Q." + cSQLDBHelper.KEY_START_DATE + ", Q." + cSQLDBHelper.KEY_END_DATE + ", " +
                "Q." + cSQLDBHelper.KEY_CREATED_DATE + ", Q." + cSQLDBHelper.KEY_MODIFIED_DATE + ", " +
                "Q." + cSQLDBHelper.KEY_SYNCED_DATE +
                " FROM " +
                cSQLDBHelper.TABLE_tblINPUT + " I, " +
                cSQLDBHelper.TABLE_tblQUESTION + " Q " +
                //FIXME: cSQLDBHelper.TABLE_tblINPUT_QUESTION + " I_Q " +
                " WHERE ((I." + cSQLDBHelper.KEY_ID + " = I_Q." + cSQLDBHelper.KEY_INPUT_FK_ID +
                " AND Q." + cSQLDBHelper.KEY_ID + " = I_Q." + cSQLDBHelper.KEY_QUESTION_FK_ID +
                " AND I." + cSQLDBHelper.KEY_ID + " = ?) AND " +
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
                String.valueOf(inputID),
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
//                    question.getLogFrameModel().setLogFrameID(
//                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_LOGFRAME_FK_ID)));
//                    question.getQuestionTypeModel().setQuestionTypeID(
//                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_QUESTION_TYPE_FK_ID)));
//                    question.getQuestionGroupingModel().setQuestionGroupingID(
//                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_QUESTION_GROUPING_FK_ID)));
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
                    question.setLabel(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_LABEL)));
                    question.setQuestion(
                            cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_QUESTION)));
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

                    /* populate a input object */
                    /* logframe object */
//                    question.setLogFrameModel(logFrameRepository.getLogFrameModelByID(
//                            question.getLogFrameModel().getLogFrameID(), userID, primaryRoleBITS,
//                            secondaryRoleBITS, statusBITS));

//                    question.setQuestionGroupingModel(
//                            questionGroupingRepository.getQuestionGroupingModelByID(
//                                    question.getQuestionGroupingModel().getQuestionGroupingID(),
//                                    userID, primaryRoleBITS, secondaryRoleBITS, statusBITS));

                    /* instantiate question type objects. only one of them should be non-null
                    cPrimitiveQuestionModel primitive = primitiveTypeRepository.getPrimitiveTypeModelSet(
                            question.getQuestionTypeID(), userID, primaryRoleBITS,
                            secondaryRoleBITS, statusBITS);
                    cArrayQuestionModel array = arrayTypeRepository.getArrayTypeModelSet(
                            question.getQuestionTypeID(), userID, primaryRoleBITS,
                            secondaryRoleBITS, statusBITS);
                    cMatrixQuestionModel matrix = matrixTypeRepository.getMatrixTypeModelSet(
                            question.getQuestionTypeID(), userID, primaryRoleBITS,
                            secondaryRoleBITS, statusBITS); */
                    /* up casting to the question type
                    if (primitive != null){
                        question.setQuestionTypeModel(primitive);
                    }
                    else if (array != null){
                        question.setQuestionTypeModel(array);
                    }
                    else if (matrix != null){
                        question.setQuestionTypeModel(matrix);
                    }*/



                    questionModelSet.add(question);

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to read a INPUT QUESTION entity: " + e.getMessage());
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
     * return a resource type model
     *
     * @param resourceTypeID    resource type identification
     * @param userID            user identification
     * @param primaryRoleBITS   primary role bits
     * @param secondaryRoleBITS secondary role bits
     * @param statusBITS        status bits
     * @return resource type model
     */
    protected cResourceTypeModel getResourceTypeModelByID(long resourceTypeID, long userID,
                                                          int primaryRoleBITS, int secondaryRoleBITS,
                                                          int statusBITS) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // construct a selection query
        String selectQuery = "SELECT * FROM " + cSQLDBHelper.TABLE_tblRESOURCETYPE +
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
                String.valueOf(resourceTypeID),
                String.valueOf(userID),           /* access due to organization creator */
                String.valueOf(userID),           /* access due to record owner */
                String.valueOf(primaryRoleBITS),  /* access due to membership in primary role */
                String.valueOf(secondaryRoleBITS),/* access due to membership in secondary role */
                String.valueOf(statusBITS)});     /* access due to assigned statuses */

        cResourceTypeModel resourceType = new cResourceTypeModel();

        try {
            if (cursor.moveToFirst()) {
//                resourceType.setResourceTypeID(
//                        cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ID)));
//                resourceType.setServerID(
//                        cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_SERVER_ID)));
//                resourceType.setOwnerID(
//                        cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OWNER_ID)));
//                resourceType.setOrgID(
//                        cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ORG_ID)));
//                resourceType.setGroupBITS(
//                        cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_GROUP_BITS)));
//                resourceType.setPermsBITS(
//                        cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PERMS_BITS)));
//                resourceType.setStatusBITS(
//                        cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_STATUS_BITS)));
//                resourceType.setName(
//                        cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_NAME)));
//                resourceType.setDescription(
//                        cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_DESCRIPTION)));
//                resourceType.setCreatedDate(Timestamp.valueOf(
//                        cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_CREATED_DATE))));
//                resourceType.setModifiedDate(Timestamp.valueOf(
//                        cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_MODIFIED_DATE))));
//                resourceType.setSyncedDate(Timestamp.valueOf(
//                        cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_SYNCED_DATE))));
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to read an RESOURCE TYPE entity: " + e.getMessage());
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        // close the database connection
        db.close();

        return resourceType;
    }

    /**
     * return an activity model
     *
     * @param workplanID        work plan identification
     * @param userID            user identification
     * @param primaryRoleBITS   primary role bits
     * @param secondaryRoleBITS secondary role bits
     * @param statusBITS        status bits
     * @return activity model
     */
    protected cActivityModel getActivityModelByID(long workplanID, long userID,
                                                  int primaryRoleBITS, int secondaryRoleBITS,
                                                  int statusBITS) {

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + cSQLDBHelper.TABLE_tblCOMPONENT + " C INNER JOIN " +
                cSQLDBHelper.TABLE_tblACTIVITY + " A ON C." + cSQLDBHelper.KEY_ID + " = A." +
                cSQLDBHelper.KEY_COMPONENT_FK_ID +
                " WHERE ((W." + cSQLDBHelper.KEY_ID + " = ?) AND " +
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
                String.valueOf(workplanID),
                String.valueOf(userID),           /* access due to organization creator */
                String.valueOf(userID),           /* access due to record owner */
                String.valueOf(primaryRoleBITS),  /* access due to membership in primary role */
                String.valueOf(secondaryRoleBITS),/* access due to membership in secondary role */
                String.valueOf(statusBITS)});     /* access due to assigned statuses */

        cActivityModel activity = new cActivityModel();

        try {
            if (cursor.moveToFirst()) {

//                activity.setComponentID(cursor.getInt(
//                        cursor.getColumnIndex(cSQLDBHelper.KEY_COMPONENT_FK_ID)));
////                activity.getLogFrameModel().setLogFrameID(
////                        cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_LOGFRAME_FK_ID)));
//                activity.getOutputModel().setComponentID(
//                        cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OUTPUT_FK_ID)));
//                activity.setServerID(
//                        cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_SERVER_ID)));
//                activity.setOwnerID(
//                        cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OWNER_ID)));
//                activity.setOrgID(
//                        cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ORG_ID)));
//                activity.setGroupBITS(
//                        cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_GROUP_BITS)));
//                activity.setPermsBITS(
//                        cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PERMS_BITS)));
//                activity.setStatusBITS(
//                        cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_STATUS_BITS)));
//                activity.setName(
//                        cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_NAME)));
//                activity.setDescription(
//                        cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_DESCRIPTION)));
//                activity.setStartDate(Timestamp.valueOf(
//                        cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_START_DATE))));
//                activity.setEndDate(Timestamp.valueOf(
//                        cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_END_DATE))));
//                activity.setCreatedDate(Timestamp.valueOf(
//                        cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_CREATED_DATE))));
//                activity.setModifiedDate(Timestamp.valueOf(
//                        cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_MODIFIED_DATE))));
//                activity.setSyncedDate(Timestamp.valueOf(
//                        cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_SYNCED_DATE))));
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

        return activity;
    }

    /**
     * return set of journal entries
     *
     * @param inputID           input identification
     * @param userID            user identification
     * @param primaryRoleBITS   primary role bits
     * @param secondaryRoleBITS secondary role bits
     * @param statusBITS        status bits
     * @return set of journal entries
     */
    protected Set<cJournalModel> getJournalModelSetByID(long inputID, long userID, int primaryRoleBITS,
                                                        int secondaryRoleBITS, int statusBITS) {

        Set<cJournalModel> journalModelSet = new HashSet<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + cSQLDBHelper.TABLE_tblJOURNAL +
                " WHERE ((" + cSQLDBHelper.KEY_INPUT_FK_ID + "= ? ) AND " +
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
                String.valueOf(inputID),
                String.valueOf(userID),           /* access due to organization creator */
                String.valueOf(userID),           /* access due to record owner */
                String.valueOf(primaryRoleBITS),  /* access due to membership in primary role */
                String.valueOf(secondaryRoleBITS),/* access due to membership in secondary role */
                String.valueOf(statusBITS)});     /* access due to assigned statuses */

        try {
            if (cursor.moveToFirst()) {
                do {
                    cJournalModel journal = new cJournalModel();

                    journal.setJournalID(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ID)));
                    journal.setInputID(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_INPUT_FK_ID)));
                    journal.setTransactionID(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_TRANSACTION_FK_ID)));
                    journal.setServerID(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_SERVER_ID)));
                    journal.setOwnerID(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OWNER_ID)));
                    journal.setOrgID(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ORG_ID)));
                    journal.setGroupBITS(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_GROUP_BITS)));
                    journal.setPermsBITS(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PERMS_BITS)));
                    journal.setStatusBITS(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_STATUS_BITS)));
                    journal.setDescription(
                            cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_DESCRIPTION)));
                    journal.setEntryType(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ENTRY_TYPE)));
                    journal.setAmount(
                            cursor.getDouble(cursor.getColumnIndex(cSQLDBHelper.KEY_AMOUNT)));
                    journal.setCreatedDate(Timestamp.valueOf(
                            cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_CREATED_DATE))));
                    journal.setModifiedDate(Timestamp.valueOf(
                            cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_MODIFIED_DATE))));
                    journal.setSyncedDate(Timestamp.valueOf(
                            cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_SYNCED_DATE))));

                    journalModelSet.add(journal);

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to read a JOURNAL entity: " + e.getMessage());
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        // close the database connection
        db.close();

        return journalModelSet;
    }

    /**
     * return set of sub-logframe activity models
     *
     * @param inputID           input identification
     * @param userID            user identification
     * @param primaryRoleBITS   primary role bits
     * @param secondaryRoleBITS secondary role bits
     * @param statusBITS        status bits
     * @return set of activities
     */
    protected Set<cActivityModel> getChildActivitySetByID(
            long inputID, long userID, int primaryRoleBITS, int secondaryRoleBITS, int statusBITS) {

        Set<cActivityModel> activityModelSet = new HashSet<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String selectQuery = "SELECT DISTINCT I." + cSQLDBHelper.KEY_ID + ", " +
                "WOR." + cSQLDBHelper.KEY_ID + ", WOR." + cSQLDBHelper.KEY_LOGFRAME_FK_ID + ", " +
                "ACT." + cSQLDBHelper.KEY_PARENT_FK_ID + ", ACT." + cSQLDBHelper.KEY_OUTPUT_FK_ID + ", " +
                "WOR." + cSQLDBHelper.KEY_SERVER_ID + ", WOR." + cSQLDBHelper.KEY_OWNER_ID + ", " +
                "WOR." + cSQLDBHelper.KEY_ORG_ID + ", WOR." + cSQLDBHelper.KEY_GROUP_BITS + ", " +
                "WOR." + cSQLDBHelper.KEY_PERMS_BITS + ", WOR." + cSQLDBHelper.KEY_STATUS_BITS + ", " +
                "WOR." + cSQLDBHelper.KEY_NAME + ", WOR." + cSQLDBHelper.KEY_DESCRIPTION + ", " +
                "WOR." + cSQLDBHelper.KEY_START_DATE + ", WOR." + cSQLDBHelper.KEY_END_DATE + ", " +
                "WOR." + cSQLDBHelper.KEY_CREATED_DATE + ", WOR." + cSQLDBHelper.KEY_MODIFIED_DATE + ", " +
                "WOR." + cSQLDBHelper.KEY_SYNCED_DATE +
                " FROM " + cSQLDBHelper.TABLE_tblCOMPONENT + " WOR " +
                " INNER JOIN " + cSQLDBHelper.TABLE_tblACTIVITY + " ACT " +
                " ON WOR." + cSQLDBHelper.KEY_ID + " = ACT." + cSQLDBHelper.KEY_COMPONENT_FK_ID + ", " +
                cSQLDBHelper.TABLE_tblINPUT + " I, " +
                cSQLDBHelper.TABLE_tblINPUT_ACTIVITY + " I_A, " +
                cSQLDBHelper.TABLE_tblLOGFRAMETREE + " LT " +
                " WHERE ((I." + cSQLDBHelper.KEY_ID + " = I_A." + cSQLDBHelper.KEY_INPUT_FK_ID +
                " AND I." + cSQLDBHelper.KEY_LOGFRAME_FK_ID + " = I_A." + cSQLDBHelper.KEY_PARENT_FK_ID +
                " AND WOR." + cSQLDBHelper.KEY_ID + " = I_A." + cSQLDBHelper.KEY_COMPONENT_FK_ID +
                " AND WOR." + cSQLDBHelper.KEY_LOGFRAME_FK_ID + " = I_A." + cSQLDBHelper.KEY_CHILD_FK_ID +
                " AND I_A." + cSQLDBHelper.KEY_PARENT_FK_ID + " = LT." + cSQLDBHelper.KEY_PARENT_FK_ID +
                " AND I_A." + cSQLDBHelper.KEY_CHILD_FK_ID + " = LT." + cSQLDBHelper.KEY_CHILD_FK_ID +
                " AND I." + cSQLDBHelper.KEY_ID + " = ?) AND " +
                /* read access permissions */
                /* organization creator is always allowed to do everything (i.e., where: userID = orgID)*/
                " ((WOR." + cSQLDBHelper.KEY_ORG_ID + " = ?) " +
                /* owner permission */
                " OR ((((WOR." + cSQLDBHelper.KEY_OWNER_ID + " = ?) " +
                " AND ((WOR." + cSQLDBHelper.KEY_PERMS_BITS + " & " + cBitwise.OWNER_READ + ") != 0))" +
                /* group (owner/primary organization) permission */
                " OR (((WOR." + cSQLDBHelper.KEY_GROUP_BITS + " & ?) != 0) " +
                " AND ((WOR." + cSQLDBHelper.KEY_PERMS_BITS + " & " + cBitwise.GROUP_READ + ") != 0))" +
                /* other (secondary organizations) permission */
                " OR (((WOR." + cSQLDBHelper.KEY_GROUP_BITS + " & ?) != 0) " +
                " AND ((WOR." + cSQLDBHelper.KEY_PERMS_BITS + " & " + cBitwise.OTHER_READ + ") != 0)))" +
                /* owner, group and other permissions allowed when the statuses hold */
                " AND ((WOR." + cSQLDBHelper.KEY_STATUS_BITS + " = 0) " +
                " OR ((WOR." + cSQLDBHelper.KEY_STATUS_BITS + " & ?) != 0)))))";

        Cursor cursor = db.rawQuery(selectQuery, new String[]{
                String.valueOf(inputID),
                String.valueOf(userID),           /* access due to organization creator */
                String.valueOf(userID),           /* access due to record owner */
                String.valueOf(primaryRoleBITS),  /* access due to membership in primary role */
                String.valueOf(secondaryRoleBITS),/* access due to membership in secondary role */
                String.valueOf(statusBITS)});     /* access due to assigned statuses */

        try {
            if (cursor.moveToFirst()) {
                do {
                    cActivityModel activity = new cActivityModel();

//                    activity.setComponentID(cursor.getInt(
//                            cursor.getColumnIndex(cSQLDBHelper.KEY_COMPONENT_FK_ID)));
//                    activity.getOutputModel().setComponentID(
//                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OUTPUT_FK_ID)));
//                    activity.setServerID(
//                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_SERVER_ID)));
//                    activity.setOwnerID(
//                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OWNER_ID)));
//                    activity.setOrgID(
//                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ORG_ID)));
//                    activity.setGroupBITS(
//                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_GROUP_BITS)));
//                    activity.setPermsBITS(
//                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PERMS_BITS)));
//                    activity.setStatusBITS(
//                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_STATUS_BITS)));
//                    activity.setName(
//                            cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_NAME)));
//                    activity.setDescription(
//                            cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_DESCRIPTION)));
//                    activity.setStartDate(Timestamp.valueOf(
//                            cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_START_DATE))));
//                    activity.setEndDate(Timestamp.valueOf(
//                            cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_END_DATE))));
//                    activity.setCreatedDate(Timestamp.valueOf(
//                            cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_CREATED_DATE))));
//                    activity.setModifiedDate(Timestamp.valueOf(
//                            cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_MODIFIED_DATE))));
//                    activity.setSyncedDate(Timestamp.valueOf(
//                            cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_SYNCED_DATE))));

                    activityModelSet.add(activity);

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to read a ACTIVITY entity: " + e.getMessage());
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        // close the database connection
        db.close();

        return activityModelSet;
    }

    /* ######################################## DELETE ACTIONS ########################################*/

    /*
     * the function delete all activities
     */
    public boolean deleteActivities() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        try {
            if (db.delete(cSQLDBHelper.TABLE_tblACTIVITY, null, null) < 0) {
                return false;
            }
        } catch (Exception e) {
            Log.d(TAG, "Exception in deleting all OUTPUTS " + e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }

    /*
     * the function delete all input outcomes
     */
    public boolean deleteInputMaterials() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        try {
            if (db.delete(cSQLDBHelper.TABLE_tblACTIVITY_OUTPUT, null, null) < 0) {
                return false;
            }
        } catch (Exception e) {
            Log.d(TAG, "Exception in deleting all ACTIVITY OUTPUTS " + e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }
}
