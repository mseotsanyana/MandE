package com.me.mseotsanyana.mande.PL.presenters.session.Impl;

import com.me.mseotsanyana.mande.BLL.executor.iExecutor;
import com.me.mseotsanyana.mande.BLL.executor.iMainThread;
import com.me.mseotsanyana.mande.BLL.interactors.session.role.Impl.cReadTeamRolesInteractorImpl;
import com.me.mseotsanyana.mande.BLL.interactors.session.role.iReadTeamRolesInteractor;
import com.me.mseotsanyana.mande.BLL.model.session.cRoleModel;
import com.me.mseotsanyana.mande.BLL.repository.session.iRoleRepository;
import com.me.mseotsanyana.mande.BLL.repository.session.iSharedPreferenceRepository;
import com.me.mseotsanyana.mande.PL.presenters.base.cAbstractPresenter;
import com.me.mseotsanyana.mande.PL.presenters.session.iRolePresenter;

import java.util.List;

public class cRolePresenterImpl extends cAbstractPresenter implements iRolePresenter,
        iReadTeamRolesInteractor.Callback{
    //private static String TAG = cTeamPresenterImpl.class.getSimpleName();

    private View view;
    private final iSharedPreferenceRepository sharedPreferenceRepository;
    private final iRoleRepository roleRepository;

    public cRolePresenterImpl(iExecutor executor, iMainThread mainThread,
                              View view,
                              iSharedPreferenceRepository sharedPreferenceRepository,
                              iRoleRepository roleRepository) {
        super(executor, mainThread);

        this.view = view;
        this.sharedPreferenceRepository = sharedPreferenceRepository;
        this.roleRepository = roleRepository;
    }

    // READ ROLES

    @Override
    public void readRoles() {
        iReadTeamRolesInteractor readTeamInteractor = new cReadTeamRolesInteractorImpl(
                executor,
                mainThread,
                sharedPreferenceRepository,
                roleRepository,
                this);

        view.showProgress();
        readTeamInteractor.execute();
    }

    @Override
    public void onReadTeamRolesSucceeded(List<cRoleModel> roleModels) {
        if(this.view != null) {
            this.view.onReadRolesSucceeded(roleModels);
            this.view.hideProgress();
        }
    }

    @Override
    public void onReadTeamRolesFailed(String msg) {
        if(this.view != null) {
            this.view.onReadRolesFailed(msg);
            this.view.hideProgress();
        }
    }

    /* ===================================== END PREFERENCE ===================================== */

    /* corresponding view functions */
    @Override
    public void resume() {
        readRoles();
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
