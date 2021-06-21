package com.me.mseotsanyana.mande.BLL.model.session;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Created by mseotsanyana on 2017/08/24.
 */

public class cMenuModel implements Comparable<cMenuModel>{
    private int menuServerID;
    private int parentServerID;

    private String ownerID;
    private String orgOwnerID;
    private int teamOwnerBIT;
    private int unixpermsBITS;
    private int statusesBITS;

    private String name;
    private String description;
    private Date createdDate;
    private Date modifiedDate;
    private Date syncedDate;

    public boolean hasChildren, isGroup;

    private List<cMenuModel> subMenuModels;

    public cMenuModel(){
        //menuModelSet = new HashSet<>();
    }

    public cMenuModel(String name, boolean isGroup, boolean hasChildren) {
        this.name = name;
        this.isGroup = isGroup;
        this.hasChildren = hasChildren;
    }

    public int getMenuServerID() {
        return menuServerID;
    }

    public void setMenuServerID(int menuServerID) {
        this.menuServerID = menuServerID;
    }

    public int getParentServerID() {
        return parentServerID;
    }

    public void setParentServerID(int parentServerID) {
        this.parentServerID = parentServerID;
    }

    public String getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(String ownerID) {
        this.ownerID = ownerID;
    }

    public String getOrgOwnerID() {
        return orgOwnerID;
    }

    public void setOrgOwnerID(String orgOwnerID) {
        this.orgOwnerID = orgOwnerID;
    }

    public int getTeamOwnerBIT() {
        return teamOwnerBIT;
    }

    public void setTeamOwnerBIT(int teamOwnerBIT) {
        this.teamOwnerBIT = teamOwnerBIT;
    }

    public int getUnixpermsBITS() {
        return unixpermsBITS;
    }

    public void setUnixpermsBITS(int unixpermsBITS) {
        this.unixpermsBITS = unixpermsBITS;
    }

    public int getStatusesBITS() {
        return statusesBITS;
    }

    public void setStatusesBITS(int statusesBITS) {
        this.statusesBITS = statusesBITS;
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

    public List<cMenuModel> getSubMenuModels() {
        return subMenuModels;
    }

    public void setSubMenuModels(ArrayList<cMenuModel> subMenuModels) {
        this.subMenuModels = subMenuModels;
    }

    public boolean isHasChildren() {
        return hasChildren;
    }

    public void setHasChildren(boolean hasChildren) {
        this.hasChildren = hasChildren;
    }

    public boolean isGroup() {
        return isGroup;
    }

    public void setGroup(boolean group) {
        isGroup = group;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof cMenuModel)) return false;
        cMenuModel menuModel = (cMenuModel) o;
        return getMenuServerID() == menuModel.getMenuServerID() &&
                getParentServerID() == menuModel.getParentServerID() &&
                getOwnerID() == menuModel.getOwnerID() &&
                getOrgOwnerID() == menuModel.getOrgOwnerID() &&
                getTeamOwnerBIT() == menuModel.getTeamOwnerBIT() &&
                getUnixpermsBITS() == menuModel.getUnixpermsBITS() &&
                getStatusesBITS() == menuModel.getStatusesBITS() &&
                Objects.equals(getName(), menuModel.getName()) &&
                Objects.equals(getDescription(), menuModel.getDescription()) &&
                Objects.equals(getCreatedDate(), menuModel.getCreatedDate()) &&
                Objects.equals(getModifiedDate(), menuModel.getModifiedDate()) &&
                Objects.equals(getSyncedDate(), menuModel.getSyncedDate()) &&
                Objects.equals(getSubMenuModels(), menuModel.getSubMenuModels());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMenuServerID(), getParentServerID(), getOwnerID(),
                getOrgOwnerID(), getTeamOwnerBIT(), getUnixpermsBITS(), getStatusesBITS(),
                getName(), getDescription(), getCreatedDate(), getModifiedDate(),
                getSyncedDate(), getSubMenuModels());
    }

    @Override
    public int compareTo(cMenuModel menuModel) {
        int compareID=menuModel.getMenuServerID();
        /* For Ascending order*/
        return this.getMenuServerID() - compareID;
    }
}
