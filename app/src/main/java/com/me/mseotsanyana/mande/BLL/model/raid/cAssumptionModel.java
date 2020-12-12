package com.me.mseotsanyana.mande.BLL.model.raid;

import com.me.mseotsanyana.mande.BLL.model.logframe.cRaidModel;
import com.me.mseotsanyana.mande.BLL.model.raid.cAssumptionRegisterModel;

public class cAssumptionModel extends cRaidModel {
    private cAssumptionRegisterModel assumptionRegisterModel;

    public cAssumptionModel(){
        assumptionRegisterModel = new cAssumptionRegisterModel();
    }

    public cAssumptionRegisterModel getAssumptionRegisterModel() {
        return assumptionRegisterModel;
    }

    public void setAssumptionRegisterModel(cAssumptionRegisterModel assumptionRegisterModel) {
        this.assumptionRegisterModel = assumptionRegisterModel;
    }
}
