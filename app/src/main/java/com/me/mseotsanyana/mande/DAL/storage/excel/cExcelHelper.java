package com.me.mseotsanyana.mande.DAL.storage.excel;

import android.content.Context;
import android.content.res.AssetManager;

import com.me.mseotsanyana.mande.UTIL.cConstant;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;


/**
 * Created by mseotsanyana on 2017/05/23.
 */

public class cExcelHelper {
    private static SimpleDateFormat sdf = cConstant.FORMAT_DATE;
    private static String TAG = cExcelHelper.class.getSimpleName();

    private InputStream excelGLOBAL, excelSESSION, excelLOGFRAME,excelEVALUATION,
            excelMONITORING, excelRAID, excelAWPB;

    private Workbook workbookGLOBAL, workbookSESSION, workbookLOGFRAME, workbookEVALUATION,
            workbookMONITORING, workbookRAID, workbookAWPB;

    private int rowsGLOBAL, rowsSESSION, rowsLOGFRAME, rowsEVALUATION, rowsMONITORING,
            rowsRAID, rowsAWPB;

    /*############################### START SESSION MODULE TABLES ################################*/

    public static final String SHEET_tblADDRESS            = "tblADDRESS";           /* 1  */
    public static final String SHEET_tblORGANIZATION       = "tblORGANIZATION";      /* 2  */
    public static final String SHEET_tblFUNDER             = "tblFUNDER";            /* 3  */
    public static final String SHEET_tblBENEFICIARY        = "tblBENEFICIARY";       /* 4  */
    public static final String SHEET_tblIMPLEMENTINGAGENCY = "tblIMPLEMENTINGAGENCY";/* 5  */
    public static final String SHEET_tblVALUE              = "tblVALUE";             /* 6  */
    public static final String SHEET_tblUSER               = "tblUSER";              /* 7  */
    public static final String SHEET_tblSESSION            = "tblSESSION";           /* 8  */
    public static final String SHEET_tblROLE               = "tblROLE";              /* 9  */
    public static final String SHEET_tblMENU               = "tblMENU";              /* 10 */
    public static final String SHEET_tblENTITY             = "tblENTITY";            /* 12 */
    public static final String SHEET_tblOPERATION          = "tblOPERATION";         /* 13 */
    public static final String SHEET_tblSTATUS             = "tblSTATUS";            /* 14 */
    public static final String SHEET_tblSTATUSSET          = "tblSTATUSSET";         /* 11 */
    public static final String SHEET_tblORG_ADDRESS        = "tblORG_ADDRESS";       /* 15 */
    public static final String SHEET_tblUSER_ADDRESS       = "tblUSER_ADDRESS";      /* 16 */
    public static final String SHEET_tblUSER_ROLE          = "tblUSER_ROLE";         /* 17 */
    public static final String SHEET_tblSESSION_ROLE       = "tblSESSION_ROLE";      /* 18 */
    public static final String SHEET_tblMENU_ROLE          = "tblMENU_ROLE";         /* 19 */
    public static final String SHEET_tblPRIVILEGE          = "tblPRIVILEGE";         /* 20 */
    public static final String SHEET_tblPERMISSION         = "tblPERMISSION";        /* 20 */
    public static final String SHEET_tblSTATUSSET_STATUS   = "tblSTATUSSET_STATUS";  /* 21 */
    public static final String SHEET_tblSETTING            = "tblSETTING";           /* 22 */
    public static final String SHEET_tblNOTIFICATION       = "tblNOTIFICATION";      /* 23 */
    public static final String SHEET_tblSUBSCRIBER         = "tblSUBSCRIBER";        /* 24 */
    public static final String SHEET_tblPUBLISHER          = "tblPUBLISHER";         /* 25 */
    public static final String SHEET_tblNOTIFY_SETTING     = "tblNOTIFY_SETTING";    /* 26 */
    public static final String SHEET_tblACTIVITYLOG        = "tblACTIVITYLOG";       /* 27 */

    /*################################ END SESSION MODULE TABLES #################################*/

    /*############################### START GLOBAL MODULE TABLES #################################*/

    public static final String SHEET_tblFREQUENCY          = "tblFREQUENCY";                 /* 1 */
    public static final String SHEET_tblPERIOD             = "tblPERIOD";                    /* 2 */
    public static final String SHEET_tblFISCALYEAR         = "tblFISCALYEAR";                /* 3 */


    /*############################## START LOGFRAME MODULE TABLES ################################*/

