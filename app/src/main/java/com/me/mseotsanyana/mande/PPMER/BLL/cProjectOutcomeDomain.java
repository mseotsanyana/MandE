package com.me.mseotsanyana.mande.PPMER.BLL;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by mseotsanyana on 2017/06/26.
 */

public class cProjectOutcomeDomain implements Parcelable {
    private int projectID;
    private int outcomeID;
    private int ownerID;
    private Date createDate;

    public cProjectOutcomeDomain() {
        super();
    }

    protected cProjectOutcomeDomain(Parcel in) {
        super();
        this.setProjectID(in.readInt());
        this.setOutcomeID(in.readInt());
        this.setOwnerID(in.readInt());
        this.setCreateDate(new Date(in.readLong()));
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(this.getProjectID());
        out.writeInt(this.getOutcomeID());
        out.writeInt(this.getOwnerID());
        out.writeLong(this.getCreateDate().getTime());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<cProjectOutcomeDomain> CREATOR = new Creator<cProjectOutcomeDomain>() {
        @Override
        public cProjectOutcomeDomain createFromParcel(Parcel in) {
            return new cProjectOutcomeDomain(in);
        }

        @Override
        public cProjectOutcomeDomain[] newArray(int size) {
            return new cProjectOutcomeDomain[size];
        }
    };

    public int getProjectID() {
        return projectID;
    }

    public void setProjectID(int projectID) {
        this.projectID = projectID;
    }

    public int getOutcomeID() {
        return outcomeID;
    }

    public void setOutcomeID(int outcomeID) {
        this.outcomeID = outcomeID;
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
