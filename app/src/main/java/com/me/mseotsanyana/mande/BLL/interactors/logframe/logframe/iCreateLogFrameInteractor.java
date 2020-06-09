package com.me.mseotsanyana.mande.BLL.interactors.logframe.logframe;

import com.me.mseotsanyana.mande.BLL.interactors.base.iInteractor;
import com.me.mseotsanyana.mande.DAL.model.logframe.cLogFrameModel;

public interface iCreateLogFrameInteractor extends iInteractor {
    interface Callback{
        void onLogFrameCreated(cLogFrameModel logFrameModel, String msg);
        void onLogFrameCreateFailed(String msg);
    }
}
