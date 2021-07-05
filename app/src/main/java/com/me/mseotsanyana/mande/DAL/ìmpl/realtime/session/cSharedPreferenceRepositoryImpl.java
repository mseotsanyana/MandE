package com.me.mseotsanyana.mande.DAL.ìmpl.realtime.session;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.me.mseotsanyana.mande.BLL.model.session.cMenuModel;
import com.me.mseotsanyana.mande.BLL.repository.session.iSharedPreferenceRepository;

import java.util.List;
import java.util.Map;
import java.util.SortedMap;

public class cSharedPreferenceRepositoryImpl /*implements iSharedPreferenceRepository*/ {
    //private static String TAG = cSharedPreference.class.getSimpleName();

    // Shared preferences file name
    public static final String KEY_USER_PREFS      = "USER_PREFS";

    // General global settings
    public static final String KEY_USER_PROFILE    = "KEY-USER-PROFILE";

    // Global settings for bitwise access control
    public static final String KEY_USER_ID     = "KEY-USER-ID";
    public static final String KEY_ORG_ID      = "KEY-ORG-ID";
    public static final String KEY_PERMS_BITS  = "KEY-PERMS-BITS";
    public static final String KEY_STATUS_BITS = "KEY_STATUS_BITS";

    /* prefixes for preference keys */

    public static final String KEY_MODULE_ENTITY_BITS    = "KEY-MEB";
    public static final String KEY_ENTITY_OPERATION_BITS = "KEY-EOB";
    public static final String KEY_OPERATION_STATUS_BITS = "KEY-OSB";

    public static final String KEY_STATUS_SET = "KEY-SS";
    public static final String KEY_ROLE_SET   = "KEY-RS";

    public static final String KEY_INDIVIDUAL_OWNER_SET   = "KEY-IOS";
    public static final String KEY_ORGANIZATION_OWNER_SET = "KEY-OOS";

    // All shared preferences file name
    public static final String KEY_IS_LOGGEDIN = "KEY-ISLOGGEDIN";
    public static final String KEY_TAG         = "KEY-PMER";

    private SharedPreferences settings;
    private SharedPreferences.Editor editor;

//    private int primaryRoleBITS, secondaryRoleBITS, entityBITS, operationBITS, statusBITS;

    public static final int[] entity_types = {
            1,  /* 0 = BRBAC (1) */
            2   /* 1 = PPMER (2) */
    };

    public cSharedPreferenceRepositoryImpl(Context context) {
        setSettings(context.getSharedPreferences(KEY_USER_PREFS, Context.MODE_PRIVATE));
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
     * @param key key
     */
    public void removeSetting(String key) {
        editor.remove(key);
    }

    /**
     * This takes the key and value parameters of an integer setting and
     * updates the corresponding preference.
     *
     * @param key key
     * @param value value
     */
    public void saveIntSetting(String key, int value) {
        editor.putInt(key, value);
    }

    /**
     * This takes the key and value parameters of an string setting and
     * updates the corresponding preference.
     *
     * @param key key
     * @param value value
     */
    public void saveStringSetting(String key, String value) {
        editor.putString(key, value);
    }

    /**
     * This takes the key and value parameters of an boolean setting and
     * updates the corresponding preference.
     *
     * @param key key
     * @param value value
     */
    public void saveBooleanSetting(String key, Boolean value) {
        editor.putBoolean(key, value);
    }

}
