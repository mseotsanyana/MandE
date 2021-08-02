package com.me.mseotsanyana.mande.PL.presenters.session;

import com.me.mseotsanyana.mande.BLL.model.session.cOrganizationModel;
import com.me.mseotsanyana.mande.BLL.model.session.cUserProfileModel;
import com.me.mseotsanyana.mande.PL.presenters.base.iPresenter;
import com.me.mseotsanyana.mande.PL.ui.iBaseView;

import java.util.List;

public interface iOrganizationPresenter extends iPresenter {
    interface View extends iBaseView {
        void onClickCreateOrganization();

        void onCreateOrganizationFailed(String msg);
        void onCreateOrganizationSucceeded(String msg);
        void onReadOrganizationsFailed(String msg);
        void onReadOrganizationsSucceeded(List<cOrganizationModel> organizationModels);

        void onReadOrganizationMembersFailed(String msg);
        void onReadOrganizationMembersSucceeded(List<cUserProfileModel> userProfileModels);

        /*EditText getNameEditText();
        EditText getEmailEditText();
        EditText getWebsiteEditText();

        String getResourceString(int resourceID);*/
    }

    /* implemented in PresenterImpl to link ui with InteractorImpl */
    void createOrganization(cOrganizationModel organizationModel);

    void readOrganizations();
    void readOrganizationMembers();
}

