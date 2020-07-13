package com.me.mseotsanyana.mande.BLL.repository.wpb;

import com.me.mseotsanyana.mande.DAL.model.wpb.cMaterialModel;

import java.util.Set;

public interface iMaterialRepository {
    /* the read functions of the Material entity */
    Set<cMaterialModel> getMaterialModelSet(long inputID, long userID, int primaryRoleBITS,
                                           int secondaryRoleBITS, int statusBITS);
}
