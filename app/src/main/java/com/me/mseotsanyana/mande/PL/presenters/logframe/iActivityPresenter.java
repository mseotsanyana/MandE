package com.me.mseotsanyana.mande.PL.presenters.logframe;

import com.me.mseotsanyana.mande.BLL.model.logframe.cActivityModel;
import com.me.mseotsanyana.mande.BLL.model.logframe.cLogFrameModel;
import com.me.mseotsanyana.mande.PL.presenters.base.iPresenter;
import com.me.mseotsanyana.mande.PL.ui.iBaseView;
import com.me.mseotsanyana.treeadapterlibrary.cTreeModel;

import java.util.ArrayList;

public interface iActivityPresenter extends iPresenter {
    interface View extends iBaseView {
        /* pass data from presenter to the view */
        void onClickBMBActivity(int menuIndex);
        void onClickCreateActivity(cActivityModel activityModel);
        void onClickUpdateActivity(cActivityModel activityModel, int position);
        void onClickDeleteActivity(long activityID, int position);
        void onClickSyncActivity(cActivityModel activityModel);

        /* pass data from interactor to the view */
        void onActivityModelsRetrieved(String logFrameName, ArrayList<cTreeModel> activityModelSet);
        void onActivityModelsFailed(String msg);
    }

    /* pass data from view to the interactor */
    void readActivityModels(cLogFrameModel logFrameModel);

    /*
    void createActivityModel(cActivityModel activityModel);
    void updateActivityModel(cActivityModel activityModel, int position);
    void deleteActivityModel(long outputID, int position);
    void syncActivityModel(cActivityModel activityModel);
     */
}