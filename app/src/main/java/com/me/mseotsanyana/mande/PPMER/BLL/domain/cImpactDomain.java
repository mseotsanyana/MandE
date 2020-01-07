package com.me.mseotsanyana.mande.PPMER.BLL.domain;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class cImpactDomain implements Parcelable {
    private int objectiveID;
    private int projectID;
	private int ownerID;
	private String objectiveName;
	private String objectiveDescription;
    private Date createDate;

    public cImpactDomain() {
        super();
    }


    protected cImpactDomain(Parcel in) {
        objectiveID = in.readInt();
        projectID = in.readInt();
        ownerID = in.readInt();
        objectiveName = in.readString();
        objectiveDescription = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(objectiveID);
        dest.writeInt(projectID);
        dest.writeInt(ownerID);
        dest.writeString(objectiveName);
        dest.writeString(objectiveDescription);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<cImpactDomain> CREATOR = new Creator<cImpactDomain>() {
        @Override
        public cImpactDomain createFromParcel(Parcel in) {
            return new cImpactDomain(in);
        }

        @Override
        public cImpactDomain[] newArray(int size) {
            return new cImpactDomain[size];
        }
    };

    public int getObjectiveID() {
        return objectiveID;
    }

    public void setObjectiveID(int objectiveID) {
        this.objectiveID = objectiveID;
    }

    public int getProjectID() {
        return projectID;
    }

    public void setProjectID(int projectID) {
        this.projectID = projectID;
    }

    public int getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(int ownerID) {
        this.ownerID = ownerID;
    }

    public String getObjectiveName() {
        return objectiveName;
    }

    public void setObjectiveName(String objectiveName) {
        this.objectiveName = objectiveName;
    }

    public String getObjectiveDescription() {
        return objectiveDescription;
    }

    public void setObjectiveDescription(String objectiveDescription) {
        this.objectiveDescription = objectiveDescription;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
