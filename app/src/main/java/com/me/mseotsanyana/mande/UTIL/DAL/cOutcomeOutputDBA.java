package com.me.mseotsanyana.mande.UTIL.DAL;

import android.content.Context;

import com.me.mseotsanyana.mande.DAL.ìmpl.logframe.cOutcomeRepositoryImpl;
import com.me.mseotsanyana.mande.DAL.ìmpl.logframe.cOutputRepositoryImpl;
import com.me.mseotsanyana.mande.DAL.storage.database.cSQLDBHelper;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by mseotsanyana on 2017/06/27.
 */

public class cOutcomeOutputDBA {
    // an object of the database helper
    private cSQLDBHelper dbHelper;

    private cOutcomeRepositoryImpl outcomeDBA;
    private cOutputRepositoryImpl outputDBA;

    private static final SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
    private static final String TAG = "dbHelper";

    public cOutcomeOutputDBA(Context context) {
        dbHelper = new cSQLDBHelper(context);
        outcomeDBA = new cOutcomeRepositoryImpl(context);
        outputDBA = new cOutputRepositoryImpl(context);
    }
/*
    public boolean addOutcomeOutput(cOutcomeOutputModel outcomeOutputModel){
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long result = -1;

        // get an outcome by id
        cOutcomeModel outcomeModel = outcomeDBA.getOutcomeByID(outcomeOutputModel.getOutcomeID());
        // get a output by id
        cOutputModel outputModel = outputDBA.getOutputByID(outcomeOutputModel.getOutputID());

        if ((outcomeModel != null) && (outputModel != null)){

            ContentValues cv = new ContentValues();

            cv.put(cSQLDBHelper.KEY_OUTCOME_FK_ID, outcomeModel.getOutcomeID());
            cv.put(cSQLDBHelper.KEY_OUTPUT_FK_ID, outputModel.getOutputID());
            cv.put(cSQLDBHelper.KEY_OUTCOME_OUTPUT_OWNER_ID, outcomeOutputModel.getOwnerID());
            cv.put(cSQLDBHelper.KEY_OUTCOME_OUTPUT_DATE, formatter.format(outcomeOutputModel.getCreateDate()));

            // insert outcome output record
            result = db.insert(cSQLDBHelper.TABLE_OUTCOME_OUTPUT, null, cv);
        }

        // close the database connection
        db.close();

        return result > -1;
    }

    public List<cOutcomeOutputModel> getOutcomeOutputList() {

        List<cOutcomeOutputModel> outcomeOutputModelList = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+ cSQLDBHelper.TABLE_OUTCOME_OUTPUT, null);

        try {
            if (cursor.moveToFirst()) {
                do {
                    cOutcomeOutputModel outcome = new cOutcomeOutputModel();

                    outcome.setOutcomeID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OUTCOME_FK_ID)));
                    outcome.setOutputID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OUTPUT_FK_ID)));
                    outcome.setOwnerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OUTCOME_OUTPUT_OWNER_ID)));
                    outcome.setCreateDate(formatter.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_OUTCOME_OUTPUT_DATE))));

                    outcomeOutputModelList.add(outcome);

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

        return outcomeOutputModelList;
    }

 */
}
