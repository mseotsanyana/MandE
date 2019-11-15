package com.me.mseotsanyana.mande.BRBAC.BLL;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;
import java.util.Objects;
import java.util.Set;

public class cNotificationDomain implements Parcelable {
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

    private cEntityDomain entityDomain;
    private cOperationDomain operationDomain;

    private Set<cUserDomain> publisherModelSet;
    private Set<cUserDomain> subscriberModelSet;
    private Set<cSettingDomain> settingDomainSet;

    public cNotificationDomain() { }

    protected cNotificationDomain(Parcel in) {
        this.setNotificationID(in.readInt());
        this.setEntityID(in.readInt());
        this.setEntityTypeID(in.readInt());
        this.setOperationID(in.readInt());
        this.setServerID(in.readInt());
        this.setOwnerID(in.readInt());
        this.setOrgID(in.readInt());
        this.setGroupBITS(in.readInt());
        this.setPermsBITS(in.readInt());
        this.setStatusBITS(in.readInt());
        this.setName(in.readString());
        this.setDescription(in.readString());
        this.setCreatedDate(new Date(in.readLong()));
        this.setModifiedDate(new Date(in.readLong()));
        this.setSyncedDate(new Date(in.readLong()));

        entityDomain = in.readParcelable(cEntityDomain.class.getClassLoader());
        operationDomain = in.readParcelable(cOperationDomain.class.getClassLoader());
    }


    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(this.getNotificationID());
        out.writeInt(this.getEntityID());
        out.writeInt(this.getEntityTypeID());
        out.writeInt(this.getOperationID());
        out.writeInt(this.getServerID());
        out.writeInt(this.getOwnerID());
        out.writeInt(this.getOrgID());
        out.writeInt(this.getGroupBITS());
        out.writeInt(this.getPermsBITS());
        out.writeInt(this.getStatusBITS());
        out.writeString(this.getName());
        out.writeString(this.getDescription());
        out.writeLong(this.getCreatedDate().getTime());
        out.writeLong(this.getModifiedDate().getTime());
        out.writeLong(this.getSyncedDate().getTime());

        out.writeParcelable(entityDomain, flags);
        out.writeParcelable(operationDomain, flags);

    }

    public static final Creator<cNotificationDomain> CREATOR = new Creator<cNotificationDomain>() {
        @Override
        public cNotificationDomain createFromParcel(Parcel in) {
            return new cNotificationDomain(in);
        }

        @Override
        public cNotificationDomain[] newArray(int size) {
            return new cNotificationDomain[size];
        }
    };

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

    public cEntityDomain getEntityDomain() {
        return entityDomain;
    }

    public void setEntityDomain(cEntityDomain entityDomain) {
        this.entityDomain = entityDomain;
    }

    public cOperationDomain getOperationDomain() {
        return operationDomain;
    }

    public void setOperationDomain(cOperationDomain operationDomain) {
        this.operationDomain = operationDomain;
    }

    public Set<cUserDomain> getPublisherModelSet() {
        return publisherModelSet;
    }

    public void setPublisherModelSet(Set<cUserDomain> publisherModelSet) {
        this.publisherModelSet = publisherModelSet;
    }

    public Set<cUserDomain> getSubscriberModelSet() {
        return subscriberModelSet;
    }

    public void setSubscriberModelSet(Set<cUserDomain> subscriberModelSet) {
        this.subscriberModelSet = subscriberModelSet;
    }

    public Set<cSettingDomain> getSettingDomainSet() {
        return settingDomainSet;
    }

    public void setSettingDomainSet(Set<cSettingDomain> settingDomainSet) {
        this.settingDomainSet = settingDomainSet;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof cNotificationDomain)) return false;
        cNotificationDomain that = (cNotificationDomain) o;
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
                Objects.equals(getEntityDomain(), that.getEntityDomain()) &&
                Objects.equals(getOperationDomain(), that.getOperationDomain()) &&
                Objects.equals(getPublisherModelSet(), that.getPublisherModelSet()) &&
                Objects.equals(getSubscriberModelSet(), that.getSubscriberModelSet()) &&
                Objects.equals(getSettingDomainSet(), that.getSettingDomainSet());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNotificationID(), getEntityID(), getEntityTypeID(),
                getOperationID(), getServerID(), getOwnerID(), getOrgID(), getGroupBITS(),
                getPermsBITS(), getStatusBITS(), getName(), getDescription(), getCreatedDate(),
                getModifiedDate(), getSyncedDate(), getEntityDomain(), getOperationDomain(),
                getPublisherModelSet(), getSubscriberModelSet(), getSettingDomainSet());
    }
}
