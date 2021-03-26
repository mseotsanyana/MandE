package com.me.mseotsanyana.mande.BLL.interactors.session.menu;

import com.me.mseotsanyana.mande.BLL.interactors.base.iInteractor;
import com.me.mseotsanyana.mande.BLL.model.session.cMenuModel;

import java.util.List;

public interface iReadMenuItemsInteractor extends iInteractor {
    interface Callback {
        void onReadMenuItemsFailed(String msg);
        void onReadMenuItemsSucceeded(List<cMenuModel> menuModels);
    }
}
