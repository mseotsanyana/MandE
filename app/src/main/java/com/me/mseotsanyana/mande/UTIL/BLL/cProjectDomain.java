package com.me.mseotsanyana.mande.UTIL.BLL;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class cProjectDomain implements Parcelable
{
    private int projectID;
    private int overallAimID;
    private int specificAimID;
    private int ownerID;
    private int projectManagerID;
    private String projectName;
	private String projectDescription;
    private String country;
    private String region;
    private int projectStatus;
    private Date createDate;
    private Date startDate;
    private Date closeDate;

    public cProjectDomain() {
        super();
    }

    /**
     * For reconstructing the object reading from the Parcel data
     * @param in
     */

    private cProjectDomain(Parcel in) {
        super();
        this.setProjectID(in.readInt());
        this.setOverallAimID(in.readInt());
        this.setSpecificAimID(in.readInt());
        this.setOwnerID(in.readInt());
        this.setProjectManagerID(in.readInt());
        this.setProjectName(in.readString());
        this.setProjectDescription(in.readString());
        this.setCountry(in.readString());
        this.setRegion(in.readString());
        this.setProjectStatus(in.readInt());
        this.setCreateDate(new Date(in.readLong()));
        this.setStartDate(new Date(in.readLong()));
        this.setCloseDate(new Date(in.readLong()));
    }

    public static final Parcelable.Creator<cProjectDomain> CREATOR = new Parcelable.Creator<cProjectDomain>() {
        @Override
        public cProjectDomain createFromParcel(Parcel in) {
            return new cProjectDomain(in);
        }

        @Override
        public cProjectDomain[] newArray(int size) {
            return new cProjectDomain[size];
        }
    };

    @Override
    public int describeContents() {
        return hashCode();
    }

    @Override
    public void writeToParcel(Parcel out, int i) {
        out.writeInt(this.getProjectID());
        out.writeInt(this.getOverallAimID());
        out.writeInt(this.getSpecificAimID());
        out.writeInt(this.getOwnerID());
        out.writeInt(this.getProjectManagerID());
        out.writeString(this.getProjectName());
        out.writeString(this.getProjectDescription());
        out.writeString(this.getCountry());
        out.writeString(this.getRegion());
        out.writeInt(this.getProjectStatus());
        out.writeLong(this.getCreateDate().getTime());
        out.writeLong(this.getStartDate().getTime());
        out.writeLong(this.getCloseDate().getTime());
    }

	public int getProjectID() {
		return projectID;
	}

	public void setProjectID(int projectID) {
		this.projectID = projectID;
	}

	public String getProjectName() {
		return projectName;
	}
	
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getProjectDescription() {
		return projectDescription;
	}

	public void setProjectDescription(String projectDescription) {
		this.projectDescription = projectDescription;
	}

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getCloseDate() {
        return closeDate;
    }

    public void setCloseDate(Date closeDate) {
        this.closeDate = closeDate;
    }

    public int getProjectManagerID() {
        return projectManagerID;
    }

    public void setProjectManagerID(int projectManagerID) {
        this.projectManagerID = projectManagerID;
    }

    public int getSpecificAimID() {
        return specificAimID;
    }

    public void setSpecificAimID(int specificAimID) {
        this.specificAimID = specificAimID;
    }

    public int getOverallAimID() {
        return overallAimID;
    }

    public void setOverallAimID(int overallAimID) {
        this.overallAimID = overallAimID;
    }

    public int getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(int ownerID) {
        this.ownerID = ownerID;
    }

    public int getProjectStatus() {
        return projectStatus;
    }

    public void setProjectStatus(int projectStatus) {
        this.projectStatus = projectStatus;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
