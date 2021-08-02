package com.me.mseotsanyana.mande.BLL.interactors.session.user.Impl;

import com.me.mseotsanyana.mande.BLL.executor.iExecutor;
import com.me.mseotsanyana.mande.BLL.executor.iMainThread;
import com.me.mseotsanyana.mande.BLL.interactors.base.cAbstractInteractor;
import com.me.mseotsanyana.mande.BLL.interactors.session.user.iReadUserProfileInteractor;
import com.me.mseotsanyana.mande.BLL.model.session.cUserProfileModel;
import com.me.mseotsanyana.mande.BLL.repository.session.iSharedPreferenceRepository;
import com.me.mseotsanyana.mande.BLL.repository.session.iUserProfileRepository;

public class cReadUserProfileInteractorImpl extends cAbstractInteractor
        implements iReadUserProfileInteractor {
    private static String TAG = cReadUserProfileInteractorImpl.class.getSimpleName();

    private Callback callback;
    private iSharedPreferenceRepository preferenceRepository;
    private final iUserProfileRepository userProfileRepository;

    public cReadUserProfileInteractorImpl(iExecutor threadExecutor, iMainThread mainThread,
                                          iSharedPreferenceRepository preferenceRepository,
                                          iUserProfileRepository userProfileRepository,
                                          Callback callback) {
        super(threadExecutor, mainThread);

        if (preferenceRepository == null || callback == null) {
            throw new IllegalArgumentException("Arguments can not be null!");
        }

        this.preferenceRepository = preferenceRepository;
        this.userProfileRepository = userProfileRepository;
        this.callback = callback;

    }

    /* */
    private void notifyError(String msg) {
        mainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onReadUserProfileFailed(msg);
            }
        });
    }

    /* */
    private void postMessage(cUserProfileModel userProfileModel) {
        mainThread.post(new Runnable() {
            @Override
            public void run() {
                callback.onReadUserProfileRetrieved(userProfileModel);
            }
        });
    }


    @Override
    public void run() {
        userProfileRepository.readUserProfile(
                new iUserProfileRepository.iReadUserProfileRepositoryCallback() {
                    @Override
                    public void onReadUserProfileSucceeded(cUserProfileModel userProfileModel) {
                        postMessage(userProfileModel);
                    }

                    @Override
                    public void onReadUserProfileFailed(String msg) {
                        notifyError(msg);
                    }
                });
    }
}
