package com.me.mseotsanyana.mande.BLL.repository.session;

import com.me.mseotsanyana.mande.BLL.model.session.cMenuModel;

import java.util.List;

public interface iPrivilegeRepository {
    void saveUserPrivileges(iSaveUserPrivilegesCallback callback);
    interface iSaveUserPrivilegesCallback{
        void onSaveUserPrivilegesSucceeded(String msg);
        void onSaveUserPrivilegesFailed(String msg);

        void onSaveOwnerID(String ownerServerID);
        void onSaveOrganizationServerID(String organizationServerID);

        void onSavePrimaryTeamBIT(int primaryTeamBIT);
        void onSaveSecondaryTeamBITS(int secondaryTeamBITS);
        void onSaveEntityBITS(String moduleKey, int entityBITS);
        void onSaveOperationBITS(String moduleKey, String entityKey, int operationBITS);
        void onSaveStatusBITS(String moduleKey, String entityKey, String operationKey,
                              List<Integer> statuses);

        void onSaveUnixPermBITS(String moduleKey, String entityKey, int unixpermBITS);

        void onSaveMenuItems(List<cMenuModel> menuModels);

        void onClearPreferences();
    }
//    /* user shared preferences */
//    void commitSettings();
//    void deleteSettings();
//
//    /* save personal preferences of the loggedIn user */
//    void saveUserID(long userID);
//    void saveUserProfile(cUserModel userModel);
//    void saveCurrentOrganizationAndTeams();
//
//    void savePermissionBITS(Set<cPermissionModel> permissionSet);
//
//    //void saveOrganizationID(long organizationID);
//    //void savePrimaryTeamBIT(String teamID);
//    //void saveSecondaryTeamsBITS();
//    void saveDefaultPermsBITS(int bitNumber);
//    void saveDefaultStatusBITS(int bitNumber);
//
//
//
//    void saveStatusSet(Set<cStatusModel> statusModelSet);
//    void saveRoleSet(Set<cRoleModel> roleModelSet);
//    void saveIndividualOwners(Set<cUserModel> userModels);
//    void saveOrganizationOwners(Set<cOrganizationModel> organizationModels);
//
//    /* load personal preferences of the loggedIn user */
//    boolean isLoggedIn();
//    String loadUserID();
//    String loadOrganizationID();
//    int loadPrimaryRoleBITS();
//    int loadSecondaryRoleBITS();
//    int loadDefaultPermsBITS();
//    int loadDefaultStatusBITS();
//
//    String loadUserProfile();
//    int loadEntityBITS(long entityTypeID);
//    int loadOperationBITS(long entityID, long entityTypeID);
//    int loadStatusBITS(long entityID, long entityTypeID, long operationID);
//    Set loadStatusSet();
//    Set loadRoleSet();
//    Set loadIndividualOwners();
//    Set loadOrganizationOwners();
}
