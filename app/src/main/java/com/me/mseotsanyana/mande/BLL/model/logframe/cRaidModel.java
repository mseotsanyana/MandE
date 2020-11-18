package com.me.mseotsanyana.mande.BLL.model.logframe;

import com.me.mseotsanyana.mande.BLL.model.common.cFrequencyModel;
import com.me.mseotsanyana.mande.BLL.model.raid.cRAIDImpactModel;
import com.me.mseotsanyana.mande.BLL.model.raid.cRAIDLikelihoodModel;
import com.me.mseotsanyana.mande.BLL.model.wpb.cHumanSetModel;

import java.util.Date;

public class cRaidModel {
    private long raidID;
    private long serverID;
    private long ownerID;
    private long orgID;
    private int groupBITS;
    private int permsBITS;
    private int statusBITS;
    private String name;
    private String description;
    private String status;
    private Date startDate;
    private Date endDate;
    private Date createdDate;
    private Date modifiedDate;
    private Date syncedDate;

    private cLogFrameModel logFrameModel;
    private cHumanSetModel originatorModel;
    private cHumanSetModel ownerModel;
    private cFrequencyModel frequencyModel;
    private cRAIDLikelihoodModel raidLikelihoodModel;
    private cRAIDImpactModel raidImpactModel;

    public long getRaidID() {
        return raidID;
    }

    public void setRaidID(long raidID) {
        this.raidID = raidID;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public cHumanSetModel getOriginatorModel() {
        return originatorModel;
    }

    public void setOriginatorModel(cHumanSetModel originatorModel) {
        this.originatorModel = originatorModel;
    }

    public cHumanSetModel getOwnerModel() {
        return ownerModel;
    }

    public void setOwnerModel(cHumanSetModel ownerModel) {
        this.ownerModel = ownerModel;
    }

    public cFrequencyModel getFrequencyModel() {
        return frequencyModel;
    }

    public void setFrequencyModel(cFrequencyModel frequencyModel) {
        this.frequencyModel = frequencyModel;
    }

    public cRAIDLikelihoodModel getRaidLikelihoodModel() {
        return raidLikelihoodModel;
    }

    public void setRaidLikelihoodModel(cRAIDLikelihoodModel raidLikelihoodModel) {
        this.raidLikelihoodModel = raidLikelihoodModel;
    }

    public cRAIDImpactModel getRaidImpactModel() {
        return raidImpactModel;
    }

    public void setRaidImpactModel(cRAIDImpactModel raidImpactModel) {
        this.raidImpactModel = raidImpactModel;
    }
}