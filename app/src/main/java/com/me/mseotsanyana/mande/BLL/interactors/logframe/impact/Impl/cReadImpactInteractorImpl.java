package com.me.mseotsanyana.mande.BLL.interactors.logframe.impact.Impl;

import android.util.Log;

import com.google.gson.Gson;
import com.me.mseotsanyana.mande.BLL.executor.iExecutor;
import com.me.mseotsanyana.mande.BLL.executor.iMainThread;
import com.me.mseotsanyana.mande.BLL.interactors.base.cAbstractInteractor;
import com.me.mseotsanyana.mande.BLL.interactors.logframe.impact.iReadImpactInteractor;
import com.me.mseotsanyana.mande.BLL.repository.logframe.iImpactRepository;
import com.me.mseotsanyana.mande.BLL.repository.session.iSessionManagerRepository;
import com.me.mseotsanyana.mande.BLL.model.logframe.cImpactModel;
import com.me.mseotsanyana.mande.BLL.model.logframe.cOutcomeModel;
import com.me.mseotsanyana.mande.BLL.model.logframe.cQuestionModel;
import com.me.mseotsanyana.mande.BLL.model.logframe.cRaidModel;
import com.me.mseotsanyana.mande.DAL.storage.preference.cBitwise;
import com.me.mseotsanyana.treeadapterlibrary.cTreeModel;

import java.util.ArrayList;
import java.util.Set;

public class cReadImpactInteractorImpl extends cAbstractInteractor
        implements iReadImpactInteractor {
    private static String TAG = cReadImpactInteractorImpl.class.getSimpleName();

    private Callback callback;
    private iImpactRepository impactRepository;
    private long userID, logFrameID;
    private int primaryRoleBITS, secondaryRoleBITS, operationBITS, statusBITS;

    private String logFrameName;

    public cReadImpactInteractorImpl(iExecutor threadExecutor, iMainThread mainThread,
                                     iSessionManagerRepository sessionManagerRepository,
                                     iImpactRepository impactRepository,
                                     Callback callback, long logFrameID) {
        super(threadExecutor, mainThread);

        if (sessionManagerRepository == null || impactRepository == null || callback == null) {
            throw new IllegalArgumentException("Arguments can not be null!");
        }

        this.impactRepository = impactRepository;
        this.callback = callback;

        this.logFrameID = logFrameID;

        /* common attributes */
        this.userID = sessionManagerRepository.loadUserID();
        this.primaryRoleBITS = sessionManagerRepository.loadPrimaryRoleBITS();
        this.secondaryRoleBITS = sessionManagerRepository.loadSecondaryRoleBITS();

        /* attributes related to IMPACT entity */
        this.operationBITS = sessionManagerRepository.loadOperationBITS(
                cBitwise.IMPACT, cBitwise.LOGFRAME_MODULE);
        this.statusBITS = sessionManagerRepository.loadStatusBITS(
                cBitwise.IMPACT, cBitwise.LOGFRAME_MODULE, cBitwise.READ);
    }

    /* */
    private void notifyError(String msg) {
        mainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onImpactModelsFailed(msg);
            }
        });
    }

    /* */
    private void postMessage(String logFrameName, ArrayList<cTreeModel> impactTreeModels) {
        mainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onImpactModelsRetrieved(logFrameName, impactTreeModels);
            }
        });
    }

    private ArrayList<cTreeModel> getImpactTree(Set<cImpactModel> impactModelSet) {
        ArrayList<cTreeModel> impactTreeModels = new ArrayList<>();
        int parentIndex = 0, childIndex;

        ArrayList<cImpactModel> impactModels = new ArrayList<>(impactModelSet);
        if (impactModels.size() > 0) {
            logFrameName = impactModels.get(0).getLogFrameModel().getName();
        }

        for (int i = 0; i < impactModels.size(); i++) {

            /* impact parent */
            cImpactModel impactModel = impactModels.get(i);
            impactTreeModels.add(new cTreeModel(parentIndex, -1, 0, impactModel));

            /* set of impact children under the impact */
            childIndex = parentIndex;
            ArrayList<cImpactModel> impacts = new ArrayList<>(impactModel.getChildImpactModelSet());
            for (int j = 0; j < impacts.size(); j++) {
                childIndex = childIndex + 1;
                impactTreeModels.add(new cTreeModel(childIndex, parentIndex, 1, impacts.get(j)));
            }

            /* next parent index */
            parentIndex = childIndex + 1;

            /* set of outcomes under the impact
            childIndex = parentIndex + 2;
            ArrayList<cOutcomeModel> outcomes = new ArrayList<>(impactModel.getOutcomeModelSet());
            impactTreeModels.add(new cTreeModel(childIndex, parentIndex, 2, outcomes));*/

            /* set of questions under the impact
            childIndex = parentIndex + 3;
            ArrayList<cQuestionModel> questions = new ArrayList<>(impactModel.getQuestionModelSet());
            impactTreeModels.add(new cTreeModel(childIndex, parentIndex, 2, questions));*/

            /* set of raids under the impact
            childIndex = parentIndex + 4;
            ArrayList<cRaidModel> raids = new ArrayList<>(impactModel.getRaidModelSet());
            impactTreeModels.add(new cTreeModel(childIndex, parentIndex, 2, raids));*/
        }
        return impactTreeModels;
    }

    @Override
    public void run() {
        if ((operationBITS & cBitwise.READ) != 0) {

            /* retrieve a set logFrames from the database */
            Log.d(TAG, "LOGFRAME ID = "+logFrameID+"; USER ID = "+userID+"; PRIMARY = "+primaryRoleBITS+
                    "; SECONDARY = "+secondaryRoleBITS+"; STATUS = "+statusBITS);

            Set<cImpactModel> impactModelSet = impactRepository.getImpactModelSet(logFrameID,
                    userID, primaryRoleBITS, secondaryRoleBITS, statusBITS);

            if (impactModelSet != null) {
                Gson gson = new Gson();
                Log.d(TAG, "IMPACT = "+gson.toJson(impactModelSet.size()));

                ArrayList<cTreeModel> impactTreeModels = getImpactTree(impactModelSet);

                postMessage(logFrameName, impactTreeModels);
            } else {
                notifyError("Failed to read impacts !!");
            }
        } else {
            notifyError("Failed due to reading access rights !!");
        }
    }
}
