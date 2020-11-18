package com.me.mseotsanyana.mande.PL.presenters.logframe;

import com.me.mseotsanyana.mande.BLL.model.logframe.cInputModel;
import com.me.mseotsanyana.mande.PL.presenters.base.iPresenter;
import com.me.mseotsanyana.mande.PL.ui.iBaseView;
import com.me.mseotsanyana.treeadapterlibrary.cTreeModel;

import java.util.ArrayList;
import java.util.Map;

public interface iInputPresenter extends iPresenter {
    interface View extends iBaseView {
        /* pass data from presenter to the view */
        void onClickBMBInput(int menuIndex);
        void onClickCreateInput(cInputModel inputModel);
        void onClickUpdateInput(cInputModel inputModel, int position);
        void onClickDeleteInput(long outputID, int position);
        void onClickSyncInput(cInputModel inputModel);

        /* pass data from interactor to the view */
        void onInputModelsRetrieved(Map<Integer, ArrayList<cTreeModel>> inputModelSet);
        void onInputModelsFailed(String msg);
    }

    /* pass data from view to the interactor */
    void readInputModels(long logFrameID);

    /*
    void createInputModel(cInputModel inputModel);
    void updateInputModel(cInputModel inputModel, int position);
    void deleteInputModel(long inputID, int position);
    void syncInputModel(cInputModel inputModel);
     */
}