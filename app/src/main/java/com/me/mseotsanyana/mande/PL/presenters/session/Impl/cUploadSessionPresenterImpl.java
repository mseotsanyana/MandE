package com.me.mseotsanyana.mande.PL.presenters.session.Impl;

import com.me.mseotsanyana.mande.BLL.executor.iExecutor;
import com.me.mseotsanyana.mande.BLL.executor.iMainThread;
import com.me.mseotsanyana.mande.BLL.interactors.logframe.Impl.cUploadLogFrameInteractorImpl;
import com.me.mseotsanyana.mande.BLL.interactors.logframe.iUploadLogFrameInteractor;
import com.me.mseotsanyana.mande.BLL.interactors.session.Impl.cUploadSessionInteractorImpl;
import com.me.mseotsanyana.mande.BLL.interactors.session.iUploadSessionInteractor;
import com.me.mseotsanyana.mande.BLL.repository.logframe.iUploadLogFrameRepository;
import com.me.mseotsanyana.mande.BLL.repository.session.iUploadSessionRepository;
import com.me.mseotsanyana.mande.PL.presenters.base.cAbstractPresenter;
import com.me.mseotsanyana.mande.PL.presenters.logframe.iUploadLogFramePresenter;
import com.me.mseotsanyana.mande.PL.presenters.session.iUploadSessionPresenter;

public class cUploadSessionPresenterImpl extends cAbstractPresenter implements iUploadSessionPresenter,
        iUploadSessionInteractor.Callback {
    private static String TAG = cUploadSessionPresenterImpl.class.getSimpleName();

    private View view;
    private iUploadSessionRepository uploadSessionRepository;

    public cUploadSessionPresenterImpl(iExecutor executor, iMainThread mainThread,
                                       View view, iUploadSessionRepository uploadSessionRepository) {
        super(executor, mainThread);

        this.view = view;
        this.uploadSessionRepository = uploadSessionRepository;
    }

    @Override
    public void uploadSessionFromExcel() {

        iUploadSessionInteractor uploadInteractor = new cUploadSessionInteractorImpl(
                executor,
                mainThread,
                uploadSessionRepository,
                this);

        view.showProgress();

        uploadInteractor.execute();
    }

    @Override
    public void onUploadingSessionFailed(String msg) {
        if(this.view != null) {
            this.view.onUploadCompleted(msg);
            this.view.hideProgress();
        }
    }

    @Override
    public void onUploadingSessionSucceeded(String msg) {
        if(this.view != null) {
            this.view.onUploadCompleted(msg);
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
