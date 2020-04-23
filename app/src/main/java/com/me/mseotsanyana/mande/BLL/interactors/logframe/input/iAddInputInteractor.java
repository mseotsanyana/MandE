package com.me.mseotsanyana.mande.BLL.interactors.logframe.input;

import com.me.mseotsanyana.mande.BLL.interactors.base.iInteractor;

public interface iAddInputInteractor extends iInteractor {
    interface Callback{
        void onLogFrameAdded();
    }
}
