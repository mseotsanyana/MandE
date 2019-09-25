package com.me.mseotsanyana.mande.PPMER.BLL;

import android.content.Context;

import com.me.mseotsanyana.mande.PPMER.DAL.cOutputDBA;
import com.me.mseotsanyana.mande.PPMER.DAL.cOutputModel;

import java.util.ArrayList;
import java.util.List;

public class cOutputHandler extends cMapper<cOutputModel, cOutputDomain>{
    private cOutputDBA outputDBA;
    private Context context;

    public cOutputHandler(Context context) {
        outputDBA = new cOutputDBA(context);
        this.context = context;
    }

    public boolean deleteAllOutputs() {
        return outputDBA.deleteAllOutputs();
    }

    public boolean addOutput(cOutputDomain domain) {
        // map the business domain to the model
        cOutputModel model = this.DomainToModel(domain);
        return outputDBA.addOutput(model);
    }

    public ArrayList<cOutputDomain> getOutputList() {
        List<cOutputModel> outputModel = outputDBA.getOutputList();

        ArrayList<cOutputDomain> outputDomain = new ArrayList<>();
        cOutputDomain domain;

        for(int i = 0; i < outputModel.size(); i++) {
            domain = this.ModelToDomain(outputModel.get(i));
            outputDomain.add(domain);
        }

        return outputDomain;
    }

    @Override
    protected cOutputModel DomainToModel(cOutputDomain domain) {
        cOutputModel model = new cOutputModel();

        model.setOutputID(domain.getOutputID());
        model.setObjectiveID(domain.getObjectiveID());
        model.setOwnerID(domain.getOwnerID());
        model.setOutputName(domain.getOutputName());
        model.setOutputDescription(domain.getOutputDescription());
        model.setCreateDate(domain.getCreateDate());

        return model;
    }

    @Override
    protected cOutputDomain ModelToDomain(cOutputModel model) {
        cOutputDomain domain = new cOutputDomain();

        domain.setOutputID(model.getOutputID());
        domain.setObjectiveID(model.getObjectiveID());
        domain.setOwnerID(model.getOwnerID());
        domain.setOutputName(model.getOutputName());
        domain.setOutputDescription(model.getOutputDescription());
        domain.setCreateDate(model.getCreateDate());

        return domain;
    }
}
