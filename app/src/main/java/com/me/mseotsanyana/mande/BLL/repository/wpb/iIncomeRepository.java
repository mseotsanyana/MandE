package com.me.mseotsanyana.mande.BLL.repository.wpb;

import com.me.mseotsanyana.mande.DAL.model.wpb.cIncomeModel;

import java.util.Set;

public interface iIncomeRepository {
    /* the read functions of the Income entity */
    Set<cIncomeModel> getIncomeModelSet(long inputID, long userID, int primaryRoleBITS,
                                        int secondaryRoleBITS, int statusBITS);
}
