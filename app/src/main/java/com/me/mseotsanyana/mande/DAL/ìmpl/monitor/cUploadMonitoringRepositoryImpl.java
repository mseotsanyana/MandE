package com.me.mseotsanyana.mande.DAL.ìmpl.monitor;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.util.Pair;

import com.google.gson.Gson;
import com.me.mseotsanyana.mande.BLL.repository.monitor.iUploadMonitoringRepository;
import com.me.mseotsanyana.mande.DAL.model.monitor.cArrayTargetModel;
import com.me.mseotsanyana.mande.DAL.model.monitor.cCriteriaScoreModel;
import com.me.mseotsanyana.mande.DAL.model.monitor.cDataCollectorModel;
import com.me.mseotsanyana.mande.DAL.model.monitor.cDataSourceModel;
import com.me.mseotsanyana.mande.DAL.model.monitor.cIndicatorModel;
import com.me.mseotsanyana.mande.DAL.model.monitor.cIndicatorTypeModel;
import com.me.mseotsanyana.mande.DAL.model.monitor.cMOVModel;
import com.me.mseotsanyana.mande.DAL.model.monitor.cMatrixTargetModel;
import com.me.mseotsanyana.mande.DAL.model.monitor.cMethodModel;
import com.me.mseotsanyana.mande.DAL.model.monitor.cQualitativeCriteriaModel;
import com.me.mseotsanyana.mande.DAL.model.monitor.cQualitativeSetModel;
import com.me.mseotsanyana.mande.DAL.model.monitor.cQuantitativeTypeModel;
import com.me.mseotsanyana.mande.DAL.model.monitor.cTargetModel;
import com.me.mseotsanyana.mande.DAL.model.wpb.cMilestoneModel;
import com.me.mseotsanyana.mande.DAL.storage.database.cSQLDBHelper;
import com.me.mseotsanyana.mande.DAL.storage.excel.cExcelHelper;
import com.me.mseotsanyana.mande.UTIL.cConstant;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import static com.me.mseotsanyana.mande.UTIL.cConstant.ARRAY_INDICATOR;
import static com.me.mseotsanyana.mande.UTIL.cConstant.CONS_QUARTERLY_ID;
import static com.me.mseotsanyana.mande.UTIL.cConstant.MATRIX_INDICATOR;
import static com.me.mseotsanyana.mande.UTIL.cConstant.QUALITATIVE_INDICATOR;
import static com.me.mseotsanyana.mande.UTIL.cConstant.QUANTITATIVE_INDICATOR;

public class cUploadMonitoringRepositoryImpl implements iUploadMonitoringRepository {
    private static SimpleDateFormat sdf = cConstant.FORMAT_DATE;
    private static String TAG = cUploadMonitoringRepositoryImpl.class.getSimpleName();

    /* an object of the database helper */
    private cSQLDBHelper dbHelper;
    /* an object of the excel helper */
    private cExcelHelper excelHelper;

    public cUploadMonitoringRepositoryImpl(Context context) {
        dbHelper = new cSQLDBHelper(context);
        excelHelper = new cExcelHelper(context);
    }

    /*###################################### METHOD FUNCTIONS ####################################*/

