package com.me.mseotsanyana.mande.STORAGE.database;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.me.mseotsanyana.mande.UTILITY.cConstant;

import java.text.SimpleDateFormat;

public class cSQLDBHelper extends SQLiteOpenHelper {
    private static SimpleDateFormat sdf = cConstant.FORMAT_DATE;
    private static String TAG = cSQLDBHelper.class.getSimpleName();

    // Database Name
    private static final String DATABASE_NAME = "MEDB.db";
    // Database Version
    private static final int DATABASE_VERSION = 89;

    /*################################ START GLOBAL MODULE TABLES ################################*/

    public static final String TABLE_tblFREQUENCY          = "tblFREQUENCY";       /* 1 */
    public static final String TABLE_tblPERIOD             = "tblPERIOD";          /* 2 */
    public static final String TABLE_tblFISCALYEAR         = "tblFISCALYEAR";      /* 3 */

    /*############################### START GLOBAL MODULE TABLES #################################*/

    /*################################ START BRBAC MODULE TABLES #################################*/

    public static final String TABLE_tblADDRESS            = "tblADDRESS";           /* 1  */
    public static final String TABLE_tblORGANIZATION       = "tblORGANIZATION";      /* 2  */
    public static final String TABLE_tblFUNDER             = "tblFUNDER";            /* 3  */
    public static final String TABLE_tblBENEFICIARY        = "tblBENEFICIARY";       /* 4  */
    public static final String TABLE_tblIMPLEMENTINGAGENCY = "tblIMPLEMENTINGAGENCY";/* 5  */
    public static final String TABLE_tblVALUE              = "tblVALUE";             /* 6  */
    public static final String TABLE_tblUSER               = "tblUSER";              /* 7  */
    public static final String TABLE_tblSESSION            = "tblSESSION";           /* 8  */
    public static final String TABLE_tblROLE               = "tblROLE";              /* 9  */
    public static final String TABLE_tblMENU               = "tblMENU";              /* 10 */
    public static final String TABLE_tblPRIVILEGE          = "tblPRIVILEGE";         /* 11 */
    public static final String TABLE_tblENTITY             = "tblENTITY";            /* 12 */
    public static final String TABLE_tblOPERATION          = "tblOPERATION";         /* 13 */
    public static final String TABLE_tblSTATUS             = "tblSTATUS";            /* 14 */
    public static final String TABLE_tblORG_ADDRESS        = "tblORG_ADDRESS";       /* 15 */
    public static final String TABLE_tblUSER_ADDRESS       = "tblUSER_ADDRESS";      /* 16 */
    public static final String TABLE_tblUSER_ROLE          = "tblUSER_ROLE";         /* 17 */
    public static final String TABLE_tblSESSION_ROLE       = "tblSESSION_ROLE";      /* 18 */
    public static final String TABLE_tblMENU_ROLE          = "tblMENU_ROLE";         /* 19 */
    public static final String TABLE_tblPERMISSION         = "tblPERMISSION";        /* 20 */
    public static final String TABLE_tblPRIV_STATUS        = "tblPRIV_STATUS";       /* 21 */
    public static final String TABLE_tblSETTING            = "tblSETTING";           /* 22 */
    public static final String TABLE_tblNOTIFICATION       = "tblNOTIFICATION";      /* 23 */
    public static final String TABLE_tblSUBSCRIBER         = "tblSUBSCRIBER";        /* 24 */
    public static final String TABLE_tblPUBLISHER          = "tblPUBLISHER";         /* 25 */
    public static final String TABLE_tblNOTIFY_SETTING     = "tblNOTIFY_SETTING";    /* 26 */
    public static final String TABLE_tblACTIVITYLOG        = "tblACTIVITYLOG";       /* 27 */

    /*################################ END BRBAC MODULE TABLES ###################################*/

    /*############################## START LOGFRAME MODULE TABLES ################################*/

    public static final String TABLE_tblLOGFRAME           = "tblLOGFRAME";          /* 1  */
    public static final String TABLE_tblLOGFRAMETREE       = "tblLOGFRAMETREE";      /* 2  */
    public static final String TABLE_tblIMPACT             = "tblIMPACT";            /* 3  */
    public static final String TABLE_tblOUTCOME            = "tblOUTCOME";           /* 4  */
    public static final String TABLE_tblOUTPUT             = "tblOUTPUT";            /* 5  */
    public static final String TABLE_tblACTIVITYPLANNING   = "tblACTIVITYPLANNING";  /* 6  */
    public static final String TABLE_tblACTIVITY           = "tblACTIVITY";          /* 7  */
    public static final String TABLE_tblINPUT              = "tblINPUT";             /* 8  */
    public static final String TABLE_tblRESOURCETYPE       = "tblRESOURCETYPE";      /* 9  */
    public static final String TABLE_tblRESOURCE           = "tblRESOURCE";          /* 10  */
    public static final String TABLE_tblEVALUATIONCRITERIA = "tblEVALUATIONCRITERIA";/* 11 */
    public static final String TABLE_tblQUESTIONGROUPING   = "tblQUESTIONGROUPING";  /* 12 */
    public static final String TABLE_tblPRIMITIVETYPE      = "tblPRIMITIVETYPE";     /* 13 */
    public static final String TABLE_tblQUESTIONTYPE       = "tblQUESTIONTYPE";      /* 14 */
    public static final String TABLE_tblARRAYTYPE          = "tblARRAYTYPE";         /* 15 */
    public static final String TABLE_tblMATRIXTYPE         = "tblMATRIXTYPE";        /* 16 */
    public static final String TABLE_tblQUESTION           = "tblQUESTION";          /* 17 */
    public static final String TABLE_tblRAID               = "tblRAID";              /* 18 */
    public static final String TABLE_tblOUTCOME_IMPACT     = "tblOUTCOME_IMPACT";    /* 19 */
    public static final String TABLE_tblOUTPUT_OUTCOME     = "tblOUTPUT_OUTCOME";    /* 20 */
    public static final String TABLE_tblACTIVITY_OUTPUT    = "tblACTIVITY_OUTPUT";   /* 21 */
    public static final String TABLE_tblPRECEDINGACTIVITY  = "tblPRECEDINGACTIVITY"; /* 22 */
    public static final String TABLE_tblACTIVITYASSIGNMENT = "tblACTIVITYASSIGNMENT";/* 23 */
    public static final String TABLE_tblIMPACT_QUESTION    = "tblIMPACT_QUESTION";   /* 24 */
    public static final String TABLE_tblOUTCOME_QUESTION   = "tblOUTCOME_QUESTION";  /* 25 */
    public static final String TABLE_tblOUTPUT_QUESTION    = "tblOUTPUT_QUESTION";   /* 26 */
    public static final String TABLE_tblACTIVITY_QUESTION  = "tblACTIVITY_QUESTION"; /* 27 */
    public static final String TABLE_tblINPUT_QUESTION     = "tblINPUT_QUESTION";    /* 28 */
    public static final String TABLE_tblIMPACT_RAID        = "tblIMPACT_RAID";       /* 29 */
    public static final String TABLE_tblOUTCOME_RAID       = "tblOUTCOME_RAID";      /* 30 */
    public static final String TABLE_tblOUTPUT_RAID        = "tblOUTPUT_RAID";       /* 31 */
    public static final String TABLE_tblACTIVITY_RAID      = "tblACTIVITY_RAID";     /* 32 */

    /*############################### END LOGFRAME MODULE TABLES #################################*/

    /*################################ START AWPB MODULE TABLES ##################################*/

    public static final String TABLE_tblHUMAN             = "tblHUMAN";            /* 1  */
    public static final String TABLE_tblMATERIAL          = "tblMATERIAL";         /* 2  */
    public static final String TABLE_tblINCOME            = "tblINCOME";           /* 3  */
    public static final String TABLE_tblEXPENSE           = "tblEXPENSE";          /* 4  */
    public static final String TABLE_tblHUMANSET          = "tblHUMANSET";         /* 5  */
    public static final String TABLE_tblFUND              = "tblFUND";             /* 6  */
    public static final String TABLE_tblMILESTONE         = "'tblMILESTONE'";      /* 7  */
    public static final String TABLE_tblTASK              = "tblTASK";             /* 8  */
    public static final String TABLE_tblACTIVITYTASK      = "tblACTIVITYTASK";     /* 9  */
    public static final String TABLE_tblPRECEDINGTASK     = "tblPRECEDINGTASK";    /* 10 */
    public static final String TABLE_tblTASK_MILESTONE    = "tblTASK_MILESTONE";   /* 11 */
    public static final String TABLE_tblTASKASSIGNMENT    = "tblTASKASSIGNMENT";   /* 12 */
    public static final String TABLE_tblUSERCOMMENT       = "tblUSERCOMMENT";      /* 13 */
    public static final String TABLE_tblTIMESHEET         = "tblTIMESHEET";        /* 14 */
    public static final String TABLE_tblINVOICE           = "tblINVOICE";          /* 15 */
    public static final String TABLE_tblINVOICE_TIMESHEET = "tblINVOICE_TIMESHEET";/* 16 */
    public static final String TABLE_tblEXTENSION         = "tblEXTENSION";        /* 17 */
    public static final String TABLE_tblEXT_DOC_TYPE      = "tblEXT_DOC_TYPE";     /* 18 */
    public static final String TABLE_tblDOCUMENT          = "tblDOCUMENT";         /* 19 */
    public static final String TABLE_tblTRANSACTION       = "tblTRANSACTION";      /* 20 */
    public static final String TABLE_tblINTERNAL          = "tblINTERNAL";         /* 21 */
    public static final String TABLE_tblEXTERNAL          = "tblEXTERNAL";         /* 22 */
    public static final String TABLE_tblJOURNAL           = "tblJOURNAL";          /* 23 */

    /*################################# END AWPB MODULE TABLES ###################################*/

    /*################################ START RAID MODULE TABLES ##################################*/

    public static final String TABLE_tblRISKREGISTER      = "tblRISKREGISTER";     /* 1  */
    public static final String TABLE_tblRISKLIKELIHOOD    = "tblRISKLIKELIHOOD";   /* 2  */
    public static final String TABLE_tblRISKLIKELIHOODSET = "tblRISKLIKELIHOODSET";/* 3  */
    public static final String TABLE_tblRISKIMPACT        = "tblRISKIMPACT";       /* 4  */
    public static final String TABLE_tblRISKIMPACTSET     = "tblRISKIMPACTSET";    /* 5  */
    public static final String TABLE_tblRISKCRITERIA      = "tblRISKCRITERIA";     /* 6  */
    public static final String TABLE_tblRISKCRITERIASET   = "tblRISKCRITERIASET";  /* 7  */
    public static final String TABLE_tblRISK              = "tblRISK";             /* 8  */
    public static final String TABLE_tblRISKROOTCAUSE     = "tblRISKROOTCAUSE";    /* 9  */
    public static final String TABLE_tblRISKCONSEQUENCE   = "tblRISKCONSEQUENCE";  /* 10 */
    public static final String TABLE_tblCURRENTCONTROL    = "tblCURRENTCONTROL";   /* 11 */
    public static final String TABLE_tblADDITIONALCONTROL = "tblADDITIONALCONTROL";/* 12 */
    public static final String TABLE_tblRISKMILESTONE     = "tblRISKMILESTONE";    /* 13 */
    public static final String TABLE_tblRISKANALYSIS      = "tblRISKANALYSIS";     /* 14 */
    public static final String TABLE_tblRISKPLAN          = "tblRISKPLAN";         /* 15 */
    public static final String TABLE_tblRISKACTIONTYPE    = "tblRISKACTIONTYPE";   /* 16 */
    public static final String TABLE_tblRISKACTION        = "tblRISKACTION";       /* 17 */

    /*################################# END RAID MODULE TABLES ###################################*/


    /*############################# START EVALUATION MODULE TABLES ###############################*/

    public static final String TABLE_tblARRAYCHOICE            = "tblARRAYCHOICE";           /* 1 */
    public static final String TABLE_tblARRAYCHOICESET         = "tblARRAYCHOICESET";        /* 2 */
    public static final String TABLE_tblROWOPTION              = "tblROWOPTION";             /* 3 */
    public static final String TABLE_tblCOLOPTION              = "tblCOLOPTION";             /* 4 */
    public static final String TABLE_tblMATRIXCHOICE           = "tblMATRIXCHOICE";          /* 5 */
    public static final String TABLE_tblMATRIXCHOICESET        = "tblMATRIXCHOICESET";       /* 6 */
    public static final String TABLE_tblEVALUATIONTYPE         = "tblEVALUATIONTYPE";        /* 7 */
    public static final String TABLE_tblQUESTIONNAIRE          = "tblQUESTIONNAIRE";         /* 8 */
    public static final String TABLE_tblQUESTION_QUESTIONNAIRE = "tblQUESTION_QUESTIONNAIRE";/* 9 */
    public static final String TABLE_tblCONDITIONAL_ORDER      = "tblCONDITIONAL_ORDER";     /* 10*/
    public static final String TABLE_tblQUESTIONNAIRE_USER     = "tblQUESTIONNAIRE_USER";    /* 11*/
    public static final String TABLE_tblERESPONSE              = "tblERESPONSE";             /* 12*/
    public static final String TABLE_tblNUMERICRESPONSE        = "tblNUMERICRESPONSE";       /* 13*/
    public static final String TABLE_tblTEXTRESPONSE           = "tblTEXTRESPONSE";          /* 14*/
    public static final String TABLE_tblDATERESPONSE           = "tblDATERESPONSE";          /* 15*/
    public static final String TABLE_tblARRAYRESPONSE          = "tblARRAYRESPONSE";         /* 16*/
    public static final String TABLE_tblMATRIXRESPONSE         = "tblMATRIXRESPONSE";        /* 17*/

    /*############################## END EVALUATION MODULE TABLES ################################*/


    /*############################ START MONITORING MODULE TABLES ################################*/

    public static final String TABLE_tblQUESTION_INDICATOR   = "tblQUESTION_INDICATOR";   /* 1 */
    public static final String TABLE_tblMOV                  = "tblMOV";                  /* 2 */
    public static final String TABLE_tblMETHOD               = "tblMETHOD";               /* 3 */
    public static final String TABLE_tblUNIT                 = "tblUNIT";                 /* 4 */
    public static final String TABLE_tblINDICATORTYPE        = "tblINDICATORTYPE";        /* 5 */
    public static final String TABLE_tblQUALITATIVE          = "tblQUALITATIVE";          /* 6 */
    public static final String TABLE_tblQUANTITATIVE         = "tblQUANTITATIVE";         /* 7 */
    public static final String TABLE_tblDATACOLLECTOR        = "tblDATACOLLECTOR";        /* 8 */
    public static final String TABLE_tblINDICATOR            = "tblINDICATOR";            /* 9 */
    public static final String TABLE_tblINDICATORMILESTONE   = "tblINDICATORMILESTONE";   /* 10*/
    public static final String TABLE_tblINDICATOR_MOV        = "tblINDICATOR_MOV";        /* 11*/
    public static final String TABLE_tblINDICATOR_METHOD     = "tblINDICATOR_METHOD";     /* 12*/
    public static final String TABLE_tblQUALITATIVECHOICE    = "tblQUALITATIVECHOICE";    /* 13*/
    public static final String TABLE_tblQUALITATIVECHOICESET = "tblQUALITATIVECHOICESET"; /* 14*/
    public static final String TABLE_tblMRESPONSE            = "tblMRESPONSE";            /* 15*/
    public static final String TABLE_tblQUALITATIVERESPONSE  = "tblQUALITATIVERESPONSE";  /* 16*/
    public static final String TABLE_tblQUANTITATIVERESPONSE = "tblQUANTITATIVERESPONSE"; /* 17*/

    /*############################## END MONITORING MODULE TABLES ################################*/

    /*###################################### START OF KEYS #######################################*/

    public static final String KEY_ID = "_id";
    public static final String KEY_ENTITY_TYPE_ID = "_id_entity_type";
    public static final String KEY_OWNER_ID = "_id_owner";
    public static final String KEY_ORG_ID = "_id_org";
    public static final String KEY_SERVER_ID = "_id_server";
    public static final String KEY_GROUP_BITS = "_bits_group";
    public static final String KEY_PERMS_BITS = "_bits_perms";
    public static final String KEY_STATUS_BITS = "_bits_status";
    public static final String KEY_CREATED_DATE = "created_date";
    public static final String KEY_MODIFIED_DATE = "modified_date";
    public static final String KEY_SYNCED_DATE = "synced_date";
    public static final String KEY_START_DATE = "start_date";
    public static final String KEY_END_DATE = "end_date";
    public static final String KEY_NAME = "name";
    public static final String KEY_SURNAME = "surname";
    public static final String KEY_GENDER = "gender";
    public static final String KEY_PHONE = "phone";
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_STREET = "street";
    public static final String KEY_CITY = "city";
    public static final String KEY_PROVINCE = "province";
    public static final String KEY_POSTAL_CODE = "postal_code";
    public static final String KEY_COUNTRY = "country";
    public static final String KEY_TELEPHONE = "telephone";
    public static final String KEY_FAX = "fax";
    public static final String KEY_VISION = "vision";
    public static final String KEY_MISSION = "mission";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_WEBSITE = "website";
    public static final String KEY_PHOTO = "photo_path";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_SALT = "salt";
    public static final String KEY_SETTING_VALUE = "setting_value";

    public static final String KEY_QUESTION = "question";
    public static final String KEY_TARGET_VALUE = "target_value";
    public static final String KEY_QUANTITY = "quantity";
    public static final String KEY_EXPENSE = "expense";
    public static final String KEY_FUND = "fund";
    public static final String KEY_HOURS = "hours";
    public static final String KEY_RATE = "rate";
    public static final String KEY_USER_COMMENT = "comment";
    public static final String KEY_START_TIME = "start_time";
    public static final String KEY_END_TIME = "end_time";
    public static final String KEY_ENTRY_TYPE = "entry_type";
    public static final String KEY_AMOUNT = "amount";

    public static final String KEY_MILESTONE_FK_ID = "_id_milestone_fk";
    public static final String KEY_USER_FK_ID = "_id_user_fk";
    //public static final String KEY_HUMANSET_FK_ID = "_id_humanset_fk";
    public static final String KEY_SUBSCRIBER_FK_ID = "_id_subscriber_fk";
    public static final String KEY_PUBLISHER_FK_ID = "_id_publisher_fk";
    public static final String KEY_PARENT_FK_ID = "_id_parent_fk";
    public static final String KEY_CHILD_FK_ID = "_id_child_fk";
    public static final String KEY_LOGFRAME_FK_ID = "_id_logframe_fk";
    public static final String KEY_IMPACT_FK_ID = "_id_impact_fk";
    public static final String KEY_OUTCOME_FK_ID = "_id_outcome_fk";
    public static final String KEY_OUTPUT_FK_ID = "_id_output_fk";
    public static final String KEY_ACTIVITY_FK_ID = "_id_activity_fk";
    public static final String KEY_ACTIVITYPLANNING_FK_ID = "_id_activity_planning_fk";
    public static final String KEY_PRECEDING_ACTIVITY_FK_ID = "_id_preceding_activity_fk";
    public static final String KEY_INPUT_FK_ID = "_id_input_fk";
    public static final String KEY_RESOURCE_TYPE_FK_ID = "_id_resource_type";
    public static final String KEY_QUESTION_FK_ID = "_id_question_fk";
    public static final String KEY_EVALUATION_TYPE_FK_ID = "_id_evaluation_type_fk";
    public static final String KEY_QUESTION_GROUPING_FK_ID = "_id_question_grouping_fk";
    public static final String KEY_QUESTION_TYPE_FK_ID = "_id_question_type_fk";
    public static final String KEY_INDICATOR_TYPE_FK_ID = "_id_indicator_type_fk";
    public static final String KEY_DATA_COLLECTOR_FK_ID = "_id_data_collector_fk";
    public static final String KEY_MATRIX_TYPE_FK_ID = "_id_matrix_type_fk";
    public static final String KEY_ARRAY_TYPE_FK_ID = "_id_array_type_fk";
    public static final String KEY_ARRAY_CHOICE_FK_ID = "_id_array_choice_fk";
    public static final String KEY_ROW_OPTION_FK_ID = "_id_row_option_fk";
    public static final String KEY_COL_OPTION_FK_ID = "_id_col_option_fk";
    public static final String KEY_MATRIX_CHOICE_FK_ID = "_id_matrix_choice_fk";
    public static final String KEY_EVALUATION_CRITERIA_FK_ID = "_id_evaluation_criteria_fk";
    public static final String KEY_RAID_FK_ID = "_id_raid_fk";
    public static final String KEY_RESOURCE_FK_ID = "_id_resource_fk";
    public static final String KEY_ADDRESS_FK_ID = "_id_address_fk";
    public static final String KEY_ORGANIZATION_FK_ID = "_id_organization_fk";
    public static final String KEY_ROLE_FK_ID = "_id_role_fk";
    public static final String KEY_SESSION_FK_ID = "_id_session_fk";
    public static final String KEY_MENU_FK_ID = "_id_menu_fk";
    public static final String KEY_PRIVILEGE_FK_ID = "_id_privilege_fk";
    public static final String KEY_ENTITY_FK_ID = "_id_entity_fk";
    public static final String KEY_ENTITY_TYPE_FK_ID = "_id_entity_type_fk";
    public static final String KEY_OPERATION_FK_ID = "_id_operation_fk";
    public static final String KEY_STATUS_FK_ID = "_id_status_fk";
    public static final String KEY_NOTIFICATION_FK_ID = "_id_notification_fk";
    public static final String KEY_SETTING_FK_ID = "_id_setting_fk";

    public static final String KEY_QUESTIONNAIRE_FK_ID = "_id_questionnaire_fk";
    public static final String KEY_QUESTION_ORDER_FK_ID = "_id_question_order_fk";
    public static final String KEY_QUESTION_RESPONSE_FK_ID = "_id_question_response_fk";
    public static final String KEY_POS_RES_ORDER_FK_ID = "_id_pos_res_order_fk";
    public static final String KEY_NEG_RES_ORDER_FK_ID = "_id_neg_res_order_fk";
    public static final String KEY_INDICATOR_FK_ID = "_id_indicator_fk";
    public static final String KEY_MOV_FK_ID = "_id_mov_fk";
    public static final String KEY_METHOD_FK_ID = "_id_method_fk";
    public static final String KEY_QUALITATIVE_CHOICE_FK_ID = "_id_qualitative_choice_fk";
    public static final String KEY_INDICATOR_MILESTONE_FK_ID = "_id_indicator_milestone_fk";
    public static final String KEY_UNIT_FK_ID = "_id_unit_fk";

    public static final String KEY_MRESPONSE_FK_ID = "_id_mresponse_fk";

    public static final String KEY_NUMERIC_RESPONSE_FK_ID = "_id_numeric_response_fk";
    public static final String KEY_TEXT_RESPONSE_FK_ID = "_id_text_response_fk";
    public static final String KEY_DATE_RESPONSE_FK_ID = "_id_date_response_fk";
    public static final String KEY_ARRAY_RESPONSE_FK_ID = "_id_array_response_fk";
    public static final String KEY_MATRIX_RESPONSE_FK_ID = "_id_matrix_response_fk";
    public static final String KEY_ROW_RESPONSE_FK_ID = "_id_row_response_fk";
    public static final String KEY_COL_RESPONSE_FK_ID = "_id_col_response_fk";

    public static final String KEY_HUMAN_FK_ID = "_id_human_fk";
    public static final String KEY_INCOME_FK_ID = "_id_income_fk";
    public static final String KEY_FUNDER_FK_ID = "_id_funder_fk";
    public static final String KEY_BENEFICIARY_FK_ID = "_id_beneficiary_fk";
    public static final String KEY_TASK_FK_ID = "_id_task_fk";
    public static final String KEY_PRECEDING_TASK_FK_ID = "_id_preceding_task_fk";
    public static final String KEY_STAFF_FK_ID = "_id_staff_fk";
    public static final String KEY_EXTENSION_FK_ID = "_id_extension_fk";
    public static final String KEY_EXT_DOC_TYPE_FK_ID = "_id_ext_doc_type_fk";
    public static final String KEY_DOCUMENT_FK_ID = "_id_document_fk";
    public static final String KEY_TRANSACTION_FK_ID = "_id_transaction_fk";

    public static final String KEY_QUESTIONNAIRE_USER_FK_ID = "_id_questionnaire_user_fk";
    public static final String KEY_RESPONSE_FK_ID = "_id_response_fk";
    public static final String KEY_FREQUENCY_FK_ID = "_id_frequency_fk";
    public static final String KEY_RISK_REGISTER_FK_ID = "_id_risk_register_fk";
    public static final String KEY_RISK_LIKELIHOOD_FK_ID = "_id_risk_likelihood_fk";
    public static final String KEY_RISK_IMPACT_FK_ID = "_id_risk_impact_fk";
    public static final String KEY_RISK_CRITERIA_FK_ID = "_id_risk_criteria_fk";
    public static final String KEY_RISK_FK_ID = "_id_risk_fk";
    public static final String KEY_RISK_PLAN_FK_ID = "_id_risk_plan_fk";
    public static final String KEY_RISK_ACTION_TYPE_FK_ID = "_id_risk_action_type_fk";
    public static final String KEY_RISK_INDEX = "_id_risk_index_fk";
    public static final String KEY_RISK_MILESTONE_FK_ID = "_id_risk_milestone_fk";
    public static final String KEY_RISKLIKELIHOOD_VALUE = "value";
    public static final String KEY_RISKIMPACT_VALUE = "value";
    public static final String KEY_RISKMAP_LOWER = "lower_limit";
    public static final String KEY_RISKMAP_UPPER = "upper_limit";
    public static final String KEY_RISK_ACTION_FK_ID = "_id_risk_action_fk";
    public static final String KEY_RISK_ANALYSIS_FK_ID = "_id_risk_analysis_fk";

    public static final String KEY_USER_ID = "_id_todelete";
    public static final String KEY_ORGANIZATION_ID = "_id_todelete";
    public static final String KEY_STATUS_ID = "_id_todelete";

    public static final String KEY_FISCAL_YEAR_FK_ID = "_id_fiscal_year_fk";
    public static final String KEY_INVOICE_FK_ID = "_id_invoice_fk";
    public static final String KEY_PERIOD_FK_ID = "_id_period_fk";
    public static final String KEY_TIMESHEET_FK_ID = "_id_timesheet_fk";

    /*######################################### END OF KEYS ######################################*/


