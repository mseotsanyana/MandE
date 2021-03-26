package com.me.mseotsanyana.mande.DAL.ìmpl.sqlite.common;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.me.mseotsanyana.mande.BLL.repository.common.iUploadGlobalRepository;
import com.me.mseotsanyana.mande.BLL.model.common.cChartModel;
import com.me.mseotsanyana.mande.BLL.model.common.cFiscalYearModel;
import com.me.mseotsanyana.mande.BLL.model.common.cFrequencyModel;
import com.me.mseotsanyana.mande.BLL.model.common.cPeriodModel;
import com.me.mseotsanyana.mande.DAL.storage.database.cSQLDBHelper;
import com.me.mseotsanyana.mande.DAL.storage.excel.cExcelHelper;
import com.me.mseotsanyana.mande.UTIL.cConstant;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.text.SimpleDateFormat;

public class cUploadGlobalRepositoryImpl implements iUploadGlobalRepository {
    private static SimpleDateFormat sdf = cConstant.FORMAT_DATE;
    private static String TAG = cUploadGlobalRepositoryImpl.class.getSimpleName();

    /* an object of the database helper */
    private cSQLDBHelper dbHelper;
    /* an object of the excel helper */
    private cExcelHelper excelHelper;

    public cUploadGlobalRepositoryImpl(Context context) {
        dbHelper = new cSQLDBHelper(context);
        excelHelper = new cExcelHelper(context);
    }

    /*#################################### FREQUENCY FUNCTIONS ###################################*/

    @Override
    public boolean addFrequencyFromExcel() {
        Workbook workbook = excelHelper.getWorkbookGLOBAL();
        Sheet FSheet = workbook.getSheet(cExcelHelper.SHEET_tblFREQUENCY);

        if (FSheet == null) {
            return false;
        }

        for (Row cRowF : FSheet) {
            //just skip the row if row number is 0
            if (cRowF.getRowNum() == 0) {
                continue;
            }

            cFrequencyModel frequencyModel = new cFrequencyModel();

            frequencyModel.setFrequencyID((int)
                    cRowF.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            frequencyModel.setName(
                    cRowF.getCell(1, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
            frequencyModel.setDescription(
                    cRowF.getCell(2, Row.CREATE_NULL_AS_BLANK).getStringCellValue());

            if (!addFrequency(frequencyModel)) {
                return false;
            }
        }

        return true;
    }

    public boolean addFrequency(cFrequencyModel frequencyModel){
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_ID, frequencyModel.getFrequencyID());
        cv.put(cSQLDBHelper.KEY_NAME, frequencyModel.getName());
        cv.put(cSQLDBHelper.KEY_DESCRIPTION, frequencyModel.getDescription());

        // insert record details
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblFREQUENCY, null, cv) < 0) {
                return false;
            }
        } catch (Exception e) {
            Log.d(TAG, "Exception in importing FREQUENCY from Excel: " + e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }

    @Override
    public boolean deleteFrequencies() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblFREQUENCY, null, null);

        // close the database connection
        db.close();

