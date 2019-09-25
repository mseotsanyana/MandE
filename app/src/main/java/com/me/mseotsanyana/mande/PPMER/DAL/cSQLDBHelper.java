package com.me.mseotsanyana.mande.PPMER.DAL;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.me.mseotsanyana.mande.Util.cPopulateModelsFromExcel;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;

public class cSQLDBHelper extends SQLiteOpenHelper
{
    // Logcat tag
    private static final String LOG = "dbHelper";

    private Context context;
    private cPopulateModelsFromExcel populateModelsFromExcel;
    private ProgressDialog progressDialog;

    String[] listOfBRBACTables = {"ADDRESS","ORGANIZATION","VALUE","USER","SESSION","ROLE","MENU",
            "PRIVILEGE","ENTITY","OPERATION","STATUS","USER_ROLE","SESSION_ROLE","MENU_ROLE",
            "PERMISSION"};

    // Database Version
    private static final int DATABASE_VERSION = 153;

    // Database Name
    private static final String DATABASE_NAME = "ME.db";

    // ##### PLANNING MODULE #####
    public static final String TABLE_tblLOGFRAME          = "tblLOGFRAME";          /* 1 */
    public static final String TABLE_tblLOGFRAMETREE      = "tblLOGFRAMETREE";      /* 2 */
    public static final String TABLE_tblIMPACT            = "tblIMPACT";            /* 3 */
    public static final String TABLE_tblOUTCOME           = "tblOUTCOME";           /* 4 */
    public static final String TABLE_tblOUTPUT            = "tblOUTPUT";            /* 5 */
    public static final String TABLE_tblACTIVITY          = "tblACTIVITY";          /* 6 */
    public static final String TABLE_tblINPUT             = "tblINPUT";             /* 7 */
    public static final String TABLE_tblPRECEDINGACTIVITY = "tblPRECEDINGACTIVITY"; /* 8 */

    public static final String TABLE_tblOUTCOME_IMPACT    = "tblOUTCOME_IMPACT";    /* 9 */
    public static final String TABLE_tblOUTPUT_OUTCOME    = "tblOUTPUT_OUTCOME";    /* 10 */
    public static final String TABLE_tblACTIVITY_OUTPUT   = "tblACTIVITY_OUTPUT";   /* 11 */

    public static final String TABLE_tblCRITERIA          = "tblCRITERIA";          /* 12 */
    public static final String TABLE_tblQUESTION_GROUPING = "tblQUESTION_GROUPING"; /* 13 */
    public static final String TABLE_tblQUESTION_TYPE     = "tblQUESTION_TYPE";     /* 14 */

    public static final String TABLE_tblQUESTION          = "tblQUESTION";          /* 15 */
    public static final String TABLE_tblIMPACT_QUESTION   = "tblIMPACT_QUESTION";   /* 16 */
    public static final String TABLE_tblOUTCOME_QUESTION  = "tblOUTCOME_QUESTION";  /* 17 */
    public static final String TABLE_tblOUTPUT_QUESTION   = "tblOUTPUT_QUESTION";   /* 18 */
    public static final String TABLE_tblACTIVITY_QUESTION = "tblACTIVITY_QUESTION";  /* 19 */
    public static final String TABLE_tblINPUT_QUESTION    = "tblINPUT_QUESTION";    /* 20 */

    public static final String TABLE_tblRAID              = "tblRAID";              /* 21 */
    public static final String TABLE_tblIMPACT_RAID       = "tblIMPACT_RAID";       /* 22 */
    public static final String TABLE_tblOUTCOME_RAID      = "tblOUTCOME_RAID";      /* 23 */
    public static final String TABLE_tblOUTPUT_RAID       = "tblOUTPUT_RAID";       /* 24 */
    public static final String TABLE_tblACTIVITY_RAID     = "tblACTIVITY_RAID";     /* 25 */

    // Bitwise Role Based Access Control (BRBAC) tables
    public static final String TABLE_ADDRESS          = "ADDRESS";         /* 1 */
    public static final String TABLE_ORGANIZATION     = "ORGANIZATION";    /* 2 */
    public static final String TABLE_VALUE            = "VALUE";           /* 3 */
    public static final String TABLE_USER             = "USER";            /* 4 */
    public static final String TABLE_SESSION          = "SESSION";         /* 5 */
    public static final String TABLE_ROLE             = "ROLE";            /* 6 */
    public static final String TABLE_MENU             = "MENU";            /* 7 */
    public static final String TABLE_PRIVILEGE        = "PRIVILEGE";       /* 8 */
    public static final String TABLE_ENTITY           = "ENTITY";          /* 9 */
    public static final String TABLE_OPERATION        = "OPERATION";       /* 10 */
    public static final String TABLE_STATUS           = "STATUS";          /* 11 */
    public static final String TABLE_USER_ROLE        = "USER_ROLE";       /* 12 */
    public static final String TABLE_SESSION_ROLE     = "SESSION_ROLE";    /* 13 */
    public static final String TABLE_MENU_ROLE        = "MENU_ROLE";       /* 14 */
    public static final String TABLE_PRIVILEGE_ROLE   = "PRIVILEGE_ROLE";  /* 15 */
    //public static final String TABLE_OPERATION_STATUS = "OPERATION_STATUS";/* 16 */
    public static final String TABLE_PERMISSION       = "PERMISSION";      /* 17 */

    //##### End of BRBAC tables #####

    public static final String TABLE_TYPE         = "TYPE";   /* 5 */
    public static final String TABLE_ACTIVITYLOG  = "ACTIVITYLOG";  /* 7 */
    public static final String TABLE_NOTIFICATION = "NOTIFICATION"; /* 9 */

    // dimension Tables --
    public static final String TABLE_DATE           = "DATE";           /* 1 */
    public static final String TABLE_UNIT           = "UNIT";           /* 3 */
    public static final String TABLE_SCALE          = "SCALE";          /* 4 */
    public static final String TABLE_CATEGORY       = "CATEGORY";       /* 5 */
    public static final String TABLE_RISKMAP        = "RISKMAP";        /* 6 */
    public static final String TABLE_RISKLIKELIHOOD = "RISKLIKELIHOOD"; /* 7 */
    public static final String TABLE_RISKIMPACT     = "RISKIMPACT";     /* 8 */

    // relation tables
    public static final String TABLE_OVERALLAIM   = "OVERALLAIM";   /* 12 */
    public static final String TABLE_PROJECT      = "PROJECT";      /* 13 */
    public static final String TABLE_SPECIFICAIM  = "SPECIFICAIM";  /* 14 */
    public static final String TABLE_OUTCOME      = "OUTCOME";      /* 15 */
    public static final String TABLE_OBJECTIVE    = "OBJECTIVE";    /* 16 */
    public static final String TABLE_OUTPUT       = "OUTPUT";       /* 17 */
    public static final String TABLE_ACTIVITY     = "ACTIVITY";     /* 18 */
    public static final String TABLE_INDICATOR    = "INDICATOR";    /* 19 */
    public static final String TABLE_QUANTITATIVEINDICATOR = "QUANTITATIVEINDICATOR";/* 20 */
    public static final String TABLE_QUALITATIVEINDICATOR  = "QUALITATIVEINDICATOR"; /* 21 */
    public static final String TABLE_HYBRIDINDICATOR       = "HYBRIDINDICATOR";      /* 22 */
    public static final String TABLE_QUANTITATIVETARGET    = "QUANTITATIVETARGET";   /* 23 */
    public static final String TABLE_QUALITATIVETARGET     = "QUALITATIVETARGET";    /* 24 */
    public static final String TABLE_HYBRIDTARGET          = "HYBRIDTARGET";         /* 25 */
    public static final String TABLE_MOV          = "MOV";          /* 19 */
    public static final String TABLE_RISKREGISTER = "RISKREGISTER"; /* 20 */
    public static final String TABLE_RISK         = "RISK";         /* 20 */
    public static final String TABLE_RISKCONSEQUENCE = "RISKCONSEQUENCE"; /* 20 */
    public static final String TABLE_RISKMITIGATION  = "RISKMITIGATION"; /* 20 */
    public static final String TABLE_RISKMANAGEMENT  = "RISKMANAGEMENT"; /* 20 */
    private static final String TABLE_WORKPLAN     = "WORKPLAN";     /* 21 */
    private static final String TABLE_RESOURCE     = "RESOURCE";     /* 22 */
    private static final String TABLE_ACCOUNTTYPE  = "ACCOUNTTYPE";  /* 23 */
    private static final String TABLE_BUDGET       = "BUDGET";       /* 24 */
    private static final String TABLE_CASHFLOW     = "CASHFLOW";     /* 25 */
    private static final String TABLE_MONITORING   = "MONITORING";   /* 26 */
    private static final String TABLE_EVALUATION   = "EVALUATION";   /* 27 */
    private static final String TABLE_CRITERION    = "CRITERION";    /* 28 */
    private static final String TABLE_QUESTION     = "QUESTION";     /* 29 */

