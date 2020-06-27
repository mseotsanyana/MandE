package com.me.mseotsanyana.mande.BLL.interactors.logframe.output;

import com.me.mseotsanyana.mande.BLL.interactors.base.iInteractor;
import com.me.mseotsanyana.treeadapterlibrary.cTreeModel;

import java.util.ArrayList;

/**
 *  This interactor is responsible for retrieving a set outputs
 *  from the database.
 */
public interface iReadOutputInteractor extends iInteractor {
    interface Callback {
        void onOutputsRetrieved(String logFrameName, ArrayList<cTreeModel> outputTreeModels);
        void onOutputsRetrieveFailed(String msg);
    }
}
