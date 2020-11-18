package com.me.mseotsanyana.mande.BLL.model.raid;

public class cRiskActionModel extends cActionModel {
    private cRiskReviewModel riskReviewModel;
    private cRiskActionTypeModel riskActionTypeModel;

    public cRiskReviewModel getRiskReviewModel() {
        return riskReviewModel;
    }

    public void setRiskReviewModel(cRiskReviewModel riskReviewModel) {
        this.riskReviewModel = riskReviewModel;
    }

    public cRiskActionTypeModel getRiskActionTypeModel() {
        return riskActionTypeModel;
    }

    public void setRiskActionTypeModel(cRiskActionTypeModel riskActionTypeModel) {
        this.riskActionTypeModel = riskActionTypeModel;
    }
}
