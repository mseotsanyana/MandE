package com.me.mseotsanyana.mande.BRBAC.BLL;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class cOrganizationDomain implements Parcelable {
    private int organizationID;
    private int addressID;
    private int ownerID;
    private String organizationName;
    private String physicalAddress;
    private String telephone;
    private String fax;
    private String vision;
    private String mission;
    private String emailAddress;
    private String website;
    private Date createDate;

    public cOrganizationDomain() {
        super();
    }

    public cOrganizationDomain(String orginization) {
        this.setOrganizationName(orginization);
    }

    /**
     * For reconstructing the object reading from the Parcel data
     * @param in
     */

    private cOrganizationDomain(Parcel in) {
        super();
        this.setOrganizationID(in.readInt());
        this.setOwnerID(in.readInt());
        this.setOrganizationName(in.readString());
        this.setPhysicalAddress(in.readString());
        this.setTelephone(in.readString());
        this.setFax(in.readString());
        this.setVision(in.readString());
        this.setMission(in.readString());
        this.setEmailAddress(in.readString());
        this.setWebsite(in.readString());
        this.setCreateDate(new Date(in.readLong()));
    }

    public static final Creator<cOrganizationDomain> CREATOR = new Creator<cOrganizationDomain>() {
        @Override
        public cOrganizationDomain createFromParcel(Parcel in) {
            return new cOrganizationDomain(in);
        }

        @Override
        public cOrganizationDomain[] newArray(int size) {
            return new cOrganizationDomain[size];
        }
    };

    @Override
    public int describeContents() {
        return hashCode();
    }

    @Override
    public void writeToParcel(Parcel out, int i) {
        out.writeInt(this.getOrganizationID());
        out.writeInt(this.getOwnerID());
        out.writeString(this.getOrganizationName());
        out.writeString(this.getPhysicalAddress());
        out.writeString(this.getTelephone());
        out.writeString(this.getFax());
        out.writeString(this.getVision());
        out.writeString(this.getMission());
        out.writeString(this.getEmailAddress());
        out.writeString(this.getWebsite());
        out.writeLong(this.getCreateDate().getTime());
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

    public String getPhysicalAddress() {
        return physicalAddress;
    }

    public void setPhysicalAddress(String physicalAddress) {
        this.physicalAddress = physicalAddress;
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

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
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
