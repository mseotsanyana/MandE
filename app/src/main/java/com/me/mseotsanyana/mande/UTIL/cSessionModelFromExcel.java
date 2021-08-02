package com.me.mseotsanyana.mande.UTIL;

import android.content.Context;

import com.google.gson.Gson;
import com.me.mseotsanyana.mande.DAL.ìmpl.sqlite.session.cAddressRepositoryImpl;
import com.me.mseotsanyana.mande.BLL.model.session.cAddressModel;
import com.me.mseotsanyana.mande.DAL.ìmpl.sqlite.session.cEntityRepositoryImpl;
import com.me.mseotsanyana.mande.BLL.model.session.cEntityModel;
import com.me.mseotsanyana.mande.DAL.ìmpl.sqlite.session.cMenuRepositoryImpl;
import com.me.mseotsanyana.mande.BLL.model.session.cMenuModel;
import com.me.mseotsanyana.mande.DAL.ìmpl.sqlite.session.cNotificationRepositoryImpl;
import com.me.mseotsanyana.mande.BLL.model.session.cNotificationModel;
import com.me.mseotsanyana.mande.DAL.ìmpl.sqlite.session.cOperationRepositoryImpl;
import com.me.mseotsanyana.mande.BLL.model.session.cOperationModel;
import com.me.mseotsanyana.mande.DAL.ìmpl.sqlite.session.cOrganizationRepositoryImpl;
import com.me.mseotsanyana.mande.BLL.model.session.cOrganizationModel;
import com.me.mseotsanyana.mande.BLL.model.session.cPermissionModel;
import com.me.mseotsanyana.mande.DAL.ìmpl.sqlite.session.cRoleRepositoryImpl;
import com.me.mseotsanyana.mande.BLL.model.session.cRoleModel;
import com.me.mseotsanyana.mande.DAL.ìmpl.sqlite.session.cSettingRepositoryImpl;
import com.me.mseotsanyana.mande.BLL.model.session.cSettingModel;
import com.me.mseotsanyana.mande.DAL.ìmpl.sqlite.session.cStatusRepositoryImpl;
import com.me.mseotsanyana.mande.BLL.model.session.cStatusModel;
import com.me.mseotsanyana.mande.DAL.ìmpl.sqlite.session.cUserSQLiteRepositoryImpl;
import com.me.mseotsanyana.mande.BLL.model.session.cUserModel;
import com.me.mseotsanyana.mande.DAL.ìmpl.sqlite.session.cValueRepositoryImpl;
import com.me.mseotsanyana.mande.BLL.model.session.cValueModel;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by mseotsanyana on 2017/05/23.
 */

public class cSessionModelFromExcel {
    private static SimpleDateFormat sdf = cConstant.FORMAT_DATE;
    private static String TAG = cSessionModelFromExcel.class.getSimpleName();

    private cAddressModel addressModel;
    private cOrganizationModel organizationModel;
    private cValueModel valueModel;
    private cUserModel userModel;
    private cRoleModel roleModel;
    private cMenuModel menuModel;
    //private cPrivilegeModel privilegeModel;
    private cEntityModel entityModel;
    private cOperationModel operationModel;
    private cStatusModel statusModel;
    private cPermissionModel permissionModel;
    private cSettingModel settingModel;
    private cNotificationModel notificationModel;

    private cAddressRepositoryImpl addressDBA;
    private cOrganizationRepositoryImpl organizationDBA;
    private cValueRepositoryImpl valueDBA;
    private cUserSQLiteRepositoryImpl userDBA;
    private cRoleRepositoryImpl roleDBA;
    private cMenuRepositoryImpl menuDBA;
    //private cPrivilegeRepositoryImpl privilegeDBA;
    private cEntityRepositoryImpl entityDBA;
    private cOperationRepositoryImpl operationDBA;
    private cStatusRepositoryImpl statusDBA;
    //private cPermissionRepositoryImpl permissionDBA;
    private cSettingRepositoryImpl settingDBA;
    private cNotificationRepositoryImpl notificationDBA;

    private Context context;
    private Gson gson;

