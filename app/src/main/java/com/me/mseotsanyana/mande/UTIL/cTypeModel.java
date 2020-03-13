package com.me.mseotsanyana.mande.UTIL;

import java.util.Date;

/**
 * Created by mseotsanyana on 2017/08/24.
 */

public class cTypeModel {
    private int typeID;
    private int ownerID;
    private int groupBITS;
    private int permsBITS;
    private int typeBITS;
    private int statusBITS;
    private String name;
    private String description;
    private Date createDate;

    public int getTypeID() {
        return typeID;
    }

    public void setTypeID(int typeID) {
        this.typeID = typeID;
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

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
