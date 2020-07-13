package com.me.mseotsanyana.mande.DAL.model.session;

import com.me.mseotsanyana.mande.DAL.model.wpb.cIncomeModel;

public class cCrowdFundModel {
    private long incomeID;
    private long funderID;
    private long beneficiaryID;
    private double amount;

    private cIncomeModel incomeModel;
    private cFunderModel funderModel;
    private cBeneficiaryModel beneficiaryModel;


    public long getIncomeID() {
        return incomeID;
    }

    public void setIncomeID(long incomeID) {
        this.incomeID = incomeID;
    }

    public long getFunderID() {
        return funderID;
    }

    public void setFunderID(long funderID) {
        this.funderID = funderID;
    }

    public long getBeneficiaryID() {
        return beneficiaryID;
    }

    public void setBeneficiaryID(long beneficiaryID) {
        this.beneficiaryID = beneficiaryID;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public cIncomeModel getIncomeModel() {
        return incomeModel;
    }

    public void setIncomeModel(cIncomeModel incomeModel) {
        this.incomeModel = incomeModel;
    }

    public cFunderModel getFunderModel() {
        return funderModel;
    }

    public void setFunderModel(cFunderModel funderModel) {
        this.funderModel = funderModel;
    }

    public cBeneficiaryModel getBeneficiaryModel() {
        return beneficiaryModel;
    }

    public void setBeneficiaryModel(cBeneficiaryModel beneficiaryModel) {
        this.beneficiaryModel = beneficiaryModel;
    }
}
