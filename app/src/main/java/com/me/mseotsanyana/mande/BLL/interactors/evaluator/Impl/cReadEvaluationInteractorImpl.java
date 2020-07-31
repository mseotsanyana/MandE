package com.me.mseotsanyana.mande.BLL.interactors.evaluator.Impl;

import android.util.Log;

import com.google.gson.Gson;
import com.me.mseotsanyana.mande.BLL.executor.iExecutor;
import com.me.mseotsanyana.mande.BLL.executor.iMainThread;
import com.me.mseotsanyana.mande.BLL.interactors.base.cAbstractInteractor;
import com.me.mseotsanyana.mande.BLL.interactors.evaluator.iReadEvaluationInteractor;
import com.me.mseotsanyana.mande.BLL.repository.evaluator.iEvaluationRepository;
import com.me.mseotsanyana.mande.BLL.repository.session.iSessionManagerRepository;
import com.me.mseotsanyana.mande.DAL.model.evaluator.cEvaluationModel;
import com.me.mseotsanyana.mande.DAL.model.logframe.cQuestionModel;
import com.me.mseotsanyana.mande.DAL.model.session.cUserModel;
import com.me.mseotsanyana.mande.DAL.storage.preference.cBitwise;
import com.me.mseotsanyana.treeadapterlibrary.cTreeModel;

import java.util.ArrayList;
import java.util.Set;

public class cReadEvaluationInteractorImpl extends cAbstractInteractor
        implements iReadEvaluationInteractor {
    private static String TAG = cReadEvaluationInteractorImpl.class.getSimpleName();

    private Callback callback;
    private iEvaluationRepository evaluationRepository;
    private long userID, logFrameID;
    private int primaryRoleBITS, secondaryRoleBITS, operationBITS, statusBITS;

    public cReadEvaluationInteractorImpl(iExecutor threadExecutor, iMainThread mainThread,
                                         iSessionManagerRepository sessionManagerRepository,
                                         iEvaluationRepository evaluationRepository,
                                         Callback callback, long logFrameID) {
        super(threadExecutor, mainThread);

        if (sessionManagerRepository == null || evaluationRepository == null || callback == null) {
            throw new IllegalArgumentException("Arguments can not be null!");
        }

        this.evaluationRepository = evaluationRepository;
        this.callback = callback;

        this.logFrameID = logFrameID;

        /* common attributes */
        this.userID = sessionManagerRepository.loadUserID();
        this.primaryRoleBITS = sessionManagerRepository.loadPrimaryRoleBITS();
        this.secondaryRoleBITS = sessionManagerRepository.loadSecondaryRoleBITS();

        /* attributes related to OUTCOME entity */
        this.operationBITS = sessionManagerRepository.loadOperationBITS(
                cBitwise.EVALUATION, cBitwise.EVALUATION_MODULE);
        this.statusBITS = sessionManagerRepository.loadStatusBITS(
                cBitwise.EVALUATION, cBitwise.EVALUATION_MODULE, cBitwise.READ);
    }

    /* */
    private void notifyError(String msg) {
        mainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onEvaluationModelsFailed(msg);
            }
        });
    }

    /* */
    private void postMessage(ArrayList<cTreeModel> evaluationTreeModels) {
        mainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onEvaluationModelsRetrieved(evaluationTreeModels);
            }
        });
    }

    private ArrayList<cTreeModel> getEvaluationTree(Set<cEvaluationModel> evaluationModelSet) {
        ArrayList<cTreeModel> evaluationTreeModels = new ArrayList<>();
        int parentIndex = 0, childIndex = 0;

        ArrayList<cEvaluationModel> evaluationModels = new ArrayList<>(evaluationModelSet);

        for (int i = 0; i < evaluationModels.size(); i++) {
            /* evaluation */
            cEvaluationModel evaluationModel = evaluationModels.get(i);
            evaluationTreeModels.add(new cTreeModel(parentIndex, -1, 0,
                    evaluationModel));

            /* set of users under the evaluation */
            ArrayList<cUserModel> users = new ArrayList<>(evaluationModel.getUserModelSet());
            if (users.size() > 0) {
                childIndex = parentIndex + 1;
                evaluationTreeModels.add(new cTreeModel(childIndex, parentIndex, 1, users));
            }

            /* set of questions under the evaluation */
            ArrayList<cQuestionModel> questions = new ArrayList<>(
                    evaluationModel.getQuestionModelSet());
            if (questions.size() > 0) {
                childIndex = parentIndex + 2;
                evaluationTreeModels.add(new cTreeModel(childIndex, parentIndex, 1, questions));
            }

            /* next parent index */
            parentIndex = childIndex + 1;
        }
        return evaluationTreeModels;
    }

    @Override
    public void run() {
        if ((operationBITS & cBitwise.READ) != 0) {

            /* retrieve a set logFrames from the database */
            Log.d(TAG, "LOGFRAME ID = " + logFrameID + "; USER ID = " + userID +
                    "; PRIMARY = " + primaryRoleBITS + "; SECONDARY = " + secondaryRoleBITS +
                    "; STATUS = " + statusBITS);

            Set<cEvaluationModel> evaluationModelSet = evaluationRepository.getEvaluationModelSet(
                    logFrameID, userID, primaryRoleBITS, secondaryRoleBITS, statusBITS);

            if (evaluationModelSet != null) {
                Gson gson = new Gson();
                Log.d(TAG, "EVALUATION = " + gson.toJson(evaluationModelSet.size()));

                ArrayList<cTreeModel> evaluationTreeModels = getEvaluationTree(evaluationModelSet);

                postMessage(evaluationTreeModels);
            } else {
                notifyError("Failed to read evaluations !!");
            }
        } else {
            notifyError("Failed due to reading access rights !!");
        }
    }
}
