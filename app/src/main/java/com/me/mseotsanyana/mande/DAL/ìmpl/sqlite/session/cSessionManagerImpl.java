package com.me.mseotsanyana.mande.DAL.ìmpl.sqlite.session;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.me.mseotsanyana.mande.BLL.repository.session.iSessionManagerRepository;
import com.me.mseotsanyana.mande.BLL.model.session.cOrganizationModel;
import com.me.mseotsanyana.mande.BLL.model.session.cPermissionModel;
import com.me.mseotsanyana.mande.BLL.model.session.cRoleModel;
import com.me.mseotsanyana.mande.BLL.model.session.cStatusModel;
import com.me.mseotsanyana.mande.BLL.model.session.cStatusSetModel;
import com.me.mseotsanyana.mande.BLL.model.session.cUserModel;
import com.me.mseotsanyana.mande.DAL.storage.preference.cSharedPreference;
import com.me.mseotsanyana.mande.UTIL.cConstant;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Set;

import static com.me.mseotsanyana.mande.DAL.storage.preference.cBitwise.types;

public class cSessionManagerImpl implements iSessionManagerRepository {
    private static SimpleDateFormat sdf = cConstant.FORMAT_DATE;
    private static String TAG = cSessionManagerImpl.class.getSimpleName();

    private SharedPreferences preferences;
    private cSharedPreference sharedPreference;
    private SharedPreferences.Editor editor;
    private Gson gson;

    public cSessionManagerImpl(Context context) {
        sharedPreference = new cSharedPreference(context);
        preferences      = sharedPreference.getSettings();
        editor           = sharedPreference.getEditor();

        gson = new Gson();
    }

    /*################################# SHARED PREFERENCE ACTIONS ################################*/

    /* #################### FUNCTIONS FOR SAVING AND LOADING CURRENT SETTINGS ####################*/

    /**
     * This method can be called to check whether there is any logged in user.
     * If no user is loggedIn, it returns false otherwise true
     */
    public boolean isLoggedIn() {
        return preferences.getBoolean(cSharedPreference.KEY_IS_LOGGEDIN, false);
    }

    /**
     * This sets user ID (which is used as owner ID) for records and saves it.
     *
     * @param userID
     */
    public void saveUserID(long userID) {
        editor.putLong(cSharedPreference.KEY_USER_ID, userID);
    }

    /**
     * This returns user ID of the loggedIn user. If no user, it returns -1.
     *
     * @return userID
     */
    public long loadUserID() {
        return preferences.getLong(cSharedPreference.KEY_USER_ID, -1);
    }

    /**
     * This sets user organization ID for records and saves it.
     *
     * @param organizationID
     */
    public void saveOrganizationID(long organizationID) {
        editor.putLong(cSharedPreference.KEY_ORG_ID, organizationID);
    }

    /**
     * This returns organization ID of the loggedIn user. If no organization,
     * it returns -1.
     *
     * @return organizationID
     */
    public long loadOrganizationID() {
        return preferences.getLong(cSharedPreference.KEY_ORG_ID, -1);
    }

    public void saveDefaultPermsBITS(int bitNumber) {
        editor.putInt(cSharedPreference.KEY_PERMS_BITS, bitNumber);
    }

    public int loadDefaultPermsBITS() {
        return preferences.getInt(cSharedPreference.KEY_PERMS_BITS, -1);
    }

    public void saveDefaultStatusBITS(int bitNumber) {
        editor.putInt(cSharedPreference.KEY_STATUS_BITS, bitNumber);
    }

    public int loadDefaultStatusBITS() {
        return preferences.getInt(cSharedPreference.KEY_STATUS_BITS, -1);
    }

    /**
     * This sets the user model json string of the loggedIn user and saves it.
     *
     * @param userModel
     */
    public void saveUserProfile(cUserModel userModel) {
        String userProfile = gson.toJson(userModel);
        editor.putString(cSharedPreference.KEY_USER_PROFILE, userProfile);
    }

    /**
     * This returns the json string of the loggedIn user domain. If no user is
     * loggedIn it returns null
     *
     * @return json string
     */
    public String loadUserProfile() {
        return preferences.getString(cSharedPreference.KEY_USER_PROFILE, null);
    }

