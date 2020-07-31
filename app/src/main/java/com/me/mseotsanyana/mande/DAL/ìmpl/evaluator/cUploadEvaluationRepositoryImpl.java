package com.me.mseotsanyana.mande.DAL.ìmpl.evaluator;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.util.Pair;

import com.google.gson.Gson;
import com.me.mseotsanyana.mande.BLL.repository.evaluator.iUploadEvaluationRepository;
import com.me.mseotsanyana.mande.DAL.model.evaluator.cArrayChoiceModel;
import com.me.mseotsanyana.mande.DAL.model.evaluator.cColOptionModel;
import com.me.mseotsanyana.mande.DAL.model.evaluator.cEvaluationResponseModel;
import com.me.mseotsanyana.mande.DAL.model.evaluator.cEvaluationTypeModel;
import com.me.mseotsanyana.mande.DAL.model.evaluator.cMatrixChoiceModel;
import com.me.mseotsanyana.mande.DAL.model.evaluator.cEvaluationModel;
import com.me.mseotsanyana.mande.DAL.model.evaluator.cRowOptionModel;
import com.me.mseotsanyana.mande.DAL.storage.database.cSQLDBHelper;
import com.me.mseotsanyana.mande.DAL.storage.excel.cExcelHelper;
import com.me.mseotsanyana.mande.UTIL.cConstant;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class cUploadEvaluationRepositoryImpl implements iUploadEvaluationRepository {
    private static SimpleDateFormat sdf = cConstant.FORMAT_DATE;
    private static String TAG = cUploadEvaluationRepositoryImpl.class.getSimpleName();

    /* an object of the database helper */
    private cSQLDBHelper dbHelper;
    /* an object of the excel helper */
    private cExcelHelper excelHelper;

    public cUploadEvaluationRepositoryImpl(Context context) {
        dbHelper = new cSQLDBHelper(context);
        excelHelper = new cExcelHelper(context);
    }

    /*################################## ARRAY CHOICE FUNCTIONS ##################################*/

    /**
     * This function extracts array choice data from excel and adds it to the database.
     *
     * @return
     */
    @Override
    public boolean addArrayChoiceFromExcel() {
        Workbook workbook = excelHelper.getWorkbookEVALUATION();
        Sheet ACSheet = workbook.getSheet(cExcelHelper.SHEET_tblARRAYCHOICE);

        if (ACSheet == null) {
            return false;
        }

        for (Row cRow : ACSheet) {
            //just skip the row if row number is 0
            if (cRow.getRowNum() == 0) {
                continue;
            }

            cArrayChoiceModel arrayChoiceModel = new cArrayChoiceModel();

            arrayChoiceModel.setArrayChoiceID(
                    (int) cRow.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            arrayChoiceModel.setName(
                    cRow.getCell(1, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
            arrayChoiceModel.setDescription(
                    cRow.getCell(2, Row.CREATE_NULL_AS_BLANK).getStringCellValue());


            /* get array choices grouped by question and array type */
            Map<Long, Set<Pair<Long, Long>>> qtcMap = new HashMap<>();
            Set<Pair<Long, Long>> tcSet = new HashSet<>();
            long questionID, typeID, choiceID;

            Sheet qtcSheet = workbook.getSheet(cExcelHelper.SHEET_tblARRAYCHOICESET);
            for (Row rowQTC : qtcSheet) {
                //just skip the row if row number is 0
                if (rowQTC.getRowNum() == 0) {
                    continue;
                }

                choiceID = (int) rowQTC.getCell(2, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                if (arrayChoiceModel.getArrayChoiceID() == choiceID) {
                    questionID = (int) rowQTC.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                    typeID = (int) rowQTC.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                    tcSet.add(new Pair<>(questionID, typeID));
                }
            }

            qtcMap.put(arrayChoiceModel.getArrayChoiceID(), tcSet);

            if (!addArrayChoice(arrayChoiceModel, qtcMap)) {
                return false;
            }

            //Gson gson = new Gson();
            //Log.d(TAG, gson.toJson(arrayChoiceModel));
        }

        return true;
    }

    /**
     * This function adds array choice data to the database.
     *
     * @param arrayChoiceModel array choice model
     * @param qtcMap question
     * @return bool
     */
    public boolean addArrayChoice(cArrayChoiceModel arrayChoiceModel,
                                  Map<Long, Set<Pair<Long, Long>>> qtcMap) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_ID, arrayChoiceModel.getArrayChoiceID());
        cv.put(cSQLDBHelper.KEY_NAME, arrayChoiceModel.getName());
        cv.put(cSQLDBHelper.KEY_DESCRIPTION, arrayChoiceModel.getDescription());

        // insert project details
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblARRAYCHOICE, null, cv) < 0) {
                return false;
            }

            // add question, type and choice tuple
            for (Map.Entry<Long, Set<Pair<Long, Long>>> qtcEntry : qtcMap.entrySet()) {
                long choiceID = qtcEntry.getKey();
                for (Pair<Long, Long> qcPair : qtcEntry.getValue()) {
                    long questionID = qcPair.first;
                    long typeID = qcPair.second;

                    if (!addQuestionArrayTypeChoice(questionID, typeID, choiceID))
                        return false;
                }
            }

        } catch (Exception e) {
            Log.d(TAG, "Exception in importing ARRAY CHOICE from Excel: " + e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }

    /**
     * @param questionID question identification
     * @param typeID type identification
     * @param choiceID choice identification
     * @return bool
     */
    public boolean addQuestionArrayTypeChoice(long questionID, long typeID, long choiceID) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(cSQLDBHelper.KEY_QUESTION_FK_ID, questionID);
        cv.put(cSQLDBHelper.KEY_ARRAY_TYPE_FK_ID, typeID);
        cv.put(cSQLDBHelper.KEY_ARRAY_CHOICE_FK_ID, choiceID);

        try {
            if (db.insert(cSQLDBHelper.TABLE_tblARRAYCHOICESET, null, cv) < 0) {
                return false;
            }
        } catch (Exception e) {
            Log.d(TAG, "Exception in importing ARRAY TYPE CHOICE from Excel: " + e.getMessage());
        }

        return true;
    }

    public boolean deleteArrayChoices() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblARRAYCHOICE, null, null);

        // close the database connection
        db.close();

        return result > -1;
    }

    public boolean deleteArrayChoiceSets() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblARRAYCHOICESET, null, null);

        // close the database connection
        db.close();

        return result > -1;
    }

    /*################################### ROW OPTION FUNCTIONS ###################################*/

    /**
     * This function extracts row option data from excel and adds it to the database.
     *
     * @return bool
     */
    @Override
    public boolean addRowOptionFromExcel() {
        Workbook workbook = excelHelper.getWorkbookEVALUATION();
        Sheet ROSheet = workbook.getSheet(cExcelHelper.SHEET_tblROWOPTION);

        if (ROSheet == null) {
            return false;
        }

        for (Row cRow : ROSheet) {
            //just skip the row if row number is 0
            if (cRow.getRowNum() == 0) {
                continue;
            }

            cRowOptionModel rowOptionModel = new cRowOptionModel();

            rowOptionModel.setRowOptionID((int)
                    cRow.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            rowOptionModel.setName(
                    cRow.getCell(1, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
            rowOptionModel.setDescription(
                    cRow.getCell(2, Row.CREATE_NULL_AS_BLANK).getStringCellValue());

            if (!addRowOption(rowOptionModel)) {
                return false;
            }
        }

        return true;
    }

    /**
     * This function adds row option data to the database.
     *
     * @param rowOptionModel row option
     * @return bool
     */
    public boolean addRowOption(cRowOptionModel rowOptionModel) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_ID, rowOptionModel.getRowOptionID());
        cv.put(cSQLDBHelper.KEY_NAME, rowOptionModel.getName());
        cv.put(cSQLDBHelper.KEY_DESCRIPTION, rowOptionModel.getDescription());

        // insert project details
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblROWOPTION, null, cv) < 0) {
                return false;
            }
        } catch (Exception e) {
            Log.d(TAG, "Exception in importing ROW OPTION from Excel: " + e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }

    public boolean deleteRowOptions() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblROWOPTION, null, null);

        // close the database connection
        db.close();

        return result > -1;
    }

    /*################################### COL OPTION FUNCTIONS ###################################*/

    /**
     * This function extracts col option data from excel and adds it to the database.
     *
     * @return bool
     */

    @Override
    public boolean addColOptionFromExcel() {
        Workbook workbook = excelHelper.getWorkbookEVALUATION();
        Sheet COSheet = workbook.getSheet(cExcelHelper.SHEET_tblCOLOPTION);

        if (COSheet == null) {
            return false;
        }

        for (Row cRow : COSheet) {
            //just skip the row if row number is 0
            if (cRow.getRowNum() == 0) {
                continue;
            }

            cColOptionModel colOptionModel = new cColOptionModel();

            colOptionModel.setColOptionID((int)
                    cRow.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            colOptionModel.setName(
                    cRow.getCell(1, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
            colOptionModel.setDescription(
                    cRow.getCell(2, Row.CREATE_NULL_AS_BLANK).getStringCellValue());

            if (!addColOption(colOptionModel)) {
                return false;
            }
        }

        return true;
    }

    /**
     * @param colOptionModel column option
     * @return bool
     */
    public boolean addColOption(cColOptionModel colOptionModel) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_ID, colOptionModel.getColOptionID());
        cv.put(cSQLDBHelper.KEY_NAME, colOptionModel.getName());
        cv.put(cSQLDBHelper.KEY_DESCRIPTION, colOptionModel.getDescription());

        // insert project details
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblCOLOPTION, null, cv) < 0) {
                return false;
            }
        } catch (Exception e) {
            Log.d(TAG, "Exception in importing COL OPTION from Excel: " + e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }

    public boolean deleteColOptions() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblCOLOPTION, null, null);

        // close the database connection
        db.close();

        return result > -1;
    }

    /*################################# MATRIX CHOICE FUNCTIONS ##################################*/

    /**
     * This function extracts matrix choice data from excel and adds it to the database.
     *
     * @return bool
     */
    @Override
    public boolean addMatrixChoiceFromExcel() {
        Workbook workbook = excelHelper.getWorkbookEVALUATION();
        Sheet MCSheet = workbook.getSheet(cExcelHelper.SHEET_tblMATRIXCHOICE);

        if (MCSheet == null) {
            return false;
        }

        for (Row cRow : MCSheet) {
            //just skip the row if row number is 0
            if (cRow.getRowNum() == 0) {
                continue;
            }

            cMatrixChoiceModel matrixChoiceModel = new cMatrixChoiceModel();

            matrixChoiceModel.setMatrixChoiceID(
                    (int) cRow.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            matrixChoiceModel.setRowID(
                    (int) cRow.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            matrixChoiceModel.setColID(
                    (int) cRow.getCell(2, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());

            /* get array choices grouped by question and matrix type */
            Map<Long, Set<Pair<Long, Long>>> qtcMap = new HashMap<>();
            Set<Pair<Long, Long>> tcSet = new HashSet<>();
            long questionID, typeID, choiceID;

            Sheet qtcSheet = workbook.getSheet(cExcelHelper.SHEET_tblMATRIXCHOICESET);
            for (Row rowQTC : qtcSheet) {
                //just skip the row if row number is 0
                if (rowQTC.getRowNum() == 0) {
                    continue;
                }

                choiceID = (int) rowQTC.getCell(2, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                if (matrixChoiceModel.getMatrixChoiceID() == choiceID) {
                    questionID = (int) rowQTC.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                    typeID = (int) rowQTC.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                    tcSet.add(new Pair<>(questionID, typeID));
                }
            }

            qtcMap.put(matrixChoiceModel.getMatrixChoiceID(), tcSet);

            if (!addMatrixChoice(matrixChoiceModel, qtcMap)) {
                return false;
            }

            //Gson gson = new Gson();
            //Log.d(TAG, gson.toJson(arrayChoiceModel));
        }

        return true;
    }

    /**
     * This function adds matrix choice data to the database.
     *
     * @param matrixChoiceModel matrix choice
     * @return bool
     */
    public boolean addMatrixChoice(cMatrixChoiceModel matrixChoiceModel,
                                   Map<Long, Set<Pair<Long, Long>>> qtcMap) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_ID, matrixChoiceModel.getMatrixChoiceID());
        cv.put(cSQLDBHelper.KEY_ROW_OPTION_FK_ID, matrixChoiceModel.getRowID());
        cv.put(cSQLDBHelper.KEY_COL_OPTION_FK_ID, matrixChoiceModel.getColID());

        // insert project details
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblMATRIXCHOICE, null, cv) < 0) {
                return false;
            }

            // add question, type and choice tuple
            for (Map.Entry<Long, Set<Pair<Long, Long>>> qtcEntry : qtcMap.entrySet()) {
                long choiceID = qtcEntry.getKey();
                for (Pair<Long, Long> qcPair : qtcEntry.getValue()) {
                    long questionID = qcPair.first;
                    long typeID = qcPair.second;

                    if (!addQuestionMatrixTypeChoice(questionID, typeID, choiceID))
                        return false;
                }
            }

        } catch (Exception e) {
            Log.d(TAG, "Exception in importing MATRIX CHOICE from Excel: " + e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }

    public boolean addQuestionMatrixTypeChoice(long questionID, long typeID, long choiceID) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(cSQLDBHelper.KEY_QUESTION_FK_ID, questionID);
        cv.put(cSQLDBHelper.KEY_MATRIX_TYPE_FK_ID, typeID);
        cv.put(cSQLDBHelper.KEY_MATRIX_CHOICE_FK_ID, choiceID);

        return db.insert(cSQLDBHelper.TABLE_tblMATRIXCHOICESET, null, cv) >= 0;
    }

    public boolean deleteMatrixChoices() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblMATRIXCHOICE, null, null);

        // close the database connection
        db.close();

        return result > -1;
    }

    public boolean deleteMatrixChoiceSets() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblMATRIXCHOICESET, null, null);

        // close the database connection
        db.close();

        return result > -1;
    }

    /* ################################ EVALUATION TYPE FUNCTIONS ################################*/

    /**
     * This function extracts evaluation data from excel and adds it to the database.
     *
     * @return bool
     */
    @Override
    public boolean addEvaluationTypeFromExcel() {
        Workbook workbook = excelHelper.getWorkbookEVALUATION();
        Sheet ETSheet = workbook.getSheet(cExcelHelper.SHEET_tblEVALUATIONTYPE);

        if (ETSheet == null) {
            return false;
        }

        for (Row cRow : ETSheet) {
            //just skip the row if row number is 0
            if (cRow.getRowNum() == 0) {
                continue;
            }

            cEvaluationTypeModel evaluationTypeModel = new cEvaluationTypeModel();

            evaluationTypeModel.setEvaluationTypeID((int)
                    cRow.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            evaluationTypeModel.setName(
                    cRow.getCell(1, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
            evaluationTypeModel.setDescription(
                    cRow.getCell(2, Row.CREATE_NULL_AS_BLANK).getStringCellValue());

            if (!addEvaluationType(evaluationTypeModel)) {
                return false;
            }
        }

        return true;
    }

    /**
     * This function adds evaluation type data to the database.
     *
     * @param evaluationTypeModel evaluation type
     * @return bool
     */
    public boolean addEvaluationType(cEvaluationTypeModel evaluationTypeModel) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_ID, evaluationTypeModel.getEvaluationTypeID());
        cv.put(cSQLDBHelper.KEY_NAME, evaluationTypeModel.getName());
        cv.put(cSQLDBHelper.KEY_DESCRIPTION, evaluationTypeModel.getDescription());

        // insert evaluation type details
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblEVALUATIONTYPE, null, cv) < 0) {
                return false;
            }
        } catch (Exception e) {
            Log.d(TAG, "Exception in importing EVALUATION TYPE from Excel: " + e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }

    public boolean deleteEvaluationTypes() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblEVALUATIONTYPE, null, null);

        // close the database connection
        db.close();

        return result > -1;
    }

    /* ################################# EVALUATION FUNCTIONS #################################*/

    @Override
    public boolean addEvaluationFromExcel() {
        Workbook workbook = excelHelper.getWorkbookEVALUATION();
        Sheet QSheet = workbook.getSheet(cExcelHelper.SHEET_tblEVALUATION);

        if (QSheet == null) {
            return false;
        }

        for (Row cRow : QSheet) {
            //just skip the row if row number is 0
            if (cRow.getRowNum() == 0) {
                continue;
            }

            cEvaluationModel evaluationModel = new cEvaluationModel();

            evaluationModel.setEvaluationID((int)
                    cRow.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            evaluationModel.setEvaluationTypeID((int)
                    cRow.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            evaluationModel.setName(
                    cRow.getCell(2, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
            evaluationModel.setDescription(
                    cRow.getCell(3, Row.CREATE_NULL_AS_BLANK).getStringCellValue());

            /* get questions of the questionnaire*/
            Set<Pair<Long, Long>> questionSet = new HashSet<>();
            long eqID = -1, evaluationID = -1, questionID = -1;
            Sheet EQSheet = workbook.getSheet(cExcelHelper.SHEET_tblEVALUATION_QUESTION);
            for (Row rowEQ : EQSheet) {
                //just skip the row if row number is 0
                if (rowEQ.getRowNum() == 0) {
                    continue;
                }

                evaluationID = (int) rowEQ.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                if (evaluationModel.getEvaluationID() == evaluationID) {
                    eqID = (int) rowEQ.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                    questionID = (int) rowEQ.getCell(2, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                    questionSet.add(new Pair<>(eqID, questionID));
                }
            }

            /* get users of the evaluation */
            Set<Pair<Long, Long>> userSet = new HashSet<>();
            long ueID = -1, userID = -1;
            evaluationID = -1;
            Sheet UESheet = workbook.getSheet(cExcelHelper.SHEET_tblUSER_EVALUATION);
            for (Row rowUE : UESheet) {
                //just skip the row if row number is 0
                if (rowUE.getRowNum() == 0) {
                    continue;
                }

                evaluationID = (int) rowUE.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                if (evaluationModel.getEvaluationID() == evaluationID) {
                    ueID = (int) rowUE.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                    userID = (int) rowUE.getCell(2, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                    userSet.add(new Pair<>(ueID, userID));
                }
            }

            if (!addEvaluation(evaluationModel, questionSet, userSet)) {
                return false;
            }

            /* conditional order of the questionnaire */
            int coID = -1, questionOrderID = -1, rQuestionID = -1, prQuestionID = -1, nrQuestionID = -1;
            Sheet COSheet = workbook.getSheet(cExcelHelper.SHEET_tblCONDITIONAL_ORDER);
            for (Row rowCO : COSheet) {
                //just skip the row if row number is 0
                if (rowCO.getRowNum() == 0) {
                    continue;
                }

                for (Pair question : questionSet) {
                    questionOrderID = (int) rowCO.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                    if ((long) question.second == questionOrderID) {
                        coID = (int) rowCO.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                        rQuestionID = (int) rowCO.getCell(2, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                        prQuestionID = (int) rowCO.getCell(3, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                        nrQuestionID = (int) rowCO.getCell(4, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();


                        if (!addConditionalOrder(coID, questionOrderID, rQuestionID, prQuestionID, nrQuestionID)) {
                            return false;
                        }
                    }
                }
            }


            Gson gson = new Gson();
            Log.d(TAG, gson.toJson(evaluationModel));
            Log.d(TAG, gson.toJson(userSet));
        }

        return true;
    }

    public boolean addEvaluation(cEvaluationModel evaluationModel,
                                    Set<Pair<Long, Long>> questionSet,
                                    Set<Pair<Long, Long>> userSet) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_ID, evaluationModel.getEvaluationID());
        cv.put(cSQLDBHelper.KEY_EVALUATION_TYPE_FK_ID, evaluationModel.getEvaluationTypeID());
        cv.put(cSQLDBHelper.KEY_NAME, evaluationModel.getName());
        cv.put(cSQLDBHelper.KEY_DESCRIPTION, evaluationModel.getDescription());

        // insert evaluation type details
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblEVALUATION, null, cv) < 0) {
                return false;
            }

            // add questions
            for (Pair<Long, Long> pair : questionSet) {
                if (!addEvaluationQuestion(pair.first, evaluationModel.getEvaluationID(), pair.second))
                    return false;
            }

            // add users
            for (Pair<Long, Long> pair : userSet) {
                if (!addUserEvaluation(pair.first, evaluationModel.getEvaluationID(), pair.second))
                    return false;
            }


        } catch (Exception e) {
            Log.d(TAG, "Exception in importing EVALUATION from Excel: " + e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }

    public boolean addEvaluationQuestion(long qqID, long evaluationID, long questionID) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_ID, qqID);
        cv.put(cSQLDBHelper.KEY_EVALUATION_FK_ID, evaluationID);
        cv.put(cSQLDBHelper.KEY_QUESTION_FK_ID, questionID);

        // insert evaluation type details
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblQUESTION_EVALUATION, null, cv) < 0) {
                return false;
            }
        } catch (Exception e) {
            Log.d(TAG, "Exception in importing EVALUATION QUESTIONS from Excel: " + e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }

    public boolean addConditionalOrder(long coID, long questionOrderID,
                                       long rQuestionID, long prQuestionID, long nrQuestionID) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_ID, coID);
        cv.put(cSQLDBHelper.KEY_QUESTION_ORDER_FK_ID, questionOrderID);
        cv.put(cSQLDBHelper.KEY_QUESTION_RESPONSE_FK_ID, rQuestionID);
        cv.put(cSQLDBHelper.KEY_POS_RES_ORDER_FK_ID, prQuestionID);
        cv.put(cSQLDBHelper.KEY_NEG_RES_ORDER_FK_ID, nrQuestionID);

        // insert evaluation type details
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblCONDITIONAL_ORDER, null, cv) < 0) {
                return false;
            }
        } catch (Exception e) {
            Log.d(TAG, "Exception in importing CONDITIONAL ORDER from Excel: " + e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }


    public boolean addUserEvaluation(long quID, long evaluationID, long userID) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_ID, quID);
        cv.put(cSQLDBHelper.KEY_EVALUATION_FK_ID, evaluationID);
        cv.put(cSQLDBHelper.KEY_USER_FK_ID, userID);

        // insert evaluation type details
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblUSER_EVALUATION, null, cv) < 0) {
                return false;
            }
        } catch (Exception e) {
            Log.d(TAG, "Exception in importing USER EVALUATION from Excel: " + e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }

    public boolean deleteEvaluations() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblEVALUATION, null, null);

        // close the database connection
        db.close();

        return result > -1;
    }

    public boolean deleteEvaluationQuestions() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblQUESTION_EVALUATION, null, null);

        // close the database connection
        db.close();

        return result > -1;
    }

    public boolean deleteConditionalOrders() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblCONDITIONAL_ORDER, null, null);

        // close the database connection
        db.close();

        return result > -1;
    }

    public boolean deleteUserEvaluations() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblUSER_EVALUATION, null, null);

        // close the database connection
        db.close();

        return result > -1;
    }

    /* ############################### EVALUATION RESPONSE FUNCTIONS #############################*/

    @Override
    public boolean addEvaluationResponseFromExcel() {
        Workbook workbook = excelHelper.getWorkbookEVALUATION();
        Sheet ERSheet = workbook.getSheet(cExcelHelper.SHEET_tblEVALUATION_RESPONSE);

        if (ERSheet == null) {
            return false;
        }

        for (Row cRow : ERSheet) {
            //just skip the row if row number is 0
            if (cRow.getRowNum() == 0) {
                continue;
            }

            cEvaluationResponseModel eresponseModel = new cEvaluationResponseModel();

            eresponseModel.setEvaluationResponseID((int)
                    cRow.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            eresponseModel.setUserEvaluationID((int)
                    cRow.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());

            long responseID, numQuestionID = -1, txtQuestionID = -1, dateQuestionID = -1,
                    arrQuestionID = -1, matQuestionID = -1, numericResponse = -1;
            long textResponseID;
            String textResponse = null;
            long dateResponseID;
            Date dateResponse = null;
            long arrayResponseID, arrayResponse = -1;
            long matrixResponseID, matrixResponse = -1, rowResponse = 0, colResponse = 0;

            /* get numeric responses */

            Sheet NRSheet = workbook.getSheet(cExcelHelper.SHEET_tblNUMERICRESPONSE);
            for (Row rowNR : NRSheet) {
                //just skip the row if row number is 0
                if (rowNR.getRowNum() == 0) {
                    continue;
                }

                responseID = (int) rowNR.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                if (eresponseModel.getEvaluationResponseID() == responseID) {
                    numQuestionID = (int) rowNR.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                    numericResponse = (int) rowNR.getCell(2, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                }
            }

            /* get text responses */

            Sheet TRSheet = workbook.getSheet(cExcelHelper.SHEET_tblTEXTRESPONSE);
            for (Row rowTR : TRSheet) {
                //just skip the row if row number is 0
                if (rowTR.getRowNum() == 0) {
                    continue;
                }

                textResponseID = (int) rowTR.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                if (eresponseModel.getEvaluationResponseID() == textResponseID) {
                    txtQuestionID = (int) rowTR.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                    textResponse = rowTR.getCell(2, Row.CREATE_NULL_AS_BLANK).getStringCellValue();
                }
            }

            /* get text responses */

            Sheet DRSheet = workbook.getSheet(cExcelHelper.SHEET_tblDATERESPONSE);
            for (Row rowDR : DRSheet) {
                //just skip the row if row number is 0
                if (rowDR.getRowNum() == 0) {
                    continue;
                }

                dateResponseID = (int) rowDR.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                if (eresponseModel.getEvaluationResponseID() == dateResponseID) {
                    dateQuestionID = (int) rowDR.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                    dateResponse = rowDR.getCell(2, Row.CREATE_NULL_AS_BLANK).getDateCellValue();
                }
            }

            /* get array responses */

            Sheet ARSheet = workbook.getSheet(cExcelHelper.SHEET_tblARRAYRESPONSE);
            for (Row rowAR : ARSheet) {
                //just skip the row if row number is 0
                if (rowAR.getRowNum() == 0) {
                    continue;
                }

                arrayResponseID = (int) rowAR.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                if (eresponseModel.getEvaluationResponseID() == arrayResponseID) {
                    arrQuestionID = (int) rowAR.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                    arrayResponse = (int) rowAR.getCell(2, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                }
            }

            /* get matrix responses */

            Sheet MRSheet = workbook.getSheet(cExcelHelper.SHEET_tblMATRIXRESPONSE);
            for (Row rowMR : MRSheet) {
                //just skip the row if row number is 0
                if (rowMR.getRowNum() == 0) {
                    continue;
                }

                matrixResponseID = (int) rowMR.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();

                if (eresponseModel.getEvaluationResponseID() == matrixResponseID) {
                    matQuestionID = (int) rowMR.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                    matrixResponse = (int) rowMR.getCell(2, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                    rowResponse = (int) rowMR.getCell(3, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                    colResponse = (int) rowMR.getCell(4, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                }
            }

            if (!addEvaluationResponse(eresponseModel, numQuestionID, numericResponse,
                    txtQuestionID, textResponse, dateQuestionID, dateResponse,
                    arrQuestionID, arrayResponse, matQuestionID, matrixResponse,
                    rowResponse, colResponse)) {
                return false;
            }
        }

        return true;
    }

    public boolean addEvaluationResponse(cEvaluationResponseModel eresponseModel, long numQuestionID,
                                long numericResponse, long txtQuestionID, String textResponse,
                                long dateQuestionID, Date dateResponse, long arrQuestionID,
                                long arrayResponse, long matQuestionID, long matrixResponse,
                                long rowResponse, long colResponse) {

        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_ID, eresponseModel.getEvaluationResponseID());
        cv.put(cSQLDBHelper.KEY_USER_EVALUATION_FK_ID, eresponseModel.getUserEvaluationID());

        // insert evaluation type details
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblEVALUATION_RESPONSE, null, cv) < 0) {
                return false;
            }

            if (numericResponse != -1) {
                if (!addNumericResponse(eresponseModel.getEvaluationResponseID(), numQuestionID,
                        numericResponse))
                    return false;
            }

            if (textResponse != null) {
                if (!addTextResponse(eresponseModel.getEvaluationResponseID(), txtQuestionID,
                        textResponse))
                    return false;
            }

            if (dateResponse != null) {
                if (!addDateResponse(eresponseModel.getEvaluationResponseID(), dateQuestionID,
                        dateResponse))
                    return false;
            }

            if (arrayResponse != -1) {
                if (!addArrayResponse(eresponseModel.getEvaluationResponseID(), arrQuestionID,
                        arrayResponse))
                    return false;
            }

            if (matrixResponse != -1) {
                if (!addMatrixResponse(eresponseModel.getEvaluationResponseID(), matQuestionID,
                        matrixResponse, rowResponse, colResponse))
                    return false;
            }

        } catch (Exception e) {
            Log.d(TAG, "Exception in importing EVALUATION RESPONSE from Excel: " + e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }

    public boolean addNumericResponse(long eresponseID, long numQuestionID, long numericResponse) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_EVALUATION_RESPONSE_FK_ID, eresponseID);
        cv.put(cSQLDBHelper.KEY_QUESTION_FK_ID, numQuestionID);
        cv.put(cSQLDBHelper.KEY_NUMERIC_RESPONSE, numericResponse);

        // insert evaluation type details
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblNUMERICRESPONSE, null, cv) < 0) {
                return false;
            }
        } catch (Exception e) {
            Log.d(TAG, "Exception in importing NUMERIC RESPONSE from Excel: " + e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }

    public boolean addTextResponse(long eresponseID, long txtQuestionID, String testResponse) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_EVALUATION_RESPONSE_FK_ID, eresponseID);
        cv.put(cSQLDBHelper.KEY_QUESTION_FK_ID, txtQuestionID);
        cv.put(cSQLDBHelper.KEY_TEXT_RESPONSE, testResponse);

        // insert evaluation type details
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblTEXTRESPONSE, null, cv) < 0) {
                return false;
            }
        } catch (Exception e) {
            Log.d(TAG, "Exception in importing TEXT RESPONSE from Excel: " + e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }

    public boolean addDateResponse(long eresponseID, long dateQuestionID, Date dateResponse) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_EVALUATION_RESPONSE_FK_ID, eresponseID);
        cv.put(cSQLDBHelper.KEY_QUESTION_FK_ID, dateQuestionID);
        cv.put(cSQLDBHelper.KEY_DATE_RESPONSE, String.valueOf(dateResponse));

        // insert evaluation type details
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblDATERESPONSE, null, cv) < 0) {
                return false;
            }
        } catch (Exception e) {
            Log.d(TAG, "Exception in importing DATE RESPONSE from Excel: " + e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }

    public boolean addArrayResponse(long eresponseID, long arrQuestionID, long arrayResponse) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_EVALUATION_RESPONSE_FK_ID, eresponseID);
        cv.put(cSQLDBHelper.KEY_QUESTION_FK_ID, arrQuestionID);
        cv.put(cSQLDBHelper.KEY_ARRAY_RESPONSE_FK_ID, arrayResponse);

        // insert evaluation type details
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblARRAYRESPONSE, null, cv) < 0) {
                return false;
            }
        } catch (Exception e) {
            Log.d(TAG, "Exception in importing ARRAY RESPONSE from Excel: " + e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }

    public boolean addMatrixResponse(long eresponseID, long matQuestionID, long matrixResponse,
                                     long rowResponse, long colResponse) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_EVALUATION_RESPONSE_FK_ID, eresponseID);
        cv.put(cSQLDBHelper.KEY_QUESTION_FK_ID, matQuestionID);
        cv.put(cSQLDBHelper.KEY_MATRIX_RESPONSE_FK_ID, matrixResponse);
        cv.put(cSQLDBHelper.KEY_ROW_RESPONSE_FK_ID, rowResponse);
        cv.put(cSQLDBHelper.KEY_COL_RESPONSE_FK_ID, colResponse);


        // insert evaluation type details
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblMATRIXRESPONSE, null, cv) < 0) {
                return false;
            }
        } catch (Exception e) {
            Log.d(TAG, "Exception in importing MATRIX RESPONSE from Excel: " + e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }

    public boolean deleteEvaluationResponses() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblEVALUATION_RESPONSE, null, null);

        // close the database connection
        db.close();

        return result > -1;
    }

    public boolean deleteNumericResponses() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblNUMERICRESPONSE, null, null);

        // close the database connection
        db.close();

        return result > -1;
    }

    public boolean deleteTextResponses() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblTEXTRESPONSE, null, null);

        // close the database connection
        db.close();

        return result > -1;
    }

    public boolean deleteDateResponses() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblDATERESPONSE, null, null);

        // close the database connection
        db.close();

        return result > -1;
    }

    public boolean deleteArrayResponses() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblARRAYRESPONSE, null, null);

        // close the database connection
        db.close();

        return result > -1;
    }

    public boolean deleteMatrixResponses() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblMATRIXRESPONSE, null, null);

        // close the database connection
        db.close();

        return result > -1;
    }
}
