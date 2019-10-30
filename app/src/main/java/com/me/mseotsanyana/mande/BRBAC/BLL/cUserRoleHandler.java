package com.me.mseotsanyana.mande.BRBAC.BLL;

import android.content.Context;
import android.util.Log;

import com.me.mseotsanyana.mande.BRBAC.DAL.cRoleDBA;
import com.me.mseotsanyana.mande.BRBAC.DAL.cRoleModel;
import com.me.mseotsanyana.mande.BRBAC.DAL.cUserDBA;
import com.me.mseotsanyana.mande.BRBAC.DAL.cUserModel;
import com.me.mseotsanyana.mande.PPMER.BLL.cMapper;
import com.me.mseotsanyana.treeadapterlibrary.cTreeModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mseotsanyana on 2017/08/29.
 */

public class cUserRoleHandler extends cMapper<cUserRoleModel, cUserRoleDomain> {
    private Context context;
    private cSessionManager session;

    private cRoleDBA roleDBA;
    private cUserDBA userDBA;
    private cUserRoleDBA userRoleDBA;

    private cRoleHandler roleHandler;
    private cUserHandler userHandler;

    private int entityBITS, operationBITS;

    public cUserRoleHandler(Context context, cSessionManager session) {
        this.context = context;
        this.session = session;

        roleDBA = new cRoleDBA(context);
        userDBA = new cUserDBA(context);
        userRoleDBA = new cUserRoleDBA(context);

        userHandler = new cUserHandler(context, session);
        roleHandler = new cRoleHandler(context,session);

        /** 1. ENTITY SECTION **/

        // entity bits of all entities that are accessible
        entityBITS = session.loadEntityBITS(session.loadUserID(),
                session.loadOrganizationID(), cSessionManager.types[0]);

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

    // business rules for adding a user group
    public boolean addUserRoleFromExcel(cUserRoleDomain domain) {
        // map the business domain to the model.
        cUserRoleModel model = this.DomainToModel(domain);
        return userRoleDBA.addUserRoleFromExcel(model);
    }

    // business rules for adding a user group
    public boolean addUserRole(cUserRoleDomain domain) {
        // map the business domain to the model
        cUserRoleModel model = this.DomainToModel(domain);
        return userRoleDBA.addUserRole(model);
    }

    public boolean deleteUserRoles() {
        return userRoleDBA.deleteUserRoles();
    }


    public ArrayList<cUserRoleDomain> getUserRoleList() {
        List<cUserRoleModel> userRoleModel = userRoleDBA.getUserRoleList();

        ArrayList<cUserRoleDomain> userRoleDomains = new ArrayList<>();
        cUserRoleDomain domain;

        for (int i = 0; i < userRoleModel.size(); i++) {
            domain = this.ModelToDomain(userRoleModel.get(i));
            userRoleDomains.add(domain);
        }

        return userRoleDomains;
    }

    public ArrayList<cUserDomain> getUsersByRoleID(int roleID) {
        List<cUserModel> userModels = userRoleDBA.getUsersByRoleID(roleID);

        ArrayList<cUserDomain> userDomains = new ArrayList<>();
        cUserDomain domain;

        for (int i = 0; i < userModels.size(); i++) {
            domain = userHandler.ModelToDomain(userModels.get(i));
            userDomains.add(domain);
        }

        return userDomains;

    }

    public ArrayList<cRoleDomain> getRolesByUserID(int userID) {
        List<cRoleModel> roleModels = userRoleDBA.getRolesByUserID(userID);

        ArrayList<cRoleDomain> roleDomains = new ArrayList<>();
        cRoleDomain domain;

        for (int i = 0; i < roleModels.size(); i++) {
            domain = roleHandler.ModelToDomain(roleModels.get(i));
            roleDomains.add(domain);
        }

        return roleDomains;
    }

    public ArrayList<Integer> getRoleIDsByUserID(int userID) {
        return userRoleDBA.getRoleIDsByUserID(userID);
    }

    public ArrayList<Integer> getOrganizationIDsByUserID(int userID) {
        return userRoleDBA.getOrganizationIDsByUserID(userID);
    }

    public ArrayList<Integer> getRoleIDsByOrganizationID(int organizationID) {
        return userRoleDBA.getRoleIDsByOrganizationID(organizationID);
    }

    public ArrayList<cTreeModel> getRoleUserTree(int userID, int orgID,
                                                 int groupBITS, int otherBITS) {
        int statusBITS = 0; //FIXME
        List<cRoleModel> roleModels = roleDBA.getRoleList(
                userID, orgID, groupBITS, otherBITS, operationBITS, statusBITS);

        List<cUserRoleModel> userRoleModels = userRoleDBA.getUserRoleList();

        ArrayList<cTreeModel> treeModel = new ArrayList<>();

        cRoleDomain roleDomain;
        cUserDomain userDomain;

        int parentIndex = 0, childIndex = 0;

        for (int i = 0; i < roleModels.size(); i++) {
            roleDomain = roleHandler.ModelToDomain(roleDBA.getRoleByID(roleModels.get(i).getRoleID()));
            treeModel.add(new cTreeModel(parentIndex,-1,0, roleDomain));
            Log.d("TREE PARENT = ", "("+parentIndex+" , "+-1+")");
            childIndex = parentIndex;
            for (int j = 0; j < userRoleModels.size(); j++) {
                if (roleDomain.getRoleID() == userRoleModels.get(j).getRoleID()){
                    childIndex = childIndex + 1;
                    userDomain = userHandler.ModelToDomain(userDBA.getUserByID(userRoleModels.get(j).getUserID()));
                    treeModel.add(new cTreeModel(childIndex, parentIndex, 1, userDomain));
                    Log.d("TREE CHILDREN = ", "("+childIndex+" , "+parentIndex+")");
                }
            }
            parentIndex = childIndex + 1;
        }

        return treeModel;
    }

    @Override
    protected cUserRoleModel DomainToModel(cUserRoleDomain domain) {
        cUserRoleModel model = new cUserRoleModel();

        model.setUserID(domain.getUserID());
        model.setRoleID(domain.getRoleID());
        model.setOwnerID(domain.getOwnerID());
        model.setGroupBITS(domain.getGroupBITS());
        model.setPermsBITS(domain.getPermsBITS());
        model.setStatusBITS(domain.getStatusBITS());
        model.setCreatedDate(domain.getCreatedDate());
        model.setModifiedDate(domain.getModifiedDate());
        model.setSyncedDate(domain.getSyncedDate());

        return model;
    }

    @Override
    protected cUserRoleDomain ModelToDomain(cUserRoleModel model) {
        cUserRoleDomain domain = new cUserRoleDomain();

        domain.setUserID(model.getUserID());
        domain.setRoleID(model.getRoleID());
        domain.setOwnerID(model.getOwnerID());
        domain.setGroupBITS(model.getGroupBITS());
        domain.setPermsBITS(model.getPermsBITS());
        domain.setStatusBITS(model.getStatusBITS());
        domain.setCreatedDate(model.getCreatedDate());
        domain.setModifiedDate(model.getModifiedDate());
        domain.setSyncedDate(model.getSyncedDate());

        return domain;
    }
}
