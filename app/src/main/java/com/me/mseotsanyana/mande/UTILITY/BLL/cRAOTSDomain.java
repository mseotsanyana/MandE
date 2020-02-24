package com.me.mseotsanyana.mande.UTILITY.BLL;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by mseotsanyana on 2017/08/29.
 */

public class cRAOTSDomain implements Parcelable {
    private int roleID;
    private int actionID;
    private int entityID;
    private int typeID;
    private int statusID;
    private int ownerID;
    private int groupBITS;
    private int permsBITS;
    private int typeBITS;
    private int statusBITS;
    private Date createDate;

    public cRAOTSDomain(){}

    protected cRAOTSDomain(Parcel in) {
        this.setRoleID(in.readInt());
        this.setActionID(in.readInt());
        this.setEntityID(in.readInt());
        this.setTypeID(in.readInt());
        this.setStatusID(in.readInt());
        this.setOwnerID(in.readInt());
        this.setGroupBITS(in.readInt());
        this.setPermsBITS(in.readInt());
        this.setTypeID(in.readInt());
        this.setStatusID(in.readInt());
        this.setCreateDate(new Date(in.readLong()));
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(this.getRoleID());
        out.writeInt(this.getActionID());
        out.writeInt(this.getEntityID());
        out.writeInt(this.getTypeID());
        out.writeInt(this.getStatusID());
        out.writeInt(this.getOwnerID());
        out.writeInt(this.getGroupBITS());
        out.writeInt(this.getPermsBITS());
        out.writeInt(this.getTypeBITS());
        out.writeInt(this.getStatusBITS());
        out.writeLong(this.getCreateDate().getTime());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<cRAOTSDomain> CREATOR = new Creator<cRAOTSDomain>() {
        @Override
        public cRAOTSDomain createFromParcel(Parcel in) {
            return new cRAOTSDomain(in);
        }

        @Override
        public cRAOTSDomain[] newArray(int size) {
            return new cRAOTSDomain[size];
        }
    };

    public int getRoleID() {
        return roleID;
    }

    public void setRoleID(int roleID) {
        this.roleID = roleID;
    }

    public int getActionID() {
        return actionID;
    }

    public void setActionID(int actionID) {
        this.actionID = actionID;
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

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

}
