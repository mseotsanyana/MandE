package com.me.mseotsanyana.mande.PPMER.DAL.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.me.mseotsanyana.mande.PPMER.BLL.repository.iUploadRAIDRepository;
import com.me.mseotsanyana.mande.PPMER.DAL.model.cRiskActionModel;
import com.me.mseotsanyana.mande.PPMER.DAL.model.cRiskActionTypeModel;
import com.me.mseotsanyana.mande.PPMER.DAL.model.cRiskAnalysisModel;
import com.me.mseotsanyana.mande.PPMER.DAL.model.cRiskConsequenceModel;
import com.me.mseotsanyana.mande.PPMER.DAL.model.cRiskCriteriaModel;
import com.me.mseotsanyana.mande.PPMER.DAL.model.cRiskImpactModel;
import com.me.mseotsanyana.mande.PPMER.DAL.model.cRiskLikelihoodModel;
import com.me.mseotsanyana.mande.PPMER.DAL.model.cRiskRegisterModel;
import com.me.mseotsanyana.mande.PPMER.DAL.model.cRiskRootCauseModel;
import com.me.mseotsanyana.mande.STORAGE.database.cSQLDBHelper;
import com.me.mseotsanyana.mande.STORAGE.excel.cExcelHelper;
import com.me.mseotsanyana.mande.PPMER.DAL.model.cRiskModel;
import com.me.mseotsanyana.mande.UTILITY.cConstant;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.text.SimpleDateFormat;
import java.util.Iterator;

public class cUploadRAIDRepositoryImpl implements iUploadRAIDRepository {
    private static SimpleDateFormat sdf = cConstant.FORMAT_DATE;
    private static String TAG = cUploadRAIDRepositoryImpl.class.getSimpleName();

    /* an object of the database helper */
    private cSQLDBHelper dbHelper;
    /* an object of the excel helper */
    private cExcelHelper excelHelper;

    public cUploadRAIDRepositoryImpl(Context context) {
        dbHelper = new cSQLDBHelper(context);
        excelHelper = new cExcelHelper(context);
    }

    /*################################## RISK REGISTER FUNCTIONS #################################*/

    @Override
    public boolean addRegisterFromExcel() {
        Workbook workbook = excelHelper.getWorkbookRAID();
        Sheet RSheet = workbook.getSheet(cExcelHelper.SHEET_tblRISKREGISTER);

        if (RSheet == null) {
            return false;
        }

        for (Iterator<Row> ritR = RSheet.iterator(); ritR.hasNext(); ) {
            Row cRowR = ritR.next();

            //just skip the row if row number is 0
            if (cRowR.getRowNum() == 0) {
                continue;
            }

            cRiskRegisterModel riskRegisterModel = new cRiskRegisterModel();

            riskRegisterModel.setRiskRegisterID((int)
                    cRowR.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            riskRegisterModel.setName(
                    cRowR.getCell(1, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
            riskRegisterModel.setDescription(
                    cRowR.getCell(2, Row.CREATE_NULL_AS_BLANK).getStringCellValue());

            if (!addRiskRegister(riskRegisterModel)) {
                return false;
            }
        }

        return true;
    }

    public boolean addRiskRegister(cRiskRegisterModel riskRegisterModel){
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_ID, riskRegisterModel.getRiskRegisterID());
        cv.put(cSQLDBHelper.KEY_NAME, riskRegisterModel.getName());
        cv.put(cSQLDBHelper.KEY_DESCRIPTION, riskRegisterModel.getDescription());

        // insert record details
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblRISKREGISTER, null, cv) < 0) {
                return false;
            }
        } catch (Exception e) {
            Log.d(TAG, "Exception in importing RISK REGISTER from Excel: " + e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }

    @Override
    public boolean deleteRegisters() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblRISKREGISTER, null, null);

        // close the database connection
        db.close();

