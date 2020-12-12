package com.me.mseotsanyana.mande.BLL.model.logframe;

import com.me.mseotsanyana.mande.BLL.model.common.cMatrixChartModel;
import com.me.mseotsanyana.mande.BLL.model.evaluator.cMatrixResponseModel;
import com.me.mseotsanyana.mande.BLL.model.evaluator.cMatrixSetModel;

import java.util.Set;

public class cMatrixQuestionModel extends cQuestionModel{
    private long matrixSetID;
    private long matrixChartID;

    private cMatrixSetModel matrixSetModel;
    private cMatrixChartModel matrixChartModel;

    private Set<cMatrixResponseModel> matrixResponseModelSet;

    public long getMatrixSetID() {
        return matrixSetID;
    }

    public void setMatrixSetID(long matrixSetID) {
        this.matrixSetID = matrixSetID;
    }

    public long getMatrixChartID() {
        return matrixChartID;
    }

    public void setMatrixChartID(long matrixChartID) {
        this.matrixChartID = matrixChartID;
    }

    public cMatrixSetModel getMatrixSetModel() {
        return matrixSetModel;
    }

    public void setMatrixSetModel(cMatrixSetModel matrixSetModel) {
        this.matrixSetModel = matrixSetModel;
    }

    public cMatrixChartModel getMatrixChartModel() {
        return matrixChartModel;
    }

    public void setMatrixChartModel(cMatrixChartModel matrixChartModel) {
        this.matrixChartModel = matrixChartModel;
    }

    public Set<cMatrixResponseModel> getMatrixResponseModelSet() {
        return matrixResponseModelSet;
    }

    public void setMatrixResponseModelSet(Set<cMatrixResponseModel> matrixResponseModelSet) {
        this.matrixResponseModelSet = matrixResponseModelSet;
    }
}
