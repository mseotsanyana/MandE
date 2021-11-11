package com.me.mseotsanyana.mande.DAL.ìmpl.firestore.session;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.WriteBatch;
import com.me.mseotsanyana.mande.BLL.model.session.cUserProfileModel;
import com.me.mseotsanyana.mande.BLL.repository.session.iUserProfileRepository;
import com.me.mseotsanyana.mande.DAL.storage.database.cRealtimeHelper;
import com.me.mseotsanyana.mande.DAL.storage.excel.cExcelHelper;
import com.me.mseotsanyana.mande.DAL.ìmpl.cDatabaseUtils;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Created by mseotsanyana on 2016/10/23.
 */
public class cUserProfileFirestoreRepositoryImpl implements iUserProfileRepository {
    private static final String TAG = cUserProfileFirestoreRepositoryImpl.class.getSimpleName();

    // an object of the database helper
    private final cExcelHelper excelHelper;

    private final FirebaseFirestore db;
    private final FirebaseAuth firebaseAuth;
    private final Context context;

    public cUserProfileFirestoreRepositoryImpl(Context context) {
        this.context = context;
        this.firebaseAuth = FirebaseAuth.getInstance();
        this.db = FirebaseFirestore.getInstance();
        this.excelHelper = new cExcelHelper(context);
    }

    /* ##################################### CREATE ACTIONS ##################################### */

