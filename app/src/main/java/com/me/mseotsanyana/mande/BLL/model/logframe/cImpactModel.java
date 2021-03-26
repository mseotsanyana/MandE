package com.me.mseotsanyana.mande.BLL.model.logframe;

import java.util.HashSet;
import java.util.Set;

public class cImpactModel extends cComponentModel {
    private Set<cImpactModel> childImpactModelSet; //children
    private Set<cOutcomeModel> outcomeModelSet;

    public cImpactModel(){
        /* mappings */
        childImpactModelSet = new HashSet<>();
        outcomeModelSet = new HashSet<>();
    }

    public Set<cImpactModel> getChildImpactModelSet() {
        return childImpactModelSet;
    }

    public void setChildImpactModelSet(Set<cImpactModel> childImpactModelSet) {
        this.childImpactModelSet = childImpactModelSet;
    }

    public Set<cOutcomeModel> getOutcomeModelSet() {
        return outcomeModelSet;
    }

    public void setOutcomeModelSet(Set<cOutcomeModel> outcomeModelSet) {
        this.outcomeModelSet = outcomeModelSet;
    }
}
