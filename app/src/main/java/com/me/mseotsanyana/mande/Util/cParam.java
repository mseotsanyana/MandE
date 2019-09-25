package com.me.mseotsanyana.mande.Util;

/**
 * Created by mseotsanyana on 2018/03/08.
 */

public class cParam {
    private int userID;
    private int orgID;
    private int primaryRole;
    private int secondaryRoles;

    public cParam(int userID, int orgID, int primaryRole, int secondaryRoles){
        this.setUserID(userID);
        this.setOrgID(orgID);
        this.setPrimaryRole(primaryRole);
        this.setSecondaryRoles(secondaryRoles);
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getOrgID() {
        return orgID;
    }

    public void setOrgID(int orgID) {
        this.orgID = orgID;
    }

    public int getPrimaryRole() {
        return primaryRole;
    }

    public void setPrimaryRole(int primaryRole) {
        this.primaryRole = primaryRole;
    }

    public int getSecondaryRoles() {
        return secondaryRoles;
    }

    public void setSecondaryRoles(int secondaryRoles) {
        this.secondaryRoles = secondaryRoles;
    }
}
