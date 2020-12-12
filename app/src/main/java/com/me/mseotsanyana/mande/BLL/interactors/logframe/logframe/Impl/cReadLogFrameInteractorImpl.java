package com.me.mseotsanyana.mande.BLL.interactors.logframe.logframe.Impl;

import android.util.Log;

import com.google.gson.Gson;
import com.me.mseotsanyana.mande.BLL.executor.iExecutor;
import com.me.mseotsanyana.mande.BLL.executor.iMainThread;
import com.me.mseotsanyana.mande.BLL.interactors.base.cAbstractInteractor;
import com.me.mseotsanyana.mande.BLL.interactors.logframe.logframe.iReadLogFrameInteractor;
import com.me.mseotsanyana.mande.BLL.repository.logframe.iLogFrameRepository;
import com.me.mseotsanyana.mande.BLL.repository.session.iMenuRepository;
import com.me.mseotsanyana.mande.BLL.repository.session.iSessionManagerRepository;
import com.me.mseotsanyana.mande.BLL.model.logframe.cLogFrameModel;
import com.me.mseotsanyana.mande.BLL.model.session.cMenuModel;
import com.me.mseotsanyana.mande.DAL.storage.preference.cBitwise;
import com.me.mseotsanyana.treeadapterlibrary.cTreeModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

