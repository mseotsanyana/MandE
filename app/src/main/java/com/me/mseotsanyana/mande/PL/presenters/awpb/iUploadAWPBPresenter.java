package com.me.mseotsanyana.mande.PL.presenters.awpb;

import com.me.mseotsanyana.mande.PL.presenters.base.iPresenter;
import com.me.mseotsanyana.mande.PL.ui.iBaseView;

public interface iUploadAWPBPresenter extends iPresenter {
    interface View extends iBaseView {

        void onUploadCompleted(String msg);
    }

    void uploadAWPBFromExcel();
}

