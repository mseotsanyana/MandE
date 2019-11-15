package com.me.mseotsanyana.mande.BRBAC.DAL;

import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Created by mseotsanyana on 2017/08/24.
 */

public class cRoleModel {
    private int roleID;
    private int organizationID;
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

    private cOrganizationModel organizationModel;
    private cPrivilegeModel privilegeModel;

    private Set<cUserModel> userModelSet;
    private Set<cSessionModel> sessionModelSet;
    private Set<cMenuModel> menuModelSet;

    public cRoleModel(){
        menuModelSet = new HashSet<>();
        menuModelSet = new HashSet<>();
        sessionModelSet = new HashSet<>();
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

    public cOrganizationModel getOrganizationModel() {
        return organizationModel;
    }

    public void setOrganizationModel(cOrganizationModel organizationModel) {
        this.organizationModel = organizationModel;
    }

    public cPrivilegeModel getPrivilegeModel() {
        return privilegeModel;
    }

    public void setPrivilegeModel(cPrivilegeModel privilegeModel) {
        this.privilegeModel = privilegeModel;
    }

    public Set<cUserModel> getUserModelSet() {
        return userModelSet;
    }

    public void setUserModelSet(Set<cUserModel> userModelSet) {
        this.userModelSet = userModelSet;
    }

    public Set<cSessionModel> getSessionModelSet() {
        return sessionModelSet;
    }

    public void setSessionModelSet(Set<cSessionModel> sessionModelSet) {
        this.sessionModelSet = sessionModelSet;
    }

    public Set<cMenuModel> getMenuModelSet() {
        return menuModelSet;
    }

    public void setMenuModelSet(Set<cMenuModel> menuModelSet) {
        this.menuModelSet = menuModelSet;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof cRoleModel)) return false;
        cRoleModel roleModel = (cRoleModel) o;
        return getRoleID() == roleModel.getRoleID() &&
                getOrganizationID() == roleModel.getOrganizationID() &&
                getServerID() == roleModel.getServerID() &&
                getOwnerID() == roleModel.getOwnerID() &&
                getOrgID() == roleModel.getOrgID() &&
                getGroupBITS() == roleModel.getGroupBITS() &&
                getPermsBITS() == roleModel.getPermsBITS() &&
                getStatusBITS() == roleModel.getStatusBITS() &&
                Objects.equals(getName(), roleModel.getName()) &&
                Objects.equals(getDescription(), roleModel.getDescription()) &&
                Objects.equals(getCreatedDate(), roleModel.getCreatedDate()) &&
                Objects.equals(getModifiedDate(), roleModel.getModifiedDate()) &&
                Objects.equals(getSyncedDate(), roleModel.getSyncedDate()) &&
                Objects.equals(getOrganizationModel(), roleModel.getOrganizationModel()) &&
                Objects.equals(getPrivilegeModel(), roleModel.getPrivilegeModel()) &&
                Objects.equals(getUserModelSet(), roleModel.getUserModelSet()) &&
                Objects.equals(getSessionModelSet(), roleModel.getSessionModelSet()) &&
                Objects.equals(getMenuModelSet(), roleModel.getMenuModelSet());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRoleID(), getOrganizationID(), getServerID(),
                getOwnerID(), getOrgID(), getGroupBITS(), getPermsBITS(),
                getStatusBITS(), getName(), getDescription(), getCreatedDate(),
                getModifiedDate(), getSyncedDate(), getOrganizationModel(),
                getPrivilegeModel(), getUserModelSet(), getSessionModelSet(),
                getMenuModelSet());
    }
}
