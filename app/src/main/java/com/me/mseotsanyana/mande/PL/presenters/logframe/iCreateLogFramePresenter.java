package com.me.mseotsanyana.mande.PL.presenters.logframe;

import com.me.mseotsanyana.mande.PL.presenters.base.iPresenter;
import com.me.mseotsanyana.mande.PL.ui.iBaseView;

public interface iCreateLogFramePresenter extends iPresenter {
    interface View extends iBaseView {
        void onClickCreateLogframe();
        void onClickLogframeCreated();
        void onLogframeCreated();
    }

    void addLogFramesFromExcel();
    void createLogframe();
}

