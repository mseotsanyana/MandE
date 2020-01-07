package com.me.mseotsanyana.mande.PPMER.BLL.interactors;

import com.me.mseotsanyana.mande.PPMER.BLL.interactors.base.iInteractor;

public interface iAddLogFrameInteractor extends iInteractor {
    interface Callback{
        void onLogFrameAdded();
    }
}
