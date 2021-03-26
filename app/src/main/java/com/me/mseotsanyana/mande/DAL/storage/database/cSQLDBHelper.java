package com.me.mseotsanyana.mande.DAL.storage.database;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.me.mseotsanyana.mande.UTIL.cConstant;

import java.text.SimpleDateFormat;

public class cSQLDBHelper extends SQLiteOpenHelper {
    private static SimpleDateFormat sdf = cConstant.FORMAT_DATE;
    private static String TAG = cSQLDBHelper.class.getSimpleName();

    // Database Name
    private static final String DATABASE_NAME = "MEDB.db";
    // Database Version
    private static final int DATABASE_VERSION = 130;

    /*################################ START GLOBAL MODULE TABLES ################################*/

    public static final String TABLE_tblFREQUENCY             = "tblFREQUENCY";             /* 1 */
    public static final String TABLE_tblPERIOD                = "tblPERIOD";                /* 2 */
    public static final String TABLE_tblFISCALYEAR            = "tblFISCALYEAR";            /* 3 */
    public static final String TABLE_tblCHART                 = "tblCHART";                 /* 4 */

    /*############################### START GLOBAL MODULE TABLES #################################*/

    /*################################ START BRBAC MODULE TABLES #################################*/

    public static final String TABLE_tblADDRESS               = "tblADDRESS";              /* 1  */
    public static final String TABLE_tblORGANIZATION          = "tblORGANIZATION";         /* 2  */
    public static final String TABLE_tblFUNDER                = "tblFUNDER";               /* 3  */
    public static final String TABLE_tblBENEFICIARY           = "tblBENEFICIARY";          /* 4  */
    public static final String TABLE_tblIMPLEMENTINGAGENCY    = "tblIMPLEMENTINGAGENCY";   /* 5  */
    public static final String TABLE_tblCROWDFUND             = "tblCROWDFUND";            /* 6  */
    public static final String TABLE_tblVALUE                 = "tblVALUE";                /* 7  */
    public static final String TABLE_tblUSER                  = "tblUSER";                 /* 8  */
    public static final String TABLE_tblSESSION               = "tblSESSION";              /* 9  */
    public static final String TABLE_tblROLE                  = "tblROLE";                 /* 10 */
    public static final String TABLE_tblMENU                  = "tblMENU";                 /* 11 */
    public static final String TABLE_tblPRIVILEGE             = "tblPRIVILEGE";            /* 12 */
    public static final String TABLE_tblENTITY                = "tblENTITY";               /* 13 */
    public static final String TABLE_tblOPERATION             = "tblOPERATION";            /* 14 */
    public static final String TABLE_tblSTATUS                = "tblSTATUS";               /* 15 */
    public static final String TABLE_tblSTATUSSET             = "tblSTATUSSET";            /* 16 */
    public static final String TABLE_tblORG_ADDRESS           = "tblORG_ADDRESS";          /* 17 */
    public static final String TABLE_tblUSER_ADDRESS          = "tblUSER_ADDRESS";         /* 18 */
    public static final String TABLE_tblUSER_ROLE             = "tblUSER_ROLE";            /* 19 */
    public static final String TABLE_tblSESSION_ROLE          = "tblSESSION_ROLE";         /* 20 */
    public static final String TABLE_tblMENU_ROLE             = "tblMENU_ROLE";            /* 21 */
    public static final String TABLE_tblPERMISSION            = "tblPERMISSION";           /* 22 */
    public static final String TABLE_tblSTATUSSET_STATUS      = "tblSTATUSSET_STATUS";     /* 23 */
    public static final String TABLE_tblSETTING               = "tblSETTING";              /* 24 */
    public static final String TABLE_tblNOTIFICATION          = "tblNOTIFICATION";         /* 25 */
    public static final String TABLE_tblSUBSCRIBER            = "tblSUBSCRIBER";           /* 26 */
    public static final String TABLE_tblPUBLISHER             = "tblPUBLISHER";            /* 27 */
    public static final String TABLE_tblNOTIFY_SETTING        = "tblNOTIFY_SETTING";       /* 28 */
    public static final String TABLE_tblACTIVITYLOG           = "tblACTIVITYLOG";          /* 29 */

    /*################################ END BRBAC MODULE TABLES ###################################*/

    /*############################## START LOGFRAME MODULE TABLES ################################*/

    public static final String TABLE_tblLOGFRAME              = "tblLOGFRAME";             /* 1  */
    public static final String TABLE_tblLOGFRAMETREE          = "tblLOGFRAMETREE";         /* 2  */
    public static final String TABLE_tblRESOURCETYPE          = "tblRESOURCETYPE";         /* 3  */
    public static final String TABLE_tblCOMPONENT             = "tblCOMPONENT";            /* 4  */
    public static final String TABLE_tblIMPACT                = "tblIMPACT";               /* 5  */
    public static final String TABLE_tblOUTCOME               = "tblOUTCOME";              /* 6  */
    public static final String TABLE_tblOUTPUT                = "tblOUTPUT";               /* 7  */
    public static final String TABLE_tblACTIVITY              = "tblACTIVITY";             /* 8  */
    public static final String TABLE_tblINPUT                 = "tblINPUT";                /* 9  */
    public static final String TABLE_tblPRECEDINGACTIVITY     = "tblPRECEDINGACTIVITY";    /* 10 */
    public static final String TABLE_tblACTIVITYASSIGNMENT    = "tblACTIVITYASSIGNMENT";   /* 11 */
    public static final String TABLE_tblOUTCOME_IMPACT        = "tblOUTCOME_IMPACT";       /* 12 */
    public static final String TABLE_tblOUTPUT_OUTCOME        = "tblOUTPUT_OUTCOME";       /* 13 */
    public static final String TABLE_tblACTIVITY_OUTPUT       = "tblACTIVITY_OUTPUT";      /* 14 */
    public static final String TABLE_tblINPUT_ACTIVITY        = "tblINPUT_ACTIVITY";       /* 15 */
    public static final String TABLE_tblCRITERIA              = "tblCRITERIA";             /* 16 */
    public static final String TABLE_tblQUESTIONGROUPING      = "tblQUESTIONGROUPING";     /* 17 */
    public static final String TABLE_tblQUESTIONTYPE          = "tblQUESTIONTYPE";         /* 18 */
    public static final String TABLE_tblQUESTION              = "tblQUESTION";             /* 19 */
    public static final String TABLE_tblPRIMITIVEQUESTION     = "tblPRIMITIVEQUESTION";    /* 20 */
    public static final String TABLE_tblARRAYQUESTION         = "tblARRAYQUESTION";        /* 21 */
    public static final String TABLE_tblMATRIXQUESTION        = "tblMATRIXQUESTION";       /* 22 */
    public static final String TABLE_tblQUESTION_CRITERIA     = "tblQUESTION_CRITERIA";    /* 23 */
    public static final String TABLE_tblRAIDCATEGORY          = "tblRAIDCATEGORY";         /* 24 */
    public static final String TABLE_tblRAID                  = "tblRAID";                 /* 25 */
    public static final String TABLE_tblCOMPONENT_RAID        = "tblCOMPONENT_RAID";       /* 26 */

    //public static final String TABLE_tblWORKPLAN              = "tblWORKPLAN";           /* 7  */
    //public static final String TABLE_tblRESOURCE              = "tblRESOURCE";           /* 11 */
    //public static final String TABLE_tblIMPACT_QUESTION       = "tblIMPACT_QUESTION";      /* 26 */
    //public static final String TABLE_tblOUTCOME_QUESTION      = "tblOUTCOME_QUESTION";     /* 27 */
    //public static final String TABLE_tblOUTPUT_QUESTION       = "tblOUTPUT_QUESTION";      /* 28 */
    //public static final String TABLE_tblACTIVITY_QUESTION     = "tblACTIVITY_QUESTION";    /* 29 */
    //public static final String TABLE_tblINPUT_QUESTION        = "tblINPUT_QUESTION";       /* 30 */
    //public static final String TABLE_tblIMPACT_RAID           = "tblIMPACT_RAID";          /* 31 */
    //public static final String TABLE_tblOUTCOME_RAID          = "tblOUTCOME_RAID";         /* 32 */
    //public static final String TABLE_tblOUTPUT_RAID           = "tblOUTPUT_RAID";          /* 33 */
    //public static final String TABLE_tblACTIVITY_RAID         = "tblACTIVITY_RAID";        /* 34 */

    /*############################### END LOGFRAME MODULE TABLES #################################*/

    /*############################# START EVALUATION MODULE TABLES ###############################*/

    public static final String TABLE_tblARRAYCHOICE           = "tblARRAYCHOICE";           /* 1 */
    public static final String TABLE_tblROWCHOICE             = "tblROWCHOICE";             /* 2 */
    public static final String TABLE_tblCOLCHOICE             = "tblCOLCHOICE";             /* 3 */
    public static final String TABLE_tblARRAYSET              = "tblARRAYSET";              /* 4 */
    public static final String TABLE_tblMATRIXSET             = "tblMATRIXSET";             /* 5 */
    public static final String TABLE_tblARRAYCHOICESET        = "tblARRAYCHOICESET";        /* 6 */
    public static final String TABLE_tblMATRIXCHOICESET       = "tblMATRIXCHOICESET";       /* 7 */
    public static final String TABLE_tblEVALUATIONTYPE        = "tblEVALUATIONTYPE";        /* 8 */
    public static final String TABLE_tblEVALUATION            = "tblEVALUATION";            /* 9 */
    public static final String TABLE_tblQUESTION_EVALUATION   = "tblQUESTION_EVALUATION";   /* 10*/
    public static final String TABLE_tblCONDITIONAL_ORDER     = "tblCONDITIONAL_ORDER";     /* 11*/
    public static final String TABLE_tblUSER_EVALUATION       = "tblUSER_EVALUATION";       /* 12*/
    public static final String TABLE_tblEVALUATION_RESPONSE   = "tblEVALUATION_RESPONSE";   /* 13*/
    public static final String TABLE_tblNUMERICRESPONSE       = "tblNUMERICRESPONSE";       /* 14*/
    public static final String TABLE_tblTEXTRESPONSE          = "tblTEXTRESPONSE";          /* 15*/
    public static final String TABLE_tblDATERESPONSE          = "tblDATERESPONSE";          /* 16*/
    public static final String TABLE_tblARRAYRESPONSE         = "tblARRAYRESPONSE";         /* 17*/
    public static final String TABLE_tblMATRIXRESPONSE        = "tblMATRIXRESPONSE";        /* 18*/
    public static final String TABLE_tblPRIMITIVECHART        = "tblPRIMITIVECHART";        /* 19*/
    public static final String TABLE_tblARRAYCHART            = "tblARRAYCHART";            /* 20*/
    public static final String TABLE_tblMATRIXCHART           = "tblMATRIXCHART";           /* 21*/

    /*############################## END EVALUATION MODULE TABLES ################################*/

    /*############################ START MONITORING MODULE TABLES ################################*/

    public static final String TABLE_tblQUESTION_INDICATOR    = "tblQUESTION_INDICATOR";   /* 1  */
    public static final String TABLE_tblMETHOD                = "tblMETHOD";               /* 2  */
    public static final String TABLE_tblMOV                   = "tblMOV";                  /* 3  */
    public static final String TABLE_tblDATASOURCE            = "tblDATASOURCE";           /* 4  */
    public static final String TABLE_tblINDICATORTYPE         = "tblINDICATORTYPE";        /* 5  */
    public static final String TABLE_tblQUANTITATIVETYPE      = "tblQUANTITATIVETYPE";     /* 6  */
    public static final String TABLE_tblCRITERIASCORE         = "tblCRITERIASCORE";        /* 7  */
    public static final String TABLE_tblQUALITATIVECRITERIA   = "tblQUALITATIVECRITERIA";  /* 8  */
    public static final String TABLE_tblQUALITATIVESET        = "tblQUALITATIVESET";       /* 9  */
    public static final String TABLE_tblQUALITATIVESCORESET   = "tblQUALITATIVESCORESET";  /* 10 */
    public static final String TABLE_tblARRAYINDICATOR        = "tblARRAYINDICATOR";       /* 11 */
    public static final String TABLE_tblMATRIXINDICATOR       = "tblMATRIXINDICATOR";      /* 12 */
    public static final String TABLE_tblQUALITATIVEINDICATOR  = "tblQUALITATIVEINDICATOR"; /* 13 */
    public static final String TABLE_tblQUANTITATIVEINDICATOR = "tblQUANTITATIVEINDICATOR";/* 14 */
    public static final String TABLE_tblTARGET                = "tblTARGET";               /* 15 */
    public static final String TABLE_tblQUALITATIVETARGET     = "tblQUALITATIVETARGET";    /* 16 */
    public static final String TABLE_tblQUANTITATIVETARGET    = "tblQUANTITATIVETARGET";   /* 17 */
    public static final String TABLE_tblARRAYTARGET           = "tblARRAYTARGET";          /* 18 */
    public static final String TABLE_tblMATRIXTARGET          = "tblMATRIXTARGET";         /* 19 */
    public static final String TABLE_tblDATACOLLECTOR         = "tblDATACOLLECTOR";        /* 20 */
    public static final String TABLE_tblINDICATOR             = "tblINDICATOR";            /* 21 */
    public static final String TABLE_tblINDICATORMILESTONE    = "tblINDICATORMILESTONE";   /* 22 */
    public static final String TABLE_tblQUALITATIVEMILESTONE  = "tblQUALITATIVEMILESTONE"; /* 23 */
    public static final String TABLE_tblQUANTITATIVEMILESTONE = "tblQUANTITATIVEMILESTONE";/* 24 */
    public static final String TABLE_tblARRAYMILESTONE        = "tblARRAYMILESTONE";       /* 25 */
    public static final String TABLE_tblMATRIXMILESTONE       = "tblMATRIXMILESTONE";      /* 26 */
    public static final String TABLE_tblINDICATORPROGRESS     = "tblINDICATORPROGRESS";    /* 27 */
    public static final String TABLE_tblQUALITATIVEPROGRESS   = "tblQUALITATIVEPROGRESS";  /* 28 */
    public static final String TABLE_tblQUANTITATIVEPROGRESS  = "tblQUANTITATIVEPROGRESS"; /* 29 */
    public static final String TABLE_tblARRAYPROGRESS         = "tblARRAYPROGRESS";        /* 30 */
    public static final String TABLE_tblMATRIXPROGRESS        = "tblMATRIXPROGRESS";       /* 31 */
    public static final String TABLE_tblINDICATOR_MOV         = "tblINDICATOR_MOV";        /* 32 */
    public static final String TABLE_tblINDICATOR_DATASOURCE  = "tblINDICATOR_DATASOURCE"; /* 33 */

    /*############################## END MONITORING MODULE TABLES ################################*/

    /*################################ START RAID MODULE TABLES ##################################*/

    public static final String TABLE_tblPIM                   = "tblPIM";                  /* 1  */
    public static final String TABLE_tblRAIDLIKELIHOOD        = "tblRAIDLIKELIHOOD";       /* 2  */
    public static final String TABLE_tblRAIDIMPACT            = "tblRAIDIMPACT";           /* 3  */
    public static final String TABLE_tblROBOT                 = "tblROBOT";                /* 4  */
    public static final String TABLE_tblPIMSET                = "tblPIMSET";               /* 5  */
    public static final String TABLE_tblRAIDREGISTER          = "tblRAIDREGISTER";         /* 6  */
    public static final String TABLE_tblRISKREGISTER          = "tblRISKREGISTER";         /* 7  */
    public static final String TABLE_tblASSUMPTIONREGISTER    = "tblASSUMPTIONREGISTER";   /* 8  */
    public static final String TABLE_tblISSUEREGISTER         = "tblISSUEREGISTER";        /* 9  */
    public static final String TABLE_tblDEPENDENCYREGISTER    = "tblDEPENDENCYREGISTER";   /* 10 */
    public static final String TABLE_tblRAIDLOG               = "tblRAIDLOG";              /* 11 */
    public static final String TABLE_tblRISK                  = "tblRISK";                 /* 12 */
    public static final String TABLE_tblRISKROOTCAUSE         = "tblRISKROOTCAUSE";        /* 13 */
    public static final String TABLE_tblRISKCONSEQUENCE       = "tblRISKCONSEQUENCE";      /* 14 */
    public static final String TABLE_tblASSUMPTION            = "tblASSUMPTION";           /* 15 */
    public static final String TABLE_tblISSUE                 = "tblISSUE";                /* 16 */
    public static final String TABLE_tblDEPENDENCY            = "tblDEPENDENCY";           /* 17 */
    public static final String TABLE_tblMILESTONEREVIEW       = "tblMILESTONEREVIEW";      /* 18 */
    public static final String TABLE_tblREVIEWCOMMENT         = "tblREVIEWCOMMENT";        /* 19 */
    public static final String TABLE_tblRISKREVIEW            = "tblRISKREVIEW";           /* 20 */
    public static final String TABLE_tblASSUMPTIONREVIEW      = "tblASSUMPTIONREVIEW";     /* 21 */
    public static final String TABLE_tblISSUEREVIEW           = "tblISSUEREVIEW";          /* 22 */
    public static final String TABLE_tblDEPENDENCYREVIEW      = "tblDEPENDENCYREVIEW";     /* 23 */
    public static final String TABLE_tblACTION                = "tblACTION";               /* 24 */
    public static final String TABLE_tblACTIONTASK            = "tblACTIONTASK";           /* 25 */
    public static final String TABLE_tblMILESTONE_ACTION      = "tblMILESTONE_ACTION";     /* 26 */
    public static final String TABLE_tblRISKACTIONTYPE        = "tblRISKACTIONTYPE";       /* 27 */
    public static final String TABLE_tblRISKACTION            = "tblRISKACTION";           /* 28 */
    public static final String TABLE_tblCURRENTCONTROL        = "tblCURRENTCONTROL";       /* 29 */
    public static final String TABLE_tblADDITIONALCONTROL     = "tblADDITIONALCONTROL";    /* 30 */
    public static final String TABLE_tblASSUMPTIONACTION      = "tblASSUMPTIONACTION";     /* 31 */
    public static final String TABLE_tblISSUEACTION           = "tblISSUEACTION";          /* 32 */
    public static final String TABLE_tblDEPENDENCYACTION      = "tblDEPENDENCYACTION";     /* 33 */

    /*################################# END RAID MODULE TABLES ###################################*/

    /*################################ START AWPB MODULE TABLES ##################################*/

    public static final String TABLE_tblHUMAN                 = "tblHUMAN";                /* 1  */
    public static final String TABLE_tblMATERIAL              = "tblMATERIAL";             /* 2  */
    public static final String TABLE_tblINCOME                = "tblINCOME";               /* 3  */
    public static final String TABLE_tblEXPENSE               = "tblEXPENSE";              /* 4  */
    public static final String TABLE_tblHUMANSET              = "tblHUMANSET";             /* 5  */
    public static final String TABLE_tblFUND                  = "tblFUND";                 /* 6  */
    public static final String TABLE_tblMILESTONE             = "'tblMILESTONE'";          /* 7  */
    public static final String TABLE_tblTASK                  = "tblTASK";                 /* 8  */
    public static final String TABLE_tblACTIVITYTASK          = "tblACTIVITYTASK";         /* 9  */
    public static final String TABLE_tblPRECEDINGTASK         = "tblPRECEDINGTASK";        /* 10 */
    public static final String TABLE_tblTASK_MILESTONE        = "tblTASK_MILESTONE";       /* 11 */
    public static final String TABLE_tblTASKASSIGNMENT        = "tblTASKASSIGNMENT";       /* 12 */
    public static final String TABLE_tblUSERCOMMENT           = "tblUSERCOMMENT";          /* 13 */
    public static final String TABLE_tblTIMESHEET             = "tblTIMESHEET";            /* 14 */
    public static final String TABLE_tblINVOICE               = "tblINVOICE";              /* 15 */
    public static final String TABLE_tblINVOICE_TIMESHEET     = "tblINVOICE_TIMESHEET";    /* 16 */
    public static final String TABLE_tblEXTENSION             = "tblEXTENSION";            /* 17 */
    public static final String TABLE_tblEXT_DOC_TYPE          = "tblEXT_DOC_TYPE";         /* 18 */
    public static final String TABLE_tblDOCUMENT              = "tblDOCUMENT";             /* 19 */
    public static final String TABLE_tblTRANSACTION           = "tblTRANSACTION";          /* 20 */
    public static final String TABLE_tblINTERNAL              = "tblINTERNAL";             /* 21 */
    public static final String TABLE_tblEXTERNAL              = "tblEXTERNAL";             /* 22 */
    public static final String TABLE_tblJOURNAL               = "tblJOURNAL";              /* 23 */

    /*################################# END AWPB MODULE TABLES ###################################*/

    /*###################################### START OF KEYS #######################################*/

    public static final String KEY_GROUP_BITS  = "_bits_group";
    public static final String KEY_PERMS_BITS  = "_bits_perms";
    public static final String KEY_STATUS_BITS = "_bits_status";

    public static final String KEY_ID = "_id";
    public static final String KEY_ENTITY_TYPE_ID = "_id_entity_type";
    public static final String KEY_OWNER_ID = "_id_owner";
    public static final String KEY_ORG_ID = "_id_org";
    public static final String KEY_SERVER_ID = "_id_server";
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