    // junction tables
    public static final String TABLE_USER_NOTIFICATION = "USER_NOTIFICATION";    /* 31 */
    public static final String TABLE_ROLE_OPERATION_OBJECT_TYPE_STATUS = "ROLE_OPERATION_OBJECT_TYPE_STATUS";   /* 32 */
    private static final String TABLE_OVERALLAIM_PROJECT   = "OVERALLAIM_PROJECT";   /* 33 */
    private static final String TABLE_PROJECT_STATUS       = "PROJECT_STATUS";       /* 34 */
    public static final String TABLE_PROJECT_OUTCOME       = "PROJECT_OUTCOME";      /* 35 */
    public static final String TABLE_OUTCOME_OUTPUT       = "OUTCOME_OUTPUT";       /* 36 */
    public static final String TABLE_OUTPUT_ACTIVITY      = "OUTPUT_ACTIVITY";      /* 37 */
    private static final String TABLE_OVERALLAIM_INDICATOR = "OVERALLAIM_INDICATOR"; /* 38 */
    private static final String TABLE_OUTCOME_INDICATOR    = "OUTCOME_INDICATOR";    /* 39 */
    private static final String TABLE_OUTPUT_INDICATOR     = "OUTPUT_INDICATOR";     /* 40 */
    public static final String TABLE_MOV_INDICATOR        = "MOV_INDICATOR";        /* 41 */
    public static final String TABLE_OVERALLAIM_RISK      = "OVERALLAIM_RISK";      /* 42 */
    public static final String TABLE_OUTCOME_RISK         = "OUTCOME_RISK";         /* 43 */
    public static final String TABLE_OUTPUT_RISK          = "OUTPUT_RISK";          /* 44 */
    public static final String TABLE_ACTIVITY_RISK        = "ACTIVITY_RISK";        /* 45 */
    private static final String TABLE_OVERALLAIM_CRITERION = "OVERALLAIM_CRITERION"; /* 46 */
    private static final String TABLE_OUTCOME_CRITERION    = "OUTCOME_CRITERION ";   /* 47 */
    private static final String TABLE_OUTPUT_CRITERION     = "OUTPUT_CRITERION";     /* 48 */
    private static final String TABLE_ACTIVITY_CRITERION   = "ACTIVITY_CRITERION";   /* 49 */
    private static final String TABLE_QUESTION_CATEGORY    = "QUESTION_CATEGORY";    /* 50 */
    private static final String TABLE_INDICATOR_QUESTION   = "INDICATOR_QUESTION";   /* 51 */
    private static final String TABLE_OVERALLAIM_CRITERION_QUESTION = "OVERALLAIM_CRITERION_QUESTION";/* 52 */
    private static final String TABLE_OUTCOME_CRITERION_QUESTION    = "OUTCOME_CRITERION_QUESTION"; /* 53 */
    private static final String TABLE_OUTPUT_CRITERION_QUESTION     = "OUTPUT_CRITERION_QUESTION"; /* 54 */
    private static final String TABLE_ACTIVITY_CRITERION_QUESTION   = "ACTIVITY_CRITERION_QUESTION";/* 55 */
    private static final String TABLE_MONITORING_QUESTIONNAIRE      = "MONITORING_QUESTIONNAIRE";/* 56 */
    private static final String TABLE_EVALUATION_QUESTIONNAIRE      = "EVALUATION_QUESTIONNAIRE";/* 57 */
    private static final String TABLE_MONITORING_DATACOLLECTION     = "MONITORING_RESULT";/* 58 */
    private static final String TABLE_EVALUATION_DATACOLLECTION     = "EVALUATION_RESULT";/* 59 */

    //########################### Start of Bitwise Role Based Access Control ###########################

    // ADDRESS Table - column names
    public static final String KEY_ADDRESS_ID            = "_id";
    public static final String KEY_ADDRESS_SERVER_ID     = "_id_server";
    public static final String KEY_ADDRESS_OWNER_ID      = "_id_owner";
    public static final String KEY_ADDRESS_ORG_ID        = "_id_org";
    public static final String KEY_ADDRESS_GROUP_BITS    = "_bits_group";
    public static final String KEY_ADDRESS_PERMS_BITS    = "_bits_perms";
    public static final String KEY_ADDRESS_STATUS_BITS   = "_bits_status";
    public static final String KEY_ADDRESS_STREET        = "street";
    public static final String KEY_ADDRESS_CITY          = "city";
    public static final String KEY_ADDRESS_PROVINCE      = "province";
    public static final String KEY_ADDRESS_POSTAL_CODE   = "postal_code";
    public static final String KEY_ADDRESS_COUNTRY       = "country";
    public static final String KEY_ADDRESS_CREATED_DATE  = "created_date";
    public static final String KEY_ADDRESS_MODIFIED_DATE = "modified_date";
    public static final String KEY_ADDRESS_SYNCED_DATE   = "synced_date";

