package com.me.mseotsanyana.mande.UTIL.BLL;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class cDateDomain implements Parcelable {
	private int dateID;
    private int ownerID;
	private int year;
	private int month;
	private int quarter;
	private int dayMonth;
	private int dayWeek;
	private int dayYear;
	private int dayWeekMonth;
	private int weekYear;
	private int weekMonth;

	private String nameQuarter;
	private String nameMonth;
	private String nameWeekDay;

	private Date createDate;

    public cDateDomain(){

    }

	protected cDateDomain(Parcel in) {
		this.setDateID(in.readInt());
        this.setOwnerID(in.readInt());
		this.setYear(in.readInt());
		this.setMonth(in.readInt());
		this.setQuarter(in.readInt());
		this.setDayMonth(in.readInt());
		this.setDayWeek(in.readInt());
		this.setDayYear(in.readInt());
		this.setDayWeekMonth(in.readInt());
		this.setWeekYear(in.readInt());
		this.setWeekMonth(in.readInt());
		this.setNameQuarter(in.readString());
		this.setNameMonth(in.readString());
		this.setNameWeekDay(in.readString());
		this.setCreateDate(new Date(in.readLong()));
	}

	@Override
	public void writeToParcel(Parcel out, int flags) {
		out.writeInt(this.getDateID());
        out.writeInt(this.getOwnerID());
		out.writeInt(this.getYear());
		out.writeInt(this.getMonth());
		out.writeInt(this.getQuarter());
		out.writeInt(this.getDayMonth());
		out.writeInt(this.getDayWeek());
		out.writeInt(this.getDayYear());
		out.writeInt(this.getDayWeekMonth());
		out.writeInt(this.getWeekYear());
		out.writeInt(this.getWeekMonth());
		out.writeString(this.getNameQuarter());
		out.writeString(this.getNameMonth());
		out.writeString(this.getNameWeekDay());
		out.writeLong(this.getCreateDate().getTime());
	}

	@Override
	public int describeContents() {
		return 0;
	}

	public static final Creator<cDateDomain> CREATOR = new Creator<cDateDomain>() {
		@Override
		public cDateDomain createFromParcel(Parcel in) {
			return new cDateDomain(in);
		}

		@Override
		public cDateDomain[] newArray(int size) {
			return new cDateDomain[size];
		}
	};

	public int getDateID() {
		return dateID;
	}

	public void setDateID(int dateID) {
		this.dateID = dateID;
	}

    public int getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(int ownerID) {
        this.ownerID = ownerID;
    }

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getQuarter() {
		return quarter;
	}

	public void setQuarter(int quarter) {
		this.quarter = quarter;
	}

	public int getDayMonth() {
		return dayMonth;
	}

	public void setDayMonth(int dayMonth) {
		this.dayMonth = dayMonth;
	}

	public int getDayWeek() {
		return dayWeek;
	}

	public void setDayWeek(int dayWeek) {
		this.dayWeek = dayWeek;
	}

	public int getDayYear() {
		return dayYear;
	}

	public void setDayYear(int dayYear) {
		this.dayYear = dayYear;
	}

	public int getDayWeekMonth() {
		return dayWeekMonth;
	}

	public void setDayWeekMonth(int dayWeekMonth) {
		this.dayWeekMonth = dayWeekMonth;
	}

	public int getWeekYear() {
		return weekYear;
	}

	public void setWeekYear(int weekYear) {
		this.weekYear = weekYear;
	}

	public int getWeekMonth() {
		return weekMonth;
	}

	public void setWeekMonth(int weekMonth) {
		this.weekMonth = weekMonth;
	}

	public String getNameQuarter() {
		return nameQuarter;
	}

	public void setNameQuarter(String nameQuarter) {
		this.nameQuarter = nameQuarter;
	}

	public String getNameMonth() {
		return nameMonth;
	}

	public void setNameMonth(String nameMonth) {
		this.nameMonth = nameMonth;
	}

	public String getNameWeekDay() {
		return nameWeekDay;
	}

	public void setNameWeekDay(String nameWeekDay) {
		this.nameWeekDay = nameWeekDay;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
}