    // logframe
    public static final String KEY_PARENT_FK_ID = "_id_parent_fk";
    public static final String KEY_CHILD_FK_ID = "_id_child_fk";
    public static final String KEY_LOGFRAME_FK_ID = "_id_logframe_fk";
    public static final String KEY_COMPONENT_FK_ID = "_id_component_fk";
    public static final String KEY_IMPACT_FK_ID = "_id_impact_fk";
    public static final String KEY_OUTCOME_FK_ID = "_id_outcome_fk";
    public static final String KEY_OUTPUT_FK_ID = "_id_output_fk";
    public static final String KEY_ACTIVITY_FK_ID = "_id_activity_fk";
    //public static final String KEY_WORKPLAN_FK_ID = "_id_workplan_fk";
    public static final String KEY_PRECEDING_ACTIVITY_FK_ID = "_id_preceding_activity_fk";
    public static final String KEY_INPUT_FK_ID = "_id_input_fk";
    public static final String KEY_RESOURCE_TYPE_FK_ID = "_id_resource_type";
    public static final String KEY_QUESTION_FK_ID = "_id_question_fk";
    public static final String KEY_PRIMITIVE_QUESTION_FK_ID = "_id_primitive_question_fk";
    public static final String KEY_ARRAY_QUESTION_FK_ID = "_id_array_question_fk";
    public static final String KEY_MATRIX_QUESTION_FK_ID = "_id_matrix_question_fk";
    public static final String KEY_ARRAY_SET_FK_ID = "_id_array_set_fk";
    public static final String KEY_MATRIX_SET_FK_ID = "_id_matrix_set_fk";
    public static final String KEY_PRIMITIVE_CHART_FK_ID = "_id_primitive_chart_fk";
    public static final String KEY_ARRAY_CHART_FK_ID = "_id_array_chart_fk";
    public static final String KEY_MATRIX_CHART_FK_ID = "_id_matrix_chart_fk";

    public static final String KEY_EVALUATION_TYPE_FK_ID = "_id_evaluation_type_fk";
    public static final String KEY_QUESTION_GROUPING_FK_ID = "_id_question_grouping_fk";
    public static final String KEY_QUESTION_TYPE_FK_ID = "_id_question_type_fk";
    public static final String KEY_RAID_CATEGORY_FK_ID = "_id_raid_category_fk";

    public static final String KEY_LABEL = "label";
    public static final String KEY_QUESTION = "question";
    public static final String KEY_DEFAULT_CHART = "default_chart";
    public static final String KEY_BASELINE_VALUE = "baseline_value";
    public static final String KEY_TARGET_VALUE = "target_value";
    public static final String KEY_ACTUAL_VALUE = "actual_value";
    public static final String KEY_DISAGGREGATED_BASELINE = "disaggregated_baseline";
    public static final String KEY_DISAGGREGATED_TARGET= "disaggregate_target";
    public static final String KEY_DISAGGREGATED_ACTUAL = "disaggregate_actual";
    public static final String KEY_QUANTITY = "quantity";
    public static final String KEY_INCOME = "income";
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

    // evaluation
    public static final String KEY_ARRAY_CHOICE_FK_ID = "_id_array_choice_fk";
    public static final String KEY_ROW_CHOICE_FK_ID = "_id_row_choice_fk";
    public static final String KEY_COL_CHOICE_FK_ID = "_id_col_choice_fk";
    public static final String KEY_QUALITATIVE_CRITERIA_FK_ID = "_id_qualitative_criteria_fk";
    public static final String KEY_CRITERIA_SCORE_FK_ID = "_id_criteria_score_fk";
    public static final String KEY_QUANTITATIVE_INDICATOR_FK_ID = "_id_quantitative_indicator_fk";
    public static final String KEY_QUANTITATIVE_PROGRESS_FK_ID = "_id_quantitative_progress_fk";
    public static final String KEY_QUANTITATIVE_TYPE_FK_ID = "_id_qualitative_type_fk";
    public static final String KEY_QUALITATIVE_SET_FK_ID = "_id_qualitative_set_fk";
    public static final String KEY_QUANTITATIVE_TARGET_FK_ID = "_id_quantitative_target_fk";
    public static final String KEY_QUANTITATIVE_MILESTONE_FK_ID = "_id_quantitative_milestone_fk";
    public static final String KEY_TARGET_FK_ID = "_id_target_fk";
    public static final String KEY_BASELINE_SCORE_FK_ID = "_id_baseline_score_fk";
    public static final String KEY_TARGET_SCORE_FK_ID = "_id_target_score_fk";
    public static final String KEY_PROGRESS_FK_ID = "_id_progress_fk";
    public static final String KEY_ACTUAL_SCORE_FK_ID = "_id_actual_score_fk";

    public static final String KEY_MATRIX_CHOICE_FK_ID = "_id_matrix_choice_fk";
    public static final String KEY_CRITERIA_FK_ID = "_id_criteria_fk";
    public static final String KEY_EVALUATION_FK_ID = "_id_evaluation_fk";
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

    public static final String KEY_INDICATOR_TYPE_FK_ID = "_id_indicator_type_fk";
    public static final String KEY_DATA_COLLECTOR_FK_ID = "_id_data_collector_fk";
    public static final String KEY_METHOD_FK_ID = "_id_method_fk";
    //public static final String KEY_EVALUATION_FK_ID = "_id_evaluation_fk";
    public static final String KEY_QUESTION_ORDER_FK_ID = "_id_question_order_fk";
    public static final String KEY_QUESTION_RESPONSE_FK_ID = "_id_question_response_fk";
    public static final String KEY_POS_RES_ORDER_FK_ID = "_id_pos_res_order_fk";
    public static final String KEY_NEG_RES_ORDER_FK_ID = "_id_neg_res_order_fk";
    public static final String KEY_INDICATOR_FK_ID = "_id_indicator_fk";
    public static final String KEY_MOV_FK_ID = "_id_mov_fk";
    public static final String KEY_DATA_SOURCE_FK_ID = "_id_data_source_fk";
    //public static final String KEY_QUALITATIVE_CHOICE_FK_ID = "_id_qualitative_choice_fk";
    public static final String KEY_INDICATOR_MILESTONE_FK_ID = "_id_indicator_milestone_fk";
    //public static final String KEY_UNIT_FK_ID = "_id_unit_fk";

    public static final String KEY_SCORE = "score";

    public static final String KEY_EVALUATION_RESPONSE_FK_ID = "_id_evaluation_response_fk";
    public static final String KEY_NUMERIC_RESPONSE = "_id_numeric_response";
    public static final String KEY_TEXT_RESPONSE = "_id_text_response";
    public static final String KEY_DATE_RESPONSE = "_id_date_response";
    public static final String KEY_ARRAY_RESPONSE_FK_ID = "_id_array_response_fk";
   // public static final String KEY_MATRIX_RESPONSE_FK_ID = "_id_matrix_response_fk";
    public static final String KEY_ROW_RESPONSE_FK_ID = "_id_row_response_fk";
    public static final String KEY_COL_RESPONSE_FK_ID = "_id_col_response_fk";
    public static final String KEY_CHART_FK_ID = "_id_chart_fk";

    //public static final String KEY_HUMAN_FK_ID = "_id_human_fk";
    //public static final String KEY_INCOME_FK_ID = "_id_income_fk";

    public static final String KEY_FUNDER_FK_ID = "_id_funder_fk";
    public static final String KEY_BENEFICIARY_FK_ID = "_id_beneficiary_fk";
    public static final String KEY_TASK_FK_ID = "_id_task_fk";
    public static final String KEY_PRECEDING_TASK_FK_ID = "_id_preceding_task_fk";
    public static final String KEY_STAFF_FK_ID = "_id_staff_fk";
    public static final String KEY_EXTENSION_FK_ID = "_id_extension_fk";
    public static final String KEY_EXT_DOC_TYPE_FK_ID = "_id_ext_doc_type_fk";
    public static final String KEY_DOCUMENT_FK_ID = "_id_document_fk";
    public static final String KEY_TRANSACTION_FK_ID = "_id_transaction_fk";

    public static final String KEY_USER_EVALUATION_FK_ID = "_id_user_evaluation_fk";
    public static final String KEY_FREQUENCY_FK_ID = "_id_frequency_fk";

    //public static final String KEY_RISK_CRITERIA_FK_ID = "_id_risk_criteria_fk";
    //public static final String KEY_RISK_PLAN_FK_ID = "_id_risk_plan_fk";
    //public static final String KEY_RISK_INDEX = "_id_risk_index_fk";
    //public static final String KEY_RISK_MILESTONE_FK_ID = "_id_risk_milestone_fk";
    public static final String KEY_RAIDLIKELIHOOD_VALUE = "value";
    public static final String KEY_RAIDIMPACT_VALUE = "value";

    public static final String KEY_RISK_ANALYSIS_FK_ID = "_id_risk_analysis_fk";

    public static final String KEY_STATUSSET_FK_ID = "_id_statusset_fk";
    public static final String KEY_PERMISSION_FK_ID = "_id_permission_fk";

    public static final String KEY_FISCAL_YEAR_FK_ID = "_id_fiscal_year_fk";
    public static final String KEY_INVOICE_FK_ID = "_id_invoice_fk";
    public static final String KEY_PERIOD_FK_ID = "_id_period_fk";
    public static final String KEY_TIMESHEET_FK_ID = "_id_timesheet_fk";

    //public static final String KEY_USER_ID = "_id_todelete";
    //public static final String KEY_ORGANIZATION_ID = "_id_todelete";
    //public static final String KEY_STATUS_ID = "_id_todelete";

    // RAID
    public static final String KEY_PIM_FK_ID = "_id_pim_fk";
    public static final String KEY_RAID_LIKELIHOOD_FK_ID = "_id_raid_likelihood_fk";
    public static final String KEY_RAID_IMPACT_FK_ID = "_id_raid_impact_fk";
    public static final String KEY_ROBOT_FK_ID = "_id_robot_fk";
    public static final String KEY_REGISTER_FK_ID = "_id_register_fk";
    public static final String KEY_LOWER_LIMIT = "lower_limit";
    public static final String KEY_UPPER_LIMIT = "upper_limit";
    public static final String KEY_COLOUR_NAME = "colour_name";

    public static final String KEY_RISK_REGISTER_FK_ID = "_id_risk_register_fk";
    public static final String KEY_ASS_REGISTER_FK_ID = "_id_ass_register_fk";
    public static final String KEY_ISSUE_REGISTER_FK_ID = "_id_issue_register_fk";
    public static final String KEY_DEP_REGISTER_FK_ID = "_id_dep_register_fk";

    public static final String KEY_MILESTONE_REVIEW_FK_ID = "_id_milestone_review_fk";
    public static final String KEY_RISK_FK_ID = "_id_risk_fk";
    public static final String KEY_ASSUMPTION_FK_ID = "_id_assumption_fk";
    public static final String KEY_ISSUE_FK_ID = "_id_issue_fk";
    public static final String KEY_DEPENDENCY_FK_ID = "_id_dependency_fk";

    public static final String KEY_RESIDUAL_RISK = "residual_risk";
    public static final String KEY_ASSUMPTION_RATING = "assumption_rating";
    public static final String KEY_PRIORITY_RATING = "priority_rating";
    public static final String KEY_DEPENDENCY_RATING = "dependency_rating";

    public static final String KEY_ACTION_FK_ID = "_id_action_fk";
    public static final String KEY_RISK_REVIEW_FK_ID = "_id_risk_review_fk";
    public static final String KEY_RISK_ACTION_TYPE_FK_ID = "_id_risk_action_type_fk";

    public static final String KEY_ASSUMPTION_REVIEW_FK_ID = "_id_assumption_review_fk";
    public static final String KEY_ISSUE_REVIEW_FK_ID = "_id_issue_review_fk";
    public static final String KEY_DEPENDENCY_REVIEW_FK_ID = "_id_dependency_review_fk";
    public static final String KEY_ACTION_TASK_FK_ID = "_id_action_task_fk";
    public static final String KEY_RISK_ACTION_FK_ID = "_id_risk_action_fk";

    public static final String KEY_ORIGINATOR_FK_ID = "_id_originator_fk";
    public static final String KEY_OWNER_FK_ID = "_id_owner_fk";

    public static final String KEY_HOW_VALIDATE = "_id_how_validate";
    public static final String KEY_VALIDATED = "_id_validated";

    public static final String KEY_REVIEW_COMMENT = "_id_review_comment";

    /*######################################### END OF KEYS ######################################*/


    /*#################################### START of GLOBAL MODULE ################################*/

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblFREQUENCY` (1)
    //-- -------------------------------------------------------------------------------------------
    private static final String CREATE_TABLE_tblFREQUENCY = "CREATE TABLE " + TABLE_tblFREQUENCY + "("
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

    private static final String CREATE_TABLE_tblPERIOD = "CREATE TABLE " + TABLE_tblPERIOD + "("
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

    private static final String CREATE_TABLE_tblFISCALYEAR = "CREATE TABLE " + TABLE_tblFISCALYEAR + "("
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

    private static final String CREATE_TABLE_tblCHART = "CREATE TABLE " + TABLE_tblCHART + "("
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
    private static final String CREATE_TABLE_tblADDRESS = "CREATE TABLE " + TABLE_tblADDRESS + "("
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
    private static final String CREATE_TABLE_tblORGANIZATION = "CREATE TABLE " +
            TABLE_tblORGANIZATION + "("
            + KEY_ID + " INTEGER NOT NULL, "
            + KEY_PARENT_FK_ID + " INTEGER, "
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
            + "FOREIGN KEY (" + KEY_PARENT_FK_ID + ") "
            + "REFERENCES " + TABLE_tblORGANIZATION + "(" + KEY_ID + ") "
            + "ON DELETE CASCADE ON UPDATE CASCADE);";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblFUNDER` (3)
    //-- -------------------------------------------------------------------------------------------
    private static final String CREATE_TABLE_tblFUNDER = "CREATE TABLE " +
            TABLE_tblFUNDER + "("
            + KEY_ORGANIZATION_FK_ID + " INTEGER NOT NULL, "
            + "PRIMARY KEY (" + KEY_ORGANIZATION_FK_ID + "), "
            + "FOREIGN KEY (" + KEY_ORGANIZATION_FK_ID + ") "
            + "REFERENCES " + TABLE_tblORGANIZATION + "(" + KEY_ID + ") "
            + "ON DELETE CASCADE ON UPDATE CASCADE);";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblBENEFICIARY` (4)
    //-- -------------------------------------------------------------------------------------------
    private static final String CREATE_TABLE_tblBENEFICIARY = "CREATE TABLE " +
            TABLE_tblBENEFICIARY + "("
            + KEY_ORGANIZATION_FK_ID + " INTEGER NOT NULL, "
            + "PRIMARY KEY (" + KEY_ORGANIZATION_FK_ID + "), "
            + "FOREIGN KEY (" + KEY_ORGANIZATION_FK_ID + ") "
            + "REFERENCES " + TABLE_tblORGANIZATION + "(" + KEY_ID + ") "
            + "ON DELETE CASCADE ON UPDATE CASCADE);";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblIMPLEMENTINGAGENCY` (5)
    //-- -------------------------------------------------------------------------------------------
    private static final String CREATE_TABLE_tblIMPLEMENTINGAGENCY = "CREATE TABLE " +
            TABLE_tblIMPLEMENTINGAGENCY+ "("
            + KEY_ORGANIZATION_FK_ID + " INTEGER NOT NULL, "
            + "PRIMARY KEY (" + KEY_ORGANIZATION_FK_ID + "), "
            + "FOREIGN KEY (" + KEY_ORGANIZATION_FK_ID + ") "
            + "REFERENCES " + TABLE_tblORGANIZATION + "(" + KEY_ID + ") "
            + "ON DELETE CASCADE ON UPDATE CASCADE);";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblCROWDFUND` (6)
    //-- -------------------------------------------------------------------------------------------
    public static final String CREATE_TABLE_tblCROWDFUND = "CREATE TABLE " +
            TABLE_tblCROWDFUND + "("
            + KEY_ID + " INTEGER NOT NULL, "
            + KEY_FUNDER_FK_ID + " INTEGER NOT NULL, "
            + KEY_BENEFICIARY_FK_ID + " INTEGER NOT NULL, "
            + KEY_LOGFRAME_FK_ID + " INTEGER NOT NULL, "
            + KEY_SERVER_ID + " INTEGER DEFAULT NULL, "
            + KEY_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL DEFAULT 0, "
            + KEY_FUND + " DOUBLE, "
            + KEY_DESCRIPTION + " TEXT, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + " PRIMARY KEY (" + KEY_ID + " ), "
            + " FOREIGN KEY (" + KEY_FUNDER_FK_ID + ") "
            + " REFERENCES " + TABLE_tblFUNDER + " (" + KEY_ORGANIZATION_FK_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_BENEFICIARY_FK_ID + ") "
            + " REFERENCES " + TABLE_tblBENEFICIARY + " (" + KEY_ORGANIZATION_FK_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_LOGFRAME_FK_ID + ") "
            + " REFERENCES " + TABLE_tblLOGFRAME + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE); ";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblUSER` (6)
    //-- -------------------------------------------------------------------------------------------
    private static final String CREATE_TABLE_tblUSER = "CREATE TABLE " + TABLE_tblUSER + "("
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
    private static final String CREATE_TABLE_tblSESSION = "CREATE TABLE " + TABLE_tblSESSION + "("
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
    private static final String CREATE_TABLE_tblVALUE = "CREATE TABLE " + TABLE_tblVALUE + "("
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
    private static final String CREATE_TABLE_tblROLE = "CREATE TABLE " + TABLE_tblROLE + "("
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
            + "PRIMARY KEY (" + KEY_ID + ", " + KEY_ORGANIZATION_FK_ID + "),"
            + "FOREIGN KEY (" + KEY_ORGANIZATION_FK_ID + ") "
            + "REFERENCES " + TABLE_tblORGANIZATION + " (" + KEY_ID + ") "
            + "ON DELETE CASCADE ON UPDATE CASCADE);";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblMENU` (10)
    //-- -------------------------------------------------------------------------------------------
    private static final String CREATE_TABLE_tblMENU = "CREATE TABLE " + TABLE_tblMENU + "("
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
    //-- Table `tblENTITY` (11)
    //-- -------------------------------------------------------------------------------------------
    private static final String CREATE_TABLE_tblENTITY = "CREATE TABLE " + TABLE_tblENTITY + "("
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
    //-- Table `tblOPERATION` (12)
    //-- -------------------------------------------------------------------------------------------
    private static final String CREATE_TABLE_tblOPERATION = "CREATE TABLE " + TABLE_tblOPERATION + "("
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
    //-- Table `tblSTATUS` (13)
    //-- -------------------------------------------------------------------------------------------
    private static final String CREATE_TABLE_tblSTATUS = "CREATE TABLE " + TABLE_tblSTATUS + "("
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
    //-- Table `tblSTATUSSET` (14)
    //-- -------------------------------------------------------------------------------------------
    private static final String CREATE_TABLE_tblSTATUSSET = "CREATE TABLE " + TABLE_tblSTATUSSET +"("
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
    private static final String CREATE_TABLE_tblORG_ADDRESS = "CREATE TABLE " +
            TABLE_tblORG_ADDRESS + "("
            + KEY_ORGANIZATION_FK_ID + " INTEGER NOT NULL, "
            + KEY_ADDRESS_FK_ID + " INTEGER NOT NULL, "
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
    private static final String CREATE_TABLE_tblUSER_ADDRESS = "CREATE TABLE " +
            TABLE_tblUSER_ADDRESS + "("
            + KEY_USER_FK_ID + " INTEGER NOT NULL, "
            + KEY_ADDRESS_FK_ID + " INTEGER NOT NULL, "
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
    private static final String CREATE_TABLE_tblUSER_ROLE = "CREATE TABLE " + TABLE_tblUSER_ROLE + "("
            + KEY_USER_FK_ID + " INTEGER NOT NULL, "
            + KEY_ROLE_FK_ID + " INTEGER NOT NULL, "
            + KEY_ORGANIZATION_FK_ID + " INTEGER NOT NULL, "
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
    private static final String CREATE_TABLE_tblSESSION_ROLE = "CREATE TABLE " +
            TABLE_tblSESSION_ROLE + "("
            + KEY_SESSION_FK_ID + " INTEGER NOT NULL, "
            + KEY_ROLE_FK_ID + " INTEGER NOT NULL, "
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
    private static final String CREATE_TABLE_tblMENU_ROLE = "CREATE TABLE " + TABLE_tblMENU_ROLE + "("
            + KEY_MENU_FK_ID + " INTEGER NOT NULL, "
            + KEY_ROLE_FK_ID + " INTEGER NOT NULL, "
            + KEY_ORGANIZATION_FK_ID + " INTEGER NOT NULL, "
            + " PRIMARY KEY (" + KEY_MENU_FK_ID + "," + KEY_ROLE_FK_ID + ","
            + KEY_ORGANIZATION_FK_ID + " ), "
            + " FOREIGN KEY (" + KEY_MENU_FK_ID + ") "
            + " REFERENCES " + TABLE_tblMENU + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_ROLE_FK_ID + "," + KEY_ORGANIZATION_FK_ID + ") "
            + " REFERENCES " + TABLE_tblROLE + " (" + KEY_ID + "," + KEY_ORGANIZATION_FK_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE); ";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblPRIVILEGE` (20)
    //-- -------------------------------------------------------------------------------------------
    private static final String CREATE_TABLE_tblPRIVILEGE = "CREATE TABLE " + TABLE_tblPRIVILEGE +"("
            + KEY_PERMISSION_FK_ID + " INTEGER NOT NULL, "
            + KEY_ROLE_FK_ID + " INTEGER NOT NULL, "
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
            + "PRIMARY KEY (" + KEY_PERMISSION_FK_ID + "," + KEY_ROLE_FK_ID + ","
            + KEY_ORGANIZATION_FK_ID + "),"
            + " FOREIGN KEY (" + KEY_PERMISSION_FK_ID + ") "
            + " REFERENCES " + TABLE_tblPERMISSION + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_ROLE_FK_ID + "," + KEY_ORGANIZATION_FK_ID + ") "
            + " REFERENCES " + TABLE_tblROLE + "(" + KEY_ID +","+ KEY_ORGANIZATION_FK_ID +")"
            + " ON DELETE CASCADE ON UPDATE CASCADE);";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblPERMISSION` (21)
    //-- -------------------------------------------------------------------------------------------
    private static final String CREATE_TABLE_tblPERMISSION = "CREATE TABLE " +
            TABLE_tblPERMISSION + "("
            + KEY_ID + " INTEGER NOT NULL, "
            + KEY_ENTITY_FK_ID + " INTEGER NOT NULL, "
            + KEY_ENTITY_TYPE_FK_ID + " INTEGER NOT NULL, "
            + KEY_OPERATION_FK_ID + " INTEGER NOT NULL, "
            + KEY_STATUSSET_FK_ID + " INTEGER DEFAULT NULL, "
            + KEY_SERVER_ID + " INTEGER DEFAULT NULL, "
            + KEY_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL DEFAULT 0, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + " PRIMARY KEY (" + KEY_ID + "," + KEY_ENTITY_FK_ID + ", "
            + KEY_ENTITY_TYPE_FK_ID + "," + KEY_OPERATION_FK_ID + "),"
            + " FOREIGN KEY (" + KEY_ENTITY_FK_ID + "," + KEY_ENTITY_TYPE_FK_ID + ") "
            + " REFERENCES " + TABLE_tblENTITY + " (" + KEY_ID + ", " + KEY_ENTITY_TYPE_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_OPERATION_FK_ID + ") "
            + " REFERENCES " + TABLE_tblOPERATION + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_STATUSSET_FK_ID + ") "
            + " REFERENCES " + TABLE_tblSTATUSSET + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE); ";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblPRIVILEGE_STATUS` (22)
    //-- -------------------------------------------------------------------------------------------
    private static final String CREATE_TABLE_tblSTATUSSET_STATUS = "CREATE TABLE " +
            TABLE_tblSTATUSSET_STATUS + "("
            + KEY_STATUSSET_FK_ID + " INTEGER NOT NULL, "
            + KEY_STATUS_FK_ID + " INTEGER NOT NULL, "
            + " PRIMARY KEY (" + KEY_STATUSSET_FK_ID + ", " + KEY_STATUS_FK_ID + " ), "
            + " FOREIGN KEY (" + KEY_STATUSSET_FK_ID + ") "
            + " REFERENCES " + TABLE_tblSTATUSSET + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_STATUS_FK_ID + ") "
            + " REFERENCES " + TABLE_tblSTATUS + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE); ";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblSETTING` (23)
    //-- -------------------------------------------------------------------------------------------
    private static final String CREATE_TABLE_tblSETTING = "CREATE TABLE " + TABLE_tblSETTING + "("
            + KEY_ID + " INTEGER NOT NULL, "
            + KEY_SERVER_ID + " INTEGER DEFAULT NULL, "
            + KEY_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL DEFAULT 0, "
            + KEY_NAME + " TEXT, "
            + KEY_DESCRIPTION + " TEXT, "
            + KEY_SETTING_VALUE + " INTEGER, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + "PRIMARY KEY (" + KEY_ID + "));";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblNOTIFICATION` (24)
    //-- -------------------------------------------------------------------------------------------
    private static final String CREATE_TABLE_tblNOTIFICATION = "CREATE TABLE " +
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
    //-- Table `tblPUBLISHER` (25)
    //-- -------------------------------------------------------------------------------------------
    private static final String CREATE_TABLE_tblPUBLISHER = "CREATE TABLE " + TABLE_tblPUBLISHER + "("
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
    //-- Table `tblSUBSCRIBER` (26)
    //-- -------------------------------------------------------------------------------------------
    private static final String CREATE_TABLE_tblSUBSCRIBER = "CREATE TABLE " + TABLE_tblSUBSCRIBER + "("
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
    //-- Table `tblNOTIFY_SETTING` (27)
    //-- -------------------------------------------------------------------------------------------
    private static final String CREATE_TABLE_tblNOTIFY_SETTING = "CREATE TABLE " +
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
    //-- Table `tblACTIVITYLOG` (28)
    //-- -------------------------------------------------------------------------------------------
    private static final String CREATE_TABLE_tblACTIVITYLOG = "CREATE TABLE " +
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
    private static final String CREATE_TABLE_tblLOGFRAME = "CREATE TABLE " + TABLE_tblLOGFRAME + "("
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
            + " FOREIGN KEY (" + KEY_ORGANIZATION_FK_ID + ") "
            + " REFERENCES " + TABLE_tblORGANIZATION + "(" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE);";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblLOGFRAMETREE` (2)
    //-- -------------------------------------------------------------------------------------------
    private static final String CREATE_TABLE_tblLOGFRAMETREE = "CREATE TABLE " +
            TABLE_tblLOGFRAMETREE + "("
            + KEY_PARENT_FK_ID + " INTEGER NOT NULL, "
            + KEY_CHILD_FK_ID + " INTEGER NOT NULL, "
            + " PRIMARY KEY (" + KEY_PARENT_FK_ID + "," + KEY_CHILD_FK_ID + " ), "
            + " FOREIGN KEY (" + KEY_PARENT_FK_ID + ") "
            + " REFERENCES " + TABLE_tblLOGFRAME + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_CHILD_FK_ID + ")"
            + " REFERENCES " + TABLE_tblLOGFRAME + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE);";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblRESOURCETYPE` (3)
    //-- -------------------------------------------------------------------------------------------
    private static final String CREATE_TABLE_tblRESOURCETYPE = "CREATE TABLE " +
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
    //-- Table `tblCOMPONENT` (4)
    //-- -------------------------------------------------------------------------------------------
    private static final String CREATE_TABLE_tblCOMPONENT = "CREATE TABLE " + TABLE_tblCOMPONENT+"("
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
    //-- Table `tblIMPACT` (5)
    //-- -------------------------------------------------------------------------------------------
    private static final String CREATE_TABLE_tblIMPACT = "CREATE TABLE " + TABLE_tblIMPACT + "("
            + KEY_COMPONENT_FK_ID + " INTEGER NOT NULL, "
            + KEY_PARENT_FK_ID + " INTEGER DEFAULT NULL, "
            + " PRIMARY KEY (" + KEY_COMPONENT_FK_ID + " ), "
            + " FOREIGN KEY (" + KEY_COMPONENT_FK_ID + ") "
            + " REFERENCES " + TABLE_tblCOMPONENT + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_PARENT_FK_ID + ")"
            + " REFERENCES " + TABLE_tblIMPACT + " (" + KEY_COMPONENT_FK_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE);";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblOUTCOME` (6)
    //-- -------------------------------------------------------------------------------------------
    private static final String CREATE_TABLE_tblOUTCOME = "CREATE TABLE " + TABLE_tblOUTCOME + "("
            + KEY_COMPONENT_FK_ID + " INTEGER NOT NULL, "
            + KEY_PARENT_FK_ID + " INTEGER DEFAULT NULL, "
            + KEY_IMPACT_FK_ID + " INTEGER NOT NULL, "
            + " PRIMARY KEY (" + KEY_COMPONENT_FK_ID + " ), "
            + " FOREIGN KEY (" + KEY_COMPONENT_FK_ID + ") "
            + " REFERENCES " + TABLE_tblCOMPONENT + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_PARENT_FK_ID + ") "
            + " REFERENCES " + TABLE_tblOUTCOME + " (" + KEY_COMPONENT_FK_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_IMPACT_FK_ID + ")"
            + " REFERENCES " + TABLE_tblIMPACT + " (" + KEY_COMPONENT_FK_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE);";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblOUTPUT` (7)
    //-- -------------------------------------------------------------------------------------------
    private static final String CREATE_TABLE_tblOUTPUT = "CREATE TABLE " + TABLE_tblOUTPUT + "("
            + KEY_COMPONENT_FK_ID + " INTEGER NOT NULL, "
            + KEY_PARENT_FK_ID + " INTEGER DEFAULT NULL, "
            + KEY_OUTCOME_FK_ID + " INTEGER NOT NULL, "
            + " PRIMARY KEY (" + KEY_COMPONENT_FK_ID + " ), "
            + " FOREIGN KEY (" + KEY_COMPONENT_FK_ID + ") "
            + " REFERENCES " + TABLE_tblCOMPONENT + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_PARENT_FK_ID + ") "
            + " REFERENCES " + TABLE_tblOUTPUT + " (" + KEY_COMPONENT_FK_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_OUTCOME_FK_ID + ")"
            + " REFERENCES " + TABLE_tblOUTCOME + " (" + KEY_COMPONENT_FK_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE);";

