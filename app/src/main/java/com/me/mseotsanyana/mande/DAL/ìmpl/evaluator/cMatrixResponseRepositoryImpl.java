package com.me.mseotsanyana.mande.DAL.ìmpl.evaluator;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.me.mseotsanyana.mande.DAL.model.evaluator.cMatrixResponseModel;
import com.me.mseotsanyana.mande.DAL.storage.database.cSQLDBHelper;
import com.me.mseotsanyana.mande.DAL.storage.preference.cBitwise;
import com.me.mseotsanyana.mande.UTIL.cConstant;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Set;

public class cMatrixResponseRepositoryImpl extends cEvaluationResponseRepositoryImpl {
    private static SimpleDateFormat sdf = cConstant.FORMAT_DATE;
    private static String TAG = cMatrixResponseRepositoryImpl.class.getSimpleName();

    // an object of the database helper
    private cSQLDBHelper dbHelper;
    private cUserEvaluationRepositoryImpl userEvaluationRepository;

    public cMatrixResponseRepositoryImpl(Context context) {
        super(context);
        dbHelper = new cSQLDBHelper(context);
        userEvaluationRepository = new cUserEvaluationRepositoryImpl(context);

    }

    /**
     * returns a set of expense models
     *
     * @param questionID input identification
     * @param userID user identification
     * @param primaryRoleBITS primary role bits
     * @param secondaryRoleBITS secondary role bits
     * @param statusBITS status bits
     * @return set of expenses
     */

    public Set<cMatrixResponseModel> getMatrixResponseModelSetByID(
            long questionID, long userID, int primaryRoleBITS, int secondaryRoleBITS,
            int statusBITS) {

        // set of numeric responses
        Set<cMatrixResponseModel> matrixResponseModelSet = new HashSet<>();

        // open the connection to the database
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String selectQuery = "SELECT * FROM " +
                cSQLDBHelper.TABLE_tblEVALUATION_RESPONSE +" ER INNER JOIN " +
                cSQLDBHelper.TABLE_tblMATRIXRESPONSE +" M ON ER."+cSQLDBHelper.KEY_ID +" = M." +
                cSQLDBHelper.KEY_EVALUATION_RESPONSE_FK_ID +
                " WHERE ((" + cSQLDBHelper.KEY_QUESTION_FK_ID + " = ?) AND " +
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
                String.valueOf(questionID),       /* access due to input foreign key */
                String.valueOf(userID),           /* access due to organization creator */
                String.valueOf(userID),           /* access due to record owner */
                String.valueOf(primaryRoleBITS),  /* access due to membership in primary role */
                String.valueOf(secondaryRoleBITS),/* access due to membership in secondary role */
                String.valueOf(statusBITS)});     /* access due to assigned statuses */
        try {
            if (cursor.moveToFirst()) {
                do {
                    cMatrixResponseModel matrix = new cMatrixResponseModel();

                    matrix.setEvaluationResponseID(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ID)));
                    matrix.setQuestionID(cursor.getInt(
                            cursor.getColumnIndex(cSQLDBHelper.KEY_QUESTION_FK_ID)));
                    matrix.setMatrixChoiceID(cursor.getInt(
                            cursor.getColumnIndex(cSQLDBHelper.KEY_MATRIX_CHOICE_FK_ID)));
                    matrix.setRowID(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ROW_CHOICE_FK_ID)));
                    matrix.setColID(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_COL_CHOICE_FK_ID)));
                    matrix.setServerID(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_SERVER_ID)));
                    matrix.setOwnerID(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OWNER_ID)));
                    matrix.setOrgID(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ORG_ID)));
                    matrix.setGroupBITS(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_GROUP_BITS)));
                    matrix.setPermsBITS(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PERMS_BITS)));
                    matrix.setStatusBITS(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_STATUS_BITS)));
                    matrix.setCreatedDate(Timestamp.valueOf(
                            cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_CREATED_DATE))));
                    matrix.setModifiedDate(Timestamp.valueOf(
                            cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_MODIFIED_DATE))));
                    matrix.setSyncedDate(Timestamp.valueOf(
                            cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_SYNCED_DATE))));

                    /* objects */

                    /* populate a user evaluation in superclass object */
                    matrix.setUserEvaluationModel(
                            userEvaluationRepository.getUserEvaluationModelByID(
                                    matrix.getEvaluationResponseID(), userID, primaryRoleBITS,
                                    secondaryRoleBITS, statusBITS));

                    /* add numeric responses */
                    matrixResponseModelSet.add(matrix);

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error in reading all MATRIX RESPONSE entities: " + e.getMessage());
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        // close the database connection
        db.close();

        return matrixResponseModelSet;
    }
}
