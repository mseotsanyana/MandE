package com.me.mseotsanyana.mande.COM;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.me.mseotsanyana.mande.BRBAC.PL.cSyncManager;

/**
 * Created by mseotsanyana on 2018/04/05.
 */

public class cBroadcastReceiver extends BroadcastReceiver{
    private static final String TAG = cBroadcastReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "Started services after complete booting");

        //cSyncManager scheduleJobServices = new cSyncManager(context);
        //scheduleJobServices.startServices();
    }
}
