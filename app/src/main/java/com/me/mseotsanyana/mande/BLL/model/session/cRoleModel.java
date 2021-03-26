package com.me.mseotsanyana.mande.BLL.model.session;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Created by mseotsanyana on 2017/08/24.
 */

public class cRoleModel implements Parcelable {
    private String roleServerID;
    private int planServerID;
    private String orgServerID;

    private String ownerID;
    private String orgOwnerID;
    private int teamOwnerBIT;
    private int unixpermsBITS;
    private int statusesBITS;

    private String name;
    private String description;

    private Date createdDate;
    private Date modifiedDate;
    private Date syncedDate;

    private cOrganizationModel organizationModel;

    private Set<cUserModel> userModelSet;
    private Set<cSessionModel> sessionModelSet;
    private Set<cPermissionModel> permissionModelSet;
    private Set<cMenuModel> menuModelSet;

    public cRoleModel(){
        //userModelSet    = new HashSet<>();
        //sessionModelSet = new HashSet<>();
        //menuModelSet    = new HashSet<>();
    }

    public String getRoleServerID() {
        return roleServerID;
    }

    public void setRoleServerID(String roleServerID) {
        this.roleServerID = roleServerID;
    }

    public int getPlanServerID() {
        return planServerID;
    }

    public void setPlanServerID(int planServerID) {
        this.planServerID = planServerID;
    }

    public String getOrgServerID() {
        return orgServerID;
    }

    public void setOrgServerID(String orgServerID) {
        this.orgServerID = orgServerID;
    }

    public String getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(String ownerID) {
        this.ownerID = ownerID;
    }

    public String getOrgOwnerID() {
        return orgOwnerID;
    }

    public void setOrgOwnerID(String orgOwnerID) {
        this.orgOwnerID = orgOwnerID;
    }

    public int getTeamOwnerBIT() {
        return teamOwnerBIT;
    }

    public void setTeamOwnerBIT(int teamOwnerBIT) {
        this.teamOwnerBIT = teamOwnerBIT;
    }

    public int getUnixpermsBITS() {
        return unixpermsBITS;
    }

    public void setUnixpermsBITS(int unixpermsBITS) {
        this.unixpermsBITS = unixpermsBITS;
    }

    public int getStatusesBITS() {
        return statusesBITS;
    }

    public void setStatusesBITS(int statusesBITS) {
        this.statusesBITS = statusesBITS;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Date getSyncedDate() {
        return syncedDate;
    }

    public void setSyncedDate(Date syncedDate) {
        this.syncedDate = syncedDate;
    }

    public cOrganizationModel getOrganizationModel() {
        return organizationModel;
    }

    public void setOrganizationModel(cOrganizationModel organizationModel) {
        this.organizationModel = organizationModel;
    }

    public Set<cUserModel> getUserModelSet() {
        return userModelSet;
    }

    public void setUserModelSet(Set<cUserModel> userModelSet) {
        this.userModelSet = userModelSet;
    }

    public Set<cSessionModel> getSessionModelSet() {
        return sessionModelSet;
    }

    public void setSessionModelSet(Set<cSessionModel> sessionModelSet) {
        this.sessionModelSet = sessionModelSet;
    }

    public Set<cPermissionModel> getPermissionModelSet() {
        return permissionModelSet;
    }

    public void setPermissionModelSet(Set<cPermissionModel> permissionModelSet) {
        this.permissionModelSet = permissionModelSet;
    }

    public Set<cMenuModel> getMenuModelSet() {
        return menuModelSet;
    }

    public void setMenuModelSet(Set<cMenuModel> menuModelSet) {
        this.menuModelSet = menuModelSet;
    }

    protected cRoleModel(Parcel in) {
        roleServerID = in.readString();
        planServerID = in.readInt();
        orgServerID = in.readString();
        ownerID = in.readString();
        orgOwnerID = in.readString();
        teamOwnerBIT = in.readInt();
        unixpermsBITS = in.readInt();
        statusesBITS = in.readInt();
        name = in.readString();
        description = in.readString();
        organizationModel = in.readParcelable(cOrganizationModel.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(roleServerID);
        dest.writeInt(planServerID);
        dest.writeString(orgServerID);
        dest.writeString(ownerID);
        dest.writeString(orgOwnerID);
        dest.writeInt(teamOwnerBIT);
        dest.writeInt(unixpermsBITS);
        dest.writeInt(statusesBITS);
        dest.writeString(name);
        dest.writeString(description);
        dest.writeParcelable(organizationModel, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<cRoleModel> CREATOR = new Creator<cRoleModel>() {
        @Override
        public cRoleModel createFromParcel(Parcel in) {
            return new cRoleModel(in);
        }

        @Override
        public cRoleModel[] newArray(int size) {
            return new cRoleModel[size];
        }
    };

}
