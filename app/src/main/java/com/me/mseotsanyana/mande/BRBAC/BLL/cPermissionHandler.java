package com.me.mseotsanyana.mande.BRBAC.BLL;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.me.mseotsanyana.mande.BRBAC.DAL.cEntityDBA;
import com.me.mseotsanyana.mande.BRBAC.DAL.cEntityModel;
import com.me.mseotsanyana.mande.BRBAC.DAL.cOperationDBA;
import com.me.mseotsanyana.mande.BRBAC.DAL.cOperationModel;
import com.me.mseotsanyana.mande.BRBAC.DAL.cPermissionDBA;
import com.me.mseotsanyana.mande.BRBAC.DAL.cPermissionModel;
import com.me.mseotsanyana.mande.BRBAC.DAL.cPermissionTreeModel;
import com.me.mseotsanyana.mande.BRBAC.DAL.cPrivilegeDBA;
import com.me.mseotsanyana.mande.BRBAC.DAL.cPrivilegeModel;
import com.me.mseotsanyana.mande.BRBAC.DAL.cStatusDBA;
import com.me.mseotsanyana.mande.BRBAC.DAL.cStatusModel;
import com.me.mseotsanyana.mande.COM.cEntityBITS;
import com.me.mseotsanyana.mande.COM.cEvent;
import com.me.mseotsanyana.mande.COM.cGlobalBus;
import com.me.mseotsanyana.mande.COM.cOperationBITS;
import com.me.mseotsanyana.mande.COM.cStatusBITS;
import com.me.mseotsanyana.mande.PPMER.BLL.cMapper;
import com.me.mseotsanyana.mande.Util.cBitwisePermission;
import com.me.mseotsanyana.treeadapterlibrary.cTreeModel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Created by mseotsanyana on 2017/08/29.
 */

public class cPermissionHandler extends cMapper<cPermissionModel, cPermissionDomain> {
    final static private int _id = cSessionManager.PERMISSION;
    private static String TAG = cPermissionHandler.class.getSimpleName();

    private Context context;
    private cSessionManager session;

    private cPrivilegeDBA privilegeDBA;
    private cEntityDBA entityDBA;
    private cOperationDBA operationDBA;
    private cStatusDBA statusDBA;
    private cPermissionDBA permissionDBA;

    private cPrivilegeHandler privilegeHandler;
    private cEntityHandler entityHandler;
    private cOperationHandler operationHandler;
    private cStatusHandler statusHandler;

    private cPermissionTreeHandler permissionTreeHandler;


    private int entityBITS, operationBITS;

    Gson gson = new Gson();

    class cEntityIdentity {
        int id;
        int type;

        @Override
        public boolean equals(Object obj) {
            return !super.equals(obj);
        }

        @Override
        public int hashCode() {
            return getId();
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }

    public cPermissionHandler(Context context, cSessionManager session) {
        this.context = context;
        this.session = session;

        privilegeDBA = new cPrivilegeDBA(context);
        entityDBA = new cEntityDBA(context);
        operationDBA = new cOperationDBA(context);
        statusDBA = new cStatusDBA(context);
        permissionDBA = new cPermissionDBA(context);

        privilegeHandler = new cPrivilegeHandler(context, session);
        entityHandler = new cEntityHandler(context);
        operationHandler = new cOperationHandler(context);
        statusHandler = new cStatusHandler(context);

        permissionTreeHandler = new cPermissionTreeHandler(context, session,
                entityHandler,operationHandler,statusHandler,this,
                entityDBA, operationDBA, statusDBA);


        /** 1. ENTITY SECTION **/

        // entity bits of all entities that are accessible
        entityBITS = session.loadEntityBITS(session.loadUserID(),
                session.loadOrganizationID(),
                cSessionManager.types[0]);

        /** 2. OPERATION SECTION **/

        // operations associated to ENTITY entity
        int entityOpsBITS = session.loadOperationBITS(session.loadUserID(),
                session.loadOrganizationID(), cSessionManager.ENTITY,
                cSessionManager.types[0]);
        // operations associated to OPERATION entity
        int operationOpsBITS = session.loadOperationBITS(session.loadUserID(),
                session.loadOrganizationID(), cSessionManager.OPERATION,
                cSessionManager.types[0]);
        // operations associated to STATUS entity
        int statusOpsBITS = session.loadOperationBITS(session.loadUserID(),
                session.loadOrganizationID(), cSessionManager.STATUS,
                cSessionManager.types[0]);

        // operations that are common in all entities
        operationBITS = entityOpsBITS & operationOpsBITS & statusOpsBITS;

    }

