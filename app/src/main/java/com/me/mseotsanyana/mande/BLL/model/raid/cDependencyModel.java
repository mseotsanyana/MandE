package com.me.mseotsanyana.mande.BLL.model.raid;

import com.me.mseotsanyana.mande.BLL.model.logframe.cRaidModel;
import com.me.mseotsanyana.mande.BLL.model.raid.cDependencyRegisterModel;

public class cDependencyModel extends cRaidModel {
    private cDependencyRegisterModel dependencyRegisterModel;

    public cDependencyRegisterModel getDependencyRegisterModel() {
        return dependencyRegisterModel;
    }

    public void setDependencyRegisterModel(cDependencyRegisterModel dependencyRegisterModel) {
        this.dependencyRegisterModel = dependencyRegisterModel;
    }
}
