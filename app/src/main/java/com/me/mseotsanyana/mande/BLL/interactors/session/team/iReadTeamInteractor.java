package com.me.mseotsanyana.mande.BLL.interactors.session.team;

import com.me.mseotsanyana.mande.BLL.interactors.base.iInteractor;
import com.me.mseotsanyana.mande.BLL.model.session.cTeamModel;
import com.me.mseotsanyana.mande.BLL.model.session.cUserProfileModel;
import com.me.mseotsanyana.treeadapterlibrary.cTreeModel;

import java.util.List;
import java.util.Map;

public interface iReadTeamInteractor extends iInteractor {
    interface Callback {
        void onReadTeamsFailed(String msg);
        void onReadTeamsWithMembersSucceeded(List<cTreeModel> teamsMembersTree);
    }
}
