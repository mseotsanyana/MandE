package com.me.mseotsanyana.mande.PL.presenters.session.Impl;

import com.me.mseotsanyana.mande.BLL.executor.iExecutor;
import com.me.mseotsanyana.mande.BLL.executor.iMainThread;
import com.me.mseotsanyana.mande.BLL.interactors.session.team.Impl.cReadTeamsWithMembersInteractorImpl;
import com.me.mseotsanyana.mande.BLL.interactors.session.team.iReadTeamInteractor;
import com.me.mseotsanyana.mande.BLL.model.session.cTeamModel;
import com.me.mseotsanyana.mande.BLL.repository.session.iSharedPreferenceRepository;
import com.me.mseotsanyana.mande.BLL.repository.session.iTeamRepository;
import com.me.mseotsanyana.mande.PL.presenters.base.cAbstractPresenter;
import com.me.mseotsanyana.mande.PL.presenters.session.iTeamPresenter;
import com.me.mseotsanyana.treeadapterlibrary.cTreeModel;

import java.util.List;

public class cTeamPresenterImpl extends cAbstractPresenter implements iTeamPresenter,
        iReadTeamInteractor.Callback{
    //private static String TAG = cTeamPresenterImpl.class.getSimpleName();

    private View view;
    private final iSharedPreferenceRepository sessionManagerRepository;
    private final iTeamRepository teamRepository;

    public cTeamPresenterImpl(iExecutor executor, iMainThread mainThread,
                              View view,
                              iSharedPreferenceRepository sessionManagerRepository,
                              iTeamRepository teamRepository) {
        super(executor, mainThread);

        this.view = view;
        this.sessionManagerRepository = sessionManagerRepository;
        this.teamRepository = teamRepository;
    }

    @Override
    public void onReadTeamsFailed(String msg) {
        if(this.view != null) {
            this.view.onReadTeamsFailed(msg);
            this.view.hideProgress();
        }
    }

    @Override
    public void onReadTeamsWithMembersSucceeded(List<cTreeModel> teamsMembersTree) {
        if(this.view != null) {
            this.view.onReadTeamsWithMembersSucceeded(teamsMembersTree);
            this.view.hideProgress();
        }
    }

    @Override
    public void createTeam(cTeamModel teamModel) {

    }

    @Override
    public void readTeamsWithMembers() {
        iReadTeamInteractor readTeamInteractor = new cReadTeamsWithMembersInteractorImpl(
                executor,
                mainThread,
                sessionManagerRepository,
                teamRepository,
                this);

        view.showProgress();
        readTeamInteractor.execute();
    }


    /* ===================================== END PREFERENCE ===================================== */

    /* corresponding view functions */
    @Override
    public void resume() {
        readTeamsWithMembers();
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
