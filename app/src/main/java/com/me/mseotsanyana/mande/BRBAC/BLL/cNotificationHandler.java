package com.me.mseotsanyana.mande.BRBAC.BLL;


import android.content.Context;

import com.me.mseotsanyana.mande.BRBAC.DAL.cMenuModel;
import com.me.mseotsanyana.mande.BRBAC.DAL.cNotificationModel;
import com.me.mseotsanyana.mande.UTILITY.BLL.cMapper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by mseotsanyana on 2017/08/28.
 */


public class cNotificationHandler extends cMapper<cNotificationModel, cNotificationDomain> {
    //private cNotificationDBA roleDBA;
    private Context context;
    private cSessionManager session;

    private int entityBITS, operationBITS;

    public cNotificationHandler() {
    }

    public cNotificationHandler(Context context, cSessionManager session) {
        //roleDBA = new cNotificationDBA(context);
        this.context = context;
        this.session = session;

        /** 1. ENTITY SECTION **/

        // entity bits of all entities that are accessible
        //entityBITS = session.loadEntityBITS(session.loadUserID(),
        //        session.loadOrganizationID(), cSessionManager.types[0]);

        /** 2. OPERATION SECTION **/

        // operations associated to ENTITY entity
        //operationBITS = session.loadOperationBITS(cSessionManager.,
        //        cSessionManager.types[0]);
    }

    /* ################################### CREATE ACTIONS ################################### */

    public boolean addRoleFromExcel(cNotificationDomain domain) {
        // map the business domain to the model
        cNotificationModel model = this.DomainToModel(domain);
        return true;//roleDBA.addRoleFromExcel(model);
    }

    public boolean addRole(cNotificationDomain domain) {
        // map the business domain to the model
        cNotificationModel model = this.DomainToModel(domain);
        return true;//roleDBA.addRole(model);
    }

    /* ################################### READ ACTIONS ################################### */

    public Set<cNotificationDomain> getRoleDomainSet(int userID, int orgID,
                                              int primaryRole, int secondaryRoles) {
        int statusBITS = 0;//FIXME
        Set<cNotificationModel> roleModelSet = null;//roleDBA.getRoleModelSet(
                //userID, orgID, primaryRole, secondaryRoles, operationBITS, statusBITS);

        Set<cNotificationDomain> roleDomainSet = new HashSet<>();
        cNotificationDomain domain;

        for(cNotificationModel roleModel: roleModelSet) {
            domain = this.ModelToDomain(roleModel);
            roleDomainSet.add(domain);
        }

        return roleDomainSet;
    }

    public Set<cMenuModel> getMenuModelsByRoleID(int roleID) {
        //Set<cMenuModel> roleModelSet = roleDBA.getMenusByRoleID(roleID);
        return null;//roleModelSet;
    }

    public ArrayList<cNotificationDomain> getRoleList(int userID, int orgID,
                                              int primaryRole, int secondaryRoles) {
        int statusBITS = 0;//FIXME
        List<cNotificationModel> roleModel = null;//roleDBA.getRoleList(
                //userID, orgID, primaryRole, secondaryRoles, operationBITS, statusBITS);

        ArrayList<cNotificationDomain> roleDomain = new ArrayList<>();
        cNotificationDomain domain;

        for(int i = 0; i < roleModel.size(); i++) {
            domain = this.ModelToDomain(roleModel.get(i));
            roleDomain.add(domain);
        }

        return roleDomain;
    }



    Set<cMenuModel> convertToModelSet(Set<cMenuDomain> menuDomainSet){

        Set<cMenuModel> modelSet = new HashSet<>();
        cMenuHandler menuHandler = new cMenuHandler();

        for (cMenuDomain menuDomain : menuDomainSet) {
            cMenuModel menuModel = menuHandler.DomainToModel(menuDomain);
            modelSet.add(menuModel);
        }

        return modelSet;
    }

    Set<cMenuDomain> convertToDomainSet(Set<cMenuModel> menuModelSet){
        Set<cMenuDomain> domainSet = new HashSet<>();
        cMenuHandler menuHandler = new cMenuHandler();

        for (cMenuModel menuModel : menuModelSet) {
            cMenuDomain menuDomain = menuHandler.ModelToDomain(menuModel);
            domainSet.add(menuDomain);
        }

        return domainSet;
    }

    /* ################################### UPDATE ACTIONS ################################### */

    public boolean updateRole(cNotificationDomain domain) {
        // map the business domain to the model
        cNotificationModel model = this.DomainToModel(domain);
        return true;//roleDBA.updateRole(model);
    }

    /* ################################### DELETE ACTIONS ################################### */

    public boolean deleteRoles() {
        return true;//roleDBA.deleteRoles();
    }

    /* ################################### SYNC ACTIONS ################################### */

    public cNotificationDomain getRoleByID(int roleID){
        cNotificationModel roleModel   = null;//roleDBA.getRoleByID(roleID);
        cNotificationDomain roleDomain = this.ModelToDomain(roleModel);

        return roleDomain;
    }

    @Override
    protected cNotificationModel DomainToModel(cNotificationDomain domain) {
        cNotificationModel model = new cNotificationModel();
        cOrganizationHandler organizationHandler = new cOrganizationHandler();
/*
        model.setRoleID(domain.getRoleID());
        model.setOrganizationID(domain.getOrganizationID());
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

        model.setOrganizationModel(organizationHandler.DomainToModel(domain.getOrganizationDomain()));

        if(!domain.getMenuDomainSet().isEmpty())
            model.setMenuModelSet(convertToModelSet(domain.getMenuDomainSet()));
*/
        return model;
    }

    @Override
    protected cNotificationDomain ModelToDomain(cNotificationModel model) {

        cNotificationDomain domain = new cNotificationDomain();
        /*
        cOrganizationHandler organizationHandler = new cOrganizationHandler();

        domain.setRoleID(model.getRoleID());
        domain.setOrganizationID(model.getOrganizationID());
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

        domain.setOrganizationDomain(organizationHandler.ModelToDomain(model.getOrganizationModel()));

        if(!model.getMenuModelSet().isEmpty())
            domain.setMenuDomainSet(convertToDomainSet(model.getMenuModelSet()));
*/
        return domain;
    }
}
