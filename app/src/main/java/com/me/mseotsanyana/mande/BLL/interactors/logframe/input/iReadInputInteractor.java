package com.me.mseotsanyana.mande.BLL.interactors.logframe.input;

import com.me.mseotsanyana.mande.BLL.interactors.base.iInteractor;
import com.me.mseotsanyana.treeadapterlibrary.cTreeModel;

import java.util.ArrayList;

/**
 *  This interactor is responsible for retrieving a set inputs
 *  from the database.
 */
public interface iReadInputInteractor extends iInteractor {
    interface Callback {
        void onInputsRetrieved(String logFrameName, ArrayList<cTreeModel> inputTreeModels);
        void onInputsRetrieveFailed(String msg);
    }
}
