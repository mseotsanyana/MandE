package com.me.mseotsanyana.mande.BLL.model.monitor;

import com.me.mseotsanyana.mande.BLL.model.evaluator.cColChoiceModel;
import com.me.mseotsanyana.mande.BLL.model.evaluator.cRowChoiceModel;

public class cMatrixMilestoneModel extends cQuantitativeMilestoneModel{
    private cRowChoiceModel rowChoiceModel;
    private cColChoiceModel colChoiceModel;
    private double disaggregatedBaseline;
    private double disaggregatedTarget;

    public cMatrixMilestoneModel(){
        rowChoiceModel = new cRowChoiceModel();
        colChoiceModel = new cColChoiceModel();
    }

    public cRowChoiceModel getRowChoiceModel() {
        return rowChoiceModel;
    }

    public void setRowChoiceModel(cRowChoiceModel rowChoiceModel) {
        this.rowChoiceModel = rowChoiceModel;
    }

    public cColChoiceModel getColChoiceModel() {
        return colChoiceModel;
    }

    public void setColChoiceModel(cColChoiceModel colChoiceModel) {
        this.colChoiceModel = colChoiceModel;
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
