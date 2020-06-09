package com.me.mseotsanyana.mande.BLL.interactors.logframe.logframe;

import com.me.mseotsanyana.mande.BLL.interactors.base.iInteractor;
import com.me.mseotsanyana.mande.DAL.model.logframe.cLogFrameModel;

public interface iCreateSubLogFrameInteractor extends iInteractor {
    interface Callback{
        void onSubLogFrameCreated(cLogFrameModel logFrameModel, String msg);
        void onSubLogFrameCreateFailed(String msg);
    }
}
