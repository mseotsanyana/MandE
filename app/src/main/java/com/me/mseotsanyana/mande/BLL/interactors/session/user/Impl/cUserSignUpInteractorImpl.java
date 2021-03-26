package com.me.mseotsanyana.mande.BLL.interactors.session.user.Impl;

import android.util.Log;

import com.me.mseotsanyana.mande.BLL.executor.iExecutor;
import com.me.mseotsanyana.mande.BLL.executor.iMainThread;
import com.me.mseotsanyana.mande.BLL.interactors.base.cAbstractInteractor;
import com.me.mseotsanyana.mande.BLL.interactors.session.user.iUserSignUpInteractor;
import com.me.mseotsanyana.mande.BLL.model.session.cUserModel;
import com.me.mseotsanyana.mande.BLL.repository.session.iUserRepository;

public class cUserSignUpInteractorImpl extends cAbstractInteractor implements iUserSignUpInteractor{
    private static String TAG = cUserSignUpInteractorImpl.class.getSimpleName();

    private final Callback callback;
    private final iUserRepository userRepository;

    private final String email;
    private final String password;
    private final String firstName;
    private final String surname;

    public cUserSignUpInteractorImpl(iExecutor threadExecutor, iMainThread mainThread,
                                     iUserRepository userRepository,
                                     Callback callback,
                                     String firstName, String surname,
                                     String email, String password) {
        super(threadExecutor, mainThread);

        if (userRepository == null || callback == null) {
            throw new IllegalArgumentException("Arguments can not be null!");
        }

        this.userRepository = userRepository;
        this.callback = callback;
        this.firstName = firstName;
        this.surname = surname;
        this.email = email;
        this.password = password;
    }

    /* */
    private void notifyError(String msg) {
        mainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onUserSignUpFailed(msg);
            }
        });
    }

    /* */
    private void postMessage(String msg) {
        mainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onUserSignUpSucceeded(msg);
            }
        });
    }

    /* sign up a new user */
    @Override
    public void run() {
        this.userRepository.createUserWithEmailAndPassword(
                email, password, firstName, surname, new iUserRepository.iSignUpRepositoryCallback() {

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