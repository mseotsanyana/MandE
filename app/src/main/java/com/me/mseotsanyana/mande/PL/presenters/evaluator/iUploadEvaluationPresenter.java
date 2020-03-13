package com.me.mseotsanyana.mande.PL.presenters.evaluator;

import com.me.mseotsanyana.mande.PL.presenters.base.iPresenter;
import com.me.mseotsanyana.mande.PL.ui.iBaseView;

public interface iUploadEvaluationPresenter extends iPresenter {
    interface View extends iBaseView {

        void onUploadCompleted(String msg);
    }

    void uploadEvaluationFromExcel();
}

