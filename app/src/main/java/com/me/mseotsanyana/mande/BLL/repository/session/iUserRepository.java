package com.me.mseotsanyana.mande.BLL.repository.session;

import com.me.mseotsanyana.mande.BLL.model.session.cUserModel;
import com.me.mseotsanyana.mande.BLL.model.session.cUserProfileModel;

import java.util.Set;

public interface iUserRepository {

    Set<cUserModel> getOwnerSet();
}
