package com.me.mseotsanyana.mande.DAL.model.evaluator;

import java.util.Date;

public class cMatrixChoiceModel {
    private long matrixChoiceID;
    private long rowID;
    private long colID;
    private long serverID;
    private long ownerID;
    private long orgID;
    private int groupBITS;
    private int permsBITS;
    private int statusBITS;
    private Date createdDate;
    private Date modifiedDate;
    private Date syncedDate;

    private cRowChoiceModel rowOptionModel;
    private cColChoiceModel colOptionModel;

    public cMatrixChoiceModel(){
        rowOptionModel = new cRowChoiceModel();
        colOptionModel = new cColChoiceModel();
    }

    public long getMatrixChoiceID() {
        return matrixChoiceID;
    }

    public void setMatrixChoiceID(long matrixChoiceID) {
        this.matrixChoiceID = matrixChoiceID;
    }

    public long getRowID() {
        return rowID;
    }

    public void setRowID(long rowID) {
        this.rowID = rowID;
    }

    public long getColID() {
        return colID;
    }

    public void setColID(long colID) {
        this.colID = colID;
    }

    public long getServerID() {
        return serverID;
    }

    public void setServerID(long serverID) {
        this.serverID = serverID;
    }

    public long getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(long ownerID) {
        this.ownerID = ownerID;
    }

    public long getOrgID() {
        return orgID;
    }

    public void setOrgID(long orgID) {
        this.orgID = orgID;
    }

    public void setOrgID(int orgID) {
        this.orgID = orgID;
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

    public cRowChoiceModel getRowOptionModel() {
        return rowOptionModel;
    }

    public void setRowOptionModel(cRowChoiceModel rowOptionModel) {
        this.rowOptionModel = rowOptionModel;
    }

    public cColChoiceModel getColOptionModel() {
        return colOptionModel;
    }

    public void setColOptionModel(cColChoiceModel colOptionModel) {
        this.colOptionModel = colOptionModel;
    }
}
