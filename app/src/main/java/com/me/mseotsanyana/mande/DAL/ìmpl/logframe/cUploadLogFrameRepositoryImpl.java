package com.me.mseotsanyana.mande.DAL.ìmpl.logframe;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.util.Pair;

import com.google.gson.Gson;
import com.me.mseotsanyana.mande.BLL.repository.logframe.iUploadLogFrameRepository;
import com.me.mseotsanyana.mande.DAL.model.logframe.cActivityPlanningModel;
import com.me.mseotsanyana.mande.DAL.model.evaluator.cEvaluationCriteriaModel;
import com.me.mseotsanyana.mande.DAL.model.logframe.cQuestionGroupingModel;
import com.me.mseotsanyana.mande.DAL.model.logframe.cQuestionModel;
import com.me.mseotsanyana.mande.DAL.model.logframe.cQuestionTypeModel;
import com.me.mseotsanyana.mande.DAL.model.logframe.cActivityModel;
import com.me.mseotsanyana.mande.DAL.model.logframe.cImpactModel;
import com.me.mseotsanyana.mande.DAL.model.logframe.cInputModel;
import com.me.mseotsanyana.mande.DAL.model.logframe.cLogFrameModel;
import com.me.mseotsanyana.mande.DAL.model.logframe.cOutcomeModel;
import com.me.mseotsanyana.mande.DAL.model.logframe.cOutputModel;
import com.me.mseotsanyana.mande.DAL.model.logframe.cRaidModel;
import com.me.mseotsanyana.mande.DAL.model.awpb.cResourceModel;
import com.me.mseotsanyana.mande.DAL.model.logframe.cResourceTypeModel;
import com.me.mseotsanyana.mande.DAL.storage.database.cSQLDBHelper;
import com.me.mseotsanyana.mande.DAL.storage.excel.cExcelHelper;
import com.me.mseotsanyana.mande.UTIL.cConstant;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class cUploadLogFrameRepositoryImpl implements iUploadLogFrameRepository {
    private static SimpleDateFormat sdf = cConstant.FORMAT_DATE;
    private static String TAG = cUploadLogFrameRepositoryImpl.class.getSimpleName();

    /* an object of the database helper */
    private cSQLDBHelper dbHelper;
    /* an object of the excel helper */
    private cExcelHelper excelHelper;

    public cUploadLogFrameRepositoryImpl(Context context) {
        dbHelper = new cSQLDBHelper(context);
        excelHelper = new cExcelHelper(context);
    }

    /* ######################################## LOGFRAME FUNCTIONS ########################################*/

    /**
     * This function extracts and adds the logframe data from excel.
     *
     * @return
     */
    @Override
    public boolean addLogFrameFromExcel() {
        Workbook workbook = excelHelper.getWorkbookLOGFRAME();
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
            logFrameModel.setOrganizationID((int) cRow.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            logFrameModel.setName(cRow.getCell(2, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
            logFrameModel.setDescription(cRow.getCell(3, Row.CREATE_NULL_AS_BLANK).getStringCellValue());

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
        cv.put(cSQLDBHelper.KEY_ORGANIZATION_FK_ID, logFrameModel.getOrganizationID());
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

    /* #################################### CRITERIA FUNCTIONS ###################################*/

    /**
     * This function extracts criteria data from excel and adds it to the database.
     *
     * @return
     */
    @Override
    public boolean addCriteriaFromExcel() {
        Workbook workbook = excelHelper.getWorkbookLOGFRAME();
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

            cEvaluationCriteriaModel criteriaModel = new cEvaluationCriteriaModel();

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
    public boolean addQuestionCriteria(cEvaluationCriteriaModel criteriaModel) {
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
            if (db.insert(cSQLDBHelper.TABLE_tblEVALUATIONCRITERIA, null, cv) < 0) {
                return false;
            }
        } catch (Exception e) {
            Log.d(TAG, "Exception in importing CRITERIA from Excel: " + e.getMessage());
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
        Workbook workbook = excelHelper.getWorkbookLOGFRAME();
        Sheet QGSheet = workbook.getSheet(cExcelHelper.SHEET_tblQUESTIONGROUPING);

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
            if (db.insert(cSQLDBHelper.TABLE_tblQUESTIONGROUPING, null, cv) < 0) {
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
        Workbook workbook = excelHelper.getWorkbookLOGFRAME();
        Sheet QTSheet = workbook.getSheet(cExcelHelper.SHEET_tblQUESTIONTYPE);

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
            Sheet primitiveSheet = workbook.getSheet(cExcelHelper.SHEET_tblPRIMITIVETYPE);
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

            /* get array type */
            Sheet arraySheet = workbook.getSheet(cExcelHelper.SHEET_tblARRAYTYPE);
            int arrayTypeID = -1;//Pair<Integer, Integer> customTypePair = null;
            //int customID, optionID;
            for (Iterator<Row> ritArrayType = arraySheet.iterator(); ritArrayType.hasNext(); ) {
                Row rowArrayType = ritArrayType.next();

                //just skip the row if row number is 0
                if (rowArrayType.getRowNum() == 0) {
                    continue;
                }

                questionTypeID = (int) rowArrayType.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                if (questionTypeModel.getQuestionTypeID() == questionTypeID) {
                    //optionID = (int) rowCustom.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                    //customTypePair = new Pair<>(customID, optionID);
                    arrayTypeID = questionTypeID;
                    break;
                }
            }

            /* get matrix type */
            Sheet matrixSheet = workbook.getSheet(cExcelHelper.SHEET_tblMATRIXTYPE);
            int matrixTypeID = -1;//Pair<Integer, Integer> customTypePair = null;
            //int customID, optionID;
            for (Iterator<Row> ritMatrixType = matrixSheet.iterator(); ritMatrixType.hasNext(); ) {
                Row rowMatrixType = ritMatrixType.next();

                //just skip the row if row number is 0
                if (rowMatrixType.getRowNum() == 0) {
                    continue;
                }

                questionTypeID = (int) rowMatrixType.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                if (questionTypeModel.getQuestionTypeID() == questionTypeID) {
                    //optionID = (int) rowCustom.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                    //customTypePair = new Pair<>(customID, optionID);
                    matrixTypeID = questionTypeID;
                    break;
                }
            }

            /* get choice of the question type
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
            }*/

            if (!addQuestionTypeFromExcel(questionTypeModel, primitiveTypeID, arrayTypeID, matrixTypeID)) {
                return false;
            }
        }

        return true;
    }

    /**
     * This function adds question type to the database.
     *
     * @param questionTypeModel
     * @param primitiveTypeID
     * @param arrayTypeID
     * @param matrixTypeID
     * @return
     */
    public boolean addQuestionTypeFromExcel(cQuestionTypeModel questionTypeModel,
                                            int primitiveTypeID,
                                            int arrayTypeID,
                                            int matrixTypeID) {
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
            if (db.insert(cSQLDBHelper.TABLE_tblQUESTIONTYPE, null, cv) < 0) {
                return false;
            }

            /* insert primitive type in the database */
            if (primitiveTypeID > -1) {
                ContentValues cvPrimitive = new ContentValues();

                cvPrimitive.put(cSQLDBHelper.KEY_QUESTION_TYPE_FK_ID, primitiveTypeID);

                if (db.insert(cSQLDBHelper.TABLE_tblPRIMITIVETYPE, null,
                        cvPrimitive) < 0) {
                    return false;
                }
            }

            /* insert array type in the database */
            if (arrayTypeID > -1) {
                ContentValues cvArrayType = new ContentValues();

                cvArrayType.put(cSQLDBHelper.KEY_QUESTION_TYPE_FK_ID, arrayTypeID);

                if (db.insert(cSQLDBHelper.TABLE_tblARRAYTYPE, null,
                        cvArrayType) < 0) {
                    return false;
                }
            }

            /* insert matrix type in the database */
            if (matrixTypeID > -1) {
                ContentValues cvMatrixType = new ContentValues();

                cvMatrixType.put(cSQLDBHelper.KEY_QUESTION_TYPE_FK_ID, matrixTypeID);

                if (db.insert(cSQLDBHelper.TABLE_tblMATRIXTYPE, null,
                        cvMatrixType) < 0) {
                    return false;
                }
            }

        } catch (Exception e) {
            Log.d(TAG, "Exception in importing QUESTION TYPE from Excel: " + e.getMessage());
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
        Workbook workbook = excelHelper.getWorkbookLOGFRAME();
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
            }*/

            if (!addQuestionFromExcel(questionModel)) {
                return false;
            }
        }

        return true;
    }

    /**
     * This function adds question data to the database.
     *
     * @param questionModel
     * @return
     */
    public boolean addQuestionFromExcel(cQuestionModel questionModel) {
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

        } catch (Exception e) {
            Log.d(TAG, "Exception in importing QUESTION from Excel: " + e.getMessage());
        }

        // close the database connection
        db.close();

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
        Workbook workbook = excelHelper.getWorkbookLOGFRAME();
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
        Workbook workbook = excelHelper.getWorkbookLOGFRAME();
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
        cv.put(cSQLDBHelper.KEY_EVALUATION_CRITERIA_FK_ID, criteriaID);

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
        Workbook workbook = excelHelper.getWorkbookLOGFRAME();
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
        cv.put(cSQLDBHelper.KEY_EVALUATION_CRITERIA_FK_ID, criteriaID);

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
        Workbook workbook = excelHelper.getWorkbookLOGFRAME();
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
        cv.put(cSQLDBHelper.KEY_EVALUATION_CRITERIA_FK_ID, criteriaID);

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


    /* #################################### ACTIVITY PLANNING FUNCTIONS ###################################*/

    /**
     * This function extracts criteria data from excel and adds it to the database.
     *
     * @return
     */
    @Override
    public boolean addActivityPlanningFromExcel() {
        Workbook workbook = excelHelper.getWorkbookLOGFRAME();
        Sheet APSheet = workbook.getSheet(cExcelHelper.SHEET_tblACTIVITYPLANNING);

        if (APSheet == null) {
            return false;
        }

        for (Iterator<Row> ritAP = APSheet.iterator(); ritAP.hasNext(); ) {
            Row cRow = ritAP.next();

            //just skip the row if row number is 0
            if (cRow.getRowNum() == 0) {
                continue;
            }

            cActivityPlanningModel activityPlanningModel = new cActivityPlanningModel();

            activityPlanningModel.setActivityPlanningID((int)
                    cRow.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            activityPlanningModel.setLogFrameID((int)
                    cRow.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            activityPlanningModel.setName(
                    cRow.getCell(2, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
            activityPlanningModel.setDescription(
                    cRow.getCell(3, Row.CREATE_NULL_AS_BLANK).getStringCellValue());

            if (!addActivityPlanning(activityPlanningModel)) {
                return false;
            }
        }

        return true;
    }

    /**
     * This function adds criteria data to the database.
     *
     * @param activityPlanningModel
     * @return
     */
    public boolean addActivityPlanning(cActivityPlanningModel activityPlanningModel) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_ID, activityPlanningModel.getActivityPlanningID());
        cv.put(cSQLDBHelper.KEY_LOGFRAME_FK_ID, activityPlanningModel.getLogFrameID());
        cv.put(cSQLDBHelper.KEY_NAME, activityPlanningModel.getName());
        cv.put(cSQLDBHelper.KEY_DESCRIPTION, activityPlanningModel.getDescription());

        // insert project details
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblACTIVITYPLANNING, null, cv) < 0) {
                return false;
            }
        } catch (Exception e) {
            Log.d(TAG, "Exception in importing ACTIVITY PLANNING from Excel: " + e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }

    /* ###################################### ACTIVITY FUNCTIONS ######################################*/


    /**
     * This function extracts and adds the activity data from excel.
     *
     * @return
     */
    @Override
    public boolean addActivityFromExcel() {
        Workbook workbook = excelHelper.getWorkbookLOGFRAME();
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

            activityModel.setActivityID((int)
                    cRow.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            activityModel.setParentID((int)
                    cRow.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            activityModel.setOutputID((int)
                    cRow.getCell(2, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());

            /* get activities of the activity planning*/
            //Set<Pair<Integer, Integer>> planningSet = new HashSet<>();
/*
            int activityID;
            Sheet activitySheet = workbook.getSheet(cExcelHelper.SHEET_tblACTIVITY);
            for (Iterator<Row> ritActivity = activitySheet.iterator(); ritActivity.hasNext(); ) {
                Row rowActivity = ritActivity.next();

                //just skip the row if row number is 0
                if (rowActivity.getRowNum() == 0) {
                    continue;
                }

                activityID = (int) rowActivity.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                if (activityPlanningModel.getActivityPlanningID() == activityID) {
                    activityModel.setActivityID((int)
                            rowActivity.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
                    activityModel.setParentID ((int)
                            rowActivity.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
                    activityModel.setOutputID ((int)
                            rowActivity.getCell(2, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
                    //activityModel.setLogFrameID((int)
                    //        rowActivity.getCell(3, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
                    break;
                }
            }
*/
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

            /* get activity assignment */
            Map<Integer, Set<Pair<Integer, Integer>>> asaMap = new HashMap<>();
            Set<Pair<Integer, Integer>> asSet = new HashSet<>();
            int assignmentID, staffID, assignedActivityID;

            Sheet aqaSheet = workbook.getSheet(cExcelHelper.SHEET_tblACTIVITYASSIGNMENT);
            for (Iterator<Row> ritAQA = aqaSheet.iterator(); ritAQA.hasNext(); ) {
                Row rowAQA = ritAQA.next();

                //just skip the row if row number is 0
                if (rowAQA.getRowNum() == 0) {
                    continue;
                }

                assignedActivityID = (int) rowAQA.getCell(2, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                if (activityModel.getActivityID() == assignedActivityID) {
                    staffID = (int) rowAQA.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                    assignmentID = (int) rowAQA.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                    asSet.add(new Pair<>(assignmentID, staffID));
                }
            }
            asaMap.put(activityModel.getActivityID(), asSet);

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

            Gson gson = new Gson();
            Log.d(TAG, gson.toJson(activityModel));
            Log.d(TAG, gson.toJson(precedingSet));
            Log.d(TAG, gson.toJson(asaMap));
            Log.d(TAG, gson.toJson(saoMap));
            Log.d(TAG, gson.toJson(aqcMap));
            Log.d(TAG, gson.toJson(raidSet));

            if (!addActivityFromExcel(activityModel, precedingSet, asaMap, saoMap, aqcMap, raidSet)) {
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
                                        Map<Integer, Set<Pair<Integer, Integer>>> asaMap,
                                        Map<Set<Pair<Integer, Integer>>, Pair<Integer, Integer>> saoMap,
                                        Map<Integer, Set<Pair<Integer, Integer>>> aqcMap,
                                        Set<Pair<Integer, Integer>> raidSet) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_ACTIVITYPLANNING_FK_ID, activityModel.getActivityID());
        cv.put(cSQLDBHelper.KEY_PARENT_FK_ID, activityModel.getParentID());
        cv.put(cSQLDBHelper.KEY_OUTPUT_FK_ID, activityModel.getOutputID());

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

            // add assignment, staff and activity tuple
            for (Map.Entry<Integer, Set<Pair<Integer, Integer>>> asaEntry : asaMap.entrySet()) {
                int activityID = asaEntry.getKey();
                for (Pair<Integer, Integer> saPair : asaEntry.getValue()) {
                    int assignmentID = saPair.first;
                    int staffID = saPair.second;

                    if (addActivityAssignment(assignmentID, staffID, activityID))
                        continue;
                    else
                        return false;
                }
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
     * @param assignmentID
     * @param staffID
     * @param activityID
     * @return
     */
    public boolean addActivityAssignment(int assignmentID, int staffID, int activityID) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(cSQLDBHelper.KEY_ID, assignmentID);
        cv.put(cSQLDBHelper.KEY_STAFF_FK_ID, staffID);
        cv.put(cSQLDBHelper.KEY_ACTIVITY_FK_ID, activityID);

        if (db.insert(cSQLDBHelper.TABLE_tblACTIVITYASSIGNMENT, null, cv) < 0) {
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
        cv.put(cSQLDBHelper.KEY_EVALUATION_CRITERIA_FK_ID, criteriaID);

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

    /*################################## RESOURCE TYPE FUNCTIONS #################################*/

    /**
     * This function extracts resource type data from excel and adds it to the database.
     *
     * @return
     */
    @Override
    public boolean addResourceTypeFromExcel() {
        Workbook workbook = excelHelper.getWorkbookLOGFRAME();
        Sheet RTSheet = workbook.getSheet(cExcelHelper.SHEET_tblRESOURCETYPE);

        if (RTSheet == null) {
            return false;
        }

        for (Iterator<Row> ritRT = RTSheet.iterator(); ritRT.hasNext(); ) {
            Row cRow = ritRT.next();

            //just skip the row if row number is 0
            if (cRow.getRowNum() == 0) {
                continue;
            }

            cResourceTypeModel resourceTypeModel = new cResourceTypeModel();

            resourceTypeModel.setResourceTypeID((int)
                    cRow.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            resourceTypeModel.setName(
                    cRow.getCell(1, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
            resourceTypeModel.setDescription(
                    cRow.getCell(2, Row.CREATE_NULL_AS_BLANK).getStringCellValue());

            if (!addResourceType(resourceTypeModel)) {
                return false;
            }
        }

        return true;
    }

    /**
     * This function adds resource type data to the database.
     *
     * @param resourceTypeModel
     * @return
     */
    public boolean addResourceType(cResourceTypeModel resourceTypeModel) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_ID, resourceTypeModel.getResourceTypeID());
        cv.put(cSQLDBHelper.KEY_NAME, resourceTypeModel.getName());
        cv.put(cSQLDBHelper.KEY_DESCRIPTION, resourceTypeModel.getDescription());

        // insert project details
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblRESOURCETYPE, null, cv) < 0) {
                return false;
            }
        } catch (Exception e) {
            Log.d(TAG, "Exception in importing RESOURCE TYPE from Excel: " + e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }


    /*#################################### RESOURCE FUNCTIONS ####################################*/

    /**
     * This function extracts resource type data from excel and adds it to the database.
     *
     * @return
     */
    @Override
    public boolean addResourceFromExcel() {
        Workbook workbook = excelHelper.getWorkbookLOGFRAME();
        Sheet RSheet = workbook.getSheet(cExcelHelper.SHEET_tblRESOURCE);

        if (RSheet == null) {
            return false;
        }

        for (Iterator<Row> ritR = RSheet.iterator(); ritR.hasNext(); ) {
            Row cRow = ritR.next();

            //just skip the row if row number is 0
            if (cRow.getRowNum() == 0) {
                continue;
            }

            cResourceModel resourceModel = new cResourceModel();

            resourceModel.setResourceID((int)
                    cRow.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            resourceModel.setResourceTypeID((int)
                    cRow.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            resourceModel.setName(
                    cRow.getCell(2, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
            resourceModel.setDescription(
                    cRow.getCell(3, Row.CREATE_NULL_AS_BLANK).getStringCellValue());

            if (!addResource(resourceModel)) {
                return false;
            }
        }

        return true;
    }

    /**
     * This function adds resource data to the database.
     *
     * @param resourceModel
     * @return
     */
    public boolean addResource(cResourceModel resourceModel) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_ID, resourceModel.getResourceID());
        cv.put(cSQLDBHelper.KEY_RESOURCE_TYPE_FK_ID, resourceModel.getResourceTypeID());
        cv.put(cSQLDBHelper.KEY_NAME, resourceModel.getName());
        cv.put(cSQLDBHelper.KEY_DESCRIPTION, resourceModel.getDescription());

        // insert project details
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblRESOURCE, null, cv) < 0) {
                return false;
            }
        } catch (Exception e) {
            Log.d(TAG, "Exception in importing RESOURCE from Excel: " + e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }

    /*###################################### INPUT FUNCTIONS #####################################*/

    /**
     * This function extracts and adds the input data from excel.
     *
     * @return
     */
    @Override
    public boolean addInputFromExcel() {
        Workbook logFrameWorkbook = excelHelper.getWorkbookLOGFRAME();
        Workbook awpbWorkbook = excelHelper.getWorkbookAWPB();

        Sheet inputSheet = logFrameWorkbook.getSheet(cExcelHelper.SHEET_tblINPUT);

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
            inputModel.setActivityPlanningID((int) cRow.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            inputModel.setLogFrameID((int) cRow.getCell(2, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            inputModel.setResourceID((int) cRow.getCell(3, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());

            /* get questions grouped by criteria of the input */
            Map<Integer, Set<Pair<Integer, Integer>>> iqcMap = new HashMap<>();
            Set<Pair<Integer, Integer>> qcSet = new HashSet<>();
            int inputID, questionID, criteriaID;

            Sheet iqcSheet = logFrameWorkbook.getSheet(cExcelHelper.SHEET_tblINPUT_QUESTION);
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


            /* human input */
            int humanID = -1,  humanQuantity = 0;
            /* human set input */
            Map<Integer, Set<Pair<Integer, Integer>>> hiuMap = new HashMap<>();
            Set<Pair<Integer, Integer>> huSet = new HashSet<>();

            /* material input */
            int materialID = -1, materialQuantity = 0;

            /* expense inputs */
            int expenseID = -1; double expense = 0.0;

            /* income inputs */
            int incomeID = -1, fundID = -1, funderID = -1, beneficiaryID = -1; double fund = 0.0;

            Sheet humanSheet = awpbWorkbook.getSheet(cExcelHelper.SHEET_tblHUMAN);
            for (Iterator<Row> ritHuman = humanSheet.iterator(); ritHuman.hasNext(); ) {
                Row rowHuman = ritHuman.next();

                //just skip the row if row number is 0
                if (rowHuman.getRowNum() == 0) {
                    continue;
                }

                humanID = (int) rowHuman.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                if (inputModel.getInputID() == humanID) {
                    humanQuantity = (int) rowHuman.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();

                    /* human set input */
                    int humanSetInputID = -1, humanSetID = -1, userID = -1;

                    Sheet humanSetSheet = awpbWorkbook.getSheet(cExcelHelper.SHEET_tblHUMANSET);
                    for (Iterator<Row> ritHumanSet = humanSetSheet.iterator(); ritHumanSet.hasNext(); ) {
                        Row rowHumanSet = ritHumanSet.next();

                        //just skip the row if row number is 0
                        if (rowHumanSet.getRowNum() == 0) {
                            continue;
                        }

                        humanSetInputID = (int) rowHumanSet.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                        if (inputModel.getInputID() == humanSetInputID) {
                            humanSetID = (int) rowHumanSet.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                            userID = (int) rowHumanSet.getCell(2, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                            huSet.add(new Pair<>(humanSetID, userID));
                        }
                    }

                    break;
                }else {
                    humanID = -1;
                }
            }

            hiuMap.put(inputModel.getInputID(), huSet);

            /* material input */
            Sheet materialSheet = awpbWorkbook.getSheet(cExcelHelper.SHEET_tblMATERIAL);
            for (Iterator<Row> ritMaterial = materialSheet.iterator(); ritMaterial.hasNext(); ) {
                Row rowMaterial = ritMaterial.next();

                //just skip the row if row number is 0
                if (rowMaterial.getRowNum() == 0) {
                    continue;
                }

                materialID = (int) rowMaterial.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                if (inputModel.getInputID() == materialID) {
                    materialQuantity = (int) rowMaterial.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                    break;
                }else {
                    materialID = -1;
                }
            }

            /* income input */
            Sheet incomeSheet = awpbWorkbook.getSheet(cExcelHelper.SHEET_tblINCOME);
            for (Iterator<Row> ritIncome = incomeSheet.iterator(); ritIncome.hasNext(); ) {
                Row rowIncome = ritIncome.next();

                //just skip the row if row number is 0
                if (rowIncome.getRowNum() == 0) {
                    continue;
                }

                incomeID = (int) rowIncome.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                if (inputModel.getInputID() == incomeID) {

                    /* fund input */
                    //Set<Pair<Integer, Integer>> huSet = new HashSet<>();
                    int humanSetInputID = -1, humanSetID, userID;

                    Sheet fundSheet = awpbWorkbook.getSheet(cExcelHelper.SHEET_tblFUND);
                    for (Iterator<Row> ritFund = fundSheet.iterator(); ritFund.hasNext(); ) {
                        Row rowFund = ritFund.next();

                        //just skip the row if row number is 0
                        if (rowFund.getRowNum() == 0) {
                            continue;
                        }

                        int fundInputID = (int) rowFund.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                        if (inputModel.getInputID() == fundInputID) {
                            fundID = (int) rowFund.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                            funderID = (int) rowFund.getCell(2, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                            beneficiaryID = (int) rowFund.getCell(3, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                            fund = (double) rowFund.getCell(4, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                            break;
                        }
                    }

                    break;
                }else {
                    incomeID = -1;
                }
            }

            /* expense input */
            Sheet expenseSheet = awpbWorkbook.getSheet(cExcelHelper.SHEET_tblEXPENSE);
            for (Iterator<Row> ritExpense = expenseSheet.iterator(); ritExpense.hasNext(); ) {
                Row rowExpense = ritExpense.next();

                //just skip the row if row number is 0
                if (rowExpense.getRowNum() == 0) {
                    continue;
                }

                expenseID = (int) rowExpense.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                if (inputModel.getInputID() == expenseID) {
                    expense = (int) rowExpense.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                    break;
                }else {
                    expenseID = -1;
                }
            }

            if (!addInput(inputModel,
                    iqcMap,
                    humanID, humanQuantity, hiuMap,
                    materialID, materialQuantity,
                    incomeID, fundID, funderID, beneficiaryID, fund,
                    expenseID, expense)) {
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
    public boolean addInput(cInputModel inputModel,
                            Map<Integer, Set<Pair<Integer, Integer>>> iqcMap,
                            int humanID, int humanQuantity, Map<Integer, Set<Pair<Integer, Integer>>> hiuMap,
                            int materialID, int materialQuantity,
                            int incomeID, int fundID, int funderID, int beneficiaryID, double fund,
                            int expenseID, double expense) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_ID, inputModel.getInputID());
        cv.put(cSQLDBHelper.KEY_ACTIVITYPLANNING_FK_ID, inputModel.getActivityPlanningID());
        cv.put(cSQLDBHelper.KEY_LOGFRAME_FK_ID, inputModel.getLogFrameID());
        cv.put(cSQLDBHelper.KEY_RESOURCE_FK_ID, inputModel.getResourceID());
        //cv.put(cSQLDBHelper.KEY_NAME, inputModel.getName());
        //cv.put(cSQLDBHelper.KEY_DESCRIPTION, inputModel.getDescription());

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

            if (humanID != -1) {
                if (addHumanInput(inputModel.getInputID(), humanQuantity, hiuMap))
                    return true;
                else
                    return false;
            }

            if (materialID != -1) {
                if (addMaterialInput(inputModel.getInputID(), materialQuantity))
                    return true;
                else
                    return false;
            }

            if (incomeID != -1) {
                if (addIncomeInput(fundID, inputModel.getInputID(), funderID, beneficiaryID, fund))
                    return true;
                else
                    return false;
            }

            if (expenseID != -1) {
                if (addExpenseInput(inputModel.getInputID(), expense))
                    return true;
                else
                    return false;
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
        cv.put(cSQLDBHelper.KEY_EVALUATION_CRITERIA_FK_ID, criteriaID);

        if (db.insert(cSQLDBHelper.TABLE_tblINPUT_QUESTION, null, cv) < 0) {
            return false;
        }

        return true;
    }

    public boolean addHumanInput(int inputID, int humanQuantity, Map<Integer, Set<Pair<Integer, Integer>>> hiuMap) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_INPUT_FK_ID, inputID);
        cv.put(cSQLDBHelper.KEY_QUANTITY, humanQuantity);

        // insert evaluation type details
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblHUMAN, null, cv) < 0) {
                return false;
            }

            // add activity, question and criteria tuple
            for (Map.Entry<Integer, Set<Pair<Integer, Integer>>> hiuEntry : hiuMap.entrySet()) {
                int hsInputID = hiuEntry.getKey();
                for (Pair<Integer, Integer> huPair : hiuEntry.getValue()) {
                    int humanSetID = huPair.first;
                    int userID = huPair.second;

                    if (addHumanSet(humanSetID, hsInputID, userID))
                        continue;
                    else
                        return false;
                }
            }

        } catch (Exception e) {
            Log.d(TAG, "Exception in importing HUMAN INPUT from Excel: " + e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }

    public boolean addHumanSet(int humanSetID, int hsInputID, int userID) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_ID, humanSetID);
        cv.put(cSQLDBHelper.KEY_INPUT_FK_ID, hsInputID);
        cv.put(cSQLDBHelper.KEY_USER_FK_ID, userID);

        // insert evaluation type details
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblHUMANSET, null, cv) < 0) {
                return false;
            }

        } catch (Exception e) {
            Log.d(TAG, "Exception in importing HUMAN SET from Excel: " + e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }

    public boolean addMaterialInput(int inputID, int materialQuantity) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_INPUT_FK_ID, inputID);
        cv.put(cSQLDBHelper.KEY_QUANTITY, materialQuantity);

        // insert evaluation type details
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblMATERIAL, null, cv) < 0) {
                return false;
            }
        } catch (Exception e) {
            Log.d(TAG, "Exception in importing MATERIAL INPUT from Excel: " + e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }

    public boolean addIncomeInput(int fundID, int inputID, int funderID, int beneficiaryID, double fund) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_INPUT_FK_ID, inputID);

        // insert evaluation type details
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblINCOME, null, cv) < 0) {
                return false;
            }

            if (addFund(fundID, inputID, funderID, beneficiaryID, fund))
                return true;
            else
                return false;

        } catch (Exception e) {
            Log.d(TAG, "Exception in importing INCOME INPUT from Excel: " + e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }

    public boolean addFund(int fundID, int inputID, int funderID, int beneficiaryID, double fund) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_ID, fundID);
        cv.put(cSQLDBHelper.KEY_INPUT_FK_ID, inputID);
        cv.put(cSQLDBHelper.KEY_FUNDER_FK_ID, funderID);
        cv.put(cSQLDBHelper.KEY_BENEFICIARY_FK_ID, beneficiaryID);
        cv.put(cSQLDBHelper.KEY_FUND, fund);

        // insert evaluation type details
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblFUND, null, cv) < 0) {
                return false;
            }
        } catch (Exception e) {
            Log.d(TAG, "Exception in importing FUND from Excel: " + e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }

    public boolean addExpenseInput(int inputID, double amount) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_INPUT_FK_ID, inputID);
        cv.put(cSQLDBHelper.KEY_EXPENSE, amount);

        // insert evaluation type details
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblEXPENSE, null, cv) < 0) {
                return false;
            }
        } catch (Exception e) {
            Log.d(TAG, "Exception in importing EXPENSE INPUT from Excel: " + e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }
}
