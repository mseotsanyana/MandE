package com.me.mseotsanyana.mande.PPMER.PL.presenters.Impl;

import com.me.mseotsanyana.mande.PPMER.BLL.executor.iExecutor;
import com.me.mseotsanyana.mande.PPMER.BLL.executor.iMainThread;
import com.me.mseotsanyana.mande.PPMER.BLL.interactors.Impl.cUploadRAIDInteractorImpl;
import com.me.mseotsanyana.mande.PPMER.BLL.interactors.iUploadRAIDInteractor;
import com.me.mseotsanyana.mande.PPMER.BLL.repository.iUploadRAIDRepository;
import com.me.mseotsanyana.mande.PPMER.PL.presenters.base.cAbstractPresenter;
import com.me.mseotsanyana.mande.PPMER.PL.presenters.iUploadRAIDPresenter;

public class cUploadRAIDPresenterImpl extends cAbstractPresenter implements iUploadRAIDPresenter,
        iUploadRAIDInteractor.Callback {
    private static String TAG = cUploadRAIDPresenterImpl.class.getSimpleName();

    private View view;
    private iUploadRAIDRepository uploadRAIDRepository;

    public cUploadRAIDPresenterImpl(iExecutor executor, iMainThread mainThread,
                                    View view, iUploadRAIDRepository uploadRAIDRepository) {
        super(executor, mainThread);

        this.view = view;
        this.uploadRAIDRepository = uploadRAIDRepository;
    }

    @Override
    public void uploadRAIDFromExcel() {

        iUploadRAIDInteractor uploadInteractor = new cUploadRAIDInteractorImpl(
                executor,
                mainThread,
                uploadRAIDRepository,
                this);

        view.showProgress();

        uploadInteractor.execute();
    }

    @Override
    public void onUploadRAIDCompleted() {
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
