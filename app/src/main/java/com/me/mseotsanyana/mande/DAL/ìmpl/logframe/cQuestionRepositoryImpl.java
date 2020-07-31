package com.me.mseotsanyana.mande.DAL.ìmpl.logframe;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.util.Pair;

import com.me.mseotsanyana.mande.BLL.repository.logframe.iQuestionRepository;
import com.me.mseotsanyana.mande.DAL.model.evaluator.cArrayChoiceModel;
import com.me.mseotsanyana.mande.DAL.model.evaluator.cColOptionModel;
import com.me.mseotsanyana.mande.DAL.model.evaluator.cRowOptionModel;
import com.me.mseotsanyana.mande.DAL.model.logframe.cQuestionModel;
import com.me.mseotsanyana.mande.DAL.storage.database.cSQLDBHelper;
import com.me.mseotsanyana.mande.DAL.storage.preference.cBitwise;
import com.me.mseotsanyana.mande.DAL.ìmpl.evaluator.cArrayResponseRepositoryImpl;
import com.me.mseotsanyana.mande.DAL.ìmpl.evaluator.cDateResponseRepositoryImpl;
import com.me.mseotsanyana.mande.DAL.ìmpl.evaluator.cMatrixResponseRepositoryImpl;
import com.me.mseotsanyana.mande.DAL.ìmpl.evaluator.cNumericResponseRepositoryImpl;
import com.me.mseotsanyana.mande.DAL.ìmpl.evaluator.cTextResponseRepositoryImpl;
import com.me.mseotsanyana.mande.UTIL.cConstant;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Set;

public class cQuestionRepositoryImpl implements iQuestionRepository {
    private static SimpleDateFormat sdf = cConstant.FORMAT_DATE;
    private static String TAG = cQuestionRepositoryImpl.class.getSimpleName();

    // an object of the database helper
    private cSQLDBHelper dbHelper;

    private cLogFrameRepositoryImpl logFrameRepository;
    private cQuestionGroupingRepositoryImpl questionGroupingRepository;
    private cQuestionTypeRepositoryImpl questionTypeRepository;

    private cDateResponseRepositoryImpl dateResponseRepository;
    private cNumericResponseRepositoryImpl numericResponseRepository;
    private cTextResponseRepositoryImpl textResponseRepository;
    private cArrayResponseRepositoryImpl arrayResponseRepository;
    private cMatrixResponseRepositoryImpl matrixResponseRepository;

    public cQuestionRepositoryImpl(Context context) {
        dbHelper = new cSQLDBHelper(context);
        logFrameRepository = new cLogFrameRepositoryImpl(context);
        questionGroupingRepository = new cQuestionGroupingRepositoryImpl(context);
        questionTypeRepository = new cQuestionTypeRepositoryImpl(context);

        dateResponseRepository = new cDateResponseRepositoryImpl(context);
        numericResponseRepository = new cNumericResponseRepositoryImpl(context);
        textResponseRepository = new cTextResponseRepositoryImpl(context);
        arrayResponseRepository = new cArrayResponseRepositoryImpl(context);
        matrixResponseRepository = new cMatrixResponseRepositoryImpl(context);
    }


