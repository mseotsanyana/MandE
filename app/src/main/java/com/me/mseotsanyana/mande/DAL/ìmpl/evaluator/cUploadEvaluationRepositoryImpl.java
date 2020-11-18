package com.me.mseotsanyana.mande.DAL.ìmpl.evaluator;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.util.Pair;

import com.google.gson.Gson;
import com.me.mseotsanyana.mande.BLL.repository.evaluator.iUploadEvaluationRepository;
import com.me.mseotsanyana.mande.BLL.model.evaluator.cArrayChoiceModel;
import com.me.mseotsanyana.mande.BLL.model.evaluator.cArraySetModel;
import com.me.mseotsanyana.mande.BLL.model.evaluator.cColChoiceModel;
import com.me.mseotsanyana.mande.BLL.model.evaluator.cEvaluationResponseModel;
import com.me.mseotsanyana.mande.BLL.model.evaluator.cEvaluationTypeModel;
import com.me.mseotsanyana.mande.BLL.model.evaluator.cEvaluationModel;
import com.me.mseotsanyana.mande.BLL.model.evaluator.cMatrixSetModel;
import com.me.mseotsanyana.mande.BLL.model.evaluator.cRowChoiceModel;
import com.me.mseotsanyana.mande.DAL.storage.database.cSQLDBHelper;
import com.me.mseotsanyana.mande.DAL.storage.excel.cExcelHelper;
import com.me.mseotsanyana.mande.UTIL.cConstant;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
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
    /**
     * This function extracts criteria data from excel and adds it to the database.
     *
     * @return boolean
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

            arrayChoiceModel.setArrayChoiceID((int)
                    cRow.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            arrayChoiceModel.setName(
                    cRow.getCell(1, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
            arrayChoiceModel.setDescription(
                    cRow.getCell(2, Row.CREATE_NULL_AS_BLANK).getStringCellValue());

            if (!addArrayChoice(arrayChoiceModel)) {
                return false;
            }
        }

        return true;
    }

    /**
     * This function adds criteria data to the database.
     *
     * @param arrayChoiceModel array choice model
     * @return boolean
     */
    public boolean addArrayChoice(cArrayChoiceModel arrayChoiceModel) {
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
        } catch (Exception e) {
            Log.d(TAG, "Exception in importing ARRAY CHOICE from Excel: " + e.getMessage());
        }

        // close the database connection
        db.close();

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

    /*################################### ROW CHOICE FUNCTIONS ###################################*/

    /**
     * This function extracts row choice data from excel and adds it to the database.
     *
     * @return bool
     */
    @Override
    public boolean addRowChoiceFromExcel() {
        Workbook workbook = excelHelper.getWorkbookEVALUATION();
        Sheet RCSheet = workbook.getSheet(cExcelHelper.SHEET_tblROWCHOICE);

        if (RCSheet == null) {
            return false;
        }

        for (Row cRow : RCSheet) {
            //just skip the row if row number is 0
            if (cRow.getRowNum() == 0) {
                continue;
            }

            cRowChoiceModel rowChoiceModel = new cRowChoiceModel();

            rowChoiceModel.setRowChoiceID((int)
                    cRow.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            rowChoiceModel.setName(
                    cRow.getCell(1, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
            rowChoiceModel.setDescription(
                    cRow.getCell(2, Row.CREATE_NULL_AS_BLANK).getStringCellValue());

            if (!addRowChoice(rowChoiceModel)) {
                return false;
            }
        }

        return true;
    }

    /**
     * This function adds row choice data to the database.
     *
     * @param rowChoiceModel row option
     * @return bool
     */
    public boolean addRowChoice(cRowChoiceModel rowChoiceModel) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_ID, rowChoiceModel.getRowChoiceID());
        cv.put(cSQLDBHelper.KEY_NAME, rowChoiceModel.getName());
        cv.put(cSQLDBHelper.KEY_DESCRIPTION, rowChoiceModel.getDescription());

        // insert project details
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblROWCHOICE, null, cv) < 0) {
                return false;
            }
        } catch (Exception e) {
            Log.d(TAG, "Exception in importing ROW CHOICE from Excel: " + e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }

    public boolean deleteRowChoices() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblROWCHOICE, null, null);

        // close the database connection
        db.close();

        return result > -1;
    }

    /*################################### COL CHOICE FUNCTIONS ###################################*/

    /**
     * This function extracts col choice data from excel and adds it to the database.
     *
     * @return bool
     */

    @Override
    public boolean addColChoiceFromExcel() {
        Workbook workbook = excelHelper.getWorkbookEVALUATION();
        Sheet CCSheet = workbook.getSheet(cExcelHelper.SHEET_tblCOLCHOICE);

        if (CCSheet == null) {
            return false;
        }

        for (Row cRow : CCSheet) {
            //just skip the row if row number is 0
            if (cRow.getRowNum() == 0) {
                continue;
            }

            cColChoiceModel colChoiceModel = new cColChoiceModel();

            colChoiceModel.setColChoiceID((int)
                    cRow.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            colChoiceModel.setName(
                    cRow.getCell(1, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
            colChoiceModel.setDescription(
                    cRow.getCell(2, Row.CREATE_NULL_AS_BLANK).getStringCellValue());

            if (!addColChoice(colChoiceModel)) {
                return false;
            }
        }

        return true;
    }

    /**
     * @param colChoiceModel column option
     * @return bool
     */
    public boolean addColChoice(cColChoiceModel colChoiceModel) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_ID, colChoiceModel.getColChoiceID());
        cv.put(cSQLDBHelper.KEY_NAME, colChoiceModel.getName());
        cv.put(cSQLDBHelper.KEY_DESCRIPTION, colChoiceModel.getDescription());

        // insert project details
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblCOLCHOICE, null, cv) < 0) {
                return false;
            }
        } catch (Exception e) {
            Log.d(TAG, "Exception in importing COL CHOICE from Excel: " + e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }

    public boolean deleteColChoices() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblCOLCHOICE, null, null);

        // close the database connection
        db.close();

        return result > -1;
    }


    /*################################ ARRAY SET FUNCTIONS ################################*/

    /**
     * This function extracts array choice data from excel and adds it to the database.
     *
     * @return
     */
    @Override
    public boolean addArraySetFromExcel() {
        Workbook workbook = excelHelper.getWorkbookEVALUATION();
        Sheet ASSheet = workbook.getSheet(cExcelHelper.SHEET_tblARRAYSET);
        Sheet ACSSheet = workbook.getSheet(cExcelHelper.SHEET_tblARRAYCHOICESET);

        if (ASSheet == null) {
            return false;
        }

        for (Row cASRow : ASSheet) {
            //just skip the row if row number is 0
            if (cASRow.getRowNum() == 0) {
                continue;
            }

            cArraySetModel arraySetModel = new cArraySetModel();

            arraySetModel.setArraySetID(
                    (int) cASRow.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            arraySetModel.setName(
                    cASRow.getCell(1, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
            arraySetModel.setDescription(
                    cASRow.getCell(2, Row.CREATE_NULL_AS_BLANK).getStringCellValue());


            /* array choices */
            Set<Long> arrayChoiceSet = new HashSet<>();
            long arraySetID, arrayChoiceID;

            for (Row rowACS : ACSSheet) {
                //just skip the row if row number is 0
                if (rowACS.getRowNum() == 0) {
                    continue;
                }

                arraySetID = (int) rowACS.getCell(0,
                        Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                if (arraySetModel.getArraySetID() == arraySetID) {
                    arrayChoiceID = (int) rowACS.getCell(1,
                            Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                    arrayChoiceSet.add(arrayChoiceID);
                }
            }

            if (!addArraySet(arraySetModel, arrayChoiceSet)) {
                return false;
            }
        }

        return true;
    }

    public boolean addArraySet(cArraySetModel arraySetModel, Set<Long> arrayChoiceSet) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_ID, arraySetModel.getArraySetID());
        cv.put(cSQLDBHelper.KEY_NAME, arraySetModel.getName());
        cv.put(cSQLDBHelper.KEY_DESCRIPTION, arraySetModel.getDescription());

        // insert project details
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblARRAYSET, null, cv) < 0) {
                return false;
            }

            // add question, type and choice tuple
            for (Long arrayChoiceID : arrayChoiceSet) {
                if (!addArrayChoiceSet(arraySetModel.getArraySetID(), arrayChoiceID))
                    return false;
            }

        } catch (Exception e) {
            Log.d(TAG, "Exception in importing ARRAY SET from Excel: " + e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }

    public boolean addArrayChoiceSet(long arraySetID, long arrayChoiceID) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(cSQLDBHelper.KEY_ARRAY_SET_FK_ID, arraySetID);
        cv.put(cSQLDBHelper.KEY_ARRAY_CHOICE_FK_ID, arrayChoiceID);

        try {
            if (db.insert(cSQLDBHelper.TABLE_tblARRAYCHOICESET, null, cv) < 0) {
                return false;
            }
        } catch (Exception e) {
            Log.d(TAG, "Exception in importing ARRAY CHOICE SET from Excel: " + e.getMessage());
        }

        return true;
    }

    public boolean deleteArraySets() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblARRAYSET, null, null);

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

    /*################################# MATRIX SET FUNCTIONS ##################################*/

    /**
     * This function extracts matrix set data from excel and adds it to the database.
     *
     * @return bool
     */
    @Override
    public boolean addMatrixSetFromExcel() {
        Workbook workbook = excelHelper.getWorkbookEVALUATION();
        Sheet MSSheet = workbook.getSheet(cExcelHelper.SHEET_tblMATRIXSET);
        Sheet MCSSheet = workbook.getSheet(cExcelHelper.SHEET_tblMATRIXCHOICESET);

        if (MSSheet == null) {
            return false;
        }

        for (Row rowMS : MSSheet) {
            //just skip the row if row number is 0
            if (rowMS.getRowNum() == 0) {
                continue;
            }

            cMatrixSetModel matrixSetModel = new cMatrixSetModel();

            matrixSetModel.setMatrixSetID(
                    (int) rowMS.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            matrixSetModel.setName(
                    rowMS.getCell(1, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
            matrixSetModel.setDescription(
                    rowMS.getCell(2, Row.CREATE_NULL_AS_BLANK).getStringCellValue());


            /* matrix choices */
            Set<Pair<Long, Long>> matrixChoiceSet = new HashSet<>();
            long matrixSetID, rowChoiceID, colChoiceID;

            for (Row rowMCS : MCSSheet) {
                //just skip the row if row number is 0
                if (rowMCS.getRowNum() == 0) {
                    continue;
                }

                matrixSetID = (int) rowMCS.getCell(0,
                        Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                if (matrixSetModel.getMatrixSetID() == matrixSetID) {
                    rowChoiceID = (int) rowMCS.getCell(1,
                            Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                    colChoiceID = (int) rowMCS.getCell(2,
                            Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                    matrixChoiceSet.add(new Pair<>(rowChoiceID, colChoiceID));
                }
            }

            if (!addMatrixSet(matrixSetModel, matrixChoiceSet)) {
                return false;
            }
        }

        return true;
    }

    /**
     * This function adds matrix set data to the database.
     *
     * @param matrixSetModel matrix choice
     * @return bool
     */
    public boolean addMatrixSet(cMatrixSetModel matrixSetModel,
                                Set<Pair<Long, Long>> matrixChoiceSet) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_ID, matrixSetModel.getMatrixSetID());
        cv.put(cSQLDBHelper.KEY_NAME, matrixSetModel.getName());
        cv.put(cSQLDBHelper.KEY_DESCRIPTION, matrixSetModel.getDescription());

        // insert project details
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblMATRIXSET, null, cv) < 0) {
                return false;
            }

            // add question, type and choice tuple
            for (Pair<Long, Long> matrixChoice : matrixChoiceSet) {
                if (!addMatrixChoiceSet(matrixSetModel.getMatrixSetID(),
                        matrixChoice.first, matrixChoice.second))
                    return false;
            }

        } catch (Exception e) {
            Log.d(TAG, "Exception in importing MATRIX SET from Excel: " + e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }

    public boolean addMatrixChoiceSet(long matrixSetID, long rowChoiceID, long colChoiceID) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(cSQLDBHelper.KEY_MATRIX_SET_FK_ID, matrixSetID);
        cv.put(cSQLDBHelper.KEY_ROW_CHOICE_FK_ID, rowChoiceID);
        cv.put(cSQLDBHelper.KEY_COL_CHOICE_FK_ID, colChoiceID);

        try {
            if (db.insert(cSQLDBHelper.TABLE_tblMATRIXCHOICESET, null, cv) < 0) {
                return false;
            }
        } catch (Exception e) {
            Log.d(TAG, "Exception in importing MATRIX CHOICE SET from Excel: " + e.getMessage());
        }

        return true;
    }

    public boolean deleteMatrixSets() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblMATRIXSET, null, null);

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

    /*################################ MATRIX CHOICE SET FUNCTIONS ###############################*/

    /**
     * This function extracts matrix choice data from excel and adds it to the database.
     *
     * @return
     */
//    @Override
//    public boolean addMatrixChoiceSetFromExcel() {
//        Workbook workbook = excelHelper.getWorkbookEVALUATION();
//        Sheet MSSheet = workbook.getSheet(cExcelHelper.SHEET_tblMATRIXSET);
//        Sheet MCSSheet = workbook.getSheet(cExcelHelper.SHEET_tblMATRIXCHOICESET);
//
//        if (MSSheet == null) {
//            return false;
//        }
//
//        for (Row cMSRow : MSSheet) {
//            //just skip the row if row number is 0
//            if (cMSRow.getRowNum() == 0) {
//                continue;
//            }
//
//            cMatrixSetModel matrixSetModel = new cMatrixSetModel();
//
//            matrixSetModel.setMatrixSetID(
//                    (int) cMSRow.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
//            matrixSetModel.setName(
//                    cMSRow.getCell(1, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
//            matrixSetModel.setDescription(
//                    cMSRow.getCell(2, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
//
//
//            /* matrix choices */
//            Set<Pair<Long, Long>> matrixChoiceSet = new HashSet<>();
//            long matrixSetID, rowChoiceID, colChoiceID;
//
//            for (Row rowMCS : MCSSheet) {
//                //just skip the row if row number is 0
//                if (rowMCS.getRowNum() == 0) {
//                    continue;
//                }
//
//                matrixSetID = (int) rowMCS.getCell(0,
//                        Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
//                if (matrixSetModel.getMatrixSetID() == matrixSetID) {
//                    rowChoiceID = (int) rowMCS.getCell(1,
//                            Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
//                    colChoiceID = (int) rowMCS.getCell(2,
//                            Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
//                    matrixChoiceSet.add(new Pair<>(rowChoiceID, colChoiceID));
//                }
//            }
//
//            if (!addMatrixSet(matrixSetModel, matrixChoiceSet)) {
//                return false;
//            }
//        }
//
//        return true;
//    }

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
        Sheet ESheet = workbook.getSheet(cExcelHelper.SHEET_tblEVALUATION);

        if (ESheet == null) {
            return false;
        }

        for (Row cRow : ESheet) {
            //just skip the row if row number is 0
            if (cRow.getRowNum() == 0) {
                continue;
            }

            cEvaluationModel evaluationModel = new cEvaluationModel();

            evaluationModel.setEvaluationID((int)
                    cRow.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            evaluationModel.setLogFrameID((int)
                    cRow.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            evaluationModel.setEvaluationTypeID((int)
                    cRow.getCell(2, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            evaluationModel.setName(
                    cRow.getCell(3, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
            evaluationModel.setDescription(
                    cRow.getCell(4, Row.CREATE_NULL_AS_BLANK).getStringCellValue());

            /* get questions of the view_pager_questionnaire*/
            Set<Pair<Long, Long>> questionSet = new HashSet<>();
            long eqID = -1, evaluationID = -1, questionID = -1;
            Sheet QESheet = workbook.getSheet(cExcelHelper.SHEET_tblQUESTION_EVALUATION);

            for (Row rowQE : QESheet) {
                //just skip the row if row number is 0
                if (rowQE.getRowNum() == 0) {
                    continue;
                }

                evaluationID = (int) rowQE.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                if (evaluationModel.getEvaluationID() == evaluationID) {
                    eqID = (int) rowQE.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                    questionID = (int) rowQE.getCell(2, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
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

            /* conditional order of the view_pager_questionnaire */
            int coID = -1, questionOrderID = -1, rQuestionID = -1, prQuestionID = -1, nrQuestionID = -1;
            Sheet COSheet = workbook.getSheet(cExcelHelper.SHEET_tblCONDITIONAL_ORDER);
            for (Row rowCO : COSheet) {
                //just skip the row if row number is 0
                if (rowCO.getRowNum() == 0) {
                    continue;
                }

                for (Pair<Long, Long> question : questionSet) {
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
        cv.put(cSQLDBHelper.KEY_LOGFRAME_FK_ID, evaluationModel.getLogFrameID());
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
            long matrixResponseID, rowResponse = -1, colResponse = -1;

            /* get numeric responses */

            Sheet NRSheet = workbook.getSheet(cExcelHelper.SHEET_tblNUMERICRESPONSE);
            for (Row rowNR : NRSheet) {
                //just skip the row if row number is 0
                if (rowNR.getRowNum() == 0) {
                    continue;
                }

                responseID = (int) rowNR.getCell(0,
                        Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                if (eresponseModel.getEvaluationResponseID() == responseID) {
                    numQuestionID = (int) rowNR.getCell(1,
                            Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                    numericResponse = (int) rowNR.getCell(2,
                            Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                }
            }

            /* get text responses */

            Sheet TRSheet = workbook.getSheet(cExcelHelper.SHEET_tblTEXTRESPONSE);
            for (Row rowTR : TRSheet) {
                //just skip the row if row number is 0
                if (rowTR.getRowNum() == 0) {
                    continue;
                }

                textResponseID = (int) rowTR.getCell(0,
                        Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                if (eresponseModel.getEvaluationResponseID() == textResponseID) {
                    txtQuestionID = (int) rowTR.getCell(1,
                            Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                    textResponse = rowTR.getCell(2,
                            Row.CREATE_NULL_AS_BLANK).getStringCellValue();
                }
            }

            /* get text responses */

            Sheet DRSheet = workbook.getSheet(cExcelHelper.SHEET_tblDATERESPONSE);
            for (Row rowDR : DRSheet) {
                //just skip the row if row number is 0
                if (rowDR.getRowNum() == 0) {
                    continue;
                }

                dateResponseID = (int) rowDR.getCell(0,
                        Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                if (eresponseModel.getEvaluationResponseID() == dateResponseID) {
                    dateQuestionID = (int) rowDR.getCell(1,
                            Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                    dateResponse = rowDR.getCell(2,
                            Row.CREATE_NULL_AS_BLANK).getDateCellValue();
                }
            }

            /* get array responses */

            Sheet ARSheet = workbook.getSheet(cExcelHelper.SHEET_tblARRAYRESPONSE);
            for (Row rowAR : ARSheet) {
                //just skip the row if row number is 0
                if (rowAR.getRowNum() == 0) {
                    continue;
                }

                arrayResponseID = (int) rowAR.getCell(0,
                        Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                if (eresponseModel.getEvaluationResponseID() == arrayResponseID) {
                    arrQuestionID = (int) rowAR.getCell(1,
                            Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                    arrayResponse = (int) rowAR.getCell(2,
                            Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                }
            }

            /* get matrix responses */

            Sheet MRSheet = workbook.getSheet(cExcelHelper.SHEET_tblMATRIXRESPONSE);
            for (Row rowMR : MRSheet) {
                //just skip the row if row number is 0
                if (rowMR.getRowNum() == 0) {
                    continue;
                }

                matrixResponseID = (int) rowMR.getCell(0,
                        Row.CREATE_NULL_AS_BLANK).getNumericCellValue();

                if (eresponseModel.getEvaluationResponseID() == matrixResponseID) {
                    matQuestionID = (int) rowMR.getCell(1,
                            Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                    rowResponse = (int) rowMR.getCell(2,
                            Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                    colResponse = (int) rowMR.getCell(3,
                            Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                }
            }

            if (!addEvaluationResponse(eresponseModel, numQuestionID, numericResponse,
                    txtQuestionID, textResponse, dateQuestionID, dateResponse,
                    arrQuestionID, arrayResponse, matQuestionID, rowResponse, colResponse)) {
                return false;
            }
        }

        return true;
    }

    public boolean addEvaluationResponse(cEvaluationResponseModel eresponseModel, long numQuestionID,
                                         long numericResponse, long txtQuestionID, String textResponse,
                                         long dateQuestionID, Date dateResponse, long arrQuestionID,
                                         long arrayResponse, long matQuestionID, long rowResponse,
                                         long colResponse) {

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

            if (rowResponse != -1 && rowResponse != -1) {
                if (!addMatrixResponse(eresponseModel.getEvaluationResponseID(), matQuestionID,
                        rowResponse, colResponse))
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
        cv.put(cSQLDBHelper.KEY_PRIMITIVE_QUESTION_FK_ID, numQuestionID);
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
        cv.put(cSQLDBHelper.KEY_PRIMITIVE_QUESTION_FK_ID, txtQuestionID);
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
        cv.put(cSQLDBHelper.KEY_PRIMITIVE_QUESTION_FK_ID, dateQuestionID);
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
        cv.put(cSQLDBHelper.KEY_ARRAY_QUESTION_FK_ID, arrQuestionID);
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

    public boolean addMatrixResponse(long eresponseID, long matQuestionID, long rowResponse,
                                     long colResponse) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_EVALUATION_RESPONSE_FK_ID, eresponseID);
        cv.put(cSQLDBHelper.KEY_MATRIX_QUESTION_FK_ID, matQuestionID);
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