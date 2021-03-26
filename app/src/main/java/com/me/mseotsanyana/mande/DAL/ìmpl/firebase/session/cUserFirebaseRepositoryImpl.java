package com.me.mseotsanyana.mande.DAL.ìmpl.firebase.session;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.me.mseotsanyana.mande.BLL.model.session.cUserModel;
import com.me.mseotsanyana.mande.BLL.repository.session.iUserRepository;
import com.me.mseotsanyana.mande.DAL.storage.database.cRealtimeHelper;
import com.me.mseotsanyana.mande.DAL.storage.database.cSQLDBHelper;
import com.me.mseotsanyana.mande.UTIL.cConstant;

import java.text.SimpleDateFormat;
import java.util.Set;

/**
 * Created by mseotsanyana on 2016/10/23.
 */
public class cUserFirebaseRepositoryImpl implements iUserRepository {
    private static SimpleDateFormat sdf = cConstant.FORMAT_DATE;
    private static String TAG = cUserFirebaseRepositoryImpl.class.getSimpleName();
    private cSQLDBHelper dbHelper;

    // an object of the database helper
    private FirebaseDatabase database;
    private DatabaseReference dbReference;
    private FirebaseAuth firebaseAuth;
    private Context context;

    public cUserFirebaseRepositoryImpl(Context context) {
        this.context = context;
        dbHelper = new cSQLDBHelper(context);
        firebaseAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
    }

    /* ############################################# CREATE ACTIONS ############################################# */

    @Override
    public void createUserWithEmailAndPassword(String userEmail, String userPassword, String firstName, String surname,
                                               iSignUpRepositoryCallback callback) {

        //FirebaseAuth.getInstance().signOut();

        firebaseAuth.createUserWithEmailAndPassword(userEmail, userPassword)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            /* update the profile in the firebase */
                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(firstName + " " + surname).build();
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            user.updateProfile(profileUpdates)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Log.d(TAG, "User profile updated.");
                                            }
                                        }
                                    });

                            /* update the user profile in the database */
                            dbReference = database.getReference(cRealtimeHelper.KEY_USERS);
                            dbReference.child(user.getUid())
                                    .setValue(firstName)
                                    .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG, e.getLocalizedMessage());
                                }
                            });;

                            Log.d(TAG, "createUserWithEmailAndPassword:success");
                            callback.onSignUpSucceeded("Account successfully created.");
                        } else {
                            Log.d(TAG, "createUserWithEmailAndPassword:failure", task.getException());
                            callback.onSignUpFailed("Failed to create a new account. " +
                                    "Try a different email.");
                        }
                    }
                });
    }

    /* ############################################# READ ACTIONS ############################################# */

    @Override
    public void signInWithEmailAndPassword(String email, String password,
                                           iSignInRepositoryCallback callback) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            cUserModel userModel = getUserByID(user.getUid());
                            callback.onSignInSucceeded(userModel);
                        } else {
                            Log.d(TAG, "signInWithEmail:failure", task.getException());
                            callback.onSignInFailed("Authentication failed.");
                        }
                    }
                });
    }

    @Override
    public void signOutWithEmailAndPassword(iSignOutRepositoryCallback callback) {
        //PreferenceUtil.signOut(this);
        FirebaseUser user = firebaseAuth.getInstance().getCurrentUser();
        if(user != null) {
            firebaseAuth.signOut();
            callback.onSignOutSucceeded("Logged Out");
        }else {
            callback.onSignOutFailed("Already Logged Out !!!");
        }
    }

    public cUserModel getUserByID(String UID) {
        return null;
    }

    @Override
    public cUserModel getUserByEmailPassword(String email, String password) {
        return null;
    }

    @Override
    public Set<cUserModel> getOwnerSet() {
        return null;
    }

    /* ############################################# UPDATE ACTIONS ############################################# */

    //FIXME: update user profile
    public boolean updateUserProfile(cUserModel userModel) {
        //database = FirebaseDatabase.getInstance();
        dbReference = database.getReference(cRealtimeHelper.KEY_USERS);
        final boolean[] r = {true};
        dbReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Log.d(Constants.LOG_TAG,"SUCCESS!");
                //handleReturn(dataSnapshot);
                r[0] = true;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //Log.e(Constants.LOG_TAG,"ERROR: " + databaseError.getMessage());
                //Toast.makeText(getContext(), R.string.chat_init_error, Toast.LENGTH_SHORT).show();
                //mCallback.logout();
                r[0] = false;
            }
        });

        return r[0];
    }


    public boolean updateUser(cUserModel userModel) {
        return true;
    }

    /* ############################################# DELETE ACTIONS ############################################# */

    /**
     * Delete all users
     *
     * @return Boolean
     */
    public boolean deleteUsers() {
        return true;
    }

    public boolean deleteUser(int userID) {
        return true;
    }
}
