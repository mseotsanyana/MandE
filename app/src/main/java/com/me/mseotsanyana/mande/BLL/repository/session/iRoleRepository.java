package com.me.mseotsanyana.mande.BLL.repository.session;

import com.me.mseotsanyana.mande.DAL.model.session.cPermissionModel;
import com.me.mseotsanyana.mande.DAL.model.session.cRoleModel;

import java.util.Set;

public interface iRoleRepository {
    Set<cPermissionModel> getPermissionSetByRoleID(int roleID, int organizationID);
    Set<cRoleModel> getRoleModelSet();
}
