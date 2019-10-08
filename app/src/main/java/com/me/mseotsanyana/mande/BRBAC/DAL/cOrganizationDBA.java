package com.me.mseotsanyana.mande.BRBAC.DAL;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.me.mseotsanyana.mande.PPMER.DAL.cSQLDBHelper;
import com.me.mseotsanyana.treeadapterlibrary.cTreeModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by mseotsanyana on 2017/05/25.
 */

public class cOrganizationDBA {

    private static final SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
    private static final String TAG = "dbHelper";

    // an object of the database helper
    private cSQLDBHelper dbHelper;

    public cOrganizationDBA(Context context) {
        dbHelper = new cSQLDBHelper(context);
    }

    public boolean addOrganization(cOrganizationModel organizationModel) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_ID, organizationModel.getOrganizationID());
        cv.put(cSQLDBHelper.KEY_ADDRESS_FK_ID, organizationModel.getAddressID());
        cv.put(cSQLDBHelper.KEY_OWNER_ID, organizationModel.getOwnerID());
        cv.put(cSQLDBHelper.KEY_NAME, organizationModel.getOrganizationName());
        cv.put(cSQLDBHelper.KEY_TELEPHONE, organizationModel.getTelephone());
        cv.put(cSQLDBHelper.KEY_FAX, organizationModel.getFax());
        cv.put(cSQLDBHelper.KEY_VISION, organizationModel.getVision());
        cv.put(cSQLDBHelper.KEY_MISSION, organizationModel.getMission());
        cv.put(cSQLDBHelper.KEY_EMAIL, organizationModel.getEmailAddress());
        cv.put(cSQLDBHelper.KEY_WEBSITE, organizationModel.getWebsite());
        cv.put(cSQLDBHelper.KEY_CREATED_DATE, formatter.format(organizationModel.getCreateDate()));

        // insert value record
        long result = db.insert(cSQLDBHelper.TABLE_tblORGANIZATION, null, cv);

        // close the database connection
        db.close();

        return result > -1;
    }

    public boolean addOrganizationFromExcel(cOrganizationModel organizationModel) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_ID, organizationModel.getOrganizationID());
        cv.put(cSQLDBHelper.KEY_ADDRESS_FK_ID, organizationModel.getAddressID());
        cv.put(cSQLDBHelper.KEY_NAME, organizationModel.getOrganizationName());
        cv.put(cSQLDBHelper.KEY_TELEPHONE, organizationModel.getTelephone());
        cv.put(cSQLDBHelper.KEY_FAX, organizationModel.getFax());
        cv.put(cSQLDBHelper.KEY_VISION, organizationModel.getVision());
        cv.put(cSQLDBHelper.KEY_MISSION, organizationModel.getMission());
        cv.put(cSQLDBHelper.KEY_EMAIL, organizationModel.getEmailAddress());
        cv.put(cSQLDBHelper.KEY_WEBSITE, organizationModel.getWebsite());

        // insert value record
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblORGANIZATION, null, cv) < 0) {
                return false;
            }
        } catch (Exception ex) {
            Log.d("Exception in importing ", ex.getMessage().toString());
        }

        // close the database connection
        db.close();

        return true;
    }

    public boolean deleteAllOrganizations() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblORGANIZATION, null, null);

        // close the database connection
        db.close();

        return result > -1;
    }


    public List<cOrganizationModel> getOrganizationList(
            int userID, int primaryRole, int secondaryRoles, int operationBITS, int statusBITS) {

        List<cOrganizationModel> organizationModelList = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String selectQuery = "SELECT * FROM "+ cSQLDBHelper.TABLE_tblORGANIZATION +
                " WHERE ((("+cSQLDBHelper.KEY_OWNER_ID + " = ?) " +
                " AND (("+cSQLDBHelper.KEY_PERMS_BITS + " & ?) != 0)) " +
                " OR ((("+cSQLDBHelper.KEY_GROUP_BITS + " & ?) != 0) " +
                " AND (("+cSQLDBHelper.KEY_PERMS_BITS + " & ?) != 0)) "+
                " OR ((("+cSQLDBHelper.KEY_GROUP_BITS + " & ?) != 0) " +
                " AND (("+cSQLDBHelper.KEY_PERMS_BITS + " & ?) != 0)))";

        Cursor cursor = db.rawQuery(selectQuery, new String[]{
                String.valueOf(userID),String.valueOf(operationBITS),
                String.valueOf(primaryRole),String.valueOf(operationBITS),
                String.valueOf(secondaryRoles),String.valueOf(operationBITS)});

        try {
            if (cursor.moveToFirst()) {
                do {
                    cOrganizationModel organizationModel = new cOrganizationModel();
                    // populate organization model object
                    organizationModel.setOrganizationID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ID)));
                    organizationModel.setAddressID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ADDRESS_FK_ID)));
                    organizationModel.setOwnerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OWNER_ID)));
                    organizationModel.setOrganizationName(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_NAME)));
                    organizationModel.setTelephone(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_TELEPHONE)));
                    organizationModel.setFax(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_FAX)));
                    organizationModel.setVision(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_VISION)));
                    organizationModel.setMission(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_MISSION)));
                    organizationModel.setEmailAddress(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_EMAIL)));
                    organizationModel.setWebsite(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_WEBSITE)));
                    organizationModel.setCreateDate(formatter.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_CREATED_DATE))));
                    // add model organization into the action_list
                    organizationModelList.add(organizationModel);

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to get users from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        // close the database connection
        db.close();

        return organizationModelList;
    }


    public List<cOrganizationModel> getOrganizationList() {
        List<cOrganizationModel> organizationModelList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+ cSQLDBHelper.TABLE_tblORGANIZATION, null);

        try {
            if (cursor.moveToFirst()) {
                do {
                    cOrganizationModel organizationModel = new cOrganizationModel();
                    // populate organization model object
                    organizationModel.setOrganizationID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ID)));
                    organizationModel.setAddressID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ADDRESS_FK_ID)));
                    organizationModel.setOwnerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OWNER_ID)));
                    organizationModel.setOrganizationName(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_NAME)));
                    organizationModel.setTelephone(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_TELEPHONE)));
                    organizationModel.setFax(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_FAX)));
                    organizationModel.setVision(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_VISION)));
                    organizationModel.setMission(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_MISSION)));
                    organizationModel.setEmailAddress(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_EMAIL)));
                    organizationModel.setWebsite(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_WEBSITE)));
                    organizationModel.setCreateDate(formatter.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_CREATED_DATE))));
                    // add model organization into the action_list
                    organizationModelList.add(organizationModel);

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to get values from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return organizationModelList;
    }

    public Cursor getAllOrganizations() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        try {
            String selectQuery = "SELECT " + cSQLDBHelper.KEY_ID +", "+ cSQLDBHelper.KEY_NAME + " FROM "
                    + cSQLDBHelper.TABLE_tblORGANIZATION;

            Cursor cursor = db.rawQuery(selectQuery, null);

            return cursor;

        } catch (Exception e) {
            Log.d(TAG, "Error while trying to get values from database");
            return null;
        }
    }

    public Cursor getOrganisationByID(int organizationID) {
        // open connection to read only
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        try {
            // construct a selection query
            String selectQuery = "SELECT "+ cSQLDBHelper.KEY_ID +", "+ cSQLDBHelper.KEY_NAME + " FROM "
                    + cSQLDBHelper.TABLE_tblORGANIZATION
                    + " WHERE " +
                    cSQLDBHelper.KEY_ID + "= ?";

            // open the cursor to be used to traverse the dataset
            Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(organizationID)});

            return cursor;

        } catch (Exception e) {
            Log.d(TAG, "Error while trying to get values from database");
            return null;
        }
    }

    public cOrganizationModel getOrganizationByID(int organizationID) {
        // open connection to read only
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        try {
            // construct a selection query
            String selectQuery = "SELECT "+ cSQLDBHelper.KEY_ID +", "
                    + cSQLDBHelper.KEY_NAME + " FROM "
                    + cSQLDBHelper.TABLE_tblORGANIZATION
                    + " WHERE " +
                    cSQLDBHelper.KEY_ID + "= ?";

            // open the cursor to be used to traverse the dataset
            Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(organizationID)});
            cOrganizationModel model = new cOrganizationModel();

            if (cursor.moveToFirst()) {
                model.setOrganizationID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ID)));
                model.setOrganizationName(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_NAME)));
            }
            return model;

        } catch (Exception e) {
            Log.d(TAG, "Error while trying to get values from database");
            return null;
        }
    }

    public List<cTreeModel> getOrganizationTree()
    {
        // open connection to read only
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // construct a selection query for organization
        String selectOrganizationQuery = "SELECT * FROM " + cSQLDBHelper.TABLE_tblORGANIZATION;

        // open the organization cursor to be used to traverse the dataset
        Cursor organizationCursor = db.rawQuery(selectOrganizationQuery, null);

        // the action_list of two fields to be populated and returned
        List<cTreeModel> treeList = new ArrayList<>();

        int index = 0;
        // looping through all goal rows and adding to tree action_list
        if (organizationCursor.moveToFirst()) {
            try {
                do {
                    cTreeModel treeData = new cTreeModel(
                            organizationCursor.getInt(organizationCursor.getColumnIndex(cSQLDBHelper.KEY_ID)),
                            -1,
                            0,
                            new cOrganizationModel(
                                    organizationCursor.getInt(organizationCursor.getColumnIndex(cSQLDBHelper.KEY_ID)),
                                    organizationCursor.getString(organizationCursor.getColumnIndex(cSQLDBHelper.KEY_NAME))
                            )
                    );

                    treeList.add(treeData);

                    index = index + 1;

                } while (organizationCursor.moveToNext());
            }catch (Exception e) {
                Log.d(TAG, "Error while trying to get values from database");
            } finally {
                if (organizationCursor != null && !organizationCursor.isClosed())
                    organizationCursor.close();
            }
        }

        // close the database connection
        db.close();

        return treeList;
    }
}
