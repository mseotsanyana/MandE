package com.me.mseotsanyana.mande.DAL.ìmpl.sqlite.session;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.me.mseotsanyana.mande.BLL.repository.session.iUploadSessionRepository;
import com.me.mseotsanyana.mande.BLL.model.session.cAddressModel;
import com.me.mseotsanyana.mande.BLL.model.session.cEntityModel;
import com.me.mseotsanyana.mande.BLL.model.session.cMenuModel;
import com.me.mseotsanyana.mande.BLL.model.session.cNotificationModel;
import com.me.mseotsanyana.mande.BLL.model.session.cOperationModel;
import com.me.mseotsanyana.mande.BLL.model.session.cOrganizationModel;
import com.me.mseotsanyana.mande.BLL.model.session.cPermissionModel;
import com.me.mseotsanyana.mande.BLL.model.session.cRoleModel;
import com.me.mseotsanyana.mande.BLL.model.session.cSettingModel;
import com.me.mseotsanyana.mande.BLL.model.session.cStatusModel;
import com.me.mseotsanyana.mande.BLL.model.session.cStatusSetModel;
import com.me.mseotsanyana.mande.BLL.model.session.cUserModel;
import com.me.mseotsanyana.mande.BLL.model.session.cValueModel;
import com.me.mseotsanyana.mande.DAL.storage.database.cSQLDBHelper;
import com.me.mseotsanyana.mande.DAL.storage.excel.cExcelHelper;
import com.me.mseotsanyana.mande.UTIL.cConstant;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.text.SimpleDateFormat;

public class cUploadSessionRepositoryImpl implements iUploadSessionRepository {
    private static SimpleDateFormat sdf = cConstant.FORMAT_DATE;
    private static String TAG = cUploadSessionRepositoryImpl.class.getSimpleName();

    /* an object of the database helper */
    private cSQLDBHelper dbHelper;
    /* an object of the excel helper */
    private cExcelHelper excelHelper;

    public cUploadSessionRepositoryImpl(Context context) {
        dbHelper = new cSQLDBHelper(context);
        excelHelper = new cExcelHelper(context);
    }

    /*##################################### ADDRESS FUNCTIONS ####################################*/

