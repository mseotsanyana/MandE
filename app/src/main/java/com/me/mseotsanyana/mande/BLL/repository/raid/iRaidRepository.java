package com.me.mseotsanyana.mande.BLL.repository.raid;

import com.me.mseotsanyana.mande.BLL.model.logframe.cRaidModel;

import java.util.Set;

public interface iRaidRepository {

    /* the read functions of the Input entity */
    Set<cRaidModel> getRaidModelSetByID(long logFrameID, long userID, int primaryRoleBITS,
                                            int secondaryRoleBITS, int statusBITS);
}
