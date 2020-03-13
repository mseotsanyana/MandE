package com.me.mseotsanyana.mande.BLL.domain.session;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class cPrivilegeDomain implements Parcelable {
    private int privilegeID;
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

    private HashMap<cEntityDomain, Set<cOperationDomain>> permDomainMap;
    private Set<cStatusDomain> statusDomainSet;

    public cPrivilegeDomain() {
        permDomainMap = new HashMap<>();
        statusDomainSet = new HashSet<>();
    }

    protected cPrivilegeDomain(Parcel in) {
        this.setPrivilegeID(in.readInt());
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
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(this.getPrivilegeID());
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
    }

    public int getPrivilegeID() {
        return privilegeID;
    }

    public void setPrivilegeID(int privilegeID) {
        this.privilegeID = privilegeID;
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

    public HashMap<cEntityDomain, Set<cOperationDomain>> getPermDomainMap() {
        return permDomainMap;
    }

    public void setPermDomainMap(HashMap<cEntityDomain, Set<cOperationDomain>> permDomainMap) {
        this.permDomainMap = permDomainMap;
    }

    public Set<cStatusDomain> getStatusDomainSet() {
        return statusDomainSet;
    }

    public void setStatusDomainSet(Set<cStatusDomain> statusDomainSet) {
        this.statusDomainSet = statusDomainSet;
    }

    public static final Creator<cPrivilegeDomain> CREATOR = new Creator<cPrivilegeDomain>() {
        @Override
        public cPrivilegeDomain createFromParcel(Parcel in) {
            return new cPrivilegeDomain(in);
        }

        @Override
        public cPrivilegeDomain[] newArray(int size) {
            return new cPrivilegeDomain[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof cPrivilegeDomain)) return false;
        cPrivilegeDomain that = (cPrivilegeDomain) o;
        return getPrivilegeID() == that.getPrivilegeID() &&
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
        return Objects.hash(getPrivilegeID(), getServerID(), getOwnerID(),
                getOrgID(), getGroupBITS(), getPermsBITS(), getStatusBITS(),
                getName(), getDescription(), getCreatedDate(), getModifiedDate(),
                getSyncedDate());
    }
}
