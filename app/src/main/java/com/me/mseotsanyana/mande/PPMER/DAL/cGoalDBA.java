package com.me.mseotsanyana.mande.PPMER.DAL;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.me.mseotsanyana.treeadapterlibrary.cTreeModel;

import java.lang.String;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class cGoalDBA
{
	// an object of the database helper
	private cSQLDBHelper dbHelper;

    private static final SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
    private static final String TAG = "dbHelper";

    public cGoalDBA(Context context)
    {
        dbHelper = new cSQLDBHelper(context);
    }

    public boolean addGoalFromExcel(cGoalModel goalModel) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_OVERALLAIM_ID, goalModel.getGoalID());
        cv.put(cSQLDBHelper.KEY_ORGANIZATION_FK_ID, goalModel.getOrganizationID());
        cv.put(cSQLDBHelper.KEY_OVERALLAIM_NAME, goalModel.getGoalName());
        cv.put(cSQLDBHelper.KEY_OVERALLAIM_DESCRIPTION, goalModel.getGoalDescription());

        // insert value record
        try {
            if (db.insert(cSQLDBHelper.TABLE_OVERALLAIM, null, cv) < 0) {
                return false;
            }
        } catch (Exception ex) {
            Log.d("Exception in importing ", ex.getMessage().toString());
        }

        // close the database connection
        db.close();

        return true;
    }


    public boolean addGoal(cGoalModel goalModel){
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_OVERALLAIM_ID, goalModel.getGoalID());
        cv.put(cSQLDBHelper.KEY_ORGANIZATION_FK_ID, goalModel.getOrganizationID());
        cv.put(cSQLDBHelper.KEY_OVERALLAIM_OWNER_ID, goalModel.getOwnerID());
        cv.put(cSQLDBHelper.KEY_OVERALLAIM_NAME, goalModel.getGoalName());
        cv.put(cSQLDBHelper.KEY_OVERALLAIM_DESCRIPTION, goalModel.getGoalDescription());
        cv.put(cSQLDBHelper.KEY_ORGANIZATION_CREATED_DATE, formatter.format(goalModel.getCreateDate()));


        // insert impact record
        long result = db.insert(cSQLDBHelper.TABLE_OVERALLAIM,null,cv);

        // close the database connection
        db.close();

        return result > -1;
    }

    public boolean deleteAllGoals() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_OVERALLAIM, null, null);

        // close the database connection
        db.close();

        return result > -1;
    }

    public boolean deleteGoal(int impactID){
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete a record of a specific ID
        long result = db.delete(cSQLDBHelper.TABLE_OVERALLAIM, cSQLDBHelper.KEY_PROJECT_ID + "= ?", new String[] {String.valueOf(impactID)});

        // close the database connection
        db.close();

        return result > -1;
    }

    public boolean upadateGoal(cGoalModel goalModel){
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_OVERALLAIM_NAME, goalModel.getGoalName());
        cv.put(cSQLDBHelper.KEY_OVERALLAIM_DESCRIPTION, goalModel.getGoalDescription());

        // update a specific record
        long result = db.update(cSQLDBHelper.TABLE_OVERALLAIM, cv, cSQLDBHelper.KEY_PROJECT_ID
                + "= ?", new String[] { String.valueOf(goalModel.getGoalID()) });

        // close the database connection
        db.close();

        return result > -1;
    }

    public List<cGoalModel> getGoalList() {
        List<cGoalModel> goalModelList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+ cSQLDBHelper.TABLE_OVERALLAIM, null);

        try {
            if (cursor.moveToFirst()) {
                do {
                    cGoalModel goalModel = new cGoalModel();
                    // populate overall aim (goal) model object
                    goalModel.setGoalID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OVERALLAIM_ID)));
                    goalModel.setOrganizationID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ORGANIZATION_FK_ID)));
                    goalModel.setOwnerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OVERALLAIM_OWNER_ID)));
                    goalModel.setGoalName(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_OVERALLAIM_NAME)));
                    goalModel.setGoalDescription(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_OVERALLAIM_DESCRIPTION)));
                    goalModel.setCreateDate(formatter.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_OVERALLAIM_DATE))));
                    // add model overall aim (goal) into the action_list
                    goalModelList.add(goalModel);

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to get values from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        // close the database connection
        db.close();

        return goalModelList;
    }

    cGoalModel getGoalById(int goalID){
        // open connection to read only
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // construct a selection query
        String selectQuery =  "SELECT * FROM " +
                cSQLDBHelper.TABLE_OVERALLAIM + " WHERE " +
                cSQLDBHelper.KEY_OVERALLAIM_ID + "= ?";

        int iCount = 0;
        cGoalModel goalModel = new cGoalModel();

        // open the cursor to be used to traverse the dataset
        Cursor cursor = db.rawQuery(selectQuery, new String[] { String.valueOf(goalID) } );

        // looping to a record which satisfies the condition and store in cGoalModel object
        if (cursor.moveToFirst())
        {
            do
            {
                goalModel.setGoalID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OVERALLAIM_ID)));
                goalModel.setGoalName(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_OVERALLAIM_NAME)));
                goalModel.setGoalDescription(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_OVERALLAIM_DESCRIPTION)));
            } while (cursor.moveToNext());
        }

        // close the cursor
        cursor.close();

        // close the database connection
        db.close();

        return goalModel;
    }

    public boolean deleteAllGoalsByProject(int projectID){
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete a record of a specific ID
        long result = db.delete(cSQLDBHelper.TABLE_OVERALLAIM, cSQLDBHelper.KEY_OVERALLAIM_ID + "= ?", new String[] {String.valueOf(projectID)});

        // close the database connection
        db.close();

        return result > -1;
    }

    public List<cTreeModel> getGoalTree() {
        // open connection to read only
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // construct a selection query for goals
        String selectOverallAimQuery   = "SELECT * FROM " + cSQLDBHelper.TABLE_OVERALLAIM;

        // open the goal cursor to be used to traverse the dataset
        Cursor goalCursor = db.rawQuery(selectOverallAimQuery, null);

        // the action_list of two fields to be populated and returned
        List<cTreeModel> goalTreeList = new ArrayList<>();

        // looping through all goal rows and adding to tree action_list
        if (goalCursor.moveToFirst()) {
            try {
                do {
                    cTreeModel treeData = new cTreeModel(
                            goalCursor.getInt(goalCursor.getColumnIndex(cSQLDBHelper.KEY_OVERALLAIM_ID))+1,
                            goalCursor.getInt(goalCursor.getColumnIndex(cSQLDBHelper.KEY_ORGANIZATION_FK_ID)),
                            1,
                            new cGoalModel(
                                    goalCursor.getInt(goalCursor.getColumnIndex(cSQLDBHelper.KEY_OVERALLAIM_ID)),
                                    goalCursor.getInt(goalCursor.getColumnIndex(cSQLDBHelper.KEY_ORGANIZATION_FK_ID)),
                                    goalCursor.getInt(goalCursor.getColumnIndex(cSQLDBHelper.KEY_OVERALLAIM_OWNER_ID)),
                                    goalCursor.getString(goalCursor.getColumnIndex(cSQLDBHelper.KEY_OVERALLAIM_NAME)),
                                    goalCursor.getString(goalCursor.getColumnIndex(cSQLDBHelper.KEY_OVERALLAIM_DESCRIPTION)),
                                    formatter.parse(goalCursor.getString(goalCursor.getColumnIndex(cSQLDBHelper.KEY_OVERALLAIM_DATE)))
                            )
                    );

                    goalTreeList.add(treeData);

                } while (goalCursor.moveToNext());
            }catch (Exception e) {
                Log.d(TAG, "Error while trying to get values from database");
            } finally {
                if (goalCursor != null && !goalCursor.isClosed())
                    goalCursor.close();
            }
        }
        // close the database connection
        db.close();

        return goalTreeList;
    }
}
