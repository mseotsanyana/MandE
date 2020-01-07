package com.me.mseotsanyana.mande.PPMER.DAL;

import com.me.mseotsanyana.mande.PPMER.DAL.model.cImpactModel;

/**
 * Created by mseotsanyana on 2017/07/03.
 */

public class cTriangleModel {
    private cImpactModel goalModel;
    private cSpecificAimModel specificAimModel;
    private cObjectiveModel objectiveModel;

    public cImpactModel getGoalModel() {
        return goalModel;
    }

    public void setGoalModel(cImpactModel goalModel) {
        this.goalModel = goalModel;
    }

    public cSpecificAimModel getSpecificAimModel() {
        return specificAimModel;
    }

    public void setSpecificAimModel(cSpecificAimModel specificAimModel) {
        this.specificAimModel = specificAimModel;
    }

    public cObjectiveModel getObjectiveModel() {
        return objectiveModel;
    }

    public void setObjectiveModel(cObjectiveModel objectiveModel) {
        this.objectiveModel = objectiveModel;
    }
}
