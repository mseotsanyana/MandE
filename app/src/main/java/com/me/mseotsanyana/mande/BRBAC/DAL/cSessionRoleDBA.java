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

public class cSessionRoleDBA {
    // an object of the database helper
    private cSQLDBHelper dbHelper;

    private cSessionDBA sessionDBA;
    private cRoleDBA roleDBA;
    private cOrganizationDBA organizationDBA;

    private static final SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
    private static final String TAG = "dbHelper";

    public cSessionRoleDBA(Context context) {
        dbHelper = new cSQLDBHelper(context);
        sessionDBA  = new cSessionDBA(context);
        roleDBA  = new cRoleDBA(context);
        organizationDBA = new cOrganizationDBA(context);
    }

    public boolean addSessionRoleFromExcel(cSessionRoleModel sessionRoleModel){
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long result = -1;

        // get an session by id
        cSessionModel sessionModel = sessionDBA.getSessionByID(sessionRoleModel.getSessionID());
        // get a role by id
        cRoleModel roleModel = roleDBA.getRoleByID(sessionRoleModel.getRoleID());
        // get a role by id
        cOrganizationModel organizationModel = organizationDBA.getOrganizationByID(sessionRoleModel.getOrganizationID());

        if ((sessionModel != null) && (roleModel != null) && (organizationModel != null)){

            ContentValues cv = new ContentValues();

            cv.put(cSQLDBHelper.KEY_SESSION_FK_ID, sessionModel.getSessionID());
            cv.put(cSQLDBHelper.KEY_SESSION_FK_ID, roleModel.getRoleID());
            cv.put(cSQLDBHelper.KEY_ORGANIZATION_FK_ID, organizationModel.getOrganizationID());
            //cv.put(cSQLDBHelper.KEY_DATE, formatter.format(sessionRoleModel.getCreateDate()));

            // insert session role record
            result = db.insert(cSQLDBHelper.TABLE_tblSESSION, null, cv);
        }

        // close the database connection
        db.close();

        return result > -1;
    }

    public boolean addSessionRole(cSessionRoleModel sessionRoleModel){
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long result = -1;

        // get an session by id
        cSessionModel sessionModel = sessionDBA.getSessionByID(sessionRoleModel.getSessionID());
        // get a role by id
        cRoleModel groupModel = roleDBA.getRoleByID(sessionRoleModel.getRoleID());

        if ((sessionModel != null) && (groupModel != null)){

            ContentValues cv = new ContentValues();

            cv.put(cSQLDBHelper.KEY_SESSION_FK_ID, sessionModel.getSessionID());
            cv.put(cSQLDBHelper.KEY_SESSION_FK_ID, groupModel.getRoleID());
            cv.put(cSQLDBHelper.KEY_OWNER_ID, sessionRoleModel.getOwnerID());
            cv.put(cSQLDBHelper.KEY_GROUP_BITS, sessionRoleModel.getGroupBITS());
            cv.put(cSQLDBHelper.KEY_PERMS_BITS, sessionRoleModel.getPermsBITS());
            cv.put(cSQLDBHelper.KEY_STATUS_BITS, sessionRoleModel.getStatusBITS());
            //cv.put(cSQLDBHelper.KEY_DATE, formatter.format(sessionRoleModel.getCreateDate()));

            // insert session group record
            result = db.insert(cSQLDBHelper.TABLE_tblSESSION, null, cv);
        }

        // close the database connection
        db.close();

        return result > -1;
    }

    public List<cSessionRoleModel> getSessionRoleList() {

        List<cSessionRoleModel> sessionRoleModels = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+ cSQLDBHelper.TABLE_tblSESSION, null);

