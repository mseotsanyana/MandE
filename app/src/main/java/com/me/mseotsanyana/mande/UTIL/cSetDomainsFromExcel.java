package com.me.mseotsanyana.mande.UTIL;

import android.content.Context;

import com.me.mseotsanyana.mande.BLL.domain.session.cOperationDomain;
import com.me.mseotsanyana.mande.BLL.domain.session.cPermissionDomain;
import com.me.mseotsanyana.mande.BLL.domain.session.cSessionDomain;
import com.me.mseotsanyana.mande.BLL.domain.logframe.cActivityDomain;
import com.me.mseotsanyana.mande.UTIL.BLL.cDateDomain;
import com.me.mseotsanyana.mande.UTIL.BLL.cDateHandler;
import com.me.mseotsanyana.mande.BLL.domain.session.cMenuDomain;
import com.me.mseotsanyana.mande.BLL.domain.session.cMenuRoleDomain;
import com.me.mseotsanyana.mande.UTIL.BLL.cGoalDomain;
import com.me.mseotsanyana.mande.BLL.domain.session.cRoleDomain;
import com.me.mseotsanyana.mande.BLL.domain.session.cEntityDomain;
import com.me.mseotsanyana.mande.BLL.domain.logframe.cImpactDomain;
import com.me.mseotsanyana.mande.BLL.domain.session.cOrganizationDomain;
import com.me.mseotsanyana.mande.BLL.domain.logframe.cOutcomeDomain;
import com.me.mseotsanyana.mande.UTIL.BLL.cOutcomeOutputDomain;
import com.me.mseotsanyana.mande.UTIL.BLL.cOutputActivityDomain;
import com.me.mseotsanyana.mande.BLL.domain.logframe.cOutputDomain;
import com.me.mseotsanyana.mande.UTIL.BLL.cProjectDomain;
import com.me.mseotsanyana.mande.UTIL.BLL.cProjectOutcomeDomain;
import com.me.mseotsanyana.mande.UTIL.BLL.cSpecificAimDomain;
import com.me.mseotsanyana.mande.BLL.domain.session.cStatusDomain;
import com.me.mseotsanyana.mande.BLL.domain.session.cUserDomain;
import com.me.mseotsanyana.mande.BLL.domain.session.cValueDomain;

import org.apache.poi.ss.usermodel.Row;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by mseotsanyana on 2017/05/23.
 */

public class cSetDomainsFromExcel {
    private cUserDomain userDomain;
    private cSessionDomain sessionDomain;
    private cRoleDomain roleDomain;
    private cMenuDomain menuDomain;
    private cOperationDomain operationDomain;
    private cEntityDomain entityDomain;
    private cTypeDomain typeDomain;
    private cStatusDomain statusDomain;
    //private cUserRoleDomain userRoleDomain;
    //private cSessionRoleDomain sessionRoleDomain;
    private cMenuRoleDomain menuRoleDomain;
    private cPermissionDomain permissionDomain;

    private cDateDomain dateDomain;

    private cOrganizationDomain organizationDomain;
    private cValueDomain valueDomain;
    private cGoalDomain goalDomain;
    private cSpecificAimDomain specificAimDomain;
    private cImpactDomain objectiveDomain;
    private cProjectDomain projectDomain;
    private cOutcomeDomain outcomeDomain;
    private cOutputDomain outputDomain;
    private cActivityDomain activityDomain;
    private cProjectOutcomeDomain projectOutcomeDomain;
    private cOutcomeOutputDomain outcomeOutputDomain;
    private cOutputActivityDomain outputActivityDomain;

    private cDateHandler dateHandler;

    private Context context;
    public cSetDomainsFromExcel(Context context){
        this.context = context;
    }


