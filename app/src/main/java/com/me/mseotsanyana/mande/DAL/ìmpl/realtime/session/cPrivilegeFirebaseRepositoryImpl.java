package com.me.mseotsanyana.mande.DAL.ìmpl.realtime.session;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.me.mseotsanyana.mande.BLL.model.session.cUserAccountModel;
import com.me.mseotsanyana.mande.BLL.repository.session.iPrivilegeRepository;
import com.me.mseotsanyana.mande.DAL.storage.database.cRealtimeHelper;

public class cPrivilegeFirebaseRepositoryImpl implements iPrivilegeRepository {
    private static final String TAG = cPrivilegeFirebaseRepositoryImpl.class.getSimpleName();

    private final FirebaseDatabase database;


    public cPrivilegeFirebaseRepositoryImpl() {
        this.database = FirebaseDatabase.getInstance();
    }

    /*################################# SHARED PREFERENCE ACTIONS ################################*/

    @Override
    public void saveUserPrivileges(iPrivilegeRepository.iSaveUserPrivilegesCallback callback) {

        DatabaseReference dbUserAccountsRef = database.getReference(cRealtimeHelper.KEY_USERACCOUNTS);

        String userServerID = FirebaseAuth.getInstance().getUid();
        Query userAccountQuery = dbUserAccountsRef.orderByChild("userServerID").equalTo(userServerID);
        userAccountQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                callback.onClearPreferences();
                for (DataSnapshot useraccount : snapshot.getChildren()) {
                    cUserAccountModel userAccountModel = useraccount.getValue(cUserAccountModel.class);

                    if (userAccountModel.isCurrentOrganization()) {
                        String userAccountID  = userAccountModel.getUserAccountServerID();
                        String organizationID = userAccountModel.getOrganizationServerID();
                        String primaryTeamID  = userAccountModel.getTeamServerID();

                        /* call back on saving organization identification */
                        callback.onSaveOrganizationServerID(organizationID);
                        /* call back on saving primary bit */
                        callback.onSavePrimaryTeamBIT(Integer.parseInt(primaryTeamID));

                        saveSecondaryTeamsBITS(userAccountID, primaryTeamID, callback);

                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    /**
     * This sets secondary teams bits of the loggedIn user and saves it.
     *
     * @param accountServerID user account identification
     */
    public void saveSecondaryTeamsBITS(String accountServerID, String primaryTeamID,
                                       iSaveUserPrivilegesCallback callback) {
        DatabaseReference dbMemberTeamsRef = database.getReference(cRealtimeHelper.KEY_MEMBER_TEAMS);
        dbMemberTeamsRef.child(accountServerID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                int secondaryTeamBITS = 0;
                for (DataSnapshot team : snapshot.getChildren()) {
                    String teamID = team.child("teamServerID").getValue(String.class);
                    secondaryTeamBITS |= Integer.parseInt(teamID);
                    // Team Roles
                    readTeamsRoles(team.getKey(), callback);
                }
                secondaryTeamBITS &= ~Integer.parseInt(primaryTeamID);

                /* call back on saving secondary bits */
                callback.onSaveSecondaryTeamBITS(secondaryTeamBITS);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void readTeamsRoles(String teamServerID, iSaveUserPrivilegesCallback callback){
        DatabaseReference dbTeamsRolesRef = database.getReference(cRealtimeHelper.KEY_TEAM_ROLES);
        dbTeamsRolesRef.child(teamServerID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot role: snapshot.getChildren()) {
                    Log.d(TAG, "DataSnapshot ROLES ==>> " + role);
                    readRolesPrivileges(role.getKey(), callback);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void readRolesPrivileges(String roleServerID, iSaveUserPrivilegesCallback callback){
        DatabaseReference dbRolesPrivilegesRef = database.getReference(cRealtimeHelper.KEY_ROLE_PRIVILEGES);
        dbRolesPrivilegesRef.child(roleServerID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot privilege: snapshot.getChildren()) {
                    Log.d(TAG, "DataSnapshot PRIVILEGE ==>> " + privilege);

                    readPrivilegePermission(privilege.getKey(), callback);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void readPrivilegePermission(String privilegeServerID, iSaveUserPrivilegesCallback callback){
        DatabaseReference dbPrivilegesPermsRef = database.getReference(cRealtimeHelper.KEY_PRIVILEGE_PERMISSIONS);
        dbPrivilegesPermsRef.child(privilegeServerID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot module : snapshot.getChildren()) {
                    int entityBITS = 0;
                    for (DataSnapshot entity: module.getChildren()){
                        int operationBITS = 0, statusBITS = 0;
                        for(DataSnapshot operation: entity.getChildren()){
                            operationBITS |= Integer.parseInt(operation.getKey());
                            statusBITS |= operation.getValue(Integer.class);
                            //callback.onSaveStatusBITS(module.getKey(), entity.getKey(), operation.getKey(), statusBITS);
                        }
                        callback.onSaveOperationBITS(module.getKey(), entity.getKey(), operationBITS);
                        entityBITS |= Integer.parseInt(entity.getKey());
                    }
                    callback.onSaveEntityBITS(module.getKey(), entityBITS);
                    //moduleBITS |= Integer.parseInt(module.getKey());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}


//
//    /**
//     * @param permissionModelSet
//     */
//    public void savePermissionBITS(Set<cPermissionModel> permissionModelSet) {
//        ArrayList<cPermissionModel> perm = new ArrayList<>(permissionModelSet);
//        for (int i = 0; i < perm.size(); i++) {
//            /* save operation BITS for an entity : ENTITY-TYPE->OPS_BITS*/
//            int operationBITS = 0;
//            for (int j = 0; j < perm.size(); j++) {
//                if (perm.get(i).getEntityServerID() == perm.get(j).getEntityServerID() &&
//                        (perm.get(i).getEntityTypeServerID() == perm.get(j).getEntityTypeServerID())) {
//                    operationBITS |= Integer.parseInt(perm.get(j).getOperationServerID());
//                }
//            }
//            StringBuilder opsKey = new StringBuilder(cSharedPreference.KEY_ENTITY_OPERATION_BITS);
//            opsKey.append("-");
//            opsKey.append(perm.get(i).getEntityServerID());
//            opsKey.append("-");
//            opsKey.append(perm.get(i).getEntityTypeServerID());
//            editor.putInt(String.valueOf(opsKey), operationBITS);
//
//            /* save status BITS for a privilege: ENTITY-TYPE-OPERATION->STATUS_BITS*/
//            cStatusSetModel statusSetModel = null;//perm.get(i).getStatusSetModel();
//            ArrayList<cStatusModel> status = new ArrayList<>(statusSetModel.getStatusModelSet());
//
//            int statusBITS = 0;
//            for (int k = 0; k < status.size(); k++) {
//                statusBITS |= status.get(k).getStatusID();
//            }
//            StringBuilder statusKey = new StringBuilder(cSharedPreference.KEY_OPERATION_STATUS_BITS);
//            statusKey.append("-");
//            statusKey.append(perm.get(i).getEntityServerID());
//            statusKey.append("-");
//            statusKey.append(perm.get(i).getEntityTypeServerID());
//            statusKey.append("-");
//            statusKey.append(perm.get(i).getOperationServerID());
//            editor.putInt(String.valueOf(statusKey), (Integer) statusBITS);
//        }
//
//        /* save entity BITS for an entity : ENTITY-TYPE->BITS */
//        for (int i = 0; i < types.length; i++) {
//            int entityBITS = 0;
//            for (cPermissionModel entityModel : permissionModelSet) {
//                if (Integer.parseInt(entityModel.getEntityTypeServerID()) == types[i]) {
//                    entityBITS |= Integer.parseInt(entityModel.getEntityServerID());
//                }
//            }
//            StringBuilder entityKey = null;//new StringBuilder(cSharedPreference.KEY_ENTITY_TYPE_BITS);
//            entityKey.append("-");
//            entityKey.append(types[i]);
//            editor.putInt(String.valueOf(entityKey), (Integer) entityBITS);
//        }
//    }
//
//
//    /**
//     * This returns user ID of the loggedIn user. If no user, it returns -1.
//     *
//     * @return userID user identification
//     */
//    public String loadUserID() {
//        return preferences.getString(cSharedPreference.KEY_USER_ID, null);
//    }
//
//    /**
//     * This returns organization ID of the loggedIn user. If no organization,
//     * it returns null.
//     *
//     * @return organizationID organization identification
//     */
//    public String loadOrganizationID() {
//        return preferences.getString(cSharedPreference.KEY_ORG_ID, null);
//    }
//
//    /**
//     * This returns a primary role bits of the loggedIn user. If no primary role,
//     * it returns -1.
//     *
//     * @return primary team bit/identification
//     */
//    public int loadPrimaryTeamBIT() {
//        return preferences.getInt(cSharedPreference.KEY_PRIMARY_TEAM_BIT, -1);
//    }
//
//    /**
//     * This returns a secondary roles of the loggedIn user. If no secondary roles,
//     * it returns -1.
//     *
//     * @return secondary teams
//     */
//    public int loadSecondaryTeamBITS() {
//        return preferences.getInt(cSharedPreference.KEY_SECONDARY_TEAM_BITS, -1);
//    }
//
//
//    public void saveDefaultPermsBITS(int bitNumber) {
//        editor.putInt(cSharedPreference.KEY_PERMS_BITS, bitNumber);
//    }
//
//    public int loadDefaultPermsBITS() {
//        return preferences.getInt(cSharedPreference.KEY_PERMS_BITS, -1);
//    }
//
//    public void saveDefaultStatusBITS(int bitNumber) {
//        editor.putInt(cSharedPreference.KEY_STATUS_BITS, bitNumber);
//    }
//
//    public int loadDefaultStatusBITS() {
//        return preferences.getInt(cSharedPreference.KEY_STATUS_BITS, -1);
//    }
//
//    /**
//     * This sets the user model json string of the loggedIn user and saves it.
//     *
//     * @param userModel
//     */
//    public void saveUserProfile(cUserModel userModel) {
//        String userProfile = gson.toJson(userModel);
//        editor.putString(cSharedPreference.KEY_USER_PROFILE, userProfile);
//    }
//
//    /**
//     * This returns the json string of the loggedIn user domain. If no user is
//     * loggedIn it returns null
//     *
//     * @return json string
//     */
//    public String loadUserProfile() {
//        return preferences.getString(cSharedPreference.KEY_USER_PROFILE, null);
//    }
//
//
//    /**
//     * This returns entity BITS that belong to an entity type.
//     *
//     * @param entityTypeID
//     * @return
//     */
//    public int loadEntityBITS(long entityTypeID) {
//        StringBuilder entityTypeKey = new StringBuilder(cSharedPreference.KEY_MODULE_ENTITY_BITS);
//        entityTypeKey.append("-");
//        entityTypeKey.append(entityTypeID);
//        return preferences.getInt(String.valueOf(entityTypeKey), 0);
//    }
//
//    /**
//     * This returns operation BITS that belong to an entity with the specified
//     * entityID and entityTypeID.
//     *
//     * @param entityID
//     * @param entityTypeID
//     * @return
//     */
//    public int loadOperationBITS(long entityID, long entityTypeID) {
//        StringBuilder operationKey = new StringBuilder(cSharedPreference.KEY_ENTITY_OPERATION_BITS);
//        operationKey.append("-");
//        operationKey.append(entityID);
//        operationKey.append("-");
//        operationKey.append(entityTypeID);
//        return preferences.getInt(String.valueOf(operationKey), 0);
//    }
//
//    /**
//     * This returns status BITS that apply to operations that belong to an permission
//     * with the specified permissionID
//     *
//     * @param entityID
//     * @param entityTypeID
//     * @param operationID
//     * @return
//     */
//    public int loadStatusBITS(long entityID, long entityTypeID, long operationID) {
//        StringBuilder statusKey = new StringBuilder(cSharedPreference.KEY_OPERATION_STATUS_BITS);
//        statusKey.append("-");
//        statusKey.append(entityID);
//        statusKey.append("-");
//        statusKey.append(entityTypeID);
//        statusKey.append("-");
//        statusKey.append(operationID);
//        return preferences.getInt(String.valueOf(statusKey), 0);
//    }
//
//    @Override
//    public void saveStatusSet(Set<cStatusModel> statusModelSet) {
//        String statusSet = gson.toJson(statusModelSet);
//        editor.putString(cSharedPreference.KEY_STATUS_SET, statusSet);
//    }
//
//    @Override
//    public void saveRoleSet(Set<cRoleModel> roleModelSet) {
//        String roleSet = gson.toJson(roleModelSet);
//        editor.putString(cSharedPreference.KEY_ROLE_SET, roleSet);
//    }
//
//    @Override
//    public Set loadStatusSet() {
//        String statusString = preferences.getString(cSharedPreference.KEY_STATUS_SET,"");
//
//        Set<cStatusModel> statusModelSet = gson.fromJson(statusString,
//                new TypeToken<Set<cStatusModel>>(){}.getType());
//
//        return statusModelSet;
//    }
//
//    @Override
//    public Set loadRoleSet() {
//        String roleString = preferences.getString(cSharedPreference.KEY_ROLE_SET,"");
//
//        Set<cRoleModel> statusModelSet = gson.fromJson(roleString,
//                new TypeToken<Set<cRoleModel>>(){}.getType());
//
//        return statusModelSet;
//    }
//
//    @Override
//    public void saveIndividualOwners(Set<cUserModel> userModels) {
//        String userSet = gson.toJson(userModels);
//        editor.putString(cSharedPreference.KEY_INDIVIDUAL_OWNER_SET, userSet);
//    }
//
//    @Override
//    public void saveOrganizationOwners(Set<cOrganizationModel> organizationModels) {
//        String organizationSet = gson.toJson(organizationModels);
//        editor.putString(cSharedPreference.KEY_ORGANIZATION_OWNER_SET, organizationSet);
//    }
//
//    @Override
//    public Set loadIndividualOwners() {
//        String ownerString = preferences.getString(
//                cSharedPreference.KEY_INDIVIDUAL_OWNER_SET,"");
//
//        Set<cUserModel> userModels = gson.fromJson(ownerString,
//                new TypeToken<Set<cUserModel>>(){}.getType());
//
//        return userModels;
//    }
//
//    @Override
//    public Set loadOrganizationOwners() {
//        String organizationString = preferences.getString(
//                cSharedPreference.KEY_ORGANIZATION_OWNER_SET,"");
//
//        Set<cOrganizationModel> organizationModels = gson.fromJson(organizationString,
//                new TypeToken<Set<cOrganizationModel>>(){}.getType());
//
//        return organizationModels;
//    }


//    /* #################### FUNCTIONS FOR SAVING AND LOADING CURRENT SETTINGS ####################*/
//
//    /**
//     * This method can be called to check whether there is any logged in user.
//     * If no user is loggedIn, it returns false otherwise true
//     */
//    public boolean isLoggedIn() {
//        return preferences.getBoolean(cSharedPreference.KEY_IS_LOGGEDIN, false);
//    }
//
//    /**
//     * This sets the loggedIn variable to true
//     */
//    public void loginUser() {
//        editor.putBoolean(cSharedPreference.KEY_IS_LOGGEDIN, true);
//    }
//
//    /**
//     * This sets the loggedIn variable to true
//     */
//    public void logoutUser() {
//        editor.putBoolean(cSharedPreference.KEY_IS_LOGGEDIN, false);
//    }
//
//    /**
//     * This sets user ID (which is used as owner ID) for records and saves it.
//     */
//    public void saveUserID() {
//        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
//        editor.putString(cSharedPreference.KEY_USER_ID, firebaseUser.getUid());
//    }
//
//    /**
//     * This sets user organization identification, primary and secondary bits.
//     */
//    public void saveCurrentOrganizationAndTeams() {
//        DatabaseReference dbUserAccountsRef = database.getReference(cRealtimeHelper.KEY_USERACCOUNTS);
//        Query userAccountQuery = dbUserAccountsRef.orderByChild("currentOrganization").equalTo(true);
//        userAccountQuery.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                cUserAccountModel userAccountModel = snapshot.getValue(cUserAccountModel.class);
//                String userAccountID = userAccountModel.getUserAccountServerID();
//                String organizationID = userAccountModel.getOrganizationServerID();
//                String primaryTeamID = userAccountModel.getTeamServerID();
//
//                saveOrganizationID(organizationID);
//                savePrimaryTeamBIT(primaryTeamID);
//                saveSecondaryTeamsBITS(userAccountID, primaryTeamID);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//    }
//
//        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
//        DatabaseReference dbUserAccountTeamsRef;
//        dbUserAccountTeamsRef = database.getReference(cRealtimeHelper.KEY_MEMBER_TEAMS);
//        dbUserAccountTeamsRef.child(firebaseUser.getUid()).addListenerForSingleValueEvent(
//                new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                        Log.d(TAG,"=========================>>>>>>>>>>> "+snapshot);
//
//                        for (DataSnapshot team : snapshot.getChildren()) {
//                            //Log.d(TAG,"=========================>>>>>>>>>>> "+team);
//                            String teamID = team.getValue(String.class);
//
//
//                            DatabaseReference dbTeamRolesRef;
//                            dbTeamRolesRef = database.getReference(cRealtimeHelper.KEY_TEAM_ROLES);
//                            dbTeamRolesRef.child(teamID).addListenerForSingleValueEvent(
//                                    new ValueEventListener() {
//                                        @Override
//                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
//                                            for (DataSnapshot role : snapshot.getChildren()) {
//                                                String roleID = role.getValue(String.class);
//
//                                                DatabaseReference dbRolePrivilegesRef;
//                                                dbRolePrivilegesRef = database.getReference(cRealtimeHelper.KEY_ROLE_PRIVILEGES);
//                                                dbRolePrivilegesRef.child(roleID).addListenerForSingleValueEvent(
//                                                        new ValueEventListener() {
//                                                            @Override
//                                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                                                                for (DataSnapshot privilege : snapshot.getChildren()) {
//                                                                    String privilegeID = privilege.getValue(String.class);
//
//                                                                    StringBuilder moduleEntityKey, entityOperationKey, entityStatusKey;
//                                                                    moduleEntityKey = new StringBuilder(cSharedPreference.KEY_MODULE_ENTITY_BITS);
//                                                                    entityOperationKey = new StringBuilder(cSharedPreference.KEY_ENTITY_OPERATION_BITS);
//                                                                    entityStatusKey = new StringBuilder(cSharedPreference.KEY_OPERATION_STATUS_BITS);
//
//                                                                    /* save permissions - for table access control */
//                                                                    DatabaseReference dbPrivilegePermissionsRef;
//                                                                    dbPrivilegePermissionsRef = database.getReference(cRealtimeHelper.KEY_PRIVILEGE_PERMISSIONS);
//                                                                    dbPrivilegePermissionsRef.child(privilegeID).addListenerForSingleValueEvent(
//                                                                            new ValueEventListener() {
//                                                                                @Override
//                                                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
//                                                                                    for (DataSnapshot module : snapshot.getChildren()) {
//                                                                                        String moduleID = module.getKey();
//
//                                                                                        int entityBITS = 0;
//                                                                                        for (DataSnapshot entity : module.getChildren()) {
//                                                                                            String entityID = entity.getKey();
//                                                                                            int operationBITS = 0;
//                                                                                            for (DataSnapshot operation : entity.getChildren()) {
//                                                                                                /* save status bits for each operation in the entity */
//                                                                                                entityStatusKey.append("-");
//                                                                                                entityStatusKey.append(operation.getKey());
//                                                                                                editor.putInt(String.valueOf(entityStatusKey), operation.getValue(Integer.class));
//                                                                                                /* calculate operation bits for the entity */
//                                                                                                operationBITS |= Integer.parseInt(operation.getKey());
//                                                                                            }
//                                                                                            /* save operation bits for the entity */
//                                                                                            entityOperationKey.append("-");
//                                                                                            entityOperationKey.append(entityID);
//                                                                                            editor.putInt(String.valueOf(entityOperationKey), operationBITS);
//                                                                                            /* calculate entity bits for the module */
//                                                                                            entityBITS |= Integer.parseInt(entity.getKey());
//                                                                                        }
//                                                                                        /* save entity bits for the module */
//                                                                                        moduleEntityKey.append("-");
//                                                                                        moduleEntityKey.append(moduleID);
//                                                                                        editor.putInt(String.valueOf(moduleEntityKey), entityBITS);
//                                                                                    }
//
//                                                                                    callback.onSavePrivilegeSucceeded(true);
//                                                                                }
//
//                                                                                @Override
//                                                                                public void onCancelled(@NonNull DatabaseError error) {
//
//                                                                                }
//                                                                            });
//
//                                                                    /* save unix permissions - for row access control
//                                                                    DatabaseReference dbPrivilegeUnixPermissionsRef;
//                                                                    dbPrivilegeUnixPermissionsRef = database.getReference(cRealtimeHelper.KEY_PRIVILEGE_UNIXPERMS);
//                                                                    dbPrivilegeUnixPermissionsRef.child(privilegeID).addListenerForSingleValueEvent(
//                                                                            new ValueEventListener() {
//                                                                                @Override
//                                                                                public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                                                                                }
//
//                                                                                @Override
//                                                                                public void onCancelled(@NonNull DatabaseError error) {
//
//                                                                                }
//                                                                            });*/
//
//                                                                }
//
//                                                            }
//
//                                                            @Override
//                                                            public void onCancelled(@NonNull DatabaseError error) {
//
//                                                            }
//                                                        });
//
//                                            }
//                                        }
//
//                                        @Override
//                                        public void onCancelled(@NonNull DatabaseError error) {
//
//                                        }
//                                    });
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });