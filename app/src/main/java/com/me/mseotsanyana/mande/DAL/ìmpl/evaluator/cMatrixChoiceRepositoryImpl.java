package com.me.mseotsanyana.mande.DAL.ìmpl.evaluator;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.util.Pair;

import com.me.mseotsanyana.mande.DAL.model.evaluator.cColChoiceModel;
import com.me.mseotsanyana.mande.DAL.model.evaluator.cMatrixChoiceModel;
import com.me.mseotsanyana.mande.DAL.model.evaluator.cRowChoiceModel;
import com.me.mseotsanyana.mande.DAL.storage.database.cSQLDBHelper;
import com.me.mseotsanyana.mande.DAL.storage.preference.cBitwise;
import com.me.mseotsanyana.mande.UTIL.cConstant;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Set;

public class cMatrixChoiceRepositoryImpl {
    private static SimpleDateFormat sdf = cConstant.TIMESTAMP_FORMAT_DATE;
    private static String TAG = cMatrixChoiceRepositoryImpl.class.getSimpleName();

    /* an object of the database helper */
    private cSQLDBHelper dbHelper;

    public cMatrixChoiceRepositoryImpl(Context context) {
        dbHelper = new cSQLDBHelper(context);
    }

    /* ####################################### READ ACTIONS ######################################*/

