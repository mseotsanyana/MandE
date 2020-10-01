package com.me.mseotsanyana.mande.DAL.ìmpl.evaluator;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.util.Pair;

import com.me.mseotsanyana.mande.DAL.model.evaluator.cArrayResponseModel;
import com.me.mseotsanyana.mande.DAL.storage.database.cSQLDBHelper;
import com.me.mseotsanyana.mande.DAL.storage.preference.cBitwise;
import com.me.mseotsanyana.mande.UTIL.cConstant;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Set;

public class cArrayResponseRepositoryImpl extends cEvaluationResponseRepositoryImpl {
    private static SimpleDateFormat sdf = cConstant.FORMAT_DATE;
    private static String TAG = cArrayResponseRepositoryImpl.class.getSimpleName();

    // an object of the database helper
    private cSQLDBHelper dbHelper;
    private cUserEvaluationRepositoryImpl userEvaluationRepository;

    public cArrayResponseRepositoryImpl(Context context) {
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

    public Set<cArrayResponseModel> getArrayResponseModelSetByID(
            long questionID, long userID, int primaryRoleBITS, int secondaryRoleBITS,
            int statusBITS) {

        // set of numeric responses
        Set<cArrayResponseModel> arrayResponseModelSet = new HashSet<>();

        // open the connection to the database
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String selectQuery = "SELECT * FROM " +
                cSQLDBHelper.TABLE_tblEVALUATION_RESPONSE +" ER INNER JOIN " +
                cSQLDBHelper.TABLE_tblARRAYRESPONSE +" A ON ER."+cSQLDBHelper.KEY_ID +" = A." +
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
                    cArrayResponseModel array = new cArrayResponseModel();

                    array.setEvaluationResponseID(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ID)));
                    array.setQuestionID(cursor.getInt(
                            cursor.getColumnIndex(cSQLDBHelper.KEY_QUESTION_FK_ID)));
                    array.setArrayChoiceID(cursor.getInt(
                            cursor.getColumnIndex(cSQLDBHelper.KEY_ARRAY_CHOICE_FK_ID)));
                    array.setServerID(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_SERVER_ID)));
                    array.setOwnerID(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OWNER_ID)));
                    array.setOrgID(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ORG_ID)));
                    array.setGroupBITS(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_GROUP_BITS)));
                    array.setPermsBITS(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PERMS_BITS)));
                    array.setStatusBITS(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_STATUS_BITS)));
                    array.setCreatedDate(Timestamp.valueOf(
                            cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_CREATED_DATE))));
                    array.setModifiedDate(Timestamp.valueOf(
                            cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_MODIFIED_DATE))));
                    array.setSyncedDate(Timestamp.valueOf(
                            cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_SYNCED_DATE))));

                    /* objects */

                    /* populate a user evaluation in superclass object */
                    array.setUserEvaluationModel(
                            userEvaluationRepository.getUserEvaluationModelByID(
                                    array.getEvaluationResponseID(), userID, primaryRoleBITS,
                                    secondaryRoleBITS, statusBITS));

                    /* populate array choices for the response */

                    /* add numeric responses */
                    arrayResponseModelSet.add(array);

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error in reading all ARRAY RESPONSE entities: " + e.getMessage());
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        // close the database connection
        db.close();

        return arrayResponseModelSet;
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

    public Set<Pair<String, Long>> getArrayResponseFreqTableByID(
            long questionID, long userID, int primaryRoleBITS, int secondaryRoleBITS,
            int statusBITS) {

        // set of frequency table responses
        Set<Pair<String, Long>> arrayResponseFreqTableSet = new HashSet<>();

        // open the connection to the database
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String selectQuery = "SELECT AC."+cSQLDBHelper.KEY_NAME +" AS CHOICE, " +
                " COUNT(AR."+cSQLDBHelper.KEY_ARRAY_RESPONSE_FK_ID+") AS FREQUENCY FROM " +
                cSQLDBHelper.TABLE_tblEVALUATION_RESPONSE +" ER INNER JOIN " +
                cSQLDBHelper.TABLE_tblARRAYRESPONSE +" AR ON ER."+cSQLDBHelper.KEY_ID +" = AR." +
                cSQLDBHelper.KEY_EVALUATION_RESPONSE_FK_ID +", " + cSQLDBHelper.TABLE_tblARRAYCHOICE +" AC " +
                " WHERE ((AC." + cSQLDBHelper.KEY_ID + " = AR." + cSQLDBHelper.KEY_ARRAY_RESPONSE_FK_ID +
                " AND AR."+cSQLDBHelper.KEY_QUESTION_FK_ID + " = ?) AND " +
                /* read access permissions */
                /* organization creator is always allowed to do everything (i.e., where: userID = orgID)*/
                " ((ER." + cSQLDBHelper.KEY_ORG_ID + " = ?) " +
                /* owner permission */
                " OR ((((ER." + cSQLDBHelper.KEY_OWNER_ID + " = ?) " +
                " AND ((ER." + cSQLDBHelper.KEY_PERMS_BITS + " & " + cBitwise.OWNER_READ + ") != 0))" +
                /* group (owner/primary organization) permission */
                " OR (((ER." + cSQLDBHelper.KEY_GROUP_BITS + " & ?) != 0) " +
                " AND ((ER." + cSQLDBHelper.KEY_PERMS_BITS + " & " + cBitwise.GROUP_READ + ") != 0))" +
                /* other (secondary organizations) permission */
                " OR (((ER." + cSQLDBHelper.KEY_GROUP_BITS + " & ?) != 0) " +
                " AND ((ER." + cSQLDBHelper.KEY_PERMS_BITS + " & " + cBitwise.OTHER_READ + ") != 0)))" +
                /* owner, group and other permissions allowed when the statuses hold */
                " AND ((ER." + cSQLDBHelper.KEY_STATUS_BITS + " = 0) " +
                " OR ((ER." + cSQLDBHelper.KEY_STATUS_BITS + " & ?) != 0))))) " +
                " GROUP BY AC."+cSQLDBHelper.KEY_NAME;

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

                    String choice = cursor.getString(cursor.getColumnIndex("CHOICE"));
                    long frequency = cursor.getLong(cursor.getColumnIndex("FREQUENCY"));

                    arrayResponseFreqTableSet.add(new Pair<String, Long>(choice, frequency));

                    //Log.d(TAG," TABLE = "+questionID+" -> "+choice+", "+frequency);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error in reading all ARRAY RESPONSE FREQUENCIES: " + e.getMessage());
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        // close the database connection
        db.close();

        return arrayResponseFreqTableSet;
    }
}
