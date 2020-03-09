package com.me.mseotsanyana.mande.PPMER.PL.presenters;

import com.me.mseotsanyana.mande.PPMER.PL.presenters.base.iPresenter;
import com.me.mseotsanyana.mande.PPMER.PL.ui.iBaseView;

public interface iUploadMonitoringPresenter extends iPresenter {
    interface View extends iBaseView {

        void onUploadCompleted(String msg);
    }

    void uploadMonitoringFromExcel();
}

