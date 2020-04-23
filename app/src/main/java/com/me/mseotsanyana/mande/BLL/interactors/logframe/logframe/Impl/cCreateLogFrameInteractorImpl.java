package com.me.mseotsanyana.mande.BLL.interactors.logframe.logframe.Impl;

import com.me.mseotsanyana.mande.BLL.executor.iExecutor;
import com.me.mseotsanyana.mande.BLL.executor.iMainThread;
import com.me.mseotsanyana.mande.BLL.interactors.base.cAbstractInteractor;
import com.me.mseotsanyana.mande.BLL.interactors.logframe.logframe.iCreateLogFrameInteractor;
import com.me.mseotsanyana.mande.BLL.repository.logframe.iLogFrameRepository;

public class cCreateLogFrameInteractorImpl extends cAbstractInteractor
        implements iCreateLogFrameInteractor {
    private static String TAG = cCreateLogFrameInteractorImpl.class.getSimpleName();

    private Callback callback;
    private iLogFrameRepository logFrameRepository;

    public cCreateLogFrameInteractorImpl(iExecutor threadExecutor, iMainThread mainThread,
                                         iLogFrameRepository logFrameRepository, Callback callback) {
        super(threadExecutor, mainThread);

        if (logFrameRepository == null || callback == null) {
            throw new IllegalArgumentException("Arguments can not be null!");
        }

        this.logFrameRepository = logFrameRepository;
        this.callback = callback;
    }

    @Override
    public void run() {
        /* create a new logFrame object and insert it */
        logFrameRepository.addLogFrameFromExcel();

        /* notify on the main thread that we have inserted this item */
        mainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onLogFrameCreated();
            }
        });
    }
}
