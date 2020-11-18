package com.me.mseotsanyana.mande.BLL.repository.awpb;

import com.me.mseotsanyana.mande.BLL.model.wpb.cHumanModel;

import java.util.Set;

public interface iHumanRepository {
    /* the read functions of the Human entity */
    Set<cHumanModel> getHumanModelSet(long inputID, long userID, int primaryRoleBITS,
                                      int secondaryRoleBITS, int statusBITS);
}
