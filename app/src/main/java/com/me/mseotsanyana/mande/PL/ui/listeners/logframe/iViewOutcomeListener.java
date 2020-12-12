package com.me.mseotsanyana.mande.PL.ui.listeners.logframe;

import com.me.mseotsanyana.mande.BLL.model.logframe.cOutcomeModel;

public interface iViewOutcomeListener {
    //void onClickDetailOutcome(cOutcomeModel[] outcomeModels, int position);
    void onClickUpdateOutcome(cOutcomeModel outcomeModel, int position);
    void onClickDeleteOutcome(long outcomeID, int position);
    void onClickSyncOutcome(cOutcomeModel outcomeModel, int position);
    void onClickBMBOutcome(int index);
}