    @Override
    public boolean addMethodFromExcel() {
        Workbook workbook = excelHelper.getWorkbookMONITORING();
        Sheet MSheet = workbook.getSheet(cExcelHelper.SHEET_tblMETHOD);

        if (MSheet == null) {
            return false;
        }

        for (Row cRowM : MSheet) {
            //just skip the row if row number is 0
            if (cRowM.getRowNum() == 0) {
                continue;
            }

            cMethodModel methodModel = new cMethodModel();

            methodModel.setMethodID((int)
                    cRowM.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            methodModel.setName(
                    cRowM.getCell(1, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
            methodModel.setDescription(
                    cRowM.getCell(2, Row.CREATE_NULL_AS_BLANK).getStringCellValue());

            if (!addMethod(methodModel)) {
                return false;
            }
        }

        return true;
    }

    public boolean addMethod(cMethodModel methodModel) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_ID, methodModel.getMethodID());
        cv.put(cSQLDBHelper.KEY_NAME, methodModel.getName());
        cv.put(cSQLDBHelper.KEY_DESCRIPTION, methodModel.getDescription());

        // insert record details
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblMETHOD, null, cv) < 0) {
                return false;
            }
        } catch (Exception e) {
            Log.d(TAG, "Exception in importing METHOD from Excel: " + e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }

    public boolean deleteMethods() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblMETHOD, null, null);

        // close the database connection
        db.close();

        return result > -1;
    }


    /*####################################### MOV FUNCTIONS ######################################*/

    @Override
    public boolean addMOVsFromExcel() {
        Workbook workbook = excelHelper.getWorkbookMONITORING();
        Sheet MOVSheet = workbook.getSheet(cExcelHelper.SHEET_tblMOV);

        if (MOVSheet == null) {
            return false;
        }

        for (Row cRowMOV : MOVSheet) {
            //just skip the row if row number is 0
            if (cRowMOV.getRowNum() == 0) {
                continue;
            }

            cMOVModel movModel = new cMOVModel();

            movModel.setMovID((int)
                    cRowMOV.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            movModel.setName(
                    cRowMOV.getCell(1, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
            movModel.setDescription(
                    cRowMOV.getCell(2, Row.CREATE_NULL_AS_BLANK).getStringCellValue());

            if (!addMOV(movModel)) {
                return false;
            }
        }

        return true;
    }

    public boolean addMOV(cMOVModel movModel) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_ID, movModel.getMovID());
        cv.put(cSQLDBHelper.KEY_NAME, movModel.getName());
        cv.put(cSQLDBHelper.KEY_DESCRIPTION, movModel.getDescription());

        // insert record details
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblMOV, null, cv) < 0) {
                return false;
            }
        } catch (Exception e) {
            Log.d(TAG, "Exception in importing MOV from Excel: " + e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }

    public boolean deleteMOVs() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblMOV, null, null);

        // close the database connection
        db.close();

        return result > -1;
    }

    /*###################################### DATA SOURCE FUNCTIONS ####################################*/

    @Override
    public boolean addDataSourceFromExcel() {
        Workbook workbook = excelHelper.getWorkbookMONITORING();
        Sheet DSSheet = workbook.getSheet(cExcelHelper.SHEET_tblDATASOURCE);

        if (DSSheet == null) {
            return false;
        }

        for (Row cRow : DSSheet) {
            //just skip the row if row number is 0
            if (cRow.getRowNum() == 0) {
                continue;
            }

            cDataSourceModel dataSourceModel = new cDataSourceModel();

            dataSourceModel.setDataSourceID((int)
                    cRow.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            dataSourceModel.setName(
                    cRow.getCell(1, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
            dataSourceModel.setDescription(
                    cRow.getCell(2, Row.CREATE_NULL_AS_BLANK).getStringCellValue());

            if (!addDataSource(dataSourceModel)) {
                return false;
            }
        }

        return true;
    }

    public boolean addDataSource(cDataSourceModel dataSourceModel) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_ID, dataSourceModel.getDataSourceID());
        cv.put(cSQLDBHelper.KEY_NAME, dataSourceModel.getName());
        cv.put(cSQLDBHelper.KEY_DESCRIPTION, dataSourceModel.getDescription());

        // insert record details
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblDATASOURCE, null, cv) < 0) {
                return false;
            }
        } catch (Exception e) {
            Log.d(TAG, "Exception in importing DATA SOURCE from Excel: " + e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }

    public boolean deleteDataSources() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblDATASOURCE, null, null);

        // close the database connection
        db.close();

        return result > -1;
    }

    /*################################# QUANTITATIVE TYPE FUNCTIONS ##############################*/

    @Override
    public boolean addQuantitativeTypeFromExcel() {
        Workbook workbook = excelHelper.getWorkbookMONITORING();
        Sheet QTSheet = workbook.getSheet(cExcelHelper.SHEET_tblQUANTITATIVETYPE);

        if (QTSheet == null) {
            return false;
        }

        for (Row cRow : QTSheet) {
            //just skip the row if row number is 0
            if (cRow.getRowNum() == 0) {
                continue;
            }

            cQuantitativeTypeModel quantitativeTypeModel = new cQuantitativeTypeModel();

            quantitativeTypeModel.setQuantitativeTypeID((int)
                    cRow.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            quantitativeTypeModel.setName(
                    cRow.getCell(1, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
            quantitativeTypeModel.setDescription(
                    cRow.getCell(2, Row.CREATE_NULL_AS_BLANK).getStringCellValue());

            if (!addQuantitativeType(quantitativeTypeModel)) {
                return false;
            }
        }

        return true;
    }

    public boolean addQuantitativeType(cQuantitativeTypeModel quantitativeTypeModel) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_ID, quantitativeTypeModel.getQuantitativeTypeID());
        cv.put(cSQLDBHelper.KEY_NAME, quantitativeTypeModel.getName());
        cv.put(cSQLDBHelper.KEY_DESCRIPTION, quantitativeTypeModel.getDescription());

        // insert record details
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblQUANTITATIVETYPE, null, cv) < 0) {
                return false;
            }
        } catch (Exception e) {
            Log.d(TAG, "Exception in importing QUANTITATIVE TYPE from Excel: " + e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }

    public boolean deleteQuantitativeTypes() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblQUANTITATIVETYPE, null, null);

        // close the database connection
        db.close();

        return result > -1;
    }

    /*################################### INDICATOR TYPE FUNCTIONS ###############################*/

    @Override
    public boolean addIndicatorTypeFromExcel() {
        Workbook workbook = excelHelper.getWorkbookMONITORING();
        Sheet ITSheet = workbook.getSheet(cExcelHelper.SHEET_tblINDICATORTYPE);

        if (ITSheet == null) {
            return false;
        }

        for (Row cRowIT : ITSheet) {
            //just skip the row if row number is 0
            if (cRowIT.getRowNum() == 0) {
                continue;
            }

            cIndicatorTypeModel indicatorTypeModel = new cIndicatorTypeModel();

            indicatorTypeModel.setIndicatorTypeID((int)
                    cRowIT.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            indicatorTypeModel.setName(
                    cRowIT.getCell(1, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
            indicatorTypeModel.setDescription(
                    cRowIT.getCell(2, Row.CREATE_NULL_AS_BLANK).getStringCellValue());

            if (!addIndicatorType(indicatorTypeModel)) {
                return false;
            }
        }

        return true;
    }

    public boolean addIndicatorType(cIndicatorTypeModel indicatorTypeModel) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_ID, indicatorTypeModel.getIndicatorTypeID());
        cv.put(cSQLDBHelper.KEY_NAME, indicatorTypeModel.getName());
        cv.put(cSQLDBHelper.KEY_DESCRIPTION, indicatorTypeModel.getDescription());

        // insert record details
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblINDICATORTYPE, null, cv) < 0) {
                return false;
            }
        } catch (Exception e) {
            Log.d(TAG, "Exception in importing INDICATOR TYPE from Excel: " + e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }

    public boolean deleteIndicatorTypes() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblINDICATORTYPE, null, null);

        // close the database connection
        db.close();

        return result > -1;
    }

    /*################################### DATA COLLECTOR FUNCTIONS ###############################*/

    @Override
    public boolean addDataCollectorFromExcel() {
        Workbook workbook = excelHelper.getWorkbookMONITORING();
        Sheet DCSheet = workbook.getSheet(cExcelHelper.SHEET_tblDATACOLLECTOR);

        if (DCSheet == null) {
            return false;
        }

        for (Row cRowDC : DCSheet) {
            //just skip the row if row number is 0
            if (cRowDC.getRowNum() == 0) {
                continue;
            }

            cDataCollectorModel dataCollectorModel = new cDataCollectorModel();

            dataCollectorModel.setDataCollectorID((int)
                    cRowDC.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            dataCollectorModel.setStaffID((int)
                    cRowDC.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            dataCollectorModel.setName(
                    cRowDC.getCell(2, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
            dataCollectorModel.setDescription(
                    cRowDC.getCell(3, Row.CREATE_NULL_AS_BLANK).getStringCellValue());

            if (!addDataCollector(dataCollectorModel)) {
                return false;
            }
        }

        return true;
    }

    public boolean addDataCollector(cDataCollectorModel dataCollectorModel) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_ID, dataCollectorModel.getDataCollectorID());
        cv.put(cSQLDBHelper.KEY_STAFF_FK_ID, dataCollectorModel.getStaffID());
        //cv.put(cSQLDBHelper.KEY_NAME, dataCollectorModel.getName());
        //cv.put(cSQLDBHelper.KEY_DESCRIPTION, dataCollectorModel.getDescription());

        // insert record details
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblDATACOLLECTOR, null, cv) < 0) {
                return false;
            }
        } catch (Exception e) {
            Log.d(TAG, "Exception in importing DATA COLLECTOR from Excel: " + e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }

    public boolean deleteDataCollectors() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblDATACOLLECTOR, null, null);

        // close the database connection
        db.close();

        return result > -1;
    }

    /*################################### CRITERIA SCORE FUNCTIONS ###############################*/

    @Override
    public boolean addCriteriaScoreFromExcel() {
        Workbook workbook = excelHelper.getWorkbookMONITORING();
        Sheet CSSheet = workbook.getSheet(cExcelHelper.SHEET_tblCRITERIASCORE);
        Sheet QCSheet = workbook.getSheet(cExcelHelper.SHEET_tblQUALITATIVECRITERIA);

        if (CSSheet == null) {
            return false;
        }

        for (Row cRow : CSSheet) {
            //just skip the row if row number is 0
            if (cRow.getRowNum() == 0) {
                continue;
            }

            cCriteriaScoreModel criteriaScoreModel = new cCriteriaScoreModel();

            criteriaScoreModel.setCriteriaScoreID((int)
                    cRow.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            criteriaScoreModel.setScore((int)
                    cRow.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            criteriaScoreModel.setName(
                    cRow.getCell(2, Row.CREATE_NULL_AS_BLANK).getStringCellValue());

            Set<cQualitativeCriteriaModel> qualitativeCriteriaModelSet = new HashSet<>();
            for (Row cQCRow : QCSheet) {
                //just skip the row if row number is 0
                if (cQCRow.getRowNum() == 0) {
                    continue;
                }

                long criteriaCoreID = (int) cQCRow.getCell(1,
                        Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                if (criteriaScoreModel.getCriteriaScoreID() == criteriaCoreID) {

                    cQualitativeCriteriaModel criteria = new cQualitativeCriteriaModel();

                    criteria.setQualitativeCriteriaID((int) cQCRow.getCell(0,
                            Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
                    criteria.setCriteriaScoreID((int) cQCRow.getCell(1,
                            Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
                    criteria.setName(cQCRow.getCell(2,
                            Row.CREATE_NULL_AS_BLANK).getStringCellValue());
                    criteria.setDescription(cQCRow.getCell(3,
                            Row.CREATE_NULL_AS_BLANK).getStringCellValue());

                    qualitativeCriteriaModelSet.add(criteria);
                }
            }

            if (!addCriteriaScore(criteriaScoreModel, qualitativeCriteriaModelSet)) {
                return false;
            }
        }

        return true;
    }

    public boolean addCriteriaScore(cCriteriaScoreModel criteriaScoreModel,
                                    Set<cQualitativeCriteriaModel> qualitativeCriteriaModelSet) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_ID, criteriaScoreModel.getCriteriaScoreID());
        cv.put(cSQLDBHelper.KEY_SCORE, criteriaScoreModel.getScore());
        cv.put(cSQLDBHelper.KEY_NAME, criteriaScoreModel.getName());

        // insert record details
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblCRITERIASCORE, null, cv) < 0) {
                return false;
            }

            for (cQualitativeCriteriaModel criteriaModel: qualitativeCriteriaModelSet){
                if(!addQualitativeCriteria(criteriaModel.getQualitativeCriteriaID(),
                        criteriaScoreModel.getCriteriaScoreID(), criteriaModel.getName(),
                        criteriaModel.getDescription())){
                    return false;
                }
            }

        } catch (Exception e) {
            Log.d(TAG, "Exception in importing CRITERIA SCORE from Excel: " + e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }

    public boolean addQualitativeCriteria(long qualitativeCriteriaID, long criteriaScoreID,
                                          String name, String description) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_ID, qualitativeCriteriaID);
        cv.put(cSQLDBHelper.KEY_CRITERIA_SCORE_FK_ID, criteriaScoreID);
        cv.put(cSQLDBHelper.KEY_NAME, name);
        cv.put(cSQLDBHelper.KEY_DESCRIPTION, description);

        // insert record details
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblQUALITATIVECRITERIA, null, cv) < 0) {
                return false;
            }
        } catch (Exception e) {
            Log.d(TAG, "Exception in importing QUALITATIVE CRITERIA SCORE from Excel: " +
                    e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }

    public boolean deleteCriteriaScores() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblCRITERIASCORE, null,
                null);

        // close the database connection
        db.close();

        return result > -1;
    }

    public boolean deleteQualitativeCriteria() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblQUALITATIVECRITERIA, null,
                null);

        // close the database connection
        db.close();

        return result > -1;
    }

    /*################################## QUALITATIVE SET FUNCTIONS ###############################*/

    @Override
    public boolean addQualitativeSetFromExcel() {
        Workbook workbook = excelHelper.getWorkbookMONITORING();
        Sheet QSSheet = workbook.getSheet(cExcelHelper.SHEET_tblQUALITATIVESET);
        Sheet QSSSheet = workbook.getSheet(cExcelHelper.SHEET_tblQUALITATIVESCORESET);

        if (QSSheet == null) {
            return false;
        }

        for (Row cRow : QSSheet) {
            //just skip the row if row number is 0
            if (cRow.getRowNum() == 0) {
                continue;
            }

            cQualitativeSetModel qualitativeSetModel = new cQualitativeSetModel();

            qualitativeSetModel.setQualitativeSetID((int)
                    cRow.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            qualitativeSetModel.setName(
                    cRow.getCell(1, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
            qualitativeSetModel.setDescription(
                    cRow.getCell(2, Row.CREATE_NULL_AS_BLANK).getStringCellValue());

            Set<Long> criteriaScoreIDSet = new HashSet<>();
            for (Row cQSSRow : QSSSheet) {
                //just skip the row if row number is 0
                if (cQSSRow.getRowNum() == 0) {
                    continue;
                }

                long qualitativeSetID = (int) cQSSRow.getCell(0,
                        Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                if (qualitativeSetModel.getQualitativeSetID() == qualitativeSetID) {

                    long scoreCriteriaID = (int) cQSSRow.getCell(1,
                            Row.CREATE_NULL_AS_BLANK).getNumericCellValue();

                    criteriaScoreIDSet.add(scoreCriteriaID);
                }
            }

            if (!addQualitativeScoreSet(qualitativeSetModel, criteriaScoreIDSet)) {
                return false;
            }
        }

        return true;
    }

    public boolean addQualitativeScoreSet(cQualitativeSetModel qualitativeSetModel,
                                          Set<Long> criteriaScoreIDSet) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_ID, qualitativeSetModel.getQualitativeSetID());
        cv.put(cSQLDBHelper.KEY_NAME, qualitativeSetModel.getName());
        cv.put(cSQLDBHelper.KEY_DESCRIPTION, qualitativeSetModel.getDescription());

        // insert record details
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblQUALITATIVESET, null, cv) < 0) {
                return false;
            }

            for (long criteriaScoreID: criteriaScoreIDSet){
                if(!addQualitativeScore(qualitativeSetModel.getQualitativeSetID(),criteriaScoreID)){
                    return false;
                }
            }

        } catch (Exception e) {
            Log.d(TAG, "Exception in importing QUALITATIVE SET from Excel: " + e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }

    public boolean addQualitativeScore(long qualitativeSetID, long criteriaScoreID) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_QUALITATIVE_SET_FK_ID, qualitativeSetID);
        cv.put(cSQLDBHelper.KEY_CRITERIA_SCORE_FK_ID, criteriaScoreID);

        // insert record details
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblQUALITATIVESCORESET, null, cv) < 0) {
                return false;
            }
        } catch (Exception e) {
            Log.d(TAG, "Exception in importing QUALITATIVE SCORE SET SCORE from Excel: " +
                    e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }

    public boolean deleteQualitativeSets() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblQUALITATIVESET, null,
                null);

        // close the database connection
        db.close();

        return result > -1;
    }

    public boolean deleteQualitativeScoreSets() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblQUALITATIVESCORESET, null,
                null);

        // close the database connection
        db.close();

        return result > -1;
    }

    /*####################################### TARGET FUNCTIONS ###################################*/

    @Override
    public boolean addTargetFromExcel() {
        Workbook workbook = excelHelper.getWorkbookMONITORING();
        Sheet TSheet = workbook.getSheet(cExcelHelper.SHEET_tblTARGET);
        Sheet QLTSheet = workbook.getSheet(cExcelHelper.SHEET_tblQUALITATIVETARGET);
        Sheet QNTSheet = workbook.getSheet(cExcelHelper.SHEET_tblQUANTITATIVETARGET);

        if (TSheet == null) {
            return false;
        }

        for (Row cRow : TSheet) {
            //just skip the row if row number is 0
            if (cRow.getRowNum() == 0) {
                continue;
            }

            cTargetModel targetModel = new cTargetModel();

            targetModel.setTargetID((int)
                    cRow.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            targetModel.setName(
                    cRow.getCell(1, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
            targetModel.setDescription(
                    cRow.getCell(2, Row.CREATE_NULL_AS_BLANK).getStringCellValue());

            Set<Pair<Long, Long>> qualitativeModelSet = new HashSet<>();
            for (Row cQLRow : QLTSheet) {
                //just skip the row if row number is 0
                if (cQLRow.getRowNum() == 0) {
                    continue;
                }

                long qualitativeID = (int) cQLRow.getCell(0,
                        Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                if (targetModel.getTargetID() == qualitativeID) {
                    long baseline = (int) cQLRow.getCell(1,
                            Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                    long target = (int)cQLRow.getCell(2,
                            Row.CREATE_NULL_AS_BLANK).getNumericCellValue();

                    qualitativeModelSet.add(new Pair<>(baseline, target));
                }
            }

            Set<Pair<Long, Long>> quantitativeModelSet = new HashSet<>();
            for (Row cQNRow : QNTSheet) {
                //just skip the row if row number is 0
                if (cQNRow.getRowNum() == 0) {
                    continue;
                }

                long quantitativeID = (int) cQNRow.getCell(0,
                        Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                if (targetModel.getTargetID() == quantitativeID) {
                    long baseline = (int) cQNRow.getCell(1,
                            Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                    long target = (int)cQNRow.getCell(2,
                            Row.CREATE_NULL_AS_BLANK).getNumericCellValue();

                    quantitativeModelSet.add(new Pair<>(baseline, target));
                }
            }

            if (!addTarget(targetModel, qualitativeModelSet, quantitativeModelSet)) {
                return false;
            }
        }

        return true;
    }

    public boolean addTarget(cTargetModel targetModel,
                                    Set<Pair<Long, Long>> qualitativeModelSet,
                                    Set<Pair<Long, Long>> quantitativeModelSet) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_ID, targetModel.getTargetID());
        cv.put(cSQLDBHelper.KEY_NAME, targetModel.getName());
        cv.put(cSQLDBHelper.KEY_DESCRIPTION, targetModel.getDescription());

        // insert record details
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblTARGET, null, cv) < 0) {
                return false;
            }

            for (Pair<Long, Long> qualitativePair: qualitativeModelSet){
                if(!addQualitativeTarget(targetModel.getTargetID(), qualitativePair.first,
                        qualitativePair.second)){
                    return false;
                }
            }

            for (Pair<Long, Long> quantitativePair: quantitativeModelSet){
                if(!addQuantitativeTarget(targetModel.getTargetID(), quantitativePair.first,
                        quantitativePair.second)){
                    return false;
                }
            }

        } catch (Exception e) {
            Log.d(TAG, "Exception in importing TARGET from Excel: " + e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }

    public boolean addQualitativeTarget(long targetID, long baseline_score, long target_score) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_TARGET_FK_ID, targetID);
        cv.put(cSQLDBHelper.KEY_BASELINE_SCORE_FK_ID, baseline_score);
        cv.put(cSQLDBHelper.KEY_TARGET_SCORE_FK_ID, target_score);

        // insert record details
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblQUALITATIVETARGET, null, cv) < 0) {
                return false;
            }
        } catch (Exception e) {
            Log.d(TAG, "Exception in importing QUALITATIVE TARGET from Excel: " +
                    e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }

    public boolean addQuantitativeTarget(long targetID, long baseline, long target) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_TARGET_FK_ID, targetID);
        cv.put(cSQLDBHelper.KEY_BASELINE_VALUE, baseline);
        cv.put(cSQLDBHelper.KEY_TARGET_VALUE, target);

        // insert record details
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblQUANTITATIVETARGET, null, cv) < 0) {
                return false;
            }

            Workbook workbook = excelHelper.getWorkbookMONITORING();
            Sheet ATSheet = workbook.getSheet(cExcelHelper.SHEET_tblARRAYTARGET);
            Sheet MTSheet = workbook.getSheet(cExcelHelper.SHEET_tblMATRIXTARGET);

            Set<cArrayTargetModel> arrayChoiceSet = new HashSet<>();
            for (Row cATRow : ATSheet) {
                //just skip the row if row number is 0
                if (cATRow.getRowNum() == 0) {
                    continue;
                }

                long quantitativeTargetID = (int) cATRow.getCell(0,
                        Row.CREATE_NULL_AS_BLANK).getNumericCellValue();

                if (targetID == quantitativeTargetID) {

                    cArrayTargetModel arrayTargetModel = new cArrayTargetModel();

                    arrayTargetModel.getArrayChoiceModel().setArrayChoiceID((int)
                            cATRow.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
                    arrayTargetModel.setDisaggregatedBaseline((int)
                            cATRow.getCell(2, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
                    arrayTargetModel.setDisaggregatedTarget((int)
                            cATRow.getCell(3, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());

                    //long array_choice = (int) cATRow.getCell(1,
                    // Row.CREATE_NULL_AS_BLANK).getNumericCellValue();

                    arrayChoiceSet.add(arrayTargetModel);
                }
            }

            Set<cMatrixTargetModel> matrixChoiceSet = new HashSet<>();
            for (Row cMTRow : MTSheet) {
                //just skip the row if row number is 0
                if (cMTRow.getRowNum() == 0) {
                    continue;
                }

                long quantitativeTargetID = (int) cMTRow.getCell(0,
                        Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                if (targetID == quantitativeTargetID) {

                    cMatrixTargetModel matrixTargetModel = new cMatrixTargetModel();

                    matrixTargetModel.getRowChoiceModel().setRowChoiceID((int)
                            cMTRow.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
                    matrixTargetModel.getColChoiceModel().setColChoiceID((int)
                            cMTRow.getCell(2, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
                    matrixTargetModel.setDisaggregatedBaseline((int)
                            cMTRow.getCell(3, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
                    matrixTargetModel.setDisaggregatedTarget((int)
                            cMTRow.getCell(4, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());

                    /*long row_choice = (int) cMTRow.getCell(1,
                            Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                    long col_choice = (int) cMTRow.getCell(2,
                            Row.CREATE_NULL_AS_BLANK).getNumericCellValue();*/

                    matrixChoiceSet.add(matrixTargetModel);
                }
            }

            if(!addQuantitativeChildTarget(targetID, arrayChoiceSet, matrixChoiceSet)){
                return false;
            }

        } catch (Exception e) {
            Log.d(TAG, "Exception in importing QUANTITATIVE TARGET from Excel: " +
                    e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }

    public boolean addQuantitativeChildTarget(long targetID, Set<cArrayTargetModel> arrayChoiceSet,
                                              Set<cMatrixTargetModel> matrixChoiceSet){
        for(cArrayTargetModel arrayTarget: arrayChoiceSet) {
            if (!addArrayTarget(targetID,
                    arrayTarget.getArrayChoiceModel().getArrayChoiceID(),
                    arrayTarget.getDisaggregatedBaseline(),
                    arrayTarget.getDisaggregatedTarget())) {
                return false;
            }
        }
        for(cMatrixTargetModel matrixTarget: matrixChoiceSet) {
            if (!addMatrixTarget(targetID,
                    matrixTarget.getRowChoiceModel().getRowChoiceID(),
                    matrixTarget.getColChoiceModel().getColChoiceID(),
                    matrixTarget.getDisaggregatedBaseline(),
                    matrixTarget.getDisaggregatedTarget())) {
                return false;
            }
        }

        return true;
    }

    public boolean addArrayTarget(long targetID, long arrayChoiceID, int baseline, int target) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_QUANTITATIVE_TARGET_FK_ID, targetID);
        cv.put(cSQLDBHelper.KEY_ARRAY_CHOICE_FK_ID, arrayChoiceID);
        cv.put(cSQLDBHelper.KEY_DISAGGREGATED_BASELINE, baseline);
        cv.put(cSQLDBHelper.KEY_DISAGGREGATED_TARGET, target);

        // insert evaluation type details
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblARRAYTARGET, null, cv) < 0) {
                return false;
            }
        } catch (Exception e) {
            Log.d(TAG, "Exception in importing ARRAY TARGET from Excel: " + e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }

    public boolean addMatrixTarget(long targetID, long rowChoiceID, long colChoiceID,
                                   int baseline, int target) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_QUANTITATIVE_TARGET_FK_ID, targetID);
        cv.put(cSQLDBHelper.KEY_ROW_CHOICE_FK_ID, rowChoiceID);
        cv.put(cSQLDBHelper.KEY_COL_CHOICE_FK_ID, colChoiceID);
        cv.put(cSQLDBHelper.KEY_DISAGGREGATED_BASELINE, baseline);
        cv.put(cSQLDBHelper.KEY_DISAGGREGATED_TARGET, target);

        // insert evaluation type details
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblMATRIXTARGET, null, cv) < 0) {
                return false;
            }
        } catch (Exception e) {
            Log.d(TAG, "Exception in importing MATRIX TARGET from Excel: " + e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }

    public boolean deleteTargets() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblTARGET, null,
                null);

        // close the database connection
        db.close();

        return result > -1;
    }

    public boolean deleteQualitativeTargets() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblQUALITATIVETARGET, null,
                null);

        // close the database connection
        db.close();

        return result > -1;
    }

    public boolean deleteQuantitativeTargets() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblQUANTITATIVETARGET, null,
                null);

        // close the database connection
        db.close();

        return result > -1;
    }

    public boolean deleteArrayTargets() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblARRAYTARGET, null,
                null);

        // close the database connection
        db.close();

        return result > -1;
    }

    public boolean deleteMatrixTargets() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblMATRIXTARGET, null,
                null);

        // close the database connection
        db.close();

        return result > -1;
    }

    /*###################################### INDICATOR FUNCTIONS #################################*/

    @Override
    public boolean addIndicatorFromExcel() {
        Workbook workbook = excelHelper.getWorkbookMONITORING();
        Sheet ISheet = workbook.getSheet(cExcelHelper.SHEET_tblINDICATOR);
        Sheet QLISheet = workbook.getSheet(cExcelHelper.SHEET_tblQUALITATIVEINDICATOR);
        Sheet QNISheet = workbook.getSheet(cExcelHelper.SHEET_tblQUANTITATIVEINDICATOR);

        if (ISheet == null) {
            return false;
        }

        for (Row cRow : ISheet) {
            //just skip the row if row number is 0
            if (cRow.getRowNum() == 0) {
                continue;
            }

            cIndicatorModel indicatorModel = new cIndicatorModel();

            indicatorModel.setIndicatorID((int) cRow.getCell(0,
                    Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            indicatorModel.setLogFrameID((int) cRow.getCell(1,
                    Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            indicatorModel.setTargetID((int) cRow.getCell(2,
                    Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            indicatorModel.setIndicatorTypeID((int) cRow.getCell(3,
                    Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            indicatorModel.setFrequencyID((int) cRow.getCell(4,
                    Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            indicatorModel.setMethodID((int) cRow.getCell(5,
                    Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            indicatorModel.setChartID((int) cRow.getCell(6,
                    Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            indicatorModel.setDataCollectorID((int) cRow.getCell(7,
                    Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            indicatorModel.setName(cRow.getCell(8,
                    Row.CREATE_NULL_AS_BLANK).getStringCellValue());
            indicatorModel.setDescription(cRow.getCell(9,
                    Row.CREATE_NULL_AS_BLANK).getStringCellValue());
            indicatorModel.setStartDate(cRow.getCell(10,
                    Row.CREATE_NULL_AS_BLANK).getDateCellValue());
            indicatorModel.setEndDate(cRow.getCell(11,
                    Row.CREATE_NULL_AS_BLANK).getDateCellValue());

            Set<Long> qualitativeSetIDs = new HashSet<>();
            for (Row cQLRow : QLISheet) {
                //just skip the row if row number is 0
                if (cQLRow.getRowNum() == 0) {
                    continue;
                }

                long qualitativeID = (int) cQLRow.getCell(0,
                        Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                if (indicatorModel.getIndicatorID() == qualitativeID) {
                    long qualitativeSetID = (int) cQLRow.getCell(1,
                            Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                    qualitativeSetIDs.add(qualitativeSetID);
                }
            }

            Set<Long> quantitativeTypeIDs = new HashSet<>();
            for (Row cQNRow : QNISheet) {
                //just skip the row if row number is 0
                if (cQNRow.getRowNum() == 0) {
                    continue;
                }

                long quantitativeID = (int) cQNRow.getCell(0,
                        Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                if (indicatorModel.getIndicatorID() == quantitativeID) {
                    long quantitativeTypeID = (int) cQNRow.getCell(1,
                            Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                    quantitativeTypeIDs.add(quantitativeTypeID);
                }
            }

            ArrayList<cMilestoneModel> milestoneModels = new ArrayList<>();
            if (indicatorModel.getFrequencyID() == CONS_QUARTERLY_ID) {
                /* add indicator milestone */
                LocalDate startDate = convertDate2LocalDate(indicatorModel.getStartDate());
                LocalDate endDate = convertDate2LocalDate(indicatorModel.getEndDate());
                milestoneModels = generateQuarterlySequence(startDate, endDate);
            }

            if (!addIndicator(indicatorModel, qualitativeSetIDs, quantitativeTypeIDs,
                    milestoneModels)) {
                return false;
            }

            //if (!addIndicator(indicatorModel, milestoneModels)) {
            //    return false;
            // }
            // Gson gson = new Gson();
            //ArrayList<String> quarters = new ArrayList<>();
        }

        return true;
    }

    public boolean addIndicator(cIndicatorModel indicatorModel,
                                Set<Long> qualitativeSetIDs,
                                Set<Long> quantitativeTypeIDs,
                                ArrayList<cMilestoneModel> milestoneModels) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_ID, indicatorModel.getIndicatorID());
        cv.put(cSQLDBHelper.KEY_LOGFRAME_FK_ID, indicatorModel.getLogFrameID());
        cv.put(cSQLDBHelper.KEY_TARGET_FK_ID, indicatorModel.getTargetID());
        cv.put(cSQLDBHelper.KEY_INDICATOR_TYPE_FK_ID, indicatorModel.getIndicatorTypeID());
        cv.put(cSQLDBHelper.KEY_FREQUENCY_FK_ID, indicatorModel.getFrequencyID());
        cv.put(cSQLDBHelper.KEY_METHOD_FK_ID, indicatorModel.getMethodID());
        cv.put(cSQLDBHelper.KEY_CHART_FK_ID, indicatorModel.getChartID());
        cv.put(cSQLDBHelper.KEY_DATA_COLLECTOR_FK_ID, indicatorModel.getDataCollectorID());
        cv.put(cSQLDBHelper.KEY_NAME, indicatorModel.getName());
        cv.put(cSQLDBHelper.KEY_DESCRIPTION, indicatorModel.getDescription());
        //cv.put(cSQLDBHelper.KEY_QUESTION, indicatorModel.getQuestion());
        cv.put(cSQLDBHelper.KEY_START_DATE, String.valueOf(indicatorModel.getStartDate()));
        cv.put(cSQLDBHelper.KEY_END_DATE, String.valueOf(indicatorModel.getEndDate()));

        // insert project details
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblINDICATOR, null, cv) < 0) {
                return false;
            }

            /* qualitative indicator */
            for (long qualitativeID: qualitativeSetIDs){
                if(!addQualitativeIndicator(indicatorModel.getIndicatorID(), qualitativeID)){
                    return false;
                }
            }

            /* quantitative indicator */
            for (long quantitativeTypeID: quantitativeTypeIDs){
                if(!addQuantitativeIndicator(indicatorModel.getIndicatorID(), quantitativeTypeID)){
                    return false;
                }
            }

            /* indicator milestone */
            for (cMilestoneModel milestoneModel : milestoneModels) {
                if (!addMilestone(milestoneModel, indicatorModel)) {
                    return false;
                }
            }

        } catch (Exception e) {
            Log.d(TAG, "Exception in importing INDICATOR from Excel: " + e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }

    public boolean addQualitativeIndicator(long indicatorID, long qualitativeSetID) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_INDICATOR_FK_ID, indicatorID);
        cv.put(cSQLDBHelper.KEY_QUALITATIVE_SET_FK_ID, qualitativeSetID);

        // insert record details
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblQUALITATIVEINDICATOR, null, cv) < 0) {
                return false;
            }
        } catch (Exception e) {
            Log.d(TAG, "Exception in importing QUALITATIVE INDICATOR from Excel: " +
                    e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }


    public boolean addQuantitativeIndicator(long indicatorID, long quantitativeTypeID) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_INDICATOR_FK_ID, indicatorID);
        cv.put(cSQLDBHelper.KEY_QUANTITATIVE_TYPE_FK_ID, quantitativeTypeID);

        // insert record details
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblQUANTITATIVEINDICATOR, null, cv) < 0) {
                return false;
            }

            Workbook workbook = excelHelper.getWorkbookMONITORING();
            Sheet AISheet = workbook.getSheet(cExcelHelper.SHEET_tblARRAYINDICATOR);
            Sheet MISheet = workbook.getSheet(cExcelHelper.SHEET_tblMATRIXINDICATOR);

            Set<Long> arraySetIDs = new HashSet<>();
            for (Row cAIRow : AISheet) {
                //just skip the row if row number is 0
                if (cAIRow.getRowNum() == 0) {
                    continue;
                }

                long quantitativeIndicatorID = (int) cAIRow.getCell(0,
                        Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                if (indicatorID == quantitativeIndicatorID) {
                    long array_set = (int) cAIRow.getCell(1,
                            Row.CREATE_NULL_AS_BLANK).getNumericCellValue();

                    arraySetIDs.add(array_set);
                }
            }

            Set<Long> matrixSetIDs = new HashSet<>();
            for (Row cMIRow : MISheet) {
                //just skip the row if row number is 0
                if (cMIRow.getRowNum() == 0) {
                    continue;
                }

                long quantitativeIndicatorID = (int) cMIRow.getCell(0,
                        Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                if (indicatorID == quantitativeIndicatorID) {
                    long matrix_set = (int) cMIRow.getCell(1,
                            Row.CREATE_NULL_AS_BLANK).getNumericCellValue();

                    matrixSetIDs.add(matrix_set);
                }
            }

            if(!addQuantitativeChildIndicator(indicatorID, arraySetIDs, matrixSetIDs)){
                return false;
            }

        } catch (Exception e) {
            Log.d(TAG, "Exception in importing QUANTITATIVE TARGET from Excel: " +
                    e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }


    public boolean addQuantitativeChildIndicator(long indicatorID, Set<Long> arraySetIDs,
                                                 Set<Long> matrixSetIDs){
        for(long arraySetID: arraySetIDs) {
            if (!addArrayIndicator(indicatorID, arraySetID)) {
                return false;
            }
        }
        for(long matrixSetID: matrixSetIDs) {
            if (!addMatrixIndicator(indicatorID, matrixSetID)) {
                return false;
            }
        }

        return true;
    }

    public boolean addArrayIndicator(long indicatorID, long arraySetID) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_INDICATOR_FK_ID, indicatorID);
        cv.put(cSQLDBHelper.KEY_ARRAY_SET_FK_ID, arraySetID);

        // insert evaluation type details
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblARRAYINDICATOR, null, cv) < 0) {
                return false;
            }
        } catch (Exception e) {
            Log.d(TAG, "Exception in importing ARRAY INDICATOR from Excel: " + e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }

    public boolean addMatrixIndicator(long indicatorID, long matrixSetID) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_INDICATOR_FK_ID, indicatorID);
        cv.put(cSQLDBHelper.KEY_MATRIX_SET_FK_ID, matrixSetID);

        // insert evaluation type details
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblMATRIXINDICATOR, null, cv) < 0) {
                return false;
            }
        } catch (Exception e) {
            Log.d(TAG, "Exception in importing MATRIX INDICATOR from Excel: " + e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }


    public boolean addMilestone(cMilestoneModel milestoneModel, cIndicatorModel indicatorModel) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_NAME, milestoneModel.getName());
        cv.put(cSQLDBHelper.KEY_DESCRIPTION, milestoneModel.getDescription());
        cv.put(cSQLDBHelper.KEY_START_DATE, String.valueOf(milestoneModel.getStartDate()));
        cv.put(cSQLDBHelper.KEY_END_DATE, String.valueOf(milestoneModel.getEndDate()));

        // insert project details
        try {
            long milestoneID = db.insert(cSQLDBHelper.TABLE_tblMILESTONE, null, cv);
            if (milestoneID < 0) {
                return false;
            }

            if(!addIndicatorMilestone(milestoneID, indicatorModel.getIndicatorID())){
                return false;
            }
/*
            if (indicatorModel.getIndicatorID() == QUALITATIVE_INDICATOR){
                addQualitativeMilestone();
            }else if (indicatorModel.getIndicatorID() == QUANTITATIVE_INDICATOR){
                addQuantitativeMilestone();
            }else if (indicatorModel.getIndicatorID() == ARRAY_INDICATOR){
                addArrayMilestone();
            }else if (indicatorModel.getIndicatorID() == MATRIX_INDICATOR){
                addMatrixMilestone();
            }
*/
        } catch (Exception e) {
            Log.d(TAG, "Exception in importing MILESTONE from Excel: " + e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }

    public boolean addIndicatorMilestone(long milestoneID, long indicatorID) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_MILESTONE_FK_ID, milestoneID);
        cv.put(cSQLDBHelper.KEY_INDICATOR_FK_ID, indicatorID);

        // insert project details
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblINDICATORMILESTONE, null, cv) < 0) {
                return false;
            }

        } catch (Exception e) {
            Log.d(TAG, "Exception in importing INDICATOR MILESTONE from Excel: " + e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }

    public ArrayList<cMilestoneModel> generateQuarterlySequence(LocalDate startDate,
                                                                LocalDate endDate) {
        // first truncate startDate to first day of quarter
        int startMonth = startDate.getMonthValue();
        startMonth -= (startMonth - 1) % 3;
        startDate = startDate.withMonth(startMonth).withDayOfMonth(1);

        DateTimeFormatter nameFormatter
                = DateTimeFormatter.ofPattern("QQQ", Locale.ENGLISH);
        DateTimeFormatter descriptionFormatter
                = DateTimeFormatter.ofPattern("uuuuQQQ", Locale.ENGLISH);

        //ArrayList<String> quarterSequence = new ArrayList<>();

        ArrayList<cMilestoneModel> milestoneModels = new ArrayList<>();
        cMilestoneModel milestoneModel = null;

        // iterate thorough quarters
        LocalDate currentQuarterStart = startDate;
        while (!currentQuarterStart.isAfter(endDate)) {
            milestoneModel = new cMilestoneModel();
            // assign a quarter
            if (currentQuarterStart.format(nameFormatter).equals("Q1")) {
                milestoneModel.setName("First Quarter");
            }
            if (currentQuarterStart.format(nameFormatter).equals("Q2")) {
                milestoneModel.setName("Second Quarter");
            }
            if (currentQuarterStart.format(nameFormatter).equals("Q3")) {
                milestoneModel.setName("Third Quarter");
            }
            if (currentQuarterStart.format(nameFormatter).equals("Q4")) {
                milestoneModel.setName("Fourth Quarter");
            }
            milestoneModel.setDescription(currentQuarterStart.format(descriptionFormatter));
            // assign start and end dates
            milestoneModel.setStartDate(convertLocalDate2Date(currentQuarterStart));
            milestoneModel.setEndDate(convertLocalDate2Date(currentQuarterStart.plusMonths(3)));

            milestoneModels.add(milestoneModel);

            currentQuarterStart = currentQuarterStart.plusMonths(3);
        }

        //Gson gson = new Gson();
        //Log.d(TAG, gson.toJson(milestoneModels));

        return milestoneModels;
    }

    public LocalDate convertDate2LocalDate(Date date) {
        return LocalDate.from(Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()));
    }

    public Date convertLocalDate2Date(LocalDate dateToConvert) {
        return java.util.Date.from(dateToConvert.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }

    @Override
    public boolean deleteIndicators() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblINDICATOR, null, null);

        // close the database connection
        db.close();

        return result > -1;
    }

    public boolean deleteQualitativeIndicators() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblQUALITATIVEINDICATOR, null,
                null);

        // close the database connection
        db.close();

        return result > -1;
    }

    public boolean deleteQuantitativeIndicators() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblQUANTITATIVEINDICATOR, null,
                null);

        // close the database connection
        db.close();

        return result > -1;
    }

    public boolean deleteArrayIndicators() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblARRAYINDICATOR, null,
                null);

        // close the database connection
        db.close();

        return result > -1;
    }

    public boolean deleteMatrixIndicators() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblMATRIXINDICATOR, null,
                null);

        // close the database connection
        db.close();

        return result > -1;
    }

    @Override
    public boolean deleteMilestones() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblMILESTONE, null, null);

        // close the database connection
        db.close();

        return result > -1;
    }

    @Override
    public boolean deleteIndicatorMilestones() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblINDICATORMILESTONE, null, null);

        // close the database connection
        db.close();

        return result > -1;
    }
}

   /*
       /*public ArrayList<String> generateQuarterlySequence(LocalDate startDate, LocalDate endDate) {
        // first truncate startDate to first day of quarter
        int startMonth = startDate.getMonthValue();
        startMonth -= (startMonth - 1) % 3;
        startDate = startDate.withMonth(startMonth).withDayOfMonth(1);

        DateTimeFormatter quarterFormatter
                = DateTimeFormatter.ofPattern("QQQ", Locale.ENGLISH);
        ArrayList<String> quarterSequence = new ArrayList<>();

        // iterate thorough quarters
        LocalDate currentQuarterStart = startDate;
        while (!currentQuarterStart.isAfter(endDate)) {
            quarterSequence.add(currentQuarterStart.format(quarterFormatter));
            currentQuarterStart = currentQuarterStart.plusMonths(3);
        }
        return quarterSequence;
    }


       public cMilestoneModel createMilestoneModel(String quarter) {
        cMilestoneModel milestoneModel = new cMilestoneModel();

        // assign a quarter
        if (quarter.equals("Q1")) {
            milestoneModel.setName("First Quarter");
        }
        if (quarter.equals("Q2")) {
            milestoneModel.setName("Second Quarter");
        }
        if (quarter.equals("Q3")) {
            milestoneModel.setName("Third Quarter");
        }
        if (quarter.equals("Q4")) {
            milestoneModel.setName("Fourth Quarter");
        }
        // assign a reporting date
        milestoneModel.setReportedDate((new Date()));

        return milestoneModel;
    }


            Gson gson = new Gson();
            Log.d(TAG, gson.toJson(startDate));
            Log.d(TAG, gson.toJson(endDate));
            Log.d(TAG, gson.toJson(quarters));

             get questions grouped by criteria of the output
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

            /* get outcomes of the output for a sub-logframe
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

            /* get raids of the output
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
*/
//Log.d(TAG, gson.toJson(sooMap));
//Log.d(TAG, gson.toJson(oqcMap));
//Log.d(TAG, gson.toJson(raidSet));

    /* ############################### MONITORING RESPONSE FUNCTIONS #############################*/

