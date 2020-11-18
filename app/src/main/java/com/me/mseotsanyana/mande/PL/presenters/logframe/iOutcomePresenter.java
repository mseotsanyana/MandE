package com.me.mseotsanyana.mande.PL.presenters.logframe;

import com.me.mseotsanyana.mande.BLL.model.logframe.cOutcomeModel;
import com.me.mseotsanyana.mande.PL.presenters.base.iPresenter;
import com.me.mseotsanyana.mande.PL.ui.iBaseView;
import com.me.mseotsanyana.treeadapterlibrary.cTreeModel;

import java.util.ArrayList;

public interface iOutcomePresenter extends iPresenter {
    interface View extends iBaseView {
        /* pass data from presenter to the view */
        void onClickBMBOutcome(int menuIndex);
        void onClickCreateOutcome(cOutcomeModel outcomeModel);
        void onClickUpdateOutcome(cOutcomeModel outcomeModel, int position);
        void onClickDeleteOutcome(long outcomeID, int position);
        void onClickSyncOutcome(cOutcomeModel outcomeModel);

        /* pass data from interactor to the view */
        void onOutcomeModelsRetrieved(String logFrameName, ArrayList<cTreeModel> outcomeModelSet);
        void onOutcomeModelsFailed(String msg);
    }

    /* pass data from view to the interactor */
    void readOutcomeModels(long logFrameID);

    /*
    void createOutcomeModel(cOutcomeModel outcomeModel);
    void updateOutcomeModel(cOutcomeModel outcomeModel, int position);
    void deleteOutcomeModel(long outcomeID, int position);
    void syncOutcomeModel(cOutcomeModel outcomeModel);
     */
}