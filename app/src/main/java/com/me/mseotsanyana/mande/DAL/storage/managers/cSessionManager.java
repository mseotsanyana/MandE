package com.me.mseotsanyana.mande.DAL.storage.managers;

/**
 * Created by mseotsanyana on 2017/09/07.
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.util.Pair;

import com.google.gson.Gson;
import com.me.mseotsanyana.mande.BLL.domain.session.cEntityDomain;
import com.me.mseotsanyana.mande.BLL.domain.session.cMenuDomain;
import com.me.mseotsanyana.mande.BLL.domain.session.cNotificationDomain;
import com.me.mseotsanyana.mande.BLL.domain.session.cOperationDomain;
import com.me.mseotsanyana.mande.BLL.domain.session.cPrivilegeDomain;
import com.me.mseotsanyana.mande.BLL.domain.session.cRoleDomain;
import com.me.mseotsanyana.mande.BLL.domain.session.cStatusDomain;
import com.me.mseotsanyana.mande.BLL.domain.session.cUserDomain;
import com.me.mseotsanyana.mande.BLL.interactors.session.cMenuHandler;
import com.me.mseotsanyana.mande.BLL.interactors.session.cNotificationHandler;
import com.me.mseotsanyana.mande.BLL.interactors.session.cPrivilegeHandler;
import com.me.mseotsanyana.mande.BLL.interactors.session.cRoleHandler;
import com.me.mseotsanyana.mande.BLL.interactors.session.cUserHandler;
import com.me.mseotsanyana.mande.UTIL.cBitwisePermission;
import com.me.mseotsanyana.mande.UTIL.cConstant;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class cSessionManager extends cBitwisePermission implements Parcelable {
    private static String TAG = cSessionManager.class.getSimpleName();

    private SharedPreferences settings;
    private SharedPreferences.Editor editor;

    private cUserHandler userHandler;
    private cRoleHandler roleHandler;
    private cMenuHandler menuHandler;
    private cPrivilegeHandler privilegeHandler;
    private cNotificationHandler notificationHandler;

    private cUserDomain userDomain;
    private Set<cRoleDomain> roleDomainSet;
    private Set<cMenuDomain> menuDomainSet;
    private Set<cPrivilegeDomain> privilegeDomainSet;
    private Set<cNotificationDomain> notificationDomainSet;

    private int userID, orgID, primaryRoleBIT, secondaryRoleBITS,
            entityBITS, operationBITS, statusBITS;

    private HashMap<cEntityDomain, Pair<Integer, Integer>> privilegeDomainBITS;

    private Gson gson;

    public cSessionManager(Context context) {
        settings = context.getSharedPreferences(cConstant.KEY_USER_PREFS, Context.MODE_PRIVATE);
        editor = settings.edit();

        gson = new Gson();

        //roleDomainSet = new HashSet<>();
        menuDomainSet = new HashSet<>();
        privilegeDomainSet = new HashSet<>();
        notificationDomainSet  = new HashSet<>();

        userHandler = new cUserHandler(context, this);
        roleHandler = new cRoleHandler(context, this);
        menuHandler = new cMenuHandler(context, this);
        privilegeHandler = new cPrivilegeHandler(context, this);
        notificationHandler = new cNotificationHandler(context, this);
    }

    protected cSessionManager(Parcel in) {
        userDomain = in.readParcelable(cUserDomain.class.getClassLoader());
        userID = in.readInt();
        orgID = in.readInt();
        primaryRoleBIT = in.readInt();
        secondaryRoleBITS = in.readInt();
        entityBITS = in.readInt();
        operationBITS = in.readInt();
        statusBITS = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(userDomain, flags);
        dest.writeInt(userID);
        dest.writeInt(orgID);
        dest.writeInt(primaryRoleBIT);
        dest.writeInt(secondaryRoleBITS);
        dest.writeInt(entityBITS);
        dest.writeInt(operationBITS);
        dest.writeInt(statusBITS);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<cSessionManager> CREATOR = new Creator<cSessionManager>() {
        @Override
        public cSessionManager createFromParcel(Parcel in) {
            return new cSessionManager(in);
        }

        @Override
        public cSessionManager[] newArray(int size) {
            return new cSessionManager[size];
        }
    };

    /**
     * This method sets the session object for the current logged in user.
     * This is called from inside the SmartLoginActivity to save the
     * current logged in user to the shared preferences.
     */
    public boolean setUserSession(Context context, cUserDomain userDomain) {
        if (userDomain != null) {
            try {

                /* ########## PRELIMINARY INFORMATION FOR THE LOGGEDIN USER ########## */

                /* 1. set a USER domain of the loggedIn user */
                setUserDomain(userDomain);
                Log.d(TAG, " USER PROFILE: " + gson.toJson(userDomain));

                /* 2. set a user ID for the loggedIn user */
                setUserID(userDomain.getUserID());

                /* 3. set a user organization ID for the loggedIn user */
                setOrgID(userDomain.getOrgID());

                /* 4. set primary role/group BIT of the loggedIn user */
                setPrimaryRoleBIT(computePrimaryRoleBIT(userDomain.getRoleDomainSet()));

                /* 5. set secondary role/group BITS of the loggedIn user */
                setSecondaryRoleBITS(computeSecondaryRoleBITS(userDomain.getRoleDomainSet()));

                Log.d(TAG, " USER ORGANIZATION ID SET:" +
                        " OWNER ID = " + getUserID() +
                        "; OWNER ORG. ID = " + getOrgID() +
                        "; PRIMARY ROLE BIT = " + getPrimaryRoleBIT() +
                        "; SECONDARY ROLE BITS = " + getSecondaryRoleBITS());

                /* 6. set role/group domain SET of the loggedIn user */
                setRoleDomainSet(roleHandler.getRoleDomainSet(
                        getUserID(), getOrgID(), getPrimaryRoleBIT(), getSecondaryRoleBITS()));

                Log.d(TAG, " ROLE SET: " + gson.toJson(roleDomainSet));

                /* 7. set menu domain SET for the loggedIn user */
                setMenuDomainSet(menuHandler.getMenuDomainSet(
                        getUserID(), getOrgID(), getPrimaryRoleBIT(), getSecondaryRoleBITS()));

                Log.d(TAG, " MENU SET: " + gson.toJson(menuDomainSet));

                /* 8. set privilege domain SET for the loggedIn user */
                setPrivilegeDomainSet(privilegeHandler.getPrivilegeDomainSet(
                        userID, orgID, primaryRoleBIT, secondaryRoleBITS));

                /* 9. set privilege domain BITS for the loggedIn user */
                setPrivilegeDomainBITS(computePrivilegeDomainBITS(getPrivilegeDomainSet()));


                /* ########## SETTING OF PREFERENCES FOR THE LOGGED-IN USER ########## */

                /* 1. user profile details */
                saveUserProfile(getUserDomain());

                /* 2. user own identification */
                saveUserID(userID);

                /* 3. user own organization */
                saveOrgID(orgID);

                /* 4. user primary role - the organization he/she belongs to */
                savePrimaryRole(primaryRoleBIT);

                /* 5. user secondary roles - other organization he/she belongs to through roles */
                saveSecondaryRoles(secondaryRoleBITS);

                /* 6. user privileges - these are entity bits with their operation and status bits */
                savePrivilegeDomainBITS(privilegeDomainBITS);

                // once the user session has been set, the login is then set and settings saved.
                //loginUser();
                //deleteSettings();
                //commitSettings();

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

    /* ########## UTILITY FUNCTIONS FOR SHARED PREFERENCE SETTINGS ########## */

    /**
     * This returns the primary role/group of the loggedIn user
     *
     * @param roleDomainSet
     * @return
     */
    public int computePrimaryRoleBIT(Set<cRoleDomain> roleDomainSet) {
        int pRoleBIT = 0;
        for (cRoleDomain domain : roleDomainSet) {
            if (getUserDomain().getOrganizationID() == domain.getOrganizationID()) {
                pRoleBIT = domain.getRoleID();
                break;
            }
        }
        return pRoleBIT;
    }

    /**
     * This return the secondary role/group of the loggedIn user
     *
     * @param roleDomainSet
     * @return
     */
    public int computeSecondaryRoleBITS(Set<cRoleDomain> roleDomainSet) {
        int sRoleBITS = 0;
        for (cRoleDomain domain : roleDomainSet) {
            if (getUserDomain().getOrganizationID() != domain.getOrganizationID()) {
                sRoleBITS |= domain.getRoleID();
            }
        }
        return sRoleBITS;
    }

    /**
     * This returns the privileges of the loggedIn user
     *
     * @param privilegeDomainSet
     * @return
     */
    public HashMap<cEntityDomain, Pair<Integer, Integer>>
    computePrivilegeDomainBITS(Set<cPrivilegeDomain> privilegeDomainSet) {

        HashMap<cEntityDomain, Pair<Integer, Integer>> privilegeMapBITS = new HashMap();
        for (int i = 0; i < entity_types.length; i++) {
            for (cPrivilegeDomain privilegeDomain : privilegeDomainSet) {

                setEntityBITS(0);
                cEntityDomain entityDomain = null;
                for (Map.Entry<cEntityDomain, Set<cOperationDomain>>
                        entityEntry : privilegeDomain.getPermDomainMap().entrySet()) {

                    entityDomain = entityEntry.getKey();
                    Set<cOperationDomain> operationDomainSet = entityEntry.getValue();
                    if (entity_types[i] == entityDomain.getTypeID()) {
                        setEntityBITS(getEntityBITS() | entityDomain.getEntityID());

                        setOperationBITS(0);
                        for (cOperationDomain operationDomain : operationDomainSet) {
                            setOperationBITS(getOperationBITS() | operationDomain.getOperationID());
                        }
                    }

                    setStatusBITS(0);
                    for (cStatusDomain statusDomain : privilegeDomain.getStatusDomainSet()) {
                        setStatusBITS(getStatusBITS() | statusDomain.getStatusID());
                    }

                    if (getEntityBITS() != 0 && entityDomain != null) {
                        privilegeMapBITS.put(entityDomain,
                                new Pair<Integer, Integer>(getOperationBITS(), getStatusBITS()));
                    }
                }
            }
        }

        return privilegeMapBITS;
    }

    /* ########## SET and GET FUNCTIONS FOR SHARED PREFERENCE SETTINGS ########## */

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


    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getOrgID() {
        return orgID;
    }

    public void setOrgID(int orgID) {
        this.orgID = orgID;
    }

    public int getPrimaryRoleBIT() {
        return primaryRoleBIT;
    }

    public void setPrimaryRoleBIT(int primaryRoleBIT) {
        this.primaryRoleBIT = primaryRoleBIT;
    }

    public int getSecondaryRoleBITS() {
        return secondaryRoleBITS;
    }

    public void setSecondaryRoleBITS(int secondaryRoleBITS) {
        this.secondaryRoleBITS = secondaryRoleBITS;
    }

    public Set<cMenuDomain> getMenuDomainSet() {
        return menuDomainSet;
    }

    public void setMenuDomainSet(Set<cMenuDomain> menuDomainSet) {
        this.menuDomainSet = menuDomainSet;
    }

    public Set<cRoleDomain> getRoleDomainSet() {
        return roleDomainSet;
    }

    public void setRoleDomainSet(Set<cRoleDomain> roleDomainSet) {
        this.roleDomainSet = roleDomainSet;
    }

    public int getEntityBITS() {
        return entityBITS;
    }

    public void setEntityBITS(int entityBITS) {
        this.entityBITS = entityBITS;
    }

    public int getOperationBITS() {
        return operationBITS;
    }

    public void setOperationBITS(int operationBITS) {
        this.operationBITS = operationBITS;
    }

    public int getStatusBITS() {
        return statusBITS;
    }

    public void setStatusBITS(int statusBITS) {
        this.statusBITS = statusBITS;
    }

    public HashMap<cEntityDomain, Pair<Integer, Integer>> getPrivilegeDomainBITS() {
        return privilegeDomainBITS;
    }

    public void setPrivilegeDomainBITS(HashMap<cEntityDomain, Pair<Integer, Integer>> privilegeDomainBITS) {
        this.privilegeDomainBITS = privilegeDomainBITS;
    }

    public Set<cPrivilegeDomain> getPrivilegeDomainSet() {
        return privilegeDomainSet;
    }

    public void setPrivilegeDomainSet(Set<cPrivilegeDomain> privilegeDomainSet) {
        this.privilegeDomainSet = privilegeDomainSet;
    }

    public Set<cNotificationDomain> getNotificationDomainSet() {
        return notificationDomainSet;
    }

    public void setNotificationDomainSet(Set<cNotificationDomain> notificationDomainSet) {
        this.notificationDomainSet = notificationDomainSet;
    }

    /* ########## FUNCTIONS FOR SAVING AND LOADING CURRENT SETTINGS /* ########## */

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
    public void saveOrgID(int organizationID) {
        editor.putInt(cConstant.KEY_ORGANIZATION_ID, organizationID);
    }

    /**
     * This returns organization ID of the loggedIn user. If no organization,
     * it returns -1.
     *
     * @return organizationID
     */
    public int loadOrgID() {
        return settings.getInt(cConstant.KEY_ORGANIZATION_ID, -1);
    }

    /**
     * This sets primary role of the loggedIn user and saves it.
     *
     * @param primaryRole
     */
    public void savePrimaryRole(int primaryRole) {
        StringBuilder key = new StringBuilder(cConstant.KEY_PRIMARY_ROLE_BIT);
        editor.putInt(String.valueOf(key), primaryRole);
    }

    /**
     * This returns a primary role of the loggedIn user. If no primary role,
     * it returns -1.
     *
     * @return
     */
    public int loadPrimaryRole() {
        StringBuilder key = new StringBuilder(cConstant.KEY_PRIMARY_ROLE_BIT);
        return settings.getInt(String.valueOf(key), -1);
    }

    /**
     * This sets secondary roles of the loggedIn user and saves it.
     *
     * @param secondaryRoles
     */
    public void saveSecondaryRoles(int secondaryRoles) {
        StringBuilder key = new StringBuilder(cConstant.KEY_SECONDARY_ROLE_BITS);
        editor.putInt(String.valueOf(key), secondaryRoles);
    }

    /**
     * This returns a secondary roles of the loggedIn user. If no secondary roles,
     * it returns -1.
     *
     * @return
     */
    public int loadSecondaryRoles() {
        StringBuilder key = new StringBuilder(cConstant.KEY_SECONDARY_ROLE_BITS);
        return settings.getInt(String.valueOf(key), -1);
    }

    /**
     * This saves operation BITS and status BITS that belong to a specified entity with
     * the specified entityID and entityTypeID
     *
     * @param privilegeMapBITS
     */
    public void savePrivilegeDomainBITS(HashMap<cEntityDomain, Pair<Integer, Integer>> privilegeMapBITS) {
        for (Map.Entry<cEntityDomain, Pair<Integer, Integer>> opEntry : privilegeMapBITS.entrySet()) {
            cEntityDomain entityDomain = opEntry.getKey();
            Pair pair = opEntry.getValue();

            /*build and save entity operation preferences */
            StringBuilder operationKey = new StringBuilder(cConstant.KEY_ENTITY_OPERATION_BITS);
            operationKey.append("-");
            operationKey.append(entityDomain.getEntityID());
            operationKey.append("-");
            operationKey.append(entityDomain.getTypeID());
            editor.putInt(String.valueOf(operationKey), (Integer) pair.first);

            /* build and save operation status preferences*/
            StringBuilder statusKey = new StringBuilder(cConstant.KEY_OPERATION_STATUS_BITS);
            statusKey.append("-");
            statusKey.append(entityDomain.getEntityID());
            statusKey.append("-");
            statusKey.append(entityDomain.getTypeID());
            editor.putInt(String.valueOf(statusKey), (Integer) pair.second);

            Log.d(TAG, " PRIVILEGE BITS: " +
                    entityDomain.getEntityID() + "-" +
                    entityDomain.getTypeID() + " = (" + pair.first + ", " + pair.second + ")");
        }
    }

    /**
     * This returns operation BITS that belong to an entity with the specified
     * entityID and entityTypeID
     *
     * @param entityID
     * @param typeID
     * @return
     */
    public int loadOperationBITS(int entityID, int typeID) {
        StringBuilder key = new StringBuilder(cConstant.KEY_ENTITY_OPERATION_BITS);
        key.append("-");
        key.append(entityID);
        key.append("-");
        key.append(typeID);
        return settings.getInt(String.valueOf(key), 0);
    }

    /**
     * This returns status BITS that apply to operations that belong to an entity
     * with the specified entityID and entityTypeID
     *
     * @param entityID
     * @param typeID
     * @return
     */
    public int loadStatusBITS(int entityID, int typeID) {
        StringBuilder key = new StringBuilder(cConstant.KEY_OPERATION_STATUS_BITS);
        key.append("-");
        key.append(entityID);
        key.append("-");
        key.append(typeID);

        return settings.getInt(String.valueOf(key), 0);
    }

    /* ########## FUNCTIONS FOR CRUD AND LOG IN/OUT OPERATIONS ########## */

    /**
     * This saves the settings of the loggedIn user. Should be called
     * after every preference setting function.
     */
    public void commitSettings() {
        editor.commit();
    }

    /**
     * This takes a key parameter and removes the corresponding preference.
     *
     * @param key
     */
    public void removeSetting(String key) {
        editor.remove(key);
    }

    /**
     * This takes the key and value parameters of an integer setting and
     * updates the corresponding preference.
     *
     * @param key
     * @param value
     */
    public void updateIntSetting(String key, int value) {
        editor.putInt(key, value);

    }

    /**
     * This takes the key and value parameters of an string setting and
     * updates the corresponding preference.
     *
     * @param key
     * @param value
     */
    public void updateStringSetting(String key, String value) {
        editor.putString(key, value);
    }

    /**
     * This takes the key and value parameters of an boolean setting and
     * updates the corresponding preference.
     *
     * @param key
     * @param value
     */
    public void updateBooleanSetting(String key, Boolean value) {
        editor.putBoolean(key, value);
    }

    /**
     * This deletes all the saved settings of the loggedIn user.
     */
    public void deleteSettings() {
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