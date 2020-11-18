package com.me.mseotsanyana.mande.BLL.model.raid;

public class cRiskReviewModel extends cMilestoneReviewModel {
    private double residualRisk;
    private cRiskModel riskModel;

    public cRiskReviewModel(){
        riskModel = new cRiskModel();
    }

    public double getResidualRisk() {
        return residualRisk;
    }

    public void setResidualRisk(double residualRisk) {
        this.residualRisk = residualRisk;
    }

    public cRiskModel getRiskModel() {
        return riskModel;
    }

    public void setRiskModel(cRiskModel riskModel) {
        this.riskModel = riskModel;
    }
}
