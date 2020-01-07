package com.me.mseotsanyana.mande;

import android.os.Handler;
import android.os.Looper;

import com.me.mseotsanyana.mande.PPMER.BLL.executor.iMainThread;

public class cMainThreadImpl implements iMainThread {
    private static iMainThread mainThread;

    private Handler handler;

    private cMainThreadImpl() {
        handler = new Handler(Looper.getMainLooper());
    }

    @Override
    public void post(Runnable runnable) {
        handler.post(runnable);
    }

    public static iMainThread getInstance() {
        if (mainThread == null) {
            mainThread = new cMainThreadImpl();
        }
        return mainThread;
    }
}
