package com.me.mseotsanyana.mande.BLL.model.logframe;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class cComponentModel implements Parcelable {
	private long componentID;
	private long parentID;
	private long serverID;
    private long ownerID;
    private long orgID;
    private int groupBITS;
    private int permsBITS;
    private int statusBITS;
	private String name;
	private String description;
	private Date startDate;
	private Date endDate;
    private Date createdDate;
    private Date modifiedDate;
    private Date syncedDate;

    /*** mappings ***/
    private cLogFrameModel logFrameModel;
    private Set<cQuestionModel> questionModelSet;
    private Set<cRaidModel> raidModelSet;

    public cComponentModel(){
        /* mappings */
        logFrameModel = new cLogFrameModel();
        questionModelSet = new HashSet<>();
        raidModelSet = new HashSet<>();
    }

    protected cComponentModel(Parcel in) {
        componentID = in.readLong();
        parentID = in.readLong();
        serverID = in.readLong();
        ownerID = in.readLong();
        orgID = in.readLong();
        groupBITS = in.readInt();
        permsBITS = in.readInt();
        statusBITS = in.readInt();
        name = in.readString();
        description = in.readString();
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

    public long getComponentID() {
        return componentID;
    }

    public void setComponentID(long componentID) {
        this.componentID = componentID;
    }

    public long getParentID() {
        return parentID;
    }

    public void setParentID(long parentID) {
        this.parentID = parentID;
    }

    public long getServerID() {
        return serverID;
    }

    public void setServerID(long serverID) {
        this.serverID = serverID;
    }

    public long getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(long ownerID) {
        this.ownerID = ownerID;
    }

    public long getOrgID() {
        return orgID;
    }

    public void setOrgID(long orgID) {
        this.orgID = orgID;
    }

    public int getGroupBITS() {
        return groupBITS;
    }

    public void setGroupBITS(int groupBITS) {
        this.groupBITS = groupBITS;
    }

    public int getPermsBITS() {
        return permsBITS;
    }

    public void setPermsBITS(int permsBITS) {
        this.permsBITS = permsBITS;
    }

    public int getStatusBITS() {
        return statusBITS;
    }

    public void setStatusBITS(int statusBITS) {
        this.statusBITS = statusBITS;
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

    public Date getSyncedDate() {
        return syncedDate;
    }

    public void setSyncedDate(Date syncedDate) {
        this.syncedDate = syncedDate;
    }

    public cLogFrameModel getLogFrameModel() {
        return logFrameModel;
    }

    public void setLogFrameModel(cLogFrameModel logFrameModel) {
        this.logFrameModel = logFrameModel;
    }

    public Set<cQuestionModel> getQuestionModelSet() {
        return questionModelSet;
    }

    public void setQuestionModelSet(Set<cQuestionModel> questionModelSet) {
        this.questionModelSet = questionModelSet;
    }

    public Set<cRaidModel> getRaidModelSet() {
        return raidModelSet;
    }

    public void setRaidModelSet(Set<cRaidModel> raidModelSet) {
        this.raidModelSet = raidModelSet;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(componentID);
        parcel.writeLong(parentID);
        parcel.writeLong(serverID);
        parcel.writeLong(ownerID);
        parcel.writeLong(orgID);
        parcel.writeInt(groupBITS);
        parcel.writeInt(permsBITS);
        parcel.writeInt(statusBITS);
        parcel.writeString(name);
        parcel.writeString(description);
    }
}
