package com.me.mseotsanyana.mande.PL.presenters.logframe;

import com.me.mseotsanyana.mande.DAL.model.logframe.cOutputModel;
import com.me.mseotsanyana.mande.PL.presenters.base.iPresenter;
import com.me.mseotsanyana.mande.PL.ui.iBaseView;
import com.me.mseotsanyana.treeadapterlibrary.cTreeModel;

import java.util.ArrayList;

public interface iOutputPresenter extends iPresenter {
    interface View extends iBaseView {
        /* pass data from presenter to the view */
        void onClickBMBOutput(int menuIndex);
        void onClickCreateOutput(cOutputModel outputModel);
        void onClickUpdateOutput(cOutputModel outputModel, int position);
        void onClickDeleteOutput(long outputID, int position);
        void onClickSyncOutput(cOutputModel outputModel);

        /* pass data from interactor to the view */
        void onOutputModelsRetrieved(String logFrameName, ArrayList<cTreeModel> outputModelSet);
        void onOutputModelsFailed(String msg);
    }

    /* pass data from view to the interactor */
    void readOutputModels(long logFrameID);

    /*
    void createOutputModel(cOutputModel outputModel);
    void updateOutputModel(cOutputModel outputModel, int position);
    void deleteOutputModel(long outputID, int position);
    void syncOutputModel(cOutputModel outputModel);
     */
}