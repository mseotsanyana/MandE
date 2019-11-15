package com.me.mseotsanyana.mande.UTILITY;

import com.me.mseotsanyana.mande.BRBAC.BLL.cPermissionDomain;

import java.util.ArrayList;

/**
 * Created by mseotsanyana on 2018/03/08.
 */

public class cPermParam {
    private ArrayList<cPermissionDomain> create_perms;
    private ArrayList<cPermissionDomain> update_perms;
    private ArrayList<cPermissionDomain> delete_perms;

    public cPermParam(ArrayList<cPermissionDomain> create_perms,
                      ArrayList<cPermissionDomain> update_perms,
                      ArrayList<cPermissionDomain> delete_perms){

        this.setCreate_perms(create_perms);
        this.setUpdate_perms(update_perms);
        this.setDelete_perms(delete_perms);

    }

    public ArrayList<cPermissionDomain> getCreate_perms() {
        return create_perms;
    }

    public void setCreate_perms(ArrayList<cPermissionDomain> create_perms) {
        this.create_perms = create_perms;
    }

    public ArrayList<cPermissionDomain> getDelete_perms() {
        return delete_perms;
    }

    public void setDelete_perms(ArrayList<cPermissionDomain> delete_perms) {
        this.delete_perms = delete_perms;
    }

    public ArrayList<cPermissionDomain> getUpdate_perms() {
        return update_perms;
    }

    public void setUpdate_perms(ArrayList<cPermissionDomain> update_perms) {
        this.update_perms = update_perms;
    }
}
