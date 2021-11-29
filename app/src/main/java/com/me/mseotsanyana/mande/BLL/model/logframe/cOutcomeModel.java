package com.me.mseotsanyana.mande.BLL.model.logframe;

import com.google.firebase.firestore.Exclude;

import java.util.ArrayList;
import java.util.List;

public class cOutcomeModel extends cComponentModel {
    private String parentServerID;
    private List<cOutcomeModel> childModels;

    private String impactServerID;

    private List<cOutputModel> outputModels;

    // sub-logframe impacts
    private List<cImpactModel> subimpactModels;

    public cOutcomeModel(){
        parentServerID = null;
        impactServerID = null;
        childModels = new ArrayList<>();
        outputModels = new ArrayList<>();
        subimpactModels = new ArrayList<>();
    }

    public String getParentServerID() {
        return parentServerID;
    }

    public void setParentServerID(String parentServerID) {
        this.parentServerID = parentServerID;
    }

    public String getImpactServerID() {
        return impactServerID;
    }

    public void setImpactServerID(String impactServerID) {
        this.impactServerID = impactServerID;
    }

    @Exclude
    public List<cOutcomeModel> getChildModels() {
        return childModels;
    }

    public void setChildModels(List<cOutcomeModel> childModels) {
        this.childModels = childModels;
    }

    @Exclude
    public List<cOutputModel> getOutputModels() {
        return outputModels;
    }

    public void setOutputModels(List<cOutputModel> outputModels) {
        this.outputModels = outputModels;
    }

    @Exclude
    public List<cImpactModel> getSubimpactModels() {
        return subimpactModels;
    }

    public void setSubimpactModels(List<cImpactModel> subimpactModels) {
        this.subimpactModels = subimpactModels;
    }
}
