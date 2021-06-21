package com.me.mseotsanyana.mande.BLL.interactors.session.team.Impl;

import android.util.Log;

import com.google.gson.Gson;
import com.me.mseotsanyana.mande.BLL.executor.iExecutor;
import com.me.mseotsanyana.mande.BLL.executor.iMainThread;
import com.me.mseotsanyana.mande.BLL.interactors.base.cAbstractInteractor;
import com.me.mseotsanyana.mande.BLL.interactors.session.team.iReadTeamInteractor;
import com.me.mseotsanyana.mande.BLL.model.session.cTeamModel;
import com.me.mseotsanyana.mande.BLL.model.session.cUserProfileModel;
import com.me.mseotsanyana.mande.BLL.repository.session.iSharedPreferenceRepository;
import com.me.mseotsanyana.mande.BLL.repository.session.iTeamRepository;
import com.me.mseotsanyana.treeadapterlibrary.cTreeModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class cReadTeamsWithMembersInteractorImpl extends cAbstractInteractor
        implements iReadTeamInteractor {
    private static String TAG = cReadTeamsWithMembersInteractorImpl.class.getSimpleName();

    private final long userServerID;
    private final int primaryTeamBIT;
    private final int secondaryTeamBITS;
    private final int operationBITS;
    private final int statusBITS;

    private String organizationServerID = "-M_V3K7Qok8iuitQuJol";

    private final Callback callback;
    private final iTeamRepository teamRepository;
    private final iSharedPreferenceRepository sharedPreferenceRepository;

    public cReadTeamsWithMembersInteractorImpl(iExecutor threadExecutor, iMainThread mainThread,
                                               iSharedPreferenceRepository sharedPreferenceRepository,
                                               iTeamRepository teamRepository,
                                               Callback callback) {
        super(threadExecutor, mainThread);

        if (sharedPreferenceRepository == null || teamRepository == null || callback == null) {
            throw new IllegalArgumentException("Arguments can not be null!");
        }

        this.sharedPreferenceRepository = sharedPreferenceRepository;
        this.teamRepository = teamRepository;
        this.callback = callback;

        userServerID = 0;
        primaryTeamBIT = 0;
        secondaryTeamBITS = 0;
        operationBITS = 0;
        statusBITS = 0;

//        /* common attributes */
//        this.userServerID = this.sharedPreferencesRepository.loadUserID();
//        this.primaryTeamBIT = this.sharedPreferencesRepository.loadPrimaryRoleBITS();
//        this.secondaryTeamBITS = this.sharedPreferencesRepository.loadSecondaryRoleBITS();
//
//        /* attributes related to Team Entity */
//        this.operationBITS = this.sharedPreferencesRepository.loadOperationBITS(
//                cBitwise.MENU, cBitwise.SESSION_MODULE);
//        this.statusBITS = this.sharedPreferencesRepository.loadStatusBITS(
//                cBitwise.MENU, cBitwise.SESSION_MODULE, cBitwise.READ);

    }

    /* */
    private void notifyError(String msg) {
        mainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onReadTeamsFailed(msg);
            }
        });
    }

    /* */
    private void teamsWithMembersMessage(List<cTreeModel> teamsMembersTree) {
        mainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onReadTeamsWithMembersSucceeded(teamsMembersTree);
            }
        });
    }

    @Override
    public void run() {
        Gson gson = new Gson();
        this.teamRepository.readTeamsWithMembers(userServerID, primaryTeamBIT,
                secondaryTeamBITS, operationBITS, statusBITS, organizationServerID,
                new iTeamRepository.iReadTeamModelCallback() {
            @Override
            public void onReadTeamsWithMembersSucceeded(Map<cTeamModel, List<cUserProfileModel>>
                                                                teamMembersMap) {
                List<cTreeModel> teamsMembersTree = new ArrayList<>();
                int parentIndex = 0, childIndex;
                for(Map.Entry<cTeamModel, List<cUserProfileModel>> entry: teamMembersMap.entrySet()){
                    /* a team */
                    cTeamModel teamModel = entry.getKey();
                    teamsMembersTree.add(new cTreeModel(parentIndex, -1, 0,
                            teamModel));

                    /* a list of team members under the team */
                    childIndex = parentIndex;
                    for(cUserProfileModel userProfileModel: entry.getValue()){
                        childIndex = childIndex + 1;
                        teamsMembersTree.add(new cTreeModel(childIndex, parentIndex, 1,
                                userProfileModel));
                    }
                    /* next parent index */
                    parentIndex = childIndex + 1;
                }

                Log.d(TAG, "teamMembersMap => "+gson.toJson(teamsMembersTree));
                teamsWithMembersMessage(teamsMembersTree);
            }

            @Override
            public void onReadTeamFailed(String msg) {
                notifyError(msg);
            }
        });
    }
}