package com.me.mseotsanyana.mande.BLL.interactors.session.entity.Impl;

import android.content.Context;

import com.me.mseotsanyana.mande.BLL.domain.session.cEntityDomain;
import com.me.mseotsanyana.mande.DAL.ìmpl.session.cEntityRepositoryImpl;
import com.me.mseotsanyana.mande.DAL.model.session.cEntityModel;
import com.me.mseotsanyana.mande.UTIL.BLL.cMapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mseotsanyana on 2017/08/28.
 */

public class cEntityHandler extends cMapper<cEntityModel, cEntityDomain> {
    private cEntityRepositoryImpl entityDBA;

    private Context context;

    public cEntityHandler(Context context) {
        entityDBA = new cEntityRepositoryImpl(context);
        this.context = context;
    }

    public cEntityHandler() {

    }

    public boolean deleteEntities() {
        return entityDBA.deleteEntities();
    }

    public boolean addEntityFromExcel(cEntityDomain domain) {
        // map the business domain to the model
        cEntityModel model = this.DomainToModel(domain);
        return entityDBA.addEntityFromExcel(model);
    }

    public boolean addEntity(cEntityDomain domain) {
        // map the business domain to the model
        cEntityModel model = this.DomainToModel(domain);
        return entityDBA.addEntity(model);
    }

    public ArrayList<cEntityDomain> getEntityList() {
        List<cEntityModel> entityModel = entityDBA.getEntityList();

        ArrayList<cEntityDomain> entityDomain = new ArrayList<>();
        cEntityDomain domain;

        for(int i = 0; i < entityModel.size(); i++) {
            domain = this.ModelToDomain(entityModel.get(i));
            entityDomain.add(domain);
        }

        return entityDomain;
    }

    public String getEntityNameByID(int entityID) {
        String entityName = entityDBA.getEntityNameByID(entityID);

        return entityName;
    }

    public cEntityDomain getEntityByID(int entityID, int typeID) {
        cEntityModel model = entityDBA.getEntityByID(entityID, typeID);

        cEntityDomain domain = ModelToDomain(model);

        return domain;
    }

    @Override
    protected cEntityModel DomainToModel(cEntityDomain domain) {
        cEntityModel model = new cEntityModel();

        model.setEntityID(domain.getEntityID());
        model.setEntityTypeID(domain.getTypeID());
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
    protected cEntityDomain ModelToDomain(cEntityModel model) {
        cEntityDomain domain = new cEntityDomain();

        domain.setEntityID(model.getEntityID());
        domain.setTypeID(model.getEntityTypeID());
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
