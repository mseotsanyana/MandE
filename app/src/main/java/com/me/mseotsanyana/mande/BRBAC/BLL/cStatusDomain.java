package com.me.mseotsanyana.mande.BRBAC.BLL;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by mseotsanyana on 2017/08/24.
 */

public class cStatusDomain implements Parcelable{
    private int statusID;
    private int ownerID;
    private int groupBITS;
    private int permsBITS;
    private int typeBITS;
    private int statusBITS;
    private String name;
    private String description;
    private Date createDate;

    private boolean dirty;
    private boolean state;

    public cStatusDomain(){}

    public cStatusDomain(cStatusDomain statusDomain){
        this.statusID = statusDomain.getStatusID();
        this.ownerID = statusDomain.getOwnerID();
        this.groupBITS = statusDomain.getGroupBITS();
        this.permsBITS = statusDomain.getPermsBITS();
        this.typeBITS = statusDomain.getTypeBITS();
        this.statusBITS = statusDomain.getStatusBITS();
        this.name = statusDomain.getName();
        this.description = statusDomain.getDescription();
        this.createDate = statusDomain.getCreateDate();
        this.dirty = statusDomain.isDirty();
        this.state = statusDomain.isState();
    }

    protected cStatusDomain(Parcel in) {
        this.setStatusID(in.readInt());
        this.setOwnerID(in.readInt());
        this.setGroupBITS(in.readInt());
        this.setPermsBITS(in.readInt());
        this.setTypeBITS(in.readInt());
        this.setStatusBITS(in.readInt());
        this.setName(in.readString());
        this.setDescription(in.readString());
        this.setCreateDate(new Date(in.readLong()));
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(this.getStatusID());
        out.writeInt(this.getOwnerID());
        out.writeInt(this.getGroupBITS());
        out.writeInt(this.getPermsBITS());
        out.writeInt(this.getTypeBITS());
        out.writeInt(this.getStatusBITS());
        out.writeString(this.getName());
        out.writeString(this.getDescription());
        out.writeLong(this.getCreateDate().getTime());
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

    public int getStatusID() {
        return statusID;
    }

    public void setStatusID(int statusID) {
        this.statusID = statusID;
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

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public boolean isDirty() {
        return dirty;
    }

    public void setDirty(boolean dirty) {
        this.dirty = dirty;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }
}