    public boolean deleteAllPermissions() {
        return permissionDBA.deleteAllPermissions();
    }

    // business rules for adding permissions
    public boolean addPermissionFromExcel(cPermissionDomain domain) {
        // map the business domain to the model.
        cPermissionModel model = this.DomainToModel(domain);
        return permissionDBA.addPermissionFromExcel(model);
    }

    // business rules for adding a user group
    public boolean addPermission(cPermissionDomain domain) {
        Log.d(TAG,"PERMISSION DOMAIN = (" + gson.toJson(domain) + ")");
        // map the business domain to the model
        cPermissionModel model = this.DomainToModel(domain);
        Log.d(TAG,"PERMISSION MODEL = (" + gson.toJson(model) + ")");
        return permissionDBA.addPermission(model);
    }


    public boolean updatePermission(cPermissionDomain domain) {
        // map the business domain to the model
        cPermissionModel model = this.DomainToModel(domain);
        return permissionDBA.updatePermission(model);
    }

    public boolean deletePermission(cPermissionDomain domain) {
        // map the business domain to the model
        Log.d("PERMISSION DOMAIN = ", "(" + gson.toJson(domain) + ")");

        cPermissionModel model = this.DomainToModel(domain);
        return permissionDBA.deletePermission(model);
    }

    public boolean deletePermissionByIDs(int privilegeID, int entityID, int typeID, int operationID, int statusID) {
        return permissionDBA.deletePermissionByIDs(privilegeID, entityID, typeID, operationID, statusID);
    }

    public boolean deletePermissionByEntityIDs(int organizationID, int privilegeID, int entityID, int typeID) {
        return permissionDBA.deletePermissionByEntityIDs(organizationID, privilegeID, entityID, typeID);
    }


    public ArrayList<cPermissionDomain> getPermissionList(int userID, int primaryRole, int secondaryRoles) {
        /** 3. STATUS SECTION **/
        // statuses of operations associated with ENTITY entity
        int entityOpsStatusBITS = session.loadStatusBITS(session.loadUserID(),
                session.loadOrganizationID(), cSessionManager.ENTITY,
                cSessionManager.types[0], cSessionManager.READ);
        // statuses of operations associated with OPERATION entity
        int operationOpsStatusBITS = session.loadStatusBITS(session.loadUserID(),
                session.loadOrganizationID(), cSessionManager.OPERATION,
                cSessionManager.types[0], cSessionManager.READ);
        // statuses of operations associated with STATUS entity
        int statusOpsStatusBITS = session.loadStatusBITS(session.loadUserID(),
                session.loadOrganizationID(), cSessionManager.STATUS,
                cSessionManager.types[0], cSessionManager.READ);

        // statuses from owner read, group read and other read from PERMISSION
        // auxiliary entity
        int statusBITS = entityOpsStatusBITS & operationOpsStatusBITS & statusOpsStatusBITS;

        // musk the statuses with ACTIVATED statuses (i.e., read only activated records)
        //statusBITS &= session.statuses[2];

        List<cPermissionModel> permissionModel = permissionDBA.getPermissionList(
                userID, primaryRole, secondaryRoles, operationBITS, statusBITS);

        ArrayList<cPermissionDomain> permissionDomains = new ArrayList<>();
        cPermissionDomain domain;

        for (int i = 0; i < permissionModel.size(); i++) {
            domain = this.ModelToDomain(permissionModel.get(i));
            permissionDomains.add(domain);
        }

        return permissionDomains;
    }

    public ArrayList<cPermissionDomain> getPermissionBITSByPrivilegeID(int privilegeID) {
        List<cPermissionModel> permissionModel = permissionDBA.getPermissionBITSByPrivilegeID(privilegeID);

        ArrayList<cPermissionDomain> permissionDomains = new ArrayList<>();
        cPermissionDomain domain;

        for (int i = 0; i < permissionModel.size(); i++) {
            domain = this.ModelToDomain(permissionModel.get(i));
            permissionDomains.add(domain);
        }

        return permissionDomains;
    }

