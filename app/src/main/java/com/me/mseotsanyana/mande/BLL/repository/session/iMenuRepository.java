package com.me.mseotsanyana.mande.BLL.repository.session;

import com.me.mseotsanyana.mande.BLL.model.session.cMenuModel;

import java.util.List;

public interface iMenuRepository {
    void getMenuModels(long userID, int primaryRole, int secondaryRoles, int statusBITS,
                         iMenuRepository.iReadMenuModelSetCallback callback);

    interface iReadMenuModelSetCallback{
        void onReadMenuSucceeded(List<cMenuModel> menuModels);
        void onReadMenuFailed(String msg);
    }

    //Set<cMenuModel> getMenuModelSet(long userID, int primaryRole,
    //                                int secondaryRoles, int statusBITS);
}
