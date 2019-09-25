package com.me.mseotsanyana.mande.PPMER.BLL;

import android.content.Context;

import com.me.mseotsanyana.mande.PPMER.DAL.cSpecificAimDBA;
import com.me.mseotsanyana.mande.PPMER.DAL.cSpecificAimModel;

import java.util.ArrayList;
import java.util.List;

public class cSpecificAimHandler extends cMapper<cSpecificAimModel, cSpecificAimDomain>
{
    private cSpecificAimDBA specificAimDBA;
    private Context context;

    public cSpecificAimHandler(Context context) {
        specificAimDBA = new cSpecificAimDBA(context);
        this.context = context;
    }

    // business rules for adding a project
    public boolean addSpecificAim(cSpecificAimDomain domain) {
        // mapp the business domain to the model
        cSpecificAimModel model = this.DomainToModel(domain);
        return specificAimDBA.addSpecificAim(model);
    }

    // business rules for adding a specific aim
    public boolean addSpecificAimFromExcel(cSpecificAimDomain domain) {
        cSpecificAimModel model = this.DomainToModel(domain);
        return specificAimDBA.addSpecificAimFromExcel(model);
    }

    // business rules for deleting organization
    public boolean deleteAllSpecificAims() {
        return specificAimDBA.deleteAllSpecificAims();
    }

    public ArrayList<cSpecificAimDomain> getSpecificAimList() {
        List<cSpecificAimModel> specificAimModels = specificAimDBA.getSpecificAimList();

        ArrayList<cSpecificAimDomain> specificAimDomains = new ArrayList<>();
        cSpecificAimDomain domain;

        for(int i = 0; i < specificAimModels.size(); i++) {
            domain = this.ModelToDomain(specificAimModels.get(i));
            specificAimDomains.add(domain);
        }

        return specificAimDomains;
    }

    @Override
    protected cSpecificAimDomain ModelToDomain(cSpecificAimModel model) {
        cSpecificAimDomain domain = new cSpecificAimDomain();

        domain.setProjectID(model.getProjectID());
        domain.setOverallAimID(model.getOverallAimID());
        domain.setOwnerID(model.getOwnerID());
        domain.setSpecificAimName(model.getSpecificAimName());
        domain.setSpecificAimDescription(model.getSpecificAimDescription());
        domain.setCreateDate(model.getCreateDate());

        return domain;
    }

    @Override
    protected cSpecificAimModel DomainToModel(cSpecificAimDomain domain) {
        cSpecificAimModel model = new cSpecificAimModel();

        model.setProjectID(domain.getProjectID());
        model.setOverallAimID(domain.getOverallAimID());
        model.setOwnerID(domain.getOwnerID());
        model.setSpecificAimName(domain.getSpecificAimName());
        model.setSpecificAimDescription(domain.getSpecificAimDescription());
        model.setCreateDate(domain.getCreateDate());

        return model;
    }

}
