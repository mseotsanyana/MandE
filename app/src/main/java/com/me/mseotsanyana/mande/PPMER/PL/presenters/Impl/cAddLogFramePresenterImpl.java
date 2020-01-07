package com.me.mseotsanyana.mande.PPMER.PL.presenters.Impl;

import android.util.Log;

import com.me.mseotsanyana.mande.PPMER.BLL.executor.iExecutor;
import com.me.mseotsanyana.mande.PPMER.BLL.executor.iMainThread;
import com.me.mseotsanyana.mande.PPMER.BLL.interactors.Impl.cAddLogFrameInteractorImpl;
import com.me.mseotsanyana.mande.PPMER.BLL.interactors.iAddLogFrameInteractor;
import com.me.mseotsanyana.mande.PPMER.BLL.repository.iLogFrameRepository;
import com.me.mseotsanyana.mande.PPMER.PL.presenters.base.cAbstractPresenter;
import com.me.mseotsanyana.mande.PPMER.PL.presenters.iAddLogFramePresenter;

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
