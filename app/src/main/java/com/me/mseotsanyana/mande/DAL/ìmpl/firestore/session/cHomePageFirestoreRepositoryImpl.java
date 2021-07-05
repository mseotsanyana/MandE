package com.me.mseotsanyana.mande.DAL.ìmpl.firestore.session;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;
import com.me.mseotsanyana.mande.BLL.model.session.cUserAccountModel;
import com.me.mseotsanyana.mande.BLL.model.session.cUserProfileModel;
import com.me.mseotsanyana.mande.BLL.repository.session.iHomePageRepository;
import com.me.mseotsanyana.mande.DAL.storage.database.cRealtimeHelper;
import com.me.mseotsanyana.mande.DAL.ìmpl.cDatabaseUtils;

import java.util.List;
import java.util.Objects;


/**
 * Created by mseotsanyana on 2017/08/24.
 */

public class cHomePageFirestoreRepositoryImpl implements iHomePageRepository {
    private static final String TAG = cHomePageFirestoreRepositoryImpl.class.getSimpleName();

    private final FirebaseFirestore db;
    private final Context context;

    Gson gson = new Gson();

    public cHomePageFirestoreRepositoryImpl(Context context) {
        this.context = context;
        this.db = FirebaseFirestore.getInstance();
    }

    /* ############################################# READ ACTIONS ############################################# */

    @Override
    public void updateHomePageModels(String userServerID, String orgServerID, int primaryTeamBIT,
                                     List<Integer> secondaryTeamBITS, int statusBITS,
                                     List<Integer> statuses, int permBITS,
                                     iHomePageCallback callback) {

        /* read an organization of the current loggedIn user */
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        if (firebaseUser != null) {
            /* READ USER PROFILE */
            readUserProfile(firebaseUser, callback);
        } else {
            callback.onReadHomePageFailed("Failed to retrieve loggedIn user! " +
                    "Logout and login again!!");
            Log.w(TAG, "Failed to retrieve loggedIn user!!");
        }
    }

    /**
     * @param firebaseUser firebase user
     * @param callback     return user profile
     */
    private void readUserProfile(FirebaseUser firebaseUser, iHomePageCallback callback) {
        CollectionReference coUserProfilesRef;
        coUserProfilesRef = db.collection(cRealtimeHelper.KEY_USERPROFILES);

        coUserProfilesRef.document(firebaseUser.getUid()).get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        DocumentSnapshot doc = task.getResult();
                        if (doc != null) {
                            cUserProfileModel userProfileModel = doc.toObject(cUserProfileModel.class);
                            /* READ USER ACCOUNTS */
                            if (userProfileModel != null) {
                                userProfileModel.setUserServerID(firebaseUser.getUid());
                                readUserAccounts(userProfileModel, callback);
                            } else {
                                callback.onReadHomePageFailed("Failed to read user profiles");
                            }
                        } else {
                            callback.onReadHomePageFailed("Failed to read user profiles");
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        callback.onReadHomePageFailed("Failed to read user profiles");
                        Log.d(TAG, "Failed to read value.", e);
                    }
                });
    }

    /**
     * read an account of an active organization of the loggedIn user
     *
     * @param userProfileModel user profile
     * @param callback         return user accounts
     */
    private void readUserAccounts(cUserProfileModel userProfileModel,
                                  iHomePageCallback callback) {
        CollectionReference coUserAccountsRef;
        coUserAccountsRef = db.collection(cRealtimeHelper.KEY_USERACCOUNTS);
        Query userServerQuery = coUserAccountsRef
                .whereEqualTo("userServerID", userProfileModel.getUserServerID())
                .whereEqualTo("currentOrganization", true);

        userServerQuery.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        cUserAccountModel userAccountModel = null;
                        for (DocumentSnapshot user : Objects.requireNonNull(task.getResult())) {
                            userAccountModel = user.toObject(cUserAccountModel.class);
                            if ((userAccountModel != null) && (userAccountModel.isCurrentOrganization())) {
                                break;
                            }
                        }

                        /* check whether there is an active account, otherwise load default settings */
                        if (userAccountModel != null) {

                            /* call back on user profile */
                            callback.onReadUserProfileSucceeded(userProfileModel);

                            /* menu is saved in pref file when a user sign in - just retrieve it */
                            callback.onReadMenuItemsSucceeded();

                            /* FIXME: USER ACCOUNT TEAMS */
                            //readUserAccountTeams(userAccountModel, callback);

                        } else {
                            callback.onDefaultHomePageSucceeded(userProfileModel,
                                    cDatabaseUtils.getDefaultMenuModelSet(context));
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        callback.onReadHomePageFailed("Failed to read user accounts");
                        Log.d(TAG, "Failed to read value.", e);
                    }
                });

    }

