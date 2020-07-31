package com.me.mseotsanyana.mande.DAL.ìmpl.wpb;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.util.Pair;

import com.google.gson.Gson;
import com.me.mseotsanyana.mande.BLL.repository.wpb.iUploadAWPBRepository;
import com.me.mseotsanyana.mande.DAL.model.wpb.cDocumentModel;
import com.me.mseotsanyana.mande.DAL.model.wpb.cExternalModel;
import com.me.mseotsanyana.mande.DAL.model.wpb.cInternalModel;
import com.me.mseotsanyana.mande.DAL.model.wpb.cInvoiceModel;
import com.me.mseotsanyana.mande.DAL.model.wpb.cJournalModel;
import com.me.mseotsanyana.mande.DAL.model.wpb.cTaskModel;
import com.me.mseotsanyana.mande.DAL.model.wpb.cTransactionModel;
import com.me.mseotsanyana.mande.DAL.storage.database.cSQLDBHelper;
import com.me.mseotsanyana.mande.DAL.storage.excel.cExcelHelper;
import com.me.mseotsanyana.mande.UTIL.cConstant;

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
        /* get preceding tasks */
        Sheet precedingTaskSheet = workbook.getSheet(cExcelHelper.SHEET_tblPRECEDINGTASK);
        /* get activity tasks */
        Sheet activityTaskSheet = workbook.getSheet(cExcelHelper.SHEET_tblACTIVITYTASK);
        /* get time sheet */
        Sheet tmSheet = workbook.getSheet(cExcelHelper.SHEET_tblTASK_MILESTONE);
        /* get task assignment */
        Sheet tstSheet = workbook.getSheet(cExcelHelper.SHEET_tblTASKASSIGNMENT);
        /* get task comments */
        Sheet commentSheet = workbook.getSheet(cExcelHelper.SHEET_tblUSERCOMMENT);
        /* get time sheets */
        Sheet timesheetSheet = workbook.getSheet(cExcelHelper.SHEET_tblTIMESHEET);
        /* get invoice for timesheet sheets */
        Sheet itSheet = workbook.getSheet(cExcelHelper.SHEET_tblINVOICE_TIMESHEET);

        if (taskSheet == null) {
            return false;
        }

        for (Row cRow : taskSheet) {
            //just skip the row if row number is 0
            if (cRow.getRowNum() == 0) {
                continue;
            }

            cTaskModel taskModel = new cTaskModel();

            taskModel.setTaskID((int) cRow.getCell(0,
                    Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            taskModel.setName(cRow.getCell(1,
                    Row.CREATE_NULL_AS_BLANK).getStringCellValue());
            taskModel.setDescription(cRow.getCell(2,
                    Row.CREATE_NULL_AS_BLANK).getStringCellValue());

            /* get preceding tasks of the task */
            Set<Pair<Long, Long>> precedingSet = new HashSet<>();
            long precedingTaskID, succeedingTaskID;
            for (Row rowPrecedingTask : precedingTaskSheet) {
                //just skip the row if row number is 0
                if (rowPrecedingTask.getRowNum() == 0) {
                    continue;
                }

                precedingTaskID = (int) rowPrecedingTask.getCell(0,
                        Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                if (taskModel.getTaskID() == precedingTaskID) {
                    succeedingTaskID = (int) rowPrecedingTask.getCell(1,
                            Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                    precedingSet.add(new Pair<>(precedingTaskID, succeedingTaskID));
                }
            }

            /* get task of the activity */
            Set<Pair<Long, Long>> taskSet = new HashSet<>();
            long taskID, activityID;

            for (Row rowActivityTask : activityTaskSheet) {
                //just skip the row if row number is 0
                if (rowActivityTask.getRowNum() == 0) {
                    continue;
                }

                taskID = (int) rowActivityTask.getCell(0,
                        Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                if (taskModel.getTaskID() == taskID) {
                    activityID = (int) rowActivityTask.getCell(1,
                            Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                    taskSet.add(new Pair<>(taskID, activityID));
                }
            }

            /* get milestone for the task */
            Set<Pair<Long, Long>> milestoneSet = new HashSet<>();
            taskID = -1;
            long milestoneID = -1;
            for (Row rowTM : tmSheet) {
                //just skip the row if row number is 0
                if (rowTM.getRowNum() == 0) {
                    continue;
                }

                taskID = (int) rowTM.getCell(0,
                        Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                if (taskModel.getTaskID() == taskID) {
                    milestoneID = (int) rowTM.getCell(1,
                            Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                    milestoneSet.add(new Pair<>(taskID, milestoneID));
                }
            }

            /* add assigned task */
            int assignmentID = -1, assignedStaffID = -1, assignedTaskID = -1, assignedHours = 0;
            double assignedRate = 0.0;
            for (Row rowTST : tstSheet) {
                //just skip the row if row number is 0
                if (rowTST.getRowNum() == 0) {
                    continue;
                }

                assignedTaskID = (int) rowTST.getCell(2,
                        Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                if (taskModel.getTaskID() == assignedTaskID) {
                    assignmentID = (int) rowTST.getCell(0,
                            Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                    assignedStaffID = (int) rowTST.getCell(1,
                            Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                    assignedHours = (int) rowTST.getCell(3,
                            Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                    assignedRate = (int) rowTST.getCell(4,
                            Row.CREATE_NULL_AS_BLANK).getNumericCellValue();

                    if (!addAssignedTask(assignmentID, assignedStaffID,
                            assignedTaskID, assignedHours, assignedRate)) {
                        return false;
                    }
                }
            }

            /* add comments for a task */
            int commentID = -1, commentStaffID = -1, commentTaskID = -1;
            String comment = null;
            for (Row rowUC : commentSheet) {
                //just skip the row if row number is 0
                if (rowUC.getRowNum() == 0) {
                    continue;
                }

                commentTaskID = (int) rowUC.getCell(2,
                        Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                if (taskModel.getTaskID() == commentTaskID) {
                    commentID = (int) rowUC.getCell(0,
                            Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                    commentStaffID = (int) rowUC.getCell(1,
                            Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                    comment = rowUC.getCell(3, Row.CREATE_NULL_AS_BLANK).getStringCellValue();

                    if (!addCommentTask(commentID, commentStaffID, commentTaskID, comment)) {
                        return false;
                    }
                }
            }

            /* add timesheet for a task */
            int timesheetID = -1, timesheetStaffID = -1, timesheetTaskID = -1;
            Date startTime, endTime;
            for (Row rowTS : timesheetSheet) {
                //just skip the row if row number is 0
                if (rowTS.getRowNum() == 0) {
                    continue;
                }

                timesheetTaskID = (int) rowTS.getCell(2,
                        Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                if (taskModel.getTaskID() == timesheetTaskID) {
                    timesheetID = (int) rowTS.getCell(0,
                            Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                    timesheetStaffID = (int) rowTS.getCell(1,
                            Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                    startTime = rowTS.getCell(3, Row.CREATE_NULL_AS_BLANK).getDateCellValue();
                    endTime = rowTS.getCell(4, Row.CREATE_NULL_AS_BLANK).getDateCellValue();

                    if (!addTimesheetTask(timesheetID,
                            timesheetStaffID, timesheetTaskID, startTime, endTime, itSheet)) {
                        return false;
                    }
                }
            }

            if (!addTask(taskModel, taskSet, precedingSet, milestoneSet,
                    tstSheet, timesheetSheet, commentSheet, itSheet)) {
                return false;
            }
        }
        return true;
    }

    private boolean addTask(cTaskModel taskModel,
                            Set<Pair<Long, Long>> activityTaskSet,
                            Set<Pair<Long, Long>> precedingSet,
                            Set<Pair<Long, Long>> milestoneSet,
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
            for (Pair<Long, Long> pair : activityTaskSet) {
                long taskID = pair.first;
                long activityID = pair.second;
                if (!addActivityTask(taskID, activityID))
                    return false;
            }

            /* add preceding task */
            for (Pair<Long, Long> pair : precedingSet) {
                long succeedingID = pair.first;
                long precedingID = pair.second;
                if (!addPrecedingTask(succeedingID, precedingID))
                    return false;
            }

            /* add milestone task */
            for (Pair<Long, Long> pair : milestoneSet) {
                long taskID = pair.first;
                long milestoneID = pair.second;
                if (!addTaskMilestone(taskID, milestoneID))
                    return false;
            }

        } catch (Exception e) {
            Log.d(TAG, "Exception in importing TASK from Excel: " + e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }

    private boolean addActivityTask(long taskID, long activityID) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(cSQLDBHelper.KEY_TASK_FK_ID, taskID);
        cv.put(cSQLDBHelper.KEY_ACTIVITY_FK_ID, activityID);
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblACTIVITYTASK, null, cv) < 0) {
                return false;
            }
        } catch (Exception e) {
            Log.d(TAG, "Exception in importing ACTIVITY TASK from Excel: " + e.getMessage());
        }

        return true;
    }

    private boolean addPrecedingTask(long taskID, long precedingID) {
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

    private boolean addTaskMilestone(long taskID, long milestoneID) {
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

    private boolean addAssignedTask(int assignmentID, int assignedStaffID, int assignedTaskID,
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

    private boolean addTimesheetTask(int timesheetID, int timesheetStaffID, int timesheetTaskID,
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
            for (Row rowIT : itSheet) {
                //just skip the row if row number is 0
                if (rowIT.getRowNum() == 0) {
                    continue;
                }

                int timesheetFKID = (int) rowIT.getCell(1,
                        Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                if (timesheetID == timesheetFKID) {
                    int invoiceFKID = (int) rowIT.getCell(0,
                            Row.CREATE_NULL_AS_BLANK).getNumericCellValue();

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

    private boolean addCommentTask(int commentID, int commentStaffID, int commentTaskID,
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

    private boolean addInvoiceTimesheet(int invoiceID, int timesheetID) {
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

        for (Row cRow : DSheet) {
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

    private boolean addDocument(cDocumentModel documentModel) {
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
        Workbook workbook = excelHelper.getWorkbookAWPB();
        Sheet ISheet = workbook.getSheet(cExcelHelper.SHEET_tblINVOICE);

        if (ISheet == null) {
            return false;
        }

        for (Row cRow : ISheet) {
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

    private boolean addInvoice(cInvoiceModel invoiceModel) {
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

        for (Row cRow : transactionSheet) {
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

    private boolean addTransaction(cTransactionModel transactionModel,
                                   Sheet internalSheet, Sheet externalSheet) {
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
            for (Row rowI : internalSheet) {
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
            for (Row rowE : externalSheet) {
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

    private boolean addInternal(cInternalModel internalModel) {
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

    private boolean addExternal(cExternalModel externalModel) {
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

        for (Row cRow : journalSheet) {
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
            journalModel.setDescription(
                    cRow.getCell(5, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
            //journalModel.setCreatedDate(
            //        cRow.getCell(6, Row.CREATE_NULL_AS_BLANK).getDateCellValue());

            if (!addJournal(journalModel)) {
                return false;
            }
        }

        return true;
    }

    private boolean addJournal(cJournalModel journalModel) {
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
        cv.put(cSQLDBHelper.KEY_DESCRIPTION, journalModel.getDescription());
        //cv.put(cSQLDBHelper.KEY_CREATED_DATE, sdf.format(String.valueOf(journalModel.getCreatedDate())));

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
