package com.me.mseotsanyana.mande.BLL.interactors.logframe.outcome.Impl;

import android.util.Log;

import com.google.gson.Gson;
import com.me.mseotsanyana.mande.BLL.executor.iExecutor;
import com.me.mseotsanyana.mande.BLL.executor.iMainThread;
import com.me.mseotsanyana.mande.BLL.interactors.base.cAbstractInteractor;
import com.me.mseotsanyana.mande.BLL.interactors.logframe.outcome.iReadOutcomeInteractor;
import com.me.mseotsanyana.mande.BLL.repository.logframe.iOutcomeRepository;
import com.me.mseotsanyana.mande.BLL.model.logframe.cOutcomeModel;
import com.me.mseotsanyana.mande.BLL.repository.session.iSharedPreferenceRepository;
import com.me.mseotsanyana.mande.DAL.storage.preference.cBitwise;
import com.me.mseotsanyana.treeadapterlibrary.cTreeModel;

import java.util.ArrayList;
import java.util.Set;

public class cReadOutcomeInteractorImpl extends cAbstractInteractor
        implements iReadOutcomeInteractor {
    private static String TAG = cReadOutcomeInteractorImpl.class.getSimpleName();

    private Callback callback;
    private iOutcomeRepository outcomeRepository;
    private long userID, logFrameID;
    private int primaryRoleBITS, secondaryRoleBITS, operationBITS, statusBITS;

    private String logFrameName;

    public cReadOutcomeInteractorImpl(iExecutor threadExecutor, iMainThread mainThread,
                                      iSharedPreferenceRepository sessionManagerRepository,
                                      iOutcomeRepository outcomeRepository,
                                      Callback callback, long logFrameID) {
        super(threadExecutor, mainThread);

        if (sessionManagerRepository == null || outcomeRepository == null || callback == null) {
            throw new IllegalArgumentException("Arguments can not be null!");
        }

        this.outcomeRepository = outcomeRepository;
        this.callback = callback;

        this.logFrameID = logFrameID;

//        /* common attributes */
//        this.userID = sessionManagerRepository.loadUserID();
//        this.primaryRoleBITS = sessionManagerRepository.loadPrimaryRoleBITS();
//        this.secondaryRoleBITS = sessionManagerRepository.loadSecondaryRoleBITS();
//
//        /* attributes related to OUTCOME entity */
//        this.operationBITS = sessionManagerRepository.loadOperationBITS(
//                cBitwise.OUTCOME, cBitwise.LOGFRAME_MODULE);
//        this.statusBITS = sessionManagerRepository.loadStatusBITS(
//                cBitwise.OUTCOME, cBitwise.LOGFRAME_MODULE, cBitwise.READ);
    }

    /* */
    private void notifyError(String msg) {
        mainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onOutcomeModelsFailed(msg);
            }
        });
    }

    /* */
    private void postMessage(String logFrameName, ArrayList<cTreeModel> outcomeTreeModels) {
        mainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onOutcomeModelsRetrieved(logFrameName, outcomeTreeModels);
            }
        });
    }

    private ArrayList<cTreeModel> getOutcomeTree(Set<cOutcomeModel> outcomeModelSet) {
        ArrayList<cTreeModel> outcomeTreeModels = new ArrayList<>();
        int parentIndex = 0, childIndex = 0;
        ArrayList<cOutcomeModel> outcomeModels = new ArrayList<>(outcomeModelSet);
        if (outcomeModels.size() > 0) {
            logFrameName = outcomeModels.get(0).getLogFrameModel().getName();
        }

        for (int i = 0; i < outcomeModels.size(); i++) {
            /* impact parent */
            cOutcomeModel outcomeModel = outcomeModels.get(i);
            outcomeTreeModels.add(new cTreeModel(parentIndex, -1, 0, outcomeModel));

            /* set of impact children under the impact */
            childIndex = parentIndex;
            ArrayList<cOutcomeModel> outcomes = new ArrayList<>(outcomeModel.getChildOutcomeModelSet());
            for (int j = 0; j < outcomes.size(); j++) {
                childIndex = childIndex + 1;
                outcomeTreeModels.add(new cTreeModel(childIndex, parentIndex, 1, outcomes.get(j)));
            }

            /* next parent index */
            parentIndex = childIndex + 1;






//            /* impact parent */
//            cOutcomeModel outcomeModel = outcomeModels.get(i);
//            outcomeTreeModels.add(new cTreeModel(parentIndex, -1, 0, outcomeModel));
//
//            /* set of impact children under the sub-logframe of the outcome logframe */
//            Map<Pair<Long, Long>, Set<cImpactModel>> childImpactMap = outcomeModel.getChildImpactModelMap();
//            if (childImpactMap.size() > 0) {
//                for (Map.Entry<Pair<Long, Long>, Set<cImpactModel>> entry : childImpactMap.entrySet()) {
//                    childIndex = parentIndex + 1;
//                    ArrayList<cImpactModel> impacts = new ArrayList<>(entry.getValue());
//                    outcomeTreeModels.add(new cTreeModel(childIndex, parentIndex, 1, impacts));
//                    //Log.d(TAG,entry.getKey() + " = " + entry.getValue());
//                }
//            }
//
//            /* set of outcome children under the outcome */
//            ArrayList<cOutcomeModel> outcomes = new ArrayList<>(outcomeModel.getChildOutcomeModelSet());
//            if(outcomes.size() > 0) {
//                childIndex = parentIndex + 2;
//                outcomeTreeModels.add(new cTreeModel(childIndex, parentIndex, 1, outcomes));
//            }
//
//            /* set of outputs under the outcome */
//            ArrayList<cOutputModel> outputs = new ArrayList<>(outcomeModel.getOutputModelSet());
//            if(outputs.size() > 0) {
//                childIndex = parentIndex + 2;
//                outcomeTreeModels.add(new cTreeModel(childIndex, parentIndex, 1, outputs));
//            }
//
//            /* set of questions under the outcome */
//            ArrayList<cQuestionModel> questions = new ArrayList<>(outcomeModel.getQuestionModelSet());
//            if(questions.size() > 0) {
//                childIndex = parentIndex + 3;
//                outcomeTreeModels.add(new cTreeModel(childIndex, parentIndex, 1, questions));
//            }
//
//            /* set of raids under the outcome */
//            ArrayList<cRaidModel> raids = new ArrayList<>(outcomeModel.getRaidModelSet());
//            if(raids.size() > 0) {
//                childIndex = parentIndex + 4;
//                outcomeTreeModels.add(new cTreeModel(childIndex, parentIndex, 1, raids));
//            }
//            /* next parent index */
//            parentIndex = childIndex + 1;
        }
        return outcomeTreeModels;
    }

    @Override
    public void run() {
        if ((operationBITS & cBitwise.READ) != 0) {

            /* retrieve a set logFrames from the database */
            Log.d(TAG, "LOGFRAME ID = "+logFrameID+"; USER ID = "+userID+"; PRIMARY = "+primaryRoleBITS+
                    "; SECONDARY = "+secondaryRoleBITS+"; STATUS = "+statusBITS);

            Set<cOutcomeModel> outcomeModelSet = outcomeRepository.getOutcomeModelSet(logFrameID,
                    userID, primaryRoleBITS, secondaryRoleBITS, statusBITS);

            if (outcomeModelSet != null) {
                Gson gson = new Gson();
                Log.d(TAG, "IMPACT = "+gson.toJson(outcomeModelSet.size()));

                ArrayList<cTreeModel> outcomeTreeModels = getOutcomeTree(outcomeModelSet);

                postMessage(logFrameName, outcomeTreeModels);
            } else {
                notifyError("Failed to read impacts !!");
            }
        } else {
            notifyError("Failed due to reading access rights !!");
        }
    }
}
