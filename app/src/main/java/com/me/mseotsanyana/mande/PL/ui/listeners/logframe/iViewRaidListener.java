package com.me.mseotsanyana.mande.PL.ui.listeners.logframe;

import com.me.mseotsanyana.mande.BLL.model.logframe.cRaidModel;

public interface iViewRaidListener {
    void onClickUpdateRaid(int position, cRaidModel raidModel);
    void onClickDeleteRaid(int position, int raidID);
    void onClickSyncRaid(int position, cRaidModel raidModel);
    void onClickBMBRaid(int index);
}
