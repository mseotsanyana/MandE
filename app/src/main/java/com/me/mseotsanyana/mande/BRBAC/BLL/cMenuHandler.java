package com.me.mseotsanyana.mande.BRBAC.BLL;


import android.content.Context;

import com.me.mseotsanyana.mande.BRBAC.DAL.cMenuDBA;
import com.me.mseotsanyana.mande.BRBAC.DAL.cMenuModel;
import com.me.mseotsanyana.mande.PPMER.BLL.cMapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mseotsanyana on 2017/08/28.
 */

public class cMenuHandler extends cMapper<cMenuModel, cMenuDomain> {
    private cMenuDBA menuDBA;
    private Context context;

    public cMenuHandler(Context context) {
        menuDBA = new cMenuDBA(context);
        this.context = context;
    }

    public boolean deleteAllMenuItems() {
        return menuDBA.deleteAllMenuItems();
    }

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
        model.setCreateDate(domain.getCreateDate());

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
        domain.setCreateDate(model.getCreateDate());

        return domain;
    }
}
