package com.me.mseotsanyana.mande.BLL.interactors.session;

import com.me.mseotsanyana.mande.BLL.interactors.base.iInteractor;

public interface iUploadSessionInteractor extends iInteractor {
    interface Callback{
        void onUploadingSessionFailed(String msg);
        void onUploadingSessionSucceeded(String msg);
    }
}
