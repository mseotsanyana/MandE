package com.me.mseotsanyana.mande.DAL.ìmpl.session;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.me.mseotsanyana.mande.BLL.repository.session.iUploadSessionRepository;
import com.me.mseotsanyana.mande.DAL.model.session.cAddressModel;
import com.me.mseotsanyana.mande.DAL.model.session.cOrganizationModel;
import com.me.mseotsanyana.mande.DAL.model.session.cUserModel;
import com.me.mseotsanyana.mande.DAL.model.session.cValueModel;
import com.me.mseotsanyana.mande.DAL.storage.database.cSQLDBHelper;
import com.me.mseotsanyana.mande.DAL.storage.excel.cExcelHelper;
import com.me.mseotsanyana.mande.UTIL.cConstant;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;

import static com.me.mseotsanyana.mande.DAL.storage.database.cSQLDBHelper.TABLE_tblVALUE;

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
     * @return
     */
    @Override
    public boolean addAddressFromExcel() {
        Workbook workbook = excelHelper.getWorkbookSESSION();
        Sheet ASheet = workbook.getSheet(cExcelHelper.SHEET_tblADDRESS);

        if (ASheet == null) {
            return false;
        }

        for (Iterator<Row> ritA = ASheet.iterator(); ritA.hasNext(); ) {
            Row cRow = ritA.next();

            //just skip the row if row number is 0
            if (cRow.getRowNum() == 0) {
                continue;
            }

            cAddressModel addressModel = new cAddressModel();

            addressModel.setAddressID((int)cRow.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            addressModel.setStreet(cRow.getCell(1, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
            addressModel.setCity(cRow.getCell(2, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
            addressModel.setProvince(cRow.getCell(3, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
            addressModel.setPostalCode(cRow.getCell(4, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
            addressModel.setCountry(cRow.getCell(5, Row.CREATE_NULL_AS_BLANK).getStringCellValue());

            if (!addAddress(addressModel)) {
                return false;
            }
        }

        return true;
    }

    /**
     * This function adds address data to the database.
     *
     * @param addressModel
     * @return
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
        Sheet orgValueSheet = workbook.getSheet("tblVALUE");
        Sheet orgUserSheet = workbook.getSheet("tblUSER");

        for (Iterator<Row> rit = orgSheet.iterator(); rit.hasNext(); ) {
            Row cRow = rit.next();

            //just skip the row if row number is 0
            if (cRow.getRowNum() == 0) {
                continue;
            }

            cOrganizationModel organizationModel = new cOrganizationModel();

            organizationModel.setOrganizationID(
                    (int) cRow.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            organizationModel.setName(
                    cRow.getCell(1, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
            organizationModel.setPhone(
                    cRow.getCell(2, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
            organizationModel.setFax(
                    cRow.getCell(3, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
            organizationModel.setVision(
                    cRow.getCell(4, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
            organizationModel.setMission(
                    cRow.getCell(5, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
            organizationModel.setEmail(
                    cRow.getCell(6, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
            organizationModel.setWebsite(
                    cRow.getCell(7, Row.CREATE_NULL_AS_BLANK).getStringCellValue());

            // add the row into the database
            if (!addOrganization(organizationModel, orgAddressSheet, orgBeneficiarySheet,
                    orgFunderSheet, orgIASheet, orgValueSheet, orgUserSheet)) {
                return false;
            }
        }
        return true;
    }

    // add an organization record from excel to database
    public boolean addOrganization(cOrganizationModel organizationModel, Sheet orgAddressSheet,
                                   Sheet orgBeneficiarySheet, Sheet orgFunderSheet,
                                   Sheet orgIASheet, Sheet orgValueSheet, Sheet orgUserSheet) {


        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_ID, organizationModel.getOrganizationID());
        cv.put(cSQLDBHelper.KEY_NAME, organizationModel.getName());
        cv.put(cSQLDBHelper.KEY_TELEPHONE, organizationModel.getPhone());
        cv.put(cSQLDBHelper.KEY_FAX, organizationModel.getFax());
        cv.put(cSQLDBHelper.KEY_VISION, organizationModel.getVision());
        cv.put(cSQLDBHelper.KEY_MISSION, organizationModel.getMission());
        cv.put(cSQLDBHelper.KEY_EMAIL, organizationModel.getEmail());
        cv.put(cSQLDBHelper.KEY_WEBSITE, organizationModel.getWebsite());

        // insert value record
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblORGANIZATION, null, cv) < 0) {
                return false;
            }

            int organizationID, addressID, beneficiaryID, funderID, agencyID;

            /* Organization Address */
            for (Iterator<Row> rit = orgAddressSheet.iterator(); rit.hasNext(); ) {
                Row cOrgAddressRow = rit.next();

                //just skip the row if row number is 0
                if (cOrgAddressRow.getRowNum() == 0) {
                    continue;
                }

                organizationID = (int) cOrgAddressRow.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                if (organizationModel.getOrganizationID() == organizationID) {
                    addressID = (int) cOrgAddressRow.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                    // add organization addresses
                    if (addOrganizationAddress(organizationModel.getOrganizationID(), addressID))
                        continue;
                    else
                        return false;
                }
            }

            /* Beneficiary Organization */
            for (Iterator<Row> rit = orgBeneficiarySheet.iterator(); rit.hasNext(); ) {
                Row cOrgBeneficiaryRow = rit.next();

                //just skip the row if row number is 0
                if (cOrgBeneficiaryRow.getRowNum() == 0) {
                    continue;
                }

                beneficiaryID = (int) cOrgBeneficiaryRow.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                if (organizationModel.getOrganizationID() == beneficiaryID) {
                    // add beneficiary
                    if (addBeneficiary(beneficiaryID))
                        continue;
                    else
                        return false;
                }
            }

            /* Funder Organization */
            for (Iterator<Row> rit = orgFunderSheet.iterator(); rit.hasNext(); ) {
                Row cOrgFunderRow = rit.next();

                //just skip the row if row number is 0
                if (cOrgFunderRow.getRowNum() == 0) {
                    continue;
                }

                funderID = (int) cOrgFunderRow.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                if (organizationModel.getOrganizationID() == funderID) {
                    // add funders
                    if (addFunder(funderID))
                        continue;
                    else
                        return false;
                }
            }

            /* Implementation Agency Organization */
            for (Iterator<Row> rit = orgIASheet.iterator(); rit.hasNext(); ) {
                Row cOrgAgencyRow = rit.next();

                //just skip the row if row number is 0
                if (cOrgAgencyRow.getRowNum() == 0) {
                    continue;
                }

                agencyID = (int) cOrgAgencyRow.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                if (organizationModel.getOrganizationID() == agencyID) {
                    // add organization addresses
                    if (addAgency(agencyID))
                        continue;
                    else
                        return false;
                }
            }

            /* Values */
            for (Iterator<Row> rit = orgValueSheet.iterator(); rit.hasNext(); ) {
                Row cValueRow = rit.next();

                //just skip the row if row number is 0
                if (cValueRow.getRowNum() == 0) {
                    continue;
                }

                organizationID = (int) cValueRow.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                if (organizationModel.getOrganizationID() == organizationID) {
                    cValueModel valueModel = new cValueModel();

                    valueModel.setValueID((int)cValueRow.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
                    valueModel.setOrganizationID(organizationID);
                    valueModel.setName(cValueRow.getCell(2, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
                    valueModel.setDescription(cValueRow.getCell(3, Row.CREATE_NULL_AS_BLANK).getStringCellValue());


                    // add organization addresses
                    if (addValue(valueModel))
                        continue;
                    else
                        return false;
                }
            }

            /* Users */
            for (Iterator<Row> rit = orgUserSheet.iterator(); rit.hasNext(); ) {
                Row cUserRow = rit.next();

                //just skip the row if row number is 0
                if (cUserRow.getRowNum() == 0) {
                    continue;
                }

                organizationID = (int) cUserRow.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                if (organizationModel.getOrganizationID() == organizationID) {
                    cUserModel userModel = new cUserModel();

                    userModel.setUserID((int)cUserRow.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
                    userModel.setOrganizationID(organizationID);
                    userModel.setName(cUserRow.getCell(2, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
                    userModel.setSurname(cUserRow.getCell(3, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
                    userModel.setGender(cUserRow.getCell(4, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
                    userModel.setDescription(cUserRow.getCell(5, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
                    userModel.setEmail(cUserRow.getCell(6, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
                    userModel.setWebsite(cUserRow.getCell(7, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
                    userModel.setPhone(cUserRow.getCell(8, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
                    userModel.setPassword(cUserRow.getCell(9, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
                    userModel.setSalt(cUserRow.getCell(10, Row.CREATE_NULL_AS_BLANK).getStringCellValue());

                    // add organization addresses
                    if (addUser(userModel))
                        continue;
                    else
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

    public boolean addOrganizationAddress(int organizationID, int addressID) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(cSQLDBHelper.KEY_ORGANIZATION_FK_ID, organizationID);
        cv.put(cSQLDBHelper.KEY_ADDRESS_FK_ID, addressID);

        if (db.insert(cSQLDBHelper.TABLE_tblORG_ADDRESS, null, cv) < 0) {
            return false;
        }

        return true;
    }

    public boolean addBeneficiary(int organizationID) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(cSQLDBHelper.KEY_ORGANIZATION_FK_ID, organizationID);

        if (db.insert(cSQLDBHelper.TABLE_tblBENEFICIARY, null, cv) < 0) {
            return false;
        }

        return true;
    }

    public boolean addFunder(int organizationID) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(cSQLDBHelper.KEY_ORGANIZATION_FK_ID, organizationID);

        if (db.insert(cSQLDBHelper.TABLE_tblFUNDER, null, cv) < 0) {
            return false;
        }

        return true;
    }

    public boolean addAgency(int organizationID) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(cSQLDBHelper.KEY_ORGANIZATION_FK_ID, organizationID);

        if (db.insert(cSQLDBHelper.TABLE_tblIMPLEMENTINGAGENCY, null, cv) < 0) {
            return false;
        }

        return true;
    }

    public boolean addValue(cValueModel valueModel){
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
            if (db.insert(TABLE_tblVALUE, null, cv) < 0) {
                return false;
            }
        } catch (Exception e) {
            Log.d(TAG, "Exception in importing VALUE from Excel: " + e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }

    public boolean addUser(cUserModel userModel){
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

            // add user addresses
            /*for (int address : addresses) {
                if (addUserAddress(userModel.getUserID(), address))
                    continue;
                else
                    return false;
            }*/
        } catch (Exception e) {
            Log.d(TAG, "Exception in importing USER from Excel: " + e.getMessage());
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
        long result = db.delete(TABLE_tblVALUE, null, null);

        // close the database connection
        db.close();

        return result > -1;
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
    public boolean deleteImplementingAgencies() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblIMPLEMENTINGAGENCY, null, null);

        // close the database connection
        db.close();

        return result > -1;
    }

    /* ################################## ORGANIZATION FUNCTIONS #################################*/

}
