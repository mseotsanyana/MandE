package com.me.mseotsanyana.mande.BRBAC.BLL;


import android.content.Context;

import com.me.mseotsanyana.mande.BRBAC.BLL.cRoleDomain;
import com.me.mseotsanyana.mande.BRBAC.DAL.cRoleDBA;
import com.me.mseotsanyana.mande.BRBAC.DAL.cRoleModel;
import com.me.mseotsanyana.mande.PPMER.BLL.cMapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mseotsanyana on 2017/08/28.
 */


public class cRoleHandler extends cMapper<cRoleModel, cRoleDomain> {
    private cRoleDBA roleDBA;
    private Context context;
    private cSessionManager session;

    private int entityBITS, operationBITS;

    public cRoleHandler() {
    }

    public cRoleHandler(Context context, cSessionManager session) {
        roleDBA = new cRoleDBA(context);
        this.context = context;
        this.session = session;

        /** 1. ENTITY SECTION **/

        // entity bits of all entities that are accessible
        entityBITS = session.loadEntityBITS(session.loadUserID(),
                session.loadOrganizationID(), cSessionManager.types[0]);

        /** 2. OPERATION SECTION **/

        // operations associated to ENTITY entity
        operationBITS = session.loadOperationBITS(session.loadUserID(),
                session.loadOrganizationID(), cSessionManager.PRIVILEGE,
                cSessionManager.types[0]);
    }

    public boolean deleteAllRoles() {
        return roleDBA.deleteAllRoles();
    }

    public boolean addRoleFromExcel(cRoleDomain domain) {
        // map the business domain to the model
        cRoleModel model = this.DomainToModel(domain);
        return roleDBA.addRoleFromExcel(model);
    }

    public boolean addRole(cRoleDomain domain) {
        // map the business domain to the model
        cRoleModel model = this.DomainToModel(domain);
        return roleDBA.addRole(model);
    }

    public boolean updateRole(cRoleDomain domain) {
        // map the business domain to the model
        cRoleModel model = this.DomainToModel(domain);
        return roleDBA.updateRole(model);
    }

    public cRoleDomain getRoleByID(int roleID){
        cRoleModel roleModel   = roleDBA.getRoleByID(roleID);
        cRoleDomain roleDomain = this.ModelToDomain(roleModel);

        return roleDomain;
    }

    public ArrayList<cRoleDomain> getRoleList(int userID, int orgID,
                                              int primaryRole, int secondaryRoles) {
        int statusBITS = 0;//FIXME
        List<cRoleModel> roleModel = roleDBA.getRoleList(
                userID, orgID, primaryRole, secondaryRoles, operationBITS, statusBITS);

        ArrayList<cRoleDomain> roleDomain = new ArrayList<>();
        cRoleDomain domain;

        for(int i = 0; i < roleModel.size(); i++) {
            domain = this.ModelToDomain(roleModel.get(i));
            roleDomain.add(domain);
        }

        return roleDomain;
    }

    @Override
    protected cRoleModel DomainToModel(cRoleDomain domain) {
        cRoleModel model = new cRoleModel();

        model.setRoleID(domain.getRoleID());
        model.setOrganizationID(domain.getOrganizationID());
        model.setOwnerID(domain.getOwnerID());
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
    protected cRoleDomain ModelToDomain(cRoleModel model) {
        cRoleDomain domain = new cRoleDomain();

        domain.setRoleID(model.getRoleID());
        domain.setOrganizationID(model.getOrganizationID());
        domain.setOwnerID(model.getOwnerID());
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
