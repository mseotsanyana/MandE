package com.me.mseotsanyana.mande.PL.presenters.logframe;

import com.me.mseotsanyana.mande.DAL.model.logframe.cOutcomeModel;
import com.me.mseotsanyana.mande.PL.presenters.base.iPresenter;
import com.me.mseotsanyana.mande.PL.ui.iBaseView;
import com.me.mseotsanyana.treeadapterlibrary.cTreeModel;

import java.util.ArrayList;

public interface iOutcomePresenter extends iPresenter {
    interface View extends iBaseView {
        /* pass data from presenter to the view */
        void onClickBMBOutcome(int menuIndex);
        void onClickCreateOutcome(cOutcomeModel outcomeModel);
        void onClickUpdateOutcome(int position, cOutcomeModel outcomeModel);
        void onClickDeleteOutcome(int position, long outcomeID);
        void onClickSyncOutcome(cOutcomeModel outcomeModel);

        /* pass data from interactor to the view */
        void onRetrieveOutcomesCompleted(String logFrameName, ArrayList<cTreeModel> outcomeModelSet);

        /*void onLogFrameCreated(cLogFrameModel logFrameModel, String msg);
        void onLogFrameCreateFailed(String msg);
        void onSubLogFrameCreated(cLogFrameModel logFrameModel, String msg);
        void onSubLogFrameCreateFailed(String msg);


        void onLogFrameUpdated(cLogFrameModel logFrameModel, int position, String msg);
        void onLogFrameUpdateFailed(String msg);

        void onLogFrameDeleted(int position, String msg);
        void onSubLogFrameDeleted(int position, String msg);
        void onLogFrameDeleteFailed(String msg);
        void onSubLogFrameDeleteFailed(String msg);
        void onLogFrameSynced(cLogFrameModel logFrameModel);*/

        /* shared preferences
        void onRetrieveSharedOrgsCompleted(ArrayList<cOrganizationModel> organizationModels);*/

        /* common details
        cCommonFragmentAdapter onGetCommonFragmentAdapter();*/
    }

    /* pass data from view to the interactor */
    //void createLogFrameModel(cLogFrameModel logFrameModel);
    //void createSubLogFrameModel(long logFrameID, cLogFrameModel logSubFrameModel);
    void readOutcomes(long logFrameID);
    //void updateLogFrame(cLogFrameModel logFrameModel, int position);
    //void deleteLogFrameModel(long logFrameID, int position);
    //void deleteSubLogFrameModel(long logSubFrameID, int position);
    //void syncImpactsModel(cLogFrameModel logFrameModel);
    //void readSharedOrganizations();
}