    public ArrayList<cOperationDomain> getEntityOperations(int privilegeID, int entityID, int typeID) {
        List<cOperationModel> operationModels = permissionDBA.getEntityOperations(privilegeID, entityID, typeID);

        ArrayList<cOperationDomain> operationDomains = new ArrayList<>();
        cOperationDomain domain;

        for (int i = 0; i < operationModels.size(); i++) {
            domain = operationHandler.ModelToDomain(operationModels.get(i));
            operationDomains.add(domain);
        }

        return operationDomains;
    }

    public ArrayList<cPermissionDomain> getPermissionsByPrivilegeID(int privilegeID) {
        ArrayList<cPermissionModel> permissionModels = permissionDBA.getPermissionsByPrivilegeID(privilegeID);


        ArrayList<cPermissionDomain> permissionDomains = new ArrayList<>();
        cPermissionDomain domain;

        for (int i = 0; i < permissionModels.size(); i++) {
            /*Log.d(" PERMISSION MODELS ", ""+
                    permissionModels.get(i).getRoleID()+","+
                    permissionModels.get(i).getEntityID()+","+
                    permissionModels.get(i).getOperationID()+","+
                    permissionModels.get(i).getTypeID()+","+
                    permissionModels.get(i).getStatusID());*/

            domain = ModelToDomain(permissionModels.get(i));
            permissionDomains.add(domain);
        }

        return permissionDomains;

    }
/*
    public ArrayList<cRoleDomain> getRolesByUserID(int userID){
        List<cRoleModel> roleModels = permissionDBA.getRolesByUserID(userID);

        ArrayList<cRoleDomain> roleDomains = new ArrayList<>();
        cRoleDomain domain;

        for(int i = 0; i < roleModels.size(); i++) {
            domain = roleHandler.ModelToDomain(roleModels.get(i));
            roleDomains.add(domain);
        }

        return roleDomains;

    }

    public ArrayList<Integer> getRoleIDsByUserID(int userID){
        return permissionDBA.getRoleIDsByUserID(userID);
    }
*/

