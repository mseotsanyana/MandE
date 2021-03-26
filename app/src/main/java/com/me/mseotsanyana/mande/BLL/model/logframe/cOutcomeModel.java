package com.me.mseotsanyana.mande.BLL.model.logframe;

import android.util.Pair;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class cOutcomeModel extends cComponentModel {
    /*** incoming mappings ***/
    private cImpactModel impactModel;
    private Set<cOutcomeModel> childOutcomeModelSet; //children
    /*** outgoing mappings ***/
    private Set<cOutputModel> outputModelSet;
    /* set of impact in a child logframe for the outcome of the parent logframe */
    private Map<Pair<Long, Long>, Set<cImpactModel>> childImpactModelMap;

    public cOutcomeModel(){
        impactModel = new cImpactModel();
        childOutcomeModelSet = new HashSet<>();
        outputModelSet = new HashSet<>();
        childImpactModelMap =new HashMap<>();
    }

    public cImpactModel getImpactModel() {
        return impactModel;
    }

    public void setImpactModel(cImpactModel impactModel) {
        this.impactModel = impactModel;
    }

    public Set<cOutcomeModel> getChildOutcomeModelSet() {
        return childOutcomeModelSet;
    }

    public void setChildOutcomeModelSet(Set<cOutcomeModel> childOutcomeModelSet) {
        this.childOutcomeModelSet = childOutcomeModelSet;
    }

    public Set<cOutputModel> getOutputModelSet() {
        return outputModelSet;
    }

    public void setOutputModelSet(Set<cOutputModel> outputModelSet) {
        this.outputModelSet = outputModelSet;
    }

    public Map<Pair<Long, Long>, Set<cImpactModel>> getChildImpactModelMap() {
        return childImpactModelMap;
    }

    public void setChildImpactModelMap(Map<Pair<Long, Long>, Set<cImpactModel>> childImpactModelMap) {
        this.childImpactModelMap = childImpactModelMap;
    }
}
