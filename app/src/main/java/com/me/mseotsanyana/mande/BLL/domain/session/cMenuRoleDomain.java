package com.me.mseotsanyana.mande.BLL.domain.session;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by mseotsanyana on 2017/06/27.
 */

public class cMenuRoleDomain implements Parcelable{
    private int menuID;
    private int roleID;
    private int ownerID;
    private int groupBITS;
    private int permsBITS;
    private int typeBITS;
    private int statusBITS;
    private Date createDate;

    public cMenuRoleDomain(){}

    protected cMenuRoleDomain(Parcel in) {
        this.setMenuID(in.readInt());
        this.setRoleID(in.readInt());
        this.setOwnerID(in.readInt());
        this.setGroupBITS(in.readInt());
        this.setPermsBITS(in.readInt());
        this.setTypeBITS(in.readInt());
        this.setStatusBITS(in.readInt());
        this.setCreateDate(new Date(in.readLong()));
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(this.getMenuID());
        out.writeInt(this.getRoleID());
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

    public static final Creator<cMenuRoleDomain> CREATOR = new Creator<cMenuRoleDomain>() {
        @Override
        public cMenuRoleDomain createFromParcel(Parcel in) {
            return new cMenuRoleDomain(in);
        }

        @Override
        public cMenuRoleDomain[] newArray(int size) {
            return new cMenuRoleDomain[size];
        }
    };

    public int getMenuID() {
        return menuID;
    }

    public void setMenuID(int menuID) {
        this.menuID = menuID;
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

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

}
