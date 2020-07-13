package com.me.mseotsanyana.mande.UTIL;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.me.mseotsanyana.mande.BLL.interactors.session.address.Impl.cAddressHandler;
import com.me.mseotsanyana.mande.BLL.interactors.session.entity.Impl.cEntityHandler;
import com.me.mseotsanyana.mande.BLL.interactors.session.menu.Impl.cMenuHandler;
import com.me.mseotsanyana.mande.BLL.interactors.session.operation.Impl.cOperationHandler;
import com.me.mseotsanyana.mande.BLL.interactors.session.role.Impl.cRoleHandler;
import com.me.mseotsanyana.mande.BLL.interactors.session.session.Impl.cSessionHandler;
import com.me.mseotsanyana.mande.BLL.interactors.session.status.Impl.cStatusInteractorImpl;
import com.me.mseotsanyana.mande.BLL.interactors.session.user.Impl.cUserHandler;
import com.me.mseotsanyana.mande.BLL.interactors.session.value.Impl.cValueHandler;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by mseotsanyana on 2017/09/19.
 */

public class cUploadSessionData extends AsyncTask<String, Integer, String> {

    String[] sessionTables = {"tblADDRESS","tblORGANIZATION","tblVALUE","tblUSER","tblMENU","tblROLE",
            "tblPRIVILEGE","tblENTITY","tblOPERATION","tblSTATUS","tblSETTINGS","tblNOTIFICATION"};

    /*,
            "tblPRIVILEGE","tblENTITY","tblOPERATION","tblSTATUS","tblORG_ADDRESS","tblUSER_ADDRESS",
            "tblUSER_ROLE","tblMENU_ROLE","tblPERMISSION","tblPERM_STATUS","tblSETTING","tblNOTIFICATION",
            "tblPUBLISHER","tblSUBSCRIBER","tblNOTIFY_SETTING"};*/

    private cAddressHandler addressHandler;
    //private cOrganizationHandler organizationHandler;
    private cValueHandler valueHandler;
    private cUserHandler userHandler;
    private cSessionHandler sessionHandler;
    private cRoleHandler roleHandler;
    private cMenuHandler menuHandler;
    //private cPrivilegeHandler privilegeHandler;
    private cEntityHandler entityHandler;
    private cOperationHandler operationHandler;
    private cStatusInteractorImpl statusHandler;

    /*
    private cUserRoleHandler userRoleHandler;
    private cSessionRoleHandler sessionRoleHandler;
    private cMenuRoleHandler menuRoleHandler;
    private cPrivilegeRoleHandler privilegeRoleHandler;
    private cPrivilegeRoleHandler operationStatusHandler;
    private cPermissionHandler permissionHandler;
    */

    private cSessionModelFromExcel sessionModelFromExcel;

    private ProgressDialog progressDialog;

    private Context context;

    //private cSessionManager session;

