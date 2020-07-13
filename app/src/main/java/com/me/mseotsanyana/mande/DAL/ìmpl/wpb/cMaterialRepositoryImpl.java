package com.me.mseotsanyana.mande.DAL.ìmpl.wpb;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.me.mseotsanyana.mande.BLL.repository.wpb.iMaterialRepository;
import com.me.mseotsanyana.mande.DAL.model.wpb.cMaterialModel;
import com.me.mseotsanyana.mande.DAL.storage.database.cSQLDBHelper;
import com.me.mseotsanyana.mande.DAL.storage.preference.cBitwise;
import com.me.mseotsanyana.mande.DAL.ìmpl.logframe.cInputRepositoryImpl;
import com.me.mseotsanyana.mande.UTIL.cConstant;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Set;

public class cMaterialRepositoryImpl extends cInputRepositoryImpl implements iMaterialRepository {
    private static SimpleDateFormat sdf = cConstant.FORMAT_DATE;
    private static String TAG = cMaterialRepositoryImpl.class.getSimpleName();

    // an object of the database helper
    private cSQLDBHelper dbHelper;

    public cMaterialRepositoryImpl(Context context) {
        super(context);
        dbHelper = new cSQLDBHelper(context);
    }

    @Override
    public Set<cMaterialModel> getMaterialModelSet(long logframeID, long userID, int primaryRoleBITS,
                                                   int secondaryRoleBITS, int statusBITS) {
        // set of expenses
        Set<cMaterialModel> materialModelSet = new HashSet<>();

        // open the connection to the database
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + cSQLDBHelper.TABLE_tblINPUT +" R INNER JOIN " +
                cSQLDBHelper.TABLE_tblMATERIAL +" M ON R."+cSQLDBHelper.KEY_ID +" = M." +
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
                    cMaterialModel material = new cMaterialModel();

                    material.setInputID(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ID)));
                    material.setWorkplanID(cursor.getInt(
                            cursor.getColumnIndex(cSQLDBHelper.KEY_WORKPLAN_FK_ID)));
                    material.setLogFrameID(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_LOGFRAME_FK_ID)));
                    material.setResourceID(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_RESOURCE_FK_ID)));
                    material.setServerID(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_SERVER_ID)));
                    material.setOwnerID(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OWNER_ID)));
                    material.setOrgID(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ORG_ID)));
                    material.setGroupBITS(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_GROUP_BITS)));
                    material.setPermsBITS(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PERMS_BITS)));
                    material.setStatusBITS(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_STATUS_BITS)));
                    material.setQuantity(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_QUANTITY)));
                    material.setStartDate(Timestamp.valueOf(
                            cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_START_DATE))));
                    material.setEndDate(Timestamp.valueOf(
                            cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_END_DATE))));
                    material.setCreatedDate(Timestamp.valueOf(
                            cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_CREATED_DATE))));
                    material.setModifiedDate(Timestamp.valueOf(
                            cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_MODIFIED_DATE))));
                    material.setSyncedDate(Timestamp.valueOf(
                            cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_SYNCED_DATE))));

                    /* objects */

                    /* populate a logframe object */
                    material.setLogFrameModel(getLogFrameModelByID(material.getLogFrameID(), userID,
                            primaryRoleBITS, secondaryRoleBITS, statusBITS));
                    /* populate an resource object */
                    material.setResourceModel(getResourceModelByID(
                            material.getResourceID(), userID, primaryRoleBITS, secondaryRoleBITS,
                            statusBITS));
                    /* populate an activity object */
                    material.setActivityModel(getActivityModelByID(
                            material.getWorkplanID(), userID, primaryRoleBITS, secondaryRoleBITS,
                            statusBITS));

                    /* sets */

                    /* populate question components */
                    material.setQuestionModelSet(getQuestionModelSetByID(material.getInputID(),
                            userID, primaryRoleBITS, secondaryRoleBITS, statusBITS));
                    /* populate journal components */
                    material.setJournalModelSet(getJournalModelSetByID(material.getInputID(), userID,
                            primaryRoleBITS, secondaryRoleBITS, statusBITS));

                    /* maps */

                    /* populate child activity for the input*/
                    material.setChildActivityModelSet(getChildActivitySetByID(material.getInputID(),
                            userID, primaryRoleBITS, secondaryRoleBITS, statusBITS));

                    /* add expenses */
                    materialModelSet.add(material);

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error in reading all MATERIAL entities: " + e.getMessage());
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        // close the database connection
        db.close();

        return materialModelSet;
    }
}
