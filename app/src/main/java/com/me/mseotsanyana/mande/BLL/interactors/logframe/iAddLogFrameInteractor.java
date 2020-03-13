package com.me.mseotsanyana.mande.BLL.interactors.logframe;

import com.me.mseotsanyana.mande.BLL.interactors.base.iInteractor;

public interface iAddLogFrameInteractor extends iInteractor {
    interface Callback{
        void onLogFrameAdded();
    }
}
