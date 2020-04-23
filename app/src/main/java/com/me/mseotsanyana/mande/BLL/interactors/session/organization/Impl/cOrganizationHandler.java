package com.me.mseotsanyana.mande.BLL.interactors.session.organization.Impl;

import android.content.Context;
import android.database.Cursor;

import com.me.mseotsanyana.mande.BLL.domain.session.cOrganizationDomain;
import com.me.mseotsanyana.mande.BLL.interactors.session.value.Impl.cValueHandler;
import com.me.mseotsanyana.mande.DAL.storage.preference.cBitwise;
import com.me.mseotsanyana.mande.DAL.ìmpl.session.cOrganizationRepositoryImpl;
import com.me.mseotsanyana.mande.DAL.model.session.cOrganizationModel;
import com.me.mseotsanyana.mande.DAL.ìmpl.session.cValueRepositoryImpl;
import com.me.mseotsanyana.mande.DAL.model.session.cValueModel;
import com.me.mseotsanyana.mande.UTIL.COM.cEvent;
import com.me.mseotsanyana.mande.UTIL.COM.cGlobalBus;
import com.me.mseotsanyana.mande.UTIL.BLL.cMapper;
import com.me.mseotsanyana.treeadapterlibrary.cTreeModel;

import java.util.ArrayList;
import java.util.List;


public class cOrganizationHandler extends cMapper<cOrganizationModel, cOrganizationDomain> {
    //final static private int _id = cSessionManager.ORGANIZATION;
    private static String TAG = cOrganizationHandler.class.getSimpleName();

    private Context context;
    //private cSessionManager session;

    private cOrganizationRepositoryImpl organizationDBA;
    private cValueRepositoryImpl valueDBA;
    private cValueHandler valueHandler;

    private int entityBITS, operationBITS;

    public cOrganizationHandler(){}

