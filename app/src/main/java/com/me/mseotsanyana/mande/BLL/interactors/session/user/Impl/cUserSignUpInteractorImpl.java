package com.me.mseotsanyana.mande.BLL.interactors.session.user.Impl;

import com.me.mseotsanyana.mande.BLL.executor.iExecutor;
import com.me.mseotsanyana.mande.BLL.executor.iMainThread;
import com.me.mseotsanyana.mande.BLL.interactors.base.cAbstractInteractor;
import com.me.mseotsanyana.mande.BLL.interactors.session.user.iUserSignUpInteractor;
import com.me.mseotsanyana.mande.BLL.model.session.cUserProfileModel;
import com.me.mseotsanyana.mande.BLL.repository.session.iUserProfileRepository;

public class cUserSignUpInteractorImpl extends cAbstractInteractor implements iUserSignUpInteractor{
    //private static String TAG = cUserSignUpInteractorImpl.class.getSimpleName();

    private final Callback callback;
    private final iUserProfileRepository userProfileRepository;

    private final cUserProfileModel userProfileModel;

    public cUserSignUpInteractorImpl(iExecutor threadExecutor, iMainThread mainThread,
                                     iUserProfileRepository userProfileRepository,
                                     Callback callback,
                                     cUserProfileModel userProfileModel) {
        super(threadExecutor, mainThread);

        if (userProfileRepository == null || callback == null) {
            throw new IllegalArgumentException("Arguments can not be null!");
        }

        this.userProfileRepository = userProfileRepository;
        this.callback = callback;

        this.userProfileModel = userProfileModel;
    }

    /* */
    private void notifyError(String msg) {
        mainThread.post(() -> callback.onUserSignUpFailed(msg));
    }

    /* */
    private void postMessage(String msg) {
        mainThread.post(() -> callback.onUserSignUpSucceeded(msg));
    }

    /* sign up a new user */
    @Override
    public void run() {
        this.userProfileRepository.createUserWithEmailAndPassword(userProfileModel,
                new iUserProfileRepository.iSignUpRepositoryCallback() {

            /* new user successfully registered with firebase */
            @Override
            public void onSignUpSucceeded(String msg) {
                postMessage(msg);
            }

            /* new user failed to register with firebase */
            @Override
            public void onSignUpFailed(String msg) {
                notifyError(msg);
            }
        });
    }
}