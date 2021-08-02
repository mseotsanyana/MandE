package com.me.mseotsanyana.mande.BLL.model.session;

import com.google.firebase.firestore.Exclude;

public class cModuleModel {
    private String moduleServerID;
    private String name;
    private boolean checked;

    public String getModuleServerID() {
        return moduleServerID;
    }

    public void setModuleServerID(String moduleServerID) {
        this.moduleServerID = moduleServerID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isChecked() {
        return checked;
    }

    @Exclude
    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
