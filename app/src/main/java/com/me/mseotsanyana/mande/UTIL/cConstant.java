package com.me.mseotsanyana.mande.UTIL;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class cConstant {
    public static final int NUM_OPS = 3;
    public static final int NUM_STS = 5;

    public static final int OWNER = 0;
    public static final int GROUP = 1;
    public static final int OTHER = 2;

    public static final int NUM_PERMS = 15;

    public static final int EXPAND   = 0;
    public static final int COLLAPSE = 1;


    /* frequency constant identification */
    public static final int CONS_DAILY_ID        = 1;
    public static final int CONS_WEEKLY_ID       = 2;
    public static final int CONS_FORT_NIGHTLY_ID = 3;
    public static final int CONS_MONTHLY_ID      = 4;
    public static final int CONS_QUARTERLY_ID    = 5;
    public static final int CONS_ANNUALLY_ID     = 6;


    public static final SimpleDateFormat FORMAT_DATE = new SimpleDateFormat(
            "dd MMMM, yyyy hh:mm:ss a", Locale.US);
    public static final SimpleDateFormat SHORT_FORMAT_DATE = new SimpleDateFormat(
            "dd-MM-yyyy", Locale.US);
    public static final SimpleDateFormat TIMESTAMP_FORMAT_DATE = new SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss", Locale.US);

    public static final NumberFormat SA_CURRENCY_FORMAT = NumberFormat.getCurrencyInstance(
            Locale.getDefault());
    public static final NumberFormat USA_CURRENCY_FORMAT = NumberFormat.getCurrencyInstance(
            Locale.US);

    public static final String BASE_URL = "http://10.0.2.2/";
    public static final String REGISTER_OPERATION = "register";
    public static final String LOGIN_OPERATION = "login";
    public static final String CHANGE_PASSWORD_OPERATION = "chgPass";

    public static final String SUCCESS = "success";
    public static final String FAILURE = "failure";
    //public static final String IS_LOGGED_IN = "isLoggedIn";

    // constants from cBitwise
    //public static final int NUM_OPERATIONS = 15;
    //public static final int NUM_STATUSES   = 5;

    // URLs for communicating with the server
    public static final String URL_ADDRESS = BASE_URL+"MEServer/sAddressAPI.php";
    public static final String URL_USER    = BASE_URL+"MEServer/sUserAPI.php";

}

