package com.me.mseotsanyana.mande.PPMER.DAL;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.me.mseotsanyana.mande.STORAGE.database.cSQLDBHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class cRiskDBA {
    // an object of the database helper
    private cSQLDBHelper dbHelper;

    private static final SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
    private static final String TAG = "dbHelper";

    public cRiskDBA(Context context) {
        dbHelper = new cSQLDBHelper(context);
    }


    public boolean deleteAllRisks() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_RISK, null, null);

        // close the database connection
        db.close();

        return result > -1;
    }

    public boolean addRisk(cRiskModel movModel) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_RISK_ID, movModel.getRiskID());
        cv.put(cSQLDBHelper.KEY_RISK_OWNER_ID, movModel.getOwnerID());
        cv.put(cSQLDBHelper.KEY_RISK_NAME, movModel.getRiskName());
        cv.put(cSQLDBHelper.KEY_RISK_DESCRIPTION, movModel.getRiskDescription());
        cv.put(cSQLDBHelper.KEY_RISK_DATE, formatter.format(movModel.getCreateDate()));

        // insert outcome record
        try {
            if (db.insert(cSQLDBHelper.TABLE_RISK, null, cv) < 0) {
                return false;
            }
        } catch (Exception ex) {
            Log.d("Exception in importing ", ex.getMessage().toString());
        }

        // close the database connection
        db.close();

        return true;
    }

    public cRiskModel getRiskByID(int riskID) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        // construct a selection query
        String selectQuery = "SELECT * FROM " +
                cSQLDBHelper.TABLE_RISK + " WHERE " +
                cSQLDBHelper.KEY_RISK_ID + "= ?";

        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(riskID)});

        cRiskModel risk = new cRiskModel();

        try {
            if (cursor.moveToFirst()) {
                do {
                    risk.setRiskID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_RISK_ID)));
                    risk.setOwnerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_RISK_OWNER_ID)));
                    risk.setRiskName(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_RISK_NAME)));
                    risk.setRiskDescription(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_RISK_DESCRIPTION)));
                    risk.setCreateDate(formatter.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_RISK_DATE))));

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

        return risk;
    }


    public List<cRiskModel> getRiskList() {

        List<cRiskModel> movModelList = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+ cSQLDBHelper.TABLE_RISK, null);

        try {
            if (cursor.moveToFirst()) {
                do {
                    cRiskModel risk = new cRiskModel();

                    risk.setRiskID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_RISK_ID)));
                    risk.setOwnerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_RISK_OWNER_ID)));
                    risk.setRiskName(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_RISK_NAME)));
                    risk.setRiskDescription(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_RISK_DESCRIPTION)));
                    risk.setCreateDate(formatter.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_RISK_DATE))));

                    movModelList.add(risk);

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

        return movModelList;
    }
}
