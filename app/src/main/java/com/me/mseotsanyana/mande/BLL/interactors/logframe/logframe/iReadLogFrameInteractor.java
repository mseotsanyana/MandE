package com.me.mseotsanyana.mande.BLL.interactors.logframe.logframe;

import com.me.mseotsanyana.mande.BLL.interactors.base.iInteractor;
import com.me.mseotsanyana.treeadapterlibrary.cTreeModel;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 *  This interactor is responsible for retrieving a set LogFrames
 *  from the database.
 */
public interface iReadLogFrameInteractor extends iInteractor {
    interface Callback {
        void onLogFramesRetrieved(LinkedHashMap<String, List<String>> menuModelSet, ArrayList<cTreeModel> logFrameModelSet);
        void onLogFramesRetrieveFailed(String msg);
    }
}
