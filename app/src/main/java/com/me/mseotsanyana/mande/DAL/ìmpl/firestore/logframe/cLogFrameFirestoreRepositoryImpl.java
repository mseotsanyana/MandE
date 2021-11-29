package com.me.mseotsanyana.mande.DAL.ìmpl.firestore.logframe;

import android.content.Context;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.WriteBatch;
import com.me.mseotsanyana.mande.BLL.model.logframe.cActivityModel;
import com.me.mseotsanyana.mande.BLL.model.logframe.cImpactModel;
import com.me.mseotsanyana.mande.BLL.model.logframe.cInputModel;
import com.me.mseotsanyana.mande.BLL.model.logframe.cLogFrameModel;
import com.me.mseotsanyana.mande.BLL.model.logframe.cOutcomeModel;
import com.me.mseotsanyana.mande.BLL.model.logframe.cOutputModel;
import com.me.mseotsanyana.mande.BLL.model.logframe.cQuestionModel;
import com.me.mseotsanyana.mande.BLL.model.logframe.cRaidModel;
import com.me.mseotsanyana.mande.BLL.model.logframe.cResourceTypeModel;
import com.me.mseotsanyana.mande.BLL.model.monitor.cIndicatorModel;
import com.me.mseotsanyana.mande.BLL.model.utils.cCommonPropertiesModel;
import com.me.mseotsanyana.mande.BLL.repository.logframe.iLogFrameRepository;
import com.me.mseotsanyana.mande.DAL.storage.database.cRealtimeHelper;
import com.me.mseotsanyana.mande.DAL.storage.excel.cExcelHelper;
import com.me.mseotsanyana.mande.DAL.ìmpl.cDatabaseUtils;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Created by mseotsanyana on 2016/10/23.
 */
public class cLogFrameFirestoreRepositoryImpl implements iLogFrameRepository {
    //private static final String TAG = cLogFrameFirestoreRepositoryImpl.class.getSimpleName();

    private final cExcelHelper excelHelper;
    // an object of the database helper
    private final FirebaseFirestore db;
    //private final FirebaseAuth firebaseAuth;
    private final Context context;

    public cLogFrameFirestoreRepositoryImpl(Context context) {
        this.context = context;
        //this.firebaseAuth = FirebaseAuth.getInstance();
        this.db = FirebaseFirestore.getInstance();

        excelHelper = new cExcelHelper(context);
    }

