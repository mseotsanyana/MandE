package com.me.mseotsanyana.mande.PL.presenters.session.Impl;

import com.me.mseotsanyana.mande.BLL.executor.iExecutor;
import com.me.mseotsanyana.mande.BLL.executor.iMainThread;

import com.me.mseotsanyana.mande.BLL.interactors.session.session.Impl.cHomePageInteractorImpl;
import com.me.mseotsanyana.mande.BLL.interactors.session.session.iHomePageInteractor;
import com.me.mseotsanyana.mande.BLL.model.session.cMenuModel;
import com.me.mseotsanyana.mande.BLL.model.session.cUserProfileModel;
import com.me.mseotsanyana.mande.BLL.repository.session.iHomePageRepository;
import com.me.mseotsanyana.mande.BLL.repository.session.iSharedPreferenceRepository;
import com.me.mseotsanyana.mande.PL.presenters.base.cAbstractPresenter;
import com.me.mseotsanyana.mande.PL.presenters.session.iHomePagePresenter;

import java.util.List;

public class cHomePagePresenterImpl extends cAbstractPresenter implements iHomePagePresenter,
        iHomePageInteractor.Callback{

    private View view;
    private final iSharedPreferenceRepository sharedPreferenceRepository;
    private final iHomePageRepository homePageRepository;

    public cHomePagePresenterImpl(iExecutor executor, iMainThread mainThread, View view,
                                  iSharedPreferenceRepository sharedPreferenceRepository,
                                  iHomePageRepository homePageRepository) {
        super(executor, mainThread);

        this.view = view;
        this.sharedPreferenceRepository = sharedPreferenceRepository;
        this.homePageRepository = homePageRepository;
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
    public void onDefaultHomePageSucceeded(List<cMenuModel> menuModels) {
        if(this.view != null) {
            this.view.onDefaultHomePageSucceeded(menuModels);
        }
    }

    @Override
    public void loadHomePage() {
        iHomePageInteractor homePageInteractor = new cHomePageInteractorImpl(
                executor,
                mainThread,this,
                sharedPreferenceRepository,
                homePageRepository);

        view.showProgress();
        homePageInteractor.execute();
    }

    /* general presentation methods */

    @Override
    public void resume() {
        loadHomePage();
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
