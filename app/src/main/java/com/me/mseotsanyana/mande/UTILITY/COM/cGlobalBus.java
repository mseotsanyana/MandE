package com.me.mseotsanyana.mande.UTILITY.COM;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by mseotsanyana on 2016/11/26.
 */

public class cGlobalBus {
    private static EventBus sBus;
    public static EventBus getBus() {
        if (sBus == null)
            sBus = EventBus.getDefault();
        return sBus;
    }
}
