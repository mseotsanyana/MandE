package com.me.mseotsanyana.mande.PL.presenters.session.Impl;

import com.me.mseotsanyana.mande.BLL.executor.iExecutor;
import com.me.mseotsanyana.mande.BLL.executor.iMainThread;
import com.me.mseotsanyana.mande.BLL.interactors.session.menu.Impl.cReadMenuInteractorImpl;
import com.me.mseotsanyana.mande.BLL.interactors.session.menu.iReadMenuInteractor;
import com.me.mseotsanyana.mande.BLL.repository.session.iMenuRepository;
import com.me.mseotsanyana.mande.BLL.repository.session.iSharedPreferenceRepository;
import com.me.mseotsanyana.mande.PL.presenters.base.cAbstractPresenter;
import com.me.mseotsanyana.mande.PL.presenters.session.iMenuPresenter;
import com.me.mseotsanyana.treeadapterlibrary.cTreeModel;

import java.util.List;

public class cMenuPresenterImpl extends cAbstractPresenter implements iMenuPresenter,
        iReadMenuInteractor.Callback{
    //private static final String TAG = cMenuPresenterImpl.class.getSimpleName();

    private View view;
    private final iSharedPreferenceRepository sessionManagerRepository;
    private final iMenuRepository menuRepository;

    public cMenuPresenterImpl(iExecutor executor, iMainThread mainThread,
                              View view,
                              iSharedPreferenceRepository sessionManagerRepository,
                              iMenuRepository menuRepository) {
        super(executor, mainThread);

        this.view = view;
        this.sessionManagerRepository = sessionManagerRepository;
        this.menuRepository = menuRepository;
    }

    @Override
    public void onReadMenuFailed(String msg) {
        if(this.view != null) {
            this.view.onReadMenuFailed(msg);
            this.view.hideProgress();
        }
    }

    @Override
    public void onReadMenuSucceeded(List<cTreeModel> treeModels) {
        if(this.view != null) {
            this.view.onReadMenuSucceeded(treeModels);
            this.view.hideProgress();
        }
    }

    @Override
    public void readMenuItems() {
        iReadMenuInteractor readMenuInteractor = new cReadMenuInteractorImpl(
                executor,
                mainThread,
                sessionManagerRepository,
                menuRepository,
                this);

        view.showProgress();
        readMenuInteractor.execute();
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
