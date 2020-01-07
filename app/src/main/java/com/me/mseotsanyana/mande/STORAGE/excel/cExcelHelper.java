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
    private InputStream excelFile;
    private Workbook workbook;

    private int allRows;

    /*############################## START PLANNING MODULE TABLES ################################*/

    public static final String SHEET_tblLOGFRAME           = "tblLOGFRAME";                 /* 1  */
    public static final String SHEET_tblLOGFRAMETREE       = "tblLOGFRAMETREE";             /* 2  */
    public static final String SHEET_tblIMPACT             = "tblIMPACT";                   /* 3  */
    public static final String SHEET_tblOUTCOME            = "tblOUTCOME";                  /* 4  */
    public static final String SHEET_tblOUTPUT             = "tblOUTPUT";                   /* 5  */
    public static final String SHEET_tblACTIVITY           = "tblACTIVITY";                 /* 6  */
    public static final String SHEET_tblINPUT              = "tblINPUT";                    /* 7  */
    public static final String SHEET_tblQUESTION_GROUPING  = "tblQUESTIONGROUPING";        /* 8  */
    public static final String SHEET_tblQUESTION_TYPE      = "tblQUESTIONTYPE";            /* 9  */
    public static final String SHEET_tblPRIMITIVE_TYPE     = "tblPRIMITIVETYPE";           /* 10 */
    //public static final String SHEET_tblMULTIVALUED_OPTION = "tblMULTIVALUED_OPTION";       /* 11 */
    public static final String SHEET_tblCUSTOM_TYPE        = "tblCUSTOMTYPE";              /* 12 */
    public static final String SHEET_tblQUESTION           = "tblQUESTION";                 /* 13  */
    public static final String SHEET_tblCHOICE             = "tblCHOICE";                   /* 14 */
    public static final String SHEET_tblCHOICESET          = "tblCHOICESET";                /* 15 */
    public static final String SHEET_tblCRITERIA           = "tblCRITERIA";                 /* 16 */
    public static final String SHEET_tblRAID               = "tblRAID";                     /* 17 */
    public static final String SHEET_tblOUTCOME_IMPACT     = "tblOUTCOME_IMPACT";           /* 18 */
    public static final String SHEET_tblOUTPUT_OUTCOME     = "tblOUTPUT_OUTCOME";           /* 19 */
    public static final String SHEET_tblACTIVITY_OUTPUT    = "tblACTIVITY_OUTPUT";          /* 20 */
    public static final String SHEET_tblPRECEDINGACTIVITY  = "tblPRECEDINGACTIVITY";        /* 21 */
    public static final String SHEET_tblIMPACT_QUESTION    = "tblIMPACT_QUESTION_CRITERIA"; /* 22 */
    public static final String SHEET_tblOUTCOME_QUESTION   = "tblOUTCOME_QUESTION_CRITERIA";/* 23 */
    public static final String SHEET_tblOUTPUT_QUESTION    = "tblOUTPUT_QUESTION_CRITERIA"; /* 24 */
    public static final String SHEET_tblACTIVITY_QUESTION  = "tblACTIVITY_QUESTION_CRTERIA";/* 25 */
    public static final String SHEET_tblINPUT_QUESTION     = "tblINPUT_QUESTION_CRITERIA";  /* 26 */
    public static final String SHEET_tblIMPACT_RAID        = "tblIMPACT_RAID";              /* 27 */
    public static final String SHEET_tblOUTCOME_RAID       = "tblOUTCOME_RAID";             /* 28 */
    public static final String SHEET_tblOUTPUT_RAID        = "tblOUTPUT_RAID";              /* 29 */
    public static final String SHEET_tblACTIVITY_RAID      = "tblACTIVITY_RAID";            /* 30 */

    public static final String SHEET_tblEVALUATION_QUESTION  = "tblEVALUATION_QUESTION";    /* 1  */
    public static final String SHEET_tblMONITORING_QUESTION  = "tblMONITORING_QUESTION";    /* 2  */


    public cExcelHelper(Context context){
        try {
            assetManager = context.getAssets();
            excelFile = assetManager.open("LOGFRAME.xlsx");
            setWorkbook(new XSSFWorkbook(excelFile));
            setAllRows(computeAllRows());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int computeAllRows() {
        int allRows = 0;

        int numSheets = getWorkbook().getNumberOfSheets();

        for (int i = 0; i < numSheets; i++) {
            Sheet sheet = getWorkbook().getSheet(getWorkbook().getSheetName(i));
            // number of rows (for all tables used for checking progress
            allRows = allRows + (sheet.getPhysicalNumberOfRows() - 1);
        }
        return allRows;
    }

    public int getAllRows() {
        return allRows;
    }

    public void setAllRows(int allRows) {
        this.allRows = allRows;
    }

    public Workbook getWorkbook() {
        return workbook;
    }

    public void setWorkbook(Workbook workbook) {
        this.workbook = workbook;
    }
}
