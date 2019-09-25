package com.me.mseotsanyana.mande.BRBAC.DAL;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.me.mseotsanyana.mande.PPMER.DAL.cSQLDBHelper;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by mseotsanyana on 2017/08/24.
 */

public class cAddressDBA {
    // an object of the database helper
    private cSQLDBHelper dbHelper;

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final String TAG = "dbHelper";

    public cAddressDBA(Context context) {
        dbHelper = new cSQLDBHelper(context);
    }

    public boolean deleteAllAddresses() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_ADDRESS, null, null);

        // close the database connection
        db.close();

        return result > -1;
    }

    public boolean addAddressFromExcel(cAddressModel addressModel) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_ADDRESS_ID, addressModel.getAddressID());
        cv.put(cSQLDBHelper.KEY_ADDRESS_STREET, addressModel.getStreet());
        cv.put(cSQLDBHelper.KEY_ADDRESS_CITY, addressModel.getCity());
        cv.put(cSQLDBHelper.KEY_ADDRESS_PROVINCE, addressModel.getProvince());
        cv.put(cSQLDBHelper.KEY_ADDRESS_POSTAL_CODE, addressModel.getPostalCode());
        cv.put(cSQLDBHelper.KEY_ADDRESS_COUNTRY, addressModel.getCountry());

        // insert outcome record
        try {
            if (db.insert(cSQLDBHelper.TABLE_ADDRESS, null, cv) < 0) {
                return false;
            }
        } catch (Exception ex) {
            Log.d("Exception in importing ", ex.getMessage().toString());
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

        cv.put(cSQLDBHelper.KEY_ADDRESS_STREET, addressModel.getStreet());
        cv.put(cSQLDBHelper.KEY_ADDRESS_CITY, addressModel.getCity());
        cv.put(cSQLDBHelper.KEY_ADDRESS_PROVINCE, addressModel.getProvince());
        cv.put(cSQLDBHelper.KEY_ADDRESS_POSTAL_CODE, addressModel.getPostalCode());
        cv.put(cSQLDBHelper.KEY_ADDRESS_COUNTRY, addressModel.getCountry());

        // insert outcome record
        long id = db.insert(cSQLDBHelper.TABLE_ADDRESS, null, cv);

        // close the database connection
        db.close();

        return id;
    }

    public long updateAddress(cAddressModel addressModel)
    {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_ADDRESS_ID, addressModel.getAddressID());
        cv.put(cSQLDBHelper.KEY_ADDRESS_SERVER_ID, addressModel.getServerID());
        cv.put(cSQLDBHelper.KEY_ADDRESS_OWNER_ID, addressModel.getOwnerID());
        cv.put(cSQLDBHelper.KEY_ADDRESS_GROUP_BITS, addressModel.getGroupBITS());
        cv.put(cSQLDBHelper.KEY_ADDRESS_PERMS_BITS, addressModel.getPermsBITS());
        cv.put(cSQLDBHelper.KEY_ADDRESS_STATUS_BITS, addressModel.getStatusBITS());
        cv.put(cSQLDBHelper.KEY_ADDRESS_STREET, addressModel.getStreet());
        cv.put(cSQLDBHelper.KEY_ADDRESS_CITY, addressModel.getCity());
        cv.put(cSQLDBHelper.KEY_ADDRESS_PROVINCE, addressModel.getProvince());
        cv.put(cSQLDBHelper.KEY_ADDRESS_POSTAL_CODE, addressModel.getPostalCode());
        cv.put(cSQLDBHelper.KEY_ADDRESS_COUNTRY, addressModel.getCountry());
        cv.put(cSQLDBHelper.KEY_ADDRESS_CREATED_DATE, sdf.format(addressModel.getCreatedDate()));
        cv.put(cSQLDBHelper.KEY_ADDRESS_MODIFIED_DATE, sdf.format(addressModel.getModifiedDate()));
        cv.put(cSQLDBHelper.KEY_ADDRESS_SYNCED_DATE, sdf.format(addressModel.getSyncedDate()));


        // update a specific record
        long result = db.update(cSQLDBHelper.TABLE_ADDRESS, cv,
                cSQLDBHelper.KEY_ADDRESS_ID + "= ?",
                new String[]{String.valueOf(addressModel.getAddressID())});

        // close the database connection
        db.close();

        return result;
    }

    public boolean deleteAddress(int addressID) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete a specific record
        long result = db.delete(cSQLDBHelper.TABLE_ADDRESS,
                cSQLDBHelper.KEY_ADDRESS_ID + "= ?",
                new String[]{String.valueOf(addressID)});

        // close the database connection
        db.close();

        return result > -1;
    }


    public boolean deleteAddresses() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_ADDRESS, null, null);

        // close the database connection
        db.close();

        return result > -1;
    }

    public int isServerID(int serverID) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // construct a selection query
        String selectQuery = "SELECT * FROM " +
                cSQLDBHelper.TABLE_ADDRESS + " WHERE " +
                cSQLDBHelper.KEY_ADDRESS_SERVER_ID + " = ?";

        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(serverID)});

        int addressID = -1;

        try {
            if (cursor.moveToFirst()) {
                addressID = cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ADDRESS_ID));
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
                cSQLDBHelper.TABLE_ADDRESS + " WHERE " +
                cSQLDBHelper.KEY_ADDRESS_ID + "= ?";

        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(addressID)});

        cAddressModel address = new cAddressModel();

        try {
            if (cursor.moveToFirst()) {
                do {
                    address.setAddressID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ADDRESS_ID)));
                    address.setServerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ADDRESS_SERVER_ID)));
                    address.setOwnerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ADDRESS_OWNER_ID)));
                    address.setGroupBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ADDRESS_GROUP_BITS)));
                    address.setPermsBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ADDRESS_PERMS_BITS)));
                    address.setStatusBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ADDRESS_STATUS_BITS)));
                    address.setStreet(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_ADDRESS_STREET)));
                    address.setCity(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_ADDRESS_CITY)));
                    address.setProvince(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_ADDRESS_PROVINCE)));
                    address.setPostalCode(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_ADDRESS_POSTAL_CODE)));
                    address.setCountry(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_ADDRESS_COUNTRY)));
                    address.setCreatedDate(Timestamp.valueOf(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_ADDRESS_CREATED_DATE))));
                    address.setModifiedDate(Timestamp.valueOf(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_ADDRESS_MODIFIED_DATE))));
                    address.setSyncedDate(Timestamp.valueOf(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_ADDRESS_SYNCED_DATE))));

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
                cSQLDBHelper.TABLE_ADDRESS + " WHERE " +
                cSQLDBHelper.KEY_ADDRESS_SERVER_ID + "= ?";

        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(serverID)});

        cAddressModel address = new cAddressModel();

        try {
            if (cursor.moveToFirst()) {
                do {
                    address.setAddressID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ADDRESS_ID)));
                    address.setServerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ADDRESS_SERVER_ID)));
                    address.setOwnerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ADDRESS_OWNER_ID)));
                    address.setGroupBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ADDRESS_GROUP_BITS)));
                    address.setPermsBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ADDRESS_PERMS_BITS)));
                    address.setStatusBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ADDRESS_STATUS_BITS)));
                    address.setStreet(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_ADDRESS_STREET)));
                    address.setCity(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_ADDRESS_CITY)));
                    address.setProvince(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_ADDRESS_PROVINCE)));
                    address.setPostalCode(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_ADDRESS_POSTAL_CODE)));
                    address.setCountry(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_ADDRESS_COUNTRY)));
                    address.setCreatedDate(Timestamp.valueOf(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_ADDRESS_CREATED_DATE))));
                    address.setModifiedDate(Timestamp.valueOf(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_ADDRESS_MODIFIED_DATE))));
                    address.setSyncedDate(Timestamp.valueOf(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_ADDRESS_SYNCED_DATE))));

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

    public List<cAddressModel> getAddresses(int userID, int group, int other, int perms, int[] status) {

        List<cAddressModel> addressModels = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();

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
                /*
                " AND ((" + cSQLDBHelper.KEY_ADDRESS_STATUS_BITS + " = 0) " +
                " OR ((" + cSQLDBHelper.KEY_ADDRESS_STATUS_BITS + " & ?) != 0)))";
*/
        Cursor cursor = db.rawQuery(selectQuery, new String[]{
                String.valueOf(userID), String.valueOf(perms),String.valueOf(status[0]),
                String.valueOf(group), String.valueOf(perms),String.valueOf(status[1]),
                String.valueOf(other), String.valueOf(perms),String.valueOf(status[2])});

        try {
            if (cursor.moveToFirst()) {
                do {
                    cAddressModel address = new cAddressModel();

                    address.setAddressID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ADDRESS_ID)));
                    address.setServerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ADDRESS_SERVER_ID)));
                    address.setOwnerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ADDRESS_OWNER_ID)));
                    address.setGroupBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ADDRESS_GROUP_BITS)));
                    address.setPermsBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ADDRESS_PERMS_BITS)));
                    address.setStatusBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ADDRESS_STATUS_BITS)));
                    address.setStreet(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_ADDRESS_STREET)));
                    address.setCity(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_ADDRESS_CITY)));
                    address.setProvince(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_ADDRESS_PROVINCE)));
                    address.setPostalCode(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_ADDRESS_POSTAL_CODE)));
                    address.setCountry(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_ADDRESS_COUNTRY)));
                    address.setCreatedDate(Timestamp.valueOf(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_ADDRESS_CREATED_DATE))));
                    address.setModifiedDate(Timestamp.valueOf(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_ADDRESS_MODIFIED_DATE))));
                    address.setSyncedDate(Timestamp.valueOf(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_ADDRESS_SYNCED_DATE))));

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


    /* read the records to sync with the server */
    public List<cAddressModel> getAddresses2Sync(int userID, int group, int other, int perms, int[] status) {

        List<cAddressModel> addressModels = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();

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

        try {
            if (cursor.moveToFirst()) {
                do {
                    cAddressModel address = new cAddressModel();

                    address.setAddressID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ADDRESS_ID)));
                    address.setServerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ADDRESS_SERVER_ID)));
                    address.setOwnerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ADDRESS_OWNER_ID)));
                    address.setGroupBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ADDRESS_GROUP_BITS)));
                    address.setPermsBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ADDRESS_PERMS_BITS)));
                    address.setStatusBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ADDRESS_STATUS_BITS)));
                    address.setStreet(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_ADDRESS_STREET)));
                    address.setCity(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_ADDRESS_CITY)));
                    address.setProvince(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_ADDRESS_PROVINCE)));
                    address.setPostalCode(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_ADDRESS_POSTAL_CODE)));
                    address.setCountry(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_ADDRESS_COUNTRY)));
                    address.setCreatedDate(Timestamp.valueOf(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_ADDRESS_CREATED_DATE))));
                    address.setModifiedDate(Timestamp.valueOf(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_ADDRESS_MODIFIED_DATE))));
                    address.setSyncedDate(Timestamp.valueOf(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_ADDRESS_SYNCED_DATE))));

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