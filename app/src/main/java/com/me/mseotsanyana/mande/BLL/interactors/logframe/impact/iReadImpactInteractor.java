package com.me.mseotsanyana.mande.BLL.interactors.logframe.impact;

import com.me.mseotsanyana.mande.BLL.interactors.base.iInteractor;
import com.me.mseotsanyana.treeadapterlibrary.cTreeModel;

import java.util.List;

/**
 *  This interactor is responsible for retrieving a set impacts
 *  from the database.
 */
public interface iReadImpactInteractor extends iInteractor {
    interface Callback {
        void onImpactModelsRetrieved(String logframeServerID, List<cTreeModel> impactTreeModels);
        void onImpactModelsFailed(String msg);
    }
}