    /**
     * This function extracts criteria data from excel and adds it to the database.
     *
     * @return boolean
     */
    @Override
    public boolean addAddressFromExcel() {
        Workbook workbook = excelHelper.getWorkbookSESSION();
        Sheet ASheet = workbook.getSheet(cExcelHelper.SHEET_tblADDRESS);

        if (ASheet == null) {
            return false;
        }

        for (Row cRow : ASheet) {
            //just skip the row if row number is 0
            if (cRow.getRowNum() == 0) {
                continue;
            }

            cAddressModel addressModel = new cAddressModel();

            addressModel.setAddressID((int) cRow.getCell(0,
                    Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            addressModel.setStreet(cRow.getCell(1,
                    Row.CREATE_NULL_AS_BLANK).getStringCellValue());
            addressModel.setCity(cRow.getCell(2,
                    Row.CREATE_NULL_AS_BLANK).getStringCellValue());
            addressModel.setProvince(cRow.getCell(3,
                    Row.CREATE_NULL_AS_BLANK).getStringCellValue());
            addressModel.setPostalCode(cRow.getCell(4,
                    Row.CREATE_NULL_AS_BLANK).getStringCellValue());
            addressModel.setCountry(cRow.getCell(5,
                    Row.CREATE_NULL_AS_BLANK).getStringCellValue());

            if (!addAddress(addressModel)) {
                return false;
            }
        }

        return true;
    }

    /**
     * This function adds address data to the database.
     *
     * @param addressModel address
     * @return boolean
     */
    public boolean addAddress(cAddressModel addressModel) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_ID, addressModel.getAddressID());
        cv.put(cSQLDBHelper.KEY_STREET, addressModel.getStreet());
        cv.put(cSQLDBHelper.KEY_CITY, addressModel.getCity());
        cv.put(cSQLDBHelper.KEY_PROVINCE, addressModel.getProvince());
        cv.put(cSQLDBHelper.KEY_POSTAL_CODE, addressModel.getPostalCode());
        cv.put(cSQLDBHelper.KEY_COUNTRY, addressModel.getCountry());

        // insert project details
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblADDRESS, null, cv) < 0) {
                return false;
            }
        } catch (Exception e) {
            Log.d(TAG, "Exception in importing ADDRESS from Excel: " + e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }

    /* ################################## ORGANIZATION FUNCTIONS #################################*/

    @Override
    public boolean addOrganizationFromExcel() {
        Workbook workbook = excelHelper.getWorkbookSESSION();
        Sheet orgSheet = workbook.getSheet("tblORGANIZATION");
        Sheet orgAddressSheet = workbook.getSheet("tblORG_ADDRESS");
        Sheet orgBeneficiarySheet = workbook.getSheet("tblBENEFICIARY");
        Sheet orgFunderSheet = workbook.getSheet("tblFUNDER");
        Sheet orgIASheet = workbook.getSheet("tblIMPLEMENTINGAGENCY");
        Sheet orgCFSheet = workbook.getSheet("tblCROWDFUND");
        Sheet orgValueSheet = workbook.getSheet("tblVALUE");
        //Sheet orgUserSheet = workbook.getSheet("tblUSER");

        for (Row cRow : orgSheet) {
            //just skip the row if row number is 0
            if (cRow.getRowNum() == 0) {
                continue;
            }

            cOrganizationModel organizationModel = new cOrganizationModel();

            //organizationModel.setOrganizationID(
            //        (int) cRow.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            organizationModel.setName(
                    cRow.getCell(1, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
            //organizationModel.setPhone(
            //        cRow.getCell(2, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
            //organizationModel.setFax(
            //        cRow.getCell(3, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
            //organizationModel.setVision(
            //        cRow.getCell(4, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
            //organizationModel.setMission(
            //        cRow.getCell(5, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
            organizationModel.setEmail(
                    cRow.getCell(6, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
            organizationModel.setWebsite(
                    cRow.getCell(7, Row.CREATE_NULL_AS_BLANK).getStringCellValue());

            // add the row into the database
            if (!addOrganization(organizationModel, orgAddressSheet, orgBeneficiarySheet,
                    orgFunderSheet, orgIASheet, orgCFSheet, orgValueSheet)) {
                return false;
            }
        }
        return true;
    }

    // add an organization record from excel to database
    public boolean addOrganization(cOrganizationModel organizationModel, Sheet orgAddressSheet,
                                   Sheet orgBeneficiarySheet, Sheet orgFunderSheet,
                                   Sheet orgIASheet, Sheet orgCFSheet, Sheet orgValueSheet) {


        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        //cv.put(cSQLDBHelper.KEY_ID, organizationModel.getOrganizationID());
        cv.put(cSQLDBHelper.KEY_NAME, organizationModel.getName());
        //cv.put(cSQLDBHelper.KEY_TELEPHONE, organizationModel.getPhone());
        //cv.put(cSQLDBHelper.KEY_FAX, organizationModel.getFax());
        //cv.put(cSQLDBHelper.KEY_VISION, organizationModel.getVision());
        //cv.put(cSQLDBHelper.KEY_MISSION, organizationModel.getMission());
        cv.put(cSQLDBHelper.KEY_EMAIL, organizationModel.getEmail());
        cv.put(cSQLDBHelper.KEY_WEBSITE, organizationModel.getWebsite());

        // insert value record
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblORGANIZATION, null, cv) < 0) {
                return false;
            }

            long organizationID, addressID, beneficiaryID, funderID, agencyID;

            /* Organization Address */
            for (Row cOrgAddressRow : orgAddressSheet) {
                //just skip the row if row number is 0
                if (cOrgAddressRow.getRowNum() == 0) {
                    continue;
                }

                organizationID = (int) cOrgAddressRow.getCell(0,
                        Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                if (true/*organizationModel.getOrganizationID() == organizationID*/) {
                    addressID = (int) cOrgAddressRow.getCell(1,
                            Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                    // add organization addresses
                    if (!addOrganizationAddress(0/*organizationModel.getOrganizationID()*/, addressID))
                        return false;
                }
            }

            /* Beneficiary Organization */
            for (Row cOrgBeneficiaryRow : orgBeneficiarySheet) {
                //just skip the row if row number is 0
                if (cOrgBeneficiaryRow.getRowNum() == 0) {
                    continue;
                }

                beneficiaryID = (int) cOrgBeneficiaryRow.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                if (true/*organizationModel.getOrganizationID() == beneficiaryID*/) {
                    // add beneficiary
                    if (!addBeneficiary(beneficiaryID))
                        return false;
                }
            }

            /* Funder Organization */
            for (Row cOrgFunderRow : orgFunderSheet) {
                //just skip the row if row number is 0
                if (cOrgFunderRow.getRowNum() == 0) {
                    continue;
                }

                funderID = (int) cOrgFunderRow.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                if (true/*organizationModel.getOrganizationID() == funderID*/) {
                    // add funders
                    if (!addFunder(funderID, orgCFSheet))
                        return false;
                }
            }

            /* Implementation Agency Organization */
            for (Row cOrgAgencyRow : orgIASheet) {
                //just skip the row if row number is 0
                if (cOrgAgencyRow.getRowNum() == 0) {
                    continue;
                }

                agencyID = (int) cOrgAgencyRow.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                if (true/*organizationModel.getOrganizationID() == agencyID*/) {
                    // add organization addresses
                    if (!addAgency(agencyID))
                        return false;
                }
            }

            /* Values */
            for (Row cValueRow : orgValueSheet) {
                //just skip the row if row number is 0
                if (cValueRow.getRowNum() == 0) {
                    continue;
                }

                organizationID = (int) cValueRow.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                if (true/*organizationModel.getOrganizationID() == organizationID*/) {
                    cValueModel valueModel = new cValueModel();

                    valueModel.setValueID((int) cValueRow.getCell(0,
                            Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
                    valueModel.setOrganizationID(organizationID);
                    valueModel.setName(cValueRow.getCell(2,
                            Row.CREATE_NULL_AS_BLANK).getStringCellValue());
                    valueModel.setDescription(cValueRow.getCell(3,
                            Row.CREATE_NULL_AS_BLANK).getStringCellValue());


                    // add organization addresses
                    if (!addValue(valueModel))
                        return false;
                }
            }

        } catch (Exception e) {
            Log.d(TAG, "Exception in importing ORGANIZATION from Excel: " + e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }

    public boolean addOrganizationAddress(long organizationID, long addressID) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(cSQLDBHelper.KEY_ORGANIZATION_FK_ID, organizationID);
        cv.put(cSQLDBHelper.KEY_ADDRESS_FK_ID, addressID);

        return db.insert(cSQLDBHelper.TABLE_tblORG_ADDRESS, null, cv) >= 0;
    }

    public boolean addBeneficiary(long organizationID) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(cSQLDBHelper.KEY_ORGANIZATION_FK_ID, organizationID);

        return db.insert(cSQLDBHelper.TABLE_tblBENEFICIARY, null, cv) >= 0;
    }

    public boolean addFunder(long organizationID, Sheet orgCFSheet) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(cSQLDBHelper.KEY_ORGANIZATION_FK_ID, organizationID);

        if(db.insert(cSQLDBHelper.TABLE_tblFUNDER, null, cv) >= 0){
            /* Funder Organization */
            long cfID, logFrameID, funderID, beneficiaryID; double amount; String description;
            for (Row cOrgCFRow : orgCFSheet) {
                //just skip the row if row number is 0
                if (cOrgCFRow.getRowNum() == 0) {
                    continue;
                }

                funderID = (int) cOrgCFRow.getCell(2,
                        Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                if (organizationID == funderID) {
                    cfID = (int) cOrgCFRow.getCell(0,
                            Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                    logFrameID = (int) cOrgCFRow.getCell(1,
                            Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                    beneficiaryID = (int) cOrgCFRow.getCell(3,
                            Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                    amount = (int) cOrgCFRow.getCell(4,
                            Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                    description = cOrgCFRow.getCell(5,
                            Row.CREATE_NULL_AS_BLANK).getStringCellValue();

                    if (!addCrowdFund(cfID, logFrameID, funderID, beneficiaryID, amount,
                            description))
                        return false;
                }
            }
        }

        return db.insert(cSQLDBHelper.TABLE_tblFUNDER, null, cv) >= 0;
    }

    public boolean addAgency(long organizationID) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(cSQLDBHelper.KEY_ORGANIZATION_FK_ID, organizationID);

        return db.insert(cSQLDBHelper.TABLE_tblIMPLEMENTINGAGENCY, null, cv) >= 0;
    }

    public boolean addCrowdFund(long cfID, long logFrameID, long funderID, long beneficiaryID,
                                double amount, String description) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_ID, cfID);
        cv.put(cSQLDBHelper.KEY_LOGFRAME_FK_ID, logFrameID);
        cv.put(cSQLDBHelper.KEY_FUNDER_FK_ID, funderID);
        cv.put(cSQLDBHelper.KEY_BENEFICIARY_FK_ID, beneficiaryID);
        cv.put(cSQLDBHelper.KEY_FUND, amount);
        cv.put(cSQLDBHelper.KEY_DESCRIPTION, description);

        // insert value record
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblCROWDFUND, null, cv) < 0) {
                return false;
            }
        } catch (Exception e) {
            Log.d(TAG, "Exception in importing CROWD FUND from Excel: " + e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }

    public boolean addValue(cValueModel valueModel) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_ID, valueModel.getValueID());
        cv.put(cSQLDBHelper.KEY_ORGANIZATION_FK_ID, valueModel.getOrganizationID());
        cv.put(cSQLDBHelper.KEY_NAME, valueModel.getName());
        cv.put(cSQLDBHelper.KEY_DESCRIPTION, valueModel.getDescription());

        // insert value record
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblVALUE, null, cv) < 0) {
                return false;
            }
        } catch (Exception e) {
            Log.d(TAG, "Exception in importing VALUE from Excel: " + e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }

    @Override
    public boolean deleteAddresses() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblADDRESS, null, null);

        // close the database connection
        db.close();

        return result > -1;
    }

    @Override
    public boolean deleteValues() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblVALUE, null, null);

        // close the database connection
        db.close();

        return result > -1;
    }

    @Override
    public boolean deleteOrganizations() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblORGANIZATION, null, null);

        // close the database connection
        db.close();

        return result > -1;
    }

    @Override
    public boolean deleteOrgAddresses() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblORG_ADDRESS, null, null);

        // close the database connection
        db.close();

        return result > -1;
    }

    @Override
    public boolean deleteBeneficiaries() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblBENEFICIARY, null, null);

        // close the database connection
        db.close();

        return result > -1;
    }

    @Override
    public boolean deleteFunders() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblFUNDER, null, null);

        // close the database connection
        db.close();

        return result > -1;
    }

    @Override
    public boolean deleteCrowdFunds() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblCROWDFUND, null, null);

        // close the database connection
        db.close();

        return result > -1;
    }

    @Override
    public boolean deleteImplementingAgencies() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblIMPLEMENTINGAGENCY, null, null);

        // close the database connection
        db.close();

        return result > -1;
    }

    /* ###################################### USER FUNCTIONS #####################################*/

    @Override
    public boolean addUserFromExcel() {
        Workbook workbook = excelHelper.getWorkbookSESSION();
        Sheet USheet = workbook.getSheet(cExcelHelper.SHEET_tblUSERPROFILE);
        Sheet UASheet = workbook.getSheet(cExcelHelper.SHEET_tblUSER_ADDRESS);

        if (USheet == null) {
            return false;
        }

        for (Row cRow : USheet) {
            //just skip the row if row number is 0
            if (cRow.getRowNum() == 0) {
                continue;
            }

            cUserModel userModel = new cUserModel();

            userModel.setUserID((int) cRow.getCell(0,
                    Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            userModel.setOrganizationID((int) cRow.getCell(1,
                    Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            userModel.setName(cRow.getCell(2, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
            userModel.setSurname(cRow.getCell(3, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
            userModel.setGender(cRow.getCell(4, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
            userModel.setDescription(cRow.getCell(5, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
            userModel.setEmail(cRow.getCell(6, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
            userModel.setWebsite(cRow.getCell(7, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
            userModel.setPhone(cRow.getCell(8, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
            userModel.setPassword(cRow.getCell(9, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
            userModel.setSalt(cRow.getCell(10, Row.CREATE_NULL_AS_BLANK).getStringCellValue());

            // add organization addresses
            if (!addUser(userModel, UASheet))
                return false;
        }

        return true;
    }

    public boolean addUser(cUserModel userModel, Sheet UASheet) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_ID, userModel.getUserID());
        cv.put(cSQLDBHelper.KEY_ORGANIZATION_FK_ID, userModel.getOrganizationID());
        cv.put(cSQLDBHelper.KEY_NAME, userModel.getName());
        cv.put(cSQLDBHelper.KEY_SURNAME, userModel.getSurname());
        cv.put(cSQLDBHelper.KEY_GENDER, userModel.getGender());
        cv.put(cSQLDBHelper.KEY_DESCRIPTION, userModel.getDescription());
        cv.put(cSQLDBHelper.KEY_EMAIL, userModel.getEmail());
        cv.put(cSQLDBHelper.KEY_WEBSITE, userModel.getWebsite());
        cv.put(cSQLDBHelper.KEY_PHONE, userModel.getPhone());
        cv.put(cSQLDBHelper.KEY_PASSWORD, userModel.getPassword());
        cv.put(cSQLDBHelper.KEY_SALT, userModel.getSalt());

        // insert outcome record
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblUSER, null, cv) < 0) {
                return false;
            }

            /* User Address */
            for (Row cRow : UASheet) {
                //just skip the row if row number is 0
                if (cRow.getRowNum() == 0) {
                    continue;
                }

                int userID = (int) cRow.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                if (userModel.getUserID() == userID) {
                    int addressID = (int) cRow.getCell(1,
                            Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                    // add organization addresses
                    if (!addUserAddress(userID, addressID))
                        return false;
                }
            }

        } catch (Exception e) {
            Log.d(TAG, "Exception in importing USER from Excel: " + e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }

    public boolean addUserAddress(int userID, int addressID) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(cSQLDBHelper.KEY_USER_FK_ID, userID);
        cv.put(cSQLDBHelper.KEY_ADDRESS_FK_ID, addressID);
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblUSER_ADDRESS, null, cv) < 0) {
                return false;
            }

        } catch (Exception e) {
            Log.d(TAG, "Exception in importing USER ADDRESS from Excel: " + e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }

    @Override
    public boolean deleteUsers() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblUSER, null, null);

        // close the database connection
        db.close();

        return result > -1;
    }

    @Override
    public boolean deleteUserAddresses() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblUSER_ADDRESS, null, null);

        // close the database connection
        db.close();

        return result > -1;
    }

    /* ###################################### MENU FUNCTIONS #####################################*/

    @Override
    public boolean addMenuFromExcel() {
        Workbook workbook = excelHelper.getWorkbookSESSION();
        Sheet MSheet = workbook.getSheet(cExcelHelper.SHEET_tblMENU);

        if (MSheet == null) {
            return false;
        }

        for (Row cRow : MSheet) {
            //just skip the row if row number is 0
            if (cRow.getRowNum() == 0) {
                continue;
            }

            cMenuModel menuModel = new cMenuModel();

            menuModel.setMenuServerID((int) cRow.getCell(0,
                    Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            menuModel.setParentServerID((int) cRow.getCell(1,
                    Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            menuModel.setName(cRow.getCell(2,
                    Row.CREATE_NULL_AS_BLANK).getStringCellValue());
//            menuModel.setDescription(cRow.getCell(3,
//                    Row.CREATE_NULL_AS_BLANK).getStringCellValue());

            if (!addMenu(menuModel)) {
                return false;
            }
        }

        return true;
    }

    private boolean addMenu(cMenuModel menuModel) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_ID, menuModel.getMenuServerID());
        cv.put(cSQLDBHelper.KEY_PARENT_FK_ID, menuModel.getParentServerID());
        cv.put(cSQLDBHelper.KEY_NAME, menuModel.getName());
//        cv.put(cSQLDBHelper.KEY_DESCRIPTION, menuModel.getDescription());

        // insert outcome record
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblMENU, null, cv) < 0) {
                return false;
            }
        } catch (Exception e) {
            Log.d(TAG, "Exception in importing MENU from Excel: " + e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }

    @Override
    public boolean deleteMenuItems() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblMENU, null, null);

        // close the database connection
        db.close();

        return result > -1;
    }

    /* #################################### ENTITY FUNCTIONS #####################################*/

    @Override
    public boolean addEntityFromExcel() {
        Workbook workbook = excelHelper.getWorkbookSESSION();
        Sheet ESheet = workbook.getSheet(cExcelHelper.SHEET_tblENTITY);

        if (ESheet == null) {
            return false;
        }

        for (Row cRow : ESheet) {
            //just skip the row if row number is 0
            if (cRow.getRowNum() == 0) {
                continue;
            }

            cEntityModel entityModel = null;//new cEntityModel(entityID, moduleID, operationStatusMap, unixPermList);

//            entityModel.setEntityID((int) cRow.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
//            entityModel.setModuleID((int) cRow.getCell(1,
//                    Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
//            entityModel.setName(cRow.getCell(2,
//                    Row.CREATE_NULL_AS_BLANK).getStringCellValue());
//            entityModel.setDescription(cRow.getCell(3,
//                    Row.CREATE_NULL_AS_BLANK).getStringCellValue());


            if (!addEntity(entityModel)) {
                return false;
            }
        }

        return true;
    }

    public boolean addEntity(cEntityModel entityModel) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_ID, entityModel.getEntityServerID());
//        cv.put(cSQLDBHelper.KEY_ENTITY_TYPE_ID, entityModel.getModuleID());
//        cv.put(cSQLDBHelper.KEY_NAME, entityModel.getName());
//        cv.put(cSQLDBHelper.KEY_DESCRIPTION, entityModel.getDescription());

        // insert outcome record
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblENTITY, null, cv) < 0) {
                return false;
            }
        } catch (Exception e) {
            Log.d(TAG, "Exception in importing ENTITY from Excel: " + e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }

    @Override
    public boolean deleteEntities() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblENTITY, null, null);

        // close the database connection
        db.close();

        return result > -1;
    }

    /* ################################### OPERATION FUNCTIONS ###################################*/

    @Override
    public boolean addOperationFromExcel() {
        Workbook workbook = excelHelper.getWorkbookSESSION();
        Sheet OSheet = workbook.getSheet(cExcelHelper.SHEET_tblOPERATION);

        if (OSheet == null) {
            return false;
        }

        for (Row cRow : OSheet) {
            //just skip the row if row number is 0
            if (cRow.getRowNum() == 0) {
                continue;
            }

            cOperationModel operationModel = new cOperationModel();

//            operationModel.setOperationID((int) cRow.getCell(0,
//                    Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            operationModel.setName(cRow.getCell(1,
                    Row.CREATE_NULL_AS_BLANK).getStringCellValue());
            operationModel.setDescription(cRow.getCell(2,
                    Row.CREATE_NULL_AS_BLANK).getStringCellValue());

            if (!addOperation(operationModel)) {
                return false;
            }
        }

        return true;
    }

    public boolean addOperation(cOperationModel operationModel) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
//        cv.put(cSQLDBHelper.KEY_ID, operationModel.getOperationID());
        cv.put(cSQLDBHelper.KEY_NAME, operationModel.getName());
        cv.put(cSQLDBHelper.KEY_DESCRIPTION, operationModel.getDescription());

        // insert outcome record
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblOPERATION, null, cv) < 0) {
                return false;
            }
        } catch (Exception e) {
            Log.d(TAG, "Exception in importing OPERATION from Excel: " + e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }


    @Override
    public boolean deleteOperations() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblOPERATION, null, null);

        // close the database connection
        db.close();

        return result > -1;
    }

    /* #################################### STATUS FUNCTIONS #####################################*/

    @Override
    public boolean addStatusFromExcel() {
        Workbook workbook = excelHelper.getWorkbookSESSION();
        Sheet SSheet = workbook.getSheet(cExcelHelper.SHEET_tblSTATUS);

        if (SSheet == null) {
            return false;
        }

        for (Row cRow : SSheet) {
            //just skip the row if row number is 0
            if (cRow.getRowNum() == 0) {
                continue;
            }

            cStatusModel statusModel = new cStatusModel();

            //statusModel.setStatusServerID((int) cRow.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            statusModel.setName(cRow.getCell(1,
                    Row.CREATE_NULL_AS_BLANK).getStringCellValue());
            statusModel.setDescription(cRow.getCell(2,
                    Row.CREATE_NULL_AS_BLANK).getStringCellValue());

            if (!addStatus(statusModel)) {
                return false;
            }
        }

        return true;
    }

    public boolean addStatus(cStatusModel statusModel) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_ID, statusModel.getStatusServerID());
        cv.put(cSQLDBHelper.KEY_NAME, statusModel.getName());
        cv.put(cSQLDBHelper.KEY_DESCRIPTION, statusModel.getDescription());

        // insert outcome record
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblSTATUS, null, cv) < 0) {
                return false;
            }
        } catch (Exception e) {
            Log.d(TAG, "Exception in importing STATUS from Excel: " + e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }

    @Override
    public boolean deleteStatuses() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblSTATUS, null, null);

        // close the database connection
        db.close();

        return result > -1;
    }

    /*#################################### STATUSSET FUNCTIONS ###################################*/

    @Override
    public boolean addStatusSetFromExcel() {
        Workbook workbook = excelHelper.getWorkbookSESSION();
        Sheet SSheet = workbook.getSheet(cExcelHelper.SHEET_tblSTATUSSET);
        Sheet SSSheet = workbook.getSheet(cExcelHelper.SHEET_tblSTATUSSET_STATUS);

        if (SSheet == null) {
            return false;
        }

        for (Row cRow : SSheet) {
            //just skip the row if row number is 0
            if (cRow.getRowNum() == 0) {
                continue;
            }

            cStatusSetModel statusSetModel = new cStatusSetModel();

            statusSetModel.setStatusSetID((int)
                    cRow.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            statusSetModel.setName(
                    cRow.getCell(1, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
            statusSetModel.setDescription(
                    cRow.getCell(2, Row.CREATE_NULL_AS_BLANK).getStringCellValue());

            if (!addStatusSet(statusSetModel, SSSheet)) {
                return false;
            }
        }

        return true;
    }

    public boolean addStatusSet(cStatusSetModel statusSetModel, Sheet SSSheet) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_ID, statusSetModel.getStatusSetID());
        cv.put(cSQLDBHelper.KEY_NAME, statusSetModel.getName());
        cv.put(cSQLDBHelper.KEY_DESCRIPTION, statusSetModel.getDescription());

        // insert outcome record
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblSTATUSSET, null, cv) < 0) {
                return false;
            }

            // add statusset status
            for (Row cRow : SSSheet) {
                //just skip the row if row number is 0
                if (cRow.getRowNum() == 0) {
                    continue;
                }

                int statusSetID = (int) cRow.getCell(0,
                        Row.CREATE_NULL_AS_BLANK).getNumericCellValue();

                if (statusSetModel.getStatusSetID() == statusSetID) {

                    int statusID = (int) cRow.getCell(1,
                            Row.CREATE_NULL_AS_BLANK).getNumericCellValue();

                    // add organization addresses
                    if (!addStatusSetStatus(statusSetID, statusID))
                        return false;
                }
            }
        } catch (Exception e) {
            Log.d(TAG, "Exception in importing STATUS SET from Excel: " + e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }

    public boolean addStatusSetStatus(int statusSetID, int statusID) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(cSQLDBHelper.KEY_STATUSSET_FK_ID, statusSetID);
        cv.put(cSQLDBHelper.KEY_STATUS_FK_ID, statusID);
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblSTATUSSET_STATUS, null, cv) < 0) {
                return false;
            }

        } catch (Exception e) {
            Log.d(TAG, "Exception in importing STATUSSET STATUS from Excel: " + e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }

    @Override
    public boolean deleteStatusSets() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblSTATUSSET, null, null);

        // close the database connection
        db.close();

        return result > -1;
    }

    @Override
    public boolean deleteStatusSetStatus() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblSTATUSSET_STATUS, null, null);

        // close the database connection
        db.close();

        return result > -1;
    }

    /*#################################### PERMISSION FUNCTIONS ##################################*/

    @Override
    public boolean addPermissionFromExcel() {
        Workbook workbook = excelHelper.getWorkbookSESSION();
        Sheet PSheet = workbook.getSheet(cExcelHelper.SHEET_tblPERMISSION);

        if (PSheet == null) {
            return false;
        }

        for (Row cRow : PSheet) {
            //just skip the row if row number is 0
            if (cRow.getRowNum() == 0) {
                continue;
            }

            cPermissionModel permissionModel = null;//new cPermissionModel(permissions);

//            permissionModel.setPermissionID((int)
//                    cRow.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
//            permissionModel.setEntityID((int)
//                    cRow.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
//            permissionModel.setEntityTypeID((int)
//                    cRow.getCell(2, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
//            permissionModel.setOperationID((int)
//                    cRow.getCell(3, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
//            permissionModel.setStatusSetID((int)
//                    cRow.getCell(4, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());

            if (!addPermission(permissionModel)) {
                return false;
            }
        }

        return true;
    }

    private boolean addPermission(cPermissionModel permissionModel) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
