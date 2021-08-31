package com.me.mseotsanyana.mande.BLL.model.session;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.firestore.Exclude;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * Created by mseotsanyana on 2017/08/24.
 */

public class cRoleModel implements Parcelable {
    private String roleServerID;

    private String userOwnerID;
    private String organizationOwnerID;
    private int teamOwnerBIT;
    private List<Integer> unixpermBITS;
    private int statusBIT;

    private String name;
    private String description;

    private Date createdDate;
    private Date modifiedDate;

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

    public cRoleModel(cRoleModel roleModel){
        //this.setRoleServerID(roleModel.getRoleServerID());

        this.setUserOwnerID(roleModel.getUserOwnerID());
        this.setOrganizationOwnerID(roleModel.getOrganizationOwnerID());
        this.setTeamOwnerBIT(roleModel.getTeamOwnerBIT());
        this.setUnixpermBITS(roleModel.getUnixpermBITS());
        this.setStatusBIT(roleModel.getStatusBIT());

        this.setName(roleModel.getName());
        this.setDescription(roleModel.getDescription());

        this.setCreatedDate(roleModel.getCreatedDate());
        this.setModifiedDate(roleModel.getModifiedDate());
    }

    @Exclude
    public String getRoleServerID() {
        return roleServerID;
    }

    public void setRoleServerID(String roleServerID) {
        this.roleServerID = roleServerID;
    }

    public String getUserOwnerID() {
        return userOwnerID;
    }

    public void setUserOwnerID(String userOwnerID) {
        this.userOwnerID = userOwnerID;
    }

    public String getOrganizationOwnerID() {
        return organizationOwnerID;
    }

    public void setOrganizationOwnerID(String organizationOwnerID) {
        this.organizationOwnerID = organizationOwnerID;
    }

    public int getTeamOwnerBIT() {
        return teamOwnerBIT;
    }

    public void setTeamOwnerBIT(int teamOwnerBIT) {
        this.teamOwnerBIT = teamOwnerBIT;
    }

    public List<Integer> getUnixpermBITS() {
        return unixpermBITS;
    }

    public void setUnixpermBITS(List<Integer> unixpermBITS) {
        this.unixpermBITS = unixpermBITS;
    }

    public int getStatusBIT() {
        return statusBIT;
    }

    public void setStatusBIT(int statusBIT) {
        this.statusBIT = statusBIT;
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

    @Exclude
    public cOrganizationModel getOrganizationModel() {
        return organizationModel;
    }

    public void setOrganizationModel(cOrganizationModel organizationModel) {
        this.organizationModel = organizationModel;
    }

    @Exclude
    public Set<cUserModel> getUserModelSet() {
        return userModelSet;
    }

    public void setUserModelSet(Set<cUserModel> userModelSet) {
        this.userModelSet = userModelSet;
    }

    @Exclude
    public Set<cSessionModel> getSessionModelSet() {
        return sessionModelSet;
    }

    public void setSessionModelSet(Set<cSessionModel> sessionModelSet) {
        this.sessionModelSet = sessionModelSet;
    }

    @Exclude
    public Set<cPermissionModel> getPermissionModelSet() {
        return permissionModelSet;
    }

    public void setPermissionModelSet(Set<cPermissionModel> permissionModelSet) {
        this.permissionModelSet = permissionModelSet;
    }

    @Exclude
    public Set<cMenuModel> getMenuModelSet() {
        return menuModelSet;
    }

    public void setMenuModelSet(Set<cMenuModel> menuModelSet) {
        this.menuModelSet = menuModelSet;
    }

    protected cRoleModel(Parcel in) {
        roleServerID = in.readString();
        userOwnerID = in.readString();
        organizationOwnerID = in.readString();
        teamOwnerBIT = in.readInt();
        //unixpermsBITS = in.readInt();
        statusBIT = in.readInt();
        name = in.readString();
        description = in.readString();
        organizationModel = in.readParcelable(cOrganizationModel.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(roleServerID);
        dest.writeString(userOwnerID);
        dest.writeString(organizationOwnerID);
        dest.writeInt(teamOwnerBIT);
        //dest.writeInt(unixpermsBITS);
        dest.writeInt(statusBIT);
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
