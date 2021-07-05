package com.me.mseotsanyana.mande.DAL.ìmpl.sqlite.session;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.me.mseotsanyana.mande.BLL.model.session.cEntityModel;
import com.me.mseotsanyana.mande.DAL.storage.database.cSQLDBHelper;
import com.me.mseotsanyana.mande.UTIL.cConstant;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mseotsanyana on 2017/08/24.
 */

public class cEntityRepositoryImpl {
    private static SimpleDateFormat sdf = cConstant.FORMAT_DATE;
    private static String TAG = cEntityRepositoryImpl.class.getSimpleName();

    // an object of the database helper
    private cSQLDBHelper dbHelper;

    public cEntityRepositoryImpl(Context context) {
        dbHelper = new cSQLDBHelper(context);
    }

    /* ############################################# CREATE ACTIONS ############################################# */

    public boolean addEntityFromExcel(cEntityModel entityModel) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_ID, entityModel.getEntityServerID());
//        cv.put(cSQLDBHelper.KEY_ENTITY_TYPE_ID, entityModel.getModuleID());
//        cv.put(cSQLDBHelper.KEY_NAME, entityModel.getName());
//        cv.put(cSQLDBHelper.KEY_DESCRIPTION, entityModel.getDescription());

        // insert outcome record
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblENTITY, null, cv) < 0) {
                return false;
            }
        } catch (Exception e) {
            Log.d(TAG,"Exception in adding: "+e.getMessage());
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
        cv.put(cSQLDBHelper.KEY_ID, objectModel.getEntityServerID());
//        cv.put(cSQLDBHelper.KEY_ENTITY_TYPE_FK_ID, objectModel.getModuleID());
//        cv.put(cSQLDBHelper.KEY_SERVER_ID, objectModel.getServerID());
//        cv.put(cSQLDBHelper.KEY_OWNER_ID, objectModel.getOwnerID());
//        cv.put(cSQLDBHelper.KEY_ORG_ID, objectModel.getOrgID());
//        cv.put(cSQLDBHelper.KEY_GROUP_BITS, objectModel.getGroupBITS());
//        cv.put(cSQLDBHelper.KEY_PERMS_BITS, objectModel.getPermsBITS());
//        cv.put(cSQLDBHelper.KEY_STATUS_BITS, objectModel.getStatusBITS());
//        cv.put(cSQLDBHelper.KEY_NAME, objectModel.getName());
//        cv.put(cSQLDBHelper.KEY_DESCRIPTION, objectModel.getDescription());

        // insert outcome record
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblENTITY, null, cv) < 0) {
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

    /**
     * Read entity by ID
     * @param entityID
     * @param entityTypeID
     * @return
     */
    public cEntityModel getEntityByID(int entityID, int entityTypeID) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        // construct a selection query
        String selectQuery = "SELECT * FROM " +
                cSQLDBHelper.TABLE_tblENTITY + " WHERE " +
                cSQLDBHelper.KEY_ID + " = ? AND "+ cSQLDBHelper.KEY_ENTITY_TYPE_ID + " = ?";

        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(entityID),
                String.valueOf(entityTypeID)});

        cEntityModel entity = null;//new cEntityModel(entityID, moduleID, operationStatusMap, unixPermList);

        try {
            if (cursor.moveToFirst()) {
                do {
//                    entity.setEntityID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ID)));
//                    entity.setModuleID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ENTITY_TYPE_ID)));
//                    entity.setServerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_SERVER_ID)));
//                    entity.setOwnerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OWNER_ID)));
//                    entity.setOrgID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ORG_ID)));
//                    entity.setGroupBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_GROUP_BITS)));
//                    entity.setPermsBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PERMS_BITS)));
//                    entity.setStatusBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_STATUS_BITS)));
//                    entity.setName(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_NAME)));
//                    entity.setDescription(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_DESCRIPTION)));
//                    entity.setCreatedDate(Timestamp.valueOf
//                            (cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_CREATED_DATE))));
//                    entity.setModifiedDate(
//                            Timestamp.valueOf(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_MODIFIED_DATE))));
//                    entity.setSyncedDate(
//                            Timestamp.valueOf(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_SYNCED_DATE))));
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

        return entity;
    }

    public String getEntityNameByID(int entityID) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        // construct a selection query
        String selectQuery = "SELECT * FROM " +
                cSQLDBHelper.TABLE_tblENTITY + " WHERE " +
                cSQLDBHelper.KEY_ID + "= ?";

        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(entityID)});

        String entityName = null;

        try {
            if (cursor.moveToFirst()) {
                do {
                    entityName = cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_NAME));
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
        Cursor cursor = db.rawQuery("SELECT * FROM "+ cSQLDBHelper.TABLE_tblENTITY, null);

        try {
            if (cursor.moveToFirst()) {
                do {
                    cEntityModel entity = null;//new cEntityModel(entityID, moduleID, operationStatusMap, unixPermList);

//                    entity.setEntityID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ID)));
//                    entity.setModuleID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ENTITY_TYPE_ID)));
//                    entity.setOwnerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OWNER_ID)));
//                    entity.setGroupBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_GROUP_BITS)));
//                    entity.setPermsBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PERMS_BITS)));
//                    entity.setStatusBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_STATUS_BITS)));
//                    entity.setName(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_NAME)));
//                    entity.setDescription(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_DESCRIPTION)));
//                    entity.setCreatedDate(Timestamp.valueOf(
//                            cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_CREATED_DATE))));
//                    entity.setModifiedDate(Timestamp.valueOf(
//                            cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_MODIFIED_DATE))));
//                    entity.setSyncedDate(Timestamp.valueOf(
//                            cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_SYNCED_DATE))));
//                    objectModelList.add(entity);

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

    /* ############################################# UPDATE ACTIONS ############################################# */


    /* ############################################# DELETE ACTIONS ############################################# */

    public boolean deleteEntities() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblENTITY, null, null);

        // close the database connection
        db.close();

        return result > -1;
    }

    /* ############################################# SYNCED ACTIONS ############################################# */


}

