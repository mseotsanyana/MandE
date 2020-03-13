package com.me.mseotsanyana.mande.DAL.model.session;


import java.util.Date;
import java.util.Set;

public class cSessionModel {
    private int sessionID;
    private int userID;
    private int serverID;
    private int ownerID;
    private int orgID;
    private int groupBITS;
    private int permsBITS;
    private int statusBITS;
    private Date createdDate;
    private Date modifiedDate;
    private Date syncedDate;

    private cUserModel userModel;
    private Set<cRoleModel> roleModelSet;

    public cSessionModel(){}

    public cSessionModel(cSessionModel sessionModel){
        this.setSessionID(sessionModel.getSessionID());
        this.setUserID(sessionModel.getUserID());
        this.setServerID(sessionModel.getServerID());
        this.setOwnerID(sessionModel.getOwnerID());
        this.setOrgID(sessionModel.getOrgID());
        this.setGroupBITS(sessionModel.getGroupBITS());
        this.setPermsBITS(sessionModel.getPermsBITS());
        this.setStatusBITS(sessionModel.getStatusBITS());
        this.setCreatedDate(sessionModel.getCreatedDate());
        this.setModifiedDate(sessionModel.getModifiedDate());
        this.setSyncedDate(sessionModel.getSyncedDate());
    }

    public int getSessionID() {
        return sessionID;
    }

    public void setSessionID(int sessionID) {
        this.sessionID = sessionID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(int ownerID) {
        this.ownerID = ownerID;
    }

    public int getOrgID() {
        return orgID;
    }

    public void setOrgID(int orgID) {
        this.orgID = orgID;
    }

    public int getServerID() {
        return serverID;
    }

    public void setServerID(int serverID) {
        this.serverID = serverID;
    }

    public int getGroupBITS() {
        return groupBITS;
    }

    public void setGroupBITS(int groupBITS) {
        this.groupBITS = groupBITS;
    }

    public int getPermsBITS() {
        return permsBITS;
    }

    public void setPermsBITS(int permsBITS) {
        this.permsBITS = permsBITS;
    }

    public int getStatusBITS() {
        return statusBITS;
    }

    public void setStatusBITS(int statusBITS) {
        this.statusBITS = statusBITS;
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

    public Date getSyncedDate() {
        return syncedDate;
    }

    public void setSyncedDate(Date syncedDate) {
        this.syncedDate = syncedDate;
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
