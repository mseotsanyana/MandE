package com.me.mseotsanyana.mande.DAL.ìmpl.session;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.me.mseotsanyana.mande.BLL.repository.session.iMenuRepository;
import com.me.mseotsanyana.mande.DAL.model.session.cMenuModel;
import com.me.mseotsanyana.mande.DAL.storage.database.cSQLDBHelper;
import com.me.mseotsanyana.mande.DAL.storage.preference.cBitwise;
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

public class cMenuRepositoryImpl implements iMenuRepository {
    private static SimpleDateFormat sdf = cConstant.FORMAT_DATE;
    private static String TAG = cMenuRepositoryImpl.class.getSimpleName();

    // an object of the database helper
    private cSQLDBHelper dbHelper;

    public cMenuRepositoryImpl(Context context) {
        dbHelper = new cSQLDBHelper(context);

    }

    /* ############################################# CREATE ACTIONS ############################################# */


    public boolean addMenuFromExcel(cMenuModel menuModel) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_ID, menuModel.getMenuID());
        cv.put(cSQLDBHelper.KEY_PARENT_FK_ID, menuModel.getParentID());
        cv.put(cSQLDBHelper.KEY_NAME, menuModel.getName());
        cv.put(cSQLDBHelper.KEY_DESCRIPTION, menuModel.getDescription());

        // insert outcome record
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblMENU, null, cv) < 0) {
                return false;
            }
        } catch (Exception e) {
            Log.d(TAG,"Exception in reading: " + e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }

    public boolean addMenu(cMenuModel menuModel) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_ID, menuModel.getMenuID());
        cv.put(cSQLDBHelper.KEY_SERVER_ID, menuModel.getServerID());
        cv.put(cSQLDBHelper.KEY_PARENT_FK_ID, menuModel.getParentID());
        cv.put(cSQLDBHelper.KEY_OWNER_ID, menuModel.getOwnerID());
        cv.put(cSQLDBHelper.KEY_ORG_ID, menuModel.getOrgID());
        cv.put(cSQLDBHelper.KEY_GROUP_BITS, menuModel.getGroupBITS());
        cv.put(cSQLDBHelper.KEY_PERMS_BITS, menuModel.getPermsBITS());
        cv.put(cSQLDBHelper.KEY_STATUS_BITS, menuModel.getStatusBITS());
        cv.put(cSQLDBHelper.KEY_NAME, menuModel.getName());
        cv.put(cSQLDBHelper.KEY_DESCRIPTION, menuModel.getDescription());

        // insert outcome record
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblMENU, null, cv) < 0) {
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

    public Set<cMenuModel> getMenuModelSet(long userID, int primaryRoleBITS,
                                           int secondaryRoleBITS, int statusBITS) {
        Set<cMenuModel> menuModelSet = new HashSet<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String selectQuery = "SELECT * FROM "+ cSQLDBHelper.TABLE_tblMENU +
                " WHERE (" + //((" + cSQLDBHelper.KEY_ID + " = ?) AND " +
                /* read access permissions */
                /* organization creator is always allowed to do everything (i.e., where:
                   userID = orgID) */
                " ((" + cSQLDBHelper.KEY_ORG_ID + " = ?) " +
                /* owner permission */
                " OR ((((" + cSQLDBHelper.KEY_OWNER_ID + " = ?) " +
                " AND ((" + cSQLDBHelper.KEY_PERMS_BITS + " & " + cBitwise.OWNER_READ + ") != 0))" +
                /* group (owner/primary organization) permission */
                " OR (((" + cSQLDBHelper.KEY_GROUP_BITS + " & ?) != 0) " +
                " AND ((" + cSQLDBHelper.KEY_PERMS_BITS + " & " + cBitwise.GROUP_READ + ") != 0))" +
                /* other (secondary organizations) permission */
                " OR (((" + cSQLDBHelper.KEY_GROUP_BITS + " & ?) != 0) " +
                " AND ((" + cSQLDBHelper.KEY_PERMS_BITS + " & " + cBitwise.OTHER_READ + ") != 0)))" +
                /* owner, group and other permissions allowed when the statuses hold */
                " AND ((" + cSQLDBHelper.KEY_STATUS_BITS + " = 0) " +
                " OR ((" + cSQLDBHelper.KEY_STATUS_BITS + " & ?) != 0)))))";

        Cursor cursor = db.rawQuery(selectQuery, new String[]{
                String.valueOf(userID),           /* access due to organization creator */
                String.valueOf(userID),           /* access due to record owner */
                String.valueOf(primaryRoleBITS),  /* access due to membership in primary role */
                String.valueOf(secondaryRoleBITS),/* access due to membership in secondary role */
                String.valueOf(statusBITS)});     /* access due to assigned statuses */

        try {
            if (cursor.moveToFirst()) {
                do {
                    cMenuModel menu = new cMenuModel();

                    menu.setMenuID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ID)));
                    menu.setParentID(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PARENT_FK_ID)));
                    menu.setServerID(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_SERVER_ID)));
                    menu.setOwnerID(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OWNER_ID)));
                    menu.setOrgID(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ORG_ID)));
                    menu.setGroupBITS(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_GROUP_BITS)));
                    menu.setPermsBITS(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PERMS_BITS)));
                    menu.setStatusBITS(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_STATUS_BITS)));
                    menu.setName(
                            cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_NAME)));
                    menu.setDescription(
                            cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_DESCRIPTION)));
                    menu.setCreatedDate(Timestamp.valueOf(
                            cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_CREATED_DATE))));
                    menu.setModifiedDate(Timestamp.valueOf(
                            cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_MODIFIED_DATE))));
                    menu.setSyncedDate(Timestamp.valueOf(
                            cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_SYNCED_DATE))));

                    // populate menu for a specific parent menu
                    menu.setMenuModelSet(getSubsetMenuByID(menu.getMenuID()));

                    menuModelSet.add(menu);

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "ERROR READING ROLE SET:- "+e.getMessage());
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        // close the database connection
        db.close();

        return menuModelSet;
    }

    public Set<cMenuModel> getSubMenuByID(int menuID) {

        Set<cMenuModel> menuModelSet = new HashSet<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + cSQLDBHelper.TABLE_tblMENU +
                " WHERE ((" + cSQLDBHelper.KEY_PARENT_FK_ID + " = ?) AND " +
                /* read access permissions */
                /* organization creator is always allowed to do everything (i.e., where:
                   userID = orgID) */
                " ((" + cSQLDBHelper.KEY_ORG_ID + " = ?) " +
                /* owner permission */
                " OR ((((" + cSQLDBHelper.KEY_OWNER_ID + " = ?) " +
                " AND ((" + cSQLDBHelper.KEY_PERMS_BITS + " & " + cBitwise.OWNER_READ + ") != 0))" +
                /* group (owner/primary organization) permission */
                " OR (((" + cSQLDBHelper.KEY_GROUP_BITS + " & ?) != 0) " +
                " AND ((" + cSQLDBHelper.KEY_PERMS_BITS + " & " + cBitwise.GROUP_READ + ") != 0))" +
                /* other (secondary organizations) permission */
                " OR (((" + cSQLDBHelper.KEY_GROUP_BITS + " & ?) != 0) " +
                " AND ((" + cSQLDBHelper.KEY_PERMS_BITS + " & " + cBitwise.OTHER_READ + ") != 0)))" +
                /* owner, group and other permissions allowed when the statuses hold */
                " AND ((" + cSQLDBHelper.KEY_STATUS_BITS + " = 0) " +
                " OR ((" + cSQLDBHelper.KEY_STATUS_BITS + " & ?) != 0)))))";

        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(menuID)});

        try {
            if (cursor.moveToFirst()) {
                do {
                    cMenuModel menu = new cMenuModel();

                    menu.setMenuID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ID)));
                    menu.setParentID(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PARENT_FK_ID)));
                    menu.setOwnerID(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OWNER_ID)));
                    menu.setGroupBITS(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_GROUP_BITS)));
                    menu.setPermsBITS(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PERMS_BITS)));
                    menu.setStatusBITS(
                            cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_STATUS_BITS)));
                    menu.setName(
                            cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_NAME)));
                    menu.setDescription(
                            cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_DESCRIPTION)));
                    menu.setCreatedDate(Timestamp.valueOf(
                            cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_CREATED_DATE))));
                    menu.setModifiedDate(Timestamp.valueOf(
                            cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_MODIFIED_DATE))));
                    menu.setSyncedDate(Timestamp.valueOf(
                            cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_SYNCED_DATE))));

                    menuModelSet.add(menu);

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

        return menuModelSet;
    }

    public List<cMenuModel> getMenuList() {

        List<cMenuModel> menuModelList = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+ cSQLDBHelper.TABLE_tblMENU, null);

        try {
            if (cursor.moveToFirst()) {
                do {
                    cMenuModel menu = new cMenuModel();

                    menu.setMenuID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ID)));
                    menu.setParentID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PARENT_FK_ID)));
                    menu.setOwnerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OWNER_ID)));
                    menu.setGroupBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_GROUP_BITS)));
                    menu.setPermsBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PERMS_BITS)));
                    menu.setStatusBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_STATUS_BITS)));
                    menu.setName(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_NAME)));
                    menu.setDescription(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_DESCRIPTION)));
                    //menu.setCreateDate(formatter.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_DATE))));

                    menuModelList.add(menu);

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

        return menuModelList;
    }

    /**
     * Read subset menu items by menu ID
     * @param menuID
     * @return
     */
    public Set<cMenuModel> getSubsetMenuByID(int menuID) {

        Set<cMenuModel> menuModelSet = new HashSet<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String selectQuery = "SELECT * FROM " +
                cSQLDBHelper.TABLE_tblMENU + " WHERE " +
                cSQLDBHelper.KEY_PARENT_FK_ID + "= ?";

        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(menuID)});

        try {
            if (cursor.moveToFirst()) {
                do {
                    cMenuModel menu = new cMenuModel();

                    menu.setMenuID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ID)));
                    menu.setParentID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PARENT_FK_ID)));
                    menu.setServerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_SERVER_ID)));
                    menu.setOwnerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OWNER_ID)));
                    menu.setOrgID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ORG_ID)));
                    menu.setGroupBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_GROUP_BITS)));
                    menu.setPermsBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PERMS_BITS)));
                    menu.setStatusBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_STATUS_BITS)));
                    menu.setName(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_NAME)));
                    menu.setDescription(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_DESCRIPTION)));
                    menu.setCreatedDate(
                            Timestamp.valueOf(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_CREATED_DATE))));
                    menu.setModifiedDate(
                            Timestamp.valueOf(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_MODIFIED_DATE))));
                    menu.setSyncedDate(
                            Timestamp.valueOf(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_SYNCED_DATE))));

                    menuModelSet.add(menu);

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

        return menuModelSet;
    }

    public cMenuModel getMenuByID(int menuID) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        // construct a selection query
        String selectQuery = "SELECT * FROM " +
                cSQLDBHelper.TABLE_tblMENU + " WHERE " +
                cSQLDBHelper.KEY_ID + "= ?";

        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(menuID)});

        cMenuModel menu = new cMenuModel();

        try {
            if (cursor.moveToFirst()) {
                do {
                    menu.setMenuID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ID)));
                    menu.setParentID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PARENT_FK_ID)));
                    menu.setOwnerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OWNER_ID)));
                    menu.setGroupBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_GROUP_BITS)));
                    menu.setPermsBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PERMS_BITS)));
                    menu.setStatusBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_STATUS_BITS)));
                    menu.setName(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_NAME)));
                    menu.setDescription(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_DESCRIPTION)));
                    //menu.setCreateDate(formatter.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_DATE))));

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

        return menu;
    }

    /* ############################################# UPDATE ACTIONS ############################################# */


    /* ############################################# DELETE ACTIONS ############################################# */

    public boolean deleteMenuItems() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblMENU, null, null);

        // close the database connection
        db.close();

        return result > -1;
    }

    /* ############################################# SYNCED ACTIONS ############################################# */

}
