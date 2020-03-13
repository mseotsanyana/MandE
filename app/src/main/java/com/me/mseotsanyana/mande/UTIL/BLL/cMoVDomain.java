package com.me.mseotsanyana.mande.UTIL.BLL;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class cMoVDomain implements Parcelable{
	private int MoVID;
	private int ownerID;
	private String MoVName;
	private String MoVDescription;
	private Date createDate;

    protected cMoVDomain(Parcel in) {
        this.setMoVID(in.readInt());
        this.setOwnerID(in.readInt());
        this.setMoVName(in.readString());
        this.setMoVDescription(in.readString());
        this.setCreateDate(new Date(in.readLong()));
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(this.getMoVID());
        out.writeInt(this.getOwnerID());
        out.writeString(this.getMoVName());
        out.writeString(this.getMoVDescription());
        out.writeLong(this.getCreateDate().getTime());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<cMoVDomain> CREATOR = new Creator<cMoVDomain>() {
        @Override
        public cMoVDomain createFromParcel(Parcel in) {
            return new cMoVDomain(in);
        }

        @Override
        public cMoVDomain[] newArray(int size) {
            return new cMoVDomain[size];
        }
    };

    public int getMoVID() {
		return MoVID;
	}
	
	public void setMoVID(int moVID) {
		MoVID = moVID;
	}


	public String getMoVName() {
		return MoVName;
	}

	public void setMoVName(String moVName) {
		MoVName = moVName;
	}

	public String getMoVDescription() {
		return MoVDescription;
	}

	public void setMoVDescription(String moVDescription) {
		MoVDescription = moVDescription;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

    public int getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(int ownerID) {
        this.ownerID = ownerID;
    }
}
