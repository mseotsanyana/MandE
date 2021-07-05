package com.me.mseotsanyana.mande.DAL.ìmpl.firestore.session;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.me.mseotsanyana.mande.BLL.model.session.cUserProfileModel;
import com.me.mseotsanyana.mande.BLL.model.utils.cCommonPropertiesModel;
import com.me.mseotsanyana.mande.BLL.repository.session.iUserProfileRepository;
import com.me.mseotsanyana.mande.DAL.storage.database.cRealtimeHelper;
import com.me.mseotsanyana.mande.DAL.ìmpl.cDatabaseUtils;

import java.util.Date;
import java.util.Objects;

/**
 * Created by mseotsanyana on 2016/10/23.
 */
public class cUserProfileFirestoreRepositoryImpl implements iUserProfileRepository {
    private static String TAG = cUserProfileFirestoreRepositoryImpl.class.getSimpleName();

    // an object of the database helper
    private final FirebaseFirestore db;
    private final FirebaseAuth firebaseAuth;
    private final Context context;

    public cUserProfileFirestoreRepositoryImpl(Context context) {
        this.context = context;
        this.firebaseAuth = FirebaseAuth.getInstance();
        this.db = FirebaseFirestore.getInstance();
    }

    /* ############################################# CREATE ACTIONS ############################################# */

    @Override
    public void createUserWithEmailAndPassword(String firstname, String surname, String userEmail,
                                               String userPassword, iSignUpRepositoryCallback callback) {
        firebaseAuth.createUserWithEmailAndPassword(userEmail, userPassword)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                        assert user != null;
                        if (!user.isEmailVerified()) {
                            /* send an email verification */
                            sendEmailVerification(user);

                            /* update the profile in the firebase */
                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(firstname + " " + surname).build();
                            user.updateProfile(profileUpdates).addOnCompleteListener(updateProfileTask -> {
                                if (updateProfileTask.isSuccessful()) {
                                    Log.d(TAG, "User profile updated.");
                                }
                            });



                            /* update the user profile in the database */
                            cUserProfileModel userProfileModel = new cUserProfileModel(
                                    user.getUid(), firstname, surname, userEmail);

                            // update default date
                            Date currentDate = new Date();
                            userProfileModel.setCreatedDate(currentDate);
                            userProfileModel.setModifiedDate(currentDate);

                            CollectionReference coUsersRef = db.collection(cRealtimeHelper.KEY_USERPROFILES);
                            coUsersRef.document(user.getUid())
                                    .set(userProfileModel)
                                    .addOnFailureListener(e -> Log.d(TAG, Objects.requireNonNull(e.getLocalizedMessage())));

                            /* create default menu for the user
                            DatabaseReference dbMenuItemsRef = database.getReference(cRealtimeHelper.KEY_MENU_ITEMS);
                            dbMenuItemsRef.setValue(cDatabaseUtils.getDefaultMenuModelSet(context))
                                    .addOnFailureListener(e -> Log.d(TAG, Objects.requireNonNull(e.getLocalizedMessage())));*/

                            Log.d(TAG, "createUserWithEmailAndPassword:success");
                            callback.onSignUpSucceeded("Account successfully created.");
                        }
                    } else {
                        Log.d(TAG, "createUserWithEmailAndPassword:failure", task.getException());
                        callback.onSignUpFailed("Failed to create a new account. " +
                                "Try a different email.");
                    }
                });
    }

    public void sendEmailVerification(FirebaseUser user) {
        //FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            user.sendEmailVerification().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(context, "Verification email sent to " +
                            user.getEmail(), Toast.LENGTH_LONG).show();
                } else {
                    Log.e(TAG, "sendEmailVerification", task.getException());
                    Toast.makeText(context, "Failed to send verification email.",
                            Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    /* ############################################# READ ACTIONS ############################################# */

    @Override
    public void signInWithEmailAndPassword(String email, String password, iSignInRepositoryCallback callback) {
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    if (!user.isEmailVerified()) {
                        callback.onSignInFailed("Verification email sent to " + user.getEmail());
                        FirebaseAuth.getInstance().signOut();
                    } else {
                        Log.d(TAG, "signInWithEmail:success");
                        callback.onSignInSucceeded("Authentication succeeded");
                    }
                } else {
                    Log.d(TAG, "signInWithEmail:failure ", task.getException());
                    callback.onSignInFailed("Authentication failed!");
                }
            } else {
                Log.d(TAG, "signInWithEmail:failure ", task.getException());
                callback.onSignInFailed("Authentication failed!");
            }
        });
    }

    @Override
    public void signOutWithEmailAndPassword(iSignOutRepositoryCallback callback) {
        //PreferenceUtil.signOut(this);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            firebaseAuth.signOut();
            callback.onSignOutSucceeded("Logged Out");
        } else {
            callback.onSignOutFailed("Already Logged Out !!!");
        }
    }

    /* ############################################# READ ACTIONS ############################################# */

    @Override
    public void readUserProfile(iReadUserProfileRepositoryCallback callback) {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {
            CollectionReference coUserProfileRef = db.collection(cRealtimeHelper.KEY_USERPROFILES);
            coUserProfileRef.document(user.getUid()).get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot doc = task.getResult();
                                if (doc != null) {
                                    cUserProfileModel userProfile = doc.toObject(cUserProfileModel.class);
                                    callback.onReadUserProfileSucceeded(userProfile);
                                }
                            } else {
                                callback.onReadUserProfileFailed("Undefined error! Please report to the developer.");
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            callback.onReadUserProfileFailed("Failure to read user profile!");
                        }
                    });
        } else {
            callback.onReadUserProfileFailed("Failure to read user profile!");
        }
    }

    /* ############################################# UPDATE ACTIONS ############################################# */

    @Override
    public void updateUserProfile(long userID, int primaryRole, int secondaryRoles, int statusBITS,
                                  cUserProfileModel userProfileModel,
                                  iUpdateUserProfileRepositoryCallback callback) {

        FirebaseUser user = firebaseAuth.getCurrentUser();

        if (user != null) {
            userProfileModel.setEmail(user.getEmail());
            CollectionReference coUserProfileRef = db.collection(cRealtimeHelper.KEY_USERPROFILES);
            coUserProfileRef.document(user.getUid()).set(userProfileModel)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                callback.onUpdateUserProfileSucceeded("Successfully Updated");
                            } else {
                                callback.onUpdateUserProfileFailed("Undefined error! Talk to the Admin");
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            callback.onUpdateUserProfileFailed("Failed to update user profile " + e);
                        }
                    });
        } else {
            callback.onUpdateUserProfileFailed("Failed to update user profile!");
        }
    }

    /* ############################################# DELETE ACTIONS ############################################# */

}
