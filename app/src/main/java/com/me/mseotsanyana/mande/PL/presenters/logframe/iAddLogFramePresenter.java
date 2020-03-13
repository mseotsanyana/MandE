package com.me.mseotsanyana.mande.PL.presenters.logframe;

import com.me.mseotsanyana.mande.PL.presenters.base.iPresenter;
import com.me.mseotsanyana.mande.PL.ui.iBaseView;

public interface iAddLogFramePresenter extends iPresenter {
    interface View extends iBaseView {

        void onLogFrameAdded();
    }

    void addLogFramesFromExcel();
}

