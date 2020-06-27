package com.me.mseotsanyana.mande.PL.ui.listeners.logframe;

import com.me.mseotsanyana.mande.DAL.model.logframe.cOutcomeModel;

public interface iViewOutcomeListener {
    void onClickUpdateOutcome(int position, cOutcomeModel outcomeModel);
    void onClickDeleteOutcome(int position, int outcomeID);
    void onClickSyncOutcome(int position, cOutcomeModel outcomeModel);
    void onClickBMBOutcome(int index);
}
