package com.me.mseotsanyana.mande.BLL.interactors.session.status.Impl;

import android.content.Context;

import com.google.gson.Gson;
import com.me.mseotsanyana.mande.BLL.executor.iExecutor;
import com.me.mseotsanyana.mande.BLL.executor.iMainThread;
import com.me.mseotsanyana.mande.BLL.interactors.base.cAbstractInteractor;
import com.me.mseotsanyana.mande.BLL.interactors.session.status.iStatusInteractor;
import com.me.mseotsanyana.mande.BLL.repository.session.iStatusRepository;
import com.me.mseotsanyana.mande.DAL.ìmpl.session.cStatusRepositoryImpl;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by mseotsanyana on 2017/08/28.
 */

public class cStatusInteractorImpl extends cAbstractInteractor implements iStatusInteractor {
    private static String TAG = cStatusInteractorImpl.class.getSimpleName();
    private cStatusRepositoryImpl statusDBA;
    private Context context;

    private Callback callback;
    private iStatusRepository statusRepository;

    Gson gson = new Gson();

    public cStatusInteractorImpl(iExecutor threadExecutor, iMainThread mainThread,
                                 iStatusRepository statusRepository,
                                 Callback callback) {
        super(threadExecutor, mainThread);

        if (statusRepository == null || callback == null) {
            throw new IllegalArgumentException("Arguments can not be null!");
        }
        this.statusRepository = statusRepository;
        this.callback = callback;
    }

    @Override
    public void run() {

    }
}
