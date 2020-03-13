package com.me.mseotsanyana.mande.BLL.interactors.session;


import android.content.Context;

import com.me.mseotsanyana.mande.BLL.domain.session.cMenuDomain;
import com.me.mseotsanyana.mande.DAL.storage.managers.cSessionManager;
import com.me.mseotsanyana.mande.DAL.ìmpl.session.cMenuImpl;
import com.me.mseotsanyana.mande.DAL.model.session.cMenuModel;
import com.me.mseotsanyana.mande.UTIL.BLL.cMapper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by mseotsanyana on 2017/08/28.
 */

public class cMenuHandler extends cMapper<cMenuModel, cMenuDomain> {
    private cMenuImpl menuDBA;
    private Context context;
    private cSessionManager session;

    private int entityBITS, operationBITS;

    public cMenuHandler() {}

    public cMenuHandler(Context context, cSessionManager session) {
        menuDBA = new cMenuImpl(context);
        this.context = context;
        this.session = session;

        /** 1. ENTITY SECTION **/

        // entity bits of all entities that are accessible
        //entityBITS = session.loadEntityBITS(session.loadUserID(),
        //        session.loadOrganizationID(), cSessionManager.types[0]);

        /** 2. OPERATION SECTION **/

        // operations associated to ENTITY entity
        //operationBITS = session.loadOperationBITS(cSessionManager.MENU,
        //        cSessionManager.types[0]);
    }

    /* ################################### CREATE ACTIONS ################################### */

    public boolean addMenuFromExcel(cMenuDomain domain) {
        // map the business domain to the model
        cMenuModel model = this.DomainToModel(domain);
        return menuDBA.addMenuFromExcel(model);
    }

    public boolean addMenu(cMenuDomain domain) {
        // map the business domain to the model
        cMenuModel model = this.DomainToModel(domain);
        return menuDBA.addMenu(model);
    }

    /* ################################### READ ACTIONS ################################### */

    public Set<cMenuDomain> getMenuDomainSet(int userID, int orgID,
                                             int primaryRole, int secondaryRoles) {
        int statusBITS = 0;//FIXME
        Set<cMenuModel> menuModelSet = menuDBA.getMenuModelSet(
                userID, orgID, primaryRole, secondaryRoles, operationBITS, statusBITS);

        Set<cMenuDomain> menuDomainSet = new HashSet<>();
        cMenuDomain domain;

        for(cMenuModel menuModel: menuModelSet) {
            domain = this.ModelToDomain(menuModel);
            menuDomainSet.add(domain);
        }

        return menuDomainSet;
    }

    public cMenuDomain getMenuByID(int menuID){
        cMenuModel menuModel = menuDBA.getMenuByID(menuID);
        cMenuDomain menuDomain = this.ModelToDomain(menuModel);

        return menuDomain;
    }

    public ArrayList<cMenuDomain> getSubMenuByID(int menuID){
        List<cMenuModel> menuModels = menuDBA.getSubMenuByID(menuID);

        ArrayList<cMenuDomain> menuDomains = new ArrayList<>();
        cMenuDomain domain;

        for(int i = 0; i < menuModels.size(); i++) {
            domain = this.ModelToDomain(menuModels.get(i));
            menuDomains.add(domain);
        }

        return menuDomains;
    }

    public ArrayList<cMenuDomain> getMenuList() {
        List<cMenuModel> menuModel = menuDBA.getMenuList();

        ArrayList<cMenuDomain> menuDomains = new ArrayList<>();
        cMenuDomain domain;

        for(int i = 0; i < menuModel.size(); i++) {
            domain = this.ModelToDomain(menuModel.get(i));
            menuDomains.add(domain);
        }

        return menuDomains;
    }

    Set<cMenuModel> convertToModelSet(Set<cMenuDomain> menuDomainSet){

        Set<cMenuModel> modelSet = new HashSet<>();
        //cMenuHandler menuHandler = new cMenuHandler();

        for (cMenuDomain menuDomain : menuDomainSet) {
            cMenuModel menuModel = this.DomainToModel(menuDomain);
            modelSet.add(menuModel);
        }

        return modelSet;
    }

    Set<cMenuDomain> convertToDomainSet(Set<cMenuModel> menuModelSet){
        Set<cMenuDomain> domainSet = new HashSet<>();
        //cMenuHandler menuHandler = new cMenuHandler();

        for (cMenuModel menuModel : menuModelSet) {
            cMenuDomain menuDomain = this.ModelToDomain(menuModel);
            domainSet.add(menuDomain);
        }

        return domainSet;
    }


    /* ################################### UPDATE ACTIONS ################################### */

    /* ################################### DELETE ACTIONS ################################### */

    public boolean deleteAllMenuItems() {
        return menuDBA.deleteMenuItems();
    }

    /* ################################### SYNC ACTIONS ################################### */

    @Override
    protected cMenuModel DomainToModel(cMenuDomain domain) {
        cMenuModel model = new cMenuModel();

        model.setMenuID(domain.getMenuID());
        model.setParentID(domain.getParentID());
        model.setOwnerID(domain.getOwnerID());
        model.setGroupBITS(domain.getGroupBITS());
        model.setPermsBITS(domain.getPermsBITS());
        model.setStatusBITS(domain.getStatusBITS());
        model.setName(domain.getName());
        model.setDescription(domain.getDescription());
        model.setCreatedDate(domain.getCreatedDate());
        model.setModifiedDate(domain.getModifiedDate());
        model.setSyncedDate(domain.getSyncedDate());

        if(!domain.getMenuDomainSet().isEmpty())
            model.setMenuModelSet(convertToModelSet(domain.getMenuDomainSet()));

        return model;
    }

    @Override
    protected cMenuDomain ModelToDomain(cMenuModel model) {
        cMenuDomain domain = new cMenuDomain();

        domain.setMenuID(model.getMenuID());
        domain.setParentID(model.getParentID());
        domain.setOwnerID(model.getOwnerID());
        domain.setGroupBITS(model.getGroupBITS());
        domain.setPermsBITS(model.getPermsBITS());
        domain.setStatusBITS(model.getStatusBITS());
        domain.setName(model.getName());
        domain.setDescription(model.getDescription());
        domain.setCreatedDate(model.getCreatedDate());
        domain.setModifiedDate(model.getModifiedDate());
        domain.setSyncedDate(model.getSyncedDate());

        if(!model.getMenuModelSet().isEmpty())
            domain.setMenuDomainSet(convertToDomainSet(model.getMenuModelSet()));

        return domain;
    }
}
