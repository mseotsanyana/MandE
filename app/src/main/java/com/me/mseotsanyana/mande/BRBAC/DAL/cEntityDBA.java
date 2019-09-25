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
 * Created by mseotsanyana on 2017/08/24.
 */

public class cEntityDBA {
    // an object of the database helper
    private cSQLDBHelper dbHelper;

    private static final SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
    private static final String TAG = "dbHelper";

    public cEntityDBA(Context context) {
        dbHelper = new cSQLDBHelper(context);
    }

    public boolean deleteAllEntities() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_ENTITY, null, null);

        // close the database connection
        db.close();

        return result > -1;
    }

    public boolean addEntityFromExcel(cEntityModel objectModel) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_ENTITY_ID, objectModel.getEntityID());
        cv.put(cSQLDBHelper.KEY_ENTITY_TYPE_ID, objectModel.getTypeID());
        cv.put(cSQLDBHelper.KEY_ENTITY_NAME, objectModel.getName());
        cv.put(cSQLDBHelper.KEY_ENTITY_DESCRIPTION, objectModel.getDescription());
        //cv.put(cSQLDBHelper.KEY_ENTITY_DATE, formatter.format(objectModel.getCreateDate()));

        // insert outcome record
        try {
            if (db.insert(cSQLDBHelper.TABLE_ENTITY, null, cv) < 0) {
                return false;
            }
        } catch (Exception ex) {
            Log.d("Exception in importing ", ex.getMessage().toString());
        }

        // close the database connection
        db.close();

        return true;
    }

    public boolean addEntity(cEntityModel objectModel) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_ENTITY_ID, objectModel.getEntityID());
        cv.put(cSQLDBHelper.KEY_ENTITY_OWNER_ID, objectModel.getOwnerID());
        cv.put(cSQLDBHelper.KEY_ENTITY_GROUP_BITS, objectModel.getGroupBITS());
        cv.put(cSQLDBHelper.KEY_ENTITY_PERMS_BITS, objectModel.getPermsBITS());
        cv.put(cSQLDBHelper.KEY_ENTITY_STATUS_BITS, objectModel.getStatusBITS());
        cv.put(cSQLDBHelper.KEY_ENTITY_NAME, objectModel.getName());
        cv.put(cSQLDBHelper.KEY_ENTITY_DESCRIPTION, objectModel.getDescription());
        //cv.put(cSQLDBHelper.KEY_ENTITY_DATE, formatter.format(objectModel.getCreateDate()));

        // insert outcome record
        try {
            if (db.insert(cSQLDBHelper.TABLE_ENTITY, null, cv) < 0) {
                return false;
            }
        } catch (Exception ex) {
            Log.d("Exception in importing ", ex.getMessage().toString());
        }

        // close the database connection
        db.close();

        return true;
    }

    public cEntityModel getEntityByID(int entityID, int typeID) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        // construct a selection query
        String selectQuery = "SELECT * FROM " +
                cSQLDBHelper.TABLE_ENTITY + " WHERE " +
                cSQLDBHelper.KEY_ENTITY_ID + " = ? AND "+ cSQLDBHelper.KEY_ENTITY_TYPE_ID + " = ?";

        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(entityID), String.valueOf(typeID)});

        cEntityModel entity = new cEntityModel();

        try {
            if (cursor.moveToFirst()) {
                do {
                    entity.setEntityID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ENTITY_ID)));
                    entity.setTypeID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ENTITY_TYPE_ID)));
                    entity.setOwnerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ENTITY_OWNER_ID)));
                    entity.setGroupBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ENTITY_GROUP_BITS)));
                    entity.setPermsBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ENTITY_PERMS_BITS)));
                    entity.setStatusBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ENTITY_STATUS_BITS)));
                    entity.setName(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_ENTITY_NAME)));
                    entity.setDescription(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_ENTITY_DESCRIPTION)));
                    //entity.setCreateDate(formatter.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_ENTITY_DATE))));

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

        return entity;
    }

    public String getEntityNameByID(int entityID) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        // construct a selection query
        String selectQuery = "SELECT * FROM " +
                cSQLDBHelper.TABLE_ENTITY + " WHERE " +
                cSQLDBHelper.KEY_ENTITY_ID + "= ?";

        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(entityID)});

        String entityName = null;

        try {
            if (cursor.moveToFirst()) {
                do {
                    entityName = cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_ENTITY_NAME));
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

        return entityName;
    }

    public List<cEntityModel> getEntityList() {

        List<cEntityModel> objectModelList = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+ cSQLDBHelper.TABLE_ENTITY, null);

        try {
            if (cursor.moveToFirst()) {
                do {
                    cEntityModel object = new cEntityModel();

                    object.setEntityID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ENTITY_ID)));
                    object.setTypeID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ENTITY_TYPE_ID)));
                    object.setOwnerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ENTITY_OWNER_ID)));
                    object.setGroupBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ENTITY_GROUP_BITS)));
                    object.setPermsBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ENTITY_PERMS_BITS)));
                    object.setStatusBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ENTITY_STATUS_BITS)));
                    object.setName(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_ENTITY_NAME)));
                    object.setDescription(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_ENTITY_DESCRIPTION)));
                    //object.setCreateDate(formatter.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_ENTITY_DATE))));

                    objectModelList.add(object);

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

        return objectModelList;
    }
}