    public static final String SHEET_tblLOGFRAME           = "tblLOGFRAME";                 /* 1  */
    public static final String SHEET_tblLOGFRAMETREE       = "tblLOGFRAMETREE";             /* 2  */
    public static final String SHEET_tblIMPACT             = "tblIMPACT";                   /* 3  */
    public static final String SHEET_tblOUTCOME            = "tblOUTCOME";                  /* 4  */
    public static final String SHEET_tblOUTPUT             = "tblOUTPUT";                   /* 5  */
    public static final String SHEET_tblWORKPLAN           = "tblWORKPLAN";                 /* 6  */
    public static final String SHEET_tblACTIVITY           = "tblACTIVITY";                 /* 7  */
    public static final String SHEET_tblINPUT              = "tblINPUT";                    /* 8  */
    public static final String SHEET_tblCRITERIA           = "tblCRITERIA";                 /* 9  */
    public static final String SHEET_tblQUESTIONGROUPING   = "tblQUESTIONGROUPING";         /* 10 */
    public static final String SHEET_tblQUESTIONTYPE       = "tblQUESTIONTYPE";             /* 11 */
    public static final String SHEET_tblPRIMITIVETYPE      = "tblPRIMITIVETYPE";            /* 12 */
    public static final String SHEET_tblARRAYTYPE          = "tblARRAYTYPE";                /* 13 */
    public static final String SHEET_tblMATRIXTYPE         = "tblMATRIXTYPE";               /* 14 */
    public static final String SHEET_tblQUESTION           = "tblQUESTION";                 /* 15 */
    public static final String SHEET_tblRAID               = "tblRAID";                     /* 16 */
    public static final String SHEET_tblOUTCOME_IMPACT     = "tblOUTCOME_IMPACT";           /* 17 */
    public static final String SHEET_tblOUTPUT_OUTCOME     = "tblOUTPUT_OUTCOME";           /* 18 */
    public static final String SHEET_tblACTIVITY_OUTPUT    = "tblACTIVITY_OUTPUT";          /* 19 */
    public static final String SHEET_tblINPUT_ACTIVITY     = "tblINPUT_ACTIVITY";           /* 20 */
    public static final String SHEET_tblPRECEDINGACTIVITY  = "tblPRECEDINGACTIVITY";        /* 21 */
    public static final String SHEET_tblACTIVITYASSIGNMENT = "tblACTIVITYASSIGNMENT";       /* 22 */
    public static final String SHEET_tblRESOURCETYPE       = "tblRESOURCETYPE";             /* 23 */
    public static final String SHEET_tblRESOURCE           = "tblRESOURCE";                 /* 24 */
    public static final String SHEET_tblIMPACT_QUESTION    = "tblIMPACT_QUESTION_CRITERIA"; /* 25 */
    public static final String SHEET_tblOUTCOME_QUESTION   = "tblOUTCOME_QUESTION_CRITERIA";/* 26 */
    public static final String SHEET_tblOUTPUT_QUESTION    = "tblOUTPUT_QUESTION_CRITERIA"; /* 27 */
    public static final String SHEET_tblACTIVITY_QUESTION  = "tblACTIVITY_QUESTION_CRITERIA";/* 28 */
    public static final String SHEET_tblINPUT_QUESTION     = "tblINPUT_QUESTION_CRITERIA";  /* 29 */
    public static final String SHEET_tblIMPACT_RAID        = "tblIMPACT_RAID";              /* 30 */
    public static final String SHEET_tblOUTCOME_RAID       = "tblOUTCOME_RAID";             /* 31 */
    public static final String SHEET_tblOUTPUT_RAID        = "tblOUTPUT_RAID";              /* 32 */
    public static final String SHEET_tblACTIVITY_RAID      = "tblACTIVITY_RAID";            /* 33 */

    /*############################## START EVALUATION MODULE TABLES ##############################*/

