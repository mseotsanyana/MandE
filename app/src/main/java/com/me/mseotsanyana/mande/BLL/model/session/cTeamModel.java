package com.me.mseotsanyana.mande.BLL.model.session;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.firestore.Exclude;

import java.util.Date;
import java.util.List;

/**
 * Created by mseotsanyana on 2021/03/18.
 */

public class cTeamModel implements Parcelable {
    private String compositeServerID;
    private String teamServerID;
    private String organizationServerID;

    private String userOwnerID;
    private String organizationOwnerID;
    private int teamOwnerBIT;
    private List<Integer> unixpermBITS;
    private int statusBIT;

    private int numTeams;

    private String name;
    private String description;

    private Date createdDate;
    private Date modifiedDate;

    private Date syncedDate;

    public cTeamModel(){

    }

    @Exclude
    public String getCompositeServerID() {
        return compositeServerID;
    }

    public void setCompositeServerID(String compositeServerID) {
        this.compositeServerID = compositeServerID;
    }

    public String getTeamServerID() {
        return teamServerID;
    }

    public void setTeamServerID(String teamServerID) {
        this.teamServerID = teamServerID;
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

    public String getOrganizationServerID() {
        return organizationServerID;
    }

    public void setOrganizationServerID(String organizationServerID) {
        this.organizationServerID = organizationServerID;
    }

    public int getNumTeams() {
        return numTeams;
    }

    public void setNumTeams(int numTeams) {
        this.numTeams = numTeams;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(userOwnerID);
        parcel.writeString(organizationOwnerID);
        parcel.writeInt(teamOwnerBIT);
        //parcel.writeInt(unixpermsBITS);
        parcel.writeInt(statusBIT);
        parcel.writeString(teamServerID);
        parcel.writeString(organizationServerID);
        parcel.writeString(name);
        parcel.writeString(description);
    }

    protected cTeamModel(Parcel in) {
        userOwnerID = in.readString();
        organizationOwnerID = in.readString();
        teamOwnerBIT = in.readInt();
        //unixpermsBITS = in.readInt();
        statusBIT = in.readInt();
        teamServerID = in.readString();
        organizationServerID = in.readString();
        name = in.readString();
        description = in.readString();
    }

    public static final Creator<cTeamModel> CREATOR = new Creator<cTeamModel>() {
        @Override
        public cTeamModel createFromParcel(Parcel in) {
            return new cTeamModel(in);
        }

        @Override
        public cTeamModel[] newArray(int size) {
            return new cTeamModel[size];
        }
    };
}
