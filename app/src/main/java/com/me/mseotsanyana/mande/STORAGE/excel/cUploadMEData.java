package com.me.mseotsanyana.mande.STORAGE.excel;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.widget.Toast;

import com.me.mseotsanyana.mande.BRBAC.BLL.cSessionManager;
import com.me.mseotsanyana.mande.PPMER.BLL.domain.cActivityDomain;
import com.me.mseotsanyana.mande.PPMER.BLL.interactors.cActivityInterator;
import com.me.mseotsanyana.mande.UTILITY.BLL.cGoalDomain;
import com.me.mseotsanyana.mande.PPMER.BLL.interactors.cImpactInterator;
import com.me.mseotsanyana.mande.PPMER.BLL.domain.cImpactDomain;
import com.me.mseotsanyana.mande.BRBAC.BLL.cOrganizationDomain;
import com.me.mseotsanyana.mande.BRBAC.BLL.cOrganizationHandler;
import com.me.mseotsanyana.mande.PPMER.BLL.domain.cOutcomeDomain;
import com.me.mseotsanyana.mande.PPMER.BLL.interactors.cOutcomeInterator;
import com.me.mseotsanyana.mande.UTILITY.BLL.cOutcomeOutputDomain;
import com.me.mseotsanyana.mande.UTILITY.BLL.cOutcomeOutputHandler;
import com.me.mseotsanyana.mande.UTILITY.BLL.cOutputActivityDomain;
import com.me.mseotsanyana.mande.UTILITY.BLL.cOutputActivityHandler;
import com.me.mseotsanyana.mande.PPMER.BLL.domain.cOutputDomain;
import com.me.mseotsanyana.mande.PPMER.BLL.interactors.cOutputInteractor;
import com.me.mseotsanyana.mande.UTILITY.BLL.cProjectDomain;
import com.me.mseotsanyana.mande.UTILITY.BLL.cProjectHandler;
import com.me.mseotsanyana.mande.UTILITY.BLL.cProjectOutcomeDomain;
import com.me.mseotsanyana.mande.UTILITY.BLL.cProjectOutcomeHandler;
import com.me.mseotsanyana.mande.UTILITY.BLL.cSpecificAimDomain;
import com.me.mseotsanyana.mande.UTILITY.BLL.cSpecificAimHandler;
import com.me.mseotsanyana.mande.BRBAC.BLL.cValueDomain;
import com.me.mseotsanyana.mande.BRBAC.BLL.cValueHandler;
import com.me.mseotsanyana.mande.UTILITY.cSetDomainsFromExcel;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by mseotsanyana on 2017/09/18.
 */

public class cUploadMEData {
    private final Context context;
    private cSessionManager session;

    private cOrganizationDomain organizationDomain;
    private cValueDomain valueDomain;
    private cGoalDomain goalDomain;
    private cSpecificAimDomain specificAimDomain;
    private cProjectDomain projectDomain;
    private cImpactDomain objectiveDomain;
    private cOutcomeDomain outcomeDomain;
    private cOutputDomain outputDomain;
    private cActivityDomain activityDomain;

    private cProjectOutcomeDomain projectOutcomeDomain;
    private cOutcomeOutputDomain outcomeOutputDomain;
    private cOutputActivityDomain outputActivityDomain;

    private cOrganizationHandler organizationHandler;
    private cValueHandler valueHandler;
    private cImpactInterator goalHandler;
    private cSpecificAimHandler specificAimHandler;
    private cProjectHandler projectHandler;
    //private cObjectiveInteractor objectiveHandler;
    private cOutcomeInterator outcomeHandler;
    private cOutputInteractor outputHandler;
    private cActivityInterator activityHandler;

    private cProjectOutcomeHandler projectOutcomeHandler;
    private cOutcomeOutputHandler outcomeOutputHandler;
    private cOutputActivityHandler outputActivityHandler;

    private ProgressDialog pd;
    private cSetDomainsFromExcel uploadDemoData;

    public cUploadMEData(Context context, cSessionManager session){
        // for adding data into database from excel
        this.context = context;
        this.session = session;

        organizationHandler = new cOrganizationHandler(context, session);
        valueHandler = new cValueHandler(context);
        goalHandler = new cImpactInterator(context);
        specificAimHandler = new cSpecificAimHandler(context);
        projectHandler = new cProjectHandler(context);
       // objectiveHandler = new cObjectiveInteractor(context);
        outcomeHandler = new cOutcomeInterator(context);
        outputHandler = new cOutputInteractor(context);
        activityHandler = new cActivityInterator(context);

        projectOutcomeHandler = new cProjectOutcomeHandler(context);
        outcomeOutputHandler = new cOutcomeOutputHandler(context);
        outputActivityHandler = new cOutputActivityHandler(context);
    }

    class cUploadRBACData extends AsyncTask<String, Integer, String> {

        String[] listOfRBACTables = {"USER","ROLE","ACTION","OBJECT","TYPE","STATUS","USER_ROLE","RAOTS"};
        private Context context;

