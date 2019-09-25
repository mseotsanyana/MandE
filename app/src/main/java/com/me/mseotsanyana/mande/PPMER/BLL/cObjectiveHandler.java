package com.me.mseotsanyana.mande.PPMER.BLL;

import android.content.Context;

import com.me.mseotsanyana.mande.PPMER.DAL.cObjectiveDBA;
import com.me.mseotsanyana.mande.PPMER.DAL.cObjectiveModel;

import java.util.ArrayList;
import java.util.List;

public class cObjectiveHandler extends cMapper<cObjectiveModel, cObjectiveDomain>{
    private cObjectiveDBA objectiveDBA;
    private Context context;

    public cObjectiveHandler(Context context) {
        objectiveDBA = new cObjectiveDBA(context);
        this.context = context;
    }

    public boolean deleteAllObjectives() {
        return objectiveDBA.deleteAllObjectives();
    }

    public boolean addObjective(cObjectiveDomain domain) {
        // map the business domain to the model
        cObjectiveModel model = this.DomainToModel(domain);
        return objectiveDBA.addObjective(model);
    }

    public ArrayList<cObjectiveDomain> getObjectiveList() {
        List<cObjectiveModel> objectiveModel = objectiveDBA.getObjectiveList();

        ArrayList<cObjectiveDomain> objectiveDomain = new ArrayList<>();
        cObjectiveDomain domain;

        for(int i = 0; i < objectiveModel.size(); i++) {
            domain = this.ModelToDomain(objectiveModel.get(i));
            objectiveDomain.add(domain);
        }

        return objectiveDomain;
    }

    @Override
    protected cObjectiveModel DomainToModel(cObjectiveDomain domain) {
        cObjectiveModel model = new cObjectiveModel();

        model.setObjectiveID(domain.getObjectiveID());
        model.setProjectID(domain.getProjectID());
        model.setOwnerID(domain.getOwnerID());
        model.setObjectiveName(domain.getObjectiveName());
        model.setObjectiveDescription(domain.getObjectiveDescription());
        model.setCreateDate(domain.getCreateDate());

        return model;
    }

    @Override
    protected cObjectiveDomain ModelToDomain(cObjectiveModel model) {
        cObjectiveDomain domain = new cObjectiveDomain();

        domain.setObjectiveID(model.getObjectiveID());
        domain.setProjectID(model.getProjectID());
        domain.setOwnerID(model.getOwnerID());
        domain.setObjectiveName(model.getObjectiveName());
        domain.setObjectiveDescription(model.getObjectiveDescription());
        domain.setCreateDate(model.getCreateDate());

        return domain;
    }
}
