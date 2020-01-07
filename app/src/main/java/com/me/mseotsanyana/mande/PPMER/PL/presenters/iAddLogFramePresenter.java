package com.me.mseotsanyana.mande.PPMER.PL.presenters;

import com.me.mseotsanyana.mande.PPMER.BLL.domain.cLogFrameDomain;
import com.me.mseotsanyana.mande.PPMER.PL.presenters.base.iPresenter;
import com.me.mseotsanyana.mande.PPMER.PL.ui.iBaseView;

import java.util.Set;

public interface iAddLogFramePresenter extends iPresenter {
    interface View extends iBaseView {

        void onLogFrameAdded();
    }

    void addLogFramesFromExcel();
}

