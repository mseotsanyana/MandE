package com.me.mseotsanyana.mande.PL.presenters.session.Impl;

import com.me.mseotsanyana.mande.BLL.executor.iExecutor;
import com.me.mseotsanyana.mande.BLL.executor.iMainThread;

import com.me.mseotsanyana.mande.BLL.interactors.session.session.Impl.cLoadProfileSettingsInteractorImpl;
import com.me.mseotsanyana.mande.BLL.interactors.session.session.iHomePageInteractor;
import com.me.mseotsanyana.mande.BLL.model.session.cMenuModel;
import com.me.mseotsanyana.mande.BLL.model.session.cUserProfileModel;
import com.me.mseotsanyana.mande.BLL.repository.session.iUserProfileAndMenuItemsRepository;
import com.me.mseotsanyana.mande.BLL.repository.session.iSharedPreferenceRepository;
import com.me.mseotsanyana.mande.PL.presenters.base.cAbstractPresenter;
import com.me.mseotsanyana.mande.PL.presenters.session.iHomePagePresenter;

import java.util.List;

public class cHomePagePresenterImpl extends cAbstractPresenter implements iHomePagePresenter,
        iHomePageInteractor.Callback{

    private View view;
    private final iSharedPreferenceRepository sharedPreferenceRepository;
    private final iUserProfileAndMenuItemsRepository userProfileAndMenuItemsRepository;

    public cHomePagePresenterImpl(iExecutor executor, iMainThread mainThread, View view,
                                  iSharedPreferenceRepository sharedPreferenceRepository,
                                  iUserProfileAndMenuItemsRepository userProfileAndMenuItemsRepository) {
        super(executor, mainThread);

        this.view = view;
        this.sharedPreferenceRepository = sharedPreferenceRepository;
        this.userProfileAndMenuItemsRepository = userProfileAndMenuItemsRepository;
    }

    @Override
    public void onReadHomePageFailed(String msg) {
        if(this.view != null) {
            this.view.onReadHomePageFailed(msg);
        }
    }

    @Override
    public void onReadUserProfileSucceeded(cUserProfileModel userProfileModel) {
        if(this.view != null) {
            this.view.onReadUserProfileSucceeded(userProfileModel);
        }
    }

    @Override
    public void onReadMenuItemsSucceeded(List<cMenuModel> menuModels) {
        if(this.view != null) {
            this.view.onReadMenuItemsSucceeded(menuModels);
        }
    }

    @Override
    public void onDefaultHomePageSucceeded(cUserProfileModel userProfileModel,
                                          List<cMenuModel> menuModels) {
        if(this.view != null) {
            this.view.onDefaultHomePageSucceeded(userProfileModel, menuModels);
        }
    }

    @Override
    public void updateHomePage() {
        iHomePageInteractor updateHomePageInteract = new cLoadProfileSettingsInteractorImpl(
                executor,
                mainThread,this,
                sharedPreferenceRepository,
                userProfileAndMenuItemsRepository
                );

        view.showProgress();
        updateHomePageInteract.execute();
    }

    /* general presentation methods */

    @Override
    public void resume() {
        updateHomePage();
    }

    @Override
    public void pause() {

    }

    @Override
    public void stop() {
        if(this.view != null){
            this.view.hideProgress();
        }
    }

    @Override
    public void destroy() {
        this.view = null;
    }

    @Override
    public void onError(String message) {

    }
}