package com.me.mseotsanyana.mande.PPMER.DAL;


import com.me.mseotsanyana.mande.BRBAC.DAL.cUserModel;

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
