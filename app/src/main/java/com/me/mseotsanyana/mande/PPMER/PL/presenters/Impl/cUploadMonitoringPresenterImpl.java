package com.me.mseotsanyana.mande.PPMER.PL.presenters.Impl;

import com.me.mseotsanyana.mande.PPMER.BLL.executor.iExecutor;
import com.me.mseotsanyana.mande.PPMER.BLL.executor.iMainThread;
import com.me.mseotsanyana.mande.PPMER.BLL.interactors.Impl.cUploadLogFrameInteractorImpl;
import com.me.mseotsanyana.mande.PPMER.BLL.interactors.Impl.cUploadMonitoringInteractorImpl;
import com.me.mseotsanyana.mande.PPMER.BLL.interactors.iUploadLogFrameInteractor;
import com.me.mseotsanyana.mande.PPMER.BLL.interactors.iUploadMonitoringInteractor;
import com.me.mseotsanyana.mande.PPMER.BLL.repository.iUploadLogFrameRepository;
import com.me.mseotsanyana.mande.PPMER.BLL.repository.iUploadMonitoringRepository;
import com.me.mseotsanyana.mande.PPMER.PL.presenters.base.cAbstractPresenter;
import com.me.mseotsanyana.mande.PPMER.PL.presenters.iUploadLogFramePresenter;
import com.me.mseotsanyana.mande.PPMER.PL.presenters.iUploadMonitoringPresenter;

public class cUploadMonitoringPresenterImpl extends cAbstractPresenter implements iUploadMonitoringPresenter,
        iUploadMonitoringInteractor.Callback {
    private static String TAG = cUploadMonitoringPresenterImpl.class.getSimpleName();

    private View view;
    private iUploadMonitoringRepository uploadMonitoringRepository;

    public cUploadMonitoringPresenterImpl(iExecutor executor, iMainThread mainThread,
                                          View view, iUploadMonitoringRepository uploadMonitoringRepository) {
        super(executor, mainThread);

        this.view = view;
        this.uploadMonitoringRepository = uploadMonitoringRepository;
    }

    @Override
    public void uploadMonitoringFromExcel() {

        iUploadMonitoringInteractor uploadInteractor = new cUploadMonitoringInteractorImpl(
                executor,
                mainThread,
                uploadMonitoringRepository,
                this);

        view.showProgress();

        uploadInteractor.execute();
    }

    @Override
    public void onUploadMonitoringCompleted() {
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
