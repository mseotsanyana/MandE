package com.me.mseotsanyana.mande.BLL.model.evaluator;

import com.me.mseotsanyana.mande.BLL.model.logframe.cLogFrameModel;
import com.me.mseotsanyana.mande.BLL.model.logframe.cQuestionModel;
import com.me.mseotsanyana.mande.BLL.model.session.cUserModel;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class cEvaluationModel {
    private long evaluationID;
    private long logFrameID;
    private long evaluationTypeID;
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

    private Object questionnaireObj;

    /* logframe which contains the evaluation */
    private cLogFrameModel logFrameModel;
    /* the type of evaluation */
    private cEvaluationTypeModel evaluationTypeModel;

    /* set of people required to respond to the evaluation */
    private Set<cUserModel> userModelSet;
    /* set of questions that also contains their responses */
    private Set<cQuestionModel> questionModelSet;

    public cEvaluationModel(){
        logFrameModel = new cLogFrameModel();
        evaluationTypeModel = new cEvaluationTypeModel();

        userModelSet = new HashSet<>();
        questionModelSet = new HashSet<>();
    }

    public long getEvaluationID() {
        return evaluationID;
    }

    public void setEvaluationID(long evaluationID) {
        this.evaluationID = evaluationID;
    }

    public long getLogFrameID() {
        return logFrameID;
    }

    public void setLogFrameID(long logFrameID) {
        this.logFrameID = logFrameID;
    }

    public long getEvaluationTypeID() {
        return evaluationTypeID;
    }

    public void setEvaluationTypeID(long evaluationTypeID) {
        this.evaluationTypeID = evaluationTypeID;
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

    public cEvaluationTypeModel getEvaluationTypeModel() {
        return evaluationTypeModel;
    }

    public void setEvaluationTypeModel(cEvaluationTypeModel evaluationTypeModel) {
        this.evaluationTypeModel = evaluationTypeModel;
    }

    public Set<cUserModel> getUserModelSet() {
        return userModelSet;
    }

    public void setUserModelSet(Set<cUserModel> userModelSet) {
        this.userModelSet = userModelSet;
    }

    public Set<cQuestionModel> getQuestionModelSet() {
        return questionModelSet;
    }

    public void setQuestionModelSet(Set<cQuestionModel> questionModelSet) {
        this.questionModelSet = questionModelSet;
    }

    public Object getQuestionnaireObj() {
        return questionnaireObj;
    }

    public void setQuestionnaireObj(Object questionnaireObj) {
        this.questionnaireObj = questionnaireObj;
    }
}
