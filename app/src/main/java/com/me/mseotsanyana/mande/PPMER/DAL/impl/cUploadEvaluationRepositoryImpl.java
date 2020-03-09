package com.me.mseotsanyana.mande.PPMER.DAL.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.util.Pair;

import com.google.gson.Gson;
import com.me.mseotsanyana.mande.PPMER.BLL.repository.iUploadEvaluationRepository;
import com.me.mseotsanyana.mande.PPMER.DAL.model.cArrayChoiceModel;
import com.me.mseotsanyana.mande.PPMER.DAL.model.cColOptionModel;
import com.me.mseotsanyana.mande.PPMER.DAL.model.cEResponseModel;
import com.me.mseotsanyana.mande.PPMER.DAL.model.cEvaluationTypeModel;
import com.me.mseotsanyana.mande.PPMER.DAL.model.cMatrixChoiceModel;
import com.me.mseotsanyana.mande.PPMER.DAL.model.cQuestionnaireModel;
import com.me.mseotsanyana.mande.PPMER.DAL.model.cRowOptionModel;
import com.me.mseotsanyana.mande.STORAGE.database.cSQLDBHelper;
import com.me.mseotsanyana.mande.STORAGE.excel.cExcelHelper;
import com.me.mseotsanyana.mande.UTILITY.cConstant;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
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

        for (Iterator<Row> ritAC = ACSheet.iterator(); ritAC.hasNext(); ) {
            Row cRow = ritAC.next();

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
            Map<Integer, Set<Pair<Integer, Integer>>> qtcMap = new HashMap<>();
            Set<Pair<Integer, Integer>> tcSet = new HashSet<>();
            int questionID, typeID, choiceID;

            Sheet qtcSheet = workbook.getSheet(cExcelHelper.SHEET_tblARRAYCHOICESET);
            for (Iterator<Row> ritQTC = qtcSheet.iterator(); ritQTC.hasNext(); ) {
                Row rowQTC = ritQTC.next();

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
     * @param arrayChoiceModel
     * @param qtcMap
     * @return
     */
    public boolean addArrayChoice(cArrayChoiceModel arrayChoiceModel,
                                  Map<Integer, Set<Pair<Integer, Integer>>> qtcMap) {
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
            for (Map.Entry<Integer, Set<Pair<Integer, Integer>>> qtcEntry : qtcMap.entrySet()) {
                int choiceID = qtcEntry.getKey();
                for (Pair<Integer, Integer> qcPair : qtcEntry.getValue()) {
                    int questionID = qcPair.first;
                    int typeID = qcPair.second;

                    if (addQuestionArrayTypeChoice(questionID, typeID, choiceID))
                        continue;
                    else
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
     * @param questionID
     * @param typeID
     * @param choiceID
     * @return
     */
    public boolean addQuestionArrayTypeChoice(int questionID, int typeID, int choiceID) {
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
     * @return
     */
    @Override
    public boolean addRowOptionFromExcel() {
        Workbook workbook = excelHelper.getWorkbookEVALUATION();
        Sheet ROSheet = workbook.getSheet(cExcelHelper.SHEET_tblROWOPTION);

        if (ROSheet == null) {
            return false;
        }

        for (Iterator<Row> ritRO = ROSheet.iterator(); ritRO.hasNext(); ) {
            Row cRow = ritRO.next();

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
     * @param rowOptionModel
     * @return
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
     * @return
     */

    @Override
    public boolean addColOptionFromExcel() {
        Workbook workbook = excelHelper.getWorkbookEVALUATION();
        Sheet COSheet = workbook.getSheet(cExcelHelper.SHEET_tblCOLOPTION);

        if (COSheet == null) {
            return false;
        }

        for (Iterator<Row> ritCO = COSheet.iterator(); ritCO.hasNext(); ) {
            Row cRow = ritCO.next();

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
     * @param colOptionModel
     * @return
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
     * @return
     */
    @Override
    public boolean addMatrixChoiceFromExcel() {
        Workbook workbook = excelHelper.getWorkbookEVALUATION();
        Sheet MCSheet = workbook.getSheet(cExcelHelper.SHEET_tblMATRIXCHOICE);

        if (MCSheet == null) {
            return false;
        }

        for (Iterator<Row> ritMC = MCSheet.iterator(); ritMC.hasNext(); ) {
            Row cRow = ritMC.next();

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
            Map<Integer, Set<Pair<Integer, Integer>>> qtcMap = new HashMap<>();
            Set<Pair<Integer, Integer>> tcSet = new HashSet<>();
            int questionID, typeID, choiceID;

            Sheet qtcSheet = workbook.getSheet(cExcelHelper.SHEET_tblMATRIXCHOICESET);
            for (Iterator<Row> ritQTC = qtcSheet.iterator(); ritQTC.hasNext(); ) {
                Row rowQTC = ritQTC.next();

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
     * @param matrixChoiceModel
     * @return
     */
    public boolean addMatrixChoice(cMatrixChoiceModel matrixChoiceModel,
                                   Map<Integer, Set<Pair<Integer, Integer>>> qtcMap) {
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
            for (Map.Entry<Integer, Set<Pair<Integer, Integer>>> qtcEntry : qtcMap.entrySet()) {
                int choiceID = qtcEntry.getKey();
                for (Pair<Integer, Integer> qcPair : qtcEntry.getValue()) {
                    int questionID = qcPair.first;
                    int typeID = qcPair.second;

                    if (addQuestionMatrixTypeChoice(questionID, typeID, choiceID))
                        continue;
                    else
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

    public boolean addQuestionMatrixTypeChoice(int questionID, int typeID, int choiceID) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(cSQLDBHelper.KEY_QUESTION_FK_ID, questionID);
        cv.put(cSQLDBHelper.KEY_MATRIX_TYPE_FK_ID, typeID);
        cv.put(cSQLDBHelper.KEY_MATRIX_CHOICE_FK_ID, choiceID);

        if (db.insert(cSQLDBHelper.TABLE_tblMATRIXCHOICESET, null, cv) < 0) {
            return false;
        }

        return true;
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
     * @return
     */
    @Override
    public boolean addEvaluationTypeFromExcel() {
        Workbook workbook = excelHelper.getWorkbookEVALUATION();
        Sheet ETSheet = workbook.getSheet(cExcelHelper.SHEET_tblEVALUATIONTYPE);

        if (ETSheet == null) {
            return false;
        }

        for (Iterator<Row> ritET = ETSheet.iterator(); ritET.hasNext(); ) {
            Row cRow = ritET.next();

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
     * @param evaluationTypeModel
     * @return
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

    /* ################################# QUESTIONNAIRE FUNCTIONS #################################*/

    @Override
    public boolean addQuestionnaireFromExcel() {
        Workbook workbook = excelHelper.getWorkbookEVALUATION();
        Sheet QSheet = workbook.getSheet(cExcelHelper.SHEET_tblQUESTIONNAIRE);

        if (QSheet == null) {
            return false;
        }

        for (Iterator<Row> ritQ = QSheet.iterator(); ritQ.hasNext(); ) {
            Row cRow = ritQ.next();

            //just skip the row if row number is 0
            if (cRow.getRowNum() == 0) {
                continue;
            }

            cQuestionnaireModel questionnaireModel = new cQuestionnaireModel();

            questionnaireModel.setQuestionnaireID((int)
                    cRow.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            questionnaireModel.setEvaluationTypeID((int)
                    cRow.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            questionnaireModel.setName(
                    cRow.getCell(2, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
            questionnaireModel.setDescription(
                    cRow.getCell(3, Row.CREATE_NULL_AS_BLANK).getStringCellValue());

            /* get questions of the questionnaire*/
            Set<Pair<Integer, Integer>> questionSet = new HashSet<>();
            int qqID = -1, questionnaireID = -1, questionID = -1;
            Sheet QQSheet = workbook.getSheet(cExcelHelper.SHEET_tblQUESTIONNAIRE_QUESTION);
            for (Iterator<Row> ritQQ = QQSheet.iterator(); ritQQ.hasNext(); ) {
                Row rowQQ = ritQQ.next();

                //just skip the row if row number is 0
                if (rowQQ.getRowNum() == 0) {
                    continue;
                }

                questionnaireID = (int) rowQQ.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                if (questionnaireModel.getQuestionnaireID() == questionnaireID) {
                    qqID = (int) rowQQ.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                    questionID = (int) rowQQ.getCell(2, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                    questionSet.add(new Pair<>(qqID, questionID));
                }
            }

            /* get users of the questionnaire */
            Set<Pair<Integer, Integer>> userSet = new HashSet<>();
            int quID = -1, userID = -1;
            questionnaireID = -1;
            Sheet QUSheet = workbook.getSheet(cExcelHelper.SHEET_tblQUESTIONNAIRE_USER);
            for (Iterator<Row> ritQU = QUSheet.iterator(); ritQU.hasNext(); ) {
                Row rowQU = ritQU.next();

                //just skip the row if row number is 0
                if (rowQU.getRowNum() == 0) {
                    continue;
                }

                questionnaireID = (int) rowQU.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                if (questionnaireModel.getQuestionnaireID() == questionnaireID) {
                    quID = (int) rowQU.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                    userID = (int) rowQU.getCell(2, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                    userSet.add(new Pair<>(quID, userID));
                }
            }

            if (!addQuestionnaire(questionnaireModel, questionSet, userSet)) {
                return false;
            }

            Gson gson = new Gson();
            Log.d(TAG, gson.toJson(questionnaireModel));
            Log.d(TAG, gson.toJson(userSet));

            /* conditional order of the questionnaire*/
            int coID = -1, questionOrderID = -1, rQuestionID = -1, prQuestionID = -1, nrQuestionID = -1;
            Sheet COSheet = workbook.getSheet(cExcelHelper.SHEET_tblCONDITIONAL_ORDER);
            for (Iterator<Row> ritCO = COSheet.iterator(); ritCO.hasNext(); ) {
                Row rowCO = ritCO.next();

                //just skip the row if row number is 0
                if (rowCO.getRowNum() == 0) {
                    continue;
                }

                for (Pair question : questionSet) {
                    questionOrderID = (int) rowCO.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                    if ((int) question.first == questionOrderID) {
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
        }

        return true;
    }

    public boolean addQuestionnaire(cQuestionnaireModel questionnaireModel,
                                    Set<Pair<Integer, Integer>> questionSet,
                                    Set<Pair<Integer, Integer>> userSet) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_ID, questionnaireModel.getQuestionnaireID());
        cv.put(cSQLDBHelper.KEY_EVALUATION_TYPE_FK_ID, questionnaireModel.getEvaluationTypeID());
        cv.put(cSQLDBHelper.KEY_NAME, questionnaireModel.getName());
        cv.put(cSQLDBHelper.KEY_DESCRIPTION, questionnaireModel.getDescription());

        // insert evaluation type details
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblQUESTIONNAIRE, null, cv) < 0) {
                return false;
            }

            // add questions
            for (Pair<Integer, Integer> pair : questionSet) {
                if (addQuestionnaireQuestion(pair.first, questionnaireModel.getQuestionnaireID(), pair.second))
                    continue;
                else
                    return false;
            }

            // add users
            for (Pair<Integer, Integer> pair : userSet) {
                if (addQuestionnaireUser(pair.first, questionnaireModel.getQuestionnaireID(), pair.second))
                    continue;
                else
                    return false;
            }


        } catch (Exception e) {
            Log.d(TAG, "Exception in importing QUESTIONNAIRE from Excel: " + e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }

    public boolean addQuestionnaireQuestion(int qqID, int questionnaireID, int questionID) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_ID, qqID);
        cv.put(cSQLDBHelper.KEY_QUESTIONNAIRE_FK_ID, questionnaireID);
        cv.put(cSQLDBHelper.KEY_QUESTION_FK_ID, questionID);

        // insert evaluation type details
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblQUESTION_QUESTIONNAIRE, null, cv) < 0) {
                return false;
            }
        } catch (Exception e) {
            Log.d(TAG, "Exception in importing QUESTIONNAIRE QUESTIONS from Excel: " + e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }

    public boolean addConditionalOrder(int coID, int questionOrderID,
                                       int rQuestionID, int prQuestionID, int nrQuestionID) {
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


    public boolean addQuestionnaireUser(int quID, int questionnaireID, int userID) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_ID, quID);
        cv.put(cSQLDBHelper.KEY_QUESTIONNAIRE_FK_ID, questionnaireID);
        cv.put(cSQLDBHelper.KEY_USER_FK_ID, userID);

        // insert evaluation type details
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblQUESTIONNAIRE_USER, null, cv) < 0) {
                return false;
            }
        } catch (Exception e) {
            Log.d(TAG, "Exception in importing QUESTIONNAIRE USERS from Excel: " + e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }

    public boolean deleteQuestionnaires() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblQUESTIONNAIRE, null, null);

        // close the database connection
        db.close();

        return result > -1;
    }

    public boolean deleteQuestionnaireQuestions() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblQUESTION_QUESTIONNAIRE, null, null);

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

    public boolean deleteQuestionnaireUsers() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblQUESTIONNAIRE_USER, null, null);

        // close the database connection
        db.close();

        return result > -1;
    }

    /* ############################### EVALUATION RESPONSE FUNCTIONS #############################*/

    @Override
    public boolean addEResponseFromExcel() {
        Workbook workbook = excelHelper.getWorkbookEVALUATION();
        Sheet ERSheet = workbook.getSheet(cExcelHelper.SHEET_tblERESPONSE);

        if (ERSheet == null) {
            return false;
        }

        for (Iterator<Row> ritER = ERSheet.iterator(); ritER.hasNext(); ) {
            Row cRow = ritER.next();

            //just skip the row if row number is 0
            if (cRow.getRowNum() == 0) {
                continue;
            }

            cEResponseModel eResponseModel = new cEResponseModel();

            eResponseModel.setEresponseID((int)
                    cRow.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            eResponseModel.setQuestionID((int)
                    cRow.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            eResponseModel.setQuestionnaireUserID((int)
                    cRow.getCell(2, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());

            int responseID, numericResponse = -1;
            int textResponseID;
            String textResponse = null;
            int dateResponseID;
            Date dateResponse = null;
            int arrayResponseID, arrayResponse = -1;
            int matrixResponseID, matrixResponse = -1, rowResponse = 0, colResponse = 0;

            /* get numeric responses */

            Sheet NRSheet = workbook.getSheet(cExcelHelper.SHEET_tblNUMERICRESPONSE);
            for (Iterator<Row> ritNR = NRSheet.iterator(); ritNR.hasNext(); ) {
                Row rowNR = ritNR.next();

                //just skip the row if row number is 0
                if (rowNR.getRowNum() == 0) {
                    continue;
                }

                responseID = (int) rowNR.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                if (eResponseModel.getEresponseID() == responseID) {
                    numericResponse = (int) rowNR.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                }
            }

            /* get text responses */

            Sheet TRSheet = workbook.getSheet(cExcelHelper.SHEET_tblTEXTRESPONSE);
            for (Iterator<Row> ritTR = TRSheet.iterator(); ritTR.hasNext(); ) {
                Row rowTR = ritTR.next();

                //just skip the row if row number is 0
                if (rowTR.getRowNum() == 0) {
                    continue;
                }

                textResponseID = (int) rowTR.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                if (eResponseModel.getEresponseID() == textResponseID) {
                    textResponse = rowTR.getCell(1, Row.CREATE_NULL_AS_BLANK).getStringCellValue();
                }
            }

            /* get text responses */

            Sheet DRSheet = workbook.getSheet(cExcelHelper.SHEET_tblDATERESPONSE);
            for (Iterator<Row> ritDR = DRSheet.iterator(); ritDR.hasNext(); ) {
                Row rowDR = ritDR.next();

                //just skip the row if row number is 0
                if (rowDR.getRowNum() == 0) {
                    continue;
                }

                dateResponseID = (int) rowDR.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                if (eResponseModel.getEresponseID() == dateResponseID) {
                    dateResponse = rowDR.getCell(1, Row.CREATE_NULL_AS_BLANK).getDateCellValue();
                }
            }

            /* get array responses */

            Sheet ARSheet = workbook.getSheet(cExcelHelper.SHEET_tblARRAYRESPONSE);
            for (Iterator<Row> ritAR = ARSheet.iterator(); ritAR.hasNext(); ) {
                Row rowAR = ritAR.next();

                //just skip the row if row number is 0
                if (rowAR.getRowNum() == 0) {
                    continue;
                }

                arrayResponseID = (int) rowAR.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                if (eResponseModel.getEresponseID() == arrayResponseID) {
                    arrayResponse = (int) rowAR.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                }
            }

            /* get matrix responses */

            Sheet MRSheet = workbook.getSheet(cExcelHelper.SHEET_tblMATRIXRESPONSE);
            for (Iterator<Row> ritMR = MRSheet.iterator(); ritMR.hasNext(); ) {
                Row rowMR = ritMR.next();

                //just skip the row if row number is 0
                if (rowMR.getRowNum() == 0) {
                    continue;
                }

                matrixResponseID = (int) rowMR.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                if (eResponseModel.getEresponseID() == matrixResponseID) {
                    matrixResponse = (int) rowMR.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                    rowResponse = (int) rowMR.getCell(2, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                    colResponse = (int) rowMR.getCell(3, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                }
            }

            if (!addEResponse(eResponseModel, numericResponse,
                    textResponse, dateResponse, arrayResponse, matrixResponse, rowResponse, colResponse)) {
                return false;
            }
        }

        return true;
    }

    public boolean addEResponse(cEResponseModel eResponseModel, int numericResponse,
                                String textResponse, Date dateResponse, int arrayResponse,
                                int matrixResponse, int rowResponse, int colResponse) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_ID, eResponseModel.getEresponseID());
        cv.put(cSQLDBHelper.KEY_QUESTION_FK_ID, eResponseModel.getQuestionID());
        cv.put(cSQLDBHelper.KEY_QUESTIONNAIRE_USER_FK_ID, eResponseModel.getQuestionnaireUserID());

        // insert evaluation type details
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblERESPONSE, null, cv) < 0) {
                return false;
            }

            if (numericResponse != -1) {
                if (addNumericResponse(eResponseModel.getEresponseID(), numericResponse))
                    return true;
                else
                    return false;
            }

            if (textResponse != null) {
                if (addTextResponse(eResponseModel.getEresponseID(), textResponse))
                    return true;
                else
                    return false;
            }

            if (dateResponse != null) {
                if (addDateResponse(eResponseModel.getEresponseID(), dateResponse))
                    return true;
                else
                    return false;
            }

            if (arrayResponse != -1) {
                if (addArrayResponse(eResponseModel.getEresponseID(), arrayResponse))
                    return true;
                else
                    return false;
            }

            if (matrixResponse != -1) {
                if (addMatrixResponse(eResponseModel.getEresponseID(), matrixResponse, rowResponse, colResponse))
                    return true;
                else
                    return false;
            }

        } catch (Exception e) {
            Log.d(TAG, "Exception in importing EVALUATION RESPONSES from Excel: " + e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }

    public boolean addNumericResponse(int eresponseID, int numericResponse) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_RESPONSE_FK_ID, eresponseID);
        cv.put(cSQLDBHelper.KEY_NUMERIC_RESPONSE_FK_ID, numericResponse);

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

    public boolean addTextResponse(int eresponseID, String testResponse) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_RESPONSE_FK_ID, eresponseID);
        cv.put(cSQLDBHelper.KEY_TEXT_RESPONSE_FK_ID, testResponse);

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

    public boolean addDateResponse(int eresponseID, Date dateResponse) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_RESPONSE_FK_ID, eresponseID);
        cv.put(cSQLDBHelper.KEY_DATE_RESPONSE_FK_ID, String.valueOf(dateResponse));

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

    public boolean addArrayResponse(int eresponseID, int arrayResponse) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_RESPONSE_FK_ID, eresponseID);
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

    public boolean addMatrixResponse(int eresponseID, int matrixResponse, int rowResponse, int colResponse) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_RESPONSE_FK_ID, eresponseID);
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

    public boolean deleteEResponses() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblERESPONSE, null, null);

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
