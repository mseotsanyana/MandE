package com.me.mseotsanyana.mande.BLL.repository.logframe;

public interface iUploadLogFrameRepository {
    boolean deleteLogFrame();
    boolean deleteLogFrameTree();

    boolean deleteResourceTypes();

    boolean deleteComponents();
    boolean deleteImpacts();
    boolean deleteOutcomeImpacts();
    boolean deleteOutcomes();
    boolean deleteOutputOutcomes();
    boolean deleteOutputs();

    boolean deleteActivities();
    boolean deletePrecedingActivities();
    boolean deleteActivityAssignments();
    boolean deleteActivityOutputs();

    boolean deleteInputs();
    boolean deleteInputActivities();
    boolean deleteHumans();
    boolean deleteHumanSets();
    boolean deleteMaterials();
    boolean deleteIncomes();
    boolean deleteFunds();
    boolean deleteExpenses();

    boolean deleteCriteria();
    boolean deleteQuestionGroupings();
    boolean deleteQuestionTypes();
    boolean deleteQuestions();
    boolean deleteQuestionCriteria();

    boolean deletePrimitiveQuestions();
    boolean deleteArrayQuestions();
    boolean deleteMatrixQuestions();

    boolean deleteRaidCategories();
    boolean deleteRaids();
    boolean deleteComponentRaids();

    /* add (create) functions */
    boolean addLogFrameFromExcel();
    boolean addResourceTypeFromExcel();
    boolean addComponentFromExcel();

    boolean addCriteriaFromExcel();
    boolean addQuestionGroupingFromExcel();
    boolean addQuestionTypeFromExcel();
    boolean addQuestionFromExcel();

    boolean addRaidCategoryFromExcel();
    boolean addRaidFromExcel();

    /*boolean addImpactFromExcel();
    boolean addOutcomeFromExcel();
    boolean addOutputFromExcel();
    boolean addWorkplanFromExcel();
    boolean addActivityFromExcel();*/
    //boolean addResourceFromExcel();
    //boolean addInputFromExcel();
    //boolean deleteImpactQuestions();
    //boolean deleteImpactRaids();
    //boolean deleteOutcomeQuestions();
    //boolean deleteOutcomeRaids();
    //boolean deleteOutputQuestions();
    //boolean deleteOutputRaids();
    //boolean deleteWorkPlans();
    //boolean deleteActivityQuestions();
    //boolean deleteActivityRaids()
    //boolean deleteResources();
    //boolean deleteInputQuestions();
}
