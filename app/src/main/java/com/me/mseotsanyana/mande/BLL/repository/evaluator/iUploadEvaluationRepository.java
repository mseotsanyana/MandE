package com.me.mseotsanyana.mande.BLL.repository.evaluator;

public interface iUploadEvaluationRepository {
    boolean deleteArrayChoices();
    boolean deleteArrayChoiceSets();
    boolean deleteRowOptions();
    boolean deleteColOptions();
    boolean deleteMatrixChoices();
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
    boolean addRowOptionFromExcel();
    boolean addColOptionFromExcel();
    boolean addMatrixChoiceFromExcel();
    boolean addEvaluationTypeFromExcel();
    boolean addEvaluationFromExcel();
    boolean addEvaluationResponseFromExcel();
}
