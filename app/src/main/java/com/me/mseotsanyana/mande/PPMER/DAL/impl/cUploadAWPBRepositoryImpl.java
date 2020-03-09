package com.me.mseotsanyana.mande.PPMER.DAL.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.util.Pair;

import com.me.mseotsanyana.mande.PPMER.BLL.repository.iUploadAWPBRepository;
import com.me.mseotsanyana.mande.PPMER.DAL.model.cDocumentModel;
import com.me.mseotsanyana.mande.PPMER.DAL.model.cExternalModel;
import com.me.mseotsanyana.mande.PPMER.DAL.model.cInternalModel;
import com.me.mseotsanyana.mande.PPMER.DAL.model.cInvoiceModel;
import com.me.mseotsanyana.mande.PPMER.DAL.model.cJournalModel;
import com.me.mseotsanyana.mande.PPMER.DAL.model.cTaskModel;
import com.me.mseotsanyana.mande.PPMER.DAL.model.cTransactionModel;
import com.me.mseotsanyana.mande.STORAGE.database.cSQLDBHelper;
import com.me.mseotsanyana.mande.STORAGE.excel.cExcelHelper;
import com.me.mseotsanyana.mande.UTILITY.cConstant;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class cUploadAWPBRepositoryImpl implements iUploadAWPBRepository {
    private static SimpleDateFormat sdf = cConstant.FORMAT_DATE;
    private static String TAG = cUploadAWPBRepositoryImpl.class.getSimpleName();

    /* an object of the database helper */
    private cSQLDBHelper dbHelper;
    /* an object of the excel helper */
    private cExcelHelper excelHelper;

    public cUploadAWPBRepositoryImpl(Context context) {
        dbHelper = new cSQLDBHelper(context);
        excelHelper = new cExcelHelper(context);
    }

    @Override
    public boolean addTaskFromExcel() {
        Workbook workbook = excelHelper.getWorkbookAWPB();
        Sheet taskSheet = workbook.getSheet(cExcelHelper.SHEET_tblTASK);
        if (taskSheet == null) {
            return false;
        }

        for (Iterator<Row> ritTask = taskSheet.iterator(); ritTask.hasNext(); ) {
            Row cRow = ritTask.next();

            //just skip the row if row number is 0
            if (cRow.getRowNum() == 0) {
                continue;
            }

            cTaskModel taskModel = new cTaskModel();

            taskModel.setTaskID((int) cRow.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            taskModel.setName(cRow.getCell(1, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
            taskModel.setDescription(cRow.getCell(2, Row.CREATE_NULL_AS_BLANK).getStringCellValue());

            /* get task of the activity */
            Set<Pair<Integer, Integer>> taskSet = new HashSet<>();
            int taskID, activityID;
            Sheet activityTaskSheet = workbook.getSheet(cExcelHelper.SHEET_tblACTIVITYTASK);
            for (Iterator<Row> ritActivityTask = activityTaskSheet.iterator(); ritActivityTask.hasNext(); ) {
                Row rowActivityTask = ritActivityTask.next();

                //just skip the row if row number is 0
                if (rowActivityTask.getRowNum() == 0) {
                    continue;
                }

                taskID = (int) rowActivityTask.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                if (taskModel.getTaskID() == taskID) {
                    activityID = (int) rowActivityTask.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                    taskSet.add(new Pair<>(taskID, activityID));
                }
            }

/*
            Map<Integer, Set<Pair<Integer, Integer>>> tamMap = new HashMap<>();
            Set<Pair<Integer, Integer>> amSet = new HashSet<>();
            int taskID, activityID, milestoneID;

            Sheet tamSheet = workbook.getSheet(cExcelHelper.SHEET_tblACTIVITYTASK);
            for (Iterator<Row> ritTAM = tamSheet.iterator(); ritTAM.hasNext(); ) {
                Row rowTAM = ritTAM.next();

                //just skip the row if row number is 0
                if (rowTAM.getRowNum() == 0) {
                    continue;
                }

                taskID = (int) rowTAM.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                if (taskModel.getTaskID() == taskID) {
                    activityID = (int) rowTAM.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                    milestoneID = (int) rowTAM.getCell(2, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                    amSet.add(new Pair<>(activityID, milestoneID));
                }
            }
            tamMap.put(taskModel.getTaskID(), amSet);
            */

            /* get preceding tasks of the task */
            Set<Pair<Integer, Integer>> precedingSet = new HashSet<>();
            int precedingTaskID, succeedingTaskID;
            Sheet precedingTaskSheet = workbook.getSheet(cExcelHelper.SHEET_tblPRECEDINGTASK);
            for (Iterator<Row> ritPrecedingTask = precedingTaskSheet.iterator(); ritPrecedingTask.hasNext(); ) {
                Row rowPrecedingTask = ritPrecedingTask.next();

                //just skip the row if row number is 0
                if (rowPrecedingTask.getRowNum() == 0) {
                    continue;
                }

                precedingTaskID = (int) rowPrecedingTask.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                if (taskModel.getTaskID() == precedingTaskID) {
                    succeedingTaskID = (int) rowPrecedingTask.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                    precedingSet.add(new Pair<>(precedingTaskID, succeedingTaskID));
                }
            }

            /* get milestone for the task */
            Set<Pair<Integer, Integer>> tmSet = new HashSet<>();
            taskID = -1; int milestoneID = -1;
            Sheet tmSheet = workbook.getSheet(cExcelHelper.SHEET_tblTASK_MILESTONE);
            for (Iterator<Row> ritTM = tmSheet.iterator(); ritTM.hasNext(); ) {
                Row rowTM = ritTM.next();

                //just skip the row if row number is 0
                if (rowTM.getRowNum() == 0) {
                    continue;
                }

                taskID = (int) rowTM.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                if (taskModel.getTaskID() == taskID) {
                    milestoneID = (int) rowTM.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                    tmSet.add(new Pair<>(taskID, milestoneID));
                }
            }

            /* get task assignment */
            Sheet tstSheet = workbook.getSheet(cExcelHelper.SHEET_tblTASKASSIGNMENT);

            /* get time sheets */
            Sheet timesheetSheet = workbook.getSheet(cExcelHelper.SHEET_tblTIMESHEET);

            /* get invoice for timesheet sheets */
            Sheet itSheet = workbook.getSheet(cExcelHelper.SHEET_tblINVOICE_TIMESHEET);

            /* get task comments */
            Sheet commentSheet = workbook.getSheet(cExcelHelper.SHEET_tblUSERCOMMENT);

            if (!addTask(taskModel, taskSet, precedingSet, tmSet,
                    tstSheet, timesheetSheet, commentSheet, itSheet)) {
                return false;
            }
        }
        return true;
    }

    public boolean addTask(cTaskModel taskModel,
                           Set<Pair<Integer, Integer>> activityTaskSet,
                           Set<Pair<Integer, Integer>> precedingSet,
                           Set<Pair<Integer, Integer>> tmSet,
                           Sheet tstSheet, Sheet timesheetSheet, Sheet commentSheet, Sheet itSheet) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_ID, taskModel.getTaskID());
        cv.put(cSQLDBHelper.KEY_NAME, taskModel.getName());
        cv.put(cSQLDBHelper.KEY_DESCRIPTION, taskModel.getDescription());

        // insert project details
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblTASK, null, cv) < 0) {
                return false;
            }

            // add activity task
            for (Pair<Integer, Integer> pair : activityTaskSet) {
                int taskID = pair.first;
                int activityID = pair.second;
                if (addActivityTask(taskID, activityID))
                    continue;
                else
                    return false;
            }

            /* add preceding task */
            for (Pair<Integer, Integer> pair : precedingSet) {
                int succeedingID = pair.first;
                int precedingID = pair.second;
                if (addPrecedingTask(succeedingID, precedingID))
                    continue;
                else
                    return false;
            }

            /* add milestone task */
            for (Pair<Integer, Integer> pair : tmSet) {
                int taskID = pair.first;
                int milestoneID = pair.second;
                if (addTaskMilestone(taskID, milestoneID))
                    continue;
                else
                    return false;
            }


            /* add assigned task */
            int assignmentID = -1, assignedStaffID = -1, assignedTaskID = -1, assignedHours = 0;
            double assignedRate = 0.0;
            for (Iterator<Row> ritTST = tstSheet.iterator(); ritTST.hasNext(); ) {
                Row rowTST = ritTST.next();

                //just skip the row if row number is 0
                if (rowTST.getRowNum() == 0) {
                    continue;
                }

                assignedTaskID = (int) rowTST.getCell(2, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                if (taskModel.getTaskID() == assignedTaskID) {
                    assignmentID = (int) rowTST.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                    assignedStaffID = (int) rowTST.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                    assignedHours = (int) rowTST.getCell(3, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                    assignedRate = (int) rowTST.getCell(4, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();

                    if (!addAssignedTask(assignmentID, assignedStaffID,
                            assignedTaskID, assignedHours, assignedRate)) {
                        return false;
                    }
                }
            }

            /* add comments for a task */
            int commentID = -1, commentStaffID = -1, commentTaskID = -1;
            String comment = null;
            for (Iterator<Row> ritUC = commentSheet.iterator(); ritUC.hasNext(); ) {
                Row rowUC = ritUC.next();

                //just skip the row if row number is 0
                if (rowUC.getRowNum() == 0) {
                    continue;
                }

                commentTaskID = (int) rowUC.getCell(2, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                if (taskModel.getTaskID() == commentTaskID) {
                    commentID = (int) rowUC.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                    commentStaffID = (int) rowUC.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                    comment = rowUC.getCell(3, Row.CREATE_NULL_AS_BLANK).getStringCellValue();

                    if (!addCommentTask(commentID, commentStaffID, commentTaskID, comment)) {
                        return false;
                    }
                }
            }

            /* add timesheet for a task */
            int timesheetID = -1, timesheetStaffID = -1, timesheetTaskID = -1;
            Date startTime, endTime;
            for (Iterator<Row> ritTS = timesheetSheet.iterator(); ritTS.hasNext(); ) {
                Row rowTS = ritTS.next();

                //just skip the row if row number is 0
                if (rowTS.getRowNum() == 0) {
                    continue;
                }

                timesheetTaskID = (int) rowTS.getCell(2, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                if (taskModel.getTaskID() == timesheetTaskID) {
                    timesheetID = (int) rowTS.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                    timesheetStaffID = (int) rowTS.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                    startTime = rowTS.getCell(3, Row.CREATE_NULL_AS_BLANK).getDateCellValue();
                    endTime = rowTS.getCell(4, Row.CREATE_NULL_AS_BLANK).getDateCellValue();

                    if (!addTimesheetTask(timesheetID,
                            timesheetStaffID, timesheetTaskID, startTime, endTime, itSheet)) {
                        return false;
                    }
                }
            }
        } catch (Exception e) {
            Log.d(TAG, "Exception in importing TASK from Excel: " + e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }

    public boolean addActivityTask(int taskID, int activityID) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(cSQLDBHelper.KEY_TASK_FK_ID, taskID);
        cv.put(cSQLDBHelper.KEY_ACTIVITY_FK_ID, activityID);

        if (db.insert(cSQLDBHelper.TABLE_tblACTIVITYTASK, null, cv) < 0) {
            return false;
        }

        return true;
    }

    public boolean addPrecedingTask(int taskID, int precedingID) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(cSQLDBHelper.KEY_TASK_FK_ID, taskID);
        cv.put(cSQLDBHelper.KEY_PRECEDING_TASK_FK_ID, precedingID);

        try {
            if (db.insert(cSQLDBHelper.TABLE_tblPRECEDINGTASK, null, cv) < 0) {
                return false;
            }
        } catch (Exception e) {
            Log.d(TAG, "Exception in importing PRECEDING TASK from Excel: " + e.getMessage());
        }

        return true;
    }

    public boolean addTaskMilestone(int taskID, int milestoneID) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(cSQLDBHelper.KEY_TASK_FK_ID, taskID);
        cv.put(cSQLDBHelper.KEY_MILESTONE_FK_ID, milestoneID);

        try {
            if (db.insert(cSQLDBHelper.TABLE_tblTASK_MILESTONE, null, cv) < 0) {
                return false;
            }
        } catch (Exception e) {
            Log.d(TAG, "Exception in importing TASK MILESTONE from Excel: " + e.getMessage());
        }

        return true;
    }

    public boolean addAssignedTask(int assignmentID, int assignedStaffID, int assignedTaskID,
                                   int assignedHours, double assignedRate) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(cSQLDBHelper.KEY_ID, assignmentID);
        cv.put(cSQLDBHelper.KEY_STAFF_FK_ID, assignedStaffID);
        cv.put(cSQLDBHelper.KEY_TASK_FK_ID, assignedTaskID);
        cv.put(cSQLDBHelper.KEY_HOURS, assignedHours);
        cv.put(cSQLDBHelper.KEY_RATE, assignedRate);

        try {
            if (db.insert(cSQLDBHelper.TABLE_tblTASKASSIGNMENT, null, cv) < 0) {
                return false;
            }
        } catch (Exception e) {
            Log.d(TAG, "Exception in importing TASK ASSIGNMENT from Excel: " + e.getMessage());
        }

        return true;
    }

    public boolean addTimesheetTask(int timesheetID, int timesheetStaffID, int timesheetTaskID,
                                    Date startTime, Date endTime, Sheet itSheet) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(cSQLDBHelper.KEY_ID, timesheetID);
        cv.put(cSQLDBHelper.KEY_STAFF_FK_ID, timesheetStaffID);
        cv.put(cSQLDBHelper.KEY_TASK_FK_ID, timesheetTaskID);
        cv.put(cSQLDBHelper.KEY_START_TIME, String.valueOf(startTime));
        cv.put(cSQLDBHelper.KEY_END_TIME, String.valueOf(endTime));

        try {
            if (db.insert(cSQLDBHelper.TABLE_tblTIMESHEET, null, cv) < 0) {
                return false;
            }

            /* add timesheet to an invoice */
            for (Iterator<Row> ritIT = itSheet.iterator(); ritIT.hasNext(); ) {
                Row rowIT = ritIT.next();

                //just skip the row if row number is 0
                if (rowIT.getRowNum() == 0) {
                    continue;
                }

                int timesheetFKID = (int) rowIT.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                if (timesheetID == timesheetFKID) {
                    int invoiceFKID = (int) rowIT.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();

                    if (!addInvoiceTimesheet(invoiceFKID, timesheetFKID)) {
                        return false;
                    }
                }
            }

        } catch (Exception e) {
            Log.d(TAG, "Exception in importing TIMESHEET from Excel: " + e.getMessage());
        }

        return true;
    }

    public boolean addInvoiceTimesheet(int invoiceID, int timesheetID) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(cSQLDBHelper.KEY_INVOICE_FK_ID, invoiceID);
        cv.put(cSQLDBHelper.KEY_TIMESHEET_FK_ID, timesheetID);

        try {
            if (db.insert(cSQLDBHelper.TABLE_tblINVOICE_TIMESHEET, null, cv) < 0) {
                return false;
            }
        } catch (Exception e) {
            Log.d(TAG, "Exception in importing INVOICE TIMESHEET from Excel: " + e.getMessage());
        }

        return true;
    }


    public boolean addCommentTask(int commentID, int commentStaffID, int commentTaskID,
                                  String comment) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(cSQLDBHelper.KEY_ID, commentID);
        cv.put(cSQLDBHelper.KEY_STAFF_FK_ID, commentStaffID);
        cv.put(cSQLDBHelper.KEY_TASK_FK_ID, commentTaskID);
        cv.put(cSQLDBHelper.KEY_USER_COMMENT, comment);

        try {
            if (db.insert(cSQLDBHelper.TABLE_tblUSERCOMMENT, null, cv) < 0) {
                return false;
            }
        } catch (Exception e) {
            Log.d(TAG, "Exception in importing USER COMMENT from Excel: " + e.getMessage());
        }

        return true;
    }

    @Override
    public boolean deleteTasks() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblTASK, null, null);

        // close the database connection
        db.close();

        return result > -1;
    }

    @Override
    public boolean deleteActivityTasks() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblACTIVITYTASK, null, null);

        // close the database connection
        db.close();

        return result > -1;
    }

    @Override
    public boolean deletePrecedingTasks() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblPRECEDINGTASK, null, null);

        // close the database connection
        db.close();

        return result > -1;
    }

    @Override
    public boolean deleteTaskMilestones() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblTASK_MILESTONE, null, null);

        // close the database connection
        db.close();

        return result > -1;
    }

    @Override
    public boolean deleteTaskAssignments() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblTASKASSIGNMENT, null, null);

        // close the database connection
        db.close();

        return result > -1;
    }

    @Override
    public boolean deleteTimesheets() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblTIMESHEET, null, null);

        // close the database connection
        db.close();

        return result > -1;
    }

    @Override
    public boolean deleteInvoiceTimesheet() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblINVOICE_TIMESHEET, null, null);

        // close the database connection
        db.close();

        return result > -1;
    }

    @Override
    public boolean deleteUserComments() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblUSERCOMMENT, null, null);

        // close the database connection
        db.close();

        return result > -1;
    }

    /*#################################### DOCUMENT FUNCTIONS ####################################*/

    @Override
    public boolean addDocumentFromExcel() {
        Workbook workbook = excelHelper.getWorkbookAWPB();
        Sheet DSheet = workbook.getSheet(cExcelHelper.SHEET_tblDOCUMENT);

        if (DSheet == null) {
            return false;
        }

        for (Iterator<Row> ritD = DSheet.iterator(); ritD.hasNext(); ) {
            Row cRow = ritD.next();

            //just skip the row if row number is 0
            if (cRow.getRowNum() == 0) {
                continue;
            }

            cDocumentModel documentModel = new cDocumentModel();

            documentModel.setDocumentID((int)
                    cRow.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            documentModel.setExtDocTypeID((int)
                    cRow.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            documentModel.setName(
                    cRow.getCell(2, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
            documentModel.setDescription(
                    cRow.getCell(3, Row.CREATE_NULL_AS_BLANK).getStringCellValue());

            if (!addDocument(documentModel)) {
                return false;
            }
        }

        return true;
    }

    public boolean addDocument(cDocumentModel documentModel) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_ID, documentModel.getDocumentID());
        cv.put(cSQLDBHelper.KEY_EXT_DOC_TYPE_FK_ID, documentModel.getExtDocTypeID());
        cv.put(cSQLDBHelper.KEY_NAME, documentModel.getName());
        cv.put(cSQLDBHelper.KEY_DESCRIPTION, documentModel.getDescription());

        // insert project details
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblDOCUMENT, null, cv) < 0) {
                return false;
            }
        } catch (Exception e) {
            Log.d(TAG, "Exception in importing DOCUMENT from Excel: " + e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }

    @Override
    public boolean deleteDocuments() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblDOCUMENT, null, null);

        // close the database connection
        db.close();

        return result > -1;
    }

    /*##################################### INVOICE FUNCTIONS ####################################*/

    @Override
    public boolean addInvoiceFromExcel() {
        Workbook workbook = excelHelper.getWorkbookLOGFRAME();
        Sheet ISheet = workbook.getSheet(cExcelHelper.SHEET_tblINVOICE);

        if (ISheet == null) {
            return false;
        }

        for (Iterator<Row> ritI = ISheet.iterator(); ritI.hasNext(); ) {
            Row cRow = ritI.next();

            //just skip the row if row number is 0
            if (cRow.getRowNum() == 0) {
                continue;
            }

            cInvoiceModel invoiceModel = new cInvoiceModel();

            invoiceModel.setInvoiceID((int)
                    cRow.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            invoiceModel.setName(
                    cRow.getCell(1, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
            invoiceModel.setDescription(
                    cRow.getCell(2, Row.CREATE_NULL_AS_BLANK).getStringCellValue());

            if (!addInvoice(invoiceModel)) {
                return false;
            }
        }

        return true;
    }

    public boolean addInvoice(cInvoiceModel invoiceModel){
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_ID, invoiceModel.getInvoiceID());
        cv.put(cSQLDBHelper.KEY_NAME, invoiceModel.getName());
        cv.put(cSQLDBHelper.KEY_DESCRIPTION, invoiceModel.getDescription());

        // insert project details
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblINVOICE, null, cv) < 0) {
                return false;
            }
        } catch (Exception e) {
            Log.d(TAG, "Exception in importing INVOICE from Excel: " + e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }

    @Override
    public boolean deleteInvoices() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblINVOICE, null, null);

        // close the database connection
        db.close();

        return result > -1;
    }

    /*################################### TRANSACTION FUNCTIONS ##################################*/

    @Override
    public boolean addTransactionFromExcel() {
        Workbook workbook = excelHelper.getWorkbookAWPB();
        Sheet transactionSheet = workbook.getSheet(cExcelHelper.SHEET_tblTRANSACTION);
        // get task internal
        Sheet internalSheet = workbook.getSheet(cExcelHelper.SHEET_tblINTERNAL);
        // get time external
        Sheet externalSheet = workbook.getSheet(cExcelHelper.SHEET_tblEXTERNAL);

        if (transactionSheet == null) {
            return false;
        }

        for (Iterator<Row> ritTransaction = transactionSheet.iterator(); ritTransaction.hasNext(); ) {
            Row cRow = ritTransaction.next();

            //just skip the row if row number is 0
            if (cRow.getRowNum() == 0) {
                continue;
            }

            cTransactionModel transactionModel = new cTransactionModel();

            transactionModel.setTransactionID((int)
                    cRow.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            transactionModel.setDocumentID((int)
                    cRow.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            transactionModel.setPeriodID((int)
                    cRow.getCell(2, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            transactionModel.setName(
                    cRow.getCell(3, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
            transactionModel.setDescription(
                    cRow.getCell(4, Row.CREATE_NULL_AS_BLANK).getStringCellValue());

            if (!addTransaction(transactionModel, internalSheet, externalSheet)) {
                return false;
            }
        }

        return true;
    }

    public boolean addTransaction(cTransactionModel transactionModel,
                                  Sheet internalSheet, Sheet externalSheet){
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_ID, transactionModel.getTransactionID());
        cv.put(cSQLDBHelper.KEY_DOCUMENT_FK_ID, transactionModel.getDocumentID());
        cv.put(cSQLDBHelper.KEY_PERIOD_FK_ID, transactionModel.getPeriodID());
        cv.put(cSQLDBHelper.KEY_NAME, transactionModel.getName());
        cv.put(cSQLDBHelper.KEY_DESCRIPTION, transactionModel.getDescription());

        // insert project details
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblTRANSACTION, null, cv) < 0) {
                return false;
            }

            /* add internal for a transaction */
            for (Iterator<Row> ritI = internalSheet.iterator(); ritI.hasNext(); ) {
                Row rowI = ritI.next();

                //just skip the row if row number is 0
                if (rowI.getRowNum() == 0) {
                    continue;
                }

                int internalID = (int) rowI.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                if (transactionModel.getTransactionID() == internalID) {
                    cInternalModel internalModel = new cInternalModel();
                    internalModel.setInternalID(internalID);
                    internalModel.setInvoiceID((int)
                            rowI.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
                    if (!addInternal(internalModel)) {
                        return false;
                    }
                }
            }

            /* add external for a transaction */
            for (Iterator<Row> ritE = externalSheet.iterator(); ritE.hasNext(); ) {
                Row rowE = ritE.next();

                //just skip the row if row number is 0
                if (rowE.getRowNum() == 0) {
                    continue;
                }

                int externalID = (int) rowE.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                if (transactionModel.getTransactionID() == externalID) {
                    cExternalModel externalModel = new cExternalModel();
                    externalModel.setExternalID((externalID));
                    if (!addExternal(externalModel)) {
                        return false;
                    }
                }
            }
        } catch (Exception e) {
            Log.d(TAG, "Exception in importing TRANSACTION from Excel: " + e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }

    public boolean addInternal(cInternalModel internalModel){
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_TRANSACTION_FK_ID, internalModel.getInvoiceID());
        cv.put(cSQLDBHelper.KEY_INVOICE_FK_ID, internalModel.getInvoiceID());

        // insert project details
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblINTERNAL, null, cv) < 0) {
                return false;
            }
        } catch (Exception e) {
            Log.d(TAG, "Exception in importing INTERNAL from Excel: " + e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }

    public boolean addExternal(cExternalModel externalModel){
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_TRANSACTION_FK_ID, externalModel.getExternalID());

        // insert project details
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblEXTERNAL, null, cv) < 0) {
                return false;
            }
        } catch (Exception e) {
            Log.d(TAG, "Exception in importing EXTERNAL from Excel: " + e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }

    @Override
    public boolean deleteTransactions() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblTRANSACTION, null, null);

        // close the database connection
        db.close();

        return result > -1;
    }

    @Override
    public boolean deleteInternals() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblINTERNAL, null, null);

        // close the database connection
        db.close();

        return result > -1;
    }

    @Override
    public boolean deleteExternals() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblEXTERNAL, null, null);

        // close the database connection
        db.close();

        return result > -1;
    }

    /*##################################### JOURNAL FUNCTIONS ####################################*/

    @Override
    public boolean addJournalFromExcel() {
        Workbook workbook = excelHelper.getWorkbookAWPB();
        Sheet journalSheet = workbook.getSheet(cExcelHelper.SHEET_tblJOURNAL);

        if (journalSheet == null) {
            return false;
        }

        //Log.d(TAG, "HEREEEE - 1");

        for (Iterator<Row> ritJournal = journalSheet.iterator(); ritJournal.hasNext(); ) {
            Row cRow = ritJournal.next();
            //Log.d(TAG, "HEREEEE - 2");
            //just skip the row if row number is 0
            if (cRow.getRowNum() == 0) {
                continue;
            }

            cJournalModel journalModel = new cJournalModel();

            journalModel.setJournalID((int)
                    cRow.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            journalModel.setInputID((int)
                    cRow.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            journalModel.setTransactionID((int)
                    cRow.getCell(2, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            journalModel.setEntryType((int)
                    cRow.getCell(3, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            journalModel.setAmount(
                    cRow.getCell(4, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            //journalModel.setCreatedDate(
            //        cRow.getCell(5, Row.CREATE_NULL_AS_BLANK).getDateCellValue());

            //Gson gson = new Gson();
            //Log.d(TAG,gson.toJson(journalModel));

            if (!addJournal(journalModel)) {
                return false;
            }
        }
        //Log.d(TAG, "HEREEEE - 3");

        return true;
    }

    public boolean addJournal(cJournalModel journalModel){
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_ID, journalModel.getJournalID());
        cv.put(cSQLDBHelper.KEY_INPUT_FK_ID, journalModel.getInputID());
        cv.put(cSQLDBHelper.KEY_TRANSACTION_FK_ID, journalModel.getTransactionID());
        cv.put(cSQLDBHelper.KEY_ENTRY_TYPE, journalModel.getEntryType());
        cv.put(cSQLDBHelper.KEY_AMOUNT, journalModel.getAmount());
        cv.put(cSQLDBHelper.KEY_CREATED_DATE, String.valueOf(journalModel.getCreatedDate()));

        // insert project details
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblJOURNAL, null, cv) < 0) {
                return false;
            }
        } catch (Exception e) {
            Log.d(TAG, "Exception in importing JOURNAL from Excel: " + e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }

    @Override
    public boolean deleteJournals() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblJOURNAL, null, null);

        // close the database connection
        db.close();

        return result > -1;
    }
}
