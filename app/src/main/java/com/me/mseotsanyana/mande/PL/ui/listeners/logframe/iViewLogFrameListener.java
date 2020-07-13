package com.me.mseotsanyana.mande.PL.ui.listeners.logframe;

import com.me.mseotsanyana.mande.DAL.model.logframe.cLogFrameModel;

public interface iViewLogFrameListener {
    void onClickBMBLogFrame(int index, long logFrameID);
    void onClickCreateSubLogFrame(long logFrameID, cLogFrameModel logFrameModel);
    void onClickUpdateLogFrame(int position, cLogFrameModel logFrameModel);
    void onClickDeleteLogFrame(int position, long logframeID);
    void onClickSyncLogFrame(int position, cLogFrameModel logFrameModel);
}