    public cOrganizationHandler(Context context) {
        organizationDBA = new cOrganizationRepositoryImpl(context);
        valueDBA = new cValueRepositoryImpl(context);
        this.context = context;
        //this.session = session;

        /** 1. ENTITY SECTION **/

        // entity bits of all entities that are accessible
        //entityBITS = session.loadEntityBITS(session.loadUserID(),
        //        session.loadOrganizationID(), cSessionManager.types[0]);

        /** 2. OPERATION SECTION **/

        // operations associated to ENTITY entity
        operationBITS = 0;//session.loadOperationBITS(cSessionManager.ORGANIZATION, cSessionManager.types[0]);
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    // business rules for adding a project
    public boolean addOrganization(cOrganizationDomain domain) {
        // map the business domain to the model
        cOrganizationModel model = this.DomainToModel(domain);
        return organizationDBA.addOrganization(model);
    }

    // business rules for adding a project
    public boolean addOrganizationFromExcel(cOrganizationDomain domain) {
        cOrganizationModel model = this.DomainToModel(domain);
        return true;//organizationDBA.addOrganizationFromExcel(model);
    }

    // business rules for deleting organization
    public boolean deleteOrganizations() {
        return organizationDBA.deleteOrganizations();
    }

    public ArrayList<cOrganizationDomain> getOrganizationList(int userID, int orgID,
                                              int primaryRole, int secondaryRoles) {
        // statuses of read operation associated with ORGANIZATION entity
        int statusBITS = 0;//session.loadStatusBITS(cSessionManager.ORGANIZATION, cSessionManager.types[0]);

        ArrayList<cOrganizationDomain> organizationDomains = new ArrayList<>();
/*
        Log.d(TAG, "ENTITY ID = " + cSessionManager.ORGANIZATION +
                ", ENTITY BITS = " + entityBITS + ", USER ID = " + userID +
                ", ORG. ID = " + orgID + ", PRIMARY ROLE = " + primaryRole +
                ", SECONDARY ROLES = " + secondaryRoles + ", OPERATIONS = " + operationBITS +
                ", STATUSES = " + statusBITS);
*/
        if(!cBitwise.isEntity(/*cSessionManager.ORGANIZATION*/0, entityBITS)){
            cEvent.cHandlerFragmentMessage handlerFragmentMessage =
                    new cEvent.cHandlerFragmentMessage("Insufficient privileges to access entity. " +
                            "Please contact your system administrator");
            cGlobalBus.getBus().post(handlerFragmentMessage);
        }
        else if (cBitwise.isRead(operationBITS, statusBITS)) {
            List<cOrganizationModel> organizationModels = organizationDBA.getOrganizationList(
                    userID, primaryRole, secondaryRoles, operationBITS, statusBITS);

            cOrganizationDomain domain;

            for (int i = 0; i < organizationModels.size(); i++) {
                domain = this.ModelToDomain(organizationModels.get(i));
                organizationDomains.add(domain);
            }
        } else {
            cEvent.cHandlerFragmentMessage handlerFragmentMessage =
                    new cEvent.cHandlerFragmentMessage("Insufficient privileges. " +
                            "Please contact your system administrator");
            cGlobalBus.getBus().post(handlerFragmentMessage);
        }

        return organizationDomains;
    }



    // business rules for the adapter
    public ArrayList<cOrganizationDomain> getOrganizationList() {
        List<cOrganizationModel> organizationModelList = organizationDBA.getOrganizationList();
        List<cValueModel> valueModelList = new ArrayList<>();
        cOrganizationModel organizationModel;

        ArrayList<cOrganizationDomain> organizationDomain = new ArrayList<>();
        cOrganizationDomain domain;

        for(int i = 0; i < organizationModelList.size(); i++) {
            organizationModel = organizationModelList.get(i);
            int organizationID = organizationModel.getOrganizationID();
            valueModelList = valueDBA.getValuesByID(organizationID);
            //organizationModel.setValues(valueModelList);

            domain = this.ModelToDomain(organizationModel);

            organizationDomain.add(domain);
        }

        return organizationDomain;
    }

    public Cursor getAllOrganizations() {
        return organizationDBA.getAllOrganizations();
    }

    public cOrganizationDomain getOrganizationByID(int organizationID) {
        cOrganizationModel model = organizationDBA.getOrganizationByID(organizationID);
        cOrganizationDomain domain = this.ModelToDomain(model);
        return domain;
    }

    public ArrayList<cTreeModel> getOrganizationTree(){
        List<cTreeModel> organizationModelTree = organizationDBA.getOrganizationTree();
        ArrayList<cTreeModel> organizationDomainTree = new ArrayList<>();
        cOrganizationDomain organizationDomain;


        for (int i = 0; i < organizationModelTree.size(); i++) {
            int child = organizationModelTree.get(i).getChildID();
            int parent = organizationModelTree.get(i).getParentID();
            int type = organizationModelTree.get(i).getType();

            cOrganizationModel organizationModel = (cOrganizationModel) organizationModelTree.get(i).getModelObject();

            organizationDomain = ModelToDomain(organizationModel);

            organizationDomainTree.add(new cTreeModel(child, parent, type, organizationDomain));
        }

        return organizationDomainTree;
    }

    @Override
    protected cOrganizationDomain ModelToDomain(cOrganizationModel model) {
        cOrganizationDomain domain = new cOrganizationDomain();

        domain.setOrganizationID(model.getOrganizationID());
        domain.setServerID(model.getServerID());
        domain.setOwnerID(model.getOwnerID());
        domain.setOrgID(model.getOrgID());
        domain.setGroupBITS(model.getGroupBITS());
        domain.setPermsBITS(model.getPermsBITS());
        domain.setStatusBITS(model.getStatusBITS());
        domain.setName(model.getName());
        domain.setPhone(model.getPhone());
        domain.setFax(model.getFax());
        domain.setVision(model.getVision());
        domain.setMission(model.getMission());
        domain.setEmail(model.getEmail());
        domain.setWebsite(model.getWebsite());
        domain.setCreatedDate(model.getCreatedDate());
        domain.setModifiedDate(model.getModifiedDate());
        domain.setSyncedDate(model.getSyncedDate());

        return domain;
    }

    @Override
    protected cOrganizationModel DomainToModel(cOrganizationDomain domain) {
        cOrganizationModel model = new cOrganizationModel();

        model.setOrganizationID(domain.getOrganizationID());
        model.setServerID(domain.getServerID());
        model.setOwnerID(domain.getOwnerID());
        model.setOrgID(domain.getOrgID());
        model.setName(domain.getName());
        model.setGroupBITS(domain.getGroupBITS());
        model.setPermsBITS(domain.getPermsBITS());
        model.setStatusBITS(domain.getStatusBITS());
        model.setPhone(domain.getPhone());
        model.setFax(domain.getFax());
        model.setVision(domain.getVision());
        model.setMission(domain.getMission());
        model.setEmail(domain.getEmail());
        model.setWebsite(domain.getWebsite());
        model.setCreatedDate(domain.getCreatedDate());
        model.setModifiedDate(domain.getModifiedDate());
        model.setSyncedDate(domain.getSyncedDate());

        return model;
    }
}
