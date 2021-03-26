package com.me.mseotsanyana.mande.BLL.interactors.logframe.logframe.Impl;

import android.util.Log;

import com.google.gson.Gson;
import com.me.mseotsanyana.mande.BLL.executor.iExecutor;
import com.me.mseotsanyana.mande.BLL.executor.iMainThread;
import com.me.mseotsanyana.mande.BLL.interactors.base.cAbstractInteractor;
import com.me.mseotsanyana.mande.BLL.interactors.logframe.logframe.iUploadLogFrameInteractor;
import com.me.mseotsanyana.mande.BLL.repository.logframe.iUploadLogFrameRepository;
import com.me.mseotsanyana.mande.BuildConfig;

public class cUploadLogFrameInteractorImpl extends cAbstractInteractor
        implements iUploadLogFrameInteractor {
    private Callback callback;
    private iUploadLogFrameRepository uploadLMRepository;

    public cUploadLogFrameInteractorImpl(iExecutor threadExecutor, iMainThread mainThread,
                                         iUploadLogFrameRepository uploadLMRepository,
                                         Callback callback) {
        super(threadExecutor, mainThread);

        if (uploadLMRepository == null || callback == null) {
            throw new IllegalArgumentException("Arguments can not be null!");
        }

        this.uploadLMRepository = uploadLMRepository;
        this.callback = callback;
    }

    /* notify on the main thread */
    private void notifyError(String msg) {
        mainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onUploadLogFrameCompleted(msg);
            }
        });
    }

    /* notify on the main thread */
    private void postMessage(String msg) {
        mainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onUploadLogFrameCompleted(msg);
            }
        });
    }

    @Override
    public void run() {

        /* delete all logframe module records */
        uploadLMRepository.deleteLogFrame();

        uploadLMRepository.deleteLogFrameTree();

        uploadLMRepository.deleteResourceTypes();

        uploadLMRepository.deleteComponents();
        uploadLMRepository.deleteImpacts();
        uploadLMRepository.deleteOutcomeImpacts();
        uploadLMRepository.deleteOutcomes();
        uploadLMRepository.deleteOutputOutcomes();
        uploadLMRepository.deleteOutputs();

        uploadLMRepository.deleteActivities();
        uploadLMRepository.deletePrecedingActivities();
        uploadLMRepository.deleteActivityAssignments();
        uploadLMRepository.deleteActivityOutputs();

        uploadLMRepository.deleteInputs();
        uploadLMRepository.deleteInputActivities();
        uploadLMRepository.deleteHumans();
        uploadLMRepository.deleteHumanSets();
        uploadLMRepository.deleteMaterials();
        uploadLMRepository.deleteIncomes();
        uploadLMRepository.deleteFunds();
        uploadLMRepository.deleteExpenses();

        uploadLMRepository.deleteCriteria();
        uploadLMRepository.deleteQuestionGroupings();
        uploadLMRepository.deleteQuestionTypes();
        uploadLMRepository.deleteQuestions();
        uploadLMRepository.deleteQuestionCriteria();

        uploadLMRepository.deletePrimitiveQuestions();
        uploadLMRepository.deleteArrayQuestions();
        uploadLMRepository.deleteMatrixQuestions();

        uploadLMRepository.deleteRaidCategories();
        uploadLMRepository.deleteRaids();
        uploadLMRepository.deleteComponentRaids();

        /* upload all logframe module records */
        if (uploadLMRepository.addLogFrameFromExcel()) {
            postMessage("LogFrame Entity Added Successfully!");
        } else {
            notifyError("Failed to Add LogFrame Entity");
        }

        if (uploadLMRepository.addResourceTypeFromExcel()) {
            postMessage("ResourceType Entity Added Successfully!");
        } else {
            notifyError("Failed to Add ResourceType Entity");
        }

        if (uploadLMRepository.addComponentFromExcel()) {
            postMessage("Component Entity Added Successfully!");
        } else {
            notifyError("Failed to Add Component Entity");
        }

        if (uploadLMRepository.addCriteriaFromExcel()) {
            postMessage("Criteria Entity Added Successfully!");
        } else {
            notifyError("Failed to Add Criteria Entity");
        }
        if (uploadLMRepository.addQuestionGroupingFromExcel()) {
            postMessage("QuestionGrouping Entity Added Successfully!");
        } else {
            notifyError("Failed to Add QuestionGrouping Entity");
        }
        if (uploadLMRepository.addQuestionTypeFromExcel()) {
            postMessage("QuestionType Entity Added Successfully!");
        } else {
            notifyError("Failed to Add QuestionType Entity");
        }
        if (uploadLMRepository.addQuestionFromExcel()) {
            postMessage("Question Entity Added Successfully!");
        } else {
            notifyError("Failed to Add Question Entity");
        }

        if (uploadLMRepository.addRaidCategoryFromExcel()) {
            postMessage("Raid Category Entity Added Successfully!");
        } else {
            notifyError("Failed to Add Raid Category Entity");
        }
        if (uploadLMRepository.addRaidFromExcel()) {
            postMessage("Raid Entity Added Successfully!");
        } else {
            notifyError("Failed to Add Raid Entity");
        }
    }
}
