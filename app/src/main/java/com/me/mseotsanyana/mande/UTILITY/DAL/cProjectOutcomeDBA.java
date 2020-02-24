package com.me.mseotsanyana.mande.UTILITY.DAL;

import android.content.Context;

import com.me.mseotsanyana.mande.PPMER.DAL.impl.cLogFrameRepositoryImpl;
import com.me.mseotsanyana.mande.PPMER.DAL.impl.cOutcomeImpl;
import com.me.mseotsanyana.mande.STORAGE.database.cSQLDBHelper;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by mseotsanyana on 2017/06/26.
 */

public class cProjectOutcomeDBA {
    // an object of the database helper
    private cSQLDBHelper dbHelper;

    private cLogFrameRepositoryImpl projectDBA;
    private cOutcomeImpl outcomeDBA;

    private static final SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
    private static final String TAG = "dbHelper";

    public cProjectOutcomeDBA(Context context) {
        dbHelper = new cSQLDBHelper(context);
        projectDBA = new cLogFrameRepositoryImpl(context);
        outcomeDBA = new cOutcomeImpl(context);
    }
/*
    public boolean addProjectOutcome(cProjectOutcomeModel projectOutcomeModel){
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long result = -1;

        // get a project by id
        cLogFrameModel projectModel = projectDBA.getProjectByID(projectOutcomeModel.getProjectID());
        // get an outcome by id
        cOutcomeModel outcomeModel = outcomeDBA.getOutcomeByID(projectOutcomeModel.getOutcomeID());

        if ((projectModel != null) && (outcomeModel != null)){

            ContentValues cv = new ContentValues();

            cv.put(cSQLDBHelper.KEY_PROJECT_FK_ID, projectModel.getProjectID());
            cv.put(cSQLDBHelper.KEY_OUTCOME_FK_ID, outcomeModel.getOutcomeID());
            cv.put(cSQLDBHelper.KEY_PROJECT_OUTCOME_OWNER_ID, projectOutcomeModel.getOwnerID());
            cv.put(cSQLDBHelper.KEY_PROJECT_OUTCOME_DATE, formatter.format(projectOutcomeModel.getCreateDate()));

            // insert project outcome record
            result = db.insert(cSQLDBHelper.TABLE_PROJECT_OUTCOME, null, cv);
        }

        // close the database connection
        db.close();

        return result > -1;
    }

    public List<cProjectOutcomeModel> getProjectOutcomeList() {

        List<cProjectOutcomeModel> projectOutcomeModelList = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+ cSQLDBHelper.TABLE_PROJECT_OUTCOME, null);

        try {
            if (cursor.moveToFirst()) {
                do {
                    cProjectOutcomeModel project = new cProjectOutcomeModel();

                    project.setProjectID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PROJECT_FK_ID)));
                    project.setOutcomeID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OUTCOME_FK_ID)));
                    project.setOwnerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PROJECT_OUTCOME_OWNER_ID)));
                    project.setCreateDate(formatter.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_PROJECT_OUTCOME_DATE))));

                    projectOutcomeModelList.add(project);

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

        return projectOutcomeModelList;
    }

 */
}
