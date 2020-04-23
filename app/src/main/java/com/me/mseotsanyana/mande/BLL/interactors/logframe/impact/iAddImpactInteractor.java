package com.me.mseotsanyana.mande.BLL.interactors.logframe.impact;

import com.me.mseotsanyana.mande.BLL.interactors.base.iInteractor;

public interface iAddImpactInteractor extends iInteractor {
    interface Callback{
        void onLogFrameAdded();
    }
}
