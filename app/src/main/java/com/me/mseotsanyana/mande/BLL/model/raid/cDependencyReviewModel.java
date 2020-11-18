package com.me.mseotsanyana.mande.BLL.model.raid;

public class cDependencyReviewModel extends cMilestoneReviewModel {
    private double dependencyRating;
    private cDependencyModel dependencyModel;

    public double getDependencyRating() {
        return dependencyRating;
    }

    public void setDependencyRating(double dependencyRating) {
        this.dependencyRating = dependencyRating;
    }

    public cDependencyModel getDependencyModel() {
        return dependencyModel;
    }

    public void setDependencyModel(cDependencyModel dependencyModel) {
        this.dependencyModel = dependencyModel;
    }
}
