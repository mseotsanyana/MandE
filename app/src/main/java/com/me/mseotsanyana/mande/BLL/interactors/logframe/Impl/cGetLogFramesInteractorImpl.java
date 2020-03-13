package com.me.mseotsanyana.mande.BLL.interactors.logframe.Impl;


import com.me.mseotsanyana.mande.BLL.executor.iExecutor;
import com.me.mseotsanyana.mande.BLL.executor.iMainThread;
import com.me.mseotsanyana.mande.BLL.interactors.base.cAbstractInteractor;
import com.me.mseotsanyana.mande.BLL.interactors.logframe.iGetLogFramesInteractor;
import com.me.mseotsanyana.mande.BLL.repository.logframe.iLogFrameRepository;


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
