package com.me.mseotsanyana.mande.PPMER.BLL;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class cRiskMitigationDomain implements Parcelable {
    private int riskMitigationID;
	private int riskID;
	private int ownerID;
	private String riskName;
	private String riskDescription;
	private Date createDate;

    protected cRiskMitigationDomain(Parcel in) {
        this.setRiskMitigationID(in.readInt());
        this.setRiskID(in.readInt());
        this.setOwnerID(in.readInt());
        this.setRiskName(in.readString());
        this.setRiskDescription(in.readString());
        this.setCreateDate(new Date(in.readLong()));
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(this.getRiskMitigationID());
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

    public static final Creator<cRiskMitigationDomain> CREATOR = new Creator<cRiskMitigationDomain>() {
        @Override
        public cRiskMitigationDomain createFromParcel(Parcel in) {
            return new cRiskMitigationDomain(in);
        }

        @Override
        public cRiskMitigationDomain[] newArray(int size) {
            return new cRiskMitigationDomain[size];
        }
    };

    public int getRiskMitigationID() {
        return riskMitigationID;
    }

    public void setRiskMitigationID(int riskMitigationID) {
        this.riskMitigationID = riskMitigationID;
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
