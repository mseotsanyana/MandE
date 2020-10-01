package com.me.mseotsanyana.mande.BLL.repository.monitor;

public interface iUploadMonitoringRepository {
    /* delete functions */
    boolean deleteMethods();
    boolean deleteMOVs();
    boolean deleteDataSources();
    boolean deleteQuantitativeTypes();
    boolean deleteIndicatorTypes();
    boolean deleteCriteriaScores();
    boolean deleteQualitativeCriteria();
    boolean deleteQualitativeSets();
    boolean deleteQualitativeScoreSets();

    boolean deleteTargets();
    boolean deleteQualitativeTargets();
    boolean deleteQuantitativeTargets();
    boolean deleteArrayTargets();
    boolean deleteMatrixTargets();
    boolean deleteDataCollectors();
    boolean deleteIndicators();
    boolean deleteQualitativeIndicators();
    boolean deleteQuantitativeIndicators();
    boolean deleteArrayIndicators();
    boolean deleteMatrixIndicators();
    boolean deleteMilestones();
    boolean deleteIndicatorMilestones();

    /* add functions */
    boolean addMethodFromExcel();
    boolean addMOVsFromExcel();
    boolean addDataSourceFromExcel();
    boolean addIndicatorTypeFromExcel();
    boolean addQuantitativeTypeFromExcel();
    boolean addCriteriaScoreFromExcel();
    boolean addQualitativeSetFromExcel();
    boolean addTargetFromExcel();
    boolean addIndicatorFromExcel();
    boolean addDataCollectorFromExcel();
    //boolean addMResponseFromExcel();
    //boolean deleteQuantitatives();
    //boolean deleteQualitatives();
    //boolean deleteQualitativeChoiceSets();
    //boolean deleteMresponses();
    //boolean deleteQuantitativeResponses();
    //boolean deleteQualitativeResponses();
    //boolean deleteQualitativeChoices();
}
