package com.me.mseotsanyana.mande.UTILITY.BLL;

import java.util.Date;

public class cHybridTargetDomain {
    private int hybridID;
	private int indicatorID;
    private int ownerID;
    private int categoryID; // target
    private Date createDate;


    public int getHybridID() {
        return hybridID;
    }

    public void setHybridID(int hybridID) {
        this.hybridID = hybridID;
    }

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

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
