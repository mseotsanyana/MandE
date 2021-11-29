package com.me.mseotsanyana.mande.PL.presenters.logframe.Impl;

import com.me.mseotsanyana.mande.BLL.executor.iExecutor;
import com.me.mseotsanyana.mande.BLL.executor.iMainThread;
import com.me.mseotsanyana.mande.BLL.interactors.logframe.logframe.Impl.cCreateLogFrameInteractorImpl;
import com.me.mseotsanyana.mande.BLL.interactors.logframe.logframe.Impl.cCreateSubLogFrameInteractorImpl;
import com.me.mseotsanyana.mande.BLL.interactors.logframe.logframe.Impl.cDeleteLogFrameInteractorImpl;
import com.me.mseotsanyana.mande.BLL.interactors.logframe.logframe.Impl.cDeleteSubLogFrameInteractorImpl;
import com.me.mseotsanyana.mande.BLL.interactors.logframe.logframe.Impl.cReadLogFrameInteractorImpl;
import com.me.mseotsanyana.mande.BLL.interactors.logframe.logframe.Impl.cUpdateLogFrameInteractorImpl;
import com.me.mseotsanyana.mande.BLL.interactors.logframe.logframe.Impl.cUploadLogFrameInteractorImpl;
import com.me.mseotsanyana.mande.BLL.interactors.logframe.logframe.iCreateLogFrameInteractor;
import com.me.mseotsanyana.mande.BLL.interactors.logframe.logframe.iCreateSubLogFrameInteractor;
import com.me.mseotsanyana.mande.BLL.interactors.logframe.logframe.iDeleteLogFrameInteractor;
import com.me.mseotsanyana.mande.BLL.interactors.logframe.logframe.iDeleteSubLogFrameInteractor;
import com.me.mseotsanyana.mande.BLL.interactors.logframe.logframe.iReadLogFrameInteractor;
import com.me.mseotsanyana.mande.BLL.interactors.logframe.logframe.iUpdateLogFrameInteractor;
import com.me.mseotsanyana.mande.BLL.interactors.logframe.logframe.iUploadLogFrameInteractor;
import com.me.mseotsanyana.mande.BLL.repository.logframe.iLogFrameRepository;
import com.me.mseotsanyana.mande.BLL.repository.session.iSharedPreferenceRepository;
import com.me.mseotsanyana.mande.BLL.model.logframe.cLogFrameModel;
import com.me.mseotsanyana.mande.PL.presenters.base.cAbstractPresenter;
import com.me.mseotsanyana.mande.PL.presenters.logframe.iLogFramePresenter;
import com.me.mseotsanyana.treeadapterlibrary.cTreeModel;

import java.util.List;

