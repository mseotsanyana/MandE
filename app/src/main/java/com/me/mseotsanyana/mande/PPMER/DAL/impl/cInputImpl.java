package com.me.mseotsanyana.mande.PPMER.DAL.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.me.mseotsanyana.mande.PPMER.DAL.cBudgetModel;
import com.me.mseotsanyana.mande.PPMER.DAL.cHumanSetModel;
import com.me.mseotsanyana.mande.PPMER.DAL.cMaterialModel;
import com.me.mseotsanyana.mande.PPMER.DAL.model.cInputModel;
import com.me.mseotsanyana.mande.STORAGE.database.cSQLDBHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class cInputImpl {
    // an object of the database helper
    private cSQLDBHelper dbHelper;

    private static final SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
    private static final String TAG = "dbHelper";

    public cInputImpl(Context context) {
        dbHelper = new cSQLDBHelper(context);
    }

    /*
     * the function adding a specific input
     */
    public boolean addInput(cInputModel inputModel) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_ID, inputModel.getInputID());
        cv.put(cSQLDBHelper.KEY_SERVER_ID, inputModel.getServerID());
        cv.put(cSQLDBHelper.KEY_OWNER_ID, inputModel.getOwnerID());
        cv.put(cSQLDBHelper.KEY_ORG_ID, inputModel.getOrgID());
        cv.put(cSQLDBHelper.KEY_GROUP_BITS, inputModel.getGroupBITS());
        cv.put(cSQLDBHelper.KEY_PERMS_BITS, inputModel.getPermsBITS());
        cv.put(cSQLDBHelper.KEY_STATUS_BITS, inputModel.getStatusBITS());
        cv.put(cSQLDBHelper.KEY_NAME, inputModel.getName());
        cv.put(cSQLDBHelper.KEY_DESCRIPTION, inputModel.getDescription());
        cv.put(cSQLDBHelper.KEY_START_DATE, formatter.format(inputModel.getStartDate()));
        cv.put(cSQLDBHelper.KEY_END_DATE, formatter.format(inputModel.getEndDate()));
        cv.put(cSQLDBHelper.KEY_CREATED_DATE, formatter.format(inputModel.getCreatedDate()));
        cv.put(cSQLDBHelper.KEY_MODIFIED_DATE, formatter.format(inputModel.getModifiedDate()));
        cv.put(cSQLDBHelper.KEY_SYNCED_DATE, formatter.format(inputModel.getSyncedDate()));

        // insert project details
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblINPUT, null, cv) < 0){
                return false;
            }
        } catch (Exception e) {
            Log.d(TAG, "Exception in adding OUTPUT "+e.getMessage().toString());
        }

        // close the database connection
        db.close();

        return true;
    }
    /*
     * the function adding a specific input
     */
    public boolean addInputHumanSet(cHumanSetModel humanSetModel) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_INPUT_FK_ID, humanSetModel.getInputID());

        // insert project details
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblHUMANSET, null, cv) < 0){
                return false;
            }
        } catch (Exception e) {
            Log.d(TAG, "Exception in adding OUTPUT "+e.getMessage().toString());
        }

        // close the database connection
        db.close();

        return true;
    }


    /*
     * the function adding a specific input
     */
    public boolean addInputMaterial(cMaterialModel materialModel) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_INPUT_FK_ID, materialModel.getInputID());

        // insert project details
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblMATERIAL, null, cv) < 0) {
                return false;
            }
        } catch (Exception e) {
            Log.d(TAG, "Exception in adding OUTPUT "+e.getMessage().toString());
        }

        // close the database connection
        db.close();

        return true;
    }


    /*
     * the function adding a specific input
     */
    public boolean addInputBudget(cInputModel inputModel, cBudgetModel budgetModel) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_ID, inputModel.getInputID());
        cv.put(cSQLDBHelper.KEY_SERVER_ID, inputModel.getServerID());
        cv.put(cSQLDBHelper.KEY_OWNER_ID, inputModel.getOwnerID());
        cv.put(cSQLDBHelper.KEY_ORG_ID, inputModel.getOrgID());
        cv.put(cSQLDBHelper.KEY_GROUP_BITS, inputModel.getGroupBITS());
        cv.put(cSQLDBHelper.KEY_PERMS_BITS, inputModel.getPermsBITS());
        cv.put(cSQLDBHelper.KEY_STATUS_BITS, inputModel.getStatusBITS());
        cv.put(cSQLDBHelper.KEY_NAME, inputModel.getName());
        cv.put(cSQLDBHelper.KEY_DESCRIPTION, inputModel.getDescription());
        cv.put(cSQLDBHelper.KEY_START_DATE, formatter.format(inputModel.getStartDate()));
        cv.put(cSQLDBHelper.KEY_END_DATE, formatter.format(inputModel.getEndDate()));
        cv.put(cSQLDBHelper.KEY_CREATED_DATE, formatter.format(inputModel.getCreatedDate()));
        cv.put(cSQLDBHelper.KEY_MODIFIED_DATE, formatter.format(inputModel.getModifiedDate()));
        cv.put(cSQLDBHelper.KEY_SYNCED_DATE, formatter.format(inputModel.getSyncedDate()));

        // insert project details
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblACTIVITY, null, cv) < 0) {
                return false;
            }
        } catch (Exception e) {
            Log.d(TAG, "Exception in adding OUTPUT "+e.getMessage().toString());
        }

        // close the database connection
        db.close();

        return true;
    }

    /*
     * the function adding a specific input outcome
     */
    public boolean addInputBudget(cMaterialModel  materialModel){
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_INPUT_FK_ID, materialModel.getInputID());
        /*
        cv.put(cSQLDBHelper.KEY_OUTPUT_FK_ID, materialModel.getMaterialID());
        cv.put(cSQLDBHelper.KEY_PARENT_FK_ID, materialModel.getParentID());
        cv.put(cSQLDBHelper.KEY_CHILD_FK_ID, materialModel.getChildID());
        cv.put(cSQLDBHelper.KEY_SERVER_ID, materialModel.getServerID());
        cv.put(cSQLDBHelper.KEY_OWNER_ID, materialModel.getOwnerID());
        cv.put(cSQLDBHelper.KEY_ORG_ID, materialModel.getOrgID());
        cv.put(cSQLDBHelper.KEY_GROUP_BITS, materialModel.getGroupBITS());
        cv.put(cSQLDBHelper.KEY_PERMS_BITS, materialModel.getPermsBITS());
        cv.put(cSQLDBHelper.KEY_STATUS_BITS, materialModel.getStatusBITS());
        cv.put(cSQLDBHelper.KEY_CREATED_DATE, formatter.format(materialModel.getCreatedDate()));
        cv.put(cSQLDBHelper.KEY_MODIFIED_DATE, formatter.format(materialModel.getModifiedDate()));
        cv.put(cSQLDBHelper.KEY_SYNCED_DATE, formatter.format(materialModel.getSyncedDate()));
         */

        // insert project details
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblACTIVITY_OUTPUT, null, cv) < 0) {
                return false;
            }
        } catch (Exception e) {
            Log.d(TAG, "Exception in adding OUTPUT_OUTPUTs "+e.getMessage().toString());
        }

        // close the database connection
        db.close();

        return true;
    }

    /*
     * the function delete all activities
     */
    public boolean deleteActivities() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        try {
            if(db.delete(cSQLDBHelper.TABLE_tblACTIVITY, null, null) < 0){
                return false;
            }
        }catch (Exception e){
            Log.d(TAG, "Exception in deleting all OUTPUTs "+e.getMessage().toString());
        }

        // close the database connection
        db.close();

        return true;
    }

    /*
     * the function delete all input outcomes
     */
    public boolean deleteInputMaterials() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        try {
            if(db.delete(cSQLDBHelper.TABLE_tblACTIVITY_OUTPUT, null, null) < 0){
                return false;
            }
        }catch (Exception e){
            Log.d(TAG, "Exception in deleting all ACTIVITY_OUTPUTs "+e.getMessage().toString());
        }

        // close the database connection
        db.close();

        return true;
    }

    /*
     * the function fetches all inputs
     */
    public ArrayList<cInputModel> getInputModels() {
        // list of inputs
        ArrayList<cInputModel> inputModels = new ArrayList<>();

        // open the connection to the database
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // construct a selection query
        String selectQuery = "SELECT * FROM "+ cSQLDBHelper.TABLE_tblACTIVITY;

        // construct an argument cursor
        Cursor cursor = db.rawQuery(selectQuery, null);

        try {
            if (cursor.moveToFirst()) {
                do {
                    cInputModel inputModel = new cInputModel();

                    inputModel.setInputID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ID)));
                    inputModel.setServerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_SERVER_ID)));
                    inputModel.setOwnerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OWNER_ID)));
                    inputModel.setOrgID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ORG_ID)));
                    inputModel.setGroupBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_GROUP_BITS)));
                    inputModel.setPermsBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PERMS_BITS)));
                    inputModel.setStatusBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_STATUS_BITS)));
                    inputModel.setName(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_NAME)));
                    inputModel.setDescription(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_DESCRIPTION)));
                    inputModel.setStartDate(formatter.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_START_DATE))));
                    inputModel.setEndDate(formatter.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_END_DATE))));
                    inputModel.setCreatedDate(formatter.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_CREATED_DATE))));
                    inputModel.setModifiedDate(formatter.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_MODIFIED_DATE))));
                    inputModel.setSyncedDate(formatter.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_SYNCED_DATE))));

                    inputModels.add(inputModel);

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Exception in reading all OUTPUTs "+e.getMessage().toString());
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        // close the database connection
        db.close();

        return inputModels;
    }

    // get input outcomes
    public ArrayList<cMaterialModel> getInputMaterialsByID(int inputID) {
        // list of child outcome
        ArrayList<cMaterialModel> materialModels = new ArrayList<cMaterialModel>();

        // open the connection to the database
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // construct a selection query
        String selectQuery = "SELECT * FROM " +
                cSQLDBHelper.TABLE_tblACTIVITY + " input, " +
                cSQLDBHelper.TABLE_tblOUTPUT + " output, " +
                cSQLDBHelper.TABLE_tblACTIVITY_OUTPUT + " input_output " +
                " WHERE input."+cSQLDBHelper.KEY_ID+" = input_output."+cSQLDBHelper.KEY_ACTIVITY_FK_ID +
                " AND input."+cSQLDBHelper.KEY_LOGFRAME_FK_ID + " = input_output."+cSQLDBHelper.KEY_PARENT_FK_ID +
                " AND output."+cSQLDBHelper.KEY_ID + " = input_output."+cSQLDBHelper.KEY_OUTPUT_FK_ID +
                " AND output."+cSQLDBHelper.KEY_LOGFRAME_FK_ID +" = input_output."+cSQLDBHelper.KEY_CHILD_FK_ID +
                " AND input."+cSQLDBHelper.KEY_ID +" = ?";

        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(inputID)});

        try {
            if (cursor.moveToFirst()) {
                do {

                    cMaterialModel outputModel = new cMaterialModel();
/*
                    outputModel.setID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ID)));
                    outputModel.setServerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_SERVER_ID)));
                    outputModel.setOwnerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OWNER_ID)));
                    outputModel.setOrgID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ORG_ID)));
                    outputModel.setGroupBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_GROUP_BITS)));
                    outputModel.setPermsBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PERMS_BITS)));
                    outputModel.setStatusBITS(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_STATUS_BITS)));
                    outputModel.setName(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_NAME)));
                    outputModel.setDescription(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_DESCRIPTION)));
                    outputModel.setStartDate(formatter.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_START_DATE))));
                    outputModel.setEndDate(formatter.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_END_DATE))));
                    outputModel.setCreatedDate(formatter.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_CREATED_DATE))));
                    outputModel.setModifiedDate(formatter.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_MODIFIED_DATE))));
                    outputModel.setSyncedDate(formatter.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_SYNCED_DATE))));
*/
                    materialModels.add(outputModel);

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Exception in reading all ACTIVITY_OUTPUTs "+e.getMessage().toString());
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        // close the database connection
        db.close();

        return materialModels;
    }
}
