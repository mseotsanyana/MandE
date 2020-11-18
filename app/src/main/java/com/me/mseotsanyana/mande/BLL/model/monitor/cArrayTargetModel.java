package com.me.mseotsanyana.mande.BLL.model.monitor;

import com.me.mseotsanyana.mande.BLL.model.evaluator.cArrayChoiceModel;

public class cArrayTargetModel extends cQuantitativeTargetModel{
    private cArrayChoiceModel arrayChoiceModel;
    private double disaggregatedBaseline;
    private double disaggregatedTarget;

    public cArrayChoiceModel getArrayChoiceModel() {
        return arrayChoiceModel;
    }

    public void setArrayChoiceModel(cArrayChoiceModel arrayChoiceModel) {
        this.arrayChoiceModel = arrayChoiceModel;
    }

    public double getDisaggregatedBaseline() {
        return disaggregatedBaseline;
    }

    public void setDisaggregatedBaseline(double disaggregatedBaseline) {
        this.disaggregatedBaseline = disaggregatedBaseline;
    }

    public double getDisaggregatedTarget() {
        return disaggregatedTarget;
    }

    public void setDisaggregatedTarget(double disaggregatedTarget) {
        this.disaggregatedTarget = disaggregatedTarget;
    }
}
