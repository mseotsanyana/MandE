package com.me.mseotsanyana.mande.BRBAC.DAL;

import com.me.mseotsanyana.mande.BRBAC.DAL.cValueModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class cOrganizationModel
{
    private int organizationID;
    private int addressID;

    private int ownerID;
    private String organizationName;
    private String telephone;
    private String fax;

    private String vision;
    private String mission;
    private String emailAddress;
    private String website;
    private Date createDate;

    public cOrganizationModel(){

    }

    cOrganizationModel(int organizationID, String organizationName){
        this.organizationID = organizationID;
        this.organizationName = organizationName;
    }

    cOrganizationModel(int organizationID, int contactPersonID, int ownerID,
                       String organizationName, String physicalAddress, String telephone,
                       String fax, String vision, String mission, List<cValueModel> values,
                       String emailAddress, String website, Date createDate){
        this.organizationID = organizationID;
        this.organizationName = organizationName;
    }

    public int getOrganizationID() {
        return organizationID;
    }

    public void setOrganizationID(int organizationID) {
        this.organizationID = organizationID;
    }

    public int getAddressID() {
        return addressID;
    }

    public void setAddressID(int addressID) {
        this.addressID = addressID;
    }

    public int getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(int ownerID) {
        this.ownerID = ownerID;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
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

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

}
