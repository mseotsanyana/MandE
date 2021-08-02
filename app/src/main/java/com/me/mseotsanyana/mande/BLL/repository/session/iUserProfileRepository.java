package com.me.mseotsanyana.mande.BLL.repository.session;

import com.me.mseotsanyana.mande.BLL.model.session.cUserProfileModel;

public interface iUserProfileRepository {
    void createUserWithEmailAndPassword(String firstName, String surname, String email, String password,
                                        iSignUpRepositoryCallback callback);
    interface iSignUpRepositoryCallback{
        void onSignUpSucceeded(String msg);
        void onSignUpFailed(String msg);
    }

    void signOutWithEmailAndPassword(iSignOutRepositoryCallback callback);
    interface iSignOutRepositoryCallback{
        void onSignOutSucceeded(String msg);
        void onSignOutFailed(String msg);
    }

    void readUserProfile(iReadUserProfileRepositoryCallback callback);
    interface iReadUserProfileRepositoryCallback{
        void onReadUserProfileSucceeded(cUserProfileModel userProfileModel);
        void onReadUserProfileFailed(String msg);
    }

    void signInWithEmailAndPassword(String email, String password,
                                    iSignInRepositoryCallback callback);
    interface iSignInRepositoryCallback{
        void onSignInSucceeded();
        void onSignInFailed(String msg);
    }

    void updateUserProfile(long userID, int primaryRole, int secondaryRoles, int statusBITS,
                           cUserProfileModel userProfileModel,
                           iUpdateUserProfileRepositoryCallback callback);

    interface iUpdateUserProfileRepositoryCallback{
        void onUpdateUserProfileSucceeded(String msg);
        void onUpdateUserProfileFailed(String msg);
    }
}
