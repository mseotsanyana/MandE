package com.me.mseotsanyana.mande.UTIL.BLL;

import java.util.Date;

/**
 * Created by mseotsanyana on 2016/12/27.
 */

public class cWorkplanDomain {
    private int workplanID;
    private int userID;
    private Date startDate;
    private Date finishDate;

    public int getWorkplanID() {
        return workplanID;
    }

    public void setWorkplanID(int workplanID) {
        this.workplanID = workplanID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }
}
