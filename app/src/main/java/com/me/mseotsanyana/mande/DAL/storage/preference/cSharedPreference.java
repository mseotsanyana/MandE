package com.me.mseotsanyana.mande.DAL.storage.preference;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

public class cSharedPreference {
    // Shared preferences file name
    public static final String KEY_USER_PREFS            = "USER_PREFS";

    // General global settings
    public static final String KEY_USER_PROFILE          = "KEY-USER-PROFILE";

    // global settings for bitwise access control
    public static final String KEY_USER_ID               = "KEY-USER-ID";
    public static final String KEY_ORG_ID                = "KEY-ORG-ID";
    public static final String KEY_PRIMARY_TEAM_BIT      = "KEY-PTB";
    public static final String KEY_SECONDARY_TEAMS       = "KEY-STM";
    public static final String KEY_MODULE_ENTITY_BITS    = "KEY-MEB";
    public static final String KEY_ENTITY_OPERATION_BITS = "KEY-EOB";
    public static final String KEY_OPERATION_STATUS_BITS = "KEY-OSB";
    public static final String KEY_UNIX_PERM_BITS        = "KEY-UPB";
    public static final String KEY_MENU_ITEM_BITS        = "KEY-MIB";
    public static final String KEY_IS_LOGGEDIN           = "KEY-ISLOGGEDIN";

    /* system module constants */
    public static final int SESSION_MODULE    = 1;
    public static final int LOGFRAME_MODULE   = 2;
    public static final int EVALUATION_MODULE = 4;

    /* system entity constants */
    public static final int USER             = 1;
    public static final int SESSION          = 2;
    public static final int PLAN             = 4;
    public static final int FEATURE          = 8;
    public static final int ORGANIZATION     = 16;
    public static final int USERACCOUNT      = 32;
    public static final int TEAM             = 64;
    public static final int ROLE             = 128;
    public static final int PERMISSION       = 256;


    /* system entity operation constants */
    public static final int CREATE           = 1;
    public static final int READ             = 2;
    public static final int UPDATE           = 4;
    public static final int DELETE           = 8;
    public static final int JOIN             = 16;
    public static final int UPLOAD           = 32;
    public static final int DOWNLOAD         = 64;

    /* unix-style row-level operations */
    public static final int OWNER_READ       = 2048;
    public static final int PRIMARY_READ     = 1024;
    public static final int SECONDARY_READ   = 512;
    public static final int WORKPLACE_READ   = 256;

    public static final int OWNER_UPDATE     = 128;
    public static final int PRIMARY_UPDATE   = 64;
    public static final int SECONDARY_UPDATE = 32;
    public static final int WORKPLACE_UPDATE = 16;

    public static final int OWNER_DELETE     = 8;
    public static final int PRIMARY_DELETE   = 4;
    public static final int SECONDARY_DELETE = 2;
    public static final int WORKPLACE_DELETE = 1;

    public static final int[] permissions = {
            OWNER_READ,       /* 0  */
            PRIMARY_READ,     /* 1  */
            SECONDARY_READ,   /* 2  */
            WORKPLACE_READ,   /* 3  */
            OWNER_UPDATE,     /* 4  */
            PRIMARY_UPDATE,   /* 5  */
            SECONDARY_UPDATE, /* 6  */
            WORKPLACE_UPDATE, /* 7  */
            OWNER_DELETE,     /* 8 */
            PRIMARY_DELETE,   /* 9 */
            SECONDARY_DELETE, /* 10 */
            WORKPLACE_DELETE, /* 11 */
    };

    private SharedPreferences settings;
    private SharedPreferences.Editor editor;

    @SuppressLint("CommitPrefEdits")
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
