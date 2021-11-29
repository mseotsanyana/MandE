package com.me.mseotsanyana.mande.BLL.model.logframe;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.firestore.Exclude;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class cComponentModel implements Parcelable {
    private String componentServerID;
    private cLogFrameModel logFrameModel;

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

    /*** mappings ***/
    private List<cQuestionModel> questionModels;
    private List<cRaidModel> raidModels;

    public cComponentModel(){
        /* mappings */
        logFrameModel = new cLogFrameModel();
        questionModels = new ArrayList<>();
        raidModels = new ArrayList<>();
    }

    protected cComponentModel(Parcel in) {
        componentServerID = in.readString();
        logFrameModel = in.readParcelable(cLogFrameModel.class.getClassLoader());
        userOwnerID = in.readString();
        organizationOwnerID = in.readString();
        teamOwnerBIT = in.readInt();
        statusBIT = in.readInt();
        name = in.readString();
        description = in.readString();
        questionModels = in.createTypedArrayList(cQuestionModel.CREATOR);
    }

    public static final Creator<cComponentModel> CREATOR = new Creator<cComponentModel>() {
        @Override
        public cComponentModel createFromParcel(Parcel in) {
            return new cComponentModel(in);
        }

        @Override
        public cComponentModel[] newArray(int size) {
            return new cComponentModel[size];
        }
    };

    public String getComponentServerID() {
        return componentServerID;
    }

    public void setComponentServerID(String componentServerID) {
        this.componentServerID = componentServerID;
    }

    public cLogFrameModel getLogFrameModel() {
        return logFrameModel;
    }

    public void setLogFrameModel(cLogFrameModel logFrameModel) {
        this.logFrameModel = logFrameModel;
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

    @Exclude
    public List<cQuestionModel> getQuestionModels() {
        return questionModels;
    }

    @Exclude
    public List<cRaidModel> getRaidModels() {
        return raidModels;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(componentServerID);
        parcel.writeParcelable(logFrameModel, i);
        parcel.writeString(userOwnerID);
        parcel.writeString(organizationOwnerID);
        parcel.writeInt(teamOwnerBIT);
        parcel.writeInt(statusBIT);
        parcel.writeString(name);
        parcel.writeString(description);
        parcel.writeTypedList(questionModels);
    }
}
