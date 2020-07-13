package com.me.mseotsanyana.mande.DAL.model.wpb;

import com.me.mseotsanyana.mande.DAL.model.session.cUserModel;

import java.util.Set;

public class cHumanSetModel {
    private long humanSetID;
    private long inputID;
    private long userID;

    private Set<cUserModel> userModelSet;

    public long getHumanSetID() {
        return humanSetID;
    }

    public void setHumanSetID(long humanSetID) {
        this.humanSetID = humanSetID;
    }

    public long getInputID() {
        return inputID;
    }

    public void setInputID(long inputID) {
        this.inputID = inputID;
    }

    public long getUserID() {
        return userID;
    }

    public void setUserID(long userID) {
        this.userID = userID;
    }

    public Set<cUserModel> getUserModelSet() {
        return userModelSet;
    }

    public void setUserModelSet(Set<cUserModel> userModelSet) {
        this.userModelSet = userModelSet;
    }
}
