package com.me.mseotsanyana.mande.BLL.interactors.logframe.activity;

import com.me.mseotsanyana.mande.BLL.interactors.base.iInteractor;
import com.me.mseotsanyana.treeadapterlibrary.cTreeModel;

import java.util.ArrayList;

/**
 *  This interactor is responsible for retrieving a set activities
 *  from the database.
 */
public interface iReadActivityInteractor extends iInteractor {
    interface Callback {
        void onActivitiesRetrieved(String logFrameName, ArrayList<cTreeModel> activityTreeModels);
        void onActivitiesRetrieveFailed(String msg);
    }
}
