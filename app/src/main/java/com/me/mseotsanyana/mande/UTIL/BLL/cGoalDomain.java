package com.me.mseotsanyana.mande.UTIL.BLL;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by mseotsanyana on 2016/12/27.
 */

public class cGoalDomain implements Parcelable {
    private int goalID;
    private int organizationID;
    private int ownerID;
    private String goalName;
    private String goalDescription;
    private Date createDate;

    public cGoalDomain(){

    }

    public cGoalDomain(String goalName){
        this.setGoalName(goalName);
    }

    cGoalDomain(Parcel in){
        super();
    }

    public static final Creator<cGoalDomain> CREATOR = new Creator<cGoalDomain>() {
        @Override
        public cGoalDomain createFromParcel(Parcel in) {
            return new cGoalDomain(in);
        }

        @Override
        public cGoalDomain[] newArray(int size) {
            return new cGoalDomain[size];
        }
    };

    @Override
    public int describeContents() {
        return hashCode();
    }

    @Override
    public void writeToParcel(Parcel out, int i) {

    }

    public int getGoalID() {
        return goalID;
    }

    public void setGoalID(int goalID) {
        this.goalID = goalID;
    }

    public int getOrganizationID() {
        return organizationID;
    }

    public void setOrganizationID(int organizationID) {
        this.organizationID = organizationID;
    }

    public int getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(int ownerID) {
        this.ownerID = ownerID;
    }

    public String getGoalName() {
        return goalName;
    }

    public void setGoalName(String goalName) {
        this.goalName = goalName;
    }

    public String getGoalDescription() {
        return goalDescription;
    }

    public void setGoalDescription(String goalDescription) {
        this.goalDescription = goalDescription;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
