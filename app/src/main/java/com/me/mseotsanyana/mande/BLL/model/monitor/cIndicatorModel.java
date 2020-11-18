package com.me.mseotsanyana.mande.BLL.model.monitor;

import com.me.mseotsanyana.mande.BLL.model.common.cChartModel;
import com.me.mseotsanyana.mande.BLL.model.common.cFrequencyModel;
import com.me.mseotsanyana.mande.BLL.model.logframe.cLogFrameModel;

import java.util.Date;
import java.util.Set;

public class cIndicatorModel {
    private long indicatorID;
    private int serverID;
    private int ownerID;
    private int orgID;
    private int groupBITS;
    private int permsBITS;
    private int statusBITS;
    private String name;
    private String description;
    private String question;
    private Date startDate;
    private Date endDate;
    private Date createdDate;
    private Date modifiedDate;
    private Date syncedDate;

    private cLogFrameModel logFrameModel;
    private cTargetModel targetModel;
    private cIndicatorTypeModel indicatorTypeModel;
    private cFrequencyModel frequencyModel;
    private cMethodModel methodModel;
    private cChartModel chartModel;
    private cDataCollectorModel dataCollectorModel;

    private Set<cMoVModel> movModelSet;
    private Set<cDataSourceModel> dataSourceModelSet;

    public long getIndicatorID() {
        return indicatorID;
    }

    public void setIndicatorID(long indicatorID) {
        this.indicatorID = indicatorID;
    }

    public int getServerID() {
        return serverID;
    }

    public void setServerID(int serverID) {
        this.serverID = serverID;
    }

    public int getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(int ownerID) {
        this.ownerID = ownerID;
    }

    public int getOrgID() {
        return orgID;
    }

    public void setOrgID(int orgID) {
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

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
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

    public cTargetModel getTargetModel() {
        return targetModel;
    }

    public void setTargetModel(cTargetModel targetModel) {
        this.targetModel = targetModel;
    }

    public cIndicatorTypeModel getIndicatorTypeModel() {
        return indicatorTypeModel;
    }

    public void setIndicatorTypeModel(cIndicatorTypeModel indicatorTypeModel) {
        this.indicatorTypeModel = indicatorTypeModel;
    }

    public cFrequencyModel getFrequencyModel() {
        return frequencyModel;
    }

    public void setFrequencyModel(cFrequencyModel frequencyModel) {
        this.frequencyModel = frequencyModel;
    }

    public cMethodModel getMethodModel() {
        return methodModel;
    }

    public void setMethodModel(cMethodModel methodModel) {
        this.methodModel = methodModel;
    }

    public cChartModel getChartModel() {
        return chartModel;
    }

    public void setChartModel(cChartModel chartModel) {
        this.chartModel = chartModel;
    }

    public cDataCollectorModel getDataCollectorModel() {
        return dataCollectorModel;
    }

    public void setDataCollectorModel(cDataCollectorModel dataCollectorModel) {
        this.dataCollectorModel = dataCollectorModel;
    }

    public Set<cMoVModel> getMovModelSet() {
        return movModelSet;
    }

    public void setMovModelSet(Set<cMoVModel> movModelSet) {
        this.movModelSet = movModelSet;
    }

    public Set<cDataSourceModel> getDataSourceModelSet() {
        return dataSourceModelSet;
    }

    public void setDataSourceModelSet(Set<cDataSourceModel> dataSourceModelSet) {
        this.dataSourceModelSet = dataSourceModelSet;
    }
}
