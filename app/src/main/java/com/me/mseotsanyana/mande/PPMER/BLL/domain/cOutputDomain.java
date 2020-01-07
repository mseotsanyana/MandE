package com.me.mseotsanyana.mande.PPMER.BLL.domain;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class cOutputDomain implements Parcelable {
    private int outputID;
    private int objectiveID;
	private int ownerID;
	private String outputName;
	private String outputDescription;
    private Date createDate;

    public cOutputDomain() {
        super();
    }

    protected cOutputDomain(Parcel in) {
        this.setOutputID(in.readInt());
        this.setObjectiveID(in.readInt());
        this.setOwnerID(in.readInt());
        this.setOutputName(in.readString());
        this.setOutputDescription(in.readString());
        this.setCreateDate(new Date(in.readLong()));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(this.getOutputID());
        out.writeInt(this.getObjectiveID());
        out.writeInt(this.getOwnerID());
        out.writeString(this.getOutputName());
        out.writeString(this.getOutputDescription());
        out.writeLong(this.getCreateDate().getTime());
    }

    public static final Creator<cOutputDomain> CREATOR = new Creator<cOutputDomain>() {
        @Override
        public cOutputDomain createFromParcel(Parcel in) {
            return new cOutputDomain(in);
        }

        @Override
        public cOutputDomain[] newArray(int size) {
            return new cOutputDomain[size];
        }
    };

    public int getOutputID() {
		return outputID;
	}

	public void setOutputID(int outputID) {
		this.outputID = outputID;
	}

    public int getObjectiveID() {
        return objectiveID;
    }

    public void setObjectiveID(int objectiveID) {
        this.objectiveID = objectiveID;
    }

    public int getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(int ownerID) {
        this.ownerID = ownerID;
    }

	public String getOutputName() {
		return outputName;
	}

	public void setOutputName(String outputName) {
		this.outputName = outputName;
	}

    public String getOutputDescription() {
        return outputDescription;
    }

    public void setOutputDescription(String outputDescription) {
        this.outputDescription = outputDescription;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
