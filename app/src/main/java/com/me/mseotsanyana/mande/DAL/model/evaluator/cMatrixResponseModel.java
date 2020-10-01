package com.me.mseotsanyana.mande.DAL.model.evaluator;

public class cMatrixResponseModel extends cEvaluationResponseModel{
    private long matrixChoiceID;
    private long rowID;
    private long colID;

    private cMatrixChoiceModel matrixChoiceModel;
    private cRowChoiceModel rowOptionModel;
    private cColChoiceModel colOptionModel;

    public cMatrixResponseModel(){
        super();
        matrixChoiceModel = new cMatrixChoiceModel();
        rowOptionModel = new cRowChoiceModel();
        colOptionModel = new cColChoiceModel();
    }

    public long getMatrixChoiceID() {
        return matrixChoiceID;
    }

    public void setMatrixChoiceID(long matrixChoiceID) {
        this.matrixChoiceID = matrixChoiceID;
    }

    public long getRowID() {
        return rowID;
    }

    public void setRowID(long rowID) {
        this.rowID = rowID;
    }

    public long getColID() {
        return colID;
    }

    public void setColID(long colID) {
        this.colID = colID;
    }

    public cMatrixChoiceModel getMatrixChoiceModel() {
        return matrixChoiceModel;
    }

    public void setMatrixChoiceModel(cMatrixChoiceModel matrixChoiceModel) {
        this.matrixChoiceModel = matrixChoiceModel;
    }

    public cRowChoiceModel getRowOptionModel() {
        return rowOptionModel;
    }

    public void setRowOptionModel(cRowChoiceModel rowOptionModel) {
        this.rowOptionModel = rowOptionModel;
    }

    public cColChoiceModel getColOptionModel() {
        return colOptionModel;
    }

    public void setColOptionModel(cColChoiceModel colOptionModel) {
        this.colOptionModel = colOptionModel;
    }
}
