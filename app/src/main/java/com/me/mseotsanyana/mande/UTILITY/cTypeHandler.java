package com.me.mseotsanyana.mande.UTILITY;

import android.content.Context;

import com.me.mseotsanyana.mande.PPMER.BLL.cMapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mseotsanyana on 2017/08/28.
 */

public class cTypeHandler extends cMapper<cTypeModel, cTypeDomain> {
    private cTypeDBA typeDBA;
    private Context context;

    public cTypeHandler(Context context) {
        typeDBA = new cTypeDBA(context);
        this.context = context;
    }

    public boolean deleteAllTypes() {
        return typeDBA.deleteAllTypes();
    }

    public boolean addTypeFromExcel(cTypeDomain domain) {
        // map the business domain to the model
        cTypeModel model = this.DomainToModel(domain);
        return typeDBA.addTypeFromExcel(model);
    }

    public boolean addType(cTypeDomain domain) {
        // map the business domain to the model
        cTypeModel model = this.DomainToModel(domain);
        return typeDBA.addType(model);
    }

    public ArrayList<cTypeDomain> getTypeList() {
        List<cTypeModel> typeModel = typeDBA.getTypeList();

        ArrayList<cTypeDomain> typeDomain = new ArrayList<>();
        cTypeDomain domain;

        for(int i = 0; i < typeModel.size(); i++) {
            domain = this.ModelToDomain(typeModel.get(i));
            typeDomain.add(domain);
        }
        return typeDomain;
    }

    @Override
    protected cTypeModel DomainToModel(cTypeDomain domain) {
        cTypeModel model = new cTypeModel();

        model.setTypeID(domain.getTypeID());
        model.setOwnerID(domain.getOwnerID());
        model.setGroupBITS(domain.getGroupBITS());
        model.setPermsBITS(domain.getPermsBITS());
        model.setTypeBITS(domain.getTypeBITS());
        model.setStatusBITS(domain.getStatusBITS());
        model.setName(domain.getName());
        model.setDescription(domain.getDescription());
        model.setCreateDate(domain.getCreateDate());

        return model;    }

    @Override
    protected cTypeDomain ModelToDomain(cTypeModel model) {
        cTypeDomain domain = new cTypeDomain();

        domain.setTypeID(model.getTypeID());
        domain.setOwnerID(model.getOwnerID());
        domain.setGroupBITS(model.getGroupBITS());
        domain.setPermsBITS(model.getPermsBITS());
        domain.setTypeBITS(model.getTypeBITS());
        domain.setStatusBITS(model.getStatusBITS());
        domain.setName(model.getName());
        domain.setDescription(model.getDescription());
        domain.setCreateDate(model.getCreateDate());

        return domain;
    }
}
