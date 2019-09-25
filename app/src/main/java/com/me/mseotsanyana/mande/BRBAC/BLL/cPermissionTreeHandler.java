package com.me.mseotsanyana.mande.BRBAC.BLL;


import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.me.mseotsanyana.mande.BRBAC.DAL.cEntityDBA;
import com.me.mseotsanyana.mande.BRBAC.DAL.cEntityModel;
import com.me.mseotsanyana.mande.BRBAC.DAL.cOperationDBA;
import com.me.mseotsanyana.mande.BRBAC.DAL.cOperationModel;
import com.me.mseotsanyana.mande.BRBAC.DAL.cPermissionModel;
import com.me.mseotsanyana.mande.BRBAC.DAL.cPermissionTreeModel;
import com.me.mseotsanyana.mande.BRBAC.DAL.cStatusDBA;
import com.me.mseotsanyana.mande.BRBAC.DAL.cStatusModel;
import com.me.mseotsanyana.mande.PPMER.BLL.cMapper;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mseotsanyana on 2017/08/28.
 */

public class cPermissionTreeHandler extends cMapper<cPermissionTreeModel, cPermissionTreeDomain> {
    final static private int _id = cSessionManager.PRIVILEGE;

    private Context context;
    private cSessionManager session;

    private cEntityHandler entityHandler;
    private cOperationHandler operationHandler;
    private cStatusHandler statusHandler;
    private cPermissionHandler permissionHandler;

    private cOperationDBA operationDBA;
    private cStatusDBA statusDBA;
    private cEntityDBA entityDBA;

    public cPermissionTreeHandler(Context context, cSessionManager session,
                                  cEntityHandler entityHandler,
                                  cOperationHandler operationHandler,
                                  cStatusHandler statusHandler,
                                  cPermissionHandler permissionHandler,
                                  cEntityDBA entityDBA,
                                  cOperationDBA operationDBA,
                                  cStatusDBA statusDBA) {
        this.context = context;
        this.session = session;

        this.entityHandler     = entityHandler;
        this.operationHandler  = operationHandler;
        this.statusHandler     = statusHandler;
        this.permissionHandler = permissionHandler;

        this.entityDBA    = entityDBA;
        this.operationDBA = operationDBA;
        this.statusDBA    = statusDBA;
    }

    HashMap<cOperationModel, HashMap<cStatusModel, cPermissionModel>> convertToModel(
            HashMap<cOperationDomain, HashMap<cStatusDomain, cPermissionDomain>> domainMap){

        HashMap<cOperationModel, HashMap<cStatusModel, cPermissionModel>> modelMap = new HashMap<>();

        for (Map.Entry<cOperationDomain, HashMap<cStatusDomain, cPermissionDomain>> opsEntry : domainMap.entrySet()) {
            cOperationDomain operationDomain = opsEntry.getKey();

            HashMap<cStatusModel, cPermissionModel> opsValues = new HashMap<>();
            for (Map.Entry<cStatusDomain, cPermissionDomain> statusEntry : opsEntry.getValue().entrySet()) {
                cStatusDomain statusDomain = statusEntry.getKey();
                cPermissionDomain permissionDomain = statusEntry.getValue();

                permissionDomain.setOperationDomain(operationDomain);
                permissionDomain.setStatusDomain(statusDomain);

                opsValues.put(statusHandler.DomainToModel(statusDomain),
                        permissionHandler.DomainToModel(permissionDomain));
            }
            modelMap.put(operationHandler.DomainToModel(operationDomain), opsValues);
        }

        return modelMap;
    }
    Gson gson = new Gson();
    HashMap<cOperationDomain, HashMap<cStatusDomain, cPermissionDomain>> convertToDomain(
            HashMap<cOperationModel, HashMap<cStatusModel, cPermissionModel>> modelMap){

        HashMap<cOperationDomain, HashMap<cStatusDomain, cPermissionDomain>> domainMap = new HashMap<>();


        for (Map.Entry<cOperationModel, HashMap<cStatusModel, cPermissionModel>> opsEntry : modelMap.entrySet()) {
            cOperationModel operationModel = operationDBA.getOperationByID(opsEntry.getKey().getOperationID());

            HashMap<cStatusDomain, cPermissionDomain> opsValues = new HashMap<>();
            for (Map.Entry<cStatusModel, cPermissionModel> statusEntry : opsEntry.getValue().entrySet()) {
                //Log.d("STS = ", "" + gson.toJson(statusEntry.getValue()));
                cStatusModel statusModel = statusDBA.getStatusByID(statusEntry.getKey().getStatusID());
                cPermissionModel permissionModel = statusEntry.getValue();
                permissionModel.setOperationModel(operationModel);
                permissionModel.setStatusModel(statusModel);
                //permissionModel.setCreatedDate(statusEntry.);

                //Log.d("STS = ", "" + gson.toJson(permissionModel));
                //String perm = gson.toJson(permissionModel);
                //Log.d("STATUS MODEL = ", "" + gson.toJson(statusHandler.ModelToDomain(statusModel)));

                opsValues.put(statusHandler.ModelToDomain(statusModel),
                        permissionHandler.ModelToDomain(permissionModel));

            }
            domainMap.put(operationHandler.ModelToDomain(operationModel), opsValues);
        }

        return domainMap;
    }

    @Override
    protected cPermissionTreeModel DomainToModel(cPermissionTreeDomain domain) {
        cPermissionTreeModel model = new cPermissionTreeModel();
        cEntityModel entityModel = new cEntityModel(entityDBA.getEntityByID(model.getEntityModel().getEntityID(),
                model.getEntityModel().getTypeID()));
        //String perm = gson.toJson(permissionModel);
        model.setOrganizationID(domain.getOrganizationID());
        model.setPrivilegeID(domain.getPrivilegeID());
        model.setEntityModel(entityModel);
        model.setPermModelDetails(convertToModel(domain.getPermDomainDetails()));

        return model;
    }

    @Override
    protected cPermissionTreeDomain ModelToDomain(cPermissionTreeModel model) {
        cPermissionTreeDomain domain = new cPermissionTreeDomain();
        cEntityDomain entityDomain = new cEntityDomain (
                entityHandler.getEntityByID(model.getEntityModel().getEntityID(),
                model.getEntityModel().getTypeID()));
        //Log.d("STS = ", "" + gson.toJson(entityDomain));

        domain.setOrganizationID(model.getOrganizationID());
        domain.setPrivilegeID(model.getPrivilegeID());
        domain.setEntityDomain(entityDomain);
        domain.setPermDomainDetails(convertToDomain(model.getPermModelDetails()));

        return domain;
    }
}