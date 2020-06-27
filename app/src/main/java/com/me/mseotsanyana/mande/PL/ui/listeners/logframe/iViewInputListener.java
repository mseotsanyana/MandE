package com.me.mseotsanyana.mande.PL.ui.listeners.logframe;

import com.me.mseotsanyana.mande.DAL.model.logframe.cActivityModel;
import com.me.mseotsanyana.mande.DAL.model.logframe.cInputModel;

public interface iViewInputListener {
    void onClickUpdateInput(int position, cInputModel inputModel);
    void onClickDeleteInput(int position, int outputID);
    void onClickSyncInput(int position, cInputModel inputModel);
    void onClickBMBInput(int index);
}
