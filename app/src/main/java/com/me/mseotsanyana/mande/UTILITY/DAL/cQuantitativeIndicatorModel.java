package com.me.mseotsanyana.mande.UTILITY.DAL;

import java.util.Date;

public class cQuantitativeIndicatorModel {
	private int indicatorID;
    private int ownerID;
    private int unitID;
    private int quantitativeBaseline;
    private Date createDate;

    public int getIndicatorID() {
        return indicatorID;
    }

    public void setIndicatorID(int indicatorID) {
        this.indicatorID = indicatorID;
    }

    public int getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(int ownerID) {
        this.ownerID = ownerID;
    }

    public int getUnitID() {
        return unitID;
    }

    public void setUnitID(int unitID) {
        this.unitID = unitID;
    }

    public int getQuantitativeBaseline() {
        return quantitativeBaseline;
    }

    public void setQuantitativeBaseline(int quantitativeBaseline) {
        this.quantitativeBaseline = quantitativeBaseline;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }


}
