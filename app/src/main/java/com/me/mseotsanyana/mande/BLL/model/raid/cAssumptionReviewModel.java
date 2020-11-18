package com.me.mseotsanyana.mande.BLL.model.raid;

public class cAssumptionReviewModel extends cMilestoneReviewModel {
    private double assumptionRating;
    private cAssumptionModel assumptionModel;

    public double getAssumptionRating() {
        return assumptionRating;
    }

    public void setAssumptionRating(double assumptionRating) {
        this.assumptionRating = assumptionRating;
    }

    public cAssumptionModel getAssumptionModel() {
        return assumptionModel;
    }

    public void setAssumptionModel(cAssumptionModel assumptionModel) {
        this.assumptionModel = assumptionModel;
    }
}
