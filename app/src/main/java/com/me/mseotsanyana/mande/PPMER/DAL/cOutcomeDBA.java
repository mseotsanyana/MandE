package com.me.mseotsanyana.mande.PPMER.DAL;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.lang.String;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class cOutcomeDBA {
	// an object of the database helper
	private cSQLDBHelper dbHelper;

	private static final SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
	private static final String TAG = "dbHelper";

	public cOutcomeDBA(Context context) {
		dbHelper = new cSQLDBHelper(context);
	}


    public boolean deleteAllOutcomes() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_OUTCOME, null, null);

        // close the database connection
        db.close();

        return result > -1;
    }

	public boolean addOutcome(cOutcomeModel outcomeModel) {
		// open the connection to the database
		SQLiteDatabase db = dbHelper.getWritableDatabase();

		// create content object for storing data
		ContentValues cv = new ContentValues();

		// assign values to the table fields
		cv.put(cSQLDBHelper.KEY_OUTCOME_ID, outcomeModel.getOutcomeID());
		cv.put(cSQLDBHelper.KEY_OUTCOME_OWNER_ID, outcomeModel.getOwnerID());
		cv.put(cSQLDBHelper.KEY_OUTCOME_NAME, outcomeModel.getOutcomeName());
		cv.put(cSQLDBHelper.KEY_OUTCOME_DESCRIPTION, outcomeModel.getOutcomeDescription());
		cv.put(cSQLDBHelper.KEY_OUTCOME_DATE, formatter.format(outcomeModel.getCreateDate()));

		// insert outcome record
		try {
			if (db.insert(cSQLDBHelper.TABLE_OUTCOME, null, cv) < 0) {
				return false;
			}
		} catch (Exception ex) {
			Log.d("Exception in importing ", ex.getMessage().toString());
		}

		// close the database connection
		db.close();

		return true;
	}

    public cOutcomeModel getOutcomeByID(int outcomeID) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        // construct a selection query
        String selectQuery = "SELECT * FROM " +
                cSQLDBHelper.TABLE_OUTCOME + " WHERE " +
                cSQLDBHelper.KEY_OUTCOME_ID + "= ?";

        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(outcomeID)});

        cOutcomeModel outcome = new cOutcomeModel();

        try {
            if (cursor.moveToFirst()) {
                do {
                    outcome.setOutcomeID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OUTCOME_ID)));
                    outcome.setOwnerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OUTCOME_OWNER_ID)));
                    outcome.setOutcomeName(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_OUTCOME_NAME)));
                    outcome.setOutcomeDescription(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_OUTCOME_DESCRIPTION)));
                    outcome.setCreateDate(formatter.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_OUTCOME_DATE))));

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

        return outcome;
    }


    public List<cOutcomeModel> getOutcomeList() {

        List<cOutcomeModel> outcomeModelList = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+ cSQLDBHelper.TABLE_OUTCOME, null);

        try {
            if (cursor.moveToFirst()) {
                do {
                    cOutcomeModel outcome = new cOutcomeModel();

                    outcome.setOutcomeID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OUTCOME_ID)));
                    outcome.setOwnerID(cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OUTCOME_OWNER_ID)));
                    outcome.setOutcomeName(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_OUTCOME_NAME)));
                    outcome.setOutcomeDescription(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_OUTCOME_DESCRIPTION)));
                    outcome.setCreateDate(formatter.parse(cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_OUTCOME_DATE))));

                    outcomeModelList.add(outcome);

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

        return outcomeModelList;
    }
}
