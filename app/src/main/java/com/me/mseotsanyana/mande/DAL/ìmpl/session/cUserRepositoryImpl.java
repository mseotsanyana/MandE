package com.me.mseotsanyana.mande.DAL.ìmpl.session;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.google.gson.Gson;
import com.me.mseotsanyana.mande.BLL.repository.session.iUserRepository;
import com.me.mseotsanyana.mande.DAL.model.session.cAddressModel;
import com.me.mseotsanyana.mande.DAL.model.session.cNotificationModel;
import com.me.mseotsanyana.mande.DAL.model.session.cOrganizationModel;
import com.me.mseotsanyana.mande.DAL.model.session.cRoleModel;
import com.me.mseotsanyana.mande.DAL.model.session.cUserModel;
import com.me.mseotsanyana.mande.DAL.storage.database.cSQLDBHelper;
import com.me.mseotsanyana.mande.DAL.storage.preference.cSharedPreference;
import com.me.mseotsanyana.mande.UTIL.cConstant;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by mseotsanyana on 2016/10/23.
 */
public class cUserRepositoryImpl implements iUserRepository {
    private static SimpleDateFormat sdf = cConstant.FORMAT_DATE;
    private static String TAG = cUserRepositoryImpl.class.getSimpleName();

    // an object of the database helper
    private cSQLDBHelper dbHelper;

    private SharedPreferences settings;
    private cSharedPreference sharedPreference;
    SharedPreferences.Editor editor;

    private int primaryRoleBITS, secondaryRoleBITS, entityBITS, operationBITS, statusBITS;

    private Gson gson;

    public cUserRepositoryImpl(Context context) {
        dbHelper = new cSQLDBHelper(context);
        sharedPreference = new cSharedPreference(context);
        gson = new Gson();
        settings = sharedPreference.getSettings();
        editor = sharedPreference.getEditor();
    }

    /* ############################################# CREATE ACTIONS ############################################# */

    /**
     * Add user from an excel file
     *
     * @param userModel
     * @param addresses
     * @return
     */
    public boolean addUserFromExcel(cUserModel userModel, ArrayList<Integer> addresses) {
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
            for (int address : addresses) {
                if (addUserAddress(userModel.getUserID(), address))
                    continue;
                else
                    return false;
            }
        } catch (Exception e) {
            Log.d(TAG, "Exception in importing: " + e.getMessage().toString());
        }

        // close the database connection
        db.close();

