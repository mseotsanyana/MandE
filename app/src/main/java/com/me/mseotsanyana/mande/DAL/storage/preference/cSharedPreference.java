package com.me.mseotsanyana.mande.DAL.storage.preference;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.me.mseotsanyana.mande.DAL.model.session.cRoleModel;
import com.me.mseotsanyana.mande.DAL.model.session.cUserModel;
import com.me.mseotsanyana.mande.UTIL.cConstant;

public class cSharedPreference {
    private static String TAG = cSharedPreference.class.getSimpleName();

    // Shared preferences file name
    public static final String KEY_USER_PREFS      = "USER_PREFS";

    // General global settings
    public static final String KEY_USER_PROFILE    = "KEY-USER-PROFILE";

    // Global settings for bitwise access control
    public static final String KEY_USER_ID          = "KEY-USER-ID";
    public static final String KEY_ORGANIZATION_ID  = "KEY-ORG-ID";
    public static final String KEY_ORGANIZATIONS_ID = "KEY-ORGS-ID";

    /* prefixes for preference keys */
    public static final String KEY_PRIMARY_ROLE_BITS     = "KEY-PRB";
    public static final String KEY_SECONDARY_ROLE_BITS   = "KEY-SRB";
    public static final String KEY_ENTITY_TYPE_BITS      = "KEY-ETB";
    public static final String KEY_ENTITY_OPERATION_BITS = "KEY-EOB";
    public static final String KEY_OPERATION_STATUS_BITS = "KEY-OSB";

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

    public cSharedPreference(Context context) {
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
}