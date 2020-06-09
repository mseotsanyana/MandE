package com.me.mseotsanyana.mande.BLL.interactors.logframe.logframe;

import com.me.mseotsanyana.mande.BLL.interactors.base.iInteractor;

public interface iDeleteLogFrameInteractor extends iInteractor {
    interface Callback{
        void onLogFrameDeleted(int position, String msg);
        void onLogFrameDeleteFailed(String msg);
        void onSubLogFrameDeleted(int position, String msg);
        void onSubLogFrameDeleteFailed(String msg);
    }
}
