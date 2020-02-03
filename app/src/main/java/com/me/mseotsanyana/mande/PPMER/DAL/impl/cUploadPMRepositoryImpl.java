package com.me.mseotsanyana.mande.PPMER.DAL.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.util.Pair;

import com.google.gson.Gson;
import com.me.mseotsanyana.mande.PPMER.BLL.repository.iUploadPMRepository;
import com.me.mseotsanyana.mande.PPMER.DAL.model.cChoiceModel;
import com.me.mseotsanyana.mande.PPMER.DAL.model.cCriteriaModel;
import com.me.mseotsanyana.mande.PPMER.DAL.model.cMultivaluedOptionModel;
import com.me.mseotsanyana.mande.PPMER.DAL.model.cQuestionGroupingModel;
import com.me.mseotsanyana.mande.PPMER.DAL.model.cQuestionModel;
import com.me.mseotsanyana.mande.PPMER.DAL.model.cQuestionTypeModel;
import com.me.mseotsanyana.mande.PPMER.DAL.model.cActivityModel;
import com.me.mseotsanyana.mande.PPMER.DAL.model.cImpactModel;
import com.me.mseotsanyana.mande.PPMER.DAL.model.cInputModel;
import com.me.mseotsanyana.mande.PPMER.DAL.model.cLogFrameModel;
import com.me.mseotsanyana.mande.PPMER.DAL.model.cOutcomeModel;
import com.me.mseotsanyana.mande.PPMER.DAL.model.cOutputModel;
import com.me.mseotsanyana.mande.PPMER.DAL.model.cRaidModel;
import com.me.mseotsanyana.mande.STORAGE.database.cSQLDBHelper;
import com.me.mseotsanyana.mande.STORAGE.excel.cExcelHelper;
import com.me.mseotsanyana.mande.UTILITY.cConstant;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class cUploadPMRepositoryImpl implements iUploadPMRepository {
    private static SimpleDateFormat sdf = cConstant.FORMAT_DATE;
    private static String TAG = cUploadPMRepositoryImpl.class.getSimpleName();

    /* an object of the database helper */
    private cSQLDBHelper dbHelper;
    /* an object of the excel helper */
    private cExcelHelper excelHelper;

    public cUploadPMRepositoryImpl(Context context) {
        dbHelper = new cSQLDBHelper(context);
        excelHelper = new cExcelHelper(context);
    }


    /* #################################### CRITERIA FUNCTIONS ###################################*/

    /**
     * This function extracts criteria data from excel and adds it to the database.
     *
     * @return
     */
    @Override
    public boolean addCriteriaFromExcel() {
        Workbook workbook = excelHelper.getWorkbook();
        Sheet CSheet = workbook.getSheet(cExcelHelper.SHEET_tblCRITERIA);

        if (CSheet == null) {
            return false;
        }

        for (Iterator<Row> ritC = CSheet.iterator(); ritC.hasNext(); ) {
            Row cRow = ritC.next();

            //just skip the row if row number is 0
            if (cRow.getRowNum() == 0) {
                continue;
            }

            cCriteriaModel criteriaModel = new cCriteriaModel();

            criteriaModel.setCriteriaID((int)
                    cRow.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            criteriaModel.setName(
                    cRow.getCell(1, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
            criteriaModel.setDescription(
                    cRow.getCell(2, Row.CREATE_NULL_AS_BLANK).getStringCellValue());

            if (!addQuestionCriteria(criteriaModel)) {
                return false;
            }
        }

        return true;
    }

    /**
     * This function adds criteria data to the database.
     *
     * @param criteriaModel
     * @return
     */
    public boolean addQuestionCriteria(cCriteriaModel criteriaModel) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_ID, criteriaModel.getCriteriaID());
        cv.put(cSQLDBHelper.KEY_NAME, criteriaModel.getName());
        cv.put(cSQLDBHelper.KEY_DESCRIPTION, criteriaModel.getDescription());

        // insert project details
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblCRITERIA, null, cv) < 0) {
                return false;
            }
        } catch (Exception e) {
            Log.d(TAG, "Exception in importing CRITERIA from Excel: " + e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }

    /*###################################### CHOICE FUNCTIONS ####################################*/

    /**
     * This function extracts choice data from excel and adds it to the database.
     *
     * @return
     */
    @Override
    public boolean addChoiceFromExcel() {
        Workbook workbook = excelHelper.getWorkbook();
        Sheet CSheet = workbook.getSheet(cExcelHelper.SHEET_tblCHOICE);

        if (CSheet == null) {
            return false;
        }

        for (Iterator<Row> ritC = CSheet.iterator(); ritC.hasNext(); ) {
            Row cRow = ritC.next();

            //just skip the row if row number is 0
            if (cRow.getRowNum() == 0) {
                continue;
            }

            cChoiceModel choiceModel = new cChoiceModel();

            choiceModel.setChoiceID((int) cRow.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            choiceModel.setName(cRow.getCell(1, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
            choiceModel.setDescription(cRow.getCell(2, Row.CREATE_NULL_AS_BLANK).getStringCellValue());

            if (!addChoiceFromExcel(choiceModel)) {
                return false;
            }
        }

        return true;
    }

    /**
     * This function adds choice data to the database.
     *
     * @param choiceModel
     * @return
     */
    public boolean addChoiceFromExcel(cChoiceModel choiceModel) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_ID, choiceModel.getChoiceID());
        cv.put(cSQLDBHelper.KEY_NAME, choiceModel.getName());
        cv.put(cSQLDBHelper.KEY_DESCRIPTION, choiceModel.getDescription());

        // insert project details
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblCHOICE, null, cv) < 0) {
                return false;
            }
        } catch (Exception e) {
            Log.d(TAG, "Exception in importing CHOICE from Excel: " + e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }

    /*################################ QUESTION GROUPING FUNCTIONS ###############################*/

    /**
     * This function extracts question grouping data from excel and adds it to the database.
     *
     * @return
     */
    @Override
    public boolean addQuestionGroupingFromExcel() {
        Workbook workbook = excelHelper.getWorkbook();
        Sheet QGSheet = workbook.getSheet(cExcelHelper.SHEET_tblQUESTION_GROUPING);

        if (QGSheet == null) {
            return false;
        }

        for (Iterator<Row> ritQG = QGSheet.iterator(); ritQG.hasNext(); ) {
            Row cRow = ritQG.next();

            //just skip the row if row number is 0
            if (cRow.getRowNum() == 0) {
                continue;
            }

            cQuestionGroupingModel questionGroupingModel = new cQuestionGroupingModel();

            questionGroupingModel.setQuestionGroupingID(
                    (int) cRow.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            questionGroupingModel.setName(
                    cRow.getCell(1, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
            questionGroupingModel.setDescription(
                    cRow.getCell(2, Row.CREATE_NULL_AS_BLANK).getStringCellValue());

            if (!addQuestionGroupingFromExcel(questionGroupingModel)) {
                return false;
            }
        }

        return true;
    }

    /**
     * This function adds question grouping data to the database.
     *
     * @param questionGroupingModel
     * @return
     */
    public boolean addQuestionGroupingFromExcel(cQuestionGroupingModel questionGroupingModel) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_ID, questionGroupingModel.getQuestionGroupingID());
        cv.put(cSQLDBHelper.KEY_NAME, questionGroupingModel.getName());
        cv.put(cSQLDBHelper.KEY_DESCRIPTION, questionGroupingModel.getDescription());

        // insert project details
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblQUESTION_GROUPING, null, cv) < 0) {
                return false;
            }
        } catch (Exception e) {
            Log.d(TAG, "Exception in importing QUESTION GROUPING from Excel: " + e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }

    /* ################################## QUESTION TYPE FUNCTIONS ################################*/

    /**
     * This function extracts question type data from excel and adds it to the database.
     *
     * @return
     */
    @Override
    public boolean addQuestionTypeFromExcel() {
        Workbook workbook = excelHelper.getWorkbook();
        Sheet QTSheet = workbook.getSheet(cExcelHelper.SHEET_tblQUESTION_TYPE);

        if (QTSheet == null) {
            return false;
        }

        for (Iterator<Row> ritQT = QTSheet.iterator(); ritQT.hasNext(); ) {
            Row cRow = ritQT.next();

            //just skip the row if row number is 0
            if (cRow.getRowNum() == 0) {
                continue;
            }

            cQuestionTypeModel questionTypeModel = new cQuestionTypeModel();

            questionTypeModel.setQuestionTypeID(
                    (int) cRow.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            questionTypeModel.setName(
                    cRow.getCell(1, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
            questionTypeModel.setDescription(
                    cRow.getCell(2, Row.CREATE_NULL_AS_BLANK).getStringCellValue());


            /* get primitive type */
            Sheet primitiveSheet = workbook.getSheet(cExcelHelper.SHEET_tblPRIMITIVE_TYPE);
            int questionTypeID, primitiveTypeID = -1;

            for (Iterator<Row> ritPrimitive = primitiveSheet.iterator(); ritPrimitive.hasNext(); ) {
                Row rowPrimitive = ritPrimitive.next();

                //just skip the row if row number is 0
                if (rowPrimitive.getRowNum() == 0) {
                    continue;
                }

                questionTypeID = (int) rowPrimitive.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                if (questionTypeModel.getQuestionTypeID() == questionTypeID) {
                    primitiveTypeID = questionTypeID;
                    break;
                }
            }

            /* get custom type */
            Sheet customSheet = workbook.getSheet(cExcelHelper.SHEET_tblCUSTOM_TYPE);
            int customTypeID = -1;//Pair<Integer, Integer> customTypePair = null;
            //int customID, optionID;
            for (Iterator<Row> ritCustom = customSheet.iterator(); ritCustom.hasNext(); ) {
                Row rowCustom = ritCustom.next();

                //just skip the row if row number is 0
                if (rowCustom.getRowNum() == 0) {
                    continue;
                }

                questionTypeID = (int) rowCustom.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                if (questionTypeModel.getQuestionTypeID() == questionTypeID) {
                    //optionID = (int) rowCustom.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                    //customTypePair = new Pair<>(customID, optionID);
                    customTypeID = questionTypeID;
                    break;
                }
            }

            /* get choice of the question type */
            Set<Pair<Integer, Integer>> choiceSet = new HashSet<>();
            int choiceID;
            Sheet choiceSetSheet = workbook.getSheet(cExcelHelper.SHEET_tblCHOICESET);
            for (Iterator<Row> ritChoiceSet = choiceSetSheet.iterator(); ritChoiceSet.hasNext(); ) {
                Row rowChoiceSet = ritChoiceSet.next();

                //just skip the row if row number is 0
                if (rowChoiceSet.getRowNum() == 0) {
                    continue;
                }

                questionTypeID = (int) rowChoiceSet.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                if (questionTypeModel.getQuestionTypeID() == questionTypeID) {
                    choiceID = (int) rowChoiceSet.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                    choiceSet.add(new Pair<>(questionTypeID, choiceID));
                }
            }

            if (!addQuestionTypeFromExcel(questionTypeModel, choiceSet, customTypeID, primitiveTypeID)) {
                return false;
            }
        }

        return true;
    }

    /**
     * This function adds question type to the database.
     *
     * @param questionTypeModel
     * @param choiceSet
     * @param customTypeID
     * @param primitiveTypeID
     * @return
     */
    public boolean addQuestionTypeFromExcel(cQuestionTypeModel questionTypeModel,
                                            Set<Pair<Integer, Integer>> choiceSet,
                                            int customTypeID,
                                            int primitiveTypeID) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_ID, questionTypeModel.getQuestionTypeID());
        cv.put(cSQLDBHelper.KEY_NAME, questionTypeModel.getName());
        cv.put(cSQLDBHelper.KEY_DESCRIPTION, questionTypeModel.getDescription());

        // insert question type details
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblQUESTION_TYPE, null, cv) < 0) {
                return false;
            }

            /* insert primitive type in the database */
            if (primitiveTypeID > -1) {
                ContentValues cvPrimitive = new ContentValues();

                cvPrimitive.put(cSQLDBHelper.KEY_QUESTION_TYPE_FK_ID, primitiveTypeID);

                if (db.insert(cSQLDBHelper.TABLE_tblPRIMITIVE_TYPE, null,
                        cvPrimitive) < 0) {
                    return false;
                }
            }

            /* insert custom type in the database */
            if (customTypeID > -1) {
                ContentValues cvCustom = new ContentValues();

                cvCustom.put(cSQLDBHelper.KEY_QUESTION_TYPE_FK_ID, customTypeID);

                if (db.insert(cSQLDBHelper.TABLE_tblCUSTOM_TYPE, null,
                        cvCustom) < 0) {
                    return false;
                }
            }

            // add question type choices
            for (Pair<Integer, Integer> pair : choiceSet) {
                int questionTypeID = pair.first;
                int choiceID = pair.second;
                if (addChoiceSet(questionTypeID, choiceID))
                    continue;
                else
                    return false;
            }

        } catch (Exception e) {
            Log.d(TAG, "Exception in importing QUESTION TYPE from Excel: " + e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }

    /**
     * This function adds custom and choice data to the database.
     *
     * @param questionTypeID
     * @param choiceID
     * @return
     */
    public boolean addChoiceSet(int questionTypeID, int choiceID) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_QUESTION_TYPE_FK_ID, questionTypeID);
        cv.put(cSQLDBHelper.KEY_CHOICE_FK_ID, choiceID);

        // insert project details
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblCHOICESET, null, cv) < 0) {
                return false;
            }
        } catch (Exception e) {
            Log.d(TAG, "Exception in importing CHOICE SET from Excel: " + e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }

    /* #################################### QUESTION FUNCTIONS ###################################*/

    /**
     * This function extracts question data from excel and adds it to the database.
     *
     * @return
     */
    @Override
    public boolean addQuestionFromExcel() {
        Workbook workbook = excelHelper.getWorkbook();
        Sheet questionSheet = workbook.getSheet(cExcelHelper.SHEET_tblQUESTION);

        if (questionSheet == null) {
            return false;
        }

        for (Iterator<Row> ritQuestion = questionSheet.iterator(); ritQuestion.hasNext(); ) {
            Row cRow = ritQuestion.next();

            //just skip the row if row number is 0
            if (cRow.getRowNum() == 0) {
                continue;
            }

            cQuestionModel questionModel = new cQuestionModel();

            questionModel.setQuestionID((int) cRow.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            questionModel.setLogFrameID((int) cRow.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            questionModel.setQuestionTypeID((int) cRow.getCell(2, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            questionModel.setQuestionGroupID((int) cRow.getCell(3, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            questionModel.setName(cRow.getCell(4, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
            questionModel.setDescription(cRow.getCell(5, Row.CREATE_NULL_AS_BLANK).getStringCellValue());

            /* get evaluation question type
            Sheet evaluationSheet = workbook.getSheet(cExcelHelper.SHEET_tblEVALUATION_QUESTION);
            int questionID, evaluationID = -1;

            for (Iterator<Row> ritEvaluation = evaluationSheet.iterator(); ritEvaluation.hasNext(); ) {
                Row rowEvaluation = ritEvaluation.next();

                //just skip the row if row number is 0
                if (rowEvaluation.getRowNum() == 0) {
                    continue;
                }

                questionID = (int) rowEvaluation.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                if (questionModel.getQuestionID() == questionID) {
                    evaluationID = questionModel.getQuestionID();
                    break;
                }
            }*/

            /* get monitoring question type
            Sheet monitoringSheet = workbook.getSheet(cExcelHelper.SHEET_tblMONITORING_QUESTION);
            int monitoringID = -1;

            for (Iterator<Row> ritMonitoring = monitoringSheet.iterator(); ritMonitoring.hasNext(); ) {
                Row rowMonitoring = ritMonitoring.next();

                //just skip the row if row number is 0
                if (rowMonitoring.getRowNum() == 0) {
                    continue;
                }

                questionID = (int) rowMonitoring.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                if (questionModel.getQuestionID() == questionID) {
                    monitoringID = questionModel.getQuestionID();
                    break;
                }
            }

            if (!addQuestionFromExcel(questionModel, evaluationID, monitoringID)) {
                return false;
            }*/
        }

        return true;
    }

    /**
     * This function adds question data to the database.
     *
     * @param questionModel
     * @param evaluationID
     * @param monitoringID
     * @return
     */
    public boolean addQuestionFromExcel(cQuestionModel questionModel,
                                        int evaluationID, int monitoringID) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_ID, questionModel.getQuestionID());
        cv.put(cSQLDBHelper.KEY_LOGFRAME_FK_ID, questionModel.getLogFrameID());
        cv.put(cSQLDBHelper.KEY_QUESTION_TYPE_FK_ID, questionModel.getQuestionTypeID());
        cv.put(cSQLDBHelper.KEY_QUESTION_GROUPING_FK_ID, questionModel.getQuestionGroupID());
        cv.put(cSQLDBHelper.KEY_NAME, questionModel.getName());
        cv.put(cSQLDBHelper.KEY_DESCRIPTION, questionModel.getDescription());


        try {
            /* insert question details */
            if (db.insert(cSQLDBHelper.TABLE_tblQUESTION, null, cv) < 0) {
                return false;
            }

            /* insert primitive type in the database
            if (evaluationID > -1) {
                ContentValues cvEvaluation = new ContentValues();
                cvEvaluation.put(cSQLDBHelper.KEY_QUESTION_FK_ID, evaluationID);

                if (db.insert(cSQLDBHelper.TABLE_tblEVALUATION_QUESTION, null,
                        cvEvaluation) < 0) {
                    return false;
                }
            }*/

            /* insert primitive type in the database
            if (monitoringID > -1) {
                ContentValues cvMonitoring = new ContentValues();
                cvMonitoring.put(cSQLDBHelper.KEY_QUESTION_FK_ID, monitoringID);

                if (db.insert(cSQLDBHelper.TABLE_tblMONITORING_QUESTION, null,
                        cvMonitoring) < 0) {
                    return false;
                }
            }*/

        } catch (Exception e) {
            Log.d(TAG, "Exception in importing QUESTION from Excel: " + e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }


    /* ######################################## LOGFRAME FUNCTIONS ########################################*/

    /**
     * This function extracts and adds the logframe data from excel.
     *
     * @return
     */
    @Override
    public boolean addLogFrameFromExcel() {
        Workbook workbook = excelHelper.getWorkbook();
        Sheet logFrameSheet = workbook.getSheet(cExcelHelper.SHEET_tblLOGFRAME);
        if (logFrameSheet == null) {
            return false;
        }

        for (Iterator<Row> ritLogFrame = logFrameSheet.iterator(); ritLogFrame.hasNext(); ) {
            Row cRow = ritLogFrame.next();

            //just skip the row if row number is 0
            if (cRow.getRowNum() == 0) {
                continue;
            }

            cLogFrameModel logFrameModel = new cLogFrameModel();

            logFrameModel.setLogFrameID((int) cRow.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            logFrameModel.setName(cRow.getCell(1, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
            logFrameModel.setDescription(cRow.getCell(2, Row.CREATE_NULL_AS_BLANK).getStringCellValue());

            Set<Integer> subLogFrameDomainSet = new HashSet<>();
            int parentID, childID;

            Sheet logFrameTreeSheet = workbook.getSheet(cExcelHelper.SHEET_tblLOGFRAMETREE);
            for (Iterator<Row> ritLogframeTree = logFrameTreeSheet.iterator(); ritLogframeTree.hasNext(); ) {
                Row rowLogFrameTree = ritLogframeTree.next();

                //just skip the row if row number is 0
                if (rowLogFrameTree.getRowNum() == 0) {
                    continue;
                }

                parentID = (int) rowLogFrameTree.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                if (logFrameModel.getLogFrameID() == parentID) {
                    childID = (int) rowLogFrameTree.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                    subLogFrameDomainSet.add(childID);
                }
            }

            if (!addLogFrameFromExcel(logFrameModel, subLogFrameDomainSet)) {
                return false;
            }
        }

        return true;
    }

    /**
     * This function adds the logframe and sub-logframe data from excel.
     *
     * @param logFrameModel
     * @param subLogFrameSet
     * @return
     */
    public boolean addLogFrameFromExcel(cLogFrameModel logFrameModel, Set<Integer> subLogFrameSet) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_ID, logFrameModel.getLogFrameID());
        cv.put(cSQLDBHelper.KEY_NAME, logFrameModel.getName());
        cv.put(cSQLDBHelper.KEY_DESCRIPTION, logFrameModel.getDescription());

        // insert LogFrame record
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblLOGFRAME, null, cv) < 0) {
                return false;
            }

            // add subLogFrame
            for (int childID : subLogFrameSet) {
                if (addLogFrameTree(logFrameModel.getLogFrameID(), childID))
                    continue;
                else
                    return false;
            }
        } catch (Exception e) {
            Log.d(TAG, "Exception in importing LOGFRAME from Excel: " + e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }

    /**
     * This function adds the sub-logframe data from excel.
     *
     * @param parentID
     * @param childID
     * @return
     */
    public boolean addLogFrameTree(int parentID, int childID) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(cSQLDBHelper.KEY_PARENT_FK_ID, parentID);
        cv.put(cSQLDBHelper.KEY_CHILD_FK_ID, childID);

        //Gson gson = new Gson();
        //Log.d(TAG, gson.toJson(parentID)+" -> "+gson.toJson(childID));
        if (db.insert(cSQLDBHelper.TABLE_tblLOGFRAMETREE, null, cv) < 0) {
            return false;
        }

        return true;
    }

    /* ######################################## RAID FUNCTIONS ########################################*/

    /**
     * This function extracts raid data from excel and adds it to the database.
     *
     * @return
     */
    @Override
    public boolean addRaidFromExcel() {
        Workbook workbook = excelHelper.getWorkbook();
        Sheet RSheet = workbook.getSheet(cExcelHelper.SHEET_tblRAID);

        if (RSheet == null) {
            return false;
        }

        for (Iterator<Row> ritR = RSheet.iterator(); ritR.hasNext(); ) {
            Row cRow = ritR.next();

            //just skip the row if row number is 0
            if (cRow.getRowNum() == 0) {
                continue;
            }

            cRaidModel raidModel = new cRaidModel();

            raidModel.setRaidID((int) cRow.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            raidModel.setLogFrameID((int) cRow.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            raidModel.setName(cRow.getCell(2, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
            raidModel.setDescription(cRow.getCell(3, Row.CREATE_NULL_AS_BLANK).getStringCellValue());

            if (!addRaidFromExcel(raidModel)) {
                return false;
            }
        }

        return true;
    }

    /**
     * This function adds raid data to the database.
     *
     * @param raidModel
     * @return
     */
    public boolean addRaidFromExcel(cRaidModel raidModel) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_ID, raidModel.getRaidID());
        cv.put(cSQLDBHelper.KEY_LOGFRAME_FK_ID, raidModel.getLogFrameID());
        cv.put(cSQLDBHelper.KEY_NAME, raidModel.getName());
        cv.put(cSQLDBHelper.KEY_DESCRIPTION, raidModel.getDescription());

        // insert project details
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblRAID, null, cv) < 0) {
                return false;
            }
        } catch (Exception e) {
            Log.d(TAG, "Exception in importing RAID from Excel: " + e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }

    /* ######################################## IMPACT FUNCTIONS ########################################*/

    /**
     * This function extracts and adds the impact data from excel.
     *
     * @return
     */
    @Override
    public boolean addImpactFromExcel() {
        Workbook workbook = excelHelper.getWorkbook();
        Sheet impactSheet = workbook.getSheet(cExcelHelper.SHEET_tblIMPACT);

        if (impactSheet == null) {
            return false;
        }

        for (Iterator<Row> ritImpact = impactSheet.iterator(); ritImpact.hasNext(); ) {
            Row cRow = ritImpact.next();

            //just skip the row if row number is 0
            if (cRow.getRowNum() == 0) {
                continue;
            }

            cImpactModel impactModel = new cImpactModel();

            impactModel.setImpactID((int) cRow.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            impactModel.setParentID((int) cRow.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            impactModel.setLogFrameID((int) cRow.getCell(2, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            impactModel.setName(cRow.getCell(3, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
            impactModel.setDescription(cRow.getCell(4, Row.CREATE_NULL_AS_BLANK).getStringCellValue());

            /* get questions grouped by criteria of the impact */
            Map<Integer, Set<Pair<Integer, Integer>>> iqcMap = new HashMap<>();
            Set<Pair<Integer, Integer>> qcSet = new HashSet<>();
            int impactID, questionID, criteriaID;

            Sheet iqcSheet = workbook.getSheet(cExcelHelper.SHEET_tblIMPACT_QUESTION);
            for (Iterator<Row> ritIQC = iqcSheet.iterator(); ritIQC.hasNext(); ) {
                Row rowIQC = ritIQC.next();

                //just skip the row if row number is 0
                if (rowIQC.getRowNum() == 0) {
                    continue;
                }

                impactID = (int) rowIQC.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                if (impactModel.getImpactID() == impactID) {
                    questionID = (int) rowIQC.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                    criteriaID = (int) rowIQC.getCell(2, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                    qcSet.add(new Pair<>(questionID, criteriaID));
                }
            }

            /* get raids of the impact */
            Set<Pair<Integer, Integer>> raidSet = new HashSet<>();
            int raidID;
            Sheet impactRaidSheet = workbook.getSheet(cExcelHelper.SHEET_tblIMPACT_RAID);
            for (Iterator<Row> ritImpactRaid = impactRaidSheet.iterator(); ritImpactRaid.hasNext(); ) {
                Row rowImpactRaid = ritImpactRaid.next();

                //just skip the row if row number is 0
                if (rowImpactRaid.getRowNum() == 0) {
                    continue;
                }

                impactID = (int) rowImpactRaid.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                if (impactModel.getImpactID() == impactID) {
                    raidID = (int) rowImpactRaid.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                    raidSet.add(new Pair<>(raidID, impactID));
                }
            }

            iqcMap.put(impactModel.getImpactID(), qcSet);

            //Gson gson = new Gson();
            //Log.d(TAG, gson.toJson(raidSet));
            //Log.d(TAG, gson.toJson(iqcMap));

            if (!addImpactFromExcel(impactModel, iqcMap, raidSet)) {
                return false;
            }
        }

        return true;
    }

    /**
     * This function extracts and adds the impact data from excel.
     *
     * @param impactModel
     * @param iqcMap
     * @param raidSet
     * @return
     */
    public boolean addImpactFromExcel(cImpactModel impactModel,
                                      Map<Integer, Set<Pair<Integer, Integer>>> iqcMap,
                                      Set<Pair<Integer, Integer>> raidSet) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_ID, impactModel.getImpactID());
        cv.put(cSQLDBHelper.KEY_PARENT_FK_ID, impactModel.getParentID());
        cv.put(cSQLDBHelper.KEY_LOGFRAME_FK_ID, impactModel.getLogFrameID());
        cv.put(cSQLDBHelper.KEY_NAME, impactModel.getName());
        cv.put(cSQLDBHelper.KEY_DESCRIPTION, impactModel.getDescription());

        // insert project details
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblIMPACT, null, cv) < 0) {
                return false;
            }

            // add impact, question and criteria tuple
            for (Map.Entry<Integer, Set<Pair<Integer, Integer>>> iqcEntry : iqcMap.entrySet()) {
                int impactID = iqcEntry.getKey();
                for (Pair<Integer, Integer> qcPair : iqcEntry.getValue()) {
                    int questionID = qcPair.first;
                    int criteriaID = qcPair.second;

                    if (addImpactQuestionCriteria(impactID, questionID, criteriaID))
                        continue;
                    else
                        return false;
                }
            }

            // add raid
            for (Pair<Integer, Integer> pair : raidSet) {
                if (addImpactRaid(pair.first, pair.second))
                    continue;
                else
                    return false;
            }
        } catch (Exception e) {
            Log.d(TAG, "Exception in importing IMPACT from Excel: " + e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }

    /**
     * This function adds the impact, question and criteria to the database.
     *
     * @param impactID
     * @param questionID
     * @param criteriaID
     * @return
     */
    public boolean addImpactQuestionCriteria(int impactID, int questionID, int criteriaID) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(cSQLDBHelper.KEY_IMPACT_FK_ID, impactID);
        cv.put(cSQLDBHelper.KEY_QUESTION_FK_ID, questionID);
        cv.put(cSQLDBHelper.KEY_CRITERIA_FK_ID, criteriaID);

        if (db.insert(cSQLDBHelper.TABLE_tblIMPACT_QUESTION, null, cv) < 0) {
            return false;
        }

        return true;
    }

    /**
     * This function adds the impact and raid to the database.
     *
     * @param impactID
     * @param raidID
     * @return
     */
    public boolean addImpactRaid(int impactID, int raidID) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(cSQLDBHelper.KEY_IMPACT_FK_ID, impactID);
        cv.put(cSQLDBHelper.KEY_RAID_FK_ID, raidID);

        if (db.insert(cSQLDBHelper.TABLE_tblIMPACT_RAID, null, cv) < 0) {
            return false;
        }

        return true;
    }

    /* ######################################## OUTCOME FUNCTIONS ########################################*/

    /**
     * This function extracts and adds the outcome data from excel.
     *
     * @return
     */
    @Override
    public boolean addOutcomeFromExcel() {
        Workbook workbook = excelHelper.getWorkbook();
        Sheet outcomeSheet = workbook.getSheet(cExcelHelper.SHEET_tblOUTCOME);
        Gson gson = new Gson();
        if (outcomeSheet == null) {
            return false;
        }

        for (Iterator<Row> ritOutcome = outcomeSheet.iterator(); ritOutcome.hasNext(); ) {
            Row cRow = ritOutcome.next();

            //just skip the row if row number is 0
            if (cRow.getRowNum() == 0) {
                continue;
            }

            cOutcomeModel outcomeModel = new cOutcomeModel();

            outcomeModel.setOutcomeID((int) cRow.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            outcomeModel.setParentID((int) cRow.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            outcomeModel.setImpactID((int) cRow.getCell(2, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            outcomeModel.setLogFrameID((int) cRow.getCell(3, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            outcomeModel.setName(cRow.getCell(4, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
            outcomeModel.setDescription(cRow.getCell(5, Row.CREATE_NULL_AS_BLANK).getStringCellValue());

            /* get questions grouped by criteria of the outcome */
            Map<Integer, Set<Pair<Integer, Integer>>> oqcMap = new HashMap<>();
            Set<Pair<Integer, Integer>> qcSet = new HashSet<>();
            int outcomeID, questionID, criteriaID;

            Sheet oqcSheet = workbook.getSheet(cExcelHelper.SHEET_tblOUTCOME_QUESTION);
            for (Iterator<Row> ritOQC = oqcSheet.iterator(); ritOQC.hasNext(); ) {
                Row rowOQC = ritOQC.next();

                //just skip the row if row number is 0
                if (rowOQC.getRowNum() == 0) {
                    continue;
                }

                outcomeID = (int) rowOQC.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                if (outcomeModel.getOutcomeID() == outcomeID) {
                    questionID = (int) rowOQC.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                    criteriaID = (int) rowOQC.getCell(2, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                    qcSet.add(new Pair<>(questionID, criteriaID));
                }
            }
            oqcMap.put(outcomeModel.getOutcomeID(), qcSet);

            /* get impacts of the outcome for a sub-logframe */
            Map<Set<Pair<Integer, Integer>>, Pair<Integer, Integer>> soiMap = new HashMap<>();
            int parentID, childID, impactID;

            Sheet oiSheet = workbook.getSheet(cExcelHelper.SHEET_tblOUTCOME_IMPACT);
            for (Iterator<Row> ritOI = oiSheet.iterator(); ritOI.hasNext(); ) {
                Row rowOI = ritOI.next();

                //just skip the row if row number is 0
                if (rowOI.getRowNum() == 0) {
                    continue;
                }

                outcomeID = (int) rowOI.getCell(2, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                if (outcomeModel.getOutcomeID() == outcomeID) {
                    Set<Pair<Integer, Integer>> impactSet = new HashSet<>();
                    parentID = (int) rowOI.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                    childID = (int) rowOI.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                    impactID = (int) rowOI.getCell(3, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                    impactSet.add(new Pair(outcomeID, impactID));
                    soiMap.put(impactSet, new Pair<>(parentID, childID));
                }
            }

            /* get raids of the outcome */
            Set<Pair<Integer, Integer>> raidSet = new HashSet<>();
            int raidID;
            Sheet outcomeRaidSheet = workbook.getSheet(cExcelHelper.SHEET_tblOUTCOME_RAID);
            for (Iterator<Row> ritOutcomeRaid = outcomeRaidSheet.iterator(); ritOutcomeRaid.hasNext(); ) {
                Row rowOutcomeRaid = ritOutcomeRaid.next();

                //just skip the row if row number is 0
                if (rowOutcomeRaid.getRowNum() == 0) {
                    continue;
                }

                outcomeID = (int) rowOutcomeRaid.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                if (outcomeModel.getOutcomeID() == outcomeID) {
                    raidID = (int) rowOutcomeRaid.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                    raidSet.add(new Pair<>(outcomeID, raidID));
                }
            }


            //Log.d(TAG, gson.toJson(osiMap));
            //Log.d(TAG, gson.toJson(oqcMap));
            //Log.d(TAG, gson.toJson(raidSet));


            if (!addOutcomeFromExcel(outcomeModel, soiMap, oqcMap, raidSet)) {
                return false;
            }
        }

        return true;
    }

    /**
     *
     * @param outcomeModel
     * @param soiMap
     * @param oqcMap
     * @param raidSet
     * @return
     */
    public boolean addOutcomeFromExcel(cOutcomeModel outcomeModel,
                                       Map<Set<Pair<Integer, Integer>>, Pair<Integer, Integer>> soiMap,
                                       Map<Integer, Set<Pair<Integer, Integer>>> oqcMap,
                                       Set<Pair<Integer, Integer>> raidSet) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_ID, outcomeModel.getOutcomeID());
        cv.put(cSQLDBHelper.KEY_PARENT_FK_ID, outcomeModel.getParentID());
        cv.put(cSQLDBHelper.KEY_IMPACT_FK_ID, outcomeModel.getImpactID());
        cv.put(cSQLDBHelper.KEY_LOGFRAME_FK_ID, outcomeModel.getLogFrameID());
        cv.put(cSQLDBHelper.KEY_NAME, outcomeModel.getName());
        cv.put(cSQLDBHelper.KEY_DESCRIPTION, outcomeModel.getDescription());

        // insert project details
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblOUTCOME, null, cv) < 0) {
                return false;
            }

            // add sub-logFrame, outcome and impact tuple
            for (Map.Entry<Set<Pair<Integer, Integer>>, Pair<Integer, Integer>> soiEntry : soiMap.entrySet()) {
                for (Pair<Integer, Integer> oiPair : soiEntry.getKey()) {
                    int outcomeID = oiPair.first;
                    int impactID = oiPair.second;

                    Pair<Integer, Integer> sPair = soiEntry.getValue();
                    int parentID = sPair.first;
                    int childID = sPair.second;

                    if (addOutcomeImpact(parentID, childID, outcomeID, impactID))
                        continue;
                    else
                        return false;
                }
            }


            // add outcome, question and criteria tuple
            for (Map.Entry<Integer, Set<Pair<Integer, Integer>>> oqcEntry : oqcMap.entrySet()) {
                int impactID = oqcEntry.getKey();
                for (Pair<Integer, Integer> qcPair : oqcEntry.getValue()) {
                    int questionID = qcPair.first;
                    int criteriaID = qcPair.second;

                    if (addOutcomeQuestionCriteria(impactID, questionID, criteriaID))
                        continue;
                    else
                        return false;
                }
            }

            // add outcome raid
            for (Pair<Integer, Integer> pair : raidSet) {
                int raidID = pair.first;
                int outcomeID = pair.second;
                if (addOutcomeRaid(outcomeID, raidID))
                    continue;
                else
                    return false;
            }

        } catch (Exception e) {
            Log.d(TAG, "Exception in importing OUTCOME from Excel: " + e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }

    /**
     *
     * @param parentID
     * @param childID
     * @param outcomeID
     * @param impactID
     * @return
     */
    public boolean addOutcomeImpact(int parentID, int childID, int outcomeID, int impactID) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(cSQLDBHelper.KEY_PARENT_FK_ID, parentID);
        cv.put(cSQLDBHelper.KEY_CHILD_FK_ID, childID);
        cv.put(cSQLDBHelper.KEY_OUTCOME_FK_ID, outcomeID);
        cv.put(cSQLDBHelper.KEY_IMPACT_FK_ID, impactID);

        if (db.insert(cSQLDBHelper.TABLE_tblOUTCOME_IMPACT, null, cv) < 0) {
            return false;
        }

        return true;
    }

    /**
     *
     * @param outcomeID
     * @param questionID
     * @param criteriaID
     * @return
     */
    public boolean addOutcomeQuestionCriteria(int outcomeID, int questionID, int criteriaID) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(cSQLDBHelper.KEY_OUTCOME_FK_ID, outcomeID);
        cv.put(cSQLDBHelper.KEY_QUESTION_FK_ID, questionID);
        cv.put(cSQLDBHelper.KEY_CRITERIA_FK_ID, criteriaID);

        if (db.insert(cSQLDBHelper.TABLE_tblOUTCOME_QUESTION, null, cv) < 0) {
            return false;
        }

        return true;
    }

    /**
     *
     * @param outcomeID
     * @param raidID
     * @return
     */
    public boolean addOutcomeRaid(int outcomeID, int raidID) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(cSQLDBHelper.KEY_OUTCOME_FK_ID, outcomeID);
        cv.put(cSQLDBHelper.KEY_RAID_FK_ID, raidID);

        if (db.insert(cSQLDBHelper.TABLE_tblOUTCOME_RAID, null, cv) < 0) {
            return false;
        }

        return true;
    }

    /* ######################################## OUTPUT FUNCTIONS ########################################*/

    /**
     * This function extracts and adds the output data from excel.
     *
     * @return
     */
    @Override
    public boolean addOutputFromExcel() {
        Workbook workbook = excelHelper.getWorkbook();
        Sheet outputSheet = workbook.getSheet(cExcelHelper.SHEET_tblOUTPUT);

        if (outputSheet == null) {
            return false;
        }

        for (Iterator<Row> ritOutput = outputSheet.iterator(); ritOutput.hasNext(); ) {
            Row cRow = ritOutput.next();

            //just skip the row if row number is 0
            if (cRow.getRowNum() == 0) {
                continue;
            }

            cOutputModel outputModel = new cOutputModel();

            outputModel.setOutputID((int) cRow.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            outputModel.setParentID((int) cRow.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            outputModel.setOutcomeID((int) cRow.getCell(2, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            outputModel.setLogFrameID((int) cRow.getCell(3, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            outputModel.setName(cRow.getCell(4, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
            outputModel.setDescription(cRow.getCell(5, Row.CREATE_NULL_AS_BLANK).getStringCellValue());

            /* get questions grouped by criteria of the output */
            Map<Integer, Set<Pair<Integer, Integer>>> oqcMap = new HashMap<>();
            Set<Pair<Integer, Integer>> qcSet = new HashSet<>();
            int outputID, questionID, criteriaID;

            Sheet oqcSheet = workbook.getSheet(cExcelHelper.SHEET_tblOUTPUT_QUESTION);
            for (Iterator<Row> ritOQC = oqcSheet.iterator(); ritOQC.hasNext(); ) {
                Row rowOQC = ritOQC.next();

                //just skip the row if row number is 0
                if (rowOQC.getRowNum() == 0) {
                    continue;
                }

                outputID = (int) rowOQC.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                if (outputModel.getOutputID() == outputID) {
                    questionID = (int) rowOQC.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                    criteriaID = (int) rowOQC.getCell(2, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                    qcSet.add(new Pair<>(questionID, criteriaID));
                }
            }
            oqcMap.put(outputModel.getOutputID(), qcSet);

            /* get outcomes of the output for a sub-logframe */
            Map<Set<Pair<Integer, Integer>>, Pair<Integer, Integer>> sooMap = new HashMap<>();
            int parentID, childID, outcomeID;

            Sheet ooSheet = workbook.getSheet(cExcelHelper.SHEET_tblOUTPUT_OUTCOME);
            for (Iterator<Row> ritOO = ooSheet.iterator(); ritOO.hasNext(); ) {
                Row rowOO = ritOO.next();

                //just skip the row if row number is 0
                if (rowOO.getRowNum() == 0) {
                    continue;
                }

                outputID = (int) rowOO.getCell(2, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                if (outputModel.getOutputID() == outputID) {
                    Set<Pair<Integer, Integer>> impactSet = new HashSet<>();
                    parentID = (int) rowOO.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                    childID = (int) rowOO.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                    outcomeID = (int) rowOO.getCell(3, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                    impactSet.add(new Pair(outputID, outcomeID));
                    sooMap.put(impactSet, new Pair<>(parentID, childID));
                }
            }

            /* get raids of the output */
            Set<Pair<Integer, Integer>> raidSet = new HashSet<>();
            int raidID;
            Sheet outputRaidSheet = workbook.getSheet(cExcelHelper.SHEET_tblOUTPUT_RAID);
            for (Iterator<Row> ritOutputRaid = outputRaidSheet.iterator(); ritOutputRaid.hasNext(); ) {
                Row rowOutputRaid = ritOutputRaid.next();

                //just skip the row if row number is 0
                if (rowOutputRaid.getRowNum() == 0) {
                    continue;
                }

                outputID = (int) rowOutputRaid.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                if (outputModel.getOutputID() == outputID) {
                    raidID = (int) rowOutputRaid.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                    raidSet.add(new Pair<>(outputID, raidID));
                }
            }

            //Log.d(TAG, gson.toJson(sooMap));
            //Log.d(TAG, gson.toJson(oqcMap));
            //Log.d(TAG, gson.toJson(raidSet));

            if (!addOutputFromExcel(outputModel, sooMap, oqcMap, raidSet)) {
                return false;
            }
        }

        return true;
    }

    /**
     *
     * @param outputModel
     * @param sooMap
     * @param oqcMap
     * @param raidSet
     * @return
     */
    public boolean addOutputFromExcel(cOutputModel outputModel,
                                      Map<Set<Pair<Integer, Integer>>, Pair<Integer, Integer>> sooMap,
                                      Map<Integer, Set<Pair<Integer, Integer>>> oqcMap,
                                      Set<Pair<Integer, Integer>> raidSet) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_ID, outputModel.getOutputID());
        cv.put(cSQLDBHelper.KEY_PARENT_FK_ID, outputModel.getParentID());
        cv.put(cSQLDBHelper.KEY_OUTCOME_FK_ID, outputModel.getOutcomeID());
        cv.put(cSQLDBHelper.KEY_LOGFRAME_FK_ID, outputModel.getLogFrameID());
        cv.put(cSQLDBHelper.KEY_NAME, outputModel.getName());
        cv.put(cSQLDBHelper.KEY_DESCRIPTION, outputModel.getDescription());

        // insert output details
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblOUTPUT, null, cv) < 0) {
                return false;
            }

            // add sub-logFrame, outcome and impact tuple
            for (Map.Entry<Set<Pair<Integer, Integer>>, Pair<Integer, Integer>> sooEntry : sooMap.entrySet()) {
                for (Pair<Integer, Integer> ooPair : sooEntry.getKey()) {
                    int outputID = ooPair.first;
                    int outcomeID = ooPair.second;

                    Pair<Integer, Integer> sPair = sooEntry.getValue();
                    int parentID = sPair.first;
                    int childID = sPair.second;

                    if (addOutputOutcome(parentID, childID, outputID, outcomeID))
                        continue;
                    else
                        return false;
                }
            }

            // add output, question and criteria tuple
            for (Map.Entry<Integer, Set<Pair<Integer, Integer>>> oqcEntry : oqcMap.entrySet()) {
                int outputID = oqcEntry.getKey();
                for (Pair<Integer, Integer> qcPair : oqcEntry.getValue()) {
                    int questionID = qcPair.first;
                    int criteriaID = qcPair.second;

                    if (addOutputQuestionCriteria(outputID, questionID, criteriaID))
                        continue;
                    else
                        return false;
                }
            }

            // add output raid
            for (Pair<Integer, Integer> pair : raidSet) {
                int raidID = pair.first;
                int outputID = pair.second;
                if (addOutputRaid(outputID, raidID))
                    continue;
                else
                    return false;
            }

        } catch (Exception e) {
            Log.d(TAG, "Exception in importing OUTPUT from Excel: " + e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }

    /**
     *
     * @param parentID
     * @param childID
     * @param outputID
     * @param outcomeID
     * @return
     */
    public boolean addOutputOutcome(int parentID, int childID, int outputID, int outcomeID) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(cSQLDBHelper.KEY_PARENT_FK_ID, parentID);
        cv.put(cSQLDBHelper.KEY_CHILD_FK_ID, childID);
        cv.put(cSQLDBHelper.KEY_OUTPUT_FK_ID, outputID);
        cv.put(cSQLDBHelper.KEY_OUTCOME_FK_ID, outcomeID);

        if (db.insert(cSQLDBHelper.TABLE_tblOUTPUT_OUTCOME, null, cv) < 0) {
            return false;
        }

        return true;
    }

    /**
     *
     * @param outputID
     * @param questionID
     * @param criteriaID
     * @return
     */
    public boolean addOutputQuestionCriteria(int outputID, int questionID, int criteriaID) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(cSQLDBHelper.KEY_OUTPUT_FK_ID, outputID);
        cv.put(cSQLDBHelper.KEY_QUESTION_FK_ID, questionID);
        cv.put(cSQLDBHelper.KEY_CRITERIA_FK_ID, criteriaID);

        if (db.insert(cSQLDBHelper.TABLE_tblOUTPUT_QUESTION, null, cv) < 0) {
            return false;
        }

        return true;
    }

    /**
     *
     * @param outputID
     * @param raidID
     * @return
     */
    public boolean addOutputRaid(int outputID, int raidID) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(cSQLDBHelper.KEY_OUTPUT_FK_ID, outputID);
        cv.put(cSQLDBHelper.KEY_RAID_FK_ID, raidID);

        if (db.insert(cSQLDBHelper.TABLE_tblOUTPUT_RAID, null, cv) < 0) {
            return false;
        }

        return true;
    }

    /* ######################################## ACTIVITY FUNCTIONS ########################################*/

    /**
     * This function extracts and adds the activity data from excel.
     *
     * @return
     */
    @Override
    public boolean addActivityFromExcel() {
        Workbook workbook = excelHelper.getWorkbook();
        Sheet activitySheet = workbook.getSheet(cExcelHelper.SHEET_tblACTIVITY);

        if (activitySheet == null) {
            return false;
        }

        for (Iterator<Row> ritActivity = activitySheet.iterator(); ritActivity.hasNext(); ) {
            Row cRow = ritActivity.next();

            //just skip the row if row number is 0
            if (cRow.getRowNum() == 0) {
                continue;
            }

            cActivityModel activityModel = new cActivityModel();

            activityModel.setActivityID((int) cRow.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            activityModel.setParentID((int) cRow.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            activityModel.setOutputID((int) cRow.getCell(2, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            activityModel.setLogFrameID((int) cRow.getCell(3, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            activityModel.setName(cRow.getCell(4, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
            activityModel.setDescription(cRow.getCell(5, Row.CREATE_NULL_AS_BLANK).getStringCellValue());

            /* get preceding activities of the activity */
            Set<Pair<Integer, Integer>> precedingSet = new HashSet<>();
            int precedingActivityID, succeedingActivityID;
            Sheet precedingActivitySheet = workbook.getSheet(cExcelHelper.SHEET_tblPRECEDINGACTIVITY);
            for (Iterator<Row> ritPrecedingActivity = precedingActivitySheet.iterator(); ritPrecedingActivity.hasNext(); ) {
                Row rowPrecedingActivity = ritPrecedingActivity.next();

                //just skip the row if row number is 0
                if (rowPrecedingActivity.getRowNum() == 0) {
                    continue;
                }

                precedingActivityID = (int) rowPrecedingActivity.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                if (activityModel.getActivityID() == precedingActivityID) {
                    succeedingActivityID = (int) rowPrecedingActivity.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                    precedingSet.add(new Pair<>(precedingActivityID, succeedingActivityID));
                }
            }

            /* get questions grouped by criteria of the output */
            Map<Integer, Set<Pair<Integer, Integer>>> aqcMap = new HashMap<>();
            Set<Pair<Integer, Integer>> qcSet = new HashSet<>();
            int activityID, questionID, criteriaID;

            Sheet aqcSheet = workbook.getSheet(cExcelHelper.SHEET_tblACTIVITY_QUESTION);
            for (Iterator<Row> ritAQC = aqcSheet.iterator(); ritAQC.hasNext(); ) {
                Row rowAQC = ritAQC.next();

                //just skip the row if row number is 0
                if (rowAQC.getRowNum() == 0) {
                    continue;
                }

                activityID = (int) rowAQC.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                if (activityModel.getActivityID() == activityID) {
                    questionID = (int) rowAQC.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                    criteriaID = (int) rowAQC.getCell(2, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                    qcSet.add(new Pair<>(questionID, criteriaID));
                }
            }
            aqcMap.put(activityModel.getOutputID(), qcSet);

            /* get outputs of the activity for a sub-logframe */
            Map<Set<Pair<Integer, Integer>>, Pair<Integer, Integer>> saoMap = new HashMap<>();
            int parentID, childID, outputID;

            Sheet aoSheet = workbook.getSheet(cExcelHelper.SHEET_tblACTIVITY_OUTPUT);
            for (Iterator<Row> ritAO = aoSheet.iterator(); ritAO.hasNext(); ) {
                Row rowAO = ritAO.next();

                //just skip the row if row number is 0
                if (rowAO.getRowNum() == 0) {
                    continue;
                }

                activityID = (int) rowAO.getCell(2, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                if (activityModel.getActivityID() == activityID) {
                    Set<Pair<Integer, Integer>> AOSet = new HashSet<>();
                    parentID = (int) rowAO.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                    childID = (int) rowAO.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                    outputID = (int) rowAO.getCell(3, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                    AOSet.add(new Pair(activityID, outputID));
                    saoMap.put(AOSet, new Pair<>(parentID, childID));
                }
            }

            /* get raids of the output */
            Set<Pair<Integer, Integer>> raidSet = new HashSet<>();
            int raidID;
            Sheet activityRaidSheet = workbook.getSheet(cExcelHelper.SHEET_tblACTIVITY_RAID);
            for (Iterator<Row> ritActivityRaid = activityRaidSheet.iterator(); ritActivityRaid.hasNext(); ) {
                Row rowActivityRaid = ritActivityRaid.next();

                //just skip the row if row number is 0
                if (rowActivityRaid.getRowNum() == 0) {
                    continue;
                }

                activityID = (int) rowActivityRaid.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                if (activityModel.getActivityID() == activityID) {
                    raidID = (int) rowActivityRaid.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                    raidSet.add(new Pair<>(activityID, raidID));
                }
            }

            //Log.d(TAG, gson.toJson(precedingSet));
            //Log.d(TAG, gson.toJson(saoMap));
            //Log.d(TAG, gson.toJson(oqcMap));
            //Log.d(TAG, gson.toJson(raidSet));

            if (!addActivityFromExcel(activityModel, precedingSet, saoMap, aqcMap, raidSet)) {
                return false;
            }
        }

        return true;
    }

    /**
     *
     * @param activityModel
     * @param precedingSet
     * @param saoMap
     * @param aqcMap
     * @param raidSet
     * @return
     */
    public boolean addActivityFromExcel(cActivityModel activityModel,
                                        Set<Pair<Integer, Integer>> precedingSet,
                                        Map<Set<Pair<Integer, Integer>>, Pair<Integer, Integer>> saoMap,
                                        Map<Integer, Set<Pair<Integer, Integer>>> aqcMap,
                                        Set<Pair<Integer, Integer>> raidSet) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_ID, activityModel.getActivityID());
        cv.put(cSQLDBHelper.KEY_PARENT_FK_ID, activityModel.getParentID());
        cv.put(cSQLDBHelper.KEY_OUTPUT_FK_ID, activityModel.getOutputID());
        cv.put(cSQLDBHelper.KEY_LOGFRAME_FK_ID, activityModel.getLogFrameID());
        cv.put(cSQLDBHelper.KEY_NAME, activityModel.getName());
        cv.put(cSQLDBHelper.KEY_DESCRIPTION, activityModel.getDescription());

        // insert activity details
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblACTIVITY, null, cv) < 0) {
                return false;
            }

            // add preceding activity
            for (Pair<Integer, Integer> pair : precedingSet) {
                int succeedingID = pair.first;
                int precedingID = pair.second;
                if (addPrecedingActivity(succeedingID, precedingID))
                    continue;
                else
                    return false;
            }

            // add sub-logFrame, activity and output tuple
            for (Map.Entry<Set<Pair<Integer, Integer>>, Pair<Integer, Integer>> saoEntry : saoMap.entrySet()) {
                for (Pair<Integer, Integer> aoPair : saoEntry.getKey()) {
                    int activityID = aoPair.first;
                    int outputID = aoPair.second;

                    Pair<Integer, Integer> sPair = saoEntry.getValue();
                    int parentID = sPair.first;
                    int childID = sPair.second;

                    if (addActivityOutput(parentID, childID, activityID, outputID))
                        continue;
                    else
                        return false;
                }
            }

            // add activity, question and criteria tuple
            for (Map.Entry<Integer, Set<Pair<Integer, Integer>>> aqcEntry : aqcMap.entrySet()) {
                int activityID = aqcEntry.getKey();
                for (Pair<Integer, Integer> qcPair : aqcEntry.getValue()) {
                    int questionID = qcPair.first;
                    int criteriaID = qcPair.second;

                    if (addActivityQuestionCriteria(activityID, questionID, criteriaID))
                        continue;
                    else
                        return false;
                }
            }

            // add activity raid
            for (Pair<Integer, Integer> pair : raidSet) {
                int raidID = pair.first;
                int activityID = pair.second;
                if (addActivityRaid(activityID, raidID))
                    continue;
                else
                    return false;
            }
        } catch (Exception e) {
            Log.d(TAG, "Exception in importing ACTIVITY from Excel: " + e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }

    /**
     *
     * @param activityID
     * @param precedingID
     * @return
     */
    public boolean addPrecedingActivity(int activityID, int precedingID) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(cSQLDBHelper.KEY_ACTIVITY_FK_ID, activityID);
        cv.put(cSQLDBHelper.KEY_PRECEDING_ACTIVITY_FK_ID, precedingID);

        if (db.insert(cSQLDBHelper.TABLE_tblPRECEDINGACTIVITY, null, cv) < 0) {
            return false;
        }

        return true;
    }

    /**
     *
     * @param parentID
     * @param childID
     * @param activityID
     * @param outputID
     * @return
     */
    public boolean addActivityOutput(int parentID, int childID, int activityID, int outputID) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(cSQLDBHelper.KEY_PARENT_FK_ID, parentID);
        cv.put(cSQLDBHelper.KEY_CHILD_FK_ID, childID);
        cv.put(cSQLDBHelper.KEY_ACTIVITY_FK_ID, activityID);
        cv.put(cSQLDBHelper.KEY_OUTPUT_FK_ID, outputID);

        if (db.insert(cSQLDBHelper.TABLE_tblACTIVITY_OUTPUT, null, cv) < 0) {
            return false;
        }

        return true;
    }

    /**
     *
     * @param activityID
     * @param questionID
     * @param criteriaID
     * @return
     */
    public boolean addActivityQuestionCriteria(int activityID, int questionID, int criteriaID) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(cSQLDBHelper.KEY_ACTIVITY_FK_ID, activityID);
        cv.put(cSQLDBHelper.KEY_QUESTION_FK_ID, questionID);
        cv.put(cSQLDBHelper.KEY_CRITERIA_FK_ID, criteriaID);

        if (db.insert(cSQLDBHelper.TABLE_tblACTIVITY_QUESTION, null, cv) < 0) {
            return false;
        }

        return true;
    }

    /**
     *
     * @param activityID
     * @param raidID
     * @return
     */
    public boolean addActivityRaid(int activityID, int raidID) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(cSQLDBHelper.KEY_ACTIVITY_FK_ID, activityID);
        cv.put(cSQLDBHelper.KEY_RAID_FK_ID, raidID);

        if (db.insert(cSQLDBHelper.TABLE_tblACTIVITY_RAID, null, cv) < 0) {
            return false;
        }

        return true;
    }

    public boolean deleteActivities() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblACTIVITY, null, null);

        // close the database connection
        db.close();

        return result > -1;
    }

    /*###################################### INPUT FUNCTIONS #####################################*/

    /**
     * This function extracts and adds the input data from excel.
     *
     * @return
     */
    @Override
    public boolean addInputFromExcel() {
        Workbook workbook = excelHelper.getWorkbook();
        Sheet inputSheet = workbook.getSheet(cExcelHelper.SHEET_tblINPUT);

        if (inputSheet == null) {
            return false;
        }

        for (Iterator<Row> ritInput = inputSheet.iterator(); ritInput.hasNext(); ) {
            Row cRow = ritInput.next();

            //just skip the row if row number is 0
            if (cRow.getRowNum() == 0) {
                continue;
            }

            cInputModel inputModel = new cInputModel();

            inputModel.setInputID((int) cRow.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            inputModel.setActivityID((int) cRow.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            inputModel.setLogFrameID((int) cRow.getCell(2, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            inputModel.setName(cRow.getCell(3, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
            inputModel.setDescription(cRow.getCell(4, Row.CREATE_NULL_AS_BLANK).getStringCellValue());

            /* get questions grouped by criteria of the input */
            Map<Integer, Set<Pair<Integer, Integer>>> iqcMap = new HashMap<>();
            Set<Pair<Integer, Integer>> qcSet = new HashSet<>();
            int inputID, questionID, criteriaID;

            Sheet iqcSheet = workbook.getSheet(cExcelHelper.SHEET_tblINPUT_QUESTION);
            for (Iterator<Row> ritIQC = iqcSheet.iterator(); ritIQC.hasNext(); ) {
                Row rowIQC = ritIQC.next();

                //just skip the row if row number is 0
                if (rowIQC.getRowNum() == 0) {
                    continue;
                }

                inputID = (int) rowIQC.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                if (inputModel.getInputID() == inputID) {
                    questionID = (int) rowIQC.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                    criteriaID = (int) rowIQC.getCell(2, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                    qcSet.add(new Pair<>(questionID, criteriaID));
                }
            }
            iqcMap.put(inputModel.getInputID(), qcSet);

            if (!addInputFromExcel(inputModel, iqcMap)) {
                return false;
            }
        }

        return true;
    }

    /**
     *
     * @param inputModel
     * @param iqcMap
     * @return
     */
    public boolean addInputFromExcel(cInputModel inputModel,
                                     Map<Integer, Set<Pair<Integer, Integer>>> iqcMap) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_ID, inputModel.getInputID());
        cv.put(cSQLDBHelper.KEY_ACTIVITY_FK_ID, inputModel.getActivityID());
        cv.put(cSQLDBHelper.KEY_LOGFRAME_FK_ID, inputModel.getLogFrameID());
        cv.put(cSQLDBHelper.KEY_NAME, inputModel.getName());
        cv.put(cSQLDBHelper.KEY_DESCRIPTION, inputModel.getDescription());

        // insert project details
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblINPUT, null, cv) < 0) {
                return false;
            }

            // add activity, question and criteria tuple
            for (Map.Entry<Integer, Set<Pair<Integer, Integer>>> iqcEntry : iqcMap.entrySet()) {
                int inputID = iqcEntry.getKey();
                for (Pair<Integer, Integer> qcPair : iqcEntry.getValue()) {
                    int questionID = qcPair.first;
                    int criteriaID = qcPair.second;

                    if (addInputQuestionCriteria(inputID, questionID, criteriaID))
                        continue;
                    else
                        return false;
                }
            }

        } catch (Exception e) {
            Log.d(TAG, "Exception in importing INPUT from Excel: " + e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }

    /**
     *
     * @param inputID
     * @param questionID
     * @param criteriaID
     * @return
     */
    public boolean addInputQuestionCriteria(int inputID, int questionID, int criteriaID) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(cSQLDBHelper.KEY_INPUT_FK_ID, inputID);
        cv.put(cSQLDBHelper.KEY_QUESTION_FK_ID, questionID);
        cv.put(cSQLDBHelper.KEY_CRITERIA_FK_ID, criteriaID);

        if (db.insert(cSQLDBHelper.TABLE_tblINPUT_QUESTION, null, cv) < 0) {
            return false;
        }

        return true;
    }
}
