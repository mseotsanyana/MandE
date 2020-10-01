package com.me.mseotsanyana.mande.BLL.interactors.session.role.Impl;


import android.content.Context;

import com.me.mseotsanyana.mande.BLL.domain.session.cMenuDomain;
import com.me.mseotsanyana.mande.BLL.domain.session.cRoleDomain;
import com.me.mseotsanyana.mande.BLL.interactors.session.menu.Impl.cMenuHandler;
import com.me.mseotsanyana.mande.DAL.model.session.cMenuModel;
import com.me.mseotsanyana.mande.DAL.ìmpl.session.cRoleRepositoryImpl;
import com.me.mseotsanyana.mande.DAL.model.session.cRoleModel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by mseotsanyana on 2017/08/28.
 */


public class cRoleHandler {
    private cRoleRepositoryImpl roleDBA;
    private Context context;
    //private cSessionManager session;

    private int entityBITS, operationBITS;

    public cRoleHandler() {
    }

    public cRoleHandler(Context context) {
        roleDBA = new cRoleRepositoryImpl(context);
        this.context = context;
        //this.session = session;

        /** 1. ENTITY SECTION **/

        // entity bits of all entities that are accessible
        //entityBITS = session.loadEntityBITS(session.loadUserID(),
        //        session.loadOrganizationID(), cSessionManager.types[0]);

        /** 2. OPERATION SECTION **/

        // operations associated to ENTITY entity
        //operationBITS = session.loadOperationBITS(cSessionManager.ENTITY,
        //        cSessionManager.types[0]);
    }

    /* ################################### CREATE ACTIONS ################################### */

    public boolean addRoleFromExcel(cRoleDomain domain) {
        // map the business domain to the model
        cRoleModel model = this.DomainToModel(domain);
        return true;//roleDBA.addRoleFromExcel(model);
    }

    public boolean addRole(cRoleDomain domain) {
        // map the business domain to the model
        cRoleModel model = this.DomainToModel(domain);
        return true;//roleDBA.addRole(model);
    }

    /* ################################### READ ACTIONS ################################### */

    public Set<cRoleDomain> getRoleDomainSet(int userID, int orgID,
                                              int primaryRole, int secondaryRoles) {
        int statusBITS = 0;//FIXME
        Set<cRoleModel> roleModelSet = roleDBA.getRoleModelSet(
                userID, orgID, primaryRole, secondaryRoles, operationBITS, statusBITS);

        Set<cRoleDomain> roleDomainSet = new HashSet<>();
        cRoleDomain domain;

        for(cRoleModel roleModel: roleModelSet) {
            domain = this.ModelToDomain(roleModel);
            roleDomainSet.add(domain);
        }

        return roleDomainSet;
    }

    public Set<cMenuModel> getMenuModelsByRoleID(int roleID, int organizationID) {
        Set<cMenuModel> roleModelSet = roleDBA.getMenusByRoleID(roleID, organizationID);
        return roleModelSet;
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

    Set<cMenuModel> convertToModelSet(Set<cMenuDomain> menuDomainSet){

        Set<cMenuModel> modelSet = new HashSet<>();
        cMenuHandler menuHandler = new cMenuHandler();

        for (cMenuDomain menuDomain : menuDomainSet) {
            //cMenuModel menuModel = menuHandler.DomainToModel(menuDomain);
            //modelSet.add(menuModel);
        }

        return modelSet;
    }

    Set<cMenuDomain> convertToDomainSet(Set<cMenuModel> menuModelSet){
        Set<cMenuDomain> domainSet = new HashSet<>();
        cMenuHandler menuHandler = new cMenuHandler();

        for (cMenuModel menuModel : menuModelSet) {
            //cMenuDomain menuDomain = menuHandler.ModelToDomain(menuModel);
            //domainSet.add(menuDomain);
        }

        return domainSet;
    }

    /* ################################### UPDATE ACTIONS ################################### */

    public boolean updateRole(cRoleDomain domain) {
        // map the business domain to the model
        cRoleModel model = this.DomainToModel(domain);
        return true;//roleDBA.updateRole(model);
    }

    /* ################################### DELETE ACTIONS ################################### */

    public boolean deleteRoles() {
        return roleDBA.deleteRoles();
    }

    /* ################################### SYNC ACTIONS ################################### */

    public cRoleDomain getRoleByID(int roleID){
        cRoleModel roleModel   = roleDBA.getRoleByID(roleID);
        cRoleDomain roleDomain = this.ModelToDomain(roleModel);

        return roleDomain;
    }

    //@Override
    protected cRoleModel DomainToModel(cRoleDomain domain) {
        cRoleModel model = new cRoleModel();
        //cOrganizationHandler organizationHandler = new cOrganizationHandler();

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

        if(domain.getOrganizationDomain() != null){
            //model.setOrganizationModel(
            //        organizationHandler.DomainToModel(domain.getOrganizationDomain()));
        }

        if(!domain.getMenuDomainSet().isEmpty())
            model.setMenuModelSet(convertToModelSet(domain.getMenuDomainSet()));

        return model;
    }

    //@Override
    protected cRoleDomain ModelToDomain(cRoleModel model) {
        cRoleDomain domain = new cRoleDomain();
        //cOrganizationHandler organizationHandler = new cOrganizationHandler();

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

        if(model.getOrganizationModel() != null){
            //domain.setOrganizationDomain(
            //        organizationHandler.ModelToDomain(model.getOrganizationModel()));
        }

        if(!model.getMenuModelSet().isEmpty())
            domain.setMenuDomainSet(convertToDomainSet(model.getMenuModelSet()));

        return domain;
    }
}
