package com.me.mseotsanyana.mande.DAL.model.monitor;

import com.me.mseotsanyana.mande.DAL.model.evaluator.cArrayChoiceModel;

public class cArrayTargetModel extends cQuantitativeTargetModel{
    private cArrayChoiceModel arrayChoiceModel;
    private int disaggregatedBaseline;
    private int disaggregatedTarget;

    public cArrayTargetModel(){
        arrayChoiceModel = new cArrayChoiceModel();
    }

    public cArrayChoiceModel getArrayChoiceModel() {
        return arrayChoiceModel;
    }

    public void setArrayChoiceModel(cArrayChoiceModel arrayChoiceModel) {
        this.arrayChoiceModel = arrayChoiceModel;
    }

    public int getDisaggregatedBaseline() {
        return disaggregatedBaseline;
    }

    public void setDisaggregatedBaseline(int disaggregatedBaseline) {
        this.disaggregatedBaseline = disaggregatedBaseline;
    }

    public int getDisaggregatedTarget() {
        return disaggregatedTarget;
    }

    public void setDisaggregatedTarget(int disaggregatedTarget) {
        this.disaggregatedTarget = disaggregatedTarget;
    }
}
