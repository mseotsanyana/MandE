package com.me.mseotsanyana.mande.DAL.model.wpb;

import java.util.Date;

public class cActivityTaskModel extends cTaskModel{
    private long activityTaskID;
    private long activityPlanningID;

    public long getActivityTaskID() {
        return activityTaskID;
    }

    public void setActivityTaskID(long activityTaskID) {
        this.activityTaskID = activityTaskID;
    }

    public long getActivityPlanningID() {
        return activityPlanningID;
    }

    public void setActivityPlanningID(long activityPlanningID) {
        this.activityPlanningID = activityPlanningID;
    }
}