    public static final String SHEET_tblARRAYCHOICE            = "tblARRAYCHOICE";           /* 1 */
    public static final String SHEET_tblARRAYCHOICESET         = "tblARRAYCHOICESET";        /* 2 */
    public static final String SHEET_tblROWOPTION              = "tblROWOPTION";             /* 3 */
    public static final String SHEET_tblCOLOPTION              = "tblCOLOPTION";             /* 4 */
    public static final String SHEET_tblMATRIXCHOICE           = "tblMATRIXCHOICE";          /* 5 */
    public static final String SHEET_tblMATRIXCHOICESET        = "tblMATRIXCHOICESET";       /* 6 */
    public static final String SHEET_tblEVALUATIONTYPE         = "tblEVALUATIONTYPE";        /* 7 */
    public static final String SHEET_tblQUESTIONNAIRE          = "tblQUESTIONNAIRE";         /* 8 */
    public static final String SHEET_tblQUESTIONNAIRE_QUESTION = "tblQUESTIONNAIRE_QUESTION";/* 9 */
    public static final String SHEET_tblCONDITIONAL_ORDER      = "tblCONDITIONAL_ORDER";     /* 10*/
    public static final String SHEET_tblQUESTIONNAIRE_USER     = "tblQUESTIONNAIRE_USER";    /* 11*/
    public static final String SHEET_tblERESPONSE              = "tblERESPONSE";             /* 12*/
    public static final String SHEET_tblNUMERICRESPONSE        = "tblNUMERICRESPONSE";       /* 13*/
    public static final String SHEET_tblTEXTRESPONSE           = "tblTEXTRESPONSE";          /* 14*/
    public static final String SHEET_tblDATERESPONSE           = "tblDATERESPONSE";          /* 15*/
    public static final String SHEET_tblARRAYRESPONSE          = "tblARRAYRESPONSE";         /* 16*/
    public static final String SHEET_tblMATRIXRESPONSE         = "tblMATRIXRESPONSE";        /* 17*/

    /*############################## START MONITORING MODULE TABLES ##############################*/

    public static final String SHEET_tblMOV                    = "tblMOV";                   /* 1 */
    public static final String SHEET_tblMETHOD                 = "tblMETHOD";                /* 2 */
    public static final String SHEET_tblUNIT                   = "tblUNIT";                  /* 3 */
    public static final String SHEET_tblINDICATORTYPE          = "tblINDICATORTYPE";         /* 4 */
    public static final String SHEET_tblQUALITATIVECHOICE      = "tblQUALITATIVECHOICE";     /* 5 */
    public static final String SHEET_tblDATACOLLECTOR          = "tblDATACOLLECTOR";         /* 6 */
    public static final String SHEET_tblQUANTITATIVE           = "tblQUANTITATIVE";          /* 7 */
    public static final String SHEET_tblQUALITATIVE            = "tblQUALITATIVE";           /* 8 */
    public static final String SHEET_tblQUALITATIVECHOICESET   = "tblQUALITATIVECHOICESET";  /* 9 */
    public static final String SHEET_tblINDICATOR              = "tblINDICATOR";             /* 10*/
    public static final String SHEET_tblMRESPONSE              = "tblMRESPONSE";             /* 11*/
    public static final String SHEET_tblQUANTITATIVERESPONSE   = "tblQUANTITATIVERESPONSE";  /* 12*/
    public static final String SHEET_tblQUALITATIVERESPONSE    = "tblQUALITATIVERESPONSE";   /* 13*/

    /*################################ START AWPB MODULE TABLES ##################################*/

    public static final String SHEET_tblHUMAN                  = "tblHUMAN";                 /* 1 */
    public static final String SHEET_tblHUMANSET               = "tblHUMANSET";              /* 2 */
    public static final String SHEET_tblMATERIAL               = "tblMATERIAL";              /* 3 */
    public static final String SHEET_tblEXPENSE                = "tblEXPENSE";               /* 4 */
    public static final String SHEET_tblINCOME                 = "tblINCOME";                /* 5 */
    public static final String SHEET_tblFUND                   = "tblFUND";                  /* 6 */
    public static final String SHEET_tblTASK                   = "tblTASK";                  /* 7 */
    public static final String SHEET_tblPRECEDINGTASK          = "tblPRECEDINGTASK";         /* 8 */
    public static final String SHEET_tblTASK_MILESTONE         = "tblTASK_MILESTONE";        /* 9 */
    public static final String SHEET_tblACTIVITYTASK           = "tblACTIVITYTASK";          /* 10*/
    public static final String SHEET_tblTASKASSIGNMENT         = "tblTASKASSIGNMENT";        /* 11*/
    public static final String SHEET_tblUSERCOMMENT            = "tblUSERCOMMENT";           /* 12*/
    public static final String SHEET_tblTIMESHEET              = "tblTIMESHEET";             /* 13*/
    public static final String SHEET_tblINVOICE                = "tblINVOICE";               /* 14*/
    public static final String SHEET_tblINVOICE_TIMESHEET      = "tblINVOICE_TIMESHEET";     /* 15*/
    public static final String SHEET_tblDOCUMENT               = "tblDOCUMENT";              /* 16*/
    public static final String SHEET_tblTRANSACTION            = "tblTRANSACTION";           /* 17*/
    public static final String SHEET_tblINTERNAL               = "tblINTERNAL";              /* 18*/
    public static final String SHEET_tblEXTERNAL               = "tblEXTERNAL";              /* 19*/
    public static final String SHEET_tblJOURNAL                = "tblJOURNAL";               /* 20*/

