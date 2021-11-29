package com.me.mseotsanyana.mande.BLL.interactors.logframe.logframe;

import com.me.mseotsanyana.mande.BLL.interactors.base.iInteractor;

public interface iUploadLogFrameInteractor extends iInteractor {
    interface Callback{
        void onUploadLogFrameCompleted(String msg);
        void onUploadLogFrameFailed(String msg);
    }
}
