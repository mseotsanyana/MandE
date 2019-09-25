package com.me.mseotsanyana.mande.PPMER.BLL;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class cActivityDomain implements Parcelable {
	private int activityID;
	private int ownerID;
	private String activityName;
	private String activityDescription;
	private Date createDate;

    public cActivityDomain(){}

    protected cActivityDomain(Parcel in) {
        this.setActivityID(in.readInt());
        this.setOwnerID(in.readInt());
        this.setActivityName(in.readString());
        this.setActivityDescription(in.readString());
        this.setCreateDate(new Date(in.readLong()));
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(this.getActivityID());
        out.writeInt(this.getOwnerID());
        out.writeString(this.getActivityName());
        out.writeString(this.getActivityDescription());
        out.writeLong(this.getCreateDate().getTime());
    }

    public static final Creator<cActivityDomain> CREATOR = new Creator<cActivityDomain>() {
        @Override
        public cActivityDomain createFromParcel(Parcel in) {
            return new cActivityDomain(in);
        }

        @Override
        public cActivityDomain[] newArray(int size) {
            return new cActivityDomain[size];
        }
    };
    @Override
    public int describeContents() {
        return 0;
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

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

	public String getActivityDescription() {
		return activityDescription;
	}

	public void setActivityDescription(String activityDescription) {
		this.activityDescription = activityDescription;
	}

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

}

