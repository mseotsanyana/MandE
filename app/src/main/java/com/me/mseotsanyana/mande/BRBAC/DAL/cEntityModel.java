package com.me.mseotsanyana.mande.BRBAC.DAL;

import java.util.Date;

/**
 * Created by mseotsanyana on 2017/08/24.
 */

public class cEntityModel {
    private int entityID;
    private int typeID;
    private int ownerID;
    private int groupBITS;
    private int permsBITS;
    private int statusBITS;
    private String name;
    private String description;
    private Date createdDate;
    private Date modifiedDate;
    private Date syncedDate;

    public cEntityModel(){}

    public cEntityModel(cEntityModel entityModel){
        this.setEntityID(entityModel.getEntityID());
        this.setTypeID(entityModel.getTypeID());
        this.setOwnerID(entityModel.getOwnerID());
        this.setGroupBITS(entityModel.getGroupBITS());
        this.setPermsBITS(entityModel.getPermsBITS());
        this.setStatusBITS(entityModel.getStatusBITS());
        this.setName(entityModel.getName());
        this.setDescription(entityModel.getDescription());
        this.setCreatedDate(entityModel.getCreatedDate());
        this.setModifiedDate(entityModel.getModifiedDate());
        this.setSyncedDate(entityModel.getSyncedDate());
    }

    public int getEntityID() {
        return entityID;
    }

    public void setEntityID(int entityID) {
        this.entityID = entityID;
    }

    public int getTypeID() {
        return typeID;
    }

    public void setTypeID(int typeID) {
        this.typeID = typeID;
    }

    public int getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(int ownerID) {
        this.ownerID = ownerID;
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
}
