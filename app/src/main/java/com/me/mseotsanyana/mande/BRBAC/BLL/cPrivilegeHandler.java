package com.me.mseotsanyana.mande.BRBAC.BLL;

import android.content.Context;

import com.me.mseotsanyana.mande.BRBAC.DAL.cPrivilegeDBA;
import com.me.mseotsanyana.mande.BRBAC.DAL.cPrivilegeModel;
import com.me.mseotsanyana.mande.PPMER.BLL.cMapper;

public class cPrivilegeHandler  extends cMapper<cPrivilegeModel, cPrivilegeDomain> {


    private cPrivilegeDBA privilegeDBA;
    private Context context;


    public cPrivilegeHandler(Context context, cSessionManager session) {
        privilegeDBA = new cPrivilegeDBA(context);
        this.context = context;
    }

    // business rules for deleting values
    public boolean deletePrivileges() {
        return privilegeDBA.deletePrivileges();
    }


    @Override
    protected cPrivilegeModel DomainToModel(cPrivilegeDomain domain) {
        cPrivilegeModel model = new cPrivilegeModel();

        model.setPrivilegeID(domain.getPrivilegeID());
        model.setServerID(domain.getServerID());
        model.setOwnerID(domain.getOwnerID());
        model.setOrgID(domain.getOrgID());
        model.setGroupBITS(domain.getGroupBITS());
        model.setPermsBITS(domain.getPermsBITS());
        model.setStatusBITS(domain.getStatusBITS());
        model.setName(domain.getName());
        model.setDescription(domain.getDescription());
        model.setCreatedDate(domain.getCreatedDate());
        model.setModifiedDate(domain.getModifiedDate());
        model.setSyncedDate(domain.getSyncedDate());

        return model;
    }

    @Override
    protected cPrivilegeDomain ModelToDomain(cPrivilegeModel model) {
        cPrivilegeDomain domain = new cPrivilegeDomain();

        domain.setPrivilegeID(model.getPrivilegeID());
        domain.setServerID(model.getServerID());
        domain.setOwnerID(model.getOwnerID());
        domain.setOrgID(model.getOrgID());
        domain.setGroupBITS(model.getGroupBITS());
        domain.setPermsBITS(model.getPermsBITS());
        domain.setStatusBITS(model.getStatusBITS());
        domain.setName(model.getName());
        domain.setDescription(model.getDescription());
        domain.setCreatedDate(model.getCreatedDate());
        domain.setModifiedDate(model.getModifiedDate());
        domain.setSyncedDate(model.getSyncedDate());

        return domain;
    }
}