    /**
     * This sets primary role bits of the loggedIn user and saves it.
     *
     * @param userModel
     */
    public void savePrimaryRoleBITS(cUserModel userModel) {
        int primaryRoleBITS = 0;
        for (cRoleModel model : userModel.getRoleModelSet()) {
            /*if (userModel.getOrganizationID() == model.getOrganizationID()) {
                primaryRoleBITS = model.getRoleID();
                break;
            }*/
        }
        StringBuilder key = new StringBuilder(cSharedPreference.KEY_PRIMARY_ROLE_BITS);
        editor.putInt(String.valueOf(key), primaryRoleBITS);
    }

    /**
     * This returns a primary role bits of the loggedIn user. If no primary role,
     * it returns -1.
     *
     * @return
     */
    public int loadPrimaryRoleBITS() {
        StringBuilder key = new StringBuilder(cSharedPreference.KEY_PRIMARY_ROLE_BITS);
        return preferences.getInt(String.valueOf(key), -1);
    }

    /**
     * This sets secondary role bits of the loggedIn user and saves it.
     *
     * @param userModel
     */
    public void saveSecondaryRoleBITS(cUserModel userModel) {
        int secondaryRoleBITS = 0;
        for (cRoleModel model : userModel.getRoleModelSet()) {
            /*if (userModel.getOrganizationID() != model.getOrganizationID()) {
                secondaryRoleBITS |= model.getRoleID();
            }*/
        }
        StringBuilder key = new StringBuilder(cSharedPreference.KEY_SECONDARY_ROLE_BITS);
        editor.putInt(String.valueOf(key), secondaryRoleBITS);
    }

    /**
     * This returns a secondary roles of the loggedIn user. If no secondary roles,
     * it returns -1.
     *
     * @return
     */
    public int loadSecondaryRoleBITS() {
        StringBuilder key = new StringBuilder(cSharedPreference.KEY_SECONDARY_ROLE_BITS);
        return preferences.getInt(String.valueOf(key), -1);
    }

    /**
     * @param permissionModelSet
     */
    public void savePermissionBITS(Set<cPermissionModel> permissionModelSet) {
        ArrayList<cPermissionModel> perm = new ArrayList<>(permissionModelSet);
        for (int i = 0; i < perm.size(); i++) {
            /* save operation BITS for an entity : ENTITY-TYPE->OPS_BITS*/
            int operationBITS = 0;
            for (int j = 0; j < perm.size(); j++) {
                if (perm.get(i).getEntityID() == perm.get(j).getEntityID() &&
                        (perm.get(i).getEntityTypeID() == perm.get(j).getEntityTypeID())) {
                    operationBITS |= perm.get(j).getOperationID();
                }
            }
            StringBuilder opsKey = new StringBuilder(cSharedPreference.KEY_ENTITY_OPERATION_BITS);
            opsKey.append("-");
            opsKey.append(perm.get(i).getEntityID());
            opsKey.append("-");
            opsKey.append(perm.get(i).getEntityTypeID());
            editor.putInt(String.valueOf(opsKey), operationBITS);

            /* save status BITS for a privilege: ENTITY-TYPE-OPERATION->STATUS_BITS*/
            cStatusSetModel statusSetModel = perm.get(i).getStatusSetModel();
            ArrayList<cStatusModel> status = new ArrayList<>(statusSetModel.getStatusModelSet());

            int statusBITS =0;
            for (int k = 0; k < status.size(); k++) {
                statusBITS |= status.get(k).getStatusID();
            }
            StringBuilder statusKey = new StringBuilder(cSharedPreference.KEY_OPERATION_STATUS_BITS);
            statusKey.append("-");
            statusKey.append(perm.get(i).getEntityID());
            statusKey.append("-");
            statusKey.append(perm.get(i).getEntityTypeID());
            statusKey.append("-");
            statusKey.append(perm.get(i).getOperationID());
            editor.putInt(String.valueOf(statusKey), (Integer) statusBITS);
        }

        /* save entity BITS for an entity : ENTITY-TYPE->BITS */
        for (int i = 0; i < types.length; i++) {
            int entityBITS = 0;
            for (cPermissionModel entityModel : permissionModelSet) {
                if (entityModel.getEntityTypeID() == types[i]) {
                    entityBITS |= entityModel.getEntityID();
                }
            }
            StringBuilder entityKey = new StringBuilder(cSharedPreference.KEY_ENTITY_TYPE_BITS);
            entityKey.append("-");
            entityKey.append(types[i]);
            editor.putInt(String.valueOf(entityKey), (Integer) entityBITS);
        }
    }

    /**
     * This returns entity BITS that belong to an entity type.
     *
     * @param entityTypeID
     * @return
     */
    public int loadEntityBITS(long entityTypeID) {
        StringBuilder entityTypeKey = new StringBuilder(cSharedPreference.KEY_ENTITY_TYPE_BITS);
        entityTypeKey.append("-");
        entityTypeKey.append(entityTypeID);
        return preferences.getInt(String.valueOf(entityTypeKey), 0);
    }

