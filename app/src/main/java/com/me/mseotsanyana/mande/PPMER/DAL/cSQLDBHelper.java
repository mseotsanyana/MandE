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
    // logcat tag
    private static final String TAG = "dbHelper";

    private Context context;
    private cPopulateModelsFromExcel populateModelsFromExcel;
    private ProgressDialog progressDialog;

    String[] listOfBRBACTables = {"ADDRESS","ORGANIZATION","VALUE","USER","SESSION","ROLE","MENU",
            "PRIVILEGE","ENTITY","OPERATION","STATUS","USER_ROLE","SESSION_ROLE","MENU_ROLE",
            "PERMISSION"};

    // Database Name
    private static final String DATABASE_NAME = "MEDB.db";
    // Database Version
    private static final int DATABASE_VERSION = 2;

    //############################ START BRBAC MODULE TABLES ###########################
    public static final String TABLE_tblADDRESS         = "tblADDRESS";         /* 1  */
    public static final String TABLE_tblORGANIZATION    = "tblORGANIZATION";    /* 2  */
    public static final String TABLE_tblVALUE           = "tblVALUE";           /* 3  */
    public static final String TABLE_tblUSER            = "tblUSER";            /* 4  */
    public static final String TABLE_tblSESSION         = "tblSESSION";         /* 5  */
    public static final String TABLE_tblROLE            = "tblROLE";            /* 6  */
    public static final String TABLE_tblMENU            = "tblMENU";            /* 7  */
    public static final String TABLE_tblPRIVILEGE       = "tblPRIVILEGE";       /* 8  */
    public static final String TABLE_tblENTITY          = "tblENTITY";          /* 9  */
    public static final String TABLE_tblOPERATION       = "tblOPERATION";       /* 10 */
    public static final String TABLE_tblSTATUS          = "tblSTATUS";          /* 11 */
    public static final String TABLE_tblORG_ADDRESS     = "tblORG_ADDRESS";     /* 12 */
    public static final String TABLE_tblUSER_ADDRESS    = "tblUSER_ADDRESS";    /* 13 */
    public static final String TABLE_tblUSER_ROLE       = "tblUSER_ROLE";       /* 14 */
    public static final String TABLE_tblSESSION_ROLE    = "tblSESSION_ROLE";    /* 15 */
    public static final String TABLE_tblMENU_ROLE       = "tblMENU_ROLE";       /* 16 */
    public static final String TABLE_tblPERMISSION      = "tblPERMISSION";      /* 17 */
    public static final String TABLE_tblNOTIFICATION    = "tblNOTIFICATION";    /* 18 */
    public static final String TABLE_tblSETTING         = "tblSETTING";         /* 19 */
    public static final String TABLE_tblSUBSCRIBER      = "tblSUBSCRIBER";      /* 20 */
    public static final String TABLE_tblPUBLISHER       = "tblPUBLISHER";       /* 21 */
    public static final String TABLE_tblSETNOTIFICATION = "tblSETNOTIFICATION"; /* 22 */
    public static final String TABLE_tblSUBPUB          = "tblSUBPUB";          /* 23 */
    public static final String TABLE_tblACTIVITYLOG     = "tblACTIVITYLOG ";    /* 24 */
    //############################# END BRBAC MODULE TABLES ##############################

    //########################## START PLANNING MODULE TABLES ##############################
    public static final String TABLE_tblLOGFRAME          = "tblLOGFRAME";          /* 1  */
    public static final String TABLE_tblLOGFRAMETREE      = "tblLOGFRAMETREE";      /* 2  */
    public static final String TABLE_tblIMPACT            = "tblIMPACT";            /* 3  */
    public static final String TABLE_tblOUTCOME           = "tblOUTCOME";           /* 4  */
    public static final String TABLE_tblOUTPUT            = "tblOUTPUT";            /* 5  */
    public static final String TABLE_tblACTIVITY          = "tblACTIVITY";          /* 6  */
    public static final String TABLE_tblPRECEDINGACTIVITY = "tblPRECEDINGACTIVITY"; /* 7  */
    public static final String TABLE_tblINPUT             = "tblINPUT";             /* 8  */
    public static final String TABLE_tblHUMANSET          = "tblHUMANSET";          /* 9  */
    public static final String TABLE_tblMATERIAL          = "tblMATERIAL";          /* 10 */
    public static final String TABLE_tblBUDGET            = "tblBUDGET";            /* 11 */
    public static final String TABLE_tblOUTCOME_IMPACT    = "tblOUTCOME_IMPACT";    /* 12 */
    public static final String TABLE_tblOUTPUT_OUTCOME    = "tblOUTPUT_OUTCOME";    /* 13 */
    public static final String TABLE_tblACTIVITY_OUTPUT   = "tblACTIVITY_OUTPUT";   /* 14 */
    public static final String TABLE_tblCRITERIA          = "tblCRITERIA";          /* 15 */
    public static final String TABLE_tblQUESTION_GROUPING = "tblQUESTION_GROUPING"; /* 16 */
    public static final String TABLE_tblQUESTION_TYPE     = "tblQUESTION_TYPE";     /* 17 */
    public static final String TABLE_tblCHOICESET         = "tblCHOICESET";         /* 18 */
    public static final String TABLE_tblQUESTION          = "tblQUESTION";          /* 19 */
    public static final String TABLE_tblIMPACT_QUESTION   = "tblIMPACT_QUESTION";   /* 20 */
    public static final String TABLE_tblOUTCOME_QUESTION  = "tblOUTCOME_QUESTION";  /* 21 */
    public static final String TABLE_tblOUTPUT_QUESTION   = "tblOUTPUT_QUESTION";   /* 22 */
    public static final String TABLE_tblACTIVITY_QUESTION = "tblACTIVITY_QUESTION"; /* 23 */
    public static final String TABLE_tblINPUT_QUESTION    = "tblINPUT_QUESTION";    /* 24 */
    public static final String TABLE_tblRAID              = "tblRAID";              /* 25 */
    public static final String TABLE_tblIMPACT_RAID       = "tblIMPACT_RAID";       /* 26 */
    public static final String TABLE_tblOUTCOME_RAID      = "tblOUTCOME_RAID";      /* 27 */
    public static final String TABLE_tblOUTPUT_RAID       = "tblOUTPUT_RAID";       /* 28 */
    public static final String TABLE_tblACTIVITY_RAID     = "tblACTIVITY_RAID";     /* 29 */
    public static final String TABLE_tblACCOUNT           = "tblACCOUNT";           /* 30 */
    //############################# END PLANNING MODULE TABLES ##############################


    /** Bitwise Role Based Access Control (BRBAC) tables
    public static final String TABLE_ADDRESS            = "ADDRESS";          1
    public static final String TABLE_ORGANIZATION       = "ORGANIZATION";     2
    public static final String TABLE_VALUE              = "VALUE";            3
    public static final String TABLE_USER               = "USER";             4
    public static final String TABLE_SESSION            = "SESSION";          5
    public static final String TABLE_ROLE               = "ROLE";             6
    public static final String TABLE_MENU               = "MENU";             7
    public static final String TABLE_PRIVILEGE          = "PRIVILEGE";        8
    public static final String TABLE_ENTITY             = "ENTITY";           9
    public static final String TABLE_OPERATION          = "OPERATION";        10
    public static final String TABLE_STATUS             = "STATUS";           11
    public static final String TABLE_USER_ROLE          = "USER_ROLE";        12
    public static final String TABLE_SESSION_ROLE       = "SESSION_ROLE";     13
    public static final String TABLE_MENU_ROLE          = "MENU_ROLE";        14
    public static final String TABLE_PRIVILEGE_ROLE     = "PRIVILEGE_ROLE";   15
    //public static final String TABLE_OPERATION_STATUS = "OPERATION_STATUS"; 16
    public static final String TABLE_PERMISSION         = "PERMISSION";       17 **/

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

    public static final String KEY_ID             = "_id";
    public static final String KEY_ENTITY_TYPE_ID = "_id_entity_type";
    public static final String KEY_OWNER_ID       = "_id_owner";
    public static final String KEY_ORG_ID         = "_id_org";
    public static final String KEY_SERVER_ID      = "_id_server";
    public static final String KEY_GROUP_BITS     = "_bits_group";
    public static final String KEY_PERMS_BITS     = "_bits_perms";
    public static final String KEY_STATUS_BITS    = "_bits_status";
    public static final String KEY_CREATED_DATE   = "created_date";
    public static final String KEY_MODIFIED_DATE  = "modified_date";
    public static final String KEY_SYNCED_DATE    = "synced_date";
    public static final String KEY_START_DATE     = "start_date";
    public static final String KEY_END_DATE       = "end_date";
    public static final String KEY_NAME           = "name";
    public static final String KEY_SURNAME        = "surname";
    public static final String KEY_GENDER         = "gender";
    public static final String KEY_PHONE          = "phone";
    public static final String KEY_DESCRIPTION    = "description";
    public static final String KEY_STREET         = "street";
    public static final String KEY_CITY           = "city";
    public static final String KEY_PROVINCE       = "province";
    public static final String KEY_POSTAL_CODE    = "postal_code";
    public static final String KEY_COUNTRY        = "country";
    public static final String KEY_TELEPHONE      = "telephone";
    public static final String KEY_FAX            = "fax";
    public static final String KEY_VISION         = "vision";
    public static final String KEY_MISSION        = "mission";
    public static final String KEY_EMAIL          = "email";
    public static final String KEY_WEBSITE        = "website";
    public static final String KEY_PHOTO          = "photo_path";
    public static final String KEY_PASSWORD       = "password";
    public static final String KEY_SALT           = "salt";
    public static final String KEY_SETTING_VALUE  = "setting_value";


    public static final String KEY_USER_FK_ID               = "_id_user_fk";
    public static final String KEY_SUBSCRIBER_FK_ID         = "_id_subscriber_fk";
    public static final String KEY_PUBLISHER_FK_ID          = "_id_publisher_fk";
    public static final String KEY_PARENT_FK_ID             = "_id_parent_fk";
    public static final String KEY_CHILD_FK_ID              = "_id_child_fk";
    public static final String KEY_LOGFRAME_FK_ID           = "_id_logframe_fk";
    public static final String KEY_IMPACT_FK_ID             = "_id_impact_fk";
    public static final String KEY_OUTCOME_FK_ID            = "_id_outcome_fk";
    public static final String KEY_OUTPUT_FK_ID             = "_id_impact_fk";
    public static final String KEY_ACTIVITY_FK_ID           = "_id_activity_fk";
    public static final String KEY_PRECEDING_ACTIVITY_FK_ID = "_id_preceding_activity_fk";
    public static final String KEY_INPUT_FK_ID              = "_id_input_fk";
    public static final String KEY_QUESTION_FK_ID           = "_id_question_fk";
    public static final String KEY_QUESTION_GROUPING_FK_ID  = "_id_question_grouping_fk";
    public static final String KEY_QUESTION_TYPE_FK_ID      = "_id_question_type_fk";
    public static final String KEY_CHOICESET_FK_ID          = "_id_choice_fk";
    public static final String KEY_CRITERIA_FK_ID           = "_id_criteria_fk";
    public static final String KEY_RAID_FK_ID               = "_id_raid_fk";
    public static final String KEY_ACCOUNT_FK_ID            = "_id_account_fk";
    public static final String KEY_ADDRESS_FK_ID            = "_id_address_fk";
    public static final String KEY_ORGANIZATION_FK_ID       = "_id_organization_fk";
    public static final String KEY_ROLE_FK_ID               = "_id_role_fk";
    public static final String KEY_SESSION_FK_ID            = "_id_session_fk";
    public static final String KEY_MENU_FK_ID               = "_id_menu_fk";
    public static final String KEY_PRIVILEGE_FK_ID          = "_id_privilege_fk";
    public static final String KEY_ENTITY_FK_ID             = "_id_entity_fk";
    public static final String KEY_ENTITY_TYPE_FK_ID        = "_id_entity_type_fk";
    public static final String KEY_OPERATION_FK_ID          = "_id_operation_fk";
    public static final String KEY_STATUS_FK_ID             = "_id_status_fk";
    public static final String KEY_NOTIFICATION_FK_ID       = "_id_notification_fk";
    public static final String KEY_SETTING_FK_ID            = "_id_setting_fk";

    public static final String KEY_USER_ID         = "_id_todelete";
    public static final String KEY_ORGANIZATION_ID = "_id_todelete";
    public static final String KEY_STATUS_ID       = "_id_todelete";

    //####################################### START of BRBAC MODULE ######################################


    //-- ----------------------------------------------------------------------------------------
    //-- Table `tblADDRESS`
    //-- ----------------------------------------------------------------------------------------
    public static final String CREATE_TABLE_tblADDRESS = "CREATE TABLE " + TABLE_tblADDRESS + "("
            + KEY_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_SERVER_ID +" INTEGER DEFAULT NULL, "
            + KEY_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL DEFAULT 3, "
            + KEY_STREET + " TEXT, "
            + KEY_CITY + " TEXT, "
            + KEY_PROVINCE + " TEXT, "
            + KEY_POSTAL_CODE + " TEXT, "
            + KEY_COUNTRY + " TEXT, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP " + ");";

    //-- --------------------------------------------------------------------------------------------------
    //-- Table `tblORGANIZATION`
    //-- --------------------------------------------------------------------------------------------------
    public static final String CREATE_TABLE_tblORGANIZATION = "CREATE TABLE " + TABLE_tblORGANIZATION + "("
            + KEY_ID +" INTEGER NOT NULL, "
            + KEY_ADDRESS_FK_ID + " INTEGER, "
            + KEY_SERVER_ID +" INTEGER DEFAULT NULL, "
            + KEY_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL DEFAULT 3, "
            + KEY_NAME + " TEXT, "
            + KEY_TELEPHONE + " TEXT, "
            + KEY_FAX + " TEXT, "
            + KEY_VISION + " TEXT, "
            + KEY_MISSION + " TEXT, "
            + KEY_EMAIL + " TEXT, "
            + KEY_WEBSITE + " TEXT, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + "PRIMARY KEY (" + KEY_ID + "), "
            + "FOREIGN KEY (" + KEY_ADDRESS_FK_ID + ") "
            + "REFERENCES " + TABLE_tblADDRESS + "("+ KEY_ID + ") "
            + "ON DELETE CASCADE ON UPDATE CASCADE);";

    //-- ----------------------------------------------------------------------------------
    //-- Table `tblUSER`
    //-- ----------------------------------------------------------------------------------
    public static final String CREATE_TABLE_tblUSER = "CREATE TABLE " + TABLE_tblUSER + "("
            + KEY_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_ORGANIZATION_FK_ID + " INTEGER NOT NULL, "
            + KEY_SERVER_ID +" INTEGER DEFAULT NULL, "
            + KEY_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL DEFAULT 3, "
            + KEY_PHOTO + " TEXT , "
            + KEY_NAME + " TEXT, "
            + KEY_SURNAME + " TEXT, "
            + KEY_GENDER + " TEXT, "
            + KEY_DESCRIPTION + " TEXT, "
            + KEY_EMAIL + " TEXT NOT NULL UNIQUE, "
            + KEY_WEBSITE + " TEXT, "
            + KEY_PHONE + " TEXT, "
            + KEY_PASSWORD + " TEXT, "
            + KEY_SALT + " TEXT, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + "FOREIGN KEY (" + KEY_ORGANIZATION_FK_ID + ") "
            + "REFERENCES " + TABLE_tblORGANIZATION +"("+ KEY_ID +") "
            + "ON DELETE CASCADE ON UPDATE CASCADE);";

    //-- ----------------------------------------------------------------------------------------
    //-- Table `tblSESSION`
    //-- ----------------------------------------------------------------------------------------
    public static final String CREATE_TABLE_tblSESSION = "CREATE TABLE " + TABLE_tblSESSION + "("
            + KEY_ID +" INTEGER NOT NULL, "
            + KEY_USER_FK_ID +" INTEGER NOT NULL, "
            + KEY_SERVER_ID +" INTEGER DEFAULT NULL, "
            + KEY_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL DEFAULT 3, "
            + KEY_PHOTO + " TEXT, "
            + KEY_NAME + " TEXT, "
            + KEY_SURNAME + " TEXT, "
            + KEY_DESCRIPTION + " TEXT, "
            + KEY_EMAIL + " TEXT, "
            + KEY_WEBSITE + " TEXT, "
            + KEY_PHONE + " TEXT, "
            + KEY_PASSWORD + " TEXT, "
            + KEY_SALT + " TEXT, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + "PRIMARY KEY (" + KEY_ID + "), "
            + "FOREIGN KEY (" + KEY_USER_FK_ID + ") "
            + "REFERENCES " + TABLE_tblUSER +"("+ KEY_ID +") "
            + "ON DELETE CASCADE ON UPDATE CASCADE);";

    //-- ------------------------------------------------------------------------------------
    //-- Table `tblVALUE`
    //-- ------------------------------------------------------------------------------------
    public static final String CREATE_TABLE_tblVALUE = "CREATE TABLE " + TABLE_tblVALUE + "("
            + KEY_ID +" INTEGER NOT NULL, "
            + KEY_ORGANIZATION_FK_ID + " INTEGER NOT NULL, "
            + KEY_SERVER_ID +" INTEGER DEFAULT NULL, "
            + KEY_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL DEFAULT 3, "
            + KEY_NAME + " TEXT, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + "PRIMARY KEY (" + KEY_ID + "), "
            + "FOREIGN KEY (" + KEY_ORGANIZATION_FK_ID + ") "
            + "REFERENCES " + TABLE_tblORGANIZATION +"("+ KEY_ID +") "
            + "ON DELETE CASCADE ON UPDATE CASCADE);";

    //-- ----------------------------------------------------------------------------------
    //-- Table `tblROLE`
    //-- ----------------------------------------------------------------------------------
    public static final String CREATE_TABLE_tblROLE = "CREATE TABLE " + TABLE_tblROLE + "("
            + KEY_ID +" INTEGER NOT NULL, "
            + KEY_ORGANIZATION_FK_ID + " INTEGER NOT NULL, "
            + KEY_SERVER_ID +" INTEGER DEFAULT NULL, "
            + KEY_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL DEFAULT 3, "
            + KEY_NAME + " TEXT, "
            + KEY_DESCRIPTION + " TEXT, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + "PRIMARY KEY (" + KEY_ID + ", "+ KEY_ORGANIZATION_FK_ID +"),"
            + "FOREIGN KEY (" + KEY_ORGANIZATION_FK_ID + ") "
            + "REFERENCES " + TABLE_tblORGANIZATION +"("+ KEY_ID +") "
            + "ON DELETE CASCADE ON UPDATE CASCADE);";

    //-- ----------------------------------------------------------------------------------
    //-- Table `tblMENU`
    //-- ----------------------------------------------------------------------------------
    public static final String CREATE_TABLE_tblMENU = "CREATE TABLE " + TABLE_tblMENU + "("
            + KEY_ID +" INTEGER NOT NULL, "
            + KEY_PARENT_FK_ID +" INTEGER NOT NULL DEFAULT 0, "
            + KEY_SERVER_ID +" INTEGER DEFAULT NULL, "
            + KEY_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL DEFAULT 3, "
            + KEY_NAME + " TEXT, "
            + KEY_DESCRIPTION + " TEXT, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + "PRIMARY KEY (" + KEY_ID + "),"
            + "FOREIGN KEY (" + KEY_PARENT_FK_ID + ") "
            + "REFERENCES " + TABLE_tblMENU +"("+ KEY_ID +") "
            + "ON DELETE CASCADE ON UPDATE CASCADE);";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblPRIVILEGE`
    //-- -------------------------------------------------------------------------------------------
    public static final String CREATE_TABLE_tblPRIVILEGE = "CREATE TABLE " + TABLE_tblPRIVILEGE + "("
            + KEY_ID +" INTEGER NOT NULL, "
            + KEY_ROLE_FK_ID +" INTEGER NOT NULL, "
            + KEY_SERVER_ID +" INTEGER DEFAULT NULL, "
            + KEY_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL DEFAULT 3, "
            + KEY_NAME + " TEXT, "
            + KEY_DESCRIPTION + " TEXT, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + "PRIMARY KEY (" + KEY_ID +"),"
            + "FOREIGN KEY (" + KEY_ROLE_FK_ID +") "
            + "REFERENCES " + TABLE_tblROLE +"(" + KEY_ID +") "
            + "ON DELETE CASCADE ON UPDATE CASCADE);";

    //-- --------------------------------------------------------------------------------------
    //-- Table `tblENTITY`
    //-- --------------------------------------------------------------------------------------
    public static final String CREATE_TABLE_tblENTITY = "CREATE TABLE " + TABLE_tblENTITY + "("
            + KEY_ID +" INTEGER NOT NULL, "
            + KEY_ENTITY_TYPE_ID +" INTEGER NOT NULL, "
            + KEY_SERVER_ID +" INTEGER DEFAULT NULL, "
            + KEY_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL DEFAULT 3, "
            + KEY_NAME + " TEXT, "
            + KEY_DESCRIPTION + " TEXT, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + "PRIMARY KEY (" + KEY_ID  +","+ KEY_ENTITY_TYPE_ID +"));";

    //-- --------------------------------------------------------------------------------------------
    //-- Table `tblOPERATION`
    //-- --------------------------------------------------------------------------------------------
    public static final String CREATE_TABLE_tblOPERATION = "CREATE TABLE " + TABLE_tblOPERATION + "("
            + KEY_ID +" INTEGER NOT NULL, "
            + KEY_SERVER_ID +" INTEGER DEFAULT NULL, "
            + KEY_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL DEFAULT 3, "
            + KEY_NAME + " TEXT, "
            + KEY_DESCRIPTION + " TEXT, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + "PRIMARY KEY (" + KEY_ID +"));";

    //-- --------------------------------------------------------------------------------------
    //-- Table `tblSTATUS`
    //-- --------------------------------------------------------------------------------------
    public static final String CREATE_TABLE_tblSTATUS = "CREATE TABLE " + TABLE_tblSTATUS + "("
            + KEY_ID +" INTEGER NOT NULL, "
            + KEY_SERVER_ID +" INTEGER DEFAULT NULL, "
            + KEY_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL DEFAULT 3, "
            + KEY_NAME + " TEXT, "
            + KEY_DESCRIPTION + " TEXT, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + "PRIMARY KEY (" + KEY_ID + "));";

    //-- ------------------------------------------------------------------------------------------------
    //-- Table `tblORG_ADDRESS`
    //-- ------------------------------------------------------------------------------------------------
    public static final String CREATE_TABLE_tblORG_ADDRESS = "CREATE TABLE " + TABLE_tblORG_ADDRESS + "("
            + KEY_ORGANIZATION_FK_ID + " INTEGER NOT NULL, "
            + KEY_ADDRESS_FK_ID + " INTEGER NOT NULL, "
            + KEY_SERVER_ID + " INTEGER DEFAULT NULL, "
            + KEY_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL DEFAULT 3, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + " PRIMARY KEY (" + KEY_ORGANIZATION_FK_ID + "," + KEY_ADDRESS_FK_ID +" ), "
            + " FOREIGN KEY (" + KEY_ORGANIZATION_FK_ID + ") "
            + " REFERENCES " + TABLE_tblORGANIZATION +" ("+ KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_ADDRESS_FK_ID + ") "
            + " REFERENCES " + TABLE_tblADDRESS +" ("+ KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE); ";

    //-- --------------------------------------------------------------------------------------------------
    //-- Table `tblUSER_ADDRESS`
    //-- --------------------------------------------------------------------------------------------------
    public static final String CREATE_TABLE_tblUSER_ADDRESS = "CREATE TABLE " + TABLE_tblUSER_ADDRESS + "("
            + KEY_USER_FK_ID + " INTEGER NOT NULL, "
            + KEY_ADDRESS_FK_ID + " INTEGER NOT NULL, "
            + KEY_SERVER_ID + " INTEGER DEFAULT NULL, "
            + KEY_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL DEFAULT 3, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + " PRIMARY KEY (" + KEY_USER_FK_ID + "," + KEY_ADDRESS_FK_ID +" ), "
            + " FOREIGN KEY (" + KEY_USER_FK_ID + ") "
            + " REFERENCES " + TABLE_tblUSER +" ("+ KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_ADDRESS_FK_ID + ") "
            + " REFERENCES " + TABLE_tblADDRESS +" ("+ KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE); ";

    //-- --------------------------------------------------------------------------------------------
    //-- Table `tblUSER_ROLE`
    //-- --------------------------------------------------------------------------------------------
    public static final String CREATE_TABLE_tblUSER_ROLE = "CREATE TABLE " + TABLE_tblUSER_ROLE + "("
            + KEY_USER_FK_ID + " INTEGER NOT NULL, "
            + KEY_ROLE_FK_ID + " INTEGER NOT NULL, "
            + KEY_SERVER_ID + " INTEGER DEFAULT NULL, "
            + KEY_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL DEFAULT 3, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + " PRIMARY KEY (" + KEY_USER_FK_ID + "," + KEY_ROLE_FK_ID +" ), "
            + " FOREIGN KEY (" + KEY_USER_FK_ID + ") "
            + " REFERENCES " + TABLE_tblUSER +" ("+ KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_ROLE_FK_ID + ") "
            + " REFERENCES " + TABLE_tblROLE +" ("+ KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE); ";

    //-- --------------------------------------------------------------------------------------------------
    //-- Table `tblSESSION_ROLE`
    //-- --------------------------------------------------------------------------------------------------
    public static final String CREATE_TABLE_tblSESSION_ROLE = "CREATE TABLE " + TABLE_tblSESSION_ROLE + "("
            + KEY_SESSION_FK_ID + " INTEGER NOT NULL, "
            + KEY_ROLE_FK_ID + " INTEGER NOT NULL, "
            + KEY_SERVER_ID + " INTEGER DEFAULT NULL, "
            + KEY_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL DEFAULT 3, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + " PRIMARY KEY (" + KEY_SESSION_FK_ID + "," + KEY_ROLE_FK_ID +" ), "
            + " FOREIGN KEY (" + KEY_SESSION_FK_ID + ") "
            + " REFERENCES " + TABLE_tblSESSION +" ("+ KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_ROLE_FK_ID + ") "
            + " REFERENCES " + TABLE_tblROLE +" ("+ KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE); ";

    //-- --------------------------------------------------------------------------------------------
    //-- Table `tblMENU_ROLE`
    //-- --------------------------------------------------------------------------------------------
    public static final String CREATE_TABLE_tblMENU_ROLE = "CREATE TABLE " + TABLE_tblMENU_ROLE + "("
            + KEY_MENU_FK_ID + " INTEGER NOT NULL, "
            + KEY_ROLE_FK_ID + " INTEGER NOT NULL, "
            + KEY_SERVER_ID + " INTEGER DEFAULT NULL, "
            + KEY_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL DEFAULT 3, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + " PRIMARY KEY (" + KEY_MENU_FK_ID + "," + KEY_ROLE_FK_ID +" ), "
            + " FOREIGN KEY (" + KEY_MENU_FK_ID + ") "
            + " REFERENCES " + TABLE_tblMENU+" ("+ KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_ROLE_FK_ID + ") "
            + " REFERENCES " + TABLE_tblROLE +" ("+ KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE); ";

    //-- ----------------------------------------------------------------------------------------------
    //-- Table `tblPERMISSION`
    //-- ----------------------------------------------------------------------------------------------
    public static final String CREATE_TABLE_tblPERMISSION = "CREATE TABLE " + TABLE_tblPERMISSION + "("
            + KEY_PRIVILEGE_FK_ID +" INTEGER NOT NULL, "
            + KEY_ENTITY_FK_ID +" INTEGER NOT NULL, "
            + KEY_ENTITY_TYPE_FK_ID +" INTEGER NOT NULL, "
            + KEY_OPERATION_FK_ID +" INTEGER NOT NULL, "
            + KEY_STATUS_FK_ID +" INTEGER NOT NULL, "
            + KEY_SERVER_ID + " INTEGER DEFAULT NULL, "
            + KEY_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL DEFAULT 3, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + " PRIMARY KEY (" + KEY_PRIVILEGE_FK_ID + "," + KEY_ENTITY_FK_ID +", "
            + KEY_ENTITY_TYPE_FK_ID +", "+ KEY_OPERATION_FK_ID +", "+ KEY_STATUS_FK_ID + " ), "
            + " FOREIGN KEY (" + KEY_PRIVILEGE_FK_ID + ") "
            + " REFERENCES " + TABLE_tblPRIVILEGE+" ("+ KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_ENTITY_FK_ID +", "+ KEY_ENTITY_TYPE_FK_ID + ") "
            + " REFERENCES " + TABLE_tblENTITY +" ("+ KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_OPERATION_FK_ID + ") "
            + " REFERENCES " + TABLE_tblOPERATION +" ("+ KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_STATUS_FK_ID + ") "
            + " REFERENCES " + TABLE_tblSTATUS +" ("+ KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE); ";

    //-- --------------------------------------------------------------------------------------------
    //-- Table `tblSETTING`
    //-- --------------------------------------------------------------------------------------------
    public static final String CREATE_TABLE_tblSETTING = "CREATE TABLE " + TABLE_tblSETTING + "("
            + KEY_ID +" INTEGER NOT NULL, "
            + KEY_SERVER_ID +" INTEGER DEFAULT NULL, "
            + KEY_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL DEFAULT 3, "
            + KEY_NAME + " TEXT, "
            + KEY_DESCRIPTION + " TEXT, "
            + KEY_SETTING_VALUE + " BOOLEAN, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + "PRIMARY KEY (" + KEY_ID +"));";

    //-- --------------------------------------------------------------------------------------------------
    //-- Table `tblNOTIFICATION`
    //-- --------------------------------------------------------------------------------------------------
    public static final String CREATE_TABLE_tblNOTIFICATION = "CREATE TABLE " + TABLE_tblNOTIFICATION + "("
            + KEY_ID +" INTEGER NOT NULL, "
            + KEY_ENTITY_FK_ID +" INTEGER NOT NULL, "
            + KEY_ENTITY_TYPE_FK_ID +" INTEGER NOT NULL, "
            + KEY_SERVER_ID +" INTEGER DEFAULT NULL, "
            + KEY_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL DEFAULT 3, "
            + KEY_NAME + " TEXT, "
            + KEY_DESCRIPTION + " TEXT, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + "PRIMARY KEY (" + KEY_ID + "),"
            + "FOREIGN KEY (" + KEY_ENTITY_FK_ID +","+ KEY_ENTITY_TYPE_FK_ID + ") "
            + "REFERENCES " + TABLE_tblENTITY +"("+ KEY_ENTITY_FK_ID +","+ KEY_ENTITY_TYPE_FK_ID +") "
            + "ON DELETE CASCADE ON UPDATE CASCADE);";

    //-- --------------------------------------------------------------------------------------------
    //-- Table `tblPUBLISHER`
    //-- --------------------------------------------------------------------------------------------
    public static final String CREATE_TABLE_tblPUBLISHER = "CREATE TABLE " + TABLE_tblPUBLISHER + "("
            + KEY_PUBLISHER_FK_ID +" INTEGER NOT NULL, "
            + KEY_NOTIFICATION_FK_ID +" INTEGER NOT NULL, "
            + KEY_SERVER_ID +" INTEGER DEFAULT NULL, "
            + KEY_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL DEFAULT 3, "
            + KEY_NAME + " TEXT, "
            + KEY_DESCRIPTION + " TEXT, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + " PRIMARY KEY (" + KEY_PUBLISHER_FK_ID + "," + KEY_NOTIFICATION_FK_ID +"),"
            + " FOREIGN KEY (" + KEY_PUBLISHER_FK_ID + ") "
            + " REFERENCES " + TABLE_tblUSER+" ("+ KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_NOTIFICATION_FK_ID + ") "
            + " REFERENCES " + TABLE_tblNOTIFICATION +" ("+ KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE); ";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblSUBSCRIBER`
    //-- -------------------------------------------------------------------------------------------
    public static final String CREATE_TABLE_tblSUBSCRIBER = "CREATE TABLE " + TABLE_tblSUBSCRIBER + "("
            + KEY_SUBSCRIBER_FK_ID +" INTEGER NOT NULL, "
            + KEY_NOTIFICATION_FK_ID +" INTEGER NOT NULL, "
            + KEY_SERVER_ID +" INTEGER DEFAULT NULL, "
            + KEY_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL DEFAULT 3, "
            + KEY_NAME + " TEXT, "
            + KEY_DESCRIPTION + " TEXT, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + " PRIMARY KEY (" + KEY_SUBSCRIBER_FK_ID + "," + KEY_NOTIFICATION_FK_ID + "),"
            + " FOREIGN KEY (" + KEY_SUBSCRIBER_FK_ID + ") "
            + " REFERENCES " + TABLE_tblUSER+" ("+ KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_NOTIFICATION_FK_ID + ") "
            + " REFERENCES " + TABLE_tblNOTIFICATION +" ("+ KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE); ";

    //-- --------------------------------------------------------------------------------------------------------
    //-- Table `tblSETNOTIFICATION`
    //-- --------------------------------------------------------------------------------------------------------
    public static final String CREATE_TABLE_tblSETNOTIFICATION = "CREATE TABLE " + TABLE_tblSETNOTIFICATION + "("
            + KEY_NOTIFICATION_FK_ID +" INTEGER NOT NULL, "
            + KEY_SETTING_FK_ID +" INTEGER NOT NULL, "
            + KEY_SERVER_ID +" INTEGER DEFAULT NULL, "
            + KEY_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL DEFAULT 3, "
            + KEY_NAME + " TEXT, "
            + KEY_DESCRIPTION + " TEXT, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + " PRIMARY KEY (" + KEY_NOTIFICATION_FK_ID + "," + KEY_SETTING_FK_ID + "),"
            + " FOREIGN KEY (" + KEY_NOTIFICATION_FK_ID + ") "
            + " REFERENCES " + TABLE_tblNOTIFICATION+" ("+ KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_SETTING_FK_ID + ") "
            + " REFERENCES " + TABLE_tblSETTING +" ("+ KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE); ";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblSUBPUB`
    //-- -------------------------------------------------------------------------------------------
    public static final String CREATE_TABLE_tbltblSUBPUB = "CREATE TABLE " + TABLE_tblSUBPUB + "("
            + KEY_SUBSCRIBER_FK_ID +" INTEGER NOT NULL, "
            + KEY_NOTIFICATION_FK_ID +" INTEGER NOT NULL, "
            + KEY_SERVER_ID +" INTEGER DEFAULT NULL, "
            + KEY_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL DEFAULT 3, "
            + KEY_NAME + " TEXT, "
            + KEY_DESCRIPTION + " TEXT, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + " PRIMARY KEY (" + KEY_SUBSCRIBER_FK_ID + "," + KEY_NOTIFICATION_FK_ID + "),"
            + " FOREIGN KEY (" + KEY_SUBSCRIBER_FK_ID + ") "
            + " REFERENCES " + TABLE_tblUSER+" ("+ KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_NOTIFICATION_FK_ID + ") "
            + " REFERENCES " + TABLE_tblNOTIFICATION +" ("+ KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE); ";

    //-- -----------------------------------------------------------------------------------------------
    //-- Table `tblACTIVITYLOG`
    //-- -----------------------------------------------------------------------------------------------
    public static final String CREATE_TABLE_tblACTIVITYLOG = "CREATE TABLE "+ TABLE_tblACTIVITYLOG + "("
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
            + KEY_END_DATE + " DATE NOT NULL, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP );";

    //##################################### END of BRBAC MODULE ####################################




    //################################### START PLANNING MODULE ###################################

    //-- ----------------------------------------------------------------------------------------
    //-- Table `tblLOGFRAME`
    //-- ----------------------------------------------------------------------------------------
    public static final String CREATE_TABLE_tblLOGFRAME = "CREATE TABLE "+ TABLE_tblLOGFRAME + "("
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
            + KEY_END_DATE + " DATE NOT NULL, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP );";

    //-- -------------------------------------------------------------------------------------------------
    //-- Table `tblLOGFRAMETREE`
    //-- -------------------------------------------------------------------------------------------------
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
            + " REFERENCES " + TABLE_tblLOGFRAME +" ("+ KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_CHILD_FK_ID + ")"
            + " REFERENCES " + TABLE_tblLOGFRAME +" ("+ KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE);";

    //-- -------------------------------------------------------------------------------------
    //-- Table `tblIMPACT`
    //-- -------------------------------------------------------------------------------------
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
            + " FOREIGN KEY (" + KEY_CHILD_FK_ID + ") "
            + " REFERENCES " + TABLE_tblIMPACT +" ("+ KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_LOGFRAME_FK_ID + ")"
            + " REFERENCES " + TABLE_tblLOGFRAME +" ("+ KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE);";

    //-- ---------------------------------------------------------------------------------------
    //-- Table `tblOUTCOME`
    //-- ---------------------------------------------------------------------------------------
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
            + " FOREIGN KEY (" + KEY_CHILD_FK_ID + ") "
            + " REFERENCES " + TABLE_tblOUTCOME +" ("+ KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_IMPACT_FK_ID + ") "
            + " REFERENCES " + TABLE_tblIMPACT +" ("+ KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_LOGFRAME_FK_ID + ")"
            + " REFERENCES " + TABLE_tblLOGFRAME +" ("+ KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE);";

    //-- -------------------------------------------------------------------------------------
    //-- Table `tblOUTPUT`
    //-- -------------------------------------------------------------------------------------
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
            + " FOREIGN KEY (" + KEY_CHILD_FK_ID + ") "
            + " REFERENCES " + TABLE_tblOUTPUT +" ("+ KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_OUTCOME_FK_ID + ") "
            + " REFERENCES " + TABLE_tblOUTCOME +" ("+ KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_LOGFRAME_FK_ID + ")"
            + " REFERENCES " + TABLE_tblLOGFRAME +" ("+ KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE);";

    //-- -----------------------------------------------------------------------------------------
    //-- Table `tblACTIVITY`
    //-- -----------------------------------------------------------------------------------------
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
            + " FOREIGN KEY (" + KEY_CHILD_FK_ID + ") "
            + " REFERENCES " + TABLE_tblACTIVITY +" ("+ KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_OUTPUT_FK_ID + ") "
            + " REFERENCES " + TABLE_tblOUTPUT +" ("+ KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_LOGFRAME_FK_ID + ")"
            + " REFERENCES " + TABLE_tblLOGFRAME +" ("+ KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE);";

    //-- -----------------------------------------------------------------------------------------------------------
    //-- Table `tblPRECEDINGACTIVITY`
    //-- -----------------------------------------------------------------------------------------------------------
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
            + " FOREIGN KEY (" + KEY_ACTIVITY_FK_ID + ") "
            + " REFERENCES " + TABLE_tblACTIVITY +" ("+ KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_PRECEDING_ACTIVITY_FK_ID + ")"
            + " REFERENCES " + TABLE_tblACTIVITY +" ("+ KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE);";

    //-- -----------------------------------------------------------------------------------
    //-- Table `tblINPUT`
    //-- -----------------------------------------------------------------------------------
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
            + " FOREIGN KEY (" + KEY_ACTIVITY_FK_ID + ") "
            + " REFERENCES " + TABLE_tblACTIVITY +" ("+ KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_LOGFRAME_FK_ID + ")"
            + " REFERENCES " + TABLE_tblLOGFRAME +" ("+ KEY_ID + ")"
            + " ON DELETE CASCADE ON UPDATE CASCADE);";


    //-- ---------------------------------------------------------------------------------------------------
    //-- Table `tblHUMANSET`
    //-- ---------------------------------------------------------------------------------------------------
    public static final String CREATE_TABLE_tblHUMANSET = "CREATE TABLE "+ TABLE_tblHUMANSET + "("
            + KEY_INPUT_FK_ID +" INTEGER NOT NULL, "
            + KEY_SERVER_ID +" INTEGER NOT NULL, "
            + KEY_OWNER_ID +" INTEGER NOT NULL, "
            + KEY_ORG_ID + " INTEGER NOT NULL, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + " PRIMARY KEY (" + KEY_INPUT_FK_ID +" ), "
            + " FOREIGN KEY (" + KEY_INPUT_FK_ID + ") "
            + " REFERENCES " + TABLE_tblINPUT +" ("+ KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE); ";

    //-- ---------------------------------------------------------------------------------------------------
    //-- Table `tblMATERIAL`
    //-- ---------------------------------------------------------------------------------------------------
    public static final String CREATE_TABLE_tblMATERIAL = "CREATE TABLE "+ TABLE_tblMATERIAL + "("
            + KEY_INPUT_FK_ID +" INTEGER NOT NULL, "
            + KEY_SERVER_ID +" INTEGER NOT NULL, "
            + KEY_OWNER_ID +" INTEGER NOT NULL, "
            + KEY_ORG_ID + " INTEGER NOT NULL, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + " PRIMARY KEY (" + KEY_INPUT_FK_ID +" ), "
            + " FOREIGN KEY (" + KEY_INPUT_FK_ID + ") "
            + " REFERENCES " + TABLE_tblINPUT +" ("+ KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE); ";

    //-- ---------------------------------------------------------------------------------------------------
    //-- Table `tblBUDGET`
    //-- ---------------------------------------------------------------------------------------------------
    public static final String CREATE_TABLE_tblBUDGET = "CREATE TABLE "+ TABLE_tblBUDGET + "("
            + KEY_INPUT_FK_ID +" INTEGER NOT NULL, "
            + KEY_ACCOUNT_FK_ID +" INTEGER NOT NULL, "
            + KEY_SERVER_ID +" INTEGER NOT NULL, "
            + KEY_OWNER_ID +" INTEGER NOT NULL, "
            + KEY_ORG_ID + " INTEGER NOT NULL, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + " PRIMARY KEY (" + KEY_INPUT_FK_ID +" ), "
            + " FOREIGN KEY (" + KEY_INPUT_FK_ID + ") "
            + " REFERENCES " + TABLE_tblINPUT + " ("+ KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_ACCOUNT_FK_ID + ") "
            + " REFERENCES " + TABLE_tblACCOUNT +" ("+ KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE); ";


    //-- -----------------------------------------------------------------------------------------------------
    //-- Table `tblOUTCOME_IMPACT`
    //-- -----------------------------------------------------------------------------------------------------
    public static final String CREATE_TABLE_tblOUTCOME_IMPACT = "CREATE TABLE "+ TABLE_tblOUTCOME_IMPACT + "("
            + KEY_OUTCOME_FK_ID +" INTEGER NOT NULL, "
            + KEY_IMPACT_FK_ID +" INTEGER NOT NULL, "
            + KEY_PARENT_FK_ID +" INTEGER NOT NULL, "
            + KEY_CHILD_FK_ID +" INTEGER NOT NULL, "
            + KEY_SERVER_ID +" INTEGER NOT NULL, "
            + KEY_OWNER_ID +" INTEGER NOT NULL, "
            + KEY_ORG_ID + " INTEGER NOT NULL, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + " PRIMARY KEY (" + KEY_OUTCOME_FK_ID + "," + KEY_IMPACT_FK_ID + ","
            + KEY_PARENT_FK_ID + "," + KEY_CHILD_FK_ID +" ), "
            + " FOREIGN KEY (" + KEY_OUTCOME_FK_ID + ") "
            + " REFERENCES " + TABLE_tblOUTCOME +" ("+ KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_IMPACT_FK_ID + ") "
            + " REFERENCES " + TABLE_tblIMPACT +" ("+ KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_PARENT_FK_ID +","+ KEY_CHILD_FK_ID + ")"
            + " REFERENCES " + TABLE_tblLOGFRAMETREE +" (" + KEY_PARENT_FK_ID +","+ KEY_CHILD_FK_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE);";

    //-- -----------------------------------------------------------------------------------------------------
    //-- Table `tblOUTPUT_OUTCOME`
    //-- -----------------------------------------------------------------------------------------------------
    public static final String CREATE_TABLE_tblOUTPUT_OUTCOME = "CREATE TABLE "+ TABLE_tblOUTPUT_OUTCOME + "("
            + KEY_OUTPUT_FK_ID +" INTEGER NOT NULL, "
            + KEY_OUTCOME_FK_ID +" INTEGER NOT NULL, "
            + KEY_PARENT_FK_ID +" INTEGER NOT NULL, "
            + KEY_CHILD_FK_ID +" INTEGER NOT NULL, "
            + KEY_SERVER_ID +" INTEGER NOT NULL, "
            + KEY_OWNER_ID +" INTEGER NOT NULL, "
            + KEY_ORG_ID + " INTEGER NOT NULL, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + " PRIMARY KEY (" + KEY_OUTPUT_FK_ID + "," + KEY_OUTCOME_FK_ID + ","
            + KEY_PARENT_FK_ID + "," + KEY_CHILD_FK_ID +" ), "
            + " FOREIGN KEY (" + KEY_OUTPUT_FK_ID + ") "
            + " REFERENCES " + TABLE_tblOUTPUT +" ("+ KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_OUTCOME_FK_ID + ") "
            + " REFERENCES " + TABLE_tblOUTCOME +" ("+ KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_PARENT_FK_ID +","+ KEY_CHILD_FK_ID + ")"
            + " REFERENCES " + TABLE_tblLOGFRAMETREE +" (" + KEY_PARENT_FK_ID +","+ KEY_CHILD_FK_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE);";

    //-- -------------------------------------------------------------------------------------------------------
    //-- Table `tblACTIVITY_OUTPUT`
    //-- -------------------------------------------------------------------------------------------------------
    public static final String CREATE_TABLE_tblACTIVITY_OUTPUT = "CREATE TABLE "+ TABLE_tblACTIVITY_OUTPUT + "("
            + KEY_ACTIVITY_FK_ID +" INTEGER NOT NULL, "
            + KEY_OUTPUT_FK_ID +" INTEGER NOT NULL, "
            + KEY_PARENT_FK_ID +" INTEGER NOT NULL, "
            + KEY_CHILD_FK_ID +" INTEGER NOT NULL, "
            + KEY_SERVER_ID +" INTEGER NOT NULL, "
            + KEY_OWNER_ID +" INTEGER NOT NULL, "
            + KEY_ORG_ID + " INTEGER NOT NULL, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + " PRIMARY KEY (" + KEY_ACTIVITY_FK_ID + "," + KEY_OUTPUT_FK_ID + ","
            + KEY_PARENT_FK_ID + "," + KEY_CHILD_FK_ID +" ), "
            + " FOREIGN KEY (" + KEY_ACTIVITY_FK_ID + ") "
            + " REFERENCES " + TABLE_tblACTIVITY +" ("+ KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_OUTPUT_FK_ID + ") "
            + " REFERENCES " + TABLE_tblOUTPUT +" ("+ KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_PARENT_FK_ID +","+ KEY_CHILD_FK_ID + ")"
            + " REFERENCES " + TABLE_tblLOGFRAMETREE +" (" + KEY_PARENT_FK_ID +","+ KEY_CHILD_FK_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE); ";

    //-- -----------------------------------------------------------------------------------------
    //-- Table `tblCRITERIA`
    //-- -----------------------------------------------------------------------------------------
    public static final String CREATE_TABLE_tblCRITERIA = "CREATE TABLE "+ TABLE_tblCRITERIA + "("
            + KEY_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "
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
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP); ";

    //-- -----------------------------------------------------------------------------------------------------------
    //-- Table `tblQUESTION_GROUPING`
    //-- -----------------------------------------------------------------------------------------------------------
    public static final String CREATE_TABLE_tblQUESTION_GROUPING = "CREATE TABLE "+ TABLE_tblQUESTION_GROUPING + "("
            + KEY_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "
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
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP); ";

    //-- ---------------------------------------------------------------------------------------------------
    //-- Table `tblQUESTION_TYPE`
    //-- ---------------------------------------------------------------------------------------------------
    public static final String CREATE_TABLE_tblQUESTION_TYPE = "CREATE TABLE "+ TABLE_tblQUESTION_TYPE + "("
            + KEY_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "
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
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP); ";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblCHOICESET`
    //-- -------------------------------------------------------------------------------------------
    public static final String CREATE_TABLE_tblCHOICESET = "CREATE TABLE "+ TABLE_tblCHOICESET + "("
            + KEY_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "
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
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP); ";

    //-- -----------------------------------------------------------------------------------------
    //-- Table `tblQUESTION`
    //-- -----------------------------------------------------------------------------------------
    public static final String CREATE_TABLE_tblQUESTION = "CREATE TABLE "+ TABLE_tblQUESTION + "("
            + KEY_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_LOGFRAME_FK_ID +" INTEGER NOT NULL, "
            + KEY_QUESTION_GROUPING_FK_ID +" INTEGER NOT NULL, "
            + KEY_QUESTION_TYPE_FK_ID +" INTEGER NOT NULL, "
            + KEY_CHOICESET_FK_ID +" INTEGER NOT NULL, "
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
            + " FOREIGN KEY (" + KEY_QUESTION_GROUPING_FK_ID + ") "
            + " REFERENCES " + TABLE_tblQUESTION_GROUPING +" ("+ KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_QUESTION_TYPE_FK_ID + ") "
            + " REFERENCES " + TABLE_tblQUESTION_TYPE +" ("+ KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_CHOICESET_FK_ID + ") "
            + " REFERENCES " + TABLE_tblCHOICESET +" ("+ KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_LOGFRAME_FK_ID + ")"
            + " REFERENCES " + TABLE_tblLOGFRAME +" ("+ KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE);";

    //-- -------------------------------------------------------------------------------------------------------
    //-- Table `tblIMPACT_QUESTION`
    //-- -------------------------------------------------------------------------------------------------------
    public static final String CREATE_TABLE_tblIMPACT_QUESTION = "CREATE TABLE "+ TABLE_tblIMPACT_QUESTION + "("
            + KEY_IMPACT_FK_ID +" INTEGER NOT NULL, "
            + KEY_QUESTION_FK_ID +" INTEGER NOT NULL, "
            + KEY_CRITERIA_FK_ID +" INTEGER NOT NULL, "
            + KEY_SERVER_ID +" INTEGER NOT NULL, "
            + KEY_OWNER_ID +" INTEGER NOT NULL, "
            + KEY_ORG_ID + " INTEGER NOT NULL, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + " PRIMARY KEY (" + KEY_IMPACT_FK_ID + "," + KEY_QUESTION_FK_ID + "," + KEY_CRITERIA_FK_ID +" ), "
            + " FOREIGN KEY (" + KEY_IMPACT_FK_ID + ") "
            + " REFERENCES " + TABLE_tblIMPACT +" ("+ KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_QUESTION_FK_ID + ") "
            + " REFERENCES " + TABLE_tblQUESTION +" ("+ KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_CRITERIA_FK_ID +")"
            + " REFERENCES " + TABLE_tblCRITERIA +" (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE);";

    //-- ---------------------------------------------------------------------------------------------------------
    //-- Table `tblOUTCOME_QUESTION`
    //-- ---------------------------------------------------------------------------------------------------------
    public static final String CREATE_TABLE_tblOUTCOME_QUESTION = "CREATE TABLE "+ TABLE_tblOUTCOME_QUESTION + "("
            + KEY_OUTCOME_FK_ID +" INTEGER NOT NULL, "
            + KEY_QUESTION_FK_ID +" INTEGER NOT NULL, "
            + KEY_CRITERIA_FK_ID +" INTEGER NOT NULL, "
            + KEY_SERVER_ID +" INTEGER NOT NULL, "
            + KEY_OWNER_ID +" INTEGER NOT NULL, "
            + KEY_ORG_ID + " INTEGER NOT NULL, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + " PRIMARY KEY (" + KEY_OUTCOME_FK_ID + "," + KEY_QUESTION_FK_ID + "," + KEY_CRITERIA_FK_ID +" ), "
            + " FOREIGN KEY (" + KEY_OUTCOME_FK_ID + ") "
            + " REFERENCES " + TABLE_tblOUTCOME +" ("+ KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_QUESTION_FK_ID + ") "
            + " REFERENCES " + TABLE_tblQUESTION +" ("+ KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_CRITERIA_FK_ID +")"
            + " REFERENCES " + TABLE_tblCRITERIA +" (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE);";

    //-- -------------------------------------------------------------------------------------------------------
    //-- Table `tblOUTPUT_QUESTION`
    //-- -------------------------------------------------------------------------------------------------------
    public static final String CREATE_TABLE_tblOUTPUT_QUESTION = "CREATE TABLE "+ TABLE_tblOUTPUT_QUESTION + "("
            + KEY_OUTPUT_FK_ID +" INTEGER NOT NULL, "
            + KEY_QUESTION_FK_ID +" INTEGER NOT NULL, "
            + KEY_CRITERIA_FK_ID +" INTEGER NOT NULL, "
            + KEY_SERVER_ID +" INTEGER NOT NULL, "
            + KEY_OWNER_ID +" INTEGER NOT NULL, "
            + KEY_ORG_ID + " INTEGER NOT NULL, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + " PRIMARY KEY (" + KEY_OUTPUT_FK_ID + "," + KEY_QUESTION_FK_ID + "," + KEY_CRITERIA_FK_ID +" ), "
            + " FOREIGN KEY (" + KEY_OUTPUT_FK_ID + ") "
            + " REFERENCES " + TABLE_tblOUTPUT +" ("+ KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_QUESTION_FK_ID + ") "
            + " REFERENCES " + TABLE_tblQUESTION +" ("+ KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_CRITERIA_FK_ID +")"
            + " REFERENCES " + TABLE_tblCRITERIA +" (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE);";

    //-- -----------------------------------------------------------------------------------------------------------
    //-- Table `tblACTIVITY_QUESTION`
    //-- -----------------------------------------------------------------------------------------------------------
    public static final String CREATE_TABLE_tblACTIVITY_QUESTION = "CREATE TABLE "+ TABLE_tblACTIVITY_QUESTION + "("
            + KEY_ACTIVITY_FK_ID +" INTEGER NOT NULL, "
            + KEY_QUESTION_FK_ID +" INTEGER NOT NULL, "
            + KEY_CRITERIA_FK_ID +" INTEGER NOT NULL, "
            + KEY_SERVER_ID +" INTEGER NOT NULL, "
            + KEY_OWNER_ID +" INTEGER NOT NULL, "
            + KEY_ORG_ID + " INTEGER NOT NULL, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + " PRIMARY KEY (" + KEY_ACTIVITY_FK_ID + "," + KEY_QUESTION_FK_ID + "," + KEY_CRITERIA_FK_ID +" ), "
            + " FOREIGN KEY (" + KEY_ACTIVITY_FK_ID + ") "
            + " REFERENCES " + TABLE_tblACTIVITY +" ("+ KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_QUESTION_FK_ID + ") "
            + " REFERENCES " + TABLE_tblQUESTION +" ("+ KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_CRITERIA_FK_ID +")"
            + " REFERENCES " + TABLE_tblCRITERIA +" (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE);";

    //-- -----------------------------------------------------------------------------------------------------
    //-- Table `tblINPUT_QUESTION`
    //-- -----------------------------------------------------------------------------------------------------
    public static final String CREATE_TABLE_tblINPUT_QUESTION = "CREATE TABLE "+ TABLE_tblINPUT_QUESTION + "("
            + KEY_INPUT_FK_ID +" INTEGER NOT NULL, "
            + KEY_QUESTION_FK_ID +" INTEGER NOT NULL, "
            + KEY_CRITERIA_FK_ID +" INTEGER NOT NULL, "
            + KEY_SERVER_ID +" INTEGER NOT NULL, "
            + KEY_OWNER_ID +" INTEGER NOT NULL, "
            + KEY_ORG_ID + " INTEGER NOT NULL, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + " PRIMARY KEY (" + KEY_INPUT_FK_ID + "," + KEY_QUESTION_FK_ID + "," + KEY_CRITERIA_FK_ID +" ), "
            + " FOREIGN KEY (" + KEY_INPUT_FK_ID + ") "
            + " REFERENCES " + TABLE_tblINPUT +" ("+ KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_QUESTION_FK_ID + ") "
            + " REFERENCES " + TABLE_tblQUESTION +" ("+ KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_CRITERIA_FK_ID +")"
            + " REFERENCES " + TABLE_tblCRITERIA +" (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE);";

    //-- ---------------------------------------------------------------------------------
    //-- Table `tblRAID`
    //-- ---------------------------------------------------------------------------------
    public static final String CREATE_TABLE_tblRAID = "CREATE TABLE "+ TABLE_tblRAID + "("
            + KEY_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "
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
            + " FOREIGN KEY (" + KEY_LOGFRAME_FK_ID + ")"
            + " REFERENCES " + TABLE_tblLOGFRAME +" ("+ KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE);";

    //-- -----------------------------------------------------------------------------------------------
    //-- Table `tblIMPACT_RAID`
    //-- -----------------------------------------------------------------------------------------------
    public static final String CREATE_TABLE_tblIMPACT_RAID = "CREATE TABLE "+ TABLE_tblIMPACT_RAID + "("
            + KEY_IMPACT_FK_ID +" INTEGER NOT NULL, "
            + KEY_RAID_FK_ID +" INTEGER NOT NULL, "
            + KEY_SERVER_ID +" INTEGER NOT NULL, "
            + KEY_OWNER_ID +" INTEGER NOT NULL, "
            + KEY_ORG_ID + " INTEGER NOT NULL, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + " PRIMARY KEY (" + KEY_IMPACT_FK_ID + "," + KEY_RAID_FK_ID +" ), "
            + " FOREIGN KEY (" + KEY_IMPACT_FK_ID + ") "
            + " REFERENCES " + TABLE_tblIMPACT +" ("+ KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_RAID_FK_ID + ") "
            + " REFERENCES " + TABLE_tblRAID +" ("+ KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE); ";

    //-- -------------------------------------------------------------------------------------------------
    //-- Table `tblOUTCOME_RAID`
    //-- -------------------------------------------------------------------------------------------------
    public static final String CREATE_TABLE_tblOUTCOME_RAID = "CREATE TABLE "+ TABLE_tblOUTCOME_RAID + "("
            + KEY_OUTCOME_FK_ID +" INTEGER NOT NULL, "
            + KEY_RAID_FK_ID +" INTEGER NOT NULL, "
            + KEY_SERVER_ID +" INTEGER NOT NULL, "
            + KEY_OWNER_ID +" INTEGER NOT NULL, "
            + KEY_ORG_ID + " INTEGER NOT NULL, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + " PRIMARY KEY (" + KEY_OUTCOME_FK_ID + "," + KEY_RAID_FK_ID +" ), "
            + " FOREIGN KEY (" + KEY_OUTCOME_FK_ID + ") "
            + " REFERENCES " + TABLE_tblOUTCOME +" ("+ KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_RAID_FK_ID + ") "
            + " REFERENCES " + TABLE_tblRAID +" ("+ KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE); ";

    //-- -----------------------------------------------------------------------------------------------
    //-- Table `tblOUTPUT_RAID`
    //-- -----------------------------------------------------------------------------------------------
    public static final String CREATE_TABLE_tblOUTPUT_RAID = "CREATE TABLE "+ TABLE_tblOUTPUT_RAID + "("
            + KEY_OUTPUT_FK_ID +" INTEGER NOT NULL, "
            + KEY_RAID_FK_ID +" INTEGER NOT NULL, "
            + KEY_SERVER_ID +" INTEGER NOT NULL, "
            + KEY_OWNER_ID +" INTEGER NOT NULL, "
            + KEY_ORG_ID + " INTEGER NOT NULL, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + " PRIMARY KEY (" + KEY_OUTPUT_FK_ID + "," + KEY_RAID_FK_ID +" ), "
            + " FOREIGN KEY (" + KEY_OUTPUT_FK_ID + ") "
            + " REFERENCES " + TABLE_tblOUTPUT +" ("+ KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_RAID_FK_ID + ") "
            + " REFERENCES " + TABLE_tblRAID +" ("+ KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE); ";

    //-- ---------------------------------------------------------------------------------------------------
    //-- Table `tblACTIVITY_RAID`
    //-- ---------------------------------------------------------------------------------------------------
    public static final String CREATE_TABLE_tblACTIVITY_RAID = "CREATE TABLE "+ TABLE_tblACTIVITY_RAID + "("
            + KEY_ACTIVITY_FK_ID +" INTEGER NOT NULL, "
            + KEY_RAID_FK_ID +" INTEGER NOT NULL, "
            + KEY_SERVER_ID +" INTEGER NOT NULL, "
            + KEY_OWNER_ID +" INTEGER NOT NULL, "
            + KEY_ORG_ID + " INTEGER NOT NULL, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + " PRIMARY KEY (" + KEY_ACTIVITY_FK_ID + "," + KEY_RAID_FK_ID +" ), "
            + " FOREIGN KEY (" + KEY_ACTIVITY_FK_ID + ") "
            + " REFERENCES " + TABLE_tblACTIVITY+" ("+ KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_RAID_FK_ID + ") "
            + " REFERENCES " + TABLE_tblRAID +" ("+ KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE); ";


    //######################################## END PLANNING MODULE ########################################



    // TYPE table - column names
    public static final String KEY_TYPE_ID          = "_id";
    public static final String KEY_TYPE_OWNER_ID    = "_id_owner";
    public static final String KEY_TYPE_GROUP_BITS  = "_bits_group";
    public static final String KEY_TYPE_PERMS_BITS  = "_bits_perms";
    public static final String KEY_TYPE_TYPE_BITS   = "_bits_type";
    public static final String KEY_TYPE_STATUS_BITS = "_bits_status";
    public static final String KEY_TYPE_NAME        = "name";
    public static final String KEY_TYPE_DESCRIPTION = "description";
    public static final String KEY_TYPE_DATE        = "date";

    // TYPE table - create statement
    public static final String CREATE_TABLE_TYPE = "CREATE TABLE " + TABLE_TYPE + "("
            + KEY_TYPE_ID +" INTEGER PRIMARY KEY, "
            + KEY_TYPE_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_TYPE_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_TYPE_PERMS_BITS + " INTEGER NOT NULL DEFAULT 500, "
            + KEY_TYPE_TYPE_BITS + " INTEGER NOT NULL DEFAULT 0, "
            + KEY_TYPE_STATUS_BITS + " INTEGER NOT NULL DEFAULT 0, "
            + KEY_TYPE_NAME + " TEXT, "
            + KEY_TYPE_DESCRIPTION + " TEXT, "
            + KEY_TYPE_DATE + " DATE " + ")";


    // NOTIFICATION Table - column names
    public static final String KEY_NOTIFICATION_ID          = "_id";
    public static final String KEY_NOTIFICATION_OWNER_ID    = "_id_owner";
    public static final String KEY_NOTIFICATION_GROUP_BITS  = "_bits_group";
    public static final String KEY_NOTIFICATION_PERMS_BITS  = "_bits_perms";
    public static final String KEY_NOTIFICATION_TYPE_BITS   = "_bits_type";
    public static final String KEY_NOTIFICATION_STATUS_BITS = "_bits_status";
    public static final String KEY_NOTIFICATION_NAME        = "name";
    public static final String KEY_NOTIFICATION_DESCRIPTION = "description";
    public static final String KEY_NOTIFICATION_DATE        = "date";

    // ACTION table - create statement
    public static final String CREATE_TABLE_NOTIFICATION = "CREATE TABLE " + TABLE_NOTIFICATION + "("
            + KEY_NOTIFICATION_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_NOTIFICATION_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_NOTIFICATION_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_NOTIFICATION_PERMS_BITS + " INTEGER NOT NULL DEFAULT 500, "
            + KEY_NOTIFICATION_TYPE_BITS + " INTEGER NOT NULL DEFAULT 0, "
            + KEY_NOTIFICATION_STATUS_BITS + " INTEGER NOT NULL DEFAULT 0, "
            + KEY_NOTIFICATION_NAME + " TEXT, "
            + KEY_NOTIFICATION_DESCRIPTION + " TEXT, "
            + KEY_NOTIFICATION_DATE + " DATE " + ")";

