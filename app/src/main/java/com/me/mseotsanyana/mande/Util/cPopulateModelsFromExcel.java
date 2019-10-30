package com.me.mseotsanyana.mande.Util;

import android.content.Context;

import com.me.mseotsanyana.mande.BRBAC.DAL.cAddressDBA;
import com.me.mseotsanyana.mande.BRBAC.DAL.cAddressModel;
import com.me.mseotsanyana.mande.BRBAC.DAL.cEntityDBA;
import com.me.mseotsanyana.mande.BRBAC.DAL.cEntityModel;
import com.me.mseotsanyana.mande.BRBAC.DAL.cMenuDBA;
import com.me.mseotsanyana.mande.BRBAC.DAL.cMenuModel;
import com.me.mseotsanyana.mande.BRBAC.DAL.cNotificationDBA;
import com.me.mseotsanyana.mande.BRBAC.DAL.cNotificationModel;
import com.me.mseotsanyana.mande.BRBAC.DAL.cOperationDBA;
import com.me.mseotsanyana.mande.BRBAC.DAL.cOperationModel;
import com.me.mseotsanyana.mande.BRBAC.DAL.cOrganizationDBA;
import com.me.mseotsanyana.mande.BRBAC.DAL.cOrganizationModel;
import com.me.mseotsanyana.mande.BRBAC.DAL.cPermissionDBA;
import com.me.mseotsanyana.mande.BRBAC.DAL.cPermissionModel;
import com.me.mseotsanyana.mande.BRBAC.DAL.cPrivilegeDBA;
import com.me.mseotsanyana.mande.BRBAC.DAL.cPrivilegeModel;
import com.me.mseotsanyana.mande.BRBAC.DAL.cRoleDBA;
import com.me.mseotsanyana.mande.BRBAC.DAL.cRoleModel;
import com.me.mseotsanyana.mande.BRBAC.DAL.cSessionDBA;
import com.me.mseotsanyana.mande.BRBAC.DAL.cSessionModel;
import com.me.mseotsanyana.mande.BRBAC.DAL.cSettingDBA;
import com.me.mseotsanyana.mande.BRBAC.DAL.cSettingModel;
import com.me.mseotsanyana.mande.BRBAC.DAL.cStatusDBA;
import com.me.mseotsanyana.mande.BRBAC.DAL.cStatusModel;
import com.me.mseotsanyana.mande.BRBAC.DAL.cUserDBA;
import com.me.mseotsanyana.mande.BRBAC.DAL.cUserModel;
import com.me.mseotsanyana.mande.BRBAC.DAL.cValueDBA;
import com.me.mseotsanyana.mande.BRBAC.DAL.cValueModel;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.util.ArrayList;
import java.util.Iterator;


/**
 * Created by mseotsanyana on 2017/05/23.
 */

public class cPopulateModelsFromExcel {
    private static final String TAG = "cModelsFromExcel";

    private cAddressModel addressModel;
    private cOrganizationModel organizationModel;
    private cValueModel valueModel;
    private cUserModel userModel;
    private cSessionModel sessionModel;
    private cRoleModel roleModel;
    private cMenuModel menuModel;
    private cPrivilegeModel privilegeModel;
    private cEntityModel entityModel;
    private cOperationModel operationModel;
    private cStatusModel statusModel;
    private cPermissionModel permissionModel;
    private cSettingModel settingModel;
    private cNotificationModel notificationModel;

    private cAddressDBA addressDBA;
    private cOrganizationDBA organizationDBA;
    private cValueDBA valueDBA;
    private cUserDBA userDBA;
    private cSessionDBA sessionDBA;
    private cRoleDBA roleDBA;
    private cMenuDBA menuDBA;
    private cPrivilegeDBA privilegeDBA;
    private cEntityDBA entityDBA;
    private cOperationDBA operationDBA;
    private cStatusDBA statusDBA;
    private cPermissionDBA permissionDBA;
    private cSettingDBA settingDBA;
    private cNotificationDBA notificationDBA;

    private Context context;

