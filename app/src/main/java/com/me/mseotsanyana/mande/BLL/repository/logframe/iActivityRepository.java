package com.me.mseotsanyana.mande.BLL.repository.logframe;

import com.me.mseotsanyana.mande.BLL.model.logframe.cActivityModel;

import java.util.Set;

public interface iActivityRepository {
    /* the create function of the Activity entity */
    boolean addActivity(cActivityModel activityModel);

    /* the read functions of the Activity entity */
    Set<cActivityModel> getActivityModelSet(long logFrameID, long userID, int primaryRoleBITS,
                                            int secondaryRoleBITS, int statusBITS);

}
