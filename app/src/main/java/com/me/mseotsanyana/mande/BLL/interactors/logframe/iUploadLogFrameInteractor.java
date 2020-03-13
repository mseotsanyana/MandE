package com.me.mseotsanyana.mande.BLL.interactors.logframe;

import com.me.mseotsanyana.mande.BLL.interactors.base.iInteractor;

public interface iUploadLogFrameInteractor extends iInteractor {
    interface Callback{
        void onUploadLogFrameCompleted(String s);
    }
}
