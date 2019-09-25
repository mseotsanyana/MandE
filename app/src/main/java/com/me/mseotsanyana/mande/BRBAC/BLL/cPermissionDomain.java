package com.me.mseotsanyana.mande.BRBAC.BLL;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Created by mseotsanyana on 2017/08/29.
 */

public class cPermissionDomain implements Parcelable {
    private int organizationID;
    private cPrivilegeDomain privilegeDomain;
    private cEntityDomain entityDomain;
    private cOperationDomain operationDomain;
    private cStatusDomain statusDomain;

    private int ownerID;
    private int orgID;
    private int groupBITS;
    private int permsBITS;
    private int statusBITS;
    private Date createdDate;
    private Date modifiedDate;
    private Date syncedDate;

    public cPermissionDomain(){
        this.setPrivilegeDomain(new cPrivilegeDomain());
        this.setEntityDomain(new cEntityDomain());
        this.setOperationDomain(new cOperationDomain());
        this.setStatusDomain(new cStatusDomain());
    }

    public cPermissionDomain(cPermissionDomain permissionDomain){
        this.setOrganizationID(permissionDomain.getOrganizationID());
        this.setPrivilegeDomain(new cPrivilegeDomain(permissionDomain.getPrivilegeDomain()));
        this.setEntityDomain(new cEntityDomain(permissionDomain.getEntityDomain()));
        this.setOperationDomain(new cOperationDomain(permissionDomain.getOperationDomain()));
        this.setStatusDomain(new cStatusDomain(permissionDomain.getStatusDomain()));

        this.setOwnerID(permissionDomain.getOwnerID());
        this.setOrgID(permissionDomain.getOrgID());
        this.setGroupBITS(permissionDomain.getGroupBITS());
        this.setPermsBITS(permissionDomain.getPermsBITS());
        this.setStatusBITS(permissionDomain.getStatusBITS());
        this.setCreatedDate(permissionDomain.getCreatedDate());
        this.setModifiedDate(permissionDomain.getModifiedDate());
        this.setSyncedDate(permissionDomain.getSyncedDate());
    }

    public cPermissionDomain(cPrivilegeDomain privilegeDomain,
                             cEntityDomain entityDomain,
                             cOperationDomain operationDomain,
                             cStatusDomain statusDomain){
        this.setPrivilegeDomain(new cPrivilegeDomain());
        this.setEntityDomain(new cEntityDomain());
        this.setOperationDomain(new cOperationDomain());
        this.setStatusDomain(new cStatusDomain());
    }


    protected cPermissionDomain(Parcel in) {
        this.setOrganizationID(in.readInt());
        setPrivilegeDomain((cPrivilegeDomain) in.readParcelable(cPrivilegeDomain.class.getClassLoader()));
        setEntityDomain((cEntityDomain) in.readParcelable(cEntityDomain.class.getClassLoader()));
        setOperationDomain((cOperationDomain) in.readParcelable(cOperationDomain.class.getClassLoader()));
        setStatusDomain((cStatusDomain) in.readParcelable(cStatusDomain.class.getClassLoader()));

        this.setOwnerID(in.readInt());
        this.setOrgID(in.readInt());
        this.setGroupBITS(in.readInt());
        this.setPermsBITS(in.readInt());
        this.setStatusBITS(in.readInt());
        this.setCreatedDate(new Date(in.readLong()));
        this.setModifiedDate(new Date(in.readLong()));
        this.setSyncedDate(new Date(in.readLong()));
    }

    public static final Creator<cPermissionDomain> CREATOR = new Creator<cPermissionDomain>() {
        @Override
        public cPermissionDomain createFromParcel(Parcel in) {
            return new cPermissionDomain(in);
        }

        @Override
        public cPermissionDomain[] newArray(int size) {
            return new cPermissionDomain[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int i) {
        out.writeInt(this.getOrganizationID());
        out.writeParcelable(this.getPrivilegeDomain(), i);
        out.writeParcelable(this.getEntityDomain(), i);
        out.writeParcelable(this.getOperationDomain(), i);
        out.writeParcelable(this.getStatusDomain(), i);
        out.writeInt(this.getOwnerID());
        out.writeInt(this.getOrgID());
        out.writeInt(this.getGroupBITS());
        out.writeInt(this.getPermsBITS());
        out.writeInt(this.getStatusBITS());
        out.writeLong(this.getCreatedDate().getTime());
        out.writeLong(this.getModifiedDate().getTime());
        out.writeLong(this.getSyncedDate().getTime());
    }

    public int getOrganizationID() {
        return organizationID;
    }

    public void setOrganizationID(int organizationID) {
        this.organizationID = organizationID;
    }

    public cPrivilegeDomain getPrivilegeDomain() {
        return privilegeDomain;
    }

    public void setPrivilegeDomain(cPrivilegeDomain privilegeDomain) {
        this.privilegeDomain = privilegeDomain;
    }

    public cEntityDomain getEntityDomain() {
        return entityDomain;
    }

    public void setEntityDomain(cEntityDomain entityDomain) {
        this.entityDomain = entityDomain;
    }

    public cOperationDomain getOperationDomain() {
        return operationDomain;
    }

    public void setOperationDomain(cOperationDomain operationDomain) {
        this.operationDomain = operationDomain;
    }

    public cStatusDomain getStatusDomain() {
        return statusDomain;
    }

    public void setStatusDomain(cStatusDomain statusDomain) {
        this.statusDomain = statusDomain;
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

    /** two permissions are equal if operation and status IDs are equal **/
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        cPermissionDomain perm = (cPermissionDomain) o;
        return ((getOperationDomain().getOperationID() == perm.getOperationDomain().getOperationID()) &&
                getStatusDomain().getStatusID() == perm.getStatusDomain().getStatusID());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getOperationDomain().getOperationID()+getStatusDomain().getStatusID());
    }

    @Override
    public String toString() {
        return "cPermissionDomain{" +
                "operationID=" + getOperationDomain().getOperationID() +
                "statusID=" + getStatusDomain().getStatusID() +
                ", operation='" + getOperationDomain().getName() + '\'' +
                ", status='" + getStatusDomain().getName() + '\'' +
                '}';
    }

}
