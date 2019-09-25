package com.me.mseotsanyana.mande.BRBAC.DAL;

import java.util.HashMap;
import java.util.Objects;

public class cPermissionTreeModel {
    private int organizationID;
    private int privilegeID;
    private cEntityModel entityModel;

    private HashMap<cOperationModel,
            HashMap<cStatusModel, cPermissionModel>> permModelDetails;

    public cPermissionTreeModel(){}

    public cPermissionTreeModel(int organizationID, int privilegeID, cEntityModel entityModel){
        this.organizationID = organizationID;
        this.privilegeID = privilegeID;
        this.entityModel = entityModel;
    }

    public cPermissionTreeModel(int organizationID, int privilegeID, cEntityModel entityModel,
                         HashMap<cOperationModel, HashMap<cStatusModel,
                                 cPermissionModel>> permModelDetails){
       this.organizationID = organizationID;
       this.privilegeID = privilegeID;
       this.entityModel = entityModel;
       this.permModelDetails = permModelDetails;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        cPermissionTreeModel that = (cPermissionTreeModel) o;
        return organizationID == that.organizationID && privilegeID == that.privilegeID &&
                Objects.equals(entityModel, that.entityModel) &&
                Objects.equals(permModelDetails, that.permModelDetails);
    }

    @Override
    public int hashCode() {
        return Objects.hash(organizationID, privilegeID, entityModel, permModelDetails);
    }

    public int getOrganizationID() {
        return organizationID;
    }

    public void setOrganizationID(int organizationID) {
        this.organizationID = organizationID;
    }

    public int getPrivilegeID() {
        return privilegeID;
    }

    public void setPrivilegeID(int privilegeID) {
        this.privilegeID = privilegeID;
    }

    public cEntityModel getEntityModel() {
        return entityModel;
    }

    public void setEntityModel(cEntityModel entityModel) {
        this.entityModel = entityModel;
    }

    public HashMap<cOperationModel, HashMap<cStatusModel,
            cPermissionModel>> getPermModelDetails() {
        return permModelDetails;
    }

    public void setPermModelDetails(HashMap<cOperationModel,
            HashMap<cStatusModel, cPermissionModel>> permModelDetails) {
        this.permModelDetails = permModelDetails;
    }
}
