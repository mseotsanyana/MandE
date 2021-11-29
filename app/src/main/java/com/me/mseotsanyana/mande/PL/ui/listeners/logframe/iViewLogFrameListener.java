package com.me.mseotsanyana.mande.PL.ui.listeners.logframe;

import com.me.mseotsanyana.mande.BLL.model.logframe.cLogFrameModel;

public interface iViewLogFrameListener {
    void onClickBMBLogFrame(int index, cLogFrameModel logFrameModel);
    void onClickCreateSubLogFrame(String logFrameID, cLogFrameModel logFrameModel);
    void onClickUpdateLogFrame(int position, cLogFrameModel logFrameModel);
    void onClickDeleteLogFrame(int position, String logframeID);
}
