package com.me.mseotsanyana.mande.BLL.interactors.session.status.Impl;

import android.content.Context;

import com.me.mseotsanyana.mande.BLL.domain.session.cStatusDomain;
import com.me.mseotsanyana.mande.DAL.ìmpl.session.cStatusRepositoryImpl;
import com.me.mseotsanyana.mande.DAL.model.session.cStatusModel;
import com.me.mseotsanyana.mande.UTIL.BLL.cMapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mseotsanyana on 2017/08/28.
 */

public class cStatusHandler extends cMapper<cStatusModel, cStatusDomain> {
    private cStatusRepositoryImpl statusDBA;
    private Context context;

    public cStatusHandler(Context context) {
        statusDBA = new cStatusRepositoryImpl(context);
        this.context = context;
    }

    public cStatusHandler() {

    }

    public boolean deleteStatuses() {
        return statusDBA.deleteStatuses();
    }

    public boolean addStatusFromExcel(cStatusDomain domain) {
        // map the business domain to the model
        cStatusModel model = this.DomainToModel(domain);
        return statusDBA.addStatusFromExcel(model);
    }

    public boolean addStatus(cStatusDomain domain) {
        // map the business domain to the model
        cStatusModel model = this.DomainToModel(domain);
        return statusDBA.addStatus(model);
    }

    public cStatusDomain getStatusByID(int statusID) {
        cStatusModel model = statusDBA.getStatusByID(statusID);
        cStatusDomain domain = ModelToDomain(model);
        return domain;
    }


    public ArrayList<cStatusDomain> getStatusList() {
        List<cStatusModel> statusModel = statusDBA.getStatusList();

        ArrayList<cStatusDomain> statusDomain = new ArrayList<>();
        cStatusDomain domain;

        for(int i = 0; i < statusModel.size(); i++) {
            domain = this.ModelToDomain(statusModel.get(i));
            statusDomain.add(domain);
        }
        return statusDomain;
    }

    @Override
    protected cStatusModel DomainToModel(cStatusDomain domain) {
        cStatusModel model = new cStatusModel();

        model.setStatusID(domain.getStatusID());
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
    protected cStatusDomain ModelToDomain(cStatusModel model) {
        cStatusDomain domain = new cStatusDomain();

        domain.setStatusID(model.getStatusID());
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
