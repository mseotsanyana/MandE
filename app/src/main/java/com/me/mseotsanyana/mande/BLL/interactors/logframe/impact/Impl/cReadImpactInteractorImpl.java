package com.me.mseotsanyana.mande.BLL.interactors.logframe.impact.Impl;

import android.util.Log;

import com.me.mseotsanyana.mande.BLL.executor.iExecutor;
import com.me.mseotsanyana.mande.BLL.executor.iMainThread;
import com.me.mseotsanyana.mande.BLL.interactors.base.cAbstractInteractor;
import com.me.mseotsanyana.mande.BLL.interactors.cInteractorUtils;
import com.me.mseotsanyana.mande.BLL.interactors.logframe.impact.iReadImpactInteractor;
import com.me.mseotsanyana.mande.BLL.model.logframe.cLogFrameModel;
import com.me.mseotsanyana.mande.BLL.model.logframe.cOutcomeModel;
import com.me.mseotsanyana.mande.BLL.repository.logframe.iImpactRepository;
import com.me.mseotsanyana.mande.BLL.model.logframe.cImpactModel;
import com.me.mseotsanyana.mande.BLL.repository.session.iSharedPreferenceRepository;
import com.me.mseotsanyana.mande.DAL.storage.preference.cSharedPreference;
import com.me.mseotsanyana.treeadapterlibrary.cTreeModel;

import java.util.ArrayList;
import java.util.List;

