package com.me.mseotsanyana.mande.BLL.domain.session;

import android.os.Parcel;
import android.os.Parcelable;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;
import java.util.Set;

/**
 * Created by mseotsanyana on 2017/08/24.
 */

public class cUserDomain implements Parcelable {
    private int userID;
    private int organizationID;
    private int serverID;
    private int ownerID;
    private int orgID;
    private String uniqueID;
    private int groupBITS;
    private int permsBITS;
    private int statusBITS;
    private String photo;
    private String name;
    private String surname;
    private String gender;
    private String description;
    private String email;
    private String website;
    private String phone;
    private String password;
    private String salt;
    private String oldPassword;
    private String newPassword;
    private Date createdDate;
    private Date modifiedDate;
    private Date syncedDate;


    cOrganizationDomain organizationDomain;

    Set<cAddressDomain> addressDomainSet;
    Set<cSessionDomain> sessionDomainSet;
    Set<cRoleDomain> roleDomainSet;
    Set<cNotificationDomain> publisherDomainSet;
    Set<cNotificationDomain> subscriberDomainSet;

    protected cUserDomain(Parcel in) {
        this.setUserID(in.readInt());
        this.setOrganizationID(in.readInt());
        this.setServerID(in.readInt());
        this.setOwnerID(in.readInt());
        this.setOrgID(in.readInt());
        this.setUniqueID(in.readString());
        this.setGroupBITS(in.readInt());
        this.setPermsBITS(in.readInt());
        this.setStatusBITS(in.readInt());
        this.setPhoto(in.readString());
        this.setName(in.readString());
        this.setSurname(in.readString());
        this.setGender(in.readString());
        this.setDescription(in.readString());
        this.setEmail(in.readString());
        this.setWebsite(in.readString());
        this.setPhone(in.readString());
        this.setPassword(in.readString());
        this.setSalt(in.readString());
        this.setCreatedDate(new Timestamp(in.readLong()));
        this.setModifiedDate(new Timestamp(in.readLong()));
        this.setSyncedDate(new Timestamp(in.readLong()));
    }