    public cUploadSessionData(Context context){
        this.context = context;
        //this.session = session;

        addressHandler = new cAddressHandler(context);
        //organizationHandler = new cOrganizationHandler(context);
        valueHandler = new cValueHandler(context);
        userHandler = new cUserHandler(context);
        menuHandler = new cMenuHandler(context);
        roleHandler = new cRoleHandler(context);
        //privilegeHandler = new cPrivilegeHandler(context, session);
        entityHandler = new cEntityHandler(context);
        operationHandler = new cOperationHandler(context);
        statusHandler = null;//new cStatusInteractorImpl(context);

        /*
        userRoleHandler = new cUserRoleHandler(context, session);
        sessionRoleHandler = new cSessionRoleHandler(context);
        menuRoleHandler = new cMenuRoleHandler(context);
        privilegeRoleHandler = new cPrivilegeRoleHandler(context);
        operationStatus = new cOperationStatusHandler(context);
        permissionHandler = new cPermissionHandler(context, session);
         */
        sessionModelFromExcel = new cSessionModelFromExcel(context);
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
            InputStream excelFile = assetManager.open("SESSION.xlsx");
            Workbook workbook = new XSSFWorkbook(excelFile);

            int allRows = 0;
            ArrayList<Sheet> sheets = new ArrayList<Sheet>();

            for (int i = 0; i < sessionTables.length; i++) {
                Sheet sheet = workbook.getSheet(sessionTables[i]);

                sheets.add(sheet);
                //publishProgress(i);

                // number of rows (for all tables used for checking progress
                allRows = allRows + (sheet.getPhysicalNumberOfRows() - 1);
            }

            int currentRows = 0;
            // loop through the table action_list
            for (int i = 0; i < sessionTables.length; i++) {
                Sheet sheet = workbook.getSheet(sessionTables[i]);

                if (sheet == null) {
                    return null;
                }


                switch (i) {
                    case 0:
                        //addressHandler.deleteAddresses();
                        for (Iterator<Row> rit = sheet.iterator(); rit.hasNext(); ) {
                            Row cRow = rit.next();

                            //just skip the row if row number is 0
                            if (cRow.getRowNum() == 0) {
                                continue;
                            }

                            // add the row into the database
                            sessionModelFromExcel.addAddressFromExcel(cRow);

                            // publish the progress after adding a record
                            currentRows++;
                            publishProgress(currentRows * 100 / allRows);
                        }
                        break;

                    case 1:
                        //organizationHandler.deleteOrganizations();
                        Sheet org_address = workbook.getSheet("tblORG_ADDRESS");
                        Sheet org_beneficiary = workbook.getSheet("tblBENEFICIARY");
                        Sheet org_funder = workbook.getSheet("tblFUNDER");
                        Sheet org_ia = workbook.getSheet("tblIMPLEMENTINGAGENCY");

                        for (Iterator<Row> rit = sheet.iterator(); rit.hasNext(); ) {
                            Row cRow = rit.next();

                            //just skip the row if row number is 0
                            if (cRow.getRowNum() == 0) {
                                continue;
                            }

                            // add the row into the database
                            sessionModelFromExcel.addOrganizationFromExcel(cRow, org_address,
                                    org_beneficiary, org_funder, org_ia);

                            // publish the progress after adding a record
                            currentRows++;
                            publishProgress(currentRows * 100 / allRows);
                        }
                        break;

                    case 2:
                        //valueHandler.deleteValues();
                        for (Iterator<Row> rit = sheet.iterator(); rit.hasNext(); ) {
                            Row cRow = rit.next();

                            //just skip the row if row number is 0
                            if (cRow.getRowNum() == 0) {
                                continue;
                            }

                            // add the row into the database
                            sessionModelFromExcel.addValueFromExcel(cRow);

                            // publish the progress after adding a record
                            currentRows++;
                            publishProgress(currentRows * 100 / allRows);
                        }
                        break;

                    case 3:
                        //userHandler.deleteUsers();
                        Sheet user_address = workbook.getSheet("tblUSER_ADDRESS");
                        for (Iterator<Row> rit = sheet.iterator(); rit.hasNext(); ) {
                            Row cRow = rit.next();

                            //just skip the row if row number is 0
                            if (cRow.getRowNum() == 0) {
                                continue;
                            }

                            // add the row into the database
                            sessionModelFromExcel.addUserFromExcel(cRow, user_address);

                            // publish the progress after adding a record
                            currentRows++;
                            publishProgress(currentRows * 100 / allRows);
                        }
                        break;

                    case 4:
                        //menuHandler.deleteMenuItems();
                        for (Iterator<Row> rit = sheet.iterator(); rit.hasNext(); ) {
                            Row cRow = rit.next();

                            //just skip the row if row number is 0
                            if (cRow.getRowNum() == 0) {
                                continue;
                            }

                            // add the row into the database
                            sessionModelFromExcel.addMenuFromExcel(cRow);

                            // publish the progress after adding a record
                            currentRows++;
                            publishProgress(currentRows * 100 / allRows);
                        }
                        break;

                    case 5:
                        //roleHandler.deleteRoles();
                        Sheet role_users = workbook.getSheet("tblUSER_ROLE");
                        Sheet role_menus = workbook.getSheet("tblMENU_ROLE");

                        for (Iterator<Row> rit = sheet.iterator(); rit.hasNext(); ) {
                            Row cRow = rit.next();

                            //just skip the row if row number is 0
                            if (cRow.getRowNum() == 0) {
                                continue;
                            }

                            // add the row into the database
                            sessionModelFromExcel.addRoleFromExcel(cRow, role_users, role_menus);

                            // publish the progress after adding a record
                            currentRows++;
                            publishProgress(currentRows * 100 / allRows);
                        }
                        break;

                    case 6:
                        //privilegeHandler.deletePrivileges();

                        Sheet priv_permissions = workbook.getSheet("tblPERMISSION");
                        Sheet priv_statuses = workbook.getSheet("tblPRIV_STATUS");

                        for (Iterator<Row> rit = sheet.iterator(); rit.hasNext(); ) {
                            Row cRow = rit.next();

                            //just skip the row if row number is 0
                            if (cRow.getRowNum() == 0) {
                                continue;
                            }

                            // add the row into the database
                            //sessionModelFromExcel.addPrivilegeFromExcel(cRow, priv_permissions, priv_statuses);

                            // publish the progress after adding a record
                            currentRows++;
                            publishProgress(currentRows * 100 / allRows);
                        }
                        break;

                    case 7:
                        //entityHandler.deleteEntities();
                        for (Iterator<Row> rit = sheet.iterator(); rit.hasNext(); ) {
                            Row cRow = rit.next();

                            //just skip the row if row number is 0
                            if (cRow.getRowNum() == 0) {
                                continue;
                            }

                            // add the row into the database
                            sessionModelFromExcel.addEntityFromExcel(cRow);

                            // publish the progress after adding a record
                            currentRows++;
                            publishProgress(currentRows * 100 / allRows);
                        }
                        break;

                    case 8:
                        //operationHandler.deleteOperations();
                        for (Iterator<Row> rit = sheet.iterator(); rit.hasNext(); ) {
                            Row cRow = rit.next();

                            //just skip the row if row number is 0
                            if (cRow.getRowNum() == 0) {
                                continue;
                            }

                            // add the row into the database
                            sessionModelFromExcel.addOperationFromExcel(cRow);

                            // publish the progress after adding a record
                            currentRows++;
                            publishProgress(currentRows * 100 / allRows);
                        }
                        break;

                    case 9:
                        //statusHandler.deleteStatuses();
                        for (Iterator<Row> rit = sheet.iterator(); rit.hasNext(); ) {
                            Row cRow = rit.next();

                            //just skip the row if row number is 0
                            if (cRow.getRowNum() == 0) {
                                continue;
                            }

                            // add the row into the database
                            sessionModelFromExcel.addStatusFromExcel(cRow);

                            // publish the progress after adding a record
                            currentRows++;
                            publishProgress(currentRows * 100 / allRows);
                        }
                        break;
/*
                    case 10:
                        //permissionHandler.deleteAllPermissions();
                        Sheet perm_statuses = workbook.getSheet("tblPERM_STATUS");

                        for (Iterator<Row> rit = sheet.iterator(); rit.hasNext(); ) {
                            Row cRow = rit.next();

                            //just skip the row if row number is 0
                            if (cRow.getRowNum() == 0) {
                                continue;
                            }

                            // add the row into the database
                            sessionModelFromExcel.addPermissionFromExcel(cRow, perm_statuses);

                            // publish the progress after adding a record
                            currentRows++;
                            publishProgress(currentRows * 100 / allRows);
                        }
                        break;
*/
                    case 10:
                        //statusHandler.deleteAllStatuses();
                        for (Iterator<Row> rit = sheet.iterator(); rit.hasNext(); ) {
                            Row cRow = rit.next();

                            //just skip the row if row number is 0
                            if (cRow.getRowNum() == 0) {
                                continue;
                            }

                            // add the row into the database
                            sessionModelFromExcel.addSettingsFromExcel(cRow);

                            // publish the progress after adding a record
                            currentRows++;
                            publishProgress(currentRows * 100 / allRows);
                        }
                        break;

                    case 11:
                        //statusHandler.deleteAllStatuses();
                        Sheet notify_publishers = workbook.getSheet("tblPUBLISHER");
                        Sheet notify_subscribers = workbook.getSheet("tblSUBSCRIBER");
                        Sheet notify_settings = workbook.getSheet("tblNOTIFY_SETTING");

                        for (Iterator<Row> rit = sheet.iterator(); rit.hasNext(); ) {
                            Row cRow = rit.next();

                            //just skip the row if row number is 0
                            if (cRow.getRowNum() == 0) {
                                continue;
                            }

                            // add the row into the database
                            sessionModelFromExcel.addNotificationFromExcel(cRow, notify_publishers,
                                    notify_subscribers, notify_settings);

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
        cUploadSessionData uploadMEData = new cUploadSessionData(context);
        uploadMEData.execute();
    }
*/