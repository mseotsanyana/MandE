package com.me.mseotsanyana.mande.DAL.ìmpl.sqlite.raid;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.me.mseotsanyana.mande.BLL.model.raid.cAssumptionActionModel;
import com.me.mseotsanyana.mande.BLL.model.raid.cAssumptionModel;
import com.me.mseotsanyana.mande.BLL.model.raid.cAssumptionReviewModel;
import com.me.mseotsanyana.mande.BLL.model.raid.cDependencyActionModel;
import com.me.mseotsanyana.mande.BLL.model.raid.cDependencyModel;
import com.me.mseotsanyana.mande.BLL.model.raid.cDependencyReviewModel;
import com.me.mseotsanyana.mande.BLL.model.raid.cIssueActionModel;
import com.me.mseotsanyana.mande.BLL.model.raid.cIssueCommentModel;
import com.me.mseotsanyana.mande.BLL.model.raid.cIssueModel;
import com.me.mseotsanyana.mande.BLL.model.raid.cIssueReviewModel;
import com.me.mseotsanyana.mande.BLL.model.raid.cPIMModel;
import com.me.mseotsanyana.mande.BLL.model.raid.cPIMSETModel;
import com.me.mseotsanyana.mande.BLL.model.raid.cRAIDLOGModel;
import com.me.mseotsanyana.mande.BLL.model.raid.cRAIDRegisterModel;
import com.me.mseotsanyana.mande.BLL.model.raid.cRiskActionModel;
import com.me.mseotsanyana.mande.BLL.model.raid.cRiskReviewModel;
import com.me.mseotsanyana.mande.BLL.repository.raid.iUploadRAIDRepository;
import com.me.mseotsanyana.mande.BLL.model.raid.cActionModel;
import com.me.mseotsanyana.mande.BLL.model.raid.cRiskActionTypeModel;
import com.me.mseotsanyana.mande.BLL.model.raid.cMilestoneReviewModel;
import com.me.mseotsanyana.mande.BLL.model.raid.cRiskConsequenceModel;
import com.me.mseotsanyana.mande.BLL.model.raid.cRobotModel;
import com.me.mseotsanyana.mande.BLL.model.raid.cRAIDImpactModel;
import com.me.mseotsanyana.mande.BLL.model.raid.cRAIDLikelihoodModel;
import com.me.mseotsanyana.mande.BLL.model.raid.cRiskRootCauseModel;
import com.me.mseotsanyana.mande.DAL.storage.database.cSQLDBHelper;
import com.me.mseotsanyana.mande.DAL.storage.excel.cExcelHelper;
import com.me.mseotsanyana.mande.BLL.model.raid.cRiskModel;
import com.me.mseotsanyana.mande.UTIL.cConstant;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Set;

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

    /*####################################### PIM FUNCTIONS ######################################*/

    @Override
    public boolean addPIMFromExcel() {
        Workbook workbook = excelHelper.getWorkbookRAID();
        Sheet PIMSheet = workbook.getSheet(cExcelHelper.SHEET_tblPIM);

        if (PIMSheet == null) {
            return false;
        }

        for (Row rowPIM : PIMSheet) {
            //just skip the row if row number is 0
            if (rowPIM.getRowNum() == 0) {
                continue;
            }

            cPIMModel pimModel = new cPIMModel();

            pimModel.setPimID((int)
                    rowPIM.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            pimModel.setName(
                    rowPIM.getCell(1, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
            pimModel.setDescription(
                    rowPIM.getCell(2, Row.CREATE_NULL_AS_BLANK).getStringCellValue());

            if (!addPIM(pimModel)) {
                return false;
            }
        }

        return true;
    }

    public boolean addPIM(cPIMModel pimModel) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_ID, pimModel.getPimID());
        cv.put(cSQLDBHelper.KEY_NAME, pimModel.getName());
        cv.put(cSQLDBHelper.KEY_DESCRIPTION, pimModel.getDescription());

        // insert record details
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblPIM, null, cv) < 0) {
                return false;
            }
        } catch (Exception e) {
            Log.d(TAG, "Exception in importing PIM from Excel: " + e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }

    @Override
    public boolean deletePIMs() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblPIM, null, null);

        // close the database connection
        db.close();

        return result > -1;
    }

    /*################################# RAID LIKELIHOOD FUNCTIONS ################################*/

    @Override
    public boolean addRAIDLikelihoodFromExcel() {
        Workbook workbook = excelHelper.getWorkbookRAID();
        Sheet RLSheet = workbook.getSheet(cExcelHelper.SHEET_tblRAIDLIKELIHOOD);

        if (RLSheet == null) {
            return false;
        }

        for (Row rowRL : RLSheet) {
            //just skip the row if row number is 0
            if (rowRL.getRowNum() == 0) {
                continue;
            }

            cRAIDLikelihoodModel raidLikelihoodModel = new cRAIDLikelihoodModel();

            raidLikelihoodModel.setRaidLikelihoodID((int)
                    rowRL.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            raidLikelihoodModel.setName(
                    rowRL.getCell(1, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
            raidLikelihoodModel.setDescription(
                    rowRL.getCell(2, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
            raidLikelihoodModel.setValue((int)
                    rowRL.getCell(3, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());

            if (!addRAIDLikelihood(raidLikelihoodModel)) {
                return false;
            }
        }

        return true;
    }

    public boolean addRAIDLikelihood(cRAIDLikelihoodModel raidLikelihoodModel) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_ID, raidLikelihoodModel.getRaidLikelihoodID());
        cv.put(cSQLDBHelper.KEY_NAME, raidLikelihoodModel.getName());
        cv.put(cSQLDBHelper.KEY_DESCRIPTION, raidLikelihoodModel.getDescription());
        cv.put(cSQLDBHelper.KEY_RAIDLIKELIHOOD_VALUE, raidLikelihoodModel.getValue());

        // insert record details
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblRAIDLIKELIHOOD, null, cv) < 0) {
                return false;
            }
        } catch (Exception e) {
            Log.d(TAG, "Exception in importing RAID Likelihood from Excel: " + e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }

    @Override
    public boolean deleteRAIDLIKELIHOODs() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblRAIDLIKELIHOOD, null,
                null);

        // close the database connection
        db.close();

        return result > -1;
    }

    /*################################### RAID IMPACT FUNCTIONS ##################################*/

    @Override
    public boolean addRAIDImpactFromExcel() {
        Workbook workbook = excelHelper.getWorkbookRAID();
        Sheet RISheet = workbook.getSheet(cExcelHelper.SHEET_tblRAIDIMPACT);

        if (RISheet == null) {
            return false;
        }

        for (Row rowRI : RISheet) {
            //just skip the row if row number is 0
            if (rowRI.getRowNum() == 0) {
                continue;
            }

            cRAIDImpactModel raidImpactModel = new cRAIDImpactModel();

            raidImpactModel.setRaidImpactID((int)
                    rowRI.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            raidImpactModel.setName(
                    rowRI.getCell(1, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
            raidImpactModel.setDescription(
                    rowRI.getCell(2, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
            raidImpactModel.setValue((int)
                    rowRI.getCell(3, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());

            if (!addRAIDImpact(raidImpactModel)) {
                return false;
            }
        }

        return true;
    }

    public boolean addRAIDImpact(cRAIDImpactModel raidImpactModel) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_ID, raidImpactModel.getRaidImpactID());
        cv.put(cSQLDBHelper.KEY_NAME, raidImpactModel.getName());
        cv.put(cSQLDBHelper.KEY_DESCRIPTION, raidImpactModel.getDescription());
        cv.put(cSQLDBHelper.KEY_RAIDIMPACT_VALUE, raidImpactModel.getValue());

        // insert record details
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblRAIDIMPACT, null, cv) < 0) {
                return false;
            }
        } catch (Exception e) {
            Log.d(TAG, "Exception in importing RAID Impact from Excel: " + e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }

    @Override
    public boolean deleteRAIDIMPACTs() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblRAIDIMPACT, null, null);

        // close the database connection
        db.close();

        return result > -1;
    }

    /*###################################### ROBOT FUNCTIONS #####################################*/

    @Override
    public boolean addRobotFromExcel() {
        Workbook workbook = excelHelper.getWorkbookRAID();
        Sheet RSheet = workbook.getSheet(cExcelHelper.SHEET_tblROBOT);

        if (RSheet == null) {
            return false;
        }

        for (Row rowR : RSheet) {
            //just skip the row if row number is 0
            if (rowR.getRowNum() == 0) {
                continue;
            }

            cRobotModel robotModel = new cRobotModel();

            robotModel.setRobotID((int)
                    rowR.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            robotModel.setColour(
                    rowR.getCell(1, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
            robotModel.setLowerLimit((int)
                    rowR.getCell(2, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            robotModel.setUpperLimit((int)
                    rowR.getCell(3, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());

            if (!addRobot(robotModel)) {
                return false;
            }
        }

        return true;
    }

    public boolean addRobot(cRobotModel robotModel) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_ID, robotModel.getRobotID());
        cv.put(cSQLDBHelper.KEY_COLOUR_NAME, robotModel.getColour());
        cv.put(cSQLDBHelper.KEY_LOWER_LIMIT, robotModel.getLowerLimit());
        cv.put(cSQLDBHelper.KEY_UPPER_LIMIT, robotModel.getUpperLimit());

        // insert record details
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblROBOT, null, cv) < 0) {
                return false;
            }
        } catch (Exception e) {
            Log.d(TAG, "Exception in importing ROBOT from Excel: " + e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }

    @Override
    public boolean deleteROBOTs() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblROBOT, null, null);

        // close the database connection
        db.close();

        return result > -1;
    }

    /*##################################### PIM SET FUNCTIONS ####################################*/

    @Override
    public boolean addPIMSETFromExcel() {
        Workbook workbook = excelHelper.getWorkbookRAID();
        Sheet PSSheet = workbook.getSheet(cExcelHelper.SHEET_tblPIMSET);

        if (PSSheet == null) {
            return false;
        }

        for (Row rowPS : PSSheet) {
            //just skip the row if row number is 0
            if (rowPS.getRowNum() == 0) {
                continue;
            }

            cPIMSETModel pimsetModel = new cPIMSETModel();

            pimsetModel.getPimModel().setPimID((int)
                    rowPS.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            pimsetModel.getRaidLikelihoodModel().setRaidLikelihoodID((int)
                    rowPS.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            pimsetModel.getRaidImpactModel().setRaidImpactID((int)
                    rowPS.getCell(2, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            pimsetModel.getRobotModel().setRobotID((int)
                    rowPS.getCell(3, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());

            if (!addPIMSET(pimsetModel)) {
                return false;
            }
        }

        return true;
    }

    public boolean addPIMSET(cPIMSETModel pimsetModel) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_PIM_FK_ID, pimsetModel.getPimModel().getPimID());
        cv.put(cSQLDBHelper.KEY_RAID_LIKELIHOOD_FK_ID, pimsetModel.getRaidLikelihoodModel().getRaidLikelihoodID());
        cv.put(cSQLDBHelper.KEY_RAID_IMPACT_FK_ID, pimsetModel.getRaidImpactModel().getRaidImpactID());
        cv.put(cSQLDBHelper.KEY_ROBOT_FK_ID, pimsetModel.getRobotModel().getRobotID());

        // insert record details
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblPIMSET, null, cv) < 0) {
                return false;
            }
        } catch (Exception e) {
            Log.d(TAG, "Exception in importing PIM SET from Excel: " + e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }

    @Override
    public boolean deletePIMSETs() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblPIMSET, null, null);

        // close the database connection
        db.close();

        return result > -1;
    }

    /*################################## RAID REGISTER FUNCTIONS #################################*/

    @Override
    public boolean addRAIDRegisterFromExcel() {
        Workbook workbook = excelHelper.getWorkbookRAID();
        Sheet RRSheet = workbook.getSheet(cExcelHelper.SHEET_tblRAIDREGISTER);

        Sheet RSheet = workbook.getSheet(cExcelHelper.SHEET_tblRISKREGISTER);
        Sheet ASheet = workbook.getSheet(cExcelHelper.SHEET_tblASSUMPTIONREGISTER);
        Sheet ISheet = workbook.getSheet(cExcelHelper.SHEET_tblISSUEREGISTER);
        Sheet DSheet = workbook.getSheet(cExcelHelper.SHEET_tblDEPENDENCYREGISTER);

        if (RRSheet == null) {
            return false;
        }

        for (Row rowRR : RRSheet) {
            //just skip the row if row number is 0
            if (rowRR.getRowNum() == 0) {
                continue;
            }

            cRAIDRegisterModel raidRegisterModel = new cRAIDRegisterModel();

            raidRegisterModel.setRaidRegisterID((int)
                    rowRR.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            raidRegisterModel.getPimModel().setPimID((int)
                    rowRR.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            raidRegisterModel.setName(
                    rowRR.getCell(2, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
            raidRegisterModel.setDescription(
                    rowRR.getCell(3, Row.CREATE_NULL_AS_BLANK).getStringCellValue());

            // Risk Register
            Set<Long> riskRegisterSet = new HashSet<>();
            for (Row rowR : RSheet) {
                //just skip the row if row number is 0
                if (rowR.getRowNum() == 0) {
                    continue;
                }

                long riskRegisterID = (int) rowR.getCell(0,
                        Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                if (raidRegisterModel.getRaidRegisterID() == riskRegisterID) {
                    riskRegisterSet.add(riskRegisterID);
                }
            }

            // Assumption Register
            Set<Long> assRegisterSet = new HashSet<>();
            for (Row rowA : ASheet) {
                //just skip the row if row number is 0
                if (rowA.getRowNum() == 0) {
                    continue;
                }

                long assRegisterID = (int) rowA.getCell(0,
                        Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                if (raidRegisterModel.getRaidRegisterID() == assRegisterID) {
                    assRegisterSet.add(assRegisterID);
                }
            }

            // Issue Register
            Set<Long> issueRegisterSet = new HashSet<>();
            for (Row rowI : ISheet) {
                //just skip the row if row number is 0
                if (rowI.getRowNum() == 0) {
                    continue;
                }

                long issueRegisterID = (int) rowI.getCell(0,
                        Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                if (raidRegisterModel.getRaidRegisterID() == issueRegisterID) {
                    issueRegisterSet.add(issueRegisterID);
                }
            }

            // Dependency Register
            Set<Long> depRegisterSet = new HashSet<>();
            for (Row rowD : DSheet) {
                //just skip the row if row number is 0
                if (rowD.getRowNum() == 0) {
                    continue;
                }

                long depRegisterID = (int) rowD.getCell(0,
                        Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                if (raidRegisterModel.getRaidRegisterID() == depRegisterID) {
                    depRegisterSet.add(depRegisterID);
                }
            }

            if (!addRAIDRegister(raidRegisterModel,
                    riskRegisterSet, assRegisterSet, issueRegisterSet, depRegisterSet)) {
                return false;
            }
        }

        return true;
    }

    public boolean addRAIDRegister(cRAIDRegisterModel raidRegisterModel,
                                   Set<Long> riskRegisterSet, Set<Long> assRegisterSet,
                                   Set<Long> issueRegisterSet, Set<Long> depRegisterSet) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_ID, raidRegisterModel.getRaidRegisterID());
        cv.put(cSQLDBHelper.KEY_PIM_FK_ID, raidRegisterModel.getPimModel().getPimID());
        cv.put(cSQLDBHelper.KEY_NAME, raidRegisterModel.getName());
        cv.put(cSQLDBHelper.KEY_DESCRIPTION, raidRegisterModel.getDescription());

        // insert record details
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblRAIDREGISTER, null, cv) < 0) {
                return false;
            }

            for (long riskRegisterID : riskRegisterSet) {
                if (!addRiskRegister(riskRegisterID)) {
                    return false;
                }
            }

            for (long assRegisterID : assRegisterSet) {
                if (!addAssumptionRegister(assRegisterID)) {
                    return false;
                }
            }

            for (long issueRegisterID : issueRegisterSet) {
                if (!addIssueRegister(issueRegisterID)) {
                    return false;
                }
            }

            for (long depRegisterID : depRegisterSet) {
                if (!addDependencyRegister(depRegisterID)) {
                    return false;
                }
            }


        } catch (Exception e) {
            Log.d(TAG, "Exception in importing RAID REGISTER from Excel: " + e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }

    public boolean addRiskRegister(long riskRegisterID) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_REGISTER_FK_ID, riskRegisterID);

        // insert record details
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblRISKREGISTER, null, cv) < 0) {
                return false;
            }
        } catch (Exception e) {
            Log.d(TAG, "Exception in importing RISK REGISTER from Excel: " +
                    e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }

    public boolean addAssumptionRegister(long assRegisterID) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_REGISTER_FK_ID, assRegisterID);

        // insert record details
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblASSUMPTIONREGISTER, null, cv) < 0) {
                return false;
            }
        } catch (Exception e) {
            Log.d(TAG, "Exception in importing ASSUMPTION REGISTER from Excel: " +
                    e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }

    public boolean addIssueRegister(long issueRegisterID) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_REGISTER_FK_ID, issueRegisterID);

        // insert record details
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblISSUEREGISTER, null, cv) < 0) {
                return false;
            }
        } catch (Exception e) {
            Log.d(TAG, "Exception in importing ISSUE REGISTER from Excel: " +
                    e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }

    public boolean addDependencyRegister(long depRegisterID) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_REGISTER_FK_ID, depRegisterID);

        // insert record details
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblDEPENDENCYREGISTER, null, cv) < 0) {
                return false;
            }
        } catch (Exception e) {
            Log.d(TAG, "Exception in importing DEPENDENCY REGISTER from Excel: " +
                    e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }

    @Override
    public boolean deleteRAIDRegisters() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblRAIDREGISTER, null,
                null);

        // close the database connection
        db.close();

        return result > -1;
    }

    @Override
    public boolean deleteRiskRegisters() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblRISKREGISTER, null,
                null);

        // close the database connection
        db.close();

        return result > -1;
    }

    @Override
    public boolean deleteAssumptionRegisters() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblASSUMPTIONREGISTER, null,
                null);

        // close the database connection
        db.close();

        return result > -1;
    }

    @Override
    public boolean deleteIssueRegisters() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblISSUEREGISTER, null,
                null);

        // close the database connection
        db.close();

        return result > -1;
    }

    @Override
    public boolean deleteDependencyRegisters() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblDEPENDENCYREGISTER, null,
                null);

        // close the database connection
        db.close();

        return result > -1;
    }

    /*##################################### RAID LOG FUNCTIONS ###################################*/

    @Override
    public boolean addRAIDLOGFromExcel() {
        Workbook workbook = excelHelper.getWorkbookRAID();
        Sheet RLSheet = workbook.getSheet(cExcelHelper.SHEET_tblRAIDLOG);


        if (RLSheet == null) {
            return false;
        }

        for (Row rowRL : RLSheet) {
            //just skip the row if row number is 0
            if (rowRL.getRowNum() == 0) {
                continue;
            }

            cRAIDLOGModel raidlogModel = new cRAIDLOGModel();

            raidlogModel.setRaidLogID((int)
                    rowRL.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
//            raidlogModel.getLogFrameModel().setLogFrameID((int)
//                    rowRL.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            raidlogModel.getRiskRegisterModel().setRaidRegisterID((int)
                    rowRL.getCell(2, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            raidlogModel.getAssRegisterModel().setRaidRegisterID((int)
                    rowRL.getCell(3, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            raidlogModel.getIssueRegisterModel().setRaidRegisterID((int)
                    rowRL.getCell(4, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            raidlogModel.getDepRegisterModel().setRaidRegisterID((int)
                    rowRL.getCell(5, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            raidlogModel.setName(
                    rowRL.getCell(6, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
            raidlogModel.setDescription(
                    rowRL.getCell(7, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
            if (!addRAIDLOG(raidlogModel)) {
                return false;
            }
        }

        return true;
    }

    public boolean addRAIDLOG(cRAIDLOGModel raidlogModel) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_ID, raidlogModel.getRaidLogID());
//        cv.put(cSQLDBHelper.KEY_LOGFRAME_FK_ID,
//                raidlogModel.getLogFrameModel().getLogFrameID());
        cv.put(cSQLDBHelper.KEY_RISK_REGISTER_FK_ID,
                raidlogModel.getRiskRegisterModel().getRaidRegisterID());
        cv.put(cSQLDBHelper.KEY_ASS_REGISTER_FK_ID,
                raidlogModel.getAssRegisterModel().getRaidRegisterID());
        cv.put(cSQLDBHelper.KEY_ISSUE_REGISTER_FK_ID,
                raidlogModel.getIssueRegisterModel().getRaidRegisterID());
        cv.put(cSQLDBHelper.KEY_DEP_REGISTER_FK_ID,
                raidlogModel.getDepRegisterModel().getRaidRegisterID());
        cv.put(cSQLDBHelper.KEY_NAME, raidlogModel.getName());
        cv.put(cSQLDBHelper.KEY_DESCRIPTION, raidlogModel.getDescription());

        // insert record details
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblRAIDLOG, null, cv) < 0) {
                return false;
            }
        } catch (Exception e) {
            Log.d(TAG, "Exception in importing RAID LOG from Excel: " + e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }

    @Override
    public boolean deleteRAIDLOGs() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblRAIDLOG, null,
                null);

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

        for (Row rowR : RSheet) {
            //just skip the row if row number is 0
            if (rowR.getRowNum() == 0) {
                continue;
            }

            cRiskModel riskModel = new cRiskModel();

            riskModel.setRaidID((int)
                    rowR.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            riskModel.getRiskRegisterModel().setRaidRegisterID((int)
                    rowR.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());

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
        cv.put(cSQLDBHelper.KEY_RISK_REGISTER_FK_ID,
                riskModel.getRiskRegisterModel().getRaidRegisterID());

        // insert record details
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblRISK, null, cv) < 0) {
                return false;
            }

            /* risk root causes */
            for (Row rowRRS : RRSSheet) {
                //just skip the row if row number is 0
                if (rowRRS.getRowNum() == 0) {
                    continue;
                }

                int riskID = (int) rowRRS.getCell(1,
                        Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                if (riskModel.getRaidID() == riskID) {

                    cRiskRootCauseModel riskRootCauseModel = new cRiskRootCauseModel();

                    riskRootCauseModel.setRiskRootCauseID((int)
                            rowRRS.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
                    riskRootCauseModel.getRiskModel().setRaidID(riskID);
                    riskRootCauseModel.setName(
                            rowRRS.getCell(2, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
                    riskRootCauseModel.setDescription(
                            rowRRS.getCell(3, Row.CREATE_NULL_AS_BLANK).getStringCellValue());

                    if (!addRiskRootCause(riskRootCauseModel)) {
                        return false;
                    }
                }
            }

            /* risk consequences */
            for (Row rowRCS : RCSSheet) {
                //just skip the row if row number is 0
                if (rowRCS.getRowNum() == 0) {
                    continue;
                }

                int riskID = (int) rowRCS.getCell(1,
                        Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                if (riskModel.getRaidID() == riskID) {

                    cRiskConsequenceModel riskConsequenceModel = new cRiskConsequenceModel();

                    riskConsequenceModel.setRiskConsequenceID((int)
                            rowRCS.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
                    riskConsequenceModel.getRiskModel().setRaidID(riskID);
                    riskConsequenceModel.setName(
                            rowRCS.getCell(2, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
                    riskConsequenceModel.setDescription(
                            rowRCS.getCell(3, Row.CREATE_NULL_AS_BLANK).getStringCellValue());

                    if (!addRiskConsequence(riskConsequenceModel)) {
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

    private boolean addRiskRootCause(cRiskRootCauseModel riskRootCauseModel) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_ID, riskRootCauseModel.getRiskRootCauseID());
        cv.put(cSQLDBHelper.KEY_RISK_FK_ID, riskRootCauseModel.getRiskModel().getRaidID());
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

    private boolean addRiskConsequence(cRiskConsequenceModel riskConsequenceModel) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_ID, riskConsequenceModel.getRiskConsequenceID());
        cv.put(cSQLDBHelper.KEY_RISK_FK_ID, riskConsequenceModel.getRiskModel().getRaidID());
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
    public boolean deleteRiskRootCauses() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblRISKROOTCAUSE, null, null);

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

    /*################################### ASSUMPTION FUNCTIONS ###################################*/

    @Override
    public boolean addAssumptionFromExcel() {
        Workbook workbook = excelHelper.getWorkbookRAID();
        Sheet ASheet = workbook.getSheet(cExcelHelper.SHEET_tblASSUMPTION);

        if (ASheet == null) {
            return false;
        }

        for (Row rowA : ASheet) {
            //just skip the row if row number is 0
            if (rowA.getRowNum() == 0) {
                continue;
            }

            cAssumptionModel assumptionModel = new cAssumptionModel();

            assumptionModel.setRaidID((int)
                    rowA.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            assumptionModel.getAssumptionRegisterModel().setRaidRegisterID((int)
                    rowA.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());

            if (!addAssumption(assumptionModel)) {
                return false;
            }
        }

        return true;
    }

    private boolean addAssumption(cAssumptionModel assumptionModel) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_RAID_FK_ID, assumptionModel.getRaidID());
        cv.put(cSQLDBHelper.KEY_ASS_REGISTER_FK_ID,
                assumptionModel.getAssumptionRegisterModel().getRaidRegisterID());

        // insert record details
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblASSUMPTION, null, cv) < 0) {
                return false;
            }
        } catch (Exception e) {
            Log.d(TAG, "Exception in importing ASSUMPTION from Excel: " + e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }

    @Override
    public boolean deleteAssumptions() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblASSUMPTION, null,
                null);

        // close the database connection
        db.close();

        return result > -1;
    }

    /*##################################### ISSUE FUNCTIONS ######################################*/

    @Override
    public boolean addIssueFromExcel() {
        Workbook workbook = excelHelper.getWorkbookRAID();
        Sheet ISheet = workbook.getSheet(cExcelHelper.SHEET_tblISSUE);

        if (ISheet == null) {
            return false;
        }

        for (Row rowI : ISheet) {
            //just skip the row if row number is 0
            if (rowI.getRowNum() == 0) {
                continue;
            }

            cIssueModel issueModel = new cIssueModel();

            issueModel.setRaidID((int)
                    rowI.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            issueModel.getIssueRegisterModel().setRaidRegisterID((int)
                    rowI.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());

            if (!addIssue(issueModel)) {
                return false;
            }
        }

        return true;
    }

    private boolean addIssue(cIssueModel issueModel) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_RAID_FK_ID, issueModel.getRaidID());
        cv.put(cSQLDBHelper.KEY_ISSUE_REGISTER_FK_ID,
                issueModel.getIssueRegisterModel().getRaidRegisterID());

        // insert record details
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblISSUE, null, cv) < 0) {
                return false;
            }
        } catch (Exception e) {
            Log.d(TAG, "Exception in importing ISSUE from Excel: " + e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }

    @Override
    public boolean deleteIssues() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblISSUE, null,
                null);

        // close the database connection
        db.close();

        return result > -1;
    }

    /*################################## ISSUE COMMENT FUNCTIONS #################################*/

    @Override
    public boolean addIssueCommentFromExcel() {
        Workbook workbook = excelHelper.getWorkbookRAID();
        Sheet ICSheet = workbook.getSheet(cExcelHelper.SHEET_tblISSUECOMMENT);

        if (ICSheet == null) {
            return false;
        }

        for (Row rowIC : ICSheet) {
            //just skip the row if row number is 0
            if (rowIC.getRowNum() == 0) {
                continue;
            }

            cIssueCommentModel issueCommentModel = new cIssueCommentModel();

            issueCommentModel.setIssueCommentID((int)
                    rowIC.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            issueCommentModel.getIssueModel().setRaidID((int)
                    rowIC.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            issueCommentModel.getStaffModel().setHumanSetID((int)
                    rowIC.getCell(2, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());

            if (!addIssueComment(issueCommentModel)) {
                return false;
            }
        }

        return true;
    }

    private boolean addIssueComment(cIssueCommentModel issueCommentModel) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        //cv.put(cSQLDBHelper.KEY_RAID_FK_ID, issueCommentModel.getRaidID());
        //cv.put(cSQLDBHelper.KEY_DEP_REGISTER_FK_ID,issueCommentModel.getDependencyRegisterModel().getRaidRegisterID());

        // insert record details
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblREVIEWCOMMENT, null, cv) < 0) {
                return false;
            }
        } catch (Exception e) {
            Log.d(TAG, "Exception in importing ISSUE COMMENT from Excel: " + e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }

    //@Override
    public boolean deleteReviewComments() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblREVIEWCOMMENT, null,
                null);

        // close the database connection
        db.close();

        return result > -1;
    }

    /*################################### DEPENDENCY FUNCTIONS ###################################*/

    @Override
    public boolean addDependencyFromExcel() {
        Workbook workbook = excelHelper.getWorkbookRAID();
        Sheet DSheet = workbook.getSheet(cExcelHelper.SHEET_tblDEPENDENCY);

        if (DSheet == null) {
            return false;
        }

        for (Row rowD : DSheet) {
            //just skip the row if row number is 0
            if (rowD.getRowNum() == 0) {
                continue;
            }

            cDependencyModel dependencyModel = new cDependencyModel();

            dependencyModel.setRaidID((int)
                    rowD.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            dependencyModel.getDependencyRegisterModel().setRaidRegisterID((int)
                    rowD.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            dependencyModel.setHowValidated(
                    rowD.getCell(2, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
            dependencyModel.setValidated((int)
                    rowD.getCell(3, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());

            if (!addDependency(dependencyModel)) {
                return false;
            }
        }

        return true;
    }

    private boolean addDependency(cDependencyModel dependencyModel) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_RAID_FK_ID, dependencyModel.getRaidID());
        cv.put(cSQLDBHelper.KEY_DEP_REGISTER_FK_ID,
                dependencyModel.getDependencyRegisterModel().getRaidRegisterID());
        cv.put(cSQLDBHelper.KEY_HOW_VALIDATE, dependencyModel.getHowValidated());
        cv.put(cSQLDBHelper.KEY_VALIDATED, dependencyModel.getValidated());

        // insert record details
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblDEPENDENCY, null, cv) < 0) {
                return false;
            }
        } catch (Exception e) {
            Log.d(TAG, "Exception in importing DEPENDENCY from Excel: " + e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }

    @Override
    public boolean deleteDependencies() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblDEPENDENCY, null,
                null);

        // close the database connection
        db.close();

        return result > -1;
    }

    /*################################### RAID REVIEW FUNCTIONS ##################################*/

    @Override
    public boolean addMilestoneReviewFromExcel() {
        Workbook workbook = excelHelper.getWorkbookRAID();
        Sheet RRSheet = workbook.getSheet(cExcelHelper.SHEET_tblMILESTONEREVIEW);

        Sheet RSheet = workbook.getSheet(cExcelHelper.SHEET_tblRISKREVIEW);
        Sheet ASheet = workbook.getSheet(cExcelHelper.SHEET_tblASSUMPTIONREVIEW);
        Sheet ISheet = workbook.getSheet(cExcelHelper.SHEET_tblISSUEREVIEW);
        Sheet DSheet = workbook.getSheet(cExcelHelper.SHEET_tblDEPENDENCYREVIEW);

        if (RRSheet == null) {
            return false;
        }

        for (Row rowRR : RRSheet) {
            //just skip the row if row number is 0
            if (rowRR.getRowNum() == 0) {
                continue;
            }

            cMilestoneReviewModel milestoneReviewModel = new cMilestoneReviewModel();

            milestoneReviewModel.setMilestoneID((int)
                    rowRR.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            milestoneReviewModel.getRaidLikelihoodModel().setRaidLikelihoodID((int)
                    rowRR.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            milestoneReviewModel.getRaidImpactModel().setRaidImpactID((int)
                    rowRR.getCell(2, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            milestoneReviewModel.getRobotModel().setRobotID((int)
                    rowRR.getCell(3, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());

            // Risk Review
            Set<cRiskReviewModel> riskReviewSet = new HashSet<>();
            for (Row rowR : RSheet) {
                //just skip the row if row number is 0
                if (rowR.getRowNum() == 0) {
                    continue;
                }

                long riskReviewID = (int) rowR.getCell(0,
                        Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                if (milestoneReviewModel.getMilestoneID() == riskReviewID) {
                    cRiskReviewModel riskReviewModel = new cRiskReviewModel();

                    riskReviewModel.setMilestoneID(riskReviewID);
                    riskReviewModel.getRiskModel().setRaidID((int) rowR.getCell(1,
                            Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
                    riskReviewModel.setResidualRisk((int) rowR.getCell(2,
                            Row.CREATE_NULL_AS_BLANK).getNumericCellValue());

                    riskReviewSet.add(riskReviewModel);
                }
            }

            // Assumption Review
            Set<cAssumptionReviewModel> assReviewSet = new HashSet<>();
            for (Row rowA : ASheet) {
                //just skip the row if row number is 0
                if (rowA.getRowNum() == 0) {
                    continue;
                }

                long assReviewID = (int) rowA.getCell(0,
                        Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                if (milestoneReviewModel.getMilestoneID() == assReviewID) {
                    cAssumptionReviewModel assReviewModel = new cAssumptionReviewModel();

                    assReviewModel.setMilestoneID(assReviewID);
                    assReviewModel.getAssumptionModel().setRaidID((int) rowA.getCell(1,
                            Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
                    assReviewModel.setAssumptionRating((int) rowA.getCell(2,
                            Row.CREATE_NULL_AS_BLANK).getNumericCellValue());

                    assReviewSet.add(assReviewModel);
                }
            }

            // Issue Review
            Set<cIssueReviewModel> issueReviewSet = new HashSet<>();
            for (Row rowI : ISheet) {
                //just skip the row if row number is 0
                if (rowI.getRowNum() == 0) {
                    continue;
                }

                long issueReviewID = (int) rowI.getCell(0,
                        Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                if (milestoneReviewModel.getMilestoneID() == issueReviewID) {
                    cIssueReviewModel issueReviewModel = new cIssueReviewModel();

                    issueReviewModel.setMilestoneID(issueReviewID);
                    issueReviewModel.getIssueModel().setRaidID((int) rowI.getCell(1,
                            Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
                    issueReviewModel.setPriorityRating((int) rowI.getCell(2,
                            Row.CREATE_NULL_AS_BLANK).getNumericCellValue());

                    issueReviewSet.add(issueReviewModel);
                }
            }

            // Dependency Review
            Set<cDependencyReviewModel> depReviewSet = new HashSet<>();
            for (Row rowD : DSheet) {
                //just skip the row if row number is 0
                if (rowD.getRowNum() == 0) {
                    continue;
                }

                long depReviewID = (int) rowD.getCell(0,
                        Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                if (milestoneReviewModel.getMilestoneID() == depReviewID) {
                    cDependencyReviewModel depReviewModel = new cDependencyReviewModel();

                    depReviewModel.setMilestoneID(depReviewID);
                    depReviewModel.getDependencyModel().setRaidID((int) rowD.getCell(1,
                            Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
                    depReviewModel.setDependencyRating((int) rowD.getCell(2,
                            Row.CREATE_NULL_AS_BLANK).getNumericCellValue());

                    depReviewSet.add(depReviewModel);
                }
            }

            if (!addMilestoneReview(milestoneReviewModel,
                    riskReviewSet, assReviewSet, issueReviewSet, depReviewSet)) {
                return false;
            }
        }

        return true;
    }

    public boolean addMilestoneReview(cMilestoneReviewModel milestoneReviewModel,
                                      Set<cRiskReviewModel> riskReviewSet,
                                      Set<cAssumptionReviewModel> assReviewSet,
                                      Set<cIssueReviewModel> issueReviewSet,
                                      Set<cDependencyReviewModel> depReviewSet) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_ID, milestoneReviewModel.getMilestoneID());
        cv.put(cSQLDBHelper.KEY_RAID_LIKELIHOOD_FK_ID,
                milestoneReviewModel.getRaidLikelihoodModel().getRaidLikelihoodID());
        cv.put(cSQLDBHelper.KEY_RAID_IMPACT_FK_ID,
                milestoneReviewModel.getRaidImpactModel().getRaidImpactID());
        cv.put(cSQLDBHelper.KEY_ROBOT_FK_ID, milestoneReviewModel.getRobotModel().getRobotID());

        // insert record details
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblMILESTONEREVIEW, null, cv) < 0) {
                return false;
            }

            for (cRiskReviewModel riskReview : riskReviewSet) {
                if (!addRiskReview(riskReview)) {
                    return false;
                }
            }

            for (cAssumptionReviewModel assReview : assReviewSet) {
                if (!addAssumptionReview(assReview)) {
                    return false;
                }
            }

            for (cIssueReviewModel issueReview : issueReviewSet) {
                if (!addIssueReview(issueReview)) {
                    return false;
                }
            }

            for (cDependencyReviewModel depReview : depReviewSet) {
                if (!addDependencyReview(depReview)) {
                    return false;
                }
            }


        } catch (Exception e) {
            Log.d(TAG, "Exception in importing MILESTONE REVIEW from Excel: " + e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }

    public boolean addRiskReview(cRiskReviewModel riskReview) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_RISK_REVIEW_FK_ID, riskReview.getMilestoneID());
        cv.put(cSQLDBHelper.KEY_RISK_FK_ID, riskReview.getRiskModel().getRaidID());
        cv.put(cSQLDBHelper.KEY_RESIDUAL_RISK, riskReview.getResidualRisk());

        // insert record details
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblRISKREVIEW, null, cv) < 0) {
                return false;
            }
        } catch (Exception e) {
            Log.d(TAG, "Exception in importing RISK REVIEW from Excel: " +
                    e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }

    public boolean addAssumptionReview(cAssumptionReviewModel assReview) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_ASSUMPTION_REVIEW_FK_ID, assReview.getMilestoneID());
        cv.put(cSQLDBHelper.KEY_ASSUMPTION_FK_ID, assReview.getAssumptionModel().getRaidID());
        cv.put(cSQLDBHelper.KEY_ASSUMPTION_RATING, assReview.getAssumptionRating());

        // insert record details
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblASSUMPTIONREVIEW, null, cv) < 0) {
                return false;
            }
        } catch (Exception e) {
            Log.d(TAG, "Exception in importing ASSUMPTION REVIEW from Excel: " +
                    e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }

    public boolean addIssueReview(cIssueReviewModel issueReview) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_ISSUE_REVIEW_FK_ID, issueReview.getMilestoneID());
        cv.put(cSQLDBHelper.KEY_ISSUE_FK_ID, issueReview.getIssueModel().getRaidID());
        cv.put(cSQLDBHelper.KEY_PRIORITY_RATING, issueReview.getPriorityRating());

        // insert record details
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblISSUEREVIEW, null, cv) < 0) {
                return false;
            }
        } catch (Exception e) {
            Log.d(TAG, "Exception in importing ISSUE REVIEW from Excel: " +
                    e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }

    public boolean addDependencyReview(cDependencyReviewModel depReview) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_DEPENDENCY_REVIEW_FK_ID, depReview.getMilestoneID());
        cv.put(cSQLDBHelper.KEY_DEPENDENCY_FK_ID, depReview.getDependencyModel().getRaidID());
        cv.put(cSQLDBHelper.KEY_DEPENDENCY_RATING, depReview.getDependencyRating());

        // insert record details
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblDEPENDENCYREVIEW, null, cv) < 0) {
                return false;
            }
        } catch (Exception e) {
            Log.d(TAG, "Exception in importing DEPENDENCY REVIEW from Excel: " +
                    e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }

    @Override
    public boolean deleteMilestoneReviews() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblMILESTONEREVIEW, null,
                null);

        // close the database connection
        db.close();

        return result > -1;
    }

    @Override
    public boolean deleteRiskReviews() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblRISKREVIEW, null,
                null);

        // close the database connection
        db.close();

        return result > -1;
    }

    @Override
    public boolean deleteAssumptionReviews() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblASSUMPTIONREVIEW, null,
                null);

        // close the database connection
        db.close();

        return result > -1;
    }

    @Override
    public boolean deleteIssueReviews() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblISSUEREVIEW, null,
                null);

        // close the database connection
        db.close();

        return result > -1;
    }

    @Override
    public boolean deleteDependencyReviews() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblDEPENDENCYREVIEW, null,
                null);

        // close the database connection
        db.close();

        return result > -1;
    }

    /*###################################### ACTION FUNCTIONS ####################################*/

    @Override
    public boolean addActionFromExcel() {
        Workbook workbook = excelHelper.getWorkbookRAID();
        Sheet ASheet = workbook.getSheet(cExcelHelper.SHEET_tblACTION);

        Sheet RASheet = workbook.getSheet(cExcelHelper.SHEET_tblRISKACTION);
        Sheet AASheet = workbook.getSheet(cExcelHelper.SHEET_tblASSUMPTIONACTION);
        Sheet IASheet = workbook.getSheet(cExcelHelper.SHEET_tblISSUEACTION);
        Sheet DASheet = workbook.getSheet(cExcelHelper.SHEET_tblDEPENDENCYACTION);

        if (ASheet == null) {
            return false;
        }

        for (Row rowA : ASheet) {
            //just skip the row if row number is 0
            if (rowA.getRowNum() == 0) {
                continue;
            }

            cActionModel actionModel = new cActionModel();

            actionModel.setWorkplanID((int)
                    rowA.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());


            // Risk Action
            Set<cRiskActionModel> riskActionSet = new HashSet<>();
            for (Row rowRA : RASheet) {
                //just skip the row if row number is 0
                if (rowRA.getRowNum() == 0) {
                    continue;
                }

                long riskActionID = (int) rowRA.getCell(0,
                        Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                if (actionModel.getWorkplanID() == riskActionID) {
                    cRiskActionModel riskActionModel = new cRiskActionModel();

                    riskActionModel.setWorkplanID(riskActionID);
                    riskActionModel.getRiskReviewModel().setMilestoneID((int)
                            rowRA.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
                    riskActionModel.getRiskActionTypeModel().setRiskActionTypeID((int)
                            rowRA.getCell(2, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());

                    riskActionSet.add(riskActionModel);
                }
            }

            // Assumption Action
            Set<cAssumptionActionModel> assActionSet = new HashSet<>();
            for (Row rowAA : AASheet) {
                //just skip the row if row number is 0
                if (rowAA.getRowNum() == 0) {
                    continue;
                }

                long assActionID = (int) rowA.getCell(0,
                        Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                if (actionModel.getWorkplanID() == assActionID) {
                    cAssumptionActionModel assActionModel = new cAssumptionActionModel();

                    assActionModel.setWorkplanID(assActionID);
                    assActionModel.getAssumptionReviewModel().setMilestoneID((int)
                            rowA.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());

                    assActionSet.add(assActionModel);
                }
            }

            // Issue Action
            Set<cIssueActionModel> issueActionSet = new HashSet<>();
            for (Row rowIA : IASheet) {
                //just skip the row if row number is 0
                if (rowIA.getRowNum() == 0) {
                    continue;
                }

                long issueActionID = (int) rowIA.getCell(0,
                        Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                if (actionModel.getWorkplanID() == issueActionID) {
                    cIssueActionModel issueActionModel = new cIssueActionModel();

                    issueActionModel.setWorkplanID(issueActionID);
                    issueActionModel.getIssueReviewModel().setMilestoneID((int)
                            rowIA.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());

                    issueActionSet.add(issueActionModel);
                }
            }

            // Dependency Action
            Set<cDependencyActionModel> depActionSet = new HashSet<>();
            for (Row rowDA : DASheet) {
                //just skip the row if row number is 0
                if (rowDA.getRowNum() == 0) {
                    continue;
                }

                long depActionID = (int) rowDA.getCell(0,
                        Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                if (actionModel.getWorkplanID() == depActionID) {
                    cDependencyActionModel depActionModel = new cDependencyActionModel();

                    depActionModel.setWorkplanID(depActionID);
                    depActionModel.getDependencyReviewModel().setMilestoneID((int)
                            rowDA.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());

                    depActionSet.add(depActionModel);
                }
            }

            if (!addAction(actionModel,
                    riskActionSet, assActionSet, issueActionSet, depActionSet)) {
                return false;
            }
        }

        return true;
    }

    public boolean addAction(cActionModel actionModel,
                             Set<cRiskActionModel> riskActionSet,
                             Set<cAssumptionActionModel> assActionSet,
                             Set<cIssueActionModel> issueActionSet,
                             Set<cDependencyActionModel> depActionSet) {

        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_ACTIVITY_FK_ID, actionModel.getWorkplanID());

        // insert record details
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblACTION, null, cv) < 0) {
                return false;
            }

            for (cRiskActionModel riskAction : riskActionSet) {
                if (!addRiskAction(riskAction)) {
                    return false;
                }
            }

            for (cAssumptionActionModel assAction : assActionSet) {
                if (!addAssumptionAction(assAction)) {
                    return false;
                }
            }

            for (cIssueActionModel issueAction : issueActionSet) {
                if (!addIssueAction(issueAction)) {
                    return false;
                }
            }

            for (cDependencyActionModel depAction : depActionSet) {
                if (!addDependencyAction(depAction)) {
                    return false;
                }
            }


        } catch (Exception e) {
            Log.d(TAG, "Exception in importing ACTION from Excel: " + e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }

    public boolean addRiskAction(cRiskActionModel riskAction) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_ACTION_FK_ID, riskAction.getWorkplanID());
        cv.put(cSQLDBHelper.KEY_RISK_REVIEW_FK_ID,
                riskAction.getRiskReviewModel().getMilestoneID());
        cv.put(cSQLDBHelper.KEY_RISK_ACTION_TYPE_FK_ID,
                riskAction.getRiskActionTypeModel().getRiskActionTypeID());

        // insert record details
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblRISKACTION, null, cv) < 0) {
                return false;
            }
        } catch (Exception e) {
            Log.d(TAG, "Exception in importing RISK ACTION from Excel: " +
                    e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }

    public boolean addAssumptionAction(cAssumptionActionModel assAction) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_ACTION_FK_ID, assAction.getWorkplanID());
        cv.put(cSQLDBHelper.KEY_ASSUMPTION_REVIEW_FK_ID,
                assAction.getAssumptionReviewModel().getMilestoneID());

        // insert record details
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblASSUMPTIONACTION, null, cv) < 0) {
                return false;
            }
        } catch (Exception e) {
            Log.d(TAG, "Exception in importing ASSUMPTION ACTION from Excel: " +
                    e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }

    public boolean addIssueAction(cIssueActionModel issueAction) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_ACTION_FK_ID, issueAction.getWorkplanID());
        cv.put(cSQLDBHelper.KEY_ISSUE_REVIEW_FK_ID,
                issueAction.getIssueReviewModel().getMilestoneID());

        // insert record details
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblISSUEACTION, null, cv) < 0) {
                return false;
            }
        } catch (Exception e) {
            Log.d(TAG, "Exception in importing ISSUE ACTION from Excel: " +
                    e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }

    public boolean addDependencyAction(cDependencyActionModel depAction) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_ACTION_FK_ID, depAction.getWorkplanID());
        cv.put(cSQLDBHelper.KEY_DEPENDENCY_REVIEW_FK_ID,
                depAction.getDependencyReviewModel().getMilestoneID());

        // insert record details
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblDEPENDENCYACTION, null, cv) < 0) {
                return false;
            }
        } catch (Exception e) {
            Log.d(TAG, "Exception in importing DEPENDENCY ACTION from Excel: " +
                    e.getMessage());
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
    public boolean deleteActions() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblACTION, null,
                null);

        // close the database connection
        db.close();

        return result > -1;
    }

    @Override
    public boolean deleteRiskActions() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblRISKACTION, null,
                null);

        // close the database connection
        db.close();

        return result > -1;
    }

    @Override
    public boolean deleteAssumptionActions() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblASSUMPTIONACTION, null,
                null);

        // close the database connection
        db.close();

        return result > -1;
    }

    @Override
    public boolean deleteIssueActions() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblISSUEACTION, null,
                null);

        // close the database connection
        db.close();

        return result > -1;
    }

    @Override
    public boolean deleteDependencyActions() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblDEPENDENCYACTION, null,
                null);

        // close the database connection
        db.close();

        return result > -1;
    }

    @Override
    public boolean deleteRiskCurrentControls() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblCURRENTCONTROL, null,
                null);

        // close the database connection
        db.close();

        return result > -1;
    }

    @Override
    public boolean deleteRiskAdditionalControls() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblADDITIONALCONTROL, null,
                null);

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

        for (Row cRow : RATSheet) {
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





//    /*################################### RISK IMPACT FUNCTIONS ##################################*/
//
//    @Override
//    public boolean addRiskImpactFromExcel() {
//        Workbook workbook = excelHelper.getWorkbookRAID();
//        Sheet RISheet = workbook.getSheet(cExcelHelper.SHEET_tblRISKIMPACT);
//        Sheet RISSheet = workbook.getSheet(cExcelHelper.SHEET_tblRISKIMPACTSET);
//
//
//        if (RISheet == null) {
//            return false;
//        }
//
//        for (Iterator<Row> ritRI = RISheet.iterator(); ritRI.hasNext(); ) {
//            Row cRowRI = ritRI.next();
//
//            //just skip the row if row number is 0
//            if (cRowRI.getRowNum() == 0) {
//                continue;
//            }
//
//            cRAIDImpactModel riskImpactModel = new cRAIDImpactModel();
//
//            riskImpactModel.setRiskImpactID((int)
//                    cRowRI.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
//            riskImpactModel.setName(
//                    cRowRI.getCell(1, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
//            riskImpactModel.setDescription(
//                    cRowRI.getCell(2, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
//            riskImpactModel.setValue((int)
//                    cRowRI.getCell(3, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
//
//            if (!addRiskImpact(riskImpactModel, RISSheet)) {
//                return false;
//            }
//        }
//
//        return true;
//    }
//
//    public boolean addRiskImpact(cRAIDImpactModel riskImpactModel, Sheet RISSheet) {
//        // open the connection to the database
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//
//        // create content object for storing data
//        ContentValues cv = new ContentValues();
//
//        // assign values to the table fields
//        cv.put(cSQLDBHelper.KEY_ID, riskImpactModel.getRiskImpactID());
//        cv.put(cSQLDBHelper.KEY_NAME, riskImpactModel.getName());
//        cv.put(cSQLDBHelper.KEY_DESCRIPTION, riskImpactModel.getDescription());
//        cv.put(cSQLDBHelper.KEY_RISKIMPACT_VALUE, riskImpactModel.getValue());
//
//        // insert record details
//        try {
//            if (db.insert(cSQLDBHelper.TABLE_tblRISKIMPACT, null, cv) < 0) {
//                return false;
//            }
//
//            for (Iterator<Row> ritRIS = RISSheet.iterator(); ritRIS.hasNext(); ) {
//                Row rowRIS = ritRIS.next();
//
//                //just skip the row if row number is 0
//                if (rowRIS.getRowNum() == 0) {
//                    continue;
//                }
//
//                int riskImpactID = (int) rowRIS.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
//                if (riskImpactModel.getRiskImpactID() == riskImpactID) {
//                    int registerID = (int) rowRIS.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
//
//                    if (!addRiskImpactSet(registerID, riskImpactID)) {
//                        return false;
//                    }
//                }
//            }
//
//        } catch (Exception e) {
//            Log.d(TAG, "Exception in importing RISK IMPACT from Excel: " + e.getMessage());
//        }
//
//        // close the database connection
//        db.close();
//
//        return true;
//    }
//
//    public boolean addRiskImpactSet(int registerID, int riskImpactID) {
//        // open the connection to the database
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//
//        // create content object for storing data
//        ContentValues cv = new ContentValues();
//
//        // assign values to the table fields
//        cv.put(cSQLDBHelper.KEY_RISK_REGISTER_FK_ID, registerID);
//        cv.put(cSQLDBHelper.KEY_RISK_IMPACT_FK_ID, riskImpactID);
//
//        // insert record details
//        try {
//            if (db.insert(cSQLDBHelper.TABLE_tblRISKIMPACTSET, null, cv) < 0) {
//                return false;
//            }
//
//        } catch (Exception e) {
//            Log.d(TAG, "Exception in importing RISK IMPACT SET from Excel: " + e.getMessage());
//        }
//
//        // close the database connection
//        db.close();
//
//        return true;
//    }
//
//    @Override
//    public boolean deleteRiskImpacts() {
//        // open the connection to the database
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//
//        // delete all records
//        long result = db.delete(cSQLDBHelper.TABLE_tblRISKIMPACT, null, null);
//
//        // close the database connection
//        db.close();
//
//        return result > -1;
//    }
//
//    @Override
//    public boolean deleteRiskImpactSets() {
//        // open the connection to the database
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//
//        // delete all records
//        long result = db.delete(cSQLDBHelper.TABLE_tblRISKIMPACTSET, null, null);
//
//        // close the database connection
//        db.close();
//
//        return result > -1;
//    }
//
//    /*################################## RISK CRITERIA FUNCTIONS #################################*/
//
//    @Override
//    public boolean addRiskCriteriaFromExcel() {
//        Workbook workbook = excelHelper.getWorkbookRAID();
//        Sheet RCSheet = workbook.getSheet(cExcelHelper.SHEET_tblRISKCRITERIA);
//        Sheet RCSSheet = workbook.getSheet(cExcelHelper.SHEET_tblRISKCRITERIASET);
//
//
//        if (RCSheet == null) {
//            return false;
//        }
//
//        for (Iterator<Row> ritRC = RCSheet.iterator(); ritRC.hasNext(); ) {
//            Row cRowRC = ritRC.next();
//
//            //just skip the row if row number is 0
//            if (cRowRC.getRowNum() == 0) {
//                continue;
//            }
//
//            cRobotModel riskCriteriaModel = new cRobotModel();
//
//            riskCriteriaModel.setRiskCriteriaID((int)
//                    cRowRC.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
//            riskCriteriaModel.setName(
//                    cRowRC.getCell(1, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
//            riskCriteriaModel.setDescription(
//                    cRowRC.getCell(2, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
//            riskCriteriaModel.setLowerLimit((int)
//                    cRowRC.getCell(3, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
//            riskCriteriaModel.setUpperLimit((int)
//                    cRowRC.getCell(4, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
//
//            if (!addRiskCriteria(riskCriteriaModel, RCSSheet)) {
//                return false;
//            }
//        }
//
//        return true;
//    }
//
//    public boolean addRiskCriteria(cRobotModel riskCriteriaModel, Sheet RCSSheet) {
//        // open the connection to the database
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//
//        // create content object for storing data
//        ContentValues cv = new ContentValues();
//
//        // assign values to the table fields
//        cv.put(cSQLDBHelper.KEY_ID, riskCriteriaModel.getRiskCriteriaID());
//        cv.put(cSQLDBHelper.KEY_NAME, riskCriteriaModel.getName());
//        cv.put(cSQLDBHelper.KEY_DESCRIPTION, riskCriteriaModel.getDescription());
//        cv.put(cSQLDBHelper.KEY_RISKMAP_LOWER, riskCriteriaModel.getLowerLimit());
//        cv.put(cSQLDBHelper.KEY_RISKMAP_UPPER, riskCriteriaModel.getUpperLimit());
//
//        // insert record details
//        try {
//            if (db.insert(cSQLDBHelper.TABLE_tblRISKCRITERIA, null, cv) < 0) {
//                return false;
//            }
//
//            for (Iterator<Row> ritRCS = RCSSheet.iterator(); ritRCS.hasNext(); ) {
//                Row rowRCS = ritRCS.next();
//
//                //just skip the row if row number is 0
//                if (rowRCS.getRowNum() == 0) {
//                    continue;
//                }
//
//                int riskCriteriaID = (int) rowRCS.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
//                if (riskCriteriaModel.getRiskCriteriaID() == riskCriteriaID) {
//                    int registerID = (int) rowRCS.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
//
//                    if (!addRiskCriteriaSet(registerID, riskCriteriaID)) {
//                        return false;
//                    }
//                }
//            }
//
//        } catch (Exception e) {
//            Log.d(TAG, "Exception in importing RISK CRITERIA from Excel: " + e.getMessage());
//        }
//
//        // close the database connection
//        db.close();
//
//        return true;
//    }
//
//    public boolean addRiskCriteriaSet(int registerID, int riskCriteriaID) {
//        // open the connection to the database
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//
//        // create content object for storing data
//        ContentValues cv = new ContentValues();
//
//        // assign values to the table fields
//        cv.put(cSQLDBHelper.KEY_RISK_REGISTER_FK_ID, registerID);
//        cv.put(cSQLDBHelper.KEY_RISK_CRITERIA_FK_ID, riskCriteriaID);
//
//        // insert record details
//        try {
//            if (db.insert(cSQLDBHelper.TABLE_tblRISKCRITERIASET, null, cv) < 0) {
//                return false;
//            }
//
//        } catch (Exception e) {
//            Log.d(TAG, "Exception in importing RISK CRITERIA SET from Excel: " + e.getMessage());
//        }
//
//        // close the database connection
//        db.close();
//
//        return true;
//    }
//
//    @Override
//    public boolean deleteRiskCriteria() {
//        // open the connection to the database
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//
//        // delete all records
//        long result = db.delete(cSQLDBHelper.TABLE_tblRISKCRITERIA, null, null);
//
//        // close the database connection
//        db.close();
//
//        return result > -1;
//    }
//
//    @Override
//    public boolean deleteRiskCriteriaSets() {
//        // open the connection to the database
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//
//        // delete all records
//        long result = db.delete(cSQLDBHelper.TABLE_tblRISKCRITERIASET, null, null);
//
//        // close the database connection
//        db.close();
//
//        return result > -1;
//    }
//
//
//    /*################################## RISK ANALYSIS FUNCTIONS #################################*/
//
//    @Override
//    public boolean addRiskAnalysisFromExcel() {
//        Workbook workbook = excelHelper.getWorkbookRAID();
//        Sheet RASheet = workbook.getSheet(cExcelHelper.SHEET_tblRISKANALYSIS);
//
//        if (RASheet == null) {
//            return false;
//        }
//
//        for (Iterator<Row> ritRA = RASheet.iterator(); ritRA.hasNext(); ) {
//            Row cRowRA = ritRA.next();
//
//            //just skip the row if row number is 0
//            if (cRowRA.getRowNum() == 0) {
//                continue;
//            }
//
//            cMilestoneReviewModel riskAnalysisModel = new cMilestoneReviewModel();
//
//            riskAnalysisModel.setRiskAnalysisID((int)
//                    cRowRA.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
//            riskAnalysisModel.setRiskMilestoneID((int)
//                    cRowRA.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
//            riskAnalysisModel.setRiskLikelihoodID((int)
//                    cRowRA.getCell(2, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
//            riskAnalysisModel.setRiskImpactID((int)
//                    cRowRA.getCell(3, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
//
//            if (!addRiskAnalysis(riskAnalysisModel)) {
//                return false;
//            }
//        }
//
//        return true;
//    }
//
//    private boolean addRiskAnalysis(cMilestoneReviewModel riskAnalysisModel) {
//        // open the connection to the database
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//
//        // create content object for storing data
//        ContentValues cv = new ContentValues();
//
//        // assign values to the table fields
//        cv.put(cSQLDBHelper.KEY_ID, riskAnalysisModel.getRiskAnalysisID());
//        cv.put(cSQLDBHelper.KEY_RISK_MILESTONE_FK_ID, riskAnalysisModel.getRiskMilestoneID());
//        cv.put(cSQLDBHelper.KEY_RISK_LIKELIHOOD_FK_ID, riskAnalysisModel.getRiskLikelihoodID());
//        cv.put(cSQLDBHelper.KEY_RISK_IMPACT_FK_ID, riskAnalysisModel.getRiskImpactID());
//
//        // insert record details
//        try {
//            if (db.insert(cSQLDBHelper.TABLE_tblRISKANALYSIS, null, cv) < 0) {
//                return false;
//            }
//
//        } catch (Exception e) {
//            Log.d(TAG, "Exception in importing RISK ANALYSIS from Excel: " + e.getMessage());
//        }
//
//        // close the database connection
//        db.close();
//
//        return true;
//    }
//
//    @Override
//    public boolean deleteRiskAnalysis() {
//        // open the connection to the database
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//
//        // delete all records
//        long result = db.delete(cSQLDBHelper.TABLE_tblRISKANALYSIS, null, null);
//
//        // close the database connection
//        db.close();
//
//        return result > -1;
//    }
//
//
//    /*################################### RISK ACTION FUNCTIONS ##################################*/
//
//    @Override
//    public boolean addRiskActionFromExcel() {
//        Workbook workbook = excelHelper.getWorkbookRAID();
//        Sheet RSheet = workbook.getSheet(cExcelHelper.SHEET_tblRISKACTION);
//        Sheet CCSheet = workbook.getSheet(cExcelHelper.SHEET_tblCURRENTCONTROL);
//        Sheet ACSheet = workbook.getSheet(cExcelHelper.SHEET_tblADDITIONALCONTROL);
//
//        if (RSheet == null) {
//            return false;
//        }
//
//        for (Iterator<Row> ritR = RSheet.iterator(); ritR.hasNext(); ) {
//            Row cRowR = ritR.next();
//
//            //just skip the row if row number is 0
//            if (cRowR.getRowNum() == 0) {
//                continue;
//            }
//
//            cActionModel riskActionModel = new cActionModel();
//
//            riskActionModel.setTaskID((int)
//                    cRowR.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
//            riskActionModel.setRiskPlanID((int)
//                    cRowR.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
//            riskActionModel.setRiskActionTypeID((int)
//                    cRowR.getCell(2, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
//
//            if (!addRiskAction(riskActionModel, CCSheet, ACSheet)) {
//                return false;
//            }
//        }
//
//        return true;
//    }
//
//    private boolean addRiskAction(cActionModel riskActionModel, Sheet CCSheet, Sheet ACSheet) {
//        // open the connection to the database
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//
//        // create content object for storing data
//        ContentValues cv = new ContentValues();
//
//        // assign values to the table fields
//        cv.put(cSQLDBHelper.KEY_TASK_FK_ID, riskActionModel.getTaskID());
//        cv.put(cSQLDBHelper.KEY_RISK_PLAN_FK_ID, riskActionModel.getRiskPlanID());
//        cv.put(cSQLDBHelper.KEY_RISK_ACTION_TYPE_FK_ID, riskActionModel.getRiskActionTypeID());
//
//        // insert record details
//        try {
//            if (db.insert(cSQLDBHelper.TABLE_tblRISKACTION, null, cv) < 0) {
//                return false;
//            }
//
//            /* risk current control */
//            for (Iterator<Row> ritCC = CCSheet.iterator(); ritCC.hasNext(); ) {
//                Row rowCC = ritCC.next();
//
//                //just skip the row if row number is 0
//                if (rowCC.getRowNum() == 0) {
//                    continue;
//                }
//
//                int riskActionID = (int) rowCC.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
//                if (riskActionModel.getTaskID() == riskActionID) {
//                    int riskAnalysisID = (int)
//                            rowCC.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
//                    //int riskActionTypeID = (int)
//                    //       rowCC.getCell(2, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
//
//                    if (!addCurrentControl(riskActionID, riskAnalysisID)) {
//                        return false;
//                    }
//                }
//            }
//
//            /* risk additional control */
//            for (Iterator<Row> ritAC = ACSheet.iterator(); ritAC.hasNext(); ) {
//                Row rowAC = ritAC.next();
//
//                //just skip the row if row number is 0
//                if (rowAC.getRowNum() == 0) {
//                    continue;
//                }
//
//                int riskActionID = (int) rowAC.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
//                if (riskActionModel.getTaskID() == riskActionID) {
//                    int riskAnalysisID = (int)
//                            rowAC.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
//                    //int riskActionTypeID = (int)
//                    //        rowAC.getCell(2, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
//
//                    if (!addAdditionalControl(riskActionID, riskAnalysisID)) {
//                        return false;
//                    }
//                }
//            }
//
//        } catch (Exception e) {
//            Log.d(TAG, "Exception in importing RISK ACTION from Excel: " + e.getMessage());
//        }
//
//        // close the database connection
//        db.close();
//
//        return true;
//    }
}
