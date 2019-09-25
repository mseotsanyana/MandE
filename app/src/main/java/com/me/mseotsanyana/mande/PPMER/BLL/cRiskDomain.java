package com.me.mseotsanyana.mande.PPMER.BLL;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class cRiskDomain implements Parcelable{
	private int riskID;
	private int riskRegisterID;
	private int ownerID;
	private int ownershipID;
	private String riskName;
	private String riskDescription;
	private int status;
	private int likelihood;
	private int impact;
	private Date createDate;

    protected cRiskDomain(Parcel in) {
        this.setRiskID(in.readInt());
		this.setRiskRegisterID(in.readInt());
        this.setOwnerID(in.readInt());
		this.setOwnershipID(in.readInt());
        this.setRiskName(in.readString());
        this.setRiskDescription(in.readString());
		this.setStatus(in.readInt());
		this.setLikelihood(in.readInt());
		this.setImpact(in.readInt());
        this.setCreateDate(new Date(in.readLong()));
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(this.getRiskID());
		out.writeInt(this.getRiskRegisterID());
        out.writeInt(this.getOwnerID());
		out.writeInt(this.getOwnershipID());
        out.writeString(this.getRiskName());
        out.writeString(this.getRiskDescription());
		out.writeInt(this.getStatus());
		out.writeInt(this.getLikelihood());
		out.writeInt(this.getImpact());
        out.writeLong(this.getCreateDate().getTime());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<cRiskDomain> CREATOR = new Creator<cRiskDomain>() {
        @Override
        public cRiskDomain createFromParcel(Parcel in) {
            return new cRiskDomain(in);
        }

        @Override
        public cRiskDomain[] newArray(int size) {
            return new cRiskDomain[size];
        }
    };

    public int getRiskID() {
		return riskID;
	}

	public void setRiskID(int riskID) {
		this.riskID = riskID;
	}

	public String getRiskName() {
		return riskName;
	}

	public void setRiskName(String riskName) {
		this.riskName = riskName;
	}

	public String getRiskDescription() {
		return riskDescription;
	}

	public void setRiskDescription(String riskDescription) {
		this.riskDescription = riskDescription;
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

	public int getRiskRegisterID() {
		return riskRegisterID;
	}

	public void setRiskRegisterID(int riskRegisterID) {
		this.riskRegisterID = riskRegisterID;
	}

	public int getOwnershipID() {
		return ownershipID;
	}

	public void setOwnershipID(int ownershipID) {
		this.ownershipID = ownershipID;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
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
}
