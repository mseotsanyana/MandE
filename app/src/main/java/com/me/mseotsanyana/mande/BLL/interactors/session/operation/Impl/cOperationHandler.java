package com.me.mseotsanyana.mande.BLL.interactors.session.operation.Impl;


import android.content.Context;

import com.me.mseotsanyana.mande.BLL.domain.session.cOperationDomain;
import com.me.mseotsanyana.mande.DAL.ìmpl.session.cOperationRepositoryImpl;
import com.me.mseotsanyana.mande.DAL.model.session.cOperationModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mseotsanyana on 2017/08/28.
 */

public class cOperationHandler {
    private cOperationRepositoryImpl operationDBA;
    private Context context;

    public cOperationHandler(Context context) {
        operationDBA = new cOperationRepositoryImpl(context);
        this.context = context;
    }

    public cOperationHandler() {

    }

    public boolean deleteOperations() {
        return operationDBA.deleteOperations();
    }

    public boolean addOperationFromExcel(cOperationDomain domain) {
        // map the business domain to the model
        cOperationModel model = this.DomainToModel(domain);
        return operationDBA.addOperationFromExcel(model);
    }

    public boolean addOperation(cOperationDomain domain) {
        // map the business domain to the model
        cOperationModel model = this.DomainToModel(domain);
        return operationDBA.addOperation(model);
    }

    public cOperationDomain getOperationByID(int operationID) {
        cOperationModel model = operationDBA.getOperationByID(operationID);
        cOperationDomain domain = ModelToDomain(model);
        return domain;
    }

    public ArrayList<cOperationDomain> getOperationList() {
        List<cOperationModel> operationModel = operationDBA.getOperationList();

        ArrayList<cOperationDomain> operationDomain = new ArrayList<>();
        cOperationDomain domain;

        for(int i = 0; i < operationModel.size(); i++) {
            domain = this.ModelToDomain(operationModel.get(i));
            operationDomain.add(domain);
        }

        return operationDomain;
    }

    //@Override
    protected cOperationModel DomainToModel(cOperationDomain domain) {
        cOperationModel model = new cOperationModel();

        model.setOperationID(domain.getOperationID());
        model.setOwnerID(domain.getOwnerID());
        model.setGroupBITS(domain.getGroupBITS());
        model.setPermsBITS(domain.getPermsBITS());
        model.setStatusBITS(domain.getStatusBITS());
        model.setName(domain.getName());
        model.setDescription(domain.getDescription());
        model.setCreatedDate(domain.getCreatedDate());
        model.setModifiedDate(domain.getModifiedDate());
        model.setSyncedDate(domain.getSyncedDate());

        return model;
    }

    //@Override
    protected cOperationDomain ModelToDomain(cOperationModel model) {
        cOperationDomain domain = new cOperationDomain();

        domain.setOperationID(model.getOperationID());
        domain.setOwnerID(model.getOwnerID());
        domain.setGroupBITS(model.getGroupBITS());
        domain.setPermsBITS(model.getPermsBITS());
        domain.setStatusBITS(model.getStatusBITS());
        domain.setName(model.getName());
        domain.setDescription(model.getDescription());
        domain.setCreatedDate(model.getCreatedDate());
        domain.setModifiedDate(model.getModifiedDate());
        domain.setSyncedDate(model.getSyncedDate());

        return domain;
    }
}
