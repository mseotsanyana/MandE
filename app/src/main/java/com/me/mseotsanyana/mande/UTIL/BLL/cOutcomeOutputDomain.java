package com.me.mseotsanyana.mande.UTIL.BLL;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by mseotsanyana on 2017/06/26.
 */

public class cOutcomeOutputDomain implements Parcelable {
    private int outcomeID;
    private int outputID;
    private int ownerID;
    private Date createDate;

    public cOutcomeOutputDomain() {
        super();
    }

    protected cOutcomeOutputDomain(Parcel in) {
        super();
        this.setOutcomeID(in.readInt());
        this.setOutputID(in.readInt());
        this.setOwnerID(in.readInt());
        this.setCreateDate(new Date(in.readLong()));
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(this.getOutcomeID());
        out.writeInt(this.getOutputID());
        out.writeInt(this.getOwnerID());
        out.writeLong(this.getCreateDate().getTime());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<cOutcomeOutputDomain> CREATOR = new Creator<cOutcomeOutputDomain>() {
        @Override
        public cOutcomeOutputDomain createFromParcel(Parcel in) {
            return new cOutcomeOutputDomain(in);
        }

        @Override
        public cOutcomeOutputDomain[] newArray(int size) {
            return new cOutcomeOutputDomain[size];
        }
    };

    public int getOutcomeID() {
        return outcomeID;
    }

    public void setOutcomeID(int outcomeID) {
        this.outcomeID = outcomeID;
    }

    public int getOutputID() {
        return outputID;
    }

    public void setOutputID(int outputID) {
        this.outputID = outputID;
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
