package com.me.mseotsanyana.mande.BLL.interactors.session.menu;

import com.me.mseotsanyana.mande.BLL.interactors.base.iInteractor;
import com.me.mseotsanyana.mande.BLL.model.session.cAddressModel;

import java.util.List;

public interface iGetMenuItemsInteractor extends iInteractor {
    interface Callback{
        void onGetUserAddresses(List<cAddressModel> addressModels);
    }
}
