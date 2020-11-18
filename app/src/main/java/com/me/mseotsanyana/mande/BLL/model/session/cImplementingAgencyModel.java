package com.me.mseotsanyana.mande.BLL.model.session;

public class cImplementingAgencyModel extends cOrganizationModel {
    private long organizationID;

    @Override
    public long getOrganizationID() {
        return organizationID;
    }

    @Override
    public void setOrganizationID(long organizationID) {
        this.organizationID = organizationID;
    }
}
