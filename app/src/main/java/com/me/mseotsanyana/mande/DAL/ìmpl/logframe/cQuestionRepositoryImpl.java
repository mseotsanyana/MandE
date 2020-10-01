package com.me.mseotsanyana.mande.DAL.ìmpl.logframe;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.me.mseotsanyana.mande.BLL.repository.logframe.iQuestionRepository;
import com.me.mseotsanyana.mande.DAL.model.logframe.cArrayQuestionModel;
import com.me.mseotsanyana.mande.DAL.model.logframe.cMatrixQuestionModel;
import com.me.mseotsanyana.mande.DAL.model.logframe.cPrimitiveQuestionModel;
import com.me.mseotsanyana.mande.DAL.model.logframe.cQuestionModel;
import com.me.mseotsanyana.mande.DAL.storage.database.cSQLDBHelper;
import com.me.mseotsanyana.mande.DAL.storage.preference.cBitwise;
import com.me.mseotsanyana.mande.DAL.ìmpl.evaluator.cArrayChoiceRepositoryImpl;
import com.me.mseotsanyana.mande.DAL.ìmpl.evaluator.cArrayTypeRepositoryImpl;
import com.me.mseotsanyana.mande.DAL.ìmpl.evaluator.cMatrixChoiceRepositoryImpl;
import com.me.mseotsanyana.mande.DAL.ìmpl.evaluator.cMatrixTypeRepositoryImpl;
import com.me.mseotsanyana.mande.DAL.ìmpl.evaluator.cPrimitiveTypeRepositoryImpl;
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
    private Context context;

    public cQuestionRepositoryImpl(Context context) {
        this.dbHelper = new cSQLDBHelper(context);
        this.context = context;
    }

    /**
     * return question set
     *
     * @param questionID question identification
     * @param userID user identification
     * @param primaryRoleBITS primary role bits
     * @param secondaryRoleBITS secondary role bits
     * @param statusBITS status bits
     * @return question set
     */
    @Override
    public Set<cQuestionModel> getQuestionModelSetByID(long questionID, long userID,
                                                       int primaryRoleBITS, int secondaryRoleBITS,
                                                       int statusBITS) {

        Set<cQuestionModel> questionModelSet = new HashSet<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + cSQLDBHelper.TABLE_tblQUESTION +
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
                String.valueOf(questionID),
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
                    question.setLabel(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_LABEL)));
                    question.setQuestion(
                            cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_QUESTION)));
                    question.setDefaultChart(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_DEFAULT_CHART)));
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
                    cLogFrameRepositoryImpl logFrameRepository = new cLogFrameRepositoryImpl(context);
                    question.setLogFrameModel(logFrameRepository.getLogFrameModelByID(
                            question.getLogFrameID(), userID, primaryRoleBITS, secondaryRoleBITS,
                            statusBITS));

                    /* question grouping object */
                    cQuestionGroupingRepositoryImpl questionGroupingRepository;
                    questionGroupingRepository = new cQuestionGroupingRepositoryImpl(context);

                    question.setQuestionGroupingModel(
                            questionGroupingRepository.getQuestionGroupingModelByID(
                                    question.getQuestionGroupID(), userID, primaryRoleBITS,
                                    secondaryRoleBITS, statusBITS));

                    /* instantiate question type objects. only one of them should be non-null */
                    cPrimitiveTypeRepositoryImpl primitiveTypeRepository;
                    cArrayTypeRepositoryImpl arrayTypeRepository;
                    cMatrixTypeRepositoryImpl matrixTypeRepository;
                    primitiveTypeRepository = new cPrimitiveTypeRepositoryImpl(context);
                    arrayTypeRepository = new cArrayTypeRepositoryImpl(context);
                    matrixTypeRepository = new cMatrixTypeRepositoryImpl(context);

                    cPrimitiveQuestionModel primitive = primitiveTypeRepository.getPrimitiveTypeModelSet(
                            question.getQuestionTypeID(), userID, primaryRoleBITS,
                            secondaryRoleBITS, statusBITS);
                    cArrayQuestionModel array = arrayTypeRepository.getArrayTypeModelSet(
                            question.getQuestionTypeID(), userID, primaryRoleBITS,
                            secondaryRoleBITS, statusBITS);
                    cMatrixQuestionModel matrix = matrixTypeRepository.getMatrixTypeModelSet(
                            question.getQuestionTypeID(), userID, primaryRoleBITS,
                            secondaryRoleBITS, statusBITS);

                    /* up casting to the question type*/
                    if (primitive != null){
                        //question.setQuestionTypeModel(primitive);
                    }
                    else if (array != null){
                        //question.setQuestionTypeModel(array);
                    }
                    else if (matrix != null){
                        //question.setQuestionTypeModel(matrix);
                    }

                    /* set */
                    /* array response choices
                    cArrayChoiceRepositoryImpl arrayChoiceRepository;
                    arrayChoiceRepository = new cArrayChoiceRepositoryImpl(context);
                    question.setArrayChoiceModelSet(
                            arrayChoiceRepository.getArrayChoiceModelSetByID(
                                    question.getQuestionID(), userID, primaryRoleBITS,
                                    secondaryRoleBITS, statusBITS));*/

                    /* matrix response choices
                    cMatrixChoiceRepositoryImpl matrixChoiceRepository;
                    matrixChoiceRepository = new cMatrixChoiceRepositoryImpl(context);
                    question.setMatrixChoiceModelSet(
                            matrixChoiceRepository.getMatrixChoiceModelSetByID(
                            question.getQuestionID(), userID, primaryRoleBITS,
                            secondaryRoleBITS, statusBITS));*/

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
}
