package com.me.mseotsanyana.mande.BLL.interactors.session;


import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.me.mseotsanyana.mande.BLL.domain.session.cPermissionDomain;
import com.me.mseotsanyana.mande.DAL.storage.managers.cSessionManager;
import com.me.mseotsanyana.mande.DAL.ìmpl.session.cPermissionImpl;
import com.me.mseotsanyana.mande.DAL.model.session.cPermissionModel;
import com.me.mseotsanyana.mande.UTIL.BLL.cMapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mseotsanyana on 2017/08/28.
 */

public class cPermissionHandler extends cMapper<cPermissionModel, cPermissionDomain> {
    private static final String TAG = cPermissionHandler.class.getSimpleName();

    final static private int _id = cSessionManager.PRIVILEGE;
    private Context context;
    private cSessionManager session;

    private cPermissionImpl permissionDBA;

    private int entityBITS, operationBITS;

    Gson gson = new Gson();

    public cPermissionHandler(Context context, cSessionManager session) {
        permissionDBA = new cPermissionImpl(context);
        this.context = context;
        this.session = session;

        /** 1. ENTITY SECTION **/

        // entity bits of all entities that are accessible
        //entityBITS = session.loadEntityBITS(session.loadUserID(),
        //        session.loadOrganizationID(), cSessionManager.types[0]);

        /** 2. OPERATION SECTION **/

        // operations associated to ENTITY entity
        operationBITS = session.loadOperationBITS(cSessionManager.PRIVILEGE,
                cSessionManager.types[0]);
    }

    public boolean updatePrivilege(cPermissionDomain domain) {
        // map the business domain to the model
        Log.d(TAG, gson.toJson(domain));
        cPermissionModel model = this.DomainToModel(domain);
        Log.d(TAG, gson.toJson(model));
        return permissionDBA.updatePermission(model);
    }

    public boolean deleteAllPrivilegeItems() {
        return permissionDBA.deletePermissions();
    }

    public boolean deletePrivilege(cPermissionDomain domain) {
        // map the business domain to the model
        cPermissionModel model = this.DomainToModel(domain);
        return permissionDBA.deletePermission(model);
    }

    public boolean addPrivilegeFromExcel(cPermissionDomain domain) {
        // map the business domain to the model
        cPermissionModel model = this.DomainToModel(domain);
        return true;//permissionDBA.addPermissionFromExcel(model);
    }

    public boolean addPrivilege(cPermissionDomain domain) {
        // map the business domain to the model
        cPermissionModel model = this.DomainToModel(domain);
        return permissionDBA.addPermission(model);
    }

    public cPermissionDomain getPrivilegeByID(int organizationID, int roleID){
        cPermissionModel privilegeModel = null;//permissionDBA.getPermissionByID(organizationID, roleID);
        cPermissionDomain privilegeDomain = this.ModelToDomain(privilegeModel);

        return privilegeDomain;
    }


    public ArrayList<cPermissionDomain> getPrivilegesByIDs(int organizationID, int privilegeID){
        List<cPermissionModel> privilegeModels = null;//permissionDBA.getPermissionsByIDs(organizationID, privilegeID);

        ArrayList<cPermissionDomain> privilegeDomains = new ArrayList<>();
        cPermissionDomain domain;

        for(int i = 0; i < privilegeModels.size(); i++) {
            domain = this.ModelToDomain(privilegeModels.get(i));
            privilegeDomains.add(domain);
        }

        return privilegeDomains;
    }


    public ArrayList<cPermissionDomain> getPrivilegeList(int userID, int orgID,
                                                         int primaryRole, int secondaryRoles) {
        // statuses of operations associated with ENTITY entity
        int privilegeOpsStatusBITS = session.loadStatusBITS(cSessionManager.PRIVILEGE,
                cSessionManager.types[0]);

        List<cPermissionModel> privilegeModel = permissionDBA.getPermissionList(
                userID, orgID, primaryRole, secondaryRoles, operationBITS, privilegeOpsStatusBITS);

        ArrayList<cPermissionDomain> privilegeDomains = new ArrayList<>();
        cPermissionDomain domain;

        for(int i = 0; i < privilegeModel.size(); i++) {
            domain = this.ModelToDomain(privilegeModel.get(i));
            privilegeDomains.add(domain);
        }

        return privilegeDomains;
    }

    @Override
    protected cPermissionModel DomainToModel(cPermissionDomain domain) {
        cPermissionModel model = new cPermissionModel();

        //-model.setOrganizationID(domain.getOrganizationID());
        //-model.setPrivilegeID(domain.getPrivilegeID());
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
    protected cPermissionDomain ModelToDomain(cPermissionModel model) {
        cPermissionDomain domain = new cPermissionDomain();

        //-domain.setOrganizationID(model.getOrganizationID());
        //-domain.setPrivilegeID(model.getPrivilegeID());
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
