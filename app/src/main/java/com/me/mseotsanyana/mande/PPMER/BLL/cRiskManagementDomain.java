package com.me.mseotsanyana.mande.PPMER.BLL;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class cRiskManagementDomain  implements Parcelable {
	private int riskID;
	private int timeID;
	private int ownerID;
	private int likelihood;
	private int impact;
    private String comment;
	private Date createDate;

    protected cRiskManagementDomain(Parcel in) {
        this.setRiskID(in.readInt());
        this.setTimeID(in.readInt());
        this.setOwnerID(in.readInt());
        this.setLikelihood(in.readInt());
        this.setImpact(in.readInt());
        this.setComment(in.readString());
        this.setCreateDate(new Date(in.readLong()));
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(this.getRiskID());
        out.writeInt(this.getTimeID());
        out.writeInt(this.getOwnerID());
        out.writeInt(this.getLikelihood());
        out.writeInt(this.getImpact());
        out.writeString(this.getComment());
        out.writeLong(this.getCreateDate().getTime());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<cRiskManagementDomain> CREATOR = new Creator<cRiskManagementDomain>() {
        @Override
        public cRiskManagementDomain createFromParcel(Parcel in) {
            return new cRiskManagementDomain(in);
        }

        @Override
        public cRiskManagementDomain[] newArray(int size) {
            return new cRiskManagementDomain[size];
        }
    };

    public int getRiskID() {
		return riskID;
	}

	public void setRiskID(int riskID) {
		this.riskID = riskID;
	}

    public int getTimeID() {
        return timeID;
    }

    public void setTimeID(int timeID) {
        this.timeID = timeID;
    }

    public int getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(int ownerID) {
        this.ownerID = ownerID;
    }

    public int getLikelihood() {
        return likelihood;
    }

    public void setLikelihood(int likelihood) {
        this.likelihood = likelihood;
    }

    public int getImpact() {
        return impact;
    }

    public void setImpact(int impact) {
        this.impact = impact;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

}
