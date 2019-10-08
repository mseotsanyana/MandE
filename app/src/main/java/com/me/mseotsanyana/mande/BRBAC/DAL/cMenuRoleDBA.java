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
 * Created by mseotsanyana on 2017/06/27.
 */

public class cMenuRoleDBA {
    // an object of the database helper
    private cSQLDBHelper dbHelper;

    private cMenuDBA menuDBA;
    private cRoleDBA roleDBA;
    private cOrganizationDBA organizationDBA;

    private static final SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
    private static final String TAG = "dbHelper";

    public cMenuRoleDBA(Context context) {
        dbHelper = new cSQLDBHelper(context);
        menuDBA  = new cMenuDBA(context);
        roleDBA  = new cRoleDBA(context);
        organizationDBA = new cOrganizationDBA(context);
    }

    public boolean addMenuRoleFromExcel(cMenuRoleModel menuRoleModel){
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long result = -1;

        // get an menu by id
        cMenuModel menuModel = menuDBA.getMenuByID(menuRoleModel.getMenuID());
        // get a group by id
        cRoleModel roleModel = roleDBA.getRoleByID(menuRoleModel.getRoleID());
        // get a group by id
        cOrganizationModel organizationModel = organizationDBA.getOrganizationByID(menuRoleModel.getOrganizationID());

        if ((menuModel != null) && (roleModel != null) && (organizationModel != null)){

            ContentValues cv = new ContentValues();

            cv.put(cSQLDBHelper.KEY_ID, menuModel.getMenuID());
            cv.put(cSQLDBHelper.KEY_ID, roleModel.getRoleID());
            cv.put(cSQLDBHelper.KEY_ORGANIZATION_FK_ID, organizationModel.getOrganizationID());
            //cv.put(cSQLDBHelper.KEY_DATE, formatter.format(menuRoleModel.getCreateDate()));

            // insert menu group record
            result = db.insert(cSQLDBHelper.TABLE_tblMENU, null, cv);
        }

        // close the database connection
        db.close();

        return result > -1;
    }

    public boolean addMenuRole(cMenuRoleModel menuRoleModel){
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long result = -1;

        // get an menu by id
        cMenuModel menuModel = menuDBA.getMenuByID(menuRoleModel.getMenuID());
        // get a group by id
        cRoleModel roleModel = roleDBA.getRoleByID(menuRoleModel.getRoleID());

        if ((menuModel != null) && (roleModel != null)){

            ContentValues cv = new ContentValues();

            cv.put(cSQLDBHelper.KEY_ID, menuModel.getMenuID());
            cv.put(cSQLDBHelper.KEY_ID, roleModel.getRoleID());
            cv.put(cSQLDBHelper.KEY_OWNER_ID, menuRoleModel.getOwnerID());
            cv.put(cSQLDBHelper.KEY_GROUP_BITS, menuRoleModel.getGroupBITS());
            cv.put(cSQLDBHelper.KEY_PERMS_BITS, menuRoleModel.getPermsBITS());
            cv.put(cSQLDBHelper.KEY_STATUS_BITS, menuRoleModel.getStatusBITS());
            //cv.put(cSQLDBHelper.KEY_DATE, formatter.format(menuRoleModel.getCreateDate()));

            // insert menu group record
            result = db.insert(cSQLDBHelper.TABLE_tblMENU, null, cv);
        }

        // close the database connection
        db.close();

        return result > -1;
    }

    public List<cMenuRoleModel> getMenuRoleList() {

        List<cMenuRoleModel> menuRoleModelList = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+ cSQLDBHelper.TABLE_tblUSER, null);

        try {
            if (cursor.moveToFirst()) {
                do {
                    cMenuRoleModel menuRole = new cMenuRoleModel();

                    menuRole.setMenuID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ID)));
                    menuRole.setRoleID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ID)));
                    menuRole.setOwnerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OWNER_ID)));
                    menuRole.setGroupBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_GROUP_BITS)));
                    menuRole.setPermsBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PERMS_BITS)));
                    menuRole.setStatusBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_STATUS_BITS)));
                    //menuRole.setCreateDate(formatter.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_DATE))));

                    menuRoleModelList.add(menuRole);

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to get menu groups from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        // close the database connection
        db.close();

        return menuRoleModelList;
    }

    public List<cMenuModel> getMenusByRoleID(int roleID) {

        List<cMenuModel> menuModels = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT menu."+cSQLDBHelper.KEY_ID + ", menu." +
                cSQLDBHelper.KEY_NAME + ", menu."+cSQLDBHelper.KEY_CREATED_DATE + " FROM "+
                cSQLDBHelper.TABLE_tblMENU + " menu, " +
                cSQLDBHelper.TABLE_tblROLE + " role, " +
                cSQLDBHelper.TABLE_tblMENU +" menu_role " +
                " WHERE menu."+cSQLDBHelper.KEY_ID+" = menu_role." + cSQLDBHelper.KEY_ID +
                " AND role."+cSQLDBHelper.KEY_ID + " = menu_role." + cSQLDBHelper.KEY_ID +
                " AND role."+cSQLDBHelper.KEY_ID + " = "+ roleID +
                " AND menu."+cSQLDBHelper.KEY_PARENT_FK_ID + " = "+ 0, null);

        try {
            if (cursor.moveToFirst()) {
                do {
                    cMenuModel menu = new cMenuModel();

                    menu.setMenuID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ID)));
                    //menu.setParentID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PARENT_ID)));
                    //menu.setOwnerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OWNER_ID)));
                    //menu.setGroupBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_GROUP_BITS)));
                    //menu.setPermsBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PERMS_BITS)));
                    //menu.setTypeBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_TYPE_BITS)));
                    //menu.setStatusBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_STATUS_BITS)));
                    menu.setName(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_NAME)));
                    //menu.setDescription(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_DESCRIPTION)));
                    //menu.setCreateDate(formatter.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_DATE))));

                    menuModels.add(menu);

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to get menu groups from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        // close the database connection
        db.close();

        return menuModels;
    }

    public List<cRoleModel> getRolesByMenuID(int menuID) {

        List<cRoleModel> roleModels = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM "+
                cSQLDBHelper.TABLE_tblMENU + " menu, " +
                cSQLDBHelper.TABLE_tblROLE + " role, " +
                cSQLDBHelper.TABLE_tblMENU +" menu_role " +
                "WHERE menu."+cSQLDBHelper.KEY_ID+" = menu_role."+cSQLDBHelper.KEY_ID+
                " AND role."+cSQLDBHelper.KEY_ID+" = menu_role."+cSQLDBHelper.KEY_ID+
                " AND "+ menuID +" = menu."+cSQLDBHelper.KEY_ID, null);

        try {
            if (cursor.moveToFirst()) {
                do {
                    cRoleModel role = new cRoleModel();

                    role.setRoleID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ID)));
                    role.setOrganizationID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ORGANIZATION_FK_ID)));
                    role.setOwnerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OWNER_ID)));
                    role.setGroupBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_GROUP_BITS)));
                    role.setPermsBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PERMS_BITS)));
                    role.setStatusBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_STATUS_BITS)));
                    role.setName(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_NAME)));
                    role.setDescription(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_DESCRIPTION)));
                    //role.setCreateDate(formatter.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_DATE))));

                    roleModels.add(role);

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to get menu_role from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        // close the database connection
        db.close();

        return roleModels;
    }
}
