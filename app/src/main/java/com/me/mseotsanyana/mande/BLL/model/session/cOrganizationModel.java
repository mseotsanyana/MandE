package com.me.mseotsanyana.mande.BLL.model.session;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.firestore.Exclude;

import java.util.Date;
import java.util.List;
import java.util.Set;

public class cOrganizationModel implements Parcelable {
    private String organizationServerID;
    private int organizationType;

    private String ownerID;
    private String orgOwnerID;
    private int teamOwnerBIT;
    private List<Integer> unixpermsBITS;
    private int statusesBITS;

    private int numOrganizations;

    private String name;
    private String email;
    private String website;
    private Date createdDate;
    private Date modifiedDate;

    private Date syncedDate;

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
        this.setOwnerID(organizationModel.getOwnerID());
        this.setOrgOwnerID(organizationModel.getOrgOwnerID());
        this.setTeamOwnerBIT(organizationModel.getTeamOwnerBIT());
        this.setUnixpermsBITS(organizationModel.getUnixpermsBITS());
        this.setStatusesBITS(organizationModel.getStatusesBITS());
        this.setName(organizationModel.getName());
        this.setEmail(organizationModel.getEmail());
        this.setWebsite(organizationModel.getWebsite());
        this.setCreatedDate(organizationModel.getCreatedDate());
        this.setModifiedDate(organizationModel.getModifiedDate());
        this.setSyncedDate(organizationModel.getSyncedDate());
    }

    protected cOrganizationModel(Parcel in) {
        organizationServerID = in.readString();
        ownerID = in.readString();
        orgOwnerID = in.readString();
        teamOwnerBIT = in.readInt();
        //unixpermsBITS = in.readInt();
        statusesBITS = in.readInt();
        name = in.readString();
        email = in.readString();
        website = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(organizationServerID);
        dest.writeString(ownerID);
        dest.writeString(orgOwnerID);
        dest.writeInt(teamOwnerBIT);
        //dest.writeInt(unixpermsBITS);
        dest.writeInt(statusesBITS);
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

    public String getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(String ownerID) {
        this.ownerID = ownerID;
    }

    public String getOrgOwnerID() {
        return orgOwnerID;
    }

    public void setOrgOwnerID(String orgOwnerID) {
        this.orgOwnerID = orgOwnerID;
    }

    public int getTeamOwnerBIT() {
        return teamOwnerBIT;
    }

    public void setTeamOwnerBIT(int teamOwnerBIT) {
        this.teamOwnerBIT = teamOwnerBIT;
    }

    public List<Integer> getUnixpermsBITS() {
        return unixpermsBITS;
    }

    public void setUnixpermsBITS(List<Integer> unixpermsBITS) {
        this.unixpermsBITS = unixpermsBITS;
    }

    public int getStatusesBITS() {
        return statusesBITS;
    }

    public void setStatusesBITS(int statusesBITS) {
        this.statusesBITS = statusesBITS;
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
    public Date getSyncedDate() {
        return syncedDate;
    }

    public void setSyncedDate(Date syncedDate) {
        this.syncedDate = syncedDate;
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
