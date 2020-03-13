package com.me.mseotsanyana.mande.UTIL.BLL;

import android.content.Context;

import com.me.mseotsanyana.mande.UTIL.DAL.cOutcomeOutputDBA;
import com.me.mseotsanyana.mande.UTIL.DAL.cOutcomeOutputModel;

/**
 * Created by mseotsanyana on 2017/06/26.
 */

public class cOutcomeOutputHandler extends cMapper<cOutcomeOutputModel, cOutcomeOutputDomain>{

    private cOutcomeOutputDBA outcomeOutputDBA;
    private Context context;

    public cOutcomeOutputHandler(Context context) {
        outcomeOutputDBA = new cOutcomeOutputDBA(context);
        this.context = context;
    }
    /*
    // business rules for adding a outcome output
    public boolean addOutcomeOutput(cOutcomeOutputDomain domain) {
        // map the business domain to the model
        cOutcomeOutputModel model = this.DomainToModel(domain);
        return outcomeOutputDBA.addOutcomeOutput(model);
    }

    public ArrayList<cOutcomeOutputDomain> getOutcomeOutputList() {
        List<cOutcomeOutputModel> outcomeOutputModel = outcomeOutputDBA.getOutcomeOutputList();

        ArrayList<cOutcomeOutputDomain> outcomeOutputDomain = new ArrayList<>();
        cOutcomeOutputDomain domain;

        for(int i = 0; i < outcomeOutputModel.size(); i++) {
            domain = this.ModelToDomain(outcomeOutputModel.get(i));
            outcomeOutputDomain.add(domain);
        }

        return outcomeOutputDomain;
    }
*/
    @Override
    protected cOutcomeOutputModel DomainToModel(cOutcomeOutputDomain domain) {
        cOutcomeOutputModel model = new cOutcomeOutputModel();

        model.setOutcomeID(domain.getOutcomeID());
        model.setOutputID(domain.getOutputID());
        model.setOwnerID(domain.getOwnerID());
        model.setCreateDate(domain.getCreateDate());

        return model;
    }

    @Override
    protected cOutcomeOutputDomain ModelToDomain(cOutcomeOutputModel model) {
        cOutcomeOutputDomain domain = new cOutcomeOutputDomain();

        domain.setOutcomeID(model.getOutcomeID());
        domain.setOutputID(model.getOutputID());
        domain.setOwnerID(model.getOwnerID());
        domain.setCreateDate(model.getCreateDate());

        return domain;
    }
}
