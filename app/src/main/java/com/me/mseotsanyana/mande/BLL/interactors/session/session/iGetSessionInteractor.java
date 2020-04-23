package com.me.mseotsanyana.mande.BLL.interactors.session.session;

import com.me.mseotsanyana.mande.BLL.interactors.base.iInteractor;

public interface iGetSessionInteractor extends iInteractor {
    /* implemented in PresenterImpl and called in InteractorImpl */
    interface Callback{
        void onUploadingSessionFailed(String msg);
        void onUploadingSessionSucceeded(String msg);
    }
}
