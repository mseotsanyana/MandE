package com.me.mseotsanyana.mande.PPMER.PL.presenters.Impl;

import com.me.mseotsanyana.mande.PPMER.BLL.executor.iExecutor;
import com.me.mseotsanyana.mande.PPMER.BLL.executor.iMainThread;
import com.me.mseotsanyana.mande.PPMER.BLL.interactors.Impl.cUploadLogFrameInteractorImpl;
import com.me.mseotsanyana.mande.PPMER.BLL.interactors.iUploadLogFrameInteractor;
import com.me.mseotsanyana.mande.PPMER.BLL.repository.iUploadLogFrameRepository;
import com.me.mseotsanyana.mande.PPMER.PL.presenters.base.cAbstractPresenter;
import com.me.mseotsanyana.mande.PPMER.PL.presenters.iUploadLogFramePresenter;

public class cUploadLogFramePresenterImpl extends cAbstractPresenter implements iUploadLogFramePresenter,
        iUploadLogFrameInteractor.Callback {
    private static String TAG = cUploadLogFramePresenterImpl.class.getSimpleName();

    private View view;
    private iUploadLogFrameRepository uploadLogFrameRepository;

    public cUploadLogFramePresenterImpl(iExecutor executor, iMainThread mainThread,
                                        View view, iUploadLogFrameRepository uploadLogFrameRepository) {
        super(executor, mainThread);

        this.view = view;
        this.uploadLogFrameRepository = uploadLogFrameRepository;
    }

    @Override
    public void uploadLogFrameFromExcel() {

        iUploadLogFrameInteractor uploadInteractor = new cUploadLogFrameInteractorImpl(
                executor,
                mainThread,
                uploadLogFrameRepository,
                this);

        view.showProgress();

        uploadInteractor.execute();
    }

    @Override
    public void onUploadLogFrameCompleted() {
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
