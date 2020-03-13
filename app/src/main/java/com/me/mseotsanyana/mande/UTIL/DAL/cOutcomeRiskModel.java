package com.me.mseotsanyana.mande.UTIL.DAL;

import java.util.Date;

/**
 * Created by mseotsanyana on 2017/06/27.
 */

public class cOutcomeRiskModel {
    private int outcomeID;
    private int riskID;
    private int ownerID;
    private Date createDate;

    public int getOutcomeID() {
        return outcomeID;
    }

    public void setOutcomeID(int outcomeID) {
        this.outcomeID = outcomeID;
    }

    public int getRiskID() {
        return riskID;
    }

    public void setRiskID(int riskID) {
        this.riskID = riskID;
    }

    public int getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(int ownerID) {
        this.ownerID = ownerID;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
