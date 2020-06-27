package com.me.mseotsanyana.mande.BLL.repository.logframe;

import com.me.mseotsanyana.mande.DAL.model.logframe.cInputModel;

import java.util.Set;

public interface iInputRepository {
    /* the create function of the Input entity */
    boolean addInput(cInputModel inputModel);

    /* the read functions of the Input entity */
    Set<cInputModel> getInputModelSet(long logFrameID, int userID, int primaryRoleBITS,
                                      int secondaryRoleBITS, int statusBITS);
}
