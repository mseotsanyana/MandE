package com.me.mseotsanyana.mande.BLL.repository.session;

import com.me.mseotsanyana.mande.DAL.model.session.cPermissionModel;
import com.me.mseotsanyana.mande.DAL.model.session.cUserModel;

import java.util.Set;

public interface iUserRepository {
    cUserModel getUserByEmailPassword(String email, String password);

    /*int getPrimaryRoleBITS(cUserModel userModel);
    int getSecondaryRoleBITS(cUserModel userModel);*/
}