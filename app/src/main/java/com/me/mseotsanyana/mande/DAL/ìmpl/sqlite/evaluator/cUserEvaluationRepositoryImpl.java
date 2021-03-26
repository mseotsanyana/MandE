package com.me.mseotsanyana.mande.DAL.ìmpl.sqlite.evaluator;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.me.mseotsanyana.mande.BLL.model.evaluator.cUserEvaluationModel;
import com.me.mseotsanyana.mande.DAL.storage.database.cSQLDBHelper;
import com.me.mseotsanyana.mande.DAL.storage.preference.cBitwise;
import com.me.mseotsanyana.mande.DAL.ìmpl.sqlite.session.cUserSQLiteRepositoryImpl;
import com.me.mseotsanyana.mande.UTIL.cConstant;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class cUserEvaluationRepositoryImpl {
    private static SimpleDateFormat sdf = cConstant.TIMESTAMP_FORMAT_DATE;
    private static String TAG = cUserEvaluationRepositoryImpl.class.getSimpleName();

    /* an object of the database helper */
    private cSQLDBHelper dbHelper;

    private cUserSQLiteRepositoryImpl userRepository;
    private cEvaluationRepositoryImpl evaluationRepository;

    public cUserEvaluationRepositoryImpl(Context context) {
        dbHelper = new cSQLDBHelper(context);

        userRepository = new cUserSQLiteRepositoryImpl(context);
        evaluationRepository = new cEvaluationRepositoryImpl(context);
    }

    /* ####################################### READ ACTIONS ######################################*/

    public cUserEvaluationModel getUserEvaluationModelByID(
            long userEvaluationID, long userID, int primaryRoleBITS, int secondaryRoleBITS,
            int statusBITS) {

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + cSQLDBHelper.TABLE_tblUSER_EVALUATION +
                " WHERE ((" + cSQLDBHelper.KEY_ID + " = ?) AND " +
                /* read access permissions */
                /* organization creator is always allowed to do everything (i.e., where: userID = orgID) */
                " ((" + cSQLDBHelper.KEY_ORG_ID + " = ?) " +
                /* owner permission */
                " OR ((" + cSQLDBHelper.KEY_OWNER_ID + " = ?) " +
                " AND ((" + cSQLDBHelper.KEY_PERMS_BITS + " & " + cBitwise.OWNER_READ + ") != 0))" +
                /* group (owner/primary organization) permission */
                " OR (((" + cSQLDBHelper.KEY_GROUP_BITS + " & ?) != 0) " +
                " AND ((" + cSQLDBHelper.KEY_PERMS_BITS + " & " + cBitwise.GROUP_READ + ") != 0))" +
                /* other (secondary organizations) permission */
                " OR (((" + cSQLDBHelper.KEY_GROUP_BITS + " & ?) != 0) " +
                " AND ((" + cSQLDBHelper.KEY_PERMS_BITS + " & " + cBitwise.OTHER_READ + ") != 0))" +
                /* owner, group and other permissions allowed when the statuses hold */
                " AND ((" + cSQLDBHelper.KEY_STATUS_BITS + " = 0) " +
                " OR ((" + cSQLDBHelper.KEY_STATUS_BITS + " & ?) != 0))))";

        Cursor cursor = db.rawQuery(selectQuery, new String[]{
                String.valueOf(userEvaluationID), /* access due to being user evaluation */
                String.valueOf(userID),           /* access due to organization creator */
                String.valueOf(userID),           /* access due to record owner */
                String.valueOf(primaryRoleBITS),  /* access due to membership in primary role */
                String.valueOf(secondaryRoleBITS),/* access due to membership in secondary role */
                String.valueOf(statusBITS)});     /* access due to assigned statuses */

        cUserEvaluationModel userEvaluation = new cUserEvaluationModel();

        try {
            if (cursor.moveToFirst()) {

                userEvaluation.setUseEvaluationID(
                        cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ID)));
                userEvaluation.setServerID(
                        cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_SERVER_ID)));
                userEvaluation.setOwnerID(
                        cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OWNER_ID)));
                userEvaluation.setOrgID(
                        cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ORG_ID)));
                userEvaluation.setGroupBITS(
                        cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_GROUP_BITS)));
                userEvaluation.setPermsBITS(
                        cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PERMS_BITS)));
                userEvaluation.setStatusBITS(
                        cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_STATUS_BITS)));
                userEvaluation.setName(
                        cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_NAME)));
                userEvaluation.setDescription(
                        cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_DESCRIPTION)));
                userEvaluation.setStartDate(Timestamp.valueOf(
                        cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_START_DATE))));
                userEvaluation.setEndDate(Timestamp.valueOf(
                        cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_END_DATE))));
                userEvaluation.setCreatedDate(Timestamp.valueOf(
                        cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_CREATED_DATE))));
                userEvaluation.setModifiedDate(Timestamp.valueOf(
                        cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_MODIFIED_DATE))));
                userEvaluation.setSyncedDate(Timestamp.valueOf(
                        cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_SYNCED_DATE))));

                userEvaluation.setUserModel(userRepository.getUserModelByID(userID,
                        primaryRoleBITS, secondaryRoleBITS, statusBITS));

                userEvaluation.setEvaluationModel(evaluationRepository.getEvaluationModelByID(
                        userEvaluationID, userID, primaryRoleBITS, secondaryRoleBITS,
                        statusBITS));

            }
        } catch (Exception e) {
            Log.d(TAG, "Error while reading USER EVALUATION table:" + e.getMessage());
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        // close the database connection
        db.close();

        return userEvaluation;
    }
}
