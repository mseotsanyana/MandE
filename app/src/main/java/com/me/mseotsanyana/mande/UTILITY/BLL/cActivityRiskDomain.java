package com.me.mseotsanyana.mande.UTILITY.BLL;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by mseotsanyana on 2017/06/27.
 */

public class cActivityRiskDomain implements Parcelable{
    private int activityID;
    private int riskID;
    private int ownerID;
    private Date createDate;

    protected cActivityRiskDomain(Parcel in) {
        this.setActivityID(in.readInt());
        this.setRiskID(in.readInt());
        this.setOwnerID(in.readInt());
        this.setCreateDate(new Date(in.readLong()));
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(this.getActivityID());
        out.writeInt(this.getRiskID());
        out.writeInt(this.getOwnerID());
        out.writeLong(this.getCreateDate().getTime());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<cActivityRiskDomain> CREATOR = new Creator<cActivityRiskDomain>() {
        @Override
        public cActivityRiskDomain createFromParcel(Parcel in) {
            return new cActivityRiskDomain(in);
        }

        @Override
        public cActivityRiskDomain[] newArray(int size) {
            return new cActivityRiskDomain[size];
        }
    };

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

    public void setActivityID(int activityID) {
        this.activityID = activityID;
    }

    public int getActivityID() {
        return activityID;
    }
}
