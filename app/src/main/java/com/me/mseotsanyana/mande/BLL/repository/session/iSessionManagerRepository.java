package com.me.mseotsanyana.mande.BLL.repository.session;

import com.me.mseotsanyana.mande.DAL.model.session.cOrganizationModel;
import com.me.mseotsanyana.mande.DAL.model.session.cPermissionModel;
import com.me.mseotsanyana.mande.DAL.model.session.cRoleModel;
import com.me.mseotsanyana.mande.DAL.model.session.cStatusModel;
import com.me.mseotsanyana.mande.DAL.model.session.cUserModel;

import java.util.Set;

public interface iSessionManagerRepository {
    /* user shared preferences */
    void commitSettings();
    void deleteSettings();

    /* save personal preferences of the loggedIn user */
    void saveUserID(int userID);
    void saveOrganizationID(int organizationID);
    void savePrimaryRoleBITS(cUserModel userModel);
    void saveSecondaryRoleBITS(cUserModel userModel);
    void saveDefaultPermsBITS(int bitNumber);
    void saveDefaultStatusBITS(int bitNumber);

    void saveUserProfile(cUserModel userModel);
    void savePermissionBITS(Set<cPermissionModel> permissionSet);
    void saveStatusSet(Set<cStatusModel> statusModelSet);
    void saveRoleSet(Set<cRoleModel> roleModelSet);
    void saveIndividualOwners(Set<cUserModel> userModels);
    void saveOrganizationOwners(Set<cOrganizationModel> organizationModels);

    /* load personal preferences of the loggedIn user */
    boolean isLoggedIn();
    int loadUserID();
    int loadOrganizationID();
    int loadPrimaryRoleBITS();
    int loadSecondaryRoleBITS();
    int loadDefaultPermsBITS();
    int loadDefaultStatusBITS();

    String loadUserProfile();
    int loadEntityBITS(int entityTypeID);
    int loadOperationBITS(int entityID, int entityTypeID);
    int loadStatusBITS(int entityID, int entityTypeID, int operationID);
    Set loadStatusSet();
    Set loadRoleSet();
    Set loadIndividualOwners();
    Set loadOrganizationOwners();
}