    // MENU table - create statement
    public static final String CREATE_TABLE_ADDRESS = "CREATE TABLE " + TABLE_ADDRESS + "("
            + KEY_ADDRESS_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_ADDRESS_SERVER_ID +" INTEGER DEFAULT NULL, "
            + KEY_ADDRESS_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ADDRESS_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ADDRESS_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ADDRESS_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_ADDRESS_STATUS_BITS + " INTEGER NOT NULL DEFAULT 3, "
            + KEY_ADDRESS_STREET + " TEXT, "
            + KEY_ADDRESS_CITY + " TEXT, "
            + KEY_ADDRESS_PROVINCE + " TEXT, "
            + KEY_ADDRESS_POSTAL_CODE + " TEXT, "
            + KEY_ADDRESS_COUNTRY + " TEXT, "
            + KEY_ADDRESS_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_ADDRESS_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_ADDRESS_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP " + ");";

    // ORGANIZATION Table - column names
    public static final String KEY_ORGANIZATION_ID            = "_id";
    public static final String KEY_ORGANIZATION_SERVER_ID     = "_id_server";
    public static final String KEY_ADDRESS_FK_ID              = "_id_address_fk";
    public static final String KEY_ORGANIZATION_OWNER_ID      = "_id_owner";
    public static final String KEY_ORGANIZATION_ORG_ID        = "_id_org";
    public static final String KEY_ORGANIZATION_GROUP_BITS    = "_bits_group";
    public static final String KEY_ORGANIZATION_PERMS_BITS    = "_bits_perms";
    public static final String KEY_ORGANIZATION_STATUS_BITS   = "_bits_status";
    public static final String KEY_ORGANIZATION_NAME          = "name";
    public static final String KEY_ORGANIZATION_TELEPHONE     = "telephone";
    public static final String KEY_ORGANIZATION_FAX           = "fax";
    public static final String KEY_ORGANIZATION_VISION        = "vision";
    public static final String KEY_ORGANIZATION_MISSION       = "mission";
    public static final String KEY_ORGANIZATION_EMAIL         = "email";
    public static final String KEY_ORGANIZATION_WEBSITE       = "website";
    public static final String KEY_ORGANIZATION_CREATED_DATE  = "created_date";
    public static final String KEY_ORGANIZATION_MODIFIED_DATE = "modified_date";
    public static final String KEY_ORGANIZATION_SYNCED_DATE   = "synced_date";

    // ORGANIZATION table - create statement
    public static final String CREATE_TABLE_ORGANIZATION = "CREATE TABLE " + TABLE_ORGANIZATION + "("
            + KEY_ORGANIZATION_ID +" INTEGER NOT NULL, "
            + KEY_ORGANIZATION_SERVER_ID +" INTEGER DEFAULT NULL, "
            + KEY_ADDRESS_FK_ID + " INTEGER, "
            + KEY_ORGANIZATION_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ORGANIZATION_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ORGANIZATION_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ORGANIZATION_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_ORGANIZATION_STATUS_BITS + " INTEGER NOT NULL DEFAULT 3, "
            + KEY_ORGANIZATION_NAME + " TEXT, "
            + KEY_ORGANIZATION_TELEPHONE + " TEXT, "
            + KEY_ORGANIZATION_FAX + " TEXT, "
            + KEY_ORGANIZATION_VISION + " TEXT, "
            + KEY_ORGANIZATION_MISSION + " TEXT, "
            + KEY_ORGANIZATION_EMAIL + " TEXT, "
            + KEY_ORGANIZATION_WEBSITE + " TEXT, "
            + KEY_ORGANIZATION_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_ORGANIZATION_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_ORGANIZATION_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + "FOREIGN KEY (" + KEY_ADDRESS_FK_ID + ") "
            + "REFERENCES " + TABLE_ADDRESS + "("+ KEY_ADDRESS_ID + ") ON DELETE CASCADE,"
            + "PRIMARY KEY (" + KEY_ORGANIZATION_ID + "));";

    // USER table - column names
    public static final String KEY_USER_ID            = "_id";
    public static final String KEY_USER_SERVER_ID     = "_id_server";
    public static final String KEY_ORGANIZATION_FK_ID = "_id_organization_fk";
    public static final String KEY_USER_OWNER_ID      = "_id_owner";
    public static final String KEY_USER_ORG_ID        = "_id_org";
    public static final String KEY_USER_GROUP_BITS    = "_bits_group";
    public static final String KEY_USER_PERMS_BITS    = "_bits_perms";
    public static final String KEY_USER_STATUS_BITS   = "_bits_status";
    public static final String KEY_USER_PHOTO         = "photo_path";
    public static final String KEY_USER_NAME          = "name";
    public static final String KEY_USER_SURNAME       = "surname";
    public static final String KEY_USER_GENDER        = "gender";
    public static final String KEY_USER_DESCRIPTION   = "description";
    public static final String KEY_USER_EMAIL         = "email";
    public static final String KEY_USER_WEBSITE       = "website";
    public static final String KEY_USER_PHONE         = "phone";
    public static final String KEY_USER_PASSWORD      = "password";
    public static final String KEY_USER_SALT          = "salt";
    public static final String KEY_USER_CREATED_DATE  = "created_date";
    public static final String KEY_USER_MODIFIED_DATE = "modified_date";
    public static final String KEY_USER_SYNCED_DATE   = "synced_date";

    // USER table - create statement
    public static final String CREATE_TABLE_USER = "CREATE TABLE " + TABLE_USER + "("
            + KEY_USER_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_USER_SERVER_ID +" INTEGER DEFAULT NULL, "
            + KEY_ORGANIZATION_FK_ID + " INTEGER NOT NULL, "
            + KEY_ADDRESS_FK_ID + " INTEGER NOT NULL, "
            + KEY_USER_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_USER_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_USER_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_USER_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_USER_STATUS_BITS + " INTEGER NOT NULL DEFAULT 3, "
            + KEY_USER_PHOTO + " TEXT , "
            + KEY_USER_NAME + " TEXT, "
            + KEY_USER_SURNAME + " TEXT, "
            + KEY_USER_GENDER + " TEXT, "
            + KEY_USER_DESCRIPTION + " TEXT, "
            + KEY_USER_EMAIL + " TEXT NOT NULL UNIQUE, "
            + KEY_USER_WEBSITE + " TEXT, "
            + KEY_USER_PHONE + " TEXT, "
            + KEY_USER_PASSWORD + " TEXT, "
            + KEY_USER_SALT + " TEXT, "
            + KEY_USER_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_USER_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_USER_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + "FOREIGN KEY (" + KEY_ADDRESS_FK_ID + ") "
            + "REFERENCES " + TABLE_ADDRESS + "("+ KEY_ADDRESS_ID + ") ON DELETE CASCADE,"
            + "FOREIGN KEY (" + KEY_ORGANIZATION_FK_ID + ") "
            + "REFERENCES " + TABLE_ORGANIZATION +"("+ KEY_ORGANIZATION_ID +") ON DELETE CASCADE );";

    // SESSION Table - column names
    public static final String KEY_SESSION_ID            = "_id";
    public static final String KEY_SESSION_SERVER_ID     = "_id_server";
    public static final String KEY_USER_FK_ID            = "_id_user_fk";
    public static final String KEY_SESSION_OWNER_ID      = "_id_owner";
    public static final String KEY_SESSION_ORG_ID        = "_id_org";
    public static final String KEY_SESSION_GROUP_BITS    = "_bits_group";
    public static final String KEY_SESSION_PERMS_BITS    = "_bits_perms";
    public static final String KEY_SESSION_STATUS_BITS   = "_bits_status";
    public static final String KEY_SESSION_PHOTO         = "photo_path";
    public static final String KEY_SESSION_NAME          = "name";
    public static final String KEY_SESSION_SURNAME       = "surname";
    public static final String KEY_SESSION_DESCRIPTION   = "description";
    public static final String KEY_SESSION_EMAIL         = "email";
    public static final String KEY_SESSION_WEBSITE       = "website";
    public static final String KEY_SESSION_PHONE         = "phone";
    public static final String KEY_SESSION_PASSWORD      = "password";
    public static final String KEY_SESSION_SALT          = "salt";
    public static final String KEY_SESSION_CREATED_DATE  = "created_date";
    public static final String KEY_SESSION_MODIFIED_DATE = "modified_date";
    public static final String KEY_SESSION_SYNCED_DATE   = "synced_date";

    // SESSION table - create statement
    public static final String CREATE_TABLE_SESSION = "CREATE TABLE " + TABLE_SESSION + "("
            + KEY_SESSION_ID +" INTEGER NOT NULL, "
            + KEY_SESSION_SERVER_ID +" INTEGER DEFAULT NULL, "
            + KEY_USER_FK_ID +" INTEGER NOT NULL, "
            + KEY_ORGANIZATION_FK_ID + " INTEGER NOT NULL, "
            + KEY_ADDRESS_FK_ID + " INTEGER NOT NULL, "
            + KEY_SESSION_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_SESSION_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_SESSION_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_SESSION_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_SESSION_STATUS_BITS + " INTEGER NOT NULL DEFAULT 3, "
            + KEY_SESSION_PHOTO + " TEXT, "
            + KEY_SESSION_NAME + " TEXT, "
            + KEY_SESSION_SURNAME + " TEXT, "
            + KEY_SESSION_DESCRIPTION + " TEXT, "
            + KEY_SESSION_EMAIL + " TEXT, "
            + KEY_SESSION_WEBSITE + " TEXT, "
            + KEY_SESSION_PHONE + " TEXT, "
            + KEY_SESSION_PASSWORD + " TEXT, "
            + KEY_SESSION_SALT + " TEXT, "
            + KEY_SESSION_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SESSION_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SESSION_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + "PRIMARY KEY (" + KEY_SESSION_ID + "), "
            + "FOREIGN KEY (" + KEY_USER_FK_ID + ") "
            + "REFERENCES " + TABLE_USER +"("+ KEY_USER_ID +") ON DELETE CASCADE );";

    // VALUE Table - column names
    public static final String KEY_VALUE_ID            = "_id";
    public static final String KEY_VALUE_SERVER_ID     = "_id_server";
    public static final String KEY_VALUE_OWNER_ID      = "_id_owner";
    public static final String KEY_VALUE_ORG_ID        = "_id_org";
    public static final String KEY_VALUE_GROUP_BITS    = "_bits_group";
    public static final String KEY_VALUE_PERMS_BITS    = "_bits_perms";
    public static final String KEY_VALUE_STATUS_BITS   = "_bits_status";
    public static final String KEY_VALUE_NAME          = "name";
    public static final String KEY_VALUE_CREATED_DATE  = "created_date";
    public static final String KEY_VALUE_MODIFIED_DATE = "modified_date";
    public static final String KEY_VALUE_SYNCED_DATE   = "synced_date";

    // VALUE table - create statement
    public static final String CREATE_TABLE_VALUE = "CREATE TABLE " + TABLE_VALUE + "("
            + KEY_VALUE_ID +" INTEGER NOT NULL, "
            + KEY_VALUE_SERVER_ID +" INTEGER DEFAULT NULL, "
            + KEY_ORGANIZATION_FK_ID + " INTEGER NOT NULL, "
            + KEY_VALUE_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_VALUE_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_VALUE_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_VALUE_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_VALUE_STATUS_BITS + " INTEGER NOT NULL DEFAULT 3, "
            + KEY_VALUE_NAME + " TEXT, "
            + KEY_VALUE_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_VALUE_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_VALUE_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + "PRIMARY KEY (" + KEY_VALUE_ID + "), "
            + "FOREIGN KEY (" + KEY_ORGANIZATION_FK_ID + ") "
            + "REFERENCES " + TABLE_ORGANIZATION +"("+ KEY_ORGANIZATION_ID +") ON DELETE CASCADE);";

    // ROLE Table - column names
    public static final String KEY_ROLE_ID            = "_id";
    public static final String KEY_ROLE_SERVER_ID     = "_id_server";
    public static final String KEY_ROLE_OWNER_ID      = "_id_owner";
    public static final String KEY_ROLE_ORG_ID        = "_id_org";
    public static final String KEY_ROLE_GROUP_BITS    = "_bits_group";
    public static final String KEY_ROLE_PERMS_BITS    = "_bits_perms";
    public static final String KEY_ROLE_STATUS_BITS   = "_bits_status";
    public static final String KEY_ROLE_NAME          = "name";
    public static final String KEY_ROLE_DESCRIPTION   = "description";
    public static final String KEY_ROLE_CREATED_DATE  = "created_date";
    public static final String KEY_ROLE_MODIFIED_DATE = "modified_date";
    public static final String KEY_ROLE_SYNCED_DATE   = "synced_date";

    // ROLE table - create statement
    public static final String CREATE_TABLE_ROLE = "CREATE TABLE " + TABLE_ROLE + "("
            + KEY_ROLE_ID +" INTEGER NOT NULL, "
            + KEY_ORGANIZATION_FK_ID + " INTEGER NOT NULL, "
            + KEY_ROLE_SERVER_ID +" INTEGER DEFAULT NULL, "
            + KEY_ROLE_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ROLE_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ROLE_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ROLE_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_ROLE_STATUS_BITS + " INTEGER NOT NULL DEFAULT 3, "
            + KEY_ROLE_NAME + " TEXT, "
            + KEY_ROLE_DESCRIPTION + " TEXT, "
            + KEY_ROLE_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_ROLE_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_ROLE_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + "PRIMARY KEY (" + KEY_ROLE_ID + ", "+ KEY_ORGANIZATION_FK_ID +"),"
            + "FOREIGN KEY (" + KEY_ORGANIZATION_FK_ID + ") "
            + "REFERENCES " + TABLE_ORGANIZATION +"("+ KEY_ORGANIZATION_ID +") ON DELETE CASCADE );";

    // MENU Table - column names
    public static final String KEY_MENU_ID            = "_id";
    public static final String KEY_MENU_SERVER_ID     = "_id_server";
    public static final String KEY_MENU_PARENT_ID     = "_id_parent";
    public static final String KEY_MENU_OWNER_ID      = "_id_owner";
    public static final String KEY_MENU_ORG_ID        = "_id_org";
    public static final String KEY_MENU_GROUP_BITS    = "_bits_group";
    public static final String KEY_MENU_PERMS_BITS    = "_bits_perms";
    public static final String KEY_MENU_STATUS_BITS   = "_bits_status";
    public static final String KEY_MENU_NAME          = "name";
    public static final String KEY_MENU_DESCRIPTION   = "description";
    public static final String KEY_MENU_CREATED_DATE  = "created_date";
    public static final String KEY_MENU_MODIFIED_DATE = "modified_date";
    public static final String KEY_MENU_SYNCED_DATE   = "synced_date";

    // MENU table - create statement
    public static final String CREATE_TABLE_MENU = "CREATE TABLE " + TABLE_MENU + "("
            + KEY_MENU_ID +" INTEGER NOT NULL, "
            + KEY_MENU_SERVER_ID +" INTEGER DEFAULT NULL, "
            + KEY_MENU_PARENT_ID +" INTEGER NOT NULL DEFAULT 0, "
            + KEY_MENU_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_MENU_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_MENU_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_MENU_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_MENU_STATUS_BITS + " INTEGER NOT NULL DEFAULT 3, "
            + KEY_MENU_NAME + " TEXT, "
            + KEY_MENU_DESCRIPTION + " TEXT, "
            + KEY_MENU_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MENU_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MENU_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + "PRIMARY KEY (" + KEY_MENU_ID + "));";

    // PRIVILEGE Table - column names
    public static final String KEY_ROLE_FK_ID              = "_id_role_fk";
    public static final String KEY_PRIVILEGE_SERVER_ID     = "_id_server";
    public static final String KEY_PRIVILEGE_OWNER_ID      = "_id_owner";
    public static final String KEY_PRIVILEGE_ORG_ID        = "_id_org";
    public static final String KEY_PRIVILEGE_GROUP_BITS    = "_bits_group";
    public static final String KEY_PRIVILEGE_PERMS_BITS    = "_bits_perms";
    public static final String KEY_PRIVILEGE_STATUS_BITS   = "_bits_status";
    public static final String KEY_PRIVILEGE_NAME          = "name";
    public static final String KEY_PRIVILEGE_DESCRIPTION   = "description";
    public static final String KEY_PRIVILEGE_CREATED_DATE  = "created_date";
    public static final String KEY_PRIVILEGE_MODIFIED_DATE = "modified_date";
    public static final String KEY_PRIVILEGE_SYNCED_DATE   = "synced_date";

    // PRIVILEGE table - create statement
    public static final String CREATE_TABLE_PRIVILEGE = "CREATE TABLE " + TABLE_PRIVILEGE + "("
            + KEY_ORGANIZATION_FK_ID +" INTEGER NOT NULL, "
            + KEY_ROLE_FK_ID +" INTEGER NOT NULL, "
            + KEY_PRIVILEGE_SERVER_ID +" INTEGER DEFAULT NULL, "
            + KEY_PRIVILEGE_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PRIVILEGE_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PRIVILEGE_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PRIVILEGE_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_PRIVILEGE_STATUS_BITS + " INTEGER NOT NULL DEFAULT 3, "
            + KEY_PRIVILEGE_NAME + " TEXT, "
            + KEY_PRIVILEGE_DESCRIPTION + " TEXT, "
            + KEY_PRIVILEGE_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_PRIVILEGE_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_PRIVILEGE_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + "PRIMARY KEY (" + KEY_ORGANIZATION_FK_ID + ", "+ KEY_ROLE_FK_ID +"),"
            + "FOREIGN KEY (" + KEY_ORGANIZATION_FK_ID + ", "+ KEY_ROLE_FK_ID + ") "
            + "REFERENCES " + TABLE_ROLE +"(" + KEY_ORGANIZATION_FK_ID +", "+ KEY_ROLE_ID +") ON DELETE CASCADE );";

    // ENTITY Table - column names
    public static final String KEY_ENTITY_ID            = "_id";
    public static final String KEY_ENTITY_SERVER_ID     = "_id_server";
    public static final String KEY_ENTITY_TYPE_ID       = "_id_type";
    public static final String KEY_ENTITY_OWNER_ID      = "_id_owner";
    public static final String KEY_ENTITY_ORG_ID        = "_id_org";
    public static final String KEY_ENTITY_GROUP_BITS    = "_bits_group";
    public static final String KEY_ENTITY_PERMS_BITS    = "_bits_perms";
    public static final String KEY_ENTITY_STATUS_BITS   = "_bits_status";
    public static final String KEY_ENTITY_NAME          = "name";
    public static final String KEY_ENTITY_DESCRIPTION   = "description";
    public static final String KEY_ENTITY_CREATED_DATE  = "created_date";
    public static final String KEY_ENTITY_MODIFIED_DATE = "modified_date";
    public static final String KEY_ENTITY_SYNCED_DATE   = "synced_date";

    // ENTITY table - create statement
    public static final String CREATE_TABLE_ENTITY = "CREATE TABLE " + TABLE_ENTITY + "("
            + KEY_ENTITY_ID +" INTEGER NOT NULL, "
            + KEY_ENTITY_TYPE_ID +" INTEGER NOT NULL, "
            + KEY_ENTITY_SERVER_ID +" INTEGER DEFAULT NULL, "
            + KEY_ENTITY_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ENTITY_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ENTITY_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ENTITY_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_ENTITY_STATUS_BITS + " INTEGER NOT NULL DEFAULT 3, "
            + KEY_ENTITY_NAME + " TEXT, "
            + KEY_ENTITY_DESCRIPTION + " TEXT, "
            + KEY_ENTITY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_ENTITY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_ENTITY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + "PRIMARY KEY (" + KEY_ENTITY_ID  +","+ KEY_ENTITY_TYPE_ID +"));";

    // OPERATION Table - column names
    public static final String KEY_OPERATION_ID            = "_id";
    public static final String KEY_OPERATION_SERVER_ID     = "_id_server";
    public static final String KEY_OPERATION_OWNER_ID      = "_id_owner";
    public static final String KEY_OPERATION_ORG_ID        = "_id_org";
    public static final String KEY_OPERATION_GROUP_BITS    = "_bits_group";
    public static final String KEY_OPERATION_PERMS_BITS    = "_bits_perms";
    public static final String KEY_OPERATION_STATUS_BITS   = "_bits_status";
    public static final String KEY_OPERATION_NAME          = "name";
    public static final String KEY_OPERATION_DESCRIPTION   = "description";
    public static final String KEY_OPERATION_CREATED_DATE  = "created_date";
    public static final String KEY_OPERATION_MODIFIED_DATE = "modified_date";
    public static final String KEY_OPERATION_SYNCED_DATE   = "synced_date";

    // OPERATION table - create statement
    public static final String CREATE_TABLE_OPERATION = "CREATE TABLE " + TABLE_OPERATION + "("
            + KEY_OPERATION_ID +" INTEGER NOT NULL, "
            + KEY_OPERATION_SERVER_ID +" INTEGER DEFAULT NULL, "
            + KEY_OPERATION_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_OPERATION_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_OPERATION_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_OPERATION_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_OPERATION_STATUS_BITS + " INTEGER NOT NULL DEFAULT 3, "
            + KEY_OPERATION_NAME + " TEXT, "
            + KEY_OPERATION_DESCRIPTION + " TEXT, "
            + KEY_OPERATION_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_OPERATION_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_OPERATION_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + "PRIMARY KEY (" + KEY_OPERATION_ID +"));";

    // STATUS table - column names
    public static final String KEY_STATUS_ID            = "_id";
    public static final String KEY_STATUS_SERVER_ID     = "_id_server";
    public static final String KEY_STATUS_OWNER_ID      = "_id_owner";
    public static final String KEY_STATUS_ORG_ID        = "_id_org";
    public static final String KEY_STATUS_GROUP_BITS    = "_bits_group";
    public static final String KEY_STATUS_PERMS_BITS    = "_bits_perms";
    public static final String KEY_STATUS_STATUS_BITS   = "_bits_status";
    public static final String KEY_STATUS_NAME          = "name";
    public static final String KEY_STATUS_DESCRIPTION   = "description";
    public static final String KEY_STATUS_CREATED_DATE  = "created_date";
    public static final String KEY_STATUS_MODIFIED_DATE = "modified_date";
    public static final String KEY_STATUS_SYNCED_DATE   = "synced_date";

    // STATUS table - create statement
    public static final String CREATE_TABLE_STATUS = "CREATE TABLE " + TABLE_STATUS + "("
            + KEY_STATUS_ID +" INTEGER NOT NULL, "
            + KEY_STATUS_SERVER_ID +" INTEGER DEFAULT NULL, "
            + KEY_STATUS_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_STATUS_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_STATUS_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_STATUS_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_STATUS_STATUS_BITS + " INTEGER NOT NULL DEFAULT 3, "
            + KEY_STATUS_NAME + " TEXT, "
            + KEY_STATUS_DESCRIPTION + " TEXT, "
            + KEY_STATUS_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_STATUS_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_STATUS_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + "PRIMARY KEY (" + KEY_STATUS_ID + "));";

    // USER_ROLE Table - column names
    //public static final String KEY_ROLE_FK_ID              = "_id_role_fk";
    public static final String KEY_USER_SERVER_FK_ID       = "_id_user_server";
    public static final String KEY_ROLE_SERVER_FK_ID       = "_id_role_server";
    public static final String KEY_USER_ROLE_OWNER_ID      = "_id_owner";
    public static final String KEY_USER_ROLE_ORG_ID        = "_id_org";
    public static final String KEY_USER_ROLE_GROUP_BITS    = "_bits_group";
    public static final String KEY_USER_ROLE_PERMS_BITS    = "_bits_perms";
    public static final String KEY_USER_ROLE_STATUS_BITS   = "_bits_status";
    public static final String KEY_USER_ROLE_CREATED_DATE  = "created_date";
    public static final String KEY_USER_ROLE_MODIFIED_DATE = "modified_date";
    public static final String KEY_USER_ROLE_SYNCED_DATE   = "synced_date";

    // USER_ROLE table - create statement
    public static final String CREATE_TABLE_USER_ROLE = "CREATE TABLE " + TABLE_USER_ROLE + "("
            + KEY_USER_FK_ID + " INTEGER NOT NULL, "
            + KEY_ROLE_FK_ID + " INTEGER NOT NULL, "
            + KEY_ORGANIZATION_FK_ID + " INTEGER NOT NULL, "
            + KEY_USER_SERVER_FK_ID + " INTEGER DEFAULT NULL, "
            + KEY_ROLE_SERVER_FK_ID + " INTEGER DEFAULT NULL, "
            + KEY_USER_ROLE_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_USER_ROLE_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_USER_ROLE_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_USER_ROLE_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_USER_ROLE_STATUS_BITS + " INTEGER NOT NULL DEFAULT 3, "
            + KEY_USER_ROLE_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_USER_ROLE_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_USER_ROLE_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + "PRIMARY KEY (" + KEY_USER_FK_ID +", "+ KEY_ROLE_FK_ID +", "+ KEY_ORGANIZATION_FK_ID +"), "
            + "FOREIGN KEY (" + KEY_USER_FK_ID + ") "
            + "REFERENCES " + TABLE_USER + "("+ KEY_USER_ID + ") ON DELETE CASCADE,"
            + "FOREIGN KEY (" + KEY_ROLE_FK_ID + ", "+ KEY_ORGANIZATION_FK_ID +") "
            + "REFERENCES " + TABLE_ROLE + "("+ KEY_ROLE_ID + ", "+ KEY_ORGANIZATION_FK_ID +") ON DELETE CASCADE);";

    // SESSION_ROLE Table - column names
    public static final String KEY_SESSION_FK_ID              = "_id_session_fk";
    public static final String KEY_SESSION_SERVER_FK_ID       = "_id_session_server";
    public static final String KEY_SESSION_ROLE_OWNER_ID      = "_id_owner";
    public static final String KEY_SESSION_ROLE_ORG_ID        = "_id_org";
    public static final String KEY_SESSION_ROLE_GROUP_BITS    = "_bits_group";
    public static final String KEY_SESSION_ROLE_PERMS_BITS    = "_bits_perms";
    public static final String KEY_SESSION_ROLE_STATUS_BITS   = "_bits_status";
    public static final String KEY_SESSION_ROLE_CREATED_DATE  = "created_date";
    public static final String KEY_SESSION_ROLE_MODIFIED_DATE = "modified_date";
    public static final String KEY_SESSION_ROLE_SYNCED_DATE   = "synced_date";

    // SESSION_ROLE table - create statement
    public static final String CREATE_TABLE_SESSION_ROLE = "CREATE TABLE " + TABLE_SESSION_ROLE + "("
            + KEY_SESSION_FK_ID + " INTEGER NOT NULL, "
            + KEY_ROLE_FK_ID + " INTEGER NOT NULL, "
            + KEY_ORGANIZATION_FK_ID + " INTEGER NOT NULL, "
            + KEY_SESSION_SERVER_FK_ID + " INTEGER DEFAULT NULL, "
            + KEY_ROLE_SERVER_FK_ID + " INTEGER DEFAULT NULL, "
            + KEY_SESSION_ROLE_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_SESSION_ROLE_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_SESSION_ROLE_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_SESSION_ROLE_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_SESSION_ROLE_STATUS_BITS + " INTEGER NOT NULL DEFAULT 3, "
            + KEY_SESSION_ROLE_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SESSION_ROLE_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SESSION_ROLE_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + "PRIMARY KEY (" + KEY_SESSION_FK_ID +", "+ KEY_ROLE_FK_ID +", "+ KEY_ORGANIZATION_FK_ID +"), "
            + "FOREIGN KEY (" + KEY_SESSION_FK_ID + ") "
            + "REFERENCES " + TABLE_SESSION + "("+ KEY_SESSION_ID + ") ON DELETE CASCADE,"
            + "FOREIGN KEY (" + KEY_ROLE_FK_ID + ", "+ KEY_ORGANIZATION_FK_ID +") "
            + "REFERENCES " + TABLE_ROLE + "("+ KEY_ROLE_ID + ", "+ KEY_ORGANIZATION_FK_ID +") ON DELETE CASCADE);";

    // MENU_ROLE Table - column names
    public static final String KEY_MENU_FK_ID              = "_id_menu_fk";
    public static final String KEY_MENU_SERVER_FK_ID       = "_id_menu_server";
    public static final String KEY_MENU_ROLE_OWNER_ID      = "_id_owner";
    public static final String KEY_MENU_ROLE_ORG_ID        = "_id_org";
    public static final String KEY_MENU_ROLE_GROUP_BITS    = "_bits_group";
    public static final String KEY_MENU_ROLE_PERMS_BITS    = "_bits_perms";
    public static final String KEY_MENU_ROLE_STATUS_BITS   = "_bits_status";
    public static final String KEY_MENU_ROLE_CREATED_DATE  = "created_date";
    public static final String KEY_MENU_ROLE_MODIFIED_DATE = "modified_date";
    public static final String KEY_MENU_ROLE_SYNCED_DATE   = "synced_date";

    // MENU_ROLE table - create statement
    public static final String CREATE_TABLE_MENU_ROLE = "CREATE TABLE " + TABLE_MENU_ROLE + "("
            + KEY_MENU_FK_ID + " INTEGER NOT NULL, "
            + KEY_ROLE_FK_ID + " INTEGER NOT NULL, "
            + KEY_ORGANIZATION_FK_ID + " INTEGER NOT NULL, "
            + KEY_MENU_SERVER_FK_ID + " INTEGER DEFAULT NULL, "
            + KEY_ROLE_SERVER_FK_ID + " INTEGER DEFAULT NULL, "
            + KEY_MENU_ROLE_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_MENU_ROLE_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_MENU_ROLE_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_MENU_ROLE_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_MENU_ROLE_STATUS_BITS + " INTEGER NOT NULL DEFAULT 3, "
            + KEY_MENU_ROLE_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MENU_ROLE_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MENU_ROLE_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + "PRIMARY KEY (" + KEY_MENU_FK_ID +", "+ KEY_ROLE_FK_ID +", "+ KEY_ORGANIZATION_FK_ID +"), "
            + "FOREIGN KEY (" + KEY_MENU_FK_ID + ") "
            + "REFERENCES " + TABLE_MENU + "("+ KEY_MENU_ID + ") ON DELETE CASCADE,"
            + "FOREIGN KEY (" + KEY_ROLE_FK_ID + ", "+ KEY_ORGANIZATION_FK_ID +") "
            + "REFERENCES " + TABLE_ROLE + "("+ KEY_ROLE_ID + ", "+ KEY_ORGANIZATION_FK_ID +") ON DELETE CASCADE);";
