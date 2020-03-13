package com.me.mseotsanyana.mande.UTIL.DAL;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.me.mseotsanyana.mande.DAL.storage.database.cSQLDBHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class cDateDBA {
    // an object of the database helper
    private cSQLDBHelper dbHelper;

    private static final SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
    private static final String TAG = "dbHelper";

    public cDateDBA(Context context) {
        dbHelper = new cSQLDBHelper(context);
    }

    public boolean deleteAllDates() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_DATE, null, null);

        // close the database connection
        db.close();

        return result > -1;
    }

    public boolean addDate(cDateModel dateModel) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_DATE_ID, dateModel.getDateID());
        cv.put(cSQLDBHelper.KEY_DATE_OWNER_ID, dateModel.getOwnerID());
        cv.put(cSQLDBHelper.KEY_DATE_YEAR, dateModel.getYear());
        cv.put(cSQLDBHelper.KEY_DATE_MONTH, dateModel.getMonth());
        cv.put(cSQLDBHelper.KEY_DATE_QUARTER, dateModel.getQuarter());
        cv.put(cSQLDBHelper.KEY_DATE_DAY_MONTH, dateModel.getDayMonth());
        cv.put(cSQLDBHelper.KEY_DATE_DAY_WEEK, dateModel.getDayWeek());
        cv.put(cSQLDBHelper.KEY_DATE_DAY_YEAR, dateModel.getDayYear());
        cv.put(cSQLDBHelper.KEY_DATE_DAY_WEEK_MONTH, dateModel.getDayWeekMonth());
        cv.put(cSQLDBHelper.KEY_DATE_WEEK_YEAR, dateModel.getWeekYear());
        cv.put(cSQLDBHelper.KEY_DATE_WEEK_MONTH, dateModel.getWeekMonth());
        cv.put(cSQLDBHelper.KEY_DATE_NAME_QUARTER, dateModel.getNameQuarter());
        cv.put(cSQLDBHelper.KEY_DATE_NAME_MONTH, dateModel.getNameMonth());
        cv.put(cSQLDBHelper.KEY_DATE_NAME_WEEK_DAY, dateModel.getNameWeekDay());
        cv.put(cSQLDBHelper.KEY_DATE_DATE, formatter.format(dateModel.getCreateDate()));

        // insert date dim record
        try {
            if (db.insert(cSQLDBHelper.TABLE_DATE, null, cv) < 0) {
                return false;
            }
        } catch (Exception ex) {
            Log.d("Exception in importing ", ex.getMessage().toString());
        }

        // close the database connection
        db.close();

        return true;
    }

    public cDateModel getDateByID(int dateID) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        // construct a selection query
        String selectQuery = "SELECT * FROM " +
                cSQLDBHelper.TABLE_DATE + " WHERE " +
                cSQLDBHelper.KEY_DATE_ID + "= ?";

        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(dateID)});

        cDateModel dateModel = new cDateModel();

        try {
            if (cursor.moveToFirst()) {
                do {
                    dateModel.setDateID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_DATE_ID)));
                    dateModel.setOwnerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_DATE_OWNER_ID)));
                    dateModel.setYear(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_DATE_YEAR)));
                    dateModel.setMonth(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_DATE_MONTH)));
                    dateModel.setQuarter(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_DATE_QUARTER)));
                    dateModel.setDayMonth(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_DATE_DAY_MONTH)));
                    dateModel.setDayWeek(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_DATE_DAY_WEEK)));
                    dateModel.setDayYear(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_DATE_DAY_YEAR)));
                    dateModel.setDayWeekMonth(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_DATE_DAY_WEEK_MONTH)));
                    dateModel.setWeekYear(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_DATE_WEEK_YEAR)));
                    dateModel.setWeekMonth(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_DATE_WEEK_MONTH)));
                    dateModel.setNameQuarter(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_DATE_NAME_QUARTER)));
                    dateModel.setNameMonth(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_DATE_NAME_MONTH)));
                    dateModel.setNameWeekDay(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_DATE_NAME_WEEK_DAY)));
                    dateModel.setCreateDate(formatter.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_DATE_DATE))));

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

        return dateModel;
    }

    public List<cDateModel> getDateList() {

        List<cDateModel> dateModelList = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+ cSQLDBHelper.TABLE_DATE, null);

        try {
            if (cursor.moveToFirst()) {
                do {
                    cDateModel dateModel = new cDateModel();

                    dateModel.setDateID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_DATE_ID)));
                    dateModel.setOwnerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_DATE_OWNER_ID)));
                    dateModel.setYear(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_DATE_YEAR)));
                    dateModel.setMonth(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_DATE_MONTH)));
                    dateModel.setQuarter(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_DATE_QUARTER)));
                    dateModel.setDayMonth(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_DATE_DAY_MONTH)));
                    dateModel.setDayWeek(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_DATE_DAY_WEEK)));
                    dateModel.setDayYear(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_DATE_DAY_YEAR)));
                    dateModel.setDayWeekMonth(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_DATE_DAY_WEEK_MONTH)));
                    dateModel.setWeekYear(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_DATE_WEEK_YEAR)));
                    dateModel.setWeekMonth(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_DATE_WEEK_MONTH)));
                    dateModel.setNameQuarter(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_DATE_NAME_QUARTER)));
                    dateModel.setNameMonth(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_DATE_NAME_MONTH)));
                    dateModel.setNameWeekDay(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_DATE_NAME_WEEK_DAY)));
                    dateModel.setCreateDate(formatter.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_DATE_DATE))));
                    dateModelList.add(dateModel);

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to get date dim from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        // close the database connection
        db.close();

        return dateModelList;
    }
}
