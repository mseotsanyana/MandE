package com.me.mseotsanyana.mande.PL.presenters.logframe.Impl;

import android.util.Log;

import com.google.gson.Gson;
import com.me.mseotsanyana.mande.BLL.executor.iExecutor;
import com.me.mseotsanyana.mande.BLL.executor.iMainThread;
import com.me.mseotsanyana.mande.BLL.interactors.logframe.input.Impl.cReadInputInteractorImpl;
import com.me.mseotsanyana.mande.BLL.interactors.logframe.input.iReadInputInteractor;
import com.me.mseotsanyana.mande.BLL.interactors.logframe.outcome.Impl.cReadOutcomeInteractorImpl;
import com.me.mseotsanyana.mande.BLL.interactors.logframe.outcome.iReadOutcomeInteractor;
import com.me.mseotsanyana.mande.BLL.repository.logframe.iInputRepository;
import com.me.mseotsanyana.mande.BLL.repository.logframe.iOutcomeRepository;
import com.me.mseotsanyana.mande.BLL.repository.session.iSessionManagerRepository;
import com.me.mseotsanyana.mande.BLL.repository.wpb.iExpenseRepository;
import com.me.mseotsanyana.mande.BLL.repository.wpb.iHumanRepository;
import com.me.mseotsanyana.mande.BLL.repository.wpb.iIncomeRepository;
import com.me.mseotsanyana.mande.BLL.repository.wpb.iMaterialRepository;
import com.me.mseotsanyana.mande.PL.presenters.base.cAbstractPresenter;
import com.me.mseotsanyana.mande.PL.presenters.logframe.iInputPresenter;
import com.me.mseotsanyana.mande.PL.presenters.logframe.iOutcomePresenter;
import com.me.mseotsanyana.treeadapterlibrary.cTreeModel;

import java.util.ArrayList;
import java.util.Map;

public class cInputPresenterImpl extends cAbstractPresenter implements iInputPresenter,
        iReadInputInteractor.Callback/*, iReadSharedOrgsInteractor.Callback,
        iCreateLogFrameInteractor.Callback, iCreateSubLogFrameInteractor.Callback,
        iUpdateLogFrameInteractor.Callback, iDeleteLogFrameInteractor.Callback,
        iDeleteSubLogFrameInteractor.Callback*/{
    private static String TAG = cInputPresenterImpl.class.getSimpleName();

    private View view;
    private iSessionManagerRepository sessionManagerRepository;
    private iHumanRepository humanRepository;
    private iMaterialRepository materialRepository;
    private iIncomeRepository incomeRepository;
    private iExpenseRepository expenseRepository;
    private long logFrameID;

    public cInputPresenterImpl(iExecutor executor, iMainThread mainThread,
                               View view,
                               iSessionManagerRepository sessionManagerRepository,
                               iHumanRepository humanRepository,
                               iMaterialRepository materialRepository,
                               iIncomeRepository incomeRepository,
                               iExpenseRepository expenseRepository,
                               long logFrameID) {
        super(executor, mainThread);

        this.view = view;
        this.sessionManagerRepository = sessionManagerRepository;
        this.humanRepository = humanRepository;
        this.materialRepository = materialRepository;
        this.incomeRepository = incomeRepository;
        this.expenseRepository = expenseRepository;
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
    public void readInputModels(long logFrameID) {
        iReadInputInteractor readInputInteractor = new cReadInputInteractorImpl(
                executor,
                mainThread,
                sessionManagerRepository,
                humanRepository,
                materialRepository,
                incomeRepository,
                expenseRepository,
                this,
                logFrameID);

        view.showProgress();
        readInputInteractor.execute();
    }

    @Override
    public void onInputModelsRetrieved(Map<Integer, ArrayList<cTreeModel>> inputTreeModels) {
        Gson gson = new Gson();
        if(this.view != null) {
            this.view.onInputModelsRetrieved(inputTreeModels);
            this.view.hideProgress();
        }
    }

    @Override
    public void onInputModelsFailed(String msg) {
        if(this.view != null) {
            this.view.onInputModelsFailed(msg);
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
        readInputModels(this.logFrameID);
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