package com.me.mseotsanyana.mande.UTIL.DAL;

import java.util.Date;

/**
 * Created by mseotsanyana on 2017/06/26.
 */

public class cProjectOutcomeModel {
    private int projectID;
    private int outcomeID;
    private int ownerID;
    private Date createDate;

    public int getProjectID() {
        return projectID;
    }

    public void setProjectID(int projectID) {
        this.projectID = projectID;
    }

    public int getOutcomeID() {
        return outcomeID;
    }

    public void setOutcomeID(int outcomeID) {
        this.outcomeID = outcomeID;
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
