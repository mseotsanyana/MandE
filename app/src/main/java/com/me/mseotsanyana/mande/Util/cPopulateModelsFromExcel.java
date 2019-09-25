package com.me.mseotsanyana.mande.Util;

import android.content.Context;

import com.me.mseotsanyana.mande.BRBAC.DAL.cAddressDBA;
import com.me.mseotsanyana.mande.BRBAC.DAL.cAddressModel;
import com.me.mseotsanyana.mande.BRBAC.DAL.cEntityDBA;
import com.me.mseotsanyana.mande.BRBAC.DAL.cEntityModel;
import com.me.mseotsanyana.mande.BRBAC.DAL.cMenuDBA;
import com.me.mseotsanyana.mande.BRBAC.DAL.cMenuModel;
import com.me.mseotsanyana.mande.BRBAC.DAL.cMenuRoleDBA;
import com.me.mseotsanyana.mande.BRBAC.DAL.cMenuRoleModel;
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
import com.me.mseotsanyana.mande.BRBAC.DAL.cSessionRoleDBA;
import com.me.mseotsanyana.mande.BRBAC.DAL.cSessionRoleModel;
import com.me.mseotsanyana.mande.BRBAC.DAL.cStatusDBA;
import com.me.mseotsanyana.mande.BRBAC.DAL.cStatusModel;
import com.me.mseotsanyana.mande.BRBAC.DAL.cUserDBA;
import com.me.mseotsanyana.mande.BRBAC.DAL.cUserModel;
import com.me.mseotsanyana.mande.BRBAC.DAL.cUserRoleDBA;
import com.me.mseotsanyana.mande.BRBAC.DAL.cUserRoleModel;
import com.me.mseotsanyana.mande.BRBAC.DAL.cValueDBA;
import com.me.mseotsanyana.mande.BRBAC.DAL.cValueModel;

