package com.me.mseotsanyana.mande.BRBAC.BLL;

import android.content.Context;

import com.me.mseotsanyana.mande.BRBAC.BLL.cStatusDomain;
import com.me.mseotsanyana.mande.BRBAC.DAL.cStatusDBA;
import com.me.mseotsanyana.mande.BRBAC.DAL.cStatusModel;
import com.me.mseotsanyana.mande.PPMER.BLL.cMapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mseotsanyana on 2017/08/28.
 */

public class cStatusHandler extends cMapper<cStatusModel, cStatusDomain> {
    private cStatusDBA statusDBA;
    private Context context;

    public cStatusHandler(Context context) {
        statusDBA = new cStatusDBA(context);
        this.context = context;
    }

    public boolean deleteAllStatuses() {
        return statusDBA.deleteAllStatuses();
    }

    public boolean addStatusFromExcel(cStatusDomain domain) {
        // map the business domain to the model
        cStatusModel model = this.DomainToModel(domain);
        return statusDBA.addStatusFromExcel(model);
    }

    public boolean addStatus(cStatusDomain domain) {
        // map the business domain to the model
        cStatusModel model = this.DomainToModel(domain);
        return statusDBA.addStatus(model);
    }

    public cStatusDomain getStatusByID(int statusID) {
        cStatusModel model = statusDBA.getStatusByID(statusID);
        cStatusDomain domain = ModelToDomain(model);
        return domain;
    }


    public ArrayList<cStatusDomain> getStatusList() {
        List<cStatusModel> statusModel = statusDBA.getStatusList();

        ArrayList<cStatusDomain> statusDomain = new ArrayList<>();
        cStatusDomain domain;

        for(int i = 0; i < statusModel.size(); i++) {
            domain = this.ModelToDomain(statusModel.get(i));
            statusDomain.add(domain);
        }
        return statusDomain;
    }

    @Override
    protected cStatusModel DomainToModel(cStatusDomain domain) {
        cStatusModel model = new cStatusModel();

        model.setStatusID(domain.getStatusID());
        model.setOwnerID(domain.getOwnerID());
        model.setGroupBITS(domain.getGroupBITS());
        model.setPermsBITS(domain.getPermsBITS());
        model.setStatusBITS(domain.getStatusBITS());
        model.setName(domain.getName());
        model.setDescription(domain.getDescription());
        model.setCreateDate(domain.getCreateDate());

        return model;
    }

    @Override
    protected cStatusDomain ModelToDomain(cStatusModel model) {
        cStatusDomain domain = new cStatusDomain();

        domain.setStatusID(model.getStatusID());
        domain.setOwnerID(model.getOwnerID());
        domain.setGroupBITS(model.getGroupBITS());
        domain.setPermsBITS(model.getPermsBITS());
        domain.setStatusBITS(model.getStatusBITS());
        domain.setName(model.getName());
        domain.setDescription(model.getDescription());
        domain.setCreateDate(model.getCreateDate());

        return domain;
    }
}
