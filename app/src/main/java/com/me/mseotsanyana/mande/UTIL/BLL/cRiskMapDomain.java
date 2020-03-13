package com.me.mseotsanyana.mande.UTIL.BLL;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by mseotsanyana on 2017/07/28.
 */

public class cRiskMapDomain implements Parcelable {
    private int riskMapID;
    private int riskRegisterID;
    private int ownerID;
    private String riskMapName;
    private String riskMapDescription;
    private int lowerLimit;
    private int upperLimit;
    private Date createDate;

    protected cRiskMapDomain(Parcel in) {
        this.setRiskMapID(in.readInt());
        this.setRiskRegisterID(in.readInt());
        this.setOwnerID(in.readInt());
        this.setRiskMapName(in.readString());
        this.setRiskMapDescription(in.readString());
        this.setLowerLimit(in.readInt());
        this.setUpperLimit(in.readInt());
        this.setCreateDate(new Date(in.readLong()));
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(this.getRiskMapID());
        out.writeInt(this.getRiskRegisterID());
        out.writeInt(this.getOwnerID());
        out.writeString(this.getRiskMapName());
        out.writeString(this.getRiskMapDescription());
        out.writeInt(this.getLowerLimit());
        out.writeInt(this.getUpperLimit());
        out.writeLong(this.getCreateDate().getTime());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<cRiskMapDomain> CREATOR = new Creator<cRiskMapDomain>() {
        @Override
        public cRiskMapDomain createFromParcel(Parcel in) {
            return new cRiskMapDomain(in);
        }

        @Override
        public cRiskMapDomain[] newArray(int size) {
            return new cRiskMapDomain[size];
        }
    };

    public int getRiskMapID() {
        return riskMapID;
    }

    public void setRiskMapID(int riskMapID) {
        this.riskMapID = riskMapID;
    }

    public int getRiskRegisterID() {
        return riskRegisterID;
    }

    public void setRiskRegisterID(int riskRegisterID) {
        this.riskRegisterID = riskRegisterID;
    }

    public int getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(int ownerID) {
        this.ownerID = ownerID;
    }

    public String getRiskMapName() {
        return riskMapName;
    }

    public void setRiskMapName(String riskMapName) {
        this.riskMapName = riskMapName;
    }

    public String getRiskMapDescription() {
        return riskMapDescription;
    }

    public void setRiskMapDescription(String riskMapDescription) {
        this.riskMapDescription = riskMapDescription;
    }

    public int getLowerLimit() {
        return lowerLimit;
    }

    public void setLowerLimit(int lowerLimit) {
        this.lowerLimit = lowerLimit;
    }

    public int getUpperLimit() {
        return upperLimit;
    }

    public void setUpperLimit(int upperLimit) {
        this.upperLimit = upperLimit;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