/*

    public static final String KEY_USER_PRIMARY_ROLE    = "USER_PRIMARY_ROLE";
    public static final String KEY_USER_SECONDARY_ROLES = "USER_SECONDARY_ROLES";
    public static final String KEY_USER_BRBAC_ENTITIES  = "USER_USER_BRBAC_ENTITIES";
    public static final String KEY_USER_PPMER_ENTITIES  = "USER_USER_PPMER_ENTITIES";
    public static final String KEY_USER_ALL_OPERATIONS  = "USER_ALL_OPERATIONS";
    public static final String KEY_USER_ALL_STATUSES    = "USER_ALL_STATUSES";

    /* the settings for all brbac entities
    public static final String KEY_ADDRESS_ENTITY_ID    = "ADDRESS_ENTITY_ID";
    public static final String KEY_ADDRESS_ENTITY_TYPE  = "ADDRESS_ENTITY_TYPE";
    public static final String KEY_ADDRESS_OPERATIONS   = "ADDRESS_OPERATIONS";
    public static final String KEY_ADDRESS_PERMS_STATUS = "ADDRESS_PERMS_STATUS";
    public static final String KEY_ADDRESS_STATUSES     = "ADDRESS_STATUSES";

    public static final String KEY_ORGANIZATION_ENTITY_ID    = "ORGANIZATION_ENTITY_ID";
    public static final String KEY_ORGANIZATION_ENTITY_TYPE  = "ORGANIZATION_ENTITY_TYPE";
    public static final String KEY_ORGANIZATION_OPERATIONS   = "ORGANIZATION_OPERATIONS";
    public static final String KEY_ORGANIZATION_PERMS_STATUS = "ORGANIZATION_PERMS_STATUS";
    public static final String KEY_ORGANIZATION_STATUSES     = "ORGANIZATION_STATUSES";

    public static final String KEY_VALUE_ENTITY_ID    = "VALUE_ENTITY_ID";
    public static final String KEY_VALUE_ENTITY_TYPE  = "VALUE_ENTITY_TYPE";
    public static final String KEY_VALUE_OPERATIONS   = "VALUE_OPERATIONS";
    public static final String KEY_VALUE_PERMS_STATUS = "VALUE_PERMS_STATUS";
    public static final String KEY_VALUE_STATUSES     = "VALUE_STATUSES";

    public static final String KEY_USER_ENTITY_ID    = "USER_ENTITY_ID";
    public static final String KEY_USER_ENTITY_TYPE  = "USER_ENTITY_TYPE";
    public static final String KEY_USER_OPERATIONS   = "USER_OPERATIONS";
    public static final String KEY_USER_PERMS_STATUS = "USER_PERMS_STATUS";
    public static final String KEY_USER_STATUSES     = "USER_STATUSES";

    public static final String KEY_SESSION_ENTITY_ID    = "SESSION_ENTITY_ID";
    public static final String KEY_SESSION_ENTITY_TYPE  = "SESSION_ENTITY_TYPE";
    public static final String KEY_SESSION_OPERATIONS   = "SESSION_OPERATIONS";
    public static final String KEY_SESSION_PERMS_STATUS = "SESSION_PERMS_STATUS";
    public static final String KEY_SESSION_STATUSES     = "SESSION_STATUSES";

    public static final String KEY_ROLE_ENTITY_ID    = "ROLE_ENTITY_ID";
    public static final String KEY_ROLE_ENTITY_TYPE  = "ROLE_ENTITY_TYPE";
    public static final String KEY_ROLE_OPERATIONS   = "ROLE_OPERATIONS";
    public static final String KEY_ROLE_PERMS_STATUS = "ROLE_PERMS_STATUS";
    public static final String KEY_ROLE_STATUSES     = "ROLE_STATUSES";

    public static final String KEY_MENU_ENTITY_ID    = "MENU_ENTITY_ID";
    public static final String KEY_MENU_ENTITY_TYPE  = "MENU_ENTITY_TYPE";
    public static final String KEY_MENU_OPERATIONS   = "MENU_OPERATIONS";
    public static final String KEY_MENU_PERMS_STATUS = "MENU_PERMS_STATUS";
    public static final String KEY_MENU_STATUSES     = "MENU_STATUSES";

    public static final String KEY_PRIVILEGE_ENTITY_ID    = "PRIVILEGE_ENTITY_ID";
    public static final String KEY_PRIVILEGE_ENTITY_TYPE  = "PRIVILEGE_ENTITY_TYPE";
    public static final String KEY_PRIVILEGE_OPERATIONS   = "PRIVILEGE_OPERATIONS";
    public static final String KEY_PRIVILEGE_PERMS_STATUS = "PRIVILEGE_PERMS_STATUS";
    public static final String KEY_PRIVILEGE_STATUSES     = "PRIVILEGE_STATUSES";

    public static final String KEY_ENTITY_ENTITY_ID    = "ENTITY_ENTITY_ID";
    public static final String KEY_ENTITY_ENTITY_TYPE  = "ENTITY_ENTITY_TYPE";
    public static final String KEY_ENTITY_OPERATIONS   = "ENTITY_OPERATIONS";
    public static final String KEY_ENTITY_PERMS_STATUS = "ENTITY_PERMS_STATUS";
    public static final String KEY_ENTITY_STATUSES     = "ENTITY_STATUSES";

    public static final String KEY_OPERATION_ENTITY_ID    = "OPERATION_ENTITY_ID";
    public static final String KEY_OPERATION_ENTITY_TYPE  = "OPERATION_ENTITY_TYPE";
    public static final String KEY_OPERATION_OPERATIONS   = "OPERATION_OPERATIONS";
    public static final String KEY_OPERATION_PERMS_STATUS = "OPERATION_PERMS_STATUS";
    public static final String KEY_OPERATION_STATUSES     = "OPERATION_STATUSES";

    public static final String KEY_STATUS_ENTITY_ID    = "STATUS_ENTITY_ID";
    public static final String KEY_STATUS_ENTITY_TYPE  = "STATUS_ENTITY_TYPE";
    public static final String KEY_STATUS_OPERATIONS   = "STATUS_OPERATIONS";
    public static final String KEY_STATUS_PERMS_STATUS = "STATUS_PERMS_STATUS";
    public static final String KEY_STATUS_STATUSES     = "STATUS_STATUSES";

 */
