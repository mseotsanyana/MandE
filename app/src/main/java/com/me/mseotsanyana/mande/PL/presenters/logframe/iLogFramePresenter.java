package com.me.mseotsanyana.mande.PL.presenters.logframe;

import com.me.mseotsanyana.mande.DAL.model.logframe.cLogFrameModel;
import com.me.mseotsanyana.mande.DAL.model.session.cOrganizationModel;
import com.me.mseotsanyana.mande.PL.presenters.base.iPresenter;
import com.me.mseotsanyana.mande.PL.ui.adapters.common.cCommonFragmentAdapter;
import com.me.mseotsanyana.mande.PL.ui.iBaseView;
import com.me.mseotsanyana.treeadapterlibrary.cTreeModel;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public interface iLogFramePresenter extends iPresenter {
    interface View extends iBaseView {
        /* pass data from presenter to the view */
        void onClickBoomMenu(int menuIndex);
        void onClickCreateLogFrame(cLogFrameModel logFrameModel);
        void onClickCreateSubLogFrame(long logFrameID, cLogFrameModel logFrameModel);
        void onClickUpdateLogFrame(int position, cLogFrameModel logFrameModel);
        void onClickDeleteLogFrame(int position, long logframeID);
        void onClickDeleteSubLogFrame(int position, long logframeID);
        void onClickSyncLogFrame(cLogFrameModel logFrameModel);

        /* pass data from interactor to the view */
        void onRetrieveLogFramesCompleted(LinkedHashMap<String, List<String>> menuModelSet,
                                          ArrayList<cTreeModel> logFrameModelSet);

        void onLogFrameCreated(cLogFrameModel logFrameModel, String msg);
        void onLogFrameCreateFailed(String msg);
        void onSubLogFrameCreated(cLogFrameModel logFrameModel, String msg);
        void onSubLogFrameCreateFailed(String msg);


        void onLogFrameUpdated(cLogFrameModel logFrameModel, int position, String msg);
        void onLogFrameUpdateFailed(String msg);

        void onLogFrameDeleted(int position, String msg);
        void onSubLogFrameDeleted(int position, String msg);
        void onLogFrameDeleteFailed(String msg);
        void onSubLogFrameDeleteFailed(String msg);
        void onLogFrameSynced(cLogFrameModel logFrameModel);

        /* shared preferences */
        void onRetrieveSharedOrgsCompleted(ArrayList<cOrganizationModel> organizationModels);

        /* common details */
        cCommonFragmentAdapter onGetCommonFragmentAdapter();
    }

    /* pass data from view to the interactor */
    void createLogFrameModel(cLogFrameModel logFrameModel);
    void createSubLogFrameModel(long logFrameID, cLogFrameModel logSubFrameModel);
    void readAllLogFrames();
    void updateLogFrame(cLogFrameModel logFrameModel, int position);
    void deleteLogFrameModel(long logFrameID, int position);
    void deleteSubLogFrameModel(long logSubFrameID, int position);
    void syncLogFrameModel(cLogFrameModel logFrameModel);
    void readSharedOrganizations();
}