    @Override
    public void upLoadLogFrameFromExcel(String organizationServerID, String userServerID,
                                        int primaryTeamBIT, int statusBIT, String filePath,
                                        iUploadLogFrameCallback callback) {

        Workbook workbook = excelHelper.getWorkbookLOGFRAME();

        Sheet logFrameSheet = workbook.getSheet(cExcelHelper.SHEET_tblLOGFRAME);
        Sheet logFrameTreeSheet = workbook.getSheet(cExcelHelper.SHEET_tblLOGFRAMETREE);

        Sheet componentSheet = workbook.getSheet(cExcelHelper.SHEET_tblCOMPONENT);
        Sheet impactSheet = workbook.getSheet(cExcelHelper.SHEET_tblIMPACT);
        Sheet outcomeSheet = workbook.getSheet(cExcelHelper.SHEET_tblOUTCOME);
        Sheet outcomeImpactSheet = workbook.getSheet(cExcelHelper.SHEET_tblOUTCOME_IMPACT);
        Sheet outputSheet = workbook.getSheet(cExcelHelper.SHEET_tblOUTPUT);
        Sheet outputOutcomeSheet = workbook.getSheet(cExcelHelper.SHEET_tblOUTPUT_OUTCOME);
        Sheet activitySheet = workbook.getSheet(cExcelHelper.SHEET_tblACTIVITY);
        Sheet activityOutputSheet = workbook.getSheet(cExcelHelper.SHEET_tblACTIVITY_OUTPUT);
        Sheet preActivitySheet = workbook.getSheet(cExcelHelper.SHEET_tblPRECEDINGACTIVITY);
        Sheet activityAssSheet = workbook.getSheet(cExcelHelper.SHEET_tblACTIVITYASSIGNMENT);
        Sheet inputSheet = workbook.getSheet(cExcelHelper.SHEET_tblINPUT);
        Sheet inputActivitySheet = workbook.getSheet(cExcelHelper.SHEET_tblINPUT_ACTIVITY);

        Sheet humanSheet = workbook.getSheet(cExcelHelper.SHEET_tblHUMAN);
        Sheet materialSheet = workbook.getSheet(cExcelHelper.SHEET_tblMATERIAL);
        Sheet expenseSheet = workbook.getSheet(cExcelHelper.SHEET_tblEXPENSE);
        Sheet incomeSheet = workbook.getSheet(cExcelHelper.SHEET_tblINCOME);
        Sheet resourceTypeSheet = workbook.getSheet(cExcelHelper.SHEET_tblRESOURCETYPE);

        Date now = new Date();

        // resource types
        List<cResourceTypeModel> resourceTypeModels = new ArrayList<>();
        for (Row resourceTypeRow : resourceTypeSheet) {
            //just skip the row if row number is 0
            if (resourceTypeRow.getRowNum() == 0) {
                continue;
            }

            cResourceTypeModel resourceTypeModel = new cResourceTypeModel();

            resourceTypeModel.setResourceTypeServerID(String.valueOf(
                    cDatabaseUtils.getCellAsNumeric(resourceTypeRow, 0)));
            resourceTypeModel.setName(cDatabaseUtils.getCellAsString(resourceTypeRow, 1));
            resourceTypeModel.setDescription(cDatabaseUtils.getCellAsString(resourceTypeRow, 2));

            // update common attributes
            cCommonPropertiesModel commonPropertiesModel;
            commonPropertiesModel = cDatabaseUtils.getCommonModel(context);

            resourceTypeModel.setOrganizationOwnerID(organizationServerID);
            resourceTypeModel.setUserOwnerID(userServerID);
            resourceTypeModel.setTeamOwnerBIT(primaryTeamBIT);
            resourceTypeModel.setUnixpermBITS(commonPropertiesModel.getCunixpermBITS());
            resourceTypeModel.setStatusBIT(statusBIT);

            resourceTypeModel.setCreatedDate(now);
            resourceTypeModel.setModifiedDate(now);

            resourceTypeModels.add(resourceTypeModel);
        }

        /* logframes */

        if (logFrameSheet != null) {
            for (Row cRow : logFrameSheet) {
                //just skip the row if row number is 0
                if (cRow.getRowNum() == 0) {
                    continue;
                }

                cLogFrameModel logFrameModel = new cLogFrameModel();

                logFrameModel.setLogFrameServerID(String.valueOf(
                        cDatabaseUtils.getCellAsNumeric(cRow, 0)));
                logFrameModel.setParentServerID(String.valueOf(
                        cDatabaseUtils.getCellAsNumeric(cRow, 1)));
                logFrameModel.setName(cDatabaseUtils.getCellAsString(cRow, 2));
                logFrameModel.setDescription(cDatabaseUtils.getCellAsString(cRow, 3));
                logFrameModel.setStartDate(cDatabaseUtils.getCellAsDate(cRow, 4));
                logFrameModel.setEndDate(cDatabaseUtils.getCellAsDate(cRow, 5));

                // update parent logframes
                if (logFrameModel.getParentServerID().equals("0")) {
                    logFrameModel.setParentServerID(null);
                }

                // update child logframes
                if (logFrameTreeSheet != null) {
                    for (Row cRowTree : logFrameTreeSheet) {
                        //just skip the row if row number is 0
                        if (cRowTree.getRowNum() == 0) {
                            continue;
                        }

                        String parentID = String.valueOf(
                                cDatabaseUtils.getCellAsNumeric(cRowTree, 0));
                        if (parentID.equals(logFrameModel.getLogFrameServerID())) {
                            String childID = String.valueOf(
                                    cDatabaseUtils.getCellAsNumeric(cRowTree, 1));
                            logFrameModel.getChildren().add(childID);
                        }
                    }
                }

                // update common attributes
                cCommonPropertiesModel commonPropertiesModel;
                commonPropertiesModel = cDatabaseUtils.getCommonModel(context);

                logFrameModel.setOrganizationOwnerID(organizationServerID);
                logFrameModel.setUserOwnerID(userServerID);
                logFrameModel.setTeamOwnerBIT(primaryTeamBIT);
                logFrameModel.setUnixpermBITS(commonPropertiesModel.getCunixpermBITS());
                logFrameModel.setStatusBIT(statusBIT);

                logFrameModel.setCreatedDate(now);
                logFrameModel.setModifiedDate(now);

                /* logframe components */
                Map<String, Map<String, Object>> componentModels = new HashMap<>();
                List<cImpactModel> impactModels = new ArrayList<>();
                List<cOutcomeModel> outcomeModels = new ArrayList<>();

                Map<String, Map<String, Object>> outcomeImpactModels = new HashMap<>();
                List<cOutputModel> outputModels = new ArrayList<>();
                Map<String, Map<String, Object>> outputOutcomeModels = new HashMap<>();
                List<cActivityModel> activityModels = new ArrayList<>();
                Map<String, Map<String, Object>> activityOutputModels = new HashMap<>();
                Map<String, Map<String, Object>> precedingActivities = new HashMap<>();
                Map<String, Map<String, Object>> activityAssignments = new HashMap<>();
                List<cInputModel> inputModels = new ArrayList<>();
                Map<String, Map<String, Object>> inputActivityModels = new HashMap<>();
                Map<String, Map<String, Object>> humanModels = new HashMap<>();
                Map<String, Map<String, Object>> materialModels = new HashMap<>();
                Map<String, Map<String, Object>> expenseModels = new HashMap<>();
                Map<String, Map<String, Object>> incomeModels = new HashMap<>();

                for (Row componentRow : componentSheet) {
                    //just skip the row if row number is 0
                    if (componentRow.getRowNum() == 0) {
                        continue;
                    }

                    String logFrameServerID = String.valueOf(
                            cDatabaseUtils.getCellAsNumeric(componentRow, 1));
                    if (logFrameServerID.equals(logFrameModel.getLogFrameServerID())) {

                        String componentServerID = String.valueOf(
                                cDatabaseUtils.getCellAsNumeric(componentRow, 0));
                        String logframeServerID = String.valueOf(
                                cDatabaseUtils.getCellAsNumeric(componentRow, 1));

                        Map<String, Object> componentModel = new HashMap<>();
                        componentModel.put("componentServerID", componentServerID);
                        componentModel.put("logframeServerID", logframeServerID);

                        componentModels.put(componentServerID, componentModel);

                        // list of components of the logframe
                        logFrameModel.getComponents().add(componentServerID);

                        // logframe impacts
                        for (Row impactRow : impactSheet) {
                            //just skip the row if row number is 0
                            if (impactRow.getRowNum() == 0) {
                                continue;
                            }

                            String componentID = String.valueOf(
                                    cDatabaseUtils.getCellAsNumeric(impactRow, 0));
                            if (componentID.equals(componentServerID)) {
                                String parentID = String.valueOf(
                                        cDatabaseUtils.getCellAsNumeric(impactRow, 1));

                                cImpactModel impactModel = new cImpactModel();

                                impactModel.setComponentServerID(String.valueOf(
                                        cDatabaseUtils.getCellAsNumeric(componentRow, 0)));
                                impactModel.getLogFrameModel().setLogFrameServerID(String.valueOf(
                                        cDatabaseUtils.getCellAsNumeric(componentRow, 1)));
                                impactModel.setParentServerID(
                                        !parentID.equals("0") ? parentID : null);
                                impactModel.setName(
                                        cDatabaseUtils.getCellAsString(componentRow, 2));
                                impactModel.setDescription(
                                        cDatabaseUtils.getCellAsString(componentRow, 3));
                                impactModel.setStartDate(
                                        cDatabaseUtils.getCellAsDate(componentRow, 4));
                                impactModel.setEndDate(
                                        cDatabaseUtils.getCellAsDate(componentRow, 5));

                                // update common attributes
                                impactModel.setOrganizationOwnerID(organizationServerID);
                                impactModel.setUserOwnerID(userServerID);
                                impactModel.setTeamOwnerBIT(primaryTeamBIT);
                                impactModel.setUnixpermBITS(commonPropertiesModel.getCunixpermBITS());
                                impactModel.setStatusBIT(statusBIT);

                                impactModel.setCreatedDate(now);
                                impactModel.setModifiedDate(now);

                                impactModels.add(impactModel);
                            }
                        }

                        // logframe outcomes
                        for (Row outcomeRow : outcomeSheet) {
                            //just skip the row if row number is 0
                            if (outcomeRow.getRowNum() == 0) {
                                continue;
                            }

                            String componentID = String.valueOf(
                                    cDatabaseUtils.getCellAsNumeric(outcomeRow, 0));
                            if (componentID.equals(componentServerID)) {
                                String parentID = String.valueOf(
                                        cDatabaseUtils.getCellAsNumeric(outcomeRow, 1));
                                String impactID = String.valueOf(
                                        cDatabaseUtils.getCellAsNumeric(outcomeRow, 2));

                                cOutcomeModel outcomeModel = new cOutcomeModel();

                                outcomeModel.setComponentServerID(String.valueOf(
                                        cDatabaseUtils.getCellAsNumeric(componentRow, 0)));
                                outcomeModel.getLogFrameModel().setLogFrameServerID(String.valueOf(
                                        cDatabaseUtils.getCellAsNumeric(componentRow, 1)));
                                outcomeModel.setParentServerID(
                                        !parentID.equals("0") ? parentID : null);
                                outcomeModel.setImpactServerID(impactID);
                                outcomeModel.setName(
                                        cDatabaseUtils.getCellAsString(componentRow, 2));
                                outcomeModel.setDescription(
                                        cDatabaseUtils.getCellAsString(componentRow, 3));
                                outcomeModel.setStartDate(
                                        cDatabaseUtils.getCellAsDate(componentRow, 4));
                                outcomeModel.setEndDate(
                                        cDatabaseUtils.getCellAsDate(componentRow, 5));

                                // update common attributes
                                outcomeModel.setOrganizationOwnerID(organizationServerID);
                                outcomeModel.setUserOwnerID(userServerID);
                                outcomeModel.setTeamOwnerBIT(primaryTeamBIT);
                                outcomeModel.setUnixpermBITS(commonPropertiesModel.getCunixpermBITS());
                                outcomeModel.setStatusBIT(statusBIT);

                                outcomeModel.setCreatedDate(now);
                                outcomeModel.setModifiedDate(now);

                                outcomeModels.add(outcomeModel);

                            }
                        }

                        /* impacts of the outcome for a sub-logframe */
                        for (Row outcomeImpactRow : outcomeImpactSheet) {
                            //just skip the row if row number is 0
                            if (outcomeImpactRow.getRowNum() == 0) {
                                continue;
                            }

                            String outcomeID = String.valueOf(
                                    cDatabaseUtils.getCellAsNumeric(outcomeImpactRow, 2));

                            if (outcomeID.equals(componentServerID)) {

                                String parentID = String.valueOf(
                                        cDatabaseUtils.getCellAsNumeric(outcomeImpactRow, 0));
                                String childID = String.valueOf(
                                        cDatabaseUtils.getCellAsNumeric(outcomeImpactRow, 1));
                                String impactID = String.valueOf(
                                        cDatabaseUtils.getCellAsNumeric(outcomeImpactRow, 3));

                                Map<String, Object> outcomeImpactModel = new HashMap<>();

                                outcomeImpactModel.put("parentLogframeServerID", parentID);
                                outcomeImpactModel.put("childLogframeServerID", childID);
                                outcomeImpactModel.put("outcomeServerID", outcomeID);
                                outcomeImpactModel.put("impactServerID", impactID);

                                outcomeImpactModels.put(parentID + "_" + childID + "_" +
                                        outcomeID + "_" + impactID, outcomeImpactModel);
                            }
                        }

                        // logframe outputs
                        for (Row outputRow : outputSheet) {
                            //just skip the row if row number is 0
                            if (outputRow.getRowNum() == 0) {
                                continue;
                            }

                            String componentID = String.valueOf(
                                    cDatabaseUtils.getCellAsNumeric(outputRow, 0));
                            if (componentID.equals(componentServerID)) {
                                String parentID = String.valueOf(
                                        cDatabaseUtils.getCellAsNumeric(outputRow, 1));
                                String outcomeID = String.valueOf(
                                        cDatabaseUtils.getCellAsNumeric(outputRow, 2));

                                cOutputModel outputModel = new cOutputModel();

                                outputModel.setComponentServerID(String.valueOf(
                                        cDatabaseUtils.getCellAsNumeric(componentRow, 0)));
                                outputModel.getLogFrameModel().setLogFrameServerID(String.valueOf(
                                        cDatabaseUtils.getCellAsNumeric(componentRow, 1)));
                                outputModel.setParentServerID(
                                        !parentID.equals("0") ? parentID : null);
                                outputModel.setOutcomeServerID(outcomeID);
                                outputModel.setName(cDatabaseUtils.getCellAsString(componentRow, 2));
                                outputModel.setDescription(cDatabaseUtils.getCellAsString(componentRow, 3));
                                outputModel.setStartDate(cDatabaseUtils.getCellAsDate(componentRow, 4));
                                outputModel.setEndDate(cDatabaseUtils.getCellAsDate(componentRow, 5));

                                // update common attributes
                                outputModel.setOrganizationOwnerID(organizationServerID);
                                outputModel.setUserOwnerID(userServerID);
                                outputModel.setTeamOwnerBIT(primaryTeamBIT);
                                outputModel.setUnixpermBITS(commonPropertiesModel.getCunixpermBITS());
                                outputModel.setStatusBIT(statusBIT);

                                outputModel.setCreatedDate(now);
                                outputModel.setModifiedDate(now);

                                outputModels.add(outputModel);

                            }
                        }

                        /* outcomes of the output for a sub-logframe */
                        for (Row outputOutcomeRow : outputOutcomeSheet) {
                            //just skip the row if row number is 0
                            if (outputOutcomeRow.getRowNum() == 0) {
                                continue;
                            }

                            String outputID = String.valueOf(
                                    cDatabaseUtils.getCellAsNumeric(outputOutcomeRow, 2));
                            if (outputID.equals(componentServerID)) {

                                String parentID = String.valueOf(
                                        cDatabaseUtils.getCellAsNumeric(outputOutcomeRow, 0));
                                String childID = String.valueOf(
                                        cDatabaseUtils.getCellAsNumeric(outputOutcomeRow, 1));
                                String outcomeID = String.valueOf(
                                        cDatabaseUtils.getCellAsNumeric(outputOutcomeRow, 3));

                                Map<String, Object> outputOutcomeModel = new HashMap<>();
                                outputOutcomeModel.put("parentLogframeServerID", parentID);
                                outputOutcomeModel.put("childLogframeServerID", childID);
                                outputOutcomeModel.put("outputServerID", outputID);
                                outputOutcomeModel.put("outcomeServerID", outcomeID);

                                outputOutcomeModels.put(parentID + "_" + childID + "_" +
                                        outputID + "_" + outcomeID, outputOutcomeModel);
                            }
                        }

                        // logframe activities
                        for (Row activityRow : activitySheet) {
                            //just skip the row if row number is 0
                            if (activityRow.getRowNum() == 0) {
                                continue;
                            }

                            String componentID = String.valueOf(
                                    cDatabaseUtils.getCellAsNumeric(activityRow, 0));
                            if (componentID.equals(componentServerID)) {
                                String parentID = String.valueOf(
                                        cDatabaseUtils.getCellAsNumeric(activityRow, 1));
                                String outputID = String.valueOf(
                                        cDatabaseUtils.getCellAsNumeric(activityRow, 2));

                                cActivityModel activityModel = new cActivityModel();

                                activityModel.setComponentServerID(String.valueOf(
                                        cDatabaseUtils.getCellAsNumeric(componentRow, 0)));
                                activityModel.getLogFrameModel().setLogFrameServerID(String.valueOf(
                                        cDatabaseUtils.getCellAsNumeric(componentRow, 1)));
                                activityModel.setParentServerID(
                                        !parentID.equals("0") ? parentID : null);
                                activityModel.setOutputServerID(outputID);
                                activityModel.setName(
                                        cDatabaseUtils.getCellAsString(componentRow, 2));
                                activityModel.setDescription(
                                        cDatabaseUtils.getCellAsString(componentRow, 3));
                                activityModel.setStartDate(
                                        cDatabaseUtils.getCellAsDate(componentRow, 4));
                                activityModel.setEndDate(
                                        cDatabaseUtils.getCellAsDate(componentRow, 5));

                                // update common attributes
                                activityModel.setOrganizationOwnerID(organizationServerID);
                                activityModel.setUserOwnerID(userServerID);
                                activityModel.setTeamOwnerBIT(primaryTeamBIT);
                                activityModel.setUnixpermBITS(
                                        commonPropertiesModel.getCunixpermBITS());
                                activityModel.setStatusBIT(statusBIT);

                                activityModel.setCreatedDate(now);
                                activityModel.setModifiedDate(now);

                                activityModels.add(activityModel);
                            }
                        }

                        /* outputs of the activity for a sub-logframe */
                        for (Row activityOutputRow : activityOutputSheet) {
                            //just skip the row if row number is 0
                            if (activityOutputRow.getRowNum() == 0) {
                                continue;
                            }

                            String activityID = String.valueOf(
                                    cDatabaseUtils.getCellAsNumeric(activityOutputRow, 2));
                            if (activityID.equals(componentServerID)) {

                                String parentID = String.valueOf(
                                        cDatabaseUtils.getCellAsNumeric(activityOutputRow, 0));
                                String childID = String.valueOf(
                                        cDatabaseUtils.getCellAsNumeric(activityOutputRow, 1));
                                String outputID = String.valueOf(
                                        cDatabaseUtils.getCellAsNumeric(activityOutputRow, 3));

                                Map<String, Object> activityOutputModel = new HashMap<>();

                                activityOutputModel.put("parentLogframeServerID", parentID);
                                activityOutputModel.put("childLogframeServerID", childID);
                                activityOutputModel.put("activityServerID", activityID);
                                activityOutputModel.put("outputServerID", outputID);

                                activityOutputModels.put(parentID + "_" + childID + "_" +
                                        activityID + "_" + outputID, activityOutputModel);
                            }
                        }

                        /* preceding activities of the activity */
                        for (Row preActivityRow : preActivitySheet) {
                            //just skip the row if row number is 0
                            if (preActivityRow.getRowNum() == 0) {
                                continue;
                            }

                            String sucActivityID = String.valueOf(
                                    cDatabaseUtils.getCellAsNumeric(preActivityRow, 0));
                            if (sucActivityID.equals(componentServerID)) {
                                String preActivityID = String.valueOf(
                                        cDatabaseUtils.getCellAsNumeric(preActivityRow, 1));

                                Map<String, Object> preActivityModel = new HashMap<>();
                                preActivityModel.put("activityServerID", sucActivityID);
                                preActivityModel.put("precedingActivityServerID", preActivityID);

                                precedingActivities.put(sucActivityID + "_" + preActivityID,
                                        preActivityModel);
                            }
                        }

                        /* activity staff assignments */
                        for (Row activityAssRow : activityAssSheet) {
                            //just skip the row if row number is 0
                            if (activityAssRow.getRowNum() == 0) {
                                continue;
                            }

                            String assignedActivityID = String.valueOf(
                                    cDatabaseUtils.getCellAsNumeric(activityAssRow, 2));
                            if (assignedActivityID.equals(componentServerID)) {
                                String staffID = String.valueOf(
                                        cDatabaseUtils.getCellAsNumeric(activityAssRow, 1));
                                String assignmentID = String.valueOf(
                                        cDatabaseUtils.getCellAsNumeric(activityAssRow, 0));

                                Map<String, Object> assActivityModel = new HashMap<>();
                                assActivityModel.put("assignmentServerID", assignmentID);
                                assActivityModel.put("userServerID", staffID);
                                assActivityModel.put("activityServerID", assignedActivityID);

                                activityAssignments.put(assignmentID, assActivityModel);
                            }
                        }

                        // logframe inputs
                        for (Row inputRow : inputSheet) {
                            //just skip the row if row number is 0
                            if (inputRow.getRowNum() == 0) {
                                continue;
                            }

                            String componentID = String.valueOf(
                                    cDatabaseUtils.getCellAsNumeric(inputRow, 0));
                            if (componentID.equals(componentServerID)) {
                                String activityID = String.valueOf(
                                        cDatabaseUtils.getCellAsNumeric(inputRow, 1));
                                String resourceTypeID = String.valueOf(
                                        cDatabaseUtils.getCellAsNumeric(inputRow, 2));

                                cInputModel inputModel = new cInputModel();

                                inputModel.setComponentServerID(String.valueOf(
                                        cDatabaseUtils.getCellAsNumeric(componentRow, 0)));
                                inputModel.getLogFrameModel().setLogFrameServerID(String.valueOf(
                                        cDatabaseUtils.getCellAsNumeric(componentRow, 1)));
                                inputModel.getResourceTypeModel().setResourceTypeServerID(resourceTypeID);
                                inputModel.setActivityServerID(activityID);
                                inputModel.setName(
                                        cDatabaseUtils.getCellAsString(componentRow, 2));
                                inputModel.setDescription(
                                        cDatabaseUtils.getCellAsString(componentRow, 3));
                                inputModel.setStartDate(
                                        cDatabaseUtils.getCellAsDate(componentRow, 4));
                                inputModel.setEndDate(
                                        cDatabaseUtils.getCellAsDate(componentRow, 5));

                                // update common attributes
                                inputModel.setOrganizationOwnerID(organizationServerID);
                                inputModel.setUserOwnerID(userServerID);
                                inputModel.setTeamOwnerBIT(primaryTeamBIT);
                                inputModel.setUnixpermBITS(
                                        commonPropertiesModel.getCunixpermBITS());
                                inputModel.setStatusBIT(statusBIT);

                                inputModel.setCreatedDate(now);
                                inputModel.setModifiedDate(now);

                                inputModels.add(inputModel);

                            }
                        }

                        /* activities of the input for a sub-logframe */
                        for (Row inputActivityRow : inputActivitySheet) {
                            //just skip the row if row number is 0
                            if (inputActivityRow.getRowNum() == 0) {
                                continue;
                            }

                            String inputID = String.valueOf(
                                    cDatabaseUtils.getCellAsNumeric(inputActivityRow, 2));
                            if (inputID.equals(componentServerID)) {

                                String parentID = String.valueOf(
                                        cDatabaseUtils.getCellAsNumeric(inputActivityRow, 0));
                                String childID = String.valueOf(
                                        cDatabaseUtils.getCellAsNumeric(inputActivityRow, 1));
                                String activityID = String.valueOf(
                                        cDatabaseUtils.getCellAsNumeric(inputActivityRow, 3));

                                Map<String, Object> inputActivityModel = new HashMap<>();
                                inputActivityModel.put("parentLogframeServerID", parentID);
                                inputActivityModel.put("childLogframeServerID", childID);
                                inputActivityModel.put("inputServerID", inputID);
                                inputActivityModel.put("activityServerID", activityID);

                                inputActivityModels.put(parentID + "_" + childID + "_" +
                                        inputID + "_" + activityID, inputActivityModel);
                            }
                        }

                        /* human inputs */
                        for (Row humanRow : humanSheet) {
                            //just skip the row if row number is 0
                            if (humanRow.getRowNum() == 0) {
                                continue;
                            }

                            String inputID = String.valueOf(
                                    cDatabaseUtils.getCellAsNumeric(humanRow, 0));
                            if (inputID.equals(componentServerID)) {
                                int humanQuantity = cDatabaseUtils.getCellAsNumeric(
                                        humanRow, 1);

                                Map<String, Object> humanModel = new HashMap<>();
                                humanModel.put("inputServerID", inputID);
                                humanModel.put("humanQuantity", humanQuantity);

                                humanModels.put(inputID, humanModel);
                            }
                        }

                        /* expense inputs */
                        for (Row expenseRow : expenseSheet) {
                            //just skip the row if row number is 0
                            if (expenseRow.getRowNum() == 0) {
                                continue;
                            }

                            String inputID = String.valueOf(
                                    cDatabaseUtils.getCellAsNumeric(expenseRow, 0));
                            if (inputID.equals(componentServerID)) {
                                double expenditure = cDatabaseUtils.getCellAsNumeric(
                                        expenseRow, 1);

                                Map<String, Object> expenseModel = new HashMap<>();
                                expenseModel.put("inputServerID", inputID);
                                expenseModel.put("expenditure", expenditure);

                                expenseModels.put(inputID, expenseModel);
                            }
                        }

                        /* material inputs */
                        for (Row materialRow : materialSheet) {
                            //just skip the row if row number is 0
                            if (materialRow.getRowNum() == 0) {
                                continue;
                            }

                            String inputID = String.valueOf(
                                    cDatabaseUtils.getCellAsNumeric(materialRow, 0));
                            if (inputID.equals(componentServerID)) {
                                int materialQuantity = cDatabaseUtils.getCellAsNumeric(
                                        materialRow, 1);

                                Map<String, Object> materialModel = new HashMap<>();
                                materialModel.put("inputServerID", inputID);
                                materialModel.put("materialQuantity", materialQuantity);

                                materialModels.put(inputID, materialModel);
                            }
                        }

                        /* income inputs */
                        for (Row incomeRow : incomeSheet) {
                            //just skip the row if row number is 0
                            if (incomeRow.getRowNum() == 0) {
                                continue;
                            }

                            String inputID = String.valueOf(
                                    cDatabaseUtils.getCellAsNumeric(incomeRow, 0));
                            if (inputID.equals(componentServerID)) {
                                Map<String, Object> incomeModel = new HashMap<>();
                                incomeModel.put("inputServerID", inputID);

                                incomeModels.put(inputID, incomeModel);
                            }
                        }
                    }
                }

//                if (logFrameModel.getParentServerID() == null) {
//                    for (int i = 0; i < outcomeModels.size(); i++) {
//                        for (String childID : logFrameModel.getChildren()) {
//
//                            String buildKey = logFrameModel.getLogFrameServerID() + "_" +
//                                    childID + "_" +
//                                    outcomeModels.get(i).getComponentServerID();
//                            Log.d("TAG", " ==== " + buildKey + " === " + outcomeImpactModels.size());
//                            for (Map.Entry<String, List<String>> impact :
//                                    outcomeImpactModels.entrySet()) {
//                                String key = impact.getKey();
//                                Log.d("TAG", " ==== " + buildKey + " === " + key);
//                                if (key.equals(buildKey)) {
//                                    outcomeModels.get(i).getSubimpactModels().addAll(impact.getValue());
//                                }
//                            }
//                        }
//                    }
//                }

//                // update outcomes in the impact component
//                for (int i = 0; i < impactModels.size(); i++) {
//                    for (int j = 0; j < outcomeModels.size(); j++) {
//                        if (impactModels.get(i).getComponentServerID().equals(
//                                outcomeModels.get(j).getImpactServerID())) {
//                            impactModels.get(i).getOutcomeModels().add(
//                                    outcomeModels.get(j).getComponentServerID());
//                        }
//                    }
//                }
//                // update outputs in the outcome component
//                for (int i = 0; i < outcomeModels.size(); i++) {
//                    for (int j = 0; j < outputModels.size(); j++) {
//                        if (outcomeModels.get(i).getComponentServerID().equals(
//                                outputModels.get(j).getOutcomeServerID())) {
//                            outcomeModels.get(i).getOutputs().add(
//                                    outputModels.get(j).getComponentServerID());
//                        }
//                    }
//                }
//                // update activities in the output component
//                for (int i = 0; i < outputModels.size(); i++) {
//                    for (int j = 0; j < activityModels.size(); j++) {
//                        if (outputModels.get(i).getComponentServerID().equals(
//                                activityModels.get(j).getOutputServerID())) {
//                            outputModels.get(i).getActivities().add(
//                                    activityModels.get(j).getComponentServerID());
//                        }
//                    }
//                }
//                // update inputs in the activity component
//                for (int i = 0; i < activityModels.size(); i++) {
//                    for (int j = 0; j < inputModels.size(); j++) {
//                        if (activityModels.get(i).getComponentServerID().equals(
//                                inputModels.get(j).getActivityServerID())) {
//                            activityModels.get(i).getInputs().add(
//                                    inputModels.get(j).getComponentServerID());
//                        }
//                    }
//                }


                // add logframes
                createLogFrameFromExcel(logFrameModel, componentModels, impactModels,
                        outcomeModels, outcomeImpactModels, outputModels, outputOutcomeModels,
                        activityModels, activityOutputModels, precedingActivities,
                        activityAssignments, resourceTypeModels, inputModels,
                        inputActivityModels, humanModels, materialModels,
                        expenseModels, incomeModels, callback);
            }

        } else {
            callback.onUploadLogFrameFailed("Failed to read excel file!");
        }

    }

