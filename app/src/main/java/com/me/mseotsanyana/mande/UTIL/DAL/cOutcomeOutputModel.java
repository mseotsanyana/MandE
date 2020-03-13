package com.me.mseotsanyana.mande.UTIL.DAL;

import java.util.Date;

/**
 * Created by mseotsanyana on 2017/06/27.
 */

public class cOutcomeOutputModel {
    private int outcomeID;
    private int outputID;
    private int ownerID;
    private Date createDate;

    public int getOutcomeID() {
        return outcomeID;
    }

    public void setOutcomeID(int outcomeID) {
        this.outcomeID = outcomeID;
    }

    public int getOutputID() {
        return outputID;
    }

    public void setOutputID(int outputID) {
        this.outputID = outputID;
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
