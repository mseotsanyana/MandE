package com.me.mseotsanyana.mande.BRBAC.DAL;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.me.mseotsanyana.mande.PPMER.DAL.cSQLDBHelper;
import com.me.mseotsanyana.mande.Util.cConstant;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class cSettingDBA {
    private static SimpleDateFormat sdf = cConstant.FORMAT_DATE;
    private static String TAG = cSettingDBA.class.getSimpleName();

    // an object of the database helper
    private cSQLDBHelper dbHelper;

    public cSettingDBA(Context context) {
        dbHelper = new cSQLDBHelper(context);
    }

    /* ############################################# CREATE ACTIONS ############################################# */

    /**
     * Add settings from an excel file
     * @param settingModel
     * @return Boolean
     */
    public boolean addSettingFromExcel(cSettingModel settingModel) {
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
            Log.d(TAG, "Exception in importing: "+e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }

    /* ############################################# READ ACTIONS ############################################# */

    /**
     * Read and filter the settings
     * @param userID
     * @param primaryRole
     * @param secondaryRoles
     * @param operationBITS
     * @param statusBITS
     * @return List
     */
    public List<cSettingModel> getSettings(
            int userID, int primaryRole, int secondaryRoles, int operationBITS, int statusBITS) {

        List<cSettingModel> settingModels = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String selectQuery = "SELECT * "+
                " FROM "+ cSQLDBHelper.TABLE_tblSETTING +
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
                    cSettingModel settingModel = new cSettingModel();

                    settingModel.setSettingID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ID)));
                    settingModel.setServerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_SERVER_ID)));
                    settingModel.setOwnerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OWNER_ID)));
                    settingModel.setOrgID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ORG_ID)));
                    settingModel.setGroupBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_GROUP_BITS)));
                    settingModel.setPermsBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PERMS_BITS)));
                    settingModel.setStatusBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_STATUS_BITS)));
                    settingModel.setName(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_NAME)));
                    settingModel.setDescription(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_DESCRIPTION)));
                    settingModel.setSettingValue(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_SETTING_VALUE)));
                    settingModel.setCreatedDate(sdf.parse(cursor.getString(cursor.getColumnIndex(
                            cSQLDBHelper.KEY_CREATED_DATE))));
                    settingModel.setModifiedDate(sdf.parse(cursor.getString(cursor.getColumnIndex(
                            cSQLDBHelper.KEY_MODIFIED_DATE))));
                    settingModel.setSyncedDate(sdf.parse(cursor.getString(cursor.getColumnIndex(
                            cSQLDBHelper.KEY_SYNCED_DATE))));

                    settingModels.add(settingModel);

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, " Error while reading: "+e.getMessage());
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        // close the database connection
        db.close();

        return settingModels;
    }


    /* ############################################# UPDATE ACTIONS ############################################# */



    /* ############################################# DELETE ACTIONS ############################################# */

    /**
     * Delete all settings
     * @return Boolean
     */
    public boolean deleteSettings() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblSETTING, null, null);

        // close the database connection
        db.close();

        return result > -1;
    }

    /* ############################################# SYNC ACTIONS ############################################# */


}
