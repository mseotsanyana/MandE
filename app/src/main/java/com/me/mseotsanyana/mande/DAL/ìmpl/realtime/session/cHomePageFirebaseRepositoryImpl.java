package com.me.mseotsanyana.mande.DAL.ìmpl.realtime.session;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.me.mseotsanyana.mande.BLL.model.session.cMenuModel;
import com.me.mseotsanyana.mande.BLL.model.session.cUserAccountModel;
import com.me.mseotsanyana.mande.BLL.model.session.cUserProfileModel;
import com.me.mseotsanyana.mande.BLL.repository.session.iHomePageRepository;
import com.me.mseotsanyana.mande.BLL.repository.session.iSharedPreferenceRepository;
import com.me.mseotsanyana.mande.DAL.storage.database.cRealtimeHelper;
import com.me.mseotsanyana.mande.DAL.ìmpl.cDatabaseUtils;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by mseotsanyana on 2017/08/24.
 */

public class cHomePageFirebaseRepositoryImpl implements iHomePageRepository {
    //private static SimpleDateFormat sdf = cConstant.FORMAT_DATE;
    private static final String TAG = cHomePageFirebaseRepositoryImpl.class.getSimpleName();

    private final Context context;
    private final FirebaseDatabase database;

    public cHomePageFirebaseRepositoryImpl(Context context) {
        this.context = context;
        this.database = FirebaseDatabase.getInstance();
    }

    /* ############################################# READ ACTIONS ############################################# */

    @Override
    public void loadHomePage(boolean isPermissionLoaded,
                             iSharedPreferenceRepository sharedPreferenceRepository,
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
        DatabaseReference dbUserProfilesRef;
        dbUserProfilesRef = database.getReference(cRealtimeHelper.KEY_USERPROFILES);
        dbUserProfilesRef.child(firebaseUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                cUserProfileModel userProfileModel = snapshot.getValue(cUserProfileModel.class);
                /* READ USER ACCOUNTS */
                assert userProfileModel != null;
                readUserAccounts(userProfileModel, callback);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callback.onReadHomePageFailed("Failed to read user profiles: " +
                        error.toException());
                Log.d(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    /**
     * @param userProfileModel user profile
     * @param callback         return user accounts
     */
    private void readUserAccounts(cUserProfileModel userProfileModel,
                                  iHomePageCallback callback) {
        DatabaseReference dbUserAccountsRef;
        dbUserAccountsRef = database.getReference(cRealtimeHelper.KEY_USERACCOUNTS);
        Query userServerQuery = dbUserAccountsRef.orderByChild("userServerID").equalTo(
                userProfileModel.getUserServerID());

        userServerQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                cUserAccountModel userAccountModel = null;
                /* search for a current active user account */
                for (DataSnapshot useraccount : snapshot.getChildren()) {
                    userAccountModel = useraccount.getValue(cUserAccountModel.class);

                    assert userAccountModel != null;
                    if (userAccountModel.isCurrentOrganization()) {
                        break;
                    }
                }
                /* check whether there is an active account, otherwise load default settings */
                if (userAccountModel != null) {

                    /* call back on user profile */
                    callback.onReadUserProfileSucceeded(userProfileModel);

                    /* USER ACCOUNT TEAMS */
                    readUserAccountTeams(userAccountModel, callback);

                } else {
                    callback.onDefaultHomePageSucceeded(
                            cDatabaseUtils.getDefaultMenuModelSet(context));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callback.onReadHomePageFailed("Failed to read user accounts: " +
                        error.toException());
                Log.d(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    /**
     * @param userAccountModel user account
     * @param callback         return teams
     */
    private void readUserAccountTeams(cUserAccountModel userAccountModel,
                                      iHomePageCallback callback) {
        DatabaseReference dbUserAccountTeamsRef;
        dbUserAccountTeamsRef = database.getReference(cRealtimeHelper.KEY_MEMBER_TEAMS);
        dbUserAccountTeamsRef.child(userAccountModel.getUserAccountServerID()).
                addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot dataSnapshotTeamID : dataSnapshot.getChildren()) {
                            String teamID = dataSnapshotTeamID.getKey();
                            /* TEAM ROLES */
                            readTeamRoles(teamID, callback);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        callback.onReadHomePageFailed("Failed to read user teams: " +
                                error.toException());
                        Log.w(TAG, "Failed to read value.", error.toException());
                    }
                });

    }

    /**
     * @param teamID   team identification
     * @param callback return roles
     */
    private void readTeamRoles(String teamID, iHomePageCallback callback) {
        DatabaseReference dbUserAccountRolesRef;
        dbUserAccountRolesRef = database.getReference(cRealtimeHelper.KEY_TEAM_ROLES);
        dbUserAccountRolesRef.child(teamID).
                addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot dataSnapshotRoleID : dataSnapshot.getChildren()) {
                            String roleID = dataSnapshotRoleID.getKey();

                            /* ROLE MENU ITEMS */
                            readRoleMenuItems(roleID, callback);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        callback.onReadHomePageFailed("Failed to read team roles: " +
                                error.toException());
                        Log.w(TAG, "Failed to read value.", error.toException());
                    }
                });
    }

