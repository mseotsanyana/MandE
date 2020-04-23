package com.me.mseotsanyana.mande.DAL.model.session;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class cUserModel implements Parcelable {
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

    cOrganizationModel organizationModel;

    Set<cAddressModel> addressModelSet;
    Set<cSessionModel> sessionModelSet;
    Set<cRoleModel> roleModelSet;
    Set<cNotificationModel> publisherModelSet;
    Set<cNotificationModel> subscriberModelSet;

    public cUserModel(){
        addressModelSet = new HashSet<>();
        sessionModelSet = new HashSet<>();
        roleModelSet = new HashSet<>();
        publisherModelSet = new HashSet<>();
        subscriberModelSet = new HashSet<>();
    };

    public cUserModel(cUserModel userModel){
        this.setUserID(userModel.getUserID());
        this.setOrganizationID(userModel.getOrganizationID());
        this.setServerID(userModel.getServerID());
        this.setOwnerID(userModel.getOwnerID());
        this.setOrgID(userModel.getOrgID());
        this.setUniqueID(userModel.getUniqueID());
        this.setGroupBITS(userModel.getGroupBITS());
        this.setPermsBITS(userModel.getPermsBITS());
        this.setStatusBITS(userModel.getStatusBITS());
        this.setName(userModel.getName());
        this.setSurname(userModel.getSurname());
        this.setGender(userModel.getGender());
        this.setDescription(userModel.getDescription());
        this.setEmail(userModel.getEmail());
        this.setWebsite(userModel.getWebsite());
        this.setPhone(userModel.getPhone());
        this.setNewPassword(userModel.getNewPassword());
        this.setOldPassword(userModel.getOldPassword());
        this.setCreatedDate(userModel.getCreatedDate());
        this.setModifiedDate(userModel.getModifiedDate());
        this.setSyncedDate(userModel.getSyncedDate());
    }

    protected cUserModel(Parcel in) {
        userID = in.readInt();
        organizationID = in.readInt();
        serverID = in.readInt();
        ownerID = in.readInt();
        orgID = in.readInt();
        uniqueID = in.readString();
        groupBITS = in.readInt();
        permsBITS = in.readInt();
        statusBITS = in.readInt();
        photo = in.readString();
        name = in.readString();
        surname = in.readString();
        gender = in.readString();
        description = in.readString();
        email = in.readString();
        website = in.readString();
        phone = in.readString();
        password = in.readString();
        salt = in.readString();
        oldPassword = in.readString();
        newPassword = in.readString();
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

    public cOrganizationModel getOrganizationModel() {
        return organizationModel;
    }

    public void setOrganizationModel(cOrganizationModel organizationModel) {
        this.organizationModel = organizationModel;
    }

    public Set<cAddressModel> getAddressModelSet() {
        return addressModelSet;
    }

    public void setAddressModelSet(Set<cAddressModel> addressModelSet) {
        this.addressModelSet = addressModelSet;
    }

    public Set<cSessionModel> getSessionModelSet() {
        return sessionModelSet;
    }

    public void setSessionModelSet(Set<cSessionModel> sessionModelSet) {
        this.sessionModelSet = sessionModelSet;
    }

    public Set<cRoleModel> getRoleModelSet() {
        return roleModelSet;
    }

    public void setRoleModelSet(Set<cRoleModel> roleModelSet) {
        this.roleModelSet = roleModelSet;
    }

    public Set<cNotificationModel> getPublisherModelSet() {
        return publisherModelSet;
    }

    public void setPublisherModelSet(Set<cNotificationModel> publisherModelSet) {
        this.publisherModelSet = publisherModelSet;
    }

    public Set<cNotificationModel> getSubscriberModelSet() {
        return subscriberModelSet;
    }

    public void setSubscriberModelSet(Set<cNotificationModel> subscriberModelSet) {
        this.subscriberModelSet = subscriberModelSet;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof cUserModel)) return false;
        cUserModel userModel = (cUserModel) o;
        return getUserID() == userModel.getUserID() &&
                getOrganizationID() == userModel.getOrganizationID() &&
                getServerID() == userModel.getServerID() &&
                getOwnerID() == userModel.getOwnerID() &&
                getOrgID() == userModel.getOrgID() &&
                getGroupBITS() == userModel.getGroupBITS() &&
                getPermsBITS() == userModel.getPermsBITS() &&
                getStatusBITS() == userModel.getStatusBITS() &&
                Objects.equals(getUniqueID(), userModel.getUniqueID()) &&
                Objects.equals(getPhoto(), userModel.getPhoto()) &&
                Objects.equals(getName(), userModel.getName()) &&
                Objects.equals(getSurname(), userModel.getSurname()) &&
                Objects.equals(getGender(), userModel.getGender()) &&
                Objects.equals(getDescription(), userModel.getDescription()) &&
                Objects.equals(getEmail(), userModel.getEmail()) &&
                Objects.equals(getWebsite(), userModel.getWebsite()) &&
                Objects.equals(getPhone(), userModel.getPhone()) &&
                Objects.equals(getPassword(), userModel.getPassword()) &&
                Objects.equals(getSalt(), userModel.getSalt()) &&
                Objects.equals(getOldPassword(), userModel.getOldPassword()) &&
                Objects.equals(getNewPassword(), userModel.getNewPassword()) &&
                Objects.equals(getCreatedDate(), userModel.getCreatedDate()) &&
                Objects.equals(getModifiedDate(), userModel.getModifiedDate()) &&
                Objects.equals(getSyncedDate(), userModel.getSyncedDate()) &&
                Objects.equals(getOrganizationModel(), userModel.getOrganizationModel()) &&
                Objects.equals(getAddressModelSet(), userModel.getAddressModelSet()) &&
                Objects.equals(getSessionModelSet(), userModel.getSessionModelSet()) &&
                Objects.equals(getRoleModelSet(), userModel.getRoleModelSet()) &&
                Objects.equals(getPublisherModelSet(), userModel.getPublisherModelSet()) &&
                Objects.equals(getSubscriberModelSet(), userModel.getSubscriberModelSet());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUserID(), getOrganizationID(), getServerID(), getOwnerID(),
                getOrgID(), getUniqueID(), getGroupBITS(), getPermsBITS(), getStatusBITS(),
                getPhoto(), getName(), getSurname(), getGender(), getDescription(), getEmail(),
                getWebsite(), getPhone(), getPassword(), getSalt(), getOldPassword(),
                getNewPassword(), getCreatedDate(), getModifiedDate(), getSyncedDate(),
                getOrganizationModel(), getAddressModelSet(), getSessionModelSet(),
                getRoleModelSet(), getPublisherModelSet(), getSubscriberModelSet());
    }

    public static final Creator<cUserModel> CREATOR = new Creator<cUserModel>() {
        @Override
        public cUserModel createFromParcel(Parcel in) {
            return new cUserModel(in);
        }

        @Override
        public cUserModel[] newArray(int size) {
            return new cUserModel[size];
        }
    };


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(userID);
        dest.writeInt(organizationID);
        dest.writeInt(serverID);
        dest.writeInt(ownerID);
        dest.writeInt(orgID);
        dest.writeString(uniqueID);
        dest.writeInt(groupBITS);
        dest.writeInt(permsBITS);
        dest.writeInt(statusBITS);
        dest.writeString(photo);
        dest.writeString(name);
        dest.writeString(surname);
        dest.writeString(gender);
        dest.writeString(description);
        dest.writeString(email);
        dest.writeString(website);
        dest.writeString(phone);
        dest.writeString(password);
        dest.writeString(salt);
        dest.writeString(oldPassword);
        dest.writeString(newPassword);
    }
}
