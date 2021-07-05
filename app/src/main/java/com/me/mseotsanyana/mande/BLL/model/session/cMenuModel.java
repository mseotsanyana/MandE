package com.me.mseotsanyana.mande.BLL.model.session;

import com.google.firebase.firestore.Exclude;
import java.util.List;

/**
 * Created by mseotsanyana on 2017/08/24.
 */

public class cMenuModel{
    private int menuServerID;
    private int parentServerID;

    private String name;
    private List<cMenuModel> submenu;

    public cMenuModel(){}

    public cMenuModel(String name, boolean isGroup, boolean hasChildren) {
        this.name = name;
    }

    @Exclude
    public int getMenuServerID() {
        return menuServerID;
    }

    public void setMenuServerID(int menuServerID) {
        this.menuServerID = menuServerID;
    }

    @Exclude
    public int getParentServerID() {
        return parentServerID;
    }

    public void setParentServerID(int parentServerID) {
        this.parentServerID = parentServerID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<cMenuModel> getSubmenu() {
        return submenu;
    }

    public void setSubmenu(List<cMenuModel> submenu) {
        this.submenu = submenu;
    }
}
