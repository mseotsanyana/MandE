package com.me.mseotsanyana.mande.PL.presenters.logframe;

import com.me.mseotsanyana.mande.BLL.model.logframe.cQuestionModel;
import com.me.mseotsanyana.mande.PL.presenters.base.iPresenter;
import com.me.mseotsanyana.mande.PL.ui.iBaseView;

import java.util.ArrayList;

public interface iQuestionPresenter extends iPresenter {
    interface View extends iBaseView {
        /* pass data from presenter to the view */
        void onClickDetailQuestion(cQuestionModel questionModel);
        void onClickUpdateQuestion(cQuestionModel questionModel, int position);
        void onClickDeleteQuestion(long questionID, int position);
        void onClickSyncQuestion(cQuestionModel questionModel);

        /* pass data from interactor to the view */
        void onQuestionModelsRetrieved(String logFrameName, ArrayList<cQuestionModel> questionModels);
        void onQuestionModelsFailed(String msg);
    }

    /* pass data from view to the interactor */
    void readQuestionModels(long logFrameID);

    /*
    void createQuestionModel(cQuestionModel questionModel);
    void updateQuestionModel(cQuestionModel questionModel, int position);
    void deleteQuestionModel(long questionID, int position);
    void syncQuestionModel(cQuestionModel questionModel);
     */
}