public class cReadLogFrameInteractorImpl extends cAbstractInteractor
        implements iReadLogFrameInteractor {
    private static String TAG = cReadLogFrameInteractorImpl.class.getSimpleName();

    private Callback callback;
    private iSessionManagerRepository sessionManagerRepository;
    private iMenuRepository menuRepository;
    private iLogFrameRepository logFrameRepository;
    private long userID;
    private int primaryRoleBITS, secondaryRoleBITS,
            operationMenuBITS, statusMenuBITS, operationLogFrameBITS, statusLogFrameBITS;

    public cReadLogFrameInteractorImpl(iExecutor threadExecutor, iMainThread mainThread,
                                       iSessionManagerRepository sessionManagerRepository,
                                       iMenuRepository menuRepository,
                                       iLogFrameRepository logFrameRepository,
                                       Callback callback) {
        super(threadExecutor, mainThread);

        if (sessionManagerRepository == null || menuRepository == null ||
                logFrameRepository == null || callback == null) {
            throw new IllegalArgumentException("Arguments can not be null!");
        }

        this.sessionManagerRepository = sessionManagerRepository;
        this.menuRepository = menuRepository;
        this.logFrameRepository = logFrameRepository;
        this.callback = callback;

        /* common attributes */
        this.userID = this.sessionManagerRepository.loadUserID();
        this.primaryRoleBITS = this.sessionManagerRepository.loadPrimaryRoleBITS();
        this.secondaryRoleBITS = this.sessionManagerRepository.loadSecondaryRoleBITS();

        /* attributes related to MENU entity */
        this.operationMenuBITS = this.sessionManagerRepository.loadOperationBITS(
                cBitwise.MENU, cBitwise.SESSION_MODULE);
        this.statusMenuBITS = this.sessionManagerRepository.loadStatusBITS(
                cBitwise.MENU, cBitwise.SESSION_MODULE, cBitwise.READ);

        /* attributes related to LOGFRAME entity */
        this.operationLogFrameBITS = this.sessionManagerRepository.loadOperationBITS(
                cBitwise.LOGFRAME, cBitwise.LOGFRAME_MODULE);
        this.statusLogFrameBITS = this.sessionManagerRepository.loadStatusBITS(
                cBitwise.LOGFRAME, cBitwise.LOGFRAME_MODULE, cBitwise.READ);
    }

    /* */
    private void notifyError(String msg) {
        mainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onLogFramesRetrieveFailed(msg);
            }
        });
    }

    /* */
    private void postMessage(LinkedHashMap<String, List<String>> expandableMenuItems,
                             ArrayList<cTreeModel> logFrameTreeModels) {
        mainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onLogFramesRetrieved(expandableMenuItems, logFrameTreeModels);
            }
        });
    }

    private LinkedHashMap<String, List<String>> getExpandableMenu(Set<cMenuModel> menuModelSet) {
        LinkedHashMap<String, List<String>> expandableMenuItems = new LinkedHashMap<>();
        /* sort the menu model set */
        SortedSet<cMenuModel> sortedMenuModelSet = new TreeSet<>(Comparator.comparing(
                cMenuModel::getMenuID));
        sortedMenuModelSet.addAll(menuModelSet);

        for (cMenuModel menuModel : sortedMenuModelSet) {
            if (menuModel.getParentID() == 0) { /* FIXME: the parent ID should default
                                                   to zero in a database! */
                List<String> subMenu = new ArrayList<String>();

                SortedSet<cMenuModel> subMenuModelSet = new TreeSet<>(Comparator.comparing(
                        cMenuModel::getMenuID));
                subMenuModelSet.addAll(menuModel.getMenuModelSet());

                for (cMenuModel subMenuModel : subMenuModelSet) {
                    subMenu.add(subMenuModel.getName());
                }

                expandableMenuItems.put(menuModel.getName(), subMenu);
            }
        }
        return expandableMenuItems;
    }

    ArrayList<cTreeModel> getLogFrameTree(Set<cLogFrameModel> logFrameModelSet) {
        ArrayList<cTreeModel> logFrameTreeModels = new ArrayList<>();
        int parentIndex = 0, childIndex = 0;
        ArrayList<cLogFrameModel> logFrameModels = new ArrayList<>(logFrameModelSet);

        for (int i = 0; i < logFrameModels.size(); i++) {

            cLogFrameModel logFrameModel = logFrameModels.get(i);
            if (logFrameModels.get(i).getLogFrameParentID() == 0) {
                logFrameTreeModels.add(
                        new cTreeModel(parentIndex, -1, 0, logFrameModel));

                ArrayList<cLogFrameModel> subLogFrameModels = new ArrayList<>(
                        logFrameModel.getLogFrameModelSet());


                childIndex = parentIndex;
                for (int j = 0; j < subLogFrameModels.size(); j++) {
                    childIndex = childIndex + 1;
                    logFrameTreeModels.add(new cTreeModel(
                            childIndex, parentIndex, 1, subLogFrameModels.get(j)));
                }
                parentIndex = childIndex + 1;
            }
        }

        /* sort the tree models */
        Collections.sort(logFrameTreeModels, cTreeModel.childTreeModel);

        return logFrameTreeModels;
    }

    @Override
    public void run() {
        if (((operationMenuBITS & operationLogFrameBITS & cBitwise.READ) != 0)) {
            /* retrieve a set menu items from the database */
            Set<cMenuModel> menuModelSet = menuRepository.getMenuModelSet(
                    userID, primaryRoleBITS, secondaryRoleBITS, statusMenuBITS);

            /* retrieve a set logFrames from the database */
            Log.d(TAG, "USER ID = "+userID+" PRIMARY = "+primaryRoleBITS+
                    " SECONDARY = "+secondaryRoleBITS+" STATUS = "+statusLogFrameBITS);
            Set<cLogFrameModel> logFrameModelSet = logFrameRepository.getLogFrameModelSet(
                    userID, primaryRoleBITS, secondaryRoleBITS, statusLogFrameBITS);

            if (menuModelSet != null && logFrameModelSet != null) {
                Gson gson = new Gson();
                Log.d(TAG, "LOGFRAME = "+gson.toJson(logFrameModelSet.size()));

                LinkedHashMap<String, List<String>> expandableMenuItems = getExpandableMenu(
                        menuModelSet);
                ArrayList<cTreeModel> logFrameTreeModels = getLogFrameTree(logFrameModelSet);

                /* used to search for individual and\or organization owners */
                // indOwners = getIndividualOwners();
                // orgOwners = getOrganizationOwners();

                postMessage(expandableMenuItems, logFrameTreeModels);
            } else {
                notifyError("Failed to load main menu items and Logframes !!");
            }
        } else {
            notifyError("Failed due to access rights to main menu items and Logframes !!");
        }
    }
}
