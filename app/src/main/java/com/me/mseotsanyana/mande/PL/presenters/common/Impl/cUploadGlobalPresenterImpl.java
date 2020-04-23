package com.me.mseotsanyana.mande.PL.presenters.common.Impl;

import com.me.mseotsanyana.mande.BLL.executor.iExecutor;
import com.me.mseotsanyana.mande.BLL.executor.iMainThread;
import com.me.mseotsanyana.mande.BLL.interactors.common.Impl.cUploadGlobalInteractorImpl;
import com.me.mseotsanyana.mande.BLL.interactors.common.iUploadGlobalInteractor;
import com.me.mseotsanyana.mande.BLL.repository.common.iUploadGlobalRepository;
import com.me.mseotsanyana.mande.PL.presenters.base.cAbstractPresenter;
import com.me.mseotsanyana.mande.PL.presenters.common.iUploadGlobalPresenter;

public class cUploadGlobalPresenterImpl extends cAbstractPresenter implements iUploadGlobalPresenter,
        iUploadGlobalInteractor.Callback {
    private static String TAG = cUploadGlobalPresenterImpl.class.getSimpleName();

    private View view;
    private iUploadGlobalRepository uploadGlobalRepository;

    public cUploadGlobalPresenterImpl(iExecutor executor, iMainThread mainThread,
                                      View view, iUploadGlobalRepository uploadGlobalRepository) {
        super(executor, mainThread);

        this.view = view;
        this.uploadGlobalRepository = uploadGlobalRepository;
    }

    @Override
    public void uploadGlobalFromExcel() {

        iUploadGlobalInteractor uploadInteractor = new cUploadGlobalInteractorImpl(
                executor,
                mainThread,
                uploadGlobalRepository,
                this);

        view.showProgress();

        uploadInteractor.execute();
    }

    @Override
    public void onUploadGlobalCompleted(String msg) {
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
