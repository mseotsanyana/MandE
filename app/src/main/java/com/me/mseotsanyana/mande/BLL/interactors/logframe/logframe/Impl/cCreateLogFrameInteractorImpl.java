package com.me.mseotsanyana.mande.BLL.interactors.logframe.logframe.Impl;

import com.me.mseotsanyana.mande.BLL.executor.iExecutor;
import com.me.mseotsanyana.mande.BLL.executor.iMainThread;
import com.me.mseotsanyana.mande.BLL.interactors.base.cAbstractInteractor;
import com.me.mseotsanyana.mande.BLL.interactors.logframe.logframe.iCreateLogFrameInteractor;
import com.me.mseotsanyana.mande.BLL.repository.logframe.iLogFrameRepository;
import com.me.mseotsanyana.mande.BLL.model.logframe.cLogFrameModel;
import com.me.mseotsanyana.mande.BLL.repository.session.iSharedPreferenceRepository;

import java.util.Date;

public class cCreateLogFrameInteractorImpl extends cAbstractInteractor
        implements iCreateLogFrameInteractor {
    private static String TAG = cCreateLogFrameInteractorImpl.class.getSimpleName();

    private iLogFrameRepository logFrameRepository;
    private Callback callback;
    private cLogFrameModel logFrameModel;

    public cCreateLogFrameInteractorImpl(iExecutor threadExecutor, iMainThread mainThread,
                                         iSharedPreferenceRepository sessionManagerRepository,
                                         iLogFrameRepository logFrameRepository, Callback callback,
                                         cLogFrameModel logFrameModel) {
        super(threadExecutor, mainThread);

        if (logFrameRepository == null || callback == null) {
            throw new IllegalArgumentException("Arguments can not be null!");
        }

        this.logFrameRepository = logFrameRepository;
        this.callback = callback;
        this.logFrameModel = logFrameModel;

//        /* add common attributes */
//        this.logFrameModel.setOwnerID(sessionManagerRepository.loadUserID());
//        this.logFrameModel.setOrgID(sessionManagerRepository.loadOrganizationID());
//        this.logFrameModel.setGroupBITS(sessionManagerRepository.loadPrimaryRoleBITS()|
//                sessionManagerRepository.loadSecondaryRoleBITS());
//        this.logFrameModel.setPermsBITS(sessionManagerRepository.loadDefaultPermsBITS());
//        this.logFrameModel.setStatusBITS(sessionManagerRepository.loadDefaultStatusBITS());
        this.logFrameModel.setCreatedDate(new Date());
        this.logFrameModel.setModifiedDate(new Date());
        //this.logFrameModel.setSyncedDate(new Date());
    }

    /* */
    private void notifyError(String msg) {
        mainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onLogFrameCreateFailed(msg);
            }
        });
    }

    /* */
    private void postMessage(String msg) {
        mainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onLogFrameCreated(logFrameModel, msg);
            }
        });
    }

    @Override
    public void run() {

        /* create a new logFrame object and insert it */
        boolean result = logFrameRepository.createLogFrameModel(logFrameModel);

        if(result){
            postMessage("Successfully added");
        }else {
            notifyError("Failed to add !!");
        }
    }
}
