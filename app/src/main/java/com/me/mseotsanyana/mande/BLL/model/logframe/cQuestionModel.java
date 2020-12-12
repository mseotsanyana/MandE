package com.me.mseotsanyana.mande.BLL.model.logframe;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Pair;

import com.me.mseotsanyana.mande.BLL.model.monitor.cIndicatorModel;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class cQuestionModel implements Parcelable {
    private long questionID;
    //private long logFrameID;
    //private long questionTypeID;
    //private long questionGroupID;
    private long serverID;
    private long ownerID;
    private long orgID;
    private int groupBITS;
    private int permsBITS;
    private int statusBITS;
    private int label;
    private String question;
    private long defaultChart;
    private Date startDate;
    private Date endDate;
    private Date createdDate;
    private Date modifiedDate;
    private Date syncedDate;

    /* logframe */
    private cLogFrameModel logFrameModel;
    /* questions on the same page */
    private cQuestionGroupingModel questionGroupingModel;
    /* link to primitive type, array type and matrix type */
    private cQuestionTypeModel questionTypeModel;

    /* set of monitoring indicators for the question */
    private Set<cIndicatorModel> indicatorModelSet;

    /* maps containing question types and response choices */
    //private Set<cArrayChoiceModel> arrayChoiceModelSet;
    //private Set<Pair<cRowChoiceModel, cColChoiceModel>> matrixChoiceModelSet;

    /* frequency table for array response questions */
    private Set<Pair<String, Long>> arrayResponseFreqTable;

    public cQuestionModel(cQuestionModel questionModel){
        this.questionID = questionModel.getQuestionID();
        this.serverID = questionModel.getQuestionID();
        this.ownerID = questionModel.getQuestionID();
        this.orgID = questionModel.getQuestionID();
        this.groupBITS = questionModel.getGroupBITS();
        this.permsBITS = questionModel.getPermsBITS();
        this.statusBITS = questionModel.getStatusBITS();
        this.label = questionModel.getLabel();
        this.question = questionModel.getQuestion();
        this.defaultChart = questionModel.getDefaultChart();
        this.startDate = questionModel.getStartDate();
        this.endDate = questionModel.getEndDate();
        this.createdDate = questionModel.getCreatedDate();
        this.modifiedDate = questionModel.getModifiedDate();
        this.syncedDate = questionModel.getSyncedDate();

    }
    public cQuestionModel(){
        logFrameModel = new cLogFrameModel();
        questionGroupingModel = new cQuestionGroupingModel();
        questionTypeModel = new cQuestionTypeModel();

        arrayResponseFreqTable = new HashSet<>();
        indicatorModelSet = new HashSet<>();
    }

    public long getQuestionID() {
        return questionID;
    }

    public void setQuestionID(long questionID) {
        this.questionID = questionID;
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

    public int getLabel() {
        return label;
    }

    public void setLabel(int label) {
        this.label = label;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public long getDefaultChart() {
        return defaultChart;
    }

    public void setDefaultChart(long defaultChart) {
        this.defaultChart = defaultChart;
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

    public cQuestionGroupingModel getQuestionGroupingModel() {
        return questionGroupingModel;
    }

    public void setQuestionGroupingModel(cQuestionGroupingModel questionGroupingModel) {
        this.questionGroupingModel = questionGroupingModel;
    }

    public cQuestionTypeModel getQuestionTypeModel() {
        return questionTypeModel;
    }

    public void setQuestionTypeModel(cQuestionTypeModel questionTypeModel) {
        this.questionTypeModel = questionTypeModel;
    }

    public Set<Pair<String, Long>> getArrayResponseFreqTable() {
        return arrayResponseFreqTable;
    }

    public void setArrayResponseFreqTable(Set<Pair<String, Long>> arrayResponseFreqTable) {
        this.arrayResponseFreqTable = arrayResponseFreqTable;
    }

    public Set<cIndicatorModel> getIndicatorModelSet() {
        return indicatorModelSet;
    }

    public void setIndicatorModelSet(Set<cIndicatorModel> indicatorModelSet) {
        this.indicatorModelSet = indicatorModelSet;
    }

    protected cQuestionModel(Parcel in) {
        questionID = in.readInt();
        serverID = in.readInt();
        ownerID = in.readInt();
        orgID = in.readInt();
        groupBITS = in.readInt();
        permsBITS = in.readInt();
        statusBITS = in.readInt();
        label = in.readInt();
        question = in.readString();
    }

    public static final Creator<cQuestionModel> CREATOR = new Creator<cQuestionModel>() {
        @Override
        public cQuestionModel createFromParcel(Parcel in) {
            return new cQuestionModel(in);
        }

        @Override
        public cQuestionModel[] newArray(int size) {
            return new cQuestionModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(questionID);
        parcel.writeLong(serverID);
        parcel.writeLong(ownerID);
        parcel.writeLong(orgID);
        parcel.writeInt(groupBITS);
        parcel.writeInt(permsBITS);
        parcel.writeInt(statusBITS);
        parcel.writeInt(label);
        parcel.writeString(question);
    }
}
