package com.me.mseotsanyana.mande.Util;

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
 * Created by mseotsanyana on 2017/08/24.
 */

public class cTypeDBA {
    // an object of the database helper
    private cSQLDBHelper dbHelper;

    private static final SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
    private static final String TAG = "dbHelper";

    public cTypeDBA(Context context) {
        dbHelper = new cSQLDBHelper(context);
    }

    public boolean deleteAllTypes() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_TYPE, null, null);

        // close the database connection
        db.close();

        return result > -1;
    }

    public boolean addTypeFromExcel(cTypeModel typeModel) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_TYPE_ID, typeModel.getTypeID());
        cv.put(cSQLDBHelper.KEY_TYPE_NAME, typeModel.getName());
        cv.put(cSQLDBHelper.KEY_TYPE_DESCRIPTION, typeModel.getDescription());
        cv.put(cSQLDBHelper.KEY_TYPE_DATE, formatter.format(typeModel.getCreateDate()));

        // insert outcome record
        try {
            if (db.insert(cSQLDBHelper.TABLE_TYPE, null, cv) < 0) {
                return false;
            }
        } catch (Exception ex) {
            Log.d("Exception in importing ", ex.getMessage().toString());
        }

        // close the database connection
        db.close();

        return true;
    }

    public boolean addType(cTypeModel typeModel) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_TYPE_ID, typeModel.getTypeID());
        cv.put(cSQLDBHelper.KEY_TYPE_OWNER_ID, typeModel.getOwnerID());
        cv.put(cSQLDBHelper.KEY_TYPE_GROUP_BITS, typeModel.getGroupBITS());
        cv.put(cSQLDBHelper.KEY_TYPE_PERMS_BITS, typeModel.getPermsBITS());
        cv.put(cSQLDBHelper.KEY_TYPE_TYPE_BITS, typeModel.getTypeBITS());
        cv.put(cSQLDBHelper.KEY_TYPE_STATUS_BITS, typeModel.getStatusBITS());
        cv.put(cSQLDBHelper.KEY_TYPE_NAME, typeModel.getName());
        cv.put(cSQLDBHelper.KEY_TYPE_DESCRIPTION, typeModel.getDescription());
        cv.put(cSQLDBHelper.KEY_TYPE_DATE, formatter.format(typeModel.getCreateDate()));

        // insert outcome record
        try {
            if (db.insert(cSQLDBHelper.TABLE_TYPE, null, cv) < 0) {
                return false;
            }
        } catch (Exception ex) {
            Log.d("Exception in importing ", ex.getMessage().toString());
        }

        // close the database connection
        db.close();

        return true;
    }

    public cTypeModel getTypeByID(int typeID) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        // construct a selection query
        String selectQuery = "SELECT * FROM " +
                cSQLDBHelper.TABLE_TYPE + " WHERE " +
                cSQLDBHelper.KEY_TYPE_ID + "= ?";

        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(typeID)});

        cTypeModel type = new cTypeModel();

        try {
            if (cursor.moveToFirst()) {
                do {
                    type.setTypeID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_TYPE_ID)));
                    type.setOwnerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_TYPE_OWNER_ID)));
                    type.setGroupBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_TYPE_GROUP_BITS)));
                    type.setPermsBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_TYPE_PERMS_BITS)));
                    type.setTypeBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_TYPE_TYPE_BITS)));
                    type.setStatusBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_TYPE_STATUS_BITS)));
                    type.setName(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_TYPE_NAME)));
                    type.setDescription(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_TYPE_DESCRIPTION)));
                    type.setCreateDate(formatter.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_TYPE_DATE))));

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

        return type;
    }


    public List<cTypeModel> getTypeList() {

        List<cTypeModel> typeModelList = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+ cSQLDBHelper.TABLE_TYPE, null);

        try {
            if (cursor.moveToFirst()) {
                do {
                    cTypeModel type = new cTypeModel();

                    type.setTypeID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_TYPE_ID)));
                    type.setOwnerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_TYPE_OWNER_ID)));
                    type.setGroupBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_TYPE_GROUP_BITS)));
                    type.setPermsBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_TYPE_PERMS_BITS)));
                    type.setTypeBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_TYPE_TYPE_BITS)));
                    type.setStatusBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_TYPE_STATUS_BITS)));
                    type.setName(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_TYPE_NAME)));
                    type.setDescription(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_TYPE_DESCRIPTION)));
                    type.setCreateDate(formatter.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_TYPE_DATE))));

                    typeModelList.add(type);

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

        return typeModelList;
    }
}
