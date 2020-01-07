package com.me.mseotsanyana.mande.PPMER.BLL;

import android.os.Parcel;
import android.os.Parcelable;

import com.me.mseotsanyana.mande.PPMER.BLL.domain.cImpactDomain;

/**
 * Created by mseotsanyana on 2017/07/03.
 */

public class cTriangleDomain implements Parcelable {
    private cGoalDomain goalDomain;
    private cSpecificAimDomain specificAimDomain;
    private cImpactDomain objectiveDomain;

    protected cTriangleDomain(Parcel in) {
        this.setGoalDomain((cGoalDomain) in.readParcelable(cGoalDomain.class.getClassLoader()));
        this.setSpecificAimDomain((cSpecificAimDomain) in.readParcelable(cSpecificAimDomain.class.getClassLoader()));
        this.setObjectiveDomain((cImpactDomain) in.readParcelable(cImpactDomain.class.getClassLoader()));
    }

    /**
     * Flatten this object in to a Parcel.
     *
     * @param out  The Parcel in which the object should be written.
     * @param flags Additional flags about how the object should be written.
     *              May be 0 or {@link #PARCELABLE_WRITE_RETURN_VALUE}.
     */
    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeParcelable(this.getGoalDomain(), flags);
        out.writeParcelable(this.getSpecificAimDomain(), flags);
        out.writeParcelable(this.getObjectiveDomain(), flags);
    }

    /**
     * Describe the kinds of special objects contained in this Parcelable
     * instance's marshaled representation. For example, if the object will
     * include a file descriptor in the output of {@link #writeToParcel(Parcel, int)},
     * the return value of this method must include the
     * {@link #CONTENTS_FILE_DESCRIPTOR} bit.
     *
     * @return a bitmask indicating the set of special object types marshaled
     * by this Parcelable object instance.
     * @see #CONTENTS_FILE_DESCRIPTOR
     */
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<cTriangleDomain> CREATOR = new Creator<cTriangleDomain>() {
        @Override
        public cTriangleDomain createFromParcel(Parcel in) {
            return new cTriangleDomain(in);
        }

        @Override
        public cTriangleDomain[] newArray(int size) {
            return new cTriangleDomain[size];
        }
    };


    public cGoalDomain getGoalDomain() {
        return goalDomain;
    }

    public void setGoalDomain(cGoalDomain goalDomain) {
        this.goalDomain = goalDomain;
    }

    public cSpecificAimDomain getSpecificAimDomain() {
        return specificAimDomain;
    }

    public void setSpecificAimDomain(cSpecificAimDomain specificAimDomain) {
        this.specificAimDomain = specificAimDomain;
    }

    public cImpactDomain getObjectiveDomain() {
        return objectiveDomain;
    }

    public void setObjectiveDomain(cImpactDomain objectiveDomain) {
        this.objectiveDomain = objectiveDomain;
    }
}
