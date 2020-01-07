package com.me.mseotsanyana.mande.PPMER.BLL.interactors.Impl;

import com.me.mseotsanyana.mande.PPMER.BLL.executor.iExecutor;
import com.me.mseotsanyana.mande.PPMER.BLL.executor.iMainThread;
import com.me.mseotsanyana.mande.PPMER.BLL.interactors.base.cAbstractInteractor;
import com.me.mseotsanyana.mande.PPMER.BLL.interactors.iUploadPMInteractor;
import com.me.mseotsanyana.mande.PPMER.BLL.repository.iUploadPMRepository;

public class cUploadPMInteractorImpl extends cAbstractInteractor
        implements iUploadPMInteractor {
    private Callback callback;
    private iUploadPMRepository uploadPMRepository;

    public cUploadPMInteractorImpl(iExecutor threadExecutor, iMainThread mainThread,
                                   iUploadPMRepository uploadPMRepository, Callback callback) {
        super(threadExecutor, mainThread);



        if (uploadPMRepository == null || callback == null) {
            throw new IllegalArgumentException("Arguments can not be null!");
        }

        this.uploadPMRepository = uploadPMRepository;
        this.callback = callback;
    }
/*
    boolean addLogFrameFromExcel();
    boolean addRaidFromExcel();
    boolean addCriteriaFromExcel();
    boolean addChoiceFromExcel();
    boolean addQuestionGroupingFromExcel();
    boolean addMultivaluedOptionFromExcel();
    boolean addQuestionTypeFromExcel();
    boolean addQuestionFromExcel();
    boolean addImpactFromExcel();
    boolean addOutcomeFromExcel();
    boolean addOutputFromExcel();
    boolean addActivityFromExcel();
    boolean addInputFromExcel();
*/

    @Override
    public void run() {
        /* create a new CRITERIA object and insert it in the database */
        //uploadPMRepository.deleteCriteria();
        uploadPMRepository.addCriteriaFromExcel();

        /* create a new CHOICE object and insert it in the database */
        //uploadPMRepository.deleteChoices();
        uploadPMRepository.addChoiceFromExcel();

        /* create a new QUESTION GROUPING object and insert it in the database */
        //uploadPMRepository.deleteQuestionGroupings();
        uploadPMRepository.addQuestionGroupingFromExcel();

        /* create a new QUESTION TYPE object and insert it in the database */
        //uploadPMRepository.deleteQuestionTypes();
        uploadPMRepository.addQuestionTypeFromExcel();

        /* create a new QUESTION object and insert it in the database */
        //uploadPMRepository.deleteQuestions();
        uploadPMRepository.addQuestionFromExcel();

        /* create a new LOGFRAME object and insert it in the database */
        //uploadPMRepository.deleteLogFrames();
        uploadPMRepository.addLogFrameFromExcel();

        /* create a new RAID object and insert it in the database */
        //uploadPMRepository.deleteRaids();
        uploadPMRepository.addRaidFromExcel();

        /* create a new IMPACT object and insert it in the database */
        //uploadPMRepository.deleteImpacts();
        uploadPMRepository.addImpactFromExcel();

        /* create a new OUTCOME object and insert it in the database */
        //uploadPMRepository.deleteOutcomes();
        uploadPMRepository.addOutcomeFromExcel();

        /* create a new OUTPUT object and insert it in the database */
        //uploadPMRepository.deleteOutputs();
        uploadPMRepository.addOutputFromExcel();

        /* create a new ACTIVITY object and insert it in the database */
        //uploadPMRepository.deleteActivities();
        uploadPMRepository.addActivityFromExcel();

        /* create a new INPUT object and insert it in the database */
        //uploadPMRepository.deleteInputs();
        uploadPMRepository.addInputFromExcel();

        /* notify on the main thread that we have inserted this item */
        mainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onUploadPMCompleted();
            }
        });
    }
}
