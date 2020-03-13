package com.me.mseotsanyana.mande.BLL.domain.session;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Created by mseotsanyana on 2017/08/24.
 */

public class cMenuDomain implements Parcelable{
    private int menuID;
    private int parentID;
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

    private Set<cMenuDomain> menuDomainSet;

    public cMenuDomain(){
        menuDomainSet = new HashSet<>();
    }

    protected cMenuDomain(Parcel in) {
        this.setMenuID(in.readInt());
        this.setParentID(in.readInt());
        this.setOwnerID(in.readInt());
        this.setGroupBITS(in.readInt());
        this.setPermsBITS(in.readInt());
        this.setStatusBITS(in.readInt());
        this.setName(in.readString());
        this.setDescription(in.readString());
        this.setCreatedDate(new Date(in.readLong()));
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(this.getMenuID());
        out.writeInt(this.getParentID());
        out.writeInt(this.getOwnerID());
        out.writeInt(this.getGroupBITS());
        out.writeInt(this.getPermsBITS());
        out.writeInt(this.getStatusBITS());
        out.writeString(this.getName());
        out.writeString(this.getDescription());
        out.writeLong(this.getCreatedDate().getTime());
    }

    public int getMenuID() {
        return menuID;
    }

    public void setMenuID(int menuID) {
        this.menuID = menuID;
    }

    public int getParentID() {
        return parentID;
    }

    public void setParentID(int parentID) {
        this.parentID = parentID;
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

    public Set<cMenuDomain> getMenuDomainSet() {
        return menuDomainSet;
    }

    public void setMenuDomainSet(Set<cMenuDomain> menuDomainSet) {
        this.menuDomainSet = menuDomainSet;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<cMenuDomain> CREATOR = new Creator<cMenuDomain>() {
        @Override
        public cMenuDomain createFromParcel(Parcel in) {
            return new cMenuDomain(in);
        }

        @Override
        public cMenuDomain[] newArray(int size) {
            return new cMenuDomain[size];
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof cMenuDomain)) return false;
        cMenuDomain that = (cMenuDomain) o;
        return getMenuID() == that.getMenuID() &&
                getParentID() == that.getParentID() &&
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
                Objects.equals(getMenuDomainSet(), that.getMenuDomainSet());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMenuID(), getParentID(), getServerID(), getOwnerID(),
                getOrgID(), getGroupBITS(), getPermsBITS(), getStatusBITS(), getName(),
                getDescription(), getCreatedDate(), getModifiedDate(), getSyncedDate(),
                getMenuDomainSet());
    }
}
