package com.me.mseotsanyana.mande.BLL.interactors.session.team;

import com.me.mseotsanyana.mande.BLL.interactors.base.iInteractor;
import com.me.mseotsanyana.treeadapterlibrary.cTreeModel;

import java.util.List;

public interface iReadTeamsWithRolesInteractor extends iInteractor {
    interface Callback {
        void onReadTeamsWithRolesFailed(String msg);
        void onReadTeamsWithRolesSucceeded(List<cTreeModel> teamsRolesTree);
    }
}
