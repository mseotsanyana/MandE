package com.me.mseotsanyana.mande.BLL.interactors.logframe.logframe.Impl;

import android.util.Log;

import com.me.mseotsanyana.mande.BLL.executor.iExecutor;
import com.me.mseotsanyana.mande.BLL.executor.iMainThread;
import com.me.mseotsanyana.mande.BLL.interactors.base.cAbstractInteractor;
import com.me.mseotsanyana.mande.BLL.interactors.logframe.logframe.iDeleteLogFrameInteractor;
import com.me.mseotsanyana.mande.BLL.repository.logframe.iLogFrameRepository;

public class cDeleteLogFrameInteractorImpl extends cAbstractInteractor
        implements iDeleteLogFrameInteractor {
    private static String TAG = cDeleteLogFrameInteractorImpl.class.getSimpleName();

    private Callback callback;
    private iLogFrameRepository logFrameRepository;
    private long logFrameID;
    private int position;

    public cDeleteLogFrameInteractorImpl(iExecutor threadExecutor, iMainThread mainThread,
                                         iLogFrameRepository logFrameRepository, Callback callback,
                                         long logFrameID, int position) {
        super(threadExecutor, mainThread);

        if (logFrameRepository == null || callback == null) {
            throw new IllegalArgumentException("Arguments can not be null!");
        }

        this.logFrameRepository = logFrameRepository;
        this.callback = callback;
        this.logFrameID = logFrameID;
        this.position = position;
    }

    /* */
    private void notifyError(String msg) {
        mainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onLogFrameDeleteFailed(msg);
            }
        });
    }

    /* */
    private void postMessage(String msg) {
        mainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onLogFrameDeleted(position, msg);
            }
        });
    }

    @Override
    public void run() {
        /* delete a logframe model */
        boolean result = logFrameRepository.deleteLogFrame(logFrameID);

        if(result){
            postMessage("Successfully deleted");
        }else {
            notifyError("Failed to delete !!");
        }
    }
}