    /*################################ START RAID MODULE TABLES ##################################*/

    public static final String SHEET_tblRISKREGISTER           = "tblRISKREGISTER";         /* 1  */
    public static final String SHEET_tblRISKLIKELIHOOD         = "tblRISKLIKELIHOOD";       /* 2  */
    public static final String SHEET_tblRISKLIKELIHOODSET      = "tblRISKLIKELIHOODSET";    /* 3  */
    public static final String SHEET_tblRISKIMPACT             = "tblRISKIMPACT";           /* 4  */
    public static final String SHEET_tblRISKIMPACTSET          = "tblRISKIMPACTSET";        /* 5  */
    public static final String SHEET_tblRISKCRITERIA           = "tblRISKCRITERIA";         /* 6  */
    public static final String SHEET_tblRISKCRITERIASET        = "tblRISKCRITERIASET";      /* 7  */
    public static final String SHEET_tblRISK                   = "tblRISK";                 /* 8  */
    public static final String SHEET_tblRISKROOTCAUSE          = "tblRISKROOTCAUSE";        /* 9  */
    public static final String SHEET_tblRISKCONSEQUENCE        = "tblRISKCONSEQUENCE";      /* 10 */
    public static final String SHEET_tblCURRENTCONTROL         = "tblCURRENTCONTROL";       /* 11 */
    public static final String SHEET_tblADDITIONALCONTROL      = "tblADDITIONALCONTROL";    /* 12 */
    public static final String SHEET_tblRISKMILESTONE          = "tblRISKMILESTONE";        /* 13 */
    public static final String SHEET_tblRISKANALYSIS           = "tblRISKANALYSIS";         /* 14 */
    public static final String SHEET_tblRISKPLAN               = "tblRISKPLAN";             /* 15 */
    public static final String SHEET_tblRISKACTIONTYPE         = "tblRISKACTIONTYPE";       /* 16 */
    public static final String SHEET_tblRISKACTION             = "tblRISKACTION";           /* 17 */

    /*################################# END RAID MODULE TABLES ###################################*/

