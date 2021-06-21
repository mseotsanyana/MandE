package com.me.mseotsanyana.mande.BLL.interactors.session.user.Impl;

import com.me.mseotsanyana.mande.BLL.executor.iExecutor;
import com.me.mseotsanyana.mande.BLL.executor.iMainThread;
import com.me.mseotsanyana.mande.BLL.interactors.base.cAbstractInteractor;
import com.me.mseotsanyana.mande.BLL.interactors.session.user.iUserSignOutInteractor;
import com.me.mseotsanyana.mande.BLL.repository.session.iUserProfileRepository;
import com.me.mseotsanyana.mande.BLL.repository.session.iUserRepository;

public class cUserSignOutInteractorImpl extends cAbstractInteractor implements iUserSignOutInteractor {
    private static String TAG = cUserSignOutInteractorImpl.class.getSimpleName();

    private final Callback callback;
    private final iUserProfileRepository userProfileRepository;

    public cUserSignOutInteractorImpl(iExecutor threadExecutor, iMainThread mainThread,
                                      iUserProfileRepository userProfileRepository,
                                      Callback callback) {
        super(threadExecutor, mainThread);

        if (userProfileRepository == null || callback == null) {
            throw new IllegalArgumentException("Arguments can not be null!");
        }

        this.userProfileRepository = userProfileRepository;
        this.callback = callback;
    }

    /* */
    private void notifyError(String msg) {
        mainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onUserSignOutFailed(msg);
            }
        });
    }

    /* */
    private void postMessage(String msg) {
        mainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onUserSignOutSucceeded(msg);
            }
        });
    }

    /* sign up a new user */
    @Override
    public void run() {
        this.userProfileRepository.signOutWithEmailAndPassword(new iUserProfileRepository.iSignOutRepositoryCallback() {
            /* logged user successfully sign out with firebase */
            @Override
            public void onSignOutSucceeded(String msg) {
                postMessage(msg);
            }
            /* logged user failed to register with firebase */
            @Override
            public void onSignOutFailed(String msg) {
                notifyError(msg);
            }
        });
    }
}