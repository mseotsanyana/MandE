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

public class cMenuDBA {
    // an object of the database helper
    private cSQLDBHelper dbHelper;

    private static final SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
    private static final String TAG = "dbHelper";

    public cMenuDBA(Context context) {
        dbHelper = new cSQLDBHelper(context);
    }

    public boolean deleteAllMenuItems() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_MENU, null, null);

        // close the database connection
        db.close();

        return result > -1;
    }

    public boolean addMenuFromExcel(cMenuModel menuModel) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_MENU_ID, menuModel.getMenuID());
        cv.put(cSQLDBHelper.KEY_MENU_PARENT_ID, menuModel.getParentID());
        cv.put(cSQLDBHelper.KEY_MENU_NAME, menuModel.getName());
        cv.put(cSQLDBHelper.KEY_MENU_DESCRIPTION, menuModel.getDescription());
        //cv.put(cSQLDBHelper.KEY_MENU_DATE, formatter.format(menuModel.getCreateDate()));

        // insert outcome record
        try {
            if (db.insert(cSQLDBHelper.TABLE_MENU, null, cv) < 0) {
                return false;
            }
        } catch (Exception ex) {
            Log.d("Exception in importing ", ex.getMessage().toString());
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
        cv.put(cSQLDBHelper.KEY_MENU_ID, menuModel.getMenuID());
        cv.put(cSQLDBHelper.KEY_MENU_PARENT_ID, menuModel.getParentID());
        cv.put(cSQLDBHelper.KEY_MENU_OWNER_ID, menuModel.getOwnerID());
        cv.put(cSQLDBHelper.KEY_MENU_GROUP_BITS, menuModel.getGroupBITS());
        cv.put(cSQLDBHelper.KEY_MENU_PERMS_BITS, menuModel.getPermsBITS());
        cv.put(cSQLDBHelper.KEY_MENU_STATUS_BITS, menuModel.getStatusBITS());
        cv.put(cSQLDBHelper.KEY_MENU_NAME, menuModel.getName());
        cv.put(cSQLDBHelper.KEY_MENU_DESCRIPTION, menuModel.getDescription());
        //cv.put(cSQLDBHelper.KEY_MENU_DATE, formatter.format(menuModel.getCreateDate()));

        // insert outcome record
        try {
            if (db.insert(cSQLDBHelper.TABLE_MENU, null, cv) < 0) {
                return false;
            }
        } catch (Exception ex) {
            Log.d("Exception in importing ", ex.getMessage().toString());
        }

        // close the database connection
        db.close();

        return true;
    }

    public cMenuModel getMenuByID(int menuID) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        // construct a selection query
        String selectQuery = "SELECT * FROM " +
                cSQLDBHelper.TABLE_MENU + " WHERE " +
                cSQLDBHelper.KEY_MENU_ID + "= ?";

        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(menuID)});

        cMenuModel menu = new cMenuModel();

        try {
            if (cursor.moveToFirst()) {
                do {
                    menu.setMenuID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_MENU_ID)));
                    menu.setParentID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_MENU_PARENT_ID)));
                    menu.setOwnerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_MENU_OWNER_ID)));
                    menu.setGroupBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_MENU_GROUP_BITS)));
                    menu.setPermsBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_MENU_PERMS_BITS)));
                    menu.setStatusBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_MENU_STATUS_BITS)));
                    menu.setName(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_MENU_NAME)));
                    menu.setDescription(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_MENU_DESCRIPTION)));
                    //menu.setCreateDate(formatter.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_MENU_DATE))));

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


    public List<cMenuModel> getMenuList() {

        List<cMenuModel> menuModelList = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+ cSQLDBHelper.TABLE_MENU, null);

        try {
            if (cursor.moveToFirst()) {
                do {
                    cMenuModel menu = new cMenuModel();

                    menu.setMenuID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_MENU_ID)));
                    menu.setParentID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_MENU_PARENT_ID)));
                    menu.setOwnerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_MENU_OWNER_ID)));
                    menu.setGroupBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_MENU_GROUP_BITS)));
                    menu.setPermsBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_MENU_PERMS_BITS)));
                    menu.setStatusBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_MENU_STATUS_BITS)));
                    menu.setName(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_MENU_NAME)));
                    menu.setDescription(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_MENU_DESCRIPTION)));
                    //menu.setCreateDate(formatter.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_MENU_DATE))));

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


    public List<cMenuModel> getSubMenuByID(int menuID) {

        List<cMenuModel> menuModelList = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String selectQuery = "SELECT * FROM " +
                cSQLDBHelper.TABLE_MENU + " WHERE " +
                cSQLDBHelper.KEY_MENU_PARENT_ID + "= ?";

        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(menuID)});

        try {
            if (cursor.moveToFirst()) {
                do {
                    cMenuModel menu = new cMenuModel();

                    menu.setMenuID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_MENU_ID)));
                    menu.setParentID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_MENU_PARENT_ID)));
                    menu.setOwnerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_MENU_OWNER_ID)));
                    menu.setGroupBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_MENU_GROUP_BITS)));
                    menu.setPermsBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_MENU_PERMS_BITS)));
                    menu.setStatusBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_MENU_STATUS_BITS)));
                    menu.setName(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_MENU_NAME)));
                    menu.setDescription(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_MENU_DESCRIPTION)));
                    //menu.setCreateDate(formatter.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_MENU_DATE))));

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
}
