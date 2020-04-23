package com.me.mseotsanyana.mande.DAL.ìmpl.session;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.me.mseotsanyana.mande.DAL.model.session.cAddressModel;
import com.me.mseotsanyana.mande.DAL.model.session.cOrganizationModel;
import com.me.mseotsanyana.mande.DAL.model.session.cRoleModel;
import com.me.mseotsanyana.mande.DAL.model.session.cUserModel;
import com.me.mseotsanyana.mande.DAL.model.session.cValueModel;
import com.me.mseotsanyana.mande.DAL.storage.database.cSQLDBHelper;
import com.me.mseotsanyana.mande.UTIL.cConstant;
import com.me.mseotsanyana.treeadapterlibrary.cTreeModel;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by mseotsanyana on 2017/05/25.
 */

public class cOrganizationRepositoryImpl {
    private static SimpleDateFormat sdf = cConstant.FORMAT_DATE;
    private static String TAG = cOrganizationRepositoryImpl.class.getSimpleName();

    // an object of the database helper
    private cSQLDBHelper dbHelper;

    public cOrganizationRepositoryImpl(Context context) {
        dbHelper = new cSQLDBHelper(context);
    }

    /* ############################################# CREATE ACTIONS ############################################# */

    /**
     * Add organization data from an excel file
     * @param organizationModel
     * @return Boolean
     */
    public boolean addOrganizationFromExcel(cOrganizationModel organizationModel, ArrayList<Integer> addresses,
                                            ArrayList<Integer> beneficiaries, ArrayList<Integer> funders,
                                            ArrayList<Integer> agencies) {
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

            // add organization addresses
            for(int address: addresses){
                if (addOrganizationAddress(organizationModel.getOrganizationID(), address))
                    continue;
                else
                    return false;
            }

            // add beneficiary
            for(int beneficiary: beneficiaries){
                if (addBeneficiary(beneficiary))
                    continue;
                else
                    return false;
            }

            // add funders
            for(int funder: funders){
                if (addFunder(funder))
                    continue;
                else
                    return false;
            }

            // add organization addresses
            for(int agency: agencies){
                if (addAgency(agency))
                    continue;
                else
                    return false;
            }

        } catch (Exception ex) {
            Log.d("Exception in importing ", ex.getMessage().toString());
        }

        // close the database connection
        db.close();

