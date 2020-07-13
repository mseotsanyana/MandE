package com.me.mseotsanyana.mande.BLL.repository.logframe;

public interface iUploadLogFrameRepository {
    boolean deleteCriteria();

    boolean deleteQuestionGroupings();
    boolean deletePrimitiveTypes();
    boolean deleteArrayTypes();
    boolean deleteMatrixTypes();
    boolean deleteQuestionTypes();
    boolean deleteQuestions();

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

    boolean deleteWorkplans();

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
    boolean addCriteriaFromExcel();
    boolean addQuestionGroupingFromExcel();
    boolean addQuestionTypeFromExcel();
    boolean addQuestionFromExcel();
    boolean addLogFrameFromExcel();
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
