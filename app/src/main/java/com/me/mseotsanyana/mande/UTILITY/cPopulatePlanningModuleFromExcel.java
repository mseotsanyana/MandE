package com.me.mseotsanyana.mande.UTILITY;

import android.content.Context;


import com.me.mseotsanyana.mande.BRBAC.BLL.cSessionManager;
import com.me.mseotsanyana.mande.PPMER.DAL.cActivityDBA;
import com.me.mseotsanyana.mande.PPMER.DAL.cActivityModel;
import com.me.mseotsanyana.mande.PPMER.DAL.cImpactDBA;
import com.me.mseotsanyana.mande.PPMER.DAL.cImpactModel;
import com.me.mseotsanyana.mande.PPMER.DAL.cInputDBA;
import com.me.mseotsanyana.mande.PPMER.DAL.cInputModel;
import com.me.mseotsanyana.mande.PPMER.DAL.cLogFrameDBA;
import com.me.mseotsanyana.mande.PPMER.DAL.cLogFrameModel;
import com.me.mseotsanyana.mande.PPMER.DAL.cOutcomeDBA;
import com.me.mseotsanyana.mande.PPMER.DAL.cOutcomeModel;
import com.me.mseotsanyana.mande.PPMER.DAL.cOutputDBA;
import com.me.mseotsanyana.mande.PPMER.DAL.cOutputModel;

import org.apache.poi.ss.usermodel.Row;


/**
 * Created by mseotsanyana on 2017/05/23.
 */

public class cPopulatePlanningModuleFromExcel {
    private static final String TAG = "cModelsFromExcel";

    private cSessionManager session;

    private cLogFrameModel logFrameModel;
    private cImpactModel impactModel;
    private cOutcomeModel outcomeModel;
    private cOutputModel outputModel;
    private cActivityModel activityModel;
    private cInputModel inputModel;
    /*
    private cPrecedingActivityModel precedingActivityModel;
    private cCriteriaModel criteriaModel;
    private cQuestionGroupingModel questionGroupingModel;
    private cQuestionTypeModel questionTypeModel;
    private cChoiceSet choiceSet;
    private cQuestionModel questionModel;
    private cRaidModel raidModel;
    */


    private cLogFrameDBA logFrameDBA;
    //private cLogFrameTreeDBA logFrameTreeDBA;
    private cImpactDBA impactDBA;
    private cOutcomeDBA outcomeDBA;
    private cOutputDBA outputDBA;
    private cActivityDBA activityDBA;
    private cInputDBA inputDBA;
    /*
    private cPrecedingActivityDBA precedingActivityDBA;
    private cOutcomeImpactDBA outcomeImpactDBA;
    private cOutputOutcomeDBA outputOutcomeDBA;
    private cActivityOutputDBA activityOutputDBA;
    private cCriteriaDBA criteriaDBA;
    private cQuestionGroupingDBA questionGroupingDBA;
    private cQuestionTypeDBA questionTypeDBA;
    private cQuestionDBA questionDBA;
    private cImpactQuestionDBA impactQuestionDBA;
    private cOutcomeQuestionDBA outcomeQuestionDBA;
    private cOutputQuestionDBA outputQuestionDBA;
    private cActivityQuestionDBA activityQuestionDBA;
    private cInputQuestionDBA inputQuestionDBA;
    private cRaidDBA raidDBA;
    private cImpactRaidDBA impactRaidDBA;
    private cOutcomeRaidDBA outcomeRaidDBA;
    private cOutputRaidDBA outputRaidDBA;
    private cActivityRaidDBA activityRaidDBA;
    */
    private Context context;

    public cPopulatePlanningModuleFromExcel(Context context){
        this.context = context;
        logFrameDBA  = new cLogFrameDBA(context);
        session = new cSessionManager(context);
    }

    // store the excel row into the organization model object
    public void addAddressFromExcel(Row cRow){
        logFrameModel = new cLogFrameModel();

        logFrameModel.setID((int)cRow.getCell(0, Row.CREATE_NULL_AS_BLANK).getNumericCellValue());
        logFrameModel.setName(cRow.getCell(1, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
        logFrameModel.setDescription(cRow.getCell(2, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
        //logFrameModel.setGroupBITS(session.lo());
        //logFrameModel.setPermsBITS();
        //logFrameDBA.addLogFrameFromExcel(logFrameModel);
    }

}