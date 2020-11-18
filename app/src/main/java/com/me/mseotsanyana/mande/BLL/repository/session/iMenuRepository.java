package com.me.mseotsanyana.mande.BLL.repository.session;

import com.me.mseotsanyana.mande.BLL.model.session.cMenuModel;

import java.util.Set;

public interface iMenuRepository {
    Set<cMenuModel> getMenuModelSet(long userID, int primaryRole,
                                    int secondaryRoles, int statusBITS);
}
