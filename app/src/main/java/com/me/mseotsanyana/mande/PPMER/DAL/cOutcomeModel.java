package com.me.mseotsanyana.mande.PPMER.DAL;

import java.util.Date;

public class cOutcomeModel {
	private int outcomeID;
    private int ownerID;
	private String outcomeName;
	private String outcomeDescription;
    private Date createDate;

	public int getOutcomeID() {
		return outcomeID;
	}

	public void setOutcomeID(int outcomeID) {
		this.outcomeID = outcomeID;
	}

    public int getOwnerID() {
        return ownerID;
    }

    public void setOwnerID(int ownerID) {
        this.ownerID = ownerID;
    }

	public String getOutcomeName() {
		return outcomeName;
	}

	public void setOutcomeName(String outcomeName) {
		this.outcomeName = outcomeName;
	}

    public String getOutcomeDescription() {
        return outcomeDescription;
    }

    public void setOutcomeDescription(String outcomeDescription) {
        this.outcomeDescription = outcomeDescription;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
