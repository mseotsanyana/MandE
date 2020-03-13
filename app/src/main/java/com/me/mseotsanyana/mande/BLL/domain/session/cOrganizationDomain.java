package com.me.mseotsanyana.mande.BLL.domain.session;

import android.os.Parcel;
import android.os.Parcelable;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;
import java.util.Set;

public class cOrganizationDomain implements Parcelable {
    private int organizationID;
    private int serverID;
    private int ownerID;
    private int orgID;
    private int groupBITS;
    private int permsBITS;
    private int statusBITS;
    private String name;
    private String phone;
    private String fax;
    private String vision;
    private String mission;
    private String email;
    private String website;
    private Date createdDate;
    private Date modifiedDate;
    private Date syncedDate;

    Set<cUserDomain> userDomainSet;
    Set<cRoleDomain> roleDomainSet;
    Set<cValueDomain> valueDomainSet;
    Set<cAddressDomain> addressDomainSet;

    public cOrganizationDomain() {
        super();
    }

    public cOrganizationDomain(String orginization) {
        this.setName(orginization);
    }

    /**
     * For reconstructing the object reading from the Parcel data
     * @param in
     */

    private cOrganizationDomain(Parcel in) {
        super();
        this.setOrganizationID(in.readInt());
        this.setServerID(in.readInt());
        this.setOwnerID(in.readInt());
        this.setOrgID(in.readInt());
        this.setGroupBITS(in.readInt());
        this.setPermsBITS(in.readInt());
        this.setStatusBITS(in.readInt());
        this.setName(in.readString());
        this.setPhone(in.readString());
        this.setFax(in.readString());
        this.setVision(in.readString());
        this.setMission(in.readString());
        this.setEmail(in.readString());
        this.setWebsite(in.readString());
        this.setCreatedDate(new Timestamp(in.readLong()));
        this.setModifiedDate(new Timestamp(in.readLong()));
        this.setSyncedDate(new Timestamp(in.readLong()));
    }

    public static final Creator<cOrganizationDomain> CREATOR = new Creator<cOrganizationDomain>() {
        @Override
        public cOrganizationDomain createFromParcel(Parcel in) {
            return new cOrganizationDomain(in);
        }

        @Override
        public cOrganizationDomain[] newArray(int size) {
            return new cOrganizationDomain[size];
        }
    };

    @Override
    public int describeContents() {
        return hashCode();
    }

    @Override
    public void writeToParcel(Parcel out, int i) {
        out.writeInt(this.getOrganizationID());
        out.writeInt(this.getServerID());
        out.writeInt(this.getOwnerID());
        out.writeInt(this.getOrgID());
        out.writeInt(this.getGroupBITS());
        out.writeInt(this.getPermsBITS());
        out.writeInt(this.getStatusBITS());
        out.writeString(this.getName());
        out.writeString(this.getPhone());
        out.writeString(this.getFax());
        out.writeString(this.getVision());
        out.writeString(this.getMission());
        out.writeString(this.getEmail());
        out.writeString(this.getWebsite());
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getVision() {
        return vision;
    }

    public void setVision(String vision) {
        this.vision = vision;
    }

    public String getMission() {
        return mission;
    }

    public void setMission(String mission) {
        this.mission = mission;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
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

    public Set<cUserDomain> getUserDomainSet() {
        return userDomainSet;
    }

    public void setUserDomainSet(Set<cUserDomain> userDomainSet) {
        this.userDomainSet = userDomainSet;
    }

    public Set<cRoleDomain> getRoleDomainSet() {
        return roleDomainSet;
    }

    public void setRoleDomainSet(Set<cRoleDomain> roleDomainSet) {
        this.roleDomainSet = roleDomainSet;
    }

    public Set<cValueDomain> getValueDomainSet() {
        return valueDomainSet;
    }

    public void setValueDomainSet(Set<cValueDomain> valueDomainSet) {
        this.valueDomainSet = valueDomainSet;
    }

    public Set<cAddressDomain> getAddressDomainSet() {
        return addressDomainSet;
    }

    public void setAddressDomainSet(Set<cAddressDomain> addressDomainSet) {
        this.addressDomainSet = addressDomainSet;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof cOrganizationDomain)) return false;
        cOrganizationDomain that = (cOrganizationDomain) o;
        return getOrganizationID() == that.getOrganizationID() &&
                getServerID() == that.getServerID() &&
                getOwnerID() == that.getOwnerID() &&
                getOrgID() == that.getOrgID() &&
                getGroupBITS() == that.getGroupBITS() &&
                getPermsBITS() == that.getPermsBITS() &&
                getStatusBITS() == that.getStatusBITS() &&
                Objects.equals(getName(), that.getName()) &&
                Objects.equals(getPhone(), that.getPhone()) &&
                Objects.equals(getFax(), that.getFax()) &&
                Objects.equals(getVision(), that.getVision()) &&
                Objects.equals(getMission(), that.getMission()) &&
                Objects.equals(getEmail(), that.getEmail()) &&
                Objects.equals(getWebsite(), that.getWebsite()) &&
                Objects.equals(getCreatedDate(), that.getCreatedDate()) &&
                Objects.equals(getModifiedDate(), that.getModifiedDate()) &&
                Objects.equals(getSyncedDate(), that.getSyncedDate()) &&
                Objects.equals(getUserDomainSet(), that.getUserDomainSet()) &&
                Objects.equals(getRoleDomainSet(), that.getRoleDomainSet()) &&
                Objects.equals(getValueDomainSet(), that.getValueDomainSet()) &&
                Objects.equals(getAddressDomainSet(), that.getAddressDomainSet());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getOrganizationID(), getServerID(), getOwnerID(), getOrgID(),
                getGroupBITS(), getPermsBITS(), getStatusBITS(), getName(), getPhone(),
                getFax(), getVision(), getMission(), getEmail(), getWebsite(),
                getCreatedDate(), getModifiedDate(), getSyncedDate(),
                getUserDomainSet(), getRoleDomainSet(), getValueDomainSet(),
                getAddressDomainSet());
    }
}
