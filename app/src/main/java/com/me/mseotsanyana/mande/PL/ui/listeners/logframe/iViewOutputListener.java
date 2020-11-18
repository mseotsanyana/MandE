package com.me.mseotsanyana.mande.PL.ui.listeners.logframe;

import com.me.mseotsanyana.mande.BLL.model.logframe.cOutputModel;

public interface iViewOutputListener {
    void onClickUpdateOutput(int position, cOutputModel outputModel);
    void onClickDeleteOutput(int position, long outputID);
    void onClickSyncOutput(int position, cOutputModel outputModel);
    void onClickBMBOutput(int index);
}
