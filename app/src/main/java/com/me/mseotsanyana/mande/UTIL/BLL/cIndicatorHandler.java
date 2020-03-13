package com.me.mseotsanyana.mande.UTIL.BLL;

import android.content.Context;

import com.me.mseotsanyana.mande.UTIL.DAL.cIndicatorDBA;
import com.me.mseotsanyana.mande.DAL.model.monitor.cIndicatorModel;

public class cIndicatorHandler extends cMapper<cIndicatorModel, cIndicatorDomain>{
    private cIndicatorDBA indicatorDBA;
    private Context context;

    public cIndicatorHandler(Context context) {
        indicatorDBA = new cIndicatorDBA(context);
        this.context = context;
    }
/*
    public boolean deleteAllIndicators() {
        return indicatorDBA.deleteAllIndicators();
    }

    public boolean addIndicator(cIndicatorDomain domain) {
        // map the business domain to the model
        cIndicatorModel model = this.DomainToModel(domain);
        return indicatorDBA.addIndicator(model);
    }

    public ArrayList<cIndicatorDomain> getIndicatorList() {
        List<cIndicatorModel> indicatorModel = indicatorDBA.getIndicatorList();

        ArrayList<cIndicatorDomain> indicatorDomain = new ArrayList<>();
        cIndicatorDomain domain;

        for(int i = 0; i < indicatorModel.size(); i++) {
            domain = this.ModelToDomain(indicatorModel.get(i));
            indicatorDomain.add(domain);
        }

        return indicatorDomain;
    }
*/
    @Override
    protected cIndicatorModel DomainToModel(cIndicatorDomain domain) {
        cIndicatorModel model = new cIndicatorModel();
/*
        model.setIndicatorID(domain.getIndicatorID());
        model.setOwnerID(domain.getOwnerID());
        model.setStatusID(domain.getStatusID());
        model.setIndicatorName(domain.getIndicatorName());
        model.setIndicatorDescription(domain.getIndicatorDescription());
        model.setIndicatorType(domain.getIndicatorType());
        model.setCreateDate(domain.getCreateDate());
*/
        return model;
    }

    @Override
    protected cIndicatorDomain ModelToDomain(cIndicatorModel model) {
        cIndicatorDomain domain = new cIndicatorDomain();
/*
        domain.setIndicatorID(model.getIndicatorID());
        domain.setOwnerID(model.getOwnerID());
        domain.setStatusID(model.getStatusID());
        domain.setIndicatorName(model.getIndicatorName());
        domain.setIndicatorDescription(model.getIndicatorDescription());
        domain.setIndicatorType(model.getIndicatorType());
        domain.setCreateDate(model.getCreateDate());
*/
        return domain;
    }
}