        return true;
    }

    public long addUser(cUserModel userModel, int organizationID) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_ORGANIZATION_FK_ID, organizationID);
        cv.put(cSQLDBHelper.KEY_NAME, userModel.getName());
        cv.put(cSQLDBHelper.KEY_SURNAME, userModel.getSurname());
        cv.put(cSQLDBHelper.KEY_GENDER, userModel.getGender());
        cv.put(cSQLDBHelper.KEY_PHOTO, userModel.getPhoto());
        cv.put(cSQLDBHelper.KEY_DESCRIPTION, userModel.getDescription());
        cv.put(cSQLDBHelper.KEY_EMAIL, userModel.getEmail());
        cv.put(cSQLDBHelper.KEY_WEBSITE, userModel.getWebsite());
        cv.put(cSQLDBHelper.KEY_PHONE, userModel.getPhone());
        cv.put(cSQLDBHelper.KEY_PASSWORD, userModel.getPassword());
        cv.put(cSQLDBHelper.KEY_SALT, userModel.getSalt());

        // insert outcome record
        long userID = -1;
        try {
            userID = db.insert(cSQLDBHelper.TABLE_tblUSER, null, cv);
            if (userID < 0) {
                return userID;
            }
        } catch (Exception e) {
            Log.d(TAG, "Exception in importing: " + e.getMessage().toString());
        }

        // close the database connection
        db.close();

        return userID;
    }

    public boolean addUserAddress(int userID, int addressID) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(cSQLDBHelper.KEY_USER_FK_ID, userID);
        cv.put(cSQLDBHelper.KEY_ADDRESS_FK_ID, addressID);

        if (db.insert(cSQLDBHelper.TABLE_tblUSER_ADDRESS, null, cv) < 0) {
            return false;
        }

        return true;
    }

    /* ############################################# READ ACTIONS ############################################# */

    /**
     * This is used to read a user during login
     *
     * @param email
     * @param password
     * @return
     */
    @Override
    public cUserModel getUserByEmailPassword(String email, String password) {
        cUserModel user = null;
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        // construct a selection query
        String selectQuery = "SELECT * FROM " +
                cSQLDBHelper.TABLE_tblUSER + " WHERE " +
                cSQLDBHelper.KEY_EMAIL + " = ? " + " AND " + cSQLDBHelper.KEY_PASSWORD + " = ?";

        Cursor cursor = db.rawQuery(selectQuery, new String[]{email, password});

        try {
            if (cursor.moveToFirst()) {
                //do {
                user = new cUserModel();

                user.setUserID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ID)));
                user.setOrganizationID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ORGANIZATION_FK_ID)));
                user.setServerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_SERVER_ID)));
                user.setOwnerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OWNER_ID)));
                user.setOrgID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ORG_ID)));
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
                user.setCreatedDate(
                        Timestamp.valueOf(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_CREATED_DATE))));
                user.setModifiedDate(
                        Timestamp.valueOf(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_MODIFIED_DATE))));
                user.setSyncedDate(
                        Timestamp.valueOf(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_SYNCED_DATE))));

                // populate user own organization
                user.setOrganizationModel(getOrganizationByID(user.getOrganizationID()));

                // populate user addresses
                user.setAddressModelSet(getAddressByUserID(user.getUserID()));

                // populate user roles
                Gson gson = new Gson();
                user.setRoleModelSet(getRolesByUserID(user.getUserID()));
                //Log.d(TAG," ROLES 0 = "+gson.toJson(getRolesByUserID(user.getUserID())));
                //Log.d(TAG," ROLES 1 = "+gson.toJson(user.getRoleModelSet()));

                // populate user published notifications
                user.setPublisherModelSet(getNotificationsByPublisherID(user.getUserID()));

                // populate user subscribed notifications
                user.setSubscriberModelSet(getNotificationsBySubscriberID(user.getUserID()));
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to get user by email from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        // close the database connection
        db.close();

        return user;
    }

    /**
     * Read and filter a list of users
     *
     * @param userID
     * @param primaryRole
     * @param secondaryRoles
     * @param operationBITS
     * @param statusBITS
     * @return
     */
    public List<cUserModel> getUserList(
            int userID, int primaryRole, int secondaryRoles, int operationBITS, int statusBITS) {

        List<cUserModel> userModelList = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String selectQuery = "SELECT " + cSQLDBHelper.KEY_ORGANIZATION_FK_ID + ", " +
                cSQLDBHelper.KEY_ID + ", " + cSQLDBHelper.KEY_PHOTO + ", " +
                cSQLDBHelper.KEY_ADDRESS_FK_ID + ", " + cSQLDBHelper.KEY_OWNER_ID + ", " +
                cSQLDBHelper.KEY_GROUP_BITS + ", " + cSQLDBHelper.KEY_PERMS_BITS + ", " +
                cSQLDBHelper.KEY_STATUS_BITS + ", " + cSQLDBHelper.KEY_NAME + ", " +
                cSQLDBHelper.KEY_SURNAME + ", " + cSQLDBHelper.KEY_DESCRIPTION + ", " +
                cSQLDBHelper.KEY_GENDER + ", " + cSQLDBHelper.KEY_EMAIL + ", " +
                cSQLDBHelper.KEY_WEBSITE + ", " + cSQLDBHelper.KEY_PHONE + ", " +
                cSQLDBHelper.KEY_PASSWORD + ", " + cSQLDBHelper.KEY_SALT + ", " +
                cSQLDBHelper.KEY_CREATED_DATE + ", " +
                cSQLDBHelper.KEY_MODIFIED_DATE + ", " +
                cSQLDBHelper.KEY_SYNCED_DATE +
                " FROM " + cSQLDBHelper.TABLE_tblUSER +
                " WHERE (((" + cSQLDBHelper.KEY_OWNER_ID + " = ?) " +
                " AND ((" + cSQLDBHelper.KEY_PERMS_BITS + " & ?) != 0)) " +
                " OR (((" + cSQLDBHelper.KEY_GROUP_BITS + " & ?) != 0) " +
                " AND ((" + cSQLDBHelper.KEY_PERMS_BITS + " & ?) != 0)) " +
                " OR (((" + cSQLDBHelper.KEY_GROUP_BITS + " & ?) != 0) " +
                " AND ((" + cSQLDBHelper.KEY_PERMS_BITS + " & ?) != 0)))";

        Cursor cursor = db.rawQuery(selectQuery, new String[]{
                String.valueOf(userID), String.valueOf(operationBITS),
                String.valueOf(primaryRole), String.valueOf(operationBITS),
                String.valueOf(secondaryRoles), String.valueOf(operationBITS)});

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

                    // construct an organization
                    user.setOrganizationModel(getOrganizationByID(user.getOrganizationID()));

                    // populate user addresses
                    user.setAddressModelSet(getAddressByUserID(user.getUserID()));

                    // populate user roles
                    user.setRoleModelSet(getRolesByUserID(user.getUserID()));

                    // populate user published notifications
                    user.setPublisherModelSet(getNotificationsByPublisherID(user.getUserID()));

                    // populate user subscribed notifications
                    user.setSubscriberModelSet(getNotificationsBySubscriberID(user.getUserID()));

                    userModelList.add(user);


                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, e.getMessage() + " Error while trying to get users from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        // close the database connection
        db.close();

        return userModelList;
    }

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
            Log.d(TAG, "Error while reading: " + e.getMessage());
            return null;
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
    }


    /**
     * Read addresses by user ID
     *
     * @param userID
     * @return
     */
    public Set<cAddressModel> getAddressByUserID(int userID) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Set<cAddressModel> addressModels = new HashSet();

        // construct a selection query
        String selectQuery = "SELECT " +
                "address." + cSQLDBHelper.KEY_ID + ", address." + cSQLDBHelper.KEY_SERVER_ID + ", " +
                "address." + cSQLDBHelper.KEY_OWNER_ID + ", address." + cSQLDBHelper.KEY_ORG_ID + ", " +
                "address." + cSQLDBHelper.KEY_GROUP_BITS + ", address." + cSQLDBHelper.KEY_PERMS_BITS + ", " +
                "address." + cSQLDBHelper.KEY_STATUS_BITS + ", address." + cSQLDBHelper.KEY_STREET + ", " +
                "address." + cSQLDBHelper.KEY_CITY + ", address." + cSQLDBHelper.KEY_PROVINCE + ", " +
                "address." + cSQLDBHelper.KEY_POSTAL_CODE + ", address." + cSQLDBHelper.KEY_COUNTRY + ", " +
                "address." + cSQLDBHelper.KEY_CREATED_DATE + ", address." + cSQLDBHelper.KEY_MODIFIED_DATE + ", " +
                "address." + cSQLDBHelper.KEY_SYNCED_DATE +
                " FROM " +
                cSQLDBHelper.TABLE_tblUSER + " user, " +
                cSQLDBHelper.TABLE_tblADDRESS + " address, " +
                cSQLDBHelper.TABLE_tblUSER_ADDRESS + " user_address " +
                " WHERE user." + cSQLDBHelper.KEY_ID + " = user_address." + cSQLDBHelper.KEY_USER_FK_ID +
                " AND address." + cSQLDBHelper.KEY_ID + " = user_address." + cSQLDBHelper.KEY_ADDRESS_FK_ID +
                " AND user." + cSQLDBHelper.KEY_ID + " = ?";

        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(userID)});

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
                    address.setCreatedDate(Timestamp.valueOf(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_CREATED_DATE))));
                    address.setModifiedDate(Timestamp.valueOf(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_MODIFIED_DATE))));
                    address.setSyncedDate(Timestamp.valueOf(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_SYNCED_DATE))));

                    addressModels.add(address);

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to get projects from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        // close the database connection
        db.close();

        return addressModels;
    }

    /**
     * Read roles by user ID
     *
     * @param userID
     * @return Set
     */
    public Set<cRoleModel> getRolesByUserID(int userID) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Set<cRoleModel> roleModelSet = new HashSet();

        // construct a selection query
        String selectQuery = "SELECT " +
                "role." + cSQLDBHelper.KEY_ID + ", role." + cSQLDBHelper.KEY_ORGANIZATION_FK_ID + ", " +
                "role." + cSQLDBHelper.KEY_SERVER_ID + ", " +
                "role." + cSQLDBHelper.KEY_OWNER_ID + ", role." + cSQLDBHelper.KEY_ORG_ID + ", " +
                "role." + cSQLDBHelper.KEY_GROUP_BITS + ", role." + cSQLDBHelper.KEY_PERMS_BITS + ", " +
                "role." + cSQLDBHelper.KEY_STATUS_BITS + ", role." + cSQLDBHelper.KEY_NAME + ", " +
                "role." + cSQLDBHelper.KEY_DESCRIPTION + ", role." + cSQLDBHelper.KEY_CREATED_DATE + ", " +
                "role." + cSQLDBHelper.KEY_MODIFIED_DATE + ", role." + cSQLDBHelper.KEY_SYNCED_DATE +
                " FROM " +
                cSQLDBHelper.TABLE_tblUSER + " user, " +
                cSQLDBHelper.TABLE_tblROLE + " role, " +
                cSQLDBHelper.TABLE_tblUSER_ROLE + " user_role " +
                " WHERE user." + cSQLDBHelper.KEY_ID + " = user_role." + cSQLDBHelper.KEY_USER_FK_ID +
                " AND role." + cSQLDBHelper.KEY_ID + " = user_role." + cSQLDBHelper.KEY_ROLE_FK_ID +
                " AND role." + cSQLDBHelper.KEY_ORGANIZATION_FK_ID + " = user_role." + cSQLDBHelper.KEY_ORGANIZATION_FK_ID +
                " AND user." + cSQLDBHelper.KEY_ID + " = ?";

        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(userID)});

        try {
            if (cursor.moveToFirst()) {
                do {

                    cRoleModel roleModel = new cRoleModel();

                    roleModel.setRoleID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ID)));
                    roleModel.setOrganizationID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ORGANIZATION_FK_ID)));
                    roleModel.setServerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_SERVER_ID)));
                    roleModel.setOwnerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OWNER_ID)));
                    roleModel.setOrgID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ORG_ID)));
                    roleModel.setGroupBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_GROUP_BITS)));
                    roleModel.setPermsBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PERMS_BITS)));
                    roleModel.setStatusBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_STATUS_BITS)));
                    roleModel.setName(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_NAME)));
                    roleModel.setDescription(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_DESCRIPTION)));
                    roleModel.setCreatedDate(Timestamp.valueOf(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_CREATED_DATE))));
                    roleModel.setModifiedDate(Timestamp.valueOf(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_MODIFIED_DATE))));
                    roleModel.setSyncedDate(Timestamp.valueOf(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_SYNCED_DATE))));

                    // construct an organization model
                    //roleModel.setOrganizationModel(new cOrganizationModel(
                    //        organizationDBA.getOrganizationByID(roleModel.getOrganizationID())));

                    roleModelSet.add(roleModel);

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error reading: " + e.getMessage());
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
     * Read and filter notifications/messages by subscriber ID
     *
     * @param subscriberID
     * @return
     */
    public Set<cNotificationModel> getNotificationsBySubscriberID(int subscriberID) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Set<cNotificationModel> notificationModelSet = new HashSet();

        // construct a selection query
        String selectQuery = "SELECT " +
                "notification." + cSQLDBHelper.KEY_ID + ", notification." + cSQLDBHelper.KEY_ENTITY_FK_ID + ", " +
                "notification." + cSQLDBHelper.KEY_ENTITY_TYPE_FK_ID + ", notification." + cSQLDBHelper.KEY_SERVER_ID + ", " +
                "notification." + cSQLDBHelper.KEY_OWNER_ID + ", notification." + cSQLDBHelper.KEY_ORG_ID + ", " +
                "notification." + cSQLDBHelper.KEY_GROUP_BITS + ", notification." + cSQLDBHelper.KEY_PERMS_BITS + ", " +
                "notification." + cSQLDBHelper.KEY_STATUS_BITS + ", notification." + cSQLDBHelper.KEY_NAME + ", " +
                "notification." + cSQLDBHelper.KEY_DESCRIPTION + ", notification." + cSQLDBHelper.KEY_CREATED_DATE + ", " +
                "notification." + cSQLDBHelper.KEY_MODIFIED_DATE + ", notification." + cSQLDBHelper.KEY_SYNCED_DATE +
                " FROM " +
                cSQLDBHelper.TABLE_tblUSER + " user, " +
                cSQLDBHelper.TABLE_tblNOTIFICATION + " notification, " +
                cSQLDBHelper.TABLE_tblSUBSCRIBER + " subscriber " +
                " WHERE user." + cSQLDBHelper.KEY_ID + " = subscriber." + cSQLDBHelper.KEY_SUBSCRIBER_FK_ID +
                " AND notification." + cSQLDBHelper.KEY_ID + " = subscriber." + cSQLDBHelper.KEY_NOTIFICATION_FK_ID +
                " AND user." + cSQLDBHelper.KEY_ID + " = ?";

        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(subscriberID)});

        try {
            if (cursor.moveToFirst()) {
                do {

                    cNotificationModel notificationModel = new cNotificationModel();

                    notificationModel.setNotificationID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ID)));
                    notificationModel.setEntityID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ENTITY_FK_ID)));
                    notificationModel.setEntityTypeID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ENTITY_TYPE_FK_ID)));
                    notificationModel.setServerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_SERVER_ID)));
                    notificationModel.setOwnerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OWNER_ID)));
                    notificationModel.setOrgID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ORG_ID)));
                    notificationModel.setGroupBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_GROUP_BITS)));
                    notificationModel.setPermsBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PERMS_BITS)));
                    notificationModel.setStatusBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_STATUS_BITS)));
                    notificationModel.setName(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_NAME)));
                    notificationModel.setDescription(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_DESCRIPTION)));
                    notificationModel.setCreatedDate(
                            Timestamp.valueOf(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_CREATED_DATE))));
                    notificationModel.setModifiedDate(
                            Timestamp.valueOf(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_MODIFIED_DATE))));
                    notificationModel.setSyncedDate(
                            Timestamp.valueOf(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_SYNCED_DATE))));

                    notificationModelSet.add(notificationModel);

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while reading " + e.getMessage());
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        // close the database connection
        db.close();

        return notificationModelSet;
    }

    /**
     * Read and filter notifications/messages by publisher ID
     *
     * @param publisherID
     * @return
     */
    public Set<cNotificationModel> getNotificationsByPublisherID(int publisherID) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Set<cNotificationModel> notificationModelSet = new HashSet();

        // construct a selection query
        String selectQuery = "SELECT " +
                "notification." + cSQLDBHelper.KEY_ID + ", notification." + cSQLDBHelper.KEY_ENTITY_FK_ID + ", " +
                "notification." + cSQLDBHelper.KEY_ENTITY_TYPE_FK_ID + ", notification." + cSQLDBHelper.KEY_SERVER_ID + ", " +
                "notification." + cSQLDBHelper.KEY_OWNER_ID + ", notification." + cSQLDBHelper.KEY_ORG_ID + ", " +
                "notification." + cSQLDBHelper.KEY_GROUP_BITS + ", notification." + cSQLDBHelper.KEY_PERMS_BITS + ", " +
                "notification." + cSQLDBHelper.KEY_STATUS_BITS + ", notification." + cSQLDBHelper.KEY_NAME + ", " +
                "notification." + cSQLDBHelper.KEY_DESCRIPTION + ", notification." + cSQLDBHelper.KEY_CREATED_DATE + ", " +
                "notification." + cSQLDBHelper.KEY_MODIFIED_DATE + ", notification." + cSQLDBHelper.KEY_SYNCED_DATE +
                " FROM " +
                cSQLDBHelper.TABLE_tblUSER + " user, " +
                cSQLDBHelper.TABLE_tblNOTIFICATION + " notification, " +
                cSQLDBHelper.TABLE_tblPUBLISHER + " publisher " +
                " WHERE user." + cSQLDBHelper.KEY_ID + " = publisher." + cSQLDBHelper.KEY_PUBLISHER_FK_ID +
                " AND notification." + cSQLDBHelper.KEY_ID + " = publisher." + cSQLDBHelper.KEY_NOTIFICATION_FK_ID +
                " AND user." + cSQLDBHelper.KEY_ID + " = ?";

        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(publisherID)});

        try {
            if (cursor.moveToFirst()) {
                do {

                    cNotificationModel notificationModel = new cNotificationModel();

                    notificationModel.setNotificationID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ID)));
                    notificationModel.setEntityID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ENTITY_FK_ID)));
                    notificationModel.setEntityTypeID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ENTITY_TYPE_FK_ID)));
                    notificationModel.setServerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_SERVER_ID)));
                    notificationModel.setOwnerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OWNER_ID)));
                    notificationModel.setOrgID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ORG_ID)));
                    notificationModel.setGroupBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_GROUP_BITS)));
                    notificationModel.setPermsBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PERMS_BITS)));
                    notificationModel.setStatusBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_STATUS_BITS)));
                    notificationModel.setName(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_NAME)));
                    notificationModel.setDescription(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_DESCRIPTION)));
                    notificationModel.setCreatedDate(
                            Timestamp.valueOf(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_CREATED_DATE))));
                    notificationModel.setModifiedDate(
                            Timestamp.valueOf(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_MODIFIED_DATE))));
                    notificationModel.setSyncedDate(
                            Timestamp.valueOf(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_SYNCED_DATE))));

                    notificationModelSet.add(notificationModel);

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while reading " + e.getMessage());
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        // close the database connection
        db.close();

        return notificationModelSet;
    }

    public cUserModel getUserByID(int userID) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        // construct a selection query
        String selectQuery = "SELECT * FROM " +
                cSQLDBHelper.TABLE_tblUSER + " WHERE " +
                cSQLDBHelper.KEY_ID + "= ?";

        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(userID)});

