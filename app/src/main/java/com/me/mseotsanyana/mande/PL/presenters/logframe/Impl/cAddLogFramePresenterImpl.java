package com.me.mseotsanyana.mande.PL.presenters.logframe.Impl;

import com.me.mseotsanyana.mande.BLL.executor.iExecutor;
import com.me.mseotsanyana.mande.BLL.executor.iMainThread;
import com.me.mseotsanyana.mande.BLL.interactors.logframe.Impl.cAddLogFrameInteractorImpl;
import com.me.mseotsanyana.mande.BLL.interactors.logframe.iAddLogFrameInteractor;
import com.me.mseotsanyana.mande.BLL.repository.logframe.iLogFrameRepository;
import com.me.mseotsanyana.mande.PL.presenters.base.cAbstractPresenter;
import com.me.mseotsanyana.mande.PL.presenters.logframe.iAddLogFramePresenter;

public class cAddLogFramePresenterImpl extends cAbstractPresenter implements iAddLogFramePresenter,
        iAddLogFrameInteractor.Callback {
    private static String TAG = cAddLogFramePresenterImpl.class.getSimpleName();

    private iAddLogFramePresenter.View view;
    private iLogFrameRepository logFrameRepository;

    public cAddLogFramePresenterImpl(iExecutor executor, iMainThread mainThread,
                                     View view, iLogFrameRepository logFrameRepository) {
        super(executor, mainThread);

        this.view = view;
        this.logFrameRepository = logFrameRepository;
    }

    @Override
    public void addLogFramesFromExcel() {
        iAddLogFrameInteractor addLogFrameInteractor = new cAddLogFrameInteractorImpl(
                executor,
                mainThread,
                logFrameRepository,
                this);

        view.showProgress();
        addLogFrameInteractor.execute();
    }

    @Override
    public void onLogFrameAdded() {
        if(this.view != null) {
            this.view.onLogFrameAdded();
            this.view.hideProgress();
        }
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
