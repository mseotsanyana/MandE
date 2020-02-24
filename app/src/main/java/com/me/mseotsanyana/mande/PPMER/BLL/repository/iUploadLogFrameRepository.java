package com.me.mseotsanyana.mande.PPMER.BLL.repository;

public interface iUploadLogFrameRepository {
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