    public ArrayList<cTreeModel> getPermissionTree(int userID, int orgID,
                                                   int primaryRole, int secondaryRoles) {
        /** 3. STATUS SECTION **/
        // statuses of operations associated with ENTITY entity
        int entityOpsStatusBITS = session.loadStatusBITS(userID, orgID,
                cSessionManager.ENTITY, cSessionManager.types[0], cSessionManager.READ);

        Log.d(TAG, "USER ID = " + userID + ", ORG ID = " + orgID +
                ", ENTITY ID = " + cSessionManager.ENTITY +
                ", ENTITY TYPE = " + cSessionManager.types[0] +
                ", OPERATION BITS = " + cSessionManager.READ);

        // statuses of operations associated with OPERATION entity
        int operationOpsStatusBITS = session.loadStatusBITS(userID, orgID,
                cSessionManager.OPERATION, cSessionManager.types[0], cSessionManager.READ);
        // statuses of operations associated with STATUS entity
        int statusOpsStatusBITS = session.loadStatusBITS(userID, orgID,
                cSessionManager.STATUS, cSessionManager.types[0], cSessionManager.READ);

        // statuses from owner read, group read and other read from PERMISSION
        // auxiliary entity
        int statusBITS = entityOpsStatusBITS;// & operationOpsStatusBITS & statusOpsStatusBITS;

        //FIXME
        // musk the statuses with ACTIVATED statuses (i.e., read only activated records)
        //statusBITS &= session.statuses[2];


        //Log.d(TAG, "ENTITY ID = " + _id + ", ENTITY BITS = " + entityBITS +
        //        ", PRIMARY ROLE = " + primaryRole + ", SECONDARY ROLES = " + secondaryRoles +
        //        ", OPERATIONS = " + operationBITS + ", STATUSES = " + statusBITS);


        ArrayList<cTreeModel> treeModel = new ArrayList<>();
        if (!cBitwisePermission.isEntity(
                _id,       /* entity id for PERMISSION auxiliary entity */
                entityBITS /* entity bits of accessible entities */)) {

            Log.d("PERM = ", "not yet implemented ");
        } else if (cBitwisePermission.isRead(
                operationBITS,              /* operation bits of allowed operations */
                statusBITS                  /* status bits a read operation */)) {

            List<cPrivilegeModel> privilegeModels = privilegeDBA.getPrivilegeList(
                    userID, orgID, primaryRole, secondaryRoles, operationBITS, statusBITS);


            List<cPermissionTreeModel> permissionTreeModels = permissionDBA.getPermissionTreeDetails(
                    userID, primaryRole, secondaryRoles, operationBITS, statusBITS);

            Gson gson = new Gson();
            //String perm = gson.toJson(permissionTreeModels.get(0).getPermModelDetails());
            //Log.d("PERM = ", "" + perm);


            //List<cPermissionModel> permissionModels = permissionDBA.getDistinctPermissionList();


            cPrivilegeDomain privilegeDomain;
            cPermissionTreeDomain permissionTreeDomain;

            int parentIndex = 0, childIndex = 0;

            for (int i = 0; i < privilegeModels.size(); i++) {
                privilegeDomain = privilegeHandler.ModelToDomain(
                        privilegeDBA.getPrivilegeByID(privilegeModels.get(i).getOrganizationID(),
                                privilegeModels.get(i).getPrivilegeID()));
                //Log.d("PRIVILEDGE DOMAIN = ", gson.toJson(privilegeDomain));
                if (privilegeDomain != null) {
                    treeModel.add(new cTreeModel(parentIndex, -1, 0, privilegeDomain));
                    //Log.d("TREE PARENT = ", "(" + parentIndex + " , " + -1 + ")");
                    childIndex = parentIndex;
                    for (int j = 0; j < permissionTreeModels.size(); j++) {
                        int permissionPrivilegeID = permissionTreeModels.get(j).getPrivilegeID();
                        if (privilegeDomain.getPrivilegeID() == permissionPrivilegeID) {
                            childIndex = childIndex + 1;
                            permissionTreeDomain = permissionTreeHandler.ModelToDomain(permissionTreeModels.get(j));
                            //Log.d("TREE CHILDREN = ", gson.toJson(permissionTreeDomain));

                            treeModel.add(new cTreeModel(childIndex, parentIndex, 1, permissionTreeDomain));
                            //Log.d("TREE CHILDREN = ", "("+childIndex+" , "+parentIndex+") = "+permissionDomain.getEntityID()+"->"+permissionDomain.getTypeID());
                        }
                    }
                    parentIndex = childIndex + 1;
                }
            }

        } else {
            cEvent.cHandlerFragmentMessage handlerFragmentMessage =
                    new cEvent.cHandlerFragmentMessage("Insufficient privileges. " +
                            "Please contact your system administrator");
            cGlobalBus.getBus().post(handlerFragmentMessage);

            return null;
        }


        return treeModel;
    }

