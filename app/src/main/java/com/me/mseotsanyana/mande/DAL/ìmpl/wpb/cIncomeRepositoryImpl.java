package com.me.mseotsanyana.mande.DAL.ìmpl.wpb;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.me.mseotsanyana.mande.BLL.repository.wpb.iIncomeRepository;
import com.me.mseotsanyana.mande.DAL.model.logframe.cActivityModel;
import com.me.mseotsanyana.mande.DAL.model.logframe.cQuestionModel;
import com.me.mseotsanyana.mande.DAL.model.session.cFunderModel;
import com.me.mseotsanyana.mande.DAL.model.session.cOrganizationModel;
import com.me.mseotsanyana.mande.DAL.model.wpb.cExpenseModel;
import com.me.mseotsanyana.mande.DAL.model.wpb.cIncomeModel;
import com.me.mseotsanyana.mande.DAL.storage.database.cSQLDBHelper;
import com.me.mseotsanyana.mande.DAL.storage.preference.cBitwise;
import com.me.mseotsanyana.mande.DAL.ìmpl.logframe.cInputRepositoryImpl;
import com.me.mseotsanyana.mande.UTIL.cConstant;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Set;

public class cIncomeRepositoryImpl extends cInputRepositoryImpl implements iIncomeRepository {
    private static SimpleDateFormat sdf = cConstant.FORMAT_DATE;
    private static String TAG = cIncomeRepositoryImpl.class.getSimpleName();

    // an object of the database helper
    private cSQLDBHelper dbHelper;

    public cIncomeRepositoryImpl(Context context) {
        super(context);
        dbHelper = new cSQLDBHelper(context);
    }

