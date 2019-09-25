package com.me.mseotsanyana.mande.PPMER.BLL;

import android.content.Context;

import com.me.mseotsanyana.mande.PPMER.DAL.cProjectOutcomeDBA;
import com.me.mseotsanyana.mande.PPMER.DAL.cProjectOutcomeModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mseotsanyana on 2017/06/26.
 */

public class cProjectOutcomeHandler extends cMapper<cProjectOutcomeModel, cProjectOutcomeDomain>{

    private cProjectOutcomeDBA projectOutcomeDBA;
    private Context context;

    public cProjectOutcomeHandler(Context context) {
        projectOutcomeDBA = new cProjectOutcomeDBA(context);
        this.context = context;
    }
    // business rules for adding a project
    public boolean addProjectOutcome(cProjectOutcomeDomain domain) {
        // mapp the business domain to the model
        cProjectOutcomeModel model = this.DomainToModel(domain);
        return projectOutcomeDBA.addProjectOutcome(model);
    }

    public ArrayList<cProjectOutcomeDomain> getProjectOutcomeList() {
        List<cProjectOutcomeModel> projectOutcomeModel = projectOutcomeDBA.getProjectOutcomeList();

        ArrayList<cProjectOutcomeDomain> projectOutcomeDomain = new ArrayList<>();
        cProjectOutcomeDomain domain;

        for(int i = 0; i < projectOutcomeModel.size(); i++) {
            domain = this.ModelToDomain(projectOutcomeModel.get(i));
            projectOutcomeDomain.add(domain);
        }

        return projectOutcomeDomain;
    }

    @Override
    protected cProjectOutcomeModel DomainToModel(cProjectOutcomeDomain domain) {
        cProjectOutcomeModel model = new cProjectOutcomeModel();

        model.setProjectID(domain.getProjectID());
        model.setOutcomeID(domain.getOutcomeID());
        model.setOwnerID(domain.getOwnerID());
        model.setCreateDate(domain.getCreateDate());

        return model;
    }

    @Override
    protected cProjectOutcomeDomain ModelToDomain(cProjectOutcomeModel model) {
        cProjectOutcomeDomain domain = new cProjectOutcomeDomain();

        domain.setProjectID(model.getProjectID());
        domain.setOutcomeID(model.getOutcomeID());
        domain.setOwnerID(model.getOwnerID());
        domain.setCreateDate(model.getCreateDate());

        return domain;
    }
}
