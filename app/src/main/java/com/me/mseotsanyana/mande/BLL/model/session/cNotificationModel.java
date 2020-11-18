package com.me.mseotsanyana.mande.BLL.model.session;

import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class cNotificationModel {
    private int notificationID;
    private int entityID;
    private int entityTypeID;
    private int operationID;
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
    private cOperationModel operationModel;

    private Set<cUserModel> publisherModelSet;
    private Set<cUserModel> subscriberModelSet;
    private Set<cSettingModel> settingModelSet;

    public cNotificationModel(){
        publisherModelSet = new HashSet<>();
        subscriberModelSet = new HashSet<>();
        settingModelSet = new HashSet<>();
    }

    public cNotificationModel(cNotificationModel notificationModel){
        this.setNotificationID(notificationModel.getNotificationID());
        this.setEntityID(notificationModel.getEntityID());
        this.setEntityTypeID(notificationModel.getEntityTypeID());
        this.setOperationID(notificationModel.getOperationID());
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

    public int getOperationID() {
        return operationID;
    }

    public void setOperationID(int operationID) {
        this.operationID = operationID;
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

    public cOperationModel getOperationModel() {
        return operationModel;
    }

    public void setOperationModel(cOperationModel operationModel) {
        this.operationModel = operationModel;
    }

    public Set<cUserModel> getPublisherModelSet() {
        return publisherModelSet;
    }

    public void setPublisherModelSet(Set<cUserModel> publisherModelSet) {
        this.publisherModelSet = publisherModelSet;
    }

    public Set<cUserModel> getSubscriberModelSet() {
        return subscriberModelSet;
    }

    public void setSubscriberModelSet(Set<cUserModel> subscriberModelSet) {
        this.subscriberModelSet = subscriberModelSet;
    }

    public Set<cSettingModel> getSettingModelSet() {
        return settingModelSet;
    }

    public void setSettingModelSet(Set<cSettingModel> settingModelSet) {
        this.settingModelSet = settingModelSet;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof cNotificationModel)) return false;
        cNotificationModel that = (cNotificationModel) o;
        return getNotificationID() == that.getNotificationID() &&
                getEntityID() == that.getEntityID() &&
                getEntityTypeID() == that.getEntityTypeID() &&
                getOperationID() == that.getOperationID() &&
                getServerID() == that.getServerID() &&
                getOwnerID() == that.getOwnerID() &&
                getOrgID() == that.getOrgID() &&
                getGroupBITS() == that.getGroupBITS() &&
                getPermsBITS() == that.getPermsBITS() &&
                getStatusBITS() == that.getStatusBITS() &&
                Objects.equals(getName(), that.getName()) &&
                Objects.equals(getDescription(), that.getDescription()) &&
                Objects.equals(getCreatedDate(), that.getCreatedDate()) &&
                Objects.equals(getModifiedDate(), that.getModifiedDate()) &&
                Objects.equals(getSyncedDate(), that.getSyncedDate()) &&
                Objects.equals(getEntityModel(), that.getEntityModel()) &&
                Objects.equals(getOperationModel(), that.getOperationModel()) &&
                Objects.equals(getPublisherModelSet(), that.getPublisherModelSet()) &&
                Objects.equals(getSubscriberModelSet(), that.getSubscriberModelSet()) &&
                Objects.equals(getSettingModelSet(), that.getSettingModelSet());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNotificationID(), getEntityID(), getEntityTypeID(),
                getOperationID(), getServerID(), getOwnerID(), getOrgID(), getGroupBITS(),
                getPermsBITS(), getStatusBITS(), getName(), getDescription(), getCreatedDate(),
                getModifiedDate(), getSyncedDate(), getEntityModel(), getOperationModel(),
                getPublisherModelSet(), getSubscriberModelSet(), getSettingModelSet());
    }
}
