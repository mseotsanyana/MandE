package com.me.mseotsanyana.mande.BLL.interactors.logframe.logframe.Impl;

import com.me.mseotsanyana.mande.BLL.executor.iExecutor;
import com.me.mseotsanyana.mande.BLL.executor.iMainThread;
import com.me.mseotsanyana.mande.BLL.interactors.base.cAbstractInteractor;
import com.me.mseotsanyana.mande.BLL.interactors.logframe.logframe.iCreateLogFrameInteractor;
import com.me.mseotsanyana.mande.BLL.interactors.logframe.logframe.iSyncLogFrameInteractor;
import com.me.mseotsanyana.mande.BLL.repository.logframe.iLogFrameRepository;

public class cSyncLogFrameInteractorImpl extends cAbstractInteractor
        implements iSyncLogFrameInteractor {
    private static String TAG = cSyncLogFrameInteractorImpl.class.getSimpleName();

    private Callback callback;
    private iLogFrameRepository logFrameRepository;

    public cSyncLogFrameInteractorImpl(iExecutor threadExecutor, iMainThread mainThread,
                                       iLogFrameRepository logFrameRepository, Callback callback) {
        super(threadExecutor, mainThread);

        if (logFrameRepository == null || callback == null) {
            throw new IllegalArgumentException("Arguments can not be null!");
        }

        this.logFrameRepository = logFrameRepository;
        this.callback = callback;
    }

    /* */
    private void notifyError(String msg) {
        mainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onLogFrameSyncFailed(msg);
            }
        });
    }

    /* */
    private void postMessage() {
        mainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onLogFrameSynced();
            }
        });
    }

    @Override
    public void run() {
        /* create a new logFrame object and insert it */
        //logFrameRepository.sy();

        postMessage();
    }
}
