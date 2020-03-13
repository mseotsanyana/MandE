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

public class cRoleDomain implements Parcelable{
    private int roleID;
    private int organizationID;
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

    private cOrganizationDomain organizationDomain;
    private cPrivilegeDomain privilegeDomain;

    private Set<cUserDomain> userDomainSet;
    private Set<cSessionDomain> sessionDomainSet;
    private Set<cMenuDomain> menuDomainSet;

    public cRoleDomain(){
        userDomainSet    = new HashSet<>();
        sessionDomainSet = new HashSet<>();
        menuDomainSet    = new HashSet<>();
    }

    protected cRoleDomain(Parcel in) {
        this.setRoleID(in.readInt());
        this.setOrganizationID(in.readInt());
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
        out.writeInt(this.getRoleID());
        out.writeInt(this.getOrganizationID());
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

    public int getRoleID() {
        return roleID;
    }

    public void setRoleID(int roleID) {
        this.roleID = roleID;
    }

    public int getOrganizationID() {
        return organizationID;
    }

    public void setOrganizationID(int organizationID) {
        this.organizationID = organizationID;
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

    public cOrganizationDomain getOrganizationDomain() {
        return organizationDomain;
    }

    public void setOrganizationDomain(cOrganizationDomain organizationDomain) {
        this.organizationDomain = organizationDomain;
    }

    public Set<cUserDomain> getUserDomainSet() {
        return userDomainSet;
    }

    public void setUserDomainSet(Set<cUserDomain> userDomainSet) {
        this.userDomainSet = userDomainSet;
    }

    public Set<cSessionDomain> getSessionDomainSet() {
        return sessionDomainSet;
    }

    public void setSessionDomainSet(Set<cSessionDomain> sessionDomainSet) {
        this.sessionDomainSet = sessionDomainSet;
    }

    public Set<cMenuDomain> getMenuDomainSet() {
        return menuDomainSet;
    }

    public void setMenuDomainSet(Set<cMenuDomain> menuDomainSet) {
        this.menuDomainSet = menuDomainSet;
    }

    public cPrivilegeDomain getPrivilegeDomain() {
        return privilegeDomain;
    }

    public void setPrivilegeDomain(cPrivilegeDomain privilegeDomain) {
        this.privilegeDomain = privilegeDomain;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<cRoleDomain> CREATOR = new Creator<cRoleDomain>() {
        @Override
        public cRoleDomain createFromParcel(Parcel in) {
            return new cRoleDomain(in);
        }

        @Override
        public cRoleDomain[] newArray(int size) {
            return new cRoleDomain[size];
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof cRoleDomain)) return false;
        cRoleDomain that = (cRoleDomain) o;
        return getRoleID() == that.getRoleID() &&
                getOrganizationID() == that.getOrganizationID() &&
                getPrivilegeID() == that.getPrivilegeID() &&
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
                Objects.equals(getOrganizationDomain(), that.getOrganizationDomain()) &&
                Objects.equals(getPrivilegeDomain(), that.getPrivilegeDomain()) &&
                Objects.equals(getUserDomainSet(), that.getUserDomainSet()) &&
                Objects.equals(getSessionDomainSet(), that.getSessionDomainSet()) &&
                Objects.equals(getMenuDomainSet(), that.getMenuDomainSet());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRoleID(), getOrganizationID(), getPrivilegeID(),
                getServerID(), getOwnerID(), getOrgID(), getGroupBITS(), getPermsBITS(),
                getStatusBITS(), getName(), getDescription(), getCreatedDate(),
                getModifiedDate(), getSyncedDate(), getOrganizationDomain(),
                getPrivilegeDomain(), getUserDomainSet(), getSessionDomainSet(),
                getMenuDomainSet());
    }
}
