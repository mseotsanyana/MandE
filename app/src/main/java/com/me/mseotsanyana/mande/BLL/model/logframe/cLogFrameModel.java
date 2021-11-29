package com.me.mseotsanyana.mande.BLL.model.logframe;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class cLogFrameModel implements Parcelable {
    private String logFrameServerID;
    private String parentServerID;
    private List<String> children;
    private List<String> components;

    // common attributes
    private String userOwnerID;
    private String organizationOwnerID;
    private int teamOwnerBIT;
    private List<Integer> unixpermBITS;
    private int statusBIT;

    // meta attributes
    private String name;
    private String description;
    private Date startDate;
    private Date endDate;
    private Date createdDate;
    private Date modifiedDate;

    public cLogFrameModel(){
        parentServerID = null;
        children = new ArrayList<>();
        components = new ArrayList<>();
    }

    protected cLogFrameModel(Parcel in) {
        logFrameServerID = in.readString();
        parentServerID = in.readString();
        children = in.createStringArrayList();
        components = in.createStringArrayList();
        userOwnerID = in.readString();
        organizationOwnerID = in.readString();
        teamOwnerBIT = in.readInt();
        statusBIT = in.readInt();
        name = in.readString();
        description = in.readString();
    }

    public static final Creator<cLogFrameModel> CREATOR = new Creator<cLogFrameModel>() {
        @Override
        public cLogFrameModel createFromParcel(Parcel in) {
            return new cLogFrameModel(in);
        }

        @Override
        public cLogFrameModel[] newArray(int size) {
            return new cLogFrameModel[size];
        }
    };

    public String getLogFrameServerID() {
        return logFrameServerID;
    }

    public void setLogFrameServerID(String logFrameServerID) {
        this.logFrameServerID = logFrameServerID;
    }

    public String getParentServerID() {
        return parentServerID;
    }

    public void setParentServerID(String parentServerID) {
        this.parentServerID = parentServerID;
    }

    public List<String> getChildren() {
        return children;
    }

    public void setChildren(List<String> children) {
        this.children = children;
    }


    public List<String> getComponents() {
        return components;
    }

    public void setComponents(List<String> components) {
        this.components = components;
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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
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

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(logFrameServerID);
        parcel.writeString(parentServerID);
        parcel.writeStringList(children);
        parcel.writeStringList(components);
        parcel.writeString(userOwnerID);
        parcel.writeString(organizationOwnerID);
        parcel.writeInt(teamOwnerBIT);
        parcel.writeInt(statusBIT);
        parcel.writeString(name);
        parcel.writeString(description);
    }
}