    @Override
    public void createUserWithEmailAndPassword(String firstname, String surname, String userEmail,
                                               String userPassword,
                                               iSignUpRepositoryCallback callback) {
        firebaseAuth.createUserWithEmailAndPassword(userEmail, userPassword)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                        assert user != null;
                        if (!user.isEmailVerified()) {
                            /* send an email verification */
                            sendEmailVerification(user);

                            /* update the profile in the firebase */
                            UserProfileChangeRequest profileUpdates;
                            profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(firstname + " " + surname).build();
                            user.updateProfile(profileUpdates).addOnCompleteListener(
                                    updateProfileTask -> {
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

                            CollectionReference coUsersRef;
                            coUsersRef = db.collection(cRealtimeHelper.KEY_USERPROFILES);
                            coUsersRef.document(user.getUid())
                                    .set(userProfileModel)
                                    .addOnFailureListener(e -> Log.d(TAG,
                                            Objects.requireNonNull(e.getLocalizedMessage())));

                            Log.d(TAG, "createUserWithEmailAndPassword:success");
                            callback.onSignUpSucceeded("Account successfully created.");
                        }
                    } else {
                        Log.d(TAG, "createUserWithEmailAndPassword:failure",
                                task.getException());
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

    /* ###################################### READ ACTIONS ###################################### */

    @Override
    public void signInWithEmailAndPassword(String email, String password,
                                           iSignInRepositoryCallback callback) {
        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    if (!user.isEmailVerified()) {
                        callback.onSignInFailed("Verification email sent to " +
                                user.getEmail());
                        FirebaseAuth.getInstance().signOut();
                    } else {
                        Log.d(TAG, "signInWithEmail:success");
                        callback.onSignInSucceeded();
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

    /* ###################################### READ ACTIONS ###################################### */

    @Override
    public void readMyUserProfile(iReadMyUserProfileRepositoryCallback callback) {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null) {
            CollectionReference coUserProfileRef = db.collection(cRealtimeHelper.KEY_USERPROFILES);
            coUserProfileRef.document(user.getUid()).get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            DocumentSnapshot doc = task.getResult();
                            if (doc != null) {
                                cUserProfileModel userProfile;
                                userProfile = doc.toObject(cUserProfileModel.class);
                                callback.onReadMyUserProfileSucceeded(userProfile);
                            }
                        } else {
                            callback.onReadMyUserProfileFailed(
                                    "Undefined error! Please report to the developer.");
                        }
                    })
                    .addOnFailureListener(e ->
                            callback.onReadMyUserProfileFailed("Failure to read user profile!"));
        } else {
            callback.onReadMyUserProfileFailed("Failure to read user profile!");
        }
    }

    @Override
    public void readUserProfiles(iReadUserProfilesRepositoryCallback callback) {
        FirebaseUser user = firebaseAuth.getCurrentUser();

        if (user != null) {
            CollectionReference coUserProfileRef;
            coUserProfileRef = db.collection(cRealtimeHelper.KEY_USERPROFILES);

            Query userProfileQuery = coUserProfileRef
                    .whereEqualTo("userOwnerID", user.getUid());

            userProfileQuery.get().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    List<cUserProfileModel> userProfileModels = new ArrayList<>();
                    for (DocumentSnapshot userprofile_doc : Objects.requireNonNull(task.getResult())) {
                        cUserProfileModel userProfileModel;
                        userProfileModel = userprofile_doc.toObject(cUserProfileModel.class);

                        if (userProfileModel != null) {
                            userProfileModels.add(userProfileModel);
                        }
                    }
                    callback.onReadUserProfilesSucceeded(userProfileModels);
                } else {
                    callback.onReadUserProfilesFailed("Failed to upload user profiles.");
                }
            });
        } else {
            callback.onReadUserProfilesFailed("Failure to read user profile!");
        }
    }

    /* ##################################### UPDATE ACTIONS ##################################### */

    @Override
    public void updateUserProfile(long userID, int primaryRole, int secondaryRoles, int statusBITS,
                                  cUserProfileModel userProfileModel,
                                  iUpdateUserProfileRepositoryCallback callback) {

        FirebaseUser user = firebaseAuth.getCurrentUser();

        if (user != null) {
            userProfileModel.setEmail(user.getEmail());
            CollectionReference coUserProfileRef = db.collection(cRealtimeHelper.KEY_USERPROFILES);
            coUserProfileRef.document(user.getUid()).set(userProfileModel)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            callback.onUpdateUserProfileSucceeded("Successfully Updated");
                        } else {
                            callback.onUpdateUserProfileFailed(
                                    "Undefined error! Talk to the Admin");
                        }
                    })
                    .addOnFailureListener(e -> callback.onUpdateUserProfileFailed(
                            "Failed to update user profile " + e));
        } else {
            callback.onUpdateUserProfileFailed("Failed to update user profile!");
        }
    }

    /* ##################################### DELETE ACTIONS ##################################### */


    /* ##################################### UPLOAD ACTIONS ##################################### */

    @Override
    public void uploadUserProfilesFromExcel(iUploadUserProfilesRepositoryCallback callback) {
        Workbook workbook = excelHelper.getWorkbookSESSION();
        Sheet userProfileSheet = workbook.getSheet(cExcelHelper.SHEET_tblUSERPROFILE);

        Log.d(TAG, "IAM HERS========================= "+userProfileSheet.getSheetName());

        // user profile
        List<cUserProfileModel> userProfileModels = new ArrayList<>();

        for (Row userProfileRow : userProfileSheet) {
            //just skip the row if row number is 0
            if (userProfileRow.getRowNum() == 0) {
                continue;
            }

            cUserProfileModel userProfileModel = new cUserProfileModel();

            userProfileModel.setUserServerID(String.valueOf(
                    cDatabaseUtils.getCellAsNumeric(userProfileRow, 0)));
            userProfileModel.setName(cDatabaseUtils.getCellAsString(userProfileRow, 1));
            userProfileModel.setSurname(cDatabaseUtils.getCellAsString(userProfileRow, 2));
            userProfileModel.setDesignation(cDatabaseUtils.getCellAsString(userProfileRow, 3));
            userProfileModel.setLocation(cDatabaseUtils.getCellAsString(userProfileRow, 4));
            userProfileModel.setEmail(cDatabaseUtils.getCellAsString(userProfileRow, 5));
            userProfileModel.setWebsite(cDatabaseUtils.getCellAsString(userProfileRow, 6));
            userProfileModel.setPhone(cDatabaseUtils.getCellAsString(userProfileRow, 7));

            /* user who created user profiles */
            FirebaseUser user = firebaseAuth.getCurrentUser();
            assert user != null;
            userProfileModel.setUserOwnerID(user.getUid());

            Date now = new Date();
            userProfileModel.setCreatedDate(now);
            userProfileModel.setModifiedDate(now);

            userProfileModels.add(userProfileModel);

        }

        // add logframes
        createUserProfileFromExcel(userProfileModels, callback);

    }

    private void createUserProfileFromExcel(List<cUserProfileModel> userProfileModels,
                                            iUploadUserProfilesRepositoryCallback callback) {

        CollectionReference coUserProfileRef;
        coUserProfileRef = db.collection(cRealtimeHelper.KEY_USERPROFILES);

        /* create a batch object */
        WriteBatch batch = db.batch();

        /* add user profiles  */
        for (cUserProfileModel userProfileModel : userProfileModels) {
            String userProfileID = coUserProfileRef.document().getId();
            batch.set(coUserProfileRef.document(userProfileID), userProfileModel);
        }

        batch.commit().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                callback.onUploadUserProfilesSucceeded("User Profile module successfully uploaded");
            } else {
                callback.onUploadUserProfilesFailed("Failed to update User Profile module");
            }
        });
    }
}
