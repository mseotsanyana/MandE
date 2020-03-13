package com.me.mseotsanyana.mande.DAL.model.session;

import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Created by mseotsanyana on 2017/08/24.
 */

public class cMenuModel {
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

    private Set<cMenuModel> menuModelSet;

    public cMenuModel(){
        menuModelSet = new HashSet<>();
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

    public Set<cMenuModel> getMenuModelSet() {
        return menuModelSet;
    }

    public void setMenuModelSet(Set<cMenuModel> menuModelSet) {
        this.menuModelSet = menuModelSet;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof cMenuModel)) return false;
        cMenuModel menuModel = (cMenuModel) o;
        return getMenuID() == menuModel.getMenuID() &&
                getParentID() == menuModel.getParentID() &&
                getServerID() == menuModel.getServerID() &&
                getOwnerID() == menuModel.getOwnerID() &&
                getOrgID() == menuModel.getOrgID() &&
                getGroupBITS() == menuModel.getGroupBITS() &&
                getPermsBITS() == menuModel.getPermsBITS() &&
                getStatusBITS() == menuModel.getStatusBITS() &&
                Objects.equals(getName(), menuModel.getName()) &&
                Objects.equals(getDescription(), menuModel.getDescription()) &&
                Objects.equals(getCreatedDate(), menuModel.getCreatedDate()) &&
                Objects.equals(getModifiedDate(), menuModel.getModifiedDate()) &&
                Objects.equals(getSyncedDate(), menuModel.getSyncedDate()) &&
                Objects.equals(getMenuModelSet(), menuModel.getMenuModelSet());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMenuID(), getParentID(), getServerID(), getOwnerID(),
                getOrgID(), getGroupBITS(), getPermsBITS(), getStatusBITS(), getName(),
                getDescription(), getCreatedDate(), getModifiedDate(), getSyncedDate(),
                getMenuModelSet());
    }
}
