package com.me.mseotsanyana.mande.PL.presenters.logframe;

import com.me.mseotsanyana.mande.PL.presenters.base.iPresenter;
import com.me.mseotsanyana.mande.PL.ui.iBaseView;

public interface iSyncLogFramePresenter  extends iPresenter {
    interface View extends iBaseView {

        void onClickSyncLogframe();

        void onClickLogframeSynced();
    }

    void syncLogframe(int logframeID);
}