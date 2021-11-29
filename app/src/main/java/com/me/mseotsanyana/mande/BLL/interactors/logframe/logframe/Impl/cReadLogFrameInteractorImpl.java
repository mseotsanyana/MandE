package com.me.mseotsanyana.mande.BLL.interactors.logframe.logframe.Impl;

import android.util.Log;

import com.me.mseotsanyana.mande.BLL.executor.iExecutor;
import com.me.mseotsanyana.mande.BLL.executor.iMainThread;
import com.me.mseotsanyana.mande.BLL.interactors.base.cAbstractInteractor;
import com.me.mseotsanyana.mande.BLL.interactors.cInteractorUtils;
import com.me.mseotsanyana.mande.BLL.interactors.logframe.logframe.iReadLogFrameInteractor;
import com.me.mseotsanyana.mande.BLL.repository.logframe.iLogFrameRepository;
import com.me.mseotsanyana.mande.BLL.model.logframe.cLogFrameModel;
import com.me.mseotsanyana.mande.BLL.repository.session.iSharedPreferenceRepository;
import com.me.mseotsanyana.mande.DAL.storage.preference.cSharedPreference;
import com.me.mseotsanyana.treeadapterlibrary.cTreeModel;

import java.util.ArrayList;
import java.util.List;

public class cReadLogFrameInteractorImpl extends cAbstractInteractor
        implements iReadLogFrameInteractor {
    private static final String TAG = cReadLogFrameInteractorImpl.class.getSimpleName();

    private final iReadLogFrameInteractor.Callback callback;
    private final iLogFrameRepository logFrameRepository;

    private final String userServerID;
    private final String organizationServerID;
    private final int primaryTeamBIT;
    private final List<Integer> secondaryTeamBITS;
    private final List<Integer> statusBITS;

    private final int entityBITS;
    private final int entitypermBITS;

    public cReadLogFrameInteractorImpl(iExecutor threadExecutor, iMainThread mainThread,
                                       iSharedPreferenceRepository sharedPreferenceRepository,
                                       iLogFrameRepository logFrameRepository,
                                       Callback callback) {
        super(threadExecutor, mainThread);

        if (sharedPreferenceRepository == null || logFrameRepository == null || callback == null) {
            throw new IllegalArgumentException("Arguments can not be null!");
        }

        this.logFrameRepository = logFrameRepository;
        this.callback = callback;

        // load user shared preferences
        this.userServerID = sharedPreferenceRepository.loadUserID();
        this.organizationServerID = sharedPreferenceRepository.loadOrganizationID();
        this.primaryTeamBIT = sharedPreferenceRepository.loadPrimaryTeamBIT();
        this.secondaryTeamBITS = sharedPreferenceRepository.loadSecondaryTeams();

        // load entity shared preferences
        this.entityBITS = sharedPreferenceRepository.loadEntityBITS(
                cSharedPreference.LOGFRAME_MODULE);
        this.entitypermBITS = sharedPreferenceRepository.loadEntityPermissionBITS(
                cSharedPreference.LOGFRAME_MODULE, cSharedPreference.LOGFRAME);
        this.statusBITS = sharedPreferenceRepository.loadOperationStatuses(
                cSharedPreference.LOGFRAME_MODULE, cSharedPreference.LOGFRAME,
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
        mainThread.post(() -> callback.onLogFramesRetrieveFailed(msg));
    }

    /* */
    private void postMessage(List<cTreeModel> treeModels) {
        mainThread.post(() -> callback.onLogFramesRetrieved(treeModels));
    }


    List<cTreeModel> getLogFrameTree(List<cLogFrameModel> logFrameModels) {
        List<cTreeModel> logFrameTreeModels = new ArrayList<>();
        int parentIndex = 0, childIndex;



        for (int i = 0; i < logFrameModels.size(); i++) {
            // create logframe without parent logframe
            if (logFrameModels.get(i).getParentServerID() == null) {
                logFrameTreeModels.add(
                        new cTreeModel(parentIndex, -1, 0, logFrameModels.get(i)));
            }

            // create parent logframe with child logframes
            childIndex = parentIndex;
            for (int j = 0; j < logFrameModels.size(); j++) {
                if (logFrameModels.get(i).getLogFrameServerID().equals(
                        logFrameModels.get(j).getParentServerID())) {
                    childIndex = childIndex + 1;
                    logFrameTreeModels.add(new cTreeModel(
                            childIndex, parentIndex, 1, logFrameModels.get(j)));
                }
            }
            parentIndex = childIndex + 1;
        }
        //Gson gson = new Gson();
        //Log.d(TAG, "====================="+gson.toJson(logFrameTreeModels));
        /* sort the tree models */
        //logFrameTreeModels.sort(cTreeModel.childTreeModel);
        return logFrameTreeModels;
    }

    @Override
    public void run() {
        if (cInteractorUtils.isSettingsNonNull(organizationServerID, userServerID, entityBITS,
                entitypermBITS, primaryTeamBIT, secondaryTeamBITS, statusBITS)) {
            if ((this.entityBITS & cSharedPreference.LOGFRAME) != 0) {
                if ((this.entitypermBITS & cSharedPreference.READ) != 0) {

                    logFrameRepository.readLogFrames(organizationServerID, userServerID,
                            primaryTeamBIT, secondaryTeamBITS, statusBITS,
                            new iLogFrameRepository.iReadLogFramesCallback() {
                                @Override
                                public void onReadLogFrameSucceeded(List<cLogFrameModel>
                                                                            logFrameModels) {

                                    List<cTreeModel> treeModels = getLogFrameTree(logFrameModels);
                                    postMessage(treeModels);
                                }

                                @Override
                                public void onReadLogFrameFailed(String msg) {
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

//    private LinkedHashMap<String, List<String>> getExpandableMenu(Set<cMenuModel> menuModelSet) {
//        LinkedHashMap<String, List<String>> expandableMenuItems = new LinkedHashMap<>();
//        /* sort the menu model set */
//        SortedSet<cMenuModel> sortedMenuModelSet = new TreeSet<>(Comparator.comparing(
//                cMenuModel::getMenuServerID));
//        sortedMenuModelSet.addAll(menuModelSet);
//
//        for (cMenuModel menuModel : sortedMenuModelSet) {
//            if (menuModel.getParentServerID() == 0) { /* FIXME: the parent ID should default
//                                                   to zero in a database! */
//                List<String> subMenu = new ArrayList<String>();
//
//                SortedSet<cMenuModel> subMenuModelSet = new TreeSet<>(Comparator.comparing(
//                        cMenuModel::getMenuServerID));
//                //subMenuModelSet.addAll(menuModel.getMenuModelSet());
//
//                for (cMenuModel subMenuModel : subMenuModelSet) {
//                    subMenu.add(subMenuModel.getName());
//                }
//
//                expandableMenuItems.put(menuModel.getName(), subMenu);
//            }
//        }
//        return expandableMenuItems;
//    }

//        /* retrieve a set logFrames from the database */
//        Log.d(TAG, "USER ID = " + userID + " PRIMARY = " + primaryRoleBITS +
//                " SECONDARY = " + secondaryRoleBITS + " STATUS = " + statusLogFrameBITS);
//        Set<cLogFrameModel> logFrameModelSet = logFrameRepository.getLogFrameModelSet(
//                userID, primaryRoleBITS, secondaryRoleBITS, statusLogFrameBITS);
//
//        if (menuModelSet != null && logFrameModelSet != null) {
//            Gson gson = new Gson();
//            Log.d(TAG, "LOGFRAME = " + gson.toJson(logFrameModelSet.size()));
//
//            LinkedHashMap<String, List<String>> expandableMenuItems = getExpandableMenu(
//                    menuModelSet);
//
//
//            /* used to search for individual and\or organization owners */
//            // indOwners = getIndividualOwners();
//            // orgOwners = getOrganizationOwners();
//
//
//        } else {
//            notifyError("Failed to load main menu items and Logframes !!");
//        }