        public cUploadRBACData(Context context){
            this.context = context;

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(context);
            pd.setTitle("Uploading...");
            pd.setMessage("Please wait.");
            pd.setIndeterminate(false);
            pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            pd.setMax(100);
            pd.setCancelable(false);
            pd.show();
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                AssetManager assetManager = context.getAssets();
                InputStream excelFile = assetManager.open("me_db.xlsx");
                Workbook workbook = new XSSFWorkbook(excelFile);

                int allRows = 0;
                ArrayList<Sheet> sheets = new ArrayList<Sheet>();
                for (int i = 0; i < listOfRBACTables.length; i++) {
                    Sheet sheet = workbook.getSheet(listOfRBACTables[i]);
                    sheets.add(sheet);

                    // number of rows (for all tables used for checking progress
                    allRows = allRows + (sheet.getPhysicalNumberOfRows() - 1);
                }

                int currentRows = 0;
                // loop through the table action_list
                for (int i = 0; i < listOfRBACTables.length; i++) {
                    Sheet sheet = workbook.getSheet(listOfRBACTables[i]);

                    if (sheet == null) {
                        return null;
                    }
/*
                    switch (i) {
                        case 0:
                            //delete all organizations
                            organizationHandler.deleteAllOrganizations();

                            for (Iterator<Row> rit = sheet.iterator(); rit.hasNext(); ) {
                                Row cRow = rit.next();

                                //just skip the row if row number is 0
                                if (cRow.getRowNum() == 0) {
                                    continue;
                                }

                                // populate data into organization domain
                                organizationDomain = uploadDemoData.getOrganizationFromExcel(cRow);

                                // add the row into the database
                                organizationHandler.addOrganizationFromExcel(organizationDomain);

                                // publish the progress after adding a record
                                currentRows++;
                                publishProgress(currentRows * 100 / allRows);
                            }
                            break;

                        case 1:
                            // delete all values
                            valueHandler.deleteAllValues();

                            for (Iterator<Row> rit = sheet.iterator(); rit.hasNext(); ) {
                                Row cRow = rit.next();

                                //just skip the row if row number is 0
                                if (cRow.getRowNum() == 0) {
                                    continue;
                                }

                                // populate data into value domain
                                valueDomain = uploadDemoData.getValueFromExcel(cRow);

                                // add the row into the database
                                valueHandler.addValueFromExcel(valueDomain);

                                // publish the progress after adding a record
                                currentRows++;
                                publishProgress(currentRows * 100 / allRows);
                            }
                            break;

                        case 2:
                            // delete all goals (overall aims)
                            goalHandler.deleteAllGoals();

                            for (Iterator<Row> rit = sheet.iterator(); rit.hasNext(); ) {
                                Row cRow = rit.next();

                                //just skip the row if row number is 0
                                if (cRow.getRowNum() == 0) {
                                    continue;
                                }

                                // populate data into value domain
                                goalDomain = uploadDemoData.getGoalFromExcel(cRow);

                                // add the row into the database
                                goalHandler.addGoalFromExcel(goalDomain);

                                // publish the progress after adding a record
                                currentRows++;
                                publishProgress(currentRows * 100 / allRows);
                            }
                            break;

                        case 3:
                            // delete all specific aim
                            specificAimHandler.deleteAllSpecificAims();

                            for (Iterator<Row> rit = sheet.iterator(); rit.hasNext(); ) {
                                Row cRow = rit.next();

                                //just skip the row if row number is 0
                                if (cRow.getRowNum() == 0) {
                                    continue;
                                }

                                // populate data into specific aim domain
                                specificAimDomain = uploadDemoData.getSpecificAimFromExcel(cRow);

                                // add the row into the database
                                specificAimHandler.addSpecificAimFromExcel(specificAimDomain);

                                // publish the progress after adding a record
                                currentRows++;
                                publishProgress(currentRows * 100 / allRows);
                            }
                            break;

                        case 4:
                            // delete all projects
                            projectHandler.deleteAllProjects();

                            for (Iterator<Row> rit = sheet.iterator(); rit.hasNext(); ) {
                                Row cRow = rit.next();

                                //just skip the row if row number is 0
                                if (cRow.getRowNum() == 0) {
                                    continue;
                                }

                                // populate data into project domain
                                projectDomain = uploadDemoData.getProjectFromExcel(cRow);

                                // add the row into the database
                                projectHandler.addProject(projectDomain);

                                // publish the progress after adding a record
                                currentRows++;
                                publishProgress(currentRows * 100 / allRows);
                            }
                            break;

                        case 5:
                            // delete all objectives
                            objectiveHandler.deleteAllObjectives();

                            for (Iterator<Row> rit = sheet.iterator(); rit.hasNext(); ) {
                                Row cRow = rit.next();

                                //just skip the row if row number is 0
                                if (cRow.getRowNum() == 0) {
                                    continue;
                                }

                                // populate data into project domain
                                objectiveDomain = uploadDemoData.getObjectiveFromExcel(cRow);

                                // add the row into the database
                                objectiveHandler.addObjective(objectiveDomain);

                                // publish the progress after adding a record
                                currentRows++;
                                publishProgress(currentRows * 100 / allRows);
                            }
                            break;

                        case 6:
                            // delete all outcomes
                            outcomeHandler.deleteAllOutcomes();

                            for (Iterator<Row> rit = sheet.iterator(); rit.hasNext(); ) {
                                Row cRow = rit.next();

                                //just skip the row if row number is 0
                                if (cRow.getRowNum() == 0) {
                                    continue;
                                }

                                // populate data into outcome domain
                                outcomeDomain = uploadDemoData.getOutcomeFromExcel(cRow);

                                // add the row into the database
                                outcomeHandler.addOutcome(outcomeDomain);

                                // publish the progress after adding a record
                                currentRows++;
                                publishProgress(currentRows * 100 / allRows);
                            }
                            break;

                        case 7:
                            // delete all outputs
                            outputHandler.deleteAllOutputs();

                            for (Iterator<Row> rit = sheet.iterator(); rit.hasNext(); ) {
                                Row cRow = rit.next();

                                //just skip the row if row number is 0
                                if (cRow.getRowNum() == 0) {
                                    continue;
                                }

                                // populate data into output domain
                                outputDomain = uploadDemoData.getOutputFromExcel(cRow);

                                // add the row into the database:
                                outputHandler.addOutput(outputDomain);

                                // publish the progress after adding a record
                                currentRows++;
                                publishProgress(currentRows * 100 / allRows);
                            }
                            break;

                        case 8:
                            // delete all activity
                            activityHandler.deleteAllActivities();

                            for (Iterator<Row> rit = sheet.iterator(); rit.hasNext(); ) {
                                Row cRow = rit.next();

                                //just skip the row if row number is 0
                                if (cRow.getRowNum() == 0) {
                                    continue;
                                }

                                // populate data into project domain
                                activityDomain = uploadDemoData.getActivityFromExcel(cRow);

                                // add the row into the database:
                                activityHandler.addActivity(activityDomain);

                                // publish the progress after adding a record
                                currentRows++;
                                publishProgress(currentRows * 100 / allRows);
                            }
                            break;

                        case 9:
                            // delete all project outcome
                            //projectOutcomeHandler.d();

                            for (Iterator<Row> rit = sheet.iterator(); rit.hasNext(); ) {
                                Row cRow = rit.next();

                                //just skip the row if row number is 0
                                if (cRow.getRowNum() == 0) {
                                    continue;
                                }

                                // populate data into project domain
                                projectOutcomeDomain = uploadDemoData.getProjectOutcomeFromExcel(cRow);

                                // add the row into the database:
                                projectOutcomeHandler.addProjectOutcome(projectOutcomeDomain);

                                // publish the progress after adding a record
                                currentRows++;
                                publishProgress(currentRows * 100 / allRows);
                            }
                            break;

                        case 10:
                            // delete all outcome output
                            //outcomeHandler.deleteAllOutcomes();
                            for (Iterator<Row> rit = sheet.iterator(); rit.hasNext(); ) {
                                Row cRow = rit.next();

                                //just skip the row if row number is 0
                                if (cRow.getRowNum() == 0) {
                                    continue;
                                }

                                // populate data into project domain
                                outcomeOutputDomain = uploadDemoData.getOutcomeOutputFromExcel(cRow);

                                // add the row into the database:
                                outcomeOutputHandler.addOutcomeOutput(outcomeOutputDomain);

                                // publish the progress after adding a record
                                currentRows++;
                                publishProgress(currentRows * 100 / allRows);
                            }
                            break;

                        case 11:
                            // delete all output activity
                            //outputActivityHandler.d();

                            for (Iterator<Row> rit = sheet.iterator(); rit.hasNext(); ) {
                                Row cRow = rit.next();

                                //just skip the row if row number is 0
                                if (cRow.getRowNum() == 0) {
                                    continue;
                                }

                                // populate data into project domain
                                outputActivityDomain = uploadDemoData.getOutputActivityFromExcel(cRow);

                                // add the row into the database:
                                outputActivityHandler.addOutputActivity(outputActivityDomain);

                                // publish the progress after adding a record
                                currentRows++;
                                publishProgress(currentRows * 100 / allRows);
                            }
                            break;

                        case 12:
                            cProjectDomain projectDomain = new cProjectDomain();
                            //Calendar start_date   = projectDomain.getStartDate();
                            //Date end_date     = projectDomain.getCloseDate();
                            //Date current_date = start_date;

                            //while (current_date <= end_date){

                            //}

                            break;

                        default:
                            break;
                    }
                    */
                }
            } catch (Exception e) {
                //Log.e("Error: ", e.getMessage());
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            // Set progress percentage
            pd.setProgress(progress[0]);
            //Toast.makeText(getApplicationContext(), "tables = "+progress[0], Toast.LENGTH_LONG).show();
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (pd != null) {
                if (pd.isShowing()) {
                    pd.dismiss();
                    Toast.makeText(context, "Uploading complete", Toast.LENGTH_LONG).show();
                }
            }
        }
    }
/*

    public void uploadMEDataFromExcel(){
        cUploadSessionData uploadMEData = new cUploadSessionData(context);
        uploadMEData.execute();
    }*/
}