        try {
            if (cursor.moveToFirst()) {
                do {
                    cSessionRoleModel sessionRole = new cSessionRoleModel();

                    sessionRole.setSessionID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_SESSION_FK_ID)));
                    sessionRole.setRoleID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_SESSION_FK_ID)));
                    sessionRole.setOwnerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OWNER_ID)));
                    sessionRole.setGroupBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_GROUP_BITS)));
                    sessionRole.setPermsBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PERMS_BITS)));
                    sessionRole.setStatusBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_STATUS_BITS)));
                    //sessionRole.setCreateDate(formatter.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_DATE))));

                    sessionRoleModels.add(sessionRole);

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to get session groups from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        // close the database connection
        db.close();

        return sessionRoleModels;
    }


    public List<cSessionModel> getSessionsByRoleID(int roleID) {

        List<cSessionModel> sessionModels = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM "+
                cSQLDBHelper.TABLE_tblSESSION + " session, " +
                cSQLDBHelper.TABLE_tblROLE + " role, " +
                cSQLDBHelper.TABLE_tblSESSION +" session_role " +
                "WHERE session."+cSQLDBHelper.KEY_ID+" = session_role."+cSQLDBHelper.KEY_SESSION_FK_ID+
                " AND role."+cSQLDBHelper.KEY_ID+" = session_role."+cSQLDBHelper.KEY_SESSION_FK_ID+
                " AND "+ roleID +" = role."+cSQLDBHelper.KEY_ID, null);

        try {
            if (cursor.moveToFirst()) {
                do {
                    cSessionModel session = new cSessionModel();

                    session.setSessionID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ID)));
                    session.setOwnerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OWNER_ID)));
                    session.setGroupBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_GROUP_BITS)));
                    session.setPermsBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PERMS_BITS)));
                    session.setStatusBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_STATUS_BITS)));
                    session.setName(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_NAME)));
                    session.setSurname(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_SURNAME)));
                    session.setDescription(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_DESCRIPTION)));
                    session.setEmail(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_EMAIL)));
                    session.setPassword(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_PASSWORD)));
                    session.setSalt(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_SALT)));
                    //session.setCreateDate(formatter.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_DATE))));

                    sessionModels.add(session);

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to get session groups from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        // close the database connection
        db.close();

        return sessionModels;
    }

    public List<cRoleModel> getRolesBySessionID(int sessionID) {

        List<cRoleModel> roleModels = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT role." + cSQLDBHelper.KEY_ID + ", role." +
                cSQLDBHelper.KEY_NAME + ", role." + cSQLDBHelper.KEY_DESCRIPTION + ", role." +
                cSQLDBHelper.KEY_CREATED_DATE + " FROM " +
                cSQLDBHelper.TABLE_tblSESSION + " session, " +
                cSQLDBHelper.TABLE_tblROLE + " role, " +
                cSQLDBHelper.TABLE_tblSESSION +" session_role " +
                "WHERE session."+cSQLDBHelper.KEY_ID+" = session_role."+cSQLDBHelper.KEY_SESSION_FK_ID+
                " AND role."+cSQLDBHelper.KEY_ID+" = session_role."+cSQLDBHelper.KEY_ROLE_FK_ID+
                " AND "+ sessionID +" = session."+cSQLDBHelper.KEY_ID, null);

        try {
            if (cursor.moveToFirst()) {
                do {
                    cRoleModel role = new cRoleModel();

                    role.setRoleID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ID)));
                    //role.setOrganizationID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ORGANIZATION_FK_ID)));
                    //role.setOwnerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OWNER_ID)));
                    //role.setGroupBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_GROUP_BITS)));
                    //role.setPermsBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PERMS_BITS)));
                    //role.setTypeBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_TYPE_BITS)));
                    //role.setStatusBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_STATUS_BITS)));
                    role.setName(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_NAME)));
                    role.setDescription(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_DESCRIPTION)));
                    //role.setCreateDate(formatter.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_DATE))));

                    roleModels.add(role);

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to get session groups from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        // close the database connection
        db.close();

        return roleModels;
    }

    public ArrayList<Integer> getRoleIDsBySessionID(int sessionID) {

        ArrayList<Integer> roleIDs = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT role." + cSQLDBHelper.KEY_ID + " FROM " +
                cSQLDBHelper.TABLE_tblSESSION + " session, " +
                cSQLDBHelper.TABLE_tblROLE + " role, " +
                cSQLDBHelper.TABLE_tblSESSION +" session_role " +
                "WHERE session."+cSQLDBHelper.KEY_ID+" = session_role."+cSQLDBHelper.KEY_SESSION_FK_ID+
                " AND role."+cSQLDBHelper.KEY_ID+" = session_role."+cSQLDBHelper.KEY_ROLE_FK_ID+
                " AND "+ sessionID +" = session."+cSQLDBHelper.KEY_ID, null);

        try {
            if (cursor.moveToFirst()) {
                do {
                    roleIDs.add(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ID)));
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to get session groups from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        // close the database connection
        db.close();

        return roleIDs;
    }
}
