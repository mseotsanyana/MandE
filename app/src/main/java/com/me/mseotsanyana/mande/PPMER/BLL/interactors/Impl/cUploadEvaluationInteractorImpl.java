package com.me.mseotsanyana.mande.PPMER.BLL.interactors.Impl;

import com.me.mseotsanyana.mande.PPMER.BLL.executor.iExecutor;
import com.me.mseotsanyana.mande.PPMER.BLL.executor.iMainThread;
import com.me.mseotsanyana.mande.PPMER.BLL.interactors.base.cAbstractInteractor;
import com.me.mseotsanyana.mande.PPMER.BLL.interactors.iUploadEvaluationInteractor;
import com.me.mseotsanyana.mande.PPMER.BLL.repository.iUploadEvaluationRepository;

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

        uploadEvaluationRepository.addArrayChoiceFromExcel();
        uploadEvaluationRepository.addRowOptionFromExcel();
        uploadEvaluationRepository.addColOptionFromExcel();
        uploadEvaluationRepository.addMatrixChoiceFromExcel();
        uploadEvaluationRepository.addEvaluationTypeFromExcel();
        uploadEvaluationRepository.addQuestionnaireFromExcel();
        uploadEvaluationRepository.addEResponseFromExcel();

        /* notify on the main thread that we have inserted this item */
        mainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onUploadEvaluationCompleted();
            }
        });
    }
}