    protected cMatrixChoiceModel getMatrixChoiceModelByID(
            long matrixChoiceID, long userID, int primaryRoleBITS, int secondaryRoleBITS,
            int statusBITS) {

        // open the connection to the database
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // construct a selection query
        String selectQuery = "SELECT * FROM " + cSQLDBHelper.TABLE_tblMATRIXSET +
                " WHERE ((" + cSQLDBHelper.KEY_ID + "= ? ) AND " +
                /* read access permissions */
                /* organization creator is always allowed to do everything (i.e., where: userID = orgID)*/
                " ((" + cSQLDBHelper.KEY_ORG_ID + " = ?) " +
                /* owner permission */
                " OR ((((" + cSQLDBHelper.KEY_OWNER_ID + " = ?) " +
                " AND ((" + cSQLDBHelper.KEY_PERMS_BITS + " & " + cBitwise.OWNER_READ + ") != 0))" +
                /* group (owner/primary organization) permission */
                " OR (((" + cSQLDBHelper.KEY_GROUP_BITS + " & ?) != 0) " +
                " AND ((" + cSQLDBHelper.KEY_PERMS_BITS + " & " + cBitwise.GROUP_READ + ") != 0))" +
                /* other (secondary organizations) permission */
                " OR (((" + cSQLDBHelper.KEY_GROUP_BITS + " & ?) != 0) " +
                " AND ((" + cSQLDBHelper.KEY_PERMS_BITS + " & " + cBitwise.OTHER_READ + ") != 0)))" +
                /* owner, group and other permissions allowed when the statuses hold */
                " AND ((" + cSQLDBHelper.KEY_STATUS_BITS + " = 0) " +
                " OR ((" + cSQLDBHelper.KEY_STATUS_BITS + " & ?) != 0)))))";

        Cursor cursor = db.rawQuery(selectQuery, new String[]{
                String.valueOf(matrixChoiceID),
                String.valueOf(userID),           /* access due to organization creator */
                String.valueOf(userID),           /* access due to record owner */
                String.valueOf(primaryRoleBITS),  /* access due to membership in primary role */
                String.valueOf(secondaryRoleBITS),/* access due to membership in secondary role */
                String.valueOf(statusBITS)});     /* access due to assigned statuses */

        cMatrixChoiceModel matrixChoice = new cMatrixChoiceModel();

        try {
            if (cursor.moveToFirst()) {
                matrixChoice.setMatrixChoiceID(
                        cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ID)));
                matrixChoice.setServerID(
                        cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_SERVER_ID)));
                matrixChoice.setOwnerID(
                        cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_OWNER_ID)));
                matrixChoice.setOrgID(
                        cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_ORG_ID)));
                matrixChoice.setGroupBITS(
                        cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_GROUP_BITS)));
                matrixChoice.setPermsBITS(
                        cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_PERMS_BITS)));
                matrixChoice.setStatusBITS(
                        cursor.getInt(cursor.getColumnIndex(cSQLDBHelper.KEY_STATUS_BITS)));
                matrixChoice.setCreatedDate(Timestamp.valueOf(
                        cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_CREATED_DATE))));
                matrixChoice.setModifiedDate(Timestamp.valueOf(
                        cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_MODIFIED_DATE))));
                matrixChoice.setSyncedDate(Timestamp.valueOf(
                        cursor.getString(cursor.getColumnIndex(cSQLDBHelper.KEY_SYNCED_DATE))));
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to read an MATRIX CHOICE entity: " + e.getMessage());
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

        // close the database connection
        db.close();

        return matrixChoice;
    }

    public Set<Pair<cRowChoiceModel, cColChoiceModel>> getMatrixChoiceModelSetByID(
            long questionID, long userID, int primaryRoleBITS, int secondaryRoleBITS,
            int statusBITS) {

        Set<Pair<cRowChoiceModel, cColChoiceModel>> matrixChoiceModelSet = new HashSet<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String selectQuery = "SELECT " +
                "RO." + cSQLDBHelper.KEY_ID + " AS _id_row, " +
                "RO." + cSQLDBHelper.KEY_SERVER_ID + " AS _id_row_server, " +
                "RO." + cSQLDBHelper.KEY_OWNER_ID + " AS _id_row_owner, " +
                "RO." + cSQLDBHelper.KEY_ORG_ID + " AS _id_row_org, " +
                "RO." + cSQLDBHelper.KEY_GROUP_BITS + " AS row_group_bits, " +
                "RO." + cSQLDBHelper.KEY_PERMS_BITS + " AS row_perms_bits, " +
                "RO." + cSQLDBHelper.KEY_STATUS_BITS + " AS row_status_bits, " +
                "RO." + cSQLDBHelper.KEY_NAME + " AS row_name, " +
                "RO." + cSQLDBHelper.KEY_DESCRIPTION + " AS row_description, " +
                "RO." + cSQLDBHelper.KEY_CREATED_DATE + " AS row_created_date, " +
                "RO." + cSQLDBHelper.KEY_MODIFIED_DATE + " AS row_modified_date, " +
                "RO." + cSQLDBHelper.KEY_SYNCED_DATE + " AS row_synced_date, " +
                "CO." + cSQLDBHelper.KEY_ID + " AS _id_col, " +
                "CO." + cSQLDBHelper.KEY_SERVER_ID + " AS _id_col_server, " +
                "CO." + cSQLDBHelper.KEY_OWNER_ID + " AS _id_col_owner, " +
                "CO." + cSQLDBHelper.KEY_ORG_ID + " AS _id_col_org, " +
                "CO." + cSQLDBHelper.KEY_GROUP_BITS + " AS col_group_bits, " +
                "CO." + cSQLDBHelper.KEY_PERMS_BITS + " AS col_perms_bits, " +
                "CO." + cSQLDBHelper.KEY_STATUS_BITS + " AS col_status_bits, " +
                "CO." + cSQLDBHelper.KEY_NAME + " AS col_name, " +
                "CO." + cSQLDBHelper.KEY_DESCRIPTION + " AS col_description, " +
                "CO." + cSQLDBHelper.KEY_CREATED_DATE + " AS col_created_date, " +
                "CO." + cSQLDBHelper.KEY_MODIFIED_DATE + " AS col_modified_date, " +
                "CO." + cSQLDBHelper.KEY_SYNCED_DATE + " AS col_synced_date, " +
                "MC." + cSQLDBHelper.KEY_OWNER_ID + ", " +
                "MC." + cSQLDBHelper.KEY_ORG_ID + ", " +
                "MC." + cSQLDBHelper.KEY_GROUP_BITS + ", " +
                "MC." + cSQLDBHelper.KEY_PERMS_BITS + ", " +
                "MC." + cSQLDBHelper.KEY_STATUS_BITS +
                " FROM " +
                cSQLDBHelper.TABLE_tblMATRIXSET +" MC, " +
                cSQLDBHelper.TABLE_tblROWCHOICE +" RO, " +
                cSQLDBHelper.TABLE_tblCOLCHOICE + " CO " +
                " WHERE (RO." + cSQLDBHelper.KEY_ID + " = MC." + cSQLDBHelper.KEY_ROW_CHOICE_FK_ID +
                " AND CO." + cSQLDBHelper.KEY_ID + " = MC." + cSQLDBHelper.KEY_COL_CHOICE_FK_ID +
                " AND (MC." + cSQLDBHelper.KEY_ID + " IN " +
                " (SELECT MC."+cSQLDBHelper.KEY_ID + " FROM " +
                cSQLDBHelper.TABLE_tblQUESTION +" Q, " +
                cSQLDBHelper.TABLE_tblMATRIXQUESTION +" T, " +
                cSQLDBHelper.TABLE_tblMATRIXSET +" C, " +
                cSQLDBHelper.TABLE_tblMATRIXCHOICESET + " MS " +
                " WHERE (Q." + cSQLDBHelper.KEY_ID + " = MS." + cSQLDBHelper.KEY_QUESTION_FK_ID +
                " AND T." + cSQLDBHelper.KEY_QUESTION_TYPE_FK_ID + " = MS." + cSQLDBHelper.KEY_MATRIX_QUESTION_FK_ID +
                " AND C." + cSQLDBHelper.KEY_ID + " = MS." + cSQLDBHelper.KEY_MATRIX_CHOICE_FK_ID +
                " AND Q." + cSQLDBHelper.KEY_ID + " = ? ))) " +
                " AND " +
                /* read access permissions */
                /* organization creator is always allowed to do everything (i.e., where: userID = orgID)*/
                " ((MC." + cSQLDBHelper.KEY_ORG_ID + " = ?) " +
                /* owner permission */
                " OR ((((MC." + cSQLDBHelper.KEY_OWNER_ID + " = ?) " +
                " AND ((MC." + cSQLDBHelper.KEY_PERMS_BITS + " & " + cBitwise.OWNER_READ + ") != 0))" +
                /* group (owner/primary organization) permission */
                " OR (((MC." + cSQLDBHelper.KEY_GROUP_BITS + " & ?) != 0) " +
                " AND ((MC." + cSQLDBHelper.KEY_PERMS_BITS + " & " + cBitwise.GROUP_READ + ") != 0))" +
                /* other (secondary organizations) permission */
                " OR (((MC." + cSQLDBHelper.KEY_GROUP_BITS + " & ?) != 0) " +
                " AND ((MC." + cSQLDBHelper.KEY_PERMS_BITS + " & " + cBitwise.OTHER_READ + ") != 0)))" +
                /* owner, group and other permissions allowed when the statuses hold */
                " AND ((MC." + cSQLDBHelper.KEY_STATUS_BITS + " = 0) " +
                " OR ((MC." + cSQLDBHelper.KEY_STATUS_BITS + " & ?) != 0)))))";

        Cursor cursor = db.rawQuery(selectQuery, new String[]{
                String.valueOf(questionID),
                String.valueOf(userID),           /* access due to organization creator */
                String.valueOf(userID),           /* access due to record owner */
                String.valueOf(primaryRoleBITS),  /* access due to membership in primary role */
                String.valueOf(secondaryRoleBITS),/* access due to membership in secondary role */
                String.valueOf(statusBITS)});     /* access due to assigned statuses */

        try {
            if (cursor.moveToFirst()) {
                do {
                    cRowChoiceModel rowOption = new cRowChoiceModel();
                    cColChoiceModel colOption = new cColChoiceModel();

                    /* row data */
                    rowOption.setRowChoiceID(
                            cursor.getInt(cursor.getColumnIndex("_id_row")));
                    rowOption.setServerID(
                            cursor.getInt(cursor.getColumnIndex("_id_row_server")));
                    rowOption.setOwnerID(
                            cursor.getInt(cursor.getColumnIndex("_id_row_owner")));
                    rowOption.setOrgID(
                            cursor.getInt(cursor.getColumnIndex("_id_row_org")));
                    rowOption.setGroupBITS(
                            cursor.getInt(cursor.getColumnIndex("row_group_bits")));
                    rowOption.setPermsBITS(
                            cursor.getInt(cursor.getColumnIndex("row_perms_bits")));
                    rowOption.setStatusBITS(
                            cursor.getInt(cursor.getColumnIndex("row_status_bits")));
                    rowOption.setName(
                            cursor.getString(cursor.getColumnIndex("row_name")));
                    rowOption.setDescription(
                            cursor.getString(cursor.getColumnIndex("row_description")));
                    rowOption.setCreatedDate(Timestamp.valueOf(
                            cursor.getString(cursor.getColumnIndex("row_created_date"))));
                    rowOption.setModifiedDate(Timestamp.valueOf(
                            cursor.getString(cursor.getColumnIndex("row_modified_date"))));
                    rowOption.setSyncedDate(Timestamp.valueOf(
                            cursor.getString(cursor.getColumnIndex("row_synced_date"))));

                    /* col data */
                    colOption.setColChoiceID(
                            cursor.getInt(cursor.getColumnIndex("_id_col")));
                    colOption.setServerID(
                            cursor.getInt(cursor.getColumnIndex("_id_col_server")));
                    colOption.setOwnerID(
                            cursor.getInt(cursor.getColumnIndex("_id_col_owner")));
                    colOption.setOrgID(
                            cursor.getInt(cursor.getColumnIndex("_id_col_org")));
                    colOption.setGroupBITS(
                            cursor.getInt(cursor.getColumnIndex("col_group_bits")));
                    colOption.setPermsBITS(
                            cursor.getInt(cursor.getColumnIndex("col_perms_bits")));
                    colOption.setStatusBITS(
                            cursor.getInt(cursor.getColumnIndex("col_status_bits")));
                    colOption.setName(
                            cursor.getString(cursor.getColumnIndex("col_name")));
                    colOption.setDescription(
                            cursor.getString(cursor.getColumnIndex("col_description")));
                    colOption.setCreatedDate(Timestamp.valueOf(
                            cursor.getString(cursor.getColumnIndex("col_created_date"))));
                    colOption.setModifiedDate(Timestamp.valueOf(
                            cursor.getString(cursor.getColumnIndex("col_modified_date"))));
                    colOption.setSyncedDate(Timestamp.valueOf(
                            cursor.getString(cursor.getColumnIndex("col_synced_date"))));

                    matrixChoiceModelSet.add(new Pair<>(rowOption, colOption));

                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to read a MATRIX CHOICE entity: " + e.getMessage());
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        // close the database connection
        db.close();

        return matrixChoiceModelSet;
    }

}
