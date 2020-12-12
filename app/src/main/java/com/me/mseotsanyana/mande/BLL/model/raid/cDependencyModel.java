package com.me.mseotsanyana.mande.BLL.model.raid;

import com.me.mseotsanyana.mande.BLL.model.logframe.cRaidModel;
import com.me.mseotsanyana.mande.BLL.model.raid.cDependencyRegisterModel;

public class cDependencyModel extends cRaidModel {
    private String howValidated;
    private int validated;

    private cDependencyRegisterModel dependencyRegisterModel;

    public cDependencyModel(){
        dependencyRegisterModel = new cDependencyRegisterModel();
    }

    public String getHowValidated() {
        return howValidated;
    }

    public void setHowValidated(String howValidated) {
        this.howValidated = howValidated;
    }

    public int getValidated() {
        return validated;
    }

    public void setValidated(int validated) {
        this.validated = validated;
    }

    public cDependencyRegisterModel getDependencyRegisterModel() {
        return dependencyRegisterModel;
    }

    public void setDependencyRegisterModel(cDependencyRegisterModel dependencyRegisterModel) {
        this.dependencyRegisterModel = dependencyRegisterModel;
    }
}
