package com.me.mseotsanyana.mande.BLL.interactors.logframe.outcome.Impl;
import android.content.Context;

import com.me.mseotsanyana.mande.BLL.domain.logframe.cOutcomeDomain;
import com.me.mseotsanyana.mande.DAL.ìmpl.logframe.cOutcomeRepositoryImpl;
import com.me.mseotsanyana.mande.DAL.model.logframe.cOutcomeModel;

public class cOutcomeInterator  {
    private cOutcomeRepositoryImpl outcomeDBA;
    private Context context;

    public cOutcomeInterator(Context context) {
        outcomeDBA = new cOutcomeRepositoryImpl(context);
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