    private void createLogFrameFromExcel(cLogFrameModel logFrameModel,
                                        Map<String, Map<String, Object>> componentModels,
                                        List<cImpactModel> impactModels,
                                        List<cOutcomeModel> outcomeModels,
                                        Map<String, Map<String, Object>> outcomeImpactModels,
                                        List<cOutputModel> outputModels,
                                        Map<String, Map<String, Object>> outputOutcomeModels,
                                        List<cActivityModel> activityModels,
                                        Map<String, Map<String, Object>> activityOutputModels,
                                        Map<String, Map<String, Object>> precedingActivities,
                                        Map<String, Map<String, Object>> activityAssignments,
                                        List<cResourceTypeModel> resourceTypeModels,
                                        List<cInputModel> inputModels,
                                        Map<String, Map<String, Object>> inputActivityModels,
                                        Map<String, Map<String, Object>> humanModels,
                                        Map<String, Map<String, Object>> materialModels,
                                        Map<String, Map<String, Object>> expenseModels,
                                        Map<String, Map<String, Object>> incomeModels,
                                        iUploadLogFrameCallback callback) {


        CollectionReference coLogFrameRef, coComponentRef, coImpactRef, coOutcomeRef,
                coOutcomeImpactRef, coOutputRef, coOutputOutcomeRef, coActivityRef,
                coActivityOutputRef, coPreActivityRef, coAssActivityRef, coResourceTypeRef,
                coInputRef, coInputActivityRef, coHumanRef, coMaterialRef, coExpenseRef,
                coIncomeRef;

        coLogFrameRef = db.collection(cRealtimeHelper.KEY_LOGFRAMES);
        coComponentRef = db.collection(cRealtimeHelper.KEY_LOGFRAME_COMPONENTS);
        coImpactRef = db.collection(cRealtimeHelper.KEY_COMPONENT_IMPACTS);
        coOutcomeRef = db.collection(cRealtimeHelper.KEY_COMPONENT_OUTCOMES);
        coOutcomeImpactRef = db.collection(cRealtimeHelper.KEY_OUTCOME_IMPACTS);
        coOutputRef = db.collection(cRealtimeHelper.KEY_COMPONENT_OUTPUTS);
        coOutputOutcomeRef = db.collection(cRealtimeHelper.KEY_OUTPUT_OUTCOMES);
        coActivityRef = db.collection(cRealtimeHelper.KEY_COMPONENT_ACTIVITIES);
        coActivityOutputRef = db.collection(cRealtimeHelper.KEY_ACTIVITY_OUTPUTS);
        coPreActivityRef = db.collection(cRealtimeHelper.KEY_ACTIVITY_PRECEDINGS);
        coAssActivityRef = db.collection(cRealtimeHelper.KEY_ACTIVITY_ASSIGNMENTS);
        coResourceTypeRef = db.collection(cRealtimeHelper.KEY_RESOURCETYPES);
        coInputRef = db.collection(cRealtimeHelper.KEY_COMPONENT_INPUTS);
        coInputActivityRef = db.collection(cRealtimeHelper.KEY_INPUT_ACTIVITIES);
        coHumanRef = db.collection(cRealtimeHelper.KEY_INPUT_HUMANS);
        coMaterialRef = db.collection(cRealtimeHelper.KEY_INPUT_MATERIALS);
        coExpenseRef = db.collection(cRealtimeHelper.KEY_INPUT_EXPENSES);
        coIncomeRef = db.collection(cRealtimeHelper.KEY_INPUT_INCOMES);

        /* create a batch object */
        WriteBatch batch = db.batch();

        /* add logframe */
        batch.set(coLogFrameRef.document(logFrameModel.getLogFrameServerID()),
                logFrameModel);

        /* add corresponding components */
        for (Map.Entry<String, Map<String, Object>> componentModel : componentModels.entrySet()) {
            batch.set(coComponentRef.document(componentModel.getKey()), componentModel.getValue());
        }

        /* add impact components */
        for (cImpactModel impactModel : impactModels) {
            batch.set(coImpactRef.document(impactModel.getComponentServerID()), impactModel);
        }

        /* add outcome components */
        for (cOutcomeModel outcomeModel : outcomeModels) {
            batch.set(coOutcomeRef.document(outcomeModel.getComponentServerID()), outcomeModel);
        }

        /* add outcome-impacts components */
        for (Map.Entry<String, Map<String, Object>> outcomeImpactModel :
                outcomeImpactModels.entrySet()) {
            batch.set(coOutcomeImpactRef.document(outcomeImpactModel.getKey()),
                    outcomeImpactModel.getValue());
        }

        /* add output components */
        for (cOutputModel outputModel : outputModels) {
            batch.set(coOutputRef.document(outputModel.getComponentServerID()), outputModel);
        }

        /* add output-outcomes components */
        for (Map.Entry<String, Map<String, Object>> outputOutcomeModel :
                outputOutcomeModels.entrySet()) {
            batch.set(coOutputOutcomeRef.document(outputOutcomeModel.getKey()),
                    outputOutcomeModel.getValue());
        }

        /* add activity components */
        for (cActivityModel activityModel : activityModels) {
            batch.set(coActivityRef.document(activityModel.getComponentServerID()), activityModel);
        }
        /* add activity-outputs components */
        for (Map.Entry<String, Map<String, Object>> activityOutputModel :
                activityOutputModels.entrySet()) {
            batch.set(coActivityOutputRef.document(activityOutputModel.getKey()),
                    activityOutputModel.getValue());
        }

        /* add preceding activities */
        for (Map.Entry<String, Map<String, Object>> preActivity : precedingActivities.entrySet()) {
            batch.set(coPreActivityRef.document(preActivity.getKey()), preActivity.getValue());
        }

        /* add assignment of activities */
        for (Map.Entry<String, Map<String, Object>> assActivity : activityAssignments.entrySet()) {
            batch.set(coAssActivityRef.document(assActivity.getKey()), assActivity.getValue());
        }

        /* add resource types  */
        for (cResourceTypeModel resourceTypeModel : resourceTypeModels) {
            batch.set(coResourceTypeRef.document(resourceTypeModel.getResourceTypeServerID()),
                    resourceTypeModel);
        }

        /* add input components */
        for (cInputModel inputModel : inputModels) {
            batch.set(coInputRef.document(inputModel.getComponentServerID()), inputModel);
        }

        /* add input-activities components */
        for (Map.Entry<String, Map<String, Object>> inputActivityModel :
                inputActivityModels.entrySet()) {
            batch.set(coInputActivityRef.document(inputActivityModel.getKey()),
                    inputActivityModel.getValue());
        }

        /* add humans */
        for (Map.Entry<String, Map<String, Object>> humanModel : humanModels.entrySet()) {
            batch.set(coHumanRef.document(humanModel.getKey()), humanModel.getValue());
        }

        /* add material */
        for (Map.Entry<String, Map<String, Object>> materialModel : materialModels.entrySet()) {
            batch.set(coMaterialRef.document(materialModel.getKey()), materialModel.getValue());
        }

        /* add expenses */
        for (Map.Entry<String, Map<String, Object>> expenseModel : expenseModels.entrySet()) {
            batch.set(coExpenseRef.document(expenseModel.getKey()), expenseModel.getValue());
        }

        /* add incomes */
        for (Map.Entry<String, Map<String, Object>> incomeModel : incomeModels.entrySet()) {
            batch.set(coIncomeRef.document(incomeModel.getKey()), incomeModel.getValue());
        }

        batch.commit().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                callback.onUploadLogFrameSucceeded("LogFrame module successfully uploaded");
            } else {
                callback.onUploadLogFrameFailed("Failed to update LogFrame module");
            }
        });
    }

    //FIXME: change to call backs
    @Override
    public boolean addLogFrameFromExcel() {
        return false;
    }

    @Override
    public boolean createLogFrameModel(cLogFrameModel logFrameModel) {
        return false;
    }

    @Override
    public boolean createSubLogFrameModel(String logFrameID, cLogFrameModel logFrameModel) {
        return false;
    }

    @Override
    public boolean updateLogFrameModel(cLogFrameModel logFrameModel) {
        return false;
    }

    @Override
    public void readLogFrames(String organizationServerID, String userServerID,
                              int primaryTeamBIT, List<Integer> secondaryTeamBITS,
                              List<Integer> statusBITS, iReadLogFramesCallback callback) {

        CollectionReference coLogFrameRef = db.collection(cRealtimeHelper.KEY_LOGFRAMES);

        Query logframeQuery = coLogFrameRef
                .whereEqualTo("organizationOwnerID", organizationServerID)
                .whereIn("statusBIT", statusBITS);

        logframeQuery.get()
                .addOnCompleteListener(task -> {
                    List<cLogFrameModel> logFrameModels = new ArrayList<>();
                    for (DocumentSnapshot logframe_doc : Objects.requireNonNull(task.getResult())) {
                        cLogFrameModel logFrameModel = logframe_doc.toObject(cLogFrameModel.class);

                        if (logFrameModel != null) {
                            cDatabaseUtils.cUnixPerm perm = new cDatabaseUtils.cUnixPerm();
                            perm.setUserOwnerID(logFrameModel.getUserOwnerID());
                            perm.setTeamOwnerBIT(logFrameModel.getTeamOwnerBIT());
                            perm.setUnixpermBITS(logFrameModel.getUnixpermBITS());

                            if (cDatabaseUtils.isPermitted(perm, userServerID, primaryTeamBIT,
                                    secondaryTeamBITS)) {
                                logFrameModels.add(logFrameModel);
                            }
                        }
                    }


                    // call back on logframes
                    callback.onReadLogFrameSucceeded(logFrameModels);
                })
                .addOnFailureListener(e -> callback.onReadLogFrameFailed(
                        "Failed to read logframes"));
    }

    @Override
    public Set<cImpactModel> getImpactModelSetByID(long logFrameID, long userID, int primaryRole,
                                                   int secondaryRoles, int statusBITS) {
        return null;
    }

    @Override
    public Set<cOutcomeModel> getOutcomeModelSetByID(long logFrameID, long userID, int primaryRole,
                                                     int secondaryRoles, int statusBITS) {
        return null;
    }

    @Override
    public Set<cOutputModel> getOutputModelSetByID(long logFrameID, long userID, int primaryRole,
                                                   int secondaryRoles, int statusBITS) {
        return null;
    }

    @Override
    public Set<cActivityModel> getActivityModelSetByID(long logFrameID, long userID,
                                                       int primaryRole, int secondaryRoles,
                                                       int statusBITS) {
        return null;
    }

    @Override
    public Set<cInputModel> getInputModelSetByID(long logFrameID, long userID, int primaryRole,
                                                 int secondaryRoles, int statusBITS) {
        return null;
    }

    @Override
    public ArrayList<cLogFrameModel> getLogFrameModels() {
        return null;
    }

    @Override
    public ArrayList<cLogFrameModel> getChildLogFramesByID(long parentID) {
        return null;
    }

    @Override
    public ArrayList<cImpactModel> getImpactsByID(long logFrameID) {
        return null;
    }

    @Override
    public ArrayList<cOutcomeModel> getOutcomesByID(long logFrameID) {
        return null;
    }

    @Override
    public ArrayList<cOutputModel> getOutputsByID(long logFrameID) {
        return null;
    }

    @Override
    public ArrayList<cActivityModel> getActivitiesByID(long logFrameID) {
        return null;
    }

    @Override
    public ArrayList<cInputModel> getInputsByID(long logFrameID) {
        return null;
    }

    @Override
    public ArrayList<cQuestionModel> getQuestionsByID(long logFrameID) {
        return null;
    }

    @Override
    public ArrayList<cIndicatorModel> getIndicatorsByID(long logFrameID) {
        return null;
    }

    @Override
    public ArrayList<cRaidModel> getRaidsByID(long logFrameID) {
        return null;
    }

    @Override
    public boolean deleteLogFrame(String logFrameID) {
        return false;
    }

    @Override
    public boolean deleteSubLogFrame(long logFrameID) {
        return false;
    }

    @Override
    public boolean deleteLogFrames() {
        return false;
    }
}

