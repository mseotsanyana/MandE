package com.me.mseotsanyana.mande.BLL.repository.session;

import com.me.mseotsanyana.mande.BLL.model.session.cUserModel;

import java.util.Set;

public interface iUserRepository {
    cUserModel getUserByEmailPassword(String email, String password);
    Set<cUserModel> getOwnerSet();

    /*int getPrimaryRoleBITS(cUserModel userModel);
    int getSecondaryRoleBITS(cUserModel userModel);*/
}
