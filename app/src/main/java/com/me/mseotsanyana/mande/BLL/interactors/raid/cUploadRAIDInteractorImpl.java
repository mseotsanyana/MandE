package com.me.mseotsanyana.mande.BLL.interactors.raid;

import com.me.mseotsanyana.mande.BLL.executor.iExecutor;
import com.me.mseotsanyana.mande.BLL.executor.iMainThread;
import com.me.mseotsanyana.mande.BLL.interactors.base.cAbstractInteractor;
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

    @Override
    public void run() {
        /* delete RAID functions */
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
        /*uploadRAIDRepository.deleteRiskAnalysis();*/
        uploadRAIDRepository.deleteRiskActionTypes();
        uploadRAIDRepository.deleteRiskActions();
        uploadRAIDRepository.deleteRiskCurrentControls();
        uploadRAIDRepository.deleteRiskAdditionalControls();

        /* add RAID functions */
        uploadRAIDRepository.addRegisterFromExcel();
        uploadRAIDRepository.addRiskLikelihoodFromExcel();
        uploadRAIDRepository.addRiskImpactFromExcel();
        uploadRAIDRepository.addRiskCriteriaFromExcel();
        uploadRAIDRepository.addRiskFromExcel();
        /*uploadRAIDRepository.addRiskAnalysisFromExcel();*/
        uploadRAIDRepository.addRiskActionTypeFromExcel();
        uploadRAIDRepository.addRiskActionFromExcel();

        /* notify on the main thread that we have inserted this item */
        mainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onUploadRAIDCompleted("RAID Modules Added Successfully!");
            }
        });
    }
}
