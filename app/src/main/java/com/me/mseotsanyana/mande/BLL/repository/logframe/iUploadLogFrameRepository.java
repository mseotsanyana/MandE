package com.me.mseotsanyana.mande.BLL.repository.logframe;

public interface iUploadLogFrameRepository {
    /*boolean deleteCriteria();
    boolean deleteQuestionGrouping();
    boolean deleteQuestionType();
    boolean deleteQuestion();
    boolean deleteLogFrame();
    boolean deleteRaid();
    boolean deleteImpact();
    boolean deleteOutcome();
    boolean deleteOutput();
    boolean deleteActivityPlanning();
    boolean deleteActivity();
    boolean deleteResourceType();
    boolean deleteResource();
    boolean deleteInput();*/


    /* add (create) functions */
    boolean addCriteriaFromExcel();
    boolean addQuestionGroupingFromExcel();
    boolean addQuestionTypeFromExcel();
    boolean addQuestionFromExcel();
    boolean addLogFrameFromExcel();
    boolean addRaidFromExcel();
    boolean addImpactFromExcel();
    boolean addOutcomeFromExcel();
    boolean addOutputFromExcel();
    boolean addActivityPlanningFromExcel();
    boolean addActivityFromExcel();
    boolean addResourceTypeFromExcel();
    boolean addResourceFromExcel();
    boolean addInputFromExcel();

    /* delete functions */
    boolean deleteActivities();
}