    /**
     * @param roleID   role identification
     * @param callback return role menu items
     */
    private void readRoleMenuItems(String roleID, iHomePageCallback callback) {
        DatabaseReference dbRoleMenuItemsRef;
        dbRoleMenuItemsRef = database.getReference(cRealtimeHelper.KEY_ROLE_PERMISSIONS);
        dbRoleMenuItemsRef.child(roleID).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Set<cMenuModel> menuModels = new HashSet<>();
                        for (DataSnapshot menuItem : snapshot.getChildren()) {
                            cMenuModel menuModel = menuItem.getValue(cMenuModel.class);
                            menuModels.add(menuModel);
                        }
                        /* call back on menu items */
                        //callback.onReadMenuItemsSucceeded(menuModels);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        callback.onReadHomePageFailed("Failed to read role menu items: " +
                                error.toException());
                        Log.w(TAG, "Failed to read value.", error.toException());
                    }
                });
    }

}

//    /**
//     *
//     * @param userAccountModel user account
//     * @param callback return roles
//     */
//    private void readTeamRoles(cUserAccountModel userAccountModel,
//                                      iUpdateHomePageCallback callback) {
//        DatabaseReference dbUserAccountRolesRef;
//        dbUserAccountRolesRef = database.getReference(cRealtimeHelper.KEY_TEAM_ROLES);
//        dbUserAccountRolesRef.child(userAccountModel.getUserAccountServerID()).
//                addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        List<cRoleModel> roleModels = new ArrayList<>();
//                        final long[] numRoles = {dataSnapshot.getChildrenCount()};
//                        for (DataSnapshot dataSnapshotRole : dataSnapshot.getChildren()) {
//                            /* ROLES */
//                            readRoles(roleModels, numRoles, dataSnapshotRole, callback);
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//                        callback.onReadHomePageFailed("Failed to read user roles: " +
//                                error.toException());
//                        Log.w(TAG, "Failed to read value.", error.toException());
//                    }
//                });
//    }
//
//    /**
//     *
//     * @param roleModels roles
//     * @param numRoles number of roles
//     * @param roleID data snapshot for the role identification
//     * @param callback return roles
//     */
//    private void readRoles(List<cRoleModel> roleModels, final long[] numRoles,
//                           String roleID, iUpdateHomePageCallback callback) {
//        DatabaseReference dbRolesRef = database.getReference(cRealtimeHelper.KEY_ROLES);
//
//        dbRolesRef.child(Objects.requireNonNull(roleID)).
//                addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        cRoleModel roleModel = snapshot.getValue(cRoleModel.class);
//                        assert roleModel != null;
//                        roleModels.add(roleModel);
//
//                        /* add all the roles in the list before a call back on the list */
//                        if (numRoles[0] - 1 == 0) {
//                            callback.onReadRolesSucceeded(roleModels);
//                        } else {
//                            numRoles[0] = numRoles[0] - 1;
//                        }
//
//                        /* ROLE MENU ITEMS */
//                        readRoleMenuItems(roleModel, callback);
//
//                        /* ROLE PRIVILEGES */
//                        //readRolePrivileges(roleModel, callback);
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//                        callback.onReadHomePageFailed("Failed to read roles: " +
//                                error.toException());
//                        Log.w(TAG, "Failed to read value.", error.toException());
//                    }
//                });
//    }
//
//
//
//    /**
//     *
//     * @param roleModel role
//     * @param callback return role privileges
//     */
//    private void readRolePrivileges(cRoleModel roleModel, iUpdateHomePageCallback callback) {
//        DatabaseReference dbRolePrivilegesRef;
//        dbRolePrivilegesRef = database.getReference(cRealtimeHelper.KEY_ROLE_PRIVILEGES);
//        dbRolePrivilegesRef.child(roleModel.getRoleServerID()).addListenerForSingleValueEvent(
//                new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        List<cPrivilegeModel> privilegeModels = new ArrayList<>();
//                        final long[] numPrivileges = {snapshot.getChildrenCount()};
//                        for (DataSnapshot privilege : snapshot.getChildren()) {
//                            /* PRIVILEGES */
//                            readPrivileges(roleModel, privilegeModels, numPrivileges, privilege, callback);
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//                        callback.onReadHomePageFailed("Failed to read role privileges: " +
//                                error.toException());
//                        Log.w(TAG, "Failed to read value.", error.toException());
//                    }
//                });
//    }
//
//    /**
//     *
//     * @param privilegeModels privileges
//     * @param numPrivileges number of privileges
//     * @param privilege privilege
//     * @param callback return privileges
//     */
//    private void readPrivileges(cRoleModel roleModel, List<cPrivilegeModel> privilegeModels, final long[] numPrivileges,
//                                DataSnapshot privilege, iUpdateHomePageCallback callback) {
//        DatabaseReference dbPrivilegesRef;
//        dbPrivilegesRef = database.getReference(cRealtimeHelper.KEY_PRIVILEGES);
//        dbPrivilegesRef.child(Objects.requireNonNull(privilege.getKey())).
//                addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        cPrivilegeModel privilegeModel;
//                        privilegeModel = snapshot.getValue(cPrivilegeModel.class);
//                        privilegeModels.add(privilegeModel);
//
//                        /* add all the roles in the list before a call back on the list */
//                        if (numPrivileges[0] - 1 == 0) {
//                            callback.onReadRolePrivilegesSucceeded(roleModel, privilegeModels);
//                        } else {
//                            numPrivileges[0] = numPrivileges[0] - 1;
//                        }
//
//                        /* PRIVILEGE PERMISSIONS */
//                        Log.d(TAG, "==============================>>>>>>"+gson.toJson(privilegeModel));
//
//                        assert privilegeModel != null;
//                        //readPrivilegePermissions(privilegeModel, callback);
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//                        callback.onReadHomePageFailed("Failed to read privileges: " +
//                                error.toException());
//                        Log.w(TAG, "Failed to read value.", error.toException());
//                    }
//                });
//    }
//
//    /**
//     *
//     * @param privilegeModel privilege
//     * @param callback return on privilege permissions
//     */
//    private void readPrivilegePermissions(cPrivilegeModel privilegeModel,
//                                          iUpdateHomePageCallback callback) {
//        DatabaseReference dbPrivilegePermsRef;
//        dbPrivilegePermsRef = database.getReference(cRealtimeHelper.KEY_PRIVILEGE_PERMISSIONS);
//        dbPrivilegePermsRef.child(privilegeModel.getPrivilegeServerID()).
//                addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        List<cPermissionModel> permissionModels = new ArrayList<>();
//                        for (DataSnapshot perm : snapshot.getChildren()) {
//                            cPermissionModel permissionModel = perm.getValue(cPermissionModel.class);
//                            permissionModels.add(permissionModel);
//                        }
//                        /* call back on permissions */
//                        callback.onReadPrivilegePermissionsSucceeded(privilegeModel, permissionModels);
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//                        callback.onReadHomePageFailed("Failed to read privilege permissions: "
//                                + error.toException());
//                        Log.w(TAG, "Failed to read value.", error.toException());
//                    }
//                });
//    }


