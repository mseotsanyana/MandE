package com.me.mseotsanyana.mande.DAL.ìmpl.sqlite.session;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.google.gson.Gson;
import com.me.mseotsanyana.mande.BLL.repository.session.iManageSessionRepository;
import com.me.mseotsanyana.mande.BLL.model.session.cSessionModel;
import com.me.mseotsanyana.mande.DAL.storage.database.cSQLDBHelper;
import com.me.mseotsanyana.mande.DAL.storage.preference.cSharedPreference;
import com.me.mseotsanyana.mande.UTIL.cConstant;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mseotsanyana on 2016/10/23.
 */
public class cSessionRepositoryImpl implements iManageSessionRepository {
    private static SimpleDateFormat sdf = cConstant.FORMAT_DATE;
    private static String TAG = cSessionRepositoryImpl.class.getSimpleName();

    // an object of the database helper
    private cSQLDBHelper dbHelper;
    /* an object of the shared preference */
    private cSharedPreference preference;

    private int userID;
    private int orgID;
    private int primaryRoleBIT;
    private int secondaryRoleBITS;

    Gson gson = new Gson();

    public cSessionRepositoryImpl(Context context) {
        dbHelper   = new cSQLDBHelper(context);
        preference = new cSharedPreference(context);
    }

    /*##################################### PREFERENCE ACTIONS ###################################*/


    @Override
    public boolean clearSharedPreferences() {
        return false;
    }

    @Override
    public boolean setSharedPreferences() {
        return false;
    }

    /* ############################################# CREATE ACTIONS ############################################# */

    public boolean addSessionFromExcel(cSessionModel sessionModel) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_ID, sessionModel.getSessionID());
        cv.put(cSQLDBHelper.KEY_USER_FK_ID, sessionModel.getUserID());

        // insert outcome record
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblSESSION, null, cv) < 0) {
                return false;
            }
        } catch (Exception e) {
            Log.d(TAG, "Exception in importing: " + e.getMessage().toString());
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

    /* ############################################# READ ACTIONS ############################################# */

    public List<cSessionModel> getSessionList(
            int userID, int primaryRole, int secondaryRoles, int operationBITS, int statusBITS) {

        List<cSessionModel> sessionModelList = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + cSQLDBHelper.TABLE_tblSESSION +
                " WHERE (((" + cSQLDBHelper.KEY_GROUP_BITS + " & ?) != 0) " +
                " OR ((" + cSQLDBHelper.KEY_OWNER_ID + " = ?) " +
                " AND ((" + cSQLDBHelper.KEY_PERMS_BITS + " & ?) != 0)) " +
                " OR (((" + cSQLDBHelper.KEY_GROUP_BITS + " & ?) != 0) " +
                " AND ((" + cSQLDBHelper.KEY_PERMS_BITS + " & ?) != 0)))";

        Cursor cursor = db.rawQuery(selectQuery, new String[]{
                String.valueOf(primaryRole),
                String.valueOf(userID), String.valueOf(operationBITS),
                String.valueOf(secondaryRoles), String.valueOf(operationBITS)});

        try {
            if (cursor.moveToFirst()) {
                do {
                    cSessionModel session = new cSessionModel();

                    session.setSessionID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ID)));
                    session.setUserID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_USER_FK_ID)));
                    session.setOwnerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OWNER_ID)));
                    session.setGroupBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_GROUP_BITS)));
                    session.setPermsBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PERMS_BITS)));
                    session.setStatusBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_STATUS_BITS)));
                    session.setCreatedDate(sdf.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_CREATED_DATE))));
                    session.setModifiedDate(sdf.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_MODIFIED_DATE))));
                    session.setSyncedDate(sdf.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_SYNCED_DATE))));

                    // construct a user
                    //session.setUserModel(new cUserModel(userDBA.getUserByID(session.getUserID())));

                    // populate roles for a specific session
                    //session.setRoleModelSet(userDBA.getRolesByUserID(session.getUserID()));

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
                cSQLDBHelper.KEY_EMAIL + " = ? " + " AND " + cSQLDBHelper.KEY_PASSWORD + " = ?";

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

    /* ############################################# UPDATE ACTIONS ############################################# */

    /* ############################################# DELETE ACTIONS ############################################# */


    public boolean deleteSessions() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblSESSION, null, null);

        // close the database connection
        db.close();

        return result > -1;
    }

    /* ############################################# SYNCED ACTIONS ############################################# */

}
