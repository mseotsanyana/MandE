package com.me.mseotsanyana.mande.UTIL.DAL;

import java.util.Date;

public class cHybridIndicatorModel {
	private int indicatorID;
    private int ownerID;
    private int categoryID;
    private int hybridBaseline;
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

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public int getHybridBaseline() {
        return hybridBaseline;
    }

    public void setHybridBaseline(int hybridBaseline) {
        this.hybridBaseline = hybridBaseline;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

}
