package com.me.mseotsanyana.mande.BLL.model.utils;

import java.util.List;

public class cCommonPropertiesModel {
    private String cownerID;
    private String corgOwnerID;
    private int cteamOwnerBIT;
    private List<Integer> cunixpermBITS;
    private int cstatusBIT;

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

    public List<Integer> getCunixpermBITS() {
        return cunixpermBITS;
    }

    public void setCunixpermBITS(List<Integer> cunixpermBITS) {
        this.cunixpermBITS = cunixpermBITS;
    }

    public int getCstatusBIT() {
        return cstatusBIT;
    }

    public void setCstatusBIT(int cstatusBIT) {
        this.cstatusBIT = cstatusBIT;
    }
}
