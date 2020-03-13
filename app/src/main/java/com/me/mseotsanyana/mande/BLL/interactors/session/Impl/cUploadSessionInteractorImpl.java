package com.me.mseotsanyana.mande.BLL.interactors.session.Impl;

import com.me.mseotsanyana.mande.BLL.executor.iExecutor;
import com.me.mseotsanyana.mande.BLL.executor.iMainThread;
import com.me.mseotsanyana.mande.BLL.interactors.base.cAbstractInteractor;
import com.me.mseotsanyana.mande.BLL.interactors.session.iUploadSessionInteractor;
import com.me.mseotsanyana.mande.BLL.repository.session.iUploadSessionRepository;

public class cUploadSessionInteractorImpl extends cAbstractInteractor
        implements iUploadSessionInteractor {
    private Callback callback;
    private iUploadSessionRepository uploadSessionRepository;

    public cUploadSessionInteractorImpl(iExecutor threadExecutor, iMainThread mainThread,
                                        iUploadSessionRepository uploadSessionRepository,
                                        Callback callback) {
        super(threadExecutor, mainThread);



        if (uploadSessionRepository == null || callback == null) {
            throw new IllegalArgumentException("Arguments can not be null!");
        }

        this.uploadSessionRepository = uploadSessionRepository;
        this.callback = callback;
    }

    private void notifyError(){
        mainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onUploadingSessionFailed("Failed to Upload Session Module !!");
            }
        });
    }

    private void postMessage(String msg){
        mainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onUploadingSessionSucceeded(msg);
            }
        });
    }

    @Override
    public void run() {
        /* delete from the database */
        uploadSessionRepository.deleteAddresses();
        uploadSessionRepository.deleteValues();
        uploadSessionRepository.deleteOrganizations();
        uploadSessionRepository.deleteOrgAddresses();
        uploadSessionRepository.deleteBeneficiaries();
        uploadSessionRepository.deleteFunders();
        uploadSessionRepository.deleteImplementingAgencies();

        /* create and insert it in the database */
        if(uploadSessionRepository.addAddressFromExcel()){
            postMessage("Address Entity (in Session Module) Added Successfully!");
        }else {
            notifyError();
        }

        if(uploadSessionRepository.addOrganizationFromExcel()){
            postMessage("Organization Entity (in Session Module) Added Successfully!");
        }else {
            notifyError();
        }

    }
}
