package com.me.mseotsanyana.mande.BRBAC.DAL;

import com.me.mseotsanyana.mande.BRBAC.DAL.cValueModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

public class cOrganizationModel implements Serializable {
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

    Set<cUserModel> userModelSet;
    Set<cRoleModel> roleModelSet;
    Set<cValueModel> valueModelSet;
    Set<cAddressModel> addressModelSet;

    cOrganizationModel(){}

    cOrganizationModel(cOrganizationModel organizationModel){
        this.setOrganizationID(organizationModel.getOrganizationID());
        this.setServerID(organizationModel.getServerID());
        this.setOwnerID(organizationModel.getOwnerID());
        this.setOrgID(organizationModel.getOwnerID());
        this.setGroupBITS(organizationModel.getGroupBITS());
        this.setPermsBITS(organizationModel.getPermsBITS());
        this.setStatusBITS(organizationModel.getStatusBITS());
        this.setName(organizationModel.getName());
        this.setPhone(organizationModel.getPhone());
        this.setFax(organizationModel.getFax());
        this.setVision(organizationModel.getVision());
        this.setMission(organizationModel.getMission());
        this.setEmail(organizationModel.getEmail());
        this.setWebsite(organizationModel.getWebsite());
        this.setCreatedDate(organizationModel.getCreatedDate());
        this.setModifiedDate(organizationModel.getModifiedDate());
        this.setSyncedDate(organizationModel.getSyncedDate());
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

    public Set<cUserModel> getUserModelSet() {
        return userModelSet;
    }

    public void setUserModelSet(Set<cUserModel> userModelSet) {
        this.userModelSet = userModelSet;
    }

    public Set<cRoleModel> getRoleModelSet() {
        return roleModelSet;
    }

    public void setRoleModelSet(Set<cRoleModel> roleModelSet) {
        this.roleModelSet = roleModelSet;
    }

    public Set<cValueModel> getValueModelSet() {
        return valueModelSet;
    }

    public void setValueModelSet(Set<cValueModel> valueModelSet) {
        this.valueModelSet = valueModelSet;
    }

    public Set<cAddressModel> getAddressModelSet() {
        return addressModelSet;
    }

    public void setAddressModelSet(Set<cAddressModel> addressModelSet) {
        this.addressModelSet = addressModelSet;
    }
}
