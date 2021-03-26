package com.me.mseotsanyana.mande.BLL.model.logframe;

import com.me.mseotsanyana.mande.BLL.model.wpb.cActivityTaskModel;

import java.util.HashSet;
import java.util.Set;

public class cActivityModel extends cComponentModel {
    /*** incoming mappings ***/
    private cOutputModel outputModel;
    private Set<cActivityModel> childActivityModelSet;
    /*** outgoing mappings ***/
    private Set<cInputModel> inputModelSet;
    private Set<cActivityTaskModel> taskModelSet;
    private Set<cPrecedingActivityModel> precedingActivityModelSet;
    private Set<cActivityAssignmentModel> activityAssignmentModelSet;
    /* set of output in a sub-logframe for the parent activity */
    private Set<cOutputModel> childOutputModelSet;

    public cActivityModel(){
        /* incoming mappings */
        outputModel = new cOutputModel();
        childActivityModelSet = new HashSet<>();
        /* outgoing mappings */
        inputModelSet = new HashSet<>();
        taskModelSet = new HashSet<>();
        precedingActivityModelSet = new HashSet<>();
        activityAssignmentModelSet = new HashSet<>();
        childOutputModelSet = new HashSet<>();
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

    public Set<cPrecedingActivityModel> getPrecedingActivityModelSet() {
        return precedingActivityModelSet;
    }

    public void setPrecedingActivityModelSet(Set<cPrecedingActivityModel> precedingActivityModelSet) {
        this.precedingActivityModelSet = precedingActivityModelSet;
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

