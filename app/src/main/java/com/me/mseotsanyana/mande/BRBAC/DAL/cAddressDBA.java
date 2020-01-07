package com.me.mseotsanyana.mande.BRBAC.DAL;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.me.mseotsanyana.mande.STORAGE.database.cSQLDBHelper;
import com.me.mseotsanyana.mande.UTILITY.cConstant;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mseotsanyana on 2017/08/24.
 */

public class cAddressDBA {
    private static SimpleDateFormat sdf = cConstant.FORMAT_DATE;
    private static String TAG = cAddressDBA.class.getSimpleName();

    // an object of the database helper
    private cSQLDBHelper dbHelper;

    public cAddressDBA(Context context) {
        dbHelper = new cSQLDBHelper(context);
    }

    /* ############################################# CREATE ACTIONS ############################################# */

    /**
     * Add addresses from excel file
     * @param addressModel
     * @return Boolean
     */
    public boolean addAddressFromExcel(cAddressModel addressModel) {
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

        // insert address record
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblADDRESS, null, cv) < 0) {
                return false;
            }
        } catch (Exception e) {
            Log.d(TAG,"Exception in importing: "+e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }

    public long createAddress(cAddressModel addressModel) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        cv.put(cSQLDBHelper.KEY_STREET, addressModel.getStreet());
        cv.put(cSQLDBHelper.KEY_CITY, addressModel.getCity());
        cv.put(cSQLDBHelper.KEY_PROVINCE, addressModel.getProvince());
        cv.put(cSQLDBHelper.KEY_POSTAL_CODE, addressModel.getPostalCode());
        cv.put(cSQLDBHelper.KEY_COUNTRY, addressModel.getCountry());

        // insert outcome record
        long id = db.insert(cSQLDBHelper.TABLE_tblADDRESS, null, cv);

        // close the database connection
        db.close();

        return id;
    }

    /* ############################################# READ ACTIONS ############################################# */

    /**
     * Read and filter addresses
     * @param userID
     * @param group
     * @param other
     * @param perms
     * @param status
     * @return List
     */
    public List<cAddressModel> getAddresses(int userID, int group, int other, int perms, int[] status) {

        List<cAddressModel> addressModels = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + cSQLDBHelper.TABLE_tblADDRESS +
                " WHERE (((" + cSQLDBHelper.KEY_OWNER_ID + " = ?) " +
                " AND ((" + cSQLDBHelper.KEY_PERMS_BITS + " & ?) != 0) " +
                " AND ((0 = ?) " + " OR ((" + cSQLDBHelper.KEY_STATUS_BITS + " & ?) != 0))) " +
                " OR (((" + cSQLDBHelper.KEY_GROUP_BITS + " & ?) != 0) " +
                " AND ((" + cSQLDBHelper.KEY_PERMS_BITS + " & ?) != 0) " +
                " AND ((0 = ?) " + " OR ((" + cSQLDBHelper.KEY_STATUS_BITS + " & ?) != 0))) " +
                " OR (((" + cSQLDBHelper.KEY_GROUP_BITS + " & ?) != 0) " +
                " AND ((" + cSQLDBHelper.KEY_PERMS_BITS + " & ?) != 0)" +
                " AND ((0 = ?) " + " OR ((" + cSQLDBHelper.KEY_STATUS_BITS + " & ?) != 0))))";
                /*
                " AND ((" + cSQLDBHelper.KEY_STATUS_BITS + " = 0) " +
                " OR ((" + cSQLDBHelper.KEY_STATUS_BITS + " & ?) != 0)))";
*/
        Cursor cursor = db.rawQuery(selectQuery, new String[]{
                String.valueOf(userID), String.valueOf(perms),String.valueOf(status[0]),
                String.valueOf(group), String.valueOf(perms),String.valueOf(status[1]),
                String.valueOf(other), String.valueOf(perms),String.valueOf(status[2])});

        try {
            if (cursor.moveToFirst()) {
                do {
                    cAddressModel address = new cAddressModel();

                    address.setAddressID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ID)));
                    address.setServerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_SERVER_ID)));
                    address.setOwnerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OWNER_ID)));
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
            Log.d(TAG, "Error while trying to get users from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        // close the database connection
        db.close();

        return addressModels;
    }

    public int isServerID(int serverID) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // construct a selection query
        String selectQuery = "SELECT * FROM " +
                cSQLDBHelper.TABLE_tblADDRESS + " WHERE " +
                cSQLDBHelper.KEY_SERVER_ID + " = ?";

        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(serverID)});

        int addressID = -1;

        try {
            if (cursor.moveToFirst()) {
                addressID = cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ID));
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

        return addressID;
    }

    public cAddressModel getAddressByID(int addressID) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        // construct a selection query
        String selectQuery = "SELECT * FROM " +
                cSQLDBHelper.TABLE_tblADDRESS + " WHERE " +
                cSQLDBHelper.KEY_ID + "= ?";

        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(addressID)});

        cAddressModel address = new cAddressModel();

        try {
            if (cursor.moveToFirst()) {
                do {
                    address.setAddressID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ID)));
                    address.setServerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_SERVER_ID)));
                    address.setOwnerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OWNER_ID)));
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

        return address;
    }

    public cAddressModel getAddressByServerID(int serverID) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        // construct a selection query
        String selectQuery = "SELECT * FROM " +
                cSQLDBHelper.TABLE_tblADDRESS + " WHERE " +
                cSQLDBHelper.KEY_SERVER_ID + "= ?";

        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(serverID)});

        cAddressModel address = new cAddressModel();

        try {
            if (cursor.moveToFirst()) {
                do {
                    address.setAddressID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ID)));
                    address.setServerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_SERVER_ID)));
                    address.setOwnerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OWNER_ID)));
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

        return address;
    }

    /* ############################################# UPDATE ACTIONS ############################################# */

    public long updateAddress(cAddressModel addressModel) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_ID, addressModel.getAddressID());
        cv.put(cSQLDBHelper.KEY_SERVER_ID, addressModel.getServerID());
        cv.put(cSQLDBHelper.KEY_OWNER_ID, addressModel.getOwnerID());
        cv.put(cSQLDBHelper.KEY_GROUP_BITS, addressModel.getGroupBITS());
        cv.put(cSQLDBHelper.KEY_PERMS_BITS, addressModel.getPermsBITS());
        cv.put(cSQLDBHelper.KEY_STATUS_BITS, addressModel.getStatusBITS());
        cv.put(cSQLDBHelper.KEY_STREET, addressModel.getStreet());
        cv.put(cSQLDBHelper.KEY_CITY, addressModel.getCity());
        cv.put(cSQLDBHelper.KEY_PROVINCE, addressModel.getProvince());
        cv.put(cSQLDBHelper.KEY_POSTAL_CODE, addressModel.getPostalCode());
        cv.put(cSQLDBHelper.KEY_COUNTRY, addressModel.getCountry());
        cv.put(cSQLDBHelper.KEY_CREATED_DATE, sdf.format(addressModel.getCreatedDate()));
        cv.put(cSQLDBHelper.KEY_MODIFIED_DATE, sdf.format(addressModel.getModifiedDate()));
        cv.put(cSQLDBHelper.KEY_SYNCED_DATE, sdf.format(addressModel.getSyncedDate()));


        // update a specific record
        long result = db.update(cSQLDBHelper.TABLE_tblADDRESS, cv,
                cSQLDBHelper.KEY_ID + "= ?",
                new String[]{String.valueOf(addressModel.getAddressID())});

        // close the database connection
        db.close();

        return result;
    }

    /* ############################################# DELETE ACTIONS ############################################# */

    /**
     * Delete all addresses
     * @return Boolean
     */
    public boolean deleteAddresses() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblADDRESS, null, null);

        // close the database connection
        db.close();

        return result > -1;
    }


    public boolean deleteAllAddresses() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblADDRESS, null, null);

        // close the database connection
        db.close();

        return result > -1;
    }

    public boolean deleteAddress(int addressID) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete a specific record
        long result = db.delete(cSQLDBHelper.TABLE_tblADDRESS,
                cSQLDBHelper.KEY_ID + "= ?",
                new String[]{String.valueOf(addressID)});

        // close the database connection
        db.close();

        return result > -1;
    }

    /* ############################################# SYNC ACTIONS ############################################# */

    /* read the records to sync with the server */
    public List<cAddressModel> getAddresses2Sync(int userID, int group, int other, int perms, int[] status) {

        List<cAddressModel> addressModels = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + cSQLDBHelper.TABLE_tblADDRESS +
                " WHERE (((" + cSQLDBHelper.KEY_OWNER_ID + " = ?) " +
                " AND ((" + cSQLDBHelper.KEY_PERMS_BITS + " & ?) != 0) " +
                " AND ((0 = ?) " + " OR ((" + cSQLDBHelper.KEY_STATUS_BITS + " & ?) != 0))) " +
                " OR (((" + cSQLDBHelper.KEY_GROUP_BITS + " & ?) != 0) " +
                " AND ((" + cSQLDBHelper.KEY_PERMS_BITS + " & ?) != 0) " +
                " AND ((0 = ?) " + " OR ((" + cSQLDBHelper.KEY_STATUS_BITS + " & ?) != 0))) " +
                " OR (((" + cSQLDBHelper.KEY_GROUP_BITS + " & ?) != 0) " +
                " AND ((" + cSQLDBHelper.KEY_PERMS_BITS + " & ?) != 0)" +
                " AND ((0 = ?) " + " OR ((" + cSQLDBHelper.KEY_STATUS_BITS + " & ?) != 0))))";

        Cursor cursor = db.rawQuery(selectQuery, new String[]{
                String.valueOf(userID), String.valueOf(perms),String.valueOf(status[0]),
                String.valueOf(group), String.valueOf(perms),String.valueOf(status[1]),
                String.valueOf(other), String.valueOf(perms),String.valueOf(status[2])});

        try {
            if (cursor.moveToFirst()) {
                do {
                    cAddressModel address = new cAddressModel();

                    address.setAddressID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ID)));
                    address.setServerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_SERVER_ID)));
                    address.setOwnerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OWNER_ID)));
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
            Log.d(TAG, "Error while trying to get users from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        // close the database connection
        db.close();

        return addressModels;
    }
}