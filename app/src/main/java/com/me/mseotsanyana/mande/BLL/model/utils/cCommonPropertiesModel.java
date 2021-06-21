package com.me.mseotsanyana.mande.BLL.model.utils;

import java.util.List;

public class cCommonPropertiesModel {
    private String cownerID;
    private String corgOwnerID;
    private int cteamOwnerBIT;
    private List<Integer> cunixpermsBITS;
    private int cstatusesBITS;

    public String getCownerID() {
        return cownerID;
    }

    public void setCownerID(String cownerID) {
        this.cownerID = cownerID;
    }

    public String getCorgOwnerID() {
        return corgOwnerID;
    }

    public void setCorgOwnerID(String corgOwnerID) {
        this.corgOwnerID = corgOwnerID;
    }

    public int getCteamOwnerBIT() {
        return cteamOwnerBIT;
    }

    public void setCteamOwnerBIT(int cteamOwnerBIT) {
        this.cteamOwnerBIT = cteamOwnerBIT;
    }

    public List<Integer> getCunixpermsBITS() {
        return cunixpermsBITS;
    }

    public void setCunixpermsBITS(List<Integer> cunixpermsBITS) {
        this.cunixpermsBITS = cunixpermsBITS;
    }

    public int getCstatusesBITS() {
        return cstatusesBITS;
    }

    public void setCstatusesBITS(int cstatusesBITS) {
        this.cstatusesBITS = cstatusesBITS;
    }
}
