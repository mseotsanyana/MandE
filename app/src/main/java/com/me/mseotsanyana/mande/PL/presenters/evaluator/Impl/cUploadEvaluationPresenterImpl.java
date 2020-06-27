package com.me.mseotsanyana.mande.PL.presenters.evaluator.Impl;

import com.me.mseotsanyana.mande.BLL.executor.iExecutor;
import com.me.mseotsanyana.mande.BLL.executor.iMainThread;
import com.me.mseotsanyana.mande.BLL.interactors.evaluator.Impl.cUploadEvaluationInteractorImpl;
import com.me.mseotsanyana.mande.BLL.interactors.evaluator.iUploadEvaluationInteractor;
import com.me.mseotsanyana.mande.BLL.repository.evaluator.iUploadEvaluationRepository;
import com.me.mseotsanyana.mande.PL.presenters.base.cAbstractPresenter;
import com.me.mseotsanyana.mande.PL.presenters.evaluator.iUploadEvaluationPresenter;

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
    public void onUploadEvaluationCompleted(String msg) {
        if(this.view != null) {
            this.view.onUploadCompleted("Upload Evaluation", msg);
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
            this.view.onUploadCompleted("Upload Evaluation", message);
            this.view.hideProgress();
        }
    }
}
