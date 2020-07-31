package com.me.mseotsanyana.mande.DAL.ìmpl.evaluator;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.me.mseotsanyana.mande.BLL.repository.evaluator.iEvaluationRepository;
import com.me.mseotsanyana.mande.DAL.model.evaluator.cArrayChoiceModel;
import com.me.mseotsanyana.mande.DAL.model.evaluator.cEvaluationModel;
import com.me.mseotsanyana.mande.DAL.model.evaluator.cEvaluationResponseModel;
import com.me.mseotsanyana.mande.DAL.model.evaluator.cEvaluationTypeModel;
import com.me.mseotsanyana.mande.DAL.model.evaluator.cMatrixChoiceModel;
import com.me.mseotsanyana.mande.DAL.model.logframe.cArrayTypeModel;
import com.me.mseotsanyana.mande.DAL.model.logframe.cLogFrameModel;
import com.me.mseotsanyana.mande.DAL.model.logframe.cMatrixTypeModel;
import com.me.mseotsanyana.mande.DAL.model.session.cUserModel;
import com.me.mseotsanyana.mande.DAL.storage.database.cSQLDBHelper;
import com.me.mseotsanyana.mande.DAL.storage.preference.cBitwise;
import com.me.mseotsanyana.mande.DAL.ìmpl.logframe.cLogFrameRepositoryImpl;
import com.me.mseotsanyana.mande.DAL.ìmpl.logframe.cQuestionRepositoryImpl;
import com.me.mseotsanyana.mande.UTIL.cConstant;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class cEvaluationRepositoryImpl implements iEvaluationRepository {
    private static SimpleDateFormat sdf = cConstant.FORMAT_DATE;
    private static String TAG = cEvaluationRepositoryImpl.class.getSimpleName();

    // an object of the database helper
    private cSQLDBHelper dbHelper;

    private cLogFrameRepositoryImpl logFrameRepository;
    private cEvaluationTypeRepositoryImpl evaluationTypeRepository;
    private cQuestionRepositoryImpl questionRepository;

    public cEvaluationRepositoryImpl(Context context) {
        dbHelper = new cSQLDBHelper(context);
        logFrameRepository = new cLogFrameRepositoryImpl(context);
        evaluationTypeRepository = new cEvaluationTypeRepositoryImpl(context);
        questionRepository = new cQuestionRepositoryImpl(context);
    }

    /* ###################################### CREATE ACTIONS #####################################*/

    /*
     * the function adding a specific input
     */
    @Override
    public boolean addEvaluation(cEvaluationModel evaluationModel) {
        return false;
    }

    /* ###################################### READ ACTIONS #######################################*/

    protected cEvaluationModel getEvaluationModelByID(
            long evaluationID, long userID, int primaryRoleBITS, int secondaryRoleBITS,
            int statusBITS) {

        // open the connection to the database
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // construct a selection query
        String selectQuery = "SELECT * FROM " + cSQLDBHelper.TABLE_tblEVALUATION +
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
                String.valueOf(evaluationID),
                String.valueOf(userID),           /* access due to organization creator */
                String.valueOf(userID),           /* access due to record owner */
                String.valueOf(primaryRoleBITS),  /* access due to membership in primary role */
                String.valueOf(secondaryRoleBITS),/* access due to membership in secondary role */
                String.valueOf(statusBITS)});     /* access due to assigned statuses */

        cEvaluationModel evaluation = new cEvaluationModel();

        try {
            if (cursor.moveToFirst()) {

                evaluation.setEvaluationID(
                        cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ID)));
                evaluation.setLogFrameID(
                        cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_LOGFRAME_FK_ID)));
                evaluation.setEvaluationTypeID(
                        cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_EVALUATION_TYPE_FK_ID)));
                evaluation.setServerID(
                        cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_SERVER_ID)));
                evaluation.setOwnerID(
                        cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OWNER_ID)));
                evaluation.setOrgID(
                        cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ORG_ID)));
                evaluation.setGroupBITS(
                        cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_GROUP_BITS)));
                evaluation.setPermsBITS(
                        cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PERMS_BITS)));
                evaluation.setStatusBITS(
                        cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_STATUS_BITS)));
                evaluation.setName(
                        cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_NAME)));
                evaluation.setDescription(
                        cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_DESCRIPTION)));
                evaluation.setStartDate(Timestamp.valueOf(
                        cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_START_DATE))));
                evaluation.setEndDate(Timestamp.valueOf(
                        cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_END_DATE))));
                evaluation.setCreatedDate(Timestamp.valueOf(
                        cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_CREATED_DATE))));
                evaluation.setModifiedDate(Timestamp.valueOf(
                        cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_MODIFIED_DATE))));
                evaluation.setSyncedDate(Timestamp.valueOf(
                        cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_SYNCED_DATE))));
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to read an EVALUATION entity: " + e.getMessage());
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        // close the database connection
        db.close();

        return evaluation;
    }

    /**
     * return evaluation set
     *
     * @param logFrameID        logframe identification
     * @param userID            user identification
     * @param primaryRoleBITS   primary role bits
     * @param secondaryRoleBITS secondary role bits
     * @param statusBITS        status bits
     * @return evaluation set
     */
    @Override
    public Set<cEvaluationModel> getEvaluationModelSet(long logFrameID, long userID,
                                                       int primaryRoleBITS, int secondaryRoleBITS,
                                                       int statusBITS) {

        Set<cEvaluationModel> evaluationModelSet = new HashSet<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + cSQLDBHelper.TABLE_tblEVALUATION +
                " WHERE ((" + cSQLDBHelper.KEY_LOGFRAME_FK_ID + " = ?) AND " +
                /* read access permissions */
                /* organization creator is always allowed to do everything (i.e., where: userID = orgID)*/
                " ((" + cSQLDBHelper.KEY_ORG_ID + " = ?) " +
                /* owner permission */
                " OR ((((" + cSQLDBHelper.KEY_OWNER_ID + " = ?) " +
                " AND ((" + cSQLDBHelper.KEY_PERMS_BITS + " & " + cBitwise.OWNER_READ + ") != 0)) " +
                /* group (owner/primary organization) permission */
                " OR (((" + cSQLDBHelper.KEY_GROUP_BITS + " & ?) != 0) " +
                " AND ((" + cSQLDBHelper.KEY_PERMS_BITS + " & " + cBitwise.GROUP_READ + ") != 0)) " +
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

        try {
            if (cursor.moveToFirst()) {
                do {
                    cEvaluationModel evaluation = new cEvaluationModel();

                    evaluation.setEvaluationID(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ID)));
                    evaluation.setLogFrameID(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_LOGFRAME_FK_ID)));
                    evaluation.setEvaluationTypeID(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_EVALUATION_TYPE_FK_ID)));
                    evaluation.setServerID(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_SERVER_ID)));
                    evaluation.setOwnerID(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OWNER_ID)));
                    evaluation.setOrgID(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ORG_ID)));
                    evaluation.setGroupBITS(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_GROUP_BITS)));
                    evaluation.setPermsBITS(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PERMS_BITS)));
                    evaluation.setStatusBITS(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_STATUS_BITS)));
                    evaluation.setName(
                            cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_NAME)));
                    evaluation.setDescription(
                            cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_DESCRIPTION)));
                    evaluation.setStartDate(Timestamp.valueOf(
                            cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_START_DATE))));
                    evaluation.setEndDate(Timestamp.valueOf(
                            cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_END_DATE))));
                    evaluation.setCreatedDate(Timestamp.valueOf(
                            cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_CREATED_DATE))));
                    evaluation.setModifiedDate(Timestamp.valueOf(
                            cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_MODIFIED_DATE))));
                    evaluation.setSyncedDate(Timestamp.valueOf(
                            cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_SYNCED_DATE))));

                    /* set a logframe for the evaluation */
                    evaluation.setLogFrameModel(logFrameRepository.getLogFrameModelByID(
                            evaluation.getLogFrameID(), userID, primaryRoleBITS, secondaryRoleBITS,
                            statusBITS));

                    /* set a evaluation type for the evaluation */
                    evaluation.setEvaluationTypeModel(
                            evaluationTypeRepository.getEvaluationTypeModelByID(
                                    evaluation.getEvaluationTypeID(), userID, primaryRoleBITS,
                                    secondaryRoleBITS, statusBITS));

                    /* populate user set */
                    evaluation.setUserModelSet(getUserModelSetByID(evaluation.getEvaluationID(),
                            userID, primaryRoleBITS, secondaryRoleBITS, statusBITS));

                    /* populate question set with associated sets */
                    evaluation.setQuestionModelSet(questionRepository.getQuestionModelSetByID(
                            evaluation.getEvaluationID(), userID, primaryRoleBITS,
                            secondaryRoleBITS, statusBITS));

                    /* add impact in the set */
                    evaluationModelSet.add(evaluation);

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while reading EVALUATION table: " + e.getMessage());
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        // close the database connection
        db.close();

        return evaluationModelSet;
    }

    /**
     * return user set
     *
     * @param evaluationID      evaluation identification
     * @param userID            user identification
     * @param primaryRoleBITS   primary role bits
     * @param secondaryRoleBITS secondary role bits
     * @param statusBITS        status bits
     * @return user set
     */
    protected Set<cUserModel> getUserModelSetByID(long evaluationID, long userID,
                                                  int primaryRoleBITS, int secondaryRoleBITS,
                                                  int statusBITS) {

        Set<cUserModel> userModelSet = new HashSet<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String selectQuery = "SELECT " +
                "E." + cSQLDBHelper.KEY_EVALUATION_FK_ID + ", U." + cSQLDBHelper.KEY_ID + ", " +
                "U." + cSQLDBHelper.KEY_ORGANIZATION_FK_ID + ", " +
                "U." + cSQLDBHelper.KEY_SERVER_ID + ", U." + cSQLDBHelper.KEY_OWNER_ID + ", " +
                "U." + cSQLDBHelper.KEY_ORG_ID + ", U." + cSQLDBHelper.KEY_GROUP_BITS + ", " +
                "U." + cSQLDBHelper.KEY_PERMS_BITS + ", U." + cSQLDBHelper.KEY_STATUS_BITS + ", " +
                "U." + cSQLDBHelper.KEY_NAME + ", U." + cSQLDBHelper.KEY_SURNAME + ", " +
                "U." + cSQLDBHelper.KEY_EMAIL + ", U." + cSQLDBHelper.KEY_PHONE + ", " +
                "U." + cSQLDBHelper.KEY_CREATED_DATE + ", U." + cSQLDBHelper.KEY_MODIFIED_DATE + ", " +
                "U." + cSQLDBHelper.KEY_SYNCED_DATE +
                " FROM " +
                cSQLDBHelper.TABLE_tblEVALUATION + " E, " +
                cSQLDBHelper.TABLE_tblUSER + " U, " +
                cSQLDBHelper.TABLE_tblUSER_EVALUATION + " U_E " +
                " WHERE ((E." + cSQLDBHelper.KEY_INPUT_FK_ID + " = U_E." + cSQLDBHelper.KEY_EVALUATION_FK_ID +
                " AND U." + cSQLDBHelper.KEY_ID + " = U_E." + cSQLDBHelper.KEY_USER_FK_ID +
                " AND E." + cSQLDBHelper.KEY_EVALUATION_FK_ID + " = ?) AND " +
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
                String.valueOf(evaluationID),
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

    private Set<cEvaluationResponseModel> getEvaluationResponseModelSetByID(
            long evaluationID, long userID, int primaryRoleBITS, int secondaryRoleBITS,
            int statusBITS) {

        return null;
    }

    private Map<cArrayTypeModel, Set<cArrayChoiceModel>> getArrayChoiceModelMapByID(
            long evaluationID, long userID, int primaryRoleBITS, int secondaryRoleBITS,
            int statusBITS) {

        return null;
    }

    private Map<cMatrixTypeModel, Set<cMatrixChoiceModel>> getMatrixChoiceModelMapByID(
            long evaluationID, long userID, int primaryRoleBITS, int secondaryRoleBITS,
            int statusBITS) {

        return null;
    }
}
