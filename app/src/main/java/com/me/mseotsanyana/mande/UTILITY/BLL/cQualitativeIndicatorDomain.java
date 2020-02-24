package com.me.mseotsanyana.mande.UTILITY.BLL;

import java.util.Date;

public class cQualitativeIndicatorDomain {
	private int indicatorID;
    private int ownerID;
    private String qualitativeBaseline;
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

    public String getQualitativeBaseline() {
        return qualitativeBaseline;
    }

    public void setQualitativeBaseline(String qualitativeBaseline) {
        this.qualitativeBaseline = qualitativeBaseline;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
