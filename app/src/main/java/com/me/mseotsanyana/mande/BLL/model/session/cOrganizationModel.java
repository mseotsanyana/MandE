package com.me.mseotsanyana.mande.BLL.model.session;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;
import java.util.Objects;
import java.util.Set;

public class cOrganizationModel implements Parcelable {
    private long organizationID;
    private int serverID;
    private int ownerID;
    private int orgID;
    private int groupBITS;
    private int permsBITS;
    private int statusBITS;
    private String name;
    private String phone;
    private String fax;
    private String vision;
    private String mission;
    private String email;
    private String website;
    private Date createdDate;
    private Date modifiedDate;
    private Date syncedDate;

    Set<cUserModel> userModelSet;
    Set<cRoleModel> roleModelSet;
    Set<cValueModel> valueModelSet;
    Set<cAddressModel> addressModelSet;

    public cOrganizationModel(){}

    public cOrganizationModel(cOrganizationModel organizationModel){
        this.setOrganizationID(organizationModel.getOrganizationID());
        this.setServerID(organizationModel.getServerID());
        this.setOwnerID(organizationModel.getOwnerID());
        this.setOrgID(organizationModel.getOwnerID());
        this.setGroupBITS(organizationModel.getGroupBITS());
        this.setPermsBITS(organizationModel.getPermsBITS());
        this.setStatusBITS(organizationModel.getStatusBITS());
        this.setName(organizationModel.getName());
        this.setPhone(organizationModel.getPhone());
        this.setFax(organizationModel.getFax());
        this.setVision(organizationModel.getVision());
        this.setMission(organizationModel.getMission());
        this.setEmail(organizationModel.getEmail());
        this.setWebsite(organizationModel.getWebsite());
        this.setCreatedDate(organizationModel.getCreatedDate());
        this.setModifiedDate(organizationModel.getModifiedDate());
        this.setSyncedDate(organizationModel.getSyncedDate());
    }

    protected cOrganizationModel(Parcel in) {
        organizationID = in.readInt();
        serverID = in.readInt();
        ownerID = in.readInt();
        orgID = in.readInt();
        groupBITS = in.readInt();
        permsBITS = in.readInt();
        statusBITS = in.readInt();
        name = in.readString();
        phone = in.readString();
        fax = in.readString();
        vision = in.readString();
        mission = in.readString();
        email = in.readString();
        website = in.readString();
    }

    public static final Creator<cOrganizationModel> CREATOR = new Creator<cOrganizationModel>() {
        @Override
        public cOrganizationModel createFromParcel(Parcel in) {
            return new cOrganizationModel(in);
        }

        @Override
        public cOrganizationModel[] newArray(int size) {
            return new cOrganizationModel[size];
        }
    };

    public long getOrganizationID() {
        return organizationID;
    }

    public void setOrganizationID(long organizationID) {
        this.organizationID = organizationID;
    }

    public int getServerID() {
        return serverID;
    }

    public void setServerID(int serverID) {
        this.serverID = serverID;
    }

    public int getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(int ownerID) {
        this.ownerID = ownerID;
    }

    public int getOrgID() {
        return orgID;
    }

    public void setOrgID(int orgID) {
        this.orgID = orgID;
    }

    public int getGroupBITS() {
        return groupBITS;
    }

    public void setGroupBITS(int groupBITS) {
        this.groupBITS = groupBITS;
    }

    public int getPermsBITS() {
        return permsBITS;
    }

    public void setPermsBITS(int permsBITS) {
        this.permsBITS = permsBITS;
    }

    public int getStatusBITS() {
        return statusBITS;
    }

    public void setStatusBITS(int statusBITS) {
        this.statusBITS = statusBITS;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getVision() {
        return vision;
    }

    public void setVision(String vision) {
        this.vision = vision;
    }

    public String getMission() {
        return mission;
    }

    public void setMission(String mission) {
        this.mission = mission;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
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

    public Set<cUserModel> getUserModelSet() {
        return userModelSet;
    }

    public void setUserModelSet(Set<cUserModel> userModelSet) {
        this.userModelSet = userModelSet;
    }

    public Set<cRoleModel> getRoleModelSet() {
        return roleModelSet;
    }

    public void setRoleModelSet(Set<cRoleModel> roleModelSet) {
        this.roleModelSet = roleModelSet;
    }

    public Set<cValueModel> getValueModelSet() {
        return valueModelSet;
    }

    public void setValueModelSet(Set<cValueModel> valueModelSet) {
        this.valueModelSet = valueModelSet;
    }

    public Set<cAddressModel> getAddressModelSet() {
        return addressModelSet;
    }

    public void setAddressModelSet(Set<cAddressModel> addressModelSet) {
        this.addressModelSet = addressModelSet;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof cOrganizationModel)) return false;
        cOrganizationModel that = (cOrganizationModel) o;
        return getOrganizationID() == that.getOrganizationID() &&
                getServerID() == that.getServerID() &&
                getOwnerID() == that.getOwnerID() &&
                getOrgID() == that.getOrgID() &&
                getGroupBITS() == that.getGroupBITS() &&
                getPermsBITS() == that.getPermsBITS() &&
                getStatusBITS() == that.getStatusBITS() &&
                Objects.equals(getName(), that.getName()) &&
                Objects.equals(getPhone(), that.getPhone()) &&
                Objects.equals(getFax(), that.getFax()) &&
                Objects.equals(getVision(), that.getVision()) &&
                Objects.equals(getMission(), that.getMission()) &&
                Objects.equals(getEmail(), that.getEmail()) &&
                Objects.equals(getWebsite(), that.getWebsite()) &&
                Objects.equals(getCreatedDate(), that.getCreatedDate()) &&
                Objects.equals(getModifiedDate(), that.getModifiedDate()) &&
                Objects.equals(getSyncedDate(), that.getSyncedDate()) &&
                Objects.equals(getUserModelSet(), that.getUserModelSet()) &&
                Objects.equals(getRoleModelSet(), that.getRoleModelSet()) &&
                Objects.equals(getValueModelSet(), that.getValueModelSet()) &&
                Objects.equals(getAddressModelSet(), that.getAddressModelSet());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getOrganizationID(), getServerID(), getOwnerID(), getOrgID(),
                getGroupBITS(), getPermsBITS(), getStatusBITS(), getName(), getPhone(),
                getFax(), getVision(), getMission(), getEmail(), getWebsite(),
                getCreatedDate(), getModifiedDate(), getSyncedDate(), getUserModelSet(),
                getRoleModelSet(), getValueModelSet(), getAddressModelSet());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(organizationID);
        parcel.writeInt(serverID);
        parcel.writeInt(ownerID);
        parcel.writeInt(orgID);
        parcel.writeInt(groupBITS);
        parcel.writeInt(permsBITS);
        parcel.writeInt(statusBITS);
        parcel.writeString(name);
        parcel.writeString(phone);
        parcel.writeString(fax);
        parcel.writeString(vision);
        parcel.writeString(mission);
        parcel.writeString(email);
        parcel.writeString(website);
    }
}
