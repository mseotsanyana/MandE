package com.me.mseotsanyana.mande.BRBAC.BLL;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by mseotsanyana on 2017/08/24.
 */

public class cOperationDomain implements Parcelable {
    private int operationID;
    private int ownerID;
    private int groupBITS;
    private int permsBITS;
    private int statusBITS;
    private String name;
    private String description;
    private Date createDate;
    private Date modifiedDate;
    private Date syncedDate;

    private boolean dirty;
    private boolean state;

    public cOperationDomain() {
    }


    public cOperationDomain(cOperationDomain operationDomain) {
        this.operationID = operationDomain.getOperationID();
        this.ownerID = operationDomain.getOwnerID();
        this.groupBITS = operationDomain.getGroupBITS();
        this.permsBITS = operationDomain.getPermsBITS();
        this.statusBITS = operationDomain.getStatusBITS();
        this.name = operationDomain.getName();
        this.description = operationDomain.getDescription();
        this.createDate = operationDomain.getCreateDate();
        this.modifiedDate = operationDomain.getCreateDate();
        this.syncedDate = operationDomain.getCreateDate();

        this.dirty = operationDomain.isDirty();
        this.state = operationDomain.isState();
    }

    protected cOperationDomain(Parcel in) {
        this.setOperationID(in.readInt());
        this.setOwnerID(in.readInt());
        this.setGroupBITS(in.readInt());
        this.setPermsBITS(in.readInt());
        this.setStatusBITS(in.readInt());
        this.setName(in.readString());
        this.setDescription(in.readString());
        this.setCreateDate(new Date(in.readLong()));
        this.setModifiedDate(new Date(in.readLong()));
        this.setSyncedDate(new Date(in.readLong()));

        //this.setDirty(in.readInt());
        //this.setState(in.boolea);
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(this.getOperationID());
        out.writeInt(this.getOwnerID());
        out.writeInt(this.getGroupBITS());
        out.writeInt(this.getPermsBITS());
        out.writeInt(this.getStatusBITS());
        out.writeString(this.getName());
        out.writeString(this.getDescription());
        out.writeLong(this.getCreateDate().getTime());
        out.writeLong(this.getModifiedDate().getTime());
        out.writeLong(this.getSyncedDate().getTime());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<cOperationDomain> CREATOR = new Creator<cOperationDomain>() {
        @Override
        public cOperationDomain createFromParcel(Parcel in) {
            return new cOperationDomain(in);
        }

        @Override
        public cOperationDomain[] newArray(int size) {
            return new cOperationDomain[size];
        }
    };

    public int getOperationID() {
        return operationID;
    }

    public void setOperationID(int operationID) {
        this.operationID = operationID;
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

