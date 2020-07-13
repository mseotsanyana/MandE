package com.me.mseotsanyana.mande.DAL.model.session;

import com.me.mseotsanyana.mande.DAL.model.session.cOrganizationModel;

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
