package com.me.mseotsanyana.mande.UTIL.DAL;


import com.me.mseotsanyana.mande.DAL.model.session.cUserModel;

public class cUserRequest {

    private String operation;
    private cUserModel user;

    public void setOperation(String operation)
    {
        this.operation = operation;
    }

    public void setUser(cUserModel user)
    {
        this.user = user;
    }
}
