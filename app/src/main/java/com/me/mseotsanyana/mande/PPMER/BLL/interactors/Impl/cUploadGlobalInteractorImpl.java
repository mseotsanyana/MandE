package com.me.mseotsanyana.mande.PPMER.BLL.interactors.Impl;

import com.me.mseotsanyana.mande.PPMER.BLL.executor.iExecutor;
import com.me.mseotsanyana.mande.PPMER.BLL.executor.iMainThread;
import com.me.mseotsanyana.mande.PPMER.BLL.interactors.base.cAbstractInteractor;
import com.me.mseotsanyana.mande.PPMER.BLL.interactors.iUploadGlobalInteractor;
import com.me.mseotsanyana.mande.PPMER.BLL.repository.iUploadGlobalRepository;

public class cUploadGlobalInteractorImpl extends cAbstractInteractor
        implements iUploadGlobalInteractor {
    private Callback callback;
    private iUploadGlobalRepository uploadGlobalRepository;

    public cUploadGlobalInteractorImpl(iExecutor threadExecutor, iMainThread mainThread,
                                       iUploadGlobalRepository uploadGlobalRepository,
                                       Callback callback) {
        super(threadExecutor, mainThread);



        if (uploadGlobalRepository == null || callback == null) {
            throw new IllegalArgumentException("Arguments can not be null!");
        }

        this.uploadGlobalRepository = uploadGlobalRepository;
        this.callback = callback;
    }

    @Override
    public void run() {
        /* create a new objects and insert it in the database */
        uploadGlobalRepository.deleteFrequencies();
        uploadGlobalRepository.addFrequencyFromExcel();

        /* notify on the main thread that we have inserted this item */
        mainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onUploadGlobalCompleted();
            }
        });
    }
}