    public cSessionModelFromExcel(Context context){
        this.context    = context;

        addressDBA      = new cAddressRepositoryImpl(context);
        organizationDBA = new cOrganizationRepositoryImpl(context);
        valueDBA        = new cValueRepositoryImpl(context);
        userDBA         = new cUserSQLiteRepositoryImpl(context);
        roleDBA         = new cRoleRepositoryImpl(context);
        menuDBA         = new cMenuRepositoryImpl(context);
        //privilegeDBA    = new cPrivilegeRepositoryImpl(context);
        entityDBA       = new cEntityRepositoryImpl(context);
        operationDBA    = new cOperationRepositoryImpl(context);
        statusDBA       = new cStatusRepositoryImpl(context);
        //permissionDBA   = new cPermissionRepositoryImpl(context);
        settingDBA      = new cSettingRepositoryImpl(context);
        notificationDBA = new cNotificationRepositoryImpl(context);

        gson = new Gson();
    }

    // add an address record from excel to database
    public void addAddressFromExcel(Row cRow){
        addressModel = new cAddressModel();

        addressModel.setAddressID((int)cRow.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
        addressModel.setStreet(cRow.getCell(1, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
        addressModel.setCity(cRow.getCell(2, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
        addressModel.setProvince(cRow.getCell(3, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
        addressModel.setPostalCode(cRow.getCell(4, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
        addressModel.setCountry(cRow.getCell(5, Row.CREATE_NULL_AS_BLANK).getStringCellValue());

        //Log.d(TAG, "=========================================================");
        //Log.d(TAG, gson.toJson(addressModel));
    }

    // add an organization record from excel to database
    public void addOrganizationFromExcel(Row cRow, Sheet org_addresses,
                                         Sheet org_beneficiaries, Sheet org_funders,Sheet org_agencies){
        organizationModel = new cOrganizationModel();

        //organizationModel.setOrganizationID((int)cRow.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
        organizationModel.setName(cRow.getCell(1, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
        //organizationModel.setPhone(cRow.getCell(2, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
        //organizationModel.setFax(cRow.getCell(3, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
        //organizationModel.setVision(cRow.getCell(4, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
        //organizationModel.setMission(cRow.getCell(5, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
        organizationModel.setEmail(cRow.getCell(6, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
        organizationModel.setWebsite(cRow.getCell(7, Row.CREATE_NULL_AS_BLANK).getStringCellValue());

        ArrayList<Integer> addresses = new ArrayList<>();
        ArrayList<Integer> beneficiaries = new ArrayList<>();
        ArrayList<Integer> funders = new ArrayList<>();
        ArrayList<Integer> agencies = new ArrayList<>();

        int organizationID, addressID, beneficiaryID, funderID, agencyID;
        for (Iterator<Row> rit = org_addresses.iterator(); rit.hasNext(); ) {
            Row cOrgAddressRow = rit.next();

            //just skip the row if row number is 0
            if (cOrgAddressRow.getRowNum() == 0) {
                continue;
            }

            organizationID = (int)cOrgAddressRow.getCell (0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
            if (true/*organizationModel.getOrganizationID() == organizationID*/){
                addressID = (int)cOrgAddressRow.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                addresses.add(addressID);
            }
        }

        for (Iterator<Row> rit = org_beneficiaries.iterator(); rit.hasNext(); ) {
            Row cOrgBeneficiaryRow = rit.next();

            //just skip the row if row number is 0
            if (cOrgBeneficiaryRow.getRowNum() == 0) {
                continue;
            }

            beneficiaryID = (int)cOrgBeneficiaryRow.getCell (0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
            if (true/*organizationModel.getOrganizationID() == beneficiaryID*/){
                beneficiaries.add(beneficiaryID);
            }
        }

        for (Iterator<Row> rit = org_funders.iterator(); rit.hasNext(); ) {
            Row cOrgFunderRow = rit.next();

            //just skip the row if row number is 0
            if (cOrgFunderRow.getRowNum() == 0) {
                continue;
            }

            funderID = (int)cOrgFunderRow.getCell (0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
            if (true/*organizationModel.getOrganizationID() == funderID*/){
                funders.add(funderID);
            }
        }

        for (Iterator<Row> rit = org_agencies.iterator(); rit.hasNext(); ) {
            Row cOrgAgencyRow = rit.next();

            //just skip the row if row number is 0
            if (cOrgAgencyRow.getRowNum() == 0) {
                continue;
            }

            agencyID = (int)cOrgAgencyRow.getCell (0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
            if (true/*organizationModel.getOrganizationID() == agencyID*/){
                agencies.add(agencyID);
            }
        }
        //Log.d(TAG, "=========================================================");
        //Log.d(TAG, gson.toJson(organizationModel));
        //Log.d(TAG, gson.toJson(addresses));

        organizationDBA.addOrganizationFromExcel(organizationModel, addresses, beneficiaries, funders, agencies);
    }

    // add an organizational value record from excel to database
    public void addValueFromExcel(Row cRow){
        valueModel = new cValueModel();

        valueModel.setValueID((int)cRow.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
        valueModel.setOrganizationID((int)cRow.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
        valueModel.setName(cRow.getCell(2, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
        valueModel.setDescription(cRow.getCell(3, Row.CREATE_NULL_AS_BLANK).getStringCellValue());

        //Log.d(TAG, "=========================================================");
        //Log.d(TAG, gson.toJson(valueModel));

        valueDBA.addValueFromExcel(valueModel);
    }

    public void addUserFromExcel(Row cRow, Sheet user_addresses) {
        userModel = new cUserModel();

        userModel.setUserID((int)cRow.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
        userModel.setOrganizationID((int)cRow.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
        userModel.setName(cRow.getCell(2, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
        userModel.setSurname(cRow.getCell(3, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
        userModel.setGender(cRow.getCell(4, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
        userModel.setDescription(cRow.getCell(5, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
        userModel.setEmail(cRow.getCell(6, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
        userModel.setWebsite(cRow.getCell(7, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
        userModel.setPhone(cRow.getCell(8, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
        userModel.setPassword(cRow.getCell(9, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
        userModel.setSalt(cRow.getCell(10, Row.CREATE_NULL_AS_BLANK).getStringCellValue());

        //userModel.setPhoto(cBitmap.getBitmapFromAssets(context,"HB_551.png"));

        ArrayList<Integer> addresses = new ArrayList<>();
        int userID, addressID;
        for (Iterator<Row> rit = user_addresses.iterator(); rit.hasNext(); ) {
            Row cUserAddressRow = rit.next();

            //just skip the row if row number is 0
            if (cUserAddressRow.getRowNum() == 0) {
                continue;
            }

            userID = (int)cUserAddressRow.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
            if (userModel.getUserID() == userID){
                addressID = (int)cUserAddressRow.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                addresses.add(addressID);
            }
        }

        //Log.d(TAG, "=========================================================");
        //Log.d(TAG, gson.toJson(userModel));
        //Log.d(TAG, gson.toJson(addresses));

        userDBA.addUserFromExcel(userModel, addresses);
    }

    public void addMenuFromExcel(Row cRow) {
        menuModel = new cMenuModel();

        menuModel.setMenuServerID((int)cRow.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
        menuModel.setParentServerID((int)cRow.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
        menuModel.setName(cRow.getCell(2, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
        //menuModel.setDescription(cRow.getCell(3, Row.CREATE_NULL_AS_BLANK).getStringCellValue());

        //Log.d(TAG, "=========================================================");
        //Log.d(TAG, gson.toJson(menuModel));

        menuDBA.addMenuFromExcel(menuModel);
    }

    public void addRoleFromExcel(Row cRow, Sheet role_users, Sheet role_menus) {
        roleModel = new cRoleModel();

        //roleModel.setRoleID((int)cRow.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
        //roleModel.setOrganizationID((int)cRow.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
        roleModel.setName(cRow.getCell(2, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
        roleModel.setDescription(cRow.getCell(3, Row.CREATE_NULL_AS_BLANK).getStringCellValue());

        ArrayList<Integer> users = new ArrayList<>();
        ArrayList<Integer> menus = new ArrayList<>();
        int roleID, organizationID, userID, menuID;

        // construct list of users
        for (Iterator<Row> rit = role_users.iterator(); rit.hasNext(); ) {
            Row cRoleUsersRow = rit.next();

            //just skip the row if row number is 0
            if (cRoleUsersRow.getRowNum() == 0) {
                continue;
            }

            roleID = (int)cRoleUsersRow.getCell(2, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
            organizationID = (int)cRoleUsersRow.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
            /*if (roleModel.getRoleID() == roleID && roleModel.getOrganizationID() == organizationID){
                userID = (int)cRoleUsersRow.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                users.add(userID);
            }*/
        }

        // construct list of menus
        for (Iterator<Row> rit = role_menus.iterator(); rit.hasNext(); ) {
            Row cRoleMenusRow = rit.next();

            //just skip the row if row number is 0
            if (cRoleMenusRow.getRowNum() == 0) {
                continue;
            }

            roleID = (int)cRoleMenusRow.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
            organizationID = (int)cRoleMenusRow.getCell(2, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
            /*if (roleModel.getRoleID() == roleID && roleModel.getOrganizationID() == organizationID){
                menuID = (int)cRoleMenusRow.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                menus.add(menuID);
            }*/
        }

        //Log.d(TAG, "=========================================================");
        //Log.d(TAG, gson.toJson(roleModel));
        //Log.d(TAG, gson.toJson(users));
        //Log.d(TAG, gson.toJson(menus));

        roleDBA.addRoleFromExcel(roleModel, users, menus);
    }

    public void addEntityFromExcel(Row cRow) {
        entityModel = null;//new cEntityModel(entityID, moduleID, operationStatusMap, unixPermList);

        //entityModel.setEntityID((int)cRow.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
        //entityModel.setModuleID((int)cRow.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
        //entityModel.setName(cRow.getCell(2, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
        //entityModel.setDescription(cRow.getCell(3, Row.CREATE_NULL_AS_BLANK).getStringCellValue());

        //Log.d(TAG, "=========================================================");
        //Log.d(TAG, gson.toJson(entityModel));

        entityDBA.addEntityFromExcel(entityModel);
    }

    public void addOperationFromExcel(Row cRow) {
        operationModel = new cOperationModel();

//        operationModel.setOperationID((int)cRow.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
        operationModel.setName(cRow.getCell(1, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
        operationModel.setDescription(cRow.getCell(2, Row.CREATE_NULL_AS_BLANK).getStringCellValue());

        //Log.d(TAG, "=========================================================");
        //Log.d(TAG, gson.toJson(operationModel));

        operationDBA.addOperationFromExcel(operationModel);
    }

    public void addStatusFromExcel(Row cRow) {
        statusModel = new cStatusModel();

        statusModel.setStatusServerID((int)cRow.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
        statusModel.setName(cRow.getCell(1, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
        statusModel.setDescription(cRow.getCell(2, Row.CREATE_NULL_AS_BLANK).getStringCellValue());

        //Log.d(TAG, "=========================================================");
        //Log.d(TAG, gson.toJson(statusModel));

        statusDBA.addStatusFromExcel(statusModel);
    }
/*
    public void addPrivilegeFromExcel(Row cRow, Sheet priv_permissions, Sheet priv_statuses) {
        privilegeModel = new cPrivilegeModel();

        privilegeModel.setPrivilegeID((int)cRow.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
        privilegeModel.setName(cRow.getCell(1, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
        privilegeModel.setDescription(cRow.getCell(2, Row.CREATE_NULL_AS_BLANK).getStringCellValue());

        ArrayList<Integer> statuses = new ArrayList<>();

        ArrayList<Integer> entities    = new ArrayList<>();
        ArrayList<Integer> entityTypes = new ArrayList<>();
        ArrayList<Integer> operations  = new ArrayList<>();

        int privilegeID, entityID, entityTypeID, operationID, statusID;

        // construct list of permissions
        for (Iterator<Row> rit = priv_permissions.iterator(); rit.hasNext(); ) {
            Row cPrivPermissionsRow = rit.next();

            //just skip the row if row number is 0
            if (cPrivPermissionsRow.getRowNum() == 0) {
                continue;
            }

            privilegeID = (int)cPrivPermissionsRow.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();

            if (privilegeModel.getPrivilegeID() == privilegeID){
                entityID = (int)cPrivPermissionsRow.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                entityTypeID = (int)cPrivPermissionsRow.getCell(2, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                operationID = (int)cPrivPermissionsRow.getCell(3, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();

                add permissions
                entities.add(entityID);
                entityTypes.add(entityTypeID);
                operations.add(operationID);
            }
        }

        construct list of privilege statuses
        for (Iterator<Row> rit = priv_statuses.iterator(); rit.hasNext(); ) {
            Row cPrivStatusesRow = rit.next();

            //just skip the row if row number is 0
            if (cPrivStatusesRow.getRowNum() == 0) {
                continue;
            }

            privilegeID = (int)cPrivStatusesRow.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();

            if (privilegeModel.getPrivilegeID() == privilegeID){
                statusID = (int)cPrivStatusesRow.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                statuses.add(statusID);
            }
        }

        //Log.d(TAG, "=========================================================");
        //Log.d(TAG, gson.toJson(privilegeModel));

        privilegeDBA.addPrivilegeFromExcel(privilegeModel, entities, entityTypes, operations, statuses);
    }
*/
/*
    public void addPermissionFromExcel(Row cRow, Sheet perm_statuses) {
        permissionModel = new cPermissionModel();

        permissionModel.setPrivilegeID((int)cRow.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
        permissionModel.setEntityID((int)cRow.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
        permissionModel.setEntityTypeID((int)cRow.getCell(2, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
        permissionModel.setOperationID((int)cRow.getCell(3, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());

        ArrayList<Integer> statuses = new ArrayList<>();
        int privilegeID, entityID, entityTypeID, operationID, statusID;

        // construct list of menus
        for (Iterator<Row> rit = perm_statuses.iterator(); rit.hasNext(); ) {
            Row cPermStatusesRow = rit.next();

            //just skip the row if row number is 0
            if (cPermStatusesRow.getRowNum() == 0) {
                continue;
            }

            privilegeID = (int)cPermStatusesRow.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
            entityID = (int)cPermStatusesRow.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
            entityTypeID= (int)cPermStatusesRow.getCell(2, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
            operationID= (int)cPermStatusesRow.getCell(3, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();

            if (permissionModel.getPrivilegeID() == privilegeID &&
                    permissionModel.getEntityID() == entityID &&
                    permissionModel.getEntityTypeID() == entityTypeID &&
                    permissionModel.getOperationID() == operationID){
                statusID = (int)cPermStatusesRow.getCell(4, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                statuses.add(statusID);
            }
        }

        //Log.d(TAG, "=========================================================");
        //Log.d(TAG, gson.toJson(permissionModel));
        //Log.d(TAG, gson.toJson(statuses));

        permissionDBA.addPermissionFromExcel(permissionModel, statuses);
    }
*/
    public void addSettingsFromExcel(Row cRow){
        settingModel = new cSettingModel();

        settingModel.setSettingID((int)cRow.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
        settingModel.setName(cRow.getCell(1, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
        settingModel.setDescription(cRow.getCell(2, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
        settingModel.setSettingValue((int)cRow.getCell(3, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());

        //Log.d(TAG, "=========================================================");
        //Log.d(TAG, gson.toJson(settingModel));

        settingDBA.addSettingFromExcel(settingModel);
    }

    public void addNotificationFromExcel(Row cRow, Sheet notify_publishers,
                                         Sheet notify_subscribers,
                                         Sheet notify_settings) {
        notificationModel = new cNotificationModel();

        notificationModel.setNotificationID((int)cRow.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
        notificationModel.setEntityID((int)cRow.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
        notificationModel.setEntityTypeID((int)cRow.getCell(2, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
        notificationModel.setOperationID((int)cRow.getCell(3, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
        notificationModel.setName(cRow.getCell(4, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
        notificationModel.setDescription(cRow.getCell(5, Row.CREATE_NULL_AS_BLANK).getStringCellValue());

        ArrayList<Integer> publishers = new ArrayList<>();
        ArrayList<Integer> subscribers = new ArrayList<>();
        ArrayList<Integer> settings = new ArrayList<>();

        int notifyID, publisherID, subscribersID, settingsID;

        // construct list of publishers
        for (Iterator<Row> rit = notify_publishers.iterator(); rit.hasNext(); ) {
            Row cNotifyPublishersRow = rit.next();

            //just skip the row if row number is 0
            if (cNotifyPublishersRow.getRowNum() == 0) {
                continue;
            }

            notifyID = (int)cNotifyPublishersRow.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
            if (notificationModel.getNotificationID() == notifyID){
                publisherID = (int)cNotifyPublishersRow.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                publishers.add(publisherID);
            }
        }

        // construct list of subscribers
        for (Iterator<Row> rit = notify_subscribers.iterator(); rit.hasNext(); ) {
            Row cNotifySubscribersRow = rit.next();

            //just skip the row if row number is 0
            if (cNotifySubscribersRow.getRowNum() == 0) {
                continue;
            }

            notifyID = (int)cNotifySubscribersRow.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
            if (notificationModel.getNotificationID() == notifyID){
                subscribersID = (int)cNotifySubscribersRow.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                subscribers.add(subscribersID);
            }
        }

        // construct list of notification settings
        for (Iterator<Row> rit = notify_settings.iterator(); rit.hasNext(); ) {
            Row cNotifySettingsRow = rit.next();

            //just skip the row if row number is 0
            if (cNotifySettingsRow.getRowNum() == 0) {
                continue;
            }

            notifyID = (int)cNotifySettingsRow.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
            if (notificationModel.getNotificationID() == notifyID){
                settingsID = (int)cNotifySettingsRow.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                settings.add(settingsID);
            }
        }

        //Log.d(TAG, "=========================================================");
        //Log.d(TAG, gson.toJson(notificationModel));
        //Log.d(TAG, gson.toJson(publishers));
        //Log.d(TAG, gson.toJson(subscribers));
        //Log.d(TAG, gson.toJson(settings));

        notificationDBA.addNotificationFromExcel(notificationModel, publishers, subscribers, settings);
    }
}
