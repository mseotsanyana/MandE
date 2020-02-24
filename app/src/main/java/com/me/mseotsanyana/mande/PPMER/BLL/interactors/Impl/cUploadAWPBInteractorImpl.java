package com.me.mseotsanyana.mande.PPMER.BLL.interactors.Impl;

import com.me.mseotsanyana.mande.PPMER.BLL.executor.iExecutor;
import com.me.mseotsanyana.mande.PPMER.BLL.executor.iMainThread;
import com.me.mseotsanyana.mande.PPMER.BLL.interactors.base.cAbstractInteractor;
import com.me.mseotsanyana.mande.PPMER.BLL.interactors.iUploadAWPBInteractor;
import com.me.mseotsanyana.mande.PPMER.BLL.repository.iUploadAWPBRepository;

public class cUploadAWPBInteractorImpl extends cAbstractInteractor
        implements iUploadAWPBInteractor {
    private Callback callback;
    private iUploadAWPBRepository uploadAWPBRepository;

    public cUploadAWPBInteractorImpl(iExecutor threadExecutor, iMainThread mainThread,
                                     iUploadAWPBRepository uploadAWPBRepository,
                                     Callback callback) {
        super(threadExecutor, mainThread);



        if (uploadAWPBRepository == null || callback == null) {
            throw new IllegalArgumentException("Arguments can not be null!");
        }

        this.uploadAWPBRepository = uploadAWPBRepository;
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
                callback.onUploadAWPBCompleted();
            }
        });
    }
}
