package com.me.mseotsanyana.mande.BLL.model.raid;

public class cIssueReviewModel extends cMilestoneReviewModel {
    private double priorityRating;
    private cIssueModel issueModel;

    public double getPriorityRating() {
        return priorityRating;
    }

    public void setPriorityRating(double priorityRating) {
        this.priorityRating = priorityRating;
    }

    public cIssueModel getIssueModel() {
        return issueModel;
    }

    public void setIssueModel(cIssueModel issueModel) {
        this.issueModel = issueModel;
    }
}
