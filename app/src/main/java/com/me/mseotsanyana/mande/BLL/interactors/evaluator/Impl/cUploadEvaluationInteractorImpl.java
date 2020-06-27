package com.me.mseotsanyana.mande.BLL.interactors.evaluator.Impl;

import com.me.mseotsanyana.mande.BLL.executor.iExecutor;
import com.me.mseotsanyana.mande.BLL.executor.iMainThread;
import com.me.mseotsanyana.mande.BLL.interactors.base.cAbstractInteractor;
import com.me.mseotsanyana.mande.BLL.interactors.evaluator.iUploadEvaluationInteractor;
import com.me.mseotsanyana.mande.BLL.repository.evaluator.iUploadEvaluationRepository;

public class cUploadEvaluationInteractorImpl extends cAbstractInteractor
        implements iUploadEvaluationInteractor {
    private Callback callback;
    private iUploadEvaluationRepository uploadEvaluationRepository;

    public cUploadEvaluationInteractorImpl(iExecutor threadExecutor, iMainThread mainThread,
                                           iUploadEvaluationRepository uploadEvaluationRepository,
                                           Callback callback) {
        super(threadExecutor, mainThread);

        if (uploadEvaluationRepository == null || callback == null) {
            throw new IllegalArgumentException("Arguments can not be null!");
        }

        this.uploadEvaluationRepository = uploadEvaluationRepository;
        this.callback = callback;
    }

    /* notify on the main thread */
    private void notifyError(String msg){
        mainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onUploadEvaluationCompleted(msg);
            }
        });
    }

    /* notify on the main thread */
    private void postMessage(String msg){
        mainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onUploadEvaluationCompleted(msg);
            }
        });
    }

    @Override
    public void run() {
        /* create a new ARRAY CHOICE object and insert it in the database */
        uploadEvaluationRepository.deleteArrayChoices();
        uploadEvaluationRepository.deleteArrayChoices();
        uploadEvaluationRepository.deleteArrayChoiceSets();
        uploadEvaluationRepository.deleteRowOptions();
        uploadEvaluationRepository.deleteColOptions();
        uploadEvaluationRepository.deleteMatrixChoices();
        uploadEvaluationRepository.deleteMatrixChoiceSets();
        uploadEvaluationRepository.deleteEvaluationTypes();
        uploadEvaluationRepository.deleteQuestionnaires();
        uploadEvaluationRepository.deleteQuestionnaireQuestions();
        uploadEvaluationRepository.deleteConditionalOrders();
        uploadEvaluationRepository.deleteQuestionnaireUsers();
        uploadEvaluationRepository.deleteEResponses();
        uploadEvaluationRepository.deleteNumericResponses();
        uploadEvaluationRepository.deleteTextResponses();
        uploadEvaluationRepository.deleteDateResponses();
        uploadEvaluationRepository.deleteArrayResponses();
        uploadEvaluationRepository.deleteMatrixResponses();

        if(uploadEvaluationRepository.addArrayChoiceFromExcel()){
            postMessage("ArrayChoice Entity Added Successfully!");
        }else {
            notifyError("Failed to Add ArrayChoice Entity");
        }

        if(uploadEvaluationRepository.addRowOptionFromExcel()){
            postMessage("RowOption Entity Added Successfully!");
        }else {
            notifyError("Failed to Add RowOption Entity");
        }

        if(uploadEvaluationRepository.addColOptionFromExcel()){
            postMessage("ColOption Entity Added Successfully!");
        }else {
            notifyError("Failed to Add ColOption Entity");
        }

        if(uploadEvaluationRepository.addMatrixChoiceFromExcel()){
            postMessage("MatrixChoice Entity Added Successfully!");
        }else {
            notifyError("Failed to Add MatrixChoice Entity");
        }

        if(uploadEvaluationRepository.addEvaluationTypeFromExcel()){
            postMessage("EvaluationType Entity Added Successfully!");
        }else {
            notifyError("Failed to Add EvaluationType Entity");
        }

        if(uploadEvaluationRepository.addQuestionnaireFromExcel()){
            postMessage("Questionnaire Entity Added Successfully!");
        }else {
            notifyError("Failed to Add Questionnaire Entity");
        }

        if(uploadEvaluationRepository.addEResponseFromExcel()){
            postMessage("EResponse Entity Added Successfully!");
        }else {
            notifyError("Failed to Add EResponse Entity");
        }
    }
}