/*
        String selectQuery = "SELECT * FROM " + cSQLDBHelper.TABLE_ADDRESS +
                " WHERE (((" + cSQLDBHelper.KEY_ADDRESS_OWNER_ID + " = ?) " +
                " AND ((" + cSQLDBHelper.KEY_ADDRESS_PERMS_BITS + " & ?) != 0) " +
                " AND ((0 = ?) " + " OR ((" + cSQLDBHelper.KEY_ADDRESS_STATUS_BITS + " & ?) != 0))) " +
                " OR (((" + cSQLDBHelper.KEY_ADDRESS_GROUP_BITS + " & ?) != 0) " +
                " AND ((" + cSQLDBHelper.KEY_ADDRESS_PERMS_BITS + " & ?) != 0) " +
                " AND ((0 = ?) " + " OR ((" + cSQLDBHelper.KEY_ADDRESS_STATUS_BITS + " & ?) != 0))) " +
                " OR (((" + cSQLDBHelper.KEY_ADDRESS_GROUP_BITS + " & ?) != 0) " +
                " AND ((" + cSQLDBHelper.KEY_ADDRESS_PERMS_BITS + " & ?) != 0)" +
                " AND ((0 = ?) " + " OR ((" + cSQLDBHelper.KEY_ADDRESS_STATUS_BITS + " & ?) != 0))))";

        Cursor cursor = db.rawQuery(selectQuery, new String[]{
                String.valueOf(userID), String.valueOf(perms),String.valueOf(status[0]),
                String.valueOf(group), String.valueOf(perms),String.valueOf(status[1]),
                String.valueOf(other), String.valueOf(perms),String.valueOf(status[2])});
*/

        cUserModel user = new cUserModel();

        try {
            if (cursor.moveToFirst()) {
                do {
                    user.setUserID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ID)));
                    //org.setOrganizationID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ORGANIZATION_FK_ID)));
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
                    //user.setCreateDate(formatter.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_DATE))));

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to get projects from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        // close the database connection
        db.close();

        return user;
    }

    /**
     * This method to check user exist or not
     *
     * @param email
     * @return true/false
     */
    public boolean checkUser(String email) {

        // array of columns to fetch
        String[] columns = {
                cSQLDBHelper.KEY_ID
        };

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // selection criteria
        String selection = cSQLDBHelper.KEY_EMAIL + " = ?";

        // selection argument
        String[] selectionArgs = {email};

        // query user table with condition
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com';
         */
        Cursor cursor = db.query(cSQLDBHelper.TABLE_tblUSER, //Table to query
                columns,                                  //columns to return
                selection,                                //columns for the WHERE clause
                selectionArgs,                            //The values for the WHERE clause
                null,                                     //group the rows
                null,                                     //filter by row groups
                null);                                    //The sort order

        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if (cursorCount > 0) {
            return true;
        }

        return false;
    }

    /**
     * This method to check user exist or not
     *
     * @param email
     * @param password
     * @return true/false
     */
    public cUserModel checkUser(String email, String password) {

        // array of columns to fetch
        String[] columns = {
                cSQLDBHelper.KEY_ID
        };

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // selection criteria
        String selection = cSQLDBHelper.KEY_EMAIL + " = ?" + " AND " + cSQLDBHelper.KEY_PASSWORD + " = ?";

        // selection arguments
        String[] selectionArgs = {email, password};

        // query user table with conditions
        /**
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@androidtutorialshub.com' AND user_password = 'qwerty';
         */
        Cursor cursor = db.query(cSQLDBHelper.TABLE_tblUSER, //Table to query
                columns,                                  //columns to return
                selection,                                //columns for the WHERE clause
                selectionArgs,                            //The values for the WHERE clause
                null,                                     //group the rows
                null,                                     //filter by row groups
                null);                                    //The sort order

        cUserModel user = new cUserModel();

        try {
            if (cursor.moveToFirst()) {
                do {
                    user.setUserID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ID)));
                    user.setOwnerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OWNER_ID)));
                    user.setGroupBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_GROUP_BITS)));
                    user.setPermsBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PERMS_BITS)));
                    user.setStatusBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_STATUS_BITS)));
                    user.setName(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_NAME)));
                    user.setSurname(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_SURNAME)));
                    user.setGender(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_GENDER)));
                    user.setDescription(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_DESCRIPTION)));
                    user.setEmail(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_EMAIL)));
                    user.setPassword(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_PASSWORD)));
                    user.setSalt(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_SALT)));
                    //user.setCreateDate(formatter.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_DATE))));

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to get projects from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        // close the database connection
        db.close();

        return user;
    }

    /**
     * gets the path of the photo for the specified report in the database.
     *
     * @param userID the identifier of the photo for which to get the photo.
     * @return the photo for the user, or null if no photo was found.
     */
    public String getUserPhotoPath(int userID) {
        // Gets the database in the current database helper in read-only mode
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // After the query, the cursor points to the first database row
        // returned by the request
        Cursor cursor = null;/*db.query(cSQLDBHelper.TABLE_tblUSER,
                null All columns ,
                cSQLDBHelper.KEY_UNIT_ID + " = ? ",
                new String[]{Long.toString(userID)},
                null,
                null,
                null);*/

        String photoPath = null;
        if (cursor != null && cursor.moveToFirst()) {
            // Get the path of the picture from the database row pointed by
            // the cursor using the getColumnIndex method of the cursor.
            int columnIndex = cursor.getColumnIndex(cSQLDBHelper.KEY_PHOTO);
            photoPath = cursor.getString(columnIndex);
            cursor.close();
        }

        db.close();

        return photoPath;
    }

    /* ############################################# UPDATE ACTIONS ############################################# */

    public boolean updateUser(cUserModel userModel) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        Date date = new Date();
        Timestamp ts = new Timestamp(date.getTime());

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_ID, userModel.getUserID());
        cv.put(cSQLDBHelper.KEY_PHOTO, userModel.getPhoto());
        cv.put(cSQLDBHelper.KEY_OWNER_ID, userModel.getOwnerID());
        cv.put(cSQLDBHelper.KEY_GROUP_BITS, userModel.getGroupBITS());
        cv.put(cSQLDBHelper.KEY_PERMS_BITS, userModel.getPermsBITS());
        cv.put(cSQLDBHelper.KEY_STATUS_BITS, userModel.getStatusBITS());
        cv.put(cSQLDBHelper.KEY_PHOTO, userModel.getPhoto());
        cv.put(cSQLDBHelper.KEY_NAME, userModel.getName());
        cv.put(cSQLDBHelper.KEY_SURNAME, userModel.getSurname());
        cv.put(cSQLDBHelper.KEY_GENDER, userModel.getGender());
        cv.put(cSQLDBHelper.KEY_DESCRIPTION, userModel.getDescription());
        cv.put(cSQLDBHelper.KEY_EMAIL, userModel.getEmail());
        cv.put(cSQLDBHelper.KEY_WEBSITE, userModel.getWebsite());
        cv.put(cSQLDBHelper.KEY_PHONE, userModel.getPhone());
        cv.put(cSQLDBHelper.KEY_PASSWORD, userModel.getPassword());
        cv.put(cSQLDBHelper.KEY_SALT, userModel.getSalt());

        cv.put(cSQLDBHelper.KEY_MODIFIED_DATE, sdf.format(ts));

        // update a specific record
        long result = db.update(cSQLDBHelper.TABLE_tblUSER, cv,
                cSQLDBHelper.KEY_ID + "= ?",
                new String[]{String.valueOf(userModel.getUserID())});

        // close the database connection
        db.close();

        return result > -1;
    }

    /**
     * update the path of the photo for the specified user in the database.
     *
     * @param photoPath the identifier of the photo for which to get the photo.
     */
    public int updateUserPhotoPath(int userID, String photoPath) {
        // updates the database entry for the user to point to the photo
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues newPhotoValue = new ContentValues();

        newPhotoValue.put(cSQLDBHelper.KEY_PHOTO, photoPath);

        int updatedUserID = db.update(cSQLDBHelper.TABLE_tblUSER,
                newPhotoValue,
                cSQLDBHelper.KEY_ID + " = ? ",
                new String[]{Long.toString(userID)});

        db.close();

        return updatedUserID;
    }

    /* ############################################# DELETE ACTIONS ############################################# */

    /**
     * Delete all users
     *
     * @return Boolean
     */
    public boolean deleteUsers() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblUSER, null, null);

        // close the database connection
        db.close();

        return result > -1;
    }

    public boolean deleteUser(int userID) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete a specific record
        long result = db.delete(cSQLDBHelper.TABLE_tblUSER,
                cSQLDBHelper.KEY_ID + "= ?",
                new String[]{String.valueOf(userID)});

        // close the database connection
        db.close();

        return result > -1;
    }

    /* ############################################# SYNC ACTIONS ############################################# */

}
