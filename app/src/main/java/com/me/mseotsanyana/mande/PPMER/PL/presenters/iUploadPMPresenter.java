package com.me.mseotsanyana.mande.PPMER.PL.presenters;

import com.me.mseotsanyana.mande.PPMER.PL.presenters.base.iPresenter;
import com.me.mseotsanyana.mande.PPMER.PL.ui.iBaseView;

public interface iUploadPMPresenter extends iPresenter {
    interface View extends iBaseView {

        void onUploadCompleted();
    }

    void uploadPMFromExcel();
}

