package com.me.mseotsanyana.mande.UTILITY.BLL;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by mseotsanyana on 2017/06/26.
 */

public class cOutputActivityDomain implements Parcelable {
    private int outputID;
    private int activityID;
    private int ownerID;
    private Date createDate;

    public cOutputActivityDomain() {
        super();
    }

    protected cOutputActivityDomain(Parcel in) {
        super();
        this.setOutputID(in.readInt());
        this.setActivityID(in.readInt());
        this.setOwnerID(in.readInt());
        this.setCreateDate(new Date(in.readLong()));
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(this.getOutputID());
        out.writeInt(this.getActivityID());
        out.writeInt(this.getOwnerID());
        out.writeLong(this.getCreateDate().getTime());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<cOutputActivityDomain> CREATOR = new Creator<cOutputActivityDomain>() {
        @Override
        public cOutputActivityDomain createFromParcel(Parcel in) {
            return new cOutputActivityDomain(in);
        }

        @Override
        public cOutputActivityDomain[] newArray(int size) {
            return new cOutputActivityDomain[size];
        }
    };

    public int getOutputID() {
        return outputID;
    }

    public void setOutputID(int outputID) {
        this.outputID = outputID;
    }

    public int getActivityID() {
        return activityID;
    }

    public void setActivityID(int activityID) {
        this.activityID = activityID;
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
