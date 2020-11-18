package com.me.mseotsanyana.mande.BLL.model.raid;

import com.me.mseotsanyana.mande.BLL.model.wpb.cMilestoneModel;

public class cMilestoneReviewModel extends cMilestoneModel {

    private cRAIDLikelihoodModel raidLikelihoodModel;
    private cRAIDImpactModel raidImpactModel;
    private cRobotModel robotModel;

    public cRAIDLikelihoodModel getRaidLikelihoodModel() {
        return raidLikelihoodModel;
    }

    public void setRaidLikelihoodModel(cRAIDLikelihoodModel raidLikelihoodModel) {
        this.raidLikelihoodModel = raidLikelihoodModel;
    }

    public cRAIDImpactModel getRaidImpactModel() {
        return raidImpactModel;
    }

    public void setRaidImpactModel(cRAIDImpactModel raidImpactModel) {
        this.raidImpactModel = raidImpactModel;
    }

    public cRobotModel getRobotModel() {
        return robotModel;
    }

    public void setRobotModel(cRobotModel robotModel) {
        this.robotModel = robotModel;
    }
}
