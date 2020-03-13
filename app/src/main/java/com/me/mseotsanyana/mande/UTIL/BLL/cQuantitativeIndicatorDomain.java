package com.me.mseotsanyana.mande.UTIL.BLL;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class cQuantitativeIndicatorDomain implements Parcelable {
	private int indicatorID;
    private int ownerID;
    private int unitID;
    private int quantitativeBaseline;
    private Date createDate;

    public static final Creator<cQuantitativeIndicatorDomain> CREATOR = new Creator<cQuantitativeIndicatorDomain>() {
        @Override
        public cQuantitativeIndicatorDomain createFromParcel(Parcel in) {
            return new cQuantitativeIndicatorDomain(in);
        }

        @Override
        public cQuantitativeIndicatorDomain[] newArray(int size) {
            return new cQuantitativeIndicatorDomain[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    protected cQuantitativeIndicatorDomain(Parcel in) {
        this.setIndicatorID(in.readInt());
        this.setOwnerID(in.readInt());
        this.setUnitID(in.readInt());
        this.setQuantitativeBaseline(in.readInt());
        this.setCreateDate(new Date(in.readLong()));
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(this.getIndicatorID());
        out.writeInt(this.getOwnerID());
        out.writeInt(this.getUnitID());
        out.writeInt(this.getQuantitativeBaseline());
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

    public int getUnitID() {
        return unitID;
    }

    public void setUnitID(int unitID) {
        this.unitID = unitID;
    }

    public int getQuantitativeBaseline() {
        return quantitativeBaseline;
    }

    public void setQuantitativeBaseline(int quantitativeBaseline) {
        this.quantitativeBaseline = quantitativeBaseline;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

}