    @Override
    public Set<cIncomeModel> getIncomeModelSet(long logframeID, long userID, int primaryRoleBITS,
                                               int secondaryRoleBITS, int statusBITS) {
        // list of activities
        Set<cIncomeModel> incomeModelSet = new HashSet<>();

        // open the connection to the database
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + cSQLDBHelper.TABLE_tblINPUT +" R INNER JOIN " +
                cSQLDBHelper.TABLE_tblINCOME +" I ON R."+cSQLDBHelper.KEY_ID +" = I." +
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
                    cIncomeModel income = new cIncomeModel();

                    income.setInputID(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ID)));
                    income.setWorkplanID(cursor.getInt(
                            cursor.getColumnIndex(cSQLDBHelper.KEY_WORKPLAN_FK_ID)));
                    income.setLogFrameID(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_LOGFRAME_FK_ID)));
                    income.setResourceID(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_RESOURCE_FK_ID)));
                    income.setServerID(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_SERVER_ID)));
                    income.setOwnerID(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OWNER_ID)));
                    income.setOrgID(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ORG_ID)));
                    income.setGroupBITS(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_GROUP_BITS)));
                    income.setPermsBITS(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PERMS_BITS)));
                    income.setStatusBITS(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_STATUS_BITS)));
                    income.setStartDate(Timestamp.valueOf(
                            cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_START_DATE))));
                    income.setEndDate(Timestamp.valueOf(
                            cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_END_DATE))));
                    income.setCreatedDate(Timestamp.valueOf(
                            cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_CREATED_DATE))));
                    income.setModifiedDate(Timestamp.valueOf(
                            cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_MODIFIED_DATE))));
                    income.setSyncedDate(Timestamp.valueOf(
                            cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_SYNCED_DATE))));

                    /* objects */

                    /* populate a logframe object */
                    income.setLogFrameModel(getLogFrameModelByID(income.getLogFrameID(), userID,
                            primaryRoleBITS, secondaryRoleBITS, statusBITS));
                    /* populate an resource object */
                    income.setResourceModel(getResourceModelByID(
                            income.getResourceID(), userID, primaryRoleBITS, secondaryRoleBITS,
                            statusBITS));
                    /* populate an activity object */
                    income.setActivityModel(getActivityModelByID(
                            income.getWorkplanID(), userID, primaryRoleBITS, secondaryRoleBITS,
                            statusBITS));

                    /* sets */
                    /* populate a set of funders */
                    income.setFunderModelSet(getFunderModelSetByID(income.getInputID(), userID,
                            primaryRoleBITS, secondaryRoleBITS, statusBITS));
                    /* populate question components */
                    income.setQuestionModelSet(getQuestionModelSetByID(
                            income.getInputID(), userID, primaryRoleBITS, secondaryRoleBITS,
                            statusBITS));
                    /* populate journal components */
                    income.setJournalModelSet(getJournalModelSetByID(income.getInputID(), userID,
                            primaryRoleBITS, secondaryRoleBITS, statusBITS));

                    /* maps */

                    /* populate child activity for the input */
                    income.setChildActivityModelSet(getChildActivitySetByID(income.getInputID(),
                            userID, primaryRoleBITS, secondaryRoleBITS, statusBITS));

                    /* add expenses */
                    incomeModelSet.add(income);

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error in reading all INCOME entities: " + e.getMessage());
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        // close the database connection
        db.close();

        return incomeModelSet;
    }

    protected Set<cFunderModel> getFunderModelSetByID(long inputID, long userID, int primaryRoleBITS,
                                              int secondaryRoleBITS, int statusBITS) {
        Set<cFunderModel> funderModelSet = new HashSet<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String selectQuery = "SELECT " + "INC." + cSQLDBHelper.KEY_INPUT_FK_ID + ", " +
                "FUN." + cSQLDBHelper.KEY_ORGANIZATION_FK_ID + ", ORG." + cSQLDBHelper.KEY_ID + ", " +
                "ORG." + cSQLDBHelper.KEY_SERVER_ID + ", ORG." + cSQLDBHelper.KEY_OWNER_ID + ", " +
                "ORG." + cSQLDBHelper.KEY_ORG_ID + ", ORG." + cSQLDBHelper.KEY_GROUP_BITS + ", " +
                "ORG." + cSQLDBHelper.KEY_PERMS_BITS + ", ORG." + cSQLDBHelper.KEY_STATUS_BITS + ", " +
                "ORG." + cSQLDBHelper.KEY_NAME + ", ORG." + cSQLDBHelper.KEY_TELEPHONE + ", " +
                "ORG." + cSQLDBHelper.KEY_EMAIL + ", ORG." + cSQLDBHelper.KEY_WEBSITE + ", " +
                "ORG." + cSQLDBHelper.KEY_CREATED_DATE + ", ORG." + cSQLDBHelper.KEY_MODIFIED_DATE + ", " +
                "ORG." + cSQLDBHelper.KEY_SYNCED_DATE +
                " FROM " + cSQLDBHelper.TABLE_tblORGANIZATION +" ORG " +
                " INNER JOIN " + cSQLDBHelper.TABLE_tblFUNDER +" FUN " +
                " ON ORG."+cSQLDBHelper.KEY_ID +" = FUN."+cSQLDBHelper.KEY_ORGANIZATION_FK_ID + ", " +
                cSQLDBHelper.TABLE_tblINPUT +" INP " +
                " INNER JOIN " + cSQLDBHelper.TABLE_tblINCOME +" INC " +
                " ON INP."+cSQLDBHelper.KEY_ID +" = INC."+cSQLDBHelper.KEY_INPUT_FK_ID + ", " +
                cSQLDBHelper.TABLE_tblFUND + " I_F " +
                " WHERE ((INC." + cSQLDBHelper.KEY_INPUT_FK_ID + " = I_F." + cSQLDBHelper.KEY_INPUT_FK_ID +
                " AND FUN." + cSQLDBHelper.KEY_ORGANIZATION_FK_ID + " = I_F." + cSQLDBHelper.KEY_FUNDER_FK_ID +
                " AND INC." + cSQLDBHelper.KEY_INPUT_FK_ID + " = ?) AND " +
                /* read access permissions */
                /* organization creator is always allowed to do everything (i.e., where: userID = orgID)*/
                " ((ORG." + cSQLDBHelper.KEY_ORG_ID + " = ?) " +
                /* owner permission */
                " OR ((((ORG." + cSQLDBHelper.KEY_OWNER_ID + " = ?) " +
                " AND ((ORG." + cSQLDBHelper.KEY_PERMS_BITS + " & " + cBitwise.OWNER_READ + ") != 0))" +
                /* group (owner/primary organization) permission */
                " OR (((ORG." + cSQLDBHelper.KEY_GROUP_BITS + " & ?) != 0) " +
                " AND ((ORG." + cSQLDBHelper.KEY_PERMS_BITS + " & " + cBitwise.GROUP_READ + ") != 0))" +
                /* other (secondary organizations) permission */
                " OR (((ORG." + cSQLDBHelper.KEY_GROUP_BITS + " & ?) != 0) " +
                " AND ((ORG." + cSQLDBHelper.KEY_PERMS_BITS + " & " + cBitwise.OTHER_READ + ") != 0)))" +
                /* owner, group and other permissions allowed when the statuses hold */
                " AND ((ORG." + cSQLDBHelper.KEY_STATUS_BITS + " = 0) " +
                " OR ((ORG." + cSQLDBHelper.KEY_STATUS_BITS + " & ?) != 0)))))";

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

                    cFunderModel funder = new cFunderModel();

                    funder.setOrganizationID(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ID)));
                    funder.setServerID(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_SERVER_ID)));
                    funder.setOwnerID(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OWNER_ID)));
                    funder.setOrgID(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ORG_ID)));
                    funder.setGroupBITS(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_GROUP_BITS)));
                    funder.setPermsBITS(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PERMS_BITS)));
                    funder.setStatusBITS(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_STATUS_BITS)));
                    funder.setName(
                            cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_NAME)));
                    funder.setPhone(
                            cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_TELEPHONE)));
                    funder.setEmail(
                            cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_EMAIL)));
                    funder.setWebsite(
                            cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_WEBSITE)));
                    funder.setCreatedDate(Timestamp.valueOf(
                            cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_CREATED_DATE))));
                    funder.setModifiedDate(Timestamp.valueOf(
                            cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_MODIFIED_DATE))));
                    funder.setSyncedDate(Timestamp.valueOf(
                            cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_SYNCED_DATE))));

                    funderModelSet.add(funder);

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to read a FUNDER entity: " + e.getMessage());
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        // close the database connection
        db.close();

        return funderModelSet;
    }
}
