package com.me.mseotsanyana.mande.PL.ui.listeners.logframe;

import com.me.mseotsanyana.mande.DAL.model.logframe.cActivityModel;

public interface iViewActivityListener {
    void onClickUpdateActivity(int position, cActivityModel activityModel);
    void onClickDeleteActivity(int position, long outputID);
    void onClickSyncActivity(int position, cActivityModel activityModel);
    void onClickBMBActivity(int index);
}
