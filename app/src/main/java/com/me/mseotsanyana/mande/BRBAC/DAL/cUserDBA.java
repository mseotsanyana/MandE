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
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by mseotsanyana on 2016/10/23.
 */
public class cUserDBA {
    // an object of the database helper
    private cSQLDBHelper dbHelper;

    private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
    private static final String TAG = "dbHelper";

    public cUserDBA(Context context) {
        dbHelper = new cSQLDBHelper(context);
    }

    public boolean deleteAllUsers() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblUSER, null, null);

        // close the database connection
        db.close();

        return result > -1;
    }

    public boolean addUserFromExcel(cUserModel userModel) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_ID, userModel.getUserID());
        cv.put(cSQLDBHelper.KEY_ORGANIZATION_FK_ID, userModel.getOrganizationID());
        cv.put(cSQLDBHelper.KEY_ADDRESS_FK_ID, userModel.getAddressID());
        cv.put(cSQLDBHelper.KEY_NAME, userModel.getName());
        cv.put(cSQLDBHelper.KEY_SURNAME, userModel.getSurname());
        cv.put(cSQLDBHelper.KEY_GENDER, userModel.getGender());
        cv.put(cSQLDBHelper.KEY_PHOTO, userModel.getPhotoPath());
        cv.put(cSQLDBHelper.KEY_DESCRIPTION, userModel.getDescription());
        cv.put(cSQLDBHelper.KEY_EMAIL, userModel.getEmail());
        cv.put(cSQLDBHelper.KEY_WEBSITE, userModel.getWebsite());
        cv.put(cSQLDBHelper.KEY_PHONE, userModel.getPhone());
        cv.put(cSQLDBHelper.KEY_PASSWORD, userModel.getPassword());
        cv.put(cSQLDBHelper.KEY_SALT, userModel.getSalt());
        //cv.put(cSQLDBHelper.KEY_DATE, formatter.format(userModel.getCreateDate()));

        // insert outcome record
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblUSER, null, cv) < 0) {
                return false;
            }
        } catch (Exception ex) {
            Log.d("Exception in importing ", ex.getMessage().toString());
        }

        // close the database connection
        db.close();

        return true;
    }

    public long createUser(cUserModel userModel) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_PHOTO, userModel.getPhotoPath());

        cv.put(cSQLDBHelper.KEY_ORGANIZATION_FK_ID, userModel.getOrganizationID());
        cv.put(cSQLDBHelper.KEY_ADDRESS_FK_ID, userModel.getAddressID());
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
        long id = db.insert(cSQLDBHelper.TABLE_tblUSER, null, cv);
        /*try {
            if (db.insert(cSQLDBHelper.TABLE_tblUSER, null, cv) < 0) {
                return false;
            }
        } catch (Exception ex) {
            Log.d("Exception in importing ", ex.getMessage().toString());
        }*/

        // close the database connection
        db.close();

        return id;
    }

    public boolean updateUser(cUserModel userModel){
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        Date date= new Date();
        Timestamp ts = new Timestamp(date.getTime());

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_ID, userModel.getUserID());
        cv.put(cSQLDBHelper.KEY_PHOTO, userModel.getPhotoPath());
        cv.put(cSQLDBHelper.KEY_ADDRESS_FK_ID, userModel.getAddressID());
        cv.put(cSQLDBHelper.KEY_OWNER_ID, userModel.getOwnerID());
        cv.put(cSQLDBHelper.KEY_GROUP_BITS, userModel.getGroupBITS());
        cv.put(cSQLDBHelper.KEY_PERMS_BITS, userModel.getPermsBITS());
        cv.put(cSQLDBHelper.KEY_STATUS_BITS, userModel.getStatusBITS());
        cv.put(cSQLDBHelper.KEY_PHOTO, userModel.getPhotoPath());
        cv.put(cSQLDBHelper.KEY_NAME, userModel.getName());
        cv.put(cSQLDBHelper.KEY_SURNAME, userModel.getSurname());
        cv.put(cSQLDBHelper.KEY_GENDER, userModel.getGender());
        cv.put(cSQLDBHelper.KEY_DESCRIPTION, userModel.getDescription());
        cv.put(cSQLDBHelper.KEY_EMAIL, userModel.getEmail());
        cv.put(cSQLDBHelper.KEY_WEBSITE, userModel.getWebsite());
        cv.put(cSQLDBHelper.KEY_PHONE, userModel.getPhone());
        cv.put(cSQLDBHelper.KEY_PASSWORD, userModel.getPassword());
        cv.put(cSQLDBHelper.KEY_SALT, userModel.getSalt());

        cv.put(cSQLDBHelper.KEY_MODIFIED_DATE, formatter.format(ts));

        // update a specific record
        long result = db.update(cSQLDBHelper.TABLE_tblUSER, cv,
                cSQLDBHelper.KEY_ID + "= ?",
                new String[]{String.valueOf(userModel.getUserID())});

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
                    user.setOrganizationID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ORGANIZATION_FK_ID)));
                    user.setAddressID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ADDRESS_FK_ID)));
                    user.setOwnerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OWNER_ID)));
                    user.setGroupBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_GROUP_BITS)));
                    user.setPermsBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PERMS_BITS)));
                    user.setStatusBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_STATUS_BITS)));
                    user.setPhotoPath(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_PHOTO)));
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


    public cUserModel getUserByEmailPassword(String email, String password) {
        cUserModel user = null;
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        // construct a selection query
        String selectQuery = "SELECT * FROM " +
                cSQLDBHelper.TABLE_tblUSER + " WHERE " +
                cSQLDBHelper.KEY_EMAIL+" = ? "+" AND "+cSQLDBHelper.KEY_PASSWORD+" = ?";

        Cursor cursor = db.rawQuery(selectQuery, new String[]{email, password});


        try {
            if (cursor.moveToFirst()) {
                do {
                    user = new cUserModel();

                    user.setUserID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ID)));
                    user.setOrganizationID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ORGANIZATION_FK_ID)));
                    user.setAddressID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ADDRESS_FK_ID)));
                    user.setOwnerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OWNER_ID)));
                    user.setGroupBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_GROUP_BITS)));
                    user.setPermsBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PERMS_BITS)));
                    user.setStatusBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_STATUS_BITS)));
                    user.setPhotoPath(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_PHOTO)));
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


    public List<cUserModel> getUserList(
            int userID, int primaryRole, int secondaryRoles, int operationBITS, int statusBITS) {

        List<cUserModel> userModelList = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String selectQuery = "SELECT "+cSQLDBHelper.KEY_ORGANIZATION_FK_ID+", "+
                cSQLDBHelper.KEY_ID+", "+cSQLDBHelper.KEY_PHOTO+", "+
                cSQLDBHelper.KEY_ADDRESS_FK_ID+", "+cSQLDBHelper.KEY_OWNER_ID+", "+
                cSQLDBHelper.KEY_GROUP_BITS+", "+cSQLDBHelper.KEY_PERMS_BITS+", "+
                cSQLDBHelper.KEY_STATUS_BITS+", "+cSQLDBHelper.KEY_NAME+", "+
                cSQLDBHelper.KEY_SURNAME+", "+ cSQLDBHelper.KEY_DESCRIPTION+", "+
                cSQLDBHelper.KEY_GENDER+", "+cSQLDBHelper.KEY_EMAIL+", "+
                cSQLDBHelper.KEY_WEBSITE+", "+cSQLDBHelper.KEY_PHONE+", "+
                cSQLDBHelper.KEY_PASSWORD+", "+ cSQLDBHelper.KEY_SALT+", "+
                cSQLDBHelper.KEY_CREATED_DATE +", "+
                cSQLDBHelper.KEY_MODIFIED_DATE +", "+
                cSQLDBHelper.KEY_SYNCED_DATE+
                " FROM "+ cSQLDBHelper.TABLE_tblUSER +
                " WHERE ((("+cSQLDBHelper.KEY_OWNER_ID+" = ?) " +
                " AND (("+cSQLDBHelper.KEY_PERMS_BITS+" & ?) != 0)) " +
                " OR ((("+cSQLDBHelper.KEY_GROUP_BITS +" & ?) != 0) " +
                " AND (("+cSQLDBHelper.KEY_PERMS_BITS+" & ?) != 0)) "+
                " OR ((("+cSQLDBHelper.KEY_GROUP_BITS +" & ?) != 0) " +
                " AND (("+cSQLDBHelper.KEY_PERMS_BITS+" & ?) != 0)))";

        Cursor cursor = db.rawQuery(selectQuery, new String[]{
                String.valueOf(userID),String.valueOf(operationBITS),
                String.valueOf(primaryRole),String.valueOf(operationBITS),
                String.valueOf(secondaryRoles),String.valueOf(operationBITS)});

        try {
            if (cursor.moveToFirst()) {
                do {
                    cUserModel user = new cUserModel();
                    user.setOrganizationID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ORGANIZATION_FK_ID)));
                    user.setUserID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ID)));
                    user.setPhotoPath(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_PHOTO)));
                    user.setAddressID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ADDRESS_FK_ID)));
                    //user.s(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ORGANIZATION_FK_ID)));
                    user.setOwnerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OWNER_ID)));
                    user.setGroupBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_GROUP_BITS)));
                    user.setPermsBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PERMS_BITS)));
                    user.setStatusBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_STATUS_BITS)));
                    user.setName(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_NAME)));
                    user.setSurname(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_SURNAME)));
                    user.setGender(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_GENDER)));
                    user.setDescription(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_DESCRIPTION)));
                    user.setEmail(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_EMAIL)));
                    user.setWebsite(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_WEBSITE)));
                    user.setPhone(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_PHONE)));
                    user.setPassword(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_PASSWORD)));
                    user.setSalt(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_SALT)));
                    user.setCreatedDate(formatter.parse(cursor.getString(cursor.getColumnIndex(
                            cSQLDBHelper.KEY_CREATED_DATE))));
                    user.setModifiedDate(formatter.parse(cursor.getString(cursor.getColumnIndex(
                            cSQLDBHelper.KEY_MODIFIED_DATE))));
                    user.setSyncedDate(formatter.parse(cursor.getString(cursor.getColumnIndex(
                            cSQLDBHelper.KEY_SYNCED_DATE))));

                    userModelList.add(user);

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, e.getMessage()+" Error while trying to get users from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        // close the database connection
        db.close();

        return userModelList;
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
     * update the path of the photo for the specified user in the database.
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

    /**
     * gets the path of the photo for the specified report in the database.
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
            int columnIndex  = cursor.getColumnIndex(cSQLDBHelper.KEY_PHOTO);
            photoPath = cursor.getString(columnIndex);
            cursor.close();
        }

        db.close();

        return photoPath;
    }
}
