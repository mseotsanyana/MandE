package com.me.mseotsanyana.mande.PPMER.DAL;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.me.mseotsanyana.treeadapterlibrary.cTreeModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class cProjectDBA {

    private static final SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy",Locale.US);

    // an object of the database helper
    private cSQLDBHelper dbHelper;
    private static final String TAG = "dbHelper";

    public cProjectDBA(Context context) {
        dbHelper = new cSQLDBHelper(context);
    }

    /**
     * this function adds the project details
     *
     * @param project
     * @return result
     */
    public boolean addProject(cProjectModel project) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_PROJECT_ID, project.getProjectID());
        cv.put(cSQLDBHelper.KEY_OVERALLAIM_FK_ID, project.getOverallAimID());
        cv.put(cSQLDBHelper.KEY_SPECIFICAIM_FK_ID, project.getSpecificAimID());
        cv.put(cSQLDBHelper.KEY_PROJECT_OWNER_ID, project.getOwnerID());
        cv.put(cSQLDBHelper.KEY_PROJECT_MANAGER_ID, project.getProjectManagerID());
        cv.put(cSQLDBHelper.KEY_PROJECT_NAME, project.getProjectName());
        cv.put(cSQLDBHelper.KEY_PROJECT_DESCRIPTION, project.getProjectDescription());
        cv.put(cSQLDBHelper.KEY_PROJECT_COUNTRY, project.getCountry());
        cv.put(cSQLDBHelper.KEY_PROJECT_REGION, project.getRegion());
        cv.put(cSQLDBHelper.KEY_PROJECT_STATUS, project.getProjectStatus());
        cv.put(cSQLDBHelper.KEY_PROJECT_DATE, formatter.format(project.getCreateDate()));
        cv.put(cSQLDBHelper.KEY_PROJECT_START_DATE, formatter.format(project.getStartDate()));
        cv.put(cSQLDBHelper.KEY_PROJECT_CLOSE_DATE, formatter.format(project.getCloseDate()));

        // insert project record
        long result = db.insert(cSQLDBHelper.TABLE_PROJECT, null, cv);

        // close the database connection
        db.close();

        return result > -1;
    }


    public boolean addProjectFromExcel(cProjectModel project) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_PROJECT_ID, project.getProjectID());
        cv.put(cSQLDBHelper.KEY_OVERALLAIM_FK_ID, project.getOverallAimID());
        cv.put(cSQLDBHelper.KEY_SPECIFICAIM_FK_ID, project.getSpecificAimID());
        cv.put(cSQLDBHelper.KEY_PROJECT_OWNER_ID, project.getOwnerID());
        cv.put(cSQLDBHelper.KEY_PROJECT_MANAGER_ID, project.getProjectManagerID());
        cv.put(cSQLDBHelper.KEY_PROJECT_NAME, project.getProjectName());
        cv.put(cSQLDBHelper.KEY_PROJECT_DESCRIPTION, project.getProjectDescription());
        cv.put(cSQLDBHelper.KEY_PROJECT_COUNTRY, project.getCountry());
        cv.put(cSQLDBHelper.KEY_PROJECT_REGION, project.getRegion());
        cv.put(cSQLDBHelper.KEY_PROJECT_STATUS, project.getProjectStatus());
        cv.put(cSQLDBHelper.KEY_PROJECT_DATE, formatter.format(project.getCreateDate()));
        cv.put(cSQLDBHelper.KEY_PROJECT_START_DATE, formatter.format(project.getStartDate()));
        cv.put(cSQLDBHelper.KEY_PROJECT_CLOSE_DATE, formatter.format(project.getCloseDate()));

        try {
            if (db.insert(cSQLDBHelper.TABLE_PROJECT, null, cv) < 0) {
                return false;
            }
        } catch (Exception ex) {
            Log.d("Exception in importing ", ex.getMessage().toString());
        }

        // close the database connection
        db.close();

        return true;
    }


    public boolean deleteProject(cProjectModel project)
    {
        // before deleting project delete all impacts/goals under this project
        // to enforce referential constraints which is not automatically supported
        // in SQLite android
        //impactDBA.deleteAllImpactsByProject(projectID);

        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete a record of a specific ID
        long result = db.delete(cSQLDBHelper.TABLE_PROJECT, cSQLDBHelper.KEY_PROJECT_ID + " = ?", new String[]{String.valueOf(project.getProjectID())});

        // close the database connection
        db.close();

        return result > -1;
    }


    public boolean deleteAllProjects() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_PROJECT, null, null);

        // close the database connection
        db.close();

        return result > -1;
    }

    public boolean updateProject(cProjectModel project)
    {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_PROJECT_MANAGER_ID,project.getProjectManagerID());
        cv.put(cSQLDBHelper.KEY_PROJECT_NAME, project.getProjectName());
        cv.put(cSQLDBHelper.KEY_PROJECT_DESCRIPTION, project.getProjectDescription());
        cv.put(cSQLDBHelper.KEY_PROJECT_COUNTRY, project.getCountry());
        cv.put(cSQLDBHelper.KEY_PROJECT_REGION, project.getRegion());
        cv.put(cSQLDBHelper.KEY_PROJECT_START_DATE,formatter.format(project.getStartDate()));
        cv.put(cSQLDBHelper.KEY_PROJECT_CLOSE_DATE,formatter.format(project.getCloseDate()));
        cv.put(cSQLDBHelper.KEY_PROJECT_STATUS, project.getProjectStatus());


        // update a specific record
        long result = db.update(cSQLDBHelper.TABLE_PROJECT, cv, cSQLDBHelper.KEY_PROJECT_ID + "= ?", new String[]{String.valueOf(project.getProjectID())});

        // close the database connection
        db.close();

        return result > -1;
    }

    public Cursor getAllProject() {

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor projectListCursor = db.rawQuery("SELECT * FROM "+ cSQLDBHelper.TABLE_PROJECT, null);

        if (projectListCursor != null)
        {
            projectListCursor.moveToFirst();
        }

        return projectListCursor;
    }

     /*
   fetch all data from UserTable
    */
    public List<cProjectModel> getProjectList() {

        List<cProjectModel> projectModelList = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+ cSQLDBHelper.TABLE_PROJECT, null);

        try {
            if (cursor.moveToFirst()) {
                do {
                    cProjectModel project = new cProjectModel();

                    project.setProjectID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PROJECT_ID)));
                    project.setOverallAimID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OVERALLAIM_FK_ID)));
                    project.setSpecificAimID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_SPECIFICAIM_FK_ID)));
                    project.setOwnerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PROJECT_OWNER_ID)));
                    project.setProjectManagerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PROJECT_MANAGER_ID)));
                    project.setProjectName(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_PROJECT_NAME)));
                    project.setProjectDescription(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_PROJECT_DESCRIPTION)));
                    project.setCountry(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_PROJECT_COUNTRY)));
                    project.setRegion(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_PROJECT_REGION)));
                    project.setProjectStatus(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PROJECT_STATUS)));
                    project.setStartDate(formatter.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_PROJECT_START_DATE))));
                    project.setCloseDate(formatter.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_PROJECT_CLOSE_DATE))));
                    project.setCreateDate(formatter.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_PROJECT_DATE))));

                    projectModelList.add(project);

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

        return projectModelList;
    }


    cProjectModel getProjectByID(int projectID) {
        // open connection to read only
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // construct a selection query
        String selectQuery = "SELECT * FROM " +
                cSQLDBHelper.TABLE_PROJECT + " WHERE " +
                cSQLDBHelper.KEY_PROJECT_ID + "= ?";

        cProjectModel project = new cProjectModel();

        // open the cursor to be used to traverse the dataset
        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(projectID)});

        try {
            // looping to a record which satisfies the condition and store in cProjectModel object
            if (cursor.moveToFirst()) {
                do {
                    project.setProjectID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PROJECT_ID)));
                    project.setOverallAimID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OVERALLAIM_FK_ID)));
                    project.setSpecificAimID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_SPECIFICAIM_FK_ID)));
                    project.setOwnerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PROJECT_OWNER_ID)));
                    project.setProjectManagerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PROJECT_MANAGER_ID)));
                    project.setProjectName(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_PROJECT_NAME)));
                    project.setProjectDescription(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_PROJECT_DESCRIPTION)));
                    project.setCountry(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_PROJECT_COUNTRY)));
                    project.setRegion(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_PROJECT_REGION)));
                    project.setProjectStatus(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PROJECT_STATUS)));
                    project.setStartDate(formatter.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_PROJECT_START_DATE))));
                    project.setCloseDate(formatter.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_PROJECT_CLOSE_DATE))));
                    project.setCreateDate(formatter.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_PROJECT_DATE))));
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to get projects from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        // close the cursor
        cursor.close();

        // close the database connection
        db.close();

        return project;
    }


    public List<cTreeModel> getProjectTree() {
        // open connection to read only
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // construct a selection query for projects
        String selectProjectQuery   = "SELECT * FROM " + cSQLDBHelper.TABLE_PROJECT;

        // open the goal cursor to be used to traverse the dataset
        Cursor projectCursor = db.rawQuery(selectProjectQuery, null);

        // the action_list of two fields to be populated and returned
        List<cTreeModel> projectTreeList = new ArrayList<>();

        // looping through all goal rows and adding to tree action_list
        if (projectCursor.moveToFirst()) {
            try {
                do {
                    cTreeModel treeData = new cTreeModel(
                            projectCursor.getInt(projectCursor.getColumnIndex(cSQLDBHelper.KEY_PROJECT_ID))+1,
                            projectCursor.getInt(projectCursor.getColumnIndex(cSQLDBHelper.KEY_OVERALLAIM_FK_ID)),
                            1,
                            new cProjectModel(
                                    projectCursor.getInt(projectCursor.getColumnIndex(cSQLDBHelper.KEY_PROJECT_ID)),
                                    projectCursor.getInt(projectCursor.getColumnIndex(cSQLDBHelper.KEY_OVERALLAIM_FK_ID)),
                                    projectCursor.getInt(projectCursor.getColumnIndex(cSQLDBHelper.KEY_SPECIFICAIM_FK_ID)),
                                    projectCursor.getInt(projectCursor.getColumnIndex(cSQLDBHelper.KEY_PROJECT_OWNER_ID)),
                                    projectCursor.getInt(projectCursor.getColumnIndex(cSQLDBHelper.KEY_PROJECT_MANAGER_ID)),
                                    projectCursor.getString(projectCursor.getColumnIndex(cSQLDBHelper.KEY_PROJECT_NAME)),
                                    projectCursor.getString(projectCursor.getColumnIndex(cSQLDBHelper.KEY_PROJECT_DESCRIPTION)),
                                    projectCursor.getString(projectCursor.getColumnIndex(cSQLDBHelper.KEY_PROJECT_COUNTRY)),
                                    projectCursor.getString(projectCursor.getColumnIndex(cSQLDBHelper.KEY_PROJECT_REGION)),
                                    projectCursor.getInt(projectCursor.getColumnIndex(cSQLDBHelper.KEY_PROJECT_STATUS)),
                                    formatter.parse(projectCursor.getString(projectCursor.getColumnIndex(cSQLDBHelper.KEY_PROJECT_START_DATE))),
                                    formatter.parse(projectCursor.getString(projectCursor.getColumnIndex(cSQLDBHelper.KEY_PROJECT_CLOSE_DATE))),
                                    formatter.parse(projectCursor.getString(projectCursor.getColumnIndex(cSQLDBHelper.KEY_PROJECT_CLOSE_DATE)))
                            )
                    );

                    projectTreeList.add(treeData);

                } while (projectCursor.moveToNext());
            }catch (Exception e) {
                Log.d(TAG, "Error while trying to get values from database");
            } finally {
                if (projectCursor != null && !projectCursor.isClosed())
                    projectCursor.close();
            }
        }
        // close the database connection
        db.close();

        return projectTreeList;
    }
}