        return result > -1;
    }

    /*################################# RISK LIKELIHOOD FUNCTIONS ################################*/

    @Override
    public boolean addRiskLikelihoodFromExcel() {
        Workbook workbook = excelHelper.getWorkbookRAID();
        Sheet RLSheet = workbook.getSheet(cExcelHelper.SHEET_tblRISKLIKELIHOOD);
        Sheet RLSSheet = workbook.getSheet(cExcelHelper.SHEET_tblRISKLIKELIHOODSET);


        if (RLSheet == null) {
            return false;
        }

        for (Iterator<Row> ritRL = RLSheet.iterator(); ritRL.hasNext(); ) {
            Row cRowRL = ritRL.next();

            //just skip the row if row number is 0
            if (cRowRL.getRowNum() == 0) {
                continue;
            }

            cRiskLikelihoodModel riskLikelihoodModel = new cRiskLikelihoodModel();

            riskLikelihoodModel.setRiskLikelihoodID((int)
                    cRowRL.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            riskLikelihoodModel.setName(
                    cRowRL.getCell(1, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
            riskLikelihoodModel.setDescription(
                    cRowRL.getCell(2, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
            riskLikelihoodModel.setValue((int)
                    cRowRL.getCell(3, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());

            if (!addRiskLikelihood(riskLikelihoodModel, RLSSheet)) {
                return false;
            }
        }

        return true;
    }

    public boolean addRiskLikelihood(cRiskLikelihoodModel riskLikelihoodModel, Sheet RLSSheet){
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_ID, riskLikelihoodModel.getRiskLikelihoodID());
        cv.put(cSQLDBHelper.KEY_NAME, riskLikelihoodModel.getName());
        cv.put(cSQLDBHelper.KEY_DESCRIPTION, riskLikelihoodModel.getDescription());
        cv.put(cSQLDBHelper.KEY_RISKLIKELIHOOD_VALUE, riskLikelihoodModel.getValue());

        // insert record details
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblRISKLIKELIHOOD, null, cv) < 0) {
                return false;
            }

            for (Iterator<Row> ritRLS = RLSSheet.iterator(); ritRLS.hasNext(); ) {
                Row rowRLS = ritRLS.next();

                //just skip the row if row number is 0
                if (rowRLS.getRowNum() == 0) {
                    continue;
                }

                int riskLikelihoodID = (int) rowRLS.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                if (riskLikelihoodModel.getRiskLikelihoodID() == riskLikelihoodID) {
                    int registerID = (int)rowRLS.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();

                    if (!addRiskLikelihoodSet(registerID, riskLikelihoodID)) {
                        return false;
                    }
                }
            }

        } catch (Exception e) {
            Log.d(TAG, "Exception in importing RISK LIKELIHOOD from Excel: " + e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }

    public boolean addRiskLikelihoodSet(int registerID, int riskLikelihoodID){
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_RISK_REGISTER_FK_ID, registerID);
        cv.put(cSQLDBHelper.KEY_RISK_LIKELIHOOD_FK_ID, riskLikelihoodID);

        // insert record details
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblRISKLIKELIHOODSET, null, cv) < 0) {
                return false;
            }

        } catch (Exception e) {
            Log.d(TAG, "Exception in importing RISK LIKELIHOOD SET from Excel: " + e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }

    @Override
    public boolean deleteRiskLikelihoods() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblRISKLIKELIHOOD, null, null);

        // close the database connection
        db.close();

        return result > -1;
    }

    @Override
    public boolean deleteRiskLikelihoodSets() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblRISKLIKELIHOODSET, null, null);

        // close the database connection
        db.close();

        return result > -1;
    }

    /*################################### RISK IMPACT FUNCTIONS ##################################*/

    @Override
    public boolean addRiskImpactFromExcel() {
        Workbook workbook = excelHelper.getWorkbookRAID();
        Sheet RISheet = workbook.getSheet(cExcelHelper.SHEET_tblRISKIMPACT);
        Sheet RISSheet = workbook.getSheet(cExcelHelper.SHEET_tblRISKIMPACTSET);


        if (RISheet == null) {
            return false;
        }

        for (Iterator<Row> ritRI = RISheet.iterator(); ritRI.hasNext(); ) {
            Row cRowRI = ritRI.next();

            //just skip the row if row number is 0
            if (cRowRI.getRowNum() == 0) {
                continue;
            }

            cRiskImpactModel riskImpactModel = new cRiskImpactModel();

            riskImpactModel.setRiskImpactID((int)
                    cRowRI.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            riskImpactModel.setName(
                    cRowRI.getCell(1, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
            riskImpactModel.setDescription(
                    cRowRI.getCell(2, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
            riskImpactModel.setValue((int)
                    cRowRI.getCell(3, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());

            if (!addRiskImpact(riskImpactModel, RISSheet)) {
                return false;
            }
        }

        return true;
    }

    public boolean addRiskImpact(cRiskImpactModel riskImpactModel, Sheet RISSheet){
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_ID, riskImpactModel.getRiskImpactID());
        cv.put(cSQLDBHelper.KEY_NAME, riskImpactModel.getName());
        cv.put(cSQLDBHelper.KEY_DESCRIPTION, riskImpactModel.getDescription());
        cv.put(cSQLDBHelper.KEY_RISKIMPACT_VALUE, riskImpactModel.getValue());

        // insert record details
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblRISKIMPACT, null, cv) < 0) {
                return false;
            }

            for (Iterator<Row> ritRIS = RISSheet.iterator(); ritRIS.hasNext(); ) {
                Row rowRIS = ritRIS.next();

                //just skip the row if row number is 0
                if (rowRIS.getRowNum() == 0) {
                    continue;
                }

                int riskImpactID = (int) rowRIS.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                if (riskImpactModel.getRiskImpactID() == riskImpactID) {
                    int registerID = (int)rowRIS.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();

                    if (!addRiskImpactSet(registerID, riskImpactID)) {
                        return false;
                    }
                }
            }

        } catch (Exception e) {
            Log.d(TAG, "Exception in importing RISK IMPACT from Excel: " + e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }

    public boolean addRiskImpactSet(int registerID, int riskImpactID){
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_RISK_REGISTER_FK_ID, registerID);
        cv.put(cSQLDBHelper.KEY_RISK_IMPACT_FK_ID, riskImpactID);

        // insert record details
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblRISKIMPACTSET, null, cv) < 0) {
                return false;
            }

        } catch (Exception e) {
            Log.d(TAG, "Exception in importing RISK IMPACT SET from Excel: " + e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }

    @Override
    public boolean deleteRiskImpacts() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblRISKIMPACT, null, null);

        // close the database connection
        db.close();

        return result > -1;
    }

    @Override
    public boolean deleteRiskImpactSets() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblRISKIMPACTSET, null, null);

        // close the database connection
        db.close();

        return result > -1;
    }

    /*################################## RISK CRITERIA FUNCTIONS #################################*/

    @Override
    public boolean addRiskCriteriaFromExcel() {
        Workbook workbook = excelHelper.getWorkbookRAID();
        Sheet RCSheet = workbook.getSheet(cExcelHelper.SHEET_tblRISKCRITERIA);
        Sheet RCSSheet = workbook.getSheet(cExcelHelper.SHEET_tblRISKCRITERIASET);


        if (RCSheet == null) {
            return false;
        }

        for (Iterator<Row> ritRC = RCSheet.iterator(); ritRC.hasNext(); ) {
            Row cRowRC = ritRC.next();

            //just skip the row if row number is 0
            if (cRowRC.getRowNum() == 0) {
                continue;
            }

            cRiskCriteriaModel riskCriteriaModel = new cRiskCriteriaModel();

            riskCriteriaModel.setRiskCriteriaID((int)
                    cRowRC.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            riskCriteriaModel.setName(
                    cRowRC.getCell(1, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
            riskCriteriaModel.setDescription(
                    cRowRC.getCell(2, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
            riskCriteriaModel.setLowerLimit((int)
                    cRowRC.getCell(3, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            riskCriteriaModel.setUpperLimit((int)
                    cRowRC.getCell(4, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());

            if (!addRiskCriteria(riskCriteriaModel, RCSSheet)) {
                return false;
            }
        }

        return true;
    }

    public boolean addRiskCriteria(cRiskCriteriaModel riskCriteriaModel, Sheet RCSSheet){
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_ID, riskCriteriaModel.getRiskCriteriaID());
        cv.put(cSQLDBHelper.KEY_NAME, riskCriteriaModel.getName());
        cv.put(cSQLDBHelper.KEY_DESCRIPTION, riskCriteriaModel.getDescription());
        cv.put(cSQLDBHelper.KEY_RISKMAP_LOWER, riskCriteriaModel.getLowerLimit());
        cv.put(cSQLDBHelper.KEY_RISKMAP_UPPER, riskCriteriaModel.getUpperLimit());

        // insert record details
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblRISKCRITERIA, null, cv) < 0) {
                return false;
            }

            for (Iterator<Row> ritRCS = RCSSheet.iterator(); ritRCS.hasNext(); ) {
                Row rowRCS = ritRCS.next();

                //just skip the row if row number is 0
                if (rowRCS.getRowNum() == 0) {
                    continue;
                }

                int riskCriteriaID = (int) rowRCS.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                if (riskCriteriaModel.getRiskCriteriaID() == riskCriteriaID) {
                    int registerID = (int)rowRCS.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();

                    if (!addRiskCriteriaSet(registerID, riskCriteriaID)) {
                        return false;
                    }
                }
            }

        } catch (Exception e) {
            Log.d(TAG, "Exception in importing RISK CRITERIA from Excel: " + e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }

    public boolean addRiskCriteriaSet(int registerID, int riskCriteriaID){
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_RISK_REGISTER_FK_ID, registerID);
        cv.put(cSQLDBHelper.KEY_RISK_CRITERIA_FK_ID, riskCriteriaID);

        // insert record details
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblRISKCRITERIASET, null, cv) < 0) {
                return false;
            }

        } catch (Exception e) {
            Log.d(TAG, "Exception in importing RISK CRITERIA SET from Excel: " + e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }

    @Override
    public boolean deleteRiskCriteria() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblRISKCRITERIA, null, null);

        // close the database connection
        db.close();

        return result > -1;
    }

    @Override
    public boolean deleteRiskCriteriaSets() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblRISKCRITERIASET, null, null);

        // close the database connection
        db.close();

        return result > -1;
    }

    /*###################################### RISK FUNCTIONS ######################################*/

    @Override
    public boolean addRiskFromExcel() {
        Workbook workbook = excelHelper.getWorkbookRAID();
        Sheet RSheet = workbook.getSheet(cExcelHelper.SHEET_tblRISK);
        Sheet RCSSheet = workbook.getSheet(cExcelHelper.SHEET_tblRISKCONSEQUENCE);
        Sheet RRSSheet = workbook.getSheet(cExcelHelper.SHEET_tblRISKROOTCAUSE);

        if (RSheet == null) {
            return false;
        }

        for (Iterator<Row> ritR = RSheet.iterator(); ritR.hasNext(); ) {
            Row cRowR = ritR.next();

            //just skip the row if row number is 0
            if (cRowR.getRowNum() == 0) {
                continue;
            }

            cRiskModel riskModel = new cRiskModel();

            riskModel.setRaidID((int)
                    cRowR.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            riskModel.setRiskRegisterID((int)
                    cRowR.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            riskModel.setStaffID((int)
                    cRowR.getCell(2, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            riskModel.setFrequencyID((int)
                    cRowR.getCell(3, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            riskModel.setRiskLikelihoodID((int)
                    cRowR.getCell(4, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            riskModel.setRiskImpactID((int)
                    cRowR.getCell(5, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            riskModel.setName(
                    cRowR.getCell(6, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
            riskModel.setDescription(
                    cRowR.getCell(7, Row.CREATE_NULL_AS_BLANK).getStringCellValue());


            if (!addRisk(riskModel, RCSSheet, RRSSheet)) {
                return false;
            }
        }

        return true;
    }

    private boolean addRisk(cRiskModel riskModel, Sheet RCSSheet, Sheet RRSSheet) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_RAID_FK_ID, riskModel.getRaidID());
        cv.put(cSQLDBHelper.KEY_RISK_REGISTER_FK_ID, riskModel.getRiskRegisterID());
        cv.put(cSQLDBHelper.KEY_STAFF_FK_ID, riskModel.getStaffID());
        cv.put(cSQLDBHelper.KEY_FREQUENCY_FK_ID, riskModel.getFrequencyID());
        cv.put(cSQLDBHelper.KEY_RISK_LIKELIHOOD_FK_ID, riskModel.getRiskLikelihoodID());
        cv.put(cSQLDBHelper.KEY_RISK_IMPACT_FK_ID, riskModel.getRiskImpactID());
        cv.put(cSQLDBHelper.KEY_NAME, riskModel.getName());
        cv.put(cSQLDBHelper.KEY_DESCRIPTION, riskModel.getDescription());

        // insert record details
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblRISK, null, cv) < 0) {
                return false;
            }

            /* risk consequences */
            for (Iterator<Row> ritRCS = RCSSheet.iterator(); ritRCS.hasNext(); ) {
                Row rowRCS = ritRCS.next();

                //just skip the row if row number is 0
                if (rowRCS.getRowNum() == 0) {
                    continue;
                }

                int riskID = (int) rowRCS.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                if (riskModel.getRaidID() == riskID) {
                    cRiskConsequenceModel riskConsequenceModel = new cRiskConsequenceModel();
                    riskConsequenceModel.setRiskConsequenceID((int)
                            rowRCS.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
                    riskConsequenceModel.setRiskID(riskID);
                    riskConsequenceModel.setName(
                            rowRCS.getCell(2, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
                    riskConsequenceModel.setDescription(
                            rowRCS.getCell(3, Row.CREATE_NULL_AS_BLANK).getStringCellValue());

                    if (!addRiskConsequence(riskConsequenceModel)) {
                        return false;
                    }
                }
            }

            /* risk root causes */
            for (Iterator<Row> ritRRS = RRSSheet.iterator(); ritRRS.hasNext(); ) {
                Row rowRRS = ritRRS.next();

                //just skip the row if row number is 0
                if (rowRRS.getRowNum() == 0) {
                    continue;
                }

                int riskID = (int) rowRRS.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                if (riskModel.getRaidID() == riskID) {
                    cRiskRootCauseModel riskRootCauseModel = new cRiskRootCauseModel();

                    riskRootCauseModel.setRiskRootCauseID((int)
                            rowRRS.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
                    riskRootCauseModel.setRiskID(riskID);
                    riskRootCauseModel.setName(
                            rowRRS.getCell(2, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
                    riskRootCauseModel.setDescription(
                            rowRRS.getCell(3, Row.CREATE_NULL_AS_BLANK).getStringCellValue());

                    if (!addRiskRootCause(riskRootCauseModel)) {
                        return false;
                    }
                }
            }

        } catch (Exception e) {
            Log.d(TAG, "Exception in importing RISK from Excel: " + e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }

    private boolean addRiskConsequence(cRiskConsequenceModel riskConsequenceModel) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_ID, riskConsequenceModel.getRiskConsequenceID());
        cv.put(cSQLDBHelper.KEY_RISK_FK_ID, riskConsequenceModel.getRiskID());
        cv.put(cSQLDBHelper.KEY_NAME, riskConsequenceModel.getName());
        cv.put(cSQLDBHelper.KEY_DESCRIPTION, riskConsequenceModel.getDescription());

        // insert record details
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblRISKCONSEQUENCE, null, cv) < 0) {
                return false;
            }

        } catch (Exception e) {
            Log.d(TAG, "Exception in importing RISK CONSEQUENCES from Excel: " + e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }

    private boolean addRiskRootCause(cRiskRootCauseModel riskRootCauseModel) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_ID, riskRootCauseModel.getRiskRootCauseID());
        cv.put(cSQLDBHelper.KEY_RISK_FK_ID, riskRootCauseModel.getRiskID());
        cv.put(cSQLDBHelper.KEY_NAME, riskRootCauseModel.getName());
        cv.put(cSQLDBHelper.KEY_DESCRIPTION, riskRootCauseModel.getDescription());

        // insert record details
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblRISKROOTCAUSE, null, cv) < 0) {
                return false;
            }

        } catch (Exception e) {
            Log.d(TAG, "Exception in importing RISK ROOT CAUSES from Excel: " + e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }

    @Override
    public boolean deleteRisks() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblRISK, null, null);

        // close the database connection
        db.close();

        return result > -1;
    }

    @Override
    public boolean deleteRiskConsequences() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblRISKCONSEQUENCE, null, null);

        // close the database connection
        db.close();

        return result > -1;
    }

    @Override
    public boolean deleteRiskRootCauses() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblRISKROOTCAUSE, null, null);

        // close the database connection
        db.close();

        return result > -1;
    }

    /*################################## RISK ANALYSIS FUNCTIONS #################################*/

    @Override
    public boolean addRiskAnalysisFromExcel() {
        Workbook workbook = excelHelper.getWorkbookRAID();
        Sheet RASheet = workbook.getSheet(cExcelHelper.SHEET_tblRISKANALYSIS);

        if (RASheet == null) {
            return false;
        }

        for (Iterator<Row> ritRA = RASheet.iterator(); ritRA.hasNext(); ) {
            Row cRowRA = ritRA.next();

            //just skip the row if row number is 0
            if (cRowRA.getRowNum() == 0) {
                continue;
            }

            cRiskAnalysisModel riskAnalysisModel = new cRiskAnalysisModel();

            riskAnalysisModel.setRiskAnalysisID((int)
                    cRowRA.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            riskAnalysisModel.setRiskMilestoneID((int)
                    cRowRA.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            riskAnalysisModel.setRiskLikelihoodID((int)
                    cRowRA.getCell(2, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            riskAnalysisModel.setRiskImpactID((int)
                    cRowRA.getCell(3, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());

            if (!addRiskAnalysis(riskAnalysisModel)) {
                return false;
            }
        }

        return true;
    }

    private boolean addRiskAnalysis(cRiskAnalysisModel riskAnalysisModel) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_ID, riskAnalysisModel.getRiskAnalysisID());
        cv.put(cSQLDBHelper.KEY_RISK_MILESTONE_FK_ID, riskAnalysisModel.getRiskMilestoneID());
        cv.put(cSQLDBHelper.KEY_RISK_LIKELIHOOD_FK_ID, riskAnalysisModel.getRiskLikelihoodID());
        cv.put(cSQLDBHelper.KEY_RISK_IMPACT_FK_ID, riskAnalysisModel.getRiskImpactID());

        // insert record details
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblRISKANALYSIS, null, cv) < 0) {
                return false;
            }

        } catch (Exception e) {
            Log.d(TAG, "Exception in importing RISK ANALYSIS from Excel: " + e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }

    @Override
    public boolean deleteRiskAnalysis() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblRISKANALYSIS, null, null);

        // close the database connection
        db.close();

        return result > -1;
    }
    /*################################# RISK ACTION TYPE FUNCTIONS ###############################*/

    @Override
    public boolean addRiskActionTypeFromExcel() {
        Workbook workbook = excelHelper.getWorkbookRAID();
        Sheet RATSheet = workbook.getSheet(cExcelHelper.SHEET_tblRISKACTIONTYPE);

        if (RATSheet == null) {
            return false;
        }

        for (Iterator<Row> ritRAT = RATSheet.iterator(); ritRAT.hasNext(); ) {
            Row cRow = ritRAT.next();

            //just skip the row if row number is 0
            if (cRow.getRowNum() == 0) {
                continue;
            }

            cRiskActionTypeModel riskActionTypeModel = new cRiskActionTypeModel();

            riskActionTypeModel.setRiskActionTypeID((int)
                    cRow.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            riskActionTypeModel.setName(
                    cRow.getCell(1, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
            riskActionTypeModel.setDescription(
                    cRow.getCell(2, Row.CREATE_NULL_AS_BLANK).getStringCellValue());

            if (!addRiskActionType(riskActionTypeModel)) {
                return false;
            }
        }

        return true;
    }

    private boolean addRiskActionType(cRiskActionTypeModel riskActionTypeModel) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_ID, riskActionTypeModel.getRiskActionTypeID());
        cv.put(cSQLDBHelper.KEY_NAME, riskActionTypeModel.getName());
        cv.put(cSQLDBHelper.KEY_DESCRIPTION, riskActionTypeModel.getDescription());

        // insert project details
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblRISKACTIONTYPE, null, cv) < 0) {
                return false;
            }
        } catch (Exception e) {
            Log.d(TAG, "Exception in importing RISK ACTION TYPE from Excel: " + e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }

    @Override
    public boolean deleteRiskActionTypes() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblRISKACTIONTYPE, null, null);

        // close the database connection
        db.close();

        return result > -1;
    }

    /*################################### RISK ACTION FUNCTIONS ##################################*/

    @Override
    public boolean addRiskActionFromExcel() {
        Workbook workbook = excelHelper.getWorkbookRAID();
        Sheet RSheet = workbook.getSheet(cExcelHelper.SHEET_tblRISKACTION);
        Sheet CCSheet = workbook.getSheet(cExcelHelper.SHEET_tblCURRENTCONTROL);
        Sheet ACSheet = workbook.getSheet(cExcelHelper.SHEET_tblADDITIONALCONTROL);

        if (RSheet == null) {
            return false;
        }

        for (Iterator<Row> ritR = RSheet.iterator(); ritR.hasNext(); ) {
            Row cRowR = ritR.next();

            //just skip the row if row number is 0
            if (cRowR.getRowNum() == 0) {
                continue;
            }

            cRiskActionModel riskActionModel = new cRiskActionModel();

            riskActionModel.setTaskID((int)
                    cRowR.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            riskActionModel.setRiskPlanID((int)
                    cRowR.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            riskActionModel.setRiskActionTypeID((int)
                    cRowR.getCell(2, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());

            if (!addRiskAction(riskActionModel, CCSheet, ACSheet)) {
                return false;
            }
        }

        return true;
    }

    private boolean addRiskAction(cRiskActionModel riskActionModel, Sheet CCSheet, Sheet ACSheet){
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_TASK_FK_ID, riskActionModel.getTaskID());
        cv.put(cSQLDBHelper.KEY_RISK_PLAN_FK_ID, riskActionModel.getRiskPlanID());
        cv.put(cSQLDBHelper.KEY_RISK_ACTION_TYPE_FK_ID, riskActionModel.getRiskActionTypeID());

        // insert record details
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblRISKACTION, null, cv) < 0) {
                return false;
            }

            /* risk current control */
            for (Iterator<Row> ritCC = CCSheet.iterator(); ritCC.hasNext(); ) {
                Row rowCC = ritCC.next();

                //just skip the row if row number is 0
                if (rowCC.getRowNum() == 0) {
                    continue;
                }

                int riskActionID = (int) rowCC.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                if (riskActionModel.getTaskID() == riskActionID) {
                    int riskAnalysisID = (int)
                            rowCC.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                    //int riskActionTypeID = (int)
                    //       rowCC.getCell(2, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();

                    if (!addCurrentControl(riskActionID, riskAnalysisID)) {
                        return false;
                    }
                }
            }

            /* risk additional control */
            for (Iterator<Row> ritAC = ACSheet.iterator(); ritAC.hasNext(); ) {
                Row rowAC = ritAC.next();

                //just skip the row if row number is 0
                if (rowAC.getRowNum() == 0) {
                    continue;
                }

                int riskActionID = (int) rowAC.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                if (riskActionModel.getTaskID() == riskActionID) {
                    int riskAnalysisID = (int)
                            rowAC.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                    //int riskActionTypeID = (int)
                    //        rowAC.getCell(2, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();

                    if (!addAdditionalControl(riskActionID, riskAnalysisID)) {
                        return false;
                    }
                }
            }

        } catch (Exception e) {
            Log.d(TAG, "Exception in importing RISK ACTION from Excel: " + e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }

    private boolean addCurrentControl(int riskActionID, int riskAnalysisID) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_RISK_ACTION_FK_ID, riskActionID);
        cv.put(cSQLDBHelper.KEY_RISK_ANALYSIS_FK_ID, riskAnalysisID);
        //cv.put(cSQLDBHelper.KEY_RISK_ACTION_TYPE_FK_ID, riskActionTypeID);

        // insert project details
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblCURRENTCONTROL, null, cv) < 0) {
                return false;
            }
        } catch (Exception e) {
            Log.d(TAG, "Exception in importing RISK CURRENT CONTROL from Excel: " +
                    e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }

    private boolean addAdditionalControl(int riskActionID, int riskAnalysisID) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_RISK_ACTION_FK_ID, riskActionID);
        cv.put(cSQLDBHelper.KEY_RISK_ANALYSIS_FK_ID, riskAnalysisID);
        //cv.put(cSQLDBHelper.KEY_RISK_ACTION_TYPE_FK_ID, riskActionTypeID);

        // insert project details
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblADDITIONALCONTROL, null, cv) < 0) {
                return false;
            }
        } catch (Exception e) {
            Log.d(TAG, "Exception in importing RISK ADDITIONAL CONTROL from Excel: " +
                    e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }

    @Override
    public boolean deleteRiskActions() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblRISKACTION, null, null);

        // close the database connection
        db.close();

        return result > -1;
    }

    @Override
    public boolean deleteRiskCurrentControls() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblCURRENTCONTROL, null, null);

        // close the database connection
        db.close();

        return result > -1;
    }

    @Override
    public boolean deleteRiskAdditionalControls() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblADDITIONALCONTROL, null, null);

        // close the database connection
        db.close();

        return result > -1;
    }
}
