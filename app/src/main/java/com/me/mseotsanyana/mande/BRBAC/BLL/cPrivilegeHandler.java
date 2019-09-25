package com.me.mseotsanyana.mande.BRBAC.BLL;


import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.me.mseotsanyana.mande.BRBAC.DAL.cPrivilegeDBA;
import com.me.mseotsanyana.mande.BRBAC.DAL.cPrivilegeModel;
import com.me.mseotsanyana.mande.PPMER.BLL.cMapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mseotsanyana on 2017/08/28.
 */

public class cPrivilegeHandler extends cMapper<cPrivilegeModel, cPrivilegeDomain> {
    private static final String TAG = cPrivilegeHandler.class.getSimpleName();

    final static private int _id = cSessionManager.PRIVILEGE;
    private Context context;
    private cSessionManager session;

    private cPrivilegeDBA privilegeDBA;

    private int entityBITS, operationBITS;

    Gson gson = new Gson();

    public cPrivilegeHandler(Context context, cSessionManager session) {
        privilegeDBA = new cPrivilegeDBA(context);
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

    public boolean updatePrivilege(cPrivilegeDomain domain) {
        // map the business domain to the model
        Log.d(TAG, gson.toJson(domain));
        cPrivilegeModel model = this.DomainToModel(domain);
        Log.d(TAG, gson.toJson(model));
        return privilegeDBA.updatePrivilege(model);
    }

    public boolean deleteAllPrivilegeItems() {
        return privilegeDBA.deleteAllPrivileges();
    }

    public boolean deletePrivilege(cPrivilegeDomain domain) {
        // map the business domain to the model
        cPrivilegeModel model = this.DomainToModel(domain);
        return privilegeDBA.deletePrivilege(model);
    }

    public boolean addPrivilegeFromExcel(cPrivilegeDomain domain) {
        // map the business domain to the model
        cPrivilegeModel model = this.DomainToModel(domain);
        return privilegeDBA.addPrivilegeFromExcel(model);
    }

    public boolean addPrivilege(cPrivilegeDomain domain) {
        // map the business domain to the model
        cPrivilegeModel model = this.DomainToModel(domain);
        return privilegeDBA.addPrivilege(model);
    }

    public cPrivilegeDomain getPrivilegeByID(int organizationID, int roleID){
        cPrivilegeModel privilegeModel = privilegeDBA.getPrivilegeByID(organizationID, roleID);
        cPrivilegeDomain privilegeDomain = this.ModelToDomain(privilegeModel);

        return privilegeDomain;
    }


    public ArrayList<cPrivilegeDomain> getPrivilegesByIDs(int organizationID,int privilegeID){
        List<cPrivilegeModel> privilegeModels = privilegeDBA.getPrivilegesByIDs(organizationID, privilegeID);

        ArrayList<cPrivilegeDomain> privilegeDomains = new ArrayList<>();
        cPrivilegeDomain domain;

        for(int i = 0; i < privilegeModels.size(); i++) {
            domain = this.ModelToDomain(privilegeModels.get(i));
            privilegeDomains.add(domain);
        }

        return privilegeDomains;
    }


    public ArrayList<cPrivilegeDomain> getPrivilegeList(int userID, int orgID,
                                                        int primaryRole, int secondaryRoles) {
        // statuses of operations associated with ENTITY entity
        int privilegeOpsStatusBITS = session.loadStatusBITS(session.loadUserID(),
                session.loadOrganizationID(), cSessionManager.PRIVILEGE,
                cSessionManager.types[0], cSessionManager.READ);

        List<cPrivilegeModel> privilegeModel = privilegeDBA.getPrivilegeList(
                userID, orgID, primaryRole, secondaryRoles, operationBITS, privilegeOpsStatusBITS);

        ArrayList<cPrivilegeDomain> privilegeDomains = new ArrayList<>();
        cPrivilegeDomain domain;

        for(int i = 0; i < privilegeModel.size(); i++) {
            domain = this.ModelToDomain(privilegeModel.get(i));
            privilegeDomains.add(domain);
        }

        return privilegeDomains;
    }

    @Override
    protected cPrivilegeModel DomainToModel(cPrivilegeDomain domain) {
        cPrivilegeModel model = new cPrivilegeModel();

        model.setOrganizationID(domain.getOrganizationID());
        model.setPrivilegeID(domain.getPrivilegeID());
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

        domain.setOrganizationID(model.getOrganizationID());
        domain.setPrivilegeID(model.getPrivilegeID());
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
