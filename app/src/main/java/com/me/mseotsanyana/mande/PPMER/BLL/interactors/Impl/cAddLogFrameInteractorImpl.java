package com.me.mseotsanyana.mande.PPMER.BLL.interactors.Impl;

import android.util.Log;

import com.me.mseotsanyana.mande.PPMER.BLL.executor.iExecutor;
import com.me.mseotsanyana.mande.PPMER.BLL.executor.iMainThread;
import com.me.mseotsanyana.mande.PPMER.BLL.interactors.base.cAbstractInteractor;
import com.me.mseotsanyana.mande.PPMER.BLL.interactors.iAddLogFrameInteractor;
import com.me.mseotsanyana.mande.PPMER.BLL.repository.iLogFrameRepository;

public class cAddLogFrameInteractorImpl extends cAbstractInteractor
        implements iAddLogFrameInteractor {
    private static String TAG = cAddLogFrameInteractorImpl.class.getSimpleName();

    private Callback callback;
    private iLogFrameRepository logFrameRepository;

    public cAddLogFrameInteractorImpl(iExecutor threadExecutor, iMainThread mainThread,
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
                callback.onLogFrameAdded();
            }
        });
    }
}
