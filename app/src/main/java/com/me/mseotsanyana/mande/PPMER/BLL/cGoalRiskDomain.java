package com.me.mseotsanyana.mande.PPMER.BLL;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by mseotsanyana on 2017/06/27.
 */

public class cGoalRiskDomain implements Parcelable{
    private int goalID;
    private int riskID;
    private int ownerID;
    private Date createDate;

    protected cGoalRiskDomain(Parcel in) {
        this.setGoalID(in.readInt());
        this.setRiskID(in.readInt());
        this.setOwnerID(in.readInt());
        this.setCreateDate(new Date(in.readLong()));
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(this.getGoalID());
        out.writeInt(this.getRiskID());
        out.writeInt(this.getOwnerID());
        out.writeLong(this.getCreateDate().getTime());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<cGoalRiskDomain> CREATOR = new Creator<cGoalRiskDomain>() {
        @Override
        public cGoalRiskDomain createFromParcel(Parcel in) {
            return new cGoalRiskDomain(in);
        }

        @Override
        public cGoalRiskDomain[] newArray(int size) {
            return new cGoalRiskDomain[size];
        }
    };

    public int getGoalID() {
        return goalID;
    }

    public void setGoalID(int goalID) {
        this.goalID = goalID;
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
