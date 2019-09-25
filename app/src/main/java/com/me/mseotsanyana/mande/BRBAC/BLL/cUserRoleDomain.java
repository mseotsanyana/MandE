package com.me.mseotsanyana.mande.BRBAC.BLL;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by mseotsanyana on 2017/08/29.
 */

public class cUserRoleDomain implements Parcelable {
    private int userID;
    private int roleID;
    private int ownerID;
    private int groupBITS;
    private int permsBITS;
    private int typeBITS;
    private int statusBITS;
    private Date createdDate;
    private Date modifiedDate;
    private Date syncedDate;

    public cUserRoleDomain(){}

    protected cUserRoleDomain(Parcel in) {
        this.setUserID(in.readInt());
        this.setRoleID(in.readInt());
        this.setOwnerID(in.readInt());
        this.setGroupBITS(in.readInt());
        this.setPermsBITS(in.readInt());
        this.setTypeBITS(in.readInt());
        this.setStatusBITS(in.readInt());
        this.setCreatedDate(new Date(in.readLong()));
        this.setModifiedDate(new Date(in.readLong()));
        this.setSyncedDate(new Date(in.readLong()));
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(this.getUserID());
        out.writeInt(this.getRoleID());
        out.writeInt(this.getOwnerID());
        out.writeInt(this.getGroupBITS());
        out.writeInt(this.getPermsBITS());
        out.writeInt(this.getTypeBITS());
        out.writeInt(this.getStatusBITS());
        out.writeLong(this.getCreatedDate().getTime());
        out.writeLong(this.getModifiedDate().getTime());
        out.writeLong(this.getSyncedDate().getTime());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<cUserRoleDomain> CREATOR = new Creator<cUserRoleDomain>() {
        @Override
        public cUserRoleDomain createFromParcel(Parcel in) {
            return new cUserRoleDomain(in);
        }

        @Override
        public cUserRoleDomain[] newArray(int size) {
            return new cUserRoleDomain[size];
        }
    };

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getRoleID() {
        return roleID;
    }

    public void setRoleID(int roleID) {
        this.roleID = roleID;
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

    public int getTypeBITS() {
        return typeBITS;
    }

    public void setTypeBITS(int typeBITS) {
        this.typeBITS = typeBITS;
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
}