import org.apache.poi.ss.usermodel.Row;


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
    private cOperationModel operationModel;
    private cEntityModel entityModel;
    private cStatusModel statusModel;
    private cUserRoleModel userRoleModel;
    private cSessionRoleModel sessionRoleModel;
    private cMenuRoleModel menuRoleModel;
    //private cPrivilegeRoleModel privilegeRoleModel;
    private cPermissionModel permissionModel;

    private cAddressDBA addressDBA;
    private cOrganizationDBA organizationDBA;
    private cValueDBA valueDBA;
    private cUserDBA userDBA;
    private cSessionDBA sessionDBA;
    private cRoleDBA roleDBA;
    private cMenuDBA menuDBA;
    private cPrivilegeDBA privilegeDBA;
    private cOperationDBA operationDBA;
    private cEntityDBA entityDBA;
    private cStatusDBA statusDBA;
    private cUserRoleDBA userRoleDBA;
    private cSessionRoleDBA sessionRoleDBA;
    private cMenuRoleDBA menuRoleDBA;
    //private cPrivilegeRoleDBA privilegeRoleDBA;
    private cPermissionDBA permissionDBA;

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
        operationDBA       = new cOperationDBA(context);
        entityDBA          = new cEntityDBA(context);
        statusDBA          = new cStatusDBA(context);
        userRoleDBA        = new cUserRoleDBA(context);
        sessionRoleDBA     = new cSessionRoleDBA(context);
        menuRoleDBA        = new cMenuRoleDBA(context);
        //privilegeRoleDBA   = new cPrivilegeRoleDBA(context);
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
        //addressModel.setCreateDate(cRow.getCell(6, Row.CREATE_NULL_AS_BLANK).getDateCellValue());

        addressDBA.addAddressFromExcel(addressModel);
    }

    // store the excel row into the organization model object
    public void addOrganizationFromExcel(Row cRow){
        organizationModel = new cOrganizationModel();

        organizationModel.setOrganizationID((int)cRow.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
        organizationModel.setAddressID((int)cRow.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
        organizationModel.setOrganizationName(cRow.getCell(2, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
        organizationModel.setTelephone(cRow.getCell(3, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
        organizationModel.setFax(cRow.getCell(4, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
        organizationModel.setVision(cRow.getCell(5, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
        organizationModel.setMission(cRow.getCell(6, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
        organizationModel.setEmailAddress(cRow.getCell(7, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
        organizationModel.setWebsite(cRow.getCell(8, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
        //organizationModel.setCreateDate(cRow.getCell(9, Row.CREATE_NULL_AS_BLANK).getDateCellValue());

        organizationDBA.addOrganizationFromExcel(organizationModel);

    }

    // store the excel row into the organization values model object
    public void addValueFromExcel(Row cRow){
        valueModel = new cValueModel();

        valueModel.setValueID((int)cRow.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
        valueModel.setOrganizationID((int)cRow.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
        valueModel.setValueName(cRow.getCell(2, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
        //valueModel.setCreateDate(cRow.getCell(3, Row.CREATE_NULL_AS_BLANK).getDateCellValue());

        valueDBA.addValueFromExcel(valueModel);
    }

    public void addUserFromExcel(Row cRow) {
        userModel = new cUserModel();

        userModel.setUserID((int)cRow.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
        userModel.setOrganizationID((int)cRow.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
        userModel.setAddressID((int)cRow.getCell(2, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
        userModel.setPhotoPath(cRow.getCell(3, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
        userModel.setName(cRow.getCell(4, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
        userModel.setSurname(cRow.getCell(5, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
        userModel.setGender(cRow.getCell(6, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
        userModel.setDescription(cRow.getCell(7, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
        userModel.setEmail(cRow.getCell(8, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
        userModel.setWebsite(cRow.getCell(9, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
        userModel.setPhone(cRow.getCell(10, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
        userModel.setPassword(cRow.getCell(11, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
        userModel.setSalt(cRow.getCell(12, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
        //userModel.setCreateDate(cRow.getCell(11, Row.CREATE_NULL_AS_BLANK).getDateCellValue());

        //userModel.setPhoto(cBitmap.getBitmapFromAssets(context,"HB_551.png"));

        userDBA.addUserFromExcel(userModel);
    }

    public void addSessionFromExcel(Row cRow) {
        sessionModel = new cSessionModel();

        sessionModel.setSessionID((int)cRow.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
        sessionModel.setUserID((int)cRow.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
        sessionModel.setOrganizationID((int)cRow.getCell(2, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
        sessionModel.setAddressID((int)cRow.getCell(3, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
        sessionModel.setName(cRow.getCell(4, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
        sessionModel.setSurname(cRow.getCell(5, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
        sessionModel.setDescription(cRow.getCell(6, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
        sessionModel.setEmail(cRow.getCell(7, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
        sessionModel.setWebsite(cRow.getCell(8, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
        sessionModel.setPhone(cRow.getCell(9, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
        sessionModel.setPassword(cRow.getCell(10, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
        sessionModel.setSalt(cRow.getCell(11, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
        //sessionModel.setCreateDate(cRow.getCell(12, Row.CREATE_NULL_AS_BLANK).getDateCellValue());

        //sessionModel.setPhoto(cBitmap.getBitmapFromAssets(context,"profile.png"));

        sessionDBA.addSessionFromExcel(sessionModel);
    }

    public void addRoleFromExcel(Row cRow) {
        roleModel = new cRoleModel();

        roleModel.setRoleID((int)cRow.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
        roleModel.setOrganizationID((int)cRow.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
        roleModel.setName(cRow.getCell(2, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
        roleModel.setDescription(cRow.getCell(3, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
        //roleModel.setCreateDate(cRow.getCell(4, Row.CREATE_NULL_AS_BLANK).getDateCellValue());

        //Gson gson = new Gson();
        //Log.d(TAG, gson.toJson(roleModel));

        roleDBA.addRoleFromExcel(roleModel);
    }

    public void addMenuFromExcel(Row cRow) {
        menuModel = new cMenuModel();

        menuModel.setMenuID((int)cRow.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
        menuModel.setParentID((int)cRow.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
        menuModel.setName(cRow.getCell(2, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
        menuModel.setDescription(cRow.getCell(3, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
        //menuModel.setCreateDate(cRow.getCell(4, Row.CREATE_NULL_AS_BLANK).getDateCellValue());

        menuDBA.addMenuFromExcel(menuModel);
    }

    public void addPrivilegeFromExcel(Row cRow) {
        privilegeModel = new cPrivilegeModel();

        privilegeModel.setOrganizationID((int)cRow.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
        privilegeModel.setPrivilegeID((int)cRow.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
        privilegeModel.setName(cRow.getCell(2, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
        privilegeModel.setDescription(cRow.getCell(3, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
        //privilegeModel.setCreateDate(cRow.getCell(3, Row.CREATE_NULL_AS_BLANK).getDateCellValue());

        privilegeDBA.addPrivilegeFromExcel(privilegeModel);
    }

    public void addOperationFromExcel(Row cRow) {
        operationModel = new cOperationModel();

        operationModel.setOperationID((int)cRow.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
        operationModel.setName(cRow.getCell(1, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
        operationModel.setDescription(cRow.getCell(2, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
        //operationModel.setCreateDate(cRow.getCell(3, Row.CREATE_NULL_AS_BLANK).getDateCellValue());

        operationDBA.addOperationFromExcel(operationModel);
    }

    public void addEntityFromExcel(Row cRow) {
        entityModel = new cEntityModel();

        entityModel.setEntityID((int)cRow.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
        entityModel.setTypeID((int)cRow.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
        entityModel.setName(cRow.getCell(2, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
        entityModel.setDescription(cRow.getCell(3, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
        //entityModel.setCreateDate(cRow.getCell(4, Row.CREATE_NULL_AS_BLANK).getDateCellValue());

        entityDBA.addEntityFromExcel(entityModel);
    }

    public void addStatusFromExcel(Row cRow) {
        statusModel = new cStatusModel();

        statusModel.setStatusID((int)cRow.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
        statusModel.setName(cRow.getCell(1, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
        statusModel.setDescription(cRow.getCell(2, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
        //statusModel.setCreateDate(cRow.getCell(3, Row.CREATE_NULL_AS_BLANK).getDateCellValue());

        statusDBA.addStatusFromExcel(statusModel);
    }

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
    public void addPermissionFromExcel(Row cRow) {
        permissionModel = new cPermissionModel();

        privilegeModel  = new cPrivilegeModel();
        entityModel     = new cEntityModel();
        operationModel  = new cOperationModel();
        statusModel     = new cStatusModel();

        permissionModel.setOrganizationID((int)cRow.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
        privilegeModel.setPrivilegeID((int)cRow.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
        entityModel.setEntityID((int)cRow.getCell(2, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
        entityModel.setTypeID((int)cRow.getCell(3, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
        operationModel.setOperationID((int)cRow.getCell(4, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
        statusModel.setStatusID((int)cRow.getCell(5, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());

        permissionModel.setPrivilegeModel(privilegeModel);
        permissionModel.setEntityModel(entityModel);
        permissionModel.setOperationModel(operationModel);
        permissionModel.setStatusModel(statusModel);

        permissionDBA.addPermissionFromExcel(permissionModel);

        //permissionModel.setStatusID((int)cRow.getCell(4, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
        //permissionModel.setCreateDate(cRow.getCell(5, Row.CREATE_NULL_AS_BLANK).getDateCellValue());

        //int statusSum = operationStatusHandler.getSumOfStatusIDs(permissionModel.getOperationID());
        //permissionModel.setStatuses(statusSum);

    }
}
