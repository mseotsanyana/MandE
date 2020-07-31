package com.me.mseotsanyana.mande.DAL.model.logframe;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Pair;

import com.me.mseotsanyana.mande.DAL.model.evaluator.cArrayChoiceModel;
import com.me.mseotsanyana.mande.DAL.model.evaluator.cArrayResponseModel;
import com.me.mseotsanyana.mande.DAL.model.evaluator.cColOptionModel;
import com.me.mseotsanyana.mande.DAL.model.evaluator.cDateResponseModel;
import com.me.mseotsanyana.mande.DAL.model.evaluator.cMatrixChoiceModel;
import com.me.mseotsanyana.mande.DAL.model.evaluator.cMatrixResponseModel;
import com.me.mseotsanyana.mande.DAL.model.evaluator.cNumericResponseModel;
import com.me.mseotsanyana.mande.DAL.model.evaluator.cRowOptionModel;
import com.me.mseotsanyana.mande.DAL.model.evaluator.cTextResponseModel;
import com.me.mseotsanyana.mande.DAL.model.monitor.cIndicatorModel;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class cQuestionModel implements Parcelable {
    private long questionID;
    private long logFrameID;
    private long questionTypeID;
    private long questionGroupID;
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

    /* logframe */
    private cLogFrameModel logFrameModel;
    /* questions on the same page */
    private cQuestionGroupingModel questionGroupingModel;
    /* link to primitive type, array type and matrix type */
    private cQuestionTypeModel questionTypeModel;

    /* set of evaluation responses for the question */
    private Set<cDateResponseModel> dateResponseModelSet;
    private Set<cNumericResponseModel> numericResponseModelSet;
    private Set<cTextResponseModel> textResponseModelSet;
    private Set<cArrayResponseModel> arrayResponseModelSet;
    private Set<cMatrixResponseModel> matrixResponseModelSet;

    /* set of monitoring indicators for the question */
    private Set<cIndicatorModel> indicatorModelSet;

    /* maps containing question types and response choices */
    private Set<cArrayChoiceModel> arrayChoiceModelSet;
    private Set<Pair<cRowOptionModel, cColOptionModel>> matrixChoiceModelSet;

    //private Map<cArrayTypeModel, Set<cArrayChoiceModel>> arrayChoiceMap;
    //private Map<cMatrixTypeModel, Set<cMatrixChoiceModel>> matrixChoiceMap;

    public cQuestionModel(){
        logFrameModel = new cLogFrameModel();
        questionGroupingModel = new cQuestionGroupingModel();
        questionTypeModel = new cQuestionTypeModel();

        arrayChoiceModelSet = new HashSet<>();
        matrixChoiceModelSet = new HashSet<>();

        indicatorModelSet = new HashSet<>();
    }

    public long getQuestionID() {
        return questionID;
    }

    public void setQuestionID(long questionID) {
        this.questionID = questionID;
    }

    public long getLogFrameID() {
        return logFrameID;
    }

    public void setLogFrameID(long logFrameID) {
        this.logFrameID = logFrameID;
    }

    public long getQuestionTypeID() {
        return questionTypeID;
    }

    public void setQuestionTypeID(long questionTypeID) {
        this.questionTypeID = questionTypeID;
    }

    public long getQuestionGroupID() {
        return questionGroupID;
    }

    public void setQuestionGroupID(long questionGroupID) {
        this.questionGroupID = questionGroupID;
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

    public Set<cDateResponseModel> getDateResponseModelSet() {
        return dateResponseModelSet;
    }

    public void setDateResponseModelSet(Set<cDateResponseModel> dateResponseModelSet) {
        this.dateResponseModelSet = dateResponseModelSet;
    }

    public Set<cNumericResponseModel> getNumericResponseModelSet() {
        return numericResponseModelSet;
    }

    public void setNumericResponseModelSet(Set<cNumericResponseModel> numericResponseModelSet) {
        this.numericResponseModelSet = numericResponseModelSet;
    }

    public Set<cTextResponseModel> getTextResponseModelSet() {
        return textResponseModelSet;
    }

    public void setTextResponseModelSet(Set<cTextResponseModel> textResponseModelSet) {
        this.textResponseModelSet = textResponseModelSet;
    }

    public Set<cArrayResponseModel> getArrayResponseModelSet() {
        return arrayResponseModelSet;
    }

    public void setArrayResponseModelSet(Set<cArrayResponseModel> arrayResponseModelSet) {
        this.arrayResponseModelSet = arrayResponseModelSet;
    }

    public Set<cMatrixResponseModel> getMatrixResponseModelSet() {
        return matrixResponseModelSet;
    }

    public void setMatrixResponseModelSet(Set<cMatrixResponseModel> matrixResponseModelSet) {
        this.matrixResponseModelSet = matrixResponseModelSet;
    }

    public Set<cIndicatorModel> getIndicatorModelSet() {
        return indicatorModelSet;
    }

    public void setIndicatorModelSet(Set<cIndicatorModel> indicatorModelSet) {
        this.indicatorModelSet = indicatorModelSet;
    }

    public Set<cArrayChoiceModel> getArrayChoiceModelSet() {
        return arrayChoiceModelSet;
    }

    public void setArrayChoiceModelSet(Set<cArrayChoiceModel> arrayChoiceModelSet) {
        this.arrayChoiceModelSet = arrayChoiceModelSet;
    }

    public Set<Pair<cRowOptionModel, cColOptionModel>> getMatrixChoiceModelSet() {
        return matrixChoiceModelSet;
    }

    public void setMatrixChoiceModelSet(Set<Pair<cRowOptionModel, cColOptionModel>> matrixChoiceModelSet) {
        this.matrixChoiceModelSet = matrixChoiceModelSet;
    }

    protected cQuestionModel(Parcel in) {
        questionID = in.readInt();
        logFrameID = in.readInt();
        questionTypeID = in.readInt();
        questionGroupID = in.readInt();
        serverID = in.readInt();
        ownerID = in.readInt();
        orgID = in.readInt();
        groupBITS = in.readInt();
        permsBITS = in.readInt();
        statusBITS = in.readInt();
        name = in.readString();
        description = in.readString();
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
        parcel.writeLong(logFrameID);
        parcel.writeLong(questionTypeID);
        parcel.writeLong(questionGroupID);
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
