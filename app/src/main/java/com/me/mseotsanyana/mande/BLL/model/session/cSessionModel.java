package com.me.mseotsanyana.mande.BLL.model.session;

import java.util.Date;
import java.util.List;
import java.util.Set;

public class cSessionModel {
    private int sessionServerID;
    private int userServerID;

    private String userOwnerID;
    private String organizationOwnerID;
    private int teamOwnerBIT;
    private List<Integer> unixpermBITS;
    private int statusBIT;

    private Date createdDate;
    private Date modifiedDate;

    private cUserModel userModel;
    private Set<cRoleModel> roleModelSet;

    public cSessionModel(){}

    public cSessionModel(cSessionModel sessionModel){
        this.setCreatedDate(sessionModel.getCreatedDate());
        this.setModifiedDate(sessionModel.getModifiedDate());
    }

    public int getSessionServerID() {
        return sessionServerID;
    }

    public void setSessionServerID(int sessionServerID) {
        this.sessionServerID = sessionServerID;
    }

    public int getUserServerID() {
        return userServerID;
    }

    public void setUserServerID(int userServerID) {
        this.userServerID = userServerID;
    }

    public String getUserOwnerID() {
        return userOwnerID;
    }

    public void setUserOwnerID(String userOwnerID) {
        this.userOwnerID = userOwnerID;
    }

    public String getOrganizationOwnerID() {
        return organizationOwnerID;
    }

    public void setOrganizationOwnerID(String organizationOwnerID) {
        this.organizationOwnerID = organizationOwnerID;
    }

    public int getTeamOwnerBIT() {
        return teamOwnerBIT;
    }

    public void setTeamOwnerBIT(int teamOwnerBIT) {
        this.teamOwnerBIT = teamOwnerBIT;
    }

    public List<Integer> getUnixpermBITS() {
        return unixpermBITS;
    }

    public void setUnixpermBITS(List<Integer> unixpermBITS) {
        this.unixpermBITS = unixpermBITS;
    }

    public int getStatusBIT() {
        return statusBIT;
    }

    public void setStatusBIT(int statusBIT) {
        this.statusBIT = statusBIT;
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

    public cUserModel getUserModel() {
        return userModel;
    }

    public void setUserModel(cUserModel userModel) {
        this.userModel = userModel;
    }

    public Set<cRoleModel> getRoleModelSet() {
        return roleModelSet;
    }

    public void setRoleModelSet(Set<cRoleModel> roleModelSet) {
        this.roleModelSet = roleModelSet;
    }
}
