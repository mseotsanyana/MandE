package com.me.mseotsanyana.mande.BLL.model.session;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by mseotsanyana on 2021/03/18.
 */

public class cTeamModel implements Parcelable {
    private String teamServerID;
    private String orgServerID;

    private String ownerID;
    private String orgOwnerID;
    private int teamOwnerBIT;
    private int unixpermsBITS;
    private int statusesBITS;

    private String name;
    private String description;

    private Date createdDate;
    private Date modifiedDate;
    private Date syncedDate;

    public cTeamModel(){

    }

    public String getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(String ownerID) {
        this.ownerID = ownerID;
    }

    public String getTeamServerID() {
        return teamServerID;
    }

    public void setTeamServerID(String teamServerID) {
        this.teamServerID = teamServerID;
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

    public String getOrgServerID() {
        return orgServerID;
    }

    public void setOrgServerID(String orgServerID) {
        this.orgServerID = orgServerID;
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
        parcel.writeString(ownerID);
        parcel.writeString(orgOwnerID);
        parcel.writeInt(teamOwnerBIT);
        parcel.writeInt(unixpermsBITS);
        parcel.writeInt(statusesBITS);
        parcel.writeString(teamServerID);
        parcel.writeString(orgServerID);
        parcel.writeString(name);
        parcel.writeString(description);
    }

    protected cTeamModel(Parcel in) {
        ownerID = in.readString();
        orgOwnerID = in.readString();
        teamOwnerBIT = in.readInt();
        unixpermsBITS = in.readInt();
        statusesBITS = in.readInt();
        teamServerID = in.readString();
        orgServerID = in.readString();
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
