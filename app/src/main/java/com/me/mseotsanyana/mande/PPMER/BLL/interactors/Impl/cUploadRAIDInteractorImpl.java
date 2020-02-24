package com.me.mseotsanyana.mande.PPMER.BLL.interactors.Impl;

import com.me.mseotsanyana.mande.PPMER.BLL.executor.iExecutor;
import com.me.mseotsanyana.mande.PPMER.BLL.executor.iMainThread;
import com.me.mseotsanyana.mande.PPMER.BLL.interactors.base.cAbstractInteractor;
import com.me.mseotsanyana.mande.PPMER.BLL.interactors.iUploadRAIDInteractor;
import com.me.mseotsanyana.mande.PPMER.BLL.repository.iUploadRAIDRepository;

public class cUploadRAIDInteractorImpl extends cAbstractInteractor
        implements iUploadRAIDInteractor {
    private Callback callback;
    private iUploadRAIDRepository uploadRAIDRepository;

    public cUploadRAIDInteractorImpl(iExecutor threadExecutor, iMainThread mainThread,
                                     iUploadRAIDRepository uploadRAIDRepository,
                                     Callback callback) {
        super(threadExecutor, mainThread);



        if (uploadRAIDRepository == null || callback == null) {
            throw new IllegalArgumentException("Arguments can not be null!");
        }

        this.uploadRAIDRepository = uploadRAIDRepository;
        this.callback = callback;
    }

    @Override
    public void run() {
        /* create a new CRITERIA object and insert it in the database */
        //uploadLMRepository.deleteCriteria();
        //uploadLMRepository.addCriteriaFromExcel();

        /* notify on the main thread that we have inserted this item */
        mainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onUploadRAIDCompleted();
            }
        });
    }
}
