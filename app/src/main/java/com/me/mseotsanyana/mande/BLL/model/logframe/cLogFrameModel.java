package com.me.mseotsanyana.mande.BLL.model.logframe;

import com.me.mseotsanyana.mande.BLL.model.evaluator.cEvaluationModel;
import com.me.mseotsanyana.mande.BLL.model.raid.cRAIDLOGModel;
import com.me.mseotsanyana.mande.BLL.model.session.cCrowdFundModel;
import com.me.mseotsanyana.mande.BLL.model.session.cOrganizationModel;
import com.me.mseotsanyana.mande.BLL.model.monitor.cIndicatorModel;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class cLogFrameModel {
    private long logFrameID;
    private long logFrameParentID;
    private long organizationID;
    private long serverID;
    private long orgID;
    private long ownerID;
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

    private cOrganizationModel organizationModel;

    private Set<cLogFrameModel> logFrameModelSet;
    private Set<cComponentModel> componentModelSet;
    private Set<cQuestionModel> questionModelSet;
    private Set<cRaidModel> raidModelSet;
    private Set<cRAIDLOGModel> raidlogModelSet;
    private Set<cEvaluationModel> evaluationModelSet;
    private Set<cIndicatorModel> indicatorModelSet;
    private Set<cCrowdFundModel> crowdFundModelSet;

    public cLogFrameModel(){
        organizationModel = new cOrganizationModel();

        logFrameModelSet = new HashSet<>();
        componentModelSet = new HashSet<>();
        questionModelSet = new HashSet<>();
        raidModelSet = new HashSet<>();
        raidlogModelSet = new HashSet<>();
        evaluationModelSet = new HashSet<>();
        indicatorModelSet = new HashSet<>();
        crowdFundModelSet = new HashSet<>();
    }

    public long getLogFrameID() {
        return logFrameID;
    }

    public void setLogFrameID(long logFrameID) {
        this.logFrameID = logFrameID;
    }

    public long getLogFrameParentID() {
        return logFrameParentID;
    }

    public void setLogFrameParentID(long logFrameParentID) {
        this.logFrameParentID = logFrameParentID;
    }

    public long getOrganizationID() {
        return organizationID;
    }

    public void setOrganizationID(long organizationID) {
        this.organizationID = organizationID;
    }

    public long getServerID() {
        return serverID;
    }

    public void setServerID(long serverID) {
        this.serverID = serverID;
    }

    public long getOrgID() {
        return orgID;
    }

    public void setOrgID(long orgID) {
        this.orgID = orgID;
    }

    public long getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(long ownerID) {
        this.ownerID = ownerID;
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

    public cOrganizationModel getOrganizationModel() {
        return organizationModel;
    }

    public void setOrganizationModel(cOrganizationModel organizationModel) {
        this.organizationModel = organizationModel;
    }

    public Set<cLogFrameModel> getLogFrameModelSet() {
        return logFrameModelSet;
    }

    public void setLogFrameModelSet(Set<cLogFrameModel> logFrameModelSet) {
        this.logFrameModelSet = logFrameModelSet;
    }

    public Set<cComponentModel> getComponentModelSet() {
        return componentModelSet;
    }

    public void setComponentModelSet(Set<cComponentModel> componentModelSet) {
        this.componentModelSet = componentModelSet;
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

    public Set<cRAIDLOGModel> getRaidlogModelSet() {
        return raidlogModelSet;
    }

    public void setRaidlogModelSet(Set<cRAIDLOGModel> raidlogModelSet) {
        this.raidlogModelSet = raidlogModelSet;
    }

    public Set<cEvaluationModel> getEvaluationModelSet() {
        return evaluationModelSet;
    }

    public void setEvaluationModelSet(Set<cEvaluationModel> evaluationModelSet) {
        this.evaluationModelSet = evaluationModelSet;
    }

    public Set<cIndicatorModel> getIndicatorModelSet() {
        return indicatorModelSet;
    }

    public void setIndicatorModelSet(Set<cIndicatorModel> indicatorModelSet) {
        this.indicatorModelSet = indicatorModelSet;
    }

    public Set<cCrowdFundModel> getCrowdFundModelSet() {
        return crowdFundModelSet;
    }

    public void setCrowdFundModelSet(Set<cCrowdFundModel> crowdFundModelSet) {
        this.crowdFundModelSet = crowdFundModelSet;
    }
}
