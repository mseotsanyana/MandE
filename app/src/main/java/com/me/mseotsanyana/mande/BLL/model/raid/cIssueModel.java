package com.me.mseotsanyana.mande.BLL.model.raid;

import com.me.mseotsanyana.mande.BLL.model.logframe.cRaidModel;
import com.me.mseotsanyana.mande.BLL.model.raid.cIssueRegisterModel;

public class cIssueModel extends cRaidModel {
    private cIssueRegisterModel issueRegisterModel;

    public cIssueRegisterModel getIssueRegisterModel() {
        return issueRegisterModel;
    }

    public void setIssueRegisterModel(cIssueRegisterModel issueRegisterModel) {
        this.issueRegisterModel = issueRegisterModel;
    }
}
