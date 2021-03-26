package com.me.mseotsanyana.mande.BLL.model.session;

import java.util.Date;

public class cFeatureModel {
    private int featureServerID;

    private String name;
    private String description;

    private Date createdDate;
    private Date modifiedDate;

    public cFeatureModel(){
    }

    public int getFeatureServerID() {
        return featureServerID;
    }

    public void setFeatureServerID(int featureServerID) {
        this.featureServerID = featureServerID;
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
}
