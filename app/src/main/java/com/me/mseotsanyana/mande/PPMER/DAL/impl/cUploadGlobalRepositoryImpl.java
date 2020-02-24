package com.me.mseotsanyana.mande.PPMER.DAL.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.me.mseotsanyana.mande.PPMER.BLL.repository.iUploadGlobalRepository;
import com.me.mseotsanyana.mande.PPMER.DAL.model.cFrequencyModel;
import com.me.mseotsanyana.mande.PPMER.DAL.model.cMOVModel;
import com.me.mseotsanyana.mande.STORAGE.database.cSQLDBHelper;
import com.me.mseotsanyana.mande.STORAGE.excel.cExcelHelper;
import com.me.mseotsanyana.mande.UTILITY.cConstant;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.text.SimpleDateFormat;
import java.util.Iterator;


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

    /*####################################### MOV FUNCTIONS ######################################*/

    @Override
    public boolean addFrequencyFromExcel() {
        Workbook workbook = excelHelper.getWorkbookGLOBAL();
        Sheet FSheet = workbook.getSheet(cExcelHelper.SHEET_tblFREQUENCY);

        if (FSheet == null) {
            return false;
        }

        for (Iterator<Row> ritF = FSheet.iterator(); ritF.hasNext(); ) {
            Row cRowF = ritF.next();

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
}
