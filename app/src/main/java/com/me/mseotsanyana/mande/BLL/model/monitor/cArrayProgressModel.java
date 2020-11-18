package com.me.mseotsanyana.mande.BLL.model.monitor;

import com.me.mseotsanyana.mande.BLL.model.evaluator.cArrayChoiceModel;

public class cArrayProgressModel extends cQuantitativeProgressModel{
    private cArrayChoiceModel arrayChoiceModel;
    private double disaggregatedActual;

    public cArrayChoiceModel getArrayChoiceModel() {
        return arrayChoiceModel;
    }

    public void setArrayChoiceModel(cArrayChoiceModel arrayChoiceModel) {
        this.arrayChoiceModel = arrayChoiceModel;
    }

    public double getDisaggregatedActual() {
        return disaggregatedActual;
    }

    public void setDisaggregatedActual(double disaggregatedActual) {
        this.disaggregatedActual = disaggregatedActual;
    }
}
