package com.me.mseotsanyana.mande.UTILITY.DAL;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.me.mseotsanyana.mande.STORAGE.database.cSQLDBHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class cObjectiveDBA {
    // an object of the database helper
    private cSQLDBHelper dbHelper;

    private static final SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
    private static final String TAG = "dbHelper";

    public cObjectiveDBA(Context context) {
        dbHelper = new cSQLDBHelper(context);
    }


    public boolean deleteAllObjectives() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_OBJECTIVE, null, null);

        // close the database connection
        db.close();

        return result > -1;
    }

    public boolean addObjective(cObjectiveModel objectiveModel) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_OBJECTIVE_ID, objectiveModel.getObjectiveID());
        cv.put(cSQLDBHelper.KEY_PROJECT_FK_ID, objectiveModel.getProjectID());
        cv.put(cSQLDBHelper.KEY_OBJECTIVE_OWNER_ID, objectiveModel.getOwnerID());
        cv.put(cSQLDBHelper.KEY_OBJECTIVE_NAME, objectiveModel.getObjectiveName());
        cv.put(cSQLDBHelper.KEY_OBJECTIVE_DESCRIPTION, objectiveModel.getObjectiveDescription());
        cv.put(cSQLDBHelper.KEY_OBJECTIVE_DATE, formatter.format(objectiveModel.getCreateDate()));

        // insert objective record
        try {
            if (db.insert(cSQLDBHelper.TABLE_OBJECTIVE, null, cv) < 0) {
                return false;
            }
        } catch (Exception ex) {
            Log.d("Exception in importing ", ex.getMessage().toString());
        }

        // close the database connection
        db.close();

        return true;
    }

    public cObjectiveModel getObjectiveByID(int objectiveID) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        // construct a selection query
        String selectQuery = "SELECT * FROM " +
                cSQLDBHelper.TABLE_OBJECTIVE + " WHERE " +
                cSQLDBHelper.KEY_OBJECTIVE_ID + "= ?";

        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(objectiveID)});

        cObjectiveModel objective = new cObjectiveModel();

        try {
            if (cursor.moveToFirst()) {
                do {
                    objective.setObjectiveID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OBJECTIVE_ID)));
                    objective.setProjectID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PROJECT_FK_ID)));
                    objective.setOwnerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OBJECTIVE_OWNER_ID)));
                    objective.setObjectiveName(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_OBJECTIVE_NAME)));
                    objective.setObjectiveDescription(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_OBJECTIVE_DESCRIPTION)));
                    objective.setCreateDate(formatter.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_OBJECTIVE_DATE))));

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

        return objective;
    }


    public List<cObjectiveModel> getObjectiveList() {

        List<cObjectiveModel> objectiveModelList = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+ cSQLDBHelper.TABLE_OBJECTIVE, null);

        try {
            if (cursor.moveToFirst()) {
                do {
                    cObjectiveModel objective = new cObjectiveModel();

                    objective.setObjectiveID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OBJECTIVE_ID)));
                    objective.setProjectID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PROJECT_FK_ID)));
                    objective.setOwnerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OBJECTIVE_OWNER_ID)));
                    objective.setObjectiveName(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_OBJECTIVE_NAME)));
                    objective.setObjectiveDescription(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_OBJECTIVE_DESCRIPTION)));
                    objective.setCreateDate(formatter.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_OBJECTIVE_DATE))));

                    objectiveModelList.add(objective);

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

        return objectiveModelList;
    }
}
