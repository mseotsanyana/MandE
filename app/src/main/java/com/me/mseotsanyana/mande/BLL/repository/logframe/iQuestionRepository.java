package com.me.mseotsanyana.mande.BLL.repository.logframe;

import com.me.mseotsanyana.mande.DAL.model.logframe.cQuestionModel;

import java.util.Set;

public interface iQuestionRepository {

    /* the read functions of the Input entity */
    Set<cQuestionModel> getQuestionModelSetByID(long logFrameID, long userID, int primaryRoleBITS,
                                            int secondaryRoleBITS, int statusBITS);
}
