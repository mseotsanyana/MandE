package com.me.mseotsanyana.mande.DAL.model.evaluator;

public class cMatrixResponseModel extends cEvaluationResponseModel{
    private long matrixChoiceID;
    private long rowID;
    private long colID;

    private cMatrixChoiceModel matrixChoiceModel;
    private cRowOptionModel rowOptionModel;
    private cColOptionModel colOptionModel;

    public cMatrixResponseModel(){
        super();
        matrixChoiceModel = new cMatrixChoiceModel();
        rowOptionModel = new cRowOptionModel();
        colOptionModel = new cColOptionModel();
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

    public cRowOptionModel getRowOptionModel() {
        return rowOptionModel;
    }

    public void setRowOptionModel(cRowOptionModel rowOptionModel) {
        this.rowOptionModel = rowOptionModel;
    }

    public cColOptionModel getColOptionModel() {
        return colOptionModel;
    }

    public void setColOptionModel(cColOptionModel colOptionModel) {
        this.colOptionModel = colOptionModel;
    }
}
