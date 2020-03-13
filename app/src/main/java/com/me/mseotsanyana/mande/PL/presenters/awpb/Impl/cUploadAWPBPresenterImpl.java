package com.me.mseotsanyana.mande.PL.presenters.awpb.Impl;

import com.me.mseotsanyana.mande.BLL.executor.iExecutor;
import com.me.mseotsanyana.mande.BLL.executor.iMainThread;
import com.me.mseotsanyana.mande.BLL.interactors.awpb.Impl.cUploadAWPBInteractorImpl;
import com.me.mseotsanyana.mande.BLL.interactors.awpb.iUploadAWPBInteractor;
import com.me.mseotsanyana.mande.BLL.repository.awpb.iUploadAWPBRepository;
import com.me.mseotsanyana.mande.PL.presenters.base.cAbstractPresenter;
import com.me.mseotsanyana.mande.PL.presenters.awpb.iUploadAWPBPresenter;

public class cUploadAWPBPresenterImpl extends cAbstractPresenter implements iUploadAWPBPresenter,
        iUploadAWPBInteractor.Callback {
    private static String TAG = cUploadAWPBPresenterImpl.class.getSimpleName();

    private View view;
    private iUploadAWPBRepository uploadAWPBRepository;

    public cUploadAWPBPresenterImpl(iExecutor executor, iMainThread mainThread,
                                    View view, iUploadAWPBRepository uploadAWPBRepository) {
        super(executor, mainThread);

        this.view = view;
        this.uploadAWPBRepository = uploadAWPBRepository;
    }

    @Override
    public void uploadAWPBFromExcel() {

        iUploadAWPBInteractor uploadInteractor = new cUploadAWPBInteractorImpl(
                executor,
                mainThread,
                uploadAWPBRepository,
                this);

        view.showProgress();

        uploadInteractor.execute();
    }

    @Override
    public void onUploadAWPBCompleted(String msg) {
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
