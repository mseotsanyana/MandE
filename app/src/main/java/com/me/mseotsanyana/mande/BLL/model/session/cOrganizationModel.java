package com.me.mseotsanyana.mande.BLL.model.session;

import android.os.Parcel;
import android.os.Parcelable;
import android.provider.ContactsContract;

import java.util.Date;
import java.util.Objects;
import java.util.Set;

public class cOrganizationModel implements Parcelable {
    private String orgServerID;

    private String ownerID;
    private String orgOwnerID;
    private int teamOwnerBIT;
    private int unixpermsBITS;
    private int statusesBITS;

    private int orgType;
    private String name;
    private String email;
    private String website;
    private Date createdDate;
    private Date modifiedDate;
    private Date syncedDate;

    Set<cUserModel> userModelSet;
    Set<cRoleModel> roleModelSet;
    Set<cValueModel> valueModelSet;
    Set<cAddressModel> addressModelSet;

    public cOrganizationModel(){}

    public cOrganizationModel(int orgType, String name, String email, String website){
        this.orgType = orgType;
        this.name = name;
        this.email = email;
        this.website = website;
    }

    public cOrganizationModel(cOrganizationModel organizationModel){
        this.setOrgServerID(organizationModel.getOrgServerID());
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
        orgServerID = in.readString();
        ownerID = in.readString();
        orgOwnerID = in.readString();
        teamOwnerBIT = in.readInt();
        unixpermsBITS = in.readInt();
        statusesBITS = in.readInt();
        name = in.readString();
        email = in.readString();
        website = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(orgServerID);
        dest.writeString(ownerID);
        dest.writeString(orgOwnerID);
        dest.writeInt(teamOwnerBIT);
        dest.writeInt(unixpermsBITS);
        dest.writeInt(statusesBITS);
        dest.writeInt(orgType);
        dest.writeString(name);
        dest.writeString(email);
        dest.writeString(website);
    }

    public String getOrgServerID() {
        return orgServerID;
    }

    public void setOrgServerID(String orgServerID) {
        this.orgServerID = orgServerID;
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

    public int getUnixpermsBITS() {
        return unixpermsBITS;
    }

    public void setUnixpermsBITS(int unixpermsBITS) {
        this.unixpermsBITS = unixpermsBITS;
    }

    public int getStatusesBITS() {
        return statusesBITS;
    }

    public void setStatusesBITS(int statusesBITS) {
        this.statusesBITS = statusesBITS;
    }

    public int getOrgType() {
        return orgType;
    }

    public void setOrgType(int orgType) {
        this.orgType = orgType;
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

    public Date getSyncedDate() {
        return syncedDate;
    }

    public void setSyncedDate(Date syncedDate) {
        this.syncedDate = syncedDate;
    }

    public Set<cUserModel> getUserModelSet() {
        return userModelSet;
    }

    public void setUserModelSet(Set<cUserModel> userModelSet) {
        this.userModelSet = userModelSet;
    }

    public Set<cRoleModel> getRoleModelSet() {
        return roleModelSet;
    }

    public void setRoleModelSet(Set<cRoleModel> roleModelSet) {
        this.roleModelSet = roleModelSet;
    }

    public Set<cValueModel> getValueModelSet() {
        return valueModelSet;
    }

    public void setValueModelSet(Set<cValueModel> valueModelSet) {
        this.valueModelSet = valueModelSet;
    }

    public Set<cAddressModel> getAddressModelSet() {
        return addressModelSet;
    }

    public void setAddressModelSet(Set<cAddressModel> addressModelSet) {
        this.addressModelSet = addressModelSet;
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