    // create a tree model for permissions
    public ArrayList<cTreeModel> getPermissionTree_old() {
        List<cPrivilegeModel> privilegeModels = null;//privilegeDBA.getPrivilegeList();

        ArrayList<cTreeModel> treeModel = new ArrayList<>();

        int parentIndex = 0, childIndex = 0;

        for (int i = 0; i < privilegeModels.size(); i++) {
            // get list of permission records for privilege
            ArrayList<cPermissionModel> permissionModels = permissionDBA.getPermissionsByPrivilegeID(
                    privilegeModels.get(i).getPrivilegeID());

            cPrivilegeDomain privilegeDomain = privilegeHandler.ModelToDomain(
                    privilegeDBA.getPrivilegeByID(privilegeModels.get(i).getOrganizationID(),
                            privilegeModels.get(i).getPrivilegeID()));

            treeModel.add(new cTreeModel(parentIndex, -1, 0, privilegeDomain));
            //Log.d("TREE PARENT = ", "(" + parentIndex + " , " + -1 + ")");
            childIndex = parentIndex;

            Gson gson = new Gson();
            //Log.d("PERMISSION MODEL = ", "(" + gson.toJson(permissionModels) + ")");

            HashSet<cEntityIdentity> entityModelSet = new HashSet<cEntityIdentity>();
            for (int j = 0; j < permissionModels.size(); j++) {
                cEntityIdentity entityIdentity = new cEntityIdentity();
                //entityIdentity.setId(permissionModels.get(j).getEntityID());
                //entityIdentity.setType(permissionModels.get(j).getTypeID());
                // get an entity under the permission record
                //Log.d("ENTITYID, TYPID = ", "(" + permissionModels.get(j).getEntityID() +
                //        " , " + permissionModels.get(j).getTypeID() + ")");

                //cEntityModel entityModel = entityDBA.getEntityByID(
                //        permissionModels.get(j).getEntityID(),
                //        permissionModels.get(j).getTypeID());

                //Log.d("ENTITY MODEL = ", "(" + gson.toJson(entityModel) + ")");

                entityModelSet.add(entityIdentity);

            }

            //Log.d("SET = ", "(" + gson.toJson(entityModelSet) + ")");

            ArrayList<cEntityIdentity> entityIdentities = new ArrayList<cEntityIdentity>(entityModelSet);

            for (int k = 0; k < entityIdentities.size(); k++) {
                childIndex = childIndex + 1;
                cEntityModel entityModel = entityDBA.getEntityByID(
                        entityIdentities.get(k).getId(),
                        entityIdentities.get(k).getType());

                cEntityDomain entityDomain = entityHandler.ModelToDomain(entityModel);
                treeModel.add(new cTreeModel(childIndex, parentIndex, 1, entityDomain));
                //Log.d("TREE PARENT = ", "(" + parentIndex + " , " + childIndex + " Name = " + gson.toJson(entityModels.get(k)) + ")");

                /*
                // get an operation under the permission record
                cOperationModel operationModel = operationDBA.getOperationByID(
                        permissionModels.get(j).getOperationID());
                // get a status under the permission record
                cStatusModel statusModel = statusDBA.getStatusByID(
                        permissionModels.get(j).getStatusID());*/

            }
            parentIndex = childIndex + 1;
        }

        return treeModel;
    }

    public List<cEntityBITS> getEntitiesBIT(ArrayList<cPrivilegeDomain> privilegeDomains) {
        ArrayList<cPrivilegeModel> privilegeModels = new ArrayList<>();
        for (int i = 0; i < privilegeDomains.size(); i++) {
            cPrivilegeModel model = privilegeHandler.DomainToModel(privilegeDomains.get(i));
            privilegeModels.add(model);
        }

        return permissionDBA.getEntitiesBIT(privilegeModels);
    }

    public List<cOperationBITS> getOperationsBIT(ArrayList<cPrivilegeDomain> privilegeDomains) {
        ArrayList<cPrivilegeModel> privilegeModels = new ArrayList<>();
        for (int i = 0; i < privilegeDomains.size(); i++) {
            cPrivilegeModel model = privilegeHandler.DomainToModel(privilegeDomains.get(i));
            privilegeModels.add(model);
        }

        return permissionDBA.getOperationsBIT(privilegeModels);
    }

    public List<cStatusBITS> getStatusesBIT(ArrayList<cPrivilegeDomain> privilegeDomains) {
        ArrayList<cPrivilegeModel> privilegeModels = new ArrayList<>();
        for (int i = 0; i < privilegeDomains.size(); i++) {
            cPrivilegeModel model = privilegeHandler.DomainToModel(privilegeDomains.get(i));
            privilegeModels.add(model);
        }

        return permissionDBA.getStatusesBIT(privilegeModels);
    }

