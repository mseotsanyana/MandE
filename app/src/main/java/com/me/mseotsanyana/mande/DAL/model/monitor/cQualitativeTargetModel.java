package com.me.mseotsanyana.mande.DAL.model.monitor;

public class cQualitativeTargetModel extends cTargetModel{
    cCriteriaScoreModel baselineScore;
    cCriteriaScoreModel targetScore;

    public cCriteriaScoreModel getBaselineScore() {
        return baselineScore;
    }

    public void setBaselineScore(cCriteriaScoreModel baselineScore) {
        this.baselineScore = baselineScore;
    }

    public cCriteriaScoreModel getTargetScore() {
        return targetScore;
    }

    public void setTargetScore(cCriteriaScoreModel targetScore) {
        this.targetScore = targetScore;
    }
}
