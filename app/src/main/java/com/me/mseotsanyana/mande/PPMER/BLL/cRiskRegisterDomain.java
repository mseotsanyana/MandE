package com.me.mseotsanyana.mande.PPMER.BLL;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by mseotsanyana on 2017/07/28.
 */

public class cRiskRegisterDomain implements Parcelable {
    private int riskRegisterID;
    private int projectID;
    private int ownerID;
    private String riskRegisterName;
    private String riskRegisterDescription;
    private Date createDate;

    protected cRiskRegisterDomain(Parcel in) {
        this.setRiskRegisterID(in.readInt());
        this.setProjectID(in.readInt());
        this.setOwnerID(in.readInt());
        this.setRiskRegisterName(in.readString());
        this.setRiskRegisterDescription(in.readString());
        this.setCreateDate(new Date(in.readLong()));
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(this.getRiskRegisterID());
        out.writeInt(this.getProjectID());
        out.writeInt(this.getOwnerID());
        out.writeString(this.getRiskRegisterName());
        out.writeString(this.getRiskRegisterDescription());
        out.writeLong(this.getCreateDate().getTime());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<cRiskRegisterDomain> CREATOR = new Creator<cRiskRegisterDomain>() {
        @Override
        public cRiskRegisterDomain createFromParcel(Parcel in) {
            return new cRiskRegisterDomain(in);
        }

        @Override
        public cRiskRegisterDomain[] newArray(int size) {
            return new cRiskRegisterDomain[size];
        }
    };

    public int getRiskRegisterID() {
        return riskRegisterID;
    }

    public void setRiskRegisterID(int riskRegisterID) {
        this.riskRegisterID = riskRegisterID;
    }

    public int getProjectID() {
        return projectID;
    }

    public void setProjectID(int projectID) {
        this.projectID = projectID;
    }

    public int getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(int ownerID) {
        this.ownerID = ownerID;
    }

    public String getRiskRegisterName() {
        return riskRegisterName;
    }

    public void setRiskRegisterName(String riskRegisterName) {
        this.riskRegisterName = riskRegisterName;
    }

    public String getRiskRegisterDescription() {
        return riskRegisterDescription;
    }

    public void setRiskRegisterDescription(String riskRegisterDescription) {
        this.riskRegisterDescription = riskRegisterDescription;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
