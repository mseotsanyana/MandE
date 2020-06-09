package com.me.mseotsanyana.mande.BLL.interactors.logframe.logframe;

import com.me.mseotsanyana.mande.BLL.interactors.base.iInteractor;

public interface iSyncLogFrameInteractor extends iInteractor {
    interface Callback{
        void onLogFrameSynced();
        void onLogFrameSyncFailed(String msg);
    }
}
