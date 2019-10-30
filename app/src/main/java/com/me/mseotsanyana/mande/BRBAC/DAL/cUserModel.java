package com.me.mseotsanyana.mande.BRBAC.DAL;


import java.util.Date;
import java.util.Set;

public class cUserModel {
    private int userID;
    private int organizationID;
    private int serverID;
    private int ownerID;
    private int orgID;
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
    private String uniqueID;
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

    public cUserModel(){};

    public cUserModel(cUserModel userModel){
        this.setUserID(userModel.getUserID());
        this.setOrganizationID(userModel.getOrganizationID());
        this.setServerID(userModel.getServerID());
        this.setOwnerID(userModel.getOwnerID());
        this.setOrgID(userModel.getOrgID());
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
        this.setUniqueID(userModel.getUniqueID());
        this.setCreatedDate(userModel.getCreatedDate());
        this.setModifiedDate(userModel.getModifiedDate());
        this.setSyncedDate(userModel.getSyncedDate());
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

    public String getUniqueID() {
        return uniqueID;
    }

    public void setUniqueID(String uniqueID) {
        this.uniqueID = uniqueID;
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
}
