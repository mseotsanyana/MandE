package com.me.mseotsanyana.mande.PPMER.PL.presenters.base;

import com.me.mseotsanyana.mande.PPMER.BLL.executor.iExecutor;
import com.me.mseotsanyana.mande.PPMER.BLL.executor.iMainThread;

public abstract class cAbstractPresenter {
    protected iExecutor   executor;
    protected iMainThread mainThread;

    public cAbstractPresenter(iExecutor executor, iMainThread mainThread) {
        this.executor = executor;
        this.mainThread = mainThread;
    }
}