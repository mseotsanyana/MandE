package com.me.mseotsanyana.mande.PPMER.DAL.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.me.mseotsanyana.mande.PPMER.BLL.repository.iUploadEMRepository;
import com.me.mseotsanyana.mande.PPMER.DAL.model.cEvaluationTypeModel;
import com.me.mseotsanyana.mande.STORAGE.database.cSQLDBHelper;
import com.me.mseotsanyana.mande.STORAGE.excel.cExcelHelper;
import com.me.mseotsanyana.mande.UTILITY.cConstant;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.text.SimpleDateFormat;
import java.util.Iterator;

public class cUploadEMRepositoryImpl implements iUploadEMRepository {
    private static SimpleDateFormat sdf = cConstant.FORMAT_DATE;
    private static String TAG = cUploadPMRepositoryImpl.class.getSimpleName();

    /* an object of the database helper */
    private cSQLDBHelper dbHelper;
    /* an object of the excel helper */
    private cExcelHelper excelHelper;

    public cUploadEMRepositoryImpl(Context context) {
        dbHelper = new cSQLDBHelper(context);
        excelHelper = new cExcelHelper(context);
    }

    /* ################################ EVALUATION TYPE FUNCTIONS ################################*/

    /**
     * This function extracts evaluation data from excel and adds it to the database.
     *
     * @return
     */
    @Override
    public boolean addEvaluationTypeFromExcel() {
        Workbook workbook = excelHelper.getWorkbook();
        Sheet ETSheet = workbook.getSheet(cExcelHelper.SHEET_tblEVALUATION_TYPE);

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
            if (db.insert(cSQLDBHelper.TABLE_tblEVALUATION_TYPE, null, cv) < 0) {
                return false;
            }
        } catch (Exception e) {
            Log.d(TAG, "Exception in importing EVALUATION TYPE from Excel: " + e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }

    @Override
    public boolean addEQuestionnaireFromExcel() {
        return false;
    }
}
