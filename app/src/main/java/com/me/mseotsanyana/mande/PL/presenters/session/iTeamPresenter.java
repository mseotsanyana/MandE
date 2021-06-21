package com.me.mseotsanyana.mande.PL.presenters.session;

import com.me.mseotsanyana.mande.BLL.model.session.cTeamModel;
import com.me.mseotsanyana.mande.BLL.model.session.cUserProfileModel;
import com.me.mseotsanyana.mande.PL.presenters.base.iPresenter;
import com.me.mseotsanyana.mande.PL.ui.iBaseView;
import com.me.mseotsanyana.treeadapterlibrary.cTreeModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface iTeamPresenter extends iPresenter {
    interface View extends iBaseView {
        void onClickCreateTeam();

        void onCreateTeamFailed(String msg);
        void onCreateTeamSucceeded(String msg);

        void onReadTeamsFailed(String msg);
        void onReadTeamsWithMembersSucceeded (List<cTreeModel> teamsMembersTree);

        /*EditText getNameEditText();
        EditText getEmailEditText();
        EditText getWebsiteEditText();

        String getResourceString(int resourceID);*/
    }

    /* implemented in PresenterImpl to link ui with InteractorImpl */
    void createTeam(cTeamModel teamModel);

    void readTeamsWithMembers();
}

