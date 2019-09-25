package com.me.mseotsanyana.mande.BRBAC.BLL;

import android.os.Parcel;
import android.os.Parcelable;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by mseotsanyana on 2017/08/24.
 */

public class cUserDomain implements Parcelable {
    private int userID;
    private int organizationID;
    private int addressID;
    private int ownerID;
    private int groupBITS;
    private int permsBITS;
    private int statusBITS;
    private String photoPath;
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

    protected cUserDomain(Parcel in) {
        this.setUserID(in.readInt());
        this.setOrganizationID(in.readInt());
        this.setOwnerID(in.readInt());
        this.setGroupBITS(in.readInt());
        this.setPermsBITS(in.readInt());
        this.setStatusBITS(in.readInt());
        this.setName(in.readString());
        this.setSurname(in.readString());
        this.setGender(in.readString());
        this.setDescription(in.readString());
        this.setEmail(in.readString());
        this.setUniqueID(in.readString());
        this.setPassword(in.readString());
        this.setSalt(in.readString());
        this.setOldPassword(in.readString());
        this.setNewPassword(in.readString());
        this.setCreatedDate(new Timestamp(in.readLong()));
        this.setModifiedDate(new Timestamp(in.readLong()));
        this.setSyncedDate(new Timestamp(in.readLong()));
    }

    public cUserDomain(){}

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(this.getUserID());
        out.writeInt(this.getOrganizationID());
        out.writeInt(this.getOwnerID());
        out.writeInt(this.getGroupBITS());
        out.writeInt(this.getPermsBITS());
        out.writeInt(this.getStatusBITS());
        out.writeString(this.getName());
        out.writeString(this.getSurname());
        out.writeString(this.getGender());
        out.writeString(this.getDescription());
        out.writeString(this.getEmail());
        out.writeString(this.getUniqueID());
        out.writeString(this.getPassword());
        out.writeString(this.getSalt());
        out.writeString(this.getOldPassword());
        out.writeString(this.getNewPassword());
        out.writeLong(this.getCreatedDate().getTime());
        out.writeLong(this.getModifiedDate().getTime());
        out.writeLong(this.getSyncedDate().getTime());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<cUserDomain> CREATOR = new Creator<cUserDomain>() {
        @Override
        public cUserDomain createFromParcel(Parcel in) {
            return new cUserDomain(in);
        }

        @Override
        public cUserDomain[] newArray(int size) {
            return new cUserDomain[size];
        }
    };

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getAddressID() {
        return addressID;
    }

    public void setAddressID(int addressID) {
        this.addressID = addressID;
    }

    public int getOrganizationID() {
        return organizationID;
    }

    public void setOrganizationID(int organizationID) {
        this.organizationID = organizationID;
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

    public int getStatusBITS() {
        return statusBITS;
    }

    public void setStatusBITS(int statusBITS) {
        this.statusBITS = statusBITS;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
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

/*
    public String toString() {
        return "cUserDomain{" +
                "ID='"       + getUserID() + '\'' +
                ", NAME='"   + getName() + '\'' +
                ", SURNAME='"  + getSurname() + '\'' +
                ", DESCRIPTION='" + getDescription() + '\'' +
                ", EMAIL='"      + getEmail() + '\'' +
                '}';
    }
    */
}
