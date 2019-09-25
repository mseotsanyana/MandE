package com.me.mseotsanyana.mande.BRBAC.BLL;

/**
 * Created by mseotsanyana on 2017/09/07.
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.me.mseotsanyana.mande.COM.cEntityBITS;
import com.me.mseotsanyana.mande.COM.cOperationBITS;
import com.me.mseotsanyana.mande.COM.cStatusBITS;
import com.me.mseotsanyana.mande.Util.cBitwisePermission;
import com.me.mseotsanyana.mande.Util.cConstant;

import java.util.ArrayList;
import java.util.List;

public class cSessionManager extends cBitwisePermission {
    private static String TAG = cSessionManager.class.getSimpleName();

    private SharedPreferences settings;
    private SharedPreferences.Editor editor;
    private Gson gson;

    private cUserDomain userDomain;
    private ArrayList<Integer> userOrganizationIDs;
    private ArrayList<cRoleDomain> userRoles;
    private ArrayList<cPrivilegeDomain> rolePrivileges;

    private List<cEntityBITS> entitiesBITS;
    private List<cOperationBITS> operationsBITS;
    private List<cStatusBITS> statusesBITS;

    private cUserRoleHandler userRoleHandler;
    private cPrivilegeHandler privilegeHandler;
    private cPermissionHandler permissionHandler;

    public cSessionManager(Context context) {
        settings = context.getSharedPreferences(cConstant.KEY_USER_PREFS, Context.MODE_PRIVATE);
        editor   = settings.edit();
        gson     = new Gson();

        userRoleHandler      = new cUserRoleHandler(context, this);
        privilegeHandler     = new cPrivilegeHandler(context, this);
        permissionHandler    = new cPermissionHandler(context, this);
    }

    /**
     * This method sets the session object for the current logged in user.
     * This is called from inside the SmartLoginActivity to save the
     * current logged in user to the shared preferences.
     */
    public boolean setUserSession(Context context, cUserDomain userDomain) {
        if (userDomain != null) {
            try {

                /* ### preliminary information for the loggedIn user ### */

                /* 1. user domain of the loggedIn user */
                setUserDomain(userDomain);
                int userID = getUserDomain().getUserID();
                Log.d(TAG + " USER ", gson.toJson(getUserDomain()));

                /* 2. list of organization IDs for the loggedIn user */
                setUserOrganizations(userRoleHandler.getOrganizationIDsByUserID(userID));
                Log.d(TAG + " USER ORGANIZATIONS ", gson.toJson(getUserOrganizationIDs()));

                /* 3. list of roles for the loggedIn user */
                setUserRoles(userRoleHandler.getRolesByUserID(userID));
                Log.d(TAG + " USER ROLES ", gson.toJson(getUserRoles()));

                /* 4. list of privileges for the loggedIn user */
                setRolePrivileges(getMergedPrivileges(getUserRoles()));
                Log.d(TAG + " ROLE PRIVILEGES ", gson.toJson(getRolePrivileges()));


                /* ### setting of preferences for the loggedIn user ### */

                /* 1. user profile details */
                saveUserProfile(getUserDomain());

                /* 2. user own identification */
                saveUserID(userID);

                /* 3. user own organization */
                saveOrganizationID(getUserDomain().getOrganizationID());

                /* ### setting of preferences for the loggedIn user by organizations ### */
                int primaryRole = 0, secondaryRoles = 0;
                ArrayList<Integer> roleIDs;
                ArrayList<Integer> orgs = userRoleHandler.getOrganizationIDsByUserID(userID);
                for (int i = 0; i < orgs.size(); i++){
                    roleIDs = userRoleHandler.getRoleIDsByOrganizationID(orgs.get(i));
                    if (roleIDs.remove(new Integer(1))){
                        primaryRole = 1;
                    }

                    for (int j = 0; j < roleIDs.size(); j++) {
                        secondaryRoles |= roleIDs.get(j);
                    }

                    /*
                    Log.d(TAG," ORG = "+ orgs.get(i)+", " +
                            "PRIMARY ROLE = "+ primaryRole+", " +
                            "SECONDARY ROLES = "+ secondaryRoles);
                    */

                    /* 1. user own organizations */
                    saveOrganizationIDS(orgs.get(i));

                    /* 2. user primary role - the organization he/she belongs to */
                    savePrimaryRole(userID, orgs.get(i), primaryRole);

                    /* 3. user secondary roles - other organization he/she belongs to
                     *    through roles */
                    saveSecondaryRoles(userID, orgs.get(i), computeSecondaryRoles(getUserDomain()));

                    /* 4. entity bits of entities the loggedIn user has access to, grouped by
                     *    entity types */
                    setEntitiesBITS(permissionHandler.getEntitiesBIT(getRolePrivileges()));
                    saveEntityBITS(userID, orgs.get(i), getEntitiesBITS());

                    /* 5. operation bits by entityID and entity_typeID */
                    setOperationsBITS(permissionHandler.getOperationsBIT(getRolePrivileges()));
                    saveOperationBITS(userID, orgs.get(i), getOperationsBITS());

                    /* 5. status bits by entityID, typeID and operationID */
                    setStatusesBITS(permissionHandler.getStatusesBIT(getRolePrivileges()));
                    saveStatusBITS(userID, orgs.get(i), getStatusesBITS());

                    // reset
                    primaryRole = 0;
                    secondaryRoles = 0;
                }

                // once the user session has been set, the login is then set and settings saved.
                loginUser();
                //deleteSettings();
                commitSettings();

                return true;

            } catch (Exception e) {
                Log.e("Session Error", e.getMessage());
                return false;
            }
        } else {
            Log.e("Session Error", "User is null");
            return false;
        }
    }

    //************* UTILITY FUNCTIONS FOR SHARED PREFERENCE SETTINGS *************

    /**
     * This returns the user object of the loggedIn user
     *
     * @return userDomain
     */
    public cUserDomain getUserDomain() {
        return userDomain;
    }

    /**
     * This sets the user domain object for the loggedIn user.
     *
     * @param userDomain
     */
    public void setUserDomain(cUserDomain userDomain) {
        this.userDomain = userDomain;
    }

    /**
     * This returns the user roles of the loggedIn user.
     *
     * @return userRoles
     */
    public ArrayList<Integer> getUserOrganizationIDs() {
        return this.userOrganizationIDs;
    }

    /**
     * This returns the user roles of the loggedIn user.
     *
     * @return userRoles
     */
    public ArrayList<cRoleDomain> getUserRoles() {
        return userRoles;
    }

    /**
     * This sets the roles of the loggedIn user
     *
     * @param organizationIDs
     */
    public void setUserOrganizations(ArrayList<Integer> organizationIDs) {
        this.userOrganizationIDs = organizationIDs;
    }

    /**
     * This sets the roles of the loggedIn user
     *
     * @param userRoles
     */
    public void setUserRoles(ArrayList<cRoleDomain> userRoles) {
        this.userRoles = userRoles;
    }

    /**
     * This returns the user privileges of the loggedIn user.
     *
     * @return rolePrivileges
     */
    public ArrayList<cPrivilegeDomain> getRolePrivileges() {
        return rolePrivileges;
    }

    /**
     * This sets the privileges of the loggedIn user
     *
     * @param rolePrivileges
     */
    public void setRolePrivileges(ArrayList<cPrivilegeDomain> rolePrivileges) {
        this.rolePrivileges = rolePrivileges;
    }

    public List<cEntityBITS> getEntitiesBITS() {
        return entitiesBITS;
    }

    public void setEntitiesBITS(List<cEntityBITS> entitiesBITS) {
        this.entitiesBITS = entitiesBITS;
    }

    public List<cOperationBITS> getOperationsBITS() {
        return operationsBITS;
    }

    public void setOperationsBITS(List<cOperationBITS> operationsBITS) {
        this.operationsBITS = operationsBITS;
    }

    public List<cStatusBITS> getStatusesBITS() {
        return statusesBITS;
    }

    public void setStatusesBITS(List<cStatusBITS> statusesBITS) {
        this.statusesBITS = statusesBITS;
    }

    /**
     * This reads from the shared preferences and builds a cUserDomain object and returns it.
     * If no user is logged in it returns null.
     *
     * @return userDomain
     */
    public cUserDomain loadCurrentUser() {
        String currentUser = settings.getString(cConstant.KEY_USER_PROFILE, null);
        cUserDomain userDomain = null;
        try {
            Gson gson = new Gson();
            userDomain = gson.fromJson(currentUser, cUserDomain.class);
        } catch (Exception e) {
            Log.e(TAG + " getCurrentUser ", e.getMessage());
        }
        return userDomain;
    }

    /**
     * This takes a list of user roles as a parameter and returns the merged
     * privileges.
     *
     * @param userRoles
     * @return mergedPrivileges
     */
    public ArrayList<cPrivilegeDomain> getMergedPrivileges(ArrayList<cRoleDomain> userRoles) {
        ArrayList<cPrivilegeDomain> mergedPrivileges = new ArrayList<cPrivilegeDomain>();

        for (int i = 0; i < userRoles.size(); i++) {
            ArrayList<cPrivilegeDomain> privileges = privilegeHandler.getPrivilegesByIDs(
                    userRoles.get(i).getOrganizationID(), userRoles.get(i).getRoleID());
            mergedPrivileges.addAll(privileges);
        }
        return mergedPrivileges;
    }

    /**
     * This takes user domain as parameter to compute and return secondary
     * roles of the loggedIn user.
     *
     * @param userDomain
     * @return secondaryRoles
     */
    public int computeSecondaryRoles(cUserDomain userDomain){
        int secondaryRoles = 0;
        ArrayList<cRoleDomain> roleDomains = userRoleHandler.getRolesByUserID(
                userDomain.getUserID());

        for (int i = 0; i < roleDomains.size(); i++) {
            secondaryRoles |= roleDomains.get(i).getOrganizationID();
        }

        // remove primary role from secondary
        secondaryRoles &= ~userDomain.getOrganizationID();

        return secondaryRoles;
    }

    //************* FUNCTIONS FOR MANIPULATING CURRENT SETTINGS *************

    /**
     * This sets the user domain json string of the loggedIn user and saves it.
     *
     * @param userDomain
     */
    public void saveUserProfile(cUserDomain userDomain) {
        String userProfile = gson.toJson(userDomain);
        editor.putString(cConstant.KEY_USER_PROFILE, userProfile);
    }

    /**
     * This returns the json string of the loggedIn user domain. If no user is
     * loggedIn it returns null
     *
     * @return json string
     */
    private String loadUserProfile() {
        return settings.getString(cConstant.KEY_USER_PROFILE, null);
    }

    /**
     * This sets user ID (which is used as owner ID) for records and saves it.
     *
     * @param userID
     */
    public void saveUserID(int userID) {
        editor.putInt(cConstant.KEY_USER_ID, userID);
    }

    /**
     * This returns user ID of the loggedIn user. If no user, it returns -1.
     *
     * @return userID
     */
    public int loadUserID() {
        return settings.getInt(cConstant.KEY_USER_ID, -1);
    }

    /**
     * This sets user ID (which is used as owner ID) for records and saves it.
     *
     * @param organizationID
     */
    public void saveOrganizationID(int organizationID) {
        editor.putInt(cConstant.KEY_ORGANIZATION_ID, organizationID);
    }

    /**
     * This returns organization ID of the loggedIn user. If no organization,
     * it returns -1.
     *
     * @return organizationID
     */
    public int loadOrganizationID() {
        return settings.getInt(cConstant.KEY_ORGANIZATION_ID, -1);
    }

    /**
     * This sets organization ID (which is used as owner organization ID)
     * for records and saves it.
     *
     * @param organizationID
     */
    public void saveOrganizationIDS(int organizationID) {
        StringBuilder key = new StringBuilder(cConstant.KEY_ORGANIZATIONS_ID);
        key.append("-");
        key.append(organizationID);
        editor.putInt(String.valueOf(key), organizationID);
    }

    /**
     * This returns organization IDs of the loggedIn user. If no organization,
     * it returns -1.
     *
     * @return organizationID
     */
    public int loadOrganizationIDS(int organizationID) {
        StringBuilder key = new StringBuilder(cConstant.KEY_ORGANIZATIONS_ID);
        key.append("-");
        key.append(organizationID);
        return settings.getInt(String.valueOf(key), -1);
    }

    /**
     * This sets primary role of the loggedIn user and saves it.
     *
     * @param userID
     * @param orgID
     * @param primaryRole
     */
    public void savePrimaryRole(int userID, int orgID, int primaryRole) {
        StringBuilder key = new StringBuilder(cConstant.KEY_PRIMARY_ROLE_BIT);
        key.append("-");
        key.append(userID);
        key.append("-");
        key.append(orgID);
        editor.putInt(String.valueOf(key), primaryRole);
    }

    /**
     * This returns a primary role of the loggedIn user. If no primary role,
     * it returns -1.
     *
     * @param userID
     * @param orgID
     * @return
     */
    public int loadPrimaryRole(int userID, int orgID) {
        StringBuilder key = new StringBuilder(cConstant.KEY_PRIMARY_ROLE_BIT);
        key.append("-");
        key.append(userID);
        key.append("-");
        key.append(orgID);
        return settings.getInt(String.valueOf(key), -1);
    }

    /**
     * This sets secondary roles of the loggedIn user and saves it.
     *
     * @param userID
     * @param orgID
     * @param secondaryRoles
     */
    public void saveSecondaryRoles(int userID, int orgID, int secondaryRoles) {
        StringBuilder key = new StringBuilder(cConstant.KEY_SECONDARY_ROLE_BITS);
        key.append("-");
        key.append(userID);
        key.append("-");
        key.append(orgID);
        editor.putInt(String.valueOf(key), secondaryRoles);
    }

    /**
     * This returns a secondary roles of the loggedIn user. If no secondary roles,
     * it returns -1.
     *
     * @param userID
     * @param orgID
     * @return
     */
    public int loadSecondaryRoles(int userID, int orgID) {
        StringBuilder key = new StringBuilder(cConstant.KEY_SECONDARY_ROLE_BITS);
        key.append("-");
        key.append(userID);
        key.append("-");
        key.append(orgID);
        return settings.getInt(String.valueOf(key), -1);
    }

    /**
     * This takes a list of entityBITS grouped by typeID and saves them. This contains
     * list of entities the loggedIn user has access to.
     *
     * @param userID
     * @param orgID
     * @param entityBITS
     */
    public void saveEntityBITS(int userID, int orgID, List<cEntityBITS> entityBITS) {
        for (cEntityBITS entityBIT : entityBITS) {
            StringBuilder key = new StringBuilder(cConstant.KEY_ENTITY_TYPE_BITS);
            key.append("-");
            key.append(userID);
            key.append("-");
            key.append(orgID);
            key.append("-");
            key.append(entityBIT.getTypeID());
            editor.putInt(String.valueOf(key), entityBIT.getEntityBITS());
        }
    }

    /**
     * This takes userID, orgID and typeID of the entity and returns the
     * bits which represents the entities the user has access to.
     * If no userID, orgID and typeID, returns 0.
     *
     * @param userID
     * @param orgID
     * @param typeID
     * @return entity bits
     */
    public int loadEntityBITS(int userID, int orgID, int typeID) {
        StringBuilder key = new StringBuilder(cConstant.KEY_ENTITY_TYPE_BITS);
        key.append("-");
        key.append(userID);
        key.append("-");
        key.append(orgID);
        key.append("-");
        key.append(typeID);
        return settings.getInt(String.valueOf(key), 0);
    }

    /**
     * This takes a list of operationBITS grouped by userID, orgID, entityID, and
     * typeID and saves them. This contains list of operations the loggedIn user
     * has access to on entities.
     *
     * @param userID
     * @param orgID
     * @param operationBITS
     */
    public void saveOperationBITS(int userID, int orgID, List<cOperationBITS> operationBITS) {
        for (cOperationBITS operationBIT : operationBITS) {
            StringBuilder key = new StringBuilder(cConstant.KEY_ENTITY_OPERATION_BITS);
            key.append("-");
            key.append(userID);
            key.append("-");
            key.append(orgID);
            key.append("-");
            key.append(operationBIT.getEntityID());
            key.append("-");
            key.append(operationBIT.getTypeID());
            editor.putInt(String.valueOf(key), operationBIT.getOperationBITS());
        }
    }

    /**
     * This takes userID, orgID, entityID and typeID and returns the bits.
     * If no entityID and typeID, returns 0.
     *
     * @param userID
     * @param orgID
     * @param entityID
     * @param typeID
     * @return operation bits
     */
    public int loadOperationBITS(int userID, int orgID, int entityID, int typeID) {
        StringBuilder key = new StringBuilder(cConstant.KEY_ENTITY_OPERATION_BITS);
        key.append("-");
        key.append(userID);
        key.append("-");
        key.append(orgID);
        key.append("-");
        key.append(entityID);
        key.append("-");
        key.append(typeID);
        return settings.getInt(String.valueOf(key), 0);
    }

    /**
     * This takes a list of statusBITS grouped by userID, orgID, entityID, typeID,
     * operationID and saves them. This contains list of statuses the loggedIn user
     * has access to on operations.
     *
     * @param userID
     * @param orgID
     * @param statusBITS
     */
    public void saveStatusBITS(int userID, int orgID, List<cStatusBITS> statusBITS) {
        for (cStatusBITS statusBIT : statusBITS) {
            StringBuilder key = new StringBuilder(cConstant.KEY_OPERATION_STATUS_BITS);
            key.append("-");
            key.append(userID);
            key.append("-");
            key.append(orgID);
            key.append("-");
            key.append(statusBIT.getEntityID());
            key.append("-");
            key.append(statusBIT.getTypeID());
            key.append("-");
            key.append(statusBIT.getOperationID());
            editor.putInt(String.valueOf(key), statusBIT.getStatusBITS());
        }
    }

    /**
     * This takes userID, orgID, entityID, typeID and operationID and returns the bits.
     * If no entityID. typeID and operationID, returns 0.
     *
     * @param userID
     * @param orgID
     * @param entityID
     * @param typeID
     * @param compositeOpID
     * @return status bits
     */

    public int loadStatusBITS(int userID, int orgID, int entityID, int typeID, int compositeOpID) {
        int opBITS = 0;
        for(int i = 0; i < permissions.length; i++){
            // get the specific operation
            int opBIT = permissions[i] & compositeOpID;
            // get the prefix of the key
            StringBuilder key = new StringBuilder(cConstant.KEY_OPERATION_STATUS_BITS);
            key.append("-");
            key.append(userID);
            key.append("-");
            key.append(orgID);
            key.append("-");
            key.append(entityID);
            key.append("-");
            key.append(typeID);
            key.append("-");
            key.append(opBIT);
            opBITS |= settings.getInt(String.valueOf(key), 0);
        }
        return opBITS;
    }


    //************* FUNCTIONS FOR CRUD AND LOG IN/OUT OPERATIONS *************

    /**
     * This saves the settings of the loggedIn user. Should be called
     * after every preference setting function.
     */
    public void commitSettings(){
        editor.commit();
    }

    /**
     * This takes a key parameter and removes the corresponding preference.
     *
     * @param key
     */
    public void removeSetting(String key){
        editor.remove(key);
    }

    /**
     * This takes the key and value parameters of an integer setting and
     * updates the corresponding preference.
     *
     * @param key
     * @param value
     */
    public void updateIntSetting(String key, int value){
        editor.putInt(key, value);

    }

    /**
     * This takes the key and value parameters of an string setting and
     * updates the corresponding preference.
     *
     * @param key
     * @param value
     */
    public void updateStringSetting(String key, String value){
        editor.putString(key, value);
    }

    /**
     * This takes the key and value parameters of an boolean setting and
     * updates the corresponding preference.
     *
     * @param key
     * @param value
     */
    public void updateBooleanSetting(String key, Boolean value){
        editor.putBoolean(key, value);
    }

    /**
     * This deletes all the saved settings of the loggedIn user.
     */
    public void deleteSettings(){
        editor.clear();
    }

    /**
     * This sets the loggedIn variable to true
     */
    public void loginUser() {
        editor.putBoolean(cConstant.KEY_IS_LOGGEDIN, true);
    }

    /**
     * This sets the loggedIn variable to true
     */
    public void logoutUser() {
        editor.putBoolean(cConstant.KEY_IS_LOGGEDIN, false);
    }

    /**
     * This method can be called to check whether there is any logged in user.
     * If no user is loggedIn, it returns false otherwise true
     */
    public boolean isLoggedIn() {
        return settings.getBoolean(cConstant.KEY_IS_LOGGEDIN, false);
    }

}