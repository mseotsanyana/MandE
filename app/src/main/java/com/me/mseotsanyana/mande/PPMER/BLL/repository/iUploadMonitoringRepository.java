package com.me.mseotsanyana.mande.PPMER.BLL.repository;

public interface iUploadMonitoringRepository {
    /* delete functions */
    boolean deleteMOVs();
    boolean deleteMethods();
    boolean deleteUnits();
    boolean deleteIndicatorTypes();
    boolean deleteQualitativeChoices();
    boolean deleteDataCollectors();
    boolean deleteIndicators();
    boolean deleteQuantitatives();
    boolean deleteQualitatives();
    boolean deleteQualitativeChoiceSets();
    boolean deleteMilestones();
    boolean deleteIndicatorMilestones();
    boolean deleteMresponses();
    boolean deleteQuantitativeResponses();
    boolean deleteQualitativeResponses();

    /* add functions */
    boolean addMOVFromExcel();
    boolean addMethodFromExcel();
    boolean addUnitFromExcel();
    boolean addIndicatorTypeFromExcel();
    boolean addQualitativeChoiceFromExcel();
    boolean addDataCollectorFromExcel();
    boolean addIndicatorFromExcel();
    boolean addMResponseFromExcel();
}
