package com.me.mseotsanyana.mande.PPMER.BLL;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class cRiskConsequenceDomain implements Parcelable {
    private int riskConsequenceID;
	private int riskID;
	private int ownerID;
	private String riskName;
	private String riskDescription;
	private Date createDate;

    protected cRiskConsequenceDomain(Parcel in) {
        this.setRiskConsequenceID(in.readInt());
        this.setRiskID(in.readInt());
        this.setOwnerID(in.readInt());
        this.setRiskName(in.readString());
        this.setRiskDescription(in.readString());
        this.setCreateDate(new Date(in.readLong()));
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(this.getRiskConsequenceID());
        out.writeInt(this.getRiskID());
        out.writeInt(this.getOwnerID());
        out.writeString(this.getRiskName());
        out.writeString(this.getRiskDescription());
        out.writeLong(this.getCreateDate().getTime());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<cRiskConsequenceDomain> CREATOR = new Creator<cRiskConsequenceDomain>() {
        @Override
        public cRiskConsequenceDomain createFromParcel(Parcel in) {
            return new cRiskConsequenceDomain(in);
        }

        @Override
        public cRiskConsequenceDomain[] newArray(int size) {
            return new cRiskConsequenceDomain[size];
        }
    };

    public int getRiskConsequenceID() {
        return riskConsequenceID;
    }

    public void setRiskConsequenceID(int riskConsequenceID) {
        this.riskConsequenceID = riskConsequenceID;
    }

    public int getRiskID() {
        return riskID;
    }

    public void setRiskID(int riskID) {
        this.riskID = riskID;
    }

    public int getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(int ownerID) {
        this.ownerID = ownerID;
    }

    public String getRiskName() {
        return riskName;
    }

    public void setRiskName(String riskName) {
        this.riskName = riskName;
    }

    public String getRiskDescription() {
        return riskDescription;
    }

    public void setRiskDescription(String riskDescription) {
        this.riskDescription = riskDescription;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
