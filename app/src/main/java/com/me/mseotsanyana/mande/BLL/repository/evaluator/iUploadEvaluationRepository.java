package com.me.mseotsanyana.mande.BLL.repository.evaluator;

public interface iUploadEvaluationRepository {
    boolean deleteArrayChoices();
    boolean deleteArraySets();
    boolean deleteArrayChoiceSets();
    boolean deleteRowChoices();
    boolean deleteColChoices();
    boolean deleteMatrixSets();
    boolean deleteMatrixChoiceSets();

    boolean deleteEvaluationTypes();
    boolean deleteEvaluations();
    boolean deleteEvaluationQuestions();
    boolean deleteConditionalOrders();
    boolean deleteUserEvaluations();
    boolean deleteEvaluationResponses();
    boolean deleteNumericResponses();
    boolean deleteTextResponses();
    boolean deleteDateResponses();
    boolean deleteArrayResponses();
    boolean deleteMatrixResponses();

    boolean addArrayChoiceFromExcel();
    boolean addArraySetFromExcel();

    boolean addRowChoiceFromExcel();
    boolean addColChoiceFromExcel();
    boolean addMatrixSetFromExcel();

    boolean addEvaluationTypeFromExcel();
    boolean addEvaluationFromExcel();
    boolean addEvaluationResponseFromExcel();
}
