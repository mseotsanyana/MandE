package com.me.mseotsanyana.mande.BRBAC.BLL;

import android.content.Context;

import com.me.mseotsanyana.mande.BRBAC.DAL.cEntityModel;
import com.me.mseotsanyana.mande.BRBAC.DAL.cOperationModel;
import com.me.mseotsanyana.mande.BRBAC.DAL.cPrivilegeDBA;
import com.me.mseotsanyana.mande.BRBAC.DAL.cPrivilegeModel;
import com.me.mseotsanyana.mande.BRBAC.DAL.cStatusModel;
import com.me.mseotsanyana.mande.UTILITY.BLL.cMapper;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class cPrivilegeHandler  extends cMapper<cPrivilegeModel, cPrivilegeDomain> {
    private static String TAG = cPrivilegeHandler.class.getSimpleName();

    private cPrivilegeDBA privilegeDBA;
    private Context context;

    private int entityBITS, operationBITS;

    public cPrivilegeHandler(Context context, cSessionManager session) {
        privilegeDBA = new cPrivilegeDBA(context);
        this.context = context;

        /** 1. ENTITY SECTION **/

        // entity bits of all entities that are accessible
        //entityBITS = session.loadEntityBITS(session.loadUserID(),
        //        session.loadOrganizationID(), cSessionManager.types[0]);

        /** 2. OPERATION SECTION **/

        // operations associated to ENTITY entity
        operationBITS = session.loadOperationBITS(cSessionManager.PRIVILEGE,
                cSessionManager.types[0]);
    }

    /* ################################### CREATE ACTIONS ################################### */


    /* ################################### READ ACTIONS ################################### */

    public Set<cPrivilegeDomain> getPrivilegeDomainSet(int userID, int orgID,
                                             int primaryRole, int secondaryRoles) {
        int statusBITS = 0;//FIXME
        Set<cPrivilegeModel> privilegeModelSet = privilegeDBA.getPrivilegeModelSet(
                userID, orgID, primaryRole, secondaryRoles, operationBITS, statusBITS);

        Set<cPrivilegeDomain> privilegeDomainSet = new HashSet<>();
        cPrivilegeDomain domain;

        for(cPrivilegeModel privilegeModel: privilegeModelSet) {
            domain = this.ModelToDomain(privilegeModel);
            privilegeDomainSet.add(domain);
        }

        return privilegeDomainSet;
    }

    HashMap<cEntityModel, Set<cOperationModel>> convertToModelMap(
            HashMap<cEntityDomain, Set<cOperationDomain>> domainMap){

        HashMap<cEntityModel, Set<cOperationModel>> modelMap = new HashMap<>();

        for (Map.Entry<cEntityDomain, Set<cOperationDomain>> entityEntry : domainMap.entrySet()) {
            cEntityDomain entityDomain = entityEntry.getKey();

            cEntityHandler entityHandler = new cEntityHandler();
            cOperationHandler operationHandler = new cOperationHandler();

            Set<cOperationModel> operationModelSet = new HashSet<>();
            for (cOperationDomain operationDomain : entityEntry.getValue()) {
                operationModelSet.add(operationHandler.DomainToModel(operationDomain));
            }
            modelMap.put(entityHandler.DomainToModel(entityDomain), operationModelSet);
        }

        return modelMap;
    }

    HashMap<cEntityDomain, Set<cOperationDomain>> convertToDomainMap(
            HashMap<cEntityModel, Set<cOperationModel>> modelMap){

        HashMap<cEntityDomain, Set<cOperationDomain>> domainMap = new HashMap<>();

        for (Map.Entry<cEntityModel, Set<cOperationModel>> entityEntry : modelMap.entrySet()) {
            cEntityModel entityModel = entityEntry.getKey();

            //Gson gson = new Gson();
            //Log.d(TAG,"ENTITY = "+gson.toJson(entityModel));

            cEntityHandler entityHandler = new cEntityHandler();
            cOperationHandler operationHandler = new cOperationHandler();

            Set<cOperationDomain> operationDomainSet = new HashSet<>();
            for (cOperationModel operationModel : entityEntry.getValue()) {
                operationDomainSet.add(operationHandler.ModelToDomain(operationModel));
            }
            domainMap.put(entityHandler.ModelToDomain(entityModel), operationDomainSet);
        }

        return domainMap;
    }

    Set<cStatusModel> convertToModelSet(Set<cStatusDomain> statusDomainSet){

        Set<cStatusModel> modelSet = new HashSet<>();
        cStatusHandler statusHandler = new cStatusHandler();

        for (cStatusDomain statusDomain : statusDomainSet) {
            cStatusModel statusModel = statusHandler.DomainToModel(statusDomain);
            modelSet.add(statusModel);
        }

        return modelSet;
    }

    Set<cStatusDomain> convertToDomainSet(Set<cStatusModel> statusModelSet){
        Set<cStatusDomain> domainSet = new HashSet<>();
        cStatusHandler statusHandler = new cStatusHandler();

        for (cStatusModel statusModel : statusModelSet) {
            cStatusDomain menuDomain = statusHandler.ModelToDomain(statusModel);
            domainSet.add(menuDomain);
        }

        return domainSet;
    }


    /* ################################### UPDATE ACTIONS ################################### */


    /* ################################### DELETE ACTIONS ################################### */

    // business rules for deleting values
    public boolean deletePrivileges() {
        return privilegeDBA.deletePrivileges();
    }

    /* ################################### SYNC ACTIONS ################################### */




    @Override
    protected cPrivilegeModel DomainToModel(cPrivilegeDomain domain) {
        cPrivilegeModel model = new cPrivilegeModel();

        model.setPrivilegeID(domain.getPrivilegeID());
        model.setServerID(domain.getServerID());
        model.setOwnerID(domain.getOwnerID());
        model.setOrgID(domain.getOrgID());
        model.setGroupBITS(domain.getGroupBITS());
        model.setPermsBITS(domain.getPermsBITS());
        model.setStatusBITS(domain.getStatusBITS());
        model.setName(domain.getName());
        model.setDescription(domain.getDescription());
        model.setCreatedDate(domain.getCreatedDate());
        model.setModifiedDate(domain.getModifiedDate());
        model.setSyncedDate(domain.getSyncedDate());

        if(!domain.getPermDomainMap().isEmpty()) {
            model.setPermModelMap(convertToModelMap(domain.getPermDomainMap()));
        }

        if(!domain.getStatusDomainSet().isEmpty()) {
            model.setStatusModelSet(convertToModelSet(domain.getStatusDomainSet()));
        }

        return model;
    }

    @Override
    protected cPrivilegeDomain ModelToDomain(cPrivilegeModel model) {
        cPrivilegeDomain domain = new cPrivilegeDomain();

        domain.setPrivilegeID(model.getPrivilegeID());
        domain.setServerID(model.getServerID());
        domain.setOwnerID(model.getOwnerID());
        domain.setOrgID(model.getOrgID());
        domain.setGroupBITS(model.getGroupBITS());
        domain.setPermsBITS(model.getPermsBITS());
        domain.setStatusBITS(model.getStatusBITS());
        domain.setName(model.getName());
        domain.setDescription(model.getDescription());
        domain.setCreatedDate(model.getCreatedDate());
        domain.setModifiedDate(model.getModifiedDate());
        domain.setSyncedDate(model.getSyncedDate());

        if(!model.getPermModelMap().isEmpty()) {
            domain.setPermDomainMap(convertToDomainMap(model.getPermModelMap()));
        }

        if(!model.getStatusModelSet().isEmpty())
            domain.setStatusDomainSet(convertToDomainSet(model.getStatusModelSet()));

        return domain;
    }
}
