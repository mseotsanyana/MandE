package com.me.mseotsanyana.mande.BLL.interactors.logframe.logframe.Impl;

import android.util.Log;

import com.me.mseotsanyana.mande.BLL.executor.iExecutor;
import com.me.mseotsanyana.mande.BLL.executor.iMainThread;
import com.me.mseotsanyana.mande.BLL.interactors.base.cAbstractInteractor;
import com.me.mseotsanyana.mande.BLL.interactors.logframe.logframe.iDeleteLogFrameInteractor;
import com.me.mseotsanyana.mande.BLL.interactors.logframe.logframe.iDeleteSubLogFrameInteractor;
import com.me.mseotsanyana.mande.BLL.repository.logframe.iLogFrameRepository;

public class cDeleteSubLogFrameInteractorImpl extends cAbstractInteractor
        implements iDeleteSubLogFrameInteractor {
    private static String TAG = cDeleteSubLogFrameInteractorImpl.class.getSimpleName();

    private Callback callback;
    private iLogFrameRepository logFrameRepository;
    private String logFrameID;
    private int position;

    public cDeleteSubLogFrameInteractorImpl(iExecutor threadExecutor, iMainThread mainThread,
                                            iLogFrameRepository logFrameRepository, Callback callback,
                                            String logFrameID, int position) {
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
                callback.onSubLogFrameDeleteFailed(msg);
            }
        });
    }

    /* */
    private void postMessage(String msg) {
        mainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onSubLogFrameDeleted(position, msg);
            }
        });
    }

    @Override
    public void run() {
        /* delete a logframe model */
        boolean result = logFrameRepository.deleteLogFrame(logFrameID);
        Log.d(TAG, "============"+result);
        if(result){
            postMessage("Successfully deleted a LogFrame");
        }else {
            notifyError("Failed to delete LogFrame !!");
        }
    }
}