    public cExcelHelper(Context context){
        try {
            AssetManager assetManager = context.getAssets();

            setExcelGLOBAL(assetManager.open("GLOBAL.xlsx"));
            setExcelSESSION(assetManager.open("SESSION.xlsx"));
            setExcelLOGFRAME(assetManager.open("LOGFRAME.xlsx"));
            setExcelEVALUATION(assetManager.open("EVALUATION.xlsx"));
            setExcelMONITORING(assetManager.open("MONITORING.xlsx"));
            setExcelRAID(assetManager.open("RAID.xlsx"));
            setExcelAWPB(assetManager.open("AWPB.xlsx"));

            setWorkbookGLOBAL(new XSSFWorkbook(getExcelGLOBAL()));
            setRowsGLOBAL(computeAllRows(getWorkbookGLOBAL()));

            setWorkbookSESSION(new XSSFWorkbook(getExcelSESSION()));
            setRowsSESSION(computeAllRows(getWorkbookSESSION()));

            setWorkbookLOGFRAME(new XSSFWorkbook(getExcelLOGFRAME()));
            setRowsLOGFRAME(computeAllRows(getWorkbookLOGFRAME()));

            setWorkbookEVALUATION(new XSSFWorkbook(getExcelEVALUATION()));
            setRowsEVALUATION(computeAllRows(getWorkbookEVALUATION()));

            setWorkbookMONITORING(new XSSFWorkbook(getExcelMONITORING()));
            setRowsMONITORING(computeAllRows(getWorkbookMONITORING()));

            setWorkbookRAID(new XSSFWorkbook(getExcelRAID()));
            setRowsRAID(computeAllRows(getWorkbookRAID()));

            setWorkbookAWPB(new XSSFWorkbook(getExcelAWPB()));
            setRowsAWPB(computeAllRows(getWorkbookAWPB()));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int computeAllRows(Workbook workbook) {
        int allRows = 0;

        int numSheets = workbook.getNumberOfSheets();

        for (int i = 0; i < numSheets; i++) {
            Sheet sheet = workbook.getSheet(workbook.getSheetName(i));
            // number of rows (for all tables used for checking progress
            allRows = allRows + (sheet.getPhysicalNumberOfRows() - 1);
        }
        return allRows;
    }

    public InputStream getExcelGLOBAL() {
        return excelGLOBAL;
    }

    public void setExcelGLOBAL(InputStream excelGLOBAL) {
        this.excelGLOBAL = excelGLOBAL;
    }

    public InputStream getExcelSESSION() {
        return excelSESSION;
    }

    public void setExcelSESSION(InputStream excelSESSION) {
        this.excelSESSION = excelSESSION;
    }

    public InputStream getExcelLOGFRAME() {
        return excelLOGFRAME;
    }

    public void setExcelLOGFRAME(InputStream excelLOGFRAME) {
        this.excelLOGFRAME = excelLOGFRAME;
    }

    public InputStream getExcelEVALUATION() {
        return excelEVALUATION;
    }

    public void setExcelEVALUATION(InputStream excelEVALUATION) {
        this.excelEVALUATION = excelEVALUATION;
    }

    public InputStream getExcelMONITORING() {
        return excelMONITORING;
    }

    public void setExcelMONITORING(InputStream excelMONITORING) {
        this.excelMONITORING = excelMONITORING;
    }

    public InputStream getExcelRAID() {
        return excelRAID;
    }

    public void setExcelRAID(InputStream excelRAID) {
        this.excelRAID = excelRAID;
    }

    public InputStream getExcelAWPB() {
        return excelAWPB;
    }

    public void setExcelAWPB(InputStream excelAWPB) {
        this.excelAWPB = excelAWPB;
    }

    public Workbook getWorkbookGLOBAL() {
        return workbookGLOBAL;
    }

    public void setWorkbookGLOBAL(Workbook workbookGLOBAL) {
        this.workbookGLOBAL = workbookGLOBAL;
    }

    public Workbook getWorkbookSESSION() {
        return workbookSESSION;
    }

    public void setWorkbookSESSION(Workbook workbookSESSION) {
        this.workbookSESSION = workbookSESSION;
    }

    public Workbook getWorkbookLOGFRAME() {
        return workbookLOGFRAME;
    }

    public void setWorkbookLOGFRAME(Workbook workbookLOGFRAME) {
        this.workbookLOGFRAME = workbookLOGFRAME;
    }

    public Workbook getWorkbookEVALUATION() {
        return workbookEVALUATION;
    }

    public void setWorkbookEVALUATION(Workbook workbookEVALUATION) {
        this.workbookEVALUATION = workbookEVALUATION;
    }

    public Workbook getWorkbookMONITORING() {
        return workbookMONITORING;
    }

    public void setWorkbookMONITORING(Workbook workbookMONITORING) {
        this.workbookMONITORING = workbookMONITORING;
    }

    public Workbook getWorkbookRAID() {
        return workbookRAID;
    }

    public void setWorkbookRAID(Workbook workbookRAID) {
        this.workbookRAID = workbookRAID;
    }

    public Workbook getWorkbookAWPB() {
        return workbookAWPB;
    }

    public void setWorkbookAWPB(Workbook workbookAWPB) {
        this.workbookAWPB = workbookAWPB;
    }

    public int getRowsGLOBAL() {
        return rowsGLOBAL;
    }

    public void setRowsGLOBAL(int rowsGLOBAL) {
        this.rowsGLOBAL = rowsGLOBAL;
    }

    public int getRowsSESSION() {
        return rowsSESSION;
    }

    public void setRowsSESSION(int rowsSESSION) {
        this.rowsSESSION = rowsSESSION;
    }

    public int getRowsLOGFRAME() {
        return rowsLOGFRAME;
    }

    public void setRowsLOGFRAME(int rowsLOGFRAME) {
        this.rowsLOGFRAME = rowsLOGFRAME;
    }

    public int getRowsEVALUATION() {
        return rowsEVALUATION;
    }

    public void setRowsEVALUATION(int rowsEVALUATION) {
        this.rowsEVALUATION = rowsEVALUATION;
    }

    public int getRowsMONITORING() {
        return rowsMONITORING;
    }

    public void setRowsMONITORING(int rowsMONITORING) {
        this.rowsMONITORING = rowsMONITORING;
    }

    public int getRowsRAID() {
        return rowsRAID;
    }

    public void setRowsRAID(int rowsRAID) {
        this.rowsRAID = rowsRAID;
    }

    public int getRowsAWPB() {
        return rowsAWPB;
    }

    public void setRowsAWPB(int rowsAWPB) {
        this.rowsAWPB = rowsAWPB;
    }
}