//=== START DIMENTIONS

    // ACTIVITYLOG table - column names
    public static final String KEY_ACTIVITYLOG_TIMESTAMP_ID = "_id_timestamp";
    public static final String KEY_ACTIVITYLOG_OWNER_ID     = "_id_owner";
    public static final String KEY_ACTIVITYLOG_NAME         = "name";
    public static final String KEY_ACTIVITYLOG_DESCRIPTION  = "description";
    public static final String KEY_ACTIVITYLOG_DATE         = "date";

    // ACTIVITYLOG table - create statement
    public static final String CREATE_TABLE_ACTIVITYLOG = "CREATE TABLE " + TABLE_ACTIVITYLOG + "("
            + KEY_USER_ID +" INTEGER NOT NULL, "
            + KEY_ACTIVITYLOG_TIMESTAMP_ID +" INTEGER NOT NULL, "
            + KEY_ACTIVITYLOG_OWNER_ID + " INTEGER NOT NULL, "
            + KEY_ACTIVITYLOG_NAME + " TEXT, "
            + KEY_ACTIVITYLOG_DESCRIPTION + " TEXT, "
            + KEY_ACTIVITYLOG_DATE + " DATE, "
            + "PRIMARY KEY (" + KEY_USER_ID + " ," + KEY_ACTIVITYLOG_TIMESTAMP_ID +" ));";

    // CATEGORY table - column names
    private static final String KEY_CATEGORY_ID       = "_id";
    private static final String KEY_CATEGORY_OWNER_ID = "_id_owner";
    private static final String KEY_CATEGORY_NAME     = "name";
    private static final String KEY_CATEGORY_DATE     = "date";

    // CATEGORY table - create statement
    public static final String CREATE_TABLE_CATEGORY = "CREATE TABLE " + TABLE_CATEGORY + "("
            + KEY_CATEGORY_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_CATEGORY_OWNER_ID + " INTEGER NOT NULL, "
            + KEY_CATEGORY_NAME + " TEXT, "
            + KEY_CATEGORY_DATE + " DATE " + ")";

    // DATE table - column names
    public static final String KEY_DATE_ID         = "_id";
    public static final String KEY_DATE_OWNER_ID   = "_id_owner";
    public static final String KEY_DATE_YEAR       = "year";
    public static final String KEY_DATE_MONTH      = "month";
    public static final String KEY_DATE_QUARTER    = "quarter";
    public static final String KEY_DATE_DAY_MONTH  = "day_month";
    public static final String KEY_DATE_DAY_WEEK   = "day_week";
    public static final String KEY_DATE_DAY_YEAR   = "day_year";
    public static final String KEY_DATE_DAY_WEEK_MONTH = "day_week_month";
    public static final String KEY_DATE_WEEK_YEAR     = "week_year";
    public static final String KEY_DATE_WEEK_MONTH    = "week_month";
    public static final String KEY_DATE_NAME_QUARTER  = "name_quarter";
    public static final String KEY_DATE_NAME_MONTH    = "name_month";
    public static final String KEY_DATE_NAME_WEEK_DAY = "name_week_day";
    public static final String KEY_DATE_DATE          = "date";

    // DATE table - create statement
    public static final String CREATE_TABLE_DATE = "CREATE TABLE " + TABLE_DATE + "("
            + KEY_DATE_ID +" INTEGER PRIMARY KEY, "
            + KEY_DATE_OWNER_ID + " INTEGER NOT NULL, "
            + KEY_DATE_YEAR + " INTEGER NOT NULL, "
            + KEY_DATE_MONTH + " INTEGER NOT NULL, "
            + KEY_DATE_QUARTER + " INTEGER NOT NULL, "
            + KEY_DATE_DAY_MONTH + " INTEGER NOT NULL, "
            + KEY_DATE_DAY_WEEK + " INTEGER NOT NULL, "
            + KEY_DATE_DAY_YEAR + " INTEGER NOT NULL, "
            + KEY_DATE_DAY_WEEK_MONTH + " INTEGER NOT NULL, "
            + KEY_DATE_WEEK_YEAR + " INTEGER NOT NULL, "
            + KEY_DATE_WEEK_MONTH + " INTEGER NOT NULL, "
            + KEY_DATE_NAME_QUARTER + " INTEGER NOT NULL, "
            + KEY_DATE_NAME_MONTH + " INTEGER NOT NULL, "
            + KEY_DATE_NAME_WEEK_DAY + " INTEGER NOT NULL, "
            + KEY_DATE_DATE + " DATE " + ")";

    // UNIT table - column names
    public static final String KEY_UNIT_ID       = "_id";
    public static final String KEY_UNIT_OWNER_ID = "_id_owner";
    public static final String KEY_UNIT_NAME     = "name";
    public static final String KEY_UNIT_DATE     = "date";

    // UNIT table - create statement
    public static final String CREATE_TABLE_UNIT = "CREATE TABLE " + TABLE_UNIT + "("
            + KEY_UNIT_ID +" INTEGER PRIMARY KEY, "
            + KEY_UNIT_OWNER_ID + " INTEGER NOT NULL, "
            + KEY_UNIT_NAME + " TEXT, "
            + KEY_UNIT_DATE + " DATE " + ")";

    // SCALE table - column names
    public static final String KEY_SCALE_ID          = "_id";
    public static final String KEY_SCALE_OWNER_ID    = "_id_owner";
    public static final String KEY_SCALE_START       = "scale_start";
    public static final String KEY_SCALE_END         = "scale_end";
    public static final String KEY_SCALE_NAME        = "name";
    public static final String KEY_SCALE_DESCRIPTION = "description";
    public static final String KEY_SCALE_DATE        = "date";

    // SCALE table - create statement
    public static final String CREATE_TABLE_SCALE = "CREATE TABLE " + TABLE_SCALE + "("
            + KEY_SCALE_ID +" INTEGER PRIMARY KEY, "
            + KEY_SCALE_OWNER_ID + " INTEGER NOT NULL, "
            + KEY_SCALE_START + " INTEGER, "
            + KEY_SCALE_END + " INTEGER, "
            + KEY_SCALE_NAME + " TEXT, "
            + KEY_SCALE_DESCRIPTION + " TEXT, "
            + KEY_SCALE_DATE + " DATE " + ")";