        return result > -1;
    }

    /*################################### FISCAL YEAR FUNCTIONS ##################################*/

    @Override
    public boolean addFiscalYearFromExcel() {
        Workbook workbook = excelHelper.getWorkbookGLOBAL();
        Sheet FYSheet = workbook.getSheet(cExcelHelper.SHEET_tblFISCALYEAR);
        Sheet PSheet = workbook.getSheet(cExcelHelper.SHEET_tblPERIOD);

        if (FYSheet == null) {
            return false;
        }

        for (Row cRowFY : FYSheet) {
            //just skip the row if row number is 0
            if (cRowFY.getRowNum() == 0) {
                continue;
            }

            cFiscalYearModel fiscalYearModel = new cFiscalYearModel();

            fiscalYearModel.setFiscalYearID((int)
                    cRowFY.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            fiscalYearModel.setName(
                    cRowFY.getCell(1, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
            fiscalYearModel.setDescription(
                    cRowFY.getCell(2, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
            fiscalYearModel.setStartDate(
                    cRowFY.getCell(3, Row.CREATE_NULL_AS_BLANK).getDateCellValue());
            fiscalYearModel.setEndDate(
                    cRowFY.getCell(4, Row.CREATE_NULL_AS_BLANK).getDateCellValue());

            if (!addFiscalYear(fiscalYearModel, PSheet)) {
                return false;
            }
        }

        return true;
    }

    public boolean addFiscalYear(cFiscalYearModel fiscalYearModel, Sheet PSheet){
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_ID, fiscalYearModel.getFiscalYearID());
        cv.put(cSQLDBHelper.KEY_NAME, fiscalYearModel.getName());
        cv.put(cSQLDBHelper.KEY_DESCRIPTION, fiscalYearModel.getDescription());
        cv.put(cSQLDBHelper.KEY_START_DATE, String.valueOf(fiscalYearModel.getStartDate()));
        cv.put(cSQLDBHelper.KEY_END_DATE, String.valueOf(fiscalYearModel.getEndDate()));

        // insert record details
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblFISCALYEAR, null, cv) < 0) {
                return false;
            }

            /* add assigned task */
            long fiscalYearID = -1;

            for (Row rowP : PSheet) {
                //just skip the row if row number is 0
                if (rowP.getRowNum() == 0) {
                    continue;
                }

                fiscalYearID = (int) rowP.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                if (fiscalYearModel.getFiscalYearID() == fiscalYearID) {
                    cPeriodModel periodModel = new cPeriodModel();

                    periodModel.setPeriodID((int) rowP.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
                    periodModel.setFiscalYearID((int) rowP.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
                    periodModel.setName(rowP.getCell(2, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
                    periodModel.setDescription(rowP.getCell(3, Row.CREATE_NULL_AS_BLANK).getStringCellValue());

                    if (!addPeriod(periodModel)) {
                        return false;
                    }
                }
            }


        } catch (Exception e) {
            Log.d(TAG, "Exception in importing FISCAL YEAR from Excel: " + e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }

    public boolean addPeriod(cPeriodModel periodModel){
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_ID, periodModel.getPeriodID());
        cv.put(cSQLDBHelper.KEY_FISCAL_YEAR_FK_ID, periodModel.getFiscalYearID());
        cv.put(cSQLDBHelper.KEY_NAME, periodModel.getName());
        cv.put(cSQLDBHelper.KEY_DESCRIPTION, periodModel.getDescription());

        // insert record details
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblPERIOD, null, cv) < 0) {
                return false;
            }

        } catch (Exception e) {
            Log.d(TAG, "Exception in importing PERIOD from Excel: " + e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }

    @Override
    public boolean deletePeriods() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblPERIOD, null, null);

        // close the database connection
        db.close();

        return result > -1;
    }

    @Override
    public boolean deleteFiscalYears() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblFISCALYEAR, null, null);

        // close the database connection
        db.close();

        return result > -1;
    }

    /*###################################### CHART FUNCTIONS #####################################*/

    @Override
    public boolean addChartFromExcel() {
        Workbook workbook = excelHelper.getWorkbookGLOBAL();
        Sheet CSheet = workbook.getSheet(cExcelHelper.SHEET_tblCHART);

        if (CSheet == null) {
            return false;
        }

        for (Row cRow : CSheet) {
            //just skip the row if row number is 0
            if (cRow.getRowNum() == 0) {
                continue;
            }

            cChartModel chartModel = new cChartModel();

            chartModel.setChartID((int)
                    cRow.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            chartModel.setName(
                    cRow.getCell(1, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
            chartModel.setDescription(
                    cRow.getCell(2, Row.CREATE_NULL_AS_BLANK).getStringCellValue());

            if (!addChart(chartModel)) {
                return false;
            }
        }

        return true;
    }

    public boolean addChart(cChartModel chartModel){
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_ID, chartModel.getChartID());
        cv.put(cSQLDBHelper.KEY_NAME, chartModel.getName());
        cv.put(cSQLDBHelper.KEY_DESCRIPTION, chartModel.getDescription());

        // insert record details
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblCHART, null, cv) < 0) {
                return false;
            }
        } catch (Exception e) {
            Log.d(TAG, "Exception in importing CHART from Excel: " + e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }

    @Override
    public boolean deleteCharts() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblCHART, null, null);

        // close the database connection
        db.close();

        return result > -1;
    }

}