//    //-- -------------------------------------------------------------------------------------------
//    //-- Table `tblWORKPLAN` (6)
//    //-- -------------------------------------------------------------------------------------------
//    private static final String CREATE_TABLE_tblWORKPLAN = "CREATE TABLE " +
//            TABLE_tblWORKPLAN + "("
//            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
//            + KEY_LOGFRAME_FK_ID + " INTEGER NOT NULL, "
//            + KEY_SERVER_ID + " INTEGER DEFAULT NULL, "
//            + KEY_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
//            + KEY_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
//            + KEY_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
//            + KEY_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
//            + KEY_STATUS_BITS + " INTEGER NOT NULL DEFAULT 0, "
//            + KEY_NAME + " TEXT NOT NULL, "
//            + KEY_DESCRIPTION + " TEXT NULL, "
//            + KEY_START_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
//            + KEY_END_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
//            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
//            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
//            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
//            + " FOREIGN KEY (" + KEY_LOGFRAME_FK_ID + ")"
//            + " REFERENCES " + TABLE_tblLOGFRAME + " (" + KEY_ID + ") "
//            + " ON DELETE CASCADE ON UPDATE CASCADE);";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblACTIVITY` (8)
    //-- -------------------------------------------------------------------------------------------
    private static final String CREATE_TABLE_tblACTIVITY = "CREATE TABLE " + TABLE_tblACTIVITY + "("
            + KEY_COMPONENT_FK_ID + " INTEGER NOT NULL, "
            + KEY_PARENT_FK_ID + " INTEGER DEFAULT NULL, "
            + KEY_OUTPUT_FK_ID + " INTEGER NOT NULL, "
            + " PRIMARY KEY (" + KEY_COMPONENT_FK_ID + " ), "
            + " FOREIGN KEY (" + KEY_COMPONENT_FK_ID + ") "
            + " REFERENCES " + TABLE_tblCOMPONENT + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_PARENT_FK_ID + ") "
            + " REFERENCES " + TABLE_tblACTIVITY + " (" + KEY_COMPONENT_FK_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_OUTPUT_FK_ID + ")"
            + " REFERENCES " + TABLE_tblOUTPUT + " (" + KEY_COMPONENT_FK_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE);";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblINPUT` (9)
    //-- -------------------------------------------------------------------------------------------
    private static final String CREATE_TABLE_tblINPUT = "CREATE TABLE " + TABLE_tblINPUT + "("
            + KEY_COMPONENT_FK_ID + " INTEGER NOT NULL, "
            + KEY_ACTIVITY_FK_ID + " INTEGER NOT NULL, "
            + KEY_RESOURCE_TYPE_FK_ID + " INTEGER NOT NULL, "
            + " PRIMARY KEY (" + KEY_COMPONENT_FK_ID + " ), "
            + " FOREIGN KEY (" + KEY_COMPONENT_FK_ID + ") "
            + " REFERENCES " + TABLE_tblCOMPONENT + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_ACTIVITY_FK_ID + ") "
            + " REFERENCES " + TABLE_tblACTIVITY + " (" + KEY_COMPONENT_FK_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_RESOURCE_TYPE_FK_ID + ")"
            + " REFERENCES " + TABLE_tblRESOURCETYPE + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE);";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblPRECEDINGACTIVITY` (10)
    //-- -------------------------------------------------------------------------------------------
    private static final String CREATE_TABLE_tblPRECEDINGACTIVITY = "CREATE TABLE " +
            TABLE_tblPRECEDINGACTIVITY + "("
            + KEY_ACTIVITY_FK_ID + " INTEGER NOT NULL, "
            + KEY_PRECEDING_ACTIVITY_FK_ID + " INTEGER NOT NULL, "
            + " PRIMARY KEY (" + KEY_ACTIVITY_FK_ID + "," + KEY_PRECEDING_ACTIVITY_FK_ID + "),"
            + " FOREIGN KEY (" + KEY_ACTIVITY_FK_ID + ") "
            + " REFERENCES " + TABLE_tblACTIVITY + " (" + KEY_ACTIVITY_FK_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_PRECEDING_ACTIVITY_FK_ID + ")"
            + " REFERENCES " + TABLE_tblACTIVITY + " (" + KEY_ACTIVITY_FK_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE);";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblACTIVITYASSIGNMENT` (11)
    //-- -------------------------------------------------------------------------------------------
    private static final String CREATE_TABLE_tblACTIVITYASSIGNMENT = "CREATE TABLE " +
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
            + " REFERENCES " + TABLE_tblACTIVITY + " (" + KEY_ACTIVITY_FK_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_STAFF_FK_ID + ")"
            + " REFERENCES " + TABLE_tblHUMANSET + " (" + KEY_ID + ")"
            + " ON DELETE CASCADE ON UPDATE CASCADE);";


//    //-- -------------------------------------------------------------------------------------------
//    //-- Table `tblRESOURCE` (10)
//    //-- -------------------------------------------------------------------------------------------
//    private static final String CREATE_TABLE_tblRESOURCE = "CREATE TABLE " + TABLE_tblRESOURCE + "("
//            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
//            + KEY_RESOURCE_TYPE_FK_ID + " INTEGER NOT NULL, "
//            + KEY_SERVER_ID + " INTEGER DEFAULT NULL, "
//            + KEY_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
//            + KEY_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
//            + KEY_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
//            + KEY_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
//            + KEY_STATUS_BITS + " INTEGER NOT NULL DEFAULT 0, "
//            + KEY_NAME + " TEXT NOT NULL, "
//            + KEY_DESCRIPTION + " TEXT NULL, "
//            + KEY_START_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
//            + KEY_END_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
//            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
//            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
//            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
//            + " FOREIGN KEY (" + KEY_RESOURCE_TYPE_FK_ID + ")"
//            + " REFERENCES " + TABLE_tblRESOURCETYPE + " (" + KEY_ID + ")"
//            + " ON DELETE CASCADE ON UPDATE CASCADE);";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblOUTCOME_IMPACT` (12)
    //-- -------------------------------------------------------------------------------------------
    private static final String CREATE_TABLE_tblOUTCOME_IMPACT = "CREATE TABLE " +
            TABLE_tblOUTCOME_IMPACT + "("
            + KEY_OUTCOME_FK_ID + " INTEGER NOT NULL, "
            + KEY_IMPACT_FK_ID + " INTEGER NOT NULL, "
            + KEY_PARENT_FK_ID + " INTEGER NOT NULL, "
            + KEY_CHILD_FK_ID + " INTEGER NOT NULL, "
            + " PRIMARY KEY (" + KEY_OUTCOME_FK_ID + "," + KEY_IMPACT_FK_ID + ", "
            + KEY_PARENT_FK_ID + "," + KEY_CHILD_FK_ID + " ), "
            + " FOREIGN KEY (" + KEY_OUTCOME_FK_ID + ") "
            + " REFERENCES " + TABLE_tblOUTCOME + " (" + KEY_COMPONENT_FK_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_IMPACT_FK_ID + ") "
            + " REFERENCES " + TABLE_tblIMPACT + " (" + KEY_COMPONENT_FK_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_PARENT_FK_ID + "," + KEY_CHILD_FK_ID + ")"
            + " REFERENCES " + TABLE_tblLOGFRAMETREE + " (" + KEY_PARENT_FK_ID + ","
            + KEY_CHILD_FK_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE);";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblOUTPUT_OUTCOME` (13)
    //-- -------------------------------------------------------------------------------------------
    private static final String CREATE_TABLE_tblOUTPUT_OUTCOME = "CREATE TABLE " +
            TABLE_tblOUTPUT_OUTCOME + "("
            + KEY_OUTPUT_FK_ID + " INTEGER NOT NULL, "
            + KEY_OUTCOME_FK_ID + " INTEGER NOT NULL, "
            + KEY_PARENT_FK_ID + " INTEGER NOT NULL, "
            + KEY_CHILD_FK_ID + " INTEGER NOT NULL, "
            + " PRIMARY KEY (" + KEY_OUTPUT_FK_ID + "," + KEY_OUTCOME_FK_ID + ", "
            + KEY_PARENT_FK_ID + "," + KEY_CHILD_FK_ID + " ), "
            + " FOREIGN KEY (" + KEY_OUTPUT_FK_ID + ") "
            + " REFERENCES " + TABLE_tblOUTPUT + " (" + KEY_COMPONENT_FK_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_OUTCOME_FK_ID + ") "
            + " REFERENCES " + TABLE_tblOUTCOME + " (" + KEY_COMPONENT_FK_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_PARENT_FK_ID + "," + KEY_CHILD_FK_ID + ")"
            + " REFERENCES " + TABLE_tblLOGFRAMETREE + " (" + KEY_PARENT_FK_ID + ","
            + KEY_CHILD_FK_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE);";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblACTIVITY_OUTPUT` (14)
    //-- -------------------------------------------------------------------------------------------
    private static final String CREATE_TABLE_tblACTIVITY_OUTPUT = "CREATE TABLE " +
            TABLE_tblACTIVITY_OUTPUT + "("
            + KEY_ACTIVITY_FK_ID + " INTEGER NOT NULL, "
            + KEY_OUTPUT_FK_ID + " INTEGER NOT NULL, "
            + KEY_PARENT_FK_ID + " INTEGER NOT NULL, "
            + KEY_CHILD_FK_ID + " INTEGER NOT NULL, "
            + " PRIMARY KEY (" + KEY_ACTIVITY_FK_ID + "," + KEY_OUTPUT_FK_ID + ", "
            + KEY_PARENT_FK_ID + "," + KEY_CHILD_FK_ID + " ), "
            + " FOREIGN KEY (" + KEY_ACTIVITY_FK_ID + ") "
            + " REFERENCES " + TABLE_tblACTIVITY + " (" + KEY_COMPONENT_FK_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_OUTPUT_FK_ID + ") "
            + " REFERENCES " + TABLE_tblOUTPUT + " (" + KEY_COMPONENT_FK_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_PARENT_FK_ID + "," + KEY_CHILD_FK_ID + ")"
            + " REFERENCES " + TABLE_tblLOGFRAMETREE + " (" + KEY_PARENT_FK_ID + ","
            + KEY_CHILD_FK_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE); ";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblINPUT_ACTIVITY` (15)
    //-- -------------------------------------------------------------------------------------------
    private static final String CREATE_TABLE_tblINPUT_ACTIVITY = "CREATE TABLE " +
            TABLE_tblINPUT_ACTIVITY + "("
            + KEY_INPUT_FK_ID + " INTEGER NOT NULL, "
            + KEY_ACTIVITY_FK_ID + " INTEGER NOT NULL, "
            + KEY_PARENT_FK_ID + " INTEGER NOT NULL, "
            + KEY_CHILD_FK_ID + " INTEGER NOT NULL, "
            + " PRIMARY KEY (" + KEY_INPUT_FK_ID + "," + KEY_ACTIVITY_FK_ID + ", "
            + KEY_PARENT_FK_ID + "," + KEY_CHILD_FK_ID + " ), "
            + " FOREIGN KEY (" + KEY_INPUT_FK_ID + ") "
            + " REFERENCES " + TABLE_tblINPUT + " (" + KEY_COMPONENT_FK_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_ACTIVITY_FK_ID + ") "
            + " REFERENCES " + TABLE_tblACTIVITY + " (" + KEY_COMPONENT_FK_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_PARENT_FK_ID + "," + KEY_CHILD_FK_ID + ")"
            + " REFERENCES " + TABLE_tblLOGFRAMETREE + " (" + KEY_PARENT_FK_ID + ","
            + KEY_CHILD_FK_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE); ";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblCRITERIA` (16)
    //-- -------------------------------------------------------------------------------------------
    private static final String CREATE_TABLE_tblCRITERIA = "CREATE TABLE " +
            TABLE_tblCRITERIA+ "("
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
    //-- Table `tblQUESTIONGROUPING` (17)
    //-- -------------------------------------------------------------------------------------------
    private static final String CREATE_TABLE_tblQUESTIONGROUPING = "CREATE TABLE " +
            TABLE_tblQUESTIONGROUPING + "("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_SERVER_ID + " INTEGER DEFAULT NULL, "
            + KEY_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL DEFAULT 0, "
            + KEY_LABEL + " INTEGER NOT NULL, "
            + KEY_NAME + " TEXT NOT NULL, "
            + KEY_DESCRIPTION + " TEXT NULL, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP); ";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblQUESTIONTYPE` (18)
    //-- -------------------------------------------------------------------------------------------
    private static final String CREATE_TABLE_tblQUESTIONTYPE = "CREATE TABLE " +
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
    //-- Table `tblQUESTION` (19)
    //-- -------------------------------------------------------------------------------------------
    private static final String CREATE_TABLE_tblQUESTION = "CREATE TABLE " + TABLE_tblQUESTION + "("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_LOGFRAME_FK_ID + " INTEGER NOT NULL, "
            + KEY_COMPONENT_FK_ID + " INTEGER NOT NULL, "
            + KEY_QUESTION_GROUPING_FK_ID + " INTEGER DEFAULT NULL, "
            + KEY_QUESTION_TYPE_FK_ID + " INTEGER NOT NULL, "
            + KEY_SERVER_ID + " INTEGER DEFAULT NULL, "
            + KEY_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL DEFAULT 0, "
            + KEY_LABEL + " INTEGER NOT NULL, "
            + KEY_QUESTION + " TEXT NOT NULL, "
            + KEY_DEFAULT_CHART + " INTEGER, "
            + KEY_START_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_END_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + " FOREIGN KEY (" + KEY_LOGFRAME_FK_ID + ") "
            + " REFERENCES " + TABLE_tblLOGFRAME + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_COMPONENT_FK_ID + ") "
            + " REFERENCES " + TABLE_tblCOMPONENT + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_QUESTION_GROUPING_FK_ID + ") "
            + " REFERENCES " + TABLE_tblQUESTIONGROUPING + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_QUESTION_TYPE_FK_ID + ")"
            + " REFERENCES " + TABLE_tblQUESTIONTYPE + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE);";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblPRIMITIVEQUESTION` (20)
    //-- -------------------------------------------------------------------------------------------
    private static final String CREATE_TABLE_tblPRIMITIVEQUESTION = "CREATE TABLE " +
            TABLE_tblPRIMITIVEQUESTION + "("
            + KEY_QUESTION_FK_ID + " INTEGER NOT NULL, "
            + KEY_PRIMITIVE_CHART_FK_ID + " INTEGER NOT NULL, "
            + " PRIMARY KEY (" + KEY_QUESTION_FK_ID + " ), "
            + " FOREIGN KEY (" + KEY_QUESTION_FK_ID + ") "
            + " REFERENCES " + TABLE_tblQUESTION + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_PRIMITIVE_CHART_FK_ID + ") "
            + " REFERENCES " + TABLE_tblPRIMITIVECHART + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE); ";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblARRAYQUESTION` (21)
    //-- -------------------------------------------------------------------------------------------
    private static final String CREATE_TABLE_tblARRAYQUESTION = "CREATE TABLE " +
            TABLE_tblARRAYQUESTION + "("
            + KEY_QUESTION_FK_ID + " INTEGER NOT NULL, "
            + KEY_ARRAY_SET_FK_ID + " INTEGER NOT NULL, "
            + KEY_ARRAY_CHART_FK_ID + " INTEGER NOT NULL, "
            + " PRIMARY KEY (" + KEY_QUESTION_FK_ID + " ), "
            + " FOREIGN KEY (" + KEY_QUESTION_FK_ID + ") "
            + " REFERENCES " + TABLE_tblQUESTION + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_ARRAY_SET_FK_ID + ") "
            + " REFERENCES " + TABLE_tblARRAYSET + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_ARRAY_CHART_FK_ID + ") "
            + " REFERENCES " + TABLE_tblARRAYCHART + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE); ";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblMATRIXQUESTION` (22)
    //-- -------------------------------------------------------------------------------------------
    private static final String CREATE_TABLE_tblMATRIXQUESTION = "CREATE TABLE " +
            TABLE_tblMATRIXQUESTION + "("
            + KEY_QUESTION_FK_ID + " INTEGER NOT NULL, "
            + KEY_MATRIX_SET_FK_ID + " INTEGER NOT NULL, "
            + KEY_MATRIX_CHART_FK_ID + " INTEGER NOT NULL, "
            + " PRIMARY KEY (" + KEY_QUESTION_FK_ID + " ), "
            + " FOREIGN KEY (" + KEY_QUESTION_FK_ID + ") "
            + " REFERENCES " + TABLE_tblQUESTION + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_MATRIX_SET_FK_ID + ") "
            + " REFERENCES " + TABLE_tblMATRIXSET + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_MATRIX_CHART_FK_ID + ") "
            + " REFERENCES " + TABLE_tblMATRIXCHART + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE); ";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblQUESTION_CRITERIA` (23)
    //-- -------------------------------------------------------------------------------------------
    private static final String CREATE_TABLE_tblQUESTION_CRITERIA = "CREATE TABLE " +
            TABLE_tblQUESTION_CRITERIA + "("
            + KEY_QUESTION_FK_ID + " INTEGER NOT NULL, "
            + KEY_CRITERIA_FK_ID + " INTEGER NOT NULL, "
            + " PRIMARY KEY (" + KEY_QUESTION_FK_ID + "," + KEY_CRITERIA_FK_ID + "),"
            + " FOREIGN KEY (" + KEY_QUESTION_FK_ID + ") "
            + " REFERENCES " + TABLE_tblQUESTION + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_CRITERIA_FK_ID + ")"
            + " REFERENCES " + TABLE_tblCRITERIA + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE);";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblRAIDCATEGORY` (24)
    //-- -------------------------------------------------------------------------------------------
    private static final String CREATE_TABLE_tblRAIDCATEGORY = "CREATE TABLE " +
            TABLE_tblRAIDCATEGORY + "("
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
    //-- Table `tblRAID` (25)
    //-- -------------------------------------------------------------------------------------------
    private static final String CREATE_TABLE_tblRAID = "CREATE TABLE " + TABLE_tblRAID + "("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_LOGFRAME_FK_ID + " INTEGER NOT NULL, "
            + KEY_RAID_CATEGORY_FK_ID + " INTEGER NOT NULL, "
            + KEY_ORIGINATOR_FK_ID + " INTEGER NOT NULL, "
            + KEY_OWNER_FK_ID + " INTEGER NOT NULL, "
            + KEY_FREQUENCY_FK_ID + " INTEGER NOT NULL, "
            + KEY_RAID_LIKELIHOOD_FK_ID + " INTEGER NOT NULL, "
            + KEY_RAID_IMPACT_FK_ID + " INTEGER NOT NULL, "
            + KEY_ROBOT_FK_ID + " INTEGER NOT NULL, "
            + KEY_SERVER_ID + " INTEGER DEFAULT NULL, "
            + KEY_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL DEFAULT 0, "
            + KEY_SCORE + " DOUBLE NOT NULL, "
            + KEY_NAME + " TEXT NOT NULL, "
            + KEY_DESCRIPTION + " TEXT NULL, "
            + KEY_START_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_END_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + " FOREIGN KEY (" + KEY_LOGFRAME_FK_ID + ") "
            + " REFERENCES " + TABLE_tblLOGFRAME + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_RAID_CATEGORY_FK_ID + ") "
            + " REFERENCES " + TABLE_tblRAIDCATEGORY + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_ORIGINATOR_FK_ID + ") "
            + " REFERENCES " + TABLE_tblHUMANSET + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_OWNER_FK_ID + ") "
            + " REFERENCES " + TABLE_tblHUMANSET + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_FREQUENCY_FK_ID + ") "
            + " REFERENCES " + TABLE_tblFREQUENCY + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_RAID_LIKELIHOOD_FK_ID + ") "
            + " REFERENCES " + TABLE_tblRAIDLIKELIHOOD + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_RAID_IMPACT_FK_ID + ") "
            + " REFERENCES " + TABLE_tblRAIDIMPACT + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_ROBOT_FK_ID + ")"
            + " REFERENCES " + TABLE_tblROBOT + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE);";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblCOMPONENT_RAID` (26)
    //-- -------------------------------------------------------------------------------------------
    private static final String CREATE_TABLE_tblCOMPONENT_RAID = "CREATE TABLE " +
            TABLE_tblCOMPONENT_RAID + "("
            + KEY_COMPONENT_FK_ID + " INTEGER NOT NULL, "
            + KEY_RAID_FK_ID + " INTEGER NOT NULL, "
            + " PRIMARY KEY (" + KEY_COMPONENT_FK_ID + "," + KEY_RAID_FK_ID + "),"
            + " FOREIGN KEY (" + KEY_COMPONENT_FK_ID + ") "
            + " REFERENCES " + TABLE_tblCOMPONENT + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_RAID_FK_ID + ")"
            + " REFERENCES " + TABLE_tblRAID + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE);";

