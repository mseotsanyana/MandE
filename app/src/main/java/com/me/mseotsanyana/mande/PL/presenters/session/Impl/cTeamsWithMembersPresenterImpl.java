package com.me.mseotsanyana.mande.PL.presenters.session.Impl;

import com.me.mseotsanyana.mande.BLL.executor.iExecutor;
import com.me.mseotsanyana.mande.BLL.executor.iMainThread;
import com.me.mseotsanyana.mande.BLL.interactors.session.team.Impl.cReadTeamsWithMembersInteractorImpl;
import com.me.mseotsanyana.mande.BLL.interactors.session.team.iReadTeamsWithMembersInteractor;
import com.me.mseotsanyana.mande.BLL.model.session.cTeamModel;
import com.me.mseotsanyana.mande.BLL.repository.session.iSharedPreferenceRepository;
import com.me.mseotsanyana.mande.BLL.repository.session.iTeamRepository;
import com.me.mseotsanyana.mande.PL.presenters.base.cAbstractPresenter;
import com.me.mseotsanyana.mande.PL.presenters.session.iTeamsWithMembersPresenter;
import com.me.mseotsanyana.treeadapterlibrary.cTreeModel;

import java.util.List;

public class cTeamsWithMembersPresenterImpl extends cAbstractPresenter implements
        iTeamsWithMembersPresenter, iReadTeamsWithMembersInteractor.Callback {
    //private static String TAG = cTeamPresenterImpl.class.getSimpleName();

    private View view;
    private final iSharedPreferenceRepository preferenceRepository;
    private final iTeamRepository teamRepository;

    public cTeamsWithMembersPresenterImpl(iExecutor executor, iMainThread mainThread,
                                          View view,
                                          iSharedPreferenceRepository preferenceRepository,
                                          iTeamRepository teamRepository) {
        super(executor, mainThread);

        this.view = view;
        this.preferenceRepository = preferenceRepository;
        this.teamRepository = teamRepository;
    }

    @Override
    public void createTeam(cTeamModel teamModel) {

    }

    @Override
    public void readTeamsWithMembers() {
        iReadTeamsWithMembersInteractor readTeamInteractor = new cReadTeamsWithMembersInteractorImpl(
                executor,
                mainThread,
                preferenceRepository,
                teamRepository,
                this);

        view.showProgress();
        readTeamInteractor.execute();
    }

    @Override
    public void onReadTeamsWithMembersFailed(String msg) {
        if (this.view != null) {
            this.view.onReadTeamsWithMembersFailed(msg);
            this.view.hideProgress();
        }
    }

    @Override
    public void onReadTeamsWithMembersSucceeded(List<cTreeModel> teamsMembersTree) {
        if (this.view != null) {
            this.view.onReadTeamsWithMembersSucceeded(teamsMembersTree);
            this.view.hideProgress();
        }
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
