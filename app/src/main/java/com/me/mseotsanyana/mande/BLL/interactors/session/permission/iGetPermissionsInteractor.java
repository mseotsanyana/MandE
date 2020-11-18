package com.me.mseotsanyana.mande.BLL.interactors.session.permission;

import com.me.mseotsanyana.mande.BLL.interactors.base.iInteractor;
import com.me.mseotsanyana.mande.BLL.model.session.cAddressModel;

import java.util.List;

public interface iGetPermissionsInteractor extends iInteractor {
    interface Callback{
        void onGetUserAddresses(List<cAddressModel> addressModels);
    }
}