//    //-- -------------------------------------------------------------------------------------------
//    //-- Table `tblIMPACT_QUESTION` (25)
//    //-- -------------------------------------------------------------------------------------------
//    private static final String CREATE_TABLE_tblIMPACT_QUESTION = "CREATE TABLE " +
//            TABLE_tblIMPACT_QUESTION + "("
//            + KEY_IMPACT_FK_ID + " INTEGER NOT NULL, "
//            + KEY_QUESTION_FK_ID + " INTEGER NOT NULL, "
//            + KEY_EVALUATION_CRITERIA_FK_ID + " INTEGER NOT NULL, "
//            + " PRIMARY KEY (" + KEY_IMPACT_FK_ID + "," + KEY_QUESTION_FK_ID + ","
//            + KEY_EVALUATION_CRITERIA_FK_ID + " ), "
//            + " FOREIGN KEY (" + KEY_IMPACT_FK_ID + ") "
//            + " REFERENCES " + TABLE_tblIMPACT + " (" + KEY_ID + ") "
//            + " ON DELETE CASCADE ON UPDATE CASCADE, "
//            + " FOREIGN KEY (" + KEY_QUESTION_FK_ID + ") "
//            + " REFERENCES " + TABLE_tblQUESTION + " (" + KEY_ID + ") "
//            + " ON DELETE CASCADE ON UPDATE CASCADE, "
//            + " FOREIGN KEY (" + KEY_EVALUATION_CRITERIA_FK_ID + ")"
//            + " REFERENCES " + TABLE_tblEVALUATIONCRITERIA + " (" + KEY_ID + ") "
//            + " ON DELETE CASCADE ON UPDATE CASCADE);";
//
//    //-- -------------------------------------------------------------------------------------------
//    //-- Table `tblOUTCOME_QUESTION` (26)
//    //-- -------------------------------------------------------------------------------------------
//    private static final String CREATE_TABLE_tblOUTCOME_QUESTION = "CREATE TABLE " +
//            TABLE_tblOUTCOME_QUESTION + "("
//            + KEY_OUTCOME_FK_ID + " INTEGER NOT NULL, "
//            + KEY_QUESTION_FK_ID + " INTEGER NOT NULL, "
//            + KEY_EVALUATION_CRITERIA_FK_ID + " INTEGER NOT NULL, "
//            + " PRIMARY KEY (" + KEY_OUTCOME_FK_ID + "," + KEY_QUESTION_FK_ID + ","
//            + KEY_EVALUATION_CRITERIA_FK_ID + " ), "
//            + " FOREIGN KEY (" + KEY_OUTCOME_FK_ID + ") "
//            + " REFERENCES " + TABLE_tblOUTCOME + " (" + KEY_ID + ") "
//            + " ON DELETE CASCADE ON UPDATE CASCADE, "
//            + " FOREIGN KEY (" + KEY_QUESTION_FK_ID + ") "
//            + " REFERENCES " + TABLE_tblQUESTION + " (" + KEY_ID + ") "
//            + " ON DELETE CASCADE ON UPDATE CASCADE, "
//            + " FOREIGN KEY (" + KEY_EVALUATION_CRITERIA_FK_ID + ")"
//            + " REFERENCES " + TABLE_tblEVALUATIONCRITERIA + " (" + KEY_ID + ") "
//            + " ON DELETE CASCADE ON UPDATE CASCADE);";
//
//    //-- -------------------------------------------------------------------------------------------
//    //-- Table `tblOUTPUT_QUESTION` (27)
//    //-- -------------------------------------------------------------------------------------------
//    private static final String CREATE_TABLE_tblOUTPUT_QUESTION = "CREATE TABLE " +
//            TABLE_tblOUTPUT_QUESTION + "("
//            + KEY_OUTPUT_FK_ID + " INTEGER NOT NULL, "
//            + KEY_QUESTION_FK_ID + " INTEGER NOT NULL, "
//            + KEY_EVALUATION_CRITERIA_FK_ID + " INTEGER NOT NULL, "
//            + " PRIMARY KEY (" + KEY_OUTPUT_FK_ID + "," + KEY_QUESTION_FK_ID + ","
//            + KEY_EVALUATION_CRITERIA_FK_ID + " ), "
//            + " FOREIGN KEY (" + KEY_OUTPUT_FK_ID + ") "
//            + " REFERENCES " + TABLE_tblOUTPUT + " (" + KEY_ID + ") "
//            + " ON DELETE CASCADE ON UPDATE CASCADE, "
//            + " FOREIGN KEY (" + KEY_QUESTION_FK_ID + ") "
//            + " REFERENCES " + TABLE_tblQUESTION + " (" + KEY_ID + ") "
//            + " ON DELETE CASCADE ON UPDATE CASCADE, "
//            + " FOREIGN KEY (" + KEY_EVALUATION_CRITERIA_FK_ID + ")"
//            + " REFERENCES " + TABLE_tblEVALUATIONCRITERIA + " (" + KEY_ID + ") "
//            + " ON DELETE CASCADE ON UPDATE CASCADE);";
//
//    //-- -------------------------------------------------------------------------------------------
//    //-- Table `tblACTIVITY_QUESTION` (28)
//    //-- -------------------------------------------------------------------------------------------
//    private static final String CREATE_TABLE_tblACTIVITY_QUESTION = "CREATE TABLE " +
//            TABLE_tblACTIVITY_QUESTION + "("
//            + KEY_WORKPLAN_FK_ID + " INTEGER NOT NULL, "
//            + KEY_QUESTION_FK_ID + " INTEGER NOT NULL, "
//            + KEY_EVALUATION_CRITERIA_FK_ID + " INTEGER NOT NULL, "
//            + " PRIMARY KEY (" + KEY_WORKPLAN_FK_ID + "," + KEY_QUESTION_FK_ID + ","
//            + KEY_EVALUATION_CRITERIA_FK_ID + " ), "
//            + " FOREIGN KEY (" + KEY_WORKPLAN_FK_ID + ") "
//            + " REFERENCES " + TABLE_tblACTIVITY + " (" + KEY_WORKPLAN_FK_ID + ") "
//            + " ON DELETE CASCADE ON UPDATE CASCADE, "
//            + " FOREIGN KEY (" + KEY_QUESTION_FK_ID + ") "
//            + " REFERENCES " + TABLE_tblQUESTION + " (" + KEY_ID + ") "
//            + " ON DELETE CASCADE ON UPDATE CASCADE, "
//            + " FOREIGN KEY (" + KEY_EVALUATION_CRITERIA_FK_ID + ")"
//            + " REFERENCES " + TABLE_tblEVALUATIONCRITERIA + " (" + KEY_ID + ") "
//            + " ON DELETE CASCADE ON UPDATE CASCADE);";
//
//    //-- -------------------------------------------------------------------------------------------
//    //-- Table `tblINPUT_QUESTION` (29)
//    //-- -------------------------------------------------------------------------------------------
//    private static final String CREATE_TABLE_tblINPUT_QUESTION = "CREATE TABLE " +
//            TABLE_tblINPUT_QUESTION + "("
//            + KEY_INPUT_FK_ID + " INTEGER NOT NULL, "
//            + KEY_QUESTION_FK_ID + " INTEGER NOT NULL, "
//            + KEY_EVALUATION_CRITERIA_FK_ID + " INTEGER NOT NULL, "
//            + " PRIMARY KEY (" + KEY_INPUT_FK_ID + "," + KEY_QUESTION_FK_ID + ","
//            + KEY_EVALUATION_CRITERIA_FK_ID + " ), "
//            + " FOREIGN KEY (" + KEY_INPUT_FK_ID + ") "
//            + " REFERENCES " + TABLE_tblINPUT + " (" + KEY_ID + ") "
//            + " ON DELETE CASCADE ON UPDATE CASCADE, "
//            + " FOREIGN KEY (" + KEY_QUESTION_FK_ID + ") "
//            + " REFERENCES " + TABLE_tblQUESTION + " (" + KEY_ID + ") "
//            + " ON DELETE CASCADE ON UPDATE CASCADE, "
//            + " FOREIGN KEY (" + KEY_EVALUATION_CRITERIA_FK_ID + ")"
//            + " REFERENCES " + TABLE_tblEVALUATIONCRITERIA + " (" + KEY_ID + ") "
//            + " ON DELETE CASCADE ON UPDATE CASCADE);";
//
//    //-- -------------------------------------------------------------------------------------------
//    //-- Table `tblIMPACT_RAID` (30)
//    //-- -------------------------------------------------------------------------------------------
//    private static final String CREATE_TABLE_tblIMPACT_RAID = "CREATE TABLE " +
//            TABLE_tblIMPACT_RAID + "("
//            + KEY_IMPACT_FK_ID + " INTEGER NOT NULL, "
//            + KEY_RAID_FK_ID + " INTEGER NOT NULL, "
//            + " PRIMARY KEY (" + KEY_IMPACT_FK_ID + "," + KEY_RAID_FK_ID + " ), "
//            + " FOREIGN KEY (" + KEY_IMPACT_FK_ID + ") "
//            + " REFERENCES " + TABLE_tblIMPACT + " (" + KEY_ID + ") "
//            + " ON DELETE CASCADE ON UPDATE CASCADE, "
//            + " FOREIGN KEY (" + KEY_RAID_FK_ID + ") "
//            + " REFERENCES " + TABLE_tblRAID + " (" + KEY_ID + ") "
//            + " ON DELETE CASCADE ON UPDATE CASCADE); ";
//
//    //-- -------------------------------------------------------------------------------------------
//    //-- Table `tblOUTCOME_RAID` (31)
//    //-- -------------------------------------------------------------------------------------------
//    private static final String CREATE_TABLE_tblOUTCOME_RAID = "CREATE TABLE " +
//            TABLE_tblOUTCOME_RAID + "("
//            + KEY_OUTCOME_FK_ID + " INTEGER NOT NULL, "
//            + KEY_RAID_FK_ID + " INTEGER NOT NULL, "
//            + " PRIMARY KEY (" + KEY_OUTCOME_FK_ID + "," + KEY_RAID_FK_ID + " ), "
//            + " FOREIGN KEY (" + KEY_OUTCOME_FK_ID + ") "
//            + " REFERENCES " + TABLE_tblOUTCOME + " (" + KEY_ID + ") "
//            + " ON DELETE CASCADE ON UPDATE CASCADE, "
//            + " FOREIGN KEY (" + KEY_RAID_FK_ID + ") "
//            + " REFERENCES " + TABLE_tblRAID + " (" + KEY_ID + ") "
//            + " ON DELETE CASCADE ON UPDATE CASCADE); ";
//
//    //-- -------------------------------------------------------------------------------------------
//    //-- Table `tblOUTPUT_RAID` (32)
//    //-- -------------------------------------------------------------------------------------------
//    private static final String CREATE_TABLE_tblOUTPUT_RAID = "CREATE TABLE " +
//            TABLE_tblOUTPUT_RAID + "("
//            + KEY_OUTPUT_FK_ID + " INTEGER NOT NULL, "
//            + KEY_RAID_FK_ID + " INTEGER NOT NULL, "
//            + " PRIMARY KEY (" + KEY_OUTPUT_FK_ID + "," + KEY_RAID_FK_ID + " ), "
//            + " FOREIGN KEY (" + KEY_OUTPUT_FK_ID + ") "
//            + " REFERENCES " + TABLE_tblOUTPUT + " (" + KEY_ID + ") "
//            + " ON DELETE CASCADE ON UPDATE CASCADE, "
//            + " FOREIGN KEY (" + KEY_RAID_FK_ID + ") "
//            + " REFERENCES " + TABLE_tblRAID + " (" + KEY_ID + ") "
//            + " ON DELETE CASCADE ON UPDATE CASCADE); ";
//
//    //-- -------------------------------------------------------------------------------------------
//    //-- Table `tblACTIVITY_RAID` (33)
//    //-- -------------------------------------------------------------------------------------------
//    private static final String CREATE_TABLE_tblACTIVITY_RAID = "CREATE TABLE " +
//            TABLE_tblACTIVITY_RAID + "("
//            + KEY_WORKPLAN_FK_ID + " INTEGER NOT NULL, "
//            + KEY_RAID_FK_ID + " INTEGER NOT NULL, "
//            + " PRIMARY KEY (" + KEY_WORKPLAN_FK_ID + "," + KEY_RAID_FK_ID + " ), "
//            + " FOREIGN KEY (" + KEY_WORKPLAN_FK_ID + ") "
//            + " REFERENCES " + TABLE_tblACTIVITY + " (" + KEY_WORKPLAN_FK_ID + ") "
//            + " ON DELETE CASCADE ON UPDATE CASCADE, "
//            + " FOREIGN KEY (" + KEY_RAID_FK_ID + ") "
//            + " REFERENCES " + TABLE_tblRAID + " (" + KEY_ID + ") "
//            + " ON DELETE CASCADE ON UPDATE CASCADE); ";


    /*##################################### END LOGFRAME MODULE ##################################*/

    /*################################### START EVALUATION MODULE ################################*/

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblARRAYCHOICE ` (1)
    //-- -------------------------------------------------------------------------------------------
    private static final String CREATE_TABLE_tblARRAYCHOICE = "CREATE TABLE " +
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
    //-- Table `tblROWCHOICE` (2)
    //-- -------------------------------------------------------------------------------------------
    private static final String CREATE_TABLE_tblROWCHOICE = "CREATE TABLE " +
            TABLE_tblROWCHOICE + "("
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
    //-- Table `tblCOLCHOICE` (3)
    //-- -------------------------------------------------------------------------------------------
    private static final String CREATE_TABLE_tblCOLCHOICE = "CREATE TABLE " +
            TABLE_tblCOLCHOICE + "("
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
    //-- Table `tblARRAYSET` (4)
    //-- -------------------------------------------------------------------------------------------
    private static final String CREATE_TABLE_tblARRAYSET = "CREATE TABLE " +
            TABLE_tblARRAYSET + "("
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
    //-- Table `tblMATRIXSET` (5)
    //-- -------------------------------------------------------------------------------------------
    private static final String CREATE_TABLE_tblMATRIXSET = "CREATE TABLE " +
            TABLE_tblMATRIXSET + "("
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
    //-- Table `tblARRAYCHOICESET` (6)
    //-- -------------------------------------------------------------------------------------------
    private static final String CREATE_TABLE_tblARRAYCHOICESET = "CREATE TABLE " +
            TABLE_tblARRAYCHOICESET+ "("
            + KEY_ARRAY_SET_FK_ID + " INTEGER NOT NULL, "
            + KEY_ARRAY_CHOICE_FK_ID + " INTEGER NOT NULL, "
            + " PRIMARY KEY (" + KEY_ARRAY_SET_FK_ID + "," + KEY_ARRAY_CHOICE_FK_ID + " ), "
            + " FOREIGN KEY (" + KEY_ARRAY_SET_FK_ID + ") "
            + " REFERENCES " + TABLE_tblARRAYSET + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_ARRAY_CHOICE_FK_ID + ")"
            + " REFERENCES " + TABLE_tblARRAYCHOICE + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE);";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblMATRIXCHOICESET` (7)
    //-- -------------------------------------------------------------------------------------------
    private static final String CREATE_TABLE_tblMATRIXCHOICESET = "CREATE TABLE " +
            TABLE_tblMATRIXCHOICESET + "("
            + KEY_MATRIX_SET_FK_ID + " INTEGER NOT NULL, "
            + KEY_ROW_CHOICE_FK_ID + " INTEGER NOT NULL, "
            + KEY_COL_CHOICE_FK_ID + " INTEGER NOT NULL, "
            + " PRIMARY KEY (" + KEY_MATRIX_SET_FK_ID + "," + KEY_ROW_CHOICE_FK_ID+ ","
            + KEY_COL_CHOICE_FK_ID + " ), "
            + " FOREIGN KEY (" + KEY_MATRIX_SET_FK_ID + ") "
            + " REFERENCES " + TABLE_tblMATRIXSET + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_ROW_CHOICE_FK_ID + ") "
            + " REFERENCES " + TABLE_tblROWCHOICE + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_COL_CHOICE_FK_ID + ") "
            + " REFERENCES " + TABLE_tblCOLCHOICE + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE); ";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblEVALUATIONTYPE ` (8)
    //-- -------------------------------------------------------------------------------------------
    private static final String CREATE_TABLE_tblEVALUATIONTYPE = "CREATE TABLE " +
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
    //-- Table `tblEVALUATION` (9)
    //-- -------------------------------------------------------------------------------------------
    private static final String CREATE_TABLE_tblEVALUATION = "CREATE TABLE " +
            TABLE_tblEVALUATION + "("
            + KEY_ID + " INTEGER NOT NULL, "
            + KEY_LOGFRAME_FK_ID + " INTEGER NOT NULL, "
            + KEY_EVALUATION_TYPE_FK_ID + " INTEGER NOT NULL, "
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
            + " PRIMARY KEY (" + KEY_ID + "),"
            + " FOREIGN KEY (" + KEY_LOGFRAME_FK_ID + ") "
            + " REFERENCES " + TABLE_tblLOGFRAME + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_EVALUATION_TYPE_FK_ID + ") "
            + " REFERENCES " + TABLE_tblEVALUATIONTYPE + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE); ";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblQUESTION_EVALUATION` (10)
    //-- -------------------------------------------------------------------------------------------
    private static final String CREATE_TABLE_tblQUESTION_EVALUATION = "CREATE TABLE " +
            TABLE_tblQUESTION_EVALUATION + "("
            + KEY_ID + " INTEGER NOT NULL, "
            + KEY_QUESTION_FK_ID + " INTEGER NOT NULL, "
            + KEY_EVALUATION_FK_ID + " INTEGER NOT NULL, "
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
            + " FOREIGN KEY (" + KEY_QUESTION_FK_ID + ") "
            + " REFERENCES " + TABLE_tblQUESTION + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_EVALUATION_FK_ID + ") "
            + " REFERENCES " + TABLE_tblEVALUATION + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE); ";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblCONDITIONAL_ORDER` (11)
    //-- -------------------------------------------------------------------------------------------
    private static final String CREATE_TABLE_tblCONDITIONAL_ORDER = "CREATE TABLE " +
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
            + " PRIMARY KEY (" + KEY_ID + "),"
            + " FOREIGN KEY (" + KEY_QUESTION_ORDER_FK_ID + ") "
            + " REFERENCES " + TABLE_tblQUESTION_EVALUATION + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_QUESTION_RESPONSE_FK_ID + ") "
            + " REFERENCES " + TABLE_tblQUESTION_EVALUATION + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_POS_RES_ORDER_FK_ID + ") "
            + " REFERENCES " + TABLE_tblQUESTION_EVALUATION + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_NEG_RES_ORDER_FK_ID + ") "
            + " REFERENCES " + TABLE_tblQUESTION_EVALUATION + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE); ";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblUSER_EVALUATION ` (12)
    //-- -------------------------------------------------------------------------------------------

    private static final String CREATE_TABLE_tblUSER_EVALUATION = "CREATE TABLE " +
            TABLE_tblUSER_EVALUATION + "("
            + KEY_ID + " INTEGER NOT NULL, "
            + KEY_USER_FK_ID + " INTEGER NOT NULL, "
            + KEY_EVALUATION_FK_ID + " INTEGER NOT NULL, "
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
            + " FOREIGN KEY (" + KEY_USER_FK_ID + ") "
            + " REFERENCES " + TABLE_tblUSER + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_EVALUATION_FK_ID + ") "
            + " REFERENCES " + TABLE_tblEVALUATION + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE); ";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblEVALUATION_RESPONSE` (13)
    //-- -------------------------------------------------------------------------------------------
    private static final String CREATE_TABLE_tblEVALUATION_RESPONSE = "CREATE TABLE " +
            TABLE_tblEVALUATION_RESPONSE+ "("
            + KEY_ID + " INTEGER NOT NULL, "
            + KEY_USER_EVALUATION_FK_ID + " INTEGER NOT NULL, "
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
            + " FOREIGN KEY (" + KEY_USER_EVALUATION_FK_ID + ") "
            + " REFERENCES " + TABLE_tblUSER_EVALUATION + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE); ";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblNUMERICRESPONSE` (14)
    //-- -------------------------------------------------------------------------------------------
    private static final String CREATE_TABLE_tblNUMERICRESPONSE = "CREATE TABLE " +
            TABLE_tblNUMERICRESPONSE+ "("
            + KEY_EVALUATION_RESPONSE_FK_ID + " INTEGER NOT NULL, "
            + KEY_PRIMITIVE_QUESTION_FK_ID + " INTEGER NOT NULL, "
            + KEY_NUMERIC_RESPONSE + " INTEGER NOT NULL, "
            + " PRIMARY KEY (" + KEY_EVALUATION_RESPONSE_FK_ID + "),"
            + " FOREIGN KEY (" + KEY_PRIMITIVE_QUESTION_FK_ID + ") "
            + " REFERENCES " + TABLE_tblPRIMITIVEQUESTION + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_EVALUATION_RESPONSE_FK_ID + ") "
            + " REFERENCES " + TABLE_tblEVALUATION_RESPONSE + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE); ";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblTEXTRESPONSE` (15)
    //-- -------------------------------------------------------------------------------------------
    private static final String CREATE_TABLE_tblTEXTRESPONSE = "CREATE TABLE " +
            TABLE_tblTEXTRESPONSE+ "("
            + KEY_EVALUATION_RESPONSE_FK_ID + " INTEGER NOT NULL, "
            + KEY_PRIMITIVE_QUESTION_FK_ID + " INTEGER NOT NULL, "
            + KEY_TEXT_RESPONSE + " TEXT NOT NULL, "
            + "PRIMARY KEY (" + KEY_EVALUATION_RESPONSE_FK_ID + "),"
            + " FOREIGN KEY (" + KEY_PRIMITIVE_QUESTION_FK_ID + ") "
            + " REFERENCES " + TABLE_tblPRIMITIVEQUESTION + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_EVALUATION_RESPONSE_FK_ID + ") "
            + " REFERENCES " + TABLE_tblEVALUATION_RESPONSE + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE); ";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblDATERESPONSE` (16)
    //-- -------------------------------------------------------------------------------------------
    private static final String CREATE_TABLE_tblDATERESPONSE = "CREATE TABLE " +
            TABLE_tblDATERESPONSE+ "("
            + KEY_EVALUATION_RESPONSE_FK_ID + " INTEGER NOT NULL, "
            + KEY_PRIMITIVE_QUESTION_FK_ID + " INTEGER NOT NULL, "
            + KEY_DATE_RESPONSE + " DATE NOT NULL, "
            + " PRIMARY KEY (" + KEY_EVALUATION_RESPONSE_FK_ID + "),"
            + " FOREIGN KEY (" + KEY_PRIMITIVE_QUESTION_FK_ID + ") "
            + " REFERENCES " + TABLE_tblPRIMITIVEQUESTION + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_EVALUATION_RESPONSE_FK_ID + ") "
            + " REFERENCES " + TABLE_tblEVALUATION_RESPONSE + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE); ";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblARRAYRESPONSE` (17)
    //-- -------------------------------------------------------------------------------------------
    private static final String CREATE_TABLE_tblARRAYRESPONSE = "CREATE TABLE " +
            TABLE_tblARRAYRESPONSE+ "("
            + KEY_EVALUATION_RESPONSE_FK_ID + " INTEGER NOT NULL, "
            + KEY_ARRAY_QUESTION_FK_ID + " INTEGER NOT NULL, "
            + KEY_ARRAY_RESPONSE_FK_ID + " INTEGER NOT NULL, "
            + " PRIMARY KEY (" + KEY_EVALUATION_RESPONSE_FK_ID + "),"
            + " FOREIGN KEY (" + KEY_ARRAY_RESPONSE_FK_ID + ") "
            + " REFERENCES " + TABLE_tblARRAYCHOICE + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_ARRAY_QUESTION_FK_ID + ") "
            + " REFERENCES " + TABLE_tblARRAYQUESTION + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_EVALUATION_RESPONSE_FK_ID + ") "
            + " REFERENCES " + TABLE_tblEVALUATION_RESPONSE + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE); ";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblMATRIXRESPONSE` (18)
    //-- -------------------------------------------------------------------------------------------
    private static final String CREATE_TABLE_tblMATRIXRESPONSE = "CREATE TABLE " +
            TABLE_tblMATRIXRESPONSE+ "("
            + KEY_EVALUATION_RESPONSE_FK_ID + " INTEGER NOT NULL, "
            + KEY_MATRIX_QUESTION_FK_ID + " INTEGER NOT NULL, "
            + KEY_ROW_RESPONSE_FK_ID + " INTEGER NOT NULL, "
            + KEY_COL_RESPONSE_FK_ID + " INTEGER NOT NULL, "
            + " PRIMARY KEY (" + KEY_EVALUATION_RESPONSE_FK_ID + "),"
            + " FOREIGN KEY (" + KEY_COL_RESPONSE_FK_ID + ") "
            + " REFERENCES " + TABLE_tblCOLCHOICE + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_ROW_RESPONSE_FK_ID + ") "
            + " REFERENCES " + TABLE_tblROWCHOICE + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_MATRIX_QUESTION_FK_ID + ") "
            + " REFERENCES " + TABLE_tblMATRIXQUESTION + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_EVALUATION_RESPONSE_FK_ID + ") "
            + " REFERENCES " + TABLE_tblEVALUATION_RESPONSE + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE); ";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblPRIMITIVECHART (19)
    //-- -------------------------------------------------------------------------------------------
    private static final String CREATE_TABLE_tblPRIMITIVECHART = "CREATE TABLE " +
            TABLE_tblPRIMITIVECHART + "("
            + KEY_CHART_FK_ID + " INTEGER NOT NULL, "
            + " PRIMARY KEY (" + KEY_CHART_FK_ID + " ), "
            + " FOREIGN KEY (" + KEY_CHART_FK_ID + ") "
            + " REFERENCES " + TABLE_tblCHART + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE); ";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblARRAYCHART` (20)
    //-- -------------------------------------------------------------------------------------------
    private static final String CREATE_TABLE_tblARRAYCHART = "CREATE TABLE " +
            TABLE_tblARRAYCHART + "("
            + KEY_CHART_FK_ID + " INTEGER NOT NULL, "
            + " PRIMARY KEY (" + KEY_CHART_FK_ID + " ), "
            + " FOREIGN KEY (" + KEY_CHART_FK_ID + ") "
            + " REFERENCES " + TABLE_tblCHART + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE); ";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblMATRIXCHART` (21)
    //-- -------------------------------------------------------------------------------------------
    private static final String CREATE_TABLE_tblMATRIXCHART = "CREATE TABLE " +
            TABLE_tblMATRIXCHART + "("
            + KEY_CHART_FK_ID + " INTEGER NOT NULL, "
            + " PRIMARY KEY (" + KEY_CHART_FK_ID + " ), "
            + " FOREIGN KEY (" + KEY_CHART_FK_ID + ") "
            + " REFERENCES " + TABLE_tblCHART + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE); ";

    /*#################################### END EVALUATION MODULE #################################*/

    /*################################### START MONITORING MODULE ################################*/

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblQUESTION_INDICATOR ` (1)
    //-- -------------------------------------------------------------------------------------------

    private static final String CREATE_TABLE_tblQUESTION_INDICATOR = "CREATE TABLE " +
            TABLE_tblQUESTION_INDICATOR + "("
            + KEY_QUESTION_FK_ID + " INTEGER NOT NULL, "
            + KEY_INDICATOR_FK_ID + " INTEGER NOT NULL, "
            + "PRIMARY KEY (" + KEY_QUESTION_FK_ID + ", " + KEY_INDICATOR_FK_ID + "),"
            + " FOREIGN KEY (" + KEY_QUESTION_FK_ID + ") "
            + " REFERENCES " + TABLE_tblQUESTION + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_INDICATOR_FK_ID + ") "
            + " REFERENCES " + TABLE_tblINDICATOR + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE); ";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblMETHOD` (2)
    //-- -------------------------------------------------------------------------------------------
    private static final String CREATE_TABLE_tblMETHOD = "CREATE TABLE " +
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
    //-- Table `tblMOV` (3)
    //-- -------------------------------------------------------------------------------------------
    private static final String CREATE_TABLE_tblMOV = "CREATE TABLE " +
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
    //-- Table `tblDATASOURCE` (4)
    //-- -------------------------------------------------------------------------------------------
    private static final String CREATE_TABLE_tblDATASOURCE = "CREATE TABLE " +
            TABLE_tblDATASOURCE + "("
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
    private static final String CREATE_TABLE_tblINDICATORTYPE = "CREATE TABLE " +
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
    //-- Table `tblQUANTITATIVETYPE` (5)
    //-- -------------------------------------------------------------------------------------------
    private static final String CREATE_TABLE_tblQUANTITATIVETYPE = "CREATE TABLE " +
            TABLE_tblQUANTITATIVETYPE + "("
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
    //-- Table `tblSCORECRITERIA` (6)
    //-- -------------------------------------------------------------------------------------------
    private static final String CREATE_TABLE_tblCRITERIASCORE = "CREATE TABLE " +
            TABLE_tblCRITERIASCORE + "("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_SERVER_ID + " INTEGER DEFAULT NULL, "
            + KEY_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL DEFAULT 0, "
            + KEY_SCORE + " INT NOT NULL, "
            + KEY_NAME + " TEXT NULL, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP); ";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblQUALITATIVECRITERIA` (7)
    //-- -------------------------------------------------------------------------------------------
    private static final String CREATE_TABLE_tblQUALITATIVECRITERIA = "CREATE TABLE " +
            TABLE_tblQUALITATIVECRITERIA + "("
            + KEY_ID + " INTEGER NOT NULL, "
            + KEY_CRITERIA_SCORE_FK_ID + " INTEGER NOT NULL, "
            + KEY_SERVER_ID + " INTEGER DEFAULT NULL, "
            + KEY_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL DEFAULT 0, "
            + KEY_NAME + " INT NOT NULL, "
            + KEY_DESCRIPTION + " TEXT NULL, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + " PRIMARY KEY (" + KEY_ID + "), "
            + " FOREIGN KEY (" + KEY_CRITERIA_SCORE_FK_ID + ") "
            + " REFERENCES " + TABLE_tblCRITERIASCORE + "(" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE);";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblQUALITATIVESET` (8)
    //-- -------------------------------------------------------------------------------------------
    private static final String CREATE_TABLE_tblQUALITATIVESET = "CREATE TABLE " +
            TABLE_tblQUALITATIVESET + "("
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
    //-- Table `tblQUALITATIVESCORESET` (9)
    //-- -------------------------------------------------------------------------------------------
    private static final String CREATE_TABLE_tblQUALITATIVESCORESET = "CREATE TABLE " +
            TABLE_tblQUALITATIVESCORESET+ "("
            + KEY_QUALITATIVE_CRITERIA_FK_ID + " INTEGER NOT NULL, "
            + KEY_CRITERIA_SCORE_FK_ID + " INTEGER NOT NULL, "
            + " PRIMARY KEY (" + KEY_QUALITATIVE_CRITERIA_FK_ID + "," + KEY_CRITERIA_SCORE_FK_ID + " ), "
            + " FOREIGN KEY (" + KEY_QUALITATIVE_CRITERIA_FK_ID + ") "
            + " REFERENCES " + TABLE_tblQUALITATIVECRITERIA + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_CRITERIA_SCORE_FK_ID + ")"
            + " REFERENCES " + TABLE_tblCRITERIASCORE+ " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE);";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblARRAYINDICATOR` (10)
    //-- -------------------------------------------------------------------------------------------
    private static final String CREATE_TABLE_tblARRAYINDICATOR = "CREATE TABLE " +
            TABLE_tblARRAYINDICATOR + "("
            + KEY_QUANTITATIVE_INDICATOR_FK_ID + " INTEGER NOT NULL, "
            + KEY_ARRAY_SET_FK_ID + " INTEGER NOT NULL, "
            + " PRIMARY KEY (" + KEY_QUANTITATIVE_INDICATOR_FK_ID + "),"
            + " FOREIGN KEY (" + KEY_QUANTITATIVE_INDICATOR_FK_ID + ") "
            + " REFERENCES " + TABLE_tblQUANTITATIVEINDICATOR + " (" + KEY_INDICATOR_FK_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_ARRAY_SET_FK_ID + ") "
            + " REFERENCES " + TABLE_tblARRAYSET + "(" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE);";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblMATRIXINDICATOR` (11)
    //-- -------------------------------------------------------------------------------------------
    private static final String CREATE_TABLE_tblMATRIXINDICATOR = "CREATE TABLE " +
            TABLE_tblMATRIXINDICATOR + "("
            + KEY_QUANTITATIVE_INDICATOR_FK_ID + " INTEGER NOT NULL, "
            + KEY_MATRIX_SET_FK_ID + " INTEGER NOT NULL, "
            + " PRIMARY KEY (" + KEY_QUANTITATIVE_INDICATOR_FK_ID + "),"
            + " FOREIGN KEY (" + KEY_QUANTITATIVE_INDICATOR_FK_ID + ") "
            + " REFERENCES " + TABLE_tblQUANTITATIVEINDICATOR + " (" + KEY_INDICATOR_FK_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_MATRIX_SET_FK_ID + ") "
            + " REFERENCES " + TABLE_tblMATRIXSET + "(" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE);";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblQUANTITATIVEINDICATOR` (12)
    //-- -------------------------------------------------------------------------------------------
    private static final String CREATE_TABLE_tblQUANTITATIVEINDICATOR = "CREATE TABLE " +
            TABLE_tblQUANTITATIVEINDICATOR + "("
            + KEY_INDICATOR_FK_ID + " INTEGER NOT NULL, "
            + KEY_QUANTITATIVE_TYPE_FK_ID + " INTEGER, "
            + " PRIMARY KEY (" + KEY_INDICATOR_FK_ID + "),"
            + " FOREIGN KEY (" + KEY_INDICATOR_FK_ID + ") "
            + " REFERENCES " + TABLE_tblINDICATOR + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_QUANTITATIVE_TYPE_FK_ID + ") "
            + " REFERENCES " + TABLE_tblQUANTITATIVETYPE + "(" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE);";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblQUALITATIVEINDICATOR` (13)
    //-- -------------------------------------------------------------------------------------------
    private static final String CREATE_TABLE_tblQUALITATIVEINDICATOR = "CREATE TABLE " +
            TABLE_tblQUALITATIVEINDICATOR + "("
            + KEY_INDICATOR_FK_ID + " INTEGER NOT NULL, "
            + KEY_QUALITATIVE_SET_FK_ID + " INTEGER, "
            + " PRIMARY KEY (" + KEY_INDICATOR_FK_ID + "),"
            + " FOREIGN KEY (" + KEY_INDICATOR_FK_ID + ") "
            + " REFERENCES " + TABLE_tblINDICATOR + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_QUALITATIVE_SET_FK_ID + ") "
            + " REFERENCES " + TABLE_tblQUALITATIVESET + "(" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE);";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblTARGET` (14)
    //-- -------------------------------------------------------------------------------------------
    private static final String CREATE_TABLE_tblTARGET = "CREATE TABLE " +
            TABLE_tblTARGET + "("
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
    //-- Table `tblQUALITATIVETARGET` (15)
    //-- -------------------------------------------------------------------------------------------
    private static final String CREATE_TABLE_tblQUALITATIVETARGET = "CREATE TABLE " +
            TABLE_tblQUALITATIVETARGET + "("
            + KEY_TARGET_FK_ID + " INTEGER NOT NULL, "
            + KEY_BASELINE_SCORE_FK_ID + " INTEGER, "
            + KEY_TARGET_SCORE_FK_ID + " INTEGER, "
            + " PRIMARY KEY (" + KEY_TARGET_FK_ID + "),"
            + " FOREIGN KEY (" + KEY_TARGET_FK_ID + ") "
            + " REFERENCES " + TABLE_tblTARGET + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_BASELINE_SCORE_FK_ID + ") "
            + " REFERENCES " + TABLE_tblCRITERIASCORE + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_TARGET_SCORE_FK_ID + ") "
            + " REFERENCES " + TABLE_tblCRITERIASCORE + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE);";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblQUANTITATIVETARGET` (16)
    //-- -------------------------------------------------------------------------------------------
    private static final String CREATE_TABLE_tblQUANTITATIVETARGET = "CREATE TABLE " +
            TABLE_tblQUANTITATIVETARGET + "("
            + KEY_TARGET_FK_ID + " INTEGER NOT NULL, "
            + KEY_BASELINE_VALUE + " INTEGER, "
            + KEY_TARGET_VALUE + " INTEGER, "
            + " PRIMARY KEY (" + KEY_TARGET_FK_ID + "),"
            + " FOREIGN KEY (" + KEY_TARGET_FK_ID + ") "
            + " REFERENCES " + TABLE_tblTARGET + "(" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE);";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblARRAYTARGET` (17)
    //-- -------------------------------------------------------------------------------------------
    private static final String CREATE_TABLE_tblARRAYTARGET = "CREATE TABLE " +
            TABLE_tblARRAYTARGET + "("
            + KEY_QUANTITATIVE_TARGET_FK_ID + " INTEGER NOT NULL, "
            + KEY_ARRAY_CHOICE_FK_ID + " INTEGER NOT NULL, "
            + KEY_DISAGGREGATED_BASELINE + " INTEGER, "
            + KEY_DISAGGREGATED_TARGET + " INTEGER, "
            + " PRIMARY KEY (" + KEY_QUANTITATIVE_TARGET_FK_ID + ", " + KEY_ARRAY_CHOICE_FK_ID + "),"
            + " FOREIGN KEY (" + KEY_QUANTITATIVE_TARGET_FK_ID + ") "
            + " REFERENCES " + TABLE_tblQUANTITATIVETARGET + " (" + KEY_TARGET_FK_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_ARRAY_CHOICE_FK_ID + ") "
            + " REFERENCES " + TABLE_tblARRAYCHOICE + "(" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE);";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblMATRIXTARGET` (18)
    //-- -------------------------------------------------------------------------------------------
    private static final String CREATE_TABLE_tblMATRIXTARGET = "CREATE TABLE " +
            TABLE_tblMATRIXTARGET + "("
            + KEY_QUANTITATIVE_TARGET_FK_ID + " INTEGER NOT NULL, "
            + KEY_ROW_CHOICE_FK_ID + " INTEGER NOT NULL, "
            + KEY_COL_CHOICE_FK_ID + " INTEGER NOT NULL, "
            + KEY_DISAGGREGATED_BASELINE + " INTEGER, "
            + KEY_DISAGGREGATED_TARGET + " INTEGER, "
            + " PRIMARY KEY (" + KEY_QUANTITATIVE_TARGET_FK_ID +", " + KEY_ROW_CHOICE_FK_ID + ", "
            + KEY_COL_CHOICE_FK_ID + "),"
            + " FOREIGN KEY (" + KEY_QUANTITATIVE_TARGET_FK_ID + ") "
            + " REFERENCES " + TABLE_tblQUANTITATIVETARGET + " (" + KEY_TARGET_FK_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_ROW_CHOICE_FK_ID + ") "
            + " REFERENCES " + TABLE_tblROWCHOICE + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_COL_CHOICE_FK_ID + ") "
            + " REFERENCES " + TABLE_tblCOLCHOICE + "(" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE);";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblDATACOLLECTOR` (19)
    //-- -------------------------------------------------------------------------------------------
    private static final String CREATE_TABLE_tblDATACOLLECTOR = "CREATE TABLE " +
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
    //-- Table `tblINDICATOR` (20)
    //-- -------------------------------------------------------------------------------------------
    private static final String CREATE_TABLE_tblINDICATOR = "CREATE TABLE " +
            TABLE_tblINDICATOR + "("
            + KEY_ID + " INTEGER NOT NULL, "
            + KEY_LOGFRAME_FK_ID + " INTEGER NOT NULL, "
            + KEY_TARGET_FK_ID + " INTEGER NOT NULL, "
            + KEY_INDICATOR_TYPE_FK_ID + " INTEGER NOT NULL, "
            + KEY_FREQUENCY_FK_ID + " INTEGER NOT NULL, "
            + KEY_METHOD_FK_ID + " INTEGER NOT NULL, "
            + KEY_CHART_FK_ID + " INTEGER NOT NULL, "
            + KEY_DATA_COLLECTOR_FK_ID + " INTEGER NOT NULL, "
            + KEY_SERVER_ID + " INTEGER DEFAULT NULL, "
            + KEY_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL DEFAULT 0, "
            + KEY_NAME + " TEXT NOT NULL, "
            + KEY_DESCRIPTION + " TEXT, "
            + KEY_START_DATE + " DATE, "
            + KEY_END_DATE + " DATE, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + " PRIMARY KEY (" + KEY_ID + "),"
            + " FOREIGN KEY (" + KEY_LOGFRAME_FK_ID + ") "
            + " REFERENCES " + TABLE_tblLOGFRAME + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_TARGET_FK_ID + ") "
            + " REFERENCES " + TABLE_tblTARGET + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_FREQUENCY_FK_ID + ") "
            + " REFERENCES " + TABLE_tblFREQUENCY + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_METHOD_FK_ID + ") "
            + " REFERENCES " + TABLE_tblMETHOD + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" +  KEY_CHART_FK_ID+ ") "
            + " REFERENCES " + TABLE_tblCHART + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_DATA_COLLECTOR_FK_ID + ") "
            + " REFERENCES " + TABLE_tblDATACOLLECTOR + "(" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE);";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblINDICATORMILESTONE` (21)
    //-- -------------------------------------------------------------------------------------------
    private static final String CREATE_TABLE_tblINDICATORMILESTONE = "CREATE TABLE " +
            TABLE_tblINDICATORMILESTONE + "("
            + KEY_MILESTONE_FK_ID + " INTEGER NOT NULL, "
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
            + " PRIMARY KEY (" + KEY_MILESTONE_FK_ID + "),"
            + " FOREIGN KEY (" + KEY_MILESTONE_FK_ID + ") "
            + " REFERENCES " + TABLE_tblMILESTONE+ " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_INDICATOR_FK_ID + ") "
            + " REFERENCES " + TABLE_tblINDICATOR + "(" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE);";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblQUALITATIVEMILESTONE` (22)
    //-- -------------------------------------------------------------------------------------------
    private static final String CREATE_TABLE_tblQUALITATIVEMILESTONE = "CREATE TABLE " +
            TABLE_tblQUALITATIVEMILESTONE + "("
            + KEY_MILESTONE_FK_ID + " INTEGER NOT NULL, "
            + KEY_BASELINE_SCORE_FK_ID + " INTEGER, "
            + KEY_TARGET_SCORE_FK_ID + " INTEGER, "
            + " PRIMARY KEY (" + KEY_MILESTONE_FK_ID + "),"
            + " FOREIGN KEY (" + KEY_MILESTONE_FK_ID + ") "
            + " REFERENCES " + TABLE_tblINDICATORMILESTONE + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_BASELINE_SCORE_FK_ID + ") "
            + " REFERENCES " + TABLE_tblCRITERIASCORE + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_TARGET_SCORE_FK_ID + ") "
            + " REFERENCES " + TABLE_tblCRITERIASCORE + "(" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE);";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblQUANTITATIVEMILESTONE` (23)
    //-- -------------------------------------------------------------------------------------------
    private static final String CREATE_TABLE_tblQUANTITATIVEMILESTONE = "CREATE TABLE " +
            TABLE_tblQUANTITATIVEMILESTONE + "("
            + KEY_MILESTONE_FK_ID + " INTEGER NOT NULL, "
            + KEY_BASELINE_VALUE + " INTEGER, "
            + KEY_TARGET_VALUE + " INTEGER, "
            + " PRIMARY KEY (" + KEY_MILESTONE_FK_ID + "),"
            + " FOREIGN KEY (" + KEY_MILESTONE_FK_ID + ") "
            + " REFERENCES " + TABLE_tblMILESTONE + "(" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE);";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblARRAYMILESTONE` (24)
    //-- -------------------------------------------------------------------------------------------
    private static final String CREATE_TABLE_tblARRAYMILESTONE = "CREATE TABLE " +
            TABLE_tblARRAYMILESTONE + "("
            + KEY_QUANTITATIVE_MILESTONE_FK_ID + " INTEGER NOT NULL, "
            + KEY_ARRAY_CHOICE_FK_ID + " INTEGER NOT NULL, "
            + KEY_DISAGGREGATED_BASELINE + " INTEGER, "
            + KEY_DISAGGREGATED_TARGET + " INTEGER, "
            + " PRIMARY KEY (" + KEY_QUANTITATIVE_MILESTONE_FK_ID + "),"
            + " FOREIGN KEY (" + KEY_QUANTITATIVE_MILESTONE_FK_ID + ") "
            + " REFERENCES " + TABLE_tblQUANTITATIVEMILESTONE + " (" + KEY_MILESTONE_FK_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_ARRAY_CHOICE_FK_ID + ") "
            + " REFERENCES " + TABLE_tblARRAYCHOICE + "(" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE);";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblMATRIXMILESTONE` (25)
    //-- -------------------------------------------------------------------------------------------
    private static final String CREATE_TABLE_tblMATRIXMILESTONE = "CREATE TABLE " +
            TABLE_tblMATRIXMILESTONE + "("
            + KEY_QUANTITATIVE_MILESTONE_FK_ID + " INTEGER NOT NULL, "
            + KEY_ROW_CHOICE_FK_ID + " INTEGER NOT NULL, "
            + KEY_COL_CHOICE_FK_ID + " INTEGER NOT NULL, "
            + KEY_DISAGGREGATED_BASELINE + " INTEGER, "
            + KEY_DISAGGREGATED_TARGET + " INTEGER, "
            + " PRIMARY KEY (" + KEY_QUANTITATIVE_MILESTONE_FK_ID + "),"
            + " FOREIGN KEY (" + KEY_QUANTITATIVE_MILESTONE_FK_ID + ") "
            + " REFERENCES " + TABLE_tblQUANTITATIVEMILESTONE + " (" + KEY_MILESTONE_FK_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_ROW_CHOICE_FK_ID + ") "
            + " REFERENCES " + TABLE_tblROWCHOICE + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_COL_CHOICE_FK_ID + ") "
            + " REFERENCES " + TABLE_tblCOLCHOICE + "(" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE);";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblINDICATORPROGRESS` (26)
    //-- -------------------------------------------------------------------------------------------
    private static final String CREATE_TABLE_tblINDICATORPROGRESS = "CREATE TABLE " +
            TABLE_tblINDICATORPROGRESS + "("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_INDICATOR_MILESTONE_FK_ID + " INTEGER NOT NULL, "
            + KEY_DATA_COLLECTOR_FK_ID + " INTEGER NOT NULL, "
            + KEY_SERVER_ID + " INTEGER DEFAULT NULL, "
            + KEY_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL DEFAULT 0, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + " FOREIGN KEY (" + KEY_INDICATOR_MILESTONE_FK_ID + ") "
            + " REFERENCES " + TABLE_tblINDICATORMILESTONE+ " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_DATA_COLLECTOR_FK_ID + ") "
            + " REFERENCES " + TABLE_tblDATACOLLECTOR + "(" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE);";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblQUALITATIVEPROGRESS` (27)
    //-- -------------------------------------------------------------------------------------------
    private static final String CREATE_TABLE_tblQUALITATIVEPROGRESS = "CREATE TABLE " +
            TABLE_tblQUALITATIVEPROGRESS + "("
            + KEY_PROGRESS_FK_ID + " INTEGER NOT NULL, "
            + KEY_ACTUAL_SCORE_FK_ID + " INTEGER, "
            + " PRIMARY KEY (" + KEY_PROGRESS_FK_ID + "),"
            + " FOREIGN KEY (" + KEY_PROGRESS_FK_ID + ") "
            + " REFERENCES " + TABLE_tblINDICATORPROGRESS + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_ACTUAL_SCORE_FK_ID + ") "
            + " REFERENCES " + TABLE_tblCRITERIASCORE + "(" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE);";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblQUANTITATIVEPROGRESS` (28)
    //-- -------------------------------------------------------------------------------------------
    private static final String CREATE_TABLE_tblQUANTITATIVEPROGRESS = "CREATE TABLE " +
            TABLE_tblQUANTITATIVEPROGRESS + "("
            + KEY_PROGRESS_FK_ID + " INTEGER NOT NULL, "
            + KEY_ACTUAL_VALUE + " INTEGER, "
            + " PRIMARY KEY (" + KEY_PROGRESS_FK_ID + "),"
            + " FOREIGN KEY (" + KEY_PROGRESS_FK_ID + ") "
            + " REFERENCES " + TABLE_tblINDICATORPROGRESS + "(" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE);";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblARRAYMILESTONE` (29)
    //-- -------------------------------------------------------------------------------------------
    private static final String CREATE_TABLE_tblARRAYPROGRESS = "CREATE TABLE " +
            TABLE_tblARRAYPROGRESS + "("
            + KEY_QUANTITATIVE_PROGRESS_FK_ID + " INTEGER NOT NULL, "
            + KEY_ARRAY_CHOICE_FK_ID + " INTEGER NOT NULL, "
            + KEY_DISAGGREGATED_ACTUAL + " INTEGER, "
            + " PRIMARY KEY (" + KEY_QUANTITATIVE_PROGRESS_FK_ID + "),"
            + " FOREIGN KEY (" + KEY_QUANTITATIVE_PROGRESS_FK_ID + ") "
            + " REFERENCES " + TABLE_tblQUANTITATIVEPROGRESS + " (" + KEY_PROGRESS_FK_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_ARRAY_CHOICE_FK_ID + ") "
            + " REFERENCES " + TABLE_tblARRAYCHOICE + "(" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE);";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblMATRIXPROGRESS` (30)
    //-- -------------------------------------------------------------------------------------------
    private static final String CREATE_TABLE_tblMATRIXPROGRESS = "CREATE TABLE " +
            TABLE_tblMATRIXPROGRESS + "("
            + KEY_QUANTITATIVE_PROGRESS_FK_ID + " INTEGER NOT NULL, "
            + KEY_ROW_CHOICE_FK_ID + " INTEGER NOT NULL, "
            + KEY_COL_CHOICE_FK_ID + " INTEGER NOT NULL, "
            + KEY_DISAGGREGATED_ACTUAL + " INTEGER, "
            + " PRIMARY KEY (" + KEY_QUANTITATIVE_PROGRESS_FK_ID + "),"
            + " FOREIGN KEY (" + KEY_QUANTITATIVE_PROGRESS_FK_ID + ") "
            + " REFERENCES " + TABLE_tblQUANTITATIVEPROGRESS + " (" + KEY_PROGRESS_FK_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_ROW_CHOICE_FK_ID + ") "
            + " REFERENCES " + TABLE_tblROWCHOICE + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_COL_CHOICE_FK_ID + ") "
            + " REFERENCES " + TABLE_tblCOLCHOICE + "(" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE);";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblINDICATOR_MOV` (31)
    //-- -------------------------------------------------------------------------------------------
    private static final String CREATE_TABLE_tblINDICATOR_MOV = "CREATE TABLE " +
            TABLE_tblINDICATOR_MOV + "("
            + KEY_INDICATOR_FK_ID + " INTEGER NOT NULL, "
            + KEY_MOV_FK_ID + " INTEGER NOT NULL, "
            + " PRIMARY KEY (" + KEY_INDICATOR_FK_ID +", " +KEY_MOV_FK_ID + "),"
            + " FOREIGN KEY (" + KEY_INDICATOR_FK_ID + ") "
            + " REFERENCES " + TABLE_tblINDICATOR+ " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_MOV_FK_ID + ") "
            + " REFERENCES " + TABLE_tblMOV + "(" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE);";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblINDICATOR_DATASOURCE` (32)
    //-- -------------------------------------------------------------------------------------------
    private static final String CREATE_TABLE_tblINDICATOR_DATASOURCE = "CREATE TABLE " +
            TABLE_tblINDICATOR_DATASOURCE + "("
            + KEY_INDICATOR_FK_ID + " INTEGER NOT NULL, "
            + KEY_DATA_SOURCE_FK_ID + " INTEGER NOT NULL, "
            + " PRIMARY KEY (" + KEY_INDICATOR_FK_ID +", " + KEY_DATA_SOURCE_FK_ID + "),"
            + " FOREIGN KEY (" + KEY_INDICATOR_FK_ID + ") "
            + " REFERENCES " + TABLE_tblINDICATOR + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_DATA_SOURCE_FK_ID + ") "
            + " REFERENCES " + TABLE_tblDATASOURCE + "(" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE);";

    /*#################################### END MONITORING MODULE #################################*/

    /*###################################### START RAID MODULE ###################################*/

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblPIM` (1)
    //-- -------------------------------------------------------------------------------------------
    private static final String CREATE_TABLE_tblPIM = "CREATE TABLE " +
            TABLE_tblPIM + "("
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
    //-- Table `tblRAIDLIKELIHOOD` (2)
    //-- -------------------------------------------------------------------------------------------
    private static final String CREATE_TABLE_tblRAIDLIKELIHOOD = "CREATE TABLE " +
            TABLE_tblRAIDLIKELIHOOD + "("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_SERVER_ID + " INTEGER DEFAULT NULL, "
            + KEY_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL DEFAULT 0, "
            + KEY_NAME + " TEXT NOT NULL, "
            + KEY_DESCRIPTION + " TEXT NULL, "
            + KEY_RAIDLIKELIHOOD_VALUE + " INTEGER, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP " + ");";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblRAIDIMPACT` (3)
    //-- -------------------------------------------------------------------------------------------
    private static final String CREATE_TABLE_tblRAIDIMPACT = "CREATE TABLE " +
            TABLE_tblRAIDIMPACT + "("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_SERVER_ID + " INTEGER DEFAULT NULL, "
            + KEY_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL DEFAULT 0, "
            + KEY_NAME + " TEXT NOT NULL, "
            + KEY_DESCRIPTION + " TEXT NULL, "
            + KEY_RAIDIMPACT_VALUE + " INTEGER, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP " + ");";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblROBOT` (4)
    //-- -------------------------------------------------------------------------------------------
    private static final String CREATE_TABLE_tblROBOT = "CREATE TABLE " +
            TABLE_tblROBOT + "("
            + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + KEY_SERVER_ID + " INTEGER DEFAULT NULL, "
            + KEY_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL DEFAULT 0, "
            + KEY_COLOUR_NAME + " TEXT NOT NULL, "
            + KEY_LOWER_LIMIT + " INTEGER, "
            + KEY_UPPER_LIMIT + " INTEGER, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP " + ");";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblPIMSET` (5)
    //-- -------------------------------------------------------------------------------------------
    private static final String CREATE_TABLE_tblPIMSET = "CREATE TABLE " +
            TABLE_tblPIMSET + "("
            + KEY_PIM_FK_ID + " INTEGER NOT NULL, "
            + KEY_RAID_LIKELIHOOD_FK_ID + " INTEGER NOT NULL, "
            + KEY_RAID_IMPACT_FK_ID + " INTEGER NOT NULL, "
            + KEY_ROBOT_FK_ID + " INTEGER NOT NULL, "
            + " PRIMARY KEY (" + KEY_PIM_FK_ID +", "+ KEY_RAID_LIKELIHOOD_FK_ID +", "+
                KEY_RAID_IMPACT_FK_ID +" ), "
            + " FOREIGN KEY (" + KEY_PIM_FK_ID + ") "
            + " REFERENCES " + TABLE_tblPIM + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_RAID_LIKELIHOOD_FK_ID + ") "
            + " REFERENCES " + TABLE_tblRAIDLIKELIHOOD + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_RAID_IMPACT_FK_ID + ") "
            + " REFERENCES " + TABLE_tblRAIDIMPACT + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_ROBOT_FK_ID + ") "
            + " REFERENCES " + TABLE_tblROBOT + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE); ";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblRAIDREGISTER` (6)
    //-- -------------------------------------------------------------------------------------------
    private static final String CREATE_TABLE_tblRAIDREGISTER = "CREATE TABLE " +
            TABLE_tblRAIDREGISTER + "("
            + KEY_ID + " INTEGER NOT NULL, "
            + KEY_PIM_FK_ID + " INTEGER NOT NULL, "
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
            + " FOREIGN KEY (" + KEY_PIM_FK_ID + ") "
            + " REFERENCES " + TABLE_tblPIM + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE); ";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblRISKREGISTER` (7)
    //-- -------------------------------------------------------------------------------------------
    private static final String CREATE_TABLE_tblRISKREGISTER = "CREATE TABLE " +
            TABLE_tblRISKREGISTER + "("
            + KEY_REGISTER_FK_ID + " INTEGER NOT NULL, "
            + " PRIMARY KEY (" + KEY_REGISTER_FK_ID + "),"
            + " FOREIGN KEY (" + KEY_REGISTER_FK_ID + ") "
            + " REFERENCES " + TABLE_tblRAIDREGISTER + "(" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE);";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblASSUMPTIONREGISTER` (8)
    //-- -------------------------------------------------------------------------------------------
    private static final String CREATE_TABLE_tblASSUMPTIONREGISTER = "CREATE TABLE " +
            TABLE_tblASSUMPTIONREGISTER + "("
            + KEY_REGISTER_FK_ID + " INTEGER NOT NULL, "
            + " PRIMARY KEY (" + KEY_REGISTER_FK_ID + "),"
            + " FOREIGN KEY (" + KEY_REGISTER_FK_ID + ") "
            + " REFERENCES " + TABLE_tblRAIDREGISTER + "(" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE);";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblISSUEREGISTER` (9)
    //-- -------------------------------------------------------------------------------------------
    private static final String CREATE_TABLE_tblISSUEREGISTER = "CREATE TABLE " +
            TABLE_tblISSUEREGISTER + "("
            + KEY_REGISTER_FK_ID + " INTEGER NOT NULL, "
            + " PRIMARY KEY (" + KEY_REGISTER_FK_ID + "),"
            + " FOREIGN KEY (" + KEY_REGISTER_FK_ID + ") "
            + " REFERENCES " + TABLE_tblRAIDREGISTER + "(" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE);";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblDEPENDENCYREGISTER` (10)
    //-- -------------------------------------------------------------------------------------------
    private static final String CREATE_TABLE_tblDEPENDENCYREGISTER = "CREATE TABLE " +
            TABLE_tblDEPENDENCYREGISTER + "("
            + KEY_REGISTER_FK_ID + " INTEGER NOT NULL, "
            + " PRIMARY KEY (" + KEY_REGISTER_FK_ID + "),"
            + " FOREIGN KEY (" + KEY_REGISTER_FK_ID + ") "
            + " REFERENCES " + TABLE_tblRAIDREGISTER + "(" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE);";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblRAIDLOG` (11)
    //-- -------------------------------------------------------------------------------------------
    private static final String CREATE_TABLE_tblRAIDLOG = "CREATE TABLE " +
            TABLE_tblRAIDLOG + "("
            + KEY_ID + " INTEGER NOT NULL, "
            + KEY_LOGFRAME_FK_ID + " INTEGER NOT NULL, "
            + KEY_RISK_REGISTER_FK_ID + " INTEGER NOT NULL, "
            + KEY_ASS_REGISTER_FK_ID + " INTEGER NOT NULL, "
            + KEY_ISSUE_REGISTER_FK_ID + " INTEGER NOT NULL, "
            + KEY_DEP_REGISTER_FK_ID + " INTEGER NOT NULL, "
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
            + " FOREIGN KEY (" + KEY_LOGFRAME_FK_ID + ") "
            + " REFERENCES " + TABLE_tblLOGFRAME + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_RISK_REGISTER_FK_ID + ") "
            + " REFERENCES " + TABLE_tblRISKREGISTER + " (" + KEY_REGISTER_FK_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_ASS_REGISTER_FK_ID + ") "
            + " REFERENCES " + TABLE_tblASSUMPTIONREGISTER + " (" + KEY_REGISTER_FK_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_ISSUE_REGISTER_FK_ID + ") "
            + " REFERENCES " + TABLE_tblISSUEREGISTER + " (" + KEY_REGISTER_FK_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_DEP_REGISTER_FK_ID + ") "
            + " REFERENCES " + TABLE_tblDEPENDENCYREGISTER + " (" + KEY_REGISTER_FK_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE); ";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblRISK` (12)
    //-- -------------------------------------------------------------------------------------------
    private static final String CREATE_TABLE_tblRISK = "CREATE TABLE " +
            TABLE_tblRISK + "("
            + KEY_RAID_FK_ID + " INTEGER NOT NULL, "
            + KEY_RISK_REGISTER_FK_ID + " INTEGER NOT NULL, "
            + " PRIMARY KEY (" + KEY_RAID_FK_ID + "),"
            + " FOREIGN KEY (" + KEY_RAID_FK_ID + ") "
            + " REFERENCES " + TABLE_tblRAID + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_RISK_REGISTER_FK_ID + ") "
            + " REFERENCES " + TABLE_tblRISKREGISTER + "(" + KEY_REGISTER_FK_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE);";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblRISKROOTCAUSE` (13)
    //-- -------------------------------------------------------------------------------------------
    private static final String CREATE_TABLE_tblRISKROOTCAUSE = "CREATE TABLE " +
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
            + " REFERENCES " + TABLE_tblRISK + " (" + KEY_RAID_FK_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE); ";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblRISKCONSEQUENCE` (14)
    //-- -------------------------------------------------------------------------------------------
    private static final String CREATE_TABLE_tblRISKCONSEQUENCE = "CREATE TABLE " +
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
            + " REFERENCES " + TABLE_tblRISK + " (" + KEY_RAID_FK_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE); ";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblASSUMPTION` (15)
    //-- -------------------------------------------------------------------------------------------
    private static final String CREATE_TABLE_tblASSUMPTION = "CREATE TABLE " +
            TABLE_tblASSUMPTION + "("
            + KEY_RAID_FK_ID + " INTEGER NOT NULL, "
            + KEY_ASS_REGISTER_FK_ID + " INTEGER NOT NULL, "
            + " PRIMARY KEY (" + KEY_RAID_FK_ID + "),"
            + " FOREIGN KEY (" + KEY_RAID_FK_ID + ") "
            + " REFERENCES " + TABLE_tblRAID + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_ASS_REGISTER_FK_ID + ") "
            + " REFERENCES " + TABLE_tblRISKREGISTER + "(" + KEY_REGISTER_FK_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE);";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblISSUE` (16)
    //-- -------------------------------------------------------------------------------------------
    private static final String CREATE_TABLE_tblISSUE = "CREATE TABLE " +
            TABLE_tblISSUE + "("
            + KEY_RAID_FK_ID + " INTEGER NOT NULL, "
            + KEY_ISSUE_REGISTER_FK_ID + " INTEGER NOT NULL, "
            + " PRIMARY KEY (" + KEY_RAID_FK_ID + "),"
            + " FOREIGN KEY (" + KEY_RAID_FK_ID + ") "
            + " REFERENCES " + TABLE_tblRAID + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_ISSUE_REGISTER_FK_ID + ") "
            + " REFERENCES " + TABLE_tblRISKREGISTER + "(" + KEY_REGISTER_FK_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE);";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblDEPENDENCY` (17)
    //-- -------------------------------------------------------------------------------------------
    private static final String CREATE_TABLE_tblDEPENDENCY = "CREATE TABLE " +
            TABLE_tblDEPENDENCY + "("
            + KEY_RAID_FK_ID + " INTEGER NOT NULL, "
            + KEY_DEP_REGISTER_FK_ID + " INTEGER NOT NULL, "
            + KEY_HOW_VALIDATE + " TEXT NOT NULL, "
            + KEY_VALIDATED + " INTEGER NOT NULL, "
            + " PRIMARY KEY (" + KEY_RAID_FK_ID + "),"
            + " FOREIGN KEY (" + KEY_RAID_FK_ID + ") "
            + " REFERENCES " + TABLE_tblRAID + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_DEP_REGISTER_FK_ID + ") "
            + " REFERENCES " + TABLE_tblRISKREGISTER + "(" + KEY_REGISTER_FK_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE);";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblMILESTONEREVIEW` (18)
    //-- -------------------------------------------------------------------------------------------
    private static final String CREATE_TABLE_tblMILESTONEREVIEW = "CREATE TABLE " +
            TABLE_tblMILESTONEREVIEW + "("
            + KEY_MILESTONE_FK_ID + " INTEGER NOT NULL, "
            + KEY_RAID_LIKELIHOOD_FK_ID + " INTEGER NOT NULL, "
            + KEY_RAID_IMPACT_FK_ID + " INTEGER NOT NULL, "
            + KEY_ROBOT_FK_ID + " INTEGER NOT NULL, "
            + KEY_SERVER_ID + " INTEGER DEFAULT NULL, "
            + KEY_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL DEFAULT 0, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + " PRIMARY KEY (" + KEY_MILESTONE_FK_ID + " ), "
            + " FOREIGN KEY (" + KEY_MILESTONE_FK_ID + ") "
            + " REFERENCES " + TABLE_tblMILESTONE + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_RAID_LIKELIHOOD_FK_ID + ") "
            + " REFERENCES " + TABLE_tblRAIDLIKELIHOOD + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_RAID_IMPACT_FK_ID + ") "
            + " REFERENCES " + TABLE_tblRAIDIMPACT+ " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_ROBOT_FK_ID + ") "
            + " REFERENCES " + TABLE_tblROBOT + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE); ";
    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblREVIEWCOMMENT` (19)
    //-- -------------------------------------------------------------------------------------------
    private static final String CREATE_TABLE_tblREVIEWCOMMENT = "CREATE TABLE " +
            TABLE_tblREVIEWCOMMENT + "("
            + KEY_ID + " INTEGER NOT NULL, "
            + KEY_STAFF_FK_ID + " INTEGER NOT NULL, "
            + KEY_MILESTONE_REVIEW_FK_ID+ " INTEGER NOT NULL, "
            + KEY_SERVER_ID + " INTEGER DEFAULT NULL, "
            + KEY_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL DEFAULT 0, "
            + KEY_REVIEW_COMMENT + " TEXT, "
            + KEY_CREATED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_MODIFIED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + KEY_SYNCED_DATE + " DATE DEFAULT CURRENT_TIMESTAMP, "
            + " PRIMARY KEY (" + KEY_ID + " ), "
            + " FOREIGN KEY (" + KEY_MILESTONE_REVIEW_FK_ID + ") "
            + " REFERENCES " + TABLE_tblMILESTONEREVIEW + " (" + KEY_MILESTONE_FK_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_STAFF_FK_ID + ") "
            + " REFERENCES " + TABLE_tblHUMANSET + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE); ";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblRISKREVIEW` (20)
    //-- -------------------------------------------------------------------------------------------
    private static final String CREATE_TABLE_tblRISKREVIEW = "CREATE TABLE " +
            TABLE_tblRISKREVIEW + "("
            + KEY_MILESTONE_REVIEW_FK_ID + " INTEGER NOT NULL, "
            + KEY_RISK_FK_ID + " INTEGER NOT NULL, "
            + KEY_RESIDUAL_RISK + " DOUBLE, "
            + " PRIMARY KEY (" + KEY_MILESTONE_REVIEW_FK_ID + "),"
            + " FOREIGN KEY (" + KEY_MILESTONE_REVIEW_FK_ID + ") "
            + " REFERENCES " + TABLE_tblMILESTONEREVIEW + " (" + KEY_MILESTONE_FK_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_RISK_FK_ID + ") "
            + " REFERENCES " + TABLE_tblRISK + "(" + KEY_RAID_FK_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE);";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblASSUMPTIONREVIEW` (21)
    //-- -------------------------------------------------------------------------------------------
    private static final String CREATE_TABLE_tblASSUMPTIONREVIEW = "CREATE TABLE " +
            TABLE_tblASSUMPTIONREVIEW + "("
            + KEY_MILESTONE_REVIEW_FK_ID + " INTEGER NOT NULL, "
            + KEY_ASSUMPTION_FK_ID + " INTEGER NOT NULL, "
            + KEY_ASSUMPTION_RATING + " DOUBLE, "
            + " PRIMARY KEY (" + KEY_MILESTONE_REVIEW_FK_ID + "),"
            + " FOREIGN KEY (" + KEY_MILESTONE_REVIEW_FK_ID + ") "
            + " REFERENCES " + TABLE_tblMILESTONEREVIEW + " (" + KEY_MILESTONE_FK_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_ASSUMPTION_FK_ID + ") "
            + " REFERENCES " + TABLE_tblASSUMPTION + "(" + KEY_RAID_FK_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE);";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblISSUEREVIEW` (22)
    //-- -------------------------------------------------------------------------------------------
    private static final String CREATE_TABLE_tblISSUEREVIEW = "CREATE TABLE " +
            TABLE_tblISSUEREVIEW + "("
            + KEY_MILESTONE_REVIEW_FK_ID + " INTEGER NOT NULL, "
            + KEY_ISSUE_FK_ID + " INTEGER NOT NULL, "
            + KEY_PRIORITY_RATING + " DOUBLE, "
            + " PRIMARY KEY (" + KEY_MILESTONE_REVIEW_FK_ID + "),"
            + " FOREIGN KEY (" + KEY_MILESTONE_REVIEW_FK_ID + ") "
            + " REFERENCES " + TABLE_tblMILESTONEREVIEW + " (" + KEY_MILESTONE_FK_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_ISSUE_FK_ID + ") "
            + " REFERENCES " + TABLE_tblISSUE + "(" + KEY_RAID_FK_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE);";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblDEPENDENCYREVIEW` (23)
    //-- -------------------------------------------------------------------------------------------
    private static final String CREATE_TABLE_tblDEPENDENCYREVIEW = "CREATE TABLE " +
            TABLE_tblDEPENDENCYREVIEW + "("
            + KEY_MILESTONE_REVIEW_FK_ID + " INTEGER NOT NULL, "
            + KEY_DEPENDENCY_FK_ID + " INTEGER NOT NULL, "
            + KEY_DEPENDENCY_RATING + " DOUBLE, "
            + " PRIMARY KEY (" + KEY_MILESTONE_REVIEW_FK_ID + "),"
            + " FOREIGN KEY (" + KEY_MILESTONE_REVIEW_FK_ID + ") "
            + " REFERENCES " + TABLE_tblMILESTONEREVIEW + " (" + KEY_MILESTONE_FK_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_DEPENDENCY_FK_ID + ") "
            + " REFERENCES " + TABLE_tblDEPENDENCY + "(" + KEY_RAID_FK_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE);";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblACTION` (24) FIXME: TABLE_tblACTIVITY = TABLE_tblWORKPLAN
    //-- -------------------------------------------------------------------------------------------
    private static final String CREATE_TABLE_tblACTION = "CREATE TABLE " +
            TABLE_tblACTION + "("
            + KEY_ACTIVITY_FK_ID + " INTEGER NOT NULL, "
            + " PRIMARY KEY (" + KEY_ACTIVITY_FK_ID + "),"
            + " FOREIGN KEY (" + KEY_ACTIVITY_FK_ID + ") "
            + " REFERENCES " + TABLE_tblACTIVITY + "(" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE);";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblACTIONTASK` (25)
    //-- -------------------------------------------------------------------------------------------
    private static final String CREATE_TABLE_tblACTIONTASK = "CREATE TABLE " +
            TABLE_tblACTIONTASK + "("
            + KEY_TASK_FK_ID + " INTEGER NOT NULL, "
            + KEY_ACTION_FK_ID + " INTEGER NOT NULL, "
            + " PRIMARY KEY (" + KEY_TASK_FK_ID + " ), "
            + " FOREIGN KEY (" + KEY_TASK_FK_ID + ") "
            + " REFERENCES " + TABLE_tblTASK + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_ACTION_FK_ID + ") "
            + " REFERENCES " + TABLE_tblRISKACTION + " (" + KEY_ACTIVITY_FK_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE); ";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblMILESTONE_ACTION` (26)
    //-- -------------------------------------------------------------------------------------------
    private static final String CREATE_TABLE_tblMILESTONE_ACTION = "CREATE TABLE " +
            TABLE_tblMILESTONE_ACTION + "("
            + KEY_MILESTONE_REVIEW_FK_ID + " INTEGER NOT NULL, "
            + KEY_ACTION_TASK_FK_ID + " INTEGER NOT NULL, "
            + " PRIMARY KEY (" + KEY_MILESTONE_REVIEW_FK_ID + "," + KEY_ACTION_TASK_FK_ID + " ), "
            + " FOREIGN KEY (" + KEY_MILESTONE_REVIEW_FK_ID + ") "
            + " REFERENCES " + TABLE_tblMILESTONEREVIEW + " (" + KEY_MILESTONE_FK_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_ACTION_TASK_FK_ID + ") "
            + " REFERENCES " + TABLE_tblACTIONTASK + " (" + KEY_TASK_FK_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE); ";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblRISKACTIONTYPE` (27)
    //-- -------------------------------------------------------------------------------------------
    private static final String CREATE_TABLE_tblRISKACTIONTYPE = "CREATE TABLE " +
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
    //-- Table `tblRISKACTION` (28)
    //-- -------------------------------------------------------------------------------------------
    private static final String CREATE_TABLE_tblRISKACTION = "CREATE TABLE " +
            TABLE_tblRISKACTION + "("
            + KEY_ACTION_FK_ID + " INTEGER NOT NULL, "
            + KEY_RISK_REVIEW_FK_ID + " INTEGER NOT NULL, "
            + KEY_RISK_ACTION_TYPE_FK_ID + " INTEGER NOT NULL, "
            + " PRIMARY KEY (" + KEY_ACTION_FK_ID + " ), "
            + " FOREIGN KEY (" + KEY_ACTION_FK_ID + ") "
            + " REFERENCES " + TABLE_tblRISKACTION + " (" + KEY_ACTIVITY_FK_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_RISK_REVIEW_FK_ID + ") "
            + " REFERENCES " + TABLE_tblRISKREVIEW + " (" + KEY_MILESTONE_REVIEW_FK_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_RISK_ACTION_TYPE_FK_ID + ") "
            + " REFERENCES " + TABLE_tblRISKACTIONTYPE + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE); ";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblCURRENTCONTROL` (29)
    //-- -------------------------------------------------------------------------------------------
    private static final String CREATE_TABLE_tblCURRENTCONTROL = "CREATE TABLE " +
            TABLE_tblCURRENTCONTROL + "("
            + KEY_RISK_ACTION_FK_ID + " INTEGER NOT NULL, "
            + " PRIMARY KEY (" + KEY_RISK_ACTION_FK_ID + " ), "
            + " FOREIGN KEY (" + KEY_RISK_ACTION_FK_ID + ") "
            + " REFERENCES " + TABLE_tblRISKACTION + " (" + KEY_ACTION_FK_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE); ";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblADDITIONALCONTROL` (30)
    //-- -------------------------------------------------------------------------------------------
    private static final String CREATE_TABLE_tblADDITIONALCONTROL = "CREATE TABLE " +
            TABLE_tblADDITIONALCONTROL + "("
            + KEY_RISK_ACTION_FK_ID + " INTEGER NOT NULL, "
            + " PRIMARY KEY (" + KEY_RISK_ACTION_FK_ID + " ), "
            + " FOREIGN KEY (" + KEY_RISK_ACTION_FK_ID + ") "
            + " REFERENCES " + TABLE_tblRISKACTION + " (" + KEY_ACTION_FK_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE); ";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblASSUMPTIONACTION` (31)
    //-- -------------------------------------------------------------------------------------------
    private static final String CREATE_TABLE_tblASSUMPTIONACTION = "CREATE TABLE " +
            TABLE_tblASSUMPTIONACTION + "("
            + KEY_ACTION_FK_ID + " INTEGER NOT NULL, "
            + KEY_ASSUMPTION_REVIEW_FK_ID + " INTEGER NOT NULL, "
            + " PRIMARY KEY (" + KEY_ACTION_FK_ID + " ), "
            + " FOREIGN KEY (" + KEY_ACTION_FK_ID + ") "
            + " REFERENCES " + TABLE_tblRISKACTION + " (" + KEY_ACTIVITY_FK_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_ASSUMPTION_REVIEW_FK_ID + ") "
            + " REFERENCES " + TABLE_tblASSUMPTIONREVIEW + " (" + KEY_MILESTONE_REVIEW_FK_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE); ";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblISSUEACTION` (32)
    //-- -------------------------------------------------------------------------------------------
    private static final String CREATE_TABLE_tblISSUEACTION = "CREATE TABLE " +
            TABLE_tblISSUEACTION + "("
            + KEY_ACTION_FK_ID + " INTEGER NOT NULL, "
            + KEY_ISSUE_REVIEW_FK_ID + " INTEGER NOT NULL, "
            + " PRIMARY KEY (" + KEY_ACTION_FK_ID + " ), "
            + " FOREIGN KEY (" + KEY_ACTION_FK_ID + ") "
            + " REFERENCES " + TABLE_tblRISKACTION + " (" + KEY_ACTIVITY_FK_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_ISSUE_REVIEW_FK_ID + ") "
            + " REFERENCES " + TABLE_tblISSUEREVIEW + " (" + KEY_MILESTONE_REVIEW_FK_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE); ";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblISSUEACTION` (33)
    //-- -------------------------------------------------------------------------------------------
    private static final String CREATE_TABLE_tblDEPENDENCYACTION = "CREATE TABLE " +
            TABLE_tblDEPENDENCYACTION + "("
            + KEY_ACTION_FK_ID + " INTEGER NOT NULL, "
            + KEY_DEPENDENCY_REVIEW_FK_ID + " INTEGER NOT NULL, "
            + " PRIMARY KEY (" + KEY_ACTION_FK_ID + " ), "
            + " FOREIGN KEY (" + KEY_ACTION_FK_ID + ") "
            + " REFERENCES " + TABLE_tblRISKACTION + " (" + KEY_ACTIVITY_FK_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_DEPENDENCY_REVIEW_FK_ID + ") "
            + " REFERENCES " + TABLE_tblDEPENDENCYREVIEW + " (" + KEY_MILESTONE_REVIEW_FK_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE); ";

    /*####################################### END RAID MODULE ####################################*/

    /*###################################### START AWPB MODULE ###################################*/

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblHUMAN` (1)
    //-- -------------------------------------------------------------------------------------------
    private static final String CREATE_TABLE_tblHUMAN = "CREATE TABLE " + TABLE_tblHUMAN + "("
            + KEY_INPUT_FK_ID + " INTEGER NOT NULL, "
            + KEY_QUANTITY + " INTEGER, "
            + " PRIMARY KEY (" + KEY_INPUT_FK_ID + " ), "
            + " FOREIGN KEY (" + KEY_INPUT_FK_ID + ") "
            + " REFERENCES " + TABLE_tblINPUT + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE); ";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblMATERIAL` (2)
    //-- -------------------------------------------------------------------------------------------
    private static final String CREATE_TABLE_tblMATERIAL = "CREATE TABLE " + TABLE_tblMATERIAL + "("
            + KEY_INPUT_FK_ID + " INTEGER NOT NULL, "
            + KEY_QUANTITY + " INTEGER, "
            + " PRIMARY KEY (" + KEY_INPUT_FK_ID + " ), "
            + " FOREIGN KEY (" + KEY_INPUT_FK_ID + ") "
            + " REFERENCES " + TABLE_tblINPUT + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE); ";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblINCOME` (3)
    //-- -------------------------------------------------------------------------------------------
    private static final String CREATE_TABLE_tblINCOME = "CREATE TABLE " + TABLE_tblINCOME + "("
            + KEY_INPUT_FK_ID + " INTEGER NOT NULL, "
            + " PRIMARY KEY (" + KEY_INPUT_FK_ID + " ), "
            + " FOREIGN KEY (" + KEY_INPUT_FK_ID + ") "
            + " REFERENCES " + TABLE_tblINPUT + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE); ";
    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblEXPENSE` (4)
    //-- -------------------------------------------------------------------------------------------
    private static final String CREATE_TABLE_tblEXPENSE = "CREATE TABLE " + TABLE_tblEXPENSE + "("
            + KEY_INPUT_FK_ID + " INTEGER NOT NULL, "
            + KEY_EXPENSE + " DOUBLE, "
            + " PRIMARY KEY (" + KEY_INPUT_FK_ID + " ), "
            + " FOREIGN KEY (" + KEY_INPUT_FK_ID + ") "
            + " REFERENCES " + TABLE_tblINPUT + " (" + KEY_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE); ";

    //-- -------------------------------------------------------------------------------------------
    //-- Table `tblHUMANSET` (5)
    //-- -------------------------------------------------------------------------------------------
    private static final String CREATE_TABLE_tblHUMANSET = "CREATE TABLE " +
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
            + KEY_FUNDER_FK_ID + " INTEGER NOT NULL, "
            + KEY_INPUT_FK_ID + " INTEGER NOT NULL, "
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
            + " FOREIGN KEY (" + KEY_FUNDER_FK_ID + ") "
            + " REFERENCES " + TABLE_tblFUNDER + " (" + KEY_ORGANIZATION_FK_ID + ") "
            + " ON DELETE CASCADE ON UPDATE CASCADE, "
            + " FOREIGN KEY (" + KEY_INPUT_FK_ID + ") "
            + " REFERENCES " + TABLE_tblINCOME + " (" + KEY_INPUT_FK_ID + ") "
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
    private static final String CREATE_TABLE_tblPRECEDINGTASK = "CREATE TABLE " +
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
    private static final String CREATE_TABLE_tblTASK_MILESTONE = "CREATE TABLE " +
            TABLE_tblTASK_MILESTONE + "("
            + KEY_TASK_FK_ID + " INTEGER NOT NULL, "
            + KEY_MILESTONE_FK_ID + " INTEGER NOT NULL, "
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
    private static final String CREATE_TABLE_tblTASKASSIGNMENT = "CREATE TABLE " +
            TABLE_tblTASKASSIGNMENT + "("
            + KEY_ID + " INTEGER NOT NULL, "
            + KEY_STAFF_FK_ID + " INTEGER NOT NULL, "
            + KEY_TASK_FK_ID + " INTEGER NOT NULL, "
            + KEY_SERVER_ID + " INTEGER DEFAULT NULL, "
            + KEY_OWNER_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_ORG_ID + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_GROUP_BITS + " INTEGER NOT NULL DEFAULT 1, "
            + KEY_PERMS_BITS + " INTEGER NOT NULL DEFAULT 4729, "
            + KEY_STATUS_BITS + " INTEGER NOT NULL DEFAULT 0, "
            + KEY_HOURS + " INTEGER, "
            + KEY_RATE + " DOUBLE, "
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
    private static final String CREATE_TABLE_tblUSERCOMMENT = "CREATE TABLE " +
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
    private static final String CREATE_TABLE_tblTIMESHEET = "CREATE TABLE " +
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
    private static final String CREATE_TABLE_tblINVOICE = "CREATE TABLE " + TABLE_tblINVOICE + "("
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
    private static final String CREATE_TABLE_tblINVOICE_TIMESHEET = "CREATE TABLE " +
            TABLE_tblINVOICE_TIMESHEET + "("
            + KEY_INVOICE_FK_ID + " INTEGER NOT NULL, "
            + KEY_TIMESHEET_FK_ID + " INTEGER NOT NULL, "
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
    private static final String CREATE_TABLE_tblEXTENSION = "CREATE TABLE " +
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
    private static final String CREATE_TABLE_tblEXT_DOC_TYPE = "CREATE TABLE " +
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
    private static final String CREATE_TABLE_tblDOCUMENT = "CREATE TABLE " +
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
    private static final String CREATE_TABLE_tblTRANSACTION = "CREATE TABLE " +
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
    private static final String CREATE_TABLE_tblINTERNAL = "CREATE TABLE " +
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
    private static final String CREATE_TABLE_tblEXTERNAL = "CREATE TABLE " +
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
    private static final String CREATE_TABLE_tblJOURNAL = "CREATE TABLE " +
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
            + KEY_DESCRIPTION + " TEXT, "
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


    public cSQLDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    private cSQLDBHelper(Context context, int dbVersion, String dbName) {
        super(context, dbName, null, dbVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        /* drop all tables */
        dropTables(db);

        db.execSQL("DROP TABLE IF EXISTS " + "tblWORKPLAN");
        db.execSQL("DROP TABLE IF EXISTS " + "tblRESOURCE");
        db.execSQL("DROP TABLE IF EXISTS " + "tblEVALUATIONCRITERIA");
        db.execSQL("DROP TABLE IF EXISTS " + "tblIMPACT_QUESTION");
        db.execSQL("DROP TABLE IF EXISTS " + "tblOUTCOME_QUESTION");
        db.execSQL("DROP TABLE IF EXISTS " + "tblOUTPUT_QUESTION");
        db.execSQL("DROP TABLE IF EXISTS " + "tblACTIVITY_QUESTION");
        db.execSQL("DROP TABLE IF EXISTS " + "tblINPUT_QUESTION");
        db.execSQL("DROP TABLE IF EXISTS " + "tblIMPACT_RAID");
        db.execSQL("DROP TABLE IF EXISTS " + "tblOUTCOME_RAID");
        db.execSQL("DROP TABLE IF EXISTS " + "tblOUTPUT_RAID");
        db.execSQL("DROP TABLE IF EXISTS " + "tblACTIVITY_RAID");

        /* create all tables */
        createTables(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        /* create new tables */
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

    private void createTables(SQLiteDatabase db) {

        // 1. global tables
        String[] create_global_tables = new String[]{
                CREATE_TABLE_tblFREQUENCY, CREATE_TABLE_tblPERIOD,
                CREATE_TABLE_tblFISCALYEAR, CREATE_TABLE_tblCHART
        };

        // 2. user access control tables
        String[] create_brbac_tables = new String[]{
                CREATE_TABLE_tblADDRESS, CREATE_TABLE_tblORGANIZATION,
                CREATE_TABLE_tblFUNDER, CREATE_TABLE_tblBENEFICIARY,
                CREATE_TABLE_tblIMPLEMENTINGAGENCY, CREATE_TABLE_tblCROWDFUND,
                CREATE_TABLE_tblVALUE, CREATE_TABLE_tblUSER,
                CREATE_TABLE_tblSESSION, CREATE_TABLE_tblROLE,
                CREATE_TABLE_tblMENU, CREATE_TABLE_tblPRIVILEGE,
                CREATE_TABLE_tblENTITY, CREATE_TABLE_tblOPERATION,
                CREATE_TABLE_tblSTATUS, CREATE_TABLE_tblSTATUSSET,
                CREATE_TABLE_tblORG_ADDRESS, CREATE_TABLE_tblUSER_ADDRESS,
                CREATE_TABLE_tblUSER_ROLE, CREATE_TABLE_tblSESSION_ROLE,
                CREATE_TABLE_tblMENU_ROLE, CREATE_TABLE_tblPERMISSION,
                CREATE_TABLE_tblSTATUSSET_STATUS, CREATE_TABLE_tblSETTING,
                CREATE_TABLE_tblNOTIFICATION, CREATE_TABLE_tblSUBSCRIBER,
                CREATE_TABLE_tblPUBLISHER, CREATE_TABLE_tblNOTIFY_SETTING,
                CREATE_TABLE_tblACTIVITYLOG
        };

        /* 3. logframe tables */
        String[] create_logframe_tables = new String[]{
                CREATE_TABLE_tblLOGFRAME, CREATE_TABLE_tblLOGFRAMETREE,
                CREATE_TABLE_tblCOMPONENT, CREATE_TABLE_tblIMPACT,
                CREATE_TABLE_tblOUTCOME, CREATE_TABLE_tblOUTPUT,
                CREATE_TABLE_tblACTIVITY, CREATE_TABLE_tblINPUT,
                CREATE_TABLE_tblPRECEDINGACTIVITY, CREATE_TABLE_tblACTIVITYASSIGNMENT,
                CREATE_TABLE_tblRESOURCETYPE, CREATE_TABLE_tblOUTCOME_IMPACT,
                CREATE_TABLE_tblOUTPUT_OUTCOME, CREATE_TABLE_tblACTIVITY_OUTPUT,
                CREATE_TABLE_tblINPUT_ACTIVITY, CREATE_TABLE_tblCRITERIA,
                CREATE_TABLE_tblQUESTIONGROUPING, CREATE_TABLE_tblQUESTIONTYPE,
                CREATE_TABLE_tblQUESTION, CREATE_TABLE_tblPRIMITIVEQUESTION,
                CREATE_TABLE_tblARRAYQUESTION, CREATE_TABLE_tblMATRIXQUESTION,
                CREATE_TABLE_tblQUESTION_CRITERIA, CREATE_TABLE_tblRAIDCATEGORY,
                CREATE_TABLE_tblRAID, CREATE_TABLE_tblCOMPONENT_RAID
//                CREATE_TABLE_tblRESOURCE, CREATE_TABLE_tblIMPACT_QUESTION,
//                CREATE_TABLE_tblOUTCOME_QUESTION, CREATE_TABLE_tblOUTPUT_QUESTION,
//                CREATE_TABLE_tblACTIVITY_QUESTION, CREATE_TABLE_tblINPUT_QUESTION,
//                CREATE_TABLE_tblIMPACT_RAID, CREATE_TABLE_tblOUTCOME_RAID,
//                CREATE_TABLE_tblOUTPUT_RAID, CREATE_TABLE_tblACTIVITY_RAID
        };

        /* 4. evaluation tables */
        String[] create_evaluation_tables = new String[]{
                CREATE_TABLE_tblARRAYCHOICE, CREATE_TABLE_tblROWCHOICE,
                CREATE_TABLE_tblCOLCHOICE, CREATE_TABLE_tblARRAYSET,
                CREATE_TABLE_tblMATRIXSET, CREATE_TABLE_tblARRAYCHOICESET,
                CREATE_TABLE_tblMATRIXCHOICESET, CREATE_TABLE_tblEVALUATIONTYPE,
                CREATE_TABLE_tblEVALUATION, CREATE_TABLE_tblQUESTION_EVALUATION,
                CREATE_TABLE_tblCONDITIONAL_ORDER, CREATE_TABLE_tblUSER_EVALUATION,
                CREATE_TABLE_tblEVALUATION_RESPONSE, CREATE_TABLE_tblNUMERICRESPONSE,
                CREATE_TABLE_tblTEXTRESPONSE, CREATE_TABLE_tblDATERESPONSE,
                CREATE_TABLE_tblARRAYRESPONSE, CREATE_TABLE_tblMATRIXRESPONSE,
                CREATE_TABLE_tblPRIMITIVECHART, CREATE_TABLE_tblARRAYCHART,
                CREATE_TABLE_tblMATRIXCHART
        };

        /* 5. monitoring tables */
        String[] create_monitoring_tables = new String[]{
                CREATE_TABLE_tblQUESTION_INDICATOR, CREATE_TABLE_tblMETHOD, CREATE_TABLE_tblMOV,
                CREATE_TABLE_tblDATASOURCE, CREATE_TABLE_tblINDICATORTYPE,
                CREATE_TABLE_tblQUANTITATIVETYPE, CREATE_TABLE_tblCRITERIASCORE,
                CREATE_TABLE_tblQUALITATIVECRITERIA, CREATE_TABLE_tblQUALITATIVESET,
                CREATE_TABLE_tblQUALITATIVESCORESET, CREATE_TABLE_tblARRAYINDICATOR,
                CREATE_TABLE_tblMATRIXINDICATOR, CREATE_TABLE_tblQUALITATIVEINDICATOR,
                CREATE_TABLE_tblQUANTITATIVEINDICATOR, CREATE_TABLE_tblTARGET,
                CREATE_TABLE_tblQUALITATIVETARGET, CREATE_TABLE_tblQUANTITATIVETARGET,
                CREATE_TABLE_tblARRAYTARGET, CREATE_TABLE_tblMATRIXTARGET,
                CREATE_TABLE_tblDATACOLLECTOR, CREATE_TABLE_tblINDICATOR,
                CREATE_TABLE_tblINDICATORMILESTONE, CREATE_TABLE_tblQUALITATIVEMILESTONE,
                CREATE_TABLE_tblQUANTITATIVEMILESTONE, CREATE_TABLE_tblARRAYMILESTONE,
                CREATE_TABLE_tblMATRIXMILESTONE, CREATE_TABLE_tblINDICATORPROGRESS,
                CREATE_TABLE_tblQUALITATIVEPROGRESS, CREATE_TABLE_tblQUANTITATIVEPROGRESS,
                CREATE_TABLE_tblARRAYPROGRESS, CREATE_TABLE_tblMATRIXPROGRESS,
                CREATE_TABLE_tblINDICATOR_MOV, CREATE_TABLE_tblINDICATOR_DATASOURCE
        };

        /* 6. risk, assumption, issue and dependency (raid) tables */
        String[] create_raid_tables = new String[]{
                CREATE_TABLE_tblPIM, CREATE_TABLE_tblRAIDLIKELIHOOD,
                CREATE_TABLE_tblRAIDIMPACT, CREATE_TABLE_tblROBOT,
                CREATE_TABLE_tblPIMSET, CREATE_TABLE_tblRAIDREGISTER,
                CREATE_TABLE_tblRISKREGISTER, CREATE_TABLE_tblASSUMPTIONREGISTER,
                CREATE_TABLE_tblISSUEREGISTER, CREATE_TABLE_tblDEPENDENCYREGISTER,
                CREATE_TABLE_tblRAIDLOG, CREATE_TABLE_tblRISK,
                CREATE_TABLE_tblRISKROOTCAUSE, CREATE_TABLE_tblRISKCONSEQUENCE,
                CREATE_TABLE_tblASSUMPTION, CREATE_TABLE_tblISSUE,
                CREATE_TABLE_tblDEPENDENCY, CREATE_TABLE_tblMILESTONEREVIEW,
                CREATE_TABLE_tblREVIEWCOMMENT, CREATE_TABLE_tblRISKREVIEW,
                CREATE_TABLE_tblASSUMPTIONREVIEW, CREATE_TABLE_tblISSUEREVIEW,
                CREATE_TABLE_tblDEPENDENCYREVIEW, CREATE_TABLE_tblACTION,
                CREATE_TABLE_tblACTIONTASK, CREATE_TABLE_tblMILESTONE_ACTION,
                CREATE_TABLE_tblRISKACTIONTYPE, CREATE_TABLE_tblRISKACTION,
                CREATE_TABLE_tblCURRENTCONTROL, CREATE_TABLE_tblADDITIONALCONTROL,
                CREATE_TABLE_tblASSUMPTIONACTION, CREATE_TABLE_tblISSUEACTION,
                CREATE_TABLE_tblDEPENDENCYACTION
        };

        /* 7. annual work plan and budget (awpb) tables */
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
            for (String create_evaluation_table : create_evaluation_tables) {
                db.execSQL(create_evaluation_table);
            }
            for (String create_monitoring_table : create_monitoring_tables) {
                db.execSQL(create_monitoring_table);
            }
            for (String create_raid_table : create_raid_tables) {
                db.execSQL(create_raid_table);
            }
            for (String create_awpb_table : create_awpb_tables) {
                db.execSQL(create_awpb_table);
            }

            db.setTransactionSuccessful();
        } catch (SQLException e) {
            Log.e("Error onCreate Tables", e.toString());
            throw e;
        } finally {
            db.endTransaction();
        }
    }

    private void dropTables(SQLiteDatabase db) {

        /* drop global tables */
        String[] global_tables = new String[]{
                TABLE_tblFREQUENCY, TABLE_tblPERIOD, TABLE_tblFISCALYEAR, TABLE_tblCHART
        };

        /* drop bitwise role based access control tables */
        String[] brbac_tables = new String[]{
                TABLE_tblADDRESS, TABLE_tblORGANIZATION, TABLE_tblFUNDER,
                TABLE_tblBENEFICIARY, TABLE_tblIMPLEMENTINGAGENCY, TABLE_tblCROWDFUND,
                TABLE_tblVALUE, TABLE_tblUSER, TABLE_tblSESSION, TABLE_tblROLE,
                TABLE_tblMENU, TABLE_tblPRIVILEGE, TABLE_tblENTITY,
                TABLE_tblOPERATION, TABLE_tblSTATUS, TABLE_tblSTATUSSET,
                TABLE_tblORG_ADDRESS, TABLE_tblUSER_ADDRESS, TABLE_tblUSER_ROLE,
                TABLE_tblSESSION_ROLE, TABLE_tblMENU_ROLE, TABLE_tblPERMISSION,
                TABLE_tblSTATUSSET_STATUS, TABLE_tblSETTING, TABLE_tblNOTIFICATION,
                TABLE_tblSUBSCRIBER, TABLE_tblPUBLISHER, TABLE_tblNOTIFY_SETTING,
                TABLE_tblACTIVITYLOG
        };

        /* drop logframe tables */
        String[] logframe_tables = new String[]{
                TABLE_tblLOGFRAME, TABLE_tblLOGFRAMETREE, TABLE_tblCOMPONENT, TABLE_tblIMPACT,
                TABLE_tblOUTCOME, TABLE_tblOUTPUT, TABLE_tblACTIVITY, TABLE_tblINPUT,
                TABLE_tblPRECEDINGACTIVITY, TABLE_tblACTIVITYASSIGNMENT, TABLE_tblRESOURCETYPE,
                TABLE_tblOUTCOME_IMPACT, TABLE_tblOUTPUT_OUTCOME, TABLE_tblACTIVITY_OUTPUT,
                TABLE_tblINPUT_ACTIVITY, TABLE_tblCRITERIA, TABLE_tblQUESTIONGROUPING,
                TABLE_tblQUESTIONTYPE, TABLE_tblQUESTION, TABLE_tblPRIMITIVEQUESTION,
                TABLE_tblARRAYQUESTION, TABLE_tblMATRIXQUESTION, TABLE_tblQUESTION_CRITERIA,
                TABLE_tblRAIDCATEGORY, TABLE_tblRAID, TABLE_tblCOMPONENT_RAID
        };

        /* drop evaluation tables */
        String[] evaluation_tables = new String[]{
                TABLE_tblARRAYCHOICE, TABLE_tblROWCHOICE, TABLE_tblCOLCHOICE, TABLE_tblARRAYSET,
                TABLE_tblMATRIXSET, TABLE_tblARRAYCHOICESET, TABLE_tblMATRIXCHOICESET,
                TABLE_tblEVALUATIONTYPE, TABLE_tblEVALUATION, TABLE_tblQUESTION_EVALUATION,
                TABLE_tblCONDITIONAL_ORDER, TABLE_tblUSER_EVALUATION, TABLE_tblEVALUATION_RESPONSE,
                TABLE_tblNUMERICRESPONSE, TABLE_tblTEXTRESPONSE, TABLE_tblDATERESPONSE,
                TABLE_tblARRAYRESPONSE, TABLE_tblMATRIXRESPONSE, TABLE_tblPRIMITIVECHART,
                TABLE_tblARRAYCHART, TABLE_tblMATRIXCHART
        };

        /* drop monitoring tables */
        String[] monitoring_tables = new String[]{
                TABLE_tblQUESTION_INDICATOR, TABLE_tblMETHOD, TABLE_tblMOV, TABLE_tblDATASOURCE,
                TABLE_tblINDICATORTYPE, TABLE_tblQUANTITATIVETYPE, TABLE_tblCRITERIASCORE,
                TABLE_tblQUALITATIVECRITERIA, TABLE_tblQUALITATIVESET, TABLE_tblQUALITATIVESCORESET,
                TABLE_tblARRAYINDICATOR, TABLE_tblMATRIXINDICATOR, TABLE_tblQUALITATIVEINDICATOR,
                TABLE_tblQUANTITATIVEINDICATOR, TABLE_tblTARGET, TABLE_tblQUALITATIVETARGET,
                TABLE_tblQUANTITATIVETARGET, TABLE_tblARRAYTARGET, TABLE_tblMATRIXTARGET,
                TABLE_tblDATACOLLECTOR, TABLE_tblINDICATOR, TABLE_tblINDICATORMILESTONE,
                TABLE_tblQUALITATIVEMILESTONE, TABLE_tblQUANTITATIVEMILESTONE,
                TABLE_tblARRAYMILESTONE, TABLE_tblMATRIXMILESTONE, TABLE_tblINDICATORPROGRESS,
                TABLE_tblQUALITATIVEPROGRESS, TABLE_tblQUANTITATIVEPROGRESS, TABLE_tblARRAYPROGRESS,
                TABLE_tblMATRIXPROGRESS, TABLE_tblINDICATOR_MOV, TABLE_tblINDICATOR_DATASOURCE
        };

        /* drop raid tables */
        String[] raid_tables = new String[]{
                TABLE_tblPIM, TABLE_tblRAIDLIKELIHOOD, TABLE_tblRAIDIMPACT,
                TABLE_tblROBOT, TABLE_tblPIMSET, TABLE_tblRAIDREGISTER,
                TABLE_tblRISKREGISTER, TABLE_tblASSUMPTIONREGISTER, TABLE_tblISSUEREGISTER,
                TABLE_tblDEPENDENCYREGISTER, TABLE_tblRAIDLOG, TABLE_tblRISK,
                TABLE_tblRISKROOTCAUSE, TABLE_tblRISKCONSEQUENCE, TABLE_tblASSUMPTION,
                TABLE_tblISSUE,  TABLE_tblDEPENDENCY, TABLE_tblMILESTONEREVIEW,
                TABLE_tblREVIEWCOMMENT, TABLE_tblRISKREVIEW, TABLE_tblASSUMPTIONREVIEW,
                TABLE_tblISSUEREVIEW, TABLE_tblDEPENDENCYREVIEW, TABLE_tblACTION,
                TABLE_tblACTIONTASK, TABLE_tblMILESTONE_ACTION, TABLE_tblRISKACTIONTYPE,
                TABLE_tblRISKACTION, TABLE_tblCURRENTCONTROL, TABLE_tblADDITIONALCONTROL,
                TABLE_tblASSUMPTIONACTION, TABLE_tblISSUEACTION, TABLE_tblDEPENDENCYACTION
        };

        /* drop awpb tables */
        String[] awpb_tables = new String[]{
                TABLE_tblHUMAN, TABLE_tblMATERIAL, TABLE_tblINCOME, TABLE_tblEXPENSE,
                TABLE_tblHUMANSET, TABLE_tblFUND, TABLE_tblTASK, TABLE_tblACTIVITYTASK,
                TABLE_tblPRECEDINGTASK, TABLE_tblTASK_MILESTONE, TABLE_tblMILESTONE,
                TABLE_tblTASKASSIGNMENT, TABLE_tblUSERCOMMENT, TABLE_tblTIMESHEET,
                TABLE_tblINVOICE, TABLE_tblINVOICE_TIMESHEET, TABLE_tblEXTENSION,
                TABLE_tblEXT_DOC_TYPE, TABLE_tblDOCUMENT, TABLE_tblTRANSACTION,
                TABLE_tblINTERNAL, TABLE_tblEXTERNAL, TABLE_tblJOURNAL
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
            for (String evaluation_table : evaluation_tables) {
                db.execSQL("DROP TABLE IF EXISTS " + evaluation_table);
            }
            for (String monitoring_table : monitoring_tables) {
                db.execSQL("DROP TABLE IF EXISTS " + monitoring_table);
            }
            for (String raid_table : raid_tables) {
                db.execSQL("DROP TABLE IF EXISTS " + raid_table);
            }
            for (String awpb_table : awpb_tables) {
                db.execSQL("DROP TABLE IF EXISTS " + awpb_table);
            }
            db.setTransactionSuccessful();
        } catch (SQLException e) {
            Log.e("Error onUpgrade Tables", e.toString());
        } finally {
            db.endTransaction();
        }
    }
}