package com.me.mseotsanyana.mande.BRBAC.DAL;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by mseotsanyana on 2018/01/30.
 */

public class cAddressModel {
    private int addressID;
    private int serverID;
    private int ownerID;
    private int orgID;
    private int groupBITS;
    private int permsBITS;
    private int statusBITS;
    private String street;
    private String city;
    private String province;
    private String postalCode;
    private String country;
    private Date createdDate;
    private Date modifiedDate;
    private Date syncedDate;

    cAddressModel(){}

    cAddressModel(cAddressModel addressModel){
        this.setAddressID(addressModel.getAddressID());
        this.setServerID(addressModel.getServerID());
        this.setOwnerID(addressModel.getOwnerID());
        this.setOrgID(addressModel.getOrgID());
        this.setGroupBITS(addressModel.getGroupBITS());
        this.setPermsBITS(addressModel.getPermsBITS());
        this.setStatusBITS(addressModel.getStatusBITS());
        this.setStreet(addressModel.getStreet());
        this.setCity(addressModel.getCity());
        this.setProvince(addressModel.getProvince());
        this.setPostalCode(addressModel.getPostalCode());
        this.setCountry(addressModel.getCountry());
        this.setCreatedDate(addressModel.getCreatedDate());
        this.setModifiedDate(addressModel.getModifiedDate());
        this.setSyncedDate(addressModel.getSyncedDate());
    }

    public int getAddressID() {
        return addressID;
    }

    public void setAddressID(int addressID) {
        this.addressID = addressID;
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

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
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
}
