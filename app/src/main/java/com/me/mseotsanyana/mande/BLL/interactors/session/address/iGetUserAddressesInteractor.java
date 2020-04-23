package com.me.mseotsanyana.mande.BLL.interactors.session.address;

import com.me.mseotsanyana.mande.BLL.interactors.base.iInteractor;
import com.me.mseotsanyana.mande.DAL.model.session.cAddressModel;

import java.util.List;

public interface iGetUserAddressesInteractor extends iInteractor {
    interface Callback{
        void onGetUserAddresses(List<cAddressModel> addressModels);
    }
}
