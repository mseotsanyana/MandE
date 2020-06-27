package com.me.mseotsanyana.mande.PL.presenters.monitor.Impl;

import com.me.mseotsanyana.mande.BLL.executor.iExecutor;
import com.me.mseotsanyana.mande.BLL.executor.iMainThread;
import com.me.mseotsanyana.mande.BLL.interactors.monitor.Impl.cUploadMonitoringInteractorImpl;
import com.me.mseotsanyana.mande.BLL.interactors.monitor.iUploadMonitoringInteractor;
import com.me.mseotsanyana.mande.BLL.repository.monitor.iUploadMonitoringRepository;
import com.me.mseotsanyana.mande.PL.presenters.base.cAbstractPresenter;
import com.me.mseotsanyana.mande.PL.presenters.monitor.iUploadMonitoringPresenter;

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
    public void onUploadMonitoringCompleted(String msg) {
        if(this.view != null) {
            this.view.onUploadCompleted("Upload Monitoring", msg);
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
        if(this.view != null) {
            this.view.onUploadCompleted("Upload Monitoring", message);
            this.view.hideProgress();
        }
    }
}