//        cv.put(cSQLDBHelper.KEY_ID, permissionModel.getPermissionID());
//        cv.put(cSQLDBHelper.KEY_ENTITY_FK_ID, permissionModel.getEntityID());
//        cv.put(cSQLDBHelper.KEY_ENTITY_TYPE_FK_ID, permissionModel.getEntityTypeID());
//        cv.put(cSQLDBHelper.KEY_OPERATION_FK_ID, permissionModel.getOperationID());
//        cv.put(cSQLDBHelper.KEY_STATUSSET_FK_ID, permissionModel.getStatusSetID());

        // insert a record
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblPERMISSION, null, cv) < 0) {
                return false;
            }
        } catch (Exception e) {
            Log.d(TAG, "Exception in importing PERMISSION from Excel: " + e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }

    @Override
    public boolean deletePermissions() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblPERMISSION, null, null);

        // close the database connection
        db.close();

        return result > -1;
    }

    /*####################################### ROLE FUNCTIONS #####################################*/

    @Override
    public boolean addRoleFromExcel() {
        Workbook workbook = excelHelper.getWorkbookSESSION();
        Sheet RSheet = workbook.getSheet(cExcelHelper.SHEET_tblROLE);
        Sheet URSheet = workbook.getSheet(cExcelHelper.SHEET_tblUSER_ROLE);
        Sheet MRSheet = workbook.getSheet(cExcelHelper.SHEET_tblMENU_ROLE);
        Sheet PSheet = workbook.getSheet(cExcelHelper.SHEET_tblPRIVILEGE);

        if (RSheet == null) {
            return false;
        }

        for (Row cRow : RSheet) {
            //just skip the row if row number is 0
            if (cRow.getRowNum() == 0) {
                continue;
            }

            cRoleModel roleModel = new cRoleModel();

            /*roleModel.setRoleID((int) cRow.getCell(0,
                    Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            roleModel.setOrganizationID((int) cRow.getCell(1,
                    Row.CREATE_NULL_AS_BLANK).getNumericCellValue());*/
            roleModel.setName(cRow.getCell(2,
                    Row.CREATE_NULL_AS_BLANK).getStringCellValue());
            roleModel.setDescription(cRow.getCell(3,
                    Row.CREATE_NULL_AS_BLANK).getStringCellValue());

            if (!addRole(roleModel, URSheet, MRSheet, PSheet)) {
                return false;
            }
        }

        return true;
    }

    private boolean addRole(cRoleModel roleModel, Sheet URSheet, Sheet MRSheet, Sheet PSheet) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        //cv.put(cSQLDBHelper.KEY_ID, roleModel.getRoleID());
        //cv.put(cSQLDBHelper.KEY_ORGANIZATION_FK_ID, roleModel.getOrganizationID());
        cv.put(cSQLDBHelper.KEY_NAME, roleModel.getName());
        cv.put(cSQLDBHelper.KEY_DESCRIPTION, roleModel.getDescription());

        // insert outcome record
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblROLE, null, cv) < 0) {
                return false;
            }

            // add user roles
            for (Row cRow : URSheet) {
                //just skip the row if row number is 0
                if (cRow.getRowNum() == 0) {
                    continue;
                }

                int organizationID = (int) cRow.getCell(1,
                        Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                int roleID = (int) cRow.getCell(2,
                        Row.CREATE_NULL_AS_BLANK).getNumericCellValue();

                /*if (roleModel.getOrganizationID() == organizationID &&
                        roleModel.getRoleID() == roleID) {

                    int userID = (int) cRow.getCell(0,
                            Row.CREATE_NULL_AS_BLANK).getNumericCellValue();

                    // add organization addresses
                    if (!addUserRole(userID, organizationID, roleID))
                        return false;
                }*/
            }

            // add menu roles
            for (Row cRow : MRSheet) {
                //just skip the row if row number is 0
                if (cRow.getRowNum() == 0) {
                    continue;
                }

                int roleID = (int) cRow.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                int organizationID = (int) cRow.getCell(2, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();

                /*if (roleModel.getRoleID() == roleID && roleModel.getOrganizationID() == organizationID) {

                    int menuID = (int) cRow.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();

                    // add organization addresses
                    if (!addMenuRole(menuID, roleID, organizationID))
                        return false;
                }*/
            }

            // add privilege
            //Log.d(TAG,"PRIV-SIZE = "+PSheet.getPhysicalNumberOfRows());
            for (Row cRow : PSheet) {
                //just skip the row if row number is 0
                if (cRow.getRowNum() == 0) {
                    continue;
                }

                int organizationID = (int) cRow.getCell(1,
                        Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                int roleID = (int) cRow.getCell(2, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                //Log.d(TAG,"ORG = "+organizationID+", ROLE = "+roleID);

                /*if (roleModel.getOrganizationID() == organizationID &&
                        roleModel.getRoleID() == roleID) {

                    //Log.d(TAG,"PRIV-2");
                    int permissionID = (int) cRow.getCell(0,
                            Row.CREATE_NULL_AS_BLANK).getNumericCellValue();

                    // add organization addresses
                    if (!addPrivilege(permissionID, organizationID, roleID))
                        return false;
                }*/
            }

        } catch (Exception e) {
            Log.d(TAG, "Exception in importing ROLE from Excel: " + e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }

    public boolean addUserRole(int userID, int organizationID, int roleID) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(cSQLDBHelper.KEY_USER_FK_ID, userID);
        cv.put(cSQLDBHelper.KEY_ORGANIZATION_FK_ID, organizationID);
        cv.put(cSQLDBHelper.KEY_ROLE_FK_ID, roleID);
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblUSER_ROLE, null, cv) < 0) {
                return false;
            }
        } catch (Exception e) {
            Log.d(TAG, "Exception in importing USER ROLE from Excel: " + e.getMessage());
        }

        // close the database connection
        db.close();
        return true;
    }

    public boolean addMenuRole(int menuID, int roleID, int organizationID) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(cSQLDBHelper.KEY_MENU_FK_ID, menuID);
        cv.put(cSQLDBHelper.KEY_ROLE_FK_ID, roleID);
        cv.put(cSQLDBHelper.KEY_ORGANIZATION_FK_ID, organizationID);

        try {
            if (db.insert(cSQLDBHelper.TABLE_tblMENU_ROLE, null, cv) < 0) {
                return false;
            }
        } catch (Exception e) {
            Log.d(TAG, "Exception in importing MENU ROLE from Excel: " + e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }

    public boolean addPrivilege(int permissionID, int organizationID, int roleID) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(cSQLDBHelper.KEY_PERMISSION_FK_ID, permissionID);
        cv.put(cSQLDBHelper.KEY_ORGANIZATION_FK_ID, organizationID);
        cv.put(cSQLDBHelper.KEY_ROLE_FK_ID, roleID);
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblPRIVILEGE, null, cv) < 0) {
                return false;
            }

        } catch (Exception e) {
            Log.d(TAG, "Exception in importing PRIVILEGE from Excel: " + e.getMessage());
        }

        // close the database connection
        db.close();
        return true;
    }

    @Override
    public boolean deleteRoles() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblROLE, null, null);

        // close the database connection
        db.close();

        return result > -1;
    }

    @Override
    public boolean deleteUserRoles() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblUSER_ROLE, null, null);

        // close the database connection
        db.close();

        return result > -1;
    }

    @Override
    public boolean deleteMenuRoles() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblMENU_ROLE, null, null);

        // close the database connection
        db.close();

        return result > -1;
    }

    @Override
    public boolean deletePrivileges() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblPRIVILEGE, null, null);

        // close the database connection
        db.close();

        return result > -1;
    }

    /* ##################################### SETTING FUNCTIONS ###################################*/

    @Override
    public boolean addSettingFromExcel() {
        Workbook workbook = excelHelper.getWorkbookSESSION();
        Sheet SSheet = workbook.getSheet(cExcelHelper.SHEET_tblSETTING);

        if (SSheet == null) {
            return false;
        }

        for (Row cRow : SSheet) {
            //just skip the row if row number is 0
            if (cRow.getRowNum() == 0) {
                continue;
            }

            cSettingModel settingModel = new cSettingModel();

            settingModel.setSettingID((int) cRow.getCell(0,
                    Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            settingModel.setName(cRow.getCell(1, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
            settingModel.setDescription(cRow.getCell(2,
                    Row.CREATE_NULL_AS_BLANK).getStringCellValue());
            settingModel.setSettingValue((int) cRow.getCell(3,
                    Row.CREATE_NULL_AS_BLANK).getNumericCellValue());

            if (!addSetting(settingModel)) {
                return false;
            }
        }

        return true;
    }

    public boolean addSetting(cSettingModel settingModel) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_ID, settingModel.getSettingID());
        cv.put(cSQLDBHelper.KEY_NAME, settingModel.getName());
        cv.put(cSQLDBHelper.KEY_DESCRIPTION, settingModel.getDescription());
        cv.put(cSQLDBHelper.KEY_SETTING_VALUE, settingModel.getSettingValue());

        // insert a record
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblSETTING, null, cv) < 0) {
                return false;
            }
        } catch (Exception e) {
            Log.d(TAG, "Exception in importing SETTING from Excel: " + e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }

    @Override
    public boolean deleteSettings() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblSETTING, null, null);

        // close the database connection
        db.close();

        return result > -1;
    }

    /* ################################### NOTIFICATION FUNCTIONS ################################*/

    @Override
    public boolean addNotificationFromExcel() {
        Workbook workbook = excelHelper.getWorkbookSESSION();
        Sheet NSheet = workbook.getSheet(cExcelHelper.SHEET_tblNOTIFICATION);
        Sheet PSheet = workbook.getSheet(cExcelHelper.SHEET_tblPUBLISHER);
        Sheet SSheet = workbook.getSheet(cExcelHelper.SHEET_tblSUBSCRIBER);
        Sheet NSSheet = workbook.getSheet(cExcelHelper.SHEET_tblNOTIFY_SETTING);

        if (SSheet == null) {
            return false;
        }

        for (Row cRow : NSheet) {
            //just skip the row if row number is 0
            if (cRow.getRowNum() == 0) {
                continue;
            }

            cNotificationModel notificationModel = new cNotificationModel();

            notificationModel.setNotificationID((int) cRow.getCell(0,
                    Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            notificationModel.setEntityID((int) cRow.getCell(1,
                    Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            notificationModel.setEntityTypeID((int) cRow.getCell(2,
                    Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            notificationModel.setOperationID((int) cRow.getCell(3,
                    Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            notificationModel.setName(cRow.getCell(4,
                    Row.CREATE_NULL_AS_BLANK).getStringCellValue());
            notificationModel.setDescription(cRow.getCell(5,
                    Row.CREATE_NULL_AS_BLANK).getStringCellValue());

            if (!addNotification(notificationModel, PSheet, SSheet, NSSheet)) {
                return false;
            }
        }

        return true;
    }

    private boolean addNotification(cNotificationModel notificationModel,
                                    Sheet PSheet, Sheet SSheet, Sheet NSSheet) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_ID, notificationModel.getNotificationID());
        cv.put(cSQLDBHelper.KEY_ENTITY_FK_ID, notificationModel.getEntityID());
        cv.put(cSQLDBHelper.KEY_ENTITY_TYPE_FK_ID, notificationModel.getEntityTypeID());
        cv.put(cSQLDBHelper.KEY_OPERATION_FK_ID, notificationModel.getOperationID());
        cv.put(cSQLDBHelper.KEY_NAME, notificationModel.getName());
        cv.put(cSQLDBHelper.KEY_DESCRIPTION, notificationModel.getDescription());

        // insert a record
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblNOTIFICATION, null, cv) < 0) {
                return false;
            }

            // add notification publishers
            for (Row cRow : PSheet) {
                //just skip the row if row number is 0
                if (cRow.getRowNum() == 0) {
                    continue;
                }

                int notificationID = (int) cRow.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                if (notificationModel.getNotificationID() == notificationID) {

                    int userID = (int) cRow.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();

                    // add organization addresses
                    if (!addPublisher(userID, notificationID))
                        return false;
                }
            }

            // add notification subscribers
            for (Row cRow : SSheet) {
                //just skip the row if row number is 0
                if (cRow.getRowNum() == 0) {
                    continue;
                }

                int notificationID = (int) cRow.getCell(1,
                        Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                if (notificationModel.getNotificationID() == notificationID) {

                    int userID = (int) cRow.getCell(0,
                            Row.CREATE_NULL_AS_BLANK).getNumericCellValue();

                    // add organization addresses
                    if (!addSubscriber(userID, notificationID))
                        return false;
                }
            }

            // add notification settings
            for (Row cRow : NSSheet) {
                //just skip the row if row number is 0
                if (cRow.getRowNum() == 0) {
                    continue;
                }

                int notificationID = (int) cRow.getCell(0,
                        Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                if (notificationModel.getNotificationID() == notificationID) {
                    int settingID = (int) cRow.getCell(1,
                            Row.CREATE_NULL_AS_BLANK).getNumericCellValue();

                    // add organization addresses
                    if (!addNotificationSetting(notificationID, settingID))
                        return false;
                }
            }

        } catch (Exception e) {
            Log.d(TAG, "Exception in importing NOTIFICATION from Excel: " + e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }

    public boolean addPublisher(int publisherID, int notificationID) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(cSQLDBHelper.KEY_PUBLISHER_FK_ID, publisherID);
        cv.put(cSQLDBHelper.KEY_NOTIFICATION_FK_ID, notificationID);

        try {

            if (db.insert(cSQLDBHelper.TABLE_tblPUBLISHER, null, cv) < 0) {
                return false;
            }

        } catch (Exception e) {
            Log.d(TAG, "Exception in importing PUBLISHER from Excel: " + e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }

    public boolean addSubscriber(int subscriberID, int notificationID) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(cSQLDBHelper.KEY_SUBSCRIBER_FK_ID, subscriberID);
        cv.put(cSQLDBHelper.KEY_NOTIFICATION_FK_ID, notificationID);

        try {

            if (db.insert(cSQLDBHelper.TABLE_tblSUBSCRIBER, null, cv) < 0) {
                return false;
            }

        } catch (Exception e) {
            Log.d(TAG, "Exception in importing SUBSCRIBER from Excel: " + e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }

    public boolean addNotificationSetting(int notificationID, int settingID) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(cSQLDBHelper.KEY_NOTIFICATION_FK_ID, notificationID);
        cv.put(cSQLDBHelper.KEY_SETTING_FK_ID, settingID);

        try {

            if (db.insert(cSQLDBHelper.TABLE_tblNOTIFY_SETTING, null, cv) < 0) {
                return false;
            }

        } catch (Exception e) {
            Log.d(TAG, "Exception in importing NOTIFICATION SETTINGS from Excel: " + e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }

    @Override
    public boolean deleteNotifications() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblNOTIFICATION, null, null);

        // close the database connection
        db.close();

        return result > -1;
    }

    @Override
    public boolean deletePublishers() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblPUBLISHER, null, null);

        // close the database connection
        db.close();

        return result > -1;
    }

    @Override
    public boolean deleteSubscribers() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblSUBSCRIBER, null, null);

        // close the database connection
        db.close();

        return result > -1;
    }

    @Override
    public boolean deleteNotifySettings() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblNOTIFY_SETTING, null, null);

        // close the database connection
        db.close();

        return result > -1;
    }
}
