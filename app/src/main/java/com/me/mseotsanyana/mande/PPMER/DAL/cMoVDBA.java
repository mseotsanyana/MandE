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

public class cMoVDBA {
    // an object of the database helper
    private cSQLDBHelper dbHelper;

    private static final SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
    private static final String TAG = "dbHelper";

    public cMoVDBA(Context context) {
        dbHelper = new cSQLDBHelper(context);
    }


    public boolean deleteAllMoVs() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_MOV, null, null);

        // close the database connection
        db.close();

        return result > -1;
    }

    public boolean addMoV(cMoVModel movModel) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_MOV_ID, movModel.getMoVID());
        cv.put(cSQLDBHelper.KEY_MOV_OWNER_ID, movModel.getOwnerID());
        cv.put(cSQLDBHelper.KEY_MOV_NAME, movModel.getMoVName());
        cv.put(cSQLDBHelper.KEY_MOV_DESCRIPTION, movModel.getMoVDescription());
        cv.put(cSQLDBHelper.KEY_MOV_DATE, formatter.format(movModel.getCreateDate()));

        // insert outcome record
        try {
            if (db.insert(cSQLDBHelper.TABLE_MOV, null, cv) < 0) {
                return false;
            }
        } catch (Exception ex) {
            Log.d("Exception in importing ", ex.getMessage().toString());
        }

        // close the database connection
        db.close();

        return true;
    }

    public cMoVModel getMoVByID(int movID) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        // construct a selection query
        String selectQuery = "SELECT * FROM " +
                cSQLDBHelper.TABLE_MOV + " WHERE " +
                cSQLDBHelper.KEY_MOV_ID + "= ?";

        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(movID)});

        cMoVModel mov = new cMoVModel();

        try {
            if (cursor.moveToFirst()) {
                do {
                    mov.setMoVID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_MOV_ID)));
                    mov.setOwnerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_MOV_OWNER_ID)));
                    mov.setMoVName(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_MOV_NAME)));
                    mov.setMoVDescription(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_MOV_DESCRIPTION)));
                    mov.setCreateDate(formatter.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_MOV_DATE))));

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

        return mov;
    }


    public List<cMoVModel> getMoVList() {

        List<cMoVModel> movModelList = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+ cSQLDBHelper.TABLE_MOV, null);

        try {
            if (cursor.moveToFirst()) {
                do {
                    cMoVModel mov = new cMoVModel();

                    mov.setMoVID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_MOV_ID)));
                    mov.setOwnerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_MOV_OWNER_ID)));
                    mov.setMoVName(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_MOV_NAME)));
                    mov.setMoVDescription(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_MOV_DESCRIPTION)));
                    mov.setCreateDate(formatter.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_MOV_DATE))));

                    movModelList.add(mov);

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
