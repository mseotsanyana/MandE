package com.me.mseotsanyana.mande.BLL.interactors.session.value.Impl;

import android.content.Context;

import com.me.mseotsanyana.mande.BLL.domain.session.cValueDomain;
import com.me.mseotsanyana.mande.DAL.ìmpl.session.cValueRepositoryImpl;
import com.me.mseotsanyana.mande.DAL.model.session.cValueModel;

import java.util.ArrayList;
import java.util.List;


public class cValueHandler
{
    private cValueRepositoryImpl valueDBA;
    private Context context;

    public cValueHandler(Context context) {
        valueDBA = new cValueRepositoryImpl(context);
        this.setContext(context);
    }

    // business rules for adding a value
    public boolean addValue(cValueDomain domain) {
        // map the business domain to the model
        cValueModel model = this.DomainToModel(domain);
        return valueDBA.addValue(model);
    }

    // business rules for adding a value from excel
    public boolean addValueFromExcel(cValueDomain domain) {
        cValueModel model = this.DomainToModel(domain);
        return valueDBA.addValueFromExcel(model);
    }

    // business rules for deleting values
    public boolean deleteValues() {
        return valueDBA.deleteValues();
    }

    // business rules for the adapter
    public List<cValueDomain> getListOfValues() {
        List<cValueModel> valueModel = valueDBA.getListOfValues();
        List<cValueDomain> valueDomain = new ArrayList<>();
        cValueDomain domain;

        for(int i = 0; i < valueModel.size(); i++) {
            domain = this.ModelToDomain(valueModel.get(i));
            valueDomain.add(domain);
        }

        return valueDomain;
    }

    //@Override
    protected cValueDomain ModelToDomain(cValueModel model) {
        cValueDomain domain = new cValueDomain();

        //domain.setValueID(model.getValueID());
        //domain.setOrganizationID(model.getOrganizationID());
        //domain.setOwnerID(model.getOwnerID());
        domain.setValueName(model.getName());
        domain.setCreateDate(model.getCreatedDate());

        return domain;
    }

    //@Override
    protected cValueModel DomainToModel(cValueDomain domain) {
        cValueModel model = new cValueModel();

        model.setValueID(domain.getValueID());
        model.setOrganizationID(domain.getOrganizationID());
        model.setOwnerID(domain.getOwnerID());
        model.setName(domain.getValueName());
        model.setCreatedDate(domain.getCreateDate());

        return model;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
