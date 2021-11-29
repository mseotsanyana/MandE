package com.me.mseotsanyana.mande.BLL.interactors.session.userprofile.Impl;

import com.me.mseotsanyana.mande.BLL.executor.iExecutor;
import com.me.mseotsanyana.mande.BLL.executor.iMainThread;
import com.me.mseotsanyana.mande.BLL.interactors.base.cAbstractInteractor;
import com.me.mseotsanyana.mande.BLL.interactors.session.userprofile.iUserProfilesInteractor;
import com.me.mseotsanyana.mande.BLL.repository.session.iSharedPreferenceRepository;
import com.me.mseotsanyana.mande.BLL.repository.session.iUserProfileRepository;

public class cUploadUserProfilesInteractorImpl extends cAbstractInteractor
        implements iUserProfilesInteractor {

    //private static final String TAG = cUploadUserProfilesInteractorImpl.class.getSimpleName();

    private final Callback callback;
    private final iUserProfileRepository userProfileRepository;

    private final String filename;
    public cUploadUserProfilesInteractorImpl(iExecutor threadExecutor, iMainThread mainThread,
                                             iSharedPreferenceRepository sharedPreferenceRepository,
                                             iUserProfileRepository userProfileRepository,
                                             Callback callback, String filename) {
        super(threadExecutor, mainThread);

        if (sharedPreferenceRepository == null || userProfileRepository == null || callback == null) {
            throw new IllegalArgumentException("Arguments can not be null!");
        }

        this.callback = callback;
        this.userProfileRepository = userProfileRepository;
        this.filename = filename;
    }

    /* notify on the main thread */
    private void notifyError(String msg) {
        mainThread.post(() -> callback.onUploadUserProfilesFailed(msg));
    }

    /* notify on the main thread */
    private void postMessage(String msg) {
        mainThread.post(() -> callback.onUploadUserProfilesSucceeded(msg));
    }

    @Override
    public void run() {
        this.userProfileRepository.uploadUserProfilesFromExcel(filename,
                new iUserProfileRepository.iUploadUserProfilesRepositoryCallback() {
                    @Override
                    public void onUploadUserProfilesSucceeded(String msg) {
                        postMessage(msg);
                    }

                    @Override
                    public void onUploadUserProfilesFailed(String msg) {
                        notifyError(msg);
                    }
                });
    }
}