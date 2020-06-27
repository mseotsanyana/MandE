package com.me.mseotsanyana.mande.BLL.interactors.logframe.logframe.Impl;

import com.me.mseotsanyana.mande.BLL.executor.iExecutor;
import com.me.mseotsanyana.mande.BLL.executor.iMainThread;
import com.me.mseotsanyana.mande.BLL.interactors.base.cAbstractInteractor;
import com.me.mseotsanyana.mande.BLL.interactors.logframe.logframe.iUploadLogFrameInteractor;
import com.me.mseotsanyana.mande.BLL.repository.logframe.iUploadLogFrameRepository;

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

        uploadLMRepository.deleteCriteria();

        uploadLMRepository.deleteQuestionGroupings();
        uploadLMRepository.deletePrimitiveTypes();
        uploadLMRepository.deleteArrayTypes();
        uploadLMRepository.deleteMatrixTypes();
        uploadLMRepository.deleteQuestionTypes();
        uploadLMRepository.deleteQuestions();

        uploadLMRepository.deleteRaids();

        uploadLMRepository.deleteImpactQuestions();
        uploadLMRepository.deleteImpactRaids();
        uploadLMRepository.deleteImpacts();

        uploadLMRepository.deleteOutcomeImpacts();
        uploadLMRepository.deleteOutcomeQuestions();
        uploadLMRepository.deleteOutcomeRaids();
        uploadLMRepository.deleteOutcomes();

        uploadLMRepository.deleteOutputOutcomes();
        uploadLMRepository.deleteOutputQuestions();
        uploadLMRepository.deleteOutputRaids();
        uploadLMRepository.deleteOutputs();

        uploadLMRepository.deleteActivityPlannings();

        uploadLMRepository.deletePrecedingActivities();
        uploadLMRepository.deleteActivityAssignments();
        uploadLMRepository.deleteActivityOutputs();
        uploadLMRepository.deleteActivityQuestions();
        uploadLMRepository.deleteActivityRaids();
        uploadLMRepository.deleteActivities();

        uploadLMRepository.deleteResourceTypes();
        uploadLMRepository.deleteResources();

        uploadLMRepository.deleteInputQuestions();
        uploadLMRepository.deleteInputActivities();
        uploadLMRepository.deleteHumans();
        uploadLMRepository.deleteHumanSets();
        uploadLMRepository.deleteMaterials();
        uploadLMRepository.deleteIncomes();
        uploadLMRepository.deleteFunds();
        uploadLMRepository.deleteExpenses();
        uploadLMRepository.deleteInputs();

        uploadLMRepository.deleteLogFrameTree();
        uploadLMRepository.deleteLogFrame();

        /* upload all logframe module records */

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

        if (uploadLMRepository.addLogFrameFromExcel()) {
            postMessage("LogFrame Entity Added Successfully!");
        } else {
            notifyError("Failed to Add LogFrame Entity");
        }

        if (uploadLMRepository.addRaidFromExcel()) {
            postMessage("Raid Entity Added Successfully!");
        } else {
            notifyError("Failed to Add Raid Entity");
        }

        if (uploadLMRepository.addImpactFromExcel()) {
            postMessage("Impact Entity Added Successfully!");
        } else {
            notifyError("Failed to Add Impact Entity");
        }

        if (uploadLMRepository.addOutputFromExcel()) {
            postMessage("Output Entity Added Successfully!");
        } else {
            notifyError("Failed to Add Output Entity");
        }

        if (uploadLMRepository.addActivityPlanningFromExcel()) {
            postMessage("ActivityPlanning Entity Added Successfully!");
        } else {
            notifyError("Failed to Add ActivityPlanning Entity");
        }

        if (uploadLMRepository.addActivityFromExcel()) {
            postMessage("Activity Entity Added Successfully!");
        } else {
            notifyError("Failed to Add Activity Entity");
        }

        if (uploadLMRepository.addResourceTypeFromExcel()) {
            postMessage("ResourceType Entity Added Successfully!");
        } else {
            notifyError("Failed to Add ResourceType Entity");
        }

        if (uploadLMRepository.addResourceFromExcel()) {
            postMessage("Resource Entity Added Successfully!");
        } else {
            notifyError("Failed to Add Resource Entity");
        }

        if (uploadLMRepository.addOutcomeFromExcel()) {
            postMessage("Criteria Entity Added Successfully!");
        } else {
            notifyError("Failed to Add Criteria Entity");
        }

        if (uploadLMRepository.addInputFromExcel()) {
            postMessage("Input Entity Added Successfully!");
        } else {
            notifyError("Failed to Add Input Entity");
        }
    }
}
