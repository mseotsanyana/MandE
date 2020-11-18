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
        uploadRAIDRepository.deletePIMs();
        uploadRAIDRepository.deleteRAIDLIKELIHOODs();
        uploadRAIDRepository.deleteRAIDIMPACTs();
        uploadRAIDRepository.deleteROBOTs();
        uploadRAIDRepository.deletePIMSETs();

        uploadRAIDRepository.deleteRiskRegisters();
        uploadRAIDRepository.deleteAssumptionRegisters();
        uploadRAIDRepository.deleteIssueRegisters();
        uploadRAIDRepository.deleteDependencyRegisters();
        uploadRAIDRepository.deleteRAIDRegisters();
        uploadRAIDRepository.deleteRAIDLOGs();
        uploadRAIDRepository.deleteRiskConsequences();
        uploadRAIDRepository.deleteRiskRootCauses();
        uploadRAIDRepository.deleteRisks();
        uploadRAIDRepository.deleteAssumptions();
        uploadRAIDRepository.deleteIssues();
        uploadRAIDRepository.deleteDependencies();

        uploadRAIDRepository.deleteRiskReviews();
        uploadRAIDRepository.deleteAssumptionReviews();
        uploadRAIDRepository.deleteIssueReviews();
        uploadRAIDRepository.deleteDependencyReviews();
        uploadRAIDRepository.deleteMilestoneReviews();

        uploadRAIDRepository.deleteRiskActions();
        uploadRAIDRepository.deleteAssumptionActions();
        uploadRAIDRepository.deleteIssueActions();
        uploadRAIDRepository.deleteDependencyActions();
        uploadRAIDRepository.deleteActions();

        uploadRAIDRepository.deleteRiskCurrentControls();
        uploadRAIDRepository.deleteRiskAdditionalControls();
        uploadRAIDRepository.deleteRiskActionTypes();

        /* upload all raid module records */
        if(uploadRAIDRepository.addPIMFromExcel()){
            postMessage("PIM Entity Added Successfully!");
        }else {
            notifyError("Failed to Add PIM Entity");
        }

        if(uploadRAIDRepository.addRAIDLikelihoodFromExcel()){
            postMessage("RAIDLikelihood Entity Added Successfully!");
        }else {
            notifyError("Failed to Add RAIDLikelihood Entity");
        }

        if(uploadRAIDRepository.addRAIDImpactFromExcel()){
            postMessage("RAIDImpact Entity Added Successfully!");
        }else {
            notifyError("Failed to Add RAIDImpact Entity");
        }

        if(uploadRAIDRepository.addRobotFromExcel()){
            postMessage("Robot Entity Added Successfully!");
        }else {
            notifyError("Failed to Add Robot Entity");
        }

        if(uploadRAIDRepository.addPIMSETFromExcel()){
            postMessage("PIMSET Entity Added Successfully!");
        }else {
            notifyError("Failed to Add PIMSET Entity");
        }

        if(uploadRAIDRepository.addRAIDRegisterFromExcel()){
            postMessage("RAIDRegister Entity Added Successfully!");
        }else {
            notifyError("Failed to Add RAIDRegister Entity");
        }

        if(uploadRAIDRepository.addRAIDLOGFromExcel()){
            postMessage("RAIDLOG Entity Added Successfully!");
        }else {
            notifyError("Failed to Add RAIDLOG Entity");
        }

        if(uploadRAIDRepository.addRiskFromExcel()){
            postMessage("Risk Entity Added Successfully!");
        }else {
            notifyError("Failed to Add Risk Entity");
        }

        if(uploadRAIDRepository.addAssumptionFromExcel()){
            postMessage("Assumption Entity Added Successfully!");
        }else {
            notifyError("Failed to Add Assumption Entity");
        }

        if(uploadRAIDRepository.addIssueFromExcel()){
            postMessage("RiskAction Entity Added Successfully!");
        }else {
            notifyError("Failed to Add RiskAction Entity");
        }

        if(uploadRAIDRepository.addIssueCommentFromExcel()){
            postMessage("IssueComment Entity Added Successfully!");
        }else {
            notifyError("Failed to Add IssueComment Entity");
        }

        if(uploadRAIDRepository.addDependencyFromExcel()){
            postMessage("Dependency Entity Added Successfully!");
        }else {
            notifyError("Failed to Add Dependency Entity");
        }

        if(uploadRAIDRepository.addMilestoneReviewFromExcel()){
            postMessage("MilestoneReview Entity Added Successfully!");
        }else {
            notifyError("Failed to Add MilestoneReview Entity");
        }

        if(uploadRAIDRepository.addRiskActionTypeFromExcel()){
            postMessage("RiskActionType Entity Added Successfully!");
        }else {
            notifyError("Failed to Add RiskActionType Entity");
        }

        if(uploadRAIDRepository.addActionFromExcel()){
            postMessage("Action Entity Added Successfully!");
        }else {
            notifyError("Failed to Add Action Entity");
        }
    }
}
