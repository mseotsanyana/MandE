package com.me.mseotsanyana.mande.DAL.ìmpl.wpb;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.me.mseotsanyana.mande.BLL.repository.wpb.iHumanRepository;
import com.me.mseotsanyana.mande.DAL.model.session.cUserModel;
import com.me.mseotsanyana.mande.DAL.model.wpb.cHumanModel;
import com.me.mseotsanyana.mande.DAL.storage.database.cSQLDBHelper;
import com.me.mseotsanyana.mande.DAL.storage.preference.cBitwise;
import com.me.mseotsanyana.mande.DAL.ìmpl.logframe.cInputRepositoryImpl;
import com.me.mseotsanyana.mande.UTIL.cConstant;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Set;

public class cHumanRepositoryImpl extends cInputRepositoryImpl implements iHumanRepository {
    private static SimpleDateFormat sdf = cConstant.FORMAT_DATE;
    private static String TAG = cHumanRepositoryImpl.class.getSimpleName();

    // an object of the database helper
    private cSQLDBHelper dbHelper;

    public cHumanRepositoryImpl(Context context) {
        super(context);
        dbHelper = new cSQLDBHelper(context);
    }

    @Override
    public Set<cHumanModel> getHumanModelSet(long logframeID, long userID, int primaryRoleBITS,
                                             int secondaryRoleBITS, int statusBITS) {
        // set of staff
        Set<cHumanModel> humanModelSet = new HashSet<>();

        // open the connection to the database
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + cSQLDBHelper.TABLE_tblINPUT +" R INNER JOIN " +
                cSQLDBHelper.TABLE_tblHUMAN +" H ON R."+cSQLDBHelper.KEY_ID +" = H." +
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
                String.valueOf(logframeID),       /* access due to input foreign key */
                String.valueOf(userID),           /* access due to organization creator */
                String.valueOf(userID),           /* access due to record owner */
                String.valueOf(primaryRoleBITS),  /* access due to membership in primary role */
                String.valueOf(secondaryRoleBITS),/* access due to membership in secondary role */
                String.valueOf(statusBITS)});     /* access due to assigned statuses */
        try {
            if (cursor.moveToFirst()) {
                do {
                    cHumanModel human = new cHumanModel();

                    human.setInputID(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ID)));
                    human.setWorkplanID(cursor.getInt(
                            cursor.getColumnIndex(cSQLDBHelper.KEY_WORKPLAN_FK_ID)));
                    human.setLogFrameID(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_LOGFRAME_FK_ID)));
                    human.setResourceID(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_RESOURCE_FK_ID)));
                    human.setServerID(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_SERVER_ID)));
                    human.setOwnerID(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OWNER_ID)));
                    human.setOrgID(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ORG_ID)));
                    human.setGroupBITS(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_GROUP_BITS)));
                    human.setPermsBITS(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PERMS_BITS)));
                    human.setStatusBITS(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_STATUS_BITS)));
                    human.setQuantity(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_QUANTITY)));
                    human.setStartDate(Timestamp.valueOf(
                            cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_START_DATE))));
                    human.setEndDate(Timestamp.valueOf(
                            cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_END_DATE))));
                    human.setCreatedDate(Timestamp.valueOf(
                            cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_CREATED_DATE))));
                    human.setModifiedDate(Timestamp.valueOf(
                            cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_MODIFIED_DATE))));
                    human.setSyncedDate(Timestamp.valueOf(
                            cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_SYNCED_DATE))));

                    /* objects */

                    /* populate a logframe object */
                    human.setLogFrameModel(getLogFrameModelByID(human.getLogFrameID(), userID,
                            primaryRoleBITS, secondaryRoleBITS, statusBITS));
                    /* populate an resource object */
                    human.setResourceModel(getResourceModelByID(
                            human.getResourceID(), userID, primaryRoleBITS, secondaryRoleBITS,
                            statusBITS));
                    /* populate an activity object */
                    human.setActivityModel(getActivityModelByID(
                            human.getWorkplanID(), userID, primaryRoleBITS, secondaryRoleBITS,
                            statusBITS));

                    /* sets */
                    /* populate user models */
                    human.setUserModelSet(getUserModelSetByID(human.getInputID(),
                            userID, primaryRoleBITS, secondaryRoleBITS, statusBITS));

                    /* populate question components */
                    human.setQuestionModelSet(getQuestionModelSetByID(human.getInputID(),
                            userID, primaryRoleBITS, secondaryRoleBITS, statusBITS));
                    /* populate journal components */
                    human.setJournalModelSet(getJournalModelSetByID(human.getInputID(), userID,
                            primaryRoleBITS, secondaryRoleBITS, statusBITS));

                    /* maps */

                    /* populate child activity for the input*/
                    human.setChildActivityModelSet(getChildActivitySetByID(human.getInputID(),
                            userID, primaryRoleBITS, secondaryRoleBITS, statusBITS));

                    /* add expenses */
                    humanModelSet.add(human);

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error in reading all HUMAN entities: " + e.getMessage());
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        // close the database connection
        db.close();

        return humanModelSet;
    }

    protected Set<cUserModel> getUserModelSetByID(
            long inputID, long userID, int primaryRoleBITS, int secondaryRoleBITS, int statusBITS) {

        Set<cUserModel> userModelSet = new HashSet<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String selectQuery = "SELECT " +
                "H." + cSQLDBHelper.KEY_INPUT_FK_ID + ", U." + cSQLDBHelper.KEY_ID + ", " +
                "U." + cSQLDBHelper.KEY_ORGANIZATION_FK_ID + ", " +
                "U." + cSQLDBHelper.KEY_SERVER_ID + ", U." + cSQLDBHelper.KEY_OWNER_ID + ", " +
                "U." + cSQLDBHelper.KEY_ORG_ID + ", U." + cSQLDBHelper.KEY_GROUP_BITS + ", " +
                "U." + cSQLDBHelper.KEY_PERMS_BITS + ", U." + cSQLDBHelper.KEY_STATUS_BITS + ", " +
                "U." + cSQLDBHelper.KEY_NAME + ", U." + cSQLDBHelper.KEY_SURNAME + ", " +
                "U." + cSQLDBHelper.KEY_EMAIL + ", U." + cSQLDBHelper.KEY_PHONE + ", " +
                "U." + cSQLDBHelper.KEY_CREATED_DATE + ", U." + cSQLDBHelper.KEY_MODIFIED_DATE + ", " +
                "U." + cSQLDBHelper.KEY_SYNCED_DATE +
                " FROM " +
                cSQLDBHelper.TABLE_tblHUMAN + " H, " +
                cSQLDBHelper.TABLE_tblUSER + " U, " +
                cSQLDBHelper.TABLE_tblHUMANSET + " H_U " +
                " WHERE ((H." + cSQLDBHelper.KEY_INPUT_FK_ID + " = H_U." + cSQLDBHelper.KEY_INPUT_FK_ID +
                " AND U." + cSQLDBHelper.KEY_ID + " = H_U." + cSQLDBHelper.KEY_USER_FK_ID +
                " AND H." + cSQLDBHelper.KEY_INPUT_FK_ID + " = ?) AND " +
                /* read access permissions */
                /* organization creator is always allowed to do everything (i.e., where: userID = orgID)*/
                " ((U." + cSQLDBHelper.KEY_ORG_ID + " = ?) " +
                /* owner permission */
                " OR ((((U." + cSQLDBHelper.KEY_OWNER_ID + " = ?) " +
                " AND ((U." + cSQLDBHelper.KEY_PERMS_BITS + " & " + cBitwise.OWNER_READ + ") != 0))" +
                /* group (owner/primary organization) permission */
                " OR (((U." + cSQLDBHelper.KEY_GROUP_BITS + " & ?) != 0) " +
                " AND ((U." + cSQLDBHelper.KEY_PERMS_BITS + " & " + cBitwise.GROUP_READ + ") != 0))" +
                /* other (secondary organizations) permission */
                " OR (((U." + cSQLDBHelper.KEY_GROUP_BITS + " & ?) != 0) " +
                " AND ((U." + cSQLDBHelper.KEY_PERMS_BITS + " & " + cBitwise.OTHER_READ + ") != 0)))" +
                /* owner, group and other permissions allowed when the statuses hold */
                " AND ((U." + cSQLDBHelper.KEY_STATUS_BITS + " = 0) " +
                " OR ((U." + cSQLDBHelper.KEY_STATUS_BITS + " & ?) != 0)))))";

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
                    cUserModel user = new cUserModel();

                    user.setUserID(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ID)));
                    user.setOrganizationID(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ORGANIZATION_FK_ID)));
                    user.setServerID(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_SERVER_ID)));
                    user.setOwnerID(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OWNER_ID)));
                    user.setOrgID(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ORG_ID)));
                    user.setGroupBITS(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_GROUP_BITS)));
                    user.setPermsBITS(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PERMS_BITS)));
                    user.setStatusBITS(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_STATUS_BITS)));
                    user.setName(
                            cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_NAME)));
                    user.setSurname(
                            cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_SURNAME)));
                    user.setPhone(
                            cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_PHONE)));
                    user.setEmail(
                            cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_EMAIL)));
                    user.setCreatedDate(Timestamp.valueOf(
                            cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_CREATED_DATE))));
                    user.setModifiedDate(Timestamp.valueOf(
                            cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_MODIFIED_DATE))));
                    user.setSyncedDate(Timestamp.valueOf(
                            cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_SYNCED_DATE))));

                    userModelSet.add(user);

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to read a USER entity: " + e.getMessage());
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        // close the database connection
        db.close();

        return userModelSet;
    }

}
