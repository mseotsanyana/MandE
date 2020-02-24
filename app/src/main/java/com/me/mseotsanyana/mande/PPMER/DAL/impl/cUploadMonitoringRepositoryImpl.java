package com.me.mseotsanyana.mande.PPMER.DAL.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.util.Pair;

import com.google.gson.Gson;
import com.me.mseotsanyana.mande.PPMER.BLL.repository.iUploadMonitoringRepository;
import com.me.mseotsanyana.mande.PPMER.DAL.model.cDataCollectorModel;
import com.me.mseotsanyana.mande.PPMER.DAL.model.cIndicatorModel;
import com.me.mseotsanyana.mande.PPMER.DAL.model.cIndicatorTypeModel;
import com.me.mseotsanyana.mande.PPMER.DAL.model.cMOVModel;
import com.me.mseotsanyana.mande.PPMER.DAL.model.cMResponseModel;
import com.me.mseotsanyana.mande.PPMER.DAL.model.cMethodModel;
import com.me.mseotsanyana.mande.PPMER.DAL.model.cMilestoneModel;
import com.me.mseotsanyana.mande.PPMER.DAL.model.cQualitativeChoiceModel;
import com.me.mseotsanyana.mande.PPMER.DAL.model.cUnitModel;
import com.me.mseotsanyana.mande.STORAGE.database.cSQLDBHelper;
import com.me.mseotsanyana.mande.STORAGE.excel.cExcelHelper;
import com.me.mseotsanyana.mande.UTILITY.cConstant;

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
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import static com.me.mseotsanyana.mande.UTILITY.cConstant.CONS_QUARTERLY_ID;

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

    /*####################################### MOV FUNCTIONS ######################################*/

    @Override
    public boolean addMOVFromExcel() {
        Workbook workbook = excelHelper.getWorkbookMONITORING();
        Sheet MOVSheet = workbook.getSheet(cExcelHelper.SHEET_tblMOV);

        if (MOVSheet == null) {
            return false;
        }

        for (Iterator<Row> ritMOV = MOVSheet.iterator(); ritMOV.hasNext(); ) {
            Row cRowMOV = ritMOV.next();

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

    /*###################################### METHOD FUNCTIONS ####################################*/

    @Override
    public boolean addMethodFromExcel() {
        Workbook workbook = excelHelper.getWorkbookMONITORING();
        Sheet MSheet = workbook.getSheet(cExcelHelper.SHEET_tblMETHOD);

        if (MSheet == null) {
            return false;
        }

        for (Iterator<Row> ritM = MSheet.iterator(); ritM.hasNext(); ) {
            Row cRowM = ritM.next();

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

    /*####################################### UNIT FUNCTIONS #####################################*/

    @Override
    public boolean addUnitFromExcel() {
        Workbook workbook = excelHelper.getWorkbookMONITORING();
        Sheet USheet = workbook.getSheet(cExcelHelper.SHEET_tblUNIT);

        if (USheet == null) {
            return false;
        }

        for (Iterator<Row> ritU = USheet.iterator(); ritU.hasNext(); ) {
            Row cRowU = ritU.next();

            //just skip the row if row number is 0
            if (cRowU.getRowNum() == 0) {
                continue;
            }

            cUnitModel unitModel = new cUnitModel();

            unitModel.setUnitID((int)
                    cRowU.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            unitModel.setName(
                    cRowU.getCell(1, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
            unitModel.setDescription(
                    cRowU.getCell(2, Row.CREATE_NULL_AS_BLANK).getStringCellValue());

            if (!addUnit(unitModel)) {
                return false;
            }
        }

        return true;
    }

    public boolean addUnit(cUnitModel unitModel) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_ID, unitModel.getUnitID());
        cv.put(cSQLDBHelper.KEY_NAME, unitModel.getName());
        cv.put(cSQLDBHelper.KEY_DESCRIPTION, unitModel.getDescription());

        // insert record details
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblUNIT, null, cv) < 0) {
                return false;
            }
        } catch (Exception e) {
            Log.d(TAG, "Exception in importing UNIT from Excel: " + e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }

    public boolean deleteUnits() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblUNIT, null, null);

        // close the database connection
        db.close();

        return result > -1;
    }

    /*################################## INDICATOR TYPE FUNCTIONS ################################*/

    @Override
    public boolean addIndicatorTypeFromExcel() {
        Workbook workbook = excelHelper.getWorkbookMONITORING();
        Sheet ITSheet = workbook.getSheet(cExcelHelper.SHEET_tblINDICATORTYPE);

        if (ITSheet == null) {
            return false;
        }

        for (Iterator<Row> ritIT = ITSheet.iterator(); ritIT.hasNext(); ) {
            Row cRowIT = ritIT.next();

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

            /* get quantitative of the indicator type */
            int quantitativeID = -1, unitID = -1;
            Sheet quantitativeSheet = workbook.getSheet(cExcelHelper.SHEET_tblQUANTITATIVE);
            for (Iterator<Row> ritQuantitative = quantitativeSheet.iterator(); ritQuantitative.hasNext(); ) {
                Row rowQuantitative = ritQuantitative.next();

                //just skip the row if row number is 0
                if (rowQuantitative.getRowNum() == 0) {
                    continue;
                }

                quantitativeID = (int) rowQuantitative.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                if (indicatorTypeModel.getIndicatorTypeID() == quantitativeID) {
                    unitID = (int) rowQuantitative.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                    break;
                }else{
                    quantitativeID = -1;
                }
            }


            /* get qualitative of the indicator type */
            int qualitativeID = -1;
            Sheet qualitativeSheet = workbook.getSheet(cExcelHelper.SHEET_tblQUALITATIVE);
            for (Iterator<Row> ritQualitative = qualitativeSheet.iterator(); ritQualitative.hasNext(); ) {
                Row rowQualitative = ritQualitative.next();

                //just skip the row if row number is 0
                if (rowQualitative.getRowNum() == 0) {
                    continue;
                }

                qualitativeID = (int) rowQualitative.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                if (indicatorTypeModel.getIndicatorTypeID() == qualitativeID) {
                    break;
                }else {
                    qualitativeID = -1;
                }
            }

            Gson gson = new Gson();
            Log.d(TAG, gson.toJson(indicatorTypeModel));

            /* get choice grouped by indicator and indicator type */
            Map<Integer, Set<Pair<Integer, Integer>>> citMap = new HashMap<>();
            Set<Pair<Integer, Integer>> itSet = new HashSet<>();
            int indicatorID, typeID, choiceID;

            Sheet citSheet = workbook.getSheet(cExcelHelper.SHEET_tblQUALITATIVECHOICESET);
            for (Iterator<Row> ritCIT = citSheet.iterator(); ritCIT.hasNext(); ) {
                Row rowCIT = ritCIT.next();

                //just skip the row if row number is 0
                if (rowCIT.getRowNum() == 0) {
                    continue;
                }

                typeID = (int) rowCIT.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                if (indicatorTypeModel.getIndicatorTypeID() == typeID) {
                    indicatorID = (int) rowCIT.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                    choiceID = (int) rowCIT.getCell(2, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                    itSet.add(new Pair<>(indicatorID, choiceID));
                }
            }
            citMap.put(indicatorTypeModel.getIndicatorTypeID(), itSet);



            if (!addIndicatorType(indicatorTypeModel, citMap, quantitativeID, qualitativeID, unitID)) {
                return false;
            }
        }

        return true;
    }

    public boolean addIndicatorType(cIndicatorTypeModel indicatorTypeModel,
                                    Map<Integer, Set<Pair<Integer, Integer>>> citMap,
                                    int quantitativeID, int qualitativeID, int unitID) {

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

            if (quantitativeID != -1) {
                if (addQuantitative(quantitativeID, unitID))
                    return true;
                else
                    return false;
            }

            if (qualitativeID != -1) {
                if (addQualitative(qualitativeID, citMap))
                    return true;
                else
                    return false;
            }

        } catch (Exception e) {
            Log.d(TAG, "Exception in importing INDICATOR TYPE from Excel: " + e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }

    public boolean addQuantitative(int quantitativeID, int unitID) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_INDICATOR_TYPE_FK_ID, quantitativeID);
        cv.put(cSQLDBHelper.KEY_UNIT_FK_ID, unitID);

        // insert evaluation type details
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblQUANTITATIVE, null, cv) < 0) {
                return false;
            }
        } catch (Exception e) {
            Log.d(TAG, "Exception in importing QUANTITATIVE from Excel: " + e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }

    public boolean addQualitative(int qualitativeID,
                                  Map<Integer, Set<Pair<Integer, Integer>>> citMap) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_INDICATOR_TYPE_FK_ID, qualitativeID);

        // insert evaluation type details
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblQUALITATIVE, null, cv) < 0) {
                return false;
            }

            // add indicator, type and choice tuple
            for (Map.Entry<Integer, Set<Pair<Integer, Integer>>> citEntry : citMap.entrySet()) {
                int typeID = citEntry.getKey();
                for (Pair<Integer, Integer> itPair : citEntry.getValue()) {
                    int indicatorID = itPair.first;
                    int choiceID = itPair.second;

                    if (addIndicatorTypeChoice(indicatorID, typeID, choiceID))
                        continue;
                    else
                        return false;
                }
            }

        } catch (Exception e) {
            Log.d(TAG, "Exception in importing QUALITATIVE from Excel: " + e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }

    public boolean addIndicatorTypeChoice(int indicatorID, int typeID, int choiceID) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(cSQLDBHelper.KEY_INDICATOR_FK_ID, indicatorID);
        cv.put(cSQLDBHelper.KEY_INDICATOR_TYPE_FK_ID, typeID);
        cv.put(cSQLDBHelper.KEY_QUALITATIVE_CHOICE_FK_ID, choiceID);

        if (db.insert(cSQLDBHelper.TABLE_tblQUALITATIVECHOICESET, null, cv) < 0) {
            return false;
        }

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

    public boolean deleteQuantitatives() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblQUANTITATIVE, null, null);

        // close the database connection
        db.close();

        return result > -1;
    }

    public boolean deleteQualitatives() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblQUALITATIVE, null, null);

        // close the database connection
        db.close();

        return result > -1;
    }

    public boolean deleteQualitativeChoiceSets() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblQUALITATIVECHOICESET, null, null);

        // close the database connection
        db.close();

        return result > -1;
    }

    /*################################ QUALITATIVE CHOICE FUNCTIONS ##############################*/

    @Override
    public boolean addQualitativeChoiceFromExcel() {
        Workbook workbook = excelHelper.getWorkbookMONITORING();
        Sheet QCSheet = workbook.getSheet(cExcelHelper.SHEET_tblQUALITATIVECHOICE);

        if (QCSheet == null) {
            return false;
        }

        for (Iterator<Row> ritQC = QCSheet.iterator(); ritQC.hasNext(); ) {
            Row cRowQC = ritQC.next();

            //just skip the row if row number is 0
            if (cRowQC.getRowNum() == 0) {
                continue;
            }

            cQualitativeChoiceModel qualitativeChoiceModel = new cQualitativeChoiceModel();

            qualitativeChoiceModel.setQualitativeChoiceID((int)
                    cRowQC.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            qualitativeChoiceModel.setName(
                    cRowQC.getCell(1, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
            qualitativeChoiceModel.setDescription(
                    cRowQC.getCell(2, Row.CREATE_NULL_AS_BLANK).getStringCellValue());

            if (!addQualitativeChoice(qualitativeChoiceModel)) {
                return false;
            }
        }

        return true;
    }

    public boolean addQualitativeChoice(cQualitativeChoiceModel qualitativeChoiceModel) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_ID, qualitativeChoiceModel.getQualitativeChoiceID());
        cv.put(cSQLDBHelper.KEY_NAME, qualitativeChoiceModel.getName());
        cv.put(cSQLDBHelper.KEY_DESCRIPTION, qualitativeChoiceModel.getDescription());

        // insert record details
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblQUALITATIVECHOICE, null, cv) < 0) {
                return false;
            }
        } catch (Exception e) {
            Log.d(TAG, "Exception in importing QUALITATIVE CHOICE from Excel: " + e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }

    public boolean deleteQualitativeChoices() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblQUALITATIVECHOICE, null, null);

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

        for (Iterator<Row> ritDC = DCSheet.iterator(); ritDC.hasNext(); ) {
            Row cRowDC = ritDC.next();

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

    /*###################################### INDICATOR FUNCTIONS #################################*/

    @Override
    public boolean addIndicatorFromExcel() {
        Workbook workbook = excelHelper.getWorkbookMONITORING();
        Sheet indicatorSheet = workbook.getSheet(cExcelHelper.SHEET_tblINDICATOR);

        if (indicatorSheet == null) {
            return false;
        }

        for (Iterator<Row> ritIndicator = indicatorSheet.iterator(); ritIndicator.hasNext(); ) {
            Row cRow = ritIndicator.next();

            //just skip the row if row number is 0
            if (cRow.getRowNum() == 0) {
                continue;
            }

            cIndicatorModel indicatorModel = new cIndicatorModel();

            indicatorModel.setIndicatorID((int) cRow.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            indicatorModel.setLogFrameID((int) cRow.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            indicatorModel.setIndicatorTypeID((int) cRow.getCell(2, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            indicatorModel.setFrequencyID((int) cRow.getCell(3, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            indicatorModel.setDataCollectorID((int) cRow.getCell(4, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            indicatorModel.setName(cRow.getCell(5, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
            indicatorModel.setDescription(cRow.getCell(6, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
            indicatorModel.setQuestion(cRow.getCell(7, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
            indicatorModel.setStartDate(cRow.getCell(8, Row.CREATE_NULL_AS_BLANK).getDateCellValue());
            indicatorModel.setEndDate(cRow.getCell(9, Row.CREATE_NULL_AS_BLANK).getDateCellValue());

            Gson gson = new Gson();

            //ArrayList<String> quarters = new ArrayList<>();
            ArrayList<cMilestoneModel> milestoneModels = new ArrayList<>();
            if (indicatorModel.getFrequencyID() == CONS_QUARTERLY_ID) {
                /* add indicator milestone */
                LocalDate startDate = convertDate2LocalDate(indicatorModel.getStartDate());
                LocalDate endDate = convertDate2LocalDate(indicatorModel.getEndDate());
                milestoneModels = generateQuarterlySequence(startDate, endDate);
            }


            if (!addIndicator(indicatorModel, milestoneModels)) {
                return false;
            }
        }

        return true;
    }

    public boolean addIndicator(cIndicatorModel indicatorModel,
                                ArrayList<cMilestoneModel> milestoneModels) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_ID, indicatorModel.getIndicatorID());
        cv.put(cSQLDBHelper.KEY_LOGFRAME_FK_ID, indicatorModel.getLogFrameID());
        cv.put(cSQLDBHelper.KEY_INDICATOR_TYPE_FK_ID, indicatorModel.getIndicatorTypeID());
        cv.put(cSQLDBHelper.KEY_FREQUENCY_FK_ID, indicatorModel.getFrequencyID());
        cv.put(cSQLDBHelper.KEY_DATA_COLLECTOR_FK_ID, indicatorModel.getDataCollectorID());
        cv.put(cSQLDBHelper.KEY_NAME, indicatorModel.getName());
        cv.put(cSQLDBHelper.KEY_DESCRIPTION, indicatorModel.getDescription());
        cv.put(cSQLDBHelper.KEY_QUESTION, indicatorModel.getQuestion());
        cv.put(cSQLDBHelper.KEY_START_DATE, String.valueOf(indicatorModel.getStartDate()));
        cv.put(cSQLDBHelper.KEY_END_DATE, String.valueOf(indicatorModel.getEndDate()));

        // insert project details
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblINDICATOR, null, cv) < 0) {
                return false;
            }

            for (cMilestoneModel milestoneModel : milestoneModels) {
                if (!addMilestone(milestoneModel, indicatorModel.getIndicatorID())) {
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

    public boolean addMilestone(cMilestoneModel milestoneModel, int indicatorID) {
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

            if(!addIndicatorMilestone((int)milestoneID, indicatorID)){
                return false;
            }

        } catch (Exception e) {
            Log.d(TAG, "Exception in importing MILESTONE from Excel: " + e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }

    public boolean addIndicatorMilestone(int milestoneID, int indicatorID) {
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

    public ArrayList<cMilestoneModel> generateQuarterlySequence(LocalDate startDate, LocalDate endDate) {
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

    /* ############################### MONITORING RESPONSE FUNCTIONS #############################*/

    @Override
    public boolean addMResponseFromExcel() {
        Workbook workbook = excelHelper.getWorkbookMONITORING();
        Sheet MRSheet = workbook.getSheet(cExcelHelper.SHEET_tblMRESPONSE);

        if (MRSheet == null) {
            return false;
        }

        for (Iterator<Row> ritMR = MRSheet.iterator(); ritMR.hasNext(); ) {
            Row cRow = ritMR.next();

            //just skip the row if row number is 0
            if (cRow.getRowNum() == 0) {
                continue;
            }

            cMResponseModel mResponseModel = new cMResponseModel();

            mResponseModel.setMresponseID((int)
                    cRow.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            mResponseModel.setIndicatorMilestoneID((int)
                    cRow.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            mResponseModel.setDataCollectorID((int)
                    cRow.getCell(2, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());

            /* get quantitative responses */
            int QNresponseID = -1, unitID = -1;
            double value = 0.0;
            Sheet QNSheet = workbook.getSheet(cExcelHelper.SHEET_tblQUANTITATIVERESPONSE);
            for (Iterator<Row> ritQNR = QNSheet.iterator(); ritQNR.hasNext(); ) {
                Row rowQNR = ritQNR.next();

                //just skip the row if row number is 0
                if (rowQNR.getRowNum() == 0) {
                    continue;
                }

                QNresponseID = (int) rowQNR.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                if (mResponseModel.getMresponseID() == QNresponseID) {
                    unitID = (int) rowQNR.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                    value = (int) rowQNR.getCell(2, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                }else {
                    QNresponseID = -1;
                }
            }

            /* get qualitative responses */
            int QLresponseID = -1, qualitativeChoiceID = -1;
            Sheet QLRSheet = workbook.getSheet(cExcelHelper.SHEET_tblQUALITATIVERESPONSE);
            for (Iterator<Row> ritQLR = QLRSheet.iterator(); ritQLR.hasNext(); ) {
                Row rowQLR = ritQLR.next();

                //just skip the row if row number is 0
                if (rowQLR.getRowNum() == 0) {
                    continue;
                }

                QLresponseID = (int) rowQLR.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                if (mResponseModel.getMresponseID() == QLresponseID) {
                    qualitativeChoiceID = (int) rowQLR.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                }
            }

            if (!addMResponse(mResponseModel,
                    QNresponseID, unitID, value, QLresponseID,  qualitativeChoiceID)) {
                return false;
            }
        }
        return true;
    }

    public boolean addMResponse(cMResponseModel mResponseModel, int QNresponseID, int unitID, double value,
                                int QLresponseID, int qualitativeChoiceID) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_ID, mResponseModel.getMresponseID());
        cv.put(cSQLDBHelper.KEY_INDICATOR_MILESTONE_FK_ID, mResponseModel.getIndicatorMilestoneID());
        cv.put(cSQLDBHelper.KEY_DATA_COLLECTOR_FK_ID, mResponseModel.getDataCollectorID());

        // insert evaluation type details
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblMRESPONSE, null, cv) < 0) {
                return false;
            }

            if (QNresponseID != -1) {
                if (addQuantitativeResponse(mResponseModel.getMresponseID(), unitID, value))
                    return true;
                else
                    return false;
            }

            if (QLresponseID != -1) {
                if (addQualitativeResponse(mResponseModel.getMresponseID(), qualitativeChoiceID))
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

    public boolean addQuantitativeResponse(int mresponseID, int unitID, double value) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_MRESPONSE_FK_ID, mresponseID);
        cv.put(cSQLDBHelper.KEY_UNIT_FK_ID, unitID);
        cv.put(cSQLDBHelper.KEY_TARGET_VALUE, value);

        // insert details
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblQUANTITATIVERESPONSE, null, cv) < 0) {
                return false;
            }
        } catch (Exception e) {
            Log.d(TAG, "Exception in importing QUANTITATIVE RESPONSE from Excel: " + e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }

    public boolean addQualitativeResponse(int mresponseID, int qualitativeChoiceID) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_MRESPONSE_FK_ID, mresponseID);
        cv.put(cSQLDBHelper.KEY_QUALITATIVE_CHOICE_FK_ID, qualitativeChoiceID);

        // insert details
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblQUALITATIVERESPONSE, null, cv) < 0) {
                return false;
            }
        } catch (Exception e) {
            Log.d(TAG, "Exception in importing QUALITATIVE RESPONSE from Excel: " + e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }


    @Override
    public boolean deleteMresponses() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblMRESPONSE, null, null);

        // close the database connection
        db.close();

        return result > -1;
    }

    @Override
    public boolean deleteQuantitativeResponses() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblQUANTITATIVERESPONSE, null, null);

        // close the database connection
        db.close();

        return result > -1;
    }

    @Override
    public boolean deleteQualitativeResponses() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblQUALITATIVERESPONSE, null, null);

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