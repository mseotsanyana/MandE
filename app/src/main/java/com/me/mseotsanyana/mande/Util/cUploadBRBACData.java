package com.me.mseotsanyana.mande.Util;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.me.mseotsanyana.mande.BRBAC.BLL.cAddressHandler;
import com.me.mseotsanyana.mande.BRBAC.BLL.cEntityHandler;
import com.me.mseotsanyana.mande.BRBAC.BLL.cMenuHandler;
import com.me.mseotsanyana.mande.BRBAC.BLL.cOperationHandler;
import com.me.mseotsanyana.mande.BRBAC.BLL.cOrganizationHandler;
import com.me.mseotsanyana.mande.BRBAC.BLL.cPermissionHandler;
import com.me.mseotsanyana.mande.BRBAC.BLL.cRoleHandler;
import com.me.mseotsanyana.mande.BRBAC.BLL.cSessionHandler;
import com.me.mseotsanyana.mande.BRBAC.BLL.cSessionManager;
import com.me.mseotsanyana.mande.BRBAC.BLL.cStatusHandler;
import com.me.mseotsanyana.mande.BRBAC.BLL.cUserHandler;
import com.me.mseotsanyana.mande.BRBAC.BLL.cValueHandler;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by mseotsanyana on 2017/09/19.
 */

public class cUploadBRBACData extends AsyncTask<String, Integer, String> {

    //String[] listOfBRBACTables = {"ORGANIZATION","USER","SESSION","VALUE","ROLE","MENU","ENTITY",
    //        "OPERATION","STATUS","PERMISSION","USER_ROLE","SESSION_ROLE","MENU_ROLE"};
            //"PERMISSION_ROLE"};

    String[] listOfBRBACTables = {"ADDRESS","ORGANIZATION","VALUE","USER","SESSION","ROLE","MENU",
                    "PRIVILEGE","ENTITY","OPERATION","STATUS","USER_ROLE","SESSION_ROLE","MENU_ROLE",
                    "PERMISSION"};

    private cAddressHandler addressHandler;
    private cOrganizationHandler organizationHandler;
    private cValueHandler valueHandler;
    private cUserHandler userHandler;
    private cSessionHandler sessionHandler;
    private cRoleHandler roleHandler;
    private cMenuHandler menuHandler;
    private cPermissionHandler privilegeHandler;
    private cEntityHandler entityHandler;
    private cOperationHandler operationHandler;
    private cStatusHandler statusHandler;

    /*
    private cUserRoleHandler userRoleHandler;
    private cSessionRoleHandler sessionRoleHandler;
    private cMenuRoleHandler menuRoleHandler;
    private cPrivilegeRoleHandler privilegeRoleHandler;
    private cPrivilegeRoleHandler operationStatusHandler;
    */
    private cPermissionHandler permissionHandler;


    private cPopulateModelsFromExcel populateModelsFromExcel;

    private ProgressDialog progressDialog;

    private Context context;

    private cSessionManager session;

