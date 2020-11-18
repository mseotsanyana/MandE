package com.me.mseotsanyana.mande.BLL.interactors.logframe.activity.Impl;

import android.util.Log;

import com.google.gson.Gson;
import com.me.mseotsanyana.mande.BLL.executor.iExecutor;
import com.me.mseotsanyana.mande.BLL.executor.iMainThread;
import com.me.mseotsanyana.mande.BLL.interactors.base.cAbstractInteractor;
import com.me.mseotsanyana.mande.BLL.interactors.logframe.activity.iReadActivityInteractor;
import com.me.mseotsanyana.mande.BLL.repository.logframe.iActivityRepository;
import com.me.mseotsanyana.mande.BLL.repository.session.iSessionManagerRepository;
import com.me.mseotsanyana.mande.BLL.model.logframe.cActivityModel;
import com.me.mseotsanyana.mande.BLL.model.logframe.cOutputModel;
import com.me.mseotsanyana.mande.BLL.model.logframe.cQuestionModel;
import com.me.mseotsanyana.mande.BLL.model.logframe.cRaidModel;
import com.me.mseotsanyana.mande.DAL.storage.preference.cBitwise;
import com.me.mseotsanyana.treeadapterlibrary.cTreeModel;

import java.util.ArrayList;
import java.util.Set;

public class cReadActivityInteractorImpl extends cAbstractInteractor
        implements iReadActivityInteractor {
    private static String TAG = cReadActivityInteractorImpl.class.getSimpleName();

    private Callback callback;
    private iActivityRepository activityRepository;
    private long logFrameID, userID;
    private int primaryRoleBITS, secondaryRoleBITS, operationBITS, statusBITS;

    private String logFrameName;

    public cReadActivityInteractorImpl(iExecutor threadExecutor, iMainThread mainThread,
                                       iSessionManagerRepository sessionManagerRepository,
                                       iActivityRepository activityRepository,
                                       Callback callback, long logFrameID) {
        super(threadExecutor, mainThread);

        if (sessionManagerRepository == null || activityRepository == null || callback == null) {
            throw new IllegalArgumentException("Arguments can not be null!");
        }

        this.activityRepository = activityRepository;
        this.callback = callback;

        this.logFrameID = logFrameID;

        /* common attributes */
        this.userID = sessionManagerRepository.loadUserID();
        this.primaryRoleBITS = sessionManagerRepository.loadPrimaryRoleBITS();
        this.secondaryRoleBITS = sessionManagerRepository.loadSecondaryRoleBITS();

        /* attributes related to ACTIVITY entity */
        this.operationBITS = sessionManagerRepository.loadOperationBITS(
                cBitwise.ACTIVITY, cBitwise.LOGFRAME_MODULE);
        this.statusBITS = sessionManagerRepository.loadStatusBITS(
                cBitwise.ACTIVITY, cBitwise.LOGFRAME_MODULE, cBitwise.READ);
    }

    /* */
    private void notifyError(String msg) {
        mainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onActivityModelsFailed(msg);
            }
        });
    }

    /* */
    private void postMessage(String logFrameName, ArrayList<cTreeModel> activityTreeModels) {
        mainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onActivityModelsRetrieved(logFrameName, activityTreeModels);
            }
        });
    }

    private ArrayList<cTreeModel> getActivityTree(Set<cActivityModel> activityModelSet) {
        ArrayList<cTreeModel> activityTreeModels = new ArrayList<>();
        int parentIndex = 0, childIndex = 0;

        ArrayList<cActivityModel> activityModels = new ArrayList<>(activityModelSet);
        if (activityModelSet.size() > 0) {
            logFrameName = activityModels.get(0).getLogFrameModel().getName();
        }

        for (int i = 0; i < activityModels.size(); i++) {
            /* activity model */
            cActivityModel activityModel = activityModels.get(i);
            activityTreeModels.add(new cTreeModel(parentIndex, -1, 0, activityModel));

            /* set of output children under the sub-logframe of the activity logframe */
            ArrayList<cOutputModel> outputs = new ArrayList<>(activityModel.getChildOutputModelSet());
            if (outputs.size() > 0) {
                childIndex = parentIndex + 1;
                activityTreeModels.add(new cTreeModel(childIndex, parentIndex, 1, outputs));
            }

            /* set of activities children under the activity */
            ArrayList<cActivityModel> activities = new ArrayList<>(activityModel.getChildActivityModelSet());
            if (outputs.size() > 0) {
                childIndex = parentIndex + 2;
                activityTreeModels.add(new cTreeModel(childIndex, parentIndex, 1, activities));
            }

            /* set of inputs under the activity */
            ArrayList<cActivityModel> inputs = new ArrayList<>(activityModel.getChildActivityModelSet());
            if (activities.size() > 0) {
                childIndex = parentIndex + 3;
                activityTreeModels.add(new cTreeModel(childIndex, parentIndex, 1, inputs));
            }

            /* set of questions under the activity */
            ArrayList<cQuestionModel> questions = new ArrayList<>(activityModel.getQuestionModelSet());
            if (questions.size() > 0) {
                childIndex = parentIndex + 4;
                activityTreeModels.add(new cTreeModel(childIndex, parentIndex, 1, questions));
            }

            /* set of raids under the activity */
            ArrayList<cRaidModel> raids = new ArrayList<>(activityModel.getRaidModelSet());
            if (raids.size() > 0) {
                childIndex = parentIndex + 5;
                activityTreeModels.add(new cTreeModel(childIndex, parentIndex, 1, raids));
            }
            /* next parent index */
            parentIndex = childIndex + 1;
        }
        return activityTreeModels;
    }

    @Override
    public void run() {
        if ((operationBITS & cBitwise.READ) != 0) {

            /* retrieve a set logFrames from the database */
            Log.d(TAG, "LOGFRAME ID = " + logFrameID + "; USER ID = " + userID +
                    "; PRIMARY = " + primaryRoleBITS + "; SECONDARY = " + secondaryRoleBITS +
                    "; STATUS = " + statusBITS);

            Set<cActivityModel> activityModelSet = activityRepository.getActivityModelSet(logFrameID,
                    userID, primaryRoleBITS, secondaryRoleBITS, statusBITS);

            if (activityModelSet != null) {
                Gson gson = new Gson();
                Log.d(TAG, "ACTIVITY = " + gson.toJson(activityModelSet.size()));

                ArrayList<cTreeModel> activityTreeModels = getActivityTree(activityModelSet);

                postMessage(logFrameName, activityTreeModels);
            } else {
                notifyError("Failed to read activities !!");
            }
        } else {
            notifyError("Failed due to reading access rights !!");
        }
    }
}
