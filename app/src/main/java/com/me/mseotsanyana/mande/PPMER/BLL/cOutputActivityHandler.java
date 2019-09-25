package com.me.mseotsanyana.mande.PPMER.BLL;

import android.content.Context;

import com.me.mseotsanyana.mande.PPMER.DAL.cOutputActivityDBA;
import com.me.mseotsanyana.mande.PPMER.DAL.cOutputActivityModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mseotsanyana on 2017/06/26.
 */

public class cOutputActivityHandler extends cMapper<cOutputActivityModel, cOutputActivityDomain>{

    private cOutputActivityDBA outputActivityDBA;
    private Context context;

    public cOutputActivityHandler(Context context) {
        outputActivityDBA = new cOutputActivityDBA(context);
        this.context = context;
    }
    // business rules for adding a output activity
    public boolean addOutputActivity(cOutputActivityDomain domain) {
        // map the business domain to the model
        cOutputActivityModel model = this.DomainToModel(domain);
        return outputActivityDBA.addOuputActivity(model);
    }

    public ArrayList<cOutputActivityDomain> getOutputActivityList() {
        List<cOutputActivityModel> outputActivityModel = outputActivityDBA.getOutputActivityList();

        ArrayList<cOutputActivityDomain> outputActivityDomain = new ArrayList<>();
        cOutputActivityDomain domain;

        for(int i = 0; i < outputActivityModel.size(); i++) {
            domain = this.ModelToDomain(outputActivityModel.get(i));
            outputActivityDomain.add(domain);
        }

        return outputActivityDomain;
    }

    @Override
    protected cOutputActivityModel DomainToModel(cOutputActivityDomain domain) {
        cOutputActivityModel model = new cOutputActivityModel();

        model.setOutputID(domain.getOutputID());
        model.setActivityID(domain.getActivityID());
        model.setOwnerID(domain.getOwnerID());
        model.setCreateDate(domain.getCreateDate());

        return model;
    }

    @Override
    protected cOutputActivityDomain ModelToDomain(cOutputActivityModel model) {
        cOutputActivityDomain domain = new cOutputActivityDomain();

        domain.setOutputID(model.getOutputID());
        domain.setActivityID(model.getActivityID());
        domain.setOwnerID(model.getOwnerID());
        domain.setCreateDate(model.getCreateDate());

        return domain;
    }
}
