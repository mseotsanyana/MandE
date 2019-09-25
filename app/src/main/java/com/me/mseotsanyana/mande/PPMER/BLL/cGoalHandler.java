package com.me.mseotsanyana.mande.PPMER.BLL;

import android.content.Context;

import com.me.mseotsanyana.mande.PPMER.DAL.cGoalDBA;
import com.me.mseotsanyana.mande.PPMER.DAL.cGoalModel;
import com.me.mseotsanyana.treeadapterlibrary.cTreeModel;

import java.util.ArrayList;
import java.util.List;

public class cGoalHandler extends cMapper<cGoalModel, cGoalDomain>
{
    private cGoalDBA goalDBA;
    private Context context;

    public cGoalHandler(Context context) {
        goalDBA = new cGoalDBA(context);
        this.setContext(context);
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    // business rules for adding a value from excel
    public boolean addGoalFromExcel(cGoalDomain domain) {
        cGoalModel model = this.DomainToModel(domain);
        return goalDBA.addGoalFromExcel(model);
    }

    // business rules for adding a value from excel
    public boolean addGoal(cGoalDomain domain) {
        cGoalModel model = this.DomainToModel(domain);
        return goalDBA.addGoal(model);
    }

    // business rules for deleting organization
    public boolean deleteAllGoals() {
        return goalDBA.deleteAllGoals();
    }

    public ArrayList<cGoalDomain> getGoalList(){
        List<cGoalModel> goalModelList = goalDBA.getGoalList();
        cGoalModel goalModel;

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

            cGoalModel goalModel = (cGoalModel) goalModelTree.get(i).getModelObject();

            goalDomain = ModelToDomain(goalModel);

            goalDomainTree.add(new cTreeModel(child, parent, type, goalDomain));
        }

        return goalDomainTree;
    }

    @Override
	protected cGoalModel DomainToModel(cGoalDomain domain) {
        cGoalModel model = new cGoalModel();

        model.setGoalID(domain.getGoalID());
        model.setOrganizationID(domain.getOrganizationID());
        model.setOwnerID(domain.getOwnerID());
        model.setGoalName(domain.getGoalName());
        model.setGoalDescription(domain.getGoalDescription());
        model.setCreateDate(domain.getCreateDate());

		return model;
	}

	@Override
	protected cGoalDomain ModelToDomain(cGoalModel model) {
        cGoalDomain domain = new cGoalDomain();

        domain.setGoalID(model.getGoalID());
        domain.setOrganizationID(model.getOrganizationID());
        domain.setOwnerID(model.getOwnerID());
        domain.setGoalName(model.getGoalName());
        domain.setGoalDescription(model.getGoalDescription());
        domain.setCreateDate(model.getCreateDate());

		return domain;
	}
}
