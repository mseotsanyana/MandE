package com.me.mseotsanyana.mande.BLL.repository.session;

import com.me.mseotsanyana.mande.DAL.model.session.cMenuModel;

import java.util.Set;

public interface iMenuRepository {
    Set<cMenuModel> getMenuModelSet(int userID, int primaryRole,
                                    int secondaryRoles, int statusBITS);
}