//    /**
//     * read teams associated with the loggedIn user
//     *
//     * @param userAccountModel user account
//     * @param callback         return teams
//     */
//    private void readUserAccountTeams(cUserAccountModel userAccountModel,
//                                      iHomePageCallback callback) {
//        CollectionReference coUserAccountTeamsRef;
//        coUserAccountTeamsRef = db.collection(cRealtimeHelper.KEY_TEAM_MEMBERS);
//        Query teamServerQuery = coUserAccountTeamsRef
//                .whereEqualTo("userAccountServerID", userAccountModel.getUserAccountServerID());
//
//        teamServerQuery.get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        List<String> team_ids = new ArrayList<>();
//                        for (QueryDocumentSnapshot team : Objects.requireNonNull(task.getResult())) {
//                            Map<String, Object> teamMap = team.getData();
//                            String teamID = Objects.requireNonNull(teamMap.get("teamMemberServerID")).toString();
//                            team_ids.add(teamID);
//                        }
//                        /* MEMBER TEAMS */
//                        readTeamRoles(team_ids, callback);
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        callback.onReadHomePageFailed("Failed to read user teams ");
//                        Log.w(TAG, "Failed to read value.", e);
//                    }
//                });
//    }
//
//    /**
//     * read team roles
//     *
//     * @param callback return roles
//     */
//
//    private void readTeamRoles(List<String> team_ids, iHomePageCallback callback) {
//        CollectionReference coTeamRolesRef;
//        coTeamRolesRef = db.collection(cRealtimeHelper.KEY_TEAM_ROLES);
//        Query roleServerQuery = coTeamRolesRef
//                .whereIn("teamServerID", team_ids);
//
//        roleServerQuery.get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        List<String> role_ids = new ArrayList<>();
//                        for (QueryDocumentSnapshot role : Objects.requireNonNull(task.getResult())) {
//                            Map<String, Object> roleMap = role.getData();
//                            String roleID = Objects.requireNonNull(roleMap.get("roleServerID")).toString();
//                            role_ids.add(roleID);
//                        }
//                        /* TEAM ROLES */
//                        readRoleMenuItems(role_ids, callback);
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        callback.onReadHomePageFailed("Failed to read team roles: " +
//                                e.toString());
//                        Log.w(TAG, "Failed to read value.", e);
//                    }
//                });
//    }
//
//    /**
//     * read role menu items
//     *
//     * @param callback return role menu items
//     */
//    private void readRoleMenuItems(List<String> role_ids, iHomePageCallback callback) {
//        CollectionReference coRoleMenuItemsRef;
//
//        coRoleMenuItemsRef = db.collection(cRealtimeHelper.KEY_ROLE_MENU_ITEMS);
//        Query menuServerQuery = coRoleMenuItemsRef
//                .whereIn(FieldPath.documentId(), role_ids);
//
//        menuServerQuery.get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        //final int[] num_menu_items = {task.getResult().size()};
//                        //List<String> menu_ids = new ArrayList<>();
//                        //Gson gson = new Gson();
//                        //Log.d(TAG, "MENU ITEMS = "+task.getResult().getDocuments());
////                        Set<Integer> map = new <>();
////                        for (QueryDocumentSnapshot menu_item : Objects.requireNonNull(task.getResult())) {
////                            Map<String, Object> menu_items = menu_item.getData();
////
////                            Map<Integer, Object> treeMap = new TreeMap<>(menu_items);
////
////                            //List<String> keys = new ArrayList<>(menu_items.entrySet());
////                            //Collections.sort(keys);
////
////                            Log.d(TAG, "MENU ITEM ==============>>>>>>>>>>>>>>>>> " + treeMap.keySet());
////                            //Log.d(TAG, "MENU ITEM ==============>>>>>>>>>>>>>>>>> " + menu_items.values());
////                        }
//                        /* ROLE MENU ITEMS */
//                        //readMenuItems(menuID, menuModels, num_menu_items, callback);
//                        //readMenuItems(menu_ids, callback);
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        callback.onReadHomePageFailed("Failed to read role menu items: " +
//                                e.toString());
//                        Log.w(TAG, "Failed to read value.", e);
//                    }
//                });
//    }

    /**
     * read menu items
     *
     * @param callback return menu items
     */
//    private void readMenuItems(List<String> menu_ids, iUserProfileAndMenuItemsCallback callback) {
//        CollectionReference coMenuItemsRef;
//        coMenuItemsRef = db.collection(cRealtimeHelper.KEY_MENU_ITEMS);
//
//        //CollectionReference coRoleMenuItemsRef;
//        //coRoleMenuItemsRef = db.collection(cRealtimeHelper.KEY_ROLE_MENU_ITEMS);
//        Query menuServerQuery = coMenuItemsRef.whereIn("roleServerID", menu_ids);
//
//
//        coRoleMenuItemsRef.document(menuServerID).get()
//                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                        DocumentSnapshot doc = task.getResult();
//                        cMenuModel menuModel = doc.toObject(cMenuModel.class);
//                        menuModels.add(menuModel);
//
//                        if (num_menu_items[0] - 1 == 0) {
//                            /* call back on menu items */
//                            callback.onReadMenuItemsSucceeded(menuModels);
//                        } else {
//                            num_menu_items[0] = num_menu_items[0] - 1;
//                        }
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        callback.onReadHomePageFailed("Failed to read menu items: " +
//                                e.toString());
//                        Log.w(TAG, "Failed to read value.", e);
//                    }
//                });
//    }
}