    /*#################################### START of GLOBAL MODULE ################################*/

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblFREQUENCY` (1)
    //-- -------------------------------------------------------------------------------------------
    public static final String CREATE_TABLE_tblFREQUENCY = "CREATE TABLE " + TABLE_tblFREQUENCY + "("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_SERVER_ID + " INTEGER DEFAULT NULL, "
            + KEY_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL DEFAULT 0, "
            + KEY_NAME + " TEXT NOT NULL, "
            + KEY_DESCRIPTION + " TEXT NULL, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP); ";

    public static final String CREATE_TABLE_tblPERIOD = "CREATE TABLE " + TABLE_tblPERIOD + "("
            + KEY_ID + " INTEGER NOT NULL, "
            + KEY_FISCAL_YEAR_FK_ID + " INTEGER, "
            + KEY_SERVER_ID + " INTEGER DEFAULT NULL, "
            + KEY_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL DEFAULT 0, "
            + KEY_NAME + " TEXT NOT NULL, "
            + KEY_DESCRIPTION + " TEXT NULL, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + "PRIMARY KEY (" + KEY_ID + "), "
            + "FOREIGN KEY (" + KEY_FISCAL_YEAR_FK_ID + ") "
            + "REFERENCES " + TABLE_tblFISCALYEAR + "(" + KEY_ID + ") "
            + "ON DELETE CASCADE ON UPDATE CASCADE);";

    public static final String CREATE_TABLE_tblFISCALYEAR = "CREATE TABLE " + TABLE_tblFISCALYEAR + "("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_SERVER_ID + " INTEGER DEFAULT NULL, "
            + KEY_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL DEFAULT 0, "
            + KEY_NAME + " TEXT NOT NULL, "
            + KEY_DESCRIPTION + " TEXT NULL, "
            + KEY_START_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_END_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP); ";

    /*#################################### START of GLOBAL MODULE ################################*/

    /*##################################### START of BRBAC MODULE ################################*/

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblADDRESS` (1)
    //-- -------------------------------------------------------------------------------------------
    public static final String CREATE_TABLE_tblADDRESS = "CREATE TABLE " + TABLE_tblADDRESS + "("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_SERVER_ID + " INTEGER DEFAULT NULL, "
            + KEY_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL DEFAULT 0, "
            + KEY_STREET + " TEXT, "
            + KEY_CITY + " TEXT, "
            + KEY_PROVINCE + " TEXT, "
            + KEY_POSTAL_CODE + " TEXT, "
            + KEY_COUNTRY + " TEXT, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP " + ");";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblORGANIZATION` (2)
    //-- -------------------------------------------------------------------------------------------
    public static final String CREATE_TABLE_tblORGANIZATION = "CREATE TABLE " +
            TABLE_tblORGANIZATION + "("
            + KEY_ID + " INTEGER NOT NULL, "
            + KEY_ADDRESS_FK_ID + " INTEGER, "
            + KEY_SERVER_ID + " INTEGER DEFAULT NULL, "
            + KEY_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL DEFAULT 0, "
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
            + "REFERENCES " + TABLE_tblADDRESS + "(" + KEY_ID + ") "
            + "ON DELETE CASCADE ON UPDATE CASCADE);";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblFUNDER` (3)
    //-- -------------------------------------------------------------------------------------------
    public static final String CREATE_TABLE_tblFUNDER = "CREATE TABLE " +
            TABLE_tblFUNDER + "("
            + KEY_ORGANIZATION_FK_ID + " INTEGER NOT NULL, "
            + KEY_SERVER_ID + " INTEGER DEFAULT NULL, "
            + KEY_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL DEFAULT 0, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + "PRIMARY KEY (" + KEY_ORGANIZATION_FK_ID + "), "
            + "FOREIGN KEY (" + KEY_ORGANIZATION_FK_ID + ") "
            + "REFERENCES " + TABLE_tblORGANIZATION + "(" + KEY_ID + ") "
            + "ON DELETE CASCADE ON UPDATE CASCADE);";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblBENEFICIARY` (4)
    //-- -------------------------------------------------------------------------------------------
    public static final String CREATE_TABLE_tblBENEFICIARY = "CREATE TABLE " +
            TABLE_tblBENEFICIARY + "("
            + KEY_ORGANIZATION_FK_ID + " INTEGER NOT NULL, "
            + KEY_SERVER_ID + " INTEGER DEFAULT NULL, "
            + KEY_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL DEFAULT 0, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + "PRIMARY KEY (" + KEY_ORGANIZATION_FK_ID + "), "
            + "FOREIGN KEY (" + KEY_ORGANIZATION_FK_ID + ") "
            + "REFERENCES " + TABLE_tblORGANIZATION + "(" + KEY_ID + ") "
            + "ON DELETE CASCADE ON UPDATE CASCADE);";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblIMPLEMENTINGAGENCY` (5)
    //-- -------------------------------------------------------------------------------------------
    public static final String CREATE_TABLE_tblIMPLEMENTINGAGENCY = "CREATE TABLE " +
            TABLE_tblIMPLEMENTINGAGENCY+ "("
            + KEY_ORGANIZATION_FK_ID + " INTEGER NOT NULL, "
            + KEY_SERVER_ID + " INTEGER DEFAULT NULL, "
            + KEY_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL DEFAULT 0, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + "PRIMARY KEY (" + KEY_ORGANIZATION_FK_ID + "), "
            + "FOREIGN KEY (" + KEY_ORGANIZATION_FK_ID + ") "
            + "REFERENCES " + TABLE_tblORGANIZATION + "(" + KEY_ID + ") "
            + "ON DELETE CASCADE ON UPDATE CASCADE);";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblUSER` (6)
    //-- -------------------------------------------------------------------------------------------
    public static final String CREATE_TABLE_tblUSER = "CREATE TABLE " + TABLE_tblUSER + "("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_ORGANIZATION_FK_ID + " INTEGER NOT NULL, "
            + KEY_SERVER_ID + " INTEGER DEFAULT NULL, "
            + KEY_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL DEFAULT 0, "
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
            + "REFERENCES " + TABLE_tblORGANIZATION + "(" + KEY_ID + ") "
            + "ON DELETE CASCADE ON UPDATE CASCADE);";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblSESSION` (7)
    //-- -------------------------------------------------------------------------------------------
    public static final String CREATE_TABLE_tblSESSION = "CREATE TABLE " + TABLE_tblSESSION + "("
            + KEY_ID + " INTEGER NOT NULL, "
            + KEY_USER_FK_ID + " INTEGER NOT NULL, "
            + KEY_SERVER_ID + " INTEGER DEFAULT NULL, "
            + KEY_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL DEFAULT 0, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + "PRIMARY KEY (" + KEY_ID + "), "
            + "FOREIGN KEY (" + KEY_USER_FK_ID + ") "
            + "REFERENCES " + TABLE_tblUSER + "(" + KEY_ID + ") "
            + "ON DELETE CASCADE ON UPDATE CASCADE);";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblVALUE` (8)
    //-- -------------------------------------------------------------------------------------------
    public static final String CREATE_TABLE_tblVALUE = "CREATE TABLE " + TABLE_tblVALUE + "("
            + KEY_ID + " INTEGER NOT NULL, "
            + KEY_ORGANIZATION_FK_ID + " INTEGER NOT NULL, "
            + KEY_SERVER_ID + " INTEGER DEFAULT NULL, "
            + KEY_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL DEFAULT 0, "
            + KEY_NAME + " TEXT, "
            + KEY_DESCRIPTION + " TEXT, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + "PRIMARY KEY (" + KEY_ID + "), "
            + "FOREIGN KEY (" + KEY_ORGANIZATION_FK_ID + ") "
            + "REFERENCES " + TABLE_tblORGANIZATION + "(" + KEY_ID + ") "
            + "ON DELETE CASCADE ON UPDATE CASCADE);";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblROLE` (9)
    //-- -------------------------------------------------------------------------------------------
    public static final String CREATE_TABLE_tblROLE = "CREATE TABLE " + TABLE_tblROLE + "("
            + KEY_ID + " INTEGER NOT NULL, "
            + KEY_ORGANIZATION_FK_ID + " INTEGER NOT NULL, "
            + KEY_PRIVILEGE_FK_ID + " INTEGER NOT NULL, "
            + KEY_SERVER_ID + " INTEGER DEFAULT NULL, "
            + KEY_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL DEFAULT 0, "
            + KEY_NAME + " TEXT, "
            + KEY_DESCRIPTION + " TEXT, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + "PRIMARY KEY (" + KEY_ID + ", " + KEY_ORGANIZATION_FK_ID + "),"
            + "FOREIGN KEY (" + KEY_ORGANIZATION_FK_ID + ") "
            + "REFERENCES " + TABLE_tblORGANIZATION + " (" + KEY_ID + ") "
            + "ON DELETE CASCADE ON UPDATE CASCADE, "
            + "FOREIGN KEY (" + KEY_PRIVILEGE_FK_ID + ") "
            + "REFERENCES " + TABLE_tblPRIVILEGE + "(" + KEY_ID + ") "
            + "ON DELETE CASCADE ON UPDATE CASCADE);";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblMENU` (10)
    //-- -------------------------------------------------------------------------------------------
    public static final String CREATE_TABLE_tblMENU = "CREATE TABLE " + TABLE_tblMENU + "("
            + KEY_ID + " INTEGER NOT NULL, "
            + KEY_PARENT_FK_ID + " INTEGER, "
            + KEY_SERVER_ID + " INTEGER DEFAULT NULL, "
            + KEY_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL DEFAULT 0, "
            + KEY_NAME + " TEXT, "
            + KEY_DESCRIPTION + " TEXT, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + "PRIMARY KEY (" + KEY_ID + "),"
            + "FOREIGN KEY (" + KEY_PARENT_FK_ID + ") "
            + "REFERENCES " + TABLE_tblMENU + "(" + KEY_ID + ") "
            + "ON DELETE CASCADE ON UPDATE CASCADE);";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblPRIVILEGE` (11)
    //-- -------------------------------------------------------------------------------------------
    public static final String CREATE_TABLE_tblPRIVILEGE = "CREATE TABLE " + TABLE_tblPRIVILEGE + "("
            + KEY_ID + " INTEGER NOT NULL, "
            + KEY_SERVER_ID + " INTEGER DEFAULT NULL, "
            + KEY_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL DEFAULT 0, "
            + KEY_NAME + " TEXT, "
            + KEY_DESCRIPTION + " TEXT, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + "PRIMARY KEY (" + KEY_ID + "));";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblENTITY` (12)
    //-- -------------------------------------------------------------------------------------------
    public static final String CREATE_TABLE_tblENTITY = "CREATE TABLE " + TABLE_tblENTITY + "("
            + KEY_ID + " INTEGER NOT NULL, "
            + KEY_ENTITY_TYPE_ID + " INTEGER NOT NULL, "
            + KEY_SERVER_ID + " INTEGER DEFAULT NULL, "
            + KEY_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL DEFAULT 0, "
            + KEY_NAME + " TEXT, "
            + KEY_DESCRIPTION + " TEXT, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + "PRIMARY KEY (" + KEY_ID + "," + KEY_ENTITY_TYPE_ID + "));";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblOPERATION` (13)
    //-- -------------------------------------------------------------------------------------------
    public static final String CREATE_TABLE_tblOPERATION = "CREATE TABLE " + TABLE_tblOPERATION + "("
            + KEY_ID + " INTEGER NOT NULL, "
            + KEY_SERVER_ID + " INTEGER DEFAULT NULL, "
            + KEY_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL DEFAULT 0, "
            + KEY_NAME + " TEXT, "
            + KEY_DESCRIPTION + " TEXT, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + "PRIMARY KEY (" + KEY_ID + "));";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblSTATUS` (14)
    //-- -------------------------------------------------------------------------------------------
    public static final String CREATE_TABLE_tblSTATUS = "CREATE TABLE " + TABLE_tblSTATUS + "("
            + KEY_ID + " INTEGER NOT NULL, "
            + KEY_SERVER_ID + " INTEGER DEFAULT NULL, "
            + KEY_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL DEFAULT 0, "
            + KEY_NAME + " TEXT, "
            + KEY_DESCRIPTION + " TEXT, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + "PRIMARY KEY (" + KEY_ID + "));";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblORG_ADDRESS` (15)
    //-- -------------------------------------------------------------------------------------------
    public static final String CREATE_TABLE_tblORG_ADDRESS = "CREATE TABLE " +
            TABLE_tblORG_ADDRESS + "("
            + KEY_ORGANIZATION_FK_ID + " INTEGER NOT NULL, "
            + KEY_ADDRESS_FK_ID + " INTEGER NOT NULL, "
            + KEY_SERVER_ID + " INTEGER DEFAULT NULL, "
            + KEY_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL DEFAULT 0, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + " PRIMARY KEY (" + KEY_ORGANIZATION_FK_ID + "," + KEY_ADDRESS_FK_ID + " ), "
            + " FOREIGN KEY (" + KEY_ORGANIZATION_FK_ID + ") "
            + " REFERENCES " + TABLE_tblORGANIZATION + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_ADDRESS_FK_ID + ") "
            + " REFERENCES " + TABLE_tblADDRESS + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE); ";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblUSER_ADDRESS` (16)
    //-- -------------------------------------------------------------------------------------------
    public static final String CREATE_TABLE_tblUSER_ADDRESS = "CREATE TABLE " +
            TABLE_tblUSER_ADDRESS + "("
            + KEY_USER_FK_ID + " INTEGER NOT NULL, "
            + KEY_ADDRESS_FK_ID + " INTEGER NOT NULL, "
            + KEY_SERVER_ID + " INTEGER DEFAULT NULL, "
            + KEY_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL DEFAULT 0, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + " PRIMARY KEY (" + KEY_USER_FK_ID + "," + KEY_ADDRESS_FK_ID + " ), "
            + " FOREIGN KEY (" + KEY_USER_FK_ID + ") "
            + " REFERENCES " + TABLE_tblUSER + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_ADDRESS_FK_ID + ") "
            + " REFERENCES " + TABLE_tblADDRESS + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE); ";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblUSER_ROLE` (17)
    //-- -------------------------------------------------------------------------------------------
    public static final String CREATE_TABLE_tblUSER_ROLE = "CREATE TABLE " + TABLE_tblUSER_ROLE + "("
            + KEY_USER_FK_ID + " INTEGER NOT NULL, "
            + KEY_ROLE_FK_ID + " INTEGER NOT NULL, "
            + KEY_ORGANIZATION_FK_ID + " INTEGER NOT NULL, "
            + KEY_SERVER_ID + " INTEGER DEFAULT NULL, "
            + KEY_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL DEFAULT 0, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + " PRIMARY KEY (" + KEY_USER_FK_ID + "," + KEY_ROLE_FK_ID + ","
            + KEY_ORGANIZATION_FK_ID + " ), "
            + " FOREIGN KEY (" + KEY_USER_FK_ID + ") "
            + " REFERENCES " + TABLE_tblUSER + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_ROLE_FK_ID + "," + KEY_ORGANIZATION_FK_ID + ") "
            + " REFERENCES " + TABLE_tblROLE + " (" + KEY_ID + ", " + KEY_ORGANIZATION_FK_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE); ";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblSESSION_ROLE` (18)
    //-- -------------------------------------------------------------------------------------------
    public static final String CREATE_TABLE_tblSESSION_ROLE = "CREATE TABLE " +
            TABLE_tblSESSION_ROLE + "("
            + KEY_SESSION_FK_ID + " INTEGER NOT NULL, "
            + KEY_ROLE_FK_ID + " INTEGER NOT NULL, "
            + KEY_SERVER_ID + " INTEGER DEFAULT NULL, "
            + KEY_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL DEFAULT 0, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + " PRIMARY KEY (" + KEY_SESSION_FK_ID + "," + KEY_ROLE_FK_ID + " ), "
            + " FOREIGN KEY (" + KEY_SESSION_FK_ID + ") "
            + " REFERENCES " + TABLE_tblSESSION + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_ROLE_FK_ID + ") "
            + " REFERENCES " + TABLE_tblROLE + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE); ";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblMENU_ROLE` (19)
    //-- -------------------------------------------------------------------------------------------
    public static final String CREATE_TABLE_tblMENU_ROLE = "CREATE TABLE " + TABLE_tblMENU_ROLE + "("
            + KEY_MENU_FK_ID + " INTEGER NOT NULL, "
            + KEY_ROLE_FK_ID + " INTEGER NOT NULL, "
            + KEY_ORGANIZATION_FK_ID + " INTEGER NOT NULL, "
            + KEY_SERVER_ID + " INTEGER DEFAULT NULL, "
            + KEY_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL DEFAULT 0, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + " PRIMARY KEY (" + KEY_MENU_FK_ID + "," + KEY_ROLE_FK_ID + ","
            + KEY_ORGANIZATION_FK_ID + " ), "
            + " FOREIGN KEY (" + KEY_MENU_FK_ID + ") "
            + " REFERENCES " + TABLE_tblMENU + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_ROLE_FK_ID + "," + KEY_ORGANIZATION_FK_ID + ") "
            + " REFERENCES " + TABLE_tblROLE + " (" + KEY_ID + "," + KEY_ORGANIZATION_FK_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE); ";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblPERMISSION` (20)
    //-- -------------------------------------------------------------------------------------------
    public static final String CREATE_TABLE_tblPERMISSION = "CREATE TABLE " + TABLE_tblPERMISSION + "("
            + KEY_PRIVILEGE_FK_ID + " INTEGER NOT NULL, "
            + KEY_ENTITY_FK_ID + " INTEGER NOT NULL, "
            + KEY_ENTITY_TYPE_FK_ID + " INTEGER NOT NULL, "
            + KEY_OPERATION_FK_ID + " INTEGER NOT NULL, "
            + KEY_SERVER_ID + " INTEGER DEFAULT NULL, "
            + KEY_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL DEFAULT 0, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + " PRIMARY KEY (" + KEY_PRIVILEGE_FK_ID + "," + KEY_ENTITY_FK_ID + ", " +
            KEY_ENTITY_TYPE_FK_ID + "," + KEY_OPERATION_FK_ID + "),"
            + " FOREIGN KEY (" + KEY_PRIVILEGE_FK_ID + ") "
            + " REFERENCES " + TABLE_tblPRIVILEGE + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_ENTITY_FK_ID + "," + KEY_ENTITY_TYPE_FK_ID + ") "
            + " REFERENCES " + TABLE_tblENTITY + " (" + KEY_ID + ", " + KEY_ENTITY_TYPE_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_OPERATION_FK_ID + ") "
            + " REFERENCES " + TABLE_tblOPERATION + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE); ";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblPRIVILEGE_STATUS` (21)
    //-- -------------------------------------------------------------------------------------------
    public static final String CREATE_TABLE_tblPRIV_STATUS = "CREATE TABLE " +
            TABLE_tblPRIV_STATUS + "("
            + KEY_PRIVILEGE_FK_ID + " INTEGER NOT NULL, "
            + KEY_STATUS_FK_ID + " INTEGER NOT NULL, "
            + KEY_SERVER_ID + " INTEGER DEFAULT NULL, "
            + KEY_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL DEFAULT 0, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + " PRIMARY KEY (" + KEY_PRIVILEGE_FK_ID + ", " + KEY_STATUS_FK_ID + " ), "
            + " FOREIGN KEY (" + KEY_PRIVILEGE_FK_ID + ") "
            + " REFERENCES " + TABLE_tblPRIVILEGE + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_STATUS_FK_ID + ") "
            + " REFERENCES " + TABLE_tblSTATUS + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE); ";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblSETTING` (22)
    //-- -------------------------------------------------------------------------------------------
    public static final String CREATE_TABLE_tblSETTING = "CREATE TABLE " + TABLE_tblSETTING + "("
            + KEY_ID + " INTEGER NOT NULL, "
            + KEY_SERVER_ID + " INTEGER DEFAULT NULL, "
            + KEY_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL DEFAULT 0, "
            + KEY_NAME + " TEXT, "
            + KEY_DESCRIPTION + " TEXT, "
            + KEY_SETTING_VALUE + " BOOLEAN, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + "PRIMARY KEY (" + KEY_ID + "));";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblNOTIFICATION` (23)
    //-- -------------------------------------------------------------------------------------------
    public static final String CREATE_TABLE_tblNOTIFICATION = "CREATE TABLE " +
            TABLE_tblNOTIFICATION + "("
            + KEY_ID + " INTEGER NOT NULL, "
            + KEY_ENTITY_FK_ID + " INTEGER NOT NULL, "
            + KEY_ENTITY_TYPE_FK_ID + " INTEGER NOT NULL, "
            + KEY_OPERATION_FK_ID + " INTEGER NOT NULL, "
            + KEY_SERVER_ID + " INTEGER DEFAULT NULL, "
            + KEY_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL DEFAULT 0, "
            + KEY_NAME + " TEXT, "
            + KEY_DESCRIPTION + " TEXT, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + " PRIMARY KEY (" + KEY_ID + "),"
            + " FOREIGN KEY (" + KEY_ENTITY_FK_ID + "," + KEY_ENTITY_TYPE_FK_ID + ") "
            + " REFERENCES " + TABLE_tblENTITY + "(" + KEY_ID + "," + KEY_ENTITY_TYPE_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_OPERATION_FK_ID + ") "
            + " REFERENCES " + TABLE_tblOPERATION + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE );";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblPUBLISHER` (24)
    //-- -------------------------------------------------------------------------------------------
    public static final String CREATE_TABLE_tblPUBLISHER = "CREATE TABLE " + TABLE_tblPUBLISHER + "("
            + KEY_PUBLISHER_FK_ID + " INTEGER NOT NULL, "
            + KEY_NOTIFICATION_FK_ID + " INTEGER NOT NULL, "
            + KEY_SERVER_ID + " INTEGER DEFAULT NULL, "
            + KEY_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL DEFAULT 0, "
            + KEY_NAME + " TEXT, "
            + KEY_DESCRIPTION + " TEXT, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + " PRIMARY KEY (" + KEY_PUBLISHER_FK_ID + "," + KEY_NOTIFICATION_FK_ID + "),"
            + " FOREIGN KEY (" + KEY_PUBLISHER_FK_ID + ") "
            + " REFERENCES " + TABLE_tblUSER + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_NOTIFICATION_FK_ID + ") "
            + " REFERENCES " + TABLE_tblNOTIFICATION + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE); ";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblSUBSCRIBER` (25)
    //-- -------------------------------------------------------------------------------------------
    public static final String CREATE_TABLE_tblSUBSCRIBER = "CREATE TABLE " + TABLE_tblSUBSCRIBER + "("
            + KEY_SUBSCRIBER_FK_ID + " INTEGER NOT NULL, "
            + KEY_NOTIFICATION_FK_ID + " INTEGER NOT NULL, "
            + KEY_SERVER_ID + " INTEGER DEFAULT NULL, "
            + KEY_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL DEFAULT 0, "
            + KEY_NAME + " TEXT, "
            + KEY_DESCRIPTION + " TEXT, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + " PRIMARY KEY (" + KEY_SUBSCRIBER_FK_ID + "," + KEY_NOTIFICATION_FK_ID + "),"
            + " FOREIGN KEY (" + KEY_SUBSCRIBER_FK_ID + ") "
            + " REFERENCES " + TABLE_tblUSER + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_NOTIFICATION_FK_ID + ") "
            + " REFERENCES " + TABLE_tblNOTIFICATION + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE); ";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblNOTIFY_SETTING` (26)
    //-- -------------------------------------------------------------------------------------------
    public static final String CREATE_TABLE_tblNOTIFY_SETTING = "CREATE TABLE " +
            TABLE_tblNOTIFY_SETTING + "("
            + KEY_NOTIFICATION_FK_ID + " INTEGER NOT NULL, "
            + KEY_SETTING_FK_ID + " INTEGER NOT NULL, "
            + KEY_SERVER_ID + " INTEGER DEFAULT NULL, "
            + KEY_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL DEFAULT 0, "
            + KEY_NAME + " TEXT, "
            + KEY_DESCRIPTION + " TEXT, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + " PRIMARY KEY (" + KEY_NOTIFICATION_FK_ID + "," + KEY_SETTING_FK_ID + "),"
            + " FOREIGN KEY (" + KEY_NOTIFICATION_FK_ID + ") "
            + " REFERENCES " + TABLE_tblNOTIFICATION + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_SETTING_FK_ID + ") "
            + " REFERENCES " + TABLE_tblSETTING + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE); ";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblACTIVITYLOG` (27)
    //-- -------------------------------------------------------------------------------------------
    public static final String CREATE_TABLE_tblACTIVITYLOG = "CREATE TABLE " +
            TABLE_tblACTIVITYLOG + "("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_SERVER_ID + " INTEGER NOT NULL, "
            + KEY_OWNER_ID + " INTEGER NOT NULL, "
            + KEY_ORG_ID + " INTEGER NOT NULL, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL, "
            + KEY_NAME + " TEXT NOT NULL, "
            + KEY_DESCRIPTION + " TEXT NULL, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP );";

    /*################################### END of BRBAC MODULE ####################################*/


    //################################## START LOGFRAME MODULE ###################################*/

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblLOGFRAME` (1)
    //-- -------------------------------------------------------------------------------------------
    public static final String CREATE_TABLE_tblLOGFRAME = "CREATE TABLE " + TABLE_tblLOGFRAME + "("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_ORGANIZATION_FK_ID + " INTEGER NOT NULL, "
            + KEY_SERVER_ID + " INTEGER DEFAULT NULL, "
            + KEY_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL DEFAULT 0, "
            + KEY_NAME + " TEXT NOT NULL, "
            + KEY_DESCRIPTION + " TEXT NULL, "
            + KEY_START_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_END_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + "FOREIGN KEY (" + KEY_ORGANIZATION_FK_ID + ") "
            + "REFERENCES " + TABLE_tblORGANIZATION + "(" + KEY_ID + ") "
            + "ON DELETE CASCADE ON UPDATE CASCADE);";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblLOGFRAMETREE` (2)
    //-- -------------------------------------------------------------------------------------------
    public static final String CREATE_TABLE_tblLOGFRAMETREE = "CREATE TABLE " +
            TABLE_tblLOGFRAMETREE + "("
            + KEY_PARENT_FK_ID + " INTEGER NOT NULL, "
            + KEY_CHILD_FK_ID + " INTEGER NOT NULL, "
            + KEY_SERVER_ID + " INTEGER DEFAULT NULL, "
            + KEY_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL DEFAULT 0, "
            + KEY_START_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_END_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + " PRIMARY KEY (" + KEY_PARENT_FK_ID + "," + KEY_CHILD_FK_ID + " ), "
            + " FOREIGN KEY (" + KEY_PARENT_FK_ID + ") "
            + " REFERENCES " + TABLE_tblLOGFRAME + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_CHILD_FK_ID + ")"
            + " REFERENCES " + TABLE_tblLOGFRAME + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE);";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblIMPACT` (3)
    //-- -------------------------------------------------------------------------------------------
    public static final String CREATE_TABLE_tblIMPACT = "CREATE TABLE " + TABLE_tblIMPACT + "("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_PARENT_FK_ID + " INTEGER DEFAULT NULL, "
            + KEY_LOGFRAME_FK_ID + " INTEGER NOT NULL, "
            + KEY_SERVER_ID + " INTEGER DEFAULT NULL, "
            + KEY_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL DEFAULT 0, "
            + KEY_NAME + " TEXT NOT NULL, "
            + KEY_DESCRIPTION + " TEXT NULL, "
            + KEY_START_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_END_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + " FOREIGN KEY (" + KEY_PARENT_FK_ID + ") "
            + " REFERENCES " + TABLE_tblIMPACT + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_LOGFRAME_FK_ID + ")"
            + " REFERENCES " + TABLE_tblLOGFRAME + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE);";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblOUTCOME` (4)
    //-- -------------------------------------------------------------------------------------------
    public static final String CREATE_TABLE_tblOUTCOME = "CREATE TABLE " + TABLE_tblOUTCOME + "("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_PARENT_FK_ID + " INTEGER DEFAULT NULL, "
            + KEY_IMPACT_FK_ID + " INTEGER NOT NULL, "
            + KEY_LOGFRAME_FK_ID + " INTEGER NOT NULL, "
            + KEY_SERVER_ID + " INTEGER DEFAULT NULL, "
            + KEY_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL DEFAULT 0, "
            + KEY_NAME + " TEXT NOT NULL, "
            + KEY_DESCRIPTION + " TEXT NULL, "
            + KEY_START_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_END_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + " FOREIGN KEY (" + KEY_PARENT_FK_ID + ") "
            + " REFERENCES " + TABLE_tblOUTCOME + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_IMPACT_FK_ID + ") "
            + " REFERENCES " + TABLE_tblIMPACT + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_LOGFRAME_FK_ID + ")"
            + " REFERENCES " + TABLE_tblLOGFRAME + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE);";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblOUTPUT` (5)
    //-- -------------------------------------------------------------------------------------------
    public static final String CREATE_TABLE_tblOUTPUT = "CREATE TABLE " + TABLE_tblOUTPUT + "("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_PARENT_FK_ID + " INTEGER DEFAULT NULL, "
            + KEY_OUTCOME_FK_ID + " INTEGER NOT NULL, "
            + KEY_LOGFRAME_FK_ID + " INTEGER NOT NULL, "
            + KEY_SERVER_ID + " INTEGER DEFAULT NULL, "
            + KEY_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL DEFAULT 0, "
            + KEY_NAME + " TEXT NOT NULL, "
            + KEY_DESCRIPTION + " TEXT NULL, "
            + KEY_START_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_END_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + " FOREIGN KEY (" + KEY_PARENT_FK_ID + ") "
            + " REFERENCES " + TABLE_tblOUTPUT + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_OUTCOME_FK_ID + ") "
            + " REFERENCES " + TABLE_tblOUTCOME + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_LOGFRAME_FK_ID + ")"
            + " REFERENCES " + TABLE_tblLOGFRAME + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE);";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblACTIVITYPLANNING` (6)
    //-- -------------------------------------------------------------------------------------------
    public static final String CREATE_TABLE_tblACTIVITYPLANNING = "CREATE TABLE " +
            TABLE_tblACTIVITYPLANNING + "("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_LOGFRAME_FK_ID + " INTEGER NOT NULL, "
            + KEY_SERVER_ID + " INTEGER DEFAULT NULL, "
            + KEY_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL DEFAULT 0, "
            + KEY_NAME + " TEXT NOT NULL, "
            + KEY_DESCRIPTION + " TEXT NULL, "
            + KEY_START_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_END_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + " FOREIGN KEY (" + KEY_LOGFRAME_FK_ID + ")"
            + " REFERENCES " + TABLE_tblLOGFRAME + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE);";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblACTIVITY` (7)
    //-- -------------------------------------------------------------------------------------------
    public static final String CREATE_TABLE_tblACTIVITY = "CREATE TABLE " + TABLE_tblACTIVITY + "("
            + KEY_ACTIVITYPLANNING_FK_ID + " INTEGER NOT NULL, "
            + KEY_PARENT_FK_ID + " INTEGER DEFAULT NULL, "
            + KEY_OUTPUT_FK_ID + " INTEGER NOT NULL, "
            + KEY_SERVER_ID + " INTEGER DEFAULT NULL, "
            + KEY_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL DEFAULT 0, "
            + KEY_START_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_END_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + " PRIMARY KEY (" + KEY_ACTIVITYPLANNING_FK_ID + " ), "
            + " FOREIGN KEY (" + KEY_ACTIVITYPLANNING_FK_ID + ") "
            + " REFERENCES " + TABLE_tblACTIVITYPLANNING + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_PARENT_FK_ID + ") "
            + " REFERENCES " + TABLE_tblACTIVITY + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_OUTPUT_FK_ID + ")"
            + " REFERENCES " + TABLE_tblOUTPUT + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE);";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblINPUT` (8)
    //-- -------------------------------------------------------------------------------------------
    public static final String CREATE_TABLE_tblINPUT = "CREATE TABLE " + TABLE_tblINPUT + "("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_ACTIVITYPLANNING_FK_ID + " INTEGER NOT NULL, "
            + KEY_LOGFRAME_FK_ID + " INTEGER NOT NULL, "
            + KEY_RESOURCE_FK_ID + " INTEGER NOT NULL, "
            + KEY_SERVER_ID + " INTEGER DEFAULT NULL, "
            + KEY_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL DEFAULT 0, "
            + KEY_START_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_END_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + " FOREIGN KEY (" + KEY_ACTIVITYPLANNING_FK_ID + ") "
            + " REFERENCES " + TABLE_tblACTIVITYPLANNING + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_LOGFRAME_FK_ID + ") "
            + " REFERENCES " + TABLE_tblLOGFRAME + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_RESOURCE_FK_ID + ")"
            + " REFERENCES " + TABLE_tblRESOURCE + " (" + KEY_ID + ")"
            + " ON DELETE CASCADE ON UPDATE CASCADE);";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblRESOURCETYPE` (9)
    //-- -------------------------------------------------------------------------------------------
    public static final String CREATE_TABLE_tblRESOURCETYPE = "CREATE TABLE " +
            TABLE_tblRESOURCETYPE + "("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_SERVER_ID + " INTEGER DEFAULT NULL, "
            + KEY_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL DEFAULT 0, "
            + KEY_NAME + " TEXT NOT NULL, "
            + KEY_DESCRIPTION + " TEXT NULL, "
            + KEY_START_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_END_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP );";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblRESOURCE` (10)
    //-- -------------------------------------------------------------------------------------------
    public static final String CREATE_TABLE_tblRESOURCE = "CREATE TABLE " + TABLE_tblRESOURCE + "("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_RESOURCE_TYPE_FK_ID + " INTEGER NOT NULL, "
            + KEY_SERVER_ID + " INTEGER DEFAULT NULL, "
            + KEY_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL DEFAULT 0, "
            + KEY_NAME + " TEXT NOT NULL, "
            + KEY_DESCRIPTION + " TEXT NULL, "
            + KEY_START_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_END_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + " FOREIGN KEY (" + KEY_RESOURCE_TYPE_FK_ID + ")"
            + " REFERENCES " + TABLE_tblRESOURCETYPE + " (" + KEY_ID + ")"
            + " ON DELETE CASCADE ON UPDATE CASCADE);";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblCRITERIA` (11)
    //-- -------------------------------------------------------------------------------------------
    public static final String CREATE_TABLE_tblEVALUATIONCRITERIA = "CREATE TABLE " +
            TABLE_tblEVALUATIONCRITERIA+ "("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_SERVER_ID + " INTEGER DEFAULT NULL, "
            + KEY_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL DEFAULT 0, "
            + KEY_NAME + " TEXT NOT NULL, "
            + KEY_DESCRIPTION + " TEXT NULL, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP); ";


    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblQUESTIONGROUPING` (12)
    //-- -------------------------------------------------------------------------------------------
    public static final String CREATE_TABLE_tblQUESTIONGROUPING = "CREATE TABLE " +
            TABLE_tblQUESTIONGROUPING + "("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_SERVER_ID + " INTEGER DEFAULT NULL, "
            + KEY_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL DEFAULT 0, "
            + KEY_NAME + " TEXT NOT NULL, "
            + KEY_DESCRIPTION + " TEXT NULL, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP); ";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblQUESTIONTYPE` (13)
    //-- -------------------------------------------------------------------------------------------
    public static final String CREATE_TABLE_tblQUESTIONTYPE = "CREATE TABLE " +
            TABLE_tblQUESTIONTYPE + "("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_SERVER_ID + " INTEGER DEFAULT NULL, "
            + KEY_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL DEFAULT 0, "
            + KEY_NAME + " TEXT NOT NULL, "
            + KEY_DESCRIPTION + " TEXT NULL, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP); ";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblPRIMITIVETYPE` (14)
    //-- -------------------------------------------------------------------------------------------
    public static final String CREATE_TABLE_tblPRIMITIVETYPE = "CREATE TABLE " +
            TABLE_tblPRIMITIVETYPE + "("
            + KEY_QUESTION_TYPE_FK_ID + " INTEGER NOT NULL, "
            + KEY_SERVER_ID + " INTEGER DEFAULT NULL, "
            + KEY_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL DEFAULT 0, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + "PRIMARY KEY (" + KEY_QUESTION_TYPE_FK_ID + "),"
            + "FOREIGN KEY (" + KEY_QUESTION_TYPE_FK_ID + ") "
            + "REFERENCES " + TABLE_tblQUESTIONTYPE + "(" + KEY_ID + ") "
            + "ON DELETE CASCADE ON UPDATE CASCADE);";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblARRAYTYPE` (15)
    //-- -------------------------------------------------------------------------------------------
    public static final String CREATE_TABLE_tblARRAYTYPE = "CREATE TABLE " +
            TABLE_tblARRAYTYPE + "("
            + KEY_QUESTION_TYPE_FK_ID + " INTEGER NOT NULL, "
            + KEY_SERVER_ID + " INTEGER DEFAULT NULL, "
            + KEY_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL DEFAULT 0, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + " PRIMARY KEY (" + KEY_QUESTION_TYPE_FK_ID + " ), "
            + " FOREIGN KEY (" + KEY_QUESTION_TYPE_FK_ID + ") "
            + " REFERENCES " + TABLE_tblQUESTIONTYPE + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE); ";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblMATRIXTYPE` (16)
    //-- -------------------------------------------------------------------------------------------
    public static final String CREATE_TABLE_tblMATRIXTYPE = "CREATE TABLE " +
            TABLE_tblMATRIXTYPE + "("
            + KEY_QUESTION_TYPE_FK_ID + " INTEGER NOT NULL, "
            + KEY_SERVER_ID + " INTEGER DEFAULT NULL, "
            + KEY_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL DEFAULT 0, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + " PRIMARY KEY (" + KEY_QUESTION_TYPE_FK_ID + " ), "
            + " FOREIGN KEY (" + KEY_QUESTION_TYPE_FK_ID + ") "
            + " REFERENCES " + TABLE_tblQUESTIONTYPE + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE); ";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblQUESTION` (17)
    //-- -------------------------------------------------------------------------------------------
    public static final String CREATE_TABLE_tblQUESTION = "CREATE TABLE " + TABLE_tblQUESTION + "("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_LOGFRAME_FK_ID + " INTEGER NOT NULL, "
            + KEY_QUESTION_GROUPING_FK_ID + " INTEGER DEFAULT NULL, "
            + KEY_QUESTION_TYPE_FK_ID + " INTEGER NOT NULL, "
            + KEY_SERVER_ID + " INTEGER DEFAULT NULL, "
            + KEY_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL DEFAULT 0, "
            + KEY_NAME + " TEXT NOT NULL, "
            + KEY_DESCRIPTION + " TEXT NULL, "
            + KEY_START_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_END_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + " FOREIGN KEY (" + KEY_QUESTION_GROUPING_FK_ID + ") "
            + " REFERENCES " + TABLE_tblQUESTIONGROUPING + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_QUESTION_TYPE_FK_ID + ") "
            + " REFERENCES " + TABLE_tblQUESTIONTYPE + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_LOGFRAME_FK_ID + ")"
            + " REFERENCES " + TABLE_tblLOGFRAME + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE);";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblRAID` (18)
    //-- -------------------------------------------------------------------------------------------
    public static final String CREATE_TABLE_tblRAID = "CREATE TABLE " + TABLE_tblRAID + "("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_LOGFRAME_FK_ID + " INTEGER NOT NULL, "
            + KEY_SERVER_ID + " INTEGER DEFAULT NULL, "
            + KEY_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL DEFAULT 0, "
            + KEY_NAME + " TEXT NOT NULL, "
            + KEY_DESCRIPTION + " TEXT NULL, "
            + KEY_START_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_END_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + " FOREIGN KEY (" + KEY_LOGFRAME_FK_ID + ")"
            + " REFERENCES " + TABLE_tblLOGFRAME + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE);";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblOUTCOME_IMPACT` (19)
    //-- -------------------------------------------------------------------------------------------
    public static final String CREATE_TABLE_tblOUTCOME_IMPACT = "CREATE TABLE " +
            TABLE_tblOUTCOME_IMPACT + "("
            + KEY_OUTCOME_FK_ID + " INTEGER NOT NULL, "
            + KEY_IMPACT_FK_ID + " INTEGER NOT NULL, "
            + KEY_PARENT_FK_ID + " INTEGER NOT NULL, "
            + KEY_CHILD_FK_ID + " INTEGER NOT NULL, "
            + KEY_SERVER_ID + " INTEGER DEFAULT NULL, "
            + KEY_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL DEFAULT 0, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + " PRIMARY KEY (" + KEY_OUTCOME_FK_ID + "," + KEY_IMPACT_FK_ID + " ), "
            + " FOREIGN KEY (" + KEY_OUTCOME_FK_ID + ") "
            + " REFERENCES " + TABLE_tblOUTCOME + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_IMPACT_FK_ID + ") "
            + " REFERENCES " + TABLE_tblIMPACT + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_PARENT_FK_ID + "," + KEY_CHILD_FK_ID + ")"
            + " REFERENCES " + TABLE_tblLOGFRAMETREE + " (" + KEY_PARENT_FK_ID + ","
            + KEY_CHILD_FK_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE);";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblOUTPUT_OUTCOME` (20)
    //-- -------------------------------------------------------------------------------------------
    public static final String CREATE_TABLE_tblOUTPUT_OUTCOME = "CREATE TABLE " +
            TABLE_tblOUTPUT_OUTCOME + "("
            + KEY_OUTPUT_FK_ID + " INTEGER NOT NULL, "
            + KEY_OUTCOME_FK_ID + " INTEGER NOT NULL, "
            + KEY_PARENT_FK_ID + " INTEGER NOT NULL, "
            + KEY_CHILD_FK_ID + " INTEGER NOT NULL, "
            + KEY_SERVER_ID + " INTEGER DEFAULT NULL, "
            + KEY_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL DEFAULT 0, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + " PRIMARY KEY (" + KEY_OUTPUT_FK_ID + "," + KEY_OUTCOME_FK_ID + " ), "
            + " FOREIGN KEY (" + KEY_OUTPUT_FK_ID + ") "
            + " REFERENCES " + TABLE_tblOUTPUT + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_OUTCOME_FK_ID + ") "
            + " REFERENCES " + TABLE_tblOUTCOME + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_PARENT_FK_ID + "," + KEY_CHILD_FK_ID + ")"
            + " REFERENCES " + TABLE_tblLOGFRAMETREE + " (" + KEY_PARENT_FK_ID + ","
            + KEY_CHILD_FK_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE);";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblACTIVITY_OUTPUT` (21)
    //-- -------------------------------------------------------------------------------------------
    public static final String CREATE_TABLE_tblACTIVITY_OUTPUT = "CREATE TABLE " +
            TABLE_tblACTIVITY_OUTPUT + "("
            + KEY_ACTIVITY_FK_ID + " INTEGER NOT NULL, "
            + KEY_OUTPUT_FK_ID + " INTEGER NOT NULL, "
            + KEY_PARENT_FK_ID + " INTEGER NOT NULL, "
            + KEY_CHILD_FK_ID + " INTEGER NOT NULL, "
            + KEY_SERVER_ID + " INTEGER DEFAULT NULL, "
            + KEY_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL DEFAULT 0, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + " PRIMARY KEY (" + KEY_ACTIVITY_FK_ID + "," + KEY_OUTPUT_FK_ID + " ), "
            + " FOREIGN KEY (" + KEY_ACTIVITY_FK_ID + ") "
            + " REFERENCES " + TABLE_tblACTIVITY + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_OUTPUT_FK_ID + ") "
            + " REFERENCES " + TABLE_tblOUTPUT + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_PARENT_FK_ID + "," + KEY_CHILD_FK_ID + ")"
            + " REFERENCES " + TABLE_tblLOGFRAMETREE + " (" + KEY_PARENT_FK_ID + ","
            + KEY_CHILD_FK_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE); ";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblPRECEDINGACTIVITY` (22)
    //-- -------------------------------------------------------------------------------------------
    public static final String CREATE_TABLE_tblPRECEDINGACTIVITY = "CREATE TABLE " +
            TABLE_tblPRECEDINGACTIVITY + "("
            + KEY_ACTIVITY_FK_ID + " INTEGER NOT NULL, "
            + KEY_PRECEDING_ACTIVITY_FK_ID + " INTEGER NOT NULL, "
            + KEY_SERVER_ID + " INTEGER DEFAULT NULL, "
            + KEY_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL DEFAULT 0, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + " PRIMARY KEY (" + KEY_ACTIVITY_FK_ID + "," + KEY_PRECEDING_ACTIVITY_FK_ID + "),"
            + " FOREIGN KEY (" + KEY_ACTIVITY_FK_ID + ") "
            + " REFERENCES " + TABLE_tblACTIVITY + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_PRECEDING_ACTIVITY_FK_ID + ")"
            + " REFERENCES " + TABLE_tblACTIVITY + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE);";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblACTIVITYASSIGNMENT` (23)
    //-- -------------------------------------------------------------------------------------------
    public static final String CREATE_TABLE_tblACTIVITYASSIGNMENT = "CREATE TABLE " +
            TABLE_tblACTIVITYASSIGNMENT + "("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_STAFF_FK_ID + " INTEGER NOT NULL, "
            + KEY_ACTIVITY_FK_ID + " INTEGER NOT NULL, "
            + KEY_SERVER_ID + " INTEGER DEFAULT NULL, "
            + KEY_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL DEFAULT 0, "
            + KEY_START_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_END_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + " FOREIGN KEY (" + KEY_ACTIVITY_FK_ID + ") "
            + " REFERENCES " + TABLE_tblACTIVITY + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_STAFF_FK_ID + ")"
            + " REFERENCES " + TABLE_tblHUMANSET + " (" + KEY_ID + ")"
            + " ON DELETE CASCADE ON UPDATE CASCADE);";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblIMPACT_QUESTION` (24)
    //-- -------------------------------------------------------------------------------------------
    public static final String CREATE_TABLE_tblIMPACT_QUESTION = "CREATE TABLE " +
            TABLE_tblIMPACT_QUESTION + "("
            + KEY_IMPACT_FK_ID + " INTEGER NOT NULL, "
            + KEY_QUESTION_FK_ID + " INTEGER NOT NULL, "
            + KEY_EVALUATION_CRITERIA_FK_ID + " INTEGER NOT NULL, "
            + KEY_SERVER_ID + " INTEGER DEFAULT NULL, "
            + KEY_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL DEFAULT 0, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + " PRIMARY KEY (" + KEY_IMPACT_FK_ID + "," + KEY_QUESTION_FK_ID + ","
            + KEY_EVALUATION_CRITERIA_FK_ID + " ), "
            + " FOREIGN KEY (" + KEY_IMPACT_FK_ID + ") "
            + " REFERENCES " + TABLE_tblIMPACT + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_QUESTION_FK_ID + ") "
            + " REFERENCES " + TABLE_tblQUESTION + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_EVALUATION_CRITERIA_FK_ID + ")"
            + " REFERENCES " + TABLE_tblEVALUATIONCRITERIA + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE);";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblOUTCOME_QUESTION` (25)
    //-- -------------------------------------------------------------------------------------------
    public static final String CREATE_TABLE_tblOUTCOME_QUESTION = "CREATE TABLE " +
            TABLE_tblOUTCOME_QUESTION + "("
            + KEY_OUTCOME_FK_ID + " INTEGER NOT NULL, "
            + KEY_QUESTION_FK_ID + " INTEGER NOT NULL, "
            + KEY_EVALUATION_CRITERIA_FK_ID + " INTEGER NOT NULL, "
            + KEY_SERVER_ID + " INTEGER DEFAULT NULL, "
            + KEY_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL DEFAULT 0, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + " PRIMARY KEY (" + KEY_OUTCOME_FK_ID + "," + KEY_QUESTION_FK_ID + ","
            + KEY_EVALUATION_CRITERIA_FK_ID + " ), "
            + " FOREIGN KEY (" + KEY_OUTCOME_FK_ID + ") "
            + " REFERENCES " + TABLE_tblOUTCOME + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_QUESTION_FK_ID + ") "
            + " REFERENCES " + TABLE_tblQUESTION + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_EVALUATION_CRITERIA_FK_ID + ")"
            + " REFERENCES " + TABLE_tblEVALUATIONCRITERIA + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE);";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblOUTPUT_QUESTION` (26)
    //-- -------------------------------------------------------------------------------------------
    public static final String CREATE_TABLE_tblOUTPUT_QUESTION = "CREATE TABLE " +
            TABLE_tblOUTPUT_QUESTION + "("
            + KEY_OUTPUT_FK_ID + " INTEGER NOT NULL, "
            + KEY_QUESTION_FK_ID + " INTEGER NOT NULL, "
            + KEY_EVALUATION_CRITERIA_FK_ID + " INTEGER NOT NULL, "
            + KEY_SERVER_ID + " INTEGER DEFAULT NULL, "
            + KEY_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL DEFAULT 0, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + " PRIMARY KEY (" + KEY_OUTPUT_FK_ID + "," + KEY_QUESTION_FK_ID + ","
            + KEY_EVALUATION_CRITERIA_FK_ID + " ), "
            + " FOREIGN KEY (" + KEY_OUTPUT_FK_ID + ") "
            + " REFERENCES " + TABLE_tblOUTPUT + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_QUESTION_FK_ID + ") "
            + " REFERENCES " + TABLE_tblQUESTION + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_EVALUATION_CRITERIA_FK_ID + ")"
            + " REFERENCES " + TABLE_tblEVALUATIONCRITERIA + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE);";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblACTIVITY_QUESTION` (27)
    //-- -------------------------------------------------------------------------------------------
    public static final String CREATE_TABLE_tblACTIVITY_QUESTION = "CREATE TABLE " +
            TABLE_tblACTIVITY_QUESTION + "("
            + KEY_ACTIVITY_FK_ID + " INTEGER NOT NULL, "
            + KEY_QUESTION_FK_ID + " INTEGER NOT NULL, "
            + KEY_EVALUATION_CRITERIA_FK_ID + " INTEGER NOT NULL, "
            + KEY_SERVER_ID + " INTEGER DEFAULT NULL, "
            + KEY_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL DEFAULT 0, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + " PRIMARY KEY (" + KEY_ACTIVITY_FK_ID + "," + KEY_QUESTION_FK_ID + ","
            + KEY_EVALUATION_CRITERIA_FK_ID + " ), "
            + " FOREIGN KEY (" + KEY_ACTIVITY_FK_ID + ") "
            + " REFERENCES " + TABLE_tblACTIVITY + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_QUESTION_FK_ID + ") "
            + " REFERENCES " + TABLE_tblQUESTION + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_EVALUATION_CRITERIA_FK_ID + ")"
            + " REFERENCES " + TABLE_tblEVALUATIONCRITERIA + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE);";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblINPUT_QUESTION` (28)
    //-- -------------------------------------------------------------------------------------------
    public static final String CREATE_TABLE_tblINPUT_QUESTION = "CREATE TABLE " +
            TABLE_tblINPUT_QUESTION + "("
            + KEY_INPUT_FK_ID + " INTEGER NOT NULL, "
            + KEY_QUESTION_FK_ID + " INTEGER NOT NULL, "
            + KEY_EVALUATION_CRITERIA_FK_ID + " INTEGER NOT NULL, "
            + KEY_SERVER_ID + " INTEGER DEFAULT NULL, "
            + KEY_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL DEFAULT 0, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + " PRIMARY KEY (" + KEY_INPUT_FK_ID + "," + KEY_QUESTION_FK_ID + ","
            + KEY_EVALUATION_CRITERIA_FK_ID + " ), "
            + " FOREIGN KEY (" + KEY_INPUT_FK_ID + ") "
            + " REFERENCES " + TABLE_tblINPUT + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_QUESTION_FK_ID + ") "
            + " REFERENCES " + TABLE_tblQUESTION + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_EVALUATION_CRITERIA_FK_ID + ")"
            + " REFERENCES " + TABLE_tblEVALUATIONCRITERIA + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE);";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblIMPACT_RAID` (29)
    //-- -------------------------------------------------------------------------------------------
    public static final String CREATE_TABLE_tblIMPACT_RAID = "CREATE TABLE " +
            TABLE_tblIMPACT_RAID + "("
            + KEY_IMPACT_FK_ID + " INTEGER NOT NULL, "
            + KEY_RAID_FK_ID + " INTEGER NOT NULL, "
            + KEY_SERVER_ID + " INTEGER DEFAULT NULL, "
            + KEY_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL DEFAULT 0, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + " PRIMARY KEY (" + KEY_IMPACT_FK_ID + "," + KEY_RAID_FK_ID + " ), "
            + " FOREIGN KEY (" + KEY_IMPACT_FK_ID + ") "
            + " REFERENCES " + TABLE_tblIMPACT + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_RAID_FK_ID + ") "
            + " REFERENCES " + TABLE_tblRAID + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE); ";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblOUTCOME_RAID` (30)
    //-- -------------------------------------------------------------------------------------------
    public static final String CREATE_TABLE_tblOUTCOME_RAID = "CREATE TABLE " +
            TABLE_tblOUTCOME_RAID + "("
            + KEY_OUTCOME_FK_ID + " INTEGER NOT NULL, "
            + KEY_RAID_FK_ID + " INTEGER NOT NULL, "
            + KEY_SERVER_ID + " INTEGER DEFAULT NULL, "
            + KEY_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL DEFAULT 0, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + " PRIMARY KEY (" + KEY_OUTCOME_FK_ID + "," + KEY_RAID_FK_ID + " ), "
            + " FOREIGN KEY (" + KEY_OUTCOME_FK_ID + ") "
            + " REFERENCES " + TABLE_tblOUTCOME + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_RAID_FK_ID + ") "
            + " REFERENCES " + TABLE_tblRAID + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE); ";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblOUTPUT_RAID` (31)
    //-- -------------------------------------------------------------------------------------------
    public static final String CREATE_TABLE_tblOUTPUT_RAID = "CREATE TABLE " +
            TABLE_tblOUTPUT_RAID + "("
            + KEY_OUTPUT_FK_ID + " INTEGER NOT NULL, "
            + KEY_RAID_FK_ID + " INTEGER NOT NULL, "
            + KEY_SERVER_ID + " INTEGER DEFAULT NULL, "
            + KEY_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL DEFAULT 0, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + " PRIMARY KEY (" + KEY_OUTPUT_FK_ID + "," + KEY_RAID_FK_ID + " ), "
            + " FOREIGN KEY (" + KEY_OUTPUT_FK_ID + ") "
            + " REFERENCES " + TABLE_tblOUTPUT + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_RAID_FK_ID + ") "
            + " REFERENCES " + TABLE_tblRAID + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE); ";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblACTIVITY_RAID` (32)
    //-- -------------------------------------------------------------------------------------------
    public static final String CREATE_TABLE_tblACTIVITY_RAID = "CREATE TABLE " +
            TABLE_tblACTIVITY_RAID + "("
            + KEY_ACTIVITY_FK_ID + " INTEGER NOT NULL, "
            + KEY_RAID_FK_ID + " INTEGER NOT NULL, "
            + KEY_SERVER_ID + " INTEGER DEFAULT NULL, "
            + KEY_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL DEFAULT 0, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + " PRIMARY KEY (" + KEY_ACTIVITY_FK_ID + "," + KEY_RAID_FK_ID + " ), "
            + " FOREIGN KEY (" + KEY_ACTIVITY_FK_ID + ") "
            + " REFERENCES " + TABLE_tblACTIVITY + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_RAID_FK_ID + ") "
            + " REFERENCES " + TABLE_tblRAID + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE); ";


    /*##################################### END LOGFRAME MODULE ##################################*/


    /*###################################### START AWPB MODULE ###################################*/

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblHUMAN` (1)
    //-- -------------------------------------------------------------------------------------------
    public static final String CREATE_TABLE_tblHUMAN = "CREATE TABLE " + TABLE_tblHUMAN + "("
            + KEY_INPUT_FK_ID + " INTEGER NOT NULL, "
            + KEY_SERVER_ID + " INTEGER DEFAULT NULL, "
            + KEY_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL DEFAULT 0, "
            + KEY_QUANTITY + " INTEGER, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + " PRIMARY KEY (" + KEY_INPUT_FK_ID + " ), "
            + " FOREIGN KEY (" + KEY_INPUT_FK_ID + ") "
            + " REFERENCES " + TABLE_tblINPUT + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE); ";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblMATERIAL` (2)
    //-- -------------------------------------------------------------------------------------------
    public static final String CREATE_TABLE_tblMATERIAL = "CREATE TABLE " + TABLE_tblMATERIAL + "("
            + KEY_INPUT_FK_ID + " INTEGER NOT NULL, "
            + KEY_SERVER_ID + " INTEGER DEFAULT NULL, "
            + KEY_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL DEFAULT 0, "
            + KEY_QUANTITY + " INTEGER, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + " PRIMARY KEY (" + KEY_INPUT_FK_ID + " ), "
            + " FOREIGN KEY (" + KEY_INPUT_FK_ID + ") "
            + " REFERENCES " + TABLE_tblINPUT + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE); ";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblINCOME` (3)
    //-- -------------------------------------------------------------------------------------------
    public static final String CREATE_TABLE_tblINCOME = "CREATE TABLE " + TABLE_tblINCOME + "("
            + KEY_INPUT_FK_ID + " INTEGER NOT NULL, "
            + KEY_SERVER_ID + " INTEGER DEFAULT NULL, "
            + KEY_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL DEFAULT 0, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + " PRIMARY KEY (" + KEY_INPUT_FK_ID + " ), "
            + " FOREIGN KEY (" + KEY_INPUT_FK_ID + ") "
            + " REFERENCES " + TABLE_tblINPUT + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE); ";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblEXPENSE` (4)
    //-- -------------------------------------------------------------------------------------------
    public static final String CREATE_TABLE_tblEXPENSE = "CREATE TABLE " + TABLE_tblEXPENSE + "("
            + KEY_INPUT_FK_ID + " INTEGER NOT NULL, "
            + KEY_SERVER_ID + " INTEGER DEFAULT NULL, "
            + KEY_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL DEFAULT 0, "
            + KEY_EXPENSE + " DOUBLE, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + " PRIMARY KEY (" + KEY_INPUT_FK_ID + " ), "
            + " FOREIGN KEY (" + KEY_INPUT_FK_ID + ") "
            + " REFERENCES " + TABLE_tblINPUT + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE); ";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblHUMANSET` (5)
    //-- -------------------------------------------------------------------------------------------
    public static final String CREATE_TABLE_tblHUMANSET = "CREATE TABLE " +
            TABLE_tblHUMANSET + "("
            + KEY_ID + " INTEGER NOT NULL, "
            + KEY_INPUT_FK_ID + " INTEGER NOT NULL, "
            + KEY_USER_FK_ID + " INTEGER NOT NULL, "
            + KEY_SERVER_ID + " INTEGER DEFAULT NULL, "
            + KEY_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL DEFAULT 0, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + " PRIMARY KEY (" + KEY_ID + " ), "
            + " FOREIGN KEY (" + KEY_INPUT_FK_ID + ") "
            + " REFERENCES " + TABLE_tblHUMAN + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_USER_FK_ID + ") "
            + " REFERENCES " + TABLE_tblUSER + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE); ";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblFUND` (6)
    //-- -------------------------------------------------------------------------------------------
    public static final String CREATE_TABLE_tblFUND = "CREATE TABLE " +
            TABLE_tblFUND + "("
            + KEY_ID + " INTEGER NOT NULL, "
            + KEY_INPUT_FK_ID + " INTEGER NOT NULL, "
            + KEY_FUNDER_FK_ID + " INTEGER NOT NULL, "
            + KEY_BENEFICIARY_FK_ID + " INTEGER NOT NULL, "
            + KEY_SERVER_ID + " INTEGER DEFAULT NULL, "
            + KEY_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL DEFAULT 0, "
            + KEY_FUND + " DOUBLE, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + " PRIMARY KEY (" + KEY_ID + " ), "
            + " FOREIGN KEY (" + KEY_INPUT_FK_ID + ") "
            + " REFERENCES " + TABLE_tblINCOME + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_FUNDER_FK_ID + ") "
            + " REFERENCES " + TABLE_tblFUNDER + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_BENEFICIARY_FK_ID + ") "
            + " REFERENCES " + TABLE_tblBENEFICIARY + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE); ";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblMILESTONE` (7)
    //-- -------------------------------------------------------------------------------------------
    public static final String CREATE_TABLE_tblMILESTONE = "CREATE TABLE " + TABLE_tblMILESTONE + "("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_SERVER_ID + " INTEGER DEFAULT NULL, "
            + KEY_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL DEFAULT 0, "
            + KEY_NAME + " TEXT NOT NULL, "
            + KEY_DESCRIPTION + " TEXT, "
            + KEY_TARGET_VALUE + " INTEGER, "
            + KEY_START_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_END_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP " + ");";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblTASK` (8)
    //-- -------------------------------------------------------------------------------------------
    public static final String CREATE_TABLE_tblTASK = "CREATE TABLE " + TABLE_tblTASK + "("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_SERVER_ID + " INTEGER DEFAULT NULL, "
            + KEY_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL DEFAULT 0, "
            + KEY_NAME + " TEXT NOT NULL, "
            + KEY_DESCRIPTION + " TEXT NULL, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP " + ");";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblACTIVITYTASK` (9)
    //-- -------------------------------------------------------------------------------------------
    public static final String CREATE_TABLE_tblACTIVITYTASK = "CREATE TABLE " +
            TABLE_tblACTIVITYTASK + "("
            + KEY_TASK_FK_ID + " INTEGER NOT NULL, "
            + KEY_ACTIVITY_FK_ID + " INTEGER NOT NULL, "
            + KEY_SERVER_ID + " INTEGER DEFAULT NULL, "
            + KEY_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL DEFAULT 0, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + " PRIMARY KEY (" + KEY_TASK_FK_ID + " ), "
            + " FOREIGN KEY (" + KEY_TASK_FK_ID + ") "
            + " REFERENCES " + TABLE_tblTASK + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_ACTIVITY_FK_ID + ") "
            + " REFERENCES " + TABLE_tblACTIVITY + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE); ";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblPRECEDINGTASK` (10)
    //-- -------------------------------------------------------------------------------------------
    public static final String CREATE_TABLE_tblPRECEDINGTASK = "CREATE TABLE " +
            TABLE_tblPRECEDINGTASK + "("
            + KEY_TASK_FK_ID+ " INTEGER NOT NULL, "
            + KEY_PRECEDING_TASK_FK_ID + " INTEGER NOT NULL, "
            + KEY_SERVER_ID + " INTEGER DEFAULT NULL, "
            + KEY_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL DEFAULT 0, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + " PRIMARY KEY (" + KEY_TASK_FK_ID + "," + KEY_PRECEDING_TASK_FK_ID + " ), "
            + " FOREIGN KEY (" + KEY_TASK_FK_ID + ") "
            + " REFERENCES " + TABLE_tblPRECEDINGTASK + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_PRECEDING_TASK_FK_ID + ") "
            + " REFERENCES " + TABLE_tblPRECEDINGTASK + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE); ";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblTASK_MILESTONE` (11)
    //-- -------------------------------------------------------------------------------------------
    public static final String CREATE_TABLE_tblTASK_MILESTONE = "CREATE TABLE " +
            TABLE_tblTASK_MILESTONE + "("
            + KEY_TASK_FK_ID + " INTEGER NOT NULL, "
            + KEY_MILESTONE_FK_ID + " INTEGER NOT NULL, "
            + KEY_SERVER_ID + " INTEGER DEFAULT NULL, "
            + KEY_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL DEFAULT 0, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + " PRIMARY KEY (" + KEY_TASK_FK_ID + "," + KEY_MILESTONE_FK_ID + " ), "
            + " FOREIGN KEY (" + KEY_TASK_FK_ID + ") "
            + " REFERENCES " + TABLE_tblTASK + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_MILESTONE_FK_ID + ") "
            + " REFERENCES " + TABLE_tblMILESTONE + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE); ";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblTASKASSIGNMENT` (12)
    //-- -------------------------------------------------------------------------------------------
    public static final String CREATE_TABLE_tblTASKASSIGNMENT = "CREATE TABLE " +
            TABLE_tblTASKASSIGNMENT + "("
            + KEY_ID + " INTEGER NOT NULL, "
            + KEY_STAFF_FK_ID + " INTEGER NOT NULL, "
            + KEY_TASK_FK_ID + " INTEGER NOT NULL, "
            + KEY_SERVER_ID + " INTEGER DEFAULT NULL, "
            + KEY_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_HOURS + " INTEGER, "
            + KEY_RATE + " INTEGER, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL DEFAULT 0, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + " PRIMARY KEY (" + KEY_ID + " ), "
            + " FOREIGN KEY (" + KEY_TASK_FK_ID + ") "
            + " REFERENCES " + TABLE_tblTASK + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_STAFF_FK_ID + ") "
            + " REFERENCES " + TABLE_tblHUMANSET + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE); ";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblUSERCOMMENT` (13)
    //-- -------------------------------------------------------------------------------------------
    public static final String CREATE_TABLE_tblUSERCOMMENT = "CREATE TABLE " +
            TABLE_tblUSERCOMMENT + "("
            + KEY_ID + " INTEGER NOT NULL, "
            + KEY_STAFF_FK_ID + " INTEGER NOT NULL, "
            + KEY_TASK_FK_ID+ " INTEGER NOT NULL, "
            + KEY_SERVER_ID + " INTEGER DEFAULT NULL, "
            + KEY_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL DEFAULT 0, "
            + KEY_USER_COMMENT + " TEXT, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + " PRIMARY KEY (" + KEY_ID + " ), "
            + " FOREIGN KEY (" + KEY_TASK_FK_ID + ") "
            + " REFERENCES " + TABLE_tblTASK + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_STAFF_FK_ID + ") "
            + " REFERENCES " + TABLE_tblHUMANSET + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE); ";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblTIMESHEET` (14)
    //-- -------------------------------------------------------------------------------------------
    public static final String CREATE_TABLE_tblTIMESHEET = "CREATE TABLE " +
            TABLE_tblTIMESHEET + "("
            + KEY_ID + " INTEGER NOT NULL, "
            + KEY_STAFF_FK_ID + " INTEGER NOT NULL, "
            + KEY_TASK_FK_ID + " INTEGER NOT NULL, "
            + KEY_SERVER_ID + " INTEGER DEFAULT NULL, "
            + KEY_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL DEFAULT 0, "
            + KEY_START_TIME + " DATE, "
            + KEY_END_TIME + " DATE, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + " PRIMARY KEY (" + KEY_ID + " ), "
            + " FOREIGN KEY (" + KEY_TASK_FK_ID + ") "
            + " REFERENCES " + TABLE_tblTASK + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_STAFF_FK_ID + ") "
            + " REFERENCES " + TABLE_tblHUMANSET + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE); ";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblINVOICE` (15)
    //-- -------------------------------------------------------------------------------------------
    public static final String CREATE_TABLE_tblINVOICE = "CREATE TABLE " + TABLE_tblINVOICE + "("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_SERVER_ID + " INTEGER DEFAULT NULL, "
            + KEY_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL DEFAULT 0, "
            + KEY_NAME + " TEXT NOT NULL, "
            + KEY_DESCRIPTION + " TEXT NULL, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP " + ");";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblINVOICE_TIMESHEET` (16)
    //-- -------------------------------------------------------------------------------------------
    public static final String CREATE_TABLE_tblINVOICE_TIMESHEET = "CREATE TABLE " +
            TABLE_tblINVOICE_TIMESHEET + "("
            + KEY_INVOICE_FK_ID + " INTEGER NOT NULL, "
            + KEY_TIMESHEET_FK_ID + " INTEGER NOT NULL, "
            + KEY_SERVER_ID + " INTEGER DEFAULT NULL, "
            + KEY_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL DEFAULT 0, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + " PRIMARY KEY (" + KEY_INVOICE_FK_ID + "," + KEY_TIMESHEET_FK_ID + " ), "
            + " FOREIGN KEY (" + KEY_INVOICE_FK_ID + ") "
            + " REFERENCES " + TABLE_tblINVOICE + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_TIMESHEET_FK_ID + ") "
            + " REFERENCES " + TABLE_tblTIMESHEET + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE); ";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblEXTENSION` (17)
    //-- -------------------------------------------------------------------------------------------
    public static final String CREATE_TABLE_tblEXTENSION = "CREATE TABLE " +
            TABLE_tblEXTENSION + "("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_SERVER_ID + " INTEGER DEFAULT NULL, "
            + KEY_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL DEFAULT 0, "
            + KEY_NAME + " TEXT NOT NULL, "
            + KEY_DESCRIPTION + " TEXT NULL, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP " + ");";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblEXT_DOC_TYPE` (18)
    //-- -------------------------------------------------------------------------------------------
    public static final String CREATE_TABLE_tblEXT_DOC_TYPE = "CREATE TABLE " +
            TABLE_tblEXT_DOC_TYPE + "("
            + KEY_ID + " INTEGER NOT NULL, "
            + KEY_EXTENSION_FK_ID + " INTEGER NOT NULL, "
            + KEY_SERVER_ID + " INTEGER DEFAULT NULL, "
            + KEY_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL DEFAULT 0, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + " PRIMARY KEY (" + KEY_ID + " ), "
            + " FOREIGN KEY (" + KEY_EXTENSION_FK_ID + ") "
            + " REFERENCES " + TABLE_tblEXTENSION + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE); ";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblDOCUMENT` (19)
    //-- -------------------------------------------------------------------------------------------
    public static final String CREATE_TABLE_tblDOCUMENT = "CREATE TABLE " +
            TABLE_tblDOCUMENT + "("
            + KEY_ID + " INTEGER NOT NULL, "
            + KEY_EXT_DOC_TYPE_FK_ID + " INTEGER NOT NULL, "
            + KEY_SERVER_ID + " INTEGER DEFAULT NULL, "
            + KEY_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL DEFAULT 0, "
            + KEY_NAME + " TEXT NOT NULL, "
            + KEY_DESCRIPTION + " TEXT NULL, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + " PRIMARY KEY (" + KEY_ID + " ), "
            + " FOREIGN KEY (" + KEY_EXT_DOC_TYPE_FK_ID + ") "
            + " REFERENCES " + TABLE_tblEXT_DOC_TYPE + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE); ";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblTRANSACTION` (20)
    //-- -------------------------------------------------------------------------------------------
    public static final String CREATE_TABLE_tblTRANSACTION = "CREATE TABLE " +
            TABLE_tblTRANSACTION + "("
            + KEY_ID + " INTEGER NOT NULL, "
            + KEY_DOCUMENT_FK_ID + " INTEGER NOT NULL, "
            + KEY_INVOICE_FK_ID + " INTEGER, "
            + KEY_PERIOD_FK_ID + " INTEGER NOT NULL, "
            + KEY_SERVER_ID + " INTEGER DEFAULT NULL, "
            + KEY_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL DEFAULT 0, "
            + KEY_NAME + " TEXT NOT NULL, "
            + KEY_DESCRIPTION + " TEXT NULL, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + " PRIMARY KEY (" + KEY_ID + " ), "
            + " FOREIGN KEY (" + KEY_DOCUMENT_FK_ID + ") "
            + " REFERENCES " + TABLE_tblTRANSACTION  + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_INVOICE_FK_ID + ") "
            + " REFERENCES " + TABLE_tblINVOICE + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_PERIOD_FK_ID + ") "
            + " REFERENCES " + TABLE_tblPERIOD + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE); ";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblINTERNAL` (21)
    //-- -------------------------------------------------------------------------------------------
    public static final String CREATE_TABLE_tblINTERNAL = "CREATE TABLE " +
            TABLE_tblINTERNAL + "("
            + KEY_TRANSACTION_FK_ID + " INTEGER NOT NULL, "
            + KEY_INVOICE_FK_ID + " INTEGER NOT NULL, "
            + KEY_SERVER_ID + " INTEGER DEFAULT NULL, "
            + KEY_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL DEFAULT 0, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + " PRIMARY KEY (" + KEY_TRANSACTION_FK_ID + " ), "
            + " FOREIGN KEY (" + KEY_TRANSACTION_FK_ID + ") "
            + " REFERENCES " + TABLE_tblTRANSACTION + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_INVOICE_FK_ID + ") "
            + " REFERENCES " + TABLE_tblINVOICE + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE); ";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblEXTERNAL` (22)
    //-- -------------------------------------------------------------------------------------------
    public static final String CREATE_TABLE_tblEXTERNAL = "CREATE TABLE " +
            TABLE_tblEXTERNAL + "("
            + KEY_TRANSACTION_FK_ID + " INTEGER NOT NULL, "
            + KEY_SERVER_ID + " INTEGER DEFAULT NULL, "
            + KEY_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL DEFAULT 0, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + " PRIMARY KEY (" + KEY_TRANSACTION_FK_ID + " ), "
            + " FOREIGN KEY (" + KEY_TRANSACTION_FK_ID + ") "
            + " REFERENCES " + TABLE_tblTRANSACTION + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE); ";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblJOURNAL` (23)
    //-- -------------------------------------------------------------------------------------------
    public static final String CREATE_TABLE_tblJOURNAL = "CREATE TABLE " +
            TABLE_tblJOURNAL + "("
            + KEY_ID + " INTEGER NOT NULL, "
            + KEY_INPUT_FK_ID+ " INTEGER NOT NULL, "
            + KEY_TRANSACTION_FK_ID + " INTEGER NOT NULL, "
            + KEY_SERVER_ID + " INTEGER DEFAULT NULL, "
            + KEY_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL DEFAULT 0, "
            + KEY_ENTRY_TYPE + " INTEGER, "
            + KEY_AMOUNT + " DOUBLE, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + " PRIMARY KEY (" + KEY_ID + " ), "
            + " FOREIGN KEY (" + KEY_INPUT_FK_ID + ") "
            + " REFERENCES " + TABLE_tblINPUT + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_TRANSACTION_FK_ID + ") "
            + " REFERENCES " + TABLE_tblTRANSACTION + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE); ";

    /*####################################### END AWPB MODULE ####################################*/


    /*###################################### START RAID MODULE ###################################*/

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblRISKREGISTER` (1)
    //-- -------------------------------------------------------------------------------------------
    public static final String CREATE_TABLE_tblRISKREGISTER = "CREATE TABLE " + TABLE_tblRISKREGISTER + "("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_SERVER_ID + " INTEGER DEFAULT NULL, "
            + KEY_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL DEFAULT 0, "
            + KEY_NAME + " TEXT NOT NULL, "
            + KEY_DESCRIPTION + " TEXT NULL, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP " + ");";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblRISKLIKELIHOOD` (2)
    //-- -------------------------------------------------------------------------------------------
    public static final String CREATE_TABLE_tblRISKLIKELIHOOD = "CREATE TABLE " +
            TABLE_tblRISKLIKELIHOOD + "("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_SERVER_ID + " INTEGER DEFAULT NULL, "
            + KEY_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL DEFAULT 0, "
            + KEY_NAME + " TEXT NOT NULL, "
            + KEY_DESCRIPTION + " TEXT NULL, "
            + KEY_RISKLIKELIHOOD_VALUE + " INTEGER, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP " + ");";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblLIKELIHOODSET` (3)
    //-- -------------------------------------------------------------------------------------------
    public static final String CREATE_TABLE_tblRISKLIKELIHOODSET = "CREATE TABLE " +
            TABLE_tblRISKLIKELIHOODSET + "("
            + KEY_ID+ " INTEGER NOT NULL, "
            + KEY_RISK_REGISTER_FK_ID + " INTEGER NOT NULL, "
            + KEY_RISK_LIKELIHOOD_FK_ID + " INTEGER NOT NULL, "
            + KEY_SERVER_ID + " INTEGER DEFAULT NULL, "
            + KEY_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL DEFAULT 0, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + " PRIMARY KEY (" + KEY_ID + " ), "
            + " FOREIGN KEY (" + KEY_RISK_REGISTER_FK_ID + ") "
            + " REFERENCES " + TABLE_tblRISKREGISTER + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_RISK_LIKELIHOOD_FK_ID + ") "
            + " REFERENCES " + TABLE_tblRISKLIKELIHOOD + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE); ";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblRISKIMPACT` (4)
    //-- -------------------------------------------------------------------------------------------
    public static final String CREATE_TABLE_tblRISKIMPACT = "CREATE TABLE " +
            TABLE_tblRISKIMPACT + "("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_SERVER_ID + " INTEGER DEFAULT NULL, "
            + KEY_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL DEFAULT 0, "
            + KEY_NAME + " TEXT NOT NULL, "
            + KEY_DESCRIPTION + " TEXT NULL, "
            + KEY_RISKIMPACT_VALUE + " INTEGER, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP " + ");";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblRISKIMPACTSET` (5)
    //-- -------------------------------------------------------------------------------------------
    public static final String CREATE_TABLE_tblRISKIMPACTSET = "CREATE TABLE " +
            TABLE_tblRISKIMPACTSET + "("
            + KEY_ID+ " INTEGER NOT NULL, "
            + KEY_RISK_REGISTER_FK_ID + " INTEGER NOT NULL, "
            + KEY_RISK_IMPACT_FK_ID + " INTEGER NOT NULL, "
            + KEY_SERVER_ID + " INTEGER DEFAULT NULL, "
            + KEY_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL DEFAULT 0, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + " PRIMARY KEY (" + KEY_ID + " ), "
            + " FOREIGN KEY (" + KEY_RISK_REGISTER_FK_ID + ") "
            + " REFERENCES " + TABLE_tblRISKREGISTER + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_RISK_IMPACT_FK_ID + ") "
            + " REFERENCES " + TABLE_tblRISKIMPACT + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE); ";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblRISKCRITERIA` (6)
    //-- -------------------------------------------------------------------------------------------
    public static final String CREATE_TABLE_tblRISKCRITERIA = "CREATE TABLE " +
            TABLE_tblRISKCRITERIA + "("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_SERVER_ID + " INTEGER DEFAULT NULL, "
            + KEY_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL DEFAULT 0, "
            + KEY_NAME + " TEXT NOT NULL, "
            + KEY_DESCRIPTION + " TEXT NULL, "
            + KEY_RISKMAP_LOWER + " INTEGER, "
            + KEY_RISKMAP_UPPER + " INTEGER, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP " + ");";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblRISKCRITERIASET` (7)
    //-- -------------------------------------------------------------------------------------------
    public static final String CREATE_TABLE_tblRISKCRITERIASET = "CREATE TABLE " +
            TABLE_tblRISKCRITERIASET + "("
            + KEY_ID+ " INTEGER NOT NULL, "
            + KEY_RISK_REGISTER_FK_ID + " INTEGER NOT NULL, "
            + KEY_RISK_CRITERIA_FK_ID + " INTEGER NOT NULL, "
            + KEY_SERVER_ID + " INTEGER DEFAULT NULL, "
            + KEY_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL DEFAULT 0, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + " PRIMARY KEY (" + KEY_ID + " ), "
            + " FOREIGN KEY (" + KEY_RISK_REGISTER_FK_ID + ") "
            + " REFERENCES " + TABLE_tblRISKREGISTER + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_RISK_CRITERIA_FK_ID + ") "
            + " REFERENCES " + TABLE_tblRISKCRITERIA + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE); ";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblRISK` (8)
    //-- -------------------------------------------------------------------------------------------
    public static final String CREATE_TABLE_tblRISK = "CREATE TABLE " +
            TABLE_tblRISK + "("
            + KEY_RAID_FK_ID + " INTEGER NOT NULL, "
            + KEY_RISK_REGISTER_FK_ID + " INTEGER NOT NULL, "
            + KEY_STAFF_FK_ID + " INTEGER NOT NULL, "
            + KEY_FREQUENCY_FK_ID + " INTEGER NOT NULL, "
            + KEY_RISK_LIKELIHOOD_FK_ID + " INTEGER NOT NULL, "
            + KEY_RISK_IMPACT_FK_ID + " INTEGER NOT NULL, "
            + KEY_SERVER_ID + " INTEGER DEFAULT NULL, "
            + KEY_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL DEFAULT 0, "
            + KEY_NAME + " TEXT NOT NULL, "
            + KEY_DESCRIPTION + " TEXT NULL, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + " PRIMARY KEY (" + KEY_RAID_FK_ID + " ), "
            + " FOREIGN KEY (" + KEY_RAID_FK_ID + ") "
            + " REFERENCES " + TABLE_tblRAID + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_RISK_REGISTER_FK_ID + ") "
            + " REFERENCES " + TABLE_tblRISKREGISTER + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_STAFF_FK_ID + ") "
            + " REFERENCES " + TABLE_tblHUMANSET + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_FREQUENCY_FK_ID + ") "
            + " REFERENCES " + TABLE_tblFREQUENCY + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_RISK_LIKELIHOOD_FK_ID + ") "
            + " REFERENCES " + TABLE_tblRISKLIKELIHOOD + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_RISK_IMPACT_FK_ID + ") "
            + " REFERENCES " + TABLE_tblRISKIMPACT + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE); ";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblRISKROOTCAUSE` (9)
    //-- -------------------------------------------------------------------------------------------
    public static final String CREATE_TABLE_tblRISKROOTCAUSE = "CREATE TABLE " +
            TABLE_tblRISKROOTCAUSE + "("
            + KEY_ID + " INTEGER NOT NULL, "
            + KEY_RISK_FK_ID + " INTEGER NOT NULL, "
            + KEY_SERVER_ID + " INTEGER DEFAULT NULL, "
            + KEY_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL DEFAULT 0, "
            + KEY_NAME + " TEXT NOT NULL, "
            + KEY_DESCRIPTION + " TEXT NULL, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + " PRIMARY KEY (" + KEY_ID + " ), "
            + " FOREIGN KEY (" + KEY_RISK_FK_ID + ") "
            + " REFERENCES " + TABLE_tblRISK + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE); ";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblRISKCONSEQUENCE` (10)
    //-- -------------------------------------------------------------------------------------------
    public static final String CREATE_TABLE_tblRISKCONSEQUENCE = "CREATE TABLE " +
            TABLE_tblRISKCONSEQUENCE + "("
            + KEY_ID + " INTEGER NOT NULL, "
            + KEY_RISK_FK_ID + " INTEGER NOT NULL, "
            + KEY_SERVER_ID + " INTEGER DEFAULT NULL, "
            + KEY_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL DEFAULT 0, "
            + KEY_NAME + " TEXT NOT NULL, "
            + KEY_DESCRIPTION + " TEXT NULL, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + " PRIMARY KEY (" + KEY_ID + " ), "
            + " FOREIGN KEY (" + KEY_RISK_FK_ID + ") "
            + " REFERENCES " + TABLE_tblRISK + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE); ";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblCURRENTCONTROL` (11)
    //-- -------------------------------------------------------------------------------------------
    public static final String CREATE_TABLE_tblCURRENTCONTROL = "CREATE TABLE " +
            TABLE_tblCURRENTCONTROL + "("
            + KEY_RISK_ACTION_FK_ID + " INTEGER NOT NULL, "
            + KEY_RISK_ANALYSIS_FK_ID + " INTEGER NOT NULL, "
            + KEY_SERVER_ID + " INTEGER DEFAULT NULL, "
            + KEY_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL DEFAULT 0, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + " PRIMARY KEY (" + KEY_RISK_ACTION_FK_ID + " ), "
            + " FOREIGN KEY (" + KEY_RISK_ACTION_FK_ID + ") "
            + " REFERENCES " + TABLE_tblRISKACTION + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_RISK_ANALYSIS_FK_ID + ") "
            + " REFERENCES " + TABLE_tblRISKANALYSIS + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE); ";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblCURRENTCONTROL` (12)
    //-- -------------------------------------------------------------------------------------------
    public static final String CREATE_TABLE_tblADDITIONALCONTROL = "CREATE TABLE " +
            TABLE_tblADDITIONALCONTROL + "("
            + KEY_RISK_ACTION_FK_ID + " INTEGER NOT NULL, "
            + KEY_RISK_ANALYSIS_FK_ID + " INTEGER NOT NULL, "
            + KEY_SERVER_ID + " INTEGER DEFAULT NULL, "
            + KEY_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL DEFAULT 0, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + " PRIMARY KEY (" + KEY_RISK_ACTION_FK_ID + " ), "
            + " FOREIGN KEY (" + KEY_RISK_ACTION_FK_ID + ") "
            + " REFERENCES " + TABLE_tblRISKACTION + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_RISK_ANALYSIS_FK_ID + ") "
            + " REFERENCES " + TABLE_tblRISKANALYSIS + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE); ";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblRISKMILESTONE` (13)
    //-- -------------------------------------------------------------------------------------------
    public static final String CREATE_TABLE_tblRISKMILESTONE = "CREATE TABLE " +
            TABLE_tblRISKMILESTONE + "("
            + KEY_MILESTONE_FK_ID + " INTEGER NOT NULL, "
            + KEY_RISK_FK_ID + " INTEGER NOT NULL, "
            + KEY_SERVER_ID + " INTEGER DEFAULT NULL, "
            + KEY_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL DEFAULT 0, "
            + KEY_NAME + " TEXT NOT NULL, "
            + KEY_DESCRIPTION + " TEXT NULL, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + " PRIMARY KEY (" + KEY_MILESTONE_FK_ID + " ), "
            + " FOREIGN KEY (" + KEY_MILESTONE_FK_ID + ") "
            + " REFERENCES " + TABLE_tblMILESTONE + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_RISK_FK_ID + ") "
            + " REFERENCES " + TABLE_tblRISK + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE); ";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblRISKANALYSIS` (14)
    //-- -------------------------------------------------------------------------------------------
    public static final String CREATE_TABLE_tblRISKANALYSIS = "CREATE TABLE " +
            TABLE_tblRISKANALYSIS + "("
            + KEY_ID + " INTEGER NOT NULL, "
            + KEY_RISK_MILESTONE_FK_ID + " INTEGER NOT NULL, "
            + KEY_RISK_LIKELIHOOD_FK_ID + " INTEGER NOT NULL, "
            + KEY_RISK_IMPACT_FK_ID + " INTEGER NOT NULL, "
            + KEY_SERVER_ID + " INTEGER DEFAULT NULL, "
            + KEY_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL DEFAULT 0, "
            + KEY_RISK_INDEX + " INTEGER, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + " PRIMARY KEY (" + KEY_ID + " ), "
            + " FOREIGN KEY (" + KEY_RISK_MILESTONE_FK_ID + ") "
            + " REFERENCES " + TABLE_tblMILESTONE + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_RISK_LIKELIHOOD_FK_ID + ") "
            + " REFERENCES " + TABLE_tblRISKLIKELIHOOD + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_RISK_IMPACT_FK_ID + ") "
            + " REFERENCES " + TABLE_tblRISKIMPACT + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE); ";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblRISKPLAN` (15)
    //-- -------------------------------------------------------------------------------------------
    public static final String CREATE_TABLE_tblRISKPLAN = "CREATE TABLE " +
            TABLE_tblRISKPLAN + "("
            + KEY_ACTIVITYPLANNING_FK_ID + " INTEGER NOT NULL, "
            + KEY_SERVER_ID + " INTEGER DEFAULT NULL, "
            + KEY_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL DEFAULT 0, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + " PRIMARY KEY (" + KEY_ACTIVITYPLANNING_FK_ID + " ), "
            + " FOREIGN KEY (" + KEY_ACTIVITYPLANNING_FK_ID + ") "
            + " REFERENCES " + TABLE_tblACTIVITYPLANNING + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE); ";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblRISKACTIONTYPE` (16)
    //-- -------------------------------------------------------------------------------------------
    public static final String CREATE_TABLE_tblRISKACTIONTYPE = "CREATE TABLE " +
            TABLE_tblRISKACTIONTYPE + "("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_SERVER_ID + " INTEGER DEFAULT NULL, "
            + KEY_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL DEFAULT 0, "
            + KEY_NAME + " TEXT NOT NULL, "
            + KEY_DESCRIPTION + " TEXT NULL, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP " + ");";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblRISKACTION` (17)
    //-- -------------------------------------------------------------------------------------------
    public static final String CREATE_TABLE_tblRISKACTION = "CREATE TABLE " +
            TABLE_tblRISKACTION + "("
            + KEY_TASK_FK_ID + " INTEGER NOT NULL, "
            + KEY_RISK_PLAN_FK_ID + " INTEGER NOT NULL, "
            + KEY_RISK_ACTION_TYPE_FK_ID + " INTEGER NOT NULL, "
            + KEY_SERVER_ID + " INTEGER DEFAULT NULL, "
            + KEY_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL DEFAULT 0, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + " PRIMARY KEY (" + KEY_TASK_FK_ID + " ), "
            + " FOREIGN KEY (" + KEY_TASK_FK_ID + ") "
            + " REFERENCES " + TABLE_tblTASK + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_RISK_PLAN_FK_ID + ") "
            + " REFERENCES " + TABLE_tblRISKPLAN + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_RISK_ACTION_TYPE_FK_ID + ") "
            + " REFERENCES " + TABLE_tblRISKACTIONTYPE + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE); ";

    /*####################################### END RAID MODULE ####################################*/


    /*################################### START EVALUATION MODULE ################################*/

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblARRAYCHOICE ` (1)
    //-- -------------------------------------------------------------------------------------------
    public static final String CREATE_TABLE_tblARRAYCHOICE = "CREATE TABLE " +
            TABLE_tblARRAYCHOICE  + "("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_SERVER_ID + " INTEGER DEFAULT NULL, "
            + KEY_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL DEFAULT 0, "
            + KEY_NAME + " TEXT NOT NULL, "
            + KEY_DESCRIPTION + " TEXT NULL, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP); ";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblARRAYCHOICESET` (2)
    //-- -------------------------------------------------------------------------------------------
    public static final String CREATE_TABLE_tblARRAYCHOICESET = "CREATE TABLE " +
            TABLE_tblARRAYCHOICESET+ "("
            + KEY_QUESTION_FK_ID + " INTEGER NOT NULL, "
            + KEY_ARRAY_TYPE_FK_ID + " INTEGER NOT NULL, "
            + KEY_ARRAY_CHOICE_FK_ID + " INTEGER NOT NULL, "
            + KEY_SERVER_ID + " INTEGER DEFAULT NULL, "
            + KEY_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL DEFAULT 0, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + " PRIMARY KEY (" + KEY_QUESTION_FK_ID + "," + KEY_ARRAY_TYPE_FK_ID + ","
            + KEY_ARRAY_CHOICE_FK_ID + " ), "
            + " FOREIGN KEY (" + KEY_QUESTION_FK_ID + ") "
            + " REFERENCES " + TABLE_tblQUESTION + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_ARRAY_TYPE_FK_ID + ") "
            + " REFERENCES " + TABLE_tblARRAYTYPE + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_ARRAY_CHOICE_FK_ID + ")"
            + " REFERENCES " + TABLE_tblARRAYCHOICE + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE);";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblROWOPTION` (3)
    //-- -------------------------------------------------------------------------------------------
    public static final String CREATE_TABLE_tblROWOPTION = "CREATE TABLE " +
            TABLE_tblROWOPTION + "("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_SERVER_ID + " INTEGER DEFAULT NULL, "
            + KEY_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL DEFAULT 0, "
            + KEY_NAME + " TEXT NOT NULL, "
            + KEY_DESCRIPTION + " TEXT NULL, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP); ";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblCOLOPTION` (4)
    //-- -------------------------------------------------------------------------------------------
    public static final String CREATE_TABLE_tblCOLOPTION = "CREATE TABLE " +
            TABLE_tblCOLOPTION + "("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_SERVER_ID + " INTEGER DEFAULT NULL, "
            + KEY_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL DEFAULT 0, "
            + KEY_NAME + " TEXT NOT NULL, "
            + KEY_DESCRIPTION + " TEXT NULL, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP); ";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblMATRIXCHOICE` (5)
    //-- -------------------------------------------------------------------------------------------
    public static final String CREATE_TABLE_tblMATRIXCHOICE = "CREATE TABLE " +
            TABLE_tblMATRIXCHOICE + "("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_ROW_OPTION_FK_ID + " INTEGER NOT NULL, "
            + KEY_COL_OPTION_FK_ID + " INTEGER NOT NULL, "
            + KEY_SERVER_ID + " INTEGER DEFAULT NULL, "
            + KEY_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL DEFAULT 0, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + " FOREIGN KEY (" + KEY_ROW_OPTION_FK_ID + ") "
            + " REFERENCES " + TABLE_tblROWOPTION + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_COL_OPTION_FK_ID + ") "
            + " REFERENCES " + TABLE_tblCOLOPTION + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE); ";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblMATRIXCHOICESET` (6)
    //-- -------------------------------------------------------------------------------------------
    public static final String CREATE_TABLE_tblMATRIXCHOICESET = "CREATE TABLE " +
            TABLE_tblMATRIXCHOICESET+ "("
            + KEY_QUESTION_FK_ID + " INTEGER NOT NULL, "
            + KEY_MATRIX_TYPE_FK_ID + " INTEGER NOT NULL, "
            + KEY_MATRIX_CHOICE_FK_ID + " INTEGER NOT NULL, "
            + KEY_SERVER_ID + " INTEGER DEFAULT NULL, "
            + KEY_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL DEFAULT 0, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + " PRIMARY KEY (" + KEY_QUESTION_FK_ID + "," + KEY_MATRIX_TYPE_FK_ID + ","
            + KEY_MATRIX_CHOICE_FK_ID + " ), "
            + " FOREIGN KEY (" + KEY_QUESTION_FK_ID + ") "
            + " REFERENCES " + TABLE_tblQUESTION + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_MATRIX_TYPE_FK_ID + ") "
            + " REFERENCES " + TABLE_tblMATRIXTYPE + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_MATRIX_CHOICE_FK_ID + ")"
            + " REFERENCES " + TABLE_tblMATRIXCHOICE + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE);";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblEVALUATIONTYPE ` (7)
    //-- -------------------------------------------------------------------------------------------
    public static final String CREATE_TABLE_tblEVALUATIONTYPE = "CREATE TABLE " +
            TABLE_tblEVALUATIONTYPE + "("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_SERVER_ID + " INTEGER DEFAULT NULL, "
            + KEY_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL DEFAULT 0, "
            + KEY_NAME + " TEXT NOT NULL, "
            + KEY_DESCRIPTION + " TEXT NULL, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP); ";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblQUESTIONNAIRE` (8)
    //-- -------------------------------------------------------------------------------------------
    public static final String CREATE_TABLE_tblQUESTIONNAIRE = "CREATE TABLE " +
            TABLE_tblQUESTIONNAIRE + "("
            + KEY_ID + " INTEGER NOT NULL, "
            + KEY_EVALUATION_TYPE_FK_ID + " INTEGER NOT NULL, "
            + KEY_SERVER_ID + " INTEGER DEFAULT NULL, "
            + KEY_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL DEFAULT 0, "
            + KEY_NAME + " TEXT NOT NULL, "
            + KEY_DESCRIPTION + " TEXT NULL, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + " PRIMARY KEY (" + KEY_ID + "),"
            + " FOREIGN KEY (" + KEY_EVALUATION_TYPE_FK_ID + ")"
            + " REFERENCES " + TABLE_tblEVALUATIONTYPE + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE);";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblQUESTION_QUESTIONNAIRE` (9)
    //-- -------------------------------------------------------------------------------------------

    public static final String CREATE_TABLE_tblQUESTION_QUESTIONNAIRE = "CREATE TABLE " +
            TABLE_tblQUESTION_QUESTIONNAIRE + "("
            + KEY_ID + " INTEGER NOT NULL, "
            + KEY_QUESTION_FK_ID + " INTEGER NOT NULL, "
            + KEY_QUESTIONNAIRE_FK_ID + " INTEGER NOT NULL, "
            + KEY_SERVER_ID + " INTEGER DEFAULT NULL, "
            + KEY_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL DEFAULT 0, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + "PRIMARY KEY (" + KEY_ID + "),"
            + " FOREIGN KEY (" + KEY_QUESTION_FK_ID + ") "
            + " REFERENCES " + TABLE_tblQUESTION + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_QUESTIONNAIRE_FK_ID + ") "
            + " REFERENCES " + TABLE_tblQUESTIONNAIRE + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE); ";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblCONDITIONAL_ORDER` (10)
    //-- -------------------------------------------------------------------------------------------

    public static final String CREATE_TABLE_tblCONDITIONAL_ORDER = "CREATE TABLE " +
            TABLE_tblCONDITIONAL_ORDER + "("
            + KEY_ID + " INTEGER NOT NULL, "
            + KEY_QUESTION_ORDER_FK_ID + " INTEGER NOT NULL, "
            + KEY_QUESTION_RESPONSE_FK_ID + " INTEGER NOT NULL, "
            + KEY_POS_RES_ORDER_FK_ID + " INTEGER NOT NULL, "
            + KEY_NEG_RES_ORDER_FK_ID  + " INTEGER NOT NULL, "
            + KEY_SERVER_ID + " INTEGER DEFAULT NULL, "
            + KEY_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL DEFAULT 0, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + "PRIMARY KEY (" + KEY_ID + "),"
            + " FOREIGN KEY (" + KEY_QUESTION_ORDER_FK_ID + ") "
            + " REFERENCES " + TABLE_tblQUESTION_QUESTIONNAIRE + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_QUESTION_RESPONSE_FK_ID + ") "
            + " REFERENCES " + TABLE_tblQUESTION_QUESTIONNAIRE + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_POS_RES_ORDER_FK_ID + ") "
            + " REFERENCES " + TABLE_tblQUESTION_QUESTIONNAIRE + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_NEG_RES_ORDER_FK_ID + ") "
            + " REFERENCES " + TABLE_tblQUESTION_QUESTIONNAIRE + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE); ";


    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblQUESTIONNAIRE_USER ` (11)
    //-- -------------------------------------------------------------------------------------------

    public static final String CREATE_TABLE_tblQUESTIONNAIRE_USER = "CREATE TABLE " +
            TABLE_tblQUESTIONNAIRE_USER + "("
            + KEY_ID + " INTEGER NOT NULL, "
            + KEY_USER_FK_ID + " INTEGER NOT NULL, "
            + KEY_QUESTIONNAIRE_FK_ID + " INTEGER NOT NULL, "
            + KEY_SERVER_ID + " INTEGER DEFAULT NULL, "
            + KEY_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL DEFAULT 0, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + "PRIMARY KEY (" + KEY_ID + "),"
            + " FOREIGN KEY (" + KEY_USER_FK_ID + ") "
            + " REFERENCES " + TABLE_tblUSER + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_QUESTIONNAIRE_FK_ID + ") "
            + " REFERENCES " + TABLE_tblQUESTIONNAIRE + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE); ";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblRESPONSE` (12)
    //-- -------------------------------------------------------------------------------------------

    public static final String CREATE_TABLE_tblERESPONSE = "CREATE TABLE " +
            TABLE_tblERESPONSE+ "("
            + KEY_ID + " INTEGER NOT NULL, "
            + KEY_QUESTION_FK_ID + " INTEGER NOT NULL, "
            + KEY_QUESTIONNAIRE_USER_FK_ID + " INTEGER NOT NULL, "
            + KEY_SERVER_ID + " INTEGER DEFAULT NULL, "
            + KEY_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL DEFAULT 0, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + "PRIMARY KEY (" + KEY_ID + "),"
            + " FOREIGN KEY (" + KEY_QUESTION_FK_ID + ") "
            + " REFERENCES " + TABLE_tblQUESTION + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_QUESTIONNAIRE_USER_FK_ID + ") "
            + " REFERENCES " + TABLE_tblQUESTIONNAIRE_USER + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE); ";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblNUMERICRESPONSE` (13)
    //-- -------------------------------------------------------------------------------------------

    public static final String CREATE_TABLE_tblNUMERICRESPONSE = "CREATE TABLE " +
            TABLE_tblNUMERICRESPONSE+ "("
            + KEY_RESPONSE_FK_ID + " INTEGER NOT NULL, "
            + KEY_SERVER_ID + " INTEGER DEFAULT NULL, "
            + KEY_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL DEFAULT 0, "
            + KEY_NUMERIC_RESPONSE_FK_ID + " INTEGER NOT NULL, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + "PRIMARY KEY (" + KEY_RESPONSE_FK_ID + "),"
            + " FOREIGN KEY (" + KEY_RESPONSE_FK_ID + ") "
            + " REFERENCES " + TABLE_tblERESPONSE + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE); ";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblTEXTRESPONSE` (14)
    //-- -------------------------------------------------------------------------------------------

    public static final String CREATE_TABLE_tblTEXTRESPONSE = "CREATE TABLE " +
            TABLE_tblTEXTRESPONSE+ "("
            + KEY_RESPONSE_FK_ID + " INTEGER NOT NULL, "
            + KEY_SERVER_ID + " INTEGER DEFAULT NULL, "
            + KEY_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL DEFAULT 0, "
            + KEY_TEXT_RESPONSE_FK_ID + " TEXT NOT NULL, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + "PRIMARY KEY (" + KEY_RESPONSE_FK_ID + "),"
            + " FOREIGN KEY (" + KEY_RESPONSE_FK_ID + ") "
            + " REFERENCES " + TABLE_tblERESPONSE + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE); ";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblDATERESPONSE` (15)
    //-- -------------------------------------------------------------------------------------------

    public static final String CREATE_TABLE_tblDATERESPONSE = "CREATE TABLE " +
            TABLE_tblDATERESPONSE+ "("
            + KEY_RESPONSE_FK_ID + " INTEGER NOT NULL, "
            + KEY_SERVER_ID + " INTEGER DEFAULT NULL, "
            + KEY_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL DEFAULT 0, "
            + KEY_DATE_RESPONSE_FK_ID + " DATE NOT NULL, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + "PRIMARY KEY (" + KEY_RESPONSE_FK_ID + "),"
            + " FOREIGN KEY (" + KEY_RESPONSE_FK_ID + ") "
            + " REFERENCES " + TABLE_tblERESPONSE + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE); ";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblARRAYRESPONSE` (16)
    //-- -------------------------------------------------------------------------------------------

    public static final String CREATE_TABLE_tblARRAYRESPONSE = "CREATE TABLE " +
            TABLE_tblARRAYRESPONSE+ "("
            + KEY_RESPONSE_FK_ID + " INTEGER NOT NULL, "
            + KEY_SERVER_ID + " INTEGER DEFAULT NULL, "
            + KEY_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL DEFAULT 0, "
            + KEY_ARRAY_RESPONSE_FK_ID + " INTEGER NOT NULL, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + "PRIMARY KEY (" + KEY_RESPONSE_FK_ID + "),"
            + " FOREIGN KEY (" + KEY_RESPONSE_FK_ID + ") "
            + " REFERENCES " + TABLE_tblERESPONSE + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE); ";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblMATRIXRESPONSE` (17)
    //-- -------------------------------------------------------------------------------------------

    public static final String CREATE_TABLE_tblMATRIXRESPONSE = "CREATE TABLE " +
            TABLE_tblMATRIXRESPONSE+ "("
            + KEY_RESPONSE_FK_ID + " INTEGER NOT NULL, "
            + KEY_SERVER_ID + " INTEGER DEFAULT NULL, "
            + KEY_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL DEFAULT 0, "
            + KEY_MATRIX_RESPONSE_FK_ID + " INTEGER NOT NULL, "
            + KEY_ROW_RESPONSE_FK_ID + " INTEGER NOT NULL, "
            + KEY_COL_RESPONSE_FK_ID + " INTEGER NOT NULL, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + "PRIMARY KEY (" + KEY_RESPONSE_FK_ID + "),"
            + " FOREIGN KEY (" + KEY_RESPONSE_FK_ID + ") "
            + " REFERENCES " + TABLE_tblERESPONSE + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE); ";

    /*#################################### END EVALUATION MODULE #################################*/


    /*################################### START MONITORING MODULE ################################*/

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblQUESTION_INDICATOR ` (1)
    //-- -------------------------------------------------------------------------------------------

    public static final String CREATE_TABLE_tblQUESTION_INDICATOR = "CREATE TABLE " +
            TABLE_tblQUESTION_INDICATOR + "("
            + KEY_QUESTION_FK_ID + " INTEGER NOT NULL, "
            + KEY_INDICATOR_FK_ID + " INTEGER NOT NULL, "
            + KEY_SERVER_ID + " INTEGER DEFAULT NULL, "
            + KEY_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL DEFAULT 0, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + "PRIMARY KEY (" + KEY_QUESTION_FK_ID + ", " + KEY_INDICATOR_FK_ID + "),"
            + " FOREIGN KEY (" + KEY_QUESTION_FK_ID + ") "
            + " REFERENCES " + TABLE_tblQUESTION + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_INDICATOR_FK_ID + ") "
            + " REFERENCES " + TABLE_tblINDICATOR + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE); ";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblMOV` (2)
    //-- -------------------------------------------------------------------------------------------
    public static final String CREATE_TABLE_tblMOV = "CREATE TABLE " +
            TABLE_tblMOV + "("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_SERVER_ID + " INTEGER DEFAULT NULL, "
            + KEY_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL DEFAULT 0, "
            + KEY_NAME + " TEXT NOT NULL, "
            + KEY_DESCRIPTION + " TEXT NULL, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP); ";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblMETHOD` (3)
    //-- -------------------------------------------------------------------------------------------
    public static final String CREATE_TABLE_tblMETHOD = "CREATE TABLE " +
            TABLE_tblMETHOD + "("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_SERVER_ID + " INTEGER DEFAULT NULL, "
            + KEY_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL DEFAULT 0, "
            + KEY_NAME + " TEXT NOT NULL, "
            + KEY_DESCRIPTION + " TEXT NULL, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP); ";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblUNIT` (4)
    //-- -------------------------------------------------------------------------------------------
    public static final String CREATE_TABLE_tblUNIT = "CREATE TABLE " +
            TABLE_tblUNIT + "("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_SERVER_ID + " INTEGER DEFAULT NULL, "
            + KEY_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL DEFAULT 0, "
            + KEY_NAME + " TEXT NOT NULL, "
            + KEY_DESCRIPTION + " TEXT NULL, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP); ";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblINDICATORTYPE` (5)
    //-- -------------------------------------------------------------------------------------------
    public static final String CREATE_TABLE_tblINDICATORTYPE = "CREATE TABLE " +
            TABLE_tblINDICATORTYPE + "("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_SERVER_ID + " INTEGER DEFAULT NULL, "
            + KEY_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL DEFAULT 0, "
            + KEY_NAME + " TEXT NOT NULL, "
            + KEY_DESCRIPTION + " TEXT NULL, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP); ";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblQUANTITATIVE` (6)
    //-- -------------------------------------------------------------------------------------------
    public static final String CREATE_TABLE_tblQUANTITATIVE = "CREATE TABLE " +
            TABLE_tblQUANTITATIVE + "("
            + KEY_INDICATOR_TYPE_FK_ID + " INTEGER NOT NULL, "
            + KEY_UNIT_FK_ID + " INTEGER, "
            + KEY_SERVER_ID + " INTEGER DEFAULT NULL, "
            + KEY_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL DEFAULT 0, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + " PRIMARY KEY (" + KEY_INDICATOR_TYPE_FK_ID + "),"
            + " FOREIGN KEY (" + KEY_INDICATOR_TYPE_FK_ID + ") "
            + " REFERENCES " + TABLE_tblINDICATORTYPE + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_UNIT_FK_ID + ") "
            + " REFERENCES " + TABLE_tblUNIT + "(" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE);";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblQUALITATIVE` (7)
    //-- -------------------------------------------------------------------------------------------
    public static final String CREATE_TABLE_tblQUALITATIVE = "CREATE TABLE " +
            TABLE_tblQUALITATIVE + "("
            + KEY_INDICATOR_TYPE_FK_ID + " INTEGER NOT NULL, "
            + KEY_SERVER_ID + " INTEGER DEFAULT NULL, "
            + KEY_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL DEFAULT 0, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + "PRIMARY KEY (" + KEY_INDICATOR_TYPE_FK_ID + "),"
            + "FOREIGN KEY (" + KEY_INDICATOR_TYPE_FK_ID + ") "
            + "REFERENCES " + TABLE_tblINDICATORTYPE + "(" + KEY_ID + ") "
            + "ON DELETE CASCADE ON UPDATE CASCADE);";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblDATACOLLECTOR` (8)
    //-- -------------------------------------------------------------------------------------------
    public static final String CREATE_TABLE_tblDATACOLLECTOR = "CREATE TABLE " +
            TABLE_tblDATACOLLECTOR + "("
            + KEY_ID + " INTEGER NOT NULL, "
            + KEY_STAFF_FK_ID + " INTEGER, "
            + KEY_SERVER_ID + " INTEGER DEFAULT NULL, "
            + KEY_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL DEFAULT 0, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + "PRIMARY KEY (" + KEY_ID + "),"
            + "FOREIGN KEY (" + KEY_STAFF_FK_ID + ") "
            + "REFERENCES " + TABLE_tblHUMANSET + "(" + KEY_ID + ") "
            + "ON DELETE CASCADE ON UPDATE CASCADE);";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblINDICATOR` (9)
    //-- -------------------------------------------------------------------------------------------
    public static final String CREATE_TABLE_tblINDICATOR = "CREATE TABLE " +
            TABLE_tblINDICATOR + "("
            + KEY_ID + " INTEGER NOT NULL, "
            + KEY_LOGFRAME_FK_ID + " INTEGER NOT NULL, "
            + KEY_INDICATOR_TYPE_FK_ID + " INTEGER NOT NULL, "
            + KEY_DATA_COLLECTOR_FK_ID + " INTEGER NOT NULL, "
            + KEY_FREQUENCY_FK_ID + " INTEGER NOT NULL, "
            + KEY_SERVER_ID + " INTEGER DEFAULT NULL, "
            + KEY_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL DEFAULT 0, "
            + KEY_NAME + " TEXT NOT NULL, "
            + KEY_DESCRIPTION + " TEXT, "
            + KEY_QUESTION + " TEXT, "
            + KEY_TARGET_VALUE + " INTEGER, "
            + KEY_START_DATE + " DATE, "
            + KEY_END_DATE + " DATE, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + "PRIMARY KEY (" + KEY_ID + "),"
            + " FOREIGN KEY (" + KEY_LOGFRAME_FK_ID + ") "
            + " REFERENCES " + TABLE_tblLOGFRAME + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_INDICATOR_TYPE_FK_ID + ") "
            + " REFERENCES " + TABLE_tblINDICATORTYPE + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_DATA_COLLECTOR_FK_ID + ") "
            + " REFERENCES " + TABLE_tblDATACOLLECTOR + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + "FOREIGN KEY (" + KEY_FREQUENCY_FK_ID + ") "
            + "REFERENCES " + TABLE_tblFREQUENCY + "(" + KEY_ID + ") "
            + "ON DELETE CASCADE ON UPDATE CASCADE);";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblINDICATORMILESTONE` (10)
    //-- -------------------------------------------------------------------------------------------
    public static final String CREATE_TABLE_tblINDICATORMILESTONE = "CREATE TABLE " +
            TABLE_tblINDICATORMILESTONE + "("
            + KEY_MILESTONE_FK_ID + " INTEGER NOT NULL, "
            + KEY_INDICATOR_FK_ID + " INTEGER, "
            + KEY_SERVER_ID + " INTEGER DEFAULT NULL, "
            + KEY_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL DEFAULT 0, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + "PRIMARY KEY (" + KEY_MILESTONE_FK_ID + "),"
            + " FOREIGN KEY (" + KEY_MILESTONE_FK_ID + ") "
            + " REFERENCES " + TABLE_tblMILESTONE+ " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + "FOREIGN KEY (" + KEY_INDICATOR_FK_ID + ") "
            + "REFERENCES " + TABLE_tblINDICATOR + "(" + KEY_ID + ") "
            + "ON DELETE CASCADE ON UPDATE CASCADE);";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblINDICATOR_MOV` (11)
    //-- -------------------------------------------------------------------------------------------
    public static final String CREATE_TABLE_tblINDICATOR_MOV = "CREATE TABLE " +
            TABLE_tblINDICATOR_MOV + "("
            + KEY_INDICATOR_FK_ID + " INTEGER NOT NULL, "
            + KEY_MOV_FK_ID + " INTEGER NOT NULL, "
            + KEY_SERVER_ID + " INTEGER DEFAULT NULL, "
            + KEY_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL DEFAULT 0, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + "PRIMARY KEY (" + KEY_INDICATOR_FK_ID +", " +KEY_MOV_FK_ID + "),"
            + " FOREIGN KEY (" + KEY_INDICATOR_FK_ID + ") "
            + " REFERENCES " + TABLE_tblINDICATOR+ " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + "FOREIGN KEY (" + KEY_MOV_FK_ID + ") "
            + "REFERENCES " + TABLE_tblMOV + "(" + KEY_ID + ") "
            + "ON DELETE CASCADE ON UPDATE CASCADE);";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblINDICATOR_METHOD` (12)
    //-- -------------------------------------------------------------------------------------------
    public static final String CREATE_TABLE_tblINDICATOR_METHOD = "CREATE TABLE " +
            TABLE_tblINDICATOR_METHOD + "("
            + KEY_INDICATOR_FK_ID + " INTEGER NOT NULL, "
            + KEY_METHOD_FK_ID + " INTEGER NOT NULL, "
            + KEY_SERVER_ID + " INTEGER DEFAULT NULL, "
            + KEY_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL DEFAULT 0, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + "PRIMARY KEY (" + KEY_INDICATOR_FK_ID +", " + KEY_METHOD_FK_ID + "),"
            + " FOREIGN KEY (" + KEY_INDICATOR_FK_ID + ") "
            + " REFERENCES " + TABLE_tblINDICATOR + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + "FOREIGN KEY (" + KEY_METHOD_FK_ID + ") "
            + "REFERENCES " + TABLE_tblMETHOD + "(" + KEY_ID + ") "
            + "ON DELETE CASCADE ON UPDATE CASCADE);";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblQUALITATIVECHOICE` (13)
    //-- -------------------------------------------------------------------------------------------
    public static final String CREATE_TABLE_tblQUALITATIVECHOICE = "CREATE TABLE " +
            TABLE_tblQUALITATIVECHOICE + "("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_SERVER_ID + " INTEGER DEFAULT NULL, "
            + KEY_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL DEFAULT 0, "
            + KEY_NAME + " TEXT NOT NULL, "
            + KEY_DESCRIPTION + " TEXT NULL, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP); ";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblQUALITATIVECHOICESET` (14)
    //-- -------------------------------------------------------------------------------------------
    public static final String CREATE_TABLE_tblQUALITATIVECHOICESET = "CREATE TABLE " +
            TABLE_tblQUALITATIVECHOICESET + "("
            + KEY_INDICATOR_FK_ID + " INTEGER NOT NULL, "
            + KEY_INDICATOR_TYPE_FK_ID + " INTEGER NOT NULL, "
            + KEY_QUALITATIVE_CHOICE_FK_ID + " INTEGER NOT NULL, "
            + KEY_SERVER_ID + " INTEGER DEFAULT NULL, "
            + KEY_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL DEFAULT 0, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + " PRIMARY KEY (" + KEY_INDICATOR_FK_ID + "," + KEY_INDICATOR_TYPE_FK_ID + ","
            + KEY_QUALITATIVE_CHOICE_FK_ID + " ), "
            + " FOREIGN KEY (" + KEY_INDICATOR_FK_ID + ") "
            + " REFERENCES " + TABLE_tblINDICATOR + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_INDICATOR_TYPE_FK_ID + ") "
            + " REFERENCES " + TABLE_tblINDICATORTYPE + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_QUALITATIVE_CHOICE_FK_ID + ")"
            + " REFERENCES " + TABLE_tblQUALITATIVECHOICE + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE);";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblQUANTITATIVERESPONSE` (15)
    //-- -------------------------------------------------------------------------------------------
    public static final String CREATE_TABLE_tblQUANTITATIVERESPONSE = "CREATE TABLE " +
            TABLE_tblQUANTITATIVERESPONSE + "("
            + KEY_MRESPONSE_FK_ID + " INTEGER NOT NULL, "
            + KEY_UNIT_FK_ID + " INTEGER NOT NULL, "
            + KEY_SERVER_ID + " INTEGER DEFAULT NULL, "
            + KEY_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL DEFAULT 0, "
            + KEY_TARGET_VALUE + " INTEGER, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + "PRIMARY KEY (" + KEY_MRESPONSE_FK_ID + "),"
            + " FOREIGN KEY (" + KEY_MRESPONSE_FK_ID + ") "
            + " REFERENCES " + TABLE_tblMRESPONSE + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_UNIT_FK_ID + ") "
            + " REFERENCES " + TABLE_tblUNIT + "(" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE);";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblQUALITATIVERESPONSE` (10)
    //-- -------------------------------------------------------------------------------------------
    public static final String CREATE_TABLE_tblQUALITATIVERESPONSE = "CREATE TABLE " +
            TABLE_tblQUALITATIVERESPONSE + "("
            + KEY_MRESPONSE_FK_ID + " INTEGER NOT NULL, "
            + KEY_QUALITATIVE_CHOICE_FK_ID + " INTEGER, "
            + KEY_SERVER_ID + " INTEGER DEFAULT NULL, "
            + KEY_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL DEFAULT 0, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + "PRIMARY KEY (" + KEY_MRESPONSE_FK_ID + "),"
            + " FOREIGN KEY (" + KEY_MRESPONSE_FK_ID + ") "
            + " REFERENCES " + TABLE_tblMRESPONSE + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_QUALITATIVE_CHOICE_FK_ID + ") "
            + " REFERENCES " + TABLE_tblQUALITATIVECHOICE + "(" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE);";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblMRESPONSE` (15)
    //-- -------------------------------------------------------------------------------------------
    public static final String CREATE_TABLE_tblMRESPONSE = "CREATE TABLE " +
            TABLE_tblMRESPONSE + "("
            + KEY_ID + " INTEGER NOT NULL, "
            + KEY_INDICATOR_MILESTONE_FK_ID + " INTEGER, "
            + KEY_DATA_COLLECTOR_FK_ID + " INTEGER, "
            + KEY_SERVER_ID + " INTEGER DEFAULT NULL, "
            + KEY_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL DEFAULT 0, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + " PRIMARY KEY (" + KEY_ID + "),"
            + " FOREIGN KEY (" + KEY_INDICATOR_MILESTONE_FK_ID + ") "
            + " REFERENCES " + TABLE_tblINDICATORMILESTONE + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_DATA_COLLECTOR_FK_ID + ") "
            + " REFERENCES " + TABLE_tblDATACOLLECTOR + "(" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE);";

    /*#################################### END MONITORING MODULE #################################*/

    public cSQLDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public cSQLDBHelper(Context context, int dbVersion, String dbName) {
        super(context, dbName, null, dbVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        /** drop all tables **/
        dropTables(db);

        db.execSQL("DROP TABLE IF EXISTS " + "tblISSUE");
        db.execSQL("DROP TABLE IF EXISTS " + "tblRISKISSUE");
        db.execSQL("DROP TABLE IF EXISTS " + "tblLIKELIHOOD");
        db.execSQL("DROP TABLE IF EXISTS " + "tblCONTINGENCYPLAN");
        db.execSQL("DROP TABLE IF EXISTS " + "tblMITIGATIONPLAN");
        db.execSQL("DROP TABLE IF EXISTS " + "tblMITIGATIONACTION");
        db.execSQL("DROP TABLE IF EXISTS " + "tblPREVENTATIVEPLAN");
        db.execSQL("DROP TABLE IF EXISTS " + "tblCONSEQUENCE");
        db.execSQL("DROP TABLE IF EXISTS " + "tblREGISTER");
        db.execSQL("DROP TABLE IF EXISTS " + "tblGENERALLEDGER");
        db.execSQL("DROP TABLE IF EXISTS " + "tblCRITERIA");
        db.execSQL("DROP TABLE IF EXISTS " + "tblROOTCAUSE");

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
        if (!db.isReadOnly()) {
            //enable foreign key constraints
            db.execSQL("PRAGMA foreign_keys=OFF;");
        }
    }

    public void createTables(SQLiteDatabase db) {

        // 1. global tables
        String[] create_global_tables = new String[]{
                CREATE_TABLE_tblFREQUENCY, CREATE_TABLE_tblPERIOD,
                CREATE_TABLE_tblFISCALYEAR
        };

        // 2. user access control tables
        String[] create_brbac_tables = new String[]{
                CREATE_TABLE_tblADDRESS, CREATE_TABLE_tblORGANIZATION,
                CREATE_TABLE_tblFUNDER, CREATE_TABLE_tblBENEFICIARY,
                CREATE_TABLE_tblIMPLEMENTINGAGENCY,CREATE_TABLE_tblVALUE,
                CREATE_TABLE_tblUSER, CREATE_TABLE_tblSESSION,
                CREATE_TABLE_tblROLE, CREATE_TABLE_tblMENU,
                CREATE_TABLE_tblPRIVILEGE, CREATE_TABLE_tblENTITY,
                CREATE_TABLE_tblOPERATION, CREATE_TABLE_tblSTATUS,
                CREATE_TABLE_tblORG_ADDRESS, CREATE_TABLE_tblUSER_ADDRESS,
                CREATE_TABLE_tblUSER_ROLE, CREATE_TABLE_tblSESSION_ROLE,
                CREATE_TABLE_tblMENU_ROLE, CREATE_TABLE_tblPERMISSION,
                CREATE_TABLE_tblPRIV_STATUS, CREATE_TABLE_tblSETTING,
                CREATE_TABLE_tblNOTIFICATION, CREATE_TABLE_tblSUBSCRIBER,
                CREATE_TABLE_tblPUBLISHER, CREATE_TABLE_tblNOTIFY_SETTING,
                CREATE_TABLE_tblACTIVITYLOG
        };

        /* 3. logframe tables */
        String[] create_logframe_tables = new String[]{
                CREATE_TABLE_tblLOGFRAME, CREATE_TABLE_tblLOGFRAMETREE,
                CREATE_TABLE_tblIMPACT, CREATE_TABLE_tblOUTCOME,
                CREATE_TABLE_tblOUTPUT, CREATE_TABLE_tblACTIVITYPLANNING,
                CREATE_TABLE_tblACTIVITY, CREATE_TABLE_tblINPUT,
                CREATE_TABLE_tblRESOURCETYPE, CREATE_TABLE_tblRESOURCE,
                CREATE_TABLE_tblEVALUATIONCRITERIA, CREATE_TABLE_tblQUESTIONGROUPING,
                CREATE_TABLE_tblPRIMITIVETYPE, CREATE_TABLE_tblQUESTIONTYPE,
                CREATE_TABLE_tblARRAYTYPE, CREATE_TABLE_tblMATRIXTYPE,
                CREATE_TABLE_tblQUESTION, CREATE_TABLE_tblRAID,
                CREATE_TABLE_tblOUTCOME_IMPACT, CREATE_TABLE_tblOUTPUT_OUTCOME,
                CREATE_TABLE_tblACTIVITY_OUTPUT, CREATE_TABLE_tblPRECEDINGACTIVITY,
                CREATE_TABLE_tblACTIVITYASSIGNMENT, CREATE_TABLE_tblIMPACT_QUESTION,
                CREATE_TABLE_tblOUTCOME_QUESTION, CREATE_TABLE_tblOUTPUT_QUESTION,
                CREATE_TABLE_tblACTIVITY_QUESTION, CREATE_TABLE_tblINPUT_QUESTION,
                CREATE_TABLE_tblIMPACT_RAID, CREATE_TABLE_tblOUTCOME_RAID,
                CREATE_TABLE_tblOUTPUT_RAID, CREATE_TABLE_tblACTIVITY_RAID
        };

        /* 4. annual work plan and budget (awpb) tables */
        String[] create_awpb_tables = new String[]{
                CREATE_TABLE_tblHUMAN, CREATE_TABLE_tblMATERIAL,
                CREATE_TABLE_tblINCOME, CREATE_TABLE_tblEXPENSE,
                CREATE_TABLE_tblHUMANSET, CREATE_TABLE_tblFUND,
                CREATE_TABLE_tblTASK, CREATE_TABLE_tblACTIVITYTASK,
                CREATE_TABLE_tblPRECEDINGTASK, CREATE_TABLE_tblTASK_MILESTONE,
                 CREATE_TABLE_tblMILESTONE, CREATE_TABLE_tblTASKASSIGNMENT,
                CREATE_TABLE_tblUSERCOMMENT, CREATE_TABLE_tblTIMESHEET,
                CREATE_TABLE_tblINVOICE, CREATE_TABLE_tblINVOICE_TIMESHEET,
                CREATE_TABLE_tblEXTENSION, CREATE_TABLE_tblEXT_DOC_TYPE,
                CREATE_TABLE_tblDOCUMENT, CREATE_TABLE_tblTRANSACTION,
                CREATE_TABLE_tblINTERNAL, CREATE_TABLE_tblEXTERNAL,
                CREATE_TABLE_tblJOURNAL
        };

        /* 5. risk, assumption, issue and dependency (raid) tables */
        String[] create_raid_tables = new String[]{
                CREATE_TABLE_tblRISKREGISTER, CREATE_TABLE_tblRISKLIKELIHOOD,
                CREATE_TABLE_tblRISKLIKELIHOODSET, CREATE_TABLE_tblRISKIMPACT,
                CREATE_TABLE_tblRISKIMPACTSET, CREATE_TABLE_tblRISKCRITERIA,
                CREATE_TABLE_tblRISKCRITERIASET, CREATE_TABLE_tblRISK,
                CREATE_TABLE_tblRISKROOTCAUSE, CREATE_TABLE_tblRISKCONSEQUENCE,
                CREATE_TABLE_tblCURRENTCONTROL, CREATE_TABLE_tblADDITIONALCONTROL,
                CREATE_TABLE_tblRISKMILESTONE, CREATE_TABLE_tblRISKANALYSIS,
                CREATE_TABLE_tblRISKPLAN, CREATE_TABLE_tblRISKACTIONTYPE,
                CREATE_TABLE_tblRISKACTION
        };

        /* 6. evaluation tables */
        String[] create_evaluation_tables = new String[]{
                CREATE_TABLE_tblARRAYCHOICE, CREATE_TABLE_tblARRAYCHOICESET,
                CREATE_TABLE_tblROWOPTION, CREATE_TABLE_tblCOLOPTION,
                CREATE_TABLE_tblMATRIXCHOICE, CREATE_TABLE_tblMATRIXCHOICESET,
                CREATE_TABLE_tblEVALUATIONTYPE, CREATE_TABLE_tblQUESTIONNAIRE,
                CREATE_TABLE_tblQUESTION_QUESTIONNAIRE, CREATE_TABLE_tblCONDITIONAL_ORDER,
                CREATE_TABLE_tblQUESTIONNAIRE_USER, CREATE_TABLE_tblERESPONSE,
                CREATE_TABLE_tblNUMERICRESPONSE, CREATE_TABLE_tblTEXTRESPONSE,
                CREATE_TABLE_tblDATERESPONSE, CREATE_TABLE_tblARRAYRESPONSE,
                CREATE_TABLE_tblMATRIXRESPONSE
        };

        /* 7. monitoring tables */
        String[] create_monitoring_tables = new String[]{
                CREATE_TABLE_tblQUESTION_INDICATOR, CREATE_TABLE_tblINDICATOR,
                CREATE_TABLE_tblMOV, CREATE_TABLE_tblMETHOD,
                CREATE_TABLE_tblUNIT, CREATE_TABLE_tblINDICATORTYPE,
                CREATE_TABLE_tblQUANTITATIVE, CREATE_TABLE_tblQUALITATIVE,
                CREATE_TABLE_tblDATACOLLECTOR, CREATE_TABLE_tblINDICATORMILESTONE,
                CREATE_TABLE_tblINDICATOR_MOV, CREATE_TABLE_tblINDICATOR_METHOD,
                CREATE_TABLE_tblQUALITATIVECHOICE, CREATE_TABLE_tblQUALITATIVECHOICESET,
                CREATE_TABLE_tblMRESPONSE, CREATE_TABLE_tblQUANTITATIVERESPONSE,
                CREATE_TABLE_tblQUALITATIVERESPONSE
        };

        db.beginTransaction();
        try {
            for (String create_global_table : create_global_tables) {
                db.execSQL(create_global_table);
            }
            for (String create_brbac_table : create_brbac_tables) {
                db.execSQL(create_brbac_table);
            }
            for (String create_logframe_table : create_logframe_tables) {
                db.execSQL(create_logframe_table);
            }
            for (String create_awpb_table : create_awpb_tables) {
                db.execSQL(create_awpb_table);
            }
            for (String create_raid_table : create_raid_tables) {
                db.execSQL(create_raid_table);
            }
            for (String create_evaluation_table : create_evaluation_tables) {
                db.execSQL(create_evaluation_table);
            }
            for (String create_monitoring_table : create_monitoring_tables) {
                db.execSQL(create_monitoring_table);
            }
            db.setTransactionSuccessful();
        } catch (SQLException e) {
            Log.e("Error onCreate Tables", e.toString());
            throw e;
        } finally {
            db.endTransaction();
        }
    }

    public void dropTables(SQLiteDatabase db) {

        /** drop global tables **/
        String[] global_tables = new String[]{
                TABLE_tblFREQUENCY, TABLE_tblPERIOD, TABLE_tblFISCALYEAR
        };

        /** drop bitwise role based access control tables **/
        String[] brbac_tables = new String[]{
                TABLE_tblADDRESS, TABLE_tblORGANIZATION, TABLE_tblFUNDER,
                TABLE_tblBENEFICIARY, TABLE_tblIMPLEMENTINGAGENCY, TABLE_tblVALUE,
                TABLE_tblUSER, TABLE_tblSESSION, TABLE_tblROLE,
                TABLE_tblMENU, TABLE_tblPRIVILEGE, TABLE_tblENTITY,
                TABLE_tblOPERATION, TABLE_tblSTATUS, TABLE_tblORG_ADDRESS,
                TABLE_tblUSER_ADDRESS, TABLE_tblUSER_ROLE, TABLE_tblSESSION_ROLE,
                TABLE_tblMENU_ROLE, TABLE_tblPERMISSION, TABLE_tblPRIV_STATUS,
                TABLE_tblSETTING, TABLE_tblNOTIFICATION, TABLE_tblSUBSCRIBER,
                TABLE_tblPUBLISHER, TABLE_tblNOTIFY_SETTING, TABLE_tblACTIVITYLOG
        };

        /** drop logframe tables **/
        String[] logframe_tables = new String[]{
                TABLE_tblLOGFRAME, TABLE_tblLOGFRAMETREE, TABLE_tblIMPACT,
                TABLE_tblOUTCOME, TABLE_tblOUTPUT, TABLE_tblACTIVITYPLANNING,
                TABLE_tblACTIVITY, TABLE_tblINPUT, TABLE_tblRESOURCETYPE,
                TABLE_tblRESOURCE, TABLE_tblEVALUATIONCRITERIA, TABLE_tblQUESTIONGROUPING,
                TABLE_tblPRIMITIVETYPE, TABLE_tblQUESTIONTYPE, TABLE_tblARRAYTYPE,
                TABLE_tblMATRIXTYPE, TABLE_tblQUESTION, TABLE_tblRAID,
                TABLE_tblOUTCOME_IMPACT, TABLE_tblOUTPUT_OUTCOME, TABLE_tblACTIVITY_OUTPUT,
                TABLE_tblPRECEDINGACTIVITY, TABLE_tblACTIVITYASSIGNMENT, TABLE_tblIMPACT_QUESTION,
                TABLE_tblOUTCOME_QUESTION, TABLE_tblOUTPUT_QUESTION, TABLE_tblACTIVITY_QUESTION,
                TABLE_tblINPUT_QUESTION, TABLE_tblIMPACT_RAID, TABLE_tblOUTCOME_RAID,
                TABLE_tblOUTPUT_RAID, TABLE_tblACTIVITY_RAID
        };

        /** drop awpb tables **/
        String[] awpb_tables = new String[]{
                TABLE_tblHUMAN, TABLE_tblMATERIAL, TABLE_tblINCOME, TABLE_tblEXPENSE,
                TABLE_tblHUMANSET, TABLE_tblFUND, TABLE_tblTASK, TABLE_tblACTIVITYTASK,
                TABLE_tblPRECEDINGTASK, TABLE_tblTASK_MILESTONE, TABLE_tblMILESTONE,
                TABLE_tblTASKASSIGNMENT, TABLE_tblUSERCOMMENT, TABLE_tblTIMESHEET,
                TABLE_tblINVOICE, TABLE_tblINVOICE_TIMESHEET, TABLE_tblEXTENSION,
                TABLE_tblEXT_DOC_TYPE, TABLE_tblDOCUMENT, TABLE_tblTRANSACTION,
                TABLE_tblINTERNAL, TABLE_tblEXTERNAL, TABLE_tblJOURNAL
        };

        /** drop raid tables **/
        String[] raid_tables = new String[]{
                TABLE_tblRISKREGISTER, TABLE_tblRISKLIKELIHOOD, TABLE_tblRISKLIKELIHOODSET,
                TABLE_tblRISKIMPACT, TABLE_tblRISKIMPACTSET, TABLE_tblRISKCRITERIA,
                TABLE_tblRISKCRITERIASET, TABLE_tblRISK, TABLE_tblRISKROOTCAUSE,
                TABLE_tblRISKCONSEQUENCE, TABLE_tblCURRENTCONTROL, TABLE_tblADDITIONALCONTROL,
                TABLE_tblRISKMILESTONE, TABLE_tblRISKANALYSIS, TABLE_tblRISKPLAN,
                TABLE_tblRISKACTIONTYPE, TABLE_tblRISKACTION
        };

        /** drop evaluation tables **/
        String[] evaluation_tables = new String[]{
                TABLE_tblARRAYCHOICE, TABLE_tblARRAYCHOICESET, TABLE_tblROWOPTION,
                TABLE_tblCOLOPTION, TABLE_tblMATRIXCHOICE, TABLE_tblMATRIXCHOICESET,
                TABLE_tblEVALUATIONTYPE, TABLE_tblQUESTIONNAIRE, TABLE_tblQUESTION_QUESTIONNAIRE,
                TABLE_tblCONDITIONAL_ORDER, TABLE_tblQUESTIONNAIRE_USER, TABLE_tblERESPONSE,
                TABLE_tblNUMERICRESPONSE, TABLE_tblTEXTRESPONSE, TABLE_tblDATERESPONSE,
                TABLE_tblARRAYRESPONSE, TABLE_tblMATRIXRESPONSE
        };

        /** drop monitoring tables **/
        String[] monitoring_tables = new String[]{
                TABLE_tblQUESTION_INDICATOR, TABLE_tblINDICATOR, TABLE_tblMOV,
                TABLE_tblMETHOD, TABLE_tblUNIT, TABLE_tblINDICATORTYPE,
                TABLE_tblQUANTITATIVE, TABLE_tblQUALITATIVE, TABLE_tblDATACOLLECTOR,
                TABLE_tblINDICATORMILESTONE, TABLE_tblINDICATOR_MOV, TABLE_tblINDICATOR_METHOD,
                TABLE_tblQUALITATIVECHOICE, TABLE_tblQUALITATIVECHOICESET, TABLE_tblMRESPONSE,
                TABLE_tblQUANTITATIVERESPONSE, TABLE_tblQUALITATIVERESPONSE
        };

        db.beginTransaction();
        try {
            for (String global_table : global_tables) {
                db.execSQL("DROP TABLE IF EXISTS " + global_table);
            }
            for (String brbac_table : brbac_tables) {
                db.execSQL("DROP TABLE IF EXISTS " + brbac_table);
            }
            for (String logframe_table : logframe_tables) {
                db.execSQL("DROP TABLE IF EXISTS " + logframe_table);
            }
            for (String awpb_table : awpb_tables) {
                db.execSQL("DROP TABLE IF EXISTS " + awpb_table);
            }
            for (String raid_table : raid_tables) {
                db.execSQL("DROP TABLE IF EXISTS " + raid_table);
            }
            for (String evaluation_table : evaluation_tables) {
                db.execSQL("DROP TABLE IF EXISTS " + evaluation_table);
            }
            for (String monitoring_table : monitoring_tables) {
                db.execSQL("DROP TABLE IF EXISTS " + monitoring_table);
            }
            db.setTransactionSuccessful();
        } catch (SQLException e) {
            Log.e("Error onUpgrade Tables", e.toString());
        } finally {
            db.endTransaction();
        }
    }

    /*#$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$ TO REMOVE $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$#*/

    /**
     * Bitwise Role Based Access Control (BRBAC) tables
     * public static final String TABLE_ADDRESS            = "ADDRESS";          1
     * public static final String TABLE_ORGANIZATION       = "ORGANIZATION";     2
     * public static final String TABLE_VALUE              = "VALUE";            3
     * public static final String TABLE_USER               = "USER";             4
     * public static final String TABLE_SESSION            = "SESSION";          5
     * public static final String TABLE_ROLE               = "ROLE";             6
     * public static final String TABLE_MENU               = "MENU";             7
     * public static final String TABLE_PRIVILEGE          = "PRIVILEGE";        8
     * public static final String TABLE_ENTITY             = "ENTITY";           9
     * public static final String TABLE_OPERATION          = "OPERATION";        10
     * public static final String TABLE_STATUS             = "STATUS";           11
     * public static final String TABLE_USER_ROLE          = "USER_ROLE";        12
     * public static final String TABLE_SESSION_ROLE       = "SESSION_ROLE";     13
     * public static final String TABLE_MENU_ROLE          = "MENU_ROLE";        14
     * public static final String TABLE_PRIVILEGE_ROLE     = "PRIVILEGE_ROLE";   15
     * //public static final String TABLE_OPERATION_STATUS = "OPERATION_STATUS"; 16
     * public static final String TABLE_PERMISSION         = "PERMISSION";       17
     **/

    //##### End of BRBAC tables #####

    public static final String TABLE_TYPE = "TYPE";   /* 5 */
    public static final String TABLE_ACTIVITYLOG = "ACTIVITYLOG";  /* 7 */
    public static final String TABLE_NOTIFICATION = "NOTIFICATION"; /* 9 */

    // dimension Tables --
    public static final String TABLE_DATE = "DATE";           /* 1 */
    public static final String TABLE_UNIT = "UNIT";           /* 3 */
    public static final String TABLE_SCALE = "SCALE";          /* 4 */
    public static final String TABLE_CATEGORY = "CATEGORY";       /* 5 */
    public static final String TABLE_RISKMAP = "RISKMAP";        /* 6 */
    public static final String TABLE_RISKLIKELIHOOD = "RISKLIKELIHOOD"; /* 7 */
    public static final String TABLE_RISKIMPACT = "RISKIMPACT";     /* 8 */

    // relation tables
    public static final String TABLE_OVERALLAIM = "OVERALLAIM";   /* 12 */
    public static final String TABLE_PROJECT = "PROJECT";      /* 13 */
    public static final String TABLE_SPECIFICAIM = "SPECIFICAIM";  /* 14 */
    public static final String TABLE_OUTCOME = "OUTCOME";      /* 15 */
    public static final String TABLE_OBJECTIVE = "OBJECTIVE";    /* 16 */
    public static final String TABLE_OUTPUT = "OUTPUT";       /* 17 */
    public static final String TABLE_ACTIVITY = "ACTIVITY";     /* 18 */
    public static final String TABLE_INDICATOR = "INDICATOR";    /* 19 */
    public static final String TABLE_QUANTITATIVEINDICATOR = "QUANTITATIVEINDICATOR";/* 20 */
    public static final String TABLE_QUALITATIVEINDICATOR = "QUALITATIVEINDICATOR"; /* 21 */
    public static final String TABLE_HYBRIDINDICATOR = "HYBRIDINDICATOR";      /* 22 */
    public static final String TABLE_QUANTITATIVETARGET = "QUANTITATIVETARGET";   /* 23 */
    public static final String TABLE_QUALITATIVETARGET = "QUALITATIVETARGET";    /* 24 */
    public static final String TABLE_HYBRIDTARGET = "HYBRIDTARGET";         /* 25 */
    public static final String TABLE_MOV = "MOV";          /* 19 */
    public static final String TABLE_RISKREGISTER = "RISKREGISTER"; /* 20 */
    public static final String TABLE_RISK = "RISK";         /* 20 */
    public static final String TABLE_RISKCONSEQUENCE = "RISKCONSEQUENCE"; /* 20 */
    public static final String TABLE_RISKMITIGATION = "RISKMITIGATION"; /* 20 */
    public static final String TABLE_RISKMANAGEMENT = "RISKMANAGEMENT"; /* 20 */
    private static final String TABLE_WORKPLAN = "WORKPLAN";     /* 21 */
    private static final String TABLE_RESOURCE = "RESOURCE";     /* 22 */
    private static final String TABLE_ACCOUNTTYPE = "ACCOUNTTYPE";  /* 23 */
    private static final String TABLE_BUDGET = "BUDGET";       /* 24 */
    private static final String TABLE_CASHFLOW = "CASHFLOW";     /* 25 */
    private static final String TABLE_MONITORING = "MONITORING";   /* 26 */
    private static final String TABLE_EVALUATION = "EVALUATION";   /* 27 */
    private static final String TABLE_CRITERION = "CRITERION";    /* 28 */
    private static final String TABLE_QUESTION = "QUESTION";     /* 29 */

    // junction tables
    public static final String TABLE_USER_NOTIFICATION = "USER_NOTIFICATION";    /* 31 */
    public static final String TABLE_ROLE_OPERATION_OBJECT_TYPE_STATUS = "ROLE_OPERATION_OBJECT_TYPE_STATUS";   /* 32 */
    private static final String TABLE_OVERALLAIM_PROJECT = "OVERALLAIM_PROJECT";   /* 33 */
    private static final String TABLE_PROJECT_STATUS = "PROJECT_STATUS";       /* 34 */
    public static final String TABLE_PROJECT_OUTCOME = "PROJECT_OUTCOME";      /* 35 */
    public static final String TABLE_OUTCOME_OUTPUT = "OUTCOME_OUTPUT";       /* 36 */
    public static final String TABLE_OUTPUT_ACTIVITY = "OUTPUT_ACTIVITY";      /* 37 */
    private static final String TABLE_OVERALLAIM_INDICATOR = "OVERALLAIM_INDICATOR"; /* 38 */
    private static final String TABLE_OUTCOME_INDICATOR = "OUTCOME_INDICATOR";    /* 39 */
    private static final String TABLE_OUTPUT_INDICATOR = "OUTPUT_INDICATOR";     /* 40 */
    public static final String TABLE_MOV_INDICATOR = "MOV_INDICATOR";        /* 41 */
    public static final String TABLE_OVERALLAIM_RISK = "OVERALLAIM_RISK";      /* 42 */
    public static final String TABLE_OUTCOME_RISK = "OUTCOME_RISK";         /* 43 */
    public static final String TABLE_OUTPUT_RISK = "OUTPUT_RISK";          /* 44 */
    public static final String TABLE_ACTIVITY_RISK = "ACTIVITY_RISK";        /* 45 */
    private static final String TABLE_OVERALLAIM_CRITERION = "OVERALLAIM_CRITERION"; /* 46 */
    private static final String TABLE_OUTCOME_CRITERION = "OUTCOME_CRITERION ";   /* 47 */
    private static final String TABLE_OUTPUT_CRITERION = "OUTPUT_CRITERION";     /* 48 */
    private static final String TABLE_ACTIVITY_CRITERION = "ACTIVITY_CRITERION";   /* 49 */
    private static final String TABLE_QUESTION_CATEGORY = "QUESTION_CATEGORY";    /* 50 */
    private static final String TABLE_INDICATOR_QUESTION = "INDICATOR_QUESTION";   /* 51 */
    private static final String TABLE_OVERALLAIM_CRITERION_QUESTION = "OVERALLAIM_CRITERION_QUESTION";/* 52 */
    private static final String TABLE_OUTCOME_CRITERION_QUESTION = "OUTCOME_CRITERION_QUESTION"; /* 53 */
    private static final String TABLE_OUTPUT_CRITERION_QUESTION = "OUTPUT_CRITERION_QUESTION"; /* 54 */
    private static final String TABLE_ACTIVITY_CRITERION_QUESTION = "ACTIVITY_CRITERION_QUESTION";/* 55 */
    private static final String TABLE_MONITORING_QUESTIONNAIRE = "MONITORING_QUESTIONNAIRE";/* 56 */
    private static final String TABLE_EVALUATION_QUESTIONNAIRE = "EVALUATION_QUESTIONNAIRE";/* 57 */
    private static final String TABLE_MONITORING_DATACOLLECTION = "MONITORING_RESULT";/* 58 */
    private static final String TABLE_EVALUATION_DATACOLLECTION = "EVALUATION_RESULT";/* 59 */


    // TYPE table - column names
    public static final String KEY_TYPE_ID = "_id";
    public static final String KEY_TYPE_OWNER_ID = "_id_owner";
    public static final String KEY_TYPE_GROUP_BITS = "_bits_group";
    public static final String KEY_TYPE_PERMS_BITS = "_bits_perms";
    public static final String KEY_TYPE_TYPE_BITS = "_bits_type";
    public static final String KEY_TYPE_STATUS_BITS = "_bits_status";
    public static final String KEY_TYPE_NAME = "name";
    public static final String KEY_TYPE_DESCRIPTION = "description";
    public static final String KEY_TYPE_DATE = "date";

    // TYPE table - create statement
    public static final String CREATE_TABLE_TYPE = "CREATE TABLE " + TABLE_TYPE + "("
            + KEY_TYPE_ID + " INTEGER PRIMARY KEY, "
            + KEY_TYPE_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_TYPE_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_TYPE_PERMS_BITS + " INTEGER NOT NULL DEFAULT 500, "
            + KEY_TYPE_TYPE_BITS + " INTEGER NOT NULL DEFAULT 0, "
            + KEY_TYPE_STATUS_BITS + " INTEGER NOT NULL DEFAULT 0, "
            + KEY_TYPE_NAME + " TEXT, "
            + KEY_TYPE_DESCRIPTION + " TEXT, "
            + KEY_TYPE_DATE + " DATE " + ")";


    // NOTIFICATION Table - column names
    public static final String KEY_NOTIFICATION_ID = "_id";
    public static final String KEY_NOTIFICATION_OWNER_ID = "_id_owner";
    public static final String KEY_NOTIFICATION_GROUP_BITS = "_bits_group";
    public static final String KEY_NOTIFICATION_PERMS_BITS = "_bits_perms";
    public static final String KEY_NOTIFICATION_TYPE_BITS = "_bits_type";
    public static final String KEY_NOTIFICATION_STATUS_BITS = "_bits_status";
    public static final String KEY_NOTIFICATION_NAME = "name";
    public static final String KEY_NOTIFICATION_DESCRIPTION = "description";
    public static final String KEY_NOTIFICATION_DATE = "date";

    // ACTION table - create statement
    public static final String CREATE_TABLE_NOTIFICATION = "CREATE TABLE " + TABLE_NOTIFICATION + "("
            + KEY_NOTIFICATION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
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
    public static final String KEY_ACTIVITYLOG_OWNER_ID = "_id_owner";
    public static final String KEY_ACTIVITYLOG_NAME = "name";
    public static final String KEY_ACTIVITYLOG_DESCRIPTION = "description";
    public static final String KEY_ACTIVITYLOG_DATE = "date";

    // ACTIVITYLOG table - create statement
    public static final String CREATE_TABLE_ACTIVITYLOG = "CREATE TABLE " + TABLE_ACTIVITYLOG + "("
            + KEY_USER_ID + " INTEGER NOT NULL, "
            + KEY_ACTIVITYLOG_TIMESTAMP_ID + " INTEGER NOT NULL, "
            + KEY_ACTIVITYLOG_OWNER_ID + " INTEGER NOT NULL, "
            + KEY_ACTIVITYLOG_NAME + " TEXT, "
            + KEY_ACTIVITYLOG_DESCRIPTION + " TEXT, "
            + KEY_ACTIVITYLOG_DATE + " DATE, "
            + "PRIMARY KEY (" + KEY_USER_ID + " ," + KEY_ACTIVITYLOG_TIMESTAMP_ID + " ));";

    // CATEGORY table - column names
    private static final String KEY_CATEGORY_ID = "_id";
    private static final String KEY_CATEGORY_OWNER_ID = "_id_owner";
    private static final String KEY_CATEGORY_NAME = "name";
    private static final String KEY_CATEGORY_DATE = "date";

    // CATEGORY table - create statement
    public static final String CREATE_TABLE_CATEGORY = "CREATE TABLE " + TABLE_CATEGORY + "("
            + KEY_CATEGORY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_CATEGORY_OWNER_ID + " INTEGER NOT NULL, "
            + KEY_CATEGORY_NAME + " TEXT, "
            + KEY_CATEGORY_DATE + " DATE " + ")";

    // DATE table - column names
    public static final String KEY_DATE_ID = "_id";
    public static final String KEY_DATE_OWNER_ID = "_id_owner";
    public static final String KEY_DATE_YEAR = "year";
    public static final String KEY_DATE_MONTH = "month";
    public static final String KEY_DATE_QUARTER = "quarter";
    public static final String KEY_DATE_DAY_MONTH = "day_month";
    public static final String KEY_DATE_DAY_WEEK = "day_week";
    public static final String KEY_DATE_DAY_YEAR = "day_year";
    public static final String KEY_DATE_DAY_WEEK_MONTH = "day_week_month";
    public static final String KEY_DATE_WEEK_YEAR = "week_year";
    public static final String KEY_DATE_WEEK_MONTH = "week_month";
    public static final String KEY_DATE_NAME_QUARTER = "name_quarter";
    public static final String KEY_DATE_NAME_MONTH = "name_month";
    public static final String KEY_DATE_NAME_WEEK_DAY = "name_week_day";
    public static final String KEY_DATE_DATE = "date";

    // DATE table - create statement
    public static final String CREATE_TABLE_DATE = "CREATE TABLE " + TABLE_DATE + "("
            + KEY_DATE_ID + " INTEGER PRIMARY KEY, "
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
    public static final String KEY_UNIT_ID = "_id";
    public static final String KEY_UNIT_OWNER_ID = "_id_owner";
    public static final String KEY_UNIT_NAME = "name";
    public static final String KEY_UNIT_DATE = "date";

    // UNIT table - create statement
    public static final String CREATE_TABLE_UNIT = "CREATE TABLE " + TABLE_UNIT + "("
            + KEY_UNIT_ID + " INTEGER PRIMARY KEY, "
            + KEY_UNIT_OWNER_ID + " INTEGER NOT NULL, "
            + KEY_UNIT_NAME + " TEXT, "
            + KEY_UNIT_DATE + " DATE " + ")";

    // SCALE table - column names
    public static final String KEY_SCALE_ID = "_id";
    public static final String KEY_SCALE_OWNER_ID = "_id_owner";
    public static final String KEY_SCALE_START = "scale_start";
    public static final String KEY_SCALE_END = "scale_end";
    public static final String KEY_SCALE_NAME = "name";
    public static final String KEY_SCALE_DESCRIPTION = "description";
    public static final String KEY_SCALE_DATE = "date";

    // SCALE table - create statement
    public static final String CREATE_TABLE_SCALE = "CREATE TABLE " + TABLE_SCALE + "("
            + KEY_SCALE_ID + " INTEGER PRIMARY KEY, "
            + KEY_SCALE_OWNER_ID + " INTEGER NOT NULL, "
            + KEY_SCALE_START + " INTEGER, "
            + KEY_SCALE_END + " INTEGER, "
            + KEY_SCALE_NAME + " TEXT, "
            + KEY_SCALE_DESCRIPTION + " TEXT, "
            + KEY_SCALE_DATE + " DATE " + ")";

//=== END OF DIMENTIONS


    // OVERALLAIM table - column names
    public static final String KEY_OVERALLAIM_ID = "_id";
    public static final String KEY_OVERALLAIM_OWNER_ID = "_id_owner";
    public static final String KEY_OVERALLAIM_NAME = "name";
    public static final String KEY_OVERALLAIM_DESCRIPTION = "description";
    public static final String KEY_OVERALLAIM_DATE = "date";

    // OVERALLAIM table - create statement
    public static final String CREATE_TABLE_OVERALLAIM = "CREATE TABLE " + TABLE_OVERALLAIM + "("
            + KEY_OVERALLAIM_ID + " INTEGER PRIMARY KEY, "
            + KEY_ORGANIZATION_FK_ID + " INTEGER NOT NULL, "
            + KEY_OVERALLAIM_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_OVERALLAIM_NAME + " TEXT, "
            + KEY_OVERALLAIM_DESCRIPTION + " TEXT, "
            + KEY_OVERALLAIM_DATE + " DATE, "
            + "FOREIGN KEY (" + KEY_ORGANIZATION_FK_ID + ") REFERENCES " + TABLE_tblORGANIZATION + "(" + KEY_ORGANIZATION_ID + ") "
            + "ON DELETE CASCADE);";

    // SPECIFICAIM table - column names
    public static final String KEY_SPECIFICAIM_ID = "_id";
    public static final String KEY_OVERALLAIM_FK_ID = "_id_overallaim_fk";
    public static final String KEY_SPECIFICAIM_OWNER_ID = "_id_owner";
    public static final String KEY_SPECIFICAIM_NAME = "name";
    public static final String KEY_SPECIFICAIM_DESCRIPTION = "description";
    public static final String KEY_SPECIFICAIM_DATE = "date";

    // SPECIFICAIM table - create statement
    public static final String CREATE_TABLE_SPECIFICAIM = "CREATE TABLE " + TABLE_SPECIFICAIM + "("
            + KEY_SPECIFICAIM_ID + " INTEGER PRIMARY KEY, "
            + KEY_OVERALLAIM_FK_ID + " INTEGER NOT NULL, "
            + KEY_SPECIFICAIM_OWNER_ID + " INTEGER NOT NULL, "
            + KEY_SPECIFICAIM_NAME + " TEXT, "
            + KEY_SPECIFICAIM_DESCRIPTION + " TEXT, "
            + KEY_SPECIFICAIM_DATE + " DATE, "
            + "FOREIGN KEY (" + KEY_OVERALLAIM_FK_ID + ") REFERENCES " + TABLE_OVERALLAIM + "(" + KEY_OVERALLAIM_ID + ") ON DELETE CASCADE);";

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
            + "FOREIGN KEY (" + KEY_OVERALLAIM_FK_ID + ") REFERENCES " + TABLE_OVERALLAIM + "(" + KEY_OVERALLAIM_ID + ") ON DELETE CASCADE, "
            + "FOREIGN KEY (" + KEY_SPECIFICAIM_FK_ID + ") REFERENCES " + TABLE_SPECIFICAIM + "(" + KEY_SPECIFICAIM_ID + ") ON DELETE CASCADE);";

    // OUTCOME table - column names
    public static final String KEY_OUTCOME_ID = "_id";
    public static final String KEY_OUTCOME_OWNER_ID = "_id_owner";
    public static final String KEY_OUTCOME_NAME = "name";
    public static final String KEY_OUTCOME_DESCRIPTION = "description";
    public static final String KEY_OUTCOME_DATE = "date";

    // OUTCOME table - create statement
    public static final String CREATE_TABLE_OUTCOME = "CREATE TABLE " + TABLE_OUTCOME + "("
            + KEY_OUTCOME_ID + " INTEGER PRIMARY KEY, "
            + KEY_OUTCOME_OWNER_ID + " INTEGER NOT NULL, "
            + KEY_OUTCOME_NAME + " TEXT, "
            + KEY_OUTCOME_DESCRIPTION + " TEXT, "
            + KEY_OUTCOME_DATE + " DATE)";

    // OBJECTIVE table - column names
    public static final String KEY_OBJECTIVE_ID = "_id";
    public static final String KEY_PROJECT_FK_ID = "_id_project_fk";
    public static final String KEY_OBJECTIVE_OWNER_ID = "_id_owner";
    public static final String KEY_OBJECTIVE_NAME = "name";
    public static final String KEY_OBJECTIVE_DESCRIPTION = "description";
    public static final String KEY_OBJECTIVE_DATE = "date";

    // OBJECTIVE table - create statement
    public static final String CREATE_TABLE_OBJECTIVE = "CREATE TABLE " + TABLE_OBJECTIVE + "("
            + KEY_OBJECTIVE_ID + " INTEGER PRIMARY KEY, "
            + KEY_PROJECT_FK_ID + " INTEGER NOT NULL, "
            + KEY_OBJECTIVE_OWNER_ID + " INTEGER NOT NULL, "
            + KEY_OBJECTIVE_NAME + " TEXT, "
            + KEY_OBJECTIVE_DESCRIPTION + " TEXT, "
            + KEY_OBJECTIVE_DATE + " DATE, "
            + "FOREIGN KEY (" + KEY_PROJECT_FK_ID + ") REFERENCES " + TABLE_PROJECT + "(" + KEY_PROJECT_ID + ") ON DELETE CASCADE);";

    // OUTPUT table - column names
    public static final String KEY_OUTPUT_ID = "_id";
    public static final String KEY_OBJECTIVE_FK_ID = "_id_objective_fk";
    public static final String KEY_OUTPUT_OWNER_ID = "_id_owner";
    public static final String KEY_OUTPUT_NAME = "name";
    public static final String KEY_OUTPUT_DESCRIPTION = "description";
    public static final String KEY_OUTPUT_DATE = "date";

    // OUTPUT table - create statement
    public static final String CREATE_TABLE_OUTPUT = "CREATE TABLE " + TABLE_OUTPUT + "("
            + KEY_OUTPUT_ID + " INTEGER PRIMARY KEY, "
            + KEY_OBJECTIVE_FK_ID + " INTEGER, "
            + KEY_OUTPUT_OWNER_ID + " INTEGER NOT NULL, "
            + KEY_OUTPUT_NAME + " TEXT, "
            + KEY_OUTPUT_DESCRIPTION + " TEXT, "
            + KEY_OUTPUT_DATE + " DATE, "
            + "FOREIGN KEY (" + KEY_OBJECTIVE_FK_ID + ") REFERENCES " + TABLE_OBJECTIVE + "(" + KEY_OBJECTIVE_ID + ") ON DELETE CASCADE);";

    // ACTIVITY table - column names
    public static final String KEY_ACTIVITY_ID = "_id";
    public static final String KEY_ACTIVITY_OWNER_ID = "_id_owner";
    public static final String KEY_ACTIVITY_NAME = "name";
    public static final String KEY_ACTIVITY_DESCRIPTION = "description";
    public static final String KEY_ACTIVITY_DATE = "date";

    // ACTIVITY table - create statement
    public static final String CREATE_TABLE_ACTIVITY = "CREATE TABLE " + TABLE_ACTIVITY + "("
            + KEY_ACTIVITY_ID + " INTEGER PRIMARY KEY, "
            + KEY_ACTIVITY_OWNER_ID + " INTEGER NOT NULL, "
            + KEY_ACTIVITY_NAME + " TEXT, "
            + KEY_ACTIVITY_DESCRIPTION + " TEXT, "
            + KEY_ACTIVITY_DATE + " DATE " + ")";

    // INDICATOR table - column names
    public static final String KEY_INDICATOR_ID = "_id";
    public static final String KEY_INDICATOR_OWNER_ID = "_id_owner";
    //public static final String KEY_STATUS_FK_ID          = "_id_status_fk";
    public static final String KEY_INDICATOR_NAME = "name";
    public static final String KEY_INDICATOR_DESCRIPTION = "description";
    public static final String KEY_INDICATOR_TYPE = "type";
    public static final String KEY_INDICATOR_DATE = "date";

    // INDICATOR table - create statement
    public static final String CREATE_TABLE_INDICATOR = "CREATE TABLE " + TABLE_INDICATOR + "("
            + KEY_INDICATOR_ID + " INTEGER PRIMARY KEY, "
            + KEY_INDICATOR_OWNER_ID + " INTEGER NOT NULL, "
            + KEY_STATUS_FK_ID + " INTEGER NOT NULL, "
            + KEY_INDICATOR_NAME + " TEXT, "
            + KEY_INDICATOR_DESCRIPTION + " TEXT, "
            + KEY_INDICATOR_TYPE + " INTEGER NOT NULL, "
            + KEY_INDICATOR_DATE + " DATE " + ")";


    public static final String KEY_QUANTITATIVE_OWNER_ID = "_id_owner";
    public static final String KEY_QUANTITATIVE_BASELINE = "baseline";
    public static final String KEY_QUANTITATIVE_BASELINE_DATE = "date";

    // QUNTITATIVE INDICATOR - create statement
    public static final String CREATE_TABLE_QUANTITATIVEINDICATOR = "CREATE TABLE " + TABLE_QUANTITATIVEINDICATOR + "("
            + KEY_INDICATOR_FK_ID + " INTEGER PRIMARY KEY, "
            + KEY_QUANTITATIVE_OWNER_ID + " INTEGER NOT NULL, "
            + KEY_UNIT_FK_ID + " INTEGER , "
            + KEY_QUANTITATIVE_BASELINE + " INTEGER, "
            + KEY_QUANTITATIVE_BASELINE_DATE + " DATE, "
            + "FOREIGN KEY (" + KEY_INDICATOR_FK_ID + ") REFERENCES " + TABLE_INDICATOR + "(" + KEY_INDICATOR_ID + ") ON DELETE CASCADE, "
            + "FOREIGN KEY (" + KEY_UNIT_FK_ID + ") REFERENCES " + TABLE_UNIT + "(" + KEY_UNIT_ID + ") ON DELETE CASCADE);";

    // QUALITATIVE INDICATOR - column names
    public static final String KEY_QUALITATIVE_OWNER_ID = "_id_owner";
    public static final String KEY_QUALITATIVE_BASELINE = "baseline";
    public static final String KEY_QUALITATIVE_BASELINE_DATE = "date";

    // QUALITATIVE INDICATOR - create statement
    public static final String CREATE_TABLE_QUALITATIVEINDICATOR = "CREATE TABLE " + TABLE_QUALITATIVEINDICATOR + "("
            + KEY_INDICATOR_FK_ID + " INTEGER PRIMARY KEY, "
            + KEY_QUALITATIVE_OWNER_ID + " INTEGER NOT NULL, "
            + KEY_QUALITATIVE_BASELINE + " TEXT, "
            + KEY_QUALITATIVE_BASELINE_DATE + " DATE, "
            + "FOREIGN KEY (" + KEY_INDICATOR_FK_ID + ") REFERENCES " + TABLE_INDICATOR + "(" + KEY_INDICATOR_ID + ") ON DELETE CASCADE);";

    // HYBRID TARGET - column names
    public static final String KEY_HYBRID_OWNER_ID = "_id_owner";
    public static final String KEY_CATEGORY_FK_ID = "_id_category_fk";
    public static final String KEY_HYBRID_BASELINE = "baseline";
    public static final String KEY_HYBRID_BASELINE_DATE = "date";

    // HYBRID INDICATOR - create statement
    public static final String CREATE_TABLE_HYBRIDINDICATOR = "CREATE TABLE " + TABLE_HYBRIDINDICATOR + "("
            + KEY_INDICATOR_FK_ID + " INTEGER PRIMARY KEY, "
            + KEY_HYBRID_OWNER_ID + " INTEGER NOT NULL, "
            + KEY_CATEGORY_FK_ID + " INTEGER , "
            + KEY_HYBRID_BASELINE + " INTEGER, "
            + KEY_HYBRID_BASELINE_DATE + " DATE, "
            + "FOREIGN KEY (" + KEY_INDICATOR_FK_ID + ") REFERENCES " + TABLE_INDICATOR + "(" + KEY_INDICATOR_ID + ") ON DELETE CASCADE, "
            + "FOREIGN KEY (" + KEY_CATEGORY_FK_ID + ") REFERENCES " + TABLE_CATEGORY + "(" + KEY_CATEGORY_ID + ") ON DELETE CASCADE);";

    // QUANTITATIVE TARGET - column names
    public static final String KEY_QUANTITATIVE_ID = "_id";
    public static final String KEY_QUANTITATIVE_TARGET = "target";
    public static final String KEY_QUANTITATIVE_TARGET_DATE = "date";

    // QUANTITATIVE TARGET - create statement
    public static final String CREATE_TABLE_QUANTITATIVETARGET = "CREATE TABLE " + TABLE_QUANTITATIVETARGET + "("
            + KEY_QUANTITATIVE_ID + " INTEGER PRIMARY KEY, "
            + KEY_INDICATOR_FK_ID + " INTEGER, "
            + KEY_QUANTITATIVE_OWNER_ID + " INTEGER NOT NULL, "
            + KEY_QUANTITATIVE_TARGET + " INTEGER, "
            + KEY_QUANTITATIVE_TARGET_DATE + " DATE, "
            + "FOREIGN KEY (" + KEY_INDICATOR_FK_ID + ") REFERENCES " + TABLE_QUANTITATIVEINDICATOR + "(" + KEY_INDICATOR_FK_ID + ") ON DELETE CASCADE);";

    // QUALITATIVE TARGET - column names
    public static final String KEY_QUALITATIVE_ID = "_id";
    public static final String KEY_QUALITATIVE_TARGET = "target";
    public static final String KEY_QUALITATIVE_TARGET_DATE = "date";

    // QUALITATIVE TARGET - create statement
    public static final String CREATE_TABLE_QUALITATIVETARGET = "CREATE TABLE " + TABLE_QUALITATIVETARGET + "("
            + KEY_QUALITATIVE_ID + " INTEGER PRIMARY KEY, "
            + KEY_INDICATOR_FK_ID + " INTEGER NOT NULL, "
            + KEY_QUALITATIVE_OWNER_ID + " INTEGER NOT NULL, "
            + KEY_QUALITATIVE_TARGET + " TEXT, "
            + KEY_QUALITATIVE_TARGET_DATE + " DATE, "
            + "FOREIGN KEY (" + KEY_INDICATOR_FK_ID + ") REFERENCES " + TABLE_QUALITATIVEINDICATOR + "(" + KEY_INDICATOR_FK_ID + ") ON DELETE CASCADE);";

    // HYBRID TARGET - column names
    public static final String KEY_HYBRID_ID = "_id";
    public static final String KEY_HYBRID_TARGET = "target";
    public static final String KEY_HYBRID_TARGET_DATE = "date";

    // HYBRID INDICATOR - create statement
    public static final String CREATE_TABLE_HYBRIDTARGET = "CREATE TABLE " + TABLE_HYBRIDTARGET + "("
            + KEY_HYBRID_ID + " INTEGER PRIMARY KEY, "
            + KEY_INDICATOR_FK_ID + " INTEGER NOT NULL, "
            + KEY_HYBRID_OWNER_ID + " INTEGER NOT NULL, "
            + KEY_CATEGORY_FK_ID + " INTEGER , "
            + KEY_HYBRID_TARGET + " INTEGER, "
            + KEY_HYBRID_TARGET_DATE + " DATE, "
            + "FOREIGN KEY (" + KEY_INDICATOR_FK_ID + ") REFERENCES " + TABLE_HYBRIDINDICATOR + "(" + KEY_INDICATOR_FK_ID + ") ON DELETE CASCADE, "
            + "FOREIGN KEY (" + KEY_CATEGORY_FK_ID + ") REFERENCES " + TABLE_CATEGORY + "(" + KEY_CATEGORY_ID + ") ON DELETE CASCADE);";


    // MOV table - column names
    public static final String KEY_MOV_ID = "_id";
    public static final String KEY_MOV_OWNER_ID = "_id_owner";
    public static final String KEY_MOV_NAME = "name";
    public static final String KEY_MOV_DESCRIPTION = "description";
    public static final String KEY_MOV_DATE = "date";

    // MOV table - create statement
    public static final String CREATE_TABLE_MOV = "CREATE TABLE " + TABLE_MOV + "("
            + KEY_MOV_ID + " INTEGER PRIMARY KEY, "
            + KEY_MOV_OWNER_ID + " INTEGER NOT NULL, "
            + KEY_MOV_NAME + " TEXT, "
            + KEY_MOV_DESCRIPTION + " TEXT, "
            + KEY_MOV_DATE + " DATE " + ")";

    // RISKREGISTER table - column names
    public static final String KEY_RISKREGISTER_ID = "_id";
    public static final String KEY_RISKREGISTER_FK_ID = "_id_riskregister_fk";
    public static final String KEY_RISKREGISTER_OWNER_ID = "_id_owner";
    public static final String KEY_RISKREGISTER_NAME = "name";
    public static final String KEY_RISKREGISTER_DESCRIPTION = "description";
    public static final String KEY_RISKREGISTER_DATE = "date";

    // RISKREGISTER table - create statement
    public static final String CREATE_TABLE_RISKREGISTER = "CREATE TABLE " + TABLE_RISKREGISTER + "("
            + KEY_RISKREGISTER_ID + " INTEGER PRIMARY KEY, "
            + KEY_PROJECT_FK_ID + " INTEGER NOT NULL, "
            + KEY_RISKREGISTER_OWNER_ID + " INTEGER NOT NULL,"
            + KEY_RISKREGISTER_NAME + " TEXT, "
            + KEY_RISKREGISTER_DESCRIPTION + " TEXT, "
            + KEY_RISKREGISTER_DATE + " DATE, "
            + "FOREIGN KEY (" + KEY_PROJECT_FK_ID + ") REFERENCES " + TABLE_PROJECT + "(" + KEY_PROJECT_ID + ") ON DELETE CASCADE );";

    // RISKMAP table - column names
    public static final String KEY_RISKMAP_ID = "_id";
    public static final String KEY_RISKMAP_OWNER_ID = "_id_owner";
    public static final String KEY_RISKMAP_NAME = "name";
    public static final String KEY_RISKMAP_DESCRIPTION = "description";

    public static final String KEY_RISKMAP_DATE = "date";

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
            + "FOREIGN KEY (" + KEY_RISKREGISTER_FK_ID + ") REFERENCES " + TABLE_RISKREGISTER + "(" + KEY_RISKREGISTER_ID + ") ON DELETE CASCADE );";

    // RISKLIKELIHOOD table - column names
    public static final String KEY_RISKLIKELIHOOD_ID = "_id";
    public static final String KEY_RISKLIKELIHOOD_OWNER_ID = "_id_owner";
    public static final String KEY_RISKLIKELIHOOD_NAME = "name";
    public static final String KEY_RISKLIKELIHOOD_DESCRIPTION = "description";

    public static final String KEY_RISKLIKELIHOOD_DATE = "date";

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
    public static final String KEY_RISKIMPACT_OWNER_ID = "_id_owner";
    public static final String KEY_RISKIMPACT_NAME = "name";
    public static final String KEY_RISKIMPACT_DESCRIPTION = "description";

    public static final String KEY_RISKIMPACT_DATE = "date";

    // RISKLIKELIHOOD table - create statement
    public static final String CREATE_TABLE_RISKIMPACT = "CREATE TABLE " + TABLE_RISKIMPACT + "("
            + KEY_RISKIMPACT_ID + " INTEGER PRIMARY KEY, "
            + KEY_RISKIMPACT_OWNER_ID + " INTEGER NOT NULL, "
            + KEY_RISKIMPACT_NAME + " TEXT, "
            + KEY_RISKIMPACT_DESCRIPTION + " TEXT, "
            + KEY_RISKIMPACT_VALUE + " INTEGER, "
            + KEY_RISKIMPACT_DATE + " DATE" + ")";

    // RISK table - column names
    public static final String KEY_RISK_ID = "_id";
    public static final String KEY_RISK_OWNER_ID = "_id_owner";
    public static final String KEY_RISK_OWNERSHIP_ID = "_id_ownership";
    public static final String KEY_RISK_NAME = "name";
    public static final String KEY_RISK_DESCRIPTION = "description";
    public static final String KEY_RISK_STATUS = "status";
    public static final String KEY_RISK_LIKELIHOOD = "likelihood";
    public static final String KEY_RISK_IMPACT = "impact";
    public static final String KEY_RISK_DATE = "date";

    // RISK table - create statement
    public static final String CREATE_TABLE_RISK = "CREATE TABLE " + TABLE_RISK + "("
            + KEY_RISK_ID + " INTEGER PRIMARY KEY, "
            + KEY_RISKREGISTER_FK_ID + " INTEGER NOT NULL, "
            + KEY_RISK_OWNER_ID + " INTEGER NOT NULL, "
            + KEY_RISK_OWNERSHIP_ID + " INTEGER NOT NULL, "
            + KEY_RISK_NAME + " TEXT, "
            + KEY_RISK_DESCRIPTION + " TEXT, "
            + KEY_RISK_STATUS + " INTEGER NOT NULL, "
            + KEY_RISK_LIKELIHOOD + " INTEGER, "
            + KEY_RISK_IMPACT + " INTEGER, "
            + KEY_RISK_DATE + " DATE, "
            + "FOREIGN KEY (" + KEY_RISKREGISTER_FK_ID + ") REFERENCES " + TABLE_RISKREGISTER + "(" + KEY_RISKREGISTER_ID + ") ON DELETE CASCADE );";


    // RISKCONSEQUENCE table - column names
    public static final String KEY_RISKCONSEQUENCE_ID = "_id";

    public static final String KEY_RISKCONSEQUENCE_OWNER_ID = "_id_owner";
    public static final String KEY_RISKCONSEQUENCE_NAME = "name";
    public static final String KEY_RISKCONSEQUENCE_DESCRIPTION = "description";
    public static final String KEY_RISKCONSEQUENCE_DATE = "date";

    // RISKCONSEQUENCE table - create statement
    public static final String CREATE_TABLE_RISKCONSEQUENCE = "CREATE TABLE " + TABLE_RISKCONSEQUENCE + "("
            + KEY_RISKCONSEQUENCE_ID + " INTEGER PRIMARY KEY, "
            + KEY_RISK_FK_ID + " INTEGER NOT NULL, "
            + KEY_RISKCONSEQUENCE_OWNER_ID + " INTEGER NOT NULL, "
            + KEY_RISKCONSEQUENCE_NAME + " TEXT, "
            + KEY_RISKCONSEQUENCE_DESCRIPTION + " TEXT, "
            + KEY_RISKCONSEQUENCE_DATE + " DATE, "
            + "FOREIGN KEY (" + KEY_RISK_FK_ID + ") REFERENCES " + TABLE_RISK + "(" + KEY_RISK_ID + ") ON DELETE CASCADE);";

    // RISKCONSEQUENCE table - column names
    public static final String KEY_RISKMITIGATION_ID = "_id";
    public static final String KEY_RISKMITIGATION_OWNER_ID = "_id_owner";
    public static final String KEY_RISKMITIGATION_NAME = "name";
    public static final String KEY_RISKMITIGATION_DESCRIPTION = "description";
    public static final String KEY_RISKMITIGATION_DATE = "date";

    // RISKCONSEQUENCE table - create statement
    public static final String CREATE_TABLE_RISKMITIGATION = "CREATE TABLE " + TABLE_RISKMITIGATION + "("
            + KEY_RISKMITIGATION_ID + " INTEGER PRIMARY KEY, "
            + KEY_RISK_FK_ID + " INTEGER NOT NULL, "
            + KEY_RISKMITIGATION_OWNER_ID + " INTEGER NOT NULL, "
            + KEY_RISKMITIGATION_NAME + " TEXT, "
            + KEY_RISKMITIGATION_DESCRIPTION + " TEXT, "
            + KEY_RISKMITIGATION_DATE + " DATE, "
            + "FOREIGN KEY (" + KEY_RISK_FK_ID + ") REFERENCES " + TABLE_RISK + "(" + KEY_RISK_ID + ") ON DELETE CASCADE);";

    // RISKMANAGEMENT table - column names
    public static final String KEY_DATE_FK_ID = "_id_date_fk";
    public static final String KEY_RISKMANAGEMENT_OWNER_ID = "_id_owner";
    public static final String KEY_RISKMANAGEMENT_LIKELIHOOD = "likelihood";
    public static final String KEY_RISKMANAGEMENT_IMPACT = "impact";
    public static final String KEY_RISKMANAGEMENT_COMMENT = "comment";
    public static final String KEY_RISKMANAGEMENT_DATE = "date";

    // RISKMANAGEMENT table - create statement
    public static final String CREATE_TABLE_RISKMANAGEMENT = "CREATE TABLE " + TABLE_RISKMANAGEMENT + "("
            + KEY_RISK_FK_ID + " INTEGER NOT NULL, "
            + KEY_DATE_FK_ID + " INTEGER NOT NULL, "
            + KEY_RISKMANAGEMENT_OWNER_ID + " INTEGER NOT NULL, "
            + KEY_RISKMANAGEMENT_LIKELIHOOD + " INTEGER, "
            + KEY_RISKMANAGEMENT_IMPACT + " INTEGER, "
            + KEY_RISKMANAGEMENT_COMMENT + " TEXT, "
            + KEY_RISKMANAGEMENT_DATE + " DATE, "
            + "FOREIGN KEY (" + KEY_RISK_FK_ID + ") REFERENCES " + TABLE_RISK + "(" + KEY_RISK_ID + ") ON DELETE CASCADE, "
            + "FOREIGN KEY (" + KEY_DATE_FK_ID + ") REFERENCES " + TABLE_DATE + "(" + KEY_DATE_ID + ") ON DELETE CASCADE, "
            + "PRIMARY KEY (" + KEY_RISK_FK_ID + ", " + KEY_DATE_FK_ID + "));";

    // WORKPLAN table - column names
    private static final String KEY_WORKPLAN_ID = "_id";
    private static final String KEY_WORKPLAN_OWNER_ID = "_id_owner";
    private static final String KEY_WORKPLAN_START_DATE = "start_date";
    private static final String KEY_WORKPLAN_END_DATE = "end_date";

    // WORKPLAN table - create statement
    public static final String CREATE_TABLE_WORKPLAN = "CREATE TABLE " + TABLE_WORKPLAN + "("
            + KEY_WORKPLAN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_WORKPLAN_OWNER_ID + " INTEGER NOT NULL, "
            + KEY_WORKPLAN_START_DATE + " DATE, "
            + KEY_WORKPLAN_END_DATE + " DATE " + ")";

    // RESOURCE table - column names
    private static final String KEY_RESOURCE_ID = "_id";
    private static final String KEY_RESOURCE_OWNER_ID = "_id_owner";
    private static final String KEY_RESOURCE_NAME = "name";
    private static final String KEY_RESOURCE_DATE = "date";

    // RESOURCE table - create statement
    public static final String CREATE_TABLE_RESOURCE = "CREATE TABLE " + TABLE_RESOURCE + "("
            + KEY_RESOURCE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_RESOURCE_OWNER_ID + " INTEGER NOT NULL, "
            + KEY_RESOURCE_NAME + " TEXT, "
            + KEY_RESOURCE_DATE + " DATE " + ")";

    // ACCOUNTTYPE table - column names
    private static final String KEY_ACCOUNTTYPE_ID = "_id";
    private static final String KEY_ACCOUNTTYPE_OWNER_ID = "_id_owner";
    private static final String KEY_ACCOUNTTYPE_NAME = "name";
    private static final String KEY_ACCOUNTTYPE_DATE = "date";

    // ACCOUNTTYPE table - create statement
    public static final String CREATE_TABLE_ACCOUNTTYPE = "CREATE TABLE " + TABLE_ACCOUNTTYPE + "("
            + KEY_ACCOUNTTYPE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_ACCOUNTTYPE_OWNER_ID + " INTEGER NOT NULL, "
            + KEY_ACCOUNTTYPE_NAME + " TEXT, "
            + KEY_ACCOUNTTYPE_DATE + " DATE " + ")";

    // BUDGET table - column names
    private static final String KEY_ACTIVITY_RESOURCE_ID = "_id_resource";
    private static final String KEY_ACCOUNT_TYPE_ID = "_id_type";
    private static final String KEY_BUDGET_OWNER_ID = "_id_owner";
    private static final String KEY_BUDGET_NAME = "name";
    private static final String KEY_BUDGET_DATE = "date";

    // BUDGET table - create statement
    public static final String CREATE_TABLE_BUDGET = "CREATE TABLE " + TABLE_BUDGET + "("
            + KEY_ACTIVITY_RESOURCE_ID + " INTEGER NOT NULL, "
            //+ KEY_ACTIVITY_RESOURCE_TYPE_ID + " INTEGER NOT NULL, "
            + KEY_ACCOUNT_TYPE_ID + " INTEGER NOT NULL, "
            + KEY_BUDGET_OWNER_ID + " INTEGER NOT NULL, "
            + KEY_BUDGET_NAME + " TEXT, "
            + KEY_BUDGET_DATE + " DATE, ";
            //+ "PRIMARY KEY (" + KEY_ACTIVITY_RESOURCE_ID + " ," + KEY_ACTIVITY_RESOURCE_TYPE_ID + " ," + KEY_ACCOUNT_TYPE_ID + " ));";

    // CASHFLOW table - column names
    private static final String KEY_CASHFLOW_OWNER_ID = "_id_owner";
    private static final String KEY_CASHFLOW_NAME = "name";
    private static final String KEY_CASHFLOW_DATE = "date";

    // CASHFLOW table - create statement
    public static final String CREATE_TABLE_CASHFLOW = "CREATE TABLE " + TABLE_CASHFLOW + "("
            + KEY_ACTIVITY_RESOURCE_ID + " INTEGER NOT NULL, "
            //+ KEY_ACTIVITY_RESOURCE_TYPE_ID + " INTEGER NOT NULL, "
            + KEY_ACCOUNT_TYPE_ID + " INTEGER NOT NULL, "
            + KEY_CASHFLOW_OWNER_ID + " INTEGER NOT NULL, "
            + KEY_CASHFLOW_NAME + " TEXT, "
            + KEY_CASHFLOW_DATE + " DATE, ";
            //+ "PRIMARY KEY (" + KEY_ACTIVITY_RESOURCE_ID + " ," + KEY_ACTIVITY_RESOURCE_TYPE_ID + " ," + KEY_ACCOUNT_TYPE_ID + " ));";

    // MONITORING Table - column names
    private static final String KEY_MONITORING_ID = "_id";
    private static final String KEY_MONITORING_OWNER_ID = "_id_owner";
    private static final String KEY_MONITORING_NAME = "name";
    private static final String KEY_MONITORING_DATE = "date";

    // MONITORING table - create statement
    public static final String CREATE_TABLE_MONITORING = "CREATE TABLE " + TABLE_MONITORING + "("
            + KEY_MONITORING_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_MONITORING_OWNER_ID + " INTEGER NOT NULL, "
            + KEY_MONITORING_NAME + " TEXT, "
            + KEY_MONITORING_DATE + " DATE " + ")";


    // EVALUATION table - column names
    private static final String KEY_EVALUATION_ID = "_id";
    private static final String KEY_EVALUATION_OWNER_ID = "_id_owner";
    private static final String KEY_EVALUATION_NAME = "name";
    private static final String KEY_EVALUATION_DATE = "date";

    // OUTPUT table - create statement
    public static final String CREATE_TABLE_EVALUATION = "CREATE TABLE " + TABLE_EVALUATION + "("
            + KEY_EVALUATION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_PROJECT_FK_ID + " INTEGER NOT NULL, "
            + KEY_EVALUATION_OWNER_ID + " INTEGER NOT NULL, "
            + KEY_EVALUATION_NAME + " TEXT, "
            + KEY_EVALUATION_DATE + " DATE, "
            + "FOREIGN KEY (" + KEY_PROJECT_FK_ID + ") REFERENCES " + TABLE_PROJECT + "(" + KEY_PROJECT_ID + "));";

    // ROLE Table - column names
    private static final String KEY_CRITERION_ID = "_id";
    private static final String KEY_CRITERION_OWNER_ID = "_id_owner";
    private static final String KEY_CRITERION_NAME = "name";
    private static final String KEY_CRITERION_DESCRIPTION = "description";

    // ROLE table - create statement
    public static final String CREATE_TABLE_CRITERION = "CREATE TABLE " + TABLE_CRITERION + "("
            + KEY_CRITERION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_CRITERION_OWNER_ID + " INTEGER NOT NULL, "
            + KEY_CRITERION_NAME + " TEXT, "
            + KEY_CRITERION_DESCRIPTION + " TEXT " + ")";

    // QUESTION table - column names
    private static final String KEY_QUESTION_ID = "_id";
    private static final String KEY_QUESTION_OWNER_ID = "_id_owner";
    private static final String KEY_QUESTION_NAME = "name";
    private static final String KEY_QUESTION_DATE = "date";

    // QUESTION table - create statement
    public static final String CREATE_TABLE_QUESTION = "CREATE TABLE " + TABLE_QUESTION + "("
            + KEY_QUESTION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_QUESTION_OWNER_ID + " INTEGER NOT NULL, "
            + KEY_QUESTION_NAME + " TEXT, "
            + KEY_QUESTION_DATE + " DATE " + ")";


    //=======================================================================================

    // OVERALLAIM_PROJECT table - column names
    private static final String KEY_OVERALLAIM_PROJECT_OWNER_ID = "_id_owner";

    // OVERALLAIM_PROJECT table - create statement
    public static final String CREATE_TABLE_OVERALLAIM_PROJECT = "CREATE TABLE " + TABLE_OVERALLAIM_PROJECT + "("
            + KEY_OVERALLAIM_FK_ID + " INTEGER NOT NULL, "
            + KEY_PROJECT_FK_ID + " INTEGER NOT NULL, "
            + KEY_OVERALLAIM_PROJECT_OWNER_ID + " INTEGER NOT NULL,"
            + "FOREIGN KEY (" + KEY_OVERALLAIM_FK_ID + ") REFERENCES " + TABLE_OVERALLAIM + "(" + KEY_OVERALLAIM_ID + "),"
            + "FOREIGN KEY (" + KEY_PROJECT_FK_ID + ") REFERENCES " + TABLE_PROJECT + "(" + KEY_PROJECT_ID + "),"
            + "PRIMARY KEY (" + KEY_OVERALLAIM_FK_ID + ", " + KEY_PROJECT_FK_ID + "));";

    // PROJECT_STATUS table - column names
    private static final String KEY_PROJECT_STATUS_OWNER_ID = "_id_owner";

    // PROJECT_STATUS table - create statement
    public static final String CREATE_TABLE_PROJECT_STATUS = "CREATE TABLE " + TABLE_PROJECT_STATUS + "("
            + KEY_PROJECT_FK_ID + " INTEGER NOT NULL, "
            + KEY_STATUS_FK_ID + " INTEGER NOT NULL, "
            + KEY_PROJECT_STATUS_OWNER_ID + " INTEGER NOT NULL, "
            + "FOREIGN KEY (" + KEY_PROJECT_FK_ID + ") REFERENCES " + TABLE_PROJECT + "(" + KEY_PROJECT_ID + "),"
            + "FOREIGN KEY (" + KEY_STATUS_FK_ID + ") REFERENCES " + TABLE_tblSTATUS + "(" + KEY_STATUS_ID + "),"
            + "PRIMARY KEY (" + KEY_PROJECT_FK_ID + ", " + KEY_STATUS_FK_ID + "));";

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
            + "FOREIGN KEY (" + KEY_PROJECT_FK_ID + ") REFERENCES " + TABLE_PROJECT + "(" + KEY_PROJECT_ID + ") ON DELETE CASCADE,"
            + "FOREIGN KEY (" + KEY_OUTCOME_FK_ID + ") REFERENCES " + TABLE_OUTCOME + "(" + KEY_OUTCOME_ID + ") ON DELETE CASCADE,"
            + "PRIMARY KEY (" + KEY_PROJECT_FK_ID + ", " + KEY_OUTCOME_FK_ID + "));";

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
            + "FOREIGN KEY (" + KEY_OUTCOME_FK_ID + ") REFERENCES " + TABLE_OUTCOME + "(" + KEY_OUTCOME_ID + ") ON DELETE CASCADE,"
            + "FOREIGN KEY (" + KEY_OUTPUT_FK_ID + ") REFERENCES " + TABLE_OUTPUT + "(" + KEY_OUTPUT_ID + ") ON DELETE CASCADE,"
            + "PRIMARY KEY (" + KEY_OUTCOME_FK_ID + ", " + KEY_OUTPUT_FK_ID + "));";

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
            + "FOREIGN KEY (" + KEY_OUTPUT_FK_ID + ") REFERENCES " + TABLE_OUTPUT + "(" + KEY_OUTPUT_ID + ") ON DELETE CASCADE,"
            + "FOREIGN KEY (" + KEY_ACTIVITY_FK_ID + ") REFERENCES " + TABLE_ACTIVITY + "(" + KEY_ACTIVITY_ID + ") ON DELETE CASCADE,"
            + "PRIMARY KEY (" + KEY_OUTPUT_FK_ID + ", " + KEY_ACTIVITY_FK_ID + "));";


    // OVERALLAIM_INDICATOR table - column names
    public static final String KEY_OVERALLAIM_INDICATOR_OWNER_ID = "_id_owner";
    public static final String KEY_OVERALLAIM_INDICATOR_DATE = "date";

    // OVERALLAIM_INDICATOR table - create statement
    public static final String CREATE_TABLE_OVERALLAIM_INDICATOR = "CREATE TABLE " + TABLE_OVERALLAIM_INDICATOR + "("
            + KEY_OVERALLAIM_FK_ID + " INTEGER NOT NULL, "
            + KEY_INDICATOR_FK_ID + " INTEGER NOT NULL, "
            + KEY_OVERALLAIM_INDICATOR_OWNER_ID + " INTEGER NOT NULL,"
            + KEY_OVERALLAIM_INDICATOR_DATE + " DATE, "
            + "FOREIGN KEY (" + KEY_OVERALLAIM_FK_ID + ") REFERENCES " + TABLE_OVERALLAIM + "(" + KEY_OVERALLAIM_ID + "),"
            + "FOREIGN KEY (" + KEY_INDICATOR_FK_ID + ") REFERENCES " + TABLE_INDICATOR + "(" + KEY_INDICATOR_ID + "),"
            + "PRIMARY KEY (" + KEY_OVERALLAIM_FK_ID + ", " + KEY_INDICATOR_FK_ID + "))";

    // OUTCOME_INDICATOR table - column names
    public static final String KEY_OUTCOME_INDICATOR_OWNER_ID = "_id_owner";
    public static final String KEY_OUTCOME_INDICATOR_DATE = "date";

    // OUTCOME_INDICATOR table - create statement
    public static final String CREATE_TABLE_OUTCOME_INDICATOR = "CREATE TABLE " + TABLE_OUTCOME_INDICATOR + "("
            + KEY_OUTCOME_FK_ID + " INTEGER NOT NULL, "
            + KEY_INDICATOR_FK_ID + " INTEGER NOT NULL, "
            + KEY_OUTCOME_INDICATOR_OWNER_ID + " INTEGER NOT NULL,"
            + KEY_OUTCOME_INDICATOR_DATE + " DATE, "
            + "FOREIGN KEY (" + KEY_OUTCOME_FK_ID + ") REFERENCES " + TABLE_OUTCOME + "(" + KEY_OUTCOME_ID + "),"
            + "FOREIGN KEY (" + KEY_INDICATOR_FK_ID + ") REFERENCES " + TABLE_INDICATOR + "(" + KEY_INDICATOR_ID + "),"
            + "PRIMARY KEY (" + KEY_OUTCOME_FK_ID + ", " + KEY_INDICATOR_FK_ID + "));";

    // OUTPUT_INDICATOR table - column names
    public static final String KEY_OUTPUT_INDICATOR_OWNER_ID = "_id_owner";
    public static final String KEY_OUTPUT_INDICATOR_DATE = "date";

    // OUTPUT_INDICATOR table - create statement
    public static final String CREATE_TABLE_OUTPUT_INDICATOR = "CREATE TABLE " + TABLE_OUTPUT_INDICATOR + "("
            + KEY_OUTPUT_FK_ID + " INTEGER NOT NULL, "
            + KEY_INDICATOR_FK_ID + " INTEGER NOT NULL, "
            + KEY_OUTPUT_INDICATOR_OWNER_ID + " INTEGER NOT NULL,"
            + KEY_OUTPUT_INDICATOR_DATE + " DATE, "
            + "FOREIGN KEY (" + KEY_OUTPUT_FK_ID + ") REFERENCES " + TABLE_OUTPUT + "(" + KEY_OUTPUT_ID + "),"
            + "FOREIGN KEY (" + KEY_INDICATOR_FK_ID + ") REFERENCES " + TABLE_INDICATOR + "(" + KEY_INDICATOR_ID + "),"
            + "PRIMARY KEY (" + KEY_OUTPUT_FK_ID + ", " + KEY_INDICATOR_FK_ID + "));";

    // MOV_INDICATOR table - column names
    public static final String KEY_MOV_INDICATOR_OWNER_ID = "_id_owner";
    public static final String KEY_MOV_INDICATOR_DATE = "date";

    // MOV_INDICATOR table - create statement
    public static final String CREATE_TABLE_MOV_INDICATOR = "CREATE TABLE " + TABLE_MOV_INDICATOR + "("
            + KEY_MOV_FK_ID + " INTEGER NOT NULL, "
            + KEY_INDICATOR_FK_ID + " INTEGER NOT NULL, "
            + KEY_MOV_INDICATOR_OWNER_ID + " INTEGER NOT NULL,"
            + KEY_MOV_INDICATOR_DATE + " DATE, "
            + "FOREIGN KEY (" + KEY_MOV_FK_ID + ") REFERENCES " + TABLE_MOV + "(" + KEY_MOV_ID + "),"
            + "FOREIGN KEY (" + KEY_INDICATOR_FK_ID + ") REFERENCES " + TABLE_INDICATOR + "(" + KEY_INDICATOR_ID + "),"
            + "PRIMARY KEY (" + KEY_MOV_FK_ID + ", " + KEY_INDICATOR_FK_ID + "));";

    // OVERALLAIM_RISK table - column names
    public static final String KEY_OVERALLAIM_RISK_OWNER_ID = "_id_owner";
    public static final String KEY_OVERALLAIM_RISK_DATE = "date";

    // OVERALLAIM_RISK table - create statement
    public static final String CREATE_TABLE_OVERALLAIM_RISK = "CREATE TABLE " + TABLE_OVERALLAIM_RISK + "("
            + KEY_OVERALLAIM_FK_ID + " INTEGER NOT NULL, "
            + KEY_RISK_FK_ID + " INTEGER NOT NULL, "
            + KEY_OVERALLAIM_RISK_OWNER_ID + " INTEGER NOT NULL,"
            + KEY_OVERALLAIM_RISK_DATE + " DATE, "
            + "FOREIGN KEY (" + KEY_OVERALLAIM_FK_ID + ") REFERENCES " + TABLE_OVERALLAIM + "(" + KEY_OVERALLAIM_ID + ") ON DELETE CASCADE,"
            + "FOREIGN KEY (" + KEY_RISK_FK_ID + ") REFERENCES " + TABLE_RISK + "(" + KEY_RISK_ID + ") ON DELETE CASCADE,"
            + "PRIMARY KEY (" + KEY_OVERALLAIM_FK_ID + ", " + KEY_RISK_FK_ID + "));";

    // OUTCOME_RISK table - column names
    public static final String KEY_OUTCOME_RISK_OWNER_ID = "_id_owner";
    public static final String KEY_OUTCOME_RISK_DATE = "date";

    // OUTCOME_RISK table - create statement
    public static final String CREATE_TABLE_OUTCOME_RISK = "CREATE TABLE " + TABLE_OUTCOME_RISK + "("
            + KEY_OUTCOME_FK_ID + " INTEGER NOT NULL, "
            + KEY_RISK_FK_ID + " INTEGER NOT NULL, "
            + KEY_OUTCOME_RISK_OWNER_ID + " INTEGER NOT NULL, "
            + KEY_OUTCOME_RISK_DATE + " DATE, "
            + "FOREIGN KEY (" + KEY_OUTCOME_FK_ID + ") REFERENCES " + TABLE_OUTCOME + "(" + KEY_OUTCOME_ID + ") ON DELETE CASCADE,"
            + "FOREIGN KEY (" + KEY_RISK_FK_ID + ") REFERENCES " + TABLE_RISK + "(" + KEY_RISK_ID + ") ON DELETE CASCADE,"
            + "PRIMARY KEY (" + KEY_OUTCOME_FK_ID + ", " + KEY_RISK_FK_ID + "));";

    // OUTPUT_RISK table - column names
    public static final String KEY_OUTPUT_RISK_OWNER_ID = "_id_owner";
    public static final String KEY_OUTPUT_RISK_DATE = "date";

    // OUTPUT_RISK table - create statement
    public static final String CREATE_TABLE_OUTPUT_RISK = "CREATE TABLE " + TABLE_OUTPUT_RISK + "("
            + KEY_OUTPUT_FK_ID + " INTEGER NOT NULL, "
            + KEY_RISK_FK_ID + " INTEGER NOT NULL, "
            + KEY_OUTPUT_RISK_OWNER_ID + " INTEGER NOT NULL, "
            + KEY_OUTPUT_RISK_DATE + " DATE, "
            + "FOREIGN KEY (" + KEY_OUTPUT_FK_ID + ") REFERENCES " + TABLE_OUTPUT + "(" + KEY_OUTPUT_ID + ") ON DELETE CASCADE,"
            + "FOREIGN KEY (" + KEY_RISK_FK_ID + ") REFERENCES " + TABLE_RISK + "(" + KEY_RISK_ID + ") ON DELETE CASCADE,"
            + "PRIMARY KEY (" + KEY_OUTPUT_FK_ID + ", " + KEY_RISK_FK_ID + "));";

    // ACTIVITY_RISK table - column names
    public static final String KEY_ACTIVITY_RISK_OWNER_ID = "_id_owner";
    public static final String KEY_ACTIVITY_RISK_DATE = "date";

    // ACTIVITY_RISK table - create statement
    public static final String CREATE_TABLE_ACTIVITY_RISK = "CREATE TABLE " + TABLE_ACTIVITY_RISK + "("
            + KEY_ACTIVITY_FK_ID + " INTEGER NOT NULL, "
            + KEY_RISK_FK_ID + " INTEGER NOT NULL, "
            + KEY_ACTIVITY_RISK_OWNER_ID + " INTEGER NOT NULL,"
            + KEY_ACTIVITY_RISK_DATE + " DATE, "
            + "FOREIGN KEY (" + KEY_ACTIVITY_FK_ID + ") REFERENCES " + TABLE_ACTIVITY + "(" + KEY_ACTIVITY_ID + ") ON DELETE CASCADE,"
            + "FOREIGN KEY (" + KEY_RISK_FK_ID + ") REFERENCES " + TABLE_RISK + "(" + KEY_RISK_ID + ") ON DELETE CASCADE,"
            + "PRIMARY KEY (" + KEY_ACTIVITY_FK_ID + ", " + KEY_RISK_FK_ID + "));";


    // OVERALLAIM_CRITERION table - column names
    private static final String KEY_CRITERION_FK_ID = "_id_criterion_fk";
    private static final String KEY_OVERALLAIM_CRITERION_OWNER_ID = "_id_owner";

    // OVERALLAIM_CRITERION table - create statement
    public static final String CREATE_TABLE_OVERALLAIM_CRITERION = "CREATE TABLE " + TABLE_OVERALLAIM_CRITERION + "("
            + KEY_OVERALLAIM_FK_ID + " INTEGER NOT NULL, "
            + KEY_CRITERION_FK_ID + " INTEGER NOT NULL, "
            + KEY_OVERALLAIM_CRITERION_OWNER_ID + " INTEGER NOT NULL,"
            + "FOREIGN KEY (" + KEY_OVERALLAIM_FK_ID + ") REFERENCES " + TABLE_OVERALLAIM + "(" + KEY_OVERALLAIM_ID + "),"
            + "FOREIGN KEY (" + KEY_CRITERION_FK_ID + ") REFERENCES " + TABLE_CRITERION + "(" + KEY_CRITERION_ID + "),"
            + "PRIMARY KEY (" + KEY_OVERALLAIM_FK_ID + ", " + KEY_CRITERION_FK_ID + "));";


    // OUTCOME_CRITERION table - column names
    private static final String KEY_OUTCOME_CRITERION_OWNER_ID = "_id_owner";

    // OUTCOME_CRITERION table - create statement
    public static final String CREATE_TABLE_OUTCOME_CRITERION = "CREATE TABLE " + TABLE_OUTCOME_CRITERION + "("
            + KEY_OUTCOME_FK_ID + " INTEGER NOT NULL, "
            + KEY_CRITERION_FK_ID + " INTEGER NOT NULL, "
            + KEY_OUTCOME_CRITERION_OWNER_ID + " INTEGER NOT NULL,"
            + "FOREIGN KEY (" + KEY_OUTCOME_FK_ID + ") REFERENCES " + TABLE_OUTCOME + "(" + KEY_OUTCOME_ID + "),"
            + "FOREIGN KEY (" + KEY_CRITERION_FK_ID + ") REFERENCES " + TABLE_CRITERION + "(" + KEY_CRITERION_ID + "),"
            + "PRIMARY KEY (" + KEY_OUTCOME_FK_ID + ", " + KEY_CRITERION_FK_ID + "));";

    // OUTPUT_CRITERION table - column names
    private static final String KEY_OUTPUT_CRITERION_OWNER_ID = "_id_owner";

    // OUTPUT_CRITERION table - create statement
    public static final String CREATE_TABLE_OUTPUT_CRITERION = "CREATE TABLE " + TABLE_OUTPUT_CRITERION + "("
            + KEY_OUTPUT_FK_ID + " INTEGER NOT NULL, "
            + KEY_CRITERION_FK_ID + " INTEGER NOT NULL, "
            + KEY_OUTPUT_CRITERION_OWNER_ID + " INTEGER NOT NULL,"
            + "FOREIGN KEY (" + KEY_OUTPUT_FK_ID + ") REFERENCES " + TABLE_OUTPUT + "(" + KEY_OUTPUT_ID + "),"
            + "FOREIGN KEY (" + KEY_CRITERION_FK_ID + ") REFERENCES " + TABLE_CRITERION + "(" + KEY_CRITERION_ID + "),"
            + "PRIMARY KEY (" + KEY_OUTPUT_FK_ID + ", " + KEY_CRITERION_FK_ID + "));";

    // ACTIVITY_CRITERION table - column names
    private static final String KEY_ACTIVITY_CRITERION_OWNER_ID = "_id_owner";

    // ACTIVITY_CRITERION table - create statement
    public static final String CREATE_TABLE_ACTIVITY_CRITERION = "CREATE TABLE " + TABLE_ACTIVITY_CRITERION + "("
            + KEY_ACTIVITY_FK_ID + " INTEGER NOT NULL, "
            + KEY_CRITERION_FK_ID + " INTEGER NOT NULL, "
            + KEY_ACTIVITY_CRITERION_OWNER_ID + " INTEGER NOT NULL,"
            + "FOREIGN KEY (" + KEY_ACTIVITY_FK_ID + ") REFERENCES " + TABLE_ACTIVITY + "(" + KEY_ACTIVITY_ID + "),"
            + "FOREIGN KEY (" + KEY_CRITERION_FK_ID + ") REFERENCES " + TABLE_CRITERION + "(" + KEY_CRITERION_ID + "),"
            + "PRIMARY KEY (" + KEY_ACTIVITY_FK_ID + ", " + KEY_CRITERION_FK_ID + "));";

    // QUESTION_CATEGORY table - column names
    //private static final String KEY_QUESTION_FK_ID = "_id_question_fk";
    private static final String KEY_QUESTION_CATEGORY_OWNER_ID = "_id_owner";

    // QUESTION_CATEGORY table - create statement
    public static final String CREATE_TABLE_QUESTION_CATEGORY = "CREATE TABLE " + TABLE_QUESTION_CATEGORY + "("
            + KEY_QUESTION_FK_ID + " INTEGER NOT NULL, "
            + KEY_CATEGORY_FK_ID + " INTEGER NOT NULL, "
            + KEY_QUESTION_CATEGORY_OWNER_ID + " INTEGER NOT NULL,"
            + "FOREIGN KEY (" + KEY_QUESTION_FK_ID + ") REFERENCES " + TABLE_QUESTION + "(" + KEY_QUESTION_ID + "),"
            + "FOREIGN KEY (" + KEY_CATEGORY_FK_ID + ") REFERENCES " + TABLE_CATEGORY + "(" + KEY_CATEGORY_ID + "),"
            + "PRIMARY KEY (" + KEY_QUESTION_FK_ID + ", " + KEY_CATEGORY_FK_ID + "));";

    // INDICATOR_QUESTION table - column names
    private static final String KEY_INDICATOR_QUESTION_OWNER_ID = "_id_owner";

    // INDICATOR_QUESTION table - create statement
    public static final String CREATE_TABLE_INDICATOR_QUESTION = "CREATE TABLE " + TABLE_INDICATOR_QUESTION + "("
            + KEY_INDICATOR_FK_ID + " INTEGER NOT NULL, "
            + KEY_QUESTION_FK_ID + " INTEGER NOT NULL, "
            + KEY_INDICATOR_QUESTION_OWNER_ID + " INTEGER NOT NULL,"
            + "FOREIGN KEY (" + KEY_INDICATOR_FK_ID + ") REFERENCES " + TABLE_INDICATOR + "(" + KEY_INDICATOR_ID + "),"
            + "FOREIGN KEY (" + KEY_QUESTION_FK_ID + ") REFERENCES " + TABLE_QUESTION + "(" + KEY_QUESTION_ID + "),"
            + "PRIMARY KEY (" + KEY_INDICATOR_FK_ID + ", " + KEY_QUESTION_FK_ID + "));";

    // OVERALLAIM_CRITERION_QUESTION table - column names
    private static final String KEY_OVERALLAIM_CRITERION_QUESTION_OWNER_ID = "_id_owner";

    // OVERALLAIM_CRITERION_QUESTION table - create statement
    public static final String CREATE_TABLE_OVERALLAIM_CRITERION_QUESTION = "CREATE TABLE " + TABLE_OVERALLAIM_CRITERION_QUESTION + "("
            + KEY_OVERALLAIM_FK_ID + " INTEGER NOT NULL, "
            + KEY_CRITERION_FK_ID + " INTEGER NOT NULL, "
            + KEY_QUESTION_FK_ID + " INTEGER NOT NULL, "
            + KEY_OVERALLAIM_CRITERION_QUESTION_OWNER_ID + " INTEGER NOT NULL,"
            + "FOREIGN KEY (" + KEY_OVERALLAIM_FK_ID + ") REFERENCES " + TABLE_OVERALLAIM + "(" + KEY_OVERALLAIM_ID + "),"
            + "FOREIGN KEY (" + KEY_CRITERION_FK_ID + ") REFERENCES " + TABLE_CRITERION + "(" + KEY_CRITERION_ID + "),"
            + "FOREIGN KEY (" + KEY_QUESTION_FK_ID + ") REFERENCES " + TABLE_QUESTION + "(" + KEY_QUESTION_ID + "),"
            + "PRIMARY KEY (" + KEY_OVERALLAIM_FK_ID + ", " + KEY_CRITERION_FK_ID + ", " + KEY_QUESTION_FK_ID + "));";

    // OUTCOME_CRITERION_QUESTION table - column names
    private static final String KEY_OUTCOME_CRITERION_QUESTION_OWNER_ID = "_id_owner";

    // OUTCOME_CRITERION_QUESTION table - create statement
    public static final String CREATE_TABLE_OUTCOME_CRITERION_QUESTION = "CREATE TABLE " + TABLE_OUTCOME_CRITERION_QUESTION + "("
            + KEY_OUTCOME_FK_ID + " INTEGER NOT NULL, "
            + KEY_CRITERION_FK_ID + " INTEGER NOT NULL, "
            + KEY_QUESTION_FK_ID + " INTEGER NOT NULL, "
            + KEY_OUTCOME_CRITERION_QUESTION_OWNER_ID + " INTEGER NOT NULL,"
            + "FOREIGN KEY (" + KEY_OUTCOME_FK_ID + ") REFERENCES " + TABLE_OUTCOME + "(" + KEY_OUTCOME_ID + "),"
            + "FOREIGN KEY (" + KEY_CRITERION_FK_ID + ") REFERENCES " + TABLE_CRITERION + "(" + KEY_CRITERION_ID + "),"
            + "FOREIGN KEY (" + KEY_QUESTION_FK_ID + ") REFERENCES " + TABLE_QUESTION + "(" + KEY_QUESTION_ID + "),"
            + "PRIMARY KEY (" + KEY_OUTCOME_FK_ID + ", " + KEY_CRITERION_FK_ID + ", " + KEY_QUESTION_FK_ID + "));";

    // OUTPUT_CRITERION_QUESTION table - column names
    private static final String KEY_OUTPUT_CRITERION_QUESTION_OWNER_ID = "_id_owner";

    // OUTPUT_CRITERION_QUESTION table - create statement
    public static final String CREATE_TABLE_OUTPUT_CRITERION_QUESTION = "CREATE TABLE " + TABLE_OUTPUT_CRITERION_QUESTION + "("
            + KEY_OUTPUT_FK_ID + " INTEGER NOT NULL, "
            + KEY_CRITERION_FK_ID + " INTEGER NOT NULL, "
            + KEY_QUESTION_FK_ID + " INTEGER NOT NULL, "
            + KEY_OUTPUT_CRITERION_QUESTION_OWNER_ID + " INTEGER NOT NULL,"
            + "FOREIGN KEY (" + KEY_OUTPUT_FK_ID + ") REFERENCES " + TABLE_OUTPUT + "(" + KEY_OUTPUT_ID + "),"
            + "FOREIGN KEY (" + KEY_CRITERION_FK_ID + ") REFERENCES " + TABLE_CRITERION + "(" + KEY_CRITERION_ID + "),"
            + "FOREIGN KEY (" + KEY_QUESTION_FK_ID + ") REFERENCES " + TABLE_QUESTION + "(" + KEY_QUESTION_ID + "),"
            + "PRIMARY KEY (" + KEY_OUTPUT_FK_ID + ", " + KEY_CRITERION_FK_ID + ", " + KEY_QUESTION_FK_ID + "));";

    // ACTIVITY_CRITERION_QUESTION table - column names
    private static final String KEY_ACTIVITY_CRITERION_QUESTION_OWNER_ID = "_id_owner";

    // ACTIVITY_CRITERION_QUESTION table - create statement
    public static final String CREATE_TABLE_ACTIVITY_CRITERION_QUESTION = "CREATE TABLE " + TABLE_ACTIVITY_CRITERION_QUESTION + "("
            + KEY_ACTIVITY_FK_ID + " INTEGER NOT NULL, "
            + KEY_CRITERION_FK_ID + " INTEGER NOT NULL, "
            + KEY_QUESTION_FK_ID + " INTEGER NOT NULL, "
            + KEY_ACTIVITY_CRITERION_QUESTION_OWNER_ID + " INTEGER NOT NULL,"
            + "FOREIGN KEY (" + KEY_ACTIVITY_FK_ID + ") REFERENCES " + TABLE_ACTIVITY + "(" + KEY_ACTIVITY_ID + "),"
            + "FOREIGN KEY (" + KEY_CRITERION_FK_ID + ") REFERENCES " + TABLE_CRITERION + "(" + KEY_CRITERION_ID + "),"
            + "FOREIGN KEY (" + KEY_QUESTION_FK_ID + ") REFERENCES " + TABLE_QUESTION + "(" + KEY_QUESTION_ID + "),"
            + "PRIMARY KEY (" + KEY_ACTIVITY_FK_ID + ", " + KEY_CRITERION_FK_ID + ", " + KEY_QUESTION_FK_ID + "));";

    // MONITORING_QUESTIONNAIRE table - column names
    private static final String KEY_MONITORING_FK_ID = "_id_monitoring_fk";
    private static final String KEY_MONITORING_QUESTIONNAIRE_OWNER_ID = "_id_owner";

    // MONITORING_QUESTIONNAIRE table - create statement
    public static final String CREATE_TABLE_MONITORING_QUESTIONNAIRE = "CREATE TABLE " + TABLE_MONITORING_QUESTIONNAIRE + "("
            + KEY_MONITORING_FK_ID + " INTEGER NOT NULL, "
            + KEY_QUESTION_FK_ID + " INTEGER NOT NULL, "
            + KEY_MONITORING_QUESTIONNAIRE_OWNER_ID + " INTEGER NOT NULL,"
            + "FOREIGN KEY (" + KEY_MONITORING_FK_ID + ") REFERENCES " + TABLE_MONITORING + "(" + KEY_MONITORING_ID + "),"
            + "FOREIGN KEY (" + KEY_QUESTION_FK_ID + ") REFERENCES " + TABLE_QUESTION + "(" + KEY_QUESTION_ID + "),"
            + "PRIMARY KEY (" + KEY_MONITORING_FK_ID + ", " + KEY_QUESTION_FK_ID + "));";

    // EVALUATION_QUESTIONNAIRE table - column names
    private static final String KEY_EVALUATION_FK_ID = "_id_monitoring_fk";
    private static final String KEY_EVALUATION_QUESTIONNAIRE_OWNER_ID = "_id_owner";

    // EVALUATION_QUESTIONNAIRE table - create statement
    public static final String CREATE_TABLE_EVALUATION_QUESTIONNAIRE = "CREATE TABLE " + TABLE_EVALUATION_QUESTIONNAIRE + "("
            + KEY_EVALUATION_FK_ID + " INTEGER NOT NULL, "
            + KEY_QUESTION_FK_ID + " INTEGER NOT NULL, "
            + KEY_EVALUATION_QUESTIONNAIRE_OWNER_ID + " INTEGER NOT NULL,"
            + "FOREIGN KEY (" + KEY_EVALUATION_FK_ID + ") REFERENCES " + TABLE_EVALUATION + "(" + KEY_EVALUATION_ID + "),"
            + "FOREIGN KEY (" + KEY_QUESTION_FK_ID + ") REFERENCES " + TABLE_QUESTION + "(" + KEY_QUESTION_ID + "),"
            + "PRIMARY KEY (" + KEY_EVALUATION_FK_ID + ", " + KEY_QUESTION_FK_ID + "));";


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
            + "FOREIGN KEY (" + KEY_MONITORING_FK_ID + ") REFERENCES " + TABLE_MONITORING + "(" + KEY_MONITORING_ID + "),"
            + "FOREIGN KEY (" + KEY_QUESTION_FK_ID + ") REFERENCES " + TABLE_QUESTION + "(" + KEY_QUESTION_ID + "),"
            + "FOREIGN KEY (" + KEY_USER_FK_ID + ") REFERENCES " + TABLE_tblUSER + "(" + KEY_USER_ID + "),"
            + "PRIMARY KEY (" + KEY_MONITORING_FK_ID + ", " + KEY_QUESTION_FK_ID + ", " + KEY_USER_FK_ID + "));";

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
            + "FOREIGN KEY (" + KEY_EVALUATION_FK_ID + ") REFERENCES " + TABLE_EVALUATION + "(" + KEY_EVALUATION_ID + "),"
            + "FOREIGN KEY (" + KEY_QUESTION_FK_ID + ") REFERENCES " + TABLE_QUESTION + "(" + KEY_QUESTION_ID + "),"
            + "FOREIGN KEY (" + KEY_USER_FK_ID + ") REFERENCES " + TABLE_tblUSER + "(" + KEY_USER_ID + "),"
            + "PRIMARY KEY (" + KEY_EVALUATION_FK_ID + ", " + KEY_QUESTION_FK_ID + ", " + KEY_USER_FK_ID + "));";

}