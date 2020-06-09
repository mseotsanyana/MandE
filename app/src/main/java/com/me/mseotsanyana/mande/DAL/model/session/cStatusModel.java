package com.me.mseotsanyana.mande.DAL.model.session;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;
import java.util.Objects;

/**
 * Created by mseotsanyana on 2017/08/24.
 */

public class cStatusModel implements Parcelable {
    private int statusID;
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

    private boolean state;

    public cStatusModel(){}

    public cStatusModel(cStatusModel statusModel){
        this.setStatusID(statusModel.getStatusID());
        this.setServerID(statusModel.getServerID());
        this.setOwnerID(statusModel.getOwnerID());
        this.setOrgID(statusModel.getOrgID());
        this.setGroupBITS(statusModel.getGroupBITS());
        this.setPermsBITS(statusModel.getPermsBITS());
        this.setStatusBITS(statusModel.getStatusBITS());
        this.setName(statusModel.getName());
        this.setDescription(statusModel.getDescription());
        this.setCreatedDate(statusModel.getCreatedDate());
        this.setModifiedDate(statusModel.getModifiedDate());
        this.setSyncedDate(statusModel.getSyncedDate());
    }

    protected cStatusModel(Parcel in) {
        statusID = in.readInt();
        serverID = in.readInt();
        ownerID = in.readInt();
        orgID = in.readInt();
        groupBITS = in.readInt();
        permsBITS = in.readInt();
        statusBITS = in.readInt();
        name = in.readString();
        description = in.readString();
        state = in.readByte() != 0;
    }

    public int getStatusID() {
        return statusID;
    }

    public void setStatusID(int statusID) {
        this.statusID = statusID;
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

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof cStatusModel)) return false;
        cStatusModel that = (cStatusModel) o;
        return getStatusID() == that.getStatusID() &&
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
                Objects.equals(getSyncedDate(), that.getSyncedDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getStatusID(), getServerID(), getOwnerID(),
                getOrgID(), getGroupBITS(), getPermsBITS(), getStatusBITS(),
                getName(), getDescription(), getCreatedDate(), getModifiedDate(),
                getSyncedDate());
    }

    public static final Creator<cStatusModel> CREATOR = new Creator<cStatusModel>() {
        @Override
        public cStatusModel createFromParcel(Parcel in) {
            return new cStatusModel(in);
        }

        @Override
        public cStatusModel[] newArray(int size) {
            return new cStatusModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(statusID);
        parcel.writeInt(serverID);
        parcel.writeInt(ownerID);
        parcel.writeInt(orgID);
        parcel.writeInt(groupBITS);
        parcel.writeInt(permsBITS);
        parcel.writeInt(statusBITS);
        parcel.writeString(name);
        parcel.writeString(description);
        parcel.writeByte((byte) (state ? 1 : 0));
    }
}
