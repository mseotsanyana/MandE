package com.me.mseotsanyana.mande.BLL.model.session;

import java.util.Date;

public class cPlanModel {
    private String planServerID;

    private String name;
    private String description;

    private int orgLimit;
    private int teamLimit;
    private int orgUserLimit;
    private int teamUserLimit;

    private Date createdDate;
    private Date modifiedDate;

    public cPlanModel(){
    }

    public String getPlanServerID() {
        return planServerID;
    }

    public void setPlanServerID(String planServerID) {
        this.planServerID = planServerID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public int getOrgLimit() {
        return orgLimit;
    }

    public void setOrgLimit(int orgLimit) {
        this.orgLimit = orgLimit;
    }

    public int getTeamLimit() {
        return teamLimit;
    }

    public void setTeamLimit(int teamLimit) {
        this.teamLimit = teamLimit;
    }

    public int getOrgUserLimit() {
        return orgUserLimit;
    }

    public void setOrgUserLimit(int orgUserLimit) {
        this.orgUserLimit = orgUserLimit;
    }

    public int getTeamUserLimit() {
        return teamUserLimit;
    }

    public void setTeamUserLimit(int teamUserLimit) {
        this.teamUserLimit = teamUserLimit;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }
}
