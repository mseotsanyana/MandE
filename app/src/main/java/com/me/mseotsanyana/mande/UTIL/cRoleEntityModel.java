package com.me.mseotsanyana.mande.UTIL;

import java.util.Date;

/**
 * Created by mseotsanyana on 2017/06/27.
 */

public class cRoleEntityModel {
    private int entityID;
    private int roleID;
    private int ownerID;
    private int groupBITS;
    private int permsBITS;
    private int typeBITS;
    private int statusBITS;
    private int permission;
    private Date createDate;

    public int getEntityID() {
        return entityID;
    }

    public void setEntityID(int entityID) {
        this.entityID = entityID;
    }

    public int getRoleID() {
        return roleID;
    }

    public void setRoleID(int roleID) {
        this.roleID = roleID;
    }

    public int getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(int ownerID) {
        this.ownerID = ownerID;
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

    public int getTypeBITS() {
        return typeBITS;
    }

    public void setTypeBITS(int typeBITS) {
        this.typeBITS = typeBITS;
    }

    public int getStatusBITS() {
        return statusBITS;
    }

    public void setStatusBITS(int statusBITS) {
        this.statusBITS = statusBITS;
    }

    public int getPermission() {
        return permission;
    }

    public void setPermission(int permission) {
        this.permission = permission;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

}
