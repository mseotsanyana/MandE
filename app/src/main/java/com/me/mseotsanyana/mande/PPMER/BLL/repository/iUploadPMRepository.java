package com.me.mseotsanyana.mande.PPMER.BLL.repository;

public interface iUploadPMRepository{
    /* add (create) functions */
    boolean addCriteriaFromExcel();
    boolean addChoiceFromExcel();
    boolean addQuestionGroupingFromExcel();
    //boolean addMultivaluedOptionFromExcel();
    boolean addQuestionTypeFromExcel();

    boolean addQuestionFromExcel();

    boolean addLogFrameFromExcel();
    boolean addRaidFromExcel();
    boolean addImpactFromExcel();
    boolean addOutcomeFromExcel();
    boolean addOutputFromExcel();
    boolean addActivityFromExcel();
    boolean addInputFromExcel();

    /* delete functions */
    boolean deleteActivities();
}
