package com.me.mseotsanyana.mande.BLL.interactors.raid;

import com.me.mseotsanyana.mande.BLL.interactors.base.iInteractor;

public interface iUploadRAIDInteractor extends iInteractor {
    interface Callback{
        void onUploadRAIDCompleted(String s);
    }
}
