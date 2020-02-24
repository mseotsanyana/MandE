package com.me.mseotsanyana.mande.PPMER.BLL.repository;

public interface iUploadEvaluationRepository {
    boolean deleteArrayChoices();
    boolean deleteArrayChoiceSets();
    boolean deleteRowOptions();
    boolean deleteColOptions();
    boolean deleteMatrixChoices();
    boolean deleteMatrixChoiceSets();
    boolean deleteEvaluationTypes();
    boolean deleteQuestionnaires();
    boolean deleteQuestionnaireQuestions();
    boolean deleteConditionalOrders();
    boolean deleteQuestionnaireUsers();
    boolean deleteEResponses();
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
    boolean addQuestionnaireFromExcel();
    boolean addEResponseFromExcel();
}
