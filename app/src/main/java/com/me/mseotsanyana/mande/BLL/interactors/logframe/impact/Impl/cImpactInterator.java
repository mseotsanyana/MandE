package com.me.mseotsanyana.mande.BLL.interactors.logframe.impact.Impl;

import android.content.Context;

import com.me.mseotsanyana.mande.UTIL.BLL.cGoalDomain;
import com.me.mseotsanyana.mande.UTIL.BLL.cMapper;
import com.me.mseotsanyana.mande.DAL.ìmpl.logframe.cImpactImpl;
import com.me.mseotsanyana.mande.DAL.model.logframe.cImpactModel;

public class cImpactInterator extends cMapper<cImpactModel, cGoalDomain>
{
    private cImpactImpl goalDBA;
    private Context context;

    public cImpactInterator(Context context) {
        goalDBA = new cImpactImpl(context);
        this.setContext(context);
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
/***
    // business rules for adding a value from excel
    public boolean addGoalFromExcel(cGoalDomain domain) {
        cImpactModel model = this.DomainToModel(domain);
        return goalDBA.addGoalFromExcel(model);
    }

    // business rules for adding a value from excel
    public boolean addGoal(cGoalDomain domain) {
        cImpactModel model = this.DomainToModel(domain);
        return goalDBA.addGoal(model);
    }

    // business rules for deleting organization
    public boolean deleteAllGoals() {
        return goalDBA.deleteAllGoals();
    }

    public ArrayList<cGoalDomain> getGoalList(){
        List<cImpactModel> goalModelList = goalDBA.getGoalList();
        cImpactModel goalModel;

        ArrayList<cGoalDomain> goalDomain = new ArrayList<>();
        cGoalDomain domain;

        for(int i = 0; i < goalModelList.size(); i++){
            goalModel = goalModelList.get(i);
            domain = this.ModelToDomain(goalModel);
            goalDomain.add(domain);
        }

        return goalDomain;
    }

    public ArrayList<cTreeModel> getGoalTree(){
        List<cTreeModel> goalModelTree = goalDBA.getGoalTree();
        ArrayList<cTreeModel> goalDomainTree = new ArrayList<>();
        cGoalDomain goalDomain;


        for (int i = 0; i < goalModelTree.size(); i++) {
            int child = goalModelTree.get(i).getChildID();
            int parent = goalModelTree.get(i).getParentID();
            int type = goalModelTree.get(i).getType();

            cImpactModel goalModel = (cImpactModel) goalModelTree.get(i).getModelObject();

            goalDomain = ModelToDomain(goalModel);

            goalDomainTree.add(new cTreeModel(child, parent, type, goalDomain));
        }

        return goalDomainTree;
    }
 ***/
@Override
	protected cImpactModel DomainToModel(cGoalDomain domain) {
        cImpactModel model = new cImpactModel();
/*
        model.setGoalID(domain.getGoalID());
        model.setOrganizationID(domain.getOrganizationID());
        model.setOwnerID(domain.getOwnerID());
        model.setGoalName(domain.getGoalName());
        model.setGoalDescription(domain.getGoalDescription());
        model.setCreateDate(domain.getCreateDate());
*/
		return model;
	}

	@Override
	protected cGoalDomain ModelToDomain(cImpactModel model) {
        cGoalDomain domain = new cGoalDomain();
/*
        domain.setGoalID(model.getGoalID());
        domain.setOrganizationID(model.getOrganizationID());
        domain.setOwnerID(model.getOwnerID());
        domain.setGoalName(model.getGoalName());
        domain.setGoalDescription(model.getGoalDescription());
        domain.setCreateDate(model.getCreateDate());
*/
		return domain;
	}

}
