package com.me.mseotsanyana.mande.BLL.interactors.logframe.logframe;

import com.me.mseotsanyana.mande.BLL.interactors.base.iInteractor;

public interface iCreateLogFrameInteractor extends iInteractor {
    interface Callback{
        void onLogFrameCreated();
    }
}
