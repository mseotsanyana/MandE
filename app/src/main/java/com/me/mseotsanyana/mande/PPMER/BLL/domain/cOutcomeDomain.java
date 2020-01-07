package com.me.mseotsanyana.mande.PPMER.BLL.domain;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class cOutcomeDomain implements Parcelable {
	private int outcomeID;
	private int ownerID;
	private String outcomeName;
	private String outcomeDescription;
	private Date CreateDate;

    public cOutcomeDomain() {
        super();
    }

    protected cOutcomeDomain(Parcel in) {
        this.setOutcomeID(in.readInt());
        this.setOwnerID(in.readInt());
        this.setOutcomeName(in.readString());
        this.setOutcomeDescription(in.readString());
        this.setCreateDate(new Date(in.readLong()));
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(this.getOutcomeID());
        out.writeInt(this.getOwnerID());
        out.writeString(this.getOutcomeName());
        out.writeString(this.getOutcomeDescription());
        out.writeLong(this.getCreateDate().getTime());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<cOutcomeDomain> CREATOR = new Creator<cOutcomeDomain>() {
        @Override
        public cOutcomeDomain createFromParcel(Parcel in) {
            return new cOutcomeDomain(in);
        }

        @Override
        public cOutcomeDomain[] newArray(int size) {
            return new cOutcomeDomain[size];
        }
    };

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

	public String getOutcomeName() {
		return outcomeName;
	}

	public void setOutcomeName(String outcomeName) {
		this.outcomeName = outcomeName;
	}

    public String getOutcomeDescription() {
        return outcomeDescription;
    }

    public void setOutcomeDescription(String outcomeDescription) {
        this.outcomeDescription = outcomeDescription;
    }

    public Date getCreateDate() {
        return CreateDate;
    }

    public void setCreateDate(Date createDate) {
        CreateDate = createDate;
    }
}