public class cLogFramePresenterImpl extends cAbstractPresenter implements iLogFramePresenter,
        iReadLogFrameInteractor.Callback, iUploadLogFrameInteractor.Callback,
        iCreateLogFrameInteractor.Callback, iCreateSubLogFrameInteractor.Callback,
        iUpdateLogFrameInteractor.Callback, iDeleteLogFrameInteractor.Callback,
        iDeleteSubLogFrameInteractor.Callback {

    //private static final String TAG = cLogFramePresenterImpl.class.getSimpleName();

    private View view;
    private final iSharedPreferenceRepository sharedPreferenceRepository;
    private final iLogFrameRepository logFrameRepository;

    public cLogFramePresenterImpl(iExecutor executor, iMainThread mainThread,
                                  View view,
                                  iSharedPreferenceRepository sharedPreferenceRepository,
                                  iLogFrameRepository logFrameRepository) {
        super(executor, mainThread);

        this.view = view;
        this.sharedPreferenceRepository = sharedPreferenceRepository;
        this.logFrameRepository = logFrameRepository;
    }

    /* ====================================== START CREATE ====================================== */
    /* create a sub-logframe model */
    @Override
    public void createLogFrameModel(cLogFrameModel logFrameModel) {
        iCreateLogFrameInteractor createLogFrameInteractor =
                new cCreateLogFrameInteractorImpl(executor, mainThread,
                        sharedPreferenceRepository,
                        logFrameRepository,
                        this,
                        logFrameModel);

        view.showProgress();
        createLogFrameInteractor.execute();
    }

    @Override
    public void createSubLogFrameModel(String logFrameID, cLogFrameModel logSubFrameModel) {
        iCreateSubLogFrameInteractor createSubLogFrameInteractor =
                new cCreateSubLogFrameInteractorImpl(executor, mainThread,
                        sharedPreferenceRepository,
                        logFrameRepository,
                        this,
                        logFrameID,
                        logSubFrameModel);

        view.showProgress();
        createSubLogFrameInteractor.execute();
    }

    /* create success  response to the view */
    @Override
    public void onLogFrameCreated(cLogFrameModel logFrameModel, String msg) {
        if (this.view != null) {
            this.view.onLogFrameCreated(logFrameModel, msg);
            this.view.hideProgress();
        }
    }

    @Override
    public void onSubLogFrameCreated(cLogFrameModel logFrameModel, String msg) {
        if (this.view != null) {
            this.view.onSubLogFrameCreated(logFrameModel, msg);
            this.view.hideProgress();
        }
    }

    /* create failure response to the view */
    @Override
    public void onLogFrameCreateFailed(String msg) {
        if (this.view != null) {
            this.view.onLogFrameCreateFailed(msg);
            this.view.hideProgress();
        }
    }

    @Override
    public void onSubLogFrameCreateFailed(String msg) {
        if (this.view != null) {
            this.view.onSubLogFrameCreateFailed(msg);
            this.view.hideProgress();
        }
    }

    /* ======================================= END CREATE ======================================= */

    /* ======================================= START READ ======================================= */
    @Override
    public void readLogFrames() {
        iReadLogFrameInteractor readLogFrameInteractor = new cReadLogFrameInteractorImpl(
                executor,
                mainThread,
                sharedPreferenceRepository,
                logFrameRepository,
                this);

        view.showProgress();
        readLogFrameInteractor.execute();
    }

    @Override
    public void onLogFramesRetrieved(List<cTreeModel> treeModels) {
        if (this.view != null) {
            this.view.onRetrieveLogFramesCompleted(treeModels);
            this.view.hideProgress();
        }
    }

    @Override
    public void onLogFramesRetrieveFailed(String msg) {
        if (this.view != null) {
            this.view.onRetrieveLogFramesFailed(msg);
            this.view.hideProgress();
        }
    }
    /* ======================================== END READ ======================================== */

    /* ====================================== START UPDATE ====================================== */
    @Override
    public void updateLogFrame(cLogFrameModel logFrameModel, int position) {
        iUpdateLogFrameInteractor updateLogFrameInteractor =
                new cUpdateLogFrameInteractorImpl(executor, mainThread,
                        logFrameRepository,
                        this,
                        logFrameModel,
                        position);

        view.showProgress();
        updateLogFrameInteractor.execute();
    }

    @Override
    public void onLogFrameUpdated(cLogFrameModel logFrameModel, int position, String msg) {
        if (this.view != null) {
            this.view.onLogFrameUpdated(logFrameModel, position, msg);
            this.view.hideProgress();
        }
    }

    @Override
    public void onLogFrameUpdateFailed(String msg) {

    }
    /* ======================================= END UPDATE ======================================= */

    /* ====================================== START DELETE ====================================== */
    @Override
    public void deleteLogFrameModel(String logFrameID, int position) {
        iDeleteLogFrameInteractor deleteLogFrameInteractor =
                new cDeleteLogFrameInteractorImpl(executor, mainThread,
                        logFrameRepository,
                        this,
                        logFrameID, position);

        view.showProgress();
        deleteLogFrameInteractor.execute();
    }

    @Override
    public void deleteSubLogFrameModel(String logSubFrameID, int position) {
        iDeleteSubLogFrameInteractor delSubLogFrameInteractor =
                new cDeleteSubLogFrameInteractorImpl(executor, mainThread,
                        logFrameRepository,
                        this,
                        logSubFrameID, position);

        view.showProgress();
        delSubLogFrameInteractor.execute();
    }

    @Override
    public void onLogFrameDeleted(int position, String msg) {
        if (this.view != null) {
            this.view.onLogFrameDeleted(position, msg);
            this.view.hideProgress();
        }
    }

    @Override
    public void onLogFrameDeleteFailed(String msg) {
        if (this.view != null) {
            this.view.onLogFrameDeleteFailed(msg);
            this.view.hideProgress();
        }
    }

    @Override
    public void onSubLogFrameDeleted(int position, String msg) {
        if (this.view != null) {
            this.view.onSubLogFrameDeleted(position, msg);
            this.view.hideProgress();
        }
    }

    @Override
    public void onSubLogFrameDeleteFailed(String msg) {
        if (this.view != null) {
            this.view.onSubLogFrameDeleteFailed(msg);
            this.view.hideProgress();
        }
    }

    /* ======================================= END DELETE ======================================= */

    /* ====================================== START UPLOAD ====================================== */
    @Override
    public void uploadLogFrameFromExcel(String filePath) {
        iUploadLogFrameInteractor uploadLogFrameInteractor =
                new cUploadLogFrameInteractorImpl(executor, mainThread,
                        sharedPreferenceRepository,
                        logFrameRepository,
                        this,
                        filePath);

        view.showProgress();
        uploadLogFrameInteractor.execute();
    }

    /* ======================================= END UPLOAD ======================================= */

    /* ==================================== START PREFERENCE ==================================== */

    @Override
    public void onUploadLogFrameCompleted(String msg) {
        if (this.view != null) {
            this.view.onUploadLogFrameCompleted(msg);
            this.view.hideProgress();
        }
    }

    @Override
    public void onUploadLogFrameFailed(String msg) {
        if (this.view != null) {
            this.view.onUploadLogFrameFailed(msg);
            this.view.hideProgress();
        }
    }

    /* ===================================== END PREFERENCE ===================================== */


    /* corresponding view functions */
    @Override
    public void resume() {
        readLogFrames();
    }

    @Override
    public void pause() {

    }

    @Override
    public void stop() {
        if (this.view != null) {
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
