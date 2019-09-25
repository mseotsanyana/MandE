package com.me.mseotsanyana.mande.BRBAC.BLL;

import android.content.Context;


import com.me.mseotsanyana.mande.BRBAC.DAL.cMenuModel;
import com.me.mseotsanyana.mande.BRBAC.DAL.cMenuRoleDBA;
import com.me.mseotsanyana.mande.BRBAC.DAL.cMenuRoleModel;
import com.me.mseotsanyana.mande.BRBAC.DAL.cRoleModel;
import com.me.mseotsanyana.mande.PPMER.BLL.cMapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mseotsanyana on 2017/06/26.
 */

public class cMenuRoleHandler extends cMapper<cMenuRoleModel, cMenuRoleDomain> {
    private Context context;
    private cSessionManager session;

    private cMenuRoleDBA menuRoleDBA;
    private cMenuHandler menuHandler;
    private cRoleHandler roleHandler;

    public cMenuRoleHandler(Context context, cSessionManager session) {
        menuRoleDBA = new cMenuRoleDBA(context);
        menuHandler = new cMenuHandler(context);
        roleHandler = new cRoleHandler(context, session);
        this.context = context;
        this.session = session;
    }
    // business rules for adding a outcome output
    public boolean addMenuRole(cMenuRoleDomain domain) {
        // map the business domain to the model
        cMenuRoleModel model = this.DomainToModel(domain);
        return menuRoleDBA.addMenuRole(model);
    }

    public boolean addMenuRoleFromExcel(cMenuRoleDomain domain) {
        // map the business domain to the model
        cMenuRoleModel model = this.DomainToModel(domain);
        return menuRoleDBA.addMenuRoleFromExcel(model);
    }

    public ArrayList<cMenuRoleDomain> getMenuRoleList() {
        List<cMenuRoleModel> menuRoleModels = menuRoleDBA.getMenuRoleList();

        ArrayList<cMenuRoleDomain> menuRoleDomains = new ArrayList<>();
        cMenuRoleDomain domain;

        for(int i = 0; i < menuRoleModels.size(); i++) {
            domain = this.ModelToDomain(menuRoleModels.get(i));
            menuRoleDomains.add(domain);
        }

        return menuRoleDomains;
    }

    public ArrayList<cRoleDomain> getRolesByMenuID(int menuID){
        List<cRoleModel> roleModels = menuRoleDBA.getRolesByMenuID(menuID);

        ArrayList<cRoleDomain> roleDomains = new ArrayList<>();
        cRoleDomain domain;

        for(int i = 0; i < roleModels.size(); i++) {
            domain = roleHandler.ModelToDomain(roleModels.get(i));
            roleDomains.add(domain);
        }

        return roleDomains;
    }

    public ArrayList<cMenuDomain> getMenusByRoleID(int roleID){
        List<cMenuModel> menuModels = menuRoleDBA.getMenusByRoleID(roleID);

        ArrayList<cMenuDomain> menuDomains = new ArrayList<>();
        cMenuDomain domain;

        for(int i = 0; i < menuModels.size(); i++) {
            domain = menuHandler.ModelToDomain(menuModels.get(i));
            menuDomains.add(domain);
        }

        return menuDomains;
    }

    @Override
    protected cMenuRoleModel DomainToModel(cMenuRoleDomain domain) {
        cMenuRoleModel model = new cMenuRoleModel();

        model.setMenuID(domain.getMenuID());
        model.setRoleID(domain.getRoleID());
        model.setOwnerID(domain.getOwnerID());
        model.setGroupBITS(domain.getGroupBITS());
        model.setPermsBITS(domain.getPermsBITS());
        model.setTypeBITS(domain.getTypeBITS());
        model.setStatusBITS(domain.getStatusBITS());
        model.setCreateDate(domain.getCreateDate());

        return model;
    }

    @Override
    protected cMenuRoleDomain ModelToDomain(cMenuRoleModel model) {
        cMenuRoleDomain domain = new cMenuRoleDomain();

        domain.setMenuID(model.getMenuID());
        domain.setRoleID(model.getRoleID());
        domain.setOwnerID(model.getOwnerID());
        domain.setGroupBITS(model.getGroupBITS());
        domain.setPermsBITS(model.getPermsBITS());
        domain.setTypeBITS(model.getTypeBITS());
        domain.setStatusBITS(model.getStatusBITS());
        domain.setCreateDate(model.getCreateDate());

        return domain;
    }
}