        return true;
    }

    /**
     * Add organization data
     * @param organizationModel
     * @return Boolean
     */
    public boolean addOrganization(cOrganizationModel organizationModel) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_ID, organizationModel.getOrganizationID());
        cv.put(cSQLDBHelper.KEY_SERVER_ID, organizationModel.getServerID());
        cv.put(cSQLDBHelper.KEY_OWNER_ID, organizationModel.getOwnerID());
        cv.put(cSQLDBHelper.KEY_NAME, organizationModel.getName());
        cv.put(cSQLDBHelper.KEY_TELEPHONE, organizationModel.getPhone());
        cv.put(cSQLDBHelper.KEY_FAX, organizationModel.getFax());
        cv.put(cSQLDBHelper.KEY_VISION, organizationModel.getVision());
        cv.put(cSQLDBHelper.KEY_MISSION, organizationModel.getMission());
        cv.put(cSQLDBHelper.KEY_EMAIL, organizationModel.getEmail());
        cv.put(cSQLDBHelper.KEY_WEBSITE, organizationModel.getWebsite());
        cv.put(cSQLDBHelper.KEY_CREATED_DATE, sdf.format(organizationModel.getCreatedDate()));
        cv.put(cSQLDBHelper.KEY_MODIFIED_DATE, sdf.format(organizationModel.getModifiedDate()));
        cv.put(cSQLDBHelper.KEY_SYNCED_DATE, sdf.format(organizationModel.getSyncedDate()));

        // insert value record
        long result = db.insert(cSQLDBHelper.TABLE_tblORGANIZATION, null, cv);

        // close the database connection
        db.close();

        return result > -1;
    }

    /**
     * Add auxiliary information
     * @param organizationID
     * @param addressID
     * @return
     */
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

    /* ############################################# READ ACTIONS ############################################# */

    public List<cOrganizationModel> getOrganizationList(
            int userID, int primaryRole, int secondaryRoles, int operationBITS, int statusBITS) {

        List<cOrganizationModel> organizationModelList = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String selectQuery = "SELECT * FROM "+ cSQLDBHelper.TABLE_tblORGANIZATION +
                " WHERE ((("+cSQLDBHelper.KEY_OWNER_ID + " = ?) " +
                " AND (("+cSQLDBHelper.KEY_PERMS_BITS + " & ?) != 0)) " +
                " OR ((("+cSQLDBHelper.KEY_GROUP_BITS + " & ?) != 0) " +
                " AND (("+cSQLDBHelper.KEY_PERMS_BITS + " & ?) != 0)) "+
                " OR ((("+cSQLDBHelper.KEY_GROUP_BITS + " & ?) != 0) " +
                " AND (("+cSQLDBHelper.KEY_PERMS_BITS + " & ?) != 0)))";

        Cursor cursor = db.rawQuery(selectQuery, new String[]{
                String.valueOf(userID),String.valueOf(operationBITS),
                String.valueOf(primaryRole),String.valueOf(operationBITS),
                String.valueOf(secondaryRoles),String.valueOf(operationBITS)});

        try {
            if (cursor.moveToFirst()) {
                do {
                    cOrganizationModel organization = new cOrganizationModel();
                    // populate organization model object
                    organization.setOrganizationID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ID)));
                    organization.setServerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_SERVER_ID)));
                    organization.setOwnerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OWNER_ID)));
                    organization.setOrgID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ORG_ID)));
                    organization.setGroupBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_GROUP_BITS)));
                    organization.setPermsBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PERMS_BITS)));
                    organization.setStatusBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_STATUS_BITS)));
                    organization.setName(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_NAME)));
                    organization.setPhone(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_TELEPHONE)));
                    organization.setFax(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_FAX)));
                    organization.setVision(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_VISION)));
                    organization.setMission(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_MISSION)));
                    organization.setEmail(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_EMAIL)));
                    organization.setWebsite(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_WEBSITE)));
                    organization.setCreatedDate(
                            sdf.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_CREATED_DATE))));
                    organization.setModifiedDate(
                            sdf.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_MODIFIED_DATE))));
                    organization.setSyncedDate(
                            sdf.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_SYNCED_DATE))));

                    // populate organization users
                    organization.setUserModelSet(getUsersByOrganizationID(organization.getOrganizationID()));

                    // populate organization roles
                    organization.setRoleModelSet(getRolesByOrganizationID(organization.getOrganizationID()));

                    // populate organization values
                    organization.setValueModelSet(getValuesByOrganizationID(organization.getOrganizationID()));

                    // populate organization addresses
                    organization.setAddressModelSet(getAddressByOrganizationID(organization.getOrganizationID()));

                    // add model organization into the action_list
                    organizationModelList.add(organization);

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to get users from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        // close the database connection
        db.close();

        return organizationModelList;
    }

    /**
     * Read users by organization ID
     * @param organizationID
     * @return
     */
    public Set<cUserModel> getUsersByOrganizationID(int organizationID) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Set<cUserModel> userModelSet = new HashSet();

        // construct a selection query
        String selectQuery = "SELECT " + "user."+cSQLDBHelper.KEY_ID +", "+
                "user."+cSQLDBHelper.KEY_ORGANIZATION_FK_ID + ", user."+cSQLDBHelper.KEY_SERVER_ID +", "+
                "user." +cSQLDBHelper.KEY_OWNER_ID + ", user." +cSQLDBHelper.KEY_ORG_ID +", "+
                "user."+cSQLDBHelper.KEY_GROUP_BITS + ", user." +cSQLDBHelper.KEY_PERMS_BITS +", "+
                "user."+cSQLDBHelper.KEY_STATUS_BITS + ", user." +cSQLDBHelper.KEY_NAME +", "+
                "user."+cSQLDBHelper.KEY_SURNAME + ", user." +cSQLDBHelper.KEY_DESCRIPTION +", "+
                "user."+cSQLDBHelper.KEY_GENDER + ", user." +cSQLDBHelper.KEY_EMAIL +", "+
                "user."+cSQLDBHelper.KEY_WEBSITE + ", user." +cSQLDBHelper.KEY_PHONE +", "+
                "user."+cSQLDBHelper.KEY_PASSWORD + ", user." +cSQLDBHelper.KEY_SALT +", "+
                "user."+cSQLDBHelper.KEY_CREATED_DATE +", "+
                "user."+cSQLDBHelper.KEY_MODIFIED_DATE +", "+
                "user."+cSQLDBHelper.KEY_SYNCED_DATE +", "+
                "organization."+cSQLDBHelper.KEY_ID +
                " FROM " +
                cSQLDBHelper.TABLE_tblUSER + " user, " +
                cSQLDBHelper.TABLE_tblORGANIZATION + " organization " +
                " WHERE organization."+cSQLDBHelper.KEY_ID + " = user."+cSQLDBHelper.KEY_ORGANIZATION_FK_ID +
                " AND organization."+cSQLDBHelper.KEY_ID +" = ?";

        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(organizationID)});

        try {
            if (cursor.moveToFirst()) {
                do {

                    cUserModel user = new cUserModel();

                    user.setUserID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ID)));
                    user.setOrganizationID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ORGANIZATION_FK_ID)));
                    user.setServerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_SERVER_ID)));
                    user.setOwnerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OWNER_ID)));
                    user.setGroupBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_GROUP_BITS)));
                    user.setPermsBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PERMS_BITS)));
                    user.setStatusBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_STATUS_BITS)));
                    user.setPhoto(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_PHOTO)));
                    user.setName(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_NAME)));
                    user.setSurname(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_SURNAME)));
                    user.setGender(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_GENDER)));
                    user.setDescription(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_DESCRIPTION)));
                    user.setEmail(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_EMAIL)));
                    user.setWebsite(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_WEBSITE)));
                    user.setPhone(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_PHONE)));
                    user.setPassword(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_PASSWORD)));
                    user.setSalt(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_SALT)));
                    user.setCreatedDate(sdf.parse(cursor.getString(cursor.getColumnIndex(
                            cSQLDBHelper.KEY_CREATED_DATE))));
                    user.setModifiedDate(sdf.parse(cursor.getString(cursor.getColumnIndex(
                            cSQLDBHelper.KEY_MODIFIED_DATE))));
                    user.setSyncedDate(sdf.parse(cursor.getString(cursor.getColumnIndex(
                            cSQLDBHelper.KEY_SYNCED_DATE))));

                    userModelSet.add(user);

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while reading: "+e.getMessage());
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        // close the database connection
        db.close();

        return userModelSet;
    }

    /**
     * Read roles by organization ID
     * @param organizationID
     * @return
     */
    public Set<cRoleModel> getRolesByOrganizationID(int organizationID) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Set<cRoleModel> roleModelSet = new HashSet();

        // construct a selection query
        String selectQuery = "SELECT " + "role."+cSQLDBHelper.KEY_ID +", "+
                "role."+cSQLDBHelper.KEY_ORGANIZATION_FK_ID + ", role."+cSQLDBHelper.KEY_SERVER_ID +", "+
                "role." +cSQLDBHelper.KEY_OWNER_ID + ", role." +cSQLDBHelper.KEY_ORG_ID +", "+
                "role."+cSQLDBHelper.KEY_GROUP_BITS + ", role." +cSQLDBHelper.KEY_PERMS_BITS +", "+
                "role."+cSQLDBHelper.KEY_STATUS_BITS + ", role." +cSQLDBHelper.KEY_NAME +", "+
                "role."+cSQLDBHelper.KEY_NAME + ", role." +cSQLDBHelper.KEY_DESCRIPTION +", "+
                "role."+cSQLDBHelper.KEY_CREATED_DATE +", "+
                "role."+cSQLDBHelper.KEY_MODIFIED_DATE +", "+
                "role."+cSQLDBHelper.KEY_SYNCED_DATE +", "+
                "organization."+cSQLDBHelper.KEY_ID +
                " FROM " +
                cSQLDBHelper.TABLE_tblROLE+ " role, " +
                cSQLDBHelper.TABLE_tblORGANIZATION + " organization " +
                " WHERE organization."+cSQLDBHelper.KEY_ID + " = role."+cSQLDBHelper.KEY_ORGANIZATION_FK_ID +
                " AND organization."+cSQLDBHelper.KEY_ID +" = ?";

        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(organizationID)});

        try {
            if (cursor.moveToFirst()) {
                do {

                    cRoleModel role = new cRoleModel();

                    role.setRoleID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ID)));
                    role.setOrganizationID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ORGANIZATION_FK_ID)));
                    role.setServerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_SERVER_ID)));
                    role.setOwnerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OWNER_ID)));
                    role.setOrgID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ORG_ID)));
                    role.setGroupBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_GROUP_BITS)));
                    role.setPermsBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PERMS_BITS)));
                    role.setStatusBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_STATUS_BITS)));
                    role.setName(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_NAME)));
                    role.setDescription(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_DESCRIPTION)));
                    role.setCreatedDate(sdf.parse(cursor.getString(cursor.getColumnIndex(
                            cSQLDBHelper.KEY_CREATED_DATE))));
                    role.setModifiedDate(sdf.parse(cursor.getString(cursor.getColumnIndex(
                            cSQLDBHelper.KEY_MODIFIED_DATE))));
                    role.setSyncedDate(sdf.parse(cursor.getString(cursor.getColumnIndex(
                            cSQLDBHelper.KEY_SYNCED_DATE))));

                    roleModelSet.add(role);

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while reading: "+e.getMessage());
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        // close the database connection
        db.close();

        return roleModelSet;
    }

    /**
     * Read values by organization ID
     * @param organizationID
     * @return
     */
    public Set<cValueModel> getValuesByOrganizationID(int organizationID) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Set<cValueModel> valueModelSet = new HashSet();

        // construct a selection query
        String selectQuery = "SELECT " + "value."+cSQLDBHelper.KEY_ID +", "+
                "value."+cSQLDBHelper.KEY_ORGANIZATION_FK_ID + ", value."+cSQLDBHelper.KEY_SERVER_ID +", "+
                "value." +cSQLDBHelper.KEY_OWNER_ID + ", value." +cSQLDBHelper.KEY_ORG_ID +", "+
                "value."+cSQLDBHelper.KEY_GROUP_BITS + ", value." +cSQLDBHelper.KEY_PERMS_BITS +", "+
                "value."+cSQLDBHelper.KEY_STATUS_BITS + ", value." +cSQLDBHelper.KEY_NAME +", "+
                "value."+cSQLDBHelper.KEY_NAME + ", value." +cSQLDBHelper.KEY_DESCRIPTION +", "+
                "value."+cSQLDBHelper.KEY_SETTING_VALUE +", "+
                "value."+cSQLDBHelper.KEY_CREATED_DATE +", "+
                "value."+cSQLDBHelper.KEY_MODIFIED_DATE +", "+
                "value."+cSQLDBHelper.KEY_SYNCED_DATE +", "+
                "organization."+cSQLDBHelper.KEY_ID +
                " FROM " +
                cSQLDBHelper.TABLE_tblVALUE+ " value, " +
                cSQLDBHelper.TABLE_tblORGANIZATION + " organization " +
                " WHERE organization."+cSQLDBHelper.KEY_ID + " = value."+cSQLDBHelper.KEY_ORGANIZATION_FK_ID +
                " AND organization."+cSQLDBHelper.KEY_ID +" = ?";

        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(organizationID)});

        try {
            if (cursor.moveToFirst()) {
                do {

                    cValueModel value = new cValueModel();

                    value.setValueID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ID)));
                    value.setOrganizationID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ORGANIZATION_FK_ID)));
                    value.setServerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_SERVER_ID)));
                    value.setOwnerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OWNER_ID)));
                    value.setOrgID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ORG_ID)));
                    value.setGroupBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_GROUP_BITS)));
                    value.setPermsBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PERMS_BITS)));
                    value.setStatusBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_STATUS_BITS)));
                    value.setName(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_NAME)));
                    value.setDescription(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_DESCRIPTION)));
                    value.setSettingValue(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_SETTING_VALUE)));
                    value.setCreatedDate(sdf.parse(cursor.getString(cursor.getColumnIndex(
                            cSQLDBHelper.KEY_CREATED_DATE))));
                    value.setModifiedDate(sdf.parse(cursor.getString(cursor.getColumnIndex(
                            cSQLDBHelper.KEY_MODIFIED_DATE))));
                    value.setSyncedDate(sdf.parse(cursor.getString(cursor.getColumnIndex(
                            cSQLDBHelper.KEY_SYNCED_DATE))));

                    valueModelSet.add(value);

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while reading: "+e.getMessage());
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        // close the database connection
        db.close();

        return valueModelSet;
    }

    /**
     * Read addresses by organization ID
     * @param organizationID
     * @return
     */
    public Set<cAddressModel> getAddressByOrganizationID(int organizationID) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Set<cAddressModel> addressModelSet = new HashSet();

        // construct a selection query
        String selectQuery = "SELECT " + "address."+cSQLDBHelper.KEY_ID +", "+
                "address."+cSQLDBHelper.KEY_ORGANIZATION_FK_ID + ", address."+cSQLDBHelper.KEY_SERVER_ID +", "+
                "address." +cSQLDBHelper.KEY_OWNER_ID + ", address." +cSQLDBHelper.KEY_ORG_ID +", "+
                "address."+cSQLDBHelper.KEY_GROUP_BITS + ", address." +cSQLDBHelper.KEY_PERMS_BITS +", "+
                "address."+cSQLDBHelper.KEY_STATUS_BITS + ", address." +cSQLDBHelper.KEY_STREET +", "+
                "address."+cSQLDBHelper.KEY_CITY + ", address." +cSQLDBHelper.KEY_PROVINCE +", "+
                "address."+cSQLDBHelper.KEY_POSTAL_CODE + ", address." +cSQLDBHelper.KEY_COUNTRY +", "+
                "address."+cSQLDBHelper.KEY_CREATED_DATE +", "+
                "address."+cSQLDBHelper.KEY_MODIFIED_DATE +", "+
                "address."+cSQLDBHelper.KEY_SYNCED_DATE +", "+
                "organization."+cSQLDBHelper.KEY_ID +
                " FROM " +
                cSQLDBHelper.TABLE_tblORGANIZATION+ " organization, " +
                cSQLDBHelper.TABLE_tblADDRESS+ " address, " +
                cSQLDBHelper.TABLE_tblORG_ADDRESS + " org_address " +
                " WHERE organization."+cSQLDBHelper.KEY_ID + " = org_address."+cSQLDBHelper.KEY_ORGANIZATION_FK_ID +
                " AND address."+cSQLDBHelper.KEY_ID + " = org_address."+cSQLDBHelper.KEY_ADDRESS_FK_ID +
                " AND organization."+cSQLDBHelper.KEY_ID +" = ?";

        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(organizationID)});

        try {
            if (cursor.moveToFirst()) {
                do {

                    cAddressModel address = new cAddressModel();

                    address.setAddressID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ID)));
                    address.setServerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_SERVER_ID)));
                    address.setOwnerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OWNER_ID)));
                    address.setOrgID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ORG_ID)));
                    address.setGroupBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_GROUP_BITS)));
                    address.setPermsBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PERMS_BITS)));
                    address.setStatusBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_STATUS_BITS)));
                    address.setStreet(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_STREET)));
                    address.setCity(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_CITY)));
                    address.setProvince(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_PROVINCE)));
                    address.setPostalCode(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_POSTAL_CODE)));
                    address.setCountry(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_COUNTRY)));
                    address.setCreatedDate(
                            Timestamp.valueOf(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_CREATED_DATE))));
                    address.setModifiedDate(
                            Timestamp.valueOf(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_MODIFIED_DATE))));
                    address.setSyncedDate(
                            Timestamp.valueOf(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_SYNCED_DATE))));

                    addressModelSet.add(address);

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while reading: "+e.getMessage());
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        // close the database connection
        db.close();

        return addressModelSet;
    }

    /**
     *
     * @param organizationID
     * @return cOrganizationModel
     */
    public cOrganizationModel getOrganizationByID(int organizationID) {
        // open connection to read only
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = null;

        try {
            // construct a selection query
            String selectQuery = "SELECT * FROM " + cSQLDBHelper.TABLE_tblORGANIZATION
                    + " WHERE " + cSQLDBHelper.KEY_ID + "= ?";

            // open the cursor to be used to traverse the dataset
            cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(organizationID)});

            cOrganizationModel organization = new cOrganizationModel();

            if (cursor.moveToFirst()) {

                organization.setOrganizationID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ID)));
                organization.setServerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_SERVER_ID)));
                organization.setOwnerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OWNER_ID)));
                organization.setOrgID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ORG_ID)));
                organization.setGroupBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_GROUP_BITS)));
                organization.setPermsBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PERMS_BITS)));
                organization.setStatusBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_STATUS_BITS)));
                organization.setName(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_NAME)));
                organization.setPhone(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_TELEPHONE)));
                organization.setFax(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_FAX)));
                organization.setVision(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_VISION)));
                organization.setMission(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_MISSION)));
                organization.setEmail(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_EMAIL)));
                organization.setWebsite(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_WEBSITE)));
                organization.setCreatedDate(
                        Timestamp.valueOf(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_CREATED_DATE))));
                organization.setModifiedDate(
                        Timestamp.valueOf(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_MODIFIED_DATE))));
                organization.setSyncedDate(
                        Timestamp.valueOf(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_SYNCED_DATE))));
            }

            return organization;

        } catch (Exception e) {
            Log.d(TAG, "Error while reading: "+e.getMessage());
            return null;
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
    }


    public List<cOrganizationModel> getOrganizationList() {
        List<cOrganizationModel> organizationModelList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+ cSQLDBHelper.TABLE_tblORGANIZATION, null);

        try {
            if (cursor.moveToFirst()) {
                do {
                    cOrganizationModel organizationModel = new cOrganizationModel();
                    // populate organization model object
                    organizationModel.setOrganizationID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ID)));
                    //organizationModel.setAddressID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ADDRESS_FK_ID)));
                    organizationModel.setOwnerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OWNER_ID)));
                    organizationModel.setName(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_NAME)));
                    organizationModel.setPhone(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_TELEPHONE)));
                    organizationModel.setFax(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_FAX)));
                    organizationModel.setVision(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_VISION)));
                    organizationModel.setMission(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_MISSION)));
                    organizationModel.setEmail(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_EMAIL)));
                    organizationModel.setWebsite(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_WEBSITE)));
                    organizationModel.setCreatedDate(sdf.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_CREATED_DATE))));
                    // add model organization into the action_list
                    organizationModelList.add(organizationModel);

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to get values from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return organizationModelList;
    }

    public Cursor getAllOrganizations() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        try {
            String selectQuery = "SELECT " + cSQLDBHelper.KEY_ID +", "+ cSQLDBHelper.KEY_NAME + " FROM "
                    + cSQLDBHelper.TABLE_tblORGANIZATION;

            Cursor cursor = db.rawQuery(selectQuery, null);

            return cursor;

        } catch (Exception e) {
            Log.d(TAG, "Error while trying to get values from database");
            return null;
        }
    }

    public Cursor getOrganisationByID(int organizationID) {
        // open connection to read only
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        try {
            // construct a selection query
            String selectQuery = "SELECT "+ cSQLDBHelper.KEY_ID +", "+ cSQLDBHelper.KEY_NAME + " FROM "
                    + cSQLDBHelper.TABLE_tblORGANIZATION
                    + " WHERE " +
                    cSQLDBHelper.KEY_ID + "= ?";

            // open the cursor to be used to traverse the dataset
            Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(organizationID)});

            return cursor;

        } catch (Exception e) {
            Log.d(TAG, "Error while trying to get values from database");
            return null;
        }
    }

    public List<cTreeModel> getOrganizationTree()
    {
        // open connection to read only
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // construct a selection query for organization
        String selectOrganizationQuery = "SELECT * FROM " + cSQLDBHelper.TABLE_tblORGANIZATION;

        // open the organization cursor to be used to traverse the dataset
        Cursor organizationCursor = db.rawQuery(selectOrganizationQuery, null);

        // the action_list of two fields to be populated and returned
        List<cTreeModel> treeList = new ArrayList<>();

        int index = 0;
        // looping through all goal rows and adding to tree action_list
        if (organizationCursor.moveToFirst()) {
            try {
                do {
                    cTreeModel treeData = new cTreeModel(
                            organizationCursor.getInt(organizationCursor.getColumnIndex(cSQLDBHelper.KEY_ID)),
                            -1,
                            0,
                            null
                            //new cOrganizationModel(
                            //        organizationCursor.getInt(organizationCursor.getColumnIndex(cSQLDBHelper.KEY_ID)),
                            //        organizationCursor.getString(organizationCursor.getColumnIndex(cSQLDBHelper.KEY_NAME))
                            //)
                    );

                    treeList.add(treeData);

                    index = index + 1;

                } while (organizationCursor.moveToNext());
            }catch (Exception e) {
                Log.d(TAG, "Error while trying to get values from database");
            } finally {
                if (organizationCursor != null && !organizationCursor.isClosed())
                    organizationCursor.close();
            }
        }

        // close the database connection
        db.close();

        return treeList;
    }

    /* ############################################# UPDATE ACTIONS ############################################# */


    /* ############################################# DELETE ACTIONS ############################################# */

    /**
     * Delete all organizations
     * @return Boolean
     */
    public boolean deleteOrganizations() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblORGANIZATION, null, null);

        // close the database connection
        db.close();

        return result > -1;
    }

    /* ############################################# SYNC ACTIONS ############################################# */

}
