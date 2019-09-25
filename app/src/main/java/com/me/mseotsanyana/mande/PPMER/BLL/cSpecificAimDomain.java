package com.me.mseotsanyana.mande.PPMER.BLL;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class cSpecificAimDomain implements Parcelable
{
    private int projectID;
    private int overallAimID;
    private int ownerID;
    private String specificAimName;
	private String specificAimDescription;
    private Date createDate;

    public cSpecificAimDomain() {
        super();
    }

    /**
     * For reconstructing the object reading from the Parcel data
     * @param in
     */

    private cSpecificAimDomain(Parcel in) {
        super();
        this.setProjectID(in.readInt());
        this.setOverallAimID(in.readInt());
        this.setOwnerID(in.readInt());
        this.setSpecificAimName(in.readString());
        this.setSpecificAimDescription(in.readString());
        this.setCreateDate(new Date(in.readLong()));
    }

    public static final Creator<cSpecificAimDomain> CREATOR = new Creator<cSpecificAimDomain>() {
        @Override
        public cSpecificAimDomain createFromParcel(Parcel in) {
            return new cSpecificAimDomain(in);
        }

        @Override
        public cSpecificAimDomain[] newArray(int size) {
            return new cSpecificAimDomain[size];
        }
    };

    @Override
    public int describeContents() {
        return hashCode();
    }

    @Override
    public void writeToParcel(Parcel out, int i) {
        out.writeInt(this.getProjectID());
        out.writeInt(this.getOverallAimID());
        out.writeInt(this.getOwnerID());
        out.writeString(this.getSpecificAimName());
        out.writeString(this.getSpecificAimDescription());
        out.writeLong(this.getCreateDate().getTime());
    }

    public int getProjectID() {
        return projectID;
    }

    public void setProjectID(int projectID) {
        this.projectID = projectID;
    }

    public int getOverallAimID() {
        return overallAimID;
    }

    public void setOverallAimID(int overallAimID) {
        this.overallAimID = overallAimID;
    }

    public int getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(int ownerID) {
        this.ownerID = ownerID;
    }

    public String getSpecificAimName() {
        return specificAimName;
    }

    public void setSpecificAimName(String specificAimName) {
        this.specificAimName = specificAimName;
    }

    public String getSpecificAimDescription() {
        return specificAimDescription;
    }

    public void setSpecificAimDescription(String specificAimDescription) {
        this.specificAimDescription = specificAimDescription;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

}
