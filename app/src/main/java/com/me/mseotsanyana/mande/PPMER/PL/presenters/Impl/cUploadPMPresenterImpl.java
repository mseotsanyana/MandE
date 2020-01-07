package com.me.mseotsanyana.mande.PPMER.PL.presenters.Impl;

import com.me.mseotsanyana.mande.PPMER.BLL.executor.iExecutor;
import com.me.mseotsanyana.mande.PPMER.BLL.executor.iMainThread;
import com.me.mseotsanyana.mande.PPMER.BLL.interactors.Impl.cUploadPMInteractorImpl;
import com.me.mseotsanyana.mande.PPMER.BLL.interactors.iUploadPMInteractor;
import com.me.mseotsanyana.mande.PPMER.BLL.repository.iUploadPMRepository;
import com.me.mseotsanyana.mande.PPMER.PL.presenters.base.cAbstractPresenter;
import com.me.mseotsanyana.mande.PPMER.PL.presenters.iUploadPMPresenter;

public class cUploadPMPresenterImpl extends cAbstractPresenter implements iUploadPMPresenter,
        iUploadPMInteractor.Callback {
    private static String TAG = cUploadPMPresenterImpl.class.getSimpleName();

    private View view;
    private iUploadPMRepository uploadPMRepository;

    public cUploadPMPresenterImpl(iExecutor executor, iMainThread mainThread,
                                  View view, iUploadPMRepository uploadPMRepository) {
        super(executor, mainThread);

        this.view = view;
        this.uploadPMRepository = uploadPMRepository;
    }

    @Override
    public void uploadPMFromExcel() {

        iUploadPMInteractor uploadInteractor = new cUploadPMInteractorImpl(
                executor,
                mainThread,
                uploadPMRepository,
                this);

        view.showProgress();

        uploadInteractor.execute();
    }

    @Override
    public void onUploadPMCompleted() {
        if(this.view != null) {
            this.view.onUploadCompleted();
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
