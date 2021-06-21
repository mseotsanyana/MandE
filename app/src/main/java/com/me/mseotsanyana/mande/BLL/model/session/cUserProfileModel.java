package com.me.mseotsanyana.mande.BLL.model.session;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.firestore.Exclude;

import java.util.Date;

public class cUserProfileModel implements Parcelable {
    private String userServerID;

    /*private String ownerID;
    private String orgOwnerID;
    private int teamOwnerBIT;
    private int unixpermsBITS;
    private int statusesBITS;*/

    private String photo;
    private String name;
    private String surname;
    private String gender;
    private String location;
    private String phone;
    private String email;
    private Date createdDate;
    private Date modifiedDate;

    public cUserProfileModel(){}

    public cUserProfileModel(String userServerID, String name, String surname,
                             String email){
        this.userServerID = userServerID;
        this.name = name;
        this.surname = surname;
        this.email = email;
    };

    public cUserProfileModel(String userServerID, String name, String surname,
                             String location, String phone, String email){
        this.userServerID = userServerID;
        this.name = name;
        this.surname = surname;
        this.location = location;
        this.phone = phone;
        this.email = email;
    };

    @Exclude
    public String getUserServerID() {
        return userServerID;
    }

    public void setUserServerID(String userServerID) {
        this.userServerID = userServerID;
    }

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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    protected cUserProfileModel(Parcel in) {
        userServerID = in.readString();
        photo = in.readString();
        name = in.readString();
        surname = in.readString();
        gender = in.readString();
        location = in.readString();
        phone = in.readString();
        email = in.readString();
        //createdDate = in.readString();
        //modifiedDate = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(userServerID);
        dest.writeString(photo);
        dest.writeString(name);
        dest.writeString(surname);
        dest.writeString(gender);
        dest.writeString(location);
        dest.writeString(phone);
        dest.writeString(email);
        //dest.writeString(createdDate);
        //dest.writeString(modifiedDate);
    }

    @Override
    public int describeContents() {
        return 0;
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
}
