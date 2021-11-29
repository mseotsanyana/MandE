package com.me.mseotsanyana.mande.BLL.interactors.session.userprofile.Impl;

import com.me.mseotsanyana.mande.BLL.executor.iExecutor;
import com.me.mseotsanyana.mande.BLL.executor.iMainThread;
import com.me.mseotsanyana.mande.BLL.interactors.base.cAbstractInteractor;
import com.me.mseotsanyana.mande.BLL.interactors.session.userprofile.iUserProfilesInteractor;
import com.me.mseotsanyana.mande.BLL.model.session.cUserProfileModel;
import com.me.mseotsanyana.mande.BLL.repository.session.iSharedPreferenceRepository;
import com.me.mseotsanyana.mande.BLL.repository.session.iUserProfileRepository;

public class cUpdateUserProfileImageInteractorImpl extends cAbstractInteractor
        implements iUserProfilesInteractor {
    //private static final String TAG = cReadUserProfilesInteractorImpl.class.getSimpleName();

    private final Callback callback;
    private final iUserProfileRepository userProfileRepository;

    private final String userServerID;
    private final byte[] userProfileImageData;

    public cUpdateUserProfileImageInteractorImpl(iExecutor threadExecutor, iMainThread mainThread,
                                                 iSharedPreferenceRepository sharedPreferenceRepository,
                                                 iUserProfileRepository userProfileRepository,
                                                 Callback callback, String userServerID,
                                                 byte[] userProfileImageData) {
        super(threadExecutor, mainThread);

        if (sharedPreferenceRepository == null || userProfileRepository == null || callback == null) {
            throw new IllegalArgumentException("Arguments can not be null!");
        }

        this.userProfileRepository = userProfileRepository;
        this.callback = callback;

        this.userServerID = userServerID;
        this.userProfileImageData = userProfileImageData;
    }

    /* */
    private void notifyError(String msg) {
        mainThread.post(() -> callback.onUpdateUserProfileImageFailed(msg));
    }

    /* */
    private void userProfilesMessage(String msg) {
        mainThread.post(() -> callback.onUpdateUserProfileImageSucceeded(msg));
    }

    @Override
    public void run() {
        this.userProfileRepository.updateUserProfileImage(userServerID, userProfileImageData,
                new iUserProfileRepository.iUpdateUserProfileImageRepositoryCallback() {
                    @Override
                    public void onUpdateUserProfileImageSucceeded(Object msg) {
                        userProfilesMessage(msg.toString());
                    }

                    @Override
                    public void onUpdateUserProfileImageFailed(Object msg) {
                        notifyError(msg.toString());
                    }
                });
    }
}