    /**
     * This returns operation BITS that belong to an entity with the specified
     * entityID and entityTypeID.
     *
     * @param entityID
     * @param entityTypeID
     * @return
     */
    public int loadOperationBITS(long entityID, long entityTypeID) {
        StringBuilder operationKey = new StringBuilder(cSharedPreference.KEY_ENTITY_OPERATION_BITS);
        operationKey.append("-");
        operationKey.append(entityID);
        operationKey.append("-");
        operationKey.append(entityTypeID);
        return preferences.getInt(String.valueOf(operationKey), 0);
    }

    /**
     * This returns status BITS that apply to operations that belong to an permission
     * with the specified permissionID
     *
     * @param entityID
     * @param entityTypeID
     * @param operationID
     * @return
     */
    public int loadStatusBITS(long entityID, long entityTypeID, long operationID) {
        StringBuilder statusKey = new StringBuilder(cSharedPreference.KEY_OPERATION_STATUS_BITS);
        statusKey.append("-");
        statusKey.append(entityID);
        statusKey.append("-");
        statusKey.append(entityTypeID);
        statusKey.append("-");
        statusKey.append(operationID);
        return preferences.getInt(String.valueOf(statusKey), 0);
    }

    @Override
    public void saveStatusSet(Set<cStatusModel> statusModelSet) {
        String statusSet = gson.toJson(statusModelSet);
        editor.putString(cSharedPreference.KEY_STATUS_SET, statusSet);
    }

    @Override
    public void saveRoleSet(Set<cRoleModel> roleModelSet) {
        String roleSet = gson.toJson(roleModelSet);
        editor.putString(cSharedPreference.KEY_ROLE_SET, roleSet);
    }

    @Override
    public Set loadStatusSet() {
        String statusString = preferences.getString(cSharedPreference.KEY_STATUS_SET,"");

        Set<cStatusModel> statusModelSet = gson.fromJson(statusString,
                new TypeToken<Set<cStatusModel>>(){}.getType());

        return statusModelSet;
    }

    @Override
    public Set loadRoleSet() {
        String roleString = preferences.getString(cSharedPreference.KEY_ROLE_SET,"");

        Set<cRoleModel> statusModelSet = gson.fromJson(roleString,
                new TypeToken<Set<cRoleModel>>(){}.getType());

        return statusModelSet;
    }

    @Override
    public void saveIndividualOwners(Set<cUserModel> userModels) {
        String userSet = gson.toJson(userModels);
        editor.putString(cSharedPreference.KEY_INDIVIDUAL_OWNER_SET, userSet);
    }

    @Override
    public void saveOrganizationOwners(Set<cOrganizationModel> organizationModels) {
        String organizationSet = gson.toJson(organizationModels);
        editor.putString(cSharedPreference.KEY_ORGANIZATION_OWNER_SET, organizationSet);
    }

    @Override
    public Set loadIndividualOwners() {
        String ownerString = preferences.getString(
                cSharedPreference.KEY_INDIVIDUAL_OWNER_SET,"");

        Set<cUserModel> userModels = gson.fromJson(ownerString,
                new TypeToken<Set<cUserModel>>(){}.getType());

        return userModels;
    }

    @Override
    public Set loadOrganizationOwners() {
        String organizationString = preferences.getString(
                cSharedPreference.KEY_ORGANIZATION_OWNER_SET,"");

        Set<cOrganizationModel> organizationModels = gson.fromJson(organizationString,
                new TypeToken<Set<cOrganizationModel>>(){}.getType());

        return organizationModels;
    }

    /* ###################### FUNCTIONS FOR CRUD AND LOG IN/OUT OPERATIONS ###################### */

    /**
     * This saves the preference of the loggedIn user. Should be called
     * after every preference setting function.
     */
    public void commitSettings() {
        editor.commit();
    }

    /**
     * This deletes all the saved settings of the loggedIn user.
     */
    public void deleteSettings() {
        editor.clear();
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
     * This sets the loggedIn variable to true
     */
    public void loginUser() {
        editor.putBoolean(cSharedPreference.KEY_IS_LOGGEDIN, true);
    }

    /**
     * This sets the loggedIn variable to true
     */
    public void logoutUser() {
        editor.putBoolean(cSharedPreference.KEY_IS_LOGGEDIN, false);
    }
}
