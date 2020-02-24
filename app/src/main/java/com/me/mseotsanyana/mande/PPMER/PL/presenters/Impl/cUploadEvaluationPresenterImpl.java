package com.me.mseotsanyana.mande.PPMER.PL.presenters.Impl;

import com.me.mseotsanyana.mande.PPMER.BLL.executor.iExecutor;
import com.me.mseotsanyana.mande.PPMER.BLL.executor.iMainThread;
import com.me.mseotsanyana.mande.PPMER.BLL.interactors.Impl.cUploadEvaluationInteractorImpl;
import com.me.mseotsanyana.mande.PPMER.BLL.interactors.iUploadEvaluationInteractor;
import com.me.mseotsanyana.mande.PPMER.BLL.repository.iUploadEvaluationRepository;
import com.me.mseotsanyana.mande.PPMER.PL.presenters.base.cAbstractPresenter;
import com.me.mseotsanyana.mande.PPMER.PL.presenters.iUploadEvaluationPresenter;

public class cUploadEvaluationPresenterImpl extends cAbstractPresenter implements iUploadEvaluationPresenter,
        iUploadEvaluationInteractor.Callback {
    private static String TAG = cUploadEvaluationPresenterImpl.class.getSimpleName();

    private View view;
    private iUploadEvaluationRepository uploadEvaluationRepository;

    public cUploadEvaluationPresenterImpl(iExecutor executor, iMainThread mainThread,
                                          View view, iUploadEvaluationRepository uploadEvaluationRepository) {
        super(executor, mainThread);

        this.view = view;
        this.uploadEvaluationRepository = uploadEvaluationRepository;
    }

    @Override
    public void uploadEvaluationFromExcel() {

        iUploadEvaluationInteractor uploadInteractor = new cUploadEvaluationInteractorImpl(
                executor,
                mainThread,
                uploadEvaluationRepository,
                this);

        view.showProgress();

        uploadInteractor.execute();
    }

    @Override
    public void onUploadEvaluationCompleted() {
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
