package com.me.mseotsanyana.mande.BLL.model.monitor;

public class cQuantitativeMilestoneModel extends cIndicatorMilestoneModel {
    double baselineValue;
    double targetValue;

    public double getBaselineValue() {
        return baselineValue;
    }

    public void setBaselineValue(double baselineValue) {
        this.baselineValue = baselineValue;
    }

    public double getTargetValue() {
        return targetValue;
    }

    public void setTargetValue(double targetValue) {
        this.targetValue = targetValue;
    }
}
