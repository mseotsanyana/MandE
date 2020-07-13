package com.me.mseotsanyana.mande.BLL.interactors.logframe.input;

import com.me.mseotsanyana.mande.BLL.interactors.base.iInteractor;
import com.me.mseotsanyana.treeadapterlibrary.cTreeModel;

import java.util.ArrayList;
import java.util.Map;

/**
 *  This interactor is responsible for retrieving a set inputs
 *  from the database.
 */
public interface iReadInputInteractor extends iInteractor {
    interface Callback {
        void onInputModelsRetrieved(Map<Integer, ArrayList<cTreeModel>> inputTreeModels);
        void onInputModelsFailed(String msg);
    }
}
