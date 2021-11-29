package com.me.mseotsanyana.mande.BLL.interactors.session.user.Impl;

import com.me.mseotsanyana.mande.BLL.executor.iExecutor;
import com.me.mseotsanyana.mande.BLL.executor.iMainThread;
import com.me.mseotsanyana.mande.BLL.interactors.base.cAbstractInteractor;
import com.me.mseotsanyana.mande.BLL.interactors.session.user.iUpdateUserProfileInteractor;
import com.me.mseotsanyana.mande.BLL.model.session.cUserProfileModel;
import com.me.mseotsanyana.mande.BLL.repository.session.iSharedPreferenceRepository;
import com.me.mseotsanyana.mande.BLL.repository.session.iUserProfileRepository;

public class cUpdateUserProfileInteractorImpl extends cAbstractInteractor
        implements iUpdateUserProfileInteractor {
    private static String TAG = cUpdateUserProfileInteractorImpl.class.getSimpleName();

    private Callback callback;
    private iSharedPreferenceRepository sessionManagerRepository;
    private iUserProfileRepository userProfileRepository;
    private cUserProfileModel userProfileModel;

    public cUpdateUserProfileInteractorImpl(iExecutor threadExecutor, iMainThread mainThread,
                                            iSharedPreferenceRepository sessionManagerRepository,
                                            iUserProfileRepository userProfileRepository,
                                            Callback callback, cUserProfileModel userProfileModel) {
        super(threadExecutor, mainThread);

        if (sessionManagerRepository == null || callback == null) {
            throw new IllegalArgumentException("Arguments can not be null!");
        }

        this.sessionManagerRepository = sessionManagerRepository;
        this.userProfileRepository = userProfileRepository;
        this.callback = callback;
        this.userProfileModel = userProfileModel;
    }

    /* */
    private void notifyError(String msg) {
        mainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onUpdateUserProfileFailed(msg);
            }
        });
    }

    /* */
    private void postMessage(String msg) {
        mainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onUpdateUserProfileRetrieved(msg);
            }
        });
    }


    @Override
    public void run() {
        userProfileRepository.updateUserProfileImage(0, 0, 0, 0,
                userProfileModel, new iUserProfileRepository.iUpdateUserProfileRepositoryCallback() {
                    @Override
                    public void onUpdateUserProfileSucceeded(String msg) {
                        postMessage(msg);
                    }

                    @Override
                    public void onUpdateUserProfileFailed(String msg) {
                        notifyError(msg);
                    }
                });
    }
}
