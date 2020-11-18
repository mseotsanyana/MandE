package com.me.mseotsanyana.mande.BLL.interactors.logframe.logframe.Impl;

import com.me.mseotsanyana.mande.BLL.executor.iExecutor;
import com.me.mseotsanyana.mande.BLL.executor.iMainThread;
import com.me.mseotsanyana.mande.BLL.interactors.base.cAbstractInteractor;
import com.me.mseotsanyana.mande.BLL.interactors.logframe.logframe.iCreateSubLogFrameInteractor;
import com.me.mseotsanyana.mande.BLL.repository.logframe.iLogFrameRepository;
import com.me.mseotsanyana.mande.BLL.repository.session.iSessionManagerRepository;
import com.me.mseotsanyana.mande.BLL.model.logframe.cLogFrameModel;

import java.util.Date;

public class cCreateSubLogFrameInteractorImpl extends cAbstractInteractor
        implements iCreateSubLogFrameInteractor {
    private static String TAG = cCreateSubLogFrameInteractorImpl.class.getSimpleName();

    private iLogFrameRepository logSubFrameRepository;
    private Callback callback;
    private long logFrameID;
    private cLogFrameModel logSubFrameModel;

    public cCreateSubLogFrameInteractorImpl(iExecutor threadExecutor, iMainThread mainThread,
                                            iSessionManagerRepository sessionManagerRepository,
                                            iLogFrameRepository logSubFrameRepository, Callback callback,
                                            long logFrameID, cLogFrameModel logSubFrameModel) {
        super(threadExecutor, mainThread);

        if (logSubFrameRepository == null || callback == null) {
            throw new IllegalArgumentException("Arguments can not be null!");
        }

        this.logSubFrameRepository = logSubFrameRepository;
        this.callback = callback;
        this.logFrameID = logFrameID;
        this.logSubFrameModel = logSubFrameModel;

        /* add common attributes */
        this.logSubFrameModel.setOwnerID(sessionManagerRepository.loadUserID());
        this.logSubFrameModel.setOrgID(sessionManagerRepository.loadOrganizationID());
        this.logSubFrameModel.setGroupBITS(sessionManagerRepository.loadPrimaryRoleBITS()|
                sessionManagerRepository.loadSecondaryRoleBITS());
        this.logSubFrameModel.setPermsBITS(sessionManagerRepository.loadDefaultPermsBITS());
        this.logSubFrameModel.setStatusBITS(sessionManagerRepository.loadDefaultStatusBITS());
        this.logSubFrameModel.setCreatedDate(new Date());
        this.logSubFrameModel.setModifiedDate(new Date());
        this.logSubFrameModel.setSyncedDate(new Date());
    }

    /* */
    private void notifyError(String msg) {
        mainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onSubLogFrameCreateFailed(msg);
            }
        });
    }

    /* */
    private void postMessage(String msg) {
        mainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onSubLogFrameCreated(logSubFrameModel, msg);
            }
        });
    }

    @Override
    public void run() {

        /* create a new logFrame object and insert it */
        boolean result = logSubFrameRepository.createSubLogFrameModel(logFrameID, logSubFrameModel);

        if(result){
            postMessage("Successfully added");
        }else {
            notifyError("Failed to add !!");
        }
    }
}
