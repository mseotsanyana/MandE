package com.me.mseotsanyana.mande.BLL.interactors.logframe.activity.Impl;

import android.content.Context;

import com.me.mseotsanyana.mande.UTIL.BLL.cMapper;
import com.me.mseotsanyana.mande.BLL.domain.logframe.cActivityDomain;
import com.me.mseotsanyana.mande.DAL.ìmpl.logframe.cActivityImpl;
import com.me.mseotsanyana.mande.DAL.model.logframe.cActivityModel;

public class cActivityInterator extends cMapper<cActivityModel, cActivityDomain> {
    private cActivityImpl activityDBA;
    private Context context;

    public cActivityInterator(Context context) {
        activityDBA = new cActivityImpl(context);
        this.context = context;
    }
/*
    public boolean deleteAllActivities() {
        return activityDBA.deleteAllActivities();
    }

    public boolean addActivity(cActivityDomain domain) {
        // map the business domain to the model
        cActivityModel model = this.DomainToModel(domain);
        return activityDBA.addActivity(model);
    }

    public ArrayList<cActivityDomain> getActivityList() {
        List<cActivityModel> activityModel = activityDBA.getActivityList();

        ArrayList<cActivityDomain> activityDomain = new ArrayList<>();
        cActivityDomain domain;

        for(int i = 0; i < activityModel.size(); i++) {
            domain = this.ModelToDomain(activityModel.get(i));
            activityDomain.add(domain);
        }

        return activityDomain;
    }

*/

    @Override
    protected cActivityModel DomainToModel(cActivityDomain domain) {
        cActivityModel model = new cActivityModel();
/*
        model.setActivityID(domain.getActivityID());
        model.setOwnerID(domain.getOwnerID());
        model.setActivityName(domain.getActivityName());
        model.setActivityDescription(domain.getActivityDescription());
        model.setCreateDate(domain.getCreateDate());
*/
        return model;
    }

    @Override
    protected cActivityDomain ModelToDomain(cActivityModel model) {
        cActivityDomain domain = new cActivityDomain();
/*
        domain.setActivityID(model.getActivityID());
        domain.setOwnerID(model.getOwnerID());
        domain.setActivityName(model.getActivityName());
        domain.setActivityDescription(model.getActivityDescription());
        domain.setCreateDate(model.getCreateDate());
*/
        return domain;
    }
}
