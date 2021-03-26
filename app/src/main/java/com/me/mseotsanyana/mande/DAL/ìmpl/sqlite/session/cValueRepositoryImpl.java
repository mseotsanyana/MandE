package com.me.mseotsanyana.mande.DAL.ìmpl.sqlite.session;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.me.mseotsanyana.mande.BLL.model.session.cValueModel;
import com.me.mseotsanyana.mande.DAL.storage.database.cSQLDBHelper;
import com.me.mseotsanyana.mande.UTIL.cConstant;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static com.me.mseotsanyana.mande.DAL.storage.database.cSQLDBHelper.TABLE_tblVALUE;

/**
 * Created by mseotsanyana on 2017/05/25.
 */

public class cValueRepositoryImpl {
    private static SimpleDateFormat sdf = cConstant.FORMAT_DATE;
    private static String TAG = cValueRepositoryImpl.class.getSimpleName();

    // an object of the database helper
    private cSQLDBHelper dbHelper;

    public cValueRepositoryImpl(Context context) {
        dbHelper = new cSQLDBHelper(context);
    }

    /* ############################################# CREATE ACTIONS ############################################# */

    /**
     * Add organizational value from excel
     * @param valueModel
     * @return
     */
    public boolean addValueFromExcel(cValueModel valueModel) {
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
            Log.d(TAG,"Exception in reading: " + e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }


    public boolean addValue(cValueModel valueModel) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_ID, valueModel.getValueID());
        cv.put(cSQLDBHelper.KEY_ORGANIZATION_FK_ID, valueModel.getOrganizationID());
        cv.put(cSQLDBHelper.KEY_OWNER_ID, valueModel.getOwnerID());
        cv.put(cSQLDBHelper.KEY_NAME, valueModel.getName());

        // insert value record
        long result = db.insert(TABLE_tblVALUE, null, cv);

        // close the database connection
        db.close();

        return result > -1;
    }

    /* ############################################# READ ACTIONS ############################################# */

    public List<cValueModel> getListOfValues() {
        List<cValueModel> valueModelList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+ TABLE_tblVALUE, null);

        try {
            if (cursor.moveToFirst()) {
                do {
                    cValueModel valueModel = new cValueModel();
                    // populate value model object
                    valueModel.setValueID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ID)));
                    valueModel.setOrganizationID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ORGANIZATION_FK_ID)));
                    valueModel.setOwnerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OWNER_ID)));
                    valueModel.setName(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_NAME)));
                    //valueModel.setCreateDate(formatter.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_DATE))));
                    // add model value into the action_list
                    valueModelList.add(valueModel);

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to get values from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return valueModelList;
    }

    public List<cValueModel> getValuesByID(int organizationID) {
        List<cValueModel> valueModelList = new ArrayList<>();

        // open connection to read only
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // construct a selection query
        String selectQuery = "SELECT  * FROM " + TABLE_tblVALUE + " WHERE "
                + cSQLDBHelper.KEY_ORGANIZATION_FK_ID + " = ?";

        // open the cursor to be used to traverse the dataset
        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(organizationID)});

        // looping to a record which satisfies the condition and store in cLogFrameModel object
        try {
            if (cursor.moveToFirst()) {
                do {
                    cValueModel valueModel = new cValueModel();
                    // populate value model object
                    valueModel.setValueID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ID)));
                    valueModel.setOrganizationID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ORGANIZATION_FK_ID)));
                    valueModel.setOwnerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OWNER_ID)));
                    valueModel.setName(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_NAME)));
                    //valueModel.setCreateDate(formatter.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_DATE))));
                    // add model value into the action_list
                    valueModelList.add(valueModel);

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to get values from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        // close the cursor
        cursor.close();

        // close the database connection
        db.close();

        return valueModelList;
    }

    /* ############################################# UPDATE ACTIONS ############################################# */

    /* ############################################# DELETE ACTIONS ############################################# */

    public boolean deleteValues()
    {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(TABLE_tblVALUE, null, null);

        // close the database connection
        db.close();

        return result > -1;
    }

    /* ############################################# SYNC ACTIONS ############################################# */

}
