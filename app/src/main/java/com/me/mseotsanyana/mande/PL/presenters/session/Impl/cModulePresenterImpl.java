package com.me.mseotsanyana.mande.PL.presenters.session.Impl;

import android.util.Log;

import com.google.gson.Gson;
import com.me.mseotsanyana.mande.BLL.executor.iExecutor;
import com.me.mseotsanyana.mande.BLL.executor.iMainThread;
import com.me.mseotsanyana.mande.BLL.interactors.session.module.Impl.cReadModuleInteractorImpl;
import com.me.mseotsanyana.mande.BLL.interactors.session.module.iReadModuleInteractor;
import com.me.mseotsanyana.mande.BLL.interactors.session.permission.Impl.cUpdatePermissionInteractorImpl;
import com.me.mseotsanyana.mande.BLL.interactors.session.permission.iUpdatePermissionInteractor;
import com.me.mseotsanyana.mande.BLL.model.session.cEntityModel;
import com.me.mseotsanyana.mande.BLL.repository.session.iModuleRepository;
import com.me.mseotsanyana.mande.BLL.repository.session.iSharedPreferenceRepository;
import com.me.mseotsanyana.mande.PL.presenters.base.cAbstractPresenter;
import com.me.mseotsanyana.mande.PL.presenters.session.iModulePresenter;
import com.me.mseotsanyana.treeadapterlibrary.cNode;
import com.me.mseotsanyana.treeadapterlibrary.cTreeModel;

import java.util.List;

public class cModulePresenterImpl extends cAbstractPresenter implements iModulePresenter,
        iReadModuleInteractor.Callback, iUpdatePermissionInteractor.Callback{
    //private static final String TAG = cModulePresenterImpl.class.getSimpleName();

    private View view;
    private final iSharedPreferenceRepository sessionManagerRepository;
    private final iModuleRepository moduleRepository;

    public cModulePresenterImpl(iExecutor executor, iMainThread mainThread,
                                View view,
                                iSharedPreferenceRepository sessionManagerRepository,
                                iModuleRepository moduleRepository) {
        super(executor, mainThread);

        this.view = view;
        this.sessionManagerRepository = sessionManagerRepository;
        this.moduleRepository = moduleRepository;
    }

    @Override
    public void onReadModuleFailed(String msg) {
        if(this.view != null) {
            this.view.onReadModuleFailed(msg);
            this.view.hideProgress();
        }
    }

    @Override
    public void onReadModuleSucceeded(List<cTreeModel> treeModels) {
        if(this.view != null) {
            this.view.onReadModuleSucceeded(treeModels);
            this.view.hideProgress();
        }
    }

    @Override
    public void readModules() {
        iReadModuleInteractor readModuleInteractor = new cReadModuleInteractorImpl(
                executor,
                mainThread,
                sessionManagerRepository,
                moduleRepository,
                this);

        view.showProgress();
        readModuleInteractor.execute();
    }

    @Override
    public void onUpdatePermissionFailed(String msg) {
        if(this.view != null) {
            this.view.onUpdatePermissionFailed(msg);
            this.view.hideProgress();
        }
    }

    @Override
    public void onUpdatePermissionSucceeded(String msg) {
        if(this.view != null) {
            this.view.onUpdatePermissionSucceeded(msg);
            this.view.hideProgress();
        }
    }

    @Override
    public void updatePermissions(List<cNode> nodes) {
        iUpdatePermissionInteractor updatePermissionInteractor = new cUpdatePermissionInteractorImpl(
                executor,
                mainThread,
                sessionManagerRepository,
                moduleRepository,
                this,
                nodes);

        view.showProgress();
        updatePermissionInteractor.execute();
    }

    /* ===================================== END PREFERENCE ===================================== */

    /* corresponding view functions */
    @Override
    public void resume() {
        readModules();
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
