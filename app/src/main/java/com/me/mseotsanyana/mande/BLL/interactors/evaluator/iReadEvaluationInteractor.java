package com.me.mseotsanyana.mande.BLL.interactors.evaluator;

import com.me.mseotsanyana.mande.BLL.interactors.base.iInteractor;
import com.me.mseotsanyana.treeadapterlibrary.cTreeModel;

import java.util.ArrayList;
import java.util.Map;

/**
 *  This interactor is responsible for retrieving a set inputs
 *  from the database.
 */
public interface iReadEvaluationInteractor extends iInteractor {
    interface Callback {
        void onEvaluationModelsRetrieved(ArrayList<cTreeModel> evaluationTreeModels);
        void onEvaluationModelsFailed(String msg);
    }
}
