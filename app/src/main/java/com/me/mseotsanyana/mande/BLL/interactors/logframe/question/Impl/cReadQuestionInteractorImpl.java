package com.me.mseotsanyana.mande.BLL.interactors.logframe.question.Impl;

import android.util.Log;

import com.google.gson.Gson;
import com.me.mseotsanyana.mande.BLL.executor.iExecutor;
import com.me.mseotsanyana.mande.BLL.executor.iMainThread;
import com.me.mseotsanyana.mande.BLL.interactors.base.cAbstractInteractor;
import com.me.mseotsanyana.mande.BLL.interactors.logframe.output.iReadOutputInteractor;
import com.me.mseotsanyana.mande.BLL.interactors.logframe.question.iReadQuestionInteractor;
import com.me.mseotsanyana.mande.BLL.model.logframe.cOutputModel;
import com.me.mseotsanyana.mande.BLL.model.logframe.cQuestionModel;
import com.me.mseotsanyana.mande.BLL.repository.logframe.iOutputRepository;
import com.me.mseotsanyana.mande.BLL.repository.logframe.iQuestionRepository;
import com.me.mseotsanyana.mande.BLL.repository.session.iSessionManagerRepository;
import com.me.mseotsanyana.mande.DAL.storage.preference.cBitwise;
import com.me.mseotsanyana.treeadapterlibrary.cTreeModel;

import java.util.ArrayList;
import java.util.Set;

public class cReadQuestionInteractorImpl extends cAbstractInteractor
        implements iReadQuestionInteractor {
    private static String TAG = cReadQuestionInteractorImpl.class.getSimpleName();

    private Callback callback;
    private iQuestionRepository questionRepository;
    private long userID, logFrameID;
    private int primaryRoleBITS, secondaryRoleBITS, operationBITS, statusBITS;

    private String logFrameName;

    public cReadQuestionInteractorImpl(iExecutor threadExecutor, iMainThread mainThread,
                                       iSessionManagerRepository sessionManagerRepository,
                                       iQuestionRepository questionRepository,
                                       Callback callback, long logFrameID) {
        super(threadExecutor, mainThread);

        if (sessionManagerRepository == null || questionRepository == null || callback == null) {
            throw new IllegalArgumentException("Arguments can not be null!");
        }

        this.questionRepository = questionRepository;
        this.callback = callback;

        this.logFrameID = logFrameID;

        /* common attributes */
        this.userID = sessionManagerRepository.loadUserID();
        this.primaryRoleBITS = sessionManagerRepository.loadPrimaryRoleBITS();
        this.secondaryRoleBITS = sessionManagerRepository.loadSecondaryRoleBITS();

        /* attributes related to OUTCOME entity */
        this.operationBITS = sessionManagerRepository.loadOperationBITS(
                cBitwise.OUTPUT, cBitwise.LOGFRAME_MODULE);
        this.statusBITS = sessionManagerRepository.loadStatusBITS(
                cBitwise.OUTPUT, cBitwise.LOGFRAME_MODULE, cBitwise.READ);
    }

    /* */
    private void notifyError(String msg) {
        mainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onQuestionModelsFailed(msg);
            }
        });
    }

    /* */
    private void postMessage(String logFrameName, ArrayList<cQuestionModel> questionModels) {
        mainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onQuestionModelsRetrieved(logFrameName, questionModels);
            }
        });
    }

    private ArrayList<cTreeModel> getOutputTree(Set<cOutputModel> outputModelSet) {
        ArrayList<cTreeModel> outputTreeModels = new ArrayList<>();
        int parentIndex = 0, childIndex = 0;

        ArrayList<cOutputModel> outputModels = new ArrayList<>(outputModelSet);
        if (outputModelSet.size() > 0) {
            logFrameName = outputModels.get(0).getLogFrameModel().getName();
        }

        for (int i = 0; i < outputModels.size(); i++) {
            /* output */
            cOutputModel outputModel = outputModels.get(i);
            outputTreeModels.add(new cTreeModel(parentIndex, -1, 0, outputModel));

            /* set of output children under the output */
            childIndex = parentIndex;
            ArrayList<cOutputModel> outputs = new ArrayList<>(outputModel.getChildrenOutputModelSet());
            for (int j = 0; j < outputs.size(); j++) {
                childIndex = childIndex + 1;
                outputTreeModels.add(new cTreeModel(childIndex, parentIndex, 1, outputs));
            }

            /* next parent index */
            parentIndex = childIndex + 1;

//            /* set of outcome children under the sub-logframe of the output logframe */
//            ArrayList<cOutcomeModel> outcomes = new ArrayList<>(outputModel.getChildOutcomeModelSet());
//            if (outcomes.size() > 0) {
//                childIndex = parentIndex + 1;
//                outputTreeModels.add(new cTreeModel(childIndex, parentIndex, 1, outcomes));
//            }
//
//
//            /* set of activities under the output */
//            ArrayList<cActivityModel> activities = new ArrayList<>(outputModel.getActivityModelSet());
//            if (activities.size() > 0) {
//                childIndex = parentIndex + 3;
//                outputTreeModels.add(new cTreeModel(childIndex, parentIndex, 1, activities));
//            }
//
//            /* set of questions under the outcome */
//            ArrayList<cQuestionModel> questions = new ArrayList<>(outputModel.getQuestionModelSet());
//            if (questions.size() > 0) {
//                childIndex = parentIndex + 4;
//                outputTreeModels.add(new cTreeModel(childIndex, parentIndex, 1, questions));
//            }
//
//            /* set of raids under the outcome */
//            ArrayList<cRaidModel> raids = new ArrayList<>(outputModel.getRaidModelSet());
//            if (raids.size() > 0) {
//                childIndex = parentIndex + 5;
//                outputTreeModels.add(new cTreeModel(childIndex, parentIndex, 1, raids));
//            }

        }
        return outputTreeModels;
    }

    @Override
    public void run() {
        if ((operationBITS & cBitwise.READ) != 0) {

            /* retrieve a set logFrames from the database */
            Log.d(TAG, "LOGFRAME ID = " + logFrameID + "; USER ID = " + userID + "; " +
                    "PRIMARY = " + primaryRoleBITS + "; SECONDARY = " + secondaryRoleBITS + "; " +
                    "STATUS = " + statusBITS);

            Set<cQuestionModel> questionModelSet = questionRepository.getQuestionModelSetByID(
                    logFrameID, userID, primaryRoleBITS, secondaryRoleBITS, statusBITS);

            if (questionModelSet != null) {
                Gson gson = new Gson();
                Log.d(TAG, "IMPACT = " + gson.toJson(questionModelSet.size()));

                //ArrayList<cTreeModel> outputTreeModels = getOutputTree(questionModelSet);

                postMessage(logFrameName, new ArrayList<>(questionModelSet));
            } else {
                notifyError("Failed to read outputs !!");
            }
        } else {
            notifyError("Failed due to reading access rights !!");
        }
    }
}