    public cUploadBRBACData(Context context, cSessionManager session){
        this.context = context;
        this.session = session;

        addressHandler = new cAddressHandler(context);
        organizationHandler = new cOrganizationHandler(context, session);
        valueHandler = new cValueHandler(context);
        userHandler = new cUserHandler(context, session);
        sessionHandler = new cSessionHandler(context);
        roleHandler = new cRoleHandler(context, session);
        menuHandler = new cMenuHandler(context);
        privilegeHandler = new cPermissionHandler(context, session);
        entityHandler = new cEntityHandler(context);
        operationHandler = new cOperationHandler(context);
        statusHandler = new cStatusHandler(context);

        /*
        userRoleHandler = new cUserRoleHandler(context, session);
        sessionRoleHandler = new cSessionRoleHandler(context);
        menuRoleHandler = new cMenuRoleHandler(context);
        privilegeRoleHandler = new cPrivilegeRoleHandler(context);
        operationStatus = new cOperationStatusHandler(context);
        */
        permissionHandler = new cPermissionHandler(context, session);
        populateModelsFromExcel = new cPopulateModelsFromExcel(context);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Uploading default settings...");
        progressDialog.setMessage("Please wait.");
        progressDialog.setIndeterminate(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setMax(100);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }


    @Override
    protected String doInBackground(String... params) {
        try {
            AssetManager assetManager = context.getAssets();
            InputStream excelFile = assetManager.open("me_db.xlsx");
            Workbook workbook = new XSSFWorkbook(excelFile);

            int allRows = 0;
            ArrayList<Sheet> sheets = new ArrayList<Sheet>();

            for (int i = 0; i < listOfBRBACTables.length; i++) {
                Sheet sheet = workbook.getSheet(listOfBRBACTables[i]);

                sheets.add(sheet);
                //publishProgress(i);

                // number of rows (for all tables used for checking progress
                allRows = allRows + (sheet.getPhysicalNumberOfRows() - 1);
            }

            int currentRows = 0;
            // loop through the table action_list
            for (int i = 0; i < listOfBRBACTables.length; i++) {
                Sheet sheet = workbook.getSheet(listOfBRBACTables[i]);

                if (sheet == null) {
                    return null;
                }


                switch (i) {
                    case 0:
                        addressHandler.deleteAddresses();
                        Sheet org_address = workbook.getSheet("ORG_ADDRESS");
                        ArrayList<Integer> org = new ArrayList<>();
                        for (Iterator<Row> rit = sheet.iterator(); rit.hasNext(); ) {
                            Row cRow = rit.next();

                            //just skip the row if row number is 0
                            if (cRow.getRowNum() == 0) {
                                continue;
                            }


                            // add the row into the database
                            populateModelsFromExcel.addAddressFromExcel(cRow, org_address);
                            populateAuxiliaryTable(org_address, cRow);

                            // publish the progress after adding a record
                            currentRows++;
                            publishProgress(currentRows * 100 / allRows);
                        }
                        break;

                    case 1:
                        organizationHandler.deleteAllOrganizations();
                        for (Iterator<Row> rit = sheet.iterator(); rit.hasNext(); ) {
                            Row cRow = rit.next();

                            //just skip the row if row number is 0
                            if (cRow.getRowNum() == 0) {
                                continue;
                            }

                            // add the row into the database
                            populateModelsFromExcel.addOrganizationFromExcel(cRow);

                            // publish the progress after adding a record
                            currentRows++;
                            publishProgress(currentRows * 100 / allRows);
                        }
                        break;

                    case 2:
                        valueHandler.deleteAllValues();
                        for (Iterator<Row> rit = sheet.iterator(); rit.hasNext(); ) {
                            Row cRow = rit.next();

                            //just skip the row if row number is 0
                            if (cRow.getRowNum() == 0) {
                                continue;
                            }

                            // add the row into the database
                            populateModelsFromExcel.addValueFromExcel(cRow);

                            // publish the progress after adding a record
                            currentRows++;
                            publishProgress(currentRows * 100 / allRows);
                        }
                        break;

                    case 3:
                        userHandler.deleteAllUsers();
                        for (Iterator<Row> rit = sheet.iterator(); rit.hasNext(); ) {
                            Row cRow = rit.next();

                            //just skip the row if row number is 0
                            if (cRow.getRowNum() == 0) {
                                continue;
                            }

                            // add the row into the database
                            populateModelsFromExcel.addUserFromExcel(cRow);

                            // publish the progress after adding a record
                            currentRows++;
                            publishProgress(currentRows * 100 / allRows);
                        }
                        break;

                    case 4:
                        sessionHandler.deleteAllSessions();
                        for (Iterator<Row> rit = sheet.iterator(); rit.hasNext(); ) {
                            Row cRow = rit.next();

                            //just skip the row if row number is 0
                            if (cRow.getRowNum() == 0) {
                                continue;
                            }

                            // add the row into the database
                            populateModelsFromExcel.addSessionFromExcel(cRow);

                            // publish the progress after adding a record
                            currentRows++;
                            publishProgress(currentRows * 100 / allRows);
                        }
                        break;

                    case 5:
                        roleHandler.deleteAllRoles();
                        for (Iterator<Row> rit = sheet.iterator(); rit.hasNext(); ) {
                            Row cRow = rit.next();

                            //just skip the row if row number is 0
                            if (cRow.getRowNum() == 0) {
                                continue;
                            }

                            // add the row into the database
                            populateModelsFromExcel.addRoleFromExcel(cRow);

                            // publish the progress after adding a record
                            currentRows++;
                            publishProgress(currentRows * 100 / allRows);
                        }
                        break;

                    case 6:
                        menuHandler.deleteAllMenuItems();
                        for (Iterator<Row> rit = sheet.iterator(); rit.hasNext(); ) {
                            Row cRow = rit.next();

                            //just skip the row if row number is 0
                            if (cRow.getRowNum() == 0) {
                                continue;
                            }

                            // add the row into the database
                            populateModelsFromExcel.addMenuFromExcel(cRow);

                            // publish the progress after adding a record
                            currentRows++;
                            publishProgress(currentRows * 100 / allRows);
                        }
                        break;

                    case 7:
                        privilegeHandler.deleteAllPrivilegeItems();
                        for (Iterator<Row> rit = sheet.iterator(); rit.hasNext(); ) {
                            Row cRow = rit.next();

                            //just skip the row if row number is 0
                            if (cRow.getRowNum() == 0) {
                                continue;
                            }

                            // add the row into the database
                            populateModelsFromExcel.addPrivilegeFromExcel(cRow);

                            // publish the progress after adding a record
                            currentRows++;
                            publishProgress(currentRows * 100 / allRows);
                        }
                        break;

                    case 8:
                        entityHandler.deleteAllEntities();
                        for (Iterator<Row> rit = sheet.iterator(); rit.hasNext(); ) {
                            Row cRow = rit.next();

                            //just skip the row if row number is 0
                            if (cRow.getRowNum() == 0) {
                                continue;
                            }

                            // add the row into the database
                            populateModelsFromExcel.addEntityFromExcel(cRow);

                            // publish the progress after adding a record
                            currentRows++;
                            publishProgress(currentRows * 100 / allRows);
                        }
                        break;

                    case 9:
                        operationHandler.deleteAllOperations();
                        for (Iterator<Row> rit = sheet.iterator(); rit.hasNext(); ) {
                            Row cRow = rit.next();

                            //just skip the row if row number is 0
                            if (cRow.getRowNum() == 0) {
                                continue;
                            }

                            // add the row into the database
                            populateModelsFromExcel.addOperationFromExcel(cRow);

                            // publish the progress after adding a record
                            currentRows++;
                            publishProgress(currentRows * 100 / allRows);
                        }
                        break;

                    case 10:
                        statusHandler.deleteAllStatuses();
                        for (Iterator<Row> rit = sheet.iterator(); rit.hasNext(); ) {
                            Row cRow = rit.next();

                            //just skip the row if row number is 0
                            if (cRow.getRowNum() == 0) {
                                continue;
                            }

                            // add the row into the database
                            populateModelsFromExcel.addStatusFromExcel(cRow);

                            // publish the progress after adding a record
                            currentRows++;
                            publishProgress(currentRows * 100 / allRows);
                        }
                        break;

                    case 11:
                        for (Iterator<Row> rit = sheet.iterator(); rit.hasNext(); ) {
                            Row cRow = rit.next();

                            //just skip the row if row number is 0
                            if (cRow.getRowNum() == 0) {
                                continue;
                            }

                            // add the row into the database
                            populateModelsFromExcel.addUserRoleFromExcel(cRow);

                            // publish the progress after adding a record
                            currentRows++;
                            publishProgress(currentRows * 100 / allRows);
                        }
                        break;

                    case 12:
                        for (Iterator<Row> rit = sheet.iterator(); rit.hasNext(); ) {
                            Row cRow = rit.next();

                            //just skip the row if row number is 0
                            if (cRow.getRowNum() == 0) {
                                continue;
                            }

                            // add the row into the database
                            populateModelsFromExcel.addSessionRoleFromExcel(cRow);

                            // publish the progress after adding a record
                            currentRows++;
                            publishProgress(currentRows * 100 / allRows);
                        }
                        break;

                    case 13:
                        for (Iterator<Row> rit = sheet.iterator(); rit.hasNext(); ) {
                            Row cRow = rit.next();

                            //just skip the row if row number is 0
                            if (cRow.getRowNum() == 0) {
                                continue;
                            }

                            // add the row into the database
                            populateModelsFromExcel.addMenuRoleFromExcel(cRow);

                            // publish the progress after adding a record
                            currentRows++;
                            publishProgress(currentRows * 100 / allRows);
                        }
                        break;

                    case 14:
                        permissionHandler.deleteAllPermissions();
                        for (Iterator<Row> rit = sheet.iterator(); rit.hasNext(); ) {
                            Row cRow = rit.next();

                            //just skip the row if row number is 0
                            if (cRow.getRowNum() == 0) {
                                continue;
                            }

                            // add the row into the database
                            populateModelsFromExcel.addPermissionFromExcel(cRow);

                            // publish the progress after adding a record
                            currentRows++;
                            publishProgress(currentRows * 100 / allRows);
                        }
                        break;

                    default:
                        break;
                }
            }

        } catch (Exception e) {
            Log.e("Error: ", e.getMessage());
            //e.printStackTrace();
        }

        return null;

    }

    @Override
    protected void onProgressUpdate(Integer... progress) {
        // Set progress percentage
        progressDialog.setProgress(progress[0]);
        //Toast.makeText(context, "tables = "+progress[0], Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        if (progressDialog != null) {
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
                Toast.makeText(context, "Uploading complete", Toast.LENGTH_LONG).show();
            }
        }

    }

}