//=== END OF DIMENTIONS


    // OVERALLAIM table - column names
    public static final String KEY_OVERALLAIM_ID          = "_id";
    public static final String KEY_OVERALLAIM_OWNER_ID    = "_id_owner";
    public static final String KEY_OVERALLAIM_NAME        = "name";
    public static final String KEY_OVERALLAIM_DESCRIPTION = "description";
    public static final String KEY_OVERALLAIM_DATE        = "date";

    // OVERALLAIM table - create statement
    public static final String CREATE_TABLE_OVERALLAIM = "CREATE TABLE " + TABLE_OVERALLAIM + "("
            + KEY_OVERALLAIM_ID +" INTEGER PRIMARY KEY, "
            + KEY_ORGANIZATION_FK_ID + " INTEGER NOT NULL, "
            + KEY_OVERALLAIM_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_OVERALLAIM_NAME + " TEXT, "
            + KEY_OVERALLAIM_DESCRIPTION + " TEXT, "
            + KEY_OVERALLAIM_DATE + " DATE, "
            + "FOREIGN KEY (" + KEY_ORGANIZATION_FK_ID + ") REFERENCES " + TABLE_tblORGANIZATION +"("+ KEY_ORGANIZATION_ID +") "
            + "ON DELETE CASCADE);";

    // SPECIFICAIM table - column names
    public static final String KEY_SPECIFICAIM_ID       = "_id";
    public static final String KEY_OVERALLAIM_FK_ID = "_id_overallaim_fk";
    public static final String KEY_SPECIFICAIM_OWNER_ID = "_id_owner";
    public static final String KEY_SPECIFICAIM_NAME     = "name";
    public static final String KEY_SPECIFICAIM_DESCRIPTION = "description";
    public static final String KEY_SPECIFICAIM_DATE     = "date";

    // SPECIFICAIM table - create statement
    public static final String CREATE_TABLE_SPECIFICAIM = "CREATE TABLE " + TABLE_SPECIFICAIM + "("
            + KEY_SPECIFICAIM_ID +" INTEGER PRIMARY KEY, "
            + KEY_OVERALLAIM_FK_ID + " INTEGER NOT NULL, "
            + KEY_SPECIFICAIM_OWNER_ID + " INTEGER NOT NULL, "
            + KEY_SPECIFICAIM_NAME + " TEXT, "
            + KEY_SPECIFICAIM_DESCRIPTION + " TEXT, "
            + KEY_SPECIFICAIM_DATE + " DATE, "
            + "FOREIGN KEY (" + KEY_OVERALLAIM_FK_ID + ") REFERENCES " + TABLE_OVERALLAIM +"("+ KEY_OVERALLAIM_ID +") ON DELETE CASCADE);";

    // PROJECT table column names
    public static final String KEY_PROJECT_ID = "_id";
    public static final String KEY_SPECIFICAIM_FK_ID = "_id_specificaim_fk";
    public static final String KEY_PROJECT_MANAGER_ID = "_id_manager";
    public static final String KEY_PROJECT_OWNER_ID = "_id_owner";
    public static final String KEY_PROJECT_NAME = "name";
    public static final String KEY_PROJECT_DESCRIPTION = "description";
    public static final String KEY_PROJECT_COUNTRY = "country";
    public static final String KEY_PROJECT_REGION = "region";
    public static final String KEY_PROJECT_STATUS = "status";
    public static final String KEY_PROJECT_DATE = "date";
    public static final String KEY_PROJECT_START_DATE = "start_date";
    public static final String KEY_PROJECT_CLOSE_DATE = "close_date";

    // PROJECT table create statement
    public static final String CREATE_TABLE_PROJECT = "CREATE TABLE " + TABLE_PROJECT + "("
            + KEY_PROJECT_ID + " INTEGER PRIMARY KEY, "
            + KEY_OVERALLAIM_FK_ID + " INTEGER NOT NULL, "
            + KEY_SPECIFICAIM_FK_ID + " INTEGER NOT NULL, "
            + KEY_PROJECT_OWNER_ID + " INTEGER NOT NULL, "
            + KEY_PROJECT_MANAGER_ID + " INTEGER NOT NULL, "
            + KEY_PROJECT_NAME + " TEXT, "
            + KEY_PROJECT_DESCRIPTION + " TEXT, "
            + KEY_PROJECT_COUNTRY + " TEXT, "
            + KEY_PROJECT_REGION + " TEXT, "
            + KEY_PROJECT_STATUS + " INTEGER NOT NULL, "
            + KEY_PROJECT_DATE + " DATE, "
            + KEY_PROJECT_START_DATE + " DATE, "
            + KEY_PROJECT_CLOSE_DATE + " DATE, "
            + "FOREIGN KEY (" + KEY_OVERALLAIM_FK_ID + ") REFERENCES " + TABLE_OVERALLAIM +"("+ KEY_OVERALLAIM_ID +") ON DELETE CASCADE, "
            + "FOREIGN KEY (" + KEY_SPECIFICAIM_FK_ID + ") REFERENCES " + TABLE_SPECIFICAIM +"("+ KEY_SPECIFICAIM_ID +") ON DELETE CASCADE);";

    // OUTCOME table - column names
    public static final String KEY_OUTCOME_ID          = "_id";
    public static final String KEY_OUTCOME_OWNER_ID    = "_id_owner";
    public static final String KEY_OUTCOME_NAME        = "name";
    public static final String KEY_OUTCOME_DESCRIPTION = "description";
    public static final String KEY_OUTCOME_DATE        = "date";

    // OUTCOME table - create statement
    public static final String CREATE_TABLE_OUTCOME = "CREATE TABLE " + TABLE_OUTCOME + "("
            + KEY_OUTCOME_ID +" INTEGER PRIMARY KEY, "
            + KEY_OUTCOME_OWNER_ID + " INTEGER NOT NULL, "
            + KEY_OUTCOME_NAME + " TEXT, "
            + KEY_OUTCOME_DESCRIPTION + " TEXT, "
            + KEY_OUTCOME_DATE + " DATE)";

    // OBJECTIVE table - column names
    public static final String KEY_OBJECTIVE_ID          = "_id";
    public static final String KEY_PROJECT_FK_ID         = "_id_project_fk";
    public static final String KEY_OBJECTIVE_OWNER_ID    = "_id_owner";
    public static final String KEY_OBJECTIVE_NAME        = "name";
    public static final String KEY_OBJECTIVE_DESCRIPTION = "description";
    public static final String KEY_OBJECTIVE_DATE        = "date";

    // OBJECTIVE table - create statement
    public static final String CREATE_TABLE_OBJECTIVE = "CREATE TABLE " + TABLE_OBJECTIVE + "("
            + KEY_OBJECTIVE_ID +" INTEGER PRIMARY KEY, "
            + KEY_PROJECT_FK_ID + " INTEGER NOT NULL, "
            + KEY_OBJECTIVE_OWNER_ID + " INTEGER NOT NULL, "
            + KEY_OBJECTIVE_NAME + " TEXT, "
            + KEY_OBJECTIVE_DESCRIPTION+ " TEXT, "
            + KEY_OBJECTIVE_DATE + " DATE, "
            + "FOREIGN KEY (" + KEY_PROJECT_FK_ID + ") REFERENCES " + TABLE_PROJECT +"("+ KEY_PROJECT_ID +") ON DELETE CASCADE);";

    // OUTPUT table - column names
    public static final String KEY_OUTPUT_ID          = "_id";
    public static final String KEY_OBJECTIVE_FK_ID    = "_id_objective_fk";
    public static final String KEY_OUTPUT_OWNER_ID    = "_id_owner";
    public static final String KEY_OUTPUT_NAME        = "name";
    public static final String KEY_OUTPUT_DESCRIPTION = "description";
    public static final String KEY_OUTPUT_DATE        = "date";

    // OUTPUT table - create statement
    public static final String CREATE_TABLE_OUTPUT = "CREATE TABLE " + TABLE_OUTPUT + "("
            + KEY_OUTPUT_ID +" INTEGER PRIMARY KEY, "
            + KEY_OBJECTIVE_FK_ID + " INTEGER, "
            + KEY_OUTPUT_OWNER_ID + " INTEGER NOT NULL, "
            + KEY_OUTPUT_NAME + " TEXT, "
            + KEY_OUTPUT_DESCRIPTION + " TEXT, "
            + KEY_OUTPUT_DATE + " DATE, "
            + "FOREIGN KEY (" + KEY_OBJECTIVE_FK_ID + ") REFERENCES " + TABLE_OBJECTIVE +"("+ KEY_OBJECTIVE_ID +") ON DELETE CASCADE);";

    // ACTIVITY table - column names
    public static final String KEY_ACTIVITY_ID          = "_id";
    public static final String KEY_ACTIVITY_OWNER_ID    = "_id_owner";
    public static final String KEY_ACTIVITY_NAME        = "name";
    public static final String KEY_ACTIVITY_DESCRIPTION = "description";
    public static final String KEY_ACTIVITY_DATE        = "date";

    // ACTIVITY table - create statement
    public static final String CREATE_TABLE_ACTIVITY = "CREATE TABLE " + TABLE_ACTIVITY + "("
            + KEY_ACTIVITY_ID +" INTEGER PRIMARY KEY, "
            + KEY_ACTIVITY_OWNER_ID + " INTEGER NOT NULL, "
            + KEY_ACTIVITY_NAME + " TEXT, "
            + KEY_ACTIVITY_DESCRIPTION + " TEXT, "
            + KEY_ACTIVITY_DATE + " DATE " + ")";

    // INDICATOR table - column names
    public static final String KEY_INDICATOR_ID          = "_id";
    public static final String KEY_INDICATOR_OWNER_ID    = "_id_owner";
    //public static final String KEY_STATUS_FK_ID          = "_id_status_fk";
    public static final String KEY_INDICATOR_NAME        = "name";
    public static final String KEY_INDICATOR_DESCRIPTION = "description";
    public static final String KEY_INDICATOR_TYPE        = "type";
    public static final String KEY_INDICATOR_DATE        = "date";

    // INDICATOR table - create statement
    public static final String CREATE_TABLE_INDICATOR = "CREATE TABLE " + TABLE_INDICATOR + "("
            + KEY_INDICATOR_ID +" INTEGER PRIMARY KEY, "
            + KEY_INDICATOR_OWNER_ID + " INTEGER NOT NULL, "
            + KEY_STATUS_FK_ID + " INTEGER NOT NULL, "
            + KEY_INDICATOR_NAME + " TEXT, "
            + KEY_INDICATOR_DESCRIPTION + " TEXT, "
            + KEY_INDICATOR_TYPE + " INTEGER NOT NULL, "
            + KEY_INDICATOR_DATE + " DATE " + ")";

    public static final String KEY_INDICATOR_FK_ID            = "_id_indicator_fk";
    public static final String KEY_QUANTITATIVE_OWNER_ID      = "_id_owner";
    public static final String KEY_UNIT_FK_ID                 = "_id_unit_fk";
    public static final String KEY_QUANTITATIVE_BASELINE      = "baseline";
    public static final String KEY_QUANTITATIVE_BASELINE_DATE = "date";

    // QUNTITATIVE INDICATOR - create statement
    public static final String CREATE_TABLE_QUANTITATIVEINDICATOR = "CREATE TABLE " + TABLE_QUANTITATIVEINDICATOR + "("
            + KEY_INDICATOR_FK_ID +" INTEGER PRIMARY KEY, "
            + KEY_QUANTITATIVE_OWNER_ID + " INTEGER NOT NULL, "
            + KEY_UNIT_FK_ID +" INTEGER , "
            + KEY_QUANTITATIVE_BASELINE + " INTEGER, "
            + KEY_QUANTITATIVE_BASELINE_DATE + " DATE, "
            + "FOREIGN KEY (" + KEY_INDICATOR_FK_ID + ") REFERENCES " + TABLE_INDICATOR +"("+ KEY_INDICATOR_ID +") ON DELETE CASCADE, "
            + "FOREIGN KEY (" + KEY_UNIT_FK_ID + ") REFERENCES " + TABLE_UNIT +"("+ KEY_UNIT_ID +") ON DELETE CASCADE);";

    // QUALITATIVE INDICATOR - column names
    public static final String KEY_QUALITATIVE_OWNER_ID      = "_id_owner";
    public static final String KEY_QUALITATIVE_BASELINE      = "baseline";
    public static final String KEY_QUALITATIVE_BASELINE_DATE = "date";

    // QUALITATIVE INDICATOR - create statement
    public static final String CREATE_TABLE_QUALITATIVEINDICATOR = "CREATE TABLE " + TABLE_QUALITATIVEINDICATOR + "("
            + KEY_INDICATOR_FK_ID +" INTEGER PRIMARY KEY, "
            + KEY_QUALITATIVE_OWNER_ID + " INTEGER NOT NULL, "
            + KEY_QUALITATIVE_BASELINE + " TEXT, "
            + KEY_QUALITATIVE_BASELINE_DATE + " DATE, "
            + "FOREIGN KEY (" + KEY_INDICATOR_FK_ID + ") REFERENCES " + TABLE_INDICATOR +"("+ KEY_INDICATOR_ID +") ON DELETE CASCADE);";

    // HYBRID TARGET - column names
    public static final String KEY_HYBRID_OWNER_ID      = "_id_owner";
    public static final String KEY_CATEGORY_FK_ID       = "_id_category_fk";
    public static final String KEY_HYBRID_BASELINE      = "baseline";
    public static final String KEY_HYBRID_BASELINE_DATE = "date";

    // HYBRID INDICATOR - create statement
    public static final String CREATE_TABLE_HYBRIDINDICATOR = "CREATE TABLE " + TABLE_HYBRIDINDICATOR + "("
            + KEY_INDICATOR_FK_ID +" INTEGER PRIMARY KEY, "
            + KEY_HYBRID_OWNER_ID + " INTEGER NOT NULL, "
            + KEY_CATEGORY_FK_ID +" INTEGER , "
            + KEY_HYBRID_BASELINE + " INTEGER, "
            + KEY_HYBRID_BASELINE_DATE + " DATE, "
            + "FOREIGN KEY (" + KEY_INDICATOR_FK_ID + ") REFERENCES " + TABLE_INDICATOR +"("+ KEY_INDICATOR_ID +") ON DELETE CASCADE, "
            + "FOREIGN KEY (" + KEY_CATEGORY_FK_ID + ") REFERENCES " + TABLE_CATEGORY +"("+ KEY_CATEGORY_ID +") ON DELETE CASCADE);";

    // QUANTITATIVE TARGET - column names
    public static final String KEY_QUANTITATIVE_ID          = "_id";
    public static final String KEY_QUANTITATIVE_TARGET      = "target";
    public static final String KEY_QUANTITATIVE_TARGET_DATE = "date";

    // QUANTITATIVE TARGET - create statement
    public static final String CREATE_TABLE_QUANTITATIVETARGET  = "CREATE TABLE " + TABLE_QUANTITATIVETARGET + "("
            + KEY_QUANTITATIVE_ID +" INTEGER PRIMARY KEY, "
            + KEY_INDICATOR_FK_ID +" INTEGER, "
            + KEY_QUANTITATIVE_OWNER_ID + " INTEGER NOT NULL, "
            + KEY_QUANTITATIVE_TARGET + " INTEGER, "
            + KEY_QUANTITATIVE_TARGET_DATE + " DATE, "
            + "FOREIGN KEY (" + KEY_INDICATOR_FK_ID + ") REFERENCES " + TABLE_QUANTITATIVEINDICATOR +"("+ KEY_INDICATOR_FK_ID +") ON DELETE CASCADE);";

    // QUALITATIVE TARGET - column names
    public static final String KEY_QUALITATIVE_ID          = "_id";
    public static final String KEY_QUALITATIVE_TARGET      = "target";
    public static final String KEY_QUALITATIVE_TARGET_DATE = "date";

    // QUALITATIVE TARGET - create statement
    public static final String CREATE_TABLE_QUALITATIVETARGET = "CREATE TABLE " + TABLE_QUALITATIVETARGET + "("
            + KEY_QUALITATIVE_ID +" INTEGER PRIMARY KEY, "
            + KEY_INDICATOR_FK_ID +" INTEGER NOT NULL, "
            + KEY_QUALITATIVE_OWNER_ID + " INTEGER NOT NULL, "
            + KEY_QUALITATIVE_TARGET + " TEXT, "
            + KEY_QUALITATIVE_TARGET_DATE + " DATE, "
            + "FOREIGN KEY (" + KEY_INDICATOR_FK_ID + ") REFERENCES " + TABLE_QUALITATIVEINDICATOR +"("+ KEY_INDICATOR_FK_ID +") ON DELETE CASCADE);";

    // HYBRID TARGET - column names
    public static final String KEY_HYBRID_ID          = "_id";
    public static final String KEY_HYBRID_TARGET      = "target";
    public static final String KEY_HYBRID_TARGET_DATE = "date";

    // HYBRID INDICATOR - create statement
    public static final String CREATE_TABLE_HYBRIDTARGET = "CREATE TABLE " + TABLE_HYBRIDTARGET + "("
            + KEY_HYBRID_ID +" INTEGER PRIMARY KEY, "
            + KEY_INDICATOR_FK_ID +" INTEGER NOT NULL, "
            + KEY_HYBRID_OWNER_ID + " INTEGER NOT NULL, "
            + KEY_CATEGORY_FK_ID +" INTEGER , "
            + KEY_HYBRID_TARGET + " INTEGER, "
            + KEY_HYBRID_TARGET_DATE + " DATE, "
            + "FOREIGN KEY (" + KEY_INDICATOR_FK_ID + ") REFERENCES " + TABLE_HYBRIDINDICATOR +"("+ KEY_INDICATOR_FK_ID +") ON DELETE CASCADE, "
            + "FOREIGN KEY (" + KEY_CATEGORY_FK_ID + ") REFERENCES " + TABLE_CATEGORY +"("+ KEY_CATEGORY_ID +") ON DELETE CASCADE);";


    // MOV table - column names
    public static final String KEY_MOV_ID          = "_id";
    public static final String KEY_MOV_OWNER_ID    = "_id_owner";
    public static final String KEY_MOV_NAME        = "name";
    public static final String KEY_MOV_DESCRIPTION = "description";
    public static final String KEY_MOV_DATE        = "date";

    // MOV table - create statement
    public static final String CREATE_TABLE_MOV = "CREATE TABLE " + TABLE_MOV + "("
            + KEY_MOV_ID +" INTEGER PRIMARY KEY, "
            + KEY_MOV_OWNER_ID + " INTEGER NOT NULL, "
            + KEY_MOV_NAME + " TEXT, "
            + KEY_MOV_DESCRIPTION + " TEXT, "
            + KEY_MOV_DATE + " DATE " + ")";

    // RISKREGISTER table - column names
    public static final String KEY_RISKREGISTER_ID          = "_id";
    public static final String KEY_RISKREGISTER_FK_ID       = "_id_riskregister_fk";
    public static final String KEY_RISKREGISTER_OWNER_ID    = "_id_owner";
    public static final String KEY_RISKREGISTER_NAME        = "name";
    public static final String KEY_RISKREGISTER_DESCRIPTION = "description";
    public static final String KEY_RISKREGISTER_DATE        = "date";

    // RISKREGISTER table - create statement
    public static final String CREATE_TABLE_RISKREGISTER = "CREATE TABLE " + TABLE_RISKREGISTER + "("
            + KEY_RISKREGISTER_ID + " INTEGER PRIMARY KEY, "
            + KEY_PROJECT_FK_ID + " INTEGER NOT NULL, "
            + KEY_RISKREGISTER_OWNER_ID + " INTEGER NOT NULL,"
            + KEY_RISKREGISTER_NAME + " TEXT, "
            + KEY_RISKREGISTER_DESCRIPTION + " TEXT, "
            + KEY_RISKREGISTER_DATE + " DATE, "
            + "FOREIGN KEY (" + KEY_PROJECT_FK_ID + ") REFERENCES " + TABLE_PROJECT + "("+ KEY_PROJECT_ID + ") ON DELETE CASCADE );";

    // RISKMAP table - column names
    public static final String KEY_RISKMAP_ID          = "_id";
    public static final String KEY_RISKMAP_OWNER_ID    = "_id_owner";
    public static final String KEY_RISKMAP_NAME        = "name";
    public static final String KEY_RISKMAP_DESCRIPTION = "description";
    public static final String KEY_RISKMAP_LOWER       = "lower_limit";
    public static final String KEY_RISKMAP_UPPER       = "upper_limit";
    public static final String KEY_RISKMAP_DATE        = "date";

    // RISKMAP table - create statement
    public static final String CREATE_TABLE_RISKMAP = "CREATE TABLE " + TABLE_RISKMAP + "("
            + KEY_RISKMAP_ID + " INTEGER PRIMARY KEY, "
            + KEY_RISKREGISTER_FK_ID + " INTEGER NOT NULL, "
            + KEY_RISKMAP_OWNER_ID + " INTEGER NOT NULL, "
            + KEY_RISKMAP_NAME + " TEXT, "
            + KEY_RISKMAP_DESCRIPTION + " TEXT, "
            + KEY_RISKMAP_LOWER + " INTEGER, "
            + KEY_RISKMAP_UPPER + " INTEGER, "
            + KEY_RISKMAP_DATE + " DATE, "
            + "FOREIGN KEY (" + KEY_RISKREGISTER_FK_ID + ") REFERENCES " + TABLE_RISKREGISTER + "("+ KEY_RISKREGISTER_ID + ") ON DELETE CASCADE );";

    // RISKLIKELIHOOD table - column names
    public static final String KEY_RISKLIKELIHOOD_ID = "_id";
    public static final String KEY_RISKLIKELIHOOD_OWNER_ID    = "_id_owner";
    public static final String KEY_RISKLIKELIHOOD_NAME        = "name";
    public static final String KEY_RISKLIKELIHOOD_DESCRIPTION = "description";
    public static final String KEY_RISKLIKELIHOOD_VALUE       = "value";
    public static final String KEY_RISKLIKELIHOOD_DATE        = "date";

    // RISKLIKELIHOOD table - create statement
    public static final String CREATE_TABLE_RISKLIKELIHOOD = "CREATE TABLE " + TABLE_RISKLIKELIHOOD + "("
            + KEY_RISKLIKELIHOOD_ID + " INTEGER PRIMARY KEY, "
            + KEY_RISKLIKELIHOOD_OWNER_ID + " INTEGER NOT NULL, "
            + KEY_RISKLIKELIHOOD_NAME + " TEXT, "
            + KEY_RISKLIKELIHOOD_DESCRIPTION + " TEXT, "
            + KEY_RISKLIKELIHOOD_VALUE + " INTEGER, "
            + KEY_RISKLIKELIHOOD_DATE + " DATE" + ")";


    // RISKLIKELIHOOD table - column names
    public static final String KEY_RISKIMPACT_ID = "_id";
    public static final String KEY_RISKIMPACT_OWNER_ID    = "_id_owner";
    public static final String KEY_RISKIMPACT_NAME        = "name";
    public static final String KEY_RISKIMPACT_DESCRIPTION = "description";
    public static final String KEY_RISKIMPACT_VALUE       = "value";
    public static final String KEY_RISKIMPACT_DATE        = "date";

    // RISKLIKELIHOOD table - create statement
    public static final String CREATE_TABLE_RISKIMPACT = "CREATE TABLE " + TABLE_RISKIMPACT + "("
            + KEY_RISKIMPACT_ID + " INTEGER PRIMARY KEY, "
            + KEY_RISKIMPACT_OWNER_ID + " INTEGER NOT NULL, "
            + KEY_RISKIMPACT_NAME + " TEXT, "
            + KEY_RISKIMPACT_DESCRIPTION + " TEXT, "
            + KEY_RISKIMPACT_VALUE + " INTEGER, "
            + KEY_RISKIMPACT_DATE + " DATE" + ")";

    // RISK table - column names
    public static final String KEY_RISK_ID            = "_id";
    public static final String KEY_RISK_OWNER_ID      = "_id_owner";
    public static final String KEY_RISK_OWNERSHIP_ID  = "_id_ownership";
    public static final String KEY_RISK_NAME          = "name";
    public static final String KEY_RISK_DESCRIPTION   = "description";
    public static final String KEY_RISK_STATUS        = "status";
    public static final String KEY_RISK_LIKELIHOOD    = "likelihood";
    public static final String KEY_RISK_IMPACT        = "impact";
    public static final String KEY_RISK_DATE          = "date";

    // RISK table - create statement
    public static final String CREATE_TABLE_RISK = "CREATE TABLE " + TABLE_RISK + "("
            + KEY_RISK_ID +" INTEGER PRIMARY KEY, "
            + KEY_RISKREGISTER_FK_ID + " INTEGER NOT NULL, "
            + KEY_RISK_OWNER_ID + " INTEGER NOT NULL, "
            + KEY_RISK_OWNERSHIP_ID + " INTEGER NOT NULL, "
            + KEY_RISK_NAME + " TEXT, "
            + KEY_RISK_DESCRIPTION + " TEXT, "
            + KEY_RISK_STATUS + " INTEGER NOT NULL, "
            + KEY_RISK_LIKELIHOOD + " INTEGER, "
            + KEY_RISK_IMPACT + " INTEGER, "
            + KEY_RISK_DATE + " DATE, "
            + "FOREIGN KEY (" + KEY_RISKREGISTER_FK_ID + ") REFERENCES " + TABLE_RISKREGISTER + "("+ KEY_RISKREGISTER_ID + ") ON DELETE CASCADE );";


    // RISKCONSEQUENCE table - column names
    public static final String KEY_RISKCONSEQUENCE_ID          = "_id";
    public static final String KEY_RISK_FK_ID                  = "_id_risk_fk";
    public static final String KEY_RISKCONSEQUENCE_OWNER_ID    = "_id_owner";
    public static final String KEY_RISKCONSEQUENCE_NAME        = "name";
    public static final String KEY_RISKCONSEQUENCE_DESCRIPTION = "description";
    public static final String KEY_RISKCONSEQUENCE_DATE        = "date";

    // RISKCONSEQUENCE table - create statement
    public static final String CREATE_TABLE_RISKCONSEQUENCE = "CREATE TABLE " + TABLE_RISKCONSEQUENCE + "("
            + KEY_RISKCONSEQUENCE_ID +" INTEGER PRIMARY KEY, "
            + KEY_RISK_FK_ID + " INTEGER NOT NULL, "
            + KEY_RISKCONSEQUENCE_OWNER_ID + " INTEGER NOT NULL, "
            + KEY_RISKCONSEQUENCE_NAME + " TEXT, "
            + KEY_RISKCONSEQUENCE_DESCRIPTION + " TEXT, "
            + KEY_RISKCONSEQUENCE_DATE + " DATE, "
            + "FOREIGN KEY (" + KEY_RISK_FK_ID + ") REFERENCES " + TABLE_RISK +"("+ KEY_RISK_ID +") ON DELETE CASCADE);";

    // RISKCONSEQUENCE table - column names
    public static final String KEY_RISKMITIGATION_ID          = "_id";
    public static final String KEY_RISKMITIGATION_OWNER_ID    = "_id_owner";
    public static final String KEY_RISKMITIGATION_NAME        = "name";
    public static final String KEY_RISKMITIGATION_DESCRIPTION = "description";
    public static final String KEY_RISKMITIGATION_DATE        = "date";

    // RISKCONSEQUENCE table - create statement
    public static final String CREATE_TABLE_RISKMITIGATION = "CREATE TABLE " + TABLE_RISKMITIGATION + "("
            + KEY_RISKMITIGATION_ID +" INTEGER PRIMARY KEY, "
            + KEY_RISK_FK_ID + " INTEGER NOT NULL, "
            + KEY_RISKMITIGATION_OWNER_ID + " INTEGER NOT NULL, "
            + KEY_RISKMITIGATION_NAME + " TEXT, "
            + KEY_RISKMITIGATION_DESCRIPTION + " TEXT, "
            + KEY_RISKMITIGATION_DATE + " DATE, "
            + "FOREIGN KEY (" + KEY_RISK_FK_ID + ") REFERENCES " + TABLE_RISK +"("+ KEY_RISK_ID +") ON DELETE CASCADE);";

    // RISKMANAGEMENT table - column names
    public static final String KEY_DATE_FK_ID                = "_id_date_fk";
    public static final String KEY_RISKMANAGEMENT_OWNER_ID   = "_id_owner";
    public static final String KEY_RISKMANAGEMENT_LIKELIHOOD = "likelihood";
    public static final String KEY_RISKMANAGEMENT_IMPACT     = "impact";
    public static final String KEY_RISKMANAGEMENT_COMMENT    = "comment";
    public static final String KEY_RISKMANAGEMENT_DATE       = "date";

    // RISKMANAGEMENT table - create statement
    public static final String CREATE_TABLE_RISKMANAGEMENT = "CREATE TABLE " + TABLE_RISKMANAGEMENT + "("
            + KEY_RISK_FK_ID +" INTEGER NOT NULL, "
            + KEY_DATE_FK_ID + " INTEGER NOT NULL, "
            + KEY_RISKMANAGEMENT_OWNER_ID + " INTEGER NOT NULL, "
            + KEY_RISKMANAGEMENT_LIKELIHOOD + " INTEGER, "
            + KEY_RISKMANAGEMENT_IMPACT + " INTEGER, "
            + KEY_RISKMANAGEMENT_COMMENT + " TEXT, "
            + KEY_RISKMANAGEMENT_DATE + " DATE, "
            + "FOREIGN KEY (" + KEY_RISK_FK_ID + ") REFERENCES " + TABLE_RISK +"("+ KEY_RISK_ID +") ON DELETE CASCADE, "
            + "FOREIGN KEY (" + KEY_DATE_FK_ID + ") REFERENCES " + TABLE_DATE +"("+ KEY_DATE_ID +") ON DELETE CASCADE, "
            + "PRIMARY KEY (" + KEY_RISK_FK_ID +", "+ KEY_DATE_FK_ID +"));";

    // WORKPLAN table - column names
    private static final String KEY_WORKPLAN_ID        = "_id";
    private static final String KEY_WORKPLAN_OWNER_ID  = "_id_owner";
    private static final String KEY_WORKPLAN_START_DATE = "start_date";
    private static final String KEY_WORKPLAN_END_DATE   = "end_date";

    // WORKPLAN table - create statement
    public static final String CREATE_TABLE_WORKPLAN = "CREATE TABLE " + TABLE_WORKPLAN + "("
            + KEY_WORKPLAN_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_WORKPLAN_OWNER_ID + " INTEGER NOT NULL, "
            + KEY_WORKPLAN_START_DATE + " DATE, "
            + KEY_WORKPLAN_END_DATE + " DATE " + ")";

    // RESOURCE table - column names
    private static final String KEY_RESOURCE_ID       = "_id";
    private static final String KEY_RESOURCE_OWNER_ID = "_id_owner";
    private static final String KEY_RESOURCE_NAME     = "name";
    private static final String KEY_RESOURCE_DATE     = "date";

    // RESOURCE table - create statement
    public static final String CREATE_TABLE_RESOURCE = "CREATE TABLE " + TABLE_RESOURCE + "("
            + KEY_RESOURCE_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_RESOURCE_OWNER_ID + " INTEGER NOT NULL, "
            + KEY_RESOURCE_NAME + " TEXT, "
            + KEY_RESOURCE_DATE + " DATE " + ")";

    // ACCOUNTTYPE table - column names
    private static final String KEY_ACCOUNTTYPE_ID       = "_id";
    private static final String KEY_ACCOUNTTYPE_OWNER_ID = "_id_owner";
    private static final String KEY_ACCOUNTTYPE_NAME     = "name";
    private static final String KEY_ACCOUNTTYPE_DATE     = "date";

    // ACCOUNTTYPE table - create statement
    public static final String CREATE_TABLE_ACCOUNTTYPE = "CREATE TABLE " + TABLE_ACCOUNTTYPE + "("
            + KEY_ACCOUNTTYPE_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_ACCOUNTTYPE_OWNER_ID + " INTEGER NOT NULL, "
            + KEY_ACCOUNTTYPE_NAME + " TEXT, "
            + KEY_ACCOUNTTYPE_DATE + " DATE " + ")";

    // BUDGET table - column names
    private static final String KEY_ACTIVITY_RESOURCE_ID      = "_id_resource";
    private static final String KEY_ACTIVITY_RESOURCE_TYPE_ID = "_id_resource_type";
    private static final String KEY_ACCOUNT_TYPE_ID           = "_id_type";
    private static final String KEY_BUDGET_OWNER_ID           = "_id_owner";
    private static final String KEY_BUDGET_NAME               = "name";
    private static final String KEY_BUDGET_DATE               = "date";

    // BUDGET table - create statement
    public static final String CREATE_TABLE_BUDGET = "CREATE TABLE " + TABLE_BUDGET + "("
            + KEY_ACTIVITY_RESOURCE_ID +" INTEGER NOT NULL, "
            + KEY_ACTIVITY_RESOURCE_TYPE_ID +" INTEGER NOT NULL, "
            + KEY_ACCOUNT_TYPE_ID +" INTEGER NOT NULL, "
            + KEY_BUDGET_OWNER_ID + " INTEGER NOT NULL, "
            + KEY_BUDGET_NAME + " TEXT, "
            + KEY_BUDGET_DATE + " DATE, "
            + "PRIMARY KEY (" + KEY_ACTIVITY_RESOURCE_ID + " ," + KEY_ACTIVITY_RESOURCE_TYPE_ID + " ," + KEY_ACCOUNT_TYPE_ID +" ));";

    // CASHFLOW table - column names
    private static final String KEY_CASHFLOW_OWNER_ID           = "_id_owner";
    private static final String KEY_CASHFLOW_NAME               = "name";
    private static final String KEY_CASHFLOW_DATE               = "date";

    // CASHFLOW table - create statement
    public static final String CREATE_TABLE_CASHFLOW = "CREATE TABLE " + TABLE_CASHFLOW + "("
            + KEY_ACTIVITY_RESOURCE_ID +" INTEGER NOT NULL, "
            + KEY_ACTIVITY_RESOURCE_TYPE_ID +" INTEGER NOT NULL, "
            + KEY_ACCOUNT_TYPE_ID +" INTEGER NOT NULL, "
            + KEY_CASHFLOW_OWNER_ID + " INTEGER NOT NULL, "
            + KEY_CASHFLOW_NAME + " TEXT, "
            + KEY_CASHFLOW_DATE + " DATE, "
            + "PRIMARY KEY (" + KEY_ACTIVITY_RESOURCE_ID + " ," + KEY_ACTIVITY_RESOURCE_TYPE_ID + " ," + KEY_ACCOUNT_TYPE_ID +" ));";

    // MONITORING Table - column names
    private static final String KEY_MONITORING_ID       = "_id";
    private static final String KEY_MONITORING_OWNER_ID = "_id_owner";
    private static final String KEY_MONITORING_NAME     = "name";
    private static final String KEY_MONITORING_DATE     = "date";

    // MONITORING table - create statement
    public static final String CREATE_TABLE_MONITORING = "CREATE TABLE " + TABLE_MONITORING + "("
            + KEY_MONITORING_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_MONITORING_OWNER_ID + " INTEGER NOT NULL, "
            + KEY_MONITORING_NAME + " TEXT, "
            + KEY_MONITORING_DATE + " DATE " + ")";


    // EVALUATION table - column names
    private static final String KEY_EVALUATION_ID       = "_id";
    private static final String KEY_EVALUATION_OWNER_ID = "_id_owner";
    private static final String KEY_EVALUATION_NAME     = "name";
    private static final String KEY_EVALUATION_DATE     = "date";

    // OUTPUT table - create statement
    public static final String CREATE_TABLE_EVALUATION = "CREATE TABLE " + TABLE_EVALUATION + "("
            + KEY_EVALUATION_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_PROJECT_FK_ID + " INTEGER NOT NULL, "
            + KEY_EVALUATION_OWNER_ID + " INTEGER NOT NULL, "
            + KEY_EVALUATION_NAME + " TEXT, "
            + KEY_EVALUATION_DATE + " DATE, "
            + "FOREIGN KEY (" + KEY_PROJECT_FK_ID + ") REFERENCES " + TABLE_PROJECT +"("+ KEY_PROJECT_ID +"));";

    // ROLE Table - column names
    private static final String KEY_CRITERION_ID          = "_id";
    private static final String KEY_CRITERION_OWNER_ID    = "_id_owner";
    private static final String KEY_CRITERION_NAME        = "name";
    private static final String KEY_CRITERION_DESCRIPTION = "description";

    // ROLE table - create statement
    public static final String CREATE_TABLE_CRITERION = "CREATE TABLE " + TABLE_CRITERION + "("
            + KEY_CRITERION_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_CRITERION_OWNER_ID + " INTEGER NOT NULL, "
            + KEY_CRITERION_NAME + " TEXT, "
            + KEY_CRITERION_DESCRIPTION + " TEXT " + ")";

    // QUESTION table - column names
    private static final String KEY_QUESTION_ID       = "_id";
    private static final String KEY_QUESTION_OWNER_ID = "_id_owner";
    private static final String KEY_QUESTION_NAME     = "name";
    private static final String KEY_QUESTION_DATE     = "date";

    // QUESTION table - create statement
    public static final String CREATE_TABLE_QUESTION = "CREATE TABLE " + TABLE_QUESTION + "("
            + KEY_QUESTION_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_QUESTION_OWNER_ID + " INTEGER NOT NULL, "
            + KEY_QUESTION_NAME + " TEXT, "
            + KEY_QUESTION_DATE + " DATE " + ")";


     //=======================================================================================

    // OVERALLAIM_PROJECT table - column names
    private static final String KEY_OVERALLAIM_PROJECT_OWNER_ID    = "_id_owner";

    // OVERALLAIM_PROJECT table - create statement
    public static final String CREATE_TABLE_OVERALLAIM_PROJECT = "CREATE TABLE " + TABLE_OVERALLAIM_PROJECT + "("
            + KEY_OVERALLAIM_FK_ID + " INTEGER NOT NULL, "
            + KEY_PROJECT_FK_ID + " INTEGER NOT NULL, "
            + KEY_OVERALLAIM_PROJECT_OWNER_ID + " INTEGER NOT NULL,"
            + "FOREIGN KEY (" + KEY_OVERALLAIM_FK_ID + ") REFERENCES " + TABLE_OVERALLAIM + "("+ KEY_OVERALLAIM_ID + "),"
            + "FOREIGN KEY (" + KEY_PROJECT_FK_ID + ") REFERENCES " + TABLE_PROJECT + "("+ KEY_PROJECT_ID + "),"
            + "PRIMARY KEY (" + KEY_OVERALLAIM_FK_ID +", "+ KEY_PROJECT_FK_ID +"));";

    // PROJECT_STATUS table - column names
    private static final String KEY_PROJECT_STATUS_OWNER_ID = "_id_owner";

    // PROJECT_STATUS table - create statement
    public static final String CREATE_TABLE_PROJECT_STATUS = "CREATE TABLE " + TABLE_PROJECT_STATUS + "("
            + KEY_PROJECT_FK_ID + " INTEGER NOT NULL, "
            + KEY_STATUS_FK_ID + " INTEGER NOT NULL, "
            + KEY_PROJECT_STATUS_OWNER_ID + " INTEGER NOT NULL, "
            + "FOREIGN KEY (" + KEY_PROJECT_FK_ID + ") REFERENCES " + TABLE_PROJECT + "("+ KEY_PROJECT_ID + "),"
            + "FOREIGN KEY (" + KEY_STATUS_FK_ID + ") REFERENCES " + TABLE_tblSTATUS + "("+ KEY_STATUS_ID + "),"
            + "PRIMARY KEY (" + KEY_PROJECT_FK_ID +", "+ KEY_STATUS_FK_ID +"));";

    // PROJECT_OUTCOME table - column names
    //public static final String KEY_OUTCOME_FK_ID = "_id_outcome_fk";
    public static final String KEY_PROJECT_OUTCOME_OWNER_ID = "_id_owner";
    public static final String KEY_PROJECT_OUTCOME_DATE = "date";

    // PROJECT_OUTCOME table - create statement
    public static final String CREATE_TABLE_PROJECT_OUTCOME = "CREATE TABLE " + TABLE_PROJECT_OUTCOME + "("
            + KEY_PROJECT_FK_ID + " INTEGER NOT NULL, "
            + KEY_OUTCOME_FK_ID + " INTEGER NOT NULL, "
            + KEY_PROJECT_OUTCOME_OWNER_ID + " INTEGER NOT NULL, "
            + KEY_PROJECT_OUTCOME_DATE + " DATE, "
            + "FOREIGN KEY (" + KEY_PROJECT_FK_ID + ") REFERENCES " + TABLE_PROJECT + "("+ KEY_PROJECT_ID + ") ON DELETE CASCADE,"
            + "FOREIGN KEY (" + KEY_OUTCOME_FK_ID + ") REFERENCES " + TABLE_OUTCOME + "("+ KEY_OUTCOME_ID + ") ON DELETE CASCADE,"
            + "PRIMARY KEY (" + KEY_PROJECT_FK_ID +", "+ KEY_OUTCOME_FK_ID +"));";

    // OUTCOME_OUTPUT table - column names
    //public static final String KEY_OUTPUT_FK_ID = "_id_output_fk";
    public static final String KEY_OUTCOME_OUTPUT_OWNER_ID = "_id_owner";
    public static final String KEY_OUTCOME_OUTPUT_DATE = "date";

    // OUTCOME_OUTPUT table - create statement
    public static final String CREATE_TABLE_OUTCOME_OUTPUT = "CREATE TABLE " + TABLE_OUTCOME_OUTPUT + "("
            + KEY_OUTCOME_FK_ID + " INTEGER NOT NULL, "
            + KEY_OUTPUT_FK_ID + " INTEGER NOT NULL, "
            + KEY_OUTCOME_OUTPUT_OWNER_ID + " INTEGER NOT NULL, "
            + KEY_OUTCOME_OUTPUT_DATE + " DATE, "
            + "FOREIGN KEY (" + KEY_OUTCOME_FK_ID + ") REFERENCES " + TABLE_OUTCOME + "("+ KEY_OUTCOME_ID + ") ON DELETE CASCADE,"
            + "FOREIGN KEY (" + KEY_OUTPUT_FK_ID + ") REFERENCES " + TABLE_OUTPUT + "("+ KEY_OUTPUT_ID + ") ON DELETE CASCADE,"
            + "PRIMARY KEY (" + KEY_OUTCOME_FK_ID +", "+ KEY_OUTPUT_FK_ID +"));";

    // OUTPUT_ACTIVITY table - column names
    //public static final String KEY_ACTIVITY_FK_ID = "_id_activity_fk";
    public static final String KEY_OUTPUT_ACTIVITY_OWNER_ID = "_id_owner";
    public static final String KEY_OUTPUT_ACTIVITY_DATE = "date";

    // OUTPUT_ACTIVITY table - create statement
    public static final String CREATE_TABLE_OUTPUT_ACTIVITY = "CREATE TABLE " + TABLE_OUTPUT_ACTIVITY + "("
            + KEY_OUTPUT_FK_ID + " INTEGER NOT NULL, "
            + KEY_ACTIVITY_FK_ID + " INTEGER NOT NULL, "
            + KEY_OUTPUT_ACTIVITY_OWNER_ID + " INTEGER NOT NULL, "
            + KEY_OUTPUT_ACTIVITY_DATE + " DATE, "
            + "FOREIGN KEY (" + KEY_OUTPUT_FK_ID + ") REFERENCES " + TABLE_OUTPUT + "("+ KEY_OUTPUT_ID + ") ON DELETE CASCADE,"
            + "FOREIGN KEY (" + KEY_ACTIVITY_FK_ID + ") REFERENCES " + TABLE_ACTIVITY + "("+ KEY_ACTIVITY_ID + ") ON DELETE CASCADE,"
            + "PRIMARY KEY (" + KEY_OUTPUT_FK_ID +", "+ KEY_ACTIVITY_FK_ID +"));";


    // OVERALLAIM_INDICATOR table - column names
    public static final String KEY_OVERALLAIM_INDICATOR_OWNER_ID = "_id_owner";
    public static final String KEY_OVERALLAIM_INDICATOR_DATE     = "date";

    // OVERALLAIM_INDICATOR table - create statement
    public static final String CREATE_TABLE_OVERALLAIM_INDICATOR = "CREATE TABLE " + TABLE_OVERALLAIM_INDICATOR + "("
            + KEY_OVERALLAIM_FK_ID + " INTEGER NOT NULL, "
            + KEY_INDICATOR_FK_ID + " INTEGER NOT NULL, "
            + KEY_OVERALLAIM_INDICATOR_OWNER_ID + " INTEGER NOT NULL,"
            + KEY_OVERALLAIM_INDICATOR_DATE + " DATE, "
            + "FOREIGN KEY (" + KEY_OVERALLAIM_FK_ID + ") REFERENCES " + TABLE_OVERALLAIM + "("+ KEY_OVERALLAIM_ID + "),"
            + "FOREIGN KEY (" + KEY_INDICATOR_FK_ID + ") REFERENCES " + TABLE_INDICATOR + "("+ KEY_INDICATOR_ID + "),"
            + "PRIMARY KEY (" + KEY_OVERALLAIM_FK_ID +", "+ KEY_INDICATOR_FK_ID +"))";

    // OUTCOME_INDICATOR table - column names
    public static final String KEY_OUTCOME_INDICATOR_OWNER_ID = "_id_owner";
    public static final String KEY_OUTCOME_INDICATOR_DATE     = "date";

    // OUTCOME_INDICATOR table - create statement
    public static final String CREATE_TABLE_OUTCOME_INDICATOR = "CREATE TABLE " + TABLE_OUTCOME_INDICATOR + "("
            + KEY_OUTCOME_FK_ID + " INTEGER NOT NULL, "
            + KEY_INDICATOR_FK_ID + " INTEGER NOT NULL, "
            + KEY_OUTCOME_INDICATOR_OWNER_ID + " INTEGER NOT NULL,"
            + KEY_OUTCOME_INDICATOR_DATE + " DATE, "
            + "FOREIGN KEY (" + KEY_OUTCOME_FK_ID + ") REFERENCES " + TABLE_OUTCOME + "("+ KEY_OUTCOME_ID + "),"
            + "FOREIGN KEY (" + KEY_INDICATOR_FK_ID + ") REFERENCES " + TABLE_INDICATOR + "("+ KEY_INDICATOR_ID + "),"
            + "PRIMARY KEY (" + KEY_OUTCOME_FK_ID +", "+ KEY_INDICATOR_FK_ID +"));";

    // OUTPUT_INDICATOR table - column names
    public static final String KEY_OUTPUT_INDICATOR_OWNER_ID = "_id_owner";
    public static final String KEY_OUTPUT_INDICATOR_DATE     = "date";

    // OUTPUT_INDICATOR table - create statement
    public static final String CREATE_TABLE_OUTPUT_INDICATOR = "CREATE TABLE " + TABLE_OUTPUT_INDICATOR + "("
            + KEY_OUTPUT_FK_ID + " INTEGER NOT NULL, "
            + KEY_INDICATOR_FK_ID + " INTEGER NOT NULL, "
            + KEY_OUTPUT_INDICATOR_OWNER_ID + " INTEGER NOT NULL,"
            + KEY_OUTPUT_INDICATOR_DATE + " DATE, "
            + "FOREIGN KEY (" + KEY_OUTPUT_FK_ID + ") REFERENCES " + TABLE_OUTPUT + "("+ KEY_OUTPUT_ID + "),"
            + "FOREIGN KEY (" + KEY_INDICATOR_FK_ID + ") REFERENCES " + TABLE_INDICATOR + "("+ KEY_INDICATOR_ID + "),"
            + "PRIMARY KEY (" + KEY_OUTPUT_FK_ID +", "+ KEY_INDICATOR_FK_ID +"));";

    // MOV_INDICATOR table - column names
    public static final String KEY_MOV_FK_ID              = "_id_mov_fk";
    public static final String KEY_MOV_INDICATOR_OWNER_ID = "_id_owner";
    public static final String KEY_MOV_INDICATOR_DATE     = "date";

    // MOV_INDICATOR table - create statement
    public static final String CREATE_TABLE_MOV_INDICATOR = "CREATE TABLE " + TABLE_MOV_INDICATOR + "("
            + KEY_MOV_FK_ID + " INTEGER NOT NULL, "
            + KEY_INDICATOR_FK_ID + " INTEGER NOT NULL, "
            + KEY_MOV_INDICATOR_OWNER_ID + " INTEGER NOT NULL,"
            + KEY_MOV_INDICATOR_DATE + " DATE, "
            + "FOREIGN KEY (" + KEY_MOV_FK_ID + ") REFERENCES " + TABLE_MOV + "("+ KEY_MOV_ID + "),"
            + "FOREIGN KEY (" + KEY_INDICATOR_FK_ID + ") REFERENCES " + TABLE_INDICATOR + "("+ KEY_INDICATOR_ID + "),"
            + "PRIMARY KEY (" + KEY_MOV_FK_ID +", "+ KEY_INDICATOR_FK_ID +"));";

    // OVERALLAIM_RISK table - column names
    public static final String KEY_OVERALLAIM_RISK_OWNER_ID = "_id_owner";
    public static final String KEY_OVERALLAIM_RISK_DATE = "date";

    // OVERALLAIM_RISK table - create statement
    public static final String CREATE_TABLE_OVERALLAIM_RISK = "CREATE TABLE " + TABLE_OVERALLAIM_RISK + "("
            + KEY_OVERALLAIM_FK_ID + " INTEGER NOT NULL, "
            + KEY_RISK_FK_ID + " INTEGER NOT NULL, "
            + KEY_OVERALLAIM_RISK_OWNER_ID + " INTEGER NOT NULL,"
            + KEY_OVERALLAIM_RISK_DATE + " DATE, "
            + "FOREIGN KEY (" + KEY_OVERALLAIM_FK_ID + ") REFERENCES " + TABLE_OVERALLAIM + "("+ KEY_OVERALLAIM_ID + ") ON DELETE CASCADE,"
            + "FOREIGN KEY (" + KEY_RISK_FK_ID + ") REFERENCES " + TABLE_RISK + "("+ KEY_RISK_ID + ") ON DELETE CASCADE,"
            + "PRIMARY KEY (" + KEY_OVERALLAIM_FK_ID +", "+ KEY_RISK_FK_ID +"));";

    // OUTCOME_RISK table - column names
    public static final String KEY_OUTCOME_RISK_OWNER_ID = "_id_owner";
    public static final String KEY_OUTCOME_RISK_DATE = "date";

    // OUTCOME_RISK table - create statement
    public static final String CREATE_TABLE_OUTCOME_RISK = "CREATE TABLE " + TABLE_OUTCOME_RISK + "("
            + KEY_OUTCOME_FK_ID + " INTEGER NOT NULL, "
            + KEY_RISK_FK_ID + " INTEGER NOT NULL, "
            + KEY_OUTCOME_RISK_OWNER_ID + " INTEGER NOT NULL, "
            + KEY_OUTCOME_RISK_DATE + " DATE, "
            + "FOREIGN KEY (" + KEY_OUTCOME_FK_ID + ") REFERENCES " + TABLE_OUTCOME + "("+ KEY_OUTCOME_ID + ") ON DELETE CASCADE,"
            + "FOREIGN KEY (" + KEY_RISK_FK_ID + ") REFERENCES " + TABLE_RISK + "("+ KEY_RISK_ID + ") ON DELETE CASCADE,"
            + "PRIMARY KEY (" + KEY_OUTCOME_FK_ID +", "+ KEY_RISK_FK_ID +"));";

    // OUTPUT_RISK table - column names
    public static final String KEY_OUTPUT_RISK_OWNER_ID = "_id_owner";
    public static final String KEY_OUTPUT_RISK_DATE = "date";

    // OUTPUT_RISK table - create statement
    public static final String CREATE_TABLE_OUTPUT_RISK = "CREATE TABLE " + TABLE_OUTPUT_RISK + "("
            + KEY_OUTPUT_FK_ID + " INTEGER NOT NULL, "
            + KEY_RISK_FK_ID + " INTEGER NOT NULL, "
            + KEY_OUTPUT_RISK_OWNER_ID + " INTEGER NOT NULL, "
            + KEY_OUTPUT_RISK_DATE + " DATE, "
            + "FOREIGN KEY (" + KEY_OUTPUT_FK_ID + ") REFERENCES " + TABLE_OUTPUT + "("+ KEY_OUTPUT_ID + ") ON DELETE CASCADE,"
            + "FOREIGN KEY (" + KEY_RISK_FK_ID + ") REFERENCES " + TABLE_RISK + "("+ KEY_RISK_ID + ") ON DELETE CASCADE,"
            + "PRIMARY KEY (" + KEY_OUTPUT_FK_ID +", "+ KEY_RISK_FK_ID +"));";

    // ACTIVITY_RISK table - column names
    public static final String KEY_ACTIVITY_RISK_OWNER_ID = "_id_owner";
    public static final String KEY_ACTIVITY_RISK_DATE = "date";

    // ACTIVITY_RISK table - create statement
    public static final String CREATE_TABLE_ACTIVITY_RISK = "CREATE TABLE " + TABLE_ACTIVITY_RISK + "("
            + KEY_ACTIVITY_FK_ID + " INTEGER NOT NULL, "
            + KEY_RISK_FK_ID + " INTEGER NOT NULL, "
            + KEY_ACTIVITY_RISK_OWNER_ID + " INTEGER NOT NULL,"
            + KEY_ACTIVITY_RISK_DATE + " DATE, "
            + "FOREIGN KEY (" + KEY_ACTIVITY_FK_ID + ") REFERENCES " + TABLE_ACTIVITY + "("+ KEY_ACTIVITY_ID + ") ON DELETE CASCADE,"
            + "FOREIGN KEY (" + KEY_RISK_FK_ID + ") REFERENCES " + TABLE_RISK + "("+ KEY_RISK_ID + ") ON DELETE CASCADE,"
            + "PRIMARY KEY (" + KEY_ACTIVITY_FK_ID +", "+ KEY_RISK_FK_ID +"));";


    // OVERALLAIM_CRITERION table - column names
    private static final String KEY_CRITERION_FK_ID = "_id_criterion_fk";
    private static final String KEY_OVERALLAIM_CRITERION_OWNER_ID = "_id_owner";

    // OVERALLAIM_CRITERION table - create statement
    public static final String CREATE_TABLE_OVERALLAIM_CRITERION = "CREATE TABLE " + TABLE_OVERALLAIM_CRITERION + "("
            + KEY_OVERALLAIM_FK_ID + " INTEGER NOT NULL, "
            + KEY_CRITERION_FK_ID + " INTEGER NOT NULL, "
            + KEY_OVERALLAIM_CRITERION_OWNER_ID + " INTEGER NOT NULL,"
            + "FOREIGN KEY (" + KEY_OVERALLAIM_FK_ID + ") REFERENCES " + TABLE_OVERALLAIM + "("+ KEY_OVERALLAIM_ID + "),"
            + "FOREIGN KEY (" + KEY_CRITERION_FK_ID + ") REFERENCES " + TABLE_CRITERION + "("+ KEY_CRITERION_ID + "),"
            + "PRIMARY KEY (" + KEY_OVERALLAIM_FK_ID +", "+ KEY_CRITERION_FK_ID +"));";


    // OUTCOME_CRITERION table - column names
    private static final String KEY_OUTCOME_CRITERION_OWNER_ID = "_id_owner";

    // OUTCOME_CRITERION table - create statement
    public static final String CREATE_TABLE_OUTCOME_CRITERION = "CREATE TABLE " + TABLE_OUTCOME_CRITERION + "("
            + KEY_OUTCOME_FK_ID + " INTEGER NOT NULL, "
            + KEY_CRITERION_FK_ID + " INTEGER NOT NULL, "
            + KEY_OUTCOME_CRITERION_OWNER_ID + " INTEGER NOT NULL,"
            + "FOREIGN KEY (" + KEY_OUTCOME_FK_ID + ") REFERENCES " + TABLE_OUTCOME + "("+ KEY_OUTCOME_ID + "),"
            + "FOREIGN KEY (" + KEY_CRITERION_FK_ID + ") REFERENCES " + TABLE_CRITERION + "("+ KEY_CRITERION_ID + "),"
            + "PRIMARY KEY (" + KEY_OUTCOME_FK_ID +", "+ KEY_CRITERION_FK_ID +"));";

    // OUTPUT_CRITERION table - column names
    private static final String KEY_OUTPUT_CRITERION_OWNER_ID = "_id_owner";

    // OUTPUT_CRITERION table - create statement
    public static final String CREATE_TABLE_OUTPUT_CRITERION = "CREATE TABLE " + TABLE_OUTPUT_CRITERION + "("
            + KEY_OUTPUT_FK_ID + " INTEGER NOT NULL, "
            + KEY_CRITERION_FK_ID + " INTEGER NOT NULL, "
            + KEY_OUTPUT_CRITERION_OWNER_ID + " INTEGER NOT NULL,"
            + "FOREIGN KEY (" + KEY_OUTPUT_FK_ID + ") REFERENCES " + TABLE_OUTPUT + "("+ KEY_OUTPUT_ID + "),"
            + "FOREIGN KEY (" + KEY_CRITERION_FK_ID + ") REFERENCES " + TABLE_CRITERION + "("+ KEY_CRITERION_ID + "),"
            + "PRIMARY KEY (" + KEY_OUTPUT_FK_ID +", "+ KEY_CRITERION_FK_ID +"));";

    // ACTIVITY_CRITERION table - column names
    private static final String KEY_ACTIVITY_CRITERION_OWNER_ID = "_id_owner";

    // ACTIVITY_CRITERION table - create statement
    public static final String CREATE_TABLE_ACTIVITY_CRITERION = "CREATE TABLE " + TABLE_ACTIVITY_CRITERION + "("
            + KEY_ACTIVITY_FK_ID + " INTEGER NOT NULL, "
            + KEY_CRITERION_FK_ID + " INTEGER NOT NULL, "
            + KEY_ACTIVITY_CRITERION_OWNER_ID + " INTEGER NOT NULL,"
            + "FOREIGN KEY (" + KEY_ACTIVITY_FK_ID + ") REFERENCES " + TABLE_ACTIVITY + "("+ KEY_ACTIVITY_ID + "),"
            + "FOREIGN KEY (" + KEY_CRITERION_FK_ID + ") REFERENCES " + TABLE_CRITERION + "("+ KEY_CRITERION_ID + "),"
            + "PRIMARY KEY (" + KEY_ACTIVITY_FK_ID +", "+ KEY_CRITERION_FK_ID +"));";

    // QUESTION_CATEGORY table - column names
    //private static final String KEY_QUESTION_FK_ID = "_id_question_fk";
    private static final String KEY_QUESTION_CATEGORY_OWNER_ID = "_id_owner";

    // QUESTION_CATEGORY table - create statement
    public static final String CREATE_TABLE_QUESTION_CATEGORY = "CREATE TABLE " + TABLE_QUESTION_CATEGORY + "("
            + KEY_QUESTION_FK_ID + " INTEGER NOT NULL, "
            + KEY_CATEGORY_FK_ID + " INTEGER NOT NULL, "
            + KEY_QUESTION_CATEGORY_OWNER_ID + " INTEGER NOT NULL,"
            + "FOREIGN KEY (" + KEY_QUESTION_FK_ID + ") REFERENCES " + TABLE_QUESTION + "("+ KEY_QUESTION_ID + "),"
            + "FOREIGN KEY (" + KEY_CATEGORY_FK_ID + ") REFERENCES " + TABLE_CATEGORY+ "("+ KEY_CATEGORY_ID + "),"
            + "PRIMARY KEY (" + KEY_QUESTION_FK_ID +", "+ KEY_CATEGORY_FK_ID +"));";

    // INDICATOR_QUESTION table - column names
    private static final String KEY_INDICATOR_QUESTION_OWNER_ID = "_id_owner";

    // INDICATOR_QUESTION table - create statement
    public static final String CREATE_TABLE_INDICATOR_QUESTION = "CREATE TABLE " + TABLE_INDICATOR_QUESTION + "("
            + KEY_INDICATOR_FK_ID + " INTEGER NOT NULL, "
            + KEY_QUESTION_FK_ID + " INTEGER NOT NULL, "
            + KEY_INDICATOR_QUESTION_OWNER_ID + " INTEGER NOT NULL,"
            + "FOREIGN KEY (" + KEY_INDICATOR_FK_ID + ") REFERENCES " + TABLE_INDICATOR + "("+ KEY_INDICATOR_ID + "),"
            + "FOREIGN KEY (" + KEY_QUESTION_FK_ID + ") REFERENCES " + TABLE_QUESTION + "("+ KEY_QUESTION_ID + "),"
            + "PRIMARY KEY (" + KEY_INDICATOR_FK_ID +", "+ KEY_QUESTION_FK_ID +"));";


    // OVERALLAIM_CRITERION_QUESTION table - column names
    private static final String KEY_OVERALLAIM_CRITERION_QUESTION_OWNER_ID = "_id_owner";

    // OVERALLAIM_CRITERION_QUESTION table - create statement
    public static final String CREATE_TABLE_OVERALLAIM_CRITERION_QUESTION = "CREATE TABLE " + TABLE_OVERALLAIM_CRITERION_QUESTION + "("
            + KEY_OVERALLAIM_FK_ID + " INTEGER NOT NULL, "
            + KEY_CRITERION_FK_ID + " INTEGER NOT NULL, "
            + KEY_QUESTION_FK_ID + " INTEGER NOT NULL, "
            + KEY_OVERALLAIM_CRITERION_QUESTION_OWNER_ID + " INTEGER NOT NULL,"
            + "FOREIGN KEY (" + KEY_OVERALLAIM_FK_ID + ") REFERENCES " + TABLE_OVERALLAIM + "("+ KEY_OVERALLAIM_ID + "),"
            + "FOREIGN KEY (" + KEY_CRITERION_FK_ID + ") REFERENCES " + TABLE_CRITERION + "("+ KEY_CRITERION_ID + "),"
            + "FOREIGN KEY (" + KEY_QUESTION_FK_ID + ") REFERENCES " + TABLE_QUESTION + "("+ KEY_QUESTION_ID + "),"
            + "PRIMARY KEY (" + KEY_OVERALLAIM_FK_ID +", "+ KEY_CRITERION_FK_ID +", "+ KEY_QUESTION_FK_ID +"));";

    // OUTCOME_CRITERION_QUESTION table - column names
    private static final String KEY_OUTCOME_CRITERION_QUESTION_OWNER_ID = "_id_owner";

    // OUTCOME_CRITERION_QUESTION table - create statement
    public static final String CREATE_TABLE_OUTCOME_CRITERION_QUESTION = "CREATE TABLE " + TABLE_OUTCOME_CRITERION_QUESTION + "("
            + KEY_OUTCOME_FK_ID + " INTEGER NOT NULL, "
            + KEY_CRITERION_FK_ID + " INTEGER NOT NULL, "
            + KEY_QUESTION_FK_ID + " INTEGER NOT NULL, "
            + KEY_OUTCOME_CRITERION_QUESTION_OWNER_ID + " INTEGER NOT NULL,"
            + "FOREIGN KEY (" + KEY_OUTCOME_FK_ID + ") REFERENCES " + TABLE_OUTCOME + "("+ KEY_OUTCOME_ID + "),"
            + "FOREIGN KEY (" + KEY_CRITERION_FK_ID + ") REFERENCES " + TABLE_CRITERION + "("+ KEY_CRITERION_ID + "),"
            + "FOREIGN KEY (" + KEY_QUESTION_FK_ID + ") REFERENCES " + TABLE_QUESTION + "("+ KEY_QUESTION_ID + "),"
            + "PRIMARY KEY (" + KEY_OUTCOME_FK_ID +", "+ KEY_CRITERION_FK_ID +", "+ KEY_QUESTION_FK_ID +"));";

    // OUTPUT_CRITERION_QUESTION table - column names
    private static final String KEY_OUTPUT_CRITERION_QUESTION_OWNER_ID = "_id_owner";

    // OUTPUT_CRITERION_QUESTION table - create statement
    public static final String CREATE_TABLE_OUTPUT_CRITERION_QUESTION = "CREATE TABLE " + TABLE_OUTPUT_CRITERION_QUESTION + "("
            + KEY_OUTPUT_FK_ID + " INTEGER NOT NULL, "
            + KEY_CRITERION_FK_ID + " INTEGER NOT NULL, "
            + KEY_QUESTION_FK_ID + " INTEGER NOT NULL, "
            + KEY_OUTPUT_CRITERION_QUESTION_OWNER_ID + " INTEGER NOT NULL,"
            + "FOREIGN KEY (" + KEY_OUTPUT_FK_ID + ") REFERENCES " + TABLE_OUTPUT + "("+ KEY_OUTPUT_ID + "),"
            + "FOREIGN KEY (" + KEY_CRITERION_FK_ID + ") REFERENCES " + TABLE_CRITERION + "("+ KEY_CRITERION_ID + "),"
            + "FOREIGN KEY (" + KEY_QUESTION_FK_ID + ") REFERENCES " + TABLE_QUESTION + "("+ KEY_QUESTION_ID + "),"
            + "PRIMARY KEY (" + KEY_OUTPUT_FK_ID +", "+ KEY_CRITERION_FK_ID +", "+ KEY_QUESTION_FK_ID +"));";

    // ACTIVITY_CRITERION_QUESTION table - column names
    private static final String KEY_ACTIVITY_CRITERION_QUESTION_OWNER_ID = "_id_owner";

    // ACTIVITY_CRITERION_QUESTION table - create statement
    public static final String CREATE_TABLE_ACTIVITY_CRITERION_QUESTION = "CREATE TABLE " + TABLE_ACTIVITY_CRITERION_QUESTION + "("
            + KEY_ACTIVITY_FK_ID + " INTEGER NOT NULL, "
            + KEY_CRITERION_FK_ID + " INTEGER NOT NULL, "
            + KEY_QUESTION_FK_ID + " INTEGER NOT NULL, "
            + KEY_ACTIVITY_CRITERION_QUESTION_OWNER_ID + " INTEGER NOT NULL,"
            + "FOREIGN KEY (" + KEY_ACTIVITY_FK_ID + ") REFERENCES " + TABLE_ACTIVITY + "("+ KEY_ACTIVITY_ID + "),"
            + "FOREIGN KEY (" + KEY_CRITERION_FK_ID + ") REFERENCES " + TABLE_CRITERION + "("+ KEY_CRITERION_ID + "),"
            + "FOREIGN KEY (" + KEY_QUESTION_FK_ID + ") REFERENCES " + TABLE_QUESTION + "("+ KEY_QUESTION_ID + "),"
            + "PRIMARY KEY (" + KEY_ACTIVITY_FK_ID +", "+ KEY_CRITERION_FK_ID +", "+ KEY_QUESTION_FK_ID +"));";

    // MONITORING_QUESTIONNAIRE table - column names
    private static final String KEY_MONITORING_FK_ID = "_id_monitoring_fk";
    private static final String KEY_MONITORING_QUESTIONNAIRE_OWNER_ID = "_id_owner";

    // MONITORING_QUESTIONNAIRE table - create statement
    public static final String CREATE_TABLE_MONITORING_QUESTIONNAIRE = "CREATE TABLE " + TABLE_MONITORING_QUESTIONNAIRE + "("
            + KEY_MONITORING_FK_ID + " INTEGER NOT NULL, "
            + KEY_QUESTION_FK_ID + " INTEGER NOT NULL, "
            + KEY_MONITORING_QUESTIONNAIRE_OWNER_ID + " INTEGER NOT NULL,"
            + "FOREIGN KEY (" + KEY_MONITORING_FK_ID + ") REFERENCES " + TABLE_MONITORING + "("+ KEY_MONITORING_ID + "),"
            + "FOREIGN KEY (" + KEY_QUESTION_FK_ID + ") REFERENCES " + TABLE_QUESTION + "("+ KEY_QUESTION_ID + "),"
            + "PRIMARY KEY (" + KEY_MONITORING_FK_ID +", "+ KEY_QUESTION_FK_ID +"));";

    // EVALUATION_QUESTIONNAIRE table - column names
    private static final String KEY_EVALUATION_FK_ID = "_id_monitoring_fk";
    private static final String KEY_EVALUATION_QUESTIONNAIRE_OWNER_ID = "_id_owner";

    // EVALUATION_QUESTIONNAIRE table - create statement
    public static final String CREATE_TABLE_EVALUATION_QUESTIONNAIRE = "CREATE TABLE " + TABLE_EVALUATION_QUESTIONNAIRE + "("
            + KEY_EVALUATION_FK_ID + " INTEGER NOT NULL, "
            + KEY_QUESTION_FK_ID + " INTEGER NOT NULL, "
            + KEY_EVALUATION_QUESTIONNAIRE_OWNER_ID + " INTEGER NOT NULL,"
            + "FOREIGN KEY (" + KEY_EVALUATION_FK_ID + ") REFERENCES " + TABLE_EVALUATION + "("+ KEY_EVALUATION_ID + "),"
            + "FOREIGN KEY (" + KEY_QUESTION_FK_ID + ") REFERENCES " + TABLE_QUESTION + "("+ KEY_QUESTION_ID + "),"
            + "PRIMARY KEY (" + KEY_EVALUATION_FK_ID +", "+ KEY_QUESTION_FK_ID +"));";


    // MONITORING_DATACOLLECTION table - column names
    private static final String KEY_MONITORING_DATACOLLECTION_OWNER_ID = "_id_owner";
    private static final String KEY_MONITORING_RESPONSE = "response";

    // MONITORING_DATACOLLECTION table - create statement
    public static final String CREATE_TABLE_MONITORING_DATACOLLECTION = "CREATE TABLE " + TABLE_MONITORING_DATACOLLECTION + "("
            + KEY_MONITORING_FK_ID + " INTEGER NOT NULL, "
            + KEY_QUESTION_FK_ID + " INTEGER NOT NULL, "
            + KEY_USER_FK_ID + " INTEGER NOT NULL, "
            + KEY_MONITORING_DATACOLLECTION_OWNER_ID + " INTEGER NOT NULL,"
            + KEY_MONITORING_RESPONSE + " INTEGER NOT NULL,"
            + "FOREIGN KEY (" + KEY_MONITORING_FK_ID + ") REFERENCES " + TABLE_MONITORING + "("+ KEY_MONITORING_ID + "),"
            + "FOREIGN KEY (" + KEY_QUESTION_FK_ID + ") REFERENCES " + TABLE_QUESTION + "("+ KEY_QUESTION_ID + "),"
            + "FOREIGN KEY (" + KEY_USER_FK_ID + ") REFERENCES " + TABLE_tblUSER + "("+ KEY_USER_ID + "),"
            + "PRIMARY KEY (" + KEY_MONITORING_FK_ID +", "+ KEY_QUESTION_FK_ID +", "+ KEY_USER_FK_ID +"));";

    // EVALUATION_DATACOLLECTION table - column names
    private static final String KEY_EVALUATION_DATACOLLECTION_OWNER_ID = "_id_owner";
    private static final String KEY_EVALUATION_RESPONSE = "response";

    // EVALUATION_DATACOLLECTION table - create statement
    public static final String CREATE_TABLE_EVALUATION_DATACOLLECTION = "CREATE TABLE " + TABLE_EVALUATION_DATACOLLECTION + "("
            + KEY_EVALUATION_FK_ID + " INTEGER NOT NULL, "
            + KEY_QUESTION_FK_ID + " INTEGER NOT NULL, "
            + KEY_USER_FK_ID + " INTEGER NOT NULL, "
            + KEY_EVALUATION_DATACOLLECTION_OWNER_ID + " INTEGER NOT NULL,"
            + KEY_EVALUATION_RESPONSE + " INTEGER NOT NULL,"
            + "FOREIGN KEY (" + KEY_EVALUATION_FK_ID + ") REFERENCES " + TABLE_EVALUATION + "("+ KEY_EVALUATION_ID + "),"
            + "FOREIGN KEY (" + KEY_QUESTION_FK_ID + ") REFERENCES " + TABLE_QUESTION + "("+ KEY_QUESTION_ID + "),"
            + "FOREIGN KEY (" + KEY_USER_FK_ID + ") REFERENCES " + TABLE_tblUSER + "("+ KEY_USER_ID + "),"
            + "PRIMARY KEY (" + KEY_EVALUATION_FK_ID +", "+ KEY_QUESTION_FK_ID +", "+ KEY_USER_FK_ID +"));";





    public cSQLDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //this.context = context;
    }
    public cSQLDBHelper(Context context, int dbVersion, String dbName) {
        super(context, dbName, null, dbVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        /** drop all tables **/
        dropTables(db);

        /** create all tables **/
        createTables(db);
    }
 
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        /** create new tables **/
        onCreate(db);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        //onCreate(db);
        //if (!db.isReadOnly()) {
            // enable foreign key constraints
            //db.execSQL("PRAGMA foreign_keys=ON;");
        //}
    }

    public void createTables(SQLiteDatabase db){
        // user access control tables
        /*db.execSQL(CREATE_TABLE_ADDRESS);
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
        db.execSQL(CREATE_TABLE_PERMISSION);*/

        String[] create_brbac_tables = new String[]{
                CREATE_TABLE_tblADDRESS, CREATE_TABLE_tblORGANIZATION, CREATE_TABLE_tblVALUE,
                CREATE_TABLE_tblUSER, CREATE_TABLE_tblSESSION, CREATE_TABLE_tblROLE, CREATE_TABLE_tblMENU,
                CREATE_TABLE_tblPRIVILEGE, CREATE_TABLE_tblENTITY, CREATE_TABLE_tblOPERATION,
                CREATE_TABLE_tblSTATUS, CREATE_TABLE_tblORG_ADDRESS, CREATE_TABLE_tblUSER_ADDRESS,
                CREATE_TABLE_tblUSER_ROLE, CREATE_TABLE_tblSESSION_ROLE, CREATE_TABLE_tblMENU_ROLE,
                CREATE_TABLE_tblPERMISSION, CREATE_TABLE_tblNOTIFICATION, CREATE_TABLE_tblSETTING,
                CREATE_TABLE_tblSUBSCRIBER, CREATE_TABLE_tblPUBLISHER, CREATE_TABLE_tblSETNOTIFICATION,
                CREATE_TABLE_tbltblSUBPUB, CREATE_TABLE_tblACTIVITYLOG
        };

        String[] create_planning_tables = new String[]{
                CREATE_TABLE_tblLOGFRAME, CREATE_TABLE_tblLOGFRAMETREE, CREATE_TABLE_tblIMPACT,
                CREATE_TABLE_tblOUTCOME, CREATE_TABLE_tblOUTPUT, CREATE_TABLE_tblACTIVITY,
                CREATE_TABLE_tblPRECEDINGACTIVITY, CREATE_TABLE_tblINPUT, CREATE_TABLE_tblOUTCOME_IMPACT,
                CREATE_TABLE_tblOUTPUT_OUTCOME, CREATE_TABLE_tblACTIVITY_OUTPUT, CREATE_TABLE_tblCRITERIA,
                CREATE_TABLE_tblQUESTION_GROUPING, CREATE_TABLE_tblQUESTION_TYPE, CREATE_TABLE_tblCHOICESET,
                CREATE_TABLE_tblQUESTION, CREATE_TABLE_tblIMPACT_QUESTION, CREATE_TABLE_tblOUTCOME_QUESTION,
                CREATE_TABLE_tblOUTPUT_QUESTION, CREATE_TABLE_tblACTIVITY_QUESTION, CREATE_TABLE_tblINPUT_QUESTION,
                CREATE_TABLE_tblRAID, CREATE_TABLE_tblIMPACT_RAID, CREATE_TABLE_tblOUTCOME_RAID,
                CREATE_TABLE_tblOUTPUT_RAID, CREATE_TABLE_tblACTIVITY_RAID
        };

        db.beginTransaction();
        try {
            for (String create_brbac_table : create_brbac_tables){
                db.execSQL(create_brbac_table);
            }
            for (String create_planning_table : create_planning_tables){
                db.execSQL(create_planning_table);
            }
            db.setTransactionSuccessful();
        }catch (SQLException e){
            Log.e("Error onCreate Tables", e.toString());
            throw e;
        }finally {
            db.endTransaction();
        }

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

        /* bitwise role based access control tables
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
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PERMISSION);*/

        /** drop all tables **/
        String[] tables = new String[]{
                TABLE_tblLOGFRAME, TABLE_tblLOGFRAMETREE, TABLE_tblIMPACT,
                TABLE_tblOUTCOME, TABLE_tblOUTPUT, TABLE_tblACTIVITY,
                TABLE_tblPRECEDINGACTIVITY, TABLE_tblINPUT, TABLE_tblOUTCOME_IMPACT,
                TABLE_tblOUTPUT_OUTCOME, TABLE_tblACTIVITY_OUTPUT, TABLE_tblCRITERIA,
                TABLE_tblQUESTION_GROUPING, TABLE_tblQUESTION_TYPE, TABLE_tblCHOICESET,
                TABLE_tblQUESTION, TABLE_tblIMPACT_QUESTION, TABLE_tblOUTCOME_QUESTION,
                TABLE_tblOUTPUT_QUESTION, TABLE_tblACTIVITY_QUESTION, TABLE_tblINPUT_QUESTION,
                TABLE_tblRAID, TABLE_tblIMPACT_RAID, TABLE_tblOUTCOME_RAID, TABLE_tblOUTPUT_RAID,
                TABLE_tblACTIVITY_RAID
        };

        db.beginTransaction();
        try {
            for (String table : tables){
                db.execSQL("DROP TABLE IF EXISTS " + table);
            }
            db.setTransactionSuccessful();
        }catch (SQLException e){
            Log.e("Error onUpgrade Tables", e.toString());
        }finally {
            db.endTransaction();
        }

/*
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
 */
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


/*



    */