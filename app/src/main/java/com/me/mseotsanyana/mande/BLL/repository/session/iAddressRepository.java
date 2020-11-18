package com.me.mseotsanyana.mande.BLL.repository.session;

import com.me.mseotsanyana.mande.BLL.model.session.cAddressModel;

public interface iAddressRepository {
    boolean addAddressFromExcel(cAddressModel addressModel);
}
