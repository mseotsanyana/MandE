package com.me.mseotsanyana.mande.BRBAC.BLL;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by mseotsanyana on 2017/08/24.
 */

public class cSessionDomain implements Parcelable {
    private int sessionID;
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
    private String description;
    private String email;
    private String website;
    private String phone;
    private String uniqueID;
    private String password;
    private String salt;
    private String oldPassword;
    private String newPassword;
    private Date createDate;

    protected cSessionDomain(Parcel in) {
        this.setSessionID(in.readInt());
        this.setUserID(in.readInt());
        this.setOwnerID(in.readInt());
        this.setGroupBITS(in.readInt());
        this.setPermsBITS(in.readInt());
        this.setStatusBITS(in.readInt());
        this.setName(in.readString());
        this.setSurname(in.readString());
        this.setDescription(in.readString());
        this.setEmail(in.readString());
        this.setUniqueID(in.readString());
        this.setPassword(in.readString());
        this.setSalt(in.readString());
        this.setOldPassword(in.readString());
        this.setNewPassword(in.readString());
    }

    public cSessionDomain(){}

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(this.getSessionID());
        out.writeInt(this.getUserID());
        out.writeInt(this.getOwnerID());
        out.writeInt(this.getGroupBITS());
        out.writeInt(this.getPermsBITS());
        out.writeInt(this.getStatusBITS());
        out.writeString(this.getName());
        out.writeString(this.getSurname());
        out.writeString(this.getDescription());
        out.writeString(this.getEmail());
        out.writeString(this.getUniqueID());
        out.writeString(this.getPassword());
        out.writeString(this.getSalt());
        out.writeString(this.getOldPassword());
        out.writeString(this.getNewPassword());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<cSessionDomain> CREATOR = new Creator<cSessionDomain>() {
        @Override
        public cSessionDomain createFromParcel(Parcel in) {
            return new cSessionDomain(in);
        }

        @Override
        public cSessionDomain[] newArray(int size) {
            return new cSessionDomain[size];
        }
    };

    public int getSessionID() {
        return sessionID;
    }

    public void setSessionID(int sessionID) {
        this.sessionID = sessionID;
    }


    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
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

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String toString() {
        return "cUserDomain{" +
                "ID='"       + getSessionID() + '\'' +
                ", NAME='"   + getName() + '\'' +
                ", SURNAME='"  + getSurname() + '\'' +
                ", DESCRIPTION='" + getDescription() + '\'' +
                ", EMAIL='"      + getEmail() + '\'' +
                '}';
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

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
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
}
