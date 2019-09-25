package com.me.mseotsanyana.mande.BRBAC.BLL;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.me.mseotsanyana.mande.BRBAC.DAL.cOrganizationDBA;
import com.me.mseotsanyana.mande.BRBAC.DAL.cOrganizationModel;
import com.me.mseotsanyana.mande.BRBAC.DAL.cValueDBA;
import com.me.mseotsanyana.mande.BRBAC.DAL.cValueModel;
import com.me.mseotsanyana.mande.COM.cEvent;
import com.me.mseotsanyana.mande.COM.cGlobalBus;
import com.me.mseotsanyana.mande.PPMER.BLL.cMapper;
import com.me.mseotsanyana.mande.Util.cBitwisePermission;
import com.me.mseotsanyana.treeadapterlibrary.cTreeModel;

import java.util.ArrayList;
import java.util.List;


public class cOrganizationHandler extends cMapper<cOrganizationModel, cOrganizationDomain> {
    //final static private int _id = cSessionManager.ORGANIZATION;
    private static String TAG = cOrganizationHandler.class.getSimpleName();

    private Context context;
    private cSessionManager session;

    private cOrganizationDBA organizationDBA;
    private cValueDBA valueDBA;
    private cValueHandler valueHandler;

    private int entityBITS, operationBITS;

    public cOrganizationHandler(Context context, cSessionManager session) {
        organizationDBA = new cOrganizationDBA(context);
        valueDBA = new cValueDBA(context);
        this.context = context;
        this.session = session;

        /** 1. ENTITY SECTION **/

        // entity bits of all entities that are accessible
        entityBITS = session.loadEntityBITS(session.loadUserID(),
                session.loadOrganizationID(), cSessionManager.types[0]);

        /** 2. OPERATION SECTION **/

        // operations associated to ENTITY entity
        operationBITS = session.loadOperationBITS(session.loadUserID(),
                session.loadOrganizationID(), cSessionManager.ORGANIZATION,
                cSessionManager.types[0]);
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
        return organizationDBA.addOrganizationFromExcel(model);
    }

    // business rules for deleting organization
    public boolean deleteAllOrganizations() {
        return organizationDBA.deleteAllOrganizations();
    }

    public ArrayList<cOrganizationDomain> getOrganizationList(int userID, int orgID,
                                              int primaryRole, int secondaryRoles) {
        // statuses of read operation associated with ORGANIZATION entity
        int statusBITS = session.loadStatusBITS(userID, orgID,
                cSessionManager.ORGANIZATION, cSessionManager.types[0], cSessionManager.READ);

        ArrayList<cOrganizationDomain> organizationDomains = new ArrayList<>();

        Log.d(TAG, "ENTITY ID = " + cSessionManager.ORGANIZATION +
                ", ENTITY BITS = " + entityBITS + ", USER ID = " + userID +
                ", ORG. ID = " + orgID + ", PRIMARY ROLE = " + primaryRole +
                ", SECONDARY ROLES = " + secondaryRoles + ", OPERATIONS = " + operationBITS +
                ", STATUSES = " + statusBITS);

        if(!cBitwisePermission.isEntity(cSessionManager.ORGANIZATION, entityBITS)){
            cEvent.cHandlerFragmentMessage handlerFragmentMessage =
                    new cEvent.cHandlerFragmentMessage("Insufficient privileges to access entity. " +
                            "Please contact your system administrator");
            cGlobalBus.getBus().post(handlerFragmentMessage);
        }
        else if (cBitwisePermission.isRead(operationBITS, statusBITS)) {
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
        domain.setAddressID(model.getAddressID());
        domain.setOwnerID(model.getOwnerID());
        domain.setOrganizationName(model.getOrganizationName());
        domain.setTelephone(model.getTelephone());
        domain.setFax(model.getFax());
        domain.setVision(model.getVision());
        domain.setMission(model.getMission());
        domain.setEmailAddress(model.getEmailAddress());
        domain.setWebsite(model.getWebsite());
        domain.setCreateDate(model.getCreateDate());

        return domain;
    }

    @Override
    protected cOrganizationModel DomainToModel(cOrganizationDomain domain) {
        cOrganizationModel model = new cOrganizationModel();

        model.setOrganizationID(domain.getOrganizationID());
        model.setAddressID(domain.getAddressID());
        model.setOwnerID(domain.getOwnerID());
        model.setOrganizationName(domain.getOrganizationName());
        model.setTelephone(domain.getTelephone());
        model.setFax(domain.getFax());
        model.setVision(domain.getVision());
        model.setMission(domain.getMission());
        model.setEmailAddress(domain.getEmailAddress());
        model.setWebsite(domain.getWebsite());
        model.setCreateDate(domain.getCreateDate());

        return model;
    }
}
