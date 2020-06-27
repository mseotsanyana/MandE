package com.me.mseotsanyana.mande.BLL.interactors.raid.Impl;

import com.me.mseotsanyana.mande.BLL.executor.iExecutor;
import com.me.mseotsanyana.mande.BLL.executor.iMainThread;
import com.me.mseotsanyana.mande.BLL.interactors.base.cAbstractInteractor;
import com.me.mseotsanyana.mande.BLL.interactors.raid.iUploadRAIDInteractor;
import com.me.mseotsanyana.mande.BLL.repository.raid.iUploadRAIDRepository;

public class cUploadRAIDInteractorImpl extends cAbstractInteractor
        implements iUploadRAIDInteractor {
    private Callback callback;
    private iUploadRAIDRepository uploadRAIDRepository;

    public cUploadRAIDInteractorImpl(iExecutor threadExecutor, iMainThread mainThread,
                                     iUploadRAIDRepository uploadRAIDRepository,
                                     Callback callback) {
        super(threadExecutor, mainThread);

        if (uploadRAIDRepository == null || callback == null) {
            throw new IllegalArgumentException("Arguments can not be null!");
        }

        this.uploadRAIDRepository = uploadRAIDRepository;
        this.callback = callback;
    }

    /* notify on the main thread */
    private void notifyError(String msg){
        mainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onUploadRAIDCompleted(msg);
            }
        });
    }

    /* notify on the main thread */
    private void postMessage(String msg){
        mainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onUploadRAIDCompleted(msg);
            }
        });
    }
    @Override
    public void run() {

        /* delete all raid module records */

        uploadRAIDRepository.deleteRegisters();
        uploadRAIDRepository.deleteRiskLikelihoods();
        uploadRAIDRepository.deleteRiskLikelihoodSets();
        uploadRAIDRepository.deleteRiskImpacts();
        uploadRAIDRepository.deleteRiskImpactSets();
        uploadRAIDRepository.deleteRiskCriteria();
        uploadRAIDRepository.deleteRiskCriteriaSets();
        uploadRAIDRepository.deleteRisks();
        uploadRAIDRepository.deleteRiskConsequences();
        uploadRAIDRepository.deleteRiskRootCauses();
        uploadRAIDRepository.deleteRiskActionTypes();
        uploadRAIDRepository.deleteRiskActions();
        uploadRAIDRepository.deleteRiskCurrentControls();
        uploadRAIDRepository.deleteRiskAdditionalControls();

        /* upload all raid module records */

        if(uploadRAIDRepository.addRegisterFromExcel()){
            postMessage("Register Entity Added Successfully!");
        }else {
            notifyError("Failed to Add Register Entity");
        }

        if(uploadRAIDRepository.addRiskLikelihoodFromExcel()){
            postMessage("RiskLikelihood Entity Added Successfully!");
        }else {
            notifyError("Failed to Add Frequency RiskLikelihood");
        }

        if(uploadRAIDRepository.addRiskImpactFromExcel()){
            postMessage("RiskImpact Entity Added Successfully!");
        }else {
            notifyError("Failed to Add RiskImpact Entity");
        }

        if(uploadRAIDRepository.addRiskCriteriaFromExcel()){
            postMessage("RiskCriteria Entity Added Successfully!");
        }else {
            notifyError("Failed to Add RiskCriteria Entity");
        }

        if(uploadRAIDRepository.addRiskFromExcel()){
            postMessage("Risk Entity Added Successfully!");
        }else {
            notifyError("Failed to Add Risk Entity");
        }

        if(uploadRAIDRepository.addRiskActionTypeFromExcel()){
            postMessage("RiskActionType Entity Added Successfully!");
        }else {
            notifyError("Failed to Add RiskActionType Entity");
        }

        if(uploadRAIDRepository.addRiskActionFromExcel()){
            postMessage("RiskAction Entity Added Successfully!");
        }else {
            notifyError("Failed to Add RiskAction Entity");
        }
    }
}
