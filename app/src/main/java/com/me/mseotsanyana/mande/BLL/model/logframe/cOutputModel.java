package com.me.mseotsanyana.mande.BLL.model.logframe;

import java.util.HashSet;
import java.util.Set;

public class cOutputModel extends cComponentModel {
    /*** incoming mappings ***/
    private cOutcomeModel outcomeModel;
    private Set<cOutputModel> childOutputModelSet;
    /*** outgoing mappings ***/
    private Set<cActivityModel> activityModelSet;
    /*** set of outcome in a child logframe for the output of the parent logframe ***/
    private Set<cOutcomeModel> childOutcomeModelSet;

    public cOutputModel(){
        outcomeModel = new cOutcomeModel();
        childOutputModelSet = new HashSet<>();
        activityModelSet = new HashSet<>();
        childOutcomeModelSet = new HashSet<>();
    }

    public cOutcomeModel getOutcomeModel() {
        return outcomeModel;
    }

    public void setOutcomeModel(cOutcomeModel outcomeModel) {
        this.outcomeModel = outcomeModel;
    }

    public Set<cOutputModel> getChildOutputModelSet() {
        return childOutputModelSet;
    }

    public void setChildOutputModelSet(Set<cOutputModel> childOutputModelSet) {
        this.childOutputModelSet = childOutputModelSet;
    }

    public Set<cActivityModel> getActivityModelSet() {
        return activityModelSet;
    }

    public void setActivityModelSet(Set<cActivityModel> activityModelSet) {
        this.activityModelSet = activityModelSet;
    }

    public Set<cOutcomeModel> getChildOutcomeModelSet() {
        return childOutcomeModelSet;
    }

    public void setChildOutcomeModelSet(Set<cOutcomeModel> childOutcomeModelSet) {
        this.childOutcomeModelSet = childOutcomeModelSet;
    }
}

