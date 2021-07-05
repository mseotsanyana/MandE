package com.me.mseotsanyana.mande.BLL.interactors.session.user.Impl;

import com.me.mseotsanyana.mande.BLL.executor.iExecutor;
import com.me.mseotsanyana.mande.BLL.executor.iMainThread;
import com.me.mseotsanyana.mande.BLL.interactors.base.cAbstractInteractor;
import com.me.mseotsanyana.mande.BLL.interactors.session.user.iUserLoginInteractor;
import com.me.mseotsanyana.mande.BLL.model.session.cMenuModel;
import com.me.mseotsanyana.mande.BLL.repository.session.iPermissionRepository;
import com.me.mseotsanyana.mande.BLL.repository.session.iSharedPreferenceRepository;
import com.me.mseotsanyana.mande.BLL.repository.session.iUserProfileRepository;
import com.me.mseotsanyana.mande.DAL.storage.preference.cSharedPreference;

import java.util.List;

public class cUserLoginInteractorImpl extends cAbstractInteractor implements iUserLoginInteractor {
    private final Callback callback;
    private final iSharedPreferenceRepository sharedPreferenceRepository;
    private final iPermissionRepository privilegeRepository;
    private final iUserProfileRepository userProfileRepository;


    private final String email;
    private final String password;

    public cUserLoginInteractorImpl(iExecutor threadExecutor, iMainThread mainThread, Callback callback,
                                    iSharedPreferenceRepository sharedPreferenceRepository,
                                    iPermissionRepository privilegeRepository,
                                    iUserProfileRepository userProfileRepository,
                                    String email, String password) {
        super(threadExecutor, mainThread);

        if (privilegeRepository == null || sharedPreferenceRepository == null ||
                userProfileRepository == null || callback == null) {
            throw new IllegalArgumentException("Arguments can not be null!");
        }

        // callback to send data messages to the presentation layer
        this.callback = callback;
        // repository interface for shared preferences
        this.sharedPreferenceRepository = sharedPreferenceRepository;
        // repository interface for loggedIn privileges
        this.privilegeRepository = privilegeRepository;
        // repository interface for user profile - menu, user account
        this.userProfileRepository = userProfileRepository;

        this.email = email;
        this.password = password;
    }

    /**
     * communicate error message to the presentation layer
     *
     * @param msg error message
     */
    private void notifyError(String msg) {
        mainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onUserLoginFailed(msg);
            }
        });
    }

    /**
     * communicate login communication message to the
     * presentation layer
     *
     * @param msg success message
     */
    private void postMessage(String msg) {
        mainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onUserLoginSucceeded(msg);
            }
        });
    }

    /**
     * delete old and save new user privileges of the loggedIn user
     */
    private void saveUserPrivileges(String msg) {
        this.privilegeRepository.saveUserPermissions(new iPermissionRepository.
                iSaveUserPermissionsCallback() {
            @Override
            public void onSaveUserPermissionsSucceeded(String privilegeMessage) {
                postMessage(msg);
            }

            @Override
            public void onSaveUserPermissionsFailed(String privilegeMessage) {
                notifyError("Failure:" + privilegeMessage);
            }

            @Override
            public void onSaveOwnerID(String ownerServerID) {
                sharedPreferenceRepository.saveStringSetting(cSharedPreference.KEY_USER_ID,
                        ownerServerID);
                sharedPreferenceRepository.commitSettings();
            }

            @Override
            public void onSaveOrganizationServerID(String organizationServerID) {
                sharedPreferenceRepository.saveStringSetting(cSharedPreference.KEY_ORG_ID,
                        organizationServerID);
                sharedPreferenceRepository.commitSettings();
            }

            @Override
            public void onSavePrimaryTeamBIT(int primaryTeamBIT) {
                sharedPreferenceRepository.saveIntSetting(cSharedPreference.KEY_PRIMARY_TEAM_BIT,
                        primaryTeamBIT);
                sharedPreferenceRepository.commitSettings();
            }

            @Override
            public void onSaveSecondaryTeams(List<Integer> secondaryTeams) {
                sharedPreferenceRepository.saveListIntegerSetting(
                        cSharedPreference.KEY_SECONDARY_TEAMS, secondaryTeams);
                sharedPreferenceRepository.commitSettings();
            }

            @Override
            public void onSaveEntityBITS(String moduleKey, int entityBITS) {
                sharedPreferenceRepository.saveIntSetting(
                        cSharedPreference.KEY_MODULE_ENTITY_BITS + "-" + moduleKey,
                        entityBITS);
                sharedPreferenceRepository.commitSettings();
            }

            @Override
            public void onSaveEntityPermBITS(String moduleKey, String entityKey, int operationBITS) {
                sharedPreferenceRepository.saveIntSetting(
                        cSharedPreference.KEY_ENTITY_OPERATION_BITS + "-" + moduleKey + "-" + entityKey,
                        operationBITS);
                sharedPreferenceRepository.commitSettings();
            }

            @Override
            public void onSaveStatusBITS(String moduleKey, String entityKey, String operationKey,
                                         List<Integer> statuses) {
                sharedPreferenceRepository.saveListIntegerSetting(
                        cSharedPreference.KEY_OPERATION_STATUS_BITS + "-" + moduleKey + "-" + entityKey + "-" +
                                operationKey, statuses);
                sharedPreferenceRepository.commitSettings();
            }

            @Override
            public void onSaveUnixPermBITS(String moduleKey, String entityKey, int unixpermBITS) {
                sharedPreferenceRepository.saveIntSetting(
                        cSharedPreference.KEY_UNIX_PERM_BITS + "-" + moduleKey + "-" + entityKey,
                        unixpermBITS);
                sharedPreferenceRepository.commitSettings();
            }

            @Override
            public void onSaveMenuItems(List<cMenuModel> menuModels) {
                sharedPreferenceRepository.saveMenuItems(
                        cSharedPreference.KEY_MENU_ITEM_BITS, menuModels);
                sharedPreferenceRepository.commitSettings();
            }

            @Override
            public void onClearPreferences() {
                sharedPreferenceRepository.deleteSettings();
            }
        });
    }

    @Override
    public void run() {

        userProfileRepository.signInWithEmailAndPassword(email, password,
                new iUserProfileRepository.iSignInRepositoryCallback() {
                    @Override
                    public void onSignInSucceeded(String msg) {
                        saveUserPrivileges(msg);
                    }

                    @Override
                    public void onSignInFailed(String msg) {
                        notifyError(msg);
                    }
                });
    }
}