//    ##################################### CREATE ACTIONS #####################################
//    public void createUserWithEmailAndPassword(String firstname, String surname, String userEmail,
//                                               String userPassword,
//                                               iSignUpRepositoryCallback callback) {
//        firebaseAuth.createUserWithEmailAndPassword(userEmail, userPassword)
//                .addOnCompleteListener(task -> {
//                    if (task.isSuccessful()) {
//                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//
//                        assert user != null;
//                        if (!user.isEmailVerified()) {
//                            /* send an email verification */
//                            sendEmailVerification(user);
//
//                            /* update the profile in the firebase */
//                            UserProfileChangeRequest profileUpdates;
//                            profileUpdates = new UserProfileChangeRequest.Builder()
//                                    .setDisplayName(firstname + " " + surname).build();
//                            user.updateProfile(profileUpdates).addOnCompleteListener(
//                                    updateProfileTask -> {
//                                        if (updateProfileTask.isSuccessful()) {
//                                            Log.d(TAG, "User profile updated.");
//                                        }
//                                    });
//
//                            /* update the user profile in the database */
//                            cUserProfileModel userProfileModel = new cUserProfileModel(
//                                    user.getUid(), firstname, surname, userEmail);
//
//                            // update default date
//                            Date currentDate = new Date();
//                            userProfileModel.setCreatedDate(currentDate);
//                            userProfileModel.setModifiedDate(currentDate);
//
//                            CollectionReference coUsersRef;
//                            coUsersRef = db.collection(cRealtimeHelper.KEY_USERPROFILES);
//                            coUsersRef.document(user.getUid())
//                                    .set(userProfileModel)
//                                    .addOnFailureListener(e -> Log.d(TAG,
//                                            Objects.requireNonNull(e.getLocalizedMessage())));
//
//                            Log.d(TAG, "createUserWithEmailAndPassword:success");
//                            callback.onSignUpSucceeded("Account successfully created.");
//                        }
//                    } else {
//                        Log.d(TAG, "createUserWithEmailAndPassword:failure",
//                                task.getException());
//                        callback.onSignUpFailed("Failed to create a new account. " +
//                                "Try a different email.");
//                    }
//                });
//    }
//
//    public void sendEmailVerification(FirebaseUser user) {
//        //FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//        if (user != null) {
//            user.sendEmailVerification().addOnCompleteListener(task -> {
//                if (task.isSuccessful()) {
//                    Toast.makeText(context, "Verification email sent to " +
//                            user.getEmail(), Toast.LENGTH_LONG).show();
//                } else {
//                    Log.e(TAG, "sendEmailVerification", task.getException());
//                    Toast.makeText(context, "Failed to send verification email.",
//                            Toast.LENGTH_LONG).show();
//                }
//            });
//        }
//    }
//
//    /* ###################################### READ ACTIONS ###################################### */
//
//    @Override
//    public void signInWithEmailAndPassword(String email, String password,
//                                           iSignInRepositoryCallback callback) {
//        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
//            if (task.isSuccessful()) {
//                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//                if (user != null) {
//                    if (!user.isEmailVerified()) {
//                        callback.onSignInFailed("Verification email sent to " +
//                                user.getEmail());
//                        FirebaseAuth.getInstance().signOut();
//                    } else {
//                        Log.d(TAG, "signInWithEmail:success");
//                        callback.onSignInSucceeded();
//                    }
//                } else {
//                    Log.d(TAG, "signInWithEmail:failure ", task.getException());
//                    callback.onSignInFailed("Authentication failed!");
//                }
//            } else {
//                Log.d(TAG, "signInWithEmail:failure ", task.getException());
//                callback.onSignInFailed("Authentication failed!");
//            }
//        });
//    }
//
//    @Override
//    public void signOutWithEmailAndPassword(iSignOutRepositoryCallback callback) {
//        //PreferenceUtil.signOut(this);
//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//        if (user != null) {
//            firebaseAuth.signOut();
//            callback.onSignOutSucceeded("Logged Out");
//        } else {
//            callback.onSignOutFailed("Already Logged Out !!!");
//        }
//    }
//
//    /* ###################################### READ ACTIONS ###################################### */
//
//    @Override
//    public void readUserProfile(iReadUserProfileRepositoryCallback callback) {
//        FirebaseUser user = firebaseAuth.getCurrentUser();
//        if (user != null) {
//            CollectionReference coUserProfileRef = db.collection(cRealtimeHelper.KEY_USERPROFILES);
//            coUserProfileRef.document(user.getUid()).get()
//                    .addOnCompleteListener(task -> {
//                        if (task.isSuccessful()) {
//                            DocumentSnapshot doc = task.getResult();
//                            if (doc != null) {
//                                cUserProfileModel userProfile;
//                                userProfile = doc.toObject(cUserProfileModel.class);
//                                callback.onReadUserProfileSucceeded(userProfile);
//                            }
//                        } else {
//                            callback.onReadUserProfileFailed(
//                                    "Undefined error! Please report to the developer.");
//                        }
//                    })
//                    .addOnFailureListener(e ->
//                            callback.onReadUserProfileFailed("Failure to read user profile!"));
//        } else {
//            callback.onReadUserProfileFailed("Failure to read user profile!");
//        }
//    }
//
//    /* ##################################### UPDATE ACTIONS ##################################### */
//
//    @Override
//    public void updateUserProfile(long userID, int primaryRole, int secondaryRoles, int statusBITS,
//                                  cUserProfileModel userProfileModel,
//                                  iUpdateUserProfileRepositoryCallback callback) {
//
//        FirebaseUser user = firebaseAuth.getCurrentUser();
//
//        if (user != null) {
//            userProfileModel.setEmail(user.getEmail());
//            CollectionReference coUserProfileRef = db.collection(cRealtimeHelper.KEY_USERPROFILES);
//            coUserProfileRef.document(user.getUid()).set(userProfileModel)
//                    .addOnCompleteListener(task -> {
//                        if (task.isSuccessful()) {
//                            callback.onUpdateUserProfileSucceeded("Successfully Updated");
//                        } else {
//                            callback.onUpdateUserProfileFailed(
//                                    "Undefined error! Talk to the Admin");
//                        }
//                    })
//                    .addOnFailureListener(e -> callback.onUpdateUserProfileFailed(
//                            "Failed to update user profile " + e));
//        } else {
//            callback.onUpdateUserProfileFailed("Failed to update user profile!");
//        }
//    }
//
//    /* ##################################### DELETE ACTIONS ##################################### */


