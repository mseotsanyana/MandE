package com.me.mseotsanyana.mande.PL.presenters.evaluator.Impl;

import com.me.mseotsanyana.mande.BLL.executor.iExecutor;
import com.me.mseotsanyana.mande.BLL.executor.iMainThread;
import com.me.mseotsanyana.mande.BLL.interactors.evaluator.Impl.cReadEvaluationInteractorImpl;
import com.me.mseotsanyana.mande.BLL.interactors.evaluator.iReadEvaluationInteractor;
import com.me.mseotsanyana.mande.BLL.repository.evaluator.iEvaluationRepository;
import com.me.mseotsanyana.mande.BLL.repository.session.iSessionManagerRepository;
import com.me.mseotsanyana.mande.PL.presenters.base.cAbstractPresenter;
import com.me.mseotsanyana.mande.PL.presenters.evaluator.iEvaluationPresenter;
import com.me.mseotsanyana.treeadapterlibrary.cTreeModel;

import java.util.ArrayList;

public class cEvaluationPresenterImpl extends cAbstractPresenter implements iEvaluationPresenter,
        iReadEvaluationInteractor.Callback/*, iReadSharedOrgsInteractor.Callback,
        iCreateLogFrameInteractor.Callback, iCreateSubLogFrameInteractor.Callback,
        iUpdateLogFrameInteractor.Callback, iDeleteLogFrameInteractor.Callback,
        iDeleteSubLogFrameInteractor.Callback*/{
    private static String TAG = cEvaluationPresenterImpl.class.getSimpleName();

    private View view;
    private iSessionManagerRepository sessionManagerRepository;
    private iEvaluationRepository evaluationRepository;
    private long logFrameID;

    public cEvaluationPresenterImpl(iExecutor executor, iMainThread mainThread,
                                    View view,
                                    iSessionManagerRepository sessionManagerRepository,
                                    iEvaluationRepository evaluationRepository, long logFrameID) {
        super(executor, mainThread);

        this.view = view;
        this.sessionManagerRepository = sessionManagerRepository;
        this.evaluationRepository = evaluationRepository;
        this.logFrameID = logFrameID;
    }

    /* ====================================== START CREATE ====================================== */
    /* create a sub-logframe model
    @Override
    public void createLogFrameModel(cLogFrameModel logFrameModel) {
        iCreateLogFrameInteractor createLogFrameInteractor = new cCreateLogFrameInteractorImpl(
                executor,
                mainThread,
                sessionManagerRepository,
                logFrameRepository,
                this,
                logFrameModel);

        view.showProgress();
        createLogFrameInteractor.execute();
    }

    @Override
    public void createSubLogFrameModel(long logFrameID, cLogFrameModel logSubFrameModel) {
        iCreateSubLogFrameInteractor createSubLogFrameInteractor = new cCreateSubLogFrameInteractorImpl(
                executor,
                mainThread,
                sessionManagerRepository,
                logFrameRepository,
                this,
                logFrameID,
                logSubFrameModel);

        view.showProgress();
        createSubLogFrameInteractor.execute();
    }
*/
    /* create success  response to the view
    @Override
    public void onLogFrameCreated(cLogFrameModel logFrameModel, String msg) {
        if(this.view != null) {
            this.view.onLogFrameCreated(logFrameModel, msg);
            this.view.hideProgress();
        }
    }

    @Override
    public void onSubLogFrameCreated(cLogFrameModel logFrameModel, String msg) {
        if(this.view != null) {
            this.view.onSubLogFrameCreated(logFrameModel, msg);
            this.view.hideProgress();
        }
    }
*/
    /* create failure response to the view
    @Override
    public void onLogFrameCreateFailed(String msg) {
        if(this.view != null) {
            this.view.onLogFrameCreateFailed(msg);
            this.view.hideProgress();
        }
    }

    @Override
    public void onSubLogFrameCreateFailed(String msg) {
        if(this.view != null) {
            this.view.onSubLogFrameCreateFailed(msg);
            this.view.hideProgress();
        }
    }
 */
    /* ======================================= END CREATE ======================================= */

    /* ======================================= START READ ======================================= */
    @Override
    public void readEvaluationModels(long logFrameID) {
        iReadEvaluationInteractor readEvaluationInteractor = new cReadEvaluationInteractorImpl(
                executor,
                mainThread,
                sessionManagerRepository,
                evaluationRepository,
                this,
                logFrameID);

        view.showProgress();
        readEvaluationInteractor.execute();
    }

    @Override
    public void onEvaluationModelsRetrieved(ArrayList<cTreeModel> evaluationTreeModels) {
        if(this.view != null) {
            this.view.onEvaluationModelsRetrieved(evaluationTreeModels);
            this.view.hideProgress();
        }
    }

    @Override
    public void onEvaluationModelsFailed(String msg) {
        if(this.view != null) {
            this.view.onEvaluationModelsFailed(msg);
            this.view.hideProgress();
        }
    }

    /* ======================================== END READ ======================================== */

    /* ====================================== START UPDATE ====================================== */
    /*
    @Override
    public void updateLogFrame(cLogFrameModel logFrameModel, int position) {
        iUpdateLogFrameInteractor updateLogFrameInteractor = new cUpdateLogFrameInteractorImpl(
                executor,
                mainThread,
                logFrameRepository,
                this,
                logFrameModel,
                position);

        view.showProgress();
        updateLogFrameInteractor.execute();
    }

    @Override
    public void onLogFrameUpdated(cLogFrameModel logFrameModel, int position, String msg) {
        if(this.view != null) {
            this.view.onLogFrameUpdated(logFrameModel, position, msg);
            this.view.hideProgress();
        }
    }

    @Override
    public void onLogFrameUpdateFailed(String msg) {

    }
     */
    /* ======================================= END UPDATE ======================================= */

    /* ====================================== START DELETE ====================================== */
    /*
    @Override
    public void deleteLogFrameModel(long logFrameID, int position) {
        iDeleteLogFrameInteractor deleteLogFrameInteractor = new cDeleteLogFrameInteractorImpl(
                executor,
                mainThread,
                logFrameRepository,
                this,
                logFrameID, position);

        view.showProgress();
        deleteLogFrameInteractor.execute();
    }

    @Override
    public void deleteSubLogFrameModel(long logSubFrameID, int position) {
        iDeleteSubLogFrameInteractor delSubLogFrameInteractor = new cDeleteSubLogFrameInteractorImpl(
                executor,
                mainThread,
                logFrameRepository,
                this,
                logSubFrameID, position);

        view.showProgress();
        delSubLogFrameInteractor.execute();
    }

    @Override
    public void onLogFrameDeleted(int position, String msg) {
        if(this.view != null) {
            this.view.onLogFrameDeleted(position, msg);
            this.view.hideProgress();
        }
    }

    @Override
    public void onLogFrameDeleteFailed(String msg) {
        if(this.view != null) {
            this.view.onLogFrameDeleteFailed(msg);
            this.view.hideProgress();
        }
    }

    @Override
    public void onSubLogFrameDeleted(int position, String msg) {
        if(this.view != null) {
            this.view.onSubLogFrameDeleted(position, msg);
            this.view.hideProgress();
        }
    }

    @Override
    public void onSubLogFrameDeleteFailed(String msg) {
        if(this.view != null) {
            this.view.onSubLogFrameDeleteFailed(msg);
            this.view.hideProgress();
        }
    }
     */
    /* ======================================= END DELETE ======================================= */

    /* ======================================= START SYNC ======================================= */
    /*@Override
    public void syncLogFrameModel(cLogFrameModel logFrameModel) {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }*/
    /* ======================================== END SYNC ======================================== */

    /* ==================================== START PREFERENCE ==================================== */
    /* shared preferences
    @Override
    public void readSharedOrganizations() {
        iReadSharedOrgsInteractor readSharedOrgsInteractor = new cReadSharedOrgsInteractorImpl(
                executor,
                mainThread,
                sessionManagerRepository,
                this);

        view.showProgress();

        readSharedOrgsInteractor.execute();
    }

    @Override
    public void onReadSharedOrgsFailed(String msg) {

    }

    @Override
    public void onSharedOrgsRetrieved(ArrayList<cOrganizationModel> organizationModels) {
        view.onRetrieveSharedOrgsCompleted(organizationModels);
    }*/

    /* ===================================== END PREFERENCE ===================================== */


    /* corresponding view functions */
    @Override
    public void resume() {
        readEvaluationModels(this.logFrameID);
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
