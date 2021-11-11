package com.me.mseotsanyana.mande.BLL.model.session;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.firestore.Exclude;

import java.util.Date;

public class cUserProfileModel implements Parcelable {
    private String userServerID;

    private String userOwnerID;

    private String photo;
    private String name;
    private String surname;
    private String designation;

    private String location;
    private String email;
    private String website;
    private String phone;


    private Date createdDate;
    private Date modifiedDate;

    public cUserProfileModel(){}

    public cUserProfileModel(String userServerID, String name, String surname,
                             String email){
        this.userServerID = userServerID;
        this.name = name;
        this.surname = surname;
        this.email = email;
    }

    public cUserProfileModel(String userServerID, String name, String surname,
                             String designation, String phone, String email,
                             String website, String location){
        this.userServerID = userServerID;
        this.name = name;
        this.surname = surname;
        this.location = location;
        this.phone = phone;
        this.email = email;
    }

    @Exclude
    public String getUserServerID() {
        return userServerID;
    }

    public void setUserServerID(String userServerID) {
        this.userServerID = userServerID;
    }

    public String getUserOwnerID() {
        return userOwnerID;
    }

    public void setUserOwnerID(String userOwnerID) {
        this.userOwnerID = userOwnerID;
    }

    @Exclude
    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
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

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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

    @Override
    public int describeContents() {
        return 0;
    }

    protected cUserProfileModel(Parcel in) {
        userServerID = in.readString();
        photo = in.readString();
        name = in.readString();
        surname = in.readString();
        designation = in.readString();
        location = in.readString();
        email = in.readString();
        website = in.readString();
        phone = in.readString();
    }

    public static final Creator<cUserProfileModel> CREATOR = new Creator<cUserProfileModel>() {
        @Override
        public cUserProfileModel createFromParcel(Parcel in) {
            return new cUserProfileModel(in);
        }

        @Override
        public cUserProfileModel[] newArray(int size) {
            return new cUserProfileModel[size];
        }
    };

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(userServerID);
        parcel.writeString(photo);
        parcel.writeString(name);
        parcel.writeString(surname);
        parcel.writeString(designation);
        parcel.writeString(location);
        parcel.writeString(email);
        parcel.writeString(website);
        parcel.writeString(phone);
    }
}