//    @Override
//    public boolean addMResponseFromExcel() {
//        Workbook workbook = excelHelper.getWorkbookMONITORING();
//        Sheet MRSheet = workbook.getSheet(cExcelHelper.SHEET_tblMRESPONSE);
//
//        if (MRSheet == null) {
//            return false;
//        }
//
//        for (Row cRow : MRSheet) {
//            //just skip the row if row number is 0
//            if (cRow.getRowNum() == 0) {
//                continue;
//            }
//
//            cMResponseModel mResponseModel = new cMResponseModel();
//
//            mResponseModel.setMresponseID((int)
//                    cRow.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
//            mResponseModel.setIndicatorMilestoneID((int)
//                    cRow.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
//            mResponseModel.setDataCollectorID((int)
//                    cRow.getCell(2, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
//
//            /* get quantitative responses */
//            int QNresponseID = -1, unitID = -1;
//            double value = 0.0;
//            Sheet QNSheet = workbook.getSheet(cExcelHelper.SHEET_tblQUANTITATIVERESPONSE);
//            for (Row rowQNR : QNSheet) {
//                //just skip the row if row number is 0
//                if (rowQNR.getRowNum() == 0) {
//                    continue;
//                }
//
//                QNresponseID = (int) rowQNR.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
//                if (mResponseModel.getMresponseID() == QNresponseID) {
//                    unitID = (int) rowQNR.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
//                    value = (int) rowQNR.getCell(2, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
//                } else {
//                    QNresponseID = -1;
//                }
//            }
//
//            /* get qualitative responses */
//            int QLresponseID = -1, qualitativeChoiceID = -1;
//            Sheet QLRSheet = workbook.getSheet(cExcelHelper.SHEET_tblQUALITATIVERESPONSE);
//            for (Row rowQLR : QLRSheet) {
//                //just skip the row if row number is 0
//                if (rowQLR.getRowNum() == 0) {
//                    continue;
//                }
//
//                QLresponseID = (int) rowQLR.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
//                if (mResponseModel.getMresponseID() == QLresponseID) {
//                    qualitativeChoiceID = (int) rowQLR.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
//                }
//            }
//
//            if (!addMResponse(mResponseModel,
//                    QNresponseID, unitID, value, QLresponseID, qualitativeChoiceID)) {
//                return false;
//            }
//        }
//        return true;
//    }
//
//    public boolean addMResponse(cMResponseModel mResponseModel, int QNresponseID, int unitID, double value,
//                                int QLresponseID, int qualitativeChoiceID) {
//        // open the connection to the database
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//
//        // create content object for storing data
//        ContentValues cv = new ContentValues();
//
//        // assign values to the table fields
//        cv.put(cSQLDBHelper.KEY_ID, mResponseModel.getMresponseID());
//        cv.put(cSQLDBHelper.KEY_INDICATOR_MILESTONE_FK_ID, mResponseModel.getIndicatorMilestoneID());
//        cv.put(cSQLDBHelper.KEY_DATA_COLLECTOR_FK_ID, mResponseModel.getDataCollectorID());
//
//        // insert evaluation type details
//        try {
//            if (db.insert(cSQLDBHelper.TABLE_tblINDICATORPROGRESS, null, cv) < 0) {
//                return false;
//            }
//
//            if (QNresponseID != -1) {
//                return addQuantitativeResponse(mResponseModel.getMresponseID(), unitID, value);
//            }
//
//            if (QLresponseID != -1) {
//                return addQualitativeResponse(mResponseModel.getMresponseID(), qualitativeChoiceID);
//            }
//
//        } catch (Exception e) {
//            Log.d(TAG, "Exception in importing EVALUATION RESPONSES from Excel: " + e.getMessage());
//        }
//
//        // close the database connection
//        db.close();
//
//        return true;
//    }
//
//    public boolean addQuantitativeResponse(int mresponseID, int unitID, double value) {
//        // open the connection to the database
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//
//        // create content object for storing data
//        ContentValues cv = new ContentValues();
//
//        // assign values to the table fields
//        cv.put(cSQLDBHelper.KEY_MRESPONSE_FK_ID, mresponseID);
//        cv.put(cSQLDBHelper.KEY_UNIT_FK_ID, unitID);
//        cv.put(cSQLDBHelper.KEY_TARGET_VALUE, value);
//
//        // insert details
//        try {
//            if (db.insert(cSQLDBHelper.TABLE_tblQUANTITATIVEPROGRESS, null, cv) < 0) {
//                return false;
//            }
//        } catch (Exception e) {
//            Log.d(TAG, "Exception in importing QUANTITATIVE RESPONSE from Excel: " + e.getMessage());
//        }
//
//        // close the database connection
//        db.close();
//
//        return true;
//    }
//
//    public boolean addQualitativeResponse(int mresponseID, int qualitativeChoiceID) {
//        // open the connection to the database
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//
//        // create content object for storing data
//        ContentValues cv = new ContentValues();
//
//        // assign values to the table fields
//        cv.put(cSQLDBHelper.KEY_MRESPONSE_FK_ID, mresponseID);
//        cv.put(cSQLDBHelper.KEY_QUALITATIVE_CHOICE_FK_ID, qualitativeChoiceID);
//
//        // insert details
//        try {
//            if (db.insert(cSQLDBHelper.TABLE_tblQUALITATIVEPROGRESS, null, cv) < 0) {
//                return false;
//            }
//        } catch (Exception e) {
//            Log.d(TAG, "Exception in importing QUALITATIVE RESPONSE from Excel: " + e.getMessage());
//        }
//
//        // close the database connection
//        db.close();
//
//        return true;
//    }
//
//
//    @Override
//    public boolean deleteMresponses() {
//        // open the connection to the database
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//
//        // delete all records
//        long result = db.delete(cSQLDBHelper.TABLE_tblINDICATORPROGRESS, null, null);
//
//        // close the database connection
//        db.close();
//
//        return result > -1;
//    }
//
//    @Override
//    public boolean deleteQuantitativeResponses() {
//        // open the connection to the database
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//
//        // delete all records
//        long result = db.delete(cSQLDBHelper.TABLE_tblQUANTITATIVEPROGRESS, null, null);
//
//        // close the database connection
//        db.close();
//
//        return result > -1;
//    }
//
//    @Override
//    public boolean deleteQualitativeResponses() {
//        // open the connection to the database
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//
//        // delete all records
//        long result = db.delete(cSQLDBHelper.TABLE_tblQUALITATIVEPROGRESS, null, null);
//
//        // close the database connection
//        db.close();
//
//        return result > -1;
//    }
//
//
//
//    /*################################## INDICATOR TYPE FUNCTIONS ################################*/
//
//    @Override
//    public boolean addIndicatorTypeFromExcel() {
//        Workbook workbook = excelHelper.getWorkbookMONITORING();
//        Sheet ITSheet = workbook.getSheet(cExcelHelper.SHEET_tblINDICATORTYPE);
//
//        if (ITSheet == null) {
//            return false;
//        }
//
//        for (Row cRowIT : ITSheet) {
//            //just skip the row if row number is 0
//            if (cRowIT.getRowNum() == 0) {
//                continue;
//            }
//
//            cIndicatorTypeModel indicatorTypeModel = new cIndicatorTypeModel();
//
//            indicatorTypeModel.setIndicatorTypeID((int)
//                    cRowIT.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
//            indicatorTypeModel.setName(
//                    cRowIT.getCell(1, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
//            indicatorTypeModel.setDescription(
//                    cRowIT.getCell(2, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
//
//            /* get quantitative of the indicator type */
//            int quantitativeID = -1, unitID = -1;
//            Sheet quantitativeSheet = workbook.getSheet(cExcelHelper.SHEET_tblQUANTITATIVE);
//            for (Row rowQuantitative : quantitativeSheet) {
//                //just skip the row if row number is 0
//                if (rowQuantitative.getRowNum() == 0) {
//                    continue;
//                }
//
//                quantitativeID = (int) rowQuantitative.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
//                if (indicatorTypeModel.getIndicatorTypeID() == quantitativeID) {
//                    unitID = (int) rowQuantitative.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
//                    break;
//                } else {
//                    quantitativeID = -1;
//                }
//            }
//
//
//            /* get qualitative of the indicator type */
//            int qualitativeID = -1;
//            Sheet qualitativeSheet = workbook.getSheet(cExcelHelper.SHEET_tblQUALITATIVE);
//            for (Row rowQualitative : qualitativeSheet) {
//                //just skip the row if row number is 0
//                if (rowQualitative.getRowNum() == 0) {
//                    continue;
//                }
//
//                qualitativeID = (int) rowQualitative.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
//                if (indicatorTypeModel.getIndicatorTypeID() == qualitativeID) {
//                    break;
//                } else {
//                    qualitativeID = -1;
//                }
//            }
//
//            Gson gson = new Gson();
//            Log.d(TAG, gson.toJson(indicatorTypeModel));
//
//            /* get choice grouped by indicator and indicator type */
//            Map<Integer, Set<Pair<Integer, Integer>>> citMap = new HashMap<>();
//            Set<Pair<Integer, Integer>> itSet = new HashSet<>();
//            int indicatorID, typeID, choiceID;
//
//            Sheet citSheet = workbook.getSheet(cExcelHelper.SHEET_tblQUALITATIVECHOICESET);
//            for (Row rowCIT : citSheet) {
//                //just skip the row if row number is 0
//                if (rowCIT.getRowNum() == 0) {
//                    continue;
//                }
//
//                typeID = (int) rowCIT.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
//                if (indicatorTypeModel.getIndicatorTypeID() == typeID) {
//                    indicatorID = (int) rowCIT.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
//                    choiceID = (int) rowCIT.getCell(2, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
//                    itSet.add(new Pair<>(indicatorID, choiceID));
//                }
//            }
//            citMap.put(indicatorTypeModel.getIndicatorTypeID(), itSet);
//
//
//            if (!addIndicatorType(indicatorTypeModel, citMap, quantitativeID, qualitativeID, unitID)) {
//                return false;
//            }
//        }
//
//        return true;
//    }
//
//    public boolean addIndicatorType(cIndicatorTypeModel indicatorTypeModel,
//                                    Map<Integer, Set<Pair<Integer, Integer>>> citMap,
//                                    int quantitativeID, int qualitativeID, int unitID) {
//
//        // open the connection to the database
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//
//        // create content object for storing data
//        ContentValues cv = new ContentValues();
//
//        // assign values to the table fields
//        cv.put(cSQLDBHelper.KEY_ID, indicatorTypeModel.getIndicatorTypeID());
//        cv.put(cSQLDBHelper.KEY_NAME, indicatorTypeModel.getName());
//        cv.put(cSQLDBHelper.KEY_DESCRIPTION, indicatorTypeModel.getDescription());
//
//        // insert record details
//        try {
//            if (db.insert(cSQLDBHelper.TABLE_tblINDICATOR, null, cv) < 0) {
//                return false;
//            }
//
//            if (quantitativeID != -1) {
//                return addQuantitative(quantitativeID, unitID);
//            }
//
//            if (qualitativeID != -1) {
//                return addQualitative(qualitativeID, citMap);
//            }
//
//        } catch (Exception e) {
//            Log.d(TAG, "Exception in importing INDICATOR TYPE from Excel: " + e.getMessage());
//        }
//
//        // close the database connection
//        db.close();
//
//        return true;
//    }
//
//    public boolean addQuantitative(int quantitativeID, int unitID) {
//        // open the connection to the database
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//
//        // create content object for storing data
//        ContentValues cv = new ContentValues();
//
//        // assign values to the table fields
//        cv.put(cSQLDBHelper.KEY_INDICATOR_TYPE_FK_ID, quantitativeID);
//        cv.put(cSQLDBHelper.KEY_UNIT_FK_ID, unitID);
//
//        // insert evaluation type details
//        try {
//            if (db.insert(cSQLDBHelper.TABLE_tblQUANTITATIVEINDICATOR, null, cv) < 0) {
//                return false;
//            }
//        } catch (Exception e) {
//            Log.d(TAG, "Exception in importing QUANTITATIVE from Excel: " + e.getMessage());
//        }
//
//        // close the database connection
//        db.close();
//
//        return true;
//    }
//
//    public boolean addQualitative(int qualitativeID,
//                                  Map<Integer, Set<Pair<Integer, Integer>>> citMap) {
//        // open the connection to the database
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//
//        // create content object for storing data
//        ContentValues cv = new ContentValues();
//
//        // assign values to the table fields
//        cv.put(cSQLDBHelper.KEY_INDICATOR_TYPE_FK_ID, qualitativeID);
//
//        // insert evaluation type details
//        try {
//            if (db.insert(cSQLDBHelper.TABLE_tblQUALITATIVEINDICATOR, null, cv) < 0) {
//                return false;
//            }
//
//            // add indicator, type and choice tuple
//            for (Map.Entry<Integer, Set<Pair<Integer, Integer>>> citEntry : citMap.entrySet()) {
//                int typeID = citEntry.getKey();
//                for (Pair<Integer, Integer> itPair : citEntry.getValue()) {
//                    int indicatorID = itPair.first;
//                    int choiceID = itPair.second;
//
//                    if (!addIndicatorTypeChoice(indicatorID, typeID, choiceID))
//                        return false;
//                }
//            }
//
//        } catch (Exception e) {
//            Log.d(TAG, "Exception in importing QUALITATIVE from Excel: " + e.getMessage());
//        }
//
//        // close the database connection
//        db.close();
//
//        return true;
//    }
//
//    public boolean addIndicatorTypeChoice(int indicatorID, int typeID, int choiceID) {
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//
//        ContentValues cv = new ContentValues();
//
//        cv.put(cSQLDBHelper.KEY_INDICATOR_FK_ID, indicatorID);
//        cv.put(cSQLDBHelper.KEY_INDICATOR_TYPE_FK_ID, typeID);
//        cv.put(cSQLDBHelper.KEY_QUALITATIVE_CHOICE_FK_ID, choiceID);
//
//        return db.insert(cSQLDBHelper.TABLE_tblQUALITATIVECRITERIA, null, cv) >= 0;
//    }
//
//    public boolean deleteIndicatorTypes() {
//        // open the connection to the database
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//
//        // delete all records
//        long result = db.delete(cSQLDBHelper.TABLE_tblINDICATOR, null, null);
//
//        // close the database connection
//        db.close();
//
//        return result > -1;
//    }
//
//    public boolean deleteQuantitatives() {
//        // open the connection to the database
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//
//        // delete all records
//        long result = db.delete(cSQLDBHelper.TABLE_tblQUANTITATIVEINDICATOR, null, null);
//
//        // close the database connection
//        db.close();
//
//        return result > -1;
//    }
//
//    public boolean deleteQualitatives() {
//        // open the connection to the database
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//
//        // delete all records
//        long result = db.delete(cSQLDBHelper.TABLE_tblQUALITATIVEINDICATOR, null, null);
//
//        // close the database connection
//        db.close();
//
//        return result > -1;
//    }
//
//    public boolean deleteQualitativeChoiceSets() {
//        // open the connection to the database
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//
//        // delete all records
//        long result = db.delete(cSQLDBHelper.TABLE_tblQUALITATIVECRITERIA, null, null);
//
//        // close the database connection
//        db.close();
//
//        return result > -1;
//    }
//
//    /*################################ QUALITATIVE CHOICE FUNCTIONS ##############################*/
//
//    @Override
//    public boolean addQualitativeChoiceFromExcel() {
//        Workbook workbook = excelHelper.getWorkbookMONITORING();
//        Sheet QCSheet = workbook.getSheet(cExcelHelper.SHEET_tblQUALITATIVECHOICE);
//
//        if (QCSheet == null) {
//            return false;
//        }
//
//        for (Row cRowQC : QCSheet) {
//            //just skip the row if row number is 0
//            if (cRowQC.getRowNum() == 0) {
//                continue;
//            }
//
//            cQualitativeChoiceModel qualitativeChoiceModel = new cQualitativeChoiceModel();
//
//            qualitativeChoiceModel.setQualitativeChoiceID((int)
//                    cRowQC.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
//            qualitativeChoiceModel.setName(
//                    cRowQC.getCell(1, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
//            qualitativeChoiceModel.setDescription(
//                    cRowQC.getCell(2, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
//
//            if (!addQualitativeChoice(qualitativeChoiceModel)) {
//                return false;
//            }
//        }
//
//        return true;
//    }
//
//    public boolean addQualitativeChoice(cQualitativeChoiceModel qualitativeChoiceModel) {
//        // open the connection to the database
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//
//        // create content object for storing data
//        ContentValues cv = new ContentValues();
//
//        // assign values to the table fields
//        cv.put(cSQLDBHelper.KEY_ID, qualitativeChoiceModel.getQualitativeChoiceID());
//        cv.put(cSQLDBHelper.KEY_NAME, qualitativeChoiceModel.getName());
//        cv.put(cSQLDBHelper.KEY_DESCRIPTION, qualitativeChoiceModel.getDescription());
//
//        // insert record details
//        try {
//            if (db.insert(cSQLDBHelper.TABLE_tblQUALITATIVECRITERIA, null, cv) < 0) {
//                return false;
//            }
//        } catch (Exception e) {
//            Log.d(TAG, "Exception in importing QUALITATIVE CHOICE from Excel: " + e.getMessage());
//        }
//
//        // close the database connection
//        db.close();
//
//        return true;
//    }
//
//    public boolean deleteQualitativeChoices() {
//        // open the connection to the database
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//
//        // delete all records
//        long result = db.delete(cSQLDBHelper.TABLE_tblQUALITATIVECRITERIA, null, null);
//
//        // close the database connection
//        db.close();
//
//        return result > -1;
//    }
