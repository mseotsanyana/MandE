package com.me.mseotsanyana.mande.BRBAC.DAL;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.me.mseotsanyana.mande.PPMER.DAL.cSQLDBHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by mseotsanyana on 2016/10/23.
 */
public class cSessionDBA {
    // an object of the database helper
    private cSQLDBHelper dbHelper;

    private static final SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
    private static final String TAG = "dbHelper";

    public cSessionDBA(Context context) {
            dbHelper = new cSQLDBHelper(context);
        }

    public boolean deleteAllSessions() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblSESSION, null, null);

        // close the database connection
        db.close();

        return result > -1;
    }

    public boolean addSessionFromExcel(cSessionModel sessionModel) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_ID, sessionModel.getSessionID());
        cv.put(cSQLDBHelper.KEY_USER_FK_ID, sessionModel.getUserID());
        cv.put(cSQLDBHelper.KEY_ORGANIZATION_FK_ID, sessionModel.getOrganizationID());
        cv.put(cSQLDBHelper.KEY_ADDRESS_FK_ID, sessionModel.getAddressID());
        cv.put(cSQLDBHelper.KEY_NAME, sessionModel.getName());
        cv.put(cSQLDBHelper.KEY_SURNAME, sessionModel.getSurname());
        cv.put(cSQLDBHelper.KEY_PHOTO, sessionModel.getPhotoPath());
        cv.put(cSQLDBHelper.KEY_DESCRIPTION, sessionModel.getDescription());
        cv.put(cSQLDBHelper.KEY_EMAIL, sessionModel.getEmail());
        cv.put(cSQLDBHelper.KEY_WEBSITE, sessionModel.getWebsite());
        cv.put(cSQLDBHelper.KEY_PHONE, sessionModel.getPhone());
        cv.put(cSQLDBHelper.KEY_PASSWORD, sessionModel.getPassword());
        cv.put(cSQLDBHelper.KEY_SALT, sessionModel.getSalt());
        //cv.put(cSQLDBHelper.KEY_DATE, formatter.format(sessionModel.getCreateDate()));

        // insert outcome record
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblSESSION, null, cv) < 0) {
                return false;
            }
        } catch (Exception ex) {
            Log.d("Exception in importing ", ex.getMessage().toString());
        }

        // close the database connection
        db.close();

        return true;
    }

    public boolean addSession(cSessionModel sessionModel) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_ID, sessionModel.getSessionID());
        cv.put(cSQLDBHelper.KEY_OWNER_ID, sessionModel.getOwnerID());
        cv.put(cSQLDBHelper.KEY_GROUP_BITS, sessionModel.getGroupBITS());
        cv.put(cSQLDBHelper.KEY_PERMS_BITS, sessionModel.getPermsBITS());
        cv.put(cSQLDBHelper.KEY_STATUS_BITS, sessionModel.getStatusBITS());
        cv.put(cSQLDBHelper.KEY_NAME, sessionModel.getName());
        cv.put(cSQLDBHelper.KEY_SURNAME, sessionModel.getSurname());
        cv.put(cSQLDBHelper.KEY_DESCRIPTION, sessionModel.getDescription());
        cv.put(cSQLDBHelper.KEY_EMAIL, sessionModel.getEmail());
        cv.put(cSQLDBHelper.KEY_PASSWORD, sessionModel.getPassword());
        cv.put(cSQLDBHelper.KEY_SALT, sessionModel.getSalt());
        //cv.put(cSQLDBHelper.KEY_DATE, formatter.format(sessionModel.getCreateDate()));

        // insert outcome record
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblSESSION, null, cv) < 0) {
                return false;
            }
        } catch (Exception ex) {
            Log.d("Exception in importing ", ex.getMessage().toString());
        }

        // close the database connection
        db.close();

        return true;
    }

    public cSessionModel getSessionByID(int sessionID) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        // construct a selection query
        String selectQuery = "SELECT * FROM " +
                cSQLDBHelper.TABLE_tblSESSION + " WHERE " +
                cSQLDBHelper.KEY_ID + "= ?";

        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(sessionID)});

        cSessionModel session = new cSessionModel();

        try {
            if (cursor.moveToFirst()) {
                do {
                    session.setSessionID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ID)));
                    session.setOwnerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OWNER_ID)));
                    session.setGroupBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_GROUP_BITS)));
                    session.setPermsBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PERMS_BITS)));
                    session.setStatusBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_STATUS_BITS)));
                    session.setName(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_NAME)));
                    session.setSurname(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_SURNAME)));
                    session.setDescription(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_DESCRIPTION)));
                    session.setEmail(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_EMAIL)));
                    session.setPassword(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_PASSWORD)));
                    session.setSalt(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_SALT)));
                    //session.setCreateDate(formatter.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_DATE))));

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

        return session;
    }


    public cSessionModel getSessionByEmailPassword(String email, String password) {
        cSessionModel session = null;
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        // construct a selection query
        String selectQuery = "SELECT * FROM " +
                cSQLDBHelper.TABLE_tblSESSION + " WHERE " +
                cSQLDBHelper.KEY_EMAIL+" = ? "+" AND "+cSQLDBHelper.KEY_PASSWORD+" = ?";

        Cursor cursor = db.rawQuery(selectQuery, new String[]{email, password});


        try {
            if (cursor.moveToFirst()) {
                do {
                    session = new cSessionModel();

                    session.setSessionID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ID)));
                    session.setOwnerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OWNER_ID)));
                    session.setGroupBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_GROUP_BITS)));
                    session.setPermsBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PERMS_BITS)));
                    session.setStatusBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_STATUS_BITS)));
                    session.setName(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_NAME)));
                    session.setSurname(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_SURNAME)));
                    session.setDescription(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_DESCRIPTION)));
                    session.setEmail(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_EMAIL)));
                    session.setPassword(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_PASSWORD)));
                    session.setSalt(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_SALT)));
                    //session.setCreateDate(formatter.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_DATE))));

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

        return session;
    }


    public List<cSessionModel> getSessionList() {

        List<cSessionModel> sessionModelList = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+ cSQLDBHelper.TABLE_tblSESSION, null);

        try {
            if (cursor.moveToFirst()) {
                do {
                    cSessionModel session = new cSessionModel();

                    session.setSessionID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ID)));
                    session.setOwnerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OWNER_ID)));
                    session.setGroupBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_GROUP_BITS)));
                    session.setPermsBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PERMS_BITS)));
                    session.setStatusBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_STATUS_BITS)));
                    session.setName(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_NAME)));
                    session.setSurname(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_SURNAME)));
                    session.setDescription(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_DESCRIPTION)));
                    session.setEmail(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_EMAIL)));
                    session.setPassword(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_PASSWORD)));
                    session.setSalt(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_SALT)));
                    //session.setCreateDate(formatter.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_DATE))));

                    sessionModelList.add(session);

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

        return sessionModelList;
    }

    /**
    * This method to check session exist or not
    *
    * @param email
    * @return true/false
    */
    public boolean checkSession(String email) {

        // array of columns to fetch
        String[] columns = {
                cSQLDBHelper.KEY_ID
        };

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // selection criteria
        String selection = cSQLDBHelper.KEY_EMAIL + " = ?";

        // selection argument
        String[] selectionArgs = {email};

        // query session table with condition
        /**
        * Here query function is used to fetch records from session table this function works like we use sql query.
        * SQL query equivalent to this query function is
        * SELECT session_id FROM session WHERE session_email = 'jack@androidtutorialshub.com';
        */
        Cursor cursor = db.query(cSQLDBHelper.TABLE_tblSESSION, //Table to query
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
    * This method to check session exist or not
    *
    * @param email
    * @param password
    * @return true/false
    */
    public cSessionModel checkSession(String email, String password) {

        // array of columns to fetch
        String[] columns = {
               cSQLDBHelper.KEY_ID
        };

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // selection criteria
        String selection = cSQLDBHelper.KEY_EMAIL + " = ?" + " AND " + cSQLDBHelper.KEY_PASSWORD + " = ?";

        // selection arguments
        String[] selectionArgs = {email, password};

        // query session table with conditions
        /**
        * Here query function is used to fetch records from session table this function works like we use sql query.
        * SQL query equivalent to this query function is
        * SELECT session_id FROM session WHERE session_email = 'jack@androidtutorialshub.com' AND session_password = 'qwerty';
        */
        Cursor cursor = db.query(cSQLDBHelper.TABLE_tblSESSION, //Table to query
                columns,                                  //columns to return
                selection,                                //columns for the WHERE clause
                selectionArgs,                            //The values for the WHERE clause
                null,                                     //group the rows
                null,                                     //filter by row groups
                null);                                    //The sort order

        cSessionModel session = new cSessionModel();

        try {
            if (cursor.moveToFirst()) {
                do {
                    session.setSessionID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ID)));
                    session.setOwnerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OWNER_ID)));
                    session.setGroupBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_GROUP_BITS)));
                    session.setPermsBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PERMS_BITS)));
                    session.setStatusBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_STATUS_BITS)));
                    session.setName(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_NAME)));
                    session.setSurname(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_SURNAME)));
                    session.setDescription(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_DESCRIPTION)));
                    session.setEmail(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_EMAIL)));
                    session.setPassword(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_PASSWORD)));
                    session.setSalt(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_SALT)));
                    //session.setCreateDate(formatter.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_DATE))));

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

        return session;
    }
}
