package com.me.mseotsanyana.mande.UTILITY.DAL;


import com.me.mseotsanyana.mande.BRBAC.DAL.cUserModel;

public class cUserResponse
{

    private String result;
    private String message;
    private cUserModel user;

    public String getResult()
    {
        return result;
    }

    public String getMessage()
    {
        return message;
    }

    public cUserModel getUser()
    {
        return user;
    }
}
