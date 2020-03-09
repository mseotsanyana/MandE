package com.me.mseotsanyana.mande.PPMER.BLL.interactors;

import com.me.mseotsanyana.mande.PPMER.BLL.interactors.base.iInteractor;

public interface iUploadMonitoringInteractor extends iInteractor {
    interface Callback{
        void onUploadMonitoringCompleted(String s);
    }
}
