package com.me.mseotsanyana.mande.DAL.ìmpl.awpb;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.me.mseotsanyana.mande.BLL.repository.awpb.iExpenseRepository;
import com.me.mseotsanyana.mande.BLL.model.wpb.cExpenseModel;
import com.me.mseotsanyana.mande.DAL.storage.database.cSQLDBHelper;
import com.me.mseotsanyana.mande.DAL.storage.preference.cBitwise;
import com.me.mseotsanyana.mande.DAL.ìmpl.logframe.cInputRepositoryImpl;
import com.me.mseotsanyana.mande.UTIL.cConstant;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Set;

public class cExpenseRepositoryImpl extends cInputRepositoryImpl implements iExpenseRepository {
    private static SimpleDateFormat sdf = cConstant.FORMAT_DATE;
    private static String TAG = cExpenseRepositoryImpl.class.getSimpleName();

    // an object of the database helper
    private cSQLDBHelper dbHelper;

    public cExpenseRepositoryImpl(Context context) {
        super(context);
        dbHelper = new cSQLDBHelper(context);
    }

    /**
     * returns a set of expense models
     *
     * @param logframeID input identification
     * @param userID user identification
     * @param primaryRoleBITS primary role bits
     * @param secondaryRoleBITS secondary role bits
     * @param statusBITS status bits
     * @return set of expenses
     */

    @Override
    public Set<cExpenseModel> getExpenseModelSet(long logframeID, long userID, int primaryRoleBITS,
                                                 int secondaryRoleBITS, int statusBITS) {
        // set of expenses
        Set<cExpenseModel> expenseModelSet = new HashSet<>();

        // open the connection to the database
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + cSQLDBHelper.TABLE_tblINPUT +" R INNER JOIN " +
                cSQLDBHelper.TABLE_tblEXPENSE +" E ON R."+cSQLDBHelper.KEY_ID +" = E." +
                cSQLDBHelper.KEY_INPUT_FK_ID +
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
                String.valueOf(logframeID),          /* access due to input foreign key */
                String.valueOf(userID),           /* access due to organization creator */
                String.valueOf(userID),           /* access due to record owner */
                String.valueOf(primaryRoleBITS),  /* access due to membership in primary role */
                String.valueOf(secondaryRoleBITS),/* access due to membership in secondary role */
                String.valueOf(statusBITS)});     /* access due to assigned statuses */
        try {
            if (cursor.moveToFirst()) {
                do {
                    cExpenseModel expense = new cExpenseModel();

                    expense.setInputID(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ID)));
                    expense.setWorkplanID(cursor.getInt(
                            cursor.getColumnIndex(cSQLDBHelper.KEY_WORKPLAN_FK_ID)));
                    expense.setLogFrameID(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_LOGFRAME_FK_ID)));
                    expense.setResourceID(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_RESOURCE_FK_ID)));
                    expense.setServerID(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_SERVER_ID)));
                    expense.setOwnerID(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OWNER_ID)));
                    expense.setOrgID(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ORG_ID)));
                    expense.setGroupBITS(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_GROUP_BITS)));
                    expense.setPermsBITS(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PERMS_BITS)));
                    expense.setStatusBITS(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_STATUS_BITS)));
                    expense.setExpense(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_EXPENSE)));
                    expense.setStartDate(Timestamp.valueOf(
                            cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_START_DATE))));
                    expense.setEndDate(Timestamp.valueOf(
                            cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_END_DATE))));
                    expense.setCreatedDate(Timestamp.valueOf(
                            cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_CREATED_DATE))));
                    expense.setModifiedDate(Timestamp.valueOf(
                            cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_MODIFIED_DATE))));
                    expense.setSyncedDate(Timestamp.valueOf(
                            cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_SYNCED_DATE))));

                    /* objects */

                    /* populate a logframe object */
                    expense.setLogFrameModel(getLogFrameModelByID(expense.getLogFrameID(), userID,
                            primaryRoleBITS, secondaryRoleBITS, statusBITS));
                    /* populate an resource object */
                    expense.setResourceModel(getResourceModelByID(
                            expense.getResourceID(), userID, primaryRoleBITS, secondaryRoleBITS,
                            statusBITS));
                    /* populate an activity object */
                    expense.setActivityModel(getActivityModelByID(
                            expense.getWorkplanID(), userID, primaryRoleBITS, secondaryRoleBITS,
                            statusBITS));

                    /* sets */

                    /* populate question components */
                    expense.setQuestionModelSet(getQuestionModelSetByID(
                            expense.getInputID(), userID, primaryRoleBITS, secondaryRoleBITS,
                            statusBITS));
                    /* populate journal components */
                    expense.setJournalModelSet(getJournalModelSetByID(expense.getInputID(), userID,
                            primaryRoleBITS, secondaryRoleBITS, statusBITS));

                    /* maps */

                    /* populate child activity for the input*/
                    expense.setChildActivityModelSet(getChildActivitySetByID(expense.getInputID(),
                            userID, primaryRoleBITS, secondaryRoleBITS, statusBITS));

                    /* add expenses */
                    expenseModelSet.add(expense);

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error in reading all EXPENSE entities: " + e.getMessage());
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        // close the database connection
        db.close();

        return expenseModelSet;
    }
}
