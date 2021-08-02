package com.me.mseotsanyana.mande.BLL.model.session;

/**
 * Created by mseotsanyana on 2017/08/24.
 */

public class cStatusModel{
    private String  statusServerID;
    private String name;
    private String description;

    private boolean checked;

    public cStatusModel(){}

    public cStatusModel(cStatusModel statusModel){
        this.setStatusServerID(statusModel.getStatusServerID());
        this.setName(statusModel.getName());
        this.setDescription(statusModel.getDescription());
        this.setChecked(statusModel.isChecked());
    }

    public String getStatusServerID() {
        return statusServerID;
    }

    public void setStatusServerID(String statusServerID) {
        this.statusServerID = statusServerID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
