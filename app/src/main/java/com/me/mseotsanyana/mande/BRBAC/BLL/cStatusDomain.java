package com.me.mseotsanyana.mande.BRBAC.BLL;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by mseotsanyana on 2017/08/24.
 */

public class cStatusDomain implements Parcelable{
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

    public cStatusDomain(){}

    public cStatusDomain(cStatusDomain statusDomain){
        this.statusID = statusDomain.getStatusID();
        this.ownerID = statusDomain.getOwnerID();
        this.groupBITS = statusDomain.getGroupBITS();
        this.permsBITS = statusDomain.getPermsBITS();
        this.statusBITS = statusDomain.getStatusBITS();
        this.name = statusDomain.getName();
        this.description = statusDomain.getDescription();
        this.createdDate = statusDomain.getCreatedDate();
    }

    protected cStatusDomain(Parcel in) {
        this.setStatusID(in.readInt());
        this.setOwnerID(in.readInt());
        this.setGroupBITS(in.readInt());
        this.setPermsBITS(in.readInt());
        this.setStatusBITS(in.readInt());
        this.setName(in.readString());
        this.setDescription(in.readString());
        this.setCreatedDate(new Date(in.readLong()));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<cStatusDomain> CREATOR = new Creator<cStatusDomain>() {
        @Override
        public cStatusDomain createFromParcel(Parcel in) {
            return new cStatusDomain(in);
        }

        @Override
        public cStatusDomain[] newArray(int size) {
            return new cStatusDomain[size];
        }
    };

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(this.getStatusID());
        out.writeInt(this.getOwnerID());
        out.writeInt(this.getGroupBITS());
        out.writeInt(this.getPermsBITS());
        out.writeInt(this.getStatusBITS());
        out.writeString(this.getName());
        out.writeString(this.getDescription());
        out.writeLong(this.getCreatedDate().getTime());
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
}
