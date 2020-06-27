package com.me.mseotsanyana.mande.BLL.repository.logframe;

import com.me.mseotsanyana.mande.DAL.model.logframe.cOutputModel;
import com.me.mseotsanyana.mande.DAL.model.logframe.cOutcomeModel;

import java.util.Set;

public interface iOutputRepository {
    /* the create function of the Impact entity */
    boolean addOutput(cOutputModel outputModel);

    /* the read functions of the Output entity */
    Set<cOutputModel> getOutputModelSet(long logFrameID, int userID, int primaryRoleBITS,
                                          int secondaryRoleBITS, int statusBITS);
}
