package com.me.mseotsanyana.mande.DAL.ìmpl.evaluator;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.me.mseotsanyana.mande.BLL.model.evaluator.cEvaluationResponseModel;
import com.me.mseotsanyana.mande.DAL.storage.database.cSQLDBHelper;
import com.me.mseotsanyana.mande.DAL.storage.preference.cBitwise;
import com.me.mseotsanyana.mande.UTIL.cConstant;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class cEvaluationResponseRepositoryImpl {
    private static SimpleDateFormat sdf = cConstant.TIMESTAMP_FORMAT_DATE;
    private static String TAG = cEvaluationResponseRepositoryImpl.class.getSimpleName();

    /* an object of the database helper */
    private cSQLDBHelper dbHelper;
    private cUserEvaluationRepositoryImpl userEvaluationRepository;

    public cEvaluationResponseRepositoryImpl(Context context) {
        dbHelper = new cSQLDBHelper(context);
        userEvaluationRepository = new cUserEvaluationRepositoryImpl(context);
    }

    /* ####################################### READ ACTIONS ######################################*/

    public cEvaluationResponseModel getEvaluationResponseModelByID(
            long evaluationResponseID, long userID, int primaryRoleBITS, int secondaryRoleBITS,
            int statusBITS) {

        // open the connection to the database
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // construct a selection query
        String selectQuery = "SELECT * FROM " + cSQLDBHelper.TABLE_tblEVALUATION_RESPONSE +
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
                String.valueOf(evaluationResponseID),
                String.valueOf(userID),           /* access due to organization creator */
                String.valueOf(userID),           /* access due to record owner */
                String.valueOf(primaryRoleBITS),  /* access due to membership in primary role */
                String.valueOf(secondaryRoleBITS),/* access due to membership in secondary role */
                String.valueOf(statusBITS)});     /* access due to assigned statuses */

        cEvaluationResponseModel evaluationResponse = new cEvaluationResponseModel();

        try {
            if (cursor.moveToFirst()) {

                evaluationResponse.setEvaluationResponseID(
                        cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ID)));
                evaluationResponse.setServerID(
                        cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_SERVER_ID)));
                evaluationResponse.setOwnerID(
                        cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OWNER_ID)));
                evaluationResponse.setOrgID(
                        cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ORG_ID)));
                evaluationResponse.setGroupBITS(
                        cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_GROUP_BITS)));
                evaluationResponse.setPermsBITS(
                        cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PERMS_BITS)));
                evaluationResponse.setStatusBITS(
                        cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_STATUS_BITS)));
                evaluationResponse.setCreatedDate(Timestamp.valueOf(
                        cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_CREATED_DATE))));
                evaluationResponse.setModifiedDate(Timestamp.valueOf(
                        cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_MODIFIED_DATE))));
                evaluationResponse.setSyncedDate(Timestamp.valueOf(
                        cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_SYNCED_DATE))));

                evaluationResponse.setUserEvaluationModel(
                        userEvaluationRepository.getUserEvaluationModelByID(evaluationResponseID,
                                userID, primaryRoleBITS, secondaryRoleBITS, statusBITS));

            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to read an EVALUATION RESPONSE entity: " + e.getMessage());
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        // close the database connection
        db.close();

        return evaluationResponse;
    }

/*
    public Set<cEvaluationResponseModel> getEvaluationResponseModelSetByID(
            long evaluationResponseID, long userID, int primaryRoleBITS, int secondaryRoleBITS,
            int statusBITS) {

        // open the connection to the database
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // construct a selection query
        String selectQuery = "SELECT * FROM " + cSQLDBHelper.TABLE_tblEVALUATION_RESPONSE +
                " WHERE ((" + cSQLDBHelper.KEY_ID + "= ? ) AND " +
                /* read access permissions */
    // organization creator is always allowed to do everything (i.e., where: userID = orgID)
//                " ((" + cSQLDBHelper.KEY_ORG_ID + " = ?) " +
//                /* owner permission
//                " OR ((((" + cSQLDBHelper.KEY_OWNER_ID + " = ?) " +
//                " AND ((" + cSQLDBHelper.KEY_PERMS_BITS + " & " + cBitwise.OWNER_READ + ") != 0))" +
//                /* group (owner/primary organization) permission
//                " OR (((" + cSQLDBHelper.KEY_GROUP_BITS + " & ?) != 0) " +
//                " AND ((" + cSQLDBHelper.KEY_PERMS_BITS + " & " + cBitwise.GROUP_READ + ") != 0))" +
//                /* other (secondary organizations) permission
//                " OR (((" + cSQLDBHelper.KEY_GROUP_BITS + " & ?) != 0) " +
//                " AND ((" + cSQLDBHelper.KEY_PERMS_BITS + " & " + cBitwise.OTHER_READ + ") != 0)))" +
//                /* owner, group and other permissions allowed when the statuses hold
//                " AND ((" + cSQLDBHelper.KEY_STATUS_BITS + " = 0) " +
//                " OR ((" + cSQLDBHelper.KEY_STATUS_BITS + " & ?) != 0)))))";
//
//        Cursor cursor = db.rawQuery(selectQuery, new String[]{
//                String.valueOf(evaluationResponseID),
//                String.valueOf(userID),           /* access due to organization creator
//                String.valueOf(userID),           /* access due to record owner
//                String.valueOf(primaryRoleBITS),  /* access due to membership in primary role
//                String.valueOf(secondaryRoleBITS),/* access due to membership in secondary role
//                String.valueOf(statusBITS)});     /* access due to assigned statuses
//
//        cEvaluationResponseModel evaluationResponse = new cEvaluationResponseModel();
//
//        try {
//            if (cursor.moveToFirst()) {
//
//                evaluationResponse.setEvaluationResponseID(
//                        cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ID)));
//                evaluationResponse.setServerID(
//                        cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_SERVER_ID)));
//                evaluationResponse.setOwnerID(
//                        cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OWNER_ID)));
//                evaluationResponse.setOrgID(
//                        cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ORG_ID)));
//                evaluationResponse.setGroupBITS(
//                        cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_GROUP_BITS)));
//                evaluationResponse.setPermsBITS(
//                        cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PERMS_BITS)));
//                evaluationResponse.setStatusBITS(
//                        cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_STATUS_BITS)));
//                evaluationResponse.setCreatedDate(Timestamp.valueOf(
//                        cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_CREATED_DATE))));
//                evaluationResponse.setModifiedDate(Timestamp.valueOf(
//                        cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_MODIFIED_DATE))));
//                evaluationResponse.setSyncedDate(Timestamp.valueOf(
//                        cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_SYNCED_DATE))));
//
//                evaluationResponse.setUserEvaluationModel(
//                        userEvaluationRepository.getUserEvaluationModelByID(evaluationResponseID,
//                                userID, primaryRoleBITS, secondaryRoleBITS, statusBITS));
//
//
//
//            }
//        } catch (Exception e) {
//            Log.d(TAG, "Error while trying to read an EVALUATION RESPONSE entity: " + e.getMessage());
//        } finally {
//            if (cursor != null && !cursor.isClosed()) {
//                cursor.close();
//            }
//        }
//
//        // close the database connection
//        db.close();
//
//        return evaluationResponse;
//    }
//
}
