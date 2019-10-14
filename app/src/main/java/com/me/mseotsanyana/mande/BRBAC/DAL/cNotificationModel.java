package com.me.mseotsanyana.mande.BRBAC.DAL;

import java.util.Date;
import java.util.Set;

public class cNotificationModel {
    private int notificationID;
    private int entityID;
    private int entityTypeID;
    private int serverID;
    private int ownerID;
    private int orgID;
    private int groupBITS;
    private int permsBITS;
    private int statusBITS;
    private String name;
    private String description;
    private Date createdDate;
    private Date modifiedDate;
    private Date syncedDate;

    private cEntityModel entityModel;

    private Set<cUserModel> publisherModels;
    private Set<cUserModel> subscriberModels;
    private Set<cSettingModel> settingModels;

    cNotificationModel(){}

    cNotificationModel(cNotificationModel notificationModel){
        this.setNotificationID(notificationModel.getNotificationID());
        this.setEntityID(notificationModel.getEntityID());
        this.setEntityTypeID(notificationModel.getEntityTypeID());
        this.setServerID(notificationModel.getServerID());
        this.setOwnerID(notificationModel.getOwnerID());
        this.setOrgID(notificationModel.getOrgID());
        this.setGroupBITS(notificationModel.getGroupBITS());
        this.setPermsBITS(notificationModel.getPermsBITS());
        this.setStatusBITS(notificationModel.getStatusBITS());
        this.setName(notificationModel.getName());
        this.setDescription(notificationModel.getDescription());
        this.setCreatedDate(notificationModel.getCreatedDate());
        this.setModifiedDate(notificationModel.getModifiedDate());
        this.setSyncedDate(notificationModel.getSyncedDate());
    }

    public int getNotificationID() {
        return notificationID;
    }

    public void setNotificationID(int notificationID) {
        this.notificationID = notificationID;
    }

    public int getEntityID() {
        return entityID;
    }

    public void setEntityID(int entityID) {
        this.entityID = entityID;
    }

    public int getEntityTypeID() {
        return entityTypeID;
    }

    public void setEntityTypeID(int entityTypeID) {
        this.entityTypeID = entityTypeID;
    }

    public int getServerID() {
        return serverID;
    }

    public void setServerID(int serverID) {
        this.serverID = serverID;
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

    public cEntityModel getEntityModel() {
        return entityModel;
    }

    public void setEntityModel(cEntityModel entityModel) {
        this.entityModel = entityModel;
    }

    public Set<cUserModel> getPublisherModels() {
        return publisherModels;
    }

    public void setPublisherModels(Set<cUserModel> publisherModels) {
        this.publisherModels = publisherModels;
    }

    public Set<cUserModel> getSubscriberModels() {
        return subscriberModels;
    }

    public void setSubscriberModels(Set<cUserModel> subscriberModels) {
        this.subscriberModels = subscriberModels;
    }

    public Set<cSettingModel> getSettingModels() {
        return settingModels;
    }

    public void setSettingModels(Set<cSettingModel> settingModels) {
        this.settingModels = settingModels;
    }
}
