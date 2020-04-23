package com.me.mseotsanyana.mande.BLL.interactors.common;

import com.me.mseotsanyana.mande.BLL.interactors.base.iInteractor;

public interface iUploadGlobalInteractor extends iInteractor {
    interface Callback{
        void onUploadGlobalCompleted(String s);
    }
}
