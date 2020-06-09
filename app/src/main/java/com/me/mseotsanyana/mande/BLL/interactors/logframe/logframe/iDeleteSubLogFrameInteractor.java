package com.me.mseotsanyana.mande.BLL.interactors.logframe.logframe;

import com.me.mseotsanyana.mande.BLL.interactors.base.iInteractor;

public interface iDeleteSubLogFrameInteractor extends iInteractor {
    interface Callback{
        void onSubLogFrameDeleted(int position, String msg);
        void onSubLogFrameDeleteFailed(String msg);
    }
}
