package com.me.mseotsanyana.mande.UTIL;


import com.me.mseotsanyana.mande.BLL.model.session.cPermissionModel;

import java.util.ArrayList;

/**
 * Created by mseotsanyana on 2018/03/08.
 */

public class cPermParam {
    private ArrayList<cPermissionModel> create_perms;
    private ArrayList<cPermissionModel> update_perms;
    private ArrayList<cPermissionModel> delete_perms;

    public cPermParam(ArrayList<cPermissionModel> create_perms,
                      ArrayList<cPermissionModel> update_perms,
                      ArrayList<cPermissionModel> delete_perms){

        this.setCreate_perms(create_perms);
        this.setUpdate_perms(update_perms);
        this.setDelete_perms(delete_perms);

    }

    public ArrayList<cPermissionModel> getCreate_perms() {
        return create_perms;
    }

    public void setCreate_perms(ArrayList<cPermissionModel> create_perms) {
        this.create_perms = create_perms;
    }

    public ArrayList<cPermissionModel> getDelete_perms() {
        return delete_perms;
    }

    public void setDelete_perms(ArrayList<cPermissionModel> delete_perms) {
        this.delete_perms = delete_perms;
    }

    public ArrayList<cPermissionModel> getUpdate_perms() {
        return update_perms;
    }

    public void setUpdate_perms(ArrayList<cPermissionModel> update_perms) {
        this.update_perms = update_perms;
    }
}
