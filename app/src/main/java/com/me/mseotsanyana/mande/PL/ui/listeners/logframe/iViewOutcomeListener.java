package com.me.mseotsanyana.mande.PL.ui.listeners.logframe;

import com.me.mseotsanyana.mande.DAL.model.logframe.cOutcomeModel;

public interface iViewOutcomeListener {
    void onClickUpdateOutcome(cOutcomeModel outcomeModel, int position);
    void onClickDeleteOutcome(long outcomeID, int position);
    void onClickSyncOutcome(cOutcomeModel outcomeModel, int position);
    void onClickBMBOutcome(int index);
}
