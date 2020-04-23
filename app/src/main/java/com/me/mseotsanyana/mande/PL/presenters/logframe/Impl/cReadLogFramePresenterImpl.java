package com.me.mseotsanyana.mande.PL.presenters.logframe.Impl;

import com.me.mseotsanyana.mande.BLL.executor.iExecutor;
import com.me.mseotsanyana.mande.BLL.executor.iMainThread;
import com.me.mseotsanyana.mande.BLL.interactors.logframe.logframe.Impl.cReadDesktopInteractorImpl;
import com.me.mseotsanyana.mande.BLL.interactors.logframe.logframe.iReadLogFrameInteractor;
import com.me.mseotsanyana.mande.BLL.repository.logframe.iLogFrameRepository;
import com.me.mseotsanyana.mande.BLL.repository.session.iMenuRepository;
import com.me.mseotsanyana.mande.BLL.repository.session.iSessionManagerRepository;
import com.me.mseotsanyana.mande.PL.presenters.base.cAbstractPresenter;
import com.me.mseotsanyana.mande.PL.presenters.logframe.iReadLogFramePresenter;
import com.me.mseotsanyana.treeadapterlibrary.cTreeModel;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class cReadLogFramePresenterImpl extends cAbstractPresenter implements iReadLogFramePresenter,
        iReadLogFrameInteractor.Callback {
    private static String TAG = cReadLogFramePresenterImpl.class.getSimpleName();

    private View view;
    private iSessionManagerRepository sessionManagerRepository;
    private iMenuRepository menuRepository;
    private iLogFrameRepository logFrameRepository;

    private int userID;

    public cReadLogFramePresenterImpl(iExecutor executor, iMainThread mainThread,
                                      View view,
                                      iSessionManagerRepository sessionManagerRepository,
                                      iMenuRepository menuRepository,
                                      iLogFrameRepository logFrameRepository,
                                      int userID) {
        super(executor, mainThread);

        this.view = view;
        this.sessionManagerRepository = sessionManagerRepository;
        this.menuRepository = menuRepository;
        this.logFrameRepository = logFrameRepository;
        this.userID = userID;
    }

    @Override
    public void readLogframe() {

        iReadLogFrameInteractor readDesktopInteractor = new cReadDesktopInteractorImpl(
                executor,
                mainThread,
                sessionManagerRepository,
                menuRepository,
                logFrameRepository,
                this,
                userID);

        view.showProgress();

        readDesktopInteractor.execute();
    }

    @Override
    public void onLogFramesRetrieved(LinkedHashMap<String, List<String>> expandableMenuItems,
                                     ArrayList<cTreeModel> logFrameTreeModels) {
        if(this.view != null) {
            this.view.onRetrieveLogFramesCompleted(expandableMenuItems, logFrameTreeModels);
            this.view.hideProgress();
        }
    }

    @Override
    public void onLogFramesRetrieveFailed(String msg) {

    }

    @Override
    public void resume() {
        readLogframe();
    }

    @Override
    public void pause() {

    }

    @Override
    public void stop() {
        if(this.view != null){
            this.view.hideProgress();
        }
    }

    @Override
    public void destroy() {
        this.view = null;
    }

    @Override
    public void onError(String message) {

    }
}