//    //fixme: to move to repository implementation
//    private static String getCellAsString(Row row, int c, FormulaEvaluator formulaEvaluator) {
//        String value = "";
//        try {
//            Cell cell = row.getCell(c);
//            CellValue cellValue = formulaEvaluator.evaluate(cell);
//            switch (cellValue.getCellType()) {
//                case Cell.CELL_TYPE_BOOLEAN:
//                    value = "" + cellValue.getBooleanValue();
//                    break;
//                case Cell.CELL_TYPE_NUMERIC:
//                    double numericValue = cellValue.getNumberValue();
//                    if (HSSFDateUtil.isCellDateFormatted(cell)) {
//                        double date = cellValue.getNumberValue();
//                        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yy");
//                        value = formatter.format(HSSFDateUtil.getJavaDate(date));
//                    } else {
//                        value = "" + numericValue;
//                    }
//                    break;
//                case Cell.CELL_TYPE_STRING:
//                    value = "" + cellValue.getStringValue();
//                    break;
//                default:
//                    break;
//            }
//        } catch (NullPointerException e) {
//            /* proper error handling should be here */
//            Log.d(TAG, e.toString());
//        }
//        return value;
//    }
//
//    public static void readExcel(File file) throws FileNotFoundException {
//        if (file == null) {
//            Log.e("NullFile", "read Excel Error, file is empty");
//            return;
//        }
//        InputStream stream = new FileInputStream(file);
//        try {
//            XSSFWorkbook workbook = new XSSFWorkbook(stream);
//            XSSFSheet sheet = workbook.getSheetAt(0);
//            int rowsCount = sheet.getPhysicalNumberOfRows();
//            FormulaEvaluator formulaEvaluator = workbook.getCreationHelper().createFormulaEvaluator();
//            for (int r = 0; r < rowsCount; r++) {
//                Row row = sheet.getRow(r);
//                int cellsCount = row.getPhysicalNumberOfCells();
//                //Read one line at a time
//                for (int c = 0; c < cellsCount; c++) {
//                    //Convert the contents of each grid to a string
//                    String value = getCellAsString(row, c, formulaEvaluator);
//                    String cellInfo = "r:" + r + "; c:" + c + "; v:" + value;
//                    Log.d(TAG, cellInfo);
//                }
//            }
//        } catch (Exception e) {
//            /* proper exception handling to be here */
//            Log.e(TAG, e.toString());
//        }
//
//    }

