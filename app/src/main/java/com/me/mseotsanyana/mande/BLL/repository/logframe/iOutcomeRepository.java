package com.me.mseotsanyana.mande.BLL.repository.logframe;

import com.me.mseotsanyana.mande.BLL.model.logframe.cOutcomeModel;

import java.util.Set;

public interface iOutcomeRepository {
    /* the create function of the Outcome entity */
    boolean addOutcome(cOutcomeModel outcomeModel);

    /* the read functions of the Outcome entity */
    Set<cOutcomeModel> getOutcomeModelSet(long logFrameID, long userID, int primaryRoleBITS,
                                          int secondaryRoleBITS, int statusBITS);
}
