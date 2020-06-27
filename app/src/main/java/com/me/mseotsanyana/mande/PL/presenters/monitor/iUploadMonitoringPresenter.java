package com.me.mseotsanyana.mande.PL.presenters.monitor;

import com.me.mseotsanyana.mande.PL.presenters.base.iPresenter;
import com.me.mseotsanyana.mande.PL.ui.iBaseView;

public interface iUploadMonitoringPresenter extends iPresenter {
    interface View extends iBaseView {

        void onUploadCompleted(String title, String msg);
    }

    void uploadMonitoringFromExcel();
}