    public cUserDomain(){}

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(this.getUserID());
        out.writeInt(this.getOrganizationID());
        out.writeInt(this.getServerID());
        out.writeInt(this.getOwnerID());
        out.writeInt(this.getOrgID());
        out.writeString(this.getUniqueID());
        out.writeInt(this.getGroupBITS());
        out.writeInt(this.getPermsBITS());
        out.writeInt(this.getStatusBITS());
        out.writeString(this.getPhoto());
        out.writeString(this.getName());
        out.writeString(this.getSurname());
        out.writeString(this.getGender());
        out.writeString(this.getDescription());
        out.writeString(this.getEmail());
        out.writeString(this.getWebsite());
        out.writeString(this.getPhone());
        out.writeString(this.getPassword());
        out.writeString(this.getSalt());
        out.writeLong(this.getCreatedDate().getTime());
        out.writeLong(this.getModifiedDate().getTime());
        out.writeLong(this.getSyncedDate().getTime());
    }


    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
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

    public String getUniqueID() {
        return uniqueID;
    }

    public void setUniqueID(String uniqueID) {
        this.uniqueID = uniqueID;
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

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
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

    public Set<cAddressDomain> getAddressDomainSet() {
        return addressDomainSet;
    }

    public void setAddressDomainSet(Set<cAddressDomain> addressDomainSet) {
        this.addressDomainSet = addressDomainSet;
    }

    public Set<cSessionDomain> getSessionDomainSet() {
        return sessionDomainSet;
    }

    public void setSessionDomainSet(Set<cSessionDomain> sessionDomainSet) {
        this.sessionDomainSet = sessionDomainSet;
    }

    public Set<cRoleDomain> getRoleDomainSet() {
        return roleDomainSet;
    }

    public void setRoleDomainSet(Set<cRoleDomain> roleDomainSet) {
        this.roleDomainSet = roleDomainSet;
    }

    public Set<cNotificationDomain> getPublisherDomainSet() {
        return publisherDomainSet;
    }

    public void setPublisherDomainSet(Set<cNotificationDomain> publisherDomainSet) {
        this.publisherDomainSet = publisherDomainSet;
    }

    public Set<cNotificationDomain> getSubscriberDomainSet() {
        return subscriberDomainSet;
    }

    public void setSubscriberDomainSet(Set<cNotificationDomain> subscriberDomainSet) {
        this.subscriberDomainSet = subscriberDomainSet;
    }
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<cUserDomain> CREATOR = new Creator<cUserDomain>() {
        @Override
        public cUserDomain createFromParcel(Parcel in) {
            return new cUserDomain(in);
        }

        @Override
        public cUserDomain[] newArray(int size) {
            return new cUserDomain[size];
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof cUserDomain)) return false;
        cUserDomain that = (cUserDomain) o;
        return getUserID() == that.getUserID() &&
                getOrganizationID() == that.getOrganizationID() &&
                getServerID() == that.getServerID() &&
                getOwnerID() == that.getOwnerID() &&
                getOrgID() == that.getOrgID() &&
                getGroupBITS() == that.getGroupBITS() &&
                getPermsBITS() == that.getPermsBITS() &&
                getStatusBITS() == that.getStatusBITS() &&
                Objects.equals(getUniqueID(), that.getUniqueID()) &&
                Objects.equals(getPhoto(), that.getPhoto()) &&
                Objects.equals(getName(), that.getName()) &&
                Objects.equals(getSurname(), that.getSurname()) &&
                Objects.equals(getGender(), that.getGender()) &&
                Objects.equals(getDescription(), that.getDescription()) &&
                Objects.equals(getEmail(), that.getEmail()) &&
                Objects.equals(getWebsite(), that.getWebsite()) &&
                Objects.equals(getPhone(), that.getPhone()) &&
                Objects.equals(getPassword(), that.getPassword()) &&
                Objects.equals(getSalt(), that.getSalt()) &&
                Objects.equals(getOldPassword(), that.getOldPassword()) &&
                Objects.equals(getNewPassword(), that.getNewPassword()) &&
                Objects.equals(getCreatedDate(), that.getCreatedDate()) &&
                Objects.equals(getModifiedDate(), that.getModifiedDate()) &&
                Objects.equals(getSyncedDate(), that.getSyncedDate()) &&
                Objects.equals(getOrganizationDomain(), that.getOrganizationDomain()) &&
                Objects.equals(getAddressDomainSet(), that.getAddressDomainSet()) &&
                Objects.equals(getSessionDomainSet(), that.getSessionDomainSet()) &&
                Objects.equals(getRoleDomainSet(), that.getRoleDomainSet()) &&
                Objects.equals(getPublisherDomainSet(), that.getPublisherDomainSet()) &&
                Objects.equals(getSubscriberDomainSet(), that.getSubscriberDomainSet());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUserID(), getOrganizationID(), getServerID(), getOwnerID(),
                getOrgID(), getUniqueID(), getGroupBITS(), getPermsBITS(), getStatusBITS(),
                getPhoto(), getName(), getSurname(), getGender(), getDescription(), getEmail(),
                getWebsite(), getPhone(), getPassword(), getSalt(), getOldPassword(),
                getNewPassword(), getCreatedDate(), getModifiedDate(), getSyncedDate(),
                getOrganizationDomain(), getAddressDomainSet(), getSessionDomainSet(),
                getRoleDomainSet(), getPublisherDomainSet(), getSubscriberDomainSet());
    }

    /*
    public String toString() {
        return "cUserDomain{" +
                "ID='"       + getUserID() + '\'' +
                ", NAME='"   + getName() + '\'' +
                ", SURNAME='"  + getSurname() + '\'' +
                ", DESCRIPTION='" + getDescription() + '\'' +
                ", EMAIL='"      + getEmail() + '\'' +
                '}';
    }
    */
}
