package com.me.mseotsanyana.mande.DAL.model.session;

public class cBeneficiaryModel extends cOrganizationModel {
    private long organizationID;

    @Override
    public long getOrganizationID() {
        return organizationID;
    }

    public void setOrganizationID(long organizationID) {
        this.organizationID = organizationID;
    }
}
