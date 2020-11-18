package com.me.mseotsanyana.mande.BLL.repository.awpb;

import com.me.mseotsanyana.mande.BLL.model.wpb.cExpenseModel;

import java.util.Set;

public interface iExpenseRepository {
    /* the read functions of the Expense entity */
    Set<cExpenseModel> getExpenseModelSet(long inputID, long userID, int primaryRoleBITS,
                                          int secondaryRoleBITS, int statusBITS);

}