    // store the excel row into the organization domain object
    public cOrganizationDomain getOrganizationFromExcel(Row cRow){
        organizationDomain = new cOrganizationDomain();

        organizationDomain.setOrganizationID((int)cRow.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
        organizationDomain.setName(cRow.getCell(1, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
        //organizationDomain.setPhysicalAddress(cRow.getCell(2, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
        organizationDomain.setPhone(cRow.getCell(3, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
        organizationDomain.setFax(cRow.getCell(4, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
        organizationDomain.setVision(cRow.getCell(5, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
        organizationDomain.setMission(cRow.getCell(6, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
        organizationDomain.setEmail(cRow.getCell(7, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
        organizationDomain.setWebsite(cRow.getCell(8, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
        //organizationDomain.setCreateDate(cRow.getCell(9, Row.CREATE_NULL_AS_BLANK).getDateCellValue());

        return organizationDomain;
    }

    // store the excel row into the organization values domain object
    public cValueDomain getValueFromExcel(Row cRow){
        valueDomain = new cValueDomain();

        valueDomain.setValueID((int)cRow.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
        valueDomain.setOrganizationID((int)cRow.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
        valueDomain.setValueName(cRow.getCell(2, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
        //valueDomain.setCreateDate(cRow.getCell(3, Row.CREATE_NULL_AS_BLANK).getDateCellValue());

        return valueDomain;
    }

    public cUserDomain getUserFromExcel(Row cRow) {
        userDomain = new cUserDomain();

        userDomain.setUserID((int)cRow.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
        userDomain.setOrganizationID((int)cRow.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
        userDomain.setName(cRow.getCell(2, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
        userDomain.setSurname(cRow.getCell(3, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
        userDomain.setDescription(cRow.getCell(4, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
        userDomain.setEmail(cRow.getCell(5, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
        userDomain.setPassword(cRow.getCell(6, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
        userDomain.setSalt(cRow.getCell(7, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
        //userDomain.setCreateDate(cRow.getCell(8, Row.CREATE_NULL_AS_BLANK).getDateCellValue());

        return userDomain;
    }

    public cSessionDomain getSessionFromExcel(Row cRow) {
        sessionDomain = new cSessionDomain();

        sessionDomain.setSessionID((int)cRow.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
        sessionDomain.setUserID((int)cRow.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
        sessionDomain.setName(cRow.getCell(2, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
        sessionDomain.setDescription(cRow.getCell(3, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
        //sessionDomain.setCreateDate(cRow.getCell(4, Row.CREATE_NULL_AS_BLANK).getDateCellValue());

        return sessionDomain;
    }

    public cRoleDomain getRoleFromExcel(Row cRow) {
        roleDomain = new cRoleDomain();

        roleDomain.setRoleID((int)cRow.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
        roleDomain.setOrganizationID((int)cRow.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
        roleDomain.setName(cRow.getCell(2, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
        roleDomain.setDescription(cRow.getCell(3, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
        //roleDomain.setCreateDate(cRow.getCell(4, Row.CREATE_NULL_AS_BLANK).getDateCellValue());

        return roleDomain;
    }

    public cMenuDomain getMenuFromExcel(Row cRow) {
        menuDomain = new cMenuDomain();

        menuDomain.setMenuID((int)cRow.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
        menuDomain.setParentID((int)cRow.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
        menuDomain.setName(cRow.getCell(2, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
        menuDomain.setDescription(cRow.getCell(3, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
        //menuDomain.setCreateDate(cRow.getCell(4, Row.CREATE_NULL_AS_BLANK).getDateCellValue());

        return menuDomain;
    }
    public cOperationDomain getOperationFromExcel(Row cRow) {
        operationDomain = new cOperationDomain();

        operationDomain.setOperationID((int)cRow.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
        operationDomain.setName(cRow.getCell(1, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
        operationDomain.setDescription(cRow.getCell(2, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
        //operationDomain.setCreateDate(cRow.getCell(3, Row.CREATE_NULL_AS_BLANK).getDateCellValue());

        return operationDomain;
    }

    public cEntityDomain getEntityFromExcel(Row cRow) {
        entityDomain = new cEntityDomain();

        entityDomain.setEntityID((int)cRow.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
        entityDomain.setName(cRow.getCell(1, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
        entityDomain.setDescription(cRow.getCell(2, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
        //entityDomain.setCreateDate(cRow.getCell(3, Row.CREATE_NULL_AS_BLANK).getDateCellValue());

        return entityDomain;
    }

    public cTypeDomain getTypeFromExcel(Row cRow) {
        typeDomain = new cTypeDomain();

        typeDomain.setTypeID((int)cRow.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
        typeDomain.setName(cRow.getCell(1, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
        typeDomain.setDescription(cRow.getCell(2, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
        //typeDomain.setCreateDate(cRow.getCell(3, Row.CREATE_NULL_AS_BLANK).getDateCellValue());

        return typeDomain;
    }

    public cStatusDomain getStatusFromExcel(Row cRow) {
        statusDomain = new cStatusDomain();

        statusDomain.setStatusID((int)cRow.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
        statusDomain.setName(cRow.getCell(1, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
        statusDomain.setDescription(cRow.getCell(2, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
        //statusDomain.setCreateDate(cRow.getCell(3, Row.CREATE_NULL_AS_BLANK).getDateCellValue());

        return statusDomain;
    }

    public cPermissionDomain getPermissionFromExcel(Row cRow) {
        permissionDomain = new cPermissionDomain();
/*
        permissionDomain.setPrivilegeID((int)cRow.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
        permissionDomain.setEntityID((int)cRow.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
        permissionDomain.setOperationID((int)cRow.getCell(2, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
        permissionDomain.setStatuses((int)cRow.getCell(3, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
        //permissionDomain.setCreateDate(cRow.getCell(4, Row.CREATE_NULL_AS_BLANK).getDateCellValue());
*/
        return permissionDomain;
    }
/*
    public cUserRoleDomain getUserRoleFromExcel(Row cRow) {
        userRoleDomain = new cUserRoleDomain();

        userRoleDomain.setUserID((int)cRow.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
        userRoleDomain.setRoleID((int)cRow.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
        //userRoleDomain.setCreateDate(cRow.getCell(2, Row.CREATE_NULL_AS_BLANK).getDateCellValue());

        return userRoleDomain;
    }

    public cSessionRoleDomain getSessionRoleFromExcel(Row cRow) {
        sessionRoleDomain = new cSessionRoleDomain();

        sessionRoleDomain.setSessionID((int)cRow.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
        sessionRoleDomain.setRoleID((int)cRow.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
        //sessionRoleDomain.setCreateDate(cRow.getCell(2, Row.CREATE_NULL_AS_BLANK).getDateCellValue());

        return sessionRoleDomain;
    }
*/
    public cMenuRoleDomain getMenuRoleFromExcel(Row cRow) {
        menuRoleDomain = new cMenuRoleDomain();

        menuRoleDomain.setMenuID((int)cRow.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
        menuRoleDomain.setRoleID((int)cRow.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
        //menuRoleDomain.setCreateDate(cRow.getCell(2, Row.CREATE_NULL_AS_BLANK).getDateCellValue());

        return menuRoleDomain;
    }
/*
    public cPermissionRoleDomain getPermissionRoleFromExcel(Row cRow) {
        permissionRoleDomain = new cPermissionRoleDomain();

        permissionRoleDomain.setPermissionID((int)cRow.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
        permissionRoleDomain.setRoleID((int)cRow.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
        permissionRoleDomain.setEntityID((int)cRow.getCell(2, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
        permissionRoleDomain.setOperationID((int)cRow.getCell(3, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
        permissionRoleDomain.setTypeID((int)cRow.getCell(4, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
        permissionRoleDomain.setStatusID((int)cRow.getCell(5, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
        permissionRoleDomain.setRoleID((int)cRow.getCell(6, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
        permissionRoleDomain.setCreateDate(cRow.getCell(7, Row.CREATE_NULL_AS_BLANK).getDateCellValue());

        return permissionRoleDomain;
    }
    */

    // store the excel row into the date dim domain object
    public cDateDomain getDateFromExcel(Row cRow){

        Date start_date = cRow.getCell(0, Row.CREATE_NULL_AS_BLANK).getDateCellValue();
        Date end_date = cRow.getCell(1, Row.CREATE_NULL_AS_BLANK).getDateCellValue();

        Calendar cal_start_date = Calendar.getInstance();
        Calendar cal_end_date   = Calendar.getInstance();
        Calendar cal_curr_date  = Calendar.getInstance();

        cal_start_date.setTime(start_date);
        cal_curr_date.setTime(start_date);
        cal_end_date.setTime(end_date);

        while (cal_curr_date.before(cal_end_date)){
            dateDomain = new cDateDomain();

            dateDomain.setDateID(((cal_curr_date.get(Calendar.YEAR) * 10000) +
                    (cal_curr_date.get(Calendar.MONTH) * 100) +
                    (cal_curr_date.get(Calendar.DAY_OF_MONTH))));
            dateDomain.setOwnerID(8);
            dateDomain.setYear(cal_curr_date.get(Calendar.YEAR));
            dateDomain.setMonth(cal_curr_date.get(Calendar.MONTH));
            //dateDomain.setQuarter(cal_curr_date.get(Calendar.));
            dateDomain.setDayMonth(cal_curr_date.get(Calendar.DAY_OF_MONTH));
            dateDomain.setDayWeek(cal_curr_date.get(Calendar.DAY_OF_WEEK));
            dateDomain.setDayYear(cal_curr_date.get(Calendar.DAY_OF_YEAR));
            dateDomain.setDayWeekMonth(cal_curr_date.get(Calendar.DAY_OF_WEEK_IN_MONTH));
            dateDomain.setWeekYear(cal_curr_date.get(Calendar.WEEK_OF_YEAR));
            dateDomain.setWeekMonth(cal_curr_date.get(Calendar.WEEK_OF_MONTH));
            //dateDomain.setNameQuarter(cal_curr_date.ge);

            // add the date dim instance in a db
            dateHandler.addDate(dateDomain);

            // add one day to current date
            cal_curr_date.add(Calendar.DATE, 1);
        }

        return dateDomain;
    }


    // store the excel row into the goal (overall aim) domain object
    public cGoalDomain getGoalFromExcel(Row cRow){
        goalDomain = new cGoalDomain();

        goalDomain.setGoalID((int)cRow.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
        goalDomain.setOrganizationID((int)cRow.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
        goalDomain.setOwnerID((int)cRow.getCell(2, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
        goalDomain.setGoalName(cRow.getCell(3, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
        goalDomain.setGoalDescription(cRow.getCell(4, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
        goalDomain.setCreateDate(cRow.getCell(5, Row.CREATE_NULL_AS_BLANK).getDateCellValue());

        return goalDomain;
    }

    // store the excel row into the specific aim domain object
    public cSpecificAimDomain getSpecificAimFromExcel(Row cRow){
        specificAimDomain = new cSpecificAimDomain();

        specificAimDomain.setProjectID((int)cRow.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
        specificAimDomain.setOverallAimID((int)cRow.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
        specificAimDomain.setOwnerID((int)cRow.getCell(2, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
        specificAimDomain.setSpecificAimName(cRow.getCell(3, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
        specificAimDomain.setSpecificAimDescription(cRow.getCell(4, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
        specificAimDomain.setCreateDate(cRow.getCell(5, Row.CREATE_NULL_AS_BLANK).getDateCellValue());

        return specificAimDomain;
    }

    // store the excel row into the project domain object
    public cProjectDomain getProjectFromExcel(Row cRow){
        projectDomain = new cProjectDomain();

        projectDomain.setProjectID((int)cRow.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
        projectDomain.setOverallAimID((int)cRow.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
        projectDomain.setSpecificAimID((int)cRow.getCell(2, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
        projectDomain.setOwnerID((int)cRow.getCell(3, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
        projectDomain.setProjectManagerID((int)cRow.getCell(4, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
        projectDomain.setProjectName(cRow.getCell(5, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
        projectDomain.setProjectDescription(cRow.getCell(6, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
        projectDomain.setCountry(cRow.getCell(7, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
        projectDomain.setRegion(cRow.getCell(8, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
        projectDomain.setProjectStatus((int)cRow.getCell(9, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
        projectDomain.setCreateDate(cRow.getCell(10, Row.CREATE_NULL_AS_BLANK).getDateCellValue());
        projectDomain.setStartDate(cRow.getCell(11, Row.CREATE_NULL_AS_BLANK).getDateCellValue());
        projectDomain.setCloseDate(cRow.getCell(12, Row.CREATE_NULL_AS_BLANK).getDateCellValue());

        return projectDomain;
    }

    // store the excel row into the project outcome domain object
    public cProjectOutcomeDomain getProjectOutcomeFromExcel(Row cRow){
        projectOutcomeDomain = new cProjectOutcomeDomain();

        projectOutcomeDomain.setProjectID((int)cRow.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
        projectOutcomeDomain.setOutcomeID((int)cRow.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
        projectOutcomeDomain.setOwnerID((int)cRow.getCell(2, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
        projectOutcomeDomain.setCreateDate(cRow.getCell(3, Row.CREATE_NULL_AS_BLANK).getDateCellValue());

        return projectOutcomeDomain;
    }

    // store the excel row into the output domain object
    public cImpactDomain getObjectiveFromExcel(Row cRow){
        objectiveDomain = new cImpactDomain();

        objectiveDomain.setObjectiveID((int)cRow.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
        objectiveDomain.setProjectID((int)cRow.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
        objectiveDomain.setOwnerID((int)cRow.getCell(2, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
        objectiveDomain.setObjectiveName(cRow.getCell(3, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
        objectiveDomain.setObjectiveDescription(cRow.getCell(4, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
        objectiveDomain.setCreateDate(cRow.getCell(5, Row.CREATE_NULL_AS_BLANK).getDateCellValue());

        return objectiveDomain;
    }

    // store the excel row into the outcome domain object
    public cOutcomeDomain getOutcomeFromExcel(Row cRow){
        outcomeDomain = new cOutcomeDomain();

        outcomeDomain.setOutcomeID((int)cRow.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
        outcomeDomain.setOwnerID((int)cRow.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
        outcomeDomain.setOutcomeName(cRow.getCell(2, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
        outcomeDomain.setOutcomeDescription(cRow.getCell(3, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
        outcomeDomain.setCreateDate(cRow.getCell(4, Row.CREATE_NULL_AS_BLANK).getDateCellValue());

        return outcomeDomain;
    }

    // store the excel row into the outcome output domain object
    public cOutcomeOutputDomain getOutcomeOutputFromExcel(Row cRow){
        outcomeOutputDomain = new cOutcomeOutputDomain();

        outcomeOutputDomain.setOutcomeID((int)cRow.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
        outcomeOutputDomain.setOutputID((int)cRow.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
        outcomeOutputDomain.setOwnerID((int)cRow.getCell(2, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
        outcomeOutputDomain.setCreateDate(cRow.getCell(3, Row.CREATE_NULL_AS_BLANK).getDateCellValue());

        return outcomeOutputDomain;
    }

    // store the excel row into the output domain object
    public cOutputDomain getOutputFromExcel(Row cRow){
        outputDomain = new cOutputDomain();

        outputDomain.setOutputID((int)cRow.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
        outputDomain.setObjectiveID((int)cRow.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
        outputDomain.setOwnerID((int)cRow.getCell(2, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
        outputDomain.setOutputName(cRow.getCell(3, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
        outputDomain.setOutputDescription(cRow.getCell(4, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
        outputDomain.setCreateDate(cRow.getCell(5, Row.CREATE_NULL_AS_BLANK).getDateCellValue());

        return outputDomain;
    }

    // store the excel row into the output activity domain object
    public cOutputActivityDomain getOutputActivityFromExcel(Row cRow){
        outputActivityDomain = new cOutputActivityDomain();

        outputActivityDomain.setOutputID((int)cRow.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
        outputActivityDomain.setActivityID((int)cRow.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
        outputActivityDomain.setOwnerID((int)cRow.getCell(2, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
        outputActivityDomain.setCreateDate(cRow.getCell(3, Row.CREATE_NULL_AS_BLANK).getDateCellValue());

        return outputActivityDomain;
    }

    // store the excel row into the activity domain object
    public cActivityDomain getActivityFromExcel(Row cRow){
        activityDomain = new cActivityDomain();

        activityDomain.setActivityID((int)cRow.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
        activityDomain.setOwnerID((int)cRow.getCell(1, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
        activityDomain.setActivityName(cRow.getCell(2, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
        activityDomain.setActivityDescription(cRow.getCell(3, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
        activityDomain.setCreateDate(cRow.getCell(4, Row.CREATE_NULL_AS_BLANK).getDateCellValue());

        return activityDomain;
    }
}
