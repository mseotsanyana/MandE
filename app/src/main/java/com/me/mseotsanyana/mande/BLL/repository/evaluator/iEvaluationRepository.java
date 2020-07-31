package com.me.mseotsanyana.mande.BLL.repository.evaluator;

import com.me.mseotsanyana.mande.DAL.model.evaluator.cEvaluationModel;

import java.util.Set;

public interface iEvaluationRepository {
    /* the create function of the Evaluation entity */
    boolean addEvaluation(cEvaluationModel evaluationModel);

    /* the read functions of the Evaluation entity */
    Set<cEvaluationModel> getEvaluationModelSet(long logFrameID, long userID,
                                                   int primaryRoleBITS, int secondaryRoleBITS,
                                                   int statusBITS);
}
