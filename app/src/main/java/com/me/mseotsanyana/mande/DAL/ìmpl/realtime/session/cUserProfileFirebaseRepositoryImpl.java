package com.me.mseotsanyana.mande.DAL.ìmpl.realtime.session;

import androidx.annotation.NonNull;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.ListenerRegistration;
import com.me.mseotsanyana.mande.BLL.model.session.cUserProfileModel;
import com.me.mseotsanyana.mande.BLL.repository.session.iUserProfileRepository;
import com.me.mseotsanyana.mande.DAL.storage.base.cFirebaseChildCallBack;
import com.me.mseotsanyana.mande.DAL.storage.database.cRealtimeHelper;
import com.me.mseotsanyana.mande.DAL.storage.database.cSQLDBHelper;
import com.me.mseotsanyana.mande.UTIL.cConstant;

import java.text.SimpleDateFormat;
import java.util.Objects;

/**
 * Created by mseotsanyana on 2016/10/23.
 */
public class cUserProfileFirebaseRepositoryImpl implements iUserProfileRepository {
    private static SimpleDateFormat sdf = cConstant.FORMAT_DATE;
    private static String TAG = cUserProfileFirebaseRepositoryImpl.class.getSimpleName();
    private cSQLDBHelper dbHelper;

    // an object of the database helper
    private final FirebaseDatabase database;
    private DatabaseReference dbReference;
    private final FirebaseAuth firebaseAuth;
    private final Context context;

    public cUserProfileFirebaseRepositoryImpl(Context context) {
        this.context = context;
        dbHelper = new cSQLDBHelper(context);
        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
    }

    /* ############################################# CREATE ACTIONS ############################################# */

    @Override
    public void createUserWithEmailAndPassword(cUserProfileModel userProfileModel, iSignUpRepositoryCallback callback) {

    }

    public void createUserWithEmailAndPassword1(String photo, String firstname, String surname, String userEmail,
                                               String designation, String userPassword, iSignUpRepositoryCallback callback) {
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
                            cUserProfileModel userProfileModel = new cUserProfileModel(null, firstname,
                                    surname, designation, userEmail,null);
                            DatabaseReference dbUsersRef = database.getReference(cRealtimeHelper.KEY_USERPROFILES);
                            dbUsersRef.child(user.getUid())
                                    .setValue(userProfileModel)
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
                    Toast.makeText(context, "Verification email sent to " + user.getEmail(), Toast.LENGTH_LONG).show();
                } else {
                    Log.e(TAG, "sendEmailVerification", task.getException());
                    Toast.makeText(context, "Failed to send verification email.", Toast.LENGTH_LONG).show();
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
                                callback.onSignInMessage("Verification email sent to " + user.getEmail());
                                FirebaseAuth.getInstance().signOut();
                            } else {
                                Log.d(TAG, "signInWithEmail:success");
                                callback.onSignInSucceeded();


                            /*DatabaseReference dbReference = database.getReference(cRealtimeHelper.KEY_USERS);
                            dbReference.child(user.getUid()).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    cUserProfileModel userProfileModel = dataSnapshot.getValue(cUserProfileModel.class);
                                    if (userProfileModel != null) {
                                        callback.onSignInSucceeded(userProfileModel);
                                    } else {
                                        callback.onSignInFailed("User Profile not found!!");
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                    Log.w(TAG, "Failed to read value.", error.toException());
                                }
                            });*/

                                //cUserModel userModel = getUserByID(user.getUid());
                                //callback.onSignInSucceeded(userModel);
                            }
                        }else{
                            callback.onSignInMessage("Authentication failed. " +
                                    Objects.requireNonNull(task.getException()).getMessage());
                        }
                    } else {
                        Log.d(TAG, "signInWithEmail:failure ", task.getException());
                        callback.onSignInMessage("Authentication failed. " +
                                Objects.requireNonNull(task.getException()).getMessage());
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
    public void readMyUserProfile(iReadMyUserProfileRepositoryCallback callback) {

        FirebaseUser user = firebaseAuth.getCurrentUser();
        if(user != null) {
            dbReference = database.getReference(cRealtimeHelper.KEY_USERPROFILES);
            dbReference.child(user.getUid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    cUserProfileModel userProfile = dataSnapshot.getValue(cUserProfileModel.class);
                    //Gson gson = new Gson();
                    //Log.d(TAG, " ======= "+gson.toJson(userProfile));

                    callback.onReadMyUserProfileSucceeded(userProfile);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    callback.onReadMyUserProfileFailed(databaseError.toString());
                }
            });
        }
    }

    @Override
    public void readUserProfiles(iReadUserProfilesRepositoryCallback callback) {

    }

    /* ##################################### UPDATE ACTIONS ##################################### */

    @Override
    public void updateUserProfileImage(long userID, int primaryRole, int secondaryRoles, int statusBITS,
                                       cUserProfileModel userProfileModel,
                                       iUpdateUserProfileRepositoryCallback callback) {

        FirebaseUser user = firebaseAuth.getCurrentUser();
        assert user != null;
        userProfileModel.setEmail(user.getEmail());
        dbReference = database.getReference(cRealtimeHelper.KEY_USERPROFILES);
        dbReference.child(user.getUid()).setValue(userProfileModel, (dbError, dbReference) -> {
                    if (dbError == null) {
                        callback.onUpdateUserProfileSucceeded("Successfully Updated");
                    }else{
                        callback.onUpdateUserProfileFailed("Failed to update user profile");
                    }

        });



    }

    @Override
    public void updateUserProfileImage(String userServerID, byte[] userProfileImageData, iUpdateUserProfileImageRepositoryCallback callback) {

    }

    /* ############################################# DELETE ACTIONS ############################################# */

    @Override
    public void uploadUserProfilesFromExcel(String filename, iUploadUserProfilesRepositoryCallback callback) {

    }

    @Override
    public ListenerRegistration readAllUserProfilesByChildEvent(cFirebaseChildCallBack firebaseChildCallBack) {
        return null;
    }
}
