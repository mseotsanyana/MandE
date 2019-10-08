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

import static com.me.mseotsanyana.mande.PPMER.DAL.cSQLDBHelper.TABLE_tblVALUE;

/**
 * Created by mseotsanyana on 2017/05/25.
 */

public class cValueDBA {
    private static final SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
    private static final String TAG = "dbHelper";

    // an object of the database helper
    private cSQLDBHelper dbHelper;

    public cValueDBA(Context context) {
        dbHelper = new cSQLDBHelper(context);
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
        cv.put(cSQLDBHelper.KEY_NAME, valueModel.getValueName());
        //cv.put(cSQLDBHelper.KEY_DATE, formatter.format(valueModel.getCreateDate()));

        // insert value record
        long result = db.insert(TABLE_tblVALUE, null, cv);

        // close the database connection
        db.close();

        return result > -1;
    }

    public boolean addValueFromExcel(cValueModel valueModel) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_ID, valueModel.getValueID());
        cv.put(cSQLDBHelper.KEY_ORGANIZATION_FK_ID, valueModel.getOrganizationID());
        cv.put(cSQLDBHelper.KEY_OWNER_ID, valueModel.getOwnerID());
        cv.put(cSQLDBHelper.KEY_NAME, valueModel.getValueName());
        //cv.put(cSQLDBHelper.KEY_DATE, formatter.format(valueModel.getCreateDate()));

        // insert value record
        try {
            if (db.insert(TABLE_tblVALUE, null, cv) < 0) {
                return false;
            }
        } catch (Exception ex) {
            Log.d("Exception in importing ", ex.getMessage().toString());
        }

        // close the database connection
        db.close();

        return true;
    }

    public boolean deleteAllValues()
    {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(TABLE_tblVALUE, null, null);

        // close the database connection
        db.close();

        return result > -1;
    }

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
                    valueModel.setValueName(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_NAME)));
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
                    valueModel.setValueName(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_NAME)));
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
}
