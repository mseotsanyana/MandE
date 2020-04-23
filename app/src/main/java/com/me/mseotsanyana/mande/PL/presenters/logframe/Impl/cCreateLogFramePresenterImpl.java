package com.me.mseotsanyana.mande.PL.presenters.logframe.Impl;

import com.me.mseotsanyana.mande.BLL.executor.iExecutor;
import com.me.mseotsanyana.mande.BLL.executor.iMainThread;
import com.me.mseotsanyana.mande.BLL.interactors.logframe.logframe.Impl.cCreateLogFrameInteractorImpl;
import com.me.mseotsanyana.mande.BLL.interactors.logframe.logframe.iCreateLogFrameInteractor;
import com.me.mseotsanyana.mande.BLL.repository.logframe.iLogFrameRepository;
import com.me.mseotsanyana.mande.PL.presenters.base.cAbstractPresenter;
import com.me.mseotsanyana.mande.PL.presenters.logframe.iCreateLogFramePresenter;

public class cCreateLogFramePresenterImpl extends cAbstractPresenter implements iCreateLogFramePresenter,
        iCreateLogFrameInteractor.Callback {
    private static String TAG = cCreateLogFramePresenterImpl.class.getSimpleName();

    private iCreateLogFramePresenter.View view;
    private iLogFrameRepository logFrameRepository;

    public cCreateLogFramePresenterImpl(iExecutor executor, iMainThread mainThread,
                                        View view, iLogFrameRepository logFrameRepository) {
        super(executor, mainThread);

        this.view = view;
        this.logFrameRepository = logFrameRepository;
    }

    @Override
    public void addLogFramesFromExcel() {
        iCreateLogFrameInteractor addLogFrameInteractor = new cCreateLogFrameInteractorImpl(
                executor,
                mainThread,
                logFrameRepository,
                this);

        view.showProgress();
        addLogFrameInteractor.execute();
    }

    @Override
    public void onLogFrameCreated() {
        if(this.view != null) {
            this.view.onLogframeCreated();
            this.view.hideProgress();
        }
    }

    @Override
    public void createLogframe() {

    }

    @Override
    public void resume() {

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
