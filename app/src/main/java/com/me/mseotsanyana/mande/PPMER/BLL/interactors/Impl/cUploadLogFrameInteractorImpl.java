package com.me.mseotsanyana.mande.PPMER.BLL.interactors.Impl;

import com.me.mseotsanyana.mande.PPMER.BLL.executor.iExecutor;
import com.me.mseotsanyana.mande.PPMER.BLL.executor.iMainThread;
import com.me.mseotsanyana.mande.PPMER.BLL.interactors.base.cAbstractInteractor;
import com.me.mseotsanyana.mande.PPMER.BLL.interactors.iUploadLogFrameInteractor;
import com.me.mseotsanyana.mande.PPMER.BLL.repository.iUploadLogFrameRepository;

public class cUploadLogFrameInteractorImpl extends cAbstractInteractor
        implements iUploadLogFrameInteractor {
    private Callback callback;
    private iUploadLogFrameRepository uploadLMRepository;

    public cUploadLogFrameInteractorImpl(iExecutor threadExecutor, iMainThread mainThread,
                                         iUploadLogFrameRepository uploadLMRepository, Callback callback) {
        super(threadExecutor, mainThread);



        if (uploadLMRepository == null || callback == null) {
            throw new IllegalArgumentException("Arguments can not be null!");
        }

        this.uploadLMRepository = uploadLMRepository;
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
        //uploadLMRepository.deleteCriteria();
        uploadLMRepository.addCriteriaFromExcel();

        /* create a new QUESTION GROUPING object and insert it in the database */
        //uploadLMRepository.deleteQuestionGroupings();
        uploadLMRepository.addQuestionGroupingFromExcel();

        /* create a new QUESTION TYPE object and insert it in the database */
        //uploadPMRepository.deleteQuestionTypes();
        uploadLMRepository.addQuestionTypeFromExcel();

        /* create a new QUESTION object and insert it in the database */
        //uploadPMRepository.deleteQuestions();
        uploadLMRepository.addQuestionFromExcel();

        /* create a new LOGFRAME object and insert it in the database */
        //uploadPMRepository.deleteLogFrames();
        uploadLMRepository.addLogFrameFromExcel();

        /* create a new RAID object and insert it in the database */
        //uploadLMRepository.deleteRaids();
        uploadLMRepository.addRaidFromExcel();

        /* create a new IMPACT object and insert it in the database */
        //uploadLMRepository.deleteImpacts();
        uploadLMRepository.addImpactFromExcel();

        /* create a new OUTCOME object and insert it in the database */
        //uploadLMRepository.deleteOutcomes();
        uploadLMRepository.addOutcomeFromExcel();

        /* create a new OUTPUT object and insert it in the database */
        //uploadLMRepository.deleteOutputs();
        uploadLMRepository.addOutputFromExcel();

        /* create a new ACTIVITY PLANNING object and insert it in the database */
        //uploadLMRepository.deleteActivities();
        uploadLMRepository.addActivityPlanningFromExcel();

        /* create a new ACTIVITY object and insert it in the database */
        //uploadLMRepository.deleteActivities();
        uploadLMRepository.addActivityFromExcel();

        /* create a new RESOURCE TYPE object and insert it in the database */
        //uploadLMRepository.deleteResourceTypes();
        uploadLMRepository.addResourceTypeFromExcel();

        /* create a new RESOURCE object and insert it in the database */
        //uploadLMRepository.deleteResources();
        uploadLMRepository.addResourceFromExcel();

        /* create a new INPUT object and insert it in the database */
        //uploadLMRepository.deleteInputs();
        uploadLMRepository.addInputFromExcel();

        /* notify on the main thread that we have inserted this item */
        mainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onUploadLogFrameCompleted("LogFrame Modules Added Successfully!");
            }
        });
    }
}
