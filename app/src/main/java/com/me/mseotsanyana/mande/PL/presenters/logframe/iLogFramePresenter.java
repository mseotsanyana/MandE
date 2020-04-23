package com.me.mseotsanyana.mande.PL.presenters.logframe;

import com.me.mseotsanyana.mande.DAL.model.logframe.cLogFrameModel;
import com.me.mseotsanyana.mande.PL.presenters.base.iPresenter;
import com.me.mseotsanyana.mande.PL.ui.iBaseView;
import com.me.mseotsanyana.treeadapterlibrary.cTreeModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

public interface iLogFramePresenter extends iPresenter {
    interface View extends iBaseView {
        /* request functions to the view */
        void onClickCreateLogframe(cLogFrameModel logFrameModel);
        void onClickUpdateLogframe(cLogFrameModel logFrameModel, int position);
        void onClickDeleteLogframe(long logframeID);
        void onClickSyncLogframe(cLogFrameModel logFrameModel);
        void onClickBoomMenu(int menuIndex);

        /* response functions from the view */
        void onRetrieveLogFramesCompleted(LinkedHashMap<String, List<String>> menuModelSet,
                                          ArrayList<cTreeModel> logFrameModelSet);
        void onLogframeCreated(cLogFrameModel logFrameModel);
        void onLogframeUpdated(cLogFrameModel logFrameModel);
        void onLogframeDeleted(long logframeID);
        void onLogframeSynced(cLogFrameModel logFrameModel);
    }

    /* request functions to the interactor */
    void createLogframe();
    void readAllLogframes();
    void updateLogframe(long logframeID, String name, String description, Date startDate, Date endDate);
    void deleteLogframe();
    void syncLogframe();
}