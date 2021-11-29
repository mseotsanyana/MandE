package com.me.mseotsanyana.mande.DAL.ìmpl.sqlite.logframe;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.util.Pair;

import com.google.gson.Gson;
import com.me.mseotsanyana.mande.BLL.model.logframe.cComponentModel;
import com.me.mseotsanyana.mande.BLL.model.logframe.cRaidCategoryModel;
import com.me.mseotsanyana.mande.BLL.repository.logframe.iUploadLogFrameRepository;
import com.me.mseotsanyana.mande.BLL.model.logframe.cCriteriaModel;
import com.me.mseotsanyana.mande.BLL.model.logframe.cQuestionGroupingModel;
import com.me.mseotsanyana.mande.BLL.model.logframe.cQuestionModel;
import com.me.mseotsanyana.mande.BLL.model.logframe.cQuestionTypeModel;
import com.me.mseotsanyana.mande.BLL.model.logframe.cActivityModel;
import com.me.mseotsanyana.mande.BLL.model.logframe.cImpactModel;
import com.me.mseotsanyana.mande.BLL.model.logframe.cInputModel;
import com.me.mseotsanyana.mande.BLL.model.logframe.cLogFrameModel;
import com.me.mseotsanyana.mande.BLL.model.logframe.cOutcomeModel;
import com.me.mseotsanyana.mande.BLL.model.logframe.cOutputModel;
import com.me.mseotsanyana.mande.BLL.model.logframe.cRaidModel;
import com.me.mseotsanyana.mande.BLL.model.logframe.cResourceTypeModel;
import com.me.mseotsanyana.mande.DAL.storage.database.cSQLDBHelper;
import com.me.mseotsanyana.mande.DAL.storage.excel.cExcelHelper;
import com.me.mseotsanyana.mande.UTIL.cConstant;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class cUploadLogFrameRepositoryImpl implements iUploadLogFrameRepository {
    private static SimpleDateFormat sdf = cConstant.FORMAT_DATE;
    private static String TAG = cUploadLogFrameRepositoryImpl.class.getSimpleName();

    /* an object of the database helper */
    private cSQLDBHelper dbHelper;
    /* an object of the excel helper */
    private cExcelHelper excelHelper;

    public cUploadLogFrameRepositoryImpl(Context context) {
        dbHelper = new cSQLDBHelper(context);
        excelHelper = new cExcelHelper(context);
    }

    /*#################################### LOGFRAME FUNCTIONS ####################################*/

    /**
     * This function extracts and adds the logframe data from excel.
     *
     * @return logFrame
     */
    @Override
    public boolean addLogFrameFromExcel() {
        Workbook workbook = excelHelper.getWorkbookLOGFRAME();
        Sheet logFrameSheet = workbook.getSheet(cExcelHelper.SHEET_tblLOGFRAME);
        if (logFrameSheet == null) {
            return false;
        }

        for (Row cRow : logFrameSheet) {
            //just skip the row if row number is 0
            if (cRow.getRowNum() == 0) {
                continue;
            }

            cLogFrameModel logFrame = new cLogFrameModel();

//            logFrame.setLogFrameID(
//                    (int) cRow.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
//            logFrame.setOrganizationID(
//                    (int) cRow.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            logFrame.setName(
                    cRow.getCell(2, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
            logFrame.setDescription(
                    cRow.getCell(3, Row.CREATE_NULL_AS_BLANK).getStringCellValue());

            Set<Long> subLogFrameModelSet = new HashSet<>();
            long parentID, childID;

            Sheet logFrameTreeSheet = workbook.getSheet(cExcelHelper.SHEET_tblLOGFRAMETREE);
            for (Row rowLogFrameTree : logFrameTreeSheet) {
                //just skip the row if row number is 0
                if (rowLogFrameTree.getRowNum() == 0) {
                    continue;
                }

                parentID = (int) rowLogFrameTree.getCell(0,
                        Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
//                if (logFrame.getLogFrameID() == parentID) {
//                    childID = (int) rowLogFrameTree.getCell(1,
//                            Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
//                    subLogFrameModelSet.add(childID);
//                }
            }

            if (!addLogFrameFromExcel(logFrame, subLogFrameModelSet)) {
                return false;
            }
        }

        return true;
    }

    /**
     * This function adds the logframe and sub-logframe data from excel.
     *
     * @param logFrameModel  logFrame
     * @param subLogFrameSet sub logFrame set
     * @return boolean
     */
    public boolean addLogFrameFromExcel(cLogFrameModel logFrameModel, Set<Long> subLogFrameSet) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
//        cv.put(cSQLDBHelper.KEY_ID, logFrameModel.getLogFrameID());
//        cv.put(cSQLDBHelper.KEY_ORGANIZATION_FK_ID, logFrameModel.getOrganizationID());
        cv.put(cSQLDBHelper.KEY_NAME, logFrameModel.getName());
        cv.put(cSQLDBHelper.KEY_DESCRIPTION, logFrameModel.getDescription());

        // insert LogFrame record
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblLOGFRAME, null, cv) < 0) {
                return false;
            }

            // add subLogFrame
            for (long childID : subLogFrameSet) {
//                if (!addLogFrameTree(logFrameModel.getLogFrameID(), childID))
//                    return false;
            }
        } catch (Exception e) {
            Log.d(TAG, "Exception in importing LOGFRAME from Excel: " + e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }

    /**
     * This function adds the sub-logframe data from excel.
     *
     * @param parentID parent identification
     * @param childID  child identification
     * @return boolean
     */
    public boolean addLogFrameTree(long parentID, long childID) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(cSQLDBHelper.KEY_PARENT_FK_ID, parentID);
        cv.put(cSQLDBHelper.KEY_CHILD_FK_ID, childID);

        return db.insert(cSQLDBHelper.TABLE_tblLOGFRAMETREE, null, cv) >= 0;
    }

    @Override
    public boolean deleteLogFrame() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblLOGFRAME, null, null);

        // close the database connection
        db.close();

        return result > -1;
    }

    @Override
    public boolean deleteLogFrameTree() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblLOGFRAMETREE, null, null);

        // close the database connection
        db.close();

        return result > -1;
    }

    /*################################## RESOURCE TYPE FUNCTIONS #################################*/

    /**
     * This function extracts resource type data from excel and adds it to the database.
     *
     * @return boolean
     */
    @Override
    public boolean addResourceTypeFromExcel() {
        Workbook workbook = excelHelper.getWorkbookLOGFRAME();
        Sheet RTSheet = workbook.getSheet(cExcelHelper.SHEET_tblRESOURCETYPE);

        if (RTSheet == null) {
            return false;
        }

        for (Row cRow : RTSheet) {
            //just skip the row if row number is 0
            if (cRow.getRowNum() == 0) {
                continue;
            }

            cResourceTypeModel resourceTypeModel = new cResourceTypeModel();

//            resourceTypeModel.setResourceTypeID((int)
//                    cRow.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
//            resourceTypeModel.setName(
//                    cRow.getCell(1, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
//            resourceTypeModel.setDescription(
//                    cRow.getCell(2, Row.CREATE_NULL_AS_BLANK).getStringCellValue());

            if (!addResourceType(resourceTypeModel)) {
                return false;
            }
        }

        return true;
    }

    /**
     * This function adds resource type data to the database.
     *
     * @param resourceTypeModel resource type model
     * @return boolean
     */
    public boolean addResourceType(cResourceTypeModel resourceTypeModel) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
