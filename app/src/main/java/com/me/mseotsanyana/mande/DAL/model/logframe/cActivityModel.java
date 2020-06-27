package com.me.mseotsanyana.mande.DAL.model.logframe;

import com.me.mseotsanyana.mande.DAL.model.wpb.cActivityTaskModel;
import com.me.mseotsanyana.mande.DAL.model.wpb.cTaskModel;

import java.util.HashSet;
import java.util.Set;

public class cActivityModel extends cActivityPlanningModel{
    private int activityPlanningID;
    private int parentID;
    private int outputID;

    /*** incoming mappings ***/
    private cLogFrameModel logFrameModel;
    private cOutputModel outputModel;
    private Set<cActivityModel> childActivityModelSet;

    /*** outgoing mappings ***/
    private Set<cInputModel> inputModelSet;
    private Set<cActivityTaskModel> taskModelSet;
    private Set<cRaidModel> raidModelSet;
    private Set<cQuestionModel> questionModelSet;
    private Set<cPrecedingActivityModel> precedingActivityModelSet;
    private Set<cActivityAssignmentModel> activityAssignmentModelSet;

    /* set of output in a sub-logframe for the parent activity */
    private Set<cOutputModel> childOutputModelSet;

    public cActivityModel(){
        /* incoming mappings */
        logFrameModel = new cLogFrameModel();
        outputModel = new cOutputModel();
        childActivityModelSet = new HashSet<>();
        /* outgoing mappings */
        inputModelSet = new HashSet<>();
        taskModelSet = new HashSet<>();
        raidModelSet = new HashSet<>();
        questionModelSet = new HashSet<>();
        precedingActivityModelSet = new HashSet<>();
        activityAssignmentModelSet = new HashSet<>();
        childOutputModelSet = new HashSet<>();
    }

    @Override
    public int getActivityPlanningID() {
        return activityPlanningID;
    }

    @Override
    public void setActivityPlanningID(int activityPlanningID) {
        this.activityPlanningID = activityPlanningID;
    }

    public int getParentID() {
        return parentID;
    }

    public void setParentID(int parentID) {
        this.parentID = parentID;
    }

    public int getOutputID() {
        return outputID;
    }

    public void setOutputID(int outputID) {
        this.outputID = outputID;
    }

    public cLogFrameModel getLogFrameModel() {
        return logFrameModel;
    }

    public void setLogFrameModel(cLogFrameModel logFrameModel) {
        this.logFrameModel = logFrameModel;
    }

    public cOutputModel getOutputModel() {
        return outputModel;
    }

    public void setOutputModel(cOutputModel outputModel) {
        this.outputModel = outputModel;
    }

    public Set<cActivityModel> getChildActivityModelSet() {
        return childActivityModelSet;
    }

    public void setChildActivityModelSet(Set<cActivityModel> childActivityModelSet) {
        this.childActivityModelSet = childActivityModelSet;
    }

    public Set<cPrecedingActivityModel> getPrecedingActivityModelSet() {
        return precedingActivityModelSet;
    }

    public void setPrecedingActivityModelSet(Set<cPrecedingActivityModel> precedingActivityModelSet) {
        this.precedingActivityModelSet = precedingActivityModelSet;
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

    public Set<cInputModel> getInputModelSet() {
        return inputModelSet;
    }

    public void setInputModelSet(Set<cInputModel> inputModelSet) {
        this.inputModelSet = inputModelSet;
    }

    public Set<cActivityTaskModel> getTaskModelSet() {
        return taskModelSet;
    }

    public void setTaskModelSet(Set<cActivityTaskModel> taskModelSet) {
        this.taskModelSet = taskModelSet;
    }

    public Set<cActivityAssignmentModel> getActivityAssignmentModelSet() {
        return activityAssignmentModelSet;
    }

    public void setActivityAssignmentModelSet(Set<cActivityAssignmentModel> activityAssignmentModelSet) {
        this.activityAssignmentModelSet = activityAssignmentModelSet;
    }

    public Set<cOutputModel> getChildOutputModelSet() {
        return childOutputModelSet;
    }

    public void setChildOutputModelSet(Set<cOutputModel> childOutputModelSet) {
        this.childOutputModelSet = childOutputModelSet;
    }
}

