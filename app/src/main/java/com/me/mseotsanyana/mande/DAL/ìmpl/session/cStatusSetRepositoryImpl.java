package com.me.mseotsanyana.mande.DAL.ìmpl.session;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.google.gson.Gson;
import com.me.mseotsanyana.mande.DAL.model.session.cStatusModel;
import com.me.mseotsanyana.mande.DAL.model.session.cStatusSetModel;
import com.me.mseotsanyana.mande.DAL.storage.database.cSQLDBHelper;
import com.me.mseotsanyana.mande.UTIL.cConstant;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by mseotsanyana on 2017/08/24.
 */

public class cStatusSetRepositoryImpl {
    private static SimpleDateFormat sdf = cConstant.FORMAT_DATE;
    private static String TAG = cStatusSetRepositoryImpl.class.getSimpleName();

    // an object of the database helper
    private cSQLDBHelper dbHelper;
    private cStatusRepositoryImpl statusRepository;

    public cStatusSetRepositoryImpl(Context context) {
        dbHelper = new cSQLDBHelper(context);
        statusRepository = new cStatusRepositoryImpl(context);

    }

    /*############################################# CREATE ACTIONS ############################################# */

    public boolean addStatusFromExcel(cStatusModel statusModel) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_ID, statusModel.getStatusID());
        cv.put(cSQLDBHelper.KEY_NAME, statusModel.getName());
        cv.put(cSQLDBHelper.KEY_DESCRIPTION, statusModel.getDescription());

        // insert outcome record
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblSTATUS, null, cv) < 0) {
                return false;
            }
        } catch (Exception e) {
            Log.d(TAG, "Exception in reading: " + e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }

    public boolean addStatus(cStatusModel statusModel) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_ID, statusModel.getStatusID());
        cv.put(cSQLDBHelper.KEY_OWNER_ID, statusModel.getOwnerID());
        cv.put(cSQLDBHelper.KEY_GROUP_BITS, statusModel.getStatusBITS());
        cv.put(cSQLDBHelper.KEY_PERMS_BITS, statusModel.getPermsBITS());
        cv.put(cSQLDBHelper.KEY_STATUS_BITS, statusModel.getStatusBITS());
        cv.put(cSQLDBHelper.KEY_NAME, statusModel.getName());
        cv.put(cSQLDBHelper.KEY_DESCRIPTION, statusModel.getDescription());
        //cv.put(cSQLDBHelper.KEY_DATE, formatter.format(statusModel.getCreateDate()));

        // insert outcome record
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblSTATUS, null, cv) < 0) {
                return false;
            }
        } catch (Exception ex) {
            Log.d("Exception in importing ", ex.getMessage().toString());
        }

        // close the database connection
        db.close();

        return true;
    }

    /*####################################### READ ACTIONS #######################################*/

    public Set<cStatusSetModel> getStatusSetModelSet(
            int userID, int orgID, int primaryStatusSet,
            int secondaryStatusSets, int operationBITS, int statusBITS) {

        Set<cStatusSetModel> statusSetModelSet = new HashSet<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + cSQLDBHelper.TABLE_tblSTATUS +
                " WHERE (((" + cSQLDBHelper.KEY_GROUP_BITS + " & ?) != 0) " +
                " OR ((" + cSQLDBHelper.KEY_OWNER_ID + " = ?) " +
                " AND ((" + cSQLDBHelper.KEY_PERMS_BITS + " & ?) != 0)) " +
                " OR (((" + cSQLDBHelper.KEY_GROUP_BITS + " & ?) != 0) " +
                " AND ((" + cSQLDBHelper.KEY_PERMS_BITS + " & ?) != 0)))";

        Cursor cursor = db.rawQuery(selectQuery, new String[]{
                String.valueOf(primaryStatusSet),
                String.valueOf(userID), String.valueOf(operationBITS),
                String.valueOf(secondaryStatusSets), String.valueOf(operationBITS)});

        try {
            if (cursor.moveToFirst()) {
                do {
                    cStatusSetModel statusSetModel = new cStatusSetModel();

                    statusSetModel.setStatusSetID(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ID)));
                    statusSetModel.setServerID(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_SERVER_ID)));
                    statusSetModel.setOwnerID(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OWNER_ID)));
                    statusSetModel.setOrgID(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ORG_ID)));
                    statusSetModel.setGroupBITS(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_GROUP_BITS)));
                    statusSetModel.setPermsBITS(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PERMS_BITS)));
                    statusSetModel.setStatusBITS(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_STATUS_BITS)));
                    statusSetModel.setName(
                            cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_NAME)));
                    statusSetModel.setDescription(
                            cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_DESCRIPTION)));
                    statusSetModel.setCreatedDate(Timestamp.valueOf(
                            cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_CREATED_DATE))));
                    statusSetModel.setModifiedDate(Timestamp.valueOf(
                            cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_MODIFIED_DATE))));
                    statusSetModel.setSyncedDate(Timestamp.valueOf(
                            cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_SYNCED_DATE))));

                    // populate statuses for a specific status set
                    //statusSetModel.setStatusModelSet(
                    //      statusRepository.getStatusList()getStatusesByID(statusSetModel.getStatusSetID()));

                    statusSetModelSet.add(statusSetModel);

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "ERROR READING ROLE SET:- " + e.getMessage());
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        // close the database connection
        db.close();

        return statusSetModelSet;
    }

    public cStatusSetModel getStatusSetByID(int statusSetID) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // construct a selection query
        String selectQuery = "SELECT * FROM " +
                cSQLDBHelper.TABLE_tblSTATUSSET + " WHERE " +
                cSQLDBHelper.KEY_ID + "= ?";

        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(statusSetID)});

        cStatusSetModel statusSetModel = null;

        try {
            if (cursor.moveToFirst()) {

                statusSetModel = new cStatusSetModel();

                statusSetModel.setStatusSetID(
                        cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ID)));
                statusSetModel.setServerID(
                        cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_SERVER_ID)));
                statusSetModel.setOwnerID(
                        cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OWNER_ID)));
                statusSetModel.setOwnerID(
                        cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ORG_ID)));
                statusSetModel.setGroupBITS(
                        cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_GROUP_BITS)));
                statusSetModel.setPermsBITS(
                        cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PERMS_BITS)));
                statusSetModel.setStatusBITS(
                        cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_STATUS_BITS)));
                statusSetModel.setName(
                        cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_NAME)));
                statusSetModel.setDescription(
                        cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_DESCRIPTION)));
                statusSetModel.setCreatedDate(Timestamp.valueOf(
                        cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_CREATED_DATE))));
                statusSetModel.setModifiedDate(Timestamp.valueOf(
                        cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_MODIFIED_DATE))));
                statusSetModel.setSyncedDate(Timestamp.valueOf(
                        cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_SYNCED_DATE))));
                /* populate status set */
                statusSetModel.setStatusModelSet(
                        getStatusByStatusSetID(statusSetModel.getStatusSetID()));

            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to get user groups from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        // close the database connection
        db.close();

        return statusSetModel;
    }


    public Set<cStatusModel> getStatusByStatusSetID(int statusSetID) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Set<cStatusModel> statusModelSet = new HashSet();

        // construct a selection query
        String selectQuery = "SELECT " + "status."+cSQLDBHelper.KEY_ID +", "+
                "status."+cSQLDBHelper.KEY_SERVER_ID + ", " +
                "status." +cSQLDBHelper.KEY_OWNER_ID + ", " +
                "status." +cSQLDBHelper.KEY_ORG_ID + ", " +
                "status."+cSQLDBHelper.KEY_GROUP_BITS + ", " +
                "status." +cSQLDBHelper.KEY_PERMS_BITS +", " +
                "status."+cSQLDBHelper.KEY_STATUS_BITS + ", " +
                "status." +cSQLDBHelper.KEY_NAME+ ", " +
                "status."+cSQLDBHelper.KEY_DESCRIPTION +", " +
                "status."+cSQLDBHelper.KEY_CREATED_DATE +", "+
                "status."+cSQLDBHelper.KEY_MODIFIED_DATE +", "+
                "status."+cSQLDBHelper.KEY_SYNCED_DATE +
                " FROM " +
                cSQLDBHelper.TABLE_tblSTATUS+ " status, " +
                cSQLDBHelper.TABLE_tblSTATUSSET+ " statusset, " +
                cSQLDBHelper.TABLE_tblSTATUSSET_STATUS+ " statusset_status " +
                " WHERE status."+cSQLDBHelper.KEY_ID + " = statusset_status."+
                cSQLDBHelper.KEY_STATUS_FK_ID +
                " AND statusset."+cSQLDBHelper.KEY_ID + " = statusset_status."+
                cSQLDBHelper.KEY_STATUSSET_FK_ID +
                " AND statusset."+cSQLDBHelper.KEY_ID +" = ?";

        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(statusSetID)});

        try {
            if (cursor.moveToFirst()) {
                do {

                    cStatusModel statusModel = new cStatusModel();

                    statusModel.setStatusID(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ID)));
                    statusModel.setServerID(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_SERVER_ID)));
                    statusModel.setOwnerID(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OWNER_ID)));
                    statusModel.setOrgID(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ORG_ID)));
                    statusModel.setGroupBITS(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_GROUP_BITS)));
                    statusModel.setPermsBITS(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PERMS_BITS)));
                    statusModel.setStatusBITS(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_STATUS_BITS)));
                    statusModel.setName(
                            cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_NAME)));
                    statusModel.setDescription(
                            cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_DESCRIPTION)));
                    statusModel.setCreatedDate(Timestamp.valueOf(
                            cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_CREATED_DATE))));
                    statusModel.setModifiedDate(Timestamp.valueOf(
                            cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_MODIFIED_DATE))));
                    statusModel.setSyncedDate(Timestamp.valueOf(
                            cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_SYNCED_DATE))));

                    // set of statuses of a status set
                    statusModelSet.add(statusModel);

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

        return statusModelSet;
    }


    public cStatusModel getStatusByID(int statusID) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        // construct a selection query
        String selectQuery = "SELECT * FROM " +
                cSQLDBHelper.TABLE_tblSTATUS + " WHERE " +
                cSQLDBHelper.KEY_ID + "= ?";

        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(statusID)});

        cStatusModel status = new cStatusModel();

        try {
            if (cursor.moveToFirst()) {
                do {
                    status.setStatusID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ID)));
                    status.setOwnerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OWNER_ID)));
                    status.setStatusBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_GROUP_BITS)));
                    status.setPermsBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PERMS_BITS)));
                    status.setStatusBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_STATUS_BITS)));
                    status.setName(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_NAME)));
                    status.setDescription(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_DESCRIPTION)));
                    //status.setCreateDate(formatter.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_DATE))));

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

        return status;
    }

    public List<cStatusModel> getStatusList() {

        List<cStatusModel> statusModelList = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + cSQLDBHelper.TABLE_tblSTATUS, null);

        try {
            if (cursor.moveToFirst()) {
                do {
                    cStatusModel status = new cStatusModel();

                    status.setStatusID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ID)));
                    status.setOwnerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OWNER_ID)));
                    status.setGroupBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_GROUP_BITS)));
                    status.setPermsBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PERMS_BITS)));
                    status.setStatusBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_STATUS_BITS)));
                    status.setName(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_NAME)));
                    status.setDescription(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_DESCRIPTION)));
                    //status.setCreateDate(formatter.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_DATE))));

                    statusModelList.add(status);

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

        return statusModelList;
    }

    /* ############################################# UPDATE ACTIONS ############################################# */

    /* ############################################# DELETE ACTIONS ############################################# */

    public boolean deleteStatuses() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblSTATUS, null, null);

        // close the database connection
        db.close();

        return result > -1;
    }

    /* ############################################# SYNC ACTIONS ############################################# */

}
