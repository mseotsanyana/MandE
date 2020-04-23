package com.me.mseotsanyana.mande.PL.presenters.logframe;

import com.me.mseotsanyana.mande.DAL.model.logframe.cLogFrameModel;
import com.me.mseotsanyana.mande.PL.presenters.base.iPresenter;
import com.me.mseotsanyana.mande.PL.ui.iBaseView;
import com.me.mseotsanyana.treeadapterlibrary.cTreeModel;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public interface iReadLogFramePresenter extends iPresenter {
    interface View extends iBaseView {
        void onRetrieveLogFramesCompleted(LinkedHashMap<String, List<String>> menuModelSet,
                                          ArrayList<cTreeModel> logFrameModelSet);
        void onClickBoomMenu(int menuIndex);

    }

    /* request functions to the interactor */
    void readLogframe();

}
//void createLogframe(cLogFrameModel logFrameModel);
//void updateLogframe(cLogFrameModel logFrameModel);
//void deleteLogframe(long costId);
//void syncLogframe(cLogFrameModel logFrameModel);

/* request functions to the view */
//void onClickCreateLogframe(cLogFrameModel logFrameModel);
//void onClickUpdateLogframe(cLogFrameModel logFrameModel);
//void onClickDeleteLogframe(long logframeID);
//void onClickSyncLogframe(cLogFrameModel logFrameModel);

/* response functions from the view */
//void onLogframeCreated(cLogFrameModel logFrameModel);
//void onLogframeUpdated(cLogFrameModel logFrameModel);
//void onLogFrameDeleted(cLogFrameModel logFrameModel);
//void onLogFrameSynced(cLogFrameModel logFrameModel);