    /**
     * return question set
     *
     * @param evaluationID evaluation identification
     * @param userID user identification
     * @param primaryRoleBITS primary role bits
     * @param secondaryRoleBITS secondary role bits
     * @param statusBITS status bits
     * @return question set
     */
    @Override
    public Set<cQuestionModel> getQuestionModelSetByID(long evaluationID, long userID,
                                                        int primaryRoleBITS, int secondaryRoleBITS,
                                                        int statusBITS) {

        Set<cQuestionModel> questionModelSet = new HashSet<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String selectQuery = "SELECT " +
                "E." + cSQLDBHelper.KEY_ID + ", Q." + cSQLDBHelper.KEY_LOGFRAME_FK_ID + ", " +
                "Q." + cSQLDBHelper.KEY_QUESTION_TYPE_FK_ID + ", " +
                "Q." + cSQLDBHelper.KEY_QUESTION_GROUPING_FK_ID + ", " +
                "Q." + cSQLDBHelper.KEY_SERVER_ID + ", Q." + cSQLDBHelper.KEY_OWNER_ID + ", " +
                "Q." + cSQLDBHelper.KEY_ORG_ID + ", Q." + cSQLDBHelper.KEY_GROUP_BITS + ", " +
                "Q." + cSQLDBHelper.KEY_PERMS_BITS + ", Q." + cSQLDBHelper.KEY_STATUS_BITS + ", " +
                "Q." + cSQLDBHelper.KEY_NAME + ", Q." + cSQLDBHelper.KEY_DESCRIPTION + ", " +
                "Q." + cSQLDBHelper.KEY_START_DATE + ", Q." + cSQLDBHelper.KEY_END_DATE + ", " +
                "Q." + cSQLDBHelper.KEY_CREATED_DATE + ", Q." + cSQLDBHelper.KEY_MODIFIED_DATE + ", " +
                "Q." + cSQLDBHelper.KEY_SYNCED_DATE +
                " FROM " +
                cSQLDBHelper.TABLE_tblEVALUATION + " E, " +
                cSQLDBHelper.TABLE_tblQUESTION + " Q, " +
                cSQLDBHelper.TABLE_tblOUTPUT_QUESTION + " E_Q " +
                " WHERE ((E." + cSQLDBHelper.KEY_ID + " = E_Q." + cSQLDBHelper.KEY_EVALUATION_FK_ID +
                " AND Q." + cSQLDBHelper.KEY_ID + " = E_Q." + cSQLDBHelper.KEY_QUESTION_FK_ID +
                " AND E." + cSQLDBHelper.KEY_ID + " = ?) AND " +
                /* read access permissions */
                /* organization creator is always allowed to do everything (i.e., where: userID = orgID)*/
                " ((Q." + cSQLDBHelper.KEY_ORG_ID + " = ?) " +
                /* owner permission */
                " OR ((((Q." + cSQLDBHelper.KEY_OWNER_ID + " = ?) " +
                " AND ((Q." + cSQLDBHelper.KEY_PERMS_BITS + " & " + cBitwise.OWNER_READ + ") != 0))" +
                /* group (owner/primary organization) permission */
                " OR (((Q." + cSQLDBHelper.KEY_GROUP_BITS + " & ?) != 0) " +
                " AND ((Q." + cSQLDBHelper.KEY_PERMS_BITS + " & " + cBitwise.GROUP_READ + ") != 0))" +
                /* other (secondary organizations) permission */
                " OR (((Q." + cSQLDBHelper.KEY_GROUP_BITS + " & ?) != 0) " +
                " AND ((Q." + cSQLDBHelper.KEY_PERMS_BITS + " & " + cBitwise.OTHER_READ + ") != 0)))" +
                /* owner, group and other permissions allowed when the statuses hold */
                " AND ((Q." + cSQLDBHelper.KEY_STATUS_BITS + " = 0) " +
                " OR ((Q." + cSQLDBHelper.KEY_STATUS_BITS + " & ?) != 0)))))";

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
                    cQuestionModel question = new cQuestionModel();

                    question.setQuestionID(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ID)));
                    question.setLogFrameID(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_LOGFRAME_FK_ID)));
                    question.setQuestionTypeID(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_QUESTION_TYPE_FK_ID)));
                    question.setQuestionGroupID(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_QUESTION_GROUPING_FK_ID)));
                    question.setServerID(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_SERVER_ID)));
                    question.setOwnerID(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OWNER_ID)));
                    question.setOrgID(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ORG_ID)));
                    question.setGroupBITS(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_GROUP_BITS)));
                    question.setPermsBITS(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PERMS_BITS)));
                    question.setStatusBITS(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_STATUS_BITS)));
                    question.setName(
                            cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_NAME)));
                    question.setDescription(
                            cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_DESCRIPTION)));
                    question.setStartDate(Timestamp.valueOf(
                            cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_START_DATE))));
                    question.setEndDate(Timestamp.valueOf(
                            cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_END_DATE))));
                    question.setCreatedDate(Timestamp.valueOf(
                            cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_CREATED_DATE))));
                    question.setModifiedDate(Timestamp.valueOf(
                            cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_MODIFIED_DATE))));
                    question.setSyncedDate(Timestamp.valueOf(
                            cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_SYNCED_DATE))));

                    /* objects */
                    /* logframe object */
                    question.setLogFrameModel(logFrameRepository.getLogFrameModelByID(
                            question.getLogFrameID(), userID, primaryRoleBITS, secondaryRoleBITS,
                            statusBITS));

                    /* question grouping object */
                    question.setQuestionGroupingModel(
                            questionGroupingRepository.getQuestionGroupingModelByID(
                                    question.getQuestionGroupID(), userID, primaryRoleBITS,
                                    secondaryRoleBITS, statusBITS));

                    /* question type object */
                    question.setQuestionTypeModel(questionTypeRepository.getQuestionTypeModelByID(
                            question.getQuestionTypeID(), userID, primaryRoleBITS,
                            secondaryRoleBITS, statusBITS));

                    /* sets */
                    /* set of evaluation responses */
                    question.setDateResponseModelSet(
                            dateResponseRepository.getDateResponseModelSetByID(
                                    question.getQuestionID(), userID, primaryRoleBITS,
                                    secondaryRoleBITS, statusBITS));

                    question.setNumericResponseModelSet(
                            numericResponseRepository.getNumericResponseModelSetByID(
                                    question.getQuestionID(), userID, primaryRoleBITS,
                                    secondaryRoleBITS, statusBITS));

                    question.setTextResponseModelSet(
                            textResponseRepository.getTextResponseModelSetByID(
                                    question.getQuestionID(), userID, primaryRoleBITS,
                                    secondaryRoleBITS, statusBITS));

                    question.setArrayResponseModelSet(
                            arrayResponseRepository.getArrayResponseModelSetByID(
                                    question.getQuestionID(), userID, primaryRoleBITS,
                                    secondaryRoleBITS, statusBITS));

                    question.setMatrixResponseModelSet(
                            matrixResponseRepository.getMatrixResponseModelSetByID(
                                    question.getQuestionID(), userID, primaryRoleBITS,
                                    secondaryRoleBITS, statusBITS));

                    /* maps */
                    /* map of array respond choices */
                    question.setArrayChoiceModelSet(getArrayChoiceModelSetByID(
                            question.getQuestionID(), userID, primaryRoleBITS,
                            secondaryRoleBITS, statusBITS));

                    /* map of matrix respond choices */
                    question.setMatrixChoiceModelSet(getMatrixChoiceModelSetByID(
                            question.getQuestionID(), userID, primaryRoleBITS,
                            secondaryRoleBITS, statusBITS));

                    questionModelSet.add(question);

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to read a QUESTION entity: " + e.getMessage());
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        // close the database connection
        db.close();

        return questionModelSet;
    }

    public Set<cArrayChoiceModel> getArrayChoiceModelSetByID(
            long questionID, long userID, int primaryRoleBITS, int secondaryRoleBITS,
            int statusBITS) {

        Set<cArrayChoiceModel> arrayChoiceModelSet = new HashSet<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String selectQuery = "SELECT C." + cSQLDBHelper.KEY_ID + ", " +
                "C." + cSQLDBHelper.KEY_SERVER_ID + ", C." + cSQLDBHelper.KEY_OWNER_ID + ", " +
                "C." + cSQLDBHelper.KEY_ORG_ID + ", C." + cSQLDBHelper.KEY_GROUP_BITS + ", " +
                "C." + cSQLDBHelper.KEY_PERMS_BITS + ", C." + cSQLDBHelper.KEY_STATUS_BITS + ", " +
                "C." + cSQLDBHelper.KEY_NAME + ", C." + cSQLDBHelper.KEY_DESCRIPTION + ", " +
                "C." + cSQLDBHelper.KEY_CREATED_DATE + ", C." + cSQLDBHelper.KEY_MODIFIED_DATE + ", " +
                "C." + cSQLDBHelper.KEY_SYNCED_DATE +
                " FROM " +
                cSQLDBHelper.TABLE_tblQUESTION +" Q, " +
                cSQLDBHelper.TABLE_tblARRAYTYPE +" T, " +
                cSQLDBHelper.TABLE_tblARRAYCHOICE + " C, " +
                cSQLDBHelper.TABLE_tblARRAYCHOICESET +" CS " +
                " WHERE ((Q." + cSQLDBHelper.KEY_ID + " = CS." + cSQLDBHelper.KEY_QUESTION_FK_ID +
                " AND T." + cSQLDBHelper.KEY_QUESTION_TYPE_FK_ID + " = CS." + cSQLDBHelper.KEY_ARRAY_TYPE_FK_ID +
                " AND C." + cSQLDBHelper.KEY_ID + " = CS." + cSQLDBHelper.KEY_ARRAY_CHOICE_FK_ID +
                " AND Q." + cSQLDBHelper.KEY_ID + " = ?) AND " +
                /* read access permissions */
                /* organization creator is always allowed to do everything (i.e., where: userID = orgID)*/
                " ((C." + cSQLDBHelper.KEY_ORG_ID + " = ?) " +
                /* owner permission */
                " OR ((((C." + cSQLDBHelper.KEY_OWNER_ID + " = ?) " +
                " AND ((C." + cSQLDBHelper.KEY_PERMS_BITS + " & " + cBitwise.OWNER_READ + ") != 0))" +
                /* group (owner/primary organization) permission */
                " OR (((C." + cSQLDBHelper.KEY_GROUP_BITS + " & ?) != 0) " +
                " AND ((C." + cSQLDBHelper.KEY_PERMS_BITS + " & " + cBitwise.GROUP_READ + ") != 0))" +
                /* other (secondary organizations) permission */
                " OR (((C." + cSQLDBHelper.KEY_GROUP_BITS + " & ?) != 0) " +
                " AND ((C." + cSQLDBHelper.KEY_PERMS_BITS + " & " + cBitwise.OTHER_READ + ") != 0)))" +
                /* owner, group and other permissions allowed when the statuses hold */
                " AND ((C." + cSQLDBHelper.KEY_STATUS_BITS + " = 0) " +
                " OR ((C." + cSQLDBHelper.KEY_STATUS_BITS + " & ?) != 0)))))";

        Cursor cursor = db.rawQuery(selectQuery, new String[]{
                String.valueOf(questionID),
                String.valueOf(userID),           /* access due to organization creator */
                String.valueOf(userID),           /* access due to record owner */
                String.valueOf(primaryRoleBITS),  /* access due to membership in primary role */
                String.valueOf(secondaryRoleBITS),/* access due to membership in secondary role */
                String.valueOf(statusBITS)});     /* access due to assigned statuses */

        try {
            if (cursor.moveToFirst()) {
                do {
                    cArrayChoiceModel arrayChoice = new cArrayChoiceModel();

                    arrayChoice.setArrayChoiceID(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ID)));
                    arrayChoice.setServerID(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_SERVER_ID)));
                    arrayChoice.setOwnerID(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OWNER_ID)));
                    arrayChoice.setOrgID(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ORG_ID)));
                    arrayChoice.setGroupBITS(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_GROUP_BITS)));
                    arrayChoice.setPermsBITS(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PERMS_BITS)));
                    arrayChoice.setStatusBITS(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_STATUS_BITS)));
                    arrayChoice.setName(
                            cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_NAME)));
                    arrayChoice.setDescription(
                            cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_DESCRIPTION)));
                    arrayChoice.setCreatedDate(Timestamp.valueOf(
                            cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_CREATED_DATE))));
                    arrayChoice.setModifiedDate(Timestamp.valueOf(
                            cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_MODIFIED_DATE))));
                    arrayChoice.setSyncedDate(Timestamp.valueOf(
                            cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_SYNCED_DATE))));


                    arrayChoiceModelSet.add(arrayChoice);

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to read a ARRAY CHOICE entity: " + e.getMessage());
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        // close the database connection
        db.close();

        return arrayChoiceModelSet;
    }

    public Set<Pair<cRowOptionModel, cColOptionModel>> getMatrixChoiceModelSetByID(
            long questionID, long userID, int primaryRoleBITS, int secondaryRoleBITS,
            int statusBITS) {

        Set<Pair<cRowOptionModel, cColOptionModel>> matrixChoiceModelSet = new HashSet<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String selectQuery = "SELECT " +
                "RO." + cSQLDBHelper.KEY_ID + " AS _id_row, " +
                "RO." + cSQLDBHelper.KEY_SERVER_ID + " AS _id_row_server, " +
                "RO." + cSQLDBHelper.KEY_OWNER_ID + " AS _id_row_owner, " +
                "RO." + cSQLDBHelper.KEY_ORG_ID + " AS _id_row_org, " +
                "RO." + cSQLDBHelper.KEY_GROUP_BITS + " AS row_group_bits, " +
                "RO." + cSQLDBHelper.KEY_PERMS_BITS + " AS row_perms_bits, " +
                "RO." + cSQLDBHelper.KEY_STATUS_BITS + " AS row_status_bits, " +
                "RO." + cSQLDBHelper.KEY_NAME + " AS row_name, " +
                "RO." + cSQLDBHelper.KEY_DESCRIPTION + " AS row_description, " +
                "RO." + cSQLDBHelper.KEY_CREATED_DATE + " AS row_created_date, " +
                "RO." + cSQLDBHelper.KEY_MODIFIED_DATE + " AS row_modified_date, " +
                "RO." + cSQLDBHelper.KEY_SYNCED_DATE + " AS row_synced_date, " +
                "CO." + cSQLDBHelper.KEY_ID + " AS _id_col, " +
                "CO." + cSQLDBHelper.KEY_SERVER_ID + " AS _id_col_server, " +
                "CO." + cSQLDBHelper.KEY_OWNER_ID + " AS _id_col_owner, " +
                "CO." + cSQLDBHelper.KEY_ORG_ID + " AS _id_col_org, " +
                "CO." + cSQLDBHelper.KEY_GROUP_BITS + " AS col_group_bits, " +
                "CO." + cSQLDBHelper.KEY_PERMS_BITS + " AS col_perms_bits, " +
                "CO." + cSQLDBHelper.KEY_STATUS_BITS + " AS col_status_bits, " +
                "CO." + cSQLDBHelper.KEY_NAME + " AS col_name, " +
                "CO." + cSQLDBHelper.KEY_DESCRIPTION + " AS col_description, " +
                "CO." + cSQLDBHelper.KEY_CREATED_DATE + " AS col_created_date, " +
                "CO." + cSQLDBHelper.KEY_MODIFIED_DATE + " AS col_modified_date, " +
                "CO." + cSQLDBHelper.KEY_SYNCED_DATE + " AS col_synced_date, " +
                "MC." + cSQLDBHelper.KEY_OWNER_ID + ", " +
                "MC." + cSQLDBHelper.KEY_ORG_ID + ", " +
                "MC." + cSQLDBHelper.KEY_GROUP_BITS + ", " +
                "MC." + cSQLDBHelper.KEY_PERMS_BITS + ", " +
                "MC." + cSQLDBHelper.KEY_STATUS_BITS +
                " FROM " +
                cSQLDBHelper.TABLE_tblMATRIXCHOICE +" MC, " +
                cSQLDBHelper.TABLE_tblROWOPTION +" RO, " +
                cSQLDBHelper.TABLE_tblCOLOPTION + " CO " +
                " WHERE (RO." + cSQLDBHelper.KEY_ID + " = MC." + cSQLDBHelper.KEY_ROW_OPTION_FK_ID +
                " AND CO." + cSQLDBHelper.KEY_ID + " = MC." + cSQLDBHelper.KEY_COL_OPTION_FK_ID +
                " AND (MC." + cSQLDBHelper.KEY_ID + " IN " +
                " (SELECT MC."+cSQLDBHelper.KEY_ID + " FROM " +
                cSQLDBHelper.TABLE_tblQUESTION +" Q, " +
                cSQLDBHelper.TABLE_tblMATRIXTYPE +" T, " +
                cSQLDBHelper.TABLE_tblMATRIXCHOICE +" C, " +
                cSQLDBHelper.TABLE_tblMATRIXCHOICESET + " MS " +
                " WHERE (Q." + cSQLDBHelper.KEY_ID + " = MS." + cSQLDBHelper.KEY_QUESTION_FK_ID +
                " AND T." + cSQLDBHelper.KEY_QUESTION_TYPE_FK_ID + " = MS." + cSQLDBHelper.KEY_MATRIX_TYPE_FK_ID +
                " AND C." + cSQLDBHelper.KEY_ID + " = MS." + cSQLDBHelper.KEY_ARRAY_CHOICE_FK_ID +
                " AND Q." + cSQLDBHelper.KEY_ID + " = ? ))) " +
                " AND " +
                /* read access permissions */
                /* organization creator is always allowed to do everything (i.e., where: userID = orgID)*/
                " ((MC." + cSQLDBHelper.KEY_ORG_ID + " = ?) " +
                /* owner permission */
                " OR ((((MC." + cSQLDBHelper.KEY_OWNER_ID + " = ?) " +
                " AND ((MC." + cSQLDBHelper.KEY_PERMS_BITS + " & " + cBitwise.OWNER_READ + ") != 0))" +
                /* group (owner/primary organization) permission */
                " OR (((MC." + cSQLDBHelper.KEY_GROUP_BITS + " & ?) != 0) " +
                " AND ((MC." + cSQLDBHelper.KEY_PERMS_BITS + " & " + cBitwise.GROUP_READ + ") != 0))" +
                /* other (secondary organizations) permission */
                " OR (((MC." + cSQLDBHelper.KEY_GROUP_BITS + " & ?) != 0) " +
                " AND ((MC." + cSQLDBHelper.KEY_PERMS_BITS + " & " + cBitwise.OTHER_READ + ") != 0)))" +
                /* owner, group and other permissions allowed when the statuses hold */
                " AND ((MC." + cSQLDBHelper.KEY_STATUS_BITS + " = 0) " +
                " OR ((MC." + cSQLDBHelper.KEY_STATUS_BITS + " & ?) != 0)))))";

        Cursor cursor = db.rawQuery(selectQuery, new String[]{
                String.valueOf(questionID),
                String.valueOf(userID),           /* access due to organization creator */
                String.valueOf(userID),           /* access due to record owner */
                String.valueOf(primaryRoleBITS),  /* access due to membership in primary role */
                String.valueOf(secondaryRoleBITS),/* access due to membership in secondary role */
                String.valueOf(statusBITS)});     /* access due to assigned statuses */

        try {
            if (cursor.moveToFirst()) {
                do {
                    cRowOptionModel rowOption = new cRowOptionModel();
                    cColOptionModel colOption = new cColOptionModel();

                    /* row data */
                    rowOption.setRowOptionID(
                            cursor.getInt(cursor.getColumnIndex("_id_row")));
                    rowOption.setServerID(
                            cursor.getInt(cursor.getColumnIndex("_id_row_server")));
                    rowOption.setOwnerID(
                            cursor.getInt(cursor.getColumnIndex("_id_row_owner")));
                    rowOption.setOrgID(
                            cursor.getInt(cursor.getColumnIndex("_id_row_org")));
                    rowOption.setGroupBITS(
                            cursor.getInt(cursor.getColumnIndex("row_group_bits")));
                    rowOption.setPermsBITS(
                            cursor.getInt(cursor.getColumnIndex("row_perms_bits")));
                    rowOption.setStatusBITS(
                            cursor.getInt(cursor.getColumnIndex("row_status_bits")));
                    rowOption.setName(
                            cursor.getString(cursor.getColumnIndex("row_name")));
                    rowOption.setDescription(
                            cursor.getString(cursor.getColumnIndex("row_description")));
                    rowOption.setCreatedDate(Timestamp.valueOf(
                            cursor.getString(cursor.getColumnIndex("row_created_date"))));
                    rowOption.setModifiedDate(Timestamp.valueOf(
                            cursor.getString(cursor.getColumnIndex("row_modified_date"))));
                    rowOption.setSyncedDate(Timestamp.valueOf(
                            cursor.getString(cursor.getColumnIndex("row_synced_date"))));

                    /* col data */
                    colOption.setColOptionID(
                            cursor.getInt(cursor.getColumnIndex("_id_col")));
                    colOption.setServerID(
                            cursor.getInt(cursor.getColumnIndex("_id_col_server")));
                    colOption.setOwnerID(
                            cursor.getInt(cursor.getColumnIndex("_id_col_owner")));
                    colOption.setOrgID(
                            cursor.getInt(cursor.getColumnIndex("_id_col_org")));
                    colOption.setGroupBITS(
                            cursor.getInt(cursor.getColumnIndex("col_group_bits")));
                    colOption.setPermsBITS(
                            cursor.getInt(cursor.getColumnIndex("col_perms_bits")));
                    colOption.setStatusBITS(
                            cursor.getInt(cursor.getColumnIndex("col_status_bits")));
                    colOption.setName(
                            cursor.getString(cursor.getColumnIndex("col_name")));
                    colOption.setDescription(
                            cursor.getString(cursor.getColumnIndex("col_description")));
                    colOption.setCreatedDate(Timestamp.valueOf(
                            cursor.getString(cursor.getColumnIndex("col_created_date"))));
                    colOption.setModifiedDate(Timestamp.valueOf(
                            cursor.getString(cursor.getColumnIndex("col_modified_date"))));
                    colOption.setSyncedDate(Timestamp.valueOf(
                            cursor.getString(cursor.getColumnIndex("col_synced_date"))));

                    matrixChoiceModelSet.add(new Pair<>(rowOption, colOption));

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to read a MATRIX CHOICE entity: " + e.getMessage());
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        // close the database connection
        db.close();

        return matrixChoiceModelSet;
    }
}
