package com.me.mseotsanyana.mande.PL.presenters.session.Impl;

import com.me.mseotsanyana.mande.BLL.executor.iExecutor;
import com.me.mseotsanyana.mande.BLL.executor.iMainThread;
import com.me.mseotsanyana.mande.BLL.interactors.session.team.Impl.cReadTeamsWithRolesInteractorImpl;
import com.me.mseotsanyana.mande.BLL.interactors.session.team.iReadTeamsWithRolesInteractor;
import com.me.mseotsanyana.mande.BLL.repository.session.iSharedPreferenceRepository;
import com.me.mseotsanyana.mande.BLL.repository.session.iTeamRepository;
import com.me.mseotsanyana.mande.PL.presenters.base.cAbstractPresenter;
import com.me.mseotsanyana.mande.PL.presenters.session.iTeamsWithRolesPresenter;
import com.me.mseotsanyana.treeadapterlibrary.cTreeModel;

import java.util.List;

public class cTeamsWithRolesPresenterImpl extends cAbstractPresenter implements
        iTeamsWithRolesPresenter, iReadTeamsWithRolesInteractor.Callback {
    //private static String TAG = cTeamsWithRolesPresenterImpl.class.getSimpleName();

    private View view;
    private final iSharedPreferenceRepository preferenceRepository;
    private final iTeamRepository teamRepository;

    public cTeamsWithRolesPresenterImpl(iExecutor executor, iMainThread mainThread,
                                        View view,
                                        iSharedPreferenceRepository preferenceRepository,
                                        iTeamRepository teamRepository) {
        super(executor, mainThread);

        this.view = view;
        this.preferenceRepository = preferenceRepository;
        this.teamRepository = teamRepository;
    }

    @Override
    public void readTeamsWithRoles() {
        iReadTeamsWithRolesInteractor readTeamInteractor = new cReadTeamsWithRolesInteractorImpl(
                executor,
                mainThread,
                preferenceRepository,
                teamRepository,
                this);

        view.showProgress();
        readTeamInteractor.execute();
    }

    @Override
    public void onReadTeamsWithRolesFailed(String msg) {
        if (this.view != null) {
            this.view.onReadTeamsWithRolesFailed(msg);
            this.view.hideProgress();
        }
    }

    @Override
    public void onReadTeamsWithRolesSucceeded(List<cTreeModel> teamsRolesTree) {
        if (this.view != null) {
            this.view.onReadTeamsWithRolesSucceeded(teamsRolesTree);
            this.view.hideProgress();
        }
    }

    /* ===================================== END PREFERENCE ===================================== */

    /* corresponding view functions */
    @Override
    public void resume() {
        readTeamsWithRoles();
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
