package com.me.mseotsanyana.mande.BLL.model.monitor;

import com.me.mseotsanyana.mande.BLL.model.evaluator.cMatrixSetModel;

public class cMatrixIndicatorModel extends cQuantitativeIndicatorModel{
    private cMatrixSetModel matrixSetModel;

    public cMatrixSetModel getMatrixSetModel() {
        return matrixSetModel;
    }

    public void setMatrixSetModel(cMatrixSetModel matrixSetModel) {
        this.matrixSetModel = matrixSetModel;
    }
}
