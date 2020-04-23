package com.me.mseotsanyana.mande.BLL.repository.session;

import com.me.mseotsanyana.mande.DAL.model.session.cPermissionModel;
import com.me.mseotsanyana.mande.DAL.model.session.cUserModel;

import java.util.Set;

public interface iSessionManagerRepository {
    /* user shared preferences */
    void commitSettings();
    void deleteSettings();

    /* save personal preferences of the loggedIn user */
    void saveUserID(int userID);
    void saveOrganizationID(int organizationID);
    void saveUserProfile(cUserModel userModel);
    void savePrimaryRoleBITS(cUserModel userModel);
    void saveSecondaryRoleBITS(cUserModel userModel);
    void savePermissionBITS(Set<cPermissionModel> permissionSet);

    /* load personal preferences of the loggedIn user */
    boolean isLoggedIn();
    int loadUserID();
    int loadOrganizationID();
    String loadUserProfile();
    int loadPrimaryRoleBITS();
    int loadSecondaryRoleBITS();
    int loadEntityBITS(int entityTypeID);
    int loadOperationBITS(int entityID, int entityTypeID);
    int loadStatusBITS(int entityID, int entityTypeID, int operationID);
}
