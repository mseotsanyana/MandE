package com.me.mseotsanyana.mande.PPMER.DAL;

import java.util.Date;

public class cMoVModel {
	private int MoVID;
    private int ownerID;
	private String MoVName;
	private String MoVDescription;
	private Date createDate;

    public int getMoVID() {
        return MoVID;
    }

    public void setMoVID(int moVID) {
        MoVID = moVID;
    }

    public String getMoVName() {
        return MoVName;
    }

    public void setMoVName(String moVName) {
        MoVName = moVName;
    }

    public String getMoVDescription() {
        return MoVDescription;
    }

    public void setMoVDescription(String moVDescription) {
        MoVDescription = moVDescription;
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
}