//        for (int i = 0; i < logFrameModels.size(); i++) {
//            String parentID = logFrameModels.get(i).getLogFrameServerID();
//
//            // update parent logframes
//            if (logFrameModels.get(i).getParentServerID().equals("0")) {
//                logFrameModels.get(i).setParentServerID(null);
//            }
//
//            // update child logframes
//            for (int j = 0; j < logFrameModels.size(); j++) {
//                if (parentID.equals(logFrameModels.get(j).getParentServerID())) {
//                    logFrameModels.get(i).getChildren().add(
//                            logFrameModels.get(j).getLogFrameServerID());
//                }
//            }
//
//
//
//            // add logframe
//            coLogFrameRef.document(logFrameModels.get(i).getLogFrameServerID())
//                    .set(logFrameModels.get(i));
//
//            // add link between frames
//            addComponents(organizationServerID, userServerID, primaryTeamBIT, statusBIT,
//                    logFrameModels.get(i).getLogFrameServerID(), componentSheet);
//
//        }

//EVALUATION

// question types
//    List<cQuestionTypeModel> questionTypeModels = new ArrayList<>();
//        for (Row questionTypeRow : questionTypeSheet) {
//                //just skip the row if row number is 0
//                if (questionTypeRow.getRowNum() == 0) {
//                continue;
//                }
//
//                cQuestionTypeModel questionTypeModel = new cQuestionTypeModel();
//
//                questionTypeModel.setQuestionTypeServerID(String.valueOf(
//                cDatabaseUtils.getCellAsNumeric(questionTypeRow, 0)));
//                questionTypeModel.setName(cDatabaseUtils.getCellAsString(questionTypeRow, 1));
//                questionTypeModel.setDescription(cDatabaseUtils.getCellAsString(questionTypeRow, 2));
//
//                // update common attributes
//                cCommonPropertiesModel commonPropertiesModel;
//                commonPropertiesModel = cDatabaseUtils.getCommonModel(context);
//
//                questionTypeModel.setOrganizationOwnerID(organizationServerID);
//                questionTypeModel.setUserOwnerID(userServerID);
//                questionTypeModel.setTeamOwnerBIT(primaryTeamBIT);
//                questionTypeModel.setUnixpermBITS(commonPropertiesModel.getCunixpermBITS());
//                questionTypeModel.setStatusBIT(statusBIT);
//
//                questionTypeModel.setCreatedDate(now);
//                questionTypeModel.setModifiedDate(now);
//
//                questionTypeModels.add(questionTypeModel);
//                }
//
//                // question grouping
//                List<cQuestionGroupingModel> questionGroupingModels = new ArrayList<>();
//        for (Row questionGroupingRow : questionGroupingSheet) {
//        //just skip the row if row number is 0
//        if (questionGroupingRow.getRowNum() == 0) {
//        continue;
//        }
//
//        cQuestionGroupingModel questionGroupingModel = new cQuestionGroupingModel();
//
//        questionGroupingModel.setQuestionGroupingServerID(String.valueOf(
//        cDatabaseUtils.getCellAsNumeric(questionGroupingRow, 0)));
//        questionGroupingModel.setLabel(cDatabaseUtils.getCellAsString(
//        questionGroupingRow, 1));
//        questionGroupingModel.setName(cDatabaseUtils.getCellAsString(
//        questionGroupingRow, 2));
//        questionGroupingModel.setDescription(cDatabaseUtils.getCellAsString(
//        questionGroupingRow, 3));
//
//        // update common attributes
//        cCommonPropertiesModel commonPropertiesModel;
//        commonPropertiesModel = cDatabaseUtils.getCommonModel(context);
//
//        questionGroupingModel.setOrganizationOwnerID(organizationServerID);
//        questionGroupingModel.setUserOwnerID(userServerID);
//        questionGroupingModel.setTeamOwnerBIT(primaryTeamBIT);
//        questionGroupingModel.setUnixpermBITS(commonPropertiesModel.getCunixpermBITS());
//        questionGroupingModel.setStatusBIT(statusBIT);
//
//        questionGroupingModel.setCreatedDate(now);
//        questionGroupingModel.setModifiedDate(now);
//
//        questionGroupingModels.add(questionGroupingModel);
//        }
//
//        // question criteria
//        List<cCriteriaModel> criteriaModels = new ArrayList<>();
//        for (Row criteriaRow : criteriaSheet) {
//        //just skip the row if row number is 0
//        if (criteriaRow.getRowNum() == 0) {
//        continue;
//        }
//
//        cCriteriaModel criteriaModel = new cCriteriaModel();
//
//        criteriaModel.setCriteriaServerID(String.valueOf(
//        cDatabaseUtils.getCellAsNumeric(criteriaRow, 0)));
//        criteriaModel.setLabel(cDatabaseUtils.getCellAsString(
//        criteriaRow, 1));
//        criteriaModel.setName(cDatabaseUtils.getCellAsString(
//        criteriaRow, 2));
//
//        // update common attributes
//        cCommonPropertiesModel commonPropertiesModel;
//        commonPropertiesModel = cDatabaseUtils.getCommonModel(context);
//
//        criteriaModel.setOrganizationOwnerID(organizationServerID);
//        criteriaModel.setUserOwnerID(userServerID);
//        criteriaModel.setTeamOwnerBIT(primaryTeamBIT);
//        criteriaModel.setUnixpermBITS(commonPropertiesModel.getCunixpermBITS());
//        criteriaModel.setStatusBIT(statusBIT);
//
//        criteriaModel.setCreatedDate(now);
//        criteriaModel.setModifiedDate(now);
//
//        criteriaModels.add(criteriaModel);
//        }
//        Sheet questionTypeSheet = workbook.getSheet(cExcelHelper.SHEET_tblQUESTIONTYPE);
//        Sheet questionGroupingSheet = workbook.getSheet(cExcelHelper.SHEET_tblQUESTIONGROUPING);
//        Sheet criteriaSheet = workbook.getSheet(cExcelHelper.SHEET_tblCRITERIA);
//        Sheet dimensionSheet = workbook.getSheet(cExcelHelper.SHEET_tblDIMENSION);
//        Sheet disaggregationSheet = workbook.getSheet(cExcelHelper.SHEET_tblDISAGGREGATION);