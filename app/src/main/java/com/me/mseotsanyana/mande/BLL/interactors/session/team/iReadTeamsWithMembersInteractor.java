package com.me.mseotsanyana.mande.BLL.interactors.session.team;

import com.me.mseotsanyana.mande.BLL.interactors.base.iInteractor;
import com.me.mseotsanyana.treeadapterlibrary.cTreeModel;

import java.util.List;

public interface iReadTeamsWithMembersInteractor extends iInteractor {
    interface Callback {
        void onReadTeamsWithMembersFailed(String msg);
        void onReadTeamsWithMembersSucceeded(List<cTreeModel> teamsMembersTree);
    }
}
