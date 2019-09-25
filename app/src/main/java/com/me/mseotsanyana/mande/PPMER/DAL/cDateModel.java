package com.me.mseotsanyana.mande.PPMER.DAL;

import java.util.Date;

public class cDateModel {
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
