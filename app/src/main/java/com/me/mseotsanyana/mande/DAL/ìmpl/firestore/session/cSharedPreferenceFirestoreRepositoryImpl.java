package com.me.mseotsanyana.mande.DAL.ìmpl.firestore.session;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.me.mseotsanyana.mande.BLL.model.session.cMenuModel;
import com.me.mseotsanyana.mande.BLL.repository.session.iSharedPreferenceRepository;
import com.me.mseotsanyana.mande.DAL.storage.preference.cSharedPreference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class cSharedPreferenceFirestoreRepositoryImpl implements iSharedPreferenceRepository {
    public static final String KEY_USER_PREFS = "USER_PREFS";

    private SharedPreferences settings;
    private SharedPreferences.Editor editor;
    private final Context context;

    public cSharedPreferenceFirestoreRepositoryImpl(Context context) {
        this.context = context;
        setSettings(this.context.getSharedPreferences(KEY_USER_PREFS, Context.MODE_PRIVATE));
        setEditor(settings.edit());
    }

    public SharedPreferences.Editor getEditor() {
        return editor;
    }

    public void setEditor(SharedPreferences.Editor editor) {
        this.editor = editor;
    }

    public SharedPreferences getSettings() {
        return settings;
    }

    public void setSettings(SharedPreferences settings) {
        this.settings = settings;
    }

    /* ###################### FUNCTIONS FOR CRUD AND LOG IN/OUT OPERATIONS ###################### */

    /**
     * This saves the preference of the loggedIn user. Should be called
     * after every preference setting function.
     */
    @Override
    public void commitSettings() {
        editor.commit();
    }

    /**
     * This deletes all the saved settings of the loggedIn user.
     */
    @Override
    public void deleteSettings() {
        editor.clear();
    }

    /**
     * This takes a key parameter and removes the corresponding preference.
     *
     * @param key key
     */
    @Override
    public void removeSetting(String key) {
        editor.remove(key);
    }

    /**
     * This takes the key and value parameters of an integer setting and
     * updates the corresponding preference.
     *
     * @param key   key
     * @param value value
     */
    @Override
    public void saveIntSetting(String key, int value) {
        editor.putInt(key, value);
    }

    /**
     * This takes the key and value parameters of an string setting and
     * updates the corresponding preference.
     *
     * @param key   key
     * @param value value
     */
    @Override
    public void saveStringSetting(String key, String value) {
        editor.putString(key, value);
    }

    /**
     * This takes the key and value parameters of an boolean setting and
     * updates the corresponding preference.
     *
     * @param key   key
     * @param value value
     */
    @Override
    public void saveBooleanSetting(String key, Boolean value) {
        editor.putBoolean(key, value);
    }


    /**
     * This takes the key and value parameters of an integer list setting and
     * updates the corresponding preference.
     *
     * @param key   key
     * @param value value
     */
    @Override
    public void saveListIntegerSetting(String key, List<Integer> value) {
        String intString;

        JSONArray jsonArr = new JSONArray();
        for (int i : value) {
            jsonArr.put(i);
        }

        intString = jsonArr.toString();
        editor.putString(key, intString);
    }

    @Override
    public void saveMenuItems(String key, List<cMenuModel> value) {
        String menuJSONString = new Gson().toJson(value);
        editor.putString(cSharedPreference.KEY_MENU_ITEM_BITS, menuJSONString);
    }

    @Override
    public String loadUserID() {
        return settings.getString(cSharedPreference.KEY_USER_ID, null);
    }

    @Override
    public String loadOrganizationID() {
        return settings.getString(cSharedPreference.KEY_ORG_ID, null);
    }

    @Override
    public int loadPrimaryTeamBIT() {
        return settings.getInt(cSharedPreference.KEY_PRIMARY_TEAM_BIT, -1);
    }

    @Override
    public List<Integer> loadSecondaryTeams() {
        String intString = settings.getString(cSharedPreference.KEY_SECONDARY_TEAMS, "");
        try {
            JSONArray jsonArr = new JSONArray(new JSONTokener(intString));
            List<Integer> result = new ArrayList<>();

            for (int i = 0; i < jsonArr.length(); i++) {
                result.add(jsonArr.getInt(i));
            }
            return result;
        } catch (JSONException e) {
            return null;
        }
    }

    @Override
    public List<Integer> loadOperationStatuses(int moduleKey, int entityKey, int operationKey) {
        String intString = settings.getString(cSharedPreference.KEY_OPERATION_STATUS_BITS + "-" +
                moduleKey + "-" + entityKey + "-" + operationKey, "");
        try {
            JSONArray jsonArr = new JSONArray(new JSONTokener(intString));
            List<Integer> result = new ArrayList<>();

            for (int i = 0; i < jsonArr.length(); i++) {
                result.add(jsonArr.getInt(i));
            }
            return result;
        } catch (JSONException e) {
            return null;
        }
    }

    @Override
    public int loadEntityPermissionBITS(int moduleKey, int entityKey) {
        return settings.getInt(cSharedPreference.KEY_ENTITY_OPERATION_BITS + "-" +
                moduleKey + "-" + entityKey, -1);
    }

    @Override
    public int loadUnixPermissionBITS(int moduleKey, int entityKey) {
        return settings.getInt(cSharedPreference.KEY_UNIX_PERM_BITS + "-" +
                moduleKey + "-" + entityKey, -1);
    }

    @Override
    public List<cMenuModel> loadMenuItems() {
        String jsonString = settings.getString(cSharedPreference.KEY_MENU_ITEM_BITS,null);
        Type type = new TypeToken<List<cMenuModel>>() {}.getType();
        return new Gson().fromJson(jsonString, type);
    }
}