package com.me.mseotsanyana.mande.BLL.interactors.logframe.input.Impl;

import android.util.Log;

import com.google.gson.Gson;
import com.me.mseotsanyana.mande.BLL.executor.iExecutor;
import com.me.mseotsanyana.mande.BLL.executor.iMainThread;
import com.me.mseotsanyana.mande.BLL.interactors.base.cAbstractInteractor;
import com.me.mseotsanyana.mande.BLL.interactors.logframe.input.iReadInputInteractor;
import com.me.mseotsanyana.mande.BLL.repository.logframe.iInputRepository;
import com.me.mseotsanyana.mande.BLL.repository.session.iSessionManagerRepository;
import com.me.mseotsanyana.mande.DAL.model.logframe.cActivityModel;
import com.me.mseotsanyana.mande.DAL.model.logframe.cInputModel;
import com.me.mseotsanyana.mande.DAL.model.logframe.cQuestionModel;
import com.me.mseotsanyana.mande.DAL.model.wpb.cJournalModel;
import com.me.mseotsanyana.mande.DAL.storage.preference.cBitwise;
import com.me.mseotsanyana.treeadapterlibrary.cTreeModel;

import java.util.ArrayList;
import java.util.Set;

public class cReadInputInteractorImpl extends cAbstractInteractor
        implements iReadInputInteractor {
    private static String TAG = cReadInputInteractorImpl.class.getSimpleName();

    private Callback callback;
    private iInputRepository inputRepository;
    private long logFrameID;
    private int userID, primaryRoleBITS, secondaryRoleBITS, operationBITS, statusBITS;

    private String logFrameName;

    public cReadInputInteractorImpl(iExecutor threadExecutor, iMainThread mainThread,
                                    iSessionManagerRepository sessionManagerRepository,
                                    iInputRepository inputRepository,
                                    Callback callback, long logFrameID) {
        super(threadExecutor, mainThread);

        if (sessionManagerRepository == null || inputRepository == null || callback == null) {
            throw new IllegalArgumentException("Arguments can not be null!");
        }

        this.inputRepository = inputRepository;
        this.callback = callback;

        this.logFrameID = logFrameID;

        /* common attributes */
        this.userID = sessionManagerRepository.loadUserID();
        this.primaryRoleBITS = sessionManagerRepository.loadPrimaryRoleBITS();
        this.secondaryRoleBITS = sessionManagerRepository.loadSecondaryRoleBITS();

        /* attributes related to INPUT entity */
        this.operationBITS = sessionManagerRepository.loadOperationBITS(
                cBitwise.INPUT, cBitwise.LOGFRAME_MODULE);
        this.statusBITS = sessionManagerRepository.loadStatusBITS(
                cBitwise.INPUT, cBitwise.LOGFRAME_MODULE, cBitwise.READ);
    }

    /* */
    private void notifyError(String msg) {
        mainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onInputsRetrieveFailed(msg);
            }
        });
    }

    /* */
    private void postMessage(String logFrameName, ArrayList<cTreeModel> inputTreeModels) {
        mainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onInputsRetrieved(logFrameName, inputTreeModels);
            }
        });
    }

    private ArrayList<cTreeModel> getInputTree(Set<cInputModel> inputModelSet) {
        ArrayList<cTreeModel> inputTreeModels = new ArrayList<>();
        int parentIndex = 0, childIndex = 0;

        ArrayList<cInputModel> inputModels = new ArrayList<>(inputModelSet);
        if (inputModelSet.size() > 0) {
            logFrameName = inputModels.get(0).getLogFrameModel().getName();
        }

        for (int i = 0; i < inputModels.size(); i++) {
            /* input model */
            cInputModel inputModel = inputModels.get(i);
            inputTreeModels.add(new cTreeModel(parentIndex, -1, 0, inputModel));

            /* set of questions under the input */
            ArrayList<cQuestionModel> questions = new ArrayList<>(inputModel.getQuestionModelSet());
            if (questions.size() > 0) {
                childIndex = parentIndex + 1;
                inputTreeModels.add(new cTreeModel(childIndex, parentIndex, 1, questions));
            }

            /* set of journals under the input */
            ArrayList<cJournalModel> journals = new ArrayList<>(inputModel.getJournalModelSet());
            if (journals.size() > 0) {
                childIndex = parentIndex + 2;
                inputTreeModels.add(new cTreeModel(childIndex, parentIndex, 1, journals));
            }

            /* set of activity children under the sub-logframe of the input logframe */
            ArrayList<cActivityModel> activities = new ArrayList<>(
                    inputModel.getChildActivityModelSet());
            if (activities.size() > 0) {
                childIndex = parentIndex + 3;
                inputTreeModels.add(new cTreeModel(childIndex, parentIndex, 1, activities));
            }

            /* next parent index */
            parentIndex = childIndex + 1;
        }
        return inputTreeModels;
    }

    @Override
    public void run() {
        if ((operationBITS & cBitwise.READ) != 0) {

            /* retrieve a set logFrames from the database */
            Log.d(TAG, "LOGFRAME ID = " + logFrameID + "; USER ID = " + userID +
                    "; PRIMARY = " + primaryRoleBITS + "; SECONDARY = " + secondaryRoleBITS +
                    "; STATUS = " + statusBITS);

            Set<cInputModel> inputModelSet = inputRepository.getInputModelSet(logFrameID,
                    userID, primaryRoleBITS, secondaryRoleBITS, statusBITS);

            if (inputModelSet != null) {
                Gson gson = new Gson();
                Log.d(TAG, "ACTIVITY = " + gson.toJson(inputModelSet.size()));

                ArrayList<cTreeModel> inputTreeModels = getInputTree(inputModelSet);

                postMessage(logFrameName, inputTreeModels);
            } else {
                notifyError("Failed to read inputs !!");
            }
        } else {
            notifyError("Failed due to reading access rights !!");
        }
    }
}
