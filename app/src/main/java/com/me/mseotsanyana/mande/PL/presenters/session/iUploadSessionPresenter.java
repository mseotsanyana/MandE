package com.me.mseotsanyana.mande.PL.presenters.session;

import com.me.mseotsanyana.mande.PL.presenters.base.iPresenter;
import com.me.mseotsanyana.mande.PL.ui.iBaseView;

public interface iUploadSessionPresenter extends iPresenter {
    interface View extends iBaseView {
        void onUploadCompleted(String msg);
    }

    void uploadSessionFromExcel();
}

