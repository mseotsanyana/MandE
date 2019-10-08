package com.me.mseotsanyana.mande.PPMER.BLL;

import android.content.Context;

import com.me.mseotsanyana.mande.PPMER.DAL.cActivityDBA;
import com.me.mseotsanyana.mande.PPMER.DAL.cActivityModel;

import java.util.ArrayList;
import java.util.List;

public class cActivityHandler extends cMapper<cActivityModel, cActivityDomain> {
    private cActivityDBA activityDBA;
    private Context context;

    public cActivityHandler(Context context) {
        activityDBA = new cActivityDBA(context);
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
