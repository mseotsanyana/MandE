package com.me.mseotsanyana.mande.BRBAC.BLL;

import android.content.Context;

import com.me.mseotsanyana.mande.BRBAC.BLL.cValueDomain;
import com.me.mseotsanyana.mande.BRBAC.DAL.cValueDBA;
import com.me.mseotsanyana.mande.BRBAC.DAL.cValueModel;
import com.me.mseotsanyana.mande.PPMER.BLL.cMapper;

import java.util.ArrayList;
import java.util.List;


public class cValueHandler extends cMapper<cValueModel, cValueDomain>
{
    private cValueDBA valueDBA;
    private Context context;

    public cValueHandler(Context context) {
        valueDBA = new cValueDBA(context);
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
    public boolean deleteAllValues()
    {
        return valueDBA.deleteAllValues();
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

    @Override
    protected cValueDomain ModelToDomain(cValueModel model) {
        cValueDomain domain = new cValueDomain();

        domain.setValueID(model.getValueID());
        domain.setOrganizationID(model.getOrganizationID());
        domain.setOwnerID(model.getOwnerID());
        domain.setValueName(model.getValueName());
        domain.setCreateDate(model.getCreateDate());

        return domain;
    }

    @Override
    protected cValueModel DomainToModel(cValueDomain domain) {
        cValueModel model = new cValueModel();

        model.setValueID(domain.getValueID());
        model.setOrganizationID(domain.getOrganizationID());
        model.setOwnerID(domain.getOwnerID());
        model.setValueName(domain.getValueName());
        model.setCreateDate(domain.getCreateDate());

        return model;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
