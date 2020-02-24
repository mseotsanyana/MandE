package com.me.mseotsanyana.mande.UTILITY.BLL;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by mseotsanyana on 2017/06/27.
 */

public class cOutputRiskDomain implements Parcelable{
    private int outputID;
    private int riskID;
    private int ownerID;
    private Date createDate;

    protected cOutputRiskDomain(Parcel in) {
        this.setOutputID(in.readInt());
        this.setRiskID(in.readInt());
        this.setOwnerID(in.readInt());
        this.setCreateDate(new Date(in.readLong()));
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(this.getOutputID());
        out.writeInt(this.getRiskID());
        out.writeInt(this.getOwnerID());
        out.writeLong(this.getCreateDate().getTime());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<cOutputRiskDomain> CREATOR = new Creator<cOutputRiskDomain>() {
        @Override
        public cOutputRiskDomain createFromParcel(Parcel in) {
            return new cOutputRiskDomain(in);
        }

        @Override
        public cOutputRiskDomain[] newArray(int size) {
            return new cOutputRiskDomain[size];
        }
    };

    public int getOutputID() {
        return outputID;
    }

    public void setOutputID(int outputID) {
        this.outputID = outputID;
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
