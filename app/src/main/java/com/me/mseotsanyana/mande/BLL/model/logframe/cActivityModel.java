package com.me.mseotsanyana.mande.BLL.model.logframe;

import com.google.firebase.firestore.Exclude;
import com.me.mseotsanyana.mande.BLL.model.session.cUserModel;
import com.me.mseotsanyana.mande.BLL.model.wpb.cTaskModel;

import java.util.ArrayList;
import java.util.List;

public class cActivityModel extends cComponentModel {
    private String parentServerID;
    private List<cActivityModel> childModels;

    private String outputServerID;

    private List<cActivityModel> precedingActivities;
    private List<cUserModel> activityAssignments;

    private List<cInputModel> inputModels;
    private List<cTaskModel> taskModels;

    /* set of output in a sub-logframe for the parent activity */
    private List<cOutputModel> suboutputModels;

    public cActivityModel(){
        parentServerID = null;
        outputServerID = null;
        childModels = new ArrayList<>();
        activityAssignments = new ArrayList<>();
        precedingActivities = new ArrayList<>();
        inputModels = new ArrayList<>();
        taskModels = new ArrayList<>();
        suboutputModels = new ArrayList<>();
    }

    public String getParentServerID() {
        return parentServerID;
    }

    public void setParentServerID(String parentServerID) {
        this.parentServerID = parentServerID;
    }

    public String getOutputServerID() {
        return outputServerID;
    }

    public void setOutputServerID(String outputServerID) {
        this.outputServerID = outputServerID;
    }

    @Exclude
    public List<cActivityModel> getChildModels() {
        return childModels;
    }

    public void setChildModels(List<cActivityModel> childModels) {
        this.childModels = childModels;
    }

    @Exclude
    public List<cActivityModel> getPrecedingActivities() {
        return precedingActivities;
    }

    public void setPrecedingActivities(List<cActivityModel> precedingActivities) {
        this.precedingActivities = precedingActivities;
    }

    @Exclude
    public List<cUserModel> getActivityAssignments() {
        return activityAssignments;
    }

    public void setActivityAssignments(List<cUserModel> activityAssignments) {
        this.activityAssignments = activityAssignments;
    }

    @Exclude
    public List<cInputModel> getInputModels() {
        return inputModels;
    }

    public void setInputModels(List<cInputModel> inputModels) {
        this.inputModels = inputModels;
    }

    @Exclude
    public List<cTaskModel> getTaskModels() {
        return taskModels;
    }

    public void setTaskModels(List<cTaskModel> taskModels) {
        this.taskModels = taskModels;
    }

    @Exclude
    public List<cOutputModel> getSuboutputModels() {
        return suboutputModels;
    }

    public void setSuboutputModels(List<cOutputModel> suboutputModels) {
        this.suboutputModels = suboutputModels;
    }
}