//        cv.put(cSQLDBHelper.KEY_ID, resourceTypeModel.getResourceTypeID());
        cv.put(cSQLDBHelper.KEY_NAME, resourceTypeModel.getName());
        cv.put(cSQLDBHelper.KEY_DESCRIPTION, resourceTypeModel.getDescription());

        // insert project details
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblRESOURCETYPE, null, cv) < 0) {
                return false;
            }
        } catch (Exception e) {
            Log.d(TAG, "Exception in importing RESOURCE TYPE from Excel: " + e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }

    @Override
    public boolean deleteResourceTypes() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblRESOURCETYPE, null, null);

        // close the database connection
        db.close();

        return result > -1;
    }

    /* ################################### COMPONENT FUNCTIONS ###################################*/

    /**
     * This function extracts and adds the impact data from excel.
     *
     * @return boolean
     */
    @Override
    public boolean addComponentFromExcel() {
        Workbook workbook = excelHelper.getWorkbookLOGFRAME();
        Workbook awpbWorkbook = excelHelper.getWorkbookAWPB();

        Sheet componentSheet = workbook.getSheet(cExcelHelper.SHEET_tblCOMPONENT);

        Sheet impactSheet = workbook.getSheet(cExcelHelper.SHEET_tblIMPACT);
        Sheet outcomeSheet = workbook.getSheet(cExcelHelper.SHEET_tblOUTCOME);
        Sheet outputSheet = workbook.getSheet(cExcelHelper.SHEET_tblOUTPUT);
        Sheet activitySheet = workbook.getSheet(cExcelHelper.SHEET_tblACTIVITY);
        Sheet inputSheet = workbook.getSheet(cExcelHelper.SHEET_tblINPUT);

        Sheet OISheet = workbook.getSheet(cExcelHelper.SHEET_tblOUTCOME_IMPACT);
        Sheet OOSheet = workbook.getSheet(cExcelHelper.SHEET_tblOUTPUT_OUTCOME);
        Sheet AOSheet = workbook.getSheet(cExcelHelper.SHEET_tblACTIVITY_OUTPUT);
        Sheet IASheet = workbook.getSheet(cExcelHelper.SHEET_tblINPUT_ACTIVITY);

        Sheet PASheet = workbook.getSheet(cExcelHelper.SHEET_tblPRECEDINGACTIVITY);
        Sheet AASheet = workbook.getSheet(cExcelHelper.SHEET_tblACTIVITYASSIGNMENT);

        Sheet humanSheet = awpbWorkbook.getSheet(cExcelHelper.SHEET_tblHUMAN);
        Sheet humanSetSheet = awpbWorkbook.getSheet(cExcelHelper.SHEET_tblHUMANSET);
        Sheet materialSheet = awpbWorkbook.getSheet(cExcelHelper.SHEET_tblMATERIAL);
        Sheet incomeSheet = awpbWorkbook.getSheet(cExcelHelper.SHEET_tblINCOME);
        Sheet fundSheet = awpbWorkbook.getSheet(cExcelHelper.SHEET_tblFUND);
        Sheet expenseSheet = awpbWorkbook.getSheet(cExcelHelper.SHEET_tblEXPENSE);

        if (componentSheet == null) {
            return false;
        }

        for (Row cRow : componentSheet) {
            //just skip the row if row number is 0
            if (cRow.getRowNum() == 0) {
                continue;
            }

            cComponentModel componentModel = new cComponentModel();

            componentModel.setComponentServerID(cRow.getCell(0,
                    Row.CREATE_NULL_AS_BLANK).getStringCellValue());
//            componentModel.getLogFrameModel().setLogFrameID((int) cRow.getCell(1,
//                    Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            componentModel.setName(cRow.getCell(2,
                    Row.CREATE_NULL_AS_BLANK).getStringCellValue());
            componentModel.setDescription(cRow.getCell(3,
                    Row.CREATE_NULL_AS_BLANK).getStringCellValue());

            /* impact component */
            Set<cImpactModel> impactModelSet = new HashSet<>();
            for (Row impactRow : impactSheet) {
                //just skip the row if row number is 0
                if (impactRow.getRowNum() == 0) {
                    continue;
                }

                long impactID = (int) impactRow.getCell(0,
                        Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
//                if (componentModel.getComponentID() == impactID) {
//
//                    cImpactModel impactModel = new cImpactModel();
//
//                    impactModel.setComponentID(impactID);
//                    impactModel.setParentID((int) impactRow.getCell(1,
//                            Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
//
//                    impactModelSet.add(impactModel);
//                }
            }

            /* outcome component */
            Set<cOutcomeModel> outcomeModelSet = new HashSet<>();
            for (Row outcomeRow : outcomeSheet) {
                //just skip the row if row number is 0
                if (outcomeRow.getRowNum() == 0) {
                    continue;
                }

                long outcomeID = (int) outcomeRow.getCell(0,
                        Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
//                if (componentModel.getComponentID() == outcomeID) {
//
//                    cOutcomeModel outcomeModel = new cOutcomeModel();
//
//                    outcomeModel.setComponentID(outcomeID);
//                    outcomeModel.setParentID((int) outcomeRow.getCell(1,
//                            Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
//                    outcomeModel.getImpactModel().setComponentID((int) outcomeRow.getCell(2,
//                            Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
//
//                    outcomeModelSet.add(outcomeModel);
//                }
            }
            /* get impacts of the outcome for a sub-logframe */
            Map<Set<Pair<Long, Long>>, Pair<Long, Long>> oiMap = new HashMap<>();
            //long parentID, childID, impactID;
            for (Row rowOI : OISheet) {
                //just skip the row if row number is 0
                if (rowOI.getRowNum() == 0) {
                    continue;
                }

                long outcomeID = (int) rowOI.getCell(2,
                        Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
//                if (componentModel.getComponentID() == outcomeID) {
//                    Set<Pair<Long, Long>> impactSet = new HashSet<>();
//                    long parentID = (int) rowOI.getCell(0,
//                            Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
//                    long childID = (int) rowOI.getCell(1,
//                            Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
//                    long impactID = (int) rowOI.getCell(3,
//                            Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
//                    impactSet.add(new Pair<>(outcomeID, impactID));
//                    oiMap.put(impactSet, new Pair<>(parentID, childID));
                }
            }

            /* output component */
            Set<cOutputModel> outputModelSet = new HashSet<>();
            for (Row outputRow : outputSheet) {
                //just skip the row if row number is 0
                if (outputRow.getRowNum() == 0) {
                    continue;
                }

                long outputID = (int) outputRow.getCell(0,
                        Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
//                if (componentModel.getComponentID() == outputID) {
//
//                    cOutputModel outputModel = new cOutputModel();
//
//                    outputModel.setComponentID(outputID);
//                    outputModel.setParentID((int) outputRow.getCell(1,
//                            Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
//                    outputModel.getOutcomeModel().setComponentID((int) outputRow.getCell(2,
//                            Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
//
//                    outputModelSet.add(outputModel);
//                }
            }
            /* get outcomes of the output for a sub-logframe */
            Map<Set<Pair<Long, Long>>, Pair<Long, Long>> ooMap = new HashMap<>();
            for (Row rowOO : OOSheet) {
                //just skip the row if row number is 0
                if (rowOO.getRowNum() == 0) {
                    continue;
                }

                long outputID = (int) rowOO.getCell(2,
                        Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
//                if (componentModel.getComponentID() == outputID) {
//                    Set<Pair<Long, Long>> impactSet = new HashSet<>();
//                    long parentID = (int) rowOO.getCell(0,
//                            Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
//                    long childID = (int) rowOO.getCell(1,
//                            Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
//                    long outcomeID = (int) rowOO.getCell(3,
//                            Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
//                    impactSet.add(new Pair<>(outputID, outcomeID));
//                    ooMap.put(impactSet, new Pair<>(parentID, childID));
//                }
            }

            /* activity component */
            Set<cActivityModel> activityModelSet = new HashSet<>();
            for (Row activityRow : activitySheet) {
                //just skip the row if row number is 0
                if (activityRow.getRowNum() == 0) {
                    continue;
                }

                long activityID = (int) activityRow.getCell(0,
                        Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
//                if (componentModel.getComponentID() == activityID) {
//
//                    cActivityModel activityModel = new cActivityModel();
//
//                    activityModel.setComponentID(activityID);
//                    activityModel.setParentID((int) activityRow.getCell(1,
//                            Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
//                    activityModel.getOutputModel().setComponentID((int) activityRow.getCell(2,
//                            Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
//
//                    activityModelSet.add(activityModel);
//                }
            }
            /* get outputs of the activity for a sub-logframe */
            Map<Set<Pair<Long, Long>>, Pair<Long, Long>> aoMap = new HashMap<>();
            for (Row rowAO : AOSheet) {
                //just skip the row if row number is 0
                if (rowAO.getRowNum() == 0) {
                    continue;
                }

                long activityID = (int) rowAO.getCell(2,
                        Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
//                if (componentModel.getComponentID() == activityID) {
//                    Set<Pair<Long, Long>> AOSet = new HashSet<>();
//                    long parentID = (int) rowAO.getCell(0,
//                            Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
//                    long childID = (int) rowAO.getCell(1,
//                            Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
//                    long outputID = (int) rowAO.getCell(3,
//                            Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
//                    AOSet.add(new Pair<>(activityID, outputID));
//                    aoMap.put(AOSet, new Pair<>(parentID, childID));
//                }
            }
            /* get preceding activities of the activity*/
            Set<Pair<Long, Long>> precedingSet = new HashSet<>();
            for (Row rowPA : PASheet) {
                //just skip the row if row number is 0
                if (rowPA.getRowNum() == 0) {
                    continue;
                }

                long precedingActivityID = (int) rowPA.getCell(0,
                        Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
//                if (componentModel.getComponentID() == precedingActivityID) {
//                    long succeedingActivityID = (int) rowPA.getCell(1,
//                            Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
//                    precedingSet.add(new Pair<>(precedingActivityID, succeedingActivityID));
//                }
            }
            /* get activity assignment */
            Map<Long, Set<Pair<Long, Long>>> assignmentMap = new HashMap<>();
            Set<Pair<Long, Long>> asSet = new HashSet<>();
            for (Row rowAA : AASheet) {
                //just skip the row if row number is 0
                if (rowAA.getRowNum() == 0) {
                    continue;
                }

                long assignedActivityID = (int) rowAA.getCell(2,
                        Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
//                if (componentModel.getComponentID() == assignedActivityID) {
//                    long staffID = (int) rowAA.getCell(1,
//                            Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
//                    long assignmentID = (int) rowAA.getCell(0,
//                            Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
//                    asSet.add(new Pair<>(assignmentID, staffID));
//                    assignmentMap.put(assignedActivityID, asSet);
//                }
            }

            /* input component */
            Set<cInputModel> inputModelSet = new HashSet<>();
            for (Row inputRow : inputSheet) {
                //just skip the row if row number is 0
                if (inputRow.getRowNum() == 0) {
                    continue;
                }

                long inputID = (int) inputRow.getCell(0,
                        Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
//                if (componentModel.getComponentID() == inputID) {
//
//                    cInputModel inputModel = new cInputModel();
//
//                    inputModel.setComponentID(inputID);
//                    inputModel.getActivityModel().setComponentID((int) inputRow.getCell(1,
//                            Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
//                    inputModel.getResourceTypeModel().setResourceTypeID((int) inputRow.getCell(2,
//                            Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
//
//                    inputModelSet.add(inputModel);
//                }
            }
            /* get activities of the input for a sub-logframe */
            Map<Set<Pair<Long, Long>>, Pair<Long, Long>> iaMap = new HashMap<>();
            for (Row rowIA : IASheet) {
                //just skip the row if row number is 0
                if (rowIA.getRowNum() == 0) {
                    continue;
                }

                long inputID = (int) rowIA.getCell(2,
                        Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
//                if (componentModel.getComponentID() == inputID) {
//                    Set<Pair<Long, Long>> activitySet = new HashSet<>();
//                    long parentID = (int) rowIA.getCell(0,
//                            Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
//                    long childID = (int) rowIA.getCell(1,
//                            Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
//                    long activityID = (int) rowIA.getCell(3,
//                            Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
//                    activitySet.add(new Pair<>(inputID, activityID));
//                    iaMap.put(activitySet, new Pair<>(parentID, childID));
//                }
            }
            /* human input */
            long humanID = -1;
            int humanQuantity = 0;

            /* human set input */
            Map<Long, Set<Pair<Long, Long>>> hiuMap = new HashMap<>();
            Set<Pair<Long, Long>> huSet = new HashSet<>();

            /* material input */
            long materialID = -1;
            int materialQuantity = 0;

            /* expense inputs */
            int expenseID = -1;
            double expense = 0.0;

            /* income inputs */
            long incomeID = -1, funderID = -1;
            double fund = 0.0;

            for (Row rowHuman : humanSheet) {
                //just skip the row if row number is 0
                if (rowHuman.getRowNum() == 0) {
                    continue;
                }

                humanID = (int) rowHuman.getCell(0,
                        Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
//                if (componentModel.getComponentID() == humanID) {
//                    humanQuantity = (int) rowHuman.getCell(1,
//                            Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
//
//                    /* human set input */
//                    long humanSetInputID = -1, humanSetID = -1, userID = -1;
//
//                    for (Row rowHumanSet : humanSetSheet) {
//                        //just skip the row if row number is 0
//                        if (rowHumanSet.getRowNum() == 0) {
//                            continue;
//                        }
//
//                        humanSetInputID = (int) rowHumanSet.getCell(1,
//                                Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
////                        if (componentModel.getComponentID() == humanSetInputID) {
////                            humanSetID = (int) rowHumanSet.getCell(0,
////                                    Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
////                            userID = (int) rowHumanSet.getCell(2,
////                                    Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
////                            huSet.add(new Pair<>(humanSetID, userID));
////                        }
//                    }
//
//                    break;
//                } else {
//                    humanID = -1;
//                }
            }
//            hiuMap.put(componentModel.getComponentID(), huSet);

            /* material input */
            for (Row rowMaterial : materialSheet) {
                //just skip the row if row number is 0
                if (rowMaterial.getRowNum() == 0) {
                    continue;
                }

                materialID = (int) rowMaterial.getCell(0,
                        Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
//                if (componentModel.getComponentID() == materialID) {
//                    materialQuantity = (int) rowMaterial.getCell(1,
//                            Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
//                    break;
//                } else {
//                    materialID = -1;
//                }
            }

            /* income input */
            Map<Long, Set<Pair<Long, Double>>> ffaMap = new HashMap<>();
            Set<Pair<Long, Double>> fundSet = new HashSet<>();
            long fundInputID, fundID = -1;
            double amount;
            for (Row rowIncome : incomeSheet) {
                //just skip the row if row number is 0
                if (rowIncome.getRowNum() == 0) {
                    continue;
                }

                incomeID = (int) rowIncome.getCell(0,
                        Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
//
//                if (componentModel.getComponentID() == incomeID) {
//
//                    for (Row rowFundSet : fundSheet) {
//                        //just skip the row if row number is 0
//                        if (rowFundSet.getRowNum() == 0) {
//                            continue;
//                        }
//
//                        fundInputID = (int) rowFundSet.getCell(1,
//                                Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
//                        if (componentModel.getComponentID() == fundInputID) {
//                            fundID = (int) rowFundSet.getCell(0,
//                                    Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
//                            funderID = (int) rowFundSet.getCell(2,
//                                    Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
//                            fund = (double) rowFundSet.getCell(3,
//                                    Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
//
//                            fundSet.add(new Pair<Long, Double>((long) funderID, fund));
//                        }
//                    }
//                    ffaMap.put(fundID, fundSet);
//
//                    break;
//                } else {
//                    incomeID = -1;
//                }
            }

            /* expense input */
            for (Row rowExpense : expenseSheet) {
                //just skip the row if row number is 0
                if (rowExpense.getRowNum() == 0) {
                    continue;
                }

                expenseID = (int) rowExpense.getCell(0,
                        Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
//                if (componentModel.getComponentID() == expenseID) {
//                    expense = (int) rowExpense.getCell(1,
//                            Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
//                    break;
//                } else {
//                    expenseID = -1;
//                }
            }

//            if (!addComponents(componentModel, impactModelSet,
//                    outcomeModelSet, outputModelSet, activityModelSet, inputModelSet,
//                    oiMap, ooMap, aoMap, iaMap, precedingSet, assignmentMap,
//                    humanID, humanQuantity, hiuMap, materialID, materialQuantity, incomeID, ffaMap,
//                    expenseID, expense)) {
//                return false;
//            }
        //}

        return true;
    }

    private boolean addComponents(cComponentModel componentModel,
                                  Set<cImpactModel> impactModelSet,
                                  Set<cOutcomeModel> outcomeModelSet,
                                  Set<cOutputModel> outputModelSet,
                                  Set<cActivityModel> activityModelSet,
                                  Set<cInputModel> inputModelSet,
                                  Map<Set<Pair<Long, Long>>, Pair<Long, Long>> oiMap,
                                  Map<Set<Pair<Long, Long>>, Pair<Long, Long>> ooMap,
                                  Map<Set<Pair<Long, Long>>, Pair<Long, Long>> aoMap,
                                  Map<Set<Pair<Long, Long>>, Pair<Long, Long>> iaMap,
                                  Set<Pair<Long, Long>> precedingSet,
                                  Map<Long, Set<Pair<Long, Long>>> assignmentMap,
                                  long humanID, int humanQuantity,
                                  Map<Long, Set<Pair<Long, Long>>> hiuMap,
                                  long materialID, int materialQuantity,
                                  long incomeID, Map<Long, Set<Pair<Long, Double>>> ffaMap,
                                  long expenseID, double expense) {

        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
//        cv.put(cSQLDBHelper.KEY_ID, componentModel.getComponentID());
//        cv.put(cSQLDBHelper.KEY_LOGFRAME_FK_ID, componentModel.getLogFrameModel().getLogFrameID());
        cv.put(cSQLDBHelper.KEY_NAME, componentModel.getName());
        cv.put(cSQLDBHelper.KEY_DESCRIPTION, componentModel.getDescription());

        // insert details
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblCOMPONENT, null, cv) < 0) {
                return false;
            }

            // impact
            for (cImpactModel impactModel : impactModelSet) {
                if (!addImpact(impactModel)) {
                    return false;
                }
            }

            // outcome
            for (cOutcomeModel outcomeModel : outcomeModelSet) {
                if (!addOutcome(outcomeModel, oiMap)) {
                    return false;
                }
            }

            // output
            for (cOutputModel outputModel : outputModelSet) {
                if (!addOutput(outputModel, ooMap)) {
                    return false;
                }
            }

            // activity
            for (cActivityModel activityModel : activityModelSet) {
                if (!addActivity(activityModel, aoMap, precedingSet, assignmentMap)) {
                    return false;
                }
            }

            // input
            for (cInputModel inputModel : inputModelSet) {
                if (!addInput(inputModel, iaMap, humanID, humanQuantity, hiuMap,
                        materialID, materialQuantity, incomeID, ffaMap, expenseID, expense)) {
                    return false;
                }
            }

        } catch (Exception e) {
            Log.d(TAG, "Exception in importing COMPACT from Excel: " + e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }

    @Override
    public boolean deleteComponents() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblCOMPONENT, null,
                null);

        // close the database connection
        db.close();

        return result > -1;
    }

    public boolean addImpact(cImpactModel impactModel) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
//        cv.put(cSQLDBHelper.KEY_COMPONENT_FK_ID, impactModel.getComponentID());
//        cv.put(cSQLDBHelper.KEY_PARENT_FK_ID, impactModel.getParentID());

        // insert project details
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblIMPACT, null, cv) < 0) {
                return false;
            }

            // add impact, question and criteria tuple

        } catch (Exception e) {
            Log.d(TAG, "Exception in importing IMPACT from Excel: " + e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }

    @Override
    public boolean deleteImpacts() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblIMPACT, null,
                null);

        // close the database connection
        db.close();

        return result > -1;
    }

    public boolean addOutcome(cOutcomeModel outcomeModel,
                              Map<Set<Pair<Long, Long>>, Pair<Long, Long>> oiMap) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
//        cv.put(cSQLDBHelper.KEY_COMPONENT_FK_ID, outcomeModel.getComponentID());
//        cv.put(cSQLDBHelper.KEY_PARENT_FK_ID, outcomeModel.getParentID());
//        cv.put(cSQLDBHelper.KEY_IMPACT_FK_ID, outcomeModel.getImpactModel().getComponentID());

        // insert project details
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblOUTCOME, null, cv) < 0) {
                return false;
            }

            // add sub-logFrame, outcome and impact tuple
            for (Map.Entry<Set<Pair<Long, Long>>, Pair<Long, Long>> soiEntry : oiMap.entrySet()) {
                for (Pair<Long, Long> oiPair : soiEntry.getKey()) {
                    long outcomeID = oiPair.first;
                    long impactID = oiPair.second;

                    Pair<Long, Long> sPair = soiEntry.getValue();
                    long parentID = sPair.first;
                    long childID = sPair.second;

                    if (!addOutcomeImpact(parentID, childID, outcomeID, impactID))
                        return false;
                }
            }


        } catch (Exception e) {
            Log.d(TAG, "Exception in importing OUTCOME from Excel: " + e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }

    /**
     * @param parentID  parent identification
     * @param childID   child identification
     * @param outcomeID outcome identification
     * @param impactID  impact identification
     * @return boolean
     */
    public boolean addOutcomeImpact(long parentID, long childID, long outcomeID, long impactID) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(cSQLDBHelper.KEY_PARENT_FK_ID, parentID);
        cv.put(cSQLDBHelper.KEY_CHILD_FK_ID, childID);
        cv.put(cSQLDBHelper.KEY_OUTCOME_FK_ID, outcomeID);
        cv.put(cSQLDBHelper.KEY_IMPACT_FK_ID, impactID);

        return db.insert(cSQLDBHelper.TABLE_tblOUTCOME_IMPACT, null, cv) >= 0;
    }

    @Override
    public boolean deleteOutcomes() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblOUTCOME, null,
                null);

        // close the database connection
        db.close();

        return result > -1;
    }

    @Override
    public boolean deleteOutcomeImpacts() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblOUTCOME_IMPACT, null,
                null);

        // close the database connection
        db.close();

        return result > -1;
    }

    public boolean addOutput(cOutputModel outputModel,
                             Map<Set<Pair<Long, Long>>, Pair<Long, Long>> ooMap) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
//        cv.put(cSQLDBHelper.KEY_COMPONENT_FK_ID, outputModel.getComponentID());
//        cv.put(cSQLDBHelper.KEY_PARENT_FK_ID, outputModel.getParentID());
//        cv.put(cSQLDBHelper.KEY_OUTCOME_FK_ID, outputModel.getOutcomeModel().getComponentID());

        // insert project details
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblOUTPUT, null, cv) < 0) {
                return false;
            }

            // add sub-logFrame, outcome and impact tuple
            for (Map.Entry<Set<Pair<Long, Long>>, Pair<Long, Long>> sooEntry :
                    ooMap.entrySet()) {
                for (Pair<Long, Long> ooPair : sooEntry.getKey()) {
                    long outputID = ooPair.first;
                    long outcomeID = ooPair.second;

                    Pair<Long, Long> sPair = sooEntry.getValue();
                    long parentID = sPair.first;
                    long childID = sPair.second;

                    if (!addOutputOutcome(parentID, childID, outputID, outcomeID))
                        return false;
                }
            }

        } catch (Exception e) {
            Log.d(TAG, "Exception in importing OUTPUT from Excel: " + e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }

    /**
     * @param parentID  parent identification
     * @param childID   child identification
     * @param outputID  output identification
     * @param outcomeID outcome identification
     * @return boolean
     */
    public boolean addOutputOutcome(long parentID, long childID, long outputID, long outcomeID) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(cSQLDBHelper.KEY_PARENT_FK_ID, parentID);
        cv.put(cSQLDBHelper.KEY_CHILD_FK_ID, childID);
        cv.put(cSQLDBHelper.KEY_OUTPUT_FK_ID, outputID);
        cv.put(cSQLDBHelper.KEY_OUTCOME_FK_ID, outcomeID);

        return db.insert(cSQLDBHelper.TABLE_tblOUTPUT_OUTCOME, null, cv) >= 0;
    }

    @Override
    public boolean deleteOutputs() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblOUTPUT, null,
                null);

        // close the database connection
        db.close();

        return result > -1;
    }

    @Override
    public boolean deleteOutputOutcomes() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblOUTPUT_OUTCOME, null,
                null);

        // close the database connection
        db.close();

        return result > -1;
    }

    public boolean addActivity(cActivityModel activityModel,
                               Map<Set<Pair<Long, Long>>, Pair<Long, Long>> aoMap,
                               Set<Pair<Long, Long>> precedingSet,
                               Map<Long, Set<Pair<Long, Long>>> assignmentMap) {

        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
//        cv.put(cSQLDBHelper.KEY_COMPONENT_FK_ID, activityModel.getComponentID());
//        cv.put(cSQLDBHelper.KEY_PARENT_FK_ID, activityModel.getParentID());
//        cv.put(cSQLDBHelper.KEY_OUTPUT_FK_ID, activityModel.getOutputModel().getComponentID());

        // insert project details
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblACTIVITY, null, cv) < 0) {
                return false;
            }

            // add preceding activity
            for (Pair<Long, Long> pair : precedingSet) {
                long succeedingID = pair.first;
                long precedingID = pair.second;
                if (!addPrecedingActivity(succeedingID, precedingID))
                    return false;
            }

            // add assignment, staff and activity tuple
            for (Map.Entry<Long, Set<Pair<Long, Long>>> asaEntry : assignmentMap.entrySet()) {
                long activityID = asaEntry.getKey();
                for (Pair<Long, Long> saPair : asaEntry.getValue()) {
                    long assignmentID = saPair.first;
                    long staffID = saPair.second;

                    if (!addActivityAssignment(assignmentID, staffID, activityID))
                        return false;
                }
            }

            // add sub-logFrame, activity and output tuple
            for (Map.Entry<Set<Pair<Long, Long>>, Pair<Long, Long>> saoEntry : aoMap.entrySet()) {
                for (Pair<Long, Long> aoPair : saoEntry.getKey()) {
                    long activityID = aoPair.first;
                    long outputID = aoPair.second;

                    Pair<Long, Long> sPair = saoEntry.getValue();
                    long parentID = sPair.first;
                    long childID = sPair.second;

                    if (!addActivityOutput(parentID, childID, activityID, outputID))
                        return false;
                }
            }

        } catch (Exception e) {
            Log.d(TAG, "Exception in importing ACTIVITY from Excel: " + e.getMessage());
        }
        // close the database connection
        db.close();

        return true;
    }

    /**
     * @param activityID  activity identification
     * @param precedingID preceding identification
     * @return boolean
     */
    public boolean addPrecedingActivity(long activityID, long precedingID) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(cSQLDBHelper.KEY_ACTIVITY_FK_ID, activityID);
        cv.put(cSQLDBHelper.KEY_PRECEDING_ACTIVITY_FK_ID, precedingID);

        return db.insert(cSQLDBHelper.TABLE_tblPRECEDINGACTIVITY, null, cv) >= 0;
    }

    /**
     * @param assignmentID assignment identification
     * @param staffID      staff identification
     * @param activityID   activity identification
     * @return boolean
     */
    public boolean addActivityAssignment(long assignmentID, long staffID, long activityID) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(cSQLDBHelper.KEY_ID, assignmentID);
        cv.put(cSQLDBHelper.KEY_STAFF_FK_ID, staffID);
        cv.put(cSQLDBHelper.KEY_ACTIVITY_FK_ID, activityID);

        return db.insert(cSQLDBHelper.TABLE_tblACTIVITYASSIGNMENT, null, cv) >= 0;
    }

    /**
     * @param parentID   parent identification
     * @param childID    child identification
     * @param activityID activity identification
     * @param outputID   output identification
     * @return boolean
     */
    public boolean addActivityOutput(long parentID, long childID, long activityID, long outputID) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(cSQLDBHelper.KEY_PARENT_FK_ID, parentID);
        cv.put(cSQLDBHelper.KEY_CHILD_FK_ID, childID);
        cv.put(cSQLDBHelper.KEY_ACTIVITY_FK_ID, activityID);
        cv.put(cSQLDBHelper.KEY_OUTPUT_FK_ID, outputID);

        return db.insert(cSQLDBHelper.TABLE_tblACTIVITY_OUTPUT, null, cv) >= 0;
    }

    @Override
    public boolean deletePrecedingActivities() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblPRECEDINGACTIVITY, null,
                null);

        // close the database connection
        db.close();

        return result > -1;
    }

    @Override
    public boolean deleteActivityAssignments() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblACTIVITYASSIGNMENT, null,
                null);

        // close the database connection
        db.close();

        return result > -1;
    }

    @Override
    public boolean deleteActivityOutputs() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblACTIVITY_OUTPUT, null, null);

        // close the database connection
        db.close();

        return result > -1;
    }

    public boolean deleteActivities() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblACTIVITY, null, null);

        // close the database connection
        db.close();

        return result > -1;
    }

    public boolean addInput(cInputModel inputModel,
                            Map<Set<Pair<Long, Long>>, Pair<Long, Long>> iaMap,
                            long humanID, int humanQuantity,
                            Map<Long, Set<Pair<Long, Long>>> hiuMap,
                            long materialID, int materialQuantity,
                            long incomeID, Map<Long, Set<Pair<Long, Double>>> ffaMap,
                            long expenseID, double expense) {

        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
//        cv.put(cSQLDBHelper.KEY_COMPONENT_FK_ID, inputModel.getComponentID());
//        cv.put(cSQLDBHelper.KEY_ACTIVITY_FK_ID, inputModel.getActivityModel().getComponentID());
//        cv.put(cSQLDBHelper.KEY_RESOURCE_TYPE_FK_ID,
//                inputModel.getResourceTypeModel().getResourceTypeID());

        // insert project details
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblINPUT, null, cv) < 0) {
                return false;
            }

            // add sub-logFrame, activity and output tuple
            for (Map.Entry<Set<Pair<Long, Long>>, Pair<Long, Long>> siaEntry : iaMap.entrySet()) {
                for (Pair<Long, Long> iaPair : siaEntry.getKey()) {
                    long inputID = iaPair.first;
                    long activityID = iaPair.second;

                    Pair<Long, Long> sPair = siaEntry.getValue();
                    long parentID = sPair.first;
                    long childID = sPair.second;

                    if (!addInputActivity(parentID, childID, inputID, activityID))
                        return false;
                }
            }