/*
    @Override
    protected String doInBackground(String... params) {
        try {
            AssetManager assetManager = context.getAssets();
            InputStream excelFile = assetManager.open("me_db.xlsx");
            Workbook workbook = new XSSFWorkbook(excelFile);


            int allRows = 0;
            ArrayList<Sheet> sheets = new ArrayList<Sheet>();

            for (int i = 0; i < listOfBRBACTables.length; i++) {
                Sheet sheet = workbook.getSheet(listOfBRBACTables[i]);

                sheets.add(sheet);
                //publishProgress(i);

                // number of rows (for all tables used for checking progress
                allRows = allRows + (sheet.getPhysicalNumberOfRows() - 1);
            }

            // clear all tables
            // grandparent tables
            organizationHandler.deleteAllOrganizations();
            entityHandler.deleteAllEntities();
            operationHandler.deleteAllOperations();
            statusHandler.deleteAllStatuses();

            // parent tables
            menuHandler.deleteAllMenuItems();
            userHandler.deleteAllUsers();
            sessionHandler.deleteAllSessions();
            roleHandler.deleteAllRoles();
            permissionHandler.deleteAllPermissions();

            int currentRows = 0;
            // loop through the table action_list
            for (int i = 0; i < listOfBRBACTables.length; i++) {
                Sheet sheet = workbook.getSheet(listOfBRBACTables[i]);

                if (sheet == null) {
                    return null;
                }


                switch (i) {
                    case 0:
                        for (Iterator<Row> rit = sheet.iterator(); rit.hasNext(); ) {
                            Row cRow = rit.next();

                            //just skip the row if row number is 0
                            if (cRow.getRowNum() == 0) {
                                continue;
                            }
                            // populate data into user domain
                            cOrganizationDomain organizationDomain = setDomainsFromExcel.getOrganizationFromExcel(cRow);
                            // add the row into the database
                            organizationHandler.addOrganizationFromExcel(organizationDomain);

                            // publish the progress after adding a record
                            currentRows++;
                            publishProgress(currentRows * 100 / allRows);
                        }
                        break;

                    case 1:
                        for (Iterator<Row> rit = sheet.iterator(); rit.hasNext(); ) {
                            Row cRow = rit.next();

                            //just skip the row if row number is 0
                            if (cRow.getRowNum() == 0) {
                                continue;
                            }
                            // populate data into user domain
                            cUserDomain userDomain = setDomainsFromExcel.getUserFromExcel(cRow);
                            // add the row into the database
                            userHandler.addUserFromExcel(userDomain);

                            // publish the progress after adding a record
                            currentRows++;
                            publishProgress(currentRows * 100 / allRows);
                        }
                        break;

                    case 2:
                        for (Iterator<Row> rit = sheet.iterator(); rit.hasNext(); ) {
                            Row cRow = rit.next();

                            //just skip the row if row number is 0
                            if (cRow.getRowNum() == 0) {
                                continue;
                            }
                            // populate data into user domain
                            cSessionDomain sessionDomain = setDomainsFromExcel.getSessionFromExcel(cRow);
                            // add the row into the database
                            sessionHandler.addSessionFromExcel(sessionDomain);

                            // publish the progress after adding a record
                            currentRows++;
                            publishProgress(currentRows * 100 / allRows);
                        }
                        break;

                    case 3:
                        for (Iterator<Row> rit = sheet.iterator(); rit.hasNext(); ) {
                            Row cRow = rit.next();

                            //just skip the row if row number is 0
                            if (cRow.getRowNum() == 0) {
                                continue;
                            }
                            // populate data into user domain
                            cValueDomain valueDomain = setDomainsFromExcel.getValueFromExcel(cRow);
                            // add the row into the database
                            valueHandler.addValueFromExcel(valueDomain);

                            // publish the progress after adding a record
                            currentRows++;
                            publishProgress(currentRows * 100 / allRows);
                        }
                        break;

                    case 4:
                        for (Iterator<Row> rit = sheet.iterator(); rit.hasNext(); ) {
                            Row cRow = rit.next();

                            //just skip the row if row number is 0
                            if (cRow.getRowNum() == 0) {
                                continue;
                            }
                            // populate data into user domain
                            cRoleDomain roleDomain = setDomainsFromExcel.getRoleFromExcel(cRow);
                            // add the row into the database
                            roleHandler.addRoleFromExcel(roleDomain);

                            // publish the progress after adding a record
                            currentRows++;
                            publishProgress(currentRows * 100 / allRows);
                        }
                        break;

                    case 5:
                        for (Iterator<Row> rit = sheet.iterator(); rit.hasNext(); ) {
                            Row cRow = rit.next();

                            //just skip the row if row number is 0
                            if (cRow.getRowNum() == 0) {
                                continue;
                            }
                            // populate data into user domain
                            cMenuDomain menuDomain = setDomainsFromExcel.getMenuFromExcel(cRow);
                            // add the row into the database
                            menuHandler.addMenuFromExcel(menuDomain);

                            // publish the progress after adding a record
                            currentRows++;
                            publishProgress(currentRows * 100 / allRows);
                        }
                        break;

                    case 6:
                        for (Iterator<Row> rit = sheet.iterator(); rit.hasNext(); ) {
                            Row cRow = rit.next();

                            //just skip the row if row number is 0
                            if (cRow.getRowNum() == 0) {
                                continue;
                            }
                            // populate data into user domain
                            cEntityDomain entityDomain = setDomainsFromExcel.getEntityFromExcel(cRow);
                            // add the row into the database
                            entityHandler.addEntityFromExcel(entityDomain);

                            // publish the progress after adding a record
                            currentRows++;
                            publishProgress(currentRows * 100 / allRows);
                        }
                        break;

                    case 7:
                        for (Iterator<Row> rit = sheet.iterator(); rit.hasNext(); ) {
                            Row cRow = rit.next();

                            //just skip the row if row number is 0
                            if (cRow.getRowNum() == 0) {
                                continue;
                            }
                            // populate data into user domain
                            cOperationDomain operationDomain = setDomainsFromExcel.getOperationFromExcel(cRow);
                            // add the row into the database
                            operationHandler.addOperationFromExcel(operationDomain);

                            // publish the progress after adding a record
                            currentRows++;
                            publishProgress(currentRows * 100 / allRows);
                        }
                        break;

                    case 8:
                        for (Iterator<Row> rit = sheet.iterator(); rit.hasNext(); ) {
                            Row cRow = rit.next();

                            //just skip the row if row number is 0
                            if (cRow.getRowNum() == 0) {
                                continue;
                            }
                            // populate data into user domain
                            cStatusDomain statusDomain = setDomainsFromExcel.getStatusFromExcel(cRow);
                            // add the row into the database
                            statusHandler.addStatusFromExcel(statusDomain);

                            // publish the progress after adding a record
                            currentRows++;
                            publishProgress(currentRows * 100 / allRows);
                        }
                        break;

                    case 9:
                        for (Iterator<Row> rit = sheet.iterator(); rit.hasNext(); ) {
                            Row cRow = rit.next();

                            //just skip the row if row number is 0
                            if (cRow.getRowNum() == 0) {
                                continue;
                            }
                            // populate data into permissions domain
                            cPermissionDomain permissionDomain = setDomainsFromExcel.getPermissionFromExcel(cRow);
                            // add the row into the database
                            permissionHandler.addPermissionFromExcel(permissionDomain);

                            // publish the progress after adding a record
                            currentRows++;
                            publishProgress(currentRows * 100 / allRows);
                        }
                        break;

                    case 10:
                        for (Iterator<Row> rit = sheet.iterator(); rit.hasNext(); ) {
                            Row cRow = rit.next();

                            //just skip the row if row number is 0
                            if (cRow.getRowNum() == 0) {
                                continue;
                            }
                            // populate data into user domain
                            cUserRoleDomain userRoleDomain = setDomainsFromExcel.getUserRoleFromExcel(cRow);
                            // add the row into the database
                            userRoleHandler.addUserRoleFromExcel(userRoleDomain);

                            // publish the progress after adding a record
                            currentRows++;
                            publishProgress(currentRows * 100 / allRows);
                        }
                        break;

                    case 11:
                        for (Iterator<Row> rit = sheet.iterator(); rit.hasNext(); ) {
                            Row cRow = rit.next();

                            //just skip the row if row number is 0
                            if (cRow.getRowNum() == 0) {
                                continue;
                            }
                            // populate data into session role domain
                            cSessionRoleDomain sessionRoleDomain = setDomainsFromExcel.getSessionRoleFromExcel(cRow);
                            // add the row into the database
                            sessionRoleHandler.addSessionRole(sessionRoleDomain);

                            // publish the progress after adding a record
                            currentRows++;
                            publishProgress(currentRows * 100 / allRows);
                        }
                        break;

                    case 12:
                        for (Iterator<Row> rit = sheet.iterator(); rit.hasNext(); ) {
                            Row cRow = rit.next();

                            //just skip the row if row number is 0
                            if (cRow.getRowNum() == 0) {
                                continue;
                            }
                            // populate data into user domain
                            cMenuRoleDomain menuRoleDomain = setDomainsFromExcel.getMenuRoleFromExcel(cRow);
                            // add the row into the database
                            menuRoleHandler.addMenuRoleFromExcel(menuRoleDomain);

                            // publish the progress after adding a record
                            currentRows++;
                            publishProgress(currentRows * 100 / allRows);
                        }
                        break;

                    default:
                        break;
                }
            }
        } catch (Exception e) {
            Log.e("Error: ", e.getMessage());
            //e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... progress) {
        // Set progress percentage
        progressDialog.setProgress(progress[0]);
        //Toast.makeText(context, "tables = "+progress[0], Toast.LENGTH_LONG).show();
    }


    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        if (progressDialog != null) {
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
                Toast.makeText(context, "Uploading Bitwise Role Access Control Data complete", Toast.LENGTH_LONG).show();
            }
        }
    }*/






/*
    public void uploadMEDataFromExcel(){
        cUploadBRBACData uploadMEData = new cUploadBRBACData(context);
        uploadMEData.execute();
    }
*/