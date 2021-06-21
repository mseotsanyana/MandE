package com.me.mseotsanyana.mande.BLL.model.session;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;
import java.util.List;
import java.util.Objects;

public class cUserAccountModel implements Parcelable {
    private String userAccountServerID;

    private String organizationServerID;
    private String teamServerID;
    private String userServerID;
    private String planServerID;
    private boolean currentOrganization;

    private String ownerID;
    private String orgOwnerID;
    private int teamOwnerBIT;
    private List<Integer> unixpermsBITS;
    private int statusesBITS;

    private Date createdDate;
    private Date modifiedDate;

    private int numUserAccounts;

    public cUserAccountModel(){}

    public cUserAccountModel(String userAccountServerID, String organizationServerID, String teamServerID,
                             String userServerID, String planServerID, boolean currentOrganization){
        this.userAccountServerID = userAccountServerID;
        this.userServerID = userServerID;
        this.teamServerID = teamServerID;
        this.organizationServerID = organizationServerID;
        this.planServerID = planServerID;
        this.currentOrganization = currentOrganization;
    }

    public int getNumUserAccounts() {
        return numUserAccounts;
    }

    public void setNumUserAccounts(int numUserAccounts) {
        this.numUserAccounts = numUserAccounts;
    }

    public String getUserAccountServerID() {
        return userAccountServerID;
    }

    public void setUserAccountServerID(String userAccountServerID) {
        this.userAccountServerID = userAccountServerID;
    }

    public String getOrganizationServerID() {
        return organizationServerID;
    }

    public void setOrganizationServerID(String organizationServerID) {
        this.organizationServerID = organizationServerID;
    }

    public String getTeamServerID() {
        return teamServerID;
    }

    public void setTeamServerID(String teamServerID) {
        this.teamServerID = teamServerID;
    }

    public String getUserServerID() {
        return userServerID;
    }

    public void setUserServerID(String userServerID) {
        this.userServerID = userServerID;
    }

    public String getPlanServerID() {
        return planServerID;
    }

    public void setPlanServerID(String planServerID) {
        this.planServerID = planServerID;
    }

    public boolean isCurrentOrganization() {
        return currentOrganization;
    }

    public void setCurrentOrganization(boolean currentOrganization) {
        this.currentOrganization = currentOrganization;
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

    protected cUserAccountModel(Parcel in) {
        userAccountServerID = in.readString();
        organizationServerID = in.readString();
        teamServerID = in.readString();
        userServerID = in.readString();
        planServerID = in.readString();
        currentOrganization = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(userAccountServerID);
        dest.writeString(organizationServerID);
        dest.writeString(teamServerID);
        dest.writeString(userServerID);
        dest.writeString(planServerID);
        dest.writeByte((byte) (currentOrganization ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<cUserAccountModel> CREATOR = new Creator<cUserAccountModel>() {
        @Override
        public cUserAccountModel createFromParcel(Parcel in) {
            return new cUserAccountModel(in);
        }

        @Override
        public cUserAccountModel[] newArray(int size) {
            return new cUserAccountModel[size];
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof cUserAccountModel)) return false;
        cUserAccountModel that = (cUserAccountModel) o;
        return isCurrentOrganization() == that.isCurrentOrganization() &&
                getUserAccountServerID().equals(that.getUserAccountServerID()) &&
                getOrganizationServerID().equals(that.getOrganizationServerID()) &&
                getTeamServerID().equals(that.getTeamServerID()) &&
                getUserServerID().equals(that.getUserServerID()) &&
                getPlanServerID().equals(that.getPlanServerID());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUserAccountServerID(),
                getOrganizationServerID(), getTeamServerID(),
                getUserServerID(), getPlanServerID(),
                isCurrentOrganization());
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
}
