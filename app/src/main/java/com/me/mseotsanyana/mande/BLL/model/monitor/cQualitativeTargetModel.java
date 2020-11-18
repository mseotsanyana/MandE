package com.me.mseotsanyana.mande.BLL.model.monitor;

public class cQualitativeTargetModel extends cTargetModel{
    cCriteriaScoreModel criteriaBaselineScoreModel;
    cCriteriaScoreModel criteriaTargetScoreModel;

    public cCriteriaScoreModel getCriteriaBaselineScoreModel() {
        return criteriaBaselineScoreModel;
    }

    public void setCriteriaBaselineScoreModel(cCriteriaScoreModel criteriaBaselineScoreModel) {
        this.criteriaBaselineScoreModel = criteriaBaselineScoreModel;
    }

    public cCriteriaScoreModel getCriteriaTargetScoreModel() {
        return criteriaTargetScoreModel;
    }

    public void setCriteriaTargetScoreModel(cCriteriaScoreModel criteriaTargetScoreModel) {
        this.criteriaTargetScoreModel = criteriaTargetScoreModel;
    }
}
