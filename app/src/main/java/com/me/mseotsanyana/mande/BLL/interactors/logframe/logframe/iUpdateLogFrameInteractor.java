package com.me.mseotsanyana.mande.BLL.interactors.logframe.logframe;

import com.me.mseotsanyana.mande.BLL.interactors.base.iInteractor;
import com.me.mseotsanyana.mande.DAL.model.logframe.cLogFrameModel;

public interface iUpdateLogFrameInteractor extends iInteractor {
    interface Callback{
        void onLogFrameUpdated(cLogFrameModel logFrameModel, int position, String msg);
        void onLogFrameUpdateFailed(String msg);
    }
}
