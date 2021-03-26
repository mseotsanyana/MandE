package com.me.mseotsanyana.mande.BLL.repository.session;

import com.me.mseotsanyana.mande.BLL.model.session.cUserModel;

import java.util.Set;

public interface iUserRepository {
    void createUserWithEmailAndPassword(String email, String password, String firstName, String surname,
                                        iSignUpRepositoryCallback callback);

    interface iSignUpRepositoryCallback{
        void onSignUpSucceeded(String msg);
        void onSignUpFailed(String msg);
    }

    void signInWithEmailAndPassword(String email, String password,
                                    iSignInRepositoryCallback callback);

    interface iSignInRepositoryCallback{
        void onSignInSucceeded(cUserModel userModel);
        void onSignInFailed(String msg);
    }

    void signOutWithEmailAndPassword(iSignOutRepositoryCallback callback);

    interface iSignOutRepositoryCallback{
        void onSignOutSucceeded(String msg);
        void onSignOutFailed(String msg);
    }


    cUserModel getUserByEmailPassword(String email, String password);

    Set<cUserModel> getOwnerSet();
}
