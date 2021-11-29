package com.me.mseotsanyana.mande.BLL.interactors.session.userprofile.Impl;

import com.google.firebase.firestore.ListenerRegistration;
import com.me.mseotsanyana.mande.BLL.executor.iExecutor;
import com.me.mseotsanyana.mande.BLL.executor.iMainThread;
import com.me.mseotsanyana.mande.BLL.interactors.base.cAbstractInteractor;
import com.me.mseotsanyana.mande.BLL.interactors.session.userprofile.iUserProfilesInteractor;
import com.me.mseotsanyana.mande.BLL.model.session.cUserProfileModel;
import com.me.mseotsanyana.mande.BLL.repository.session.iSharedPreferenceRepository;
import com.me.mseotsanyana.mande.BLL.repository.session.iUserProfileRepository;
import com.me.mseotsanyana.mande.DAL.storage.base.cFirebaseChildCallBack;

import java.util.List;

public class cReadAllUserProfilesInteractorImpl extends cAbstractInteractor
        implements iUserProfilesInteractor {
    //private static final String TAG = cReadUserProfilesInteractorImpl.class.getSimpleName();

    private final Callback callback;
    private final iUserProfileRepository userProfileRepository;
    private ListenerRegistration listenerRegistration;

    public cReadAllUserProfilesInteractorImpl(iExecutor threadExecutor, iMainThread mainThread,
                                              iSharedPreferenceRepository sharedPreferenceRepository,
                                              iUserProfileRepository userProfileRepository,
                                              Callback callback) {
        super(threadExecutor, mainThread);

        if (sharedPreferenceRepository == null || userProfileRepository == null || callback == null) {
            throw new IllegalArgumentException("Arguments can not be null!");
        }

        this.userProfileRepository = userProfileRepository;
        this.callback = callback;
    }

    /* */
    private void notifyError(String msg) {
        mainThread.post(() -> callback.onReadUserProfilesFailed(msg));
    }

    /* */
    private void userProfilesMessage(List<cUserProfileModel> userProfileModels) {
        mainThread.post(() -> callback.onReadUserProfilesSucceeded(userProfileModels));
    }

    @Override
    public void run() {
        listenerRegistration = userProfileRepository.readAllUserProfilesByChildEvent(
                new cFirebaseChildCallBack() {
            @Override
            public void onChildAdded(Object object) {
                if(object != null){
                    cUserProfileModel userProfileModel = (cUserProfileModel) object;

                }
            }

            @Override
            public void onChildChanged(Object object) {

            }

            @Override
            public void onChildRemoved(Object object) {

            }

            @Override
            public void onCancelled(Object object) {

            }
        });


//        this.userProfileRepository.readUserProfiles(
//                new iUserProfileRepository.iReadUserProfilesRepositoryCallback() {
//
//                    @Override
//                    public void onReadUserProfilesSucceeded(
//                            List<cUserProfileModel> userProfileModels) {
//                        userProfilesMessage(userProfileModels);
//                    }
//
//                    @Override
//                    public void onReadUserProfilesFailed(String msg) {
//                        notifyError(msg);
//                    }
//                });
    }
}