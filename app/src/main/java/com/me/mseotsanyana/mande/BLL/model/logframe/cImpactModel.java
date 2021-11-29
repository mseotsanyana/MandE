package com.me.mseotsanyana.mande.BLL.model.logframe;

import com.google.firebase.firestore.Exclude;

import java.util.ArrayList;
import java.util.List;

public class cImpactModel extends cComponentModel {
    private String parentServerID;
    private List<cImpactModel> childModels;

    private List<cOutcomeModel> outcomeModels;

    public cImpactModel() {
        /* mappings */
        parentServerID = null;
        childModels = new ArrayList<>();
        outcomeModels = new ArrayList<>();
    }

    public String getParentServerID() {
        return parentServerID;
    }

    public void setParentServerID(String parentServerID) {
        this.parentServerID = parentServerID;
    }

    @Exclude
    public List<cOutcomeModel> getOutcomeModels() {
        return outcomeModels;
    }

    public void setOutcomeModels(List<cOutcomeModel> outcomeModels) {
        this.outcomeModels = outcomeModels;
    }

    @Exclude
    public List<cImpactModel> getChildModels() {
        return childModels;
    }

    public void setChildModels(List<cImpactModel> childModels) {
        this.childModels = childModels;
    }
}
