package com.me.mseotsanyana.mande.BLL.repository.logframe;

public interface iUploadLogFrameRepository {
    boolean deleteEvaluationCriteria();

    boolean deleteQuestionGroupings();
    boolean deleteQuestionTypes();
    boolean deleteQuestions();

    boolean deletePrimitiveQuestions();
    boolean deleteArrayQuestions();
    boolean deleteMatrixQuestions();

    boolean deleteRaidCategories();
    boolean deleteRaids();

    boolean deleteImpactQuestions();
    boolean deleteImpactRaids();
    boolean deleteImpacts();

    boolean deleteOutcomeImpacts();
    boolean deleteOutcomeQuestions();
    boolean deleteOutcomeRaids();
    boolean deleteOutcomes();

    boolean deleteOutputOutcomes();
    boolean deleteOutputQuestions();
    boolean deleteOutputRaids();
    boolean deleteOutputs();

    boolean deleteWorkPlans();

    boolean deletePrecedingActivities();
    boolean deleteActivityAssignments();
    boolean deleteActivityOutputs();
    boolean deleteActivityQuestions();
    boolean deleteActivityRaids();
    boolean deleteActivities();

    boolean deleteResourceTypes();
    boolean deleteResources();

    boolean deleteInputQuestions();
    boolean deleteInputActivities();
    boolean deleteHumans();
    boolean deleteHumanSets();
    boolean deleteMaterials();
    boolean deleteIncomes();
    boolean deleteFunds();
    boolean deleteExpenses();
    boolean deleteInputs();

    boolean deleteLogFrameTree();
    boolean deleteLogFrame();

    /* add (create) functions */
    boolean addLogFrameFromExcel();
    boolean addEvaluationCriteriaFromExcel();
    boolean addQuestionGroupingFromExcel();
    boolean addQuestionTypeFromExcel();
    boolean addQuestionFromExcel();
    boolean addRaidCategoryFromExcel();
    boolean addRaidFromExcel();
    boolean addImpactFromExcel();
    boolean addOutcomeFromExcel();
    boolean addOutputFromExcel();
    boolean addWorkplanFromExcel();
    boolean addActivityFromExcel();
    boolean addResourceTypeFromExcel();
    boolean addResourceFromExcel();
    boolean addInputFromExcel();
}
