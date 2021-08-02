package com.me.mseotsanyana.mande.BLL.interactors.session.session.Impl;

import android.util.Log;

import com.google.gson.Gson;
import com.me.mseotsanyana.mande.BLL.executor.iExecutor;
import com.me.mseotsanyana.mande.BLL.executor.iMainThread;
import com.me.mseotsanyana.mande.BLL.interactors.base.cAbstractInteractor;
import com.me.mseotsanyana.mande.BLL.interactors.cInteractorUtils;
import com.me.mseotsanyana.mande.BLL.interactors.session.session.iHomePageInteractor;
import com.me.mseotsanyana.mande.BLL.model.session.cMenuModel;
import com.me.mseotsanyana.mande.BLL.model.session.cUserProfileModel;
import com.me.mseotsanyana.mande.BLL.repository.session.iHomePageRepository;
import com.me.mseotsanyana.mande.BLL.repository.session.iSharedPreferenceRepository;
import com.me.mseotsanyana.mande.DAL.storage.preference.cSharedPreference;

import java.util.List;


public class cHomePageInteractorImpl extends cAbstractInteractor
        implements iHomePageInteractor {
    private static final String TAG = cHomePageInteractorImpl.class.getSimpleName();

    private final Callback callback;
    private final iSharedPreferenceRepository sharedPreferenceRepository;
    private final iHomePageRepository homePageRepository;

    private final String userServerID;
    private final String organizationServerID;
    private final int primaryTeamBIT;
    private final List<Integer> secondaryTeamBITS;
    private final List<Integer> statusBITS;

    private final int entityBITS;
    private final int entitypermBITS;

    private boolean isPermissionLoaded;

    public cHomePageInteractorImpl(iExecutor threadExecutor, iMainThread mainThread,
                                   Callback callback,
                                   iSharedPreferenceRepository sharedPreferenceRepository,
                                   iHomePageRepository homePageRepository) {
        super(threadExecutor, mainThread);

        if (sharedPreferenceRepository == null || homePageRepository == null || callback == null) {
            throw new IllegalArgumentException("Arguments can not be null!");
        }

        this.sharedPreferenceRepository = sharedPreferenceRepository;
        this.homePageRepository = homePageRepository;
        this.callback = callback;


        // load user shared preferences
        this.userServerID = sharedPreferenceRepository.loadUserID();
        this.organizationServerID = sharedPreferenceRepository.loadOrganizationID();
        this.primaryTeamBIT = sharedPreferenceRepository.loadPrimaryTeamBIT();
        this.secondaryTeamBITS = sharedPreferenceRepository.loadSecondaryTeams();

        // load entity shared preferences
        this.entityBITS = sharedPreferenceRepository.loadEntityBITS(
                cSharedPreference.SESSION_MODULE);
        this.entitypermBITS = sharedPreferenceRepository.loadEntityPermissionBITS(
                cSharedPreference.SESSION_MODULE, cSharedPreference.ORGANIZATION);
        this.statusBITS = sharedPreferenceRepository.loadOperationStatuses(
                cSharedPreference.SESSION_MODULE, cSharedPreference.ORGANIZATION,
                cSharedPreference.READ);

        Log.d(TAG, " \n ORGANIZATION ID = " + this.organizationServerID +
                " \n USER ID = " + this.userServerID +
                " \n PRIMARY TEAM BIT = " + this.primaryTeamBIT +
                " \n SECONDARY TEAM BITS = " + this.secondaryTeamBITS +
                " \n ENTITY BITS = " + this.entityBITS +
                " \n ENTITY PERMISSION BITS = " + this.entitypermBITS +
                " \n OPERATION STATUSES = " + this.statusBITS);
    }

    /* call back on update home page failed */
    private void notifyError(String msg) {
        mainThread.post(() -> callback.onReadHomePageFailed(msg));
    }

    /* call back on user profile */
    private void userProfileMessage(cUserProfileModel userProfileModel) {
        mainThread.post(() -> callback.onReadUserProfileSucceeded(userProfileModel));
    }

    /* call back on role menu items */
    private void menuItemsMessage(List<cMenuModel> menuModels) {
        mainThread.post(() -> callback.onReadMenuItemsSucceeded(menuModels));
    }

    /* call back on user profile and default menu items */
    private void defaultProfileMessage(List<cMenuModel> menuModels) {
        mainThread.post(() -> callback.onDefaultHomePageSucceeded(menuModels));
    }

    @Override
    public void run() {
        /* establish whether the user has permissions or not */
        this.isPermissionLoaded = cInteractorUtils.isSettingsNonNull(
                organizationServerID, userServerID, entityBITS, entitypermBITS, primaryTeamBIT,
                secondaryTeamBITS, statusBITS);

        /* load user profile and menu items */
        this.homePageRepository.loadHomePage(isPermissionLoaded, sharedPreferenceRepository,
                new iHomePageRepository.iHomePageCallback() {
                    @Override
                    public void onReadUserProfileSucceeded(cUserProfileModel userProfileModel) {
                        userProfileMessage(userProfileModel);
                        //Log.d(TAG, " userProfileModel => " + gson.toJson(userProfileModel));
                    }

                    @Override
                    public void onReadMenuItemsSucceeded(List<cMenuModel> menuModels) {
                        Gson gson = new Gson();
                        Log.d(TAG, " roleMenuItemsMap => " + gson.toJson(menuModels));
                        menuItemsMessage(menuModels);
                        //List<cMenuModel> menuModels;
                        //menuModels = sharedPreferenceRepository.loadMenuItems();
                        //Log.d(TAG, " roleMenuItemsMap => " + gson.toJson(menuModels));
                    }

                    @Override
                    public void onDefaultHomePageSucceeded(List<cMenuModel> menuModels) {
                        defaultProfileMessage(menuModels);
                    }

                    @Override
                    public void onReadHomePageFailed(String msg) {
                        notifyError(msg);
                    }
                });

    }
}