public class cReadImpactInteractorImpl extends cAbstractInteractor
        implements iReadImpactInteractor {
    private static final String TAG = cReadImpactInteractorImpl.class.getSimpleName();

    private final Callback callback;
    private final iImpactRepository impactRepository;

    private final cLogFrameModel logFrameModel; String organizationServerID;
    private final String userServerID;
    private final int primaryTeamBIT;
    private final List<Integer> secondaryTeamBITS;
    private final List<Integer> statusBITS;

    private final int entityBITS;
    private final int entitypermBITS;

    public cReadImpactInteractorImpl(iExecutor threadExecutor, iMainThread mainThread,
                                     iSharedPreferenceRepository sharedPreferenceRepository,
                                     iImpactRepository impactRepository,
                                     Callback callback, cLogFrameModel logFrameModel) {
        super(threadExecutor, mainThread);

        if (sharedPreferenceRepository == null || impactRepository == null || callback == null) {
            throw new IllegalArgumentException("Arguments can not be null!");
        }

        this.impactRepository = impactRepository;
        this.callback = callback;
        this.logFrameModel = logFrameModel;

        // load user shared preferences
        this.userServerID = sharedPreferenceRepository.loadUserID();
        this.organizationServerID = sharedPreferenceRepository.loadOrganizationID();
        this.primaryTeamBIT = sharedPreferenceRepository.loadPrimaryTeamBIT();
        this.secondaryTeamBITS = sharedPreferenceRepository.loadSecondaryTeams();

        // load entity shared preferences
        this.entityBITS = sharedPreferenceRepository.loadEntityBITS(
                cSharedPreference.LOGFRAME_MODULE);
        this.entitypermBITS = sharedPreferenceRepository.loadEntityPermissionBITS(
                cSharedPreference.LOGFRAME_MODULE, cSharedPreference.IMPACT);
        this.statusBITS = sharedPreferenceRepository.loadOperationStatuses(
                cSharedPreference.LOGFRAME_MODULE, cSharedPreference.IMPACT,
                cSharedPreference.READ);

        Log.d(TAG, " \n ORGANIZATION ID = " + this.organizationServerID +
                " \n USER ID = " + this.userServerID +
                " \n PRIMARY TEAM BIT = " + this.primaryTeamBIT +
                " \n UNIX TEAM BITS = " + this.secondaryTeamBITS +
                " \n ENTITY BITS = " + this.entityBITS +
                " \n ENTITY PERMISSION BITS = " + this.entitypermBITS +
                " \n OPERATION STATUSES = " + this.statusBITS);
    }

    /* */
    private void notifyError(String msg) {
        mainThread.post(() -> callback.onImpactModelsFailed(msg));
    }

    /* */
    private void postMessage(List<cTreeModel> impactTreeModels) {
        mainThread.post(() -> callback.onImpactModelsRetrieved(logFrameModel.getName(),
                impactTreeModels));
    }

    private List<cTreeModel> getImpactTree(List<cImpactModel> impactModels) {
        List<cTreeModel> impactTreeModels = new ArrayList<>();
        int parentIndex = 0, childIndex;

        for (int i = 0; i < impactModels.size(); i++) {

            /* impact parent */
            cImpactModel impactModel = impactModels.get(i);
            impactTreeModels.add(new cTreeModel(parentIndex, -1, 0, impactModel));

            /* impact details as child */
            childIndex = parentIndex + 1;
            impactTreeModels.add(new cTreeModel(childIndex, parentIndex, 1, impactModel));

            /* next parent index */
            parentIndex = childIndex + 1;

//            /* list of outcomes under the impact */
//            childIndex = parentIndex;
//            List<cOutcomeModel> outcomeModels = impactModel.getOutcomeModels();
//
//            Log.d(TAG, "IMPACT ID : "+impactModel.getComponentServerID()+" - OUTCOME SIZE : "+
//                    outcomeModels.size());
//
//            for (int j = 0; j < outcomeModels.size(); j++) {
//                childIndex = childIndex + 1;
//                impactTreeModels.add(new cTreeModel(childIndex, parentIndex, 1,
//                        outcomeModels.get(j)));
//            }
//
//            /* next parent index */
//            parentIndex = childIndex + 1;

        }
        return impactTreeModels;
    }

    @Override
    public void run() {
        if (cInteractorUtils.isSettingsNonNull(organizationServerID, userServerID, entityBITS,
                entitypermBITS, primaryTeamBIT, secondaryTeamBITS, statusBITS)) {
            if ((this.entityBITS & cSharedPreference.IMPACT) != 0) {
                if ((this.entitypermBITS & cSharedPreference.READ) != 0) {

                    impactRepository.readImpacts(logFrameModel.getLogFrameServerID(),
                            organizationServerID, userServerID, primaryTeamBIT,
                            secondaryTeamBITS, statusBITS,
                            new iImpactRepository.iReadImpactsCallback() {
                                @Override
                                public void onReadImpactSucceeded(List<cImpactModel> impactModels) {
                                    List<cTreeModel> treeModels = getImpactTree(impactModels);
                                    postMessage(treeModels);
                                }

                                @Override
                                public void onReadImpactFailed(String msg) {
                                    notifyError(msg);
                                }
                            });

                } else {
                    notifyError("Permission denied! Please contact your administrator");
                }
            } else {
                notifyError("No access to the entity! Please contact your administrator");
            }
        } else {
            notifyError("Error in default settings");
        }
    }
}

//        if ((operationBITS & cBitwise.READ) != 0) {
//
//            /* retrieve a set logFrames from the database */
//            Log.d(TAG, "LOGFRAME ID = "+logFrameID+"; USER ID = "+userID+"; PRIMARY = "+primaryRoleBITS+
//                    "; SECONDARY = "+secondaryRoleBITS+"; STATUS = "+statusBITS);
//
//            Set<cImpactModel> impactModelSet = //impactRepository.getImpactModelSet(logFrameID,
//                    userID, primaryRoleBITS, secondaryRoleBITS, statusBITS);
//
//            if (impactModelSet != null) {
//                Gson gson = new Gson();
//                Log.d(TAG, "IMPACT = "+gson.toJson(impactModelSet.size()));
//
//                ArrayList<cTreeModel> impactTreeModels = getImpactTree(impactModelSet);
//
//                postMessage(logFrameName, impactTreeModels);
//            } else {
//                notifyError("Failed to read impacts !!");
//            }
//        } else {
//            notifyError("Failed due to reading access rights !!");
//        }

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