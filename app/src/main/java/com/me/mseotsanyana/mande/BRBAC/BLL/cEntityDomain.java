package com.me.mseotsanyana.mande.BRBAC.BLL;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by mseotsanyana on 2017/08/24.
 */

public class cEntityDomain implements Parcelable{

    private int entityID;
    private int typeID;
    private int ownerID;
    private int groupBITS;
    private int permsBITS;
    private int statusBITS;
    private String name;
    private String description;
    private Date createdDate;
    private Date modifiedDate;
    private Date syncedDate;

    public cEntityDomain(){}

    protected cEntityDomain(Parcel in) {
        this.setEntityID(in.readInt());
        this.setTypeID(in.readInt());
        this.setOwnerID(in.readInt());
        this.setGroupBITS(in.readInt());
        this.setPermsBITS(in.readInt());
        this.setStatusBITS(in.readInt());
        this.setName(in.readString());
        this.setDescription(in.readString());
        this.setCreatedDate(new Date(in.readLong()));
        this.setModifiedDate(new Date(in.readLong()));
        this.setSyncedDate(new Date(in.readLong()));
    }

    public cEntityDomain(cEntityDomain entityDomain){
        this.setEntityID(entityDomain.getEntityID());
        this.setTypeID(entityDomain.getTypeID());
        this.setOwnerID(entityDomain.getOwnerID());
        this.setGroupBITS(entityDomain.getGroupBITS());
        this.setPermsBITS(entityDomain.getPermsBITS());
        this.setStatusBITS(entityDomain.getStatusBITS());
        this.setName(entityDomain.getName());
        this.setDescription(entityDomain.getDescription());
        this.setCreatedDate(entityDomain.getCreatedDate());
        this.setModifiedDate(entityDomain.getModifiedDate());
        this.setSyncedDate(entityDomain.getSyncedDate());
    }


    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(this.getEntityID());
        out.writeInt(this.getTypeID());
        out.writeInt(this.getOwnerID());
        out.writeInt(this.getGroupBITS());
        out.writeInt(this.getPermsBITS());
        out.writeInt(this.getStatusBITS());
        out.writeString(this.getName());
        out.writeString(this.getDescription());
        out.writeLong(this.getCreatedDate().getTime());
        out.writeLong(this.getModifiedDate().getTime());
        out.writeLong(this.getSyncedDate().getTime());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<cRoleDomain> CREATOR = new Creator<cRoleDomain>() {
        @Override
        public cRoleDomain createFromParcel(Parcel in) {
            return new cRoleDomain(in);
        }

        @Override
        public cRoleDomain[] newArray(int size) {
            return new cRoleDomain[size];
        }
    };

    public int getEntityID() {
        return entityID;
    }

    public void setEntityID(int entityID) {
        this.entityID = entityID;
    }

    public int getTypeID() {
        return typeID;
    }

    public void setTypeID(int typeID) {
        this.typeID = typeID;
    }

    public int getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(int ownerID) {
        this.ownerID = ownerID;
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
}
