package com.me.mseotsanyana.mande.PPMER.BLL.interactors.Impl;


import com.me.mseotsanyana.mande.PPMER.BLL.domain.cLogFrameDomain;
import com.me.mseotsanyana.mande.PPMER.BLL.executor.iExecutor;
import com.me.mseotsanyana.mande.PPMER.BLL.executor.iMainThread;
import com.me.mseotsanyana.mande.PPMER.BLL.interactors.base.cAbstractInteractor;
import com.me.mseotsanyana.mande.PPMER.BLL.interactors.iGetLogFramesInteractor;
import com.me.mseotsanyana.mande.PPMER.BLL.repository.iLogFrameRepository;

import java.util.Set;


public class cGetLogFramesInteractorImpl extends cAbstractInteractor
        implements iGetLogFramesInteractor {

    private Callback       callback;
    private iLogFrameRepository logFrameRepository;

    public cGetLogFramesInteractorImpl(iExecutor threadExecutor, iMainThread mainThread,
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
        // retrieve a set logFrames from the database
        //final Set<cLogFrameDomain> logFrameDomainSet =
        //        (Set<cLogFrameDomain>) logFrameRepository.getLogFrameModelSet();


        // Show costs on the main thread
        mainThread.post(new Runnable() {
            @Override
            public void run() {
                //callback.onLogFrameRetrieved(logFrameDomainSet);
            }
        });
    }
}