/*
    // PRIVILEGE_ROLE Table - column names
    public static final String KEY_PRIVILEGE_FK_ID              = "_id_privilege_fk";
    public static final String KEY_PRIVILEGE_SERVER_FK_ID       = "_id_privilege_server";
    public static final String KEY_PRIVILEGE_ROLE_OWNER_ID      = "_id_owner";
    public static final String KEY_PRIVILEGE_ROLE_ORG_ID        = "_id_org";
    public static final String KEY_PRIVILEGE_ROLE_GROUP_BITS    = "_bits_group";
    public static final String KEY_PRIVILEGE_ROLE_PERMS_BITS    = "_bits_perms";
    public static final String KEY_PRIVILEGE_ROLE_STATUS_BITS   = "_bits_status";
    public static final String KEY_PRIVILEGE_ROLE_CREATED_DATE  = "created_date";
    public static final String KEY_PRIVILEGE_ROLE_MODIFIED_DATE = "modified_date";
    public static final String KEY_PRIVILEGE_ROLE_SYNCED_DATE   = "synced_date";

    // PRIVILEGE_ROLE table - create statement
    public static final String CREATE_TABLE_PRIVILEGE_ROLE = "CREATE TABLE " + TABLE_PRIVILEGE_ROLE + "("
            + KEY_PRIVILEGE_FK_ID + " INTEGER NOT NULL, "
            + KEY_ROLE_FK_ID + " INTEGER NOT NULL, "
            + KEY_ORGANIZATION_FK_ID + " INTEGER NOT NULL, "
            + KEY_PRIVILEGE_SERVER_FK_ID + " INTEGER DEFAULT NULL, "
            + KEY_ROLE_SERVER_FK_ID + " INTEGER DEFAULT NULL, "
            + KEY_PRIVILEGE_ROLE_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PRIVILEGE_ROLE_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PRIVILEGE_ROLE_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PRIVILEGE_ROLE_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_PRIVILEGE_ROLE_STATUS_BITS + " INTEGER NOT NULL DEFAULT 3, "
            + KEY_PRIVILEGE_ROLE_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_PRIVILEGE_ROLE_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_PRIVILEGE_ROLE_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + "PRIMARY KEY (" + KEY_PRIVILEGE_FK_ID +", "+ KEY_ROLE_FK_ID +", "+ KEY_ORGANIZATION_FK_ID +"), "
            + "FOREIGN KEY (" + KEY_PRIVILEGE_FK_ID + ") "
            + "REFERENCES " + TABLE_PRIVILEGE + "("+ KEY_PRIVILEGE_ID + ") ON DELETE CASCADE,"
            + "FOREIGN KEY (" + KEY_ROLE_FK_ID + ", "+ KEY_ORGANIZATION_FK_ID +") "
            + "REFERENCES " + TABLE_ROLE + "("+ KEY_ROLE_ID + ", "+ KEY_ORGANIZATION_FK_ID +") ON DELETE CASCADE);";
*/

    // PERMISSION table - column names
    public static final String KEY_PRIVILEGE_FK_ID          = "_id_privilege_fk";
    public static final String KEY_PRIVILEGE_SERVER_FK_ID   = "_id_privilege_server";
    public static final String KEY_ENTITY_FK_ID             = "_id_entity_fk";
    public static final String KEY_ENTITY_TYPE_FK_ID        = "_id_entity_type_fk";
    public static final String KEY_OPERATION_FK_ID          = "_id_operation_fk";
    public static final String KEY_STATUS_FK_ID             = "_id_status_fk";
    public static final String KEY_ENTITY_SERVER_FK_ID      = "_id_entity_server";
    public static final String KEY_ENTITY_TYPE_SERVER_FK_ID = "_id_entity_type_server";
    public static final String KEY_OPERATION_SERVER_FK_ID   = "_id_operation_server";
    public static final String KEY_STATUS_SERVER_FK_ID      = "_id_status_server";
    public static final String KEY_PERMISSION_OWNER_ID      = "_id_owner";
    public static final String KEY_PERMISSION_ORG_ID        = "_id_org";
    public static final String KEY_PERMISSION_GROUP_BITS    = "_bits_group";
    public static final String KEY_PERMISSION_PERMS_BITS    = "_bits_perms";
    public static final String KEY_PERMISSION_STATUS_BITS   = "_bits_status";
    public static final String KEY_PERMISSION_CREATED_DATE  = "created_date";
    public static final String KEY_PERMISSION_MODIFIED_DATE = "modified_date";
    public static final String KEY_PERMISSION_SYNCED_DATE   = "synced_date";

    // PERMISSION table - create statement
    public static final String CREATE_TABLE_PERMISSION = "CREATE TABLE " + TABLE_PERMISSION + "("
            + KEY_ORGANIZATION_FK_ID +" INTEGER NOT NULL, "
            + KEY_PRIVILEGE_FK_ID +" INTEGER NOT NULL, "
            + KEY_ENTITY_FK_ID +" INTEGER NOT NULL, "
            + KEY_ENTITY_TYPE_FK_ID +" INTEGER NOT NULL, "
            + KEY_OPERATION_FK_ID +" INTEGER NOT NULL, "
            + KEY_STATUS_FK_ID +" INTEGER NOT NULL, "
            + KEY_PRIVILEGE_SERVER_FK_ID + " INTEGER DEFAULT NULL, "
            + KEY_ENTITY_SERVER_FK_ID + " INTEGER DEFAULT NULL, "
            + KEY_ENTITY_TYPE_SERVER_FK_ID +" INTEGER DEFAULT NULL, "
            + KEY_OPERATION_SERVER_FK_ID + " INTEGER DEFAULT NULL, "
            + KEY_STATUS_SERVER_FK_ID + " INTEGER DEFAULT NULL, "
            + KEY_PERMISSION_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PERMISSION_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PERMISSION_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PERMISSION_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_PERMISSION_STATUS_BITS + " INTEGER NOT NULL DEFAULT 3, "
            + KEY_PERMISSION_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_PERMISSION_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_PERMISSION_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + "PRIMARY KEY (" + KEY_ORGANIZATION_FK_ID +", "+ KEY_PRIVILEGE_FK_ID +", "
            + KEY_ENTITY_FK_ID +", " + KEY_ENTITY_TYPE_FK_ID +", " + KEY_OPERATION_FK_ID +", "
            + KEY_STATUS_FK_ID +"), "
            + "FOREIGN KEY (" + KEY_ORGANIZATION_FK_ID +", "+ KEY_PRIVILEGE_FK_ID + ") "
            + "REFERENCES " + TABLE_PRIVILEGE + "("+ KEY_ORGANIZATION_FK_ID +", "+ KEY_ROLE_FK_ID + ") ON DELETE CASCADE, "
            + "FOREIGN KEY (" + KEY_ENTITY_FK_ID+", "+ KEY_ENTITY_TYPE_FK_ID +") "
            + "REFERENCES " + TABLE_ENTITY + "("+ KEY_ENTITY_ID +", "+ KEY_ENTITY_TYPE_ID +") ON DELETE CASCADE, "
            + "FOREIGN KEY (" + KEY_OPERATION_FK_ID +") "
            + "REFERENCES " + TABLE_OPERATION + "("+ KEY_OPERATION_ID +") ON DELETE CASCADE, "
            + "FOREIGN KEY (" + KEY_STATUS_FK_ID + ") "
            + "REFERENCES " + TABLE_STATUS + "("+ KEY_STATUS_ID + ") ON DELETE CASCADE);";

    //########################### End of Bitwise Role Based Access Control ###########################



    //########################### START PLANNING MODULE ###########################

    public static final String KEY_ID            = "_id";
    public static final String KEY_OWNER_ID      = "_id_owner";
    public static final String KEY_ORG_ID        = "_id_org";
    public static final String KEY_SERVER_ID     = "_id_server";
    public static final String KEY_GROUP_BITS    = "_bits_group";
    public static final String KEY_PERMS_BITS    = "_bits_perms";
    public static final String KEY_STATUS_BITS   = "_bits_status";
    public static final String KEY_CREATED_DATE  = "created_date";
    public static final String KEY_MODIFIED_DATE = "modified_date";
    public static final String KEY_SYNCED_DATE   = "synced_date";

    public static final String KEY_START_DATE    = "start_date";
    public static final String KEY_END_DATE      = "end_date";

    public static final String KEY_NAME          = "name";
    public static final String KEY_DESCRIPTION   = "description";

    public static final String KEY_PARENT_FK_ID = "_id_parent_fk";
    public static final String KEY_CHILD_FK_ID  = "_id_child_fk";

    public static final String KEY_LOGFRAME_FK_ID  = "_id_logframe_fk";
    public static final String KEY_IMPACT_FK_ID  = "_id_impact_fk";
    public static final String KEY_OUTCOME_FK_ID  = "_id_outcome_fk";
    public static final String KEY_OUTPUT_FK_ID  = "_id_output_fk";
    public static final String KEY_ACTIVITY_FK_ID  = "_id_activity_fk";
    public static final String KEY_PRECEDING_ACTIVITY_FK_ID  = "_id_preceding_activity_fk";


    // tblLOGFRAME table - create statement
    public static final String CREATE_TABLE_tbLOGFRAME = "CREATE TABLE "+ TABLE_tblLOGFRAME + "("
            + KEY_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_SERVER_ID +" INTEGER NOT NULL, "
            + KEY_OWNER_ID +" INTEGER NOT NULL, "
            + KEY_ORG_ID + " INTEGER NOT NULL, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL, "
            + KEY_NAME + " TEXT NOT NULL, "
            + KEY_DESCRIPTION + " TEXT NULL, "
            + KEY_START_DATE + " DATE NOT NULL, "
            + KEY_END_DATE + "DATE NOT NULL, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + "PRIMARY KEY ("+ KEY_ID +"))";

    // tblLOGFRAMETREE table - create statement
    public static final String CREATE_TABLE_tblLOGFRAMETREE = "CREATE TABLE "+ TABLE_tblLOGFRAMETREE + "("
            + KEY_PARENT_FK_ID +" INTEGER NOT NULL, "
            + KEY_CHILD_FK_ID +" INTEGER NOT NULL, "
            + KEY_SERVER_ID +" INTEGER NOT NULL, "
            + KEY_OWNER_ID +" INTEGER NOT NULL, "
            + KEY_ORG_ID + " INTEGER NOT NULL, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL, "
            + KEY_NAME + " TEXT NOT NULL, "
            + KEY_DESCRIPTION + " TEXT NULL, "
            + KEY_START_DATE + " DATE NOT NULL, "
            + KEY_END_DATE + "DATE NOT NULL, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + " PRIMARY KEY (" + KEY_PARENT_FK_ID + "," + KEY_CHILD_FK_ID +" ), "
            + " FOREIGN KEY (" + KEY_PARENT_FK_ID + ") "
            + " REFERENCES " + TABLE_tblLOGFRAME +" ("+ KEY_ID + ") ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_CHILD_FK_ID + ")"
            + " REFERENCES " + TABLE_tblLOGFRAME +" ("+ KEY_ID + ") ON DELETE CASCADE ON UPDATE CASCADE);";

    // tblIMPACT table - create statement
    public static final String CREATE_TABLE_tblIMPACT = "CREATE TABLE "+ TABLE_tblIMPACT + "("
            + KEY_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_CHILD_FK_ID +" INTEGER NOT NULL, "
            + KEY_LOGFRAME_FK_ID +" INTEGER NOT NULL, "
            + KEY_SERVER_ID +" INTEGER NOT NULL, "
            + KEY_OWNER_ID +" INTEGER NOT NULL, "
            + KEY_ORG_ID + " INTEGER NOT NULL, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL, "
            + KEY_NAME + " TEXT NOT NULL, "
            + KEY_DESCRIPTION + " TEXT NULL, "
            + KEY_START_DATE + " DATE NOT NULL, "
            + KEY_END_DATE + "DATE NOT NULL, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + " PRIMARY KEY (" + KEY_ID +" ), "
            + " FOREIGN KEY (" + KEY_CHILD_FK_ID + ") "
            + " REFERENCES " + TABLE_tblIMPACT +" ("+ KEY_ID + ") ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_LOGFRAME_FK_ID + ")"
            + " REFERENCES " + TABLE_tblLOGFRAME +" ("+ KEY_ID + ") ON DELETE CASCADE ON UPDATE CASCADE);";

    // tblOUTCOME table - create statement
    public static final String CREATE_TABLE_tblOUTCOME = "CREATE TABLE "+ TABLE_tblOUTCOME + "("
            + KEY_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_CHILD_FK_ID +" INTEGER NOT NULL, "
            + KEY_IMPACT_FK_ID +" INTEGER NOT NULL, "
            + KEY_LOGFRAME_FK_ID +" INTEGER NOT NULL, "
            + KEY_SERVER_ID +" INTEGER NOT NULL, "
            + KEY_OWNER_ID +" INTEGER NOT NULL, "
            + KEY_ORG_ID + " INTEGER NOT NULL, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL, "
            + KEY_NAME + " TEXT NOT NULL, "
            + KEY_DESCRIPTION + " TEXT NULL, "
            + KEY_START_DATE + " DATE NOT NULL, "
            + KEY_END_DATE + "DATE NOT NULL, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + " PRIMARY KEY (" + KEY_ID +" ), "
            + " FOREIGN KEY (" + KEY_CHILD_FK_ID + ") "
            + " REFERENCES " + TABLE_tblOUTCOME +" ("+ KEY_ID + ") ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_IMPACT_FK_ID + ") "
            + " REFERENCES " + TABLE_tblIMPACT +" ("+ KEY_ID + ") ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_LOGFRAME_FK_ID + ")"
            + " REFERENCES " + TABLE_tblLOGFRAME +" ("+ KEY_ID + ") ON DELETE CASCADE ON UPDATE CASCADE);";

    // tblOUTPUT table - create statement
    public static final String CREATE_TABLE_tblOUTPUT = "CREATE TABLE "+ TABLE_tblOUTPUT + "("
            + KEY_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_CHILD_FK_ID +" INTEGER NOT NULL, "
            + KEY_OUTCOME_FK_ID +" INTEGER NOT NULL, "
            + KEY_LOGFRAME_FK_ID +" INTEGER NOT NULL, "
            + KEY_SERVER_ID +" INTEGER NOT NULL, "
            + KEY_OWNER_ID +" INTEGER NOT NULL, "
            + KEY_ORG_ID + " INTEGER NOT NULL, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL, "
            + KEY_NAME + " TEXT NOT NULL, "
            + KEY_DESCRIPTION + " TEXT NULL, "
            + KEY_START_DATE + " DATE NOT NULL, "
            + KEY_END_DATE + "DATE NOT NULL, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + " PRIMARY KEY (" + KEY_ID +" ), "
            + " FOREIGN KEY (" + KEY_CHILD_FK_ID + ") "
            + " REFERENCES " + TABLE_tblOUTPUT +" ("+ KEY_ID + ") ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_OUTCOME_FK_ID + ") "
            + " REFERENCES " + TABLE_tblOUTCOME +" ("+ KEY_ID + ") ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_LOGFRAME_FK_ID + ")"
            + " REFERENCES " + TABLE_tblLOGFRAME +" ("+ KEY_ID + ") ON DELETE CASCADE ON UPDATE CASCADE);";

    // tblACTIVITY table - create statement
    public static final String CREATE_TABLE_tblACTIVITY = "CREATE TABLE "+ TABLE_tblACTIVITY + "("
            + KEY_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_CHILD_FK_ID +" INTEGER NOT NULL, "
            + KEY_OUTPUT_FK_ID +" INTEGER NOT NULL, "
            + KEY_LOGFRAME_FK_ID +" INTEGER NOT NULL, "
            + KEY_SERVER_ID +" INTEGER NOT NULL, "
            + KEY_OWNER_ID +" INTEGER NOT NULL, "
            + KEY_ORG_ID + " INTEGER NOT NULL, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL, "
            + KEY_NAME + " TEXT NOT NULL, "
            + KEY_DESCRIPTION + " TEXT NULL, "
            + KEY_START_DATE + " DATE NOT NULL, "
            + KEY_END_DATE + "DATE NOT NULL, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + " PRIMARY KEY (" + KEY_ID +" ), "
            + " FOREIGN KEY (" + KEY_CHILD_FK_ID + ") "
            + " REFERENCES " + TABLE_tblACTIVITY +" ("+ KEY_ID + ") ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_OUTPUT_FK_ID + ") "
            + " REFERENCES " + TABLE_tblOUTPUT +" ("+ KEY_ID + ") ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_LOGFRAME_FK_ID + ")"
            + " REFERENCES " + TABLE_tblLOGFRAME +" ("+ KEY_ID + ") ON DELETE CASCADE ON UPDATE CASCADE);";

    // tblINPUT table - create statement
    public static final String CREATE_TABLE_tblPRECEDINGACTIVITY = "CREATE TABLE "+ TABLE_tblPRECEDINGACTIVITY + "("
            + KEY_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_ACTIVITY_FK_ID +" INTEGER NOT NULL, "
            + KEY_PRECEDING_ACTIVITY_FK_ID +" INTEGER NOT NULL, "
            + KEY_SERVER_ID +" INTEGER NOT NULL, "
            + KEY_OWNER_ID +" INTEGER NOT NULL, "
            + KEY_ORG_ID + " INTEGER NOT NULL, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + " PRIMARY KEY (" + KEY_ID +" ), "
            + " FOREIGN KEY (" + KEY_ACTIVITY_FK_ID + ") "
            + " REFERENCES " + TABLE_tblACTIVITY +" ("+ KEY_ID + ") ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_PRECEDING_ACTIVITY_FK_ID + ")"
            + " REFERENCES " + TABLE_tblACTIVITY +" ("+ KEY_ID + ") ON DELETE CASCADE ON UPDATE CASCADE);";

    // tblINPUT table - create statement
    public static final String CREATE_TABLE_tblINPUT = "CREATE TABLE "+ TABLE_tblINPUT + "("
            + KEY_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_ACTIVITY_FK_ID +" INTEGER NOT NULL, "
            + KEY_LOGFRAME_FK_ID +" INTEGER NOT NULL, "
            + KEY_SERVER_ID +" INTEGER NOT NULL, "
            + KEY_OWNER_ID +" INTEGER NOT NULL, "
            + KEY_ORG_ID + " INTEGER NOT NULL, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL, "
            + KEY_NAME + " TEXT NOT NULL, "
            + KEY_DESCRIPTION + " TEXT NULL, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + " PRIMARY KEY (" + KEY_ID +" ), "
            + " FOREIGN KEY (" + KEY_ACTIVITY_FK_ID + ") "
            + " REFERENCES " + TABLE_tblACTIVITY +" ("+ KEY_ID + ") ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_LOGFRAME_FK_ID + ")"
            + " REFERENCES " + TABLE_tblLOGFRAME +" ("+ KEY_ID + ") ON DELETE CASCADE ON UPDATE CASCADE);";

    //########################### END PLANNING MODULE ###########################

    public cSQLDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;

    }

    @SuppressLint("LongLogTag")
    @Override
    public void onCreate(SQLiteDatabase db) {
        // create all tables
        String[] create_tables = new String[]{
                CREATE_TABLE_tbLOGFRAME, CREATE_TABLE_tblLOGFRAMETREE, CREATE_TABLE_tblIMPACT,
                CREATE_TABLE_tblOUTCOME, CREATE_TABLE_tblOUTPUT, CREATE_TABLE_tblACTIVITY,
                CREATE_TABLE_tblPRECEDINGACTIVITY, CREATE_TABLE_tblINPUT
        };

        db.beginTransaction();
        try {
            for (String create_table : create_tables){
                db.execSQL(create_table);
            }
        }catch (SQLException e){
            Log.e("Error Creating M+E Tables", e.toString());
        }finally {
            db.endTransaction();
        }

        //createTables(db);
    }
 
    @SuppressLint("LongLogTag")
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // drop all tables
        String[] tables = new String[]{
                TABLE_tblLOGFRAME, TABLE_tblLOGFRAMETREE, TABLE_tblIMPACT,
                TABLE_tblOUTCOME, TABLE_tblOUTPUT, TABLE_tblACTIVITY,
                TABLE_tblPRECEDINGACTIVITY, TABLE_tblINPUT
        };

        db.beginTransaction();
        try {
            for (String table : tables){
                db.execSQL("DROP TABLE IF EXISTS " + table);
            }
        }catch (SQLException e){
            Log.e("Error Updating M+E Tables", e.toString());
        }finally {
            db.endTransaction();
        }
        //dropTables(db);

        // create new tables
        onCreate(db);
        // populate database from excel
        //populateBRBAC();
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            // Enable foreign key constraints
            db.execSQL("PRAGMA foreign_keys=ON;");
        }
    }

    public void createTables(SQLiteDatabase db){
        // user access control tables
        db.execSQL(CREATE_TABLE_ADDRESS);
        db.execSQL(CREATE_TABLE_ORGANIZATION);
        db.execSQL(CREATE_TABLE_VALUE);
        db.execSQL(CREATE_TABLE_USER);
        db.execSQL(CREATE_TABLE_SESSION);
        db.execSQL(CREATE_TABLE_ROLE);
        db.execSQL(CREATE_TABLE_MENU);
        db.execSQL(CREATE_TABLE_PRIVILEGE);
        db.execSQL(CREATE_TABLE_ENTITY);
        db.execSQL(CREATE_TABLE_OPERATION);
        db.execSQL(CREATE_TABLE_STATUS);
        db.execSQL(CREATE_TABLE_USER_ROLE);
        db.execSQL(CREATE_TABLE_SESSION_ROLE);
        db.execSQL(CREATE_TABLE_MENU_ROLE);
        //db.execSQL(CREATE_TABLE_PRIVILEGE_ROLE);
        db.execSQL(CREATE_TABLE_PERMISSION);

        /*
        // dimension tables
        db.execSQL(CREATE_TABLE_DATE);
        db.execSQL(CREATE_TABLE_UNIT);
        db.execSQL(CREATE_TABLE_ACTIVITYLOG);
        db.execSQL(CREATE_TABLE_SCALE);
        db.execSQL(CREATE_TABLE_CATEGORY);
        db.execSQL(CREATE_TABLE_RISKMAP);
        db.execSQL(CREATE_TABLE_RISKLIKELIHOOD);
        db.execSQL(CREATE_TABLE_RISKIMPACT);

        // relation tables
        db.execSQL(CREATE_TABLE_OVERALLAIM);
        db.execSQL(CREATE_TABLE_SPECIFICAIM);
        db.execSQL(CREATE_TABLE_PROJECT);
        db.execSQL(CREATE_TABLE_OUTCOME);
        db.execSQL(CREATE_TABLE_OBJECTIVE);
        db.execSQL(CREATE_TABLE_OUTPUT);
        db.execSQL(CREATE_TABLE_ACTIVITY);
        db.execSQL(CREATE_TABLE_INDICATOR);
        db.execSQL(CREATE_TABLE_QUALITATIVEINDICATOR);
        db.execSQL(CREATE_TABLE_QUANTITATIVEINDICATOR);
        db.execSQL(CREATE_TABLE_HYBRIDINDICATOR);
        db.execSQL(CREATE_TABLE_QUALITATIVETARGET);
        db.execSQL(CREATE_TABLE_QUANTITATIVETARGET);
        db.execSQL(CREATE_TABLE_HYBRIDTARGET);
        db.execSQL(CREATE_TABLE_MOV);
        db.execSQL(CREATE_TABLE_RISK);
        db.execSQL(CREATE_TABLE_RISKCONSEQUENCE);
        db.execSQL(CREATE_TABLE_RISKMITIGATION);
        db.execSQL(CREATE_TABLE_RISKMANAGEMENT);
        db.execSQL(CREATE_TABLE_WORKPLAN);
        db.execSQL(CREATE_TABLE_RESOURCE);
        db.execSQL(CREATE_TABLE_ACCOUNTTYPE);
        db.execSQL(CREATE_TABLE_BUDGET);
        db.execSQL(CREATE_TABLE_CASHFLOW);
        db.execSQL(CREATE_TABLE_MONITORING);
        db.execSQL(CREATE_TABLE_EVALUATION);
        db.execSQL(CREATE_TABLE_CRITERION);
        db.execSQL(CREATE_TABLE_QUESTION);

        // junction tables

        db.execSQL(CREATE_TABLE_OVERALLAIM_PROJECT);
        db.execSQL(CREATE_TABLE_PROJECT_STATUS);
        db.execSQL(CREATE_TABLE_PROJECT_OUTCOME);
        db.execSQL(CREATE_TABLE_OUTCOME_OUTPUT);
        db.execSQL(CREATE_TABLE_OUTPUT_ACTIVITY);
        db.execSQL(CREATE_TABLE_OVERALLAIM_INDICATOR);
        db.execSQL(CREATE_TABLE_OUTCOME_INDICATOR);
        db.execSQL(CREATE_TABLE_OUTPUT_INDICATOR);
        db.execSQL(CREATE_TABLE_MOV_INDICATOR);
        db.execSQL(CREATE_TABLE_OVERALLAIM_RISK);
        db.execSQL(CREATE_TABLE_OUTCOME_RISK);
        db.execSQL(CREATE_TABLE_OUTPUT_RISK);
        db.execSQL(CREATE_TABLE_ACTIVITY_RISK);
        db.execSQL(CREATE_TABLE_OVERALLAIM_CRITERION);
        db.execSQL(CREATE_TABLE_OUTCOME_CRITERION);
        db.execSQL(CREATE_TABLE_OUTPUT_CRITERION);
        db.execSQL(CREATE_TABLE_ACTIVITY_CRITERION);
        db.execSQL(CREATE_TABLE_QUESTION_CATEGORY);
        db.execSQL(CREATE_TABLE_INDICATOR_QUESTION);
        db.execSQL(CREATE_TABLE_OVERALLAIM_CRITERION_QUESTION);
        db.execSQL(CREATE_TABLE_OUTCOME_CRITERION_QUESTION);
        db.execSQL(CREATE_TABLE_OUTPUT_CRITERION_QUESTION);
        db.execSQL(CREATE_TABLE_ACTIVITY_CRITERION_QUESTION);
        db.execSQL(CREATE_TABLE_EVALUATION_QUESTIONNAIRE);
        db.execSQL(CREATE_TABLE_MONITORING_QUESTIONNAIRE);
        db.execSQL(CREATE_TABLE_EVALUATION_DATACOLLECTION);
        db.execSQL(CREATE_TABLE_MONITORING_DATACOLLECTION);
        */
    }


    public void dropTables(SQLiteDatabase db){
        // on upgrade drop older tables

        // bitwise role based access control tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ADDRESS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORGANIZATION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_VALUE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SESSION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ROLE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MENU);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRIVILEGE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ENTITY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_OPERATION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STATUS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER_ROLE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SESSION_ROLE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MENU_ROLE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRIVILEGE_ROLE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PERMISSION);

        // dimension tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DATE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_UNIT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACTIVITYLOG);

        // operation tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TYPE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RISKMAP);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RISKLIKELIHOOD);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RISKIMPACT);

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SCALE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_OVERALLAIM);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SPECIFICAIM);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROJECT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_OUTCOME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_OBJECTIVE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_OUTPUT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACTIVITY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INDICATOR);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUANTITATIVEINDICATOR);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUALITATIVEINDICATOR);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HYBRIDINDICATOR);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUANTITATIVETARGET);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUALITATIVETARGET);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HYBRIDTARGET);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MOV);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RISK);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RISKCONSEQUENCE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RISKMITIGATION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RISKMANAGEMENT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WORKPLAN);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RESOURCE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACCOUNTTYPE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BUDGET);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CASHFLOW);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MONITORING);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVALUATION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CRITERION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUESTION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORY);

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_OVERALLAIM_PROJECT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROJECT_STATUS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROJECT_OUTCOME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_OUTCOME_OUTPUT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_OUTPUT_ACTIVITY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_OVERALLAIM_INDICATOR);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_OUTCOME_INDICATOR);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_OUTPUT_INDICATOR);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MOV_INDICATOR);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_OVERALLAIM_RISK);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_OUTCOME_RISK);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_OUTPUT_RISK);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACTIVITY_RISK);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_OVERALLAIM_CRITERION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_OUTCOME_CRITERION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_OUTPUT_CRITERION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACTIVITY_CRITERION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_QUESTION_CATEGORY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_INDICATOR_QUESTION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_OVERALLAIM_CRITERION_QUESTION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_OUTCOME_CRITERION_QUESTION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_OUTPUT_CRITERION_QUESTION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACTIVITY_CRITERION_QUESTION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MONITORING_QUESTIONNAIRE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVALUATION_QUESTIONNAIRE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MONITORING_DATACOLLECTION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVALUATION_DATACOLLECTION);
    }

    /**
     * This method is to fetch all user records from SQLite
     */
    public void populateBRBAC() {
        populateModelsFromExcel = new cPopulateModelsFromExcel(context);


        // AsyncTask is used that SQLite operation not blocks the UI Thread.
        new AsyncTask<String, Integer, String>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = new ProgressDialog(context);
                progressDialog.setTitle("Uploading default settings...");
                progressDialog.setMessage("Please wait.");
                progressDialog.setIndeterminate(false);
                progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                progressDialog.setMax(100);
                progressDialog.setCancelable(false);
                progressDialog.show();
            }

            @Override
            protected String doInBackground(String... params) {
                try {
                    AssetManager assetManager = context.getAssets();
                    InputStream excelFile = assetManager.open("me_db.xlsx");
                    Workbook workbook = new XSSFWorkbook(excelFile);


                    int allRows = 0;
                    ArrayList<Sheet> sheets = new ArrayList<Sheet>();

                    for (int i = 0; i < listOfBRBACTables.length; i++) {
                        Sheet sheet = workbook.getSheet(listOfBRBACTables[i]);

                        sheets.add(sheet);
                        //publishProgress(i);

                        // number of rows (for all tables used for checking progress
                        allRows = allRows + (sheet.getPhysicalNumberOfRows() - 1);
                    }


                    int currentRows = 0;
                    // loop through the table action_list
                    for (int i = 0; i < listOfBRBACTables.length; i++) {
                        Sheet sheet = workbook.getSheet(listOfBRBACTables[i]);

                        if (sheet == null) {
                            return null;
                        }


                        switch (i) {
                            case 0:
                                for (Iterator<Row> rit = sheet.iterator(); rit.hasNext(); ) {
                                    Row cRow = rit.next();

                                    //just skip the row if row number is 0
                                    if (cRow.getRowNum() == 0) {
                                        continue;
                                    }

                                    // add the row into the database
                                    populateModelsFromExcel.addAddressFromExcel(cRow);

                                    // publish the progress after adding a record
                                    currentRows++;
                                    publishProgress(currentRows * 100 / allRows);
                                }
                                break;

                            case 1:
                                for (Iterator<Row> rit = sheet.iterator(); rit.hasNext(); ) {
                                    Row cRow = rit.next();

                                    //just skip the row if row number is 0
                                    if (cRow.getRowNum() == 0) {
                                        continue;
                                    }

                                    // add the row into the database
                                    populateModelsFromExcel.addOrganizationFromExcel(cRow);

                                    // publish the progress after adding a record
                                    currentRows++;
                                    publishProgress(currentRows * 100 / allRows);
                                }
                                break;

                            case 2:
                                for (Iterator<Row> rit = sheet.iterator(); rit.hasNext(); ) {
                                    Row cRow = rit.next();

                                    //just skip the row if row number is 0
                                    if (cRow.getRowNum() == 0) {
                                        continue;
                                    }

                                    // add the row into the database
                                    populateModelsFromExcel.addValueFromExcel(cRow);

                                    // publish the progress after adding a record
                                    currentRows++;
                                    publishProgress(currentRows * 100 / allRows);
                                }
                                break;

                            case 3:
                                for (Iterator<Row> rit = sheet.iterator(); rit.hasNext(); ) {
                                    Row cRow = rit.next();

                                    //just skip the row if row number is 0
                                    if (cRow.getRowNum() == 0) {
                                        continue;
                                    }

                                    // add the row into the database
                                    populateModelsFromExcel.addUserFromExcel(cRow);

                                    // publish the progress after adding a record
                                    currentRows++;
                                    publishProgress(currentRows * 100 / allRows);
                                }
                                break;

                            case 4:
                                for (Iterator<Row> rit = sheet.iterator(); rit.hasNext(); ) {
                                    Row cRow = rit.next();

                                    //just skip the row if row number is 0
                                    if (cRow.getRowNum() == 0) {
                                        continue;
                                    }

                                    // add the row into the database
                                    populateModelsFromExcel.addSessionFromExcel(cRow);

                                    // publish the progress after adding a record
                                    currentRows++;
                                    publishProgress(currentRows * 100 / allRows);
                                }
                                break;

                            case 5:
                                for (Iterator<Row> rit = sheet.iterator(); rit.hasNext(); ) {
                                    Row cRow = rit.next();

                                    //just skip the row if row number is 0
                                    if (cRow.getRowNum() == 0) {
                                        continue;
                                    }

                                    // add the row into the database
                                    populateModelsFromExcel.addRoleFromExcel(cRow);

                                    // publish the progress after adding a record
                                    currentRows++;
                                    publishProgress(currentRows * 100 / allRows);
                                }
                                break;

                            case 6:
                                for (Iterator<Row> rit = sheet.iterator(); rit.hasNext(); ) {
                                    Row cRow = rit.next();

                                    //just skip the row if row number is 0
                                    if (cRow.getRowNum() == 0) {
                                        continue;
                                    }

                                    // add the row into the database
                                    populateModelsFromExcel.addMenuFromExcel(cRow);

                                    // publish the progress after adding a record
                                    currentRows++;
                                    publishProgress(currentRows * 100 / allRows);
                                }
                                break;

                            case 7:
                                for (Iterator<Row> rit = sheet.iterator(); rit.hasNext(); ) {
                                    Row cRow = rit.next();

                                    //just skip the row if row number is 0
                                    if (cRow.getRowNum() == 0) {
                                        continue;
                                    }

                                    // add the row into the database
                                    populateModelsFromExcel.addPrivilegeFromExcel(cRow);

                                    // publish the progress after adding a record
                                    currentRows++;
                                    publishProgress(currentRows * 100 / allRows);
                                }
                                break;

                            case 8:
                                for (Iterator<Row> rit = sheet.iterator(); rit.hasNext(); ) {
                                    Row cRow = rit.next();

                                    //just skip the row if row number is 0
                                    if (cRow.getRowNum() == 0) {
                                        continue;
                                    }

                                    // add the row into the database
                                    populateModelsFromExcel.addEntityFromExcel(cRow);

                                    // publish the progress after adding a record
                                    currentRows++;
                                    publishProgress(currentRows * 100 / allRows);
                                }
                                break;

                            case 9:
                                for (Iterator<Row> rit = sheet.iterator(); rit.hasNext(); ) {
                                    Row cRow = rit.next();

                                    //just skip the row if row number is 0
                                    if (cRow.getRowNum() == 0) {
                                        continue;
                                    }

                                    // add the row into the database
                                    populateModelsFromExcel.addOperationFromExcel(cRow);

                                    // publish the progress after adding a record
                                    currentRows++;
                                    publishProgress(currentRows * 100 / allRows);
                                }
                                break;

                            case 10:
                                for (Iterator<Row> rit = sheet.iterator(); rit.hasNext(); ) {
                                    Row cRow = rit.next();

                                    //just skip the row if row number is 0
                                    if (cRow.getRowNum() == 0) {
                                        continue;
                                    }

                                    // add the row into the database
                                    populateModelsFromExcel.addStatusFromExcel(cRow);

                                    // publish the progress after adding a record
                                    currentRows++;
                                    publishProgress(currentRows * 100 / allRows);
                                }
                                break;

                            case 11:
                                for (Iterator<Row> rit = sheet.iterator(); rit.hasNext(); ) {
                                    Row cRow = rit.next();

                                    //just skip the row if row number is 0
                                    if (cRow.getRowNum() == 0) {
                                        continue;
                                    }

                                    // add the row into the database
                                    populateModelsFromExcel.addUserRoleFromExcel(cRow);

                                    // publish the progress after adding a record
                                    currentRows++;
                                    publishProgress(currentRows * 100 / allRows);
                                }
                                break;

                            case 12:
                                for (Iterator<Row> rit = sheet.iterator(); rit.hasNext(); ) {
                                    Row cRow = rit.next();

                                    //just skip the row if row number is 0
                                    if (cRow.getRowNum() == 0) {
                                        continue;
                                    }

                                    // add the row into the database
                                    populateModelsFromExcel.addSessionRoleFromExcel(cRow);

                                    // publish the progress after adding a record
                                    currentRows++;
                                    publishProgress(currentRows * 100 / allRows);
                                }
                                break;

                            case 13:
                                for (Iterator<Row> rit = sheet.iterator(); rit.hasNext(); ) {
                                    Row cRow = rit.next();

                                    //just skip the row if row number is 0
                                    if (cRow.getRowNum() == 0) {
                                        continue;
                                    }

                                    // add the row into the database
                                    populateModelsFromExcel.addMenuRoleFromExcel(cRow);

                                    // publish the progress after adding a record
                                    currentRows++;
                                    publishProgress(currentRows * 100 / allRows);
                                }
                                break;

                            case 14:
                                for (Iterator<Row> rit = sheet.iterator(); rit.hasNext(); ) {
                                    Row cRow = rit.next();

                                    //just skip the row if row number is 0
                                    if (cRow.getRowNum() == 0) {
                                        continue;
                                    }

                                    // add the row into the database
                                    populateModelsFromExcel.addPermissionFromExcel(cRow);

                                    // publish the progress after adding a record
                                    currentRows++;
                                    publishProgress(currentRows * 100 / allRows);
                                }
                                break;

                            default:
                                break;
                        }
                    }
                } catch (Exception e) {
                    Log.e("Error: ", e.getMessage());
                    //e.printStackTrace();
                }
                return null;

            }

            @Override
            protected void onProgressUpdate(Integer... progress) {
                // Set progress percentage
                progressDialog.setProgress(progress[0]);
                //Toast.makeText(context, "tables = "+progress[0], Toast.LENGTH_LONG).show();
            }

            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                if (progressDialog != null) {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                        Toast.makeText(context, "Uploading complete", Toast.LENGTH_LONG).show();
                    }
                }

            }
        }.execute();
    }

}