//            if (humanID != -1) {
//                return addHumanInput(inputModel.getComponentID(), humanQuantity, hiuMap);
//            }
//
//            if (materialID != -1) {
//                return addMaterialInput(inputModel.getComponentID(), materialQuantity);
//            }
//
//            if (incomeID != -1) {
//                return addIncomeInput(inputModel.getComponentID(), ffaMap);
//            }
//
//            if (expenseID != -1) {
//                return addExpenseInput(inputModel.getComponentID(), expense);
//            }

        } catch (Exception e) {
            Log.d(TAG, "Exception in importing INPUT from Excel: " + e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }

    private boolean addInputActivity(long parentID, long childID, long inputID, long activityID) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(cSQLDBHelper.KEY_PARENT_FK_ID, parentID);
        cv.put(cSQLDBHelper.KEY_CHILD_FK_ID, childID);
        cv.put(cSQLDBHelper.KEY_INPUT_FK_ID, inputID);
        cv.put(cSQLDBHelper.KEY_ACTIVITY_FK_ID, activityID);

        return db.insert(cSQLDBHelper.TABLE_tblINPUT_ACTIVITY, null, cv) >= 0;
    }

    private boolean addHumanInput(long inputID, int humanQuantity, Map<Long,
            Set<Pair<Long, Long>>> hiuMap) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_INPUT_FK_ID, inputID);
        cv.put(cSQLDBHelper.KEY_QUANTITY, humanQuantity);

        // insert evaluation type details
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblHUMAN, null, cv) < 0) {
                return false;
            }

            // add activity, question and criteria tuple
            for (Map.Entry<Long, Set<Pair<Long, Long>>> hiuEntry : hiuMap.entrySet()) {
                long hsInputID = hiuEntry.getKey();
                for (Pair<Long, Long> huPair : hiuEntry.getValue()) {
                    long humanSetID = huPair.first;
                    long userID = huPair.second;

                    if (!addHumanSet(humanSetID, hsInputID, userID))
                        return false;
                }
            }

        } catch (Exception e) {
            Log.d(TAG, "Exception in importing HUMAN INPUT from Excel: " + e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }

    private boolean addHumanSet(long humanSetID, long hsInputID, long userID) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_ID, humanSetID);
        cv.put(cSQLDBHelper.KEY_INPUT_FK_ID, hsInputID);
        cv.put(cSQLDBHelper.KEY_USER_FK_ID, userID);

        // insert evaluation type details
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblHUMANSET, null, cv) < 0) {
                return false;
            }

        } catch (Exception e) {
            Log.d(TAG, "Exception in importing HUMAN SET from Excel: " + e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }

    private boolean addMaterialInput(long inputID, int materialQuantity) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_INPUT_FK_ID, inputID);
        cv.put(cSQLDBHelper.KEY_QUANTITY, materialQuantity);

        // insert evaluation type details
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblMATERIAL, null, cv) < 0) {
                return false;
            }
        } catch (Exception e) {
            Log.d(TAG, "Exception in importing MATERIAL INPUT from Excel: " + e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }

    private boolean addIncomeInput(long inputID, Map<Long, Set<Pair<Long, Double>>> ffaMap) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_INPUT_FK_ID, inputID);
        //cv.put(cSQLDBHelper.KEY_FUNDER_FK_ID, funderID);
        //cv.put(cSQLDBHelper.KEY_INCOME, income);
        // insert evaluation type details
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblINCOME, null, cv) < 0) {
                return false;
            }

            // add fundID, inputID, funderID and amount/fund tuple
            Gson gson = new Gson();
            for (Map.Entry<Long, Set<Pair<Long, Double>>> ffaEntry : ffaMap.entrySet()) {
                long fundID = ffaEntry.getKey();
                for (Pair<Long, Double> ifPair : ffaEntry.getValue()) {
                    long funderID = ifPair.first;
                    double fund = ifPair.second;
                    if (!addFund(fundID, inputID, funderID, fund)) {
                        return false;
                    }
                }
            }
        } catch (Exception e) {
            Log.d(TAG, "Exception in importing INCOME INPUT from Excel: " + e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }

    private boolean addFund(long fundID, long inputID, long funderID, double fund) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_ID, fundID);
        cv.put(cSQLDBHelper.KEY_INPUT_FK_ID, inputID);
        cv.put(cSQLDBHelper.KEY_FUNDER_FK_ID, funderID);
        cv.put(cSQLDBHelper.KEY_FUND, fund);

        // insert evaluation type details
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblFUND, null, cv) < 0) {
                return false;
            }
        } catch (Exception e) {
            Log.d(TAG, "Exception in importing FUND from Excel: " + e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }

    private boolean addExpenseInput(long inputID, double amount) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_INPUT_FK_ID, inputID);
        cv.put(cSQLDBHelper.KEY_EXPENSE, amount);

        // insert evaluation type details
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblEXPENSE, null, cv) < 0) {
                return false;
            }
        } catch (Exception e) {
            Log.d(TAG, "Exception in importing EXPENSE INPUT from Excel: " + e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }

    @Override
    public boolean deleteInputActivities() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblINPUT_ACTIVITY, null, null);

        // close the database connection
        db.close();

        return result > -1;
    }

    @Override
    public boolean deleteHumans() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblHUMAN, null, null);

        // close the database connection
        db.close();

        return result > -1;
    }

    @Override
    public boolean deleteHumanSets() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblHUMANSET, null, null);

        // close the database connection
        db.close();

        return result > -1;
    }

    @Override
    public boolean deleteMaterials() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblMATERIAL, null, null);

        // close the database connection
        db.close();

        return result > -1;
    }

    @Override
    public boolean deleteIncomes() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblINCOME, null, null);

        // close the database connection
        db.close();

        return result > -1;
    }

    @Override
    public boolean deleteFunds() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblFUND, null, null);

        // close the database connection
        db.close();

        return result > -1;
    }

    @Override
    public boolean deleteExpenses() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblEXPENSE, null, null);

        // close the database connection
        db.close();

        return result > -1;
    }

    @Override
    public boolean deleteInputs() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblINPUT, null, null);

        // close the database connection
        db.close();

        return result > -1;
    }

    /* ############################## EVALUATION CRITERIA FUNCTIONS ##############################*/

    /**
     * This function extracts criteria data from excel and adds it to the database.
     *
     * @return boolean
     */
    @Override
    public boolean addCriteriaFromExcel() {
        Workbook workbook = excelHelper.getWorkbookLOGFRAME();
        Sheet ECSheet = workbook.getSheet(cExcelHelper.SHEET_tblCRITERIA);

        if (ECSheet == null) {
            return false;
        }

        for (Row cRow : ECSheet) {
            //just skip the row if row number is 0
            if (cRow.getRowNum() == 0) {
                continue;
            }

            cCriteriaModel criteriaModel = new cCriteriaModel();

//            criteriaModel.setCriteriaID((int)
//                    cRow.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            criteriaModel.setName(
                    cRow.getCell(1, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
            criteriaModel.setDescription(
                    cRow.getCell(2, Row.CREATE_NULL_AS_BLANK).getStringCellValue());

            if (!addCriteria(criteriaModel)) {
                return false;
            }
        }

        return true;
    }

    /**
     * This function adds criteria data to the database.
     *
     * @param criteriaModel criteria model
     * @return boolean
     */
    public boolean addCriteria(cCriteriaModel criteriaModel) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
//        cv.put(cSQLDBHelper.KEY_ID, criteriaModel.getCriteriaID());
        cv.put(cSQLDBHelper.KEY_NAME, criteriaModel.getName());
        cv.put(cSQLDBHelper.KEY_DESCRIPTION, criteriaModel.getDescription());

        // insert project details
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblCRITERIA, null, cv) < 0) {
                return false;
            }
        } catch (Exception e) {
            Log.d(TAG, "Exception in importing EVALUATION CRITERIA from Excel: " + e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }

    @Override
    public boolean deleteCriteria() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblCRITERIA, null,
                null);

        // close the database connection
        db.close();

        return result > -1;
    }

    /*################################ QUESTION GROUPING FUNCTIONS ###############################*/

    /**
     * This function extracts question grouping data from excel and adds it to the database.
     *
     * @return boolean
     */
    @Override
    public boolean addQuestionGroupingFromExcel() {
        Workbook workbook = excelHelper.getWorkbookLOGFRAME();
        Sheet QGSheet = workbook.getSheet(cExcelHelper.SHEET_tblQUESTIONGROUPING);

        if (QGSheet == null) {
            return false;
        }

        for (Row cRow : QGSheet) {
            //just skip the row if row number is 0
            if (cRow.getRowNum() == 0) {
                continue;
            }

            cQuestionGroupingModel questionGroupingModel = new cQuestionGroupingModel();
//
//            questionGroupingModel.setQuestionGroupingID(
//                    (int) cRow.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
//            questionGroupingModel.setLabel((int) cRow.getCell(1,
//                    Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            questionGroupingModel.setName(
                    cRow.getCell(2, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
            questionGroupingModel.setDescription(
                    cRow.getCell(3, Row.CREATE_NULL_AS_BLANK).getStringCellValue());

            if (!addQuestionGroupingFromExcel(questionGroupingModel)) {
                return false;
            }
        }

        return true;
    }

    /**
     * This function adds question grouping data to the database.
     *
     * @param questionGroupingModel question grouping model
     * @return boolean
     */
    public boolean addQuestionGroupingFromExcel(cQuestionGroupingModel questionGroupingModel) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
//        cv.put(cSQLDBHelper.KEY_ID, questionGroupingModel.getQuestionGroupingID());
        cv.put(cSQLDBHelper.KEY_LABEL, questionGroupingModel.getLabel());
        cv.put(cSQLDBHelper.KEY_NAME, questionGroupingModel.getName());
        cv.put(cSQLDBHelper.KEY_DESCRIPTION, questionGroupingModel.getDescription());

        // insert project details
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblQUESTIONGROUPING, null, cv) < 0) {
                return false;
            }
        } catch (Exception e) {
            Log.d(TAG, "Exception in importing QUESTION GROUPING from Excel: " + e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }

    @Override
    public boolean deleteQuestionGroupings() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblQUESTIONGROUPING, null,
                null);

        // close the database connection
        db.close();

        return result > -1;
    }

    /* ################################## QUESTION TYPE FUNCTIONS ################################*/

    /**
     * This function extracts question type data from excel and adds it to the database.
     *
     * @return boolean
     */

    @Override
    public boolean addQuestionTypeFromExcel() {
        Workbook workbook = excelHelper.getWorkbookLOGFRAME();
        Sheet QTSheet = workbook.getSheet(cExcelHelper.SHEET_tblQUESTIONTYPE);

        if (QTSheet == null) {
            return false;
        }

        for (Row cRow : QTSheet) {
            //just skip the row if row number is 0
            if (cRow.getRowNum() == 0) {
                continue;
            }

            cQuestionTypeModel questionTypeModel = new cQuestionTypeModel();

//            questionTypeModel.setQuestionTypeID(
//                    (int) cRow.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            questionTypeModel.setName(
                    cRow.getCell(1, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
            questionTypeModel.setDescription(
                    cRow.getCell(2, Row.CREATE_NULL_AS_BLANK).getStringCellValue());

            if (!addQuestionType(questionTypeModel)) {
                return false;
            }
        }

        return true;
    }

    /**
     * This function adds question grouping data to the database.
     *
     * @param questionTypeModel question grouping model
     * @return boolean
     */
    public boolean addQuestionType(cQuestionTypeModel questionTypeModel) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
//        cv.put(cSQLDBHelper.KEY_ID, questionTypeModel.getQuestionTypeID());
        cv.put(cSQLDBHelper.KEY_NAME, questionTypeModel.getName());
        cv.put(cSQLDBHelper.KEY_DESCRIPTION, questionTypeModel.getDescription());

        // insert project details
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblQUESTIONTYPE, null, cv) < 0) {
                return false;
            }
        } catch (Exception e) {
            Log.d(TAG, "Exception in importing QUESTION TYPE from Excel: " + e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }

    @Override
    public boolean deleteQuestionTypes() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblQUESTIONTYPE, null,
                null);

        // close the database connection
        db.close();

        return result > -1;
    }

    /* #################################### QUESTION FUNCTIONS ###################################*/

    /**
     * This function extracts question data from excel and adds it to the database.
     *
     * @return boolean
     */
    @Override
    public boolean addQuestionFromExcel() {
        Workbook workbook = excelHelper.getWorkbookLOGFRAME();
        Sheet QSheet = workbook.getSheet(cExcelHelper.SHEET_tblQUESTION);
        Sheet PQSheet = workbook.getSheet(cExcelHelper.SHEET_tblPRIMITIVEQUESTION);
        Sheet AQSheet = workbook.getSheet(cExcelHelper.SHEET_tblARRAYQUESTION);
        Sheet MQSheet = workbook.getSheet(cExcelHelper.SHEET_tblMATRIXQUESTION);
        Sheet QCSheet = workbook.getSheet(cExcelHelper.SHEET_tblQUESTION_CRITERIA);

        if (QSheet == null) {
            return false;
        }

        for (Row rowQ : QSheet) {
            //just skip the row if row number is 0
            if (rowQ.getRowNum() == 0) {
                continue;
            }

            cQuestionModel questionModel = new cQuestionModel();

            questionModel.setQuestionID((int) rowQ.getCell(0,
                    Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
//            questionModel.getLogFrameModel().setLogFrameID((int) rowQ.getCell(1,
//                    Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
//            questionModel.getComponentModel().setComponentID((int) rowQ.getCell(2,
////                    Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
//            questionModel.getQuestionTypeModel().setQuestionTypeID((int) rowQ.getCell(3,
//                    Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
//            questionModel.getQuestionGroupingModel().setQuestionGroupingID((int) rowQ.getCell(4,
//                    Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            questionModel.setLabel((int) rowQ.getCell(5,
                    Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            questionModel.setQuestion(rowQ.getCell(6,
                    Row.CREATE_NULL_AS_BLANK).getStringCellValue());
            questionModel.setDefaultChart((int) rowQ.getCell(7,
                    Row.CREATE_NULL_AS_BLANK).getNumericCellValue());

            /* get primitive question */
            Set<Long> primitiveChartIDs = new HashSet<>();

            for (Row rowPQ : PQSheet) {
                //just skip the row if row number is 0
                if (rowPQ.getRowNum() == 0) {
                    continue;
                }

                long questionID = (int) rowPQ.getCell(0,
                        Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                if (questionModel.getQuestionID() == questionID) {
                    primitiveChartIDs.add((long) rowPQ.getCell(1,
                            Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
                }
            }

            /* get array question */
            Set<Pair<Long, Long>> arrayChartIDs = new HashSet<>();
            for (Row rowAQ : AQSheet) {
                //just skip the row if row number is 0
                if (rowAQ.getRowNum() == 0) {
                    continue;
                }

                long questionID = (long) rowAQ.getCell(0,
                        Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                if (questionModel.getQuestionID() == questionID) {
                    long arraySet = (long) rowAQ.getCell(1,
                            Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                    long arrayChart = (long) rowAQ.getCell(2,
                            Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                    arrayChartIDs.add(new Pair<>(arraySet, arrayChart));
                }
            }

            /* get matrix question */
            Set<Pair<Long, Long>> matrixChartIDs = new HashSet<>();
            for (Row rowMQ : MQSheet) {
                //just skip the row if row number is 0
                if (rowMQ.getRowNum() == 0) {
                    continue;
                }

                long questionID = (long) rowMQ.getCell(0,
                        Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                if (questionModel.getQuestionID() == questionID) {
                    long matrixSet = (long) rowMQ.getCell(1,
                            Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                    long matrixChart = (long) rowMQ.getCell(2,
                            Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                    matrixChartIDs.add(new Pair<>(matrixSet, matrixChart));
                }
            }


            /* get question criteria */
            Set<Pair<Long, Long>> qcSet = new HashSet<>();
            for (Row rowQC : QCSheet) {
                //just skip the row if row number is 0
                if (rowQC.getRowNum() == 0) {
                    continue;
                }

                long questionID = (int) rowQC.getCell(0,
                        Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                if (questionModel.getQuestionID() == questionID) {
                    long criteriaID = (int) rowQC.getCell(1,
                            Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                    qcSet.add(new Pair<>(questionID, criteriaID));
                }
            }

            if (!addQuestion(questionModel, primitiveChartIDs, arrayChartIDs,
                    matrixChartIDs, qcSet)) {
                return false;
            }
        }

        return true;
    }

    /**
     * This function adds question data to the database.
     *
     * @param questionModel question model
     * @param qcSet
     * @return boolean
     */
    public boolean addQuestion(cQuestionModel questionModel,
                               Set<Long> primitiveChartIDs,
                               Set<Pair<Long, Long>> arrayChartIDs,
                               Set<Pair<Long, Long>> matrixChartIDs,
                               Set<Pair<Long, Long>> qcSet) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_ID, questionModel.getQuestionID());
//        cv.put(cSQLDBHelper.KEY_LOGFRAME_FK_ID, questionModel.getLogFrameModel().getLogFrameID());
//        cv.put(cSQLDBHelper.KEY_COMPONENT_FK_ID, questionModel.getComponentModel().getComponentID());
//        cv.put(cSQLDBHelper.KEY_QUESTION_TYPE_FK_ID,
//                questionModel.getQuestionTypeModel().getQuestionTypeID());
//        cv.put(cSQLDBHelper.KEY_QUESTION_GROUPING_FK_ID,
//                questionModel.getQuestionGroupingModel().getQuestionGroupingID());
        cv.put(cSQLDBHelper.KEY_LABEL, questionModel.getLabel());
        cv.put(cSQLDBHelper.KEY_QUESTION, questionModel.getQuestion());
        cv.put(cSQLDBHelper.KEY_DEFAULT_CHART, questionModel.getDefaultChart());

        try {
            /* insert question details */
            if (db.insert(cSQLDBHelper.TABLE_tblQUESTION, null, cv) < 0) {
                return false;
            }

            for (long chartID : primitiveChartIDs) {
                if (!addPrimitiveQuestion(questionModel.getQuestionID(), chartID))
                    return false;
            }

            for (Pair<Long, Long> arrayQuestion : arrayChartIDs) {
                if (!addArrayQuestion(questionModel.getQuestionID(), arrayQuestion.first,
                        arrayQuestion.second))
                    return false;
            }

            for (Pair<Long, Long> matrixQuestion : matrixChartIDs) {
                if (!addMatrixQuestion(questionModel.getQuestionID(), matrixQuestion.first,
                        matrixQuestion.second))
                    return false;
            }

            // add question criteria
            for (Pair<Long, Long> pair : qcSet) {
                if (!addQuestionCriteria(pair.first, pair.second))
                    return false;
            }

        } catch (Exception e) {
            Log.d(TAG, "Exception in importing QUESTION from Excel: " + e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }

    public boolean addPrimitiveQuestion(long questionID, long primitiveChartID) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_QUESTION_FK_ID, questionID);
        cv.put(cSQLDBHelper.KEY_PRIMITIVE_CHART_FK_ID, primitiveChartID);

        // insert project details
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblPRIMITIVEQUESTION, null, cv) < 0) {
                return false;
            }
        } catch (Exception e) {
            Log.d(TAG, "Exception in importing PRIMITIVE QUESTION from Excel: " + e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }

    public boolean addArrayQuestion(long questionID, long arraySetID, long arrayChartID) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_QUESTION_FK_ID, questionID);
        cv.put(cSQLDBHelper.KEY_ARRAY_SET_FK_ID, arraySetID);
        cv.put(cSQLDBHelper.KEY_ARRAY_CHART_FK_ID, arrayChartID);

        // insert project details
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblARRAYQUESTION, null, cv) < 0) {
                return false;
            }
        } catch (Exception e) {
            Log.d(TAG, "Exception in importing ARRAY QUESTION from Excel: " + e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }

    public boolean addMatrixQuestion(long questionID, long matrixSetID, long matrixChartID) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_QUESTION_FK_ID, questionID);
        cv.put(cSQLDBHelper.KEY_MATRIX_SET_FK_ID, matrixSetID);
        cv.put(cSQLDBHelper.KEY_MATRIX_CHART_FK_ID, matrixChartID);

        // insert project details
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblMATRIXQUESTION, null, cv) < 0) {
                return false;
            }
        } catch (Exception e) {
            Log.d(TAG, "Exception in importing MATRIX QUESTION from Excel: " + e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }
    public boolean addQuestionCriteria(long questionID, long criteriaID) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(cSQLDBHelper.KEY_QUESTION_FK_ID, questionID);
        cv.put(cSQLDBHelper.KEY_CRITERIA_FK_ID, criteriaID);

        return db.insert(cSQLDBHelper.TABLE_tblQUESTION_CRITERIA, null, cv) >= 0;
    }
    @Override
    public boolean deleteQuestions() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblQUESTION, null,
                null);

        // close the database connection
        db.close();

        return result > -1;
    }

    @Override
    public boolean deletePrimitiveQuestions() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblPRIMITIVEQUESTION, null,
                null);

        // close the database connection
        db.close();

        return result > -1;
    }
    @Override
    public boolean deleteArrayQuestions() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblARRAYQUESTION, null,
                null);

        // close the database connection
        db.close();

        return result > -1;
    }
    @Override
    public boolean deleteMatrixQuestions() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblMATRIXQUESTION, null,
                null);

        // close the database connection
        db.close();

        return result > -1;
    }
    @Override
    public boolean deleteQuestionCriteria() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblQUESTION_CRITERIA, null,
                null);

        // close the database connection
        db.close();

        return result > -1;
    }

    /* ################################## RAID CATEGORY FUNCTIONS ################################*/

    /**
     * This function extracts RAID category data from excel and adds it to the database.
     *
     * @return boolean
     */

    @Override
    public boolean addRaidCategoryFromExcel() {
        Workbook workbook = excelHelper.getWorkbookLOGFRAME();
        Sheet QTSheet = workbook.getSheet(cExcelHelper.SHEET_tblRAIDCATEGORY);

        if (QTSheet == null) {
            return false;
        }

        for (Row cRow : QTSheet) {
            //just skip the row if row number is 0
            if (cRow.getRowNum() == 0) {
                continue;
            }

            cRaidCategoryModel raidCategoryModel = new cRaidCategoryModel();

            raidCategoryModel.setRaidCategoryID(
                    (int) cRow.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            raidCategoryModel.setName(
                    cRow.getCell(1, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
            raidCategoryModel.setDescription(
                    cRow.getCell(2, Row.CREATE_NULL_AS_BLANK).getStringCellValue());

            if (!addRaidCategory(raidCategoryModel)) {
                return false;
            }
        }

        return true;
    }

    /**
     * This function adds raid category data to the database.
     *
     * @param raidCategoryModel raid category model
     * @return boolean
     */
    public boolean addRaidCategory(cRaidCategoryModel raidCategoryModel) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_ID, raidCategoryModel.getRaidCategoryID());
        cv.put(cSQLDBHelper.KEY_NAME, raidCategoryModel.getName());
        cv.put(cSQLDBHelper.KEY_DESCRIPTION, raidCategoryModel.getDescription());

        // insert project details
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblRAIDCATEGORY, null, cv) < 0) {
                return false;
            }
        } catch (Exception e) {
            Log.d(TAG, "Exception in importing RAID CATEGORY from Excel: " + e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }

    @Override
    public boolean deleteRaidCategories() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblRAIDCATEGORY, null,
                null);

        // close the database connection
        db.close();

        return result > -1;
    }


    /* ##################################### RAID FUNCTIONS ######################################*/

    /**
     * This function extracts raid data from excel and adds it to the database.
     *
     * @return boolean
     */
    @Override
    public boolean addRaidFromExcel() {
        Workbook workbook = excelHelper.getWorkbookLOGFRAME();
        Sheet RSheet = workbook.getSheet(cExcelHelper.SHEET_tblRAID);
        Sheet CRSheet = workbook.getSheet(cExcelHelper.SHEET_tblCOMPONENT_RAID);

        if (RSheet == null) {
            return false;
        }

        for (Row cRow : RSheet) {
            //just skip the row if row number is 0
            if (cRow.getRowNum() == 0) {
                continue;
            }

            cRaidModel raidModel = new cRaidModel();

            raidModel.setRaidID((int) cRow.getCell(0,
                    Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
//            raidModel.getLogFrameModel().setLogFrameID((int) cRow.getCell(1,
//                    Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            raidModel.getRaidCategoryModel().setRaidCategoryID((int) cRow.getCell(2,
                    Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            raidModel.getOriginatorModel().setHumanSetID((int) cRow.getCell(3,
                    Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            raidModel.getOwnerModel().setHumanSetID((int) cRow.getCell(4,
                    Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            raidModel.getFrequencyModel().setFrequencyID((int) cRow.getCell(5,
                    Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            raidModel.getRaidLikelihoodModel().setRaidLikelihoodID((int) cRow.getCell(6,
                    Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            raidModel.getRaidImpactModel().setRaidImpactID((int) cRow.getCell(7,
                    Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            raidModel.getRobotModel().setRobotID((int) cRow.getCell(8,
                    Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            raidModel.setScore((int) cRow.getCell(9,
                    Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            raidModel.setName(cRow.getCell(10,
                    Row.CREATE_NULL_AS_BLANK).getStringCellValue());
            raidModel.setDescription(cRow.getCell(11,
                    Row.CREATE_NULL_AS_BLANK).getStringCellValue());

            /* get raids of the outcome */
            Set<Pair<Long, Long>> crSet = new HashSet<>();
            for (Row rowCR : CRSheet) {
                //just skip the row if row number is 0
                if (rowCR.getRowNum() == 0) {
                    continue;
                }

                long raidID = (int) rowCR.getCell(1,
                        Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                if (raidModel.getRaidID() == raidID) {
                    long componentID = (int) rowCR.getCell(0,
                            Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                    crSet.add(new Pair<>(componentID, raidID));
                }
            }

            if (!addRaid(raidModel, crSet)) {
                return false;
            }
        }

        return true;
    }

    /**
     * This function adds raid data to the database.
     *
     * @param raidModel raid model
     * @param crSet
     * @return boolean
     */
    public boolean addRaid(cRaidModel raidModel, Set<Pair<Long, Long>> crSet) {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // create content object for storing data
        ContentValues cv = new ContentValues();

        // assign values to the table fields
        cv.put(cSQLDBHelper.KEY_ID, raidModel.getRaidID());
//        cv.put(cSQLDBHelper.KEY_LOGFRAME_FK_ID, raidModel.getLogFrameModel().getLogFrameID());
        cv.put(cSQLDBHelper.KEY_RAID_CATEGORY_FK_ID,
                raidModel.getRaidCategoryModel().getRaidCategoryID());
        cv.put(cSQLDBHelper.KEY_ORIGINATOR_FK_ID, raidModel.getOriginatorModel().getHumanSetID());
        cv.put(cSQLDBHelper.KEY_OWNER_FK_ID, raidModel.getOwnerModel().getHumanSetID());
        cv.put(cSQLDBHelper.KEY_FREQUENCY_FK_ID, raidModel.getFrequencyModel().getFrequencyID());
        cv.put(cSQLDBHelper.KEY_RAID_LIKELIHOOD_FK_ID,
                raidModel.getRaidLikelihoodModel().getRaidLikelihoodID());
        cv.put(cSQLDBHelper.KEY_RAID_IMPACT_FK_ID, raidModel.getRaidImpactModel().getRaidImpactID());
        cv.put(cSQLDBHelper.KEY_ROBOT_FK_ID, raidModel.getRobotModel().getRobotID());
        cv.put(cSQLDBHelper.KEY_SCORE, raidModel.getScore());
        cv.put(cSQLDBHelper.KEY_NAME, raidModel.getName());
        cv.put(cSQLDBHelper.KEY_DESCRIPTION, raidModel.getDescription());

        // insert project details
        try {
            if (db.insert(cSQLDBHelper.TABLE_tblRAID, null, cv) < 0) {
                return false;
            }

            // add component raid
            for (Pair<Long, Long> pair : crSet) {
                if (!addComponentRaid(pair.first, pair.second))
                    return false;
            }

        } catch (Exception e) {
            Log.d(TAG, "Exception in importing RAID from Excel: " + e.getMessage());
        }

        // close the database connection
        db.close();

        return true;
    }
    public boolean addComponentRaid(long componentID, long raidID) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(cSQLDBHelper.KEY_COMPONENT_FK_ID, componentID);
        cv.put(cSQLDBHelper.KEY_RAID_FK_ID, raidID);

        return db.insert(cSQLDBHelper.TABLE_tblCOMPONENT_RAID, null, cv) >= 0;
    }
    @Override
    public boolean deleteRaids() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblRAID, null,
                null);

        // close the database connection
        db.close();

        return result > -1;
    }
    @Override
    public boolean deleteComponentRaids() {
        // open the connection to the database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // delete all records
        long result = db.delete(cSQLDBHelper.TABLE_tblCOMPONENT_RAID, null,
                null);

        // close the database connection
        db.close();

        return result > -1;
    }


//    /* ######################################## IMPACT FUNCTIONS ########################################*/
//
//    /**
//     * This function extracts and adds the impact data from excel.
//     *
//     * @return boolean
//     */
//    @Override
//    public boolean addImpactFromExcel() {
//        Workbook workbook = excelHelper.getWorkbookLOGFRAME();
//        Sheet impactSheet = workbook.getSheet(cExcelHelper.SHEET_tblIMPACT);
//
//        if (impactSheet == null) {
//            return false;
//        }
//
//        for (Row cRow : impactSheet) {
//            //just skip the row if row number is 0
//            if (cRow.getRowNum() == 0) {
//                continue;
//            }
//
//            cImpactModel impactModel = new cImpactModel();
//
//            impactModel.setImpactID((int) cRow.getCell(0,
//                    Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
//            impactModel.setParentID((int) cRow.getCell(1,
//                    Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
//            impactModel.setLogFrameID((int) cRow.getCell(2,
//                    Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
//            impactModel.setName(cRow.getCell(3,
//                    Row.CREATE_NULL_AS_BLANK).getStringCellValue());
//            impactModel.setDescription(cRow.getCell(4,
//                    Row.CREATE_NULL_AS_BLANK).getStringCellValue());
//
//            /* get questions grouped by criteria of the impact */
//            Map<Long, Set<Pair<Long, Long>>> iqcMap = new HashMap<>();
//            Set<Pair<Long, Long>> qcSet = new HashSet<>();
//            long impactID, questionID, criteriaID;
//
//            Sheet iqcSheet = workbook.getSheet(cExcelHelper.SHEET_tblIMPACT_QUESTION);
//            for (Row rowIQC : iqcSheet) {
//                //just skip the row if row number is 0
//                if (rowIQC.getRowNum() == 0) {
//                    continue;
//                }
//
//                impactID = (int) rowIQC.getCell(0,
//                        Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
//                if (impactModel.getImpactID() == impactID) {
//                    questionID = (int) rowIQC.getCell(1,
//                            Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
//                    criteriaID = (int) rowIQC.getCell(2,
//                            Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
//                    qcSet.add(new Pair<>(questionID, criteriaID));
//                }
//            }
//
//            /* get raids of the impact */
//            Set<Pair<Long, Long>> raidSet = new HashSet<>();
//            long raidID;
//            Sheet impactRaidSheet = workbook.getSheet(cExcelHelper.SHEET_tblIMPACT_RAID);
//            for (Row rowImpactRaid : impactRaidSheet) {
//                //just skip the row if row number is 0
//                if (rowImpactRaid.getRowNum() == 0) {
//                    continue;
//                }
//
//                impactID = (int) rowImpactRaid.getCell(1,
//                        Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
//                if (impactModel.getImpactID() == impactID) {
//                    raidID = (int) rowImpactRaid.getCell(0,
//                            Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
//                    raidSet.add(new Pair<>(raidID, impactID));
//                }
//            }
//
//            iqcMap.put(impactModel.getImpactID(), qcSet);
//
//            //Gson gson = new Gson();
//            //Log.d(TAG, gson.toJson(raidSet));
//            //Log.d(TAG, gson.toJson(iqcMap));
//
//            if (!addImpactFromExcel(impactModel, iqcMap, raidSet)) {
//                return false;
//            }
//        }
//
//        return true;
//    }
//
//    /**
//     * This function extracts and adds the impact data from excel.
//     *
//     * @param impactModel impact model
//     * @param iqcMap      impact question criteria
//     * @param raidSet     raid set
//     * @return boolean
//     */
//    public boolean addImpactFromExcel(cImpactModel impactModel,
//                                      Map<Long, Set<Pair<Long, Long>>> iqcMap,
//                                      Set<Pair<Long, Long>> raidSet) {
//        // open the connection to the database
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//
//        // create content object for storing data
//        ContentValues cv = new ContentValues();
//
//        // assign values to the table fields
//        cv.put(cSQLDBHelper.KEY_ID, impactModel.getImpactID());
//        cv.put(cSQLDBHelper.KEY_PARENT_FK_ID, impactModel.getParentID());
//        cv.put(cSQLDBHelper.KEY_LOGFRAME_FK_ID, impactModel.getLogFrameID());
//        cv.put(cSQLDBHelper.KEY_NAME, impactModel.getName());
//        cv.put(cSQLDBHelper.KEY_DESCRIPTION, impactModel.getDescription());
//
//        // insert project details
//        try {
//            if (db.insert(cSQLDBHelper.TABLE_tblIMPACT, null, cv) < 0) {
//                return false;
//            }
//
//            // add impact, question and criteria tuple
//            for (Map.Entry<Long, Set<Pair<Long, Long>>> iqcEntry : iqcMap.entrySet()) {
//                long impactID = iqcEntry.getKey();
//                for (Pair<Long, Long> qcPair : iqcEntry.getValue()) {
//                    long questionID = qcPair.first;
//                    long criteriaID = qcPair.second;
//
//                    if (!addImpactQuestionCriteria(impactID, questionID, criteriaID))
//                        return false;
//                }
//            }
//
//            // add raid
//            for (Pair<Long, Long> pair : raidSet) {
//                if (!addImpactRaid(pair.first, pair.second))
//                    return false;
//            }
//        } catch (Exception e) {
//            Log.d(TAG, "Exception in importing IMPACT from Excel: " + e.getMessage());
//        }
//
//        // close the database connection
//        db.close();
//
//        return true;
//    }
//
//    /**
//     * This function adds the impact, question and criteria to the database.
//     *
//     * @param impactID   impact identification
//     * @param questionID question identification
//     * @param criteriaID criteria identification
//     * @return boolean
//     */
//    public boolean addImpactQuestionCriteria(long impactID, long questionID, long criteriaID) {
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//
//        ContentValues cv = new ContentValues();
//
//        cv.put(cSQLDBHelper.KEY_IMPACT_FK_ID, impactID);
//        cv.put(cSQLDBHelper.KEY_QUESTION_FK_ID, questionID);
//        cv.put(cSQLDBHelper.KEY_EVALUATION_CRITERIA_FK_ID, criteriaID);
//
//        return db.insert(cSQLDBHelper.TABLE_tblIMPACT_QUESTION, null, cv) >= 0;
//    }
//
//    /**
//     * This function adds the impact and raid to the database.
//     *
//     * @param impactID impact identification
//     * @param raidID   raid identification
//     * @return boolean
//     */
//    public boolean addImpactRaid(long impactID, long raidID) {
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//
//        ContentValues cv = new ContentValues();
//
//        cv.put(cSQLDBHelper.KEY_IMPACT_FK_ID, impactID);
//        cv.put(cSQLDBHelper.KEY_RAID_FK_ID, raidID);
//
//        return db.insert(cSQLDBHelper.TABLE_tblIMPACT_RAID, null, cv) >= 0;
//    }
//
//    @Override
//    public boolean deleteImpactQuestions() {
//        // open the connection to the database
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//
//        // delete all records
//        long result = db.delete(cSQLDBHelper.TABLE_tblIMPACT_QUESTION, null,
//                null);
//
//        // close the database connection
//        db.close();
//
//        return result > -1;
//    }
//
//    @Override
//    public boolean deleteImpactRaids() {
//        // open the connection to the database
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//
//        // delete all records
//        long result = db.delete(cSQLDBHelper.TABLE_tblIMPACT_RAID, null,
//                null);
//
//        // close the database connection
//        db.close();
//
//        return result > -1;
//    }
//
//    @Override
//    public boolean deleteImpacts() {
//        // open the connection to the database
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//
//        // delete all records
//        long result = db.delete(cSQLDBHelper.TABLE_tblIMPACT, null,
//                null);
//
//        // close the database connection
//        db.close();
//
//        return result > -1;
//    }

    /* ######################################## OUTCOME FUNCTIONS ########################################*/

//    /**
//     * This function extracts and adds the outcome data from excel.
//     *
//     * @return boolean
//     */
//    @Override
//    public boolean addOutcomeFromExcel() {
//        Workbook workbook = excelHelper.getWorkbookLOGFRAME();
//        Sheet outcomeSheet = workbook.getSheet(cExcelHelper.SHEET_tblOUTCOME);
//        Gson gson = new Gson();
//        if (outcomeSheet == null) {
//            return false;
//        }
//
//        for (Row cRow : outcomeSheet) {
//            //just skip the row if row number is 0
//            if (cRow.getRowNum() == 0) {
//                continue;
//            }
//
//            cOutcomeModel outcomeModel = new cOutcomeModel();
//
//            outcomeModel.setOutcomeID((int) cRow.getCell(0,
//                    Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
//            outcomeModel.setParentID((int) cRow.getCell(1,
//                    Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
//            outcomeModel.setImpactID((int) cRow.getCell(2,
//                    Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
//            outcomeModel.setLogFrameID((int) cRow.getCell(3,
//                    Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
//            outcomeModel.setName(cRow.getCell(4,
//                    Row.CREATE_NULL_AS_BLANK).getStringCellValue());
//            outcomeModel.setDescription(cRow.getCell(5,
//                    Row.CREATE_NULL_AS_BLANK).getStringCellValue());
//
//            /* get questions grouped by criteria of the outcome */
//            Map<Long, Set<Pair<Long, Long>>> oqcMap = new HashMap<>();
//            Set<Pair<Long, Long>> qcSet = new HashSet<>();
//            long outcomeID, questionID, criteriaID;
//
//            Sheet oqcSheet = workbook.getSheet(cExcelHelper.SHEET_tblOUTCOME_QUESTION);
//            for (Row rowOQC : oqcSheet) {
//                //just skip the row if row number is 0
//                if (rowOQC.getRowNum() == 0) {
//                    continue;
//                }
//
//                outcomeID = (int) rowOQC.getCell(0,
//                        Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
//                if (outcomeModel.getOutcomeID() == outcomeID) {
//                    questionID = (int) rowOQC.getCell(1,
//                            Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
//                    criteriaID = (int) rowOQC.getCell(2,
//                            Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
//                    qcSet.add(new Pair<>(questionID, criteriaID));
//                }
//            }
//            oqcMap.put(outcomeModel.getOutcomeID(), qcSet);
//
//            /* get impacts of the outcome for a sub-logframe */
//            Map<Set<Pair<Long, Long>>, Pair<Long, Long>> soiMap = new HashMap<>();
//            long parentID, childID, impactID;
//
//            Sheet oiSheet = workbook.getSheet(cExcelHelper.SHEET_tblOUTCOME_IMPACT);
//            for (Row rowOI : oiSheet) {
//                //just skip the row if row number is 0
//                if (rowOI.getRowNum() == 0) {
//                    continue;
//                }
//
//                outcomeID = (int) rowOI.getCell(2, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
//                if (outcomeModel.getOutcomeID() == outcomeID) {
//                    Set<Pair<Long, Long>> impactSet = new HashSet<>();
//                    parentID = (int) rowOI.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
//                    childID = (int) rowOI.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
//                    impactID = (int) rowOI.getCell(3, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
//                    impactSet.add(new Pair<>(outcomeID, impactID));
//                    soiMap.put(impactSet, new Pair<>(parentID, childID));
//                }
//            }
//
//            /* get raids of the outcome */
//            Set<Pair<Long, Long>> raidSet = new HashSet<>();
//            long raidID;
//            Sheet outcomeRaidSheet = workbook.getSheet(cExcelHelper.SHEET_tblOUTCOME_RAID);
//            for (Row rowOutcomeRaid : outcomeRaidSheet) {
//                //just skip the row if row number is 0
//                if (rowOutcomeRaid.getRowNum() == 0) {
//                    continue;
//                }
//
//                outcomeID = (int) rowOutcomeRaid.getCell(0,
//                        Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
//                if (outcomeModel.getOutcomeID() == outcomeID) {
//                    raidID = (int) rowOutcomeRaid.getCell(1,
//                            Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
//                    raidSet.add(new Pair<>(outcomeID, raidID));
//                }
//            }
//            //Log.d(TAG, gson.toJson(osiMap));
//            //Log.d(TAG, gson.toJson(oqcMap));
//            //Log.d(TAG, gson.toJson(raidSet));
//
//            if (!addOutcomeFromExcel(outcomeModel, soiMap, oqcMap, raidSet)) {
//                return false;
//            }
//        }
//
//        return true;
//    }
//
//    /**
//     * @param outcomeModel outcome model
//     * @param soiMap       sub-logframe outcome impact
//     * @param oqcMap       outcome question criteria
//     * @param raidSet      raid set
//     * @return boolean
//     */
//    public boolean addOutcomeFromExcel(cOutcomeModel outcomeModel,
//                                       Map<Set<Pair<Long, Long>>, Pair<Long, Long>> soiMap,
//                                       Map<Long, Set<Pair<Long, Long>>> oqcMap,
//                                       Set<Pair<Long, Long>> raidSet) {
//        // open the connection to the database
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//
//        // create content object for storing data
//        ContentValues cv = new ContentValues();
//
//        // assign values to the table fields
//        cv.put(cSQLDBHelper.KEY_ID, outcomeModel.getOutcomeID());
//        cv.put(cSQLDBHelper.KEY_PARENT_FK_ID, outcomeModel.getParentID());
//        cv.put(cSQLDBHelper.KEY_IMPACT_FK_ID, outcomeModel.getImpactID());
//        cv.put(cSQLDBHelper.KEY_LOGFRAME_FK_ID, outcomeModel.getLogFrameID());
//        cv.put(cSQLDBHelper.KEY_NAME, outcomeModel.getName());
//        cv.put(cSQLDBHelper.KEY_DESCRIPTION, outcomeModel.getDescription());
//
//        // insert project details
//        try {
//            if (db.insert(cSQLDBHelper.TABLE_tblOUTCOME, null, cv) < 0) {
//                return false;
//            }
//
//            // add sub-logFrame, outcome and impact tuple
//            for (Map.Entry<Set<Pair<Long, Long>>, Pair<Long, Long>> soiEntry :
//                    soiMap.entrySet()) {
//                for (Pair<Long, Long> oiPair : soiEntry.getKey()) {
//                    long outcomeID = oiPair.first;
//                    long impactID = oiPair.second;
//
//                    Pair<Long, Long> sPair = soiEntry.getValue();
//                    long parentID = sPair.first;
//                    long childID = sPair.second;
//
//                    if (!addOutcomeImpact(parentID, childID, outcomeID, impactID))
//                        return false;
//                }
//            }
//
//
//            // add outcome, question and criteria tuple
//            for (Map.Entry<Long, Set<Pair<Long, Long>>> oqcEntry : oqcMap.entrySet()) {
//                long impactID = oqcEntry.getKey();
//                for (Pair<Long, Long> qcPair : oqcEntry.getValue()) {
//                    long questionID = qcPair.first;
//                    long criteriaID = qcPair.second;
//
//                    if (!addOutcomeQuestionCriteria(impactID, questionID, criteriaID))
//                        return false;
//                }
//            }
//
//            // add outcome raid
//            for (Pair<Long, Long> pair : raidSet) {
//                long raidID = pair.first;
//                long outcomeID = pair.second;
//                if (!addOutcomeRaid(outcomeID, raidID))
//                    return false;
//            }
//
//        } catch (Exception e) {
//            Log.d(TAG, "Exception in importing OUTCOME from Excel: " + e.getMessage());
//        }
//
//        // close the database connection
//        db.close();
//
//        return true;
//    }
//
//
//    /**
//     * @param outcomeID  outcome identification
//     * @param questionID question identification
//     * @param criteriaID criteria identification
//     * @return boolean
//     */
//    public boolean addOutcomeQuestionCriteria(long outcomeID, long questionID, long criteriaID) {
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//
//        ContentValues cv = new ContentValues();
//
//        cv.put(cSQLDBHelper.KEY_OUTCOME_FK_ID, outcomeID);
//        cv.put(cSQLDBHelper.KEY_QUESTION_FK_ID, questionID);
//        cv.put(cSQLDBHelper.KEY_EVALUATION_CRITERIA_FK_ID, criteriaID);
//
//        return db.insert(cSQLDBHelper.TABLE_tblOUTCOME_QUESTION, null, cv) >= 0;
//    }
//
//    /**
//     * @param outcomeID outcome identification
//     * @param raidID    raid identification
//     * @return boolean
//     */
//    public boolean addOutcomeRaid(long outcomeID, long raidID) {
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//
//        ContentValues cv = new ContentValues();
//
//        cv.put(cSQLDBHelper.KEY_OUTCOME_FK_ID, outcomeID);
//        cv.put(cSQLDBHelper.KEY_RAID_FK_ID, raidID);
//
//        return db.insert(cSQLDBHelper.TABLE_tblOUTCOME_RAID, null, cv) >= 0;
//    }
//
//    @Override
//    public boolean deleteOutcomeImpacts() {
//        // open the connection to the database
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//
//        // delete all records
//        long result = db.delete(cSQLDBHelper.TABLE_tblOUTCOME_IMPACT, null,
//                null);
//
//        // close the database connection
//        db.close();
//
//        return result > -1;
//    }
//
//    @Override
//    public boolean deleteOutcomeQuestions() {
//        // open the connection to the database
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//
//        // delete all records
//        long result = db.delete(cSQLDBHelper.TABLE_tblOUTCOME_QUESTION, null,
//                null);
//
//        // close the database connection
//        db.close();
//
//        return result > -1;
//    }
//
//    @Override
//    public boolean deleteOutcomeRaids() {
//        // open the connection to the database
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//
//        // delete all records
//        long result = db.delete(cSQLDBHelper.TABLE_tblOUTCOME_RAID, null,
//                null);
//
//        // close the database connection
//        db.close();
//
//        return result > -1;
//    }
//
//    @Override
//    public boolean deleteOutcomes() {
//        // open the connection to the database
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//
//        // delete all records
//        long result = db.delete(cSQLDBHelper.TABLE_tblOUTCOME, null,
//                null);
//
//        // close the database connection
//        db.close();
//
//        return result > -1;
//    }

    /* ######################################## OUTPUT FUNCTIONS ########################################*/

//    /**
//     * This function extracts and adds the output data from excel.
//     *
//     * @return boolean
//     */
//    @Override
//    public boolean addOutputFromExcel() {
//        Workbook workbook = excelHelper.getWorkbookLOGFRAME();
//        Sheet outputSheet = workbook.getSheet(cExcelHelper.SHEET_tblOUTPUT);
//
//        if (outputSheet == null) {
//            return false;
//        }
//
//        for (Row cRow : outputSheet) {
//            //just skip the row if row number is 0
//            if (cRow.getRowNum() == 0) {
//                continue;
//            }
//
//            cOutputModel outputModel = new cOutputModel();
//
//            outputModel.setOutputID((int) cRow.getCell(0,
//                    Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
//            outputModel.setParentID((int) cRow.getCell(1,
//                    Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
//            outputModel.setOutcomeID((int) cRow.getCell(2,
//                    Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
//            outputModel.setLogFrameID((int) cRow.getCell(3,
//                    Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
//            outputModel.setName(cRow.getCell(4,
//                    Row.CREATE_NULL_AS_BLANK).getStringCellValue());
//            outputModel.setDescription(cRow.getCell(5,
//                    Row.CREATE_NULL_AS_BLANK).getStringCellValue());
//
//            /* get questions grouped by criteria of the output */
//            Map<Long, Set<Pair<Long, Long>>> oqcMap = new HashMap<>();
//            Set<Pair<Long, Long>> qcSet = new HashSet<>();
//            long outputID, questionID, criteriaID;
//
//            Sheet oqcSheet = workbook.getSheet(cExcelHelper.SHEET_tblOUTPUT_QUESTION);
//            for (Row rowOQC : oqcSheet) {
//                //just skip the row if row number is 0
//                if (rowOQC.getRowNum() == 0) {
//                    continue;
//                }
//
//                outputID = (int) rowOQC.getCell(0,
//                        Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
//                if (outputModel.getOutputID() == outputID) {
//                    questionID = (int) rowOQC.getCell(1,
//                            Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
//                    criteriaID = (int) rowOQC.getCell(2,
//                            Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
//                    qcSet.add(new Pair<>(questionID, criteriaID));
//                }
//            }
//            oqcMap.put(outputModel.getOutputID(), qcSet);
//
//            /* get outcomes of the output for a sub-logframe */
//            Map<Set<Pair<Long, Long>>, Pair<Long, Long>> sooMap = new HashMap<>();
//            long parentID, childID, outcomeID;
//
//            Sheet ooSheet = workbook.getSheet(cExcelHelper.SHEET_tblOUTPUT_OUTCOME);
//            for (Row rowOO : ooSheet) {
//                //just skip the row if row number is 0
//                if (rowOO.getRowNum() == 0) {
//                    continue;
//                }
//
//                outputID = (int) rowOO.getCell(2, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
//                if (outputModel.getOutputID() == outputID) {
//                    Set<Pair<Long, Long>> impactSet = new HashSet<>();
//                    parentID = (int) rowOO.getCell(0,
//                            Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
//                    childID = (int) rowOO.getCell(1,
//                            Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
//                    outcomeID = (int) rowOO.getCell(3,
//                            Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
//                    impactSet.add(new Pair(outputID, outcomeID));
//                    sooMap.put(impactSet, new Pair<>(parentID, childID));
//                }
//            }
//
//            /* get raids of the output */
//            Set<Pair<Long, Long>> raidSet = new HashSet<>();
//            long raidID;
//            Sheet outputRaidSheet = workbook.getSheet(cExcelHelper.SHEET_tblOUTPUT_RAID);
//            for (Row rowOutputRaid : outputRaidSheet) {
//                //just skip the row if row number is 0
//                if (rowOutputRaid.getRowNum() == 0) {
//                    continue;
//                }
//
//                outputID = (int) rowOutputRaid.getCell(0,
//                        Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
//                if (outputModel.getOutputID() == outputID) {
//                    raidID = (int) rowOutputRaid.getCell(1,
//                            Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
//                    raidSet.add(new Pair<>(outputID, raidID));
//                }
//            }
//
//            //Log.d(TAG, gson.toJson(sooMap));
//            //Log.d(TAG, gson.toJson(oqcMap));
//            //Log.d(TAG, gson.toJson(raidSet));
//
//            if (!addOutputFromExcel(outputModel, sooMap, oqcMap, raidSet)) {
//                return false;
//            }
//        }
//
//        return true;
//    }
//
//    /**
//     * @param outputModel output model
//     * @param sooMap      sub-logframe output outcome
//     * @param oqcMap      output question criteria
//     * @param raidSet     raid set
//     * @return boolean
//     */
//    public boolean addOutputFromExcel(cOutputModel outputModel,
//                                      Map<Set<Pair<Long, Long>>, Pair<Long, Long>> sooMap,
//                                      Map<Long, Set<Pair<Long, Long>>> oqcMap,
//                                      Set<Pair<Long, Long>> raidSet) {
//        // open the connection to the database
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//
//        // create content object for storing data
//        ContentValues cv = new ContentValues();
//
//        // assign values to the table fields
//        cv.put(cSQLDBHelper.KEY_ID, outputModel.getOutputID());
//        cv.put(cSQLDBHelper.KEY_PARENT_FK_ID, outputModel.getParentID());
//        cv.put(cSQLDBHelper.KEY_OUTCOME_FK_ID, outputModel.getOutcomeID());
//        cv.put(cSQLDBHelper.KEY_LOGFRAME_FK_ID, outputModel.getLogFrameID());
//        cv.put(cSQLDBHelper.KEY_NAME, outputModel.getName());
//        cv.put(cSQLDBHelper.KEY_DESCRIPTION, outputModel.getDescription());
//
//        // insert output details
//        try {
//            if (db.insert(cSQLDBHelper.TABLE_tblOUTPUT, null, cv) < 0) {
//                return false;
//            }
//
//            // add sub-logFrame, outcome and impact tuple
//            for (Map.Entry<Set<Pair<Long, Long>>, Pair<Long, Long>> sooEntry :
//                    sooMap.entrySet()) {
//                for (Pair<Long, Long> ooPair : sooEntry.getKey()) {
//                    long outputID = ooPair.first;
//                    long outcomeID = ooPair.second;
//
//                    Pair<Long, Long> sPair = sooEntry.getValue();
//                    long parentID = sPair.first;
//                    long childID = sPair.second;
//
//                    if (!addOutputOutcome(parentID, childID, outputID, outcomeID))
//                        return false;
//                }
//            }
//
//            // add output, question and criteria tuple
//            for (Map.Entry<Long, Set<Pair<Long, Long>>> oqcEntry : oqcMap.entrySet()) {
//                long outputID = oqcEntry.getKey();
//                for (Pair<Long, Long> qcPair : oqcEntry.getValue()) {
//                    long questionID = qcPair.first;
//                    long criteriaID = qcPair.second;
//
//                    if (!addOutputQuestionCriteria(outputID, questionID, criteriaID))
//                        return false;
//                }
//            }
//
//            // add output raid
//            for (Pair<Long, Long> pair : raidSet) {
//                long raidID = pair.first;
//                long outputID = pair.second;
//                if (!addOutputRaid(outputID, raidID))
//                    return false;
//            }
//
//        } catch (Exception e) {
//            Log.d(TAG, "Exception in importing OUTPUT from Excel: " + e.getMessage());
//        }
//
//        // close the database connection
//        db.close();
//
//        return true;
//    }
//
//    /**
//     * @param parentID  parent identification
//     * @param childID   child identification
//     * @param outputID  output identification
//     * @param outcomeID outcome identification
//     * @return boolean
//     */
//    public boolean addOutputOutcome(long parentID, long childID, long outputID, long outcomeID) {
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//
//        ContentValues cv = new ContentValues();
//
//        cv.put(cSQLDBHelper.KEY_PARENT_FK_ID, parentID);
//        cv.put(cSQLDBHelper.KEY_CHILD_FK_ID, childID);
//        cv.put(cSQLDBHelper.KEY_OUTPUT_FK_ID, outputID);
//        cv.put(cSQLDBHelper.KEY_OUTCOME_FK_ID, outcomeID);
//
//        return db.insert(cSQLDBHelper.TABLE_tblOUTPUT_OUTCOME, null, cv) >= 0;
//    }
//
//    /**
//     * @param outputID   output identification
//     * @param questionID question identification
//     * @param criteriaID criteria identification
//     * @return boolean
//     */
//    public boolean addOutputQuestionCriteria(long outputID, long questionID, long criteriaID) {
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//
//        ContentValues cv = new ContentValues();
//
//        cv.put(cSQLDBHelper.KEY_OUTPUT_FK_ID, outputID);
//        cv.put(cSQLDBHelper.KEY_QUESTION_FK_ID, questionID);
//        cv.put(cSQLDBHelper.KEY_EVALUATION_CRITERIA_FK_ID, criteriaID);
//
//        return db.insert(cSQLDBHelper.TABLE_tblOUTPUT_QUESTION, null, cv) >= 0;
//    }
//
//    /**
//     * @param outputID output identification
//     * @param raidID   raid identification
//     * @return boolean
//     */
//    public boolean addOutputRaid(long outputID, long raidID) {
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//
//        ContentValues cv = new ContentValues();
//
//        cv.put(cSQLDBHelper.KEY_OUTPUT_FK_ID, outputID);
//        cv.put(cSQLDBHelper.KEY_RAID_FK_ID, raidID);
//
//        return db.insert(cSQLDBHelper.TABLE_tblOUTPUT_RAID, null, cv) >= 0;
//    }
//
//    @Override
//    public boolean deleteOutputOutcomes() {
//        // open the connection to the database
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//
//        // delete all records
//        long result = db.delete(cSQLDBHelper.TABLE_tblOUTPUT_OUTCOME, null,
//                null);
//
//        // close the database connection
//        db.close();
//
//        return result > -1;
//    }
//
//    @Override
//    public boolean deleteOutputQuestions() {
//        // open the connection to the database
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//
//        // delete all records
//        long result = db.delete(cSQLDBHelper.TABLE_tblOUTPUT_QUESTION, null,
//                null);
//
//        // close the database connection
//        db.close();
//
//        return result > -1;
//    }
//
//    @Override
//    public boolean deleteOutputRaids() {
//        // open the connection to the database
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//
//        // delete all records
//        long result = db.delete(cSQLDBHelper.TABLE_tblOUTPUT_RAID, null,
//                null);
//
//        // close the database connection
//        db.close();
//
//        return result > -1;
//    }
//
//    @Override
//    public boolean deleteOutputs() {
//        // open the connection to the database
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//
//        // delete all records
//        long result = db.delete(cSQLDBHelper.TABLE_tblOUTPUT, null,
//                null);
//
//        // close the database connection
//        db.close();
//
//        return result > -1;
//    }

    /* #################################### WORKPLAN FUNCTIONS ###################################*/

//    /**
//     * This function extracts criteria data from excel and adds it to the database.
//     *
//     * @return boolean
//     */
//    @Override
//    public boolean addWorkplanFromExcel() {
//        Workbook workbook = excelHelper.getWorkbookLOGFRAME();
//        Sheet WPSheet = workbook.getSheet(cExcelHelper.SHEET_tblWORKPLAN);
//
//        if (WPSheet == null) {
//            return false;
//        }
//
//        for (Row cRow : WPSheet) {
//            //just skip the row if row number is 0
//            if (cRow.getRowNum() == 0) {
//                continue;
//            }
//
//            cWorkplanModel workplanModel = new cWorkplanModel();
//
//            workplanModel.setWorkplanID((int)
//                    cRow.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
//            workplanModel.setLogFrameID((int)
//                    cRow.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
//            workplanModel.setName(
//                    cRow.getCell(2, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
//            workplanModel.setDescription(
//                    cRow.getCell(3, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
//
//            if (!addWorkplan(workplanModel)) {
//                return false;
//            }
//        }
//
//        return true;
//    }
//
//    /**
//     * This function adds criteria data to the database.
//     *
//     * @param workplanModel workplan model
//     * @return boolean
//     */
//    public boolean addWorkplan(cWorkplanModel workplanModel) {
//        // open the connection to the database
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//
//        // create content object for storing data
//        ContentValues cv = new ContentValues();
//
//        // assign values to the table fields
//        cv.put(cSQLDBHelper.KEY_ID, workplanModel.getWorkplanID());
//        cv.put(cSQLDBHelper.KEY_LOGFRAME_FK_ID, workplanModel.getLogFrameID());
//        cv.put(cSQLDBHelper.KEY_NAME, workplanModel.getName());
//        cv.put(cSQLDBHelper.KEY_DESCRIPTION, workplanModel.getDescription());
//
//        // insert project details
//        try {
//            if (db.insert(cSQLDBHelper.TABLE_tblWORKPLAN, null, cv) < 0) {
//                return false;
//            }
//        } catch (Exception e) {
//            Log.d(TAG, "Exception in importing WORKPLAN from Excel: " + e.getMessage());
//        }
//
//        // close the database connection
//        db.close();
//
//        return true;
//    }
//
//    @Override
//    public boolean deleteWorkPlans() {
//        // open the connection to the database
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//
//        // delete all records
//        long result = db.delete(cSQLDBHelper.TABLE_tblWORKPLAN, null,
//                null);
//
//        // close the database connection
//        db.close();
//
//        return result > -1;
//    }

    /* ###################################### ACTIVITY FUNCTIONS ######################################*/


//    /**
//     * This function extracts and adds the activity data from excel.
//     *
//     * @return boolean
//     */
//    @Override
//    public boolean addActivityFromExcel() {
//        Gson gson = new Gson();
//        Workbook workbook = excelHelper.getWorkbookLOGFRAME();
//        Sheet ASheet = workbook.getSheet(cExcelHelper.SHEET_tblACTIVITY);
//
//        if (ASheet == null) {
//            return false;
//        }
//
//        for (Row rowActivity : ASheet) {
//
//            //just skip the row if row number is 0
//            if (rowActivity.getRowNum() == 0) {
//                continue;
//            }
//            cActivityModel activityModel = new cActivityModel();
//
//            activityModel.setWorkplanID((int)
//                    rowActivity.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
//            activityModel.setParentID((int)
//                    rowActivity.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
//            activityModel.setOutputID((int)
//                    rowActivity.getCell(2, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
//
//            /* get preceding activities of the activity*/
//            Set<Pair<Long, Long>> precedingSet = new HashSet<>();
//            long precedingActivityID, succeedingActivityID;
//            Sheet precedingActivitySheet = workbook.getSheet(cExcelHelper.SHEET_tblPRECEDINGACTIVITY);
//            for (Row rowPrecedingActivity : precedingActivitySheet) {
//                //just skip the row if row number is 0
//                if (rowPrecedingActivity.getRowNum() == 0) {
//                    continue;
//                }
//
//                precedingActivityID = (int) rowPrecedingActivity.getCell(0,
//                        Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
//                if (activityModel.getWorkplanID() == precedingActivityID) {
//                    succeedingActivityID = (int) rowPrecedingActivity.getCell(1,
//                            Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
//                    precedingSet.add(new Pair<>(precedingActivityID, succeedingActivityID));
//                }
//            }
//
//            /* get activity assignment */
//            Map<Long, Set<Pair<Long, Long>>> asaMap = new HashMap<>();
//            Set<Pair<Long, Long>> asSet = new HashSet<>();
//            long assignmentID, staffID, assignedActivityID;
//
//            Sheet aqaSheet = workbook.getSheet(cExcelHelper.SHEET_tblACTIVITYASSIGNMENT);
//            for (Row rowAQA : aqaSheet) {
//                //just skip the row if row number is 0
//                if (rowAQA.getRowNum() == 0) {
//                    continue;
//                }
//
//                assignedActivityID = (int) rowAQA.getCell(2,
//                        Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
//                if (activityModel.getWorkplanID() == assignedActivityID) {
//                    staffID = (int) rowAQA.getCell(1,
//                            Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
//                    assignmentID = (int) rowAQA.getCell(0,
//                            Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
//                    asSet.add(new Pair<>(assignmentID, staffID));
//                }
//            }
//            asaMap.put(activityModel.getWorkplanID(), asSet);
//
//
//            /* get questions grouped by criteria of the activity */
//            Map<Long, Set<Pair<Long, Long>>> aqcMap = new HashMap<>();
//            Set<Pair<Long, Long>> qcSet = new HashSet<>();
//            long activityID, questionID, criteriaID;
//
//            Sheet aqcSheet = workbook.getSheet(cExcelHelper.SHEET_tblACTIVITY_QUESTION);
//            for (Row rowAQC : aqcSheet) {
//                //just skip the row if row number is 0
//                if (rowAQC.getRowNum() == 0) {
//                    continue;
//                }
//
//                activityID = (int) rowAQC.getCell(0,
//                        Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
//                if (activityModel.getWorkplanID() == activityID) {
//                    questionID = (int) rowAQC.getCell(1,
//                            Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
//                    criteriaID = (int) rowAQC.getCell(2,
//                            Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
//                    qcSet.add(new Pair<>(questionID, criteriaID));
//                }
//            }
//            aqcMap.put(activityModel.getOutputID(), qcSet);
//
//            /* get outputs of the activity for a sub-logframe */
//            Map<Set<Pair<Long, Long>>, Pair<Long, Long>> saoMap = new HashMap<>();
//            long parentID, childID, outputID;
//
//            Sheet aoSheet = workbook.getSheet(cExcelHelper.SHEET_tblACTIVITY_OUTPUT);
//            for (Row rowAO : aoSheet) {
//                //just skip the row if row number is 0
//                if (rowAO.getRowNum() == 0) {
//                    continue;
//                }
//
//                activityID = (int) rowAO.getCell(2,
//                        Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
//                if (activityModel.getWorkplanID() == activityID) {
//                    Set<Pair<Long, Long>> AOSet = new HashSet<>();
//                    parentID = (int) rowAO.getCell(0,
//                            Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
//                    childID = (int) rowAO.getCell(1,
//                            Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
//                    outputID = (int) rowAO.getCell(3,
//                            Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
//                    AOSet.add(new Pair(activityID, outputID));
//                    saoMap.put(AOSet, new Pair<>(parentID, childID));
//                }
//            }
//
//            /* get raids of the activity */
//            Set<Pair<Long, Long>> raidSet = new HashSet<>();
//            long raidID;
//            Sheet activityRaidSheet = workbook.getSheet(cExcelHelper.SHEET_tblACTIVITY_RAID);
//            for (Row rowActivityRaid : activityRaidSheet) {
//                //just skip the row if row number is 0
//                if (rowActivityRaid.getRowNum() == 0) {
//                    continue;
//                }
//
//                activityID = (int) rowActivityRaid.getCell(0,
//                        Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
//                if (activityModel.getWorkplanID() == activityID) {
//                    raidID = (int) rowActivityRaid.getCell(1,
//                            Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
//                    raidSet.add(new Pair<>(activityID, raidID));
//                }
//            }
//
//            //Log.d(TAG, gson.toJson(activityModel));
//            //Log.d(TAG, gson.toJson(precedingSet));
//            //Log.d(TAG, gson.toJson(asaMap));
//            //Log.d(TAG, gson.toJson(aqcMap));
//            //Log.d(TAG, gson.toJson(saoMap));
//            //Log.d(TAG, gson.toJson(raidSet));
//
//            if (!addActivity(activityModel, precedingSet, asaMap, saoMap, aqcMap, raidSet)) {
//                return false;
//            }
//        }
//
//        return true;
//    }

    /* get activities of the activity planning*/
    //Set<Pair<Integer, Integer>> planningSet = new HashSet<>();
/*
            int activityID;
            Sheet activitySheet = workbook.getSheet(cExcelHelper.SHEET_tblACTIVITY);
            for (Iterator<Row> ritActivity = activitySheet.iterator(); ritActivity.hasNext(); ) {
                Row rowActivity = ritActivity.next();

                //just skip the row if row number is 0
                if (rowActivity.getRowNum() == 0) {
                    continue;
                }

                activityID = (int) rowActivity.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                if (activityPlanningModel.getActivityPlanningID() == activityID) {
                    activityModel.setActivityID((int)
                            rowActivity.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
                    activityModel.setParentID ((int)
                            rowActivity.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
                    activityModel.setOutputID ((int)
                            rowActivity.getCell(2, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
                    //activityModel.setLogFrameID((int)
                    //        rowActivity.getCell(3, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
                    break;
                }
            }
        }

        return true;
    }*/

//    /**
//     * @param activityModel activity model
//     * @param precedingSet  preceding set
//     * @param saoMap        sub-activity output
//     * @param aqcMap        activity question criteria
//     * @param raidSet       raid set
//     * @return boolean
//     */
//    public boolean addActivity(cActivityModel activityModel,
//                               Set<Pair<Long, Long>> precedingSet,
//                               Map<Long, Set<Pair<Long, Long>>> asaMap,
//                               Map<Set<Pair<Long, Long>>, Pair<Long, Long>> saoMap,
//                               Map<Long, Set<Pair<Long, Long>>> aqcMap,
//                               Set<Pair<Long, Long>> raidSet) {
//        // open the connection to the database
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//
//        // create content object for storing data
//        ContentValues cv = new ContentValues();
//
//        // assign values to the table fields
//        cv.put(cSQLDBHelper.KEY_WORKPLAN_FK_ID, activityModel.getWorkplanID());
//        cv.put(cSQLDBHelper.KEY_PARENT_FK_ID, activityModel.getParentID());
//        cv.put(cSQLDBHelper.KEY_OUTPUT_FK_ID, activityModel.getOutputID());
//
//        // insert activity details
//        try {
//
//            if (db.insert(cSQLDBHelper.TABLE_tblACTIVITY, null, cv) < 0) {
//                return false;
//            }
//
//            // add preceding activity
//            for (Pair<Long, Long> pair : precedingSet) {
//                long succeedingID = pair.first;
//                long precedingID = pair.second;
//                if (!addPrecedingActivity(succeedingID, precedingID))
//                    return false;
//            }
//
//            // add assignment, staff and activity tuple
//            for (Map.Entry<Long, Set<Pair<Long, Long>>> asaEntry : asaMap.entrySet()) {
//                long activityID = asaEntry.getKey();
//                for (Pair<Long, Long> saPair : asaEntry.getValue()) {
//                    long assignmentID = saPair.first;
//                    long staffID = saPair.second;
//
//                    if (!addActivityAssignment(assignmentID, staffID, activityID))
//                        return false;
//                }
//            }
//
//            // add sub-logFrame, activity and output tuple
//            for (Map.Entry<Set<Pair<Long, Long>>, Pair<Long, Long>> saoEntry : saoMap.entrySet()) {
//                for (Pair<Long, Long> aoPair : saoEntry.getKey()) {
//                    long activityID = aoPair.first;
//                    long outputID = aoPair.second;
//
//                    Pair<Long, Long> sPair = saoEntry.getValue();
//                    long parentID = sPair.first;
//                    long childID = sPair.second;
//
//                    if (!addActivityOutput(parentID, childID, activityID, outputID))
//                        return false;
//                }
//            }
//
//            // add activity, question and criteria tuple
//            for (Map.Entry<Long, Set<Pair<Long, Long>>> aqcEntry : aqcMap.entrySet()) {
//                long activityID = aqcEntry.getKey();
//                for (Pair<Long, Long> qcPair : aqcEntry.getValue()) {
//                    long questionID = qcPair.first;
//                    long criteriaID = qcPair.second;
//
//                    if (!addActivityQuestionCriteria(activityID, questionID, criteriaID))
//                        return false;
//                }
//            }
//
//            // add activity raid
//            for (Pair<Long, Long> pair : raidSet) {
//                long raidID = pair.first;
//                long activityID = pair.second;
//                if (!addActivityRaid(activityID, raidID))
//                    return false;
//            }
//        } catch (Exception e) {
//            Log.d(TAG, "Exception in importing ACTIVITY from Excel: " + e.getMessage());
//        }
//
//        // close the database connection
//        db.close();
//
//        return true;
//    }
//
//    /**
//     * @param activityID  activity identification
//     * @param precedingID preceding identification
//     * @return boolean
//     */
//    public boolean addPrecedingActivity(long activityID, long precedingID) {
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//
//        ContentValues cv = new ContentValues();
//
//        cv.put(cSQLDBHelper.KEY_WORKPLAN_FK_ID, activityID);
//        cv.put(cSQLDBHelper.KEY_PRECEDING_ACTIVITY_FK_ID, precedingID);
//
//        return db.insert(cSQLDBHelper.TABLE_tblPRECEDINGACTIVITY, null, cv) >= 0;
//    }
//
//    /**
//     * @param assignmentID assignment identification
//     * @param staffID      staff identification
//     * @param activityID   activity identification
//     * @return boolean
//     */
//    public boolean addActivityAssignment(long assignmentID, long staffID, long activityID) {
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//
//        ContentValues cv = new ContentValues();
//
//        cv.put(cSQLDBHelper.KEY_ID, assignmentID);
//        cv.put(cSQLDBHelper.KEY_STAFF_FK_ID, staffID);
//        cv.put(cSQLDBHelper.KEY_WORKPLAN_FK_ID, activityID);
//
//        if (db.insert(cSQLDBHelper.TABLE_tblACTIVITYASSIGNMENT, null, cv) < 0) {
//            return false;
//        }
//
//        return true;
//    }
//
//    /**
//     * @param parentID   parent identification
//     * @param childID    child identification
//     * @param activityID activity identification
//     * @param outputID   output identification
//     * @return boolean
//     */
//    public boolean addActivityOutput(long parentID, long childID, long activityID, long outputID) {
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//
//        ContentValues cv = new ContentValues();
//
//        cv.put(cSQLDBHelper.KEY_PARENT_FK_ID, parentID);
//        cv.put(cSQLDBHelper.KEY_CHILD_FK_ID, childID);
//        cv.put(cSQLDBHelper.KEY_WORKPLAN_FK_ID, activityID);
//        cv.put(cSQLDBHelper.KEY_OUTPUT_FK_ID, outputID);
//
//        return db.insert(cSQLDBHelper.TABLE_tblACTIVITY_OUTPUT, null, cv) >= 0;
//    }
//
//    /**
//     * @param activityID activity identification
//     * @param questionID question identification
//     * @param criteriaID criteria identification
//     * @return boolean
//     */
//    public boolean addActivityQuestionCriteria(long activityID, long questionID, long criteriaID) {
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//
//        ContentValues cv = new ContentValues();
//
//        cv.put(cSQLDBHelper.KEY_WORKPLAN_FK_ID, activityID);
//        cv.put(cSQLDBHelper.KEY_QUESTION_FK_ID, questionID);
//        cv.put(cSQLDBHelper.KEY_EVALUATION_CRITERIA_FK_ID, criteriaID);
//
//        return db.insert(cSQLDBHelper.TABLE_tblACTIVITY_QUESTION, null, cv) >= 0;
//    }
//
//    /**
//     * @param activityID activity identification
//     * @param raidID     raid identification
//     * @return boolean
//     */
//    public boolean addActivityRaid(long activityID, long raidID) {
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//
//        ContentValues cv = new ContentValues();
//
//        cv.put(cSQLDBHelper.KEY_WORKPLAN_FK_ID, activityID);
//        cv.put(cSQLDBHelper.KEY_RAID_FK_ID, raidID);
//
//        return db.insert(cSQLDBHelper.TABLE_tblACTIVITY_RAID, null, cv) >= 0;
//    }
//
//    @Override
//    public boolean deletePrecedingActivities() {
//        // open the connection to the database
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//
//        // delete all records
//        long result = db.delete(cSQLDBHelper.TABLE_tblPRECEDINGACTIVITY, null,
//                null);
//
//        // close the database connection
//        db.close();
//
//        return result > -1;
//    }
//
//    @Override
//    public boolean deleteActivityAssignments() {
//        // open the connection to the database
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//
//        // delete all records
//        long result = db.delete(cSQLDBHelper.TABLE_tblACTIVITYASSIGNMENT, null,
//                null);
//
//        // close the database connection
//        db.close();
//
//        return result > -1;
//    }
//
//    @Override
//    public boolean deleteActivityOutputs() {
//        // open the connection to the database
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//
//        // delete all records
//        long result = db.delete(cSQLDBHelper.TABLE_tblACTIVITY_OUTPUT, null, null);
//
//        // close the database connection
//        db.close();
//
//        return result > -1;
//    }
//
//    @Override
//    public boolean deleteActivityQuestions() {
//        // open the connection to the database
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//
//        // delete all records
//        long result = db.delete(cSQLDBHelper.TABLE_tblACTIVITY_QUESTION, null, null);
//
//        // close the database connection
//        db.close();
//
//        return result > -1;
//    }
//
//    @Override
//    public boolean deleteActivityRaids() {
//        // open the connection to the database
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//
//        // delete all records
//        long result = db.delete(cSQLDBHelper.TABLE_tblACTIVITY_RAID, null, null);
//
//        // close the database connection
//        db.close();
//
//        return result > -1;
//    }
//
//    public boolean deleteActivities() {
//        // open the connection to the database
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//
//        // delete all records
//        long result = db.delete(cSQLDBHelper.TABLE_tblACTIVITY, null, null);
//
//        // close the database connection
//        db.close();
//
//        return result > -1;
//    }





    /*#################################### RESOURCE FUNCTIONS ####################################*/

//    /**
//     * This function extracts resource type data from excel and adds it to the database.
//     *
//     * @return boolean
//     */
//    @Override
//    public boolean addResourceFromExcel() {
//        Workbook workbook = excelHelper.getWorkbookLOGFRAME();
//        Sheet RSheet = workbook.getSheet(cExcelHelper.SHEET_tblRESOURCE);
//
//        if (RSheet == null) {
//            return false;
//        }
//
//        for (Row cRow : RSheet) {
//            //just skip the row if row number is 0
//            if (cRow.getRowNum() == 0) {
//                continue;
//            }
//
//            cResourceModel resourceModel = new cResourceModel();
//
//            resourceModel.setResourceID((int)
//                    cRow.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
//            resourceModel.setResourceTypeID((int)
//                    cRow.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
//            resourceModel.setName(
//                    cRow.getCell(2, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
//            resourceModel.setDescription(
//                    cRow.getCell(3, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
//
//            if (!addResource(resourceModel)) {
//                return false;
//            }
//        }
//
//        return true;
//    }
//
//    /**
//     * This function adds resource data to the database.
//     *
//     * @param resourceModel resource model
//     * @return boolean
//     */
//    public boolean addResource(cResourceModel resourceModel) {
//        // open the connection to the database
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//
//        // create content object for storing data
//        ContentValues cv = new ContentValues();
//
//        // assign values to the table fields
//        cv.put(cSQLDBHelper.KEY_ID, resourceModel.getResourceID());
//        cv.put(cSQLDBHelper.KEY_RESOURCE_TYPE_FK_ID, resourceModel.getResourceTypeID());
//        cv.put(cSQLDBHelper.KEY_NAME, resourceModel.getName());
//        cv.put(cSQLDBHelper.KEY_DESCRIPTION, resourceModel.getDescription());
//
//        // insert project details
//        try {
//            if (db.insert(cSQLDBHelper.TABLE_tblRESOURCE, null, cv) < 0) {
//                return false;
//            }
//        } catch (Exception e) {
//            Log.d(TAG, "Exception in importing RESOURCE from Excel: " + e.getMessage());
//        }
//
//        // close the database connection
//        db.close();
//
//        return true;
//    }
//
//    @Override
//    public boolean deleteResources() {
//        // open the connection to the database
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//
//        // delete all records
//        long result = db.delete(cSQLDBHelper.TABLE_tblRESOURCE, null, null);
//
//        // close the database connection
//        db.close();
//
//        return result > -1;
//    }

    /*###################################### INPUT FUNCTIONS #####################################*/

//    /**
//     * This function extracts and adds the input data from excel.
//     *
//     * @return boolean
//     */
//    @Override
//    public boolean addInputFromExcel() {
//        Workbook logFrameWorkbook = excelHelper.getWorkbookLOGFRAME();
//        Workbook awpbWorkbook = excelHelper.getWorkbookAWPB();
//
//        Sheet inputSheet = logFrameWorkbook.getSheet(cExcelHelper.SHEET_tblINPUT);
//        Sheet iqcSheet = logFrameWorkbook.getSheet(cExcelHelper.SHEET_tblINPUT_QUESTION);
//        Sheet iaSheet = logFrameWorkbook.getSheet(cExcelHelper.SHEET_tblINPUT_ACTIVITY);
//        Sheet humanSheet = awpbWorkbook.getSheet(cExcelHelper.SHEET_tblHUMAN);
//        Sheet humanSetSheet = awpbWorkbook.getSheet(cExcelHelper.SHEET_tblHUMANSET);
//        Sheet materialSheet = awpbWorkbook.getSheet(cExcelHelper.SHEET_tblMATERIAL);
//        Sheet incomeSheet = awpbWorkbook.getSheet(cExcelHelper.SHEET_tblINCOME);
//        Sheet fundSheet = awpbWorkbook.getSheet(cExcelHelper.SHEET_tblFUND);
//        Sheet expenseSheet = awpbWorkbook.getSheet(cExcelHelper.SHEET_tblEXPENSE);
//
//        if (inputSheet == null) {
//            return false;
//        }
//
//        for (Row cRow : inputSheet) {
//            //just skip the row if row number is 0
//            if (cRow.getRowNum() == 0) {
//                continue;
//            }
//
//            cInputModel inputModel = new cInputModel();
//
//            inputModel.setInputID((int) cRow.getCell(0,
//                    Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
//            inputModel.setWorkplanID((int) cRow.getCell(1,
//                    Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
//            inputModel.setLogFrameID((int) cRow.getCell(2,
//                    Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
//            inputModel.setResourceID((int) cRow.getCell(3,
//                    Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
//
//            /* get questions grouped by criteria of the input */
//            Map<Long, Set<Pair<Long, Long>>> iqcMap = new HashMap<>();
//            Set<Pair<Long, Long>> qcSet = new HashSet<>();
//            long inputID, questionID, criteriaID;
//
//            for (Row rowIQC : iqcSheet) {
//                //just skip the row if row number is 0
//                if (rowIQC.getRowNum() == 0) {
//                    continue;
//                }
//                inputID = (long) rowIQC.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
//                if (inputModel.getInputID() == inputID) {
//                    questionID = (int) rowIQC.getCell(1,
//                            Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
//                    criteriaID = (int) rowIQC.getCell(2,
//                            Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
//                    qcSet.add(new Pair<>(questionID, criteriaID));
//                    iqcMap.put(inputModel.getInputID(), qcSet);
//                }
//            }
//
//            /* get activities of the input for a sub-logframe */
//            Map<Set<Pair<Long, Long>>, Pair<Long, Long>> siaMap = new HashMap<>();
//            long parentID, childID, activityID;
//
//            for (Row rowOI : iaSheet) {
//                //just skip the row if row number is 0
//                if (rowOI.getRowNum() == 0) {
//                    continue;
//                }
//
//                inputID = (int) rowOI.getCell(2, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
//                if (inputModel.getInputID() == inputID) {
//                    Set<Pair<Long, Long>> activitySet = new HashSet<>();
//                    parentID = (int) rowOI.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
//                    childID = (int) rowOI.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
//                    activityID = (int) rowOI.getCell(3, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
//                    activitySet.add(new Pair(inputID, activityID));
//                    siaMap.put(activitySet, new Pair<>(parentID, childID));
//                }
//            }
//
//            /* human input */
//            int humanID = -1, humanQuantity = 0;
//            /* human set input */
//            Map<Long, Set<Pair<Long, Long>>> hiuMap = new HashMap<>();
//            Set<Pair<Long, Long>> huSet = new HashSet<>();
//
//            /* material input */
//            int materialID = -1, materialQuantity = 0;
//
//            /* expense inputs */
//            int expenseID = -1;
//            double expense = 0.0;
//
//            /* income inputs */
//            int incomeID = -1, funderID = -1;
//            double fund = 0.0;
//
//            for (Row rowHuman : humanSheet) {
//                //just skip the row if row number is 0
//                if (rowHuman.getRowNum() == 0) {
//                    continue;
//                }
//
//                humanID = (int) rowHuman.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
//                if (inputModel.getInputID() == humanID) {
//                    humanQuantity = (int) rowHuman.getCell(1,
//                            Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
//
//                    /* human set input */
//                    long humanSetInputID = -1, humanSetID = -1, userID = -1;
//
//                    for (Row rowHumanSet : humanSetSheet) {
//                        //just skip the row if row number is 0
//                        if (rowHumanSet.getRowNum() == 0) {
//                            continue;
//                        }
//
//                        humanSetInputID = (int) rowHumanSet.getCell(1,
//                                Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
//                        if (inputModel.getInputID() == humanSetInputID) {
//                            humanSetID = (int) rowHumanSet.getCell(0,
//                                    Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
//                            userID = (int) rowHumanSet.getCell(2,
//                                    Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
//                            huSet.add(new Pair<>(humanSetID, userID));
//                        }
//                    }
//
//                    break;
//                } else {
//                    humanID = -1;
//                }
//            }
//
//            hiuMap.put(inputModel.getInputID(), huSet);
//
//            /* material input */
//            for (Row rowMaterial : materialSheet) {
//                //just skip the row if row number is 0
//                if (rowMaterial.getRowNum() == 0) {
//                    continue;
//                }
//
//                materialID = (int) rowMaterial.getCell(0,
//                        Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
//                if (inputModel.getInputID() == materialID) {
//                    materialQuantity = (int) rowMaterial.getCell(1,
//                            Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
//                    break;
//                } else {
//                    materialID = -1;
//                }
//            }
//
//            /* income input */
//            Map<Long, Set<Pair<Long, Double>>> ffaMap = new HashMap<>();
//            Set<Pair<Long, Double>> fundSet = new HashSet<>();
//            long fundInputID, fundID = -1;
//            double amount;
//            for (Row rowIncome : incomeSheet) {
//                //just skip the row if row number is 0
//                if (rowIncome.getRowNum() == 0) {
//                    continue;
//                }
//
//                incomeID = (int) rowIncome.getCell(0,
//                        Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
//
//                if (inputModel.getInputID() == incomeID) {
//
//                    for (Row rowFundSet : fundSheet) {
//                        //just skip the row if row number is 0
//                        if (rowFundSet.getRowNum() == 0) {
//                            continue;
//                        }
//
//                        fundInputID = (int) rowFundSet.getCell(1,
//                                Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
//                        if (inputModel.getInputID() == fundInputID) {
//                            fundID = (int) rowFundSet.getCell(0,
//                                    Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
//                            funderID = (int) rowFundSet.getCell(2,
//                                    Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
//                            fund = (double) rowFundSet.getCell(3,
//                                    Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
//
//                            fundSet.add(new Pair<Long, Double>((long) funderID, fund));
//                        }
//                    }
//                    ffaMap.put(fundID, fundSet);
//
//                    break;
//                } else {
//                    incomeID = -1;
//                }
//            }
//
//            /* expense input */
//            for (Row rowExpense : expenseSheet) {
//                //just skip the row if row number is 0
//                if (rowExpense.getRowNum() == 0) {
//                    continue;
//                }
//
//                expenseID = (int) rowExpense.getCell(0,
//                        Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
//                if (inputModel.getInputID() == expenseID) {
//                    expense = (int) rowExpense.getCell(1,
//                            Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
//                    break;
//                } else {
//                    expenseID = -1;
//                }
//            }
//
//            if (!addInput(inputModel,
//                    iqcMap, siaMap,
//                    humanID, humanQuantity, hiuMap,
//                    materialID, materialQuantity,
//                    incomeID, ffaMap,
//                    expenseID, expense)) {
//                return false;
//            }
//        }
//
//        return true;
//    }
//
//    /**
//     * @param inputModel input model
//     * @param iqcMap     input question criteria
//     * @param siaMap     sub-logframe input activity
//     * @return boolean
//     */
//    private boolean addInput(cInputModel inputModel,
//                             Map<Long, Set<Pair<Long, Long>>> iqcMap,
//                             Map<Set<Pair<Long, Long>>, Pair<Long, Long>> siaMap,
//                             long humanID, int humanQuantity,
//                             Map<Long, Set<Pair<Long, Long>>> hiuMap,
//                             long materialID, int materialQuantity,
//                             long incomeID, Map<Long, Set<Pair<Long, Double>>> ffaMap,
//                             long expenseID, double expense) {
//
//        // open the connection to the database
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//
//        // create content object for storing data
//        ContentValues cv = new ContentValues();
//
//        // assign values to the table fields
//        cv.put(cSQLDBHelper.KEY_ID, inputModel.getInputID());
//        cv.put(cSQLDBHelper.KEY_WORKPLAN_FK_ID, inputModel.getWorkplanID());
//        cv.put(cSQLDBHelper.KEY_LOGFRAME_FK_ID, inputModel.getLogFrameID());
//        cv.put(cSQLDBHelper.KEY_RESOURCE_FK_ID, inputModel.getResourceID());
//
//        // insert project details
//        try {
//            if (db.insert(cSQLDBHelper.TABLE_tblINPUT, null, cv) < 0) {
//                return false;
//            }
//
//            // add activity, question and criteria tuple
//            for (Map.Entry<Long, Set<Pair<Long, Long>>> iqcEntry : iqcMap.entrySet()) {
//                long inputID = iqcEntry.getKey();
//                for (Pair<Long, Long> qcPair : iqcEntry.getValue()) {
//                    long questionID = qcPair.first;
//                    long criteriaID = qcPair.second;
//
//                    if (!addInputQuestionCriteria(inputID, questionID, criteriaID))
//                        return false;
//                }
//            }
//
//            // add sub-logFrame, activity and output tuple
//            for (Map.Entry<Set<Pair<Long, Long>>, Pair<Long, Long>> siaEntry :
//                    siaMap.entrySet()) {
//                for (Pair<Long, Long> iaPair : siaEntry.getKey()) {
//                    long inputID = iaPair.first;
//                    long activityID = iaPair.second;
//
//                    Pair<Long, Long> sPair = siaEntry.getValue();
//                    long parentID = sPair.first;
//                    long childID = sPair.second;
//
//                    if (!addInputActivity(parentID, childID, inputID, activityID))
//                        return false;
//                }
//            }
//
//            if (humanID != -1) {
//                return addHumanInput(inputModel.getInputID(), humanQuantity, hiuMap);
//            }
//
//            if (materialID != -1) {
//                return addMaterialInput(inputModel.getInputID(), materialQuantity);
//            }
//
//            if (incomeID != -1) {
//                return addIncomeInput(inputModel.getInputID(), ffaMap);
//            }
//
//            if (expenseID != -1) {
//                return addExpenseInput(inputModel.getInputID(), expense);
//            }
//
//        } catch (Exception e) {
//            Log.d(TAG, "Exception in importing INPUT from Excel: " + e.getMessage());
//        }
//
//        // close the database connection
//        db.close();
//
//        return true;
//    }
//
//    /**
//     * @param inputID    input identification
//     * @param questionID question identification
//     * @param criteriaID criteria identification
//     * @return boolean
//     */
//    private boolean addInputQuestionCriteria(long inputID, long questionID, long criteriaID) {
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//
//        ContentValues cv = new ContentValues();
//
//        cv.put(cSQLDBHelper.KEY_INPUT_FK_ID, inputID);
//        cv.put(cSQLDBHelper.KEY_QUESTION_FK_ID, questionID);
//        cv.put(cSQLDBHelper.KEY_EVALUATION_CRITERIA_FK_ID, criteriaID);
//
//        return db.insert(cSQLDBHelper.TABLE_tblINPUT_QUESTION, null, cv) >= 0;
//    }
//
//    private boolean addInputActivity(long parentID, long childID, long inputID, long activityID) {
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//
//        ContentValues cv = new ContentValues();
//
//        cv.put(cSQLDBHelper.KEY_PARENT_FK_ID, parentID);
//        cv.put(cSQLDBHelper.KEY_CHILD_FK_ID, childID);
//        cv.put(cSQLDBHelper.KEY_INPUT_FK_ID, inputID);
//        cv.put(cSQLDBHelper.KEY_WORKPLAN_FK_ID, activityID);
//
//        return db.insert(cSQLDBHelper.TABLE_tblINPUT_ACTIVITY, null, cv) >= 0;
//    }
//
//    private boolean addHumanInput(long inputID, int humanQuantity, Map<Long,
//            Set<Pair<Long, Long>>> hiuMap) {
//        // open the connection to the database
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//
//        // create content object for storing data
//        ContentValues cv = new ContentValues();
//
//        // assign values to the table fields
//        cv.put(cSQLDBHelper.KEY_INPUT_FK_ID, inputID);
//        cv.put(cSQLDBHelper.KEY_QUANTITY, humanQuantity);
//
//        // insert evaluation type details
//        try {
//            if (db.insert(cSQLDBHelper.TABLE_tblHUMAN, null, cv) < 0) {
//                return false;
//            }
//
//            // add activity, question and criteria tuple
//            for (Map.Entry<Long, Set<Pair<Long, Long>>> hiuEntry : hiuMap.entrySet()) {
//                long hsInputID = hiuEntry.getKey();
//                for (Pair<Long, Long> huPair : hiuEntry.getValue()) {
//                    long humanSetID = huPair.first;
//                    long userID = huPair.second;
//
//                    if (!addHumanSet(humanSetID, hsInputID, userID))
//                        return false;
//                }
//            }
//
//        } catch (Exception e) {
//            Log.d(TAG, "Exception in importing HUMAN INPUT from Excel: " + e.getMessage());
//        }
//
//        // close the database connection
//        db.close();
//
//        return true;
//    }
//
//    private boolean addHumanSet(long humanSetID, long hsInputID, long userID) {
//        // open the connection to the database
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//
//        // create content object for storing data
//        ContentValues cv = new ContentValues();
//
//        // assign values to the table fields
//        cv.put(cSQLDBHelper.KEY_ID, humanSetID);
//        cv.put(cSQLDBHelper.KEY_INPUT_FK_ID, hsInputID);
//        cv.put(cSQLDBHelper.KEY_USER_FK_ID, userID);
//
//        // insert evaluation type details
//        try {
//            if (db.insert(cSQLDBHelper.TABLE_tblHUMANSET, null, cv) < 0) {
//                return false;
//            }
//
//        } catch (Exception e) {
//            Log.d(TAG, "Exception in importing HUMAN SET from Excel: " + e.getMessage());
//        }
//
//        // close the database connection
//        db.close();
//
//        return true;
//    }
//
//    private boolean addMaterialInput(long inputID, int materialQuantity) {
//        // open the connection to the database
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//
//        // create content object for storing data
//        ContentValues cv = new ContentValues();
//
//        // assign values to the table fields
//        cv.put(cSQLDBHelper.KEY_INPUT_FK_ID, inputID);
//        cv.put(cSQLDBHelper.KEY_QUANTITY, materialQuantity);
//
//        // insert evaluation type details
//        try {
//            if (db.insert(cSQLDBHelper.TABLE_tblMATERIAL, null, cv) < 0) {
//                return false;
//            }
//        } catch (Exception e) {
//            Log.d(TAG, "Exception in importing MATERIAL INPUT from Excel: " + e.getMessage());
//        }
//
//        // close the database connection
//        db.close();
//
//        return true;
//    }
//
//    private boolean addIncomeInput(long inputID, Map<Long, Set<Pair<Long, Double>>> ffaMap) {
//        // open the connection to the database
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//
//        // create content object for storing data
//        ContentValues cv = new ContentValues();
//
//        // assign values to the table fields
//        cv.put(cSQLDBHelper.KEY_INPUT_FK_ID, inputID);
//        //cv.put(cSQLDBHelper.KEY_FUNDER_FK_ID, funderID);
//        //cv.put(cSQLDBHelper.KEY_INCOME, income);
//        // insert evaluation type details
//        try {
//            if (db.insert(cSQLDBHelper.TABLE_tblINCOME, null, cv) < 0) {
//                return false;
//            }
//
//            // add fundID, inputID, funderID and amount/fund tuple
//            Gson gson = new Gson();
//            for (Map.Entry<Long, Set<Pair<Long, Double>>> ffaEntry : ffaMap.entrySet()) {
//                long fundID = ffaEntry.getKey();
//                for (Pair<Long, Double> ifPair : ffaEntry.getValue()) {
//                    long funderID = ifPair.first;
//                    double fund = ifPair.second;
//                    if (!addFund(fundID, inputID, funderID, fund)) {
//                        return false;
//                    }
//                }
//            }
//        } catch (Exception e) {
//            Log.d(TAG, "Exception in importing INCOME INPUT from Excel: " + e.getMessage());
//        }
//
//        // close the database connection
//        db.close();
//
//        return true;
//    }
//
//    private boolean addFund(long fundID, long inputID, long funderID, double fund) {
//        // open the connection to the database
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//
//        // create content object for storing data
//        ContentValues cv = new ContentValues();
//
//        // assign values to the table fields
//        cv.put(cSQLDBHelper.KEY_ID, fundID);
//        cv.put(cSQLDBHelper.KEY_INPUT_FK_ID, inputID);
//        cv.put(cSQLDBHelper.KEY_FUNDER_FK_ID, funderID);
//        cv.put(cSQLDBHelper.KEY_FUND, fund);
//
//        // insert evaluation type details
//        try {
//            if (db.insert(cSQLDBHelper.TABLE_tblFUND, null, cv) < 0) {
//                return false;
//            }
//        } catch (Exception e) {
//            Log.d(TAG, "Exception in importing FUND from Excel: " + e.getMessage());
//        }
//
//        // close the database connection
//        db.close();
//
//        return true;
//    }
//
//    private boolean addExpenseInput(long inputID, double amount) {
//        // open the connection to the database
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//
//        // create content object for storing data
//        ContentValues cv = new ContentValues();
//
//        // assign values to the table fields
//        cv.put(cSQLDBHelper.KEY_INPUT_FK_ID, inputID);
//        cv.put(cSQLDBHelper.KEY_EXPENSE, amount);
//
//        // insert evaluation type details
//        try {
//            if (db.insert(cSQLDBHelper.TABLE_tblEXPENSE, null, cv) < 0) {
//                return false;
//            }
//        } catch (Exception e) {
//            Log.d(TAG, "Exception in importing EXPENSE INPUT from Excel: " + e.getMessage());
//        }
//
//        // close the database connection
//        db.close();
//
//        return true;
//    }
//
//    @Override
//    public boolean deleteInputQuestions() {
//        // open the connection to the database
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//
//        // delete all records
//        long result = db.delete(cSQLDBHelper.TABLE_tblINPUT_QUESTION, null, null);
//
//        // close the database connection
//        db.close();
//
//        return result > -1;
//    }
//
//    @Override
//    public boolean deleteInputActivities() {
//        // open the connection to the database
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//
//        // delete all records
//        long result = db.delete(cSQLDBHelper.TABLE_tblINPUT_ACTIVITY, null, null);
//
//        // close the database connection
//        db.close();
//
//        return result > -1;
//    }
//
//    @Override
//    public boolean deleteHumans() {
//        // open the connection to the database
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//
//        // delete all records
//        long result = db.delete(cSQLDBHelper.TABLE_tblHUMAN, null, null);
//
//        // close the database connection
//        db.close();
//
//        return result > -1;
//    }
//
//    @Override
//    public boolean deleteHumanSets() {
//        // open the connection to the database
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//
//        // delete all records
//        long result = db.delete(cSQLDBHelper.TABLE_tblHUMANSET, null, null);
//
//        // close the database connection
//        db.close();
//
//        return result > -1;
//    }
//
//    @Override
//    public boolean deleteMaterials() {
//        // open the connection to the database
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//
//        // delete all records
//        long result = db.delete(cSQLDBHelper.TABLE_tblMATERIAL, null, null);
//
//        // close the database connection
//        db.close();
//
//        return result > -1;
//    }
//
//    @Override
//    public boolean deleteIncomes() {
//        // open the connection to the database
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//
//        // delete all records
//        long result = db.delete(cSQLDBHelper.TABLE_tblINCOME, null, null);
//
//        // close the database connection
//        db.close();
//
//        return result > -1;
//    }
//
//    @Override
//    public boolean deleteFunds() {
//        // open the connection to the database
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//
//        // delete all records
//        long result = db.delete(cSQLDBHelper.TABLE_tblFUND, null, null);
//
//        // close the database connection
//        db.close();
//
//        return result > -1;
//    }
//
//    @Override
//    public boolean deleteExpenses() {
//        // open the connection to the database
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//
//        // delete all records
//        long result = db.delete(cSQLDBHelper.TABLE_tblEXPENSE, null, null);
//
//        // close the database connection
//        db.close();
//
//        return result > -1;
//    }
//
//    @Override
//    public boolean deleteInputs() {
//        // open the connection to the database
//        SQLiteDatabase db = dbHelper.getWritableDatabase();
//
//        // delete all records
//        long result = db.delete(cSQLDBHelper.TABLE_tblINPUT, null, null);
//
//        // close the database connection
//        db.close();
//
//        return result > -1;
//    }
}
