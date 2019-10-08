package com.me.mseotsanyana.mande.PPMER.BLL;
import android.content.Context;

import com.me.mseotsanyana.mande.PPMER.DAL.cOutcomeDBA;
import com.me.mseotsanyana.mande.PPMER.DAL.cOutcomeModel;

import java.util.ArrayList;
import java.util.List;

public class cOutcomeHandler extends cMapper<cOutcomeModel, cOutcomeDomain>{
    private cOutcomeDBA outcomeDBA;
    private Context context;

    public cOutcomeHandler(Context context) {
        outcomeDBA = new cOutcomeDBA(context);
        this.context = context;
    }
/*
    public boolean deleteAllOutcomes() {
        return outcomeDBA.deleteAllOutcomes();
    }

    public boolean addOutcome(cOutcomeDomain domain) {
        // mapp the business domain to the model
        cOutcomeModel model = this.DomainToModel(domain);
        return outcomeDBA.addOutcome(model);
    }

    public ArrayList<cOutcomeDomain> getOutcomeList() {
        List<cOutcomeModel> outcomeModel = outcomeDBA.getOutcomeList();

        ArrayList<cOutcomeDomain> outcomeDomain = new ArrayList<>();
        cOutcomeDomain domain;

        for(int i = 0; i < outcomeModel.size(); i++) {
            domain = this.ModelToDomain(outcomeModel.get(i));
            outcomeDomain.add(domain);
        }

        return outcomeDomain;
    }
*/
	@Override
	protected cOutcomeModel DomainToModel(cOutcomeDomain domain) {
        cOutcomeModel model = new cOutcomeModel();
/*
        model.setOutcomeID(domain.getOutcomeID());
        model.setOwnerID(domain.getOwnerID());
        model.setOutcomeName(domain.getOutcomeName());
        model.setOutcomeDescription(domain.getOutcomeDescription());
        model.setCreateDate(domain.getCreateDate());
*/
        return model;
	}

	@Override
	protected cOutcomeDomain ModelToDomain(cOutcomeModel model) {
		cOutcomeDomain domain = new cOutcomeDomain();
/*
		domain.setOutcomeID(model.getOutcomeID());
		domain.setOwnerID(model.getOwnerID());
		domain.setOutcomeName(model.getOutcomeName());
		domain.setOutcomeDescription(model.getOutcomeDescription());
		domain.setCreateDate(model.getCreateDate());
*/
		return domain;
	}
}
