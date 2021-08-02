package com.me.mseotsanyana.mande.PL.presenters.session;

import com.me.mseotsanyana.mande.BLL.model.session.cUserProfileModel;
import com.me.mseotsanyana.mande.PL.presenters.base.iPresenter;
import com.me.mseotsanyana.mande.PL.ui.iBaseView;

import java.util.List;

public interface iOrganizationMemberPresenter extends iPresenter {
    interface View extends iBaseView {
        void onReadOrganizationMembersFailed(String msg);
        void onReadOrganizationMembersSucceeded(List<cUserProfileModel> userProfileModels);

        /*EditText getNameEditText();
        EditText getEmailEditText();
        EditText getWebsiteEditText();

        String getResourceString(int resourceID);*/
    }

    void readOrganizationMembers();
}

