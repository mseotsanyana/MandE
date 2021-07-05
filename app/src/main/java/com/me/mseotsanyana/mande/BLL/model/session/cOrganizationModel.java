package com.me.mseotsanyana.mande.BLL.model.session;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.firestore.Exclude;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class cOrganizationModel implements Parcelable {
    private String organizationServerID;

    private String userOwnerID;
    private String organizationOwnerID;
    private int teamOwnerBIT;
    private List<Integer> unixpermBITS;
    private int statusBIT;

    private String name;
    private String email;
    private String website;
    private Date createdDate;
    private Date modifiedDate;

    private int organizationType;
    private int numOrganizations;

    Set<cUserModel> userModelSet;
    Set<cRoleModel> roleModelSet;

    public cOrganizationModel(){}

    public cOrganizationModel(int organizationType, String name, String email, String website){
        this.organizationType = organizationType;
        this.name = name;
        this.email = email;
        this.website = website;
    }

    public cOrganizationModel(cOrganizationModel organizationModel){
        this.setOrganizationServerID(organizationModel.getOrganizationServerID());
        this.setUserOwnerID(organizationModel.getUserOwnerID());
        this.setOrganizationOwnerID(organizationModel.getOrganizationOwnerID());
        this.setTeamOwnerBIT(organizationModel.getTeamOwnerBIT());
        this.setUnixpermBITS(organizationModel.getUnixpermBITS());
        this.setStatusBIT(organizationModel.getStatusBIT());
        this.setName(organizationModel.getName());
        this.setEmail(organizationModel.getEmail());
        this.setWebsite(organizationModel.getWebsite());
        this.setCreatedDate(organizationModel.getCreatedDate());
        this.setModifiedDate(organizationModel.getModifiedDate());
    }

    protected cOrganizationModel(Parcel in) {
        organizationServerID = in.readString();
        userOwnerID = in.readString();
        organizationOwnerID = in.readString();
        teamOwnerBIT = in.readInt();
        //unixpermsBITS = in.readInt();
        statusBIT = in.readInt();
        name = in.readString();
        email = in.readString();
        website = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(organizationServerID);
        dest.writeString(userOwnerID);
        dest.writeString(organizationOwnerID);
        dest.writeInt(teamOwnerBIT);
        //dest.writeInt(unixpermsBITS);
        dest.writeInt(statusBIT);
        dest.writeInt(organizationType);
        dest.writeString(name);
        dest.writeString(email);
        dest.writeString(website);
    }

    @Exclude
    public String getOrganizationServerID() {
        return organizationServerID;
    }

    public void setOrganizationServerID(String organizationServerID) {
        this.organizationServerID = organizationServerID;
    }

    @Exclude
    public int getOrganizationType() {
        return organizationType;
    }

    public void setOrganizationType(int organizationType) {
        this.organizationType = organizationType;
    }

    public String getUserOwnerID() {
        return userOwnerID;
    }

    public void setUserOwnerID(String userOwnerID) {
        this.userOwnerID = userOwnerID;
    }

    public String getOrganizationOwnerID() {
        return organizationOwnerID;
    }

    public void setOrganizationOwnerID(String organizationOwnerID) {
        this.organizationOwnerID = organizationOwnerID;
    }

    public int getTeamOwnerBIT() {
        return teamOwnerBIT;
    }

    public void setTeamOwnerBIT(int teamOwnerBIT) {
        this.teamOwnerBIT = teamOwnerBIT;
    }

    public List<Integer> getUnixpermBITS() {
        return unixpermBITS;
    }

    public void setUnixpermBITS(List<Integer> unixpermBITS) {
        this.unixpermBITS = unixpermBITS;
    }

    public int getStatusBIT() {
        return statusBIT;
    }

    public void setStatusBIT(int statusBIT) {
        this.statusBIT = statusBIT;
    }

    public int getNumOrganizations() {
        return numOrganizations;
    }

    public void setNumOrganizations(int numOrganizations) {
        this.numOrganizations = numOrganizations;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    @Exclude
    public Set<cUserModel> getUserModelSet() {
        return userModelSet;
    }

    public void setUserModelSet(Set<cUserModel> userModelSet) {
        this.userModelSet = userModelSet;
    }

    @Exclude
    public Set<cRoleModel> getRoleModelSet() {
        return roleModelSet;
    }

    public void setRoleModelSet(Set<cRoleModel> roleModelSet) {
        this.roleModelSet = roleModelSet;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<cOrganizationModel> CREATOR = new Creator<cOrganizationModel>() {
        @Override
        public cOrganizationModel createFromParcel(Parcel in) {
            return new cOrganizationModel(in);
        }

        @Override
        public cOrganizationModel[] newArray(int size) {
            return new cOrganizationModel[size];
        }
    };
}
