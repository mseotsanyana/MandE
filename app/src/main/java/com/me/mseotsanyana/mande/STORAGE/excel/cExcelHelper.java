package com.me.mseotsanyana.mande.STORAGE.excel;

import android.content.Context;
import android.content.res.AssetManager;

import com.me.mseotsanyana.mande.UTILITY.cConstant;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


/**
 * Created by mseotsanyana on 2017/05/23.
 */

public class cExcelHelper {
    private static SimpleDateFormat sdf = cConstant.FORMAT_DATE;
    private static String TAG = cExcelHelper.class.getSimpleName();

    private AssetManager assetManager;
    private InputStream excelGLOBAL, excelSESSION, excelLOGFRAME,excelEVALUATION,
            excelMONITORING, excelRAID, excelAWPB;

    private Workbook workbookGLOBAL, workbookSESSION, workbookLOGFRAME, workbookEVALUATION,
            workbookMONITORING, workbookRAID, workbookAWPB;

    private int rowsGLOBAL, rowsSESSION, rowsLOGFRAME, rowsEVALUATION, rowsMONITORING,
            rowsRAID, rowsAWPB;

    /*############################### START GLOBAL MODULE TABLES #################################*/

    public static final String SHEET_tblFREQUENCY          = "tblFREQUENCY";                 /* 1 */


    /*############################## START LOGFRAME MODULE TABLES ################################*/

    public static final String SHEET_tblLOGFRAME           = "tblLOGFRAME";                 /* 1  */
    public static final String SHEET_tblLOGFRAMETREE       = "tblLOGFRAMETREE";             /* 2  */
    public static final String SHEET_tblIMPACT             = "tblIMPACT";                   /* 3  */
    public static final String SHEET_tblOUTCOME            = "tblOUTCOME";                  /* 4  */
    public static final String SHEET_tblOUTPUT             = "tblOUTPUT";                   /* 5  */
    public static final String SHEET_tblACTIVITYPLANNING   = "tblACTIVITYPLANNING";         /* 6  */
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
    public static final String SHEET_tblPRECEDINGACTIVITY  = "tblPRECEDINGACTIVITY";        /* 20 */
    public static final String SHEET_tblACTIVITYASSIGNMENT = "tblACTIVITYASSIGNMENT";       /* 21 */
    public static final String SHEET_tblRESOURCETYPE       = "tblRESOURCETYPE";             /* 22 */
    public static final String SHEET_tblRESOURCE           = "tblRESOURCE";                 /* 23 */
    public static final String SHEET_tblIMPACT_QUESTION    = "tblIMPACT_QUESTION_CRITERIA"; /* 24 */
    public static final String SHEET_tblOUTCOME_QUESTION   = "tblOUTCOME_QUESTION_CRITERIA";/* 25 */
    public static final String SHEET_tblOUTPUT_QUESTION    = "tblOUTPUT_QUESTION_CRITERIA"; /* 26 */
    public static final String SHEET_tblACTIVITY_QUESTION  = "tblACTIVITY_QUESTION_CRTERIA";/* 27 */
    public static final String SHEET_tblINPUT_QUESTION     = "tblINPUT_QUESTION_CRITERIA";  /* 28 */
    public static final String SHEET_tblIMPACT_RAID        = "tblIMPACT_RAID";              /* 29 */
    public static final String SHEET_tblOUTCOME_RAID       = "tblOUTCOME_RAID";             /* 30 */
    public static final String SHEET_tblOUTPUT_RAID        = "tblOUTPUT_RAID";              /* 31 */
    public static final String SHEET_tblACTIVITY_RAID      = "tblACTIVITY_RAID";            /* 32 */

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

    public static final String SHEET_tblMILESTONE              = "tblMILESTONE";             /* 6 */

    public cExcelHelper(Context context){
        try {
            assetManager = context.getAssets();

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
