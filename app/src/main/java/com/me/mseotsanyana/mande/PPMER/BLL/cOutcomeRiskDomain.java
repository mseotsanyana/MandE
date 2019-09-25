package com.me.mseotsanyana.mande.PPMER.BLL;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by mseotsanyana on 2017/06/27.
 */

public class cOutcomeRiskDomain implements Parcelable{
    private int outcomeID;
    private int riskID;
    private int ownerID;
    private Date createDate;

    protected cOutcomeRiskDomain(Parcel in) {
        this.setOutcomeID(in.readInt());
        this.setRiskID(in.readInt());
        this.setOwnerID(in.readInt());
        this.setCreateDate(new Date(in.readLong()));
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(this.getOutcomeID());
        out.writeInt(this.getRiskID());
        out.writeInt(this.getOwnerID());
        out.writeLong(this.getCreateDate().getTime());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<cOutcomeRiskDomain> CREATOR = new Creator<cOutcomeRiskDomain>() {
        @Override
        public cOutcomeRiskDomain createFromParcel(Parcel in) {
            return new cOutcomeRiskDomain(in);
        }

        @Override
        public cOutcomeRiskDomain[] newArray(int size) {
            return new cOutcomeRiskDomain[size];
        }
    };

    public int getOutcomeID() {
        return outcomeID;
    }

    public void setOutcomeID(int outcomeID) {
        this.outcomeID = outcomeID;
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

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