    @Override
    protected cPermissionModel DomainToModel(cPermissionDomain domain) {
        cPermissionModel model = new cPermissionModel();

        model.setOrganizationID(domain.getOrganizationID());
        cPrivilegeModel privilegeModel = privilegeHandler.DomainToModel(domain.getPrivilegeDomain());
        cEntityModel entityModel = entityHandler.DomainToModel(domain.getEntityDomain());
        cOperationModel operationModel = operationHandler.DomainToModel(domain.getOperationDomain());
        cStatusModel statusModel = statusHandler.DomainToModel(domain.getStatusDomain());

        model.setPrivilegeModel(privilegeModel);
        model.setEntityModel(entityModel);
        model.setOperationModel(operationModel);
        model.setStatusModel(statusModel);

        model.setOwnerID(domain.getOwnerID());
        model.setOrgID(domain.getOrgID());
        model.setGroupBITS(domain.getGroupBITS());
        model.setPermsBITS(domain.getPermsBITS());
        model.setStatusBITS(domain.getStatusBITS());
        model.setCreatedDate(domain.getCreatedDate());
        model.setModifiedDate(domain.getModifiedDate());
        model.setSyncedDate(domain.getSyncedDate());

        return model;
    }

    @Override
    protected cPermissionDomain ModelToDomain(cPermissionModel model) {
        cPermissionDomain domain = new cPermissionDomain();

        domain.setOrganizationID(model.getOrganizationID());
        cPrivilegeDomain privilegeDomain = privilegeHandler.ModelToDomain(model.getPrivilegeModel());
        cEntityDomain entityDomain = entityHandler.ModelToDomain(model.getEntityModel());
        cOperationDomain operationDomain = operationHandler.ModelToDomain(model.getOperationModel());
        cStatusDomain statusDomain = statusHandler.ModelToDomain(model.getStatusModel());

        domain.setPrivilegeDomain(privilegeDomain);
        domain.setEntityDomain(entityDomain);//(entityHandler.getEntityByID(entityDomain.getEntityID(), 1));
        domain.setOperationDomain(operationDomain);
        domain.setStatusDomain(statusDomain);

        domain.setOwnerID(model.getOwnerID());
        domain.setOrgID(model.getOrgID());
        domain.setGroupBITS(model.getGroupBITS());
        domain.setPermsBITS(model.getPermsBITS());
        domain.setStatusBITS(model.getStatusBITS());
        domain.setCreatedDate(model.getCreatedDate());
        domain.setModifiedDate(model.getModifiedDate());
        domain.setSyncedDate(model.getSyncedDate());

        return domain;
    }
}
/*
    public ArrayList<cTreeModel> getPermissionTree() {
        List<cPrivilegeModel> privilegeModels   = privilegeDBA.getPrivilegeList();
        List<cPermissionModel> permissionModels = permissionDBA.getDistinctPermissionList();

        //Gson gson = new Gson();
        //String perm = gson.toJson(permissionModels);
        //Log.d("PERM = ",""+perm);

        ArrayList<cTreeModel> treeModel = new ArrayList<>();

        cPrivilegeDomain privilegeDomain;
        cPermissionDomain permissionDomain;

        int parentIndex = 0, childIndex = 0;

        for (int i = 0; i < privilegeModels.size(); i++) {
            privilegeDomain = privilegeHandler.ModelToDomain(
                    privilegeDBA.getPrivilegeByID(privilegeModels.get(i).getPrivilegeID()));
            if (privilegeDomain != null) {
                treeModel.add(new cTreeModel(parentIndex, -1, 0, privilegeDomain));
                //Log.d("TREE PARENT = ", "(" + parentIndex + " , " + -1 + ")");
                childIndex = parentIndex;
                for (int j = 0; j < permissionModels.size(); j++) {
                    if (privilegeDomain.getPrivilegeID() == permissionModels.get(j).getPrivilegeID()) {
                        childIndex = childIndex + 1;
                        permissionDomain = ModelToDomain(permissionModels.get(j));
                        /*
                        permissionDomain = ModelToDomain(
                                permissionDBA.getPermissionByIDs(permissionModels.get(j).getPrivilegeID(),
                                        permissionModels.get(j).getEntityID()));
                                /*,
                                        permissionModels.get(j).getEntityID(),
                                        permissionModels.get(j).getOperationID(),
                                        permissionModels.get(j).getStatusID()
                                ));

                        treeModel.add(new cTreeModel(childIndex, parentIndex, 1, permissionDomain));
                        //Log.d("TREE CHILDREN = ", "("+childIndex+" , "+parentIndex+") = "+permissionDomain.getEntityID()+"->"+permissionDomain.getTypeID());
                    }
                }
                parentIndex = childIndex + 1;
            }
        }

        return treeModel;
    }
*/