package com.me.mseotsanyana.mande.BLL.repository.session;

import com.me.mseotsanyana.mande.BLL.model.session.cMenuModel;

import java.util.List;
import java.util.Map;
import java.util.SortedMap;

public interface iSharedPreferenceRepository {
    /* user shared preferences */
    void commitSettings();
    void deleteSettings();
    void removeSetting(String key);

    void saveIntSetting(String key, int value);
    void saveStringSetting(String key, String value);
    void saveBooleanSetting(String key, Boolean value);
    void saveListIntegerSetting(String key, List<Integer> value);
    void saveMenuItems(String key, List<cMenuModel> value);

    /* load personal preferences of the loggedIn user */
    String loadUserID();
    String loadOrganizationID();
    int loadPrimaryTeamBIT();
    List<Integer> loadSecondaryTeams();
    int loadEntityPermissionBITS(int moduleKey, int entityKey);
    List<Integer> loadOperationStatuses(int moduleKey, int entityKey, int operationKey);
    int loadUnixPermissionBITS(int moduleKey, int entityKey);
    List<cMenuModel> loadMenuItems();

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
//    int loadPrimaryTeamBIT();
//    int loadSecondaryTeamBITS();
//    int loadEntityPermissionBITS(int moduleKey, int entityKey);
//    List<Integer> loadOperationStatuses(int moduleKey, int entityKey, int operationKey);
//    int loadUnixPermissionBITS(int moduleKey, int entityKey);

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
