package com.me.mseotsanyana.mande.BLL.domain.session;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class cValueDomain implements Parcelable {
    private int valueID;
    private int organizationID;
    private int ownerID;
    private String valueName;
    private Date createDate;

    private cOrganizationDomain organizationDomain;

    public cValueDomain() {
        super();
    }

    /**
     * For reconstructing the object reading from the Parcel data
     * @param in
     */

    private cValueDomain(Parcel in) {
        super();
        this.setValueID(in.readInt());
        this.setOrganizationID(in.readInt());
        this.setOwnerID(in.readInt());
        this.setValueName(in.readString());
        this.setCreateDate(new Date(in.readLong()));

        //this.setOrganizationDomain(in.readParcelable(cOrganizationDomain.class.getClassLoader()));
    }

    public static final Creator<cValueDomain> CREATOR = new Creator<cValueDomain>() {
        @Override
        public cValueDomain createFromParcel(Parcel in) {
            return new cValueDomain(in);
        }

        @Override
        public cValueDomain[] newArray(int size) {
            return new cValueDomain[size];
        }
    };

    @Override
    public int describeContents() {
        return hashCode();
    }

    @Override
    public void writeToParcel(Parcel out, int i) {
        out.writeInt(this.getValueID());
        out.writeInt(this.getOrganizationID());
        out.writeInt(this.getOwnerID());
        out.writeString(this.getValueName());
        out.writeLong(this.getCreateDate().getTime());
    }

    public int getValueID() {
        return valueID;
    }

    public void setValueID(int valueID) {
        this.valueID = valueID;
    }

    public int getOrganizationID() {
        return organizationID;
    }

    public void setOrganizationID(int organizationID) {
        this.organizationID = organizationID;
    }

    public int getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(int ownerID) {
        this.ownerID = ownerID;
    }

    public String getValueName() {
        return valueName;
    }

    public void setValueName(String valueName) {
        this.valueName = valueName;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public cOrganizationDomain getOrganizationDomain() {
        return organizationDomain;
    }

    public void setOrganizationDomain(cOrganizationDomain organizationDomain) {
        this.organizationDomain = organizationDomain;
    }
}
