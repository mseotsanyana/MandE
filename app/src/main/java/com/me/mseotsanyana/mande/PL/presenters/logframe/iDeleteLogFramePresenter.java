package com.me.mseotsanyana.mande.PL.presenters.logframe;

import com.me.mseotsanyana.mande.PL.presenters.base.iPresenter;
import com.me.mseotsanyana.mande.PL.ui.iBaseView;

public interface iDeleteLogFramePresenter  extends iPresenter {
    interface View extends iBaseView {
        void onClickDeleteLogframe(long logframeID);
        void onClickLogframeDeleted(long logframeID);
    }

    void deleteLogframe(int logframeID);
}
