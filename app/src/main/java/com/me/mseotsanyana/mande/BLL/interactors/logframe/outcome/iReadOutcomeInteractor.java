package com.me.mseotsanyana.mande.BLL.interactors.logframe.outcome;

import com.me.mseotsanyana.mande.BLL.interactors.base.iInteractor;
import com.me.mseotsanyana.treeadapterlibrary.cTreeModel;

import java.util.ArrayList;

/**
 *  This interactor is responsible for retrieving a set impacts
 *  from the database.
 */
public interface iReadOutcomeInteractor extends iInteractor {
    interface Callback {
        void onOutcomeModelsRetrieved(String logFrameName, ArrayList<cTreeModel> outcomeTreeModels);
        void onOutcomeModelsFailed(String msg);
    }
}
