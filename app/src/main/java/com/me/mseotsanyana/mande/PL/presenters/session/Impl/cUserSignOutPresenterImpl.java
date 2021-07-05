package com.me.mseotsanyana.mande.PL.presenters.session.Impl;

import com.me.mseotsanyana.mande.BLL.executor.iExecutor;
import com.me.mseotsanyana.mande.BLL.executor.iMainThread;
import com.me.mseotsanyana.mande.BLL.interactors.session.user.Impl.cUserSignOutInteractorImpl;
import com.me.mseotsanyana.mande.BLL.interactors.session.user.iUserSignOutInteractor;
import com.me.mseotsanyana.mande.BLL.repository.session.iSharedPreferenceRepository;
import com.me.mseotsanyana.mande.BLL.repository.session.iUserProfileRepository;
import com.me.mseotsanyana.mande.PL.presenters.base.cAbstractPresenter;
import com.me.mseotsanyana.mande.PL.presenters.session.iUserSignOutPresenter;

public class cUserSignOutPresenterImpl extends cAbstractPresenter implements iUserSignOutPresenter,
        iUserSignOutInteractor.Callback {
    //private static String TAG = cUserSignOutPresenterImpl.class.getSimpleName();

    private View view;
    private final iSharedPreferenceRepository sharedPreferenceRepository;
    private final iUserProfileRepository userProfileRepository;

    public cUserSignOutPresenterImpl(iExecutor executor, iMainThread mainThread,
                                     View view,
                                     iSharedPreferenceRepository sharedPreferenceRepository,
                                     iUserProfileRepository userProfileRepository) {
        super(executor, mainThread);

        this.view = view;
        this.sharedPreferenceRepository = sharedPreferenceRepository;
        this.userProfileRepository = userProfileRepository;
    }

    @Override
    public void signOutWithEmailAndPassword() {
        iUserSignOutInteractor userSignOutInteractor = new cUserSignOutInteractorImpl(
                executor,
                mainThread,this,
                sharedPreferenceRepository,
                userProfileRepository);

        view.showProgress();

        userSignOutInteractor.execute();
    }

    @Override
    public void onUserSignOutFailed(String msg) {
        if(this.view != null) {
            this.view.onUserSignOutFailed(msg);
            this.view.hideProgress();
        }
    }

    @Override
    public void onUserSignOutSucceeded(String msg) {
        if(this.view != null) {
            this.view.onUserSignOutSucceeded(msg);
            this.view.hideProgress();
        }
    }

    /*============================= General Presenter methods ============================= */

    @Override
    public void resume() {

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
