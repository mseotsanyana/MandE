package com.me.mseotsanyana.mande.BLL.interactors.global;

import com.me.mseotsanyana.mande.BLL.interactors.base.iInteractor;

public interface iUploadGlobalInteractor extends iInteractor {
    interface Callback{
        void onUploadGlobalCompleted(String s);
    }
}
