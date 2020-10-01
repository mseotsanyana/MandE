package com.me.mseotsanyana.mande.DAL.model.monitor;

import com.me.mseotsanyana.mande.DAL.model.evaluator.cColChoiceModel;
import com.me.mseotsanyana.mande.DAL.model.evaluator.cRowChoiceModel;

public class cMatrixTargetModel extends cQuantitativeTargetModel{
    private cRowChoiceModel rowChoiceModel;
    private cColChoiceModel colChoiceModel;
    private int disaggregatedBaseline;
    private int disaggregatedTarget;

    public cMatrixTargetModel(){
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
