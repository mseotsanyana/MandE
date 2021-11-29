package com.me.mseotsanyana.mande.BLL.model.logframe;

import com.google.firebase.firestore.Exclude;

import java.util.ArrayList;
import java.util.List;

public class cOutputModel extends cComponentModel {
    private String parentServerID;
    private List<cOutputModel> childModels;

    private String outcomeServerID;

    private List<cActivityModel> activityModels;
    // sub-logframe outcomes
    private List<cOutcomeModel> suboutcomeModels;

    public cOutputModel(){
        parentServerID = null;
        outcomeServerID = null;
        childModels = new ArrayList<>();
        activityModels = new ArrayList<>();
        suboutcomeModels = new ArrayList<>();
    }

    public String getParentServerID() {
        return parentServerID;
    }

    public void setParentServerID(String parentServerID) {
        this.parentServerID = parentServerID;
    }

    public String getOutcomeServerID() {
        return outcomeServerID;
    }

    public void setOutcomeServerID(String outcomeServerID) {
        this.outcomeServerID = outcomeServerID;
    }

    @Exclude
    public List<cOutputModel> getChildModels() {
        return childModels;
    }

    public void setChildModels(List<cOutputModel> childModels) {
        this.childModels = childModels;
    }

    @Exclude
    public List<cActivityModel> getActivityModels() {
        return activityModels;
    }

    public void setActivityModels(List<cActivityModel> activityModels) {
        this.activityModels = activityModels;
    }

    @Exclude
    public List<cOutcomeModel> getSuboutcomeModels() {
        return suboutcomeModels;
    }

    public void setSuboutcomeModels(List<cOutcomeModel> suboutcomeModels) {
        this.suboutcomeModels = suboutcomeModels;
    }
}

