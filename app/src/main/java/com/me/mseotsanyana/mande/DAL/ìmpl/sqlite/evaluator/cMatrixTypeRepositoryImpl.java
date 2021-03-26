package com.me.mseotsanyana.mande.DAL.ìmpl.sqlite.evaluator;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.me.mseotsanyana.mande.BLL.model.logframe.cMatrixQuestionModel;
import com.me.mseotsanyana.mande.DAL.storage.database.cSQLDBHelper;
import com.me.mseotsanyana.mande.DAL.storage.preference.cBitwise;
import com.me.mseotsanyana.mande.UTIL.cConstant;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class cMatrixTypeRepositoryImpl {
    private static SimpleDateFormat sdf = cConstant.FORMAT_DATE;
    private static String TAG = cMatrixTypeRepositoryImpl.class.getSimpleName();

    // an object of the database helper
    private cSQLDBHelper dbHelper;

    private cChartRepositoryImpl chartRepository;

    public cMatrixTypeRepositoryImpl(Context context) {
        dbHelper = new cSQLDBHelper(context);
        chartRepository = new cChartRepositoryImpl(context);
    }

    public cMatrixQuestionModel getMatrixTypeModelSet(
            long questionTypeID, long userID, int primaryRoleBITS, int secondaryRoleBITS,
            int statusBITS) {

        // open the connection to the database
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String selectQuery = "SELECT * FROM " +
                cSQLDBHelper.TABLE_tblQUESTIONTYPE + " Q INNER JOIN " +
                cSQLDBHelper.TABLE_tblMATRIXQUESTION + " M ON Q." + cSQLDBHelper.KEY_ID + " = M." +
                cSQLDBHelper.KEY_QUESTION_TYPE_FK_ID +
                " WHERE ((" + cSQLDBHelper.KEY_QUESTION_TYPE_FK_ID + " = ?) AND " +
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
                String.valueOf(questionTypeID),   /* access due to input foreign key */
                String.valueOf(userID),           /* access due to organization creator */
                String.valueOf(userID),           /* access due to record owner */
                String.valueOf(primaryRoleBITS),  /* access due to membership in primary role */
                String.valueOf(secondaryRoleBITS),/* access due to membership in secondary role */
                String.valueOf(statusBITS)});     /* access due to assigned statuses */

        cMatrixQuestionModel matrix = null;

        try {
            if (cursor.moveToFirst()) {

                matrix = new cMatrixQuestionModel();

                //matrix.setQuestionTypeID(cursor.getInt(
                //        cursor.getColumnIndex(cSQLDBHelper.KEY_QUESTION_TYPE_FK_ID)));
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
                //matrix.setName(
                //        cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_NAME)));
                //matrix.setDescription(
                //        cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_DESCRIPTION)));
                matrix.setCreatedDate(Timestamp.valueOf(
                        cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_CREATED_DATE))));
                matrix.setModifiedDate(Timestamp.valueOf(
                        cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_MODIFIED_DATE))));
                matrix.setSyncedDate(Timestamp.valueOf(
                        cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_SYNCED_DATE))));

                /* sets
                matrix.setMatrixChartModelSet(chartRepository.getChartModelSetByID(
                        questionTypeID, userID, primaryRoleBITS, secondaryRoleBITS, statusBITS));*/

            }
        } catch (Exception e) {
            Log.d(TAG, "Error in reading all PRIMITIVE TYPE entities: " + e.getMessage());
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        // close the database connection
        db.close();

        return matrix;
    }
}
