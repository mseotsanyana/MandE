package com.me.mseotsanyana.mande.PL.presenters.raid;

import com.me.mseotsanyana.mande.PL.presenters.base.iPresenter;
import com.me.mseotsanyana.mande.PL.ui.iBaseView;

public interface iUploadRAIDPresenter extends iPresenter {
    interface View extends iBaseView {

        void onUploadCompleted(String msg);
    }

    void uploadRAIDFromExcel();
}

