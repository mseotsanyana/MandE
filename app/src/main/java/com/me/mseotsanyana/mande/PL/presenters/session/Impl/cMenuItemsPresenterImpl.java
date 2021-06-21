package com.me.mseotsanyana.mande.PL.presenters.session.Impl;

import com.me.mseotsanyana.mande.BLL.executor.iExecutor;
import com.me.mseotsanyana.mande.BLL.executor.iMainThread;
import com.me.mseotsanyana.mande.BLL.interactors.session.menu.Impl.cReadMenuItemsInteractorImpl;
import com.me.mseotsanyana.mande.BLL.interactors.session.menu.iReadMenuItemsInteractor;
import com.me.mseotsanyana.mande.BLL.model.session.cMenuModel;
import com.me.mseotsanyana.mande.BLL.repository.session.iMenuRepository;
import com.me.mseotsanyana.mande.BLL.repository.session.iSharedPreferenceRepository;
import com.me.mseotsanyana.mande.PL.presenters.base.cAbstractPresenter;
import com.me.mseotsanyana.mande.PL.presenters.session.iMenuItemsPresenter;

import java.util.List;

public class cMenuItemsPresenterImpl extends cAbstractPresenter implements iMenuItemsPresenter,
        iReadMenuItemsInteractor.Callback{
    private static String TAG = cMenuItemsPresenterImpl.class.getSimpleName();

    private View view;
    private final iSharedPreferenceRepository sessionManagerRepository;
    private final iMenuRepository menuRepository;

    public cMenuItemsPresenterImpl(iExecutor executor, iMainThread mainThread,
                                   View view,
                                   iSharedPreferenceRepository sessionManagerRepository,
                                   iMenuRepository menuRepository) {
        super(executor, mainThread);

        this.view = view;
        this.sessionManagerRepository = sessionManagerRepository;
        this.menuRepository = menuRepository;
    }

    @Override
    public void onReadMenuItemsFailed(String msg) {
        if(this.view != null) {
            this.view.onReadMenuItemsFailed(msg);
        }
    }

    @Override
    public void onReadMenuItemsSucceeded(List<cMenuModel> menuModels) {
        if(this.view != null) {
            this.view.onReadMenuItemsSucceeded(menuModels);
        }
    }

    @Override
    public void readMenuItems() {
        iReadMenuItemsInteractor readMenuItemsInteractor = new cReadMenuItemsInteractorImpl(
                executor,
                mainThread,
                sessionManagerRepository,
                menuRepository,
                this);

        view.showProgress();
        readMenuItemsInteractor.execute();
    }

    /* ===================================== END PREFERENCE ===================================== */

    /* corresponding view functions */
    @Override
    public void resume() {
        readMenuItems();
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
