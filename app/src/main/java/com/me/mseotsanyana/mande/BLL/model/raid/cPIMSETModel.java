package com.me.mseotsanyana.mande.BLL.model.raid;

public class cPIMSETModel {
    private cPIMModel pimModel;
    private cRAIDLikelihoodModel raidLikelihoodModel;
    private cRAIDImpactModel raidImpactModel;
    private cRobotModel robotModel;

    public cPIMSETModel(){
        pimModel = new cPIMModel();
        raidLikelihoodModel = new cRAIDLikelihoodModel();
        raidImpactModel = new cRAIDImpactModel();
        robotModel = new cRobotModel();
    }

    public cPIMModel getPimModel() {
        return pimModel;
    }

    public void setPimModel(cPIMModel pimModel) {
        this.pimModel = pimModel;
    }

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
