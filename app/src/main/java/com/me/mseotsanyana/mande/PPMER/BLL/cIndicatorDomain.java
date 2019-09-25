package com.me.mseotsanyana.mande.PPMER.BLL;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class cIndicatorDomain implements Parcelable {
    private int indicatorID;
    private int ownerID;
    private int statusID;
    private String indicatorName;
    private String indicatorDescription;
    private int indicatorType;
    private Date createDate;

    public cIndicatorDomain(){

    }

    public static final Creator<cIndicatorDomain> CREATOR = new Creator<cIndicatorDomain>() {
        @Override
        public cIndicatorDomain createFromParcel(Parcel in) {
            return new cIndicatorDomain(in);
        }

        @Override
        public cIndicatorDomain[] newArray(int size) {
            return new cIndicatorDomain[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    protected cIndicatorDomain(Parcel in) {
        this.setIndicatorID(in.readInt());
        this.setOwnerID(in.readInt());
        this.setStatusID(in.readInt());
        this.setIndicatorName(in.readString());
        this.setIndicatorDescription(in.readString());
        this.setIndicatorType(in.readInt());
        this.setCreateDate(new Date(in.readLong()));
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(this.getIndicatorID());
        out.writeInt(this.getOwnerID());
        out.writeInt(this.getStatusID());
        out.writeString(this.getIndicatorName());
        out.writeString(this.getIndicatorDescription());
        out.writeInt(this.getIndicatorType());
        out.writeLong(this.getCreateDate().getTime());
    }


    public int getIndicatorID() {
		return indicatorID;
	}

	public void setIndicatorID(int indicatorID) {
		this.indicatorID = indicatorID;
	}

	public int getOwnerID() {
		return ownerID;
	}

	public void setOwnerID(int ownerID) {
		this.ownerID = ownerID;
	}

	public String getIndicatorName() {
		return indicatorName;
	}
	
	public void setIndicatorName(String indicatorName) {
		this.indicatorName = indicatorName;
	}

	public String getIndicatorDescription() {
		return indicatorDescription;
	}

	public void setIndicatorDescription(String indicatorDescription) {
		this.indicatorDescription = indicatorDescription;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}


    public int getStatusID() {
        return statusID;
    }

    public void setStatusID(int statusID) {
        this.statusID = statusID;
    }

    public int getIndicatorType() {
        return indicatorType;
    }

    public void setIndicatorType(int indicatorType) {
        this.indicatorType = indicatorType;
    }
}
