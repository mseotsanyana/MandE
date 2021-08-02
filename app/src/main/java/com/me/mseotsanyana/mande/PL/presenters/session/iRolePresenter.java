package com.me.mseotsanyana.mande.PL.presenters.session;

import com.me.mseotsanyana.mande.BLL.model.session.cRoleModel;
import com.me.mseotsanyana.mande.PL.presenters.base.iPresenter;
import com.me.mseotsanyana.mande.PL.ui.iBaseView;

import java.util.List;

public interface iRolePresenter extends iPresenter {
    interface View extends iBaseView {
        void onReadRolesFailed(String msg);
        void onReadRolesSucceeded(List<cRoleModel> roleModels);
    }
    void readRoles();
}