    public cPopulateModelsFromExcel(Context context){
        this.context       = context;

        addressDBA         = new cAddressDBA(context);
        organizationDBA    = new cOrganizationDBA(context);
        valueDBA           = new cValueDBA(context);
        userDBA            = new cUserDBA(context);
        sessionDBA         = new cSessionDBA(context);
        roleDBA            = new cRoleDBA(context);
        menuDBA            = new cMenuDBA(context);
        privilegeDBA       = new cPrivilegeDBA(context);
        entityDBA          = new cEntityDBA(context);
        operationDBA       = new cOperationDBA(context);
        statusDBA          = new cStatusDBA(context);
        permissionDBA      = new cPermissionDBA(context);
    }

    // store the excel row into the organization model object
    public void addAddressFromExcel(Row cRow){
        addressModel = new cAddressModel();

        addressModel.setAddressID((int)cRow.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
        addressModel.setStreet(cRow.getCell(1, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
        addressModel.setCity(cRow.getCell(2, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
        addressModel.setProvince(cRow.getCell(3, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
        addressModel.setPostalCode(cRow.getCell(4, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
        addressModel.setCountry(cRow.getCell(5, Row.CREATE_NULL_AS_BLANK).getStringCellValue());

        addressDBA.addAddressFromExcel(addressModel);

    }

    // store the excel row into the organization model object
    public void addOrganizationFromExcel(Row cRow, Sheet org_addresses){
        organizationModel = new cOrganizationModel();

        organizationModel.setOrganizationID((int)cRow.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
        organizationModel.setName(cRow.getCell(1, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
        organizationModel.setPhone(cRow.getCell(2, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
        organizationModel.setFax(cRow.getCell(3, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
        organizationModel.setVision(cRow.getCell(4, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
        organizationModel.setMission(cRow.getCell(5, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
        organizationModel.setEmail(cRow.getCell(6, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
        organizationModel.setWebsite(cRow.getCell(7, Row.CREATE_NULL_AS_BLANK).getStringCellValue());

        ArrayList<Integer> addresses = new ArrayList<>();
        int organizationID, addressID;
        for (Iterator<Row> rit = org_addresses.iterator(); rit.hasNext(); ) {
            Row cOrgAddressRow = rit.next();
            organizationID = (int)cOrgAddressRow.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
            if (organizationModel.getOrganizationID() == organizationID){
                addressID = (int)cOrgAddressRow.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                addresses.add(addressID);
            }
        }

        organizationDBA.addOrganizationFromExcel(organizationModel, addresses);
    }

    // store the excel row into the organization values model object
    public void addValueFromExcel(Row cRow){
        valueModel = new cValueModel();

        valueModel.setValueID((int)cRow.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
        valueModel.setOrganizationID((int)cRow.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
        valueModel.setName(cRow.getCell(2, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
        valueModel.setDescription(cRow.getCell(3, Row.CREATE_NULL_AS_BLANK).getStringCellValue());

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
            userID = (int)cUserAddressRow.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
            if (userModel.getUserID() == userID){
                addressID = (int)cUserAddressRow.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                addresses.add(addressID);
            }
        }

        userDBA.addUserFromExcel(userModel, addresses);
    }

    public void addSessionFromExcel(Row cRow) {
        sessionModel = new cSessionModel();

        sessionModel.setSessionID((int)cRow.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
        sessionModel.setUserID((int)cRow.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
        sessionModel.setName(cRow.getCell(2, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
        sessionModel.setDescription(cRow.getCell(5, Row.CREATE_NULL_AS_BLANK).getStringCellValue());

        sessionDBA.addSessionFromExcel(sessionModel);
    }

    public void addMenuFromExcel(Row cRow) {
        menuModel = new cMenuModel();

        menuModel.setMenuID((int)cRow.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
        menuModel.setParentID((int)cRow.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
        menuModel.setName(cRow.getCell(2, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
        menuModel.setDescription(cRow.getCell(3, Row.CREATE_NULL_AS_BLANK).getStringCellValue());

        menuDBA.addMenuFromExcel(menuModel);
    }

    public void addRoleFromExcel(Row cRow, Sheet role_users, Sheet role_sessions, Sheet role_menus) {
        roleModel = new cRoleModel();

        roleModel.setRoleID((int)cRow.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
        roleModel.setOrganizationID((int)cRow.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
        roleModel.setName(cRow.getCell(2, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
        roleModel.setDescription(cRow.getCell(3, Row.CREATE_NULL_AS_BLANK).getStringCellValue());

        ArrayList<Integer> users = new ArrayList<>();
        ArrayList<Integer> sessions = new ArrayList<>();
        ArrayList<Integer> menus = new ArrayList<>();
        int roleID, organizationID, userID, sessionID, menuID;

        // construct list of users
        for (Iterator<Row> rit = role_users.iterator(); rit.hasNext(); ) {
            Row cRoleUsersRow = rit.next();
            roleID = (int)cRoleUsersRow.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
            organizationID = (int)cRoleUsersRow.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
            if (roleModel.getRoleID() == roleID && roleModel.getOrganizationID() == organizationID){
                userID = (int)cRoleUsersRow.getCell(2, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                users.add(userID);
            }
        }

        // construct list of sessions
        for (Iterator<Row> rit = role_sessions.iterator(); rit.hasNext(); ) {
            Row cRoleSessionsRow = rit.next();
            roleID = (int)cRoleSessionsRow.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
            organizationID = (int)cRoleSessionsRow.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
            if (roleModel.getRoleID() == roleID && roleModel.getOrganizationID() == organizationID){
                sessionID = (int)cRoleSessionsRow.getCell(2, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                sessions.add(sessionID);
            }
        }

        // construct list of menus
        for (Iterator<Row> rit = role_menus.iterator(); rit.hasNext(); ) {
            Row cRoleMenusRow = rit.next();
            roleID = (int)cRoleMenusRow.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
            organizationID = (int)cRoleMenusRow.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
            if (roleModel.getRoleID() == roleID && roleModel.getOrganizationID() == organizationID){
                menuID = (int)cRoleMenusRow.getCell(2, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                menus.add(menuID);
            }
        }

        roleDBA.addRoleFromExcel(roleModel, users, sessions, menus);
    }

    public void addPrivilegeFromExcel(Row cRow) {
        privilegeModel = new cPrivilegeModel();

        privilegeModel.setPrivilegeID((int)cRow.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
        privilegeModel.setRoleID((int)cRow.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
        privilegeModel.setOrganizationID((int)cRow.getCell(2, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
        privilegeModel.setName(cRow.getCell(3, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
        privilegeModel.setDescription(cRow.getCell(4, Row.CREATE_NULL_AS_BLANK).getStringCellValue());

        privilegeDBA.addPrivilegeFromExcel(privilegeModel);
    }

    public void addEntityFromExcel(Row cRow) {
        entityModel = new cEntityModel();

        entityModel.setEntityID((int)cRow.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
        entityModel.setEntityTypeID((int)cRow.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
        entityModel.setName(cRow.getCell(2, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
        entityModel.setDescription(cRow.getCell(3, Row.CREATE_NULL_AS_BLANK).getStringCellValue());

        entityDBA.addEntityFromExcel(entityModel);
    }

    public void addOperationFromExcel(Row cRow) {
        operationModel = new cOperationModel();

        operationModel.setOperationID((int)cRow.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
        operationModel.setName(cRow.getCell(1, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
        operationModel.setDescription(cRow.getCell(2, Row.CREATE_NULL_AS_BLANK).getStringCellValue());

        operationDBA.addOperationFromExcel(operationModel);
    }

    public void addStatusFromExcel(Row cRow) {
        statusModel = new cStatusModel();

        statusModel.setStatusID((int)cRow.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
        statusModel.setName(cRow.getCell(1, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
        statusModel.setDescription(cRow.getCell(2, Row.CREATE_NULL_AS_BLANK).getStringCellValue());

        statusDBA.addStatusFromExcel(statusModel);
    }

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

        permissionDBA.addPermissionFromExcel(permissionModel, statuses);
    }

    public void addSettingsFromExcel(Row cRow){
        settingModel = new cSettingModel();

        settingModel.setSettingID((int)cRow.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
        settingModel.setName(cRow.getCell(1, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
        settingModel.setDescription(cRow.getCell(2, Row.CREATE_NULL_AS_BLANK).getStringCellValue());

        settingDBA.addSettingFromExcel(settingModel);
    }

    public void addNotificationFromExcel(Row cRow, Sheet notify_publishers,
                                         Sheet notify_subscribers,
                                         Sheet notify_settings) {
        notificationModel = new cNotificationModel();

        notificationModel.setNotificationID((int)cRow.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
        notificationModel.setEntityID((int)cRow.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
        notificationModel.setEntityTypeID((int)cRow.getCell(2, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
        notificationModel.setName(cRow.getCell(3, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
        notificationModel.setDescription(cRow.getCell(4, Row.CREATE_NULL_AS_BLANK).getStringCellValue());

        ArrayList<Integer> publishers = new ArrayList<>();
        ArrayList<Integer> subscribers = new ArrayList<>();
        ArrayList<Integer> settings = new ArrayList<>();
        int notifyID, publisherID, subscribersID, settingsID;

        // construct list of users
        for (Iterator<Row> rit = notify_publishers.iterator(); rit.hasNext(); ) {
            Row cNotifyPublishersRow = rit.next();
            notifyID = (int)cNotifyPublishersRow.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
            if (notificationModel.getNotificationID() == notifyID){
                publisherID = (int)cNotifyPublishersRow.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                publishers.add(publisherID);
            }
        }

        // construct list of sessions
        for (Iterator<Row> rit = notify_subscribers.iterator(); rit.hasNext(); ) {
            Row cNotifySubscribersRow = rit.next();
            notifyID = (int)cNotifySubscribersRow.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
            if (notificationModel.getNotificationID() == notifyID){
                subscribersID = (int)cNotifySubscribersRow.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                subscribers.add(subscribersID);
            }
        }

        // construct list of menus
        for (Iterator<Row> rit = notify_settings.iterator(); rit.hasNext(); ) {
            Row cNotifySettingsRow = rit.next();
            notifyID = (int)cNotifySettingsRow.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
            if (notificationModel.getNotificationID() == notifyID){
                settingsID = (int)cNotifySettingsRow.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue();
                settings.add(settingsID);
            }
        }

        notificationDBA.addNotificationFromExcel(notificationModel, publishers, subscribers, settings);
    }

/*
    public void addUserRoleFromExcel(Row cRow) {
        userRoleModel = new cUserRoleModel();

        userRoleModel.setUserID((int)cRow.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
        userRoleModel.setRoleID((int)cRow.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
        userRoleModel.setOrganizationID((int)cRow.getCell(2, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());

        userRoleDBA.addUserRoleFromExcel(userRoleModel);
    }

    public void addSessionRoleFromExcel(Row cRow) {
        sessionRoleModel = new cSessionRoleModel();

        sessionRoleModel.setSessionID((int)cRow.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
        sessionRoleModel.setRoleID((int)cRow.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
        sessionRoleModel.setOrganizationID((int)cRow.getCell(2, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());

        //sessionRoleModel.setCreateDate(cRow.getCell(2, Row.CREATE_NULL_AS_BLANK).getDateCellValue());

        sessionRoleDBA.addSessionRoleFromExcel(sessionRoleModel);
    }

    public void addMenuRoleFromExcel(Row cRow) {
        menuRoleModel = new cMenuRoleModel();

        menuRoleModel.setMenuID((int)cRow.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
        menuRoleModel.setRoleID((int)cRow.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
        menuRoleModel.setOrganizationID((int)cRow.getCell(2, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());

        //menuRoleModel.setCreateDate(cRow.getCell(2, Row.CREATE_NULL_AS_BLANK).getDateCellValue());

        menuRoleDBA.addMenuRoleFromExcel(menuRoleModel);
    }
*/
    /*
        public void addPrivilegeRoleFromExcel(Row cRow) {
            privilegeRoleModel = new cPrivilegeRoleModel();

            privilegeRoleModel.setPrivilegeID((int)cRow.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            privilegeRoleModel.setRoleID((int)cRow.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            privilegeRoleModel.setOrganizationID((int)cRow.getCell(2, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());

            //privilegeRoleModel.setCreateDate(cRow.getCell(2, Row.CREATE_NULL_AS_BLANK).getDateCellValue());

            privilegeRoleDBA.addPrivilegeRoleFromExcel(privilegeRoleModel);
        }

        public void addOperationStatusFromExcel(Row cRow) {
            operationStatusModel = new cOperationStatusModel();
            operationModel = new cOperationModel();
            statusModel = new cStatusModel();

            operationModel.setOperationID((int)cRow.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
            statusModel.setStatusID((int)cRow.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());

            operationStatusModel.setOperationModel(operationModel);
            operationStatusModel.setStatusModel(statusModel);

            operationStatusDBA.addOperationStatusFromExcel(operationStatusModel);
        }
    */
}
